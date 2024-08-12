/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/list/ReadSLTListCommand.java,v 1.3 2005/11/15 03:15:48 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.commodity.main.trx.sublimittype.ISubLimitTypeTrxValue;
import com.integrosys.cms.app.commodity.main.trx.sublimittype.OBSubLimitTypeTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.commodityglobal.sublimittype.SLTUIConstants;
import com.integrosys.cms.ui.commodityglobal.sublimittype.SubLimitTypeCommand;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-15
 * @Tag com.integrosys.cms.ui.commodityglobal.sublimit.list.
 *      ReadSubLimitTypeListCommand.java
 */
public class ReadSLTListCommand extends SubLimitTypeCommand {

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { SLTUIConstants.AN_OB_TRX_CONTEXT, SLTUIConstants.CN_OB_TRX_CONTEXT, FORM_SCOPE } };
	}

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getResultDescriptor()
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { SLTUIConstants.AN_SLT_TRX_VALUE, SLTUIConstants.CN_I_SLT_TRX_VALUE, SERVICE_SCOPE } };
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
		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		try {
			ISubLimitTypeTrxValue trxValue = new OBSubLimitTypeTrxValue();
			String action = ICMSConstant.ACTION_READ_COMMODITY_MAIN_SLT_GROUP;
			trxValue = proxy.operateSubLimitType(trxContext, trxValue, action);

			if (isWorkInProgress(trxValue.getStatus())) {
				DefaultLogger.debug(this, "Work in progress.");
				resultMap.put(SLTUIConstants.SN_WORK_IN_PROGRESS, SLTUIConstants.SN_WORK_IN_PROGRESS);
			}
			else if (trxValue.getSubLimitTypes() != null) {
				DefaultLogger.debug(this, "Clone Actual -> Staging.");
				// clone actual -> staging
				ISubLimitType[] staging = (ISubLimitType[]) AccessorUtil.deepClone(trxValue.getSubLimitTypes());
				trxValue.setStagingSubLimitTypes(staging);
			}
			sortSLTTrxValue(trxValue);
			resultMap.put(SLTUIConstants.AN_SLT_TRX_VALUE, trxValue);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "error at proxy getSubLimitTypeTrxValue");
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}
	}

	protected boolean isWorkInProgress(String status) {
		DefaultLogger.debug(this, "Status : " + status);
		if (ICMSConstant.STATE_ND.equals(status)) {
			return false;
		}
		if (ICMSConstant.STATE_ACTIVE.equals(status)) {
			return false;
		}
		return true;
	}
}
