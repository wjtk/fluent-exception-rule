package pl.wkr.fluentrule.test_.exception;

public class ExpectedExc extends Exception {
    public ExpectedExc() {}

    public ExpectedExc(String message) {
        super(message);
    }

    public ExpectedExc(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpectedExc(Throwable cause) {
        super(cause);
    }
}
