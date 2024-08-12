/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/ToTrackQuery.java,v 1.5 2005/12/07 08:44:48 hshii Exp $
 */

package com.integrosys.cms.app.transaction;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * method object for constructing ToTrack SQL implements the common behaviour
 * for ToTrack Query with Count and without Count
 * 
 */
public abstract class ToTrackQuery {
	/**
	 * reference to main transaction DAO
	 */
	protected List paramList;

	protected CMSTrxSearchCriteria criteria;

	private static final String TOTRACK_DAYS_KEY = "transaction.totrack.days";

	protected StringBuffer sql;

	public ToTrackQuery(List paramList, CMSTrxSearchCriteria criteria) {
		this.paramList = paramList;
		this.criteria = criteria;
		this.sql = new StringBuffer();
	}

	protected String constructSQL() {
		return new StringBuffer(ToTrackQueryFields()).append(conditionPart()).append(getDateCondition()).append(
				applyCheckListTrxFilter()).append(applyCustodianTrxFilter()).append(applyTransactionFilter()).append(
				applySortPart()).toString();
	}

	protected String getDateCondition() {
		String dkey = PropertyManager.getValue(TOTRACK_DAYS_KEY);
		DefaultLogger.debug(this, "Properties totrack days : " + dkey);
		if (dkey != null) {
			int days = Integer.parseInt(dkey);
			days *= -1;
			if (days != 0) {
				Calendar ca = Calendar.getInstance();
				ca.add(Calendar.DATE, days);

				Date dd = ca.getTime();
				if (dd == null) {
					dd = Calendar.getInstance().getTime();
				}

				paramList.add(DateUtil.clearTime(dd));

				return new StringBuffer().append(" AND A.TRANSACTION_DATE >= ? ").toString();
			}
		}
		return " ";
	}

	protected String oracleDateString(Date in) {

		if (in == null) {
			in = Calendar.getInstance().getTime();
		}
		return new StringBuffer().append("TO_DATE('").append(DateUtil.formatTime(in, "dd/MM/yyyy")).append(
				"', 'dd/mm/yyyy')").toString();
	}

	protected String applyCustodianTrxFilter() {
		// if cpc maker or checker logs in he/she should not see the CT
		// transaction
		if (isCPCUser(criteria)) {
			return new StringBuffer(" AND A.TRANSACTION_TYPE != '").append(ICMSConstant.INSTANCE_CUSTODIAN)
					.append("' ").toString();
		}
		return " ";
	}

	protected String applyCheckListTrxFilter() {
		// FAM is only interested in update checklist transactions as they
		// routed to them for approval see multi-level approval
		if (isFAMUser(criteria)) {
			return new StringBuffer(" AND ((A.TRANSACTION_TYPE = '").append(ICMSConstant.INSTANCE_CHECKLIST).append(
					"' AND A.TRANSACTION_SUBTYPE IN ('").append(ICMSConstant.TRX_TYPE_CC_CHECKLIST_RECEIPT).append(
					"','").append(ICMSConstant.TRX_TYPE_COL_CHECKLIST_RECEIPT).append("')) OR A.TRANSACTION_TYPE <> '")
					.append(ICMSConstant.INSTANCE_CHECKLIST).append("')").toString();
		}
		return " ";
	}

	/**
	 * result set returned required by the SQL
	 * @return String
	 */
	protected abstract String ToTrackQueryFields();

	/**
	 * Constructs the SQL string based on the search criteria Populates the
	 * parameter list for DBUtil to perform JDBC variables binding this method
	 * has a side effect, for building the SQL string and binding the variables
	 * @return String
	 */
	protected abstract String conditionPart();

	/**
	 * concatenates the main SQL with the condition
	 * @return String
	 */

	protected String applySortPart() {

		StringBuffer sb = new StringBuffer();

		if (this instanceof ToTrackQueryNoCount) {
			if (CMSTransactionUtil.isSortedByLegalName(criteria)) {
				sb.append(" ORDER BY UPPER_LEGAL_NAME, LNAME, A.TRANSACTION_DATE DESC, A.TRANSACTION_ID ");
			}
			else {// get field name and type of sort [asc/desc]
				if(criteria.getField()!=null&criteria.getSort()!=null){
				sb.append(" ORDER BY A."+criteria.getField().toUpperCase()+" "+criteria.getSort().toUpperCase()+" ");
				}
				else
				sb.append(" ORDER BY A.TRANSACTION_DATE DESC, A.TRANSACTION_ID ");
			}
		}
		return sb.toString();
	}

	private String applyTransactionFilter() {
		return CMSTransactionDAOQueryHelper.getTransactionFilterQuery(criteria, paramList);
	}

	protected boolean isCPCUser(CMSTrxSearchCriteria criteria) {
		return (ICMSConstant.TEAM_TYPE_CPC_MAKER == criteria.getTeamTypeMembershipID())
				|| (ICMSConstant.TEAM_TYPE_CPC_CHECKER == criteria.getTeamTypeMembershipID());
	}

	private boolean isFAMUser(CMSTrxSearchCriteria criteria) {
		return ICMSConstant.TEAM_TYPE_FAM_USER == criteria.getTeamTypeMembershipID();
	}

}
