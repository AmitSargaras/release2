/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/proxy/CommodityMaintenanceProxyHelper.java,v 1.2 2004/06/04 04:53:42 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.proxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.commodity.main.trx.warehouse.IWarehouseTrxValue;
import com.integrosys.cms.app.commodity.main.trx.warehouse.OBWarehouseTrxValue;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Apr 15, 2004 Time:
 * 1:57:23 PM To change this template use File | Settings | File Templates.
 */
public class CommodityMaintenanceProxyHelper {
	public static IWarehouseTrxValue filterByCountryCode(IWarehouseTrxValue trxValue, String countryCode) {

		DefaultLogger.debug("CommodityMaintenanceProxyHelper", "$$$Debug:0 filterByCountryCode " + countryCode);
		if (trxValue != null) {
			DefaultLogger.debug("CommodityMaintenanceProxyHelper", "$$$Debug:1 filterByCountryCode " + countryCode);
			IWarehouse[] actualValues = trxValue.getWarehouse();
			IWarehouse[] filteredActualValues = filterWarehousesByCountryCode(actualValues, countryCode);
			((OBWarehouseTrxValue) trxValue).setWarehouse(filteredActualValues);

			DefaultLogger.debug("CommodityMaintenanceProxyHelper", "$$$Debug:2 filterByCountryCode " + countryCode);
			IWarehouse[] stagingValues = trxValue.getStagingWarehouse();
			IWarehouse[] filteredStagingValues = filterWarehousesByCountryCode(stagingValues, countryCode);
			((OBWarehouseTrxValue) trxValue).setStagingWarehouse(filteredStagingValues);

		}
		return trxValue;
	}

	private static IWarehouse[] filterWarehousesByCountryCode(IWarehouse[] warehouses, String countryCode) {
		IWarehouse[] returnValues = null;
		if ((warehouses != null) && (countryCode != null)) {
			Collection actualValuesCol = Arrays.asList(warehouses);
			ArrayList returnList = new ArrayList();

			for (Iterator iterator = actualValuesCol.iterator(); iterator.hasNext();) {
				IWarehouse warehouse = (IWarehouse) iterator.next();
				if (warehouse.getCountryCode().equals(countryCode)) {
					returnList.add(warehouse);
				}
			}

			if (returnList.size() > 0) {
				returnValues = new IWarehouse[returnList.size()];
				returnValues = (IWarehouse[]) returnList.toArray(returnValues);
			}
		}

		return returnValues;
	}

}
