/**
 * 
 */
package com.integrosys.cms.app.ws.facade;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.ws.aop.CLIMSWebServiceMethod;
import com.integrosys.cms.app.ws.common.CLIMSWebService;
import com.integrosys.cms.app.ws.common.SecuritySubTypeEnum;
import com.integrosys.cms.app.ws.common.ValidationUtility;
import com.integrosys.cms.app.ws.dto.SecurityRequestDTO;
import com.integrosys.cms.app.ws.dto.SecurityResponseDTO;
import com.integrosys.cms.app.ws.jax.common.CMSException;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.app.ws.security.FixedDepositeSecurityResponse;
import com.integrosys.cms.app.ws.security.GuaranteeSecurityResponse;
import com.integrosys.cms.app.ws.security.InsuranceSecurityResponse;
import com.integrosys.cms.app.ws.security.PDCSecurityResponse;
import com.integrosys.cms.app.ws.security.PlantMachinerySecurityResponse;
import com.integrosys.cms.app.ws.security.PropertySecurityResponse;
import com.integrosys.cms.app.ws.security.SecurityResponseInterface;
import com.integrosys.cms.app.ws.security.SpecificAssetSecurityResponse;
import com.integrosys.cms.app.ws.security.StockDetailSecurityResponse;
import com.integrosys.cms.batch.eod.EndOfDaySyncMastersServiceImpl;

/**
 * @author Ankit
 *
 */

public class SecurityFacade {
	
	private EndOfDaySyncMastersServiceImpl endOfDaySyncMastersServiceImpl;
	
	public EndOfDaySyncMastersServiceImpl getEndOfDaySyncMastersServiceImpl() {
		return endOfDaySyncMastersServiceImpl;
	}

	public void setEndOfDaySyncMastersServiceImpl(
			EndOfDaySyncMastersServiceImpl endOfDaySyncMastersServiceImpl) {
		this.endOfDaySyncMastersServiceImpl = endOfDaySyncMastersServiceImpl;
	}
	
	@CLIMSWebServiceMethod
	public SecurityResponseDTO  getSecurityDetails(SecurityRequestDTO securityRequestDTO) throws CMSValidationFault,CMSFault{
		 
		/*EndOfDaySyncMastersServiceImpl impl = new EndOfDaySyncMastersServiceImpl();
		System.out.println("SYNC PROCESS STARTED!! ");
		getEndOfDaySyncMastersServiceImpl().performEODSyncCpsToClims();
		System.out.println("SYNC PROCESS ENDED!! ");*/
		
		SecurityResponseDTO securityResponseDTO = new SecurityResponseDTO();
		ActionErrors errors = new ActionErrors();
		try {
			
			errors = validateSecurityWSDLParameters(securityRequestDTO);
			
			if(errors.size()>0){
				HashMap errorMap = new HashMap();
				if(errors!=null && errors.size()>0){
					errorMap.put("1", errors);
					ValidationUtility.handleError(errorMap, CLIMSWebService.SECURITYSEARCH);
				}
			}else{
				String sourceSecurityId = securityRequestDTO.getSourceSecurityId();
				sourceSecurityId = sourceSecurityId.trim();
				long collateralIDLong = Long.parseLong(sourceSecurityId);
		
				String securitySubType = securityRequestDTO.getSecuritySubType();
				
				OBTrxContext trxContext = new OBTrxContext(); 
				ICollateralTrxValue itrxValue = new OBCollateralTrxValue();
				ICollateralProxy collateralProxy = CollateralProxyFactory.getProxy();
				
				try{
					itrxValue = collateralProxy.getCollateralTrxValue(trxContext, collateralIDLong);
					ICollateral collateralInstance = itrxValue.getCollateral();
					String legalName = itrxValue.getLegalName();
					
					if(securitySubType!=null && !securitySubType.isEmpty()){
						
						String resMsg = null;
						SecurityResponseInterface interfaceObj = null;
						
						SecuritySubTypeEnum enumSecuritySubType = SecuritySubTypeEnum.valueOf(securitySubType);
						
						switch(enumSecuritySubType){
						
							//Cash - Fixed Deposit Security
							case CS202 :
								interfaceObj = new FixedDepositeSecurityResponse();
								resMsg =  interfaceObj.getResponseMessage(collateralInstance,sourceSecurityId,legalName);
								break;
							
							//Property Guarantee
							case PT701 :
								interfaceObj = new PropertySecurityResponse();
								resMsg =  interfaceObj.getResponseMessage(collateralInstance,sourceSecurityId);
								break;
								
							//Bank/Corporate/Government/Standby LC/Individual Guarantee
							case GT400:
							case GT402:
							case GT405:
							case GT406:
							case GT408:
								interfaceObj = new GuaranteeSecurityResponse();
								resMsg =  interfaceObj.getResponseMessage(collateralInstance,sourceSecurityId,securitySubType);
								break;
								
							//Specific Asset Security
							case AB109 :
								interfaceObj = new SpecificAssetSecurityResponse();
								resMsg =  interfaceObj.getResponseMessage(collateralInstance,sourceSecurityId);
								break;
								
							//Plant & Machinery Security
							case AB101 :
								interfaceObj = new PlantMachinerySecurityResponse();
								resMsg =  interfaceObj.getResponseMessage(collateralInstance,sourceSecurityId);
								break;
								
							//Insurance Security
							case IN501 :
								interfaceObj = new InsuranceSecurityResponse();
								resMsg =  interfaceObj.getResponseMessage(collateralInstance,sourceSecurityId);
								break;
							
							//PDC Security
							case AB108 :
								interfaceObj = new PDCSecurityResponse();
								resMsg =  interfaceObj.getResponseMessage(collateralInstance,sourceSecurityId);
								break;
								
							//Stock Details Security
							case AB100 :
								interfaceObj = new StockDetailSecurityResponse();
								resMsg =  interfaceObj.getResponseMessage(collateralInstance,sourceSecurityId);
								break;
							default:
		                     	break;
						}
						
						if(resMsg != null && !resMsg.isEmpty()){
							securityResponseDTO.setSecurityDetails(resMsg);
							securityResponseDTO.setResponseStatus("SECURITY_FETCHED_SUCCESSFULLY");
						}else{
							DefaultLogger.error(this, "Empty Response: "+resMsg);
							throw new CMSException("Server side error");
						}
					}
				}catch (CollateralException e) {
					throw e;
				}
			}
		}catch (CMSValidationFault e) {
			throw e; 
		}
		catch (Exception e) {
			DefaultLogger.error(this, "############# error during Security WSDL Processing ######## ", e);
			throw new CMSException(e.getMessage(),e); 
		}
		return securityResponseDTO;
//		return null;
	}

	
	private ActionErrors validateSecurityWSDLParameters(SecurityRequestDTO securityRequestDTO){
		
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		ICollateralDAO collateralDAOInstance = (ICollateralDAO)BeanHouse.get("collateralDao");
		
		//WSconsumer
		if(StringUtils.isBlank(securityRequestDTO.getWsConsumer())){
			errors.add("wsConsumerError",new ActionMessage("error.string.mandatory"));
		}else{
			if(securityRequestDTO.getWsConsumer().trim().length()>50){
				errors.add("wsConsumerError",new ActionMessage("error.field.wsconsumer.length.notexceed"));
			}
		}
		//Security ID
		if(StringUtils.isBlank(securityRequestDTO.getSourceSecurityId())){
			errors.add("securityID", new ActionMessage("error.string.mandatory"));
		}else {
			if(!(errorCode = Validator.checkNumber(securityRequestDTO.getSourceSecurityId().trim(), false, 0, 99999999999999999d))
					.equals(Validator.ERROR_NONE)) {
				errors.add("securityID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", "99999999999999999"));	
			}
		}
		
		//Security Sub-type
		if(StringUtils.isBlank(securityRequestDTO.getSecuritySubType())){
			errors.add("securitySubType", new ActionMessage("error.string.mandatory"));
		}else {
			/*if(!(errorCode = Validator.checkNumber(securityRequestDTO.getSecuritySubType(), false, 0, 99999999999999999d))
					.equals(Validator.ERROR_NONE)) {
				errors.add("securitySubType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", "99999999999999999"));	
			}*/
		}
		
		if(!StringUtils.isBlank(securityRequestDTO.getSourceSecurityId())
				&& !StringUtils.isBlank(securityRequestDTO.getSecuritySubType())){
			try{
				ICollateralSubType collateralSubType = collateralDAOInstance.getCollateralSubTypeByCollateralID(new Long(securityRequestDTO.getSourceSecurityId().trim()));
				
				MasterAccessUtility masterAccessUtilityInstance = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
				if(securityRequestDTO.getSecuritySubType()!=null && !securityRequestDTO.getSecuritySubType().trim().isEmpty()){
					//Category Code = 54 -- Security Sub Type
					Object masterObj = masterAccessUtilityInstance.getObjectByEntityNameAndCPSId("entryCode", securityRequestDTO.getSecuritySubType().trim(), "securitySubType", errors,"54");
					if(!(masterObj instanceof ActionErrors)){
						securityRequestDTO.setSecuritySubType(((ICommonCodeEntry)masterObj).getEntryCode());
						if(collateralSubType!=null){
							if(!securityRequestDTO.getSecuritySubType().trim().equalsIgnoreCase(collateralSubType.getSubTypeCode())){
								errors.add("securityIDAndSecuritySubType",new ActionMessage("error.field.security.norecordfound"));
							}
						}
					}
				}
			}catch (SearchDAOException e) {
				e.printStackTrace();
				DefaultLogger.error(this, "############# error in method validateSecurityWSDLParameters() ######## ", e);
				errors.add("securityID",new ActionMessage("error.invalid.field.value"));
			}
		}
		return errors;
	}
}
