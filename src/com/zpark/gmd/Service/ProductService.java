package com.zpark.gmd.service;

import com.zpark.gmd.beans.Product;

import java.util.List;

/**
 * 产品服务接口
 */
public interface ProductService {
    
    /**
     * 查询所有产品
     * @return 产品列表
     */
    List<Product> findAll() throws Exception;
    
    /**
     * 根据ID查询产品
     * @param id 产品ID
     * @return 产品信息
     */
    Product findById(int id) throws Exception;
    
    /**
     * 添加产品
     * @param product 产品信息
     * @return 是否成功
     */
    boolean add(Product product) throws Exception;
    
    /**
     * 更新产品信息
     * @param product 产品信息
     * @return 是否成功
     */
    boolean update(Product product) throws Exception;
    
    /**
     * 删除产品
     * @param id 产品ID
     * @return 是否成功
     */
    boolean delete(int id) throws Exception;
    
    /**
     * 分页查询产品
     * @param page 页码
     * @param size 每页大小
     * @return 产品列表
     */
    List<Product> findByPage(int page, int size) throws Exception;
    
    /**
     * 统计产品总数
     * @return 产品总数
     */
    int count() throws Exception;
    
    /**
     * 根据分类查询产品
     * @param category 分类
     * @return 产品列表
     */
    List<Product> findByCategory(String category) throws Exception;
    
    /**
     * 根据品牌查询产品
     * @param brand 品牌
     * @return 产品列表
     */
    List<Product> findByBrand(String brand) throws Exception;
    
    /**
     * 根据状态查询产品
     * @param status 状态
     * @return 产品列表
     */
    List<Product> findByStatus(int status) throws Exception;
    
    /**
     * 根据条件查询产品
     * @param name 产品名称（模糊查询）
     * @param category 分类
     * @param brand 品牌
     * @param status 状态
     * @return 产品列表
     */
    List<Product> findByCondition(String name, String category, String brand, Integer status) throws Exception;
    
    /**
     * 批量删除产品
     * @param ids 产品ID数组
     * @return 是否成功
     */
    boolean batchDelete(int[] ids) throws Exception;
    
    /**
     * 更新库存
     * @param id 产品ID
     * @param quantity 库存数量
     * @return 是否成功
     */
    boolean updateStock(int id, int quantity) throws Exception;
}
