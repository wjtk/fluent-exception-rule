package pl.wkr.fluentrule.proxy.method.acceptor;

import org.assertj.core.api.AbstractAssert;

import java.lang.reflect.Method;

public class ReturningAbstractAssertAcceptor implements MethodAcceptor {

    @Override
    public boolean accept(Method method) {
        return isReturningSelf(method);
    }

    private boolean isReturningSelf(Method method) {
        //maybe getGenericType()?
        Class<?> returnType = method.getReturnType();
        return AbstractAssert.class.isAssignableFrom(returnType);
    }

}
