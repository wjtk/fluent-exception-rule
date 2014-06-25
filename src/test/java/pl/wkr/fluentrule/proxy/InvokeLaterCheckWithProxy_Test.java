package pl.wkr.fluentrule.proxy;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import pl.wkr.fluentrule.assertfactory.AssertFactory;
import pl.wkr.fluentrule.test_.ThrowingThrowableAssert;
import pl.wkr.fluentrule.test_.BaseWithFluentThrownTest;
import pl.wkr.fluentrule.test_.exception.AnyExc;
import pl.wkr.fluentrule.test_.exception.ExpectedExc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class InvokeLaterCheckWithProxy_Test extends BaseWithFluentThrownTest {

    private static final String INVOCATION_EXCEPTION_MESSAGE =
            "Exception, not AssertionError when invoking method";

    private CheckWithProxy<ThrowingThrowableAssert, Throwable> checkWithProxy;

    @Mock
    private AssertFactory<ThrowingThrowableAssert, Throwable> assertFactory;
    @Mock
    private ThrowingThrowableAssert returnedAsProxy;
    @Mock
    private ThrowingThrowableAssert returnedAsAssert;

    private CalledMethodRegister calledMethodRegister;


    @Before
    public void before() throws Throwable {
        initMocks(this);

        when(assertFactory.getAssert(any(Throwable.class))).thenReturn(returnedAsAssert);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                throw (Throwable) invocationOnMock.getArguments()[0];
            }
        }).when(returnedAsAssert).throwArgument(any(Throwable.class));

        RegisteringProxyFactory<ThrowingThrowableAssert, Throwable> registeringProxyFactory =
            new RegisteringProxyFactory<ThrowingThrowableAssert, Throwable>() {
                @Override
                public ThrowingThrowableAssert createProxy(CalledMethodRegister methodRegister) {
                    calledMethodRegister = methodRegister;
                    return returnedAsProxy;
                }
            };

        checkWithProxy = new InvokeLaterCheckWithProxy<ThrowingThrowableAssert, Throwable>(
                assertFactory, registeringProxyFactory);

        checkWithProxy.getAssertProxy();
    }


    @Test
    public void should_return_proxy() {
        assertThat(checkWithProxy.getAssertProxy()).isSameAs(returnedAsProxy);
    }


    @Test
    public void should_call_method_in_order() throws NoSuchMethodException {
        calledMethodRegister.wasCalled(ThrowingThrowableAssert.getMethod("order1", String.class, String.class), "a", "b");
        calledMethodRegister.wasCalled(ThrowingThrowableAssert.getMethod("order2"));

        checkWithProxy.check(new AnyExc());

        InOrder io = inOrder(returnedAsAssert);
        io.verify(returnedAsAssert).order1("a", "b");
        io.verify(returnedAsAssert).order2();
    }


    @Test
    public void should_throw_exception_because_not_assertion_error_from_throwable_assert_method() throws NoSuchMethodException {
        Throwable expected = new ExpectedExc();

        calledMethodRegister.wasCalled(ThrowingThrowableAssert.getMethod("throwArgument", Throwable.class), expected);

        thrown.expect(IllegalStateException.class).hasMessageContaining(INVOCATION_EXCEPTION_MESSAGE);
        thrown.expectCause().isSameAs(expected);

        checkWithProxy.check(new AnyExc());
    }


    @Test
    public void should_throw_illegal_access_exception_when_calling_throwable_assert_method() throws NoSuchMethodException {
        calledMethodRegister.wasCalled(ThrowingThrowableAssert.getMethod("illegalAccess"));

        thrown.expect(IllegalStateException.class).hasMessageContaining(INVOCATION_EXCEPTION_MESSAGE);
        thrown.expectCause(IllegalAccessException.class);

        checkWithProxy.check(new AnyExc());
    }



    @Test
    public void should_rethrow_assertion_error_from_throwable_assert_method() throws Throwable {
        AssertionError expected = new AssertionError();
        calledMethodRegister.wasCalled(ThrowingThrowableAssert.getMethod("throwArgument", Throwable.class), expected);

        thrown.expect().isSameAs(expected);

        checkWithProxy.check(new AnyExc());
    }
}
