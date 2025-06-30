package com.zpark.gmd.controller;

import com.zpark.gmd.beans.SearchResult;
import com.zpark.gmd.common.Result;
import com.zpark.gmd.service.SearchService;
import com.zpark.gmd.service.impl.SearchServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 搜索控制器
 */
@WebServlet({"/search", "/search/*"})
public class SearchController extends BaseController {

    private SearchService searchService = new SearchServiceImpl();
    
    /**
     * 全局搜索
     */
    public Result<List<SearchResult>> search(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // 获取搜索关键词
            String keyword = getParameter(req, "keyword");
            String limitStr = getParameter(req, "limit");
            
            if (isEmpty(keyword)) {
                // 如果是页面请求，转发到搜索页面
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    forward(req, resp, "/search.jsp");
                    return null;
                }
                return Result.paramError("搜索关键词不能为空");
            }
            
            // 解析限制数量，默认20条
            int limit = 20;
            if (!isEmpty(limitStr)) {
                try {
                    limit = Integer.parseInt(limitStr);
                } catch (NumberFormatException e) {
                    limit = 20;
                }
            }
            
            // 执行搜索
            List<SearchResult> results = searchService.globalSearch(keyword, limit);
            
            // 如果是页面请求，转发到搜索结果页面
            String accept = req.getHeader("Accept");
            if (accept != null && accept.contains("text/html")) {
                req.setAttribute("keyword", keyword);
                req.setAttribute("results", results);
                req.setAttribute("resultCount", results.size());
                forward(req, resp, "/searchResults.jsp");
                return null;
            }
            
            // 如果是AJAX请求，返回JSON数据
            return Result.success("搜索成功", results);
            
        } catch (Exception e) {
            e.printStackTrace();
            
            // 如果是页面请求，转发到错误页面
            String accept = req.getHeader("Accept");
            if (accept != null && accept.contains("text/html")) {
                req.setAttribute("errorMsg", "搜索失败: " + e.getMessage());
                forward(req, resp, "/error.jsp");
                return null;
            }
            
            return Result.error("搜索失败: " + e.getMessage());
        }
    }
    
    /**
     * 搜索建议（自动完成）
     */
    public Result<List<SearchResult>> suggest(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        try {
            String keyword = getParameter(req, "keyword");
            
            if (isEmpty(keyword)) {
                return Result.success("搜索建议", new ArrayList<SearchResult>());
            }
            
            // 限制建议结果为5条
            List<SearchResult> results = searchService.globalSearch(keyword, 5);
            
            return Result.success("搜索建议", results);
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取搜索建议失败: " + e.getMessage());
        }
    }
}
