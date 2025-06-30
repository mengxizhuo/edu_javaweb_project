<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.88.1">
    <title>员工的列表页面</title>
    <!-- Bootstrap core CSS -->
	<link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/dashboard.css" rel="stylesheet">
  </head>
  <body>

  <!--TopBar-->
  <jsp:include page="commons/topbar.jsp"></jsp:include>


<div class="container-fluid">
  <div class="row">
    <!-- Sidebar -->
    <jsp:include page="commons/siderbar.jsp"></jsp:include>

    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
      <h2>查询所有员工信息</h2>
        <a href="${pageContext.request.contextPath}/emps/add">
            <button class="btn btn-primary">新增</button></a>
        <br/><br/>
      <div class="table-responsive">
        <table class="table table-striped table-sm">
          <thead>
            <tr>
              <th scope="col">员工编号</th>
              <th scope="col">员工姓名</th>
              <th scope="col">员工账号</th>
              <th scope="col">员工性别</th>
              <th scope="col">员工薪资</th>
              <th scope="col">入职日期</th>
              <th scope="col">部门编号</th>
              <th scope="col">操作</th>
            </tr>
          </thead>

          <tbody>
          <c:choose>
              <c:when test="${empty list}">
                  <tr>
                      <td colspan="8" class="text-center text-muted">暂无员工数据</td>
                  </tr>
              </c:when>
              <c:otherwise>
                  <c:forEach var="emp" items="${list}" varStatus="status">
                  <tr>
                      <td>${emp.id}</td>
                      <td>${emp.name}</td>
                      <td>${emp.username}</td>
                      <td>
                          <c:choose>
                              <c:when test="${emp.gender == '1'}">
                                  <span class="badge bg-primary">男</span>
                              </c:when>
                              <c:when test="${emp.gender == '0'}">
                                  <span class="badge bg-info">女</span>
                              </c:when>
                              <c:otherwise>
                                  <span class="badge bg-secondary">未知</span>
                              </c:otherwise>
                          </c:choose>
                      </td>
                      <td>
                          <c:choose>
                              <c:when test="${not empty emp.salary}">
                                  <fmt:formatNumber value="${emp.salary}" pattern="#,##0" />
                              </c:when>
                              <c:otherwise>-</c:otherwise>
                          </c:choose>
                      </td>
                      <td>
                          <c:choose>
                              <c:when test="${not empty emp.joinDate}">
                                  <fmt:formatDate value="${emp.joinDate}" pattern="yyyy-MM-dd" />
                              </c:when>
                              <c:otherwise>-</c:otherwise>
                          </c:choose>
                      </td>
                      <td>
                          <c:choose>
                              <c:when test="${not empty emp.deptId}">
                                  ${emp.deptId}
                              </c:when>
                              <c:otherwise>-</c:otherwise>
                          </c:choose>
                      </td>
                      <td>
                          <div class="btn-group" role="group">
                              <a href="${pageContext.request.contextPath}/emps/findById?id=${emp.id}"
                                 class="btn btn-sm btn-outline-primary">
                                  <i class="bi bi-pencil"></i> 修改
                              </a>
                              <button type="button" class="btn btn-sm btn-outline-danger"
                                      onclick="confirmDelete(${emp.id}, '${emp.name}')">
                                  <i class="bi bi-trash"></i> 删除
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
          function confirmDelete(empId, empName) {
              if (confirm('确定要删除员工 "' + empName + '" 吗？此操作不可恢复！')) {
                  // 发送删除请求
                  fetch('${pageContext.request.contextPath}/emps/delete', {
                      method: 'POST',
                      headers: {
                          'Content-Type': 'application/x-www-form-urlencoded',
                      },
                      body: 'id=' + empId
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
