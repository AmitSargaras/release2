/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/list/SubmitSLTCommand.java,v 1.3 2005/11/15 03:06:17 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype.list;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.commodity.main.trx.sublimittype.ISubLimitTypeTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.commodityglobal.sublimittype.SLTUIConstants;
import com.integrosys.cms.ui.commodityglobal.sublimittype.SubLimitTypeCommand;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-20
 * @Tag 
 *      com.integrosys.cms.ui.commodityglobal.sublimittype.list.SubmitSLTCommand.
 *      java
 */
public class SubmitSLTCommand extends SubLimitTypeCommand {

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { SLTUIConstants.AN_OB_TRX_CONTEXT, SLTUIConstants.CN_OB_TRX_CONTEXT, FORM_SCOPE },
				{ SLTUIConstants.AN_SLT_TRX_VALUE, SLTUIConstants.CN_I_SLT_TRX_VALUE, SERVICE_SCOPE } };
	}

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getResultDescriptor()
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { SLTUIConstants.AN_REQ_TRX_VALUE, SLTUIConstants.CN_I_SLT_TRX_VALUE, REQUEST_SCOPE } };
	}

	/*
	 * @see
	 * com.integrosys.cms.ui.commodityglobal.sublimittype.SubLimitTypeCommand
	 * #execute(java.util.HashMap, java.util.HashMap, java.util.HashMap)
	 */
	protected void execute(HashMap paramMap, HashMap resultMap, HashMap exceptionMap) throws CommandProcessingException {
		ITrxContext trxContext = (ITrxContext) paramMap.get(SLTUIConstants.AN_OB_TRX_CONTEXT);
		trxContext.setCustomer(null);
		trxContext.setLimitProfile(null);
		ISubLimitTypeTrxValue sltTrxValue = (ISubLimitTypeTrxValue) paramMap.get(SLTUIConstants.AN_SLT_TRX_VALUE);
		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		if ((sltTrxValue == null) || (sltTrxValue.getStagingSubLimitTypes() == null)
				|| (sltTrxValue.getStagingSubLimitTypes().length == 0)) {
			exceptionMap.put(SLTUIConstants.ERR_EMPTY_SLT, new ActionMessage(SLTUIConstants.ERR_EMPTY_SLT_INFO));
			return;
		}
		try {
			boolean isUpdate = false;
			if ((sltTrxValue.getSubLimitTypes() != null) && (sltTrxValue.getSubLimitTypes().length > 0)) {
				isUpdate = true;
			}
			String action = getActionByStatus(sltTrxValue.getStatus(), isUpdate);
			DefaultLogger.debug(this, "ActionType : " + action);
			sltTrxValue = proxy.operateSubLimitType(trxContext, sltTrxValue, action);
			resultMap.put(SLTUIConstants.AN_REQ_TRX_VALUE, sltTrxValue);
		}
		catch (CommodityException e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}
	}

	private String getActionByStatus(String status, boolean isUpdate) throws CommodityException {
		if (ICMSConstant.STATE_ND.equals(status)) {
			return ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN;
		}
		if (ICMSConstant.STATE_DRAFT.equals(status)) {
			return ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN;
		}
		if (ICMSConstant.STATE_ACTIVE.equals(status)) {
			return ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN;
		}
		if (ICMSConstant.STATE_REJECTED.equals(status)) {
			if (isUpdate) {
				return ICMSConstant.ACTION_MAKER_UPDATE_RESUBMIT_COMMODITY_MAIN;
			}
			else {
				return ICMSConstant.ACTION_MAKER_CREATE_RESUBMIT_COMMODITY_MAIN;
			}
		}
		throw new CommodityException("Unknown action to proxy with status : " + (status == null ? "null" : status));
	}
}
