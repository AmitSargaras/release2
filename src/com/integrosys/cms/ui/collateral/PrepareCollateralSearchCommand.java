package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.collateral.assetbased.assetspecgold.PrepareAssetSpecGoldCommandHelper;
import com.integrosys.cms.ui.collateral.assetbased.assetspecplant.PrepareAssetSpecPlantCommandHelper;
import com.integrosys.cms.ui.collateral.assetbased.assetspecvehicles.PrepareAssetSpecVehiclesCommandHelper;
import com.integrosys.cms.ui.collateral.assetbased.assetvessel.PrepareAssetVesselCommandHelper;
import com.integrosys.cms.ui.collateral.property.PreparePropertyCommandHelper;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.YearOfManufactureList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

public class PrepareCollateralSearchCommand extends AbstractCommand {

	/**
	 * Default Constructor
	 */
	public PrepareCollateralSearchCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "collateralId", "java.lang.String", REQUEST_SCOPE },
				{ "securityType", "java.lang.String", REQUEST_SCOPE },
				{ "securitySubType", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "freqID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "freqValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				// gold
				{ "goldTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "goldTypeLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "goldGradeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "goldGradeLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "goldUOMValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "goldUOMLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				// for plant
				{ "plantEquipID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "plantEquipValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "countryValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "countryLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "leTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "leTypeLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				// for vehicle
				{ "vehicleID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "vehicleValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				// for vessel
				{ "vesselID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "vesselValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "coverageID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "coverageValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "yearValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "yearLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "titleTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "titleTypeLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "aircraftLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "aircraftValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "countryValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "countryLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE } };

	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Inside doExecute()");

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap resultMap = new HashMap();

		result = this.getOtherCodeList(result);
		DefaultLogger.debug(this, "after getOtherCodeList");
		try {
			
			  PrepareAssetSpecGoldCommandHelper.fillPrepare(map, result, exceptionMap);
			  DefaultLogger.debug(this,
			  "after PrepareAssetSpecGoldCommandHelper fillPrepare");
			  PrepareAssetSpecPlantCommandHelper.fillPrepare(map, result, exceptionMap);
			  DefaultLogger.debug(this,
			  "after PrepareAssetSpecPlantCommandHelper fillPrepare");
			  PrepareAssetSpecVehiclesCommandHelper.fillPrepare(map, result, exceptionMap);
			  DefaultLogger.debug(this,
			  "after PrepareAssetSpecVehiclesCommandHelper fillPrepare");
			  PrepareAssetVesselCommandHelper.fillPrepare(map, result, exceptionMap);
			 			
			PreparePropertyCommandHelper.fillPrepare(map, result, exceptionMap);
			DefaultLogger.debug(this, "after fillPrepare");
			//CommonCodeList commonCode = CommonCodeList.getInstance(CategoryCodeConstant.AIRCRAFT_TYPE);
			//result.put("aircraftLabels", commonCode.getCommonCodeLabels());
			//result.put("aircraftValues", commonCode.getCommonCodeValues());
			result.put("aircraftLabels",Arrays.asList("AIR TRANSPORT", "AIR TRANSPORT", "AIR TRANSPORT", "AIR TRANSPORT", "Aircraft Type"));
			result.put("aircraftValues", Arrays.asList(101, 102, 103, 104, 105));
			CountryList countryList = CountryList.getInstance();
			result.put("countryLabel", countryList.getCountryLabels());
			result.put("countryValue", countryList.getCountryValues());

		}
		catch (Exception ex) {
			DefaultLogger.debug(this, "Exception" + ex);
		}

		resultMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		resultMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		DefaultLogger.debug(this, "Going out of doExecute()");
		return resultMap;
	}

	private HashMap getOtherCodeList(HashMap result) {

		// for Year
		YearOfManufactureList yearList = YearOfManufactureList.getInstance();
		result.put("yearValue", yearList.getYearValues());
		result.put("yearLabel", yearList.getYearLabels());

		// for le
		// List leTypeValue = new ArrayList();
		// List leTypeLabel = new ArrayList();

		//CommonCodeList commonCode = CommonCodeList.getInstance(ICMSUIConstant.COMMON_CODE_REF_LE_ID_TYPE);
		/*
		 * List leTypeValue = (List) commonCode.getCommonCodeValues();//BOST, ARBS,
		 * FINS, QUAN, SEMA List leTypeLabel = (List)
		 * commonCode.getCommonCodeLabels();//BOS-TRACS, CIF, FINSYS, MUREX, SEMA
		 */		
		List leTypeValue = Arrays.asList("BOST", "ARBS", "FINS", "QUAN", "SEMA");
		List leTypeLabel = Arrays.asList("BOS-TRACS", "CIF", "FINSYS", "MUREX", "SEMA");
		if (leTypeValue == null) {
			leTypeValue = new ArrayList();
		}
		if (leTypeLabel == null) {
			leTypeLabel = new ArrayList();
		}

		result.put("leTypeValue", leTypeValue);
		result.put("leTypeLabel", leTypeLabel);

		// for title

		//CommonCodeList commonCode = CommonCodeList.getInstance(CategoryCodeConstant.TITLE_TYPE);
		/*
		 * result.put("titleTypeValue", commonCode.getCommonCodeValues());
		 * result.put("titleTypeLabel", commonCode.getCommonCodeLabels());
		 */		
		result.put("titleTypeValue", Arrays.asList(4, 3, 1, 5, 2));
		result.put("titleTypeLabel", Arrays.asList("Alienated", "Individual", "Master", "Others", "Strata"));

		/*
		 * // for state List stateValue = new ArrayList(); List stateLabel = new
		 * ArrayList(); stateValue.add("dummy"); stateLabel.add("dummy");
		 * result.put("stateValue", stateValue); result.put("stateLabel",
		 * stateLabel);
		 * 
		 * // for district List districtValue = new ArrayList(); List
		 * districtLabel = new ArrayList(); districtValue.add("dummy");
		 * districtLabel.add("dummy"); result.put("districtValue",
		 * districtValue); result.put("districtLabel", districtLabel);
		 * 
		 * // for mukim List mukimValue = new ArrayList(); List mukimLabel = new
		 * ArrayList(); mukimValue.add("dummy"); mukimLabel.add("dummy");
		 * result.put("mukimValue", mukimValue); result.put("mukimLabel",
		 * mukimLabel);
		 */

		/*
		 * FrequencyList freqList = FrequencyList.getInstance();
		 * result.put("freqValue", freqList.getFrequencyLabel());
		 * result.put("freqID", freqList.getFrequencyProperty());
		 * 
		 * CountryList countryList = CountryList.getInstance();
		 * result.put("countryLabel", countryList.getCountryLabels());
		 * result.put("countryValue", countryList.getCountryValues());
		 * 
		 * // gold GoldTypeList goldTypeList = GoldTypeList.getInstance();
		 * result.put("goldTypeValue", goldTypeList.getGoldTypeValue());
		 * result.put("goldTypeLabel", goldTypeList.getGoldTypeLabel());
		 * 
		 * GoldGradeList goldGradeList = GoldGradeList.getInstance();
		 * result.put("goldGradeValue", goldGradeList.getGoldGradeValue());
		 * result.put("goldGradeLabel", goldGradeList.getGoldGradeLabel());
		 * 
		 * GoldUOMList goldUOMList = GoldUOMList.getInstance();
		 * result.put("goldUOMValue", goldUOMList.getGoldUOMValue());
		 * result.put("goldUOMLabel", goldUOMList.getGoldUOMLabel());
		 * 
		 * // plant NatureOfChargeList list = NatureOfChargeList.getInstance();
		 * result.put("natureOfChargeID", list.getNatureOfChargeListID());
		 * result.put("natureOfChargeValue", list.getNatureOfChargeListValue());
		 * 
		 * PlantEquipTypeList plantList = PlantEquipTypeList.getInstance();
		 * result.put("plantEquipID", plantList.getPlantEquipTypeID());
		 * result.put("plantEquipValue", plantList.getPlantEquipTypeValue());
		 * 
		 * // vehicle CoverageTypeList coverageList =
		 * CoverageTypeList.getInstance(); result.put("coverageID",
		 * coverageList.getCoverageTypeID()); result.put("coverageValue",
		 * coverageList.getCoverageTypeValue());
		 * 
		 * VehicleTypeList vehicleList = VehicleTypeList.getInstance();
		 * result.put("vehicleID", vehicleList.getVehicleTypeID());
		 * result.put("vehicleValue", vehicleList.getVehicleTypeValue());
		 * 
		 * // vessel VesselTypeList vesselList = VesselTypeList.getInstance();
		 * result.put("vesselID", vesselList.getVesselTypeID());
		 * result.put("vesselValue", vesselList.getVesselTypeValue());
		 * 
		 * //property
		 * 
		 * LandAreaUOMList landAreaUOMList = LandAreaUOMList.getInstance();
		 * result.put("LandAreaUOMID", landAreaUOMList.getLandAreaUOMID());
		 * result.put("LandAreaUOMValue",
		 * landAreaUOMList.getLandAreaUOMValue());
		 * 
		 * TenureList tenureList = TenureList.getInstance();
		 * result.put("TenureID", tenureList.getTenureListID());
		 * result.put("TenureValues", tenureList.getTenureListValue());
		 * 
		 * 
		 * SecEnvRiskyList secRiskyList = SecEnvRiskyList.getInstance();
		 * result.put("secRiskyID", secRiskyList.getSecEnvRiskyID());
		 * result.put("secRiskyValue", secRiskyList.getSecEnvRiskyValue());
		 */

		return result;

	}

}
