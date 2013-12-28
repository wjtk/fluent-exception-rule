package pl.wkr.fluentrule.api;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.ThrowableAssert;
import pl.wkr.fluentrule.proxy.CheckWithProxy;
import pl.wkr.fluentrule.proxy.ProxyFactory;
import pl.wkr.fluentrule.util.ClassFinder;

import static org.assertj.core.util.Preconditions.checkNotNull;

/**
 *
 * Implements junit's expected exception rule in fluent way, returning AssertJ {@link ThrowableAssert}.
 * <p/>
 * Usage in tests:
 * <p/>
 * <pre><code>
 *  &#064;Rule
 *  public FluentExpectedException thrown = FluentExpectedException.none();
 *
 *  &#064;Test
 *  public void fluent_rule_any_exception() {
 *      thrown.expect().hasMessage("exc").hasNoCause();
 *      throw new IllegalStateException("exc");
 *  }
 * </code></pre>
 *
 * @see
 *  <a href="https://github.com/wjtk/fluent-exception-rule#readme" target="_blank">Project site - more examples</a>
 *
 *
 * @author Wojciech Krak
 */
public class FluentExpectedException extends AbstractCheckExpectedException<FluentExpectedException>{

    private static final ProxyFactory PROXY_FACTORY = new ProxyFactory();
    private static final AssertFactory<ThrowableAssert,Throwable> THROWABLE_ASSERT_FACTORY = new ThrowableAssertFactory();
    private static final AssertFactory<ThrowableAssert,Throwable> CAUSE_ASSERT_FACTORY = new CauseAssertFactory();
    private static final AssertFactory<ThrowableAssert,Throwable> ROOT_CAUSE_ASSERT_FACTORY = new RootCauseAssertFactory();
    private static final ReflectionAssertFactoryFactory REFLECTION_ASSERT_FACTORY_FACTORY = new ReflectionAssertFactoryFactory();
    private static final int THROWABLE_TYPE_INDEX_IN_AbstractThrowableAssert = 1;
    private static final ClassFinder throwableClassFinder = new ClassFinder(THROWABLE_TYPE_INDEX_IN_AbstractThrowableAssert);

    private final ProxyFactory proxyFactory;
    private final AssertFactory<ThrowableAssert,Throwable> throwableAssertFactory;
    private final AssertFactory<ThrowableAssert,Throwable> causeAssertFactory;
    private final AssertFactory<ThrowableAssert,Throwable> rootCauseAssertFactory;

    private final ReflectionAssertFactoryFactory reflectionAssertFactoryFactory;


    /**
     * Creates and returns new instance of {@code FluentExpectedException} rule.
     *
     * @return new instance
     */
    public static FluentExpectedException none() {
        return new FluentExpectedException();
    }

    protected FluentExpectedException(){
        this(PROXY_FACTORY, THROWABLE_ASSERT_FACTORY, CAUSE_ASSERT_FACTORY, ROOT_CAUSE_ASSERT_FACTORY,
                REFLECTION_ASSERT_FACTORY_FACTORY);
    }

    FluentExpectedException(ProxyFactory proxyFactory,
                            AssertFactory<ThrowableAssert,Throwable> throwableAssertFactory,
                            AssertFactory<ThrowableAssert,Throwable> causeAssertFactory,
                            AssertFactory<ThrowableAssert,Throwable> rootCauseAssertFactory,
                            ReflectionAssertFactoryFactory reflectionAssertFactoryFactory
                            ) {
        this.proxyFactory = proxyFactory;
        this.throwableAssertFactory = throwableAssertFactory;
        this.causeAssertFactory = causeAssertFactory;
        this.rootCauseAssertFactory = rootCauseAssertFactory;
        this.reflectionAssertFactoryFactory = reflectionAssertFactoryFactory;
    }

    /**
     * Starts expecting of any exception.
     *
     * @return {@link ThrowableAssert} to specify more expectations
     *
     */
    public ThrowableAssert expect() {
        return newThrowableAssertProxy();
    }

    /**
     * Starts expecting of exception of specified type.
     *
     * @param type expected type of exception
     * @return {@link ThrowableAssert} to specify more expectations
     */
    public ThrowableAssert expect(Class<? extends Throwable> type) {
        return newThrowableAssertProxy().isInstanceOf(type);
    }

    /**
     * Starts expecting exception of any of specified types.
     *
     * @param types list of expected types of exception
     * @return {@link ThrowableAssert} to specify more expectations
     */
    public ThrowableAssert expectAny(Class<?> ... types) {
        return newThrowableAssertProxy().isInstanceOfAny(types);
    }

    /**
     * Start expecting of exception with any cause.
     *
     * @return {@link ThrowableAssert} to specify more expectations on exception's cause
     */
    public ThrowableAssert expectCause(){
        return newThrowableCauseAssertProxy();
    }

    /**
     * Starts expecting of exception with cause of specified type.
     *
     * @param type expected type of exception's cause
     * @return {@link ThrowableAssert} to specify more expectations on exception's cause
     */
    public ThrowableAssert expectCause(Class<? extends Throwable> type){
        return newThrowableCauseAssertProxy().isInstanceOf(type);
    }

    /**
     * Starts expecting of exception with any root cause.
     *
     * @return {@link ThrowableAssert} to specify more expectations on exception's root cause
     */
    public ThrowableAssert expectRootCause() {
        return newThrowableRootCauseAssertProxy();
    }

    /**
     * Starts expecting of exception with root cause of specified type.
     *
     * @param type expected type of exception's root cause
     * @return {@link ThrowableAssert} to specify more expectations on exception's root cause
     */
    public ThrowableAssert expectRootCause(Class<? extends Throwable> type) {
        return newThrowableRootCauseAssertProxy().isInstanceOf(type);
    }

    /**
     * Starts expecting exception and return custom throwable assert to specify expectations on exception.
     * Method take class of any custom implementation of {@link AbstractThrowableAssert} and returns proxy of it,
     * to allow specify expectations on thrown exception.
     *
     * @param assertClass class of custom throwable assert, subclass of {@link AbstractThrowableAssert}
     * @param <A> class of custom throwable assert
     * @param <T> class of throwables supported by custom throwable assert
     * @return instance of custom throwable assert to specify more expectations on exception
     */
    public <A extends AbstractThrowableAssert<A,T>,T extends Throwable> A assertWith(Class<A> assertClass) {
        checkNotNull(assertClass);
        Class<T> throwableClass = throwableClassFinder.findConcreteClass(assertClass);
        return newProxyWithReflectionAssertFactory(assertClass, throwableClass);
    }

    //--------------------------------------------------------------------------------

    protected final ThrowableAssert newThrowableAssertProxy() {
        return newProxy( ThrowableAssert.class, Throwable.class, throwableAssertFactory);
    }

    protected final ThrowableAssert newThrowableCauseAssertProxy() {
        return newProxy(ThrowableAssert.class, Throwable.class, causeAssertFactory);
    }

    protected final ThrowableAssert newThrowableRootCauseAssertProxy() {
        return newProxy(ThrowableAssert.class, Throwable.class, rootCauseAssertFactory);
    }

    protected final <A extends AbstractThrowableAssert<A,T>, T extends Throwable>
    A newProxyWithReflectionAssertFactory(Class<A> assertClass, Class<T> throwableClass) {

        return newProxy(assertClass, throwableClass, reflectionAssertFactoryFactory.newAssertFactory(assertClass, throwableClass));
    }

    protected final <A extends AbstractThrowableAssert<A,T>, T extends Throwable>
        A newProxy(Class<A> assertClass, Class<T> throwableClass, AssertFactory<A,T> factory) {

        CheckWithProxy<A,T> check = proxyFactory.newCheckWithProxy(assertClass, throwableClass, factory);
        addCheck(check);
        return check.getAssertProxy();
    }

}
