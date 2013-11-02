package pl.wkr.fluentrule.api;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runners.model.Statement;
import org.mockito.InOrder;
import pl.wkr.fluentrule.api.testutils.MyException;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pl.wkr.fluentrule.api.testutils.StatementHelper.evaluateGetException;

public class CheckExpectedExceptionTest {

    private FluentExpectedException thrownOuter = FluentExpectedException.none().handleAssertionErrors();
    private CheckExpectedException thrown = CheckExpectedException.none();

    @Rule
    public RuleChain rule = RuleChain.outerRule(thrownOuter).around(thrown);


    @Test
    public void should_catch_simple_exception_with_message() throws Exception {
        thrown.check( new Check() {
            @Override
            public void check(Throwable exception) {
               assertThat(exception).hasMessage("xyz");
            }
        });
        throw new Exception("xyz");
    }


    @Test
    public void should_catch_exception_check_state_of_object() throws Exception {
        final StateChanger obj = new StateChanger();
        final int state = obj.status();

        thrown.check( new SafeCheck<SQLException>(){
            @Override
            protected void safeCheck(SQLException exception) {
                assertThat(exception.getErrorCode()).isEqualTo(10);
                assertThat(state).isEqualTo(obj.status());
            }
        });

        obj.doSomething();
    }

    @Test
    public void should_not_catch_because_exception_has_unexpected_type() throws Exception {
        final StateChanger obj = new StateChanger();

        thrownOuter.expect(AssertionError.class).hasMessageContaining(SQLException.class.getName())
                .hasMessageContaining(Exception.class.getName());

        thrown.check(new SafeCheck<SQLException>(){
            @Override
            protected void safeCheck(SQLException exception) {
                assertThat(exception.getErrorCode()).isEqualTo(10);
            }
        });

        obj.doSomething2();
    }


    @Test
    public void should_run_all_checks_in_order() throws Throwable {
        CheckExpectedException checker = CheckExpectedException.none();
        Check ch1 = mock(Check.class);
        Check ch2 = mock(Check.class);
        MyException exception = new MyException();
        Statement statement = mock(Statement.class);
        doThrow(exception).when(statement).evaluate();

        checker.check(ch1);
        checker.check(ch2);
        evaluateGetException(checker.apply(statement,null));

        InOrder io = inOrder(ch1,ch2);
        io.verify(ch1).check(exception);
        io.verify(ch2).check(exception);
    }

    @Test
    public void should_check_method_return_this() {
        CheckExpectedException checker = CheckExpectedException.none();
        Check notNullCheck = mock(Check.class);

        assertThat(checker.check(notNullCheck)).isEqualTo(checker);
    }

    //--------------------------------------------------------------------------

    static class StateChanger {
        public int status() { return 1; }
        public void doSomething() throws SQLException { throw new SQLException("z","z", 10);}
        public void doSomething2() throws Exception { throw new Exception("a");}
    }

}
