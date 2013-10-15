package pl.wkr.fluentrule.api.extending;

import org.assertj.core.api.AbstractThrowableAssert;

import java.util.regex.PatternSyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class PatternSyntaxExceptionAssert
        extends AbstractThrowableAssert<PatternSyntaxExceptionAssert,PatternSyntaxException> {

    protected PatternSyntaxExceptionAssert(PatternSyntaxException actual) {
        super(actual, PatternSyntaxException.class);
    }

    public PatternSyntaxExceptionAssert hasPattern(String expected) {
        assertThat(actual.getPattern())
                .overridingErrorMessage("expected PatternSyntaxException.pattern <%s> but was <%s>", expected, actual)
                .isEqualTo(expected);
        return myself;
    }
}
