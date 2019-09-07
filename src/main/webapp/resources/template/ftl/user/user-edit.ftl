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
    <legend>添加用户</legend>
</fieldset>
<form class="layui-form" action="" style="margin-top:20px" method="post">
    <input type="hidden" name="id" value="${userInfo.id}"/>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-inline">
                <input type="text" name="username" lay-verify="required" autocomplete="off" value="${userInfo.username}" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <#assign sex = "${userInfo.sex}"/>
            <label class="layui-form-label">性别</label>
            <div class="layui-input-block">
                <#if sex == 'M'>
                    <input type="radio" name="sex" value="M" title="男" checked="">
                    <input type="radio" name="sex" value="F" title="女">
                <#else>
                    <input type="radio" name="sex" value="M" title="男">
                    <input type="radio" name="sex" value="F" title="女" checked="">
                </#if>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">手机号</label>
            <div class="layui-input-inline">
                <input type="text" name="phoneNo" lay-verify="required|phone" autocomplete="off"  value="${userInfo.phoneNo}" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">年龄</label>
            <div class="layui-input-inline">
                <input type="text" name="age" lay-verify="required|number" autocomplete="off"  value="${userInfo.age}" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">到达日期</label>
            <div class="layui-input-inline">
                <input type="text" name="arriveTime" id="startTime" lay-verify="dateTime"  value="${userInfo.arriveTime}" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">离开日期</label>
            <div class="layui-input-inline">
                <input type="text" name="leaveTime" id="endTime" lay-verify="dateTime" value="${userInfo.leaveTime}" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">单位名称</label>
            <div class="layui-input-inline">
                <input type="text" name="companyName" lay-verify="required" autocomplete="off" class="layui-input" value="${userInfo.companyName}">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">单位地址</label>
            <div class="layui-input-inline">
                <input type="text" name="companyAddress" lay-verify="required" autocomplete="off" class="layui-input"  value="${userInfo.companyAddress}">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">交通方式</label>
        <div class="layui-input-inline">
            <#assign transport="${userInfo.transport}"/>
            <select name="transportSelect" id="transportSelect" lay-filter="transport" lay-verify="required">
                <option value=""></option>
                <option value="飞机" <#if transport=="飞机">selected="selected"</#if>>飞机</option>
                <option value="火车" <#if transport=="火车">selected="selected"</#if>>火车</option>
                <option value="高铁" <#if transport=="高铁">selected="selected"</#if>>高铁</option>
                <option value="长途汽车" <#if transport=="长途汽车">selected="selected"</#if>>长途汽车</option>
                <option value="自驾" <#if transport=="自驾">selected="selected"</#if>>自驾</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">住宿房号</label>
        <div class="layui-input-inline">
            <input type="text" name="roomNo" lay-verify="required" autocomplete="off" class="layui-input" value="${userInfo.roomNo}">
        </div>
    </div>
    <div class="layui-form-item" style="margin-left: 150px">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-submit" lay-submit="" lay-filter="submitForm"><i class="layui-icon">&#xe605;</i>提交</button>
            <a class="layui-btn layui-btn-warm layui-layer-close layui-layer-close1" id="editCancel"><i class="layui-icon">&#xe65c;</i>返回</a>
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

        //监听提交
        form.on('submit(submitForm)', function(data){
//            console.log(JSON.stringify(data.field));
            var transport = $('#transportSelect').val();
            $.ajax({
                url:'${base}/user/editUserInfo',
                type:'POST',
                dataType:'json',
                data:{
                    id:data.field.id
                    ,username:data.field.username
                    ,sex:data.field.sex
                    ,age:data.field.age
                    ,phoneNo:data.field.phoneNo
                    ,arriveTime:data.field.arriveTime
                    ,leaveTime:data.field.leaveTime
                    ,companyName:data.field.companyName
                    ,companyAddress:data.field.companyAddress
                    ,transport:transport
                    ,roomNo:data.field.roomNo
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

        $('#editCancel').on('click', function () {
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index);
        });
    });
</script>
</body>
</html>



