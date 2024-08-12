package com.integrosys.cms.ui.imageTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class RetriveCamCmd extends AbstractCommand {
	


	private IImageTagProxyManager imageTagProxyManager;

	private ICheckListProxyManager checklistProxyManager;

	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}

	public RetriveCamCmd()
	{}
	
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
//				{ IGlobalConstant.USER_TEAM,"com.integrosys.component.bizstructure.app.bus.ITeam",GLOBAL_SCOPE },
				{ "securityId", "java.lang.String", REQUEST_SCOPE },
				{ "facilityId", "java.lang.String", REQUEST_SCOPE },
				{ "camNo", "java.lang.String", REQUEST_SCOPE },
//				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
//				{ "transactionID", "java.lang.String", REQUEST_SCOPE },
//				{ IGlobalConstant.GLOBAL_TRX_ID, "java.lang.String",GLOBAL_SCOPE },
//				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ,"com.integrosys.cms.app.limit.bus.ILimitProfile",GLOBAL_SCOPE },
//				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,"com.integrosys.cms.app.customer.bus.ICMSCustomer",GLOBAL_SCOPE },
//				{ "securitySubType", "java.lang.String", REQUEST_SCOPE },
//				{"customerSearchCriteria","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",FORM_SCOPE },
//				{"customerSearchCriteria1","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE },
//				{ "ImageTagAddObj","com.integrosys.cms.app.imageTag.bus.OBImageTagAdd",FORM_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "custId", "java.lang.String", REQUEST_SCOPE },
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
//				{IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",GLOBAL_SCOPE },
//				{ "frompage", "java.lang.String", REQUEST_SCOPE },
//				{ "from", "java.lang.String", REQUEST_SCOPE },
//				{ "indicator", "java.lang.String", REQUEST_SCOPE }, 
				});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
//				{IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",GLOBAL_SCOPE },
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "camList", "java.util.List", REQUEST_SCOPE },
//				{ "customerList","com.integrosys.base.businfra.search.SearchResult",FORM_SCOPE },
//				{ "customerList","com.integrosys.base.businfra.search.SearchResult",SERVICE_SCOPE },
//				{ "customerList","com.integrosys.base.businfra.search.SearchResult",REQUEST_SCOPE },
//				{ "secType", "java.lang.String", REQUEST_SCOPE },
//				{"customerSearchCriteria1","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE },
//				{ "ImageTagAddObj","com.integrosys.cms.app.imageTag.bus.OBImageTagAdd",FORM_SCOPE },
//				{ "imageTagAddForm","com.integrosys.cms.ui.imageTag.ImageTagAddForm",FORM_SCOPE },
//				{ "obImageTagAddList", "java.util.ArrayList", FORM_SCOPE },
//				{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
//				{ "obImageTagAddList", "java.util.ArrayList", REQUEST_SCOPE },
//				{ "securityOb", "java.util.HashMap", REQUEST_SCOPE },
//				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
//				{ "secTypeList", "java.util.List", REQUEST_SCOPE },
//				{ "secSubtypeList", "java.util.List", REQUEST_SCOPE },
//				{ "securityIdList", "java.util.List", REQUEST_SCOPE },
//				{ IGlobalConstant.GLOBAL_TRX_ID, "java.lang.String",GLOBAL_SCOPE },
//				{ "customerObList", "java.util.Collection", REQUEST_SCOPE },
//				{ "startIndex", "java.lang.String", GLOBAL_SCOPE },
//				{IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",GLOBAL_SCOPE },
//				{ "from", "java.lang.String", REQUEST_SCOPE },
//				{"imageTagProxyManager","com.integrosys.cms.app.customer.bus.IImageTagProxyManager",SERVICE_SCOPE }

		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException, AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap result = new HashMap();

		
		String strLimitProfileId=(String) map.get("custLimitProfileID");
		long limitProfileID = Long.parseLong(strLimitProfileId);
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		//String collatralId=(String) map.get("securityId");
		//String facilityId=(String) map.get("facilityId");
		
		String camNo=(String) map.get("camNo");
		
		List documentItemList = new ArrayList();
		if(camNo!=null || "".equals(camNo)){
			try{
				documentItemList.add(CheckListDAOFactory.getCheckListDAO().retriveCam(camNo.trim()));
				//camInfoMap = CheckListDAOFactory.getCheckListDAO().retriveCam(camNo.trim());
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		
		
		result.put("camList", documentItemList);		
		result.put("custLimitProfileID", strLimitProfileId);		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return returnMap;
	}


}
