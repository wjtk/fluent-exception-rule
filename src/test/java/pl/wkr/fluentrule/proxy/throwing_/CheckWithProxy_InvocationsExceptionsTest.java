package pl.wkr.fluentrule.proxy.throwing_;

import org.junit.Before;
import org.junit.Test;
import pl.wkr.fluentrule.api.AssertFactory;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.api.test_.BaseWithFluentThrownTest;
import pl.wkr.fluentrule.proxy.CheckWithProxy;
import pl.wkr.fluentrule.proxy.ProxyFactory;

public class CheckWithProxy_InvocationsExceptionsTest extends BaseWithFluentThrownTest{


    private String INVOCATION_EXCEPTION_MESSAGE = "Exception, not AssertionError when invoking method";
    private CheckWithProxy<ThrowingThrowableAssert, Throwable> checkWithProxy;
    private ThrowingThrowableAssert assertProxy;

    @Before
    public void before() {
        checkWithProxy = new ProxyFactory().newCheckWithProxy(ThrowingThrowableAssert.class, Throwable.class,
                new AssertFactory<ThrowingThrowableAssert, Throwable>() {
                    @Override
                    public ThrowingThrowableAssert getAssert(Throwable throwable) {
                        return new ThrowingThrowableAssert(throwable);
                    }
                });

        assertProxy = checkWithProxy.getAssertProxy();
    }

    @Test
    public void should_throw_runtime_exception_because_not_assertion_error() throws Throwable {
        Throwable expected;
        assertProxy.throwThis(expected = new ExpectedExc());

        thrown.expect(RuntimeException.class).hasMessageContaining(INVOCATION_EXCEPTION_MESSAGE);
        thrown.expectCause().isSameAs(expected);

        checkWithProxy.check(new Exception());
    }

    @Test
    public void should_throw_illegalaccess_exception() {
        assertProxy.illegalAccess();

        thrown.expect(RuntimeException.class).hasMessageContaining(INVOCATION_EXCEPTION_MESSAGE);
        thrown.expect(RuntimeException.class);

        checkWithProxy.check(new Exception());
    }

    @Test
    public void should_rethrow_assertion_error() throws Throwable {
        AssertionError expected = new AssertionError();
        assertProxy.throwThis(expected);

        thrown.expect().isSameAs(expected);

        checkWithProxy.check(new Exception());
    }
}
