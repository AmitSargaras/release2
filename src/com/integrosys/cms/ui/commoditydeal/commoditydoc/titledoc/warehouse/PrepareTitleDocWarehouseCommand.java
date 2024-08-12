/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/warehouse/PrepareTitleDocWarehouseCommand.java,v 1.7 2005/05/10 07:02:39 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc.warehouse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.commodity.main.bus.warehouse.WarehouseComparator;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.ui.common.CountryList;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/05/10 07:02:39 $ Tag: $Name: $
 */

public class PrepareTitleDocWarehouseCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "warehouseIndexID", "java.lang.String", REQUEST_SCOPE },
				{ "serviceTitleDocObj", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument",
						SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "countryLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "countryValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "warehouseID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "warehouseValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
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

		CountryList countryList = CountryList.getInstance();
		result.put("countryLabels", countryList.getCountryLabels());
		result.put("countryValues", countryList.getCountryValues());

		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		Collection warehouseID = new ArrayList();
		Collection warehouseName = new ArrayList();
		try {
			IWarehouse[] warehouseList = proxy.getAllWarehouses();
			if (warehouseList != null) {
				Arrays.sort(warehouseList, new WarehouseComparator(WarehouseComparator.BY_NAME));
				for (int i = 0; i < warehouseList.length; i++) {
					warehouseID.add(String.valueOf(warehouseList[i].getWarehouseID()));
					warehouseName.add(warehouseList[i].getName());
				}
			}
		}
		catch (Exception e) {
			throw new CommandProcessingException(e.getMessage());
		}
		String warehouseIndex = (String) map.get("warehouseIndexID");
		ICommodityTitleDocument titleDoc = (ICommodityTitleDocument) map.get("serviceTitleDocObj");
		if ((warehouseIndex != null) && !warehouseIndex.equals("-1") && (titleDoc != null)
				&& (titleDoc.getWarehouseReceipts() != null)) {
			int index = Integer.parseInt(warehouseIndex);
			IWarehouseReceipt receipt = titleDoc.getWarehouseReceipts()[index];
			if ((receipt != null) && (receipt.getWarehouse() != null)
					&& !warehouseID.contains(String.valueOf(receipt.getWarehouse().getWarehouseID()))) {
				warehouseID.add(String.valueOf(receipt.getWarehouse().getWarehouseID()));
				warehouseName.add(receipt.getWarehouse().getName());
			}
		}
		result.put("warehouseID", warehouseID);
		result.put("warehouseValue", warehouseName);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
