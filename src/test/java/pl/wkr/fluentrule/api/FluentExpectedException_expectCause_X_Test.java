package pl.wkr.fluentrule.api;

import org.junit.Before;
import org.junit.Test;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.api.fluentexpectedexception_.BaseFluentExpectedExceptionTest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class FluentExpectedException_expectCause_X_Test extends BaseFluentExpectedExceptionTest {

    @Before
    public void before() {
        fluentRule = new FluentExpectedException(proxyFactory, null, throwableAssertFactoryMock, null, null);
    }


    @Test
    public void should_should__expectCause__only_create_and_return_proxy() {
        returnedProxy = fluentRule.expectCause();

        verifyNoMoreInteractions(register);
    }

    @Test
    public void should_should__expectCause_Class__create_and_call_isInstanceOf_on_proxy() {
        returnedProxy = fluentRule.expectCause(ExpectedExc.class);

        verify(register).isInstanceOf(ExpectedExc.class);
        verifyNoMoreInteractions(register);
    }
}
