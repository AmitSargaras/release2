/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insurancepolicy/ReadAddtionalDocumentFacilityDetailsCommand.java,v 1.4 2006/09/06 01:54:23 pratheepa Exp $
 */

package com.integrosys.cms.ui.collateral.addtionalDocumentFacilityDetails;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
//import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
//import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IAddtionalDocumentFacilityDetails;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
//import com.integrosys.cms.app.limit.bus.LimitException;
//import com.integrosys.cms.app.limit.proxy.ILimitProxy;
//import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.collateral.CollateralStpValidateUtils;
import com.integrosys.cms.ui.collateral.CollateralStpValidator;
import com.integrosys.cms.ui.manualinput.CommonUtil;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/09/06 01:54:23 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 1:26:03 PM
 * To change this template use Options | File Templates.
 */
public class ReadAddtionalDocumentFacilityDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE },
				
				{ "event", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "addtionalDocumentFacilityDetailsObj", "java.lang.HashMap", FORM_SCOPE },
				{ "actualInsurance", "com.integrosys.cms.app.collateral.bus.IAddtionalDocumentFacilityDetails", REQUEST_SCOPE },
				{ "stageInsurance", "com.integrosys.cms.app.collateral.bus.IAddtionalDocumentFacilityDetails", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, { "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE },
				{ "le_id_bca_ref_num", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "deferCreditApproverList", "java.util.List", SERVICE_SCOPE },
				{ "waiverCreditApproverList", "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		ICollateral iCol;
		//ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		//ICheckListProxyManager checklistproxy = CheckListProxyManagerFactory.getCheckListProxyManager();

		// ICheckListProxyManager proxy =
		// CheckListProxyManagerFactory.getCheckListProxyManager();

		String indexStr = (String) map.get("indexID");

		String from_event = (String) map.get("from_event");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");

		if ((from_event != null) && from_event.equals("read")) {
			iCol = (ICollateral) itrxValue.getCollateral();
		}
		else {
			iCol = (ICollateral) itrxValue.getStagingCollateral();
			if (ICMSConstant.STATE_PENDING_PERFECTION.equals(itrxValue.getStatus())) {
				Map context = new HashMap();
				// Andy Wong: set CMV to staging if actual got value but staging
				// blank, used for pre Stp valuation validation
				if (itrxValue.getCollateral() != null
						&& itrxValue.getCollateral().getCMV() != null
						&& (itrxValue.getStagingCollateral().getCMV() == null || itrxValue.getStagingCollateral()
								.getCMV().getAmount() <= 0)) {
					itrxValue.getStagingCollateral().setCMV(itrxValue.getCollateral().getCMV());
				}
				context.put(CollateralStpValidator.COL_OB, iCol);
				context.put(CollateralStpValidator.TRX_STATUS, itrxValue.getStatus());
				context.put(CollateralStpValidator.COL_TRX_VALUE, itrxValue);
				ActionErrors errors = CollateralStpValidateUtils.validateAndAccumulate(context);
				if (!errors.isEmpty()) {
					temp.put(MESSAGE_LIST, errors);
				}
			}

			if ((from_event != null) && from_event.equals("process")) {
				IAddtionalDocumentFacilityDetails actualInsurance = null;
				if (itrxValue.getCollateral() != null)
					actualInsurance = getItem(((ICollateral) itrxValue.getCollateral()).getAdditonalDocFacDetails(),
							indexStr);
				IAddtionalDocumentFacilityDetails stageInsurance = getItem(iCol.getAdditonalDocFacDetails(), indexStr);

				result.put("actualInsurance", actualInsurance);
				result.put("stageInsurance", stageInsurance);
			}
		}
		IAddtionalDocumentFacilityDetails docFacdetails;
		if (from_event != null) {
			docFacdetails = getItem(iCol.getAdditonalDocFacDetails(), indexStr);
			if ((docFacdetails == null) && from_event.equals("process")) {
				docFacdetails = getItem(((ICollateral) itrxValue.getCollateral()).getAdditonalDocFacDetails(), indexStr);
//				DefaultLogger.debug(this, docFacdetails.getInsuranceClaimDate());
			}
		}
		else {
			int index = Integer.parseInt(indexStr);
			if ((int) index >= 0) {
				docFacdetails = iCol.getAdditonalDocFacDetails()[index];
//				DefaultLogger.debug("***" + this, docFacdetails.getInsuranceClaimDate()); 
				DefaultLogger.debug("***REMARIK*" + this, docFacdetails.getRemark1());
			}
			else {
				docFacdetails = null;
			}
		}
		// not used in eon bank project
		// DefaultLogger.debug(this,"InsuranceDoc:" +docFacdetails.getDocumentNo());
		String collateralId = (String) map.get("collateralID");
		long lCollateralId = 0;
		if ((collateralId != null) && (collateralId.trim().length() > 0)) {
			lCollateralId = Long.parseLong(collateralId);
		}

		long llmtProfileId = 0;
//		if (docFacdetails != null && docFacdetails.getLmtProfileId() != null) {
//			llmtProfileId = docFacdetails.getLmtProfileId().longValue();
//		}
		String sCollateralId = String.valueOf(lCollateralId);
		String slmtProfileId = String.valueOf(llmtProfileId);
		String leIdAndBcaRefNum = null;
		if ((slmtProfileId != null) && (slmtProfileId.trim().length() > 0)
				&& (llmtProfileId != ICMSConstant.LONG_INVALID_VALUE)) {

//			try {
//				leIdAndBcaRefNum = limitProxy.getLEIdAndBCARef(llmtProfileId);
//			}
//			catch (LimitException e) {
//				throw new CommandProcessingException("failed to retrieve le id and bca ref num for limitProfileId ["
//						+ llmtProfileId + "]", e);
//			}
		}
		
		
		HashMap objMap = new HashMap();
		objMap.put("obj", docFacdetails);
		objMap.put("col", iCol);

		result.put("addtionalDocumentFacilityDetailsObj", objMap);

		result.put("indexID", map.get("indexID"));
		result.put("subtype", map.get("subtype"));
		result.put("from_event", from_event);
		
		//Uma Khot::Insurance Deferral maintainance
		
		
		String event=(String)map.get("event");
		
		result.put("event", event);
		
		if(
//				CollateralAction.MAKER_VIEW_ADD_DOC_FAC_DET _WAIVED.equals(event) ||
//				CollateralAction.CHECKER_VIEW_ADD_DOC_FAC_DET _WAIVED.equals(event) || CollateralAction.MAKER_CREATE_ADD_DOC_FAC_DET _WAIVED.equals(event) ||
		  CollateralAction.MAKER_SUBMIT_INS_WAIVED_ERROR.equals(event) 
//		|| CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET _WAIVED.equals(event)
		||CollateralAction.MAKER_UPDATE_INSWAIVED_LIST_ERROR.equals(event)){
			 result.put("waiverCreditApproverList", getAllWaiveCreditApprover()); 
		 }else{
			 result.put("waiverCreditApproverList", new ArrayList());
		 }
		 if(
//				 CollateralAction.MAKER_VIEW_ADD_DOC_FAC_DET _DEFERRED.equals(event) ||
//				 CollateralAction.CHECKER_VIEW_ADD_DOC_FAC_DET _DEFERRED.equals(event)  || CollateralAction.MAKER_CREATE_ADD_DOC_FAC_DET _DEFERRED.equals(event) ||
				  CollateralAction.MAKER_SUBMIT_INS_DEFERRED_ERROR.equals(event)  
//				 || CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET _DEFERRED.equals(event)
				 ||CollateralAction.MAKER_UPDATE_INSDEFERRED_LIST_ERROR.equals(event)){
			 result.put("deferCreditApproverList", getAllDeferCreditApprover()); 
		 }else{
			 result.put("deferCreditApproverList", new ArrayList());
		 }
		 
		//result.put("le_id_bca_ref_num", leIdAndBcaRefNum);
		//String event=(String)map.get("event");
		//result.put("event", event);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private List getAllDeferCreditApprover() {
		List lbValList = new ArrayList();
		try {
			
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			List defer = (List)proxy.getAllDeferCreditApprover();
			
			for (int i = 0; i < defer.size(); i++) {
				ICreditApproval creditApproval = (ICreditApproval)defer.get(i);
				
					String id = creditApproval.getApprovalCode();
					String val = creditApproval.getApprovalName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList); 
	}
	
	private List getAllWaiveCreditApprover() {
		List lbValList = new ArrayList();
		try {
			
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			List waive = (List)proxy.getAllWaiveCreditApprover();
			
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
	
	private IAddtionalDocumentFacilityDetails getItem(IAddtionalDocumentFacilityDetails temp[], String itemRef) {
		IAddtionalDocumentFacilityDetails item = null;
		if (temp == null) {
			return item;
		}
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getRefID().equals(itemRef)) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}

}
