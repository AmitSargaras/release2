package com.integrosys.cms.ui.docglobal;

import java.util.HashMap;

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
import com.integrosys.cms.app.chktemplate.trx.OBTemplateTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.documentlocation.proxy.DocumentItemProxyManagerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/02 04:23:28 $ Tag: $Name: $
 */
public class ApproveDocumentItemCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ApproveDocumentItemCommand() {
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
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE },
				{ "docTrxID", "java.lang.String", REQUEST_SCOPE } });
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
			IDocumentItemTrxValue docTrxObj = (IDocumentItemTrxValue) map.get("docTrxObj");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");

			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory
					.getCheckListTemplateProxyManager();

			IDocumentItem docItem = docTrxObj.getStagingDocumentItem();
			if (docTrxObj.getDocumentItem() == null
					&& !proxy.isDocumentCodeUnique(docItem.getItemCode(), docItem.getItemType())) {
				exceptionMap.put("itemCode", new ActionMessage("error.string.document.exist.close.transaction"));
				resultMap.put("request.ITrxValue", docTrxObj);
				resultMap.put("docTrxID", docTrxObj.getTransactionID());

				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}

			docTrxObj = proxy.checkerApproveDocItem(ctx, docTrxObj);
			if(docItem.getItemType().trim().equals("CAM") ){
			ITemplate itemTemplate =  proxy.getCAMTemplate("CAM", "CAM", "IN");
				if(itemTemplate!=null){
			ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
					if(icmsTrxValue!=null){
			ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
						if(trxValue !=null){
//							ITemplateItem[] actualTemplateItems= itemTemplate.getTemplateItemList();
							ITemplate template=	trxValue.getStagingTemplate();
							ITemplateItem[] templateItems=template.getTemplateItemList();
								for(int i=0;i<templateItems.length;i++){
//									if(docItem.getItemCode().trim().equals(templateItems[i].getItem().getItemCode())){
									templateItems[i].getItem().setTenureCount(templateItems[i].getTenureCount());
									templateItems[i].getItem().setTenureType(templateItems[i].getTenureType());
//									}						
								}
								template.setTemplateItemList(templateItems);
								trxValue.setStagingTemplate(template);
//							itemTemplate.addItem(docItem);
//							trxValue.setStagingTemplate(itemTemplate);
			proxy.checkerApproveTemplate(ctx, trxValue);
							}
						}
					}
				}
			if(docItem.getItemType().trim().equals("O") ){
				ITemplate itemTemplate =  proxy.getCAMTemplate("O", "O", "IN");
					if(itemTemplate!=null){
				ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
						if(icmsTrxValue!=null){
				ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
							if(trxValue !=null){
							ITemplate template=	trxValue.getStagingTemplate();
					//		ITemplateItem[] actualTemplateItems= itemTemplate.getTemplateItemList();
							ITemplateItem[] templateItems=template.getTemplateItemList();
								for(int i=0;i<templateItems.length;i++){
									//if(docItem.getItemCode().trim().equals(templateItems[i].getItem().getItemCode())){
										templateItems[i].getItem().setTenureCount(templateItems[i].getTenureCount());
										templateItems[i].getItem().setTenureType(templateItems[i].getTenureType());
									//}
									/*else{
									
										for(int j=0;j<actualTemplateItems.length;j++){
											if(actualTemplateItems[j].getItemCode().trim().equals(templateItems[i].getItem().getItemCode())){
												templateItems[i].getItem().setTenureCount(actualTemplateItems[j].getTenureCount());
												templateItems[i].getItem().setTenureType(actualTemplateItems[j].getTenureType());
											}
										}	
										
										
									}*/
								}
								template.setTemplateItemList(templateItems);
								//itemTemplate.addItem(docItem);
								trxValue.setStagingTemplate(template);
				proxy.checkerApproveTemplate(ctx, trxValue);
								}
							}
						}
					}
			if(docItem.getItemType().trim().equals("REC") ){
				ITemplate itemTemplate =  proxy.getCAMTemplate("REC", "REC", "IN");
					if(itemTemplate!=null){
				ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
						if(icmsTrxValue!=null){
				ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
							if(trxValue !=null){
							ITemplate template=	trxValue.getStagingTemplate();
					//		ITemplateItem[] actualTemplateItems= itemTemplate.getTemplateItemList();
							ITemplateItem[] templateItems=template.getTemplateItemList();
							if(templateItems!=null){	
							for(int i=0;i<templateItems.length;i++){
									//if(docItem.getItemCode().trim().equals(templateItems[i].getItem().getItemCode())){
										templateItems[i].getItem().setTenureCount(templateItems[i].getTenureCount());
										templateItems[i].getItem().setTenureType(templateItems[i].getTenureType());
										
									//}
									/*else{
									
										for(int j=0;j<actualTemplateItems.length;j++){
											if(actualTemplateItems[j].getItemCode().trim().equals(templateItems[i].getItem().getItemCode())){
												templateItems[i].getItem().setTenureCount(actualTemplateItems[j].getTenureCount());
												templateItems[i].getItem().setTenureType(actualTemplateItems[j].getTenureType());
											}
										}	
										
										
									}*/
								}
								template.setTemplateItemList(templateItems);
							}
								//itemTemplate.addItem(docItem);
								trxValue.setStagingTemplate(template);
				proxy.checkerApproveTemplate(ctx, trxValue);
								}
							}
						}
					}
			resultMap.put("request.ITrxValue", docTrxObj);
		}
		catch (CheckListTemplateException ex) {
			throw new CommandProcessingException("failed to approve document item workflow", ex);
		}
		catch (Exception ex) {
			throw new CommandProcessingException("failed to approve document item workflow", ex);
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
