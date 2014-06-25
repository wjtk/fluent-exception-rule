package pl.wkr.fluentrule.proxy.cglib;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import pl.wkr.fluentrule.proxy.CalledMethodRegister;
import pl.wkr.fluentrule.proxy.RegisteringProxyFactoryFactory;
import pl.wkr.fluentrule.test_.SQLExceptionAssert;
import pl.wkr.fluentrule.util.TypeDefaults;

import java.lang.reflect.Method;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

public class FactoryIT {

    private RegisteringProxyFactoryFactory rpff = new Factory(new TypeDefaults()).getRegisteringProxyFactoryFactory();
    private CalledMethodRegister methodRegister;


    @Before
    public void before() {
        methodRegister = mock(CalledMethodRegister.class);
    }


    @Test
    public void should_create_proxy_register_all_called_methods_in_order_and_return_proper_values() throws NoSuchMethodException {
        SQLExceptionAssert assertProxy =
                rpff.newRegisteringProxyFactory(SQLExceptionAssert.class, SQLException.class).createProxy(methodRegister);


        SQLExceptionAssert shouldBeProxy1 = assertProxy.hasErrorCode(10);
        SQLExceptionAssert shouldBeProxy2 = assertProxy.hasMessage("x");
        String shouldBeNull = assertProxy.descriptionText();
        int shouldBeZero = assertProxy.hashCode();
        assertProxy.isNull();


        assertThat(shouldBeProxy1).isSameAs(assertProxy);
        assertThat(shouldBeProxy2).isSameAs(assertProxy);
        assertThat(shouldBeNull).isNull();
        assertThat(shouldBeZero).isEqualTo(0);

        InOrder io = inOrder(methodRegister);
        io.verify(methodRegister).wasCalled(m("hasErrorCode", int.class), 10);
        io.verify(methodRegister).wasCalled(m("hasMessage", String.class), "x");
        io.verify(methodRegister).wasCalled(m("descriptionText"));
        io.verify(methodRegister).wasCalled(m("hashCode"));
        io.verify(methodRegister).wasCalled(m("isNull"));
    }

    private Method m(String name, Class<?> ... argTypes) throws NoSuchMethodException {
        return SQLExceptionAssert.class.getMethod(name, argTypes);
    }
}
