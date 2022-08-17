package tech.odes.rush.util.config;

import tech.odes.rush.util.ValidationUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Type-aware extension of {@link java.util.Properties}.
 */
public class TypedProperties extends Properties implements Serializable {

    public TypedProperties() {
        super(null);
    }

    public TypedProperties(Properties defaults) {
        if (Objects.nonNull(defaults)) {
            for (String key : defaults.stringPropertyNames()) {
                put(key, defaults.getProperty(key));
            }
        }
    }

    private void checkKey(String property) {
        if (!keyExists(property)) {
            throw new IllegalArgumentException("Property " + property + " not found");
        }
    }

    private boolean keyExists(String property) {
        Set<String> keys = super.stringPropertyNames();
        return keys.contains(property);
    }

    public String getString(String property) {
        checkKey(property);
        return getProperty(property);
    }

    public String getString(String property, String defaultValue) {
        return keyExists(property) ? getProperty(property) : defaultValue;
    }

    public List<String> getStringList(String property, String delimiter, List<String> defaultVal) {
        if (!keyExists(property)) {
            return defaultVal;
        }
        return Arrays.stream(getProperty(property).split(delimiter)).map(String::trim).collect(Collectors.toList());
    }

    public int getInteger(String property) {
        checkKey(property);
        return Integer.parseInt(getProperty(property));
    }

    public int getInteger(String property, int defaultValue) {
        return keyExists(property) ? Integer.parseInt(getProperty(property)) : defaultValue;
    }

    public long getLong(String property) {
        checkKey(property);
        return Long.parseLong(getProperty(property));
    }

    public long getLong(String property, long defaultValue) {
        return keyExists(property) ? Long.parseLong(getProperty(property)) : defaultValue;
    }

    public boolean getBoolean(String property) {
        checkKey(property);
        return Boolean.parseBoolean(getProperty(property));
    }

    public boolean getBoolean(String property, boolean defaultValue) {
        return keyExists(property) ? Boolean.parseBoolean(getProperty(property)) : defaultValue;
    }

    public double getDouble(String property) {
        checkKey(property);
        return Double.parseDouble(getProperty(property));
    }

    public double getDouble(String property, double defaultValue) {
        return keyExists(property) ? Double.parseDouble(getProperty(property)) : defaultValue;
    }

    public static TypedProperties buildProperties(List<String> props) {
        TypedProperties properties = new TypedProperties();
        props.forEach(x -> {
            String[] kv = x.split("=");
            ValidationUtils.checkArgument(kv.length == 2);
            properties.setProperty(kv[0], kv[1]);
        });
        return properties;
    }
}
