package pl.wkr.fluentrule.assertfactory;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.ThrowableAssert;
import pl.wkr.fluentrule.proxy.CheckWithProxy;
import pl.wkr.fluentrule.proxy.CheckWithProxyFactory;
import pl.wkr.fluentrule.util.ClassFinder;

import static org.assertj.core.util.Preconditions.checkNotNull;

public class ProxiesFactory {

    private static final CheckWithProxyFactory PROXY_FACTORY = new CheckWithProxyFactory();
    private static final ThrowableExtractor NOOP_EXTRACTOR = new NoopExtractor();
    private static final ThrowableExtractor CAUSE_EXTRACTOR = new CauseExtractor();
    private static final ThrowableExtractor ROOT_CAUSE_EXTRACTOR = new RootCauseExtractor();

    private static final ReflectionAssertFactoryFactory REFLECTION_ASSERT_FACTORY_FACTORY = new ReflectionAssertFactoryFactory();
    private static final ThrowableAssertFactory THROWABLE_ASSERT_FACTORY = new ThrowableAssertFactory();

    private static final ExtractingAssertFactoryFactory EXTRACTING_ASSERT_FACTORY_FACTORY = new ExtractingAssertFactoryFactory();

    private static final int THROWABLE_TYPE_INDEX_IN_AbstractThrowableAssert = 1;
    private static final ClassFinder throwableClassFinder = new ClassFinder(THROWABLE_TYPE_INDEX_IN_AbstractThrowableAssert);

    private final CheckWithProxyFactory checkWithProxyFactory;
    private final ThrowableExtractor noopExtractor;
    private final ThrowableExtractor causeExtractor;
    private final ThrowableExtractor rootCauseExtractor;

    private final ReflectionAssertFactoryFactory reflectionAssertFactoryFactory;
    private final ThrowableAssertFactory throwableAssertFactory;

    private final ExtractingAssertFactoryFactory extractingAssertFactoryFactory;


    public ProxiesFactory(){
        this(PROXY_FACTORY, NOOP_EXTRACTOR, CAUSE_EXTRACTOR, ROOT_CAUSE_EXTRACTOR,
                THROWABLE_ASSERT_FACTORY,
                REFLECTION_ASSERT_FACTORY_FACTORY,
                EXTRACTING_ASSERT_FACTORY_FACTORY);
    }

    ProxiesFactory(CheckWithProxyFactory checkWithProxyFactory,
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
        Class<T> throwableClass = throwableClassFinder.findConcreteClass(assertClass);

        return checkWithProxyFactory.newCheckWithProxy(assertClass, throwableClass,
                extractingAssertFactoryFactory.newAssertFactory(
                        reflectionAssertFactoryFactory.newAssertFactory(assertClass, throwableClass),
                        extractor));

    }
}
