package pl.wkr.fluentrule.proxy;

import net.sf.cglib.proxy.Callback;
import pl.wkr.fluentrule.proxy.callbackfilter.AcceptAllAccepter;
import pl.wkr.fluentrule.proxy.callbackfilter.MethodCallbackFilter;
import pl.wkr.fluentrule.util.TypeDefaults;

import java.util.Arrays;
import java.util.List;

public class RunLaterCallbackFactory  {

    private final TypeDefaults typeDefaults = new TypeDefaults();
    private final MethodCallbackFilter methodCallbackFilter;


    public RunLaterCallbackFactory() {
        methodCallbackFilter = new MethodCallbackFilter(
            Arrays.asList(
                    new ReturningAbstractAssertAccepter(),
                    new AcceptAllAccepter())
        );
    }


    public MethodCallbackFilter getMethodCallbackFilter() {
        return methodCallbackFilter;
    }

    public Callback[] newCallbackArrayForList(List<MethodCall> methodCalls) {
        return new Callback[] {
                newRunLaterReturnProxy().withList(methodCalls),
                newRunLaterReturnDefaultValue().withList(methodCalls)
        };
    }

    private RunLaterCallback newRunLaterReturnDefaultValue() {
        return new RunLaterReturnDefaultValue(typeDefaults);
    }

    private RunLaterCallback newRunLaterReturnProxy() {
        return new RunLaterReturnProxy();
    }

}
