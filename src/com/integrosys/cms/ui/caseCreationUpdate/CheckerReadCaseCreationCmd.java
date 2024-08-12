/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.caseCreationUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.caseBranch.bus.OBCaseBranch;
import com.integrosys.cms.app.caseCreation.bus.ICaseCreationDao;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationException;
import com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreation;
import com.integrosys.cms.app.caseCreationUpdate.bus.OBCaseCreation;
import com.integrosys.cms.app.caseCreationUpdate.proxy.ICaseCreationProxyManager;
import com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue;
import com.integrosys.cms.app.caseCreationUpdate.trx.OBCaseCreationTrxValue;
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
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 *$Author: Abhijit R $
 *Command for checker to read CaseCreation Trx value
 */
public class CheckerReadCaseCreationCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private ICaseCreationProxyManager caseCreationProxy;

	public ICaseCreationProxyManager getCaseCreationProxy() {
		return caseCreationProxy;
	}

	public void setCaseCreationProxy(ICaseCreationProxyManager caseCreationProxy) {
		this.caseCreationProxy = caseCreationProxy;
	}
	
	
	
	/**
	 * Default Constructor
	 */
	public CheckerReadCaseCreationCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
					GLOBAL_SCOPE },
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
			{IGlobalConstant.GLOBAL_LOS_USER, "com.integrosys.component.user.app.bus.ICommonUser",GLOBAL_SCOPE},
			{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
			{ "coordinator1Name", "java.lang.String", REQUEST_SCOPE },
			{ "coordinator2Name", "java.lang.String", REQUEST_SCOPE }
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
				{ "caseCreationUpdateObj", "com.integrosys.cms.app.caseCreationUpdate.bus.OBCaseCreation", FORM_SCOPE },
				{ "caseCreationUpdateObj", "com.integrosys.cms.app.caseCreationUpdate.bus.OBCaseCreation", SERVICE_SCOPE },
				{"ICaseCreationTrxValue", "com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue", SERVICE_SCOPE},
				{"searchResultCaseCreation", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
				{"actualSearchResultCaseCreation", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"typeMap", "java.util.ArrayList", SERVICE_SCOPE},
                {"facilityNameMap", "java.util.ArrayList", SERVICE_SCOPE},
                {"securityTypeMap", "java.util.ArrayList", SERVICE_SCOPE},
                {"receivedList", "java.util.ArrayList", SERVICE_SCOPE},
                { "coordinator1Name", "java.lang.String", SERVICE_SCOPE },
				{ "coordinator2Name", "java.lang.String", SERVICE_SCOPE },
				{ "vaultLocationValueList", "java.util.List", REQUEST_SCOPE },
				{ "vaultLocationValueList", "java.util.List", SERVICE_SCOPE },
				
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,CaseCreationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		 HashMap typeMap = new HashMap();
	        HashMap facilityNameMap = new HashMap();
	        HashMap securityTypeMap = new HashMap();
	        List receivedList = new ArrayList();
		try {
			ICaseCreation caseCreationUpdate;
			ICaseCreation actualCaseCreationUpdate;
			ICaseCreation caseCreationUpdateActual;
			ICaseCreationTrxValue trxValue=null;
			String branchCode=(String) (map.get("TrxId"));
			String event = (String) map.get("event");
			
			 ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
				long limitProfileID = limit.getLimitProfileID();
				// DefaultLogger.debug(this,"Limit profile "+limit);
				ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
				OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
				ICheckListProxyManager proxy3 = CheckListProxyManagerFactory.getCheckListProxyManager();
			// function to get CaseCreation Trx value
			trxValue = (OBCaseCreationTrxValue) getCaseCreationProxy().getCaseCreationByTrxID(branchCode);
			caseCreationUpdateActual = (OBCaseCreation) trxValue.getCaseCreation();
			caseCreationUpdate = (OBCaseCreation) trxValue.getStagingCaseCreation();
			actualCaseCreationUpdate = (OBCaseCreation) trxValue.getCaseCreation();
			SearchResult actualSearchResultCaseCreation=null;	
			if(caseCreationUpdate!=null){
				List resultList=getCaseCreationProxy().getCaseCreationByBranchCode(caseCreationUpdate.getBranchCode());
				if(resultList!=null && resultList.size()>0){
					OBCaseBranch obCaseBranch= (OBCaseBranch)resultList.get(0);
					resultMap.put("coordinator1Name",obCaseBranch.getCoordinator1());
					resultMap.put("coordinator2Name",obCaseBranch.getCoordinator2());
				}
				}	
			ICaseCreationDao caseCreationDao=(ICaseCreationDao)BeanHouse.get("caseCreationDao");
			//SearchResult searchResultCaseCreationActual=caseCreationDao.listCaseCreation("actualCaseCreation", caseCreationUpdateActual.getId());
			SearchResult searchResultCaseCreation=caseCreationDao.listCaseCreation("stageCaseCreation", caseCreationUpdate.getId());
			if(actualCaseCreationUpdate!=null){
			actualSearchResultCaseCreation=caseCreationDao.listCaseCreation("actualCaseCreation", actualCaseCreationUpdate.getId());
			}
			
			HashMap checkListMap = proxy3.getAllCollateralCheckListSummaryList(theOBTrxContext, limit.getLimitProfileID());
        	
        	
       	 CheckListSearchResult camCheckList= proxy3.getCAMCheckListByCategoryAndProfileID("CAM",limitProfileID);
			 if(camCheckList!=null){
				 
				 ICheckListTrxValue checkListTrxVal = proxy3.getCheckList( camCheckList.getCheckListID());
					ICheckList checkList = checkListTrxVal.getCheckList();
					ICheckListItem[] items = checkList.getCheckListItemList();
					if (items != null) {
						for (int xx = 0; xx < items.length; xx++) {
							if (!ICMSConstant.STATE_ITEM_WAIVED.equals(items[xx].getItemStatus())) {
								receivedList.add(items[xx]);
								typeMap.put(String.valueOf(items[xx].getCheckListItemID()), "CAM");
                            

								
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
	     								receivedList.add(items[xx]);
	     								typeMap.put(String.valueOf(items[xx].getCheckListItemID()), "Facility");
	     								facilityNameMap.put(String.valueOf(items[xx].getCheckListItemID()), obLimit.getFacilityName());
	     								
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
									receivedList.add(itemsOther[xx]);
									typeMap.put(String.valueOf(itemsOther[xx].getCheckListItemID()), "Other");
	                             

									
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
								
//								OBCMSTrxHistoryLog[] commentList = (OBCMSTrxHistoryLog[]) checkListTrxVal
//										.getTransactionHistoryCollection().toArray(new OBCMSTrxHistoryLog[0]);
								ICheckListItem[] items = checkList.getCheckListItemList();
								if (items != null) {
									for (int xx = 0; xx < items.length; xx++) {
										String securityType="";
										if (!ICMSConstant.STATE_ITEM_WAIVED.equals(items[xx].getItemStatus())) {
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
				
				
				
				List<String> vaultLocationValueList=new ArrayList<String>();
				vaultLocationValueList = getVaultLocationValueList ();
				
				resultMap.put("vaultLocationValueList", vaultLocationValueList);
				
				
				
				
       	
				resultMap.put("receivedList", receivedList);
				resultMap.put("facilityNameMap", facilityNameMap);
				resultMap.put("typeMap", typeMap);
				resultMap.put("securityTypeMap", securityTypeMap);
			
			
			
			
			
			resultMap.put("ICaseCreationTrxValue", trxValue);
			resultMap.put("caseCreationUpdateObj", caseCreationUpdate);
			resultMap.put("searchResultCaseCreation", searchResultCaseCreation);
			resultMap.put("actualSearchResultCaseCreation",actualSearchResultCaseCreation);
			resultMap.put("event", event);
		} catch (CaseCreationException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	

	public List<String> getVaultLocationValueList(){
		
		System.out.println("Inside getVaultLocationValueList Method");
		List<String> vaultLocationValueList=new ArrayList<String>();
		String sql="SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Vault_Location' AND ACTIVE_STATUS='1' ORDER BY ENTRY_NAME ";
		DBUtil dbUtil=null;
	try {
		 dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		ResultSet rs = dbUtil.executeQuery();
		if(null!=rs){
			while(rs.next()){
				vaultLocationValueList.add(rs.getString("ENTRY_NAME"));
			}
		}
		rs.close();
		
	} catch (DBConnectionException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	finally{
		try {
			dbUtil.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return vaultLocationValueList;
	}
	
	
}
