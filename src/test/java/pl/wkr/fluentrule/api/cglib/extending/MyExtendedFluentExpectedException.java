package pl.wkr.fluentrule.api.cglib.extending;

import pl.wkr.fluentrule.api.cglib.FluentExpectedException;
import pl.wkr.fluentrule.api.extending.SQLExceptionAssert;

import java.sql.SQLException;

public class MyExtendedFluentExpectedException extends FluentExpectedException {

    public SQLExceptionAssert expectSQLException() {
        return newProxy(SQLExceptionAssert.class, SQLException.class);
    }
}
