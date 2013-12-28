package pl.wkr.fluentrule.api;

import pl.wkr.fluentrule.util.ClassFinder;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Type safe implementation of {@link Check} interface. Ensures that thrown exception has expected type
 * and pass it to {@link #safeCheck(Throwable)} method. Should be used for anonymous classes.
 * <p/>
 * Usage with {@link CheckExpectedException} rule:
 * </p>
 *
 * <pre><code>
 * &#064;Rule
 * public CheckExpectedException thrown = CheckExpectedException.none();
 *
 * &#064;Test
 * public void test_state_with_type_safe_check() throws NotEnoughMoney {
 *      thrown.check(new SafeCheck<NotEnoughMoney>() {
 *          protected void safeCheck(NotEnoughMoney notEnoughMoney) {
 *              assertThat(notEnoughMoney.getLackingMoney()).isEqualTo(3);  //assert custom exception
 *          }
 *      });
 * }
 * </code></pre>
 *
 * @param <T> expected type of thrown exception
 */
public abstract class SafeCheck<T extends Throwable> implements Check{

    private static final int T_PARAM_INDEX = 0;
    private static final ClassFinder CLASS_FINDER = new ClassFinder(T_PARAM_INDEX);

    private Class<T> expectedType = null;

    /**
     * Constructs new instance, most convenient constructor.
     */
    protected SafeCheck() {
        this(CLASS_FINDER);
    }

    /**
     * Constructs new instance for expected exception's type.
     *
     * @param expectedType expected thrown exception's type
     */
    protected SafeCheck(Class<T> expectedType) {
        this.expectedType = expectedType;
    }

    /**
     * Construct new instance for expected exception's type finder.
     *
     * @param finder class finder to find concrete class of thrown exception
     */
    protected SafeCheck(ClassFinder finder) {
        expectedType = finder.findConcreteClass(getClass());
    }

    /**
     * This method is called when exception is thrown. Implementation in this class
     * pass call to type safe {@link #safeCheck(Throwable)} method.
     *
     * @param exception thrown exception
     */
    public final void check(Throwable exception) {
        assertThat(exception).isInstanceOf(expectedType);
        safeCheck(expectedType.cast(exception));
    }

    /**
     * This method is called when exception is thrown. This is type safe version of {@link #check(Throwable)}.
     *
     * @param exception thrown exception
     */
    abstract protected void safeCheck(T exception);
}
