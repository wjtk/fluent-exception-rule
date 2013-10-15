package pl.wkr.fluentrule.api;

import java.util.ArrayList;
import java.util.List;

public class CheckExpectedException extends AbstractCheckExpectedException<CheckExpectedException> {

    public static CheckExpectedException none() {
        return new CheckExpectedException();
    }

    public CheckExpectedException check(Check check) {
        checks.add(check);
        return this;
    }

    private CheckExpectedException() {}
}
