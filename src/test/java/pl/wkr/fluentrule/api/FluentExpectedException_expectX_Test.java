package pl.wkr.fluentrule.api;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.ThrowableAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.Statement;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.api.test_.SQLExceptionAssert;
import pl.wkr.fluentrule.assertfactory.ProxiesFactory;
import pl.wkr.fluentrule.proxy.CheckWithProxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static pl.wkr.fluentrule.api.rule_.StatementHelper.evaluateGetException;

public class FluentExpectedException_expectX_Test {

    private FluentExpectedException fluentRule;

    private ThrowableAssert assertProxyMock;
    private AbstractThrowableAssert<?,?> returnedProxy = null;

    private CheckWithProxy<ThrowableAssert, Throwable> checkWithProxyMock;
    private ProxiesFactory proxiesFactoryMock;

    private ExpectedExc exceptionFromStatement = new ExpectedExc();
    private Statement throwingStatement = new Statement() {
        @Override
        public void evaluate() throws Throwable {
            throw exceptionFromStatement;
        }
    };


    @SuppressWarnings("unchecked")
    @Before
    public void before() {
        assertProxyMock = mock(ThrowableAssert.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getMock();
            }
        });

        checkWithProxyMock = mock(CheckWithProxy.class);

        proxiesFactoryMock = mock(ProxiesFactory.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return checkWithProxyMock; //default answer
            }
        });

        when(checkWithProxyMock.getAssertProxy()).thenReturn(assertProxyMock);
        fluentRule = new FluentExpectedException(proxiesFactoryMock);
    }

    @After
    public void after() {
        after_assertThatReturnedThrowableAssertIsSameAsThrowableAssertMock();
        after_assertThatStatementExecutionShoulCall_CheckWithProxy_Check();
        after_assertNoMoreInteractionsWithProxiesFactoryAndReturnedProxy();
    }

    private void after_assertNoMoreInteractionsWithProxiesFactoryAndReturnedProxy() {
        verifyNoMoreInteractions(proxiesFactoryMock, assertProxyMock);
    }

    private void after_assertThatReturnedThrowableAssertIsSameAsThrowableAssertMock() {
        assertThat(returnedProxy).as("returnedProxy").isSameAs(assertProxyMock);
    }

    private void after_assertThatStatementExecutionShoulCall_CheckWithProxy_Check() {
        //noinspection ThrowableResultOfMethodCallIgnored
        evaluateGetException(fluentRule.apply(throwingStatement, null));

        verify(checkWithProxyMock).check(exceptionFromStatement);
    }


    @Test
    public void should_call_dependencies_expect() {
        returnedProxy = fluentRule.expect();

        verify(proxiesFactoryMock).newThrowableAssertProxy();
    }

    @Test
    public void should_call_dependencies_expect_class() {
        returnedProxy = fluentRule.expect(ExpectedExc.class);

        verify(proxiesFactoryMock).newThrowableAssertProxy();
        verify(assertProxyMock).isInstanceOf(ExpectedExc.class);
    }

    @Test
    public void should_call_dependencies_expectWith() {
        returnedProxy = fluentRule.expectWith(SQLExceptionAssert.class);

        verify(proxiesFactoryMock).newThrowableCustomAssertProxy(SQLExceptionAssert.class);        
    }

    @Test
    public void should_call_dependencies_expectCause() {
        returnedProxy = fluentRule.expectCause();

        verify(proxiesFactoryMock).newThrowableCauseAssertProxy();
    }

    @Test
    public void should_call_dependencies_expectCause_class() {
        returnedProxy = fluentRule.expectCause(ExpectedExc.class);

        verify(proxiesFactoryMock).newThrowableCauseAssertProxy();
        verify(assertProxyMock).isInstanceOf(ExpectedExc.class);
    }

    @Test
    public void should_call_dependencies_expectCauseWith() {
        returnedProxy = fluentRule.expectCauseWith(SQLExceptionAssert.class);

        verify(proxiesFactoryMock).newThrowableCauseCustomAssertProxy(SQLExceptionAssert.class);
    }

    @Test
    public void should_call_dependencies_expectRootCause() {
        returnedProxy = fluentRule.expectRootCause();

        verify(proxiesFactoryMock).newThrowableRootCauseAssertProxy();
    }

    @Test
    public void should_call_dependencies_expectRootCause_class() {
        returnedProxy = fluentRule.expectRootCause(ExpectedExc.class);

        verify(proxiesFactoryMock).newThrowableRootCauseAssertProxy();
        verify(assertProxyMock).isInstanceOf(ExpectedExc.class);
    }

    @Test
    public void should_call_dependencies_expectRootCauseWith() {
        returnedProxy = fluentRule.expectRootCauseWith(SQLExceptionAssert.class);

        verify(proxiesFactoryMock).newThrowableRootCauseCustomAssertProxy(SQLExceptionAssert.class);
    }
}
