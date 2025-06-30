package com.zpark.gmd.beans;

import java.util.Date;

/**
 * 客户实体类
 */
public class Customer {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String company;
    private String contactPerson;
    private int customerType; // 1-个人，2-企业
    private int status; // 1-活跃，0-停用
    private Date createTime;
    private Date updateTime;
    
    public Customer() {
    }
    
    public Customer(String name, String email, String phone, String address, 
                   String company, String contactPerson, int customerType, int status) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.company = company;
        this.contactPerson = contactPerson;
        this.customerType = customerType;
        this.status = status;
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCompany() {
        return company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getContactPerson() {
        return contactPerson;
    }
    
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    
    public int getCustomerType() {
        return customerType;
    }
    
    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    /**
     * 获取客户类型显示文本
     */
    public String getCustomerTypeText() {
        return customerType == 1 ? "个人" : "企业";
    }
    
    /**
     * 获取状态显示文本
     */
    public String getStatusText() {
        return status == 1 ? "活跃" : "停用";
    }
    
    /**
     * 数据验证
     */
    public boolean isValid() {
        return name != null && !name.trim().isEmpty();
    }
    
    /**
     * 获取显示名称（企业客户显示公司名称，个人客户显示姓名）
     */
    public String getDisplayName() {
        if (customerType == 2 && company != null && !company.trim().isEmpty()) {
            return company;
        }
        return name;
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", company='" + company + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", customerType=" + customerType +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return id == customer.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
