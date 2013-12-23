package pl.wkr.fluentrule.api;

import org.junit.Test;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.api.fluentexpectedexception_.BaseFluentExpectedExceptionTest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class FluentExpectedException_expectCause_X_Test extends BaseFluentExpectedExceptionTest {

    @Override
    protected FluentExpectedException before_createRule(FluentExpectedExceptionBuilder builder) {
        return builder.withCauseAssertFactory(throwableAssertFactoryMock).build();
    }

    @Test
    public void should_should__expectCause__only_create_and_return_proxy() {
        setReturnedThrowableAssert( getFluentRule().expectCause() );

        verifyNoMoreInteractions(register);
    }

    @Test
    public void should_should__expectCause_Class__create_and_call_isInstanceOf_on_proxy() {
        setReturnedThrowableAssert( getFluentRule().expectCause(ExpectedExc.class) );

        verify(register).isInstanceOf(ExpectedExc.class);
        verifyNoMoreInteractions(register);
    }
}
