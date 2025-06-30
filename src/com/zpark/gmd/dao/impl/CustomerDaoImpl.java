package com.zpark.gmd.dao.impl;

import com.zpark.gmd.beans.Customer;
import com.zpark.gmd.dao.CustomerDao;
import com.zpark.gmd.utils.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户数据访问实现类
 */
public class CustomerDaoImpl implements CustomerDao {
    
    @Override
    public List<Customer> findAll() throws Exception {
        List<Customer> customers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM customers ORDER BY id DESC";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Customer customer = buildCustomer(rs);
                customers.add(customer);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return customers;
    }
    
    @Override
    public Customer findById(int id) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM customers WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                return buildCustomer(rs);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return null;
    }
    
    @Override
    public Customer findByEmail(String email) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM customers WHERE email = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                return buildCustomer(rs);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return null;
    }
    
    @Override
    public boolean add(Customer customer) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "INSERT INTO customers(name, email, phone, address, company, contact_person, customer_type, status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, customer.getName());
            pst.setString(2, customer.getEmail());
            pst.setString(3, customer.getPhone());
            pst.setString(4, customer.getAddress());
            pst.setString(5, customer.getCompany());
            pst.setString(6, customer.getContactPerson());
            pst.setInt(7, customer.getCustomerType());
            pst.setInt(8, customer.getStatus());
            
            int result = pst.executeUpdate();
            return result > 0;
            
        } finally {
            JDBCUtil.closeAll(conn, pst, null);
        }
    }
    
    @Override
    public boolean update(Customer customer) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "UPDATE customers SET name = ?, email = ?, phone = ?, address = ?, " +
                        "company = ?, contact_person = ?, customer_type = ?, status = ? WHERE id = ?";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, customer.getName());
            pst.setString(2, customer.getEmail());
            pst.setString(3, customer.getPhone());
            pst.setString(4, customer.getAddress());
            pst.setString(5, customer.getCompany());
            pst.setString(6, customer.getContactPerson());
            pst.setInt(7, customer.getCustomerType());
            pst.setInt(8, customer.getStatus());
            pst.setInt(9, customer.getId());
            
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
            String sql = "DELETE FROM customers WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            
            int result = pst.executeUpdate();
            return result > 0;
            
        } finally {
            JDBCUtil.closeAll(conn, pst, null);
        }
    }
    
    @Override
    public List<Customer> findByPage(int offset, int limit) throws Exception {
        List<Customer> customers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM customers ORDER BY id DESC LIMIT ?, ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, offset);
            pst.setInt(2, limit);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Customer customer = buildCustomer(rs);
                customers.add(customer);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return customers;
    }
    
    @Override
    public int count() throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM customers";
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
    public List<Customer> findByCustomerType(int customerType) throws Exception {
        List<Customer> customers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM customers WHERE customer_type = ? ORDER BY id DESC";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, customerType);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Customer customer = buildCustomer(rs);
                customers.add(customer);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return customers;
    }
    
    @Override
    public List<Customer> findByStatus(int status) throws Exception {
        List<Customer> customers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM customers WHERE status = ? ORDER BY id DESC";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, status);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Customer customer = buildCustomer(rs);
                customers.add(customer);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return customers;
    }
    
    @Override
    public List<Customer> findByCondition(String name, String email, String phone, 
                                         Integer customerType, Integer status) throws Exception {
        List<Customer> customers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            
            StringBuilder sql = new StringBuilder("SELECT * FROM customers WHERE 1=1");
            List<Object> params = new ArrayList<>();
            
            if (name != null && !name.trim().isEmpty()) {
                sql.append(" AND (name LIKE ? OR company LIKE ?)");
                String namePattern = "%" + name.trim() + "%";
                params.add(namePattern);
                params.add(namePattern);
            }
            
            if (email != null && !email.trim().isEmpty()) {
                sql.append(" AND email LIKE ?");
                params.add("%" + email.trim() + "%");
            }
            
            if (phone != null && !phone.trim().isEmpty()) {
                sql.append(" AND phone LIKE ?");
                params.add("%" + phone.trim() + "%");
            }
            
            if (customerType != null) {
                sql.append(" AND customer_type = ?");
                params.add(customerType);
            }
            
            if (status != null) {
                sql.append(" AND status = ?");
                params.add(status);
            }
            
            sql.append(" ORDER BY id DESC");
            
            pst = conn.prepareStatement(sql.toString());
            
            for (int i = 0; i < params.size(); i++) {
                pst.setObject(i + 1, params.get(i));
            }
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Customer customer = buildCustomer(rs);
                customers.add(customer);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return customers;
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
            
            StringBuilder sql = new StringBuilder("DELETE FROM customers WHERE id IN (");
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
    public boolean existsEmail(String email, Integer excludeId) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            
            String sql;
            if (excludeId != null) {
                sql = "SELECT COUNT(*) FROM customers WHERE email = ? AND id != ?";
            } else {
                sql = "SELECT COUNT(*) FROM customers WHERE email = ?";
            }
            
            pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            
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
     * 从ResultSet构建Customer对象
     */
    private Customer buildCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        customer.setAddress(rs.getString("address"));
        customer.setCompany(rs.getString("company"));
        customer.setContactPerson(rs.getString("contact_person"));
        customer.setCustomerType(rs.getInt("customer_type"));
        customer.setStatus(rs.getInt("status"));
        customer.setCreateTime(rs.getTimestamp("create_time"));
        customer.setUpdateTime(rs.getTimestamp("update_time"));
        return customer;
    }
}
