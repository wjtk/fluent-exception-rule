package pl.wkr.fluentrule.proxy.cglib;

import net.sf.cglib.proxy.MethodProxy;
import pl.wkr.fluentrule.proxy.CalledMethodRegister;

import java.lang.reflect.Method;

class RegisterReturnObject extends RegisteringMethodInterceptor {

    public RegisterReturnObject(CalledMethodRegister register) {
        super(register);
    }

    @Override
    protected Object getReturnValue(Object object, Method method, Object[] arguments, MethodProxy methodProxy) {
        return object;
    }
}
