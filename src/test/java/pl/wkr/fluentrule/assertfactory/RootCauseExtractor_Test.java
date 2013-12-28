package pl.wkr.fluentrule.assertfactory;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;
import pl.wkr.fluentrule.assertfactory_.BaseCauseThrowableExtractorTest;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.api.exception_.UnexpectedExc;
import pl.wkr.fluentrule.assertfactory.AssertFactory;
import pl.wkr.fluentrule.assertfactory.RootCauseExtractor;

public class RootCauseExtractor_Test extends BaseCauseThrowableExtractorTest {

    @Override
    protected AssertFactory<ThrowableAssert, Throwable> getFactory() {
        return new RootCauseExtractor();
    }

    @Test
    public void should_create_assert_for_root_cause() {
        Throwable expected;
        Throwable fromExc = new UnexpectedExc(expected = new ExpectedExc());

        assertThatCreatesNotNullAssertAndItHasGivenActual(fromExc, expected);
    }

    @Test
    public void should_create_assert_for_root_cause_longer_exceptions_chain() {
        Throwable expected;
        Throwable fromExc = new UnexpectedExc(new UnexpectedExc(new UnexpectedExc(expected = new ExpectedExc())));

        assertThatCreatesNotNullAssertAndItHasGivenActual(fromExc, expected);
    }

}
