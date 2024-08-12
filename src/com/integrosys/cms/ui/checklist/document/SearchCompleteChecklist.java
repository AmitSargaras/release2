package com.integrosys.cms.ui.checklist.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.ICheckListSummary;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class SearchCompleteChecklist extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public SearchCompleteChecklist() {
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "category", "java.lang.String", REQUEST_SCOPE },
				{ "description", "java.lang.String", REQUEST_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
				{ "category1", "java.lang.String", SERVICE_SCOPE },
				{ "description1", "java.lang.String", SERVICE_SCOPE },
				{ "status1", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] { { "documentList", "java.util.List", REQUEST_SCOPE },
				{ "docCatList", "java.util.HashMap", REQUEST_SCOPE },
				{ "checkListIDMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "finalList", "java.util.List", SERVICE_SCOPE },
				{ "category1", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "description1", "java.lang.String", SERVICE_SCOPE },
				{ "status1", "java.lang.String", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", REQUEST_SCOPE } });
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
		HashMap checkListIDMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		resultMap.put("theOBTrxContext", theOBTrxContext);

		ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
        ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

		if ((!customer.getNonBorrowerInd()) ||
                (limit != null && ICMSConstant.STATE_DELETED.equals(limit.getBCAStatus()))) {
			//ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			String event=(String)map.get("event");
			long limitProfileID = limit.getLimitProfileID();
			String status=(String)map.get("status");
			if((status==null||"".equals(status)||"null".equalsIgnoreCase(status)) && !event.equals("search_checklist")){
				status=(String)map.get("status1");
			}
			String description=(String)map.get("description");
			if((description==null||"".equals(description)||"null".equalsIgnoreCase(description))&& !event.equals("search_checklist")){
				description=(String)map.get("description1");
			}
			String category=(String)map.get("category");
			if((category==null||"".equals(category)||"null".equalsIgnoreCase(category))&& !event.equals("search_checklist")){
				category=(String)map.get("category1");
			}
			
			try {
				// cc checklist
				
				//CheckListDAO checkListDAO = (CheckListDAO)BeanHouse.get("collateralDao");getSearchCheckListId
				List allcheckList=null;
				List finalList=new ArrayList();
				if(null!=category && !category.equals("")){
					allcheckList=CheckListDAOFactory.getCheckListDAO().getSearchCheckListId(String.valueOf(limitProfileID),category);
				}else{
					allcheckList=CheckListDAOFactory.getCheckListDAO().getAllCheckListId(String.valueOf(limitProfileID));
				}
				
				if(null!=allcheckList){
					String secName=null;
					String docName=null;
					for(int a=0;a<allcheckList.size();a++){
						OBCheckList obj=(OBCheckList)allcheckList.get(a);
						List allCheckListItem=null;
						if((null!=status && !status.equals(""))||(null!=description && !description.equals(""))){
							allCheckListItem=CheckListDAOFactory.getCheckListDAO().getSearchCheckListItem(String.valueOf(obj.getCheckListID()),status,description);
						}else if((null==status || status.equals(""))&&(null==description || description.equals(""))&&(null==category || category.equals(""))){
						allCheckListItem=CheckListDAOFactory.getCheckListDAO().getAllCheckListItem(String.valueOf(obj.getCheckListID()));
						}else{
							allCheckListItem=CheckListDAOFactory.getCheckListDAO().getAllCheckListStatus(String.valueOf(obj.getCheckListID()));
						}
						if(null!=obj.getCheckListType() && !obj.getCheckListType().equals("")){
						if(null!=allCheckListItem && obj.getCheckListType().equals("F")){
						 secName=CheckListDAOFactory.getCheckListDAO().getFacilityInfo(obj.getCheckListID());	
						 docName="Facility";
						}
						else if(null!=allCheckListItem && obj.getCheckListType().equals("S")){
							 secName=CheckListDAOFactory.getCheckListDAO().getSecurityInfo(obj.getCheckListID());	
							 docName="Security";
							}
						else if(null!=allCheckListItem && obj.getCheckListType().equals("O")){
							 secName="Other";	
							 docName="Other";
							}
						else{
							secName=obj.getCheckListType();
							docName=obj.getCheckListType();
						}
						}
						if(null!=allCheckListItem && allCheckListItem.size()>0){
							allCheckListItem=sortStatus(allCheckListItem);
						for(int b=0;b<allCheckListItem.size();b++){
							OBCheckListItem chkObj=(OBCheckListItem)allCheckListItem.get(b);
						 OBViewCompleteCheckList viewObj=new OBViewCompleteCheckList();
						 viewObj.setType(secName);
						 viewObj.setDocType(docName);
						 viewObj.setActive(chkObj.getDocumentStatus());
						 viewObj.setDescription(chkObj.getItemDesc());
						 viewObj.setDocAmount(chkObj.getDocAmt());
						 viewObj.setDocDate(chkObj.getDocDate());
						 viewObj.setDocExpDate(chkObj.getExpiryDate());
						 viewObj.setStatus(chkObj.getItemStatus());
						 viewObj.setVersion(chkObj.getDocumentVersion());
						 viewObj.setDocItemId(chkObj.getCheckListItemID());
						 finalList.add(viewObj);
						
					}
				}
					}
				
				}	
				resultMap.put("finalList",finalList);
				resultMap.put("status1",status);
				resultMap.put("description1",description);
				resultMap.put("category1",category);
				resultMap.put("event",event);
				
				HashMap ccMap = proxy.getAllCCCheckListSummaryList(theOBTrxContext, limitProfileID);
				ICheckListSummary[] ccCheckListSummaries = (ccMap == null) ? null : (ICheckListSummary[]) ccMap
						.get(ICMSConstant.NORMAL_LIST);
				// collateral checklist
				HashMap colCheckListMap = proxy.getAllCollateralCheckListSummaryList(theOBTrxContext, limitProfileID);
				ICheckListSummary[] colCheckListSummaries = (colCheckListMap == null) ? null
						: (ICheckListSummary[]) colCheckListMap.get(ICMSConstant.NORMAL_LIST);

				Set cmsCheckListIds = new HashSet();
				if (ccCheckListSummaries != null) {
					for (int i = 0; i < ccCheckListSummaries.length; i++) {
						if (ccCheckListSummaries[i].getCheckListID() > ICMSConstant.LONG_INVALID_VALUE) {
							cmsCheckListIds.add(new Long(ccCheckListSummaries[i].getCheckListID()));
						}
					}
				}
				if (colCheckListSummaries != null) {
					for (int i = 0; i < colCheckListSummaries.length; i++) {
						if (colCheckListSummaries[i].getCheckListID() > ICMSConstant.LONG_INVALID_VALUE) {
							cmsCheckListIds.add(new Long(colCheckListSummaries[i].getCheckListID()));
						}
					}
				}
				ILimit[] OB=limit.getLimits();
	          	for(int i=0;i<OB.length;i++){
	              ILimit obLimit= OB[i];
	              long limitId= obLimit.getLimitID();
	           
	              try {
	            	  CheckListSearchResult checkList=proxy.getCheckListByCollateralID(limitId);
	            	  if(checkList!=null){
	            		  cmsCheckListIds.add(new Long(checkList.getCheckListID()));
	            		  checkListIDMap.put(new Long(checkList.getCheckListID()), obLimit);
	            		 
	            	  }
	            	  resultMap.put("checkListIDMap",checkListIDMap);
	            	   
				} catch (CheckListException e) {
					
					e.printStackTrace();
					throw new CommandProcessingException("failed to retrieve  checklist ", e);
				}
	          	}
	          	 try {
	           	  CheckListSearchResult camCheckList= proxy.getCAMCheckListByCategoryAndProfileID("CAM",limitProfileID);
	           	  if(camCheckList!=null)
	           	cmsCheckListIds.add(new Long(camCheckList.getCheckListID())); 
	   			
	   		} catch (CheckListException e) {
	   			
	   			e.printStackTrace();
	   			throw new CommandProcessingException("failed to retrieve  checklist ", e);
	   		}
				

				HashMap docCatMap = proxy.getDocumentCategories(limitProfileID);
				for (Iterator itr = docCatMap.entrySet().iterator(); itr.hasNext();) {
					Map.Entry entry = (Map.Entry) itr.next();
					List infosList = (List) entry.getValue();
					for (Iterator itrInfo = infosList.iterator(); itrInfo.hasNext();) {
						String[] infos = (String[]) itrInfo.next();
						if (!cmsCheckListIds.contains(new Long(infos[0]))) {
							// not suppose to be viewed.
							itrInfo.remove();
						}
					}
				}

				resultMap.put("docCatList", docCatMap);
				resultMap.put("availableCmsCheckListIds", cmsCheckListIds);
			}
			catch (CheckListException ex) {
				throw new CommandProcessingException("failed to retrieve document categories for limit profile id ["
						+ limitProfileID + "]", ex);
			}
			catch (CheckListTemplateException ex) {
				throw new CommandProcessingException(
						"failed to retrieve document categories due to template error for limit profile id ["
								+ limitProfileID + "]", ex);
			}
		}
		else {
            long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;
			if (limit != null) {
				limitProfileID = limit.getLimitProfileID();
			}
			long customerID = customer.getCustomerID();

			try {
				HashMap docCatMap = proxy.getDocumentCategoriesForNonBorrower(customerID, limitProfileID);
				resultMap.put("docCatList", docCatMap);
			}
			catch (CheckListException ex) {
				throw new CommandProcessingException(
						"failed to retrieve document categories for non borrower, customer id [" + customerID
								+ "] limit profile id [" + limitProfileID + "]", ex);
			}
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	public List sortStatus(List unsortList){
		List sortedList=new ArrayList();
		List pending=new ArrayList();
		List received=new ArrayList();
		List defer=new ArrayList();
		List waive=new ArrayList();
		for(int s=0;s<unsortList.size();s++){
			OBCheckListItem sortObj=(OBCheckListItem)unsortList.get(s);
			if(sortObj.getItemStatus().equals("AWAITING")){
				pending.add(sortObj);
			}else if(sortObj.getItemStatus().equals("RECEIVED")){
				received.add(sortObj);
			}else if(sortObj.getItemStatus().equals("WAIVED")){
				waive.add(sortObj);
			}else if(sortObj.getItemStatus().equals("DEFERRED")){
				defer.add(sortObj);
			}			
		}
		if(received.size()>0){
			sortedList.addAll(received);
		}
		if(defer.size()>0){
			sortedList.addAll(defer);
		}
		if(pending.size()>0){
			sortedList.addAll(pending);
		}
		if(waive.size()>0){
			sortedList.addAll(waive);
		}
		
		return sortedList;
	}
}
