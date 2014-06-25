package pl.wkr.fluentrule.proxy.cglib;


import org.junit.Before;
import org.junit.Test;
import pl.wkr.fluentrule.proxy.method.acceptor.MethodAcceptor;
import pl.wkr.fluentrule.test_.BaseWithFluentThrownTest;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MethodCallbackFilter_Test extends BaseWithFluentThrownTest {

    private MethodAcceptor foo1Acc = mock(MethodAcceptor.class);
    private MethodAcceptor foo2Acc = mock(MethodAcceptor.class);
    private MethodCallbackFilter filter;

    @Before
    public void before() throws NoSuchMethodException {
        when(foo1Acc.accept(method("foo1"))).thenReturn(true);
        when(foo2Acc.accept(method("foo2"))).thenReturn(true);

        filter = new MethodCallbackFilter(Arrays.asList(foo1Acc, foo2Acc));
    }


    @Test
    public void should_return_proper_indices() throws NoSuchMethodException {
        assertThat(filter.accept(method("foo1"))).isEqualTo(0);
        assertThat(filter.accept(method("foo2"))).isEqualTo(1);
    }

    @Test
    public void should_throw_when_method_was_not_accepted_by_any_method_accepter() throws NoSuchMethodException {
        thrown.expect(IllegalStateException.class).hasMessage(String.format(
                "Method [%s:%s] is not accepted by any method accepter",
                Foo.class,
                "foo3"));
        filter.accept(method("foo3"));
    }


    private Method method(String name) throws NoSuchMethodException {
        return Foo.class.getMethod(name);
    }


    @SuppressWarnings("unused")
    static class Foo {

        public void foo1() {
        }

        public void foo2() {
        }

        public void foo3() {
        }
    }
}