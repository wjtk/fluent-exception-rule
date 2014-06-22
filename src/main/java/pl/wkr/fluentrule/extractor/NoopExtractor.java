package pl.wkr.fluentrule.extractor;

public class NoopExtractor implements ThrowableExtractor {

    @Override
    public Throwable extract(Throwable throwable) {
        return throwable;
    }
}
