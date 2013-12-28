package pl.wkr.fluentrule.assertfactory;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;
import pl.wkr.fluentrule.api.assertfactory_.BaseCauseAssertFactoryTest;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.api.exception_.UnexpectedExc;
import pl.wkr.fluentrule.assertfactory.AssertFactory;

public class CauseExtractor_Test extends BaseCauseAssertFactoryTest {

    @Override
    protected AssertFactory<ThrowableAssert,Throwable> getFactory() {
        return new CauseAssertFactory();
    }

    @Test
    public void should_create_assert_for_cause() {
        Throwable expected;
        Throwable fromExc = new UnexpectedExc(expected = new ExpectedExc());

        assertThatCreatesNotNullAssertAndItHasGivenActual(fromExc, expected);
    }

        @Test
    public void should_create_assert_for_cause_longer_chain() {
        Throwable expected;
        Throwable fromExc = new UnexpectedExc(expected = new ExpectedExc(new UnexpectedExc()));

        assertThatCreatesNotNullAssertAndItHasGivenActual(fromExc, expected);
    }
}
