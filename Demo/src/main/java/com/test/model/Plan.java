package com.test.model;

public class Plan {
	
    public int loanPeriod ; // 借款期数

    public Double sum ; //本息和

    public Double principal;  //本金

    public Double interest; //利息

    public Double compensateFee ; //加赔费

    public Double serviceFee ; //手续费

    public Double amount ;  //总费用

    public String repaymentDay ;  //还款日

    public int loanDays ; //每期借款天数

	public int getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(int loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getCompensateFee() {
		return compensateFee;
	}

	public void setCompensateFee(Double compensateFee) {
		this.compensateFee = compensateFee;
	}

	public Double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

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
    
    public NewPlan changeToNewPlan(){
    	NewPlan plan = new NewPlan();
    	plan.setAmount(this.amount);
    	plan.setLoanDays(this.loanDays);
    	plan.setLoanPeriod(this.loanPeriod);
    	plan.setPrincipal(this.principal);
    	plan.setInterest(this.interest);
    	plan.setSum(this.sum);
    	plan.setCompensateFee(this.compensateFee);
    	plan.setServiceFee(this.serviceFee);
    	plan.setRepaymentDay(this.repaymentDay);
    	plan.setOverdueFee(0.00);
    	plan.setCollectionFee(0.00);
    	return plan;
    }

}
