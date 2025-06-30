package com.zpark.gmd.service.impl;

import com.zpark.gmd.beans.Customer;
import com.zpark.gmd.dao.CustomerDao;
import com.zpark.gmd.dao.impl.CustomerDaoImpl;
import com.zpark.gmd.service.CustomerService;
import com.zpark.gmd.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 客户服务实现类
 */
public class CustomerServiceImpl implements CustomerService {
    
    private CustomerDao customerDao = new CustomerDaoImpl();
    
    // 邮箱格式验证正则表达式
    private static final String EMAIL_PATTERN = 
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    
    @Override
    public List<Customer> findAll() throws Exception {
        return customerDao.findAll();
    }
    
    @Override
    public Customer findById(int id) throws Exception {
        return customerDao.findById(id);
    }
    
    @Override
    public Customer findByEmail(String email) throws Exception {
        return customerDao.findByEmail(email);
    }
    
    @Override
    public boolean add(Customer customer) throws Exception {
        // 参数验证
        if (customer == null) {
            throw new IllegalArgumentException("客户信息不能为空");
        }
        
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("客户姓名不能为空");
        }
        
        // 邮箱格式验证
        if (customer.getEmail() != null && !customer.getEmail().trim().isEmpty()) {
            if (!isValidEmail(customer.getEmail())) {
                throw new IllegalArgumentException("邮箱格式不正确");
            }
            
            // 检查邮箱是否已存在
            Customer existingCustomer = customerDao.findByEmail(customer.getEmail());
            if (existingCustomer != null) {
                throw new IllegalArgumentException("邮箱已存在");
            }
        }
        
        // 设置默认值
        if (customer.getCustomerType() == 0) {
            customer.setCustomerType(1); // 默认个人客户
        }
        if (customer.getStatus() == 0) {
            customer.setStatus(1); // 默认活跃状态
        }
        
        // 开启事务
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            
            boolean result = customerDao.add(customer);
            
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
    public boolean update(Customer customer) throws Exception {
        // 参数验证
        if (customer == null) {
            throw new IllegalArgumentException("客户信息不能为空");
        }
        
        if (customer.getId() <= 0) {
            throw new IllegalArgumentException("客户ID不正确");
        }
        
        // 检查客户是否存在
        Customer existingCustomer = customerDao.findById(customer.getId());
        if (existingCustomer == null) {
            throw new IllegalArgumentException("客户不存在");
        }
        
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("客户姓名不能为空");
        }
        
        // 邮箱格式验证
        if (customer.getEmail() != null && !customer.getEmail().trim().isEmpty()) {
            if (!isValidEmail(customer.getEmail())) {
                throw new IllegalArgumentException("邮箱格式不正确");
            }
            
            // 如果修改了邮箱，检查新邮箱是否已被其他客户使用
            if (!customer.getEmail().equals(existingCustomer.getEmail())) {
                boolean emailExists = customerDao.existsEmail(customer.getEmail(), customer.getId());
                if (emailExists) {
                    throw new IllegalArgumentException("邮箱已被其他客户使用");
                }
            }
        }
        
        // 开启事务
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            
            boolean result = customerDao.update(customer);
            
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
            throw new IllegalArgumentException("客户ID不正确");
        }
        
        // 检查客户是否存在
        Customer customer = customerDao.findById(id);
        if (customer == null) {
            throw new IllegalArgumentException("客户不存在");
        }
        
        // 开启事务
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            
            boolean result = customerDao.delete(id);
            
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
    public List<Customer> findByPage(int page, int size) throws Exception {
        if (page <= 0) {
            throw new IllegalArgumentException("页码必须大于0");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("每页大小必须大于0");
        }
        
        int offset = (page - 1) * size;
        return customerDao.findByPage(offset, size);
    }
    
    @Override
    public int count() throws Exception {
        return customerDao.count();
    }
    
    @Override
    public List<Customer> findByCustomerType(int customerType) throws Exception {
        return customerDao.findByCustomerType(customerType);
    }
    
    @Override
    public List<Customer> findByStatus(int status) throws Exception {
        return customerDao.findByStatus(status);
    }
    
    @Override
    public List<Customer> findByCondition(String name, String email, String phone, 
                                         Integer customerType, Integer status) throws Exception {
        return customerDao.findByCondition(name, email, phone, customerType, status);
    }
    
    @Override
    public boolean batchDelete(int[] ids) throws Exception {
        if (ids == null || ids.length == 0) {
            throw new IllegalArgumentException("删除的客户ID不能为空");
        }
        
        // 开启事务
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            
            boolean result = customerDao.batchDelete(ids);
            
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
    
    /**
     * 验证邮箱格式
     */
    private boolean isValidEmail(String email) {
        return pattern.matcher(email).matches();
    }
}
