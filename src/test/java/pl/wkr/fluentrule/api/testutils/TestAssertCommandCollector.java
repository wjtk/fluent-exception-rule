package pl.wkr.fluentrule.api.testutils;

import pl.wkr.fluentrule.api.AssertCommandList;
import pl.wkr.fluentrule.api.AssertCommandListCollector;

public class TestAssertCommandCollector implements AssertCommandListCollector {
    private AssertCommandList acl = null;

    @Override
    public void add(AssertCommandList<?, ?> acl) {
        if(this.acl != null) throw new IllegalStateException("AssertCommandList already set, use clear()");
        this.acl = acl;
    }

    public void clear() {
        acl = null;
    }

    public void doChecks(Throwable e) {
        acl.check(e);
    }
}
