/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:  $
 */
package com.integrosys.cms.ui.liquidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.liquidation.bus.IRecovery;
import com.integrosys.cms.app.liquidation.bus.IRecoveryExpense;
import com.integrosys.cms.app.liquidation.bus.IRecoveryIncome;
import com.integrosys.cms.app.liquidation.bus.LiquidationException;
import com.integrosys.cms.app.liquidation.bus.OBLiquidation;
import com.integrosys.cms.app.liquidation.proxy.ILiquidationProxy;
import com.integrosys.cms.app.liquidation.proxy.LiquidationProxyFactory;
import com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: for Maker to update the rejected liquidation
 * Description: command that let the maker to submit the rejected liquidation
 * again for approval
 * 
 * @author $Author: Siew Kheat$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/08$ Tag: $Name$
 */
public class MakerEditRejectedLiquidationCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "InitialLiquidation", "com.integrosys.cms.app.liquidation.bus.OBLiquidation", SERVICE_SCOPE },
				{ "LiquidationTrxValue", "com.integrosys.cms.app.liquidation.trx.OBLiquidationTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "sess.collateralID", "java.lang.String", SERVICE_SCOPE },
				{ "nplInfo", "java.util.Collection", SERVICE_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Liquidation is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		// map ob from col to OB
		OBLiquidation obLiquidation = (OBLiquidation) map.get("InitialLiquidation");

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		String remarks = (String) map.get("remarks");
		String colID = (String) map.get("sess.collateralID");

		ILiquidationTrxValue liquidationTrxVal = (ILiquidationTrxValue) map.get("LiquidationTrxValue");

		try {
			ILiquidationProxy proxy = LiquidationProxyFactory.getProxy();
			liquidationTrxVal.setRemarks(remarks);

			// TODO setCollateral Id
			long collateralID = (colID == null) ? ICMSConstant.LONG_INVALID_VALUE : new Long(colID).longValue();
			obLiquidation.setCollateralID(collateralID);

			DefaultLogger.debug(this, "Status in MakerEditRejectedLiquidation : " + liquidationTrxVal.getStatus());
			updatePrimaryIDWithRefID(obLiquidation);

			ILiquidationTrxValue trxValue = proxy.makerUpdateLiquidation(trxContext, liquidationTrxVal, obLiquidation);
			resultMap.put("request.ITrxValue", trxValue);
			resultMap.put("LiquidationTrxValue", trxValue);

		}
		catch (LiquidationException e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"maker failed to update liquidation into workflow");
			cpe.initCause(e);
			throw cpe;
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	/**
	 * The rejected liquidation itself is a staging record, it contains the
	 * actual primary id in the ref id. Before updating the staging record,
	 * overwrite the primary of all objects with the ref id
	 * @param obLiquidation
	 */
	private void updatePrimaryIDWithRefID(OBLiquidation obLiquidation) {

		if (obLiquidation == null) {
			return;
		}

		if (obLiquidation.getRecoveryExpense() != null) {
			Collection recoveryExpenseList = obLiquidation.getRecoveryExpense();

			Iterator i = recoveryExpenseList.iterator();
			IRecoveryExpense re;
			while (i.hasNext()) {
				re = (IRecoveryExpense) i.next();
				if ((re.getRefID() != ICMSConstant.LONG_INVALID_VALUE) && (re.getRefID() != 0)) {
					re.setRecoveryExpenseID(new Long(re.getRefID()));
				}
			}
		}

		if (obLiquidation.getRecovery() != null) {
			Collection recoveryList = obLiquidation.getRecovery();

			Iterator i = recoveryList.iterator();
			IRecovery rc;
			while (i.hasNext()) {
				rc = (IRecovery) i.next();
				if ((rc.getRefID() != ICMSConstant.LONG_INVALID_VALUE) && (rc.getRefID() != 0)) {
					rc.setRecoveryID(new Long(rc.getRefID()));
				}

				if (rc.getRecoveryIncome() != null) {
					Collection incomeList = rc.getRecoveryIncome();

					Iterator icIterator = incomeList.iterator();
					IRecoveryIncome income;

					while (icIterator.hasNext()) {
						income = (IRecoveryIncome) icIterator.next();

						if ((income.getRefID() != ICMSConstant.LONG_INVALID_VALUE) && (income.getRefID() != 0)) {
							income.setRecoveryIncomeID(new Long(income.getRefID()));
						}
					}
				}
			}
		}

	}

}
