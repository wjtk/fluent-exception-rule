package pl.wkr.fluentrule.api;

import org.assertj.core.api.AbstractThrowableAssert;

public  interface AssertFactory<A extends AbstractThrowableAssert<A,T>, T extends Throwable> {
    A getAssert(T throwable);
}
