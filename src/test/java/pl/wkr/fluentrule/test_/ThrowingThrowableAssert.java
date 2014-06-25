package pl.wkr.fluentrule.test_;

import org.assertj.core.api.AbstractThrowableAssert;

import java.lang.reflect.Method;

@SuppressWarnings("unused")
public class ThrowingThrowableAssert extends AbstractThrowableAssert<ThrowingThrowableAssert, Throwable> {

    public ThrowingThrowableAssert() {
        super(null, ThrowingThrowableAssert.class);
    }

    public void throwArgument(Throwable e) throws Throwable {
        //to stub!
    }

    void illegalAccess() {
    }

    public void order1(String s1, String s2) {
    }

    public void order2() {
    }

    public static Method getMethod(String name, Class<?> ... argTypes) throws NoSuchMethodException {
        return ThrowingThrowableAssert.class.getDeclaredMethod(name, argTypes);
    }
}
