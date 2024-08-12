/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/ToTrackQueryCount.java,v 1.5 2005/10/21 11:09:55 hshii Exp $
 */
package com.integrosys.cms.app.transaction;

import java.util.List;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * method object for constructing ToTrack SQL implements the behaviour for
 * ToTrack Query without Count
 */
public class ToTrackQueryCount extends ToTrackQuery {

	public ToTrackQueryCount(List paramList, CMSTrxSearchCriteria criteria) {
		super(paramList, criteria);
	}

	protected String ToTrackQueryFields() {
		return "SELECT COUNT(DISTINCT A.TRANSACTION_ID) ";
	}

	protected String conditionPart() {
		StringBuffer conditionPart = new StringBuffer()
				.append(getFromPart())
				.append("WHERE A.TRANSACTION_TYPE = B.TRANSACTION_TYPE AND A.STATUS = B.CURR_STATE ")
				.append("AND (A.FROM_STATE = B.FROM_STATE OR B.FROM_STATE IS NULL) ")
				.append(
						"AND (A.TRANSACTION_SUBTYPE = B.TRANSACTION_SUBTYPE OR (A.TRANSACTION_SUBTYPE IS NULL AND B.TRANSACTION_SUBTYPE IS NULL)) ")
				//.append("AND A.TRANSACTION_ID = HIS.TRANSACTION_ID AND HIS.USER_ID = HISM.USER_ID ")
				.append("AND A.USER_ID = HISM.USER_ID ")
				.append("AND HISM.TEAM_MEMBERSHIP_ID = HISMS.TEAM_MEMBERSHIP_ID AND HISMS.TEAM_TYPE_MEMBERSHIP_ID = ? ");

		paramList.add(new Long(criteria.getTeamTypeMembershipID()));

		conditionPart
				.append(
						" AND NOT EXISTS (SELECT 1 FROM TRANSACTION A1, CMS_STATEMATRIX_ACTION B1, ")
				.append(
						"TR_STATE_MATRIX F1, CMS_TEAM C1, CMS_TEAM_TYPE_MEMBERSHIP E1 WHERE A1.TRANSACTION_TYPE = F1.STATEINS AND  ")
				.append("F1.FROMSTATE = A1.STATUS AND F1.STATEID = B1.STATE_ID AND A1.TEAM_ID = C1.TEAM_ID ")
				.append(
						"AND B1.TEAM_MEMBERSHIP_TYPE_ID = E1.TEAM_TYPE_MEMBERSHIP_ID AND E1.TEAM_TYPE_ID = C1.TEAM_TYPE_ID ")
				.append("AND B1.TEAM_MEMBERSHIP_TYPE_ID = ?");

		paramList.add(new Long(criteria.getTeamTypeMembershipID()));
		
		conditionPart.append(" AND A1.TEAM_ID = ? ");
		paramList.add(new Long(criteria.getTeamID()));
		
		conditionPart.append(" AND A1.TRANSACTION_ID = A.TRANSACTION_ID ");
		/*
		conditionPart.append(" AND (A1.TO_GROUP_TYPE_ID IS NULL OR A1.TO_GROUP_TYPE_ID = '").append(
				ICMSConstant.LONG_INVALID_VALUE).append("' OR A1.TO_GROUP_TYPE_ID = ?))");
		paramList.add(new Long(criteria.getTeamTypeMembershipID()));
		*/
		
		conditionPart.append(" ) ");
		
		String conditionSearch = getConditionSearchPart();
		if (conditionSearch != null) {
			conditionPart.append(conditionSearch);
		}

		return conditionPart.toString();

	}

	private String getFromPart() {
		StringBuffer fromString = new StringBuffer();
		if (hasSearchCriteria()) {
			fromString
					.append(
							//"FROM CMS_TRX_TOTRACK B, TRANS_HISTORY HIS, CMS_TEAM_MEMBER HISM, CMS_TEAM_MEMBERSHIP HISMS, ")
							"FROM CMS_TRX_TOTRACK B, CMS_TEAM_MEMBER HISM, CMS_TEAM_MEMBERSHIP HISMS, ")
					.append(
							"TRANSACTION A LEFT OUTER JOIN SCI_LE_SUB_PROFILE SP ON SP.CMS_LE_SUB_PROFILE_ID = A.CUSTOMER_ID ")
					.append(
							"LEFT OUTER JOIN SCI_LE_MAIN_PROFILE MP ON MP.CMS_LE_MAIN_PROFILE_ID = SP.CMS_LE_MAIN_PROFILE_ID ");
		}
		else {
			fromString.append("FROM TRANSACTION A, CMS_TRX_TOTRACK B, ").append(
					//"TRANS_HISTORY HIS, CMS_TEAM_MEMBER HISM, CMS_TEAM_MEMBERSHIP HISMS ");
					"CMS_TEAM_MEMBER HISM, CMS_TEAM_MEMBERSHIP HISMS ");
		}

		return fromString.toString();
	}

	private String getConditionSearchPart() {
		return null;
	}

	private boolean hasSearchCriteria() {
		if (((criteria.getLegalName() != null) && !criteria.getLegalName().trim().equals(""))
				|| ((criteria.getLegalID() != null) && !criteria.getLegalID().trim().equals(""))
				|| ((criteria.getLegalIDType() != null) && !criteria.getLegalIDType().trim().equals(""))
				|| ((criteria.getCustomerName() != null) && (criteria.getCustomerName().trim().length() != 0))) {
			return true;
		}
		return false;
	}
}
