package pl.wkr.fluentrule.api.extending;

import org.assertj.core.api.AbstractThrowableAssert;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class SQLExceptionAssert extends AbstractThrowableAssert<SQLExceptionAssert, SQLException> {

    protected SQLExceptionAssert(SQLException actual) {
        super(actual, SQLExceptionAssert.class);
    }

    public SQLExceptionAssert hasErrorCode(int expected) {
        int actualCode = actual.getErrorCode();
        assertThat(actualCode).overridingErrorMessage("expected SQLException.errorCode <%d> but was <%s>.", expected, actualCode).isEqualTo(expected);
        return myself;
    }
}
