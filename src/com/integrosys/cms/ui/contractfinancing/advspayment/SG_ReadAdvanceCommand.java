/**
 * Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing.advspayment;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.contractfinancing.bus.IAdvance;
import com.integrosys.cms.app.contractfinancing.bus.OBAdvance;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class SG_ReadAdvanceCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public SG_ReadAdvanceCommand() {
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
				{ "initAdvEvent", "java.lang.String", REQUEST_SCOPE },
				{ "moa", "java.lang.String", REQUEST_SCOPE },
				{ "contractFinancingTrxValue",
						"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE },
				{ "obAdvance", "com.integrosys.cms.app.contractfinancing.bus.OBAdvance", SERVICE_SCOPE },
				{ "obActualAdvance", "com.integrosys.cms.app.contractfinancing.bus.OBAdvance", SERVICE_SCOPE },
				{ "advanceIndex", "java.lang.String", SERVICE_SCOPE },
				{ "facilityType", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "commonRef", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "obAdvanceForm", "com.integrosys.cms.app.contractfinancing.bus.OBAdvance", FORM_SCOPE }, // need
																											// for
																											// maker
																											// edit
																											// page
				{ "obAdvance", "com.integrosys.cms.app.contractfinancing.bus.OBAdvance", REQUEST_SCOPE }, // need
																											// for
																											// checker
																											// and
																											// maker
																											// view
																											// page
				{ "obActualAdvance", "com.integrosys.cms.app.contractfinancing.bus.OBAdvance", REQUEST_SCOPE }, // need
																												// for
																												// checker
																												// view
																												// page
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
		String moa = (String) map.get("moa");

		String advanceIndex = (String) map.get("advanceIndex");

		String commonRef = (String) map.get("commonRef");

		try {
			IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) map.get("contractFinancingTrxValue");

			IAdvance[] advanceList = trxValue.getStagingContractFinancing().getAdvanceList();
			IAdvance[] advanceActualList = null;
			if (trxValue.getContractFinancing() != null) { // actual will be
															// null if this is
															// new record
				advanceActualList = trxValue.getContractFinancing().getAdvanceList();
			}
			OBAdvance obAdvance = (OBAdvance) map.get("obAdvance"); // get from
																	// service
																	// scope
			try {
				DefaultLogger.debug(this, "obAdvance.getReferenceNo()=" + obAdvance.getReferenceNo());
			}
			catch (Exception e) {
				DefaultLogger.debug(this, "obAdvance=" + obAdvance);
			}
			OBAdvance obActualAdvance = (OBAdvance) map.get("obActualAdvance"); // get
																				// from
																				// service
																				// scope

			if (obAdvance == null) {
				obAdvance = new OBAdvance();
			}
			if (obActualAdvance == null) {
				obActualAdvance = new OBAdvance();
			}
			if ((obAdvance != null) && (moa != null) && !moa.equals("")) {
				obAdvance.setFacilityTypeMOA(Float.parseFloat((String) map.get("moa")));
			}

			if (AdvsPaymentAction.EVENT_VIEW.equals(event)
					|| AdvsPaymentAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)
					|| AdvsPaymentAction.EVENT_MAKER_PREPARE_DELETE.equals(event)
					|| AdvsPaymentAction.EVENT_REFRESH.equals(event)) {

				String initAdvEvent = (String) map.get("initAdvEvent");
				if ((initAdvEvent != null) && !initAdvEvent.equals("")) {
					// copy all old values from ORIGINAL value.
					obAdvance = (OBAdvance) CommonUtil.deepClone(advanceList[Integer.parseInt(advanceIndex)]); // get
																												// from
																												// staging
					DefaultLogger.debug(this, "successfull copy all old values from ORIGINAL value");
				}
				resultMap.put("obAdvanceForm", obAdvance);
			}
			else if (AdvsPaymentAction.EVENT_CHECKER_VIEW.equals(event)) { // for
																			// checker
																			// view
				if (advanceList != null) {
					for (int i = 0; i < advanceList.length; i++) {
						if (advanceList[i].getCommonRef() == Long.parseLong(commonRef)) {
							obAdvance = (OBAdvance) advanceList[i];
						}
					}
				}
				if (advanceActualList != null) {
					for (int i = 0; i < advanceActualList.length; i++) {
						if (advanceActualList[i].getCommonRef() == Long.parseLong(commonRef)) {
							obActualAdvance = (OBAdvance) advanceActualList[i];
						}
					}
				}
				if (obAdvance.getAdvanceID() != ICMSConstant.LONG_INVALID_VALUE) {
					DefaultLogger.debug(this, "obAdvance.getAdvanceID()=" + obAdvance.getAdvanceID());
					resultMap.put("obAdvanceForm", obAdvance);
				}
				else {
					resultMap.put("obAdvanceForm", obActualAdvance);
				}
				resultMap.put("obActualAdvance", obActualAdvance); // actual
																	// data
			}
			resultMap.put("obAdvance", obAdvance); // staging data
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}

}