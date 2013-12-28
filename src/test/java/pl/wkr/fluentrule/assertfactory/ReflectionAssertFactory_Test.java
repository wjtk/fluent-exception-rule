package pl.wkr.fluentrule.assertfactory;

import org.junit.Test;
import pl.wkr.fluentrule.api.assertfactory_.BaseAssertFactoryTest;
import pl.wkr.fluentrule.api.test_.SQLExceptionAssert;
import pl.wkr.fluentrule.assertfactory.AssertFactory;
import pl.wkr.fluentrule.assertfactory.ReflectionAssertFactory;

import java.sql.SQLException;

public class ReflectionAssertFactory_Test extends BaseAssertFactoryTest<SQLExceptionAssert,SQLException>{

    @Override
    protected AssertFactory<SQLExceptionAssert, SQLException> getFactory() {
        return new ReflectionAssertFactory<SQLExceptionAssert,SQLException>(SQLExceptionAssert.class, SQLException.class);
    }

    @Test
    public void should_create_assert_for_exception() {
        SQLException expected = new SQLException();
        assertThatCreatesNotNullAssertAndItHasGivenActual(expected, expected);
    }
}
