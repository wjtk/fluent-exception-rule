package pl.wkr.fluentrule.api.cglib;

import com.google.common.base.Joiner;
import org.junit.Test;
import pl.wkr.fluentrule.api.extending.SQLExceptionAssert;

import java.lang.reflect.Constructor;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionAssertFactoryTest {


    @Test
    public void should_print_constructors() throws NoSuchMethodException {
        Constructor<?>[] cstrs = SQLExceptionAssert.class.getConstructors();

        for(Constructor<?> cstr : cstrs) {
            printConstructor(cstr);
        }
        System.out.println("declared constructor:");
        printConstructor(SQLExceptionAssert.class.getDeclaredConstructor(SQLException.class));
        System.out.println("-------------");

    }

    private void printConstructor(Constructor<?> cstr) {
        Joiner j = Joiner.on(", ");
        System.out.printf("constructor(%s) %n", j.join(cstr.getParameterTypes()));
    }

    @Test
    public void should_create_assert() {
        ReflectionAssertFactory<SQLExceptionAssert,SQLException> f =
                new ReflectionAssertFactory<SQLExceptionAssert,SQLException>(SQLExceptionAssert.class, SQLException.class);
        SQLExceptionAssert anAssert = f.getAssert(null);
        assertThat(anAssert).isNotNull();

    }


}
