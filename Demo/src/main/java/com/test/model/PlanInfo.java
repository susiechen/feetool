package com.test.model;

public class PlanInfo {
	private double loanAmount;
	private int periods;
	 private double rate;
	 private double monthRate;
	 private double serviceFeeRate;
	 private double headServiceFeeRate;
	 private double compensateFeeRate;
	 private double headCompensateFeeRate;
	 private String loanDate;
	 private  int repaymentDate;
	 private int numInstallmentByFee;
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public int getPeriods() {
		return periods;
	}
	public void setPeriods(int periods) {
		this.periods = periods;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getMonthRate() {
		return monthRate;
	}
	public void setMonthRate(double monthRate) {
		this.monthRate = monthRate;
	}
	public double getServiceFeeRate() {
		return serviceFeeRate;
	}
	public void setServiceFeeRate(double serviceFeeRate) {
		this.serviceFeeRate = serviceFeeRate;
	}
	public double getHeadServiceFeeRate() {
		return headServiceFeeRate;
	}
	public void setHeadServiceFeeRate(double headServiceFeeRate) {
		this.headServiceFeeRate = headServiceFeeRate;
	}
	public double getCompensateFeeRate() {
		return compensateFeeRate;
	}
	public void setCompensateFeeRate(double compensateFeeRate) {
		this.compensateFeeRate = compensateFeeRate;
	}
	public double getHeadCompensateFeeRate() {
		return headCompensateFeeRate;
	}
	public void setHeadCompensateFeeRate(double headCompensateFeeRate) {
		this.headCompensateFeeRate = headCompensateFeeRate;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public int getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(int repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public int getNumInstallmentByFee() {
		return numInstallmentByFee;
	}
	public void setNumInstallmentByFee(int numInstallmentByFee) {
		this.numInstallmentByFee = numInstallmentByFee;
	}


	 
}
