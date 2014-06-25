package pl.wkr.fluentrule.extractor;

import pl.wkr.fluentrule.test_.exception.ExpectedExc;
import pl.wkr.fluentrule.test_.exception.UnexpectedExc;

import static junitparams.JUnitParamsRunner.$;


public class NoopExtractor_Test extends BaseExtractor_Test {

    private NoopExtractor extractor = new NoopExtractor();

    @Override
    protected ThrowableExtractor getExtractor() {
        return extractor;
    }

    protected Object[] data() {
        ExpectedExc expected1 = new ExpectedExc();
        ExpectedExc expected2 = new ExpectedExc(new UnexpectedExc());
        return $(
                $(null, null, "null, should return null"),
                $(expected1, expected1, "no cause, should return input exception"),
                $(expected2, expected2, "with cause, should return input exception")
        );
    }
}
