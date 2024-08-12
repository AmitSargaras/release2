/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityuom/list/CommodityUOMListMapper.java,v 1.2 2004/06/04 05:11:44 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityuom.list;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure;
import com.integrosys.cms.app.commodity.main.trx.uom.IUnitofMeasureTrxValue;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:11:44 $ Tag: $Name: $
 */

public class CommodityUOMListMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		CommodityUOMListForm aForm = (CommodityUOMListForm) cForm;
		if (aForm.getEvent().equals(CommodityUOMListAction.EVENT_DELETE)) {
			IUnitofMeasureTrxValue trxValue = (IUnitofMeasureTrxValue) inputs.get("commodityUOMTrxValue");
			IUnitofMeasure[] staging = trxValue.getStagingUnitofMeasure();

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
						IUnitofMeasure[] newList = new IUnitofMeasure[staging.length - numDelete];
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
						trxValue.setStagingUnitofMeasure(newList);
					}
				}
			}
			return trxValue;
		}
		return inputs;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		CommodityUOMListForm aForm = (CommodityUOMListForm) cForm;

		aForm.setChkDeletes(new String[0]);

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "commodityUOMTrxValue",
				"com.integrosys.cms.app.commodity.main.trx.uom.IUnitofMeasureTrxValue", SERVICE_SCOPE }, });
	}
}
