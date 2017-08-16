<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<script type="text/javascript" src="jquery.js"></script>
<script type="text/javascript">
	$(docuemnt).ready(function(){
		$('#loanId').click(function(){
			loanAmount=$("input[name='loanAmount']").val()
			
			$.ajax{
			  url: myUrl,
			  type: 'get',
			  dataType: 'json',
			  data:
			  timeout: 1000,
			  success: function (data, status) {
			    console.log(data)
			  },
			  fail: function (err, status) {
			    console.log(err)
			  }
			}
		});
		
		
	});

</script>
<h2>借款工具</h2>
<form action="/loan/getRepaymentPlan" style="height: 268px; " method="post">
<table>
	<tr>
	  <td><label>借款金额：</label><input name="loanAmount"/></td>
	  <td><label>借款期数：</label><input name="periods" /></td> 
	  <td><label>日利率：</label><input name="rate" /></td>
	  <td><label>月利率：</label><input name="mothRate" /></td>
   </tr>
   <tr>
  <td><label>手续费率：</label><input name="serviceFeeRate" /></td>
    <td><label>砍头手续费率：</label><input name="headServiceFeeRate" /></td>
  <td><label>加赔费率：</label><input name="compensateFeeRate" /></td>
    <td><label>砍头加赔费率：</label><input name="headCompensateFeeRate" /></td>
   </tr> 
   <tr>
	  <td><label>借款日期：</label><input name="loanDate" /></td>
	  <td><label>还款日：</label><input name="repaymentDate" /></td>
	  <td><label>手续费分期期数：</label><input name="numInstallmentByFee" /></td>
  </tr>
<tr>
 <td> <input type="button"  value="提交" id='loanId'/></td>
</tr>
</table>
</form>
<div id="loanplanlist">
   <table class="table table-bordered table-striped" style="">
        <thead>        
        <th><tr>
             <td>本息和</td>
             <td>本金</td>
             <td>利息</td>
             <td>手续费</td>
             <td>加赔费</td>
             <td>应还金额</td>
             <td>借款天数</td>
             <td>还款日期</td>
             <tr>
        </th></thead>
        <c:forEach var="plan" items="${planLists}" >
        <tr>
            <td>${plan.sum}</td>
            <td>${plan.principal}</td>
             <td>${plan.interest}</td>
             <td>${plan.serviceFee}</td>
             <td>${plan.compensateFee}</td>
             <td>${plan.amount}</td>
             <td>${plan.loanDays}</td>
             <td>${plan.repaymentDay}</td>
        </tr>
        </c:forEach>               
    </table>
</div>
</body>
</html>
