package com.integrosys.cms.ui.caseCreationUpdate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationException;
import com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreationJdbc;
import com.integrosys.cms.app.caseCreationUpdate.bus.OBCaseCreation;
import com.integrosys.cms.app.caseCreationUpdate.proxy.ICaseCreationProxyManager;
import com.integrosys.cms.app.customer.bus.CustomerDAO;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 $Author: Abhijit R $
 Command for list CaseCreation
 */
public class ListCaseCreationCPUTMenuCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	
	private ICaseCreationProxyManager caseCreationProxy;

	public ICaseCreationProxyManager getCaseCreationProxy() {
		return caseCreationProxy;
	}

	public void setCaseCreationProxy(ICaseCreationProxyManager caseCreationProxy) {
		this.caseCreationProxy = caseCreationProxy;
	}

	public ListCaseCreationCPUTMenuCmd() {
		
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "partyName", "java.lang.String", REQUEST_SCOPE },
				{ "caseId", "java.lang.String", REQUEST_SCOPE },
				{ "segment", "java.lang.String", REQUEST_SCOPE },
				{ "region", "java.lang.String", REQUEST_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
				{ "txtAllParty", "java.lang.String", REQUEST_SCOPE },
				{"caseCreationUpdateList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
				{"customerMap", "java.util.HashMap", SERVICE_SCOPE},
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
					GLOBAL_SCOPE },
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				
				
			};
	}
	   public String[][] getResultDescriptor() {
	        return (new String[][]{
	                {"caseCreationUpdateList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
					{ "event", "java.lang.String", REQUEST_SCOPE },
					{"customerMap", "java.util.HashMap", SERVICE_SCOPE},
					{ "startIndex", "java.lang.String", REQUEST_SCOPE },
					{ "regionList", "java.util.List", SERVICE_SCOPE },
					{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
	                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
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
	        String startIndex = (String) map.get("startIndex");
	        int stindex = 0;
	        System.out.println("Inside ListCaseCreationCPUTMenuCmd.java");
	       // ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			//long limitProfileID = limit.getLimitProfileID();
			// DefaultLogger.debug(this,"Limit profile "+limit);
			//ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			//OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
	        String event = (String) map.get("event");
	        String partyName = (String) map.get("partyName");
	        String caseId = (String) map.get("caseId");
	        String segment = (String) map.get("segment");
	        ICustomerProxy customerProxy = CustomerProxyFactory.getProxy();
	        String region = (String) map.get("region");
	        String status = (String) map.get("status");
	        String txtAllParty = (String) map.get("txtAllParty");
	        ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
	        ICaseCreationJdbc caseCreationJdbc= (ICaseCreationJdbc) BeanHouse.get("caseCreationUpdateJdbc");
	        try {
	        	String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
	        	
	        	
	        	if(event !=null && event.equalsIgnoreCase("search_case_creation_update_paginate")){
	        		System.out.println("First If ListCaseCreationCPUTMenuCmd.java");
	        		HashMap customerMap= (HashMap) map.get("customerMap");
	        		SearchResult caseCreationUpdateList =(SearchResult)  map.get("caseCreationUpdateList");
	        		if(caseCreationUpdateList == null) {
	        			caseCreationUpdateList= (SearchResult)  getCaseCreationProxy().getAllActualCaseCreation();
	        		}
	        		 Collection caseCreationUpdateListCollection= caseCreationUpdateList.getResultList();
			           
			           Iterator iteratorCaseCreationUpdateListCollection=caseCreationUpdateListCollection.iterator();
			           while(iteratorCaseCreationUpdateListCollection.hasNext()){
			        	   
			        	   OBCaseCreation caseCreation=(OBCaseCreation)iteratorCaseCreationUpdateListCollection.next();
			        	   ICMSCustomer customer=  new CustomerDAO().getCustomerByLimitProfileIdForCaseCreation(caseCreation.getLimitProfileId());
			        	   customerMap.put(String.valueOf(caseCreation.getId()), customer);
			           }
		            if (StringUtils.isBlank(startIndex)) {
						if (StringUtils.isBlank(globalStartIndex) || "null".equals(globalStartIndex.trim())) {
							stindex = 0;
							startIndex = String.valueOf(stindex);
							resultMap.put("startIndex", startIndex);

						}
						else {
							stindex = Integer.parseInt(globalStartIndex);
							startIndex = globalStartIndex;
							resultMap.put("startIndex", startIndex);
						}
					}
					else {
						stindex = Integer.parseInt(startIndex);
						resultMap.put("startIndex", startIndex);
					}

		                  resultMap.put("caseCreationUpdateList", caseCreationUpdateList);
		                  resultMap.put("customerMap", customerMap);
		                  resultMap.put("event", event);
	        	}else{
	        		System.out.println("First else ListCaseCreationCPUTMenuCmd.java");
	        		SearchResult caseCreationUpdateList = new SearchResult();
	        		if(event!=null && event.equalsIgnoreCase("search_case_creation_update")){
		        		if("true".equals(txtAllParty)) {
		        			partyName = "";
		        			caseCreationUpdateList= (SearchResult)  caseCreationJdbc.getAllCaseCreationCPUTMenuSearch(partyName, caseId, region, segment, status);
		        		}else {
		        			caseCreationUpdateList= (SearchResult)  caseCreationJdbc.getAllCaseCreationCPUTMenuSearch(partyName, caseId, region, segment, status);
		        		}
		        	}
	        		else if("maker_list_casecreation_cput_menu".equals(event)) {
	        			
	        		}
	        		else{
		            caseCreationUpdateList= (SearchResult)  getCaseCreationProxy().getAllActualCaseCreation();
		        	}
		            
		            
		            
		            //ICustomerProxy customerProxy = CustomerProxyFactory.getProxy();
		            
	        		long limitProfileId = 0;
	        		int count = 0;
	        		ICMSCustomer customer = null;
	        		HashMap customerMap= new HashMap();
	        		if(!"maker_list_casecreation_cput_menu".equals(event)) {
			           Collection caseCreationUpdateListCollection= caseCreationUpdateList.getResultList();
			           
			           Iterator iteratorCaseCreationUpdateListCollection=caseCreationUpdateListCollection.iterator();
			           
			           System.out.println("Going for while in after iteratorCaseCreationUpdateListCollection");
			           while(iteratorCaseCreationUpdateListCollection.hasNext()){
			        	   
			        	   /*OBCaseCreation caseCreation=(OBCaseCreation)iteratorCaseCreationUpdateListCollection.next();
			        	   ICMSCustomer customer= new CustomerDAO().getCustomerByLimitProfileIdForCaseCreation(caseCreation.getLimitProfileId());
			        	   customerMap.put(String.valueOf(caseCreation.getId()), customer);*/
			        	  
			        	   OBCaseCreation caseCreation=(OBCaseCreation)iteratorCaseCreationUpdateListCollection.next();
			        	  if(limitProfileId == caseCreation.getLimitProfileId()) {
			        		  customerMap.put(String.valueOf(caseCreation.getId()), customer);
			        	  }else {
			        		  customer= new CustomerDAO().getCustomerByLimitProfileIdForCaseCreation(caseCreation.getLimitProfileId());
				        	   customerMap.put(String.valueOf(caseCreation.getId()), customer);
			        	  }
			        	   
			        	   limitProfileId = caseCreation.getLimitProfileId();
			        	   count++;
			           }
	        		}
			           System.out.println("Going for while in after iteratorCaseCreationUpdateListCollection=> Complete=>count=>"+count);
		            if (StringUtils.isBlank(startIndex)) {
						if (StringUtils.isBlank(globalStartIndex) || "null".equals(globalStartIndex.trim())) {
							stindex = 0;
							startIndex = String.valueOf(stindex);
							resultMap.put("startIndex", startIndex);

						}
						else {
							stindex = Integer.parseInt(globalStartIndex);
							startIndex = globalStartIndex;
							resultMap.put("startIndex", startIndex);
						}
					}
					else {
						stindex = Integer.parseInt(startIndex);
						resultMap.put("startIndex", startIndex);
					}

		                  resultMap.put("caseCreationUpdateList", caseCreationUpdateList);
		                  resultMap.put("customerMap", customerMap);
		                  resultMap.put("event", event);
	        	}
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	resultMap.put("regionList", getRegionList());
	        	 resultMap.put("selectedArrayMap", new HashMap());
	        }catch (CaseCreationException ex) {
	        	System.out.println("ListCaseCreationCPUTMenuCmd.java=>CaseCreationException got exception in doExecute" + ex);
	        	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
		            ex.printStackTrace();
		            throw (new CommandProcessingException(ex.getMessage()));
			} catch (Exception e) {
				System.out.println("ListCaseCreationCPUTMenuCmd.java=>Exception got exception in doExecute" + e);
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
	        System.out.println("Done in ListCaseCreationCPUTMenuCmd.java");
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	    }
	    
	    private List getRegionList() {
			List lbValList = new ArrayList();
			try {
				
				IRegionDAO regionDAO = (IRegionDAO) BeanHouse.get("regionDAO");
				Collection idList = (regionDAO.listRegion("", "")).getResultList();			
				Iterator itr =idList.iterator();
				while(itr.hasNext()){
					IRegion region = (IRegion)itr.next();
						String id = Long.toString(region.getIdRegion());
						String val = region.getRegionName();
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return CommonUtil.sortDropdown(lbValList);
		}	    
	    
	    
}