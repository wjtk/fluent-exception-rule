package pl.wkr.fluentrule.api.test_;

import org.assertj.core.api.ThrowableAssert;

public class FluentExpectedExceptionHelper {
    public static final String WAS_NOT_THROWN_MESSAGE = "Exception was expected but was not thrown";

    public static void expectAnotherClass(
            ThrowableAssert anAssert,
            final Class<? extends Throwable> expected,
            final Class<? extends Throwable> was) {

        anAssert.isInstanceOf(AssertionError.class)
                .hasMessageContaining(expected.getName())
                .hasMessageContaining(was.getName())
                .hasMessageContaining("but was instance of:")
                .hasMessageContaining("to be an instance of:");

    }
}
