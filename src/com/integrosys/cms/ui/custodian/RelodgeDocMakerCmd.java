/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.custodian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.custodian.bus.CustodianSearchCriteria;
import com.integrosys.cms.app.custodian.bus.OBCustodianDoc;
import com.integrosys.cms.app.custodian.bus.OBCustodianDocItem;
import com.integrosys.cms.app.custodian.trx.ICustodianTrxValue;
import com.integrosys.cms.app.custodian.trx.OBCustodianTrxValue;
import com.integrosys.cms.app.custodian.proxy.CustodianProxyManagerFactory;
import com.integrosys.cms.app.custodian.proxy.ICustodianProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;
import org.apache.struts.action.ActionMessage;

/**
 * Command class to relodge the custodian doc by maker..
 * @author $Author: vishal $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2005/07/26 03:33:14 $ Tag: $Name: $
 */
public class RelodgeDocMakerCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public RelodgeDocMakerCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
                {"limitprofile", "java.lang.String", REQUEST_SCOPE},
				{ "aCustodianDoc", "com.integrosys.cms.app.custodian.bus.OBCustodianDoc", FORM_SCOPE },
				{ "custodianTrxVal", "com.integrosys.cms.app.custodian.trx.ICustodianTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				// CR-34
				{ "forId", "java.lang.String", REQUEST_SCOPE },
				{ "checkListItemRef", "java.lang.String", REQUEST_SCOPE },
				{ "checkListItemRefList", "java.util.ArrayList", SERVICE_SCOPE },// CR
																					// -
																					// 107
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
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE },
				// CR-34 modification
				{ "custodianTrxVal", "com.integrosys.cms.app.custodian.trx.ICustodianTrxValue", SERVICE_SCOPE },
				{ "forId", "java.lang.String", REQUEST_SCOPE },
				{ "checkListItemRefList", "java.util.ArrayList", SERVICE_SCOPE },// CR
																					// -
																					// 107
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();  
		HashMap exceptionMap = new HashMap();
		ICustodianTrxValue lodgeCustodianTrxVal = (OBCustodianTrxValue) map.get("custodianTrxVal");
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		//theOBTrxContext.setTrxCountryOrigin(lodgeCustodianTrxVal.getTrxContext
		// ().getTrxCountryOrigin());
		ICustodianDoc custDoc = (ICustodianDoc) map.get("aCustodianDoc");
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			// CR-34 modification
			/*
			 * theOBTrxContext.setTrxCountryOrigin(CheckListUtil.getTrxCountry(theOBTrxContext
			 * .getLimitProfile(), custDoc.getCheckListID()));
			 * ICustodianProxyManager proxy =
			 * CustodianProxyManagerFactory.getCustodianProxyManager();
			 * ICMSTrxValue trxValue =
			 * proxy.reLodgeDocMaker(theOBTrxContext,lodgeCustodianTrxVal
			 * ,custDoc); resultMap.put("request.ITrxValue",trxValue);
			 */
			resultMap.put("custodianTrxVal", lodgeCustodianTrxVal);
			String forID = (String) map.get("forId");
			String checkListItemRef = (String) map.get("checkListItemRef");
			ArrayList checkListItemRefList = (java.util.ArrayList) map.get("checkListItemRefList");// CR
																									// -
																									// 107
			if ((checkListItemRefList == null) || checkListItemRefList.isEmpty()) {
				checkListItemRefList = new ArrayList();
				checkListItemRefList.add(checkListItemRef);
			}
			else {
				checkListItemRefList.add(checkListItemRef);
			}
            // Customize for alliance
            ICustodianDoc custDocStgTrxVal = lodgeCustodianTrxVal.getStagingCustodianDoc();
            ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
            String limitprofile = (String) map.get("limitprofile");
            ArrayList custDocStgList = custDocStgTrxVal.getCustodianDocItems();
            OBCustodianDocItem obCustItem = new OBCustodianDocItem();
            String currSecEnvBarcode = "";
            String currDocItemBarcode = "";
            boolean isValidEnvBarcode = false;
            boolean isUniqueDocItemBarcode = false;
            boolean isInCurrUseDocItemBarcode = false;

            if (limitprofile != null){

                for (int i=0; i<custDocStgList.size(); i++){
                    obCustItem = (OBCustodianDocItem)custDocStgList.get(i);
                    if(obCustItem.getCheckListItemRefID() == Long.parseLong(checkListItemRef)){
                        currSecEnvBarcode = obCustItem.getSecEnvelopeBarcode();
                        currDocItemBarcode = obCustItem.getCustodianDocItemBarcode();
                    }
                }

                for (int j=0; j<custDocStgList.size(); j++){
                    obCustItem = (OBCustodianDocItem)custDocStgList.get(j);
                    if(obCustItem.getCustodianDocItemBarcode() != null){
                        if((obCustItem.getCustodianDocItemBarcode().equals(currDocItemBarcode)) && (obCustItem.getCheckListItemRefID() != Long.parseLong(checkListItemRef))){
                            isInCurrUseDocItemBarcode = true;
                            break;
                        }
                    }
                }

                isValidEnvBarcode = proxy.getCheckEnvelopeBarcodeExist(Long.parseLong(limitprofile), currSecEnvBarcode);
                isUniqueDocItemBarcode = proxy.getCheckDocItemBarcodeExist(currDocItemBarcode, Long.valueOf(checkListItemRef).longValue());

                if(!isValidEnvBarcode){
                    exceptionMap.put("secEnvBarcodeError", new ActionMessage("error.ccdoc.envelope.barcodeNotExist"));
                }
                if(!isUniqueDocItemBarcode || isInCurrUseDocItemBarcode){
                    exceptionMap.put("custDocItemBarcodeError", new ActionMessage("error.ccdoc.docitem.barcodeIsExist"));
                }
            }
            //end customization for alliance
			resultMap.put("forId", forID);
			resultMap.put("checkListItemRefList", checkListItemRefList);//CR-107
			// end CR-34 modification
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