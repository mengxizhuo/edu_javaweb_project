<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>搜索结果 - ${keyword}</title>
    <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/dashboard.css" rel="stylesheet">
    <style>
        .search-result-item {
            border-left: 4px solid #0d6efd;
            transition: all 0.2s ease;
        }
        .search-result-item:hover {
            border-left-color: #0a58ca;
            background-color: #f8f9fa;
        }
        .search-type-badge {
            font-size: 0.75rem;
        }
        .search-highlight {
            background-color: #fff3cd;
            padding: 1px 3px;
            border-radius: 2px;
        }
    </style>
</head>
<body>
    <jsp:include page="commons/topbar.jsp"/>
    
    <div class="container-fluid">
        <div class="row">
            <jsp:include page="commons/sidebar.jsp"/>
            
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">搜索结果</h1>
                </div>
                
                <!-- 搜索信息 -->
                <div class="mb-4">
                    <div class="d-flex align-items-center mb-2">
                        <i data-feather="search" class="me-2"></i>
                        <span class="text-muted">搜索关键词：</span>
                        <strong class="ms-1">"${keyword}"</strong>
                    </div>
                    <p class="text-muted mb-0">
                        找到 <strong>${resultCount}</strong> 个相关结果
                    </p>
                </div>
                
                <!-- 搜索结果 -->
                <div class="search-results">
                    <c:choose>
                        <c:when test="${empty results}">
                            <div class="text-center py-5">
                                <i data-feather="search" style="width: 64px; height: 64px;" class="text-muted mb-3"></i>
                                <h4 class="text-muted">未找到相关结果</h4>
                                <p class="text-muted">请尝试使用其他关键词搜索</p>
                                <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">
                                    返回首页
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="result" items="${results}" varStatus="status">
                                <div class="card search-result-item mb-3">
                                    <div class="card-body">
                                        <div class="d-flex align-items-start">
                                            <div class="me-3">
                                                <i data-feather="${result.typeIcon}" class="text-primary" 
                                                   style="width: 24px; height: 24px;"></i>
                                            </div>
                                            <div class="flex-grow-1">
                                                <div class="d-flex align-items-center mb-2">
                                                    <h5 class="card-title mb-0 me-2">
                                                        <a href="${pageContext.request.contextPath}${result.url}" 
                                                           class="text-decoration-none">
                                                            ${result.title}
                                                        </a>
                                                    </h5>
                                                    <span class="badge bg-secondary search-type-badge">
                                                        ${result.typeName}
                                                    </span>
                                                </div>
                                                <p class="card-text text-muted mb-2">
                                                    ${result.content}
                                                </p>
                                                <small class="text-muted">
                                                    <i data-feather="link" style="width: 14px; height: 14px;"></i>
                                                    ${pageContext.request.contextPath}${result.url}
                                                </small>
                                            </div>
                                            <div class="ms-3">
                                                <a href="${pageContext.request.contextPath}${result.url}" 
                                                   class="btn btn-outline-primary btn-sm">
                                                    查看详情
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
                
                <!-- 搜索建议 -->
                <c:if test="${not empty results}">
                    <div class="mt-5 pt-4 border-top">
                        <h5 class="mb-3">搜索建议</h5>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="card">
                                    <div class="card-body text-center">
                                        <i data-feather="users" class="text-primary mb-2" style="width: 32px; height: 32px;"></i>
                                        <h6 class="card-title">员工管理</h6>
                                        <p class="card-text small text-muted">查看所有员工信息</p>
                                        <a href="${pageContext.request.contextPath}/emps/findAll" class="btn btn-sm btn-outline-primary">
                                            进入
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="card">
                                    <div class="card-body text-center">
                                        <i data-feather="package" class="text-success mb-2" style="width: 32px; height: 32px;"></i>
                                        <h6 class="card-title">产品管理</h6>
                                        <p class="card-text small text-muted">查看所有产品信息</p>
                                        <a href="${pageContext.request.contextPath}/products/findAll" class="btn btn-sm btn-outline-success">
                                            进入
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="card">
                                    <div class="card-body text-center">
                                        <i data-feather="user-check" class="text-info mb-2" style="width: 32px; height: 32px;"></i>
                                        <h6 class="card-title">客户管理</h6>
                                        <p class="card-text small text-muted">查看所有客户信息</p>
                                        <a href="${pageContext.request.contextPath}/customers/findAll" class="btn btn-sm btn-outline-info">
                                            进入
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
            </main>
        </div>
    </div>

    <script src="assets/js/bootstrap.bundle.min.js"></script>
    <script src="assets/js/feather.min.js"></script>
    <script src="assets/js/dashboard.js"></script>
    <script>
        // 初始化Feather图标
        feather.replace();
        
        // 高亮搜索关键词
        document.addEventListener('DOMContentLoaded', function() {
            const keyword = '${keyword}';
            if (keyword && keyword.trim() !== '') {
                highlightKeyword(keyword);
            }
        });
        
        function highlightKeyword(keyword) {
            const results = document.querySelectorAll('.search-result-item .card-title, .search-result-item .card-text');
            const regex = new RegExp(`(${keyword})`, 'gi');
            
            results.forEach(element => {
                if (element.textContent.toLowerCase().includes(keyword.toLowerCase())) {
                    element.innerHTML = element.innerHTML.replace(regex, '<span class="search-highlight">$1</span>');
                }
            });
        }
    </script>
</body>
</html>
