package pl.wkr.fluentrule.api.testutils;

public class ThrowableAssertMock extends AbstractThrowableAssertMock<ThrowableAssertMock, Throwable> {

    public ThrowableAssertMock(Throwable actual, ThrowableAssertMockRegister register ) {
        super(actual, ThrowableAssertMock.class, register);
    }
}
