package pl.wkr.fluentrule.assertfactory;

import org.assertj.core.api.ThrowableAssert;

import static pl.wkr.fluentrule.util.InternalAssertions.assertThat;

public class ThrowableAssertFactory implements AssertFactory<ThrowableAssert, Throwable> {

    @Override
    public ThrowableAssert getAssert(Throwable throwable) {
        return assertThat(throwable);
    }
}
