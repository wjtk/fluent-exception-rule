package pl.wkr.fluentrule.proxy.throwableassert_;

import org.assertj.core.api.Condition;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.description.Description;

import java.util.Comparator;

import static org.mockito.Mockito.mock;

/*
Mockito relies on CGLIB modified [equals()] method, but in assertj AbstractAssert.equals()
is final and throws exception. CGLIB doesn't get by with final methods, I always get exception from assertj.

Have to write my own mock which will delegate method calls to mockito mock with same
methods as AbstractThrowableAssert. This mock acts as register, which can be verified using
mockito api questions methods(e.g. verify).

ThrowableAssertMock - mock delgating all calls to ThrowableAssertMockRegister(interface mocked by mockito)
*/

public class ThrowableAssertMock extends ThrowableAssert {

    protected  ThrowableAssertMockRegister register;

    public ThrowableAssertMock() {
        this(null);
    }

    public ThrowableAssertMock(Throwable actual) {
        super(actual);
        register = mock(ThrowableAssertMockRegister.class);
    }

    public ThrowableAssertMockRegister getMockRegister() {
        return register;
    }

    //- ThrowableAssert methods --------------------------------------------------------------------------------------

    @Override
    public ThrowableAssert hasMessage(String message) {
        register.hasMessage(message);
        return myself;
    }

    @Override
    public ThrowableAssert hasNoCause() {
        register.hasNoCause();
        return myself;
    }

    @Override
    public ThrowableAssert hasMessageStartingWith(String description) {
        register.hasMessageStartingWith(description);
        return myself;
    }

    @Override
    public ThrowableAssert hasMessageContaining(String description) {
        register.hasMessageContaining(description);
        return myself;
    }

    @Override
    public ThrowableAssert hasMessageEndingWith(String description) {
        register.hasMessageEndingWith(description);
        return myself;
    }

    @Override
    public ThrowableAssert hasCauseInstanceOf(Class<? extends Throwable> type) {
        register.hasCauseInstanceOf(type);
        return myself;
    }

    @Override
    public ThrowableAssert hasCauseExactlyInstanceOf(Class<? extends Throwable> type) {
        register.hasCauseExactlyInstanceOf(type);
        return myself;
    }

    @Override
    public ThrowableAssert hasRootCauseInstanceOf(Class<? extends Throwable> type) {
        register.hasRootCauseInstanceOf(type);
        return myself;
    }

    @Override
    public ThrowableAssert hasRootCauseExactlyInstanceOf(Class<? extends Throwable> type) {
        register.hasRootCauseExactlyInstanceOf(type);
        return myself;
    }

    @Override
    public ThrowableAssert as(String description, Object... args) {
        register.as(description, args);
        return myself;
    }

    @Override
    public ThrowableAssert as(Description description) {
        register.as(description);
        return myself;
    }

    @Override
    public ThrowableAssert describedAs(String description, Object... args) {
        register.describedAs(description, args);
        return myself;
    }

    @Override
    public ThrowableAssert describedAs(Description description) {
        register.describedAs(description);
        return myself;
    }

    @Override
    public ThrowableAssert isEqualTo(Object expected) {
        register.isEqualTo(expected);
        return myself;
    }

    @Override
    public ThrowableAssert isNotEqualTo(Object other) {
        register.isNotEqualTo(other);
        return myself;
    }

    @Override
    public void isNull() {
        register.isNull();
    }

    @Override
    public ThrowableAssert isNotNull() {
        register.isNotNull();
        return myself;
    }

    @Override
    public ThrowableAssert isSameAs(Object expected) {
        register.isSameAs(expected);
        return myself;
    }

    @Override
    public ThrowableAssert isNotSameAs(Object other) {
        register.isNotSameAs(other);
        return myself;
    }

    @Override
    public ThrowableAssert isIn(Object... values) {
        register.isIn(values);
        return myself;
    }

    @Override
    public ThrowableAssert isNotIn(Object... values) {
        register.isNotIn(values);
        return myself;
    }

    @Override
    public ThrowableAssert isIn(Iterable<?> values) {
        register.isIn(values);
        return myself;
    }

    @Override
    public ThrowableAssert isNotIn(Iterable<?> values) {
        register.isNotIn(values);
        return myself;
    }

    @Override
    public ThrowableAssert is(Condition<? super Throwable> condition) {
        register.is(condition);
        return myself;
    }

    @Override
    public ThrowableAssert isNot(Condition<? super Throwable> condition) {
        register.isNot(condition);
        return myself;
    }

    @Override
    public ThrowableAssert has(Condition<? super Throwable> condition) {
        register.has(condition);
        return myself;
    }

    @Override
    public ThrowableAssert doesNotHave(Condition<? super Throwable> condition) {
        register.doesNotHave(condition);
        return myself;
    }

    @Override
    public ThrowableAssert isInstanceOf(Class<?> type) {
        register.isInstanceOf(type);
        return myself;
    }

    @Override
    public ThrowableAssert isInstanceOfAny(Class<?>... types) {
        register.isInstanceOfAny(types);
        return myself;
    }

    @Override
    public ThrowableAssert isNotInstanceOf(Class<?> type) {
        register.isNotInstanceOf(type);
        return myself;
    }

    @Override
    public ThrowableAssert isNotInstanceOfAny(Class<?>... types) {
        register.isNotInstanceOfAny(types);
        return myself;
    }

    @Override
    public ThrowableAssert hasSameClassAs(Object other) {
        register.hasSameClassAs(other);
        return myself;
    }

    @Override
    public ThrowableAssert doesNotHaveSameClassAs(Object other) {
        register.doesNotHaveSameClassAs(other);
        return myself;
    }

    @Override
    public ThrowableAssert isExactlyInstanceOf(Class<?> type) {
        register.isExactlyInstanceOf(type);
        return myself;
    }

    @Override
    public ThrowableAssert isNotExactlyInstanceOf(Class<?> type) {
        register.isNotExactlyInstanceOf(type);
        return myself;
    }

    @Override
    public ThrowableAssert isOfAnyClassIn(Class<?>... types) {
        register.isOfAnyClassIn(types);
        return myself;
    }

    @Override
    public ThrowableAssert isNotOfAnyClassIn(Class<?>... types) {
        register.isNotOfAnyClassIn(types);
        return myself;
    }

    @Override
    public String descriptionText() {
        register.descriptionText();
        return null;
    }

    @Override
    public ThrowableAssert overridingErrorMessage(String newErrorMessage, Object... args) {
        register.overridingErrorMessage(newErrorMessage, args);
        return myself;
    }

    @Override
    public ThrowableAssert usingComparator(Comparator<? super Throwable> customComparator) {
        register.usingComparator(customComparator);
        return myself;
    }

    @Override
    public ThrowableAssert usingDefaultComparator() {
        register.usingDefaultComparator();
        return myself;
    }


}
