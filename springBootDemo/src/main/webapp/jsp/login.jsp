<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录页面</title>
    <link rel="stylesheet" type="text/css" href="/css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="/css/demo.css" />
    <link rel="stylesheet" type="text/css" href="/css/component.css" />
    <!--[if IE]>
    <script src="/js/html5.js"></script>
    <![endif]-->
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
</head>
<body>
    <div class="container demo-1">
        <div class="content">
            <div id="large-header" class="large-header">
                <canvas id="demo-canvas"></canvas>
                <div class="logo_box">
                    <h3>SpringBoot-Demo-Login</h3>
                    <form action="manager" id="form" method="post">
                        <div class="input_outer">
                            <span class="u_user"></span>
                            <input name="name" class="text" style="color: #FFFFFF !important" type="text" placeholder="请输入账户">
                        </div>
                        <div class="input_outer">
                            <span class="us_uer"></span>
                            <input name="password" class="text" style="color: #FFFFFF !important; position:absolute; z-index:100;"value="" type="password" placeholder="请输入密码">
                        </div>
                        <div class="mb2"><a class="act-but submit" href="javascript:" onclick="submitForm()" style="color: #FFFFFF">登录</a></div>
                    </form>
                </div>
            </div>
        </div>
    </div><!-- /container -->

    <%--提交表单--%>
    <script type="text/javascript">
        function submitForm() {
            $.ajax({
                url: "./login",
                cache: false,
                type: "POST",
                data:$('#form').serialize(),// 你的formid
                async: true,
                success: function(data) {
                    if (data.status === 0) {
                        window.location.href = "./login";  //跳转到index
                    } else {
                        alert(data.desc);
                    }
                }
            });
        }
    </script>

    <%--页面效果--%>
    <script src="/js/TweenLite.min.js"></script>
    <script src="/js/EasePack.min.js"></script>
    <script src="/js/rAF.js"></script>
    <script src="/js/demo-1.js"></script>
</body>


</html>
