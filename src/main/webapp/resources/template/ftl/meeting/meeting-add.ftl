<#assign base = request.contextPath />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base id="base" href="${base}">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>贝勒医疗管理后台</title>
    <link rel="stylesheet" href="${base}/js/layui/css/layui.css">
    <script language="javascript" src="${base}/js/layui/layui.js" charset="utf-8"></script>
</head>
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>添加会议</legend>
</fieldset>
<form class="layui-form" action="" style="margin-top:20px" method="post">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">会议标题</label>
            <div class="layui-input-inline">
                <input type="text" name="meetingTitle" lay-verify="required" autocomplete="off" placeholder="请输入会议标题" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">主办单位</label>
            <div class="layui-input-inline">
                <input type="text" name="organizer" lay-verify="required" autocomplete="off" placeholder= "请输入主办单位" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">开始时间</label>
            <div class="layui-input-inline">
                <input type="text" name="startTime" id="startTime" lay-verify="dateTime" placeholder="请选择开始时间" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">结束时间</label>
            <div class="layui-input-inline">
                <input type="text" name="endTime" id="endTime" lay-verify="dateTime" placeholder="请选择结束时间" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">签到地点</label>
            <div class="layui-input-inline">
                <input type="text" name="checkInLocation" lay-verify="required" autocomplete="off" class="layui-input" placeholder="请输入签到地点">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">签到范围</label>
            <div class="layui-input-inline">
                <input type="text" name="checkInRange" lay-verify="required|number" autocomplete="off" class="layui-input"  placeholder="请输入签到范围">
            </div>
            <div class="layui-form-mid">米</div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">签到规则</label>
            <div class="layui-input-inline">
                <input type="text" name="checkInRule" lay-verify="required" autocomplete="off" class="layui-input" placeholder="请输入签到规则">
            </div>
        </div>
    </div>
    <div class="layui-form-item" id="appPackage">
        <label class="layui-form-label">主题图片</label>
        <div class="layui-upload">
            <button type="button" class="layui-btn layui-btn-sm" id="selectFile" required lay-verify="required"><i class="layui-icon">&#xe61f;</i>选择文件</button>
            <button type="button" class="layui-btn layui-btn-sm" id="uploadFile"><i class="layui-icon">&#xe67c;</i>开始上传</button>
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea placeholder="请输入备注" class="layui-textarea" name="remark" required lay-verify="required"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">是否需要填写个人信息</label>
        <div class="layui-input-block">
            <input type="checkbox" name="isNeedRegister" lay-skin="switch" lay-text="是|否" lay-filter="isNeedRegisterFilter">
        </div>
    </div>
    <div class="layui-form-item"  style="margin-left: 150px">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-submit" lay-submit="" lay-filter="submitForm">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary" id="btn-reset">重置</button>
        </div>
    </div>
</form>
<script>
    layui.use(['form','layer','jquery', 'upload', 'laydate'], function(){
        var form = layui.form
                ,layer = layui.layer
                ,$=layui.jquery
                ,table= layui.table
                ,upload = layui.upload
                ,laydate = layui.laydate;

        //日期
        laydate.render({
            elem: '#startTime'
            ,type: 'datetime'
        });

        laydate.render({
            elem: '#endTime'
            ,type: 'datetime'
        });

        var themeImageUrl = null;
        var isNeedRegisterFilter = "N";

        //监听提交
        form.on('submit(submitForm)', function(data){
//            console.log(JSON.stringify(data.field));
            $.ajax({
                url:'${base}/meeting/addMeeting',
                type:'POST',
                dataType:'json',
                data:{
                    meetingTitle:data.field.meetingTitle
                    ,startTime:data.field.startTime
                    ,endTime:data.field.endTime
                    ,checkInLocation:data.field.checkInLocation
                    ,checkInRange:data.field.checkInRange
                    ,organizer:data.field.organizer
                    ,checkInRule:data.field.checkInRule
                    ,remark:data.field.remark
                    ,isNeedRegister:isNeedRegisterFilter
                    ,themeImageUrl:themeImageUrl
                },
                success:function (msg) {
                    console.log(msg);
                    var returnCode=msg.success;
                    if(returnCode===0){
                        layer.closeAll('loading');
                        layer.load(2);
                        layer.msg("添加成功", {icon: 6});
                        setTimeout(function(){
                            window.parent.location.reload();//修改成功后刷新父界面
                        }, 1000);
                        //加载层-风格

                    }else{
                        var returnMsg = msg.message;
                        layer.msg(returnMsg, {icon: 5});
                    }
                }
            });
            return false;
        });

        // 重置
        $('#btn-reset').on('click', function () {
            window.location.reload()
        });

        //选完文件后不自动上传
        var index = null;
        upload.render({
            elem: '#selectFile'
            ,url: '${base}/meeting/uploadImage'
            ,auto: false
            ,accept:'images'
            ,bindAction: '#uploadFile'
            ,done: function(res){
                console.log(res);
                if(res.success ===0){
                    themeImageUrl = res.data;
                    $('#selectFile').removeAttr("lay-verify");
                    // $('#uploadFile').attr("disabled", "disabled");
                    layer.close(index);
                    layer.msg("上传成功", {icon: 6});
                }else {
                    layer.msg("上传失败", {icon: 5});
                }
            }
            ,before: function(obj){
                console.log(obj);
                index = layer.load(); //上传loading
            }
            ,error: function(index, upload){
                layer.close(index);
            }

        });



        // 表单验证
        form.verify({
            appCodeVerify: function(value, item){ //value：表单的值、item：表单的DOM对象
                if(!new RegExp("^[A-Za-z]+$").test(value)){
                    return '只能输入英文字符';
                }
            }
            ,versionVerify: [
                /^\d+(\.\d+)+(\.\d+)?$/
                ,'仅支持数字与小数点组合(eg:1.0.1)'
            ]
        });

        form.on('switch(isNeedRegisterFilter)', function(data){
            if(this.checked){
                isNeedRegisterFilter = "Y";
            }
        });
    });
</script>
</body>
</html>



