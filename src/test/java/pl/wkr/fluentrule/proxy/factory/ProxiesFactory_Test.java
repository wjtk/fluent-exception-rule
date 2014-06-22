package pl.wkr.fluentrule.proxy.factory;

import org.assertj.core.api.ThrowableAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import pl.wkr.fluentrule.assertfactory.AssertFactory;
import pl.wkr.fluentrule.assertfactory.ExtractingAssertFactoryFactory;
import pl.wkr.fluentrule.assertfactory.ReflectionAssertFactoryFactory;
import pl.wkr.fluentrule.assertfactory.ThrowableAssertFactory;
import pl.wkr.fluentrule.extractor.ThrowableExtractor;
import pl.wkr.fluentrule.proxy.CheckWithProxy;
import pl.wkr.fluentrule.proxy.CheckWithProxyFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProxiesFactory_Test {

    ProxiesFactory proxiesFactory;

    @Mock private CheckWithProxyFactory checkWithProxyFactory;
    @Mock private ThrowableExtractor noopExtractor;
    @Mock private ThrowableExtractor causeExtractor;
    @Mock private ThrowableExtractor rootCauseExtractor;
    @Mock private ThrowableAssertFactory throwableAssertFactory;
    @Mock private ReflectionAssertFactoryFactory reflectionAssertFactoryFactory;
    @Mock private ExtractingAssertFactoryFactory extractingAssertFactoryFactory;

    @Mock private AssertFactory<ThrowableAssert, Throwable> extractingAssertFactory;
    @Mock private AssertFactory<?,?> reflectionAssertFactory;
    @Mock private CheckWithProxy<?,?> checkWithProxy;

    private CheckWithProxy<?,?> returned = null;
    private boolean checkAfter = true;


    @Before
    public void before() {
        initMocks(this);

        when(extractingAssertFactoryFactory.newAssertFactory(any(AssertFactory.class), any(ThrowableExtractor.class)))
                .thenReturn(extractingAssertFactory);

        when(reflectionAssertFactoryFactory.newAssertFactory(any(Class.class), any(Class.class)))
                .thenReturn(reflectionAssertFactory);

        when(checkWithProxyFactory.newCheckWithProxy(any(Class.class), any(Class.class), any(AssertFactory.class)))
                .thenReturn(checkWithProxy);

        proxiesFactory = new ProxiesFactory(checkWithProxyFactory, noopExtractor, causeExtractor, rootCauseExtractor,
                throwableAssertFactory, reflectionAssertFactoryFactory, extractingAssertFactoryFactory);
    }

    @After
    public void after() {
        if(checkAfter) {
          checkAfter();
        }
    }

    private void checkAfter() {
        assertThat(returned).isSameAs(checkWithProxy);

        verifyNoMoreInteractions(
                checkWithProxyFactory,
                noopExtractor, causeExtractor, rootCauseExtractor,
                throwableAssertFactory, reflectionAssertFactory, extractingAssertFactoryFactory,
                extractingAssertFactory, reflectionAssertFactory, checkWithProxyFactory
        );
    }

    /*
    return checkWithProxyFactory.newCheckWithProxy(ThrowableAssert.class, Throwable.class,
            extractingAssertFactoryFactory.newAssertFactory(throwableAssertFactory, extractor));
     */

    @Test
    public void should_call_dependencies_newThrowableAssertProxy() {
        returned = proxiesFactory.newThrowableAssertProxy();

        verify(checkWithProxyFactory).newCheckWithProxy(ThrowableAssert.class, Throwable.class, extractingAssertFactory);
        verify(extractingAssertFactoryFactory).newAssertFactory(throwableAssertFactory, noopExtractor);
    }

    @Test
    public void should_call_dependencies_newThrowableCauseAssertProxy() {
        returned = proxiesFactory.newThrowableCauseAssertProxy();

        verify(checkWithProxyFactory).newCheckWithProxy(ThrowableAssert.class, Throwable.class, extractingAssertFactory);
        verify(extractingAssertFactoryFactory).newAssertFactory(throwableAssertFactory, causeExtractor);
    }

    @Test
    public void should_call_dependencies_newThrowableRootCauseAssertProxy() {
        returned = proxiesFactory.newThrowableRootCauseAssertProxy();

        verify(checkWithProxyFactory).newCheckWithProxy(ThrowableAssert.class, Throwable.class, extractingAssertFactory);
        verify(extractingAssertFactoryFactory).newAssertFactory(throwableAssertFactory, rootCauseExtractor);
    }

    @Test
    public void should_call_dependencies_newThrowableCustomAssertProxy() {
        returned = proxiesFactory.newThrowableCustomAssertProxy(ThrowableAssert.class);

        verify(checkWithProxyFactory).newCheckWithProxy(ThrowableAssert.class, Throwable.class, extractingAssertFactory);
        verify(extractingAssertFactoryFactory).newAssertFactory(reflectionAssertFactory, noopExtractor);
        verify(reflectionAssertFactoryFactory).newAssertFactory(ThrowableAssert.class, Throwable.class);
    }

    @Test
    public void should_call_dependencies_newThrowableCauseCustomAssertProxy() {
        returned = proxiesFactory.newThrowableCauseCustomAssertProxy(ThrowableAssert.class);

        verify(checkWithProxyFactory).newCheckWithProxy(ThrowableAssert.class, Throwable.class, extractingAssertFactory);
        verify(extractingAssertFactoryFactory).newAssertFactory(reflectionAssertFactory, causeExtractor);
        verify(reflectionAssertFactoryFactory).newAssertFactory(ThrowableAssert.class, Throwable.class);
    }

    @Test
    public void should_call_dependencies_newThrowableRootCauseCustomAssertProxy() {
        returned = proxiesFactory.newThrowableRootCauseCustomAssertProxy(ThrowableAssert.class);

        verify(checkWithProxyFactory).newCheckWithProxy(ThrowableAssert.class, Throwable.class, extractingAssertFactory);
        verify(extractingAssertFactoryFactory).newAssertFactory(reflectionAssertFactory, rootCauseExtractor);
        verify(reflectionAssertFactoryFactory).newAssertFactory(ThrowableAssert.class, Throwable.class);
    }



}
