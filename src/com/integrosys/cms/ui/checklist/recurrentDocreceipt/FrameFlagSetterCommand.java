/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrentDocreceipt;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.ui.checklist.ITagUntagImageConstant;
import com.integrosys.cms.ui.common.LegalFirmList;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/06/27 09:14:02 $ Tag: $Name: $
 */
public class FrameFlagSetterCommand extends AbstractCommand implements ICommonEventConstant, ITagUntagImageConstant {
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
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "legalFirm", "java.lang.String", REQUEST_SCOPE },
				{ "prev_event", "java.lang.String", REQUEST_SCOPE }, // used bt
																		// R1.5,
																		// CR17:
																		// Share
																		// Checklist
				{ "event", "java.lang.String", REQUEST_SCOPE }, // used bt R1.5,
																// CR17: Share
																// Checklist
                { "isErrorEvent", "java.lang.String", REQUEST_SCOPE }, // used to determine error from lawyer mandatory check
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
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "legalFirmValues", "java.util.Collection", REQUEST_SCOPE },
				{ "checkListForm", "com.integrosys.cms.app.checklist.bus.OBCheckList" ,FORM_SCOPE},
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ IS_IMAGE_TAG_PENDING, Boolean.class.getName(), SERVICE_SCOPE }
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
			HashMap selectedArrayMap =  new HashMap();
			resultMap.put("selectedArrayMap", selectedArrayMap);

            LegalFirmList legalFirmList = LegalFirmList.getInstance(countryCode);
			resultMap.put("legalFirmLabels", legalFirmList.getLegalFirmLabel());
			resultMap.put("legalFirmValues", legalFirmList.getLegalFirmProperty());

			// Start modification on 13May04
			String hLegalFirm = (String) map.get("leaglFirm");
			if (checkList != null) {
				if (hLegalFirm != null) {
					checkList.setLegalFirm(hLegalFirm);
				}
				resultMap.put("checkList", checkList);
			}
			// End modification on 13May04
			// CR-380 ends

            
            // R1.5-CR17 starts
			String prevEvent = (String) map.get("prev_event");
			String currEvent = (String) map.get("event");
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>> PREV_EVENT: " + prevEvent);
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>> CURR_EVENT: " + currEvent);

			// use for redirection -- use by SecurityReceiptAction only, no need
			// to set in ResultDescriptor
			if (currEvent.equals(RecurrentDocReceiptAction.EVENT_REFRESH_SHARE_CHECKLIST)
					|| currEvent.equals(RecurrentDocReceiptAction.EVENT_REFRESH_SHARE_CHECKLIST + "_prepare")) {
				resultMap.put("prev_event", prevEvent);
			}
			// R1.5-CR17 Ends

            //to check if this is an error event with the lawyer mandatory check
            //if its an error event, then set checklist to null so that mapper will not remap the values for display.
            Boolean isErrorEvent = (Boolean) map.get("isErrorEvent");
            if(isErrorEvent!=null && isErrorEvent.booleanValue()) {
                DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>**************** isErrorEvent");
                resultMap.put("checkListForm", null);
            } else {
                resultMap.put("checkListForm", checkList);
                DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>**************** NOT ERROR EVENT");                
            }
            
            if("view_ok".equals(currEvent)){
            	resultMap.put(IS_IMAGE_TAG_PENDING, null);
            }
            
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
