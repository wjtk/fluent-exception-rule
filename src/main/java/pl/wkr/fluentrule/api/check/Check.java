package pl.wkr.fluentrule.api.check;

/**
 * Interface of callback asserting thrown exception.
 * Fallback should check if thrown exception was expected,
 * and fulfils expectations. If this is not true, AssertionError
 * should be thrown. Commonly used in conjunction with Assertj assertions in callback's body.
 * <p/>
 *
 *
 * @see
 *      pl.wkr.fluentrule.api.CheckExpectedException
 *
 */
public interface Check {

    /**
     * This method is called when exception is thrown.
     *
     * @param exception thrown exception
     */
    void check(Throwable exception);
}
