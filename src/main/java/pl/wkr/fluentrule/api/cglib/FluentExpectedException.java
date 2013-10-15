package pl.wkr.fluentrule.api.cglib;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.ThrowableAssert;
import pl.wkr.fluentrule.api.AbstractCheckExpectedException;

public class FluentExpectedException extends AbstractCheckExpectedException<FluentExpectedException>{

    private final CauseAssertFactory causeAssertFactory = new CauseAssertFactory();
    private final ThrowableAssertFactory throwableAssertFactory = new ThrowableAssertFactory();

    public ThrowableAssert expect() {
        return newProxy( ThrowableAssert.class, Throwable.class, throwableAssertFactory);
    }

    public ThrowableAssert expect(Class<? extends Throwable> type) {
        return newProxy(ThrowableAssert.class, Throwable.class, throwableAssertFactory).isInstanceOf(type);
    }

    public ThrowableAssert expectAny(Class<?> ... types) {
        return newProxy(ThrowableAssert.class, Throwable.class, throwableAssertFactory).isInstanceOfAny(types);
    }

    public ThrowableAssert expectCause(){
        return newProxy(ThrowableAssert.class, Throwable.class, causeAssertFactory);
    }


    //--------------------------------------------------------------------------------

    protected final <A extends AbstractThrowableAssert<A,T>, T extends Throwable>
        A newProxy(Class<A> assertClass, Class<T> throwableClass, AssertFactory<A,T> factory) {

        CheckWithProxy<A,T> check = new CheckWithProxy<A,T>(assertClass, throwableClass, factory);
        checks.add(check);
        return check.getAssertProxy();
    }

    protected final <A extends AbstractThrowableAssert<A,T>, T extends Throwable>
        A newProxy(Class<A> assertClass, Class<T> throwableClass) {

        return newProxy(assertClass, throwableClass, new ReflectionAssertFactory<A, T>(assertClass, throwableClass));
    }
}
