/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprofile/item/CommProfileItemMapper.java,v 1.7 2006/03/03 02:11:02 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprofile.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.main.bus.profile.BuyerComparator;
import com.integrosys.cms.app.commodity.main.bus.profile.IBuyer;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;
import com.integrosys.cms.app.commodity.main.bus.profile.OBBuyer;
import com.integrosys.cms.app.commodity.main.bus.profile.OBProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.OBSupplier;
import com.integrosys.cms.app.commodity.main.bus.profile.SupplierComparator;
import com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/03/03 02:11:02 $ Tag: $Name: $
 */

public class CommProfileItemMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		CommProfileItemForm aForm = (CommProfileItemForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		int index = Integer.parseInt((String) inputs.get("indexID"));
		OBProfile obToChange = null;

		SupplierComparator s = new SupplierComparator();
		BuyerComparator b = new BuyerComparator();

		if (index == -1) {
			obToChange = new OBProfile();
		}
		else {
			IProfileTrxValue trxValue = (IProfileTrxValue) inputs.get("commProfileTrxValue");
			IProfile[] profileList = null;
			profileList = trxValue.getStagingProfile();
			try {
				obToChange = (OBProfile) AccessorUtil.deepClone(profileList[index]);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		obToChange.setCategory(aForm.getCommodityCategory());
		obToChange.setProductType(aForm.getProductType());
		obToChange.setProductSubType(aForm.getProductSubType());

		obToChange.setPriceType(aForm.getPriceType());
		if ((!isEmptyOrNull(aForm.getPriceType())) && aForm.getPriceType().equals(IProfile.PRICE_TYPE_FUTURES)) {
			obToChange.setMarketName(aForm.getMarketName());
			obToChange.setRICType(aForm.getRicFuturesChoice());
			if ((!isEmptyOrNull(aForm.getRicFuturesChoice()))
					&& aForm.getRicFuturesChoice().equals(IProfile.RIC_TYPE_FUTURES)) {
				obToChange.setReuterSymbol(aForm.getRicFutures());
			}
			else {
				obToChange.setReuterSymbol(aForm.getRicFuturesOptions());
			}
		}
		else if ((!isEmptyOrNull(aForm.getPriceType())) && aForm.getPriceType().equals(IProfile.PRICE_TYPE_CASH)) {
			obToChange.setCountryArea(aForm.getCountryArea());
			obToChange.setOutrights(aForm.getOutrights());
			obToChange.setChains(aForm.getChains());
			obToChange.setReuterSymbol(aForm.getRicCash());
			obToChange.setRICType(IProfile.RIC_TYPE_CASH);
		}
		else {
			obToChange.setNonRICDesc(aForm.getNonRICDesc());
			obToChange.setCountryArea(aForm.getCountry());
			obToChange.setRICType(IProfile.PRICE_TYPE_NOC_RIC);
		}

		obToChange.setDifferentialSign(aForm.getPlusmn());
		if (!isEmptyOrNull(aForm.getCommPriceDiff())) {
			try {
				obToChange.setPriceDifferential(UIUtil.mapStringToBigDecimal(aForm.getCommPriceDiff()));
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}
		else {
			obToChange.setPriceDifferential(null);
		}

		ArrayList newSupplier = new ArrayList();
		String[] supplierList = aForm.getSupplierList();

		HashMap supplierMap = (HashMap) inputs.get("supplierMap");
		boolean mapIsNull = (supplierMap == null);
		if (supplierList != null) {
			for (int i = 0; i < supplierList.length; i++) {
				OBSupplier obj;
				if (mapIsNull) {
					obj = new OBSupplier();
					obj.setName(supplierList[i]);
				}
				else {
					obj = (OBSupplier) supplierMap.get(supplierList[i]);
					if (obj == null) {
						obj = new OBSupplier();
						obj.setName(supplierList[i]);
					}
				}
				newSupplier.add(obj);
			}
		}
		obToChange.setSuppliers((OBSupplier[]) newSupplier.toArray(new OBSupplier[0]));
		if (obToChange.getSuppliers() != null) {
			Arrays.sort(obToChange.getSuppliers(), s);
		}

		ArrayList newBuyer = new ArrayList();
		String[] buyerList = aForm.getBuyerList();

		HashMap buyerMap = (HashMap) inputs.get("buyerMap");
		mapIsNull = (buyerMap == null);
		if (buyerList != null) {
			for (int i = 0; i < buyerList.length; i++) {
				OBBuyer obj;
				if (mapIsNull) {
					obj = new OBBuyer();
					obj.setName(buyerList[i]);
				}
				else {
					obj = (OBBuyer) buyerMap.get(buyerList[i]);
					if (obj == null) {
						obj = new OBBuyer();
						obj.setName(buyerList[i]);
					}
				}
				newBuyer.add(obj);
			}
		}
		obToChange.setBuyers((OBBuyer[]) newBuyer.toArray(new OBBuyer[0]));
		if (obToChange.getBuyers() != null) {
			Arrays.sort(obToChange.getBuyers(), b);
		}

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		CommProfileItemForm aForm = (CommProfileItemForm) cForm;
		IProfile profile = (IProfile) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		aForm.setCommodityCategory(profile.getCategory());
		aForm.setProductType(profile.getProductType());
		aForm.setProductSubType(profile.getProductSubType());
		aForm.setPriceType(profile.getPriceType());
		if (IProfile.PRICE_TYPE_FUTURES.equals(profile.getPriceType())) {
			aForm.setMarketName(profile.getMarketName());
			aForm.setRicFuturesChoice(profile.getRICType());
			if ((profile.getRICType() != null) && profile.getRICType().equals(IProfile.RIC_TYPE_FUTURES)) {
				aForm.setRicFutures(profile.getReuterSymbol());
			}
			else {
				aForm.setRicFuturesOptions(profile.getReuterSymbol());
			}
		}
		else if (IProfile.PRICE_TYPE_CASH.equals(profile.getPriceType())) {
			aForm.setCountryArea(profile.getCountryArea());
			aForm.setOutrights(profile.getOutrights());
			aForm.setChains(profile.getChains());
			aForm.setRicCash(profile.getReuterSymbol());
		}
		else {
			aForm.setNonRICCode(profile.getReuterSymbol());
			aForm.setNonRICDesc(profile.getNonRICDesc());
			aForm.setCountry(profile.getCountryArea());
		}

		aForm.setPlusmn(profile.getDifferentialSign());
		try {
			aForm.setCommPriceDiff(UIUtil.formatNumber(profile.getPriceDifferential(), 6, locale));
		}
		catch (Exception e) {
			throw new MapperException(e.getMessage());
		}

		ISupplier[] supplierList = profile.getSuppliers();
		String[] supplier = null;
		if (supplierList != null) {
			supplier = new String[supplierList.length];
			for (int i = 0; i < supplierList.length; i++) {
				supplier[i] = supplierList[i].getName();
			}
		}
		aForm.setSupplierList(supplier);

		IBuyer[] buyerList = profile.getBuyers();
		String[] buyer = null;
		if (buyerList != null) {
			buyer = new String[buyerList.length];
			for (int i = 0; i < buyerList.length; i++) {
				buyer[i] = buyerList[i].getName();
			}
		}
		aForm.setBuyerList(buyer);

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "commProfileTrxValue", "com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue",
						SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "supplierMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "buyerMap", "java.util.HashMap", SERVICE_SCOPE }, });
	}
}
