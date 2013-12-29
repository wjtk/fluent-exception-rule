package pl.wkr.fluentrule.assertfactory;

import org.assertj.core.api.AbstractThrowableAssert;


class ExtractingAssertFactory<A extends AbstractThrowableAssert<A,T>, T extends Throwable> implements AssertFactory<A,T> {

    private final AssertFactory<A, T> assertFactory;
    private final ThrowableExtractor throwableExtractor;

    public ExtractingAssertFactory(AssertFactory<A,T> assertFactory, ThrowableExtractor throwableExtractor) {
        this.assertFactory = assertFactory;
        this.throwableExtractor = throwableExtractor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public A getAssert(Throwable throwable) {
        Throwable extracted = throwableExtractor.extract(throwable);
        return assertFactory.getAssert(extracted);
    }
}
