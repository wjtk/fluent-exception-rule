package pl.wkr.fluentrule.api;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.ThrowableAssert;
import pl.wkr.fluentrule.proxy.CheckWithProxy;
import pl.wkr.fluentrule.proxy.ProxyFactory;
import pl.wkr.fluentrule.util.ClassFinder;

public class FluentExpectedException extends AbstractCheckExpectedException<FluentExpectedException>{

    private static final ProxyFactory PROXY_FACTORY = new ProxyFactory();
    private static final ThrowableAssertFactory THROWABLE_ASSERT_FACTORY = new ThrowableAssertFactory();
    private static final CauseAssertFactory CAUSE_ASSERT_FACTORY = new CauseAssertFactory();
    private static final ReflectionAssertFactoryFactory REFLECTION_ASSERT_FACTORY_FACTORY = new ReflectionAssertFactoryFactory();
    private static final int THROWABLE_TYPE_INDEX_IN_AbstractThrowableAssert = 1;
    private static final ClassFinder throwableClassFinder = new ClassFinder(THROWABLE_TYPE_INDEX_IN_AbstractThrowableAssert);

    private final ProxyFactory proxyFactory;
    private final ThrowableAssertFactory throwableAssertFactory;
    private final CauseAssertFactory causeAssertFactory;
    private final ReflectionAssertFactoryFactory reflectionAssertFactoryFactory;


    public static FluentExpectedException none() {
        return new FluentExpectedException();
    }

    protected FluentExpectedException(){
        this(PROXY_FACTORY, THROWABLE_ASSERT_FACTORY, CAUSE_ASSERT_FACTORY, REFLECTION_ASSERT_FACTORY_FACTORY);
    }

    FluentExpectedException(ProxyFactory proxyFactory,
                            ThrowableAssertFactory throwableAssertFactory,
                            CauseAssertFactory causeAssertFactory,
                            ReflectionAssertFactoryFactory reflectionAssertFactoryFactory
                            ) {
        this.proxyFactory = proxyFactory;
        this.throwableAssertFactory = throwableAssertFactory;
        this.causeAssertFactory = causeAssertFactory;
        this.reflectionAssertFactoryFactory = reflectionAssertFactoryFactory;
    }

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

    public <A extends AbstractThrowableAssert<A,T>,T extends Throwable> A assertWith(Class<A> assertClass) {
        Class<T> throwableClass = throwableClassFinder.findConcreteClass(assertClass);
        return newProxy(assertClass, throwableClass);
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
