package pl.wkr.fluentrule.proxy;

import org.assertj.core.api.AbstractThrowableAssert;
import pl.wkr.fluentrule.api.Check;

public interface CheckWithProxy<A extends AbstractThrowableAssert<A,T>,T extends Throwable> extends Check {

    A getAssertProxy();

}
