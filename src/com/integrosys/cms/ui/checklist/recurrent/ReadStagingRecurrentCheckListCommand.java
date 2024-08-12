/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrent;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.recurrent.bus.ConvenantComparator;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.proxy.IRecurrentProxyManager;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/09/21 12:30:04 $ Tag: $Name: $
 */
public class ReadStagingRecurrentCheckListCommand extends AbstractCommand {
	private IRecurrentProxyManager recurrentProxyManager;

	public void setRecurrentProxyManager(IRecurrentProxyManager recurrentProxyManager) {
		this.recurrentProxyManager = recurrentProxyManager;
	}

	public IRecurrentProxyManager getRecurrentProxyManager() {
		return recurrentProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public ReadStagingRecurrentCheckListCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "custTypeTrxID", "java.lang.String", REQUEST_SCOPE },
				{ EVENT, "java.lang.String", REQUEST_SCOPE } });
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
				{ "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList", SERVICE_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue", SERVICE_SCOPE },
				{ "flag", "java.lang.String", REQUEST_SCOPE }, { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "limitProfile", "com.integrosys.cms.app.limit.bus.ILimitProfile", FORM_SCOPE },
				{ "custTypeTrxID", "java.lang.String", REQUEST_SCOPE }, });
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
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			String custTypeTrxID = (String) map.get("custTypeTrxID");
			DefaultLogger.debug(this, "custTypeTrxID: " + custTypeTrxID);
			String event = (String) map.get(EVENT);
			DefaultLogger.debug(this, "TrxiD before backend call" + custTypeTrxID);

			IRecurrentCheckListTrxValue checkListTrxVal = getRecurrentProxyManager().getRecurrentCheckListByTrxID(
					custTypeTrxID);
			IRecurrentCheckList recChkLst = checkListTrxVal.getStagingCheckList();
			IConvenant conList[] = recChkLst.getConvenantList();

			if ((conList != null) && (conList.length > 0)) {
				Arrays.sort(conList, new ConvenantComparator());
			}
			recChkLst.setConvenantList(conList);
			checkListTrxVal.setStagingCheckList(recChkLst);

			resultMap.put("recChkLst", recChkLst);
			resultMap.put("checkListTrxVal", checkListTrxVal);
			resultMap.put("limitProfile", limit);
			resultMap.put("custTypeTrxID", custTypeTrxID);
			if ("close_checklist_item".equals(event)) {
				resultMap.put("flag", "Close");
			}
			resultMap.put("frame", "false");// used to hide frames when user
			// comes from to do list
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
