/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/SubLimitTypeCommand.java,v 1.3 2005/11/12 04:49:57 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.SubLimitTypeComparator;
import com.integrosys.cms.app.commodity.main.trx.sublimittype.ISubLimitTypeTrxValue;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-22
 * @Tag 
 *      com.integrosys.cms.ui.commodityglobal.sublimittype.SubLimitTypeCommand.java
 */
public abstract class SubLimitTypeCommand extends AbstractCommand {
	/*
	 * @see
	 * com.integrosys.base.uiinfra.common.ICommand#doExecute(java.util.HashMap)
	 */
	public HashMap doExecute(HashMap hashmap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		DefaultLogger.debug(this, " - doExecute() - Begin.");
		HashMap exceptionMap = new HashMap();
		HashMap resultMap = new HashMap();
		execute(hashmap, resultMap, exceptionMap);
		HashMap returnMap = new HashMap();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		DefaultLogger.debug(this, " - doExecute() - End.");
		return returnMap;
	}

	/**
	 * @param paramMap : A java.util.HashMap object store parameters.
	 * @param resultMap : A java.util.HashMap object store results.
	 * @param exceptionMap : A java.util.HashMap object store exceptions
	 * @throws CommandProcessingException
	 */
	protected abstract void execute(HashMap paramMap, HashMap resultMap, HashMap exceptionMap)
			throws CommandProcessingException;

	protected void sortSLTTrxValue(ISubLimitTypeTrxValue trxValue) {
		if (trxValue == null) {
			DefaultLogger.debug(this, "the trxValue is null");
		}
		HashMap limitTypeMap = CommonDataSingleton
				.getCodeCategoryValueLabelMap(SLTUIConstants.CONSTANT_COMMODITY_CATEGORY_CODE);
		sortSLTArray(trxValue.getSubLimitTypes(), limitTypeMap);
		sortSLTArray(trxValue.getStagingSubLimitTypes(), limitTypeMap);
	}

	protected void sortSLTArray(ISubLimitType[] sltArray) {
		HashMap limitTypeMap = CommonDataSingleton
				.getCodeCategoryValueLabelMap(SLTUIConstants.CONSTANT_COMMODITY_CATEGORY_CODE);
		sortSLTArray(sltArray, limitTypeMap);
	}

	protected void sortSLTArray(ISubLimitType[] sltArray, HashMap limitTypeMap) {
		if (sltArray != null) {
			SubLimitTypeComparator c = new SubLimitTypeComparator(limitTypeMap);
			Arrays.sort(sltArray, c);
		}
	}
}
