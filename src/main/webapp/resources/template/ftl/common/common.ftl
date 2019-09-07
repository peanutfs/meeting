<#macro comm>
<#assign contextPath = request.getContextPath() />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Common import</title>
    <link rel="stylesheet" href="${contextPath}/resources/js/layui/css/layui.css">
    <script language="javascript" src="${contextPath}/resources/js/layui/layui.js" charset="utf-8"></script>
</head>

<body>
<#nested>
</body>
</html>
</#macro>