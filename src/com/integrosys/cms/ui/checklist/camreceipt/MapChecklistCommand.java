package com.integrosys.cms.ui.checklist.camreceipt;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.ICheckList;

import java.util.HashMap;

/**
 * @author $Author: Cynthia $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 10/10/2008 $ Tag: $Name: $
 */
public class MapChecklistCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public MapChecklistCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
            return (new String[][] {
                    { "checkListForm", "com.integrosys.cms.app.checklist.bus.OBCheckList" ,FORM_SCOPE}
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
        return (new String[][] {
               { "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE }
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
        DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>*********** Inside MapChecklistCommand");
        ICheckList checkList = (ICheckList) map.get("checkListForm");
        DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>*********** Checklist.getLawFirmPanelFlag()" + checkList.getLawFirmPanelFlag());
        DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>*********** Checklist.getLegalFirm()" + checkList.getLegalFirm());
        DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>*********** Checklist.getLawFirmAddress()" + checkList.getLawFirmAddress());
        resultMap.put("checkList", checkList);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
