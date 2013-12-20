package pl.wkr.fluentrule.api;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractCheckExpectedException<S extends AbstractCheckExpectedException<S>>
        extends AbstractHandleExceptionRule<S> {

    protected List<Check> checks = new ArrayList<Check>();

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
