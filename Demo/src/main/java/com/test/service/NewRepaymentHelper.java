package com.test.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.model.Plan;
//还款操作类
@Service
public class NewRepaymentHelper {
	
	@Autowired
	private static Helper helper;
	
    /**
     * 非首期还款
     * @param repaymentAmt
     * @param repaymentDate
     * @param originalAmt
     * @param startTime
     * @param rililv
     * @param yuelilv
     * @param fenqi
     * @param huankuanqishu
     * @param index
     * @param numInstallmentByFee
     * @throws ParseException
     */
    public static void normalRepayment(Double repaymentAmt, 
    		String repaymentDate, 
    		Double originalAmt,
    		Date startTime,
    		Double rililv, 
    		Double yuelilv,int fenqi,int huankuanqishu,int index,
    		int numInstallmentByFee) throws ParseException//手续费的分期期数
    {
    	Double mustRepaymentAmt = 0.00;
        Double shouldRepaymentAmt = 0.00;
        int preDays = 0;
        MapStatus.listRes.get(index - 1).paymentList.add(repaymentDate);

//        //如果逾期，计算逾期应还的金额
//        Double yuqiyinghuan = MapStatus.listRes.get(index - 1).newPlan[huankuanqishu - 2].ze + 
//        		MapStatus.listRes[index - 1].newPlan[huankuanqishu - 2].faxi + 
//        		MapStatus.listRes[index - 1].newPlan[huankuanqishu - 2].cuifei;

        //确认本金逾期或者利息逾期
        if (huankuanqishu>2 && MapStatus.listRes.get(index - 1).newPlan.get(huankuanqishu - 2).overdueFee>0.00 &&
        		//还款日期大于当期账单日
        		MapStatus.listRes.get(index - 1).newPlan.get(huankuanqishu - 2).collectionFee>0.00 &&
        		MapStatus.listRes.get(index - 1).isBehindOrOutOfDue == 0)
        {
        	//逾期还款
        	//overDueRepayment( repaymentAmt, repaymentDate, originalAmt,startTime,rililv, yuelilv, fenqi, huankuanqishu, index,numInstallmentByFee);
        }//逾期还清，进入下一期还款
        else{
        	
        }
    	
    }
     //正常还款
     public void baseRepayment(Double repaymentAmt, 
     		String repaymentDate, 
     		Double originalAmt,
     		Date startTime,
     		Double rililv, 
     		Double yuelilv,int fenqi,int huankuanqishu,int index,
     		int numInstallmentByFee) throws ParseException{
    	 
    	 Double mustRepaymentAmt =  0.00;
         Double shouldRepaymentAmt = 0.00;
         int preDays = 0;
         //MapStatus.listRes.get(index-1).paymentList.add(repaymentDate);
        
         //本期之外的剩余加赔
         Double shengYuJiaPei = 0.00;
         //本期之外的剩余本金
         Double shengYuBenJin = 0.00;
         //所有的剩余本金
         Double AllshengYuBenJin = 0.00;
         //本期之外的未还手续费
         Double AllshengYuShouXuFei = 0.00;
         for (int i = 1; i < MapStatus.listRes.get(index-1).newPlan.size();i++ )
         {
         	//本期之外的剩余加赔
             shengYuJiaPei += MapStatus.listRes.get(index-1).plan.get(i).getCompensateFee();
             //本期之外的剩余本金
             shengYuBenJin+= MapStatus.listRes.get(index-1).plan.get(i).getPrincipal();
             //所有的剩余本金
             AllshengYuBenJin+= MapStatus.listRes.get(index-1).plan.get(i).getPrincipal();
             //本期之外的未还手续费
             AllshengYuShouXuFei+= MapStatus.listRes.get(index-1).plan.get(i).getServiceFee();
         }

    	 
         Double currentLx = 0.00;
         SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
         //还款日还款
         if (helper.dateDiff(f.parse(repaymentDate), f.parse(MapStatus.listRes.get(index-1).plan.get(0).repaymentDay)) == 0)
         {
             currentLx = MapStatus.listRes.get(index-1).plan.get(0).interest;
         }
         else 
         {
             currentLx = helper.getSiSheWuBuRu(AllshengYuBenJin * rililv * preDays);
         }

         //记录支付利息，用来汇总
         Double paidlixi = currentLx;

         //计算应还和必还
         shouldRepaymentAmt = currentLx + MapStatus.listRes.get(index-1).plan.get(0).compensateFee + MapStatus.listRes.get(index-1).plan.get(0).principal + MapStatus.listRes.get(index-1).plan.get(0).serviceFee;
         mustRepaymentAmt = currentLx + MapStatus.listRes.get(index-1).plan.get(0).compensateFee + MapStatus.listRes.get(index-1).plan.get(0).serviceFee;

         if (repaymentAmt >= MapStatus.listRes.get(index-1).plan.get(0).amount)
         {
             //标记为置后利息
             MapStatus.listRes.get(index-1).isBehindOrOutOfDue = 1;
         }
         else
         {
             //标记为逾期利息
             MapStatus.listRes.get(index-1).isBehindOrOutOfDue = 0;
         }


         //首期还款日前提前还款
         //if (f.parse(repaymentDate) <= f.parse(MapStatus.listRes.get(index-1).plan.get(0).repaymentDay))
         if(f.parse(repaymentDate).compareTo(f.parse(MapStatus.listRes.get(index-1).plan.get(0).repaymentDay))<=0)
         {
         	
             if (repaymentAmt < mustRepaymentAmt)
             {
                System.out.println("还款金额小于必还项");
                 return;
             }


             MapStatus.listRes.get(index-1).flag = MapStatus.listRes.get(index-1).flag + 1;

             //重置上次还款时间
             MapStatus.listRes.get(index-1).lastRepaymentDate = repaymentDate;

             //还款金额等于必还项
             if (repaymentAmt == mustRepaymentAmt)
             {               
             //plan.get(0).principal = 0;
                 MapStatus.listRes.get(index-1).plan.get(0).loanDays = MapStatus.listRes.get(index-1).plan.get(0).loanDays - preDays;
                 MapStatus.listRes.get(index-1).plan.get(0).interest = MapStatus.listRes.get(index-1).plan.get(0).interest - currentLx;
                 MapStatus.listRes.get(index-1).plan.get(0).sum = MapStatus.listRes.get(index-1).plan.get(0).principal + MapStatus.listRes.get(index-1).plan.get(0).interest;
                 MapStatus.listRes.get(index-1).plan.get(0).compensateFee = 0.00;
                 MapStatus.listRes.get(index-1).plan.get(0).serviceFee = 0.00;
                 MapStatus.listRes.get(index-1).plan.get(0).amount = MapStatus.listRes.get(index-1).plan.get(0).sum ;
                 		//MapStatus.listRes.get(index-1).plan.get(0).compensateFee + MapStatus.listRes.get(index-1).plan.get(0).serviceFee;
                 
                 
             }
             //
             else if ((repaymentAmt.compareTo(mustRepaymentAmt)>0) && 
             		((Double)(repaymentAmt-mustRepaymentAmt)).compareTo(MapStatus.listRes.get(index-1).plan.get(0).principal)<=0)
             {
                 //还款金额大于必还项，超出部分不大于本期本金
                 Double shenyubenj = 0.00;

                 MapStatus.listRes.get(index-1).plan.get(0).principal = MapStatus.listRes.get(index-1).plan.get(0).principal - (repaymentAmt - mustRepaymentAmt);
                 //更新剩余所有未还本金
                 for (Plan pl : MapStatus.listRes.get(index-1).plan)
                 {
                     shenyubenj += pl.principal;
                 }
                 MapStatus.listRes.get(index-1).plan.get(0).loanDays = MapStatus.listRes.get(index-1).plan.get(0).loanDays - preDays;
                 MapStatus.listRes.get(index-1).plan.get(0).interest = helper.getSiSheWuBuRu(shenyubenj * MapStatus.listRes.get(index-1).plan.get(0).loanDays * rililv);
                 MapStatus.listRes.get(index-1).plan.get(0).sum = MapStatus.listRes.get(index-1).plan.get(0).principal + MapStatus.listRes.get(index-1).plan.get(0).interest;
                 MapStatus.listRes.get(index-1).plan.get(0).compensateFee = 0.00;
                 MapStatus.listRes.get(index-1).plan.get(0).serviceFee = 0.00;
                 MapStatus.listRes.get(index-1).plan.get(0).amount = MapStatus.listRes.get(index-1).plan.get(0).principal + MapStatus.listRes.get(index-1).plan.get(0).compensateFee + MapStatus.listRes.get(index-1).plan.get(0).serviceFee;
                 
                 
             }
             else if (repaymentAmt > mustRepaymentAmt 
             		&& (repaymentAmt - mustRepaymentAmt) > MapStatus.listRes.get(index-1).plan.get(0).principal
             		&& (repaymentAmt - mustRepaymentAmt - MapStatus.listRes.get(index-1).plan.get(0).principal) <= AllshengYuShouXuFei)
             {
                 //还款金额大于应还项，超出部分小于剩余各期总手续费
                 //计算多余部分
                 Double outOfShould = repaymentAmt - mustRepaymentAmt - MapStatus.listRes.get(index-1).plan.get(0).principal;

                 MapStatus.listRes.get(index-1).plan.get(0).principal = 0.00;
                 Double shenyubenj = 0.00;
                 //更新剩余所有未还本金
                 for (Plan pl : MapStatus.listRes.get(index-1).plan)
                 {
                     shenyubenj += pl.principal;
                 }
                 //Double tmplx = plan.get(0).interest - currentLx;
                 MapStatus.listRes.get(index-1).plan.get(0).loanDays = MapStatus.listRes.get(index-1).plan.get(0).loanDays - preDays;
                 MapStatus.listRes.get(index-1).plan.get(0).interest = helper.getSiSheWuBuRu(shenyubenj * MapStatus.listRes.get(index-1).plan.get(0).loanDays * rililv);
                 MapStatus.listRes.get(index-1).plan.get(0).sum = MapStatus.listRes.get(index-1).plan.get(0).interest;
                 MapStatus.listRes.get(index-1).plan.get(0).amount = MapStatus.listRes.get(index-1).plan.get(0).interest;
                 MapStatus.listRes.get(index-1).plan.get(0).compensateFee = 0.00;
                 MapStatus.listRes.get(index-1).plan.get(0).serviceFee = 0.00;


                 //多余部分还了剩余的总手续费，后面两期还款计划改变
                 Double tmp = (double) Math.round((AllshengYuShouXuFei - outOfShould) / (numInstallmentByFee - 1));
                 for (int i = 1; i < numInstallmentByFee; i++)
                 {
                     Double shouxufeilast = 0.00;
                     if (i + 1 == numInstallmentByFee)
                     {
                         for (int j = 0; j < (numInstallmentByFee - 1); j++)
                         {
                             shouxufeilast += MapStatus.listRes.get(index-1).plan.get(j).serviceFee;
                         }
                         MapStatus.listRes.get(index-1).plan.get(i).serviceFee = AllshengYuShouXuFei - outOfShould - shouxufeilast;
                         MapStatus.listRes.get(index-1).plan.get(i).amount = MapStatus.listRes.get(index-1).plan.get(i).principal + MapStatus.listRes.get(index-1).plan.get(i).compensateFee + MapStatus.listRes.get(index-1).plan.get(i).serviceFee;
                     }
                     else
                     {
                         MapStatus.listRes.get(index-1).plan.get(i).serviceFee = tmp;
                         MapStatus.listRes.get(index-1).plan.get(i).amount = MapStatus.listRes.get(index-1).plan.get(i).principal + MapStatus.listRes.get(index-1).plan.get(i).compensateFee + MapStatus.listRes.get(index-1).plan.get(i).serviceFee;
                     }
                 }
             }
             else if (repaymentAmt > mustRepaymentAmt &&
                 (repaymentAmt - mustRepaymentAmt) > MapStatus.listRes.get(index-1).plan.get(0).principal &&
                 (repaymentAmt - mustRepaymentAmt - MapStatus.listRes.get(index-1).plan.get(0).principal) > AllshengYuShouXuFei &&
                 (repaymentAmt - mustRepaymentAmt - MapStatus.listRes.get(index-1).plan.get(0).principal - AllshengYuShouXuFei) <= shengYuJiaPei)
             {
                 //还款金额大于应还项，超出部分小于剩余各期总加赔
                 //计算多余部分
                 Double outOfShould = repaymentAmt - mustRepaymentAmt - MapStatus.listRes.get(index-1).plan.get(0).principal;

                 MapStatus.listRes.get(index-1).plan.get(0).principal = 0.00;
                 Double shenyubenj = (double) 0;
                 //更新剩余所有未还本金
                 for (Plan pl : MapStatus.listRes.get(index-1).plan)
                 {
                     shenyubenj += pl.principal;
                 }
                 //Double tmplx = plan.get(0).interest - currentLx;
                 MapStatus.listRes.get(index-1).plan.get(0).loanDays = MapStatus.listRes.get(index-1).plan.get(0).loanDays - preDays;
                 MapStatus.listRes.get(index-1).plan.get(0).interest = helper.getSiSheWuBuRu(shenyubenj * MapStatus.listRes.get(index-1).plan.get(0).loanDays * rililv);
                 MapStatus.listRes.get(index-1).plan.get(0).sum = MapStatus.listRes.get(index-1).plan.get(0).interest;
                 MapStatus.listRes.get(index-1).plan.get(0).amount = MapStatus.listRes.get(index-1).plan.get(0).interest;
                 MapStatus.listRes.get(index-1).plan.get(0).compensateFee =  0.00;
                 MapStatus.listRes.get(index-1).plan.get(0).serviceFee =  0.00;
                 

                 //多余部分还了剩余的总加赔，后面各期还款计划改变
                 Double tmp = (double) Math.round((shengYuJiaPei-outOfShould)/(fenqi-1));
                  for (int i = 1;i<fenqi;i++)
                  {
                      Double jiapeilast = (double) 0;
                      if (i + 1 == fenqi)
                      {
                          for (int j = 0; j < (fenqi - 1); j++)
                          {
                              jiapeilast += MapStatus.listRes.get(index-1).plan.get(j).compensateFee;
                          }
                          MapStatus.listRes.get(index-1).plan.get(i).compensateFee = shengYuJiaPei - outOfShould - jiapeilast;
                          MapStatus.listRes.get(index-1).plan.get(i).serviceFee = 0.00;
                          MapStatus.listRes.get(index-1).plan.get(i).amount = MapStatus.listRes.get(index-1).plan.get(i).principal + MapStatus.listRes.get(index-1).plan.get(i).compensateFee + MapStatus.listRes.get(index-1).plan.get(i).serviceFee;
                      }
                      else
                      {
                          MapStatus.listRes.get(index-1).plan.get(i).compensateFee = tmp;
                          MapStatus.listRes.get(index-1).plan.get(i).serviceFee = 0.00;
                          MapStatus.listRes.get(index-1).plan.get(i).amount = MapStatus.listRes.get(index-1).plan.get(i).principal + MapStatus.listRes.get(index-1).plan.get(i).compensateFee + MapStatus.listRes.get(index-1).plan.get(i).serviceFee;
                      }
                  }
             }
             else if (repaymentAmt > mustRepaymentAmt &&
                 (repaymentAmt - mustRepaymentAmt) > MapStatus.listRes.get(index-1).plan.get(0).principal &&
                 (repaymentAmt - mustRepaymentAmt - MapStatus.listRes.get(index-1).plan.get(0).principal) > AllshengYuShouXuFei &&
                 (repaymentAmt - mustRepaymentAmt - MapStatus.listRes.get(index-1).plan.get(0).principal - AllshengYuShouXuFei) > shengYuJiaPei &&
                 (repaymentAmt - mustRepaymentAmt - MapStatus.listRes.get(index-1).plan.get(0).principal - AllshengYuShouXuFei-shengYuJiaPei) <= shengYuBenJin)
             {
                 //还款金额大于应还项+剩余各期总手续费+剩余各期总加赔，超出部分小于剩余总的本金
                 //int tmpDay = MainWindow.DateDiff(f.parse(repaymentDate),f.parse(plan.get(0).hkr));
                 int tmpDay = MapStatus.listRes.get(index-1).plan.get(0).loanDays - preDays;
                 Double tmp = shengYuBenJin - (repaymentAmt - mustRepaymentAmt - MapStatus.listRes.get(index-1).plan.get(0).principal - shengYuJiaPei - AllshengYuShouXuFei);
                 //重新计算还款计划
                 //computeRepaymentPlan(tmp, fenqi,1,tmpDay,rililv,yuelilv,index);
             }
             else if (repaymentAmt > mustRepaymentAmt && 
             		(repaymentAmt - mustRepaymentAmt) > MapStatus.listRes.get(index-1).plan.get(0).principal &&
             		(repaymentAmt - mustRepaymentAmt - MapStatus.listRes.get(index-1).plan.get(0).principal - AllshengYuShouXuFei- shengYuJiaPei) >= shengYuBenJin)
             {
                 //全部还清
                 for (int i = 0; i < fenqi;i++ )
                 {
                	 Plan plan = MapStatus.listRes.get(index-1).plan.get(i);
                	 plan.sum =  0.00;
                	 plan.principal = 0.00;
                	 plan.interest = 0.00;
                	 plan.compensateFee = 0.00;
                	 plan.serviceFee = 0.00;
                	 plan.amount = 0.00;

                 }
                
             }
         }
     }
	
	
}
