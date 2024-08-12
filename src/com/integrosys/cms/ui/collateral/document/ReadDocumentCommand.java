/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/ReadDocumentCommand.java,v 1.4 2006/10/09 05:23:29 jzhai Exp $
 */

package com.integrosys.cms.ui.collateral.document;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;

/**

 * @author $Author: jzhai $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/10/09 05:23:29 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 12:13:00 PM
 * To change this template use Options | File Templates.
 */
public class ReadDocumentCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
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
		return (new String[][] { { "limitMap", "java.util.Collection", SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
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

		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		ICollateral col = null;
		String event = (String) map.get("event");
		if (DocumentAction.EVENT_READ.equals(event)) {
			col = itrxValue.getCollateral();
		}
		else {
			col = itrxValue.getStagingCollateral();
		}

		HashMap limitMap = new HashMap();
		List limitList = SecuritySubTypeUtil.getDistinctLimitList(col);

		try {
			if (("MB".equals(col.getCollateralLimits()[0].getCustomerCategory()) && (col.getCollateralLimits()[0]
					.getCoBorrowerLimitID() <= 0))) {
				limitMap = LimitProxyFactory.getProxy().getApprovedLimitAmount(limitList);
			}
			else {
				limitMap = LimitProxyFactory.getProxy().getApprovedLimitAmount4CoBorrower(limitList);
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception is thrown from LimitProxyFactory: " + e.getMessage());
		}

		result.put("limitMap", limitMap);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
