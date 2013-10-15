package pl.wkr.fluentrule.api;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestRule;
import org.junit.runners.model.Statement;
import org.mockito.InOrder;
import pl.wkr.fluentrule.api.testutils.AbstractExceptionsTest;
import pl.wkr.fluentrule.api.testutils.MyException;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static pl.wkr.fluentrule.api.testutils.StatementHelper.evaluateGetException;

public class FluentExpectedExceptionTest extends AbstractExceptionsTest {

    public FluentExpectedException thrown;

    @Override
    protected List<TestRule> getRulesToWrap() {
        thrown = new FluentExpectedException();
        return Arrays.<TestRule>asList(thrown);
    }

    @Test
    public void should_do_nothing_because_nothing_is_expected() {
        //nothing
    }

    @Test
    public void should_catch_any_exception() throws Exception {
        thrown.expect();
        throw new Exception();
    }

    @Test
    public void should_catch_exception_of_expected_type() throws Exception {
        thrown.expect(MyException.class);
        throw new MyException();
    }


    @Test
    public void should_not_catch_exception_because_nothing_is_expected() throws Exception {
        thrownOuter.expectMessage("a6b7");
        //thrown - nothing
        throw new Exception("a6b7");
    }

    @Test
    public void should_throw_assertion_that_exception_was_expected_but_not_thrown() {
        thrownOuter.expectMessage("Exception was expected but was not thrown");
        thrown.expect();
    }

    @Test
    public void should_not_catch_assertion_when_not_handle_assertions() {
        thrownOuter.expect(AssertionError.class);
        thrown.expect();
        throw new AssertionError();
    }

    @Test
    public void should_catch_assertion_when_it_is_explicitly_enabled() {
        thrown.handleAssertionErrors().expect();
        throw new AssertionError();
    }

    @Test
    public void should_not_catch_assumption_when_not_handle_assumptions() {
        thrownOuter.expect(AssumptionViolatedException.class);
        thrown.expect();
        throw new AssumptionViolatedException("a");
    }

    @Test
    public void should_catch_assumption_when_it_is_explicitly_enabled() {
        thrown.handleAssumptionViolatedExceptions().expect();
        throw new AssumptionViolatedException("a");
    }

    // methods -------------------------------------------------

    @Test
    public void should_throw_unexpected_type() throws Exception {
        thrownOuter.expect(AssertionError.class);

        thrown.expectAny(Long.class, Number.class);
        throw new Exception();
    }

    @Test
    public void should_catch_exception_by_message() throws Exception {
        thrown.expect().hasMessage("z");
        throw new Exception("z");
    }

    @Test
    public void should_catch_exception_by_cause_message() throws Exception {
        thrown.expectCause().hasMessage("z");
        throw new Exception(new Exception("z"));
    }

    @Test
    public void should_throw_because_second_requirement_is_not_fulfilled() throws Exception {
        thrownOuter.expect(AssertionError.class);

        thrown.expect(Exception.class).hasMessage("x");
        thrown.expectCause().hasMessageContaining("bug");
        throw new Exception("x", new Exception("yo"));
    }

    //---------------------------------------------------------------

    @Test
    public void should_all_except_methods_return_not_null() {
        FluentExpectedException fluentRule = new FluentExpectedException();
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fluentRule.expect()).as("expect()").isNotNull();
        soft.assertThat(fluentRule.expect(MyException.class)).as("expect(Class)").isNotNull();
        soft.assertThat(fluentRule.expectAny(MyException.class)).as("expectAny(Class...)").isNotNull();
        soft.assertThat(fluentRule.expectCause()).as("expectCause()").isNotNull();
        soft.assertAll();
    }

    @Test
    public void should_push_exception_to_all_AssertCommandLists_in_order() throws Throwable {
        FluentExpectedException fluentExpectedException = new FluentExpectedException();
        Exception exception = new Exception();
        AssertCommandList<?,?> acl1 = mock(AssertCommandList.class);
        AssertCommandList<?,?> acl2 = mock(AssertCommandList.class);
        Statement statement = mock(Statement.class);
        doThrow(exception).when(statement).evaluate();

        fluentExpectedException.getAssertCommandListCollector().add(acl1);
        fluentExpectedException.getAssertCommandListCollector().add(acl2);

        evaluateGetException(fluentExpectedException.apply(statement, null));

        InOrder io = inOrder(acl1, acl2);
        io.verify(acl1).check(exception);
        io.verify(acl2).check(exception);
    }
}


