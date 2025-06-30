<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <!-- Bootstrap JS  Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>

    <!-- Bootstrap core CSS -->
    <link href="../assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="../assets/css/dashboard.css" rel="stylesheet">

</head>
<body>

<header class="navbar navbar-dark sticky-top bg-dark shadow" style="height: 48px;">
    <div class="d-flex w-100 align-items-center h-100">
        <!-- 左侧品牌 -->
        <a class="navbar-brand col-md-3 col-lg-2 me-0 px-3 flex-shrink-0" href="${pageContext.request.contextPath}/index.jsp">
            欢迎 ${sessionScope.loginEmp.name != null ? sessionScope.loginEmp.name : '游客'} 登录成功
        </a>

        <!-- 移动端菜单按钮 -->
        <button class="navbar-toggler d-md-none" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- 中间搜索框 -->
        <div class="d-flex justify-content-center align-items-center flex-grow-1">
            <div class="position-relative search-container">
                <form action="${pageContext.request.contextPath}/search" method="get" class="d-flex">
                    <input class="form-control form-control-dark" type="text" name="keyword" id="globalSearch"
                           placeholder="搜索员工、产品、客户..." aria-label="Search" autocomplete="off">
                    <button class="btn btn-outline-light ms-2" type="submit">
                        <i data-feather="search"></i>
                    </button>
                </form>
                <!-- 搜索建议下拉框 -->
                <div id="searchSuggestions" class="position-absolute bg-white border rounded shadow-sm w-100"
                     style="top: 100%; z-index: 1000; display: none; max-height: 300px; overflow-y: auto;">
                </div>
            </div>
        </div>

        <!-- 右侧用户菜单 -->
        <div class="nav-item dropdown flex-shrink-0">
            <a class="nav-link dropdown-toggle text-white px-3" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                管理员
            </a>
            <ul class="dropdown-menu dropdown-menu-end">
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/modifyPassword.jsp">修改密码</a></li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">退出登录</a></li>
            </ul>
        </div>
    </div>

</header>

<script>
// 全局搜索功能
document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById('globalSearch');
    const suggestionsDiv = document.getElementById('searchSuggestions');
    let searchTimeout;

    if (searchInput && suggestionsDiv) {
        // 输入时显示搜索建议
        searchInput.addEventListener('input', function() {
            const keyword = this.value.trim();

            // 清除之前的定时器
            clearTimeout(searchTimeout);

            if (keyword.length < 2) {
                hideSuggestions();
                return;
            }

            // 延迟搜索，避免频繁请求
            searchTimeout = setTimeout(() => {
                fetchSuggestions(keyword);
            }, 300);
        });

        // 点击其他地方隐藏建议
        document.addEventListener('click', function(e) {
            if (!searchInput.contains(e.target) && !suggestionsDiv.contains(e.target)) {
                hideSuggestions();
            }
        });

        // 键盘导航
        searchInput.addEventListener('keydown', function(e) {
            const suggestions = suggestionsDiv.querySelectorAll('.search-suggestion-item');
            const activeItem = suggestionsDiv.querySelector('.search-suggestion-item.active');

            if (e.key === 'ArrowDown') {
                e.preventDefault();
                if (activeItem) {
                    activeItem.classList.remove('active');
                    const next = activeItem.nextElementSibling;
                    if (next) {
                        next.classList.add('active');
                    } else {
                        suggestions[0]?.classList.add('active');
                    }
                } else {
                    suggestions[0]?.classList.add('active');
                }
            } else if (e.key === 'ArrowUp') {
                e.preventDefault();
                if (activeItem) {
                    activeItem.classList.remove('active');
                    const prev = activeItem.previousElementSibling;
                    if (prev) {
                        prev.classList.add('active');
                    } else {
                        suggestions[suggestions.length - 1]?.classList.add('active');
                    }
                } else {
                    suggestions[suggestions.length - 1]?.classList.add('active');
                }
            } else if (e.key === 'Enter') {
                if (activeItem) {
                    e.preventDefault();
                    activeItem.click();
                }
            } else if (e.key === 'Escape') {
                hideSuggestions();
            }
        });
    }

    // 获取搜索建议
    function fetchSuggestions(keyword) {
        fetch('${pageContext.request.contextPath}/search/suggest?keyword=' + encodeURIComponent(keyword))
            .then(response => response.json())
            .then(data => {
                if (data.code === 200 && data.data) {
                    showSuggestions(data.data);
                } else {
                    hideSuggestions();
                }
            })
            .catch(error => {
                console.error('搜索建议获取失败:', error);
                hideSuggestions();
            });
    }

    // 显示搜索建议
    function showSuggestions(suggestions) {
        if (suggestions.length === 0) {
            hideSuggestions();
            return;
        }

        let html = '';
        suggestions.forEach(item => {
            html += `
                <div class="search-suggestion-item p-2 border-bottom" style="cursor: pointer;"
                     onclick="goToResult('${item.url}')">
                    <div class="d-flex align-items-center">
                        <i data-feather="${item.typeIcon}" class="me-2" style="width: 16px; height: 16px;"></i>
                        <div class="flex-grow-1">
                            <div class="fw-bold text-dark">${item.title}</div>
                            <small class="text-muted">${item.content}</small>
                        </div>
                        <span class="badge bg-secondary">${item.typeName}</span>
                    </div>
                </div>
            `;
        });

        suggestionsDiv.innerHTML = html;
        suggestionsDiv.style.display = 'block';

        // 重新初始化feather图标
        if (typeof feather !== 'undefined') {
            feather.replace();
        }

        // 添加hover效果
        const items = suggestionsDiv.querySelectorAll('.search-suggestion-item');
        items.forEach(item => {
            item.addEventListener('mouseenter', function() {
                items.forEach(i => i.classList.remove('active'));
                this.classList.add('active');
            });
        });
    }

    // 隐藏搜索建议
    function hideSuggestions() {
        suggestionsDiv.style.display = 'none';
        suggestionsDiv.innerHTML = '';
    }

    // 跳转到搜索结果
    window.goToResult = function(url) {
        window.location.href = '${pageContext.request.contextPath}' + url;
    };
});
</script>

<style>
.search-suggestion-item:hover,
.search-suggestion-item.active {
    background-color: #f8f9fa;
}

/* 搜索容器样式 */
.search-container {
    width: 350px; /* 控制搜索框容器宽度 */
    max-width: 100%; /* 在小屏幕上响应式 */
}

/* 搜索输入框样式 */
.form-control-dark {
    background-color: rgba(255, 255, 255, 0.1);
    border: 1px solid rgba(255, 255, 255, 0.2);
    color: white;
    height: 32px; /* 适合48px navbar的高度 */
    font-size: 0.9rem;
    margin: 0; /* 确保没有外边距 */
    line-height: 1.2;
}

.form-control-dark:focus {
    background-color: rgba(255, 255, 255, 0.15);
    border-color: rgba(255, 255, 255, 0.4);
    color: white;
    box-shadow: 0 0 0 0.2rem rgba(255, 255, 255, 0.25);
}

.form-control-dark::placeholder {
    color: rgba(255, 255, 255, 0.7);
    font-size: 0.85rem;
}

/* 确保搜索按钮与输入框高度一致 */
.btn-outline-light {
    height: 32px; /* 与输入框高度一致 */
    display: flex;
    align-items: center;
    justify-content: center;
    border-color: rgba(255, 255, 255, 0.2);
    padding: 0 10px;
    margin: 0; /* 确保没有外边距 */
    line-height: 1;
}

.btn-outline-light:hover {
    background-color: rgba(255, 255, 255, 0.1);
    border-color: rgba(255, 255, 255, 0.4);
}

/* 导航栏布局优化 */
.navbar {
    padding-top: 0 !important;
    padding-bottom: 0 !important;
}

.navbar-brand {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    display: flex;
    align-items: center;
    height: 100%;
}

/* 搜索框容器垂直居中 */
.search-container {
    display: flex;
    align-items: center;
    justify-content: center;
}

.search-container form {
    margin: 0;
    align-items: center;
    width: 100%;
}

/* 确保navbar内容垂直居中 */
.navbar .d-flex {
    min-height: 48px;
}

.navbar-brand {
    line-height: 1.2;
    padding-top: 0;
    padding-bottom: 0;
}

/* 响应式设计 */
@media (max-width: 992px) {
    .search-container {
        width: 280px;
    }

    .navbar-brand {
        font-size: 0.9rem;
    }
}

@media (max-width: 768px) {
    .search-container {
        width: 220px;
    }

    .form-control-dark::placeholder {
        content: "搜索...";
    }
}

@media (max-width: 576px) {
    .search-container {
        width: 180px;
    }

    .form-control-dark {
        font-size: 0.8rem;
    }

    .form-control-dark::placeholder {
        font-size: 0.8rem;
    }

    .navbar-brand {
        font-size: 0.8rem;
        max-width: 120px;
    }
}
</style>

</body>
</html>
