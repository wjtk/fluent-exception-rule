package pl.wkr.fluentrule.api;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;

public class ExpectedThrowableCauseAssert extends ExpectedThrowableAssert {

    protected ExpectedThrowableCauseAssert(AssertCommandListCollector aacl) {
        super(aacl);
    }

    @Override
    protected ThrowableAssert produceAssert(Throwable throwable) {
        return Assertions.assertThat(throwable.getCause());
    }
}
