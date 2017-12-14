package com.test.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.test.model.LoanProductFeeVo;

@Component
public class FeeHandler {
	
	 @Autowired
	 private Helper helper ; 

	
	 //���������ղ��
     public Map<String,Object> computeDayDiffAmount( int normalDay, Date startTime)//String[] arr,
     {
    	 Map<String,Object> map=new HashMap<String,Object>();
    	 SimpleDateFormat s=new SimpleDateFormat("yyyy/MM/dd");
    	 String firstRepaymentDate=null;
    	 String perfectRepayDate = null;
    	 int chae = 1, flag=0;
    	 int day = 0;
    	    if(helper==null)
    	    	 return null;
    	    else
    	    	day = helper.getDateInfo(startTime, 2);
         //��ȡ����յ���
    	 //��ʵ�ʽ����=����������ʱ,
    	   if(normalDay==day){ 
    		   flag=2;   //��������=�����
    		   perfectRepayDate=s.format(startTime);
    		   firstRepaymentDate=helper.getNextMonthDate(startTime);   
    	   }else{
    	 //��ʵ�ʽ���աٻ���������ʱ ����ȡ��������գ�����ո���������յĲ��С��15
    		   
    		   //�������������
    		   String repaymentDate=helper.getDateInfo(startTime, 0)+"/"+helper.getDateInfo(startTime, 1)+"/"+normalDay;
    		   System.out.println("----repaymentDate:"+repaymentDate);
    		   try {
				int offset=helper.dateDiff(startTime,s.parse(repaymentDate));//����ո����°��������յ����ڵĲ��
				////�����������������֮��������������ڽ���յ��ϸ���
				if(offset>15){
					
					perfectRepayDate=helper.getNextMonthDate(s.parse(repaymentDate),-1);
					
					//�״λ�������������������һ����
					firstRepaymentDate=helper.getNextMonthDate(s.parse(perfectRepayDate));
					//�����������������֮ǰ
					flag=0;
				}
				//�����������������֮ǰ��������������ڽ���յ��¸���
				else if(offset<-15){
					perfectRepayDate=helper.getNextMonthDate(s.parse(repaymentDate),1);
					
					//�״λ�������������������һ����
					firstRepaymentDate=helper.getNextMonthDate(s.parse(perfectRepayDate));
					//�����������������֮��
					flag=1;
					
				}
				else{
					//�����������������֮ǰ
					perfectRepayDate=repaymentDate;
					//�״λ�������������������һ����
					firstRepaymentDate=helper.getNextMonthDate(s.parse(perfectRepayDate));
					flag=(offset>0)?1:0;	
				}
				
				//计算差额天数
				chae=Math.abs(helper.dateDiff(startTime,s.parse(perfectRepayDate)));

				
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	   }
		 //
    	   map.put("firstRapaymentDate", firstRepaymentDate);
    	   map.put("chae", chae);
    	   map.put("flag", flag);
    	   map.put("perfectRepayDate", perfectRepayDate);
    	   
    	return map;
     }
	 

	 /*
	  * 计算本息和
	  */
	public int getTermPrincipalAndInterest(int periods,double investAmount,double monthRate){
		
		
		  double sum=	investAmount * monthRate * Math.pow((1+monthRate), periods) /( Math.pow((1+monthRate), periods)-1);
		  System.out.println("sum="+sum); 
		  
//		  System.out.println("test:"+periods+","+investAmount+","+monthRate);
		  return  (int) Math.round(sum);  
			
		}
	
	  
    //计算完美本金
    public double generatePerfectDayDiffAmount(double amount, int flag, int chae, double rate)
    {
    	double amount1 = 0;
    	//ʵ�ʽ���������������պ�
        if (flag == 0)
        {
            amount1 = amount / (1 + chae * rate);
        }
        //ʵ�ʽ����������������ǰ
        else if (flag == 1)
        {
            amount1 = amount * (1 + chae * rate);
        }
        else
        {
        	amount1=amount;
        }

        return amount1;
    }
	
	  /*
	   * ����ÿ�ڱ������Ϣ
	   */
	    public List<LoanProductFeeVo> getPrincleAndInterestForTerm(int periods, String loanTime, String firstRepaymentDay, double loanAmount,
	    		double amount,double rate,double monthRate){
	    	/*
	    	 * ���޸ĳ�Ϊע�ͷ�ʽ��������
	    	 */
	    	Helper helper=new Helper();
	    	FeeHandler handler=new FeeHandler();
	    	
	    	
	    	SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
	    	List<LoanProductFeeVo> loanList=new ArrayList<LoanProductFeeVo>();
	    	//���㱾Ϣ��
		    	double sum=handler.getTermPrincipalAndInterest(periods,amount,monthRate); //���������������
		    	double principal = 0;
		        double interest= 0;
		        double paidPrincipal =0;
		       	double unpaidPrincipal =loanAmount;//����ÿ����Ϣ��δ������
		       	int loanDays=0;
	       	//ÿ�ڻ�����
	       	List<String> list=new ArrayList<>();
	       	list=helper.getRepaymentDate(periods,firstRepaymentDay);
	    	for(int i=1;i<=periods;i++){
	    		LoanProductFeeVo loanProductFee=new LoanProductFeeVo();
	    		
				try {  
					 //����ÿ��֮�������
					   if(i==1){
							System.out.println("首期还款日："+firstRepaymentDay+","+loanTime);
						   	loanDays=helper.dateDiff(f.parse(loanTime), f.parse(firstRepaymentDay));
						   	//System.out.println("���㱾Ϣ�ͣ�"+firstRepaymentDay);
						   }
					   else{
						    loanDays=helper.dateDiff(f.parse(list.get(i-2)),f.parse(list.get(i-1)));
					   }
						 //��Ϣ����������
//						  interest=helper.getSiSheWuBuRu(loanAmount*rate*loanDays);
						 
						   interest=helper.getSiSheWuBuRu(unpaidPrincipal*rate*loanDays);
					  
						
					} catch (ParseException e) {
						e.printStackTrace();
					} //���һ�����¼���
				    if(i==periods){
				    	   		principal=loanAmount-paidPrincipal;
				    	   		loanProductFee.setPrincipalAddInterset(principal+interest);
				       		}
				       else {
				    	        principal=sum-interest; 
				    	        loanProductFee.setPrincipalAddInterset(sum);
				       }
//						loanProductFee.setPrincipalAddInterset(sum);
						loanProductFee.setPrincipal(principal);
						loanProductFee.setInterset(interest);
						loanProductFee.setLoanDays(loanDays);
						loanProductFee.setRepaymentDay(list.get(i-1));
						//δ������۳�ÿһ�ڵĻ����
						unpaidPrincipal=unpaidPrincipal-principal;
						paidPrincipal+=principal;
						loanList.add(loanProductFee);
	    		}
	    		
	    		
	    		    	
			return loanList;	
	    }
	   
	    /*
	     * 计算加赔费
	     */
        public Double ComputeJiaPei(double tmpAmount, double jiapeifeilv,double kantoujiapei,int fenqi)
        {
            Double zongjiapei = (double) Math.round(tmpAmount * jiapeifeilv);
            Double kantouvalue = (double) Math.round(zongjiapei * kantoujiapei);
            Double jiapei = (zongjiapei - kantouvalue) / fenqi;
            jiapei = (double) Math.round(jiapei);
            return jiapei;
        }

   

        
	    /*
	     * ����������
	     */
//        Double shouxufei = cf.ComputeShouXuFei(tmpAmount, shouxufeilv, kantoushouxuyfei, 3);
	    
        public Double ComputeShouXuFei(double tmpAmount, double shouxufeilv, double kantoushouxufei,int fenqi)
        {
            Double zongshouxufei = (double) Math.round(tmpAmount * shouxufeilv);
            Double kantouvalue = (double) Math.round(zongshouxufei * kantoushouxufei);
            Double shouxufei = (zongshouxufei - kantouvalue) / fenqi;
            shouxufei = (double) Math.round(shouxufei);
            return shouxufei;
        }
	    
	    //test method
	    public static void main(String [] args) throws ParseException{
//	    	double loanAmount=1000000;
//	    	float rate=0.0004f;
//	    	double monthRate=0.012;	
//	    	System.out.println(loanAmount+","+rate+","+monthRate);
	    	FeeHandler handler=new FeeHandler();
	    	Helper helper=new Helper();
	    	System.out.println(handler.getTermPrincipalAndInterest(3, 100,0.009));
//	        System.out.println("------------"+handler.generatePerfectDayDiffAmount(300000, 1, 6, 0.0004));
//	    	List<LoanProductFeeVo> list = handler.getPrincleAndInterestForTerm(6,"2017/01/15","2017/02/15",1000000,1000000,0.0004,0.012);
//	        for(LoanProductFeeVo l: list)
//	    	  System.out.println(l.getPrincipalAddInterset()+","+l.getPrincipal()+","+l.getInterset()+","+l.getLoanDays()+","+l.getRepaymentDay());
//	        
//	        
	        SimpleDateFormat s=new SimpleDateFormat("yyyy/MM/dd");
	        
	        //	     	   map.put("firstRapaymentDate", firstRepaymentDate);
			//	    	   map.put("chae", chae);
			//	    	   map.put("flag", flag);
			//	    	   map.put("perfectRepayDate", perfectRepayDate);
	        
	        Date date =new Date("2016/12/20");
//	        Date date =new Date("2017/07/03");
	     	System.out.println(helper.getDateInfo(date, 0)+","+helper.getDateInfo(date, 1)+","+helper.getDateInfo(date, 2));
				        	
			//Map<String, Object> map = handler.computeDayDiffAmount(3,date);
			Map<String, Object> map = handler.computeDayDiffAmount(28,date);
			
			System.out.println(map.get("firstRapaymentDate")+","+map.get("perfectRepayDate")+","+map.get("chae")+","+map.get("flag"));
			//2017/06/03,56,0,2017/05/03
			//2017/08/03,2017/07/03,5,0
	    }
	    

}
