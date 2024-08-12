/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/list/DeleteSLTListCommand.java,v 1.1 2005/10/06 06:04:01 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
 * @since 2005-9-23
 * @Tag 
 *      com.integrosys.cms.ui.commodityglobal.sublimittype.list.DeleteSLTListCommand
 *      .java
 */
public class DeleteSLTListCommand extends SubLimitTypeCommand {
	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { SLTUIConstants.AN_OB_MAP, SLTUIConstants.CN_HASHMAP, FORM_SCOPE },
				{ SLTUIConstants.AN_SLT_TRX_VALUE, SLTUIConstants.CN_I_SLT_TRX_VALUE, SERVICE_SCOPE } };
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
		ISubLimitTypeTrxValue trxValue = (ISubLimitTypeTrxValue) paramMap.get(SLTUIConstants.AN_SLT_TRX_VALUE);
		ISubLimitType[] stagingArray = trxValue.getStagingSubLimitTypes();
		if ((stagingArray == null) || (stagingArray.length == 0)) {
			DefaultLogger.debug(this, "Num of total slt : " + 0);
			return;
		}
		DefaultLogger.debug(this, "Num of total slt : " + stagingArray.length);
		HashMap indexMap = (HashMap) paramMap.get(SLTUIConstants.AN_OB_MAP);
		if (indexMap == null) {
			indexMap = new HashMap();
		}
		List newStagingList = new ArrayList();
		for (int index = 0; index < stagingArray.length; index++) {
			if (indexMap.get(String.valueOf(index)) == null) {
				newStagingList.add(stagingArray[index]);
			}
			else {
				DefaultLogger.debug(this, "Delete one slt - index : " + index);
				continue;
			}
		}
		trxValue.setStagingSubLimitTypes((ISubLimitType[]) newStagingList.toArray(new ISubLimitType[0]));
		sortSLTTrxValue(trxValue);
		resultMap.put(SLTUIConstants.AN_SLT_TRX_VALUE, trxValue);
	}
}
