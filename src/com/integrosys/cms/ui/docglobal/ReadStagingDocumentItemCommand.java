package com.integrosys.cms.ui.docglobal;

import java.util.Collection;
import java.util.HashMap;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.trx.IDocumentItemTrxValue;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/14 04:11:36 $ Tag: $Name: $
 */
public class ReadStagingDocumentItemCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadStagingDocumentItemCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "docTrxID", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "docTrxObj", "com.integrosys.cms.app.chktemplate.trx.OBDocumentItemTrxValue", SERVICE_SCOPE } });
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
				{ "documentItem", "com.integrosys.cms.app.chktemplate.bus.IDocumentItem", FORM_SCOPE },
				{ "closeFlag", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "critical", "java.lang.String", SERVICE_SCOPE },
				{ "mandatory", "java.lang.String", SERVICE_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "appCodeValue", "java.util.Collection", REQUEST_SCOPE } ,
				{ "docCodeNonUnique", "java.lang.Boolean", REQUEST_SCOPE } });
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
		String docTrxID = (String) map.get("docTrxID");
		String event = (String) map.get("event");
		String docRefID = "";
		ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();

		IDocumentItemTrxValue docTrxObj = null;
		if (docTrxID == null) {
			docTrxObj = (IDocumentItemTrxValue) map.get("docTrxObj");
		}
		else {
			try {
				docTrxObj = proxy.getDocumentItemByTrxID(docTrxID);
			}
			catch (CheckListTemplateException ex) {
				throw new CommandProcessingException("failed to retrieve document item workflow using trx id ["
						+ docTrxID + "]", ex);
			}
		}
		docRefID = docTrxObj.getReferenceID();
		IDocumentItem docItem = docTrxObj.getStagingDocumentItem();

		try {
			if ((EVENT_PROCESS.equals(event) || "edit_staging_doc_item".equals(event))) {
				if (docTrxObj.getDocumentItem() == null
						&& !proxy.isDocumentCodeUnique(docItem.getItemCode(), docItem.getItemType())) {
					ActionErrors errors = new ActionErrors();
					errors.add("itemCode", new ActionMessage("error.string.document.exist"));

					returnMap.put(MESSAGE_LIST, errors);
					resultMap.put("docCodeNonUnique", Boolean.TRUE);
				}
			}
		}
		catch (CheckListTemplateException ex) {
			throw new CommandProcessingException(
					"failed to retrieve the uniqueness of the document item, for doc code [" + docItem.getItemCode()
							+ "], type [" + docItem.getItemType() + "], transaction id [" + docTrxID + "]", ex);
		}
		
		// to assign referenceId to save/update application types in cms_doc_app_type table
		if("CAM".equals(docTrxObj.getStagingDocumentItem().getItemType())){
			ITemplate itemTemplate;
			try {
				itemTemplate = proxy.getCAMTemplate("CAM", "CAM", "IN");
				if(itemTemplate !=null){
					ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
					if(icmsTrxValue!=null){
					ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
					if(trxValue!=null){
							ITemplate template=trxValue.getStagingTemplate();
							if(template!=null){
							ITemplateItem[] templateItem=template.getTemplateItemList();
								if(templateItem!=null){
										for(int i=0;i<templateItem.length;i++){
											if(templateItem[i]!=null){
												if(docTrxObj.getStagingDocumentItem().getItemCode().trim().equals(templateItem[i].getItemCode())){
												String critical=String.valueOf(templateItem[i].getIsMandatoryInd());
												String mandatory=String.valueOf(templateItem[i].getIsMandatoryDisplayInd());
												resultMap.put("critical", critical);
												resultMap.put("mandatory", mandatory);
												}
											}
										}
									}
								}
						}
					}
				}
			
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		}
		
		
		if("O".equals(docTrxObj.getStagingDocumentItem().getItemType())){
			ITemplate itemTemplate;
			try {
				itemTemplate = proxy.getCAMTemplate("O", "O", "IN");
				if(itemTemplate !=null){
					ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
					if(icmsTrxValue!=null){
					ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
					if(trxValue!=null){
							ITemplate template=trxValue.getStagingTemplate();
							if(template!=null){
							ITemplateItem[] templateItem=template.getTemplateItemList();
								if(templateItem!=null){
										for(int i=0;i<templateItem.length;i++){
											if(templateItem[i]!=null){
												if(docTrxObj.getStagingDocumentItem().getItemCode().trim().equals(templateItem[i].getItemCode())){
												String critical=String.valueOf(templateItem[i].getIsMandatoryInd());
												String mandatory=String.valueOf(templateItem[i].getIsMandatoryDisplayInd());
												resultMap.put("critical", critical);
												resultMap.put("mandatory", mandatory);
												}
											}
										}
									}
								}
						}
					}
				}
			
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		}
		Collection docAppItemCollection;
		docAppItemCollection = docTrxObj.getStagingDocumentItem().getCMRDocAppItemList();

		LimitDAO limitDao = new LimitDAO();
		try {
			String migratedFlag = "N";	
			boolean status = false;	
			 status = limitDao.getCAMMigreted("CMS_DOCUMENT_GLOBALLIST",Long.parseLong(docRefID),"DOCUMENT_ID");
			
			if(status)
			{
				migratedFlag= "Y";
			}
			resultMap.put("migratedFlag", migratedFlag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		resultMap.put("documentItem", docItem);
		resultMap.put("docTrxObj", docTrxObj);
		resultMap.put("closeFlag", "true");
		resultMap.put("event", event);
		resultMap.put("appCodeValue", docAppItemCollection);
		
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
