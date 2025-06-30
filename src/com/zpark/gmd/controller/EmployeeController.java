package com.zpark.gmd.controller;

import com.zpark.gmd.beans.Employee;
import com.zpark.gmd.common.Result;
import com.zpark.gmd.service.EmployeeService;
import com.zpark.gmd.service.impl.EmployeeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 员工管理Controller
 */
@WebServlet("/emps/*")
public class EmployeeController extends BaseController {
    
    private EmployeeService employeeService = new EmployeeServiceImpl();
    
    /**
     * 查询所有员工
     */
    public Result<List<Employee>> findAll(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Employee> employees = employeeService.findAll();

            // 如果是页面请求，转发到JSP页面
            String accept = req.getHeader("Accept");
            if (accept != null && accept.contains("text/html")) {
                req.setAttribute("list", employees);
                forward(req, resp, "/list.jsp");
                return null;
            }

            // 如果是AJAX请求，返回JSON数据
            return Result.success("查询成功", employees);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询员工列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 新增员工 - 处理POST请求
     */
    public Result<Void> add(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 如果是GET请求，转发到添加页面
        if ("GET".equalsIgnoreCase(req.getMethod())) {
            forward(req, resp, "/addEmp.jsp");
            return null;
        }
        try {
            // 参数验证
            String name = getParameter(req, "name");
            String username = getParameter(req, "username");
            String password = getParameter(req, "password");
            String gender = getParameter(req, "gender");
            String salaryStr = getParameter(req, "salary");
            String joinDateStr = getParameter(req, "join_date");
            String deptIdStr = getParameter(req, "dept_id");
            
            if (isEmpty(name) || isEmpty(username) || isEmpty(password)) {
                return Result.paramError("姓名、用户名和密码不能为空");
            }
            
            // 创建员工对象
            Employee employee = new Employee();
            employee.setName(name);
            employee.setUsername(username);
            employee.setPassword(password);
            employee.setGender(gender);
            
            // 处理薪资
            if (!isEmpty(salaryStr)) {
                try {
                    employee.setSalary(Integer.parseInt(salaryStr));
                } catch (NumberFormatException e) {
                    return Result.paramError("薪资格式不正确");
                }
            }
            
            // 处理入职日期
            if (!isEmpty(joinDateStr)) {
                try {
                    Date joinDate = new SimpleDateFormat("yyyy-MM-dd").parse(joinDateStr);
                    employee.setJoinDate(joinDate);
                } catch (ParseException e) {
                    return Result.paramError("入职日期格式不正确");
                }
            }
            
            // 处理部门ID
            if (!isEmpty(deptIdStr)) {
                try {
                    employee.setDeptId(Integer.parseInt(deptIdStr));
                } catch (NumberFormatException e) {
                    return Result.paramError("部门ID格式不正确");
                }
            }
            
            // 调用服务层添加员工
            boolean success = employeeService.add(employee);
            
            if (success) {
                // 如果是页面请求，重定向到列表页面
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    redirect(resp, req.getContextPath() + "/emps/findAll");
                    return null;
                }
                return Result.success("添加员工成功");
            } else {
                return Result.error("添加员工失败");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("添加员工失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除员工
     */
    public Result<Void> delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String idStr = getParameter(req, "id");
            if (isEmpty(idStr)) {
                return Result.paramError("员工ID不能为空");
            }
            
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                return Result.paramError("员工ID格式不正确");
            }
            
            boolean success = employeeService.delete(id);
            
            if (success) {
                return Result.success("删除员工成功");
            } else {
                return Result.error("删除员工失败");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除员工失败: " + e.getMessage());
        }
    }
    
    /**
     * 修改员工信息
     */
    public Result<Void> update(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String idStr = getParameter(req, "id");
            if (isEmpty(idStr)) {
                return Result.paramError("员工ID不能为空");
            }
            
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                return Result.paramError("员工ID格式不正确");
            }
            
            // 获取现有员工信息
            Employee employee = employeeService.findById(id);
            if (employee == null) {
                return Result.error("员工不存在");
            }
            
            // 更新员工信息
            String name = getParameter(req, "name");
            String username = getParameter(req, "username");
            String password = getParameter(req, "password");
            String gender = getParameter(req, "gender");
            String salaryStr = getParameter(req, "salary");
            String joinDateStr = getParameter(req, "join_date");
            String deptIdStr = getParameter(req, "dept_id");
            
            if (!isEmpty(name)) employee.setName(name);
            if (!isEmpty(username)) employee.setUsername(username);
            if (!isEmpty(password)) employee.setPassword(password);
            if (!isEmpty(gender)) employee.setGender(gender);
            
            if (!isEmpty(salaryStr)) {
                try {
                    employee.setSalary(Integer.parseInt(salaryStr));
                } catch (NumberFormatException e) {
                    return Result.paramError("薪资格式不正确");
                }
            }
            
            if (!isEmpty(joinDateStr)) {
                try {
                    Date joinDate = new SimpleDateFormat("yyyy-MM-dd").parse(joinDateStr);
                    employee.setJoinDate(joinDate);
                } catch (ParseException e) {
                    return Result.paramError("入职日期格式不正确");
                }
            }
            
            if (!isEmpty(deptIdStr)) {
                try {
                    employee.setDeptId(Integer.parseInt(deptIdStr));
                } catch (NumberFormatException e) {
                    return Result.paramError("部门ID格式不正确");
                }
            }
            
            boolean success = employeeService.update(employee);

            if (success) {
                // 如果是页面请求，重定向到列表页面
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    redirect(resp, req.getContextPath() + "/emps/findAll");
                    return null;
                }
                return Result.success("修改员工信息成功");
            } else {
                return Result.error("修改员工信息失败");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("修改员工信息失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID查询员工 - 支持页面跳转和AJAX请求
     */
    public Result<Employee> findById(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String idStr = getParameter(req, "id");
            if (isEmpty(idStr)) {
                // 如果是页面请求，转发到错误页面
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.setAttribute("errorMsg", "员工ID不能为空");
                    forward(req, resp, "/error.jsp");
                    return null;
                }
                return Result.paramError("员工ID不能为空");
            }

            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.setAttribute("errorMsg", "员工ID格式不正确");
                    forward(req, resp, "/error.jsp");
                    return null;
                }
                return Result.paramError("员工ID格式不正确");
            }

            Employee employee = employeeService.findById(id);

            if (employee != null) {
                // 如果是页面请求，转发到修改页面
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.setAttribute("employee", employee);
                    forward(req, resp, "/modifyEmp.jsp");
                    return null;
                }

                // 如果是AJAX请求，返回JSON数据
                return Result.success("查询成功", employee);
            } else {
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.setAttribute("errorMsg", "员工不存在");
                    forward(req, resp, "/error.jsp");
                    return null;
                }
                return Result.error("员工不存在");
            }

        } catch (Exception e) {
            e.printStackTrace();
            String accept = req.getHeader("Accept");
            if (accept != null && accept.contains("text/html")) {
                req.setAttribute("errorMsg", "查询员工失败，请稍后重试");
                forward(req, resp, "/error.jsp");
                return null;
            }
            return Result.error("查询员工失败: " + e.getMessage());
        }
    }
}
