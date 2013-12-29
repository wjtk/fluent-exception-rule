package pl.wkr.fluentrule.api.test_;

import org.assertj.core.api.ThrowableAssert;
import pl.wkr.fluentrule.api.Check;
import pl.wkr.fluentrule.api.CheckExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class TCheckExpectedException extends CheckExpectedException {

    public static TCheckExpectedException handlingAll() {
        return new TCheckExpectedException();
    }

    protected TCheckExpectedException() {
        handleAssertionErrors();
        handleAssumptionViolatedExceptions();
    }

    public void expect(final Class<?> type) {
        check(new Check() {
            @Override
            public void check(Throwable exception) {
                assertThat(exception).isInstanceOf(type);
            }
        });
    }

    public void expectMessageContaining(final String ... messages) {
        check(new Check() {
            @Override
            public void check(Throwable exception) {
                ThrowableAssert throwableAssert = assertThat(exception);
                for (String message : messages) {
                    throwableAssert.hasMessageContaining(message);
                }
            }
        });
    }

    public void expectAnotherClass(final Class<? extends Throwable> expected,
                                   final Class<? extends Throwable> was) {
        check(new Check() {
            @Override
            public void check(Throwable exception) {
                FluentExpectedExceptionHelper.expectAnotherClass(
                    assertThat(exception),
                    expected,
                    was
                );
            }
        });
    }

    public void expectIsSame(final Throwable expected) {
        check(new Check() {
            @Override
            public void check(Throwable exception) {
                assertThat(exception).isSameAs(expected);
            }
        });
    }

}
