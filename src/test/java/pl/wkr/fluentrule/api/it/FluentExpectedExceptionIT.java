package pl.wkr.fluentrule.api.it;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.RuleChain;
import pl.wkr.fluentrule.api.Check;
import pl.wkr.fluentrule.api.CheckExpectedException;
import pl.wkr.fluentrule.api.FluentExpectedException;
import pl.wkr.fluentrule.api.testutils.SQLExceptionAssert;
import pl.wkr.fluentrule.api.testutils.MyException;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.wkr.fluentrule.api.testutils.FluentExpectedExceptionTestConstants.WAS_NOT_THROWN_MESSAGE;

public class FluentExpectedExceptionIT {

    public CheckExpectedException thrownOuter = CheckExpectedException.none().handleAssertionErrors().handleAssumptionViolatedExceptions();
    public FluentExpectedException thrown = FluentExpectedException.none();

    @Rule
    public RuleChain chain = RuleChain.outerRule(thrownOuter).around(thrown);


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
        outerExpectMessageContaining("a6b7");
        //thrown - nothing
        throw new Exception("a6b7");
    }

    @Test
    public void should_throw_assertion_that_exception_was_expected_but_not_thrown() {
        outerExpectMessageContaining("Exception was expected but was not thrown");
        thrown.expect();
    }

    @Test
    public void should_not_catch_assertion_when_not_handle_assertions() {
        outerExpect(AssertionError.class);
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
        outerExpect(AssumptionViolatedException.class);
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
    public void should_throw_unexpected_type_for_expectAny_method() throws Exception {
        outerExpect(AssertionError.class);
        outerExpectMessageContaining(Long.class.getName(), Number.class.getName(), Exception.class.getName());

        thrown.expectAny(Long.class, Number.class);
        throw new Exception();
    }

    @Test
    public void should_throw_unexpected_type_for_expect_method() throws Exception {
        outerExpect(AssertionError.class);
        outerExpectMessageContaining(SQLException.class.getName(), Exception.class.getName());

        thrown.expect(SQLException.class);
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
        outerExpect(AssertionError.class);
        outerExpectMessageContaining("yo", "bug");

        thrown.expect(Exception.class).hasMessage("x");
        thrown.expectCause().hasMessageContaining("bug");
        throw new Exception("x", new Exception("yo"));
    }

    //----- extending/assertWith ------------------------------------------

    @Test
    public void should_throw_that_exception_was_not_thrown_only_for_lone_call_of_assertWith() {
        outerExpect(AssertionError.class);
        outerExpectMessageContaining(WAS_NOT_THROWN_MESSAGE);

        thrown.assertWith(SQLExceptionAssert.class);
    }

    @Test
    public void should_catch_sql_exception() throws SQLException {
        thrown.assertWith(SQLExceptionAssert.class).hasMessageContaining("zzz").hasNoCause().hasErrorCode(10);
        throw new SQLException("zzz", "open", 10);
    }

    @Test
    public void should_throw_unexpected_type_for_assertWith_method() throws Exception {
        outerExpect(AssertionError.class);
        outerExpectMessageContaining(SQLException.class.getName(), Exception.class.getName());

        thrown.assertWith(SQLExceptionAssert.class);
        throw new Exception();
    }

    //----------------------------------------------------------------------------

    private void outerExpect(final Class<?> type) {
        thrownOuter.check(new Check() {
            @Override
            public void check(Throwable exception) {
                assertThat(exception).isInstanceOf(type);
            }
        });
    }

    private  void outerExpectMessageContaining(final String ... messages) {
        thrownOuter.check(new Check(){
            @Override
            public void check(Throwable exception) {
                ThrowableAssert throwableAssert = assertThat(exception);
                for(String message : messages) {
                    throwableAssert.hasMessageContaining(message);
                }
            }
        });
    }


}


