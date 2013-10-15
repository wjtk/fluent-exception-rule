package pl.wkr.fluentrule.api.extending;

import pl.wkr.fluentrule.api.AbstractExpectedThrowableAssert;
import pl.wkr.fluentrule.api.AssertCommand;
import pl.wkr.fluentrule.api.AssertCommandListCollector;

import java.sql.SQLException;

public class ExpectedSQLExceptionAssert
        extends AbstractExpectedThrowableAssert<ExpectedSQLExceptionAssert, SQLException, SQLExceptionAssert> {

    public ExpectedSQLExceptionAssert(AssertCommandListCollector aacl) {
        super(ExpectedSQLExceptionAssert.class, aacl);
    }

    @Override
    protected SQLExceptionAssert produceAssert(SQLException throwable) {
        return new SQLExceptionAssert(throwable);
    }

    public ExpectedSQLExceptionAssert hasErrorCode(final int expected) {
        addCommand(new AssertCommand<SQLExceptionAssert, SQLException>() {
            @Override
            public void doCheck() {
                getAssert().hasErrorCode(expected);
            }
        });
        return myself;
    }
}

