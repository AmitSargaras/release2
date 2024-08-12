package com.integrosys.cms.ui.genlad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.lad.bus.GeneratePartyLADDao;
import com.integrosys.cms.app.poi.report.IReportService;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.poi.report.ReportFilterTypeList;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;
import com.integrosys.component.login.ui.GlobalSessionConstant;

/**
 * @author cyliew
 * @author Chong Jun Yong
 * @since 2006/10/27
 */
public class LADReportCommand extends AbstractCommand implements
		ICommonEventConstant {

	private IReportService reportService;
	
	private String isRMValue="";
	
	

	public IReportService getReportService() {
		return reportService;
	}

	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
	}

	private ICityProxyManager cityProxy;

	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	private IRelationshipMgrProxyManager relationshipMgrProxyManager;

	public IRelationshipMgrProxyManager getRelationshipMgrProxyManager() {
		return relationshipMgrProxyManager;
	}

	public void setRelationshipMgrProxyManager(
			IRelationshipMgrProxyManager relationshipMgrProxyManager) {
		this.relationshipMgrProxyManager = relationshipMgrProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public LADReportCommand() {

	}

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "reportId", "java.lang.String", REQUEST_SCOPE },
				{ "generateLadFormObj","com.integrosys.cms.ui.genlad.OBFilter",FORM_SCOPE },{"USER_LOGIN_ID", "java.lang.String",GLOBAL_SCOPE }});
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
				{ "reportsFilterType", "java.lang.String", SERVICE_SCOPE },
				{ "dateValue", "java.lang.String", SERVICE_SCOPE },
				{ "dueYear", "java.lang.String", SERVICE_SCOPE },
				{ "isRecordAvailable", "java.lang.String", SERVICE_SCOPE },
				{ "recordAlreadyPresent", "java.lang.String", SERVICE_SCOPE },
				{ "fileName", "java.lang.String", REQUEST_SCOPE },
				{ "departmentList", "java.util.List", SERVICE_SCOPE },
				{ "managerList", "java.util.List", SERVICE_SCOPE },
				{ "resultList", "java.util.List", SERVICE_SCOPE },
				{ "customerObject", "java.util.List", SERVICE_SCOPE },
				{ "selectedUserObject", "java.util.List", SERVICE_SCOPE },
				{ "selectedBranchObject", "java.util.List", SERVICE_SCOPE },
				{ "guarantorObject", "java.util.List", SERVICE_SCOPE },
				{ "generateLadFormObj","com.integrosys.cms.ui.genlad.OBFilter",SERVICE_SCOPE },
				{ "relationshipMgrList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "dateList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "rmRegionList", "java.util.List", SERVICE_SCOPE },
				{ "camQuarterList", "java.util.List", SERVICE_SCOPE },
				{ "yearList", "java.util.List", SERVICE_SCOPE },
				{ "currentYear", "java.lang.String", SERVICE_SCOPE },
				{ "isRMUser", "java.lang.Boolean", SERVICE_SCOPE },
				{"isRMValue","java.lang.String", SERVICE_SCOPE},
		

		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		String date = "";
		
		String dueYear="";
		long country = 0;
		DefaultLogger.debug(this, "Inside doExecute()");

		IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse
				.get("generalParamDao");

		IGeneralParamEntry currentAppDateParamEntry = generalParamDao
				.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");

		String app_date = currentAppDateParamEntry.getParamValue();
		
		GeneratePartyLADDao partyLadDao = (GeneratePartyLADDao) BeanHouse
				.get("ladPartyDao");

		//Date currentDate =   Calendar.getInstance().getTime();
		
		
		  SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			date =new SimpleDateFormat("MMMM").format(formatter.parse(app_date));
			dueYear =new SimpleDateFormat("yyyy").format(formatter.parse(app_date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String userId=(String) map.get("USER_LOGIN_ID");
		
		Integer year=Calendar.getInstance().get(Calendar.YEAR);

		HashMap reportFilterMap = ReportFilterTypeList.getInstance()
				.getReportFilterList();

		resultMap.put("departmentList", getDepartmentList());

		resultMap.put("managerList", getManagerList());
		
		resultMap.put("yearList", getYearList(dueYear));
		
		resultMap.put("currentYear", year.toString());
		
		resultMap.put("isRMUser", isRMUser(userId));
		
//		resultMap.put("resultList",partyLadDao.getAllData() );

		List<LabelValueBean> camQuarterList = new ArrayList<LabelValueBean>();

		DefaultLogger.debug(this, "camQuarterList :" + camQuarterList);

		resultMap.put("camQuarterList", camQuarterList);

		resultMap.put("dateValue", date.toString());
		resultMap.put("dueYear", dueYear.toString());

		resultMap.put("customerObject", null);
		resultMap.put("selectedUserObject", null);
		resultMap.put("selectedBranchObject", null);
		resultMap.put("guarantorObject", null);
		resultMap.put("reportFormObj", null);

		resultMap.put("rmRegionList", getRegionList());
		resultMap.put("dateList", getDateList());

		resultMap.put("relationshipMgrList", getRelationshipMgrList("",userId));
		
		resultMap.put("isRMValue", isRMValue);
		
		resultMap.put("resultList",partyLadDao.getAllData((Boolean)resultMap.get("isRMUser"),(String)resultMap.get("isRMValue")) );
		resultMap.put("isRecordAvailable", "Y");
		resultMap.put("recordAlreadyPresent", "N");
		
		
	

		DefaultLogger.debug(this,
				"---------------OOOOOOOOOOOOOUUUUUUUUUUUUUTTTTTTTTTTTT------");

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	private List getYearList(String dueYear) {
		
		Integer year=Integer.parseInt(dueYear);
		
		Integer toYear=year+100;
		
		List yearList=new ArrayList();
		for(int i=year;i<=toYear;i++){
			
			LabelValueBean lvBean = new LabelValueBean(String.valueOf(i), String.valueOf(i));
			yearList.add(lvBean);
		}
		
		return yearList;
	}
	
	
	
	public boolean isRMUser(String employeeCode) {
		IRelationshipMgrDAO relationshipmgr = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
		String	strRelationshipmgr = "";
		if(employeeCode != null){
			strRelationshipmgr = relationshipmgr.getRelationshipMgrByEmployeeCode(employeeCode).getRelationshipMgrName();
		}
		
		return strRelationshipmgr==null?false:true;
	}

	private List getDepartmentList() {
		List departmentList = new ArrayList();
		CommonCodeList commonCode = CommonCodeList
				.getInstance(CategoryCodeConstant.HDFC_DEPARTMENT);
		Map labelValueMap = commonCode.getLabelValueMap();
		Iterator iterator = labelValueMap.entrySet().iterator();
		String label;
		String value;
		while (iterator.hasNext()) {
			Map.Entry pairs = (Map.Entry) iterator.next();
			value = pairs.getKey().toString();
			label = pairs.getKey() + " (" + pairs.getValue() + ")";
			LabelValueBean lvBean = new LabelValueBean(label, value);
			departmentList.add(lvBean);
		}
		return CommonUtil.sortDropdown(departmentList);
	}

	private List getManagerList() {
		IRelationshipMgrDAO relationDAO = (IRelationshipMgrDAO) BeanHouse
				.get("relationshipMgrDAO");
		List managerList = new ArrayList();
		List returnManager = new ArrayList();
		SearchResult manager = relationDAO.getRelationshipMgrList("", "");
		if (manager != null) {
			managerList = (List) manager.getResultList();
		}
		if (managerList.size() > 0) {
			for (int s = 0; s < managerList.size(); s++) {
				OBRelationshipMgr objManager = (OBRelationshipMgr) managerList
						.get(s);
				if (objManager != null) {
					String id = String.valueOf(objManager.getId());

					String val = objManager.getRelationshipMgrName();
					if (id != null && !"".equals(id) && null != val
							&& !val.equals("")) {
						LabelValueBean lvBean = new LabelValueBean(val, id);
						returnManager.add(lvBean);
					}
				}
			}
		}
		return CommonUtil.sortDropdown(returnManager);
	}

	private List getRegionList() {
		List lbValList = new ArrayList();
		try {
			DefaultLogger.debug(this, "inside getRegionList() method");
			List idList = (List) getRelationshipMgrProxyManager()
					.getRegionList(
							PropertyManager
									.getValue("clims.application.country"));

			for (int i = 0; i < idList.size(); i++) {
				IRegion region = (IRegion) idList.get(i);
				String id = Long.toString(region.getIdRegion());
				String val = region.getRegionName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getDateList() {
		List lbValList = new ArrayList();
		try {
			DefaultLogger.debug(this, "inside getRegionList() method");

			LabelValueBean lvBean = new LabelValueBean("January", "January");
			lbValList.add(lvBean);
			lvBean = new LabelValueBean("February", "February");
			lbValList.add(lvBean);
			lvBean = new LabelValueBean("March", "March");
			lbValList.add(lvBean);
			lvBean = new LabelValueBean("April", "April");
			lbValList.add(lvBean);
			lvBean = new LabelValueBean("May", "May");
			lbValList.add(lvBean);
			lvBean = new LabelValueBean("June", "June");
			lbValList.add(lvBean);
			lvBean = new LabelValueBean("July", "July");
			lbValList.add(lvBean);
			lvBean = new LabelValueBean("August", "August");
			lbValList.add(lvBean);
			lvBean = new LabelValueBean("September", "September");
			lbValList.add(lvBean);
			lvBean = new LabelValueBean("October", "October");
			lbValList.add(lvBean);
			lvBean = new LabelValueBean("November", "November");
			lbValList.add(lvBean);
			lvBean = new LabelValueBean("December", "December");
			lbValList.add(lvBean);

		} catch (Exception ex) {
		}
		return lbValList;
	}

	private List getRelationshipMgrList(String rmRegion, String userId) {
		List lbValList = new ArrayList();
		List idList = new ArrayList();
		isRMValue="";
		try {
			DefaultLogger.debug(this, "inside getRelationshipMgrList() method");
			SearchResult idListsr = (SearchResult) getRelationshipMgrProxyManager()
					.getRelationshipMgrList(rmRegion);

			if (idListsr != null) {
				idList = new ArrayList(idListsr.getResultList());
			}

			for (int i = 0; i < idList.size(); i++) {
				IRelationshipMgr mgr = (IRelationshipMgr) idList.get(i);
				if (mgr.getStatus().equals("ACTIVE")) {
					String id = Long.toString(mgr.getId());
					String val = mgr.getEmployeeId();
					String value=mgr.getRelationshipMgrName();
					LabelValueBean lvBean = new LabelValueBean(value, id);
					if(userId.equals(val)){
						isRMValue=id;
					}
					
					
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getList(long stateId) {
		List lbValList = new ArrayList();
		try {
			List idList = new ArrayList();
			for (int i = 0; i < idList.size(); i++) {
				IRegion region = (IRegion) idList.get(i);
				String id = "";
				String val = "";
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
