package com.integrosys.cms.ui.image;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;

public class AddDocLinkageDetailsToSessionCmd extends AbstractCommand implements ICommonEventConstant{

	@Override
	public String[][] getParameterDescriptor() {
		return new String[][]{
			{ "obImageUploadAddList", "java.util.List", SERVICE_SCOPE },
			{ "imgUploadList", "java.util.List", SERVICE_SCOPE },
			{ "custImageListWithTag", "java.util.List", SERVICE_SCOPE },
			{ "legalName", "java.lang.String", REQUEST_SCOPE },
			{ "selectIdx", "java.lang.String", REQUEST_SCOPE },
			{ "ImageUploadAddObjSession", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", SERVICE_SCOPE},
		};
	}
	
	@Override
	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap result = new HashMap();
		
		OBImageUploadAdd imageUploadAddObjSession = (OBImageUploadAdd) map.get("ImageUploadAddObjSession");
		String custId = imageUploadAddObjSession.getCustId();
		String selectIdx = (String) map.get("selectIdx");
		List imgUploadList = (List) map.get("imgUploadList");;
		List obImageUploadAddList = (List) map.get("obImageUploadAddList");
		List custImageListWithTag = (List) map.get("custImageListWithTag");

		String[] idxs = selectIdx.split(",");
		
		List<IImageUploadAdd> toAddList = new ArrayList<IImageUploadAdd>();
		
		for(String idx : idxs) {
			IImageUploadAdd ob = (IImageUploadAdd) imgUploadList.get(Integer.valueOf(idx));
			IImageUploadAdd copyOb = new OBImageUploadAdd();
			AccessorUtil.copyValue(ob, copyOb);
			copyOb.setImgId(0);
			copyOb.setCreationDate(new Date());
			String customerName ="";
			ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
			List customerList = customerDAO.searchCustomerByCIFNumber(imageUploadAddObjSession.getCustId());
			if(customerList != null){
				OBCMSCustomer customer = (OBCMSCustomer) customerList.get(0);
				customerName = customer.getCustomerName();
			}

			copyOb.setTypeOfDocument(imageUploadAddObjSession.getTypeOfDocument());
			copyOb.setFacilityName(imageUploadAddObjSession.getFacilityName());
			copyOb.setFacilityDocName(imageUploadAddObjSession.getFacilityDocName());
			copyOb.setOtherDocName(imageUploadAddObjSession.getOtherDocName());
			copyOb.setSecurityNameId(imageUploadAddObjSession.getSecurityNameId());
			copyOb.setSecurityDocName(imageUploadAddObjSession.getSecurityDocName());
			copyOb.setOtherSecDocName(imageUploadAddObjSession.getOtherSecDocName());
			copyOb.setHasCam(imageUploadAddObjSession.getHasCam());
			copyOb.setCustId(imageUploadAddObjSession.getCustId());
			copyOb.setCustName(customerName);
			copyOb.setCategory(imageUploadAddObjSession.getCategory());
			copyOb.setBank(imageUploadAddObjSession.getBank());
			copyOb.setCamDocName(imageUploadAddObjSession.getCamDocName());
			copyOb.setStatementDocName(imageUploadAddObjSession.getStatementDocName());
			copyOb.setStatementTyped(imageUploadAddObjSession.getStatementTyped());
			copyOb.setOthersDocsName(imageUploadAddObjSession.getOthersDocsName());
			copyOb.setSecurityIdi(imageUploadAddObjSession.getSecurityIdi());
			copyOb.setSubTypeSecurity(imageUploadAddObjSession.getSubTypeSecurity());
			
			toAddList.add(copyOb);
		}
		
		List newObImageUploadAddList = new ArrayList();
		newObImageUploadAddList.addAll(toAddList);
		newObImageUploadAddList.addAll(obImageUploadAddList);
		
		result.put("imgUploadList", imgUploadList);
		result.put("obImageUploadAddList", newObImageUploadAddList);
		result.put("custImageListWithTag", custImageListWithTag);
		result.put("legalName", custId);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		
		return returnMap;
	}
	
	@Override
	public String[][] getResultDescriptor() {
		return new String[][]{
			{ "obImageUploadAddList", "java.util.ArrayList", SERVICE_SCOPE },
			{ "imgUploadList", "java.util.List", SERVICE_SCOPE },
			{ "custImageListWithTag", "java.util.List", SERVICE_SCOPE },
			{ "legalName","java.lang.String",REQUEST_SCOPE },
		};
	}
	
}