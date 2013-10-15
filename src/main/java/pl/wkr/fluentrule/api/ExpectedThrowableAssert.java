package pl.wkr.fluentrule.api;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;

public class ExpectedThrowableAssert extends AbstractExpectedThrowableAssert<ExpectedThrowableAssert, Throwable, ThrowableAssert> {

    protected ExpectedThrowableAssert(AssertCommandListCollector aacl) {
        super(ExpectedThrowableAssert.class, aacl);
    }

    @Override
    protected ThrowableAssert produceAssert(Throwable throwable) {
        return Assertions.assertThat(throwable);
    }
}
