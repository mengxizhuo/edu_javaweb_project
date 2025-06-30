package com.zpark.gmd.dao;

import com.zpark.gmd.beans.Customer;

import java.util.List;

/**
 * 客户数据访问接口
 */
public interface CustomerDao {
    
    /**
     * 查询所有客户
     * @return 客户列表
     */
    List<Customer> findAll() throws Exception;
    
    /**
     * 根据ID查询客户
     * @param id 客户ID
     * @return 客户信息
     */
    Customer findById(int id) throws Exception;
    
    /**
     * 根据邮箱查询客户
     * @param email 邮箱
     * @return 客户信息
     */
    Customer findByEmail(String email) throws Exception;
    
    /**
     * 添加客户
     * @param customer 客户信息
     * @return 是否成功
     */
    boolean add(Customer customer) throws Exception;
    
    /**
     * 更新客户信息
     * @param customer 客户信息
     * @return 是否成功
     */
    boolean update(Customer customer) throws Exception;
    
    /**
     * 删除客户
     * @param id 客户ID
     * @return 是否成功
     */
    boolean delete(int id) throws Exception;
    
    /**
     * 分页查询客户
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 客户列表
     */
    List<Customer> findByPage(int offset, int limit) throws Exception;
    
    /**
     * 统计客户总数
     * @return 客户总数
     */
    int count() throws Exception;
    
    /**
     * 根据客户类型查询客户
     * @param customerType 客户类型
     * @return 客户列表
     */
    List<Customer> findByCustomerType(int customerType) throws Exception;
    
    /**
     * 根据状态查询客户
     * @param status 状态
     * @return 客户列表
     */
    List<Customer> findByStatus(int status) throws Exception;
    
    /**
     * 根据条件查询客户
     * @param name 客户名称（模糊查询）
     * @param email 邮箱（模糊查询）
     * @param phone 电话（模糊查询）
     * @param customerType 客户类型
     * @param status 状态
     * @return 客户列表
     */
    List<Customer> findByCondition(String name, String email, String phone, 
                                  Integer customerType, Integer status) throws Exception;
    
    /**
     * 批量删除客户
     * @param ids 客户ID数组
     * @return 是否成功
     */
    boolean batchDelete(int[] ids) throws Exception;
    
    /**
     * 检查邮箱是否存在
     * @param email 邮箱
     * @param excludeId 排除的客户ID（用于更新时检查）
     * @return 是否存在
     */
    boolean existsEmail(String email, Integer excludeId) throws Exception;
}
