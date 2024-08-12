/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insprotection/insswap/cds/AddCDSItemCommand.java,v 1.1 2005/09/29 09:41:57 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.insprotection.insswap.cds;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.insurance.ICDSItem;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditswaps.ICreditDefaultSwaps;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/29 09:41:57 $ Tag: $Name: $
 */

public class AddCDSItemCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "form.CDSItem", "com.integrosys.cms.app.collateral.bus.type.insurance.ICDSItem", FORM_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",
				SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ICDSItem item = (ICDSItem) map.get("form.CDSItem");

		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");

		ICreditDefaultSwaps insurance = (ICreditDefaultSwaps) itrxValue.getStagingCollateral();
		addCDS(insurance, item);

		itrxValue.setStagingCollateral(insurance);
		result.put("serviceColObj", itrxValue);

		DefaultLogger.debug(this, "inside AddCDSItemCommand");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private static void addCDS(ICreditDefaultSwaps insurance, ICDSItem iCheque) {
		ICDSItem[] existingArray = insurance.getCdsItems();
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}

		ICDSItem[] newArray = new ICDSItem[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = iCheque;

		insurance.setCdsItems(newArray);
	}
}
