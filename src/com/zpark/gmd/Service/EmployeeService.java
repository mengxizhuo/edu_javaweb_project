package com.zpark.gmd.service;

import com.zpark.gmd.beans.Employee;

import java.util.List;

/**
 * 员工服务接口
 */
public interface EmployeeService {
    
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
     * 根据用户名查询员工
     * @param username 用户名
     * @return 员工信息
     */
    Employee findByUsername(String username) throws Exception;
    
    /**
     * 分页查询员工
     * @param page 页码
     * @param size 每页大小
     * @return 员工列表
     */
    List<Employee> findByPage(int page, int size) throws Exception;
    
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
}
