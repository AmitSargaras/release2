package com.integrosys.cms.app.collateral.bus.valuation.valuator;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.VehicleValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.support.CommonCodeRegionSingleton;
import com.integrosys.cms.app.collateral.bus.valuation.support.VehicleFeedProfileSingleton;
import com.integrosys.cms.app.commodity.common.AmountConversion;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Sep 12, 2008 Time: 10:30:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class VehicleValuator extends StrtLineValuator {

	private final static Logger logger = LoggerFactory.getLogger(VehicleValuator.class);

	private VehicleFeedProfileSingleton vehicleFeedProfileSingleton;

	private CommonCodeRegionSingleton commonCodeRegionSingleton;

	public void setVehicleFeedProfileSingleton(VehicleFeedProfileSingleton vehicleFeedProfileSingleton) {
		this.vehicleFeedProfileSingleton = vehicleFeedProfileSingleton;
	}

	public VehicleFeedProfileSingleton getVehicleFeedProfileSingleton() {
		return vehicleFeedProfileSingleton;
	}

	public CommonCodeRegionSingleton getCommonCodeRegionSingleton() {
		return commonCodeRegionSingleton;
	}

	public void setCommonCodeRegionSingleton(CommonCodeRegionSingleton commonCodeRegionSingleton) {
		this.commonCodeRegionSingleton = commonCodeRegionSingleton;
	}

	public boolean checkCompleteForVal(IValuationModel model, List errorDesc) {
		VehicleValuationModel vModel = (VehicleValuationModel) model;
		setDetailsFromSingleton(vModel);
		return (isValidForFeedValuation(vModel, errorDesc)) || super.checkCompleteForVal(model, errorDesc);
	}

	private void setDetailsFromSingleton(VehicleValuationModel model) {
		Object region = commonCodeRegionSingleton.getProfile().get(model.getSecurityOrganisation());
		if (region != null) {
			model.setRegion((String) region);
		}
	}

	private boolean isValidForFeedValuation(VehicleValuationModel model, List errorDesc) {

		boolean completedFlag = true;
		completedFlag = completedFlag && (!(model.getMake() == null || model.getMake().equals("")));
		if (!completedFlag) {
			errorDesc.add("Collateral's make is empty or null: " + model.getMake());
		}

		completedFlag = completedFlag && (!(model.getModel() == null || model.getModel().equals("")));
		if (model.getModel() == null || model.getModel().equals("")) {
			errorDesc.add("Collateral's model is empty or null: " + model.getModel());
		}

		completedFlag = completedFlag && (!(model.getRegion() == null || model.getRegion().equals("")));
		if (model.getRegion() == null || model.getRegion().equals("")) {
			errorDesc.add("Collateral's region is empty or null: " + model.getRegion());
		}

		completedFlag = completedFlag && (model.getManufactureYear() != null);
		if (model.getManufactureYear() == null) {
			errorDesc.add("Collateral's manufacture year is empty or null: " + model.getManufactureYear());
		}

		return (completedFlag && getValuationProfile(model) != null);
	}

	private Amount getValuationProfile(VehicleValuationModel model) {
		Map profileMap = vehicleFeedProfileSingleton.getProfile();

		if (profileMap.isEmpty() || profileMap == null) {
			logger.debug("No vehicle valuation profiles setup!");
			return null;
		}

		Map modelMap = (Map) profileMap.get(model.getMake());
		if (modelMap == null) {
			logger.debug("No profiles setup for make [" + model.getMake() + "]");
			return null;
		}

		Map yearMap = (Map) modelMap.get(model.getModel());
		if (yearMap == null) {
			logger.debug("No profiles setup for make [" + model.getMake() + "] and model [" + model.getModel() + "]");
			return null;
		}

		Map regionMap = (Map) yearMap.get(model.getManufactureYear());
		if (regionMap == null) {
			logger.debug("No profiles setup for make [" + model.getMake() + "] and model [" + model.getModel()
					+ "] and year [" + model.getManufactureYear() + "]");
			return null;
		}

		Amount fsvAmt = (Amount) regionMap.get(model.getRegion());
		if (fsvAmt == null) {
			logger.debug("No profiles setup for make [" + model.getMake() + "] and model [" + model.getModel()
					+ "] and year [" + model.getManufactureYear() + "] and region [" + model.getRegion() + "]");
		}

		return fsvAmt;
	}

	public void performValuation(IValuationModel model) throws ValuationException {
		VehicleValuationModel vModel = (VehicleValuationModel) model;
		Amount fsv = getValuationProfile(vModel);

		if (fsv != null) { // perform valuation using feeds
			double margin = vModel.getValuationMargin();
			Amount omv = new Amount((fsv.getAmount() / margin * 100), fsv.getCurrencyCode());
			omv = AmountConversion.getConversionAmount(omv, vModel.getSecCurrency());
			vModel.setValuationDate(new Date());
			vModel.setValOMV(omv);
		}
		else { // perform straight line valuation
			logger.debug("Performing straight line valuation since no matching profile ...");
			super.performValuation(model);
		}

	}

	public void saveOnlineValuationInfoInCollateral(ICollateral collateral, IValuationModel model) {
	}

	public IValuationModel createValuationModelInstance() {
		return new VehicleValuationModel();
	}

}
