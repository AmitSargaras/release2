package com.integrosys.cms.app.ws.dto;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import com.aurionpro.clims.rest.dto.SecurityDetailRestRequestDTO;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.LmtColSearchCriteria;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.cms.ui.manualinput.security.SecDetailForm;

@Service
public class SecurityDetailsDTOMapper {


	public SecDetailForm  getFormFromDTO( SecurityDetailRequestDTO dto) {
		SimpleDateFormat date = new SimpleDateFormat("dd/MMM/yyyy");
		date.setLenient(false);
		SecDetailForm secDetailForm = new SecDetailForm();	
		
		MISecurityUIHelper helper = new MISecurityUIHelper();
		if(dto.getSecurityCountry()!=null && !dto.getSecurityCountry().trim().isEmpty()){
			secDetailForm.setSecBookingCountry(dto.getSecurityCountry().trim());
		}else{
			secDetailForm.setSecBookingCountry("");
		}
		
		if(dto.getCollateralCodeName()!=null && !dto.getCollateralCodeName().trim().isEmpty()){
			secDetailForm.setCollateralCode(dto.getCollateralCodeName().trim());
		}else{
			secDetailForm.setCollateralCode("");
		}
		

		/*if (dto.getSecuritySubType() != null) {
			//ICollateralSubType subtp = dto.getCollateralSubType();
			CollateralDAO collateralDAO = (CollateralDAO)BeanHouse.get("collateralDAO");
			ICollateralSubType subType= collateralDAO.getCollateralSubTypesBySubTypeCode(dto.getSecuritySubType());
			secDetailForm.setSecSubtype(subType.getSubTypeCode());
			secDetailForm.setSecSubtypeDesc(subType.getSubTypeDesc());
			secDetailForm.setSecType(subType.get);
			secDetailForm.setSecTypeDesc("");
		}*/
		secDetailForm.setMonitorProcess("N");
		if(dto.getSecurityCurrency()!=null && !dto.getSecurityCurrency().trim().isEmpty()){
			secDetailForm.setSecCurrency(dto.getSecurityCurrency().trim());
		}else{
			secDetailForm.setSecCurrency("");
		}
		
		if(dto.getSecurityPriority()!=null && !dto.getSecurityPriority().trim().isEmpty()){
			secDetailForm.setSecPriority(dto.getSecurityPriority().trim());
		}else{
			secDetailForm.setSecPriority("");
		}
		
		secDetailForm.setLmtSecurityCoverage("100");
		
		if(dto.getCpsSecurityId()!=null && !dto.getCpsSecurityId().trim().isEmpty()){
			secDetailForm.setCpsSecurityId(dto.getCpsSecurityId().trim());
		}else{
			secDetailForm.setCpsSecurityId("");
		}

		if(dto.getSecuritySubType()!=null && !dto.getSecuritySubType().trim().isEmpty()){
			secDetailForm.setSecSubtype(dto.getSecuritySubType().trim());
		}else{
			secDetailForm.setSecSubtype("");
		}
		
		if(dto.getPrimarySecurityAddress()!=null && !dto.getPrimarySecurityAddress().trim().isEmpty()){
			secDetailForm.setPrimarySecurityAddress(dto.getPrimarySecurityAddress().trim());
		}else{
			secDetailForm.setPrimarySecurityAddress("");
		}
		if(dto.getSecondarySecurityAddress()!=null && !dto.getSecondarySecurityAddress().trim().isEmpty()){
			secDetailForm.setSecondarySecurityAddress(dto.getSecondarySecurityAddress().trim());
		}else{
			secDetailForm.setSecondarySecurityAddress("");
		}
		if(dto.getSecurityValueAsPerCAM()!=null && !dto.getSecurityValueAsPerCAM().trim().isEmpty()){
			secDetailForm.setSecurityValueAsPerCAM(dto.getSecurityValueAsPerCAM());
		}else{
			secDetailForm.setSecurityValueAsPerCAM(null);
		}
		
		if(dto.getSecurityMargin()!=null && !dto.getSecurityMargin().trim().isEmpty()){
			secDetailForm.setSecurityMargin(dto.getSecurityMargin().trim());
		}else{
			secDetailForm.setSecurityMargin("");
		}
		if(dto.getChargePriority()!=null && !dto.getChargePriority().trim().isEmpty()){
			secDetailForm.setChargePriority(dto.getChargePriority().trim());
		}else{
			secDetailForm.setChargePriority("");
		}
		
		return secDetailForm;
	}
	
	
	public SecurityDetailRequestDTO getRequestDTOWithActualValues(SecurityDetailRequestDTO requestDTO, String event) {
		SimpleDateFormat date = new SimpleDateFormat("dd/MMM/yyyy");
		date.setLenient(false);

		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();

		requestDTO.setSecurityCountry("IN");
		requestDTO.setSecurityCurrency("INR");
		
		if(requestDTO.getCpsSecurityId()!=null && !requestDTO.getCpsSecurityId().trim().isEmpty()){
		//	Object obj = masterObj.getObjectByEntityNameAndCPSId("actualCountry", requestDTO.getSecurityCountry(),"securityCountry",errors);
			if(event.equals("WS_FAC_CREATE")){
			ILimitDAO dao = (ILimitDAO)BeanHouse.get("limitJdbcDao");
			boolean isCpsSecIdUnique = dao.isCpsSecurityIdUnique(requestDTO.getCpsSecurityId().trim());
			
			if(isCpsSecIdUnique){
				errors.add("cpsSecurityId",new ActionMessage("error.cpsSecurityId.exists"));
			}
			}
		}else{
			errors.add("cpsSecurityId",new ActionMessage("error.cpsSecurityId.mandatory"));
		}
		
		if(requestDTO.getCollateralCodeName()!=null && !requestDTO.getCollateralCodeName().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("actualCollateralNewMaster", requestDTO.getCollateralCodeName().trim(),"collateralCodeName",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setCollateralCodeName(((ICollateralNewMaster)obj).getNewCollateralCode());
			}
		}else{
			errors.add("collateralCodeName",new ActionMessage("error.collateralCodeName.mandatory"));
		}
		
		
		if(requestDTO.getSecuritySubType()!=null && !requestDTO.getSecuritySubType().trim().isEmpty()){
			//Category Code = 54 -- Security Sub Type
			Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getSecuritySubType().trim(),"securitySubType",errors,"54");
			if(!(obj instanceof ActionErrors)){
				requestDTO.setSecuritySubType(((ICommonCodeEntry)obj).getEntryCode());
			}
		}else{
			errors.add("securitySecuritySubType",new ActionMessage("error.securitySubType.mandatory"));
		}
		
		 if(requestDTO.getSecurityPriority()!=null && !requestDTO.getSecurityPriority().trim().isEmpty()){
				if(!("Y".equals(requestDTO.getSecurityPriority().trim()) || "N".equals(requestDTO.getSecurityPriority().trim()))){
					errors.add("securityPriority",new ActionMessage("error.securityPriority.invalid"));
				}
			}else{
				errors.add("securityPriority",new ActionMessage("error.securityPriority.mandatory"));
			}
		 
			if(requestDTO.getPrimarySecurityAddress()!=null && !requestDTO.getPrimarySecurityAddress().trim().isEmpty()){
				if(requestDTO.getPrimarySecurityAddress().length() > 250 )
					errors.add("primarySecurityAddress",new ActionMessage("length is exceeded"));
				else
					requestDTO.setPrimarySecurityAddress(requestDTO.getPrimarySecurityAddress().trim());
			}else{
				requestDTO.setPrimarySecurityAddress("");
			}
			if(requestDTO.getSecondarySecurityAddress()!=null && !requestDTO.getSecondarySecurityAddress().trim().isEmpty()){
				if(requestDTO.getSecondarySecurityAddress().length() > 250 )
					errors.add("secondarySecurityAddress",new ActionMessage("length is exceeded"));
				else
					requestDTO.setSecondarySecurityAddress(requestDTO.getSecondarySecurityAddress().trim());
			}else{
				requestDTO.setSecondarySecurityAddress("");
			}
			
			if(requestDTO.getSecurityValueAsPerCAM()!=null && !requestDTO.getSecurityValueAsPerCAM().trim().isEmpty()){
				try {
					date.parse(requestDTO.getSecurityValueAsPerCAM().trim());
					requestDTO.setSecurityValueAsPerCAM(requestDTO.getSecurityValueAsPerCAM().trim());
				} catch (ParseException e) {
					errors.add("securityValueAsPerCAM",new ActionMessage("error.securityValueAsPerCAM.invalid.format"));
				}
			}else{
				requestDTO.setSecurityValueAsPerCAM(null);
			}
			
			if(requestDTO.getSecurityMargin()!=null && !requestDTO.getSecurityMargin().trim().isEmpty()){
				 if(ASSTValidator.isNumeric(requestDTO.getSecurityMargin().trim())) {
						requestDTO.setSecurityMargin(requestDTO.getSecurityMargin().trim());
				 }else {
					 errors.add("securityMargin", new ActionMessage("only numeric is allowed"));
				 }
			}else{
				requestDTO.setSecurityMargin("");
			}
			
			 if(requestDTO.getChargePriority()!=null && !requestDTO.getChargePriority().trim().isEmpty()){
				  try {
					Object obj = masterObj.getMasterData("entryCode", Long.parseLong(requestDTO.getChargePriority().trim()));
					if(obj!=null){
						ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
						if("CHARGE_PRIORITY".equals(codeEntry.getCategoryCode())){
							requestDTO.setChargePriority((codeEntry).getEntryCode());
						}else{
							errors.add("chargePriority",new ActionMessage("error.chargePriority.invalid"));
						}
					}else{
						errors.add("chargePriority",new ActionMessage("error.chargePriority.invalid"));
					}
				  }catch(Exception e) {
						errors.add("chargePriority",new ActionMessage("error.chargePriority.invalid"));
				  }
			 }else{
				 requestDTO.setChargePriority("");
			 }		 
	 	requestDTO.setErrors(errors);
		return requestDTO;
	}


	public SecDetailForm getNewFieldsFormFromDTO(SecurityNewFieldsDetailRequestDTO dto) {
		SimpleDateFormat date = new SimpleDateFormat("dd/MMM/yyyy");
		date.setLenient(false);

		SecDetailForm secDetailForm = new SecDetailForm();	

		if(dto.getCpsSecurityId()!=null && !dto.getCpsSecurityId().trim().isEmpty()){
			secDetailForm.setCpsSecurityId(dto.getCpsSecurityId().trim());
		}else{
			secDetailForm.setCpsSecurityId("");
		}
		
		if(dto.getPrimarySecurityAddress()!=null && !dto.getPrimarySecurityAddress().trim().isEmpty()){
			secDetailForm.setPrimarySecurityAddress(dto.getPrimarySecurityAddress().trim());
		}else{
			secDetailForm.setPrimarySecurityAddress("");
		}
		if(dto.getSecondarySecurityAddress()!=null && !dto.getSecondarySecurityAddress().trim().isEmpty()){
			secDetailForm.setSecondarySecurityAddress(dto.getSecondarySecurityAddress().trim());
		}else{
			secDetailForm.setSecondarySecurityAddress("");
		}
		if(dto.getSecurityValueAsPerCAM()!=null && !dto.getSecurityValueAsPerCAM().trim().isEmpty()){
			secDetailForm.setSecurityValueAsPerCAM(dto.getSecurityValueAsPerCAM());
		}else{
			secDetailForm.setSecurityValueAsPerCAM(null);
		}
		
		if(dto.getSecurityMargin()!=null && !dto.getSecurityMargin().trim().isEmpty()){
			secDetailForm.setSecurityMargin(dto.getSecurityMargin().trim());
		}else{
			secDetailForm.setSecurityMargin("");
		}
		if(dto.getChargePriority()!=null && !dto.getChargePriority().trim().isEmpty()){
			secDetailForm.setChargePriority(dto.getChargePriority().trim());
		}else{
			secDetailForm.setChargePriority("");
		}
		
		return secDetailForm;

	}


	public SecurityNewFieldsDetailRequestDTO getNewFieldsRequestDTOWithActualValues(
			SecurityNewFieldsDetailRequestDTO securityDTO, String event) {
		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();
		SimpleDateFormat date = new SimpleDateFormat("dd/MMM/yyyy");
		date.setLenient(false);

		if(securityDTO.getCpsSecurityId()!=null && !securityDTO.getCpsSecurityId().trim().isEmpty()){
			if(event.equals("WS_FAC_CREATE")){
			ILimitDAO dao = (ILimitDAO)BeanHouse.get("limitJdbcDao");
			boolean isCpsSecIdUnique = dao.isCpsSecurityIdUnique(securityDTO.getCpsSecurityId().trim());
			
			if(isCpsSecIdUnique){
				errors.add("cpsSecurityId",new ActionMessage("error.cpsSecurityId.exists"));
			}
			}
		}else{
			errors.add("cpsSecurityId",new ActionMessage("error.cpsSecurityId.mandatory"));
		}
		
		if(securityDTO.getPrimarySecurityAddress()!=null && !securityDTO.getPrimarySecurityAddress().trim().isEmpty()){
			securityDTO.setPrimarySecurityAddress(securityDTO.getPrimarySecurityAddress().trim());
		}else{
			securityDTO.setPrimarySecurityAddress("");
		}
		if(securityDTO.getSecondarySecurityAddress()!=null && !securityDTO.getSecondarySecurityAddress().trim().isEmpty()){
			securityDTO.setSecondarySecurityAddress(securityDTO.getSecondarySecurityAddress().trim());
		}else{
			securityDTO.setSecondarySecurityAddress("");
		}
		
		if(securityDTO.getSecurityValueAsPerCAM()!=null && !securityDTO.getSecurityValueAsPerCAM().trim().isEmpty()){
			try {
				date.parse(securityDTO.getSecurityValueAsPerCAM().trim());
				securityDTO.setSecurityValueAsPerCAM(securityDTO.getSecurityValueAsPerCAM().trim());
			} catch (ParseException e) {
				errors.add("securityValueAsPerCAM",new ActionMessage("error.securityValueAsPerCAM.invalid.format"));
			}
		}else{
			securityDTO.setSecurityValueAsPerCAM(null);
		}
		
		if(securityDTO.getSecurityMargin()!=null && !securityDTO.getSecurityMargin().trim().isEmpty()){
			 if(ASSTValidator.isNumeric(securityDTO.getSecurityMargin().trim())) {
					securityDTO.setSecurityMargin(securityDTO.getSecurityMargin().trim());
			 }else {
				 errors.add("securityMargin", new ActionMessage("only numeric is allowed"));
			 }
		}else{
			securityDTO.setSecurityMargin("");
		}
		
		if(securityDTO.getChargePriority()!=null && !securityDTO.getChargePriority().trim().isEmpty()){
			  try {
				Object obj = masterObj.getMasterData("entryCode", Long.parseLong(securityDTO.getChargePriority().trim()));
				if(obj!=null){
					ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
					if("CHARGE_PRIORITY".equals(codeEntry.getCategoryCode())){
						securityDTO.setChargePriority((codeEntry).getEntryCode());
					}else{
						errors.add("chargePriority",new ActionMessage("error.chargePriority.invalid"));
					}
				}else{
					errors.add("chargePriority",new ActionMessage("error.chargePriority.invalid"));
				}
			  }catch(Exception e) {
					errors.add("chargePriority",new ActionMessage("error.chargePriority.invalid"));
			  }
		}else{
			 securityDTO.setChargePriority("");
		}

		securityDTO.setErrors(errors);
		return securityDTO;
	}

	public SecurityDetailRestRequestDTO getRestRequestDTOWithActualValues(SecurityDetailRestRequestDTO requestDTO, String event, ILimitProfile profile) {
		String errorCode = null;
		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();
		
		ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
		
		if ((null == requestDTO.getUniqueReqId() || requestDTO.getUniqueReqId().trim().isEmpty())
				&& ((null == requestDTO.getPartyId() || requestDTO.getPartyId().trim().isEmpty())
						|| (null == requestDTO.getExistingSecurityId() || requestDTO.getExistingSecurityId().trim().isEmpty()))) {
			errors.add("uniqueReqId",new ActionMessage("error.mandatory"));
		}else {
		
		if(null != requestDTO.getUniqueReqId() && !requestDTO.getUniqueReqId().trim().isEmpty()) {
			if(!ASSTValidator.isNumeric(requestDTO.getUniqueReqId())) {
				errors.add("uniqueReqIdNumErr",new ActionMessage("error.number.field.value"));
			}
			
		}
		
		if (null != requestDTO.getUniqueReqId() && !requestDTO.getUniqueReqId().trim().isEmpty()
				&& ASSTValidator.isNumeric(requestDTO.getUniqueReqId())) {
			if(requestDTO.getUniqueReqId().trim().length() > 22) {
				errors.add("uniqueReqIdLenErr",new ActionMessage("error.string.length.exceeded"));
			}
		}
		
		if(null != requestDTO.getSecurityType() && !requestDTO.getSecurityType().trim().isEmpty() )
		{
			
			Object objSecType = masterObj.getObjectByEntityName("entryCode", requestDTO.getSecurityType().trim(),"securityType",errors,"31");
			if(!(objSecType instanceof ActionErrors)){
				requestDTO.setSecurityType(((ICommonCodeEntry)objSecType).getEntryCode());
				if((requestDTO.getSecuritySubType()!=null && !requestDTO.getSecuritySubType().trim().isEmpty() )) {

					//Category Code = 54 -- Security Sub Type
					Object objSubType = masterObj.getObjectByEntityNameColsubType("entryCode", requestDTO.getSecuritySubType().trim(),"securitySubType",errors,"54", requestDTO.getSecurityType().trim());
					if(!(objSubType instanceof ActionErrors)){
						requestDTO.setSecuritySubType(((ICommonCodeEntry)objSubType).getEntryCode());
						if((requestDTO.getPartyId()!=null && !requestDTO.getPartyId().trim().isEmpty()) && 
								(requestDTO.getExistingSecurityId()!=null && !requestDTO.getExistingSecurityId().trim().isEmpty() ))
						{
							LmtColSearchCriteria crit = new LmtColSearchCriteria();
							crit.setLimitProfId(profile.getLimitProfileID());
				            crit.setSciSecId(requestDTO.getExistingSecurityId());
				            crit.setCustName(requestDTO.getPartyId());
				            crit.setSecSubtype(requestDTO.getSecuritySubType());
				            ICollateralDAO dao = CollateralDAOFactory.getDAO();
							OBCollateral collateral = new OBCollateral();
							collateral= dao.searchCollateralByIdSubtypeRest(crit);
							if(null == collateral) {
								errors.add("existingSecurityId", new ActionMessage("error.existingSecurity2"));
							}
						}
						else if((null != requestDTO.getPartyId() && !requestDTO.getPartyId().trim().isEmpty() && 
									(null == requestDTO.getExistingSecurityId() || requestDTO.getExistingSecurityId().trim().isEmpty())) ||
									((null == requestDTO.getPartyId() || requestDTO.getPartyId().trim().isEmpty()) && 
									(null != requestDTO.getExistingSecurityId() && !requestDTO.getExistingSecurityId().trim().isEmpty())) )
							{
								errors.add("existingSecurityId", new ActionMessage("error.existingSecurity1"));
							}
							
						else {
							
							if(null!= requestDTO.getSecurityCountry() && !requestDTO.getSecurityCountry().trim().isEmpty()) {

								Object obj = masterObj.getObjectforMasterRelatedCode("actualCountry", requestDTO.getSecurityCountry().trim(),"securityCountry",errors);
								if(!(obj instanceof ActionErrors)){
									requestDTO.setSecurityCountry(((ICountry)obj).getCountryCode());
								}else {
									errors.add("securityCountry", new ActionMessage("error.invalid"));
								}

							}else {
								requestDTO.setSecurityCountry("IN");
							}
						
							if (null != requestDTO.getSecurityBranch() && !requestDTO.getSecurityBranch().trim().isEmpty()) {

								boolean flag1 = false;
								flag1 = collateralDAO.checkSecBranchCount(requestDTO.getSecurityBranch(), requestDTO.getSecurityCountry());
								if (!flag1) {
									errors.add("securityBranch", new ActionMessage("error.invalid"));
								}

							}

							if(null!= requestDTO.getSecurityCurrency() && !requestDTO.getSecurityCurrency().trim().isEmpty()) {

								Object obj = masterObj.getObjectforMasterRelatedCode("actualForexFeedEntry", requestDTO.getSecurityCurrency().trim(),"securityCurrency",errors);
								if(!(obj instanceof ActionErrors)){
									requestDTO.setSecurityCurrency(((IForexFeedEntry)obj).getCurrencyIsoCode());
								}else {
									errors.add("securityCurrency", new ActionMessage("error.invalid"));
								}

							}else {
								requestDTO.setSecurityCurrency("INR");
							}

							

							if(requestDTO.getCollateralCodeName()!=null && !requestDTO.getCollateralCodeName().trim().isEmpty()){
								Object obj = masterObj.getObjectByEntityNameColl("actualCollateralNewMaster", requestDTO.getCollateralCodeName().trim(),"collateralCodeName",errors,requestDTO.getSecurityType().trim(),requestDTO.getSecuritySubType().trim());
								if(!(obj instanceof ActionErrors)){
									requestDTO.setCollateralCodeName(((ICollateralNewMaster)obj).getNewCollateralCode());
								}
							}else{
								errors.add("collateralCodeName",new ActionMessage("error.collateralCodeName.mandatory"));
							}

							if(requestDTO.getSecurityPriority()!=null && !requestDTO.getSecurityPriority().trim().isEmpty()){
								if(!("Y".equalsIgnoreCase(requestDTO.getSecurityPriority().trim()) || "N".equalsIgnoreCase(requestDTO.getSecurityPriority().trim()))){
									errors.add("securityPriority",new ActionMessage("error.securityPriority.invalid"));
								}
							}else{
								errors.add("securityPriority",new ActionMessage("error.securityPriority.mandatory"));
							}
							
							if(requestDTO.getMonitorProcessColl()!=null && !requestDTO.getMonitorProcessColl().trim().isEmpty()){
								if(!("Y".equalsIgnoreCase(requestDTO.getMonitorProcessColl().trim()) || "N".equalsIgnoreCase(requestDTO.getMonitorProcessColl().trim()))){
									errors.add("monitorProcessColl",new ActionMessage("error.monitorProcessColl.invalid"));
								}
							}else{
								errors.add("monitorProcessColl",new ActionMessage("error.monitorProcessColl.mandatory"));
							}

							if(requestDTO.getMonitorProcessColl()!=null && !requestDTO.getMonitorProcessColl().trim().isEmpty()){
								if("Y".equalsIgnoreCase(requestDTO.getMonitorProcessColl().trim()) && null!=requestDTO.getMonitorFrequencyColl() && !requestDTO.getMonitorFrequencyColl().trim().isEmpty()){
									Object obj = masterObj.getObjectByEntityName("entryCode", requestDTO.getMonitorFrequencyColl().trim(),"monitorFrequency",errors,"FREQUENCY");
									if(!(obj instanceof ActionErrors)){
										requestDTO.setMonitorFrequencyColl(((ICommonCodeEntry)obj).getEntryCode());
									}
									else
									{
										errors.add("monitorFrequency",new ActionMessage("error.invalid.monitorFrequency")); 
									}
								}
							}else{
								errors.add("monitorProcessColl",new ActionMessage("error.monitorProcessColl.mandatory"));
							}

						
							if(requestDTO.getSecurityCoverage()!=null && !requestDTO.getSecurityCoverage().trim().isEmpty()){
								if (!(errorCode = Validator.checkNumber(requestDTO.getSecurityCoverage(), false,0 ,IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_2_2 ,3, Locale.getDefault()))
										.equals(Validator.ERROR_NONE)) {

									errors.add("SecurityCoverage", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100"));
								}
							}else{
								errors.add("SecurityCoverage",new ActionMessage("error.mandatory"));
							}
					}
					
						/*if((requestDTO.getPartyId()!=null && !requestDTO.getPartyId().trim().isEmpty() ) ||
								requestDTO.getExistingSecurityId()!=null && !requestDTO.getExistingSecurityId().trim().isEmpty()){
							if(!("PT".equalsIgnoreCase(requestDTO.getSecurityType().trim()))){
								errors.add("securityType",new ActionMessage("error.securityType.notValid"));
							}	 
						}*/
					}else {
						errors.add("securitySubType",new ActionMessage("error.invalid"));
					}
					
				
					
				}else{
						errors.add("securitySubType",new ActionMessage("error.securitySubType.mandatory"));
					}
			}else {
				errors.add("securityType",new ActionMessage("error.invalid"));
			}
			
			}
		else{
			errors.add("securityType",new ActionMessage("error.mandatory"));
		}
		
	}
	 	requestDTO.setErrors(errors);
		return requestDTO;
	}

	public SecDetailForm  getFormFromRestDTO( SecurityDetailRestRequestDTO dto) {

		SecDetailForm secDetailForm = new SecDetailForm();	
		
		MISecurityUIHelper helper = new MISecurityUIHelper();
		if(dto.getSecurityCountry()!=null && !dto.getSecurityCountry().trim().isEmpty()){
			secDetailForm.setSecBookingCountry(dto.getSecurityCountry().trim());
		}else{
			secDetailForm.setSecBookingCountry("");
		}
		
		if(dto.getCollateralCodeName()!=null && !dto.getCollateralCodeName().trim().isEmpty()){
			secDetailForm.setCollateralCode(dto.getCollateralCodeName().trim());
		}else{
			secDetailForm.setCollateralCode("");
		}
		

		/*if (dto.getSecuritySubType() != null) {
			//ICollateralSubType subtp = dto.getCollateralSubType();
			CollateralDAO collateralDAO = (CollateralDAO)BeanHouse.get("collateralDAO");
			ICollateralSubType subType= collateralDAO.getCollateralSubTypesBySubTypeCode(dto.getSecuritySubType());
			secDetailForm.setSecSubtype(subType.getSubTypeCode());
			secDetailForm.setSecSubtypeDesc(subType.getSubTypeDesc());
			secDetailForm.setSecType(subType.get);
			secDetailForm.setSecTypeDesc("");
		}*/
		
		if(dto.getSecurityCurrency()!=null && !dto.getSecurityCurrency().trim().isEmpty()){
			secDetailForm.setSecCurrency(dto.getSecurityCurrency().trim());
		}else{
			secDetailForm.setSecCurrency("");
		}
		
		if(dto.getMonitorProcessColl()!=null && !dto.getMonitorProcessColl().trim().isEmpty()){
			secDetailForm.setMonitorProcess(dto.getMonitorProcessColl().trim());
		}else{
			secDetailForm.setMonitorProcess("N");
		}
		
		if(dto.getMonitorFrequencyColl()!=null && !dto.getMonitorFrequencyColl().trim().isEmpty()){
			secDetailForm.setMonitorFrequency(dto.getMonitorFrequencyColl().trim());
		}else{
			secDetailForm.setMonitorFrequency("");
		}
		
		if(dto.getSecurityPriority()!=null && !dto.getSecurityPriority().trim().isEmpty()){
			secDetailForm.setSecPriority(dto.getSecurityPriority().trim());
		}else{
			secDetailForm.setSecPriority("");
		}
		
		if(dto.getSecurityCoverage()!=null && !dto.getSecurityCoverage().trim().isEmpty()){
			secDetailForm.setLmtSecurityCoverage(dto.getSecurityCoverage().trim());
			System.out.print("====SecurityDetailsDTOMapper==592=======Security Coverage : "+secDetailForm.getLmtSecurityCoverage());
		}else{
			secDetailForm.setLmtSecurityCoverage("100");
		}
		//secDetailForm.setLmtSecurityCoverage("100");
		/*
		if(dto.getCpsSecurityId()!=null && !dto.getCpsSecurityId().trim().isEmpty()){
			secDetailForm.setCpsSecurityId(dto.getCpsSecurityId().trim());
		}else{
			secDetailForm.setCpsSecurityId("");
		}*/

		if(dto.getUniqueReqId()!=null && !dto.getUniqueReqId().trim().isEmpty()){
			secDetailForm.setUniqueReqId(dto.getUniqueReqId().trim());
		}else{
			secDetailForm.setUniqueReqId("");
		}

		if(dto.getSecuritySubType()!=null && !dto.getSecuritySubType().trim().isEmpty()){
			secDetailForm.setSecSubtype(dto.getSecuritySubType().trim());
		}else{
			secDetailForm.setSecSubtype("");
		}
		if(dto.getSecurityType()!=null && !dto.getSecurityType().trim().isEmpty()){
			secDetailForm.setSecType(dto.getSecurityType().trim());
		}else{
			secDetailForm.setSecType("");
		}
		
		if(dto.getSecurityRefNote()!=null && !dto.getSecurityRefNote().trim().isEmpty()){
			secDetailForm.setSecReferenceNote(dto.getSecurityRefNote().trim());
		}else{
			secDetailForm.setSecReferenceNote("");
		}
		
		return secDetailForm;
	}
}
