package pl.wkr.fluentrule.proxy;

import org.assertj.core.api.AbstractThrowableAssert;
import pl.wkr.fluentrule.assertfactory.AssertFactory;

public class CheckWithProxyFactory {

    private final RegisteringProxyFactoryFactory registeringProxyFactoryFactory;

    public CheckWithProxyFactory(RegisteringProxyFactoryFactory registeringProxyFactoryFactory) {
        this.registeringProxyFactoryFactory = registeringProxyFactoryFactory;
    }


    public <A extends AbstractThrowableAssert<A, T>, T extends Throwable>
    CheckWithProxy<A, T> newCheckWithProxy(Class<A> assertClass, Class<T> throwableClass, AssertFactory<A, T> assertFactory) {

        RegisteringProxyFactory<A,T> registeringProxyFactory = registeringProxyFactoryFactory.newRegisteringProxyFactory(assertClass, throwableClass);
        return new InvokeLaterCheckWithProxy<A,T>(assertFactory, registeringProxyFactory);
    }
}
