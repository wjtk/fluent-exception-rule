package pl.wkr.fluentrule.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeDefaults_Test {

    TypeDefaults td = TypeDefaults.instance;

    @Test
    public void should_return_type_defaults() {
        assertThat(td.getDefaultValue(Object.class)).isEqualTo(null);

        assertThat(td.getDefaultValue(int.class)).isEqualTo(0);
        assertThat(td.getDefaultValue(long.class)).isEqualTo(0L);
        assertThat(td.getDefaultValue(short.class)).isEqualTo((short) 0);
        assertThat(td.getDefaultValue(byte.class)).isEqualTo((byte) 0);
        assertThat(td.getDefaultValue(float.class)).isEqualTo(0f);
        assertThat(td.getDefaultValue(double.class)).isEqualTo(0d);

        assertThat(td.getDefaultValue(char.class)).isEqualTo('\0');
        assertThat(td.getDefaultValue(boolean.class)).isEqualTo(false);


    }
}
