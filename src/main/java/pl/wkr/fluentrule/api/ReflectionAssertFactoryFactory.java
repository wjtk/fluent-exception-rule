package pl.wkr.fluentrule.api;

import org.assertj.core.api.AbstractThrowableAssert;

class ReflectionAssertFactoryFactory {

    public <A extends AbstractThrowableAssert<A, T>, T extends Throwable>
        AssertFactory<A, T> newAssertFactory(Class<A> assertClass, Class<T> throwableClass) {

        return new ReflectionAssertFactory<A, T>(assertClass, throwableClass);
    }
}
