package pl.wkr.fluentrule.proxy;

import org.assertj.core.api.AbstractThrowableAssert;
import pl.wkr.fluentrule.api.check.Check;

/**
 * Interface for implementations of assertions proxies.
 * Proxy should call methods of real assertion when {@link #check(Throwable)} method is called.
 *
 * @param <A> type of expected exception assertion
 * @param <T> type of expected exception
 */
public interface CheckWithProxy<A extends AbstractThrowableAssert<A,T>,T extends Throwable> extends Check {


    /**
     * Should return assertion proxy.
     *
     * @return assertion proxy
     */
    A getAssertProxy();

}
