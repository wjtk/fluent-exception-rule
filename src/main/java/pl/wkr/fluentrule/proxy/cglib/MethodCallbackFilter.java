package pl.wkr.fluentrule.proxy.cglib;

import net.sf.cglib.proxy.CallbackFilter;
import pl.wkr.fluentrule.proxy.method.acceptor.MethodAcceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.util.Preconditions.checkNotNull;

class MethodCallbackFilter implements CallbackFilter {

    private final List<MethodAcceptor> methodAcceptors;

    public MethodCallbackFilter(List<MethodAcceptor> methodAcceptors) {
        checkNotNull(methodAcceptors, "methodAcceptors");
        this.methodAcceptors = new ArrayList<MethodAcceptor>(methodAcceptors);
    }

    @Override
    public int accept(Method method) {
        int i = 0;
        for(MethodAcceptor methodAcceptor : methodAcceptors) {
            if(methodAcceptor.accept(method)) {
                return i;
            }
            i++;
        }
        throw new IllegalStateException(String.format(
                "Method [%s:%s] is not accepted by any method accepter", method.getDeclaringClass(), method.getName()));
    }

}
