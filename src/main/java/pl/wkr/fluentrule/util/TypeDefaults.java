package pl.wkr.fluentrule.util;

import java.util.HashMap;
import java.util.Map;

public class TypeDefaults {

    private final Map<Class<?>, Object> defaults = new HashMap<Class<?>, Object>();

    public <T> T getDefaultValue(Class<T> type) {
        return getDefaultInner(type);
    }

    @SuppressWarnings("unchecked")
    private <T> T getDefaultInner(Class<T> type) {
        return (T) defaults.get(type);
    }

    public TypeDefaults() {
        defaults.put(boolean.class, false);
        defaults.put(char.class, '\0');
        defaults.put(byte.class, (byte) 0);
        defaults.put(short.class, (short) 0);
        defaults.put(int.class, 0);
        defaults.put(long.class, 0L);
        defaults.put(float.class, 0f);
        defaults.put(double.class, 0d);
    }
}
