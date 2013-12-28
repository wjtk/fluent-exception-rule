package pl.wkr.fluentrule.proxy;

import org.assertj.core.api.AbstractThrowableAssert;
import pl.wkr.fluentrule.api.AssertFactory;

/**
 * Factory of {@link CheckWithProxy} implementations.
 */
public class CheckWithProxyFactory {

    /**
     * Constructs new proxy of expected exception's assertion.
     *
     * @param assertClass class of expected exception's assertion
     * @param throwableClass class of expected exception
     * @param factory assertion factory
     * @param <A> type of expected exception's assertion
     * @param <T> type
     * @return new instance of proxy of exception's assertion
     */
    public <A extends AbstractThrowableAssert<A, T>, T extends Throwable>
        CheckWithProxy<A, T> newCheckWithProxy(Class<A> assertClass, Class<T> throwableClass, AssertFactory<A, T> factory) {

        return new CheckWithProxyImpl<A,T>(assertClass, throwableClass, factory);
    }

}
