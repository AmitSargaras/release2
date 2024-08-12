/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetpostdatedchqs/PrepareChequeCommand.java,v 1.7 2004/06/04 05:19:56 hltan Exp $
 */

package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.SELECTED_STOCK_LOCATIONS;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_DETAILS;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_TOTAL_INSURANCE_POLICY_AMT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.springframework.util.CollectionUtils;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ISecurityCoverage;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.app.common.util.MapperUtil;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.collateral.CollateralHelper;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/06/04 05:19:56 $ Tag: $Name: $
 */

public class PrepareStockDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
			
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",SERVICE_SCOPE },
				{ "from_page", "java.lang.String", REQUEST_SCOPE },
				{ "dueDate", "java.lang.String", REQUEST_SCOPE },
				{ "stockdocMonth", "java.lang.String", REQUEST_SCOPE },
				{ "stockdocYear", "java.lang.String", REQUEST_SCOPE },
				{ "dpShare", "java.lang.String", REQUEST_SCOPE },
				{ "remarkByMaker", "java.lang.String", REQUEST_SCOPE },
				{"stockLocation","java.lang.String", REQUEST_SCOPE},
				{ "migrationFlag", "java.lang.String", REQUEST_SCOPE },
				{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
				{ "dpCalculateManually", "java.lang.String", REQUEST_SCOPE },
				{ "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
							
				
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
				{ "assetsMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValue", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "assetsMarginTypeNew", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValueNew", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "liabilityMarginTypeAdv", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValueAdv", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE},
				{ "event", "java.lang.String", SERVICE_SCOPE},
				
			
				{ "liabilityMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValue", "java.lang.String", REQUEST_SCOPE },
				{ "insuranceList",  "java.util.List", SERVICE_SCOPE },
				{ "event", String.class.getName() , REQUEST_SCOPE },
				});

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
				{ "locationList", "java.util.List", ICommonEventConstant.REQUEST_SCOPE },
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), FORM_SCOPE },
				{ "dueDate", "java.lang.String", SERVICE_SCOPE },
				{ "stockdocMonth", "java.lang.String", SERVICE_SCOPE },
				{ "stockdocYear", "java.lang.String", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, 
				{ "dpShare", "java.lang.String", SERVICE_SCOPE },
				{ "remarkByMaker", "java.lang.String", SERVICE_SCOPE },
				{"stockLocation","java.lang.String", REQUEST_SCOPE},
				{"stockLocation","java.lang.String", SERVICE_SCOPE},
				{ "calculatedDP", "java.lang.String", SERVICE_SCOPE },
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				
				{ "totalLonable", "java.lang.String", SERVICE_SCOPE },
				{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
				
				{ "migrationFlag", "java.lang.String", SERVICE_SCOPE },
				{ "migrationFlag", "java.lang.String", REQUEST_SCOPE },
				
				{ "dpCalculateManually", "java.lang.String", SERVICE_SCOPE },
				{ "dpCalculateManually", "java.lang.String", REQUEST_SCOPE },
				
				{ "from_page", "java.lang.String", REQUEST_SCOPE },
				{ "assetsMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValue", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "assetsMarginTypeNew", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValueNew", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityMarginTypeAdv", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValueAdv", "java.lang.String", REQUEST_SCOPE },
				
				{ "liabilityMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValue", "java.lang.String", REQUEST_SCOPE },
				{ SELECTED_STOCK_LOCATIONS, String.class.getName(), SERVICE_SCOPE}
				});
	}


	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		IGeneralChargeDetails chargeDetail = (IGeneralChargeDetails) map.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
		String dueDate = MapperUtil.dateToString(chargeDetail.getDueDate(), null);
		String stockdocMonth = chargeDetail.getStockdocMonth();
		String stockdocYear = chargeDetail.getStockdocYear();
		String trxID = (String) map.get("trxID");
		
		String fundedShare = chargeDetail.getDpShare();
	//	String calculatedDP = chargeDetail.getCalculatedDP();
	//	String fromPage = (String) map.get("from_page");
		String event = (String) map.get("event");
		String stockLocation = (String) map.get("stockLocation");
		String remarkByMaker = (String) map.get("remarkByMaker");

		String calculatedDP = (String) map.get("calculatedDP");
		
		String totalLonable = (String) map.get("totalLonable");
		String migrationFlag = (String) map.get("migrationFlag");
		
		if ("prepare".equalsIgnoreCase(event)) {
		 migrationFlag = "N";
		result.put("migrationFlag", migrationFlag);
		}
		String dpCalculateManually = (String) map.get("dpCalculateManually");
		
		//DefaultLogger.debug(this, "dueDate +++++++> "+dueDate);
		String fromPage = (String) map.get("from_page");
	
	
		IGeneralChargeStockDetails[] stocksArr = chargeDetail.getGeneralChargeStockDetails();
		List<String> selectedLocationsList = new ArrayList<String>();
		if(!ArrayUtils.isEmpty(stocksArr)) {
			for(IGeneralChargeStockDetails stock : stocksArr) {
				selectedLocationsList.add(String.valueOf(stock.getLocationId()));
			}
		}
		String selectedLocations = StringUtils.EMPTY;
		if(!CollectionUtils.isEmpty(selectedLocationsList)) {
			selectedLocations = StringUtils.join(selectedLocationsList.toArray(new String[0]), ",");
		}
		IGeneralChargeDetails chargeDetails = (IGeneralChargeDetails) map.get("form.stockDetailsObject");
		IGeneralChargeStockDetails newStockDetails = chargeDetails.getGeneralChargeStockDetails()[0];
		List stockDetailsList= new ArrayList();

		if (!("edit_current_asset".equals(event) || "edit_current_liabilities".equals(event)
				|| "edit_value_debtors".equals(event) || "edit_less_value_advances".equals(event) || "cancel_current_asset_liabilities".equals(event))) {
			
			if(map.get("serviceStockDetailsList")!=null){
				stockDetailsList= (ArrayList) map.get("serviceStockDetailsList");
			}
			boolean isComponentExist=false;
			for (Iterator iterator = stockDetailsList.iterator(); iterator
					.hasNext();) {
				IGeneralChargeStockDetails stockDetails = (IGeneralChargeStockDetails) iterator.next();
				if(null != newStockDetails && null != newStockDetails.getComponent() && !"".equals(newStockDetails.getComponent())	
						&&	null != stockDetails && null != stockDetails.getComponent() && !"".equals(stockDetails.getComponent())
						&&	stockDetails.getComponent().equals(newStockDetails.getComponent())){
					isComponentExist=true;
				}

			}
			if(!isComponentExist){
				stockDetailsList.add(chargeDetails.getGeneralChargeStockDetails()[0]);
			}else{
				exceptionMap.put("componentsError", new ActionMessage("error.component.already.exist"));
			}
		}

		result.put(SELECTED_STOCK_LOCATIONS, selectedLocations);
		result.put("stockLocation", stockLocation);
		if("prepare_update".equals(fromPage)){
			result.put("serviceStockDetailsList", new ArrayList());
			result.put("dueDate", dueDate);
			result.put("stockdocMonth", stockdocMonth);
			result.put("stockdocYear", stockdocYear);			
			result.put("dpShare", fundedShare);
			result.put("calculatedDP", calculatedDP);
			result.put("remarkByMaker", remarkByMaker);
			result.put("totalLonable", totalLonable);
			result.put("migrationFlag", migrationFlag);
			result.put("dpCalculateManually", dpCalculateManually);
			
		}else{
			result.put("serviceStockDetailsList", map.get("serviceStockDetailsList"));
			result.put("remarkByMaker", remarkByMaker);
			result.put("calculatedDP", calculatedDP);
			result.put("dpShare", fundedShare);
			result.put("totalLonable", totalLonable);
			result.put("migrationFlag", migrationFlag);
			result.put("dpCalculateManually", dpCalculateManually);
		}
		
		if(CollateralAction.EVENT_PREPARE.equals(event)) {
			List<OBInsuranceGC> insuranceList = (List<OBInsuranceGC>) map.get("insuranceList");
			ISecurityCoverage secCoverage = CollateralHelper.getNewAssetBasedGCSecurityCoverageDetails(insuranceList);
			if(secCoverage != null) {
				chargeDetail.setCoverageAmount(secCoverage.getCoverageAmount());
				chargeDetail.setCoveragePercentage(secCoverage.getCoveragePercentage());
			}
			result.put(SESSION_TOTAL_INSURANCE_POLICY_AMT,CollateralHelper.getTotalInsurancePolicyAmount(null,insuranceList));
		}

		result.put("locationList", AssetGenChargeHelper.getLocationList());
		result.put("serviceColObj", map.get("serviceColObj"));
		result.put("trxID", trxID);
		result.put(SESSION_DUE_DATE_AND_STOCK_DETAILS, chargeDetail);
		result.put("from_page", map.get("from_page"));

		result.put("assetsMarginType", map.get("assetsMarginType"));
		result.put("assetsFixedMarginValue", map.get("assetsFixedMarginValue"));
		
		result.put("assetsMarginTypeNew", map.get("assetsMarginTypeNew"));
		result.put("assetsFixedMarginValueNew", map.get("assetsFixedMarginValueNew"));
		result.put("form.stockDetailsObject", chargeDetails);
		
		
		
		result.put("liabilityMarginType", map.get("liabilityMarginType"));
		result.put("liabilityFixedMarginValue", map.get("liabilityFixedMarginValue"));
		
		result.put("liabilityMarginTypeAdv", map.get("liabilityMarginTypeAdv"));
		result.put("liabilityFixedMarginValueAdv", map.get("liabilityFixedMarginValueAdv"));
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
