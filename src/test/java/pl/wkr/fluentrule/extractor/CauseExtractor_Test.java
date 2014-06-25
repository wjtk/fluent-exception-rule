package pl.wkr.fluentrule.extractor;

import pl.wkr.fluentrule.test_.exception.ExpectedExc;
import pl.wkr.fluentrule.test_.exception.UnexpectedExc;

import static junitparams.JUnitParamsRunner.$;


public class CauseExtractor_Test extends BaseExtractor_Test {

    private CauseExtractor extractor = new CauseExtractor();

    @Override
    protected ThrowableExtractor getExtractor() {
        return extractor;
    }


    @SuppressWarnings("unused")
    protected Object[] data() {
        ExpectedExc expected1 = new ExpectedExc();
        ExpectedExc expected2 = new ExpectedExc(new UnexpectedExc());
        return $(
                $(null, null, "null, should return null"),
                $(new UnexpectedExc(), null, "no cause, should return null"),
                $(new UnexpectedExc(expected1), expected1, "with cause, should return cause"),
                $(new UnexpectedExc(expected2), expected2, "with cause - longer chain, should return first cause")
        );
    }
}
