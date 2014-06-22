package pl.wkr.fluentrule.proxy.factory;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProxiesFactoryFactoryTest {

    @Test
    public void should_return_proxies_factory() {
        assertThat(new ProxiesFactoryFactory().getProxiesFactory()).isNotNull();
    }
}