package pl.wkr.fluentrule.api.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.assertj.core.api.AbstractThrowableAssert;
import pl.wkr.fluentrule.api.Check;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CheckWithProxy<A extends AbstractThrowableAssert<A,T> ,T extends Throwable> extends Check<T> {

    private AssertFactory<A,T> assertFactory;
    private MethodCollector methodCollector = new MethodCollector();
    private A assertProxy;

    public CheckWithProxy(Class<A> assertClass, Class<T> throwableClass, AssertFactory<A,T> factory) {
        super(throwableClass);
        this.assertFactory = factory;
        this.assertProxy = proxy(assertClass, throwableClass, methodCollector);
    }

    public A getAssertProxy() {
        return assertProxy;
    }


    @Override
    protected void check(T exception) {
        A anAssert = assertFactory.getAssert(exception);
        for( MethodCall call : methodCollector.methodCalls()) {
            invoke(anAssert, call);
        }
    }

    private void invoke(A anAssert, MethodCall methodCall) {
        try {
            methodCall.getMethod().invoke(anAssert, methodCall.getArgs());
        } catch (IllegalAccessException e) {
            throw getInvokingException(e, methodCall);
        } catch (InvocationTargetException e) {
            //TODO invoke catches AssertionError, why? WorkAround:
            if( e.getCause() instanceof AssertionError) {
                throw (AssertionError) e.getCause();
            }
            throw getInvokingException(e, methodCall);
        }
    }


    @SuppressWarnings("unchecked")
    private <T, V> V proxy(Class<V> assertClass, Class<T> actualClass, MethodInterceptor interceptor) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(assertClass);
        enhancer.setCallback(interceptor);
        return (V) enhancer.create(new Class[] { actualClass }, new Object[] { null });
    }


    //--------------------------------------------------

    private RuntimeException getInvokingException(Throwable cause, MethodCall methodCall) {
        return new RuntimeException(String.format("Exception when invoking method [%s]", methodCall.getMethod()),cause);
    }


    private static class MethodCollector implements MethodInterceptor {

        private List<MethodCall> calls = new ArrayList<MethodCall>();

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            calls.add( new MethodCall(method, objects));
            return o;
        }

        public List<MethodCall> methodCalls() {
            return calls;
        }
    }

    private static class MethodCall {
        private Method method;
        private Object[] args;

        MethodCall(Method method, Object[] args) {
            this.method = method;
            this.args = args;
        }

        Object[] getArgs() {
            return args;
        }

        Method getMethod() {
            return method;
        }
    }

}
