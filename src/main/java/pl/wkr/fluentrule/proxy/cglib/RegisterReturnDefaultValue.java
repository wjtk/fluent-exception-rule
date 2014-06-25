package pl.wkr.fluentrule.proxy.cglib;

import net.sf.cglib.proxy.MethodProxy;
import pl.wkr.fluentrule.proxy.CalledMethodRegister;
import pl.wkr.fluentrule.util.TypeDefaults;

import java.lang.reflect.Method;

class RegisterReturnDefaultValue extends RegisteringMethodInterceptor {

    private final TypeDefaults typeDefaults;

    public RegisterReturnDefaultValue(CalledMethodRegister register, TypeDefaults typeDefaults) {
        super(register);
        this.typeDefaults = typeDefaults;
    }

    @Override
    protected Object getReturnValue(Object object, Method method, Object[] arguments, MethodProxy methodProxy) {
        return typeDefaults.getDefaultValue(method.getReturnType());
    }
}


