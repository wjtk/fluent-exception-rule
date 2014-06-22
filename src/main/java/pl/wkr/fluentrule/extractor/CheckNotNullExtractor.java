package pl.wkr.fluentrule.extractor;

import static org.assertj.core.util.Preconditions.checkNotNull;

public class CheckNotNullExtractor implements ThrowableExtractor {

    private final ThrowableExtractor innerExtractor;
    private final String message;

    public CheckNotNullExtractor(ThrowableExtractor innerExtractor, String message) {
        this.innerExtractor = checkNotNull(innerExtractor, "innerExtractor");
        this.message = checkNotNull(message, "message");
    }


    @Override
    public Throwable extract(Throwable throwable) {
        Throwable extracted = innerExtractor.extract(throwable);
        if(extracted == null) {
           throw new AssertionError(message);
        }
        return extracted;
    }
}
