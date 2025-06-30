<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>全局搜索</title>
    <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/dashboard.css" rel="stylesheet">
    <style>
        .search-hero {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 4rem 0;
        }
        .search-box {
            max-width: 600px;
            margin: 0 auto;
        }
        .search-tips {
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 2rem;
        }
    </style>
</head>
<body>
    <jsp:include page="commons/topbar.jsp"/>
    
    <div class="container-fluid">
        <div class="row">
            <jsp:include page="commons/sidebar.jsp"/>
            
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <!-- 搜索主区域 -->
                <div class="search-hero text-center">
                    <div class="container">
                        <h1 class="display-4 mb-4">全局搜索</h1>
                        <p class="lead mb-4">快速查找员工、产品、客户信息</p>
                        
                        <div class="search-box">
                            <form action="${pageContext.request.contextPath}/search" method="get">
                                <div class="input-group input-group-lg">
                                    <input type="text" class="form-control" name="keyword" 
                                           placeholder="输入关键词搜索..." required>
                                    <button class="btn btn-light" type="submit">
                                        <i data-feather="search"></i> 搜索
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                
                <!-- 搜索提示 -->
                <div class="container mt-5">
                    <div class="search-tips">
                        <h4 class="mb-3">搜索提示</h4>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="d-flex align-items-start mb-3">
                                    <i data-feather="users" class="text-primary me-3 mt-1"></i>
                                    <div>
                                        <h6>员工搜索</h6>
                                        <small class="text-muted">可搜索员工姓名、账号、部门编号</small>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="d-flex align-items-start mb-3">
                                    <i data-feather="package" class="text-success me-3 mt-1"></i>
                                    <div>
                                        <h6>产品搜索</h6>
                                        <small class="text-muted">可搜索产品名称、分类、品牌、描述</small>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="d-flex align-items-start mb-3">
                                    <i data-feather="user-check" class="text-info me-3 mt-1"></i>
                                    <div>
                                        <h6>客户搜索</h6>
                                        <small class="text-muted">可搜索客户姓名、邮箱、电话、公司</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <!-- 快捷入口 -->
                <div class="container mt-5">
                    <h4 class="mb-4">快捷入口</h4>
                    <div class="row">
                        <div class="col-md-4 mb-3">
                            <div class="card h-100">
                                <div class="card-body text-center">
                                    <i data-feather="users" class="text-primary mb-3" style="width: 48px; height: 48px;"></i>
                                    <h5 class="card-title">员工管理</h5>
                                    <p class="card-text">管理员工信息，包括新增、修改、删除等操作</p>
                                    <a href="${pageContext.request.contextPath}/emps/findAll" class="btn btn-primary">
                                        进入管理
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 mb-3">
                            <div class="card h-100">
                                <div class="card-body text-center">
                                    <i data-feather="package" class="text-success mb-3" style="width: 48px; height: 48px;"></i>
                                    <h5 class="card-title">产品管理</h5>
                                    <p class="card-text">管理产品信息，包括库存、价格、分类等</p>
                                    <a href="${pageContext.request.contextPath}/products/findAll" class="btn btn-success">
                                        进入管理
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 mb-3">
                            <div class="card h-100">
                                <div class="card-body text-center">
                                    <i data-feather="user-check" class="text-info mb-3" style="width: 48px; height: 48px;"></i>
                                    <h5 class="card-title">客户管理</h5>
                                    <p class="card-text">管理客户信息，包括个人和企业客户</p>
                                    <a href="${pageContext.request.contextPath}/customers/findAll" class="btn btn-info">
                                        进入管理
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <script src="assets/js/bootstrap.bundle.min.js"></script>
    <script src="assets/js/feather.min.js"></script>
    <script src="assets/js/dashboard.js"></script>
    <script>
        // 初始化Feather图标
        feather.replace();
    </script>
</body>
</html>
