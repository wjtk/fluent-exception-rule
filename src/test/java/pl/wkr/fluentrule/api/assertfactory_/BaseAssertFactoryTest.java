package pl.wkr.fluentrule.api.assertfactory_;

import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.Before;
import pl.wkr.fluentrule.api.AssertFactory;
import pl.wkr.fluentrule.api.test_.BaseFluentThrownTest;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseAssertFactoryTest<A extends AbstractThrowableAssert<A,T>, T extends Throwable>
        extends BaseFluentThrownTest {

    protected AssertFactory<A,T> factory;

    @Before
    public void baseBefore() {
        factory = getFactory();
    }

    abstract protected AssertFactory<A,T> getFactory();


    protected final A assertThatCreatesNotNullAssertAndItHasGivenActual(T exception, T expectedActual) {
        A anAssert = factory.getAssert(exception);

        assertThat(anAssert).isNotNull();
        anAssert.isSameAs(expectedActual);
        return anAssert;
    }

}
