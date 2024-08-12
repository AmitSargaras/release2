/* Copyright Integro Technologies Pte Ltd
 * com.integrosys.cms.app.transaction.CMSTrxSearchingParametersXA.java Created on Jun 17, 2004 6:21:47 PM
 *
 */

package com.integrosys.cms.app.transaction;

import java.util.Calendar;
import java.util.Date;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.util.DB2DateConverter;
import com.integrosys.cms.app.common.util.dataaccess.IDAODescriptor;
import com.integrosys.cms.app.common.util.dataaccess.SearchingParameters;

/**
 * @since Jun 17, 2004
 * @author heju
 * @version 1.0.0
 */
public class CMSTrxSearchCriteriaParameterizer {
	public SearchingParameters map(CMSTrxSearchCriteria criteria) {
		SearchingParameters SearchingParameters = new SearchingParameters();

		SearchingParameters.put(IDAODescriptor.QUERYTAG, criteria.getSearchIndicator());

		SearchingParameters.put(IDAODescriptor.TEAM_TYPE_MEMBERSHIP_ID, Long.toString(criteria
				.getTeamTypeMembershipID()));

		// -----
		SearchingParameters.put(IDAODescriptor.LEGAL_NAME, IDAODescriptor.NONE_STRING);
		SearchingParameters.put(IDAODescriptor.LEGAL_NAME_CONDITION, IDAODescriptor.EMPTY_STRING);
		String paraValues = "";
		if ((criteria.getLegalName() != null) && !criteria.getLegalName().trim().equals("")) {
			paraValues = criteria.getLegalName().trim().toUpperCase();
			SearchingParameters.put(IDAODescriptor.LEGAL_NAME, paraValues);
			SearchingParameters.put(IDAODescriptor.LEGAL_NAME_CONDITION, " AND UPPER(MAIN.LMP_LONG_NAME) LIKE '"
					+ paraValues + "%' ");
		}

		// -----
		SearchingParameters.put(IDAODescriptor.ALLOWED_COUNTRIES, IDAODescriptor.NONE_STRING);
		if ((criteria.getAllowedCountries() != null) && (criteria.getAllowedCountries().length > 0)) {
			for (int i = 0; i < criteria.getAllowedCountries().length; i++) {
				String s = criteria.getAllowedCountries()[i];
				paraValues += "'" + s + "'";
				if (i != criteria.getAllowedCountries().length - 1) {
					paraValues += ", ";
				}
			}
			SearchingParameters.put(IDAODescriptor.ALLOWED_COUNTRIES, paraValues);
		}
		paraValues = "";
		// -----
		SearchingParameters.put(IDAODescriptor.ALLOWED_ORGANIZATIONS, IDAODescriptor.NONE_STRING);
		if ((criteria.getAllowedOrganisations() != null) && (criteria.getAllowedOrganisations().length > 0)) {
			for (int i = 0; i < criteria.getAllowedOrganisations().length; i++) {
				String s = criteria.getAllowedOrganisations()[i];
				paraValues += "'" + s + "'";
				if (i != criteria.getAllowedOrganisations().length - 1) {
					paraValues += ", ";
				}
			}
			SearchingParameters.put(IDAODescriptor.ALLOWED_ORGANIZATIONS, paraValues);
		}
		paraValues = "";
		// -----
		SearchingParameters.put(IDAODescriptor.ALLOWED_SEGMENTS, IDAODescriptor.NONE_STRING);
		if ((criteria.getAllowedSegments() != null) && (criteria.getAllowedSegments().length > 0)) {
			for (int i = 0; i < criteria.getAllowedSegments().length; i++) {
				String s = criteria.getAllowedSegments()[i];
				paraValues += "'" + s + "'";
				if (i != criteria.getAllowedSegments().length - 1) {
					paraValues += ", ";
				}
			}
			SearchingParameters.put(IDAODescriptor.ALLOWED_SEGMENTS, paraValues);
		}
		paraValues = "";

		// -----
		SearchingParameters.put(IDAODescriptor.TRANSACTION_TYPES, IDAODescriptor.NONE_STRING);
		SearchingParameters.put(IDAODescriptor.TRANSACTION_TYPES_CONDITION, IDAODescriptor.EMPTY_STRING);
		if ((criteria.getTransactionTypes() != null) && (criteria.getTransactionTypes().length > 0)) {
			for (int i = 0; i < criteria.getTransactionTypes().length; i++) {
				String s = criteria.getTransactionTypes()[i];
				paraValues += "'" + s + "'";
				if (i != criteria.getTransactionTypes().length - 1) {
					paraValues += ", ";
				}
			}
			SearchingParameters.put(IDAODescriptor.TRANSACTION_TYPES, paraValues);
			SearchingParameters.put(IDAODescriptor.TRANSACTION_TYPES_CONDITION, " TRANS.TRANSACTION_TYPE IN ("
					+ paraValues + ")");
		}
		paraValues = "";

		// -----
		SearchingParameters.put(IDAODescriptor.STATUS, IDAODescriptor.NONE_STRING);
		if (criteria.getCurrentState() != null) {
			if (criteria.isCurrentState()) {
				paraValues = " = ";
			}
			else {
				paraValues += " != ";
			}
			paraValues += "'" + criteria.getCurrentState() + "'";
			SearchingParameters.put(IDAODescriptor.STATUS_CONDITION, paraValues);
			SearchingParameters.put(IDAODescriptor.STATUS, criteria.getCurrentState());
		}

		// -----
		SearchingParameters.put(IDAODescriptor.LIMIT_PROFILE_ID, IDAODescriptor.NEGATIVE_ONE);
		SearchingParameters.put(IDAODescriptor.LIMIT_PROFILE_ID_CONDITION, IDAODescriptor.EMPTY_STRING);
		if ((criteria.getLimitProfileID() != null)
				&& (criteria.getLimitProfileID().longValue() != 0)
				&& (criteria.getLimitProfileID().longValue() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)
				&& (criteria.getLimitProfileID().longValue() != -1)) {
			paraValues = criteria.getLimitProfileID().toString();
			SearchingParameters.put(IDAODescriptor.LIMIT_PROFILE_ID, paraValues);
			SearchingParameters.put(IDAODescriptor.LIMIT_PROFILE_ID_CONDITION, " AND TRANS.LIMIT_PROFILE_ID = "
					+ paraValues);
		}

		// -----
		// SearchingParameters.put( "TEAM_TYPE", IDAODescriptor.EMPTY_STRING);
		SearchingParameters.put("TEAM_TYPE", criteria.getTeamType());

		// -----
		SearchingParameters.put(IDAODescriptor.USER_ID, IDAODescriptor.NEGATIVE_ONE);
		SearchingParameters.put(IDAODescriptor.USER_ID_CONDITION, IDAODescriptor.EMPTY_STRING);
		SearchingParameters.put(IDAODescriptor.USER_ID_HISTORY_CONDITION, IDAODescriptor.EMPTY_STRING);

		SearchingParameters.put(IDAODescriptor.TO_USER_ID, IDAODescriptor.NEGATIVE_ONE);
		SearchingParameters.put(IDAODescriptor.TO_USER_ID_CONDITION, IDAODescriptor.EMPTY_STRING);

		if ((criteria.getUserID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)
				&& (criteria.getUserID() != -1)) {
			paraValues = Long.toString(criteria.getUserID());
			SearchingParameters.put(IDAODescriptor.USER_ID, paraValues);
			SearchingParameters.put(IDAODescriptor.TO_USER_ID, paraValues);
			if (userSpecified(criteria.getTeamType())) {
				SearchingParameters.put(IDAODescriptor.USER_ID_CONDITION, " AND TRANS.USER_ID = " + paraValues);
				SearchingParameters.put(IDAODescriptor.TO_USER_ID_CONDITION, " AND TRANS.TO_USER_ID = " + paraValues);
			}
			SearchingParameters.put(IDAODescriptor.USER_ID_HISTORY_CONDITION, " AND HIS.USER_ID = " + paraValues);

		}

		// -----
		SearchingParameters.put(IDAODescriptor.TRANSACTION_ID, IDAODescriptor.NEGATIVE_ONE);
		if ((criteria.getTransactionID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)
				&& (criteria.getTransactionID() != -1)) {
			paraValues = Long.toString(criteria.getTransactionID());
			SearchingParameters.put(IDAODescriptor.TRANSACTION_ID, paraValues);
		}

		// ----
		SearchingParameters.put(IDAODescriptor.ROWNUM_BEGIN, Integer.toString(criteria.getStartIndex()));
		SearchingParameters.put(IDAODescriptor.PAGE_SIZE, Integer.toString(criteria.getNItems()));
		SearchingParameters.put(IDAODescriptor.TRANSACTION_DATE, getDateCondition());
		return SearchingParameters;
	}

	private String getDateCondition() {
		String dkey = PropertyManager.getValue(TOTRACK_DAYS_KEY);
		DefaultLogger.debug(this, "Properties totrack days : " + dkey);
		if (dkey != null) {
			int days = Integer.parseInt(dkey);
			days *= -1;
			if (days != 0) {
				Calendar ca = Calendar.getInstance();
				ca.add(Calendar.DATE, days);
				Date dd = ca.getTime();
				// return DateUtil.formatTime(dd, "dd/MM/yyyy");
				return DB2DateConverter.getDateTimeString(DateUtil.formatTime(dd, "dd/MM/yyyy"), "dd/MM/yyyy");
			}
		}
		return " ";
	}

	private String oracleDateString(Date in) {

		if (in == null) {
			in = Calendar.getInstance().getTime();
		}
		return "TO_DATE('" + DateUtil.formatTime(in, "dd/MM/yyyy") + "', 'dd/mm/yyyy')";
	}

	private boolean userSpecified(String team_type) {
		return ((team_type != null) && (team_type.equals("GAM") || team_type.equals("SCO") || team_type.equals("RCO")));

	}

	private static final String TOTRACK_DAYS_KEY = "transaction.totrack.days";

	/*
	 * Test ONLY
	 * 
	 * @author heju
	 */
	public static void main(String[] argv) {
		CMSTrxSearchCriteria criteria = new CMSTrxSearchCriteria();
		criteria.setAllowedCountries(new String[] { "SG", "HK" });
		criteria.setAllowedOrganisations(new String[] { "SCBL", "SCMY" });
		criteria.setAllowedSegments(new String[] { "30", "40" });

		criteria.setCurrentState(true);
		criteria.setCurrentState("New");

		// criteria.setTransactionTypes(new String[]{"LIMIT","DEAL"});
		criteria.setLimitProfileID(new Long(20031127001463L));

		criteria.setStartIndex(5);
		criteria.setNItems(50);
		CMSTrxSearchCriteriaParameterizer parizer = new CMSTrxSearchCriteriaParameterizer();
		SearchingParameters searching = parizer.map(criteria);
//		System.out.println(searching);
	}
}
