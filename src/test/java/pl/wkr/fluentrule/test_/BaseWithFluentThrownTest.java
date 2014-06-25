package pl.wkr.fluentrule.test_;

import org.junit.Rule;

public abstract class BaseWithFluentThrownTest {
    @Rule
    public TFluentExpectedException thrown = TFluentExpectedException.handlingAll();

}
