package com.zpark.gmd.dao.impl;

import com.zpark.gmd.beans.Employee;
import com.zpark.gmd.dao.EmployeeDao;
import com.zpark.gmd.utils.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 员工数据访问实现类
 */
public class EmployeeDaoImpl implements EmployeeDao {
    
    @Override
    public List<Employee> findAll() throws Exception {
        List<Employee> employees = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM employee ORDER BY id";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Employee employee = buildEmployee(rs);
                employees.add(employee);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return employees;
    }
    
    @Override
    public Employee findById(int id) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM employee WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                return buildEmployee(rs);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return null;
    }
    
    @Override
    public Employee findByUsername(String username) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM employee WHERE username = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                return buildEmployee(rs);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return null;
    }
    
    @Override
    public boolean add(Employee employee) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "INSERT INTO employee(name, username, password, gender, salary, join_date, dept_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, employee.getName());
            pst.setString(2, employee.getUsername());
            pst.setString(3, employee.getPassword());
            pst.setString(4, employee.getGender());
            pst.setObject(5, employee.getSalary());
            
            if (employee.getJoinDate() != null) {
                pst.setDate(6, new java.sql.Date(employee.getJoinDate().getTime()));
            } else {
                pst.setNull(6, Types.DATE);
            }
            
            pst.setObject(7, employee.getDeptId());
            
            int result = pst.executeUpdate();
            return result > 0;
            
        } finally {
            JDBCUtil.closeAll(conn, pst, null);
        }
    }
    
    @Override
    public boolean update(Employee employee) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "UPDATE employee SET name = ?, username = ?, password = ?, " +
                        "gender = ?, salary = ?, join_date = ?, dept_id = ? WHERE id = ?";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, employee.getName());
            pst.setString(2, employee.getUsername());
            pst.setString(3, employee.getPassword());
            pst.setString(4, employee.getGender());
            pst.setObject(5, employee.getSalary());
            
            if (employee.getJoinDate() != null) {
                pst.setDate(6, new java.sql.Date(employee.getJoinDate().getTime()));
            } else {
                pst.setNull(6, Types.DATE);
            }
            
            pst.setObject(7, employee.getDeptId());
            pst.setInt(8, employee.getId());
            
            int result = pst.executeUpdate();
            return result > 0;
            
        } finally {
            JDBCUtil.closeAll(conn, pst, null);
        }
    }
    
    @Override
    public boolean delete(int id) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "DELETE FROM employee WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            
            int result = pst.executeUpdate();
            return result > 0;
            
        } finally {
            JDBCUtil.closeAll(conn, pst, null);
        }
    }
    
    @Override
    public List<Employee> findByPage(int offset, int limit) throws Exception {
        List<Employee> employees = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM employee ORDER BY id LIMIT ?, ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, offset);
            pst.setInt(2, limit);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Employee employee = buildEmployee(rs);
                employees.add(employee);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return employees;
    }
    
    @Override
    public int count() throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM employee";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return 0;
    }
    
    @Override
    public List<Employee> findByDeptId(int deptId) throws Exception {
        List<Employee> employees = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM employee WHERE dept_id = ? ORDER BY id";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, deptId);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Employee employee = buildEmployee(rs);
                employees.add(employee);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return employees;
    }
    
    @Override
    public List<Employee> findByCondition(String name, String gender, Integer deptId) throws Exception {
        List<Employee> employees = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            
            StringBuilder sql = new StringBuilder("SELECT * FROM employee WHERE 1=1");
            List<Object> params = new ArrayList<>();
            
            if (name != null && !name.trim().isEmpty()) {
                sql.append(" AND name LIKE ?");
                params.add("%" + name.trim() + "%");
            }
            
            if (gender != null && !gender.trim().isEmpty()) {
                sql.append(" AND gender = ?");
                params.add(gender.trim());
            }
            
            if (deptId != null) {
                sql.append(" AND dept_id = ?");
                params.add(deptId);
            }
            
            sql.append(" ORDER BY id");
            
            pst = conn.prepareStatement(sql.toString());
            
            for (int i = 0; i < params.size(); i++) {
                pst.setObject(i + 1, params.get(i));
            }
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Employee employee = buildEmployee(rs);
                employees.add(employee);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return employees;
    }
    
    @Override
    public boolean batchDelete(int[] ids) throws Exception {
        if (ids == null || ids.length == 0) {
            return false;
        }
        
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = JDBCUtil.getConnection();
            
            StringBuilder sql = new StringBuilder("DELETE FROM employee WHERE id IN (");
            for (int i = 0; i < ids.length; i++) {
                if (i > 0) sql.append(",");
                sql.append("?");
            }
            sql.append(")");
            
            pst = conn.prepareStatement(sql.toString());
            
            for (int i = 0; i < ids.length; i++) {
                pst.setInt(i + 1, ids[i]);
            }
            
            int result = pst.executeUpdate();
            return result > 0;
            
        } finally {
            JDBCUtil.closeAll(conn, pst, null);
        }
    }
    
    @Override
    public boolean existsUsername(String username, Integer excludeId) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            
            String sql;
            if (excludeId != null) {
                sql = "SELECT COUNT(*) FROM employee WHERE username = ? AND id != ?";
            } else {
                sql = "SELECT COUNT(*) FROM employee WHERE username = ?";
            }
            
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            
            if (excludeId != null) {
                pst.setInt(2, excludeId);
            }
            
            rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return false;
    }
    
    /**
     * 从ResultSet构建Employee对象
     */
    private Employee buildEmployee(ResultSet rs) throws SQLException {
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
}
