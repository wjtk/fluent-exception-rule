package pl.wkr.fluentrule.api;

import org.junit.Test;
import org.junit.runners.model.Statement;
import org.mockito.InOrder;
import pl.wkr.fluentrule.api.check.Check;
import pl.wkr.fluentrule.test_.exception.ExpectedExc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static pl.wkr.fluentrule.test_.StatementHelper.evaluateGetException;

public class CheckExpectedException_Test {

    @Test
    public void should_run_all_checks_in_order() throws Throwable {
        CheckExpectedException checker = CheckExpectedException.none();
        Check ch1 = mock(Check.class);
        Check ch2 = mock(Check.class);
        ExpectedExc exception = new ExpectedExc();
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
}
