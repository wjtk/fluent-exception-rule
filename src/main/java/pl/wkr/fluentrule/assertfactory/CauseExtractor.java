package pl.wkr.fluentrule.assertfactory;

import static org.assertj.core.util.Preconditions.checkNotNull;

class CauseExtractor implements ThrowableExtractor {

    @Override
    public Throwable extract(Throwable throwable) {
        checkNotNull(throwable, "throwable");
        Throwable cause = throwable.getCause();
        //TODO this should be here?
        if( cause == null){
            throw new AssertionError("Expecting a throwable with cause, but current throwable has no cause");
        }
        return cause;
    }
}
