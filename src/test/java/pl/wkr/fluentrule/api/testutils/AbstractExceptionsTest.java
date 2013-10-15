package pl.wkr.fluentrule.api.testutils;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import java.util.List;


public abstract class AbstractExceptionsTest {

    protected ExpectedException thrownOuter = ExpectedException.none().handleAssertionErrors().handleAssumptionViolatedExceptions();

    @Rule
    public RuleChain rules;

    protected AbstractExceptionsTest() {
        rules = RuleChain.outerRule( thrownOuter  );
        for(TestRule t : getRulesToWrap()) {
            rules = rules.around(t);
        }
    }

    protected abstract List<TestRule> getRulesToWrap();
}
