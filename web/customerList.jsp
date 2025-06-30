<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>客户管理</title>
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
                    <h1 class="h2">客户管理</h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                        <div class="btn-group me-2">
                            <a href="${pageContext.request.contextPath}/customers/add" class="btn btn-sm btn-outline-secondary">
                                <i data-feather="user-plus"></i> 新增客户
                            </a>
                        </div>
                    </div>
                </div>
                
                <div class="table-responsive">
                    <table class="table table-striped table-sm">
                        <thead>
                            <tr>
                                <th>客户ID</th>
                                <th>客户信息</th>
                                <th>联系方式</th>
                                <th>地址</th>
                                <th>客户类型</th>
                                <th>状态</th>
                                <th>创建时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty list}">
                                    <tr>
                                        <td colspan="8" class="text-center text-muted">暂无客户数据</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="customer" items="${list}" varStatus="status">
                                    <tr>
                                        <td>${customer.id}</td>
                                        <td>
                                            <div>
                                                <strong>${customer.name}</strong>
                                                <c:if test="${customer.customerType == 2 && not empty customer.company}">
                                                    <br><small class="text-muted">${customer.company}</small>
                                                </c:if>
                                                <c:if test="${customer.customerType == 2 && not empty customer.contactPerson}">
                                                    <br><small class="text-info">联系人：${customer.contactPerson}</small>
                                                </c:if>
                                            </div>
                                        </td>
                                        <td>
                                            <div>
                                                <c:if test="${not empty customer.email}">
                                                    <div><i data-feather="mail" class="feather-sm"></i> ${customer.email}</div>
                                                </c:if>
                                                <c:if test="${not empty customer.phone}">
                                                    <div><i data-feather="phone" class="feather-sm"></i> ${customer.phone}</div>
                                                </c:if>
                                            </div>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty customer.address}">
                                                    <small>${customer.address}</small>
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${customer.customerType == 1}">
                                                    <span class="badge bg-info">个人</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-primary">企业</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${customer.status == 1}">
                                                    <span class="badge bg-success">活跃</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary">停用</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty customer.createTime}">
                                                    <fmt:formatDate value="${customer.createTime}" pattern="yyyy-MM-dd" />
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <div class="btn-group" role="group">
                                                <a href="${pageContext.request.contextPath}/customers/findById?id=${customer.id}" 
                                                   class="btn btn-sm btn-outline-primary" title="修改">
                                                    <i data-feather="edit"></i>
                                                </a>
                                                <button type="button" class="btn btn-sm btn-outline-danger" 
                                                        onclick="confirmDelete(${customer.id}, '${customer.name}')" title="删除">
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
        function confirmDelete(customerId, customerName) {
            if (confirm('确定要删除客户 "' + customerName + '" 吗？此操作不可恢复！')) {
                // 发送删除请求
                fetch('${pageContext.request.contextPath}/customers/delete', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: 'id=' + customerId
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
    
    <style>
        .feather-sm {
            width: 14px;
            height: 14px;
        }
    </style>
</body>
</html>
