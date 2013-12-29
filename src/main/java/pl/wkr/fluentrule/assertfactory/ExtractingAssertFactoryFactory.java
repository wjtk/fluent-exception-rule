package pl.wkr.fluentrule.assertfactory;

import org.assertj.core.api.AbstractThrowableAssert;

class ExtractingAssertFactoryFactory {

    public <A extends AbstractThrowableAssert<A,T>, T extends Throwable>
            ExtractingAssertFactory<A,T> newAssertFactory(AssertFactory<A, T> assertFactory,
                                                          ThrowableExtractor throwableExtractor) {

        return new ExtractingAssertFactory<A,T>(assertFactory, throwableExtractor);
    }
}
