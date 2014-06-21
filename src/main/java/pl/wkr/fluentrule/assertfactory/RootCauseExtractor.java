package pl.wkr.fluentrule.assertfactory;

import static org.assertj.core.util.Preconditions.checkNotNull;

class RootCauseExtractor implements ThrowableExtractor {

    @Override
    public Throwable extract(Throwable throwable) {
        checkNotNull(throwable, "throwable");
        Throwable rootCause = throwable.getCause();
        if( rootCause != null) {
            while(rootCause.getCause() != null) {
                rootCause = rootCause.getCause();
            }
        }
        if( rootCause == null){
            throw new AssertionError("Expecting a throwable with root cause, but current throwable has no cause");
        }
        return rootCause;
    }
}
