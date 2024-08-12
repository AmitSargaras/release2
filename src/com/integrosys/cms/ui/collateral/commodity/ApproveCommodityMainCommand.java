/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/ApproveCommodityMainCommand.java,v 1.7 2005/07/15 06:19:30 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyUtil;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/07/15 06:19:30 $ Tag: $Name: $
 */

public class ApproveCommodityMainCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "preConditions", "java.lang.String", REQUEST_SCOPE }, });
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
		HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
		ICollateralTrxValue[] trxValueList = (ICollateralTrxValue[]) trxValueMap.get("trxValue");
		ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

		String preCondition = (String) map.get("preConditions");
		boolean isPreConditionChanged = CommodityMainUtil.isPreConditionChanged((ICommodityCollateral) trxValueList[0]
				.getStagingCollateral(), limitProfileOB.getLimitProfileID(), preCondition);
		if (isPreConditionChanged) {
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			// update precondition and respectively information
			for (int i = 0; i < trxValueList.length; i++) {
				ICommodityCollateral col = (ICommodityCollateral) trxValueList[i].getStagingCollateral();
				col = CommodityMainUtil.updatePreCondition(col, preCondition, user, limitProfileOB.getLimitProfileID());
				trxValueList[i].setStagingCollateral(col);
			}
		}

		if (limitProfileOB != null) {
			ctx.setTrxCountryOrigin(limitProfileOB.getOriginatingLocation().getCountryCode());
		}

		ITrxContext[] ctxArr = new ITrxContext[trxValueList.length];
		for (int i = 0; i < ctxArr.length; i++) {
			ctxArr[i] = ctx;
		}
		try {
			trxValueList = CollateralProxyFactory.getProxy().checkerVerifyUpdateCollaterals(ctxArr, trxValueList);
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
