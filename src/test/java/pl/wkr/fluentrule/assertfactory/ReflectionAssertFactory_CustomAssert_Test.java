package pl.wkr.fluentrule.assertfactory;

import org.junit.Test;
import pl.wkr.fluentrule.test_.exception.UnexpectedExc;
import pl.wkr.fluentrule.test_.SQLExceptionAssert;

import java.sql.SQLException;

public class ReflectionAssertFactory_CustomAssert_Test extends BaseAssertFactoryTest<SQLExceptionAssert, SQLException> {

    @Override
    protected AssertFactory<SQLExceptionAssert, SQLException> getFactory() {
        return new ReflectionAssertFactory<SQLExceptionAssert, SQLException>(SQLExceptionAssert.class, SQLException.class);
    }

    @Test
    public void should_create_assert_for_exception() {
        SQLException expected = new SQLException();
        assertThatCreatesNotNullAssertAndItHasGivenActual(expected);
    }

    @Test
    public void should_throw_that_unexpected_type() {
        UnexpectedExc exc = new UnexpectedExc();

        thrown.expectAnotherClass(SQLException.class, UnexpectedExc.class);
        factory.getAssert(exc);
    }


}
