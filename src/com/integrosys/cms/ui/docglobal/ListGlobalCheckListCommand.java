package com.integrosys.cms.ui.docglobal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;
/**
 * Command to prepare seach criteria and search the relevant documents at global
 * level according to the criteria.
 * @author elango
 * @author Chong Jun Yong
 * @since 2003/08/11
 */
public class ListGlobalCheckListCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * To indicate whether to sort the document search result before passing to
	 * presentation layer
	 */
	private boolean toSortDocumentSearchResult = true;

	public ListGlobalCheckListCommand() {
	}

	public ListGlobalCheckListCommand(boolean toSortDocumentSearchResult) {
		this.toSortDocumentSearchResult = toSortDocumentSearchResult;
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "type", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "view", "java.lang.String", REQUEST_SCOPE },
				{ "sview", "java.lang.String", SERVICE_SCOPE },
				{ "secDocCode", "java.lang.String", REQUEST_SCOPE },
				{ "secDocDesc", "java.lang.String", REQUEST_SCOPE },
				{ "secDocTenureCount", "java.lang.String", REQUEST_SCOPE },
				{ "secDocTenureType", "java.lang.String", REQUEST_SCOPE },
				{ "secDocCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "secDocDescSession", "java.lang.String", SERVICE_SCOPE },
				{ "secDocTenureCountSession", "java.lang.String", SERVICE_SCOPE },
				{ "secDocTenureTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ "facDocCode", "java.lang.String", REQUEST_SCOPE },
				{ "facDocDesc", "java.lang.String", REQUEST_SCOPE },
				{ "facDocTenureCount", "java.lang.String", REQUEST_SCOPE },
				{ "facDocTenureType", "java.lang.String", REQUEST_SCOPE },
				{ "facDocCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "facDocDescSession", "java.lang.String", SERVICE_SCOPE },
				{ "facDocTenureCountSession", "java.lang.String", SERVICE_SCOPE },
				{ "facDocTenureTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ "camDocCode", "java.lang.String", REQUEST_SCOPE },
				{ "camDocDesc", "java.lang.String", REQUEST_SCOPE },
				{ "camDocTenureCount", "java.lang.String", REQUEST_SCOPE },
				{ "camDocTenureType", "java.lang.String", REQUEST_SCOPE },
				{ "camDocCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "camDocDescSession", "java.lang.String", SERVICE_SCOPE },
				{ "camDocTenureCountSession", "java.lang.String", SERVICE_SCOPE },
				{ "camDocTenureTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ "otherDocCode", "java.lang.String", REQUEST_SCOPE },
				{ "otherDocDesc", "java.lang.String", REQUEST_SCOPE },
				{ "otherDocTenureCount", "java.lang.String", REQUEST_SCOPE },
				{ "otherDocTenureType", "java.lang.String", REQUEST_SCOPE },
				{ "otherDocCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "otherDocDescSession", "java.lang.String", SERVICE_SCOPE },
				{ "otherDocTenureCountSession", "java.lang.String", SERVICE_SCOPE },
				{ "otherDocTenureTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ "statType", "java.lang.String", REQUEST_SCOPE },
				{ "statDesc", "java.lang.String", REQUEST_SCOPE },
				{ "statTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ "statDescSession", "java.lang.String", SERVICE_SCOPE },
				{ "go", "java.lang.String", REQUEST_SCOPE },
				{ "criteriaSession", "com.integrosys.cms.app.chktemplate.bus.DocumentSearchCriteria", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] { { "globalDocChkList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "view", "java.lang.String", REQUEST_SCOPE },
				{ "secDocCode", "java.lang.String", REQUEST_SCOPE },
				{ "secDocDesc", "java.lang.String", REQUEST_SCOPE },
				{ "secDocTenureCount", "java.lang.String", REQUEST_SCOPE },
				{ "secDocTenureType", "java.lang.String", REQUEST_SCOPE },
				{ "secDocCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "secDocDescSession", "java.lang.String", SERVICE_SCOPE },
				{ "secDocTenureCountSession", "java.lang.String", SERVICE_SCOPE },
				{ "secDocTenureTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ "facDocCode", "java.lang.String", REQUEST_SCOPE },
				{ "facDocDesc", "java.lang.String", REQUEST_SCOPE },
				{ "facDocTenureCount", "java.lang.String", REQUEST_SCOPE },
				{ "facDocTenureType", "java.lang.String", REQUEST_SCOPE },
				{ "facDocCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "facDocDescSession", "java.lang.String", SERVICE_SCOPE },
				{ "facDocTenureCountSession", "java.lang.String", SERVICE_SCOPE },
				{ "facDocTenureTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ "camDocCode", "java.lang.String", REQUEST_SCOPE },
				{ "camDocDesc", "java.lang.String", REQUEST_SCOPE },
				{ "camDocTenureCount", "java.lang.String", REQUEST_SCOPE },
				{ "camDocTenureType", "java.lang.String", REQUEST_SCOPE },
				{ "camDocCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "camDocDescSession", "java.lang.String", SERVICE_SCOPE },
				{ "camDocTenureCountSession", "java.lang.String", SERVICE_SCOPE },
				{ "camDocTenureTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ "otherDocCode", "java.lang.String", REQUEST_SCOPE },
				{ "otherDocDesc", "java.lang.String", REQUEST_SCOPE },
				{ "otherDocTenureCount", "java.lang.String", REQUEST_SCOPE },
				{ "otherDocTenureType", "java.lang.String", REQUEST_SCOPE },
				{ "otherDocCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "otherDocDescSession", "java.lang.String", SERVICE_SCOPE },
				{ "otherDocTenureCountSession", "java.lang.String", SERVICE_SCOPE },
				{ "otherDocTenureTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ "statType", "java.lang.String", REQUEST_SCOPE },
				{ "statDesc", "java.lang.String", REQUEST_SCOPE },
				{ "statTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ "statDescSession", "java.lang.String", SERVICE_SCOPE },
				{ "sview", "java.lang.String", SERVICE_SCOPE },
				{ "recurrentStatTypeList", "java.util.List", SERVICE_SCOPE },
				{ "tenureTypeList", "java.util.List", SERVICE_SCOPE },
				{ "criteriaSession", "com.integrosys.cms.app.chktemplate.bus.DocumentSearchCriteria", SERVICE_SCOPE },
				{ "searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
		
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
		String chkTemplateType = (String) map.get("type");
		String view = (String) map.get("view");
		String sview = (String) map.get("sview");
		if(view!=null){
		if(view.trim().equals("view")){
			resultMap.put("sview", view);
			resultMap.put("view", "view");
		}else{
			if(sview.trim().equals("view")){
				resultMap.put("view", "view");
			}	
		}
		}else{
			if(sview!=null){
				if(sview.trim().equals("view")){
					resultMap.put("view", "view");
				}	
			}
		}
		DocumentSearchCriteria criteria = new DocumentSearchCriteria();
		criteria.setDocumentType(chkTemplateType);
		DefaultLogger.debug(this, "Inside doExecute(), type=" + chkTemplateType);
		ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
		ArrayList globalDocChkList = new ArrayList();
		String startIdx = (String) map.get("startIndex");
		resultMap.put("startIndex", startIdx);
		if("CAM".equals(chkTemplateType)){
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
						}
					}
					}
				}
			
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		}
		

		if("O".equals(chkTemplateType)){
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
						}
					}
					}
				}
			
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if("REC".equals(chkTemplateType)){
			criteria.setFirstSort(" common_code_category_entry.entry_name ");
			criteria.setSecondSort(" CMS_DOCUMENT_GLOBALLIST.DOCUMENT_DESCRIPTION ");
			ITemplate itemTemplate;
			try {
				itemTemplate = proxy.getCAMTemplate("REC", "REC", "IN");
				if(itemTemplate !=null){
					ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
					if(icmsTrxValue!=null){
					ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
					if(trxValue!=null){
						if(!trxValue.getStatus().equals("ACTIVE")){
							resultMap.put("wip", "wip");
						}
					}
					}
				}
			
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		
		if(null!=criteria){
			if(null!=criteria.getDocumentType())
			resultMap.put("criteriaSession",criteria);
		}
		SearchResult sr = null;
		try {
			String go=(String) map.get("go");
			// recurrent fields
			String statType=(String) map.get("statType");
			String statDesc=(String) map.get("statDesc");
			String statTypeSession=(String) map.get("statTypeSession");
			String statDescSession=(String) map.get("statDescSession");
			// end recurrent fields
			
			// CAM fields
			String camDocCode=(String) map.get("camDocCode");
			String camDocDesc=(String) map.get("camDocDesc");
			String camDocTenureCount=(String) map.get("camDocTenureCount");
			String camDocTenureType=(String) map.get("camDocTenureType");
			String camDocCodeSession=(String) map.get("camDocCodeSession");
			String camDocDescSession=(String) map.get("camDocDescSession");
			String camDocTenureCountSession=(String) map.get("camDocTenureCountSession");
			String camDocTenureTypeSession=(String) map.get("camDocTenureTypeSession");
			// end CAM fields
			
			// other fields
			String otherDocCode=(String) map.get("otherDocCode");
			String otherDocDesc=(String) map.get("otherDocDesc");
			String otherDocTenureCount=(String) map.get("otherDocTenureCount");
			String otherDocTenureType=(String) map.get("otherDocTenureType");
			String otherDocCodeSession=(String) map.get("otherDocCodeSession");
			String otherDocDescSession=(String) map.get("otherDocDescSession");
			String otherDocTenureCountSession=(String) map.get("otherDocTenureCountSession");
			String otherDocTenureTypeSession=(String) map.get("otherDocTenureTypeSession");
			// end other fields
			
			// facility global fields
						String facDocCode=(String) map.get("facDocCode");
						String facDocDesc=(String) map.get("facDocDesc");
						String facDocTenureCount=(String) map.get("facDocTenureCount");
						String facDocTenureType=(String) map.get("facDocTenureType");
						String facDocCodeSession=(String) map.get("facDocCodeSession");
						String facDocDescSession=(String) map.get("facDocDescSession");
						String facDocTenureCountSession=(String) map.get("facDocTenureCountSession");
						String facDocTenureTypeSession=(String) map.get("facDocTenureTypeSession");
			// end facility fields
						
						// security global fields
						String secDocCode=(String) map.get("secDocCode");
						String secDocDesc=(String) map.get("secDocDesc");
						String secDocTenureCount=(String) map.get("secDocTenureCount");
						String secDocTenureType=(String) map.get("secDocTenureType");
						String secDocCodeSession=(String) map.get("secDocCodeSession");
						String secDocDescSession=(String) map.get("secDocDescSession");
						String secDocTenureCountSession=(String) map.get("secDocTenureCountSession");
						String secDocTenureTypeSession=(String) map.get("secDocTenureTypeSession");
			// end security fields
						
			String event=(String) map.get("event");
			DocumentSearchCriteria criteriaSession=(DocumentSearchCriteria) map.get("criteriaSession");
			
			List docCrit=new ArrayList();
			List docCritSession=(List)map.get("docCritSession");
			if(null!=criteriaSession){
				 // for cam
				if("CAM".equals(criteriaSession.getDocumentType())){
					// remove all values from session if CAM document is freshly entered
					if((event.equals("global_list"))&&go==null)
					camDocCodeSession=camDocDescSession=camDocTenureCountSession=camDocTenureTypeSession=null;
							//--> END removing values from session.
							
					// if go button is clicked then put values in session
					if(go!=null){
					if(go.equalsIgnoreCase("y")){
						camDocCodeSession=camDocCode;
						camDocDescSession=camDocDesc;
						camDocTenureCountSession=camDocTenureCount;
						camDocTenureTypeSession=camDocTenureType;
					}/*else{
						valuationAgencyCodeSession=valuationAgencyCode;
						valuationAgencyNameSession=valuationAgencyName;
					}*/
					}
					
					// get values from session.
					camDocCode=camDocCodeSession;
					camDocDesc=camDocDescSession;
					camDocTenureCount=camDocTenureCountSession;
					camDocTenureType=camDocTenureTypeSession;
					
					if(ASSTValidator.isValidDocumentName(camDocCode))
						camDocCode="";
					if(ASSTValidator.isValidDocumentName(camDocDesc))
						camDocDesc="";
					docCrit.add(camDocCode);
					docCrit.add(camDocDesc);
					if((Validator.checkNumber(camDocTenureCount,true,0,999).equals(Validator.ERROR_NONE)))
					{
					docCrit.add(camDocTenureCount);
					}
					else
					{
						docCrit.add("");
					}
					docCrit.add(camDocTenureType);
					
					resultMap.put("camDocCode",camDocCode);
					resultMap.put("camDocDesc",camDocDesc);
					resultMap.put("camDocTenureCount",camDocTenureCount);
					resultMap.put("camDocTenureType",camDocTenureType);
					resultMap.put("camDocCodeSession",camDocCodeSession);
					resultMap.put("camDocDescSession",camDocDescSession);
					resultMap.put("camDocTenureCountSession",camDocTenureCountSession);
					resultMap.put("camDocTenureTypeSession",camDocTenureTypeSession);
				}
				//end cam
				
                  // for other
				if("O".equals(criteriaSession.getDocumentType())){
					// remove all values from session if Other document is freshly entered
					if((event.equals("global_list"))&&go==null)
					otherDocCodeSession=otherDocDescSession=otherDocTenureCountSession=otherDocTenureTypeSession=null;
							//--> END removing values from session.
							
					// if go button is clicked then put values in session
					if(go!=null){
					if(go.equalsIgnoreCase("y")){
						otherDocCodeSession=otherDocCode;
						otherDocDescSession=otherDocDesc;
						otherDocTenureCountSession=otherDocTenureCount;
						otherDocTenureTypeSession=otherDocTenureType;
					}/*else{
						valuationAgencyCodeSession=valuationAgencyCode;
						valuationAgencyNameSession=valuationAgencyName;
					}*/
					}
					
					// get values from session.
					otherDocCode=otherDocCodeSession;
					otherDocDesc=otherDocDescSession;
					otherDocTenureCount=otherDocTenureCountSession;
					otherDocTenureType=otherDocTenureTypeSession;
					
					if(ASSTValidator.isValidDocumentName(otherDocCode))
						otherDocCode="";
					if(ASSTValidator.isValidDocumentName(otherDocDesc))
						otherDocDesc="";
					
					docCrit.add(otherDocCode);
					docCrit.add(otherDocDesc);
		            
					if((Validator.checkNumber(otherDocTenureCount,true,0,999).equals(Validator.ERROR_NONE)))
					{
						docCrit.add(otherDocTenureCount);
					}
					else
					{
						docCrit.add("");
					}
					docCrit.add(otherDocTenureType);
					
					resultMap.put("otherDocCode",otherDocCode);
					resultMap.put("otherDocDesc",otherDocDesc);
					resultMap.put("otherDocTenureCount",otherDocTenureCount);
					resultMap.put("otherDocTenureType",otherDocTenureType);
					resultMap.put("otherDocCodeSession",otherDocCodeSession);
					resultMap.put("otherDocDescSession",otherDocDescSession);
					resultMap.put("otherDocTenureCountSession",otherDocTenureCountSession);
					resultMap.put("otherDocTenureTypeSession",otherDocTenureTypeSession);
				}
				//end other
				
				// for security global
				if("S".equals(criteriaSession.getDocumentType())){
					// remove all values from session if sec document is freshly entered
					if((event.equals("global_list"))&&go==null)
					secDocCodeSession=secDocDescSession=secDocTenureCountSession=secDocTenureTypeSession=null;
							//--> END removing values from session.
							
					// if go button is clicked then put values in session
					if(go!=null){
					if(go.equalsIgnoreCase("y")){
						secDocCodeSession=secDocCode;
						secDocDescSession=secDocDesc;
						secDocTenureCountSession=secDocTenureCount;
						secDocTenureTypeSession=secDocTenureType;
					}/*else{
						valuationAgencyCodeSession=valuationAgencyCode;
						valuationAgencyNameSession=valuationAgencyName;
					}*/
					}
					
					// get values from session.
					secDocCode=secDocCodeSession;
					secDocDesc=secDocDescSession;
					secDocTenureCount=secDocTenureCountSession;
					secDocTenureType=secDocTenureTypeSession;
					
					if(null!=secDocCode)
					if(ASSTValidator.isValidAlphaNumStringWithoutSpace(secDocCode.trim()))
						secDocCode="";
					if(ASSTValidator.isValidDocumentName(secDocDesc))
						secDocDesc="";
					
					docCrit.add(secDocCode);
					docCrit.add(secDocDesc);
		            
					if((Validator.checkNumber(secDocTenureCount,true,0,999).equals(Validator.ERROR_NONE)))
					{
						docCrit.add(secDocTenureCount);
					}
					else
					{
						docCrit.add("");
					}
					docCrit.add(secDocTenureType);
					
					resultMap.put("secDocCode",secDocCode);
					resultMap.put("secDocDesc",secDocDesc);
					resultMap.put("secDocTenureCount",secDocTenureCount);
					resultMap.put("secDocTenureType",secDocTenureType);
					resultMap.put("secDocCodeSession",secDocCodeSession);
					resultMap.put("secDocDescSession",secDocDescSession);
					resultMap.put("secDocTenureCountSession",secDocTenureCountSession);
					resultMap.put("secDocTenureTypeSession",secDocTenureTypeSession);
				}
				//end security global
				
				// for facility global 
				
				if("F".equals(criteriaSession.getDocumentType())){
					// remove all values from session if fac document is freshly entered
					if((event.equals("global_list"))&&go==null)
					facDocCodeSession=facDocDescSession=facDocTenureCountSession=facDocTenureTypeSession=null;
							//--> END removing values from session.
							
					// if go button is clicked then put values in session
					if(go!=null){
					if(go.equalsIgnoreCase("y")){
						facDocCodeSession=facDocCode;
						facDocDescSession=facDocDesc;
						facDocTenureCountSession=facDocTenureCount;
						facDocTenureTypeSession=facDocTenureType;
					}/*else{
						valuationAgencyCodeSession=valuationAgencyCode;
						valuationAgencyNameSession=valuationAgencyName;
					}*/
					}
					
					// get values from session.
					facDocCode=facDocCodeSession;
					facDocDesc=facDocDescSession;
					facDocTenureCount=facDocTenureCountSession;
					facDocTenureType=facDocTenureTypeSession;
					
					if(null!=facDocCode)
					if(ASSTValidator.isValidAlphaNumStringWithoutSpace(facDocCode.trim()))
						facDocCode="";
					if(ASSTValidator.isValidDocumentName(facDocDesc))
						facDocDesc="";
					
					docCrit.add(facDocCode);
					docCrit.add(facDocDesc);
		            
					if((Validator.checkNumber(facDocTenureCount,true,0,999).equals(Validator.ERROR_NONE)))
					{
						docCrit.add(facDocTenureCount);
					}
					else
					{
						docCrit.add("");
					}
					docCrit.add(facDocTenureType);
					
					resultMap.put("facDocCode",facDocCode);
					resultMap.put("facDocDesc",facDocDesc);
					resultMap.put("facDocTenureCount",facDocTenureCount);
					resultMap.put("facDocTenureType",facDocTenureType);
					resultMap.put("facDocCodeSession",facDocCodeSession);
					resultMap.put("facDocDescSession",facDocDescSession);
					resultMap.put("facDocTenureCountSession",facDocTenureCountSession);
					resultMap.put("facDocTenureTypeSession",facDocTenureTypeSession);
				}
				// end facility global
				
				// for recurrent
			if("REC".equals(criteriaSession.getDocumentType())){
				
				// remove all values from session if recurrent document is freshly entered
				if((event.equals("global_list"))&&go==null)
					statTypeSession=statDescSession=null;
						//--> END removing values from session.
						
				// if go button is clicked then put values in session
				if(go!=null){
				if(go.equalsIgnoreCase("y")){
					statDescSession=statDesc;
					statTypeSession=statType;
				}/*else{
					valuationAgencyCodeSession=valuationAgencyCode;
					valuationAgencyNameSession=valuationAgencyName;
				}*/
				}
				
				// get values from session.
				statDesc=statDescSession;
				statType=statTypeSession;
				if(ASSTValidator.isValidDocumentName(statDesc))
					statDesc="";
				docCrit.add(statDesc);
				docCrit.add(statType);
				
				resultMap.put("statDesc",statDesc);
				resultMap.put("statType",statType);
				resultMap.put("statDescSession",statDescSession);
				resultMap.put("statTypeSession",statTypeSession);
			}
			//--> end recurrent
			}
			if(null==go||"".equals(go))
			sr = proxy.getDocumentItemList(criteria);
			else
			sr = proxy.getFilteredDocumentItemList(criteriaSession,docCrit);
		}
		catch (CheckListTemplateException ex) {
			throw new CommandProcessingException("failed to retrieve document item list using criteria [" + criteria
					+ "]", ex);
		}

		if (sr != null && sr.getResultList() != null) {
			globalDocChkList.addAll(sr.getResultList());
			/*if (this.toSortDocumentSearchResult) {
				Collections.sort(globalDocChkList);
			}*/
			resultMap.put("searchResult", sr);
		}
		resultMap.put("tenureTypeList", getTenureType());
		resultMap.put("recurrentStatTypeList", getRecurrentStatType());
		resultMap.put("globalDocChkList", globalDocChkList);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	 /*
     * getRecurrentStatType - get dropdown for statement type field from common code.
     */
	private List getRecurrentStatType() {
		List lbValList = new ArrayList();
		HashMap recurrentStatTypeMap;
		 ArrayList facilityCategoryLabel = new ArrayList();

			ArrayList facilityCategoryValue = new ArrayList();

			recurrentStatTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.STATEMENT_TYPE);
			facilityCategoryValue.addAll(recurrentStatTypeMap.keySet());
			facilityCategoryLabel.addAll(recurrentStatTypeMap.values());
		try {
		
			for (int i = 0; i < facilityCategoryLabel.size(); i++) {
				String id = facilityCategoryLabel.get(i).toString();
				String val = facilityCategoryValue.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(id,val);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	 /*
     * getTenureType - get dropdown for tenure type field from common code.
     */
	private List getTenureType() {
		List lbValList = new ArrayList();
		HashMap tenureTypeMap;
		 ArrayList tenureTypeLabel = new ArrayList();

			ArrayList tenureTypeValue = new ArrayList();

			tenureTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.TIME_FREQ);
			tenureTypeValue.addAll(tenureTypeMap.keySet());
			tenureTypeLabel.addAll(tenureTypeMap.values());
		try {
		
			for (int i = 0; i < tenureTypeLabel.size(); i++) {
				String id = tenureTypeLabel.get(i).toString();
				String val = tenureTypeValue.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(id,val);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
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
