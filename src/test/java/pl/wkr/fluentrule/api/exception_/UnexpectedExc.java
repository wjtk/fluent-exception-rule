package pl.wkr.fluentrule.api.exception_;

public class UnexpectedExc extends Exception {
    public UnexpectedExc() {}

    public UnexpectedExc(String message) {
        super(message);
    }

    public UnexpectedExc(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedExc(Throwable cause) {
        super(cause);
    }
}
