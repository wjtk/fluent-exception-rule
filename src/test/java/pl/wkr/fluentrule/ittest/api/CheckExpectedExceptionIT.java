package pl.wkr.fluentrule.ittest.api;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import pl.wkr.fluentrule.api.Check;
import pl.wkr.fluentrule.api.CheckExpectedException;
import pl.wkr.fluentrule.api.FluentExpectedException;
import pl.wkr.fluentrule.api.SafeCheck;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckExpectedExceptionIT {

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

    //--------------------------------------------------------------------------

    static class StateChanger {
        public int status() { return 1; }
        public void doSomething() throws SQLException { throw new SQLException("z","z", 10);}
        public void doSomething2() throws Exception { throw new Exception("a");}
    }

}
