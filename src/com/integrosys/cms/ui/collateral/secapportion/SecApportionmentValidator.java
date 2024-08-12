/*
 * Created on Jul 21, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.secapportion;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ISecApportionment;
import com.integrosys.cms.app.collateral.bus.OBSecApportionLmtDtl;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecApportionmentValidator {
	public static ActionErrors validateInput(SecApportionmentForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		return errors;
	}

	public static boolean validateDuplicate(String chargeId, String limitId, ICollateralTrxValue itrxValue) {
		List secApportionmentList = itrxValue.getStagingCollateral().getSecApportionment();
		if (secApportionmentList == null) {
			return true;
		}
		for (int i = 0; i < secApportionmentList.size(); i++) {
			ISecApportionment nextApportionment = (ISecApportionment) (secApportionmentList.get(i));
			if (String.valueOf(nextApportionment.getLimitID()).equals(limitId)
					&& String.valueOf(nextApportionment.getChargeDetailId()).equals(chargeId)) {
				return false;
			}
		}
		return true;
	}

	// curInd is the current index position in the list of apportionments
	// in case of a new apportionment, the value will be null
	public static boolean validateRankDup(String curRank, ICollateralTrxValue itrxValue) {
		List secApportionmentList = itrxValue.getStagingCollateral().getSecApportionment();
		if (secApportionmentList == null) {
			return true;
		}
		int curRankInt = Integer.parseInt(curRank);
		for (int i = 0; i < secApportionmentList.size(); i++) {
			ISecApportionment nextApportionment = (ISecApportionment) (secApportionmentList.get(i));
			if (nextApportionment.getPriorityRanking() == curRankInt) {
				return false;
			}
		}
		return true;
	}

	public static boolean validateAllApportionmentAdded(List lmtDetailList, ICollateralTrxValue itrxValue) {
		if (lmtDetailList == null) {
			return true;
		}
		List secApportionmentList = itrxValue.getStagingCollateral().getSecApportionment();
		if ((secApportionmentList == null) || (secApportionmentList.size() == 0)) {
			return true;
		}
		outer: for (int i = 0; i < lmtDetailList.size(); i++) {
			OBSecApportionLmtDtl curDtl = (OBSecApportionLmtDtl) (lmtDetailList.get(i));
			long lmtId = curDtl.getCmsLspApprLmtId();
			boolean limitIdFound = false;
			for (int j = 0; j < secApportionmentList.size(); j++) {
				ISecApportionment nextApportionment = (ISecApportionment) (secApportionmentList.get(j));
				if (nextApportionment.getLimitID() == lmtId) {
					limitIdFound = true;
					continue outer;
				}
			}
			if (!limitIdFound) {
				return false;
			}
		}
		return true;
	}

	public static boolean validateApportionments(List lmtDetailList, ICollateralTrxValue itrxValue) {
		// if (lmtDetailList == null)
		// {
		// return true;
		// }
		List secApportionmentList = itrxValue.getStagingCollateral().getSecApportionment();
		if ((secApportionmentList == null) || (secApportionmentList.size() == 0)) {
			return true;
		}
		double fsvVal = 0;

		try {

			if (itrxValue.getStagingCollateral() != null) {
				ICollateral newCol = (ICollateral) AccessorUtil.deepClone(itrxValue.getStagingCollateral());
				new CollateralValuator().setCollateralCMVFSV(newCol);
				if (newCol.getFSV() != null) {

					fsvVal = newCol.getFSV().getAmount();
					itrxValue.getStagingCollateral().setFSV(newCol.getFSV());
				}
			}
			Map availableColAmtMap = SecApportionmentUtil.recalColApportionAmt(fsvVal, secApportionmentList, true);

			for (int i = 0; i < secApportionmentList.size(); i++) {
				// for each apportionment, verify that the priority ranking
				// amount will not exceed available
				// which may happen due to a change of fsv value
				ISecApportionment nextApportionment = (ISecApportionment) (secApportionmentList.get(i));
				String pRanking = "" + nextApportionment.getPriorityRanking();
				double prAmount = nextApportionment.getPriorityRankingAmount();
				double availableAmt = ((Double) (availableColAmtMap.get(pRanking))).doubleValue();
				if (availableAmt < prAmount) {
					return false;
				}
			}

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;

	}
}
