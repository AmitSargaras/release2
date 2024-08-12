/*
 * Created on Jul 3, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.secapportion;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.collateral.bus.OBSecApportionmentDtl;
import com.integrosys.cms.app.collateral.bus.SBCollateralBusManager;
import com.integrosys.cms.app.collateral.bus.SBCollateralBusManagerHome;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ApportionSummaryViewHelper {
	public List getApportionmentSummaryList(String event, String fromEvent, Locale locale, ICollateralTrxValue itrxValue) {
		List apportionmentList = null;
		if ("read".equals(event) || ("read_return".equals(event) && "read".equals(fromEvent))) {
			apportionmentList = itrxValue.getCollateral().getSecApportionment();
		}
		else {
			apportionmentList = itrxValue.getStagingCollateral().getSecApportionment();
		}
		if ("process".equals(event) || ("process_return".equals(event) && "process".equals(fromEvent))) {
			List apportionmentListOrg = itrxValue.getCollateral().getSecApportionment();
			return getApportionSummaryList(apportionmentListOrg, apportionmentList, locale);
		}
		else {

			return getApportionSummaryList(apportionmentList, locale);
		}
	}

	public boolean apportionmentRequired(String colId) {
		try {
			SBCollateralBusManagerHome home = (SBCollateralBusManagerHome) BeanController.getEJBHome(
					ICMSJNDIConstant.SB_COLLATERAL_MGR_JNDI, SBCollateralBusManagerHome.class.getName());
			SBCollateralBusManager manager = home.create();
			return manager.checkSecurityRequireApportion(colId);
		}
		catch (Exception ex) {
		}
		return false;
	}

	public List getApportionSummaryList(List apportionmentOrg, List apportionmentStage, Locale locale) {
		List result = new ArrayList();
		if (apportionmentStage == null) {
			return result;
		}
		for (int i = 0; i < apportionmentStage.size(); i++) {
			OBSecApportionmentDtl dtl = (OBSecApportionmentDtl) (apportionmentStage.get(i));
			OBApportionSummaryLstItem summary = toSummaryObj(dtl, locale);
			OBSecApportionmentDtl origDtl = containsApportionment(dtl, apportionmentOrg);
			if (origDtl == null) {
				// newly added apportionment
				summary.setHeaderClass("indexadd");
			}
			else {
				if (!dtl.equals(origDtl)) {
					// apportionment is updated
					summary.setHeaderClass("indexdiff");
				}
				else {
					// apportionment remains unchanged
					summary.setHeaderClass("index");
				}
			}
			result.add(summary);
		}
		for (int j = 0; j < apportionmentOrg.size(); j++) {
			OBSecApportionmentDtl dtl = (OBSecApportionmentDtl) (apportionmentOrg.get(j));
			OBSecApportionmentDtl newDtl = containsApportionment(dtl, apportionmentStage);
			if (newDtl == null) {
				// apportionment is removed, but still displayed at checker
				// side,
				// row header will be in different color
				OBApportionSummaryLstItem summary = toSummaryObj(dtl, locale);
				summary.setHeaderClass("indexdel");
				result.add(summary);
			}
		}

		for (int k = 0; k < result.size(); k++) {
			OBApportionSummaryLstItem summary = (OBApportionSummaryLstItem) (result.get(k));
			if (k % 2 == 0) {
				summary.setRowClass("odd");
			}
			else {
				summary.setRowClass("even");
			}
		}
		return result;
	}

	public List getApportionSummaryList(List apportionments, Locale locale) {
		List result = new ArrayList();
		if (apportionments == null) {
			return result;
		}
		for (int i = 0; i < apportionments.size(); i++) {
			OBSecApportionmentDtl dtl = (OBSecApportionmentDtl) (apportionments.get(i));
			OBApportionSummaryLstItem summary = toSummaryObj(dtl, locale);
			summary.setHeaderClass("index");
			result.add(summary);
		}

		for (int j = 0; j < result.size(); j++) {
			OBApportionSummaryLstItem summary = (OBApportionSummaryLstItem) (result.get(j));
			if (j % 2 == 0) {
				summary.setRowClass("odd");
			}
			else {
				summary.setRowClass("even");
			}
		}
		return result;
	}

	private OBSecApportionmentDtl containsApportionment(OBSecApportionmentDtl apportionment, List listToSearch) {
		if (listToSearch == null) {
			return null;
		}
		for (int i = 0; i < listToSearch.size(); i++) {
			OBSecApportionmentDtl nextDtl = (OBSecApportionmentDtl) (listToSearch.get(i));
			if (nextDtl.getRefID() == apportionment.getRefID()) {
				return nextDtl;
			}
		}
		return null;
	}

	private OBApportionSummaryLstItem toSummaryObj(OBSecApportionmentDtl dtl, Locale locale) {
		OBApportionSummaryLstItem item = new OBApportionSummaryLstItem();
		try {

			item.setSecApportionmentId(String.valueOf(dtl.getSecApportionmentID()));
			item.setPriorityRank(String.valueOf(dtl.getPriorityRanking()));
			item.setLeIdSubProfileIdConcat(dtl.getLeId() + " / " + dtl.getSubProfileId());
			item.setLeName(dtl.getLeName());
			item.setLimitId(dtl.getLimitIdDisp());

			if (dtl.getCurrencyCode() == null) {
				item.setApportionedAmount("-");
				item.setApportionedAmtPrev("-");
			}
			else {
				Amount amt = new Amount(Double.parseDouble(roundUp(dtl.getApportionAmount(), 0)), dtl.getCurrencyCode());
				item.setApportionedAmount(CurrencyManager.convertToString(locale, amt));
				item.setApportionedAmtPrev(dtl.getCurrencyCode() + " " + roundUp(dtl.getApportionAmountPrev(), 0));
			}

			item.setRefId(String.valueOf(dtl.getRefID()));
			item.setHyperLinkLabel("View");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return item;
	}

	public static String roundUp(double d, int noofdigit) {
		try {
			// return Math.round(d * 100) / 100.0;
			return new java.math.BigDecimal(d).setScale(noofdigit, java.math.BigDecimal.ROUND_HALF_UP).toString();
		}
		catch (Exception ex) {
		}
		return "";
	}

	public static String removeDot(String s) {
		String res = "";
		if (s != null) {
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) != ',') {
					res = res + s.charAt(i);
				}
			}
		}
		return res;
	}

	public static String addDot(String s) {
		String res = "";
		if (s != null) {
			for (int i = 0; i < s.length(); i++) {
				res = s.charAt(s.length() - 1 - i) + res;
				if ((i % 3 == 2) && (i != s.length() - 1)) {
					res = "," + res;
				}
			}
		}
		return res;
	}

}
