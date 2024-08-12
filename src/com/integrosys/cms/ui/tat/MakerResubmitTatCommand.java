/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/tat/MakerResubmitTatCommand.java,v 1.2 2003/09/02 10:24:58 pooja Exp $
 */

package com.integrosys.cms.ui.tat;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;

/**
 * This class is used to list the customer details based on some search
 * contsraints
 * @author $Author: pooja $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/02 10:24:58 $ Tag: $Name: $
 */
public class MakerResubmitTatCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public MakerResubmitTatCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "trxID", "java.lang.String", REQUEST_SCOPE }, });
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

		{ "customerOb", "com.integrosys.cms.app.customer.bus.OBCMSCustomer", FORM_SCOPE },
				{ "limitprofileOb", "com.integrosys.cms.app.limit.bus.OBLimitProfile", FORM_SCOPE },
				{ "trxValue", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE }, });
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

		try {
			ICMSCustomer custob = new OBCMSCustomer();
			ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
			String trxID = (String) map.get("trxID");

			ILimitProxy limitproxy = LimitProxyFactory.getProxy();
			ILimitProfileTrxValue limitprofiletrxvalue = new OBLimitProfileTrxValue();
			limitprofiletrxvalue = limitproxy.getTrxLimitProfile(trxID);
			long subpid = limitprofiletrxvalue.getLimitProfile().getCustomerID();
			custob = custproxy.getCustomer(subpid);
			result.put("customerOb", custob);
			result.put("trxValue", limitprofiletrxvalue);
			result.put("limitprofileOb", limitprofiletrxvalue.getStagingLimitProfile());

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;

	}

}
