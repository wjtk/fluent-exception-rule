package pl.wkr.fluentrule.api;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Before;
import org.junit.Test;
import pl.wkr.fluentrule.api.fluentexpectedexception_.BaseFluentExpectedExceptionTest;

import static org.mockito.Mockito.*;

public class FluentExpectedException_assertWith_Test extends BaseFluentExpectedExceptionTest{

    @Before
    public void before() {
        ReflectionAssertFactoryFactory raf = mock(ReflectionAssertFactoryFactory.class);
        when(raf.newAssertFactory(ThrowableAssert.class, Throwable.class)).thenReturn(throwableAssertFactoryMock);

        fluentRule = new FluentExpectedException(proxyFactory, null, null, null, raf );
    }

    @Test
    public void should__assertWith__only_create_proxy_when() {
        returnedProxy = fluentRule.assertWith(ThrowableAssert.class);

        verifyNoMoreInteractions(register);
    }

}
