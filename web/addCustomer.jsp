<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>新增客户</title>
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
                    <h1 class="h2">新增客户</h1>
                </div>
                
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="card">
                            <div class="card-body">
                                <form action="${pageContext.request.contextPath}/customers/add" method="post">
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="customer_type" class="form-label">客户类型 <span class="text-danger">*</span></label>
                                            <select class="form-select" id="customer_type" name="customer_type" onchange="toggleCustomerType()" required>
                                                <option value="1" selected>个人客户</option>
                                                <option value="2">企业客户</option>
                                            </select>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label for="status" class="form-label">状态</label>
                                            <select class="form-select" id="status" name="status">
                                                <option value="1" selected>活跃</option>
                                                <option value="0">停用</option>
                                            </select>
                                        </div>
                                    </div>
                                    
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="name" class="form-label">
                                                <span id="nameLabel">客户姓名</span> <span class="text-danger">*</span>
                                            </label>
                                            <input type="text" class="form-control" id="name" name="name" required>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label for="email" class="form-label">邮箱</label>
                                            <input type="email" class="form-control" id="email" name="email" placeholder="example@email.com">
                                        </div>
                                    </div>
                                    
                                    <!-- 企业客户专用字段 -->
                                    <div id="companyFields" style="display: none;">
                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label for="company" class="form-label">公司名称</label>
                                                <input type="text" class="form-control" id="company" name="company">
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label for="contact_person" class="form-label">联系人</label>
                                                <input type="text" class="form-control" id="contact_person" name="contact_person">
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="phone" class="form-label">电话号码</label>
                                            <input type="tel" class="form-control" id="phone" name="phone" placeholder="请输入电话号码">
                                        </div>
                                    </div>
                                    
                                    <div class="mb-3">
                                        <label for="address" class="form-label">地址</label>
                                        <textarea class="form-control" id="address" name="address" rows="3" placeholder="请输入详细地址..."></textarea>
                                    </div>
                                    
                                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                        <a href="${pageContext.request.contextPath}/customers/findAll" class="btn btn-secondary me-md-2">返回列表</a>
                                        <button type="submit" class="btn btn-primary">新增客户</button>
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
        
        // 切换客户类型显示
        function toggleCustomerType() {
            const customerType = document.getElementById('customer_type').value;
            const companyFields = document.getElementById('companyFields');
            const nameLabel = document.getElementById('nameLabel');
            
            if (customerType === '2') {
                companyFields.style.display = 'block';
                nameLabel.textContent = '负责人姓名';
            } else {
                companyFields.style.display = 'none';
                nameLabel.textContent = '客户姓名';
            }
        }
        
        // 表单验证
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.querySelector('form');
            form.addEventListener('submit', function(e) {
                const name = document.getElementById('name').value.trim();
                const email = document.getElementById('email').value.trim();
                
                if (!name) {
                    alert('请输入客户姓名');
                    e.preventDefault();
                    return;
                }
                
                if (email && !isValidEmail(email)) {
                    alert('请输入有效的邮箱地址');
                    e.preventDefault();
                    return;
                }
            });
        });
        
        // 邮箱格式验证
        function isValidEmail(email) {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return emailRegex.test(email);
        }
    </script>
</body>
</html>
