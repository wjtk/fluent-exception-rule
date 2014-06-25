package pl.wkr.fluentrule.proxy;

import org.assertj.core.api.AbstractThrowableAssert;

public interface RegisteringProxyFactoryFactory {

    <A extends AbstractThrowableAssert<A, T>, T extends Throwable>
    RegisteringProxyFactory<A,T> newRegisteringProxyFactory(Class<A> assertClass, Class<T> throwableClass);
}
