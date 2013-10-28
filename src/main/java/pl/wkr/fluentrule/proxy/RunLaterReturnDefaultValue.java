package pl.wkr.fluentrule.proxy;

import net.sf.cglib.proxy.MethodProxy;
import pl.wkr.fluentrule.util.TypeDefaults;

import java.lang.reflect.Method;

public class RunLaterReturnDefaultValue extends RunLaterCallback {


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        runLater(method, objects);
        return TypeDefaults.getDefaultValue(method.getReturnType());
    }
}
