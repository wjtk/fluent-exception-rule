package pl.wkr.fluentrule.proxy.throwableassert_;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Condition;
import org.assertj.core.description.Description;

import java.util.Comparator;


/*
Mockito relies on CGLIB modified [equals()] method, but in assertj AbstractAssert.equals()
is final and throws exception. CGLIB doesn't get by with final methods, I always get exception from assertj.

Have to write my own mock which will delegate method calls to mockito mock with same
methods as AbstractThrowableAssert. This mock acts as register, which can be verified using
mockito api questions methods(e.g. verify).

ThrowableAssertMock - mock delgating all calls to ThrowableAssertMockRegister(interface mocked by mockito)
*/
public class AbstractThrowableAssertMock<
        S extends AbstractThrowableAssertMock<S,A,R>,
        A extends Throwable, R extends AbstractThrowableAssertMockRegister<A>>
        extends AbstractThrowableAssert<S,A> {

    protected R register;


    protected AbstractThrowableAssertMock(A actual, Class<?> selfType,
                                          R register) {

        super(actual, selfType);
        this.register = register;
    }

    public R getMockRegister() {
        return register;
    }

    // ------------------- methods ---------------------------------------


    @Override
    public S hasMessage(String message) {
        register.hasMessage(message);
        return myself;
    }

    @Override
    public S hasNoCause() {
        register.hasNoCause();
        return myself;
    }

    @Override
    public S hasMessageStartingWith(String description) {
        register.hasMessageStartingWith(description);
        return myself;
    }

    @Override
    public S hasMessageContaining(String description) {
        register.hasMessageContaining(description);
        return myself;
    }

    @Override
    public S hasMessageEndingWith(String description) {
        register.hasMessageEndingWith(description);
        return myself;
    }

    @Override
    public S hasCauseInstanceOf(Class<? extends Throwable> type) {
        register.hasCauseInstanceOf(type);
        return myself;
    }

    @Override
    public S hasCauseExactlyInstanceOf(Class<? extends Throwable> type) {
        register.hasCauseExactlyInstanceOf(type);
        return myself;
    }

    @Override
    public S hasRootCauseInstanceOf(Class<? extends Throwable> type) {
        register.hasRootCauseInstanceOf(type);
        return myself;
    }

    @Override
    public S hasRootCauseExactlyInstanceOf(Class<? extends Throwable> type) {
        register.hasRootCauseExactlyInstanceOf(type);
        return myself;
    }

    @Override
    public S as(String description, Object... args) {
        register.as(description, args);
        return myself;
    }

    @Override
    public S as(Description description) {
        register.as(description);
        return myself;
    }

    @Override
    public S describedAs(String description, Object... args) {
        register.describedAs(description, args);
        return myself;
    }

    @Override
    public S describedAs(Description description) {
        register.describedAs(description);
        return myself;
    }

    @Override
    public S isEqualTo(Object expected) {
        register.isEqualTo(expected);
        return myself;
    }

    @Override
    public S isNotEqualTo(Object other) {
        register.isNotEqualTo(other);
        return myself;
    }

    @Override
    public void isNull() {
        register.isNull();
    }

    @Override
    public S isNotNull() {
        register.isNotNull();
        return myself;
    }

    @Override
    public S isSameAs(Object expected) {
        register.isSameAs(expected);
        return myself;
    }

    @Override
    public S isNotSameAs(Object other) {
        register.isNotSameAs(other);
        return myself;
    }

    @Override
    public S isIn(Object... values) {
        register.isIn(values);
        return myself;
    }

    @Override
    public S isNotIn(Object... values) {
        register.isNotIn(values);
        return myself;
    }

    @Override
    public S isIn(Iterable<?> values) {
        register.isIn(values);
        return myself;
    }

    @Override
    public S isNotIn(Iterable<?> values) {
        register.isNotIn(values);
        return myself;
    }

    @Override
    public S is(Condition<? super A> condition) {
        register.is(condition);
        return myself;
    }

    @Override
    public S isNot(Condition<? super A> condition) {
        register.isNot(condition);
        return myself;
    }

    @Override
    public S has(Condition<? super A> condition) {
        register.has(condition);
        return myself;
    }

    @Override
    public S doesNotHave(Condition<? super A> condition) {
        register.doesNotHave(condition);
        return myself;
    }

    @Override
    public S isInstanceOf(Class<?> type) {
        register.isInstanceOf(type);
        return myself;
    }

    @Override
    public S isInstanceOfAny(Class<?>... types) {
        register.isInstanceOfAny(types);
        return myself;
    }

    @Override
    public S isNotInstanceOf(Class<?> type) {
        register.isNotInstanceOf(type);
        return myself;
    }

    @Override
    public S isNotInstanceOfAny(Class<?>... types) {
        register.isNotInstanceOfAny(types);
        return myself;
    }

    @Override
    public S hasSameClassAs(Object other) {
        register.hasSameClassAs(other);
        return myself;
    }

    @Override
    public S doesNotHaveSameClassAs(Object other) {
        register.doesNotHaveSameClassAs(other);
        return myself;
    }

    @Override
    public S isExactlyInstanceOf(Class<?> type) {
        register.isExactlyInstanceOf(type);
        return myself;
    }

    @Override
    public S isNotExactlyInstanceOf(Class<?> type) {
        register.isNotExactlyInstanceOf(type);
        return myself;
    }

    @Override
    public S isOfAnyClassIn(Class<?>... types) {
        register.isOfAnyClassIn(types);
        return myself;
    }

    @Override
    public S isNotOfAnyClassIn(Class<?>... types) {
        register.isNotOfAnyClassIn(types);
        return myself;
    }

    @Override
    public String descriptionText() {
        register.descriptionText();
        return null;
    }

    @Override
    public S overridingErrorMessage(String newErrorMessage, Object... args) {
        register.overridingErrorMessage(newErrorMessage, args);
        return myself;
    }

    @Override
    public S usingComparator(Comparator<? super A> customComparator) {
        register.usingComparator(customComparator);
        return myself;
    }

    @Override
    public S usingDefaultComparator() {
        register.usingDefaultComparator();
        return myself;
    }
}
