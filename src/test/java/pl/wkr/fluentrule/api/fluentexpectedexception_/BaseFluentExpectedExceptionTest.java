package pl.wkr.fluentrule.api.fluentexpectedexception_;

import org.assertj.core.api.ThrowableAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.runners.model.Statement;
import pl.wkr.fluentrule.api.AssertFactory;
import pl.wkr.fluentrule.api.FluentExpectedException;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.proxy.CheckWithProxy;
import pl.wkr.fluentrule.proxy.ProxyFactory;
import pl.wkr.fluentrule.proxy.throwableassert_.ThrowableAssertMock;
import pl.wkr.fluentrule.proxy.throwableassert_.ThrowableAssertMockRegister;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pl.wkr.fluentrule.api.rule_.StatementHelper.evaluateGetException;

public abstract class BaseFluentExpectedExceptionTest {

    protected FluentExpectedException fluentRule;
    protected ThrowableAssertMockRegister register;
    protected AssertFactory<ThrowableAssert,Throwable> throwableAssertFactoryMock;
    protected ProxyFactory proxyFactory;
    protected ThrowableAssert returnedProxy = null;

    private ThrowableAssertMock throwableAssertMock;
    private CheckWithProxy<ThrowableAssert, Throwable> checkWithProxyMock;
    private ExpectedExc exceptionFromStatement = new ExpectedExc();
    private Statement statement = new Statement() {
        @Override
        public void evaluate() throws Throwable {
            throw exceptionFromStatement;
        }
    };

    @Before
    @SuppressWarnings("unchecked")
    public final void before_BaseFluentExpectedExceptionTest() {
        proxyFactory = mock(ProxyFactory.class);
        throwableAssertFactoryMock = mock(AssertFactory.class);
        checkWithProxyMock = mock(CheckWithProxy.class);
        throwableAssertMock = new ThrowableAssertMock();
        register = throwableAssertMock.getMockRegister();

        when(proxyFactory.newCheckWithProxy(ThrowableAssert.class, Throwable.class, throwableAssertFactoryMock)).thenReturn(checkWithProxyMock);
        when(checkWithProxyMock.getAssertProxy()).thenReturn(throwableAssertMock);
    }

    @After
    public void should_return_throwableAssertMock_as_proxy_AND_executing_statement_should_call__CheckWithProxy_check() {
        assertThat(returnedProxy).as("returnedProxy").isSameAs(throwableAssertMock);

        //noinspection ThrowableResultOfMethodCallIgnored
        evaluateGetException(fluentRule.apply(statement, null));

        verify(checkWithProxyMock).check(exceptionFromStatement);
    }


}
