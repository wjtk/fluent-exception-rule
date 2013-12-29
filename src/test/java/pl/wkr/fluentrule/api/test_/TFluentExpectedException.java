package pl.wkr.fluentrule.api.test_;

import pl.wkr.fluentrule.api.FluentExpectedException;

public class TFluentExpectedException extends FluentExpectedException {

    public static TFluentExpectedException handlingAll() {
        return new TFluentExpectedException();
    }

    protected TFluentExpectedException() {
        handleAssertionErrors();
        handleAssumptionViolatedExceptions();
    }

    public void expectAnotherClass(final Class<? extends Throwable> expected,
                                   final Class<? extends Throwable> was) {

        FluentExpectedExceptionHelper.expectAnotherClass(
                expect(),
                expected,
                was
        );
    }
}
