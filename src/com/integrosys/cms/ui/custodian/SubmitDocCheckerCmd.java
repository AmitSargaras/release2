/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/custodian/SubmitDocCheckerCmd.java,v 1.6.12.1 2006/12/14 12:22:26 jychong Exp $
 */

package com.integrosys.cms.ui.custodian;

import java.util.HashMap;
import java.util.ArrayList;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.custodian.bus.ICustodianDocItem;
import com.integrosys.cms.app.custodian.proxy.CustodianProxyManagerFactory;
import com.integrosys.cms.app.custodian.proxy.ICustodianProxyManager;
import com.integrosys.cms.app.custodian.trx.ICustodianTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.checklist.CheckListUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;
import org.apache.struts.action.ActionMessage;

/**
 * Command class to Approve, Reject the lodge/relodge/uplift etc of custodian
 * doc by Custodian Checker
 * @author $Author: jychong $<br>
 * @version $Revision: 1.6.12.1 $
 * @since $Date: 2006/12/14 12:22:26 $ Tag: $Name: DEV_20060126_B286V1 $
 */
public class SubmitDocCheckerCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public SubmitDocCheckerCmd() {
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
				{ "custodianTrxVal", "com.integrosys.cms.app.custodian.trx.ICustodianTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "sess.reversalRemarks", "java.lang.String", SERVICE_SCOPE },
				{ "reversalRemarks", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
                { IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE },
                {"frame", "java.lang.String", SERVICE_SCOPE}});
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
        HashMap exceptionMap = new HashMap();
		ICustodianTrxValue iCustodianTrxValue = (ICustodianTrxValue) map.get("custodianTrxVal");
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		//theOBTrxContext.setTrxCountryOrigin(iCustodianTrxValue.getTrxContext()
		// .getTrxCountryOrigin());
		// DefaultLogger.debug(this,
		// "Inside doExecute() >> CustodianDoc  = "+iCustodianTrxValue);
		String event = (String) map.get("event");
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		String userInfo = "";
		userInfo = user == null ? "" : (user.getUserName() == null ? "" : user.getUserName());
		userInfo += user == null ? "" : (" ( " + (user.getLoginID() == null ? "" : user.getLoginID()) + " )");

		try {

			// Setting Reversal Remarks
			ICustodianDoc stageCustDoc = (ICustodianDoc) iCustodianTrxValue.getStagingCustodianDoc();
            ICustodianDoc actualCustDoc = (ICustodianDoc) iCustodianTrxValue.getCustodianDoc();
            ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
            ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
            String limitProfileStr = String.valueOf(limitProfile.getLimitProfileID());
            //[Start]
            //1. Check if the document item barcode is already exist in actual table if checker approve (not allow duplicate barcode)
            //2. Check if the security envelope is still available (not allow lodge to unavailable envelope)
            if (event.equals(CustodianAction.EVENT_APPROVE_DOC_CHECKER)){
                ArrayList docItemArray = stageCustDoc.getCustodianDocItems();
                boolean isExistDocItemBarcode = true;
                boolean isExistSecEnvBarcode = false;
                for(int i=0; i<docItemArray.size(); i++){
                    ICustodianDocItem docItem = (ICustodianDocItem)docItemArray.get(i);
                    String docItemBarcode = docItem.getCustodianDocItemBarcode();
                    String secEnvelopeBarcode = docItem.getSecEnvelopeBarcode();
                    String custStatus = docItem.getStatus();
                    if(custStatus.equals(ICMSConstant.STATE_AUTHZ_LODGE) || custStatus.equals(ICMSConstant.STATE_AUTHZ_RELODGED)){
                        isExistDocItemBarcode = proxy.getCheckDocItemBarcodeExist(docItemBarcode, docItem.getCheckListItemRefID());
                        isExistSecEnvBarcode = proxy.getCheckEnvelopeBarcodeExist(Long.parseLong(limitProfileStr), secEnvelopeBarcode);

                        if (isExistDocItemBarcode){
                            exceptionMap.put("barCodeErrorExist", new ActionMessage("error.custapp.docitem.barcodeAlreadyExist"));
                            break;
                        }
                        if(!isExistSecEnvBarcode){
                            exceptionMap.put("envBarCodeErrorNotExist", new ActionMessage("error.custapp.docitem.secenvbarcodeNotExist"));
                            break;
                        }
                    }
                }
            }
            //[End]
            if (exceptionMap.size() == 0) {
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
				    DefaultLogger.debug(this, "Staging Reversal Remarks Set " + setRevRemarks + " " + userInfo);
			    }
			    // theOBTrxContext.setTrxCountryOrigin(CheckListUtil.getTrxCountry(
			    // theOBTrxContext.getLimitProfile(),
			    // iCustodianTrxValue.getStagingCustodianDoc().getCheckListID()));
			    theOBTrxContext.setTrxCountryOrigin(CheckListUtil.getCustodianTrxCountry(theOBTrxContext.getTeam(),
					theOBTrxContext.getLimitProfile(), theOBTrxContext.getCustomer(), iCustodianTrxValue
							.getStagingCustodianDoc().getCheckListID()));
			    proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
			    ICMSTrxValue trxValue = null;
			    if ((event != null) && event.equals(CustodianAction.EVENT_REJECT_DOC_CHECKER)) {
				    trxValue = proxy.checkerRejectCustodianDoc(theOBTrxContext, iCustodianTrxValue);
			    }
			    if ((event != null) && event.equals(CustodianAction.EVENT_APPROVE_DOC_CHECKER)) {
				    trxValue = proxy.checkerApproveCustodianDoc(theOBTrxContext, iCustodianTrxValue);
			    }
			    resultMap.put("request.ITrxValue", trxValue);               
            }
            else{
                resultMap.put("frame", "false");
            }
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}