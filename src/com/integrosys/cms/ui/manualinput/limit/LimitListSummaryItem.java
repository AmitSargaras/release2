/*
 * Created on Mar 8, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.List;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.limit.bus.LimitListSecItemBase;
import com.integrosys.cms.app.limit.bus.LimitListSummaryItemBase;
import com.integrosys.cms.ui.common.constant.ICategoryEntryConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LimitListSummaryItem extends LimitListSummaryItemBase {
	public LimitListSummaryItem(LimitListSummaryItemBase itemBase, Locale locale) {
		AccessorUtil.copyValue(itemBase, this);
		this.locale = locale;
	}

	private Locale locale;

	/**
	 * @return Returns the locale.
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale The locale to set.
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/*
	 * public String getFacilityDesc() { try { return
	 * CommonDataSingleton.getCodeCategoryLabelByValue
	 * (ICategoryEntryConstant.FACILITY_DESCRIPTION, getFacilityTypeCode()); }
	 * catch(Exception ex) {} return ""; }
	 */

	public String getFacilityDesc() {
		try {
			return getProdTypeCode();
		}
		catch (Exception ex) {
		}
		return "";
	}

	public String getOuterLimitIdDisp() {
		String outerLmtId = getOuterLimitId();
		if ((outerLmtId != null) && !outerLmtId.equals("") && !outerLmtId.equals("0")) {
			return outerLmtId;
		}
		else {
			return "-";
		}
	}

	public String getApproveAmountDisp() {
		try {
			String res = getApprovedAmount();
			return res;
		}
		catch (Exception ex) {
		}
		return "";
	}

	public String getAuthorizedAmountDisp() {
		try {
			String res = CurrencyManager.convertToString(getLocale(), getAuthorizedAmount());
			return res;
		}
		catch (Exception ex) {
		}
		return "";
	}

	public String getDrawingAmountDisp() {
		try {
			String res =  getDrawingAmount();
			return res;
		}
		catch (Exception ex) {
		}
		return "";
	}

	public String getOutstandingAmountDisp() {
		try {
			String res = CurrencyManager.convertToString(getLocale(), getOutstandingAmount());
			return res;
		}
		catch (Exception ex) {
		}
		return "";
	}

	public String getActualSecCoverageDisp() {
		try {
			if (getActualSecCoverage() != null) {
				return getActualSecCoverage();
			}
			else {
				return "-";
			}
		}
		catch (Exception ex) {
		}
		return "-";
	}

	public String getSecurityIdListDisp(int index) {
		String res = "";
		List secList = getSecItemList();
		if ((secList != null) && (secList.size() > index)) {
			LimitListSecItemBase secItem = (LimitListSecItemBase) (secList.get(index));
			res = secItem.getSecurityId();
		}
		return res;
	}

	public String getSecTypeSubtypeListDisp(int index) {
		String res = "";
		List secList = getSecItemList();
		if ((secList != null) && (secList.size() > index)) {

			LimitListSecItemBase secItem = (LimitListSecItemBase) (secList.get(index));
			res = secItem.getSecTypeName() + "/" + secItem.getSecSubtypeName();

		}
		return res;
	}

	public String getSecLocListDisp(int index) {
		String res = "";
		List secList = getSecItemList();
		if ((secList != null) && (secList.size() > index)) {

			LimitListSecItemBase secItem = (LimitListSecItemBase) (secList.get(index));
			res = secItem.getSecLocDesc() == null ? "-" : secItem.getSecLocDesc();

		}
		return res;
	}

	public String getSecOrgListDisp(int index) {
		String res = "";
		List secList = getSecItemList();
		if ((secList != null) && (secList.size() > index)) {

			LimitListSecItemBase secItem = (LimitListSecItemBase) (secList.get(index));
			res = secItem.getSecOrgDesc() == null ? "-" : secItem.getSecOrgDesc();
		}
		return res;
	}

	public String getAllowDelete() {
		List secList = getSecItemList();
		if ((getInnerLimitCount() == 0) && (getLinkSecCount() == 0)) {
			return "Y";
		}
		else {
			return "N";
		}
	}

	
}
