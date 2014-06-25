package pl.wkr.fluentrule.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import org.assertj.core.api.AbstractThrowableAssert;
import pl.wkr.fluentrule.proxy.CalledMethodRegister;
import pl.wkr.fluentrule.proxy.RegisteringProxyFactory;

class CglibRegisteringProxyFactory<A extends AbstractThrowableAssert<A,T>,T extends Throwable> implements RegisteringProxyFactory<A,T> {

    private final Class<A> assertClass;
    private final Class<T> throwableClass;
    private final CglibCallbackFilterFactory cglibCallbackFilterFactory;

    public CglibRegisteringProxyFactory(Class<A> assertClass, Class<T> throwableClass, CglibCallbackFilterFactory cglibCallbackFilterFactory) {
        this.assertClass = assertClass;
        this.throwableClass = throwableClass;
        this.cglibCallbackFilterFactory = cglibCallbackFilterFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public A createProxy(CalledMethodRegister methodRegister) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(assertClass);
        enhancer.setCallbackFilter(cglibCallbackFilterFactory.getMethodCallbackFilter());
        enhancer.setCallbacks(cglibCallbackFilterFactory.newCallbackArrayForRegister(methodRegister));
        return (A) enhancer.create(new Class[] { throwableClass }, new Object[] { null });
    }
}
