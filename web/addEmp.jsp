<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.88.1">
    <title>新增员工</title>
    <!-- Bootstrap core CSS -->
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

<!-- 导入顶部导航栏 -->
<jsp:include page="commons/topbar.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row">
        <!-- 导入侧边栏 -->
        <jsp:include page="commons/sidebar.jsp"></jsp:include>

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <br/>
            <form class="row g-3 col-md-6" action="${pageContext.request.contextPath}/emps/addEmp" method="post">
                <fieldset class="form-fieldset">
                    <legend class="fieldset-legend">新增员工信息</legend>

                    <div class="col-12">
                        <label for="inputUsername" class="form-label">员工姓名</label>
                        <input type="text" class="form-control" id="inputName" name="name">
                    </div><br/>

                    <div class="col-12">
                        <label for="inputUsername" class="form-label">员工账号</label>
                        <input type="text" class="form-control" id="inputUsername" name="username">
                    </div><br/>

                    <div class="col-12">
                        <label for="inputPassword4" class="form-label">设置密码</label>
                        <input type="password" class="form-control" id="inputPassword4" name="password">
                    </div><br/>

                    <div class="col-12">
                        <fieldset class="row mb-3">
                            <legend class="col-form-label col-sm-2 pt-0">员工性别</legend>
                            <div class="col-sm-10">
                                <input class="form-check-input" type="radio" name="gender" id="gridRadios1" value="1" checked>
                                <label class="form-check-label" for="gridRadios1">男</label>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input class="form-check-input" type="radio" name="gender" id="gridRadios2" value="0">
                                <label class="form-check-label" for="gridRadios2">女</label>
                            </div>
                        </fieldset>
                    </div><br/>

                    <div class="col-12">
                        <label for="inputSalary" class="form-label">员工薪资</label>
                        <input type="text" class="form-control" id="inputSalary" name="salary">
                    </div><br/>

                    <div class="col-12">
                        <label for="inputJoinDate" class="form-label">入职日期</label>
                        <input type="date" class="form-control" id="inputJoinDate" name="join_date">
                    </div><br/>

                    <div class="col-12">
                        <label for="inputDeptId" class="form-label">部门编号</label>
                        <input type="text" class="form-control" id="inputDeptId" name="dept_id">
                    </div><br/>

                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-primary">新增</button>
                    </div>
                </fieldset>
            </form>
        </main>
    </div>
</div>

<script src="assets/js/feather.min.js"></script>
<script src="assets/js/dashboard.js"></script>
</body>
</html>