package pl.wkr.fluentrule.proxy;

import net.sf.cglib.proxy.MethodProxy;
import pl.wkr.fluentrule.util.TypeDefaults;

import java.lang.reflect.Method;

class RunLaterReturnDefaultValue extends RunLaterCallback {

    private final TypeDefaults typeDefaults;

    public RunLaterReturnDefaultValue(TypeDefaults typeDefaults) {
        this.typeDefaults = typeDefaults;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        runLater(method, objects);
        return typeDefaults.getDefaultValue(method.getReturnType());
    }
}
