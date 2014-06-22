package pl.wkr.fluentrule.assertfactory;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;

public class ThrowableAssertFactory implements AssertFactory<ThrowableAssert, Throwable> {

    @Override
    public ThrowableAssert getAssert(Throwable throwable) {
        return Assertions.assertThat(throwable);
    }
}
