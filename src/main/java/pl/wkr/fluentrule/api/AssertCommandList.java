package pl.wkr.fluentrule.api;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class AssertCommandList<A extends AbstractThrowableAssert<A,T>, T extends Throwable>{

    private List<AssertCommand<A,T>> commands = new ArrayList<AssertCommand<A, T>>();
    private AssertFactory<A, T> factory;
    private Class<T> classT;

    public AssertCommandList(Class<T> classT, AssertFactory<A,T> factory) {
        this.classT = classT;
        this.factory = factory;
    }

    public void addCommand(AssertCommand<A,T> command) {
        commands.add(command);
    }

    public void check(Throwable e) {
        Assertions.assertThat(e).isInstanceOf(classT);
        A assertion = factory.getAssert(cast(e));

        for(AssertCommand<A,T> command : commands) {
            command.setAssert(assertion);
            command.doCheck();
        }
    }

    private T cast(Throwable e) {
        return classT.cast(e);
    }
}
