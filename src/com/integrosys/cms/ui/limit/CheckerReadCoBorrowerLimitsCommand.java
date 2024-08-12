/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/CheckerReadCoBorrowerLimitsCommand.java,v 1.10 2006/09/27 06:09:07 hshii Exp $
 */

package com.integrosys.cms.ui.limit;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/09/27 06:09:07 $ Tag: $Name: $
 */

public class CheckerReadCoBorrowerLimitsCommand extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, });
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
				{ "coBorrowerLimitTrxValue", "com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue", SERVICE_SCOPE },
				{ "coBorrowerOb", "com.integrosys.cms.app.limit.bus.OBCoBorrowerLimit", FORM_SCOPE }, });
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
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		String trxID = (String) map.get("trxID");

		try {
			ILimitProfile aLimitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

			ICoBorrowerLimitTrxValue coBorrowerLimitTrxValue = limitProxy.getTrxCoBorrowerLimit(trxID);
			GenerateLimitHelper.setCustomerInCoBorrowerLimit(coBorrowerLimitTrxValue);
			GenerateLimitHelper.setMainCustomerInfoByLimitProfile(coBorrowerLimitTrxValue, aLimitProfile);
			GenerateLimitHelper.setProductDescriptionFromMainBorrowerLimit(coBorrowerLimitTrxValue.getStagingLimit());

			result.put("coBorrowerLimitTrxValue", coBorrowerLimitTrxValue);
			result.put("coBorrowerOb", coBorrowerLimitTrxValue.getStagingLimit());
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
			throw new CommandProcessingException();
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
	}

	/**
	 * compare changes
	 * @param trxValue
	 */
	private void compareChanges(ICoBorrowerLimitTrxValue trxValue) {
		if (trxValue != null) {
			ICoBorrowerLimitTrxValue[] trxValues = trxValue.getCoBorrowerLimitTrxValues();
			if (trxValues != null) {
				for (int i = 0; i < trxValues.length; i++) {
					ICoBorrowerLimit actualLimit = trxValues[i].getLimit();
					ICoBorrowerLimit stagingLimit = trxValues[i].getStagingLimit();
					if (CompareUtil.isChanged(actualLimit.getActivatedLimitAmount(), stagingLimit
							.getActivatedLimitAmount())) {
						stagingLimit.setIsChanged(true);
					}
					if (CompareUtil.isChanged(actualLimit.getLimitZerorised(), stagingLimit.getLimitZerorised())) {
						stagingLimit.setIsZerorisedChanged(true);
					}
					if (CompareUtil.isChanged(actualLimit.getZerorisedDate(), stagingLimit.getZerorisedDate())) {
						stagingLimit.setIsZerorisedDateChanged(true);
					}
					if (CompareUtil.isChanged(actualLimit.getZerorisedReasons(), stagingLimit.getZerorisedReasons())) {
						stagingLimit.setIsZerorisedReasonChanged(true);
					}
				}
			}
		}
	}
}
