package pl.wkr.fluentrule.assertfactory;


import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.extractor.NoopExtractor;

public class ExtractingAssertFactoryFactory_Test extends BaseAssertFactoryTest<ThrowableAssert, Throwable>{


    @Override
    protected AssertFactory<ThrowableAssert, Throwable> getFactory() {
        return new ExtractingAssertFactoryFactory().newAssertFactory(new ThrowableAssertFactory(), new NoopExtractor());
    }

    @Test
    public void should_create_assert_for_exception() {
        ExpectedExc expected = new ExpectedExc();
        assertThatCreatesNotNullAssertAndItHasGivenActual(expected);
    }
}