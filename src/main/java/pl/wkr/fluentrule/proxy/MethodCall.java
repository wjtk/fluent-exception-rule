package pl.wkr.fluentrule.proxy;

import java.lang.reflect.Method;

class MethodCall {
    private Method method;
    private Object[] args;

    MethodCall(Method method, Object[] args) {
        this.method = method;
        this.args = args.clone();
    }

    Object[] getArgs() {
        return args;
    }

    Method getMethod() {
        return method;
    }
}
