package com.zpark.gmd.service.impl;

import com.zpark.gmd.beans.*;
import com.zpark.gmd.dao.CustomerDao;
import com.zpark.gmd.dao.EmployeeDao;
import com.zpark.gmd.dao.ProductDao;
import com.zpark.gmd.dao.impl.CustomerDaoImpl;
import com.zpark.gmd.dao.impl.EmployeeDaoImpl;
import com.zpark.gmd.dao.impl.ProductDaoImpl;
import com.zpark.gmd.service.SearchService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 搜索服务实现类
 */
public class SearchServiceImpl implements SearchService {
    
    private EmployeeDao employeeDao = new EmployeeDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    private CustomerDao customerDao = new CustomerDaoImpl();
    
    @Override
    public List<SearchResult> globalSearch(String keyword, int limit) throws Exception {
        List<SearchResult> results = new ArrayList<>();
        
        if (keyword == null || keyword.trim().isEmpty()) {
            return results;
        }
        
        // 搜索员工
        List<SearchResult> employeeResults = searchEmployees(keyword);
        results.addAll(employeeResults);
        
        // 搜索产品
        List<SearchResult> productResults = searchProducts(keyword);
        results.addAll(productResults);
        
        // 搜索客户
        List<SearchResult> customerResults = searchCustomers(keyword);
        results.addAll(customerResults);
        
        // 限制结果数量
        if (limit > 0 && results.size() > limit) {
            results = results.subList(0, limit);
        }
        
        return results;
    }
    
    @Override
    public List<SearchResult> searchEmployees(String keyword) throws Exception {
        List<SearchResult> results = new ArrayList<>();
        List<Employee> employees = employeeDao.findAll();
        
        for (Employee emp : employees) {
            if (matchesEmployee(emp, keyword)) {
                String title = emp.getName() + " (" + emp.getUsername() + ")";
                String content = buildEmployeeContent(emp);
                String url = "/emps/findById?id=" + emp.getId();
                
                results.add(new SearchResult("employee", title, content, url, emp));
            }
        }
        
        return results;
    }
    
    @Override
    public List<SearchResult> searchProducts(String keyword) throws Exception {
        List<SearchResult> results = new ArrayList<>();
        List<Product> products = productDao.findAll();
        
        for (Product product : products) {
            if (matchesProduct(product, keyword)) {
                String title = product.getName();
                String content = buildProductContent(product);
                String url = "/products/findById?id=" + product.getId();
                
                results.add(new SearchResult("product", title, content, url, product));
            }
        }
        
        return results;
    }
    
    @Override
    public List<SearchResult> searchCustomers(String keyword) throws Exception {
        List<SearchResult> results = new ArrayList<>();
        List<Customer> customers = customerDao.findAll();
        
        for (Customer customer : customers) {
            if (matchesCustomer(customer, keyword)) {
                String title = customer.getName();
                String content = buildCustomerContent(customer);
                String url = "/customers/findById?id=" + customer.getId();
                
                results.add(new SearchResult("customer", title, content, url, customer));
            }
        }
        
        return results;
    }
    
    /**
     * 检查员工是否匹配关键词
     */
    private boolean matchesEmployee(Employee emp, String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return (emp.getName() != null && emp.getName().toLowerCase().contains(lowerKeyword)) ||
               (emp.getUsername() != null && emp.getUsername().toLowerCase().contains(lowerKeyword)) ||
               (emp.getDeptId() != null && emp.getDeptId().toString().contains(keyword));
    }
    
    /**
     * 检查产品是否匹配关键词
     */
    private boolean matchesProduct(Product product, String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return (product.getName() != null && product.getName().toLowerCase().contains(lowerKeyword)) ||
               (product.getCategory() != null && product.getCategory().toLowerCase().contains(lowerKeyword)) ||
               (product.getBrand() != null && product.getBrand().toLowerCase().contains(lowerKeyword)) ||
               (product.getDescription() != null && product.getDescription().toLowerCase().contains(lowerKeyword));
    }
    
    /**
     * 检查客户是否匹配关键词
     */
    private boolean matchesCustomer(Customer customer, String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return (customer.getName() != null && customer.getName().toLowerCase().contains(lowerKeyword)) ||
               (customer.getEmail() != null && customer.getEmail().toLowerCase().contains(lowerKeyword)) ||
               (customer.getPhone() != null && customer.getPhone().contains(keyword)) ||
               (customer.getCompany() != null && customer.getCompany().toLowerCase().contains(lowerKeyword)) ||
               (customer.getContactPerson() != null && customer.getContactPerson().toLowerCase().contains(lowerKeyword));
    }
    
    /**
     * 构建员工内容描述
     */
    private String buildEmployeeContent(Employee emp) {
        StringBuilder content = new StringBuilder();
        content.append("账号: ").append(emp.getUsername());
        
        if (emp.getDeptId() != null) {
            content.append(" | 部门: ").append(emp.getDeptId());
        }
        
        if (emp.getSalary() != null) {
            content.append(" | 薪资: ").append(emp.getSalary());
        }
        
        if (emp.getJoinDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            content.append(" | 入职日期: ").append(sdf.format(emp.getJoinDate()));
        }
        
        return content.toString();
    }
    
    /**
     * 构建产品内容描述
     */
    private String buildProductContent(Product product) {
        StringBuilder content = new StringBuilder();
        
        if (product.getCategory() != null) {
            content.append("分类: ").append(product.getCategory());
        }
        
        if (product.getBrand() != null) {
            content.append(" | 品牌: ").append(product.getBrand());
        }
        
        if (product.getPrice() != null) {
            content.append(" | 价格: ¥").append(product.getPrice());
        }
        
        if (product.getStockQuantity() > 0 ) {
            content.append(" | 库存: ").append(product.getStockQuantity());
        }
        
        if (product.getDescription() != null && !product.getDescription().isEmpty()) {
            String desc = product.getDescription();
            if (desc.length() > 50) {
                desc = desc.substring(0, 50) + "...";
            }
            content.append(" | ").append(desc);
        }
        
        return content.toString();
    }
    
    /**
     * 构建客户内容描述
     */
    private String buildCustomerContent(Customer customer) {
        StringBuilder content = new StringBuilder();

        content.append(customer.getCustomerType() == 1 ? "个人客户" : "企业客户");

        if (customer.getEmail() != null) {
            content.append(" | 邮箱: ").append(customer.getEmail());
        }

        if (customer.getPhone() != null) {
            content.append(" | 电话: ").append(customer.getPhone());
        }

        if (customer.getCompany() != null && !customer.getCompany().isEmpty()) {
            content.append(" | 公司: ").append(customer.getCompany());
        }

        if (customer.getContactPerson() != null && !customer.getContactPerson().isEmpty()) {
            content.append(" | 联系人: ").append(customer.getContactPerson());
        }

        return content.toString();
    }
}
