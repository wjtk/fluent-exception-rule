package pl.wkr.fluentrule.proxy.throwing;

import org.assertj.core.api.AbstractThrowableAssert;

public class AbstractThrowingThrowableAssert<S extends AbstractThrowableAssert<S,A>, A extends Throwable>
                extends AbstractThrowableAssert<S,A> {

    protected AbstractThrowingThrowableAssert(A actual, Class<?> selfType) {
        super(actual, selfType);
    }

    public S throwException(Exception e) throws Exception {
        throw e;
    }

    S illegalAccess() {
        return myself;
    }

}
