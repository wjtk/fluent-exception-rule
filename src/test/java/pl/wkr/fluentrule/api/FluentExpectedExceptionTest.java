package pl.wkr.fluentrule.api;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import pl.wkr.fluentrule.api.testutils.MyException;

public class FluentExpectedExceptionTest {

    @Test
    public void should_expectXXX_and_withAssert_methods_return_not_null() {
        FluentExpectedException rule = FluentExpectedException.none();
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(rule.expect()).as("expect()").isNotNull();
        soft.assertThat(rule.expect(MyException.class)).as("expect(Class)").isNotNull();
        soft.assertThat(rule.expectAny(MyException.class)).as("expectAny(Class...)").isNotNull();
        soft.assertThat(rule.expectCause()).as("expectCause()").isNotNull();
        soft.assertThat(rule.expectRootCause()).as("expectRootCause()").isNotNull();
        soft.assertAll();
    }
}
