package com.zpark.gmd.filter;

import com.zpark.gmd.common.Result;
import com.zpark.gmd.exception.BusinessException;
import com.zpark.gmd.exception.DataAccessException;
import com.zpark.gmd.exception.ValidationException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 全局异常处理过滤器
 */
@WebFilter("/*")
public class GlobalExceptionFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化操作
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            handleException(req, resp, e);
        }
    }
    
    /**
     * 处理异常
     */
    private void handleException(HttpServletRequest req, HttpServletResponse resp, Exception e) 
            throws IOException {
        
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        
        Result<?> result;
        
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            result = Result.error(be.getCode(), be.getMessage());
        } else if (e instanceof ValidationException) {
            result = Result.paramError(e.getMessage());
        } else if (e instanceof DataAccessException) {
            result = Result.error("数据访问错误: " + e.getMessage());
        } else if (e instanceof IllegalArgumentException) {
            result = Result.paramError(e.getMessage());
        } else {
            // 记录未知异常
            e.printStackTrace();
            result = Result.error("系统内部错误，请稍后重试");
        }
        
        // 检查是否是页面请求
        String accept = req.getHeader("Accept");
        if (accept != null && accept.contains("text/html")) {
            // 页面请求，转发到错误页面
            req.setAttribute("errorMsg", result.getMessage());
            try {
                req.getRequestDispatcher("/error.jsp").forward(req, resp);
            } catch (ServletException ex) {
                // 如果转发失败，直接输出错误信息
                writeJsonResponse(resp, result);
            }
        } else {
            // AJAX请求，返回JSON
            writeJsonResponse(resp, result);
        }
    }
    
    /**
     * 写入JSON响应
     */
    private void writeJsonResponse(HttpServletResponse resp, Result<?> result) throws IOException {
        PrintWriter out = resp.getWriter();
        String json = buildJsonString(result);
        out.write(json);
        out.flush();
    }
    
    /**
     * 构建JSON字符串
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
    
    @Override
    public void destroy() {
        // 清理操作
    }
}
