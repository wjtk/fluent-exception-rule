package pl.wkr.fluentrule.extractor;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.wkr.fluentrule.api.CheckExpectedException;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.api.test_.BaseWithFluentThrownTest;

import static junitparams.JUnitParamsRunner.$;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class CheckNotNullExtractor_Test extends BaseWithFluentThrownTest {

    @Rule
    public CheckExpectedException thrown2 = CheckExpectedException.none();

    @SuppressWarnings("unused")
    private Object[] constructorArgs() {
        return $(
            $(null, "", "innerExtractor"),
            $(mock(ThrowableExtractor.class), null, "message")
        );
    }

    @Parameters(method = "constructorArgs")
    @Test
    public void should_throw_when_constructor_arguments_are_null(
            ThrowableExtractor innerExtractor, String message, String exceptionMessage) {

        thrown.expect(NullPointerException.class).hasMessage(exceptionMessage);
        new CheckNotNullExtractor(innerExtractor, message);
    }


    @Test
    public void should_simply_return_exception_from_innerExtractor_when_is_not_null() {
        ExpectedExc fromExc = new ExpectedExc();
        ExpectedExc extracted = new ExpectedExc();
        ThrowableExtractor innerExtractor = mock(ThrowableExtractor.class);

        when(innerExtractor.extract(fromExc)).thenReturn(extracted);
        CheckNotNullExtractor extractor = new CheckNotNullExtractor(innerExtractor, "");

        assertThat(extractor.extract(fromExc)).isSameAs(extracted);

    }

    @Test
    public void should_throw_when_innerExtractor_returns_null() {
        final ExpectedExc fromExc = new ExpectedExc();
        final ThrowableExtractor innerExtractor = mock(ThrowableExtractor.class);

        ThrowableExtractor extractor = new CheckNotNullExtractor(innerExtractor, "m");

        thrown.expect(AssertionError.class).hasMessage("m");
        extractor.extract(fromExc);
    }

}