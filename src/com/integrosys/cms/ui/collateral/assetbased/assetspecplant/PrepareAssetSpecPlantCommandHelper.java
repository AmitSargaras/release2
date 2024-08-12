//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetspecplant;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.ISpecificChargePlant;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.collateral.assetbased.MakeModelYearPopulator;
import com.integrosys.cms.ui.common.CommonCodeList;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PrepareAssetSpecPlantCommandHelper {
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

				{ "plantEquipID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "plantEquipValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "goodStatusID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "goodStatusValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "purposeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "purposeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "equipmfID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "equipmfValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "equipriskgradingID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "equipriskgradingValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "equipcodeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "equipcodeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "yearOfManufactureOptions", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "modelOptions", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "tradeInYearOfManufactureOptions", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "tradeInModelOptions", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE } });

	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {

		String brand = (String)map.get("brand");
		String model = (String)map.get("modelNo");

		String tradeInBrand = (String)map.get("tradeInMake");
		String tradeInModel = (String)map.get("tradeInModel");
		
		String event = (String)map.get("event");
		
		if (AssetSpecPlantAction.EVENT_PREPARE_UPDATE.equals(event) ||
				AssetSpecPlantAction.EVENT_PROCESS_UPDATE.equals(event) ||
				AssetSpecPlantAction.EVENT_UPDATE_RETURN.equals(event) ||
				AssetSpecPlantAction.EVENT_DELETE_ITEM.equals(event) ||
				AssetSpecPlantAction.EVENT_DELETE_PLEDGOR.equals(event) ||
				AssetSpecPlantAction.EVENT_DELETE_PLEDGE.equals(event)) {
		

			ICollateralTrxValue colTrxValue = (ICollateralTrxValue) map.get("serviceColObj");
			if (colTrxValue != null) {
				ISpecificChargePlant col = (ISpecificChargePlant) ((colTrxValue.getStagingCollateral() == null) ? colTrxValue
						.getCollateral()
						: colTrxValue.getStagingCollateral());
				model = col.getModelNo();
				brand = col.getBrand();
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
		NatureOfChargeList list = NatureOfChargeList.getInstance();
		result.put("natureOfChargeID", list.getNatureOfChargeListID());
		result.put("natureOfChargeValue", list.getNatureOfChargeListValue());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.FREQ_TYPE);
		result.put("freqID", commonCode.getCommonCodeValues());
		result.put("freqValue", commonCode.getCommonCodeLabels());

		PlantEquipTypeList plantList = PlantEquipTypeList.getInstance();
		result.put("plantEquipID", plantList.getPlantEquipTypeID());
		result.put("plantEquipValue", plantList.getPlantEquipTypeValue());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.GOODS_STATUS);
		result.put("goodStatusID", commonCode.getCommonCodeValues());
		result.put("goodStatusValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.PLANT_EQUIP_PURPOSE);
		result.put("purposeID", commonCode.getCommonCodeValues());
		result.put("purposeValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.EQUIP_MF);
		result.put("equipmfID", commonCode.getCommonCodeValues());
		result.put("equipmfValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.EQUIP_RISK_GRADING);
		result.put("equipriskgradingID", commonCode.getCommonCodeValues());
		result.put("equipriskgradingValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.EQUIP_CODE);
		result.put("equipcodeID", commonCode.getCommonCodeValues());
		result.put("equipcodeValue", commonCode.getCommonCodeLabels());
		return;
	}

}
