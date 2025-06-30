-- 创建产品表
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

-- 创建客户表
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

-- 插入产品测试数据
INSERT INTO products (name, description, price, stock_quantity, category, brand, status) VALUES
('iPhone 15 Pro', '苹果最新旗舰手机，搭载A17 Pro芯片', 7999.00, 50, '手机', 'Apple', 1),
('MacBook Air M2', '轻薄便携笔记本电脑，13.6英寸', 8999.00, 30, '笔记本', 'Apple', 1),
('小米13 Ultra', '徕卡影像旗舰手机', 5999.00, 80, '手机', '小米', 1),
('华为MateBook X Pro', '高性能商务笔记本', 9999.00, 25, '笔记本', '华为', 1),
('iPad Pro 12.9', '专业级平板电脑', 6999.00, 40, '平板', 'Apple', 1),
('Surface Pro 9', '微软二合一平板电脑', 7299.00, 35, '平板', 'Microsoft', 1),
('AirPods Pro 2', '主动降噪无线耳机', 1899.00, 100, '耳机', 'Apple', 1),
('Sony WH-1000XM5', '头戴式降噪耳机', 2399.00, 60, '耳机', 'Sony', 1),
('Dell XPS 13', '超薄商务笔记本', 8499.00, 20, '笔记本', 'Dell', 1),
('Samsung Galaxy S23', '三星旗舰手机', 5499.00, 70, '手机', 'Samsung', 1);

-- 插入客户测试数据
INSERT INTO customers (name, email, phone, address, company, contact_person, customer_type, status) VALUES
('张三', 'zhangsan@email.com', '13800138001', '北京市朝阳区建国路1号', NULL, NULL, 1, 1),
('李四', 'lisi@email.com', '13800138002', '上海市浦东新区陆家嘴金融中心', NULL, NULL, 1, 1),
('王五', 'wangwu@email.com', '13800138003', '广州市天河区珠江新城', NULL, NULL, 1, 1),
('阿里巴巴集团', 'contact@alibaba.com', '0571-85022088', '浙江省杭州市余杭区文一西路969号', '阿里巴巴集团控股有限公司', '马云', 2, 1),
('腾讯科技', 'contact@tencent.com', '0755-86013388', '广东省深圳市南山区科技园', '深圳市腾讯计算机系统有限公司', '马化腾', 2, 1),
('百度公司', 'contact@baidu.com', '010-59928888', '北京市海淀区上地十街10号', '北京百度网讯科技有限公司', '李彦宏', 2, 1),
('赵六', 'zhaoliu@email.com', '13800138006', '成都市高新区天府大道', NULL, NULL, 1, 1),
('钱七', 'qianqi@email.com', '13800138007', '杭州市西湖区文三路', NULL, NULL, 1, 1),
('华为技术', 'contact@huawei.com', '0755-28780808', '广东省深圳市龙岗区坂田华为基地', '华为技术有限公司', '任正非', 2, 1),
('小米科技', 'contact@xiaomi.com', '010-60606666', '北京市海淀区清河中街68号', '小米科技有限责任公司', '雷军', 2, 1);
