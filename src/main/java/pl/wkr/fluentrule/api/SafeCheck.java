package pl.wkr.fluentrule.api;

import pl.wkr.fluentrule.util.ClassFinder;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class SafeCheck<T extends Throwable> implements Check{

    private static final int T_PARAM_INDEX = 0;
    private static final ClassFinder CLASS_FINDER = new ClassFinder(T_PARAM_INDEX);

    private Class<T> expectedType = null;

    protected SafeCheck() {
        this(CLASS_FINDER);
    }

    protected SafeCheck(Class<T> expectedType) {
        this.expectedType = expectedType;
    }

    protected SafeCheck(ClassFinder finder) {
        expectedType = finder.findConcreteClass(getClass());
    }

    public final void check(Throwable exception) {
        assertThat(exception).isInstanceOf(expectedType);
        safeCheck(expectedType.cast(exception));
    }

    abstract protected void safeCheck(T exception);
}
