package com.zpark.gmd.controller;

import com.zpark.gmd.beans.Product;
import com.zpark.gmd.common.Result;
import com.zpark.gmd.service.ProductService;
import com.zpark.gmd.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 产品管理Controller
 */
@WebServlet("/products/*")
public class ProductController extends BaseController {
    
    private ProductService productService = new ProductServiceImpl();
    
    /**
     * 查询所有产品
     */
    public Result<List<Product>> findAll(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        try {
            List<Product> products = productService.findAll();
            
            // 如果是页面请求，转发到JSP页面
            String accept = req.getHeader("Accept");
            if (accept != null && accept.contains("text/html")) {
                req.setAttribute("list", products);
                forward(req, resp, "/productList.jsp");
                return null;
            }
            
            // 如果是AJAX请求，返回JSON数据
            return Result.success("查询成功", products);
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询产品列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 新增产品
     */
    public Result<Void> add(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // 如果是GET请求，转发到添加页面
        if ("GET".equalsIgnoreCase(req.getMethod())) {
            forward(req, resp, "/addProduct.jsp");
            return null;
        }
        
        try {
            // 参数验证
            String name = getParameter(req, "name");
            String description = getParameter(req, "description");
            String priceStr = getParameter(req, "price");
            String stockQuantityStr = getParameter(req, "stock_quantity");
            String category = getParameter(req, "category");
            String brand = getParameter(req, "brand");
            String statusStr = getParameter(req, "status");
            
            if (isEmpty(name)) {
                return Result.paramError("产品名称不能为空");
            }
            
            if (isEmpty(priceStr)) {
                return Result.paramError("产品价格不能为空");
            }
            
            // 创建产品对象
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setCategory(category);
            product.setBrand(brand);
            
            // 处理价格
            try {
                product.setPrice(new BigDecimal(priceStr));
            } catch (NumberFormatException e) {
                return Result.paramError("价格格式不正确");
            }
            
            // 处理库存数量
            if (!isEmpty(stockQuantityStr)) {
                try {
                    product.setStockQuantity(Integer.parseInt(stockQuantityStr));
                } catch (NumberFormatException e) {
                    return Result.paramError("库存数量格式不正确");
                }
            } else {
                product.setStockQuantity(0);
            }
            
            // 处理状态
            if (!isEmpty(statusStr)) {
                try {
                    product.setStatus(Integer.parseInt(statusStr));
                } catch (NumberFormatException e) {
                    return Result.paramError("状态格式不正确");
                }
            } else {
                product.setStatus(1); // 默认上架
            }
            
            // 调用服务层添加产品
            boolean success = productService.add(product);
            
            if (success) {
                // 如果是页面请求，重定向到列表页面
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    redirect(resp, req.getContextPath() + "/products/findAll");
                    return null;
                }
                return Result.success("添加产品成功");
            } else {
                return Result.error("添加产品失败");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("添加产品失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除产品
     */
    public Result<Void> delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String idStr = getParameter(req, "id");
            if (isEmpty(idStr)) {
                return Result.paramError("产品ID不能为空");
            }
            
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                return Result.paramError("产品ID格式不正确");
            }
            
            boolean success = productService.delete(id);
            
            if (success) {
                return Result.success("删除产品成功");
            } else {
                return Result.error("删除产品失败");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除产品失败: " + e.getMessage());
        }
    }
    
    /**
     * 修改产品信息
     */
    public Result<Void> update(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String idStr = getParameter(req, "id");
            if (isEmpty(idStr)) {
                return Result.paramError("产品ID不能为空");
            }
            
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                return Result.paramError("产品ID格式不正确");
            }
            
            // 获取现有产品信息
            Product product = productService.findById(id);
            if (product == null) {
                return Result.error("产品不存在");
            }
            
            // 更新产品信息
            String name = getParameter(req, "name");
            String description = getParameter(req, "description");
            String priceStr = getParameter(req, "price");
            String stockQuantityStr = getParameter(req, "stock_quantity");
            String category = getParameter(req, "category");
            String brand = getParameter(req, "brand");
            String statusStr = getParameter(req, "status");
            
            if (!isEmpty(name)) product.setName(name);
            if (description != null) product.setDescription(description);
            if (!isEmpty(category)) product.setCategory(category);
            if (!isEmpty(brand)) product.setBrand(brand);
            
            if (!isEmpty(priceStr)) {
                try {
                    product.setPrice(new BigDecimal(priceStr));
                } catch (NumberFormatException e) {
                    return Result.paramError("价格格式不正确");
                }
            }
            
            if (!isEmpty(stockQuantityStr)) {
                try {
                    product.setStockQuantity(Integer.parseInt(stockQuantityStr));
                } catch (NumberFormatException e) {
                    return Result.paramError("库存数量格式不正确");
                }
            }
            
            if (!isEmpty(statusStr)) {
                try {
                    product.setStatus(Integer.parseInt(statusStr));
                } catch (NumberFormatException e) {
                    return Result.paramError("状态格式不正确");
                }
            }
            
            boolean success = productService.update(product);
            
            if (success) {
                // 如果是页面请求，重定向到列表页面
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    redirect(resp, req.getContextPath() + "/products/findAll");
                    return null;
                }
                return Result.success("修改产品信息成功");
            } else {
                return Result.error("修改产品信息失败");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("修改产品信息失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID查询产品
     */
    public Result<Product> findById(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        try {
            String idStr = getParameter(req, "id");
            if (isEmpty(idStr)) {
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.setAttribute("errorMsg", "产品ID不能为空");
                    forward(req, resp, "/error.jsp");
                    return null;
                }
                return Result.paramError("产品ID不能为空");
            }
            
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.setAttribute("errorMsg", "产品ID格式不正确");
                    forward(req, resp, "/error.jsp");
                    return null;
                }
                return Result.paramError("产品ID格式不正确");
            }
            
            Product product = productService.findById(id);
            
            if (product != null) {
                // 如果是页面请求，转发到修改页面
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.setAttribute("product", product);
                    forward(req, resp, "/modifyProduct.jsp");
                    return null;
                }
                
                // 如果是AJAX请求，返回JSON数据
                return Result.success("查询成功", product);
            } else {
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.setAttribute("errorMsg", "产品不存在");
                    forward(req, resp, "/error.jsp");
                    return null;
                }
                return Result.error("产品不存在");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            String accept = req.getHeader("Accept");
            if (accept != null && accept.contains("text/html")) {
                req.setAttribute("errorMsg", "查询产品失败，请稍后重试");
                forward(req, resp, "/error.jsp");
                return null;
            }
            return Result.error("查询产品失败: " + e.getMessage());
        }
    }
}
