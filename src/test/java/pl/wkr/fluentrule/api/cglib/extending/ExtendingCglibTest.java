package pl.wkr.fluentrule.api.cglib.extending;

import org.junit.Test;
import org.junit.rules.TestRule;
import pl.wkr.fluentrule.api.testutils.AbstractExceptionsTest;
import pl.wkr.fluentrule.api.testutils.MyException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ExtendingCglibTest extends AbstractExceptionsTest {

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
    public void should_throw_assertion() throws Exception {

        thrownOuter.expect(AssertionError.class);
        thrownOuter.expectMessage("xxx");
        thrownOuter.expectMessage("zzz");

        thrown.expect().hasMessage("xxx");
        throw new Exception("zzz");
    }

}
