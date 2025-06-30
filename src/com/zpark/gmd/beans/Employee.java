package com.zpark.gmd.beans;

import java.util.Date;

/**
 * 员工实体类
 */
public class Employee {
    private int id;
    private String name;
    private String username;
    private String password;
    private String gender;
    private Integer salary;
    private Date joinDate;
    private Integer deptId;

    public Employee() {
    }

    public Employee(String username) {
        this.username = username;
    }

    public Employee(int id, String name, String username, String password,
                   String gender, Integer salary, Date joinDate, Integer deptId) {
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

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
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
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", salary=" + salary +
                ", joinDate=" + joinDate +
                ", deptId=" + deptId +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /**
     * 数据验证方法
     */
    public boolean isValid() {
        return name != null && !name.trim().isEmpty() &&
               username != null && !username.trim().isEmpty() &&
               password != null && !password.trim().isEmpty();
    }

    /**
     * 获取性别显示文本
     */
    public String getGenderText() {
        if ("1".equals(gender)) {
            return "男";
        } else if ("0".equals(gender)) {
            return "女";
        }
        return "未知";
    }

    /**
     * 获取格式化的薪资
     */
    public String getFormattedSalary() {
        if (salary == null) {
            return "0";
        }
        return String.format("%,d", salary);
    }

}
