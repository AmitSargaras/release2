package com.integrosys.cms.ui.caseCreation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.caseCreation.bus.ICaseCreation;
import com.integrosys.cms.app.caseCreation.bus.ICaseCreationDao;
import com.integrosys.cms.app.caseCreation.bus.ICaseCreationRemark;
import com.integrosys.cms.app.caseCreationUpdate.proxy.ICaseCreationProxyManager;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.proxy.IHolidayProxyManager;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.component.user.app.bus.OBCommonUser;

/**
 $Author: Abhijit R $
 Command for list Holiday
 */
public class ListCaseCreationCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	
	

	public ListCaseCreationCmd() {
		
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
					GLOBAL_SCOPE },
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
			{IGlobalConstant.GLOBAL_LOS_USER, "com.integrosys.component.user.app.bus.ICommonUser",GLOBAL_SCOPE},
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				  {"role", "java.lang.String", REQUEST_SCOPE},
				
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }
				
				
			};
	}
	   public String[][] getResultDescriptor() {
	        return (new String[][]{
	                {"receivedList", "java.util.ArrayList", SERVICE_SCOPE},
	                {"typeMap", "java.util.ArrayList", SERVICE_SCOPE},
	                {"facilityNameMap", "java.util.ArrayList", SERVICE_SCOPE},
	                {"securityTypeMap", "java.util.ArrayList", SERVICE_SCOPE},
	                {"isCPUT", "java.lang.String", REQUEST_SCOPE},
					  {"isBRANCH", "java.lang.String", REQUEST_SCOPE},
					  {"remarks", "java.lang.String", REQUEST_SCOPE},
					  {"hmCaseCreation", "java.util.HashMap", SERVICE_SCOPE},
	        });
	    }

	    /**
	     * This method does the Business operations  with the HashMap and put the results back into
	     * the HashMap.
	     *
	     * @param map is of type HashMap
	     * @return HashMap with the Result
	     */
	    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
	        HashMap returnMap = new HashMap();
	        HashMap resultMap = new HashMap();
	        HashMap remarkMap = new HashMap();
	        HashMap typeMap = new HashMap();
	        HashMap facilityNameMap = new HashMap();
	        HashMap securityTypeMap = new HashMap();
	        String startIndex = (String) map.get("startIndex");
	        int stindex = 0;
	        String roleType= (String) map.get("role");
	        String isCPUT="false";
	        String isBRANCH="false";
	        String remarks="";
	        if(roleType!=null){
	        	if("cput".equals(roleType.trim())){
	        		isCPUT="true";
	        	}
	        	if("branch".equals(roleType.trim())){
	        		isBRANCH="true";
	        	}
	        }
	        OBCommonUser globalUser = (OBCommonUser) map.get(IGlobalConstant.GLOBAL_LOS_USER);
	        ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			// DefaultLogger.debug(this,"Limit profile "+limit);
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			ICheckListProxyManager proxy3 = CheckListProxyManagerFactory.getCheckListProxyManager();
			
			try {
				ICaseCreationProxyManager caseCreationProxyManager = (ICaseCreationProxyManager) BeanHouse.get("caseCreationProxy");
				
			ICaseCreationDao caseCreationDao=(ICaseCreationDao)BeanHouse.get("caseCreationDao");
			SearchResult searchResultCaseCreation=caseCreationDao.listCaseCreationByLimitProfileId("actualCaseCreation", limitProfileID);
			ArrayList stageCaseCreationList=caseCreationDao.listStageCaseCreationByLimitProfileId( limitProfileID);
			ArrayList caseCreationList = (ArrayList)searchResultCaseCreation.getResultList();
			HashMap hmCaseCreation = new HashMap();
			HashMap hmStageCaseCreation = new HashMap();
			for(int k=0;k<caseCreationList.size();k++){
				ICaseCreation caseCreation2=(ICaseCreation)caseCreationList.get(k);
				//if(remarks==null||remarks.trim().equals(""))
					//remarks=caseCreation2.getRemark();
				if(!caseCreation2.getStatus().equals("5")){
				hmCaseCreation.put(String.valueOf(caseCreation2.getChecklistitemid()),caseCreation2);
				}
			}
			
			for(int k=0;k<stageCaseCreationList.size();k++){
				
				String checklistId=(String)stageCaseCreationList.get(k);
				if(!hmCaseCreation.containsKey(checklistId)){
					hmStageCaseCreation.put(checklistId,checklistId);	
				}
				
			}
			
			
			SearchResult searchResultCaseCreationRemark=caseCreationDao.listCaseCreationRemark("actualCaseCreationRemark", limitProfileID);
			List caseCreationRemarkList = (ArrayList)searchResultCaseCreationRemark.getResultList();
			int[] idList=new int[caseCreationRemarkList.size()];
			for(int k=0;k<caseCreationRemarkList.size();k++){
			//caseCreationRemarkList.
				ICaseCreationRemark caseCreationRemark=(ICaseCreationRemark)caseCreationRemarkList.get(k);
				idList[k]=  (int)caseCreationRemark.getId();
				remarkMap.put(String.valueOf(caseCreationRemark.getId()), caseCreationRemark);
			}
			Arrays.sort(idList);
			if(idList.length>0){
			ICaseCreationRemark caseCreationRemark2=(ICaseCreationRemark)remarkMap.get(String.valueOf(idList[idList.length-1]));
			if(caseCreationRemark2!=null)
					remarks=caseCreationRemark2.getRemark();
			}
			resultMap.put("remarks", remarks);
			resultMap.put("hmCaseCreation", hmCaseCreation);
			List receivedList = new ArrayList();
            HashMap deferredMap = new HashMap();
	        
	        	HashMap checkListMap = proxy3.getAllCollateralCheckListSummaryList(theOBTrxContext, limit.getLimitProfileID());
	        	
	        	
	        	 CheckListSearchResult camCheckList= proxy3.getCAMCheckListByCategoryAndProfileID("CAM",limitProfileID);
				 if(camCheckList!=null){
					 
					 ICheckListTrxValue checkListTrxVal = proxy3.getCheckList( camCheckList.getCheckListID());
						ICheckList checkList = checkListTrxVal.getCheckList();
						ICheckListItem[] items = checkList.getCheckListItemList();
						if (items != null) {
							for (int xx = 0; xx < items.length; xx++) {
								if (!ICMSConstant.STATE_ITEM_WAIVED.equals(items[xx].getItemStatus())) {
									
									if(!hmCaseCreation.containsKey(String.valueOf(items[xx].getCheckListItemID()))){
										if(!hmStageCaseCreation.containsKey(String.valueOf(items[xx].getCheckListItemID()))){
											receivedList.add(items[xx]);
											typeMap.put(String.valueOf(items[xx].getCheckListItemID()), "CAM");
													
										}
									}

									
								}
							}
						}
				 }
				 
				 
				
				 
				   	ILimit[] OB=limit.getLimits();
		          	for(int i=0;i<OB.length;i++){
		              ILimit obLimit= OB[i];
		              long limitId= obLimit.getLimitID();
		           
		             
		            	  CheckListSearchResult facilitycheckList=proxy3.getCheckListByCollateralID(limitId);
		            	  if(facilitycheckList!=null){
		     				 
		     				 ICheckListTrxValue checkListTrxVal = proxy3.getCheckList( facilitycheckList.getCheckListID());
		     					ICheckList checkList = checkListTrxVal.getCheckList();
		     					ICheckListItem[] items = checkList.getCheckListItemList();
		     					if (items != null) {
		     						for (int xx = 0; xx < items.length; xx++) {
		     							if (!ICMSConstant.STATE_ITEM_WAIVED.equals(items[xx].getItemStatus())) {
		     								
		     								if(!hmCaseCreation.containsKey(String.valueOf(items[xx].getCheckListItemID()))){
		     									if(!hmStageCaseCreation.containsKey(String.valueOf(items[xx].getCheckListItemID()))){
		     								receivedList.add(items[xx]);
		     								typeMap.put(String.valueOf(items[xx].getCheckListItemID()), "Facility");
		     								facilityNameMap.put(String.valueOf(items[xx].getCheckListItemID()), obLimit.getFacilityName());
		     									}
		     								}
		     							}
		     						}
		     					}
		     			 }
		     			 
					
		          	}
		          	
		          	 CheckListSearchResult otherCheckList= proxy3.getCAMCheckListByCategoryAndProfileID("O",limitProfileID);
					 if(otherCheckList!=null){
						 
						 ICheckListTrxValue checkListTrxValOther = proxy3.getCheckList( otherCheckList.getCheckListID());
							ICheckList checkListOther = checkListTrxValOther.getCheckList();
							ICheckListItem[] itemsOther = checkListOther.getCheckListItemList();
							if (itemsOther != null) {
								for (int xx = 0; xx < itemsOther.length; xx++) {
									if (!ICMSConstant.STATE_ITEM_WAIVED.equals(itemsOther[xx].getItemStatus())) {
										
										if(!hmCaseCreation.containsKey(String.valueOf(itemsOther[xx].getCheckListItemID()))){
											if(!hmStageCaseCreation.containsKey(String.valueOf(itemsOther[xx].getCheckListItemID()))){
										receivedList.add(itemsOther[xx]);
										typeMap.put(String.valueOf(itemsOther[xx].getCheckListItemID()), "Other");
											}
										}

										
									}
								}
							}
					 }
					 
		          	
					if (checkListMap != null) {
						CollateralCheckListSummary[] colChkLst = (CollateralCheckListSummary[]) checkListMap.get(ICMSConstant.NORMAL_LIST);
						if (colChkLst != null) {


							for (int x = 0; x < colChkLst.length; x++) {
								if(colChkLst[x].getCheckListID()!=(-999999999))
								{
		                      
									ICheckListTrxValue checkListTrxVal = proxy3.getCheckList(colChkLst[x].getCheckListID());
									ICheckList checkList = checkListTrxVal.getCheckList();
									
//									OBCMSTrxHistoryLog[] commentList = (OBCMSTrxHistoryLog[]) checkListTrxVal
//											.getTransactionHistoryCollection().toArray(new OBCMSTrxHistoryLog[0]);
									ICheckListItem[] items = checkList.getCheckListItemList();
									if (items != null) {
										for (int xx = 0; xx < items.length; xx++) {
											String securityType="";
											if (!ICMSConstant.STATE_ITEM_WAIVED.equals(items[xx].getItemStatus())) {
												if(!hmCaseCreation.containsKey(String.valueOf(items[xx].getCheckListItemID()))){
													if(!hmStageCaseCreation.containsKey(String.valueOf(items[xx].getCheckListItemID()))){
												receivedList.add(items[xx]);
												typeMap.put(String.valueOf(items[xx].getCheckListItemID()), "Security");
												ICollateralCheckListOwner owner = (ICollateralCheckListOwner) checkList.getCheckListOwner();
												long collateralID = owner.getCollateralID();
												ICollateralProxy cProxy = CollateralProxyFactory.getProxy();
												ICollateral iCol;
												try {
													iCol = cProxy.getCollateral(collateralID, false);
													if(iCol!=null){
														securityType=iCol.getCollateralType().getTypeName()+"-"+iCol.getCollateralSubType().getSubTypeName();
													}
												}
												catch (CollateralException ex) {
													throw new MapperException("failed to retrieve collateral instance for collateral id [" + collateralID
															+ "]", ex);
												}
												typeMap.put(String.valueOf(items[xx].getCheckListItemID()), "Security");
		                                       securityTypeMap.put(String.valueOf(items[xx].getCheckListItemID()), securityType);
													}
												}
											}
										}
									}
								}
							}
						}
					}
	        	
					resultMap.put("receivedList", receivedList);
					resultMap.put("facilityNameMap", facilityNameMap);
					resultMap.put("typeMap", typeMap);
					resultMap.put("securityTypeMap", securityTypeMap);
	        	
	        } catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
	        
	        resultMap.put("isCPUT", isCPUT);
	        resultMap.put("isBRANCH", isBRANCH);
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	    }
}



