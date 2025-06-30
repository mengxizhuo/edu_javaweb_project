package com.zpark.gmd.controller;

import com.zpark.gmd.common.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * 基础Controller类，提供统一的请求处理和响应格式
 */
public abstract class BaseController extends HttpServlet {
    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 设置编码
        req.setCharacterEncoding("UTF-8");

        try {
            // 获取请求路径
            String requestURI = req.getRequestURI();
            String contextPath = req.getContextPath();
            String path = requestURI.substring(contextPath.length());

            // 检查是否是静态资源请求
            if (isStaticResource(path)) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // 解析方法名
            String methodName = parseMethodName(path);

            if (methodName == null) {
                resp.setContentType("application/json;charset=UTF-8");
                writeErrorResponse(resp, Result.error("请求路径不正确"));
                return;
            }

            // 通过反射调用对应的方法
            Method method = this.getClass().getMethod(methodName,
                    HttpServletRequest.class, HttpServletResponse.class);

            Object result = method.invoke(this, req, resp);

            // 如果方法返回Result对象，则写入响应
            if (result instanceof Result) {
                resp.setContentType("application/json;charset=UTF-8");
                writeJsonResponse(resp, (Result<?>) result);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setContentType("application/json;charset=UTF-8");
            writeErrorResponse(resp, Result.error("服务器内部错误: " + e.getMessage()));
        }
    }
    
    /**
     * 解析方法名
     * 例如：/emps/findAll -> findAll, /emps/addEmp -> add, /login -> login
     */
    protected String parseMethodName(String path) {
        // 处理直接路径映射（如 /login, /logout）
        if (path.equals("/login")) {
            return "login";
        }
        if (path.equals("/logout")) {
            return "logout";
        }

        // 处理带前缀的路径（如 /emps/findAll, /auth/login）
        String[] parts = path.split("/");
        if (parts.length >= 3) {
            String methodPath = parts[2];

            // 处理特殊的方法名映射
            switch (methodPath) {
                case "findAll":
                case "findAllEmp":
                    return "findAll";
                case "addEmp":
                    return "add";
                case "findById":
                case "findEmpById":
                    return "findById";
                case "delete":
                case "delEmpById":
                    return "delete";
                case "update":
                case "updateEmp":
                    return "update";
                case "login":
                    return "login";
                case "logout":
                    return "logout";
                case "changePassword":
                    return "changePassword";
                default:
                    return methodPath;
            }
        }
        return null;
    }
    
    /**
     * 写入JSON响应
     */
    protected void writeJsonResponse(HttpServletResponse resp, Result<?> result)
            throws IOException {
        PrintWriter out = resp.getWriter();
        String json = buildJsonString(result);
        out.write(json);
        out.flush();
    }

    /**
     * 手动构建JSON字符串
     */
    private String buildJsonString(Result<?> result) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"code\":").append(result.getCode()).append(",");
        json.append("\"message\":\"").append(escapeJson(result.getMessage())).append("\"");

        if (result.getData() != null) {
            json.append(",\"data\":");
            if (result.getData() instanceof String) {
                json.append("\"").append(escapeJson(result.getData().toString())).append("\"");
            } else {
                json.append("\"").append(result.getData().toString()).append("\"");
            }
        }

        json.append("}");
        return json.toString();
    }

    /**
     * 转义JSON字符串
     */
    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
    
    /**
     * 写入错误响应
     */
    protected void writeErrorResponse(HttpServletResponse resp, Result<?> result) 
            throws IOException {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        writeJsonResponse(resp, result);
    }
    
    /**
     * 页面跳转
     */
    protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) 
            throws ServletException, IOException {
        req.getRequestDispatcher(path).forward(req, resp);
    }
    
    /**
     * 重定向
     */
    protected void redirect(HttpServletResponse resp, String url) throws IOException {
        resp.sendRedirect(url);
    }
    
    /**
     * 获取请求参数并转换为指定类型
     */
    protected String getParameter(HttpServletRequest req, String name) {
        return req.getParameter(name);
    }
    
    protected int getIntParameter(HttpServletRequest req, String name, int defaultValue) {
        String value = req.getParameter(name);
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    /**
     * 验证参数是否为空
     */
    protected boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 检查是否是静态资源请求
     */
    protected boolean isStaticResource(String path) {
        if (path == null) return false;

        // 检查是否包含静态资源路径
        return path.contains("/assets/") ||
               path.contains("/css/") ||
               path.contains("/js/") ||
               path.contains("/images/") ||
               path.contains("/img/") ||
               path.endsWith(".css") ||
               path.endsWith(".js") ||
               path.endsWith(".png") ||
               path.endsWith(".jpg") ||
               path.endsWith(".jpeg") ||
               path.endsWith(".gif") ||
               path.endsWith(".ico") ||
               path.endsWith(".svg");
    }
}
