/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.bizstructure;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.component.bizstructure.app.bus.TeamException;
import com.integrosys.component.bizstructure.app.trx.ITeamTrxValue;

/**
 * Command class to add the new bizstructure by admin maker on the corresponding
 * event...
 * @author $Author: dli $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/16 12:33:34 $ Tag: $Name: $
 */
public class CheckerReadTeamCmd extends AbstractTeamCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public CheckerReadTeamCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TrxId", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "TeamTrxValue", "com.integrosys.cms.app.bizstructure.trx.OBCMSTeamTrxValue", SERVICE_SCOPE },
				{ "mapTeam", "com.integrosys.component.bizstructure.app.bus.OBTeam", SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String trxId = (String) map.get("TrxId");
		DefaultLogger.debug(this, "Inside doExecute()  = " + trxId);
		try {
			ITeamTrxValue teamTrxVal = getCmsTeamProxy().getTrxTeamByTrx(trxId);
			resultMap.put("TeamTrxValue", teamTrxVal);
			resultMap.put("mapTeam", teamTrxVal.getStagingTeam());
		}
		catch (TeamException e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"failed to retrieve workflow values using trx id [" + trxId + "]");
			cpe.initCause(e);
			throw cpe;
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
