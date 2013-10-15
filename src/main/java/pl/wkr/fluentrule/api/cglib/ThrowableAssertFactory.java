package pl.wkr.fluentrule.api.cglib;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;

public class ThrowableAssertFactory extends AssertFactory<ThrowableAssert,Throwable>{

    @Override
    ThrowableAssert getAssert(Throwable throwable) {
        return Assertions.assertThat(throwable);
    }
}
