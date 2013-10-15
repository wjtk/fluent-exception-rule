package pl.wkr.fluentrule.api.testutils;

import org.assertj.core.api.Condition;

public class MyCondition extends Condition<Throwable> {
    @Override
    public boolean matches(Throwable throwable) {
        return false;
    }
}
