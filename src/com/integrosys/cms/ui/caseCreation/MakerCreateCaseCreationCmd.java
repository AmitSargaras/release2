/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.caseCreation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseCreation.bus.ICaseCreation;
import com.integrosys.cms.app.caseCreation.bus.ICaseCreationDao;
import com.integrosys.cms.app.caseCreation.bus.ICaseCreationRemark;
import com.integrosys.cms.app.caseCreation.bus.OBCaseCreation;
import com.integrosys.cms.app.caseCreation.bus.OBCaseCreationRemark;
import com.integrosys.cms.app.caseCreationUpdate.proxy.ICaseCreationProxyManager;
import com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.holiday.bus.OBHoliday;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.proxy.IHolidayProxyManager;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.holiday.trx.OBHolidayTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.component.common.transaction.ICompTrxResult;
import com.integrosys.component.user.app.bus.OBCommonUser;

/**
@author $Author: Abhijit R$
* Command for Create Holiday
 */
public class MakerCreateCaseCreationCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private IHolidayProxyManager holidayProxy;

	public IHolidayProxyManager getHolidayProxy() {
		return holidayProxy;
	}

	public void setHolidayProxy(IHolidayProxyManager holidayProxy) {
		this.holidayProxy = holidayProxy;
	}
	
	
	/**
	 * Default Constructor
	 */
	
	
	public MakerCreateCaseCreationCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	 public String[][] getParameterDescriptor() {
	        return (new String[][]{
	        		{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{IGlobalConstant.GLOBAL_LOS_USER, "com.integrosys.component.user.app.bus.ICommonUser",GLOBAL_SCOPE},
				{CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String",GLOBAL_SCOPE},
	                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	                { "hiddenItemID", "java.lang.String", REQUEST_SCOPE },
	                {"event", "java.lang.String", REQUEST_SCOPE},
	                {"branchCode", "java.lang.String", REQUEST_SCOPE},
	                {"remark", "java.lang.String", REQUEST_SCOPE},
	                {"receivedList", "java.util.ArrayList", SERVICE_SCOPE},
	        		{ "caseCreationObj", "com.integrosys.cms.app.caseCreation.bus.OBCaseCreation", FORM_SCOPE }
	               
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] { 
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
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
			HashMap exceptionMap = new HashMap();
			String errorCode = "";
			boolean initCase=false;
			try {
				String teamTypeMemID = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);
				boolean isError=false;
				   ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
					long limitProfileID = limit.getLimitProfileID();
					ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
					String remarks=(String)map.get("remark");
					String branchCode=(String)map.get("branchCode");
					  OBCommonUser globalUser = (OBCommonUser) map.get(IGlobalConstant.GLOBAL_LOS_USER);	
				OBCaseCreation caseCreation = (OBCaseCreation) map.get("caseCreationObj");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				ICaseCreationDao caseCreationDao=(ICaseCreationDao)BeanHouse.get("caseCreationDao");
				  IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
	                IGeneralParamEntry generalParamEntries= generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
	    			Date  applicationDate= new Date(generalParamEntries.getParamValue());
	    			
				SearchResult searchResultCaseCreation=caseCreationDao.listCaseCreation("actualCaseCreation", limitProfileID);
				ArrayList caseCreationList = (ArrayList)searchResultCaseCreation.getResultList();
				HashMap hmCaseCreationId = new HashMap();
				for(int k=0;k<caseCreationList.size();k++){
					ICaseCreation caseCreation2=(ICaseCreation)caseCreationList.get(k);
					hmCaseCreationId.put(String.valueOf(caseCreation2.getChecklistitemid()),String.valueOf(caseCreation2.getChecklistitemid()));
					
				}
				String hiddenItemID = (String) map.get("hiddenItemID");
				StringTokenizer st = new StringTokenizer(hiddenItemID, ",");
				HashMap hm = new HashMap();
				while (st.hasMoreTokens()) {
					String key = st.nextToken();
					hm.put(key, key);
				}
				ArrayList list = (ArrayList) map.get("receivedList");
				Iterator itr = list.iterator();

				ArrayList itemList = new ArrayList();
				/*while (itr.hasNext()) {
					ICheckListItem item = (OBCheckListItem) itr.next();
					
					if (hm.containsKey(String.valueOf(item.getCheckListItemID()))) {
						if(!(hmCaseCreationId.containsKey(String.valueOf(item.getCheckListItemID())))){
							if(!("1".equals(caseCreation.getStatus()))){
								//isError=true;
								exceptionMap.put("caseCreationError", new ActionMessage("error.string.caseCreationRequest"));
								returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
								returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
								return returnMap;
							}
						}
						
						//itemList.add(item);
					}
				}*/
				/*if(!(remarks.trim().equals("")))
				{
				if (!(errorCode = Validator.checkString(remarks, false, 1, 200))
						.equals(Validator.ERROR_NONE)) {
					isError=true;
					exceptionMap.put("remarkError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							"200"));
				}
				}*/
			/*	if(isError){
					if(("1".equals(caseCreation.getStatus()))){
						//exceptionMap.put("caseCreationError", new ActionMessage("error.string.caseCreationRequest"));
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						return returnMap;
						
					}
				}else{*/
					Iterator itrOld = list.iterator();
				while (itrOld.hasNext()) {
					ICheckListItem item = (OBCheckListItem) itrOld.next();
					
					if (hm.containsKey(String.valueOf(item.getCheckListItemID()))) {
						
						initCase=true;
					
					}
				}
				
				ICaseCreationTrxValue caseCreationTrxValue=null;
				
				if(initCase){
					com.integrosys.cms.app.caseCreationUpdate.bus.OBCaseCreation obCaseCreation= new com.integrosys.cms.app.caseCreationUpdate.bus.OBCaseCreation();
					obCaseCreation.setLimitProfileId(limitProfileID);
					obCaseCreation.setCreationDate(applicationDate);
					obCaseCreation.setVersionTime(0L);
					obCaseCreation.setCurrRemarks(remarks);
					obCaseCreation.setPrevRemarks(remarks);
					obCaseCreation.setStatus("OPEN");
					obCaseCreation.setBranchCode(branchCode);
					ICaseCreationProxyManager caseCreationProxyManager = (ICaseCreationProxyManager) BeanHouse.get("caseCreationProxy");
					caseCreationTrxValue=  caseCreationProxyManager.makerCreateCaseCreation(ctx, obCaseCreation);
					resultMap.put("request.ITrxValue", caseCreationTrxValue);
				}
				
				
				Iterator itrNew = list.iterator();
				while (itrNew.hasNext()) {
					ICheckListItem item = (OBCheckListItem) itrNew.next();
					
					if (hm.containsKey(String.valueOf(item.getCheckListItemID()))) {
						
					
					
						if(caseCreationTrxValue!=null){
							if(caseCreationTrxValue.getStagingCaseCreation()!=null){
							ICaseCreation creation =new OBCaseCreation();
							creation.setChecklistitemid(item.getCheckListItemID());
							creation.setRemark(remarks);
							creation.setCaseDate(applicationDate);
							creation.setStatus("1");
							creation.setLimitProfileId(limitProfileID);
							creation.setCasecreationid(caseCreationTrxValue.getStagingCaseCreation().getId());
							creation.setRequestedDate(applicationDate);
							caseCreationDao.createCaseCreation("stageCaseCreation", creation);
							
						}
					}
						//itemList.add(item);
						
						
						
						//com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreationDao iCaseCreationDao = (com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreationDao) BeanHouse.get("caseCreationUpdateDao");
					}
				}

				//IItem[] tempAry = (IItem[]) itemList.toArray(new IItem[itemList.size()]);
				
			/*	
				ICaseCreationRemark remark= new OBCaseCreationRemark();
				remark.setLimitProfileId(limitProfileID);
				remark.setRemark(remarks);
				remark.setUpdatedDate(new Date());
				remark.setUserId(globalUser.getUserID());
				remark.setUserRole(teamTypeMemID);
				remark.setUserTeam(teamTypeMemID);
				caseCreationDao.createCaseCreationRemark("actualCaseCreationRemark", remark);
				
			*/		
			//	}
				
				
			}
			catch (TransactionException e) {
				DefaultLogger.debug(this, "got exception in doExecute" + e);
				throw (new CommandProcessingException(e.getMessage()));
			}catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
	    }
	    

}
