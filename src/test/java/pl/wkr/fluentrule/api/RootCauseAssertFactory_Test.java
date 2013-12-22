package pl.wkr.fluentrule.api;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;
import pl.wkr.fluentrule.api.assertfactory_.BaseCauseAssertFactoryTest;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.api.exception_.UnexpectedExc;

public class RootCauseAssertFactory_Test extends BaseCauseAssertFactoryTest {

    @Override
    protected AssertFactory<ThrowableAssert, Throwable> getFactory() {
        return new RootCauseAssertFactory();
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
