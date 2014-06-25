package pl.wkr.fluentrule.proxy;

import java.lang.reflect.Method;

import static org.assertj.core.util.Preconditions.checkNotNull;

class MethodCall {
    private Method method;
    private Object[] args;

    public MethodCall(Method method, Object ... args) {
        this.method = checkNotNull(method, "method");
        this.args = args.clone();
    }

    public Object[] getArgs() {
        return args;
    }

    public Method getMethod() {
        return method;
    }
}
