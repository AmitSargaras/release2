package com.integrosys.cms.ui.relationshipmgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.relationshipmgr.bus.OBLocalCAD;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class RemoveCadCommand extends AbstractCommand {

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
		return (new String[][] { { "cadEmployeeCode", "java.lang.String", REQUEST_SCOPE },
			{ "rmCode", "java.lang.String", REQUEST_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE },
			{ "relationshipMgrForm", "java.lang.object", REQUEST_SCOPE },
			{ "removedLocalCADs","java.util.List",SERVICE_SCOPE},
			{ "localCADs","java.util.List",SERVICE_SCOPE},
			{"startIndex", "java.lang.String", REQUEST_SCOPE },
		});
	}

	public String[][] getResultDescriptor() {
		// TODO Auto-generated method stub
		return (new String[][] { 
			{ "relationshipMgrForm", "java.lang.object", REQUEST_SCOPE },
			{ "regionList","java.util.List",SERVICE_SCOPE},
			{ "localCADList","java.util.List",REQUEST_SCOPE},
			{ "event", "java.lang.String", REQUEST_SCOPE },
			{ "removedLocalCADs","java.util.List",SERVICE_SCOPE},
			{"startIndex", "java.lang.String", REQUEST_SCOPE },
			{ "localCADs","java.util.List",SERVICE_SCOPE},});
	}

	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {
	
			HashMap result = new HashMap();
			HashMap exceptionMap = new HashMap();
			HashMap temp = new HashMap();
			List localCADs = new ArrayList();
			List removedLocalCADs = new ArrayList();
			String cadEmployeeCode = (String) map.get("cadEmployeeCode");
			String rmCode = (String) map.get("rmCode");
			String startIndex = (String) map.get("startIndex");
			String event = (String) map.get("event");
			if(null != map.get("localCADs")) {
				localCADs =  (List) map.get("localCADs");
			}
			RelationshipMgrForm relationshipMgr = new RelationshipMgrForm();
			relationshipMgr = (RelationshipMgrForm) map.get("relationshipMgrForm");
			
			if(null != map.get("removedLocalCADs")) {
				removedLocalCADs =  (List) map.get("removedLocalCADs");
			}

//			IRelationshipMgr iRelationshipMgr = getRelationshipMgrProxyManager().getLocalCAD(cadEmployeeCode,cadBranchCode);
//			if(null != iRelationshipMgr) {
				for(int i = 0; i< localCADs.size();i++) {
//					if(event.equals("removeEditCad")) {
						OBLocalCAD localCAD = (OBLocalCAD) localCADs.get(i);
						if(localCAD.getLocalCADEmployeeCode().equals(rmCode)) {
							removedLocalCADs.add(localCADs.get(i));
							localCADs.remove(i);
						}
//					}else {
//						IRelationshipMgr iRelationshipMgr1 = (OBRelationshipMgr) localCADs.get(i);
//						if(iRelationshipMgr1.getRelationshipMgrCode().equals(rmCode)) {
//							localCADs.remove(i);
//						}
//					}
				}				
//			}

			result.put("localCADs", localCADs);
			result.put("startIndex", startIndex);
			result.put("removedLocalCADs", removedLocalCADs);
			result.put("event", event);
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
