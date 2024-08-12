package com.integrosys.cms.ui.image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import javax.sql.rowset.serial.SerialArray;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.image.proxy.ImageUploadCommand;
import com.integrosys.cms.app.image.trx.IImageUploadTrxValue;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.OBCommonUser;

/**
 * This class defines the operations that are provided by a image upload
 * @author $Govind: S $<br>
 * @version $Revision: 0 $
 * @since $Date: 2011/03/03 11:32:23 $ Tag: $Name: $
 */
public class CreateImageUploadAddCommand extends ImageUploadCommand {

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		DefaultLogger.debug(this, "******** getParameterDescriptor Call: ");
		return (new String[][] {
					{ "customerName","java.lang.String",REQUEST_SCOPE },
					{ "startIndex", "java.lang.String", REQUEST_SCOPE },
					{ "obImageUploadAddList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "legalName","java.lang.String",REQUEST_SCOPE },
					{ "ImageUploadAddObj", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", FORM_SCOPE},
					{ "ImageUploadAddObjSession", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", SERVICE_SCOPE},
					{ "event", "java.lang.String", REQUEST_SCOPE },
					{ "fromAmt","java.lang.String",REQUEST_SCOPE },
					{ "typeOfDocVal","java.lang.String",REQUEST_SCOPE },
					{ "docNameVal","java.lang.String",REQUEST_SCOPE },
					{ "docDatetypeVal","java.lang.String",REQUEST_SCOPE },
					{ "docToAmt","java.lang.String",REQUEST_SCOPE },
					{ "docFrmDateVal","java.lang.String",REQUEST_SCOPE },
					{ "docToDateval","java.lang.String",REQUEST_SCOPE },
					{ IGlobalConstant.GLOBAL_LOS_USER, "com.integrosys.component.user.app.bus.ICommonUser",GLOBAL_SCOPE},
					{"reset", "java.lang.String", REQUEST_SCOPE}
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
		DefaultLogger.debug(this, "********  getResultDescriptor Call: ");
		return (new String[][] {
					{ "ImageUploadAddObj", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", FORM_SCOPE },
					{ "obImageUploadAddList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "startIndex", "java.lang.String", REQUEST_SCOPE },
					{ "customerName","java.lang.String",REQUEST_SCOPE },
					{ "legalName","java.lang.String",REQUEST_SCOPE },
					{ "ImageUploadAddObjSession", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", SERVICE_SCOPE},
					{ "subfolderNameList", "java.util.List", REQUEST_SCOPE },
					{ "docNameList", "java.util.List", REQUEST_SCOPE },
					{ "searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
					{ "event", "java.lang.String", REQUEST_SCOPE },
					{ "tagDetailList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "custImageListWithTag", "java.util.ArrayList", SERVICE_SCOPE },
					/*{ "obImageUploadAddList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "obImageUploadAddList", "java.util.ArrayList", REQUEST_SCOPE },
					{ "event", "java.lang.String", REQUEST_SCOPE },
					{ "imageTrxObj", "com.integrosys.cms.app.image.trx.IImageUploadTrxValue", SERVICE_SCOPE }*/
					{"bankList","java.util.List",SERVICE_SCOPE},
					{ "custIdList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "facilityCodeList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "otherDocList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "securityNameIdList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "otherSecDocList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "typeOfDocList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "statementDocList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "camNumberList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "camDocList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "otherMasterDocList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "facDocCode", "java.lang.String", REQUEST_SCOPE },
					{ "secDocCode", "java.lang.String", REQUEST_SCOPE },
					{ "custIdList", "java.util.ArrayList", REQUEST_SCOPE },
					{ "facilityCodeList", "java.util.ArrayList", REQUEST_SCOPE },
					{ "otherDocList", "java.util.ArrayList", REQUEST_SCOPE },
					{ "securityNameIdList", "java.util.ArrayList", REQUEST_SCOPE },
					{ "otherSecDocList", "java.util.ArrayList", REQUEST_SCOPE },
					{ "typeOfDocList", "java.util.ArrayList", REQUEST_SCOPE },
					{ "statementDocList", "java.util.ArrayList", REQUEST_SCOPE },
					{ "camNumberList", "java.util.ArrayList", REQUEST_SCOPE },
					{ "camDocList", "java.util.ArrayList", REQUEST_SCOPE },
					{ "categoryAfterImageView", "java.util.ArrayList", REQUEST_SCOPE },
					{ "otherMasterDocList", "java.util.ArrayList", REQUEST_SCOPE },
					{ "categoryAfterImageView", "java.util.ArrayList", SERVICE_SCOPE },
				});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @throws CommandProcessingException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		DefaultLogger.debug(this, "Enter in doExecute()");
		HashMap result = new HashMap();
		HashMap resultSet = new HashMap();
		String event=(String) map.get("event");
		String startIndex=(String) map.get("startIndex");
		result.put("startIndex", startIndex);
		IImageUploadAdd imageUploadAdd = (IImageUploadAdd) map.get("ImageUploadAddObjSession");
		ImageTagDaoImpl imageTagDaoImpl=new ImageTagDaoImpl();

		IImageUploadAdd imageUploaded = (IImageUploadAdd) map.get("ImageUploadAddObj");
		OBCommonUser globalUser = (OBCommonUser) map.get(IGlobalConstant.GLOBAL_LOS_USER);
		
		String imgUploadMethod = PropertyManager.getValue("image.upload.method","jupload");
		System.out.println("CreateImageUploadAddCommand.java Event ==>"+event);
		if ("save_image_upload".equals(event)&& imgUploadMethod.equalsIgnoreCase("zip")) {
			imageUploadAdd.setImageFile(imageUploaded.getImageFile());
			new ZipProcessorHelper().processZipFile(imageUploadAdd, globalUser.getUserID());
		}
		
		String custId= (String) map.get("legalName");
		String custName=(String) map.get("customerName");
		String fromAmt=(String) map.get("fromAmt");
		String typeOfDocVal = (String) map.get("typeOfDocVal");
        String docNameVal = (String) map.get("docNameVal");
        String docDatetypeVal = (String) map.get("docDatetypeVal");
        String docToAmt = (String) map.get("docToAmt");
        String docFrmDateVal = (String) map.get("docFrmDateVal");
        String docToDateval = (String) map.get("docToDateval");
        String reset = (String) map.get("reset");
		
//		System.out.println("CreateImageUploadAddCommand.java imageUploadAdd is ==> "+imageUploadAdd);
		if((imageUploadAdd==null || "Y".equals(reset))&& custName!=null){
			String categoryCode = imageTagDaoImpl.getCategorycode(imageUploaded.getCategory());
			String categoryAfterImageView = imageUploaded.getCategory();
//			System.out.println("CreateImageUploadAddCommand.java imageUploadAdd is null ");
			imageUploadAdd= new OBImageUploadAdd();
			imageUploadAdd.setCustId(custId);
			imageUploadAdd.setCustName(custName.replace('~', ' '));
//			imageUploadAdd.setCategory(imageUploaded.getCategory());
			imageUploadAdd.setCategory(categoryCode);
			if("".equals(categoryCode) || categoryCode == null) {
				imageUploadAdd.setCategory(imageUploaded.getCategory());
			}

			imageUploadAdd.setSubfolderName(imageUploaded.getSubfolderName());
			imageUploadAdd.setDocumentName(imageUploaded.getDocumentName());
			//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
			imageUploadAdd.setBank(imageUploaded.getBank());
			//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
			imageUploadAdd.setFacilityName(imageUploaded.getFacilityName());
			imageUploadAdd.setTypeOfDocument(imageUploaded.getTypeOfDocument());
			
			if("view_uploaded_image_listing".equals(event) && !"".equals(categoryCode) && categoryCode != null) {
			imageUploadAdd.setTypeOfDocument(categoryAfterImageView);
			}
			
			imageUploadAdd.setFacilityDocName(imageUploaded.getFacilityDocName());
			imageUploadAdd.setOtherDocName(imageUploaded.getOtherDocName());
			imageUploadAdd.setSecurityNameId(imageUploaded.getSecurityNameId());
			imageUploadAdd.setSecurityDocName(imageUploaded.getSecurityDocName());
			imageUploadAdd.setOtherSecDocName(imageUploaded.getOtherSecDocName());
			imageUploadAdd.setHasCam(imageUploaded.getHasCam());
			imageUploadAdd.setSecurityIdi(imageUploaded.getSecurityIdi());
			imageUploadAdd.setSubTypeSecurity(imageUploaded.getSubTypeSecurity());
			imageUploadAdd.setStatementTyped(imageUploaded.getStatementTyped());
			imageUploadAdd.setCamDocName(imageUploaded.getCamDocName());
			imageUploadAdd.setStatementDocName(imageUploaded.getStatementDocName());
			imageUploadAdd.setOthersDocsName(imageUploaded.getOthersDocsName());
			
			result.put("categoryAfterImageView", categoryAfterImageView);
			
		}

		try {
			ArrayList custImageList=new ArrayList();
			ArrayList custImageListWithTag=new ArrayList();
			
			ArrayList obImageUploadAddList=(ArrayList)map.get("obImageUploadAddList");
			if("view_uploaded_image_listing".equals(event) || "remove_uploaded_image_listing".equals(event)){
				obImageUploadAddList = new ArrayList();
			}
			
			if(obImageUploadAddList==null || (obImageUploadAddList!=null && obImageUploadAddList.size()==0)){
			if("remove_uploaded_image_listing".equals(event)){
				custImageList = (ArrayList) getImageUploadProxyManager().getCustRemoveImageList(imageUploadAdd);
				
				custImageListWithTag = getImageUploadProxyManager().getImageIdWithTagList(custImageList);
				
			}else{
				
				custImageList = (ArrayList) getImageUploadProxyManager().getCustImageList(imageUploadAdd);
//				System.out.println("CUSTIMAGELIST ==== "+custImageList);
			}
			}else{
				if("view_uploaded_image_listing_search".equals(event)){
					if("remove_uploaded_image_listing".equals(event)){
						custImageList = (ArrayList) getImageUploadProxyManager().getCustRemoveImageList(imageUploadAdd);
						
						custImageListWithTag = getImageUploadProxyManager().getImageIdWithTagList(custImageList);
					}else{
						
						custImageList = (ArrayList) getImageUploadProxyManager().getCustImageList(imageUploadAdd);
						
					}
					
				}else{
				
				custImageList=obImageUploadAddList;
				}
			}
//			System.out.println("CreateImageUploadAddCommand  event === "+event);
			IImageUploadTrxValue trxValue=null;
			
			if(("view_uploaded_image_listing_search".equals(event) || "view_uploaded_image_listing_search_page".equals(event)) && ("".equals(imageUploadAdd.getCategory()) || imageUploadAdd.getCategory() == null)) {
				OBImageUploadAdd obAdd = new OBImageUploadAdd();
				String typeOfDoc = "";
				for(int i=0;i<custImageList.size();i++) {
					obAdd = (OBImageUploadAdd) custImageList.get(i);
					typeOfDoc = imageTagDaoImpl.getEntryNameFromCode(obAdd.getCategory());
					obAdd.setTypeOfDocument(typeOfDoc);
				}
			}
			else if("view_uploaded_image_listing_search".equals(event) || "view_uploaded_image_listing_search_page".equals(event)) {
				OBImageUploadAdd obAdd = new OBImageUploadAdd();
				String typeOfDoc = imageUploadAdd.getTypeOfDocument();
				if(typeOfDoc == null) {
					typeOfDoc = "";
				}
				for(int i=0;i<custImageList.size();i++) {
					obAdd = (OBImageUploadAdd) custImageList.get(i);
					obAdd.setTypeOfDocument(typeOfDoc);
				}
			}
			
			if(("view_uploaded_image_listing".equals(event) && !custImageList.isEmpty()) || "save_image_upload".equals(event) || "remove_uploaded_image_listing".equals(event)) {
				OBImageUploadAdd obAdd = new OBImageUploadAdd();
				String typeOfDoc = "";
				for(int i=0;i<custImageList.size();i++) {
					obAdd = (OBImageUploadAdd) custImageList.get(i);
					typeOfDoc = imageTagDaoImpl.getEntryNameFromCode(obAdd.getCategory());
					obAdd.setTypeOfDocument(typeOfDoc);
				}
			}
			
			//trxValue = (OBImageUploadTrxValue) getImageUploadProxyManager().getImageUploadTrxValue(imageUploadAdd.getCustId());
			//DefaultLogger.debug(this, "trxValue@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@=" + trxValue);
			
			//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
			List bankList=new ArrayList();
		//	if("view_uploaded_image_listing".equals(event)){
			List<String> systemBankBranchName=new ArrayList<String>();
			List<String> otherBankBranchName=new ArrayList<String>();
			List<String> allBankBranchName=new ArrayList<String>();
			List<String> ifscBankBranchName=new ArrayList<String>();
		//	ImageTagMapForm imageTagMapForm=new ImageTagMapForm();
			
//			ImageTagDaoImpl imageTagDaoImpl=new ImageTagDaoImpl();
			String systemBankId = imageTagDaoImpl.getSystemBankId(custId);
			DefaultLogger.debug(this,"systemBankId:"+systemBankId);
			String otherBankId = imageTagDaoImpl.getOtherBankId(custId);
			DefaultLogger.debug(this,"otherBankId:"+otherBankId);
			
			if(null!=systemBankId && !systemBankId.isEmpty()){
				if(systemBankId.lastIndexOf(",")!=-1){
				systemBankId=systemBankId.substring(0, systemBankId.lastIndexOf(","));
				}
				 systemBankBranchName = imageTagDaoImpl.getSystemBankBranchName(systemBankId);
			}
			if(null!=otherBankId && !otherBankId.isEmpty()){
				if(otherBankId.lastIndexOf(",")!=-1){
					otherBankId=otherBankId.substring(0, otherBankId.lastIndexOf(","));
				}
				 otherBankBranchName = imageTagDaoImpl.getOtherBankBranchName(otherBankId);
			}
			
			ifscBankBranchName = imageTagDaoImpl.getIFSCBankBranchName(custId);
			
//			if ("save_image_upload".equals(event) && imageUploadAdd.getFacilityName() != null) {
//				String nameOfFacility = imageTagDaoImpl.getFacilityNameFromFacilityCode(imageUploadAdd.getFacilityName());
//				imageUploadAdd.setFacilityName(nameOfFacility);
//			}
			
			bankList.addAll(imageTagDaoImpl.populateBankList(systemBankBranchName));
			bankList.addAll(imageTagDaoImpl.populateBankList(otherBankBranchName));
			bankList.addAll(imageTagDaoImpl.populateBankList(ifscBankBranchName));
	//		}
		//	imageTagMapForm.setBankList(bankList);
		//	result.put("bankList", imageTagMapForm.getBankList());
			result.put("bankList", bankList);
			//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
			result.put("obImageUploadAddList", custImageList);
			result.put("tagDetailList", custImageList);
			String newFacName="";
			for(int i=0;i<custImageList.size();i++) {
				OBImageUploadAdd nameOfFac=(OBImageUploadAdd) custImageList.get(i);
				newFacName=nameOfFac.getFacilityName();
//				System.out.println("Facility Name in create Imange command === "+ newFacName); 
				if(newFacName != null) {
					String partyId = nameOfFac.getCustId();
					String facilityNameFromFCode=imageTagDaoImpl.getFacilityNameFromFacilityCode(newFacName,partyId);
//					System.out.println("facilityNameFromFCode in create Imange command === "+ facilityNameFromFCode);
					nameOfFac.setFacilityName(facilityNameFromFCode);
//					System.out.println("facility Name from List by object 11111111 ===== "+nameOfFac.getFacilityName());
				}
			}
			
			
			SearchResult searchResult= new SearchResult(0, custImageList.size(), custImageList.size(), custImageList);
			result.put("searchResult", searchResult);
			result.put("legalName", custId == null ? imageUploadAdd.getCustId() : custId);
			result.put("customerName", custName);
			result.put("fromAmt", fromAmt);
			result.put("typeOfDocVal", typeOfDocVal);
			result.put("docNameVal", docNameVal);
			result.put("docDatetypeVal", docDatetypeVal);
			result.put("docToAmt", docToAmt);
			result.put("docFrmDateVal", docFrmDateVal);
			result.put("docToDateval", docToDateval);
			//added by anil to reinitialize session obj
			
			result.put("ImageUploadAddObjSession", imageUploadAdd);
			result.put("event", event);
			//result.put("imageTrxObj", trxValue);
			//Added by Anil for mapping the ob to form
			result.put("ImageUploadAddObj", imageUploadAdd);
			
			result.put("custImageListWithTag", custImageListWithTag);
			
			List<String> custIdList=new ArrayList<String>();
			custIdList = imageTagDaoImpl.getFacilityNames(custId);
			
			List<String> facilityCodeList=new ArrayList<String>();
			facilityCodeList = imageTagDaoImpl.getFacilityCodes(custId);
			
			List<String> otherDocList=new ArrayList<String>();
			otherDocList = imageTagDaoImpl.getOtherDocumentList();
			
			List<String> securityNameIdList=new ArrayList<String>();
			securityNameIdList = imageTagDaoImpl.getSecurityNameIds(custId);
			
			List<String> otherSecDocList=new ArrayList<String>();
			otherSecDocList = imageTagDaoImpl.getSecurityOtherDocumentList ();
			
			List<String> typeOfDocList=new ArrayList<String>();
			typeOfDocList = imageTagDaoImpl.getTypeOfDocumentList ();
			
			List<String> statementDocList=new ArrayList<String>();
			statementDocList = imageTagDaoImpl.getStatementDocumentList ();
			
			List<String> camNumberList=new ArrayList<String>();
			camNumberList = imageTagDaoImpl.getcamNumberList (custId);
			
			List<String> camDocList=new ArrayList<String>();
			camDocList = imageTagDaoImpl.getCamDocumentList ();
			
			List<String> otherMasterDocList=new ArrayList<String>();
			otherMasterDocList = imageTagDaoImpl.getOtherMasterDocumentList ();
			
			
			String facDocCode = imageUploaded.getFacilityName();
			String secDocCode = imageUploaded.getSecurityNameId();
			result.put("facDocCode", facDocCode);
			result.put("secDocCode", secDocCode);
			result.put("custIdList", custIdList);
			result.put("facilityCodeList", facilityCodeList);
			result.put("otherDocList", otherDocList);
			result.put("securityNameIdList", securityNameIdList);
			result.put("otherSecDocList", otherSecDocList);
			result.put("typeOfDocList", typeOfDocList);
			result.put("statementDocList", statementDocList);
			result.put("camNumberList", camNumberList);
			result.put("camDocList", camDocList);
			result.put("otherMasterDocList", otherMasterDocList);
			
			
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			DefaultLogger.error(this, e);
			CommandProcessingException cpe = new CommandProcessingException("failed to get customer image list");
			cpe.initCause(e);
			throw cpe;
		}
		resultSet.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		DefaultLogger.debug(this, "Going out of doExecute()");
		return resultSet;
	}
}
