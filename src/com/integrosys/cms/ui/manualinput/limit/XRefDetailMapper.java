/*
 * Created on 2007-2-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.ILimitXRefCoBorrower;
import com.integrosys.cms.app.customer.bus.ILimitXRefUdf;
import com.integrosys.cms.app.customer.bus.ILimitXRefUdf2;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBLimitXRefCoBorrower;
import com.integrosys.cms.app.customer.bus.OBLimitXRefUdf;
import com.integrosys.cms.app.customer.bus.OBLimitXRefUdf2;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class XRefDetailMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "curAccount", "com.integrosys.cms.app.customer.bus.ICustomerSysXRef", SERVICE_SCOPE },
				{ "restCoBorrowerList", "java.util.List", SERVICE_SCOPE },				
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.IMapper#mapOBToForm(com.integrosys
	 * .base.uiinfra.common.CommonForm, java.lang.Object, java.util.HashMap)
	 */
	public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs) throws MapperException {
		List newCoBorrowerList = (ArrayList) (inputs.get("restCoBorrowerList"));
		ILimitTrxValue lmtTrxObj = (ILimitTrxValue) (inputs.get("lmtTrxObj"));
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {
			MILimitUIHelper helper = new MILimitUIHelper();
			OBCustomerSysXRef account = (OBCustomerSysXRef) obj;
			XRefDetailForm xrefDetailForm = (XRefDetailForm) commonForm;
			xrefDetailForm.setHostSystemCountry(account.getExternalSysCountry());
			xrefDetailForm.setHostSystemCountryDisp(helper.getHostSystemCountryDisp(account.getExternalSysCountry()));
			xrefDetailForm.setHostSystemName(account.getExternalSystemCode());
			xrefDetailForm.setHostSystemNameDisp(helper.getHostSystemNameDisp(account.getExternalSysCountry(), account
					.getExternalSystemCode()));
			xrefDetailForm.setAccountNo(account.getExternalAccountNo());
			if (account.getAcctEffectiveDate() != null) {
				xrefDetailForm.setEffectiveDate(DateUtil.formatDate(locale, account.getAcctEffectiveDate()));
			}
			xrefDetailForm.setAcctClassification(helper.getAccountClassification(account.getAccountDelinq()));
			if (account.getAccountStatus() != null) {
				xrefDetailForm.setAcctStatus(helper.getAccountStatusDisp(account.getAccountStatus()));
			}
			if (account.getRVForAccount() != null) {
				xrefDetailForm.setRealizableVal(CurrencyManager.convertToString(locale, account.getRVForAccount()));
				xrefDetailForm.setRvCcy(account.getRVForAccount().getCurrencyCode());
			}
			
			

			//Added by Shiv
			
			if("updateStatus_ubs_error".equals(xrefDetailForm.getEvent()) || "updateStatus_ts_error".equals(xrefDetailForm.getEvent())){
				
			}else{
			xrefDetailForm.setSerialNo(account.getSerialNo());
			}
			xrefDetailForm.setInterestRateType(account.getInterestRateType());
			xrefDetailForm.setIntRateFloatingType(account.getIntRateFloatingType());
			xrefDetailForm.setIntRateFloatingRange(account.getIntRateFloatingRange());
			xrefDetailForm.setIntRateFloatingMarginFlag(account.getIntRateFloatingMarginFlag());
			xrefDetailForm.setIntRateFloatingMargin(account.getIntRateFloatingMargin());
			xrefDetailForm.setIntRateFix(account.getIntRateFix());
			if(account.getReleasedAmount() == null || account.getReleasedAmount().equals("")){
				account.setReleasedAmount("0");
			}
			//xrefDetailForm.setReleasedAmount(account.getReleasedAmount());
			if(account.getUtilizedAmount() == null || account.getUtilizedAmount().equals("")){
				account.setUtilizedAmount("0");
			}
			//xrefDetailForm.setUtilizedAmount(account.getUtilizedAmount());
			
			//Phase 3 CR:comma separated
			xrefDetailForm.setReleasedAmount(UIUtil.formatWithCommaAndDecimal(account.getReleasedAmount()));
			xrefDetailForm.setUtilizedAmount(UIUtil.formatWithCommaAndDecimal(account.getUtilizedAmount()));
			
			xrefDetailForm.setReleaseDate(DateUtil.formatDate(locale, account.getReleaseDate()));
			xrefDetailForm.setDateOfReset(DateUtil.formatDate(locale, account.getDateOfReset()));
			
			xrefDetailForm.setIntradayLimitExpiryDate(DateUtil.formatDate(locale, account.getIntradayLimitExpiryDate()));
			xrefDetailForm.setDayLightLimit(UIUtil.formatWithCommaAndDecimal(account.getDayLightLimit()));
			xrefDetailForm.setIntradayLimitFlag(account.getIntradayLimitFlag());
			
			xrefDetailForm.setIsPermntSSICert(account.getIsPermntSSICert());
			xrefDetailForm.setIsCapitalMarketExposer(account.getIsCapitalMarketExposer());
			xrefDetailForm.setIsRealEstateExposer(account.getIsRealEstateExposer());
			xrefDetailForm.setEstateType(account.getEstateType());
			xrefDetailForm.setIsPrioritySector(account.getIsPrioritySector());
			xrefDetailForm.setPrioritySector(account.getPrioritySector());
			xrefDetailForm.setCommRealEstateType(account.getCommRealEstateType());
			xrefDetailForm.setSecurity1(account.getSecurity1());
			xrefDetailForm.setSecurity2(account.getSecurity2());
			xrefDetailForm.setSecurity3(account.getSecurity3());
			xrefDetailForm.setSecurity4(account.getSecurity4());
			xrefDetailForm.setSecurity5(account.getSecurity5());
			xrefDetailForm.setSecurity6(account.getSecurity6());
			xrefDetailForm.setFacilitySystem(account.getFacilitySystem());
			xrefDetailForm.setFacilitySystemID(account.getFacilitySystemID());
			xrefDetailForm.setLineNo(account.getLineNo());
			xrefDetailForm.setLiabBranch(account.getLiabBranch());
			if("UBS-LIMITS".equalsIgnoreCase(account.getFacilitySystem()) || "FCUBS-LIMITS".equalsIgnoreCase(account.getFacilitySystem())) {
				xrefDetailForm.setIsDayLightLimitAvailable(account.getIsDayLightLimitAvailable());
				xrefDetailForm.setScmFlag(account.getScmFlag());
				xrefDetailForm.setVendorDtls(account.getVendorDtls());
			}
			
			xrefDetailForm.setDayLightLimitApproved(account.getDayLightLimitApproved());
			
			if("N".equalsIgnoreCase(account.getCurrencyRestriction())){
				xrefDetailForm.setCurrencyRestriction("N");
			}else{
				xrefDetailForm.setCurrencyRestriction("Y");
			}
			//xrefDetailForm.setCurrencyRestriction(account.getCurrencyRestriction());
			
			xrefDetailForm.setMainLineCode(account.getMainLineCode());
			
			xrefDetailForm.setCurrency(account.getCurrency());
			
//			xrefDetailForm.setLimitExpiryDate(DateUtil.formatDate(locale, account.getLimitExpiryDate()));
			//xrefDetailForm.setLimitExpiryDate(account.getLimitExpiryDate());
			
			xrefDetailForm.setAvailable(account.getAvailable());
			
			xrefDetailForm.setFreeze(account.getFreeze());
			xrefDetailForm.setAdhocLine(account.getAdhocLine());
			
			xrefDetailForm.setRevolvingLine(account.getRevolvingLine());
			
			if("N".equalsIgnoreCase(account.getCloseFlag())){
				xrefDetailForm.setCloseFlag("N");
			}else{
				xrefDetailForm.setCloseFlag("Y");
			}
			//xrefDetailForm.setCloseFlag(account.getCloseFlag());
			
			xrefDetailForm.setLastavailableDate(DateUtil.formatDate(locale, account.getLastavailableDate()));
			//xrefDetailForm.setLastavailableDate(account.getLastAllocationDate());
			
			xrefDetailForm.setUploadDate(DateUtil.formatDate(locale, account.getUploadDate()));
			//xrefDetailForm.setUploadDate(account.getUploadDate());
			
			xrefDetailForm.setSegment(account.getSegment());
			
			xrefDetailForm.setRuleId(account.getRuleId());
			
			xrefDetailForm.setUncondiCancl(account.getUncondiCancl());
			
//			xrefDetailForm.setInterestRate(account.getInterestRate());
			
			xrefDetailForm.setLimitTenorDays(account.getLimitTenorDays());
			
			xrefDetailForm.setInternalRemarks(account.getInternalRemarks());
			
//			xrefDetailForm.setRemark(account.getRemark());
			xrefDetailForm.setSendToCore(account.getSendToCore());
			if("updateStatus_ubs_error".equals(xrefDetailForm.getEvent()) || "updateStatus_ts_error".equals(xrefDetailForm.getEvent())){
				
			}else{
			xrefDetailForm.setCoreStpRejectedReason(account.getCoreStpRejectedReason());
			xrefDetailForm.setStatus(account.getStatus());
			}
			xrefDetailForm.setAction(account.getAction());
			xrefDetailForm.setLineAction(account.getAction());
			
			xrefDetailForm.setSourceRefNo(account.getSourceRefNo());
			xrefDetailForm.setBranchAllowed(account.getBranchAllowed());
			xrefDetailForm.setProductAllowed(account.getProductAllowed());
			xrefDetailForm.setCurrencyAllowed(account.getCurrencyAllowed());
			xrefDetailForm.setLimitRestriction(account.getLimitRestriction());
			
			xrefDetailForm.setSendToFile(account.getSendToFile());
			
			xrefDetailForm.setLimitStartDate(DateUtil.formatDate(locale,account.getLimitStartDate()));
			
			
			if (account.getXRefID() > 0) {
				ILimitTrxValue trxValue = (ILimitTrxValue) (inputs.get("lmtTrxObj"));
				ILimit stgLmt = trxValue.getStagingLimit();
				if ((stgLmt != null) && (stgLmt.getApprovedLimitAmount() != null)
						&& (stgLmt.getApprovedLimitAmount().getCurrencyCode() != null)) {
					Amount amt = helper.getSBMILmtProxy().getNetoutStandingForAccount(
							String.valueOf(account.getXRefID()), stgLmt.getApprovedLimitAmount().getCurrencyCode());
					if (amt != null) {
						xrefDetailForm.setNetOutstandingAmt(CurrencyManager.convertToString(locale, amt));
						xrefDetailForm.setNetOutstandingCcy(amt.getCurrencyCode());
					}
				}
			}
			//Start:Code added for Upload Status by Abhijeet J
			if(account.getUploadStatus() == null || account.getUploadStatus().equals("")){
				account.setUploadStatus("N");			
			}
			xrefDetailForm.setUploadStatus(account.getUploadStatus());
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			//file upload audit trail at tranch level
			if (account.getCreatedOn() != null) {
			xrefDetailForm.setCreatedOn(df.format(account.getCreatedOn()));
			}
			if (account.getUpdatedOn() != null) {
			xrefDetailForm.setUpdatedOn(df.format(account.getUpdatedOn()));
			}
			xrefDetailForm.setCreatedBy(account.getCreatedBy());
			xrefDetailForm.setUpdatedBy(account.getUpdatedBy());
			
			xrefDetailForm.setHiddenSerialNo(account.getHiddenSerialNo());
			xrefDetailForm.setEdited(account.getEdited());
			
			xrefDetailForm.setTenure(account.getTenure());
			xrefDetailForm.setSellDownPeriod(account.getSellDownPeriod());
			xrefDetailForm.setLiabilityId(account.getLiabilityId());
			xrefDetailForm.setLimitRemarks(account.getLimitRemarks());
			
			xrefDetailForm.setUtilizedAmount(account.getUtilizedAmount());
			xrefDetailForm.setBankingArrangement(account.getBankingArrangement());
			
			if (null != account.getIdlEffectiveFromDate() && !"".equals(account.getIdlEffectiveFromDate())) {
				xrefDetailForm.setIdlEffectiveFromDate(DateUtil.formatDate(locale,account.getIdlEffectiveFromDate()));
			}else {
				xrefDetailForm.setIdlEffectiveFromDate(null);
			}
			
			if (null != account.getIdlExpiryDate()  && !"".equals(account.getIdlExpiryDate())) {
				xrefDetailForm.setIdlExpiryDate(DateUtil.formatDate(locale,account.getIdlExpiryDate()));
			}else {
				xrefDetailForm.setIdlExpiryDate(null);
			}
			//xrefDetailForm.setIdlEffectiveFromDate(DateUtil.formatDate(locale,account.getIdlEffectiveFromDate()));
//			xrefDetailForm.setIdlExpiryDate(DateUtil.formatDate(locale,account.getIdlExpiryDate()));
			xrefDetailForm.setIdlAmount(UIUtil.formatWithCommaAndDecimal(account.getIdlAmount()));
			
			//End   :Code added for Upload Status by Abhijeet J
			List newCoBorrowerList1 = (ArrayList) (inputs.get("restCoBorrowerList"));
			//System.out.println("newCoBorrowerList====================="+newCoBorrowerList);
			xrefDetailForm.setRestCoBorrowerList(newCoBorrowerList1);
			addCoBorrowerToForm(xrefDetailForm, account);
			
			//Start Santosh UBS LIMIT UPLOAD
			if(null!=account.getUdfAllowed() && null==xrefDetailForm.getUdfAllowed())
				xrefDetailForm.setUdfAllowed(account.getUdfAllowed());
			if(!"edit_released_line_details".equals(xrefDetailForm.getEvent()))
				this.addUdfToForm(xrefDetailForm, account);
			//End Santosh UBS LIMIT UPLOAD
			
			//Start UBS LIMIT UPLOAD-2
			if(null!=account.getUdfAllowed_2() && null==xrefDetailForm.getUdfAllowed_2())
				xrefDetailForm.setUdfAllowed_2(account.getUdfAllowed_2());
			if(!"edit_released_line_details".equals(xrefDetailForm.getEvent()))
				this.addUdfToForm_2(xrefDetailForm, account);
			//End UBS LIMIT UPLOAD-2
			
			if(null!= lmtTrxObj){
				LimitDAO limitDAO=new LimitDAO();
				ILimit stglimit = (ILimit)lmtTrxObj.getStagingLimit();
				if (stglimit != null){
				xrefDetailForm.setReleaseableAmountCheck(stglimit.getReleasableAmount());
				String facilityCode = stglimit.getFacilityCode();
				System.out.println("XrefMapper.java =>stglimit is not null..facilityCode=>"+facilityCode);
				xrefDetailForm.setIdlApplicableFlagCheck(limitDAO.getIDLApplicableFlagFacilityMaster(facilityCode));
				System.out.println("XrefMapper.java =>IdlApplicableFlagCheck=>"+xrefDetailForm.getIdlApplicableFlagCheck()); 
				}
			}
			
			return xrefDetailForm; 
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException();
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
		try {
			XRefDetailForm xrefDetailForm = (XRefDetailForm) commonForm;
			OBCustomerSysXRef account = (OBCustomerSysXRef)getCustomerSysXRef(inputs);
			
			if(null== account) {
				 account=new OBCustomerSysXRef();

			}
		
				if("updateStatus_ubs".equals(xrefDetailForm.getEvent()) || "updateStatus_ts".equals(xrefDetailForm.getEvent())){
				
						account.setStatus(xrefDetailForm.getStatus());
						account.setCoreStpRejectedReason(xrefDetailForm.getCoreStpRejectedReason());
						if(null==xrefDetailForm.getAction() && null!=xrefDetailForm.getLineAction()){
							xrefDetailForm.setAction(xrefDetailForm.getLineAction());
						}
						
						account.setAction(xrefDetailForm.getAction());
					if("NEW".equals(xrefDetailForm.getAction()) && "PENDING_SUCCESS".equals(xrefDetailForm.getStatus())){
						account.setSerialNo(xrefDetailForm.getSerialNo());
						account.setHiddenSerialNo(xrefDetailForm.getSerialNo());
					}
				}else{
				account.setExternalSystemCodeNum(ICMSConstant.CATEGORY_SOURCE_SYSTEM);
				account.setExternalSysCountry(xrefDetailForm.getHostSystemCountry());
				account.setExternalSystemCode(xrefDetailForm.getHostSystemName());
				account.setExternalAccountNo(xrefDetailForm.getAccountNo());
				
				//Added by Shiv
				if(xrefDetailForm.getSerialNo()!=null){
				account.setSerialNo(xrefDetailForm.getSerialNo().trim());
				}
				account.setInterestRateType(xrefDetailForm.getInterestRateType());
				account.setIntRateFloatingType(xrefDetailForm.getIntRateFloatingType());
				account.setIntRateFloatingRange(xrefDetailForm.getIntRateFloatingRange());
				account.setIntRateFloatingMarginFlag(xrefDetailForm.getIntRateFloatingMarginFlag());
				account.setIntRateFloatingMargin(xrefDetailForm.getIntRateFloatingMargin());
				account.setIntRateFix(xrefDetailForm.getIntRateFix());
			//	account.setReleasedAmount(xrefDetailForm.getReleasedAmount());
				if(xrefDetailForm.getUtilizedAmount()==null || xrefDetailForm.getUtilizedAmount().equals("")){
					xrefDetailForm.setUtilizedAmount("0");
				}
			//	account.setUtilizedAmount(xrefDetailForm.getUtilizedAmount());
				
				//Phase 3 CR:comma separated
				account.setReleasedAmount(UIUtil.removeComma(xrefDetailForm.getReleasedAmount()));
				account.setUtilizedAmount(UIUtil.removeComma(xrefDetailForm.getUtilizedAmount()));
				
				if (!AbstractCommonMapper.isEmptyOrNull(xrefDetailForm.getReleaseDate())) {
					account.setReleaseDate(DateUtil.convertDate(locale, xrefDetailForm.getReleaseDate()));
				}else {
					account.setReleaseDate(null);
				}
				if (!AbstractCommonMapper.isEmptyOrNull(xrefDetailForm.getDateOfReset())) {
					account.setDateOfReset(DateUtil.convertDate(locale, xrefDetailForm.getDateOfReset()));
				} else{
					account.setDateOfReset(null);
				}
				

				if (!AbstractCommonMapper.isEmptyOrNull(xrefDetailForm.getIntradayLimitExpiryDate())) {
						account.setIntradayLimitExpiryDate(DateUtil.convertDate(locale, xrefDetailForm.getIntradayLimitExpiryDate()));
					}else {
						account.setIntradayLimitExpiryDate(null);
					}
				
				account.setDayLightLimit(UIUtil.removeComma(xrefDetailForm.getDayLightLimit()));
				account.setIntradayLimitFlag(xrefDetailForm.getIntradayLimitFlag());
				account.setIsPermntSSICert(xrefDetailForm.getIsPermntSSICert());
				account.setIsCapitalMarketExposer(xrefDetailForm.getIsCapitalMarketExposer());
				account.setIsRealEstateExposer(xrefDetailForm.getIsRealEstateExposer());
				account.setEstateType(xrefDetailForm.getEstateType());
				account.setIsPrioritySector(xrefDetailForm.getIsPrioritySector());
				
				if("UBS-LIMITS".equalsIgnoreCase(xrefDetailForm.getFacilitySystem()) || "FCUBS-LIMITS".equalsIgnoreCase(xrefDetailForm.getFacilitySystem())) {
					account.setIsDayLightLimitAvailable(xrefDetailForm.getIsDayLightLimitAvailable());	
					account.setScmFlag(xrefDetailForm.getScmFlag());
					account.setVendorDtls(xrefDetailForm.getVendorDtls());
				}
				
				if("on".equalsIgnoreCase(xrefDetailForm.getDayLightLimitApproved())){
				account.setDayLightLimitApproved("Yes");
				}else {
					account.setDayLightLimitApproved("No");
				}
				
				if("Commercial Real estate".equals(xrefDetailForm.getEstateType())){
					account.setCommRealEstateType(xrefDetailForm.getCommRealEstateType());
				}else{
					account.setCommRealEstateType("");
				}
				
				account.setPrioritySector(xrefDetailForm.getPrioritySector());
				account.setSecurity1(xrefDetailForm.getSecurity1());
				account.setSecurity2(xrefDetailForm.getSecurity2());
				account.setSecurity3(xrefDetailForm.getSecurity3());
				account.setSecurity4(xrefDetailForm.getSecurity4());
				account.setSecurity5(xrefDetailForm.getSecurity5());
				account.setSecurity6(xrefDetailForm.getSecurity6());
				account.setFacilitySystem(xrefDetailForm.getFacilitySystem());
				account.setFacilitySystemID(xrefDetailForm.getFacilitySystemID());
				account.setLineNo(xrefDetailForm.getLineNo());
				
				account.setHiddenSerialNo(xrefDetailForm.getHiddenSerialNo());
				account.setEdited(xrefDetailForm.getEdited());
				
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				if (!AbstractCommonMapper.isEmptyOrNull(xrefDetailForm.getCreatedOn())) {
					account.setCreatedOn(df.parse(xrefDetailForm.getCreatedOn()));
				} else{
					account.setCreatedOn(null);
				}
				if (!AbstractCommonMapper.isEmptyOrNull(xrefDetailForm.getUpdatedOn())) {
					account.setUpdatedOn(df.parse(xrefDetailForm.getUpdatedOn()));
				} else{
					account.setUpdatedOn(null);
				}
				account.setCreatedBy(xrefDetailForm.getCreatedBy());
				account.setUpdatedBy(xrefDetailForm.getUpdatedBy());
				
				//Start:Code added for Upload Status by Abhijeet J
				if(xrefDetailForm.getUploadStatus()==null || xrefDetailForm.getUploadStatus().equals("")){
					xrefDetailForm.setUploadStatus("N");
				}
				account.setUploadStatus(xrefDetailForm.getUploadStatus());
				//End  :Code added for Upload Status by Abhijeet J
				
//				String branchAllowedValues = "";
//				if(null != xrefDetailForm.getAppendAliasBranchCodeList()){
//				String[] selectedAliasBranchCodeIdArr = xrefDetailForm.getAppendAliasBranchCodeList().split("-");
//				
//				
//				boolean first = true;
//				for(String string : selectedAliasBranchCodeIdArr) {
//				    if(first) {
//				    	branchAllowedValues+=string;
//				        first=false;
//				    } else {
//				    	branchAllowedValues+=","+string;
//				    }
//				}
//				}
//				account.setBranchAllowed(branchAllowedValues);
			//	account.setBranchAllowedFlag(xrefDetailForm.getBranchAllowedFlag());
				/*for (int i=0; i<selectedAliasBranchCodeIdArr.length; i++)
			     {	 
					branchAllowedValues = selectedAliasBranchCodeIdArr[i];
			     }*/
				/*Set oBRbiCodeCatSet = new HashSet();
				for (int i=0; i<selectedAliasBranchCodeIdArr.length; i++)
			     {	 
					oBRbiCodeCatSet.add(selectedAliasBranchCodeIdArr[i]);
			     }
				
				account.setBranchAllowed(oBRbiCodeCatSet);*/
				
				
				account.setLiabBranch(xrefDetailForm.getLiabBranch());
				
				if(xrefDetailForm.getCurrencyRestriction() == null || xrefDetailForm.getCurrencyRestriction().equals("")
						|| xrefDetailForm.getCurrencyRestriction().equals("N")){
					account.setCurrencyRestriction("N");
				}else{
					account.setCurrencyRestriction("Y");
				}
				//account.setCurrencyRestriction(xrefDetailForm.getCurrencyRestriction());
				
				account.setMainLineCode(xrefDetailForm.getMainLineCode());
				
				account.setCurrency(xrefDetailForm.getCurrency());
				if ("close_ubs".equals(xrefDetailForm.getEvent())||"close_ubs_rejected".equals(xrefDetailForm.getEvent())){
				account.setAvailable("No");
				}else if ("reopen_ubs".equals(xrefDetailForm.getEvent())||"reopen_ubs_rejected".equals(xrefDetailForm.getEvent())){
				account.setAvailable("Yes");
				}else{
				account.setAvailable(xrefDetailForm.getAvailable());
				}
				
				account.setFreeze(xrefDetailForm.getFreeze());
				account.setAdhocLine(xrefDetailForm.getAdhocLine());
				
				account.setRevolvingLine(xrefDetailForm.getRevolvingLine());
				
				if ("close_ubs".equals(xrefDetailForm.getEvent())||"close_ubs_rejected".equals(xrefDetailForm.getEvent())){
					account.setCloseFlag("Y");
					
				}else if ("reopen_ubs".equals(xrefDetailForm.getEvent())||"reopen_ubs_rejected".equals(xrefDetailForm.getEvent())){
					account.setCloseFlag("N");
				}else if ("close_ts".equals(xrefDetailForm.getEvent())||"close_ts_rejected".equals(xrefDetailForm.getEvent())){
					account.setCloseFlag("Y");
					
				}else if ("reopen_ts".equals(xrefDetailForm.getEvent())||"reopen_ts_rejected".equals(xrefDetailForm.getEvent())){
					account.setCloseFlag("N");
				}else{
				if(xrefDetailForm.getCloseFlag() == null || xrefDetailForm.getCloseFlag().equals("")
						|| xrefDetailForm.getCloseFlag().equals("N")){
					account.setCloseFlag("N");
				}else{
					account.setCloseFlag("Y");
				}
				}
			//	account.setCloseFlag(xrefDetailForm.getCloseFlag());
				

				if (!AbstractCommonMapper.isEmptyOrNull(xrefDetailForm.getLastavailableDate())) {
					account.setLastavailableDate(DateUtil.convertDate(locale, xrefDetailForm.getLastavailableDate()));
				}else {
					account.setLastavailableDate(null);
				}
				
				if (!AbstractCommonMapper.isEmptyOrNull(xrefDetailForm.getUploadDate())) {
					account.setUploadDate(DateUtil.convertDate(locale, xrefDetailForm.getUploadDate()));
				}else {
					account.setUploadDate(null);
				}
				
				account.setSegment(xrefDetailForm.getSegment());
				
				account.setRuleId(xrefDetailForm.getRuleId());
				
				account.setUncondiCancl(xrefDetailForm.getUncondiCancl());
				
//				account.setInterestRate(xrefDetailForm.getInterestRate());
				
				account.setLimitTenorDays(xrefDetailForm.getLimitTenorDays());
				
				account.setInternalRemarks(xrefDetailForm.getInternalRemarks());
				
//				account.setRemark(xrefDetailForm.getRemark());
				
				if (!AbstractCommonMapper.isEmptyOrNull(xrefDetailForm.getLimitStartDate())) {
					account.setLimitStartDate(DateUtil.convertDate(locale, xrefDetailForm.getLimitStartDate()));
				}else {
					account.setLimitStartDate(null);
				}
				
				
				if (!AbstractCommonMapper.isEmptyOrNull(xrefDetailForm.getIdlEffectiveFromDate())) {
					account.setIdlEffectiveFromDate(DateUtil.convertDate(locale, xrefDetailForm.getIdlEffectiveFromDate()));
				}else {
					account.setIdlEffectiveFromDate(null);
				}
				
				if (!AbstractCommonMapper.isEmptyOrNull(xrefDetailForm.getIdlExpiryDate())) {
					account.setIdlExpiryDate(DateUtil.convertDate(locale, xrefDetailForm.getIdlExpiryDate()));
				}else {
					account.setIdlExpiryDate(null);
				}
				
				account.setIdlAmount(UIUtil.removeComma(xrefDetailForm.getIdlAmount()));
				
				account.setSendToCore(xrefDetailForm.getSendToCore());
				account.setCoreStpRejectedReason(xrefDetailForm.getCoreStpRejectedReason());
				Calendar c =Calendar.getInstance();
//				if(null==xrefDetailForm.getSourceRefNo()){
//				account.setSourceRefNo(ICMSConstant.FCUBS_CAD+"18022100001");
//				}else{
					account.setSourceRefNo(xrefDetailForm.getSourceRefNo());	
//				}
					
					if(null==xrefDetailForm.getAction() && null!=xrefDetailForm.getLineAction()){
						xrefDetailForm.setAction(xrefDetailForm.getLineAction());
					}
				if("submit_ubs".equals(xrefDetailForm.getEvent()) 
						||"submit_ubs_rejected".equals(xrefDetailForm.getEvent())){
					account.setStatus("PENDING_UPDATE");
					
					if(ICMSConstant.FCUBSLIMIT_ACTION_NEW.equals(xrefDetailForm.getAction())){
						account.setAction(ICMSConstant.FCUBSLIMIT_ACTION_NEW);	
						if("N".equals(xrefDetailForm.getSendToFile())){
							account.setSerialNo(xrefDetailForm.getSerialNo());	
							account.setHiddenSerialNo(xrefDetailForm.getSerialNo());	
						}else{
//						if(null!=xrefDetailForm.getHiddenSerialNo() && xrefDetailForm.getHiddenSerialNo().equals(xrefDetailForm.getIndexID())){
//							account.setSerialNo(xrefDetailForm.getHiddenSerialNo());
//						}
							if(null==xrefDetailForm.getSerialNo() || "".equals(xrefDetailForm.getSerialNo())){
								String serialNo=""+c.get(Calendar.DAY_OF_MONTH)+c.get(Calendar.MONTH)+c.get(Calendar.HOUR)+c.get(Calendar.MILLISECOND);
								account.setSerialNo(serialNo);
								account.setHiddenSerialNo(serialNo);
								}
						}
					}else{
					account.setAction(ICMSConstant.FCUBSLIMIT_ACTION_MODIFY);
					}
				}else if("submit_ts".equals(xrefDetailForm.getEvent()) 
						||"submit_ts_rejected".equals(xrefDetailForm.getEvent())) {
//							account.setStatus("PENDING_UPDATE");
					
					if(ICMSConstant.FCUBSLIMIT_ACTION_NEW.equals(xrefDetailForm.getAction())){
						account.setAction(ICMSConstant.FCUBSLIMIT_ACTION_NEW);	
						if("N".equals(xrefDetailForm.getSendToFile())){
							account.setSerialNo(xrefDetailForm.getSerialNo());	
							account.setHiddenSerialNo(xrefDetailForm.getSerialNo());	
						}else{
//						if(null!=xrefDetailForm.getHiddenSerialNo() && xrefDetailForm.getHiddenSerialNo().equals(xrefDetailForm.getIndexID())){
//							account.setSerialNo(xrefDetailForm.getHiddenSerialNo());
//						}
							if(null==xrefDetailForm.getSerialNo() || "".equals(xrefDetailForm.getSerialNo())){
								String serialNo=""+c.get(Calendar.DAY_OF_MONTH)+c.get(Calendar.MONTH)+c.get(Calendar.HOUR)+c.get(Calendar.MILLISECOND);
								account.setSerialNo(serialNo);
								account.setHiddenSerialNo(serialNo);
								}
						}
					}else{
					account.setAction(ICMSConstant.FCUBSLIMIT_ACTION_MODIFY);
					}
				}else if ("create_ubs".equals(xrefDetailForm.getEvent())
						|| "create_ts".equals(xrefDetailForm.getEvent())){
					if(null==xrefDetailForm.getSerialNo() || "".equals(xrefDetailForm.getSerialNo())){
					String serialNo=""+c.get(Calendar.DAY_OF_MONTH)+c.get(Calendar.MONTH)+c.get(Calendar.HOUR)+c.get(Calendar.MILLISECOND);
					account.setSerialNo(serialNo);
					account.setHiddenSerialNo(serialNo);
					}
					account.setStatus("PENDING_UPDATE");
					account.setAction(ICMSConstant.FCUBSLIMIT_ACTION_NEW);
				}else if ("reopen_ubs".equals(xrefDetailForm.getEvent())||"reopen_ubs_rejected".equals(xrefDetailForm.getEvent())
						|| "reopen_ts".equals(xrefDetailForm.getEvent())||"reopen_ts_rejected".equals(xrefDetailForm.getEvent())){
					account.setStatus("PENDING_UPDATE");
					account.setAction(ICMSConstant.FCUBSLIMIT_ACTION_REOPEN);
				}else if ("close_ubs".equals(xrefDetailForm.getEvent())||"close_ubs_rejected".equals(xrefDetailForm.getEvent()) 
						|| "close_ts".equals(xrefDetailForm.getEvent())||"close_ts_rejected".equals(xrefDetailForm.getEvent())){
					account.setStatus("PENDING_UPDATE");
					account.setAction(ICMSConstant.FCUBSLIMIT_ACTION_CLOSE);
				}
						
                if("PENDING".equals(account.getStatus()) || "PENDING_REJECTED".equals(account.getStatus()) || "PENDING_SUCCESS".equals(account.getStatus())) {
				if(null!=xrefDetailForm.getStatus()) {
				account.setStatus(xrefDetailForm.getStatus());
				}else {
					account.setStatus("PENDING_UPDATE");
				}

				}else if("REJECTED".equals(account.getStatus()) || "SUCCESS".equals(account.getStatus()) || "".equals(account.getStatus()) || "-".equals(account.getStatus()) 
						|| null == account.getStatus()) {
					account.setStatus("PENDING_UPDATE");
				}
				
				account.setBranchAllowed(xrefDetailForm.getBranchAllowed());
					
				account.setProductAllowed(xrefDetailForm.getProductAllowed());
				account.setCurrencyAllowed(xrefDetailForm.getCurrencyAllowed());
				account.setLimitRestriction(xrefDetailForm.getLimitRestriction());
				if(xrefDetailForm.getSendToFile() == null || xrefDetailForm.getSendToFile().equals("")
						|| xrefDetailForm.getSendToFile().equals("Y")){
					account.setSendToFile("Y");
				}else{
					account.setSendToFile("N");
				}
				
				account.setTenure(xrefDetailForm.getTenure());
				account.setSellDownPeriod(xrefDetailForm.getSellDownPeriod());
				account.setLiabilityId(xrefDetailForm.getLiabilityId());
				account.setLimitRemarks(xrefDetailForm.getLimitRemarks());
				
				
				//Start Santosh UBS LIMIT UPLOAD
				//for udf delete
				if(null!=account.getUdfAllowed() && null!=xrefDetailForm.getUdfAllowed() && !"".equals(account.getUdfAllowed()) && !"".equals(xrefDetailForm.getUdfAllowed())) {
					List<String> oldUdf =Arrays.asList(account.getUdfAllowed().split(","));
					List<String> newUdf=Arrays.asList(xrefDetailForm.getUdfAllowed().split(","));
					String udfDelete="";
					String temp="";
					for(int i=0;i<oldUdf.size();i++) {
						for(int j=0;j<newUdf.size();j++) {
							if(!oldUdf.get(i).equals(newUdf.get(j))) {
								temp=oldUdf.get(i);
							} else {
								temp="";
								break;
							}
						}
						if(temp!="")
							udfDelete=udfDelete+temp+",";
					}
					if(udfDelete.equals(""))
						account.setUdfDelete(null);
					else
						account.setUdfDelete(udfDelete.substring(0, udfDelete.length()-1));
				}
				
				account.setUdfAllowed(xrefDetailForm.getUdfAllowed());
				if(null!=account.getUdfDelete() || !"".equals(account.getUdfAllowed())) {
					this.addUdfToOb(xrefDetailForm, account);
				
				}

				//End Santosh UBS LIMIT UPLOAD	
				
				if(null!=account.getUdfAllowed_2() && null!=xrefDetailForm.getUdfAllowed_2() && !"".equals(account.getUdfAllowed_2()) && !"".equals(xrefDetailForm.getUdfAllowed_2())) {
					List<String> oldUdf =Arrays.asList(account.getUdfAllowed_2().split(","));
					List<String> newUdf=Arrays.asList(xrefDetailForm.getUdfAllowed_2().split(","));
					String udfDelete="";
					String temp="";
					for(int i=0;i<oldUdf.size();i++) {
						for(int j=0;j<newUdf.size();j++) {
							if(!oldUdf.get(i).equals(newUdf.get(j))) {
								temp=oldUdf.get(i);
							} else {
								temp="";
								break;
							}
						}
						if(temp!="")
							udfDelete=udfDelete+temp+",";
					}
					if(udfDelete.equals(""))
						account.setUdfDelete_2(null);
					else
						account.setUdfDelete_2(udfDelete.substring(0, udfDelete.length()-1));
				}
				
				account.setUdfAllowed_2(xrefDetailForm.getUdfAllowed_2());
				if(null!=account.getUdfDelete() || !"".equals(account.getUdfAllowed_2())) {
				
					this.addUdfToOb_2(xrefDetailForm, account);
				}
				//End  UBS LIMIT UPLOAD	
				List newCoBorrowerList = (ArrayList) (inputs.get("restCoBorrowerList"));
				//	System.out.println("newCoBorrowerList====================="+newCoBorrowerList);
					xrefDetailForm.setRestCoBorrowerList(newCoBorrowerList);
					addCoBorrowerToOb(xrefDetailForm, account);
							

				//End Santosh UBS LIMIT UPLOAD	
				
				if(null!=account.getUdfAllowed_2() && null!=xrefDetailForm.getUdfAllowed_2() && !"".equals(account.getUdfAllowed_2()) && !"".equals(xrefDetailForm.getUdfAllowed_2())) {
					List<String> oldUdf =Arrays.asList(account.getUdfAllowed_2().split(","));
					List<String> newUdf=Arrays.asList(xrefDetailForm.getUdfAllowed_2().split(","));
					String udfDelete="";
					String temp="";
					for(int i=0;i<oldUdf.size();i++) {
						for(int j=0;j<newUdf.size();j++) {
							if(!oldUdf.get(i).equals(newUdf.get(j))) {
								temp=oldUdf.get(i);
							} else {
								temp="";
								break;
							}
						}
						if(temp!="")
							udfDelete=udfDelete+temp+",";
					}
					if(udfDelete.equals(""))
						account.setUdfDelete_2(null);
					else
						account.setUdfDelete_2(udfDelete.substring(0, udfDelete.length()-1));
				}
				
				account.setUdfAllowed_2(xrefDetailForm.getUdfAllowed_2());
				if(null!=account.getUdfDelete_2() || !"".equals(account.getUdfAllowed_2())) {
				
					this.addUdfToOb_2(xrefDetailForm, account);
				}
				//End  UBS LIMIT UPLOAD	
				
				account.setUtilizedAmount(xrefDetailForm.getUtilizedAmount());

				account.setBankingArrangement(xrefDetailForm.getBankingArrangement());

			}
			
			return account;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException();
		}
	}

	private ICustomerSysXRef getCustomerSysXRef(HashMap inputs) {
		ICustomerSysXRef account = (ICustomerSysXRef) (inputs.get("curAccount"));
		return account;
	}
	
	//Start Santosh UBS LIMIT UPLOAD
		private void  addUdfToOb(XRefDetailForm form, OBCustomerSysXRef ob) {
			ILimitXRefUdf udf = new OBLimitXRefUdf();
			
			udf.setId(form.getUdfId());
	        udf.setUdf1_Label(form.getUdf1_Label());
	        udf.setUdf2_Label(form.getUdf2_Label());
	        udf.setUdf3_Label(form.getUdf3_Label());
	        udf.setUdf4_Label(form.getUdf4_Label());
	        udf.setUdf5_Label(form.getUdf5_Label());
	        udf.setUdf6_Label(form.getUdf6_Label());
	        udf.setUdf7_Label(form.getUdf7_Label());
	        udf.setUdf8_Label(form.getUdf8_Label());
	        udf.setUdf9_Label(form.getUdf9_Label());
	        udf.setUdf10_Label(form.getUdf10_Label());
	        udf.setUdf11_Label(form.getUdf11_Label());
	        udf.setUdf12_Label(form.getUdf12_Label());
	        udf.setUdf13_Label(form.getUdf13_Label());
	        udf.setUdf14_Label(form.getUdf14_Label());
	        udf.setUdf15_Label(form.getUdf15_Label());
	        udf.setUdf16_Label(form.getUdf16_Label());
	        udf.setUdf17_Label(form.getUdf17_Label());
	        udf.setUdf18_Label(form.getUdf18_Label());
	        udf.setUdf19_Label(form.getUdf19_Label());
	        udf.setUdf20_Label(form.getUdf20_Label());
	        udf.setUdf21_Label(form.getUdf21_Label());
	        udf.setUdf22_Label(form.getUdf22_Label());
			udf.setUdf23_Label(form.getUdf23_Label());
	        udf.setUdf24_Label(form.getUdf24_Label());
	        udf.setUdf25_Label(form.getUdf25_Label());
	        udf.setUdf26_Label(form.getUdf26_Label());
	        udf.setUdf27_Label(form.getUdf27_Label());
	        udf.setUdf28_Label(form.getUdf28_Label());
	        udf.setUdf29_Label(form.getUdf29_Label());
	        udf.setUdf30_Label(form.getUdf30_Label());
	        udf.setUdf31_Label(form.getUdf31_Label());
	        udf.setUdf32_Label(form.getUdf32_Label());
	        udf.setUdf33_Label(form.getUdf33_Label());
	        udf.setUdf34_Label(form.getUdf34_Label());
	        udf.setUdf35_Label(form.getUdf35_Label());
	        udf.setUdf36_Label(form.getUdf36_Label());
	        udf.setUdf37_Label(form.getUdf37_Label());
	        udf.setUdf38_Label(form.getUdf38_Label());
	        udf.setUdf39_Label(form.getUdf39_Label());
	        udf.setUdf40_Label(form.getUdf40_Label());
	        udf.setUdf41_Label(form.getUdf41_Label());
	        udf.setUdf42_Label(form.getUdf42_Label());
	        udf.setUdf43_Label(form.getUdf43_Label());
	        udf.setUdf44_Label(form.getUdf44_Label());
	        udf.setUdf45_Label(form.getUdf45_Label());
	        udf.setUdf46_Label(form.getUdf46_Label());
	        udf.setUdf47_Label(form.getUdf47_Label());
	        udf.setUdf48_Label(form.getUdf48_Label());
	        udf.setUdf49_Label(form.getUdf49_Label());
	        udf.setUdf50_Label(form.getUdf50_Label());
	    	udf.setUdf51_Label(form.getUdf51_Label());
	        udf.setUdf52_Label(form.getUdf52_Label());
	        udf.setUdf53_Label(form.getUdf53_Label());
	        udf.setUdf54_Label(form.getUdf54_Label());
	        udf.setUdf55_Label(form.getUdf55_Label());
	        udf.setUdf56_Label(form.getUdf56_Label());
	        udf.setUdf57_Label(form.getUdf57_Label());
	        udf.setUdf58_Label(form.getUdf58_Label());
	        udf.setUdf59_Label(form.getUdf59_Label());
	        udf.setUdf60_Label(form.getUdf60_Label());
	        udf.setUdf61_Label(form.getUdf61_Label());
	        udf.setUdf62_Label(form.getUdf62_Label());
	        udf.setUdf63_Label(form.getUdf63_Label());
	        udf.setUdf64_Label(form.getUdf64_Label());
	        udf.setUdf65_Label(form.getUdf65_Label());
	        udf.setUdf66_Label(form.getUdf66_Label());
	        udf.setUdf67_Label(form.getUdf67_Label());
	        udf.setUdf68_Label(form.getUdf68_Label());
	        udf.setUdf69_Label(form.getUdf69_Label());
	        udf.setUdf70_Label(form.getUdf70_Label());
	        udf.setUdf71_Label(form.getUdf71_Label());
	        udf.setUdf72_Label(form.getUdf72_Label());
	        udf.setUdf73_Label(form.getUdf73_Label());
	        udf.setUdf74_Label(form.getUdf74_Label());
	        udf.setUdf75_Label(form.getUdf75_Label());
	        udf.setUdf76_Label(form.getUdf76_Label());
	        udf.setUdf77_Label(form.getUdf77_Label());
	        udf.setUdf78_Label(form.getUdf78_Label());
	        udf.setUdf79_Label(form.getUdf79_Label());
	        udf.setUdf80_Label(form.getUdf80_Label());
	        udf.setUdf81_Label(form.getUdf81_Label());
	        udf.setUdf82_Label(form.getUdf82_Label());
	        udf.setUdf83_Label(form.getUdf83_Label());
	        udf.setUdf84_Label(form.getUdf84_Label());
	        udf.setUdf85_Label(form.getUdf85_Label());
	        udf.setUdf86_Label(form.getUdf86_Label());
	        udf.setUdf87_Label(form.getUdf87_Label());
	        udf.setUdf88_Label(form.getUdf88_Label());
	        udf.setUdf89_Label(form.getUdf89_Label());
	        udf.setUdf90_Label(form.getUdf90_Label());
	        udf.setUdf91_Label(form.getUdf91_Label());
	        udf.setUdf92_Label(form.getUdf92_Label());
	        udf.setUdf93_Label(form.getUdf93_Label());
	        udf.setUdf94_Label(form.getUdf94_Label());
	        udf.setUdf95_Label(form.getUdf95_Label());
	        udf.setUdf96_Label(form.getUdf96_Label());
	        udf.setUdf97_Label(form.getUdf97_Label());
	        udf.setUdf98_Label(form.getUdf98_Label());
	        udf.setUdf99_Label(form.getUdf99_Label());
	        udf.setUdf100_Label(form.getUdf100_Label());
	         
	        udf.setUdf101_Label(form.getUdf101_Label());
	        udf.setUdf102_Label(form.getUdf102_Label());
	        udf.setUdf103_Label(form.getUdf103_Label());
	        udf.setUdf104_Label(form.getUdf104_Label());
	        udf.setUdf105_Label(form.getUdf105_Label());
	        udf.setUdf106_Label(form.getUdf106_Label());
	        udf.setUdf107_Label(form.getUdf107_Label());
	        udf.setUdf108_Label(form.getUdf108_Label());
	        udf.setUdf109_Label(form.getUdf109_Label());
	        udf.setUdf110_Label(form.getUdf110_Label());
	        udf.setUdf111_Label(form.getUdf111_Label());
	        udf.setUdf112_Label(form.getUdf112_Label());
	        udf.setUdf113_Label(form.getUdf113_Label());
	        udf.setUdf114_Label(form.getUdf114_Label());
	        udf.setUdf115_Label(form.getUdf115_Label());
	       
	        
			udf.setUdf1_Value(form.getUdf1());
	        udf.setUdf2_Value(form.getUdf2());
	        udf.setUdf3_Value(form.getUdf3());
	        udf.setUdf4_Value(form.getUdf4());
	        udf.setUdf5_Value(form.getUdf5());
	        udf.setUdf6_Value(form.getUdf6());
	        udf.setUdf7_Value(form.getUdf7());
	        udf.setUdf8_Value(form.getUdf8());
	        udf.setUdf9_Value(form.getUdf9());
	        udf.setUdf10_Value(form.getUdf10());
	        udf.setUdf11_Value(form.getUdf11());
	        udf.setUdf12_Value(form.getUdf12());
	        udf.setUdf13_Value(form.getUdf13());
	        udf.setUdf14_Value(form.getUdf14());
	        udf.setUdf15_Value(form.getUdf15());
	        udf.setUdf16_Value(form.getUdf16());
	        udf.setUdf17_Value(form.getUdf17());
	        udf.setUdf18_Value(form.getUdf18());
	        udf.setUdf19_Value(form.getUdf19());
	        udf.setUdf20_Value(form.getUdf20());
	        udf.setUdf21_Value(form.getUdf21());
	        udf.setUdf22_Value(form.getUdf22());
			udf.setUdf23_Value(form.getUdf23());
	        udf.setUdf24_Value(form.getUdf24());
	        udf.setUdf25_Value(form.getUdf25());
	        udf.setUdf26_Value(form.getUdf26());
	        udf.setUdf27_Value(form.getUdf27());
	        udf.setUdf28_Value(form.getUdf28());
	        udf.setUdf29_Value(form.getUdf29());
	        udf.setUdf30_Value(form.getUdf30());
	        udf.setUdf31_Value(form.getUdf31());
	        udf.setUdf32_Value(form.getUdf32());
	        udf.setUdf33_Value(form.getUdf33());
	        udf.setUdf34_Value(form.getUdf34());
	        udf.setUdf35_Value(form.getUdf35());
	        udf.setUdf36_Value(form.getUdf36());
	        udf.setUdf37_Value(form.getUdf37());
	        udf.setUdf38_Value(form.getUdf38());
	        udf.setUdf39_Value(form.getUdf39());
	        udf.setUdf40_Value(form.getUdf40());
	        udf.setUdf41_Value(form.getUdf41());
	        udf.setUdf42_Value(form.getUdf42());
	        udf.setUdf43_Value(form.getUdf43());
	        udf.setUdf44_Value(form.getUdf44());
	        udf.setUdf45_Value(form.getUdf45());
	        udf.setUdf46_Value(form.getUdf46());
	        udf.setUdf47_Value(form.getUdf47());
	        udf.setUdf48_Value(form.getUdf48());
	        udf.setUdf49_Value(form.getUdf49());
	        udf.setUdf50_Value(form.getUdf50());
	    	udf.setUdf51_Value(form.getUdf51());
	        udf.setUdf52_Value(form.getUdf52());
	        udf.setUdf53_Value(form.getUdf53());
	        udf.setUdf54_Value(form.getUdf54());
	        udf.setUdf55_Value(form.getUdf55());
	        udf.setUdf56_Value(form.getUdf56());
	        udf.setUdf57_Value(form.getUdf57());
	        udf.setUdf58_Value(form.getUdf58());
	        udf.setUdf59_Value(form.getUdf59());
	        udf.setUdf60_Value(form.getUdf60());
	        udf.setUdf61_Value(form.getUdf61());
	        udf.setUdf62_Value(form.getUdf62());
	        udf.setUdf63_Value(form.getUdf63());
	        udf.setUdf64_Value(form.getUdf64());
	        udf.setUdf65_Value(form.getUdf65());
	        udf.setUdf66_Value(form.getUdf66());
	        udf.setUdf67_Value(form.getUdf67());
	        udf.setUdf68_Value(form.getUdf68());
	        udf.setUdf69_Value(form.getUdf69());
	        udf.setUdf70_Value(form.getUdf70());
	        udf.setUdf71_Value(form.getUdf71());
	        udf.setUdf72_Value(form.getUdf72());
	        udf.setUdf73_Value(form.getUdf73());
	        udf.setUdf74_Value(form.getUdf74());
	        udf.setUdf75_Value(form.getUdf75());
	        udf.setUdf76_Value(form.getUdf76());
	        udf.setUdf77_Value(form.getUdf77());
	        udf.setUdf78_Value(form.getUdf78());
	        udf.setUdf79_Value(form.getUdf79());
	        udf.setUdf80_Value(form.getUdf80());
	        udf.setUdf81_Value(form.getUdf81());
	        udf.setUdf82_Value(form.getUdf82());
	        udf.setUdf83_Value(form.getUdf83());
	        udf.setUdf84_Value(form.getUdf84());
	        udf.setUdf85_Value(form.getUdf85());
	        udf.setUdf86_Value(form.getUdf86());
	        udf.setUdf87_Value(form.getUdf87());
	        udf.setUdf88_Value(form.getUdf88());
	        udf.setUdf89_Value(form.getUdf89());
	        udf.setUdf90_Value(form.getUdf90());
	        udf.setUdf91_Value(form.getUdf91());
	        udf.setUdf92_Value(form.getUdf92());
	        udf.setUdf93_Value(form.getUdf93());
	        udf.setUdf94_Value(form.getUdf94());
	        udf.setUdf95_Value(form.getUdf95());
	        udf.setUdf96_Value(form.getUdf96());
	        udf.setUdf97_Value(form.getUdf97());
	        udf.setUdf98_Value(form.getUdf98());
	        udf.setUdf99_Value(form.getUdf99());
	        udf.setUdf100_Value(form.getUdf100());
	        
	         udf.setUdf101_Value(form.getUdf101());
	        udf.setUdf102_Value(form.getUdf102());
	        udf.setUdf103_Value(form.getUdf103());
	        udf.setUdf104_Value(form.getUdf104());
	        udf.setUdf105_Value(form.getUdf105());
	           udf.setUdf106_Value(form.getUdf106());
	        udf.setUdf107_Value(form.getUdf107());
	        udf.setUdf108_Value(form.getUdf108());
	        udf.setUdf109_Value(form.getUdf109());
			udf.setUdf110_Value(form.getUdf110());
	        udf.setUdf111_Value(form.getUdf111());
	        udf.setUdf112_Value(form.getUdf112());
	        udf.setUdf113_Value(form.getUdf113());
	        udf.setUdf114_Value(form.getUdf114());
	        udf.setUdf115_Value(form.getUdf115());
	      
			udf.setUdf1_Flag(form.getUdf1_Flag());
	        udf.setUdf2_Flag(form.getUdf2_Flag());
	        udf.setUdf3_Flag(form.getUdf3_Flag());
	        udf.setUdf4_Flag(form.getUdf4_Flag());
	        udf.setUdf5_Flag(form.getUdf5_Flag());
	        udf.setUdf6_Flag(form.getUdf6_Flag());
	        udf.setUdf7_Flag(form.getUdf7_Flag());
	        udf.setUdf8_Flag(form.getUdf8_Flag());
	        udf.setUdf9_Flag(form.getUdf9_Flag());
	        udf.setUdf10_Flag(form.getUdf10_Flag());
	        udf.setUdf11_Flag(form.getUdf11_Flag());
	        udf.setUdf12_Flag(form.getUdf12_Flag());
	        udf.setUdf13_Flag(form.getUdf13_Flag());
	        udf.setUdf14_Flag(form.getUdf14_Flag());
	        udf.setUdf15_Flag(form.getUdf15_Flag());
	        udf.setUdf16_Flag(form.getUdf16_Flag());
	        udf.setUdf17_Flag(form.getUdf17_Flag());
	        udf.setUdf18_Flag(form.getUdf18_Flag());
	        udf.setUdf19_Flag(form.getUdf19_Flag());
	        udf.setUdf20_Flag(form.getUdf20_Flag());
	        udf.setUdf21_Flag(form.getUdf21_Flag());
	        udf.setUdf22_Flag(form.getUdf22_Flag());
			udf.setUdf23_Flag(form.getUdf23_Flag());
	        udf.setUdf24_Flag(form.getUdf24_Flag());
	        udf.setUdf25_Flag(form.getUdf25_Flag());
	        udf.setUdf26_Flag(form.getUdf26_Flag());
	        udf.setUdf27_Flag(form.getUdf27_Flag());
	        udf.setUdf28_Flag(form.getUdf28_Flag());
	        udf.setUdf29_Flag(form.getUdf29_Flag());
	        udf.setUdf30_Flag(form.getUdf30_Flag());
	        udf.setUdf31_Flag(form.getUdf31_Flag());
	        udf.setUdf32_Flag(form.getUdf32_Flag());
	        udf.setUdf33_Flag(form.getUdf33_Flag());
	        udf.setUdf34_Flag(form.getUdf34_Flag());
	        udf.setUdf35_Flag(form.getUdf35_Flag());
	        udf.setUdf36_Flag(form.getUdf36_Flag());
	        udf.setUdf37_Flag(form.getUdf37_Flag());
	        udf.setUdf38_Flag(form.getUdf38_Flag());
	        udf.setUdf39_Flag(form.getUdf39_Flag());
	        udf.setUdf40_Flag(form.getUdf40_Flag());
	        udf.setUdf41_Flag(form.getUdf41_Flag());
	        udf.setUdf42_Flag(form.getUdf42_Flag());
	        udf.setUdf43_Flag(form.getUdf43_Flag());
	        udf.setUdf44_Flag(form.getUdf44_Flag());
	        udf.setUdf45_Flag(form.getUdf45_Flag());
	        udf.setUdf46_Flag(form.getUdf46_Flag());
	        udf.setUdf47_Flag(form.getUdf47_Flag());
	        udf.setUdf48_Flag(form.getUdf48_Flag());
	        udf.setUdf49_Flag(form.getUdf49_Flag());
	        udf.setUdf50_Flag(form.getUdf50_Flag());
	    	udf.setUdf51_Flag(form.getUdf51_Flag());
	        udf.setUdf52_Flag(form.getUdf52_Flag());
	        udf.setUdf53_Flag(form.getUdf53_Flag());
	        udf.setUdf54_Flag(form.getUdf54_Flag());
	        udf.setUdf55_Flag(form.getUdf55_Flag());
	        udf.setUdf56_Flag(form.getUdf56_Flag());
	        udf.setUdf57_Flag(form.getUdf57_Flag());
	        udf.setUdf58_Flag(form.getUdf58_Flag());
	        udf.setUdf59_Flag(form.getUdf59_Flag());
	        udf.setUdf60_Flag(form.getUdf60_Flag());
	        udf.setUdf61_Flag(form.getUdf61_Flag());
	        udf.setUdf62_Flag(form.getUdf62_Flag());
	        udf.setUdf63_Flag(form.getUdf63_Flag());
	        udf.setUdf64_Flag(form.getUdf64_Flag());
	        udf.setUdf65_Flag(form.getUdf65_Flag());
	        udf.setUdf66_Flag(form.getUdf66_Flag());
	        udf.setUdf67_Flag(form.getUdf67_Flag());
	        udf.setUdf68_Flag(form.getUdf68_Flag());
	        udf.setUdf69_Flag(form.getUdf69_Flag());
	        udf.setUdf70_Flag(form.getUdf70_Flag());
	        udf.setUdf71_Flag(form.getUdf71_Flag());
	        udf.setUdf72_Flag(form.getUdf72_Flag());
	        udf.setUdf73_Flag(form.getUdf73_Flag());
	        udf.setUdf74_Flag(form.getUdf74_Flag());
	        udf.setUdf75_Flag(form.getUdf75_Flag());
	        udf.setUdf76_Flag(form.getUdf76_Flag());
	        udf.setUdf77_Flag(form.getUdf77_Flag());
	        udf.setUdf78_Flag(form.getUdf78_Flag());
	        udf.setUdf79_Flag(form.getUdf79_Flag());
	        udf.setUdf80_Flag(form.getUdf80_Flag());
	        udf.setUdf81_Flag(form.getUdf81_Flag());
	        udf.setUdf82_Flag(form.getUdf82_Flag());
	        udf.setUdf83_Flag(form.getUdf83_Flag());
	        udf.setUdf84_Flag(form.getUdf84_Flag());
	        udf.setUdf85_Flag(form.getUdf85_Flag());
	        udf.setUdf86_Flag(form.getUdf86_Flag());
	        udf.setUdf87_Flag(form.getUdf87_Flag());
	        udf.setUdf88_Flag(form.getUdf88_Flag());
	        udf.setUdf89_Flag(form.getUdf89_Flag());
	        udf.setUdf90_Flag(form.getUdf90_Flag());
	        udf.setUdf91_Flag(form.getUdf91_Flag());
	        udf.setUdf92_Flag(form.getUdf92_Flag());
	        udf.setUdf93_Flag(form.getUdf93_Flag());
	        udf.setUdf94_Flag(form.getUdf94_Flag());
	        udf.setUdf95_Flag(form.getUdf95_Flag());
	        udf.setUdf96_Flag(form.getUdf96_Flag());
	        udf.setUdf97_Flag(form.getUdf97_Flag());
	        udf.setUdf98_Flag(form.getUdf98_Flag());
	        udf.setUdf99_Flag(form.getUdf99_Flag());
	        udf.setUdf100_Flag(form.getUdf100_Flag());
	        
	        udf.setUdf101_Flag(form.getUdf101_Flag());
	        udf.setUdf102_Flag(form.getUdf102_Flag());
	        udf.setUdf103_Flag(form.getUdf103_Flag());
	        udf.setUdf104_Flag(form.getUdf104_Flag());
	        udf.setUdf105_Flag(form.getUdf105_Flag());
	        udf.setUdf106_Flag(form.getUdf106_Flag());
	        udf.setUdf107_Flag(form.getUdf107_Flag());
	        udf.setUdf108_Flag(form.getUdf108_Flag());
	        udf.setUdf109_Flag(form.getUdf109_Flag());
	        udf.setUdf110_Flag(form.getUdf110_Flag());
	        udf.setUdf111_Flag(form.getUdf111_Flag());
	        udf.setUdf112_Flag(form.getUdf112_Flag());
	        udf.setUdf113_Flag(form.getUdf113_Flag());
	        udf.setUdf114_Flag(form.getUdf114_Flag());
	        udf.setUdf115_Flag(form.getUdf115_Flag());
	      
	        
	        ILimitXRefUdf udfList[] = new ILimitXRefUdf[1];
	        udfList[0] = udf;
	        ob.setXRefUdfData(udfList);
		}
		
		private void  addUdfToForm(XRefDetailForm form, OBCustomerSysXRef ob) {
			
			ILimitXRefUdf[] udfDataList = ob.getXRefUdfData();
			ILimitXRefUdf udf;
			if (udfDataList != null && udfDataList.length > 0) {
				udf = udfDataList[0];
		        form.setUdfId(udf.getId());
		        form.setUdf1_Label(udf.getUdf1_Label());
		        form.setUdf2_Label(udf.getUdf2_Label());
		        form.setUdf3_Label(udf.getUdf3_Label());
		        form.setUdf4_Label(udf.getUdf4_Label());
		        form.setUdf5_Label(udf.getUdf5_Label());
		        form.setUdf6_Label(udf.getUdf6_Label());
		        form.setUdf7_Label(udf.getUdf7_Label());
		        form.setUdf8_Label(udf.getUdf8_Label());
		        form.setUdf9_Label(udf.getUdf9_Label());
		        form.setUdf10_Label(udf.getUdf10_Label());
		        form.setUdf11_Label(udf.getUdf11_Label());
		        form.setUdf12_Label(udf.getUdf12_Label());
		        form.setUdf13_Label(udf.getUdf13_Label());
		        form.setUdf14_Label(udf.getUdf14_Label());
		        form.setUdf15_Label(udf.getUdf15_Label());
		        form.setUdf16_Label(udf.getUdf16_Label());
		        form.setUdf17_Label(udf.getUdf17_Label());
		        form.setUdf18_Label(udf.getUdf18_Label());
		        form.setUdf19_Label(udf.getUdf19_Label());
		        form.setUdf20_Label(udf.getUdf20_Label());
		        form.setUdf21_Label(udf.getUdf21_Label());
		        form.setUdf22_Label(udf.getUdf22_Label());
				form.setUdf23_Label(udf.getUdf23_Label());
		        form.setUdf24_Label(udf.getUdf24_Label());
		        form.setUdf25_Label(udf.getUdf25_Label());
		        form.setUdf26_Label(udf.getUdf26_Label());
		        form.setUdf27_Label(udf.getUdf27_Label());
		        form.setUdf28_Label(udf.getUdf28_Label());
		        form.setUdf29_Label(udf.getUdf29_Label());
		        form.setUdf30_Label(udf.getUdf30_Label());
		        form.setUdf31_Label(udf.getUdf31_Label());
		        form.setUdf32_Label(udf.getUdf32_Label());
		        form.setUdf33_Label(udf.getUdf33_Label());
		        form.setUdf34_Label(udf.getUdf34_Label());
		        form.setUdf35_Label(udf.getUdf35_Label());
		        form.setUdf36_Label(udf.getUdf36_Label());
		        form.setUdf37_Label(udf.getUdf37_Label());
		        form.setUdf38_Label(udf.getUdf38_Label());
		        form.setUdf39_Label(udf.getUdf39_Label());
		        form.setUdf40_Label(udf.getUdf40_Label());
		        form.setUdf41_Label(udf.getUdf41_Label());
		        form.setUdf42_Label(udf.getUdf42_Label());
		        form.setUdf43_Label(udf.getUdf43_Label());
		        form.setUdf44_Label(udf.getUdf44_Label());
		        form.setUdf45_Label(udf.getUdf45_Label());
		        form.setUdf46_Label(udf.getUdf46_Label());
		        form.setUdf47_Label(udf.getUdf47_Label());
		        form.setUdf48_Label(udf.getUdf48_Label());
		        form.setUdf49_Label(udf.getUdf49_Label());
		        form.setUdf50_Label(udf.getUdf50_Label());
		    	form.setUdf51_Label(udf.getUdf51_Label());
		        form.setUdf52_Label(udf.getUdf52_Label());
		        form.setUdf53_Label(udf.getUdf53_Label());
		        form.setUdf54_Label(udf.getUdf54_Label());
		        form.setUdf55_Label(udf.getUdf55_Label());
		        form.setUdf56_Label(udf.getUdf56_Label());
		        form.setUdf57_Label(udf.getUdf57_Label());
		        form.setUdf58_Label(udf.getUdf58_Label());
		        form.setUdf59_Label(udf.getUdf59_Label());
		        form.setUdf60_Label(udf.getUdf60_Label());
		        form.setUdf61_Label(udf.getUdf61_Label());
		        form.setUdf62_Label(udf.getUdf62_Label());
		        form.setUdf63_Label(udf.getUdf63_Label());
		        form.setUdf64_Label(udf.getUdf64_Label());
		        form.setUdf65_Label(udf.getUdf65_Label());
		        form.setUdf66_Label(udf.getUdf66_Label());
		        form.setUdf67_Label(udf.getUdf67_Label());
		        form.setUdf68_Label(udf.getUdf68_Label());
		        form.setUdf69_Label(udf.getUdf69_Label());
		        form.setUdf70_Label(udf.getUdf70_Label());
		        form.setUdf71_Label(udf.getUdf71_Label());
		        form.setUdf72_Label(udf.getUdf72_Label());
		        form.setUdf73_Label(udf.getUdf73_Label());
		        form.setUdf74_Label(udf.getUdf74_Label());
		        form.setUdf75_Label(udf.getUdf75_Label());
		        form.setUdf76_Label(udf.getUdf76_Label());
		        form.setUdf77_Label(udf.getUdf77_Label());
		        form.setUdf78_Label(udf.getUdf78_Label());
		        form.setUdf79_Label(udf.getUdf79_Label());
		        form.setUdf80_Label(udf.getUdf80_Label());
		        form.setUdf81_Label(udf.getUdf81_Label());
		        form.setUdf82_Label(udf.getUdf82_Label());
		        form.setUdf83_Label(udf.getUdf83_Label());
		        form.setUdf84_Label(udf.getUdf84_Label());
		        form.setUdf85_Label(udf.getUdf85_Label());
		        form.setUdf86_Label(udf.getUdf86_Label());
		        form.setUdf87_Label(udf.getUdf87_Label());
		        form.setUdf88_Label(udf.getUdf88_Label());
		        form.setUdf89_Label(udf.getUdf89_Label());
		        form.setUdf90_Label(udf.getUdf90_Label());
		        form.setUdf91_Label(udf.getUdf91_Label());
		        form.setUdf92_Label(udf.getUdf92_Label());
		        form.setUdf93_Label(udf.getUdf93_Label());
		        form.setUdf94_Label(udf.getUdf94_Label());
		        form.setUdf95_Label(udf.getUdf95_Label());
		        form.setUdf96_Label(udf.getUdf96_Label());
		        form.setUdf97_Label(udf.getUdf97_Label());
		        form.setUdf98_Label(udf.getUdf98_Label());
		        form.setUdf99_Label(udf.getUdf99_Label());
		        form.setUdf100_Label(udf.getUdf100_Label());
		        
		        form.setUdf101_Label(udf.getUdf101_Label());
		        form.setUdf102_Label(udf.getUdf102_Label());
		        form.setUdf103_Label(udf.getUdf103_Label());
		        form.setUdf104_Label(udf.getUdf104_Label());
		        form.setUdf105_Label(udf.getUdf105_Label());
		         form.setUdf106_Label(udf.getUdf106_Label());
		        form.setUdf107_Label(udf.getUdf107_Label());
		        form.setUdf108_Label(udf.getUdf108_Label());
		        form.setUdf109_Label(udf.getUdf109_Label());
		        form.setUdf110_Label(udf.getUdf110_Label());
		        form.setUdf111_Label(udf.getUdf111_Label());
		        form.setUdf112_Label(udf.getUdf112_Label());
		        form.setUdf113_Label(udf.getUdf113_Label());
		        form.setUdf114_Label(udf.getUdf114_Label());
		        form.setUdf115_Label(udf.getUdf115_Label());
		       
				form.setUdf1(udf.getUdf1_Value());
		        form.setUdf2(udf.getUdf2_Value());
		        form.setUdf3(udf.getUdf3_Value());
		        form.setUdf4(udf.getUdf4_Value());
		        form.setUdf5(udf.getUdf5_Value());
		        form.setUdf6(udf.getUdf6_Value());
		        form.setUdf7(udf.getUdf7_Value());
		        form.setUdf8(udf.getUdf8_Value());
		        form.setUdf9(udf.getUdf9_Value());
		        form.setUdf10(udf.getUdf10_Value());
		        form.setUdf11(udf.getUdf11_Value());
		        form.setUdf12(udf.getUdf12_Value());
		        form.setUdf13(udf.getUdf13_Value());
		        form.setUdf14(udf.getUdf14_Value());
		        form.setUdf15(udf.getUdf15_Value());
		        form.setUdf16(udf.getUdf16_Value());
		        form.setUdf17(udf.getUdf17_Value());
		        form.setUdf18(udf.getUdf18_Value());
		        form.setUdf19(udf.getUdf19_Value());
		        form.setUdf20(udf.getUdf20_Value());
		        form.setUdf21(udf.getUdf21_Value());
		        form.setUdf22(udf.getUdf22_Value());
				form.setUdf23(udf.getUdf23_Value());
		        form.setUdf24(udf.getUdf24_Value());
		        form.setUdf25(udf.getUdf25_Value());
		        form.setUdf26(udf.getUdf26_Value());
		        form.setUdf27(udf.getUdf27_Value());
		        form.setUdf28(udf.getUdf28_Value());
		        form.setUdf29(udf.getUdf29_Value());
		        form.setUdf30(udf.getUdf30_Value());
		        form.setUdf31(udf.getUdf31_Value());
		        form.setUdf32(udf.getUdf32_Value());
		        form.setUdf33(udf.getUdf33_Value());
		        form.setUdf34(udf.getUdf34_Value());
		        form.setUdf35(udf.getUdf35_Value());
		        form.setUdf36(udf.getUdf36_Value());
		        form.setUdf37(udf.getUdf37_Value());
		        form.setUdf38(udf.getUdf38_Value());
		        form.setUdf39(udf.getUdf39_Value());
		        form.setUdf40(udf.getUdf40_Value());
		        form.setUdf41(udf.getUdf41_Value());
		        form.setUdf42(udf.getUdf42_Value());
		        form.setUdf43(udf.getUdf43_Value());
		        form.setUdf44(udf.getUdf44_Value());
		        form.setUdf45(udf.getUdf45_Value());
		        form.setUdf46(udf.getUdf46_Value());
		        form.setUdf47(udf.getUdf47_Value());
		        form.setUdf48(udf.getUdf48_Value());
		        form.setUdf49(udf.getUdf49_Value());
		        form.setUdf50(udf.getUdf50_Value());
		    	form.setUdf51(udf.getUdf51_Value());
		        form.setUdf52(udf.getUdf52_Value());
		        form.setUdf53(udf.getUdf53_Value());
		        form.setUdf54(udf.getUdf54_Value());
		        form.setUdf55(udf.getUdf55_Value());
		        form.setUdf56(udf.getUdf56_Value());
		        form.setUdf57(udf.getUdf57_Value());
		        form.setUdf58(udf.getUdf58_Value());
		        form.setUdf59(udf.getUdf59_Value());
		        form.setUdf60(udf.getUdf60_Value());
		        form.setUdf61(udf.getUdf61_Value());
		        form.setUdf62(udf.getUdf62_Value());
		        form.setUdf63(udf.getUdf63_Value());
		        form.setUdf64(udf.getUdf64_Value());
		        form.setUdf65(udf.getUdf65_Value());
		        form.setUdf66(udf.getUdf66_Value());
		        form.setUdf67(udf.getUdf67_Value());
		        form.setUdf68(udf.getUdf68_Value());
		        form.setUdf69(udf.getUdf69_Value());
		        form.setUdf70(udf.getUdf70_Value());
		        form.setUdf71(udf.getUdf71_Value());
		        form.setUdf72(udf.getUdf72_Value());
		        form.setUdf73(udf.getUdf73_Value());
		        form.setUdf74(udf.getUdf74_Value());
		        form.setUdf75(udf.getUdf75_Value());
		        form.setUdf76(udf.getUdf76_Value());
		        form.setUdf77(udf.getUdf77_Value());
		        form.setUdf78(udf.getUdf78_Value());
		        form.setUdf79(udf.getUdf79_Value());
		        form.setUdf80(udf.getUdf80_Value());
		        form.setUdf81(udf.getUdf81_Value());
		        form.setUdf82(udf.getUdf82_Value());
		        form.setUdf83(udf.getUdf83_Value());
		        form.setUdf84(udf.getUdf84_Value());
		        form.setUdf85(udf.getUdf85_Value());
		        form.setUdf86(udf.getUdf86_Value());
		        form.setUdf87(udf.getUdf87_Value());
		        form.setUdf88(udf.getUdf88_Value());
		        form.setUdf89(udf.getUdf89_Value());
		        form.setUdf90(udf.getUdf90_Value());
		        form.setUdf91(udf.getUdf91_Value());
		        form.setUdf92(udf.getUdf92_Value());
		        form.setUdf93(udf.getUdf93_Value());
		        form.setUdf94(udf.getUdf94_Value());
		        form.setUdf95(udf.getUdf95_Value());
		        form.setUdf96(udf.getUdf96_Value());
		        form.setUdf97(udf.getUdf97_Value());
		        form.setUdf98(udf.getUdf98_Value());
		        form.setUdf99(udf.getUdf99_Value());
		        form.setUdf100(udf.getUdf100_Value());
		        
		        form.setUdf101(udf.getUdf101_Value());
		        form.setUdf102(udf.getUdf102_Value());
		        form.setUdf103(udf.getUdf103_Value());
		        form.setUdf104(udf.getUdf104_Value());
		        form.setUdf105(udf.getUdf105_Value());
		            form.setUdf106(udf.getUdf106_Value());
		        form.setUdf107(udf.getUdf107_Value());
		        form.setUdf108(udf.getUdf108_Value());
		        form.setUdf109(udf.getUdf109_Value());
		        form.setUdf110(udf.getUdf110_Value());
		        form.setUdf111(udf.getUdf111_Value());
		        form.setUdf112(udf.getUdf112_Value());
		        form.setUdf113(udf.getUdf113_Value());
		        form.setUdf114(udf.getUdf114_Value());
		        form.setUdf115(udf.getUdf115_Value());
		     		        
				form.setUdf1_Flag(udf.getUdf1_Flag());
		        form.setUdf2_Flag(udf.getUdf2_Flag());
		        form.setUdf3_Flag(udf.getUdf3_Flag());
		        form.setUdf4_Flag(udf.getUdf4_Flag());
		        form.setUdf5_Flag(udf.getUdf5_Flag());
		        form.setUdf6_Flag(udf.getUdf6_Flag());
		        form.setUdf7_Flag(udf.getUdf7_Flag());
		        form.setUdf8_Flag(udf.getUdf8_Flag());
		        form.setUdf9_Flag(udf.getUdf9_Flag());
		        form.setUdf10_Flag(udf.getUdf10_Flag());
		        form.setUdf11_Flag(udf.getUdf11_Flag());
		        form.setUdf12_Flag(udf.getUdf12_Flag());
		        form.setUdf13_Flag(udf.getUdf13_Flag());
		        form.setUdf14_Flag(udf.getUdf14_Flag());
		        form.setUdf15_Flag(udf.getUdf15_Flag());
		        form.setUdf16_Flag(udf.getUdf16_Flag());
		        form.setUdf17_Flag(udf.getUdf17_Flag());
		        form.setUdf18_Flag(udf.getUdf18_Flag());
		        form.setUdf19_Flag(udf.getUdf19_Flag());
		        form.setUdf20_Flag(udf.getUdf20_Flag());
		        form.setUdf21_Flag(udf.getUdf21_Flag());
		        form.setUdf22_Flag(udf.getUdf22_Flag());
				form.setUdf23_Flag(udf.getUdf23_Flag());
		        form.setUdf24_Flag(udf.getUdf24_Flag());
		        form.setUdf25_Flag(udf.getUdf25_Flag());
		        form.setUdf26_Flag(udf.getUdf26_Flag());
		        form.setUdf27_Flag(udf.getUdf27_Flag());
		        form.setUdf28_Flag(udf.getUdf28_Flag());
		        form.setUdf29_Flag(udf.getUdf29_Flag());
		        form.setUdf30_Flag(udf.getUdf30_Flag());
		        form.setUdf31_Flag(udf.getUdf31_Flag());
		        form.setUdf32_Flag(udf.getUdf32_Flag());
		        form.setUdf33_Flag(udf.getUdf33_Flag());
		        form.setUdf34_Flag(udf.getUdf34_Flag());
		        form.setUdf35_Flag(udf.getUdf35_Flag());
		        form.setUdf36_Flag(udf.getUdf36_Flag());
		        form.setUdf37_Flag(udf.getUdf37_Flag());
		        form.setUdf38_Flag(udf.getUdf38_Flag());
		        form.setUdf39_Flag(udf.getUdf39_Flag());
		        form.setUdf40_Flag(udf.getUdf40_Flag());
		        form.setUdf41_Flag(udf.getUdf41_Flag());
		        form.setUdf42_Flag(udf.getUdf42_Flag());
		        form.setUdf43_Flag(udf.getUdf43_Flag());
		        form.setUdf44_Flag(udf.getUdf44_Flag());
		        form.setUdf45_Flag(udf.getUdf45_Flag());
		        form.setUdf46_Flag(udf.getUdf46_Flag());
		        form.setUdf47_Flag(udf.getUdf47_Flag());
		        form.setUdf48_Flag(udf.getUdf48_Flag());
		        form.setUdf49_Flag(udf.getUdf49_Flag());
		        form.setUdf50_Flag(udf.getUdf50_Flag());
		    	form.setUdf51_Flag(udf.getUdf51_Flag());
		        form.setUdf52_Flag(udf.getUdf52_Flag());
		        form.setUdf53_Flag(udf.getUdf53_Flag());
		        form.setUdf54_Flag(udf.getUdf54_Flag());
		        form.setUdf55_Flag(udf.getUdf55_Flag());
		        form.setUdf56_Flag(udf.getUdf56_Flag());
		        form.setUdf57_Flag(udf.getUdf57_Flag());
		        form.setUdf58_Flag(udf.getUdf58_Flag());
		        form.setUdf59_Flag(udf.getUdf59_Flag());
		        form.setUdf60_Flag(udf.getUdf60_Flag());
		        form.setUdf61_Flag(udf.getUdf61_Flag());
		        form.setUdf62_Flag(udf.getUdf62_Flag());
		        form.setUdf63_Flag(udf.getUdf63_Flag());
		        form.setUdf64_Flag(udf.getUdf64_Flag());
		        form.setUdf65_Flag(udf.getUdf65_Flag());
		        form.setUdf66_Flag(udf.getUdf66_Flag());
		        form.setUdf67_Flag(udf.getUdf67_Flag());
		        form.setUdf68_Flag(udf.getUdf68_Flag());
		        form.setUdf69_Flag(udf.getUdf69_Flag());
		        form.setUdf70_Flag(udf.getUdf70_Flag());
		        form.setUdf71_Flag(udf.getUdf71_Flag());
		        form.setUdf72_Flag(udf.getUdf72_Flag());
		        form.setUdf73_Flag(udf.getUdf73_Flag());
		        form.setUdf74_Flag(udf.getUdf74_Flag());
		        form.setUdf75_Flag(udf.getUdf75_Flag());
		        form.setUdf76_Flag(udf.getUdf76_Flag());
		        form.setUdf77_Flag(udf.getUdf77_Flag());
		        form.setUdf78_Flag(udf.getUdf78_Flag());
		        form.setUdf79_Flag(udf.getUdf79_Flag());
		        form.setUdf80_Flag(udf.getUdf80_Flag());
		        form.setUdf81_Flag(udf.getUdf81_Flag());
		        form.setUdf82_Flag(udf.getUdf82_Flag());
		        form.setUdf83_Flag(udf.getUdf83_Flag());
		        form.setUdf84_Flag(udf.getUdf84_Flag());
		        form.setUdf85_Flag(udf.getUdf85_Flag());
		        form.setUdf86_Flag(udf.getUdf86_Flag());
		        form.setUdf87_Flag(udf.getUdf87_Flag());
		        form.setUdf88_Flag(udf.getUdf88_Flag());
		        form.setUdf89_Flag(udf.getUdf89_Flag());
		        form.setUdf90_Flag(udf.getUdf90_Flag());
		        form.setUdf91_Flag(udf.getUdf91_Flag());
		        form.setUdf92_Flag(udf.getUdf92_Flag());
		        form.setUdf93_Flag(udf.getUdf93_Flag());
		        form.setUdf94_Flag(udf.getUdf94_Flag());
		        form.setUdf95_Flag(udf.getUdf95_Flag());
		        form.setUdf96_Flag(udf.getUdf96_Flag());
		        form.setUdf97_Flag(udf.getUdf97_Flag());
		        form.setUdf98_Flag(udf.getUdf98_Flag());
		        form.setUdf99_Flag(udf.getUdf99_Flag());
		        form.setUdf100_Flag(udf.getUdf100_Flag());
		        
		        form.setUdf101_Flag(udf.getUdf101_Flag());
		        form.setUdf102_Flag(udf.getUdf102_Flag());
		        form.setUdf103_Flag(udf.getUdf103_Flag());
		        form.setUdf104_Flag(udf.getUdf104_Flag());
		        form.setUdf105_Flag(udf.getUdf105_Flag());
		        form.setUdf106_Flag(udf.getUdf106_Flag());
		        form.setUdf107_Flag(udf.getUdf107_Flag());
		        form.setUdf108_Flag(udf.getUdf108_Flag());
		        form.setUdf109_Flag(udf.getUdf109_Flag());
		        form.setUdf110_Flag(udf.getUdf110_Flag());
		        form.setUdf111_Flag(udf.getUdf111_Flag());
		        form.setUdf112_Flag(udf.getUdf112_Flag());
		        form.setUdf113_Flag(udf.getUdf113_Flag());
		        form.setUdf114_Flag(udf.getUdf114_Flag());
		        form.setUdf115_Flag(udf.getUdf115_Flag());
		       
			}
		}
		
		//End Santosh UBS LIMIT UPLOAD


		
		private void  addUdfToOb_2(XRefDetailForm form, OBCustomerSysXRef ob) {
			ILimitXRefUdf2 udf = new OBLimitXRefUdf2();
			udf.setId(form.getUdfId());
	  
	       udf.setUdf116_Label(form.getUdf116_Label());
	 	   udf.setUdf117_Label(form.getUdf117_Label());
	 	   udf.setUdf118_Label(form.getUdf118_Label());
	 	   udf.setUdf119_Label(form.getUdf119_Label());
	 	   udf.setUdf120_Label(form.getUdf120_Label());
	 	   udf.setUdf121_Label(form.getUdf121_Label());
	 	   udf.setUdf122_Label(form.getUdf122_Label());
	 	   udf.setUdf123_Label(form.getUdf123_Label());
	 	   udf.setUdf124_Label(form.getUdf124_Label());
	 	   udf.setUdf125_Label(form.getUdf125_Label());
	 	   udf.setUdf126_Label(form.getUdf126_Label());
	 	   udf.setUdf127_Label(form.getUdf127_Label());
	 	   udf.setUdf128_Label(form.getUdf128_Label());
	 	   udf.setUdf129_Label(form.getUdf129_Label());
	 	   udf.setUdf130_Label(form.getUdf130_Label());
	 	   udf.setUdf131_Label(form.getUdf131_Label());
	 	   udf.setUdf132_Label(form.getUdf132_Label());
	 	   udf.setUdf133_Label(form.getUdf133_Label());
	 	   udf.setUdf134_Label(form.getUdf134_Label());
	 	   udf.setUdf135_Label(form.getUdf135_Label());
	 	   udf.setUdf136_Label(form.getUdf136_Label());
	 	   udf.setUdf137_Label(form.getUdf137_Label());
	 	   udf.setUdf138_Label(form.getUdf138_Label());
	 	   udf.setUdf139_Label(form.getUdf139_Label());
	 	   udf.setUdf140_Label(form.getUdf140_Label());
	 	   udf.setUdf141_Label(form.getUdf141_Label());
	 	   udf.setUdf142_Label(form.getUdf142_Label());
	 	   udf.setUdf143_Label(form.getUdf143_Label());
	 	   udf.setUdf144_Label(form.getUdf144_Label());
	 	   udf.setUdf145_Label(form.getUdf145_Label());
	 	   udf.setUdf146_Label(form.getUdf146_Label());
	 	   udf.setUdf147_Label(form.getUdf147_Label());
	 	   udf.setUdf148_Label(form.getUdf148_Label());
	 	   udf.setUdf149_Label(form.getUdf149_Label());
	 	   udf.setUdf150_Label(form.getUdf150_Label());
	 	   udf.setUdf151_Label(form.getUdf151_Label());
	 	   udf.setUdf152_Label(form.getUdf152_Label());
	 	   udf.setUdf153_Label(form.getUdf153_Label());
	 	   udf.setUdf154_Label(form.getUdf154_Label());
	 	   udf.setUdf155_Label(form.getUdf155_Label());
	 	   udf.setUdf156_Label(form.getUdf156_Label());
	 	   udf.setUdf157_Label(form.getUdf157_Label());
	 	   udf.setUdf158_Label(form.getUdf158_Label());
	 	   udf.setUdf159_Label(form.getUdf159_Label());
	 	   udf.setUdf160_Label(form.getUdf160_Label());
	 	   udf.setUdf161_Label(form.getUdf161_Label());
	 	   udf.setUdf162_Label(form.getUdf162_Label());
	 	   udf.setUdf163_Label(form.getUdf163_Label());
	 	   udf.setUdf164_Label(form.getUdf164_Label());
	 	   udf.setUdf165_Label(form.getUdf165_Label());

	 	   udf.setUdf166_Label(form.getUdf166_Label());
	 	   udf.setUdf167_Label(form.getUdf167_Label());
	 	   udf.setUdf168_Label(form.getUdf168_Label());
	 	   udf.setUdf169_Label(form.getUdf169_Label());
	 	   udf.setUdf170_Label(form.getUdf170_Label());
	 	   udf.setUdf171_Label(form.getUdf171_Label());
	 	   udf.setUdf172_Label(form.getUdf172_Label());
	 	   udf.setUdf173_Label(form.getUdf173_Label());
	 	   udf.setUdf174_Label(form.getUdf174_Label());
	 	   udf.setUdf175_Label(form.getUdf175_Label());
	 	   udf.setUdf176_Label(form.getUdf176_Label());
	 	   udf.setUdf177_Label(form.getUdf177_Label());
	 	   udf.setUdf178_Label(form.getUdf178_Label());
	 	   udf.setUdf179_Label(form.getUdf179_Label());
	 	   udf.setUdf180_Label(form.getUdf180_Label());
	 	   udf.setUdf181_Label(form.getUdf181_Label());
	 	   udf.setUdf182_Label(form.getUdf182_Label());
	 	   udf.setUdf183_Label(form.getUdf183_Label());
	 	   udf.setUdf184_Label(form.getUdf184_Label());
	 	   udf.setUdf185_Label(form.getUdf185_Label());
	 	   udf.setUdf186_Label(form.getUdf186_Label());
	 	   udf.setUdf187_Label(form.getUdf187_Label());
	 	   udf.setUdf188_Label(form.getUdf188_Label());
	 	   udf.setUdf189_Label(form.getUdf189_Label());
	 	   udf.setUdf190_Label(form.getUdf190_Label());
	 	   udf.setUdf191_Label(form.getUdf191_Label());
	 	   udf.setUdf192_Label(form.getUdf192_Label());
	 	   udf.setUdf193_Label(form.getUdf193_Label());
	 	   udf.setUdf194_Label(form.getUdf194_Label());
	 	   udf.setUdf195_Label(form.getUdf195_Label());
	 	   udf.setUdf196_Label(form.getUdf196_Label());
	 	   udf.setUdf197_Label(form.getUdf197_Label());
	 	   udf.setUdf198_Label(form.getUdf198_Label());
	 	   udf.setUdf199_Label(form.getUdf199_Label());
	 	   udf.setUdf200_Label(form.getUdf200_Label());
	 	   udf.setUdf201_Label(form.getUdf201_Label());
	 	   udf.setUdf202_Label(form.getUdf202_Label());
	 	   udf.setUdf203_Label(form.getUdf203_Label());
	 	   udf.setUdf204_Label(form.getUdf204_Label());
	 	   udf.setUdf205_Label(form.getUdf205_Label());
	 	   udf.setUdf206_Label(form.getUdf206_Label());
	 	   udf.setUdf207_Label(form.getUdf207_Label()); 
	 	   udf.setUdf208_Label(form.getUdf208_Label());
	 	   udf.setUdf209_Label(form.getUdf209_Label());
	 	   udf.setUdf210_Label(form.getUdf210_Label());
	 	   udf.setUdf211_Label(form.getUdf211_Label());
	 	   udf.setUdf212_Label(form.getUdf212_Label());
	 	   udf.setUdf213_Label(form.getUdf213_Label());
	 	   udf.setUdf214_Label(form.getUdf214_Label());
	 	   udf.setUdf215_Label(form.getUdf215_Label());
	   
	      
	       udf.setUdf116_Value(form.getUdf116());
	 	   udf.setUdf117_Value(form.getUdf117());
	 	   udf.setUdf118_Value(form.getUdf118());
	 	   udf.setUdf119_Value(form.getUdf119());
	 	   udf.setUdf120_Value(form.getUdf120());
	 	   udf.setUdf121_Value(form.getUdf121());
	 	   udf.setUdf122_Value(form.getUdf122());
	 	   udf.setUdf123_Value(form.getUdf123());
	 	   udf.setUdf124_Value(form.getUdf124());
	 	   udf.setUdf125_Value(form.getUdf125());
	 	   udf.setUdf126_Value(form.getUdf126());
	 	   udf.setUdf127_Value(form.getUdf127());
	 	   udf.setUdf128_Value(form.getUdf128());
	 	   udf.setUdf129_Value(form.getUdf129());
	 	   udf.setUdf130_Value(form.getUdf130());
	 	   udf.setUdf131_Value(form.getUdf131());
	 	   udf.setUdf132_Value(form.getUdf132());
	 	   udf.setUdf133_Value(form.getUdf133());
	 	   udf.setUdf134_Value(form.getUdf134());
	 	   udf.setUdf135_Value(form.getUdf135());
	 	   udf.setUdf136_Value(form.getUdf136());
	 	   udf.setUdf137_Value(form.getUdf137());
	 	   udf.setUdf138_Value(form.getUdf138());
	 	   udf.setUdf139_Value(form.getUdf139());
	 	   udf.setUdf140_Value(form.getUdf140());
	 	   udf.setUdf141_Value(form.getUdf141());
	 	   udf.setUdf142_Value(form.getUdf142());
	 	   udf.setUdf143_Value(form.getUdf143());
	 	   udf.setUdf144_Value(form.getUdf144());
	 	   udf.setUdf145_Value(form.getUdf145());
	 	   udf.setUdf146_Value(form.getUdf146());
	 	   udf.setUdf147_Value(form.getUdf147());
	 	   udf.setUdf148_Value(form.getUdf148());
	 	   udf.setUdf149_Value(form.getUdf149());
	 	   udf.setUdf150_Value(form.getUdf150());
	 	   udf.setUdf151_Value(form.getUdf151());
	 	   udf.setUdf152_Value(form.getUdf152());
	 	   udf.setUdf153_Value(form.getUdf153());
	 	   udf.setUdf154_Value(form.getUdf154());
	 	   udf.setUdf155_Value(form.getUdf155());
	 	   udf.setUdf156_Value(form.getUdf156());
	 	   udf.setUdf157_Value(form.getUdf157());
	 	   udf.setUdf158_Value(form.getUdf158());
	 	   udf.setUdf159_Value(form.getUdf159());
	 	   udf.setUdf160_Value(form.getUdf160());
	 	   udf.setUdf161_Value(form.getUdf161());
	 	   udf.setUdf162_Value(form.getUdf162());
	 	   udf.setUdf163_Value(form.getUdf163());
	 	   udf.setUdf164_Value(form.getUdf164());
	 	   udf.setUdf165_Value(form.getUdf165());

	 	   udf.setUdf166_Value(form.getUdf166());
	 	   udf.setUdf167_Value(form.getUdf167());
	 	   udf.setUdf168_Value(form.getUdf168());
	 	   udf.setUdf169_Value(form.getUdf169());
	 	   udf.setUdf170_Value(form.getUdf170());
	 	   udf.setUdf171_Value(form.getUdf171());
	 	   udf.setUdf172_Value(form.getUdf172());
	 	   udf.setUdf173_Value(form.getUdf173());
	 	   udf.setUdf174_Value(form.getUdf174());
	 	   udf.setUdf175_Value(form.getUdf175());
	 	   udf.setUdf176_Value(form.getUdf176());
	 	   udf.setUdf177_Value(form.getUdf177());
	 	   udf.setUdf178_Value(form.getUdf178());
	 	   udf.setUdf179_Value(form.getUdf179());
	 	   udf.setUdf180_Value(form.getUdf180());
	 	   udf.setUdf181_Value(form.getUdf181());
	 	   udf.setUdf182_Value(form.getUdf182());
	 	   udf.setUdf183_Value(form.getUdf183());
	 	   udf.setUdf184_Value(form.getUdf184());
	 	   udf.setUdf185_Value(form.getUdf185());
	 	   udf.setUdf186_Value(form.getUdf186());
	 	   udf.setUdf187_Value(form.getUdf187());
	 	   udf.setUdf188_Value(form.getUdf188());
	 	   udf.setUdf189_Value(form.getUdf189());
	 	   udf.setUdf190_Value(form.getUdf190());
	 	   udf.setUdf191_Value(form.getUdf191());
	 	   udf.setUdf192_Value(form.getUdf192());
	 	   udf.setUdf193_Value(form.getUdf193());
	 	   udf.setUdf194_Value(form.getUdf194());
	 	   udf.setUdf195_Value(form.getUdf195());
	 	   udf.setUdf196_Value(form.getUdf196());
	 	   udf.setUdf197_Value(form.getUdf197());
	 	   udf.setUdf198_Value(form.getUdf198());
	 	   udf.setUdf199_Value(form.getUdf199());
	 	   udf.setUdf200_Value(form.getUdf200());
	 	   udf.setUdf201_Value(form.getUdf201());
		   udf.setUdf202_Value(form.getUdf202());
		   udf.setUdf203_Value(form.getUdf203());
		   udf.setUdf204_Value(form.getUdf204());
		   udf.setUdf205_Value(form.getUdf205());
		   udf.setUdf206_Value(form.getUdf206());
		   udf.setUdf207_Value(form.getUdf207()); 
		   udf.setUdf208_Value(form.getUdf208());
		   udf.setUdf209_Value(form.getUdf209());
		   udf.setUdf210_Value(form.getUdf210());
		   udf.setUdf211_Value(form.getUdf211());
		   udf.setUdf212_Value(form.getUdf212());
		   udf.setUdf213_Value(form.getUdf213());
		   udf.setUdf214_Value(form.getUdf214());
		   udf.setUdf215_Value(form.getUdf215());
	         
	       udf.setUdf116_Flag(form.getUdf116_Flag());
	 	   udf.setUdf117_Flag(form.getUdf117_Flag());
	 	   udf.setUdf118_Flag(form.getUdf118_Flag());
	 	   udf.setUdf119_Flag(form.getUdf119_Flag());
	 	   udf.setUdf120_Flag(form.getUdf120_Flag());
	 	   udf.setUdf121_Flag(form.getUdf121_Flag());
	 	   udf.setUdf122_Flag(form.getUdf122_Flag());
	 	   udf.setUdf123_Flag(form.getUdf123_Flag());
	 	   udf.setUdf124_Flag(form.getUdf124_Flag());
	 	   udf.setUdf125_Flag(form.getUdf125_Flag());
	 	   udf.setUdf126_Flag(form.getUdf126_Flag());
	 	   udf.setUdf127_Flag(form.getUdf127_Flag());
	 	   udf.setUdf128_Flag(form.getUdf128_Flag());
	 	   udf.setUdf129_Flag(form.getUdf129_Flag());
	 	   udf.setUdf130_Flag(form.getUdf130_Flag());
	 	   udf.setUdf131_Flag(form.getUdf131_Flag());
	 	   udf.setUdf132_Flag(form.getUdf132_Flag());
	 	   udf.setUdf133_Flag(form.getUdf133_Flag());
	 	   udf.setUdf134_Flag(form.getUdf134_Flag());
	 	   udf.setUdf135_Flag(form.getUdf135_Flag());
	 	   udf.setUdf136_Flag(form.getUdf136_Flag());
	 	   udf.setUdf137_Flag(form.getUdf137_Flag());
	 	   udf.setUdf138_Flag(form.getUdf138_Flag());
	 	   udf.setUdf139_Flag(form.getUdf139_Flag());
	 	   udf.setUdf140_Flag(form.getUdf140_Flag());
	 	   udf.setUdf141_Flag(form.getUdf141_Flag());
	 	   udf.setUdf142_Flag(form.getUdf142_Flag());
	 	   udf.setUdf143_Flag(form.getUdf143_Flag());
	 	   udf.setUdf144_Flag(form.getUdf144_Flag());
	 	   udf.setUdf145_Flag(form.getUdf145_Flag());
	 	   udf.setUdf146_Flag(form.getUdf146_Flag());
	 	   udf.setUdf147_Flag(form.getUdf147_Flag());
	 	   udf.setUdf148_Flag(form.getUdf148_Flag());
	 	   udf.setUdf149_Flag(form.getUdf149_Flag());
	 	   udf.setUdf150_Flag(form.getUdf150_Flag());
	 	   udf.setUdf151_Flag(form.getUdf151_Flag());
	 	   udf.setUdf152_Flag(form.getUdf152_Flag());
	 	   udf.setUdf153_Flag(form.getUdf153_Flag());
	 	   udf.setUdf154_Flag(form.getUdf154_Flag());
	 	   udf.setUdf155_Flag(form.getUdf155_Flag());
	 	   udf.setUdf156_Flag(form.getUdf156_Flag());
	 	   udf.setUdf157_Flag(form.getUdf157_Flag());
	 	   udf.setUdf158_Flag(form.getUdf158_Flag());
	 	   udf.setUdf159_Flag(form.getUdf159_Flag());
	 	   udf.setUdf160_Flag(form.getUdf160_Flag());
	 	   udf.setUdf161_Flag(form.getUdf161_Flag());
	 	   udf.setUdf162_Flag(form.getUdf162_Flag());
	 	   udf.setUdf163_Flag(form.getUdf163_Flag());
	 	   udf.setUdf164_Flag(form.getUdf164_Flag());
	 	   udf.setUdf165_Flag(form.getUdf165_Flag());

	 	   udf.setUdf166_Flag(form.getUdf166_Flag());
	 	   udf.setUdf167_Flag(form.getUdf167_Flag());
	 	   udf.setUdf168_Flag(form.getUdf168_Flag());
	 	   udf.setUdf169_Flag(form.getUdf169_Flag());
	 	   udf.setUdf170_Flag(form.getUdf170_Flag());
	 	   udf.setUdf171_Flag(form.getUdf171_Flag());
	 	   udf.setUdf172_Flag(form.getUdf172_Flag());
	 	   udf.setUdf173_Flag(form.getUdf173_Flag());
	 	   udf.setUdf174_Flag(form.getUdf174_Flag());
	 	   udf.setUdf175_Flag(form.getUdf175_Flag());
	 	   udf.setUdf176_Flag(form.getUdf176_Flag());
	 	   udf.setUdf177_Flag(form.getUdf177_Flag());
	 	   udf.setUdf178_Flag(form.getUdf178_Flag());
	 	   udf.setUdf179_Flag(form.getUdf179_Flag());
	 	   udf.setUdf180_Flag(form.getUdf180_Flag());
	 	   udf.setUdf181_Flag(form.getUdf181_Flag());
	 	   udf.setUdf182_Flag(form.getUdf182_Flag());
	 	   udf.setUdf183_Flag(form.getUdf183_Flag());
	 	   udf.setUdf184_Flag(form.getUdf184_Flag());
	 	   udf.setUdf185_Flag(form.getUdf185_Flag());
	 	   udf.setUdf186_Flag(form.getUdf186_Flag());
	 	   udf.setUdf187_Flag(form.getUdf187_Flag());
	 	   udf.setUdf188_Flag(form.getUdf188_Flag());
	 	   udf.setUdf189_Flag(form.getUdf189_Flag());
	 	   udf.setUdf190_Flag(form.getUdf190_Flag());
	 	   udf.setUdf191_Flag(form.getUdf191_Flag());
	 	   udf.setUdf192_Flag(form.getUdf192_Flag());
	 	   udf.setUdf193_Flag(form.getUdf193_Flag());
	 	   udf.setUdf194_Flag(form.getUdf194_Flag());
	 	   udf.setUdf195_Flag(form.getUdf195_Flag());
	 	   udf.setUdf196_Flag(form.getUdf196_Flag());
	 	   udf.setUdf197_Flag(form.getUdf197_Flag());
	 	   udf.setUdf198_Flag(form.getUdf198_Flag());
	 	   udf.setUdf199_Flag(form.getUdf199_Flag());
	 	   udf.setUdf200_Flag(form.getUdf200_Flag());
	 	   
	 	   udf.setUdf201_Flag(form.getUdf201_Flag());
	 	   udf.setUdf202_Flag(form.getUdf202_Flag());
	 	   udf.setUdf203_Flag(form.getUdf203_Flag());
	 	   udf.setUdf204_Flag(form.getUdf204_Flag());
	 	   udf.setUdf205_Flag(form.getUdf205_Flag());
	 	   udf.setUdf206_Flag(form.getUdf206_Flag());
	 	   udf.setUdf207_Flag(form.getUdf207_Flag()); 
	 	   udf.setUdf208_Flag(form.getUdf208_Flag());
	 	   udf.setUdf209_Flag(form.getUdf209_Flag());
	 	   udf.setUdf210_Flag(form.getUdf210_Flag());
	 	   udf.setUdf211_Flag(form.getUdf211_Flag());
	 	   udf.setUdf212_Flag(form.getUdf212_Flag());
	 	   udf.setUdf213_Flag(form.getUdf213_Flag());
	 	   udf.setUdf214_Flag(form.getUdf214_Flag());
	 	   udf.setUdf215_Flag(form.getUdf215_Flag());
	       
	        
	        ILimitXRefUdf2 udfList_2[] = new ILimitXRefUdf2[1];
	        udfList_2[0] = udf;
	        ob.setXRefUdfData2(udfList_2);
	        
		}
		
		private void  addUdfToForm_2(XRefDetailForm form, OBCustomerSysXRef ob) {

			ILimitXRefUdf2[] udfDataList = ob.getXRefUdfData2();
			ILimitXRefUdf2 udf;
			if (udfDataList != null && udfDataList.length > 0) {
				udf = udfDataList[0];
		        form.setUdfId(udf.getId());
		    
               form.setUdf116_Label(udf.getUdf116_Label());
		 	   form.setUdf117_Label(udf.getUdf117_Label());
		 	   form.setUdf118_Label(udf.getUdf118_Label());
		 	   form.setUdf119_Label(udf.getUdf119_Label());
		 	   form.setUdf120_Label(udf.getUdf120_Label());
		 	   form.setUdf121_Label(udf.getUdf121_Label());
		 	   form.setUdf122_Label(udf.getUdf122_Label());
		 	   form.setUdf123_Label(udf.getUdf123_Label());
		 	   form.setUdf124_Label(udf.getUdf124_Label());
		 	   form.setUdf125_Label(udf.getUdf125_Label());
		 	   form.setUdf126_Label(udf.getUdf126_Label());
		 	   form.setUdf127_Label(udf.getUdf127_Label());
		 	   form.setUdf128_Label(udf.getUdf128_Label());
		 	   form.setUdf129_Label(udf.getUdf129_Label());
		 	   form.setUdf130_Label(udf.getUdf130_Label());
		 	   form.setUdf131_Label(udf.getUdf131_Label());
		 	   form.setUdf132_Label(udf.getUdf132_Label());
		 	   form.setUdf133_Label(udf.getUdf133_Label());
		 	   form.setUdf134_Label(udf.getUdf134_Label());
		 	   form.setUdf135_Label(udf.getUdf135_Label());
		 	   form.setUdf136_Label(udf.getUdf136_Label());
		 	   form.setUdf137_Label(udf.getUdf137_Label());
		 	   form.setUdf138_Label(udf.getUdf138_Label());
		 	   form.setUdf139_Label(udf.getUdf139_Label());
		 	   form.setUdf140_Label(udf.getUdf140_Label());
		 	   form.setUdf141_Label(udf.getUdf141_Label());
		 	   form.setUdf142_Label(udf.getUdf142_Label());
		 	   form.setUdf143_Label(udf.getUdf143_Label());
		 	   form.setUdf144_Label(udf.getUdf144_Label());
		 	   form.setUdf145_Label(udf.getUdf145_Label());
		 	   form.setUdf146_Label(udf.getUdf146_Label());
		 	   form.setUdf147_Label(udf.getUdf147_Label());
		 	   form.setUdf148_Label(udf.getUdf148_Label());
		 	   form.setUdf149_Label(udf.getUdf149_Label());
		 	   form.setUdf150_Label(udf.getUdf150_Label());
		 	   form.setUdf151_Label(udf.getUdf151_Label());
		 	   form.setUdf152_Label(udf.getUdf152_Label());
		 	   form.setUdf153_Label(udf.getUdf153_Label());
		 	   form.setUdf154_Label(udf.getUdf154_Label());
		 	   form.setUdf155_Label(udf.getUdf155_Label());
		 	   form.setUdf156_Label(udf.getUdf156_Label());
		 	   form.setUdf157_Label(udf.getUdf157_Label());
		 	   form.setUdf158_Label(udf.getUdf158_Label());
		 	   form.setUdf159_Label(udf.getUdf159_Label());
		 	   form.setUdf160_Label(udf.getUdf160_Label());
		 	   form.setUdf161_Label(udf.getUdf161_Label());
		 	   form.setUdf162_Label(udf.getUdf162_Label());
		 	   form.setUdf163_Label(udf.getUdf163_Label());
		 	   form.setUdf164_Label(udf.getUdf164_Label());
		 	   form.setUdf165_Label(udf.getUdf165_Label());

		 	   form.setUdf166_Label(udf.getUdf166_Label());
		 	   form.setUdf167_Label(udf.getUdf167_Label());
		 	   form.setUdf168_Label(udf.getUdf168_Label());
		 	   form.setUdf169_Label(udf.getUdf169_Label());
		 	   form.setUdf170_Label(udf.getUdf170_Label());
		 	   form.setUdf171_Label(udf.getUdf171_Label());
		 	   form.setUdf172_Label(udf.getUdf172_Label());
		 	   form.setUdf173_Label(udf.getUdf173_Label());
		 	   form.setUdf174_Label(udf.getUdf174_Label());
		 	   form.setUdf175_Label(udf.getUdf175_Label());
		 	   form.setUdf176_Label(udf.getUdf176_Label());
		 	   form.setUdf177_Label(udf.getUdf177_Label());
		 	   form.setUdf178_Label(udf.getUdf178_Label());
		 	   form.setUdf179_Label(udf.getUdf179_Label());
		 	   form.setUdf180_Label(udf.getUdf180_Label());
		 	   form.setUdf181_Label(udf.getUdf181_Label());
		 	   form.setUdf182_Label(udf.getUdf182_Label());
		 	   form.setUdf183_Label(udf.getUdf183_Label());
		 	   form.setUdf184_Label(udf.getUdf184_Label());
		 	   form.setUdf185_Label(udf.getUdf185_Label());
		 	   form.setUdf186_Label(udf.getUdf186_Label());
		 	   form.setUdf187_Label(udf.getUdf187_Label());
		 	   form.setUdf188_Label(udf.getUdf188_Label());
		 	   form.setUdf189_Label(udf.getUdf189_Label());
		 	   form.setUdf190_Label(udf.getUdf190_Label());
		 	   form.setUdf191_Label(udf.getUdf191_Label());
		 	   form.setUdf192_Label(udf.getUdf192_Label());
		 	   form.setUdf193_Label(udf.getUdf193_Label());
		 	   form.setUdf194_Label(udf.getUdf194_Label());
		 	   form.setUdf195_Label(udf.getUdf195_Label());
		 	   form.setUdf196_Label(udf.getUdf196_Label());
		 	   form.setUdf197_Label(udf.getUdf197_Label());
		 	   form.setUdf198_Label(udf.getUdf198_Label());
		 	   form.setUdf199_Label(udf.getUdf199_Label());
		 	   form.setUdf200_Label(udf.getUdf200_Label());
		 	   form.setUdf201_Label(udf.getUdf201_Label());
		 	   form.setUdf202_Label(udf.getUdf202_Label());
		 	   form.setUdf203_Label(udf.getUdf203_Label());
		 	   form.setUdf204_Label(udf.getUdf204_Label());
		 	   form.setUdf205_Label(udf.getUdf205_Label());
		 	   form.setUdf206_Label(udf.getUdf206_Label());
		 	   form.setUdf207_Label(udf.getUdf207_Label()); 
		 	   form.setUdf208_Label(udf.getUdf208_Label());
		 	   form.setUdf209_Label(udf.getUdf209_Label());
		 	   form.setUdf210_Label(udf.getUdf210_Label());
		 	   form.setUdf211_Label(udf.getUdf211_Label());
		 	   form.setUdf212_Label(udf.getUdf212_Label());
		 	   form.setUdf213_Label(udf.getUdf213_Label());
		 	   form.setUdf214_Label(udf.getUdf214_Label());
		 	   form.setUdf215_Label(udf.getUdf215_Label());
		 	   
				
		        
		       form.setUdf116(udf.getUdf116_Value());
		 	   form.setUdf117(udf.getUdf117_Value());
		 	   form.setUdf118(udf.getUdf118_Value());
		 	   form.setUdf119(udf.getUdf119_Value());
		 	   form.setUdf120(udf.getUdf120_Value());
		 	   form.setUdf121(udf.getUdf121_Value());
		 	   form.setUdf122(udf.getUdf122_Value());
		 	   form.setUdf123(udf.getUdf123_Value());
		 	   form.setUdf124(udf.getUdf124_Value());
		 	   form.setUdf125(udf.getUdf125_Value());
		 	   form.setUdf126(udf.getUdf126_Value());
		 	   form.setUdf127(udf.getUdf127_Value());
		 	   form.setUdf128(udf.getUdf128_Value());
		 	   form.setUdf129(udf.getUdf129_Value());
		 	   form.setUdf130(udf.getUdf130_Value());
		 	   form.setUdf131(udf.getUdf131_Value());
		 	   form.setUdf132(udf.getUdf132_Value());
		 	   form.setUdf133(udf.getUdf133_Value());
		 	   form.setUdf134(udf.getUdf134_Value());
		 	   form.setUdf135(udf.getUdf135_Value());
		 	   form.setUdf136(udf.getUdf136_Value());
		 	   form.setUdf137(udf.getUdf137_Value());
		 	   form.setUdf138(udf.getUdf138_Value());
		 	   form.setUdf139(udf.getUdf139_Value());
		 	   form.setUdf140(udf.getUdf140_Value());
		 	   form.setUdf141(udf.getUdf141_Value());
		 	   form.setUdf142(udf.getUdf142_Value());
		 	   form.setUdf143(udf.getUdf143_Value());
		 	   form.setUdf144(udf.getUdf144_Value());
		 	   form.setUdf145(udf.getUdf145_Value());
		 	   form.setUdf146(udf.getUdf146_Value());
		 	   form.setUdf147(udf.getUdf147_Value());
		 	   form.setUdf148(udf.getUdf148_Value());
		 	   form.setUdf149(udf.getUdf149_Value());
		 	   form.setUdf150(udf.getUdf150_Value());
		 	   form.setUdf151(udf.getUdf151_Value());
		 	   form.setUdf152(udf.getUdf152_Value());
		 	   form.setUdf153(udf.getUdf153_Value());
		 	   form.setUdf154(udf.getUdf154_Value());
		 	   form.setUdf155(udf.getUdf155_Value());
		 	   form.setUdf156(udf.getUdf156_Value());
		 	   form.setUdf157(udf.getUdf157_Value());
		 	   form.setUdf158(udf.getUdf158_Value());
		 	   form.setUdf159(udf.getUdf159_Value());
		 	   form.setUdf160(udf.getUdf160_Value());
		 	   form.setUdf161(udf.getUdf161_Value());
		 	   form.setUdf162(udf.getUdf162_Value());
		 	   form.setUdf163(udf.getUdf163_Value());
		 	   form.setUdf164(udf.getUdf164_Value());
		 	   form.setUdf165(udf.getUdf165_Value());

		 	   form.setUdf166(udf.getUdf166_Value());
		 	   form.setUdf167(udf.getUdf167_Value());
		 	   form.setUdf168(udf.getUdf168_Value());
		 	   form.setUdf169(udf.getUdf169_Value());
		 	   form.setUdf170(udf.getUdf170_Value());
		 	   form.setUdf171(udf.getUdf171_Value());
		 	   form.setUdf172(udf.getUdf172_Value());
		 	   form.setUdf173(udf.getUdf173_Value());
		 	   form.setUdf174(udf.getUdf174_Value());
		 	   form.setUdf175(udf.getUdf175_Value());
		 	   form.setUdf176(udf.getUdf176_Value());
		 	   form.setUdf177(udf.getUdf177_Value());
		 	   form.setUdf178(udf.getUdf178_Value());
		 	   form.setUdf179(udf.getUdf179_Value());
		 	   form.setUdf180(udf.getUdf180_Value());
		 	   form.setUdf181(udf.getUdf181_Value());
		 	   form.setUdf182(udf.getUdf182_Value());
		 	   form.setUdf183(udf.getUdf183_Value());
		 	   form.setUdf184(udf.getUdf184_Value());
		 	   form.setUdf185(udf.getUdf185_Value());
		 	   form.setUdf186(udf.getUdf186_Value());
		 	   form.setUdf187(udf.getUdf187_Value());
		 	   form.setUdf188(udf.getUdf188_Value());
		 	   form.setUdf189(udf.getUdf189_Value());
		 	   form.setUdf190(udf.getUdf190_Value());
		 	   form.setUdf191(udf.getUdf191_Value());
		 	   form.setUdf192(udf.getUdf192_Value());
		 	   form.setUdf193(udf.getUdf193_Value());
		 	   form.setUdf194(udf.getUdf194_Value());
		 	   form.setUdf195(udf.getUdf195_Value());
		 	   form.setUdf196(udf.getUdf196_Value());
		 	   form.setUdf197(udf.getUdf197_Value());
		 	   form.setUdf198(udf.getUdf198_Value());
		 	   form.setUdf199(udf.getUdf199_Value());
		 	   form.setUdf200(udf.getUdf200_Value());
		 	    
		 	   form.setUdf201(udf.getUdf201_Value());
		 	   form.setUdf202(udf.getUdf202_Value());
		 	   form.setUdf203(udf.getUdf203_Value());
		 	   form.setUdf204(udf.getUdf204_Value());
		 	   form.setUdf205(udf.getUdf205_Value());
		 	   form.setUdf206(udf.getUdf206_Value());
		 	   form.setUdf207(udf.getUdf207_Value()); 
		 	   form.setUdf208(udf.getUdf208_Value());
		 	   form.setUdf209(udf.getUdf209_Value());
		 	   form.setUdf210(udf.getUdf210_Value());
		 	   form.setUdf211(udf.getUdf211_Value());
		 	   form.setUdf212(udf.getUdf212_Value());
		 	   form.setUdf213(udf.getUdf213_Value());
		 	   form.setUdf214(udf.getUdf214_Value());
		 	   form.setUdf215(udf.getUdf215_Value());
		    
				
		       form.setUdf116_Flag(udf.getUdf116_Flag());
		 	   form.setUdf117_Flag(udf.getUdf117_Flag());
		 	   form.setUdf118_Flag(udf.getUdf118_Flag());
		 	   form.setUdf119_Flag(udf.getUdf119_Flag());
		 	   form.setUdf120_Flag(udf.getUdf120_Flag());
		 	   form.setUdf121_Flag(udf.getUdf121_Flag());
		 	   form.setUdf122_Flag(udf.getUdf122_Flag());
		 	   form.setUdf123_Flag(udf.getUdf123_Flag());
		 	   form.setUdf124_Flag(udf.getUdf124_Flag());
		 	   form.setUdf125_Flag(udf.getUdf125_Flag());
		 	   form.setUdf126_Flag(udf.getUdf126_Flag());
		 	   form.setUdf127_Flag(udf.getUdf127_Flag());
		 	   form.setUdf128_Flag(udf.getUdf128_Flag());
		 	   form.setUdf129_Flag(udf.getUdf129_Flag());
		 	   form.setUdf130_Flag(udf.getUdf130_Flag());
		 	   form.setUdf131_Flag(udf.getUdf131_Flag());
		 	   form.setUdf132_Flag(udf.getUdf132_Flag());
		 	   form.setUdf133_Flag(udf.getUdf133_Flag());
		 	   form.setUdf134_Flag(udf.getUdf134_Flag());
		 	   form.setUdf135_Flag(udf.getUdf135_Flag());
		 	   form.setUdf136_Flag(udf.getUdf136_Flag());
		 	   form.setUdf137_Flag(udf.getUdf137_Flag());
		 	   form.setUdf138_Flag(udf.getUdf138_Flag());
		 	   form.setUdf139_Flag(udf.getUdf139_Flag());
		 	   form.setUdf140_Flag(udf.getUdf140_Flag());
		 	   form.setUdf141_Flag(udf.getUdf141_Flag());
		 	   form.setUdf142_Flag(udf.getUdf142_Flag());
		 	   form.setUdf143_Flag(udf.getUdf143_Flag());
		 	   form.setUdf144_Flag(udf.getUdf144_Flag());
		 	   form.setUdf145_Flag(udf.getUdf145_Flag());
		 	   form.setUdf146_Flag(udf.getUdf146_Flag());
		 	   form.setUdf147_Flag(udf.getUdf147_Flag());
		 	   form.setUdf148_Flag(udf.getUdf148_Flag());
		 	   form.setUdf149_Flag(udf.getUdf149_Flag());
		 	   form.setUdf150_Flag(udf.getUdf150_Flag());
		 	   form.setUdf151_Flag(udf.getUdf151_Flag());
		 	   form.setUdf152_Flag(udf.getUdf152_Flag());
		 	   form.setUdf153_Flag(udf.getUdf153_Flag());
		 	   form.setUdf154_Flag(udf.getUdf154_Flag());
		 	   form.setUdf155_Flag(udf.getUdf155_Flag());
		 	   form.setUdf156_Flag(udf.getUdf156_Flag());
		 	   form.setUdf157_Flag(udf.getUdf157_Flag());
		 	   form.setUdf158_Flag(udf.getUdf158_Flag());
		 	   form.setUdf159_Flag(udf.getUdf159_Flag());
		 	   form.setUdf160_Flag(udf.getUdf160_Flag());
		 	   form.setUdf161_Flag(udf.getUdf161_Flag());
		 	   form.setUdf162_Flag(udf.getUdf162_Flag());
		 	   form.setUdf163_Flag(udf.getUdf163_Flag());
		 	   form.setUdf164_Flag(udf.getUdf164_Flag());
		 	   form.setUdf165_Flag(udf.getUdf165_Flag());

		 	   form.setUdf166_Flag(udf.getUdf166_Flag());
		 	   form.setUdf167_Flag(udf.getUdf167_Flag());
		 	   form.setUdf168_Flag(udf.getUdf168_Flag());
		 	   form.setUdf169_Flag(udf.getUdf169_Flag());
		 	   form.setUdf170_Flag(udf.getUdf170_Flag());
		 	   form.setUdf171_Flag(udf.getUdf171_Flag());
		 	   form.setUdf172_Flag(udf.getUdf172_Flag());
		 	   form.setUdf173_Flag(udf.getUdf173_Flag());
		 	   form.setUdf174_Flag(udf.getUdf174_Flag());
		 	   form.setUdf175_Flag(udf.getUdf175_Flag());
		 	   form.setUdf176_Flag(udf.getUdf176_Flag());
		 	   form.setUdf177_Flag(udf.getUdf177_Flag());
		 	   form.setUdf178_Flag(udf.getUdf178_Flag());
		 	   form.setUdf179_Flag(udf.getUdf179_Flag());
		 	   form.setUdf180_Flag(udf.getUdf180_Flag());
		 	   form.setUdf181_Flag(udf.getUdf181_Flag());
		 	   form.setUdf182_Flag(udf.getUdf182_Flag());
		 	   form.setUdf183_Flag(udf.getUdf183_Flag());
		 	   form.setUdf184_Flag(udf.getUdf184_Flag());
		 	   form.setUdf185_Flag(udf.getUdf185_Flag());
		 	   form.setUdf186_Flag(udf.getUdf186_Flag());
		 	   form.setUdf187_Flag(udf.getUdf187_Flag());
		 	   form.setUdf188_Flag(udf.getUdf188_Flag());
		 	   form.setUdf189_Flag(udf.getUdf189_Flag());
		 	   form.setUdf190_Flag(udf.getUdf190_Flag());
		 	   form.setUdf191_Flag(udf.getUdf191_Flag());
		 	   form.setUdf192_Flag(udf.getUdf192_Flag());
		 	   form.setUdf193_Flag(udf.getUdf193_Flag());
		 	   form.setUdf194_Flag(udf.getUdf194_Flag());
		 	   form.setUdf195_Flag(udf.getUdf195_Flag());
		 	   form.setUdf196_Flag(udf.getUdf196_Flag());
		 	   form.setUdf197_Flag(udf.getUdf197_Flag());
		 	   form.setUdf198_Flag(udf.getUdf198_Flag());
		 	   form.setUdf199_Flag(udf.getUdf199_Flag());
		 	   form.setUdf200_Flag(udf.getUdf200_Flag());
		 	   
		 	   form.setUdf201_Flag(udf.getUdf201_Flag());
		 	   form.setUdf202_Flag(udf.getUdf202_Flag());
		 	   form.setUdf203_Flag(udf.getUdf203_Flag());
		 	   form.setUdf204_Flag(udf.getUdf204_Flag());
		 	   form.setUdf205_Flag(udf.getUdf205_Flag());
		 	   form.setUdf206_Flag(udf.getUdf206_Flag());
		 	   form.setUdf207_Flag(udf.getUdf207_Flag()); 
		 	   form.setUdf208_Flag(udf.getUdf208_Flag());
		 	   form.setUdf209_Flag(udf.getUdf209_Flag());
		 	   form.setUdf210_Flag(udf.getUdf210_Flag());
		 	   form.setUdf211_Flag(udf.getUdf211_Flag());
		 	   form.setUdf212_Flag(udf.getUdf212_Flag());
		 	   form.setUdf213_Flag(udf.getUdf213_Flag());
		 	   form.setUdf214_Flag(udf.getUdf214_Flag());
		 	   form.setUdf215_Flag(udf.getUdf215_Flag());
		       
			}
		}
		
		
		
		private void  addCoBorrowerToOb(XRefDetailForm form, OBCustomerSysXRef ob) {
			
			List coBorrowerList1 = form.getRestCoBorrowerList();
			OBLimitXRefCoBorrower form1 = new OBLimitXRefCoBorrower();
			int cnt= coBorrowerList1.size();
			ILimitXRefCoBorrower coBorrowerList[] = new ILimitXRefCoBorrower[cnt];

			if (coBorrowerList1 != null && coBorrowerList1.size() > 0)
			{		
			
			for(int i=0; i<coBorrowerList1.size(); i++) {
				ILimitXRefCoBorrower coBorro = new OBLimitXRefCoBorrower();
				form1=(OBLimitXRefCoBorrower) coBorrowerList1.get(i);
			coBorro.setCoBorrowerId(form1.getCoBorrowerId());
			coBorro.setCoBorrowerName(form1.getCoBorrowerName());
	       
	        coBorrowerList[i] = coBorro;
				}
	    //    System.out.println("coBorrowerList======== "+coBorrowerList);
	        ob.setXRefCoBorrowerData(coBorrowerList);
			}

			else {
				   ob.setXRefCoBorrowerData(coBorrowerList);
			}
			
		/*	List coBorrowerList = form.getRestCoBorrowerList();
			ILimitXRefCoBorrower coBorro;
			if (coBorrowerList != null && coBorrowerList.size() > 0) {
				System.out.println("coBorrowerList.length in Mapper is:: "+coBorrowerList.size());
				
				List coBorrow= new ArrayList<XRefDetailForm>();
				for(int i=0; i<coBorrowerList.size(); i++) {
					OBLimitXRefCoBorrower form1 = new OBLimitXRefCoBorrower();
				coBorro = (ILimitXRefCoBorrower) coBorrowerList.get(i);
				form1.setCoBorrowerId(coBorro.getCoBorrowerId());
				form1.setCoBorrowerName(coBorro.getCoBorrowerName());
		 	   
				coBorrow.add(form1);
				}
				
				   ob.setRestCoBorrowerList(coBorrow);
				   
		 			}*/
			
		}
		

		private void  addCoBorrowerToForm(XRefDetailForm form, OBCustomerSysXRef ob) {

			ILimitXRefCoBorrower[] coBorrowerList = ob.getXRefCoBorrowerData();
			ILimitXRefCoBorrower coBorro;
			if (coBorrowerList != null && coBorrowerList.length > 0) {
				System.out.println("coBorrowerList.length in Mapper is:: "+coBorrowerList.length);
				List coBorrow= new ArrayList<XRefDetailForm>();
				List<String> LineCoBorrowerIdList = new ArrayList<String>();
				for(int i=0; i<coBorrowerList.length; i++) {
					OBLimitXRefCoBorrower form1 = new OBLimitXRefCoBorrower();
				coBorro = coBorrowerList[i];
				form1.setCoBorrowerId(coBorro.getCoBorrowerId());
				form1.setCoBorrowerName(coBorro.getCoBorrowerName());
		 	   
				coBorrow.add(form1);
				LineCoBorrowerIdList.add(coBorro.getCoBorrowerId());

				}
				
				   form.setRestCoBorrowerList(coBorrow);
				   
				//   String lineCoBorrowerIds = UIUtil.getDelimitedStringFromList(LineCoBorrowerIdList, ",");
				   String lineCoBorrowerIds = UIUtil.getJSONStringFromList(LineCoBorrowerIdList, ",");

				   lineCoBorrowerIds = lineCoBorrowerIds==null ? "" : lineCoBorrowerIds ;
					form.setLineCoBorrowerLiabIds(lineCoBorrowerIds);
		 			}
		 	}		


		
	
}
