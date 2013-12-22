package pl.wkr.fluentrule.proxy.throwableassert_;

import org.assertj.core.api.Condition;
import org.assertj.core.description.Description;

import java.util.Comparator;
public interface AbstractThrowableAssertMockRegister<A extends Throwable> {
    public void hasMessage(String message);
    public void hasNoCause();
    public void hasMessageStartingWith(String description);
    public void hasMessageContaining(String description);
    public void hasMessageEndingWith(String description);
    public void hasCauseInstanceOf(Class<? extends Throwable> type);
    public void hasCauseExactlyInstanceOf(Class<? extends Throwable> type);
    public void hasRootCauseInstanceOf(Class<? extends Throwable> type);
    public void hasRootCauseExactlyInstanceOf(Class<? extends Throwable> type);
    public void as(String description, Object... args);
    public void as(Description description);
    public void describedAs(String description, Object... args);
    public void describedAs(Description description);
    public void isEqualTo(Object expected);
    public void isNotEqualTo(Object other);
    public void isNull();
    public void isNotNull();
    public void isSameAs(Object expected);
    public void isNotSameAs(Object other);
    public void isIn(Object... values);
    public void isNotIn(Object... values);
    public void isIn(Iterable<?> values);
    public void isNotIn(Iterable<?> values);
    public void is(Condition<? super A> condition);
    public void isNot(Condition<? super A> condition);
    public void has(Condition<? super A> condition);
    public void doesNotHave(Condition<? super A> condition);
    public void isInstanceOf(Class<?> type);
    public void isInstanceOfAny(Class<?>... types);
    public void isNotInstanceOf(Class<?> type);
    public void isNotInstanceOfAny(Class<?>... types);
    public void hasSameClassAs(Object other);
    public void doesNotHaveSameClassAs(Object other);
    public void isExactlyInstanceOf(Class<?> type);
    public void isNotExactlyInstanceOf(Class<?> type);
    public void isOfAnyClassIn(Class<?>... types);
    public void isNotOfAnyClassIn(Class<?>... types);
    public void descriptionText();
    public void overridingErrorMessage(String newErrorMessage, Object... args);
    public void usingComparator(Comparator<? super A> customComparator);
    public void usingDefaultComparator();
}
