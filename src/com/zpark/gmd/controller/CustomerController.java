package com.zpark.gmd.controller;

import com.zpark.gmd.beans.Customer;
import com.zpark.gmd.common.Result;
import com.zpark.gmd.service.CustomerService;
import com.zpark.gmd.service.impl.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 客户管理Controller
 */
@WebServlet("/customers/*")
public class CustomerController extends BaseController {
    
    private CustomerService customerService = new CustomerServiceImpl();
    
    /**
     * 查询所有客户
     */
    public Result<List<Customer>> findAll(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        try {
            List<Customer> customers = customerService.findAll();
            
            // 如果是页面请求，转发到JSP页面
            String accept = req.getHeader("Accept");
            if (accept != null && accept.contains("text/html")) {
                req.setAttribute("list", customers);
                forward(req, resp, "/customerList.jsp");
                return null;
            }
            
            // 如果是AJAX请求，返回JSON数据
            return Result.success("查询成功", customers);
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询客户列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 新增客户
     */
    public Result<Void> add(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // 如果是GET请求，转发到添加页面
        if ("GET".equalsIgnoreCase(req.getMethod())) {
            forward(req, resp, "/addCustomer.jsp");
            return null;
        }
        
        try {
            // 参数验证
            String name = getParameter(req, "name");
            String email = getParameter(req, "email");
            String phone = getParameter(req, "phone");
            String address = getParameter(req, "address");
            String company = getParameter(req, "company");
            String contactPerson = getParameter(req, "contact_person");
            String customerTypeStr = getParameter(req, "customer_type");
            String statusStr = getParameter(req, "status");
            
            if (isEmpty(name)) {
                return Result.paramError("客户姓名不能为空");
            }
            
            // 创建客户对象
            Customer customer = new Customer();
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhone(phone);
            customer.setAddress(address);
            customer.setCompany(company);
            customer.setContactPerson(contactPerson);
            
            // 处理客户类型
            if (!isEmpty(customerTypeStr)) {
                try {
                    customer.setCustomerType(Integer.parseInt(customerTypeStr));
                } catch (NumberFormatException e) {
                    return Result.paramError("客户类型格式不正确");
                }
            } else {
                customer.setCustomerType(1); // 默认个人客户
            }
            
            // 处理状态
            if (!isEmpty(statusStr)) {
                try {
                    customer.setStatus(Integer.parseInt(statusStr));
                } catch (NumberFormatException e) {
                    return Result.paramError("状态格式不正确");
                }
            } else {
                customer.setStatus(1); // 默认活跃状态
            }
            
            // 调用服务层添加客户
            boolean success = customerService.add(customer);
            
            if (success) {
                // 如果是页面请求，重定向到列表页面
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    redirect(resp, req.getContextPath() + "/customers/findAll");
                    return null;
                }
                return Result.success("添加客户成功");
            } else {
                return Result.error("添加客户失败");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("添加客户失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除客户
     */
    public Result<Void> delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String idStr = getParameter(req, "id");
            if (isEmpty(idStr)) {
                return Result.paramError("客户ID不能为空");
            }
            
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                return Result.paramError("客户ID格式不正确");
            }
            
            boolean success = customerService.delete(id);
            
            if (success) {
                return Result.success("删除客户成功");
            } else {
                return Result.error("删除客户失败");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除客户失败: " + e.getMessage());
        }
    }
    
    /**
     * 修改客户信息
     */
    public Result<Void> update(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String idStr = getParameter(req, "id");
            if (isEmpty(idStr)) {
                return Result.paramError("客户ID不能为空");
            }
            
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                return Result.paramError("客户ID格式不正确");
            }
            
            // 获取现有客户信息
            Customer customer = customerService.findById(id);
            if (customer == null) {
                return Result.error("客户不存在");
            }
            
            // 更新客户信息
            String name = getParameter(req, "name");
            String email = getParameter(req, "email");
            String phone = getParameter(req, "phone");
            String address = getParameter(req, "address");
            String company = getParameter(req, "company");
            String contactPerson = getParameter(req, "contact_person");
            String customerTypeStr = getParameter(req, "customer_type");
            String statusStr = getParameter(req, "status");
            
            if (!isEmpty(name)) customer.setName(name);
            if (email != null) customer.setEmail(email);
            if (phone != null) customer.setPhone(phone);
            if (address != null) customer.setAddress(address);
            if (company != null) customer.setCompany(company);
            if (contactPerson != null) customer.setContactPerson(contactPerson);
            
            if (!isEmpty(customerTypeStr)) {
                try {
                    customer.setCustomerType(Integer.parseInt(customerTypeStr));
                } catch (NumberFormatException e) {
                    return Result.paramError("客户类型格式不正确");
                }
            }
            
            if (!isEmpty(statusStr)) {
                try {
                    customer.setStatus(Integer.parseInt(statusStr));
                } catch (NumberFormatException e) {
                    return Result.paramError("状态格式不正确");
                }
            }
            
            boolean success = customerService.update(customer);
            
            if (success) {
                // 如果是页面请求，重定向到列表页面
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    redirect(resp, req.getContextPath() + "/customers/findAll");
                    return null;
                }
                return Result.success("修改客户信息成功");
            } else {
                return Result.error("修改客户信息失败");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("修改客户信息失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID查询客户
     */
    public Result<Customer> findById(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        try {
            String idStr = getParameter(req, "id");
            if (isEmpty(idStr)) {
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.setAttribute("errorMsg", "客户ID不能为空");
                    forward(req, resp, "/error.jsp");
                    return null;
                }
                return Result.paramError("客户ID不能为空");
            }
            
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.setAttribute("errorMsg", "客户ID格式不正确");
                    forward(req, resp, "/error.jsp");
                    return null;
                }
                return Result.paramError("客户ID格式不正确");
            }
            
            Customer customer = customerService.findById(id);
            
            if (customer != null) {
                // 如果是页面请求，转发到修改页面
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.setAttribute("customer", customer);
                    forward(req, resp, "/modifyCustomer.jsp");
                    return null;
                }
                
                // 如果是AJAX请求，返回JSON数据
                return Result.success("查询成功", customer);
            } else {
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.setAttribute("errorMsg", "客户不存在");
                    forward(req, resp, "/error.jsp");
                    return null;
                }
                return Result.error("客户不存在");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            String accept = req.getHeader("Accept");
            if (accept != null && accept.contains("text/html")) {
                req.setAttribute("errorMsg", "查询客户失败，请稍后重试");
                forward(req, resp, "/error.jsp");
                return null;
            }
            return Result.error("查询客户失败: " + e.getMessage());
        }
    }
}
