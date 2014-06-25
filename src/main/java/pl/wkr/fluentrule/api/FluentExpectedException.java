package pl.wkr.fluentrule.api;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.ThrowableAssert;
import pl.wkr.fluentrule.proxy.CheckWithProxy;
import pl.wkr.fluentrule.proxy.factory.ProxiesFactory;
import pl.wkr.fluentrule.proxy.factory.ProxiesFactoryFactory;

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
public class FluentExpectedException extends AbstractCheckExpectedException<FluentExpectedException> {

    private static final ProxiesFactory PROXIES_FACTORY = new ProxiesFactoryFactory().getProxiesFactory();

    private final ProxiesFactory proxiesFactory;

    /**
     * Creates and returns new instance of {@code FluentExpectedException} rule.
     *
     * @return new instance
     */
    public static FluentExpectedException none() {
        return new FluentExpectedException();
    }

    protected FluentExpectedException() {
        this(PROXIES_FACTORY);
    }

    FluentExpectedException(ProxiesFactory proxiesFactory) {
        this.proxiesFactory = checkNotNull(proxiesFactory, "proxiesFactory");
    }

    /**
     * Starts expecting of any exception.
     *
     * @return {@link ThrowableAssert} to specify more expectations
     *
     */
    public ThrowableAssert expect() {
        return addCheckAndReturnProxy(proxiesFactory.newThrowableAssertProxy());
    }

    /**
     * Starts expecting of exception of specified type.
     *
     * @param type expected type of exception
     * @return {@link ThrowableAssert} to specify more expectations
     */
    public ThrowableAssert expect(Class<? extends Throwable> type) {
        checkNotNull(type, "type");
        return addCheckAndReturnProxy(proxiesFactory.newThrowableAssertProxy()).isInstanceOf(type);
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
    public <A extends AbstractThrowableAssert<A,T>,T extends Throwable> A expectWith(Class<A> assertClass) {
        checkNotNull(assertClass, "assertClass");
        return addCheckAndReturnProxy(proxiesFactory.newThrowableCustomAssertProxy(assertClass));
    }

    /**
     * Start expecting of exception with any cause.
     *
     * @return {@link ThrowableAssert} to specify more expectations on exception's cause
     */
    public ThrowableAssert expectCause(){
        return addCheckAndReturnProxy(proxiesFactory.newThrowableCauseAssertProxy());
    }

    /**
     * Starts expecting of exception with cause of specified type.
     *
     * @param type expected type of exception's cause
     * @return {@link ThrowableAssert} to specify more expectations on exception's cause
     */
    public ThrowableAssert expectCause(Class<? extends Throwable> type){
        checkNotNull(type, "type");
        return addCheckAndReturnProxy(proxiesFactory.newThrowableCauseAssertProxy()).isInstanceOf(type);
    }

    public <A extends AbstractThrowableAssert<A,T>,T extends Throwable> A expectCauseWith(Class<A> assertClass) {
        checkNotNull(assertClass, "assertClass");
        return addCheckAndReturnProxy(proxiesFactory.newThrowableCauseCustomAssertProxy(assertClass));
    }

    /**
     * Starts expecting of exception with any root cause.
     *
     * @return {@link ThrowableAssert} to specify more expectations on exception's root cause
     */
    public ThrowableAssert expectRootCause() {
        return addCheckAndReturnProxy(proxiesFactory.newThrowableRootCauseAssertProxy());
    }

    /**
     * Starts expecting of exception with root cause of specified type.
     *
     * @param type expected type of exception's root cause
     * @return {@link ThrowableAssert} to specify more expectations on exception's root cause
     */
    public ThrowableAssert expectRootCause(Class<? extends Throwable> type) {
        checkNotNull(type, "type");
        return addCheckAndReturnProxy(proxiesFactory.newThrowableRootCauseAssertProxy()).isInstanceOf(type);
    }

    public <A extends AbstractThrowableAssert<A,T>,T extends Throwable> A expectRootCauseWith(Class<A> assertClass) {
        checkNotNull(assertClass, "assertClass");
        return addCheckAndReturnProxy(proxiesFactory.newThrowableRootCauseCustomAssertProxy(assertClass));
    }

    //--------------------------------------------------------------------------------

    private <A extends AbstractThrowableAssert<A,T>, T extends Throwable> A addCheckAndReturnProxy(CheckWithProxy<A, T> check) {
        addCheck(check);
        return check.getAssertProxy();
    }

    //------------------------------------------

}
