package com.integrosys.cms.ui.checklist.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.chktemplate.bus.DocumentHeldSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.IDocumentHeld;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/10/30 11:10:24 $ Tag: $Name: $
 */
public class ListAllDocumentsCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListAllDocumentsCommand() {
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "docsHeldMap", "java.util.HashMap", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "waiverCreditApproverList", "java.util.List", SERVICE_SCOPE },
				{ "allCreditApproverList", "java.util.List", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", REQUEST_SCOPE } });
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
		String event = (String) map.get("event");
		DefaultLogger.debug(this, "Inside doExecute()");

		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		resultMap.put("theOBTrxContext", theOBTrxContext);

		ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		DocumentHeldSearchCriteria criteria = new DocumentHeldSearchCriteria();
		criteria.setCompletedOnly(false);
		if (!customer.getNonBorrowerInd()) { // if borrower
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			criteria.setLimitProfileID(limitProfileID);
		}
		else { // non-borrower
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

			if (limit != null) {
				limitProfileID = limit.getLimitProfileID();
			}

			criteria.setLimitProfileID(limitProfileID);
			criteria.setSubProfileID(customer.getCustomerID());
			criteria.setSearchCategory(DocumentHeldSearchCriteria.CATEGORY_NON_BORROWER);
		}
		try {
			HashMap docListMap = proxy.getDocumentsHeld(criteria);

            if (map.get("availableCmsCheckListIds") != null) {
                Set cmsCheckListIds = (Set) map.get("availableCmsCheckListIds");
                for (Iterator itr = docListMap.values().iterator(); itr.hasNext();) {
                    List documentHeldList = (List) itr.next();
                    for (Iterator itrDocHeld = documentHeldList.iterator(); itrDocHeld.hasNext();) {
                        IDocumentHeld docHeld = (IDocumentHeld) itrDocHeld.next();
                        if (!cmsCheckListIds.contains(new Long(docHeld.getCheckListID()))) {
                            itrDocHeld.remove();
                        }
                    }
                }
            }

			resultMap.put("docsHeldMap", docListMap);
		}
		catch (CheckListException e) {
			throw new CommandProcessingException("failed to retrieve document held through checklist proxy", e);
		}

		resultMap.put("event", event);
		resultMap.put("waiverCreditApproverList", getAllWaiveCreditApprover());
		resultMap.put("allCreditApproverList", getAllCreditApprover());
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	private List getAllWaiveCreditApprover() {
		List lbValList = new ArrayList();
		try {
			
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			
			List waive = (List)proxy.getAllWaiveCreditApprover();
			
			//List idList = (List) getCityProxy().getCityList(stateId);				
		
			for (int i = 0; i < waive.size(); i++) {
				ICreditApproval creditApproval = (ICreditApproval)waive.get(i);
				
					String id = creditApproval.getApprovalCode();
					String val = creditApproval.getApprovalName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	private List getAllCreditApprover() {
		List lbValList = new ArrayList();
		try {
			
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			
			List waive = (List)proxy.getAllBothCreditApprover();
			
			//List idList = (List) getCityProxy().getCityList(stateId);				
		
			for (int i = 0; i < waive.size(); i++) {
				ICreditApproval creditApproval = (ICreditApproval)waive.get(i);
				
					String id = creditApproval.getApprovalCode();
					String val = creditApproval.getApprovalName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
