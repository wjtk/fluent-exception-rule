package pl.wkr.fluentrule.api.check;

import org.junit.Test;
import pl.wkr.fluentrule.api.check.SafeCheck;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.api.exception_.UnexpectedExc;
import pl.wkr.fluentrule.api.test_.BaseWithFluentThrownTest;

import static org.assertj.core.api.Assertions.assertThat;

public class SafeCheck_Test extends BaseWithFluentThrownTest{

    private TestSafeCheck<ExpectedExc> noArgSafeCheck = new TestSafeCheck<ExpectedExc>(){};
    private TestSafeCheck<ExpectedExc> typeInCstrArgSafeCheck = new TestSafeCheck<ExpectedExc>(ExpectedExc.class){};


    @Test
    public void should_pass_exception_of_expected_type_to_check() {
        ExpectedExc expected = new ExpectedExc();

        noArgSafeCheck.check(expected);

        assertThat(noArgSafeCheck.exception).isSameAs(expected);
    }

    @Test
    public void should_throw_because_unexpected_exception_type() {
        UnexpectedExc un = new UnexpectedExc();

        thrown.expect(AssertionError.class);
        noArgSafeCheck.check(un);
    }

    @Test
    public void should_pass_exception_of_expected_type_to_check__type_in_constructor_arg() {
        ExpectedExc expected = new ExpectedExc();

        typeInCstrArgSafeCheck.check(expected);

        assertThat(typeInCstrArgSafeCheck.exception).isSameAs(expected);
    }



    //------------------------------------------

    static  abstract class TestSafeCheck<T extends Throwable> extends SafeCheck<T> {
        public T exception = null;

        protected TestSafeCheck(Class<T> expectedType) {
            super(expectedType);
        }

        public TestSafeCheck() {
        }

        @Override
        protected void safeCheck(T exception) {
            this.exception = exception;
        }
    }
}
