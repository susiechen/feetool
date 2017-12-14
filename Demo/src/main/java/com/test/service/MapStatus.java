package com.test.service;
import java.util.ArrayList;
import java.util.List;

import com.test.model.NewPlan;
import com.test.model.Plan;
import com.test.model.StatusRes;


public class MapStatus{
        public static List<StatusRes> listRes = new ArrayList<StatusRes>();
        
        //初始化状态列表
        public static void init()
        {
            listRes.add(new StatusRes(
                    new ArrayList<Plan>(),
                    new ArrayList<NewPlan>(),
                    new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    //new ArrayList<Faxi>(),
                    0,0,new ArrayList<String>(),""));
        }


        //根据索引重置指定的还款计划
        public static void resetStatus(List<StatusRes> listsr1, int index)
        { 
        		listsr1.get(index).plan = new ArrayList<Plan>();
        		listsr1.get(index).newPlan = new ArrayList<NewPlan>();
                listsr1.get(index).status = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                listsr1.get(index).faxiStatus = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                listsr1.get(index).firstRepayment = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                //listsr1.get(index).faxiList = new ArrayList<Faxi>();
                listsr1.get(index).flag = 0;
                listsr1.get(index).isBehindOrOutOfDue = 0;
                listsr1.get(index).paymentList = new ArrayList<String>();
                listsr1.get(index).lastRepaymentDate = "";
        }
    }

