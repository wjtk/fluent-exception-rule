package pl.wkr.fluentrule.proxy;

import org.assertj.core.api.AbstractThrowableAssert;

public interface RegisteringProxyFactory<A extends AbstractThrowableAssert<A,T>,T extends Throwable> {

    A createProxy(CalledMethodRegister methodRegister);
}
