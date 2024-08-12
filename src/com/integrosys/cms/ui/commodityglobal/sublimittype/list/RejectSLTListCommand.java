/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/list/RejectSLTListCommand.java,v 1.2 2005/11/15 03:15:48 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.commodity.main.trx.sublimittype.ISubLimitTypeTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.commodityglobal.sublimittype.SLTUIConstants;
import com.integrosys.cms.ui.commodityglobal.sublimittype.SubLimitTypeCommand;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-22
 * @Tag 
 *      com.integrosys.cms.ui.commodityglobal.sublimittype.list.RejectSLTListCommand
 *      .java
 */
public class RejectSLTListCommand extends SubLimitTypeCommand {
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
		OBTrxContext trxContext = (OBTrxContext) paramMap.get(SLTUIConstants.AN_OB_TRX_CONTEXT);
		trxContext.setCustomer(null);
		trxContext.setLimitProfile(null);
		ISubLimitTypeTrxValue trxValue = (ISubLimitTypeTrxValue) paramMap.get(SLTUIConstants.AN_SLT_TRX_VALUE);
		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		try {
			String action = getActionByStatus(trxValue.getStatus());
			DefaultLogger.debug(this, "Action : " + action);
			trxValue = proxy.operateSubLimitType(trxContext, trxValue, action);
			resultMap.put(SLTUIConstants.AN_REQ_TRX_VALUE, trxValue);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}
	}

	private String getActionByStatus(String status) throws CommodityException {
		DefaultLogger.debug(this, "Status : " + status);
		if (ICMSConstant.STATE_PENDING_CREATE.equals(status)) {
			return ICMSConstant.ACTION_CHECKER_CREATE_REJECT_COMMODITY_MAIN;
		}
		if (ICMSConstant.STATE_PENDING_UPDATE.equals(status)) {
			return ICMSConstant.ACTION_CHECKER_UPDATE_REJECT_COMMODITY_MAIN;
		}
		if (ICMSConstant.STATE_PENDING_DELETE.equals(status)) {
			return ICMSConstant.ACTION_CHECKER_DELETE_REJECT_COMMODITY_MAIN;
		}
		throw new CommodityException("Unknown action to proxy with status : " + (status == null ? "null" : status));
	}
}
