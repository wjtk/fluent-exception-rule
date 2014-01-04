package pl.wkr.fluentrule.proxy;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import org.assertj.core.api.AbstractThrowableAssert;
import pl.wkr.fluentrule.api.Check;
import pl.wkr.fluentrule.assertfactory.AssertFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

class CheckWithProxyImpl<A extends AbstractThrowableAssert<A,T> ,T extends Throwable>
    implements CheckWithProxy<A,T>, Check {

    private static final CallbackFilter callbackFilter = new AssertCallbackFilter();

    private AssertFactory<A,T> assertFactory;
    private A assertProxy;
    private List<MethodCall> runLaterList = new ArrayList<MethodCall>();


    public CheckWithProxyImpl(Class<A> assertClass, Class<T> throwableClass, AssertFactory<A, T> factory) {
        this.assertFactory = factory;
        this.assertProxy = proxy(assertClass, throwableClass);
    }

    public A getAssertProxy() {
        return assertProxy;
    }

    @Override
    public void check(Throwable throwable) {
        A anAssert = assertFactory.getAssert(throwable);
        for( MethodCall call : runLaterList) {
            invoke(anAssert, call);
        }
    }

    private void invoke(A anAssert, MethodCall methodCall) {
        try {
            methodCall.getMethod().invoke(anAssert, methodCall.getArgs());
        } catch (IllegalAccessException e) {
            throw getInvokingException(e, methodCall);
        } catch (InvocationTargetException e) {
            if( e.getCause() instanceof AssertionError) {
                throw (AssertionError) e.getCause();
            }
            throw getInvokingException(e.getCause(), methodCall);
        }
    }

    @SuppressWarnings("unchecked")
    private <T, V> V proxy(Class<V> assertClass, Class<T> actualClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(assertClass);
        enhancer.setCallbackFilter(callbackFilter);
        enhancer.setCallbacks(newCallbackArrayForList(runLaterList));
        return (V) enhancer.create(new Class[] { actualClass }, new Object[] { null });
    }

    private Callback[] newCallbackArrayForList(List<MethodCall> list) {
        Callback[] callbacks = new Callback[2];
        callbacks[AssertCallbackFilter.RUN_LATER_RETURN_PROXY] = new RunLaterReturnProxy().withList(list);
        callbacks[AssertCallbackFilter.RUN_LATER_RETURN_DEFAULT_VALUE] = new RunLaterReturnDefaultValue().withList(list);
        return callbacks;
    }

    //--------------------------------------------------

    private RuntimeException getInvokingException(Throwable cause, MethodCall methodCall) {
        return new RuntimeException(String.format(
                "Exception, not AssertionError when invoking method [%s].",
                methodCall.getMethod()),cause);
    }
}
