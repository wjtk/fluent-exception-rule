package pl.wkr.fluentrule.proxy.factory;

import pl.wkr.fluentrule.assertfactory.ExtractingAssertFactoryFactory;
import pl.wkr.fluentrule.assertfactory.ReflectionAssertFactoryFactory;
import pl.wkr.fluentrule.assertfactory.ThrowableAssertFactory;
import pl.wkr.fluentrule.extractor.CauseExtractor;
import pl.wkr.fluentrule.extractor.CheckNotNullExtractor;
import pl.wkr.fluentrule.extractor.NoopExtractor;
import pl.wkr.fluentrule.extractor.RootCauseExtractor;
import pl.wkr.fluentrule.proxy.CheckWithProxyFactory;
import pl.wkr.fluentrule.proxy.RegisteringProxyFactoryFactory;
import pl.wkr.fluentrule.util.TypeDefaults;

public class ProxiesFactoryFactory {

    private final ProxiesFactory proxiesFactory;


    public ProxiesFactoryFactory() {
        String causeMessage = "Expecting a throwable with cause, but current throwable has no cause";
        String rootCauseMessage = "Expecting a throwable with root cause, but current throwable has no cause";

        RegisteringProxyFactoryFactory registeringProxyFactoryFactory =
                new pl.wkr.fluentrule.proxy.cglib.Factory(new TypeDefaults()).getRegisteringProxyFactoryFactory();

        proxiesFactory =  new ProxiesFactory(
                new CheckWithProxyFactory(registeringProxyFactoryFactory),
                new NoopExtractor(),
                new CheckNotNullExtractor(
                        new CauseExtractor(), causeMessage
                ),
                new CheckNotNullExtractor(
                        new RootCauseExtractor(), rootCauseMessage
                ),
                new ThrowableAssertFactory(),
                new ReflectionAssertFactoryFactory(),
                new ExtractingAssertFactoryFactory()
             );
    }


    public ProxiesFactory getProxiesFactory() {
        return proxiesFactory;
    }
}
