package pl.wkr.fluentrule.api.test_;

import org.junit.Rule;
import pl.wkr.fluentrule.api.FluentExpectedException;

public abstract class BaseFluentThrownTest {
    @Rule
    public FluentExpectedException thrown = FluentExpectedException.none().handleAssertionErrors();

}
