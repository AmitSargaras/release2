/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/CloseCollateralCommand.java,v 1.6.10.1 2006/12/14 12:19:04 jychong Exp $
 */

package com.integrosys.cms.ui.collateral;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.6.10.1 $
 * @since $Date: 2006/12/14 12:19:04 $ Tag: $Name: DEV_20060126_B286V1 $
 */
public class DeleteCollateralCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "flag1", "java.lang.String", SERVICE_SCOPE },
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
				REQUEST_SCOPE } });
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
		String flag = (String) map.get("flag1");

		if ((itrxValue.getStatus().equals(ICMSConstant.STATE_REJECTED)
				|| itrxValue.getStatus().equals(ICMSConstant.STATE_PENDING_UPDATE)
				|| itrxValue.getStatus().equals(ICMSConstant.STATE_DRAFT) || itrxValue.getStatus().equals(
				ICMSConstant.STATE_PENDING_DELETE))
				&& (flag != null)) {
			result.put("wip", "wip");
		}
		else {
			try {
				// CollateralUiUtil.setTrxLocation(ctx,
				// itrxValue.getCollateral());

				itrxValue.getStagingCollateral().setCollateralStatus(ICMSConstant.HOST_COL_STATUS_DELETED);
				returnValue = CollateralProxyFactory.getProxy().makerDeleteCollateral(ctx, itrxValue);
				result.put("request.ITrxValue", returnValue);
			}
			catch (CollateralException e) {
				throw new CommandProcessingException("failed to delete collateral workflow", e);
			}
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
