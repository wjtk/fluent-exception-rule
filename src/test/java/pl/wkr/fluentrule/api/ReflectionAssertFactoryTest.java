package pl.wkr.fluentrule.api;

import org.junit.Test;
import pl.wkr.fluentrule.api.testutils.SQLExceptionAssert;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionAssertFactoryTest {

    @Test
    public void should_create_assert() {
        ReflectionAssertFactory<SQLExceptionAssert,SQLException> f =
                new ReflectionAssertFactory<SQLExceptionAssert,SQLException>(SQLExceptionAssert.class, SQLException.class);
        SQLExceptionAssert anAssert = f.getAssert(null);
        assertThat(anAssert).isNotNull();
    }
}
