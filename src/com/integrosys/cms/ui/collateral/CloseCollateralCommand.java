/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/CloseCollateralCommand.java,v 1.6.10.1 2006/12/14 12:19:04 jychong Exp $
 */

package com.integrosys.cms.ui.collateral;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.6.10.1 $
 * @since $Date: 2006/12/14 12:19:04 $ Tag: $Name: DEV_20060126_B286V1 $
 */

public class CloseCollateralCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",
				REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				});
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
   
		ICollateralTrxValue returnValue = new OBCollateralTrxValue();
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");

		try {
			// CollateralUiUtil.setTrxLocation(ctx, itrxValue.getCollateral());
			// close to get transaction context from existing trx
			ITrxContext oldCtx = itrxValue.getTrxContext();
			if (oldCtx != null) {
				ctx.setCustomer(oldCtx.getCustomer());
				ctx.setLimitProfile(oldCtx.getLimitProfile());
			}
			result.put("serviceColObj", itrxValue);
			
			//start-------------------------------24Sept2013-------------------------------------
			ICollateral actualCol = (ICollateral)itrxValue.getCollateral();
			actualCol.setStatus("ACTIVE");
			itrxValue.setCollateral(actualCol);
			//end---------------------------------------------------------------------------------
			returnValue = CollateralProxyFactory.getProxy().makerCancelCollateral(ctx, itrxValue);
			
			result.put("request.ITrxValue", returnValue);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "failed to close collateral workflow", e);
			CommandProcessingException cpe = new CommandProcessingException("failed to close collateral workflow");
			cpe.initCause(e);
			throw cpe;
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
