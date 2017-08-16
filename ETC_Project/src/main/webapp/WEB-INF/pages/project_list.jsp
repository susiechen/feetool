<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ETC</title>

    <!-- Bootstrap Core CSS -->
    <link href="/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/dist/css/sb-admin-2.css" rel="stylesheet">

    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>
<div class="modal fade" id="modal_action" tabindex="3" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel"><strong>新增项目 </strong></h4>
            </div>
            <div class="modal-body text-align-center">
                <strong>请输入项目名称： </strong><input type="text" title="" class="form-control" id="input_content">
            </div>
            <div class="modal-footer">
                <button id="close" type="button" class="btn btn-default" data-dismiss="modal">
                    取消
                </button>
                <button id="save_project" onclick="saveProject()" type="button" class="btn btn-primary">
                    保存
                </button>
            </div>
        </div>
    </div>
</div>
<div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.html">ETC_V1.0</a>
        </div>
        <!-- /.navbar-header -->


        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="sidebar-search">
                        <div class="input-group custom-search-form">
                            <input type="text" class="form-control" placeholder="Search...">
                                <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                        </div>
                        <!-- /input-group -->
                    </li>
                    <li>
                        <a href="index.html"><i class="fa fa-dashboard fa-fw"></i>Web Projects Manager</a>
                    </li>
                   <%-- <li>
                        <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> TestCase<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="flot.html">New TestCase</a>
                            </li>
                            <!--<li>
                                <a href="morris.html">Morris.js Charts</a>
                            </li>-->
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    <li>
                        <a href="tables.html"><i class="fa fa-table fa-fw"></i> Projects</a>
                    </li>--%>
            </div>
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->
    </nav>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Projects</h1>
                <p align="right">

                    <input type="button" name="bt_add" id="bt_add" class="btn btn-primary" value="新增项目" onclick="showModal()"/></p>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        DataTables Advanced Tables
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-projects">
                            <thead>
                            <tr class="info">
                                <th>ID</th>
                                <th>项目名称</th>
                                <th>操作</th>

                            </tr>
                            </thead>
                            <tbody id="tb">
                            </tbody>
                        </table>
                        <!-- /.table-responsive -->

                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<!-- jQuery -->
<script src="/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="/vendor/metisMenu/metisMenu.min.js"></script>

<!-- DataTables JavaScript -->
<script src="/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script src="/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
<script src="/vendor/datatables-responsive/dataTables.responsive.js"></script>

<!-- Custom Theme JavaScript -->
<script src="/dist/js/sb-admin-2.js"></script>

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
    $(document).ready(function() {
        $('#dataTables-projects').DataTable({
            responsive: true
        });
    });
</script>
<script type="text/javascript">
    var jsonobj=JSON.parse('${projects}');
        var ht="";
        for(row=0;row<jsonobj.length;row++)
        {
            ht = ht+'<tr class="odd gradeX">';
            ht = ht + '<td>' + jsonobj[row].project_id + '</td>';
            ht = ht + '<td><a href="${pageContext.request.contextPath}/testCaseList/project_id/'+jsonobj[row].project_id+'/project_name/'+jsonobj[row].project_name+'">'+jsonobj[row].project_name+'</a></td>';
            ht = ht + '<td><input type="button" class="btn btn-primary" value=编辑>|<input type="button" class="btn btn-primary" onclick="deleteRow(this,jsonobj[row].project_id)" value=删除>|<input type="button" class="btn btn-primary" id="deviceSet" value=设置></td>'
            ht = ht+'</tr>';
        }
        $('#tb').html(ht);
</script>
<div class="modal fade" id="device_action" tabindex="3" role="dialog" aria-labelledby="myDevicesLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myDevicesLabel"><strong>设备设置</strong></h4>
            </div>
            <div class="modal-body text-align-center">
                <strong>platformName(Android/IOS)： </strong><input type="text" title="" class="form-control"
                                                                   id="platformName_content">
                <strong>deviceName： </strong><input type="text" title="" class="form-control" id="deviceName_content">
                <strong>pVersion： </strong><input type="text" title="" class="form-control" id="pVersion_content">
                <strong>browserName： </strong><input type="text" title="" class="form-control" id="browserName_content">
            </div>
            <div class="modal-footer">
                <button id="cancel" type="button" class="btn btn-default" data-dismiss="modal">
                    取消
                </button>
                <button id="save_Devices" onclick="saveDevices()" type="button" class="btn btn-primary">
                    保存
                </button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var devicePID = "";
    var PJname = "";
    var project;
    <%--function selectProject() {--%>
        <%--var value = document.getElementById('project_name').value--%>
        <%--$.post("${pageContext.request.contextPath}/searchProject/" + value, function (data, status) {--%>
        <%--});--%>
    <%--}--%>
    function deleteRow(r, id) {
        $.get("${pageContext.request.contextPath}/delProject/" + id, function (data, status) {
            alert(data);
            var i = r.parentNode.parentNode.rowIndex
            document.getElementById('dataTables-projects').deleteRow(i)
        });
    }
    function updateProject(pName, pId) {
        $.post("${pageContext.request.contextPath}/updateProject/project_name/" + pName + "/pid/" + pId, function (data, status) {
            alert(data);
        });
    }

    function updateDevices(pname, pId, platformName, deviceName, pVersion, browserName) {
        $.post("${pageContext.request.contextPath}/updateDevices/project_name/" + pname + "/pid/" + pId + "/platformName/" + platformName + "/deviceName/" + deviceName + "/pVersion/" + pVersion + "/browserName/" + browserName, function (data, status) {
            alert(data);

        });
    }
    $(function () {
        $("input:button").click(function (e) {
//            && $(this).val() != "新增项目" && $(this).val() != "设置"
            if ($(this).val() == "编辑") {
                str = "确定"
            } else if ($(this).val() == "确定") {
                str = "编辑"
            } else if ($(this).val() == "设置") {
                str = "设置"
            } else if ($(this).val() == "新增项目") {
                str = "新增项目"
            }
            else if ($(this).val() == "删除") {
                str = "删除"
            }
            $(this).val(str);   // 按钮被点击后，在“编辑”和“确定”之间切换
            if ($(this).val() != "设置") {
                $(this).parent().siblings("td").each(function () {  // 获取当前行的其他单元格
                    obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框
                    if (!obj_text.length && $(this).index() != 0) {// 如果没有文本框，则添加文本框使之可以编辑
                        $(this).html("<input type='text' value='" + $(this).text() + "'>");
                    }
                    else if ($(this).index() != 0 && obj_text.length > 0) {// 如果已经存在文本框，则将其显示为文本框修改的值
                        var obj_name = $(this).children()[0].value;
                        obj_id = parseInt($($(this).parent().children()[0]).text())
                        updateProject(obj_name, obj_id);
                        $(this).html('<a href="${pageContext.request.contextPath}/testCaseList/project_id/' + obj_id + '/project_name/'+obj_name+'">' + obj_name + '</a>');
                    }
//                 $(this).html(obj_text.val());
                });
            } else {
                $(this).parent().siblings("td").each(function () {
                    if ($(this).index() == 0) {
                        obj_id = $($($(this).parent()[0]).children()[0]).text();
                        obj_name = $($($(this).parent()[0]).children()[1]).text();
                        deviceSet(obj_id, obj_name);
                    }
                });
            }
        });
    });
    $(function () {
    })
    function showModal() {
        // 显示模态框
        $("#modal_action").modal("show");
    }

    function deviceSet(id, pname) {
        devicePID = id;
        PJname = pname;
        $.post("/searchDevices/project_id/" + id, function (data, status) {
            project = data;
            $("#platformName_content").val(project.platformName);
            $("#deviceName_content").val(project.deviceName);
            $("#pVersion_content").val(project.pVersion);
            $("#browserName_content").val(project.browserName);
        });
        $("#device_action").modal("show", project);

    }
    function saveProject() {
        // 获取输入框中输入的内容
        var inputValue = $.trim($("#input_content").val());
        // 操作成功以后，隐藏模态框
        $("#modal_action").modal("hide");
        $.post("${pageContext.request.contextPath}/addProject/project_name/" + inputValue, function (data, status) {
            alert(data);
            window.location.href = "${pageContext.request.contextPath}/projectlist.do";
        });
    }


    function saveDevices() {
        var i;
        var platformName = $.trim($("#platformName_content").val());
        var deviceName = $.trim($("#deviceName_content").val());
        var pVersion = $.trim($("#pVersion_content").val());
        var browserName = $.trim($("#browserName_content").val());
        if (platformName == "" || deviceName == "" || pVersion == "" || browserName == "") {
            alert("输入信息不能为空");
        }
        else {
            updateDevices(PJname, devicePID, platformName, deviceName, pVersion, browserName);
            $("#device_action").modal("hide");
        }
    }


</script>
    </body>

    </html>
