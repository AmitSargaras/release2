package com.integrosys.cms.ui.newTat;

/**
 *@author abhijit.rudrakshawar
 *$ Command for Listing Customer
 */

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameterValue.DateFormat;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseBranch.bus.OBCaseBranch;
import com.integrosys.cms.app.caseBranch.proxy.ICaseBranchProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMasterJdbc;
import com.integrosys.cms.app.facilityNewMaster.bus.OBFacilityNewMaster;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.LimitListSummaryItemBase;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.newTat.bus.INewTat;
import com.integrosys.cms.app.newTat.bus.INewTatDAO;
import com.integrosys.cms.app.newTat.bus.INewTatJdbc;
import com.integrosys.cms.app.newTat.bus.OBNewTat;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

public class PrepareValuesCommand extends AbstractCommand {

	public PrepareValuesCommand() {

	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "id", "java.lang.String", REQUEST_SCOPE }, 
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "startIndexInner", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "lspLeIdListSearch", "java.lang.String", REQUEST_SCOPE }, 
				{ "lspShortNameListSearch", "java.lang.String", REQUEST_SCOPE }, 
				 });
	}

	  private JdbcTemplate jdbcTemplate;
	    

		public JdbcTemplate getJdbcTemplate() {
			return jdbcTemplate;
		}

		public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}
	
	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	
	 private static final String DATASOURCE_JNDI_KEY = "dbconfig.weblogic.datasource.jndiname";
	
	public String[][] getResultDescriptor() {
		return (new String[][] {
				 {"regionMap", "java.util.Map",SERVICE_SCOPE},
				 { "newTatObj", "com.integrosys.cms.app.newTat.bus.OBNewTat", FORM_SCOPE },
				 { "newTatObj.session", "com.integrosys.cms.app.newTat.bus.OBNewTat", SERVICE_SCOPE },
				 { "branchList", "java.util.List", REQUEST_SCOPE },
				 { "startIndex", "java.lang.String", REQUEST_SCOPE },
				 { "startIndexInner", "java.lang.String", REQUEST_SCOPE },
				 { "branchList", "java.util.List", SERVICE_SCOPE },
				 { "facilityNameList", "java.util.List", SERVICE_SCOPE },
				 { "relationshipMgrList", "java.util.List", SERVICE_SCOPE },
				 { "event", "java.lang.String", REQUEST_SCOPE }, 
				 {"rmMap", "java.util.Map",SERVICE_SCOPE},
				 { "currencyList", "java.util.List", SERVICE_SCOPE },
				 { "manualFacilityList", "java.util.List", SERVICE_SCOPE },
				 { "systemFacilityList", "java.util.List", SERVICE_SCOPE },
				 { "branchListValues", "java.util.List", SERVICE_SCOPE },
				 { IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				 { "facilityNameMap", "java.util.List", SERVICE_SCOPE },
				 { "newFacilityNameMap", "java.util.Map", SERVICE_SCOPE },
				 { "tatBurst", "java.lang.String", SERVICE_SCOPE },
				 { "lspLeIdListSearch", "java.lang.String", REQUEST_SCOPE }, 
				 { "lspShortNameListSearch", "java.lang.String", REQUEST_SCOPE }, 
				
		});
	}
	
private IRelationshipMgrProxyManager relationshipMgrProxyManager;


	public IRelationshipMgrProxyManager getRelationshipMgrProxyManager() {
		return relationshipMgrProxyManager;
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *             on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String id = (String) map.get("id");
		String event = (String) map.get("event");
		 String startIndex = (String) map.get("startIndex");
		 String startIndexInner = (String) map.get("startIndexInner");
		 String lspLeIdListSearch = (String) map.get("lspLeIdListSearch");
		 String lspShortNameListSearch = (String) map.get("lspShortNameListSearch");
		 
		 INewTatDAO newTat = (INewTatDAO)BeanHouse.get("newTatDao");
		ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		if(cust!=null){
		if(cust.getStatus()!=null && "INACTIVE".equalsIgnoreCase(cust.getStatus() ) && !"list".equalsIgnoreCase(event))
		{
			result.put("party_closed", "party_closed"); 
		}
		}
		HashMap systemFacilityNameMap = new HashMap();
		OBNewTat tat = new OBNewTat();
		String tatBurst="";
		
	     if(id!=null && !"".equals(id)){
		 tat = (OBNewTat)newTat.load("actualNewTAT", Long.parseLong(id));
		 if(tat!=null){
		 if(event!=null && "prepare_submit_documents".equals(event)) {
			 if(tat.getStatus()!=null && ("DOCUMENT_SUBMITTED".equals(tat.getStatus()) ||  "DEFERRAL_APPROVED".equals(tat.getStatus()))){
				 result.put("wip", "wip"); 
			 }
		 }
		 else if(event!=null && "prepare_deferral_approve".equals(event)){
			 if(tat.getStatus()!=null && ("DOCUMENT_SUBMITTED".equals(tat.getStatus()) ||  "DEFERRAL_APPROVED".equals(tat.getStatus()))){
				 result.put("wip", "wip"); 
			 }
		 }
		 else if(event!=null && "prepare_deferral_clearance".equals(event)){
			 if(tat.getStatus()!=null && ("DOCUMENT_SUBMITTED".equals(tat.getStatus()) ||  "DEFERRAL_APPROVED".equals(tat.getStatus()))){
				 result.put("wip", "wip"); 
			 }
		 }
		 else if(event!=null && "prepare_document_receive".equals(event)){
			 if(tat.getStatus()!=null && ("DOCUMENT_RECEIVED".equals(tat.getStatus()) ||  "CLOSED".equals(tat.getStatus()))){
				 result.put("wip", "wip"); 
			 }
		 }
		 else if(event!=null && "prepare_document_scan".equals(event)){
			 if(tat.getStatus()!=null && ("DOCUMENT_SCANNED".equals(tat.getStatus()))){
				 result.put("wip", "wip"); 
			 }
		 }
		 else if(event!=null && "prepare_limit_release".equals(event)){
			 if(tat.getStatus()!=null && ("LIMIT_RELEASED".equals(tat.getStatus()) ||  "DEFERRAL_RAISED".equals(tat.getStatus()))){
				 result.put("wip", "wip"); 
			 }
		 }
		 else if(event!=null && "prepare_deferral_raised".equals(event)){
			 if(tat.getStatus()!=null && ("LIMIT_RELEASED".equals(tat.getStatus()) ||  "DEFERRAL_RAISED".equals(tat.getStatus()))){
				 result.put("wip", "wip"); 
			 }
		 }
		 else if(event!=null && "prepare_clims_updated".equals(event)){
			 if(tat.getStatus()!=null && ("CLIMS_UPDATED".equals(tat.getStatus()))){
				 result.put("wip", "wip"); 
			 }
		 }
		 }
		if(tat!=null){
				MILimitUIHelper helper = new MILimitUIHelper();
				SBMILmtProxy proxy = helper.getSBMILmtProxy();
				try {
					List lmtList = proxy.getLimitSummaryListByCustID(String.valueOf(tat.getLspLeId()));
					if(lmtList!=null){
					for (int i = 0; i < lmtList.size(); i++) {
						LimitListSummaryItemBase itemBase= (LimitListSummaryItemBase) lmtList.get(i); 
						if(itemBase!=null){
							if(itemBase.getProdTypeCode()!=null && !itemBase.getProdTypeCode().trim().equals("")){
							systemFacilityNameMap.put(itemBase.getProdTypeCode().trim().toUpperCase(), itemBase.getProdTypeCode().trim().toUpperCase());
							}
						}
					}
					}
				} catch (LimitException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				IGeneralParamDao generalParam=(IGeneralParamDao) BeanHouse.get("generalParamDao");
				IGeneralParamEntry generalParamEntry2 = generalParam.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
				java.util.Date date = new java.util.Date(generalParamEntry2.getParamValue());
//				Calendar currDate = Calendar.getInstance();
//				currDate.setTime(date);
				java.util.Date d = DateUtil.getDate();
				date.setHours(d.getHours());
				date.setMinutes(d.getMinutes());
				date.setSeconds(d.getSeconds());
				 SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				
			 tatBurst = NewTatUtil.getTatBurst(tat.getModule());
			 long lTatBurst= Long.parseLong(tatBurst);
			 
			 INewTatJdbc newTatJdbcImpl = (INewTatJdbc)BeanHouse.get("newTatJdbc");
			 //INewTat iNewTatS= newTatJdbcImpl.getTatDetail(String.valueOf(tat.getCaseId()),"DOCUMENT_SUBMITTED");
			 java.util.Date startDate =null;
			 INewTat iNewTatFirst= newTatJdbcImpl.getFirstSubmitTime(String.valueOf(tat.getCaseId()),"DOCUMENT_SUBMITTED");
			 if(iNewTatFirst != null){
			 if(date.after(iNewTatFirst.getActivityTime())){
				
				 String str =  newTatJdbcImpl.getDifferenceInMin(df.format(date).toString(),date.getHours()+":"+date.getMinutes(),df.format(iNewTatFirst.getActivityTime()).toString(),iNewTatFirst.getActivityTime().getHours()+":"+iNewTatFirst.getActivityTime().getMinutes(), tat.getModule(),tat.getCaseId());
			int hr = 0;
			int min = 0;
				 String segments[] = str.split(":");
				 if(segments.length>0)
				  hr = Integer.parseInt(segments[0])*60;
				 if(segments.length>0)
				  min = Integer.parseInt(segments[1]);
				 
				 long time = hr + min;
				 
				 System.out.println("***** Processed Date *****"+df.format(date).toString());
				 System.out.println("***** Received Date *****"+df.format(iNewTatFirst.getActivityTime()).toString());
				 System.out.println("***** Processed Time *****"+ date.getHours()+":"+date.getMinutes());
				 System.out.println("***** Received Time *****"+ iNewTatFirst.getActivityTime().getHours()+":"+iNewTatFirst.getActivityTime().getMinutes());
				 System.out.println("***** module *****"+tat.getModule());
				 System.out.println("***** time difference in min *****"+time);
				 
			// long datediff = com.integrosys.cms.app.common.util.CommonUtil.dateDiff(iNewTatFirst.getActivityTime(), date, 12);
			 
				 if(time >lTatBurst){
					 tat.setIsTatBurst("Y");
				 }
			 }
			 } 
				 result.put("lspLeIdListSearch", lspLeIdListSearch);
				 result.put("lspShortNameListSearch", lspShortNameListSearch);
				 
			 result.put("newTatObj", tat);
			 result.put("newTatObj.session", tat);
			}
		}
		
		if(event!=null && !"".equals(event))
		{
			result.put("event", event);
		}
		HashMap facilityNameMap= new HashMap();	
		
		SearchResult facilityNewMasterList = new SearchResult();
    	IFacilityNewMasterJdbc facMaster = (IFacilityNewMasterJdbc)BeanHouse.get("facilityNewMasterJdbc");
    	List facilityNameList=null;
    	facilityNewMasterList= (SearchResult) facMaster.getFilteredActualFacilityNewMaster("", "", tat.getFacilityCategory(), "", "", "");
		List lbValList = new ArrayList();
		   	 if(facilityNewMasterList!=null){
		   		 facilityNameList = new ArrayList(facilityNewMasterList.getResultList());
		   		 for (int i = 0; i < facilityNameList.size(); i++) {
							IFacilityNewMaster fac = (IFacilityNewMaster)facilityNameList.get(i);
							if( fac.getStatus().equals("ACTIVE")) {
								String idVal = Long.toString(fac.getId());
								String val = fac.getNewFacilityName();
								LabelValueBean lvBean = new LabelValueBean(val, idVal);
								lbValList.add(lvBean);
								facilityNameMap.put(idVal, val);
							}
						}
				}
		   	 
		 
		result.put(IGlobalConstant.GLOBAL_CUSTOMER_OBJ,cust);
   	 	result.put("facilityNameList",  CommonUtil.sortDropdown(lbValList));
   	 	result.put("facilityNameMap",  facilityNameMap);
		result.put("branchList", getBranchList());
		result.put("branchListValues", getBranchListValues());
		result.put("startIndex", startIndex);
		result.put("startIndexInner", startIndexInner);
		result.put("regionMap", getRegionMap());
		result.put("currencyList", getCurrencyList());
		result.put("manualFacilityList", getManualFacilityList());
		result.put("systemFacilityList", getSystemFacilityList(systemFacilityNameMap));
		result.put("relationshipMgrList", getNewRelationshipMgrList(""));
		result.put("rmMap", getRelationshipManagerMap());
		result.put("tatBurst",tatBurst);
		HashMap newFacilityNameMap= new HashMap();		
		List manualFacilityList =getManualFacilityList();
		for (int a = 0; a < manualFacilityList.size(); a++) {
			LabelValueBean bean =(LabelValueBean)manualFacilityList.get(a);
			newFacilityNameMap.put(bean.getValue(),bean.getLabel() );
		}
		result.put("newFacilityNameMap", newFacilityNameMap);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	

	private HashMap getRegionMap() {
		 HashMap lbValmap = new HashMap();
			try {
				
				IRegionDAO regionDAO = (IRegionDAO) BeanHouse.get("regionDAO");
				Collection idList = (regionDAO.listRegion("Country", "india")).getResultList();			
				Iterator itr =idList.iterator();
				while(itr.hasNext()){
					IRegion region = (IRegion)itr.next();
						String id = Long.toString(region.getIdRegion());
						String val = region.getRegionName();
						lbValmap.put(id, val);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return lbValmap;
		}
	 
		private List getBranchList() {
			List lbValList = new ArrayList();
			try {
				HashMap sysBranch=new HashMap();
				ISystemBankBranchProxyManager systemBankBranchProxy =(ISystemBankBranchProxyManager) BeanHouse.get("systemBankBranchProxy");
				 SearchResult searchResult = systemBankBranchProxy.getAllActualBranch();
				 List idList = (List) searchResult.getResultList();
				for (int i = 0; i < idList.size(); i++) {
					ISystemBankBranch bankBranch = (ISystemBankBranch) idList.get(i);
					if(bankBranch.getStatus().equalsIgnoreCase("ACTIVE")){
					String id = bankBranch.getSystemBankBranchCode();
					String val = bankBranch.getSystemBankBranchName();
					String value=id+" ("+val + ")";
					sysBranch.put(id, value);
					}
				}
				ICaseBranchProxyManager caseBranchProxy =(ICaseBranchProxyManager) BeanHouse.get("caseBranchProxy");
				 SearchResult caseBranchSearchResult= (SearchResult)  caseBranchProxy.getAllActualCaseBranch();
				 List caseBranchList = (List) caseBranchSearchResult.getResultList();
				for (int i = 0; i < caseBranchList.size(); i++) {
					OBCaseBranch caseBranch=(OBCaseBranch)caseBranchList.get(i);
					if(caseBranch.getStatus().equalsIgnoreCase("ACTIVE")){
					String id = caseBranch.getBranchCode();
					String value=(String) sysBranch.get(caseBranch.getBranchCode());
					LabelValueBean lvBean = new LabelValueBean(value, id);
					
					lbValList.add(lvBean);
					}
				}
			} catch (Exception ex) {
			}
			return (List) CommonUtil.sortDropdown(lbValList);
		}
	 
	 private HashMap getRelationshipManagerMap() {
		 HashMap lbValmap = new HashMap();
			try {
				
				IRelationshipMgrDAO relationshipMgrDAO = (IRelationshipMgrDAO) BeanHouse.get("relationshipMgrDAO");
				Collection idList = relationshipMgrDAO.getRelationshipMgrList("").getResultList();			
				Iterator itr =idList.iterator();
				while(itr.hasNext()){
					IRelationshipMgr relationshipMgr = (IRelationshipMgr)itr.next();
						String id = Long.toString(relationshipMgr.getId());
						String val = relationshipMgr.getRelationshipMgrName();
						lbValmap.put(id, val);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return lbValmap;
		}
	 
	 private List getCurrencyList() {
			List lbValList = new ArrayList();
			try {
					
					IForexFeedEntry[] currency = CollateralDAOFactory.getDAO().getCurrencyList();
					
					if (currency != null) {
						for (int i = 0; i < currency.length; i++) {
							IForexFeedEntry lst = currency[i];
//							String id = lst.getCurrencyIsoCode().trim();
							String id = lst.getBuyCurrency().trim();
							String value = lst.getCurrencyIsoCode().trim();
							LabelValueBean lvBean = new LabelValueBean(value, id);
							lbValList.add(lvBean);
						}
					}
			}
			catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}
	 
	 private List getManualFacilityList() {
			List lbValList = new ArrayList();
			try {
				IFacilityNewMasterJdbc facilityNewMasterJdbc =(IFacilityNewMasterJdbc )BeanHouse.get("facilityNewMasterJdbc");
					SearchResult facilitySearch= facilityNewMasterJdbc.getAllFacilityNewMaster();
					Collection facilityList= facilitySearch.getResultList();
					if (facilityList != null) {
						Iterator iterator =facilityList.iterator();
						while (iterator.hasNext()){
							OBFacilityNewMaster obFacilityNewMaster = (OBFacilityNewMaster)iterator.next(); 
							if(obFacilityNewMaster.getStatus().equals("ACTIVE")){
							String id = obFacilityNewMaster.getNewFacilityCode();
							String value = obFacilityNewMaster.getNewFacilityName().trim();
							LabelValueBean lvBean = new LabelValueBean(value, id);
							lbValList.add(lvBean);
							}
						}
						
					}
			}
			catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}
	 	 
		private HashMap getBranchListValues() {
			HashMap lbValList = new HashMap();
			try {
				HashMap sysBranch=new HashMap();
				ISystemBankBranchProxyManager systemBankBranchProxy =(ISystemBankBranchProxyManager) BeanHouse.get("systemBankBranchProxy");
				 SearchResult searchResult = systemBankBranchProxy.getAllActualBranch();
				 List idList = (List) searchResult.getResultList();
				for (int i = 0; i < idList.size(); i++) {
					ISystemBankBranch bankBranch = (ISystemBankBranch) idList.get(i);
					String id = bankBranch.getSystemBankBranchCode();
					String val = bankBranch.getSystemBankBranchName();
					String value=id+" ("+val + ")";
					sysBranch.put(id, value);
				}
				ICaseBranchProxyManager caseBranchProxy =(ICaseBranchProxyManager) BeanHouse.get("caseBranchProxy");
				 SearchResult caseBranchSearchResult= (SearchResult)  caseBranchProxy.getAllActualCaseBranch();
				 List caseBranchList = (List) caseBranchSearchResult.getResultList();
				for (int i = 0; i < caseBranchList.size(); i++) {
					OBCaseBranch caseBranch=(OBCaseBranch)caseBranchList.get(i);
					String id = caseBranch.getBranchCode();
					String value=(String) sysBranch.get(caseBranch.getBranchCode());
					lbValList.put(id, value);
				}
			} catch (Exception ex) {
			}
			return lbValList;
		}
		private List getNewRelationshipMgrList(String rmRegion) {
			List lbValList = new ArrayList();
			List idList = new ArrayList();
			try {
				
				IRelationshipMgrDAO relationshipMgrDAO = (IRelationshipMgrDAO) BeanHouse.get("relationshipMgrDAO");
				SearchResult idListsr = (SearchResult) relationshipMgrDAO.getRelationshipMgrList(rmRegion);

				if (idListsr != null) {
					  idList = new ArrayList(idListsr.getResultList());
				}
				
				for (int i = 0; i < idList.size(); i++) {
					IRelationshipMgr mgr = (IRelationshipMgr) idList.get(i);
					if (mgr.getStatus().equals("ACTIVE")) {
						String id = Long.toString(mgr.getId());
						String val = mgr.getRelationshipMgrName();
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
					}
				}
			} catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}
		private List getSystemFacilityList(HashMap systemFacilityNameMap) {
			List lbValList = new ArrayList();
			try {
				IFacilityNewMasterJdbc facilityNewMasterJdbc =(IFacilityNewMasterJdbc )BeanHouse.get("facilityNewMasterJdbc");
					SearchResult facilitySearch= facilityNewMasterJdbc.getAllFacilityNewMaster();
					Collection facilityList= facilitySearch.getResultList();
					if (facilityList != null) {
						Iterator iterator =facilityList.iterator();
						while (iterator.hasNext()){
							OBFacilityNewMaster obFacilityNewMaster = (OBFacilityNewMaster)iterator.next(); 
							if(systemFacilityNameMap.containsKey(obFacilityNewMaster.getNewFacilityName().trim().toUpperCase())){
								if(obFacilityNewMaster.getStatus().equals("ACTIVE")){
							String id = obFacilityNewMaster.getNewFacilityCode();
							String value = obFacilityNewMaster.getNewFacilityName().trim();
							LabelValueBean lvBean = new LabelValueBean(value, id);
							lbValList.add(lvBean);
								}
							}
						}
						
					}
			}
			catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}
		
		
}