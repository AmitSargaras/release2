/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/warehouse/list/ReadWarehouseListCommand.java,v 1.4 2004/08/19 02:14:02 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.warehouse.list;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.commodity.main.bus.warehouse.WarehouseComparator;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.commodity.main.trx.warehouse.IWarehouseTrxValue;
import com.integrosys.cms.app.commodity.main.trx.warehouse.OBWarehouseTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/19 02:14:02 $ Tag: $Name: $
 */

public class ReadWarehouseListCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, { "country", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, });
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
				{ "warehouseTrxValue", "com.integrosys.cms.app.commodity.main.trx.warehouse.IWarehouseTrxValue",
						SERVICE_SCOPE }, { "warehouseCountry", "java.lang.String", SERVICE_SCOPE }, });
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

		String event = (String) map.get("event");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		IWarehouseTrxValue trxValue = new OBWarehouseTrxValue();
		String country = null;
		if (!event.equals(EVENT_PREPARE) && !event.equals(EVENT_READ)) {
			String trxID = (String) map.get("trxID");
			try {
				trxValue = proxy.getWarehouseByTrxID(ctx, trxID);
				if ((trxValue.getStagingWarehouse() != null) && (trxValue.getStagingWarehouse().length > 0)) {
					country = trxValue.getStagingWarehouse()[0].getCountryCode();
				}
				else if ((trxValue.getWarehouse() != null) && (trxValue.getWarehouse().length > 0)) {
					country = trxValue.getWarehouse()[0].getCountryCode();
				}
			}
			catch (Exception e) {
				DefaultLogger.debug(this, "error at proxy get warehouse by trxID");
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		else {
			country = (String) map.get("country");
			try {
				trxValue = proxy.getWarehouseTrxValue(ctx, country);
				if (event.equals(EVENT_PREPARE) && !trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
						&& !trxValue.getStatus().equals(ICMSConstant.STATE_ND)) {
					result.put("wip", "wip");
				}
				if (trxValue.getWarehouse() != null) {
					IWarehouse[] staging = (IWarehouse[]) AccessorUtil.deepClone(trxValue.getWarehouse());
					trxValue.setStagingWarehouse(staging);
				}
			}
			catch (Exception e) {
				DefaultLogger.debug(this, "error at proxy get warehouse by country");
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		if (trxValue.getWarehouse() != null) {
			Arrays.sort(trxValue.getWarehouse(), new WarehouseComparator(WarehouseComparator.BY_NAME));
		}
		if (trxValue.getStagingWarehouse() != null) {
			Arrays.sort(trxValue.getStagingWarehouse(), new WarehouseComparator(WarehouseComparator.BY_NAME));
		}

		result.put("warehouseTrxValue", trxValue);
		result.put("warehouseCountry", country);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
