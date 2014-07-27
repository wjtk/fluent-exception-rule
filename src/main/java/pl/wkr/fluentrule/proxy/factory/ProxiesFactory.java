package pl.wkr.fluentrule.proxy.factory;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.ThrowableAssert;
import pl.wkr.fluentrule.assertfactory.ExtractingAssertFactoryFactory;
import pl.wkr.fluentrule.assertfactory.ReflectionAssertFactoryFactory;
import pl.wkr.fluentrule.assertfactory.ThrowableAssertFactory;
import pl.wkr.fluentrule.extractor.ThrowableExtractor;
import pl.wkr.fluentrule.proxy.CheckWithProxy;
import pl.wkr.fluentrule.proxy.CheckWithProxyFactory;
import pl.wkr.fluentrule.util.ClassFinder;

import static org.assertj.core.util.Preconditions.checkNotNull;


/**
 * Class for creating proxies.
 */
public class ProxiesFactory {

    private static final int THROWABLE_TYPE_INDEX_IN_ABSTRACT_THROWABLE_ASSERT = 1;
    private static final ClassFinder THROWABLE_CLASS_FINDER = new ClassFinder(THROWABLE_TYPE_INDEX_IN_ABSTRACT_THROWABLE_ASSERT);

    private final CheckWithProxyFactory checkWithProxyFactory;
    private final ThrowableExtractor noopExtractor;
    private final ThrowableExtractor causeExtractor;
    private final ThrowableExtractor rootCauseExtractor;

    private final ReflectionAssertFactoryFactory reflectionAssertFactoryFactory;
    private final ThrowableAssertFactory throwableAssertFactory;

    private final ExtractingAssertFactoryFactory extractingAssertFactoryFactory;

    public ProxiesFactory(CheckWithProxyFactory checkWithProxyFactory,
                            ThrowableExtractor noopExtractor,
                            ThrowableExtractor causeExtractor,
                            ThrowableExtractor rootCauseExtractor,
                            ThrowableAssertFactory throwableAssertFactory,
                            ReflectionAssertFactoryFactory reflectionAssertFactoryFactory,
                            ExtractingAssertFactoryFactory extractingAssertFactoryFactory) {
        this.checkWithProxyFactory = checkWithProxyFactory;
        this.noopExtractor = noopExtractor;
        this.causeExtractor = causeExtractor;
        this.rootCauseExtractor = rootCauseExtractor;
        this.throwableAssertFactory = throwableAssertFactory;
        this.reflectionAssertFactoryFactory = reflectionAssertFactoryFactory;
        this.extractingAssertFactoryFactory = extractingAssertFactoryFactory;
    }

    public CheckWithProxy<ThrowableAssert,Throwable> newThrowableAssertProxy() {
        return innerNewThrowableAssertProxy(noopExtractor);
    }

    public CheckWithProxy<ThrowableAssert,Throwable> newThrowableCauseAssertProxy() {
        return innerNewThrowableAssertProxy(causeExtractor);
    }

    public CheckWithProxy<ThrowableAssert,Throwable> newThrowableRootCauseAssertProxy() {
        return innerNewThrowableAssertProxy(rootCauseExtractor);
    }

    public <A extends AbstractThrowableAssert<A,T>, T extends Throwable>
        CheckWithProxy<A,T> newThrowableCustomAssertProxy(Class<A> assertClass) {

        return innerNewCustomAssertProxy(assertClass, noopExtractor);
    }

    public <A extends AbstractThrowableAssert<A,T>, T extends Throwable>
        CheckWithProxy<A,T> newThrowableCauseCustomAssertProxy(Class<A> assertClass) {

        return innerNewCustomAssertProxy(assertClass, causeExtractor);
    }

    public <A extends AbstractThrowableAssert<A,T>, T extends Throwable>
    CheckWithProxy<A,T> newThrowableRootCauseCustomAssertProxy(Class<A> assertClass) {

        return innerNewCustomAssertProxy(assertClass, rootCauseExtractor);
    }

    //--------------------------------------------------------------------------------

    private CheckWithProxy<ThrowableAssert,Throwable> innerNewThrowableAssertProxy(ThrowableExtractor extractor) {
        return checkWithProxyFactory.newCheckWithProxy(ThrowableAssert.class, Throwable.class,
                extractingAssertFactoryFactory.newAssertFactory(throwableAssertFactory, extractor));
    }

    private <A extends AbstractThrowableAssert<A,T>, T extends Throwable>
        CheckWithProxy<A,T> innerNewCustomAssertProxy(Class<A> assertClass, ThrowableExtractor extractor) {

        checkNotNull(assertClass);
        Class<T> throwableClass = THROWABLE_CLASS_FINDER.findConcreteClass(assertClass);

        return checkWithProxyFactory.newCheckWithProxy(assertClass, throwableClass,
                extractingAssertFactoryFactory.newAssertFactory(
                        reflectionAssertFactoryFactory.newAssertFactory(assertClass, throwableClass),
                        extractor));

    }
}
