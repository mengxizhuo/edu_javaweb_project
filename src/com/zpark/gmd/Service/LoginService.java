package com.zpark.gmd.service;

import com.zpark.gmd.beans.Employee;

/**
 * 登录服务接口
 */
public interface LoginService {

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 员工信息，登录失败返回null
     */
    Employee login(String username, String password) throws Exception;

    /**
     * 修改密码
     * @param employeeId 员工ID
     * @param newPassword 新密码
     * @return 是否成功
     */
    boolean changePassword(int employeeId, String newPassword) throws Exception;

    /**
     * 验证密码
     * @param employeeId 员工ID
     * @param password 密码
     * @return 是否正确
     */
    boolean validatePassword(int employeeId, String password) throws Exception;
}
