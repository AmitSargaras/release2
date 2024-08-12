/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/ViewCommodityMainCommand.java,v 1.4 2005/07/18 08:11:21 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/07/18 08:11:21 $ Tag: $Name: $
 */
public class ViewCommodityMainCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "preConditions", "java.lang.String", REQUEST_SCOPE },
				{ "from_page", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE }, });
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

		String from_event = (String) map.get("from_page");

		boolean isPreConditionChanged = false;
		String preCondition = (String) map.get("preConditions");
		if ((from_event != null)
				&& (from_event.equals(CommodityMainAction.EVENT_PROCESS) || from_event
						.equals(CommodityMainAction.EVENT_PREPARE_CLOSE))) {
			isPreConditionChanged = CommodityMainUtil.isPreConditionChanged((ICommodityCollateral) trxValueList[0]
					.getStagingCollateral(), limitProfileOB.getLimitProfileID(), preCondition);
		}

		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		for (int i = 0; i < trxValueList.length; i++) {
			trxValueList[i].setRemarks(ctx.getRemarks());
			if (isPreConditionChanged) {
				// update precondition and respectively information
				ICommodityCollateral col = (ICommodityCollateral) trxValueList[i].getStagingCollateral();
				col = CommodityMainUtil.updatePreCondition(col, preCondition, user, limitProfileOB.getLimitProfileID());
				trxValueList[i].setStagingCollateral(col);
			}
		}

		trxValueMap.put("trxValue", trxValueList);
		result.put("commodityMainTrxValue", trxValueMap);
		DefaultLogger.debug(this, "after putting trxValueMap into session");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
