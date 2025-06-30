package com.zpark.gmd.dao.impl;

import com.zpark.gmd.beans.Employee;
import com.zpark.gmd.dao.LoginDao;
import com.zpark.gmd.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDaoImpl implements LoginDao {

    @Override
    public Employee login(String username, String password) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM employee WHERE username = ? AND password = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);

            rs = pst.executeQuery();

            if (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setUsername(rs.getString("username"));
                employee.setPassword(rs.getString("password"));
                employee.setGender(rs.getString("gender"));
                employee.setSalary(rs.getObject("salary", Integer.class));
                employee.setJoinDate(rs.getDate("join_date"));
                employee.setDeptId(rs.getObject("dept_id", Integer.class));
                return employee;
            }

        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }

        return null;
    }
}
