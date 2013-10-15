package pl.wkr.fluentrule.api.extending;

import pl.wkr.fluentrule.api.AbstractExpectedThrowableAssert;
import pl.wkr.fluentrule.api.AssertCommandListCollector;

import java.util.regex.PatternSyntaxException;

public class ExpectedPatternSyntaxExceptionAssert extends
        AbstractExpectedThrowableAssert<ExpectedPatternSyntaxExceptionAssert, PatternSyntaxException, PatternSyntaxExceptionAssert>
{

    public ExpectedPatternSyntaxExceptionAssert(AssertCommandListCollector addACL) {
        super(ExpectedPatternSyntaxExceptionAssert.class, addACL);
    }

    @Override
    protected PatternSyntaxExceptionAssert produceAssert(PatternSyntaxException exception) {
        return new PatternSyntaxExceptionAssert(exception);
    }
}
