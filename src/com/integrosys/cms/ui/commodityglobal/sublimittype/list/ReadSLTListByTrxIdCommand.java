/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/list/ReadSLTListByTrxIdCommand.java,v 1.2 2005/11/15 03:15:48 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
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
 * @since 2005-9-22
 * @Tag com.integrosys.cms.ui.commodityglobal.sublimittype.list.
 *      ReadSLTListByTrxIdCommand.java
 */
public class ReadSLTListByTrxIdCommand extends SubLimitTypeCommand {

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { SLTUIConstants.AN_TRX_ID, SLTUIConstants.CN_STRING, REQUEST_SCOPE },
				{ SLTUIConstants.AN_OB_TRX_CONTEXT, SLTUIConstants.CN_OB_TRX_CONTEXT, FORM_SCOPE } };
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
		String trxId = (String) paramMap.get(SLTUIConstants.AN_TRX_ID);
		DefaultLogger.debug(this, "TrxId : " + trxId);
		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		try {
			ISubLimitTypeTrxValue trxValue = new OBSubLimitTypeTrxValue();
			trxValue.setTransactionID(trxId);
			String action = ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID;
			trxValue = proxy.operateSubLimitType(trxContext, trxValue, action);

			sortSLTTrxValue(trxValue);
			resultMap.put(SLTUIConstants.AN_SLT_TRX_VALUE, trxValue);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "error at proxy get Commodity Profile by trxID");
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}
	}
}
