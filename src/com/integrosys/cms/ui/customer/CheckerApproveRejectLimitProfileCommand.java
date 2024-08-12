/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/CheckerApproveRejectLimitProfileCommand.java,v 1.6 2004/06/04 05:13:29 hltan Exp $
 */

package com.integrosys.cms.ui.customer;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * @author $Author: hltan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/04 05:13:29 $ Tag: $Name: $
 */
public class CheckerApproveRejectLimitProfileCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public CheckerApproveRejectLimitProfileCommand() {

	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE }, { "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "fam", "java.lang.String", REQUEST_SCOPE }, { "famcode", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "customerOb", "com.integrosys.cms.app.customer.bus.OBCMSCustomer", REQUEST_SCOPE },
				{ "limitprofileOb", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE },
				{ "trxValue", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "fam", "java.lang.String", SERVICE_SCOPE }, { "famcode", "java.lang.String", SERVICE_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		CustomerSearchCriteria objSearch = new CustomerSearchCriteria();
		objSearch = (CustomerSearchCriteria) map.get("customerSearchCriteria");
		String event = (String) map.get("event");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			DefaultLogger.debug(this, "Before Doing Search" + map.keySet());
			String trxID = (String) map.get("TrxId");
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitProfileTrxValue trxLimitProfile = limitProxy.getTrxLimitProfile(trxID);
			result.put("trxValue", trxLimitProfile);
			String fam = new String();
			String famcode = new String();
			ICMSCustomer globalcustOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			if (null == globalcustOB) {
				throw new CommandProcessingException("ICMSCustomer is null in session!");
			}
			ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			if (globalcustOB.getNonBorrowerInd()) {
				if (Long.toString(globalcustOB.getCustomerID()) != null) {
					fam = (String) limitProxy.getFAMNameByCustomer(globalcustOB.getCustomerID()).get(
							ICMSConstant.FAM_NAME);
				}
				famcode = (String) limitProxy.getFAMNameByCustomer(globalcustOB.getCustomerID()).get(
						ICMSConstant.FAM_CODE);
			}
			else {
				if (Long.toString(limitProfileOB.getLimitProfileID()) != null) {
					fam = (String) limitProxy.getFAMName(limitProfileOB.getLimitProfileID()).get(ICMSConstant.FAM_NAME);
					famcode = (String) limitProxy.getFAMName(limitProfileOB.getLimitProfileID()).get(
							ICMSConstant.FAM_CODE);
				}
			}
			result.put("fam", fam);
			result.put("famcode", famcode);
			if (trxLimitProfile.getStatus().equals(ICMSConstant.STATE_PENDING_UPDATE)) {
				result.put("limitprofileOb", trxLimitProfile.getStagingLimitProfile());

			}
			else {
				result.put("limitprofileOb", trxLimitProfile.getLimitProfile());
			}
			ICustomerProxy customerproxy = CustomerProxyFactory.getProxy();

			// try getting from trxLimitProfile.getTrxReferenceID() first, if
			// not exist then customerID in request
			String customerTrxID = trxLimitProfile.getTrxReferenceID();
			ICMSCustomer custOB = null;
			if (null == customerTrxID) {
				DefaultLogger.debug(this,
						"Customer Trx ID is not found in Limit Profile Trx. Using Customer ID from request instead.");
				String customerIDStr = (String) map.get("customerID");
				if (null == customerIDStr) {
					DefaultLogger.debug(this, "CustomerID is not in request either. Unable to get customer.");
					throw new CommandProcessingException("CustomerID is not in request either. Unable to get customer.");
				}
				else {
					custOB = customerproxy.getCustomer(Long.parseLong(customerIDStr));
					if (null == custOB) {
						throw new CommandProcessingException("Unable to get customer with customerID: " + customerIDStr);
					}
					else {
						DefaultLogger.debug(this, "Found Customer with customerID: " + customerIDStr);
					}
				}
			}
			else {
				ICMSCustomerTrxValue customerTrxValue = customerproxy.getTrxCustomer(customerTrxID);
				custOB = customerTrxValue.getCustomer();
			}

			if (null == custOB) {
				throw new CommandProcessingException("ICMSCustomer is null");
			}
			result.put("customerOb", custOB);

			DefaultLogger.debug(this, "Going out of doExecute()");
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return returnMap;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute", e);
			throw (new CommandProcessingException(e.getMessage()));
		}
	}
}