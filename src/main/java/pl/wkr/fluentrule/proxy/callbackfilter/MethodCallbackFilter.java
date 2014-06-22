package pl.wkr.fluentrule.proxy.callbackfilter;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.util.Preconditions.checkNotNull;

public class MethodCallbackFilter implements CallbackFilter {

    private final List<MethodAccepter> methodAccepters;

    public MethodCallbackFilter(List<MethodAccepter> methodAccepters) {
        checkNotNull(methodAccepters, "methodAccepters");
        this.methodAccepters = new ArrayList<MethodAccepter>(methodAccepters);
    }

    @Override
    public int accept(Method method) {
        int i = 0;
        for(MethodAccepter methodAccepter : methodAccepters) {
            if(methodAccepter.accept(method)) {
                return i;
            }
            i++;
        }
        throw new IllegalStateException(String.format("Method [%s:%s] is not accepted by any method accepter", method.getDeclaringClass(), method.getName()));
    }

}
