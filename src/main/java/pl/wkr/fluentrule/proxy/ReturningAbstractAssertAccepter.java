package pl.wkr.fluentrule.proxy;

import org.assertj.core.api.AbstractAssert;
import pl.wkr.fluentrule.proxy.callbackfilter.MethodAccepter;

import java.lang.reflect.Method;

public class ReturningAbstractAssertAccepter implements MethodAccepter {
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
