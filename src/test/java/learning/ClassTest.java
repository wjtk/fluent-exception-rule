package learning;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Method;

public class ClassTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void should_cast() {
        Class<? extends AbstractThrowableAssert> aClass = ThrowableAssert.class.asSubclass(AbstractThrowableAssert.class);
    }

    @Test
    public void should_not_cast() {
        thrown.expect(ClassCastException.class);
        Class<? extends ThrowableAssert> aClass = AbstractThrowableAssert.class.asSubclass(ThrowableAssert.class);
    }

    @Test
    public void should_returnType_for_void_method_be_null() throws NoSuchMethodException {
        Method method = this.getClass().getMethod("voidMethod");
        System.out.println(method.getReturnType());  //void, what is "void"???

        //assertThat(method.getReturnType().getClass())
    }

    @Test
    public void int_is_not_object() {
        thrown.expect(ClassCastException.class);
        int.class.asSubclass(Object.class);
    }


    @Test
    public void  Integer_is_object() {
        Integer.class.asSubclass(Object.class);
    }

    @Test
    public void int_class_is_class_of_question_mark() {
        Class<?> c = int.class;
    }

    //----------------
    public void voidMethod() {}
}
