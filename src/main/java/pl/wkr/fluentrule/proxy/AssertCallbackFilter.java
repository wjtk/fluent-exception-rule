package pl.wkr.fluentrule.proxy;

import net.sf.cglib.proxy.CallbackFilter;
import org.assertj.core.api.AbstractAssert;

import java.lang.reflect.Method;

class AssertCallbackFilter implements CallbackFilter {

    //for: hasMessage(), isNull()
    public static final int RUN_LATER_RETURN_PROXY = 0;

    //for other methods
    public static final int RUN_LATER_RETURN_DEFAULT_VALUE = 1;

    @Override
    public int accept(Method method) {
        if(isReturningSelf(method)) {
            return RUN_LATER_RETURN_PROXY;
        }
        return RUN_LATER_RETURN_DEFAULT_VALUE;
    }

    private boolean isReturningSelf(Method method) {
        //maybe getGenericType()?
        Class<?> returnType = method.getReturnType();
        return AbstractAssert.class.isAssignableFrom(returnType);
    }
}
