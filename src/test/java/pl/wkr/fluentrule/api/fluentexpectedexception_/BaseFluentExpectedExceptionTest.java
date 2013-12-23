package pl.wkr.fluentrule.api.fluentexpectedexception_;

import org.assertj.core.api.ThrowableAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.runners.model.Statement;
import pl.wkr.fluentrule.api.AssertFactory;
import pl.wkr.fluentrule.api.FluentExpectedException;
import pl.wkr.fluentrule.api.FluentExpectedExceptionBuilder;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.proxy.CheckWithProxy;
import pl.wkr.fluentrule.proxy.ProxyFactory;
import pl.wkr.fluentrule.proxy.throwableassert_.ThrowableAssertMock;
import pl.wkr.fluentrule.proxy.throwableassert_.ThrowableAssertMockRegister;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pl.wkr.fluentrule.api.rule_.StatementHelper.evaluateGetException;

public abstract class BaseFluentExpectedExceptionTest {

    protected ThrowableAssertMockRegister register;
    protected AssertFactory<ThrowableAssert,Throwable> throwableAssertFactoryMock;

    private ThrowableAssert returnedProxy = null;
    private FluentExpectedException fluentRule;
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
        ProxyFactory proxyFactory = mock(ProxyFactory.class);
        throwableAssertFactoryMock = mock(AssertFactory.class);
        checkWithProxyMock = mock(CheckWithProxy.class);
        throwableAssertMock = new ThrowableAssertMock();
        register = throwableAssertMock.getMockRegister();

        when(proxyFactory.newCheckWithProxy(ThrowableAssert.class, Throwable.class, throwableAssertFactoryMock)).thenReturn(checkWithProxyMock);
        when(checkWithProxyMock.getAssertProxy()).thenReturn(throwableAssertMock);

        fluentRule = before_createRule(new FluentExpectedExceptionBuilder().withProxyFactory(proxyFactory));
    }

    @After
    public void after_BaseFluentExpectedExceptionTest() {
        after_assertThatReturnedThrowableAssertIsSameAsThrowableAssertMock();
        after_assertThatStatementExecutionShoulCall_CheckWithProxy_Check();
    }

    //---------------------------------------

    protected void after_assertThatReturnedThrowableAssertIsSameAsThrowableAssertMock() {
        assertThat(returnedProxy).as("returnedProxy").isSameAs(throwableAssertMock);
    }

    protected void after_assertThatStatementExecutionShoulCall_CheckWithProxy_Check() {
        //noinspection ThrowableResultOfMethodCallIgnored
        evaluateGetException(fluentRule.apply(statement, null));

        verify(checkWithProxyMock).check(exceptionFromStatement);
    }

    //----------------------------------

    protected void setReturnedThrowableAssert(ThrowableAssert returnedProxy) {
        this.returnedProxy = returnedProxy;
    }

    protected FluentExpectedException getFluentRule() {
        return fluentRule;
    }

    protected abstract FluentExpectedException before_createRule(FluentExpectedExceptionBuilder builder);
}
