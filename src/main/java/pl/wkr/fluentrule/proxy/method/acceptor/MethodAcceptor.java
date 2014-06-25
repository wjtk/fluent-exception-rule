package pl.wkr.fluentrule.proxy.method.acceptor;

import java.lang.reflect.Method;

public interface MethodAcceptor {

    boolean accept(Method method);
}
