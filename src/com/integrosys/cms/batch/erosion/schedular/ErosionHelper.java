package com.integrosys.cms.batch.erosion.schedular;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.erosion.bus.IErosionStatus;
import com.integrosys.cms.app.erosion.bus.IErosionStatusJdbc;
import com.integrosys.cms.app.erosion.bus.IErosionStatusJdbcTemplateImpl;
import com.integrosys.cms.app.erosion.proxy.IErosionStatusProxy;
import com.integrosys.cms.app.ftp.FileUploadFtpService;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.OBGeneralParamEntry;
import com.integrosys.cms.app.poi.report.IReportDao;
import com.integrosys.cms.app.poi.report.IReportService;
import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.app.poi.report.ReportParser;
import com.integrosys.cms.app.poi.report.writer.BaseReport;
import com.integrosys.cms.app.poi.report.xml.schema.Reports;
import com.integrosys.cms.batch.eod.EODConstants;
import com.integrosys.cms.batch.reports.ReportException;

public class ErosionHelper implements IErosionHelper,IErosionFileConstants{
	
	private IErosionStatusProxy erosionStatusProxy;
	private IErosionStatusJdbc erosionStatusJdbc;
	private IReportDao reportDao;
	private IReportService reportService;
	private IErosionStatusJdbcTemplateImpl erosionStatusJdbcTemplate;
	
	public IReportService getReportService() {
		return reportService;
	}

	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
	}
	
	public IReportDao getReportDao() {
		return reportDao;
	}

	public void setReportDao(IReportDao reportDao) {
		this.reportDao = reportDao;
	}
	
	public IErosionStatusProxy getErosionStatusProxy() {
		return erosionStatusProxy;
	}

	public void setErosionStatusProxy(IErosionStatusProxy erosionStatusProxy) {
		this.erosionStatusProxy = erosionStatusProxy;
	}
	
	public IErosionStatusJdbc getErosionStatusJdbc() {
		return erosionStatusJdbc;
	}

	public void setErosionStatusJdbc(IErosionStatusJdbc erosionStatusJdbc) {
		this.erosionStatusJdbc = erosionStatusJdbc;
	}
	
	public IErosionStatusJdbcTemplateImpl getErosionStatusJdbcTemplate() {
		return erosionStatusJdbcTemplate;
	}

	public void setErosionStatusJdbcTemplate(IErosionStatusJdbcTemplateImpl erosionStatusJdbcTemplate) {
		this.erosionStatusJdbcTemplate = erosionStatusJdbcTemplate;
	}

	public boolean performActivity(IErosionStatus erosionStatus, boolean isCreateData, String applicationDate) {
		DefaultLogger.debug(this, "performActivity() starts ==== isCreateData= "+isCreateData);
		boolean isFailed=true;
		if (STATUS_FAIL.equalsIgnoreCase(erosionStatus.getStatus()) 
				||  STATUS_PENDING.equalsIgnoreCase(erosionStatus.getStatus())) {
			try {
				if(isCreateData && EXECREATEDATAFACILITYRPT.equalsIgnoreCase(erosionStatus.getActivity())) {
					DefaultLogger.debug(this,"Activity started:"+erosionStatus.getActivity());
					erosionStatusJdbc.spCreateDataForFacilityReport(applicationDate);
					erosionStatus.setStatus(STATUS_PASS);
					DefaultLogger.debug(this,"Activity finished:"+erosionStatus.getActivity());
					
				} else if(isCreateData && EXECREATEDATASECURITYRPT.equalsIgnoreCase(erosionStatus.getActivity())) {
					DefaultLogger.debug(this,"Activity started:"+erosionStatus.getActivity());
					//erosionStatusJdbc.spCreateDataForSecurityReport();
					erosionStatus.setStatus(STATUS_PASS);
					DefaultLogger.debug(this,"Activity finished:"+erosionStatus.getActivity());
					
				} else if(isCreateData && EXECREATEDATAFACWISEEROSIONRPT.equalsIgnoreCase(erosionStatus.getActivity())) {
					DefaultLogger.debug(this,"Activity started:"+erosionStatus.getActivity());
					erosionStatusJdbc.spCreateDataForFacilityWiseReport(applicationDate);
					erosionStatus.setStatus(STATUS_PASS);
					DefaultLogger.debug(this,"Activity finished:"+erosionStatus.getActivity());
					
				} else if(isCreateData && EXECREATEDATAPARTYWISEEROSIONRPT.equalsIgnoreCase(erosionStatus.getActivity())) {
					DefaultLogger.debug(this,"Activity started:"+erosionStatus.getActivity());
					//erosionStatusJdbc.spCreateDataForPartyWiseReport();
					erosionStatus.setStatus(STATUS_PASS);
					DefaultLogger.debug(this,"Activity finished:"+erosionStatus.getActivity());
					
				} else if(isCreateData && EXEUPDATEEROSIONFORNPATRAQFILE.equalsIgnoreCase(erosionStatus.getActivity())) {
					DefaultLogger.debug(this,"Activity started:"+erosionStatus.getActivity());
					//erosionStatusJdbc.spUpdateErosionForNpaFile();
					erosionStatus.setStatus(STATUS_PASS);
					DefaultLogger.debug(this,"Activity finished:"+erosionStatus.getActivity());
					
				} else if(!isCreateData && EXEGENERATEFILEFACILITYRPT.equalsIgnoreCase(erosionStatus.getActivity())) {
					DefaultLogger.debug(this,"Activity started:"+erosionStatus.getActivity());
					generateErosionReportFile(EXEGENERATEFILEFACILITYRPT,getFileName(EROSION_FACILITY_FILENAME));
					erosionStatus.setStatus(STATUS_PASS);
					DefaultLogger.debug(this,"Activity finished:"+erosionStatus.getActivity());
					
				}else if(!isCreateData && EXEGENERATEFILESECURITYRPT.equalsIgnoreCase(erosionStatus.getActivity())) {
					DefaultLogger.debug(this,"Activity started:"+erosionStatus.getActivity());
					generateErosionReportFile(EXEGENERATEFILESECURITYRPT,getFileName(EROSION_SECURITY_FILENAME));
					erosionStatus.setStatus(STATUS_PASS);
					DefaultLogger.debug(this,"Activity finished:"+erosionStatus.getActivity());
					
				}else if(!isCreateData && EXEGENERATEFILEFACWISEEROSIONRPT.equalsIgnoreCase(erosionStatus.getActivity())) {
					DefaultLogger.debug(this,"Activity started:"+erosionStatus.getActivity());
					generateErosionReportFile(EXEGENERATEFILEFACWISEEROSIONRPT,getFileName(EROSION_FACILITY_WISE_FILENAME));
					erosionStatus.setStatus(STATUS_PASS);
					DefaultLogger.debug(this,"Activity finished:"+erosionStatus.getActivity());
					
				}else if(!isCreateData && EXEGENERATEFILEPARTYWISEEROSIONRPT.equalsIgnoreCase(erosionStatus.getActivity())) {
					DefaultLogger.debug(this,"Activity started:"+erosionStatus.getActivity());
					generateErosionReportFile(EXEGENERATEFILEPARTYWISEEROSIONRPT,getFileName(EROSION_PARTY_WISE_FILENAME));
					erosionStatus.setStatus(STATUS_PASS);
					DefaultLogger.debug(this,"Activity finished:"+erosionStatus.getActivity());
				}
			}
			catch (Exception e) {
				isFailed=false;
				erosionStatus.setStatus(STATUS_FAIL);
				DefaultLogger.error(this,"BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : performActivity()) isCreateData=" +isCreateData);
				e.printStackTrace();
			}
		}
		else if(STATUS_PASS.equalsIgnoreCase(erosionStatus.getStatus())
				|| STATUS_DONE.equalsIgnoreCase(erosionStatus.getStatus())) {
			erosionStatus.setStatus(EODConstants.STATUS_DONE);
		}
		erosionStatusJdbcTemplate.updateErosionActivity(erosionStatus);
		DefaultLogger.debug(this, " performActivity() ends ====isFailed= "+isFailed);
		return isFailed;
	}
	
	public String getFileName(String reportName) {
		String date = new SimpleDateFormat(EROSION_FILEDATEFORMAT).format(new Date());
		return reportName+date+".csv";
	}
	
	public void finalizeErosion(boolean erosionProcessExecuted,boolean isCreate) {
		List erosionActivites = erosionStatusJdbcTemplate.findErosionActivities(isCreate);
		Iterator iter = erosionActivites.iterator();
		IErosionStatus erosionStatus;
		boolean status = true;
		
		while (iter.hasNext()) {
			erosionStatus = (IErosionStatus)iter.next();
			System.out.println("\n::::::::"+erosionStatus.getActivity()+" : "+erosionStatus.getStatus());
			if (STATUS_FAIL.equalsIgnoreCase(erosionStatus.getStatus())) {
				status = false;
			}
		}
		
		if (status && erosionProcessExecuted) {
			try{
				DefaultLogger.debug(this,"Inside finalizeErosion() itreator line 161");
				iter = erosionActivites.iterator();
				
				while (iter.hasNext()) {
					erosionStatus = (IErosionStatus)iter.next();
					erosionStatus.setReportingDate(new Date());
					erosionStatus.setStatus(STATUS_PENDING);
					erosionStatusJdbcTemplate.updateErosionActivity(erosionStatus);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			DefaultLogger.debug(this,"status && erosionProcessExecuted is not true: status="+status+", erosionProcessExecuted="+erosionProcessExecuted);
		}
	}
	
	public List findErosionActivities(boolean isCreate) {
		List erosionActivites = erosionStatusJdbcTemplate.findErosionActivities(isCreate);
		return erosionActivites;
	}
	
	public void generateErosionReportFile(String reportName,String fileName) {
		
		OBFilter filter = null;
		Reports reports= null;
		ReportParser parser = new ReportParser();
		
		if (EXEGENERATEFILEFACILITYRPT.equalsIgnoreCase(reportName)) {
			reports = parser.getReportObject(TEMPLATEFACILITY, filter);
			generateReport(fileName, reports, filter, TEMPLATEFACILITY);
			
		} else if (EXEGENERATEFILESECURITYRPT.equalsIgnoreCase(reportName)) {
			reports = parser.getReportObject(TEMPLATESECURITY, filter);
			generateReport(fileName, reports, filter, TEMPLATESECURITY);

		} else if (EXEGENERATEFILEFACWISEEROSIONRPT.equalsIgnoreCase(reportName)) {
			reports = parser.getReportObject(TEMPLATEFACILITYWISEEROSION, filter);
			generateReport(fileName, reports, filter, TEMPLATEFACILITYWISEEROSION);

		} else if (EXEGENERATEFILEPARTYWISEEROSIONRPT.equalsIgnoreCase(reportName)) {
			reports = parser.getReportObject(TEMPLATEPARTYWISEEROSION, filter);
			generateReport(fileName, reports, filter, TEMPLATEPARTYWISEEROSION);
		}
	}
	
	public void generateReport(String fileName,Reports reports,OBFilter filter,String reportType) {
		Map parameters =null;
		String reportFileType = "csv";
		if(reports.getReport()==null)
			throw new ReportException("Report object not found.");
		
		if(filter==null)
			filter= new OBFilter();
		
		List<Object[]> reportQueryResult = Collections.emptyList();
		reportQueryResult = getReportDao().getReportQueryResult(reports,filter);
		parameters = getReportService().generateParamatersMap(reports);
		List<String[]> reportDataList = reportService.getReportDataList(reportQueryResult,parameters, reportFileType);
		new BaseReport().writeCSVFile(fileName, reportDataList,parameters,reportType);
	}
	
	public OBGeneralParamEntry getAppDate(){
		OBGeneralParamEntry appdate = erosionStatusJdbcTemplate.getAppDate();
		return appdate;
	}
	
	public OBGeneralParamEntry getErosionDate(){
		OBGeneralParamEntry appdate = erosionStatusJdbcTemplate.getErosionDate();
		return appdate;
	}
	
	public void updateGeneralParamErosionDate(String newExecutionDate, Date erosionDate){
		erosionStatusJdbcTemplate.updateGeneralParamErosionDate(newExecutionDate,erosionDate);
	}
	
	public void executeErosionDataBackup() {
		DefaultLogger.debug(this,"Activity started:");
		erosionStatusJdbc.spErosionDataBackup();
		DefaultLogger.debug(this,"Activity finished:");
	}

}
