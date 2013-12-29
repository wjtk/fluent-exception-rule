package pl.wkr.fluentrule.assertfactory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.assertj.core.api.AbstractThrowableAssert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Preconditions.checkNotNull;

/**
 * Factory producing real assert.
 * Calls constructor:
 *      new SomeThrowableAssert(throwable);
 * Uses cglib because it is more convenient than calling constructor with JDK reflection api.
 *
 * @param <A>
 * @param <T>
 */
class ReflectionAssertFactory<A extends AbstractThrowableAssert<A,T>, T extends Throwable>
        implements AssertFactory<A,T> {

    private Class<A> assertClass;
    private Class<T> throwableClass;

    public ReflectionAssertFactory(Class<A> assertClass, Class<T> throwableClass) {
        this.assertClass = checkNotNull(assertClass, "assertClass");
        this.throwableClass = checkNotNull(throwableClass, "throwableClass");
    }

    @Override
    public A getAssert(Throwable throwable) {
        if( throwableClass != Throwable.class ){
            assertThat(throwable).isInstanceOf(throwableClass);
        }
        return proxy(assertClass, throwableClass, throwableClass.cast(throwable));
    }

    @SuppressWarnings("unchecked")
    protected <T, V> V proxy(Class<V> assertClass, Class<T> actualClass, T actual) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(assertClass);
        enhancer.setCallback(NoOp.INSTANCE);
        return (V) enhancer.create(new Class[] { actualClass }, new Object[] { actual });
    }
}
