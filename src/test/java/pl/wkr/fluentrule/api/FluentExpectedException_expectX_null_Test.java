package pl.wkr.fluentrule.api;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.wkr.fluentrule.api.test_.BaseWithFluentThrownTest;
import pl.wkr.fluentrule.proxy.factory.ProxiesFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static junitparams.JUnitParamsRunner.$;
import static org.mockito.Mockito.mock;

@RunWith(JUnitParamsRunner.class)
public class FluentExpectedException_expectX_null_Test extends BaseWithFluentThrownTest {


    @SuppressWarnings("unused")
    private Object [] data() {
        String type = "type";
        String assertClass = "assertClass";

        return $(
            $("expect", type),
            $("expectCause", type),
            $("expectRootCause", type),
            $("expectWith", assertClass),
            $("expectCauseWith", assertClass),
            $("expectRootCauseWith", assertClass)
        );
    }


    @Parameters(method = "data")
    @Test
    public void should_throw_exception_when_argument_is_null(String methodName, String argName)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        FluentExpectedException rule = new FluentExpectedException(mock(ProxiesFactory.class));
        Method method = FluentExpectedException.class.getMethod(methodName, Class.class);

        thrown.expect(InvocationTargetException.class);
        thrown.expectCause(NullPointerException.class).hasMessage(argName);
        method.invoke(rule, new Object[]{null});
    }
}
