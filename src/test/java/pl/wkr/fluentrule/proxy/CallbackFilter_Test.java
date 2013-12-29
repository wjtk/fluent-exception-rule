package pl.wkr.fluentrule.proxy;

import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.description.Description;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.wkr.fluentrule.proxy.AssertCallbackFilter.RUN_LATER_RETURN_DEFAULT_VALUE;
import static pl.wkr.fluentrule.proxy.AssertCallbackFilter.RUN_LATER_RETURN_PROXY;

public class CallbackFilter_Test {

    private AssertCallbackFilter filter = new AssertCallbackFilter();

    @Test
    public void should_filters_work() throws NoSuchMethodException {
        assertFilter(RUN_LATER_RETURN_DEFAULT_VALUE, "isNull");
        assertFilter(RUN_LATER_RETURN_DEFAULT_VALUE, "descriptionText");
        assertFilter(RUN_LATER_RETURN_DEFAULT_VALUE, "toString");

        assertFilter(RUN_LATER_RETURN_PROXY, "hasNoCause");
        assertFilter(RUN_LATER_RETURN_PROXY, "isInstanceOf", Class.class);
        assertFilter(RUN_LATER_RETURN_PROXY, "as", Description.class);
    }

    private void assertFilter(int expectedInd, String methodName, Class<?> ...argTypes)
            throws NoSuchMethodException {
        int ind = filter.accept(ThrowableAssert.class.getMethod(methodName, argTypes));
        assertThat(ind).as(methodName).isEqualTo(expectedInd);
    }


}
