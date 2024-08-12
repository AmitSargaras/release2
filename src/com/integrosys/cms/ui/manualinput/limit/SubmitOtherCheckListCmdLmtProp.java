package com.integrosys.cms.ui.manualinput.limit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateDAOFactory;
import com.integrosys.cms.app.chktemplate.bus.IDocumentDAO;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGCJdbc;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/10/09 05:39:20 $ Tag: $Name: $
 */
public class SubmitOtherCheckListCmdLmtProp extends AbstractCommand implements ICommonEventConstant {

	private boolean isMaintainChecklistWithoutApproval = true;


	/**
	 * Default Constructor
	 */
	public SubmitOtherCheckListCmdLmtProp() {
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
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{"collateralSet","java.util.HashSet",SERVICE_SCOPE},
				{ "collInsurance", "java.util.HashMap", SERVICE_SCOPE },
				{ "insuranceList", "java.util.ArrayList", SERVICE_SCOPE },});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE },
		{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
		{"insuranceList", "java.util.ArrayList", SERVICE_SCOPE},});
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
		ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
		
		 Set collateralSet = (HashSet) map.get("collateralSet");
		 Map collInsurance = (HashMap) map.get("collInsurance");
		 List insuranceList = (ArrayList) map.get("insuranceList");
		 
		 int collatrealSetSize=collateralSet.size();
		if(collatrealSetSize>0 && insuranceList.size()>0){
		ICheckList checkList = (ICheckList) map.get("checkList");
		ICheckListProxyManager checklistProxyManager =(ICheckListProxyManager)BeanHouse.get("checklistProxy");
		ILimitTrxValue itrxValue = (ILimitTrxValue) map.get("lmtTrxObj");
		

		IDocumentDAO documentDao= CheckListTemplateDAOFactory.getDocumentDAO();
		ICheckListItem temp[] = checkList.getCheckListItemList();
		ICheckList checkList2=new OBCheckList();
		
//		List insuranceList=new ArrayList();
		IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
//		
//		
//		Map<String,Long> collInsurance=new HashMap<String, Long>();
//		Iterator it1 = collateralSet.iterator();
//		while(it1.hasNext()){
//			long collateralID = (Long)it1.next();
//			SearchResult insuranceListColl=(SearchResult)insuranceGCJdbc.getAllInsurancePolicy(collateralID);
//			
//			List insuranceList1=(List)insuranceListColl.getResultList();
//			for(int k=0; k <insuranceList1.size();k++){
//				IInsurancePolicy actualObj=(IInsurancePolicy) insuranceList1.get(k);
//				collInsurance.put(actualObj.getRefID(),(Long)it1.next());
//			}
//			
//			insuranceList.addAll(insuranceListColl.getResultList());
//		}
		
		
		List aCheckListItemList1=new ArrayList<ICheckListItem>();
		
		boolean isInsuPresentInChecklist1=false;
		boolean isInsuPresentInChecklist2=false;
		
		if(null == checkListTrxVal){
			
		//	String checklistStatus="IN_PROGRESS";
			if(null!=insuranceList){
			for(int i=0;i<insuranceList.size();i++){
				IInsurancePolicy actualObj=(IInsurancePolicy)insuranceList.get(i);
				if(!"DELETED".equals(actualObj.getStatus())){
				OBCheckListItem OBCheckListItem=new OBCheckListItem();
				
				OBItem obItem=new OBItem();
				obItem.setItemCode("DOC"+actualObj.getRefID());
				obItem.setItemDesc(ICMSConstant.INSURANCE_POLICY2+"-Property"+collInsurance.get(actualObj.getRefID())+"-"+actualObj.getRefID());
				OBCheckListItem.setItem(obItem);
				
				OBCheckListItem.setItemStatus(actualObj.getInsuranceStatus());
				OBCheckListItem.setInsuranceId(String.valueOf(actualObj.getRefID()));
				
				OBCheckListItem.setUpdatedBy(actualObj.getLastUpdatedBy());
				OBCheckListItem.setUpdatedDate(actualObj.getLastUpdatedOn());
				OBCheckListItem.setApprovedBy(actualObj.getLastApproveBy());
				OBCheckListItem.setApprovedDate(actualObj.getLastApproveOn()); 
				
				aCheckListItemList1.add(OBCheckListItem);
				}
			}
			checkList2.setCheckListItemList((ICheckListItem[]) aCheckListItemList1.toArray(new ICheckListItem[aCheckListItemList1.size()]));
		}}else{
		if (temp != null && temp.length>0) {
			
			List<String> checklistInsuranceId=insuranceGCJdbc.getInsuranceIdFromChecklist(checkList.getCheckListID());
			List<String> insuranceId=new ArrayList<String>();
			Iterator it2 = collateralSet.iterator();
			while(it2.hasNext()){
				long collateralID = (Long)it2.next();
			 insuranceId.addAll(insuranceGCJdbc.getInsurancePolicyId(collateralID));
			}

			Set<String> s1=new HashSet(checklistInsuranceId);
			Set<String> s2=new HashSet(insuranceId);
			
			if(s1.size()>0 || s2.size()>0){
				if(s1.containsAll(s2)){
					isInsuPresentInChecklist2=true;	
				}
				if(s2.containsAll(s1)){
					isInsuPresentInChecklist1=true;
				}
			}
			
			if(!isInsuPresentInChecklist2 || !isInsuPresentInChecklist1){
			for (int i = 0; i < temp.length; i++) {
				
				
				/**
				 * skip update of deleted items
				 */
				if (isItemDeleted(checkList, i)) {
					continue;
				}
				
				if(null!=insuranceList){
				for(int j=0;j<insuranceList.size();j++){
					IInsurancePolicy actualObj=(IInsurancePolicy)insuranceList.get(j);
					boolean isDelete=false;
					//OBCheckListItem OBCheckListItem = null;
					OBCheckListItem OBCheckListItem = (OBCheckListItem) checkList.getCheckListItemList()[i];	
					
					if(null!=OBCheckListItem.getInsuranceId()){
					if(String.valueOf(actualObj.getRefID()).equals(OBCheckListItem.getInsuranceId())){
						if("DELETED".equals(actualObj.getStatus())){
							//don't add the object in checklist
							
							// OBCheckListItem = (OBCheckListItem) checkList.getCheckListItemList()[i];	
							 OBCheckListItem.setIsDeletedInd(true);
							 OBCheckListItem.setUpdatedBy(actualObj.getLastUpdatedBy());
							 OBCheckListItem.setUpdatedDate(actualObj.getLastUpdatedOn());
							 OBCheckListItem.setApprovedBy(actualObj.getLastApproveBy());
							 OBCheckListItem.setApprovedDate(actualObj.getLastApproveOn()); 
							 aCheckListItemList1.add(OBCheckListItem);
								break;	
						}else{
						// OBCheckListItem = (OBCheckListItem) checkList.getCheckListItemList()[i];	
							OBCheckListItem.setItemStatus(actualObj.getInsuranceStatus());
							OBCheckListItem.setUpdatedBy(actualObj.getLastUpdatedBy());
							OBCheckListItem.setUpdatedDate(actualObj.getLastUpdatedOn());
							OBCheckListItem.setApprovedBy(actualObj.getLastApproveBy());
							OBCheckListItem.setApprovedDate(actualObj.getLastApproveOn()); 
							aCheckListItemList1.add(OBCheckListItem);
							break;	
						}
						}else if(j==(insuranceList.size()-1)){
							aCheckListItemList1.add(OBCheckListItem);
							break;
						}
					}else{
						//Add the non insurance other checklist item
						// OBCheckListItem = (OBCheckListItem) checkList.getCheckListItemList()[i];	
						aCheckListItemList1.add(OBCheckListItem);
						break;
					}
					
				}
				checkList2.setCheckListItemList((ICheckListItem[]) aCheckListItemList1.toArray(new ICheckListItem[aCheckListItemList1.size()]));
			    }
		      }	
	        }
	      }
		
		//Add newly added insurance details when checklist is already created.
		Set<String> insIdNotPresentinChklist=new HashSet<String>();
		Iterator it2 = collateralSet.iterator();
		while(it2.hasNext()){
			long collateralID = (Long)it2.next();
		insIdNotPresentinChklist.addAll(insuranceGCJdbc.getInsPolicyIdNotPresentInChecklist( checkList.getCheckListID(), collateralID));
		}
		
		Iterator<String> it = insIdNotPresentinChklist.iterator();
		while(it.hasNext()){
			String id =  it.next();
		for (int i = 0; i < insuranceList.size(); i++) {
			IInsurancePolicy actualObj=(IInsurancePolicy)insuranceList.get(i);
			if(id.equals(String.valueOf(actualObj.getRefID()))){
				OBCheckListItem OBCheckListItem=new OBCheckListItem();
				
				OBItem obItem=new OBItem();
				obItem.setItemCode("DOC"+actualObj.getRefID());
				obItem.setItemDesc(ICMSConstant.INSURANCE_POLICY2+"-Property"+collInsurance.get(actualObj.getRefID())+"-"+actualObj.getRefID());
				OBCheckListItem.setItem(obItem);
				
				OBCheckListItem.setItemStatus(actualObj.getInsuranceStatus());
				OBCheckListItem.setInsuranceId(String.valueOf(actualObj.getRefID()));
				
				OBCheckListItem.setUpdatedBy(actualObj.getLastUpdatedBy());
				OBCheckListItem.setUpdatedDate(actualObj.getLastUpdatedOn());
				OBCheckListItem.setApprovedBy(actualObj.getLastApproveBy());
				OBCheckListItem.setApprovedDate(actualObj.getLastApproveOn()); 
				
				aCheckListItemList1.add(OBCheckListItem);
				checkList2.setCheckListItemList((ICheckListItem[]) aCheckListItemList1.toArray(new ICheckListItem[aCheckListItemList1.size()]));
			}
			
		}
		} 
		}
		
		
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		
		if(null!=checkList2.getCheckListItemList()){
		checkList.setCheckListItemList(checkList2.getCheckListItemList());
		}
		if (checkListTrxVal == null) {
			if(checkList.getCheckListItemList().length >0){
			if (this.isMaintainChecklistWithoutApproval) {
				try {
					checkListTrxVal = checklistProxyManager.makerCreateCheckListWithoutApproval(ctx, checkList);
				}
				catch (CheckListException ex) {
					throw new CommandProcessingException("failed to submit checklist creation workflow", ex);
				}
			}
			else {
				try {
					checkListTrxVal =checklistProxyManager.makerCreateCheckList(ctx, checkList);
				}
				catch (CheckListException ex) {
					throw new CommandProcessingException("failed to submit checklist creation workflow", ex);
				}
			}
			}
		}else if(!isInsuPresentInChecklist1 || !isInsuPresentInChecklist2){ //if there is any addition in insurance doc then only perform this update else nothing
			if (this.isMaintainChecklistWithoutApproval) {
				try {
					checkListTrxVal = checklistProxyManager.makerUpdateCheckListWithoutApproval(ctx,
							checkListTrxVal, checkList);
				}
				catch (CheckListException ex) {
					throw new CommandProcessingException("failed to submit checklist update workflow", ex);
				}
			}
			else {
				try {
					checkListTrxVal = checklistProxyManager.makerUpdateCheckList(ctx, checkListTrxVal, checkList);
				}
				catch (CheckListException ex) {
					throw new CommandProcessingException("failed to submit checklist update workflow", ex);
				}
			}
		}
			//checkListTrxVal.getCheckList().getCheckListStatus()
		resultMap.put("request.ITrxValue", checkListTrxVal);
		resultMap.put("checkListTrxVal",checkListTrxVal);
		
		resultMap.put("insuranceList",insuranceList);
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	/*private HashMap getMapFromString(String commaSepInput) {
		HashMap hm = new HashMap();
		StringTokenizer st = new StringTokenizer(commaSepInput, ",");
		while (st.hasMoreTokens()) {
			String key = st.nextToken();
			hm.put(key, key);
		}
		return hm;
	} */

	private boolean isItemDeleted(ICheckList checkList, int i) {
		return ICMSConstant.STATE_DELETED.equals(checkList.getCheckListItemList()[i].getItemStatus());
	}

}
