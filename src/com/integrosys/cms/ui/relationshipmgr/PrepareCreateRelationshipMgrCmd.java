package com.integrosys.cms.ui.relationshipmgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author $Author: Dattatray Thorat $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2011/04/06 04:23:28 $ Tag: $Name: $
 */
public class PrepareCreateRelationshipMgrCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	
	IRelationshipMgrProxyManager relationshipMgrProxyManager;
	
	IOtherBankProxyManager otherBankProxyManager;
	
	/**
	 * @return the otherBankProxyManager
	 */
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	/**
	 * @param otherBankProxyManager the otherBankProxyManager to set
	 */
	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
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

	public PrepareCreateRelationshipMgrCmd() {
		 //errors = new ActionErrors();
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
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				 { "localCADList", "java.util.List", REQUEST_SCOPE },
					{ "localCADs", "java.util.List", REQUEST_SCOPE },
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
                { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE },
                { "regionList","java.util.List",REQUEST_SCOPE},
    			{ "localCADList","java.util.List",REQUEST_SCOPE},
    			{ "localCADs","java.util.List",SERVICE_SCOPE},
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
		HashMap exceptionMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			resultMap.put("regionList", getRegionList());
			
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		if (null != map.get("localCADs")) {
			List localCADs = (List) map.get("localCADs");
			resultMap.put("localCADList", localCADs);
			resultMap.put("localCADs", localCADs);
		} else {
			resultMap.put("localCADList", new ArrayList());
			resultMap.put("localCADs", new ArrayList());
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
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
