package com.integrosys.cms.ui.docglobal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentItem;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.trx.IDocumentItemTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.documentlocation.bus.OBDocumentAppTypeItem;
import com.integrosys.cms.app.documentlocation.proxy.DocumentItemProxyManagerFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/02 04:23:28 $ Tag: $Name: $
 */
public class DraftDocumentItemCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	private  static boolean isDocumentCodeUnique = true;
	


	public static boolean getIsDocumentCodeUnique() {
		return isDocumentCodeUnique;
	}

	public static void setIsDocumentCodeUnique(boolean isDocumentCodeUnique) {
		DraftDocumentItemCommand.isDocumentCodeUnique = isDocumentCodeUnique;
	}

	public DraftDocumentItemCommand() {
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
                { "documentItem", "com.integrosys.cms.app.chktemplate.bus.OBDocumentItem", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }
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
                { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE }
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
			OBDocumentItem docItem = (OBDocumentItem) map.get("documentItem");
			//DefaultLogger.debug(this, "docItem before create" + docItem);
			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
            boolean isDocumentCodeUnique = proxy.isDocumentCodeUnique(docItem.getItemCode().trim(), docItem.getItemType());

			if(!isDocumentCodeUnique){
				exceptionMap.put("itemCode", new ActionMessage("error.string.document.exist"));
				IDocumentItemTrxValue docTrxObj = null;
				resultMap.put("request.ITrxValue", docTrxObj);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}
			
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IDocumentItemTrxValue docTrxObj = null;

			
			
//			String[] appListing = docItem.getLoanApplicationType().split("-");
//			List appList = new ArrayList();
//			
//			for (int i = 0; i < appListing.length; i++)
//			{
//				OBDocumentAppTypeItem  obDocAppTypeItem= new OBDocumentAppTypeItem();
//				obDocAppTypeItem.setAppType(appListing[i]);
//				appList.add(obDocAppTypeItem);
//			}
//			
//			docItem.setCMRDocAppItemList(appList);
			
			docTrxObj = proxy.makerDraftDocItem(ctx, docItem);
			resultMap.put("request.ITrxValue", docTrxObj);
			resultMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			DefaultLogger.debug(this, "History ID>>>>>>>>>>>>>" + docTrxObj.getCurrentTrxHistoryID());
			DefaultLogger.debug(this, "docTrxObj after update" + docTrxObj);
			
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
