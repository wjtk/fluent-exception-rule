package pl.wkr.fluentrule.proxy;

import org.assertj.core.api.AbstractThrowableAssert;
import pl.wkr.fluentrule.api.AssertFactory;

public class ProxyFactory {

    public <A extends AbstractThrowableAssert<A, T>, T extends Throwable>
        CheckWithProxy<A, T> newCheckWithProxy(Class<A> assertClass, Class<T> throwableClass, AssertFactory<A, T> factory) {

        return new CheckWithProxyImpl<A,T>(assertClass, throwableClass, factory);
    }

}
