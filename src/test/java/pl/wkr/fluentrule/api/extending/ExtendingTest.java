package pl.wkr.fluentrule.api.extending;

import org.assertj.core.api.Condition;
import org.junit.Test;
import org.junit.rules.TestRule;
import pl.wkr.fluentrule.api.testutils.AbstractExceptionsTest;
import pl.wkr.fluentrule.api.testutils.MyUnexceptedException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ExtendingTest extends AbstractExceptionsTest{

    public MyFluentExpectedException thrown;

    @Override
    protected List<TestRule> getRulesToWrap() {
        thrown = new MyFluentExpectedException();
        return Arrays.<TestRule>asList(thrown);
    }

    @Test
    public void should_catch_sql_exception() throws SQLException {
        thrown.expectSQLException().hasErrorCode(10);
        throw new SQLException("","",10);
    }

    @Test
    public void should_discover_that_exception_is_of_unexpected_type() throws Exception {
        thrownOuter.expect(AssertionError.class);
        thrownOuter.expectMessage(MyUnexceptedException.class.getName());
        thrownOuter.expectMessage(SQLException.class.getName());

        thrown.expectSQLException().hasErrorCode(10);
        throw new MyUnexceptedException();
    }

    @Test
    public void will_has_condition_be_enough_for_custom_assertions() throws SQLException {
        thrown.expect().has(new ErrorCodeIsEqualTo(10));
        throw new SQLException("a", "a", 10);
    }

    //rather poor idea..., but alternative is to write 2 Assert classes.
    class ErrorCodeIsEqualTo extends Condition<Throwable> {

        private int code;

        public ErrorCodeIsEqualTo(int code) {
            this.code = code;
        }

        @Override
        public boolean matches(Throwable e) {
            assertThat(e).isInstanceOf(SQLException.class);
            int was = ((SQLException)e).getErrorCode();
            this.describedAs("error code equal to <%d>, was <%d>", code, was);
            return  was == code;
        }
    }



    @Test
    public void check_interface_poc() {
        new SQLExceptionCheck() {
            @Override
            public void check(SQLException throwable) {
                assertThat(throwable.getErrorCode()).isEqualTo(10);
            }
        };
    }

    abstract class SQLExceptionCheck extends Check<SQLException> {}
    abstract class ThrowableCheck extends Check<Throwable> {}

    abstract class Check<T extends Throwable> {
        //Check może wykryć typ, który dostarczy klasa pochodna i sam przerzutować.
        abstract public void check(T throwable);
    }
}
