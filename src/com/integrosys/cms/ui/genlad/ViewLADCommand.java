/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genlad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.chktemplate.bus.DocumentHeldSearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.ddn.bus.DDNException;
import com.integrosys.cms.app.ddn.bus.DDNNotRequiredException;
import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.ddn.bus.IDDNCustomerDetail;
import com.integrosys.cms.app.ddn.proxy.DDNProxyManagerFactory;
import com.integrosys.cms.app.ddn.proxy.IDDNProxyManager;
import com.integrosys.cms.app.ddn.trx.IDDNTrxValue;
import com.integrosys.cms.app.lad.bus.ILAD;
import com.integrosys.cms.app.lad.bus.ILADDao;
import com.integrosys.cms.app.lad.bus.ILADItem;
import com.integrosys.cms.app.lad.bus.ILADSubItem;
import com.integrosys.cms.app.lad.bus.OBLAD;
import com.integrosys.cms.app.lad.bus.OBLADItem;
import com.integrosys.cms.app.lad.bus.OBLADSubItem;
import com.integrosys.cms.app.lad.proxy.ILADProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ITATEntry;
import com.integrosys.cms.app.limit.bus.TATComparator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/11/15 12:50:06 $ Tag: $Name: $
 */
public class ViewLADCommand extends AbstractCommand implements ICommonEventConstant {
	
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
	public ViewLADCommand() {
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
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "ladId", "java.lang.String", SERVICE_SCOPE },
				{ "tagDetailList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "custTypeTrxID", "java.lang.String", REQUEST_SCOPE },
				{ "id", "java.lang.String", REQUEST_SCOPE },
				{ "checklistId", "java.lang.String", REQUEST_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "ladName", "java.lang.String", REQUEST_SCOPE },
               
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
		return (new String[][] { { "custDetail", "com.integrosys.cms.app.ddn.bus.IDDNCustomerDetail", SERVICE_SCOPE },
				{ "certTrxVal", "com.integrosys.cms.app.ddn.trx.IDDNTrxValue", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.ddn.bus.IDDN", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.ddn.bus.IDDN", FORM_SCOPE },
				{ "ladId", "java.lang.String", SERVICE_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE }, { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "error", "java.lang.String", REQUEST_SCOPE },
				{ "custTypeTrxID", "java.lang.String", REQUEST_SCOPE },
				{ "bflFinalIssueDate", "java.lang.String", REQUEST_SCOPE },
				{ "tagDetailList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "ladList", "java.util.List", SERVICE_SCOPE },
				{ "listLADSubItem", "java.util.List", REQUEST_SCOPE },
				{ "lad", "com.integrosys.cms.app.lad.bus.ILAD", REQUEST_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "checklistId", "java.lang.String", REQUEST_SCOPE },
                { "docsHeldMap", "java.util.HashMap", REQUEST_SCOPE}});
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
		
		List tagDetailList = new ArrayList();
		//retriving image list available for tagging
		resultMap.put("tagDetailList", tagDetailList);
		try {
			ICheckList checkList = (ICheckList) map.get("checkList");
			resultMap.put("checkList", checkList);
			List ladList=new ArrayList();
			String event=(String)map.get("event");
			String ladId=(String)map.get("ladId");
			if(null==ladId || "".equals(ladId)||"null".equals(ladId)){
				ladId=(String)map.get("id");
			}
			if("prepare_checker_view_lad".equals(event)||"checker_totrack_view_lad".equals(event)
					||"prepare_view_process_lad".equals(event)||"prepare_totrack_view_lad".equals(event)
					||"draft_close_view_lad".equals(event)||"draft_process_view__lad".equals(event)
					||"prepare_view_lad_doc".equals(event)||"prepare_view_process_lad_doc".equals(event)){
				ladId=(String)map.get("id");
			}
			long id=Long.parseLong(ladId);
			String checklistId=(String)map.get("checklistId");
			//long id=Long.parseLong(ladId);
			String ladName=(String)map.get("ladName");
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			ILADProxyManager ladProxy =(ILADProxyManager)BeanHouse.get("ladProxy");
 			ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
	        ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
	        long limitProfileID = limit.getLimitProfileID();
	        ILADDao iladDao= (ILADDao) BeanHouse.get("ladDao");
	        List list=ladProxy.getLAD(limitProfileID,"GEN");
	        List listLADSubItem=iladDao.getLADSubItemById(id);
	        ILAD ilad=null;
			if(list!=null){
				ilad=(ILAD)list.get(0);
			}
				
				/*List listLADItem=ladProxy.getLADItem(ilad.getLad_id());
				ILADItem[] items=new OBLADItem[listLADItem.size()];
				if(listLADItem!=null){
					for(int j=0;j<listLADItem.size();j++){
						items[j]=(ILADItem) listLADItem.get(j);
						List listLADSubItem=ladProxy.getLADSubItem(items[j].getDoc_item_id());
						ILADSubItem[] itemSubItems=new OBLADSubItem[listLADSubItem.size()];
						if(listLADSubItem!=null){
							for(int k=0;k<listLADSubItem.size();k++){
								itemSubItems[k]=(ILADSubItem) listLADSubItem.get(k);
							}
					}
						items[j].setIladSubItem(itemSubItems);
				}
			}
	        ilad.setIladItem(items);
	        resultMap.put("lad", ilad);
	        ladList.add(ilad);
	        List listLADSubItem=iladDao.getLADSubItemSorted(ilad.getLad_id());
	        resultMap.put("listLADSubItem", listLADSubItem);
	        
	        }
			
			List list2=ladProxy.getLAD(limitProfileID,"REC");
			if(list2!=null){
				for(int i=0;i<list2.size();i++){
				ILAD ilad=(ILAD)list2.get(i);
				if(ilad.getId()==id){
				List listLADItem=ladProxy.getLADItem(ilad.getLad_id());
				ILADItem[] items=new OBLADItem[listLADItem.size()];
				if(listLADItem!=null){
					for(int j=0;j<listLADItem.size();j++){
						items[j]=(ILADItem) listLADItem.get(j);
						List listLADSubItem=ladProxy.getLADSubItem(items[j].getDoc_item_id());
						ILADSubItem[] itemSubItems=new OBLADSubItem[listLADSubItem.size()];
						if(listLADSubItem!=null){
							for(int k=0;k<listLADSubItem.size();k++){
								itemSubItems[k]=(ILADSubItem) listLADSubItem.get(k);
							}
					}
						items[j].setIladSubItem(itemSubItems);
				}
			}
	        ilad.setIladItem(items);
	        
	        ladList.add(ilad);
	       // List listLADSubItem=iladDao.getLADSubItemById(id);
	       
			}
	      
				}
	        
	        }*/
			resultMap.put("lad", ilad);
			resultMap.put("ladId", ladId);
			 resultMap.put("listLADSubItem", listLADSubItem);
			resultMap.put("checklistId", checklistId);
			resultMap.put("custTypeTrxID", map.get("custTypeTrxID"));
		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
 		DefaultLogger.debug(this, "Going out of doExecute()");
 		 
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
