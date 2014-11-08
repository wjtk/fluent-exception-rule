package pl.wkr.fluentrule.util;

import org.assertj.core.api.ThrowableAssert;

public class InternalAssertions {

    public static ThrowableAssert assertThat(Throwable throwable) {
        return new ThrowableAssertSub(throwable);
    }

    private static class ThrowableAssertSub extends ThrowableAssert {
        public ThrowableAssertSub(Throwable actual) {
            super(actual);
        }
    }

}
