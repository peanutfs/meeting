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
    <legend>修改会议</legend>
</fieldset>
<form class="layui-form" action="" style="margin-top:20px" method="post">
    <input type="hidden" name="id" value="${meetingInfo.id}"/>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">会议标题</label>
            <div class="layui-input-inline">
                <input type="text" name="meetingTitle" value="${meetingInfo.meetingTitle}" lay-verify="required" autocomplete="off"  class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">主办单位</label>
            <div class="layui-input-inline">
                <input type="text" name="organizer" lay-verify="required" value="${meetingInfo.organizer}" autocomplete="off"  class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">开始时间</label>
            <div class="layui-input-inline">
                <input type="text" name="startTime" id="startTime" lay-verify="dateTime" value="${meetingInfo.startTime?string("yyyy-MM-dd HH:mm:ss")}" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">结束时间</label>
            <div class="layui-input-inline">
                <input type="text" name="endTime" id="endTime" lay-verify="dateTime" value="${meetingInfo.endTime?string("yyyy-MM-dd HH:mm:ss")}" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">签到地点</label>
            <div class="layui-input-inline">
                <input type="text" name="checkInLocation" id="tipinput" lay-verify="required" class="layui-input" value="${meetingInfo.checkInLocation}">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">签到范围</label>
            <div class="layui-input-inline">
                <input type="text" name="checkInRange" lay-verify="required|number" autocomplete="off" class="layui-input"  value="${meetingInfo.checkInRange?c}">
            </div>
            <div class="layui-form-mid">米</div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">签到规则</label>
            <div class="layui-input-inline">
                <input type="text" name="checkInRule" lay-verify="required" autocomplete="off" class="layui-input" value="${meetingInfo.checkInRule}">
            </div>
        </div>
    </div>
    <div class="layui-form-item" id="appPackage">
        <label class="layui-form-label">主题图片</label>
        <div class="layui-upload">
            <button type="button" class="layui-btn layui-btn-sm" id="selectFile"><i class="layui-icon">&#xe61f;</i>选择文件</button>
            <button type="button" class="layui-btn layui-btn-sm" id="uploadFile"><i class="layui-icon">&#xe67c;</i>开始上传</button>
            <input type="hidden" name="themeImageUrl" value="${meetingInfo.themeImageUrl!""}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea class="layui-textarea" name="remark" required lay-verify="required">${meetingInfo.remark}</textarea>
        </div>
    </div>
    <#--<div class="layui-form-item">-->
        <#--<label class="layui-form-label">是否需要填写个人信息</label>-->
        <#--<div class="layui-input-block">-->
        <#--<#assign isNeedRegister ="${meetingInfo.isNeedRegister}"/>-->
        <#--<#if isNeedRegister == 'Y'>-->
            <#--<input type="checkbox" name="isNeedRegister" lay-skin="switch" lay-text="是|否" lay-filter="isNeedRegisterFilter" checked>-->
        <#--<#else>-->
            <#--<input type="checkbox" name="isNeedRegister" lay-skin="switch" lay-text="是|否" lay-filter="isNeedRegisterFilter">-->
        <#--</#if>-->
        <#--</div>-->
    <#--</div>-->
    <div class="layui-form-item"  style="margin-left: 150px">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-submit" lay-submit="" lay-filter="submitForm"><i class="layui-icon">&#xe605;</i>提交</button>
            <a class="layui-btn layui-btn-warm layui-layer-close layui-layer-close1" id="editCancel"><i class="layui-icon">&#xe65c;</i>返回</a>
        </div>
    </div>
</form>
<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=30d103007a4d021f643ae2fd6732df85&plugin=AMap.Autocomplete"></script>
<script type="text/javascript" src="https://cache.amap.com/lbs/static/addToolbar.js"></script>
<script type="text/javascript">
    var map = new AMap.Map("container", {
        resizeEnable: true
    });
    //输入提示
    var auto = new AMap.Autocomplete({
        input: "tipinput"
    });
</script>
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

        var newThemeImageUrl = null;
        var isNeedRegisterFilter = "N";

        //监听提交
        form.on('submit(submitForm)', function(data){
//            console.log(JSON.stringify(data.field));
            var themeImageUrl = data.field.themeImageUrl;
            if(newThemeImageUrl !== null){
                themeImageUrl = newThemeImageUrl
            }
            $.ajax({
                url:'${base}/meeting/editMeetingInfo',
                type:'POST',
                dataType:'json',
                data:{
                    id:data.field.id
                    ,meetingTitle:data.field.meetingTitle
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
                        layer.msg("更新成功", {icon: 6});
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

        // 返回
        $('#editCancel').on('click', function () {
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index);
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
                    newThemeImageUrl = res.data;
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

        form.on('switch(isNeedRegisterFilter)', function(data){
            if(this.checked){
                isNeedRegisterFilter = "Y";
            }
        });
    });
</script>
</body>
</html>



