/**
 * Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing.fdr;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.contractfinancing.bus.IFDR;
import com.integrosys.cms.app.contractfinancing.bus.OBFDR;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class ReadFDRCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadFDRCommand() {
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
				{ "fdrIndex", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] { { "obFdrForm", "com.integrosys.cms.app.contractfinancing.bus.OBFDR", FORM_SCOPE }, // need
																													// for
																													// maker
																													// edit
																													// page
																													// ,
																													// checker
																													// view
				{ "obFdr", "com.integrosys.cms.app.contractfinancing.bus.OBFDR", REQUEST_SCOPE }, // need
																									// for
																									// checker
																									// and
																									// maker
																									// view
																									// page
				{ "obActualFdr", "com.integrosys.cms.app.contractfinancing.bus.OBFDR", REQUEST_SCOPE }, // need
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

		String fdrIndex = (String) map.get("fdrIndex");
		String commonRef = (String) map.get("commonRef");

		try {
			IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) map.get("contractFinancingTrxValue");

			IFDR[] fdrList = trxValue.getStagingContractFinancing().getFdrList();
			IFDR[] fdrActualList = null;
			if (trxValue.getContractFinancing() != null) { // actual will be
															// null if this is
															// new record
				fdrActualList = trxValue.getContractFinancing().getFdrList();
			}
			OBFDR obFdr = null;
			OBFDR obActualFdr = null;

			if (FDRAction.EVENT_VIEW.equals(event) || FDRAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)
					|| FDRAction.EVENT_MAKER_PREPARE_DELETE.equals(event)) {
				obFdr = (OBFDR) fdrList[Integer.parseInt(fdrIndex)];
				resultMap.put("obFdrForm", obFdr);
			}
			else { // for checker view
				for (int i = 0; i < fdrList.length; i++) {
					if (fdrList[i].getCommonRef() == Long.parseLong(commonRef)) {
						obFdr = (OBFDR) fdrList[i];
					}
				}
				if (fdrActualList != null) {
					for (int i = 0; i < fdrActualList.length; i++) {
						if (fdrActualList[i].getCommonRef() == Long.parseLong(commonRef)) {
							obActualFdr = (OBFDR) fdrActualList[i];
						}
					}
				}
				if (obFdr == null) { // when delete staging
					resultMap.put("obFdrForm", obActualFdr);
				}
				else {
					resultMap.put("obFdrForm", obFdr);
				}
				resultMap.put("obActualFdr", obActualFdr);
			}
			resultMap.put("obFdr", obFdr); // staging data
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}

}