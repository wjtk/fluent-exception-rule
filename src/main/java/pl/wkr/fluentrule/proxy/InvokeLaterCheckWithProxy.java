package pl.wkr.fluentrule.proxy;

import org.assertj.core.api.AbstractThrowableAssert;
import pl.wkr.fluentrule.api.check.Check;
import pl.wkr.fluentrule.assertfactory.AssertFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class InvokeLaterCheckWithProxy<A extends AbstractThrowableAssert<A,T> ,T extends Throwable>
    implements CheckWithProxy<A,T>, Check {

    private final AssertFactory<A,T> assertFactory;
    private final RegisteringProxyFactory<A, T> registeringProxyFactory;
    private A assertProxy;

    private final List<MethodCall> runLaterList = new ArrayList<MethodCall>();

    public InvokeLaterCheckWithProxy(
            AssertFactory<A, T> assertFactory,
            RegisteringProxyFactory<A, T> registeringProxyFactory) {

        this.assertFactory = assertFactory;
        this.registeringProxyFactory = registeringProxyFactory;
    }

    @Override
    public A getAssertProxy() {
        if( assertProxy == null) {
            assertProxy = createProxy();
        }
        return assertProxy;
    }

    @Override
    public void check(Throwable throwable) {
        A anAssert = assertFactory.getAssert(throwable);
        for( MethodCall call : runLaterList) {
            invoke(anAssert, call);
        }
    }

    private A createProxy() {
        return registeringProxyFactory.createProxy(
                new CalledMethodRegister() {
                    @Override
                    public void wasCalled(Method method, Object... args) {
                        runLaterList.add(new MethodCall(
                            method, args
                        ));
                    }
                }
        );
    }

    private void invoke(A anAssert, MethodCall methodCall) {
        try {
            methodCall.getMethod().invoke(anAssert, methodCall.getArgs());
        } catch (IllegalAccessException e) {
            handleIllegalAccessException(e, methodCall);
        } catch (InvocationTargetException e) {
            handleInvocationTargetException(e, methodCall);
        }
    }

    private void handleInvocationTargetException(InvocationTargetException exception, MethodCall methodCall) {
        rethrowIfAssertionError(exception);
        rethrowAnyExceptionFromMethodSkipInvocationTargetException(exception, methodCall);
    }

    private void handleIllegalAccessException(IllegalAccessException exception, MethodCall methodCall) {
        throw getInvokingException(exception, methodCall);
    }

    private void rethrowIfAssertionError(InvocationTargetException exception) {
        if(exception.getCause() instanceof AssertionError) {
            throw (AssertionError) exception.getCause();
        }
    }

    private void rethrowAnyExceptionFromMethodSkipInvocationTargetException(InvocationTargetException exception, MethodCall methodCall) {
        throw getInvokingException(exception.getCause(), methodCall);
    }

    private RuntimeException getInvokingException(Throwable cause, MethodCall methodCall) {
        return new IllegalStateException(String.format(
                "Exception, not AssertionError when invoking method [%s] on throwable assert. " +
                        "This should not happen. Problem in throwable assert class.",
                methodCall.getMethod()
        ),
        cause);
    }
}
