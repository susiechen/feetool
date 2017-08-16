package com.test.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.model.NewPlan;
import com.test.model.Plan;

//还款操作类
@Service
public class RepaymentHelper {

	@Autowired
	private Helper helper;

	// 首期还款
	public void preRepaymentFirst(Double repaymentAmt, String repaymentDate,
			// Double originalAmt,
			int currentPeriod, Date startTime, Double rililv, Double yuelilv, int fenqi, int index,
			int numInstallmentByFee) throws ParseException// 手续费的分期期数
	{
		Double mustRepaymentAmt = 0.00;
		Double shouldRepaymentAmt = 0.00;
		int preDays = 0;
		MapStatus.listRes.get(index - 1).paymentList.add(repaymentDate);

		// 本期之外的剩余加赔
		Double shengYuJiaPei = 0.00;
		// 本期之外的剩余本金
		Double shengYuBenJin = 0.00;
		// 所有的剩余本金
		Double AllshengYuBenJin = 0.00;
		// 本期之外的未还手续费
		Double AllshengYuShouXuFei = 0.00;
		for (Plan plan : MapStatus.listRes.get(index - 1).plan) {
			// 本期之外的剩余加赔
			shengYuJiaPei += plan.getCompensateFee();
			// 本期之外的剩余本金
			shengYuBenJin += plan.getPrincipal();
			// 所有的剩余本金
			AllshengYuBenJin += plan.getPrincipal();
			// 本期之外的未还手续费
			AllshengYuShouXuFei += plan.getServiceFee();
		}
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");

		if (MapStatus.listRes.get(index - 1).flag == 0) {
			preDays = helper.dateDiff(startTime, f.parse(repaymentDate));
			if (preDays == 0) {
				preDays = 1;
			}
		} else {
			preDays = helper.dateDiff(f.parse(MapStatus.listRes.get(index - 1).lastRepaymentDate),
					f.parse(repaymentDate));
			if (preDays == 0) {
				preDays = 0;
			} else if (helper.dateDiff(startTime, f.parse(MapStatus.listRes.get(index - 1).lastRepaymentDate)) == 0
					&& preDays != 0) {
				preDays = preDays - 1;
			}

		}

		// 重置上次还款时间
		// lastRepaymentDate = repaymentDate;
		Double currentLx = 0.00;
		// 还款日还款
		if (helper.dateDiff(f.parse(repaymentDate),
				f.parse(MapStatus.listRes.get(index - 1).plan.get(0).repaymentDay)) == 0) {
			currentLx = MapStatus.listRes.get(index - 1).plan.get(0).interest;
		} else {
			currentLx = helper.getSiSheWuBuRu(AllshengYuBenJin * rililv * preDays);
		}

		// 记录支付利息，用来汇总
		Double paidlixi = currentLx;

		// 计算应还和必还
		shouldRepaymentAmt = currentLx + MapStatus.listRes.get(index - 1).plan.get(0).compensateFee
				+ MapStatus.listRes.get(index - 1).plan.get(0).principal
				+ MapStatus.listRes.get(index - 1).plan.get(0).serviceFee;
		mustRepaymentAmt = currentLx + MapStatus.listRes.get(index - 1).plan.get(0).compensateFee
				+ MapStatus.listRes.get(index - 1).plan.get(0).serviceFee;

		if (repaymentAmt >= MapStatus.listRes.get(index - 1).plan.get(0).amount) {
			// 标记为置后利息
			MapStatus.listRes.get(index - 1).isBehindOrOutOfDue = 1;
		} else {
			// 标记为逾期利息
			MapStatus.listRes.get(index - 1).isBehindOrOutOfDue = 0;
		}

		// 首期还款日前提前还款
		if (f.parse(repaymentDate).compareTo(f.parse(MapStatus.listRes.get(index - 1).plan.get(0).repaymentDay)) <= 0) {

			if (repaymentAmt < mustRepaymentAmt) {
				System.out.println("还款金额小于必还项");
				return;
			}
			MapStatus.listRes.get(index - 1).flag = MapStatus.listRes.get(index - 1).flag + 1;

			// 重置上次还款时间
			MapStatus.listRes.get(index - 1).lastRepaymentDate = repaymentDate;

			// 还款金额等于必还项
			if (repaymentAmt == mustRepaymentAmt) {
				// plan.get(0).principal = 0;
				MapStatus.listRes.get(index - 1).plan
						.get(0).loanDays = MapStatus.listRes.get(index - 1).plan.get(0).loanDays - preDays;
				MapStatus.listRes.get(index - 1).plan
						.get(0).interest = MapStatus.listRes.get(index - 1).plan.get(0).interest - currentLx;
				MapStatus.listRes.get(index - 1).plan
						.get(0).sum = MapStatus.listRes.get(index - 1).plan.get(0).principal
								+ MapStatus.listRes.get(index - 1).plan.get(0).interest;
				MapStatus.listRes.get(index - 1).plan.get(0).compensateFee = 0.00;
				MapStatus.listRes.get(index - 1).plan.get(0).serviceFee = 0.00;
				MapStatus.listRes.get(index - 1).plan.get(0).amount = MapStatus.listRes.get(index - 1).plan.get(0).sum;
			}
			//
			else if ((repaymentAmt.compareTo(mustRepaymentAmt) > 0) && ((Double) (repaymentAmt - mustRepaymentAmt))
					.compareTo(MapStatus.listRes.get(index - 1).plan.get(0).principal) <= 0) {
				// 还款金额大于必还项，超出部分不大于本期本金
				Double shenyubenj = 0.00;

				MapStatus.listRes.get(index - 1).plan
						.get(0).principal = MapStatus.listRes.get(index - 1).plan.get(0).principal
								- (repaymentAmt - mustRepaymentAmt);
				// 更新剩余所有未还本金
				for (Plan pl : MapStatus.listRes.get(index - 1).plan) {
					shenyubenj += pl.principal;
				}
				MapStatus.listRes.get(index - 1).plan
						.get(0).loanDays = MapStatus.listRes.get(index - 1).plan.get(0).loanDays - preDays;
				MapStatus.listRes.get(index - 1).plan.get(0).interest = helper
						.getSiSheWuBuRu(shenyubenj * MapStatus.listRes.get(index - 1).plan.get(0).loanDays * rililv);
				MapStatus.listRes.get(index - 1).plan
						.get(0).sum = MapStatus.listRes.get(index - 1).plan.get(0).principal
								+ MapStatus.listRes.get(index - 1).plan.get(0).interest;
				MapStatus.listRes.get(index - 1).plan.get(0).compensateFee = 0.00;
				MapStatus.listRes.get(index - 1).plan.get(0).serviceFee = 0.00;
				MapStatus.listRes.get(index - 1).plan.get(0).amount = MapStatus.listRes.get(index - 1).plan.get(0).sum
						+ MapStatus.listRes.get(index - 1).plan.get(0).compensateFee
						+ MapStatus.listRes.get(index - 1).plan.get(0).serviceFee;

			} else if (repaymentAmt > mustRepaymentAmt
					&& (repaymentAmt - mustRepaymentAmt) > MapStatus.listRes.get(index - 1).plan.get(0).principal
					&& (repaymentAmt - mustRepaymentAmt
							- MapStatus.listRes.get(index - 1).plan.get(0).principal) <= AllshengYuShouXuFei) {
				// 还款金额大于应还项，超出部分小于剩余各期总手续费
				// 计算多余部分
				Double outOfShould = repaymentAmt - mustRepaymentAmt
						- MapStatus.listRes.get(index - 1).plan.get(0).principal;

				MapStatus.listRes.get(index - 1).plan.get(0).principal = 0.00;
				Double shenyubenj = 0.00;
				// 更新剩余所有未还本金
				for (Plan pl : MapStatus.listRes.get(index - 1).plan) {
					shenyubenj += pl.principal;
				}
				MapStatus.listRes.get(index - 1).plan
						.get(0).loanDays = MapStatus.listRes.get(index - 1).plan.get(0).loanDays - preDays;
				MapStatus.listRes.get(index - 1).plan.get(0).interest = helper
						.getSiSheWuBuRu(shenyubenj * MapStatus.listRes.get(index - 1).plan.get(0).loanDays * rililv);
				MapStatus.listRes.get(index - 1).plan.get(0).sum = MapStatus.listRes.get(index - 1).plan
						.get(0).interest;
				MapStatus.listRes.get(index - 1).plan.get(0).amount = MapStatus.listRes.get(index - 1).plan
						.get(0).interest;
				MapStatus.listRes.get(index - 1).plan.get(0).compensateFee = 0.00;
				MapStatus.listRes.get(index - 1).plan.get(0).serviceFee = 0.00;

				// 多余部分还了剩余的总手续费，后面两期还款计划改变
				Double tmp = (double) Math.round((AllshengYuShouXuFei - outOfShould) / (numInstallmentByFee - 1));
				for (int i = 1; i < numInstallmentByFee; i++) {
					Double shouxufeilast = 0.00;
					if (i + 1 == numInstallmentByFee) {
						for (int j = 0; j < (numInstallmentByFee - 1); j++) {
							shouxufeilast += MapStatus.listRes.get(index - 1).plan.get(j).serviceFee;
						}
						MapStatus.listRes.get(index - 1).plan.get(i).serviceFee = AllshengYuShouXuFei - outOfShould
								- shouxufeilast;
						MapStatus.listRes.get(index - 1).plan
								.get(i).amount = MapStatus.listRes.get(index - 1).plan.get(i).principal
										+ MapStatus.listRes.get(index - 1).plan.get(i).compensateFee
										+ MapStatus.listRes.get(index - 1).plan.get(i).serviceFee;
					} else {
						MapStatus.listRes.get(index - 1).plan.get(i).serviceFee = tmp;
						MapStatus.listRes.get(index - 1).plan
								.get(i).amount = MapStatus.listRes.get(index - 1).plan.get(i).principal
										+ MapStatus.listRes.get(index - 1).plan.get(i).compensateFee
										+ MapStatus.listRes.get(index - 1).plan.get(i).serviceFee;
					}
				}
			} else if (repaymentAmt > mustRepaymentAmt
					&& (repaymentAmt - mustRepaymentAmt) > MapStatus.listRes.get(index - 1).plan.get(0).principal
					&& (repaymentAmt - mustRepaymentAmt
							- MapStatus.listRes.get(index - 1).plan.get(0).principal) > AllshengYuShouXuFei
					&& (repaymentAmt - mustRepaymentAmt - MapStatus.listRes.get(index - 1).plan.get(0).principal
							- AllshengYuShouXuFei) <= shengYuJiaPei) {
				// 还款金额大于应还项，超出部分小于剩余各期总加赔
				// 计算多余部分
				Double outOfShould = repaymentAmt - mustRepaymentAmt
						- MapStatus.listRes.get(index - 1).plan.get(0).principal;

				MapStatus.listRes.get(index - 1).plan.get(0).principal = 0.00;
				Double shenyubenj = (double) 0;
				// 更新剩余所有未还本金
				for (Plan pl : MapStatus.listRes.get(index - 1).plan) {
					shenyubenj += pl.principal;
				}
				// Double tmplx = plan.get(0).interest - currentLx;
				MapStatus.listRes.get(index - 1).plan
						.get(0).loanDays = MapStatus.listRes.get(index - 1).plan.get(0).loanDays - preDays;
				MapStatus.listRes.get(index - 1).plan.get(0).interest = helper
						.getSiSheWuBuRu(shenyubenj * MapStatus.listRes.get(index - 1).plan.get(0).loanDays * rililv);
				MapStatus.listRes.get(index - 1).plan.get(0).sum = MapStatus.listRes.get(index - 1).plan
						.get(0).interest;
				MapStatus.listRes.get(index - 1).plan.get(0).amount = MapStatus.listRes.get(index - 1).plan
						.get(0).interest;
				MapStatus.listRes.get(index - 1).plan.get(0).compensateFee = 0.00;
				MapStatus.listRes.get(index - 1).plan.get(0).serviceFee = 0.00;

				// 多余部分还了剩余的总加赔，后面各期还款计划改变
				Double tmp = (double) Math.round((shengYuJiaPei - outOfShould) / (fenqi - 1));
				for (int i = 1; i < fenqi; i++) {
					Double jiapeilast = (double) 0;
					if (i + 1 == fenqi) {
						for (int j = 0; j < (fenqi - 1); j++) {
							jiapeilast += MapStatus.listRes.get(index - 1).plan.get(j).compensateFee;
						}
						MapStatus.listRes.get(index - 1).plan.get(i).compensateFee = shengYuJiaPei - outOfShould
								- jiapeilast;
						MapStatus.listRes.get(index - 1).plan.get(i).serviceFee = 0.00;
						MapStatus.listRes.get(index - 1).plan
								.get(i).amount = MapStatus.listRes.get(index - 1).plan.get(i).principal
										+ MapStatus.listRes.get(index - 1).plan.get(i).compensateFee
										+ MapStatus.listRes.get(index - 1).plan.get(i).serviceFee;
					} else {
						MapStatus.listRes.get(index - 1).plan.get(i).compensateFee = tmp;
						MapStatus.listRes.get(index - 1).plan.get(i).serviceFee = 0.00;
						MapStatus.listRes.get(index - 1).plan
								.get(i).amount = MapStatus.listRes.get(index - 1).plan.get(i).principal
										+ MapStatus.listRes.get(index - 1).plan.get(i).compensateFee
										+ MapStatus.listRes.get(index - 1).plan.get(i).serviceFee;
					}
				}
			} else if (repaymentAmt > mustRepaymentAmt
					&& (repaymentAmt - mustRepaymentAmt) > MapStatus.listRes.get(index - 1).plan.get(0).principal
					&& (repaymentAmt - mustRepaymentAmt
							- MapStatus.listRes.get(index - 1).plan.get(0).principal) > AllshengYuShouXuFei
					&& (repaymentAmt - mustRepaymentAmt - MapStatus.listRes.get(index - 1).plan.get(0).principal
							- AllshengYuShouXuFei) > shengYuJiaPei
					&& (repaymentAmt - mustRepaymentAmt - MapStatus.listRes.get(index - 1).plan.get(0).principal
							- AllshengYuShouXuFei - shengYuJiaPei) <= shengYuBenJin) {
				// 还款金额大于应还项+剩余各期总手续费+剩余各期总加赔，超出部分小于剩余总的本金
				// int tmpDay =
				// MainWindow.DateDiff(f.parse(repaymentDate),f.parse(plan.get(0).hkr));
				int tmpDay = MapStatus.listRes.get(index - 1).plan.get(0).loanDays - preDays;
				Double tmp = shengYuBenJin - (repaymentAmt - mustRepaymentAmt
						- MapStatus.listRes.get(index - 1).plan.get(0).principal - shengYuJiaPei - AllshengYuShouXuFei);
				// 重新计算还款计划
				computeRepaymentPlan(tmp, fenqi, 1, tmpDay, rililv, yuelilv, index);
			} else if (repaymentAmt > mustRepaymentAmt
					&& (repaymentAmt - mustRepaymentAmt) > MapStatus.listRes.get(index - 1).plan.get(0).principal
					&& (repaymentAmt - mustRepaymentAmt - MapStatus.listRes.get(index - 1).plan.get(0).principal
							- AllshengYuShouXuFei - shengYuJiaPei) >= shengYuBenJin) {
				// 全部还清
				for (int i = 0; i < fenqi; i++) {
					MapStatus.listRes.get(index - 1).plan.get(i).sum = 0.00;
					MapStatus.listRes.get(index - 1).plan.get(i).principal = 0.00;
					MapStatus.listRes.get(index - 1).plan.get(i).interest = 0.00;
					MapStatus.listRes.get(index - 1).plan.get(i).compensateFee = 0.00;
					MapStatus.listRes.get(index - 1).plan.get(i).serviceFee = 0.00;
					MapStatus.listRes.get(index - 1).plan.get(i).amount = 0.00;

				}

			}
		}

	}

	/**
	 * 剩余期数重新计算还款计划
	 * 
	 * @param leavedAmt
	 * @param fenqi
	 * @param currentPeriod
	 * @param leavedDays
	 * @param rililv
	 * @param yuelilv
	 * @param index
	 */
	private void computeRepaymentPlan(Double leavedAmt, int fenqi, int currentPeriod, int leavedDays, Double rililv,
			Double yuelilv, int index) {
		// TODO Auto-generated method stub
		int tmpFenqi = fenqi - currentPeriod;
		Double avg = (leavedAmt * yuelilv * Math.pow(1 + yuelilv, tmpFenqi)) / (Math.pow(1 + yuelilv, tmpFenqi) - 1);
		Double avg1 = (double) Math.round(avg);

		NewPlan plan = MapStatus.listRes.get(index - 1).newPlan.get(currentPeriod - 1);
		plan.principal = 0.00;
		plan.compensateFee = 0.00;
		plan.serviceFee = 0.00;
		plan.interest = helper.getSiSheWuBuRu(rililv * leavedDays * leavedAmt);
		plan.sum = helper.getSiSheWuBuRu(rililv * leavedDays * leavedAmt);
		plan.amount = helper.getSiSheWuBuRu(rililv * leavedDays * leavedAmt);
		plan.loanDays = leavedDays;
		if (index > 1) {
			NewPlan prePlan = MapStatus.listRes.get(index - 1).newPlan.get(currentPeriod - 2);
			prePlan.interest = 0.00;
			prePlan.sum = 0.00;
			prePlan.amount = 0.00;
		}

		for (int i = currentPeriod; i < fenqi; i++) {
			Double yihuanbenjin = 0.00;

			for (int m = 0; m < i; m++) {
				NewPlan p2 = MapStatus.listRes.get(index - 1).newPlan.get(m);
				yihuanbenjin += p2.principal;
			}

			Double weihuanbenjin = leavedAmt - yihuanbenjin;
			NewPlan p1 = MapStatus.listRes.get(index - 1).newPlan.get(i);
			if ((i + 1) == fenqi) {

				p1.sum = weihuanbenjin + helper.getSiSheWuBuRu(p1.loanDays * rililv * weihuanbenjin);
				p1.interest = helper.getSiSheWuBuRu(p1.loanDays * rililv * weihuanbenjin);
				p1.principal = weihuanbenjin;
				p1.compensateFee = 0.00;
				p1.serviceFee = 0.00;
				p1.amount = weihuanbenjin + helper.getSiSheWuBuRu(p1.loanDays * rililv * weihuanbenjin);
			} else {
				p1.sum = avg1;
				p1.interest = helper.getSiSheWuBuRu(p1.loanDays * rililv * weihuanbenjin);
				p1.principal = avg1 - helper.getSiSheWuBuRu(p1.loanDays * rililv * weihuanbenjin);
				p1.compensateFee = 0.00;
				p1.serviceFee = 0.00;
				p1.amount = avg1 + 0;
			}
		}
	}

	/**
	 * 逾期还款
	 * 
	 * @param repaymentAmt
	 * @param repaymentDate
	 * @param originalAmt
	 * @param startTime
	 * @param rililv
	 * @param yuelilv
	 * @param fenqi
	 * @param index
	 * @param numInstallmentByFee
	 * @throws ParseException
	 */
	public static void overDueRepayment(Double repaymentAmt, String repaymentDate, Double originalAmt, Date startTime,
			Double rililv, Double yuelilv, int fenqi, int huankuanqishu, int index, int numInstallmentByFee)
			throws ParseException// 手续费的分期期数
	{
		// 逾期应还金额
		NewPlan overDuePlan = MapStatus.listRes.get(index - 1).newPlan.get(huankuanqishu - 2);
		Double yuqiyinghuan = overDuePlan.amount + overDuePlan.overdueFee + overDuePlan.collectionFee;
		if (repaymentAmt > yuqiyinghuan) {
			System.out.println("还款金额不能大于逾期应还");
			return;
		}
		// 还款金额小于逾期本金
		if (repaymentAmt <= overDuePlan.principal) {
			overDuePlan.principal = overDuePlan.principal - repaymentAmt;
			overDuePlan.sum = overDuePlan.principal + overDuePlan.interest;
			overDuePlan.amount = overDuePlan.sum + overDuePlan.compensateFee + overDuePlan.serviceFee;
		}
		// 还款金额小于逾期本息和
		else if (repaymentAmt > overDuePlan.principal && repaymentAmt <= overDuePlan.sum) {
			Double tmp = repaymentAmt - overDuePlan.principal;
			overDuePlan.principal = 0.00;
			overDuePlan.interest = overDuePlan.interest - tmp;
			overDuePlan.sum = overDuePlan.interest;
			overDuePlan.amount = overDuePlan.sum + overDuePlan.compensateFee + overDuePlan.serviceFee;
		}
		// 还款金额小于逾期本息和+罚息
		else if (repaymentAmt > (overDuePlan.principal + overDuePlan.interest)
				&& repaymentAmt <= (overDuePlan.principal + overDuePlan.interest + overDuePlan.overdueFee)) {
			Double tmp = repaymentAmt - overDuePlan.principal - overDuePlan.interest;
			overDuePlan.principal = 0.00;
			overDuePlan.interest = 0.00;
			overDuePlan.sum = 0.00;
			overDuePlan.amount = overDuePlan.compensateFee + overDuePlan.serviceFee;
			overDuePlan.overdueFee = overDuePlan.overdueFee - tmp;

		}
		// 还款金额小于逾期本息和+罚息+催收
		else if (repaymentAmt > (overDuePlan.principal + overDuePlan.interest + overDuePlan.overdueFee)
				&& repaymentAmt <= (overDuePlan.principal + overDuePlan.interest + overDuePlan.overdueFee
						+ overDuePlan.collectionFee)) {
			Double tmp = repaymentAmt - overDuePlan.principal - overDuePlan.interest - overDuePlan.overdueFee;
			overDuePlan.principal = 0.00;
			overDuePlan.interest = 0.00;
			overDuePlan.sum = 0.00;
			overDuePlan.amount = overDuePlan.compensateFee + overDuePlan.serviceFee;
			overDuePlan.overdueFee = 0.00;
			overDuePlan.collectionFee = overDuePlan.collectionFee - tmp;

		}
		// 还款金额小于逾期本息和+罚息+催收+手续费
		else if (overDuePlan.serviceFee != 0
				&& repaymentAmt > (overDuePlan.principal + overDuePlan.interest + overDuePlan.overdueFee
						+ overDuePlan.collectionFee)
				&& repaymentAmt <= (overDuePlan.principal + overDuePlan.interest + overDuePlan.overdueFee
						+ overDuePlan.collectionFee + overDuePlan.serviceFee)) {
			Double tmp = repaymentAmt - overDuePlan.principal - overDuePlan.interest - overDuePlan.overdueFee
					- overDuePlan.collectionFee;
			overDuePlan.principal = 0.00;
			overDuePlan.interest = 0.00;
			overDuePlan.sum = 0.00;

			overDuePlan.overdueFee = 0.00;
			overDuePlan.collectionFee = 0.00;
			overDuePlan.serviceFee = overDuePlan.serviceFee - tmp;
			overDuePlan.amount = overDuePlan.compensateFee + overDuePlan.serviceFee;

		}
		// 还款金额小于逾期本息和+罚息+催收+手续费+加赔
		else if (overDuePlan.compensateFee != 0
				&& repaymentAmt > (overDuePlan.principal + overDuePlan.interest + overDuePlan.overdueFee
						+ overDuePlan.collectionFee + overDuePlan.serviceFee)
				&& repaymentAmt <= (overDuePlan.principal + overDuePlan.interest + overDuePlan.overdueFee
						+ overDuePlan.collectionFee + overDuePlan.serviceFee + overDuePlan.compensateFee)) {
			Double tmp = repaymentAmt - overDuePlan.principal - overDuePlan.interest - overDuePlan.overdueFee
					- overDuePlan.collectionFee;
			overDuePlan.principal = 0.00;
			overDuePlan.interest = 0.00;
			overDuePlan.sum = 0.00;
			overDuePlan.overdueFee = 0.00;
			overDuePlan.collectionFee = 0.00;
			overDuePlan.serviceFee = 0.00;
			overDuePlan.compensateFee = overDuePlan.compensateFee - tmp;
			overDuePlan.amount = overDuePlan.compensateFee;

		}
		// 更新status list对应期的状态
		if (overDuePlan.amount + overDuePlan.overdueFee + overDuePlan.collectionFee == 0) {
			MapStatus.listRes.get(index - 1).status[huankuanqishu - 1] = 1;
			MapStatus.listRes.get(index - 1).flag = 0;
		}
	}

	/**
	 * 非首期还款
	 * 
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
	public void normalRepayment(Double repaymentAmt, String repaymentDate, Double originalAmt, Date startTime,
			Double rililv, Double yuelilv, int fenqi, int huankuanqishu, int index, int numInstallmentByFee)
			throws ParseException// 手续费的分期期数
	{
		// Double mustRepaymentAmt = 0.00;
		// Double shouldRepaymentAmt = 0.00;
		// int preDays = 0;
		MapStatus.listRes.get(index - 1).paymentList.add(repaymentDate);

		/*
		 * //如果逾期，计算逾期应还的金额 Double yuqiyinghuan = MapStatus.listRes.get(index -
		 * 1).newPlan[huankuanqishu - 2].amount + preNewPlan.faxi +
		 * preNewPlan.cuifei;
		 */
		// 确认本金逾期或者利息逾期
		NewPlan plan = MapStatus.listRes.get(index - 1).newPlan.get(huankuanqishu - 2);
		if (huankuanqishu > 2 && plan.overdueFee > 0.00 && plan.collectionFee > 0.00 &&
		// 还款日期大于当期账单日
				MapStatus.listRes.get(index - 1).isBehindOrOutOfDue == 0) {
			// 逾期还款
			overDueRepayment(repaymentAmt, repaymentDate, originalAmt, startTime, rililv, yuelilv, fenqi, huankuanqishu,
					index, numInstallmentByFee);
		} // 逾期还清，进入下一期还款
		else {
			baseRepayment(repaymentAmt, repaymentDate, originalAmt, startTime, rililv, yuelilv, fenqi, huankuanqishu,
					index, numInstallmentByFee);
		}

	}

	/**
	 * 正常提前还款
	 * 
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
	// 正常还款
	public void baseRepayment(Double repaymentAmt, String repaymentDate, Double originalAmt, Date startTime,
			Double rililv, Double yuelilv, int fenqi, int huankuanqishu, int index, int numInstallmentByFee)
			throws ParseException {

		Double mustRepaymentAmt = 0.00;
		Double shouldRepaymentAmt = 0.00;
		int preDays = 0;
		// MapStatus.listRes.get(index-1).paymentList.add(repaymentDate);

		// 本期之外的剩余加赔
		Double shengYuJiaPei = 0.00;
		// 本期之外的剩余本金
		Double shengYuBenJin = 0.00;
		// 所有的剩余本金
		Double allShengYuBenJin = 0.00;
		// 本期之外的未还手续费
		Double shengYuShouXuFei = 0.00;

		for (int i = huankuanqishu - 1; i < (MapStatus.listRes.get(index - 1).newPlan.size() + 1); i++) {
			NewPlan plan = MapStatus.listRes.get(index - 1).newPlan.get(index - 1);
			shengYuJiaPei += plan.compensateFee;
			shengYuShouXuFei += plan.serviceFee;
			shengYuBenJin += plan.principal;
			allShengYuBenJin = shengYuBenJin;
		}

		// 获取当前plan与上一期plan
		NewPlan newPlan = MapStatus.listRes.get(index - 1).newPlan.get(huankuanqishu - 1);
		NewPlan preNewPlan = MapStatus.listRes.get(index - 1).newPlan.get(huankuanqishu - 2);

		// int preDays = MainWindow.DateDiff(DateTime.Parse(repaymentDate),
		// DateTime.Parse(plan[0].hkr));
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
		if (MapStatus.listRes.get(index - 1).firstRepayment[huankuanqishu - 1] == 0) {
			preDays = helper.dateDiff(f.parse(preNewPlan.repaymentDay), f.parse(repaymentDate));
			if (preDays == 0) {
				preDays = 1;
			}
		} else {
			preDays = helper.dateDiff(f.parse(MapStatus.listRes.get(index - 1).lastRepaymentDate),
					f.parse(repaymentDate));

		}
		Double currentLx = 0.00;
		if (helper.dateDiff(f.parse(repaymentDate), f.parse(newPlan.repaymentDay)) == 0) {
			currentLx = newPlan.interest;
		} else {
			currentLx = helper.getSiSheWuBuRu(allShengYuBenJin * rililv * preDays);
		}

		// 记录本次支付的利息用来汇总
		Double paidlixi = currentLx;

		// 计算应还和必还
		shouldRepaymentAmt = currentLx + newPlan.compensateFee + newPlan.principal + newPlan.serviceFee
				+ preNewPlan.interest;
		mustRepaymentAmt = currentLx + newPlan.compensateFee + preNewPlan.interest + newPlan.serviceFee;

		// 还款
		if (Date.parse(repaymentDate) <= Date.parse(newPlan.repaymentDay)) {
			if (repaymentAmt < mustRepaymentAmt) {
				System.out.println("还款金额小于必还项");
				return;
			}

			// 重置上次还款时间
			MapStatus.listRes.get(index - 1).lastRepaymentDate = repaymentDate;

			// 标记是不是第一次还款
			MapStatus.listRes.get(index - 1).firstRepayment[huankuanqishu - 1] = 1;

			// 更新本期还款状态
			MapStatus.listRes.get(index - 1).status[huankuanqishu - 1] = 1;

			// 判断是逾期利息还是置后利息
			if (repaymentAmt >= newPlan.amount) {
				MapStatus.listRes.get(index - 1).isBehindOrOutOfDue = 1;
			} else {
				MapStatus.listRes.get(index - 1).isBehindOrOutOfDue = 0;
			}

			if (repaymentAmt == mustRepaymentAmt) {
				// 还款金额等于必还项
				newPlan.loanDays = newPlan.loanDays - preDays;
				newPlan.interest = newPlan.interest - currentLx;
				newPlan.sum = newPlan.principal + newPlan.interest;
				newPlan.compensateFee = 0.00;
				newPlan.serviceFee = 0.00;
				newPlan.amount = newPlan.sum + newPlan.compensateFee + newPlan.serviceFee;

				// 还清上一期的置后利息，上一期相关项金额清零
				preNewPlan.interest = 0.00;
				preNewPlan.amount = 0.00;
				preNewPlan.sum = 0.00;
			} else if ((repaymentAmt > mustRepaymentAmt) && (repaymentAmt - mustRepaymentAmt) <= newPlan.principal) {
				// 还款金额大于必还项，超出部分不大于本期本金
				Double shenyubenj = 0.00;

				newPlan.principal = newPlan.principal - (repaymentAmt - mustRepaymentAmt);

				// 更新剩余所有未还本金
				for (NewPlan pl : MapStatus.listRes.get(index - 1).newPlan) {
					shenyubenj += pl.principal;
				}
				newPlan.loanDays = newPlan.loanDays - preDays;
				newPlan.interest = helper.getSiSheWuBuRu(shenyubenj * newPlan.loanDays * rililv);
				newPlan.sum = newPlan.principal + newPlan.interest;
				newPlan.compensateFee = 0.00;
				newPlan.serviceFee = 0.00;
				newPlan.amount = newPlan.sum + newPlan.compensateFee + newPlan.serviceFee;
				preNewPlan.interest = 0.00;
				preNewPlan.amount = 0.00;
				preNewPlan.sum = 0.00;

			} else if (repaymentAmt > mustRepaymentAmt && (repaymentAmt - mustRepaymentAmt) > newPlan.principal
					&& (repaymentAmt - mustRepaymentAmt - newPlan.principal) <= shengYuShouXuFei) {
				// 还款金额大于应还项，超出部分小于剩余各期总手续费
				// 计算多余部分
				Double outOfShould = repaymentAmt - mustRepaymentAmt - newPlan.principal;

				newPlan.principal = 0.00;

				// 更新剩余所有未还本金
				Double shenyubenj = 0.00;
				for (NewPlan pl : MapStatus.listRes.get(index - 1).newPlan) {
					shenyubenj += pl.principal;
				}
				newPlan.loanDays = newPlan.loanDays - preDays;
				newPlan.interest = helper.getSiSheWuBuRu(shenyubenj * newPlan.loanDays * rililv);
				newPlan.sum = newPlan.interest;
				newPlan.amount = newPlan.interest;
				newPlan.compensateFee = 0.00;
				newPlan.serviceFee = 0.00;
				preNewPlan.interest = 0.00;
				preNewPlan.amount = 0.00;
				preNewPlan.sum = 0.00;

				// 多余部分还了剩余的总手续费，后面各期还款计划改变
				Double tmp = (double) Math
						.round((shengYuShouXuFei - outOfShould) / (numInstallmentByFee - huankuanqishu));
				for (int i = huankuanqishu; i < numInstallmentByFee; i++) {
					Double shouxufeilast = 0.00;
					NewPlan p1 = MapStatus.listRes.get(index - 1).newPlan.get(i);
					if (i == numInstallmentByFee - 1) {
						for (int j = 0; j < numInstallmentByFee - 1; j++) {
							NewPlan plan = MapStatus.listRes.get(index - 1).newPlan.get(j);
							shouxufeilast += plan.serviceFee;
						}

						p1.serviceFee = shengYuShouXuFei - outOfShould - shouxufeilast;
						p1.amount = p1.sum + p1.compensateFee + p1.serviceFee;
					} else {
						p1.serviceFee = tmp;
						p1.amount = p1.sum + tmp + p1.compensateFee;
					}
				}
			} else if (repaymentAmt > mustRepaymentAmt && (repaymentAmt - mustRepaymentAmt) > newPlan.principal
					&& (repaymentAmt - mustRepaymentAmt - newPlan.principal) > shengYuShouXuFei
					&& (repaymentAmt - mustRepaymentAmt - newPlan.principal - shengYuShouXuFei) <= shengYuShouXuFei) {
				// 还款金额大于应还项，超出部分小于剩余各期总加赔
				// 计算多余部分
				Double outOfShould = repaymentAmt - mustRepaymentAmt - newPlan.principal;

				newPlan.principal = 0.00;

				// 更新剩余所有未还本金
				Double shenyubenj = 0.00;
				for (NewPlan pl : MapStatus.listRes.get(index - 1).newPlan) {
					shenyubenj += pl.principal;
				}
				newPlan.loanDays = newPlan.loanDays - preDays;
				newPlan.interest = helper.getSiSheWuBuRu(shenyubenj * newPlan.loanDays * rililv);
				newPlan.sum = newPlan.interest;
				newPlan.amount = newPlan.interest;
				newPlan.compensateFee = 0.00;
				newPlan.serviceFee = 0.00;
				preNewPlan.interest = 0.00;
				preNewPlan.amount = 0.00;
				preNewPlan.sum = 0.00;

				// 多余部分还了剩余的总加赔，后面各期还款计划改变
				Double tmp = (double) Math.round((shengYuJiaPei - outOfShould) / (fenqi - huankuanqishu));
				for (int i = huankuanqishu; i < fenqi; i++) {
					Double jiapeilast = 0.00;
					NewPlan p1 = MapStatus.listRes.get(index - 1).newPlan.get(i);
					if (i + 1 == fenqi) {
						for (int j = 0; j < (fenqi - 1); j++) {
							NewPlan plan = MapStatus.listRes.get(index - 1).newPlan.get(j);
							jiapeilast += plan.compensateFee;
						}
						p1.compensateFee = shengYuJiaPei - outOfShould - jiapeilast;
						p1.serviceFee = 0.00;
						p1.amount = p1.sum + p1.compensateFee;
					} else {
						p1.compensateFee = tmp;
						p1.serviceFee = 0.00;
						p1.amount = p1.sum + tmp;
					}
				}
			} else if (repaymentAmt > mustRepaymentAmt && (repaymentAmt - mustRepaymentAmt) > newPlan.principal
					&& (repaymentAmt - mustRepaymentAmt - newPlan.principal - shengYuJiaPei) < shengYuBenJin) {
				// 还款金额大于应还项+剩余各期总加赔，超出部分小于剩余总的本金
				int tmpDay = newPlan.loanDays - preDays;
				Double tmp = shengYuBenJin - (repaymentAmt - mustRepaymentAmt - newPlan.principal - shengYuJiaPei);
				computeRepaymentPlan(tmp, fenqi, tmpDay, huankuanqishu, rililv, yuelilv, index);
			} else if (repaymentAmt > mustRepaymentAmt && (repaymentAmt - mustRepaymentAmt) > newPlan.principal
					&& (repaymentAmt - mustRepaymentAmt - newPlan.principal - shengYuJiaPei) >= shengYuBenJin) {
				// 全部还清
				for (int i = 0; i < fenqi; i++) {
					NewPlan plan = MapStatus.listRes.get(index - 1).newPlan.get(i);
					plan.sum = 0.00;
					plan.principal = 0.00;
					plan.interest = 0.00;
					plan.compensateFee = 0.00;
					plan.amount = 0.00;

				}
				preNewPlan.interest = 0.00;

			}
		}

	}
}
