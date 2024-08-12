/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityuom/item/CommodityUOMItemMapper.java,v 1.5 2004/10/12 01:40:46 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityuom.item;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.main.bus.profile.OBProfile;
import com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure;
import com.integrosys.cms.app.commodity.main.bus.uom.OBUnitofMeasure;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.commodity.main.trx.uom.IUnitofMeasureTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/10/12 01:40:46 $ Tag: $Name: $
 */

public class CommodityUOMItemMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		CommodityUOMItemForm aForm = (CommodityUOMItemForm) cForm;
		int index = Integer.parseInt((String) inputs.get("indexID"));
		Map productSubTypeMap = (Map) inputs.get("productSubTypeMap");
		HashMap uomMap = getUOMMap();

		OBUnitofMeasure obToChange = null;
		OBProfile profile = null;
		if (index == -1) {
			String category = (String) inputs.get("commodityCategory");
			String productType = (String) inputs.get("commodityProductType");
			obToChange = new OBUnitofMeasure();
			profile = new OBProfile();
			profile.setCategory(category);
			profile.setProductType(productType);
		}
		else {
			IUnitofMeasureTrxValue trxValue = (IUnitofMeasureTrxValue) inputs.get("commodityUOMTrxValue");
			IUnitofMeasure[] unitofMeasureList = null;
			unitofMeasureList = trxValue.getStagingUnitofMeasure();
			try {
				obToChange = (OBUnitofMeasure) AccessorUtil.deepClone(unitofMeasureList[index]);
				profile = (OBProfile) obToChange.getCommodityProfile();
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		profile.setProductSubType((String) productSubTypeMap.get(aForm.getCommoditySubType()));
		profile.setProfileID(Long.parseLong(aForm.getCommoditySubType()));
		obToChange.setCommodityProfile(profile);
		obToChange.setProfileID(Long.parseLong(aForm.getCommoditySubType()));

		obToChange.setName(aForm.getUnitOfMeasure());

		obToChange.setMarketQuantity(setQuantity(uomMap, aForm.getMarketUOMVal(), aForm.getMarketUOMUnit()));
		obToChange.setMetricQuantity(setQuantity(uomMap, aForm.getMetricUOMVal(), aForm.getMetricUOMUnit()));
		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		CommodityUOMItemForm aForm = (CommodityUOMItemForm) cForm;
		IUnitofMeasure unitofMeasure = (IUnitofMeasure) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		aForm.setCommoditySubType(String.valueOf(unitofMeasure.getProfileID()));
		aForm.setUnitOfMeasure(unitofMeasure.getName());
		if (unitofMeasure.getMarketQuantity() != null) {
			if (unitofMeasure.getMarketQuantity().getUnitofMeasure() != null) {
				aForm.setMarketUOMUnit(unitofMeasure.getMarketQuantity().getUnitofMeasure().getID());
			}
			try {
				aForm.setMarketUOMVal(UIUtil.formatNumber(unitofMeasure.getMarketQuantity().getQuantity(), 6, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setMarketUOMUnit("");
			aForm.setMarketUOMVal("");
		}

		if (unitofMeasure.getMetricQuantity() != null) {
			if (unitofMeasure.getMetricQuantity().getUnitofMeasure() != null) {
				aForm.setMetricUOMUnit(unitofMeasure.getMetricQuantity().getUnitofMeasure().getID());
			}
			try {
				aForm.setMetricUOMVal(UIUtil.formatNumber(unitofMeasure.getMetricQuantity().getQuantity(), 6, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "commodityUOMTrxValue", "com.integrosys.cms.app.commodity.main.trx.uom.IUnitofMeasureTrxValue",
						SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "commodityCategory", "java.lang.String", SERVICE_SCOPE },
				{ "commodityProductType", "java.lang.String", SERVICE_SCOPE },
				{ "productSubTypeMap", "java.util.Map", SERVICE_SCOPE }, });
	}

	private static Quantity setQuantity(HashMap uomMap, String value, String unit) {
		if (isEmptyOrNull(value)) {
			return null;
		}
		BigDecimal qtyValue = UIUtil.mapStringToBigDecimal(value);
		UOMWrapper uom = (UOMWrapper) uomMap.get(unit);
		Quantity qty = new Quantity(qtyValue, uom);

		return qty;
	}

	private static HashMap getUOMMap() throws MapperException {
		HashMap uomMap = new HashMap();
		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		try {
			UOMWrapper[] uomList = proxy.getUnitofMeasure(ICMSConstant.LONG_INVALID_VALUE);
			if (uomList != null) {
				for (int i = 0; i < uomList.length; i++) {
					uomMap.put(uomList[i].getID(), uomList[i]);
				}
			}
		}
		catch (Exception e) {
			throw new MapperException(e.getMessage());
		}
		return uomMap;
	}
}
