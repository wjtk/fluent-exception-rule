package pl.wkr.fluentrule.api;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.model.Statement;
import org.mockito.Matchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.wkr.fluentrule.api.rule_.StatementHelper.evaluateGetException;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class AbstractHandleExceptionRule_Test {

    private RuleRegister mockRuleRegister = mock(RuleRegister.class);
    private Statement statement = mock(Statement.class);
    private TestRule rule = new TestRule(mockRuleRegister);
    private Exception exception = new Exception();
    private AssertionError assertionExc = new AssertionError();
    private AssumptionViolatedException assumeExc = new AssumptionViolatedException("z");
    private boolean checkNeverCallFailBecause = true;

    @Before
    public void before() {
        when(mockRuleRegister.isExceptionExpected()).thenReturn(true);
    }

    @After
    public void after() {
        if(checkNeverCallFailBecause) verify(mockRuleRegister,never()).failBecauseExceptionWasNotThrown();
    }

    @Test
    public void should_always_evaluate_statement() throws Throwable {
        rule.apply(statement, null).evaluate();
        verify(statement).evaluate();
        reset(statement);

        rule.handleAssertionErrors().apply(statement, null).evaluate();
        verify(statement).evaluate();
        reset(statement);

        rule.handleAssumptionViolatedExceptions().apply(statement,null).evaluate();
        verify(statement).evaluate();

        checkNeverCallFailBecause = false;
    }


    @Test
    public void should_not_call_handleException_when_no_exception_is_present_and_not_expecting() {
        when(mockRuleRegister.isExceptionExpected()).thenReturn(false);

        evaluateGetException(rule.apply(statement, null));

        verify(mockRuleRegister, never()).handleException(Matchers.<Throwable>any());
    }

    @Test
    public void should_not_call_handleException_when_not_expecting_exception_even_if_exception_present() throws Throwable {
        doThrow(exception).when(statement).evaluate();
        when(mockRuleRegister.isExceptionExpected()).thenReturn(false);

        evaluateGetException(rule.apply(statement, null));

        verify(mockRuleRegister, never()).handleException(exception);
    }

    @Test
    public void should_call_handleException_when_expecting_exception_and_exception_present() throws Throwable {
        doThrow(exception).when(statement).evaluate();

        evaluateGetException(rule.apply(statement, null));

        verify(mockRuleRegister).handleException(exception);
    }

    @Test
    public void should_call_failBecauseExceptionWasNotThrown_when_exception_is_expected_but_not_present() {

        evaluateGetException(rule.apply(statement, null));

        verify(mockRuleRegister).failBecauseExceptionWasNotThrown();
        checkNeverCallFailBecause = false;
    }


    @Test
    public void should_call_handleException_when_assertionException_is_present_with_handleAssertions() throws Throwable {
        rule.handleAssertionErrors();
        doThrow(assertionExc).when(statement).evaluate();

        evaluateGetException(rule.apply(statement, null));

        verify(mockRuleRegister).handleException(assertionExc);
    }

    @Test
    public void should_not_call_handleException_when_assertionException_is_present_without_handleAssertions() throws Throwable {
        doThrow(assertionExc).when(statement).evaluate();

        evaluateGetException(rule.apply(statement, null));

        verify(mockRuleRegister,never()).handleException(assertionExc);
    }

    @Test
    public void should_call_handleException_when_assumeException_is_present_with_handleAssumptions() throws Throwable {
        rule.handleAssumptionViolatedExceptions();
        doThrow(assumeExc).when(statement).evaluate();

        evaluateGetException(rule.apply(statement, null));

        verify(mockRuleRegister).handleException(assumeExc);
    }

    @Test
    public void should_not_call_handleException_when_assumeException_is_present_without_handleAssumptions() throws Throwable {
        doThrow(assumeExc).when(statement).evaluate();

        evaluateGetException(rule.apply(statement, null));

        verify(mockRuleRegister, never()).handleException(assumeExc);
    }
    
    @Test
    public void should_exception_thrown_in_handleException_leap_out_created_statement() throws Throwable {
        doThrow(exception).when(statement).evaluate();
        doThrow(assertionExc).when(mockRuleRegister).handleException(exception);

        Throwable excLeapOut = evaluateGetException(rule.apply(statement, null));

        assertThat(excLeapOut).isEqualTo(assertionExc);
    }

    @Test
    public void should_exception_thrown_in_failBecauseExceptionWasNotThrown_leap_out_created_statemant() {
        //expected exception(always), but not throw
        doThrow(assertionExc).when(mockRuleRegister).failBecauseExceptionWasNotThrown();

        Throwable excLeapOut = evaluateGetException(rule.apply(statement, null));

        assertThat(excLeapOut).isEqualTo(assertionExc);
        checkNeverCallFailBecause = false;
    }

    //-------------------------- inner classes --------------------------

    static class TestRule extends AbstractHandleExceptionRule<TestRule> {

        private RuleRegister register;

        public TestRule(RuleRegister register) {
            this.register = register;
        }

        @Override
        protected boolean isExceptionExpected() {
            return register.isExceptionExpected();
        }

        @Override
        protected void failBecauseExceptionWasNotThrown() {
            register.failBecauseExceptionWasNotThrown();
        }

        @Override
        protected void handleException(Throwable e) {
            register.handleException(e);
        }
    }

    public interface RuleRegister {
        boolean isExceptionExpected();
        void failBecauseExceptionWasNotThrown();
        void handleException(Throwable e);
    }

}








