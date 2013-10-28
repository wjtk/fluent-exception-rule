package pl.wkr.fluentrule.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

class RunLaterReturnProxy extends RunLaterCallback {

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        runLater(method, args);
        return object;
    }
}
