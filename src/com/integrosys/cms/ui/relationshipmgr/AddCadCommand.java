package com.integrosys.cms.ui.relationshipmgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.relationshipmgr.bus.IHRMSData;
import com.integrosys.cms.app.relationshipmgr.bus.OBLocalCAD;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class AddCadCommand extends AbstractCommand {

	private IRelationshipMgrProxyManager relationshipMgrProxyManager;

	/**
	 * @return the relationshipMgrProxyManager
	 */
	public IRelationshipMgrProxyManager getRelationshipMgrProxyManager() {
		return relationshipMgrProxyManager;
	}

	/**
	 * @param relationshipMgrProxyManager
	 *            the relationshipMgrProxyManager to set
	 */
	public void setRelationshipMgrProxyManager(IRelationshipMgrProxyManager relationshipMgrProxyManager) {
		this.relationshipMgrProxyManager = relationshipMgrProxyManager;
	}

	/**
	 * Default Constructor
	 */

	public String[][] getParameterDescriptor() {
		// TODO Auto-generated method stub
		return (new String[][] { { "cadEmployeeCode", "java.lang.String", REQUEST_SCOPE },
				{ "cadBranchCode", "java.lang.String", REQUEST_SCOPE },
				{ "relationshipMgrForm", "java.lang.object", REQUEST_SCOPE },
				{ "localCADs", "java.util.List", SERVICE_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE }
				});
	}

	public String[][] getResultDescriptor() {
		// TODO Auto-generated method stub
		return (new String[][] { { "relationshipMgrForm", "java.lang.object", REQUEST_SCOPE },
				{ "regionList", "java.util.List", SERVICE_SCOPE }, { "localCADList", "java.util.List", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "localCADs", "java.util.List", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		List localCADs = new ArrayList();

		String cadEmployeeCode = (String) map.get("cadEmployeeCode");
		String cadBranchCode = (String) map.get("cadBranchCode");
		String event = (String) map.get("event");
		if (null != map.get("localCADs")) {
			localCADs = (List) map.get("localCADs");
		}
		RelationshipMgrForm relationshipMgr = new RelationshipMgrForm();
		relationshipMgr = (RelationshipMgrForm) map.get("relationshipMgrForm");
//		exceptionMap.clear();
		IRegionDAO iRegion =  (IRegionDAO) BeanHouse.get("regionDAO");
		String region = "";
//		try {
//			region = (iRegion.getRegionById(Long.parseLong(cadBranchCode)).getRegionName());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		IHRMSData iHrmsData = getRelationshipMgrProxyManager().getLocalCAD(cadEmployeeCode,
				region);
		if (null != iHrmsData) {
			int count = 0;
			for (int i = 0; i < localCADs.size(); i++) {
//				if (event.equals("addEditCad")) {
					OBLocalCAD localCAD = (OBLocalCAD) localCADs.get(i);
					if (localCAD.getLocalCADEmployeeCode().equals(iHrmsData.getEmployeeCode())) {
						count++;
					}
			}
			if (count == 0) {
				OBLocalCAD lCad = new OBLocalCAD();
				lCad.setLocalCADEmployeeCode(iHrmsData.getEmployeeCode());
				lCad.setLocalCADName(iHrmsData.getName());
				lCad.setLocalCADEmailID(iHrmsData.getEmailId());
				lCad.setLocalCADmobileNo(iHrmsData.getMobileNo());

				if (null != iHrmsData.getSupervisorEmployeeCode()) {
					IHRMSData data = getRelationshipMgrProxyManager()
							.getHRMSEmpDetails(iHrmsData.getSupervisorEmployeeCode().toString());
					if (null != data) {
						lCad.setLocalCADSupervisorName(data.getName());
						lCad.setLocalCADSupervisorEmail(data.getEmailId());
						lCad.setLocalCADSupervisorMobileNo(data.getMobileNo());
					}
				}
				localCADs.add((OBLocalCAD) lCad);
			}else {
//				temp.clear();
				if(!"addCadError".equals(event) && !"addResubmitCad_draft_error".equals(event) && !"removeResubmitCad_draft_error".equals(event) && !"removeResubmitCad_rejected_error".equals(event) && !"addResubmitCad_rejected_error".equals(event)) {
				exceptionMap.put("cadEmployeeCodeError", new ActionMessage("error.string.exist", "Local CAD"));
				}
			}
//				} else {
//					IRelationshipMgr iRelationshipMgr1 = (OBRelationshipMgr) localCADs.get(i);
//					if (iRelationshipMgr1.getRelationshipMgrCode().equals(iRelationshipMgr.getRelationshipMgrCode())) {
//						count++;
//					}
//					if (count == 0) {
//						localCADs.add((OBRelationshipMgr) iRelationshipMgr);
//					}
//				}
//			}
			
		}else {
//			boolean isAlphaNum = ASSTValidator.isValidAlphaNumStringWithoutSpace(cadEmployeeCode);
//
//			if (isAlphaNum && exceptionMap.isEmpty()) {
//				temp.clear();				
//				exceptionMap.put("cadEmployeeCodeError", new ActionMessage("error.string.specialcharacter"));
//			} else if(exceptionMap.isEmpty()){
//				temp.clear();				
//				exceptionMap.put("cadEmployeeCodeError", new ActionMessage("error.string.no.details.found"));
//			}
		}
		result.put("event", event);
		result.put("localCADs", localCADs);
		result.put("localCADList", localCADs);
		result.put("regionList", getRegionList());
		result.put("relationshipMgrForm", (RelationshipMgrForm) relationshipMgr);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private List getRegionList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getRelationshipMgrProxyManager()
					.getRegionList(PropertyManager.getValue("clims.application.country"));

			for (int i = 0; i < idList.size(); i++) {
				IRegion region = (IRegion) idList.get(i);
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