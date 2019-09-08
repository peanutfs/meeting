<#assign base = request.contextPath />
<!DOCTYPE html>
<html>
<head>
    <base id="base" href="${base}">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>贝勒医疗管理后台</title>
    <link rel="stylesheet" href="${base}/js/layui/css/layui.css">
</head>

<body class="body">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>会议列表</legend>
</fieldset>
<div class="layui-form">
    <div class="layui-form-item">
        <div class="layui-inline">
            <div class="layui-input-inline" style="width:auto; margin-left: 4px">
                <button class="layui-btn btn-add layui-btn-warm" id="btn-add"><i class="layui-icon">&#xe654;</i>新建会议</button>
                <button class="layui-btn btn-add btn-default" id="btn-refresh"><i class="layui-icon">&#xe669;</i>刷新</button>
            </div>
        </div>

    </div>
</div>
<script type="text/javascript" src="${base}/js/layui/layui.js"></script>
<table class="layui-hide" id="meetingInfoTable" lay-filter = "meetingInfoTable"></table>

<!-- 表格操作按钮集 -->
<script type="text/html" id="barOption">
    <button type="button" class="layui-btn layui-btn-sm layui-btn-warm" lay-event="addUser"><i class="layui-icon">&#xe654;</i>录入用户</button>
    <button type="button" class="layui-btn layui-btn-sm btn-default" lay-event="viewUser"><i class="layui-icon">&#xe615;</i>查看用户</button>
    <button type="button" class="layui-btn layui-btn-sm layui-btn-normal" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</button>
    <button type="button" class="layui-btn layui-btn-sm layui-btn-danger" lay-event="del"><i class="layui-icon">&#xe640;</i>删除</button>
</script>

<script type="text/html" id="isEffectiveTpl">
    <input type="checkbox" name="isEffective" value="{{d.id}}" lay-skin="switch" lay-text="是|否" lay-filter="isEffectiveFilter" {{ d.isEffective == 1 ? 'checked' : '' }}>
</script>

<script>
    layui.use(['table', 'form', 'layer'], function () {
        var form = layui.form
                , table = layui.table
                , layer = layui.layer
                ,$=layui.jquery;
        // 表格渲染
        var tableIns = table.render({
            elem: '#meetingInfoTable'
            , method:'post'
            , toolbar: '#toolbarDemo'
            , title: '版本更新信息表'
            , cellMinWidth:50
            , cols: [[
                {title: '序号', width:90, sort: true, type: 'numbers'}
                ,{field: 'id', title: '序号', width:90, sort: true, hide:true }
                , {field: 'meetingTitle', title: '会议标题'}
                , {field: 'startTime', title: '开始时间',width:120}
                , {field: 'endTime', title: '结束时间',width:120}
                , {field: 'checkInLocation', title: '签到地点'}
                , {field: 'organizer', title: '主办单位'}
                , {field: 'checkInCount', title: '签到人数', width: 100}
                , {field:'isEffective', title:'是否生效', width:100, templet: '#isEffectiveTpl', unresize: true}
                , {fixed: 'right',title: '操作', align: 'center', toolbar: '#barOption', width:400}
            ]]
            , id: 'versionInfo'
            , url: '${base}/meeting/meetingInfo'
            , page: true
            , limits: [30, 60, 90, 150, 300]
            , parseData: function (res) {
            }
        });

        form.on('switch(isEffectiveFilter)', function(obj){
            var id = this.value;
            if(obj.elem.checked){
                layer.confirm('只允许存在一条会议生效，确认生效该条？', {icon: 0}, function (index) {
                    $.ajax({
                        url: "${base}/meeting/changeEffectiveStatus",
                        type: "POST",
                        data: {id: id, isEffective:'1'},
                        success: function (msg) {
//                            var json=JSON.parse(msg);
                            var returnCode = msg.success;
                            if (returnCode === 0) {
                                //关闭弹框
                                layer.close(index);
                                //显示提示框
                                layer.msg("更新成功", {icon: 6});
                                tableIns.reload()
                            } else {
                                layer.msg("更新成功", {icon: 5});
                            }
                        }
                    });
                    return false;
                });
            }else {
                $.ajax({
                    url: "${base}/meeting/changeEffectiveStatus",
                    type: "POST",
                    data: {id: id, isEffective:'0'},
                    success: function (msg) {
//                            var json=JSON.parse(msg);
                        var returnCode = msg.success;
                        if (returnCode === 0) {
                            //显示提示框
                            layer.msg("更新成功", {icon: 6});
                            tableIns.reload()
                        } else {
                            layer.msg("更新成功", {icon: 5});
                        }
                    }
                });
            }
        });

        //监听工具条
        table.on('tool(meetingInfoTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'addUser') {
                layer.open({
                    type: 2,
                    title: '新增用户',
                    maxmin: true,
                    area: ['800px', '600px'],
                    shadeClose: false, //点击遮罩关闭
                    content: '${base}/user/addUserForm?id=' + data.id
                });
            } else if (obj.event === 'del') {
                layer.confirm('会议删除会同时删除该会议下的所有用户，确认删除该条会议？', {icon: 0}, function (index) {
                    $.ajax({
                        url: "${base}/meeting/delMeetingInfo",
                        type: "POST",
                        data: {id: data.id},
                        success: function (msg) {
//                            var json=JSON.parse(msg);
                            var returnCode = msg.success;
                            if (returnCode === 0) {
                                //删除这一行，前端界面的修改，直接删除了这一条数据
                                obj.del();
                                //关闭弹框
                                layer.close(index);
                                //显示提示框
                                layer.msg("删除成功", {icon: 6});
                                tableIns.reload()
                            } else {
                                layer.msg("删除失败", {icon: 5});
                            }
                        }
                    });
                    return false;
                });
            } else if (obj.event === 'edit') {
                layer.open({
                    type: 2,
                    title: '编辑会议信息',
                    maxmin: true,
                    area: ['800px', '550px'],
                    shadeClose: false, //点击遮罩关闭
                    content: '${base}/meeting/editMeetingInfoForm?id=' + data.id
                });
            }else if(obj.event === 'viewUser'){
                location.href="${base}/user/userInfoForm?meetingId=" + data.id;
            }
        });

        //新增会议
        $('#btn-add').on('click', function () {
            layer.open({
                type: 2,
                title: '新增会议',
                maxmin: true,
                area: ['800px', '550px'],
                shadeClose: false, //点击遮罩关闭
                content: '${base}/meeting/addMeetingForm'
            });
        });



        //搜索功能的实现
        var $ = layui.$, active = {
            reload: function () {
                //执行重载
                table.reload('versionInfo', {
                    where: {
                        appCode: $('#appName').val(),
                        appType: $('#appType').val()
                    }
                });
            }
        };

        $('#searchBtn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        // 刷新
        $('#btn-refresh').on('click', function () {
            tableIns.reload()
        });
    });
</script>
</body>
</html>