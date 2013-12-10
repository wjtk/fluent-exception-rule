package pl.wkr.fluentrule.util;

import java.util.HashMap;
import java.util.Map;

public class TypeDefaults {

    public static final TypeDefaults instance = new TypeDefaults();

    private final Map<Class<?>, Object> MAP = new HashMap<Class<?>, Object>();

    public <T> T getDefaultValue(Class<T> type) {
        return getDefaultInner(type);
    }

    @SuppressWarnings("unchecked")
    private <T> T getDefaultInner(Class<T> type) {
        return (T) MAP.get(type);
    }

    private TypeDefaults() {
        MAP.put(boolean.class, false);
        MAP.put(char.class, '\0');
        MAP.put(byte.class, (byte) 0);
        MAP.put(short.class, (short) 0);
        MAP.put(int.class, 0);
        MAP.put(long.class, 0L);
        MAP.put(float.class, 0f);
        MAP.put(double.class, 0d);
    }
}
