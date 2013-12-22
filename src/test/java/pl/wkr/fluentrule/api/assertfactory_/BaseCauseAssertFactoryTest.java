package pl.wkr.fluentrule.api.assertfactory_;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;
import pl.wkr.fluentrule.api.exception_.UnexpectedExc;

public abstract class BaseCauseAssertFactoryTest
        extends BaseAssertFactoryTest<ThrowableAssert,Throwable> {

    @Test
    public void should_throw_assertion_that_exception_has_no_cause() {
        thrown.expect().hasMessageContaining("but current throwable has no cause");

        factory.getAssert(new UnexpectedExc());
    }
}
