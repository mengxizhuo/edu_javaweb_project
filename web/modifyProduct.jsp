<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>修改产品</title>
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
                    <h1 class="h2">修改产品信息</h1>
                </div>
                
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="card">
                            <div class="card-body">
                                <form action="${pageContext.request.contextPath}/products/update" method="post">
                                    <input type="hidden" name="id" value="${product.id}">
                                    
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="name" class="form-label">产品名称 <span class="text-danger">*</span></label>
                                            <input type="text" class="form-control" id="name" name="name" value="${product.name}" required>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label for="price" class="form-label">产品价格 <span class="text-danger">*</span></label>
                                            <div class="input-group">
                                                <span class="input-group-text">¥</span>
                                                <input type="number" class="form-control" id="price" name="price" 
                                                       value="${product.price}" step="0.01" min="0" required>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="category" class="form-label">产品分类</label>
                                            <select class="form-select" id="category" name="category">
                                                <option value="">请选择分类</option>
                                                <option value="手机" <c:if test="${product.category == '手机'}">selected</c:if>>手机</option>
                                                <option value="笔记本" <c:if test="${product.category == '笔记本'}">selected</c:if>>笔记本</option>
                                                <option value="平板" <c:if test="${product.category == '平板'}">selected</c:if>>平板</option>
                                                <option value="耳机" <c:if test="${product.category == '耳机'}">selected</c:if>>耳机</option>
                                                <option value="其他" <c:if test="${product.category == '其他'}">selected</c:if>>其他</option>
                                            </select>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label for="brand" class="form-label">品牌</label>
                                            <input type="text" class="form-control" id="brand" name="brand" 
                                                   value="${product.brand}" placeholder="如：Apple、华为、小米等">
                                        </div>
                                    </div>
                                    
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="stock_quantity" class="form-label">库存数量</label>
                                            <input type="number" class="form-control" id="stock_quantity" name="stock_quantity" 
                                                   value="${product.stockQuantity}" min="0">
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label for="status" class="form-label">状态</label>
                                            <select class="form-select" id="status" name="status">
                                                <option value="1" <c:if test="${product.status == 1}">selected</c:if>>上架</option>
                                                <option value="0" <c:if test="${product.status == 0}">selected</c:if>>下架</option>
                                            </select>
                                        </div>
                                    </div>
                                    
                                    <div class="mb-3">
                                        <label for="description" class="form-label">产品描述</label>
                                        <textarea class="form-control" id="description" name="description" rows="4" 
                                                  placeholder="请输入产品详细描述...">${product.description}</textarea>
                                    </div>
                                    
                                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                        <a href="${pageContext.request.contextPath}/products/findAll" class="btn btn-secondary me-md-2">返回列表</a>
                                        <button type="submit" class="btn btn-primary">保存修改</button>
                                    </div>
                                </form>
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
        
        // 表单验证
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.querySelector('form');
            form.addEventListener('submit', function(e) {
                const name = document.getElementById('name').value.trim();
                const price = document.getElementById('price').value;
                
                if (!name) {
                    alert('请输入产品名称');
                    e.preventDefault();
                    return;
                }
                
                if (!price || parseFloat(price) < 0) {
                    alert('请输入有效的产品价格');
                    e.preventDefault();
                    return;
                }
            });
        });
    </script>
</body>
</html>
