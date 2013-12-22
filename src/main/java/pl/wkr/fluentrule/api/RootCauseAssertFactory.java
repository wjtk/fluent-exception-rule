package pl.wkr.fluentrule.api;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;

class RootCauseAssertFactory implements AssertFactory<ThrowableAssert,Throwable> {

    @Override
    public ThrowableAssert getAssert(Throwable throwable) {
        Throwable rootCause = throwable.getCause();
        if( rootCause != null) {
            while(rootCause.getCause() != null) {
                rootCause = rootCause.getCause();
            }
        }
        //TODO this should be here?
        if( rootCause == null){
            throw new AssertionError("Expecting a throwable with root cause, but current throwable has no cause");
        }
        return Assertions.assertThat(rootCause);
    }
}
