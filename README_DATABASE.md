# 数据库初始化说明

## 📊 数据库表结构

本项目新增了两个数据表：`products`（产品表）和 `customers`（客户表）。

### 1. 产品表 (products)

```sql
CREATE TABLE IF NOT EXISTS products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL COMMENT '产品名称',
    description TEXT COMMENT '产品描述',
    price DECIMAL(10,2) NOT NULL COMMENT '产品价格',
    stock_quantity INT DEFAULT 0 COMMENT '库存数量',
    category VARCHAR(50) COMMENT '产品分类',
    brand VARCHAR(50) COMMENT '品牌',
    status TINYINT DEFAULT 1 COMMENT '状态：1-上架，0-下架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='产品表';
```

### 2. 客户表 (customers)

```sql
CREATE TABLE IF NOT EXISTS customers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL COMMENT '客户姓名',
    email VARCHAR(100) UNIQUE COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '电话号码',
    address TEXT COMMENT '地址',
    company VARCHAR(100) COMMENT '公司名称',
    contact_person VARCHAR(50) COMMENT '联系人',
    customer_type TINYINT DEFAULT 1 COMMENT '客户类型：1-个人，2-企业',
    status TINYINT DEFAULT 1 COMMENT '状态：1-活跃，0-停用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='客户表';
```

## 🚀 初始化步骤

### 方法一：使用SQL脚本

1. 连接到你的MySQL数据库
2. 执行 `sql/create_tables.sql` 文件中的SQL语句
3. 该脚本会自动创建表结构并插入测试数据

### 方法二：手动执行

1. 打开MySQL客户端或phpMyAdmin
2. 选择你的数据库（通常是 `gmd` 或你配置的数据库名）
3. 复制并执行上述CREATE TABLE语句
4. 执行测试数据插入语句（见下方）

## 📝 测试数据

### 产品测试数据

系统会自动插入10条产品测试数据，包括：
- iPhone 15 Pro (¥7999.00)
- MacBook Air M2 (¥8999.00)
- 小米13 Ultra (¥5999.00)
- 华为MateBook X Pro (¥9999.00)
- iPad Pro 12.9 (¥6999.00)
- Surface Pro 9 (¥7299.00)
- AirPods Pro 2 (¥1899.00)
- Sony WH-1000XM5 (¥2399.00)
- Dell XPS 13 (¥8499.00)
- Samsung Galaxy S23 (¥5499.00)

### 客户测试数据

系统会自动插入10条客户测试数据，包括：
- 个人客户：张三、李四、王五、赵六、钱七
- 企业客户：阿里巴巴集团、腾讯科技、百度公司、华为技术、小米科技

## 🔧 功能特性

### 产品管理功能
- ✅ 产品列表查看
- ✅ 新增产品
- ✅ 修改产品信息
- ✅ 删除产品
- ✅ 产品状态管理（上架/下架）
- ✅ 库存管理
- ✅ 分类和品牌管理

### 客户管理功能
- ✅ 客户列表查看
- ✅ 新增客户
- ✅ 修改客户信息
- ✅ 删除客户
- ✅ 客户类型管理（个人/企业）
- ✅ 客户状态管理（活跃/停用）
- ✅ 邮箱唯一性验证

## 🌐 访问地址

启动服务器后，可以通过以下地址访问：

- **首页**: http://localhost:8088/edu_javaweb_project/
- **员工管理**: http://localhost:8088/edu_javaweb_project/emps/findAll
- **产品管理**: http://localhost:8088/edu_javaweb_project/products/findAll
- **客户管理**: http://localhost:8088/edu_javaweb_project/customers/findAll

## 📋 注意事项

1. 确保数据库连接配置正确
2. 确保MySQL服务正在运行
3. 如果遇到中文乱码，请检查数据库字符集设置
4. 建议使用UTF-8字符集

## 🛠️ 技术架构

- **后端**: Java Servlet + JSP
- **数据库**: MySQL
- **前端**: Bootstrap 5 + Feather Icons
- **架构模式**: MVC (Model-View-Controller)

## 📞 支持

如果在使用过程中遇到问题，请检查：
1. 数据库连接配置
2. 服务器启动状态
3. 浏览器控制台错误信息
4. 服务器日志输出
