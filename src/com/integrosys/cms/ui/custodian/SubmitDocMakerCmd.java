/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/custodian/SubmitDocMakerCmd.java,v 1.9.12.1 2006/12/14 12:22:26 jychong Exp $
 */

package com.integrosys.cms.ui.custodian;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.custodian.proxy.CustodianProxyManagerFactory;
import com.integrosys.cms.app.custodian.proxy.ICustodianProxyManager;
import com.integrosys.cms.app.custodian.trx.ICustodianTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.checklist.CheckListUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Command class to Save, Submit the lodge/relodge/uplift etc of custodian doc
 * by Custodian Maker
 * @author $Author: jychong $<br>
 * @version $Revision: 1.9.12.1 $
 * @since $Date: 2006/12/14 12:22:26 $ Tag: $Name: DEV_20060126_B286V1 $
 */
public class SubmitDocMakerCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public SubmitDocMakerCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "custodianTrxVal", "com.integrosys.cms.app.custodian.trx.ICustodianTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "checkListItemRefList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "sess.reversalRemarks", "java.lang.String", SERVICE_SCOPE },
				{ "reversalRemarks", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE }, });
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
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE },
				{ "checkListItemRefList", "java.util.ArrayList", SERVICE_SCOPE },

		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Here Checker Approve Update Doc is called.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		ICustodianTrxValue iCustodianTrxValue = (ICustodianTrxValue) map.get("custodianTrxVal");
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		//theOBTrxContext.setTrxCountryOrigin(iCustodianTrxValue.getTrxContext()
		// .getTrxCountryOrigin());
		// DefaultLogger.debug(this, "Inside doExecute()");
		String event = (String) map.get("event");
		String remarks = (String) map.get("remarks");
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		String userInfo = "";
		userInfo = user == null ? "" : (user.getUserName() == null ? "" : user.getUserName());
		userInfo += user == null ? "" : (" ( " + (user.getLoginID() == null ? "" : user.getLoginID()) + " )");

		ArrayList checkListItemRefList = (java.util.ArrayList) map.get("checkListItemRefList"); // CR
																								// -
																								// 107
		try {
			// theOBTrxContext.setTrxCountryOrigin(CheckListUtil.getTrxCountry(
			// theOBTrxContext.getLimitProfile(),
			// iCustodianTrxValue.getStagingCustodianDoc().getCheckListID()));
			theOBTrxContext.setTrxCountryOrigin(CheckListUtil.getCustodianTrxCountry(theOBTrxContext.getTeam(),
					theOBTrxContext.getLimitProfile(), theOBTrxContext.getCustomer(), iCustodianTrxValue
							.getStagingCustodianDoc().getCheckListID()));
			ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
			ICMSTrxValue trxValue = null;
			/*
			 * if (iCustodianTrxValue.getStatus() != null &&
			 * iCustodianTrxValue.getStatus().equals(ICMSConstant.STATE_ND)){ if
			 * (event != null &&
			 * event.equals(CustodianAction.EVENT_SAVE_CUST_DOC_MAKER)) trxValue
			 * =
			 * proxy.makerCreateCustodianDoc(theOBTrxContext,iCustodianTrxValue
			 * ); if (event != null &&
			 * event.equals(CustodianAction.EVENT_SUBMIT_CUST_DOC_MAKER))
			 * trxValue =
			 * proxy.makerCreateCustodianDoc(theOBTrxContext,iCustodianTrxValue
			 * ); if (event != null &&
			 * event.equals(CustodianAction.EVENT_CLOSE_CUST_DOC_MAKER))
			 * trxValue =
			 * proxy.makerCloseCreateCustodianDoc(theOBTrxContext,iCustodianTrxValue
			 * ); }else{
			 */
			iCustodianTrxValue.setRemarks(remarks);
			// Setting Reversal Remarks
			ICustodianDoc stageCustDoc = (ICustodianDoc) iCustodianTrxValue.getStagingCustodianDoc();
			String strRevRemarks = (String) map.get("reversalRemarks");
			String strSessRevRemarks = (String) map.get("sess.reversalRemarks");
			String setRevRemarks = "";

			if ((strSessRevRemarks != null) && !strSessRevRemarks.equals("")) {
				setRevRemarks = strSessRevRemarks;
			}
			if (strRevRemarks != null) {
				setRevRemarks = strRevRemarks;
			}

			String stagingRevRemarks = "";
			stagingRevRemarks = stageCustDoc.getReversalRemarks() == null ? "" : stageCustDoc.getReversalRemarks();

			if (!setRevRemarks.equalsIgnoreCase(stagingRevRemarks)) {
				stageCustDoc.setReversalRemarks(setRevRemarks);
				stageCustDoc.setReversalRmkUpdatedUserInfo(userInfo);
				iCustodianTrxValue.setStagingCustodianDoc(stageCustDoc);
			}

			if ((event != null)
					&& (event.equals(CustodianAction.EVENT_SAVE_CUST_DOC_MAKER) || event
							.equals(CustodianAction.EVENT_SAVE_TODO_CUST_DOC_MAKER))) {
				if ((checkListItemRefList != null) && !checkListItemRefList.isEmpty())// CR
																						// -
																						// 107
				{
					ICustodianDoc stagingCustodianDoc = (ICustodianDoc) iCustodianTrxValue.getStagingCustodianDoc();
					stagingCustodianDoc.setUpdatedCheckListItemRefArrayList(checkListItemRefList);
					iCustodianTrxValue.setStagingCustodianDoc(stagingCustodianDoc);
				}// CR-107
				trxValue = proxy.makerSaveCustodianDoc(theOBTrxContext, iCustodianTrxValue);
			}

			if ((event != null)
					&& (event.equals(CustodianAction.EVENT_SUBMIT_CUST_DOC_MAKER) || event
							.equals(CustodianAction.EVENT_SUBMIT_TODO_CUST_DOC_MAKER))) {
				if ((checkListItemRefList != null) && !checkListItemRefList.isEmpty())// CR
																						// -
																						// 107
				{
					ICustodianDoc stagingCustodianDoc = (ICustodianDoc) iCustodianTrxValue.getStagingCustodianDoc();
					stagingCustodianDoc.setUpdatedCheckListItemRefArrayList(checkListItemRefList);
					iCustodianTrxValue.setStagingCustodianDoc(stagingCustodianDoc);
				}// CR-107

				trxValue = proxy.makerUpdateCustodianDoc(theOBTrxContext, iCustodianTrxValue);// CR
																								// -
																								// 107
				if ((checkListItemRefList != null) && !checkListItemRefList.isEmpty())// CR
																						// -
																						// 107
				{
					checkListItemRefList.clear();
				}

			}
			if ((event != null) && event.equals(CustodianAction.EVENT_CLOSE_CUST_DOC_MAKER)) {
				trxValue = proxy.makerCloseCustodianDoc(theOBTrxContext, iCustodianTrxValue);
			}

			resultMap.put("request.ITrxValue", trxValue);
			resultMap.put("checkListItemRefList", checkListItemRefList);//CR-107
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