package pl.wkr.fluentrule.api;

import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestRule;
import pl.wkr.fluentrule.api.testutils.AbstractExceptionsTest;
import pl.wkr.fluentrule.api.testutils.MyException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        thrownOuter.expectMessage("yo");
        thrownOuter.expectMessage("bug");

        thrown.expect(Exception.class).hasMessage("x");
        thrown.expectCause().hasMessageContaining("bug");
        throw new Exception("x", new Exception("yo"));
    }

    //---------------------------------------------------------------

    @Test
    public void should_all_exceptXXX_methods_return_not_null() {
        FluentExpectedException fluentRule = new FluentExpectedException();
        assertThat(fluentRule.expect()).as("expect()").isNotNull();
        assertThat(fluentRule.expect(MyException.class)).as("expect(Class)").isNotNull();
        assertThat(fluentRule.expectAny(MyException.class)).as("expectAny(Class...)").isNotNull();
        assertThat(fluentRule.expectCause()).as("expectCause()").isNotNull();
    }


    //----------------------------
}


