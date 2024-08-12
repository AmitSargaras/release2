/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.ccreceipt;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.ui.common.LegalFirmList;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/06/21 10:53:43 $ Tag: $Name: $
 */
public class FrameFlagSetterCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public FrameFlagSetterCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "legalFirm", "java.lang.String", REQUEST_SCOPE },
				{ "makerStatus", "java.lang.String", REQUEST_SCOPE },
				{ "prev_event", "java.lang.String", REQUEST_SCOPE }, // used bt
																		// R1.5,
																		// CR17:
																		// Share
																		// Checklist
				{ "event", "java.lang.String", REQUEST_SCOPE }, // used bt R1.5,
																// CR17: Share
																// Checklist
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "legalFirmLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "legalFirmValues", "java.util.Collection", REQUEST_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }
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
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			resultMap.put("frame", map.get("frame"));

			// CR-380 starts
			String countryCode = "none";
			ICheckList checkList = (ICheckList) map.get("checkList");
			if ((checkList != null) && (checkList.getCheckListLocation() != null)
					&& (checkList.getCheckListLocation().getCountryCode() != null)) {
				countryCode = checkList.getCheckListLocation().getCountryCode();
			}
			LegalFirmList legalFirmList = LegalFirmList.getInstance(countryCode);
			resultMap.put("legalFirmLabels", legalFirmList.getLegalFirmLabel());
			resultMap.put("legalFirmValues", legalFirmList.getLegalFirmProperty());

			// Start modification on 13May04
			String hLegalFirm = (String) map.get("legalFirm");
			String makerStatus = (String) map.get("makerStatus"); // 1487
			if (checkList != null) {
				if (hLegalFirm != null) {
					checkList.setLegalFirm(hLegalFirm);
				} // 1487
				if (makerStatus != null) {
					checkList.setRemarks(makerStatus);
				} // 1487

				resultMap.put("checkList", checkList);
			}
			// End modification on 13May04

			// CR-380 ends

			// R1.5-CR17 starts
			String prevEvent = (String) map.get("prev_event");
			String currEvent = (String) map.get("event");
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>> PREV_EVENT: " + prevEvent);
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>> CURR_EVENT: " + currEvent);

			// use for redirection -- use by CCReceiptAction only, no need to
			// set in ResultDescriptor
			if (currEvent.equals(CCReceiptAction.EVENT_REFRESH_SHARE_CHECKLIST)
					|| currEvent.equals(CCReceiptAction.EVENT_REFRESH_SHARE_CHECKLIST + "_prepare")) {
				resultMap.put("prev_event", prevEvent);
			}
			// R1.5-CR17 Ends
			resultMap.put("event", currEvent);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
