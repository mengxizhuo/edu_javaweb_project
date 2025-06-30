<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.88.1">
    <title>登录页面</title>

    <!-- <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/sign-in/"> -->

    

    <!-- Bootstrap core CSS -->
<link href="assets/css/bootstrap.min.css" rel="stylesheet">

    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>

    
    <!-- Custom styles for this template -->
    <link href="assets/css/signin.css" rel="stylesheet">
  </head>
  <body class="text-center">
<main class="form-signin">
  <form action="login" method="post">
    <img class="mb-4" src="assets/images/bootstrap-logo.svg" alt="" width="72" height="57">
    <h1 class="h3 mb-3 fw-normal">登 录</h1>
    <span style="color: red">${not empty requestScope.msg ? requestScope.msg : ""}</span>

    <div class="form-floating">
      <input type="text" class="form-control" id="floatingInput" placeholder="username" name="username">
      <label for="floatingInput">用户名</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" id="floatingPassword" placeholder="Password" name="password">
      <label for="floatingPassword">密码</label>
    </div>

    <div class="checkbox mb-3">
      <label>
        <input type="checkbox" id="rememberMe" name="rememberMe" value="true"> 请记住我
      </label>
      <div class="text-end">
        <small><a href="#" id="clearRemembered" class="text-muted">清除记住的信息</a></small>
      </div>
    </div>
    <button class="w-100 btn btn-lg btn-primary" type="submit">登 录</button>
    <p class="mt-5 mb-3 text-muted">&copy; 2017–2021</p>
	<a class="btn btn-sm" th:href="#">中文</a>
	<a class="btn btn-sm" th:href="#">English</a>
  </form>
</main>

<script>
// 页面加载时检查是否有保存的用户名和密码
document.addEventListener('DOMContentLoaded', function() {
    const usernameInput = document.getElementById('floatingInput');
    const passwordInput = document.getElementById('floatingPassword');
    const rememberMeCheckbox = document.getElementById('rememberMe');

    // 从localStorage读取保存的用户名和密码
    const savedUsername = localStorage.getItem('rememberedUsername');
    const savedPassword = localStorage.getItem('rememberedPassword');

    // 如果有保存的用户名和密码，自动填充
    if (savedUsername && savedPassword) {
        usernameInput.value = savedUsername;
        passwordInput.value = savedPassword;
        rememberMeCheckbox.checked = true;
    }

    // 表单提交时处理记住密码
    const form = document.querySelector('form');
    form.addEventListener('submit', function(e) {
        if (rememberMeCheckbox.checked) {
            // 保存用户名和密码到localStorage
            localStorage.setItem('rememberedUsername', usernameInput.value);
            localStorage.setItem('rememberedPassword', passwordInput.value);
        } else {
            // 清除保存的用户名和密码
            localStorage.removeItem('rememberedUsername');
            localStorage.removeItem('rememberedPassword');
        }
    });

    // 记住我复选框变化时的处理
    rememberMeCheckbox.addEventListener('change', function() {
        if (!this.checked) {
            // 如果取消勾选，立即清除保存的信息
            localStorage.removeItem('rememberedUsername');
            localStorage.removeItem('rememberedPassword');
        }
    });

    // 清除记住的信息
    const clearRememberedLink = document.getElementById('clearRemembered');
    clearRememberedLink.addEventListener('click', function(e) {
        e.preventDefault();

        if (confirm('确定要清除所有记住的登录信息吗？')) {
            // 清除localStorage中的用户名和密码
            localStorage.removeItem('rememberedUsername');
            localStorage.removeItem('rememberedPassword');

            // 清除表单中的值
            usernameInput.value = '';
            passwordInput.value = '';
            rememberMeCheckbox.checked = false;

            alert('记住的信息已清除');
        }
    });
});
</script>

  </body>
</html>
