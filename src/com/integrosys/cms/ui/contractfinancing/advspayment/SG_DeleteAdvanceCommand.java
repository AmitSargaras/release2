/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing.advspayment;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.contractfinancing.bus.IAdvance;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.contractfinancing.bus.IPayment;
import com.integrosys.cms.app.contractfinancing.bus.OBAdvance;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;

/**
 * Created by IntelliJ IDEA. User: Tan Kien Leong Date: Mar 21, 2007 Time:
 * 11:11:09 AM To change this template use File | Settings | File Templates.
 */
public class SG_DeleteAdvanceCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public SG_DeleteAdvanceCommand() {
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
				{ "advanceIndex", "java.lang.String", SERVICE_SCOPE },
				{ "contractFinancingTrxValue",
						"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return new String[][] {};
	}

	public HashMap doExecute(HashMap hashMap) throws CommandProcessingException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		try {
			DefaultLogger.debug(this, "in DeleteAdvanceCommand");

			IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) hashMap.get("contractFinancingTrxValue");
			IContractFinancing contractFinancingObj = trxValue.getStagingContractFinancing();

			int advanceIndex = Integer.parseInt((String) hashMap.get("advanceIndex"));
			ArrayList advanceList = new ArrayList();
			IAdvance[] oldAdvance = contractFinancingObj.getAdvanceList();
			if ((oldAdvance != null) && (oldAdvance.length != 0)) {
				for (int i = 0; i < oldAdvance.length; i++) {

					if (i == advanceIndex) {

						OBAdvance obAdvance = (OBAdvance) oldAdvance[i];
						if (obAdvance.getAdvanceID() != ICMSConstant.LONG_INVALID_VALUE) {
							obAdvance.setIsDeleted(true);
							deleteChild(obAdvance);
							advanceList.add(obAdvance);
						}
						else {
							// not to add
						}
					}
					else {
						OBAdvance obAdvance = (OBAdvance) oldAdvance[i];
						advanceList.add(obAdvance);
					}
				}
			}

			contractFinancingObj.setAdvanceList((IAdvance[]) advanceList.toArray(new OBAdvance[0]));

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}

	public OBAdvance deleteChild(OBAdvance obAdvance) {
		IPayment[] paymentList = obAdvance.getPaymentList();
		if (paymentList != null) {
			for (int i = 0; i < paymentList.length; i++) {
				paymentList[i].setIsDeleted(true);
			}
		}
		obAdvance.setPaymentList(paymentList);
		return obAdvance;
	}
}