package pl.wkr.fluentrule.api.it_;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import pl.wkr.fluentrule.api.Check;
import pl.wkr.fluentrule.api.CheckExpectedException;
import pl.wkr.fluentrule.api.FluentExpectedException;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.api.exception_.OtherExc;
import pl.wkr.fluentrule.api.exception_.UnexpectedExc;
import pl.wkr.fluentrule.api.test_.SQLExceptionAssert;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class FluentExpectedExceptionIT {

    public CheckExpectedException thrownOuter = CheckExpectedException.none().handleAssertionErrors().handleAssumptionViolatedExceptions();
    public FluentExpectedException thrown = FluentExpectedException.none();

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
        outerExpectIsSame(e);

        throw e;
    }

    @Test
    public void should_throw_assertion_that_exception_was_expected_but_not_thrown() {
        outerExpectMessageContaining("Exception was expected but was not thrown");

        thrown.expect();
    }


    //----- extending/assertWith ------------------------------------------

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
