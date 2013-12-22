package pl.wkr.fluentrule.api;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;
import pl.wkr.fluentrule.api.testutils.SQLExceptionAssert;

public class FluentExpectedExceptionTest {

    @Test
    public void should_expectXXX_and_withAssert_methods_return_assert_proxy_not_null() {
        Class<?> tac = ThrowableAssert.class;
        Class<Throwable> tc = Throwable.class;
        FluentExpectedException rule = FluentExpectedException.none();

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(rule.expect())              .as("expect()").isInstanceOf(tac);
        soft.assertThat(rule.expect(tc))            .as("expect(Class)").isInstanceOf(tac);
        soft.assertThat(rule.expectAny(tc))         .as("expectAny(Class...)").isInstanceOf(tac);
        soft.assertThat(rule.expectCause())         .as("expectCause()").isInstanceOf(tac);
        soft.assertThat(rule.expectRootCause())     .as("expectRootCause()").isInstanceOf(tac);
        soft.assertThat(rule.assertWith(SQLExceptionAssert.class)).as("assertWith(AssertClass)").isInstanceOf(SQLExceptionAssert.class);
        soft.assertAll();
    }
}
