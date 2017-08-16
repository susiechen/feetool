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
<%
    long time=System.currentTimeMillis();
%>
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
                    <%--<li>--%>
                        <%--<a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>TestCase<span class="fa arrow"></span></a>--%>
                        <%--<ul class="nav nav-second-level">--%>
                            <%--<li>--%>
                                <%--<a href="flot.html">New TestCase</a>--%>
                            <%--</li>--%>
                            <%--<!--<li>--%>
                                <%--<a href="morris.html">Morris.js Charts</a>--%>
                            <%--</li>-->--%>
                        <%--</ul>--%>
                        <%--<!-- /.nav-second-level -->--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a href="tables.html"><i class="fa fa-table fa-fw"></i> Projects</a>--%>
                    <%--</li>--%>
            </div>
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->
    </nav>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">TestCase</h1>

                <p align="right">
                    <input type="button" name="bt_add" class="btn btn-primary" id="bt_add" value="新增用例" onclick="window.location.href='${pageContext.request.contextPath}/newTestCase/project_id/${project_id}' "/>
                    <a href="/ETC/${project_name}/Reports/html/index.html?radam=<%=time%>">【执行报告】</a>
                    <input type="button" class="btn btn-primary" name="suite" id="suiteRun" value="批量运行" onclick="suiteRun(${project_id},'${project_name}')"/>
                    <a href="/ETC/${project_name}/Reports/SuiteReports/html/index.html?radam=<%=time%>">【批量执行报告】</a>
                    </p>
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
                        <table width="100%" class="table table-striped table-bordered table-hover"
                               id="dataTables-testcase">
                            <thead>
                            <tr class="info">
                                <th>ID</th>
                                <th>用例名称</th>
                                <th>优先级</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="tb">
                            <c:if test="${!empty testCaseList}">
                                <c:forEach items="${testCaseList}" var="testcase">
                                    <tr align="center">
                                        <td>${testcase.tc_id}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/editTestCase/tc_id/${testcase.tc_id}">${testcase.tc_name}</a>
                                        </td>
                                        <td>${testcase.priority}</td>
                                            <%--<td>--%>
                                            <%--<a href="${pageContext.request.contextPath}/testCaseList/project_id/${project.project_id}">${project.project_name}</a>--%>
                                            <%--</td>--%>
                                        <td>
                                            <input type="button" class="btn btn-primary" value=编辑>
                                            |
                                            <input type="button" class="btn btn-primary" onclick="deleteRow(this,${testcase.tc_id},'${project_name}','${testcase.tc_name}')"
                                                   value=删除>
                                            |
                                            <input type="button" class="btn btn-primary" value=运行>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
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
    $(document).ready(function () {
        $('#dataTables-testcase').DataTable({
            responsive: true
        });
    });
</script>



    <script type="text/javascript">
    var timestamp = (new Date()).valueOf();
    function deleteRow(r, id,project_name,tcName) {
    $.get("${pageContext.request.contextPath}/delTestCase/" + id+"/project_name/"+project_name+"/tcName/"+tcName, function (data, status) {
        alert(data);
        var i = r.parentNode.parentNode.rowIndex
        document.getElementById('testcasetable').deleteRow(i);
    });
    }
    function updateTestCase(tcName, priority, tcId) {
        $.post("${pageContext.request.contextPath}/updateTestCase/tc_name/" + tcName + "/priority/" + priority + "/id/" + tcId, function (data, status) {
        alert(data);
    });
    }
    function runTc(tc_name,project_id,project_name)
    {
        $.post("${pageContext.request.contextPath}/runTestCase/tc_name/" +tc_name+"/project_id/"+project_id+"/project_name/"+project_name, function (data, status) {
        alert(data);
        window.location.reload();
    });
    }
    function suiteRun(project_id,project_name)
    {
        $.post("${pageContext.request.contextPath}/suiteRunTestCase/project_id/"+project_id+"/project_name/"+project_name, function (data, status) {
        alert(data);
    window.location.reload();
    });
    }
    $(function () {
        $("input:button").click(function (e) {
        var obj_name,obj_priority,obj_id,str;
        if ($(this).val() == "编辑") {
        str = "确定"
        } else if ($(this).val() == "确定") {
        str = "编辑"
    } else if ($(this).val() == "运行")
    {
    str="运行"
    }else if ($(this).val() == "批量运行")
    {
    str="批量运行"
    }
    else if ($(this).val() == "删除")
    {
    str="删除"
    }
    $(this).val(str); // 按钮被点击后，在“编辑”和“确定”之间切换
    if($(this).val()!="运行")
    {
    $(this).parent().siblings("td").each(function () { // 获取当前行的其他单元格
    obj_text = $(this).find("input:text"); // 判断单元格下是否有文本框
    if (!obj_text.length && $(this).index() != 0) {// 如果没有文本框，则添加文本框使之可以编辑
    $(this).html("<input type='text' value='" + $(this).text() + "'>");
    }
    else if ($(this).index() ==1 && obj_text.length > 0) {// 如果已经存在文本框，则将其显示为文本框修改的值
    obj_name = $(this).children()[0].value;
    $(this).html("<a href='${pageContext.request.contextPath}/editTestCase/tc_id/"+obj_id+"'>"+obj_name+"</a>");
    }else if(($(this).index() ==2 && obj_text.length > 0) ) {
    obj_priority = parseInt($(this).children()[0].value);
    $(this).html(obj_priority);
    updateTestCase(obj_name, obj_priority, obj_id);
    }else {
    obj_id = parseInt($($(this).parent().children()[0]).text())
    }
    // $(this).html(obj_text.val());
    });}else if($(this).val()=="运行")
    {
    $(this).parent().siblings("td").each(function () {
    if ($(this).index() ==1)
    {
    obj_name = $($($(this).parent()[0]).children()[1]).text();
    runTc(obj_name,${project_id},"${project_name}")
    }
    });
    }

    });
    });
    </script>
    </body>

    </html>
