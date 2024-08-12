package com.integrosys.cms.ui.collateral.assetbased.assetspecvehicles;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargevehicle.ISpecificChargeVehicle;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.collateral.assetbased.MakeModelYearPopulator;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.base.techinfra.logger.DefaultLogger;

public class PrepareAssetSpecVehiclesCommandHelper {

	public static String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "brand", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "modelNo", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "tradeInMake", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE},
				{ "tradeInModel", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE}, 
				{ "event", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE},
		});
	}
		
	public static String[][] getResultDescriptor() {
		return (new String[][] { { "freqID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "freqValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "assetTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "assetTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "brandID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "brandValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "coverageID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "coverageValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "dealerNameID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "dealerNameValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "vehicleID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "vehicleValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "goodStatusID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "goodStatusValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "pbtIndicatorID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "pbtIndicatorValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "energySourceID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "energySourceValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "transTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "transTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "brandID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "brandValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "yearOfManufactureOptions", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "modelOptions", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "tradeInYearOfManufactureOptions", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "tradeInModelOptions", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "branchList", "java.util.List", ICommonEventConstant.SERVICE_SCOPE },

		});
	}
	
	

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {

		String brand = (String)map.get("brand");
		String model = (String)map.get("modelNo");

		String tradeInBrand = (String)map.get("tradeInMake");
		String tradeInModel = (String)map.get("tradeInModel");
		
		String event = (String)map.get("event");
		//DefaultLogger.debug("prepareAssetSpecVehicleCommandHelper", "<<<<<<<<<<<< event: "+event);
		
		if (AssetSpecVehiclesAction.EVENT_PREPARE_UPDATE.equals(event) ||
				AssetSpecVehiclesAction.EVENT_PROCESS_UPDATE.equals(event) ||
				AssetSpecVehiclesAction.EVENT_UPDATE_RETURN.equals(event) ||
				AssetSpecVehiclesAction.EVENT_DELETE_ITEM.equals(event) ||
				AssetSpecVehiclesAction.EVENT_DELETE_PLEDGOR.equals(event) ||
				AssetSpecVehiclesAction.EVENT_DELETE_PLEDGE.equals(event)) {
			
			ICollateralTrxValue colTrxValue = (ICollateralTrxValue) map.get("serviceColObj");
			if (colTrxValue != null) {
				ISpecificChargeVehicle col = (ISpecificChargeVehicle) ((colTrxValue.getStagingCollateral() == null) ? colTrxValue
						.getCollateral()
						: colTrxValue.getStagingCollateral());
						
				//DefaultLogger.debug("prepareAssetSpecVehicleCommandHelper", "<<<<< colTrxValue.getStagingCollateral() is null: "+(colTrxValue.getStagingCollateral()==null));
				
				model = col.getModelNo();
				brand = col.getBrand();
				
				//DefaultLogger.debug("prepareAssetSpecVehicleCommandHelper", "<<<<<<<<<<< brand: "+col.getBrand()+"\tmodel: "+col.getModelNo());
				if (col.getTradeInInfo() != null && col.getTradeInInfo().length != 0) {
					tradeInBrand = col.getTradeInInfo()[0].getMake();
					tradeInModel = col.getTradeInInfo()[0].getModel();
				}
			}
		}

		CommonCodeList commonCode;

		Collection modelLabelValueBeanList = MakeModelYearPopulator.retrieveModelLabelValueBeanCollections(brand);
		result.put("modelOptions", modelLabelValueBeanList);

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.MANUFACTURE_YEAR);
		result.put("yearOfManufactureOptions", commonCode.getOptionList());
		result.put("tradeInYearOfManufactureOptions", commonCode.getOptionList());
		/*
		Collection yearOfManufactureLabelValueBeanList = MakeModelYearPopulator
				.retrieveYearOfManufactureLabelValueBeanCollections(brand + "|" + model);
		result.put("yearOfManufactureOptions", yearOfManufactureLabelValueBeanList);
		*/
		Collection tradeInModelLabelValueBeanList = MakeModelYearPopulator
				.retrieveModelLabelValueBeanCollections(tradeInBrand);
		result.put("tradeInModelOptions", tradeInModelLabelValueBeanList);
		/*
		Collection tradeInYearOfManufactureLabelValueBeanList = MakeModelYearPopulator
				.retrieveYearOfManufactureLabelValueBeanCollections(tradeInBrand + "|" + tradeInModel);
		result.put("tradeInYearOfManufactureOptions", tradeInYearOfManufactureLabelValueBeanList);
		*/
		// commonCode =
		// CommonCodeList.getInstance(CategoryCodeConstant.ASSET_MODEL_TYPE);
		// result.put("modelNoID", commonCode.getCommonCodeValues());
		// result.put("modelNoValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.FREQ_TYPE);
		result.put("freqID", commonCode.getCommonCodeValues());
		result.put("freqValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.COVERAGE_TYPE);
		result.put("coverageID", commonCode.getCommonCodeValues());
		result.put("coverageValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.VEHICLE_TYPE);
		result.put("vehicleID", commonCode.getCommonCodeValues());
		result.put("vehicleValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.GOODS_STATUS);
		result.put("goodStatusID", commonCode.getCommonCodeValues());
		result.put("goodStatusValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.TRANSMISSION_TYPE);
		result.put("transTypeID", commonCode.getCommonCodeValues());
		result.put("transTypeValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.VEHICLE_BRAND);
		result.put("brandID", commonCode.getCommonCodeValues());
		result.put("brandValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.PBR_PBT_INDICATOR);
		result.put("pbtIndicatorID", commonCode.getCommonCodeValues());
		result.put("pbtIndicatorValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.VEHICLE_TYPE);
		result.put("assetTypeID", commonCode.getCommonCodeValues());
		result.put("assetTypeValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.VEHICLE_DEALER);
		result.put("dealerNameID", commonCode.getCommonCodeValues());
		result.put("dealerNameValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.ENERGY_SOURCE);
		result.put("energySourceID", commonCode.getCommonCodeValues());
		result.put("energySourceValue", commonCode.getCommonCodeLabels());

		return;
	}
}
