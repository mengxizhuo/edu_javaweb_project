<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>系统错误</title>
    <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .error-container {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #f8f9fa;
        }
        .error-card {
            max-width: 500px;
            width: 100%;
            padding: 2rem;
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .error-icon {
            font-size: 4rem;
            color: #dc3545;
            margin-bottom: 1rem;
        }
        .error-title {
            color: #dc3545;
            margin-bottom: 1rem;
        }
        .error-message {
            color: #6c757d;
            margin-bottom: 2rem;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-card">
            <div class="error-icon">
                <i class="bi bi-exclamation-triangle"></i>
                ⚠️
            </div>
            <h2 class="error-title">系统错误</h2>
            <p class="error-message">
                <c:choose>
                    <c:when test="${not empty errorMsg}">
                        ${errorMsg}
                    </c:when>
                    <c:otherwise>
                        抱歉，系统遇到了一个错误，请稍后重试。
                    </c:otherwise>
                </c:choose>
            </p>

            <!-- 调试信息 -->
            <c:if test="${not empty param.debug}">
                <div class="mt-3">
                    <h6>调试信息:</h6>
                    <small class="text-muted">
                        请求URI: ${pageContext.request.requestURI}<br>
                        请求方法: ${pageContext.request.method}<br>
                        时间: <jsp:useBean id="now" class="java.util.Date" /><fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss" />
                    </small>
                </div>
            </c:if>
            <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                <button class="btn btn-primary" onclick="history.back()">返回上页</button>
                <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-outline-secondary">返回首页</a>
            </div>
        </div>
    </div>
    
    <script src="assets/js/bootstrap.bundle.min.js"></script>
</body>
</html>
