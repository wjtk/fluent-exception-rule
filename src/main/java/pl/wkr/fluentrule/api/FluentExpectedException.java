package pl.wkr.fluentrule.api;


import java.util.ArrayList;
import java.util.List;

//Doesn't need SELF-behavior
public class FluentExpectedException extends AbstractHandleExceptionRule<FluentExpectedException>  {

    private List<AssertCommandList<?,?>> asserts = new ArrayList<AssertCommandList<?, ?>>();

    private AssertCommandListCollector assertCommandListCollector = new AssertCommandListCollector() {
        @Override
        public void add(AssertCommandList<?, ?> acl) {
            asserts.add(acl);
        }
    };

    // ExpectedException.none() pattern is not suitable, because this class should be easy to inherit
    // and subclasses would have to deliver their version of none()
    public FluentExpectedException() {}

    public ExpectedThrowableAssert expect() {
        return newThrowableAssert();
    }

    public ExpectedThrowableAssert expect(Class<? extends Throwable> type) {
        return newThrowableAssert().isInstanceOf(type);
    }

    public ExpectedThrowableAssert expectAny(Class<?>... types) {
        return newThrowableAssert().isInstanceOfAny(types);
    }

    public ExpectedThrowableAssert expectCause() {
        return new ExpectedThrowableCauseAssert(getAssertCommandListCollector());
    }

    //-----------  protected ----------------


    @Override
    protected final boolean isExceptionExpected() {
        return asserts.size() > 0;
    }

    @Override
    protected final void failBecauseExceptionWasNotThrown() {
        throw new AssertionError("Exception was expected but was not thrown");
    }

    @Override
    protected final void handleException(Throwable e) {
        for( AssertCommandList<?,?> acl : asserts) {
            acl.check(e);
        }
    }

    protected final AssertCommandListCollector getAssertCommandListCollector() {
        return  assertCommandListCollector;
    }

    //---------- private -------------

    private ExpectedThrowableAssert newThrowableAssert() {
        return new ExpectedThrowableAssert(getAssertCommandListCollector());
    }
}


