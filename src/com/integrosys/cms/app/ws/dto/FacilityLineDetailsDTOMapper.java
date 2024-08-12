package com.integrosys.cms.app.ws.dto;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.springframework.stereotype.Service;

import com.aurionpro.clims.rest.common.ValidationErrorDetailsDTO;
import com.aurionpro.clims.rest.common.ValidationUtilityRest;
import com.aurionpro.clims.rest.dto.FacilityBodyRestRequestDTO;
import com.aurionpro.clims.rest.dto.FacilityLineDetailRestRequestDTO;
import com.aurionpro.clims.rest.dto.ResponseMessageDetailDTO;
import com.aurionpro.clims.rest.dto.SecurityDetailRestRequestDTO;
import com.aurionpro.clims.rest.mapper.UdfDetailsRestDTOMapper;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;
import com.integrosys.cms.app.customer.bus.OBCoBorrowerDetails;
import com.integrosys.cms.app.limit.bus.IFacilityCoBorrowerDetails;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBFacilityCoBorrowerDetails;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.ws.common.CLIMSWebService;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.limit.LmtDetailForm;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.manualinput.limit.XRefDetailForm;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.cms.ui.manualinput.security.SecDetailForm;

@Service
public class FacilityLineDetailsDTOMapper {

	 UdfDetailsRestDTOMapper udfDetailsRestDTOMapper=new UdfDetailsRestDTOMapper();


	public XRefDetailForm  getFormFromRestDTO( FacilityLineDetailRestRequestDTO dto) {

		XRefDetailForm xrefForm = new XRefDetailForm();
		
		
		if(dto.getSystemId()!=null && !dto.getSystemId().trim().isEmpty()){
			xrefForm.setFacilitySystemID(dto.getSystemId().trim());
		}else{
			xrefForm.setFacilitySystemID("");
		}
		
		if(dto.getLiabBranch()!=null && !dto.getLiabBranch().trim().isEmpty()){
			xrefForm.setLiabBranch(dto.getLiabBranch().trim());
		}else{
			xrefForm.setLiabBranch("");
		}
		
		
		if(dto.getIsCurrencyRestriction()!=null && !dto.getIsCurrencyRestriction().trim().isEmpty()){
			xrefForm.setCurrencyRestriction(dto.getIsCurrencyRestriction().trim());
		}else{
			xrefForm.setCurrencyRestriction("N");
		}
		if(dto.getMainLineCode()!=null && !dto.getMainLineCode().trim().isEmpty()){
			xrefForm.setMainLineCode(dto.getMainLineCode().trim());
		}else{
			xrefForm.setMainLineCode("");
		}
		if(dto.getSerialNo()!=null && !dto.getSerialNo().trim().isEmpty()){
			xrefForm.setSerialNo(dto.getSerialNo().trim());
		}else{
			xrefForm.setSerialNo("1");
		}
		
		
		if(dto.getReleasedAmount()!=null && !dto.getReleasedAmount().trim().isEmpty()){
			xrefForm.setReleasedAmount(dto.getReleasedAmount().trim());
		}else{
			xrefForm.setReleasedAmount("0");
		}
		
		if(dto.getUtilizedAmount()!=null && !dto.getUtilizedAmount().trim().isEmpty()){
			xrefForm.setUtilizedAmount(dto.getUtilizedAmount().trim());
		}else{
			xrefForm.setUtilizedAmount("0");
		}
		
		if(dto.getReleasedDate()!=null && !dto.getReleasedDate().trim().isEmpty()){
			xrefForm.setReleaseDate(dto.getReleasedDate().trim());
		}else{
			xrefForm.setReleaseDate("");
		}
		if(dto.getSendToFile()!=null && !dto.getSendToFile().trim().isEmpty()){
			xrefForm.setSendToFile(dto.getSendToFile().trim());
		}else{
			xrefForm.setSendToFile("");
		}
		if(dto.getLmtStartDate()!=null && !dto.getLmtStartDate().trim().isEmpty()){
			xrefForm.setLimitStartDate(dto.getLmtStartDate().trim());
		}else{
			xrefForm.setLimitStartDate("");
		}
		if(dto.getLmtExpiryDate()!=null && !dto.getLmtExpiryDate().trim().isEmpty()){
			xrefForm.setDateOfReset(dto.getLmtExpiryDate().trim());
		}else{
			xrefForm.setDateOfReset("");
		}
		
		if(dto.getIntradayLimitExpDate()!=null && !dto.getIntradayLimitExpDate().trim().isEmpty()){
			xrefForm.setIntradayLimitExpiryDate(dto.getIntradayLimitExpDate().trim());
		}else{
			xrefForm.setIntradayLimitExpiryDate("");
		}
		
		if(dto.getDayLightLimit()!=null && !dto.getDayLightLimit().trim().isEmpty()){
			xrefForm.setDayLightLimit(dto.getDayLightLimit().trim());
		}else{
			xrefForm.setDayLightLimit("");
		}
		
		if(dto.getIsAvailable()!=null && !dto.getIsAvailable().trim().isEmpty()){
			xrefForm.setAvailable(dto.getIsAvailable().trim());
		}else{
			xrefForm.setAvailable("");
		}
		
		if(dto.getFreeze()!=null && !dto.getFreeze().trim().isEmpty()){
			xrefForm.setFreeze(dto.getFreeze().trim());
		}else{
			xrefForm.setFreeze("");
		}

		if(dto.getRevolvingLine()!=null && !dto.getRevolvingLine().trim().isEmpty()){
			xrefForm.setRevolvingLine(dto.getRevolvingLine().trim());
		}else{
			xrefForm.setRevolvingLine("");
		}
		
		if(dto.getScmFlag()!=null && !dto.getScmFlag().trim().isEmpty()){
			xrefForm.setScmFlag(dto.getScmFlag().trim());
		}else{
			xrefForm.setScmFlag("");
		}
		
		if(dto.getLastAvailDate()!=null && !dto.getLastAvailDate().trim().isEmpty()){
			xrefForm.setLastavailableDate(dto.getLastAvailDate().trim());
		}else{
			xrefForm.setLastavailableDate("");
		}
		
		if(dto.getUploadDate()!=null && !dto.getUploadDate().trim().isEmpty()){ //ss
			xrefForm.setUploadDate(dto.getUploadDate().trim());
		}else{
			xrefForm.setUploadDate("");
		}
		
		if(dto.getSegment1()!=null && !dto.getSegment1().trim().isEmpty()){
			xrefForm.setSegment(dto.getSegment1().trim());
		}else{
			xrefForm.setSegment("");
		}
		
		if(dto.getRuleId()!=null && !dto.getRuleId().trim().isEmpty()){
			xrefForm.setRuleId(dto.getRuleId().trim());
		}else{
			xrefForm.setRuleId("");
		}
		if(dto.getCapitalMarketExposure()!=null && !dto.getCapitalMarketExposure().trim().isEmpty()){
			xrefForm.setIsCapitalMarketExposer(dto.getCapitalMarketExposure().trim());
		}else{
			xrefForm.setIsCapitalMarketExposer("");
		}
		
		if(dto.getPslFlag()!=null && !dto.getPslFlag().trim().isEmpty()){
			xrefForm.setIsPrioritySector(dto.getPslFlag().trim());
		}else{
			xrefForm.setIsPrioritySector("");
		}

		if(dto.getPslValue()!=null && !dto.getPslValue().trim().isEmpty()){
			xrefForm.setPrioritySector(dto.getPslValue().trim());
		}else{
			xrefForm.setPrioritySector("");
		}
		
		if(dto.getRealEstateExposure()!=null && !dto.getRealEstateExposure().trim().isEmpty()){
			xrefForm.setIsRealEstateExposer(dto.getRealEstateExposure().trim());
		}else{
			xrefForm.setIsRealEstateExposer("No");
		}
		
		
		if(dto.getUncondiCanclCommit()!=null && !dto.getUncondiCanclCommit().trim().isEmpty()){
			xrefForm.setUncondiCancl(dto.getUncondiCanclCommit().trim());
		}else{
			xrefForm.setUncondiCancl("");
		}
		
		if(dto.getIntrestRateType()!=null && !dto.getIntrestRateType().trim().isEmpty()){
			xrefForm.setInterestRateType(dto.getIntrestRateType().trim());
		}else{
			xrefForm.setInterestRateType("");
		}
		
		if(dto.getIntrestRate()!=null && !dto.getIntrestRate().trim().isEmpty()){
			xrefForm.setIntRateFix(dto.getIntrestRate().trim());
		}else{
			xrefForm.setIntRateFix("0");
		}
		
		if(dto.getFloatingRateType()!=null && !dto.getFloatingRateType().trim().isEmpty()){
			xrefForm.setIntRateFloatingType(dto.getFloatingRateType().trim());
		}else{
			xrefForm.setIntRateFloatingType("");
		}

		if(dto.getMargin()!=null && !dto.getMargin().trim().isEmpty()){
			xrefForm.setIntRateFloatingMargin(dto.getMargin().trim());
		}else{
			xrefForm.setIntRateFloatingMargin("0");
		}
		
		if(dto.getRealEstate()!=null && !dto.getRealEstate().trim().isEmpty()){
			xrefForm.setEstateType(dto.getRealEstate().trim());
		}else{
			xrefForm.setEstateType("");
		}
		
		if(dto.getCommRealEstate()!=null && !dto.getCommRealEstate().trim().isEmpty()){
			xrefForm.setCommRealEstateType(dto.getCommRealEstate().trim());
		}else{
			xrefForm.setCommRealEstateType("");
		}
		
		if(dto.getDayLightLmtAvail()!=null && !dto.getDayLightLmtAvail().trim().isEmpty()){
			xrefForm.setIsDayLightLimitAvailable(dto.getDayLightLmtAvail().trim());
		}else{
			xrefForm.setIsDayLightLimitAvailable("No");
		}
		
		if(dto.getDayLightLmtAppr()!=null && !dto.getDayLightLmtAppr().trim().isEmpty()){
			xrefForm.setDayLightLimitApproved(dto.getDayLightLmtAppr().trim());
		}else{
			xrefForm.setDayLightLimitApproved("");
		}

		if(dto.getLimiIndays()!=null && !dto.getLimiIndays().trim().isEmpty()){
			xrefForm.setLimitTenorDays(dto.getLimiIndays().trim());
		}else{
			xrefForm.setLimitTenorDays("");
		}

		if(dto.getClosedFlag()!=null && !dto.getClosedFlag().trim().isEmpty()){
			xrefForm.setCloseFlag(dto.getClosedFlag().trim());
		}else{
			xrefForm.setCloseFlag("");
		}
		
		if(dto.getVendorDtls()!=null && !dto.getVendorDtls().trim().isEmpty()){   //ss
			xrefForm.setVendorDtls(dto.getVendorDtls().trim());
		}else{
			xrefForm.setVendorDtls("");
		}
		
		if(dto.getLimitCustomerRestrict()!=null && !dto.getLimitCustomerRestrict().trim().isEmpty()){
			xrefForm.setLimitRestriction(dto.getLimitCustomerRestrict().trim());
		}else{
			xrefForm.setLimitRestriction("");
		}

		if(dto.getSecurity1()!=null && !dto.getSecurity1().trim().isEmpty()){
			xrefForm.setSecurity1(dto.getSecurity1().trim());
		}else{
			xrefForm.setSecurity1("");
		}
		
		if(dto.getSecurity2()!=null && !dto.getSecurity2().trim().isEmpty()){
			xrefForm.setSecurity2(dto.getSecurity2().trim());
		}else{
			xrefForm.setSecurity2("");
		}
		
		if(dto.getSecurity3()!=null && !dto.getSecurity3().trim().isEmpty()){
			xrefForm.setSecurity3(dto.getSecurity3().trim());
		}else{
			xrefForm.setSecurity3("");
		}

		if(dto.getSecurity4()!=null && !dto.getSecurity4().trim().isEmpty()){
			xrefForm.setSecurity4(dto.getSecurity4().trim());
		}else{
			xrefForm.setSecurity4("");
		}

		if(dto.getSecurity5()!=null && !dto.getSecurity5().trim().isEmpty()){
			xrefForm.setSecurity5(dto.getSecurity5().trim());
		}else{
			xrefForm.setSecurity5("");
		}

		if(dto.getSecurity6()!=null && !dto.getSecurity6().trim().isEmpty()){
			xrefForm.setSecurity6(dto.getSecurity6().trim());
		}else{
			xrefForm.setSecurity6("");
		}
		
		if(dto.getInternalRemarks()!=null && !dto.getInternalRemarks().trim().isEmpty()){
			xrefForm.setInternalRemarks(dto.getInternalRemarks().trim());
		}else{
			xrefForm.setInternalRemarks("");
		}

		
		if (null != dto.getIdlEffectiveFromDate() && !"".equals(dto.getIdlEffectiveFromDate())) {
			xrefForm.setIdlEffectiveFromDate(dto.getIdlEffectiveFromDate());
		}else {
			xrefForm.setIdlEffectiveFromDate(null);
		}
		
		if (null != dto.getIdlExpiryDate()  && !"".equals(dto.getIdlExpiryDate())) {
			xrefForm.setIdlExpiryDate(dto.getIdlExpiryDate());
		}else {
			xrefForm.setIdlExpiryDate(null);
		}
		
		
		if (null != dto.getIdlAmount()  && !"".equals(dto.getIdlAmount())) {
			xrefForm.setIdlAmount(UIUtil.formatWithCommaAndDecimal(dto.getIdlAmount()));
		}else {
			xrefForm.setIdlAmount("");
		}
		
		
		
		if((dto.getUdfList()!=null && !dto.getUdfList().isEmpty() && dto.getUdfList().size()>0) ||
		(dto.getUdf2List()!=null && !dto.getUdf2List().isEmpty() && dto.getUdf2List().size()>0)){
		udfDetailsRestDTOMapper.getUdfFormFromDTO(dto, xrefForm,"WS_LINE_CREATE_REST_UDF1");	
		}
		
		
		
		
		return xrefForm;
	}

	
	public FacilityLineDetailRestRequestDTO getRestRequestDTOWithActualValues(FacilityLineDetailRestRequestDTO requestDTO, String event, FacilityBodyRestRequestDTO facilityRequestDTO, LmtDetailForm facilityForm) 
	{
		ILimitDAO limitDAO = (ILimitDAO) BeanHouse.get("limitJdbcDao");
		ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
		String fcunsSystem=PropertyManager.getValue("fcubs.systemName");
		String ubslimitSystem=PropertyManager.getValue("ubs.systemName");
		String errorCode = null;
		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();

		UdfDetailsRestDTOMapper mapper = new UdfDetailsRestDTOMapper();
		if(requestDTO.getSegment1()!=null && !requestDTO.getSegment1().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityName("entryCode", requestDTO.getSegment1().trim(),"segment1",errors,"SEGMENT_1");
			if(!(obj instanceof ActionErrors)){
				requestDTO.setSegment1(((ICommonCodeEntry)obj).getEntryCode());
			}
			else {
				errors.add("segment1",new ActionMessage("error.invalid"));
			}
		}else{
			errors.add("segment1",new ActionMessage("error.mandatory"));
		}

		if(requestDTO.getRuleId()!=null && !requestDTO.getRuleId().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityName("entryCode", requestDTO.getRuleId().trim(),"ruleId",errors,"NPA_RULE_ID");
			if(!(obj instanceof ActionErrors)){
				requestDTO.setRuleId(((ICommonCodeEntry)obj).getEntryCode());
			}
			else {
				errors.add("ruleId",new ActionMessage("error.invalid"));
			}
		}else{
			errors.add("ruleId",new ActionMessage("error.mandatory"));
		}

		if(requestDTO.getCapitalMarketExposure()!=null && !requestDTO.getCapitalMarketExposure().trim().isEmpty()){

			if(!("Yes".equals(requestDTO.getCapitalMarketExposure().trim()) || "No".equals(requestDTO.getCapitalMarketExposure().trim()))){
				errors.add("capitalMarketExposure",new ActionMessage("error.invalid"));
			}
		}else{
			errors.add("capitalMarketExposure", new ActionMessage("error.string.mandatory"));
		}
		if(requestDTO.getPslFlag()!=null && !requestDTO.getPslFlag().trim().isEmpty()){

			if(!("Yes".equals(requestDTO.getPslFlag().trim()) || "No".equals(requestDTO.getPslFlag().trim()))){
				errors.add("pslFlag",new ActionMessage("error.invalid"));
			}
			else
			{
				if(("Yes".equals(requestDTO.getPslFlag().trim())))
				{
					if(requestDTO.getPslValue()!=null && !requestDTO.getPslValue().trim().isEmpty()){
						Object obj = masterObj.getObjectByEntityName("entryCode", requestDTO.getPslValue().trim(),"pslValue",errors,"PRIORITY_SECTOR_Y");
						if(!(obj instanceof ActionErrors)){
							requestDTO.setPslValue(((ICommonCodeEntry)obj).getEntryCode());
						}
						else {
							errors.add("pslValue",new ActionMessage("error.invalid"));
						}
					}else{
						errors.add("pslValue",new ActionMessage("error.mandatory"));
					}
				}
				if(("No".equals(requestDTO.getPslFlag().trim())))
				{
					if(requestDTO.getPslValue()!=null && !requestDTO.getPslValue().trim().isEmpty()){
						Object obj = masterObj.getObjectByEntityName("entryCode", requestDTO.getPslValue().trim(),"pslValue",errors,"PRIORITY_SECTOR");
						if(!(obj instanceof ActionErrors)){
							requestDTO.setPslValue(((ICommonCodeEntry)obj).getEntryCode());
						}
						else {
							errors.add("pslValue",new ActionMessage("error.invalid"));
						}
					}else{
						errors.add("pslValue",new ActionMessage("error.mandatory"));
					}
				}
				
			}
		}else{
			errors.add("pslFlag", new ActionMessage("error.string.mandatory"));
		}

		if(requestDTO.getRealEstateExposure()!=null && !requestDTO.getRealEstateExposure().trim().isEmpty()){

			if(!("Yes".equals(requestDTO.getRealEstateExposure().trim()) || "No".equals(requestDTO.getRealEstateExposure().trim()))){
				errors.add("realEstateExposure",new ActionMessage("error.invalid"));
			}else {
				requestDTO.setRealEstateExposure(requestDTO.getRealEstateExposure().trim());
			}
		}else{
			errors.add("realEstateExposure", new ActionMessage("error.string.mandatory"));
		}
		
		if(requestDTO.getUncondiCanclCommit()!=null && !requestDTO.getUncondiCanclCommit().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityName("entryCode", requestDTO.getUncondiCanclCommit().trim(),"uncondiCanclCommit",errors,"UNCONDI_CANCL_COMMITMENT");
			if(!(obj instanceof ActionErrors)){
				requestDTO.setUncondiCanclCommit(((ICommonCodeEntry)obj).getEntryCode());
			}
			else {
				errors.add("uncondiCanclCommit",new ActionMessage("error.invalid"));
			}
		}else{
			errors.add("uncondiCanclCommit",new ActionMessage("error.mandatory"));
		}
		
		if(requestDTO.getRealEstate()!=null && !requestDTO.getRealEstate().trim().isEmpty()){

			if(!("Commercial Real estate".equals(requestDTO.getRealEstate().trim()) || "Residential real Estate".equals(requestDTO.getRealEstate().trim())
					||  "Indirect real estate".equals(requestDTO.getRealEstate().trim()))){
				errors.add("realEstate",new ActionMessage("error.invalid"));
			}
			else
			{
				requestDTO.setRealEstate(requestDTO.getRealEstate().trim());
				
				if(("Commercial Real estate".equals(requestDTO.getRealEstate().trim())))
				{
					if(requestDTO.getCommRealEstate()!=null && !requestDTO.getCommRealEstate().trim().isEmpty()){
						Object obj = masterObj.getObjectByEntityName("entryCode", requestDTO.getCommRealEstate().trim(),"commRealEstate",errors,"COMMERCIAL_REAL_ESTATE");
						if(!(obj instanceof ActionErrors)){
							requestDTO.setCommRealEstate(((ICommonCodeEntry)obj).getEntryCode());
						}
						else {
							errors.add("commRealEstate",new ActionMessage("error.invalid"));
						}
					}else{
						errors.add("commRealEstate",new ActionMessage("error.mandatory"));
					}
				}else {
					requestDTO.setCommRealEstate(null);
				}
			}
		}
		if(requestDTO.getClosedFlag()!=null && !requestDTO.getClosedFlag().trim().isEmpty()){

			if(!("Y".equals(requestDTO.getClosedFlag().trim()) || "N".equals(requestDTO.getClosedFlag().trim()))){
				errors.add("closedFlag",new ActionMessage("error.invalid"));
			}
		}else{
			errors.add("closedFlag", new ActionMessage("error.string.mandatory"));
		}
		if(requestDTO.getUdfList()!=null && !requestDTO.getUdfList().isEmpty() 
				&& requestDTO.getUdfList().size()>0 )
		{
			mapper.getUdfRequestDTOWithActualValues(requestDTO,"WS_LINE_CREATE_REST_UDF1",errors);
		}
		if(requestDTO.getUdf2List()!=null && !requestDTO.getUdf2List().isEmpty() 
				&& requestDTO.getUdf2List().size()>0 )
		{
			mapper.getUdfRequestDTOWithActualValues(requestDTO,"WS_LINE_CREATE_REST_UDF2",errors);
		}
		if(requestDTO.getIntrestRateType()!=null && !requestDTO.getIntrestRateType().trim().isEmpty()){

			if(!("fixed".equals(requestDTO.getIntrestRateType().trim()) || "floating".equals(requestDTO.getIntrestRateType().trim()))){
				errors.add("intrestRateType",new ActionMessage("error.invalid"));
			}
		}
		
		if(requestDTO.getFloatingRateType()!=null && !requestDTO.getFloatingRateType().trim().isEmpty()){

			if(!("BASE".equals(requestDTO.getFloatingRateType().trim()) || "BPLR".equals(requestDTO.getFloatingRateType().trim()))){
				errors.add("floatingRateType",new ActionMessage("error.invalid"));
			}
		}
		
		if(requestDTO.getMarginAddSub()!=null && !requestDTO.getMarginAddSub().trim().isEmpty()){

			if(!("+".equals(requestDTO.getMarginAddSub().trim()) || "-".equals(requestDTO.getMarginAddSub().trim()))){
				errors.add("marginAddSub",new ActionMessage("error.invalid"));
			}
		}
		
		
		if ((requestDTO.getSecurity1() != null && !requestDTO.getSecurity1().trim().isEmpty())
				|| (requestDTO.getSecurity2() != null && !requestDTO.getSecurity2().trim().isEmpty())
				|| (requestDTO.getSecurity3() != null && !requestDTO.getSecurity3().trim().isEmpty())
				|| (requestDTO.getSecurity4() != null && !requestDTO.getSecurity4().trim().isEmpty())
				|| (requestDTO.getSecurity5() != null && !requestDTO.getSecurity5().trim().isEmpty())
				|| (requestDTO.getSecurity6() != null && !requestDTO.getSecurity6().trim().isEmpty())){
			
			if (requestDTO.getSecurity1() != null && !requestDTO.getSecurity1().trim().isEmpty()) {
				boolean flag1 = false;
				flag1 = collateralDAO.checkCollateralIdCount(requestDTO.getSecurity1(), facilityRequestDTO.getCamId());
				if (!flag1) {
					errors.add("security1", new ActionMessage("error.invalid"));
				}
			}
			
			
			
			if (requestDTO.getSecurity2() != null && !requestDTO.getSecurity2().trim().isEmpty()) {
				boolean flag2 = false;
				flag2 = collateralDAO.checkCollateralIdCount(requestDTO.getSecurity2(), facilityRequestDTO.getCamId());
				if (!flag2) {
					errors.add("security2", new ActionMessage("error.invalid"));
				}
			}
			
			
			if (requestDTO.getSecurity3() != null && !requestDTO.getSecurity3().trim().isEmpty()) {
				boolean flag3 = false;
				flag3 = collateralDAO.checkCollateralIdCount(requestDTO.getSecurity3(), facilityRequestDTO.getCamId());
				if (!flag3) {
					errors.add("security3", new ActionMessage("error.invalid"));
				}
			}
			
			
			if (requestDTO.getSecurity4() != null && !requestDTO.getSecurity4().trim().isEmpty()) {
				boolean flag4 = false;
				flag4 = collateralDAO.checkCollateralIdCount(requestDTO.getSecurity4(), facilityRequestDTO.getCamId());
				if (!flag4) {
					errors.add("security4", new ActionMessage("error.invalid"));
				}
			}
			
			
			if (requestDTO.getSecurity5() != null && !requestDTO.getSecurity5().trim().isEmpty()) {
				boolean flag5 = false;
				flag5 = collateralDAO.checkCollateralIdCount(requestDTO.getSecurity5(), facilityRequestDTO.getCamId());
				if (!flag5) {
					errors.add("security5", new ActionMessage("error.invalid"));
				}
			}
			
			
			if (requestDTO.getSecurity6() != null && !requestDTO.getSecurity6().trim().isEmpty()) {
				boolean flag6 = false;
				flag6 = collateralDAO.checkCollateralIdCount(requestDTO.getSecurity6(), facilityRequestDTO.getCamId());
				if (!flag6) {
					errors.add("security6", new ActionMessage("error.invalid"));
				}
			}
			
			
		}
		
		
		if(requestDTO.getDayLightLmtAppr()!=null && !requestDTO.getDayLightLmtAppr().trim().isEmpty()){

			if(!("Yes".equals(requestDTO.getDayLightLmtAppr().trim()) || "No".equals(requestDTO.getDayLightLmtAppr().trim()))){
				errors.add("dayLightLmtAppr",new ActionMessage("error.invalid"));
			}
		}
		

		if(requestDTO.getIsAvailable()!=null && !requestDTO.getIsAvailable().trim().isEmpty()){

			if(!("Yes".equals(requestDTO.getIsAvailable().trim()) || "No".equals(requestDTO.getIsAvailable().trim()))){
				errors.add("isAvailable",new ActionMessage("error.invalid"));
			}
		}
		

		if(requestDTO.getFreeze()!=null && !requestDTO.getFreeze().trim().isEmpty()){

			if(!("Yes".equals(requestDTO.getFreeze().trim()) || "No".equals(requestDTO.getFreeze().trim()))){
				errors.add("freeze",new ActionMessage("error.invalid"));
			}
		}
		
		if(requestDTO.getSendToFile()!=null && !requestDTO.getSendToFile().trim().isEmpty()){

			if(!("Y".equals(requestDTO.getSendToFile().trim()) || "N".equals(requestDTO.getSendToFile().trim()))){
				errors.add("sendToFile",new ActionMessage("error.invalid"));
			}
		}
		

		if (requestDTO.getVendorDtls() != null && !requestDTO.getVendorDtls().trim().isEmpty()) {

			boolean flag1 = false;
			flag1 = limitDAO.checkVendorCount(facilityRequestDTO.getCamId(), requestDTO.getVendorDtls() );
			if (!flag1) {
				errors.add("vendorDtls", new ActionMessage("error.invalid"));
			}

		}
		
		
		if (requestDTO.getBranchAllowed() != null && !requestDTO.getBranchAllowed().trim().isEmpty()) {
			String arr[] = requestDTO.getBranchAllowed().split(",");
			boolean flag = false;

			for (int i = 0; i < arr.length; i++) {
				if (null != arr[i] && !arr[i].trim().isEmpty()) {
					flag = limitDAO.checkLiabBrancProdCurrCount("branch",arr[i]);
					if (!flag) {
						errors.add(arr[i]+" - branchAllowed", new ActionMessage("error.invalid"));
					}

				}
			}

		}
		
		
				
		if (requestDTO.getProductAllowed() != null && !requestDTO.getProductAllowed().trim().isEmpty()) {
			String arr[] = requestDTO.getProductAllowed().split(",");
			boolean flag = false;

			for (int i = 0; i < arr.length; i++) {
				if (null != arr[i] && !arr[i].trim().isEmpty()) {
					flag = limitDAO.checkLiabBrancProdCurrCount("product",arr[i]);
					if (!flag) {
						errors.add(arr[i]+" - productAllowed", new ActionMessage("error.invalid"));
					}
				}

			}

		}
		
		
		if (requestDTO.getCurrencyAllowed() != null && !requestDTO.getCurrencyAllowed().trim().isEmpty()) {
			String arr[] = requestDTO.getCurrencyAllowed().split(",");
			boolean flag = false;

			for (int i = 0; i < arr.length; i++) {
				if (null != arr[i] && !arr[i].trim().isEmpty()) {
					flag = limitDAO.checkLiabBrancProdCurrCount("currency",arr[i]);
					if (!flag) {
						errors.add(arr[i]+" - currencyAllowed", new ActionMessage("error.invalid"));
					}
				}

			}

		}
		
		if(facilityRequestDTO.getBorrowerList() !=null && !facilityRequestDTO.getBorrowerList().isEmpty() 
				&& facilityRequestDTO.getBorrowerList().size()>0  )
		{
			if (requestDTO.getLineCoborrowerList()!=null && !requestDTO.getLineCoborrowerList().isEmpty() 
					&& requestDTO.getLineCoborrowerList().size()>0 ){

				HashMap borrowerMap = new HashMap();
				List<String>liabid= new ArrayList<String>();
				String facCoBorrowerLiabIds="";
				StringBuilder str1 = new StringBuilder("");		

				/*List<String> CoBorrowerName= new ArrayList<String>();
				String facCoBorrowerNames="";
				StringBuilder str2 = new StringBuilder("");*/

				if (requestDTO.getLineCoborrowerList().size() > 5) {

					errors.add("borrowerList", new ActionMessage("error.string.duplicate.coBorrower.size"));
					borrowerMap.put("borrowerList", errors);
				}

				else {
					//List<CoBorrowerDetailsRestRequestDTO> facCoBorrowerListNew = new ArrayList<CoBorrowerDetailsRestRequestDTO>();

					for (int i = 0;i<requestDTO.getLineCoborrowerList().size();i++){

						//CoBorrowerDetailsRestRequestDTO ob= facilityRequestDTO.getBorrowerList().get(i);
						liabid.add(requestDTO.getLineCoborrowerList().get(i).getCoBorrowerLiabId()+"-"+requestDTO.getLineCoborrowerList().get(i).getCoBorrowerName());
						str1.append(requestDTO.getLineCoborrowerList().get(i).getCoBorrowerLiabId()+"-"+requestDTO.getLineCoborrowerList().get(i).getCoBorrowerName()).append(",");
					}

					facCoBorrowerLiabIds = str1.toString();

					// Condition check to remove the last comma
					if (facCoBorrowerLiabIds.length() > 0) {
						facCoBorrowerLiabIds = facCoBorrowerLiabIds.substring( 0, facCoBorrowerLiabIds.length() - 1);	
					}

					//facCoBorrowerLiabIds= String.join(",",liabid);
					List<String> selectedCoBorrowerIds = UIUtil.getListFromDelimitedString(facCoBorrowerLiabIds, ",");
					
					//List partyCoBorrowerList = facilityRequestDTO.getBorrowerList();

					boolean flag=false;
					if(null != selectedCoBorrowerIds && !selectedCoBorrowerIds.isEmpty() ) {

						for(int i=0; i<selectedCoBorrowerIds.size(); i++) {
							for(int j=0; j<facilityRequestDTO.getBorrowerList().size(); j++) {
								//IFacilityCoBorrowerDetails facCoBorrower = new OBFacilityCoBorrowerDetails();
								//ICoBorrowerDetails partyCoBorrower = new OBCoBorrowerDetails();

								//partyCoBorrower= (ICoBorrowerDetails) partyCoBorrowerList.get(j);
								//String liabId= partyCoBorrower.getCoBorrowerLiabId();
								String borroName= facilityRequestDTO.getBorrowerList().get(j).getCoBorrowerLiabId() +"-"+facilityRequestDTO.getBorrowerList().get(j).getCoBorrowerName();
								//	String[] borroNm= borroName.split("-");
								//	borroName=borroNm[1];
								if (selectedCoBorrowerIds.get(i).equalsIgnoreCase(borroName)) {
									flag=true;
									break;
								}

							}
							if(!flag) {

								String BorrowerId =selectedCoBorrowerIds.get(i);
								errors.add("lineCoBorrowerLiabId_or_lineCoBorrowerName", new ActionMessage("error.borrowerID")); 
								borrowerMap.put(BorrowerId,errors);
								flag=false;
							}
						}
					}

				}

			}
		}
		if(requestDTO.getIsCurrencyRestriction()!=null && !requestDTO.getIsCurrencyRestriction().trim().isEmpty()){

			if(!("Y".equals(requestDTO.getIsCurrencyRestriction().trim()) || "N".equals(requestDTO.getIsCurrencyRestriction().trim()))){
				errors.add("currencyRestriction",new ActionMessage("error.invalid"));
			}
		}/*else{
			errors.add("currencyRestriction", new ActionMessage("error.string.mandatory"));
		}*/
		if(requestDTO.getReleasedAmount()!=null && !requestDTO.getReleasedAmount().trim().isEmpty())
		{
			if (!(errorCode =Validator.checkAmount(requestDTO.getReleasedAmount(), true,0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2,IGlobalConstant.DEFAULT_CURRENCY, Locale.getDefault()))
					.equals(Validator.ERROR_NONE)) {
				errors.add("releasedAmount", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2_STR));
		 }
			
		}else{
			errors.add("releasedAmount",new ActionMessage("error.mandatory"));
		}

		
		if(requestDTO.getNewLine()!=null && !requestDTO.getNewLine().trim().isEmpty()){

			if(!("Y".equals(requestDTO.getNewLine().trim()) || "N".equals(requestDTO.getNewLine().trim()))){
				errors.add("newLine",new ActionMessage("error.invalid"));
			}
		}
		
		
		if (requestDTO.getLiabBranch() != null && !requestDTO.getLiabBranch().trim().isEmpty()) {

			boolean flag = false;
			flag = limitDAO.checkLiabBrancProdCurrCount("liabBranch", requestDTO.getLiabBranch());
			if (!flag) {
				errors.add("liabBranch", new ActionMessage("error.invalid"));
			}

		}
		
		if (requestDTO.getSystemId() != null && !requestDTO.getSystemId().trim().isEmpty()) {

			boolean flag1 = false;
			flag1 = limitDAO.checkSystemCount(facilityRequestDTO.getCamId(), requestDTO.getSystemId()  );
			if (!flag1) {
				errors.add("systemId", new ActionMessage("error.invalid"));
			}

		}
		
		
		if(requestDTO.getMainLineCode() != null && !requestDTO.getMainLineCode().trim().isEmpty() ) {

			boolean flag1 = false;
			flag1 = limitDAO.checkMainLineCount(facilityRequestDTO.getCamId() );
			if (!flag1) {
				errors.add("mainLineCode", new ActionMessage("error.invalid"));
			}

		}
		
		
		if(null != requestDTO.getScmFlag() && !requestDTO.getScmFlag().trim().isEmpty()){

			if(!("Yes".equals(requestDTO.getScmFlag().trim()) || "No".equals(requestDTO.getScmFlag().trim()))){
				errors.add("scmFlag",new ActionMessage("error.invalid"));
			}
		}
		
		if(null != requestDTO.getRevolvingLine() && !requestDTO.getRevolvingLine().trim().isEmpty()){

			if(!("Yes".equals(requestDTO.getRevolvingLine().trim()) || "No".equals(requestDTO.getRevolvingLine().trim()))){
				errors.add("revolvingLine",new ActionMessage("error.invalid"));
			}
		}
		
		if(null != requestDTO.getSerialNo() && !requestDTO.getSerialNo().trim().isEmpty())
		{
			if(!(fcunsSystem.equals(facilityForm.getFacilitySystem()) || ubslimitSystem.equals(facilityForm.getFacilitySystem())))
			{
				if(ASSTValidator.isValidAlphaNumStringWithSpace(requestDTO.getSerialNo())) {
					errors.add("serialNo", new ActionMessage("error.string.invalidCharacter"));
				}

				else if (!(errorCode = Validator.checkNumber(requestDTO.getSerialNo(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("serialNo", new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.NUMBER, errorCode), "1",
							IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10_STR));
				}
			}
		}
		else
		{
			if(!(fcunsSystem.equals(facilityForm.getFacilitySystem()) || ubslimitSystem.equals(facilityForm.getFacilitySystem())))
			{
				errors.add("serialNo",new ActionMessage("error.mandatory"));
			}
			
		}
		

		requestDTO.setErrors(errors);
		return requestDTO;
	}

}
