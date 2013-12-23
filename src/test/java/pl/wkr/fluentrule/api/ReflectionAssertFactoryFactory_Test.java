package pl.wkr.fluentrule.api;

import org.junit.Test;
import pl.wkr.fluentrule.api.assertfactory_.BaseAssertFactoryTest;
import pl.wkr.fluentrule.api.test_.SQLExceptionAssert;

import java.sql.SQLException;

public class ReflectionAssertFactoryFactory_Test extends
        BaseAssertFactoryTest<SQLExceptionAssert, SQLException>{

    @Override
    protected AssertFactory<SQLExceptionAssert, SQLException> getFactory() {
        return new ReflectionAssertFactoryFactory().newAssertFactory(SQLExceptionAssert.class, SQLException.class);
    }

    @Test
    public void should_create_assert_for_exception() {
        SQLException expected = new SQLException();
        assertThatCreatesNotNullAssertAndItHasGivenActual(expected, expected);
    }
}
