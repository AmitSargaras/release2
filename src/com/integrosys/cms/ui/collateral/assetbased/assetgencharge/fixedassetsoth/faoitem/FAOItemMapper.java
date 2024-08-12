/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/fixedassetsoth/faoitem/FAOItemMapper.java,v 1.7 2005/04/07 12:00:22 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.fixedassetsoth.faoitem;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IFixedAssetOthers;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBFixedAssetOthers;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.GeneralChargeSubTypeMapper;
import com.integrosys.cms.ui.common.ForexHelper;

/**
 * Mapper for Fixed Asset Others
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/04/07 12:00:22 $ Tag: $Name: $
 */

public class FAOItemMapper extends GeneralChargeSubTypeMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		FAOItemForm aForm = (FAOItemForm) cForm;

		IGeneralCharge iCol = (IGeneralCharge) (((ICollateralTrxValue) inputs.get("serviceColObj"))
				.getStagingCollateral());
		IFixedAssetOthers faoItem = null;
		HashMap faoMap = (HashMap) iCol.getFixedAssetOthers();
		String strIndex = (String) inputs.get("indexID");

		if (strIndex.equals("-1")) {
			faoItem = new OBFixedAssetOthers();
		}
		else {
			try {
				faoItem = (IFixedAssetOthers) AccessorUtil.deepClone(faoMap.get(strIndex));
			}
			catch (Exception e) {
				DefaultLogger.error(this, "AccessorUtil.deepClone", e);
				throw new MapperException(e.getMessage());
			}
		}

		faoItem = (IFixedAssetOthers) super.mapFormToOB(cForm, inputs, faoItem);

		locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		currCode = aForm.getValCurrency();

		faoItem.setDescription(aForm.getDescription());
		faoItem.setFAOID(aForm.getFaoID());
		try {
			faoItem.setMargin(AssetGenChargeUtil.setMarginStrToDouble(aForm.getMargin()));

			faoItem.setGrossValue(convertToAmount(aForm.getGrossValueValCurr()));
		}
		catch (Exception e) {
			throw new MapperException(e.getMessage());
		}
		faoItem.setNetValue(((OBFixedAssetOthers) faoItem).getCalculatedNetValue());

		return faoItem;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		FAOItemForm aForm = (FAOItemForm) cForm;

		locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ForexHelper fr = new ForexHelper();

		HashMap faoMap = (HashMap) obj;
		IFixedAssetOthers faoItem = (OBFixedAssetOthers) faoMap.get("obj");
		String cmsSecurityCcy = (String) faoMap.get("ccy");
		CurrencyCode cmsCcy = new CurrencyCode(cmsSecurityCcy);

		aForm = (FAOItemForm) super.mapOBToForm(cForm, obj, inputs);
		aForm.setDescription(faoItem.getDescription());
		aForm.setFaoID(faoItem.getFAOID());

		aForm.setMargin(AssetGenChargeUtil.setMarginDoubleToStr(faoItem.getMargin(), locale));

		Amount amt = faoItem.getGrossValue();
		try {
			aForm.setGrossValueValCurr(convertAmtToString(amt));
		}
		catch (Exception e) {
			throw new MapperException(e.getMessage());
		}

		double value = 0;
		try {
			value = fr.convertAmount(amt, cmsCcy);
			aForm.setGrossValueCMSCurr(CurrencyManager.convertToString(locale, new Amount(value, cmsCcy)));
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Forex Exception!", e);
			aForm.setGrossValueCMSCurr("Forex Error");
		}

		amt = ((OBFixedAssetOthers) faoItem).getCalculatedNetValue();
		value = 0;
		try {
			value = fr.convertAmount(amt, cmsCcy);
			aForm.setNetValue(CurrencyManager.convertToString(locale, new Amount(value, cmsCcy)));
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Forex Exception!", e);
			aForm.setNetValue("Forex Error");
		}

		return aForm;
	}

}
