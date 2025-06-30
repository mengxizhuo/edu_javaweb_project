<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>修改密码</title>
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/dashboard.css" rel="stylesheet">
    <style>
        .form-fieldset {
            border: 2px solid #0B5ED7;
            border-radius: 8px;
            padding: 40px;
            margin: 30px 0;
            position: relative;
        }
        .fieldset-legend {
            color: #0B5ED7;
            font-size: 1.3em;
            font-weight: bold;
            padding: 0 15px;
            background: white;
            position: absolute;
            top: -15px;
            left: 20px;
        }
    </style>
</head>
<body>

<jsp:include page="commons/topbar.jsp" />
<div class="container-fluid">
    <div class="row">
        <jsp:include page="commons/sidebar.jsp" />

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <br/>

            <!-- 显示成功或错误消息 -->
            <c:if test="${not empty sessionScope.successMsg}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${sessionScope.successMsg}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <c:remove var="successMsg" scope="session"/>
            </c:if>

            <c:if test="${not empty sessionScope.errorMsg}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${sessionScope.errorMsg}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <c:remove var="errorMsg" scope="session"/>
            </c:if>

            <form class="row g-3 col-md-6" action="${pageContext.request.contextPath}/auth/changePassword" method="post">
                <fieldset class="form-fieldset">
                    <legend class="fieldset-legend">修改密码</legend>

                    <!-- 隐藏id -->
                    <input type="hidden" name="id" value="${sessionScope.loginEmp.id}"/>

                    <div class="col-12">
                        <label class="form-label">原密码</label>
                        <input type="password" class="form-control" name="oldPassword" required />
                    </div>

                    <div class="col-12">
                        <label class="form-label">新密码</label>
                        <input type="password" class="form-control" name="newPassword" required />
                    </div>

                    <div class="col-12">
                        <label class="form-label">确认新密码</label>
                        <input type="password" class="form-control" name="confirmPassword" required />
                    </div>

                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-primary">确认修改</button>
                    </div>
                </fieldset>
            </form>
        </main>
    </div>
</div>

<script src="assets/js/feather.min.js"></script>
<script src="assets/js/dashboard.js"></script>
<script>
// 表单验证
document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    const oldPasswordInput = document.querySelector('input[name="oldPassword"]');
    const newPasswordInput = document.querySelector('input[name="newPassword"]');
    const confirmPasswordInput = document.querySelector('input[name="confirmPassword"]');

    form.addEventListener('submit', function(e) {
        const oldPassword = oldPasswordInput.value.trim();
        const newPassword = newPasswordInput.value.trim();
        const confirmPassword = confirmPasswordInput.value.trim();

        // 检查是否为空
        if (!oldPassword || !newPassword || !confirmPassword) {
            alert('所有密码字段都不能为空');
            e.preventDefault();
            return;
        }

        // 检查新密码长度
        if (newPassword.length < 6) {
            alert('新密码长度不能少于6位');
            e.preventDefault();
            return;
        }

        // 检查两次密码是否一致
        if (newPassword !== confirmPassword) {
            alert('两次输入的新密码不一致');
            e.preventDefault();
            return;
        }

        // 检查新密码是否与原密码相同
        if (oldPassword === newPassword) {
            alert('新密码不能与原密码相同');
            e.preventDefault();
            return;
        }
    });

    // 实时验证确认密码
    confirmPasswordInput.addEventListener('input', function() {
        const newPassword = newPasswordInput.value;
        const confirmPassword = this.value;

        if (confirmPassword && newPassword !== confirmPassword) {
            this.setCustomValidity('两次输入的密码不一致');
        } else {
            this.setCustomValidity('');
        }
    });
});
</script>
</body>
</html>
