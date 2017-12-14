package com.test.service;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Helper {
	

    //计算两个日期之间的天数
    public  int dateDiff(Date dateStart, Date dateEnd)
    {

        int sp = ((int) (dateEnd.getTime()/ 1000) - (int) (dateStart.getTime() / 1000)) / 3600 / 24;
        return sp;
    }
    
    
    //获取下一个月的日期
    public   String getNextMonthDate(Date dateStart)
    {
    	SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        Date nextDate = null;
        java.util.Calendar caled = java.util.Calendar.getInstance();   
        
        
        caled.setTime(dateStart); 
        caled.add(Calendar.MONTH, 1) ;

        nextDate=caled.getTime();
        return f.format(nextDate);
    }
    
    //��ȡ���µ�����
    public   String getNextMonthDate(Date dateStart,int offset)
    {
    	SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        Date nextDate = null;
        java.util.Calendar caled = java.util.Calendar.getInstance();   
        
        
        caled.setTime(dateStart);
        if(offset==1)//��ȡ����
        	caled.add(Calendar.MONTH, 1) ;
        else 
            caled.add(Calendar.MONTH, -1) ;//��ȡ����
        nextDate=caled.getTime();
        return f.format(nextDate);
    }
    
    //��ȡ���ڵ�����
    public  int getDateInfo(Date dateStart,int flag){
    	java.util.Calendar caled = java.util.Calendar.getInstance();   
        caled.setTime(dateStart);
        if(flag==0)
        	return  caled.get(Calendar.YEAR);//�õ���
        else if(flag==1)
        	return caled.get(Calendar.MONTH)+1;//�õ��£���Ϊ��0��ʼ�ģ�����Ҫ��1   
        else
        	
        	return caled.get(Calendar.DAY_OF_MONTH);//�õ���
        

    	
    }
    
    
    
    //���ÿ�ڻ�������
    public    List<String> getRepaymentDate(int period,String firstRepaymentDay )
    {
    	SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
    	List<String> list=new ArrayList<>();
        for(int i=1;i<=period;i++){
        	if(i==1) 
        		list.add(firstRepaymentDay);
			else
				try {
					list.add(getNextMonthDate(f.parse(firstRepaymentDay)));
					firstRepaymentDay=getNextMonthDate(f.parse(firstRepaymentDay));
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
        	    
        }
        return list;
    }

    //�����岻�룬ֻ����Ϣ�ͷ�Ϣ����
    //����������
    public  double getSiSheWuBuRu(double amt)
    {
        
        int amount =(int) amt*100;
//        if (amt.toString().contains("."))
//        {
//            return Double.Parse(amt.toString().split('.')[0]);
//        }
//        
         
            
        return (double)(amount/100);

    }

//    public Double AllUnPaidInterest(StatusRes sr)
//    {
//        Double tmp = 0;
//        foreach(NewPlan np in sr.newPlan)
//        {
//            tmp += np.lx;
//        }
//
//        return tmp;
//    
//    }
//
//    public Double AllUnPaidOriginal(StatusRes sr)
//    {
//        Double tmp = 0;
//        foreach (NewPlan np in sr.newPlan)
//        {
//            tmp += np.bj;
//        }
//
//        return tmp;
//
//    }
//
//    public Double AllUnPaidJiaPei(StatusRes sr)
//    {
//        Double tmp = 0;
//        foreach (NewPlan np in sr.newPlan)
//        {
//            tmp += np.jp;
//        }
//
//        return tmp;
//
//    }
//
//    public Double AllUnPaidShouXuFei(StatusRes sr)
//    {
//        Double tmp = 0;
//        foreach (NewPlan np in sr.newPlan)
//        {
//            tmp += np.shouxufei;
//        }
//
//        return tmp;
//
//    }

   

    
    
    //test method
    public static void main(String [] args){
    	Helper h=new Helper();
    	Date date =new Date();
    	SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
    	System.out.println(f.format(date));
    	
    	System.out.println(h.getDateInfo(date, 0)+","+h.getDateInfo(date, 1)+","+h.getDateInfo(date, 2));
    	
    	
    	System.out.println(h.getRepaymentDate(6,"2017/02/15"));
    	try {
			System.out.println(f.format(f.parse(h.getNextMonthDate(date))));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

}
