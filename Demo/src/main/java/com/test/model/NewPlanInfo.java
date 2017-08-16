package com.test.model;

import java.util.Date;

public class NewPlanInfo {

	 
	 private	double repaymentAmt;
	 private	String currentPaymentDate;
	 private 	int currentPeriod;
	 private	double rate;
	 private	double monthRate;
	 private 	int periods;
	 private	int numInstallmentByFee;
	 private 	double loanAmount;
	 private  	double overdueFee;
	 private 	double collectionFee;
	 private	String loanDate;
	public double getRepaymentAmt() {
		return repaymentAmt;
	}
	public void setRepaymentAmt(double repaymentAmt) {
		this.repaymentAmt = repaymentAmt;
	}

	public int getCurrentPeriod() {
		return currentPeriod;
	}
	public void setCurrentPeriod(int currentPeriod) {
		this.currentPeriod = currentPeriod;
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
	public int getPeriods() {
		return periods;
	}
	public void setPeriods(int periods) {
		this.periods = periods;
	}
	public int getNumInstallmentByFee() {
		return numInstallmentByFee;
	}
	public void setNumInstallmentByFee(int numInstallmentByFee) {
		this.numInstallmentByFee = numInstallmentByFee;
	}
	public String getCurrentPaymentDate() {
		return currentPaymentDate;
	}
	public void setCurrentPaymentDate(String currentPaymentDate) {
		this.currentPaymentDate = currentPaymentDate;
	}
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public double getOverdueFee() {
		return overdueFee;
	}
	public void setOverdueFee(double overdueFee) {
		this.overdueFee = overdueFee;
	}
	public double getCollectionFee() {
		return collectionFee;
	}
	public void setCollectionFee(double collectionFee) {
		this.collectionFee = collectionFee;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	
	 
}
