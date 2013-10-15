package pl.wkr.fluentrule.api;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Condition;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.description.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import pl.wkr.fluentrule.api.testutils.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class AbstractExpectedThrowableTest {

    private ExpectedThrowableTestAssert expectedAssert;
    private TestAssertCommandCollector commandCollector;
    private ThrowableAssertMock normalAssertMock;
    private Exception exception = new Exception();
    private ExpectedThrowableTestAssert returnedAssert;
    private ThrowableAssertMockRegister mockRegister;

    @Before
    public void before() {
        mockRegister = mock(ThrowableAssertMockRegister.class);
        commandCollector = new TestAssertCommandCollector();
        normalAssertMock =  new ThrowableAssertMock(exception, mockRegister);
        expectedAssert = new ExpectedThrowableTestAssert(commandCollector, normalAssertMock);
    }

    @After
    public void after() {
        assertThat(returnedAssert).isSameAs(expectedAssert);
        verifyNoMoreInteractions(mockRegister);
        //yes, because normal assert should get only methods called explicitly in expected assert
   }

    @Test
    public void should_override_all_methods() {
        returnedAssert = expectedAssert; // to satisfy before()
        Class<?> cAssert = AbstractThrowableAssert.class;
        Class<?> cExpAssert = AbstractExpectedThrowableAssert.class;

        Multiset<String> baseMethods =  HashMultiset.create();

        for( Method m : cAssert.getMethods()) {
            baseMethods.add(m.getName());
        }
        for( Method m :cExpAssert.getDeclaredMethods()) {
            if(Modifier.isPublic(m.getModifiers())) {
                baseMethods.remove(m.getName());
            }
        }
        baseMethods.removeAll(Arrays.asList(
                "hashCode", "wait", "wait", "wait", "notify", "notifyAll", "getClass", "equals", "toString"));

        //Guava-assertj is rather poor, have to use JDK collections
        List<String> notOverrided = new ArrayList<String>(baseMethods);
        assertThat(notOverrided).isEmpty();
    }


    @Test
    public void should_call_methods_exactly_in_sequence() {
        returnedAssert = expectedAssert
                .hasMessage("z")
                .isInstanceOfAny(Long.class, Number.class)
                .hasNoCause()
                .usingDefaultComparator();
        startChecking();

        InOrder inO = inOrder(mockRegister);

        inO.verify(mockRegister).hasMessage("z");
        inO.verify(mockRegister).isInstanceOfAny(Long.class, Number.class);
        inO.verify(mockRegister).hasNoCause();
        inO.verify(mockRegister).usingDefaultComparator();
        inO.verifyNoMoreInteractions();
    }

    //---- all methods -------------------------------------------------------

    @Test
    public void should_call_should_call_hasMessage() {
        String message = "x";
        returnedAssert = expectedAssert.hasMessage(message);
        startChecking();

        verify(mockRegister).hasMessage(message);
    }

    @Test
    public void should_call_hasNoCause() {
        returnedAssert = expectedAssert.hasNoCause();
        startChecking();

        verify(mockRegister).hasNoCause();
    }

    @Test
    public void should_call_hasMessageStartingWith() {
        String description = "x";
        returnedAssert = expectedAssert.hasMessageStartingWith(description);
        startChecking();

        verify(mockRegister).hasMessageStartingWith(description);
    }

    @Test
    public void should_call_hasMessageContaining() {
        String description = "x";
        returnedAssert = expectedAssert.hasMessageContaining(description);
        startChecking();

        verify(mockRegister).hasMessageContaining(description);
    }

    @Test
    public void should_call_hasMessageEndingWith() {
        String description = "x";
        returnedAssert = expectedAssert.hasMessageEndingWith(description);
        startChecking();

        verify(mockRegister).hasMessageEndingWith(description);
    }

    @Test
    public void should_call_hasCauseInstanceOf() {
        Class<? extends Throwable> type = MyException.class;
        returnedAssert = expectedAssert.hasCauseInstanceOf(type);
        startChecking();

        verify(mockRegister).hasCauseInstanceOf(type);
    }

    @Test
    public void should_call_hasCauseExactlyInstanceOf() {
        Class<? extends Throwable> type = MyException.class;
        returnedAssert = expectedAssert.hasCauseExactlyInstanceOf(type);
        startChecking();

        verify(mockRegister).hasCauseExactlyInstanceOf(type);
    }

    @Test
    public void should_call_hasRootCauseInstanceOf() {
        Class<? extends Throwable> type = MyException.class;
        returnedAssert = expectedAssert.hasRootCauseInstanceOf(type);
        startChecking();

        verify(mockRegister).hasRootCauseInstanceOf(type);
    }

    @Test
    public void should_call_hasRootCauseExactlyInstanceOf() {
        Class<? extends Throwable> type = MyException.class;
        returnedAssert = expectedAssert.hasRootCauseExactlyInstanceOf(type);
        startChecking();

        verify(mockRegister).hasRootCauseExactlyInstanceOf(type);
    }

    @Test
    public void should_call_as__withargs() {
        String description = "x";
        Object[] args = new Object[]{new Object()};
        returnedAssert = expectedAssert.as(description, args);
        startChecking();

        verify(mockRegister).as(description,args);
    }

    @Test
    public void should_call_as() {
        Description description = new MyDescription("x");
        returnedAssert = expectedAssert.as(description);
        startChecking();

        verify(mockRegister).as(description);
    }

    @Test
    public void should_call_describedAs__withargs() {
        String description = "x";
        Object[] args = new Object[]{new Object()};
        returnedAssert = expectedAssert.describedAs(description, args);
        startChecking();

        verify(mockRegister).describedAs(description, args);
    }

    @Test
    public void should_call_describedAs() {
        Description description = new MyDescription("x");
        returnedAssert = expectedAssert.describedAs(description);
        startChecking();

        verify(mockRegister).describedAs(description);
    }

    @Test
    public void should_call_isEqualTo() {
        Object expected = new Object();
        returnedAssert = expectedAssert.isEqualTo(expected);
        startChecking();

        verify(mockRegister).isEqualTo(expected);
    }

    @Test
    public void should_call_isNotEqualTo() {
        Object other = new Object();
        returnedAssert = expectedAssert.isNotEqualTo(other);
        startChecking();

        verify(mockRegister).isNotEqualTo(other);
    }

    @Test
    public void should_call_isNull() {
        returnedAssert = expectedAssert;
        expectedAssert.isNull();
        startChecking();

        verify(mockRegister).isNull();
    }

    @Test
    public void should_call_isNotNull() {
        returnedAssert = expectedAssert.isNotNull();
        startChecking();

        verify(mockRegister).isNotNull();
    }

    @Test
    public void should_call_isSameAs() {
        Object expected = new Object();
        returnedAssert = expectedAssert.isSameAs(expected);
        startChecking();

        verify(mockRegister).isSameAs(expected);
    }

    @Test
    public void should_call_isNotSameAs() {
        Object other = new Object();
        returnedAssert = expectedAssert.isNotSameAs(other);
        startChecking();

        verify(mockRegister).isNotSameAs(other);
    }

    @Test
    public void should_call_isIn() {
        Object[] values = new Object[]{new Object()};
        returnedAssert = expectedAssert.isIn(values);
        startChecking();

        verify(mockRegister).isIn(values);
    }

    @Test
    public void should_call_isNotIn() {
        Object[] values = new Object[]{new Object()};
        returnedAssert = expectedAssert.isNotIn(values);
        startChecking();

        verify(mockRegister).isNotIn(values);
    }

    @Test
    public void should_call_isIn__with_iterable() {
        Iterable<?> values = Arrays.asList(new Object());
        returnedAssert = expectedAssert.isIn(values);
        startChecking();

        verify(mockRegister).isIn(values);
    }

    @Test
    public void should_call_isNotIn__with_iterables() {
        Iterable<?> values = Arrays.asList(new Object());
        returnedAssert = expectedAssert.isNotIn(values);
        startChecking();

        verify(mockRegister).isNotIn(values);
    }

    @Test
    public void should_call_is() {
        Condition<Throwable> condition = new MyCondition();
        returnedAssert = expectedAssert.is(condition);
        startChecking();

        verify(mockRegister).is(condition);
    }

    @Test
    public void should_call_isNot() {
        Condition<Throwable> condition = new MyCondition();
        returnedAssert = expectedAssert.isNot(condition);
        startChecking();

        verify(mockRegister).isNot(condition);
    }

    @Test
    public void should_call_has() {
        Condition<Throwable> condition = new MyCondition();
        returnedAssert = expectedAssert.has(condition);
        startChecking();

        verify(mockRegister).has(condition);
    }

    @Test
    public void should_call_doesNotHave() {
        Condition<Throwable> condition = new MyCondition();
        returnedAssert = expectedAssert.doesNotHave(condition);
        startChecking();

        verify(mockRegister).doesNotHave(condition);
    }

    @Test
    public void should_call_isInstanceOf() {
        Class<?> type = Long.class;
        returnedAssert = expectedAssert.isInstanceOf(type);
        startChecking();

        verify(mockRegister).isInstanceOf(type);
    }

    @Test
    public void should_call_isInstanceOfAny() {
        Class<?>[] types = new Class<?>[]{ Number.class};
        returnedAssert = expectedAssert.isInstanceOfAny(types);
        startChecking();

        verify(mockRegister).isInstanceOfAny(types);
    }

    @Test
    public void should_call_isNotInstanceOf() {
        Class<?> type = Number.class;
        returnedAssert = expectedAssert.isNotInstanceOf(type);
        startChecking();

        verify(mockRegister).isNotInstanceOf(type);
    }

    @Test
    public void should_call_isNotInstanceOfAny() {
        Class<?>[] types = new Class<?>[]{ Number.class};
        returnedAssert = expectedAssert.isNotInstanceOfAny(types);
        startChecking();

        verify(mockRegister).isNotInstanceOfAny(types);
    }

    @Test
    public void should_call_hasSameClassAs() {
        Object other = new Object();
        returnedAssert = expectedAssert.hasSameClassAs(other);
        startChecking();

        verify(mockRegister).hasSameClassAs(other);
    }

    @Test
    public void should_call_doesNotHaveSameClassAs() {
        Object other = new Object();
        returnedAssert = expectedAssert.doesNotHaveSameClassAs(other);
        startChecking();

        verify(mockRegister).doesNotHaveSameClassAs(other);
    }

    @Test
    public void should_call_isExactlyInstanceOf() {
        Class<?> type = Long.class;
        returnedAssert = expectedAssert.isExactlyInstanceOf(type);
        startChecking();

        verify(mockRegister).isExactlyInstanceOf(type);
    }

    @Test
    public void should_call_isNotExactlyInstanceOf() {
        Class<?> type = Long.class;
        returnedAssert = expectedAssert.isNotExactlyInstanceOf(type);
        startChecking();

        verify(mockRegister).isNotExactlyInstanceOf(type);
    }

    @Test
    public void should_call_isOfAnyClassIn() {
        Class<?>[] types = new Class<?>[]{ Number.class};
        returnedAssert = expectedAssert.isOfAnyClassIn(types);
        startChecking();

        verify(mockRegister).isOfAnyClassIn(types);
    }

    @Test
    public void should_call_isNotOfAnyClassIn() {
        Class<?>[] types = new Class<?>[]{ Number.class};
        returnedAssert = expectedAssert.isNotOfAnyClassIn(types);
        startChecking();

        verify(mockRegister).isNotOfAnyClassIn(types);
    }

    @Test
    public void should_call_descriptionText() {
        returnedAssert = expectedAssert;
        expectedAssert.descriptionText();
        startChecking();

        verify(mockRegister).descriptionText();
    }

    @Test
    public void should_call_overridingErrorMessage() {
        String newErrorMessage = "x";
        Object[] args = new Object[]{ new Object()};
        returnedAssert = expectedAssert.overridingErrorMessage(newErrorMessage, args);
        startChecking();

        verify(mockRegister).overridingErrorMessage(newErrorMessage, args);
    }

    @Test
    public void should_call_usingComparator() {
        Comparator<? super Throwable> customComparator = new MyComparator();
        returnedAssert = expectedAssert.usingComparator(customComparator);
        startChecking();

        verify(mockRegister).usingComparator(customComparator);
    }

    @Test
    public void should_call_usingDefaultComparator() {
        returnedAssert = expectedAssert.usingDefaultComparator();
        startChecking();

        verify(mockRegister).usingDefaultComparator();
    }

    // as, describeAs, descriptionText should be already visible, not only passed to normal assertion
    // when exception is checked

    @Test
    public void should_assert_description_changes_be_already_visible() {
        SoftAssertions soft = new SoftAssertions();

        expectedAssert.as(new MyDescription("a"));
        soft.assertThat(expectedAssert.descriptionText()).as("as(Description)").isEqualTo("a");

        expectedAssert.as("a%s","b");
        soft.assertThat(expectedAssert.descriptionText()).as("as(String, Object...)").isEqualTo("ab");

        expectedAssert.describedAs(new MyDescription("c"));
        soft.assertThat(expectedAssert.descriptionText()).as("describeAs(Description)").isEqualTo("c");

        returnedAssert = expectedAssert.describedAs("d%s", "e");
        soft.assertThat(expectedAssert.descriptionText()).as("describeAs(String, Object...)").isEqualTo("de");

        soft.assertAll();
    }

    //-------------------------------------------------------------------------------------

     private void startChecking() {
         commandCollector.doChecks(exception);
     }

    static class ExpectedThrowableTestAssert extends
            AbstractExpectedThrowableAssert<ExpectedThrowableTestAssert, Throwable, ThrowableAssertMock> {

        private ThrowableAssertMock mock;

        ExpectedThrowableTestAssert(AssertCommandListCollector collector, ThrowableAssertMock mock) {
            super(ExpectedThrowableTestAssert.class, collector);
            this.mock = mock;
        }

        @Override
        protected ThrowableAssertMock produceAssert(Throwable throwable) {
            return mock;
        }
    }
}
