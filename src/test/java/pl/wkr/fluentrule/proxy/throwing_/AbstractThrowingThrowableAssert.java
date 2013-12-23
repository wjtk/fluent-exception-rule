package pl.wkr.fluentrule.proxy.throwing_;

import org.assertj.core.api.AbstractThrowableAssert;

public class AbstractThrowingThrowableAssert<S extends AbstractThrowableAssert<S,A>, A extends Throwable>
                extends AbstractThrowableAssert<S,A> {

    protected AbstractThrowingThrowableAssert(A actual, Class<?> selfType) {
        super(actual, selfType);
    }

    public S throwThis(Throwable e) throws Throwable {
        throw e;
    }

    S illegalAccess() {
        return myself;
    }

}
