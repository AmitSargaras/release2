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
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;

/**
 * @author $Author: bxu $<br>
 * @version $Revision: 1.18 $
 * @since $Date: 2005/04/27 08:28:04 $ Tag: $Name: $
 */
public class GlobalPrepareSessionCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public GlobalPrepareSessionCommand() {
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
				{ IGlobalConstant.REQUEST_CUSTOMER_ID, "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.REQUEST_LIMITPROFILE_ID, "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ SPECIAL_ACTION_KEY, "java.lang.String", SPECIAL_REQUEST_SCOPE },
				{ SPECIAL_EVENT_KEY, "java.lang.String", SPECIAL_REQUEST_SCOPE },				
				{ "transactionID_backtoTask", "java.lang.String", GLOBAL_SCOPE },
				{ "transactionID", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				//{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY
				// ,"java.util.Locale", GLOBAL_SCOPE}, //TODO remove after local
				// is setup in login module
				{ "transactionID_backtoTask", "java.lang.String", GLOBAL_SCOPE } });
	}

	
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			String customerStr = (String) map.get(IGlobalConstant.REQUEST_CUSTOMER_ID);
			String profileStr = (String) map.get(IGlobalConstant.REQUEST_LIMITPROFILE_ID);
			// resultMap.put(com.integrosys.base.uiinfra.common.Constants.
			// GLOBAL_LOCALE_KEY,Locale.getDefault());//TODO remove after local
			// is setup in login module

			DefaultLogger.debug(this, "Request customerID: " + customerStr);
			DefaultLogger.debug(this, "Request LimitProfileID: " + profileStr);
			String transactionID = (String) map.get("transactionID");
			if (transactionID == null) {
				transactionID = (String) map.get("transactionID_backtoTask");
			}
			resultMap.put("transactionID_backtoTask", transactionID);

			String action = (String) map.get(SPECIAL_ACTION_KEY);
			String query = (String) map.get(SPECIAL_EVENT_KEY);
			String event = getParam(query, ICommonEventConstant.EVENT);
			
			// check whether require to reset global scope customer and AA 
			// based on action and event
			if (PropertiesConstantHelper.isResetGlobalCustomerAARequire(action, event)) {
				removeSessionCustomer(resultMap);
				removeSessionLimitProfile(resultMap);
			} else {
				// PROCESS CUSTOMER
				if ((null != customerStr) && (customerStr.length() > 0)) {
					long customerID = Long.parseLong(customerStr);
					// fetch and put in session
					if("poiReport".equalsIgnoreCase(action)) {
						customerID=0;
                    }
					
					if (customerID > 0) {
						resultMap = setSessionCustomer(customerID, resultMap);
					} else {
						removeSessionCustomer(resultMap);
					}
				}
				else {
					DefaultLogger.debug(this, "CustomerID not in Request.");
				}
	
				// PROCESS LIMIT PROFILE
				if (null != profileStr) {
					if (profileStr.length() > 0) {
						long profileID = Long.parseLong(profileStr);
						
						if("poiReport".equalsIgnoreCase(action)) {
                            profileID=0;
                        }
						// fetch and put in session
						if("poiReport".equalsIgnoreCase(action)) {
							profileID=0;
						}
						if (profileID > 0) {
							resultMap = setSessionLimitProfile(profileID, resultMap);
						}
						else {
							removeSessionLimitProfile(resultMap);
						}
					}
				}
				else {
					DefaultLogger.debug(this, "LimitProfileID not in Request.");
				}
			}
			DefaultLogger.debug(this, "Going out of doExecute()");
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in GlobalPrepareSessionCommand!", e);
			throw (new CommandProcessingException(e.toString()));
		}
	}

	private HashMap setSessionCustomer(long customerID, HashMap map) throws Exception {
		ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
		ICMSCustomer cust = custProxy.getCustomer(customerID);
		map.put(IGlobalConstant.GLOBAL_CUSTOMER_OBJ, cust);
		DefaultLogger.debug(this, "Putting Customer in Session with CustomerID: " + customerID);

		return map;
	}

	private HashMap setSessionLimitProfile(long profileID, HashMap map) throws Exception {
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		ILimitProfile profile = limitProxy.getLimitProfile(profileID);
		map.put(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, profile);
		DefaultLogger.debug(this, "Putting LimitProfile in Session with LimitProfileID: " + profileID);

		return map;
	}

	private HashMap removeSessionLimitProfile(HashMap map) throws Exception {
		map.put(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, null);
		return map;
	}

	private HashMap removeSessionCustomer(HashMap map) throws Exception {
		map.put(IGlobalConstant.GLOBAL_CUSTOMER_OBJ, null);
		return map;
	}

	private boolean limitProfileBelongsToCustomer(HashMap map) throws Exception {
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		// DefaultLogger.debug(this, "CUSTOMER: " + customer);
		// DefaultLogger.debug(this, "LimitProfile: " + limitProfile);
		if ((customer != null) && (limitProfile != null)) {
			DefaultLogger.debug(this, "CUstomerID1: " + limitProfile.getCustomerID());
			DefaultLogger.debug(this, "CUstomerID2: " + customer.getCustomerID());
			if (limitProfile.getCustomerID() != customer.getCustomerID()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Helper method to get the event from query
	 */
	private String getParam(String query, String indexKey) {
		DefaultLogger.debug(this, "Query before process: [" + query + "] key to lookup: [" + indexKey + "]");

		if ((query == null) || (indexKey == null)) {
			return null;
		}
		query= query.replace("&=&", "&");
		
		String[] queryValues = StringUtils.split(query, '&');
		for (int i = 0; i < queryValues.length-1; i++) {
			String[] queryValue = StringUtils.split(queryValues[i], '=');
			if(queryValue!=null &&  queryValue.length>0) {
				String value = (queryValue.length == 1) ? null : queryValue[1];
				if (indexKey.equals(queryValue[0])) {
					return value;
				}
			}
			
		}

		return null;
	}	
}