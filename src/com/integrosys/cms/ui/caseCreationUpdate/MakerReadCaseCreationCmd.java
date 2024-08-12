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
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
/**
*@author $Author: Abhijit R$
*Command to read CaseCreation
 */
public class MakerReadCaseCreationCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private ICaseCreationProxyManager caseCreationProxy;

	
	
	
	public ICaseCreationProxyManager getCaseCreationProxy() {
		return caseCreationProxy;
	}

	public void setCaseCreationProxy(
			ICaseCreationProxyManager caseCreationProxy) {
		this.caseCreationProxy = caseCreationProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerReadCaseCreationCmd() {
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
				 {"id", "java.lang.String", REQUEST_SCOPE},
				 {"limitProfileID", "java.lang.String", REQUEST_SCOPE},
				 { "startIndex", "java.lang.String", REQUEST_SCOPE },
				 { IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				 {"event", "java.lang.String", REQUEST_SCOPE}		 
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
				{ "caseCreationUpdateObj", "com.integrosys.cms.app.caseCreationUpdate.bus.OBCaseCreation", SERVICE_SCOPE },
				{ "caseCreationUpdateObj", "com.integrosys.cms.app.caseCreationUpdate.bus.OBCaseCreation", FORM_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "hubValueList", "java.util.List", REQUEST_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
				{"typeMap", "java.util.ArrayList", SERVICE_SCOPE},
                {"facilityNameMap", "java.util.ArrayList", SERVICE_SCOPE},
                {"securityTypeMap", "java.util.ArrayList", SERVICE_SCOPE},
                {"receivedList", "java.util.ArrayList", SERVICE_SCOPE},
                {"searchResultCaseCreation", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
				{"ICaseCreationTrxValue", "com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue", SERVICE_SCOPE},
				{ "coordinator1Name", "java.lang.String", SERVICE_SCOPE },
				{ "coordinator2Name", "java.lang.String", SERVICE_SCOPE },
				{"partyId", "java.lang.String", REQUEST_SCOPE},
				{"partyName", "java.lang.String", REQUEST_SCOPE},
				{ "submittedToValueList", "java.util.List", REQUEST_SCOPE },
				{ "submittedToCodeList", "java.util.List", REQUEST_SCOPE },
				{ "submittedToValueList", "java.util.List", SERVICE_SCOPE },
				{ "submittedToCodeList", "java.util.List", SERVICE_SCOPE },
				{"userEmployeeId", "java.lang.String", REQUEST_SCOPE},
				{"userEmployeeLoginId", "java.lang.String", REQUEST_SCOPE},
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		 HashMap facilityNameMap = new HashMap();
	        HashMap securityTypeMap = new HashMap();
	        List receivedList = new ArrayList();
	        HashMap typeMap = new HashMap();
	        List transactionHistoryList= new ArrayList();
	        System.out.println("Inside MakerReadCaseCreationCmd().");
		try {
			ICaseCreation caseCreationUpdate;
			ICaseCreationTrxValue trxValue=null;
			String caseCreationUpdateCode=(String) (map.get("id"));
			String startIdx = (String) map.get("startIndex");
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID =0l;
			if(limit!=null){
				 limitProfileID = limit.getLimitProfileID();
			}else{
				String alimitProfileID =(String) (map.get("limitProfileId"));
				limitProfileID =Long.parseLong(alimitProfileID);
			}
			System.out.println("caseCreationUpdateCode=>"+caseCreationUpdateCode+" ** limitProfileID=>"+limitProfileID+" ** startIdx=>"+startIdx);
			String partyId = "";
			String partyName = "";
			if(theOBTrxContext.getCustomer() != null) {
			partyId = (String)theOBTrxContext.getCustomer().getCifId();
			partyName = (String)theOBTrxContext.getCustomer().getCustomerNameUpper();
			}
			String userEmployeeId = theOBTrxContext.getUser().getEmployeeID();
			String userEmployeeLoginId = theOBTrxContext.getUser().getLoginID();
			// DefaultLogger.debug(this,"Limit profile "+limit);
			//ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			
			ICheckListProxyManager proxy3 = CheckListProxyManagerFactory.getCheckListProxyManager();
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			String event = (String) map.get("event");
			System.out.println("partyId=>"+partyId+" ** partyName=>"+partyName+" ** event=>"+event);
			
			if(!"maker_add_edit_caseCreationUpdate_error".equals(event)) {
			trxValue = (OBCaseCreationTrxValue) getCaseCreationProxy().getCaseCreationTrxValue(Long.parseLong(caseCreationUpdateCode));
			caseCreationUpdate = (OBCaseCreation) trxValue.getCaseCreation();
			System.out.println("trxValue=>"+trxValue);
			ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
			System.out.println("caseCreationUpdate=>"+caseCreationUpdate);
			 transactionHistoryList = customerDAO.getTransactionHistoryList(trxValue.getTransactionID());
			long id = caseCreationUpdate.getId();
			if(!trxValue.getStatus().equals("ACTIVE")){
				resultMap.put("wip", "wip");
			}
			if(caseCreationUpdate!=null){
			List resultList=getCaseCreationProxy().getCaseCreationByBranchCode(caseCreationUpdate.getBranchCode());
			System.out.println("id=>"+id+" ** caseCreationUpdate.getBranchCode()=>"+caseCreationUpdate.getBranchCode());
			if(resultList!=null && resultList.size()>0){
				OBCaseBranch obCaseBranch= (OBCaseBranch)resultList.get(0);
				resultMap.put("coordinator1Name",obCaseBranch.getCoordinator1());
				resultMap.put("coordinator2Name",obCaseBranch.getCoordinator2());
			}
			}
			
			if("".equals(partyId)) {
				partyName = (String)trxValue.getLegalName();
				String legalId = trxValue.getLegalID();
				partyId = getCustIdForCaseCreation(legalId);
			}
			System.out.println("partyName=>"+partyName+" ** partyId=>"+partyId);
			ICaseCreationDao caseCreationDao=(ICaseCreationDao)BeanHouse.get("caseCreationDao");
			SearchResult searchResultCaseCreation=caseCreationDao.listCaseCreation("actualCaseCreation", caseCreationUpdate.getId());
			
			
			HashMap checkListMap = proxy3.getAllCollateralCheckListSummaryList(theOBTrxContext, limitProfileID);
        	
        	
       	 CheckListSearchResult camCheckList= proxy3.getCAMCheckListByCategoryAndProfileID("CAM",limitProfileID);
			 if(camCheckList!=null){
				 
				 ICheckListTrxValue checkListTrxVal = proxy3.getCheckList( camCheckList.getCheckListID());
				 System.out.println("checkListTrxVal=>"+checkListTrxVal);
					ICheckList checkList = checkListTrxVal.getCheckList();
					System.out.println("checkList=>"+checkList);
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
			 System.out.println("camCheckList=>"+camCheckList+" ** limit=>"+limit);
			 
			
			 
			   	
			 ILimit[] OB=limit.getLimits();
	          	for(int i=0;i<OB.length;i++){
	              ILimit obLimit= OB[i];
	              long limitId= obLimit.getLimitID();
	              DefaultLogger.debug(this, "limitId for casecreation: " + limitId);
	              System.out.println("limitId for casecreation: " + limitId);
	              
	            	  CheckListSearchResult facilitycheckList=proxy3.getCheckListByCollateralID(limitId);
	            	  if(facilitycheckList!=null){
	            		    DefaultLogger.debug(this, "facilitycheckList.getCheckListID() for casecreation: " + facilitycheckList.getCheckListID());
	     				 ICheckListTrxValue checkListTrxVal = proxy3.getCheckList( facilitycheckList.getCheckListID());
	     					ICheckList checkList = checkListTrxVal.getCheckList();
	     					ICheckListItem[] items = checkList.getCheckListItemList();
	     					if (items != null) {
	     						 DefaultLogger.debug(this, "items for casecreation: " + items.length);
	     						for (int xx = 0; xx < items.length; xx++) {
	     							if (!ICMSConstant.STATE_ITEM_WAIVED.equals(items[xx].getItemStatus())) {
	     								 DefaultLogger.debug(this, "in if loop for casecreation:------------------------------------- " + xx+"-------------------------------------------");
	     								receivedList.add(items[xx]);
	     								typeMap.put(String.valueOf(items[xx].getCheckListItemID()), "Facility");
	     								facilityNameMap.put(String.valueOf(items[xx].getCheckListItemID()), obLimit.getFacilityName());
	     								
	     							}
	     						}
	     					}
	     			 }
	     			 
				
	          	}
	          	System.out.println("After OB=limit.getLimits() " );
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
				 System.out.println("After otherCheckList " );
	          	
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
												System.out.println("failed to retrieve collateral instance for collateral id [" + collateralID + "]  ** ex=>" + ex);
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
				System.out.println("After checkListMap " );
				resultMap.put("searchResultCaseCreation", searchResultCaseCreation);
				resultMap.put("caseCreationUpdateObj", caseCreationUpdate);
				resultMap.put("ICaseCreationTrxValue", trxValue);
				resultMap.put("receivedList", receivedList);
				resultMap.put("facilityNameMap", facilityNameMap);
				resultMap.put("typeMap", typeMap);
				resultMap.put("securityTypeMap", securityTypeMap);
				
				
				resultMap.put("partyId", partyId);
				resultMap.put("partyName", partyName);
				resultMap.put("userEmployeeId", userEmployeeId);
				resultMap.put("userEmployeeLoginId", userEmployeeLoginId);
			resultMap.put("event", event);
			resultMap.put("startIndex",startIdx);
			resultMap.put("transactionHistoryList", transactionHistoryList);
			}
				List<String> submittedToValueList=new ArrayList<String>();
				submittedToValueList = getSubmittedToValueList ();
				
				List<String> submittedToCodeList=new ArrayList<String>();
				submittedToCodeList = getSubmittedToCodeList ();
				
				resultMap.put("submittedToValueList", submittedToValueList);
				resultMap.put("submittedToCodeList", submittedToCodeList);
				
				
				List<String> vaultLocationValueList=new ArrayList<String>();
				vaultLocationValueList = getVaultLocationValueList ();
				
				resultMap.put("vaultLocationValueList", vaultLocationValueList);
				
				/*resultMap.put("receivedList", receivedList);
				resultMap.put("facilityNameMap", facilityNameMap);
				resultMap.put("typeMap", typeMap);
				resultMap.put("securityTypeMap", securityTypeMap);
				
				
				resultMap.put("partyId", partyId);
				resultMap.put("partyName", partyName);
			resultMap.put("event", event);
			resultMap.put("startIndex",startIdx);	*/
//			resultMap.put("ICaseCreationTrxValue", trxValue);
			
//			 resultMap.put("transactionHistoryList", transactionHistoryList);
		}catch (CaseCreationException ex) {
			System.out.println("got exception in doExecute CaseCreationException=>"+ex);
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		catch (TransactionException e) {
			System.out.println("got exception in doExecute TransactionException=>"+e);
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
			System.out.println("got exception in doExecute Exception=>" + e);
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
		

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	public String getCustIdForCaseCreation(String legalId) {
			System.out.println( "Inside getCustIdForCaseCreation Method");
			String custId="";
			String sql="SELECT LMP_LE_ID FROM SCI_LE_MAIN_PROFILE where CMS_LE_MAIN_PROFILE_ID='"+legalId+"' ";
			DBUtil dbUtil=null;
		try {
			 dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					custId=rs.getString("LMP_LE_ID");
				}
			}
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
		return custId;
		
	}
	
	
	public List<String> getSubmittedToValueList(){
		
		System.out.println("Inside getSubmittedToValueList Method");
		List<String> submittedToValueList=new ArrayList<String>();
		String sql="SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'SUBMITTED_TO' AND ACTIVE_STATUS='1' ORDER BY ENTRY_NAME ";
		DBUtil dbUtil=null;
	try {
		 dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		ResultSet rs = dbUtil.executeQuery();
		if(null!=rs){
			while(rs.next()){
				submittedToValueList.add(rs.getString("ENTRY_NAME"));
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
	return submittedToValueList;
	}
	
	
	
public List<String> getSubmittedToCodeList(){
		
		System.out.println("Inside getSubmittedToCodeList Method");
		List<String> submittedToCodeList=new ArrayList<String>();
		String sql="SELECT ENTRY_CODE FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'SUBMITTED_TO' AND ACTIVE_STATUS='1' ORDER BY ENTRY_NAME ";
		DBUtil dbUtil=null;
	try {
		 dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		ResultSet rs = dbUtil.executeQuery();
		if(null!=rs){
			while(rs.next()){
				submittedToCodeList.add(rs.getString("ENTRY_CODE"));
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
	return submittedToCodeList;
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
