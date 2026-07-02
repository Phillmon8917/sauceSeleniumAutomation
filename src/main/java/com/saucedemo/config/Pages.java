package com.saucedemo.config;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;


public final class Pages {
    private static final ThreadLocal<Map<Class<?>, Object>> CACHE = ThreadLocal.withInitial(HashMap::new);

    private Pages() {
    }

    public static <T> T get(Class<T> type) {
        return type.cast(CACHE.get().computeIfAbsent(type, Pages::instantiate));
    }

    public static void clear() {
        CACHE.get().clear();
    }

    private static Object instantiate(Class<?> type) {
        try {
            Constructor<?> constructor = type.getDeclaredConstructor(org.openqa.selenium.WebDriver.class);
            constructor.setAccessible(true);
            return constructor.newInstance(DriverFactory.getDriver());
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to create page object: " + type.getName(), e);
        }
    }
}
