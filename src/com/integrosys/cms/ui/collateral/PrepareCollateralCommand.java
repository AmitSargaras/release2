/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/PrepareCollateralCommand.java,v 1.20 2007/02/22 17:45:00 Jerlin Exp $
 */

package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail.ILineDetail;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.banksameccy.OBBankSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.corpthirdparty.OBCorporateThirdParty;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.government.OBGovernment;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.personal.OBPersonal;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.sblcsameccy.OBSBLCSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.others.subtype.othersa.OBOthersa;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.comgeneral.OBCommercialGeneral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.insurancecoverage.proxy.IInsuranceCoverageProxyManager;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.ui.collateral.guarantees.linedetail.ILineDetailConstants;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;

/**
 * @author $Author: Jerlin $<br>
 * @version $Revision: 1.20 $
 * @since $Date: 2007/02/22 17:45:00 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 5:17:55 PM
 * To change this template use Options | File Templates.
 */
public class PrepareCollateralCommand extends AbstractCommand {
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 */
	
	private IOtherBankProxyManager otherBankProxyManager ;

	public IOtherBankProxyManager getOtherBankProxyManager() {
		return (IOtherBankProxyManager)BeanHouse.get("otherBankProxyManager");
	}

	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		ICollateral col = null;
		
		List regionList = new ArrayList();
		List stateList = new ArrayList();
		List cityList = new ArrayList();
		
		List regionList3 = new ArrayList();
		List stateList3 = new ArrayList();
		List cityList3 = new ArrayList();
		
		List regionList2 = new ArrayList();
		List stateList2 = new ArrayList();
		List cityList2 = new ArrayList();

		ICollateralTrxValue colTrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		if (colTrxValue!=null) {
			col = (colTrxValue.getStagingCollateral() == null) ? colTrxValue.getCollateral() : colTrxValue
				.getStagingCollateral();
		}
		
		if( (col instanceof OBCorporateThirdParty) || (col instanceof OBBankSameCurrency)
				|| (col instanceof OBGovernment) || (col instanceof OBPersonal) || (col instanceof  OBSBLCSameCurrency)){
			String country = (String) map.get("country");
			String region = (String) map.get("region");
			String state = (String) map.get("locationState");
			
			if(country!=null){
	    		regionList = getRegionList(country);
	    		stateList = getStateList(region);
	    		cityList = getCityList(state);
	    		
	    		result.put("countryList",getCountryList());
	        	result.put("regionList",regionList);
	        	result.put("stateList",stateList);
	        	result.put("cityList",cityList);
	    	}
		}
		if(col instanceof OBCommercialGeneral){
			String country_v1 = (String) map.get("country_v1");
			String region_v1 = (String) map.get("region_v1");
			String state_v1 = (String) map.get("locationState_v1");
			
			String country_v3 = (String) map.get("country_v3");
			String region_v3 = (String) map.get("region_v3");
			String state_v3 = (String) map.get("locationState_v3");
			
			String country_v2 = (String) map.get("country_v2");
			String region_v2 = (String) map.get("region_v2");
			String state_v2 = (String) map.get("locationState_v2");
			
			if(country_v1==null) {
				 country_v1 = ((OBCommercialGeneral) col).getCountry_v1();
				 region_v1 = ((OBCommercialGeneral) col).getRegion_v1();
				 state_v1 = ((OBCommercialGeneral) col).getLocationState_v1();
			}
			
			if(country_v3==null) {
				 country_v3 = ((OBCommercialGeneral) col).getCountry_v3();
				 region_v3 = ((OBCommercialGeneral) col).getRegion_v3();
				 state_v3 = ((OBCommercialGeneral) col).getLocationState_v3();
			}
			if(country_v1!=null){
	    		regionList = getRegionList(country_v1);
	    		stateList = getStateList(region_v1);
	    		cityList = getCityList(state_v1);
	    		
	    		result.put("countryList",getCountryList());
	        	result.put("regionList",regionList);
	        	result.put("stateList",stateList);
	        	result.put("cityList",cityList);
	    	}
			
			if(country_v2==null) {
				 country_v2 = ((OBCommercialGeneral) col).getCountry_v2();
				 region_v2 = ((OBCommercialGeneral) col).getRegion_v2();
				 state_v2 = ((OBCommercialGeneral) col).getLocationState_v2();
			}
			
			if(country_v2!=null){
	    		regionList2 = getRegionList(country_v2);
	    		stateList2 = getStateList(region_v2);
	    		cityList2 = getCityList(state_v2);
	    		
	    		result.put("countryList_v2",getCountryList());
	        	result.put("regionList_v2",regionList2);
	        	result.put("stateList_v2",stateList2);
	        	result.put("cityList_v2",cityList2);
	    	}
			if(country_v3!=null){
	    		regionList3 = getRegionList(country_v3);
	    		stateList3 = getStateList(region_v3);
	    		cityList3 = getCityList(state_v3);
	    		
	    		result.put("countryList_v3",getCountryList());
	        	result.put("regionList_v3",regionList3);
	        	result.put("stateList_v3",stateList3);
	        	result.put("cityList_v3",cityList3);
	    	}
		}
		
		
		
		if(col instanceof OBOthersa){
			IInsuranceCoverageProxyManager insuranceCoverageProxyManager = (IInsuranceCoverageProxyManager) BeanHouse.get("insuranceCoverageProxyManager");
			
			SearchResult sr = insuranceCoverageProxyManager.getInsuranceCoverageList(null,null);
			HashMap insuranceCoverageMap = new HashMap();
			ArrayList resultList = (ArrayList)sr.getResultList();
			for (int i = 0; i < resultList.size(); i++) {
				IInsuranceCoverage insuranceCoverage = (IInsuranceCoverage) resultList.get(i);
				String id = Long.toString(insuranceCoverage.getId());
				String val = insuranceCoverage.getCompanyName();
				insuranceCoverageMap.put(id, val);
			}
			result.put("insuranceCoverageMap", insuranceCoverageMap);
		}

		// get the country listing
		CountryList list = CountryList.getInstance();
		 ISystemBankBranch systemBankBranch = null;
		String countryNme = "";

		ArrayList countryLabels = new ArrayList(list.getCountryLabels());
		ArrayList countryValues = new ArrayList(list.getCountryValues());

		result.put("countryLabels", countryLabels);
		result.put("countryValues", countryValues);

		// get the LE listing
		LEList list1 = LEList.getInstance();
		result.put("LEID", list1.getLEID());
		result.put("LEValue", list1.getLEValue());

		// get exchange control list
		ExchangeControlList list2 = ExchangeControlList.getInstance();
		result.put("ExchangeControlID", list2.getExchangeControlID());
		result.put("ExchangeControlValue", list2.getExchangecontrolValue());

		// get the charge type list
		ChargeTypeList list3 = ChargeTypeList.getInstance();
		result.put("chargeID", list3.getChargeTypeID());
		result.put("chargeValue", list3.getChargeTypeValue());

		// get the currency list
		Collection list4 = getCurrencyList();
		result.put("currencyCode", list4);

		// get the custodian list
		CommonCodeList commonCode = CommonCodeList.getInstance(CategoryCodeConstant.SEC_CUSTODIAN);
		result.put("secCustodianList", commonCode.getCommonCodeLabels());
		result.put("secCustodianID", commonCode.getCommonCodeValues());

		// put the list of frequency unit in the map
		result.put("frequencyValue", TimeFreqList.getInstance().getTimeFreqID());
		result.put("frequencyLabel", TimeFreqList.getInstance().getTimeFreqValue());

		String collateralLoc = null;
		if (map.get("collateralLoc") != null) {
			collateralLoc = (String) map.get("collateralLoc");
		}
		else {
			if (colTrxValue!=null && colTrxValue.getStagingCollateral()!=null) {
				collateralLoc = (colTrxValue.getStagingCollateral()).getCollateralLocation();
			}
		}

		//String event = (String) map.get("event");
		//result.put("event", event);
		result.put("collateralLoc", collateralLoc);

		DefaultLogger.debug(this, "collateral location: " + collateralLoc);

		commonCode = CommonCodeList.getInstance(collateralLoc, CategoryCodeConstant.VALUER);
		result.put("valuerName", commonCode.getCommonCodeValues());
		result.put("valuerNameLabel", commonCode.getCommonCodeLabels());

		// the organization list
		ArrayList organizationLabels = new ArrayList();
		ArrayList organizationValues = new ArrayList();

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.OPTION_LIST);
		result.put("optionListID", commonCode.getCommonCodeValues());
		result.put("optionListValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(collateralLoc, ICMSConstant.CATEGORY_CODE_BKGLOC, true);
		result.put("organizationValues", commonCode.getCommonCodeValues());
		result.put("organizationLabels", commonCode.getCommonCodeLabels());

		// get the risk mitigation list
		RiskMitigationList list5 = RiskMitigationList.getInstance();
		Collection riskMitigationID = list5.getRiskMitigationID();
		Collection riskMitigationValue = list5.getRiskMitigationValue();
		result.put("riskMitigationID", riskMitigationID);
		result.put("riskMitigationValue", riskMitigationValue);

		// get the yes and no list
		YesNoList list6 = YesNoList.getInstance();
		result.put("YesNoID", list6.getYesNoID());
		result.put("YesNoValue", list6.getYesNoValue());

		// get valuation Type
		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.VALUATION_TYPE);
		result.put("valuationValues", commonCode.getCommonCodeValues());
		result.put("valuationLabels", commonCode.getCommonCodeLabels());

		// get valuer description
		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.VALUER);
		result.put("valuerValues", commonCode.getCommonCodeValues());
		result.put("valuerLabels", commonCode.getCommonCodeLabels());

		// get collateral status
		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.COLLATERAL_STATUS);
		result.put("colStatusValues", commonCode.getCommonCodeValues());
		result.put("colStatusLabels", commonCode.getCommonCodeLabels());
		
        result.put("errorMsg", map.get("errorMsg"));
        
        /*Add By Govind S:06/9/2011
         * 
         */
        List collateralList = null;
		if (map.get("collateralList") != null) {
			collateralList = (List) map.get("collateralList");
		}
		else {
			if (colTrxValue!=null && colTrxValue.getStagingCollateral()!=null) {
				 collateralList = getCollateralCodeList(colTrxValue.getCollateral().getCollateralSubType().getSubTypeCode());
			
			}
		}
		if (colTrxValue!=null && colTrxValue.getStagingCollateral()!=null) {
		 String countryCode = colTrxValue.getStagingCollateral().getCollateralLocation();
		    String branchCode = colTrxValue.getStagingCollateral().getSecurityOrganization();
		   
		    systemBankBranch = getSysBankBranchByCuntryAndBranchCode(countryCode, branchCode);
		    
		    countryNme = getCountryNamebyCode(countryCode);
		    result.put("orgList", getListAllSystemBankBranch(colTrxValue.getStagingCollateral().getCollateralLocation()));
		}else{
			result.put("orgList", new ArrayList());
		}
		
		List<ILineDetail> lineDetailList = null;
		if(col instanceof IGuaranteeCollateral) {
			IGuaranteeCollateral guaranteeCol = (IGuaranteeCollateral) col;
			if(guaranteeCol.getLineDetails() != null && guaranteeCol.getLineDetails().length > 0) {
				lineDetailList = new ArrayList<ILineDetail>(Arrays.asList(guaranteeCol.getLineDetails()));
			}
		}
		
		result.put(ILineDetailConstants.SESSION_LINE_DETAIL_LIST, lineDetailList);
        result.put("collateralList", collateralList);
        result.put("systemBankBranch", systemBankBranch);
        result.put("countryNme", countryNme);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return temp;
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "countryLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "colStatusValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "colStatusLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "countryValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "event", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "LEID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "LEValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "ExchangeControlID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "ExchangeControlValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "YesNoID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "YesNoValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "chargeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "chargeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "currencyCode", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secCustodianList", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secCustodianID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "frequencyValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "frequencyLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "collateralLoc", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "optionListID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "optionListValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "organizationValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "organizationLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "riskMitigationID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "riskMitigationValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "valuerName", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "valuerNameLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "valuationValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "valuationLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "valuerValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "valuerLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
                { "errorMsg", "java.lang.String", REQUEST_SCOPE },
                { "collateralList", "java.util.List", REQUEST_SCOPE },
                { "systemBankBranch", "com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch", REQUEST_SCOPE },
                { "countryNme", "java.lang.String", REQUEST_SCOPE },
                { "countryList","java.util.List",SERVICE_SCOPE},
	            { "regionList","java.util.List",SERVICE_SCOPE},
	            { "stateList","java.util.List",SERVICE_SCOPE},
	            { "cityList","java.util.List",SERVICE_SCOPE},
	            { "insuranceCoverageMap","java.util.HashMap",SERVICE_SCOPE},
	            { "orgList", "java.util.List", REQUEST_SCOPE },
	            { "countryList_v3","java.util.List",SERVICE_SCOPE},
	            { "regionList_v3","java.util.List",SERVICE_SCOPE},
	            { "stateList_v3","java.util.List",SERVICE_SCOPE},
	            { "cityList_v3","java.util.List",SERVICE_SCOPE},
	            { "countryList_v2","java.util.List",SERVICE_SCOPE},
	            { "regionList_v2","java.util.List",SERVICE_SCOPE},
	            { "stateList_v2","java.util.List",SERVICE_SCOPE},
	            { "cityList_v2","java.util.List",SERVICE_SCOPE},
	            {ILineDetailConstants.SESSION_LINE_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE},
		});
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "collateralLoc", "java.lang.String", REQUEST_SCOPE }, 
				//{ "event", "java.lang.String", SERVICE_SCOPE }, 
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, 
                { "errorMsg", "java.lang.String", REQUEST_SCOPE },
                { "collateralList", "java.util.List", REQUEST_SCOPE },
                { "country", "java.lang.String", REQUEST_SCOPE },
                { "region", "java.lang.String", REQUEST_SCOPE },
                { "locationState", "java.lang.String", REQUEST_SCOPE },
                { "state", "java.lang.String", REQUEST_SCOPE },
                
                { "country_v1", "java.lang.String", REQUEST_SCOPE },
                { "region_v1", "java.lang.String", REQUEST_SCOPE },
                { "locationState_v1", "java.lang.String", REQUEST_SCOPE },
                { "nearestCity_v1", "java.lang.String", REQUEST_SCOPE },
                { "country_v3", "java.lang.String", REQUEST_SCOPE },
                { "region_v3", "java.lang.String", REQUEST_SCOPE },
                { "locationState_v3", "java.lang.String", REQUEST_SCOPE },
                { "nearestCity_v3", "java.lang.String", REQUEST_SCOPE },
                { "country_v2", "java.lang.String", REQUEST_SCOPE },
                { "region_v2", "java.lang.String", REQUEST_SCOPE },
                { "locationState_v2", "java.lang.String", REQUEST_SCOPE },
                { "nearestCity_v2", "java.lang.String", REQUEST_SCOPE },
				});
	}
	
	private List getListAllSystemBankBranch(String country) {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				ISystemBankBranch[] branch = CollateralDAOFactory.getDAO().getListAllSystemBankBranch(country);
				
				if (branch != null) {
					for (int i = 0; i < branch.length; i++) {
						ISystemBankBranch lst = branch[i];
						String id = lst.getSystemBankBranchCode();
						String value = lst.getSystemBankBranchName();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	protected HashMap mergeResultMap(HashMap other, HashMap original) {
		HashMap resultMap = (HashMap) original.get(ICommonEventConstant.COMMAND_RESULT_MAP);
		resultMap.putAll(other);

		return resultMap;
	}

	protected HashMap mergeExceptionMap(HashMap other, HashMap original) {
		HashMap exceptionMap = (HashMap) original.get(ICommonEventConstant.COMMAND_EXCEPTION_MAP);
		exceptionMap.putAll(other);
		return exceptionMap;
	}

	protected String[][] mergeResultDescriptor(String[][] other, String[][] original) {

		if ((original.length == 0) && (other.length == 0)) {
			return new String[0][];
		}
		if (original.length == 0) {
			return other;
		}
		if (other.length == 0) {
			return original;
		}
		String[][] returnString = new String[other.length + original.length][];
		//DefaultLogger.debug(this, "============= before 1st arraycopy: original length: " + original.length);
		System.arraycopy(original, 0, returnString, 0, original.length);
		//DefaultLogger.debug(this, "============= before 2nd arraycopy: other length: " + other.length);
		System.arraycopy(other, 0, returnString, original.length, other.length);
		//DefaultLogger.debug(this, "============= finish arraycopy to returnString: returnString length: "
		//		+ returnString.length);
		return returnString;
	}

	private List getCurrencyList() {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				IForexFeedEntry[] currency = CollateralDAOFactory.getDAO().getCurrencyList();
				
				if (currency != null) {
					for (int i = 0; i < currency.length; i++) {
						IForexFeedEntry lst = currency[i];
						String id = lst.getCurrencyIsoCode().trim();
						String value = lst.getCurrencyIsoCode().trim();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	//Add By Govind S:Get collateral code with desc,05/09/2011
	private List getCollateralCodeList(String subTypeValue) {
		List lbValList = new ArrayList();
		try {
			if (subTypeValue != null) {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				List colCodeLst = helper.getSBMISecProxy().getCollateralCodeBySubTypes(subTypeValue);
				if (colCodeLst != null) {
					
					for (int i = 0; i < colCodeLst.size(); i++) {
						String[] codeLst = (String[]) colCodeLst.get(i);
						String code = codeLst[0];
						String name = codeLst[1];
						LabelValueBean lvBean = new LabelValueBean(UIUtil.replaceSpecialCharForXml(name), UIUtil
								.replaceSpecialCharForXml(code));
						lbValList.add(lvBean);
					}
				}
			}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	private ISystemBankBranch getSysBankBranchByCuntryAndBranchCode(String country , String branchCode) {
		ISystemBankBranch branch = null;
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				 branch = CollateralDAOFactory.getDAO().getSysBankBranchByCuntryAndBranchCode(country , branchCode);
		}
		catch (Exception ex) {
		}
		return branch;
	}
	private String getCountryNamebyCode(String countryCode) {
		List lbValList = new ArrayList();
		String value = null;
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				ICountry[] country = CollateralDAOFactory.getDAO().getCountryNamebyCode(countryCode);
				
				
				if (country != null) {
					for (int i = 0; i < country.length; i++) {
						ICountry lst = country[i];
						String id = lst.getCountryCode();
						value = lst.getCountryName();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
		}
		return value;
	}
	
	//********** Added By Dattatray Thorat *************
	
	private List getCountryList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getOtherBankProxyManager().getCountryList();
			
		
			for (int i = 0; i < idList.size(); i++) {
				ICountry country = (ICountry)idList.get(i);
				if( country.getStatus().equals("ACTIVE")) {
					String id = Long.toString(country.getIdCountry());
					String val = country.getCountryName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
    
    private List getRegionList(String countryId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getOtherBankProxyManager().getRegionList(countryId);				
		
			for (int i = 0; i < idList.size(); i++) {
				IRegion region = (IRegion)idList.get(i);
				if( region.getStatus().equals("ACTIVE")) {
					String id = Long.toString(region.getIdRegion());
					String val = region.getRegionName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getStateList(String regionId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getOtherBankProxyManager().getStateList(regionId);				
		
			for (int i = 0; i < idList.size(); i++) {
				IState state = (IState)idList.get(i);
				if( state.getStatus().equals("ACTIVE")) {
					String id = Long.toString(state.getIdState());
					String val = state.getStateName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getCityList(String stateId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getOtherBankProxyManager().getCityList(stateId);				
		
			for (int i = 0; i < idList.size(); i++) {
				ICity city = (ICity)idList.get(i);
				if( city.getStatus().equals("ACTIVE")) {
					String id = Long.toString(city.getIdCity());
					String val = city.getCityName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
