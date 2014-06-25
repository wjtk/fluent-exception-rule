package pl.wkr.fluentrule.proxy.cglib;

import org.assertj.core.api.AbstractThrowableAssert;
import pl.wkr.fluentrule.proxy.RegisteringProxyFactory;
import pl.wkr.fluentrule.proxy.RegisteringProxyFactoryFactory;

class CglibRegisteringProxyFactoryFactory implements RegisteringProxyFactoryFactory {

    private final CglibCallbackFilterFactory cglibCallbackFilterFactory;

    public CglibRegisteringProxyFactoryFactory(CglibCallbackFilterFactory cglibCallbackFilterFactory) {
        this.cglibCallbackFilterFactory = cglibCallbackFilterFactory;
    }

    @Override
    public <A extends AbstractThrowableAssert<A, T>, T extends Throwable>
    RegisteringProxyFactory<A, T> newRegisteringProxyFactory(Class<A> assertClass, Class<T> throwableClass) {

        return new CglibRegisteringProxyFactory<A, T>(assertClass, throwableClass, cglibCallbackFilterFactory);
    }
}
