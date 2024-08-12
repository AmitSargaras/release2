/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/PrepareEditCoBorrowerLimitsCommand.java,v 1.5 2005/10/29 03:54:53 whuang Exp $
 */
package com.integrosys.cms.ui.limit;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/10/29 03:54:53 $ Tag: $Name: $
 */

public class PrepareEditCoBorrowerLimitsCommand extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "coBorrowerLimitTrxValue", "com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue", SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "coBorrowerOb", "com.integrosys.cms.app.limit.bus.OBCoBorrowerLimit", FORM_SCOPE },
				{ "coBorrowerLimitTrxValue", "com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue", SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
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
		HashMap temp = new HashMap();

		try {
			ITeam currentTeam = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			ICoBorrowerLimitTrxValue coBorrowerLimitTrxValue = (ICoBorrowerLimitTrxValue) map
					.get("coBorrowerLimitTrxValue");

			String status = coBorrowerLimitTrxValue.getStatus();
			if (ICMSConstant.STATE_PENDING_UPDATE.equals(status) || ICMSConstant.STATE_REJECTED.equals(status)) {
				result.put("wip", "wip");
			}

			GenerateLimitHelper.setProductDescriptionFromMainBorrowerLimit(coBorrowerLimitTrxValue.getLimit());

			result.put("coBorrowerOb", coBorrowerLimitTrxValue.getLimit());
			result.put("coBorrowerLimitTrxValue", coBorrowerLimitTrxValue);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.toString());
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
	}
}