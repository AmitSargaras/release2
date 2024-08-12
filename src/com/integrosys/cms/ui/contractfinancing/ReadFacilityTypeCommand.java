/**
 * Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.contractfinancing.bus.IContractFacilityType;
import com.integrosys.cms.app.contractfinancing.bus.OBContractFacilityType;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class ReadFacilityTypeCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadFacilityTypeCommand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "contractFinancingTrxValue",
						"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE },
				{ "facilityTypeIndex", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "commonRef", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "obFacilityTypeForm", "com.integrosys.cms.app.contractfinancing.bus.OBContractFacilityType",
						FORM_SCOPE }, // need for maker edit page, checker view
				{ "obFacilityType", "com.integrosys.cms.app.contractfinancing.bus.OBContractFacilityType",
						REQUEST_SCOPE }, // need for checker and maker view page
				{ "obActualFacilityType", "com.integrosys.cms.app.contractfinancing.bus.OBContractFacilityType",
						REQUEST_SCOPE }, // need for checker view page
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
		String event = (String) map.get("event");

		String facilityTypeIndex = (String) map.get("facilityTypeIndex");
		String commonRef = (String) map.get("commonRef");

		try {
			IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) map.get("contractFinancingTrxValue");

			IContractFacilityType[] facilityTypeList = trxValue.getStagingContractFinancing().getFacilityTypeList();
			IContractFacilityType[] facilityTypeActualList = null;
			if (trxValue.getContractFinancing() != null) { // actual will be
															// null if this is
															// new record
				facilityTypeActualList = trxValue.getContractFinancing().getFacilityTypeList();
			}
			OBContractFacilityType obFacilityType = null;
			OBContractFacilityType obActualFacilityType = null;

			if (FacilityTypeAction.EVENT_VIEW.equals(event)
					|| FacilityTypeAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)) {
				obFacilityType = (OBContractFacilityType) facilityTypeList[Integer.parseInt(facilityTypeIndex)];
				resultMap.put("obFacilityTypeForm", obFacilityType);
			}
			else { // for checker view
				for (int i = 0; i < facilityTypeList.length; i++) {
					if (facilityTypeList[i].getCommonRef() == Long.parseLong(commonRef)) {
						obFacilityType = (OBContractFacilityType) facilityTypeList[i];
					}
				}
				if (facilityTypeActualList != null) {
					for (int i = 0; i < facilityTypeActualList.length; i++) {
						if (facilityTypeActualList[i].getCommonRef() == Long.parseLong(commonRef)) {
							obActualFacilityType = (OBContractFacilityType) facilityTypeActualList[i];
						}
					}
				}
				if (obFacilityType == null) { // when delete staging
					resultMap.put("obFacilityTypeForm", obActualFacilityType);
				}
				else {
					resultMap.put("obFacilityTypeForm", obFacilityType);
				}
				resultMap.put("obActualFacilityType", obActualFacilityType);
			}
			resultMap.put("obFacilityType", obFacilityType); // staging data
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}

}