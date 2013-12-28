package pl.wkr.fluentrule.api;


/**
 * Implements junit's {@code TestRule} and allows to assert thrown exception in callback.
 *
 * <p/>
 * Usage in tests:
 * <p/>
 *
 * <pre><code>
 * &#064;Rule
 * public CheckExpectedException thrown = CheckExpectedException.none();
 *
 * &#064;Test
 * public void test_state_of_objects_raw_check() throws NotEnoughMoney {
 *      coffeeMachine.insertCoin(2);
 *
 *      thrown.check(new Check() {
 *          public void check(Throwable throwable) {
 *              assertThat(coffeeMachine.getInsertedMoney()).isEqualTo(2);  //assert state
 *           }
 *      });
 *
 *      coffeeMachine.getCoffee();
 * }
 * </code></pre>
 *
 * @see
 *  <a href="https://github.com/wjtk/fluent-exception-rule#readme" target="_blank">Project site - more examples</a>
 *
 * @author Wojciech Krak
 */
public class CheckExpectedException extends AbstractCheckExpectedException<CheckExpectedException> {

    /**
     * Creates and returns new instance of {@code CheckExpectedException} rule.
     *
     * @return new instance
     */
    public static CheckExpectedException none() {
        return new CheckExpectedException();
    }

    /**
     * Adds callback to assert thrown exception.
     *
     * @param check callback to assert thrown exception
     * @return this to allow chaining
     */
    public CheckExpectedException check(Check check) {
        addCheck(check);
        return this;
    }

    private CheckExpectedException() {}
}
