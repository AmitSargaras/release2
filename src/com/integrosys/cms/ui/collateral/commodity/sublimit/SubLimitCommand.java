/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/SubLimitCommand.java,v 1.2 2005/10/14 06:30:43 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.sublimit;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-26
 * @Tag com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitCommand.java
 */
public abstract class SubLimitCommand extends AbstractCommand {
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
}
