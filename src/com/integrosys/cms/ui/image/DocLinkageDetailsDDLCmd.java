package com.integrosys.cms.ui.image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.imageTag.bus.IImageTagDao;

public class DocLinkageDetailsDDLCmd extends AbstractCommand implements ICommonEventConstant{
	
	@Override
	public String[][] getParameterDescriptor() {
		return new String[][]{
			{ "legalName", "java.lang.String", REQUEST_SCOPE },
			{ "typeOfDocList", "java.util.List", SERVICE_SCOPE },
			{ "custIdList", "java.util.List", SERVICE_SCOPE },
			{ "facilityCodeList", "java.util.List", SERVICE_SCOPE },
			{ "otherDocList", "java.util.List", SERVICE_SCOPE },
			{ "securityNameIdList", "java.util.List", SERVICE_SCOPE },
			{ "otherSecDocList", "java.util.List", SERVICE_SCOPE },
			{ "statementDocList", "java.util.List", SERVICE_SCOPE },
			{ "camNumberList", "java.util.List", SERVICE_SCOPE },
			{ "camDocList", "java.util.List", SERVICE_SCOPE },
			{ "otherMasterDocList", "java.util.List", SERVICE_SCOPE },
			{ "bankList", "java.util.List", SERVICE_SCOPE },
			{ "facilityMap", "java.util.Map", SERVICE_SCOPE },
			{"newSession", "java.lang.String", REQUEST_SCOPE}
		};
	}
	
	@Override
	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap result = new HashMap();
		
		IImageTagDao imageTagDao = (IImageTagDao) BeanHouse.get("imageTagDao");
		
		String custId= (String) map.get("legalName");
		
		String newSession = (String) map.get("newSession");
		
		List<String> typeOfDocList = (List<String>) map.get("typeOfDocList");
		if("Y".equals(newSession) || typeOfDocList == null || typeOfDocList.size() == 0)
			typeOfDocList = imageTagDao.getTypeOfDocumentList();
		
		Map<String, String> facilityMap = (Map<String, String>) map.get("facilityMap");
		List<String> custIdList = (List<String>) map.get("custIdList");
		List<String> facilityCodeList = (List<String>) map.get("facilityCodeList");
		if("Y".equals(newSession) || facilityMap == null || facilityMap.size() == 0) {
			facilityMap = imageTagDao.getFacilityMap(custId);
			if(facilityMap != null) {
				custIdList = new ArrayList<String>();
				facilityCodeList = new ArrayList<String>();
				for(String key : facilityMap.keySet()) {
					custIdList.add(facilityMap.get(key));
					facilityCodeList.add(key);
				}
			}
		}
		
		List<String> otherDocList = (List<String>) map.get("otherDocList");
		if("Y".equals(newSession) || otherDocList == null || otherDocList.size() == 0)
			otherDocList = imageTagDao.getOtherDocumentList();
		
		List<String> securityNameIdList = (List<String>) map.get("securityNameIdList");
		if("Y".equals(newSession) || securityNameIdList == null || securityNameIdList.size() == 0)
			securityNameIdList =imageTagDao.getSecurityNameIds(custId);
		
		List<String> otherSecDocList = (List<String>) map.get("otherSecDocList");
		if("Y".equals(newSession) || otherSecDocList == null || otherSecDocList.size() == 0)
			otherSecDocList =imageTagDao.getSecurityOtherDocumentList();
		
		List<String> statementDocList = (List<String>) map.get("statementDocList");
		if("Y".equals(newSession) || statementDocList == null || statementDocList.size() == 0)
			statementDocList =imageTagDao.getStatementDocumentList();
		
		List<String> camNumberList = (List<String>) map.get("camNumberList");
		if("Y".equals(newSession) || camNumberList == null || camNumberList.size() == 0)
			camNumberList =imageTagDao.getcamNumberList(custId);
		
		List<String> camDocList = (List<String>) map.get("camDocList");
		if("Y".equals(newSession) || camDocList == null || camDocList.size() == 0)
			camDocList =imageTagDao.getCamDocumentList();
		
		List<String> otherMasterDocList = (List<String>) map.get("otherMasterDocList");
		if("Y".equals(newSession) || otherMasterDocList == null || otherMasterDocList.size() == 0)
			otherMasterDocList =imageTagDao.getOtherMasterDocumentList();
	
		List bankList= (List<String>) map.get("bankList");
		if("Y".equals(newSession) || bankList == null || bankList.size() == 0) {
			bankList = new ArrayList();
			List<String> systemBankBranchName = new ArrayList<String>();
			List<String> otherBankBranchName = new ArrayList<String>();
			List<String> ifscBankBranchName = new ArrayList<String>();
			
			String systemBankId = imageTagDao.getSystemBankId(custId);
			String otherBankId = imageTagDao.getOtherBankId(custId);
			if(null!=systemBankId && !systemBankId.isEmpty()){
				if(systemBankId.lastIndexOf(",")!=-1){
					systemBankId=systemBankId.substring(0, systemBankId.lastIndexOf(","));
				}
				systemBankBranchName = imageTagDao.getSystemBankBranchName(systemBankId);
			}
			if(null!=otherBankId && !otherBankId.isEmpty()){
				if(otherBankId.lastIndexOf(",")!=-1){
					otherBankId=otherBankId.substring(0, otherBankId.lastIndexOf(","));
				}
				 otherBankBranchName = imageTagDao.getOtherBankBranchName(otherBankId);
			}
			
			ifscBankBranchName = imageTagDao.getIFSCBankBranchName(custId);
			
			bankList.addAll(imageTagDao.populateBankList(systemBankBranchName));
			bankList.addAll(imageTagDao.populateBankList(otherBankBranchName));
			bankList.addAll(imageTagDao.populateBankList(ifscBankBranchName));
		}
		
		result.put("typeOfDocList", typeOfDocList);
		result.put("custIdList", custIdList);
		result.put("facilityMap", facilityMap);
		result.put("facilityCodeList", facilityCodeList);
		result.put("otherDocList", otherDocList);
		result.put("securityNameIdList", securityNameIdList);
		result.put("otherSecDocList", otherSecDocList);
		result.put("statementDocList", statementDocList);
		result.put("camNumberList", camNumberList);
		result.put("camDocList", camDocList);
		result.put("otherMasterDocList", otherMasterDocList);
		result.put("bankList", bankList);
		result.put("legalName", custId);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		
		return returnMap;
	}
	
	@Override
	public String[][] getResultDescriptor() {
		return new String[][]{
			{ "legalName", "java.lang.String", REQUEST_SCOPE },
			{ "typeOfDocList", "java.util.List", SERVICE_SCOPE },
			{ "custIdList", "java.util.List", SERVICE_SCOPE },
			{ "facilityCodeList", "java.util.List", SERVICE_SCOPE },
			{ "otherDocList", "java.util.List", SERVICE_SCOPE },
			{ "securityNameIdList", "java.util.List", SERVICE_SCOPE },
			{ "otherSecDocList", "java.util.List", SERVICE_SCOPE },
			{ "statementDocList", "java.util.List", SERVICE_SCOPE },
			{ "camNumberList", "java.util.List", SERVICE_SCOPE },
			{ "camDocList", "java.util.List", SERVICE_SCOPE },
			{ "otherMasterDocList", "java.util.List", SERVICE_SCOPE },
			{ "bankList", "java.util.List", SERVICE_SCOPE },
			{ "facilityMap", "java.util.Map", SERVICE_SCOPE },
		};
	}

}