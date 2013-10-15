package pl.wkr.fluentrule.api;

import org.assertj.core.api.AbstractThrowableAssert;

abstract public class AssertCommand<A extends AbstractThrowableAssert<A,T>, T extends Throwable > {

    private A assertion;

    public void setAssert(A assertion) {
        this.assertion = assertion;
    }

    protected A getAssert() {
        return assertion;
    }

    public abstract void doCheck();
}
