package pl.wkr.fluentrule.api;

import pl.wkr.fluentrule.util.ClassUtil;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class Check<T extends Throwable> {

    private static final int T_PARAM_INDEX = 0;
    private Class<T> classOfT = null;

    public Check() {
    }

    public Check(Class<T> classOfT) {
        this.classOfT = classOfT;
    }

    final void doCheck(Throwable t) {
        Class<T> classT = getClassOfT();
        assertThat(t).isInstanceOf(classT);
        check(classT.cast(t));
    }

    private Class<T> getClassOfT() {
        if( classOfT != null) return classOfT;
        return classOfT = ClassUtil.getConcreteClassOfTypeArg(getClass(), T_PARAM_INDEX);
    }

    abstract protected void check(T exception);
}
