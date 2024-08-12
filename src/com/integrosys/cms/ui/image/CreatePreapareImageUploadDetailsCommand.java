package com.integrosys.cms.ui.image;

/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/CreateSampleTestCommand.java,v 1.3 2004/07/08 12:32:45 jtan Exp $
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.image.proxy.ImageUploadCommand;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;


/**
 * This command prepares the image upload details
 * 
 * @author $Name $<br>
 * @version $Revision: 0 $
 * @since $Date:$ Tag: $Name: $
 */

public class CreatePreapareImageUploadDetailsCommand extends ImageUploadCommand {

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				
				{ "customerName","java.lang.String",REQUEST_SCOPE },
				{ "legalName","java.lang.String",REQUEST_SCOPE },
				{ "fromAmt","java.lang.String",REQUEST_SCOPE },
				{ "typeOfDocVal","java.lang.String",REQUEST_SCOPE },
				{ "docNameVal","java.lang.String",REQUEST_SCOPE },
				{ "docDatetypeVal","java.lang.String",REQUEST_SCOPE },
				{ "docToAmt","java.lang.String",REQUEST_SCOPE },
				{ "docFrmDateVal","java.lang.String",REQUEST_SCOPE },
				{ "docToDateval","java.lang.String",REQUEST_SCOPE },
				{ "ImageUploadAddObj", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", FORM_SCOPE },
				 });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "ImageUploadAddObj", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", FORM_SCOPE },
				{ "bankList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "custIdList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "facilityCodeList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "otherDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "securityNameIdList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "otherSecDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "typeOfDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "statementDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "camNumberList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "camDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "otherMasterDocList", "java.util.ArrayList", REQUEST_SCOPE },
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
				
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
     * @throws CommandValidationException on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,CommandValidationException {
		DefaultLogger.debug(this, "Enter in doExecute()");
//		String indicator = (String) map.get("indicator");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		
		String customerName=(String) map.get("customerName");
		String legalName=(String) map.get("legalName");
		IImageUploadAdd ob=(IImageUploadAdd) map.get("ImageUploadAddObj");
		String fromAmt=(String) map.get("fromAmt");
		
		 String typeOfDocVal = (String) map.get("typeOfDocVal");
         String docNameVal = (String) map.get("docNameVal");
         String docDatetypeVal = (String) map.get("docDatetypeVal");
         String docToAmt = (String) map.get("docToAmt");
         String docFrmDateVal = (String) map.get("docFrmDateVal");
         String docToDateval = (String) map.get("docToDateval");
         
		
		System.out.println("fromAmt=>"+fromAmt);
		if(customerName!=null && legalName!=null){
			ob.setCustId(legalName);
			//customerName = customerName.replace('~', ' ');
			//customerName=customerName.replace('*','\'');
			ob.setCustName(customerName);
		}
		
		DefaultLogger.debug(this, "=======================================================");
		//DefaultLogger.debug(this, "CustomerName				:"+customerName);
		//DefaultLogger.debug(this, "legalName        		:"+legalName);
		DefaultLogger.debug(this, "IImageUploadAdd ob       :"+ob);
		DefaultLogger.debug(this, "=======================================================");
		
		result.put("customerName", customerName);
		result.put("legalName", legalName);
		result.put("ImageUploadAddObj", ob);
		result.put("fromAmt", fromAmt);
		result.put("typeOfDocVal", typeOfDocVal);
		result.put("docNameVal", docNameVal);
		result.put("docDatetypeVal", docDatetypeVal);
		result.put("docToAmt", docToAmt);
		result.put("docFrmDateVal", docFrmDateVal);
		result.put("docToDateval", docToDateval);
		
		//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
		String custId= null!=legalName ? legalName : ob.getCustId();
		
		List<String> systemBankBranchName=new ArrayList<String>();
		List<String> otherBankBranchName=new ArrayList<String>();
		List<String> allBankBranchName=new ArrayList<String>();
		List<String> ifscBankBranchName=new ArrayList<String>();
	//	ImageUploadAddForm imageUploadAddForm=new ImageUploadAddForm();
		
		ImageTagDaoImpl imageTagDaoImpl=new ImageTagDaoImpl();
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
		
		List bankList=new ArrayList();
		bankList.addAll(imageTagDaoImpl.populateBankList(systemBankBranchName));
		bankList.addAll(imageTagDaoImpl.populateBankList(otherBankBranchName));
		bankList.addAll(imageTagDaoImpl.populateBankList(ifscBankBranchName));
	//	imageUploadAddForm.setBankList(bankList);
	//	result.put("bankList", imageUploadAddForm.getBankList());
		result.put("bankList", bankList);
		//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
		
/*		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		CustomerSearchCriteria formCriteria = (CustomerSearchCriteria) map.get("customerSearchCriteria");
		CustomerSearchCriteria searchCriteria = null;
		if (searchCriteria == null) {
			DefaultLogger.debug(this, "- Search Criteria from Form !");
			searchCriteria = formCriteria;
		}

		ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
		long teamTypeID = team.getTeamType().getTeamTypeID();

		if (teamTypeID == ICMSConstant.TEAM_TYPE_MR) {
			searchCriteria.setLmtProfileType(ICMSConstant.AA_TYPE_TRADE);
		}

		searchCriteria.setCtx(theOBTrxContext);

		try {
			ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
			SearchResult sr = custproxy.searchCustomer(searchCriteria);
			IImageUploadProxyManager imageUploadProxyManager = (IImageUploadProxyManager) getImageUploadProxyManager();
			result.put("imageUploadProxyManager", imageUploadProxyManager);
			result.put("customerList", sr);
			result.put(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,searchCriteria);
			result.put("customerSearchCriteria1", searchCriteria);
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			CommandProcessingException cpe = new CommandProcessingException("failed to search customer using search criteria '"+ searchCriteria + "'");
			cpe.initCause(e);
			throw cpe;
		}
*/
		
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
		camNumberList = imageTagDaoImpl.getcamNumberList (legalName);
		
		List<String> camDocList=new ArrayList<String>();
		camDocList = imageTagDaoImpl.getCamDocumentList ();
		
		List<String> otherMasterDocList=new ArrayList<String>();
		otherMasterDocList = imageTagDaoImpl.getOtherMasterDocumentList ();
		
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
		
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		DefaultLogger.debug(this, "Exit from doExecute()");
		return temp;
	}

}
