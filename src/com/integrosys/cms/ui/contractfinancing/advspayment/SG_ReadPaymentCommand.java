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
import com.integrosys.cms.app.contractfinancing.bus.IPayment;
import com.integrosys.cms.app.contractfinancing.bus.OBAdvance;
import com.integrosys.cms.app.contractfinancing.bus.OBPayment;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class SG_ReadPaymentCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public SG_ReadPaymentCommand() {
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
				{ "obAdvance", "com.integrosys.cms.app.contractfinancing.bus.OBAdvance", SERVICE_SCOPE },
				{ "obActualAdvance", "com.integrosys.cms.app.contractfinancing.bus.OBAdvance", SERVICE_SCOPE }, // for
																												// checker
				{ "paymentIndex", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
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
				{ "obPaymentForm", "com.integrosys.cms.app.contractfinancing.bus.OBPayment", FORM_SCOPE }, // need
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
																												// and
																												// maker
																												// view
																												// page
				{ "obPayment", "com.integrosys.cms.app.contractfinancing.bus.OBPayment", REQUEST_SCOPE }, // need
																											// for
																											// checker
																											// and
																											// maker
																											// view
																											// page
				{ "obActualPayment", "com.integrosys.cms.app.contractfinancing.bus.OBPayment", REQUEST_SCOPE }, // need
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
		String paymentIndex = (String) map.get("paymentIndex");
		String commonRef = (String) map.get("commonRef");

		try {

			OBAdvance obAdvance = (OBAdvance) map.get("obAdvance"); // get from
																	// service
																	// scope
			OBAdvance obActualAdvance = (OBAdvance) map.get("obActualAdvance"); // get
																				// from
																				// service
																				// scope

			IPayment[] paymentList = null;
			if (obAdvance != null) { // staging will be null if this is new
										// record
				paymentList = obAdvance.getPaymentList();
			}
			IPayment[] paymentActualList = null;
			if (obActualAdvance != null) { // actual will be null if this is new
											// record
				paymentActualList = obActualAdvance.getPaymentList();
			}
			OBPayment obPayment = null;
			OBPayment obActualPayment = null;

			if (PaymentAction.EVENT_VIEW.equals(event) || PaymentAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)
					|| PaymentAction.EVENT_MAKER_PREPARE_DELETE.equals(event)) {
				obPayment = (OBPayment) paymentList[Integer.parseInt(paymentIndex)];
				resultMap.put("obPaymentForm", obPayment);
			}
			else if (AdvsPaymentAction.EVENT_CHECKER_VIEW.equals(event)) { // for
																			// checker
																			// view
				if (paymentList != null) {
					for (int i = 0; i < paymentList.length; i++) {
						if (paymentList[i].getCommonRef() == Long.parseLong(commonRef)) {
							obPayment = (OBPayment) paymentList[i];
						}
					}
				}

				if (paymentActualList != null) {
					for (int i = 0; i < paymentActualList.length; i++) {
						if (paymentActualList[i].getCommonRef() == Long.parseLong(commonRef)) {
							obActualPayment = (OBPayment) paymentActualList[i];
						}
					}
				}
				// if (obAdvance.getAdvanceID() !=
				// ICMSConstant.LONG_INVALID_VALUE) {
				//DefaultLogger.debug(this,"obAdvance.getAdvanceID()="+obAdvance
				// .getAdvanceID());
				// resultMap.put("obAdvanceForm", obAdvance);
				// } else {
				// resultMap.put("obAdvanceForm", obActualAdvance);
				// }

				if (obPayment != null) {
					resultMap.put("obPaymentForm", obPayment);
				}
				else {
					resultMap.put("obPaymentForm", obActualPayment);
				}
				resultMap.put("obActualPayment", obActualPayment); // actual
																	// data
				resultMap.put("obActualAdvance", obActualAdvance); // actual
																	// data
			}
			resultMap.put("obPayment", obPayment); // staging data
			resultMap.put("obAdvance", obAdvance); // staging data

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			DefaultLogger.debug(this, "Going out SG_ReadPaymentCommand.");
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}

}