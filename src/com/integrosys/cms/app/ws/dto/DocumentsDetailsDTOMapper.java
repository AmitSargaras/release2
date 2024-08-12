package com.integrosys.cms.app.ws.dto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.springframework.stereotype.Service;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.checklist.bus.CheckListDAO;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.DocumentDAO;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.chktemplate.bus.OBTemplate;
import com.integrosys.cms.app.chktemplate.bus.OBTemplateItem;
import com.integrosys.cms.app.chktemplate.bus.TemplateDAO;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.ws.common.DocumentCategoryEnum;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.host.eai.limit.bus.ILimitJdbc;
import com.integrosys.cms.host.eai.security.bus.ISecurityDao;

@Service
public class DocumentsDetailsDTOMapper {
	
	public ICheckList getActualFromDTO(DocumentsDetailRequestDTO documentDetRequestDTO,ILimitJdbc limitJdbc,String camId,ICheckList obCheckList,String securitySubType) {
		
		if(obCheckList == null){
			obCheckList  = new OBCheckList();
		}
		
		ICollateralCheckListOwner checkListOwner = new OBCollateralCheckListOwner(0, 0, "","");
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		
		if(!StringUtils.isBlank(camId)){
			checkListOwner.setLimitProfileID(Long.parseLong(camId));
			obCheckList.setCheckListOwner(checkListOwner);
			
			ILimitProfile profile = null;
			try{
				profile = limitProxy.getLimitProfile(checkListOwner.getLimitProfileID());
				obCheckList.setCamDate(profile.getApprovalDate());
				obCheckList.setCamNumber(profile.getBCAReference());
				obCheckList.setCamType(profile.getCamType());
				obCheckList.setIsLatest("Y");
			}catch (LimitException e) {
				e.printStackTrace();
			}
		}
		
		int numOfItems = 0;
		
		//Set Check List Item
//		List<ICheckListItem> checkListItemArrayList  = new ArrayList<ICheckListItem>();
		if(obCheckList.getCheckListItemList()!=null && obCheckList.getCheckListItemList().length>0){
			numOfItems = obCheckList.getCheckListItemList().length;
//			checkListItemArrayList = Arrays.asList(obCheckList.getCheckListItemList());
		}
		ICheckListItem[] modifiedCheckListItemList = new OBCheckListItem[numOfItems + 1];

		String mappingId = "";
		String docCategory = "";
		String[] docDetails = null;
		String documentDesc = null;
		String documentID = null;
		String masterListId = null;
		String securitySubTypeId = null;
		String statementType = null;
		String tenureCount = null;
		String tenureType = null;
		DocumentDAO documentDAO = new DocumentDAO();
	
		// Set Document Values in CheckListItem Object - START
		ICheckListItem obCheckListItem = new OBCheckListItem();
	
		String docCode = "";
		if(documentDetRequestDTO.getDocumentCode()!=null && !documentDetRequestDTO.getDocumentCode().trim().isEmpty()){
			docCode = documentDetRequestDTO.getDocumentCode().trim();
		}
		
		obCheckListItem.setItemCode(docCode);
		
		//To Get Document Description/ID using Document code
		try {
			docDetails = documentDAO.getDocumentIdAndDescByDocCode(docCode);
			if(docDetails!=null){
				documentID = docDetails[0];
				documentDesc = docDetails[1];
				statementType = docDetails[2];
				tenureCount = docDetails[3];
				tenureType = docDetails[4];
			}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		obCheckListItem.setItemDesc(documentDesc);
		obCheckListItem.setParentItemID(Long.parseLong(documentID));
		obCheckListItem.setItemStatus("AWAITING");
		obCheckListItem.setStatementType(statementType);
		if(tenureCount!=null && !tenureCount.trim().isEmpty()){
			obCheckListItem.setTenureCount(Integer.parseInt(tenureCount.trim()));
		}else{
			obCheckListItem.setTenureCount(0);
		}
		obCheckListItem.setTenureType(tenureType);
		if(documentDetRequestDTO.getCpsDocumentId()!=null && !documentDetRequestDTO.getCpsDocumentId().trim().isEmpty()){
			obCheckListItem.setCpsId(documentDetRequestDTO.getCpsDocumentId().trim());
		}else{
			obCheckListItem.setCpsId("");
		}
		
		//Set Field values for updated date and updated by
		obCheckListItem.setUpdatedBy(PropertyManager.getValue("dummy_maker")); 
		obCheckListItem.setUpdatedDate(getApplicationDateFromParam()); 

		//Set Field values for approved date and approved by
		obCheckListItem.setApprovedBy(PropertyManager.getValue("dummy_checker")); 
		obCheckListItem.setApprovedDate(getApplicationDateFromParam()); 
		
		if(documentDetRequestDTO.getDocumentCategory()!=null && !documentDetRequestDTO.getDocumentCategory().trim().isEmpty()){
			docCategory = documentDetRequestDTO.getDocumentCategory().trim();
		}
		
		if(documentDetRequestDTO.getMappingId()!=null && !documentDetRequestDTO.getMappingId().trim().isEmpty()){
			mappingId = documentDetRequestDTO.getMappingId().trim();
		}
		
		if(mappingId!=null && !mappingId.trim().isEmpty()){
			try{
				if(documentDetRequestDTO.getDocumentCategory()!=null 
						&& !documentDetRequestDTO.getDocumentCategory().trim().isEmpty()){

					if("F".equalsIgnoreCase(documentDetRequestDTO.getDocumentCategory().trim())
							|| "S".equalsIgnoreCase(documentDetRequestDTO.getDocumentCategory().trim())){
						
						if("F".equalsIgnoreCase(documentDetRequestDTO.getDocumentCategory().trim())){
							securitySubTypeId = limitJdbc.getFacilityCodeByCMSLimitId(Long.parseLong(mappingId.trim()));
						}else if("S".equalsIgnoreCase(documentDetRequestDTO.getDocumentCategory().trim())){
							//get security Sub type from cms_security
							securitySubTypeId = securitySubType;
						}
					}
				}
			}catch(Exception e){
				DefaultLogger.error(this, e.getMessage());
			}
		}else{
			securitySubTypeId = securitySubType;
		}
		
		try{
			if(securitySubTypeId!=null && !securitySubTypeId.trim().isEmpty()){
				TemplateDAO templateDao = new TemplateDAO();
				masterListId = templateDao.searchMasterListId(securitySubTypeId);
			}
		}catch (Exception e) {
			DefaultLogger.error(this, e.getMessage());
			masterListId = securitySubTypeId;
		}
		
		//END
		
		//Add CheckListItem Object to Exising ArrayList to update
		modifiedCheckListItemList[0] = obCheckListItem;

		int i=1;
		if(obCheckList.getCheckListItemList()!=null && obCheckList.getCheckListItemList().length>0){
			for(ICheckListItem chkListItem : obCheckList.getCheckListItemList()){
				modifiedCheckListItemList[i] = chkListItem;
				i++;
			}
		}
		//this is for MASTERLIST_ID from CMS_DOCUMENT_MASTERLIST (On basis of combination of facility & doc_code or security and doc_code)
		if(masterListId!=null && !masterListId.trim().isEmpty()){
			obCheckList.setTemplateID(Long.parseLong(masterListId.trim()));
		}else{
			obCheckList.setTemplateID(0L);
		}
		
		if(docCategory!=null && !docCategory.trim().isEmpty()){
			obCheckList.setCheckListType(docCategory.trim().toUpperCase());
		}
		obCheckList.setCheckListItemList(modifiedCheckListItemList);
		
		//For Facility Document this field will set with facility ID ,
		//For Security Document this field will set with Security ID , NULL Otherwise
		if(mappingId!=null && !mappingId.trim().isEmpty()){
			checkListOwner.setCollateralID(Long.parseLong(mappingId.trim()));
		}else{
			checkListOwner.setCollateralID(0L);
		}
		obCheckList.setCheckListOwner(checkListOwner);
		
		//SET Location as default = INDIA >> To resolve data view issues on UI while edit operation
		
		IBookingLocation anIBookingLocation  = new OBBookingLocation();
		anIBookingLocation.setCountryCode("IN");
		obCheckList.setCheckListLocation(anIBookingLocation);
		
		//Set Field values for updated date and updated by
		obCheckList.setUpdatedBy(PropertyManager.getValue("dummy_maker")); 
		obCheckList.setUpdatedDate(getApplicationDateFromParam()); 

		//Set Field values for approved date and approved by
		obCheckList.setApprovedBy(PropertyManager.getValue("dummy_checker")); 
		obCheckList.setApprovedDate(getApplicationDateFromParam()); 
		
		return obCheckList;
	}

	
	public Map<String,Object> createMasterListRecord(DocumentsDetailRequestDTO docDetailReqDTO,ILimitJdbc limitJdbc){
		
		Map<String,Object> valuesMap = new HashMap<String, Object>();
		ITemplateTrxValue templateTrxVal = null;
		ITemplate obTemplate = new OBTemplate();
		
		DocumentDAO documentDAO = new DocumentDAO();
		
		String documentDesc = null;
		String docCategory = "";
		String mappingId = "";
		String[] docDetails = null;
		String documentID = null;
		String facilityCode = null;
		String masterListId = null;
		
		ITemplateItem[] finalTemplateItemArray = null;
		
		finalTemplateItemArray = new OBTemplateItem[1];
		
		//Add New Document Value
		ITemplateItem tempItem = new OBTemplateItem();
		
		String docCode ="";
		if(docDetailReqDTO.getDocumentCode()!=null && !docDetailReqDTO.getDocumentCode().trim().isEmpty()){
			docCode = docDetailReqDTO.getDocumentCode().trim();
		}
		
		try {
			docDetails = documentDAO.getDocumentIdAndDescByDocCode(docCode);
			if(docDetails!=null){
				documentID = docDetails[0];
				documentDesc = docDetails[1];
			}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(docDetailReqDTO.getDocumentCategory()!=null && !docDetailReqDTO.getDocumentCategory().trim().isEmpty()){
			docCategory = docDetailReqDTO.getDocumentCategory().trim();
		}
		
		IItem item  = new OBItem();
		item.setItemCode(docCode);
		item.setItemDesc(documentDesc);
		item.setItemID(Long.parseLong(documentID));
		tempItem.setItem(item);
		
		finalTemplateItemArray[0] = tempItem;
			
		if(docDetailReqDTO.getMappingId()!=null && !docDetailReqDTO.getMappingId().trim().isEmpty()){
			mappingId = docDetailReqDTO.getMappingId().trim();
		}
		
		if(mappingId!=null && !mappingId.trim().isEmpty()){
			try{
				facilityCode = limitJdbc.getFacilityCodeByCMSLimitId(Long.parseLong(mappingId));
				
				//Get MasterList_Id using Facility Code
				TemplateDAO templateDao = new TemplateDAO();

				obTemplate.setTemplateItemList(finalTemplateItemArray);
				obTemplate.setTemplateType(docCategory.trim().toUpperCase());
				obTemplate.setCollateralSubType(facilityCode);
				obTemplate.setCollateralType(facilityCode);
				
				masterListId = templateDao.searchMasterListId(facilityCode);
				
				//If MasterList_ID is not null,Get Transaction using MasterList_Id
				if(masterListId!=null && !masterListId.isEmpty()){
					ICheckListTemplateProxyManager checkListTemplateProxy = CheckListTemplateProxyManagerFactory
						.getCheckListTemplateProxyManager();
					try {
						String trxID = templateDao.getTransactionIdByMasterListId(masterListId);
						
						if(trxID!=null && !trxID.isEmpty()){
							templateTrxVal = checkListTemplateProxy.getTemplateByTrxID(trxID);
							if(templateTrxVal!=null && templateTrxVal.getTemplate()!=null){
								
								if(templateTrxVal.getTemplate().getTemplateItemList()!=null 
										&& templateTrxVal.getTemplate().getTemplateItemList().length >0){
									finalTemplateItemArray = new OBTemplateItem[templateTrxVal.getTemplate().getTemplateItemList()
									                                            .length + 1];
									finalTemplateItemArray[0] = tempItem;
									
									int i = 1;
									for(ITemplateItem tItem : templateTrxVal.getTemplate().getTemplateItemList()){
										finalTemplateItemArray[i] = tItem;
										i++;
									}
								}
								templateTrxVal.getTemplate().setTemplateItemList(finalTemplateItemArray);
							}
						}
					}catch (CheckListTemplateException e) {
						e.printStackTrace();
						DefaultLogger.error(this, e.getMessage());
					}
				}
			}catch(Exception e){
				DefaultLogger.error(this, e.getMessage());
			}
		}
			
		if(templateTrxVal==null){
			valuesMap.put("obTemplate",obTemplate);
		}else{
			valuesMap.put("templateTrxVal",templateTrxVal);
		}
		
		return valuesMap;
	}

	public ActionErrors validateData(DocumentsRequestDTO  documentsRequestDTO,ILimitJdbc limitJdbc,ISecurityDao securityDAO){
		
		ActionErrors errors = new ActionErrors();
		DocumentDAO documentDAO = new DocumentDAO();
		CheckListDAO checkListDAO = new CheckListDAO();
		String camId = null;
		
		//Main Fields 
		if(StringUtils.isBlank(documentsRequestDTO.getPartyId())){
			errors.add("partyId", new ActionMessage("error.string.mainfields.mandatory"));
		}else{ 
			if(documentsRequestDTO.getPartyId().trim().length()>10){
				errors.add("partyIdLengthError", new ActionMessage("error.string.wsdl.partyId.length"));
			}
			
			//To Do : Validate Party Exists in the system or not
			try{
				ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
				//Fetching Party Details and set to the context 
				ICMSCustomer cust = custProxy.getCustomerByCIFSource(documentsRequestDTO.getPartyId().trim(),null);
				if(cust==null || "".equals(cust)){
					errors.add("partyId",new ActionMessage("error.invalid.field.value"));
				}else{
					try{
						camId = limitJdbc.getCamByLlpLeId(documentsRequestDTO.getPartyId().trim());
					}catch (Exception e) {
						DefaultLogger.error(this, e.getMessage());
					}
				}
			}catch(CustomerException e){
				errors.add("partyId",new ActionMessage("error.invalid.field.value"));
			}
		}
		
		
		if(StringUtils.isBlank(documentsRequestDTO.getWsConsumer())){
			errors.add("wsConsumer", new ActionMessage("error.string.mainfields.mandatory"));
		}else{
			if(documentsRequestDTO.getWsConsumer().trim().length()>50){
				errors.add("wsConsumerLengthError", new ActionMessage("error.string.wsdl.wsconsumer.length"));
			}
		}
	
		if(documentsRequestDTO.getDocumentList()!= null && documentsRequestDTO.getDocumentList().size()>0){
			
			//Scenarios Covered For Validations :::
			
			//1. Validate DocumentCode exists in the system or not - CMS_DOCUMENT_GLOBALLIST
			//2. Validate DocumentCode with document category exists in the system or not , else 'Invalid value for Document Category message should get displayed'
			//3. Validate DocumentCode with Document Category , If Mapping Id invalid/ or not of mentioned document category , system shd display proper error message.
			//4. Validate CPSID, If record exists with mentioned CPS id then system should validate appropriately.
			
			//Details Fields
			int counter = 1;
			List<String> cpsIdList = new ArrayList<String>();
			
			for(DocumentsDetailRequestDTO docDetReqDTO : documentsRequestDTO.getDocumentList()){
				
				//Document Code
				Boolean validDataForDocCode = false;
				if(StringUtils.isBlank(docDetReqDTO.getDocumentCode())){
					errors.add("documentCode::"+counter, new ActionMessage("error.string.mandatory"));
				}else{
					if(docDetReqDTO.getDocumentCode().trim().length()>20){
						errors.add("documentCodeLengthError::"+counter, new ActionMessage("error.string.wsdl.documentCode.length",docDetReqDTO.getDocumentCode().trim()));
					}else{
						boolean containsSpacialChars = ASSTValidator.
									isValidAlphaNumStringWithoutSpace(docDetReqDTO.getDocumentCode().trim());
						if (containsSpacialChars){
							errors.add("specialCharacterDocumentCodeError::"+counter,
									new ActionMessage("error.string.wsdl.invalidCharacter",docDetReqDTO.getDocumentCode().trim()));
						}else{
							validDataForDocCode = true;
						}
					}
				}
				
				//Document Category
				if(StringUtils.isBlank(docDetReqDTO.getDocumentCategory())){
					errors.add("documentCategory::"+counter, new ActionMessage("error.string.mandatory"));
				}else{
					if(docDetReqDTO.getDocumentCategory().trim().length()>3){
						errors.add("documentCategoryLengthError::"+counter, new ActionMessage("error.string.wsdl.documentCategory.length",docDetReqDTO.getDocumentCode().trim(),": "+docDetReqDTO.getDocumentCategory().trim()));
					}else{
						if(!(DocumentCategoryEnum.F.name().equalsIgnoreCase(docDetReqDTO.getDocumentCategory().trim())
								|| DocumentCategoryEnum.S.name().equalsIgnoreCase(docDetReqDTO.getDocumentCategory().trim())
								|| DocumentCategoryEnum.CAM.name().equalsIgnoreCase(docDetReqDTO.getDocumentCategory().trim())
								|| DocumentCategoryEnum.O.name().equalsIgnoreCase(docDetReqDTO.getDocumentCategory().trim())
								|| DocumentCategoryEnum.REC.name().equalsIgnoreCase(docDetReqDTO.getDocumentCategory().trim()))){
							errors.add("documentCategoryInvalidValuesError::"+counter, new ActionMessage("error.string.wsdl.documentCategory.invalid.values",docDetReqDTO.getDocumentCode().trim(),": "+docDetReqDTO.getDocumentCategory().trim()));
						}
					}
				}
				
				try {
					if(docDetReqDTO.getDocumentCode()!=null && !docDetReqDTO.getDocumentCode().trim().isEmpty() && validDataForDocCode){
						if(!(documentDAO.checkDocumentAvailability(docDetReqDTO.getDocumentCode().trim(),null))){
							errors.add("documentCode::"+counter, new ActionMessage("error.string.wsdl.documentCode.notexist",docDetReqDTO.getDocumentCode().trim(),": "+docDetReqDTO.getDocumentCategory().trim()));
						}else if(!(documentDAO.checkDocumentAvailability(docDetReqDTO.getDocumentCode().trim(),docDetReqDTO.getDocumentCategory().trim()))){
							errors.add("documentCategory::"+counter, new ActionMessage("error.string.wsdl.documentCategory.notexist",docDetReqDTO.getDocumentCode().trim(),": "+docDetReqDTO.getDocumentCategory().trim()));
						}
					}
				} catch (DBConnectionException e) {
					DefaultLogger.error(this, e.getMessage());
					e.printStackTrace();
				} catch (SQLException e) {
					DefaultLogger.error(this, e.getMessage());
					e.printStackTrace();
				}
				
				//Document Mapping ID
				if(StringUtils.isBlank(docDetReqDTO.getMappingId()) 
						&& docDetReqDTO.getDocumentCategory() !=null && !docDetReqDTO.getDocumentCategory().trim().isEmpty()
						&& (DocumentCategoryEnum.F.name().equalsIgnoreCase(docDetReqDTO.getDocumentCategory().trim())
								|| DocumentCategoryEnum.S.name().equalsIgnoreCase(docDetReqDTO.getDocumentCategory().trim()))){
					errors.add("mappingId::"+counter, new ActionMessage("error.string.mandatory"));
				}else{
					if(docDetReqDTO.getMappingId().trim().length()>19){
						errors.add("mappingIdLengthError::"+counter, new ActionMessage("error.string.wsdl.mappingId.length",docDetReqDTO.getDocumentCode().trim(),": "+docDetReqDTO.getDocumentCategory().trim(),docDetReqDTO.getMappingId().trim()));
					}else{
						if(!StringUtils.isNumeric(docDetReqDTO.getMappingId().trim())){
							errors.add("mappingIdNotNumeric::"+counter, new ActionMessage("error.string.wsdl.mappingId.notnumeric.values",docDetReqDTO.getDocumentCode().trim(),": "+docDetReqDTO.getDocumentCategory().trim(),docDetReqDTO.getMappingId().trim()));
						}
					}
				}
				
				//Document CpsDocumentId
				if(StringUtils.isBlank(docDetReqDTO.getCpsDocumentId())){
					errors.add("cpsDocumentId::"+counter, new ActionMessage("error.string.mandatory"));
				}else{
					if(docDetReqDTO.getCpsDocumentId().trim().length()>40){
						errors.add("cpsDocumentILengthError::"+counter, new ActionMessage("error.string.wsdl.cpsDocumentId.length",docDetReqDTO.getCpsDocumentId().trim()));
					}
					
					if(checkListDAO.getChkListByCPSId(docDetReqDTO.getCpsDocumentId().trim())){
						errors.add("cpsDocumentId::"+counter, new ActionMessage("error.string.wsdl.cpsid.already.exist",docDetReqDTO.getDocumentCode().trim(),": "+docDetReqDTO.getDocumentCategory().trim(),docDetReqDTO.getCpsDocumentId().trim()));
					}else{
						if(cpsIdList!=null){
							if(!cpsIdList.isEmpty()){
								if(cpsIdList.contains(docDetReqDTO.getCpsDocumentId().trim())){
									errors.add("cpsDocumentId::"+counter, new ActionMessage("error.string.wsdl.cpsid.duplicate.infile",docDetReqDTO.getDocumentCode().trim(),": "+docDetReqDTO.getDocumentCategory().trim(),docDetReqDTO.getCpsDocumentId().trim()));
								}else{
									cpsIdList.add(docDetReqDTO.getCpsDocumentId().trim());
								}
							}else{
								cpsIdList.add(docDetReqDTO.getCpsDocumentId().trim());
							}
						}
					}
				}
				
				if(docDetReqDTO.getDocumentCategory()!=null){
					Object obj = null;
					if(docDetReqDTO.getMappingId()!=null && !docDetReqDTO.getMappingId().trim().isEmpty()
							&& StringUtils.isNumeric(docDetReqDTO.getMappingId().trim())){
						if(DocumentCategoryEnum.F.name().equalsIgnoreCase(docDetReqDTO.getDocumentCategory().trim())){
							obj = limitJdbc.getFacilityByMappingId(new Long(docDetReqDTO.getMappingId().trim()));
							if(obj==null || "0".equals(obj)){
								errors.add("mappingId::"+counter, new ActionMessage("error.string.wsdl.facility.mappingid.invalid.value",docDetReqDTO.getDocumentCode().trim(),": "+docDetReqDTO.getDocumentCategory().trim(),docDetReqDTO.getMappingId().trim()));
							}
						}else if(DocumentCategoryEnum.S.name().equalsIgnoreCase(docDetReqDTO.getDocumentCategory().trim())){
							obj = securityDAO.findSecurityByMappingId(new Long(docDetReqDTO.getMappingId().trim()));
							if(obj==null){
								errors.add("mappingId::"+counter, new ActionMessage("error.string.wsdl.security.mappingid.invalid.value",docDetReqDTO.getDocumentCode().trim(),": "+docDetReqDTO.getDocumentCategory().trim(),docDetReqDTO.getMappingId().trim()));
							}
						}
					}
				}
				

				//5. Validate Document has been already added to Specific Facility/Security/CAM/Recurrent/Others. 
				// If yes, display error message
				//Scenario : Facility : Document will be added to MasterListItem as well as CheckListItem
						// 	A. If already added in MasterListItem then Add it to CheckListItem only.
						//  B. If already added in MasterListItem and CheckListItem, Error message should get displayed.
							//	   Else System should allow to add document.
				
				if(camId!=null && !camId.trim().isEmpty()
						&& docDetReqDTO.getMappingId()!=null && !docDetReqDTO.getMappingId().trim().isEmpty()
						&& docDetReqDTO.getDocumentCategory()!=null && !docDetReqDTO.getDocumentCategory().trim().isEmpty()
						&& docDetReqDTO.getDocumentCode()!=null && !docDetReqDTO.getDocumentCode().trim().isEmpty()){
					
					try{
						if(checkListDAO.isDocumentFoundInCheckListItem(camId.trim(),docDetReqDTO.getMappingId().trim(),docDetReqDTO.getDocumentCategory().trim().toUpperCase(),docDetReqDTO.getDocumentCode().trim())){
							errors.add("documentCode::"+counter,new ActionMessage("error.string.wsdl.document.alreadyExists",docDetReqDTO.getDocumentCode().trim(),docDetReqDTO.getMappingId().trim()));
						}
					}catch (Exception e) {
						DefaultLogger.error(this, e.getMessage());
					}
					
				}
				
				//Validation for Existing CAM/Other/Recurrent Document in CheckList Item
				
				if((docDetReqDTO.getMappingId()==null || docDetReqDTO.getMappingId().trim().isEmpty())
						&& camId!=null && !camId.trim().isEmpty()
						&& docDetReqDTO.getDocumentCategory()!=null && !docDetReqDTO.getDocumentCategory().trim().isEmpty()
						&& docDetReqDTO.getDocumentCode()!=null && !docDetReqDTO.getDocumentCode().trim().isEmpty()){
					
					if(checkListDAO.isDocumentFoundInCheckListItem(camId.trim(),"0",docDetReqDTO.getDocumentCategory().trim().toUpperCase(),docDetReqDTO.getDocumentCode().trim())){
						String docCategory = "";
						if(DocumentCategoryEnum.CAM.name().equalsIgnoreCase(docDetReqDTO.getDocumentCategory().trim().toUpperCase())){
							docCategory = DocumentCategoryEnum.CAM.name(); 
						}else if(DocumentCategoryEnum.O.name().equalsIgnoreCase(docDetReqDTO.getDocumentCategory().trim().toUpperCase())){
							docCategory = "Others"; 
						}else if(DocumentCategoryEnum.REC.name().equalsIgnoreCase(docDetReqDTO.getDocumentCategory().trim().toUpperCase())){
							docCategory = "Recurrent"; 
						}
						
						errors.add("documentCode::"+counter,new ActionMessage("error.string.wsdl.document.nullmappingID.alreadyExists",docDetReqDTO.getDocumentCode().trim(),docCategory.trim()));
					}
				}
				
				counter++;
			}
		}else{
			errors.add("documentList", new ActionMessage("error.string.mandatory"));
		}
		
		return errors;
	}
	
	public ActionErrors readDocumentVaidations(DocumentsReadRequestDTO  docReadReqDTO,ILimitJdbc limitJdbc){
		
		ActionErrors errors = new ActionErrors(); 
		
		//Main Fields 
		if(StringUtils.isBlank(docReadReqDTO.getPartyId())){
			errors.add("partyId", new ActionMessage("error.string.mainfields.mandatory"));
		}else{ 
			if(docReadReqDTO.getPartyId().trim().length()>10){
				errors.add("partyIdLengthError", new ActionMessage("error.string.wsdl.partyId.length"));
			}
			
			//To Do : Validate Party Exists in the system or not
			try{
				ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
				//Fetching Party Details and set to the context 
				ICMSCustomer cust = custProxy.getCustomerByCIFSource(docReadReqDTO.getPartyId().trim(),null);
				if(cust==null || "".equals(cust)){
					errors.add("partyId",new ActionMessage("error.invalid.field.value"));
				}
			}catch(CustomerException e){
				errors.add("partyId",new ActionMessage("error.invalid.field.value"));
			}
		}
		
		
		if(StringUtils.isBlank(docReadReqDTO.getWsConsumer())){
			errors.add("wsConsumer", new ActionMessage("error.string.mainfields.mandatory"));
		}else{
			if(docReadReqDTO.getWsConsumer().trim().length()>50){
				errors.add("wsConsumerLengthError", new ActionMessage("error.string.wsdl.wsconsumer.length"));
			}
		}
		
		return errors;
	}
	
	private Date getApplicationDateFromParam(){
		Date applicationDate = new Date();
		try{
			IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			for(int i=0;i<generalParamEntries.length;i++){
				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
					applicationDate=new Date(generalParamEntries[i].getParamValue());
				}
			}
			Date d = DateUtil.getDate();
			applicationDate.setHours(d.getHours());
			applicationDate.setMinutes(d.getMinutes());
			applicationDate.setSeconds(d.getSeconds());
		}catch (Exception e) {
			DefaultLogger.error(this, "Exception encountered in method getApplicationDateFromParam()===== "+e.getMessage());
			applicationDate = DateUtil.getDate(); 
		}
		return applicationDate;
	}
	
}
