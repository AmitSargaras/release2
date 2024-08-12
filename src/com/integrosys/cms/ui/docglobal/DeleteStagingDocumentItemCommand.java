package com.integrosys.cms.ui.docglobal;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentItem;
import com.integrosys.cms.app.chktemplate.trx.IDocumentItemTrxValue;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.documentlocation.bus.OBDocumentAppTypeItem;
import com.integrosys.cms.app.documentlocation.proxy.DocumentItemProxyManagerFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/02 04:23:28 $ Tag: $Name: $
 */
public class DeleteStagingDocumentItemCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public DeleteStagingDocumentItemCommand() {
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
				{ "stgDocumentItem", "com.integrosys.cms.app.chktemplate.bus.OBDocumentItem", FORM_SCOPE },
				{ "docTrxObj", "com.integrosys.cms.app.chktemplate.trx.OBDocumentItemTrxValue", SERVICE_SCOPE },
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
		return (new String[][] {
				{ "docTrxObj", "com.integrosys.cms.app.chktemplate.trx.OBDocumentItemTrxValue", SERVICE_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE } });
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
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			OBDocumentItem docItem = (OBDocumentItem) map.get("stgDocumentItem");
			IDocumentItemTrxValue docTrxObj = (IDocumentItemTrxValue) map.get("docTrxObj");
			DefaultLogger.debug(this, "docTrxObj before Update" + docTrxObj);
			DefaultLogger.debug(this, "docItem before Update" + docItem);
			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();

			// to assign referenceId to save/update application types in cms_doc_app_type table
			
			String referenceId = docTrxObj.getReferenceID();
			
			if(referenceId == null)
			{
				referenceId = docTrxObj.getStagingReferenceID();
			}
			
			//String[] appListing = docItem.getLoanApplicationType().split("-");
			//List appList = new ArrayList();
			
			//for (int i = 0; i < appListing.length; i++)
			//{
			//	OBDocumentAppTypeItem  obDocAppTypeItem= new OBDocumentAppTypeItem();
		//		obDocAppTypeItem.setAppType(appListing[i]);
		//		appList.add(obDocAppTypeItem);
		//	}
			
			//docItem.setCMRDocAppItemList(appList);
			
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			docTrxObj = proxy.makerEditRejectedDocItemTrx(ctx, docTrxObj, docItem);
			DefaultLogger.debug(this, "docTrxObj after update" + docTrxObj);
			resultMap.put("docTrxObj", docTrxObj);
			resultMap.put("request.ITrxValue", docTrxObj);
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
