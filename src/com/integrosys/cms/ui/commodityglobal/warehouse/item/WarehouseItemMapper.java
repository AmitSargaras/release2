/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/warehouse/item/WarehouseItemMapper.java,v 1.3 2004/08/30 12:38:48 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.warehouse.item;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouseContact;
import com.integrosys.cms.app.commodity.main.bus.warehouse.OBWarehouse;
import com.integrosys.cms.app.commodity.main.bus.warehouse.OBWarehouseContact;
import com.integrosys.cms.app.commodity.main.trx.warehouse.IWarehouseTrxValue;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/30 12:38:48 $ Tag: $Name: $
 */

public class WarehouseItemMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		WarehouseItemForm aForm = (WarehouseItemForm) cForm;

		int index = Integer.parseInt((String) inputs.get("indexID"));
		OBWarehouse obToChange = null;
		if (index == -1) {
			String country = (String) inputs.get("warehouseCountry");
			obToChange = new OBWarehouse();
			obToChange.setCountryCode(country);
		}
		else {
			IWarehouseTrxValue trxValue = (IWarehouseTrxValue) inputs.get("warehouseTrxValue");
			IWarehouse[] warehouseList = null;
			warehouseList = trxValue.getStagingWarehouse();
			DefaultLogger.debug(this, "index is: " + index);
			try {
				obToChange = (OBWarehouse) AccessorUtil.deepClone(warehouseList[index]);
			}
			catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, e.getMessage());
			}
		}
		obToChange.setName(aForm.getWarehouseName());

		OBWarehouseContact warehouseContact = (OBWarehouseContact) obToChange.getContact();
		if (warehouseContact == null) {
			warehouseContact = new OBWarehouseContact();
		}
		warehouseContact.setAddressLine1(aForm.getAddress1());
		warehouseContact.setAddressLine2(aForm.getAddress2());
		warehouseContact.setCity(aForm.getCity());
		warehouseContact.setState(aForm.getState());
		warehouseContact.setPostalCode(aForm.getPostalCode());
		warehouseContact.setCountryCode(obToChange.getCountryCode());

		warehouseContact.setAttentionParty(aForm.getContactName());
		warehouseContact.setFaxNumber(aForm.getFax());
		warehouseContact.setEmailAddress(aForm.getEmail());
		warehouseContact.setTelephoneNumer(aForm.getTelephone());
		warehouseContact.setTelex(aForm.getExtensionNumber());

		obToChange.setContact(warehouseContact);
		obToChange.setRemarks(aForm.getWarehouseRemarks());

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		WarehouseItemForm aForm = (WarehouseItemForm) cForm;
		IWarehouse warehouse = (IWarehouse) obj;
		IWarehouseContact contact = warehouse.getContact();

		aForm.setWarehouseName(warehouse.getName());

		if (contact != null) {
			aForm.setAddress1(contact.getAddressLine1());
			aForm.setAddress2(contact.getAddressLine2());
			aForm.setCity(contact.getCity());
			aForm.setState(contact.getState());
			aForm.setPostalCode(contact.getPostalCode());
			aForm.setCountryCode(contact.getCountryCode());

			aForm.setContactName(contact.getAttentionParty());
			aForm.setFax(contact.getFaxNumber());
			aForm.setEmail(contact.getEmailAddress());
			aForm.setTelephone(contact.getTelephoneNumer());
			aForm.setExtensionNumber(contact.getTelex());
		}

		aForm.setWarehouseRemarks(warehouse.getRemarks());
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "warehouseTrxValue", "com.integrosys.cms.app.commodity.main.trx.warehouse.IWarehouseTrxValue",
						SERVICE_SCOPE }, { "warehouseCountry", "java.lang.String", SERVICE_SCOPE }, });
	}
}
