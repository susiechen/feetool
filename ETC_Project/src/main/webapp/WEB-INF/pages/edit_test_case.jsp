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
                    <%--
                    <li>
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

                <p align="center" >用例编辑</p>
                <div>
                <p align="center">用例名称：
                    <label for="tcname" ></label>

                    <input type="text" name="tcname" id="tcname"readonly="true" style="border-style:none" >
                    项目ID：
                    <label for="pid"></label>
                    <input type="text" name="textfield2" id="pid" readonly="true" style="border-style:none">
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

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
    $(document).ready(function () {
        $('#dataTables-projects').DataTable({
            responsive: true
        });
    });
</script>
<script type="text/javascript">
    var jsonobj=JSON.parse('${caseInfo}');
    $("#tcname").val(jsonobj.tcname);
    $("#pid").val(jsonobj.pid);
    $("#casedescription").val(jsonobj.casedescription);
    $("#priority").val(jsonobj.priority);
    for(row=0;row<jsonobj.data.length;row++)
    {
        var trObj = document.createElement("tr");
        trObj.id = new Date().getTime();
        trObj.innerHTML ='<td><input type="checkbox" name="CheckboxGroup" id="CheckboxGroup'+row+'">' +
        '复选框</td> <td> <select name="commandmenu" id="commandmenu'+row+'"> ' +
        '<option>open</option><option>滑动</option><option>等待</option><option>清空内容</option><option>输入文本</option><option>指针移动后再点击</option><option>从iframe切回</option> <option>切换到iframe</option><option value="click">点击</option><option >close</option></select></td><td>' +
        '<select name="targetmenu" id="targetmenu'+row+'"><option>xpath</option><option>id</option><option>name</option><option>tagName</option>' +
        '<option>className</option><option>linkText</option> <option>partialLinkText</option><option>cssSelector</option></select></td> ' +
        '<td><input type="text" name="textfield" id="textfield'+row+'"></td><td>' +
        '<input type="text" name="memo" id="meno'+row+'"> <td><input type="checkbox" name="CheckboxGroup2" value="复选框" id="CheckboxGroup2'+row+'">复选框' +
        ' </td></td><td><input type="button" name="add" id="add'+row+'" value="添加"  onclick="addRow()"><input type="button" name="delete" id="delete'+row+'" value="删除" onclick="del(this)"></td>'
        document.getElementById("tb").appendChild(trObj);
        var command="commandmenu"+row;var target="targetmenu"+row;var elementvalue="textfield"+row;
        var meno="meno"+row;var screamshot="CheckboxGroup2"+row;
        $("#"+command).val(jsonobj.data[row].command);
        $("#"+target).val(jsonobj.data[row].target);
        $("#"+elementvalue).val(jsonobj.data[row].elementvalue);
        $("#"+meno).val(jsonobj.data[row].meno);
        $("#"+screamshot).attr("checked",jsonobj.data[row].screamshot);
    }
    for(verifyRow=0;verifyRow<jsonobj.result.length;verifyRow++)
    {
        if(verifyRow==0)
        {
            $("#verifyCommand").val(jsonobj.result[verifyRow].command);
            $("#verifyTarget").val(jsonobj.result[verifyRow].target);
            $("#verifyValue").val(jsonobj.result[verifyRow].elementvalue);
            $("#verifyPriority").val(jsonobj.result[verifyRow].property);
            $("#parameter").val(jsonobj.result[verifyRow].paramater);
            $("#verityExceptValue").val(jsonobj.result[verifyRow].expectedvalue);
        }else {
            var trObj = document.createElement("tr");
            trObj.id = new Date().getTime();
            trObj.innerHTML = '<td><select name="verifyCommand" id="verifyCommand' + verifyRow + '"><option>AssertFalse</option><option>AssertTrue</option><option>AssertEqualsBoolean</option>' +
            '<option>AssertEqualsByte</option><option>AssertEqualsDouble</option><option>AssertEqualsString</option>' +
            '<option>AssertEqualsLong</option><option>AssertEqualsSet</option><option>AssertEqualsArrarObject</option></select></td><td>' +
            '<select name="verifyTarget" id="verifyTarget' + verifyRow + '"><option>xpath</option> <option>id</option> <option>name</option> <option>tagName</option>' +
            '<option>className</option> <option>linkText</option><option>partialLinkText</option> <option>cssSelector</option><option>list/xpath</option><option>list/id</option> ' +
            '<option>list/name</option><option>list/tagName</option> <option>list/className</option><option>list/linkText</option>' +
            '<option>list/partialLinkText</option> <option>list/cssSelector</option></select></td><td>' +
            '<input type="text" name="verifyValue" id="verifyValue' + verifyRow + '"></td><td><select name="verifyPriority" id="verifyPriority' + verifyRow + '">' +
            '<option>getText</option><option>getAttribute</option> <option>isDisplayed</option><option>isEnabled</option> ' +
            ' <option>getHashCode</option><option>toString</option><option>isSelected</option><option>getSize</option><option>getTagName</option><option>getCssValue</option>' +
            '<option>getRect</option></select><input type="text" name="paramter" id="parameter' + verifyRow + '"></td>' +
            '<td><input type="text" name="verifyExceptValue" id="verifyExceptValue' + verifyRow + '"></td>' +
            '<td><input type="button" name="verify_add" id="verify_add' + verifyRow + '" value="添加" onClick="addVerifyRow()"> ' +
            '<input type="button" name="verify_delete" id="verify_delete' + verifyRow + '" value="移除" onClick="verifydel(this)"></td>'
            document.getElementById("vb").appendChild(trObj);
            var command = "verifyCommand" + verifyRow;
            var target = "varifyTarget" + verifyRow;
            var elementvalue = "verifyValue" + verifyRow;
            var property = "verifyPriority" + verifyRow;
            var paramater = "parameter" + verifyRow;
            var expectedvalue = "verifyExceptValue" + verifyRow;
            $("#" + command).val(jsonobj.result[verifyRow].command);
            $("#" + target).val(jsonobj.result[verifyRow].target);
            $("#" + elementvalue).val(jsonobj.result[verifyRow].elementvalue);
            $("#" + property).val(jsonobj.result[verifyRow].property);
            $("#" + paramater).val(jsonobj.result[verifyRow].paramater);
            $("#" + expectedvalue).val(jsonobj.result[verifyRow].expectedvalue);
        }
    }
    var row = 0
    var verifyRow=0;
    var prow=[];
    function addRow() {
        row++;
        var trObj = document.createElement("tr");
        trObj.id = new Date().getTime();
        trObj.innerHTML ='<td><input type="checkbox" name="CheckboxGroup" id="CheckboxGroup'+row+'">' +
        '复选框</td> <td> <select name="commandmenu" id="commandmenu'+row+'"> ' +
        '<option>open</option><option>滑动</option><option>等待</option><option>清空内容</option><option>输入文本</option><option>指针移动后再点击</option><option>从iframe切回</option> <option>切换到iframe</option><option value="click">点击</option><option >close</option></select></td><td>' +
        '<select name="targetmenu" id="targetmenu'+row+'"><option>xpath</option><option>id</option><option>name</option><option>tagName</option>' +
        '<option>className</option><option>linkText</option> <option>partialLinkText</option><option>cssSelector</option></select></td> ' +
        '<td><input type="text" name="textfield" id="textfield'+row+'"></td><td>' +
        '<input type="text" name="memo" id="memo'+row+'"> <td><input type="checkbox" name="CheckboxGroup2" value="复选框" id="CheckboxGroup2'+row+'">复选框' +
        ' </td></td><td><input type="button" name="add" id="add'+row+'" value="添加"  onclick="addRow()"><input type="button" name="delete" id="delete'+row+'" value="删除" onclick="del(this)"></td>'
        document.getElementById("tb").appendChild(trObj);
    }
    function del(obj) {
        var trObj = obj.parentNode.parentNode
        document.getElementById("tb").removeChild(trObj);
    }
    function verifydel(obj) {
        var trObj = obj.parentNode.parentNode
        document.getElementById("vb").removeChild(trObj);
    }
    function getJsonData()
    {
        var jsondata={};
        jsondata["tcname"]=$("#tcname").val();
        jsondata["pid"]=$("#pid").val();
        jsondata["priority"]=parseInt($("#priority").val());
        jsondata["casedescription"]=$("#casedescription").val()
        jsondata["data"]=saveStepData();
        jsondata["result"]=saveResultData();
        return  JSON.stringify(jsondata);
    }
    var prow=[] ;
    function cutRow()
    {
        prow=[];
        var tableObj=document.getElementById("tb");
        var rows= tableObj.rows.length;
        var j=0;
        for (var i = 0; i < rows; i++)
        {
            var aa=document.getElementById('tb').rows[i-j].cells[0].childNodes[0].getAttribute("id");
            if($("#" + aa).is(':checked'))
            {
                var m=document.getElementById('tb').rows[i-j].cloneNode(true);
                prow.push(m);
                document.getElementById('tb').removeChild(document.getElementById('tb').rows[i-j]);
                j++;

            }
        }

    }
    function pasteRow()
    {
        var tableObj=document.getElementById("tb");
        var rows= prow.length;
        for (var i = 0; i < rows; i++)
        {
            document.getElementById("tb").appendChild(prow[i].cloneNode(true))
        }
    }
    function selectAll()
    {   var tableObj=document.getElementById("tb");
        var rows= tableObj.rows.length;
        for(var i=0;i<rows;i++)
        {
            var aa=document.getElementById('tb').rows[i].cells[0].childNodes[0].getAttribute("id");
            if ($("#"+aa).is(":checked")) {
                $("#"+aa).prop("checked", true);
            } else {
                $("#"+aa).prop("checked", true);
            }
        }

    }
    function saveTestCase() {

        if (checkText() == false) {
            return;
        }
        ;
        if (checkProjectIsExist() == false) {
            return;
        }

        if(saveResultData().length==0)
        {
            alert("结果验证不能为空哦！");//弹出提示
            return;
        }
        if(saveStepData().length==0)
        {
            alert("操作步骤不能为空哦！");//弹出提示
            return;
        }

        $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/saveEditTestCase.do",
            data: getJsonData(),
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                if (data && data.success == "true")
                {
                    window.location.href="javascript:history.go(-1);";
                    alert("save success");
                } else {
                    alert("save error!");
                }
            }
        });

    }

    function copyRow()
    {
        prow=[];
        var tableObj=document.getElementById("tb");
        var rows= tableObj.rows.length;
        for (var i = 0; i < rows; i++)
        {
            var aa=document.getElementById('tb').rows[i].cells[0].childNodes[0].getAttribute("id");
            if($("#" + aa).is(':checked'))
            {
                var crow=document.getElementById('tb').rows[i].cloneNode(true);
                prow.push(crow);
            }
        }

    }
    function checkText() {//js表单验证方法
        var tcname = document.getElementById("tcname").value;//通过id获取需要验证的表单元素的值
        var pid = document.getElementById("pid").value;//通过id获取需要验证的表单元素的值
        if (tcname == "" || pid == "") {//当上面获取的值为空时
            alert("用例名称和项目名称不能为空哦！");//弹出提示
            return false;//返回false（不提交表单）
        }
        return true;//提交表单
    }
    function checkProjectIsExist() {
        $.post("${pageContext.request.contextPath}/verifyProject/"+$("#pid").val(), function (data, status) {
            if(data ==false)
            {
                alert("项目名称不存在！")
            }
            return data;
        });
    }
    function addVerifyRow()
    {
        verifyRow++;
        var trObj=document.createElement("tr");
        trObj.id = new Date().getTime();
        trObj.innerHTML='<td><select name="verifyCommand" id="verifyCommand'+verifyRow+'"><option>AssertFalse</option><option>AssertTrue</option><option>AssertEqualsBoolean</option>' +
        '<option>AssertEqualsByte</option><option>AssertEqualsDouble</option><option>AssertEqualsString</option>' +
        '<option>AssertEqualsLong</option><option>AssertEqualsSet</option><option>AssertEqualsArrarObject</option></select></td><td>' +
        '<select name="verifyTarget" id="verifyTarget'+verifyRow+'"><option>xpath</option> <option>id</option> <option>name</option> <option>tagName</option>' +
        '<option>className</option> <option>linkText</option><option>partialLinkText</option> <option>cssSelector</option><option>list/xpath</option><option>list/id</option> ' +
        '<option>list/name</option><option>list/tagName</option> <option>list/className</option><option>list/linkText</option>' +
        '<option>list/partialLinkText</option> <option>list/cssSelector</option></select></td><td>' +
        '<input type="text" name="verifyValue" id="verifyValue'+verifyRow+'"></td><td><select name="verifyPriority" id="verifyPriority'+verifyRow+'">' +
        '<option>getText</option><option>getAttribute</option> <option>isDisplayed</option><option>isEnabled</option> ' +
        ' <option>getHashCode</option><option>toString</option><option>isSelected</option><option>getSize</option><option>getTagName</option><option>getCssValue</option>' +
        '<option>getRect</option></select><input type="text" name="paramter" id="parameter'+verifyRow+'"></td>' +
        '<td><input type="text" name="verifyExceptValue" id="verifyExceptValue'+verifyRow+'"></td>' +
        '<td><input type="button" name="verify_add" id="verify_add'+verifyRow+'" value="添加" onClick="addVerifyRow()"> ' +
        '<input type="button" name="verify_delete" id="verify_delete'+verifyRow+'" value="移除" onClick="verifydel(this)"></td>'
        document.getElementById("vb").appendChild(trObj);
    }

    function saveStepData() {
        var data = [];
        var tableObj = document.getElementById("tb");
        for (var i = 0; i < tableObj.rows.length; i++) { //遍历Table的所有Row
            var casestep = {};
            for (var j = 1; j < tableObj.rows[i].cells.length - 1; j++) { //遍历Row中的每一列
                if (j == 1) {
                    var aa = document.getElementById('tb').rows[i].cells[j].childNodes[0].nextSibling.getAttribute("id");
                    casestep["command"] = $("#" + aa).val();
                } else {
                    var aa = document.getElementById('tb').rows[i].cells[j].childNodes[0].getAttribute("id");
                    if (j == 4) {
                        casestep["meno"] = $("#" + aa).val();
                    } else if (j == 2) {
                        casestep["target"] = $("#" + aa).val();
                    }
                    else if (j == 3) {
                        casestep["elementvalue"] = $("#" + aa).val();
                    } else {
                        casestep["screamshot"] = $("#" + aa).is(':checked');
                    }
                }
            }
            data.push(casestep);
        }
        return data;
    }
    function saveResultData()
    {
        var result = [];
        var tableObj=document.getElementById("vb");
        for (var i = 0; i < tableObj.rows.length; i++)
        { //遍历Table的所有Row
            var casestep={};
            for (var j = 0; j < tableObj.rows[i].cells.length-1; j++)
            { //遍历Row中的每一列
                var aa=document.getElementById('vb').rows[i].cells[j].childNodes[0].getAttribute("id");
                if(j==0)
                {
                    casestep["command"]=$("#" + aa).val();
                }else if(j==3)
                {
                    var bb=document.getElementById('vb').rows[i].cells[j].childNodes[1].getAttribute("id");
                    casestep["property"]=$("#" + aa).val();
                    casestep["paramater"]=$("#"+bb).val();
                }else if (j==1)
                {
                    casestep["target"]=$("#" + aa).val();
                }
                else if (j==2)
                {
                    casestep["elementvalue"]=$("#" + aa).val();
                }else
                {
                    casestep["expectedvalue"]=$("#" + aa).val();
                }
            }
            result.push(casestep);
        }
        return result;
    }
</script>



    </body>

    </html>
