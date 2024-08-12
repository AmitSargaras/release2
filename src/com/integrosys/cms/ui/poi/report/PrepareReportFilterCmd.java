package com.integrosys.cms.ui.poi.report;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.poi.report.IReportService;
import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.batch.erosion.schedular.IErosionFileConstants;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * @author cyliew
 * @author Chong Jun Yong
 * @since 2006/10/27
 */
public class PrepareReportFilterCmd extends AbstractCommand implements
		ICommonEventConstant {

	private IReportService reportService;

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

	public void setRelationshipMgrProxyManager(IRelationshipMgrProxyManager relationshipMgrProxyManager) {
		this.relationshipMgrProxyManager = relationshipMgrProxyManager;
	}
	
	/**
	 * Default Constructor
	 */
	public PrepareReportFilterCmd() {

	}

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "reportId", "java.lang.String", REQUEST_SCOPE },
				{ "reportFormObj","com.integrosys.cms.app.poi.report.OBFilter",FORM_SCOPE }, 
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
				{ "reportsFilterType", "java.lang.String",SERVICE_SCOPE },
				{ "fileName", "java.lang.String",REQUEST_SCOPE },
				{ "departmentList", "java.util.List", SERVICE_SCOPE },
				{ "managerList", "java.util.List", SERVICE_SCOPE },				
				{ "customerObject", "java.util.List", SERVICE_SCOPE },
				{ "selectedUserObject", "java.util.List", SERVICE_SCOPE },
				{ "selectedBranchObject", "java.util.List", SERVICE_SCOPE },
				{ "guarantorObject", "java.util.List", SERVICE_SCOPE },
				{ "reportFormObj","com.integrosys.cms.app.poi.report.OBFilter",SERVICE_SCOPE },
				{ "relationshipMgrList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "rmRegionList", "java.util.List", SERVICE_SCOPE },
				{ "camQuarterList", "java.util.List", SERVICE_SCOPE },
				{ "facilityList", "java.util.List", SERVICE_SCOPE },
				{ "fileInfo", "java.util.List", REQUEST_SCOPE },
				
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
		long country = 0;
		DefaultLogger.debug(this, "Inside doExecute()");

//		String reportId = (String) map.get("reportId");
//		
//		if (reportId == null)
//			reportId = "RPT0001";

		OBFilter filter = (OBFilter) map.get("reportFormObj");
		DefaultLogger.debug(this,"========= report ID=====>" + filter.getReportId());
		HashMap reportFilterMap = ReportFilterTypeList.getInstance().getReportFilterList();
		String reportsFilterType = (String) reportFilterMap.get(filter.getReportId());
		resultMap.put("reportsFilterType", reportsFilterType);
		resultMap.put("departmentList", getDepartmentList());
		if(filter.getReportId().equals("RPT0049")||filter.getReportId().equals("RPT0009")||filter.getReportId().equals("RPT0033_1")
				||filter.getReportId().equals("RPT0033_2")||filter.getReportId().equals("RPT0002")||filter.getReportId().equals("RPT0013_1")
				||filter.getReportId().equals("RPT0013_2")||filter.getReportId().equals("RPT0013_3")||filter.getReportId().equals("RPT0013_4")
				||filter.getReportId().equals("RPT0007")||filter.getReportId().equals("RPT0010")||filter.getReportId().equals("RPT0011")
				||filter.getReportId().equals("RPT0029")||filter.getReportId().equals("RPT0024")||filter.getReportId().equals("RPT0051")
				||filter.getReportId().equals("RPT0050")){
		resultMap.put("managerList", getManagerList());
		}else{
			resultMap.put("managerList", new ArrayList());
		}
		if(filter.getReportId().equals("RPT0024")){
			ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");

			collateralDAO.executeCustLimitMviewProc();
			}
		if(filter.getReportId().equals("RPT0051")){
			ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");

			collateralDAO.executeFacSecNewMviewProc();
			}
		
		if(filter.getReportId().equals("RPT0087")){
			ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");

			//collateralDAO.getFacilityList();
			//resultMap.put("facilityList", collateralDAO.getFacilityList());
			resultMap.put("facilityList", getFacilityNameList(collateralDAO.getFacilityNameList()));
			}
		//Uma:For Cam Quarter Activity CR

	    List<LabelValueBean> camQuarterList=new ArrayList<LabelValueBean>();
		if(filter.getReportId().equals("RPT0059")){
			IGeneralParamDao generalParamDao = (IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamEntry generalParamEntry2 = generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			Date date = new Date(generalParamEntry2.getParamValue());
			Calendar currDate =Calendar.getInstance();
			currDate.setTime(date);
			int month = currDate.get(Calendar.MONTH);
			
			if((month >= Calendar.JANUARY && month <= Calendar.MARCH)){
				LabelValueBean labelValueQuarter =new LabelValueBean("Q3", "Q-2");
				camQuarterList.add(labelValueQuarter);
				labelValueQuarter =new LabelValueBean("Q4", "Q-1");
				 camQuarterList.add(labelValueQuarter);
			}else if((month >= Calendar.APRIL && month <= Calendar.JUNE)){       
				LabelValueBean labelValueQuarter =new LabelValueBean("Q4", "Q-2");
				camQuarterList.add(labelValueQuarter);
				labelValueQuarter =new LabelValueBean("Q1", "Q-1");
				 camQuarterList.add(labelValueQuarter);
			}else if((month >= Calendar.JULY && month <= Calendar.SEPTEMBER) ){       
				LabelValueBean labelValueQuarter =new LabelValueBean("Q1", "Q-2");
				camQuarterList.add(labelValueQuarter);
				labelValueQuarter =new LabelValueBean("Q2", "Q-1");
				 camQuarterList.add(labelValueQuarter);
			}else if((month >= Calendar.OCTOBER && month <= Calendar.DECEMBER)){       
				LabelValueBean labelValueQuarter =new LabelValueBean("Q2", "Q-2");
				camQuarterList.add(labelValueQuarter);
				labelValueQuarter =new LabelValueBean("Q3", "Q-1");
				camQuarterList.add(labelValueQuarter);
			}
			
			 CommonUtil.sortDropdown(camQuarterList);
		}
		DefaultLogger.debug(this, "camQuarterList :"+camQuarterList);
		resultMap.put("camQuarterList", camQuarterList);
		
		//Resetting the filters to null
		resultMap.put("customerObject", null);
		resultMap.put("selectedUserObject", null);
		resultMap.put("selectedBranchObject", null);
		resultMap.put("guarantorObject", null);
		resultMap.put("reportFormObj", filter);
		
		DefaultLogger.debug(this, "value of rm region"+filter.getRmRegion());
		resultMap.put("rmRegionList", getRegionList());
		
		String rmRegion = filter.getRmRegion();
		DefaultLogger.debug(this, "Going out of doExecute()");
		if (!(rmRegion == null || rmRegion.equals(""))) {
			resultMap.put("relationshipMgrList",getRelationshipMgrList(rmRegion));
		} else {
			resultMap.put("relationshipMgrList", getRelationshipMgrList(""));
		}
		DefaultLogger.debug(this, "---------------OOOOOOOOOOOOOUUUUUUUUUUUUUTTTTTTTTTTTT------");
		
		//Erosion CR
		List fileInfo=new ArrayList();
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		File path=new File(bundle.getString(IErosionFileConstants.FTP_EROSION_UPLOAD_LOCAL_DIR));
		File list[]=path.listFiles();
		if(null != list) {
		for(int i=list.length-1;i>=0;i--){ 
			File sortFile=list[i];
			if(filter.getReportId().equals("EROSION01") && 
					(sortFile.getName().contains(IErosionFileConstants.EROSION_FACILITY_FILENAME) 
					&& (sortFile.getName().endsWith(".csv") ||  sortFile.getName().endsWith(".CSV")))){
				fileInfo.add(list[i]);
			}else if(filter.getReportId().equals("EROSION02") && 
					(sortFile.getName().contains(IErosionFileConstants.EROSION_SECURITY_FILENAME) 
					&& (sortFile.getName().endsWith(".csv") ||  sortFile.getName().endsWith(".CSV")))){
				fileInfo.add(list[i]);
			}else if(filter.getReportId().equals("EROSION03") && 
					(sortFile.getName().contains(IErosionFileConstants.EROSION_FACILITY_WISE_FILENAME) 
					&& (sortFile.getName().endsWith(".csv") ||  sortFile.getName().endsWith(".CSV")))){
				fileInfo.add(list[i]);
			}else if(filter.getReportId().equals("EROSION04") && 
					(sortFile.getName().contains(IErosionFileConstants.EROSION_PARTY_WISE_FILENAME) 
					&& (sortFile.getName().endsWith(".csv") ||  sortFile.getName().endsWith(".CSV")))){
				fileInfo.add(list[i]);
			}
			resultMap.put("fileInfo", fileInfo);
		}
}
		//End Erosion
		
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
	 private List getDepartmentList() {
		List departmentList= new ArrayList();
		CommonCodeList  commonCode = CommonCodeList.getInstance(CategoryCodeConstant.HDFC_DEPARTMENT);
		Map labelValueMap = commonCode.getLabelValueMap();			
		Iterator iterator = labelValueMap.entrySet().iterator();
		String label;
		String value;
		while (iterator.hasNext()) {
	        Map.Entry pairs = (Map.Entry)iterator.next();
	        value=pairs.getKey().toString();
	        label=pairs.getKey()+" ("+pairs.getValue()+")";
			LabelValueBean lvBean = new LabelValueBean(label,value);
			departmentList.add(lvBean);
	    }
		return CommonUtil.sortDropdown(departmentList);
	}
	 private List getfacilityList() {
		 List lbValList = new ArrayList();
			try {
				DefaultLogger.debug(this, "inside getfacilityList() method");
				ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
				List idList = (List) collateralDAO.getFacilityList();				
			
				for (int i = 0; i < idList.size(); i++) {
					IRegion region = (IRegion)idList.get(i);
						String id = Long.toString(region.getIdRegion());
						String val = region.getRegionName();
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
				}
			} catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}
	 
	 private List getManagerList(){
		 IRelationshipMgrDAO relationDAO=(IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
		List managerList=new ArrayList();
		List returnManager=new ArrayList();
		SearchResult manager=relationDAO.getRelationshipMgrList("","");
		if(manager!=null){
		managerList=(List)manager.getResultList();
		}
		if( managerList.size()>0){
				for(int s=0;s<managerList.size();s++){
					OBRelationshipMgr objManager=(OBRelationshipMgr)managerList.get(s);
					if(objManager!=null){
						String id=String.valueOf(objManager.getId());
					
					String val = objManager.getRelationshipMgrName();
					if(id != null && !"".equals(id) && null!=val && !val.equals("")){
					LabelValueBean lvBean = new LabelValueBean(val,id);
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
				List idList = (List) getRelationshipMgrProxyManager().getRegionList(PropertyManager.getValue("clims.application.country"));				
			
				for (int i = 0; i < idList.size(); i++) {
					IRegion region = (IRegion)idList.get(i);
						String id = Long.toString(region.getIdRegion());
						String val = region.getRegionName();
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
				}
			} catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}
	 private List getRelationshipMgrList(String rmRegion) {
			List lbValList = new ArrayList();
			List idList = new ArrayList();
			try {
				DefaultLogger.debug(this, "inside getRelationshipMgrList() method");
				SearchResult idListsr = (SearchResult) getRelationshipMgrProxyManager().getRelationshipMgrList(rmRegion);

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
//			return CommonUtil.sortDropdown(lbValList);
			return lbValList;
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
//			return CommonUtil.sortDropdown(lbValList);
			return lbValList;
		}

	private List getFacilityNameList(List lst) {
		List lbValList = new ArrayList();
		try {

			for (int i = 0; i < lst.size(); i++) {
				String[] mgnrLst = (String[]) lst.get(i);
				LabelValueBean lvBean = new LabelValueBean(mgnrLst[1], mgnrLst[0]);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
