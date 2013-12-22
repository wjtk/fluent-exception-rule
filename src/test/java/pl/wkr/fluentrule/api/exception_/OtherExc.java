package pl.wkr.fluentrule.api.exception_;

public class OtherExc extends Exception{
    public OtherExc() {}

    public OtherExc(String message) {
        super(message);
    }

    public OtherExc(String message, Throwable cause) {
        super(message, cause);
    }

    public OtherExc(Throwable cause) {
        super(cause);
    }
}
