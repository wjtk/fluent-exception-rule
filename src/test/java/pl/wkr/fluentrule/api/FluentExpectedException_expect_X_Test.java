package pl.wkr.fluentrule.api;

import org.junit.Test;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.api.exception_.OtherExc;
import pl.wkr.fluentrule.api.fluentexpectedexception_.BaseFluentExpectedExceptionTest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


public class FluentExpectedException_expect_X_Test extends BaseFluentExpectedExceptionTest {

    @Override
    protected FluentExpectedException before_createRule(FluentExpectedExceptionBuilder builder) {
        return builder.withThrowableAssertFactory(throwableAssertFactoryMock).build();
    }

    @Test
    public void should__expect__only_create_and_return_proxy() {
        setReturnedThrowableAssert( getFluentRule().expect() );

        verifyNoMoreInteractions(register);
    }

    @Test
    public void should__expect_Class__create_and_call_isInstanceOf_on_proxy() {
        setReturnedThrowableAssert( getFluentRule().expect(ExpectedExc.class) );

        verify(register).isInstanceOf(ExpectedExc.class);
        verifyNoMoreInteractions(register);
    }

    @Test
    public void should__expectAny_Classes__create_and_call_isInstanceOfAny_on_proxy() {
        setReturnedThrowableAssert( getFluentRule().expectAny(ExpectedExc.class, OtherExc.class) );

        verify(register).isInstanceOfAny(ExpectedExc.class, OtherExc.class);
        verifyNoMoreInteractions(register);
    }

}


