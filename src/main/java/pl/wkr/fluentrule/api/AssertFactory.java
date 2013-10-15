package pl.wkr.fluentrule.api;

import org.assertj.core.api.AbstractThrowableAssert;

abstract class AssertFactory<A extends AbstractThrowableAssert<A,T>, T extends Throwable> {
    abstract A getAssert(T throwable);
}
