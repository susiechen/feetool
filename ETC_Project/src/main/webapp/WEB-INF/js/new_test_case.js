


    var row = 0
    var verifyRow = 0;
    var prow = [];

    function addRow() {
        row++;
        var trObj = document.createElement("tr");
        trObj.id = new Date().getTime();
        trObj.innerHTML = '<td><input type="checkbox" name="CheckboxGroup" id="CheckboxGroup' + row + '">' +
        '复选框</td> <td> <select name="commandmenu" id="commandmenu' + row + '"> ' +
        '<option>open</option><option>滑动</option><option>等待</option><option>清空内容</option><option>输入文本</option><option>指针移动后再点击</option><option>从iframe切回</option> <option>切换到iframe</option><option value="click">点击</option><option >close</option></select></td><td>' +
        '<select name="targetmenu" id="targetmenu' + row + '"><option>xpath</option><option>id</option><option>name</option><option>tagName</option>' +
        '<option>className</option><option>linkText</option> <option>partialLinkText</option><option>cssSelector</option></select></td> ' +
        '<td><input type="text" name="textfield" id="textfield' + row + '"></td><td>' +
        '<input type="text" name="memo" id="memo' + row + '"> <td><input type="checkbox" name="CheckboxGroup2" value="复选框" id="CheckboxGroup2' + row + '">复选框' +
        ' </td></td><td><input type="button" name="add" id="add' + row + '" value="添加"  onclick="addRow()"><input type="button" name="delete" id="delete' + row + '" value="删除" onclick="del(this)"></td>'
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

    function getJsonData() {
        var jsondata = {};
        jsondata["tcname"] = $("#tcname").val();
        jsondata["pid"] = $("#pid").val();
        jsondata["priority"] = parseInt($("#priority").val());
        jsondata["casedescription"] = $("#casedescription").val()
        jsondata["data"] = saveStepData();
        jsondata["result"] = saveResultData();
        return JSON.stringify(jsondata);
    }

    var prow = [];

    function cutRow() {
        prow = [];
        var tableObj = document.getElementById("tb");
        var rows = tableObj.rows.length;
        var j = 0;
        for (var i = 0; i < rows; i++) {
            var aa = document.getElementById('tb').rows[i - j].cells[0].childNodes[0].getAttribute("id");
            if ($("#" + aa).is(':checked')) {
                var m = document.getElementById('tb').rows[i - j].cloneNode(true);
                prow.push(m);
                document.getElementById('tb').removeChild(document.getElementById('tb').rows[i - j]);
                j++;

            }
        }

    }

    function pasteRow() {
        var tableObj = document.getElementById("tb");
        var rows = prow.length;
        for (var i = 0; i < rows; i++) {
            document.getElementById("tb").appendChild(prow[i].cloneNode(true))
        }
    }

    function selectAll() {
        var tableObj = document.getElementById("tb");
        var rows = tableObj.rows.length;
        for (var i = 0; i < rows; i++) {
            var aa = document.getElementById('tb').rows[i].cells[0].childNodes[0].getAttribute("id");
            if ($("#" + aa).is(":checked")) {
                $("#" + aa).prop("checked", true);
            } else {
                $("#" + aa).prop("checked", true);
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
//        if(saveStepData().data.size==0)
//        {
//            alert("操作步骤不能为空哦！");//弹出提示
//            return;
//        }
        if (saveResultData().length == 0) {
            alert("结果验证不能为空哦！");//弹出提示
            return;
        }
        if (saveStepData().length == 0) {
            alert("操作步骤不能为空哦！");//弹出提示
            return;
        }

        $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/saveTestCase.do",
            data: getJsonData(),
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                if (data && data.success == "true") {
                    window.location.href = "javascript:history.go(-1);";
                    alert("save success");
                } else {
                    alert("save error!");
                }
            }
        });

    }

    function copyRow() {
        prow = [];
        var tableObj = document.getElementById("tb");
        var rows = tableObj.rows.length;
        for (var i = 0; i < rows; i++) {
            var aa = document.getElementById('tb').rows[i].cells[0].childNodes[0].getAttribute("id");
            if ($("#" + aa).is(':checked')) {
                var crow = document.getElementById('tb').rows[i].cloneNode(true);
                prow.push(crow);
            }
        }

    }

    function checkText() {//js表单验证方法
        var tcname = document.getElementById("tcname").value;//通过id获取需要验证的表单元素的值
        var pid = document.getElementById("pid").value;//通过id获取需要验证的表单元素的值
        if (tcname == "" || pid == "") {//当上面获取的值为空时
            alert("用例名称和项目ID不能为空哦！");//弹出提示
            return false;//返回false（不提交表单）
        }
        return true;//提交表单
    }

    function checkProjectIsExist() {
        $.post("${pageContext.request.contextPath}/verifyProject/" + $("#pid").val(), function (data, status) {
            if (data == false) {
                alert("项目名称不存在！")
            }
            return data;
        });
    }

    function addVerifyRow() {
        verifyRow++;
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

    function saveResultData() {
        var result = [];
        var tableObj = document.getElementById("vb");
        for (var i = 0; i < tableObj.rows.length; i++) { //遍历Table的所有Row
            var casestep = {};
            for (var j = 0; j < tableObj.rows[i].cells.length - 1; j++) { //遍历Row中的每一列
                var aa = document.getElementById('vb').rows[i].cells[j].childNodes[0].getAttribute("id");
                if (j == 0) {
                    casestep["command"] = $("#" + aa).val();
                } else if (j == 3) {
                    var bb = document.getElementById('vb').rows[i].cells[j].childNodes[1].getAttribute("id");
                    casestep["property"] = $("#" + aa).val();
                    casestep["paramater"] = $("#" + bb).val();
                } else if (j == 1) {
                    casestep["target"] = $("#" + aa).val();
                }
                else if (j == 2) {
                    casestep["elementvalue"] = $("#" + aa).val();
                } else {
                    casestep["expectedvalue"] = $("#" + aa).val();
                }
            }
            result.push(casestep);
        }
        return result;
    }

