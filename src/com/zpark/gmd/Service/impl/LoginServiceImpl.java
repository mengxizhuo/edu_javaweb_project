package com.zpark.gmd.service.impl;

import com.zpark.gmd.service.LoginService;
import com.zpark.gmd.beans.Employee;
import com.zpark.gmd.dao.LoginDao;
import com.zpark.gmd.dao.impl.LoginDaoImpl;
import com.zpark.gmd.dao.EmployeeDao;
import com.zpark.gmd.dao.impl.EmployeeDaoImpl;
import com.zpark.gmd.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 登录服务实现类
 */
public class LoginServiceImpl implements LoginService {

    private LoginDao loginDao = new LoginDaoImpl();
    private EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public Employee login(String username, String password) throws Exception {
        // 参数验证
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }

        // 调用DAO层进行登录验证
        return loginDao.login(username.trim(), password);
    }

    @Override
    public boolean changePassword(int employeeId, String newPassword) throws Exception {
        // 参数验证
        if (employeeId <= 0) {
            throw new IllegalArgumentException("员工ID不正确");
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("新密码不能为空");
        }

        if (newPassword.length() < 6) {
            throw new IllegalArgumentException("密码长度不能少于6位");
        }

        // 检查员工是否存在
        Employee employee = employeeDao.findById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("员工不存在");
        }

        // 开启事务
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);

            // 更新密码
            employee.setPassword(newPassword);
            boolean result = employeeDao.update(employee);

            if (result) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean validatePassword(int employeeId, String password) throws Exception {
        // 参数验证
        if (employeeId <= 0) {
            throw new IllegalArgumentException("员工ID不正确");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }

        // 获取员工信息
        Employee employee = employeeDao.findById(employeeId);
        if (employee == null) {
            return false;
        }

        // 验证密码
        return password.equals(employee.getPassword());
    }
}
