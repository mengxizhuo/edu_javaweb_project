package com.zpark.gmd.dto;

import java.util.Date;

/**
 * 员工数据传输对象
 * 用于前端和后端之间的数据传输
 */
public class EmployeeDTO {
    private int id;
    private String name;
    private String username;
    private String password;
    private String gender;
    private Integer salary;
    private String joinDate; // 使用String类型便于前端处理
    private Integer deptId;
    
    public EmployeeDTO() {
    }
    
    public EmployeeDTO(int id, String name, String username, String password, 
                      String gender, Integer salary, String joinDate, Integer deptId) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.salary = salary;
        this.joinDate = joinDate;
        this.deptId = deptId;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public Integer getSalary() {
        return salary;
    }
    
    public void setSalary(Integer salary) {
        this.salary = salary;
    }
    
    public String getJoinDate() {
        return joinDate;
    }
    
    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }
    
    public Integer getDeptId() {
        return deptId;
    }
    
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
    
    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", salary=" + salary +
                ", joinDate='" + joinDate + '\'' +
                ", deptId=" + deptId +
                '}';
    }
    
    /**
     * 数据验证
     */
    public boolean isValid() {
        return name != null && !name.trim().isEmpty() &&
               username != null && !username.trim().isEmpty() &&
               password != null && !password.trim().isEmpty();
    }
}
