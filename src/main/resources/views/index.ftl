<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>Crm | 登录-注册</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/css/uniform.default.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/css/login.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/css/components.css" id="style_components" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${ctx}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/common.js"></script>
    <script type="text/javascript">
        var ctx = "${ctx}";
    </script>
    <script type="text/javascript" src="${ctx}/static/js/index.js"></script>

    <!-- BEGIN BODY -->
<body class="login">
<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
<div class="menu-toggler sidebar-toggler">
</div>
<!-- END SIDEBAR TOGGLER BUTTON -->
<!-- BEGIN LOGO -->
<div class="logo">
    <a href="index.html">
        <img src="resources/admin/layout2/img/logo-big.png" alt=""/>
    </a>
</div>
<!-- END LOGO -->
<!-- BEGIN LOGIN -->
<div class="content">
    <!-- BEGIN LOGIN FORM -->
    <h3 class="form-title">Crm登录</h3>
    <div class="alert alert-danger display-hide">
        <button class="close" data-close="alert"></button>
        <span>
			用户名</span>
    </div>
    <div class="form-group">
        <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
        <label class="control-label visible-ie8 visible-ie9">用户名</label>
        <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off"
               placeholder="用户名" name="userName"/>
    </div>
    <div class="form-group">
        <label class="control-label visible-ie8 visible-ie9">密码</label>
        <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off"
               placeholder="密码" name="password"/>
    </div>
    <div class="form-actions">
        <button onclick="login()" class="btn btn-success uppercase btn-xs btn-block">登录</button>
    </div>

    <div class="create-account">
        <p>
            <a href="javascript:;" id="register-btn" class="uppercase">注册</a>
        </p>
    </div>
    <!-- END LOGIN FORM -->

    <!-- BEGIN REGISTRATION FORM -->
    <form class="register-form" action="index.html" method="post">
        <h3>注册</h3>
        <p class="hint">
            输入个人信息
        </p>

        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">用户名</label>
            <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="用户名"
                   name="username"/>
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">密码</label>
            <input class="form-control placeholder-no-fix" type="password" autocomplete="off" id="register_password"
                   placeholder="密码" name="password"/>
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">确认密码</label>
            <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="确认密码"
                   name="rpassword"/>
        </div>

        <div class="form-actions">
            <button type="button" id="register-back-btn" class="btn btn-default">返回</button>
            <button type="submit" id="register-submit-btn" class="btn btn-success uppercase pull-right">注册</button>
        </div>
    </form>
    <!-- END REGISTRATION FORM -->
</div>
<div class="copyright">
    地址：江西省南昌市华东交通大学理工学院 咨询电话：110<br/>
    HXX科技有限公司 的icp备案号 （沪ICP备16053543号）
</div>

<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>