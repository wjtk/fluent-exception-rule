package pl.wkr.fluentrule.assertfactory;

import org.junit.Test;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.api.exception_.UnexpectedExc;
import pl.wkr.fluentrule.api.test_.BaseWithFluentThrownTest;

import static org.assertj.core.api.Assertions.assertThat;

public class RootCauseExtractor_Test extends BaseWithFluentThrownTest {

    private RootCauseExtractor extractor = new RootCauseExtractor();

    @Test
    public void should_create_assert_for_root_cause() {
        Throwable expected;
        Throwable fromExc = new UnexpectedExc(expected = new ExpectedExc());

        assertThat(extractor.extract(fromExc)).isSameAs(expected);
    }

    @Test
    public void should_create_assert_for_root_cause_longer_exceptions_chain() {
        Throwable expected;
        Throwable fromExc = new UnexpectedExc(new UnexpectedExc(new UnexpectedExc(expected = new ExpectedExc())));

        assertThat(extractor.extract(fromExc)).isSameAs(expected);
    }

    @Test
    public void should_throw_assertion_that_exception_has_no_cause() {
        thrown.expect().hasMessageContaining("but current throwable has no cause");

        extractor.extract(new UnexpectedExc());
    }

}
