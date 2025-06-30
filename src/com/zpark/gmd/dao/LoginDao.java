package com.zpark.gmd.dao;

import com.zpark.gmd.beans.Employee;

/**
 * 登录数据访问接口
 */
public interface LoginDao {

    /**
     * 用户登录验证
     * @param username 用户名
     * @param password 密码
     * @return 员工信息，登录失败返回null
     */
    Employee login(String username, String password) throws Exception;
}
