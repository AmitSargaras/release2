/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/warehouse/item/UpdateWarehouseItemCommand.java,v 1.3 2004/08/19 02:13:41 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.warehouse.item;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.commodity.main.bus.warehouse.WarehouseComparator;
import com.integrosys.cms.app.commodity.main.trx.warehouse.IWarehouseTrxValue;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/19 02:13:41 $ Tag: $Name: $
 */

public class UpdateWarehouseItemCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "warehouseItemObj", "com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse", FORM_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "warehouseTrxValue", "com.integrosys.cms.app.commodity.main.trx.warehouse.IWarehouseTrxValue",
						SERVICE_SCOPE }, { "warehouseName", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "warehouseTrxValue",
				"com.integrosys.cms.app.commodity.main.trx.warehouse.IWarehouseTrxValue", SERVICE_SCOPE }, });
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

		int index = Integer.parseInt((String) map.get("indexID"));
		IWarehouseTrxValue trxValue = (IWarehouseTrxValue) map.get("warehouseTrxValue");
		IWarehouse obj = (IWarehouse) map.get("warehouseItemObj");
		IWarehouse[] warehouseList = trxValue.getStagingWarehouse();
		boolean duplicated = false;

		IWarehouse[] existingArray = trxValue.getStagingWarehouse();
		String warehouseName = ((String) map.get("warehouseName")).trim().toUpperCase();
		if (existingArray != null) {
			for (int i = 0; (i < existingArray.length) && !duplicated; i++) {
				if ((index != i) && warehouseName.equals(existingArray[i].getName().trim().toUpperCase())) {
					duplicated = true;
				}
			}
		}
		if (duplicated) {
			exceptionMap.put("warehouseName", new ActionMessage("error.warehouse.duplicate"));
		}
		else {
			warehouseList[index] = obj;
			Arrays.sort(warehouseList, new WarehouseComparator(WarehouseComparator.BY_NAME));

			trxValue.setStagingWarehouse(warehouseList);
			result.put("warehouseTrxValue", trxValue);
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
