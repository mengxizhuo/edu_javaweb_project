package com.zpark.gmd.service.impl;

import com.zpark.gmd.beans.Employee;
import com.zpark.gmd.dao.EmployeeDao;
import com.zpark.gmd.dao.impl.EmployeeDaoImpl;
import com.zpark.gmd.service.EmployeeService;
import com.zpark.gmd.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 员工服务实现类
 */
public class EmployeeServiceImpl implements EmployeeService {
    
    private EmployeeDao employeeDao = new EmployeeDaoImpl();
    
    @Override
    public List<Employee> findAll() throws Exception {
        return employeeDao.findAll();
    }
    
    @Override
    public Employee findById(int id) throws Exception {
        return employeeDao.findById(id);
    }
    
    @Override
    public boolean add(Employee employee) throws Exception {
        // 参数验证
        if (employee == null) {
            throw new IllegalArgumentException("员工信息不能为空");
        }
        
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("员工姓名不能为空");
        }
        
        if (employee.getUsername() == null || employee.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("员工用户名不能为空");
        }
        
        if (employee.getPassword() == null || employee.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("员工密码不能为空");
        }
        
        // 检查用户名是否已存在
        Employee existingEmployee = employeeDao.findByUsername(employee.getUsername());
        if (existingEmployee != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        
        // 开启事务
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            
            boolean result = employeeDao.add(employee);
            
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
    public boolean update(Employee employee) throws Exception {
        // 参数验证
        if (employee == null) {
            throw new IllegalArgumentException("员工信息不能为空");
        }
        
        if (employee.getId() <= 0) {
            throw new IllegalArgumentException("员工ID不正确");
        }
        
        // 检查员工是否存在
        Employee existingEmployee = employeeDao.findById(employee.getId());
        if (existingEmployee == null) {
            throw new IllegalArgumentException("员工不存在");
        }
        
        // 如果修改了用户名，检查新用户名是否已被其他员工使用
        if (employee.getUsername() != null && 
            !employee.getUsername().equals(existingEmployee.getUsername())) {
            Employee userWithSameUsername = employeeDao.findByUsername(employee.getUsername());
            if (userWithSameUsername != null && userWithSameUsername.getId() != employee.getId()) {
                throw new IllegalArgumentException("用户名已被其他员工使用");
            }
        }
        
        // 开启事务
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            
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
    public boolean delete(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("员工ID不正确");
        }
        
        // 检查员工是否存在
        Employee employee = employeeDao.findById(id);
        if (employee == null) {
            throw new IllegalArgumentException("员工不存在");
        }
        
        // 开启事务
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            
            boolean result = employeeDao.delete(id);
            
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
    public Employee findByUsername(String username) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        return employeeDao.findByUsername(username);
    }
    
    @Override
    public List<Employee> findByPage(int page, int size) throws Exception {
        if (page <= 0) {
            throw new IllegalArgumentException("页码必须大于0");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("每页大小必须大于0");
        }
        
        int offset = (page - 1) * size;
        return employeeDao.findByPage(offset, size);
    }
    
    @Override
    public int count() throws Exception {
        return employeeDao.count();
    }
    
    @Override
    public List<Employee> findByDeptId(int deptId) throws Exception {
        if (deptId <= 0) {
            throw new IllegalArgumentException("部门ID不正确");
        }
        return employeeDao.findByDeptId(deptId);
    }
    
    @Override
    public List<Employee> findByCondition(String name, String gender, Integer deptId) throws Exception {
        return employeeDao.findByCondition(name, gender, deptId);
    }
}
