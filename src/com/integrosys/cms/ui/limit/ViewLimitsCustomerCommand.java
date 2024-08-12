/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/ViewLimitsCustomerCommand.java,v 1.31 2006/11/10 06:16:14 hmbao Exp $
 */

package com.integrosys.cms.ui.limit;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.CoBorrowerLimitUtils;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.31 $
 * @since $Date: 2006/11/10 06:16:14 $ Tag: $Name: $
 */
public class ViewLimitsCustomerCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ViewLimitsCustomerCommand() {

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
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "transactionID", "java.lang.String", REQUEST_SCOPE },
				{ "S_TRX_ID", "java.lang.String", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE }, { "S_TRX_ID", "java.lang.String", SERVICE_SCOPE },
				{ "limitObList", "com.integrosys.cms.app.limit.bus.OBLimitProfile", SERVICE_SCOPE },
				{ "innerOuterBcaObList", "java.util.HashMap", SERVICE_SCOPE },
				/*
				 * {"coBorrowerLimitTrxValue","com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue"
				 * ,SERVICE_SCOPE}, {"coborrowerLimitMap", "java.util.HashMap",
				 * REQUEST_SCOPE},
				 */
				{ "coBorrowerLimitObList", "com.integrosys.cms.app.limit.busICoBorrowerLimit", SERVICE_SCOPE }, });
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
		HashMap temp = new HashMap();
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		ArrayList referenceList = GenerateLimitHelper.getLimitsReference(map, theOBTrxContext);
		ILimitProfile limitProfile = (ILimitProfile) referenceList.get(0);
		HashMap bcaList = (HashMap) referenceList.get(1);
		theOBTrxContext.setLimitProfile(limitProfile);
		/*
		 * ILimitProxy limitProxy = LimitProxyFactory.getProxy(); try {
		 * ICoBorrowerLimitTrxValue coBorrowerLimitTrxValue =
		 * limitProxy.getTrxCoBorrowerLimitByLimitProfile
		 * (theOBTrxContext,limitProfile);
		 * helper.setCustomerInCoBorrowerLimit(coBorrowerLimitTrxValue);
		 * result.put("coBorrowerLimitTrxValue",coBorrowerLimitTrxValue); }
		 * catch (Exception e) { DefaultLogger.error(this,e); throw new
		 * CommandProcessingException(); } result.put("coborrowerLimitMap",
		 * (HashMap)referenceList.get(2));
		 */
		ICoBorrowerLimit[] coBorrowerLimitList = CoBorrowerLimitUtils.getAllCoBorowerLimitsByLimitProfile(limitProfile);
		GenerateLimitHelper.setCustomerInfoIntoCoBorrowerLimits(coBorrowerLimitList);

		result.put("coBorrowerLimitObList", coBorrowerLimitList);
		result.put("limitProfileID", (String) map.get("limitProfileID"));
		result.put("customerID", (String) map.get("customerID"));
		String trxID = (String) map.get("transactionID");
		if ((trxID == null) || trxID.trim().equals("")) {
			trxID = (String) map.get("S_TRX_ID");
		}
		result.put("S_TRX_ID", trxID);

		result.put("innerOuterBcaObList", bcaList);
		result.put("limitObList", limitProfile);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
	}
}