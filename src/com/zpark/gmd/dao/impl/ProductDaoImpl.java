package com.zpark.gmd.dao.impl;

import com.zpark.gmd.beans.Product;
import com.zpark.gmd.dao.ProductDao;
import com.zpark.gmd.utils.JDBCUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品数据访问实现类
 */
public class ProductDaoImpl implements ProductDao {
    
    @Override
    public List<Product> findAll() throws Exception {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM products ORDER BY id DESC";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Product product = buildProduct(rs);
                products.add(product);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return products;
    }
    
    @Override
    public Product findById(int id) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM products WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                return buildProduct(rs);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return null;
    }
    
    @Override
    public boolean add(Product product) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "INSERT INTO products(name, description, price, stock_quantity, category, brand, status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, product.getName());
            pst.setString(2, product.getDescription());
            pst.setBigDecimal(3, product.getPrice());
            pst.setInt(4, product.getStockQuantity());
            pst.setString(5, product.getCategory());
            pst.setString(6, product.getBrand());
            pst.setInt(7, product.getStatus());
            
            int result = pst.executeUpdate();
            return result > 0;
            
        } finally {
            JDBCUtil.closeAll(conn, pst, null);
        }
    }
    
    @Override
    public boolean update(Product product) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "UPDATE products SET name = ?, description = ?, price = ?, " +
                        "stock_quantity = ?, category = ?, brand = ?, status = ? WHERE id = ?";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, product.getName());
            pst.setString(2, product.getDescription());
            pst.setBigDecimal(3, product.getPrice());
            pst.setInt(4, product.getStockQuantity());
            pst.setString(5, product.getCategory());
            pst.setString(6, product.getBrand());
            pst.setInt(7, product.getStatus());
            pst.setInt(8, product.getId());
            
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
            String sql = "DELETE FROM products WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            
            int result = pst.executeUpdate();
            return result > 0;
            
        } finally {
            JDBCUtil.closeAll(conn, pst, null);
        }
    }
    
    @Override
    public List<Product> findByPage(int offset, int limit) throws Exception {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM products ORDER BY id DESC LIMIT ?, ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, offset);
            pst.setInt(2, limit);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Product product = buildProduct(rs);
                products.add(product);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return products;
    }
    
    @Override
    public int count() throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM products";
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
    public List<Product> findByCategory(String category) throws Exception {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM products WHERE category = ? ORDER BY id DESC";
            pst = conn.prepareStatement(sql);
            pst.setString(1, category);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Product product = buildProduct(rs);
                products.add(product);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return products;
    }
    
    @Override
    public List<Product> findByBrand(String brand) throws Exception {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM products WHERE brand = ? ORDER BY id DESC";
            pst = conn.prepareStatement(sql);
            pst.setString(1, brand);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Product product = buildProduct(rs);
                products.add(product);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return products;
    }
    
    @Override
    public List<Product> findByStatus(int status) throws Exception {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM products WHERE status = ? ORDER BY id DESC";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, status);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Product product = buildProduct(rs);
                products.add(product);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return products;
    }
    
    @Override
    public List<Product> findByCondition(String name, String category, String brand, Integer status) throws Exception {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            
            StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1");
            List<Object> params = new ArrayList<>();
            
            if (name != null && !name.trim().isEmpty()) {
                sql.append(" AND name LIKE ?");
                params.add("%" + name.trim() + "%");
            }
            
            if (category != null && !category.trim().isEmpty()) {
                sql.append(" AND category = ?");
                params.add(category.trim());
            }
            
            if (brand != null && !brand.trim().isEmpty()) {
                sql.append(" AND brand = ?");
                params.add(brand.trim());
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
                Product product = buildProduct(rs);
                products.add(product);
            }
            
        } finally {
            JDBCUtil.closeAll(conn, pst, rs);
        }
        
        return products;
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
            
            StringBuilder sql = new StringBuilder("DELETE FROM products WHERE id IN (");
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
    public boolean updateStock(int id, int quantity) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "UPDATE products SET stock_quantity = ? WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, quantity);
            pst.setInt(2, id);
            
            int result = pst.executeUpdate();
            return result > 0;
            
        } finally {
            JDBCUtil.closeAll(conn, pst, null);
        }
    }
    
    /**
     * 从ResultSet构建Product对象
     */
    private Product buildProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setStockQuantity(rs.getInt("stock_quantity"));
        product.setCategory(rs.getString("category"));
        product.setBrand(rs.getString("brand"));
        product.setStatus(rs.getInt("status"));
        product.setCreateTime(rs.getTimestamp("create_time"));
        product.setUpdateTime(rs.getTimestamp("update_time"));
        return product;
    }
}
