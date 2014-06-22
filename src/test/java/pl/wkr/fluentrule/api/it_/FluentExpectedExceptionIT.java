package pl.wkr.fluentrule.api.it_;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import pl.wkr.fluentrule.api.FluentExpectedException;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.api.exception_.OtherExc;
import pl.wkr.fluentrule.api.exception_.UnexpectedExc;
import pl.wkr.fluentrule.api.test_.SQLExceptionAssert;
import pl.wkr.fluentrule.api.test_.TCheckExpectedException;

import java.sql.SQLException;

import static pl.wkr.fluentrule.api.test_.FluentExpectedExceptionHelper.WAS_NOT_THROWN_MESSAGE;

public class FluentExpectedExceptionIT {

    protected TCheckExpectedException thrownOuter = TCheckExpectedException.handlingAll();
    protected FluentExpectedException thrown = FluentExpectedException.none();

    @Rule
    public RuleChain chain = RuleChain.outerRule(thrownOuter).around(thrown);


    @Test
    public void fat_example() throws Throwable {
        Throwable midChain;
        Throwable rootCause;
        Throwable cause;
        Throwable exc =
                new ExpectedExc("exc",
                    cause = new OtherExc("cause",
                            midChain = new ExpectedExc("midChain",
                                rootCause = new IllegalArgumentException("rootCause")
                            )));

        thrown.expect()
                .hasRootCauseInstanceOf(IllegalArgumentException.class)
                .hasCauseInstanceOf(OtherExc.class)
                .isSameAs(exc)
                .isNotNull()
                .hasMessage("exc")
                .hasSameClassAs(midChain)
                .isNotInstanceOf(UnexpectedExc.class);

        thrown.expectCause(OtherExc.class)
                .hasCauseInstanceOf(ExpectedExc.class)
                .hasRootCauseInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cau")
                .isIn(midChain, cause, rootCause);

        thrown.expectRootCause(IllegalArgumentException.class)
                .isNotIn(exc, cause, midChain)
                .doesNotHaveSameClassAs(cause)
                .isInstanceOf(RuntimeException.class)
                .isNotExactlyInstanceOf(RuntimeException.class)
                .hasNoCause();

        throw exc;
    }



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
        thrownOuter.expectIsSame(e);

        throw e;
    }

    @Test
    public void should_throw_assertion_that_exception_was_expected_but_not_thrown() {
        thrownOuter.expectMessageContaining(WAS_NOT_THROWN_MESSAGE);

        thrown.expect();
    }

    //------ cause and causeRoot messages

    @Test
    public void should_throw_that_exception_has_no_cause() throws UnexpectedExc {
        thrownOuter.expect(AssertionError.class).expectMessageContaining(
                "Expecting a throwable with cause, but current throwable has no cause");

        thrown.expectCause();
        throw new UnexpectedExc();
    }

    @Test
    public void should_throw_that_exception_has_no_cause_when_checking_root_cause() throws UnexpectedExc {
        thrownOuter.expect(AssertionError.class).expectMessageContaining(
                "Expecting a throwable with root cause, but current throwable has no cause");

        thrown.expectRootCause();
        throw new UnexpectedExc();
    }


    //----- extending/expectWith ------------------------------------------

    @Test
    public void should_catch_sql_exception() throws SQLException {
        thrown.expectWith(SQLExceptionAssert.class).hasMessageContaining("constraint").hasErrorCode(10).hasNoCause();
        throw new SQLException("constraint violation", "open", 10);
    }

    @Test
    public void should_throw_unexpected_type_for_expectWith_method() throws Exception {
        thrownOuter.expectAnotherClass(SQLException.class, UnexpectedExc.class);

        thrown.expectWith(SQLExceptionAssert.class);
        throw new UnexpectedExc();
    }

    @Test
    public void should_catch_cause_sql_exception() throws Exception {
        thrown.expectCauseWith(SQLExceptionAssert.class).hasCauseExactlyInstanceOf(Exception.class);
        throw new Exception(new SQLException(new Exception()));
    }

    @Test
    public void should_throw_unexpected_type_for_expectCauseWith_method() throws Exception {
        thrownOuter.expectAnotherClass(SQLException.class, UnexpectedExc.class);

        thrown.expectCauseWith(SQLExceptionAssert.class);
        throw new ExpectedExc(new UnexpectedExc());
    }


    @Test
    public void should_catch_root_cause_sql_exception() throws Exception {
        thrown.expectRootCauseWith(SQLExceptionAssert.class).hasNoCause();
        throw new Exception( new Exception(new SQLException()));
    }

    @Test
    public void should_throw_unexpected_type_for_expectRootCauseWith_method() throws Exception {
        thrownOuter.expectAnotherClass(SQLException.class, UnexpectedExc.class);

        thrown.expectRootCauseWith(SQLExceptionAssert.class);
        throw new ExpectedExc(new ExpectedExc(new UnexpectedExc()));
    }

    //----------------------------------------------------------------------------


}
