package pl.wkr.fluentrule.util;

import java.util.HashMap;
import java.util.Map;

public class TypeDefaults {

    private static final Map<Class<?>, Object> MAP = new HashMap<Class<?>, Object>();

    static {
        MAP.put(boolean.class, false);
        MAP.put(char.class, '\0');
        MAP.put(byte.class, (byte) 0);
        MAP.put(short.class, (short) 0);
        MAP.put(int.class, 0);
        MAP.put(long.class, 0L);
        MAP.put(float.class, 0f);
        MAP.put(double.class, 0d);
    }

    public static <T> T getDefaultValue(Class<T> type) {
        return getDefaultInner(type);
    }

    @SuppressWarnings("unchecked")
    private static <T> T getDefaultInner(Class<T> type) {
        return (T) MAP.get(type);
    }

    private TypeDefaults() {}
}
