/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/item/UpdateSLTItemCommand.java,v 1.1 2005/10/06 06:03:37 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype.item;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType;
import com.integrosys.cms.app.commodity.main.trx.sublimittype.ISubLimitTypeTrxValue;
import com.integrosys.cms.ui.commodityglobal.sublimittype.SLTUIConstants;
import com.integrosys.cms.ui.commodityglobal.sublimittype.SubLimitTypeCommand;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-19
 * @Tag 
 *      com.integrosys.cms.ui.commodityglobal.sublimittype.item.UpdateSLTItemCommand
 *      .java
 */
public class UpdateSLTItemCommand extends SubLimitTypeCommand {

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { SLTUIConstants.FN_IDX_ID, SLTUIConstants.CN_STRING, REQUEST_SCOPE },
				{ SLTUIConstants.AN_OB_SLT, SLTUIConstants.CN_I_SLT, FORM_SCOPE },
				{ SLTUIConstants.AN_SLT_TRX_VALUE, SLTUIConstants.CN_I_SLT_TRX_VALUE, SERVICE_SCOPE } });
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
		DefaultLogger.debug(this, "execute - Begin.");
		int index = Integer.parseInt((String) paramMap.get(SLTUIConstants.FN_IDX_ID));
		ISubLimitTypeTrxValue trxValue = (ISubLimitTypeTrxValue) paramMap.get(SLTUIConstants.AN_SLT_TRX_VALUE);
		ISubLimitType obSLT = (ISubLimitType) paramMap.get(SLTUIConstants.AN_OB_SLT);
		ISubLimitType[] existingArray = trxValue.getStagingSubLimitTypes();

		if (isDuplicate(obSLT, existingArray, index)) {
			DefaultLogger.debug(this, "Duplicate Sub Limit Type");
			exceptionMap
					.put(SLTUIConstants.ERR_DUPLICATE_SLT, new ActionMessage(SLTUIConstants.ERR_DUPLICATE_SLT_INFO));
			return;
		}
		existingArray[index] = obSLT;
		sortSLTArray(existingArray);
		trxValue.setStagingSubLimitTypes(existingArray);
		resultMap.put(SLTUIConstants.AN_SLT_TRX_VALUE, trxValue);
		DefaultLogger.debug(this, "execute - End.");
	}

	private boolean isDuplicate(ISubLimitType obSLT, ISubLimitType[] existingArray, int index) {
		if (existingArray == null) {
			return false;
		}
		int arrayLength = existingArray.length;
		DefaultLogger.debug(this, "Length of exist : " + arrayLength);
		for (int arrayIndex = 0; arrayIndex < arrayLength; arrayIndex++) {
			String sltStr = existingArray[arrayIndex].getSubLimitType();
			String ltStr = existingArray[arrayIndex].getLimitType();
			if ((index != arrayIndex) && sltStr.equals(obSLT.getSubLimitType()) && ltStr.equals(obSLT.getLimitType())) {
				DefaultLogger.debug(this, "Found Duplicate Sub Limit Type");
				return true;
			}
		}
		return false;
	}
}
