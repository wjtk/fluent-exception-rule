package pl.wkr.fluentrule.api;

import org.junit.Test;
import pl.wkr.fluentrule.api.test_.BaseWithFluentThrownTest;

import static org.assertj.core.api.Assertions.assertThat;

public class FluentExpectedException_Test extends BaseWithFluentThrownTest {

    @Test
    public void should_create_new_instance() {
        assertThat(FluentExpectedException.none()).isInstanceOf(FluentExpectedException.class);
    }

}
