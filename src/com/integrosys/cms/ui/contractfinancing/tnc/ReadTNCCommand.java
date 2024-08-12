/**
 * Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing.tnc;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.contractfinancing.bus.ITNC;
import com.integrosys.cms.app.contractfinancing.bus.OBTNC;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class ReadTNCCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadTNCCommand() {
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
				{ "tncIndex", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] { { "obTncForm", "com.integrosys.cms.app.contractfinancing.bus.OBTNC", FORM_SCOPE }, // need
																													// for
																													// maker
																													// edit
																													// page
																													// ,
																													// checker
																													// view
				{ "obTnc", "com.integrosys.cms.app.contractfinancing.bus.OBTNC", REQUEST_SCOPE }, // need
																									// for
																									// checker
																									// and
																									// maker
																									// view
																									// page
				{ "obActualTnc", "com.integrosys.cms.app.contractfinancing.bus.OBTNC", REQUEST_SCOPE }, // need
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

		String tncIndex = (String) map.get("tncIndex");
		String commonRef = (String) map.get("commonRef");

		try {
			IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) map.get("contractFinancingTrxValue");

			ITNC[] tncList = trxValue.getStagingContractFinancing().getTncList();
			ITNC[] tncActualList = null;
			if (trxValue.getContractFinancing() != null) { // actual will be
															// null if this is
															// new record
				tncActualList = trxValue.getContractFinancing().getTncList();
			}
			OBTNC obTnc = null;
			OBTNC obActualTnc = null;

			if (TNCAction.EVENT_VIEW.equals(event) || TNCAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)) {
				obTnc = (OBTNC) tncList[Integer.parseInt(tncIndex)];
				resultMap.put("obTncForm", obTnc);
			}
			else { // for checker view
				for (int i = 0; i < tncList.length; i++) {
					if (tncList[i].getCommonRef() == Long.parseLong(commonRef)) {
						obTnc = (OBTNC) tncList[i];
					}
				}
				if (tncActualList != null) {
					for (int i = 0; i < tncActualList.length; i++) {
						if (tncActualList[i].getCommonRef() == Long.parseLong(commonRef)) {
							obActualTnc = (OBTNC) tncActualList[i];
						}
					}
				}
				if (obTnc == null) { // when delete staging
					resultMap.put("obTncForm", obActualTnc);
				}
				else {
					resultMap.put("obTncForm", obTnc);
				}
				resultMap.put("obActualTnc", obActualTnc);
			}
			resultMap.put("obTnc", obTnc); // staging data
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}

}