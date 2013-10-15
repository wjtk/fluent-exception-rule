package pl.wkr.fluentrule.api;

import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runners.model.Statement;
import org.mockito.InOrder;
import pl.wkr.fluentrule.api.testutils.AbstractExceptionsTest;
import pl.wkr.fluentrule.api.testutils.MyException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pl.wkr.fluentrule.api.testutils.StatementHelper.evaluateGetException;

public class CheckExpectedExceptionTest extends AbstractExceptionsTest{

    private CheckExpectedException thrown;

    @Override
    protected List<TestRule> getRulesToWrap() {
        thrown = CheckExpectedException.none();
        return Arrays.<TestRule>asList(thrown);
    }


    @Test
    public void should_catch_exception_check_state_of_object() throws Exception {
        final StateChanger obj = new StateChanger();
        final int state = obj.status();

        thrown.check( new Check<SQLException>(){
            @Override
            protected void check(SQLException exception) {
                assertThat(exception.getErrorCode()).isEqualTo(10);
                assertThat(state).isEqualTo(obj.status());
            }
        });

        obj.doSomething();
    }

    @Test
    public void should_not_catch_because_exception_has_unexpected_type() throws Exception {
        final StateChanger obj = new StateChanger();

        thrownOuter.expect(AssertionError.class);
        thrownOuter.expectMessage(SQLException.class.getName());
        thrownOuter.expectMessage(Exception.class.getName());
        thrownOuter.expectMessage("but was instance of");

        thrown.check(new Check<SQLException>(){
            @Override
            protected void check(SQLException exception) {
                assertThat(exception.getErrorCode()).isEqualTo(10);
            }
        });

        obj.doSomething2();
    }


    @Test
    public void should_run_all_checks_in_order() throws Throwable {
        CheckExpectedException checker = CheckExpectedException.none();
        Check ch1 = mock(CheckThrowable.class);
        Check ch2 = mock(CheckThrowable.class);
        MyException exception = new MyException();
        Statement statement = mock(Statement.class);
        doThrow(exception).when(statement).evaluate();

        checker.check(ch1);
        checker.check(ch2);
        evaluateGetException(checker.apply(statement,null));

        InOrder io = inOrder(ch1,ch2);
        io.verify(ch1).doCheck(exception);
        io.verify(ch2).doCheck(exception);
    }

    @Test
    public void should_check_return_this() {
        CheckExpectedException checker = CheckExpectedException.none();

        assertThat(checker.check(mock(CheckThrowable.class))).isEqualTo(checker);
    }


    abstract static class CheckThrowable extends Check<Throwable> {};

    static class StateChanger {
        public int status() { return 1; }
        public void doSomething() throws SQLException { throw new SQLException("z","z", 10);}
        public void doSomething2() throws Exception { throw new Exception("a");}
    }

}
