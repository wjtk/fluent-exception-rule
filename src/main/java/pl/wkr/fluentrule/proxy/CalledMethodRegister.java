package pl.wkr.fluentrule.proxy;

import java.lang.reflect.Method;

public interface CalledMethodRegister {

    void wasCalled(Method method, Object ... args);
}
