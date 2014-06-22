package pl.wkr.fluentrule.extractor;

import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.api.exception_.UnexpectedExc;

import static junitparams.JUnitParamsRunner.$;


public class RootCauseExtractor_Test extends BaseExtractor_Test {

    private RootCauseExtractor extractor = new RootCauseExtractor();

    @Override
    protected ThrowableExtractor getExtractor() {
        return extractor;
    }

    protected Object[] data() {
        ExpectedExc expected = new ExpectedExc();

        return $(
            $(null, null, "null, should return null"),
            $(new UnexpectedExc(), null, "no cause, should return null"),
            $(new UnexpectedExc(expected), expected, "with cause, should return cause"),
            $(new UnexpectedExc(new UnexpectedExc(expected)), expected, "with cause - longer chain, should return root cause")
        );
    }



}
