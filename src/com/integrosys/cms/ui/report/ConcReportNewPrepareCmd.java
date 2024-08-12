/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/report/ConcReportNewPrepareCmd.java,v 1.6 2003/10/03 09:56:15 btchng Exp $
 */
package com.integrosys.cms.ui.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.stock.IStockExchange;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.SecurityTypeList;
import com.integrosys.cms.ui.common.StockExchangeList;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Prepares the input form for entering input to generate concentration report.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.6 $
 * @since $Date: 2003/10/03 09:56:15 $ Tag: $Name: $
 */
public class ConcReportNewPrepareCmd extends AbstractCommand {

	private static final String REGION_CODE_CATEGORY = "36";

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale",
				GLOBAL_SCOPE } };
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { "stockExCodeLabels", "java.util.List", REQUEST_SCOPE },
				{ "stockExCodeValues", "java.util.List", REQUEST_SCOPE },
				{ "stockSharesNumSecSubtypeLabels", "java.util.List", REQUEST_SCOPE },
				{ "stockSharesNumSecSubtypeValues", "java.util.List", REQUEST_SCOPE },
				{ "stockSharesDenSecTypeLabels", "java.util.List", REQUEST_SCOPE },
				{ "stockSharesDenSecTypeValues", "java.util.List", REQUEST_SCOPE },
				{ "secStockExNumSecSubtypeLabels", "java.util.List", REQUEST_SCOPE },
				{ "secStockExNumSecSubtypeValues", "java.util.List", REQUEST_SCOPE },
				{ "secStockExDenSecTypeLabels", "java.util.List", REQUEST_SCOPE },
				{ "secStockExDenSecTypeValues", "java.util.List", REQUEST_SCOPE },
				{ "secStockExDenSecSubtypeLabels", "java.util.List", REQUEST_SCOPE },
				{ "secStockExDenSecSubtypeValues", "java.util.List", REQUEST_SCOPE },
				{ "secSecTypeRegionLabels", "java.util.List", REQUEST_SCOPE },
				{ "secSecTypeRegionValues", "java.util.List", REQUEST_SCOPE },
				{ "secSecTypeListingLabels", "java.util.List", REQUEST_SCOPE },
				{ "secSecTypeListingValues", "java.util.List", REQUEST_SCOPE },
				{ "propertyListingLabels", "java.util.List", REQUEST_SCOPE },
				{ "propertyListingValues", "java.util.List", REQUEST_SCOPE },
				{ "propertyCountryCodeLabels", "java.util.List", REQUEST_SCOPE },
				{ "propertyCountryCodeValues", "java.util.List", REQUEST_SCOPE },
				{ "currencySecTypeListingLabels", "java.util.List", REQUEST_SCOPE },
				{ "currencySecTypeListingValues", "java.util.List", REQUEST_SCOPE } };
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			// Preparation.
			CommonDataSingleton commonData = CommonDataSingleton.getInstance();
			CountryList countryList = CountryList.getInstance();
			SecurityTypeList securityTypeList = SecurityTypeList.getInstance();
			// RegionList regionList = RegionList.getInstance();

			String[] secTypeLabelsArr = new String[3];
			String[] secTypeValuesArr = new String[3];
			String[] secSubtypeLabelsArr = new String[6];
			String[] secSubtypeValuesArr = new String[6];
			String[] secSubtype2LabelsArr = new String[4];
			String[] secSubtype2ValuesArr = new String[4];
			String[] propertyListingLabelsArr = new String[4];
			String[] propertyListingValuesArr = new String[4];

			secTypeLabelsArr[0] = "Please Select";
			secTypeLabelsArr[1] = "All";
			secTypeLabelsArr[2] = "Marketable Securities";
			secTypeValuesArr[0] = "";
			secTypeValuesArr[1] = ConcReportNewConstants.SEC_TYPE_ALL;
			secTypeValuesArr[2] = ConcReportNewConstants.SEC_TYPE_MARKETABLE;

			secSubtypeLabelsArr[0] = "Please Select";
			secSubtypeLabelsArr[1] = "All";
			secSubtypeLabelsArr[2] = "Main Index - Local";
			secSubtypeLabelsArr[3] = "Main Index - Foreign";
			secSubtypeLabelsArr[4] = "Other Listed - Local";
			secSubtypeLabelsArr[5] = "Other Listed - Foreign";
			secSubtypeValuesArr[0] = "";
			secSubtypeValuesArr[1] = ConcReportNewConstants.SEC_SUBTYPE_ALL;
			secSubtypeValuesArr[2] = ConcReportNewConstants.SEC_SUBTYPE_MAIN_LOCAL;
			secSubtypeValuesArr[3] = ConcReportNewConstants.SEC_SUBTYPE_MAIN_FOREIGN;
			secSubtypeValuesArr[4] = ConcReportNewConstants.SEC_SUBTYPE_OTHER_LOCAL;
			secSubtypeValuesArr[5] = ConcReportNewConstants.SEC_SUBTYPE_OTHER_FOREIGN;

			secSubtype2LabelsArr[0] = "Please Select";
			secSubtype2LabelsArr[1] = "All";
			secSubtype2LabelsArr[2] = "Main Index";
			secSubtype2LabelsArr[3] = "Other Listed";
			secSubtype2ValuesArr[0] = "";
			secSubtype2ValuesArr[1] = ConcReportNewConstants.SEC_SUBTYPE_ALL;
			secSubtype2ValuesArr[2] = ConcReportNewConstants.SEC_SUBTYPE_MAIN;
			secSubtype2ValuesArr[3] = ConcReportNewConstants.SEC_SUBTYPE_OTHER;

			propertyListingLabelsArr[0] = "Please Select";
			propertyListingLabelsArr[1] = "State/District";
			propertyListingLabelsArr[2] = "Post Code";
			propertyListingLabelsArr[3] = "Location";
			propertyListingValuesArr[0] = "";
			propertyListingValuesArr[1] = ConcReportNewConstants.STATE;
			propertyListingValuesArr[2] = ConcReportNewConstants.POSTCODE;
			propertyListingValuesArr[3] = ConcReportNewConstants.LOCATION;

			// Set up the lists.
			StockExchangeList stockExchangeList = StockExchangeList.getInstance();
			IStockExchange[] stockExchangesArr = stockExchangeList.getStockExchanges();
			Arrays.sort(stockExchangesArr, new Comparator() {
				public int compare(Object o1, Object o2) {
					IStockExchange se1 = (IStockExchange) o1;
					IStockExchange se2 = (IStockExchange) o2;
					return se1.getStockExchangeName().compareTo(se2.getStockExchangeName());
				}
			});

			List stockExCodeLabels = new ArrayList();
			List stockExCodeValues = new ArrayList();
			for (int i = 0; i < stockExchangesArr.length; i++) {
				stockExCodeLabels.add(stockExchangesArr[i].getStockExchangeName());
				stockExCodeValues.add(stockExchangesArr[i].getStockExchangeCode());
			}

			// List stockExCodeLabels = new
			// ArrayList(commonData.getCodeCategoryLabels(
			// ConcReportNewConstants.CODE_CATEGORY_STOCK_EXCHANGE));
			stockExCodeLabels.add(0, "Please Select");
			// List stockExCodeValues = new
			// ArrayList(commonData.getCodeCategoryValues(
			// ConcReportNewConstants.CODE_CATEGORY_STOCK_EXCHANGE));
			stockExCodeValues.add(0, "");

			List stockSharesNumSecSubtypeLabels = Arrays.asList(secSubtypeLabelsArr);
			List stockSharesNumSecSubtypeValues = Arrays.asList(secSubtypeValuesArr);

			List stockSharesDenSecTypeLabels = Arrays.asList(secTypeLabelsArr);
			List stockSharesDenSecTypeValues = Arrays.asList(secTypeValuesArr);

			List secStockExNumSecSubtypeLabels = Arrays.asList(secSubtypeLabelsArr);
			List secStockExNumSecSubtypeValues = Arrays.asList(secSubtypeValuesArr);

			List secStockExDenSecTypeLabels = Arrays.asList(secTypeLabelsArr);
			List secStockExDenSecTypeValues = Arrays.asList(secTypeValuesArr);

			List secStockExDenSecSubtypeLabels = Arrays.asList(secSubtype2LabelsArr);
			List secStockExDenSecSubtypeValues = Arrays.asList(secSubtype2ValuesArr);

			// List secSecTypeRegionLabels = new ArrayList(
			// regionList.getRegionLabels());
			List secSecTypeRegionLabels = new ArrayList(commonData.getCodeCategoryLabels(REGION_CODE_CATEGORY));
			secSecTypeRegionLabels.add(0, "Please Select");
			// List secSecTypeRegionValues = new ArrayList(
			// regionList.getRegionValues());
			List secSecTypeRegionValues = new ArrayList(commonData.getCodeCategoryValues(REGION_CODE_CATEGORY));
			secSecTypeRegionValues.add(0, "");

			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			List secSecTypeListingLabels = new ArrayList(securityTypeList.getSecurityTypeLabel(locale));
			secSecTypeListingLabels.add(0, "Please Select");
			List secSecTypeListingValues = new ArrayList(securityTypeList.getSecurityTypeProperty());
			secSecTypeListingValues.add(0, "");

			List propertyListingLabels = Arrays.asList(propertyListingLabelsArr);
			List propertyListingValues = Arrays.asList(propertyListingValuesArr);

			List propertyCountryCodeLabels = new ArrayList(countryList.getCountryLabels());
			propertyCountryCodeLabels.add(0, "Please Select");
			List propertyCountryCodeValues = new ArrayList(countryList.getCountryValues());
			propertyCountryCodeValues.add(0, "");

			List currencySecTypeListingLabels = new ArrayList(securityTypeList.getSecurityTypeLabel(locale));
			currencySecTypeListingLabels.add(0, "Please Select");
			List currencySecTypeListingValues = new ArrayList(securityTypeList.getSecurityTypeProperty());
			currencySecTypeListingValues.add(0, "");

			// Put the lists into the result map.
			resultMap.put("stockExCodeLabels", stockExCodeLabels);
			resultMap.put("stockExCodeValues", stockExCodeValues);
			resultMap.put("stockSharesNumSecSubtypeLabels", stockSharesNumSecSubtypeLabels);
			resultMap.put("stockSharesNumSecSubtypeValues", stockSharesNumSecSubtypeValues);
			resultMap.put("stockSharesDenSecTypeLabels", stockSharesDenSecTypeLabels);
			resultMap.put("stockSharesDenSecTypeValues", stockSharesDenSecTypeValues);
			resultMap.put("secStockExNumSecSubtypeLabels", secStockExNumSecSubtypeLabels);
			resultMap.put("secStockExNumSecSubtypeValues", secStockExNumSecSubtypeValues);
			resultMap.put("secStockExDenSecTypeLabels", secStockExDenSecTypeLabels);
			resultMap.put("secStockExDenSecTypeValues", secStockExDenSecTypeValues);
			resultMap.put("secStockExDenSecSubtypeLabels", secStockExDenSecSubtypeLabels);
			resultMap.put("secStockExDenSecSubtypeValues", secStockExDenSecSubtypeValues);
			resultMap.put("secSecTypeRegionLabels", secSecTypeRegionLabels);
			resultMap.put("secSecTypeRegionValues", secSecTypeRegionValues);
			resultMap.put("secSecTypeListingLabels", secSecTypeListingLabels);
			resultMap.put("secSecTypeListingValues", secSecTypeListingValues);
			resultMap.put("propertyListingLabels", propertyListingLabels);
			resultMap.put("propertyListingValues", propertyListingValues);
			resultMap.put("propertyCountryCodeLabels", propertyCountryCodeLabels);
			resultMap.put("propertyCountryCodeValues", propertyCountryCodeValues);
			resultMap.put("currencySecTypeListingLabels", currencySecTypeListingLabels);
			resultMap.put("currencySecTypeListingValues", currencySecTypeListingValues);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

}
