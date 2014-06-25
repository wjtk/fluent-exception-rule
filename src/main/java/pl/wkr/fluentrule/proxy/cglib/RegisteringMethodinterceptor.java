package pl.wkr.fluentrule.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import pl.wkr.fluentrule.proxy.CalledMethodRegister;

import java.lang.reflect.Method;

abstract class RegisteringMethodInterceptor implements MethodInterceptor {

    private final CalledMethodRegister register;

    public RegisteringMethodInterceptor(CalledMethodRegister register) {
        this.register = register;
    }

    @Override
    public Object intercept(Object object, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
        register(method, arguments);
        return getReturnValue(object, method, arguments, methodProxy);
    }


    protected void register(Method method, Object ... args) {
        register.wasCalled(method, args);
    }

    protected abstract Object getReturnValue(Object object, Method method, Object[] arguments, MethodProxy methodProxy);
}
