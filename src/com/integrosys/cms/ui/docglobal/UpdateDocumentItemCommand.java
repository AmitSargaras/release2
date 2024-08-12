package com.integrosys.cms.ui.docglobal;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentItem;
import com.integrosys.cms.app.chktemplate.trx.IDocumentItemTrxValue;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.documentlocation.proxy.DocumentItemProxyManagerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;

import java.util.HashMap;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/05 10:33:03 $ Tag: $Name: $
 */
public class UpdateDocumentItemCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public UpdateDocumentItemCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "documentItem", "com.integrosys.cms.app.chktemplate.bus.OBDocumentItem", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				  { "mandatoryID", "java.lang.String", REQUEST_SCOPE },
					{ "mandatoryDisplayID", "java.lang.String", REQUEST_SCOPE },
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
		String event = (String) map.get("event");
		try {
			OBDocumentItem docItem = (OBDocumentItem) map.get("documentItem");
			IDocumentItemTrxValue docTrxObj = (IDocumentItemTrxValue) map.get("docTrxObj");
			DefaultLogger.debug(this, "docTrxObj before Update" + docTrxObj);
			DefaultLogger.debug(this, "docItem before Update" + docItem);
			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			
			if(docTrxObj.getFromState().equals("PENDING_PERFECTION")){
				docTrxObj = proxy.makerUpdateDraftCreateDocItem(ctx, docTrxObj, docItem);
			}else{
				docTrxObj = proxy.makerUpdateDocItem(ctx, docTrxObj, docItem);
			}
			if(docItem.getItemType().trim().equals("CAM") ){
				String mandatoryID = (String) map.get("mandatoryID");
				
				String mandatoryDisplayID = (String) map.get("mandatoryDisplayID");
				ITemplate itemTemplate =  proxy.getCAMTemplate("CAM", "CAM", "IN");
				if(itemTemplate !=null){
				//itemTemplate.addItem(docItem);
				ITemplateItem temp[] = itemTemplate.getTemplateItemList();
				if (temp != null) {
					for (int i = 0; i < temp.length; i++) {
						if (temp[i].getItem().getItemCode().equals(docItem.getItemCode())) {
							
							if(mandatoryID.trim().equals("on")){
								itemTemplate.getTemplateItemList()[i].setIsMandatoryInd(true);
							}else{
								itemTemplate.getTemplateItemList()[i].setIsMandatoryInd(false);
							}
							if(mandatoryDisplayID .trim().equals("on")){
								itemTemplate.getTemplateItemList()[i].setIsMandatoryDisplayInd(true);
							}else{
								itemTemplate.getTemplateItemList()[i].setIsMandatoryDisplayInd(false);
							}
						
							}
						}
					}
				}
				ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
				ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
				proxy.makerUpdateTemplate(ctx, trxValue, itemTemplate);
			}
			
			if(docItem.getItemType().trim().equals("O") ){
				String mandatoryID = (String) map.get("mandatoryID");
				
				String mandatoryDisplayID = (String) map.get("mandatoryDisplayID");
				ITemplate itemTemplate =  proxy.getCAMTemplate("O", "O", "IN");
				if(itemTemplate !=null){
				//itemTemplate.addItem(docItem);
				ITemplateItem temp[] = itemTemplate.getTemplateItemList();
				if (temp != null) {
					for (int i = 0; i < temp.length; i++) {
						if (temp[i].getItem().getItemCode().equals(docItem.getItemCode())) {
							
							if(mandatoryID.trim().equals("on")){
								itemTemplate.getTemplateItemList()[i].setIsMandatoryInd(true);
							}else{
								itemTemplate.getTemplateItemList()[i].setIsMandatoryInd(false);
							}
							if(mandatoryDisplayID .trim().equals("on")){
								itemTemplate.getTemplateItemList()[i].setIsMandatoryDisplayInd(true);
							}else{
								itemTemplate.getTemplateItemList()[i].setIsMandatoryDisplayInd(false);
							}
						
							}
						}
					}
				}
				ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
				ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
				proxy.makerUpdateTemplate(ctx, trxValue, itemTemplate);
			}
			
			if(docItem.getItemType().trim().equals("REC") ){
				String mandatoryID = (String) map.get("mandatoryID");
				
				String mandatoryDisplayID = (String) map.get("mandatoryDisplayID");
				ITemplate itemTemplate =  proxy.getCAMTemplate("REC", "REC", "IN");
				if(itemTemplate !=null){
				//itemTemplate.addItem(docItem);
				ITemplateItem temp[] = itemTemplate.getTemplateItemList();
				if (temp != null) {
					for (int i = 0; i < temp.length; i++) {
						if (temp[i].getItem().getItemCode().equals(docItem.getItemCode())) {
							
							/*if(mandatoryID.trim().equals("on")){
								itemTemplate.getTemplateItemList()[i].setIsMandatoryInd(true);
							}else{
								itemTemplate.getTemplateItemList()[i].setIsMandatoryInd(false);
							}
							if(mandatoryDisplayID .trim().equals("on")){
								itemTemplate.getTemplateItemList()[i].setIsMandatoryDisplayInd(true);
							}else{
								itemTemplate.getTemplateItemList()[i].setIsMandatoryDisplayInd(false);
							}*/
							itemTemplate.getTemplateItemList()[i].setIsMandatoryInd(true);
							itemTemplate.getTemplateItemList()[i].setIsMandatoryDisplayInd(true);
							}
						}
					}
				}
				ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
				ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
				proxy.makerUpdateTemplate(ctx, trxValue, itemTemplate);
			}
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
	private SBCMSTrxManager getTrxManager() throws TrxOperationException {
		SBCMSTrxManager mgr = (SBCMSTrxManager) BeanController.getEJB(ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME,
				SBCMSTrxManagerHome.class.getName());

		if (null == mgr) {
			throw new TrxOperationException("failed to find cms trx manager remote interface using jndi name ["
					+ ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME + "]");
		}
		else {
			return mgr;
		}
	}
}
