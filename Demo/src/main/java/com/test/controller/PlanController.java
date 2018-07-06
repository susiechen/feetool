package com.test.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.test.model.NewPlan;
import com.test.model.NewPlanInfo;
import com.test.model.Plan;
import com.test.model.PlanInfo;
import com.test.service.FeeHandler;
import com.test.service.MapStatus;
import com.test.service.PlanHandler;
import com.test.service.RepaymentHelper;

@Controller
public class PlanController {
	    @Autowired(required=true)
	    private PlanHandler planHandler ; 
	    @Autowired(required=true)
	    private FeeHandler feeHandler ; 
	    @Autowired(required=true)
	    private RepaymentHelper repaymentHelper;
	    
	    @RequestMapping("/mypath")
	    public String test(){
	    	return "success";
	    }
	
    @ResponseBody
	@RequestMapping(value = "/loan/clearRepaymentPlan", method = RequestMethod.POST)
	public String  clearRepaymentPlan(){
    	MapStatus.resetStatus(MapStatus.listRes, 0);
    	return "";
    }
	
	@ResponseBody
	@RequestMapping(value = "/loan/getRepaymentPlan", method = RequestMethod.POST)
	public String  getReypaymentPlan(@RequestBody PlanInfo plan,Model rm){
		
		
		  double loanAmount=plan.getLoanAmount();
		  int periods=plan.getPeriods();
		  double rate=plan.getRate();
		  double monthRate=plan.getMonthRate();
		  double serviceFeeRate=plan.getServiceFeeRate();
		  double headServiceFeeRate=plan.getHeadServiceFeeRate();
		  double compensateFeeRate=plan.getCompensateFeeRate();
		  double headCompensateFeeRate=plan.getHeadCompensateFeeRate();
		  String loanDate=plan.getLoanDate();
		  int repaymentDate=plan.getRepaymentDate();
		  int numInstallmentByFee=plan.getNumInstallmentByFee();
		
		double amount=0;//
		String firstRepaymentDay=null;//
		SimpleDateFormat f=new SimpleDateFormat("yyyy/MM/dd");
		Map<String, Object> map=new HashMap<>();
		try {
			 map = feeHandler.computeDayDiffAmount(repaymentDate, f.parse(loanDate));
			 firstRepaymentDay=map.get("firstRapaymentDate").toString();
			 amount=feeHandler.generatePerfectDayDiffAmount(loanAmount,Integer.parseInt(map.get("flag").toString()) , Integer.parseInt(map.get("chae").toString()), rate);
			 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Plan> list = planHandler.genreateRepaymentPlan(periods, loanDate, firstRepaymentDay, loanAmount, rate, 
				monthRate, amount,//
				compensateFeeRate, headCompensateFeeRate, numInstallmentByFee, 
				serviceFeeRate, headServiceFeeRate);
        //初始化
		MapStatus.init();
        MapStatus.listRes.get(0).status[0] = 1;
        MapStatus.listRes.get(0).plan = list;
        
        //首次初始化数据
        if(MapStatus.listRes.get(0).newPlan==null || MapStatus.listRes.get(0).newPlan.size()==0){
	        for (Plan plan1 : MapStatus.listRes.get(0).plan)
	        {
	        	NewPlan p = plan1.changeToNewPlan();
	            MapStatus.listRes.get(0).newPlan.add(p);
	           
	        }
        }
        
		System.out.println(periods+","+ loanDate+"," +firstRepaymentDay+","+ loanAmount+","+ rate+","+ 
				monthRate+","+ amount+","+compensateFeeRate+","+ headCompensateFeeRate+","+ numInstallmentByFee+","+ 
				serviceFeeRate+","+ headServiceFeeRate);
		rm.addAttribute("planLists",JSON.toJSON(list) );
		
		return JSON.toJSON(list).toString();
    }
	
	
	
	@RequestMapping("/")
	public ModelAndView init(){
		ModelAndView model = new ModelAndView("index");
    	return model;
    }
	
	@RequestMapping("/loan/repaymentLists")
	@ResponseBody
	public  Object repaymentPlan(@RequestBody NewPlanInfo myPlan ,Model rm){
		
		 double repaymentAmt=myPlan.getRepaymentAmt();
		 String repaymentDate=myPlan.getCurrentPaymentDate();
		 int currentPeriod=myPlan.getCurrentPeriod();
		 Date startTime = null;
		try {
			startTime = new SimpleDateFormat("yyyy/MM/dd").parse(myPlan.getLoanDate());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		 double rate=myPlan.getRate();
		 double monthRate=myPlan.getMonthRate();
		 int periods=myPlan.getPeriods();
		 int numInstallmentByFee=myPlan.getNumInstallmentByFee();
		 double faxi=myPlan.getOverdueFee();
		 double cuifei=myPlan.getCollectionFee();
		 Double originalAmt=myPlan.getLoanAmount();
		
		try {
			if(currentPeriod==1)
				repaymentHelper.preRepaymentFirst(repaymentAmt, repaymentDate, currentPeriod, startTime, rate, monthRate, periods, 1, numInstallmentByFee);
			else{
				 if (MapStatus.listRes.get(0).status[currentPeriod - 2] != 1)
	                {
	                    //MessageBox.Show;
	                    return "上期未还清";
	                }

	               
					if (MapStatus.listRes.get(0).faxiStatus[currentPeriod - 2] == 0 && faxi != 0 & cuifei != 0)
	                {
						MapStatus.listRes.get(0).newPlan.get(currentPeriod - 2);
	                    MapStatus.listRes.get(0).newPlan.get(currentPeriod - 2).overdueFee= faxi;
	                    MapStatus.listRes.get(0).newPlan.get(currentPeriod - 2).collectionFee = cuifei;
	                    MapStatus.listRes.get(0).faxiStatus[currentPeriod - 2] = 1;
	                }
				
				repaymentHelper.normalRepayment(repaymentAmt, 
			    		 repaymentDate,originalAmt,startTime,rate, monthRate, periods, currentPeriod, 1,numInstallmentByFee);
			}
				
			List newPlan=null;
			if(currentPeriod==1)
				 newPlan = MapStatus.listRes.get(0).plan;
			else
				newPlan = MapStatus.listRes.get(0).newPlan;
//				rm.addAttribute("paymentplanLists", newPlan);
				
	    	return  JSON.toJSON(newPlan);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	   return null;
	}
	
}



