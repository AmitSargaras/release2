/*
 * Created on Jun 27, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.secapportion;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ISecApportionment;
import com.integrosys.cms.app.collateral.bus.OBSecApportionmentDtl;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecApportionmentMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE } };
	}

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		try {
			Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			SecApportionmentForm curForm = (SecApportionmentForm) cForm;
			ICollateralTrxValue itrxValue = ((ICollateralTrxValue) inputs.get("serviceColObj"));
			OBSecApportionmentDtl apportionDtl = new OBSecApportionmentDtl();
			apportionDtl.setPriorityRanking(Integer.parseInt(curForm.getPriorityRank()));
			apportionDtl.setCurrencyCode(curForm.getFsvCurrency());
			apportionDtl.setPriorityRankingAmount(Double.parseDouble(ApportionSummaryViewHelper.removeDot(curForm
					.getPriorityRankingAmt())));
//			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ " + apportionDtl.getPriorityRankingAmount());
			// apportionDtl.setApportionAmount(Double.parseDouble(
			// ApportionSummaryViewHelper
			// .removeDot(curForm.getApportionAmt())));
			apportionDtl.setLeId(curForm.getLeId());
			apportionDtl.setSubProfileId(curForm.getSubProfileId());
			apportionDtl.setLimitID(Long.parseLong(curForm.getLimitId()));
			apportionDtl.setChargeDetailId(Long.parseLong(curForm.getChargeDtlId()));
			if (isEmptyOrNull(curForm.getPercAmtInd())) {
				apportionDtl.setPercAmtInd(ICMSConstant.SEC_APPORTION_NA);
			}
			else {
				apportionDtl.setPercAmtInd(curForm.getPercAmtInd());
			}
			if (ICMSConstant.SEC_APPORTION_AMT.equals(curForm.getPercAmtInd()) && (curForm.getByAbsoluteAmt() != null)) {
				apportionDtl.setByAbsoluteAmt(Double.parseDouble(ApportionSummaryViewHelper.removeDot(curForm
						.getByAbsoluteAmt())));
			}
			else if (ICMSConstant.SEC_APPORTION_PERC.equals(curForm.getPercAmtInd())
					&& (curForm.getByPercentage() != null)) {
				apportionDtl.setByPercentage(Double.parseDouble(curForm.getByPercentage()));
			}

			if (isEmptyOrNull(curForm.getMinPercAmtInd())) {
				apportionDtl.setMinPercAmtInd(ICMSConstant.SEC_APPORTION_NA);
			}
			else {
				apportionDtl.setMinPercAmtInd(curForm.getMinPercAmtInd());
			}
			if (ICMSConstant.SEC_APPORTION_AMT.equals(curForm.getMinPercAmtInd())
					&& (curForm.getMinAbsoluteAmt() != null)) {
				apportionDtl.setMinAbsoluteAmt(Double.parseDouble(ApportionSummaryViewHelper.removeDot(curForm
						.getMinAbsoluteAmt())));
			}
			else if (ICMSConstant.SEC_APPORTION_PERC.equals(curForm.getMinPercAmtInd())
					&& (curForm.getMinPercentage() != null)) {
				apportionDtl.setMinPercentage(Double.parseDouble(curForm.getMinPercentage()));
			}

			if (isEmptyOrNull(curForm.getMaxPercAmtInd())) {
				apportionDtl.setMaxPercAmtInd(ICMSConstant.SEC_APPORTION_NA);
			}
			else {
				apportionDtl.setMaxPercAmtInd(curForm.getMaxPercAmtInd());
			}
			if (ICMSConstant.SEC_APPORTION_AMT.equals(curForm.getMaxPercAmtInd())
					&& (curForm.getMaxAbsoluteAmt() != null)) {
				apportionDtl.setMaxAbsoluteAmt(Double.parseDouble(ApportionSummaryViewHelper.removeDot(curForm
						.getMaxAbsoluteAmt())));
			}
			else if (ICMSConstant.SEC_APPORTION_PERC.equals(curForm.getMaxPercAmtInd())
					&& (curForm.getMaxPercentage() != null)) {
				apportionDtl.setMaxPercentage(Double.parseDouble(curForm.getMaxPercentage()));
			}
			return apportionDtl;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException("Exception caught mapFormToOB in SecApportionmentMapper");
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		try {
			Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			SecApportionmentForm curForm = (SecApportionmentForm) cForm;
			HashMap allInputVal = (HashMap) obj;
			OBSecApportionmentDtl apportionDtl = (OBSecApportionmentDtl) (allInputVal.get("form.newApportionObject"));
			String from_event = (String) inputs.get("from_event");
			double availableCollateralAmt = ((Double) (allInputVal.get("availableCollateralAmt"))).doubleValue();
			double curFsv = ((Double) (allInputVal.get("fsvAmt"))).doubleValue();
			if (curFsv < 0) {
				curFsv = 0;
			}
			double minFsvCharge = Math.min(curFsv, apportionDtl.getChargeAmount());
			curForm.setMinFsvCharge(String.valueOf(minFsvCharge));
			ICollateralTrxValue itrxValue = ((ICollateralTrxValue) inputs.get("serviceColObj"));
			ISecApportionment origApportionment = (ISecApportionment) (allInputVal.get("form.origApportionObject"));
			double priorityRankingAmt = apportionDtl.getPriorityRankingAmount();
			double apportionAmount = apportionDtl.getApportionAmount();

			curForm.setPriorityRank(String.valueOf(apportionDtl.getPriorityRanking()));
			curForm.setPriorityRankDisp(String.valueOf(apportionDtl.getPriorityRanking()));

			curForm.setAvailableCollateralAmt(ApportionSummaryViewHelper.addDot(roundInt(availableCollateralAmt)));
			curForm.setFsvCurrency(apportionDtl.getCurrencyCode());

			Amount prAmt = new Amount(apportionDtl.getPriorityRankingAmount(), apportionDtl.getCurrencyCode());
			curForm.setPriorityRankingAmt(ApportionSummaryViewHelper.addDot(roundInt(apportionDtl
					.getPriorityRankingAmount())));
			// curForm.setApportionAmt(roundUp(apportionAmount));

			curForm.setLeId(apportionDtl.getLeId());
			curForm.setLeIdDisp(apportionDtl.getLeId());
			curForm.setLeName(apportionDtl.getLeName());
			curForm.setSubProfileId(apportionDtl.getSubProfileId());
			curForm.setSubProfileIdDisp(apportionDtl.getSubProfileId());
			curForm.setLimitId(String.valueOf(apportionDtl.getLimitID()));
			if (allInputVal.get("limitIdDisp") != null) {
				curForm.setLimitIdDisp(allInputVal.get("limitIdDisp").toString());
			}
			curForm.setChargeDtlId(String.valueOf(apportionDtl.getChargeDetailId()));
			curForm.setChargeRank(apportionDtl.getChargeRank());
			curForm.setProductDesc(apportionDtl.getProductDesc());
			if ((apportionDtl.getApprovedLimitAmt() != null) && (apportionDtl.getApprovedLimitCcy() != null)) {
				Amount amt = new Amount(Double.parseDouble(apportionDtl.getApprovedLimitAmt()), apportionDtl
						.getApprovedLimitCcy());
				curForm.setApprovedLimitAmt(CurrencyManager.convertToString(locale, amt));
				curForm.setApprovedLimitCcy(apportionDtl.getApprovedLimitCcy());
			}
			if ((apportionDtl.getActivatedLimitAmt() != null) && (apportionDtl.getActivatedLimitAmt() != null)) {
				Amount amt = new Amount(Double.parseDouble(apportionDtl.getActivatedLimitAmt()), apportionDtl
						.getActivatedLimitCcy());
				curForm.setActivatedLimitAmt(CurrencyManager.convertToString(locale, amt));
				curForm.setActivatedLimitCcy(apportionDtl.getActivatedLimitCcy());
			}

			curForm.setPercAmtInd(apportionDtl.getPercAmtInd());
			if (ICMSConstant.SEC_APPORTION_AMT.equals(apportionDtl.getPercAmtInd())) {
				curForm.setByAbsoluteAmt(ApportionSummaryViewHelper.addDot(roundInt(apportionDtl.getByAbsoluteAmt())));

				double percentage = apportionDtl.getByAbsoluteAmt() / minFsvCharge;
				curForm.setByAbsoluteAmtDisp(roundTwoDigit(percentage * 100));
			}
			else if (ICMSConstant.SEC_APPORTION_PERC.equals(apportionDtl.getPercAmtInd())) {
				curForm.setByPercentage(roundTwoDigit(apportionDtl.getByPercentage()));
				curForm.setByPercentageDisp(ApportionSummaryViewHelper.addDot(roundInt(minFsvCharge
						* apportionDtl.getByPercentage() / 100.0)));
			}

			curForm.setMinPercAmtInd(apportionDtl.getMinPercAmtInd());
			if (ICMSConstant.SEC_APPORTION_AMT.equals(apportionDtl.getMinPercAmtInd())) {
				curForm
						.setMinAbsoluteAmt(ApportionSummaryViewHelper
								.addDot(roundInt(apportionDtl.getMinAbsoluteAmt())));

				double percentage = apportionDtl.getMinAbsoluteAmt() / minFsvCharge;
				curForm.setMinAbsoluteAmtDisp(roundTwoDigit(percentage * 100));
			}
			else if (ICMSConstant.SEC_APPORTION_PERC.equals(apportionDtl.getMinPercAmtInd())) {
				curForm.setMinPercentage(roundTwoDigit(apportionDtl.getMinPercentage()));
				curForm.setMinPercentageDisp(ApportionSummaryViewHelper.addDot(roundInt(minFsvCharge
						* apportionDtl.getMinPercentage() / 100.0)));
			}

			curForm.setMaxPercAmtInd(apportionDtl.getMaxPercAmtInd());
			if (ICMSConstant.SEC_APPORTION_AMT.equals(apportionDtl.getMaxPercAmtInd())) {
				curForm
						.setMaxAbsoluteAmt(ApportionSummaryViewHelper
								.addDot(roundInt(apportionDtl.getMaxAbsoluteAmt())));

				double percentage = apportionDtl.getMaxAbsoluteAmt() / minFsvCharge;
				curForm.setMaxAbsoluteAmtDisp(roundTwoDigit(percentage * 100));
			}
			else if (ICMSConstant.SEC_APPORTION_PERC.equals(apportionDtl.getMaxPercAmtInd())) {
				curForm.setMaxPercentage(roundTwoDigit(apportionDtl.getMaxPercentage()));
				curForm.setMaxPercentageDisp(ApportionSummaryViewHelper.addDot(roundInt(minFsvCharge
						* apportionDtl.getMaxPercentage() / 100.0)));
			}

			if ("process".equals(from_event)) {
				if ((origApportionment == null)
						|| (apportionDtl.getPriorityRankingAmount() != origApportionment.getPriorityRankingAmount())) {
					curForm.setPriorityRankingAmtChanged(true);
				}
				if ((origApportionment == null)
						|| !apportionDtl.getPercAmtInd().equals(origApportionment.getPercAmtInd())) {
					curForm.setByAmountChanged(true);
				}
				else if (ICMSConstant.SEC_APPORTION_PERC.equals(apportionDtl.getPercAmtInd())
						&& (origApportionment.getByPercentage() != apportionDtl.getByPercentage())) {
					curForm.setByAmountChanged(true);
				}
				else if (ICMSConstant.SEC_APPORTION_AMT.equals(apportionDtl.getPercAmtInd())
						&& (origApportionment.getByAbsoluteAmt() != apportionDtl.getByAbsoluteAmt())) {
					curForm.setByAmountChanged(true);
				}

				if ((origApportionment == null)
						|| !apportionDtl.getMinPercAmtInd().equals(origApportionment.getMinPercAmtInd())) {
					curForm.setMinimumChanged(true);
				}
				else if (ICMSConstant.SEC_APPORTION_PERC.equals(apportionDtl.getMinPercAmtInd())
						&& (origApportionment.getMinPercentage() != apportionDtl.getMinPercentage())) {
					curForm.setMinimumChanged(true);
				}
				else if (ICMSConstant.SEC_APPORTION_AMT.equals(apportionDtl.getMinPercAmtInd())
						&& (origApportionment.getMinAbsoluteAmt() != apportionDtl.getMinAbsoluteAmt())) {
					curForm.setMinimumChanged(true);
				}

				if ((origApportionment == null)
						|| !apportionDtl.getMaxPercAmtInd().equals(origApportionment.getMaxPercAmtInd())) {
					curForm.setMaximumChanged(true);
				}
				else if (ICMSConstant.SEC_APPORTION_PERC.equals(apportionDtl.getMaxPercAmtInd())
						&& (origApportionment.getMaxPercentage() != apportionDtl.getMaxPercentage())) {
					curForm.setMaximumChanged(true);
				}
				else if (ICMSConstant.SEC_APPORTION_AMT.equals(apportionDtl.getMaxPercAmtInd())
						&& (origApportionment.getMaxAbsoluteAmt() != apportionDtl.getMaxAbsoluteAmt())) {
					curForm.setMaximumChanged(true);
				}

				/*
				 * if (origApportionment == null ||
				 * apportionDtl.getApportionAmount() !=
				 * origApportionment.getApportionAmount()) {
				 * curForm.setApportionAmtChanged(true); }
				 */
			}
			return curForm;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException("Exception caught mapOBToForm in SecApportionmentMapper");
		}
	}

	public static String roundInt(double d) {
		try {
			return ApportionSummaryViewHelper.roundUp(d, 0);
		}
		catch (Exception ex) {
		}
		return "";
	}

	public static String roundTwoDigit(double d) {
		try {
			// return String.valueOf(Math.round(d * 100) / 100.0);
			return ApportionSummaryViewHelper.roundUp(d, 2);
		}
		catch (Exception ex) {
		}
		return "";
	}
}
