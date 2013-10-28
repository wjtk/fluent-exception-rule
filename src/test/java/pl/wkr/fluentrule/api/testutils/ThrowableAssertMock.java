package pl.wkr.fluentrule.api.testutils;

import static org.mockito.Mockito.mock;

public class ThrowableAssertMock extends AbstractThrowableAssertMock<
        ThrowableAssertMock, Throwable, ThrowableAssertMockRegister> {

    public ThrowableAssertMock(Throwable actual) {
        super(actual, ThrowableAssertMock.class, mock(ThrowableAssertMockRegister.class));
    }

}
