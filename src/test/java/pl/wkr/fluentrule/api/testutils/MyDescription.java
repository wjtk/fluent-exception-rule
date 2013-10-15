package pl.wkr.fluentrule.api.testutils;

import org.assertj.core.description.Description;

public class MyDescription extends Description{

    private String description;

    public MyDescription(String description) {

        this.description = description;
    }

    @Override
    public String value() {
        return description;
    }
}
