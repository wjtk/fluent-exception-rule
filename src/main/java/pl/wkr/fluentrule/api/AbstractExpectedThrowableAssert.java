package pl.wkr.fluentrule.api;

import org.assertj.core.api.*;
import org.assertj.core.description.Description;
import pl.wkr.fluentrule.util.ClassFinder;

import java.util.Comparator;

abstract public class AbstractExpectedThrowableAssert<
            S extends AbstractExpectedThrowableAssert<S,T,A>,
            T extends Throwable,
            A extends AbstractThrowableAssert<A,T>
            >
        extends AbstractThrowableAssert<S,T>{

    private final static int T_TYPE_PARAMETER_INDEX = 1;
    private final static ClassFinder CLASS_FINDER = new ClassFinder(T_TYPE_PARAMETER_INDEX);


    private final AssertCommandList<A, T> commands;

    //no inspection WeakerAccess
    protected final ThrowableAssert descriptionHolderAssert = Assertions.assertThat((Throwable)null);

    @SuppressWarnings("unchecked")
    protected AbstractExpectedThrowableAssert(Class<?> selfType, AssertCommandListCollector collector)  {
        super(null, selfType);
        commands = createAssertComandList(collector, getClassOfT());
    }

    private AssertCommandList<A,T> createAssertComandList(AssertCommandListCollector addACL, Class<T> classT) {
        AssertFactory<A,T> factory = new AssertFactory<A, T>() {
            @Override
            A getAssert(T throwable) {
                return produceAssert(throwable);
            }
        };
        AssertCommandList<A, T> cmds = new AssertCommandList<A,T>(classT, factory);
        addACL.add(cmds);
        return cmds;
    }

    private Class<T> getClassOfT() {
        return CLASS_FINDER.findConcreteClass(getClass());
    }

    final protected void addCommand(AssertCommand<A,T> command) {
         commands.addCommand(command);
    }

    abstract protected A produceAssert(T throwable);

    //------------------ overrided from AbstractThrowableAssert -------------------------

    @Override
    public S hasMessage(final String message) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().hasMessage(message);
                    }
                });
        return myself;
    }

    @Override
    public S hasNoCause() {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().hasNoCause();
                    }
                });
        return myself;
    }

    @Override
    public S hasMessageStartingWith(final String description) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().hasMessageStartingWith(description);
                    }
                });
        return myself;
    }

    @Override
    public S hasMessageContaining(final String description) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().hasMessageContaining(description);
                    }
                });
        return myself;
    }

    @Override
    public S hasMessageEndingWith(final String description) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().hasMessageEndingWith(description);
                    }
                });
        return myself;
    }

    @Override
    public S hasCauseInstanceOf(final Class<? extends Throwable> type) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().hasCauseInstanceOf(type);
                    }
                });
        return myself;
    }

    @Override
    public S hasCauseExactlyInstanceOf(final Class<? extends Throwable> type) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().hasCauseExactlyInstanceOf(type);
                    }
                });
        return myself;
    }

    @Override
    public S hasRootCauseInstanceOf(final Class<? extends Throwable> type) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().hasRootCauseInstanceOf(type);
                    }
                });
        return myself;
    }

    @Override
    public S hasRootCauseExactlyInstanceOf(final Class<? extends Throwable> type) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().hasRootCauseExactlyInstanceOf(type);
                    }
                });
        return myself;
    }

    @Override
    public S as(final String description, final Object... args) {
        descriptionHolderAssert.as(description, args);
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().as(description, args);
                    }
                });
        return myself;
    }

    @Override
    public S as(final Description description) {
        descriptionHolderAssert.as(description);
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().as(description);
                    }
                });
        return myself;
    }

    @Override
    public S describedAs(final String description, final Object... args) {
        descriptionHolderAssert.describedAs(description,args);
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().describedAs(description, args);
                    }
                });
        return myself;
    }

    @Override
    public S describedAs(final Description description) {
        descriptionHolderAssert.describedAs(description);
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().describedAs(description);
                    }
                });
        return myself;
    }

    @Override
    public S isEqualTo(final Object expected) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isEqualTo(expected);
                    }
                });
        return myself;
    }

    @Override
    public S isNotEqualTo(final Object other) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isNotEqualTo(other);
                    }
                });
        return myself;
    }

    @Override
    public void isNull() {
        addCommand(new AssertCommand<A, T>() {
            @Override
            public void doCheck() {
                getAssert().isNull();
            }
        });
    }

    @Override
    public S isNotNull() {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isNotNull();
                    }
                });
        return myself;
    }

    @Override
    public S isSameAs(final Object expected) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isSameAs(expected);
                    }
                });
        return myself;
    }

    @Override
    public S isNotSameAs(final Object other) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isNotSameAs(other);
                    }
                });
        return myself;
    }

    @Override
    public S isIn(final Object... values) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isIn(values);
                    }
                });
        return myself;
    }

    @Override
    public S isNotIn(final Object... values) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isNotIn(values);
                    }
                });
        return myself;
    }

    @Override
    public S isIn(final Iterable<?> values) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isIn(values);
                    }
                });
        return myself;
    }

    @Override
    public S isNotIn(final Iterable<?> values) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isNotIn(values);
                    }
                });
        return myself;
    }

    @Override
    public S is(final Condition<? super T> condition) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().is(condition);
                    }
                });
        return myself;
    }

    @Override
    public S isNot(final Condition<? super T> condition) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isNot(condition);
                    }
                });
        return myself;
    }

    @Override
    public S has(final Condition<? super T> condition) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().has(condition);
                    }
                });
        return myself;
    }

    @Override
    public S doesNotHave(final Condition<? super T> condition) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().doesNotHave(condition);
                    }
                });
        return myself;
    }

    @Override
    public S isInstanceOf(final Class<?> type) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isInstanceOf(type);
                    }
                });
        return myself;
    }

    @Override
    public S isInstanceOfAny(final Class<?>... types) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isInstanceOfAny(types);
                    }
                });
        return myself;
    }

    @Override
    public S isNotInstanceOf(final Class<?> type) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isNotInstanceOf(type);
                    }
                });
        return myself;
    }

    @Override
    public S isNotInstanceOfAny(final Class<?>... types) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isNotInstanceOfAny(types);
                    }
                });
        return myself;
    }

    @Override
    public S hasSameClassAs(final Object other) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().hasSameClassAs(other);
                    }
                });
        return myself;
    }

    @Override
    public S doesNotHaveSameClassAs(final Object other) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().doesNotHaveSameClassAs(other);
                    }
                });
        return myself;
    }

    @Override
    public S isExactlyInstanceOf(final Class<?> type) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isExactlyInstanceOf(type);
                    }
                });
        return myself;
    }

    @Override
    public S isNotExactlyInstanceOf(final Class<?> type) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isNotExactlyInstanceOf(type);
                    }
                });
        return myself;
    }

    @Override
    public S isOfAnyClassIn(final Class<?>... types) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isOfAnyClassIn(types);
                    }
                });
        return myself;
    }

    @Override
    public S isNotOfAnyClassIn(final Class<?>... types) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().isNotOfAnyClassIn(types);
                    }
                });
        return myself;
    }

    @Override
    public String descriptionText() {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().descriptionText();
                    }
                });
        return descriptionHolderAssert.descriptionText();
    }

    @Override
    public S overridingErrorMessage(final String newErrorMessage, final Object... args) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().overridingErrorMessage(newErrorMessage, args);
                    }
                });
        return myself;
    }

    @Override
    public S usingComparator(final Comparator<? super T> customComparator) {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().usingComparator(customComparator);
                    }
                });
        return myself;
    }

    @Override
    public S usingDefaultComparator() {
        addCommand
                (new AssertCommand<A, T>() {
                    @Override
                    public void doCheck() {
                        getAssert().usingDefaultComparator();
                    }
                });
        return myself;
    }
}



