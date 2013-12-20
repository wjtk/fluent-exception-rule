package pl.wkr.fluentrule.api;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;

class CauseAssertFactory implements AssertFactory<ThrowableAssert,Throwable> {

    @Override
    public ThrowableAssert getAssert(Throwable throwable) {
        Throwable cause = throwable.getCause();
        //TODO this should be here?
        if( cause == null){
            throw new AssertionError("Expecting a throwable with cause, but current throwable has no cause");
        }
        return Assertions.assertThat(cause);
    }
}
