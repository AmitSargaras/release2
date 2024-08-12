/*
 * Created on Jun 27, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.secapportion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.cms.app.collateral.bus.ISecApportionment;
import com.integrosys.cms.app.collateral.bus.OBSecApportionLmtDtl;
import com.integrosys.cms.app.collateral.bus.OBSecApportionmentDtl;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecApportionmentUtil {
	// used when create new security apportionment, display available collateral
	// amount as
	// total FSV amount - total priority ranking amount for previous
	// apportionment
	// deprecated method
	public static double getAvailableCollateralAmt(double totalFsv, List curApportionmentList) {
		if (curApportionmentList != null) {
			double curFsvBal = totalFsv;
			for (int i = 0; i < curApportionmentList.size(); i++) {
				ISecApportionment nextApportionment = (ISecApportionment) (curApportionmentList.get(i));
				curFsvBal = curFsvBal - nextApportionment.getApportionAmount();
			}
			if (curFsvBal < 0) {
				return 0;
			}
			else {
				return curFsvBal;
			}
		}
		return 0;
	}

	public static List getAvailableAmtForCharge(double fsvAmt, List limitDtlList, List curApportionmentList) {
		List result = new ArrayList();
		List visitedCharge = new ArrayList();
		try {
			if ((limitDtlList != null) && (curApportionmentList != null)) {
				// first recalculate apportionment amount to ensure that
				// apportion amount is set for each existing apportionment
				recalColApportionAmt(fsvAmt, curApportionmentList, true);
				for (int i = 0; i < limitDtlList.size(); i++) {
					OBSecApportionLmtDtl dtl = (OBSecApportionLmtDtl) (limitDtlList.get(i));
					if (!visitedCharge.contains("" + dtl.getChargeId())) {
						// for each charge, set available collateral amount =
						// min(fsv - total apportionment, charge - total
						// apportionment under the charge
						double chargeAmt = dtl.getChargeAmount();
						double fsvBal = Math.max(fsvAmt, 0);
						for (int j = 0; j < curApportionmentList.size(); j++) {
							ISecApportionment nextApportionment = (ISecApportionment) (curApportionmentList.get(j));
							fsvBal = fsvBal - nextApportionment.getApportionAmount();
							if (nextApportionment.getChargeDetailId() == dtl.getChargeId()) {
								chargeAmt = chargeAmt - nextApportionment.getApportionAmount();
							}
						}
						visitedCharge.add("" + dtl.getChargeId());
						List nextRec = new ArrayList();
						nextRec.add("" + dtl.getChargeId());
						nextRec.add("" + ApportionSummaryViewHelper.roundUp(Math.min(chargeAmt, fsvBal), 2));
						result.add(nextRec);
					}
				}
			}
		}
		catch (Exception ex) {
		}
		return result;
	}

	// recalculate the available collateral amount and priority ranking amount
	// for all apportionments, in case of FSV change
	// requirePersist controls whether the calculated priority ranking amount
	// need to be persisted into the object (required for maker update)
	// return: a map of available collateral amount for each apportionment
	// identified by priority ranking
	public static Map recalColApportionAmt(double totalFsv, List curApportionmentList, boolean requirePersist) {
		Map result = new HashMap();
		if (curApportionmentList != null) {
			double curFsvBal = totalFsv;
			if (curFsvBal < 0) {
				curFsvBal = 0;
			}
			// store charge id and remaining amount for the charge
			Map chargeMap = new HashMap();
			double fsvBal = totalFsv;
			if (fsvBal < 0) {
				fsvBal = 0;
			}
			for (int i = 0; i < curApportionmentList.size(); i++) {
				OBSecApportionmentDtl apportionDtl = (OBSecApportionmentDtl) (curApportionmentList.get(i));
				String curChargeId = "" + apportionDtl.getChargeDetailId();
				// System.out.println("************* CHARGE ID IS " +
				// curChargeId);
				if (!chargeMap.containsKey(curChargeId)) {
					double availableAmt = Math.min(fsvBal, apportionDtl.getChargeAmount());
					// System.out.println(
					// "******************************AVAILABLE AMOUNT IS: " +
					// availableAmt);
					double apportionment = getApportionedAmt(totalFsv, apportionDtl.getChargeAmount(), availableAmt,
							apportionDtl);
					// System.out.println(
					// "******************************APPORTIONED AMOUNT IS: " +
					// apportionment);
					chargeMap.put(curChargeId, new Double(availableAmt - apportionment));
					apportionDtl.setApportionAmount(apportionment);
					result.put("" + apportionDtl.getPriorityRanking(), new Double(availableAmt));
					fsvBal = Math.max((fsvBal - apportionment), 0);
				}
				else {
					double availableAmt = Math.min(fsvBal, ((Double) (chargeMap.get(curChargeId))).doubleValue());
					// System.out.println(
					// "******************************AVAILABLE AMOUNT IS: " +
					// availableAmt);
					double apportionment = getApportionedAmt(totalFsv, apportionDtl.getChargeAmount(), availableAmt,
							apportionDtl);
					// System.out.println(
					// "******************************APPORTIONED AMOUNT IS: " +
					// apportionment);
					chargeMap.put(curChargeId, new Double(availableAmt - apportionment));
					apportionDtl.setApportionAmount(apportionment);
					result.put("" + apportionDtl.getPriorityRanking(), new Double(availableAmt));
					fsvBal = Math.max((fsvBal - apportionment), 0);
				}

			}
		}
		return result;
	}

	public static double getApportionedAmt(double fsv, double chargeAmount, double availableAmt,
			ISecApportionment curApportionment) {
		if (fsv >= chargeAmount) {
			if (ICMSConstant.SEC_APPORTION_PERC.equals(curApportionment.getPercAmtInd())) {
				return Math.min(availableAmt, chargeAmount * curApportionment.getByPercentage() / 100.0);
			}
			else if (ICMSConstant.SEC_APPORTION_AMT.equals(curApportionment.getPercAmtInd())) {
				return Math.min(availableAmt, curApportionment.getByAbsoluteAmt());
			}
			else {
				return 0;
			}
		}
		else {
			return Math.min(availableAmt, curApportionment.getPriorityRankingAmount());
		}
	}

}
