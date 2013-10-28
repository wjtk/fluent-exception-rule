package pl.wkr.fluentrule.api.extending;

import org.junit.Test;
import org.junit.rules.TestRule;
import pl.wkr.fluentrule.api.testutils.AbstractExceptionsTest;
import pl.wkr.fluentrule.api.extending.MyExtendedFluentExpectedException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ExtendingTest extends AbstractExceptionsTest {

    public MyExtendedFluentExpectedException thrown;

    @Override
    protected List<TestRule> getRulesToWrap() {
        thrown = new MyExtendedFluentExpectedException();
        return Arrays.<TestRule>asList(thrown);
    }

    @Test
    public void should_catch_exception() throws Exception {
        thrown.expect().isInstanceOf(Exception.class).hasMessageContaining("x").hasNoCause();
        throw new Exception("x");
    }

    @Test
    public void should_catch_sql_exception() throws SQLException {
        thrown.expectSQLException().hasMessageContaining("zzz").hasNoCause().hasErrorCode(10);
        throw new SQLException("zzz", "open", 10);
    }

    @Test
    public void should_not_catch_unexpected_sql_error_code() throws SQLException {

        thrownOuter.expect(AssertionError.class);
        thrownOuter.expectMessage("<10>");
        thrownOuter.expectMessage("<11>");

        thrown.expectSQLException().hasErrorCode(11);
        throw new SQLException("reason", "state",10);
    }

    @Test
    public void should_not_catch_because_of_unexpected_type() throws Exception {

        thrownOuter.expect(AssertionError.class);
        thrownOuter.expectMessage("to be an instance");
        thrownOuter.expectMessage(SQLException.class.getName());
        thrownOuter.expectMessage(Exception.class.getName());

        thrown.expectSQLException().hasMessageContaining("xyz");
        throw new Exception("xyz");
    }





}
