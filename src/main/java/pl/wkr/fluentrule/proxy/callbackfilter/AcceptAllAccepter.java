package pl.wkr.fluentrule.proxy.callbackfilter;

import java.lang.reflect.Method;

public class AcceptAllAccepter implements MethodAccepter {
    @Override
    public boolean accept(Method method) {
        return true;
    }
}
