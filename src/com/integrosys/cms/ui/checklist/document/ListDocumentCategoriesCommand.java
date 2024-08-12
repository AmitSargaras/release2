/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAO;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.checklist.bus.ICheckListSummary;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyDAO;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/07/27 06:45:18 $ Tag: $Name: $
 */
public class ListDocumentCategoriesCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListDocumentCategoriesCommand() {
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
			long limitProfileID = limit.getLimitProfileID();
			try {
				// cc checklist
				
				//CheckListDAO checkListDAO = (CheckListDAO)BeanHouse.get("collateralDao");
				List allcheckList=CheckListDAOFactory.getCheckListDAO().getAllCheckListId(String.valueOf(limitProfileID));
				List finalList=new ArrayList();
				if(null!=allcheckList){
					String secName=null;
					String docName=null;
					for(int a=0;a<allcheckList.size();a++){
						OBCheckList obj=(OBCheckList)allcheckList.get(a);
						List allCheckListItem=CheckListDAOFactory.getCheckListDAO().getAllCheckListItem(String.valueOf(obj.getCheckListID()));
						if(null!=allCheckListItem && allCheckListItem.size()>0){
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
				resultMap.put("status1",null);
				resultMap.put("description1",null);
				resultMap.put("category1",null);
				
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
}
