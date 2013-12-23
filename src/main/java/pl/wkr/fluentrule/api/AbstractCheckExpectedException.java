package pl.wkr.fluentrule.api;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.util.Preconditions.checkNotNull;

abstract class AbstractCheckExpectedException<S extends AbstractCheckExpectedException<S>>
        extends AbstractHandleExceptionRule<S> {

    private List<Check> checks = new ArrayList<Check>();

    protected void addCheck(Check check) {
        checks.add(checkNotNull(check,"check"));
    }

    public boolean isExceptionExpected() {
        return checks.size() > 0;
    }

    protected void handleException(Throwable e) {
        for(Check c : checks) {
            c.check(e);
        }
    }

    protected void failBecauseExceptionWasNotThrown() {
        throw new AssertionError("Exception was expected but was not thrown");
    }
}
