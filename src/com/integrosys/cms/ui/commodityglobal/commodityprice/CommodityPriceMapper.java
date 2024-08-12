/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprice/CommodityPriceMapper.java,v 1.6 2006/03/03 01:39:10 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprice;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.main.bus.price.ICommodityPrice;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.trx.price.ICommodityPriceTrxValue;
import com.integrosys.cms.ui.commodityglobal.commodityprofile.CMDTProfHelper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/03/03 01:39:10 $ Tag: $Name: $
 */

public class CommodityPriceMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		CommodityPriceForm aForm = (CommodityPriceForm) cForm;
		ICommodityPriceTrxValue trxValue = (ICommodityPriceTrxValue) inputs.get("commPriceTrxValue");
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICommodityPrice[] priceList = null;
		Date stageDate;
		try {
			priceList = (ICommodityPrice[]) AccessorUtil.deepClone(trxValue.getStagingCommodityPrice());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		String ricType = (String) inputs.get("ricType");
		boolean isRIC = !IProfile.PRICE_TYPE_NOC_RIC.equals(ricType);
		if (priceList != null) {
			String[] priceUOM = aForm.getPriceUOM();
			String[] closePriceCcy = aForm.getClosePriceCcy();
			String[] closePriceAmt = aForm.getClosePriceAmt();
			String[] closeUpdateDate = aForm.getCloseUpdateDate();
			String[] currentPriceCcy = aForm.getCurrentPriceCcy();
			String[] currentPriceAmt = aForm.getCurrentPriceAmt();
			String[] currentUpdateDate = aForm.getCurrentUpdateDate();
			String[] chkUpdate = aForm.getUpdateCheck();
			for (int i = 0; (chkUpdate != null) && (i < chkUpdate.length); i++) {
				int index = Integer.parseInt(chkUpdate[i]);
				ICommodityPrice price = priceList[index];
				price.setPriceUOM(priceUOM[index]);
				if (!isEmptyOrNull(closePriceCcy[index]) && !isEmptyOrNull(closePriceAmt[index])) {
					try {
						price.setClosePrice(UIUtil.convertToAmount(locale, closePriceCcy[index], closePriceAmt[index]));
					}
					catch (Exception e) {
						e.printStackTrace();
						throw new MapperException(e.getMessage());
					}
				}
				else {
					price.setClosePrice(null);
				}
				stageDate = compareDate(locale, price.getCloseUpdateDate(), closeUpdateDate[index]);
				price.setCloseUpdateDate(stageDate);
				if (isRIC) {
					if (!isEmptyOrNull(currentPriceCcy[index]) && !isEmptyOrNull(currentPriceAmt[index])) {
						try {
							price.setCurrentPrice(UIUtil.convertToAmount(locale, currentPriceCcy[index],
									currentPriceAmt[index]));
						}
						catch (Exception e) {
							e.printStackTrace();
							throw new MapperException(e.getMessage());
						}
					}
					else {
						price.setCurrentPrice(null);
					}
					stageDate = compareDate(locale, price.getCurrentUpdateDate(), currentUpdateDate[index]);
					price.setCurrentUpdateDate(stageDate);
				}
				priceList[index] = price;
			}
		}

		return Arrays.asList(priceList);
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		CommodityPriceForm aForm = (CommodityPriceForm) cForm;
		ICommodityPrice[] priceList = (ICommodityPrice[]) ((Collection) obj).toArray(new ICommodityPrice[0]);
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		if (priceList != null) {
			String[] commodityProduct = new String[priceList.length];
			String[] priceType = new String[priceList.length];
			String[] ric = new String[priceList.length];
			String[] priceUOM = new String[priceList.length];
			String[] closePriceCcy = new String[priceList.length];
			String[] closePriceAmt = new String[priceList.length];
			String[] closeUpdateDate = new String[priceList.length];
			String[] currentPriceCcy = new String[priceList.length];
			String[] currentPriceAmt = new String[priceList.length];
			String[] currentUpdateDate = new String[priceList.length];

			for (int i = 0; i < priceList.length; i++) {
				ICommodityPrice price = priceList[i];
				IProfile profile = price.getCommodityProfile();
				commodityProduct[i] = profile.getProductSubType();
				if (!isEmptyOrNull(profile.getPriceType())) {
					priceType[i] = CMDTProfHelper.getPriceTypeDesc(profile.getPriceType());
				}
				ric[i] = profile.getReuterSymbol();
				priceUOM[i] = price.getPriceUOM();
				Amount closePrice = price.getClosePrice();
				if (closePrice != null) {
					closePriceCcy[i] = closePrice.getCurrencyCode();
					if (closePrice.getAmount() > 0) {
						try {
							closePriceAmt[i] = UIUtil.formatNumber(closePrice.getAmountAsBigDecimal(), 6, locale);
						}
						catch (Exception e) {
							throw new MapperException(e.getMessage());
						}
					}
					else {
						closePriceAmt[i] = "";
					}
				}
				else {
					closePriceCcy[i] = "";
					closePriceAmt[i] = "";
				}
				closeUpdateDate[i] = DateUtil.formatDate(locale, price.getCloseUpdateDate());
				Amount currentPrice = price.getCurrentPrice();
				if (currentPrice != null) {
					currentPriceCcy[i] = currentPrice.getCurrencyCode();
					if (currentPrice.getAmount() > 0) {
						try {
							currentPriceAmt[i] = UIUtil.formatNumber(currentPrice.getAmountAsBigDecimal(), 6, locale);
						}
						catch (Exception e) {
							throw new MapperException(e.getMessage());
						}
					}
					else {
						currentPriceAmt[i] = "";
					}
				}
				else {
					currentPriceCcy[i] = "";
					currentPriceAmt[i] = "";
				}
				currentUpdateDate[i] = DateUtil.formatDate(locale, price.getCurrentUpdateDate());
			}

			aForm.setCommodityProduct(commodityProduct);
			aForm.setPriceType(priceType);
			aForm.setRic(ric);
			aForm.setPriceUOM(priceUOM);
			aForm.setClosePriceCcy(closePriceCcy);
			aForm.setClosePriceAmt(closePriceAmt);
			aForm.setCloseUpdateDate(closeUpdateDate);
			aForm.setCurrentPriceCcy(currentPriceCcy);
			aForm.setCurrentPriceAmt(currentPriceAmt);
			aForm.setCurrentUpdateDate(currentUpdateDate);
		}

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "ricType", "java.lang.String", REQUEST_SCOPE },
				{ "commPriceTrxValue", "com.integrosys.cms.app.commodity.main.trx.price.ICommodityPriceTrxValue",
						SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
	}

	public static Date compareDate(Locale locale, Date dateOrigin, String dateStage) {
		Date returnDate = DateUtil.convertDate(locale, dateStage);

		if (dateOrigin != null) {
			String originalDate = DateUtil.formatDate(locale, dateOrigin);
			if (originalDate.equals(dateStage)) {
				returnDate = dateOrigin;
			}
		}

		return returnDate;
	}

}
