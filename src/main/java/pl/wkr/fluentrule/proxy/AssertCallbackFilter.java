package pl.wkr.fluentrule.proxy;

import net.sf.cglib.proxy.CallbackFilter;
import org.assertj.core.api.AbstractAssert;

import java.lang.reflect.Method;

public class AssertCallbackFilter implements CallbackFilter {

    public static final int NOOP = 0;
    public static final int RUN_LATER_RETURN_PROXY = 1;  //hasMessage, isNull
    public static final int RUN_LATER_RETURN_DEFAULT_VALUE = 2; //

    @Override
    public int accept(Method method) {
        if(isReturningSelf(method)) {
            return RUN_LATER_RETURN_PROXY;
        }
        return RUN_LATER_RETURN_DEFAULT_VALUE;
        //return NOOP;
    }

    private boolean isReturningSelf(Method method) {
        Class<?> returnType = method.getReturnType();
        try {
            returnType.asSubclass(AbstractAssert.class);
            //getReturnType returned AbstractAssert for isInstanceOf()!
            return true;
        } catch(ClassCastException e) {
            return false;
        }
    }
}
