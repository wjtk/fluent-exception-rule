package pl.wkr.fluentrule.proxy.callbackfilter;

import java.lang.reflect.Method;

public interface MethodAccepter {
    boolean accept(Method method);
}
