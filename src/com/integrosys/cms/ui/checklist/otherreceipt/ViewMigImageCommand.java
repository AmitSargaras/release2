/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.otherreceipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.customer.OBLegalEntity;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAO;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.imageTag.bus.IImageTagDao;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.ActionPartyList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.imageTag.IImageTagConstants;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author $Author: wltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/07/20 01:44:56 $ Tag: $Name: $
 */
public class ViewMigImageCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ViewMigImageCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
					GLOBAL_SCOPE },
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
			{ "index", "java.lang.String", REQUEST_SCOPE },
			{ "documentID", "java.lang.String", REQUEST_SCOPE },
			{ "documentType", "java.lang.String", REQUEST_SCOPE },
			{ "documentVersion", "java.lang.String", REQUEST_SCOPE },
			{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
			{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }
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
		return (new String[][] { 
				{ "migCheckListImages", "java.util.ArrayList", REQUEST_SCOPE },
				{ "documentID", "java.lang.String", REQUEST_SCOPE },
				{ "documentType", "java.lang.String", REQUEST_SCOPE },
				{ "documentVersion", "java.lang.String", REQUEST_SCOPE },
				{ "migCheckListImages", "java.util.ArrayList", SERVICE_SCOPE },
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ICheckList checkList = (ICheckList) map.get("checkList");
			String  documentID = (String) map.get("documentID");
			String  documentType = (String) map.get("documentType");
			String  documentVersion = (String) map.get("documentVersion");
			
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			// DefaultLogger.debug(this,"Limit profile "+limit);
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			String event = (String) map.get("event");
			ArrayList migCheckListImages = new ArrayList();
			CheckListDAO checkListDAO = new CheckListDAO();
      	  migCheckListImages= checkListDAO.getMigratedCheckListItemImages(String.valueOf(limitProfileID), "Other", documentID, documentVersion);
			  resultMap.put("migCheckListImages", migCheckListImages);
			  resultMap.put("documentID", documentID);
				 resultMap.put("documentType", documentType);
				 resultMap.put("documentVersion", documentVersion);
			
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		
		 
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	

}
