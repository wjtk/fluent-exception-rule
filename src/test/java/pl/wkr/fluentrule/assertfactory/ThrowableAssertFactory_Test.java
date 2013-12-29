package pl.wkr.fluentrule.assertfactory;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;

public class ThrowableAssertFactory_Test extends BaseAssertFactoryTest<ThrowableAssert, Throwable> {

    @Override
    protected AssertFactory<ThrowableAssert, Throwable> getFactory() {
        return new ThrowableAssertFactory();
    }

    @Test
    public void should_create_assert_for_exception() {
        Exception expected = new Exception();
        assertThatCreatesNotNullAssertAndItHasGivenActual(expected);
    }
}
