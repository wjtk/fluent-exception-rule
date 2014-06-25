package pl.wkr.fluentrule.proxy.method.acceptor;

import java.lang.reflect.Method;

public class AcceptAllAcceptor implements MethodAcceptor {
    @Override
    public boolean accept(Method method) {
        return true;
    }
}
