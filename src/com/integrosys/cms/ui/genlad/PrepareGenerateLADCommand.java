/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genlad;

import java.util.ArrayList;
import java.util.HashMap;

import antlr.collections.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.lad.proxy.ILADProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/12/12 10:03:30 $ Tag: $Name: $
 */
public class PrepareGenerateLADCommand extends AbstractCommand implements ICommonEventConstant {
	private ILADProxyManager ladProxy;
	
	
	public ILADProxyManager getLadProxy() {
		return ladProxy;
	}

	public void setLadProxy(ILADProxyManager ladProxy) {
		this.ladProxy = ladProxy;
	}
	/**
	 * Default Constructor
	 */
	public PrepareGenerateLADCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
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
				{ "checkLists", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE }
				
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
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			resultMap.put("theOBTrxContext", theOBTrxContext);

			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
	        ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
	        long limitProfileID = limit.getLimitProfileID();
	        ICheckList[] checkLists= proxy.getAllCheckList(limit);
	        ICheckList[] finalCheckLists= new ICheckList[checkLists.length];
	        ArrayList expList= new ArrayList();
	        
	        if(checkLists!=null){
	        	int a=0;
                for (int y = 0; y < checkLists.length; y++) {
                	if(!checkLists[y].getCheckListType().equals("CAM")){
                	
	        	ICheckListItem[] curLadList = (ICheckListItem[]) checkLists[y].getCheckListItemList();
                  if(curLadList !=null){
                	  ArrayList expList2= new ArrayList();
	        		for (int z = 0; z < curLadList.length; z++) {
	        			  
                      ICheckListItem item = (ICheckListItem) curLadList[z];
                      if(item!=null){
                    	  if(!(item.getItemStatus().equals("WAIVED"))){
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
	        
	        
	        if(checkLists!=null){
                for (int y = 0; y < checkLists.length; y++) {
	        	ICheckListItem[] curLadList = (ICheckListItem[]) checkLists[y].getCheckListItemList();
                  if(curLadList !=null){
	        		for (int z = 0; z < curLadList.length; z++) {
                      ICheckListItem item = (ICheckListItem) curLadList[z];
                      if(item!=null){
                    	  if(!(item.getItemStatus().equals("WAIVED"))){
                    	  if(item.getExpiryDate()!=null){
                      			expList.add(item.getExpiryDate());
                      		}
                    	  }
                      }
	        		}
                  }
                }
	        }
//	        System.out.println("%%%%%%%%%%%%%%%%%%%%%finalCheckLists.length%%%%%%%%%%%%%%%%%%%%%%%"+finalCheckLists.length);
	        if(finalCheckLists.length==0){
	        	resultMap.put("no_template", "true");
	        }
	        resultMap.put("checkLists", finalCheckLists);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	
}