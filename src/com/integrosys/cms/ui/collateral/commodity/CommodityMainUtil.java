/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/CommodityMainUtil.java,v 1.5 2006/09/15 12:41:34 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity;

import javax.servlet.http.HttpSession;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.ILoanLimit;
import com.integrosys.cms.app.collateral.bus.type.commodity.IPreCondition;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBPreCondition;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/15 12:41:34 $ Tag: $Name: $
 */
public class CommodityMainUtil {
	public static boolean hasActualAppCommType(ICommodityCollateral actualCol, long commonRef) {
		if (actualCol == null) {
			return false;
		}
		if ((actualCol.getApprovedCommodityTypes() == null) || (actualCol.getApprovedCommodityTypes().length == 0)) {
			return false;
		}
		for (int i = 0; i < actualCol.getApprovedCommodityTypes().length; i++) {
			if (actualCol.getApprovedCommodityTypes()[i].getCommonRef() == commonRef) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasActualContract(ICommodityCollateral actualCol, long commonRef) {
		if (actualCol == null) {
			return false;
		}
		if ((actualCol.getContracts() == null) || (actualCol.getContracts().length == 0)) {
			return false;
		}
		for (int i = 0; i < actualCol.getContracts().length; i++) {
			if (actualCol.getContracts()[i].getCommonRef() == commonRef) {
				return true;
			}
		}
		return false;
	}

	public static boolean isPreConditionChanged(ICommodityCollateral col, long limitProfileID, String preCondition) {
		IPreCondition preCond = col.retrievePreCondition(limitProfileID);
		preCondition = preCondition != null ? preCondition.trim() : preCondition;

		if (preCond != null) {
			String strObjPreCond = preCond.getPreCondition() != null ? preCond.getPreCondition().trim() : preCond
					.getPreCondition();

			if (((strObjPreCond == null) || (strObjPreCond.length() == 0)) && (preCondition != null)
					&& (preCondition.length() > 0)) {
				return true;
			}
			if ((strObjPreCond != null) && (strObjPreCond.length() > 0)
					&& ((preCondition == null) || (preCondition.length() == 0))) {
				return true;
			}
			if ((strObjPreCond != null) && (strObjPreCond.length() > 0) && (preCondition != null)
					&& (preCondition.length() > 0) && !strObjPreCond.equals(preCondition)) {
				return true;
			}
		}
		if ((preCondition != null) && (preCondition.length() > 0)) {
			return true;
		}
		return false;
	}

	public static ICommodityCollateral updatePreCondition(ICommodityCollateral col, String preCondition,
			ICommonUser user, long limitProfileID) {
		OBPreCondition preCond = (OBPreCondition) col.retrievePreCondition(limitProfileID);
		if (preCond == null) {
			preCond = new OBPreCondition();
		}

		preCond.setPreCondition(preCondition);
		preCond.setUpdateDate(DateUtil.getDate());
		preCond.setUserID(user.getUserID());
		preCond.setUserInfo(user.getUserName());
		preCond.setLimitProfileID(limitProfileID);
		col.setPreCondition(preCond);

		return col;
	}

	public static boolean isCurrUserCMTMaker(HttpSession session) {
		ICommonUser user = (ICommonUser) session.getAttribute(ICommonEventConstant.GLOBAL_SCOPE + "."
				+ IGlobalConstant.USER);
		ITeam userTeam = (ITeam) session.getAttribute(ICommonEventConstant.GLOBAL_SCOPE + "."
				+ IGlobalConstant.USER_TEAM);
		for (int i = 0; i < userTeam.getTeamMemberships().length; i++) {
			// parse team membership to validate user first
			for (int j = 0; j < userTeam.getTeamMemberships()[i].getTeamMembers().length; j++) {
				// parse team memebers to get the team member first..
				if (userTeam.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser().getUserID() == user
						.getUserID()) {
					if (userTeam.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID() == ICMSConstant.TEAM_TYPE_CMT_MAKER) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isInnerLimit(ILimit limit) {
		if (limit == null) {
			return false;
		}
		return ((limit.getOuterLimitRef() != null) && (limit.getOuterLimitRef().length() > 0) && !limit
				.getOuterLimitRef().equals("0"));
	}

	public static boolean isInnerLimit(ICoBorrowerLimit limit) {
		if (limit == null) {
			return false;
		}
		return ((limit.getOuterLimitRef() != null) && (limit.getOuterLimitRef().length() > 0) && !limit
				.getOuterLimitRef().equals("0"));
	}

	public static String getLoanLimitLimitID(ILoanLimit loanLimit) {
		String limitID = null;
		if (ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER.equals(loanLimit.getCustomerCategory())) {
			limitID = ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER + String.valueOf(loanLimit.getLimitID());
		}
		else if (ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER.equals(loanLimit.getCustomerCategory())) {
			limitID = ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER + String.valueOf(loanLimit.getCoBorrowerLimitID());
		}
		return limitID;
	}
}
