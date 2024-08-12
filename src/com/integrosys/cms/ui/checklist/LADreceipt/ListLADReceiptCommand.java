/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.LADreceipt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICustomerSearchResult;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.lad.bus.ILAD;
import com.integrosys.cms.app.lad.bus.ILADDao;
import com.integrosys.cms.app.lad.bus.ILADItem;
import com.integrosys.cms.app.lad.bus.ILADSubItem;
import com.integrosys.cms.app.lad.bus.OBLADItem;
import com.integrosys.cms.app.lad.bus.OBLADSubItem;
import com.integrosys.cms.app.lad.proxy.ILADProxyManager;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: cwtan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/02/04 03:19:21 $ Tag: $Name: $
 */
public class ListLADReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListLADReceiptCommand() {
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
                { "isViewFlag", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
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
//				{ "colChkLst", "java.util.List", REQUEST_SCOPE },
				{ "LADCheckList", "com.integrosys.cms.app.checklist.bus.CheckListSearchResult", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", FORM_SCOPE },
//				{ "innerOuterBcaObList", "java.util.HashMap", REQUEST_SCOPE },
                { "isViewFlag", "java.lang.Boolean", REQUEST_SCOPE }});
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
		ICheckListProxyManager checklistProxyManager=(ICheckListProxyManager) BeanHouse.get("checklistProxy");
		DefaultLogger.debug(this, "Inside doExecute()");
		ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		long limitProfileID = limit.getLimitProfileID();
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
        String isViewFlag = (String) map.get("isViewFlag");

          try {
        	  CheckListSearchResult LADCheckList= checklistProxyManager.getCAMCheckListByCategoryAndProfileID("LAD",limitProfileID);
        	  
        	  resultMap.put("LADCheckList", LADCheckList);
			
		} catch (CheckListException e) {
			
			e.printStackTrace();
			throw new CommandProcessingException("failed to retrieve  checklist ", e);
		}
      
		
		//updateLogin();
		//updateLAD();

		resultMap.put("frame", "true");
		resultMap.put("limitProfileID", String.valueOf(limitProfileID));
        resultMap.put("isViewFlag", new Boolean(isViewFlag));

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	
	
	private void updateLAD(){
		HashMap updateLad = new HashMap();
		try{
			
		ILADProxyManager ladProxy =(ILADProxyManager)BeanHouse.get("ladProxy");	
			List listDate=new ArrayList();
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
		IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
		Date dateApplication=new Date();
		long ladGenIndicator=0l;
		for(int i=0;i<generalParamEntries.length;i++){
			if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
				dateApplication=new Date(generalParamEntries[i].getParamValue());
			}else if(generalParamEntries[i].getParamCode().equals("LAD_GEN_INDICATOR")){
				ladGenIndicator=Long.parseLong(generalParamEntries[i].getParamValue());
			} 
		}
		ILADDao iladDao= (ILADDao) BeanHouse.get("ladDao");
		List ladList= iladDao.getAllLAD();
		if(ladList!=null){
			int ind=0;
			for(int i=0;i<ladList.size();i++){
				ILAD ilad=(ILAD)ladList.get(i);
				if(ilad.getLad_due_date()!=null){
					long  diff=ilad.getLad_due_date().getTime()-dateApplication.getTime();
				
					if(diff<0){
						ind++;
						updateLad.put(String.valueOf(ind),String.valueOf(ilad.getLimit_profile_id() ));
					}else{
				long dateDiff=	CommonUtil.dateDiff(ilad.getLad_due_date(), dateApplication, Calendar.MONTH);
					if(dateDiff<=ladGenIndicator){
						ind++;
						updateLad.put(String.valueOf(ind),String.valueOf(ilad.getLimit_profile_id() ));
					}
				}
					
				}
			}
		}
		if(updateLad.size()!=0){
			
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();	
			
        	 
			for(int b=1;b<=updateLad.size();b++){
				String alimitProfileId=(String)updateLad.get(String.valueOf(b));
				long limitProfileId=Long.parseLong(alimitProfileId);
				ILimitProxy limitProxy = LimitProxyFactory.getProxy();
				ILimitProfile limit = limitProxy.getLimitProfile(limitProfileId);
				 CheckListSearchResult ladCheckList= proxy.getCAMCheckListByCategoryAndProfileID("LAD",limitProfileId);
							
				 ICheckList[] checkLists= proxy.getAllCheckList(limit);
			        ICheckList[] finalCheckLists= new ICheckList[checkLists.length];
			        ArrayList expList= new ArrayList();
				
				
				
				 if(checkLists!=null){
			        	int a=0;
		                for (int y = 0; y < checkLists.length; y++) {
		                	if(checkLists[y].getCheckListType().equals("F")||checkLists[y].getCheckListType().equals("S")){
		                	
			        	ICheckListItem[] curLadList = (ICheckListItem[]) checkLists[y].getCheckListItemList();
		                  if(curLadList !=null){
		                	  ArrayList expList2= new ArrayList();
			        		for (int z = 0; z < curLadList.length; z++) {
			        			  
		                      ICheckListItem item = (ICheckListItem) curLadList[z];
		                      if(item!=null){
		                    	  if(item.getItemStatus().equals("RECEIVED")){
		                    	  if(item.getExpiryDate()!=null){
		                      			expList2.add(item.getExpiryDate());
		                      		}
		                    	  }
		                      }
			        		}
			        		if(expList2.size()>0){
			        			finalCheckLists[a]=checkLists[y];
			        			a++;
			        		}
		                  }
		                }
		                }
			        }
				
				
				 boolean generateLad=false;
			        
			                                                                                            
			        
			        if(finalCheckLists!=null){
			        for(int k=0;k<finalCheckLists.length;k++){
			        	 if(finalCheckLists[k]!=null){
			        if(finalCheckLists[k].getCheckListItemList()!=null){
		        		ICheckListItem[] checkListItems=finalCheckLists[k].getCheckListItemList();
		        		
		        		for(int m=0;m<checkListItems.length;m++){
		        			  if(checkListItems[m].getExpiryDate()!=null){
		        			 if(checkListItems[m].getItemStatus().equals("RECEIVED")){
		        			if(checkListItems[m].getExpiryDate()!=null){
		        			listDate.add(checkListItems[m].getExpiryDate());
		        			generateLad=true;
		        			}
		        		}
		        		}
		        		}
		        		Collections.sort(listDate);
			        	}
			        }
			        }
			        }
				
				
				
				if(finalCheckLists!=null){
					boolean isNotPreviousGenerated=true;
					ILAD  ilad=(ILAD)iladDao.getLADNormal(limitProfileId).get(0);
			        if("Y".equals(ilad.getIsOperationAllowed())){
			        	isNotPreviousGenerated=false;
			        }
					ilad.setIsOperationAllowed("Y");
			        Date changedDueDate = null;
			        Date date=(Date)listDate.get(0);
			       // changedDueDate  = CommonUtil.rollUpDateByYears(date, 3);
			        ilad.setLad_due_date(date);
					ilad=ladProxy.updateLAD(ilad);
					List ladItemList= ladProxy.getLADItem(ilad.getLad_id());
				if(ladItemList!=null){
					if(ladItemList.size()!=0){
					for(int i=0;i<ladItemList.size();i++){
						ILADItem ilad2=(ILADItem)ladItemList.get(i);
						if(ilad2!=null){
							ladProxy.deleteLADSubItem(ilad2.getDoc_item_id());
						}
					}
				}
				}
					ladProxy.deleteLADItem(ilad.getLad_id());
				//	if(isNotPreviousGenerated){
			        for(int i=0;i<finalCheckLists.length;i++){
			        	  ILADItem iladItem= new OBLADItem();
			        	  if(finalCheckLists[i]!=null){
			        		iladItem.setCategory(finalCheckLists[i].getCheckListType());
			        	iladItem.setLad_id(ilad.getLad_id());
			        	iladItem=ladProxy.createLADItem(iladItem);
			        	
			        	if(finalCheckLists[i].getCheckListItemList()!=null){
			        		ICheckListItem[] checkListItems=finalCheckLists[i].getCheckListItemList();
			        		for(int j=0;j<checkListItems.length;j++){
			        			if(checkListItems[j].getExpiryDate()!=null){
			        			if(checkListItems[j].getItemStatus().equals("RECEIVED")){
			        			ILADSubItem iladSubItem= new OBLADSubItem();
			        			iladSubItem.setDoc_item_id(iladItem.getDoc_item_id());
			        			iladSubItem.setCategory(finalCheckLists[i].getCheckListType());
			        			iladSubItem.setDoc_description(checkListItems[j].getItemDesc());
			        			iladSubItem.setExpiry_date(checkListItems[j].getExpiryDate());
			        			ladProxy.createLADSubItem(iladSubItem);
			        			}
			        		}
			        		}
			        	}
			        }
			        	
			        }
			//	}
			        
			        }
			}
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
/*private void updateLogin(){
		
		
		DBUtil dbUtil = null;
		
		String sql = "update CMS_LOGIN_AUDIT set server='app1' where login_id='ABHI11'";


		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
		
			int  rs = dbUtil.executeUpdate();
	
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListItemListForAudit", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
			}
		}
		
	}*/
}
