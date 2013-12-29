package pl.wkr.fluentrule.api.test_;

import org.junit.Rule;

public abstract class BaseWithFluentThrownTest {
    @Rule
    public TFluentExpectedException thrown = TFluentExpectedException.handlingAll();

}
