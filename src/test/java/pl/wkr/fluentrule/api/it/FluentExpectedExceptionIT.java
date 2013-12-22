package pl.wkr.fluentrule.api.it;


import org.assertj.core.api.ThrowableAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.RuleChain;
import pl.wkr.fluentrule.api.Check;
import pl.wkr.fluentrule.api.CheckExpectedException;
import pl.wkr.fluentrule.api.FluentExpectedException;
import pl.wkr.fluentrule.api.testutils.ExpectedExc;
import pl.wkr.fluentrule.api.testutils.OtherExc;
import pl.wkr.fluentrule.api.testutils.SQLExceptionAssert;
import pl.wkr.fluentrule.api.testutils.UnexpectedExc;

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
        throw new ExpectedExc();
    }

    @Test
    public void should_catch_exception_of_expected_type() throws Exception {
        thrown.expect(ExpectedExc.class);
        throw new ExpectedExc();
    }

    @Test
    public void should_not_catch_exception_because_nothing_is_expected() throws Exception {
        Exception e = new UnexpectedExc();
        outerExpectIsSame(e);

        throw e;
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
        outerExpectMessageContaining(UnexpectedExc.class.getName(), ExpectedExc.class.getName());

        thrown.expect(ExpectedExc.class);
        throw new UnexpectedExc();
    }

    @Test
    public void should_catch_exception_by_message() throws Exception {
        thrown.expect().hasMessage("z");
        throw new ExpectedExc("z");
    }

    // expectCause ------------------------------------------

    @Test
    public void should_catch_exception__expectCause() throws Exception {
        Exception expected;
        Exception toThrow = new OtherExc(expected = new ExpectedExc(new UnexpectedExc()));

        thrown.expectCause().isSameAs(expected);
        throw toThrow;
    }

    @Test
    public void should_throw_because_exception_has_no_cause__expectCause() throws Exception {
        outerExpectMessageContaining("but current throwable has no cause");

        thrown.expectCause();
        throw new UnexpectedExc();
    }

    @Test
    public void should_catch_exception__expectCause_Class() throws Exception {
        Exception toCompare;
        Exception toThrow = new OtherExc(toCompare = new ExpectedExc(new UnexpectedExc()));

        thrown.expectCause(ExpectedExc.class).isSameAs(toCompare);
        throw toThrow;
    }

    @Test
    public void should_throw_because_exception_has_no_cause__expectCause_Class() throws Exception {
        outerExpectMessageContaining("but current throwable has no cause");

        thrown.expectCause(ExpectedExc.class);
        throw new UnexpectedExc();
    }

    @Test
    public void should_throw_because_unexpected_cause_type__expectCause_Class() throws Exception {
        outerExpectMessageContaining(ExpectedExc.class.getName(), UnexpectedExc.class.getName());

        thrown.expectCause(ExpectedExc.class);
        throw new OtherExc(new UnexpectedExc(new OtherExc()));
    }


    //----------- expectRootCause -----------------------------------------------

    @Test
    public void should_catch_rootCause() throws Exception {
        Exception expected;
        Exception toThrow = new UnexpectedExc(new UnexpectedExc(expected = new ExpectedExc()));

        thrown.expectRootCause().isSameAs(expected);
        throw toThrow;
    }

    @Test
    public void should_throw_because_has_no_cause_rootCause() throws Exception {
        outerExpectMessageContaining("but current throwable has no cause");

        thrown.expectRootCause();
        throw new UnexpectedExc();
    }

    @Test
    public void should_catch_rootCause_Class() throws Exception {
        Exception expected;
        Exception toThrow = new UnexpectedExc(new UnexpectedExc(expected = new ExpectedExc()));

        thrown.expectRootCause(ExpectedExc.class).isSameAs(expected);
        throw toThrow;
    }

    @Test
    public void should_throw_because_has_no_cause_rootCause_Class() throws UnexpectedExc {
        outerExpectMessageContaining("but current throwable has no cause");

        thrown.expectRootCause(ExpectedExc.class);
        throw new UnexpectedExc();
    }

    @Test
    public void should_throw_because_unexpected_rootCause_type__rootCause_Class() throws Exception {
        outerExpectMessageContaining(ExpectedExc.class.getName(), UnexpectedExc.class.getName());

        thrown.expectRootCause(ExpectedExc.class);
        throw new ExpectedExc(new ExpectedExc(new UnexpectedExc()));
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
        thrown.assertWith(SQLExceptionAssert.class).hasMessageContaining("constraint").hasErrorCode(10).hasNoCause();
        throw new SQLException("constraint violation", "open", 10);
    }

    @Test
    public void should_throw_unexpected_type_for_assertWith_method() throws Exception {
        outerExpect(AssertionError.class);
        outerExpectMessageContaining(SQLException.class.getName(), UnexpectedExc.class.getName());

        thrown.assertWith(SQLExceptionAssert.class);
        throw new UnexpectedExc();
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

    private void outerExpectIsSame(final Throwable expected) {
        thrownOuter.check(new Check() {
            @Override
            public void check(Throwable exception) {
                assertThat(exception).isSameAs(expected);
            }
        });
    }
}


