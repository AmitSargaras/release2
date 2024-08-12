/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/secreceipt/RefreshSecurityReceiptCommand.java,v 1.1 2004/10/27 02:17:45 hshii Exp $
 */
package com.integrosys.cms.ui.checklist.secreceipt;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/10/27 02:17:45 $ Tag: $Name: $
 */
public class RefreshSecurityReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE }, });
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
		/*
		 * {"ownerObj",
		 * "com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner",
		 * FORM_SCOPE},
		 * {"legalFirmLabels","java.util.Collection",REQUEST_SCOPE},
		 * {"legalFirmValues","java.util.Collection",REQUEST_SCOPE},
		 */
		{ "forwardCollection", "java.util.Collection", REQUEST_SCOPE } //+OFFICE
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
			ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
			// ICheckList checkList = (ICheckList)map.get("checkList");

			// resultMap.put("ownerObj",checkList.getCheckListOwner());

			// CR-380 starts
			/*
			 * String countryCode="none"; if(checkList != null &&
			 * checkList.getCheckListLocation() != null &&
			 * checkList.getCheckListLocation().getCountryCode() != null ){
			 * countryCode=checkList.getCheckListLocation().getCountryCode(); }
			 * LegalFirmList legalFirmList =
			 * LegalFirmList.getInstance(countryCode);
			 * resultMap.put("legalFirmLabels"
			 * ,legalFirmList.getLegalFirmLabel());
			 * resultMap.put("legalFirmValues"
			 * ,legalFirmList.getLegalFirmProperty());
			 */
			// CR-380 ends
			resultMap.put("forwardCollection", checkListTrxVal.getNextRouteCollection()); // +
																							// OFFICE
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