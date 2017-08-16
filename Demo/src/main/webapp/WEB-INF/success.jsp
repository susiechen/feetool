<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE HTML>
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>列表信息展示</title>
<link th:href="@{/css/bootstrap.min.css}" rel="stylesheet"/>
</head>
<body>
<div layout:fragment="content" class="container">
    <table class="table table-bordered table-striped">
        <thead>        
        <th>
        <tr>
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

