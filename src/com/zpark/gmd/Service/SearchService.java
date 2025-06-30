package com.zpark.gmd.service;

import com.zpark.gmd.beans.SearchResult;
import java.util.List;

/**
 * 搜索服务接口
 */
public interface SearchService {
    
    /**
     * 全局搜索
     * @param keyword 搜索关键词
     * @param limit 限制结果数量
     * @return 搜索结果列表
     * @throws Exception
     */
    List<SearchResult> globalSearch(String keyword, int limit) throws Exception;
    
    /**
     * 搜索员工
     * @param keyword 搜索关键词
     * @return 搜索结果列表
     * @throws Exception
     */
    List<SearchResult> searchEmployees(String keyword) throws Exception;
    
    /**
     * 搜索产品
     * @param keyword 搜索关键词
     * @return 搜索结果列表
     * @throws Exception
     */
    List<SearchResult> searchProducts(String keyword) throws Exception;
    
    /**
     * 搜索客户
     * @param keyword 搜索关键词
     * @return 搜索结果列表
     * @throws Exception
     */
    List<SearchResult> searchCustomers(String keyword) throws Exception;
}
