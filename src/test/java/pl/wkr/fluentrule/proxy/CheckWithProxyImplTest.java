package pl.wkr.fluentrule.proxy;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InOrder;
import pl.wkr.fluentrule.api.AssertFactory;
import pl.wkr.fluentrule.proxy.throwableassert_.ThrowableAssertMock;
import pl.wkr.fluentrule.proxy.throwableassert_.ThrowableAssertMockRegister;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;

public class CheckWithProxyImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CheckWithProxyImpl<ThrowableAssert, Throwable> checkWithProxy;
    private ThrowableAssertMock assertProxy;
    private ThrowableAssertMockRegister register = null;


    @Before
    public void before(){
        //noinspection unchecked
        checkWithProxy = new CheckWithProxyImpl(
                ThrowableAssertMock.class, Throwable.class, new AssertFactory<ThrowableAssert, Throwable>() {
            @Override
            public ThrowableAssertMock getAssert(Throwable throwable) {
                ThrowableAssertMock tam = new ThrowableAssertMock(throwable);
                register = tam.getMockRegister();
                return tam;
            }
        });

        assertProxy = (ThrowableAssertMock) checkWithProxy.getAssertProxy();
    }

    @Test
    public void should_run_methods_in_order() {
        assertProxy.hasNoCause().hasMessage("x").as("xx").isInstanceOfAny(Exception.class);
        assertProxy.descriptionText();
        assertProxy.isNull();

        checkWithProxy.check(new Exception());

        InOrder io = inOrder(register);
        io.verify(register).hasNoCause();
        io.verify(register).hasMessage("x");
        io.verify(register).as("xx");
        io.verify(register).isInstanceOfAny(Exception.class);
        io.verify(register).descriptionText();
        io.verify(register).isNull();
    }

    @Test
    public void should_return_default_values_from_methods_not_returning_self_before_exception_checking() {
        assertThat(assertProxy.descriptionText()).isEqualTo(null);
        assertThat(assertProxy.toString()).isEqualTo(null);

        //hashCode, equals - are marked final in AbstractAssert.
    }
}
