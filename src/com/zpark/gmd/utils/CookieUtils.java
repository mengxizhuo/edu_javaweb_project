package com.zpark.gmd.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 */
public class CookieUtils {
    
    /**
     * 设置Cookie
     * @param response HttpServletResponse
     * @param name Cookie名称
     * @param value Cookie值
     * @param maxAge 过期时间（秒）
     * @param path 路径
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge, String path) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        if (path != null && !path.isEmpty()) {
            cookie.setPath(path);
        }
        response.addCookie(cookie);
    }
    
    /**
     * 设置Cookie（默认路径为"/"）
     * @param response HttpServletResponse
     * @param name Cookie名称
     * @param value Cookie值
     * @param maxAge 过期时间（秒）
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        setCookie(response, name, value, maxAge, "/");
    }
    
    /**
     * 获取Cookie值
     * @param request HttpServletRequest
     * @param name Cookie名称
     * @return Cookie值，如果不存在返回null
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    /**
     * 删除Cookie
     * @param response HttpServletResponse
     * @param name Cookie名称
     * @param path 路径
     */
    public static void deleteCookie(HttpServletResponse response, String name, String path) {
        setCookie(response, name, "", 0, path);
    }
    
    /**
     * 删除Cookie（默认路径为"/"）
     * @param response HttpServletResponse
     * @param name Cookie名称
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        deleteCookie(response, name, "/");
    }
    
    /**
     * 检查Cookie是否存在
     * @param request HttpServletRequest
     * @param name Cookie名称
     * @return 是否存在
     */
    public static boolean hasCookie(HttpServletRequest request, String name) {
        return getCookieValue(request, name) != null;
    }
}
