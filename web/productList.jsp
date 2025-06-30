<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>产品管理</title>
    <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/dashboard.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="commons/topbar.jsp"/>
    
    <div class="container-fluid">
        <div class="row">
            <jsp:include page="commons/sidebar.jsp"/>
            
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">产品管理</h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                        <div class="btn-group me-2">
                            <a href="${pageContext.request.contextPath}/products/add" class="btn btn-sm btn-outline-secondary">
                                <i data-feather="plus"></i> 新增产品
                            </a>
                        </div>
                    </div>
                </div>
                
                <div class="table-responsive">
                    <table class="table table-striped table-sm">
                        <thead>
                            <tr>
                                <th>产品ID</th>
                                <th>产品名称</th>
                                <th>分类</th>
                                <th>品牌</th>
                                <th>价格</th>
                                <th>库存</th>
                                <th>状态</th>
                                <th>创建时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty list}">
                                    <tr>
                                        <td colspan="9" class="text-center text-muted">暂无产品数据</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="product" items="${list}" varStatus="status">
                                    <tr>
                                        <td>${product.id}</td>
                                        <td>
                                            <strong>${product.name}</strong>
                                            <c:if test="${not empty product.description}">
                                                <br><small class="text-muted">${product.description}</small>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty product.category}">
                                                    <span class="badge bg-secondary">${product.category}</span>
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty product.brand}">
                                                    ${product.brand}
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty product.price}">
                                                    <span class="text-success">¥<fmt:formatNumber value="${product.price}" pattern="#,##0.00" /></span>
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${product.stockQuantity > 10}">
                                                    <span class="badge bg-success">${product.stockQuantity}</span>
                                                </c:when>
                                                <c:when test="${product.stockQuantity > 0}">
                                                    <span class="badge bg-warning">${product.stockQuantity}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-danger">${product.stockQuantity}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${product.status == 1}">
                                                    <span class="badge bg-success">上架</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary">下架</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty product.createTime}">
                                                    <fmt:formatDate value="${product.createTime}" pattern="yyyy-MM-dd" />
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <div class="btn-group" role="group">
                                                <a href="${pageContext.request.contextPath}/products/findById?id=${product.id}" 
                                                   class="btn btn-sm btn-outline-primary" title="修改">
                                                    <i data-feather="edit"></i>
                                                </a>
                                                <button type="button" class="btn btn-sm btn-outline-danger" 
                                                        onclick="confirmDelete(${product.id}, '${product.name}')" title="删除">
                                                    <i data-feather="trash-2"></i>
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    </div>

    <script src="assets/js/bootstrap.bundle.min.js"></script>
    <script src="assets/js/feather.min.js"></script>
    <script src="assets/js/dashboard.js"></script>
    <script>
        // 确认删除函数
        function confirmDelete(productId, productName) {
            if (confirm('确定要删除产品 "' + productName + '" 吗？此操作不可恢复！')) {
                // 发送删除请求
                fetch('${pageContext.request.contextPath}/products/delete', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: 'id=' + productId
                })
                .then(response => response.json())
                .then(data => {
                    if (data.code === 200) {
                        alert('删除成功！');
                        location.reload(); // 刷新页面
                    } else {
                        alert('删除失败：' + data.message);
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('删除失败，请稍后重试');
                });
            }
        }
        
        // 初始化Feather图标
        feather.replace();
    </script>
</body>
</html>
