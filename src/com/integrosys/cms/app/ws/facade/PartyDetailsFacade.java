/**
 * 
 */
package com.integrosys.cms.app.ws.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.springframework.beans.factory.annotation.Autowired;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.IBankingMethod;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.IIfscMethod;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBIfscMethod;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.manualinput.party.IIfscCodeDao;
import com.integrosys.cms.app.partygroup.bus.IPartyGroupDao;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.ws.aop.CLIMSWebServiceMethod;
import com.integrosys.cms.app.ws.common.CLIMSWebService;
import com.integrosys.cms.app.ws.common.ValidationUtility;
import com.integrosys.cms.app.ws.dto.BankingMethodRequestDTO;
import com.integrosys.cms.app.ws.dto.PartyDetailsDTOMapper;
import com.integrosys.cms.app.ws.dto.PartyDetailsRequestDTO;
import com.integrosys.cms.app.ws.dto.PartyDetailsResponseDTO;
import com.integrosys.cms.app.ws.dto.PartyIFSCBankingMethodDetailsRequestDTO;
import com.integrosys.cms.app.ws.jax.common.CMSException;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.ecbf.counterparty.ClimesToECBFHelper;

import com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerInfoForm;
import com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerValidator;
import com.integrosys.cms.ui.manualinput.customer.bankingmethod.IBankingMethodDAO;

/**
 * @author Ankit
 *
 */

public class PartyDetailsFacade{
	
	@Autowired
	private PartyDetailsDTOMapper partyDetailsDTOMapper;
	
	public void setPartyDetailsDTOMapper(PartyDetailsDTOMapper partyDetailsDTOMapper) {
		this.partyDetailsDTOMapper = partyDetailsDTOMapper;
	}

	@CLIMSWebServiceMethod
	public PartyDetailsResponseDTO  savePartyDetails(PartyDetailsRequestDTO partyDetRequestDTO) throws CMSValidationFault,CMSFault{

		PartyDetailsResponseDTO partyDetResponseDTO = new PartyDetailsResponseDTO();
		try {
			String addOrUpdate = "ADD";
			System.out.println("PartyDetailsResponseDTO  savePartyDetails => addOrUpdate=>"+addOrUpdate);
			PartyDetailsRequestDTO partyDetailsRequestDTOInstance = partyDetailsDTOMapper.getRequestDTOWithActualValues(partyDetRequestDTO,addOrUpdate);
			ActionErrors cpsIdErrors = partyDetailsRequestDTOInstance.getErrors();
			
			HashMap map = new HashMap();
			if(cpsIdErrors.size()>0){
				map.put("1", cpsIdErrors);
				ValidationUtility.handleError(map, CLIMSWebService.PARTY);
			}
			
			//ValidationUtility.handleError(cpsIdErrors, CLIMSWebService.PARTY);
			
			ManualInputCustomerInfoForm form = partyDetailsDTOMapper.getFormFromDTO(partyDetailsRequestDTOInstance,addOrUpdate);
			form.setEvent(partyDetailsRequestDTOInstance.getEvent());
			
			ActionErrors errorList = ManualInputCustomerValidator.validateInput(form, Locale.getDefault());
			
			map = new HashMap();
			if(errorList.size()>0){
				map.put("1", errorList);
				ValidationUtility.handleError(map, CLIMSWebService.PARTY);
			}
			
			//ValidationUtility.handleError(errorList, CLIMSWebService.PARTY);
			
			ICustomerProxy proxy = CustomerProxyFactory.getProxy();
			ICMSCustomerTrxValue trxValue = new OBCMSCustomerTrxValue();
			ICMSCustomerTrxValue trxResult = new OBCMSCustomerTrxValue();
			OBTrxContext trxContext = new OBTrxContext();
			trxValue.setTrxContext(trxContext);
			
			ICMSCustomer newOBCMSCustomer = new OBCMSCustomer();
			
			if(partyDetailsRequestDTOInstance.getEvent()!=null){
				
				DefaultLogger.info(this,"partyDetailsRequestDTOInstance.getEvent():::"+partyDetailsRequestDTOInstance.getEvent());
				
				if("WS_create_customer".equalsIgnoreCase(partyDetailsRequestDTOInstance.getEvent())){
					
					newOBCMSCustomer = partyDetailsDTOMapper.getActualFromDTO(partyDetailsRequestDTOInstance,null,addOrUpdate);
					
					//IFSC CAM CR code
					ICMSLegalEntity cmsLegalEntity =newOBCMSCustomer.getCMSLegalEntity();
					IBankingMethod[] bankingMthdArray =cmsLegalEntity.getBankList();
					if(null!=bankingMthdArray) {
						for(int i=0;i<bankingMthdArray.length;i++) {
							if(null!=bankingMthdArray[i]) {
								bankingMthdArray[i].setStatus("ACTIVE");
							}
						}
						cmsLegalEntity.setBankList(bankingMthdArray);
						newOBCMSCustomer.setCMSLegalEntity(cmsLegalEntity);
					}
					//end IFSC CAM CR code
					
					DefaultLogger.debug(this, "Updating Sanctioned Amount Flag using EJB from savePartyDetails for partyId :"+newOBCMSCustomer.getCifId() );
					newOBCMSCustomer.setSanctionedAmtUpdatedFlag(ICMSConstant.YES);
					
					trxValue.setStagingCustomer(newOBCMSCustomer);
					trxValue.setLegalName(newOBCMSCustomer.getCMSLegalEntity().getLegalName());
					trxValue.setCustomerName(newOBCMSCustomer.getCustomerName());
					
					IPartyGroupDao partyDao = (IPartyGroupDao)BeanHouse.get("partyGroupDao");
					newOBCMSCustomer.setCifId(partyDao.getCustomerIdCode());
					newOBCMSCustomer.getCMSLegalEntity().setLEReference(newOBCMSCustomer.getCifId());
		
					ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
					String printLogger = bundle1.getString("print.logger.enable");
					if(null!= printLogger && printLogger.equalsIgnoreCase("Y")){
						System.out.println("newOBCMSCustomer.getTotalFundedLimit():::"+newOBCMSCustomer.getTotalFundedLimit());
					}
					
					trxResult = proxy.createCustomerWithApprovalThroughWsdl(trxContext,trxValue);
					
					// IFSC CAM CR code
					DefaultLogger.debug(this, "Start get bank details using IFSC_CAM ");
					try {
						if (partyDetRequestDTO.getIfscBankingMethodDetailList() != null
								&& !partyDetRequestDTO.getIfscBankingMethodDetailList().isEmpty()) {
							DefaultLogger.debug(this, "In get bank details using IFSC_CAM...");
							List ifscBranchList = new ArrayList();
							for (PartyIFSCBankingMethodDetailsRequestDTO bankMthdDetReqDTO : partyDetRequestDTO
									.getIfscBankingMethodDetailList()) {

								if(bankMthdDetReqDTO.getIfscCode()!=null && !bankMthdDetReqDTO.getIfscCode().trim().isEmpty()) {
									IIfscMethod ifscBranch = new OBIfscMethod();
									ifscBranch.setIfscCode(bankMthdDetReqDTO.getIfscCode());
									
									if(bankMthdDetReqDTO.getBankName()!=null && !bankMthdDetReqDTO.getBankName().trim().isEmpty()) {
										ifscBranch.setBankName(bankMthdDetReqDTO.getBankName());
									} else {
										ifscBranch.setBankName("-");
									}
									
									if(bankMthdDetReqDTO.getBranchName()!=null && !bankMthdDetReqDTO.getBranchName().trim().isEmpty()) {
										ifscBranch.setBranchName(bankMthdDetReqDTO.getBranchName());
									} else {
										ifscBranch.setBranchName("-");
									}
									
									if(bankMthdDetReqDTO.getAddress()!=null && !bankMthdDetReqDTO.getAddress().trim().isEmpty()) {
										ifscBranch.setBranchNameAddress(bankMthdDetReqDTO.getAddress());
									} else {
										ifscBranch.setBranchNameAddress("-");
									}
									
									if(bankMthdDetReqDTO.getEmail()!=null && !bankMthdDetReqDTO.getEmail().trim().isEmpty()) {
										ifscBranch.setEmailID(bankMthdDetReqDTO.getEmail());
									} else {
										ifscBranch.setEmailID("-");
									}
									
									ifscBranch.setBankType("O");
									ifscBranch.setStatus("ACTIVE");
									
									if(bankMthdDetReqDTO.getNodalLead()!=null && !bankMthdDetReqDTO.getNodalLead().trim().isEmpty() 
											&& bankMthdDetReqDTO.getNodalLead().equalsIgnoreCase("Y")) {
										/*if (newOBCMSCustomer.getBankingMethod().equals("MULTIPLE") || newOBCMSCustomer.getBankingMethod().equals("OUTSIDEMULTIPLE")) {
											ifscBranch.setNodal("Y");
										} else if (newOBCMSCustomer.getBankingMethod().equals("CONSORTIUM") || newOBCMSCustomer.getBankingMethod().equals("OUTSIDECONSORTIUM")) {
											ifscBranch.setLead("Y");
										}*/
										
										if (newOBCMSCustomer.getBankingMethod().contains("MULTIPLE") || newOBCMSCustomer.getBankingMethod().contains("OUTSIDEMULTIPLE")) {
											ifscBranch.setNodal("Y");
										} else if (newOBCMSCustomer.getBankingMethod().contains("CONSORTIUM") || newOBCMSCustomer.getBankingMethod().contains("OUTSIDECONSORTIUM")) {
											ifscBranch.setLead("Y");
										}
									}
									ifscBranchList.add(ifscBranch);
								}
							}
							DefaultLogger.debug(this, "In get bank details using IFSC_CAM ifscBranchList.size(): "+ifscBranchList.size());
							// insert IFSC data to stage table
							if (null != ifscBranchList) {
								IIfscMethod ifscList[] = new IIfscMethod[ifscBranchList.size()];
								IIfscCodeDao ifscCodeDao = (IIfscCodeDao) BeanHouse.get("ifscCodeDao");

								for (int i = 0; i < ifscBranchList.size(); i++) {
									IIfscMethod ifscObj = (IIfscMethod) ifscBranchList.get(i);
									ifscObj.setCustomerId(Long.parseLong(trxResult.getStagingReferenceID()));
									ifscObj.setStatus("ACTIVE");
									IIfscMethod obj = ifscCodeDao.createStageIfscCode(ifscObj);
								}
							}
							DefaultLogger.debug(this, "Before IFSC Actual table insert");
							// IFSC Actual table insert
							String stagingReferenceID = trxResult.getStagingReferenceID();
							String referenceID = trxResult.getReferenceID();
							IIfscCodeDao ifscCodeDao = (IIfscCodeDao) BeanHouse.get("ifscCodeDao");
							ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
							String ifscCodeList = customerDAO.getIfscCodeList("stage", stagingReferenceID);
							
							customerDAO.updateSanctionedAmountUpdatedFlag(newOBCMSCustomer.getCifId(), ICMSConstant.YES);
							
							DefaultLogger.debug(this, "customerDAO.getIfscCodeList()...."+ifscCodeList);
							
							if (null != ifscCodeList && !"".equals(ifscCodeList)) {
								String[] ifscStringArry = ifscCodeList.split(",");
								ifscCodeDao.createActualIfscCode(ifscStringArry, referenceID);
							}
							DefaultLogger.debug(this, "After IFSC Actual table insert");
						}
					} catch (Exception e) {
						DefaultLogger.error(this, "############# error during create IFSC code ######## ", e);
					}
					DefaultLogger.debug(this, "End get bank details using IFSC_CAM ");
					// end IFSC 
					
					
					//Start Banking Method insert process for tables => CMS_STAGE_BANKING_METHOD_CUST and CMS_BANKING_METHOD_CUST .
					
					try {
						System.out.println("Going for Insert Process of Banking Method.");
						IBankingMethodDAO bankingMethodDAOImpl = (IBankingMethodDAO) BeanHouse.get("bankingMethodDAO");
						
						// New CAM interface CR : ON/OFF Feature
						if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("ON")) {
							List<BankingMethodRequestDTO> bankList = new LinkedList<BankingMethodRequestDTO>();		
							BankingMethodRequestDTO partyBankDTO = new BankingMethodRequestDTO();							
							if(partyDetRequestDTO.getBankingMethod()!=null && !partyDetRequestDTO.getBankingMethod().trim().isEmpty()){
								partyBankDTO.setBankingMethod(partyDetRequestDTO.getBankingMethod().trim());
							}else{
								partyBankDTO.setBankingMethod("");
							}
							bankList.add(partyBankDTO);
							partyDetRequestDTO.setBankingMethodComboBoxList(bankList);
						}
						//End
						
						String bankingMethodss = null;
						for(int i=0;i<partyDetRequestDTO.getBankingMethodComboBoxList().size();i++) {
							if(partyDetRequestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod()!=null && !partyDetRequestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod().trim().isEmpty()){
								bankingMethodss = partyDetRequestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod().trim();
							}
						}
						OBBankingMethod obj = new OBBankingMethod();												
						OBBankingMethod objActual = new OBBankingMethod();
						if(bankingMethodss != null && !"".equals(bankingMethodss)) {
						String[] bankMethodArr = bankingMethodss.split(",");
						ArrayList bankMethList = new ArrayList();
						for(int i=0; i<bankMethodArr.length;i++) {
							String[] bankMethodArr1 =bankMethodArr[i].split("-");
							bankMethList.add(bankMethodArr1[0]);
						}						
						for(int i=0;i<bankMethList.size();i++) {
							//INSERT INTO STAGE TABLE
							obj.setBankType((String)bankMethList.get(i));
							obj.setLEID(Long.parseLong(trxResult.getStagingReferenceID()));
							obj.setCustomerIDForBankingMethod(trxResult.getStagingCustomer().getCifId());
							obj.setStatus("ACTIVE");
							bankingMethodDAOImpl.insertBankingMethodCustStage(obj);
						}
						System.out.println("Insert into CMS_STAGE_BANKING_METHOD_CUST STAGE table is Done.");
						for(int i=0;i<bankMethList.size();i++) {
							//INSERT INTO ACTUAL TABLE
							objActual.setBankType((String)bankMethList.get(i));
							objActual.setLEID(Long.parseLong(trxResult.getReferenceID()));
							objActual.setCustomerIDForBankingMethod(trxResult.getCustomer().getCifId());
							objActual.setStatus("ACTIVE");
							bankingMethodDAOImpl.insertBankingMethodCustActual(objActual);	
						}
						System.out.println("Insert into CMS_BANKING_METHOD_CUST ACTUAL table is Done.");
						}												
																	
					}
					catch(Exception e) {
						System.out.println("Exception caught for Banking method insert process.=>e=>"+e);
						e.printStackTrace();
					}															
				}
			}
			
			if(trxResult != null){
				partyDetResponseDTO.setPartyId(newOBCMSCustomer.getCifId().trim());
				partyDetResponseDTO.setResponseStatus("PARTY_CREATED_SUCCESSFULLY");
				try {
					ClimesToECBFHelper.sendRequest(newOBCMSCustomer);
				} catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.error(this, "Exception caught inside sendRequest while sending data to ecbf party webservice with error: " + e.getMessage(), e);
				}
			}else{
				DefaultLogger.error(this, "savePartyDetails - no value found in trxResult: "+trxResult);
				DefaultLogger.error(this, "savePartyDetails - Else Block-EventName is : "+partyDetailsRequestDTOInstance.getEvent());
				throw new CMSException("Server side error");
			}
			
		}catch (CMSValidationFault e) {
			throw e; 
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error during party create ######## ", e);
			throw new CMSException(e.getMessage(),e); 
		}
		return partyDetResponseDTO;
	}
	
	@CLIMSWebServiceMethod
	public PartyDetailsResponseDTO  updatePartyDetails(PartyDetailsRequestDTO partyDetRequestDTO) throws CMSValidationFault,CMSFault{
		
		PartyDetailsResponseDTO partyDetResponseDTO = new PartyDetailsResponseDTO();
		try {
			String addOrUpdate = "UPDATE";
			System.out.println("PartyDetailsResponseDTO  updatePartyDetails => addOrUpdate=>"+addOrUpdate);
			PartyDetailsRequestDTO partyDetailsRequestDTOInstance = partyDetailsDTOMapper.getRequestDTOWithActualValues(partyDetRequestDTO,addOrUpdate);
			ActionErrors cpsIdErrors = partyDetailsRequestDTOInstance.getErrors();
			
			HashMap map = new HashMap();
			if(cpsIdErrors.size()>0){
				map.put("1", cpsIdErrors);
				ValidationUtility.handleError(map, CLIMSWebService.PARTY);
			}
			
			//ValidationUtility.handleError(cpsIdErrors, CLIMSWebService.PARTY);
			
			ManualInputCustomerInfoForm form = partyDetailsDTOMapper.getFormFromDTO(partyDetailsRequestDTOInstance,addOrUpdate);
			form.setEvent(partyDetailsRequestDTOInstance.getEvent());
			
			ActionErrors errorList = ManualInputCustomerValidator.validateInput(form, Locale.getDefault());
			
			map = new HashMap();
			if(errorList.size()>0){
				map.put("1", errorList);
				ValidationUtility.handleError(map, CLIMSWebService.PARTY);
			}
			
			//ValidationUtility.handleError(errorList, CLIMSWebService.PARTY);
			
			ICustomerProxy proxy = CustomerProxyFactory.getProxy();
			ICMSCustomerTrxValue trxValue = new OBCMSCustomerTrxValue();
			ICMSCustomerTrxValue trxResult = new OBCMSCustomerTrxValue();
			OBTrxContext trxContext = new OBTrxContext();
			trxValue.setTrxContext(trxContext);
			
			ICMSCustomer newOBCMSCustomer = new OBCMSCustomer();
			
			if(partyDetailsRequestDTOInstance.getEvent()!=null){
				
				DefaultLogger.info(this,"partyDetailsRequestDTOInstance.getEvent():::"+partyDetailsRequestDTOInstance.getEvent());
				
				if("WS_update_customer".equalsIgnoreCase(partyDetailsRequestDTOInstance.getEvent())){
					
					ICMSCustomer cust = CustomerProxyFactory.getProxy().getCustomerByCIFSource(partyDetailsRequestDTOInstance.getClimsPartyId().trim(),null);
					String actualTotalSanctionedLimit = cust.getTotalSanctionedLimit();
					if(cust.getStatus()!=null && !cust.getStatus().isEmpty()
							&& "INACTIVE".equalsIgnoreCase(cust.getStatus())){
						throw new CMSException("Party is inactive in system");
					}
					
					newOBCMSCustomer = partyDetailsDTOMapper.getActualFromDTO(partyDetailsRequestDTOInstance,cust,addOrUpdate);
					newOBCMSCustomer.setCifId(partyDetailsRequestDTOInstance.getClimsPartyId().trim());
					newOBCMSCustomer.getCMSLegalEntity().setLEReference(partyDetailsRequestDTOInstance.getClimsPartyId().trim());
					
					trxValue = CustomerProxyFactory.getProxy().getCustomerTrxValue(newOBCMSCustomer.getCustomerID());
					
					if(trxValue!=null && ((trxValue.getStatus().equals("PENDING_CREATE"))
							||(trxValue.getStatus().equals("PENDING_UPDATE"))
							||(trxValue.getStatus().equals("PENDING_DELETE"))
							||(trxValue.getStatus().equals("REJECTED"))||(trxValue.getStatus().equals("DRAFT")))){
						throw new CMSException("Unable to update due to invalid transaction Status :"+trxValue.getStatus());
					}
					
					ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
					String printLogger = bundle1.getString("print.logger.enable");
					if(null!= printLogger && printLogger.equalsIgnoreCase("Y")){
						System.out.println("newOBCMSCustomer.getTotalFundedLimit():::"+newOBCMSCustomer.getTotalFundedLimit());
					}
					newOBCMSCustomer.getCMSLegalEntity().setBankList(trxValue.getCustomer().getCMSLegalEntity().getBankList());
					String newTotalSanctionedLimit = newOBCMSCustomer.getTotalSanctionedLimit();
					if(StringUtils.isNotBlank(newTotalSanctionedLimit) && StringUtils.isNotBlank(actualTotalSanctionedLimit)) {
						BigDecimal newTotalSanctionedLimitVal = UIUtil.mapStringToBigDecimal(newTotalSanctionedLimit);
						BigDecimal actualTotalSanctionedLimitVal = UIUtil.mapStringToBigDecimal(actualTotalSanctionedLimit);
						if(!(actualTotalSanctionedLimitVal.compareTo(newTotalSanctionedLimitVal) == 0)) {
							DefaultLogger.debug(this, "Updating Sanctioned Amount Flag using EJB from updatePartyDetails for partyId :"+newOBCMSCustomer.getCifId() );
							newOBCMSCustomer.setSanctionedAmtUpdatedFlag(ICMSConstant.YES);
						}
						
					}
					
					trxResult = proxy.updateCustomerWithApprovalThroughWsdl(trxContext,trxValue,newOBCMSCustomer);
					
					// IFSC CAM CR code
					DefaultLogger.debug(this, "Start get bank details using IFSC_CAM ");
					try {
						if(!"UPDATE".equals(addOrUpdate)) {
						if (partyDetRequestDTO.getIfscBankingMethodDetailList() != null
								&& !partyDetRequestDTO.getIfscBankingMethodDetailList().isEmpty()) {
							DefaultLogger.debug(this, "In get bank details using IFSC_CAM...");
							List ifscBranchList = new ArrayList();
							for (PartyIFSCBankingMethodDetailsRequestDTO bankMthdDetReqDTO : partyDetRequestDTO
									.getIfscBankingMethodDetailList()) {
								if(bankMthdDetReqDTO.getIfscCode()!=null && !bankMthdDetReqDTO.getIfscCode().trim().isEmpty()) {
									IIfscMethod ifscBranch = new OBIfscMethod();
									ifscBranch.setIfscCode(bankMthdDetReqDTO.getIfscCode());
									
									if(bankMthdDetReqDTO.getBankName()!=null && !bankMthdDetReqDTO.getBankName().trim().isEmpty()) {
										ifscBranch.setBankName(bankMthdDetReqDTO.getBankName());
									} else {
										ifscBranch.setBankName("-");
									}
									
									if(bankMthdDetReqDTO.getBranchName()!=null && !bankMthdDetReqDTO.getBranchName().trim().isEmpty()) {
										ifscBranch.setBranchName(bankMthdDetReqDTO.getBranchName());
									} else {
										ifscBranch.setBranchName("-");
									}
									
									if(bankMthdDetReqDTO.getAddress()!=null && !bankMthdDetReqDTO.getAddress().trim().isEmpty()) {
										ifscBranch.setBranchNameAddress(bankMthdDetReqDTO.getAddress());
									} else {
										ifscBranch.setBranchNameAddress("-");
									}
									
									if(bankMthdDetReqDTO.getEmail()!=null && !bankMthdDetReqDTO.getEmail().trim().isEmpty()) {
										ifscBranch.setEmailID(bankMthdDetReqDTO.getEmail());
									} else {
										ifscBranch.setEmailID("-");
									}
									
									ifscBranch.setBankType("O");
									ifscBranch.setStatus("ACTIVE");
									
									if(bankMthdDetReqDTO.getNodalLead()!=null && !bankMthdDetReqDTO.getNodalLead().trim().isEmpty() 
											&& bankMthdDetReqDTO.getNodalLead().equalsIgnoreCase("Y")) {
										if (newOBCMSCustomer.getBankingMethod().contains("MULTIPLE") || newOBCMSCustomer.getBankingMethod().contains("OUTSIDEMULTIPLE")) {
											ifscBranch.setNodal("Y");
										} else if (newOBCMSCustomer.getBankingMethod().contains("CONSORTIUM") || newOBCMSCustomer.getBankingMethod().contains("OUTSIDECONSORTIUM")) {
											ifscBranch.setLead("Y");
										}
									}
									ifscBranchList.add(ifscBranch);
								}
							}
							DefaultLogger.debug(this, "In get bank details using IFSC_CAM ifscBranchList.size(): "+ifscBranchList.size());
							// insert IFSC data to stage table
							if (null != ifscBranchList) {
								IIfscMethod ifscList[] = new IIfscMethod[ifscBranchList.size()];
								IIfscCodeDao ifscCodeDao = (IIfscCodeDao) BeanHouse.get("ifscCodeDao");
								
								for (int i = 0; i < ifscBranchList.size(); i++) {
									IIfscMethod ifscObj = (IIfscMethod) ifscBranchList.get(i);
									ifscObj.setCustomerId(Long.parseLong(trxResult.getStagingReferenceID()));
									ifscObj.setStatus("ACTIVE");
									IIfscMethod obj = ifscCodeDao.createStageIfscCode(ifscObj);
								}
							}
							DefaultLogger.debug(this, "Before IFSC Actual table insert");
							// IFSC Actual table insert
							String stagingReferenceID = trxResult.getStagingReferenceID();
							String referenceID = trxResult.getReferenceID();
							IIfscCodeDao ifscCodeDao = (IIfscCodeDao) BeanHouse.get("ifscCodeDao");
							ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
							String ifscCodeList = customerDAO.getIfscCodeList("stage", stagingReferenceID);
							
							DefaultLogger.debug(this, "customerDAO.getIfscCodeList()...."+ifscCodeList);
							
							if (null != ifscCodeList && !"".equals(ifscCodeList)) {
								String[] ifscStringArry = ifscCodeList.split(",");
								ifscCodeDao.disableActualIfscCode(referenceID);
								ifscCodeDao.createActualIfscCode(ifscStringArry, referenceID);
							}
							DefaultLogger.debug(this, "After IFSC Actual table insert");
						}
						}
						
						System.out.println("Going for Insert Process of Banking Method.");
						IBankingMethodDAO bankingMethodDAOImpl = (IBankingMethodDAO) BeanHouse.get("bankingMethodDAO");
						
//						// New CAM interface CR : ON/OFF Feature
//						if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("ON")) {
//							List<BankingMethodRequestDTO> bankList = new LinkedList<BankingMethodRequestDTO>();		
//							BankingMethodRequestDTO partyBankDTO = new BankingMethodRequestDTO();							
//							if(partyDetRequestDTO.getBankingMethod()!=null && !partyDetRequestDTO.getBankingMethod().trim().isEmpty()){
//								partyBankDTO.setBankingMethod(partyDetRequestDTO.getBankingMethod().trim());
//							}else{
//								partyBankDTO.setBankingMethod("");
//							}
//							bankList.add(partyBankDTO);
//							partyDetRequestDTO.setBankingMethodComboBoxList(bankList);
//						}
//						//End
						
						String bankingMethodss = null;
						for(int i=0;i<partyDetRequestDTO.getBankingMethodComboBoxList().size();i++) {
							if(partyDetRequestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod()!=null && !partyDetRequestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod().trim().isEmpty()){
								bankingMethodss = partyDetRequestDTO.getBankingMethodComboBoxList().get(i).getBankingMethod().trim();
							}
						}
						OBBankingMethod obj = new OBBankingMethod();												
						OBBankingMethod objActual = new OBBankingMethod();
						if(bankingMethodss != null && !"".equals(bankingMethodss)) {
						String[] bankMethodArr = bankingMethodss.split(",");
						ArrayList bankMethList = new ArrayList();
						for(int i=0; i<bankMethodArr.length;i++) {
							String[] bankMethodArr1 =bankMethodArr[i].split("-");
							bankMethList.add(bankMethodArr1[0]);
						}						
						for(int i=0;i<bankMethList.size();i++) {
							//INSERT INTO STAGE TABLE
							obj.setBankType((String)bankMethList.get(i));
							obj.setLEID(Long.parseLong(trxResult.getStagingReferenceID()));
							obj.setCustomerIDForBankingMethod(trxResult.getStagingCustomer().getCifId());
							obj.setStatus("ACTIVE");
							bankingMethodDAOImpl.insertBankingMethodCustStage(obj);
						}
						System.out.println("Insert into CMS_STAGE_BANKING_METHOD_CUST STAGE table is Done.");
						bankingMethodDAOImpl.disableActualBankingMethod(trxResult.getReferenceID());
						for(int i=0;i<bankMethList.size();i++) {
							//INSERT INTO ACTUAL TABLE
							objActual.setBankType((String)bankMethList.get(i));
							objActual.setLEID(Long.parseLong(trxResult.getReferenceID()));
							objActual.setCustomerIDForBankingMethod(trxResult.getCustomer().getCifId());
							objActual.setStatus("ACTIVE");
							bankingMethodDAOImpl.insertBankingMethodCustActual(objActual);	
						}
						System.out.println("Insert into CMS_BANKING_METHOD_CUST ACTUAL table is Done.");
						}	
						
					} catch (Exception e) {
						DefaultLogger.error(this, "############# error during create IFSC code ######## ", e);
					}
					DefaultLogger.debug(this, "End get bank details using IFSC_CAM ");
					// end IFSC 	
				}
			}
			
			if(trxResult != null){
				partyDetResponseDTO.setPartyId(newOBCMSCustomer.getCifId().trim());
				partyDetResponseDTO.setResponseStatus("PARTY_UPDATED_SUCCESSFULLY");
				try {
					ClimesToECBFHelper.sendRequest(newOBCMSCustomer);
				} catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.error(this, "Exception caught inside sendRequest while sending data to ecbf party webservice with error: " + e.getMessage(), e);
				}
			}else{
				DefaultLogger.error(this, " updatePartyDetails - no value found in trxResult: "+trxResult);
				DefaultLogger.error(this, "updatePartyDetails - Else Block-EventName is : "+partyDetailsRequestDTOInstance.getEvent());
				throw new CMSException("Server side error");
			}
			
		}catch (CMSValidationFault e) {
			throw e; 
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error during party update ######## ", e);
			throw new CMSException(e.getMessage(),e); 
		}
		return partyDetResponseDTO;
	}
}
