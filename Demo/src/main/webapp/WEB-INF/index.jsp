<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>

<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#btnLoan').click(function () {
            var arr = {};
            arr.loanAmount = $("input[name='loanAmount']").val();
            arr.periods = $("input[name='periods']").val();
            arr.rate = $("input[name='rate']").val();
            arr.monthRate = $("input[name='monthRate']").val();
            arr.serviceFeeRate = $("input[name='serviceFeeRate']").val();
            arr.headServiceFeeRate = $("input[name='headServiceFeeRate']").val();
            arr.compensateFeeRate = $("input[name='compensateFeeRate']").val();
            arr.headCompensateFeeRate = $("input[name='headCompensateFeeRate']").val();
            arr.loanDate = $("input[name='loanDate']").val();
            arr.repaymentDate = $("input[name='repaymentDate']").val();
            arr.numInstallmentByFee = $("input[name='numInstallmentByFee']").val();
            var req = JSON.stringify(arr);
             console.warn(req);
            $.ajax({
                url: "/loan/getRepaymentPlan",
                type: 'post',
                dataType: 'json',
                data: req,
                contentType: "application/json;charset=UTF-8",
                success: function (data, status) {
                    console.warn(data);
                    var result = ""
                    for (var i = 0; i < data.length; i++) {
                        result = result + getTr(data[i]);
                    }
                    $("#loadthead").nextAll().remove();                    
                    $("#loadthead").after(result)
                },
                fail: function (err, status) {
                    console.log(err)
                }
            });

        });
        
     
        $('#btnRepayment').click(function () {
            var arr = {};
            arr.loanAmount = $("input[name='loanAmount']").val();
            arr.loanDate = $("input[name='loanDate']").val();
            arr.repaymentAmt = $("input[name='repaymentAmt']").val();
            arr.periods = $("input[name='periods']").val();
            arr.rate = $("input[name='rate']").val();
            arr.monthRate = $("input[name='monthRate']").val();
            arr.currentPeriod = $("input[name='currentPeriod']").val();
            arr.currentPaymentDate = $("input[name='currentPaymentDate']").val();
            arr.numInstallmentByFee = $("input[name='numInstallmentByFee']").val();
            arr.overdueFee=$("input[name='overdueFee']").val();
            arr.collectionFee=$("input[name='collectionFee']").val();
            var req = JSON.stringify(arr);
             console.warn(req);
            $.ajax({
                url: "/loan/repaymentLists",
                type: 'post',
                dataType: 'json',
                data: req,
                contentType: "application/json;charset=UTF-8",
                success: function (data, status) {
                    console.warn(data);
                    var result = ""
                    for (var i = 0; i < data.length; i++) {
                        result = result + getTr(data[i]);
                    }
                    $("#paidthead").nextAll().remove();                    
                    $("#paidthead").after(result)
                },
                fail: function (err, status) {
                    console.log(err)
                }
            });

        });
        
        
        $('#btnClear').click(function () {
            $("input[name='loanAmount']").val("");
           	$("input[name='periods']").val("");
            $("input[name='rate']").val("");
            $("input[name='monthRate']").val("");
            $("input[name='serviceFeeRate']").val("");
            $("input[name='headServiceFeeRate']").val("");
            $("input[name='compensateFeeRate']").val("");
            $("input[name='headCompensateFeeRate']").val("");
            $("input[name='loanDate']").val("");
            $("input[name='repaymentDate']").val("");
            $("input[name='numInstallmentByFee']").val("");
            $.ajax({
                url: "/loan/clearRepaymentPlan",
                type: 'post',
                dataType: 'json',
                data: """,
                contentType: "application/json;charset=UTF-8",
                success: function (data, status) {
                   console.warn('清空成功')
                },
                fail: function (err, status) {
                    console.log(err)
                }
            });

        });
        
        
    });


    
    
    function getTr(plan) {
        return "<tr>" + getTd(plan.sum) + getTd(plan.principal) + getTd(plan.interest) + getTd(plan.serviceFee) + getTd(plan.compensateFee) + getTd(plan.amount) + getTd(plan.loanDays) + getTd(plan.repaymentDay) + "</tr>";
    }

    function getTd(value) {
        return "<td>" + value + "</td>";
    }
</script>

<h2 style="" align="center">借款工具</h2>
<form action="/loan/getRepaymentPlan" style="height: 268px; " method="post">
    <table align = "center" border="0" cellpadding="0" cellspacing="20"  style="text-align: right;" >
        <tr>
            <td><label>借款金额：</label><input name="loanAmount" value="150000"/></td>
            <td><label>借款期数：</label><input name="periods" value="3"/></td>
            <td><label>日利率：</label><input name="rate" value="0.0004"/></td>
            <td><label>月利率：</label><input name="monthRate" value="0.012"/></td>
        </tr>
        <tr>
            <td><label>手续费率：</label><input name="serviceFeeRate" value="0.15"/></td>
            <td><label>砍头手续费率：</label><input name="headServiceFeeRate" value="0"/></td>
            <td><label>加赔费率：</label><input name="compensateFeeRate" value="0.1"/></td>
            <td><label>砍头加赔费率：</label><input name="headCompensateFeeRate" value="0"/></td>
        </tr>
        <tr>
            <td><label>借款日期：</label><input name="loanDate" value="2016/12/20"/></td>
            <td><label>还款日：</label><input name="repaymentDate" value="15"/></td>
            <td><label>手续费分期期数：</label><input name="numInstallmentByFee" value="3"/></td>
            <td><input type="button" value="借款" id='btnLoan'/> <input type="button" value="重置" id='btnClear'/></td>
        </tr>
        <tr>
           
        </tr> 
         <tr>
            <td><label>还款日期：</label><input name="currentPaymentDate" value="2016/12/21"/></td>
            <td><label>还款金额：</label><input name="repaymentAmt" value="15"/></td>
            <td><label>期数：</label><input name="currentPeriod" value="1"/></td>
            <td><label>罚息：</label><input name="overdueFee" value="3"/></td>
            
        </tr>
        <tr>
        	<td><label>催收：</label><input name="collectionFee" value="1000"/></td>
			<td></td>
			<td></td>
            <td><input type="button" value="还款" id='btnRepayment'/></td>
        </tr> 
    </table>
</form>
<div  style="float: left; width:100%">
    <table id="loanplanlist" class="table table-bordered table-striped" align="left" border="solid 1px #ccc" 
	 cellspacing="0" cellpadding="0" style="float: left; width:40%; margin-left:20px;" >
        <thead id="loadthead">
            <tr>
                <td>本息和</td>
                <td>本金</td>
                <td>利息</td>
                <td>手续费</td>
                <td>加赔费</td>
                <td>应还金额</td>
                <td>借款天数</td>
                <td>还款日期</td>
            </tr>
        </thead>
    </table>
    <table id="repaymentplanlist" class="table table-bordered table-striped" align="right" border="solid 1px #ccc" cellspacing="0" 
		cellpadding="0" style="float: left; width:50%;margin-left:15px;" >
        <thead id="paidthead">
            <tr>
                <td>本息和</td>
                <td>本金</td>
                <td>利息</td>
                <td>手续费</td>
                <td>加赔费</td>
                <td>应还金额</td>
                <td>借款天数</td>
                <td>还款日期</td>
                <td>罚息</td>
                <td>催收</td>
            </tr>
        </thead>
    </table> 
</div>
</body>
</html>
