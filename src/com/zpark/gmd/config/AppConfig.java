package com.zpark.gmd.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 应用配置管理类
 */
public class AppConfig {
    
    private static final String CONFIG_FILE = "app.properties";
    private static Properties properties = new Properties();
    
    static {
        loadConfig();
    }
    
    /**
     * 加载配置文件
     */
    private static void loadConfig() {
        try (InputStream is = AppConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (is != null) {
                properties.load(is);
            } else {
                System.err.println("配置文件 " + CONFIG_FILE + " 未找到，使用默认配置");
                loadDefaultConfig();
            }
        } catch (IOException e) {
            System.err.println("加载配置文件失败: " + e.getMessage());
            loadDefaultConfig();
        }
    }
    
    /**
     * 加载默认配置
     */
    private static void loadDefaultConfig() {
        properties.setProperty("app.name", "员工管理系统");
        properties.setProperty("app.version", "1.0.0");
        properties.setProperty("app.debug", "false");
        properties.setProperty("pagination.pageSize", "10");
        properties.setProperty("upload.maxSize", "10485760"); // 10MB
        properties.setProperty("session.timeout", "1800"); // 30分钟
    }
    
    /**
     * 获取字符串配置
     */
    public static String getString(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * 获取字符串配置，带默认值
     */
    public static String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * 获取整数配置
     */
    public static int getInt(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("配置项 " + key + " 不存在");
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("配置项 " + key + " 不是有效的整数: " + value);
        }
    }
    
    /**
     * 获取整数配置，带默认值
     */
    public static int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    /**
     * 获取布尔配置
     */
    public static boolean getBoolean(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("配置项 " + key + " 不存在");
        }
        return Boolean.parseBoolean(value);
    }
    
    /**
     * 获取布尔配置，带默认值
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }
    
    /**
     * 获取长整数配置
     */
    public static long getLong(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("配置项 " + key + " 不存在");
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("配置项 " + key + " 不是有效的长整数: " + value);
        }
    }
    
    /**
     * 获取长整数配置，带默认值
     */
    public static long getLong(String key, long defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    /**
     * 检查配置项是否存在
     */
    public static boolean hasProperty(String key) {
        return properties.containsKey(key);
    }
    
    /**
     * 获取所有配置
     */
    public static Properties getAllProperties() {
        return new Properties(properties);
    }
    
    /**
     * 重新加载配置
     */
    public static void reload() {
        properties.clear();
        loadConfig();
    }
}
