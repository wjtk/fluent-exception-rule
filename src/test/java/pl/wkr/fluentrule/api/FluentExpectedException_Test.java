package pl.wkr.fluentrule.api;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FluentExpectedException_Test {

    @Test
    public void should_expectXXX_and_withAssert_methods_return_assert_proxy_not_null() {
        Class<ThrowableAssert> tac = ThrowableAssert.class;
        Class<Throwable> tc = Throwable.class;
        FluentExpectedException rule = FluentExpectedException.none();

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(rule.expect()               ).as("expect()").isInstanceOf(tac);
        soft.assertThat(rule.expect(tc)             ).as("expect(Class)").isInstanceOf(tac);
        soft.assertThat(rule.expectAny(tc)          ).as("expectAny(Class...)").isInstanceOf(tac);
        soft.assertThat(rule.expectCause()          ).as("expectCause()").isInstanceOf(tac);
        soft.assertThat(rule.expectCause(tc)        ).as("expectCause(Class)").isInstanceOf(tac);
        soft.assertThat(rule.expectRootCause()      ).as("expectRootCause()").isInstanceOf(tac);
        soft.assertThat(rule.expectRootCause(tc)    ).as("expectRootCause(Class)").isInstanceOf(tac);
        soft.assertThat(rule.assertWith(tac)        ).as("assertWith(AssertClass)").isInstanceOf(tac);
        soft.assertAll();
    }

    @Test
    public void should_return_when_expecting_exception() {
        FluentExpectedException rule = FluentExpectedException.none();

        assertThat(rule.isExceptionExpected()).isFalse();
        rule.expect();
        assertThat(rule.isExceptionExpected()).isTrue();
    }
}
