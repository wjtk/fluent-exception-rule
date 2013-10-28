package pl.wkr.fluentrule.proxy.throwing;

public class ThrowingThrowableAssert extends AbstractThrowingThrowableAssert<ThrowingThrowableAssert,Throwable> {


    public ThrowingThrowableAssert(Throwable actual) {
        super(actual, ThrowingThrowableAssert.class);
    }

}
