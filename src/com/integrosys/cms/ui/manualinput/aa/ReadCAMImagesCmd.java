package com.integrosys.cms.ui.manualinput.aa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.imageTag.bus.IImageTagDao;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.imageTag.IImageTagConstants;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;

public class ReadCAMImagesCmd extends AbstractCommand {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },	
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "InitialLimitProfile", "com.integrosys.cms.app.limit.bus.OBLimitProfile", FORM_SCOPE },
				{ "camType", "java.lang.String", REQUEST_SCOPE },
				{ "camNumber", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "camDate", "java.lang.String", REQUEST_SCOPE }});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getResultDescriptor() {

		return (new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "session.camType", "java.lang.String", SERVICE_SCOPE },
				{ "session.camNumber", "java.lang.String", SERVICE_SCOPE },
				{ "session.camDate", "java.lang.String", SERVICE_SCOPE },
				{ "tagDetailList", "java.util.List", REQUEST_SCOPE },
				{ "tagDetailList", "java.util.List", SERVICE_SCOPE },
				{ "limitObj", "com.integrosys.cms.app.limit.bus.OBLimitProfile", SERVICE_SCOPE },
				{ "custId", "java.lang.String", REQUEST_SCOPE },
				{ "camChecklistArray", "java.util.ArrayList", SERVICE_SCOPE }
								
		});
		}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		
		String event = (String) map.get("event");
		String camType = (String) map.get("camType");
		String camNumber = (String) map.get("camNumber");
		String camDate = (String) map.get("camDate");
		OBLimitProfile limitObj=(OBLimitProfile)map.get("InitialLimitProfile");
		String id="";
		if(camType!=null && camNumber!=null && camDate!=null){
			id=camNumber+"-"+camType+"-"+camDate;
		}
		
	
		try {
			
			HashMap camCheckListMap=new HashMap();
			ArrayList camChecklistArray=new ArrayList();
			List tagDetailList=null;
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			ICMSLegalEntity obLegalEntity=(ICMSLegalEntity)cust.getLegalEntity();
			IImageTagDao tagDao =(IImageTagDao)BeanHouse.get("imageTagDao"); 
			IImageTagDetails existingTagDetails=tagDao.getCustImageListForView("actualOBImageTagDetails", obLegalEntity.getLEReference(), IImageTagConstants.CAM_NOTE,id);
			if(existingTagDetails!=null){
				
			tagDetailList=tagDao.getTagImageList("actualOBImageTagMap",String.valueOf(existingTagDetails.getId()), "N");
			ImageTagDaoImpl imageTagDaoImpl=new ImageTagDaoImpl();
			OBImageUploadAdd obAdd = new OBImageUploadAdd();
			String typeOfDoc = "";
			for(int i=0;i<tagDetailList.size();i++) {
				obAdd = (OBImageUploadAdd) tagDetailList.get(i);
				typeOfDoc = imageTagDaoImpl.getEntryNameFromCode(obAdd.getCategory());
				obAdd.setTypeOfDocument(typeOfDoc);
			}	
		}
			
			resultMap.put("session.camType", camType);
			resultMap.put("session.camNumber", camNumber);
			resultMap.put("session.camDate", camDate);
			resultMap.put("tagDetailList", tagDetailList);
			resultMap.put("event", event);
			resultMap.put("limitObj", limitObj);
			resultMap.put("custId",obLegalEntity.getLEReference());
			
			
		}
		catch (SearchDAOException e) {
			e.printStackTrace();
		} 

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
