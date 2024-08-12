/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetpostdatedchqs/AddChequeCommand.java,v 1.2 2005/08/26 10:12:37 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.component.bus.IComponentDao;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.insurancecoverage.bus.OBInsuranceCoverage;
import com.integrosys.cms.app.insurancecoverage.proxy.IInsuranceCoverageProxyManager;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/26 10:12:37 $ Tag: $Name: $
 */

public class ViewCurrentAssetAndLiabilityCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, 
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE }, 
				{ "totalLonable", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValue", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "assetsMarginTypeNew", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValueNew", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "liabilityMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValue", "java.lang.String", REQUEST_SCOPE },
				
				{ "liabilityMarginTypeAdv", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValueAdv", "java.lang.String", REQUEST_SCOPE },
				
				{ "obIndex", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "from_page", "java.lang.String", REQUEST_SCOPE },
		
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
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ "locationList", "java.util.List", ICommonEventConstant.REQUEST_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, 
				{ "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
				{ "assetsMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValue", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "assetsMarginTypeNew", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValueNew", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "liabilityMarginTypeAdv", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValueAdv", "java.lang.String", REQUEST_SCOPE },
				
				{ "liabilityMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValue", "java.lang.String", REQUEST_SCOPE },
				{ "insuranceCompanyList", "java.util.List", ICommonEventConstant.REQUEST_SCOPE },
				{ "from_page", "java.lang.String", REQUEST_SCOPE },
				{ "obIndex", "java.lang.String", REQUEST_SCOPE },
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE }, 
				{ "totalLonable", "java.lang.String", REQUEST_SCOPE }, 
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "cmsRefId", "java.lang.String", REQUEST_SCOPE },
				{"applicableForDpList", " java.util.HashMap", SERVICE_SCOPE},
				{"compoList", " java.util.HashMap", SERVICE_SCOPE}  
				});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		IGeneralChargeDetails chargeDetails = (IGeneralChargeDetails) map.get("form.stockDetailsObject");
		String event= (String) map.get("event");
		String calculatedDP= (String) map.get("calculatedDP");
		String totalLonable= (String) map.get("totalLonable");
		String trxID= (String) map.get("trxID");
		List stockDetailsList= new ArrayList();
		if(map.get("serviceStockDetailsList")!=null){
			stockDetailsList= (ArrayList) map.get("serviceStockDetailsList");
		}
		
		String category="";
		if("edit_prepare_current_asset".equals(event)) {
			category="Stock";
		}else if("edit_prepare_current_liabilities".equals(event)) {
			category="Creditors";
		}
		else if("edit_prepare_value_debtors".equals(event)) {
			category="Debtors";
		}
		else if("edit_prepare_less_value_advances".equals(event)) {
			category="Advances";
		}
		
		if(!"edit_current_asset_ins_error".equals(event)){
			int obIndex= 0;
			String obIndexStr = (String) map.get("obIndex");
			if(obIndexStr!=null){
				obIndex= Integer.parseInt(obIndexStr);
			}
	
			IGeneralChargeStockDetails stockDetails = (IGeneralChargeStockDetails) stockDetailsList.get(obIndex);
			IGeneralChargeStockDetails[] generalChargeStockDetailsArray=new IGeneralChargeStockDetails[1];
			generalChargeStockDetailsArray[0]=stockDetails;
			
			long cmsRefId= generalChargeStockDetailsArray[0].getCmsRefId();
			result.put("cmsRefId", cmsRefId);

			chargeDetails.setGeneralChargeStockDetails(generalChargeStockDetailsArray);
		}
		
		
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");

		IGeneralCharge iAsset = (IGeneralCharge) itrxValue.getStagingCollateral();
		//addStockDetails1(chargeDetails.getGeneralChargeStockDetails()[0]);
		itrxValue.setStagingCollateral(iAsset);
//-----------------
		String assetsMarginType = (String) map.get("assetsMarginType");
		String assetsFixedMarginValue = (String) map.get("assetsFixedMarginValue");
		
		String assetsMarginTypeNew = (String) map.get("assetsMarginTypeNew");
		String assetsFixedMarginValueNew = (String) map.get("assetsFixedMarginValueNew");
		
		String liabilityMarginType = (String) map.get("liabilityMarginType");
		String liabilityFixedMarginValue = (String) map.get("liabilityFixedMarginValue");
		
		String liabilityMarginTypeAdv = (String) map.get("liabilityMarginTypeAdv");
		String liabilityFixedMarginValueAdv = (String) map.get("liabilityFixedMarginValueAdv");

		result.put("form.stockDetailsObject", chargeDetails);
		result.put("assetsMarginType", assetsMarginType);
		result.put("assetsFixedMarginValue", assetsFixedMarginValue);
		
		result.put("assetsMarginTypeNew", assetsMarginTypeNew);
		result.put("assetsFixedMarginValueNew", assetsFixedMarginValueNew);
		
		result.put("liabilityMarginTypeAdv", liabilityMarginTypeAdv);
		result.put("liabilityFixedMarginValueAdv", liabilityFixedMarginValueAdv);
		
		result.put("liabilityMarginType", liabilityMarginType);
		result.put("liabilityFixedMarginValue", liabilityFixedMarginValue);
//-----------------
		result.put("locationList", AssetGenChargeHelper.getLocationList());
		result.put("insuranceCompanyList", getInsuranceCompanyList());
		result.put("serviceColObj", itrxValue);
		result.put("serviceStockDetailsList", stockDetailsList);
		result.put("trxID", map.get("trxID"));
		result.put("subtype", map.get("subtype"));
		result.put("calculatedDP", map.get("calculatedDP"));
		result.put("totalLonable", map.get("totalLonable"));
		result.put("from_page", map.get("from_page"));
		result.put("obIndex", map.get("obIndex"));
		result.put("event", map.get("event"));

		//Start santosh
		IComponentDao componentDAO=(IComponentDao)BeanHouse.get("componentDao");
		List<String> applicableForDpList=new ArrayList<String>();
		applicableForDpList=componentDAO.getApplicableForDpList();
		result.put("applicableForDpList", applicableForDpList);
		//end santosh
		
		List<String> compoList=new ArrayList<String>();
		AssetGenChargeHelper helper= new AssetGenChargeHelper();
		compoList=helper.getCompoList(category);
		result.put("compoList", compoList);
		
		
		DefaultLogger.debug(this, "inside AddAssetsAndLiabilityCommand");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	
	private List getInsuranceCompanyList() {
		IInsuranceCoverageProxyManager proxy= (IInsuranceCoverageProxyManager)BeanHouse.get("insuranceCoverageProxyManager");
		SearchResult insuranceCoverageSR = proxy.getInsuranceCoverageList(null, null);

		List insuranceCoverageList=new ArrayList();
		insuranceCoverageList.addAll(insuranceCoverageSR.getResultList());
	    
	    
	    List insuranceCoverageLVBList= new ArrayList();
	    if(insuranceCoverageList!=null && insuranceCoverageList.size()>0){
	    	String label;
			String value;
	    for (Iterator iter = insuranceCoverageList.iterator(); iter.hasNext();) {
			IInsuranceCoverage coverage = (OBInsuranceCoverage) iter.next();
	    	label=coverage.getCompanyName();
			value= Long.toString(coverage.getId());
			LabelValueBean lvBean = new LabelValueBean(label,value);
			insuranceCoverageLVBList.add(lvBean);
	    }
	    }
		return insuranceCoverageLVBList;
	}
	    
}
