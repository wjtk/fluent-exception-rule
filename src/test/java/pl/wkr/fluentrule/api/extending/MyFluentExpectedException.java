package pl.wkr.fluentrule.api.extending;

import pl.wkr.fluentrule.api.FluentExpectedException;

public class MyFluentExpectedException extends FluentExpectedException {

    public ExpectedSQLExceptionAssert expectSQLException() {
        return new ExpectedSQLExceptionAssert(getAssertCommandListCollector());
    }

    public ExpectedPatternSyntaxExceptionAssert expectPatternSyntaxException() {
        return new ExpectedPatternSyntaxExceptionAssert(getAssertCommandListCollector());
    }
}
