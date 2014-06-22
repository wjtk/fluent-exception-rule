package pl.wkr.fluentrule.proxy;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InOrder;
import pl.wkr.fluentrule.assertfactory.AssertFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

public class CheckWithProxyImpl_Test {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CheckWithProxyImpl<ThrowableAssert, Throwable> checkWithProxy;
    private ThrowableAssert throwableAssertMock;
    private ThrowableAssert assertProxy;


    @Before
    public void before(){
        throwableAssertMock = mock(ThrowableAssert.class);
        checkWithProxy = new CheckWithProxyImpl<ThrowableAssert, Throwable>(
                ThrowableAssert.class,
                Throwable.class,
                new AssertFactory<ThrowableAssert, Throwable>() {
                    @Override
                    public ThrowableAssert getAssert(Throwable throwable) {
                        return throwableAssertMock;
                    }
                },
                new RunLaterCallbackFactory()
        );

        assertProxy = checkWithProxy.getAssertProxy();
    }

    @Test
    public void should_run_methods_in_order() {
        assertProxy.hasNoCause().hasMessage("x").as("xx").isInstanceOfAny(Exception.class);
        assertProxy.descriptionText();
        assertProxy.isNull();

        checkWithProxy.check(new Exception());

        InOrder io = inOrder(throwableAssertMock);
        io.verify(throwableAssertMock).hasNoCause();
        io.verify(throwableAssertMock).hasMessage("x");
        io.verify(throwableAssertMock).as("xx");
        io.verify(throwableAssertMock).isInstanceOfAny(Exception.class);
        io.verify(throwableAssertMock).descriptionText();
        io.verify(throwableAssertMock).isNull();
    }

    @Test
    public void should_return_default_values_from_methods_not_returning_self_before_exception_checking() {
        assertThat(assertProxy.descriptionText()).isEqualTo(null);
        assertThat(assertProxy.toString()).isEqualTo(null);
        assertThat(assertProxy.hashCode()).isEqualTo(0);
    }
}
