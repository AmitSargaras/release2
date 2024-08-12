/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/ViewCoBorrowerCommand.java,v 1.14 2006/09/27 06:09:07 hshii Exp $
 */

package com.integrosys.cms.ui.limit;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;

import org.apache.commons.lang.Validate;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * @author $Author: hshii $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/09/27 06:09:07 $ Tag: $Name: $
 */
public class ViewCoBorrowerCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ViewCoBorrowerCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "limitID", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "coBorrowerOb", "com.integrosys.cms.app.limit.bus.OBCoBorrowerLimit", FORM_SCOPE },
				{ "coBorrowerLimitTrxValue", "com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "custOb", "com.integrosys.cms.app.customer.bus.OBCMSCustomer", REQUEST_SCOPE },
				{ "limitprofileOb", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE } });
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
		HashMap returnMap = new HashMap();

		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();

			String coBorrowerLimitId = (String) map.get("limitID");
			String event = (String) map.get("event");
			String trxID = (String) map.get("trxID");

			ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ICMSCustomer customerOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);

			Validate.notNull(limitProfileOB, "limitProfileOB object must not be null");
			Validate.notNull(customerOB, "customerOB object must not be null");

			ICoBorrowerLimitTrxValue coBorrowerLimitTrxValue = null;
			if (coBorrowerLimitId != null) {
				coBorrowerLimitTrxValue = limitProxy.getTrxCoBorrowerLimit(Long.parseLong(coBorrowerLimitId));
			}
			else {
				coBorrowerLimitTrxValue = limitProxy.getTrxCoBorrowerLimit(trxID);
			}

			ICoBorrowerLimit coBorrowerLimit = coBorrowerLimitTrxValue.getLimit();

			if (LimitsAction.PREPARE_CLOSE_CO_BORROWER_LIMITS.equals(event)
					|| LimitsAction.TO_TRACK_CO_BORROWER.equals(event)) {
				coBorrowerLimit = coBorrowerLimitTrxValue.getStagingLimit();
			}

			GenerateLimitHelper.setCustomerInfoIntoCoBorrowerLimit(coBorrowerLimit);
			GenerateLimitHelper.setProductDescriptionFromMainBorrowerLimit(coBorrowerLimit);
			coBorrowerLimit.setMainBorrowerCust(customerOB);

			result.put("coBorrowerOb", coBorrowerLimit);
			result.put("limitprofileOb", limitProfileOB);
			result.put("custOb", customerOB);
			result.put("coBorrowerLimitTrxValue", coBorrowerLimitTrxValue);
			result.put("event", event);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}