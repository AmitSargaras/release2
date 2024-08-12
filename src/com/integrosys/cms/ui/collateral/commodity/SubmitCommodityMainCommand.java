/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/SubmitCommodityMainCommand.java,v 1.10 2006/09/15 12:41:34 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyUtil;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/09/15 12:41:34 $ Tag: $Name: $
 */

public class SubmitCommodityMainCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "commodityMainObj", "java.util.HashMap", FORM_SCOPE },
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
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
		return (new String[][] { { "request.ITrxValue", "com.integrosys.base.businfra.transaction.ITrxValue",
				REQUEST_SCOPE }, });
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

		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
		HashMap objMap = (HashMap) map.get("commodityMainObj");
		ICommodityCollateral[] colList = (ICommodityCollateral[]) objMap.get("obj");

		if (limitProfileOB != null) {
			ctx.setTrxCountryOrigin(limitProfileOB.getOriginatingLocation().getCountryCode());
		}
		ICollateralTrxValue[] trxValueList = (ICollateralTrxValue[]) trxValueMap.get("trxValue");

		ICollateral[] staging = (ICollateral[]) objMap.get("obj");
		ITrxContext[] ctxList = new ITrxContext[trxValueList.length];
		if (trxValueList != null) {
			for (int i = 0; i < trxValueList.length; i++) {
				if ((staging[i].getLimitCharges() != null) && (staging[i].getLimitCharges().length > 0)) {
					ILimitCharge[] limitCharge = staging[i].getLimitCharges();
					limitCharge[0].setLimitMaps(staging[i].getCurrentCollateralLimits());
					staging[i].setLimitCharges(limitCharge);
				}
				trxValueList[i].setStagingCollateral(staging[i]);
				ctxList[i] = ctx;

				// DefaultLogger.debug(this, i+"\ttrxValue: "+trxValueList[i]);
			}
		}

		try {
			trxValueList = CollateralProxyFactory.getProxy().makerUpdateCollaterals(ctxList, trxValueList, staging);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}
		ICollateralTrxValue trxValue = CollateralProxyUtil.getParentTrxValue(trxValueList);
		result.put("request.ITrxValue", trxValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
