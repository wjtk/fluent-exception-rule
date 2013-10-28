package pl.wkr.fluentrule.proxy.throwing;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.wkr.fluentrule.api.AssertFactory;
import pl.wkr.fluentrule.api.testutils.MyException;
import pl.wkr.fluentrule.proxy.CheckWithProxy;
import pl.wkr.fluentrule.proxy.ProxyFactory;

import static org.hamcrest.CoreMatchers.*;

public class CheckWithProxy_InvocationsExceptionsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
    public void should_throw_runtime_exception_because_not_assertion_error() throws Exception {
        assertProxy.throwException(new MyException());

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Exception, not AssertionError when invoking method");

        checkWithProxy.check(new Exception());
    }

    @Test
    public void should_throw_illegalaccess_exception() {
        assertProxy.illegalAccess();

        thrown.expect(RuntimeException.class);
        thrown.expectCause(isA(IllegalAccessException.class));

        checkWithProxy.check(new Exception());
    }
}
