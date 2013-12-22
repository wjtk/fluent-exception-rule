package pl.wkr.fluentrule.api;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;
import pl.wkr.fluentrule.api.assertfactory_.BaseAssertFactoryTest;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;

public class ThrowableAssertFactoryTest extends BaseAssertFactoryTest<ThrowableAssert,Throwable> {

    @Override
    protected AssertFactory getFactory() {
        return new ThrowableAssertFactory();
    }

    @Test
    public void should_create_assert_for_exception() {
        Throwable expected = new ExpectedExc();
        assertThatCreatesNotNullAssertAndItHasGivenActual(expected, expected);
    }

}
