/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/ReturnCollateralCommand.java,v 1.4 2003/09/19 08:49:33 hshii Exp $
 */

package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail.ILineDetail;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.comgeneral.OBCommercialGeneral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.ICityDAO;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.insurancecoverage.proxy.IInsuranceCoverageProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.ui.collateral.guarantees.linedetail.ILineDetailConstants;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 *
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/19 08:49:33 $ Tag: $Name: $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 12:13:00 PM
 * To change this template use Options | File Templates.
 */
public class ReturnCollateralCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                // {"collateralID", "java.lang.String", REQUEST_SCOPE},
        		
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                { "collateralList", "java.util.List", REQUEST_SCOPE },
                { "trxID", "java.lang.String", REQUEST_SCOPE },
                {"stockLocation","java.lang.String", REQUEST_SCOPE},
                { "event", "java.lang.String", REQUEST_SCOPE },
                { "countryList", "java.util.List", SERVICE_SCOPE },	
				{ "regionList", "java.util.List", SERVICE_SCOPE },
				{ "cityList", "java.util.List", SERVICE_SCOPE },
				{ "stateList", "java.util.List", SERVICE_SCOPE },
				 { "migrationFlag", "java.lang.String", REQUEST_SCOPE },
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
        return (new String[][]{
                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                {"form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE},
                {"collateralID", "java.lang.String", REQUEST_SCOPE},
                { "collateralList", "java.util.List", REQUEST_SCOPE },
                { "systemBankBranch", "com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch", REQUEST_SCOPE },
                { "countryNme", "java.lang.String", REQUEST_SCOPE },
                { "trxID", "java.lang.String", REQUEST_SCOPE },
                {"stockLocation","java.lang.String", REQUEST_SCOPE},
				{"stockLocation","java.lang.String", SERVICE_SCOPE},
                { "insuranceCoverageMap","java.util.HashMap",SERVICE_SCOPE},
                { "orgList", "java.util.List", REQUEST_SCOPE },
            	{ "countryList", "java.util.List", SERVICE_SCOPE },	
				{ "regionList", "java.util.List", SERVICE_SCOPE },
				{ "cityList", "java.util.List", SERVICE_SCOPE },
				{ "frequencyList", "java.util.List", REQUEST_SCOPE },
				{ "stateList", "java.util.List", SERVICE_SCOPE },
				{ "countryList3", "java.util.List", SERVICE_SCOPE },	
				{ "regionList3", "java.util.List", SERVICE_SCOPE },
				{ "cityList3", "java.util.List", SERVICE_SCOPE },
				{ "stateList3", "java.util.List", SERVICE_SCOPE },
				 { "migrationFlag", "java.lang.String", SERVICE_SCOPE },
				{ILineDetailConstants.SESSION_LINE_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE},
          });
    }

    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here reading for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        ISystemBankBranch systemBankBranch = null;
        String countryNme = "";
        HashMap insuranceCoverageMap = new HashMap();
        
        IInsuranceCoverageProxyManager insuranceCoverageProxyManager = (IInsuranceCoverageProxyManager) BeanHouse.get("insuranceCoverageProxyManager");
		
		SearchResult sr = insuranceCoverageProxyManager.getInsuranceCoverageList(null,null);
		ArrayList resultList = (ArrayList)sr.getResultList();
		for (int i = 0; i < resultList.size(); i++) {
			IInsuranceCoverage insuranceCoverage = (IInsuranceCoverage) resultList.get(i);
			String id = Long.toString(insuranceCoverage.getId());
			String val = insuranceCoverage.getCompanyName();
			insuranceCoverageMap.put(id, val);
		}
		result.put("insuranceCoverageMap", insuranceCoverageMap);
		String migrationFlag = (String) map.get("migrationFlag");
		 result.put("migrationFlag", migrationFlag);
        ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");

        if (itrxValue != null) {
            
            String from_event = (String) map.get("from_event");
            String event = (String) map.get("event");
            if ((from_event != null) && from_event.equals("read") ) {
                result.put("form.collateralObject", itrxValue.getCollateral());
            } else {            	
            	ICollateral col = itrxValue.getStagingCollateral();
            	col.setCurrencyCode(itrxValue.getStagingCollateral().getCurrencyCode());
            	col.setCollateralLocation(itrxValue.getStagingCollateral().getCollateralLocation());
            	col.setSecurityOrganization(itrxValue.getStagingCollateral().getSecurityOrganization());
            	col.setSecPriority(itrxValue.getStagingCollateral().getSecPriority());
            	col.setMonitorFrequency(itrxValue.getStagingCollateral().getMonitorFrequency());
            	col.setMonitorProcess(itrxValue.getStagingCollateral().getMonitorProcess());
            	itrxValue.setStagingCollateral(col); 
                result.put("form.collateralObject", itrxValue.getStagingCollateral());
            }
            result.put("serviceColObj", itrxValue);
        }
        if (itrxValue.getCollateral() != null) {
            result.put("collateralID", String.valueOf(itrxValue.getCollateral().getCollateralID()));
        }

        if (ICMSConstant.STATE_PENDING_PERFECTION.equals(itrxValue.getStatus())
                && (ICMSConstant.STATE_PENDING_CREATE.equals(itrxValue.getFromState())
                || ICMSConstant.PENDING_UPDATE.equals(itrxValue.getFromState()))) {
            Map context = new HashMap();
            //Andy Wong: set CMV to staging if actual got value but staging blank, used for pre Stp valuation validation
                if(itrxValue.getCollateral()!=null && itrxValue.getCollateral().getCMV()!=null
                        && (itrxValue.getStagingCollateral().getCMV()==null || itrxValue.getStagingCollateral().getCMV().getAmount() <= 0)){
                itrxValue.getStagingCollateral().setCMV(itrxValue.getCollateral().getCMV());
            }
            context.put(CollateralStpValidator.COL_OB, itrxValue.getStagingCollateral());
            context.put(CollateralStpValidator.TRX_STATUS, itrxValue.getStatus());
            context.put(CollateralStpValidator.COL_TRX_VALUE, itrxValue);
            ActionErrors errors = CollateralStpValidateUtils.validateAndAccumulate(context);
            if (!errors.isEmpty()) {
                temp.put(MESSAGE_LIST, errors);
            }
        }

        /*Add By Govind S:06/9/2011
         * 
         */
        List collateralList = null;
		if (map.get("collateralList") != null) {
			collateralList = (List) map.get("collateralList");
		}
		else {
			if (itrxValue!=null) {
				 collateralList = getCollateralCodeList(itrxValue.getCollateral().getCollateralSubType().getSubTypeCode());
			
			}
		}
		if (itrxValue!=null) {
		 String countryCode = itrxValue.getStagingCollateral().getCollateralLocation();
		    String branchCode = itrxValue.getStagingCollateral().getSecurityOrganization();
		   
		    systemBankBranch = getSysBankBranchByCuntryAndBranchCode(countryCode, branchCode);
		    
		    countryNme = getCountryNamebyCode(countryCode);
		}
		
		// -----------added by Bharat & sachin
		String cityId = null;
		String countryId = null;
		String stateId = null;
		String regionId = null;
		int country = 0;
		
		String cityId3 = null;
		String countryId3 = null;
		String stateId3 = null;
		String regionId3 = null;
		int country3 = 0;
		String stockLocation = (String) map.get("stockLocation");
		result.put("stockLocation", stockLocation);
		if (itrxValue != null) {
			if (itrxValue.getStagingCollateral().getCollateralSubType().getSubTypeCode().equals("PT701")) {
				OBCommercialGeneral col = (OBCommercialGeneral) itrxValue.getStagingCollateral();
				/*cityId = col.getNearestCity();
				countryId = col.getCountry();
				stateId = col.getLocationState();
				regionId = col.getRegion();*/
				
				cityId = col.getNearestCity_v1();
				countryId = col.getCountry_v1();
				stateId = col.getLocationState_v1();
				regionId = col.getRegion_v1();
				
				cityId3 = col.getNearestCity_v3();
				countryId3 = col.getCountry_v3();
				stateId3 = col.getLocationState_v3();
				regionId3 = col.getRegion_v3();
			}else if (itrxValue.getStagingCollateral().getCollateralSubType().getSubTypeCode().equals("GT400")||
					itrxValue.getStagingCollateral().getCollateralSubType().getSubTypeCode().equals("GT402")) {
				IGuaranteeCollateral stageCollateral = (IGuaranteeCollateral) itrxValue.getStagingCollateral();
				cityId = stageCollateral.getCity();
				countryId = stageCollateral.getCountry();
				stateId = stageCollateral.getState();
				regionId = stageCollateral.getRegion();
			}
		}
		
		if (!( stateId == null  ||  stateId.equals("") )) {
			result.put("cityList", getCityList(Long.parseLong(stateId)));
		}else{
			result.put("cityList", map.get("cityList"));
		}		
		if (!( regionId == null || regionId.equals(""))) {
			result.put("stateList", getStateList(Long.parseLong(regionId)));
		}else{
			result.put("stateList",  map.get("stateList"));
		}
		if (!( countryId == null  ||  countryId.equals("") )) {
			result.put("regionList", getRegionList(Long.parseLong(countryId.trim())));
		}else{
			result.put("regionList",  map.get("regionList"));
		}
		
		
		result.put("countryList", getCountryList(country));
		
		if (!( stateId3 == null  ||  "".equals(stateId3) )) {
			result.put("cityList3", getCityList(Long.parseLong(stateId3)));
		}else{
			result.put("cityList3", map.get("cityList"));
		}		
		if (!( regionId3 == null || "".equals(regionId3))) {
			result.put("stateList3", getStateList(Long.parseLong(regionId3)));
		}else{
			result.put("stateList3",  map.get("stateList"));
		}
		if (!( countryId3 == null  ||  "".equals(countryId3) )) {
			result.put("regionList3", getRegionList(Long.parseLong(countryId3.trim())));
		}else{
			result.put("regionList3",  map.get("regionList"));
		}
		
		
		result.put("countryList3", getCountryList(country3));
		
		//-----------end by Bharat & sachin
		
		List<ILineDetail> lineDetailList = null;
		ICollateral col = itrxValue.getStagingCollateral();
		if(col instanceof IGuaranteeCollateral) {
			IGuaranteeCollateral guaranteeCol = (IGuaranteeCollateral) col;
			if(guaranteeCol.getLineDetails() != null && guaranteeCol.getLineDetails().length > 0) {
				lineDetailList = new ArrayList<ILineDetail>(Arrays.asList(guaranteeCol.getLineDetails()));
			}
		}
		
		String idStr = (String) map.get("trxID");
		result.put("trxID", idStr);
		result.put("orgList", getListAllSystemBankBranch(itrxValue.getStagingCollateral().getCollateralLocation()));
		result.put("frequencyList",getFrequencyList() );
        result.put("collateralList", collateralList);
        result.put("systemBankBranch", systemBankBranch);
        result.put("countryNme", countryNme);
        result.put(ILineDetailConstants.SESSION_LINE_DETAIL_LIST, lineDetailList);
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
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
	
	private List getCountryList(long countryId) {
		List lbValList = new ArrayList();
		try {
			ICityDAO city = (ICityDAO)BeanHouse.get("cityDAO");
			
			List idList = (List)city.getCountryList(countryId);
			for (int i = 0; i < idList.size(); i++) {
				ICountry country = (ICountry) idList.get(i);
				if (country.getStatus().equals("ACTIVE")) {
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
	private List getStateList(long stateId) {
		List lbValList = new ArrayList();
		try {
			
			ICityDAO city = (ICityDAO)BeanHouse.get("cityDAO");
			List idList = (List) city.getStateList(stateId);
			for (int i = 0; i < idList.size(); i++) {
				IState state = (IState) idList.get(i);
				if (state.getStatus().equals("ACTIVE")) {
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

	private List getRegionList(long regionId) {
		List lbValList = new ArrayList();
		try {
			
			ICityDAO city = (ICityDAO)BeanHouse.get("cityDAO");
			List idList = (List) city.getRegionList(regionId);

			for (int i = 0; i < idList.size(); i++) {
				IRegion region = (IRegion) idList.get(i);
				if (region.getStatus().equals("ACTIVE")) {
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

	
	
	private List getCityList(long cityId) {
		List lbValList = new ArrayList();
		try {
			
			ICityDAO city1 = (ICityDAO)BeanHouse.get("cityDAO");
			List idList = (List) city1.getCityList(cityId);

			for (int i = 0; i < idList.size(); i++) {
				ICity city = (ICity) idList.get(i);
				if (city.getStatus().equals("ACTIVE")) {
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
	
	private List getFrequencyList() {
		List lbValList = new ArrayList();
		List values = new ArrayList();
		TreeSet ts = new TreeSet();
		try {
		values.addAll(CommonDataSingleton.getCodeCategoryLabels(CategoryCodeConstant.FREQUENCY));
		
		for (int i = 0; i < values.size(); i++) {
			ts.add(new Integer(values.get(i).toString()));
		}
		Iterator itr = ts.iterator();
		
		while (itr.hasNext()) {
				String val = itr.next().toString();
				LabelValueBean lvBean = new LabelValueBean(val,val );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return lbValList;
}
}
