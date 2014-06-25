package pl.wkr.fluentrule.assertfactory;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;
import pl.wkr.fluentrule.test_.exception.ExpectedExc;

public class ReflectionAssertFactory_ThrowableAssert_Test extends BaseAssertFactoryTest<ThrowableAssert, Throwable> {

    @Override
    protected AssertFactory<ThrowableAssert, Throwable> getFactory() {
        return new ReflectionAssertFactory<ThrowableAssert, Throwable>(ThrowableAssert.class, Throwable.class);
    }

    @Test
    public void should_create_assert_for_exception() {
        Throwable expected = new ExpectedExc();
        assertThatCreatesNotNullAssertAndItHasGivenActual(expected);
    }
}
