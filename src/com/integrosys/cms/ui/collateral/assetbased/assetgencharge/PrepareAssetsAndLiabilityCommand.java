/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetpostdatedchqs/AddChequeCommand.java,v 1.2 2005/08/26 10:12:37 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_DETAILS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
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
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/26 10:12:37 $ Tag: $Name: $
 */

public class PrepareAssetsAndLiabilityCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValue", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "assetsMarginTypeNew", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValueNew", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "liabilityMarginTypeAdv", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValueAdv", "java.lang.String", REQUEST_SCOPE },
				
				{ "liabilityMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValue", "java.lang.String", REQUEST_SCOPE },
				
				{ "migrationFlag", "java.lang.String", REQUEST_SCOPE }, 
				
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
				{"itemType","java.lang.String", REQUEST_SCOPE },
				{"event","java.lang.String", REQUEST_SCOPE },
				
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
				{ "insuranceCompanyList", "java.util.List", ICommonEventConstant.REQUEST_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "assetsMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValue", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "assetsMarginTypeNew", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValueNew", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "liabilityMarginTypeAdv", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValueAdv", "java.lang.String", REQUEST_SCOPE },
				
				{ "liabilityMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValue", "java.lang.String", REQUEST_SCOPE }, 
				{ "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
				{"applicableForDpList", " java.util.HashMap", SERVICE_SCOPE},
				{"compoList", " java.util.HashMap", SERVICE_SCOPE} ,
				{"itemType","java.lang.String", REQUEST_SCOPE },
				{"event","java.lang.String", REQUEST_SCOPE },
				{ "migrationFlag", "java.lang.String", REQUEST_SCOPE }, 
				
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
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
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		
		String category= "";
		String itemType=(String)map.get("itemType");
		String event=(String)map.get("event");
			if("CURRENTASSET".equals(itemType)  || "prepare_create_current_asset".equals(event) ) {
				 category="Stock";
			}else if("CURRENTLIABILITIES".equals(itemType) || "prepare_create_current_liabilities".equals(event)) {
				 category="Creditors";
			}else if("VALUEDEBTORS".equals(itemType) || "prepare_create_value_debtors".equals(event)) {
				 category="Debtors";
			}else if("LESSVALUEADVANCES".equals(itemType) || "prepare_create_less_value_advances".equals(event)) {
				 category="Advances";
			}
			
		IGeneralChargeDetails newChargeDetails = (IGeneralChargeDetails) map.get("form.stockDetailsObject");
		IGeneralChargeDetails existingChargeDetails = (IGeneralChargeDetails) map.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
		
		String assetsMarginType = (String) map.get("assetsMarginType");
		String assetsFixedMarginValue = (String) map.get("assetsFixedMarginValue");
		
		String assetsMarginTypeNew = (String) map.get("assetsMarginTypeNew");
		String assetsFixedMarginValueNew = (String) map.get("assetsFixedMarginValueNew");
		
		String liabilityMarginType = (String) map.get("liabilityMarginType");
		String liabilityFixedMarginValue = (String) map.get("liabilityFixedMarginValue");
		
		String liabilityMarginTypeAdv = (String) map.get("liabilityMarginTypeAdv");
		String liabilityFixedMarginValueAdv = (String) map.get("liabilityFixedMarginValueAdv");
		
		String migrationFlag = (String) map.get("migrationFlag");

		String errorCode="";
		System.out.println("category=======validate===="+category);
		if("Stock".equalsIgnoreCase(category)) {
		if("FIXED".equals(assetsMarginType)){
			if (!(errorCode = Validator.checkNumber(assetsFixedMarginValue, true, 0, 100.00, 3,locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("marginErrorAsset",  new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						"100.00" + ""));
			}
		}
		}
		else	if("Debtors".equalsIgnoreCase(category)) {
		if("FIXED".equals(assetsMarginTypeNew)){
			if (!(errorCode = Validator.checkNumber(assetsFixedMarginValueNew, true, 0, 100.00, 3,locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("marginErrorAssetNew",  new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						"100.00" + ""));
			}
		}
		}
		
		else if("Creditors".equalsIgnoreCase(category)) {
		if("FIXED".equals(liabilityMarginType)){
			if (!(errorCode = Validator.checkNumber(liabilityFixedMarginValue, true, 0, 100.00, 3,locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("marginErrorLiability",  new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						"100.00" + ""));
			}
		}
		}
		else	if("Advances".equalsIgnoreCase(category)) {
		if("FIXED".equals(liabilityMarginTypeAdv)){
			if (!(errorCode = Validator.checkNumber(liabilityFixedMarginValueAdv, true, 0, 100.00, 3,locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("marginErrorLiabilityAdv",  new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						"100.00" + ""));
			}
		}
		}
		
		DefaultLogger.debug(this, "inside PrepareAssetsAndLiabilityCommand");
		
		if(existingChargeDetails !=null) {
			existingChargeDetails.setCoverageAmount(newChargeDetails.getCoverageAmount());
			existingChargeDetails.setAdHocCoverageAmount(newChargeDetails.getAdHocCoverageAmount());
			existingChargeDetails.setCoveragePercentage(newChargeDetails.getCoveragePercentage());
			result.put(SESSION_DUE_DATE_AND_STOCK_DETAILS, existingChargeDetails);
		}
		
		result.put("assetsMarginType", assetsMarginType);
		result.put("assetsFixedMarginValue", assetsFixedMarginValue);
		
		result.put("assetsMarginTypeNew", assetsMarginTypeNew);
		result.put("assetsFixedMarginValueNew", assetsFixedMarginValueNew);
		
		result.put("liabilityMarginTypeAdv", liabilityMarginTypeAdv);
		result.put("liabilityFixedMarginValueAdv", liabilityFixedMarginValueAdv);
		
		result.put("migrationFlag", migrationFlag);
		result.put("locationList", AssetGenChargeHelper.getLocationList());
		
		result.put("liabilityMarginType", liabilityMarginType);
		result.put("liabilityFixedMarginValue", liabilityFixedMarginValue);
		result.put("insuranceCompanyList", getInsuranceCompanyList());
		result.put("form.stockDetailsObject", map.get("form.stockDetailsObject"));
		result.put("applicableForDpList", getApplicableForDpList());
		result.put("compoList", getCompoList(category));
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		result.put("serviceColObj", itrxValue);
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
	//Start santosh
	private List getApplicableForDpList()
	{
		IComponentDao componentDAO=(IComponentDao)BeanHouse.get("componentDao");
		List<String> applicableForDpList=new ArrayList<String>();
		applicableForDpList=componentDAO.getApplicableForDpList();
		return applicableForDpList;
	}
	//end santosh
	private List getCompoList(String category)
	{
		List<String> compoList=new ArrayList<String>();
		AssetGenChargeHelper helper= new AssetGenChargeHelper();
		compoList=helper.getCompoList(category);
		return compoList;
	}
}
