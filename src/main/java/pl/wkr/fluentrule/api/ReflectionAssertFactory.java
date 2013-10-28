package pl.wkr.fluentrule.api;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.assertj.core.api.AbstractThrowableAssert;


class ReflectionAssertFactory<A extends AbstractThrowableAssert<A,T>, T extends Throwable>
        implements AssertFactory<A,T> {

    private Class<A> assertClass;
    private Class<T> throwableClass;

    public ReflectionAssertFactory(Class<A> assertClass, Class<T> throwableClass) {
        this.assertClass = assertClass;
        this.throwableClass = throwableClass;
    }

    @Override
    public A getAssert(T throwable) {
        return proxy(assertClass, throwableClass, throwable);
    }

    @SuppressWarnings("unchecked")
    protected <T, V> V proxy(Class<V> assertClass, Class<T> actualClass, T actual) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(assertClass);
        enhancer.setCallback(NoOp.INSTANCE);
        return (V) enhancer.create(new Class[] { actualClass }, new Object[] { actual });
    }


}
