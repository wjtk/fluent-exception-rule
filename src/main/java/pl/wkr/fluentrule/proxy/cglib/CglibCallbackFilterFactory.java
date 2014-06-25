package pl.wkr.fluentrule.proxy.cglib;

import net.sf.cglib.proxy.Callback;
import pl.wkr.fluentrule.proxy.CalledMethodRegister;
import pl.wkr.fluentrule.proxy.method.acceptor.AcceptAllAcceptor;
import pl.wkr.fluentrule.proxy.method.acceptor.ReturningAbstractAssertAcceptor;
import pl.wkr.fluentrule.util.TypeDefaults;

import java.util.Arrays;

class CglibCallbackFilterFactory {

    private final TypeDefaults typeDefaults;
    private final MethodCallbackFilter methodCallbackFilter;

    public CglibCallbackFilterFactory(TypeDefaults typeDefaults) {
        this.typeDefaults = typeDefaults;
        this.methodCallbackFilter = new MethodCallbackFilter(
                Arrays.asList(
                        new ReturningAbstractAssertAcceptor(),
                        new AcceptAllAcceptor())
        );
    }

    public MethodCallbackFilter getMethodCallbackFilter() {
        return methodCallbackFilter;
    }


    public Callback[] newCallbackArrayForRegister(CalledMethodRegister register) {
        return new Callback[] {
                new RegisterReturnObject(register),
                new RegisterReturnDefaultValue(register, typeDefaults)
        };
    }
}
