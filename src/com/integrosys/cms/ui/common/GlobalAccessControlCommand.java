/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.common;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.accessprofile.bus.AccessProfileException;
import com.integrosys.cms.app.accessprofile.proxy.AccessProfileProxyFactory;
import com.integrosys.cms.app.accessprofile.proxy.IAccessProfileProxy;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.bizstructure.BizStructureHelper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.23 $
 * @since $Date: 2005/10/27 06:31:01 $ Tag: $Name: $
 */
public class GlobalAccessControlCommand extends AbstractCommand implements ICommonEventConstant {

	private static final String DELIMITER = "|";

	private static final String EQUAL = "=";

	private static final String CATEGORY_CODE_DDAP = "DDAP";

	private static final String DDAP_LIMIT = "LIMIT";

	private static final String DDAP_CO_BORROWER_LIMIT = "CO_BORROWER_LIMIT";

	private static final String DDAP_LIMIT_PROFILE = "LIMIT_PROFILE";

	private static final String DDAP_TAT_ONLY = "TAT_ONLY";

	private static final String LIMIT_ID_STR = "limitID";

	private static final String ACTION_TAT = "Tat";

	private static final String DDAP_BCA_ONLY = "BCA_ONLY";

	/**
	 * Default Constructor
	 */
	public GlobalAccessControlCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ SPECIAL_ACTION_KEY, "java.lang.String", SPECIAL_REQUEST_SCOPE },
				{ SPECIAL_EVENT_KEY, "java.lang.String", SPECIAL_REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.CHANGE_PASSWORD_IND, "java.lang.String", GLOBAL_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			updateLastAccessTime ((ICommonUser)map.get(IGlobalConstant.USER));
			
			String action = (String) map.get(SPECIAL_ACTION_KEY);
			String query = (String) map.get(SPECIAL_EVENT_KEY);
			String roleType = (String) map.get(IGlobalConstant.TEAM_TYPE_MEMBERSHIP_ID);
			String changePasswordInd = (String) map.get(IGlobalConstant.CHANGE_PASSWORD_IND);
			if ((changePasswordInd != null) && changePasswordInd.equals(IGlobalConstant.CHANGE_PASSWORD)) {
				AccessDeniedException ex = new AccessDeniedException(IGlobalConstant.CHANGE_PASSWORD);
				ex.setErrorCode(IGlobalConstant.CHANGE_PASSWORD);
				throw ex;
			}

			String ev = getParam(query, ICommonEventConstant.EVENT);

			DefaultLogger.debug(this, "Request Action: " + action);
			DefaultLogger.debug(this, "Request Event: " + ev);

			String codeKey = action + DELIMITER + ev;
			DefaultLogger.debug(this, "Code Key: " + codeKey);

			// first test if URL requires DDAP (dynamic DAP);
			//String codeValue = CommonDataSingleton.getCodeCategoryLabelByValue(CATEGORY_CODE_DDAP, codeKey);
		//	DefaultLogger.debug(this, "Code Value: " + codeValue);
			//if (null != codeValue) {
				// exist, and therefore requires DDAP filter
			//	filterByDDAP(map, action, codeValue, query); // throws
																// AccessDeniedException
																// on error
			//}

			// FAP.
			filterByFunctionAccess(action, getParam(query, ICommonEventConstant.EVENT), roleType);

			DefaultLogger.debug(this, "Going out of doExecute()");
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (AccessDeniedException e) {
			DefaultLogger.error(this, "Caught AccessDeniedException.", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in GlobalAccessControlCommand!", e);
			throw (new CommandProcessingException(e.toString()));
		}
	}

	/**
	 * Update user last acces time
	 * @param user The user value
	 */
	private void updateLastAccessTime(ICommonUser user) throws CommandProcessingException {
		IAccessProfileProxy proxy = AccessProfileProxyFactory.getAccessProfileProxy();

		if (proxy == null) {
			DefaultLogger.error(this, "access profile proxy is null");
			throw new CommandProcessingException("Failed to update user last access time");
		}
		try {
			proxy.setUserLastAccessTime(user.getLoginID());
		} catch (AccessProfileException e) {
			throw new CommandProcessingException (e.getMessage());
		}
	}
	
	/**
	 * Determines function access.
	 * @param action The struts action path value.
	 * @param event The event value.
	 * @throws AccessProfileException when cannot determine access.
	 * @throws AccessDeniedException when cannot grant access.
	 */
	private void filterByFunctionAccess(String action, String event, String roleType) throws AccessProfileException,
			AccessDeniedException {
		IAccessProfileProxy proxy = AccessProfileProxyFactory.getAccessProfileProxy();

		if (proxy == null) {
			DefaultLogger.error(this, "access profile proxy is null");
			throw new AccessProfileException("access profile proxy is null");
		}

		if (event == null) {
//			For DB2
//			event = "";
//			For Oracle 
			event = " ";
		}

		boolean isAllowed = proxy.isFunctionAccessAllowed(action, event, Long.parseLong(roleType));

		if (!isAllowed) {
			AccessDeniedException e = new AccessDeniedException("No function access for (action, event, roletype) = "
					+ action + " , " + event + " , " + roleType);
			e.setErrorCode(ICMSErrorCodes.FAP_NO_ACCESS);
			throw e;
		}
		else {
			DefaultLogger.debug(this, "function access allowed.");
		}
	}

	/**
	 * Helper method to filter by dynamic DAP (DDAP)
	 */
	private void filterByDDAP(HashMap map, String action, String code, String query) throws AccessDeniedException {
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		if (null == customer) {
			AccessDeniedException e = new AccessDeniedException(
					"ICMSCustomer in Global Scope is null! Unable to proceed.");
			e.setErrorCode(ICMSErrorCodes.DDAP_SETUP_ERROR);
			throw e;
		}

		// TO-CLARIFY: getNonBorrowerInd returns true for non-borrower
		if (customer.getNonBorrowerInd()) {
			return;
		}

		/*
		 * String custType = customer.getCMSLegalEntity().getCustomerType(); if
		 * (ICMSConstant.CUST_TYPE_NON_BORROWER_CORP.equals(custType) ||
		 * ICMSConstant.CUST_TYPE_NON_BORROWER_PRIV.equals(custType)) { return;
		 * }
		 */
		String segment = customer.getCMSLegalEntity().getCustomerSegment();
		DefaultLogger.debug(this, "Customer Segment: " + segment);

		ILimitProfile profile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		if (null == profile) {
			AccessDeniedException e = new AccessDeniedException(
					"ILimitProfile in Global Scope is null! Unable to proceed.");
			e.setErrorCode(ICMSErrorCodes.DDAP_SETUP_ERROR);
			throw e;
		}
		else {
			// check if TAT date has been created, else not allowed in here
			// refresh the profile first before proceeding
			try {
				ILimitProxy proxy = LimitProxyFactory.getProxy();
				profile = proxy.getLimitProfile(profile.getLimitProfileID());
			}
			catch (LimitException e) {
				DefaultLogger.error(this, "Caught LimitException!", e);
				AccessDeniedException ae = new AccessDeniedException("Caught LimitException.");
				e.setErrorCode(ICMSErrorCodes.DDAP_SETUP_ERROR);
				throw ae;
			}
			segment = profile.getSegment();
			
			if (PropertiesConstantHelper.isFilterByApplicationType()) {
				segment = profile.getApplicationType();
			}
			
			/*
			 * Not applicable for Alliance bank phase 1 if
			 * (!DDAP_BCA_ONLY.equals(code)) { if (profile.getTATCreateDate() ==
			 * null) { if (!(action.equals(ACTION_TAT))) { //This is applicable
			 * only this is not a TAT use-case AccessDeniedException e = new
			 * AccessDeniedException( "Create TAT has not been performed yet!");
			 * e.setErrorCode(ICMSErrorCodes.DDAP_TAT_NOT_CREATED); throw e; } }
			 * }
			 */
		}

		ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
		if (null == team) {
			AccessDeniedException e = new AccessDeniedException("ITeam in Global Scope is null! Unable to proceed.");
			e.setErrorCode(ICMSErrorCodes.DDAP_SETUP_ERROR);
			throw e;
		}

		if (code.equals(DDAP_TAT_ONLY)) {
			// do nothing}
		}
		else if (code.equals(DDAP_LIMIT)) { // means to compare against limit
											// booking location
			String limitID = getParam(query, LIMIT_ID_STR);
			DefaultLogger.debug(this, "LimitID: " + limitID);
			limitID = (limitID == null) ? getParam(query, "limitId") : limitID;
			if (null == limitID) {
				AccessDeniedException e = new AccessDeniedException("Limit ID is null!");
				e.setErrorCode(ICMSErrorCodes.DDAP_LIMIT_NOT_FOUND);
				throw e;
			}

			// ILimit[] limitList = profile.getNonDeletedLimits();
			ILimit[] limitList = profile.getLimits();
			if (null == limitList) {
				AccessDeniedException e = new AccessDeniedException("LimitList is null!");
				e.setErrorCode(ICMSErrorCodes.DDAP_LIMIT_NOT_FOUND);
				throw e;
			}
			else {
				boolean found = false;
				for (int i = 0; i < limitList.length; i++) {
					ILimit limit = limitList[i];
					if (String.valueOf(limit.getLimitID()).equals(limitID)) {
						// found limit. now compare country and organisation
						
						IBookingLocation loc = limit.getBookingLocation();
						if (!(isAllowedAccess(team, loc, segment))) {
							AccessDeniedException e = new AccessDeniedException(
									"User is not allowed to access this url!");
							e.setErrorCode(ICMSErrorCodes.DDAP_NO_ACCESS);
							throw e;
						}
						else {
							found = true;
							break;
						}
					}
				}
				if (false == found) {
					// limit not found
					AccessDeniedException e = new AccessDeniedException("Limit is not found in limit profile!");
					e.setErrorCode(ICMSErrorCodes.DDAP_LIMIT_NOT_FOUND);
					throw e;
				}
				else {
					// do nothing, since this is a valid access
				}
			}
		}
		else if (code.equals(DDAP_CO_BORROWER_LIMIT)) { // means to compare
														// against limit booking
														// location
			String limitID = getParam(query, LIMIT_ID_STR);
			DefaultLogger.debug(this, "LimitID: " + limitID);
			if (null == limitID) {
				AccessDeniedException e = new AccessDeniedException("Limit ID is null!");
				e.setErrorCode(ICMSErrorCodes.DDAP_LIMIT_NOT_FOUND);
				throw e;
			}
			// ILimit[] limitList = profile.getNonDeletedLimits();
			ILimit[] limitList = profile.getLimits();
			if (null == limitList) {
				AccessDeniedException e = new AccessDeniedException("LimitList is null!");
				e.setErrorCode(ICMSErrorCodes.DDAP_LIMIT_NOT_FOUND);
				throw e;
			}
			else {
				boolean found = false;
				for (int i = 0; i < limitList.length; i++) {
					ILimit limit = limitList[i];
					ICoBorrowerLimit[] coLimitList = limit.getCoBorrowerLimits();
					if (null != coLimitList) {
						for (int j = 0; j < coLimitList.length; j++) {
							ICoBorrowerLimit coLimit = coLimitList[j];

							if (String.valueOf(coLimit.getLimitID()).equals(limitID)) {
								// found limit. now compare country and
								// organisation
								// TO DO: change location check to use outer
								// limit instead of co-borrower limit location.
								// need confirmation. (1 sept 2003)
								// lyng: confirmed on 22th July 2004 that we
								// need to use co-borrower limit location
								IBookingLocation loc = coLimit.getBookingLocation();
								// IBookingLocation loc =
								// limit.getBookingLocation();
								if (!(isAllowedAccess(team, loc, segment))) {
									AccessDeniedException e = new AccessDeniedException(
											"User is not allowed to access this url!");
									e.setErrorCode(ICMSErrorCodes.DDAP_NO_ACCESS);
									throw e;
								}
								else {
									found = true;
									break;
								}
							}
						}
					}
				}
				if (false == found) {
					// limit not found
					AccessDeniedException e = new AccessDeniedException(
							"Co Borrower Limit is not found in limit profile!");
					e.setErrorCode(ICMSErrorCodes.DDAP_LIMIT_NOT_FOUND);
					throw e;
				}
				else {
					// do nothing, since this is a valid access
				}
			}
		}
		else if (code.equals(DDAP_LIMIT_PROFILE) || code.equals(DDAP_BCA_ONLY)) { // compare
																					// against
																					// limit
																					// profile
																					// booking
																					// location
			IBookingLocation loc = profile.getOriginatingLocation();

			if (!(isAllowedAccess(team, loc, segment))) {
				AccessDeniedException e = new AccessDeniedException("User is not allowed to access this url!");
				e.setErrorCode(ICMSErrorCodes.DDAP_NO_ACCESS);
				throw e;
			}
		}
		else {
			AccessDeniedException e = new AccessDeniedException("Unknown Code: " + code);
			e.setErrorCode(ICMSErrorCodes.DDAP_SETUP_ERROR);
			throw e;
		}
		// no error so return
		return;
	}

	/**
	 * Helper method to get the event from query
	 */
	private String getParam(String query, String indexKey) {
		DefaultLogger.debug(this, "Query before process: [" + query + "] key to lookup: [" + indexKey + "]");

		if ((query == null) || (indexKey == null)) {
			return null;
		}

		String[] queryValues = StringUtils.split(query, '&');
		for (int i = 0; i < queryValues.length; i++) {
			String[] queryValue = StringUtils.split(queryValues[i], '=');

			String value = (queryValue.length == 1) ? null : queryValue[1];
			if (indexKey.equals(queryValue[0])) {
				return value;
			}
		}

		return null;
	}

	/**
	 * Helper method to compare IBookingLocation against ITeam
	 */
	private boolean isAllowedAccess(ITeam team, IBookingLocation loc, String segment) {
		String country = loc.getCountryCode();
		String org = loc.getOrganisationCode();

		String[] countryList = team.getCountryCodes();
		String[] orgList = team.getOrganisationCodes();
		String[] segmentList = team.getSegmentCodes();

		boolean foundCountry = false;
		boolean foundOrg = false;
		boolean foundSegment = false;

		// test country
		if (countryList != null) {
			for (int i = 0; i < countryList.length; i++) {
				if (countryList[i].equals(country)) {
					foundCountry = true;
				}
			}
		}
		if (!foundCountry) {
			return false;
		}
		// then org
		
		//Commented by Shiv 05062011
//		if (orgList != null) {
//			for (int j = 0; j < orgList.length; j++) {
//				if (orgList[j].equals(org)) {
//					foundOrg = true;
//				}
//			}		
//		}
//		if (!foundOrg) {
//			return false;
//		}
//		
//		if (BizStructureHelper.requireBizSegment()) {
//			// lastly test segment						
//			if (segmentList != null) {
//				for (int k = 0; k < segmentList.length; k++) {
//					if (segmentList[k].equals(segment)) {
//						foundSegment = true;
//					}
//				}
//			}
//			if (!foundSegment) {
//				return false;
//			}
//		}

		// all is found, so return true
		return true;
	}
}