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
                    <%--<li>
                        <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>TestCase<span class="fa arrow"></span></a>
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
                <h1 class="page-header">TestCase</h1>

                <p align="center" > 新增用例</p>
                <div>
                    <p align="center">用例名称：
                        <label for="tcname"></label>
                        <input type="text" name="tcname" id="tcname">
                        项目ID：
                        <label for="pid"></label>
                        <input type="text" name="textfield2" id="pid" value="${project_id}">
                        优先级：
                        <select name="priority" id="priority">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                        </select>
                    </p>
                </div>
                <p align="center">
                    <input type="button" class="btn btn-primary" name="add" id="addstep" value="添加步骤" onclick="addRow()">
                    <input type="button" class="btn btn-primary" name="copy" id="copy" value="复制" onclick="copyRow()">
                    <input type="button" class="btn btn-primary" name="cut" id="cut" value="剪切" onclick="cutRow()">
                    <input type="button" class="btn btn-primary" name="paster" id="paster" value="粘贴" onclick="pasteRow()">
                    <input type="button" class="btn btn-primary" name="selectall" id="selectall" value="全选" onclick="selectAll()">
                </p>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        TestCase Operate:
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <table width="100%" class="table table-striped table-bordered table-hover"
                               id="dataTables-operate">
                                <thread>
                                    <tr class="info">
                                        <td width="116" height="29">&nbsp;</td>
                                        <td width="116">Commad</td>
                                        <td width="116">Target</td>
                                        <td width="168">Value</td>
                                        <td width="168">Memo</td>
                                        <td width="168">ScreenShot</td>
                                        <td width="169">operate</td>
                                    </tr>
                                </thread>
                                <tbody id="tb">

                                </tbody>
                            </table>




                        <!-- /.table-responsive -->

                    </div>
                    <!-- /.panel-body -->
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        Result Verify:
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-assert">

                            <thread>
                                <tr class="info">
                                    <td width="39">Command</td>
                                    <td width="39">Target</td>
                                    <td width="168">Value</td>
                                    <td width="168">Property</td>
                                    <td width="168">expectedValue</td>
                                    <td width="67">Operate</td>
                                </tr>
                            </thread>
                            <tbody id="vb">
                            <tr id="verifyCommand0">
                                <td><select name="verifyCommand" id="verifyCommand">
                                    <option>AssertTrue</option>
                                    <option>AssertFalse</option>
                                    <option>AssertEqualsBoolean</option>
                                    <option>AssertEqualsByte</option>
                                    <option>AssertEqualsDouble</option>
                                    <option>AssertEqualsString</option>
                                    <option>AssertEqualsLong</option>
                                    <option>AssertEqualsSet</option>
                                    <option>AssertEqualsObject</option>

                                </select></td>
                                <td><select name="verifyTarget" id="verifyTarget">
                                    <option>xpath</option>
                                    <option>id</option>
                                    <option>name</option>
                                    <option>tagName</option>
                                    <option>className</option>
                                    <option>linkText</option>
                                    <option>partialLinkText</option>
                                    <option>cssSelector</option>
                                    <option>list/xpath</option>
                                    <option>list/id</option>
                                    <option>list/name</option>
                                    <option>list/tagName</option>
                                    <option>list/className</option>
                                    <option>list/linkText</option>
                                    <option>list/partialLinkText</option>
                                    <option>list/cssSelector</option>
                                </select></td>
                                <td><input type="text" name="verifyValue" id="verifyValue"></td>
                                <td><select name="verifyPriority" id="verifyPriority">
                                    <option>getText</option>
                                    <option>getAttribute</option>
                                    <option>isDisplayed</option>
                                    <option>isEnabled</option>
                                    <option>getHashCode</option>
                                    <option>toString</option>
                                    <option>isSelected</option>
                                    <option>getSize</option>
                                    <option>getTagName</option>
                                    <option>getCssValue</option>
                                    <option>getRect</option></select><input type="text" name="parameter" id="parameter"></td>
                                <td><input type="text" name="verifyExceptValue" id="verityExceptValue"></td>
                                <td><input type="button" name="verify_add" id="verify_add" value="添加" onClick="addVerifyRow()">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        </div>
                    </div>
                <!-- /.panel -->
                <div>
                    <p align="justify" >

                        <label for="casedescription"></label>
                        用例说明：
                        <textarea name="casedescription" id="casedescription" cols="30" rows="5"></textarea>
                    </p>

                    <p align="right">
                        <input type="button" name="save" class="btn btn-primary" id="save" value="保存" onclick="saveTestCase()">
                        <input type="reset" name="return" class="btn btn-primary" id="return" value="返回">
                    </p>
                </div>
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
<script type="text/javascript" src="/js/new_test_case.js"></script>

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
    $(document).ready(function () {
        $('#dataTables-projects').DataTable({
            responsive: true
        });
    });
</script>



    </body>

    </html>
