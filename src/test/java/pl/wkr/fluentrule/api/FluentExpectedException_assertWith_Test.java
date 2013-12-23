package pl.wkr.fluentrule.api;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;
import pl.wkr.fluentrule.api.fluentexpectedexception_.BaseFluentExpectedExceptionTest;

import static org.mockito.Mockito.*;

public class FluentExpectedException_assertWith_Test extends BaseFluentExpectedExceptionTest{

    @Override
    protected FluentExpectedException before_createRule(FluentExpectedExceptionBuilder builder) {
        ReflectionAssertFactoryFactory raf = mock(ReflectionAssertFactoryFactory.class);
        when(raf.newAssertFactory(ThrowableAssert.class, Throwable.class)).thenReturn(throwableAssertFactoryMock);

        return builder.withReflectionAssertFactoryFactory(raf).build();
    }

    @Test
    public void should__assertWith__only_create_proxy_when() {
        setReturnedThrowableAssert( getFluentRule().assertWith(ThrowableAssert.class) );

        verifyNoMoreInteractions(register);
    }

}
