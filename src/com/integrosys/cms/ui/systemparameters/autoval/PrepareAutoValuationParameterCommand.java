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
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.SecuritySubTypeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: for Maker to create new Auto Valuation
 * Parameters Description: command that help the Maker to create new Auto
 * Valuation Parameters
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class PrepareAutoValuationParameterCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PrepareAutoValuationParameterCommand() {
	}

	final String SEARCH_STATE = "search_state";

	final String SEARCH_DISTRICT = "search_district";

	final String SEARCH_MUKIM = "search_mukim";

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "autoValuationParameter", "com.integrosys.cms.app.propertyparameters.bus.OBPropertyParameters",
						FORM_SCOPE },
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
				{ "autoValuationParameter", "com.integrosys.cms.app.propertyparameters.bus.OBPropertyParameters",
						FORM_SCOPE }, { "countryListLabel", "java.util.ArrayList", REQUEST_SCOPE },
				{ "countryListValue", "java.util.ArrayList", REQUEST_SCOPE },
				{ "omvMeasureLabel", "java.util.ArrayList", REQUEST_SCOPE },
				{ "omvMeasureValue", "java.util.ArrayList", REQUEST_SCOPE },
				{ "omvIndicatorLabel", "java.util.ArrayList", REQUEST_SCOPE },
				{ "omvIndicatorValue", "java.util.ArrayList", REQUEST_SCOPE },
				{ "collateralSubTypeFullList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "collateralSubTypeFullListLabel", "java.util.ArrayList", REQUEST_SCOPE },
				{ "stateFullList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "stateFullListLabel", "java.util.ArrayList", REQUEST_SCOPE },
				{ "districtFullList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "districtFullListLabel", "java.util.ArrayList", REQUEST_SCOPE },
				{ "mukimFullList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "mukimFullListLabel", "java.util.ArrayList", REQUEST_SCOPE } });
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
			OBPropertyParameters obParam = new OBPropertyParameters();
			if (map.containsKey("autoValuationParameter")) {
				obParam = (OBPropertyParameters) map.get("autoValuationParameter");
			}

			DefaultLogger.debug(this, "obParam is null ? " + (obParam == null));
			DefaultLogger.debug(this, "obParam.getCountryCode() :: " + obParam.getCountryCode());
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
			ArrayList countryListLabel = new ArrayList();
			ArrayList countryListValue = new ArrayList();

			// get the countries this user is allowed
			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			getCountry(team, countryListLabel, countryListValue);

			resultMap.put("countryListLabel", countryListLabel);
			resultMap.put("countryListValue", countryListValue);

			// Measurement list
			ArrayList omvMeasureLabel = new ArrayList();
			ArrayList omvMeasureValue = new ArrayList();

			HashMap omvMeasure = CommonDataSingleton.getCodeCategoryLabelValueMap(CategoryCodeConstant.AREA_UOM);
			setValueToList(omvMeasure, omvMeasureValue, omvMeasureLabel);

			resultMap.put("omvMeasureLabel", omvMeasureLabel);
			resultMap.put("omvMeasureValue", omvMeasureValue);

			// OMVIndicator list
			ArrayList omvIndicatorLabel = new ArrayList();
			// omvIndicatorLabel.add("Reduce");
			// omvIndicatorLabel.add("Increase");
			ArrayList omvIndicatorValue = new ArrayList();
			// omvIndicatorValue.add("R");
			// omvIndicatorValue.add("I");

			HashMap omvIndicator = CommonDataSingleton.getCodeCategoryLabelValueMap(CategoryCodeConstant.OMV_TYPE);
			setValueToList(omvIndicator, omvIndicatorValue, omvIndicatorLabel);

			resultMap.put("omvIndicatorLabel", omvIndicatorLabel);
			resultMap.put("omvIndicatorValue", omvIndicatorValue);

			// get list for security sub type
			SecuritySubTypeList subTypeList = SecuritySubTypeList.getInstance();
			Collection fullList = subTypeList.getSecuritySubTypeProperty();
			setSecuritySubTypeToList(fullList, collateralSubTypeFullList, collateralSubTypeFullListLabel, subTypeList,
					locale);

			if ((obParam.getCountryCode() != null) && !("").equals(obParam.getCountryCode())) {
				// Map state = entriesAccessUtil.getValuesAndLabelsForCodes (
				// CommonCodeEntriesAccessUtil.STATE_TYPE , new String[] {
				// obParam.getCountryCode() } ) ;
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

			// if (obParam.getStateList()!=null &&
			// obParam.getStateList().size()>0)
			// {
			// Map district = entriesAccessUtil.getValuesAndLabelsForCodes (
			// CommonCodeEntriesAccessUtil.DISTRICT_TYPE , (String[])
			// obParam.getStateList().toArray(new
			// String[obParam.getStateList().size()]) ) ;
			// setValueToList(district, districtFullList,
			// districtFullListLabel);
			// }
			//
			// if (obParam.getDistrictList()!=null &&
			// obParam.getDistrictList().size()>0)
			// {
			// Map mukim = entriesAccessUtil.getValuesAndLabelsForCodes (
			// CommonCodeEntriesAccessUtil.MUKIM_TYPE , (String[])
			// obParam.getDistrictList().toArray(new
			// String[obParam.getDistrictList().size()]) ) ;
			// setValueToList(mukim, mukimFullList, mukimFullListLabel);
			// }

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

	// private OBTestPropertyParameters clearDistrictList
	// (OBTestPropertyParameters obTestPropertyParameters)
	// {
	// obTestPropertyParameters.setDistrictList(new ArrayList());
	// obTestPropertyParameters.setDistrictListLabel(new ArrayList());
	// obTestPropertyParameters.setUnSelectDistrictList(new ArrayList());
	// obTestPropertyParameters.setUnSelectDistrictListLabel(new ArrayList());
	// return obTestPropertyParameters;
	// }
	//
	// private OBTestPropertyParameters clearMukimList (OBTestPropertyParameters
	// obTestPropertyParameters)
	// {
	// obTestPropertyParameters.setMukimList(new ArrayList());
	// obTestPropertyParameters.setMukimListLabel(new ArrayList());
	// obTestPropertyParameters.setUnSelectMukimList(new ArrayList());
	// obTestPropertyParameters.setUnSelectMukimListLabel(new ArrayList());
	// return obTestPropertyParameters;
	// }
	//
	//
	// private String[][] getItemList (String searchType, String[] searchValue)
	// {
	// String[][] itemList = new String[0][0];
	// if ((SEARCH_STATE).equals(searchType))
	// {
	// itemList = new String[][] {{"Selangor","SLG"},{"Kuala Lumpur","KL"}};
	// }
	// else if ((SEARCH_DISTRICT).equals(searchType))
	// {
	// itemList = new String[][] {{"District 1","DS1"},{"District 2","DS2"}};
	// }
	// else if ((SEARCH_MUKIM).equals(searchType))
	// {
	// itemList = new String[][] {{"Mukim 1","MK1"},{"Mukim 2"," MK2"}};
	// }
	//
	// return itemList;
	// }
	//
	// private OBTestPropertyParameters setValueToList (OBTestPropertyParameters
	// obParam, String[][] itemList, String searchType)
	// {
	// ArrayList listLabel = new ArrayList();
	// ArrayList listValue = new ArrayList();
	//
	// int i = 0, j =0;
	// for (i=0; i < itemList.length; i++)
	// {
	// for (j=0; j < 2; j++)
	// {
	// if (j == 0)
	// {
	// listLabel.add(itemList[i][j]);
	// }
	// else if (j == 1)
	// {
	// listValue.add(itemList[i][j]);
	// }
	// }
	// }
	//
	// if ((SEARCH_STATE).equals(searchType))
	// {
	// obParam.setUnSelectStateList(listValue);
	// obParam.setUnSelectStateListLabel(listLabel);
	// obParam.setStateList(new ArrayList());
	// obParam.setStateListLabel(new ArrayList());
	// }
	// else if ((SEARCH_DISTRICT).equals(searchType))
	// {
	// obParam.setUnSelectDistrictList(listValue);
	// obParam.setUnSelectDistrictListLabel(listLabel);
	// obParam.setDistrictList(new ArrayList());
	// obParam.setDistrictListLabel(new ArrayList());
	// }
	// else if ((SEARCH_MUKIM).equals(searchType))
	// {
	// obParam.setUnSelectMukimList(listValue);
	// obParam.setUnSelectMukimListLabel(listLabel);
	// obParam.setMukimList(new ArrayList());
	// obParam.setMukimListLabel(new ArrayList());
	// }
	//
	// return obParam;
	// }

	// private void setValueToList (String[][] itemList, ArrayList listValue,
	// ArrayList listLabel)
	// {
	// int i = 0, j =0;
	// for (i=0; i < itemList.length; i++) {
	// for (j=0; j < 2; j++) {
	// if (j == 0) {
	// listValue.add(itemList[i][j]);
	// }
	// else if (j == 1) {
	// listLabel.add(itemList[i][j]);
	// }
	// }
	// }
	// }

	private void setSecuritySubTypeToList(Collection fullList, ArrayList listValue, ArrayList listLabel,
			SecuritySubTypeList subTypeList, Locale locale) {
		// filter out sub type for property type only
		Iterator iter = fullList.iterator();
		HashMap subTypeLabelValMap = new HashMap();
		while (iter.hasNext()) {
			String subType = (String) iter.next();
			if ((subType != null) && (ICMSConstant.SECURITY_TYPE_PROPERTY).equalsIgnoreCase(subType.substring(0, 2))) {
				// listValue.add(subType);
				// listLabel.add(subTypeList.getSecuritySubTypeValue(subType,
				// locale));
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

	// sort the list by label
	private void setValueToList(Map map, ArrayList listValue, ArrayList listLabel) {
		// Iterator keys = map.keySet ().iterator() ;
		// while ( keys.hasNext () )
		// {
		// String keyLabel = (String)keys.next () ;
		//
		// // listValue.add(key);
		// listLabel.add(map.get(key));
		// }
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
