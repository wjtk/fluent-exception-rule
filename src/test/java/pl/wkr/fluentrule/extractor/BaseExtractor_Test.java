package pl.wkr.fluentrule.extractor;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.wkr.fluentrule.api.test_.BaseWithFluentThrownTest;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public abstract class BaseExtractor_Test extends BaseWithFluentThrownTest {

    @Parameters(method = "data")
    @Test
    public void should_extract_throwable(Throwable fromExc, Throwable expected, String description) {
        assertThat(getExtractor().extract(fromExc)).describedAs(description).isSameAs(expected);
    }

    @SuppressWarnings("unused")
    protected abstract Object[] data();

    protected abstract ThrowableExtractor getExtractor();
}
