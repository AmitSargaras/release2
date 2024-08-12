package com.aurionpro.clims.rest.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import com.aurionpro.clims.rest.mapper.PartyDetailsRestDTOMapper;
import com.aurionpro.clims.rest.common.ValidationErrorDetailsDTO;
import com.aurionpro.clims.rest.common.ValidationUtilityRest;
import com.aurionpro.clims.rest.constants.ResponseConstants;
import com.aurionpro.clims.rest.dto.BodyRestResponseDTO;
import com.aurionpro.clims.rest.dto.CommonRestResponseDTO;
import com.aurionpro.clims.rest.dto.PartyBankingMethodDetailsRestRequestDTO;
import com.aurionpro.clims.rest.dto.PartyDetailsRestRequestDTO;
import com.aurionpro.clims.rest.dto.PartyIFSCBankingMethodDetailsRestRequestDTO;
import com.aurionpro.clims.rest.dto.ResponseMessageDetailDTO;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
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
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.app.systemBank.proxy.ISystemBankProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.ws.common.CLIMSWebService;
import com.integrosys.cms.app.ws.jax.common.CMSException;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.ecbf.counterparty.ClimesToECBFHelper;
import com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerInfoForm;
import com.aurionpro.clims.rest.validator.ManualInputCustomerRestValidator;
import com.integrosys.cms.ui.manualinput.customer.bankingmethod.IBankingMethodDAO;

public class PartyDetailsRestFacade {

	PartyDetailsRestDTOMapper partyDetailsDTOMapper = new PartyDetailsRestDTOMapper();

	public CommonRestResponseDTO createPartyDetailsRestApi(PartyDetailsRestRequestDTO partyDetRequestDTO)
			throws CMSValidationFault, CMSFault {

		CommonRestResponseDTO partyDetResponseDTO = new CommonRestResponseDTO();
		List<BodyRestResponseDTO> BodyRestList = new LinkedList<BodyRestResponseDTO>();
		BodyRestResponseDTO bodyObj = new BodyRestResponseDTO();
		List<ResponseMessageDetailDTO> responseMessageDetailDTOList = new ArrayList<ResponseMessageDetailDTO>();
		List<ValidationErrorDetailsDTO> validationErrorDetailsDTOList;

		try {

			PartyDetailsRestRequestDTO partyDetailsRequestDTOInstance = partyDetailsDTOMapper
					.getRequestDTOWithActualValuesRest(partyDetRequestDTO);

			ActionErrors requestErrors = partyDetailsRequestDTOInstance.getBodyDetails().get(0).getErrors();
			HashMap map = new HashMap();
			if (requestErrors.size() > 0) {
				map.put("1", requestErrors);
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(map, CLIMSWebService.PARTY);
				for (ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
					rmd.setResponseMessage(validationErrorDetailsDTO.getField());
					responseMessageDetailDTOList.add(rmd);
				}
			}

			// ValidationUtilityRest.handleError(requestErrors, CLIMSWebService.PARTY);

			ManualInputCustomerInfoForm form = partyDetailsDTOMapper.getFormFromDTORest(partyDetailsRequestDTOInstance);
			form.setEvent(partyDetailsRequestDTOInstance.getBodyDetails().get(0).getEvent());

			ActionErrors errorList = ManualInputCustomerRestValidator.validateInput(form, Locale.getDefault());
			HashMap newMap = new HashMap();
			if (errorList.size() > 0) {
				newMap.put("1", errorList);
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(newMap, CLIMSWebService.PARTY);
				for (ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
					rmd.setResponseMessage(validationErrorDetailsDTO.getField());
					responseMessageDetailDTOList.add(rmd);
				}
			}

			// ValidationUtility.handleError(errorList, CLIMSWebService.PARTY);
			if (responseMessageDetailDTOList.isEmpty()) {
				ICustomerProxy proxy = CustomerProxyFactory.getProxy();
				ICMSCustomerTrxValue trxValue = new OBCMSCustomerTrxValue();
				ICMSCustomerTrxValue trxResult = new OBCMSCustomerTrxValue();
				OBTrxContext trxContext = new OBTrxContext();
				trxValue.setTrxContext(trxContext);

				ICMSCustomer newOBCMSCustomer = new OBCMSCustomer();

				if (partyDetailsRequestDTOInstance.getBodyDetails().get(0).getEvent() != null) {

					DefaultLogger.info(this, "partyDetailsRequestDTOInstance.getEvent():::"
							+ partyDetailsRequestDTOInstance.getBodyDetails().get(0).getEvent());

					if ("Rest_create_customer"
							.equalsIgnoreCase(partyDetailsRequestDTOInstance.getBodyDetails().get(0).getEvent())) {

						newOBCMSCustomer = partyDetailsDTOMapper.getActualFromDTORest(partyDetailsRequestDTOInstance,
								null);

						// IFSC CAM CR code
						ICMSLegalEntity cmsLegalEntity = newOBCMSCustomer.getCMSLegalEntity();
						IBankingMethod[] bankingMthdArray = cmsLegalEntity.getBankList();
						if (null != bankingMthdArray) {
							for (int i = 0; i < bankingMthdArray.length; i++) {
								if (null != bankingMthdArray[i]) {
									bankingMthdArray[i].setStatus("ACTIVE");
								}
							}
							cmsLegalEntity.setBankList(bankingMthdArray);
							newOBCMSCustomer.setCMSLegalEntity(cmsLegalEntity);
						}
						// end IFSC CAM CR code

						DefaultLogger.debug(this,
								"Updating Sanctioned Amount Flag using EJB from savePartyDetails for partyId :"
										+ newOBCMSCustomer.getCifId());
						newOBCMSCustomer.setSanctionedAmtUpdatedFlag(ICMSConstant.YES);

						trxValue.setStagingCustomer(newOBCMSCustomer);
						trxValue.setLegalName(newOBCMSCustomer.getCMSLegalEntity().getLegalName());
						trxValue.setCustomerName(newOBCMSCustomer.getCustomerName());

						IPartyGroupDao partyDao = (IPartyGroupDao) BeanHouse.get("partyGroupDao");
						newOBCMSCustomer.setCifId(partyDao.getCustomerIdCode());
						newOBCMSCustomer.getCMSLegalEntity().setLEReference(newOBCMSCustomer.getCifId());

						ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
						String printLogger = bundle1.getString("print.logger.enable");
						if (null != printLogger && printLogger.equalsIgnoreCase("Y")) {
							System.out.println("newOBCMSCustomer.getTotalFundedLimit():::"
									+ newOBCMSCustomer.getTotalFundedLimit());
						}

						// IFSC CAM CR code
						List ifscBranchList = new ArrayList();

						if (partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod()
								.contains("CONSORTIUM-CONSORTIUM")
								|| partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod()
								.contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
								|| partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod()
								.equals("CONSORTIUM")
								|| partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod()
								.equals("OUTSIDECONSORTIUM")
								|| partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod()
								.contains("MULTIPLE-MULTIPLE")
								|| partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod()
								.contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
								|| partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod()
								.equals("MULTIPLE")
								|| partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod()
								.equals("OUTSIDEMULTIPLE")) {

							DefaultLogger.debug(this, "Start get bank details using IFSC_CAM ");
							ActionErrors errors = new ActionErrors();
							if (partyDetRequestDTO.getBodyDetails().get(0).getIfscBankingMethodDetailList() != null
									&& !partyDetRequestDTO.getBodyDetails().get(0).getIfscBankingMethodDetailList()
									.isEmpty()) {
								DefaultLogger.debug(this, "In get bank details using IFSC_CAM...");
								List<PartyIFSCBankingMethodDetailsRestRequestDTO> bankList = new LinkedList<PartyIFSCBankingMethodDetailsRestRequestDTO>();
								PartyIFSCBankingMethodDetailsRestRequestDTO systemBankDTO = new PartyIFSCBankingMethodDetailsRestRequestDTO();
								// for (PartyIFSCBankingMethodDetailsRestRequestDTO bankingMthdDetReqDTO :
								// partyDetRequestDTO.getBodyDetails().get(0).getIfscBankingMethodDetailList())
								// {

								// MasterAccessUtility masterObj =
								// (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
								// String bankCode=null;
								// String bankname=bankingMthdDetReqDTO.getBankName().trim();
								// String branchName=bankingMthdDetReqDTO.getBranchName();
								// IOtherBankDAO otherBankDao = (IOtherBankDAO)BeanHouse.get("otherBankDao");
								// List<OBOtherBank> OtherBankList =
								// otherBankDao.getOtherBankList(bankCode,bankname,branchName,"");

								ISystemBankProxyManager systemBankProxy = (ISystemBankProxyManager) BeanHouse
										.get("systemBankProxy");

								List systemBankList = new ArrayList();
								systemBankList = (ArrayList) systemBankProxy.getAllActual();
								OBSystemBank hdfc = (OBSystemBank) systemBankList.get(0);
								systemBankDTO.setBankType("S");
								systemBankDTO.setIfscCode(Long.toString(hdfc.getId()));
								systemBankDTO.setAddress(hdfc.getAddress());
								systemBankDTO.setBranchName(hdfc.getSystemBankName());
								systemBankDTO.setBankName(hdfc.getSystemBankName());
								systemBankDTO.setEmailID(hdfc.getContactMail());
								systemBankDTO.setRevisedEmailIds(hdfc.getRevisedContactMail());
								// bankingMthdDetReqDTO.setBranchId(OtherBankList.get(0).getOtherBranchId());
								// bankingMthdDetReqDTO.setBankType("O");
								// PartyIFSCBankingMethodDetailsRestRequestDTO partyBankDtlsRequestDTO = new
								// PartyIFSCBankingMethodDetailsRestRequestDTO();
								// }
								bankList.add(systemBankDTO);
								partyDetRequestDTO.getBodyDetails().get(0).getIfscBankingMethodDetailList()
								.addAll(bankList);
								// for (PartyIFSCBankingMethodDetailsRestRequestDTO bankMthdDetReqDTO :
								// partyDetRequestDTO.getBodyDetails().get(0).getIfscBankingMethodDetailList())
								// {
								// System.out.println("IfscBankingMethodDetailList is : "+bankMthdDetReqDTO);
								// }
								boolean checkLead = true;
								boolean checkNodal = true;
								boolean Nodalflag = true;
								boolean defaultNodal = true;
								boolean defaultLead = true;
								boolean Leadflag = true;
								int l = 0;
								int n = 0;
								for (PartyBankingMethodDetailsRestRequestDTO bank : partyDetRequestDTO.getBodyDetails()
										.get(0).getBankingMethodDetailList()) {
									System.out.println("BankingMethodDetailList is : " + bank);
									if (bank.getNodal() != null && !bank.getNodal().trim().isEmpty()) {
										System.out.println("Nodal is : " + bank.getNodal().trim());
										if (bank.getNodal().trim().equalsIgnoreCase("Y")) {
											checkNodal = false;
											n++;
										}
									}
									System.out.println("checkNodal is : " + checkNodal);
									if (bank.getLead() != null && !bank.getLead().trim().isEmpty()) {
										System.out.println("Lead is : " + bank.getLead().trim());
										if (bank.getLead().trim().equalsIgnoreCase("Y")) {
											checkLead = false;
											l++;
										}
									}
									System.out.println("checkLead is : " + checkLead);
								}

								for (PartyIFSCBankingMethodDetailsRestRequestDTO bankMthdDetReqDTO : partyDetRequestDTO
										.getBodyDetails().get(0).getIfscBankingMethodDetailList()) {

									IIfscMethod ifscBranch = new OBIfscMethod();
									boolean bankTypeFlag = false;
									if (bankMthdDetReqDTO.getBankType() != null
											&& !bankMthdDetReqDTO.getBankType().trim().isEmpty()) {
										ifscBranch.setBankType(bankMthdDetReqDTO.getBankType());
									} else {
										ifscBranch.setBankType("O");
										bankTypeFlag = true;
									}

									if (bankTypeFlag) {
										if (bankMthdDetReqDTO.getIfscCode() != null
												&& !bankMthdDetReqDTO.getIfscCode().trim().isEmpty()) {
											if (ASSTValidator.validateIFSC(bankMthdDetReqDTO.getIfscCode().trim())) {
												ifscBranch.setIfscCode(bankMthdDetReqDTO.getIfscCode().trim());
											} else {
												errors.add("ifscCode", new ActionMessage("error.ifsc.code.value"));
											}
										} else {
											errors.add("ifscCode", new ActionMessage("error.string.mandatory"));
										}
									} else {
										ifscBranch.setIfscCode(bankMthdDetReqDTO.getIfscCode());
									}

									if (bankMthdDetReqDTO.getBankName() != null
											&& !bankMthdDetReqDTO.getBankName().trim().isEmpty()) {
										ifscBranch.setBankName(bankMthdDetReqDTO.getBankName());
									} else {
										errors.add("bankName(ifsc)", new ActionMessage("error.string.mandatory"));
									}

									if (bankMthdDetReqDTO.getBranchName() != null
											&& !bankMthdDetReqDTO.getBranchName().trim().isEmpty()) {
										ifscBranch.setBranchName(bankMthdDetReqDTO.getBranchName());
									} else {
										errors.add("branchName(ifsc)", new ActionMessage("error.string.mandatory"));
									}

									if (bankMthdDetReqDTO.getAddress() != null
											&& !bankMthdDetReqDTO.getAddress().trim().isEmpty()) {
										ifscBranch.setBranchNameAddress(bankMthdDetReqDTO.getAddress());
									} else {
										errors.add("address(ifsc)", new ActionMessage("error.string.mandatory"));
									}

									String emailMailPropertyValue = PropertyManager.getValue("rest.ifsc.list.email.mandatory");
									if(emailMailPropertyValue!=null && emailMailPropertyValue.equalsIgnoreCase("Yes")){
										if (bankTypeFlag) {
											if (bankMthdDetReqDTO.getEmailID() != null
													&& !bankMthdDetReqDTO.getEmailID().trim().isEmpty()) {
												if (!ASSTValidator.isValidEmail(bankMthdDetReqDTO.getEmailID().trim())) {
													ifscBranch.setEmailID(bankMthdDetReqDTO.getEmailID());
												} else {
													errors.add("emailID(ifsc)",
															new ActionMessage("error.string.email.format"));
												}
											} else {
												errors.add("emailID(ifsc)", new ActionMessage("error.string.mandatory"));
											}
										} else {
											ifscBranch.setEmailID(bankMthdDetReqDTO.getEmailID());
										}
									}else if (emailMailPropertyValue!=null && emailMailPropertyValue.equalsIgnoreCase("No")) {
										if(bankTypeFlag) {
											if (bankMthdDetReqDTO.getEmailID() != null
													&& !bankMthdDetReqDTO.getEmailID().trim().isEmpty()) {
												if (!ASSTValidator.isValidEmail(bankMthdDetReqDTO.getEmailID().trim())) {
													ifscBranch.setEmailID(bankMthdDetReqDTO.getEmailID());
												} else {
													errors.add("emailID(ifsc)",
															new ActionMessage("error.string.email.format"));
												}
											} else {
												ifscBranch.setEmailID("-");
											}
										} else {
											ifscBranch.setEmailID(bankMthdDetReqDTO.getEmailID());
										}
									}
									// String bankCode=null;
									// String bankname=bankMthdDetReqDTO.getBankName().trim();
									// String branchName=bankMthdDetReqDTO.getBranchName();

									// if (bankMthdDetReqDTO.getNodal() != null &&
									// !bankMthdDetReqDTO.getNodal().equals("")) {
									// if (bankMthdDetReqDTO.getBankType()!=null &&
									// bankMthdDetReqDTO.getBankType().equals("O")) {
									// if (bankMthdDetReqDTO.getNodal()!= null &&
									// bankMthdDetReqDTO.getNodal().equalsIgnoreCase("Y")) {
									// ifscBranch.setNodal("o,"+bankMthdDetReqDTO.getBranchId());
									//
									// }}
									// else if (bankMthdDetReqDTO.getBankType()!=null &&
									// bankMthdDetReqDTO.getBankType().equals("S")) {
									// if (bankMthdDetReqDTO.getNodal()!= null &&
									// bankMthdDetReqDTO.getNodal().equals("Y")) {
									// ifscBranch.setNodal("s,"+bankMthdDetReqDTO.getBranchId());
									// }}}
									//
									// if (bankMthdDetReqDTO.getLead() != null &&
									// !bankMthdDetReqDTO.getLead().equals("")) {
									// if (bankMthdDetReqDTO.getBankType()!=null &&
									// bankMthdDetReqDTO.getBankType().equals("O")) {
									// if (bankMthdDetReqDTO.getLead()!= null &&
									// bankMthdDetReqDTO.getLead().equals("Y")) {
									// ifscBranch.setLead("o,"+bankMthdDetReqDTO.getBranchId());
									//
									// }}
									// else if (bankMthdDetReqDTO.getBankType()!=null &&
									// bankMthdDetReqDTO.getBankType().equals("S")) {
									// if (bankMthdDetReqDTO.getLead()!= null &&
									// bankMthdDetReqDTO.getLead().equals("Y")) {
									// ifscBranch.setLead("s,"+bankMthdDetReqDTO.getBranchId());
									//
									// }}}


									if (null != bankMthdDetReqDTO.getNodal()
											&& !bankMthdDetReqDTO.getNodal().trim().isEmpty()) {
										if ("Y".equalsIgnoreCase(bankMthdDetReqDTO.getNodal().trim())) {
											if (Nodalflag) {
												defaultNodal = false;
											} else {
												errors.add("Nodal", new ActionMessage("error.string.multiple"));
											}
											n++;
										}
									}

									if (checkNodal) {
										if (defaultNodal == false && Nodalflag == true) {
											ifscBranch.setNodal(bankMthdDetReqDTO.getNodal().trim());
											Nodalflag = false;
										}
									}

									if (null != bankMthdDetReqDTO.getLead()
											&& !bankMthdDetReqDTO.getLead().trim().isEmpty()) {
										if ("Y".equalsIgnoreCase(bankMthdDetReqDTO.getLead().trim())) {
											if (Leadflag) {
												defaultLead = false;
											} else {
												errors.add("Lead", new ActionMessage("error.string.multiple"));
											}
											l++;
										}
									}

									if (checkLead) {
										if (defaultLead == false && Leadflag == true) {
											ifscBranch.setLead(bankMthdDetReqDTO.getLead().trim());
											Leadflag = false;
										}
									}


									if (bankMthdDetReqDTO.getRevisedEmailIds() != null
											&& !bankMthdDetReqDTO.getRevisedEmailIds().trim().isEmpty()) {
										if (!ASSTValidator
												.isValidEmail(bankMthdDetReqDTO.getRevisedEmailIds().trim())) {
											ifscBranch.setRevisedEmailID(bankMthdDetReqDTO.getRevisedEmailIds().trim());
										} else {
											errors.add("revisedEmailID",
													new ActionMessage("error.string.email.format"));
										}

									} else {
										ifscBranch.setRevisedEmailID("-");
									}

									ifscBranch.setStatus("ACTIVE");

									/*
									 * if(bankMthdDetReqDTO.getNodalLead()!=null &&
									 * !bankMthdDetReqDTO.getNodalLead().trim().isEmpty() &&
									 * bankMthdDetReqDTO.getNodalLead().equalsIgnoreCase("Y")) { if
									 * (newOBCMSCustomer.getBankingMethod().equals("MULTIPLE") ||
									 * newOBCMSCustomer.getBankingMethod().equals("OUTSIDEMULTIPLE")) {
									 * ifscBranch.setNodal("Y"); } else if
									 * (newOBCMSCustomer.getBankingMethod().equals("CONSORTIUM") ||
									 * newOBCMSCustomer.getBankingMethod().equals("OUTSIDECONSORTIUM")) {
									 * ifscBranch.setLead("Y"); } }
									 */
									System.out.println("ifscBranch data is : " + ifscBranch);
									ifscBranchList.add(ifscBranch);
								}

								if (Leadflag && Nodalflag && checkNodal && checkLead) {
									errors.add("Lead/Nodal", new ActionMessage("error.string.leadnodal.flag"));								
								}

								if (n > 1) {
									errors.add("Nodal", new ActionMessage("error.string.multiple.nodal"));
								}

								if (l > 1) {
									errors.add("Lead", new ActionMessage("error.string.multiple.lead"));
								}

								HashMap newMap1 = new HashMap();
								if (errors.size() > 0) {
									newMap1.put("1", errors);
									validationErrorDetailsDTOList = ValidationUtilityRest.handleError(newMap1,
											CLIMSWebService.PARTY);
									for (ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
										ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
										rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
										rmd.setResponseMessage(validationErrorDetailsDTO.getField());
										responseMessageDetailDTOList.add(rmd);
									}
								}
								if (!responseMessageDetailDTOList.isEmpty()) {
									bodyObj.setPartyId("");
									bodyObj.setResponseStatus("FAILED");
									bodyObj.setResponseMessageList(responseMessageDetailDTOList);
									BodyRestList.add(bodyObj);
									partyDetResponseDTO.setBodyDetails(BodyRestList);
									return partyDetResponseDTO;
								}
							}
						}
						
						trxResult = proxy.createCustomerWithApprovalThroughWsdl(trxContext, trxValue);

						try {
							// insert IFSC data to stage table
							if (null != ifscBranchList) {
								DefaultLogger.debug(this, "In get bank details using IFSC_CAM ifscBranchList.size(): "
										+ ifscBranchList.size());							
//								IIfscMethod ifscList[] = new IIfscMethod[ifscBranchList.size()];
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

							DefaultLogger.debug(this, "customerDAO.getIfscCodeList()...." + ifscCodeList);

							if (null != ifscCodeList && !"".equals(ifscCodeList)) {
								String[] ifscStringArry = ifscCodeList.split(",");
								ifscCodeDao.createActualIfscCode(ifscStringArry, referenceID);
							}
							DefaultLogger.debug(this, "After IFSC Actual table insert");
						} catch (Exception e) {
							DefaultLogger.error(this, "############# error during create IFSC code ######## ", e);
						}

						String bankingMethodss = partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod();
						IBankingMethodDAO bankingMethodDAOImpl = (IBankingMethodDAO) BeanHouse.get("bankingMethodDAO");
						// String bankingMethodss = obCustomer.getBankingMethod();
						/*
						 * if(bankingMethodss == null || "".equals(bankingMethodss)) { bankingMethodss =
						 * obCustomer.getFinalBankMethodList(); }
						 */
						if (bankingMethodss != null && !"".equals(bankingMethodss)) {
							String[] bankMethodArr = bankingMethodss.split(",");
							ArrayList bankMethList = new ArrayList();
							for (int i = 0; i < bankMethodArr.length; i++) {
								String[] bankMethodArr1 = bankMethodArr[i].split("-");
								bankMethList.add(bankMethodArr1[0]);
							}

							for (int i = 0; i < bankMethList.size(); i++) {
								OBBankingMethod obj = new OBBankingMethod();
								obj.setBankType((String) bankMethList.get(i));
								obj.setLEID(Long.parseLong(trxResult.getStagingReferenceID()));
								obj.setCustomerIDForBankingMethod(newOBCMSCustomer.getCifId());
								obj.setStatus("ACTIVE");
								bankingMethodDAOImpl.insertBankingMethodCustStage(obj);

							}
						}

						// START INSERT RECORDS INTO CMS_BANKING_METHOD_CUST FOR BANKING_METHODS

						// String bankingMethodss = obCustomer.getBankingMethod();
						// if(bankingMethodss == null || "".equals(bankingMethodss)) {
						// bankingMethodss = stageCustomer.getFinalBankMethodList();
						// }
						if (bankingMethodss != null && !"".equals(bankingMethodss)) {
							String[] bankMethodArr = bankingMethodss.split(",");
							ArrayList bankMethList = new ArrayList();
							for (int i = 0; i < bankMethodArr.length; i++) {
								String[] bankMethodArr1 = bankMethodArr[i].split("-");
								bankMethList.add(bankMethodArr1[0]);
							}

							for (int i = 0; i < bankMethList.size(); i++) {
								OBBankingMethod obj = new OBBankingMethod();
								obj.setBankType((String) bankMethList.get(i));
								obj.setLEID(Long.parseLong(trxResult.getReferenceID()));
								obj.setCustomerIDForBankingMethod(newOBCMSCustomer.getCifId());
								obj.setStatus("ACTIVE");
								bankingMethodDAOImpl.insertBankingMethodCustActual(obj);

							}
						}
						// END
					}
				}

				if (trxResult != null) {

					List<ResponseMessageDetailDTO> responseMessageList = new LinkedList<ResponseMessageDetailDTO>();
					ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
					responseMessageDetailDTO.setResponseCode(ResponseConstants.PTY_SUCCESS_RESPONSE_CODE);
					responseMessageDetailDTO.setResponseMessage(ResponseConstants.PTY_ADD_SUCCESS_MESSAGE);
					responseMessageList.add(responseMessageDetailDTO);

					bodyObj.setResponseStatus("SUCCESS");
					bodyObj.setPartyId(newOBCMSCustomer.getCifId());
					bodyObj.setResponseMessageList(responseMessageList);

					BodyRestList.add(bodyObj);
					partyDetResponseDTO.setBodyDetails(BodyRestList);

					try {
						ClimesToECBFHelper.sendRequest(newOBCMSCustomer);
					} catch (Exception e) {
						e.printStackTrace();
						DefaultLogger.error(this,
								"Exception caught inside sendRequest while sending data to ecbf party webservice with error: "
										+ e.getMessage(),
								e);
					}
				} else {
					DefaultLogger.error(this, "savePartyDetails - no value found in trxResult: " + trxResult);
					DefaultLogger.error(this, "savePartyDetails - Else Block-EventName is : "
							+ partyDetailsRequestDTOInstance.getBodyDetails().get(0).getEvent());
					throw new CMSException("Server side error");
				}
			} else {
				bodyObj.setPartyId("");
				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageDetailDTOList);
				BodyRestList.add(bodyObj);
				partyDetResponseDTO.setBodyDetails(BodyRestList);
			}

		} catch (CMSValidationFault e) {
			throw e;
		} catch (Exception e) {
			DefaultLogger.error(this, "############# error during party create ######## ", e);
			throw new CMSException(e.getMessage(), e);
		}

		return partyDetResponseDTO;
	}

	// New updateParty Method for RestApi
	public CommonRestResponseDTO updatePartyDetailsRestApi(PartyDetailsRestRequestDTO partyDetRequestDTO)
			throws CMSValidationFault, CMSFault {

		CommonRestResponseDTO partyDetResponseDTO = new CommonRestResponseDTO();
		List<BodyRestResponseDTO> BodyRestList = new LinkedList<BodyRestResponseDTO>();
		BodyRestResponseDTO bodyObj = new BodyRestResponseDTO();
		List<ResponseMessageDetailDTO> responseMessageDetailDTOList = new ArrayList<ResponseMessageDetailDTO>();
		List<ValidationErrorDetailsDTO> validationErrorDetailsDTOList;
		try {

			if(partyDetRequestDTO.getBodyDetails().get(0).getClimsPartyId()==null || partyDetRequestDTO.getBodyDetails().get(0).getClimsPartyId().trim().isEmpty()){
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode("PTY0001");
					rmd.setResponseMessage("climsPartyId is mandatory");
					responseMessageDetailDTOList.add(rmd);
					bodyObj.setPartyId("");
					bodyObj.setResponseStatus("FAILED");
					bodyObj.setResponseMessageList(responseMessageDetailDTOList);
					BodyRestList.add(bodyObj);
					partyDetResponseDTO.setBodyDetails(BodyRestList);
					return partyDetResponseDTO;
			}
			
			PartyDetailsRestRequestDTO partyDetailsRequestDTOInstance = partyDetailsDTOMapper
					.getRequestDTOWithActualValuesRest(partyDetRequestDTO);
			ActionErrors requestErrors = partyDetailsRequestDTOInstance.getBodyDetails().get(0).getErrors();
			HashMap map = new HashMap();
			if (requestErrors.size() > 0) {
				map.put("1", requestErrors);
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(map, CLIMSWebService.PARTY);
				for (ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
					rmd.setResponseMessage(validationErrorDetailsDTO.getField());
					responseMessageDetailDTOList.add(rmd);
				}
			}
			// ValidationUtility.handleError(cpsIdErrors, CLIMSWebService.PARTY);

			ManualInputCustomerInfoForm form = partyDetailsDTOMapper.getFormFromDTORest(partyDetailsRequestDTOInstance);
			form.setEvent(partyDetailsRequestDTOInstance.getBodyDetails().get(0).getEvent());

			ActionErrors errorList = ManualInputCustomerRestValidator.validateInput(form, Locale.getDefault());
			HashMap newMap = new HashMap();
			if (errorList.size() > 0) {
				newMap.put("1", errorList);
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(newMap, CLIMSWebService.PARTY);
				for (ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
					rmd.setResponseMessage(validationErrorDetailsDTO.getField());
					responseMessageDetailDTOList.add(rmd);
				}
			}
			// ValidationUtility.handleError(errorList, CLIMSWebService.PARTY);

			if (responseMessageDetailDTOList.isEmpty()) {
				ICustomerProxy proxy = CustomerProxyFactory.getProxy();
				ICMSCustomerTrxValue trxValue = new OBCMSCustomerTrxValue();
				ICMSCustomerTrxValue trxResult = new OBCMSCustomerTrxValue();
				OBTrxContext trxContext = new OBTrxContext();
				trxValue.setTrxContext(trxContext);

				ICMSCustomer newOBCMSCustomer = new OBCMSCustomer();

				if (partyDetailsRequestDTOInstance.getBodyDetails().get(0).getEvent() != null) {

					DefaultLogger.info(this, "partyDetailsRequestDTOInstance.getEvent():::"
							+ partyDetailsRequestDTOInstance.getBodyDetails().get(0).getEvent());

					if ("Rest_update_customer"
							.equalsIgnoreCase(partyDetailsRequestDTOInstance.getBodyDetails().get(0).getEvent())) {

						ICMSCustomer cust = CustomerProxyFactory.getProxy().getCustomerByCIFSource(
								partyDetailsRequestDTOInstance.getBodyDetails().get(0).getClimsPartyId().trim(), null);
						String actualTotalSanctionedLimit = cust.getTotalSanctionedLimit();
						if (cust.getStatus() != null && !cust.getStatus().isEmpty()
								&& "INACTIVE".equalsIgnoreCase(cust.getStatus())) {
							ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
							rmd.setResponseCode("PTY0002");
							rmd.setResponseMessage("Party is inactive in system");
							responseMessageDetailDTOList.add(rmd);
							bodyObj.setPartyId("");
							bodyObj.setResponseStatus("FAILED");
							bodyObj.setResponseMessageList(responseMessageDetailDTOList);
							BodyRestList.add(bodyObj);
							partyDetResponseDTO.setBodyDetails(BodyRestList);
							return partyDetResponseDTO;
						}

						newOBCMSCustomer = partyDetailsDTOMapper.getActualFromDTORest(partyDetailsRequestDTOInstance,
								cust);
						newOBCMSCustomer.setCifId(
								partyDetailsRequestDTOInstance.getBodyDetails().get(0).getClimsPartyId().trim());
						newOBCMSCustomer.getCMSLegalEntity().setLEReference(
								partyDetailsRequestDTOInstance.getBodyDetails().get(0).getClimsPartyId().trim());

						trxValue = CustomerProxyFactory.getProxy()
								.getCustomerTrxValue(newOBCMSCustomer.getCustomerID());

						if (trxValue != null && ((trxValue.getStatus().equals("PENDING_CREATE"))
								|| (trxValue.getStatus().equals("PENDING_UPDATE"))
								|| (trxValue.getStatus().equals("PENDING_DELETE"))
								|| (trxValue.getStatus().equals("REJECTED"))
								|| (trxValue.getStatus().equals("DRAFT")))) {
							ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
							rmd.setResponseCode("PTY0002");
							rmd.setResponseMessage(
									"Unable to update due to invalid transaction Status :" + trxValue.getStatus());
							responseMessageDetailDTOList.add(rmd);
							bodyObj.setPartyId("");
							bodyObj.setResponseStatus("FAILED");
							bodyObj.setResponseMessageList(responseMessageDetailDTOList);
							BodyRestList.add(bodyObj);
							partyDetResponseDTO.setBodyDetails(BodyRestList);
							return partyDetResponseDTO;
						}

						ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
						String printLogger = bundle1.getString("print.logger.enable");
						if (null != printLogger && printLogger.equalsIgnoreCase("Y")) {
							System.out.println("newOBCMSCustomer.getTotalFundedLimit():::"
									+ newOBCMSCustomer.getTotalFundedLimit());
						}

						String newTotalSanctionedLimit = newOBCMSCustomer.getTotalSanctionedLimit();
						if (StringUtils.isNotBlank(newTotalSanctionedLimit)
								&& StringUtils.isNotBlank(actualTotalSanctionedLimit)) {
							BigDecimal newTotalSanctionedLimitVal = UIUtil
									.mapStringToBigDecimal(newTotalSanctionedLimit);
							BigDecimal actualTotalSanctionedLimitVal = UIUtil
									.mapStringToBigDecimal(actualTotalSanctionedLimit);
							if (!(actualTotalSanctionedLimitVal.compareTo(newTotalSanctionedLimitVal) == 0)) {
								DefaultLogger.debug(this,
										"Updating Sanctioned Amount Flag using EJB from updatePartyDetails for partyId :"
												+ newOBCMSCustomer.getCifId());
								newOBCMSCustomer.setSanctionedAmtUpdatedFlag(ICMSConstant.YES);
							}

						}

						// IFSC CAM CR code
						List ifscBranchList = new ArrayList();
						
						if (partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod()
								.contains("CONSORTIUM-CONSORTIUM")
								|| partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod()
								.contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
								|| partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod()
								.equals("CONSORTIUM")
								|| partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod()
								.equals("OUTSIDECONSORTIUM")
								|| partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod()
								.contains("MULTIPLE-MULTIPLE")
								|| partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod()
								.contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
								|| partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod()
								.equals("MULTIPLE")
								|| partyDetRequestDTO.getBodyDetails().get(0).getBankingMethod()
								.equals("OUTSIDEMULTIPLE")) {

							int n = 0;
							int l = 0;
							DefaultLogger.debug(this, "Start get bank details using IFSC_CAM ");
							ActionErrors errors = new ActionErrors();
							try {
								if (partyDetRequestDTO.getBodyDetails().get(0).getIfscBankingMethodDetailList() != null
										&& !partyDetRequestDTO.getBodyDetails().get(0).getIfscBankingMethodDetailList()
										.isEmpty()) {
									DefaultLogger.debug(this, "In get bank details using IFSC_CAM...");

									for (PartyIFSCBankingMethodDetailsRestRequestDTO bankMthdDetReqDTO : partyDetRequestDTO
											.getBodyDetails().get(0).getIfscBankingMethodDetailList()) {
										if (bankMthdDetReqDTO.getIfscCode() != null
												&& !bankMthdDetReqDTO.getIfscCode().trim().isEmpty()) {
											IIfscMethod ifscBranch = new OBIfscMethod();

											if (bankMthdDetReqDTO.getIfscCode() != null
													&& !bankMthdDetReqDTO.getIfscCode().trim().isEmpty()) {
												if (ASSTValidator.validateIFSC(bankMthdDetReqDTO.getIfscCode().trim())) {
													ifscBranch.setIfscCode(bankMthdDetReqDTO.getIfscCode().trim());
												} else {
													errors.add("ifscCode", new ActionMessage("error.ifsc.code.value"));
												}
											} else {
												errors.add("ifscCode", new ActionMessage("error.string.mandatory"));
											}

											if (bankMthdDetReqDTO.getBankName() != null
													&& !bankMthdDetReqDTO.getBankName().trim().isEmpty()) {
												ifscBranch.setBankName(bankMthdDetReqDTO.getBankName());
											} else {
												errors.add("bankName(ifsc)", new ActionMessage("error.string.mandatory"));
											}

											if (bankMthdDetReqDTO.getBranchName() != null
													&& !bankMthdDetReqDTO.getBranchName().trim().isEmpty()) {
												ifscBranch.setBranchName(bankMthdDetReqDTO.getBranchName());
											} else {
												errors.add("branchName(ifsc)", new ActionMessage("error.string.mandatory"));
											}

											if (bankMthdDetReqDTO.getAddress() != null
													&& !bankMthdDetReqDTO.getAddress().trim().isEmpty()) {
												ifscBranch.setBranchNameAddress(bankMthdDetReqDTO.getAddress());
											} else {
												errors.add("address(ifsc)", new ActionMessage("error.string.mandatory"));
											}

											String emailMailPropertyValue = PropertyManager.getValue("rest.ifsc.list.email.mandatory");
											if(emailMailPropertyValue!=null && emailMailPropertyValue.equalsIgnoreCase("Yes")) {
												if (bankMthdDetReqDTO.getEmailID() != null
														&& !bankMthdDetReqDTO.getEmailID().trim().isEmpty()) {
													if (!ASSTValidator.isValidEmail(bankMthdDetReqDTO.getEmailID().trim())) {
														ifscBranch.setEmailID(bankMthdDetReqDTO.getEmailID());
													} else {
														errors.add("emailID(ifsc)", new ActionMessage("error.string.email.format"));
													}
												} else {
													errors.add("emailID(ifsc)", new ActionMessage("error.string.mandatory"));
												}
											}else if(emailMailPropertyValue!=null && emailMailPropertyValue.equalsIgnoreCase("No")){
												if (bankMthdDetReqDTO.getEmailID() != null
														&& !bankMthdDetReqDTO.getEmailID().trim().isEmpty()) {
													if (!ASSTValidator.isValidEmail(bankMthdDetReqDTO.getEmailID().trim())) {
														ifscBranch.setEmailID(bankMthdDetReqDTO.getEmailID());
													} else {
														errors.add("emailID(ifsc)", new ActionMessage("error.string.email.format"));
													}
												} else {
													ifscBranch.setEmailID("-");
												}
											}

											if (bankMthdDetReqDTO.getLead() != null
													&& !bankMthdDetReqDTO.getLead().trim().isEmpty()) {
												ifscBranch.setLead(bankMthdDetReqDTO.getLead().trim());
												if (bankMthdDetReqDTO.getLead().trim().equals("Y")) {
													l++;
												}
											} else {
												ifscBranch.setLead("-");
											}

											if (bankMthdDetReqDTO.getNodal() != null
													&& !bankMthdDetReqDTO.getNodal().trim().isEmpty()) {
												ifscBranch.setNodal(bankMthdDetReqDTO.getNodal().trim());
												if (bankMthdDetReqDTO.getNodal().trim().equals("Y")) {
													n++;
												}
											} else {
												ifscBranch.setNodal("-");
											}

											if (bankMthdDetReqDTO.getRevisedEmailIds() != null
													&& !bankMthdDetReqDTO.getRevisedEmailIds().trim().isEmpty()) {
												if (!ASSTValidator
														.isValidEmail(bankMthdDetReqDTO.getRevisedEmailIds().trim())) {
													ifscBranch.setRevisedEmailID(
															bankMthdDetReqDTO.getRevisedEmailIds().trim());
												} else {
													errors.add("revisedEmailIds",
															new ActionMessage("error.string.email.format"));
												}
											} else {
												ifscBranch.setRevisedEmailID("-");
											}

											ifscBranch.setBankType("O");
											ifscBranch.setStatus("ACTIVE");

											/*
											 * if(bankMthdDetReqDTO.getNodalLead()!=null &&
											 * !bankMthdDetReqDTO.getNodalLead().trim().isEmpty() &&
											 * bankMthdDetReqDTO.getNodalLead().equalsIgnoreCase("Y")) { if
											 * (newOBCMSCustomer.getBankingMethod().equals("MULTIPLE") ||
											 * newOBCMSCustomer.getBankingMethod().equals("OUTSIDEMULTIPLE")) {
											 * ifscBranch.setNodal("Y"); } else if
											 * (newOBCMSCustomer.getBankingMethod().equals("CONSORTIUM") ||
											 * newOBCMSCustomer.getBankingMethod().equals("OUTSIDECONSORTIUM")) {
											 * ifscBranch.setLead("Y"); } }
											 */
											ifscBranchList.add(ifscBranch);
										}
									}

									if (n > 1) {
										errors.add("Nodal", new ActionMessage("error.string.multiple.nodal"));
									}

									if (l > 1) {
										errors.add("Lead", new ActionMessage("error.string.multiple.lead"));
									}

									HashMap newMap1 = new HashMap();
									if (errors.size() > 0) {
										newMap1.put("1", errors);
										validationErrorDetailsDTOList = ValidationUtilityRest.handleError(newMap1,
												CLIMSWebService.PARTY);
										for (ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
											ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
											rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
											rmd.setResponseMessage(validationErrorDetailsDTO.getField());
											responseMessageDetailDTOList.add(rmd);
										}
									}
									if (!responseMessageDetailDTOList.isEmpty()) {
										bodyObj.setPartyId("");
										bodyObj.setResponseStatus("FAILED");
										bodyObj.setResponseMessageList(responseMessageDetailDTOList);
										BodyRestList.add(bodyObj);
										partyDetResponseDTO.setBodyDetails(BodyRestList);
										return partyDetResponseDTO;
									}
								}
							} catch (Exception e) {
								DefaultLogger.error(this, "############# error during create IFSC code ######## ", e);
							}
							DefaultLogger.debug(this, "End get bank details using IFSC_CAM ");
						}
						// end IFSC
						
						trxResult = proxy.updateCustomerWithApprovalThroughWsdl(trxContext, trxValue,
								newOBCMSCustomer);

						DefaultLogger.debug(this, "In get bank details using IFSC_CAM ifscBranchList.size(): "
								+ ifscBranchList.size());
						// insert IFSC data to stage table
						if (null != ifscBranchList) {
//							IIfscMethod ifscList[] = new IIfscMethod[ifscBranchList.size()];
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

						DefaultLogger.debug(this, "customerDAO.getIfscCodeList()...." + ifscCodeList);

						if (null != ifscCodeList && !"".equals(ifscCodeList)) {
							String[] ifscStringArry = ifscCodeList.split(",");
							ifscCodeDao.disableActualIfscCode(referenceID);
							ifscCodeDao.createActualIfscCode(ifscStringArry, referenceID);
						}
						DefaultLogger.debug(this, "After IFSC Actual table insert");

					}
				}

				if (trxResult != null) {

					List<ResponseMessageDetailDTO> responseMessageList = new LinkedList<ResponseMessageDetailDTO>();
					ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
					responseMessageDetailDTO.setResponseCode(ResponseConstants.PTY_SUCCESS_RESPONSE_CODE);
					responseMessageDetailDTO.setResponseMessage(ResponseConstants.PTY_UPDT_SUCCESS_MESSAGE);
					responseMessageList.add(responseMessageDetailDTO);

					bodyObj.setResponseStatus("SUCCESS");
					bodyObj.setPartyId(newOBCMSCustomer.getCifId());
					bodyObj.setResponseMessageList(responseMessageList);

					BodyRestList.add(bodyObj);
					partyDetResponseDTO.setBodyDetails(BodyRestList);

					try {
						ClimesToECBFHelper.sendRequest(newOBCMSCustomer);
					} catch (Exception e) {
						e.printStackTrace();
						DefaultLogger.error(this,
								"Exception caught inside sendRequest while sending data to ecbf party webservice with error: "
										+ e.getMessage(),
								e);
					}
				}
			} else {
				bodyObj.setPartyId("");
				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageDetailDTOList);
				BodyRestList.add(bodyObj);
				partyDetResponseDTO.setBodyDetails(BodyRestList);
			}

		} catch (CMSValidationFault e) {
			throw e;
		} catch (Exception e) {
			DefaultLogger.error(this, "############# error during party update ######## ", e);
			throw new CMSException(e.getMessage(), e);
		}
		return partyDetResponseDTO;
	}

}
