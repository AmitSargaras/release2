/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/ToTrackQueryNoCount.java,v 1.6 2005/11/07 04:05:49 hshii Exp $
 */

package com.integrosys.cms.app.discrepency.bus;

import java.util.List;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;

/**
 * method object for constructing ToTrack SQL implements the behaviour for
 * ToTrack Query without Count
 */
public class ToTrackQueryNoCount extends ToTrackQuery {

	public ToTrackQueryNoCount(List paramList, CMSTrxSearchCriteria criteria) {
		super(paramList, criteria);
	}

	protected String ToTrackQueryFields() {
		final String fields = "SELECT row_number() over(partition by A.TRANSACTION_ID order by A.TRANSACTION_ID ) rnum, A.TRX_REFERENCE_ID, A.TRANSACTION_ID, "
				+ "A.CUR_TRX_HISTORY_ID, A.REFERENCE_ID, A.TRANSACTION_TYPE, A.TRANSACTION_SUBTYPE, A.STATUS, "
				+ "A.LEGAL_ID, A.LIMIT_PROFILE_ID, A.LIMIT_PROFILE_REF_NUM, A.LEGAL_NAME as LNAME, A.CUSTOMER_NAME AS CNAME, A.CUSTOMER_ID, A.TRANSACTION_DATE, "
				+ "A.TRX_ORIGIN_COUNTRY, ' ' AS USER_ACTION, ' ' AS URL, 0 AS STATEID, A.USER_INFO, B.TOTRACK_URL, B.USER_STATE, B.USER_TRX_TYPE, "
				+ "CO.COUNTRY_NAME, SP.LSP_ID, "
				+ "SP.LSP_LE_ID, SP.LSP_SHORT_NAME AS CUSTOMER_NAME, SP.LSP_SHORT_NAME AS LEGAL_NAME, UPPER(SP.LSP_SHORT_NAME) AS UPPER_LEGAL_NAME, ' ' AS TASK_FLAG, A.DEAL_NO ";
		return fields;
	}

	public String conditionPart() {

		StringBuffer conditionPart = new StringBuffer(
				" FROM  SCI_LE_SUB_PROFILE SP  " 
//				+ " LEFT OUTER JOIN  SCI_LE_MAIN_PROFILE MP  ON  SP.CMS_LE_MAIN_PROFILE_ID  = MP.CMS_LE_MAIN_PROFILE_ID    " 
				+ " RIGHT OUTER JOIN  TRANSACTION A  ON  A.CUSTOMER_ID  = SP.CMS_LE_SUB_PROFILE_ID    " 
				+ " LEFT OUTER JOIN  SCI_LSP_LMT_PROFILE LMT  ON  A.LIMIT_PROFILE_ID  = LMT.CMS_LSP_LMT_PROFILE_ID  " 
				+ "  LEFT OUTER JOIN  COUNTRY CO  ON  A.TRX_ORIGIN_COUNTRY  = CO.COUNTRY_ISO_CODE  , "
						+ " CMS_TRX_TOTRACK B, "
						// + " TRANS_HISTORY HIS, "
						+ " CMS_TEAM_MEMBER HISM, "
						+ " CMS_TEAM_MEMBERSHIP HISMS  "
						+ "WHERE A.TRANSACTION_TYPE  = B.TRANSACTION_TYPE "
						// +
						// "AND   ((A.TRANSACTION_TYPE  in ( 'COL'  , 'LIMIT'  , 'COBORROWER_LIMIT'  ) AND (A.TRX_REFERENCE_ID  IS NULL OR A.TRX_REFERENCE_ID  = DECIMAL(A.TRANSACTION_ID, 15))) OR A.TRANSACTION_TYPE  not in ( 'COL'  , 'LIMIT'  , 'COBORROWER_LIMIT')) "
						+ "AND   A.STATUS  = B.CURR_STATE "
						+ "AND   (A.FROM_STATE  = B.FROM_STATE OR B.FROM_STATE  IS NULL) "
						+ "AND   (A.TRANSACTION_SUBTYPE  = B.TRANSACTION_SUBTYPE OR (A.TRANSACTION_SUBTYPE  IS NULL AND B.TRANSACTION_SUBTYPE IS NULL)) "
						// + "AND   A.TRANSACTION_ID  = HIS.TRANSACTION_ID "
						// + "AND   HIS.USER_ID  = HISM.USER_ID "
						+ "AND 	 A.USER_ID = HISM.USER_ID "
						+ "AND   HISM.TEAM_MEMBERSHIP_ID  = HISMS.TEAM_MEMBERSHIP_ID "
						+ "AND   HISMS.TEAM_TYPE_MEMBERSHIP_ID  = ? ");

		paramList.add(new Long(this.criteria.getTeamTypeMembershipID()));

		conditionPart
				.append(
						" AND NOT EXISTS (SELECT 1 FROM TRANSACTION A1, CMS_STATEMATRIX_ACTION B1, ")
				.append(
						"TR_STATE_MATRIX F1, CMS_TEAM C1, CMS_TEAM_TYPE_MEMBERSHIP E1 WHERE A1.TRANSACTION_TYPE = F1.STATEINS AND  ")
				.append("F1.FROMSTATE = A1.STATUS AND F1.STATEID = B1.STATE_ID AND A1.TEAM_ID = C1.TEAM_ID ")
				.append(
						"AND B1.TEAM_MEMBERSHIP_TYPE_ID = E1.TEAM_TYPE_MEMBERSHIP_ID AND E1.TEAM_TYPE_ID = C1.TEAM_TYPE_ID ")
				.append("AND B1.TEAM_MEMBERSHIP_TYPE_ID = ? ");

		paramList.add(new Long(criteria.getTeamTypeMembershipID()));

		conditionPart.append(" AND A1.TEAM_ID = ? ");
		paramList.add(new Long(criteria.getTeamID()));
		
		conditionPart.append(" AND A1.TRANSACTION_ID = A.TRANSACTION_ID ");
		
		/*
		conditionPart.append(" AND (A1.TO_GROUP_TYPE_ID IS NULL OR A1.TO_GROUP_TYPE_ID = '").append(
				ICMSConstant.LONG_INVALID_VALUE).append("' OR A1.TO_GROUP_TYPE_ID = ?))");
		
		paramList.add(String.valueOf(criteria.getTeamTypeMembershipID()));
		*/
		conditionPart.append(" ) ");

		return conditionPart.toString();

	}

}
