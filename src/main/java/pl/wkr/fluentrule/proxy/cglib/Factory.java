package pl.wkr.fluentrule.proxy.cglib;

import pl.wkr.fluentrule.proxy.RegisteringProxyFactoryFactory;
import pl.wkr.fluentrule.util.TypeDefaults;

public class Factory {

    private final RegisteringProxyFactoryFactory registeringProxyFactoryFactory;


    public Factory(TypeDefaults typeDefaults) {
        this.registeringProxyFactoryFactory =
                new CglibRegisteringProxyFactoryFactory(
                    new CglibCallbackFilterFactory(
                        typeDefaults));
    }

    public RegisteringProxyFactoryFactory getRegisteringProxyFactoryFactory() {
        return registeringProxyFactoryFactory;
    }
}
