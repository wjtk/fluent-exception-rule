package pl.wkr.fluentrule.proxy;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.assertj.core.api.AbstractThrowableAssert;
import pl.wkr.fluentrule.api.AssertFactory;
import pl.wkr.fluentrule.api.SafeCheck;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

class CheckWithProxyImpl<A extends AbstractThrowableAssert<A,T> ,T extends Throwable> extends SafeCheck<T>
    implements CheckWithProxy<A,T> {

    private static final CallbackFilter callbackFilter = new AssertCallbackFilter();

    private AssertFactory<A,T> assertFactory;
    private A assertProxy;
    private List<MethodCall> runLaterList = new ArrayList<MethodCall>();


    public CheckWithProxyImpl(Class<A> assertClass, Class<T> throwableClass, AssertFactory<A, T> factory) {
        super(throwableClass);
        this.assertFactory = factory;
        this.assertProxy = proxy(assertClass, throwableClass);
    }

    public A getAssertProxy() {
        return assertProxy;
    }

    @Override
    protected void safeCheck(T exception) {
        A anAssert = assertFactory.getAssert(exception);
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
        enhancer.setCallbacks(new Callback[] {
            NoOp.INSTANCE,
            new RunLaterReturnProxy().withList(runLaterList),
            new RunLaterReturnDefaultValue().withList(runLaterList)
        });

        return (V) enhancer.create(new Class[] { actualClass }, new Object[] { null });
    }

    //--------------------------------------------------

    private RuntimeException getInvokingException(Throwable cause, MethodCall methodCall) {
        return new RuntimeException(String.format(
                "Exception, not AssertionError when invoking method [%s].",
                methodCall.getMethod()),cause);
    }


}
