package com.zpark.gmd.dao;

import com.zpark.gmd.beans.Employee;

import java.util.List;

/**
 * 员工数据访问接口
 */
public interface EmployeeDao {
    
    /**
     * 查询所有员工
     * @return 员工列表
     */
    List<Employee> findAll() throws Exception;
    
    /**
     * 根据ID查询员工
     * @param id 员工ID
     * @return 员工信息
     */
    Employee findById(int id) throws Exception;
    
    /**
     * 根据用户名查询员工
     * @param username 用户名
     * @return 员工信息
     */
    Employee findByUsername(String username) throws Exception;
    
    /**
     * 添加员工
     * @param employee 员工信息
     * @return 是否成功
     */
    boolean add(Employee employee) throws Exception;
    
    /**
     * 更新员工信息
     * @param employee 员工信息
     * @return 是否成功
     */
    boolean update(Employee employee) throws Exception;
    
    /**
     * 删除员工
     * @param id 员工ID
     * @return 是否成功
     */
    boolean delete(int id) throws Exception;
    
    /**
     * 分页查询员工
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 员工列表
     */
    List<Employee> findByPage(int offset, int limit) throws Exception;
    
    /**
     * 统计员工总数
     * @return 员工总数
     */
    int count() throws Exception;
    
    /**
     * 根据部门ID查询员工
     * @param deptId 部门ID
     * @return 员工列表
     */
    List<Employee> findByDeptId(int deptId) throws Exception;
    
    /**
     * 根据条件查询员工
     * @param name 姓名（模糊查询）
     * @param gender 性别
     * @param deptId 部门ID
     * @return 员工列表
     */
    List<Employee> findByCondition(String name, String gender, Integer deptId) throws Exception;
    
    /**
     * 批量删除员工
     * @param ids 员工ID数组
     * @return 是否成功
     */
    boolean batchDelete(int[] ids) throws Exception;
    
    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @param excludeId 排除的员工ID（用于更新时检查）
     * @return 是否存在
     */
    boolean existsUsername(String username, Integer excludeId) throws Exception;
}
