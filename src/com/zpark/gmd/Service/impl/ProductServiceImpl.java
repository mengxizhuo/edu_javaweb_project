package com.zpark.gmd.service.impl;

import com.zpark.gmd.beans.Product;
import com.zpark.gmd.dao.ProductDao;
import com.zpark.gmd.dao.impl.ProductDaoImpl;
import com.zpark.gmd.service.ProductService;
import com.zpark.gmd.utils.JDBCUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 产品服务实现类
 */
public class ProductServiceImpl implements ProductService {
    
    private ProductDao productDao = new ProductDaoImpl();
    
    @Override
    public List<Product> findAll() throws Exception {
        return productDao.findAll();
    }
    
    @Override
    public Product findById(int id) throws Exception {
        return productDao.findById(id);
    }
    
    @Override
    public boolean add(Product product) throws Exception {
        // 参数验证
        if (product == null) {
            throw new IllegalArgumentException("产品信息不能为空");
        }
        
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("产品名称不能为空");
        }
        
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("产品价格不能为空且不能小于0");
        }
        
        if (product.getStockQuantity() < 0) {
            throw new IllegalArgumentException("库存数量不能小于0");
        }
        
        // 开启事务
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            
            boolean result = productDao.add(product);
            
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
    public boolean update(Product product) throws Exception {
        // 参数验证
        if (product == null) {
            throw new IllegalArgumentException("产品信息不能为空");
        }
        
        if (product.getId() <= 0) {
            throw new IllegalArgumentException("产品ID不正确");
        }
        
        // 检查产品是否存在
        Product existingProduct = productDao.findById(product.getId());
        if (existingProduct == null) {
            throw new IllegalArgumentException("产品不存在");
        }
        
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("产品名称不能为空");
        }
        
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("产品价格不能为空且不能小于0");
        }
        
        if (product.getStockQuantity() < 0) {
            throw new IllegalArgumentException("库存数量不能小于0");
        }
        
        // 开启事务
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            
            boolean result = productDao.update(product);
            
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
            throw new IllegalArgumentException("产品ID不正确");
        }
        
        // 检查产品是否存在
        Product product = productDao.findById(id);
        if (product == null) {
            throw new IllegalArgumentException("产品不存在");
        }
        
        // 开启事务
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            
            boolean result = productDao.delete(id);
            
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
    public List<Product> findByPage(int page, int size) throws Exception {
        if (page <= 0) {
            throw new IllegalArgumentException("页码必须大于0");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("每页大小必须大于0");
        }
        
        int offset = (page - 1) * size;
        return productDao.findByPage(offset, size);
    }
    
    @Override
    public int count() throws Exception {
        return productDao.count();
    }
    
    @Override
    public List<Product> findByCategory(String category) throws Exception {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("分类不能为空");
        }
        return productDao.findByCategory(category);
    }
    
    @Override
    public List<Product> findByBrand(String brand) throws Exception {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("品牌不能为空");
        }
        return productDao.findByBrand(brand);
    }
    
    @Override
    public List<Product> findByStatus(int status) throws Exception {
        return productDao.findByStatus(status);
    }
    
    @Override
    public List<Product> findByCondition(String name, String category, String brand, Integer status) throws Exception {
        return productDao.findByCondition(name, category, brand, status);
    }
    
    @Override
    public boolean batchDelete(int[] ids) throws Exception {
        if (ids == null || ids.length == 0) {
            throw new IllegalArgumentException("删除的产品ID不能为空");
        }
        
        // 开启事务
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            
            boolean result = productDao.batchDelete(ids);
            
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
    public boolean updateStock(int id, int quantity) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("产品ID不正确");
        }
        
        if (quantity < 0) {
            throw new IllegalArgumentException("库存数量不能小于0");
        }
        
        // 检查产品是否存在
        Product product = productDao.findById(id);
        if (product == null) {
            throw new IllegalArgumentException("产品不存在");
        }
        
        // 开启事务
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            
            boolean result = productDao.updateStock(id, quantity);
            
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
}
