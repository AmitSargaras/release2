package com.integrosys.cms.ui.creditApproval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * author@ govind.sahu
 * This class implements command
 */
public class PrepareCreditApprovalCommand extends CreditApprovalCommand {
	
	IRelationshipMgrProxyManager relationshipMgrProxyManager;

	public String[][] getParameterDescriptor() {
		return new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE }, };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ CreditApprovalForm.MAPPER, "com.integrosys.cms.app.creditApproval.bus.ICreditApproval", FORM_SCOPE },
				{ "ratingTypeList", "java.util.List", REQUEST_SCOPE },
				{ "regionList", "java.util.List",REQUEST_SCOPE }
				};
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			String event = (String) map.get("event");
			resultMap.put("regionList", getRegionList());
		
			DefaultLogger.debug(this, "after getting CreditApproval feed group from proxy.");
		}
		catch (CreditApprovalException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
	         e.printStackTrace();
	         throw (new CommandProcessingException(e.getMessage()));
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
	         e.printStackTrace();
	         throw (new CommandProcessingException(e.getMessage()));
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);


		return returnMap;
	}
	
	
	private List getRegionList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getRelationshipMgrProxyManager().getRegionList("IN");				
		
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

	
}
