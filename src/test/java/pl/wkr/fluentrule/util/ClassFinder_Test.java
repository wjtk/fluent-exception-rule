package pl.wkr.fluentrule.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassFinder_Test {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ClassFinder c0 = new ClassFinder(0);
    private ClassFinder c1 = new ClassFinder(1);
    private ClassFinder c2 = new ClassFinder(2);



    @Test
    public void should_discover_class_from_concrete_class() {
        Class<?> cLong = c0.findConcreteClass(Concrete.class);
        Class<?> cString = c1.findConcreteClass(Concrete.class);
        Class<?> cDouble = c2.findConcreteClass(Concrete.class);


        assertThat(cLong).isEqualTo(Long.class);
        assertThat(cDouble).isEqualTo(Double.class);
        assertThat(cString).isEqualTo(String.class);
    }


    @Test
    public void will_not_discover_class_from_generic_class() {
        Generic<String, Double, Long> g = new Generic<String, Double, Long>();

        thrown.expectMessage("Cannot find concrete class. Class 'forClass' cannot be generic!");

        c0.findConcreteClass(g.getClass());
    }

    //helper classes

    @SuppressWarnings("unused")
    static abstract class Base<T,U> {}

    @SuppressWarnings("unused")
    static class Generic<T,U,V> extends Base<T,U>{}

    static class Concrete extends Generic<Long, String, Double>{}
}
