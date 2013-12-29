package pl.wkr.fluentrule.assertfactory;

import org.assertj.core.api.AbstractThrowableAssert;

/**
 * Interface for implementations of factories, which can construct assertion for given thrown exception.
 *
 * @param <A> type of assertion
 * @param <T> type of expected exception
 */
public interface AssertFactory<A extends AbstractThrowableAssert<A,T>, T extends Throwable> {

    /**
     * Should return constructed assertion for given exception
     *
     * @param throwable exception
     * @return  assertion for given exception
     */
    A getAssert(Throwable throwable);
}
