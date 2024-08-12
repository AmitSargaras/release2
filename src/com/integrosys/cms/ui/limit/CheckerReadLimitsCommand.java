/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/CheckerReadLimitsCommand.java,v 1.6 2005/10/14 04:03:16 whuang Exp $
 */
package com.integrosys.cms.ui.limit;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/10/14 04:03:16 $ Tag: $Name: $
 */

public class CheckerReadLimitsCommand extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "trxID", "java.lang.String", REQUEST_SCOPE } });
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
				{ "service.limitTrxValue", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "limitOb", "com.integrosys.cms.app.limit.bus.ILimit", FORM_SCOPE } });
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
		try {
			ILimitTrxValue trxValue = limitProxy.getTrxLimit((String) map.get("trxID"));
			result.put("service.limitTrxValue", trxValue);
			result.put("limitOb", trxValue.getStagingLimit());
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
			throw new CommandProcessingException();
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
	}

	/**
	 * get changed value
	 * @param trxValue
	 */
	/*
	 * private void compareChanges(ILimitTrxValue trxValue) { if
	 * (trxValue!=null) { ILimitTrxValue[] trxValues =
	 * trxValue.getLimitTrxValues(); if (trxValues!=null && trxValues.length!=0)
	 * { for (int i=0; i<trxValues.length; i++) { ILimit actualLimit =
	 * trxValues[i].getLimit(); ILimit stagingLimit =
	 * trxValues[i].getStagingLimit(); Amount actualActivatedLimitAmount =
	 * actualLimit.getActivatedLimitAmount(); float actualSecurityCoverages =
	 * actualLimit.getRequiredSecurityCoverage(); Amount
	 * stagingActivatedLimitAmount = stagingLimit.getActivatedLimitAmount();
	 * float stagingSecurityCoverages =
	 * stagingLimit.getRequiredSecurityCoverage(); if
	 * ((CompareUtil.isChanged(actualActivatedLimitAmount
	 * ,stagingActivatedLimitAmount))
	 * ||(CompareUtil.isChanged(actualSecurityCoverages
	 * ,stagingSecurityCoverages))) stagingLimit.setIsChanged(true); if
	 * (CompareUtil
	 * .isChanged(actualLimit.getIsLimitZerorised(),stagingLimit.getIsLimitZerorised
	 * ())) stagingLimit.setIsZerorisedChanged(true); if
	 * (CompareUtil.isChanged(actualLimit
	 * .getZerorisedDate(),stagingLimit.getZerorisedDate()))
	 * stagingLimit.setIsZerorisedDateChanged(true); if
	 * (CompareUtil.isChanged(actualLimit
	 * .getZerorisedReason(),stagingLimit.getZerorisedReason()))
	 * stagingLimit.setIsZerorisedReasonChanged(true); } } } }
	 */
}
