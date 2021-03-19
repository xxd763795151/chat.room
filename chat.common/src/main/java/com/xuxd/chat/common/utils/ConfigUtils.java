package com.xuxd.chat.common.utils;

import com.xuxd.chat.common.netty.NettyConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author 许晓东
 * @date 2021-03-19 23:33
 * @Description: 配置工具类
 */
public class ConfigUtils {

    public static final String CONFIG_FILE_NAME = "config.properties";

    private ConfigUtils() {
        throw new UnsupportedOperationException();
    }

    public static void load(NettyConfig config) throws Exception {
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
        load(properties, config);
    }

    public static void load(Properties fromProp, NettyConfig toConfig) throws Exception {
        Method[] methods = toConfig.getClass().getMethods();
        for (Method method : methods) {
            String mn = method.getName();
            if (mn.startsWith("set")) {
                String tmp = mn.substring(4);
                String first = mn.substring(3, 4);

                String key = first.toLowerCase() + tmp;
                String property = fromProp.getProperty(key);
                if (property != null) {
                    Class<?>[] pt = method.getParameterTypes();
                    if (pt != null && pt.length > 0) {
                        String cn = pt[0].getSimpleName();
                        Object arg = null;
                        if (cn.equals("int") || cn.equals("Integer")) {
                            arg = Integer.parseInt(property);
                        } else if (cn.equals("long") || cn.equals("Long")) {
                            arg = Long.parseLong(property);
                        } else if (cn.equals("double") || cn.equals("Double")) {
                            arg = Double.parseDouble(property);
                        } else if (cn.equals("boolean") || cn.equals("Boolean")) {
                            arg = Boolean.parseBoolean(property);
                        } else if (cn.equals("float") || cn.equals("Float")) {
                            arg = Float.parseFloat(property);
                        } else if (cn.equals("String")) {
                            arg = property;
                        } else {
                            continue;
                        }
                        method.invoke(toConfig, arg);
                    }
                }
            }
        }
    }

    public static String formatConfig(NettyConfig config) throws IllegalAccessException {
        StringBuilder configStr = new StringBuilder();
        for (Field field : getAllFields(config.getClass(), new ArrayList<Field>())) {
            if ((field.getModifiers() & Modifier.STATIC) != 0) {
                continue;
            }

            boolean isAccessible = field.isAccessible();
            field.setAccessible(true);
            configStr.append(field.getName()).append(": ").append(field.get(config)).append(System.lineSeparator());
            field.setAccessible(isAccessible);
        }

        return configStr.toString();
    }

    private static List<Field> getAllFields(Class<?> clazz, List<Field> fields) {
        if (clazz == Object.class) {
            return fields;
        }
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return getAllFields(clazz.getSuperclass(), fields);
    }
}
