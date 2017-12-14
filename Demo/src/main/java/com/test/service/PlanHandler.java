package com.test.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.test.model.LoanProductFeeVo;
import com.test.model.Plan;

@Component
public class PlanHandler {
	 @Autowired
	 private FeeHandler handler;
	
	 /*
	  * 
	  * ���ɻ���ƻ�
	  */
	  public List<Plan> genreateRepaymentPlan(int periods, 
			  String loanTime, 
			  String firstRepaymentDay, 
			  double loanAmount,//�����	
			  double rate,
			  double monthRate, 
			  double amount, //��������
			  double compensateFeeRate,
			  double headCompensateFeeRate,
			  int    numInstallmentByFee,//�����ѷ�����
			  double serviceFeeRate,
			  double headServiceFeeRate){
		  //planlist
		  List<Plan> planList=new ArrayList<>();
		  
	    	
	      List<LoanProductFeeVo> loanList = handler.getPrincleAndInterestForTerm(periods, loanTime, firstRepaymentDay, loanAmount,amount, rate, monthRate);
	       //加赔费
          Double compensateFee = handler.ComputeJiaPei(loanAmount, compensateFeeRate, headCompensateFeeRate,periods);
           //手续费
           Double serviceFee = handler.ComputeShouXuFei(loanAmount, serviceFeeRate, headServiceFeeRate, numInstallmentByFee);
	       
		   for(int i=1; i<=periods;i++){ 
			   Plan plan=new Plan();
			   plan.setLoanPeriod(periods);
			   plan.setSum(loanList.get(i-1).getPrincipalAddInterset());
			   plan.setPrincipal(loanList.get(i-1).getPrincipal());
			   plan.setInterest(loanList.get(i-1).getInterset());
			   plan.setCompensateFee(compensateFee);
			   plan.setLoanDays(loanList.get(i-1).getLoanDays()); 
			   plan.setRepaymentDay(loanList.get(i-1).getRepaymentDay());
			   //手续费
			   if(numInstallmentByFee<periods && i>numInstallmentByFee)
			       plan.setServiceFee(0.00); //设置其他期的手续费为0
			   else 
				  plan.setServiceFee(serviceFee);
			  
			   //
			   plan.setAmount(loanList.get(i-1).getPrincipalAddInterset()+compensateFee+serviceFee);
			   
			   planList.add(plan); 
		   }
		  
		  return planList;
	  }
	  
	  
	    //test method
	    public static void main(String [] args){
	    	double loanAmount=100;
	    	double rate=0.0004;
	    	double monthRate=0.012;	
	    	System.out.println(loanAmount+","+rate+","+monthRate);
	    	FeeHandler handler=new FeeHandler();
//	    	System.out.println(handler.getTermPrincipalAndInterest(6, 1000000,0.012));
	    	Date date =new Date("2016/12/20");
	    	Map<String, Object> map = handler.computeDayDiffAmount(20, date);
	    	double amount = handler.generatePerfectDayDiffAmount(loanAmount, Integer.parseInt(map.get("flag").toString()),Integer.parseInt(map.get("chae").toString()) , rate);
	    	
	    	 System.out.println("000000:"+amount);
	    	 
//	    	 List<LoanProductFeeVo> list = handler.getPrincleAndInterestForTerm(6,"2017/01/15","2017/02/15",1000000,1000000,0.0004,0.012);
//	        
//	    	for(LoanProductFeeVo l: list)
//	    	  System.out.println(l.getPrincipalAddInterset()+","+l.getPrincipal()+","+l.getInterset()+","+l.getLoanDays()+","+l.getRepaymentDay());
//	        
	        
	         SimpleDateFormat f=new SimpleDateFormat("yyyy/MM/dd");
	    	PlanHandler planHandler=new PlanHandler();
//             List<Plan> list1 = planHandler.genreateRepaymentPlan(12, "2017/01/15","2017/02/15",1000000,0.0004,0.012, 1000000,0.1,0.20, 6, 0.15, 0.00);
//	        for(Plan l1: list1)
//	    	  System.out.println(l1.getSum()+","+l1.getPrincipal()+","+l1.getInterest()+","+l1.getLoanDays()+","+l1.getRepaymentDay()+","+l1.getCompensateFee()+","+l1.getServiceFee());
//	        
//	        
            List<Plan> list1 = planHandler.genreateRepaymentPlan(6, f.format(date),map.get("firstRapaymentDate").toString(),loanAmount,
            		0.0004,0.012, amount,0.1,0.20, 6, 0.15, 0.00);
	        for(Plan l1: list1)
	    	  System.out.println(l1.getSum()+","+l1.getPrincipal()+","+l1.getInterest()+","+l1.getLoanDays()+","+l1.getRepaymentDay()+","+l1.getCompensateFee()+","+l1.getServiceFee());
	       
	        
	        
	    }

}
