package pl.wkr.fluentrule.proxy;

import net.sf.cglib.proxy.Enhancer;
import org.assertj.core.api.AbstractThrowableAssert;
import pl.wkr.fluentrule.api.check.Check;
import pl.wkr.fluentrule.assertfactory.AssertFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

class CheckWithProxyImpl<A extends AbstractThrowableAssert<A,T> ,T extends Throwable>
    implements CheckWithProxy<A,T>, Check {

    private final AssertFactory<A,T> assertFactory;
    private final RunLaterCallbackFactory runLaterCallbackFactory;
    private final A assertProxy;

    private List<MethodCall> runLaterList = new ArrayList<MethodCall>();

    public CheckWithProxyImpl(
            Class<A> assertClass,
            Class<T> throwableClass,
            AssertFactory<A, T> factory,
            RunLaterCallbackFactory runLaterCallbackFactory) {

        this.assertFactory = factory;
        this.runLaterCallbackFactory = runLaterCallbackFactory;
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
            if(e.getCause() instanceof AssertionError) {
                throw (AssertionError) e.getCause();
            }
            throw getInvokingException(e.getCause(), methodCall); //NOPMD
        }
    }

    @SuppressWarnings("unchecked")
    private <U, V> V proxy(Class<V> assertClass, Class<U> actualClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(assertClass);
        enhancer.setCallbackFilter(runLaterCallbackFactory.getMethodCallbackFilter());
        enhancer.setCallbacks(runLaterCallbackFactory.newCallbackArrayForList(runLaterList));
        return (V) enhancer.create(new Class[] { actualClass }, new Object[] { null });
    }

    //--------------------------------------------------

    private RuntimeException getInvokingException(Throwable cause, MethodCall methodCall) {
        return new IllegalStateException(String.format(
                    "Exception, not AssertionError when invoking method [%s] on throwable assert. " +
                    "This should not happen. Problem in throwable assert class.",
                    methodCall.getMethod()
                ),
                cause);
    }
}
