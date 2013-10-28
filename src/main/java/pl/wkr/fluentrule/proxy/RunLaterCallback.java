package pl.wkr.fluentrule.proxy;

import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.List;

abstract class RunLaterCallback implements MethodInterceptor {

    private List<MethodCall> runLaterList = null;

    public RunLaterCallback withList(List<MethodCall> runLaterList) {
        this.runLaterList = runLaterList;
        return this;
    }

    protected void runLater(Method method, Object[] args) {
        runLaterList.add(new MethodCall(method, args));
    }
}
