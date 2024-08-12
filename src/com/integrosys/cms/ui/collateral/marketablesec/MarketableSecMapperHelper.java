/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/marketablesec/MarketableSecMapperHelper.java,v 1.9 2006/04/05 07:58:22 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.marketablesec;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/04/05 07:58:22 $
 * Tag: $Name:  $
 */

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.OBMarketableEquity;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class MarketableSecMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		IMarketableCollateral iMarket = (IMarketableCollateral) obj;
		MarketableSecForm aForm = (MarketableSecForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		if (aForm.getEvent().equals(MarketableSecAction.EVENT_DELETE_ITEM)) {
			if (aForm.getDeleteItem() != null) {
				String[] id = aForm.getDeleteItem();

				IMarketableEquity[] oldList = iMarket.getEquityList();
				if (id.length <= oldList.length) {
					int numDelete = 0;
					for (int i = 0; i < id.length; i++) {
						if (Integer.parseInt(id[i]) < oldList.length) {
							numDelete++;
						}
					}
					if (numDelete != 0) {
						IMarketableEquity[] newList = new OBMarketableEquity[oldList.length - numDelete];
						int i = 0, j = 0;
						DefaultLogger.debug("MarketableSecMapperHelper", "id length: " + id.length);
						while (i < oldList.length) {
							if ((j < id.length) && (Integer.parseInt(id[j]) == i)) {
								j++;
							}
							else {
								newList[i - j] = oldList[i];
							}
							i++;
						}
						iMarket.setEquityList(newList);
					}
				}
			}
		}

		try {
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getMinimalFSV())) {
				iMarket.setMinimalFSV(null);
			}
			else {
				iMarket.setMinimalFSVCcyCode(iMarket.getCurrencyCode());
				iMarket.setMinimalFSV(CurrencyManager.convertToAmount(locale, iMarket.getMinimalFSVCcyCode(), aForm
						.getMinimalFSV()));
			}
			
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getCappedPrice())) iMarket.setCappedPrice(null);
			else iMarket.setCappedPrice(CurrencyManager.convertToAmount(locale, 
					iMarket.getCurrencyCode(), aForm.getCappedPrice()));
			iMarket.setStockCounterCode(aForm.getStockCounterCode());
			
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getInterestRate())) 
				iMarket.setInterestRate(ICMSConstant.DOUBLE_INVALID_VALUE);
			else iMarket.setInterestRate(Double.parseDouble(aForm.getInterestRate()));
		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.MarketableSecMapperHelper",
					"error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return iMarket;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		MarketableSecForm aForm = (MarketableSecForm) cForm;
		aForm.setDeleteItem(new String[0]);
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IMarketableCollateral iObj = (IMarketableCollateral) obj;
		try {
			if ((iObj.getMinimalFSV() != null) && (iObj.getMinimalFSV().getCurrencyCode() != null)) {
				if (iObj.getMinimalFSV().getAmount() >= 0) {
					aForm.setMinimalFSV(CurrencyManager.convertToString(locale, iObj.getMinimalFSV()));
				}
			}
			
			if (iObj.getCappedPrice()==null) aForm.setCappedPrice("");
			else aForm.setCappedPrice(CurrencyManager.convertToString(locale, iObj.getCappedPrice()));
			
			aForm.setStockCounterCode(iObj.getStockCounterCode());
			
			if (iObj.getInterestRate()==ICMSConstant.DOUBLE_INVALID_VALUE) aForm.setInterest("");
			else aForm.setInterestRate(""+iObj.getInterestRate());
			
		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.MarketableSecMapperHelper",
					"error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return aForm;
	}

}
