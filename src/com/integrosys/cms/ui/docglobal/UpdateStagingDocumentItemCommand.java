package com.integrosys.cms.ui.docglobal;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentItem;
import com.integrosys.cms.app.chktemplate.trx.IDocumentItemTrxValue;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.documentlocation.bus.OBDocumentAppTypeItem;
import com.integrosys.cms.app.documentlocation.proxy.DocumentItemProxyManagerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/02 04:23:28 $ Tag: $Name: $
 */
public class UpdateStagingDocumentItemCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public UpdateStagingDocumentItemCommand() {
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
				 { "mandatoryID", "java.lang.String", REQUEST_SCOPE },
					{ "mandatoryDisplayID", "java.lang.String", REQUEST_SCOPE },
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
			String mandatoryID = (String) map.get("mandatoryID");
			
			String mandatoryDisplayID = (String) map.get("mandatoryDisplayID");
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
			
			
			if(docTrxObj.getStagingDocumentItem().getItemType().trim().equals("CAM") ){
				
				docItem.setItemID(-999999999);
				ITemplate itemTemplate =  proxy.getCAMTemplate("CAM", "CAM", "IN");
				boolean docFound=false;
				int index=0;
				if(itemTemplate!=null){
						for(int i=0;i<itemTemplate.getTemplateItemList().length;i++){
							if(itemTemplate.getTemplateItemList()[i]!=null){
								
								if(docItem.getItemCode().trim().equals(itemTemplate.getTemplateItemList()[i].getItemCode())){
									docFound=true;
									index=i;
								}
								
							}
						}
						if(docFound){
							itemTemplate.updateItem(index, docItem);
							if(mandatoryID.trim().equals("on")){
								itemTemplate.getTemplateItemList()[index].setIsMandatoryInd(true);
							}else{
								itemTemplate.getTemplateItemList()[index].setIsMandatoryInd(false);
							}
							if(mandatoryDisplayID .trim().equals("on")){
								itemTemplate.getTemplateItemList()[index].setIsMandatoryDisplayInd(true);
							}else{
								itemTemplate.getTemplateItemList()[index].setIsMandatoryDisplayInd(false);
							}
						}else{
							itemTemplate.addItem(docItem);
							if(mandatoryID.trim().equals("on")){
								itemTemplate.getTemplateItemList()[itemTemplate.getTemplateItemList().length-1].setIsMandatoryInd(true);
							}else{
								itemTemplate.getTemplateItemList()[itemTemplate.getTemplateItemList().length-1].setIsMandatoryInd(false);
							}
							if(mandatoryDisplayID .trim().equals("on")){
								itemTemplate.getTemplateItemList()[itemTemplate.getTemplateItemList().length-1].setIsMandatoryDisplayInd(true);
							}else{
								itemTemplate.getTemplateItemList()[itemTemplate.getTemplateItemList().length-1].setIsMandatoryDisplayInd(false);
							}
						}
					
							
				ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
						if(icmsTrxValue!=null){
				ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
				//proxy.makerCloseTemplateTrx(ctx, trxValue);
				proxy.makerUpdateTemplate(ctx, trxValue, itemTemplate);
							}
						}
					}
				if(docTrxObj.getStagingDocumentItem().getItemType().trim().equals("O") ){
					docItem.setItemID(-999999999);
					ITemplate itemTemplate =  proxy.getCAMTemplate("O", "O", "IN");
					boolean docFound=false;
					int index=0;
					if(itemTemplate!=null){
							for(int i=0;i<itemTemplate.getTemplateItemList().length;i++){
								if(itemTemplate.getTemplateItemList()[i]!=null){
									
									if(docItem.getItemCode().trim().equals(itemTemplate.getTemplateItemList()[i].getItemCode())){
										docFound=true;
										index=i;
									}
									
								}
							}
							if(docFound){
								itemTemplate.updateItem(index, docItem);
								if(mandatoryID.trim().equals("on")){
									itemTemplate.getTemplateItemList()[index].setIsMandatoryInd(true);
								}else{
									itemTemplate.getTemplateItemList()[index].setIsMandatoryInd(false);
								}
								if(mandatoryDisplayID .trim().equals("on")){
									itemTemplate.getTemplateItemList()[index].setIsMandatoryDisplayInd(true);
								}else{
									itemTemplate.getTemplateItemList()[index].setIsMandatoryDisplayInd(false);
								}
							}else{
								itemTemplate.addItem(docItem);
								if(mandatoryID.trim().equals("on")){
									itemTemplate.getTemplateItemList()[itemTemplate.getTemplateItemList().length-1].setIsMandatoryInd(true);
								}else{
									itemTemplate.getTemplateItemList()[itemTemplate.getTemplateItemList().length-1].setIsMandatoryInd(false);
								}
								if(mandatoryDisplayID .trim().equals("on")){
									itemTemplate.getTemplateItemList()[itemTemplate.getTemplateItemList().length-1].setIsMandatoryDisplayInd(true);
								}else{
									itemTemplate.getTemplateItemList()[itemTemplate.getTemplateItemList().length-1].setIsMandatoryDisplayInd(false);
								}
							}
						
								
					ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
							if(icmsTrxValue!=null){
					ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
					//proxy.makerCloseTemplateTrx(ctx, trxValue);
					proxy.makerUpdateTemplate(ctx, trxValue, itemTemplate);
								}
							}
						}
				if(docTrxObj.getStagingDocumentItem().getItemType().trim().equals("REC") ){
					docItem.setItemID(-999999999);
					ITemplate itemTemplate =  proxy.getCAMTemplate("REC", "REC", "IN");
					boolean docFound=false;
					int index=0;
					if(itemTemplate!=null){
							for(int i=0;i<itemTemplate.getTemplateItemList().length;i++){
								if(itemTemplate.getTemplateItemList()[i]!=null){
									
									if(docItem.getItemCode().trim().equals(itemTemplate.getTemplateItemList()[i].getItemCode())){
										docFound=true;
										index=i;
									}
									
								}
							}
							if(docFound){
								itemTemplate.updateItem(index, docItem);
								/*if(mandatoryID.trim().equals("on")){
									itemTemplate.getTemplateItemList()[index].setIsMandatoryInd(true);
								}else{
									itemTemplate.getTemplateItemList()[index].setIsMandatoryInd(false);
								}
								if(mandatoryDisplayID .trim().equals("on")){
									itemTemplate.getTemplateItemList()[index].setIsMandatoryDisplayInd(true);
								}else{
									itemTemplate.getTemplateItemList()[index].setIsMandatoryDisplayInd(false);
								}*/
								itemTemplate.getTemplateItemList()[index].setIsMandatoryInd(true);
								itemTemplate.getTemplateItemList()[index].setIsMandatoryDisplayInd(true);
							}else{
								itemTemplate.addItem(docItem);
								/*if(mandatoryID.trim().equals("on")){
									itemTemplate.getTemplateItemList()[itemTemplate.getTemplateItemList().length-1].setIsMandatoryInd(true);
								}else{
									itemTemplate.getTemplateItemList()[itemTemplate.getTemplateItemList().length-1].setIsMandatoryInd(false);
								}
								if(mandatoryDisplayID .trim().equals("on")){
									itemTemplate.getTemplateItemList()[itemTemplate.getTemplateItemList().length-1].setIsMandatoryDisplayInd(true);
								}else{
									itemTemplate.getTemplateItemList()[itemTemplate.getTemplateItemList().length-1].setIsMandatoryDisplayInd(false);
								}*/
								itemTemplate.getTemplateItemList()[itemTemplate.getTemplateItemList().length-1].setIsMandatoryInd(true);
								itemTemplate.getTemplateItemList()[itemTemplate.getTemplateItemList().length-1].setIsMandatoryDisplayInd(true);
							}
						
								
					ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
							if(icmsTrxValue!=null){
					ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
					//proxy.makerCloseTemplateTrx(ctx, trxValue);
					proxy.makerUpdateTemplate(ctx, trxValue, itemTemplate);
								}
							}
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
