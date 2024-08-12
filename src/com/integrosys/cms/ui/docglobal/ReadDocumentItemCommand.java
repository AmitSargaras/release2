package com.integrosys.cms.ui.docglobal;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.trx.IDocumentItemTrxValue;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.collateral.trx.assetlife.ICollateralAssetLifeTrxValue;
import com.integrosys.cms.app.collateral.trx.assetlife.ReadCollateralAssetLifeByTrxIDOperation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.documentlocation.proxy.DocumentItemProxyManagerFactory;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;

import java.util.HashMap;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/14 04:11:36 $ Tag: $Name: $
 */
public class ReadDocumentItemCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadDocumentItemCommand() {
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
								{ "startIndex", "java.lang.String", REQUEST_SCOPE }
		
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
				{ "docTrxObj", "com.integrosys.cms.app.chktemplate.trx.OBDocumentItemTrxValue", SERVICE_SCOPE },
				{ "appCodeValue", "java.util.Collection", REQUEST_SCOPE } ,
				{ "critical", "java.lang.String", SERVICE_SCOPE },
				{ "mandatory", "java.lang.String", SERVICE_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "documentItem", "com.integrosys.cms.app.chktemplate.bus.IDocumentItem", FORM_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE }});
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
		String docRefID = "";
		String startIndex =(String)map.get("startIndex");
		try {
			
			DefaultLogger.debug(this, "docTrxID" + docTrxID);
			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
			IDocumentItemTrxValue docTrxObj = proxy.getDocumentItemByTrxID(docTrxID);
			docRefID = docTrxObj.getReferenceID();
			DefaultLogger.debug(this, "docTrxObj after read" + docTrxObj);
			resultMap.put("documentItem", docTrxObj.getDocumentItem());
			resultMap.put("docTrxObj", docTrxObj);
			if((docTrxObj.getStatus().equals("PENDING_CREATE"))
					||(docTrxObj.getStatus().equals("PENDING_UPDATE"))
					||(docTrxObj.getStatus().equals("PENDING_DELETE"))
					||(docTrxObj.getStatus().equals("REJECTED"))
					||(docTrxObj.getStatus().equals("DRAFT")))
			{
				resultMap.put("wip", "wip");
			}
			if(docTrxObj.getStatus().startsWith("PENDING"))
				resultMap.put("appCodeValue", docTrxObj.getStagingDocumentItem().getCMRDocAppItemList());
			else
				resultMap.put("appCodeValue", docTrxObj.getDocumentItem().getCMRDocAppItemList());
			
			if("CAM".equals(docTrxObj.getDocumentItem().getItemType())){
				ITemplate itemTemplate;
				try {
					itemTemplate = proxy.getCAMTemplate("CAM", "CAM", "IN");
					if(itemTemplate !=null){
						ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
						if(icmsTrxValue!=null){
						ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
						if(trxValue!=null){
							if(!trxValue.getStatus().equals("ACTIVE")){
								resultMap.put("wip", "wip");
							}else{
								ITemplate template=trxValue.getTemplate();
								if(template!=null){
								ITemplateItem[] templateItem=template.getTemplateItemList();
									if(templateItem!=null){
											for(int i=0;i<templateItem.length;i++){
												if(templateItem[i]!=null){
													if(docTrxObj.getDocumentItem().getItemCode().trim().equals(templateItem[i].getItemCode())){
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
					}
				
				
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
			}
			
			
			
			
			if("O".equals(docTrxObj.getDocumentItem().getItemType())){
				ITemplate itemTemplate;
				try {
					itemTemplate = proxy.getCAMTemplate("O", "O", "IN");
					if(itemTemplate !=null){
						ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
						if(icmsTrxValue!=null){
						ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
						if(trxValue!=null){
							if(!trxValue.getStatus().equals("ACTIVE")){
								resultMap.put("wip", "wip");
							}else{
								ITemplate template=trxValue.getTemplate();
								if(template!=null){
								ITemplateItem[] templateItem=template.getTemplateItemList();
									if(templateItem!=null){
											for(int i=0;i<templateItem.length;i++){
												if(templateItem[i]!=null){
													if(docTrxObj.getDocumentItem().getItemCode().trim().equals(templateItem[i].getItemCode())){
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
					}
				
				
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
			}
			
			
			
			
			
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
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
		DefaultLogger.debug(this, "Going out of doExecute()");
		resultMap.put("startIndex",startIndex);
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
