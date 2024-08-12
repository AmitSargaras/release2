/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/warehouse/item/ReadWarehouseItemCommand.java,v 1.3 2004/09/01 09:03:11 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.warehouse.item;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.commodity.main.bus.warehouse.OBWarehouse;
import com.integrosys.cms.app.commodity.main.trx.warehouse.IWarehouseTrxValue;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/09/01 09:03:11 $ Tag: $Name: $
 */

public class ReadWarehouseItemCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "warehouseTrxValue", "com.integrosys.cms.app.commodity.main.trx.warehouse.IWarehouseTrxValue",
						SERVICE_SCOPE }, { "from_event", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "warehouseItemObj", "com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse", FORM_SCOPE },
				{ "stageWarehouse", "com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse", REQUEST_SCOPE },
				{ "actualWarehouse", "com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse", REQUEST_SCOPE }, });
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

		long ref = Long.parseLong((String) map.get("indexID"));
		String from_event = (String) map.get("from_event");
		IWarehouseTrxValue trxValue = (IWarehouseTrxValue) map.get("warehouseTrxValue");
		IWarehouse[] list = trxValue.getStagingWarehouse();
		IWarehouse warehouse = new OBWarehouse();
		if ((from_event != null) && (from_event.equals(WarehouseItemAction.EVENT_PROCESS))) {
			warehouse = getItem(list, ref);
			if (warehouse == null) {
				warehouse = getItem(trxValue.getWarehouse(), ref);
			}
			IWarehouse stageObj = getItem(list, ref);
			IWarehouse actualObj = getItem(trxValue.getWarehouse(), ref);
			result.put("stageWarehouse", stageObj);
			result.put("actualWarehouse", actualObj);
		}
		else {
			warehouse = list[(int) ref];
		}
		result.put("warehouseItemObj", warehouse);
		result.put("from_event", from_event);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private IWarehouse getItem(IWarehouse temp[], long commonRef) {
		IWarehouse item = null;
		if (temp == null) {
			return item;
		}
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getCommonRef() == commonRef) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}
}
