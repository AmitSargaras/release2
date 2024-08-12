/*
 * Created on Apr 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.imageTag;

/**
 * 
 * 
 * @author Anil Pandey
 */
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.LimitListSummaryItemBase;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.ui.common.SecurityTypeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RefreshImageTagDropdownsCmd extends AbstractCommand implements IImageTagConstants{

	private IImageTagProxyManager imageTagProxyManager;

	private ICheckListProxyManager checklistProxyManager;

	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(
			IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "docTypeCode", "java.lang.String", REQUEST_SCOPE },
				//{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ,"com.integrosys.cms.app.limit.bus.ILimitProfile",GLOBAL_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "secTypeList", "java.util.List", REQUEST_SCOPE },
				{ "secSubtypeList", "java.util.List", REQUEST_SCOPE },
				{ "facilityIdList", "java.util.List", REQUEST_SCOPE },
				{ "docTypeCode", "java.lang.String", REQUEST_SCOPE },
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },


		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException, AccessDeniedException {
		HashMap returnMap = new HashMap();

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		String strLimitProfileId=(String) map.get("custLimitProfileID");
		long limitProfileID=Long.parseLong(strLimitProfileId);
		
		String docType = (String) map.get("docTypeCode");
		
		//=====================================================
		if(docType!=null&& SECURITY_DOC.equals(docType)){
			result.put("secTypeList", getSecurityTypeList());
			result.put("secSubtypeList", new ArrayList());
			result.put("facilityIdList", new ArrayList());
		}else if(FACILITY_DOC.equals(docType)){
		List facilityIdList = new ArrayList();
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
		try {
			List lmtList = proxy.getLimitSummaryListByAA(Long.toString(limitProfileID));
			if(lmtList!=null && lmtList.size()>0){
				String label;
				String value;
				for (int i = 0; i < lmtList.size(); i++) {
					LimitListSummaryItemBase limitSummaryItem=(LimitListSummaryItemBase) lmtList.get(i);
					label=limitSummaryItem.getCmsLimitId() +" - "+limitSummaryItem.getProdTypeCode();
					value= limitSummaryItem.getCmsLimitId();
					LabelValueBean lvBean = new LabelValueBean(label,value);
					facilityIdList.add(lvBean);
				}
			}
		} catch (LimitException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		result.put("facilityIdList", facilityIdList);
		result.put("secTypeList", new ArrayList());
		result.put("secSubtypeList", new ArrayList());
		//=====================================================
		}else if(CAM_DOC.equals(docType)){
			//Retriving cam checklist code will go here
		}
		
		result.put("custLimitProfileID", Long.toString(limitProfileID));
		result.put("docTypeCode", docType);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
	private List getSecurityTypeList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) (SecurityTypeList.getInstance()
					.getSecurityTypeProperty());
			List valList = (List) (SecurityTypeList.getInstance()
					.getSecurityTypeLabel(null));
			for (int i = 0; i < idList.size(); i++) {
				String id = idList.get(i).toString();
				String val = valList.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

}
