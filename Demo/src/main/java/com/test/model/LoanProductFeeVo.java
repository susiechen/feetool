package com.test.model;



public class LoanProductFeeVo {
	 //本息和
    private double principalAddInterset;
    //本金
    private double principal;
    //利息
    private double intereset;
    
    //借款天数
    private int loanDays;
    //还款日
    private  String repaymentDay;
    
    public String getRepaymentDay() {
		return repaymentDay;
	}
	public void setRepaymentDay(String repaymentDay) {
		this.repaymentDay = repaymentDay;
	}
	public int getLoanDays() {
		return loanDays;
	}
	public void setLoanDays(int loanDays) {
		this.loanDays = loanDays;
	}
	/**
     * ��Ϣ�ͣ������Ĺ�ʽ���㣬ÿ����ͬ��
     * @return
     */
    public double getPrincipalAddInterset() {
//        if(principalAddInterset == null && principal != null && interset != null) {
//            principalAddInterset = principal.add(interset);
//        }
        return principalAddInterset;
    }
    /**
     * ��Ϣ�ͣ������Ĺ�ʽ���㣬ÿ����ͬ��
     * @param principalAddInterset
     */
    public void setPrincipalAddInterset(double principalAddInterset) {
        this.principalAddInterset = principalAddInterset;
    }
    /**
     * ����
     * @return
     */
    public double getPrincipal() {
        return principal;
    }
    /**
     * ����
     * @param principal
     */
    public void setPrincipal(double principal) {
        this.principal = principal;
    }
    /**
     * ��Ϣ
     * @return
     */
    public double getInterset() {
        return intereset;
    }
    /**
     * ��Ϣ
     * @param interset
     */
    public void setInterset(double interset) {
        this.intereset = interset;
    }

}
