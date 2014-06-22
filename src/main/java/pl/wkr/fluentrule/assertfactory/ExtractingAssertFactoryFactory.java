package pl.wkr.fluentrule.assertfactory;

import org.assertj.core.api.AbstractThrowableAssert;
import pl.wkr.fluentrule.extractor.ThrowableExtractor;

public class ExtractingAssertFactoryFactory {

    public <A extends AbstractThrowableAssert<A,T>, T extends Throwable>
        AssertFactory<A,T> newAssertFactory(AssertFactory<A, T> assertFactory,
                                                          ThrowableExtractor throwableExtractor) {

        return new ExtractingAssertFactory<A,T>(assertFactory, throwableExtractor);
    }
}
