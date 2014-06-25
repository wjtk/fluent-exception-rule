package pl.wkr.fluentrule.test_.exception;

public class AnyExc extends Exception{
    public AnyExc() {}

    public AnyExc(String message) {
        super(message);
    }

    public AnyExc(String message, Throwable cause) {
        super(message, cause);
    }

    public AnyExc(Throwable cause) {
        super(cause);
    }
}
