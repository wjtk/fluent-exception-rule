package pl.wkr.fluentrule.proxy;

import net.sf.cglib.proxy.CallbackFilter;
import org.assertj.core.api.AbstractAssert;

import java.lang.reflect.Method;

class AssertCallbackFilter implements CallbackFilter {

    public static final int RUN_LATER_RETURN_PROXY = 0;  //hasMessage, isNull
    public static final int RUN_LATER_RETURN_DEFAULT_VALUE = 1; //other methods

    @Override
    public int accept(Method method) {
        if(isReturningSelf(method)) {
            return RUN_LATER_RETURN_PROXY;
        }
        return RUN_LATER_RETURN_DEFAULT_VALUE;
    }

    private boolean isReturningSelf(Method method) {
        Class<?> returnType = method.getReturnType();  //TODO, maybe getGenericType() - study!
        return AbstractAssert.class.isAssignableFrom(returnType);
    }
}
