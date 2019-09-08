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
    <legend>用户列表</legend>
</fieldset>
<div class="layui-form">
    <div class="layui-form-item">
        <div class="layui-inline">
            <div class="layui-input-inline" style="width:auto; margin-left: 4px">
                <button class="layui-btn btn-default" id="btn-refresh"><i class="layui-icon">&#xe669;</i>刷新</button>
                <button class="layui-btn layui-btn-warm" id="btn-back"><i class="layui-icon">&#xe65c;</i>返回</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${base}/js/layui/layui.js"></script>
<input type="hidden" name="meetingId" id="meetingId" value="${meetingId}">
<table class="layui-hide" id="userInfoTable" lay-filter = "userInfoTable"></table>

<!-- 表格操作按钮集 -->
<script type="text/html" id="barOption">
    <button type="button" class="layui-btn layui-btn-sm layui-btn-normal" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</button>
    <button type="button" class="layui-btn layui-btn-sm layui-btn-danger" lay-event="del"><i class="layui-icon">&#xe640;</i>删除</button>
</script>

<script type="text/html" id="sexTel">
    {{#  if(d.sex === 'M'){ }}
    男
    {{#  } else { }}
    女
    {{#  } }}
</script>

<script type="text/html" id="isCheckInTel">
    {{#  if(d.isCheckIn === '1'){ }}
    已签到
    {{#  } else { }}
    未签到
    {{#  } }}
</script>

<script>
    layui.use(['table', 'form', 'layer'], function () {
        var form = layui.form
                , table = layui.table
                , layer = layui.layer
                ,$=layui.jquery;
        // 表格渲染
        var meetingId = $('#meetingId').val();

        var tableIns = table.render({
            elem: '#userInfoTable'
            , method:'post'
            , toolbar: '#toolbarDemo'
            , title: '版本更新信息表'
            , cellMinWidth:50
            , cols: [[
                {title: '序号', width:90, sort: true, type: 'numbers'}
                ,{field: 'meetingId',title: 'meeting', width:90, sort: true, hide: true}
                ,{field: 'id', title: 'id', width:80, sort: true, hide:true}
                , {field: 'username', title: '姓名', width:100 }
                , {field: 'sex', title: '性别',width:60, templet:'#sexTel'}
                , {field: 'age', title: '年龄',width:60}
                , {field: 'phoneNo', title: '手机号', width:150}
                , {field: 'arriveTime', title: '到达时间', width:180}
                , {field: 'leaveTime', title: '离开时间', width:180}
                , {field: 'companyName', title: '单位名称'}
                , {field: 'companyAddress', title: '单位地址'}
                , {field: 'transport', title: '交通方式', width: 100}
                , {field: 'roomNo', title: '住宿房号', width: 100}
                , {field: 'isCheckIn', title: '是否签到', width: 100 ,templet:'#isCheckInTel'}
                , {fixed: 'right', title: '操作', align: 'center', toolbar: '#barOption', width:165}
            ]]
            , id: 'userInfo'
            , url: '${base}/user/userInfo?meetingId=' + meetingId
            , page: true
            , limits: [30, 60, 90, 150, 300]
            , parseData: function (res) {
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                // console.log(res);
            }
        });

        //监听工具条
        table.on('tool(userInfoTable)', function (obj) {
            var data = obj.data;
           if (obj.event === 'del') {
                layer.confirm('确认删除该条记录？', {icon: 0}, function (index) {
                    $.ajax({
                        url: "${base}/user/delUserInfo",
                        type: "POST",
                        data: {id: data.id},
                        success: function (msg) {
                            console.log(msg);
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
                   title: '编辑用户信息',
                   maxmin: true,
                   area: ['800px', '550px'],
                   shadeClose: false, //点击遮罩关闭
                   content: '${base}/user/editUserInfoForm?id=' + data.id
               });
            }
        });

        $('#btn-back').on('click', function () {
            location.href = "${base}/meeting/meetingInfoForm"
        });


        // 刷新
        $('#btn-refresh').on('click', function () {
            tableIns.reload()
        });
    });
</script>
</body>
</html>