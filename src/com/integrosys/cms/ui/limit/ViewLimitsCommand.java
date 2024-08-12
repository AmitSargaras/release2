/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/ViewLimitsCommand.java,v 1.21 2006/09/27 06:09:07 hshii Exp $
 */
package com.integrosys.cms.ui.limit;

import java.util.HashMap;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * @author $Author: hshii $<br>
 * @version $Revision: 1.21 $
 * @since $Date: 2006/09/27 06:09:07 $ Tag: $Name: $
 */
public class ViewLimitsCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ViewLimitsCommand() {

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
				// {IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ,
				// "com.integrosys.cms.app.limit.bus.ILimitProfile",
				// GLOBAL_SCOPE},
				// {"limitTrxProfile",
				// "com.integrosys.cms.app.limit.trx.ILimitTrxValue"
				// ,SERVICE_SCOPE},
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, { "fromPage", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] { { "limitOb", "com.integrosys.cms.app.limit.bus.ILimit", FORM_SCOPE },
				{ "limitOb", "com.integrosys.cms.app.limit.bus.ILimit", REQUEST_SCOPE },
				{ "actualLimit", "com.integrosys.cms.app.limit.bus.ILimit", REQUEST_SCOPE },
				{ "stageLimit", "com.integrosys.cms.app.limit.bus.ILimit", REQUEST_SCOPE },
				{ "fromPage", "java.lang.String", REQUEST_SCOPE },
				{ "service.limitTrxValue", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE } });
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
			String fromPage = (String) map.get("fromPage");
			String limitID = (String) map.get("limitID");

			ILimitProxy limitProxy = LimitProxyFactory.getProxy();

			String event = (String) map.get("event");
			ILimit limit = null;
			ILimitTrxValue limitTrxValue = null;

			if (LimitsAction.EVENT_PREPARE_UPDATE_LIMIT.equals(event) || LimitsAction.VIEW_LIMIT_DETAILS.equals(event)) {
				limitTrxValue = limitProxy.getTrxLimit(Long.parseLong(limitID));
			}
			else {
				limitTrxValue = limitProxy.getTrxLimit((String) map.get("trxID"));
			}

			if (LimitsAction.EVENT_PREPARE_UPDATE_LIMIT.equals(event)) {
				if (limitTrxValue.getStatus().equals(ICMSConstant.STATE_PENDING_UPDATE)
						|| limitTrxValue.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
					result.put("wip", "wip");
				}
				else {
					limitTrxValue.setStagingLimit((ILimit) AccessorUtil.deepClone(limitTrxValue.getLimit()));
					limit = limitTrxValue.getStagingLimit();
				}
			}
			else if (LimitsAction.VIEW_LIMIT_DETAILS.equals(event)) {
				limit = limitTrxValue.getLimit();
			}
			else {
				limit = limitTrxValue.getStagingLimit();
			}

			result.put("limitOb", limit);
			result.put("service.limitTrxValue", limitTrxValue);
			result.put("fromPage", fromPage);
		}
		catch (Exception e) {
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	// helper method to retrive ILimit from ILimitProfile by limit id
	/*
	 * private ILimit getLimitByLimitID(ILimitProfile limitProfile, String
	 * strLmtID) { if (limitProfile != null && strLmtID != null) { ILimit[]
	 * limitList = limitProfile.getLimits(); if (limitList != null) { for (int i
	 * = 0; i < limitList.length; i++) { if
	 * (String.valueOf(limitList[i].getLimitID()).equals(strLmtID)) return
	 * limitList[i]; } } } return null; }
	 */
}
