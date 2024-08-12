package com.integrosys.cms.ui.relationshipmgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.crystaldecisions.enterprise.ocaframework.idl.OCA.OCAs.Session;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.relationshipmgr.bus.IHRMSData;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class PopulateFieldsCommand extends AbstractCommand{

	private IRelationshipMgrProxyManager relationshipMgrProxyManager;


	/**
	 * @return the relationshipMgrProxyManager
	 */
	public IRelationshipMgrProxyManager getRelationshipMgrProxyManager() {
		return relationshipMgrProxyManager;
	}

	/**
	 * @param relationshipMgrProxyManager the relationshipMgrProxyManager to set
	 */
	public void setRelationshipMgrProxyManager(
			IRelationshipMgrProxyManager relationshipMgrProxyManager) {
		this.relationshipMgrProxyManager = relationshipMgrProxyManager;
	}

/**
 * Default Constructor
 */

	
	public String[][] getParameterDescriptor() {
		// TODO Auto-generated method stub
		return (new String[][] { { "RMCode", "java.lang.String", REQUEST_SCOPE },
			{ "localCADs","java.util.List",SERVICE_SCOPE},
		});
	}

	public String[][] getResultDescriptor() {
		// TODO Auto-generated method stub
		return (new String[][] { 
			{ "relationshipMgrForm", "java.lang.object", REQUEST_SCOPE },
			{ "regionList","java.util.List",SERVICE_SCOPE},
			{ "localCADList","java.util.List",REQUEST_SCOPE},
			{ "localCADs","java.util.List",SERVICE_SCOPE},
});
	}

	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {
	
			HashMap result = new HashMap();
			HashMap exceptionMap = new HashMap();
			HashMap temp = new HashMap();
			List localCADs = new ArrayList();
			
			if(null != map.get("localCADs")) {
				localCADs =  (List) map.get("localCADs");
			}
			
			String rmEmployeeCode = (String) map.get("RMCode");
			RelationshipMgrForm relationshipMgr = new RelationshipMgrForm();
			
			IHRMSData ihrmsData = getRelationshipMgrProxyManager().getHRMSEmpDetails(rmEmployeeCode);
			if(null == ihrmsData) {
				relationshipMgr.setRelationshipMgrCode(rmEmployeeCode);
			}else {
			
			relationshipMgr.setRelationshipMgrCode(ihrmsData.getEmployeeCode());
			relationshipMgr.setRelationshipMgrName(ihrmsData.getName());
			relationshipMgr.setRelationshipMgrMailId(ihrmsData.getEmailId());
			relationshipMgr.setRelationshipMgrMobileNo(ihrmsData.getMobileNo());
			relationshipMgr.setRelationshipMgrCity(ihrmsData.getCity());
			relationshipMgr.setRelationshipMgrState(ihrmsData.getState());
			relationshipMgr.setReportingHeadEmployeeCode(ihrmsData.getSupervisorEmployeeCode());

			if (null != ihrmsData.getSupervisorEmployeeCode()) {
				IHRMSData data = getRelationshipMgrProxyManager()
						.getHRMSEmpDetails(ihrmsData.getSupervisorEmployeeCode().toString());
				if (null != data) {
//					relationshipMgr.setEmployeeId(ihrmsData.getSupervisorEmployeeCode());
					relationshipMgr.setReportingHeadEmployeeCode(data.getEmployeeCode());
					relationshipMgr.setReportingHeadName(data.getName());
					relationshipMgr.setReportingHeadMailId(data.getEmailId());
					relationshipMgr.setReportingHeadMobileNo(data.getMobileNo());
				}
			}
		}
			result.put("localCADs", localCADs);
			result.put("localCADList", localCADs);
			result.put("regionList", getRegionList());
			result.put("relationshipMgrForm", (RelationshipMgrForm)relationshipMgr);
			
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);	
			return temp;
	}
	
	private List getRegionList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getRelationshipMgrProxyManager().getRegionList(PropertyManager.getValue("clims.application.country"));				
		
			for (int i = 0; i < idList.size(); i++) {
				IRegion region = (IRegion)idList.get(i);
				String id = Long.toString(region.getIdRegion());
				String val = region.getRegionName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
