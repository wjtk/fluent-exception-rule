package pl.wkr.fluentrule.assertfactory;

import org.junit.Test;
import pl.wkr.fluentrule.api.exception_.ExpectedExc;
import pl.wkr.fluentrule.api.exception_.UnexpectedExc;

import static org.assertj.core.api.Assertions.assertThat;

public class NoopExtractor_Test {

    private NoopExtractor extractor = new NoopExtractor();

    @Test
    public void should_get_exception() {
        Throwable fromExc = new ExpectedExc();

        assertThat(extractor.extract(fromExc)).isSameAs(fromExc);
    }

    @Test
    public void should_get_exception_longer_chain() {
        Throwable fromExc = new ExpectedExc(new UnexpectedExc( ));

        assertThat(extractor.extract(fromExc)).isSameAs(fromExc);
    }
}
