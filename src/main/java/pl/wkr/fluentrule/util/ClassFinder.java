package pl.wkr.fluentrule.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassFinder {

    private int typeArgIndex;

    public ClassFinder(int typeArgIndex) {
        this.typeArgIndex = typeArgIndex;
    }

    public <T> Class<T> findConcreteClass(Class<?> forClass) {
        try {
            Object prev = forClass;
            Type genericSuperclass;
            do {
                genericSuperclass = ((Class<?>) prev).getGenericSuperclass();
                prev = genericSuperclass;
            } while(!(genericSuperclass instanceof ParameterizedType));

            Type typeArg = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[typeArgIndex];
            return castToClass(typeArg);
        }
        catch(Exception e) {
            throw new RuntimeException("Cannot find concrete class. Class 'forClass' cannot be generic!", e);
        }
    }


    @SuppressWarnings("unchecked")
    private static <T> Class<T> castToClass(Type type) {
        return (Class<T>) type;
    }
}
