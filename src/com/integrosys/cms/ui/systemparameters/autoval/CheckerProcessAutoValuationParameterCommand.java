/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.systemparameters.autoval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.OBPropertyParameters;
import com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager;
import com.integrosys.cms.app.propertyparameters.proxy.PrPaProxyManagerFactory;
import com.integrosys.cms.app.propertyparameters.trx.IPrPaTrxValue;
import com.integrosys.cms.app.propertyparameters.trx.OBPrPaTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.SecuritySubTypeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: for Checker to view & prepare to approve/reject
 * the create/update/delete request for Auto Valuation Parameters Description:
 * command that help the Checker to view & prepare to approve/reject the
 * create/update/delete request for Auto Valuation Parameters
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class CheckerProcessAutoValuationParameterCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */

	// final String SEARCH_STATE = "search_state";
	// final String SEARCH_DISTRICT = "search_district";
	// final String SEARCH_MUKIM = "search_mukim";
	public CheckerProcessAutoValuationParameterCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "paramTrxId", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "IPrPaTrxValue", "com.integrosys.cms.app.propertyparameters.trx.IPrPaTrxValue", SERVICE_SCOPE },
				{ "autoValuationParameter", "com.integrosys.cms.app.propertyparameters.bus.OBPropertyParameters",
						FORM_SCOPE }, { "collateralSubTypeFullList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "collateralSubTypeFullListLabel", "java.util.ArrayList", REQUEST_SCOPE },
				{ "stateFullList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "stateFullListLabel", "java.util.ArrayList", REQUEST_SCOPE },
				{ "districtFullList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "districtFullListLabel", "java.util.ArrayList", REQUEST_SCOPE },
				{ "mukimFullList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "mukimFullListLabel", "java.util.ArrayList", REQUEST_SCOPE },

				{ "countryListLabel", "java.util.ArrayList", REQUEST_SCOPE },
				{ "countryListValue", "java.util.ArrayList", REQUEST_SCOPE },
				{ "omvMeasureLabel", "java.util.ArrayList", REQUEST_SCOPE },
				{ "omvMeasureValue", "java.util.ArrayList", REQUEST_SCOPE },
				{ "omvIndicatorLabel", "java.util.ArrayList", REQUEST_SCOPE },
				{ "omvIndicatorValue", "java.util.ArrayList", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			String paramTrxId = (String) map.get("paramTrxId");
			DefaultLogger.debug(this, "paramTrxId : " + paramTrxId);

			OBPropertyParameters obParam = new OBPropertyParameters();

			IPrPaProxyManager proxy = PrPaProxyManagerFactory.getProxyManager();
			IPrPaTrxValue trxValue = (OBPrPaTrxValue) proxy.getCCDocumentLocationByTrxID(paramTrxId);
			DefaultLogger.debug(this, "trxValue after read staging " + trxValue);

			obParam = (OBPropertyParameters) trxValue.getStagingPrPa();

			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			// CommonCodeEntriesAccessUtil entriesAccessUtil =
			// CommonCodeEntriesAccessUtil.getInstance();

			ArrayList collateralSubTypeFullList = new ArrayList();
			ArrayList collateralSubTypeFullListLabel = new ArrayList();
			ArrayList stateFullList = new ArrayList();
			ArrayList stateFullListLabel = new ArrayList();
			ArrayList districtFullList = new ArrayList();
			ArrayList districtFullListLabel = new ArrayList();
			ArrayList mukimFullList = new ArrayList();
			ArrayList mukimFullListLabel = new ArrayList();

			// get the countries this user is allowed
			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);

			ArrayList countryListLabel = new ArrayList();
			ArrayList countryListValue = new ArrayList();
			getCountry(team, countryListLabel, countryListValue);

			resultMap.put("countryListLabel", countryListLabel);
			resultMap.put("countryListValue", countryListValue);

			// measurement list
			ArrayList omvMeasureLabel = new ArrayList();
			ArrayList omvMeasureValue = new ArrayList();
			HashMap omvMeasure = CommonDataSingleton.getCodeCategoryLabelValueMap(CategoryCodeConstant.AREA_UOM);
			setValueToList(omvMeasure, omvMeasureValue, omvMeasureLabel);

			resultMap.put("omvMeasureLabel", omvMeasureLabel);
			resultMap.put("omvMeasureValue", omvMeasureValue);

			// OMVIndicator list
			ArrayList omvIndicatorLabel = new ArrayList();
			ArrayList omvIndicatorValue = new ArrayList();
			HashMap omvIndicator = CommonDataSingleton.getCodeCategoryLabelValueMap(CategoryCodeConstant.OMV_TYPE);
			setValueToList(omvIndicator, omvIndicatorValue, omvIndicatorLabel);

			resultMap.put("omvIndicatorLabel", omvIndicatorLabel);
			resultMap.put("omvIndicatorValue", omvIndicatorValue);

			SecuritySubTypeList subTypeList = SecuritySubTypeList.getInstance();
			Collection fullList = subTypeList.getSecuritySubTypeProperty();
			setSecuritySubTypeToList(fullList, collateralSubTypeFullList, collateralSubTypeFullListLabel, subTypeList,
					locale);

			if ((obParam.getCountryCode() != null) && !("").equals(obParam.getCountryCode())) {
				Map state = CommonDataSingleton.getCodeCategoryLabelValueMap(CategoryCodeConstant.STATE_CATEGORY_CODE,
						null, obParam.getCountryCode(), null);
				setValueToList(state, stateFullList, stateFullListLabel);
			}

			if ((obParam.getStateList() != null) && (obParam.getStateList().size() > 0)) {
				HashMap district = new HashMap();
				HashMap mukim = new HashMap();
				for (int i = 0; i < obParam.getStateList().size(); i++) {
					district.putAll(CommonDataSingleton.getCodeCategoryLabelValueMap(
							CategoryCodeConstant.DISTRICT_CATEGORY_CODE, null, obParam.getCountryCode(),
							(String) obParam.getStateList().get(i)));

					mukim.putAll(CommonDataSingleton.getCodeCategoryLabelValueMap(
							CategoryCodeConstant.MUKIM_CATEGORY_CODE, null, obParam.getCountryCode(), (String) obParam
									.getStateList().get(i)));
				}
				setValueToList(district, districtFullList, districtFullListLabel);
				setValueToList(mukim, mukimFullList, mukimFullListLabel);
			}

			DefaultLogger.debug(this, "collateralSubTypeFullList : " + collateralSubTypeFullList);
			DefaultLogger.debug(this, "collateralSubTypeFullListLabel : " + collateralSubTypeFullListLabel);
			DefaultLogger.debug(this, "stateFullList : " + stateFullList);
			DefaultLogger.debug(this, "stateFullListLabel : " + stateFullListLabel);
			DefaultLogger.debug(this, "districtFullList : " + districtFullList);
			DefaultLogger.debug(this, "districtFullListLabel : " + districtFullListLabel);
			DefaultLogger.debug(this, "mukimFullList : " + mukimFullList);
			DefaultLogger.debug(this, "mukimFullListLabel : " + mukimFullListLabel);

			resultMap.put("collateralSubTypeFullList", collateralSubTypeFullList);
			resultMap.put("collateralSubTypeFullListLabel", collateralSubTypeFullListLabel);
			resultMap.put("stateFullList", stateFullList);
			resultMap.put("stateFullListLabel", stateFullListLabel);
			resultMap.put("districtFullList", districtFullList);
			resultMap.put("districtFullListLabel", districtFullListLabel);
			resultMap.put("mukimFullList", mukimFullList);
			resultMap.put("mukimFullListLabel", mukimFullListLabel);

			resultMap.put("autoValuationParameter", obParam);

			resultMap.put("IPrPaTrxValue", trxValue);

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private void setSecuritySubTypeToList(Collection fullList, ArrayList listValue, ArrayList listLabel,
			SecuritySubTypeList subTypeList, Locale locale) {
		// filter out sub type for property type only
		Iterator iter = fullList.iterator();
		HashMap subTypeLabelValMap = new HashMap();
		while (iter.hasNext()) {
			String subType = (String) iter.next();
			if ((subType != null) && (ICMSConstant.SECURITY_TYPE_PROPERTY).equalsIgnoreCase(subType.substring(0, 2))) {
				subTypeLabelValMap.put(subTypeList.getSecuritySubTypeValue(subType, locale), subType);
			}
		}

		// sort the list by label
		String[] subTypeLabel = (String[]) subTypeLabelValMap.keySet().toArray(new String[0]);
		Arrays.sort(subTypeLabel);

		listLabel.addAll(new ArrayList(Arrays.asList(subTypeLabel)));
		for (int i = 0; i < subTypeLabel.length; i++) {
			listValue.add(subTypeLabelValMap.get(subTypeLabel[i]));
		}
	}

	private void setValueToList(Map map, ArrayList listValue, ArrayList listLabel) {
		if (map != null) {
			String[] keyLabel = (String[]) map.keySet().toArray(new String[0]);
			Arrays.sort(keyLabel);

			listLabel.addAll(new ArrayList(Arrays.asList(keyLabel)));
			for (int i = 0; i < keyLabel.length; i++) {
				listValue.add(map.get(keyLabel[i]));
			}
		}
	}

	private void getCountry(ITeam team, ArrayList countryListLabel, ArrayList countryListValue)
			throws CommandProcessingException {
		CountryList countries = CountryList.getInstance();
		// if(team != null)
		// {
		// HashMap countryLabelValMap = new HashMap();
		// String countriesCodeAccessed[] = team.getCountryCodes();
		//
		// for (int i=0; i<countriesCodeAccessed.length; i++)
		// {
		//countryLabelValMap.put(countries.getCountryName(countriesCodeAccessed[
		// i]), countriesCodeAccessed[i]);
		// }
		//
		// String [] countriesLabelAccessed =
		// (String[])countryLabelValMap.keySet().toArray(new String[0]);
		// Arrays.sort(countriesLabelAccessed);
		//
		// countryListLabel.addAll(new
		// ArrayList(Arrays.asList(countriesLabelAccessed)));
		// for (int i=0; i<countriesLabelAccessed.length; i++)
		// {
		//countryListValue.add(countryLabelValMap.get(countriesLabelAccessed[i])
		// );
		// }
		// }
		countryListLabel.addAll(countries.getCountryLabels());
		countryListValue.addAll(countries.getCountryValues());

		if ((countryListLabel.size() == 0) || (countryListValue.size() == 0)) {
			throw new CommandProcessingException("Failed to retrieve country names and labels");
		}
	}

}
