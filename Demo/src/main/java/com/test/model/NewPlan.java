package com.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   //@Data:Ϊ������������ getter setter ����
@NoArgsConstructor  //�����޲ι��췽����
@AllArgsConstructor //�������в����Ĺ��췽��
public class NewPlan {
	
    public int loanPeriod ; // 借款期数

    public Double sum ; //本息和

    public Double principal;  //本金

    public Double interest; //利息

    public Double compensateFee ; //加赔费

    public Double serviceFee ; //手续费

    public Double amount ;  //总额

    public String repaymentDay ;  //还款日

    public int loanDays ; //借款天数
    
    public Double  overdueFee ; //罚息

    public Double collectionFee ;  //催收
    
    
    public Plan changeToPlan(){
    	Plan plan = new Plan();
    	plan.setAmount(this.amount);
    	
    	return plan;
    }


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


	public Double getOverdueFee() {
		return overdueFee;
	}


	public void setOverdueFee(Double overdueFee) {
		this.overdueFee = overdueFee;
	}


	public Double getCollectionFee() {
		return collectionFee;
	}


	public void setCollectionFee(Double collectionFee) {
		this.collectionFee = collectionFee;
	}

}
