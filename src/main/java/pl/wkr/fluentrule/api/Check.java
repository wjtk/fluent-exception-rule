package pl.wkr.fluentrule.api;

/**
 * Interface of callback asserting thrown exception.
 */
public interface Check {

    /**
     * This method is called when exception is thrown.
     *
     * @param exception thrown exception
     */
    void check(Throwable exception);
}
