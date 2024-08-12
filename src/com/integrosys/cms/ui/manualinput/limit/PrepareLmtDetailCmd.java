/*
 * Created on Feb 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;
import com.integrosys.cms.app.customer.bus.ILimitXRefCoBorrower;
import com.integrosys.cms.app.customer.bus.OBCoBorrowerDetails;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMasterDao;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMasterJdbc;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.riskType.bus.IRiskType;
import com.integrosys.cms.app.riskType.proxy.IRiskTypeProxyManager;
import com.integrosys.cms.app.limit.bus.IFacilityCoBorrowerDetails;

import com.integrosys.cms.app.limit.bus.ICollateralAllocation;

import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.OBFacilityCoBorrowerDetails;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.ICategoryEntryConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PrepareLmtDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "agreementType", "java.lang.String", REQUEST_SCOPE },
				{ "facilityGroup", "java.lang.String", REQUEST_SCOPE },
				{ "origBookingCtry", "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE }, 
				{ "isCreate", "java.lang.String", REQUEST_SCOPE },
				{ "facCat", "java.lang.String", REQUEST_SCOPE },
				{ "facName", "java.lang.String", REQUEST_SCOPE },
				{ "facilityCode", "java.lang.String", REQUEST_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "inrValue", "java.lang.String", REQUEST_SCOPE },
				{ "liabilityIDList", "java.util.List", SERVICE_SCOPE },
				{ "subPartyName" , "java.lang.String", REQUEST_SCOPE },
				
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{ "facCoBorrowerList", "java.util.List", SERVICE_SCOPE },
				{ "facCoBorrowerLiabIds", "java.lang.String", REQUEST_SCOPE },
				/*{ "availAndOptionApplicable", "java.lang.String", SERVICE_SCOPE },*/
				{ "availAndOptionApplicable", "java.lang.String", REQUEST_SCOPE },
				
				
				});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "countryList", "java.util.List", REQUEST_SCOPE },
			//{ "lmtDetailForm", "java.lang.Object", FORM_SCOPE },
				{ "orgList", "java.util.List", REQUEST_SCOPE }, { "outerLmtList", "java.util.List", REQUEST_SCOPE },
				
				
				{ "facilityTypeList", "java.util.List", REQUEST_SCOPE },
				{ "currencyList", "java.util.List", REQUEST_SCOPE },
				{ "timeFreqList", "java.util.List", REQUEST_SCOPE },
				{ "loanTypeList", "java.util.List", REQUEST_SCOPE },
				{ "lmtSecRelnshipList", "java.util.List", REQUEST_SCOPE },
				{ "returnUrl", "java.lang.String", REQUEST_SCOPE },
				{ "facNameList", "java.util.List", SERVICE_SCOPE },
				{ "facDetailList", "java.util.List", SERVICE_SCOPE },
				{ "relationShipMngrList", "java.util.List", SERVICE_SCOPE },
				{ "subFacNameList", "java.util.List", SERVICE_SCOPE },
				{ "subPartyNameList", "java.util.List", SERVICE_SCOPE },
				{ "liabilityIDList", "java.util.List", SERVICE_SCOPE },
				{ "updateEvent", "java.lang.String", REQUEST_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "inrValue", "java.lang.String", SERVICE_SCOPE },
				{ "relationShipMgrName", "java.lang.String", SERVICE_SCOPE },
				{ "isCreate", "java.lang.String", REQUEST_SCOPE },
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
				{ "facCoBorrowerList", "java.util.List", SERVICE_SCOPE },
				{ "facCoBorrowerLiabIds", "java.lang.String", SERVICE_SCOPE },
				{ "LineCoBorrowIds", "java.util.List", SERVICE_SCOPE },

				{ "isCollateralMapped", String.class.getName() , REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "riskTypeList", "java.util.List", SERVICE_SCOPE },
				
				/*{ "availAndOptionApplicable", "java.lang.String", SERVICE_SCOPE },*/
				{ "availAndOptionApplicable", "java.lang.String", REQUEST_SCOPE },
				{ "isGeneralChargeCollateralMapped", String.class.getName() , REQUEST_SCOPE }
				});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		List actualRiskTypeIds= new ArrayList();

		try {
			DefaultLogger.debug(this, "**********In PrepareLmtDetailCmd(): Line 133.");
			ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
			ICommonUser user = (ICommonUser) (map.get(IGlobalConstant.USER));
			String event = (String) (map.get("event"));
			String limitProfileId = (String) (map.get("limitProfileID"));
			String custId = (String) (map.get("customerID"));
			String agreementType = (String) (map.get("agreementType"));
			String availAndOptionApplicable2 = (String) (map.get("availAndOptionApplicable"));
			System.out.println("availAndOptionApplicable in jAVA"+availAndOptionApplicable2);
			String isCreate = (String) (map.get("isCreate"));
			DefaultLogger.debug(this, "**********In doExecute of PrepareLmtDetailCmd : 141" + limitProfileId);
			result.put("isCreate", isCreate);
			result.put("sessionCriteria",map.get("sessionCriteria"));
			String facCat = (String) (map.get("facCat"));
			String facName=(String) (map.get("facName"));
			String facCode="";
			MILimitUIHelper helper = new MILimitUIHelper();
			if ((agreementType == null) || agreementType.trim().equals("")) {
			}
			String facGroup = (String) (map.get("facilityGroup"));
			if (facGroup == null) {
				facGroup = ICategoryEntryConstant.FACILITY_GRP_BANKING;
			}
			ILimitTrxValue lmtTrxValue = (ILimitTrxValue) (map.get("lmtTrxObj"));
			ILimit curLimit = this.getLimit(lmtTrxValue, event);
			String countryCode = (String) (map.get("origBookingCtry"));
			
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			
			if(lmtTrxValue.getStagingLimit() != null && lmtTrxValue.getStagingLimit().getFacilityCat() != null){
				if(null!=facCat && !"".equals(facCat) && !lmtTrxValue.getStagingLimit().getFacilityCat().equals(facCat)) {
					facCat=(String) (map.get("facCat"));
				}else {
					facCat = lmtTrxValue.getStagingLimit().getFacilityCat();
				}
			}
			DefaultLogger.debug(this, "PrepareLmtDetailCmd =>limit dropdown issue facCat 165:"+facCat);

			if(lmtTrxValue.getStagingLimit() != null && lmtTrxValue.getStagingLimit().getFacilityName() != null){				
					facName = lmtTrxValue.getStagingLimit().getFacilityName();
			}
			if(lmtTrxValue.getStagingLimit() != null && lmtTrxValue.getStagingLimit().getFacilityCode() != null){				
				facCode = lmtTrxValue.getStagingLimit().getFacilityCode();
			}
			else {
				facCode =  (String) (map.get("facilityCode"));
				System.out.println("facility code inside else "+facCode);
			}
			/*if ((facCat != null) && facName!=null) {
				try {
					List authorisedRiskTypes=getFacilityRiskTypeList(facCat,facName);
					List riskTypeList=getRiskTypeList();
					if(null!=authorisedRiskTypes && null!=riskTypeList) {
					 	for(int i=0;i<riskTypeList.size();i++) {
					 		LabelValueBean lvBean=(LabelValueBean)riskTypeList.get(i);
					 		if(authorisedRiskTypes.contains(lvBean.getValue())) {
					 			LabelValueBean lvBean1 = new LabelValueBean(lvBean.getLabel(), lvBean.getValue());
					 			actualRiskTypeIds.add(lvBean1);
					 		}
					 	}
					}
//					result.put("riskTypeList", actualRiskTypeIds);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}*/
			DefaultLogger.debug(this, "PrepareLmtDetailCmd =>Going for getFacilityRiskTypeList() facCode 216:"+facCode);
			if ((facCode != null) && !"".equals(facCode)) {
				try {
					List authorisedRiskTypes=getFacilityRiskTypeList(facCode);
					List riskTypeList=getRiskTypeList();
					if(null!=authorisedRiskTypes && null!=riskTypeList) {
						DefaultLogger.debug(this, "PrepareLmtDetailCmd =>authorisedRiskTypes and riskTypeList is not null  222:"+facCode);
					 	for(int i=0;i<riskTypeList.size();i++) {
					 		LabelValueBean lvBean=(LabelValueBean)riskTypeList.get(i);
					 		if(authorisedRiskTypes.contains(lvBean.getValue())) {
					 			LabelValueBean lvBean1 = new LabelValueBean(lvBean.getLabel(), lvBean.getValue());
					 			actualRiskTypeIds.add(lvBean1);
					 		}
					 	}
					}
//					result.put("riskTypeList", actualRiskTypeIds);
				} catch (Exception e) {
					DefaultLogger.debug(this, "Exception for fetching risktype list line 232=>e=>"+e);
					System.out.println("Exception for fetching risktype list line 232=>e=>"+e);
					e.printStackTrace();
				}
			}
			
			//DefaultLogger.debug(this, "limit dropdown issue facCat:"+facCat);

			if(facCat == null) {
				//DefaultLogger.debug(this, "limit dropdown issue inside if");
				result.put("facNameList",new ArrayList());
			}else{
				//DefaultLogger.debug(this, "limit dropdown issue inside else");
				result.put("facNameList", getFacName(proxy.getFacNameList(facCat)));
			//	DefaultLogger.debug(this, "limit dropdown issue inside else");
			}

			DefaultLogger.debug(this, "limit dropdown issue facCat 175:"+facCat);
			result.put("subFacNameList", getSubFacNameList(proxy.getSubFacNameList(limitProfileId)));
			if(custId!=null){
				result.put("subPartyNameList", getSubPartyNameList(proxy.getSubPartyNameList(custId)));
			}
			if(event.equals("prepare_cust_update")){
				limitProfileId = String.valueOf(lmtTrxValue.getLimitProfileID());
				custId = String.valueOf(lmtTrxValue.getCustomerID());
			}
			if(lmtTrxValue.getOperationDescField() != null && lmtTrxValue.getOperationDescField().equals("MANUAL_APPROVE_UPDATE")){
				if("prepare_update_rejected".equals(event)) 
					result.put("updateEvent", "prepare_update_rejected");
				else
					result.put("updateEvent", "prepare_update");
			}else{
				result.put("updateEvent", event);
			}
			
				result.put("fundedAmount", map.get("fundedAmount"));
				result.put("nonFundedAmount", map.get("nonFundedAmount"));
				result.put("memoExposer", map.get("memoExposer"));
				result.put("sanctionedLimit", map.get("sanctionedLimit"));
				
				DefaultLogger.debug(this, "In PrepareLmtDetailCmd 198:");
				
				BigDecimal exchangeRate = null;
				BigDecimal sancAmountVal = new BigDecimal(0);
				if(lmtTrxValue.getLimit() != null) {
				if(!AbstractCommonMapper.isEmptyOrNull(lmtTrxValue.getLimit().getRequiredSecurityCoverage()) && !AbstractCommonMapper.isEmptyOrNull(lmtTrxValue.getLimit().getCurrencyCode())){
						 IForexFeedProxy frxPxy = (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
						 exchangeRate = frxPxy.getExchangeRateWithINR(lmtTrxValue.getLimit().getCurrencyCode().trim());
				 }
				if(null != exchangeRate)
				sancAmountVal = exchangeRate.multiply(new BigDecimal(lmtTrxValue.getLimit().getRequiredSecurityCoverage()));
				}
				DefaultLogger.debug(this, "In PrepareLmtDetailCmd 210: before if and for loop");
				if(lmtTrxValue.getLimit() != null && lmtTrxValue.getLimit().getLimitSysXRefs()!=null){
					ILimitSysXRef[] limitSysXRefs = lmtTrxValue.getLimit().getLimitSysXRefs();
					TreeMap sortedMap= new TreeMap();
					for (int i = 0; i < limitSysXRefs.length; i++) {
						ILimitSysXRef iLimitSysXRef = limitSysXRefs[i];
						String serialNo=iLimitSysXRef.getCustomerSysXRef().getSerialNo();
						if(null==serialNo || serialNo.equals("")) {
							serialNo = iLimitSysXRef.getCustomerSysXRef().getHiddenSerialNo();
						}
						if(!"HIDE".equalsIgnoreCase(iLimitSysXRef.getCustomerSysXRef().getStatus()))
						sortedMap.put(iLimitSysXRef.getCustomerSysXRef().getFacilitySystemID()+serialNo, iLimitSysXRef);
					}
					Collection values = sortedMap.values();
					lmtTrxValue.getLimit().setLimitSysXRefs((ILimitSysXRef[])values.toArray(new ILimitSysXRef[values.size()]));
					
					if(lmtTrxValue.getLimit().getRelationShipManager() != null){
						result.put("relationShipMgrName", getRelationshipMgr(lmtTrxValue.getLimit().getRelationShipManager())); //shiv
					}
				}
				DefaultLogger.debug(this, "In PrepareLmtDetailCmd 230 After if and for loop:");
				if(map.get("inrValue") != null && !map.get("inrValue").equals("")){
				//BigDecimal changedValue = new BigDecimal((String)map.get("inrValue"));
					
					//Phase 3 CR:comma separated
					BigDecimal changedValue = new BigDecimal(UIUtil.removeComma((String)map.get("inrValue")));
				
				if(changedValue.compareTo(sancAmountVal) != 0){
					sancAmountVal = changedValue;
				}
				}
				
				result.put("inrValue", sancAmountVal.toString());
				
				if(lmtTrxValue.getStagingLimit() != null && lmtTrxValue.getStagingLimit().getSubPartyName() != null){
					map.put("subPartyName",lmtTrxValue.getStagingLimit().getSubPartyName());
				}
				DefaultLogger.debug(this, "In PrepareLmtDetailCmd 247:");
				if(map.get("subPartyName") != null){
					result.put("liabilityIDList", getLiabilityList(proxy.getLiabilityIDList((String)map.get("subPartyName"))));
				}else{
					result.put("liabilityIDList", new ArrayList());
				}
			IFacilityNewMasterJdbc facilityNewMasterJdbc = (IFacilityNewMasterJdbc) BeanHouse.get("facilityNewMasterJdbc");
			IFacilityNewMaster facMaster = facilityNewMasterJdbc.getFacilityMasterByFacCode(curLimit.getFacilityCode());
			String availAndOptionApplicable = facMaster != null ?facMaster.getAvailAndOptionApplicable():null;
			
			ILimit stagingLimit = lmtTrxValue.getStagingLimit();
			String isCollateralMapped = ICMSConstant.NO;	
			if(stagingLimit != null && stagingLimit.getCollateralAllocations() != null && stagingLimit.getCollateralAllocations().length>0) {
				isCollateralMapped = ICMSConstant.YES ;
			}
				
			DefaultLogger.debug(this, "In PrepareLmtDetailCmd 260:");
			String isGeneralChargeCollateralMapped = ICMSConstant.NO;	
			if(stagingLimit != null && ICMSConstant.YES.equals(stagingLimit.getIsDPRequired()) &&
					ICMSConstant.YES.equals(stagingLimit.getIsDP()) && stagingLimit.getCollateralAllocations() != null) {
				for(ICollateralAllocation limitSecMapping : stagingLimit.getCollateralAllocations() ) {
					if(limitSecMapping.getCollateral().getCollateralSubType() != null && 
							ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE.equals(limitSecMapping.getCollateral().getCollateralSubType().getSubTypeCode()) ) {
						isGeneralChargeCollateralMapped = ICMSConstant.YES ;
						break;
					}
				}
			}
				
			result.put("relationShipMngrList", getRelationShipMngrList(proxy.getRelationShipMngrList()));
			String returnUrl = getReturnUrl(lmtTrxValue, limitProfileId, isCreate, custId, event);
			result.put("orgList", getOrgList(countryCode, team));
			result.put("outerLmtList", getOuterLimitList(limitProfileId));
			result.put("facilityTypeList", getFacilityType(facGroup, agreementType, user));
			result.put("currencyList", getCurrencyList());
			result.put("timeFreqList", getTimeFreqList());
			result.put("loanTypeList", getLoanTypeList());
			result.put("lmtSecRelnshipList", getLmtSecRelnshipList());
			result.put("returnUrl", returnUrl);
			result.put("facDetailList", new ArrayList());
			if(actualRiskTypeIds!=null || !actualRiskTypeIds.isEmpty()) {
				result.put("riskTypeList", actualRiskTypeIds);
			}else {
				result.put("riskTypeList", new ArrayList());
			}

			result.put("status", lmtTrxValue.getStatus());
			DefaultLogger.debug(this, "In PrepareLmtDetailCmd 273:");
			result.put("lmtTrxObj", lmtTrxValue);
		    result.put("limitProfileID", limitProfileId);

			if(availAndOptionApplicable !=null && !availAndOptionApplicable.isEmpty())
				result.put("availAndOptionApplicable", availAndOptionApplicable);
			else
				result.put("availAndOptionApplicable", availAndOptionApplicable2);
//			result.put("isLoanOptionAvailable", availAndOptionApplicable3);

			String facCoBorrowerLiabIds =  (String) map.get("facCoBorrowerLiabIds");
			System.out.println("facCoBorrowerLiabIds in JAVA CMD:::::"+facCoBorrowerLiabIds);
			result.put("facCoBorrowerLiabIds", facCoBorrowerLiabIds);
			
			
			
			List<String> selectedCoBorrowerIds = UIUtil.getListFromDelimitedString(facCoBorrowerLiabIds, ",");

			ILimitDAO limit = LimitDAOFactory.getDAO();
			List facCoBorrowerList = (List) map.get("facCoBorrowerList");
			long custId1 = Long.parseLong(custId);
			List partyCoBorrowerList = limit.getPartyCoBorrowerDetails(custId1);

		//	List facCoBorrowerList =curLmt.getCoBorrowerDetails();
			System.out.println("facCoBorrowerList@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@in JAVA CMD=="+partyCoBorrowerList);
		//	result.put("facCoBorrowerList", facCoBorrowerList);
			
			List<IFacilityCoBorrowerDetails> facCoBorrowerListNew = new ArrayList<IFacilityCoBorrowerDetails>();

			System.out.println("facCoBorrowerList in JAVA CMD:::::"+partyCoBorrowerList);
			DefaultLogger.debug(this, "In PrepareLmtDetailCmd 297 before if and for loop for selectedCoBorrowerIds:");
		if(null != selectedCoBorrowerIds && !selectedCoBorrowerIds.isEmpty() ) {
			DefaultLogger.debug(this, "In PrepareLmtDetailCmd 299 inside if selectedCoBorrowerIds.size:"+selectedCoBorrowerIds.size()+" .. partyCoBorrowerList.size()=>"+partyCoBorrowerList.size());
		  for(int i=0; i<selectedCoBorrowerIds.size(); i++) {
			
			 for(int j=0; j<partyCoBorrowerList.size(); j++) {
				 IFacilityCoBorrowerDetails facCoBorrower = new OBFacilityCoBorrowerDetails();
				 ICoBorrowerDetails partyCoBorrower = new OBCoBorrowerDetails();
					
			//	System.out.println("partyCoBorrowerList###################::::"+partyCoBorrowerList.get(j).toString());
			//	System.out.println("facCoBorrowerList###################::::"+partyCoBorrowerList.get(j).toString());

				partyCoBorrower= (ICoBorrowerDetails) partyCoBorrowerList.get(j);
				String liabId= partyCoBorrower.getCoBorrowerLiabId();
				String borroName= partyCoBorrower.getCoBorrowerName();
			//	String[] borroNm= borroName.split("-");
			//	borroName=borroNm[1];
				if(selectedCoBorrowerIds.get(i).equalsIgnoreCase(liabId ) ) {
					
				//	System.out.println("ssssssssssssssssssssss liabId!!!!!!!!!!!!!!!!!!!!!!"+liabId+" borroName"+borroName);

					facCoBorrower.setCoBorrowerLiabId(liabId);
					facCoBorrower.setCoBorrowerName(borroName);
					facCoBorrowerListNew.add(facCoBorrower);
				}
			}
			
			}
			result.put("facCoBorrowerList", facCoBorrowerListNew);
			result.put("isGeneralChargeCollateralMapped", isGeneralChargeCollateralMapped);
		}
			else {
				DefaultLogger.debug(this, "In PrepareLmtDetailCmd 328 inside else .. selectedCoBorrowerIds:");
				result.put("facCoBorrowerList", facCoBorrowerList);
			}
		DefaultLogger.debug(this, "In PrepareLmtDetailCmd 330 after if and for loop for selectedCoBorrowerIds:");

		ILimitSysXRef[] limitSysXRefs1 =null;
		Set <String> cbHashset=new HashSet<String>();
		String liabId="";
		if(lmtTrxValue.getStagingLimit() != null && lmtTrxValue.getStagingLimit().getLimitSysXRefs()!=null) {
			limitSysXRefs1 = lmtTrxValue.getStagingLimit().getLimitSysXRefs();
		
			for (int i = 0; i < limitSysXRefs1.length; i++) {
			//	ILimitSysXRef iLimitSysXRef1 = limitSysXRefs1[i];	
			
			ILimitXRefCoBorrower[] coborroObj=	limitSysXRefs1[i].getCustomerSysXRef().getXRefCoBorrowerData();
					if(null != coborroObj ) {
						for (int j = 0; j < coborroObj.length; j++) {
							liabId =coborroObj[j].getCoBorrowerId();
							cbHashset.add(liabId);
						}	
					}
			}
		}
		DefaultLogger.debug(this, "In PrepareLmtDetailCmd 351 after if and for loop for limitSysXRefs1:");
		List<String> LineCoBorrowerIdList = new ArrayList<String>();

		if (cbHashset != null && !cbHashset.isEmpty()) {
            for (String borrowId : cbHashset) {
            	LineCoBorrowerIdList.add(borrowId);
            }
       }
		String lineCBId = UIUtil.getJSONStringFromList(LineCoBorrowerIdList, ",");

		lineCBId = lineCBId==null ? "" : lineCBId ;
	//	System.out.println("IN SaveXrefDetailCmd ssssssssssssssssssss lineCBId==============="+lineCBId);
		result.put("LineCoBorrowIds", lineCBId);
	

			result.put("isCollateralMapped", isCollateralMapped);

		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
			
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		DefaultLogger.debug(this, "Out PrepareLmtDetailCmd 374 Done.");
		return temp;
	}

	private String getReturnUrl(ILimitTrxValue lmtTrxValue, String limitProfileId, String isCreate, String custId, String event) {
		if (ICMSConstant.STATE_DRAFT.equals(lmtTrxValue.getStatus())
				|| ICMSConstant.STATE_REJECTED.equals(lmtTrxValue.getStatus())) {
			String returnUrl = "ToDo.do";
			return returnUrl;
		}
		else {
			String returnUrl = "";
			if ("Y".equals(isCreate)) {
				returnUrl = "MICustomerSearch.do?event=list_customer&source=limit";
			}
			else if(event.equals("prepare_cust_update")) {
				returnUrl = "MILimitList.do?event=show_lmt_cust_detail&limitId="+lmtTrxValue.getLimit().getLimitID() + "&limitProfileID=" + limitProfileId;
			}else {
				//String limitId = "" + lmtTrxValue.getLimit().getLimitID();
				returnUrl = "MILimitList.do?event=list_limit&limitProfileID=" + limitProfileId + "&source=manualinput" 
				+ "&customerID=" + custId;
							
			}
			return returnUrl;
		}
	}

	

	private List getOrgList(String country, ITeam team) {
//		System.out.println("RETRIEVING ORGANISATION************** COUNTRY CODE" + country);
		List lbValList = new ArrayList();
		ArrayList idList = new ArrayList();
		ArrayList valList = new ArrayList();
		if ((country != null) && !country.trim().equals("")) {
			HashMap map = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.CATEGORY_CODE_BKGLOC, null,
					country);
			Object[] keyArr = map.keySet().toArray();
			for (int i = 0; i < keyArr.length; i++) {
				Object nextKey = keyArr[i];
				boolean allowAdd = false;
				if (team.getOrganisationCodes() != null) {
					String[] orgCodes = team.getOrganisationCodes();
					for (int j = 0; j < orgCodes.length; j++) {
						if (orgCodes[j].equals(nextKey.toString())) {
							allowAdd = true;
							break;
						}
					}
				}
				if (allowAdd) {
					LabelValueBean lvBean = new LabelValueBean(map.get(nextKey).toString() + " (" + nextKey.toString()
							+ ")", nextKey.toString());
					lbValList.add(lvBean);
				}
			}
		}
		return CommonUtil.sortDropdown(lbValList);
//		return lbValList;
	}

	private List getProductGroup() {
		List lbValList = new ArrayList();
		return lbValList;
	}

	private List getFacilityType(String facilityGrp, String agreementType, ICommonUser user) {
		List lbValList = new ArrayList();
		HashMap map = new HashMap();
		if (ICategoryEntryConstant.FACILITY_GRP_TRADING.equals(facilityGrp)) {
			map = CommonDataSingleton.getCodeCategoryValueLabelMap(ICategoryEntryConstant.FACILITY_TYPE_TRADE,
					agreementType);

		}
		else if (ICategoryEntryConstant.FACILITY_GRP_BANKING.equals(facilityGrp)) {
			String userCountry = user.getCountry();
			// map = CommonDataSingleton.getCodeCategoryValueLabelMap(
			// ICategoryEntryConstant.FACILITY_DESCRIPTION, null, userCountry);
			map = CommonDataSingleton.getCodeCategoryValueLabelMap(ICategoryEntryConstant.FACILITY_DESCRIPTION, null,
					null);
		}
		Object[] keyArr = map.keySet().toArray();
		for (int i = 0; i < keyArr.length; i++) {
			Object nextKey = keyArr[i];
			LabelValueBean lvBean = new LabelValueBean(map.get(nextKey).toString(), nextKey.toString());
			lbValList.add(lvBean);
		}
		return CommonUtil.sortDropdown(lbValList);
//		return lbValList;
	}

	private List getLoanTypeList() {
		List lbValList = new ArrayList();
		HashMap map = CommonDataSingleton.getCodeCategoryValueLabelMap("LOAN_TYPE");
		Object[] keyArr = map.keySet().toArray();
		for (int i = 0; i < keyArr.length; i++) {
			Object nextKey = keyArr[i];
			LabelValueBean lvBean = new LabelValueBean(map.get(nextKey).toString(), nextKey.toString());
			lbValList.add(lvBean);
		}
		return CommonUtil.sortDropdown(lbValList);
//		return lbValList;
	}

	private List getTimeFreqList() {
		List lbValList = new ArrayList();
		HashMap map = CommonDataSingleton.getCodeCategoryValueLabelMap("63");
		Object[] keyArr = map.keySet().toArray();
		for (int i = 0; i < keyArr.length; i++) {
			Object nextKey = keyArr[i];
			LabelValueBean lvBean = new LabelValueBean(map.get(nextKey).toString(), nextKey.toString());
			lbValList.add(lvBean);
		}
		return CommonUtil.sortDropdown(lbValList);
//		return lbValList;
	}

	private List getOuterLimitList(String limitProfileId) {
		List lbValList = new ArrayList();
		MILimitUIHelper helper = new MILimitUIHelper();
		try {
			List tempList = helper.getSBMILmtProxy().getOuterLimitList(limitProfileId);
			if (tempList != null) {
				for (int i = 0; i < tempList.size(); i++) {
					String[] arr = (String[]) (tempList.get(i));
					LabelValueBean lvBean = new LabelValueBean(arr[1], arr[0]);
					lbValList.add(lvBean);
				}
			}
		}
		catch (Exception ex) {
		}
		return lbValList;
	}

	private List getLmtSecRelnshipList() {
		List lbValList = new ArrayList();
		Collection relnshipValues = CommonDataSingleton.getCodeCategoryValues(ICMSConstant.CATEGORY_CODE_LIMIT_SECURED);
		Collection relnshipLabels = CommonDataSingleton.getCodeCategoryLabels(ICMSConstant.CATEGORY_CODE_LIMIT_SECURED);

		Iterator labelIter = relnshipLabels.iterator();
		Iterator valueIter = relnshipValues.iterator();

		while (labelIter.hasNext()) {
			String id = (String) labelIter.next();
			String val = (String) valueIter.next();
			LabelValueBean lvBean = new LabelValueBean(id, val);
			lbValList.add(lvBean);

		}
		return CommonUtil.sortDropdown(lbValList);
//		return lbValList;
	}

	private ILimit getLimit(ILimitTrxValue trxValue, String event) {
		if (trxValue == null) {
			return null;
		}
		if (EventConstant.EVENT_READ.equals(event)) {
			return trxValue.getLimit();
		}
		else {
			return trxValue.getStagingLimit();
		}
	}
	
	//Shiv
	
	private List getFacName(List lst) {
		//DefaultLogger.debug(this, "limit dropdown issue inside getFacName");
		List lbValList = new ArrayList();	
		OBLimit ob = null;
		for (int i = 0; i < lst.size(); i++) {						
				ob = (OBLimit)lst.get(i);
				//DefaultLogger.debug(this, "limit dropdown issue  ob.getFacilityName():"+ob.getFacilityName());
				LabelValueBean lvBean = new LabelValueBean(ob.getFacilityName(),ob.getFacilityName() );
				lbValList.add(lvBean);
		}
				
		return CommonUtil.sortDropdown(lbValList);
//		return lbValList;
	}
	
	private List getRelationShipMngrList(List lst) {
		List lbValList = new ArrayList();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] mgnrLst = (String[])lst.get(i);
				LabelValueBean lvBean = new LabelValueBean(mgnrLst[1],mgnrLst[0] );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
//	return CommonUtil.sortDropdown(lbValList);
	return lbValList;
}
	
	private List getSubFacNameList(List lst) {
		List lbValList = new ArrayList();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] mgnrLst = (String[])lst.get(i);
				LabelValueBean lvBean = new LabelValueBean(mgnrLst[0],mgnrLst[1] );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return CommonUtil.sortDropdown(lbValList);
//	return lbValList;
}

	private List getCurrencyList() {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				IForexFeedEntry[] currency = CollateralDAOFactory.getDAO().getCurrencyList();
				
				if (currency != null) {
					for (int i = 0; i < currency.length; i++) {
						IForexFeedEntry lst = currency[i];
//						String id = lst.getCurrencyIsoCode().trim();
						String id = lst.getBuyCurrency().trim();
						String value = lst.getCurrencyIsoCode().trim();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
//		return lbValList;
	}
	
	private List getSubPartyNameList(List lst) {
		List lbValList = new ArrayList();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] mgnrLst = (String[])lst.get(i);
				LabelValueBean lvBean = new LabelValueBean(mgnrLst[1],mgnrLst[2] );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return CommonUtil.sortDropdown(lbValList);
//	return lbValList;
}
	
	public String getRelationshipMgr(String relMgrId) {
		IRelationshipMgrDAO relationshipmgr = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
		String	strRelationshipmgr = "";
		if(relMgrId != null && !relMgrId.equals("")){
			strRelationshipmgr = relationshipmgr.getRelationshipMgrByCode(relMgrId).getRelationshipMgrName();
		}
		return strRelationshipmgr;
	}
	
	private List getLiabilityList(List lst) {
		List lbValList = new ArrayList();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] mgnrLst = (String[])lst.get(i);
				LabelValueBean lvBean = new LabelValueBean(mgnrLst[0]+" - "+mgnrLst[1],mgnrLst[0]+" - "+mgnrLst[1] );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
//	return CommonUtil.sortDropdown(lbValList);
	return lbValList;
}

	private List getRiskTypeList() throws Exception {
		
		List<LabelValueBean> lbValList = new ArrayList<LabelValueBean>();
		IRiskTypeProxyManager riskTypeProxy = (IRiskTypeProxyManager)BeanHouse.get("riskTypeProxy");
		SearchResult riskTypeList= (SearchResult) riskTypeProxy.getAllActualRiskType();
		Iterator itr = riskTypeList.getResultList().iterator();
		
		while(itr.hasNext()) {
			IRiskType riskType = (IRiskType) itr.next();
			LabelValueBean lvBean = new LabelValueBean(riskType.getRiskTypeName(),riskType.getRiskTypeCode());
			lbValList.add(lvBean);
		}
		
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getFacilityRiskTypeList(String facilityCode) throws Exception {
		
		IFacilityNewMasterDao facilityNewMasterDao = (IFacilityNewMasterDao)BeanHouse.get("facilityNewMasterDao");
		IFacilityNewMaster res= facilityNewMasterDao.getFacilityNewMasterRiskTypeWithFacCode(facilityCode);
		String selectedRiskTypes=res.getSelectedRiskTypes();
		if(selectedRiskTypes!=null) {
			List<String> authorisedRiskTypes = Arrays.asList(selectedRiskTypes.split(","));
			return authorisedRiskTypes;
		}else {
			return null;
		}
	}
}