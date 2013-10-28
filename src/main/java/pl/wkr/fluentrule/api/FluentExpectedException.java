package pl.wkr.fluentrule.api;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.ThrowableAssert;
import pl.wkr.fluentrule.proxy.CheckWithProxy;
import pl.wkr.fluentrule.proxy.ProxyFactory;

public class FluentExpectedException extends AbstractCheckExpectedException<FluentExpectedException>{

    private static final CauseAssertFactory causeAssertFactory = new CauseAssertFactory();
    private static final ThrowableAssertFactory throwableAssertFactory = new ThrowableAssertFactory();
    private static final ProxyFactory proxyFactory = new ProxyFactory();
    private static final ReflectionAssertFactoryFactory reflectionAssertFactoryFactory = new ReflectionAssertFactoryFactory();

    public FluentExpectedException(){}

    //TODO constructor for testing?

    public ThrowableAssert expect() {
        return newProxy( ThrowableAssert.class, Throwable.class, throwableAssertFactory);
    }

    public ThrowableAssert expect(Class<? extends Throwable> type) {
        return newProxy(ThrowableAssert.class, Throwable.class, throwableAssertFactory).isInstanceOf(type);
    }

    public ThrowableAssert expectAny(Class<?> ... types) {
        return newProxy(ThrowableAssert.class, Throwable.class, throwableAssertFactory).isInstanceOfAny(types);
    }

    public ThrowableAssert expectCause(){
        return newProxy(ThrowableAssert.class, Throwable.class, causeAssertFactory);
    }


    //--------------------------------------------------------------------------------

    protected final <A extends AbstractThrowableAssert<A,T>, T extends Throwable>
        A newProxy(Class<A> assertClass, Class<T> throwableClass, AssertFactory<A,T> factory) {

        CheckWithProxy<A,T> check = proxyFactory.newCheckWithProxy(assertClass, throwableClass, factory);
        checks.add(check);
        return check.getAssertProxy();
    }

    protected final <A extends AbstractThrowableAssert<A,T>, T extends Throwable>
        A newProxy(Class<A> assertClass, Class<T> throwableClass) {

        //TODO  - tworzenie reflectionAssertFactory wynieść do proxyFactory??
        return newProxy(assertClass, throwableClass, reflectionAssertFactoryFactory.newFactory(assertClass, throwableClass));
    }
}
