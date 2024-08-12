/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/warehouse/list/WarehouseListMapper.java,v 1.2 2004/06/04 05:12:02 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.warehouse.list;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.commodity.main.trx.warehouse.IWarehouseTrxValue;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:12:02 $ Tag: $Name: $
 */

public class WarehouseListMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		WarehouseListForm aForm = (WarehouseListForm) cForm;

		if (aForm.getEvent().equals(WarehouseListAction.EVENT_DELETE)) {
			IWarehouseTrxValue trxValue = (IWarehouseTrxValue) inputs.get("warehouseTrxValue");
			IWarehouse[] staging = trxValue.getStagingWarehouse();

			String[] chkDelete = aForm.getChkDeletes();

			if (chkDelete != null) {
				if (chkDelete.length <= staging.length) {
					int numDelete = 0;
					for (int i = 0; i < chkDelete.length; i++) {
						if (Integer.parseInt(chkDelete[i]) < staging.length) {
							numDelete++;
						}
					}
					if (numDelete != 0) {
						IWarehouse[] newList = new IWarehouse[staging.length - numDelete];
						int i = 0, j = 0;
						while (i < staging.length) {
							if ((j < chkDelete.length) && (Integer.parseInt(chkDelete[j]) == i)) {
								j++;
							}
							else {
								newList[i - j] = staging[i];
							}
							i++;
						}
						trxValue.setStagingWarehouse(newList);
					}
				}
			}
			return trxValue;
		}
		return inputs;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		WarehouseListForm aForm = (WarehouseListForm) cForm;

		aForm.setChkDeletes(new String[0]);

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "warehouseTrxValue",
				"com.integrosys.cms.app.commodity.main.trx.warehouse.IWarehouseTrxValue", SERVICE_SCOPE }, });
	}
}
