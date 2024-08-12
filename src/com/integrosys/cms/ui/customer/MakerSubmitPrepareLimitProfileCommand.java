/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/MakerSubmitPrepareLimitProfileCommand.java,v 1.7 2004/07/30 07:31:14 pooja Exp $
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
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.ui.common.ApprovingOfficerGradeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * @author $Author: pooja $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/07/30 07:31:14 $ Tag: $Name: $
 */
public class MakerSubmitPrepareLimitProfileCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public MakerSubmitPrepareLimitProfileCommand() {

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
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "approvingOfficerGradeListID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "approvingOfficerGradeListValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "sscStatus", "java.lang.String", REQUEST_SCOPE } });
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
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			DefaultLogger.debug(this, "Before Doing Search" + map.keySet());
			ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
			String trxID = (String) map.get("TrxId");
			String event = (String) map.get("event");
			String sscStatus = "";
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitProfileTrxValue trxLimitProfile = limitProxy.getTrxLimitProfile(trxID);

			ICMSCustomer custOB = null;
			String customerTrxID = trxLimitProfile.getTrxReferenceID();
			if (null != customerTrxID) {
				ICMSCustomerTrxValue customerTrxValue = custproxy.getTrxCustomer(customerTrxID);
				custOB = customerTrxValue.getCustomer();
			}
			else {
				DefaultLogger.debug(this,
						"Getting customer from global scope since customer trx is not in LimitProfileTrx.");
				custOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
				if (null == custOB) {
					throw new CommandProcessingException("Customer not in global scope!");
				}
			}
			DefaultLogger.debug(this, "after settibng ob");
			int scc_status_ind = limitProxy.getSCCStatus(trxLimitProfile.getLimitProfile());
			if (ICMSConstant.SCC_NOT_APPLICABLE == scc_status_ind) {
				sscStatus = "N.A.";
			}
			result.put("sscStatus", sscStatus);
			result.put("customerOb", custOB);
			result.put("trxID", trxID);
			result.put("event", event);
			ApprovingOfficerGradeList approvingOfficerGradeList = ApprovingOfficerGradeList.getInstance();
			result.put("approvingOfficerGradeListID", approvingOfficerGradeList.getApprovingOfficerGradeListID());
			result.put("approvingOfficerGradeListValue", approvingOfficerGradeList.getApprovingOfficerGradeListValue());
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}

}
