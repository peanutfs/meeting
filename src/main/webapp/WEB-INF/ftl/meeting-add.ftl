<@comm.comm>
<#assign contextPath = request.getContextPath() />
<#setting classic_compatible=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml">
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
            <title>贝勒医疗管理后台</title>
        </head>
        <body>
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
                <legend>添加会议</legend>
            </fieldset>

            <form class="layui-form" action="${contextPath}/SupervisionAppVersionController/addVersionInfo.htm" style="margin-top:20px" method="post">
                <div class="layui-form-item">
                    <label class="layui-form-label">应用名称</label>
                    <div class="layui-input-block">
                        <button type="button" class="layui-btn layui-btn-sm" id="btn-add-app-name" style="float: left"><i class="layui-icon">&#xe654;</i>新增应用</button>
                    </div>
                    <div class="layui-input-block" id="div-app-name">
                        <select name="appName" id="appName" lay-filter="select" required lay-verify="required">
                            <option value=""></option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">应用类型</label>
                    <div class="layui-input-block">
                        <input type="radio" name="appType" value="android" title="安卓" checked="" lay-filter="appTypeFilter">
                        <input type="radio" name="appType" value="ios" title="苹果" lay-filter="appTypeFilter">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">版本号</label>
                    <div class="layui-input-block">
                        <input type="text" name="version" required  lay-verify="required|versionVerify" autocomplete="off" placeholder="请输入版本号" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">强制更新</label>
                    <div class="layui-input-block">
                        <input type="checkbox" name="isForceUpdate" lay-skin="switch" lay-filter="forceUpdateSwitch" lay-text="是|否">
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">更新文案</label>
                    <div class="layui-input-block">
                        <textarea placeholder="请输入更新文案" class="layui-textarea" name="description" required lay-verify="required"></textarea>
                    </div>
                </div>
                <div class="layui-form-item" id="appPackage">
                    <label class="layui-form-label">安装包(.apk)</label>
                    <div class="layui-upload">
                        <button type="button" class="layui-btn layui-btn-sm" id="selectFile" required lay-verify="required"><i class="layui-icon">&#xe61f;</i>选择文件</button>
                        <button type="button" class="layui-btn layui-btn-sm" id="uploadFile"><i class="layui-icon">&#xe67c;</i>开始上传</button>
                    </div>

                </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">安装包大小(M)</label>
                    <div class="layui-input-block">
                        <input type="text" name="packageSize" id="packageSize" required  lay-verify="required|number" autocomplete="off" placeholder="请输入安装包大小" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item" style="margin-top:40px">
                    <div class="layui-input-block">
                        <button class="layui-btn layui-btn-submit" lay-submit="" lay-filter="submitForm">立即提交</button>
                        <button type="reset" class="layui-btn layui-btn-primary" id="btn-reset">重置</button>
                    </div>
                </div>
            </form>
        </body>
    </html>
</@comm.comm>
