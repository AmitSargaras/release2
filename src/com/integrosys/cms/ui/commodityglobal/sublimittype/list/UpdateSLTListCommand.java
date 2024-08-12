/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/list/UpdateSLTListCommand.java,v 1.2 2005/11/15 03:06:17 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype.list;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.exception.CommandProcessingException;
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
 * @since 2005-9-23
 * @Tag 
 *      com.integrosys.cms.ui.commodityglobal.sublimittype.list.UpdateSLTListCommand
 *      .java
 */
public class UpdateSLTListCommand extends SubLimitTypeCommand {
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
			if ((trxValue.getSubLimitTypes() == null) || (trxValue.getSubLimitTypes().length == 0)) {
				exceptionMap.put(SLTUIConstants.ERR_EMPTY_SLT, new ActionMessage(SLTUIConstants.ERR_EMPTY_SLT_INFO));
			}
			trxValue = proxy.operateSubLimitType(trxContext, trxValue, ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN);
			resultMap.put(SLTUIConstants.AN_REQ_TRX_VALUE, trxValue);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}
	}
}
