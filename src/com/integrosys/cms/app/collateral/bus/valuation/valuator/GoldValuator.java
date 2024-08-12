package com.integrosys.cms.app.collateral.bus.valuation.valuator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargegold.ISpecificChargeGold;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.IValuator;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationDetailIncompleteException;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.GoldValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.support.GoldFeedProfileSingleton;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.gold.OBGoldFeedEntry;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Sep 9, 2008 Time: 10:47:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class GoldValuator implements IValuator {

	private GoldFeedProfileSingleton goldFeedProfileSingleton;

	public void setGoldFeedProfileSingleton(GoldFeedProfileSingleton goldFeedProfileSingleton) {
		this.goldFeedProfileSingleton = goldFeedProfileSingleton;
	}

	public GoldFeedProfileSingleton getGoldFeedProfileSingleton() {
		return goldFeedProfileSingleton;
	}

	public GoldValuator() {
	}

	public boolean checkCompleteForVal(IValuationModel model, List errorDesc) {
		GoldValuationModel valModel = (GoldValuationModel) model;
		boolean flag = true;

		if (valModel.getGoldGrade() == null || valModel.getGoldGrade().equals("")) {
			errorDesc.add("Gold grade is not defined");
			flag = false;
		}

		if (valModel.getGoldWeight() == ICMSConstant.DOUBLE_INVALID_VALUE) {
			errorDesc.add("Gold weight is not defined");
			flag = false;
		}

		if (valModel.getGoldUOM() == null || valModel.getGoldUOM().equals("")) {
			errorDesc.add("Gold (weight) unit of measurement is not defined");
			flag = false;
		}

		return flag;
	}

	public void performValuation(IValuationModel model) throws ValuationException {
		GoldValuationModel valModel = (GoldValuationModel) model;
		Map valuationProfile = goldFeedProfileSingleton.getProfile();

		OBGoldFeedEntry feedEntry = (OBGoldFeedEntry) valuationProfile.get(valModel.getGoldGrade());
		if (feedEntry == null) {
			List errorList = new ArrayList();
			errorList.add("No matching valuation profiles are found");
			throw new ValuationDetailIncompleteException("No gold feed for gold grade [" + valModel.getGoldGrade()
					+ "] valuation profiles are found", errorList);
		}

		if (valModel.getGoldUOM().equals(feedEntry.getUnitMeasurementNum())) {

			// omv amount in feedEntry currency
			Amount omv = new Amount(valModel.getGoldWeight() * feedEntry.getUnitPrice().doubleValue(), feedEntry
					.getCurrencyCode());

			// if sec currency and feed entry curreny is not then same, then
			// perform conversion
			Amount convertOMVAmt = (valModel.getSecCurrency().equals(feedEntry.getCurrencyCode())) ? omv
					: AmountConversion.getConversionAmount(omv, valModel.getSecCurrency());

			valModel.setGoldUnitPrice(new Amount(feedEntry.getUnitPrice(),
					new CurrencyCode(feedEntry.getCurrencyCode())));
			valModel.setValuationDate(new Date());
			valModel.setValOMV(convertOMVAmt);

		}
		else {
			// unit of measurement does not match - currently there is only 1
			// UOM accepted
			List errorList = new ArrayList();
			errorList.add("No conversion for the unit of measurement found");
			throw new ValuationDetailIncompleteException("No conversion for UOM of [" + valModel.getGoldUOM()
					+ "] is found", errorList);
		}

	}

	public void saveOnlineValuationInfoInCollateral(ICollateral collateral, IValuationModel model) {
		ISpecificChargeGold goldCollateral = (ISpecificChargeGold) collateral;
		GoldValuationModel valModel = (GoldValuationModel) model;
		goldCollateral.setGoldUnitPrice(valModel.getGoldUnitPrice());
	}

	public IValuationModel createValuationModelInstance() {
		return new GoldValuationModel();
	}

}
