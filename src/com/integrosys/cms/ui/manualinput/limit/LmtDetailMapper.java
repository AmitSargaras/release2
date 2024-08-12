/*
 * Created on 2007-2-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import static com.integrosys.cms.ui.manualinput.IManualInputConstants.CO_BORROWER_LIST;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import com.integrosys.cms.app.limit.bus.IFacilityCoBorrowerDetails;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.IFacilityCoBorrowerDetails;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitListSecItemBase;
import com.integrosys.cms.app.limit.bus.OBCollateralAllocation;
import com.integrosys.cms.app.limit.bus.OBFacilityCoBorrowerDetails;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.host.eai.limit.support.LimitHelper.CoBorrowerSystems;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LmtDetailMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile", GLOBAL_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "itemType", "java.lang.String", REQUEST_SCOPE },
				{ CO_BORROWER_LIST, List.class.getName(), SERVICE_SCOPE },
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.IMapper#mapOBToForm(com.integrosys
	 * .base.uiinfra.common.CommonForm, java.lang.Object, java.util.HashMap)
	 */
	private static final String DATE_FORMAT = "dd/MMM/yyyy";
	public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs) throws MapperException {
		// TODO Auto-generated method stub
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {
			String event = (String) (inputs.get("event"));
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) (inputs.get("lmtTrxObj"));
			String limitProfileID = (String) (inputs.get("limitProfileID"));
			MILimitUIHelper helper = new MILimitUIHelper();
			ILimit lmtObj = (ILimit) obj;
			
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			List lmtList = proxy.getLimitSummaryListByAA(limitProfileID);
			List lmtListFormated = helper.formatLimitListView(lmtList, locale);
			
			
			for(int i = 0; i < lmtListFormated.size(); i++){
				LimitListSummaryItem lstSummaryItem = (LimitListSummaryItem) lmtListFormated.get(i);
				List secList = lstSummaryItem.getSecItemList();
				if ((secList != null)) {
					for(int j = 0; j < secList.size(); j++ ){
						LimitListSecItemBase secItem = (LimitListSecItemBase) (secList.get(j));
						if(lmtObj.getCollateralAllocations() != null){
						for(int k = 0; k < lmtObj.getCollateralAllocations().length; k++){
							//if (lmtObj.getCollateralAllocations()[k] != null){
							if(secItem.getSecurityId().equals(String.valueOf(lmtObj.getCollateralAllocations()[k].getCollateral().getCollateralID()))){
								if(!event.equals("process")){
									if(secItem.getLmtSecurityCoverage() == null || secItem.getLmtSecurityCoverage().equals("")){
										secItem.setLmtSecurityCoverage("0");
									}
								}	
							}
							//}
						 }
						}
					}
				}
			}
			
			if(event.equals("update_return")){
				if(lmtObj.getCollateralAllocations() != null ){
				for(int k = 0; k < lmtObj.getCollateralAllocations().length; k++){
					if(lmtObj.getCollateralAllocations()[k].getCollateral() != null){
					
					String secType = "";
					if(lmtObj.getCollateralAllocations()[k].getCollateral().getCollateralSubType() != null) {
						secType = lmtObj.getCollateralAllocations()[k].getCollateral().getCollateralSubType().getTypeName();
					}
						
					String secId = String.valueOf(lmtObj.getCollateralAllocations()[k].getCollateral().getCollateralID());
					String secCov= lmtObj.getCollateralAllocations()[k].getLmtSecurityCoverage();
					System.out.print("====LmtDetailMapper==140=======Security Coverage : "+lmtObj.getCollateralAllocations()[k].getLmtSecurityCoverage());

					float secCovVal = 0;
					boolean checkSec = false;
					
					if(lmtListFormated.size() == 0){
						lmtObj.getCollateralAllocations()[k].setLmtSecurityCoverage("100");
					}		
					for(int i = 0; i < lmtListFormated.size(); i++){
						LimitListSummaryItem lstSummaryItem = (LimitListSummaryItem) lmtListFormated.get(i);
						List secList = lstSummaryItem.getSecItemList();
						if ((secList != null)) {
							for(int j = 0; j < secList.size(); j++ ){
								LimitListSecItemBase secItem = (LimitListSecItemBase) (secList.get(j));
								if(secCov == null || secCov.equals("") || secCov.equals("0")){
									if(secId.equals(secItem.getSecurityId())){
										if(secItem.getLmtSecurityCoverage()==null || secItem.getLmtSecurityCoverage().equals("")){
											secItem.setLmtSecurityCoverage("0");
										}
										System.out.print("====LmtDetailMapper==159=======secCovVal : "+secCovVal+" secItem.getLmtSecurityCoverage() : "+secItem.getLmtSecurityCoverage());
										secCovVal = secCovVal + Float.parseFloat(secItem.getLmtSecurityCoverage());
										checkSec = true;
									}
									System.out.print("====LmtDetailMapper==163===after setting values====secCovVal : "+secCovVal+" secItem.getLmtSecurityCoverage() : "+secItem.getLmtSecurityCoverage());
//									if(secType.equalsIgnoreCase("Property")){
//										String strPropertValue = proxy.getRemainingPropertyValue();									
//									}
									lmtObj.getCollateralAllocations()[k].setLmtSecurityCoverage("100");
									System.out.print("====LmtDetailMapper==168=======Security Coverage : "+lmtObj.getCollateralAllocations()[k].getLmtSecurityCoverage());
								}
							}
						}
						
					}
					
					System.out.print("====LmtDetailMapper==175=======checkSec : "+checkSec);
					if(checkSec) {
						secCovVal = 100 - secCovVal;
						lmtObj.getCollateralAllocations()[k].setLmtSecurityCoverage(String.valueOf(secCovVal));
						checkSec = false;
						System.out.print("====LmtDetailMapper==180=======Security Coverage : "+lmtObj.getCollateralAllocations()[k].getLmtSecurityCoverage());
					}
				}
				}
			}
			}
			
			
			 NumberFormat formatterDisplay = new DecimalFormat("#,###,###.##"); //Shiv 160911
			
			LmtDetailForm lmtDetailForm = (LmtDetailForm) commonForm;
			if (lmtObj.getLimitID() != ICMSConstant.LONG_INVALID_VALUE) {
				lmtDetailForm.setLimitId(String.valueOf(lmtObj.getLimitID()));
			}
			mapAgreementType(lmtDetailForm, helper, event, limitProfileID);
			mapFacilityGroup(lmtDetailForm, helper, event, limitProfileID);
			String lmtRef = lmtObj.getLimitRef();
			if ((lmtRef != null) && (lmtObj.getSourceId() != null)) {
				lmtRef = lmtRef
						+ " - "
						+ CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.CATEGORY_SOURCE_SYSTEM, lmtObj
								.getSourceId());
			}
			lmtDetailForm.setLimitRef(lmtRef);
			lmtDetailForm.setLimitExpiryDate(DateUtil.convertToDisplayDate(lmtObj.getLimitExpiryDate()));
			lmtDetailForm.setLimitDesc(lmtObj.getLimitDesc());
			lmtDetailForm.setOrigBookingCtry(lmtObj.getBookingLocation().getCountryCode());
			lmtDetailForm.setOrigBookingLoc(lmtObj.getBookingLocation().getOrganisationCode());
			lmtDetailForm.setLimitType(lmtObj.getLimitType());
			lmtDetailForm.setOuterLmtRef(lmtObj.getOuterLimitRef());
			lmtDetailForm.setOuterLmtId(String.valueOf(lmtObj.getOuterLimitID()));
			// lmtDetailForm.setFacilityType(lmtObj.getFacilityDesc());
			lmtDetailForm.setFacilityType(lmtObj.getProductDesc());
			lmtDetailForm.setRiskType(lmtObj.getRiskType());
			// lmtDetailForm.setProdType(lmtObj.getProductDesc());
			if ((lmtObj.getApprovedLimitAmount() != null)
					&& (lmtObj.getApprovedLimitAmount().getCurrencyCode() != null)) {
				if (lmtObj.getApprovedLimitAmount().getAmount() >= 0) {
					lmtDetailForm.setApprovedLmt(CurrencyManager.convertToString(Locale.US, lmtObj
							.getApprovedLimitAmount()));
				}
				lmtDetailForm.setApprovedCurrency(lmtObj.getApprovedLimitAmount().getCurrencyCode());
			}
			
			//lmtDetailForm.setRequiredSecCov(lmtObj.getRequiredSecurityCoverage());
			
			//Phase 3 CR:comma separated
			lmtDetailForm.setRequiredSecCov(UIUtil.formatWithCommaAndDecimal(lmtObj.getRequiredSecurityCoverage()));
				
			if ((lmtObj.getLimitTenor() != null) && (lmtObj.getLimitTenor().longValue() != 0)) {
				lmtDetailForm.setLmtTenor(String.valueOf(lmtObj.getLimitTenor()));
			}
			lmtDetailForm.setLmtTenorBasis(lmtObj.getLimitTenorUnit());
			if (lmtObj.getLimitAdviseInd()) {
				lmtDetailForm.setLmtAdvisedInd("Y");
			}
			else {
				lmtDetailForm.setLmtAdvisedInd("N");
			}
			lmtDetailForm.setLoanType(lmtObj.getLoanType());

			lmtDetailForm.setOrigBookingCtryDesc(helper.getOrigBookingCtryDesc(lmtObj.getBookingLocation()
					.getCountryCode()));
			lmtDetailForm.setOrigBookingLocDesc(helper.getOrigBookingLocDesc(lmtObj.getBookingLocation()
					.getCountryCode(), lmtObj.getBookingLocation().getOrganisationCode()));
			lmtDetailForm.setFacilityTypeDesc(helper.getProductDesc(lmtObj.getProductDesc()));
			lmtDetailForm.setLmtTenorBasisDesc(helper.getLmtTenorBasisDesc(lmtObj.getLimitTenorUnit()));
			lmtDetailForm.setLoanTypeDesc(helper.getLoanTypeDisp(lmtObj.getLoanType()));

			if (lmtObj.getLimitSecuredType() == null) {
				lmtDetailForm.setLmtSecRelationship(ICMSConstant.LIMIT_SECURED_TYPE_SECURED);
			}
			else {
				lmtDetailForm.setLmtSecRelationship(lmtObj.getLimitSecuredType());
			}
			if (helper.checkShowSublist(inputs)) {
				lmtDetailForm.setShowSublist("Y");
			}
			else {
				lmtDetailForm.setShowSublist(null);
			}

			if (!EventConstant.EVENT_PROCESS.equals(event) && !EventConstant.EVENT_PROCESS_RETURN.equals(event)) {
				renderAcntRefSummary(lmtObj, lmtDetailForm, helper);
				renderLmtSecSummary(lmtObj, lmtDetailForm, helper);
			}
			else {
				renderCompareAcntRef(lmtTrxObj.getLimit(), lmtTrxObj.getStagingLimit(), lmtDetailForm, helper);
				renderCompareLmtSec(lmtTrxObj.getLimit(), lmtTrxObj.getStagingLimit(), lmtDetailForm, helper);
			}
			
			//Shiv
			lmtDetailForm.setFacilityName(lmtObj.getFacilityName());
			
			lmtDetailForm.setFacilityCode(lmtObj.getFacilityCode());
			
			lmtDetailForm.setFacilityType(lmtObj.getFacilityType());
			
			lmtDetailForm.setFacilitySystem(lmtObj.getFacilitySystem());
			System.out.println("lmtObj.getFacilitySystemID() : "+lmtObj.getFacilitySystemID());
			lmtDetailForm.setFacilitySystemID(lmtObj.getFacilitySystemID());
			
			if(null != lmtObj.getLineNo()) {
			lmtDetailForm.setLineNo(lmtObj.getLineNo());
			}
			lmtDetailForm.setPurpose(lmtObj.getPurpose());
			
			lmtDetailForm.setOtherPurpose(lmtObj.getOtherPurpose());
			
			if("ET".equals(lmtObj.getFacilitySystem())) {
				lmtDetailForm.setIsDP("Y");
			}else {
				lmtDetailForm.setIsDP(lmtObj.getIsDP());
			}
			
			if(EventConstant.EVENT_READ.equalsIgnoreCase(event)&&lmtObj.getRelationShipManager()!=null)
			{
				lmtDetailForm.setRelationShipManager(getRelationshipMgr(lmtObj.getRelationShipManager()).getRelationshipMgrName());
			}
			else
			{
				lmtDetailForm.setRelationShipManager(lmtObj.getRelationShipManager());
			}
			
			lmtDetailForm.setGuarantee(lmtObj.getGuarantee());
			
			if(lmtObj.getIsReleased().equalsIgnoreCase("N")){
				lmtDetailForm.setIsReleased("N");
			}else{
				lmtDetailForm.setIsReleased("Y");
			}
			
			if(lmtObj.getIsSecured().equalsIgnoreCase("N")){
				lmtDetailForm.setIsSecured("N");
			}else{
				lmtDetailForm.setIsSecured("Y");
			}
			
			lmtDetailForm.setGrade(lmtObj.getGrade());
			
			if(lmtObj.getIsAdhoc().equalsIgnoreCase("N")){
				lmtDetailForm.setIsAdhoc("N");
			}else{
				lmtDetailForm.setIsAdhoc("on");
			}
			
			if(lmtObj.getIsAdhocToSum().equalsIgnoreCase("N")){
				lmtDetailForm.setIsAdhocToSum("N");
			}else{
				lmtDetailForm.setIsAdhocToSum("on");
			}
			
			lmtDetailForm.setFacilityCat(lmtObj.getFacilityCat());
			
			lmtDetailForm.setCurrencyCode(lmtObj.getCurrencyCode());
			

			BigDecimal exchangeRate = null;
			if(!AbstractCommonMapper.isEmptyOrNull(lmtObj.getRequiredSecurityCoverage()) && !AbstractCommonMapper.isEmptyOrNull(lmtObj.getCurrencyCode())){
					 IForexFeedProxy frxPxy = (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
					 exchangeRate = frxPxy.getExchangeRateWithINR(lmtObj.getCurrencyCode().trim());
					 BigDecimal sancAmountVal = new BigDecimal(0);
						sancAmountVal = exchangeRate.multiply(new BigDecimal(lmtObj.getRequiredSecurityCoverage()));
						lmtDetailForm.setInrValue(sancAmountVal.toString());
			 }
			if(exchangeRate == null) {
				lmtDetailForm.setInrValue("");
			}
			
//			lmtDetailForm.setReleasableAmount(lmtObj.getReleasableAmount());
//			lmtDetailForm.setAdhocLmtAmount(lmtObj.getAdhocLmtAmount());
//			lmtDetailForm.setTotalReleasedAmount(lmtObj.getTotalReleasedAmount());
			
			//Phase 3 CR:comma separated
			lmtDetailForm.setReleasableAmount(UIUtil.formatWithCommaAndDecimal(lmtObj.getReleasableAmount()));
			lmtDetailForm.setAdhocLmtAmount(UIUtil.formatWithCommaAndDecimal(lmtObj.getAdhocLmtAmount()));
			lmtDetailForm.setTotalReleasedAmount(UIUtil.formatWithCommaAndDecimal(lmtObj.getTotalReleasedAmount()));
			
			lmtDetailForm.setGuarantee(lmtObj.getGuarantee());
			
			lmtDetailForm.setAdhocFacility(lmtObj.getAdhocFacility());
			//lmtDetailForm.setAdhocLastAvailDate(lmtObj.getAdhocLastAvailDate());
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MMM/yyyy");
			if (null != lmtObj.getAdhocLastAvailDate())
			{lmtDetailForm.setAdhocLastAvailDate(sdf1.format(lmtObj.getAdhocLastAvailDate()));
			}
	
			lmtDetailForm.setMultiDrawdownAllow(lmtObj.getMultiDrawdownAllow());
			lmtDetailForm.setAdhocTenor(lmtObj.getAdhocTenor());
			//lmtDetailForm.setAdhocFacilityExpDate(lmtObj.getAdhocFacilityExpDate());
		//	SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MMM/yyyy");
			if (null !=  lmtObj.getAdhocFacilityExpDate())
			{
				lmtDetailForm.setAdhocFacilityExpDate(sdf1.format(lmtObj.getAdhocFacilityExpDate()));
			}
			lmtDetailForm.setGeneralPurposeLoan(lmtObj.getGeneralPurposeLoan());
			
			lmtDetailForm.setSubPartyName(lmtObj.getSubPartyName());
			lmtDetailForm.setSubFacilityName(lmtObj.getSubFacilityName());
			lmtDetailForm.setLiabilityID(lmtObj.getLiabilityID());
			
			lmtDetailForm.setMainFacilityId(lmtObj.getMainFacilityId());
			lmtDetailForm.setDeletedLmtSec("");
			lmtDetailForm.setDeletedActRef(new String[0]);
			lmtDetailForm.setIsFromCamonlineReq(lmtObj.getIsFromCamonlineReq());
			if(null != lmtObj.getSyndicateLoan() && lmtObj.getSyndicateLoan().equalsIgnoreCase("Y")){
				lmtDetailForm.setSyndicateLoan("Yes");
			}else if(null != lmtObj.getSyndicateLoan() && lmtObj.getSyndicateLoan().equalsIgnoreCase("N")){
				lmtDetailForm.setSyndicateLoan("No");
			}
			
			lmtDetailForm.setLimitRemarks(lmtObj.getLimitRemarks());
			
			if(null != lmtObj.getPurposeBoolean() && lmtObj.getPurposeBoolean().equalsIgnoreCase("Y")){
				lmtDetailForm.setPurposeBoolean("Yes");
			}else if(null != lmtObj.getPurposeBoolean() && lmtObj.getPurposeBoolean().equalsIgnoreCase("N")){
				lmtDetailForm.setPurposeBoolean("No");
			}
			
			setChangedInd(event, lmtTrxObj.getLimit(), lmtTrxObj.getStagingLimit(), lmtDetailForm);
			String scodLineNoList=PropertyManager.getValue("scod.linenocode.name");
			String lineNo = "";
			if(null != lmtObj.getLineNo()) {
				lineNo=lmtObj.getLineNo();
			}else {
				lineNo="";
			}
			if(scodLineNoList != null) {
				boolean scodB = false;
				if(scodLineNoList != null && !scodLineNoList.equals("")) {
				String[] scodlinelist = scodLineNoList.split(",");
				for(int i=0; i< scodlinelist.length; i++ ) {
					if(lmtDetailForm.getLineNo()!=null && lmtDetailForm.getLineNo().equals(scodlinelist[i])) {
						scodB = true;
					}
				}
				}
	    	if(scodB == true || lmtObj.getLineNo() == null){ 
			if("".equals(lmtObj.getExstAssetClassL1()) || lmtObj.getExstAssetClassL1() == null) {
			lmtDetailForm.setExstAssetClassL1("1");
			}else {
				lmtDetailForm.setExstAssetClassL1(lmtObj.getExstAssetClass());	
			}
		
			lmtDetailForm.setExstAssClassDateL1(DateUtil.formatDate(locale,lmtObj.getExstAssClassDateL1()));
			
			if("".equals(lmtObj.getExstAssetClassL2()) || lmtObj.getExstAssetClassL2() == null) {
			lmtDetailForm.setExstAssetClassL2("1");
			}else {
				lmtDetailForm.setExstAssetClassL2(lmtObj.getExstAssetClass());
			}

			lmtDetailForm.setExstAssClassDateL2(DateUtil.formatDate(locale,lmtObj.getExstAssClassDateL2()));
					
			if("".equals(lmtObj.getExstAssetClassL3()) || lmtObj.getExstAssetClassL3() == null) {
			lmtDetailForm.setExstAssetClassL3("1");
			}else {
				lmtDetailForm.setExstAssetClassL3(lmtObj.getExstAssetClass());
			}
			
			lmtDetailForm.setExstAssClassDateL3(DateUtil.formatDate(locale,lmtObj.getExstAssClassDateL3()));


			lmtDetailForm.setRevisedAssetClassL1(lmtObj.getRevisedAssetClassL1());
			
			
			lmtDetailForm.setRevsdAssClassDateL1(DateUtil.formatDate(locale,lmtObj.getRevsdAssClassDateL1()));
			
			
			lmtDetailForm.setRevisedAssetClass_L2(lmtObj.getRevisedAssetClass_L2());
			

			lmtDetailForm.setRevsdAssClassDate_L2(DateUtil.formatDate(locale,lmtObj.getRevsdAssClassDate_L2()));
			
						
			lmtDetailForm.setRevisedAssetClass_L3(lmtObj.getRevisedAssetClass_L3());
			
			
			lmtDetailForm.setRevsdAssClassDate_L3(DateUtil.formatDate(locale,lmtObj.getRevsdAssClassDate_L3()));
			
			
			lmtDetailForm.setProjectDelayedFlagL2(lmtObj.getProjectDelayedFlagL2());


			lmtDetailForm.setProjectDelayedFlagL3(lmtObj.getProjectDelayedFlagL3());
			
			
			lmtDetailForm.setLeaglOrArbiProceedLevel3(lmtObj.getLeaglOrArbiProceedLevel3());

			
			lmtDetailForm.setDetailsRsnByndProLevel3(lmtObj.getDetailsRsnByndProLevel3());
			
			lmtDetailForm.setChngInRepayScheduleL1(lmtObj.getChngInRepaySchedule());
			
			lmtDetailForm.setChngInRepayScheduleL2(lmtObj.getChngInRepayScheduleL2());
			
						
			lmtDetailForm.setChngInRepayScheduleL3(lmtObj.getChngInRepayScheduleL3());

			
			lmtDetailForm.setPromotersCapRunFlagL2(lmtObj.getPromotersCapRunFlagL2());


			lmtDetailForm.setPromotersCapRunFlagL3(lmtObj.getPromotersCapRunFlagL3());
			

			lmtDetailForm.setChangeInScopeBeforeSCODFlagL2(lmtObj.getChangeInScopeBeforeSCODFlagL2());
			
			
			lmtDetailForm.setChangeInScopeBeforeSCODFlagL3(lmtObj.getChangeInScopeBeforeSCODFlagL3());
			
			
			lmtDetailForm.setPromotersHoldEquityFlagL2(lmtObj.getPromotersHoldEquityFlagL2());
			
		
			lmtDetailForm.setPromotersHoldEquityFlagL3(lmtObj.getPromotersHoldEquityFlagL3());
			

			lmtDetailForm.setCostOverrunOrg25CostViabilityFlagL2(lmtObj.getCostOverrunOrg25CostViabilityFlagL2());
			
			
			lmtDetailForm.setCostOverrunOrg25CostViabilityFlagL3(lmtObj.getCostOverrunOrg25CostViabilityFlagL3());
			
			
			lmtDetailForm.setHasProjectViabReAssFlagL2(lmtObj.getHasProjectViabReAssFlagL2());
			
		
			lmtDetailForm.setHasProjectViabReAssFlagL3(lmtObj.getHasProjectViabReAssFlagL3());
			

			lmtDetailForm.setProjectViabReassesedFlagL2(lmtObj.getProjectViabReassesedFlagL2());
			

			lmtDetailForm.setProjectViabReassesedFlagL3(lmtObj.getProjectViabReassesedFlagL3());
			
			
			lmtDetailForm.setRevsedESCODStpDateL2(DateUtil.formatDate(locale,lmtObj.getRevsedESCODStpDateL2()));

			
			lmtDetailForm.setRevsedESCODStpDateL3(DateUtil.formatDate(locale,lmtObj.getRevsedESCODStpDateL3()));


			lmtDetailForm.setRevsedESCODStpDate(DateUtil.formatDate(locale,lmtObj.getRevsedESCODStpDate()));
			
			
			lmtDetailForm.setProjectFinance(lmtObj.getProjectFinance());

			
			lmtDetailForm.setProjectLoan(lmtObj.getProjectLoan());


			lmtDetailForm.setInfaProjectFlag(lmtObj.getInfaProjectFlag());
			

			lmtDetailForm.setWhlmreupSCOD(lmtObj.getWhlmreupSCOD());
			
			
			lmtDetailForm.setScodDate(DateUtil.formatDate(locale,lmtObj.getScodDate()));

			
			lmtDetailForm.setRemarksSCOD(lmtObj.getRemarksSCOD());
			
			if("".equals(lmtObj.getExstAssetClass()) || lmtObj.getExstAssetClass() == null) {
			lmtDetailForm.setExstAssetClass("1");
			}else {
				lmtDetailForm.setExstAssetClass(lmtObj.getExstAssetClass());
			}
			
			lmtDetailForm.setExstAssClassDate(DateUtil.formatDate(locale,lmtObj.getExstAssClassDate()));
			

			lmtDetailForm.setRevisedAssetClass(lmtObj.getRevisedAssetClass());
			
			
			lmtDetailForm.setRevsdAssClassDate(DateUtil.formatDate(locale,lmtObj.getRevsdAssClassDate()));
			
			
			lmtDetailForm.setActualCommOpsDate(DateUtil.formatDate(locale,lmtObj.getActualCommOpsDate()));
			

			lmtDetailForm.setLelvelDelaySCOD(lmtObj.getLelvelDelaySCOD());
			
			
			lmtDetailForm.setPrincipalInterestSchFlag(lmtObj.getPrincipalInterestSchFlag());
			
			
			lmtDetailForm.setProjectDelayedFlag(lmtObj.getProjectDelayedFlag());
			
			
			lmtDetailForm.setReasonLevelOneDelay(lmtObj.getReasonLevelOneDelay());
			
			
			lmtDetailForm.setChngInRepaySchedule(lmtObj.getChngInRepaySchedule());
			
			
			lmtDetailForm.setEscodLevelOneDelayDate(DateUtil.formatDate(locale,lmtObj.getEscodLevelOneDelayDate()));

			
			lmtDetailForm.setEscodLevelSecondDelayDate(DateUtil.formatDate(locale,lmtObj.getEscodLevelSecondDelayDate()));


			lmtDetailForm.setReasonLevelThreeDelay(lmtObj.getReasonLevelThreeDelay());

			
			lmtDetailForm.setEscodLevelThreeDelayDate(DateUtil.formatDate(locale,lmtObj.getEscodLevelThreeDelayDate()));


			lmtDetailForm.setLegalOrArbitrationLevel2Flag(lmtObj.getLegalOrArbitrationLevel2Flag());


			lmtDetailForm.setChngOfOwnPrjFlagNonInfraLevel2(lmtObj.getChngOfOwnPrjFlagNonInfraLevel2());

			
			lmtDetailForm.setReasonBeyondPromoterLevel2Flag(lmtObj.getReasonBeyondPromoterLevel2Flag());

			
			lmtDetailForm.setChngOfProjScopeNonInfraLevel2(lmtObj.getChngOfProjScopeNonInfraLevel2());

			
			lmtDetailForm.setChngOfOwnPrjFlagInfraLevel2(lmtObj.getChngOfOwnPrjFlagInfraLevel2());


			lmtDetailForm.setChngOfProjScopeInfraLevel2(lmtObj.getChngOfProjScopeInfraLevel2());

			
			lmtDetailForm.setLegalOrArbitrationLevel3Flag(lmtObj.getLegalOrArbitrationLevel3Flag());

			
			lmtDetailForm.setChngOfOwnPrjFlagNonInfraLevel3(lmtObj.getChngOfOwnPrjFlagNonInfraLevel3());

			
			lmtDetailForm.setReasonBeyondPromoterLevel3Flag(lmtObj.getReasonBeyondPromoterLevel3Flag());

			
			lmtDetailForm.setChngOfProjScopeNonInfraLevel3(lmtObj.getChngOfProjScopeNonInfraLevel3());


			lmtDetailForm.setChngOfOwnPrjFlagInfraLevel3(lmtObj.getChngOfOwnPrjFlagInfraLevel3());

			
			lmtDetailForm.setChngOfProjScopeInfraLevel3(lmtObj.getChngOfProjScopeInfraLevel3());

			
			lmtDetailForm.setLeaglOrArbiProceed(lmtObj.getLeaglOrArbiProceed());


			lmtDetailForm.setDetailsRsnByndPro(lmtObj.getDetailsRsnByndPro());
			

			lmtDetailForm.setRecvPriorOfSCOD(lmtObj.getRecvPriorOfSCOD());
			
			
			
			lmtDetailForm.setReasonLevelTwoDelay(lmtObj.getReasonLevelTwoDelay());
			
			
			lmtDetailForm.setPromotersCapRunFlag(lmtObj.getPromotersCapRunFlag());

			
			
			lmtDetailForm.setChangeInScopeBeforeSCODFlag(lmtObj.getChangeInScopeBeforeSCODFlag());
			
			
			
			lmtDetailForm.setPromotersHoldEquityFlag(lmtObj.getPromotersHoldEquityFlag());
			

			
			lmtDetailForm.setCostOverrunOrg25CostViabilityFlag(lmtObj.getCostOverrunOrg25CostViabilityFlag());
			

			
			
			lmtDetailForm.setHasProjectViabReAssFlag(lmtObj.getHasProjectViabReAssFlag());
			

			
			lmtDetailForm.setProjectViabReassesedFlag(lmtObj.getProjectViabReassesedFlag());
			
			
			lmtDetailForm.setActualLevelOfDelay(lmtObj.getLelvelDelaySCOD());
			
			
	    	}
			}
			

			lmtDetailForm.setBankingArrangement(lmtObj.getBankingArrangement());
			lmtDetailForm.setClauseAsPerPolicy(lmtObj.getClauseAsPerPolicy());
			
			String facilitySystem = lmtDetailForm.getFacilitySystem();
			if (CoBorrowerSystems.contains(facilitySystem)) {
				lmtDetailForm.setFacCoBorrowerInd(ICMSConstant.YES);
			} else {
				lmtDetailForm.setFacCoBorrowerInd(ICMSConstant.NO);
			}
			mapCoBorrowerOBToForm(lmtDetailForm, lmtObj.getCoBorrowerDetails(), inputs);


			lmtDetailForm.setIsDPRequired(lmtObj.getIsDPRequired());
			
			if(lmtObj.getTenor() != null)
				lmtDetailForm.setTenor(String.valueOf(lmtObj.getTenor()));
			
			if(StringUtils.isNotBlank(lmtObj.getTenorUnit()))
				lmtDetailForm.setTenorUnit(lmtObj.getTenorUnit());
			
			if(lmtObj.getMargin() != null)
				lmtDetailForm.setMargin(MapperUtil.mapDoubleToString(lmtObj.getMargin(), 2, locale));
			
			if(StringUtils.isNotBlank(lmtObj.getTenorDesc()))
				lmtDetailForm.setTenorDesc(lmtObj.getTenorDesc());
			
			if(StringUtils.isNotBlank(lmtObj.getPutCallOption()))
				lmtDetailForm.setPutCallOption(lmtObj.getPutCallOption());
			
			lmtDetailForm.setLoanAvailabilityDate(DateUtil.formatDate(locale,lmtObj.getLoanAvailabilityDate()));
			lmtDetailForm.setOptionDate(DateUtil.formatDate(locale,lmtObj.getOptionDate()));
			return lmtDetailForm;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException();
		}
	}

	private static void mapCoBorrowerOBToForm(LmtDetailForm form, List<IFacilityCoBorrowerDetails> obj, HashMap inputs) {
		if(CollectionUtils.isEmpty(obj))
			return;
		List<FacCoBorrowerDetailsForm> facCoBorrowerList = new ArrayList<FacCoBorrowerDetailsForm>();
		List<String> facCoBorrowerIdList = new ArrayList<String>();
		
		for(IFacilityCoBorrowerDetails coBorrowerObj : obj) {
			FacCoBorrowerDetailsForm coBorrowerForm = new FacCoBorrowerDetailsForm();
			coBorrowerForm.setCoBorrowerLiabId(coBorrowerObj.getCoBorrowerLiabId());
			coBorrowerForm.setCoBorrowerName(coBorrowerObj.getCoBorrowerName());
			facCoBorrowerList.add(coBorrowerForm);
			
			facCoBorrowerIdList.add(coBorrowerObj.getCoBorrowerLiabId());
		}
		
		form.setFacCoBorrowerList(facCoBorrowerList);

	//	String facCoBorrowerIds = UIUtil.getDelimitedStringFromList(facCoBorrowerIdList, ",");
		String facCoBorrowerIds = UIUtil.getJSONStringFromList(facCoBorrowerIdList, ",");
		
		facCoBorrowerIds = facCoBorrowerIds==null ? "" : facCoBorrowerIds ;
		form.setFacCoBorrowerLiabIds(facCoBorrowerIds);
	}
	
	private static void mapCoBorrowerFormToOB(LmtDetailForm form, ILimit obj, HashMap inputs) {

		List<IFacilityCoBorrowerDetails> facCoBorrowerDetailsList = new ArrayList<IFacilityCoBorrowerDetails>();

		if(StringUtils.isBlank(form.getFacCoBorrowerLiabIds())) {
			obj.setCoBorrowerDetails(facCoBorrowerDetailsList);
			return;
		}
		List<String> selectedCoBorrowerIds = UIUtil.getListFromDelimitedString(form.getFacCoBorrowerLiabIds(), ",");
		List<ICoBorrowerDetails> partyCoBorrowerList = (List<ICoBorrowerDetails>) inputs.get(CO_BORROWER_LIST);
		
		Map<String, ICoBorrowerDetails> partyCoBorrowerMap = new HashMap<String, ICoBorrowerDetails>();
		if(!CollectionUtils.isEmpty(partyCoBorrowerList)) {
			for( ICoBorrowerDetails coborrower : partyCoBorrowerList) {
				partyCoBorrowerMap.put(coborrower.getCoBorrowerLiabId(), coborrower);
			}
		}
		
		for(String coBorrowerId : selectedCoBorrowerIds) {
			ICoBorrowerDetails coBorrower = partyCoBorrowerMap.get(coBorrowerId);
			
			IFacilityCoBorrowerDetails facCoBorrower = new OBFacilityCoBorrowerDetails();
			facCoBorrower.setCoBorrowerLiabId(coBorrower.getCoBorrowerLiabId());
			facCoBorrower.setCoBorrowerName(coBorrower.getCoBorrowerName());
			facCoBorrower.setCreateBy("CMS");
			facCoBorrower.setCreationDate(UIUtil.getDate());
			facCoBorrower.setMainProfileId(coBorrower.getMainProfileId());
			
			facCoBorrowerDetailsList.add(facCoBorrower);
		}
		
		obj.setCoBorrowerDetails(facCoBorrowerDetailsList);
	}
	
	/**
	 * @param lmtDetailForm
	 * @param helper
	 * @param event
	 * @param limitProfileID
	 */
	private void mapAgreementType(LmtDetailForm lmtDetailForm, MILimitUIHelper helper, String event,
			String limitProfileID) {
		// TODO Auto-generated method stub
		try {
			if (AbstractCommonMapper.isEmptyOrNull(lmtDetailForm.getAgreementType())) {
				String agreementType = helper.getSBMILmtProxy().getAgreementByAA(limitProfileID);
				lmtDetailForm.setAgreementType(agreementType);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void mapFacilityGroup(LmtDetailForm lmtDetailForm, MILimitUIHelper helper, String event,
			String limitProfileID) {
		try {
			if (AbstractCommonMapper.isEmptyOrNull(lmtDetailForm.getFacilityGroup())) {
				String facGroup = helper.getSBMILmtProxy().getFacilityGroupByAA(limitProfileID);
				lmtDetailForm.setFacilityGroup(facGroup);
				lmtDetailForm.setFacilityGroupDesc(facGroup);
			}
		}
		catch (Exception ex) {
		}
	}

	public static void renderAcntRefSummary(ILimit lmtObj, LmtDetailForm lmtDetailForm, MILimitUIHelper helper) {
		ILimitSysXRef[] lmtRef = lmtObj.getLimitSysXRefs();
		/*
		 * List summaryList = new ArrayList(); if (lmtRef != null) { for(int
		 * i=0; i<lmtRef.length; i++) { AcntRefSummaryItem nextItem = new
		 * AcntRefSummaryItem(); ILimitSysXRef nextRef = lmtRef[i];
		 * mapAcntRefSummary(nextItem, nextRef, helper, 1);
		 * summaryList.add(nextItem); } }
		 * lmtDetailForm.setAcntRefSummaryList(setRowHeader(summaryList));
		 */
		if (lmtObj.getLimitSysXRefs() != null) {
			ILimitSysXRef[] limitSysXRefs = lmtObj.getLimitSysXRefs();
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
			
			lmtObj.setLimitSysXRefs((ILimitSysXRef[])values.toArray(new ILimitSysXRef[values.size()]));
			
			lmtDetailForm.setAcntRefSummaryList(Arrays.asList(lmtObj.getLimitSysXRefs()));
		}
		else {
			lmtDetailForm.setAcntRefSummaryList(new ArrayList());
		}
	}

	private static void mapAcntRefSummary(AcntRefSummaryItem nextItem, ILimitSysXRef nextRef, MILimitUIHelper helper,
			int type) {
		nextItem.setAccountId(String.valueOf(nextRef.getXRefID()));
		ICustomerSysXRef acctDetail = nextRef.getCustomerSysXRef();
		if (acctDetail != null) {
			nextItem.setSourceSystemCountry(helper.getHostSystemCountryDisp(acctDetail.getExternalSysCountry()));
			nextItem.setSourceSystemName(helper.getHostSystemNameDisp(acctDetail.getExternalSysCountry(), acctDetail
					.getExternalSystemCode()));
			nextItem.setAccountNo(acctDetail.getExternalAccountNo());
			nextItem.setAcctClassification(helper.getAccountClassification(acctDetail.getAccountDelinq()));
			if (type == 1) {
				nextItem.setHeaderClass("index");
			}
			else if (type == 2) {
				nextItem.setHeaderClass("indexadd");
			}
			else if (type == 3) {
				nextItem.setHeaderClass("indexdel");
			}
			else if (type == 4) {
				nextItem.setHeaderClass("indexdiff");
			}
			else {
				nextItem.setHeaderClass("index");
			}
		}
	}

	private static List setRowHeader(List summaryList) {
		if (summaryList != null) {
			for (int i = 0; i < summaryList.size(); i++) {
				Object o = summaryList.get(i);
				String rowIndex = (i % 2 == 0) ? "odd" : "even";
				if (o instanceof AcntRefSummaryItem) {
					((AcntRefSummaryItem) o).setRowClass(rowIndex);
				}
				else if (o instanceof LmtSecSummaryItem) {
					((LmtSecSummaryItem) o).setRowClass(rowIndex);
				}
			}
		}
		return summaryList;
	}

	private static void renderCompareAcntRef(ILimit origLmt, ILimit stagingLmt, LmtDetailForm lmtDetailForm,
			MILimitUIHelper helper) throws Exception {
		ILimitSysXRef[] oldLmtRef = null;
		ILimitSysXRef[] newLmtRef = null;
		if (origLmt != null) {
			oldLmtRef = origLmt.getLimitSysXRefs();
		}
		if (stagingLmt != null) {
			newLmtRef = stagingLmt.getLimitSysXRefs();
		}
		if (oldLmtRef == null) {
			oldLmtRef = new ILimitSysXRef[0];
		}
		if (newLmtRef == null) {
			newLmtRef = new ILimitSysXRef[0];
		}

		lmtDetailForm.setAcntRefSummaryList(CompareOBUtil.compOBArray(newLmtRef, oldLmtRef));
		/*
		 * ILimitSysXRef[] oldLmtRef = null; ILimitSysXRef[] newLmtRef = null;
		 * List summaryList = new ArrayList(); if (origLmt != null) { oldLmtRef
		 * = origLmt.getLimitSysXRefs(); } if (stagingLmt != null) { newLmtRef =
		 * stagingLmt.getLimitSysXRefs(); } if (oldLmtRef == null) { oldLmtRef =
		 * new ILimitSysXRef[0]; } if (newLmtRef == null) { newLmtRef = new
		 * ILimitSysXRef[0]; }
		 * 
		 * try { for(int i=0; i<newLmtRef.length; i++) { AcntRefSummaryItem
		 * newItem = new AcntRefSummaryItem(); ILimitSysXRef newRef =
		 * newLmtRef[i]; long curSid = newRef.getSID(); boolean foundInOld =
		 * false;
		 * 
		 * inner: for(int j=0; j<oldLmtRef.length; j++) { ILimitSysXRef oldRef =
		 * oldLmtRef[j]; long oldSid = oldRef.getSID(); if (curSid == oldSid) {
		 * foundInOld = true; break inner; } } if (foundInOld) {
		 * mapAcntRefSummary(newItem, newRef, helper, 2); } else {
		 * mapAcntRefSummary(newItem, newRef, helper, 1); }
		 * summaryList.add(newItem); }
		 * 
		 * for(int i=0; i<oldLmtRef.length; i++) { ILimitSysXRef oldRef =
		 * oldLmtRef[i]; long oldSid = oldRef.getSID(); boolean foundInNew =
		 * false; inner: for(int j=0; j<newLmtRef.length; j++) { ILimitSysXRef
		 * newRef = newLmtRef[j]; long curSid = newRef.getSID(); if (curSid ==
		 * oldSid) { foundInNew = true; break inner; } } if (!foundInNew) {
		 * AcntRefSummaryItem delItem = new AcntRefSummaryItem();
		 * mapAcntRefSummary(delItem, oldRef, helper, 3);
		 * summaryList.add(delItem); } } } catch(Exception ex) {}
		 * lmtDetailForm.setAcntRefSummaryList(setRowHeader(summaryList));
		 */
	}

	public static void renderLmtSecSummary(ILimit lmtObj, LmtDetailForm lmtDetailForm, MILimitUIHelper helper) {
		ICollateralAllocation[] colAlloc = lmtObj.getCollateralAllocations();
		List summaryList = new ArrayList();
		if (colAlloc != null) {
			for (int i = 0; i < colAlloc.length; i++) {
				//if(colAlloc != null){
				ICollateralAllocation nextAlloc = colAlloc[i];
				if (ICMSConstant.HOST_STATUS_DELETE.equals(nextAlloc.getHostStatus())) {
					continue;
				}

				LmtSecSummaryItem nextItem = new LmtSecSummaryItem();

				mapLmtSecSummary(nextItem, nextAlloc, helper, 1);
				summaryList.add(nextItem);
			}
			//}
		}
		lmtDetailForm.setLmtSecSummaryList(setRowHeader(summaryList));
	}

	private static void mapLmtSecSummary(LmtSecSummaryItem nextItem, ICollateralAllocation nextAlloc,
			MILimitUIHelper helper, int type) {
		ICollateral nextCol = nextAlloc.getCollateral();
		nextItem.setCollateralId(String.valueOf(nextCol.getCollateralCode()));
		nextItem.setSciSecurityId(String.valueOf(nextCol.getCollateralID()));
		nextItem.setSecurityType(nextCol.getCollateralSubType().getTypeName());
		nextItem.setSecuritySubType(nextCol.getCollateralSubType().getSubTypeName());
		System.out.print("====LmtDetailMapper==952=======nextItem.getLmtSecurityCoverage() : "+nextItem.getLmtSecurityCoverage());
		nextItem.setLmtSecurityCoverage(nextAlloc.getLmtSecurityCoverage());
		nextItem.setCpsSecurityId(nextAlloc.getCpsSecurityId());
		if (type == 1) {
			nextItem.setHeaderClass("index");
		}
		else if (type == 2) {
			nextItem.setHeaderClass("indexadd");
		}
		else if (type == 3) {
			nextItem.setHeaderClass("indexdel");
		}
		else if (type == 4) {
			nextItem.setHeaderClass("indexdiff");
		}
		else {
			nextItem.setHeaderClass("index");
		}
	}

	private static void renderCompareLmtSec(ILimit origLmt, ILimit stagingLmt, LmtDetailForm lmtDetailForm,
			MILimitUIHelper helper) {
		ICollateralAllocation[] oldAllocs = null;
		ICollateralAllocation[] newAllocs = null;
		List summaryList = new ArrayList();
		if (origLmt != null) {
			oldAllocs = origLmt.getCollateralAllocations();
		}
		if (stagingLmt != null) {
			newAllocs = stagingLmt.getCollateralAllocations();
		}
		if (oldAllocs == null) {
			oldAllocs = new ICollateralAllocation[0];
		}
		if (newAllocs == null) {
			newAllocs = new ICollateralAllocation[0];
		}
		List icollAllo = new ArrayList();
		ICollateralAllocation[] icollAlloFinal = new ICollateralAllocation[oldAllocs.length];
		int k = 0;
		for(int m=0; m <oldAllocs.length ; m++){
			ICollateralAllocation oldAlloc1 = oldAllocs[m];			
			if (!ICMSConstant.HOST_STATUS_DELETE.equals(oldAlloc1.getHostStatus())) {
			{	icollAlloFinal[k]=(ICollateralAllocation) oldAlloc1;
			k++;
		}
		}
		}
		oldAllocs = icollAlloFinal;
		try {
			for (int i = 0; i < newAllocs.length; i++) {
				LmtSecSummaryItem newItem = new LmtSecSummaryItem();
				ICollateralAllocation newAlloc = newAllocs[i];
				if (ICMSConstant.HOST_STATUS_DELETE.equals(newAlloc.getHostStatus())) {
					continue;
				}
				long curSid = newAlloc.getCollateral().getCollateralID();
				boolean foundInOld = false;

				inner: for (int j = 0; j < oldAllocs.length; j++) {
					ICollateralAllocation oldAlloc = oldAllocs[j];
					if (ICMSConstant.HOST_STATUS_DELETE.equals(oldAlloc.getHostStatus())) {
						continue;
					}

					long oldSid = oldAlloc.getCollateral().getCollateralID();
					if (curSid == oldSid) {
						foundInOld = true;
						break inner;
					}
				}
				if (foundInOld) {
					mapLmtSecSummary(newItem, newAlloc, helper, 1);
				}
				else {
					mapLmtSecSummary(newItem, newAlloc, helper, 2);
				}
				summaryList.add(newItem);
			}

			for (int i = 0; i < oldAllocs.length; i++) {
				ICollateralAllocation oldAlloc = oldAllocs[i];
				if (ICMSConstant.HOST_STATUS_DELETE.equals(oldAlloc.getHostStatus())) {
					continue;
				}

				long oldSid = oldAlloc.getCollateral().getCollateralID();
				boolean foundInNew = false;
				inner: for (int j = 0; j < newAllocs.length; j++) {
					ICollateralAllocation newAlloc = newAllocs[j];
					if (ICMSConstant.HOST_STATUS_DELETE.equals(oldAlloc.getHostStatus())) {
						continue;
					}

					long curSid = newAlloc.getCollateral().getCollateralID();
					if (curSid == oldSid) {
						foundInNew = true;
						break inner;
					}
				}
				if (!foundInNew) {
					LmtSecSummaryItem delItem = new LmtSecSummaryItem();
					mapLmtSecSummary(delItem, oldAlloc, helper, 3);
					summaryList.add(delItem);
				}
			}
		}
		catch (Exception ex) {
		}
		lmtDetailForm.setLmtSecSummaryList(setRowHeader(summaryList));
	}

	// for checker process submitted limit, we need to found what has been
	// updated and
	// display different color
	private void setChangedInd(String event, ILimit origLmt, ILimit stagingLmt, LmtDetailForm lmtDetailForm) {
		try {
			if (origLmt == null) {
				origLmt = new OBLimit();
			}
			if (!EventConstant.EVENT_PROCESS.equals(event) && !EventConstant.EVENT_PROCESS_RETURN.equals(event)) {
				return;
			}
			if (!CompareOBUtil.compOB(stagingLmt, origLmt, "limitExpiryDate")) {
				lmtDetailForm.setLimitExpiryDateClass("fieldnamediff");
			}
			if (!CompareOBUtil.compOB(stagingLmt, origLmt, "limitDesc")) {
				lmtDetailForm.setLimitDescClass("fieldnamediff");
			}
			if (!CompareOBUtil.compOB(stagingLmt.getBookingLocation(), origLmt.getBookingLocation(), "countryCode")) {
				lmtDetailForm.setOrigBookingCtryClass("fieldnamediff");
			}
			if (!CompareOBUtil
					.compOB(stagingLmt.getBookingLocation(), origLmt.getBookingLocation(), "organisationCode")) {
				lmtDetailForm.setOrigBookingLocClass("fieldnamediff");
			}
			if (!CompareOBUtil.compOB(stagingLmt, origLmt, "outerLimitRef")) {
				lmtDetailForm.setOuterLmtClass("fieldnamediff");
			}
			if (!CompareOBUtil.compOB(stagingLmt, origLmt, "limitType")) {
				lmtDetailForm.setLimitTypeClass("fieldnamediff");
			}
			/*
			 * if (CompareOBUtil.compOB(stagingLmt, origLmt, "")) {
			 * lmtDetailForm.setFacilityGroupClass("fieldnamediff"); }
			 */
			/*
			 * if (!CompareOBUtil.compOB(stagingLmt, origLmt, "productDesc")) {
			 * lmtDetailForm.setProdTypeClass("fieldnamediff"); }
			 */
			if (!CompareOBUtil.compOB(stagingLmt, origLmt, "facilityDesc")) {
				lmtDetailForm.setFacilityTypeClass("fieldnamediff");
			}

			if (!CompareOBUtil.compOB(origLmt.getApprovedLimitAmount(), stagingLmt.getApprovedLimitAmount(), "amount")
					|| !CompareOBUtil.compOB(origLmt.getApprovedLimitAmount(), stagingLmt.getApprovedLimitAmount(),
							"currencyCode")) {
				lmtDetailForm.setApprovedLmtClass("fieldnamediff");
			}
			if (!CompareOBUtil.compOB(stagingLmt, origLmt, "requiredSecurityCoverage")) {
				lmtDetailForm.setRequiredSecCovClass("fieldnamediff");
			}
			if (!CompareOBUtil.compOB(stagingLmt, origLmt, "limitTenor")
					|| !CompareOBUtil.compOB(stagingLmt, origLmt, "limitTenorUnit")) {
				lmtDetailForm.setLmtTenorClass("fieldnamediff");
			}
			if (!CompareOBUtil.compOB(stagingLmt, origLmt, "limitAdviseInd")) {
				lmtDetailForm.setLmtAdvisedIndClass("fieldnamediff");
			}
			if (!CompareOBUtil.compOB(stagingLmt, origLmt, "loanType")) {
				lmtDetailForm.setLoanTypeClass("fieldnamediff");
			}
			if (!CompareOBUtil.compOB(stagingLmt, origLmt, "limitSecuredType")) {
				lmtDetailForm.setLmtSecRelationshipClass("fieldnamediff");
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void deleteActnRef(ILimit limit, LmtDetailForm lmtDetailForm) {
		MILimitUIHelper helper = new MILimitUIHelper();
		ILimitSysXRef[] xref = limit.getLimitSysXRefs();
		String[] deletedInd = lmtDetailForm.getDeletedActRef();
		if ((xref != null) && (deletedInd != null) && (deletedInd.length > 0)) {
			List tempList = new ArrayList();
			for (int i = 0; i < xref.length; i++) {
				tempList.add(xref[i]);
			}
			List res = helper.deleteItem(tempList, deletedInd);
			limit.setLimitSysXRefs((ILimitSysXRef[]) (res.toArray(new ILimitSysXRef[0])));
		}
	}

	private void deleteLmtSecLink(ILimit limit, LmtDetailForm lmtDetailForm) {
//		System.out.println("DELETING LIMIT SECURITY LINK........");
		MILimitUIHelper helper = new MILimitUIHelper();
		ICollateralAllocation[] alloc = limit.getCollateralAllocations();
		String deletedInd = lmtDetailForm.getDeletedLmtSec();
		if ((alloc != null) && (deletedInd != null) && !deletedInd.trim().equals("")) {
//			System.out.println("ALLOCATION LENGTH&&&&&&&&&&&&&&&&& " + alloc.length + "  --- " + deletedInd);
			List tempList = new ArrayList();
			for (int i = 0; i < alloc.length; i++) {
				if (ICMSConstant.HOST_STATUS_DELETE.equals(alloc[i].getHostStatus())) {
					continue;
				}

				tempList.add(alloc[i]);
			}
			List res = helper.deleteItem(tempList, deletedInd);
			limit.setCollateralAllocations((ICollateralAllocation[]) (res.toArray(new ICollateralAllocation[0])));
//			System.out.println("RESULT LENGTH ...... " + res.size());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.IMapper#mapFormToOB(com.integrosys
	 * .base.uiinfra.common.CommonForm, java.util.HashMap)
	 */
	public Object mapFormToOB(CommonForm commonForm, HashMap inputs) throws MapperException {
		// TODO Auto-generated method stub
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		String event = (String) (inputs.get("event"));
		String itemType = (String) (inputs.get("itemType"));
		try {
			ILimit lmtObj = this.getLmtObject(inputs);
			//ILimit lmtObj = this.getAtualLmtObject(inputs);
			LmtDetailForm lmtDetailForm = (LmtDetailForm) commonForm;
			
			lmtObj.setFacilityCat(lmtDetailForm.getFacilityCat());
			
			lmtObj.setFacilityName(lmtDetailForm.getFacilityName());
			
			lmtObj.setFacilityCode(lmtDetailForm.getFacilityCode());
			
			lmtObj.setFacilityType(lmtDetailForm.getFacilityType());
			
			lmtObj.setFacilitySystem(lmtDetailForm.getFacilitySystem());
			
			lmtObj.setFacilitySystemID(lmtDetailForm.getFacilitySystemID());

			lmtObj.setRiskType(lmtDetailForm.getRiskType());
			
			lmtObj.setLineNo(lmtDetailForm.getLineNo());
			
			lmtObj.setPurpose(lmtDetailForm.getPurpose());
			
			lmtObj.setOtherPurpose(lmtDetailForm.getOtherPurpose());
						
			if("ET".equals(lmtObj.getFacilitySystem())){
				lmtObj.setIsDP("Y");
			}else{
				lmtObj.setIsDP(lmtDetailForm.getIsDP());
			}
			
			
			
			lmtObj.setRelationShipManager(lmtDetailForm.getRelationShipManager());
			
			lmtObj.setLimitType(lmtDetailForm.getLimitType());
			
			lmtObj.setLimitRemarks(lmtDetailForm.getLimitRemarks());
			
			if(lmtDetailForm.getPurposeBoolean() == null || lmtDetailForm.getPurposeBoolean().equals("")
					|| lmtDetailForm.getPurposeBoolean().equals("N")){
				lmtObj.setPurposeBoolean("N");
			}else{
				lmtObj.setPurposeBoolean("Y");
			}
			
			DefaultLogger.debug(this, "lmtDetailForm.getIsReleased() ===757==> "+lmtDetailForm.getIsReleased());
			
			if(lmtDetailForm.getIsReleased() == null || lmtDetailForm.getIsReleased().equals("")
					|| lmtDetailForm.getIsReleased().equals("N")){
				lmtObj.setIsReleased("N");
			}else{
				lmtObj.setIsReleased("Y");
			}
			
			DefaultLogger.debug(this, "lmtDetailForm.getIsSecured() ===766==> "+lmtDetailForm.getIsSecured());
			
			if(lmtDetailForm.getIsSecured() == null || lmtDetailForm.getIsSecured().equals("")
					|| lmtDetailForm.getIsSecured().equals("N")){
				lmtObj.setIsSecured("N");
			}else{
				lmtObj.setIsSecured("Y");
			}
			
			lmtObj.setGrade(lmtDetailForm.getGrade());
			

			DefaultLogger.debug(this, "lmtDetailForm.getIsAdhoc() ===778==> "+lmtDetailForm.getIsAdhoc());
			
			
			if(lmtDetailForm.getIsAdhoc() == null || lmtDetailForm.getIsAdhoc().equals("") 
					|| lmtDetailForm.getIsAdhoc().equals("N")){
				lmtObj.setIsAdhoc("N");
			}else{
				lmtObj.setIsAdhoc("Y");
			}
			
			DefaultLogger.debug(this, "lmtDetailForm.getIsAdhocToSum() ===788==> "+lmtDetailForm.getIsAdhocToSum());
			
			if(lmtDetailForm.getIsAdhocToSum() == null ||lmtDetailForm.getIsAdhocToSum().equals("")
					 ||lmtDetailForm.getIsAdhocToSum().equals("N")){
				lmtObj.setIsAdhocToSum("N");
			}else{
				lmtObj.setIsAdhocToSum("Y");
			}

			lmtObj.setCurrencyCode(lmtDetailForm.getCurrencyCode());
			
			if (!AbstractCommonMapper.isEmptyOrNull(lmtDetailForm.getRequiredSecCov())) {
				//lmtObj.setRequiredSecurityCoverage(lmtDetailForm.getRequiredSecCov());
				
				//Phase 3 CR:comma separated
				lmtObj.setRequiredSecurityCoverage(UIUtil.removeComma(lmtDetailForm.getRequiredSecCov()));
			}
			
			
			if (!AbstractCommonMapper.isEmptyOrNull(lmtDetailForm.getReleasableAmount())) {
				//lmtObj.setReleasableAmount(lmtDetailForm.getReleasableAmount());
				
				//Phase 3 CR:comma separated
				lmtObj.setReleasableAmount(UIUtil.removeComma(lmtDetailForm.getReleasableAmount()));
			}
			
			
			if (!AbstractCommonMapper.isEmptyOrNull(lmtDetailForm.getAdhocLmtAmount())) {
				//lmtObj.setAdhocLmtAmount(lmtDetailForm.getAdhocLmtAmount());
				
				//Phase 3 CR:comma separated
				lmtObj.setAdhocLmtAmount(UIUtil.removeComma(lmtDetailForm.getAdhocLmtAmount()));
			}
						
			if (!AbstractCommonMapper.isEmptyOrNull(lmtDetailForm.getTotalReleasedAmount())) {
//				lmtObj.setTotalReleasedAmount(lmtDetailForm.getTotalReleasedAmount());
				
				//Phase 3 CR:comma separated
				lmtObj.setTotalReleasedAmount(UIUtil.removeComma(lmtDetailForm.getTotalReleasedAmount()));
			}
			
			//Phase 3 CR:comma separated
			ILimitSysXRef[] limitSysXRefs = lmtObj.getLimitSysXRefs();
			
			if(null!=limitSysXRefs){
			for(int i=0; i <limitSysXRefs.length; i++){
				ILimitSysXRef iLimitSysXRef=limitSysXRefs[i];
				limitSysXRefs[i].getCustomerSysXRef().setReleasedAmount(UIUtil.removeComma(limitSysXRefs[i].getCustomerSysXRef().getReleasedAmount()));
				limitSysXRefs[i].getCustomerSysXRef().setUtilizedAmount(UIUtil.removeComma(limitSysXRefs[i].getCustomerSysXRef().getUtilizedAmount()));
				
			}
			lmtObj.setLimitSysXRefs(limitSysXRefs);
			}
			lmtObj.setGuarantee(lmtDetailForm.getGuarantee());
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MMM/yyyy");
			if (null != lmtDetailForm.getAdhocLastAvailDate()  && !"".equals(lmtDetailForm.getAdhocLastAvailDate()))
			{
			lmtObj.setAdhocLastAvailDate(sdf1.parse(lmtDetailForm.getAdhocLastAvailDate()));
			}
			lmtObj.setAdhocTenor(lmtDetailForm.getAdhocTenor());
			
			if (null != lmtDetailForm.getAdhocFacilityExpDate() && !"".equals(lmtDetailForm.getAdhocFacilityExpDate()))
			{
			lmtObj.setAdhocFacilityExpDate(sdf1.parse(lmtDetailForm.getAdhocFacilityExpDate()));
			}
			if(lmtDetailForm.getAdhocFacility() == null || lmtDetailForm.getAdhocFacility().equals("") 
					|| lmtDetailForm.getAdhocFacility().equals("N")){
				lmtObj.setAdhocFacility("N");
			}else{
				lmtObj.setAdhocFacility("Y");
			}
			
			if(lmtDetailForm.getMultiDrawdownAllow() == null || lmtDetailForm.getMultiDrawdownAllow().equals("") 
					|| lmtDetailForm.getMultiDrawdownAllow().equals("N")){
				lmtObj.setMultiDrawdownAllow("N");
			}else{
				lmtObj.setMultiDrawdownAllow("Y");
			}
			
			if(lmtDetailForm.getGeneralPurposeLoan() == null || lmtDetailForm.getGeneralPurposeLoan().equals("") 
					|| lmtDetailForm.getGeneralPurposeLoan().equals("N")){
				lmtObj.setGeneralPurposeLoan("N");
			}else{
				lmtObj.setGeneralPurposeLoan("Y");
			}
			
			if(lmtDetailForm.getAdhocFacility() == null || lmtDetailForm.getAdhocFacility().equals("") 
					|| lmtDetailForm.getAdhocFacility().equals("N")){
				lmtObj.setAdhocFacility("N");
			}else{
				lmtObj.setAdhocFacility("Y");
			}
			
			
			lmtObj.setSubPartyName(lmtDetailForm.getSubPartyName());
			
		
			if(lmtDetailForm.getSubFacilityName()==null){
				MILimitUIHelper helper = new MILimitUIHelper();
				SBMILmtProxy proxy = helper.getSBMILmtProxy();
				String subFacilityName=proxy.getFacilityName(lmtDetailForm.getMainFacilityId());
				lmtObj.setSubFacilityName(subFacilityName);
			}
			else{
				lmtObj.setSubFacilityName(lmtDetailForm.getSubFacilityName());
			}
			lmtObj.setLiabilityID(lmtDetailForm.getLiabilityID());
			
			lmtObj.setMainFacilityId(lmtDetailForm.getMainFacilityId());
			lmtObj.setLimitStatus("ACTIVE");
			lmtObj.setLimitSecuredType(lmtDetailForm.getLmtSecRelationship());
			if(lmtDetailForm.getSyndicateLoan() == null || lmtDetailForm.getSyndicateLoan().equals("")
					|| lmtDetailForm.getSyndicateLoan().equals("N")){
				lmtObj.setSyndicateLoan("N");
			}
			else
			lmtObj.setSyndicateLoan(lmtDetailForm.getSyndicateLoan());
			if('Y'== lmtObj.getIsFromCamonlineReq()){
				lmtObj.setIsFromCamonlineReq('Y');
			}else{
				lmtObj.setIsFromCamonlineReq('N');
			}
			
			if (EventConstant.EVENT_CREATE.equals(event)) {
				MILimitUIHelper helper = new MILimitUIHelper();
				renderLmtSecSummary(lmtObj,lmtDetailForm,helper);
			}
			if (EventConstant.EVENT_DELETE_ITEM.equals(event)) {
				if ("actnref".equals(itemType)) {
					deleteActnRef(lmtObj, lmtDetailForm);
				}
				/*else if ("lmtsec".equals(itemType)) {
					deleteLmtSecLink(lmtObj, lmtDetailForm);
				}*/
			}
			
			/**
			 * Start : 
			 * By : Sachin D Patil
			 * Changes for Limit linkage deletion
			 * reset security coverage values.
			 * */
			
			//--Commented By Sachin P Delete security Linkage
			//Start On:- 19 Feb 2013
			
		//	DefaultLogger.debug(this, "lmtObj.getCollateralAllocations() ===839==> "+lmtObj.getCollateralAllocations());

			try{
				/*if(lmtObj.getCollateralAllocations() != null && lmtObj.getCollateralAllocations().length > 0 ){
				DefaultLogger.debug(this, "lmtObj.getCollateralAllocations().length ===840==> "+lmtObj.getCollateralAllocations().length);
				int j = 0;
				for(int i = 0; i < lmtObj.getCollateralAllocations().length; i++) {
					DefaultLogger.debug(this, "lmtObj.getCollateralAllocations()[i].getHostStatus() ===844==> "+lmtObj.getCollateralAllocations()[i].getHostStatus());
					DefaultLogger.debug(this, "lmtDetailForm.getSecurityCoverage() ===845==> "+lmtDetailForm.getSecurityCoverage());
					if(lmtObj.getCollateralAllocations()[i].getHostStatus() != null && lmtObj.getCollateralAllocations()[i].getHostStatus().equals("I")
							&& lmtDetailForm.getSecurityCoverage() != null){
						DefaultLogger.debug(this, "lmtDetailForm.getDeletedLmtSec()===848==> "+ i +"=>"+lmtDetailForm.getDeletedLmtSec());
						if(lmtDetailForm.getDeletedLmtSec()!=null && !lmtDetailForm.getDeletedLmtSec().equals(""))
						{
							DefaultLogger.debug(this, "String.valueOf(j)===851==> "+ j +"=>"+String.valueOf(j));
							if(lmtDetailForm.getDeletedLmtSec().equals(String.valueOf(j)))
							{
								j++;
							}
						}
						lmtObj.getCollateralAllocations()[i].setLmtSecurityCoverage(lmtDetailForm.getSecurityCoverage()[j]);
						j++;
					}
				}
			}*/
				//--Commented By Sachin P Delete security Linkage
				//End
				
				
				//--Start Delete security Linkage
				//Compare form value with actual/staging value and set security coverage 
				//with respect to similar security id. Remove record from list with given index, and re-update list.  
				
				String DelIndex = "";
				if(lmtDetailForm.getSecurityIdDel()!=null && !lmtDetailForm.getSecurityIdDel().equals("")){
					
					DelIndex = lmtDetailForm.getSecurityIdDel();
					
				}
				if (!DelIndex.equals("")){ /*for deleting security*/
					
					ICheckListDAO checkListDAO = CheckListDAOFactory.getCheckListDAO();
					int count = checkListDAO.getCountSecurityDocsInCheckList(Long.parseLong(DelIndex));
					if(count==0){
						if (lmtDetailForm.getSecurityCoverage() != null	&& lmtDetailForm.getSecurityCoverage().length > 0) {
						
							//ICollateralAllocation[] icollAllo = new ICollateralAllocation[lmtDetailForm.getSecurityCoverage().length];
							List icollAllo = new ArrayList();
							// lmtObj1 = lmtObj;
							int j =0;
							if (lmtObj.getCollateralAllocations() != null
									&& lmtObj.getCollateralAllocations().length > 0) {
		
								for (int i = 0; i < lmtObj.getCollateralAllocations().length; i++) {
									
									for (int k = 0; k < lmtDetailForm.getSecurityTypeSubType().length; k++) {
										ICollateralAllocation icollAlloObj = new OBCollateralAllocation();
										String strSecurityId = lmtDetailForm.getSecurityTypeSubType()[k];
										int strSecurityIdIdx = strSecurityId.lastIndexOf(',');
										String strCollatId = strSecurityId.substring(strSecurityIdIdx + 1, strSecurityId.length());
										// Long l = Long.valueOf(str2);
										long ColID = lmtObj.getCollateralAllocations()[i].getCollateral().getCollateralID();
										String strColID = String.valueOf(ColID);						
										
											if (strColID.equals(strCollatId)) {
												String kVal = String.valueOf(k);
												if(!DelIndex.equals(strColID)){
												/*icollAllo[j] = lmtObj.getCollateralAllocations()[i];
												icollAllo[j].setLmtSecurityCoverage(lmtDetailForm.getSecurityCoverage()[k]);*/
												
												icollAlloObj = lmtObj.getCollateralAllocations()[i];
												icollAlloObj.setLmtSecurityCoverage(lmtDetailForm.getSecurityCoverage()[k]);
												System.out.print("====LmtDetailMapper==1476=======icollAlloObj.setLmtSecurityCoverag : "+icollAlloObj.getLmtSecurityCoverage());		
												icollAllo.add(icollAlloObj);
												j++;
												//DelIndex = "";
												break;
											}
										}
									}
									
														
								}
							}
							lmtObj.setCollateralAllocations(null);
							//lmtObj.setCollateralAllocations(icollAllo);
							ICollateralAllocation[] icollAlloFinal = new ICollateralAllocation[icollAllo.size()];
							
							for(int i=0; i<icollAllo.size() ; i++){
								icollAlloFinal[i]=(ICollateralAllocation) icollAllo.get(i);
							}
							
							//ICollateralAllocation[] icollAlloArray = (OBCollateralAllocation[]) icollAllo.toArray();
							lmtObj.setCollateralAllocations(icollAlloFinal);
						}
					}
				}
				
				//--End Delete security Linkage
				
				//--Start Add New Security button Security coverage was flushed reset those values.
				//Get security coverage value from form and set it to list with respect to matching security ID.
			if(event.equals("submit") ||event.equals("submit_rejected") ||event.equals("create_sub_sec") || event.equals("create"))
			{
				if (lmtDetailForm.getSecurityCoverage() != null	&& lmtDetailForm.getSecurityCoverage().length > 0)
				{
					//int j = 0;
					int indx = -1;
					for (int i = 0; i < lmtObj.getCollateralAllocations().length; i++) {
						indx = -1;
					long colid =	lmtObj.getCollateralAllocations()[i].getCollateral().getCollateralID();
					String strColid = String.valueOf(colid);
					for(int k = 0; k < lmtDetailForm.getSecurityCoverage().length ; k++)
					{
					String strCollatId = lmtDetailForm.getSecurityTypeSubType()[k];
			int collatIdIdx = strCollatId.lastIndexOf(',');
			String strCollatoralId = strCollatId.substring(collatIdIdx + 1, strCollatId.length());
			if(strColid.equals(strCollatoralId))
			{
				
				indx = k;
			}
					}
					if(indx != -1)
						
						lmtObj.getCollateralAllocations()[i].setLmtSecurityCoverage(lmtDetailForm.getSecurityCoverage()[indx]);
					System.out.print("====LmtDetailMapper==1530=======lmtObj.getCollateralAllocations()[i].setLmtSecurityCoverage() : "+lmtObj.getCollateralAllocations()[i].getLmtSecurityCoverage());	
					}
				}
				
			}
			//--End Add New Security button Security coverage was flushed reset those values.
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String scodLineNoList=PropertyManager.getValue("scod.linenocode.name");
			if(scodLineNoList != null) {
				
				boolean scodB = false;
				if(scodLineNoList != null && !scodLineNoList.equals("")) {
				String[] scodlinelist = scodLineNoList.split(",");
				for(int i=0; i< scodlinelist.length; i++ ) {
					if(lmtDetailForm.getLineNo()!=null && lmtDetailForm.getLineNo().equals(scodlinelist[i])) {
						scodB = true;
					}
				}
				}
	    	if(scodB == true){
			lmtObj.setProjectFinance(lmtDetailForm.getProjectFinance());

			
			lmtObj.setProjectLoan(lmtDetailForm.getProjectLoan());


			lmtObj.setInfaProjectFlag(lmtDetailForm.getInfaProjectFlag());
			
			lmtObj.setScodDate(DateUtil.convertDate(locale,lmtDetailForm.getScodDate()));
			
			
			lmtObj.setRemarksSCOD(lmtDetailForm.getRemarksSCOD());
			

			lmtObj.setExstAssetClass(lmtDetailForm.getExstAssetClass());
			
			
			lmtObj.setExstAssClassDate(DateUtil.convertDate(locale,lmtDetailForm.getExstAssClassDate()));
			
			if(!"create".equals(lmtDetailForm.getEvent())){
				
				if(lmtDetailForm.getWhlmreupSCOD() != null && !lmtDetailForm.getWhlmreupSCOD().equals("")) {
					if(lmtDetailForm.getWhlmreupSCOD().equals("Y") && lmtDetailForm.getProjectDelayedFlag().equals("N")) {
						lmtDetailForm.setLelvelDelaySCOD("");
					}/*else if(lmtDetailForm.getWhlmreupSCOD().equals("Y") && lmtDetailForm.getProjectDelayedFlag().equals("Y")) {
						lmtDetailForm.setActualCommOpsDate("");
					}*/
				}
				
			if(!"".equals(lmtDetailForm.getLelvelDelaySCOD()) && lmtDetailForm.getLelvelDelaySCOD() != null) {
			
			lmtObj.setExstAssetClassL1(lmtDetailForm.getExstAssetClassL1());

			lmtObj.setProjectDelayedFlag(lmtDetailForm.getProjectDelayedFlag());
			lmtObj.setExstAssClassDateL1(DateUtil.convertDate(locale,lmtDetailForm.getExstAssClassDate()));
	
			lmtObj.setExstAssetClassL2(lmtDetailForm.getExstAssetClassL2());
			

			lmtObj.setExstAssClassDateL2(DateUtil.convertDate(locale,lmtDetailForm.getExstAssClassDate()));
					
			
			lmtObj.setExstAssetClassL3(lmtDetailForm.getExstAssetClassL3());
			
			
			lmtObj.setExstAssClassDateL3(DateUtil.convertDate(locale,lmtDetailForm.getExstAssClassDate()));


			lmtObj.setRevisedAssetClassL1(lmtDetailForm.getRevisedAssetClassL1());
			
			
			lmtObj.setRevsdAssClassDateL1(DateUtil.convertDate(locale, lmtDetailForm.getRevsdAssClassDateL1()));
			
			
			lmtObj.setRevisedAssetClass_L2(lmtDetailForm.getRevisedAssetClass_L2());
			

			lmtObj.setRevsdAssClassDate_L2(DateUtil.convertDate(locale, lmtDetailForm.getRevsdAssClassDate_L2()));
			
						
			lmtObj.setRevisedAssetClass_L3(lmtDetailForm.getRevisedAssetClass_L3());
			
			
			lmtObj.setRevsdAssClassDate_L3(DateUtil.convertDate(locale, lmtDetailForm.getRevsdAssClassDate_L3()));
			
			
			lmtObj.setProjectDelayedFlagL2(lmtDetailForm.getProjectDelayedFlag());


			lmtObj.setProjectDelayedFlagL3(lmtDetailForm.getProjectDelayedFlag());
			
			
			lmtObj.setLeaglOrArbiProceedLevel3(lmtDetailForm.getLeaglOrArbiProceedLevel3());

			
			lmtObj.setDetailsRsnByndProLevel3(lmtDetailForm.getDetailsRsnByndProLevel3());
			
			if(!"".equals(lmtDetailForm.getEscodLevelSecondDelayDate()) && lmtDetailForm.getEscodLevelSecondDelayDate() != null ) {
			lmtObj.setChngInRepayScheduleL2(lmtDetailForm.getChngInRepayScheduleL2());
			}else {
				lmtObj.setChngInRepayScheduleL2("");	
			}
			
			
			
			if(!"".equals(lmtDetailForm.getEscodLevelThreeDelayDate()) && lmtDetailForm.getEscodLevelThreeDelayDate() != null ) {
			lmtObj.setChngInRepayScheduleL3(lmtDetailForm.getChngInRepayScheduleL3());
			}else {
				lmtObj.setChngInRepayScheduleL3("");	
			}
			if(!"".equals(lmtDetailForm.getEscodLevelSecondDelayDate()) && lmtDetailForm.getEscodLevelSecondDelayDate() != null ) {
			lmtObj.setPromotersCapRunFlagL2(lmtDetailForm.getPromotersCapRunFlagL2());
			}else {
				lmtObj.setPromotersCapRunFlagL2("");	
			}

			if(!"".equals(lmtDetailForm.getEscodLevelThreeDelayDate()) && lmtDetailForm.getEscodLevelThreeDelayDate() != null ) {
			lmtObj.setPromotersCapRunFlagL3(lmtDetailForm.getPromotersCapRunFlagL3());
			}else {
				lmtObj.setPromotersCapRunFlagL3("");	
			}
			if(!"".equals(lmtDetailForm.getEscodLevelSecondDelayDate()) && lmtDetailForm.getEscodLevelSecondDelayDate() != null ) {
			lmtObj.setChangeInScopeBeforeSCODFlagL2(lmtDetailForm.getChangeInScopeBeforeSCODFlagL2());
			}else {
				lmtObj.setChangeInScopeBeforeSCODFlagL2("");	
			}
			if(!"".equals(lmtDetailForm.getEscodLevelThreeDelayDate()) && lmtDetailForm.getEscodLevelThreeDelayDate() != null ) {
			lmtObj.setChangeInScopeBeforeSCODFlagL3(lmtDetailForm.getChangeInScopeBeforeSCODFlagL3());
			}else {
				lmtObj.setChangeInScopeBeforeSCODFlagL3("");	
			}
			if(!"".equals(lmtDetailForm.getEscodLevelSecondDelayDate()) && lmtDetailForm.getEscodLevelSecondDelayDate() != null ) {
			lmtObj.setPromotersHoldEquityFlagL2(lmtDetailForm.getPromotersHoldEquityFlagL2());
			}else {
				lmtObj.setPromotersHoldEquityFlagL2("");	
			}
			if(!"".equals(lmtDetailForm.getEscodLevelThreeDelayDate()) && lmtDetailForm.getEscodLevelThreeDelayDate() != null ) {
			lmtObj.setPromotersHoldEquityFlagL3(lmtDetailForm.getPromotersHoldEquityFlagL3());
			}else {
				lmtObj.setPromotersHoldEquityFlagL3("");	
			}
			if(!"".equals(lmtDetailForm.getEscodLevelSecondDelayDate()) && lmtDetailForm.getEscodLevelSecondDelayDate() != null ) {
			lmtObj.setCostOverrunOrg25CostViabilityFlagL2(lmtDetailForm.getCostOverrunOrg25CostViabilityFlagL2());
			}else {
				lmtObj.setCostOverrunOrg25CostViabilityFlagL2("");	
			}
			if(!"".equals(lmtDetailForm.getEscodLevelThreeDelayDate()) && lmtDetailForm.getEscodLevelThreeDelayDate() != null ) {
			lmtObj.setCostOverrunOrg25CostViabilityFlagL3(lmtDetailForm.getCostOverrunOrg25CostViabilityFlagL3());
			}else {
				lmtObj.setCostOverrunOrg25CostViabilityFlagL3("");	
			}
			if(!"".equals(lmtDetailForm.getEscodLevelSecondDelayDate()) && lmtDetailForm.getEscodLevelSecondDelayDate() != null ) {
			lmtObj.setHasProjectViabReAssFlagL2(lmtDetailForm.getHasProjectViabReAssFlagL2());
			}else {
				lmtObj.setHasProjectViabReAssFlagL2("");	
			}
			if(!"".equals(lmtDetailForm.getEscodLevelThreeDelayDate()) && lmtDetailForm.getEscodLevelThreeDelayDate() != null ) {
			lmtObj.setHasProjectViabReAssFlagL3(lmtDetailForm.getHasProjectViabReAssFlagL3());
			}else {
				lmtObj.setHasProjectViabReAssFlagL3("");	
			}
			if(!"".equals(lmtDetailForm.getEscodLevelSecondDelayDate()) && lmtDetailForm.getEscodLevelSecondDelayDate() != null ) {
			lmtObj.setProjectViabReassesedFlagL2(lmtDetailForm.getProjectViabReassesedFlagL2());
			}else {
				lmtObj.setProjectViabReassesedFlagL2("");	
			}
			if(!"".equals(lmtDetailForm.getEscodLevelThreeDelayDate()) && lmtDetailForm.getEscodLevelThreeDelayDate() != null ) {
			lmtObj.setProjectViabReassesedFlagL3(lmtDetailForm.getProjectViabReassesedFlagL3());
			}else {
				lmtObj.setProjectViabReassesedFlagL3("");	
			}
			
			lmtObj.setRevsedESCODStpDateL2(DateUtil.convertDate(locale, lmtDetailForm.getRevsedESCODStpDateL2()));

			
			lmtObj.setRevsedESCODStpDateL3(DateUtil.convertDate(locale,lmtDetailForm.getRevsedESCODStpDateL3()));


			lmtObj.setRevsedESCODStpDate(DateUtil.convertDate(locale,lmtDetailForm.getRevsedESCODStpDate()));
			
			
		
			lmtObj.setWhlmreupSCOD(lmtDetailForm.getWhlmreupSCOD());
			
			
			
			

			
			

			lmtObj.setRevisedAssetClass(lmtDetailForm.getRevisedAssetClass());
			
			
			lmtObj.setRevsdAssClassDate(DateUtil.convertDate(locale,lmtDetailForm.getRevsdAssClassDate()));
			
			
			lmtObj.setActualCommOpsDate(DateUtil.convertDate(locale,lmtDetailForm.getActualCommOpsDate()));
			
			if("N".equals(lmtObj.getWhlmreupSCOD())){
				lmtObj.setLelvelDelaySCOD("");	
				lmtDetailForm.setLelvelDelaySCOD("");	
			}
			
			
			if(!"".equals(lmtDetailForm.getEscodLevelOneDelayDate()) && lmtDetailForm.getEscodLevelOneDelayDate() != null && "1".equals(lmtDetailForm.getLelvelDelaySCOD())) {
			lmtObj.setLelvelDelaySCOD(lmtDetailForm.getLelvelDelaySCOD());
			}else if(!"".equals(lmtDetailForm.getEscodLevelSecondDelayDate()) && lmtDetailForm.getEscodLevelSecondDelayDate() != null && "2".equals(lmtDetailForm.getLelvelDelaySCOD())) {
				lmtObj.setLelvelDelaySCOD(lmtDetailForm.getLelvelDelaySCOD());
			}else if(!"".equals(lmtDetailForm.getEscodLevelThreeDelayDate()) && lmtDetailForm.getEscodLevelThreeDelayDate() != null && "3".equals(lmtDetailForm.getLelvelDelaySCOD())) {
				lmtObj.setLelvelDelaySCOD(lmtDetailForm.getLelvelDelaySCOD());
			}else if("".equals(lmtDetailForm.getLelvelDelaySCOD()) || lmtDetailForm.getLelvelDelaySCOD() == null) {
				lmtObj.setLelvelDelaySCOD(lmtDetailForm.getLelvelDelaySCOD());
			}
			
			
			if(!"".equals(lmtDetailForm.getEscodLevelOneDelayDate()) && lmtDetailForm.getEscodLevelOneDelayDate() != null ) {
			lmtObj.setPrincipalInterestSchFlag(lmtDetailForm.getPrincipalInterestSchFlag());
			
			
			lmtObj.setProjectDelayedFlag(lmtDetailForm.getProjectDelayedFlag());
			
			
			lmtObj.setReasonLevelOneDelay(lmtDetailForm.getReasonLevelOneDelay());
			
			
			lmtObj.setChngInRepaySchedule(lmtDetailForm.getChngInRepayScheduleL1());

			}else {
				lmtObj.setPrincipalInterestSchFlag("");
				
				
				//lmtObj.setProjectDelayedFlag("");
				
				
				lmtObj.setReasonLevelOneDelay("");
				
				
				lmtObj.setChngInRepaySchedule("");	
			}
			
			lmtObj.setEscodLevelOneDelayDate(DateUtil.convertDate(locale,lmtDetailForm.getEscodLevelOneDelayDate()));

			
			lmtObj.setEscodLevelSecondDelayDate(DateUtil.convertDate(locale,lmtDetailForm.getEscodLevelSecondDelayDate()));


			lmtObj.setReasonLevelThreeDelay(lmtDetailForm.getReasonLevelThreeDelay());

			
			lmtObj.setEscodLevelThreeDelayDate(DateUtil.convertDate(locale,lmtDetailForm.getEscodLevelThreeDelayDate()));


			lmtObj.setLegalOrArbitrationLevel2Flag(lmtDetailForm.getLegalOrArbitrationLevel2Flag());


			lmtObj.setChngOfOwnPrjFlagNonInfraLevel2(lmtDetailForm.getChngOfOwnPrjFlagNonInfraLevel2());

			
			lmtObj.setReasonBeyondPromoterLevel2Flag(lmtDetailForm.getReasonBeyondPromoterLevel2Flag());

			
			lmtObj.setChngOfProjScopeNonInfraLevel2(lmtDetailForm.getChngOfProjScopeNonInfraLevel2());

			
			lmtObj.setChngOfOwnPrjFlagInfraLevel2(lmtDetailForm.getChngOfOwnPrjFlagInfraLevel2());


			lmtObj.setChngOfProjScopeInfraLevel2(lmtDetailForm.getChngOfProjScopeInfraLevel2());

			
			lmtObj.setLegalOrArbitrationLevel3Flag(lmtDetailForm.getLegalOrArbitrationLevel3Flag());

			
			lmtObj.setChngOfOwnPrjFlagNonInfraLevel3(lmtDetailForm.getChngOfOwnPrjFlagNonInfraLevel3());

			
			lmtObj.setReasonBeyondPromoterLevel3Flag(lmtDetailForm.getReasonBeyondPromoterLevel3Flag());

			
			lmtObj.setChngOfProjScopeNonInfraLevel3(lmtDetailForm.getChngOfProjScopeNonInfraLevel3());


			lmtObj.setChngOfOwnPrjFlagInfraLevel3(lmtDetailForm.getChngOfOwnPrjFlagInfraLevel3());

			
			lmtObj.setChngOfProjScopeInfraLevel3(lmtDetailForm.getChngOfProjScopeInfraLevel3());

			
			lmtObj.setLeaglOrArbiProceed(lmtDetailForm.getLeaglOrArbiProceed());


			lmtObj.setDetailsRsnByndPro(lmtDetailForm.getDetailsRsnByndPro());
			

			lmtObj.setRecvPriorOfSCOD(lmtDetailForm.getRecvPriorOfSCOD());
			
			
			
			lmtObj.setReasonLevelTwoDelay(lmtDetailForm.getReasonLevelTwoDelay());
			
			if(!"".equals(lmtDetailForm.getEscodLevelOneDelayDate()) && lmtDetailForm.getEscodLevelOneDelayDate() != null ) {

			lmtObj.setPromotersCapRunFlag(lmtDetailForm.getPromotersCapRunFlag());

			
			
			lmtObj.setChangeInScopeBeforeSCODFlag(lmtDetailForm.getChangeInScopeBeforeSCODFlag());
			
			
			
			lmtObj.setPromotersHoldEquityFlag(lmtDetailForm.getPromotersHoldEquityFlag());
			

			
			lmtObj.setCostOverrunOrg25CostViabilityFlag(lmtDetailForm.getCostOverrunOrg25CostViabilityFlag());
			

			
			
			lmtObj.setHasProjectViabReAssFlag(lmtDetailForm.getHasProjectViabReAssFlag());
			

			
			lmtObj.setProjectViabReassesedFlag(lmtDetailForm.getProjectViabReassesedFlag());
			}else {
				lmtObj.setPromotersCapRunFlag("");

				
				
				lmtObj.setChangeInScopeBeforeSCODFlag("");
				
				
				
				lmtObj.setPromotersHoldEquityFlag("");
				

				
				lmtObj.setCostOverrunOrg25CostViabilityFlag("");
				

				
				
				lmtObj.setHasProjectViabReAssFlag("");
				

				
				lmtObj.setProjectViabReassesedFlag("");	
			}
			
			}else if("".equals(lmtDetailForm.getLelvelDelaySCOD()) || lmtDetailForm.getLelvelDelaySCOD() == null){
				

				
				/*lmtObj.setExstAssetClassL1("");

				
				lmtObj.setExstAssClassDateL1(null);
		
				lmtObj.setExstAssetClassL2("");
				

				lmtObj.setExstAssClassDateL2(null);
						
				
				lmtObj.setExstAssetClassL3("");
				
				
				lmtObj.setExstAssClassDateL3(null);


				lmtObj.setRevisedAssetClassL1("");
				
				
				lmtObj.setRevsdAssClassDateL1(null);
				
				
				lmtObj.setRevisedAssetClass_L2("");
				

				lmtObj.setRevsdAssClassDate_L2(null);
				
							
				lmtObj.setRevisedAssetClass_L3("");
				
				
				lmtObj.setRevsdAssClassDate_L3(null);
				
				
				lmtObj.setProjectDelayedFlagL2("");


				lmtObj.setProjectDelayedFlagL3("");
				
				
				lmtObj.setLeaglOrArbiProceedLevel3("");

				
				lmtObj.setDetailsRsnByndProLevel3("");
				
				
				lmtObj.setChngInRepayScheduleL2("");
				
							
				lmtObj.setChngInRepayScheduleL3("");

				
				lmtObj.setPromotersCapRunFlagL2("");


				lmtObj.setPromotersCapRunFlagL3("");
				

				lmtObj.setChangeInScopeBeforeSCODFlagL2("");
				
				
				lmtObj.setChangeInScopeBeforeSCODFlagL3("");
				
				
				lmtObj.setPromotersHoldEquityFlagL2("");
				
			
				lmtObj.setPromotersHoldEquityFlagL3("");
				

				lmtObj.setCostOverrunOrg25CostViabilityFlagL2("");
				
				
				lmtObj.setCostOverrunOrg25CostViabilityFlagL3("");
				
				
				lmtObj.setHasProjectViabReAssFlagL2("");
				
			
				lmtObj.setHasProjectViabReAssFlagL3("");
				

				lmtObj.setProjectViabReassesedFlagL2("");
				

				lmtObj.setProjectViabReassesedFlagL3("");
				
				
				lmtObj.setRevsedESCODStpDateL2(null);

				
				lmtObj.setRevsedESCODStpDateL3(null);


				lmtObj.setRevsedESCODStpDate(null);
				
				
*/			
				lmtObj.setWhlmreupSCOD(lmtDetailForm.getWhlmreupSCOD());		
				

				lmtObj.setRevisedAssetClass(lmtDetailForm.getRevisedAssetClass());
				
				if(!"".equals(lmtDetailForm.getRevsdAssClassDate())) {
				lmtObj.setRevsdAssClassDate(DateUtil.convertDate(locale,lmtDetailForm.getRevsdAssClassDate()));
				}else {
					lmtObj.setRevsdAssClassDate(null);	
				}
				
				if("N".equals(lmtObj.getWhlmreupSCOD())){
					lmtDetailForm.setActualCommOpsDate("");
				}
				if(!"".equals(lmtDetailForm.getActualCommOpsDate())) {
				lmtObj.setActualCommOpsDate(DateUtil.convertDate(locale,lmtDetailForm.getActualCommOpsDate()));
				}else {
					lmtObj.setActualCommOpsDate(null);	
				}
				lmtObj.setActualCommOpsDate(DateUtil.convertDate(locale,lmtDetailForm.getActualCommOpsDate()));
				lmtObj.setLelvelDelaySCOD("");
				
				lmtObj.setProjectDelayedFlag(lmtDetailForm.getProjectDelayedFlag());
				
				/*lmtObj.setPrincipalInterestSchFlag("");
				
				
				lmtObj.setProjectDelayedFlag("");
				
				
				lmtObj.setReasonLevelOneDelay("");
*/				
				
				lmtObj.setChngInRepaySchedule(lmtDetailForm.getChngInRepaySchedule());
				
				
				/*lmtObj.setEscodLevelOneDelayDate(null);

				
				lmtObj.setEscodLevelSecondDelayDate(null);


				lmtObj.setReasonLevelThreeDelay("");

				
				lmtObj.setEscodLevelThreeDelayDate(null);


				lmtObj.setLegalOrArbitrationLevel2Flag("");


				lmtObj.setChngOfOwnPrjFlagNonInfraLevel2("");

				
				lmtObj.setReasonBeyondPromoterLevel2Flag("");

				
				lmtObj.setChngOfProjScopeNonInfraLevel2("");

				
				lmtObj.setChngOfOwnPrjFlagInfraLevel2("");


				lmtObj.setChngOfProjScopeInfraLevel2("");

				
				lmtObj.setLegalOrArbitrationLevel3Flag("");

				
				lmtObj.setChngOfOwnPrjFlagNonInfraLevel3("");

				
				lmtObj.setReasonBeyondPromoterLevel3Flag("");

				
				lmtObj.setChngOfProjScopeNonInfraLevel3("");


				lmtObj.setChngOfOwnPrjFlagInfraLevel3("");

				
				lmtObj.setChngOfProjScopeInfraLevel3("");

				
				lmtObj.setLeaglOrArbiProceed("");


				lmtObj.setDetailsRsnByndPro("");
				

				lmtObj.setRecvPriorOfSCOD("");
				
				
				
				lmtObj.setReasonLevelTwoDelay("");
				
				
				lmtObj.setPromotersCapRunFlag("");

				
				
				lmtObj.setChangeInScopeBeforeSCODFlag("");
				
				
				
				lmtObj.setPromotersHoldEquityFlag("");
				

				
				lmtObj.setCostOverrunOrg25CostViabilityFlag("");
				

				
				
				lmtObj.setHasProjectViabReAssFlag("");
				

				
				lmtObj.setProjectViabReassesedFlag("");
				
*/				
				
			}
			}
	    	}
			}
			

			lmtObj.setBankingArrangement(lmtDetailForm.getBankingArrangement());
			lmtObj.setClauseAsPerPolicy(lmtDetailForm.getClauseAsPerPolicy());

			mapCoBorrowerFormToOB(lmtDetailForm, lmtObj, inputs);
			

			lmtObj.setIsDPRequired(lmtDetailForm.getIsDPRequired());
			if(StringUtils.isNotBlank(lmtDetailForm.getTenor()) && StringUtils.isNumeric(lmtDetailForm.getTenor()))
				lmtObj.setTenor(Long.valueOf(lmtDetailForm.getTenor()));
			
			if(StringUtils.isNotBlank(lmtDetailForm.getTenorUnit()))
				lmtObj.setTenorUnit(lmtDetailForm.getTenorUnit());
			
			if(StringUtils.isNotBlank(lmtDetailForm.getMargin()))
				lmtObj.setMargin(MapperUtil.mapStringToDouble(lmtDetailForm.getMargin(),locale));
			
			if(StringUtils.isNotBlank(lmtDetailForm.getTenorDesc()))
				lmtObj.setTenorDesc(lmtDetailForm.getTenorDesc());
			
			if(StringUtils.isNotBlank(lmtDetailForm.getPutCallOption()))
				lmtObj.setPutCallOption(lmtDetailForm.getPutCallOption());
			
			if(StringUtils.isNotBlank(lmtDetailForm.getLoanAvailabilityDate()))
				lmtObj.setLoanAvailabilityDate(DateUtil.convertDate(locale,lmtDetailForm.getLoanAvailabilityDate()));
			
			if(StringUtils.isNotBlank(lmtDetailForm.getOptionDate()))
				lmtObj.setOptionDate(DateUtil.convertDate(locale,lmtDetailForm.getOptionDate()));
			

			return lmtObj;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException();
		}
	}

	private ILimit getLmtObject(HashMap inputs) {
		ILimitTrxValue trxValue = (ILimitTrxValue) (inputs.get("lmtTrxObj"));
		String event = (String) (inputs.get("event"));
		// map form to object will always map to staging object
		return trxValue.getStagingLimit();
	}
	
	private ILimit getAtualLmtObject(HashMap inputs) {
		ILimitTrxValue trxValue = (ILimitTrxValue) (inputs.get("lmtTrxObj"));
		String event = (String) (inputs.get("event"));
		// map form to object will always map to staging object
		return trxValue.getLimit();
	}
	//Start:Code added to display Relationship Mgr Name instead of Relationship Mgr Code
	public IRelationshipMgr getRelationshipMgr(String relMgrId) {
			IRelationshipMgrDAO relationshipmgr = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
			return	relationshipmgr.getRelationshipMgrByCode(relMgrId);
	}

}
