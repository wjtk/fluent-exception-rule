package pl.wkr.fluentrule.extractor;


public class RootCauseExtractor implements ThrowableExtractor {

    @Override
    public Throwable extract(Throwable throwable) {
        return (throwable != null) ? findRootCause(throwable.getCause()) : null;
    }

    private Throwable findRootCause(Throwable throwable) {
        Throwable prev = throwable;
        Throwable rootCause = null;
        while(prev != null) {
            rootCause = prev;
            prev = prev.getCause();
        }
        return rootCause;
    }
}
