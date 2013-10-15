package pl.wkr.fluentrule.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ClassUtil {

    public static <T> Class<T> getConcreteClassOfTypeArg(Class<?> cls, int typeArgIndex) {
        Object prev = cls;
        Type genericSuperclass;
        do {
            genericSuperclass = ((Class<?>) prev).getGenericSuperclass();
            prev = genericSuperclass;
        } while(!(genericSuperclass instanceof ParameterizedType));

        Type typeArg = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[typeArgIndex];
        return castToClass(typeArg);
    }


    @SuppressWarnings("unchecked")
    private static <T> Class<T> castToClass(Type type) {
        return (Class<T>) type;
    }
}
