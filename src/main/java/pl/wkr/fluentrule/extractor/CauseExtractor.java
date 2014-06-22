package pl.wkr.fluentrule.extractor;

public class CauseExtractor implements ThrowableExtractor {

    @Override
    public Throwable extract(Throwable throwable) {
        return (throwable != null) ? throwable.getCause() : null;
    }
}
