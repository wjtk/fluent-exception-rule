package pl.wkr.fluentrule.api;

import org.assertj.core.api.ThrowableAssert;
import pl.wkr.fluentrule.proxy.ProxyFactory;

public class FluentExpectedExceptionBuilder {

    private ProxyFactory proxyFactory = null;
    private AssertFactory<ThrowableAssert,Throwable> throwableAssertFactory = null;
    private AssertFactory<ThrowableAssert,Throwable> causeAssertFactory  = null;
    private AssertFactory<ThrowableAssert,Throwable> rootCauseAssertFactory  = null;
    private ReflectionAssertFactoryFactory reflectionAssertFactoryFactory  = null;

    public FluentExpectedExceptionBuilder withProxyFactory(ProxyFactory factory) {
        proxyFactory = factory;
        return this;
    }

    public FluentExpectedExceptionBuilder withThrowableAssertFactory(AssertFactory<ThrowableAssert,Throwable> factory) {
        throwableAssertFactory = factory;
        return this;
    }

    public FluentExpectedExceptionBuilder withCauseAssertFactory(AssertFactory<ThrowableAssert,Throwable> factory) {
        causeAssertFactory = factory;
        return this;
    }

    public FluentExpectedExceptionBuilder withRootCauseAssertFactory(AssertFactory<ThrowableAssert,Throwable> factory) {
        rootCauseAssertFactory = factory;
        return this;
    }

    public FluentExpectedExceptionBuilder withReflectionAssertFactoryFactory(ReflectionAssertFactoryFactory factoryFactory) {
        reflectionAssertFactoryFactory = factoryFactory;
        return this;
    }

    public FluentExpectedException build() {
        return new FluentExpectedException(
                proxyFactory, throwableAssertFactory,
                causeAssertFactory, rootCauseAssertFactory, reflectionAssertFactoryFactory);
    }
}
