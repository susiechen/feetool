package com.test.model;
import java.util.List;

//import com.test.service.Faxi;

public class StatusRes
    {
      
		public  List<Plan> plan;

        public  List<NewPlan> newPlan;

        //记录每期还款状态
        public  int[] status ;

        //记录罚息赋值状态
        public  int[] faxiStatus ;

        //标记每一期的每一次还款是不是第一次还款
        public  int[] firstRepayment ;

        //存储罚息数据
       // public  List<Faxi> faxiList ;

        public  int flag;

        //标记是逾期利息还是置后利息
        public int isBehindOrOutOfDue;

        public  List<String> paymentList;

        public  String lastRepaymentDate ;

        /**
         *   
         * @param plan
         * @param newPlan
         * @param status
         * @param faxiStatus
         * @param firstRepayment
         * @param flag
         * @param isBehindOrOutOfDue
         * @param paymentList
         * @param lastRepaymentDate
         */

		public StatusRes(List<Plan> plan, List<NewPlan> newPlan, int[] status, int[] faxiStatus, int[] firstRepayment,
				int flag, int isBehindOrOutOfDue, List<String> paymentList, String lastRepaymentDate) {
			super();
			this.plan = plan;
			this.newPlan = newPlan;
			this.status = status;
			this.faxiStatus = faxiStatus;
			this.firstRepayment = firstRepayment;
			this.flag = flag;
			this.isBehindOrOutOfDue = isBehindOrOutOfDue;
			this.paymentList = paymentList;
			this.lastRepaymentDate = lastRepaymentDate;
		}

    }


    

