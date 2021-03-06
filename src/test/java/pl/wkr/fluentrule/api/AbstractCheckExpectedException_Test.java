package pl.wkr.fluentrule.api;

import org.junit.Test;
import pl.wkr.fluentrule.api.check.Check;
import pl.wkr.fluentrule.test_.BaseWithFluentThrownTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;


public class AbstractCheckExpectedException_Test extends BaseWithFluentThrownTest {

    private TestConcreteCheckExpectedException testCEE = new TestConcreteCheckExpectedException();

    @Test
    public void should_not_expect_exception_after_creation() {
        assertThat(testCEE.isExceptionExpected()).isFalse();
    }

    @Test
    public void should_expect_exception_when_check_was_added() {
        testCEE.addCheck(mock(Check.class));
        assertThat(testCEE.isExceptionExpected()).isTrue();
    }

    @Test
    public void should_throw_assertion_error() {
        thrown.expect(AssertionError.class).hasMessage("Exception was expected but was not thrown");
        testCEE.failBecauseExceptionWasNotThrown();

    }

    @Test
    public void should_throw_when_check_argument_is_null() {
        thrown.expect(NullPointerException.class).hasMessage("check");
        testCEE.addCheck(null);
    }


    static class TestConcreteCheckExpectedException extends AbstractCheckExpectedException {
    }
}
