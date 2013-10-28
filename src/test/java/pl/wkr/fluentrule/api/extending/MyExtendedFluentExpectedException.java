package pl.wkr.fluentrule.api.extending;

import pl.wkr.fluentrule.api.FluentExpectedException;

import java.sql.SQLException;

public class MyExtendedFluentExpectedException extends FluentExpectedException {

    public SQLExceptionAssert expectSQLException() {
        return newProxy(SQLExceptionAssert.class, SQLException.class);
    }
}
