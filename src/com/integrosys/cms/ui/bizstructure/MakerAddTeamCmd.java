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
import com.integrosys.cms.app.bizstructure.trx.OBCMSTeamTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.component.bizstructure.app.bus.OBTeam;
import com.integrosys.component.bizstructure.app.bus.TeamException;
import com.integrosys.component.bizstructure.app.trx.ITeamTrxValue;
import com.integrosys.component.common.transaction.ICompTrxResult;

/**
 * Command class to add the new bizstructure by admin maker on the corresponding
 * event...
 * @author $Author: dli $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/16 12:33:34 $ Tag: $Name: $
 */
public class MakerAddTeamCmd extends AbstractTeamCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	public MakerAddTeamCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "Team", "com.integrosys.component.bizstructure.app.bus.OBTeam", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "TeamTrxValue", "com.integrosys.cms.app.bizstructure.trx.OBCMSTeamTrxValue", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "mapTeam", "com.integrosys.component.bizstructure.app.bus.OBTeam", SERVICE_SCOPE },
				{ "request.ITrxResult", "com.integrosys.component.common.transaction.ICompTrxResult", REQUEST_SCOPE }

		});
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
		OBTeam team = (OBTeam) map.get("Team");
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		String event = (String) map.get("event");

		ITeamTrxValue trxVal = new OBCMSTeamTrxValue();
		if (event.equals("maker_edit_reject_add")) {
			trxVal = (ITeamTrxValue) map.get("TeamTrxValue");
		}

		try {
			ICompTrxResult trxResult = getCmsTeamProxy().makerCreateTeam(trxContext, trxVal, team);
			resultMap.put("request.ITrxResult", trxResult);
		}
		catch (TeamException e) {
			CommandProcessingException cpe = new CommandProcessingException("failed to create team [" + team + "]");
			cpe.initCause(e);
			throw cpe;
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		resultMap.put("mapTeam", null);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
