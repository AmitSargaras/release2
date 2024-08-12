/*
 * Created on Mar 27, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput;

import java.util.Collections;
import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CommonUtil {
	public static boolean checkWip(String event, ITrxValue value) {
		if ("prepare_update".equals(event) || "maker_prepare_edit".equals(event)
				||"prepare_update_rejected".equals(event)) {
			String status = value.getStatus();
			if (ICMSConstant.STATE_DRAFT.equals(status) || ICMSConstant.STATE_PENDING_UPDATE.equals(status)
					|| ICMSConstant.STATE_REJECTED.equals(status) || ICMSConstant.STATE_PENDING_DELETE.equals(status)
					|| ICMSConstant.STATE_REJECTED_DELETE.equals(status)) {
				return true;
			}
		}
		return false;
	}

	public static boolean checkDeleteWip(String event, ITrxValue value) {
		if ("prepare_delete".equals(event) || "maker_prepare_delete".equals(event)) {
			String status = value.getStatus();
			if (ICMSConstant.STATE_DRAFT.equals(status) || ICMSConstant.STATE_PENDING_UPDATE.equals(status)
					|| ICMSConstant.STATE_REJECTED.equals(status) || ICMSConstant.STATE_PENDING_DELETE.equals(status)
					|| ICMSConstant.STATE_REJECTED_DELETE.equals(status)) {
				return true;
			}
		}
		return false;
	}

	public static List sortDropdown(List l) {
		Collections.sort(l, new CommonLbVlBeanComparator());
		return l;
	}

	public static String roundUp(double d, int noofdigit) {
		try {
			return new java.math.BigDecimal(d).setScale(noofdigit, java.math.BigDecimal.ROUND_HALF_UP).toString();
		}
		catch (Exception ex) {
		}
		return "";
	}
}
