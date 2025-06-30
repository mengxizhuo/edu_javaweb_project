package com.zpark.gmd.vo;

/**
 * 员工视图对象
 * 用于向前端展示数据，不包含敏感信息如密码
 */
public class EmployeeVO {
    private int id;
    private String name;
    private String username;
    private String gender;
    private String genderText;
    private Integer salary;
    private String formattedSalary;
    private String joinDate;
    private Integer deptId;
    private String deptName;
    
    public EmployeeVO() {
    }
    
    public EmployeeVO(int id, String name, String username, String gender, 
                     Integer salary, String joinDate, Integer deptId) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.gender = gender;
        this.salary = salary;
        this.joinDate = joinDate;
        this.deptId = deptId;
        
        // 设置性别显示文本
        this.genderText = "1".equals(gender) ? "男" : ("0".equals(gender) ? "女" : "未知");
        
        // 设置格式化薪资
        this.formattedSalary = salary != null ? String.format("%,d", salary) : "0";
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
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
        // 同时更新性别显示文本
        this.genderText = "1".equals(gender) ? "男" : ("0".equals(gender) ? "女" : "未知");
    }
    
    public String getGenderText() {
        return genderText;
    }
    
    public void setGenderText(String genderText) {
        this.genderText = genderText;
    }
    
    public Integer getSalary() {
        return salary;
    }
    
    public void setSalary(Integer salary) {
        this.salary = salary;
        // 同时更新格式化薪资
        this.formattedSalary = salary != null ? String.format("%,d", salary) : "0";
    }
    
    public String getFormattedSalary() {
        return formattedSalary;
    }
    
    public void setFormattedSalary(String formattedSalary) {
        this.formattedSalary = formattedSalary;
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
    
    public String getDeptName() {
        return deptName;
    }
    
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
    @Override
    public String toString() {
        return "EmployeeVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", genderText='" + genderText + '\'' +
                ", salary=" + salary +
                ", formattedSalary='" + formattedSalary + '\'' +
                ", joinDate='" + joinDate + '\'' +
                ", deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
