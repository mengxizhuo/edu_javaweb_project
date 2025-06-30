package com.zpark.gmd.controller;

import com.zpark.gmd.beans.Employee;
import com.zpark.gmd.common.Result;
import com.zpark.gmd.service.LoginService;
import com.zpark.gmd.service.impl.LoginServiceImpl;
import com.zpark.gmd.utils.CookieUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录管理Controller
 */
@WebServlet({"/auth/*", "/login", "/logout"})
public class LoginController extends BaseController {
    
    private LoginService loginService = new LoginServiceImpl();
    
    /**
     * 用户登录
     */
    public Result<Employee> login(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // 如果是GET请求，显示登录页面
            if ("GET".equalsIgnoreCase(req.getMethod())) {
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    forward(req, resp, "/login.jsp");
                    return null;
                }
                return Result.error("请使用POST方法登录");
            }
            // 获取参数
            String username = getParameter(req, "username");
            String password = getParameter(req, "password");
            String rememberMe = getParameter(req, "rememberMe");
            
            // 参数验证
            if (isEmpty(username) || isEmpty(password)) {
                // 如果是页面请求，设置错误信息并转发到登录页面
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.setAttribute("msg", "用户名和密码不能为空");
                    forward(req, resp, "/login.jsp");
                    return null;
                }
                return Result.paramError("用户名和密码不能为空");
            }
            
            // 调用服务层进行登录验证
            Employee employee = loginService.login(username, password);
            
            if (employee != null) {
                // 登录成功，将用户信息存入session
                HttpSession session = req.getSession();
                session.setAttribute("loginEmp", employee);

                // 记住密码功能完全由前端JavaScript处理
                // 服务器端不再保存任何记住密码相关的信息
                // 这样更安全，所有数据都在客户端localStorage中

                // 如果是页面请求，重定向到首页
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    redirect(resp, req.getContextPath() + "/index.jsp");
                    return null;
                }

                // 如果是AJAX请求，返回JSON数据
                return Result.success("登录成功", employee);
                
            } else {
                // 登录失败
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.setAttribute("msg", "用户名或密码错误");
                    forward(req, resp, "/login.jsp");
                    return null;
                }
                return Result.error("用户名或密码错误");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            
            // 如果是页面请求，设置错误信息并转发到登录页面
            String accept = req.getHeader("Accept");
            if (accept != null && accept.contains("text/html")) {
                req.setAttribute("msg", "登录失败，请稍后重试");
                forward(req, resp, "/login.jsp");
                return null;
            }
            
            return Result.error("登录失败: " + e.getMessage());
        }
    }
    
    /**
     * 用户登出
     */
    public Result<Void> logout(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // 清除session
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            // 可选：清除记住密码的Cookie（如果用户希望完全登出）
            // 注释掉下面的代码，保留记住密码功能
            /*
            Cookie usernameCookie = new Cookie("rememberedUsername", "");
            usernameCookie.setMaxAge(0);
            usernameCookie.setPath(req.getContextPath());
            resp.addCookie(usernameCookie);
            */

            // 如果是页面请求，重定向到登录页面
            String accept = req.getHeader("Accept");
            if (accept != null && accept.contains("text/html")) {
                redirect(resp, req.getContextPath() + "/login.jsp");
                return null;
            }

            return Result.success("登出成功");
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("登出失败: " + e.getMessage());
        }
    }
    
    /**
     * 检查登录状态
     */
    public Result<Employee> checkLogin(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);
            if (session != null) {
                Employee loginEmp = (Employee) session.getAttribute("loginEmp");
                if (loginEmp != null) {
                    return Result.success("已登录", loginEmp);
                }
            }
            
            return Result.unauthorized("未登录");
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("检查登录状态失败: " + e.getMessage());
        }
    }
    
    /**
     * 修改密码
     */
    public Result<Void> changePassword(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        try {
            // 检查登录状态
            HttpSession session = req.getSession(false);
            if (session == null) {
                return Result.unauthorized("请先登录");
            }
            
            Employee loginEmp = (Employee) session.getAttribute("loginEmp");
            if (loginEmp == null) {
                return Result.unauthorized("请先登录");
            }
            
            // 获取参数
            String oldPassword = getParameter(req, "oldPassword");
            String newPassword = getParameter(req, "newPassword");
            String confirmPassword = getParameter(req, "confirmPassword");
            
            // 参数验证
            String errorMsg = null;
            if (isEmpty(oldPassword) || isEmpty(newPassword) || isEmpty(confirmPassword)) {
                errorMsg = "密码不能为空";
            } else if (!newPassword.equals(confirmPassword)) {
                errorMsg = "两次输入的新密码不一致";
            } else if (newPassword.length() < 6) {
                errorMsg = "新密码长度不能少于6位";
            } else if (!oldPassword.equals(loginEmp.getPassword())) {
                errorMsg = "原密码不正确";
            }

            if (errorMsg != null) {
                // 如果是页面请求，重定向到修改密码页面并显示错误消息
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.getSession().setAttribute("errorMsg", errorMsg);
                    redirect(resp, req.getContextPath() + "/modifyPassword.jsp");
                    return null;
                }
                return Result.paramError(errorMsg);
            }
            
            // 调用服务层修改密码
            boolean success = loginService.changePassword(loginEmp.getId(), newPassword);
            
            if (success) {
                // 更新session中的用户信息
                loginEmp.setPassword(newPassword);
                session.setAttribute("loginEmp", loginEmp);

                // 如果是页面请求，重定向到修改密码页面并显示成功消息
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.getSession().setAttribute("successMsg", "密码修改成功");
                    redirect(resp, req.getContextPath() + "/modifyPassword.jsp");
                    return null;
                }

                return Result.success("密码修改成功");
            } else {
                // 如果是页面请求，重定向到修改密码页面并显示错误消息
                String accept = req.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    req.getSession().setAttribute("errorMsg", "密码修改失败");
                    redirect(resp, req.getContextPath() + "/modifyPassword.jsp");
                    return null;
                }

                return Result.error("密码修改失败");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("密码修改失败: " + e.getMessage());
        }
    }


}
