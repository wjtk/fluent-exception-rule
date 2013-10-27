package pl.wkr.fluentrule.api;


public class CheckExpectedException extends AbstractCheckExpectedException<CheckExpectedException> {

    public static CheckExpectedException none() {
        return new CheckExpectedException();
    }

    public CheckExpectedException check(Check check) {
        //TODO not null
        checks.add(check);
        return this;
    }

    private CheckExpectedException() {}
}
