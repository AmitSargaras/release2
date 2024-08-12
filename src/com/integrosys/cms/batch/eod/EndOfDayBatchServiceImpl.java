package com.integrosys.cms.batch.eod;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.ConcurrentMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentItem;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.trx.IDocumentItemTrxValue;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.eod.basel.IBaselDao;
import com.integrosys.cms.app.eod.bus.IAdfRbiStatus;
import com.integrosys.cms.app.eod.bus.IEODStatus;
import com.integrosys.cms.app.eod.bus.IEODStatusJdbc;
import com.integrosys.cms.app.eod.camquarter.ICamQuarterDao;
import com.integrosys.cms.app.eod.proxy.IEODStatusProxy;
import com.integrosys.cms.app.eod.refreshMv.IRefreshMvDao;
import com.integrosys.cms.app.eventmonitor.enabledisableuser.MonEnableDisableUser;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.app.ftp.SFTPClient;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.holiday.bus.HolidayHelper;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.bus.IHolidayDao;
import com.integrosys.cms.app.holiday.bus.IHolidayJdbc;
import com.integrosys.cms.app.holiday.bus.OBHoliday;
import com.integrosys.cms.app.holiday.proxy.IHolidayProxyManager;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.holiday.trx.OBHolidayTrxValue;
import com.integrosys.cms.app.lad.bus.ILAD;
import com.integrosys.cms.app.lad.bus.ILADDao;
import com.integrosys.cms.app.lad.bus.ILADItem;
import com.integrosys.cms.app.lad.bus.ILADSubItem;
import com.integrosys.cms.app.lad.bus.OBLADItem;
import com.integrosys.cms.app.lad.bus.OBLADSubItem;
import com.integrosys.cms.app.lad.proxy.ILADProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;
import com.integrosys.cms.app.user.bus.StdUserDAO;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.batch.email.notification.EmailNotificationMain;
import com.integrosys.cms.batch.ramRatingDetails.schedular.RamRatingDetails;
import com.integrosys.cms.ui.basel.report.GenerateReportCmd;
/**
 * Basell Report Generation - Daily/monthly
 * Temp Folder Clean up   .. 
 * Change app date        .. done
 * generate Notification  .. done
 * check dormant users
 * force logout
 * 
 * Year end activity
 * Recurrent Holiday	  .. done  
 * Recurrent activites   
 * 
 * generateReport
 * */

public class EndOfDayBatchServiceImpl implements IEndOfDayBatchService {
	
	//Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD
	public static final String BASEL_UPDATE_REPORT_DIRECTORY = "basel.update.report.dir";
	public static final String BASEL_REPORT_FILE_NAME = "Limits_V3_";
	public static final String BASEL_UPDATE_UPLOAD = "BaselUpdateUpload";
	
	SimpleDateFormat newDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	private IGeneralParamDao generalParam;
	private IHolidayDao holidayDao;
	private IBaselDao baselDao;
	private IHolidayJdbc holidayJdbc;
	private List holidayList;
	private List recurrentHolidayList;
	private IGeneralParamEntry generalParamEntry = null;
	private String[] folderNames;
	private MonEnableDisableUser monEnableDisableUser;
	private StringBuffer log = new StringBuffer();
	private StringBuffer rBIlog = new StringBuffer();
	private Calendar currentDate = Calendar.getInstance();
	private Calendar date = Calendar.getInstance();
	private Calendar nextDate = Calendar.getInstance();
	String logfileName;
	private EmailNotificationMain emailNotificationMain;  // Shiv 131212
	private IEODStatusProxy eodStatusProxy;
	
	//Start:Uma Khot: 19/02/2016 :Refreshing MV as User Admin Report Taking time for execution.
	private IRefreshMvDao refreshMvDao;
	//End:Uma Khot: 19/02/2016 :Refreshing MV as User Admin Report Taking time for execution.
	
	IEODStatusJdbc statusJdbc = (IEODStatusJdbc) BeanHouse.get("eodStatusJdbc");
	// Start:Uma Khot: Added for CAM QUARTER ACTIVITY CR
	private ICamQuarterDao camQuarterDao;
	
	public ICamQuarterDao getCamQuarterDao() {
		return camQuarterDao;
	}

	public void setCamQuarterDao(ICamQuarterDao camQuarterDao) {
		this.camQuarterDao = camQuarterDao;
	}

	// End:Uma Khot: Added for CAM QUARTER ACTIVITY CR
	
	public IEODStatusProxy getEodStatusProxy() {
		return eodStatusProxy;
	}

	public void setEodStatusProxy(IEODStatusProxy eodStatusProxy) {
		this.eodStatusProxy = eodStatusProxy;
	}

	public EmailNotificationMain getEmailNotificationMain() {
		return emailNotificationMain;
	}

	public void setEmailNotificationMain(EmailNotificationMain emailNotificationMain) {
		this.emailNotificationMain = emailNotificationMain;
	}

	public MonEnableDisableUser getMonEnableDisableUser() {
		return monEnableDisableUser;
	}

	public void setMonEnableDisableUser(MonEnableDisableUser monEnableDisableUser) {
		this.monEnableDisableUser = monEnableDisableUser;
	}

	public IGeneralParamDao getGeneralParam() {
		return generalParam;
	}

	public void setGeneralParam(IGeneralParamDao generalParam) {
		this.generalParam = generalParam;
	}

	public IHolidayDao getHolidayDao() {
		return holidayDao;
	}

	public void setHolidayDao(IHolidayDao holidayDao) {
		this.holidayDao = holidayDao;
	}

	public IHolidayJdbc getHolidayJdbc() {
		return holidayJdbc;
	}

	public void setHolidayJdbc(IHolidayJdbc holidayJdbc) {
		this.holidayJdbc = holidayJdbc;
	}

	public IBaselDao getBaselDao() {
		return baselDao;
	}

	public void setBaselDao(IBaselDao baselDao) {
		this.baselDao = baselDao;
	}

	//Start:Uma Khot: 19/02/2016 :Refreshing MV as User Admin Report Taking time for execution.
	public IRefreshMvDao getRefreshMvDao() {
		return refreshMvDao;
	}

	public void setRefreshMvDao(IRefreshMvDao refreshMvDao) {
		this.refreshMvDao = refreshMvDao;
	}
	//End:Uma Khot: 19/02/2016 :Refreshing MV as User Admin Report Taking time for execution.

	private void getPreEODData() {
		getCurrentDate();
//		holidayList = holidayJdbc.getHolidayListForYear(date.get(Calendar.YEAR));
//		recurrentHolidayList = holidayJdbc.getRecurrentHolidayListForYear(date.get(Calendar.YEAR));
		
		//Uma:Start:Prod issue: to get holiday list for eod process which are active.
		holidayList = holidayJdbc.getHolidayListForYearEod(date.get(Calendar.YEAR));
		recurrentHolidayList = holidayJdbc.getRecurrentHolidayListForYearEod(date.get(Calendar.YEAR));
		//Uma:End:Prod issue: to get holiday list for eod process which are active.
		
		getNextWorkingDate(nextDate);
	}

	private void getCurrentDate() {
		DefaultLogger.debug(this, "getCurrentDate()");
		try {/*
			IGeneralParamGroup generalParamGroup = generalParam.getGeneralParamGroupByGroupType("actualGeneralParamGroup","GENERAL_PARAM");
			IGeneralParamEntry[] generalParamEntries = generalParamGroup.getFeedEntries();

			for (int i = 0; i < generalParamEntries.length; i++) {
				if (generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")) {
					generalParamEntry = generalParamEntries[i];
					currentDate.setTime(new Date(generalParamEntries[i].getParamValue()));
					date.setTime(new Date(generalParamEntries[i].getParamValue()));
					nextDate.setTime(new Date(generalParamEntries[i].getParamValue()));
					break;
				}
			}
			log.append("\nCurrent Application Date : "+newDateFormat.format(currentDate.getTime()));
		*/
			
			generalParamEntry = generalParam.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			System.out.println("generalParamEntry::::===="+generalParamEntry);
			
			if(generalParamEntry!=null){
				currentDate.setTime(new Date(generalParamEntry.getParamValue()));
				date.setTime(new Date(generalParamEntry.getParamValue()));
				nextDate.setTime(new Date(generalParamEntry.getParamValue()));
				log.append("\nCurrent Application Date : "+newDateFormat.format(currentDate.getTime()));
			}else{
				throw new Exception("Unable to retrieve application date");	
			}
			DefaultLogger.debug(this, "currentDate::::"+newDateFormat.format(currentDate.getTime()));
			DefaultLogger.debug(this, "date::::"+newDateFormat.format(date.getTime()));
			DefaultLogger.debug(this, "nextDate::::"+newDateFormat.format(nextDate.getTime()));
			
			System.out.println("currentDate::::"+newDateFormat.format(currentDate.getTime()));
			System.out.println("date::::"+newDateFormat.format(date.getTime()));
			System.out.println("nextDate::::"+newDateFormat.format(nextDate.getTime()));
		
		}
		catch (Exception exception) {
			DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : getCurrentDate())" );
			exception.printStackTrace();
		}
	}
	public StringBuffer performEOD() {
		System.out.println("<<<<<<<<Inside performEOD() >>>>>>");
		clearLogs();
		IGeneralParamEntry generalParamEntry = generalParam.getGeneralParamEntryByParamCodeActual("USER_HAND_OFF");
		String userHandOff = generalParamEntry.getParamValue();
		boolean status = false;
		System.out.println("<<<<<<<<Inside performEOD() >>>>>>"+userHandOff);
		if (userHandOff == null || "N".equalsIgnoreCase(userHandOff.trim())) {
			log.append(PropertyManager.getValue("error.string.eod.systemHandOff"));
			status = false;
			logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("eodFailFile");
			generateEODReport();
		}
		else {
			boolean eodProcessExecuted = false; 
			try{
			List eodActivites = eodStatusProxy.findEODActivities();
			Iterator iter = eodActivites.iterator();
			log.append("\n----- EOD Begin (System Date: "+Calendar.getInstance().getTime().toString()+")-----");
			getPreEODData();
			IEODStatus eodStatus;
			ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
			IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
				
			
			IGeneralParamEntry generalParamEntry2 = generalParam.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			Date date = new Date(generalParamEntry2.getParamValue());
			Calendar currDate = Calendar.getInstance();
			currDate.setTime(date);

			Calendar nextdate = Calendar.getInstance();
			nextdate.setTime(date);
			
			Calendar intrDayLimitNextdate = Calendar.getInstance();
			intrDayLimitNextdate.setTime(date);
			boolean intrDayLimit = false;
			
			nextdate.add(Calendar.DATE, 1);
			while (HolidayHelper.isHoliday(holidayList, nextdate)) {
				nextdate.add(Calendar.DATE, 1);
				intrDayLimit = true;
			}
			
			IGeneralParamEntry generalParamEntry3 = generalParam.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			Date curwdate = new Date(generalParamEntry2.getParamValue());
			Calendar currwDate = Calendar.getInstance();
			currwDate.setTime(date);
			currwDate.add(Calendar.DATE, 1);
			
			Calendar immdNextWorkingDate = Calendar.getInstance();
			immdNextWorkingDate.setTime(date);
			immdNextWorkingDate.add(Calendar.DATE, 1);
			while (HolidayHelper.isHoliday(holidayList, immdNextWorkingDate)) {
				immdNextWorkingDate.add(Calendar.DATE, 1);
			}
			DefaultLogger.error(this,immdNextWorkingDate.getTime());
			
			//collateralDAO.executeFCUBSActivities(currwDate.getTime(),immdNextWorkingDate.getTime());///ADD TO EOD *********** INSERT NEW ACTIVITY TO cms_eod_status
			
			/*if(intrDayLimit == true) {
				intrDayLimitNextdate = nextdate;
				intrDayLimitNextdate.add(Calendar.DATE, -1);
			updateIntraDayLimitAvailable(currDate.getTime(),intrDayLimitNextdate.getTime());
			}else {
				updateIntraDayLimitAvailable(currDate.getTime(),nextdate.getTime());	///ADD TO EOD *********** INSERT NEW ACTIVITY TO cms_eod_status
			}*/
			
			
			
			System.out.println("Going to Execute EOD Activities .. Start*******nextdate : "+nextdate);
			while (iter.hasNext()) {
				eodStatus = (IEODStatus)iter.next();
				if (EODConstants.FORCELOGOUT.equalsIgnoreCase(eodStatus.getActivity())) {
					forceLogOutUsers(eodStatus);
				}else if (EODConstants.LADINFO.equalsIgnoreCase(eodStatus.getActivity())) {
					updateLADDetails(eodStatus);
				}else if (EODConstants.GENBASEL.equalsIgnoreCase(eodStatus.getActivity())) {
					generateDailyBaselReport(eodStatus);
				}else if (EODConstants.GENMONTHLYBASEL.equalsIgnoreCase(eodStatus.getActivity())) {
					generateMonthlyBaselReport(eodStatus);
				}else if (EODConstants.GENEMAIL.equalsIgnoreCase(eodStatus.getActivity())) {
				//	eodStatus.setStatus(EODConstants.STATUS_NA);
				//	if (!eodStatus.getStatus().equalsIgnoreCase(EODConstants.STATUS_NA))
					generateEmailNotification(eodStatus);
				}else if (EODConstants.DORMANTUSERS.equalsIgnoreCase(eodStatus.getActivity())) {
					manageDormantUsers(eodStatus);
				}else if (EODConstants.EXECRECEIVEDSTMTPROC.equalsIgnoreCase(eodStatus.getActivity())) {
					eodStatus = collateralDAO.executeReceivedStatementProc(eodStatus);
					updateEODStatus(eodStatus.getStatus(), "7");
				}else if (EODConstants.EXECFDDELBKUPPROC.equalsIgnoreCase(eodStatus.getActivity())) {
					eodStatus = collateralDAO.executeFdDeletedBackupProc(eodStatus);
					updateEODStatus(eodStatus.getStatus(), "8");
				}else if (EODConstants.EXECFACCHARGEIDPROC.equalsIgnoreCase(eodStatus.getActivity())) {
					eodStatus = collateralDAO.executeFacChargeIdProc(eodStatus);
					updateEODStatus(eodStatus.getStatus(), "9");
				}else if (EODConstants.EXECINACTIVEFDPROC.equalsIgnoreCase(eodStatus.getActivity())) {
					eodStatus = collateralDAO.executeSPInactiveFdProc(eodStatus);
					updateEODStatus(eodStatus.getStatus(), "10");
				}else if (EODConstants.EXECFILEUPDCLEANUP.equalsIgnoreCase(eodStatus.getActivity())) {
					eodStatus = fileUploadJdbc.executeFileUploadCleanup(eodStatus);
					updateEODStatus(eodStatus.getStatus(), "11");
				}else if (EODConstants.EXEFCUBSACTIVITY.equalsIgnoreCase(eodStatus.getActivity())) {
					System.out.println("performEOD() => EOD Activity=>EXECUTE FCUBS ACTIVITY");
					eodStatus = collateralDAO.executeFCUBSActivities(currwDate.getTime(),immdNextWorkingDate.getTime(),eodStatus);
					System.out.println("After EXECUTE FCUBS ACTIVITY Going for updateEODStatus.. eodStatus=>"+eodStatus.getStatus());
					updateEODStatus(eodStatus.getStatus(), "13");
				}
				else if (EODConstants.EXEINTRALMTAVAILABLEACT.equalsIgnoreCase(eodStatus.getActivity())) {
					System.out.println("performEOD() => EOD Activity=>UPDATE INTRADAY LIMIT AVAILABLE ACTIVITY");
					if(intrDayLimit == true) {
					//	intrDayLimitNextdate = nextdate;
						intrDayLimitNextdate =(Calendar) nextdate.clone();
						intrDayLimitNextdate.add(Calendar.DATE, -1);
						System.out.println("EndOfDayBatchServiceimpl *********386**************intrDayLimitNextdate=> "+intrDayLimitNextdate+" nextdate : "+nextdate);
						eodStatus = updateIntraDayLimitAvailable(currDate.getTime(),intrDayLimitNextdate.getTime(),eodStatus);
					}else {
						eodStatus = updateIntraDayLimitAvailable(currDate.getTime(),nextdate.getTime(),eodStatus);	///ADD TO EOD *********** INSERT NEW ACTIVITY TO cms_eod_status
					}
					System.out.println("After UPDATE INTRADAY LIMIT AVAILABLE ACTIVITY Going for updateEODStatus.. eodStatus=>"+eodStatus.getStatus());
					updateEODStatus(eodStatus.getStatus(), "14");
				}
//				else if (EODConstants.Purge_LAD_details.equalsIgnoreCase(eodStatus.getActivity())) {
//				purgeLADData(eodStatus,"12");
//				}
				
				performLADDataPurge();
			}
			System.out.println("After Execute EOD Activities .. ******nextdate : "+nextdate);
			//Uma Khot: 19/02/2016 :Refreshing MV as User Admin Report Taking time for execution.
//			getRefreshMvDao().refreshMvForUserAdminReport();/// Scheduler Added 
			
//			getRefreshMvDao().refreshMvForEwsStockDeferral(); //  EWsStockDeferral/// Scheduler Added 

//			getRefreshMvDao().refreshMvForAuditTrailReportDB();
			//RamRatingDetails ramRatingDetails = new RamRatingDetails();
			
			//ramRatingDetails.uploadRamRatingDetails();
			
			
			//Uma Khot: 19/02/2016 :Refreshing MV as User Admin Report Taking time for execution.
			
//			resetDiarySequence();/// Scheduler Added 
			//updateIntraDayLimitAvailable();
//			IGeneralParamEntry generalParamEntry3 = generalParam.getGeneralParamEntryByParamCodeActual("EOY_CLEAN_UP");
//			if(null!=generalParamEntry3){
//			String eoyCleanUp = generalParamEntry3.getParamValue();
//			if(null!=eoyCleanUp && "Y".equalsIgnoreCase(eoyCleanUp)){
//				generateRecurrentDocsForNextYearCleanUp();
//			}
//			}
			/*
				 * EOD Issue : Preventive changes ###################################
				 * Below code has been moved to above condition and activities have been added to DATABASE CMS_EOD_STATUS table.
				 * 
				 * // Adding DP Procedure in EOD job 22-Oct-2012 By Abhijit R Start
			ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
			
			IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			

			collateralDAO.executeReceivedStatementProc();

			// End
			// Adding FD Procedure in EOD job 30-Oct-2012 By Sachin Patil Start
			collateralDAO.executeFdDeletedBackupProc();
			// End
			

			// Adding CHARGE ID Procedure in EOD job 20-MAY-2014 By Sachin Patil Start
			collateralDAO.executeFacChargeIdProc();
			// End
			
			// Adding Update query call for inactive FD's on 15-JUn-2013 By Sachin Patil Start
			collateralDAO.executeSPInactiveFdProc();
			
			// To clear upload data before 7 days
			fileUploadJdbc.executeFileUploadCleanup();*/

			//Added to clean Interfacelog table data except latest 7 days data.
//			collateralDAO.executeCmsInterfaceLogBackupProc();/// Scheduler Added
			
			//clean facility and liability intefacelog table data except last 7 days.
			
//			collateralDAO.executeFacilityIntLogBkpProc();/// Scheduler Added
//			collateralDAO.executeLiabilityIntLogBkpProc();/// Scheduler Added
			
			//Added to clean IFSCCODE_INTERFACE_LOG table data except latest 7 days data.
//			collateralDAO.executeIfscCodeInterfaceLogBackupProc();/// Scheduler Added
			
			//if date is end of year, then EOY will be called. 17-Apr-2012
			/*IGeneralParamEntry generalParamEntry2 = generalParam.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			Date date = new Date(generalParamEntry2.getParamValue());
			Calendar currDate = Calendar.getInstance();
			currDate.setTime(date);

			Calendar nextdate = Calendar.getInstance();
			nextdate.setTime(date);
			
			Calendar intrDayLimitNextdate = Calendar.getInstance();
			intrDayLimitNextdate.setTime(date);
			boolean intrDayLimit = false;
			
			nextdate.add(Calendar.DATE, 1);
			while (HolidayHelper.isHoliday(holidayList, nextdate)) {
				nextdate.add(Calendar.DATE, 1);
				intrDayLimit = true;
			}
			
			IGeneralParamEntry generalParamEntry3 = generalParam.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			Date curwdate = new Date(generalParamEntry2.getParamValue());
			Calendar currwDate = Calendar.getInstance();
			currwDate.setTime(date);
			currwDate.add(Calendar.DATE, 1);
			
			Calendar immdNextWorkingDate = Calendar.getInstance();
			immdNextWorkingDate.setTime(date);
			immdNextWorkingDate.add(Calendar.DATE, 1);
			while (HolidayHelper.isHoliday(holidayList, immdNextWorkingDate)) {
				immdNextWorkingDate.add(Calendar.DATE, 1);
			}
			DefaultLogger.error(this,immdNextWorkingDate.getTime());
			
			collateralDAO.executeFCUBSActivities(currwDate.getTime(),immdNextWorkingDate.getTime());
			
			if(intrDayLimit == true) {
				intrDayLimitNextdate = nextdate;
				intrDayLimitNextdate.add(Calendar.DATE, -1);
			updateIntraDayLimitAvailable(currDate.getTime(),intrDayLimitNextdate.getTime());
			}else {
				updateIntraDayLimitAvailable(currDate.getTime(),nextdate.getTime());	
			}*/
			//----------FCUBS Data Log Back up table and other activities---------------//
			Calendar immdNextDate = Calendar.getInstance();
			immdNextDate.setTime(date);
			immdNextDate.add(Calendar.DATE, 1);
		//	collateralDAO.executeFCUBSActivities(immdNextDate.getTime(),nextdate.getTime());

			DefaultLogger.debug(this,nextdate.getTime());
			
			//CR:FCUBS handoff
			if (currDate.get(Calendar.MONTH) != nextdate.get(Calendar.MONTH)) {
				
				SimpleDateFormat d=new SimpleDateFormat("dd-MMM-yyyy");
				String format = d.format(date);
				getBaselDao().updateMonthendDateAndFccFlag(format);
				removeFcUbsFileFromLocal();
			}
			
			//For PSR CR
//			collateralDAO.executePSRActivities(immdNextDate.getTime(),nextdate.getTime());/// Scheduler Added
			
//			System.out.println(" EndOfDaybatchServiceImpl Before going for getRefreshMvDao().refreshMvForCustomerWiseSecurityReport().");
//			getRefreshMvDao().refreshMvForCustomerWiseSecurityReport(); //  Custome Wise Security report MV /// Scheduler Added
//			System.out.println(" EndOfDaybatchServiceImpl After getRefreshMvDao().refreshMvForCustomerWiseSecurityReport() method ... ");
			
			
			// Start:Uma Khot: Added for CAM QUARTER ACTIVITY CR
			int month=currDate.get(Calendar.MONTH);
			
			if(Calendar.DECEMBER == month || Calendar.MARCH == month || Calendar.JUNE == month || Calendar.SEPTEMBER == month){
					if(month ==nextdate.get(Calendar.MONTH)){
						DefaultLogger.debug(this,"NOT a Quarter End");
						System.out.println("NOT a Quarter End");
					}
					else{
						DefaultLogger.debug(this,"It is a Quarter End");
						System.out.println("It is a Quarter End");
					//	DefaultLogger.debug(this,"Started Executing CAM Quarter Extract");
						this.performCamQuarterActivity();
						//DefaultLogger.debug(this,"CAM Quarter Extract Completed.");
					}
				}
			//End: Uma Khot: Added for CAM QUARTER ACTIVITY CR
			System.out.println("endofdayserviveimpl.java **********546*********nextdate : "+nextdate+", currDate : "+currDate);
			if (currDate.get(Calendar.YEAR) == nextdate.get(Calendar.YEAR)) {
				DefaultLogger.debug(this,"-1- Same Year ---");
				System.out.println("-1- Same Year ---");

			}else{
				DefaultLogger.debug(this,"-1- diff Year ---");
				System.out.println("Before calling performEndOfYearActivities Function");
				
				this.performEndOfYearActivities();
				System.out.println("After calling performEndOfYearActivities Function");
			}
			//End of EOY activity

//			this.finalizeEOD();
			status = false;
			eodProcessExecuted = true;
			}catch (Exception e){
				DefaultLogger.error(this,"Method performEOD() >> else condition >>> Error encountered!! "+e.getMessage());
				e.printStackTrace();
			}finally{
				DefaultLogger.debug(this,"inside Finally block >> performEOD()");
				finalizeEOD(eodProcessExecuted);
			}
		}		
		//	logfileName = PropertyManager.getValue("eodLogPath")+"EndOfDay"+newDateFormat.format(Calendar.getInstance().getTime())+"_"+Calendar.getInstance().getTimeInMillis();
		System.out.println("<<<<<<<<Inside performEOD() end>>>>>>");
		return log;
	}
	
//private void purgeLADData(IEODStatus eodStatus, String string) {
	private void performLADDataPurge() {
	ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
	collateralDAO.purgeLadData();
	}

//Start: Uma Khot: Added for CAM QUARTER ACTIVITY CR
private void performCamQuarterActivity() {
		String flag="";
		 try {
			 DefaultLogger.debug(this, "generateCamQuarterExtract process started.");
			 System.out.println("generateCamQuarterExtract process started.");
			flag=getCamQuarterDao().generateCamQuarterExtract();
			DefaultLogger.debug(this, "generateCamQuarterExtract process completed.");
			System.out.println("generateCamQuarterExtract process completed.");
		 if("success".equals(flag)){
			 DefaultLogger.debug(this, "generateCamDetailExtract process started.");
			 System.out.println("generateCamDetailExtract process started.");
			 flag=getCamQuarterDao().generateCamDetailExtract();
			 DefaultLogger.debug(this, "generateCamDetailExtract process completed.");
			 System.out.println("generateCamDetailExtract process completed.");
		 }
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				DefaultLogger.debug(this, e.getMessage());
			}
	}
//End: Uma Khot: Added for CAM QUARTER ACTIVITY CR





	/*public StringBuffer performAdfRbi() {
		clearLogs();

			List eodActivites = eodStatusProxy.findAdfRbiActivities();
			Iterator iter = eodActivites.iterator();

			getPreEODData();
			IAdfRbiStatus eodStatus;
			while (iter.hasNext()) {
				eodStatus = (IAdfRbiStatus)iter.next();
				if (EODConstants.SP_CMS_Recp_Col_Limit_Link.equalsIgnoreCase(eodStatus.getActivity())) {
					recpColLimitLink(eodStatus);
				}
				else if (EODConstants.SP_CMS_RECP_COL_PRODUCT_LINK.equalsIgnoreCase(eodStatus.getActivity())) {
					recpColProductLink(eodStatus);
				}
				else if (EODConstants.SP_cms_Recp_Collateral.equalsIgnoreCase(eodStatus.getActivity())) {
					recpCollateral(eodStatus);
				}
				else if (EODConstants.SP_CMS_RECP_CUST_Legal.equalsIgnoreCase(eodStatus.getActivity())) {
					recpCustLegal(eodStatus);
				}
				else if (EODConstants.SP_cms_Recp_Customer.equalsIgnoreCase(eodStatus.getActivity())) {
					recpCustomer(eodStatus);
				}
				else if (EODConstants.SP_cms_Recp_PARTY.equalsIgnoreCase(eodStatus.getActivity())) {
					recpParty(eodStatus);
				}
			}


			this.finalizeAdfRbi();

		return log;
	}*/


	/*public StringBuffer performAdfRbi() {
		clearLogs();
		getCurrentDate();
		recpRbiAdfAllProcedure();
		// Added by Abhijit R for managing RBI ADF JOB Status
		while(true){
		String status[]=statusJdbc.getStatusEODActivities();

		if(!EODConstants.STATUS_PENDING.equalsIgnoreCase(status[0])
				&& !EODConstants.STATUS_PENDING.equalsIgnoreCase(status[1])
				&& !EODConstants.STATUS_PENDING.equalsIgnoreCase(status[2])
				&& !EODConstants.STATUS_PENDING.equalsIgnoreCase(status[3])
				&& !EODConstants.STATUS_PENDING.equalsIgnoreCase(status[4])
				&& !EODConstants.STATUS_PENDING.equalsIgnoreCase(status[5])){
			//System.out.print("IN IF ");
			break;	
		}else{
			//System.out.print("IN else ");
		}


		}


		// End by Abhijit R 
		this.finalizeAdfRbi();

		return log;
	}*/

	public StringBuffer performAdfRbi() {
		clearLogs();
		final List eodActivites = eodStatusProxy.findAdfRbiActivities();
		 
		

		Iterator iter = eodActivites.iterator();
		
		IAdfRbiStatus eodStatus;
		while (iter.hasNext()) {
			eodStatus = (IAdfRbiStatus)iter.next();
			System.out.println("-------------Thread 1-----4---------"+eodStatus.getActivity()+":"+eodStatus.getStatus());
			if (EODConstants.SP_CMS_Recp_Col_Limit_Link.equalsIgnoreCase(eodStatus.getActivity())) {
				recpColLimitLink(eodStatus);
			}
			else if (EODConstants.SP_CMS_RECP_COL_PRODUCT_LINK.equalsIgnoreCase(eodStatus.getActivity())) {
				recpColProductLink(eodStatus);
			}
			else if (EODConstants.SP_cms_Recp_Collateral.equalsIgnoreCase(eodStatus.getActivity())) {
				recpCollateral(eodStatus);
			}
			else if (EODConstants.SP_cms_Recp_Customer.equalsIgnoreCase(eodStatus.getActivity())) {
				recpCustomer(eodStatus);
			}
			else if (EODConstants.SP_cms_Recp_PARTY.equalsIgnoreCase(eodStatus.getActivity())) {
				recpParty(eodStatus);
			}
			else if (EODConstants.SP_CMS_RECP_CUST_Legal.equalsIgnoreCase(eodStatus.getActivity())) {
				recpCustLegal(eodStatus);
			}
			else if (EODConstants.Fincon_Recp_Party.equalsIgnoreCase(eodStatus.getActivity())) {
				finconParty(eodStatus);
			}
			else if (EODConstants.Fincon_Recp_Customer.equalsIgnoreCase(eodStatus.getActivity())) {
				finconCustomer(eodStatus);
			}
			else if (EODConstants.Fincon_Recp_Cust_Legal.equalsIgnoreCase(eodStatus.getActivity())) {
				finconCustLegal(eodStatus);
			}
			else if (EODConstants.Fincon_Recp_Col_Prod_Link.equalsIgnoreCase(eodStatus.getActivity())) {
				finconColProductLink(eodStatus);
			}
			else if (EODConstants.Fincon_Recp_Col_Limit_link.equalsIgnoreCase(eodStatus.getActivity())) {
				finconColLimitLink(eodStatus);
			}
			else if (EODConstants.Fincon_Recp_Collateral.equalsIgnoreCase(eodStatus.getActivity())) {
				finconCollateral(eodStatus);
			}
			
			// Added By Dayananda Laishram on  FINCON_CR2 10/06/2015 || Starts
			else if (EODConstants.Fincon_Cust_Wise_Sec.equalsIgnoreCase(eodStatus.getActivity())) {
				finconCustWiseSec(eodStatus);
			}
			else if (EODConstants.Fincon_Master_Industry.equalsIgnoreCase(eodStatus.getActivity())) {
				finconMasterIndustry(eodStatus);
			}
			
			//Added By Dayananda Laishram on  FINCON_CR2 10/06/2015 || Ends
		}
		

	
		
		
		/*Thread t1 =	new Thread(new Runnable() {
			public void run() {
				System.out.println("-------------Thread 1------1--------");

				
				System.out.println("-------------Thread 1-----2---------");
				Iterator iter = eodActivites.iterator();
				System.out.println("-------------Thread 1-------3-------");

				IAdfRbiStatus eodStatus;
				while (iter.hasNext()) {
					eodStatus = (IAdfRbiStatus)iter.next();
					System.out.println("-------------Thread 1-----4---------"+eodStatus.getActivity()+":"+eodStatus.getStatus());
					if (EODConstants.SP_CMS_Recp_Col_Limit_Link.equalsIgnoreCase(eodStatus.getActivity())) {
						recpColLimitLink(eodStatus);
					}
					else if (EODConstants.SP_CMS_RECP_COL_PRODUCT_LINK.equalsIgnoreCase(eodStatus.getActivity())) {
						recpColProductLink(eodStatus);
					}
					else if (EODConstants.SP_cms_Recp_Collateral.equalsIgnoreCase(eodStatus.getActivity())) {
						recpCollateral(eodStatus);
					}
					else if (EODConstants.SP_cms_Recp_Customer.equalsIgnoreCase(eodStatus.getActivity())) {
						recpCustomer(eodStatus);
					}
					else if (EODConstants.SP_cms_Recp_PARTY.equalsIgnoreCase(eodStatus.getActivity())) {
						recpParty(eodStatus);
					}
					else if (EODConstants.SP_CMS_RECP_CUST_Legal.equalsIgnoreCase(eodStatus.getActivity())) {
						recpCustLegal(eodStatus);
					}
					
					
				}
				

			}
		});*/
		//By Sachin on 25-04-13
		/*Thread t2 =	new Thread(new Runnable() {
			public void run() {
				System.out.println("-------------Thread 2------1--------");

				
				System.out.println("-------------Thread 2-----2---------");
				Iterator iter = eodActivites.iterator();
				System.out.println("-------------Thread 2-------3-------");

				IAdfRbiStatus eodStatus;
				while (iter.hasNext()) {
					eodStatus = (IAdfRbiStatus)iter.next();
					System.out.println("-------------Thread 2-----4---------"+eodStatus.getActivity()+":"+eodStatus.getStatus());
				 if (EODConstants.SP_cms_Recp_PARTY.equalsIgnoreCase(eodStatus.getActivity())) {
						recpParty(eodStatus);
					}
				}

			}
		});
		
		  Thread t3 = new Thread(new Runnable() {
			public void run() {
				System.out.println("-------------Thread 3--------------");
				
				Iterator iter = eodActivites.iterator();


				IAdfRbiStatus eodStatus;
				while (iter.hasNext()) {
					eodStatus = (IAdfRbiStatus)iter.next();
					if (EODConstants.SP_CMS_RECP_CUST_Legal.equalsIgnoreCase(eodStatus.getActivity())) {
						recpCustLegal(eodStatus);
						System.out.println("-------------start Finalize---after -- t3---------");
						finalizeAdfRbi();
						System.out.println("-------------end Finalize--------------");
					}
				}
			}

		});*/
		
		


		//Runnable t1 = new RBIADFThread1();
	//	t1.start();

		//Runnable t2 = new RBIADFThread2();
	
		//By Sachin on 25-04-13	  t2.start();
		//By Sachin on 25-04-13    t3.start();
		
	/*	DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			
		
     // Added by Abhijit R for managing RBI ADF JOB Status
		while(true){
			String sql = "select status from CMS_ADF_RBI_STATUS";
			dbUtil.setSQL(sql);
			String status[]=statusJdbc.getStatusEODActivities(dbUtil);

			if(!EODConstants.STATUS_PENDING.equalsIgnoreCase(status[0])
					&& !EODConstants.STATUS_PENDING.equalsIgnoreCase(status[1])
					&& !EODConstants.STATUS_PENDING.equalsIgnoreCase(status[2])
					&& !EODConstants.STATUS_PENDING.equalsIgnoreCase(status[3])
					&& !EODConstants.STATUS_PENDING.equalsIgnoreCase(status[4])
					&& !EODConstants.STATUS_PENDING.equalsIgnoreCase(status[5])){
				//System.out.print("IN IF ");
				finalizeAdfRbi();
				break;	
			}else{
				//System.out.print("IN else ");
			}

			
		}
		} catch (DBConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			if(dbUtil != null){
			try {
				dbUtil.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		*/
		//By Sachin on 25-04-13
		/*System.out.println("-------------start Finalize----before--t3--------");
		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println("-------------start Finalize---after -----------");
		finalizeAdfRbi();
		System.out.println("-------------end Finalize--------------");
		
		return log;
	}
	
	// Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD || Starts
	public Object performBaselUpdateReport()
	{
		
		boolean activityPerformedCheck = false;
		activityPerformedCheck = getBaselDao().getActivityPerformed() ;
		Object status = "\r\nBasel Report Generation for this month is already performed !";
		File path =null;
		Calendar applicationDateLocal = Calendar.getInstance();
		clearLogs();
		generalParamEntry = generalParam.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
		System.out.println("generalParamEntry in basel report : "+generalParamEntry);
		ArrayList resultList=new ArrayList();
		
		if(generalParamEntry!=null){
			applicationDateLocal.setTime(new Date(generalParamEntry.getParamValue()));
			log.append("\r\n Job running Current Application Date : "+newDateFormat.format(applicationDateLocal.getTime()));
			log.append("\r\n");
		}
		
	//	boolean fileExistCheck = false;
		
		//Uma:For UBS NCB Migration CR 
		boolean fileExistCheckFccncb=false;
		List<File> baselFileList=new ArrayList<File>();
		
		try
		{
			if(activityPerformedCheck)
			{
				
				List<ConcurrentMap<String, String>> arrayListMonthlyBaselP2 = getBaselDao().catcheDataFromMonthlyBaselP2();
				//ProcessDataFile dataFile = new ProcessDataFile();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				ResourceBundle bundle = ResourceBundle.getBundle("ofa");
				
				//Uma:For UBS NCB Migration CR 
				String fccncbFilename=PropertyManager.getValue("basel.report.file.from.fccncb");
				String processFccncbFile = PropertyManager.getValue("process.fccncb.file");
				boolean processFccncbFileFlag;
				if("true".equalsIgnoreCase(processFccncbFile)){
					processFccncbFileFlag=true;
				}else{
					processFccncbFileFlag=false;
				}
				File baselFile = null;
				String checkDate=df.format(applicationDateLocal.getTime());
				path=new File(bundle.getString(BASEL_UPDATE_REPORT_DIRECTORY));
				System.out.println("---------1-----------: "+path);
				if(path!=null && path.exists() && path.isDirectory())
				{
					System.out.println("---------2-----------: "+path);
					File list[]=path.listFiles();
					if(list!=null && list.length>0)
					{
						System.out.println("---------3-----------: "+list);
						for(int i=list.length-1;i>=0;i--)
						{ 
							System.out.println("---------4-----------: "+list);
							File sortFile=list[i];
							System.out.println("---------5-----------: "+sortFile);
							if(sortFile!=null && sortFile.isFile())
							{
								System.out.println("---------6-----------: "+sortFile);
								//	if(sortFile.getName().contains(BASEL_REPORT_FILE_NAME+checkDate) && (sortFile.getName().endsWith(".csv") ||  sortFile.getName().endsWith(".CSV")))
								//Uma Khot:Removed checking date part in upload file as required by bank
							/*	if(sortFile.getName().startsWith(BASEL_REPORT_FILE_NAME) && (sortFile.getName().endsWith(".csv") ||  sortFile.getName().endsWith(".CSV"))){
									//	System.out.println("---------7-----------: "+BASEL_REPORT_FILE_NAME+checkDate);
										System.out.println("---------7-----------: "+BASEL_REPORT_FILE_NAME);
										baselFile = list[i];
										if(baselFile.isFile())
										{
											System.out.println("---------8-----------: "+baselFile);
											fileExistCheck = true;
											
											baselFileList.add(baselFile);
											
											System.out.println("----9---Process file Name is: "+baselFile);
											
										}
								}
								//Uma:For UBS NCB Migration CR 
								else */ if(sortFile.getName().startsWith(fccncbFilename) && (sortFile.getName().endsWith(".csv") ||  sortFile.getName().endsWith(".CSV")) && processFccncbFileFlag){
									//	System.out.println("---------7-----------: "+BASEL_REPORT_FILE_NAME+checkDate);
										System.out.println("---------7.1-----------: "+sortFile.getName());
										baselFile = list[i];
										if(baselFile.isFile())
										{
											System.out.println("---------8.1-----------: "+baselFile);
											fileExistCheckFccncb = true;
											
											baselFileList.add(baselFile);
											System.out.println("----9.1---Process file Name is: "+baselFile);
											
										}
								}
//								else{
//									status="\r\nUpdated File is not available in the FTP path: "+path;
//									System.out.println("---------10-----------: "+status);
//									fileExistCheck = false;
//									log.append("\r\n");
//									log.append("\r\n Status ::: "+status);
//									log.append("\r\n");
//									logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("baselUpdateReportFailFile");
//								}
							}
							//Uma Khot:start:Added to handle if no file is present in directory
//							else{
//								status="Basel File is not present at location: "+path;
//								System.out.println("---------10.1-----------: "+status);
//								fileExistCheck = false;
//								log.append("\r\n");
//								log.append("\r\n Status ::: "+status);
//								log.append("\r\n");
//								logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("baselUpdateReportGenerationNoFile");
//							}
							
							//Uma Khot:end:Added to handle if no file is present in directory
						}
						
						//Uma:For UBS NCB Migration CR 
				/*		if(!fileExistCheck && (!fileExistCheckFccncb && processFccncbFileFlag)){
							status="\r\nUpdated Files i.e. "+BASEL_REPORT_FILE_NAME+  " and  "+fccncbFilename+" are not available in the FTP path: "+path;
							System.out.println("---------10-----------: "+status);
							log.append("\r\n");
							log.append("\r\n Status ::: "+status);
							log.append("\r\n");
							logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("baselUpdateReportFailFile");
						}
						else if(!fileExistCheck){
							status="\r\nUpdated File i.e. "+BASEL_REPORT_FILE_NAME+" is not available in the FTP path: "+path;
							System.out.println("---------10-----------: "+status);
							log.append("\r\n");
							log.append("\r\n Status ::: "+status);
							log.append("\r\n");
							logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("baselUpdateReportFailFile");
						}else */ if(!fileExistCheckFccncb && processFccncbFileFlag){
							status="\r\nUpdated File i.e. "+fccncbFilename +" is not available in the FTP path: "+path;
							System.out.println("---------10-----------: "+status);
							log.append("\r\n");
							log.append("\r\n Status ::: "+status);
							log.append("\r\n");
							logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("baselUpdateReportFailFile");
						}
					}
					else{
						
						if(processFccncbFileFlag){
				
						//status="\r\n Both the Files i.e. "+BASEL_REPORT_FILE_NAME+  " and  "+fccncbFilename+" are not available in the FTP path: "+path;
							
						status="\r\n File "+fccncbFilename+" is not available in the FTP path: "+path;
						System.out.println("---------11-----------: "+status);
					//	fileExistCheck = false;
						fileExistCheckFccncb = false;
						log.append("\r\n");
						log.append("\r\nStatus ::: "+status);
						log.append("\r\n");
						logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("baselUpdateReportFailFile");
						} /*else{
							status="\r\n the File i.e. "+BASEL_REPORT_FILE_NAME+ " is not available in the FTP path: "+path;
							System.out.println("---------11-----------: "+status);
							fileExistCheck = false;
							//fileExistCheckFccncb = false;
							log.append("\r\n");
							log.append("\r\nStatus ::: "+status);
							log.append("\r\n");
							logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("baselUpdateReportFailFile");	
						} */
					}
				}
				else
				{
					status="\r\n FTP file path: "+path+" doest not exist !";
					System.out.println("---------12-----------: "+status);
					log.append("\r\n");
					log.append("\r\n Status ::: "+status);
					log.append("\r\n");
					logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("baselUpdateReportFailFile");
				}
	
//				if(!processFccncbFileFlag){
//					//If "process.fccncb.file" parameter is not true then process only one file i.e UBS-LIMIT
//					fileExistCheckFccncb=true;
//				}
			//	if(fileExistCheck && fileExistCheckFccncb)
				if(fileExistCheckFccncb)
				{
				//	System.out.println("---------13-[fileExistCheck] and fileExistCheckFccncb ----------: "+fileExistCheck+" and "+fileExistCheckFccncb);
					
					System.out.println("---------13- fileExistCheckFccncb ----------: "+fileExistCheckFccncb);
					
					//Uma:For UBS NCB Migration CR 
					if(null!=baselFileList && baselFileList.size()>0){
					for(int i=0; i< baselFileList.size(); i++){
					//resultList = dataFile.processFileUpload(baselFile,BASEL_UPDATE_UPLOAD);
					//resultList=new ArrayList();
					ProcessDataFile dataFile = new ProcessDataFile();
					resultList = dataFile.processFileUpload(baselFileList.get(i),BASEL_UPDATE_UPLOAD);
					status = getBaselDao().insertBaselUpdateDetails(resultList,arrayListMonthlyBaselP2);
					log.append("\r\n");
					log.append("\r\n Uploaded UBS-LIMIT file is: "+baselFileList.get(i));
					log.append("\r\n");
					log.append("\r\n Status ::: "+status);
					log.append("\r\n");
					logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("baselUpdateReportSuccessFile");
					}
					}
					try{
						DefaultLogger.debug(this,"-Start - Basel File Report Generation");
						System.out.println("-Start - Basel File Report Generation");
						GenerateReportCmd report  = new GenerateReportCmd();
						report.generateBaselOnEOD();
						System.out.println("-End - Basel File Report Generation");
					}catch (Exception e) {
						System.out.println("-Error in - Basel File Report Generation");
						e.printStackTrace();
					}
				}

			}
	
			else
			{
				System.out.println("---------14-----------: "+status);
				log.append("\r\n");
				log.append("\r\n Status ::: "+status);
				log.append("\r\n");
				logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("baselUpdateReportNoGenerationNoFile");
			}
		
		}catch(Exception exp){
			//fileExistCheck=false;
			
			fileExistCheckFccncb=false;
			
			log.append("\r\n");
			status=exp;
			log.append("\r\n Status ::: "+status);
			log.append("\r\n");
			logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("baselUpdateReportFailFile");
			System.out.println("---------15-----------: "+exp);
			exp.printStackTrace();
		}finally{
			
			generateEODReport();
			System.out.println("---------16 [generateEODReport]-----------: ");
		}
		System.out.println("---------17 [Return status]-----------: "+status);	
		return status;
	} 
			
	// Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD || Ends

	
	private void finalizeEOD(boolean eodProcessExecuted) {
		List eodActivites = eodStatusProxy.findEODActivities();
		Iterator iter = eodActivites.iterator();
		IEODStatus eodStatus;
		boolean status = true;
		String edodAppDateStatus = "";
		
		try {
			edodAppDateStatus = performDateChange();
			System.out.println("performDateChange() => edodAppDateStatus =>"+edodAppDateStatus);
			updateEODStatus(edodAppDateStatus, "15");
		}catch(Exception e) {
			System.out.println("Exception caught in finalizeEOD=> performDateChange() Method => edodAppDateStatus=>"+edodAppDateStatus+" **  e=>"+e);
			e.printStackTrace();
		}
		
		
		/*clearLogs();
		log.append("\n----- EOD Begin (System Date: "+Calendar.getInstance().getTime().toString()+")-----");*/
		log.append("\nCurrent Application Date : "+newDateFormat.format(currentDate.getTime()));
		while (iter.hasNext()) {
			eodStatus = (IEODStatus)iter.next();
			log.append("\n"+eodStatus.getActivity()+" : "+eodStatus.getStatus());
			System.out.println("\n::::::::"+eodStatus.getActivity()+" : "+eodStatus.getStatus());
			if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus())) {
				status = false;
			}
		}
		System.out.println("BEFORE performDateChange() STATUS CHECKED.. status:::"+status+"++++eodProcessExecuted:::"+eodProcessExecuted);
		// STATUS CHECK FOR performDateChange() ACTIVITY .. 
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(edodAppDateStatus)) {
			status = false;
		}
		
		System.out.println("status:::"+status+"++++eodProcessExecuted:::"+eodProcessExecuted);
		
		if (status && eodProcessExecuted) {
			try{
				DefaultLogger.debug(this,"insite itreator line 580 ");
				System.out.println("insite itreator line 580");
				logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("eodSuccessFile");
				iter = eodActivites.iterator();
				while (iter.hasNext()) {
					DefaultLogger.debug(this,"insite itreator line 584 ");
					System.out.println("insite itreator line 584");
					eodStatus = (IEODStatus)iter.next();
					eodStatus.setEodDate(nextDate.getTime());
					
					eodStatus.setStatus(EODConstants.STATUS_PENDING);
					
					DefaultLogger.debug(this,"After status set to pending "+eodStatus.getActivity());
					System.out.println("After status set to pending"+eodStatus.getActivity());
					eodStatusProxy.updateEODActivity(eodStatus);
					DefaultLogger.debug(this,"insite itreator line 589 ");
					System.out.println("insite itreator line 589");
				}
//				performDateChange();
				performSystemHandover();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("eodFailFile");
		}
		log.append("\n----- EOD End (System Date: "+Calendar.getInstance().getTime().toString()+")-----");
		generateEODReport();
	}

	private void finalizeAdfRbi() {
		System.out.println("#################in finalizeAdfRbi #########################");
		try{
		List eodActivites = eodStatusProxy.findAdfRbiActivities();
		Iterator iter = eodActivites.iterator();
		IAdfRbiStatus eodStatus;
		boolean status = true;
		clearLogs();
		log.append("\n----- ADF RBI Begin (System Date: "+Calendar.getInstance().getTime().toString()+")-----");
		log.append("\nCurrent Application Date : "+newDateFormat.format(currentDate.getTime()));
		while (iter.hasNext()) {
			eodStatus = (IAdfRbiStatus)iter.next();
			log.append("\n"+eodStatus.getActivity()+" : "+eodStatus.getStatus());
			/*if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus())) {
				status = false;
			}*/
		}
		//if (status) {
			logfileName = PropertyManager.getValue("eodLogPath")+"RBIReporteodSuccess.log";//PropertyManager.getValue("eodSuccessFile");
			iter = eodActivites.iterator();
			while (iter.hasNext()) {
				eodStatus = (IAdfRbiStatus)iter.next();
				eodStatus.setEodDate(currentDate.getTime());
				eodStatus.setStatus(EODConstants.STATUS_PENDING);
				eodStatusProxy.updateAdfRbiActivity(eodStatus);
			}
			//performDateChange();
			//performSystemHandover();
		//}
		/*else {
			logfileName = PropertyManager.getValue("eodLogPath")+"RBIReporteodFail.log";
		}*/
		log.append("\n----- ADF RBI End (System Date: "+Calendar.getInstance().getTime().toString()+")-----");
		generateEODReport();
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("################ after finalizeAdfRbi #########################");
	}
	private void manageDormantUsers(IEODStatus eodStatus) {
		DefaultLogger.debug(this, " EndOfDayBatchServiceImpl.manageDormantUsers() starts ==== ");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				getMonEnableDisableUser().start("IN", null);
				eodStatus.setStatus(EODConstants.STATUS_PASS);
			}
			catch (Exception e) {
				eodStatus.setStatus(EODConstants.STATUS_FAIL);
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : manageDormantUsers())" );
				e.printStackTrace();
			}
		}
		else if(EODConstants.STATUS_PASS.equalsIgnoreCase(eodStatus.getStatus())
				|| EODConstants.STATUS_DONE.equalsIgnoreCase(eodStatus.getStatus())) {
			eodStatus.setStatus(EODConstants.STATUS_DONE);
		}
		eodStatusProxy.updateEODActivity(eodStatus);
		DefaultLogger.debug(this, " EndOfDayBatchServiceImpl.manageDormantUsers() ends ==== ");
	}

	private void generateMonthlyBaselReport(IEODStatus eodStatus) {
		DefaultLogger.debug(this, " EndOfDayBatchServiceImpl.generateMonthlyBaselReport() starts ==== ");
		try{
			IGeneralParamEntry generalParamEntry2 = generalParam.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			Date date = new Date(generalParamEntry2.getParamValue());
			Calendar currDate = Calendar.getInstance();
			currDate.setTime(date);

			Calendar nextdate = Calendar.getInstance();
			nextdate.setTime(date);

			nextdate.add(Calendar.DATE, 1);
			while (HolidayHelper.isHoliday(holidayList, nextdate)) {
				nextdate.add(Calendar.DATE, 1);
			}

			DefaultLogger.debug(this,nextdate.getTime());

			if (currDate.get(Calendar.MONTH) == nextdate.get(Calendar.MONTH)) {
				DefaultLogger.debug(this,"-1- Same Month---");
				eodStatus.setStatus(EODConstants.STATUS_NA);
			}
			else {
				DefaultLogger.debug(this,"-1- diff Month---");
				if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
						||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
					try {
						DefaultLogger.debug(this,"-1- before run---");
						getBaselDao().executeEndOfMonthBaselReports();
						DefaultLogger.debug(this,"-1- after run---");
						eodStatus.setStatus(EODConstants.STATUS_PASS);
					}
					catch (Exception e) {
						eodStatus.setStatus(EODConstants.STATUS_FAIL);
						DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : generateDailyBaselReport())" );
						e.printStackTrace();
					}
				}else if(EODConstants.STATUS_PASS.equalsIgnoreCase(eodStatus.getStatus())
						|| EODConstants.STATUS_DONE.equalsIgnoreCase(eodStatus.getStatus())) {
					DefaultLogger.debug(this,"-1- no need---");
					eodStatus.setStatus(EODConstants.STATUS_DONE);
				}
			}
			eodStatusProxy.updateEODActivity(eodStatus);
		}catch(Exception e){
			e.printStackTrace();
		}


		try{
			/*DefaultLogger.debug(this,"-Start - Basel File Report Generation");
			System.out.println("-Start - Basel File Report Generation");
			GenerateReportCmd report  = new GenerateReportCmd();
			report.generateBaselOnEOD();
			System.out.println("-End - Basel File Report Generation");*/
		}catch (Exception e) {
			System.out.println("-Error in - Basel File Report Generation");
			e.printStackTrace();
		}
		DefaultLogger.debug(this, " EndOfDayBatchServiceImpl.generateMonthlyBaselReport() ends ==== ");
	}

	private void generateDailyBaselReport(IEODStatus eodStatus) {
		DefaultLogger.debug(this, " EndOfDayBatchServiceImpl.generateDailyBaselReport() starts ==== ");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				getBaselDao().executeEndOfDayBaselReports();
				eodStatus.setStatus(EODConstants.STATUS_PASS);
			}
			catch (Exception e) {
				eodStatus.setStatus(EODConstants.STATUS_FAIL);
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : generateDailyBaselReport())" );
				e.printStackTrace();
			}
		}
		else if(EODConstants.STATUS_PASS.equalsIgnoreCase(eodStatus.getStatus())
				|| EODConstants.STATUS_DONE.equalsIgnoreCase(eodStatus.getStatus())) {
			eodStatus.setStatus(EODConstants.STATUS_DONE);
		}
		eodStatusProxy.updateEODActivity(eodStatus);
		DefaultLogger.debug(this, " EndOfDayBatchServiceImpl.generateDailyBaselReport() ends ==== ");


		/*
		log.append("\nGenerating Daily Basel Reports : ");
		try {
			getBaselDao().executeEndOfDayBaselReports();
			log.append("Pass");
		}
		catch (Exception exp) {
			log.append("Fail");
			DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : generateDailyBaselReport())" );
			exp.printStackTrace();
		}*/
	}

	private void forceLogOutUsers(IEODStatus eodStatus) {
		DefaultLogger.debug(this, " EndOfDayBatchServiceImpl.forceLogOutUsers() starts ==== ");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				new StdUserDAO().killLoginUserDetail();
				eodStatus.setStatus(EODConstants.STATUS_PASS);
			}
			catch (Exception e) {
				eodStatus.setStatus(EODConstants.STATUS_FAIL);
				e.printStackTrace();
			}
		}
		else if(EODConstants.STATUS_PASS.equalsIgnoreCase(eodStatus.getStatus())
				|| EODConstants.STATUS_DONE.equalsIgnoreCase(eodStatus.getStatus())) {
			eodStatus.setStatus(EODConstants.STATUS_DONE);
		}
		eodStatusProxy.updateEODActivity(eodStatus);
		DefaultLogger.debug(this, " EndOfDayBatchServiceImpl.forceLogOutUsers() ends ==== ");
	}

	private void clearLogs() {
		if (log != null && log.length() > 0) {
			log.delete(0, log.length());
		}

	}
	private void cleanUpTemporaryFolders() {
		log.append("\nCleaning Temporary Folders : ");
		try {
			String folderNameProperty = PropertyManager.getValue("eodTempFolder");
			folderNames = folderNameProperty.split("\\|");
			String fileName;
			File dir;
			if (folderNames != null) {
				for (int i=0; i<folderNames.length; i++) {
					try {
						fileName = folderNames[i];
						dir = new File(fileName.trim());
						if (dir.exists() && dir.isDirectory()) {
							clearDir(dir,false);
						}
					}
					catch (Exception exp) {
						log.append("Fail");
						exp.printStackTrace();
					}
				}			
			}
			log.append("Pass");
		}
		catch (Exception exception) {
			log.append("Fail");
			DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : cleanUpTemporaryFolders())" );
			exception.printStackTrace();
		}
	}

	private void clearDir(File dir, boolean deleteParent) {
		if (dir != null) {
			File[] files = dir.listFiles();
			if (files != null) {
				for (int i=0;i<files.length;i++) {
					if (files[i].isFile()) {
						boolean delete = files[i].delete();
						if(delete==false) {
							System.out.println("file  deletion failed for file:"+files[i].getPath());	
						}
						
					}
					else if (files[i].isDirectory()) {
						clearDir(files[i], true);
					}
				}
			}
			if (deleteParent) {
				boolean delete = dir.delete();
				if(delete==false) {
					System.out.println("file  deletion failed for file:"+dir.getPath());	
				}
			}
		}
	}
	/*private void performDateChange() {
		log.append("\nPerforming Date Change (New Application Date : "+newDateFormat.format(nextDate.getTime())+") : ");
		try {
			logCurrentDate();
			getNextWorkingDate(date);
			System.out.println("General Param Entry " + generalParamEntry);
			if (generalParamEntry != null) {
				System.out.println("Next Date " + nextDate.getTime());
				Date newDate = nextDate.getTime();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				generalParamEntry.setParamValue(newDateFormat.format(nextDate.getTime()));
				//generalParam.updateGeneralParamEntry("actualGeneralParamEntry", generalParamEntry);
				generalParam.updateGeneralParamAppDate(newDateFormat.format(nextDate.getTime()));
				log.append("Pass");			
			}
			else {
				generalParamEntry = generalParam.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
				System.out.println("Next Date if general param is null " + nextDate.getTime());
				Date newDate = nextDate.getTime();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				generalParamEntry.setParamValue(newDateFormat.format(nextDate.getTime()));
				generalParam.updateGeneralParamEntry("actualGeneralParamEntry", generalParamEntry);
				log.append("Pass");	
			}
		}
		catch (Exception exception) {
			log.append("Fail");
			DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : performDateChange())" );
			exception.printStackTrace();
		}
	}*/
	
	private String performDateChange() {
		String eodAppDateStatus = "";
		log.append("\nPerforming Date Change (New Application Date : "+newDateFormat.format(nextDate.getTime())+") : ");
		try {
			logCurrentDate();
			getNextWorkingDate(date);
			System.out.println("General Param Entry " + generalParamEntry);
			if (generalParamEntry != null) {
				System.out.println("Next Date " + nextDate.getTime());
				/*Date newDate = nextDate.getTime();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");*/
				generalParamEntry.setParamValue(newDateFormat.format(nextDate.getTime()));
				//generalParam.updateGeneralParamEntry("actualGeneralParamEntry", generalParamEntry);
				generalParam.updateGeneralParamAppDate(newDateFormat.format(nextDate.getTime()));
				log.append("Pass");		
				eodAppDateStatus = "PASS";
			}
			else {
				generalParamEntry = generalParam.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
				System.out.println("Next Date if general param is null " + nextDate.getTime());
				/*Date newDate = nextDate.getTime();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");*/
				generalParamEntry.setParamValue(newDateFormat.format(nextDate.getTime()));
				generalParam.updateGeneralParamEntry("actualGeneralParamEntry", generalParamEntry);
				log.append("Pass");	
				eodAppDateStatus = "PASS";
			}
		}
		catch (Exception exception) {
			log.append("Fail");
			eodAppDateStatus = "FAIL";
			DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : performDateChange())" );
			exception.printStackTrace();
		}
		return eodAppDateStatus;
	}

	private void logCurrentDate() {
		try {
			 IGeneralParamEntry currentAppDateParamEntry = generalParam.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			 IGeneralParamEntry prevAppDateParamEntry = generalParam.getGeneralParamEntryByParamCodeActual("LAST_EOD_DATE");
			 prevAppDateParamEntry.setParamValue(currentAppDateParamEntry.getParamValue());
			 generalParam.updateGeneralParamEntry("actualGeneralParamEntry", prevAppDateParamEntry);
		} catch (Exception e) {
			DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : logCurrentDate())" );
			e.printStackTrace();
		}
	}

	private void performSystemHandover() {
		log.append("\nPerforming System Handover  : ");
		try {

			IGeneralParamEntry generalParamEntry = generalParam.getGeneralParamEntryByParamCodeActual("USER_HAND_OFF");
			
			generalParamEntry.setHandUpTime(Calendar.getInstance().getTime().toLocaleString());
			generalParamEntry.setParamValue("N");
			generalParam.updateGeneralParamEntry("actualGeneralParamEntry", generalParamEntry);
			log.append("Pass");	

		}
		catch (Exception exception) {
			log.append("Fail");
			DefaultLogger.error(this,"End of Day BatchJob Exception  (Method : performSystemHandover())" );
			exception.printStackTrace();
		}
	}
	private void getNextWorkingDate(Calendar date) {
		date.add(Calendar.DATE, 1);
		while (HolidayHelper.isHoliday(holidayList, date)) {
			date.add(Calendar.DATE, 1);
		}
		DefaultLogger.error(this,date.getTime());
	}

	public StringBuffer performEndOfYearActivities() {
		clearLogs();
		IGeneralParamEntry generalParamEntry = generalParam.getGeneralParamEntryByParamCodeActual("USER_HAND_OFF");
		String userHandOff = generalParamEntry.getParamValue();
		System.out.println("Inside EOY Function");
		if (userHandOff == null || "N".equalsIgnoreCase(userHandOff.trim())) {
			log.append(PropertyManager.getValue("error.string.eod.systemHandOff"));
		}
		else {
			log.append("\n----- End Of Year Begin ("+Calendar.getInstance().getTime().toString()+")-----");
			getPreEODData();
			generateHolidayListForNextYear();
			generateRecurrentDocsForNextYear();
			
			/*try {
				log.append("procedure EOY");
				ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
				collateralDAO.executeEOYProc();
				log.append("Pass");
			} catch (Exception e) {
				log.append("Fail");
				DefaultLogger.debug(this,"error in 904");
				System.out.println("error in 904");
				DefaultLogger.error(this,e);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			//Consider EOD as part of EOY activity. changed by Janki M on 17-APR-2012
			//this.performEOD();
			//performSystemHandover();
			logfileName = PropertyManager.getValue("eodLogPath")+"EndOfYear"+newDateFormat.format(Calendar.getInstance().getTime())+"_"+Calendar.getInstance().getTimeInMillis();
			log.append("\n----- End Of Year End ("+Calendar.getInstance().getTime().toString()+")-----");
			generateEODReport();
		}
		return log;
	}
	private void generateRecurrentDocsForNextYear() {
		log.append("\nGenerating Recurrent Documents For Next Year Part One : ");
		System.out.println("Inside Start EOY-Rec Function");
		try {
			HashMap globalHashMap= new HashMap();
			HashMap itemIdHashMap= new HashMap();
			HashMap expiryHashMap= new HashMap();
			OBTrxContext ctx= new OBTrxContext();
			ArrayList  globalDocChkList = new ArrayList();
			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
			DocumentSearchCriteria criteria = new DocumentSearchCriteria();
			criteria.setDocumentType("REC");
			SearchResult sr = null;
			try {
				sr = proxy.getDocumentItemList(criteria);
			}
			catch (CheckListTemplateException ex) {
				ex.printStackTrace();
				throw new CommandProcessingException("failed to retrieve document item list using criteria [" + criteria
						+ "]", ex);

			}

			if (sr != null && sr.getResultList() != null) {
				globalDocChkList.addAll(sr.getResultList());

				Collections.sort(globalDocChkList);

			}
			ITemplate itemTemplate =  proxy.getCAMTemplate("REC", "REC", "IN");
			if (globalDocChkList != null) {
				DocumentSearchResultItem docItem = new DocumentSearchResultItem(); 
				Iterator iter = globalDocChkList.iterator();
				Calendar cal = Calendar.getInstance();
				//Start:Added by Uma Khot to get USer Handoff date for EOY instead of calendar date.
				String activityPerformed=generalParam.getActivityPerformedForParamCode("USER_HAND_OFF");
				System.out.println("activityPerformed date of USER_HAND_OFF:"+activityPerformed);
				Date date=null;
				if(null!=activityPerformed && !activityPerformed.isEmpty()){
				 date=new Date(activityPerformed);
				System.out.println("EOY date is:"+date);
				}
				//End:Added by Uma Khot to get User Handoff date for EOY instead of calendar date.
				
				while (iter.hasNext()) {
					docItem = (DocumentSearchResultItem)iter.next();

					if (docItem.getItem().getIsRecurrent() != null ) {
						if(docItem.getItem().getIsRecurrent().trim().equals("on")){
							IItem temp = (IItem)docItem.getItem();
							OBDocumentItem documentItem= new OBDocumentItem();
							if (temp.getExpiryDate() != null ) {
							
							//	Date date=new Date();
								
								if(temp.getExpiryDate().getYear()==date.getYear()){
									temp.setItemID(-999999999L);
									//temp.setVersionTime(-999999999L);
									documentItem.setItemCode(genrateUserSegmentSeq());
									//temp.setItemCode(genrateUserSegmentSeq());	
									cal.setTime(temp.getExpiryDate());
									cal.add(Calendar.YEAR, 1);
									//temp.setExpiryDate(cal.getTime());
									documentItem.setItemDesc(docItem.getItemDesc());
									documentItem.setDocumentVersion("0");
									documentItem.setTenureCount(docItem.getTenureCount());
									documentItem.setTenureType(docItem.getTenureType());
									documentItem.setDeprecated(docItem.getDeprecated());
									documentItem.setStatus(docItem.getStatus());
									documentItem.setStatementType(docItem.getStatementType());
									documentItem.setIsRecurrent(docItem.getIsRecurrent());
									documentItem.setRating(docItem.getRating());
									documentItem.setSegment(docItem.getSegment());
									documentItem.setClassification(docItem.getClassification());
									documentItem.setGuarantor(docItem.getGuarantor());
									documentItem.setExpiryDate(cal.getTime());
									documentItem.setItemType("REC");
									
									documentItem.setOldItemCode(docItem.getItemCode());
									
									globalHashMap.put(docItem.getItem().getItemCode(), documentItem.getItemCode());

									IDocumentItemTrxValue docTrxObj = null;

									//					ITrxContext context=(ITrxContext)ctx;
									docTrxObj = proxy.makerCreateDocItem(ctx, documentItem);
									docTrxObj = proxy.checkerApproveDocItem(ctx, docTrxObj);
									itemIdHashMap.put(temp.getItemCode(), String.valueOf(docTrxObj.getDocumentItem().getItemID()));
									if(itemTemplate !=null){
										itemTemplate.addItem(documentItem);
									}
									//expiryHashMap.put(temp.getItemCode(), docTrxObj.getDocumentItem().getExpiryDate());
									//proxy.checkerApproveTemplate(ctx, trxValue);
								}
							}
						}
					}
				}
			}
			ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
			ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
			if(trxValue.getStatus().trim().equals("ACTIVE")){
				trxValue=proxy.makerUpdateTemplate(ctx, trxValue, itemTemplate);
				proxy.checkerApproveTemplate(ctx, trxValue);
			}
			ICheckListProxyManager checklistProxyManager=(ICheckListProxyManager) BeanHouse.get("checklistProxy");
			CheckListSearchResult[] checkListSearchResults =checklistProxyManager.getCheckListByCategory("REC");
			ICheckList[] checkLists=null;
		/*	if(checkListSearchResults!=null){
				for(int j =0; j<checkListSearchResults.length;j++){
					ICheckListTrxValue checkListTrxVal = checklistProxyManager.getCheckList(checkListSearchResults[j].getCheckListID());
					if( checkListTrxVal!=null){
						if( checkListTrxVal.getStatus().equals("ACTIVE")){	
							if( checkListTrxVal.getCheckList().getCheckListItemList()!=null){
								ICheckListItem[] existingItems = checkListTrxVal.getCheckList().getCheckListItemList();
								ArrayList totalNewItems = new ArrayList();
								for(int cn=0;cn<existingItems.length;cn++ ){
									totalNewItems.add(existingItems[cn]);
								}
								for(int k=0;k<checkListTrxVal.getCheckList().getCheckListItemList().length;k++){
									if(globalHashMap.containsKey(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItemCode())){


										if (existingItems != null) {
											if(null != checkListTrxVal.getCheckList().getCheckListItemList() && null != checkListTrxVal.getCheckList().getCheckListItemList()[k].getExpiryDate()){
											Calendar cal = Calendar.getInstance();
											cal.setTime(checkListTrxVal.getCheckList().getCheckListItemList()[k].getExpiryDate());
											cal.add(Calendar.YEAR, 1);
											ICheckListItem parentItem = new OBCheckListItem();
											IItem newItemCreated= new OBItem() ;
											//									 newItemCreated=checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem();
											newItemCreated.setItemDesc(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItemDesc());
											newItemCreated.setDocumentVersion("0");
											newItemCreated.setTenureCount(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getTenureCount());
											newItemCreated.setTenureType(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getTenureType());
											newItemCreated.setDeprecated(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getDeprecated());
											//										newItemCreated.setStatus(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getStatus());
											newItemCreated.setStatus("AWAITING");
											newItemCreated.setStatementType(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getStatementType());
											newItemCreated.setIsRecurrent(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getIsRecurrent());
											newItemCreated.setRating(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getRating());
											newItemCreated.setSegment(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getSegment());
											newItemCreated.setClassification(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getClassification());
											newItemCreated.setGuarantor(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getGuarantor());
											newItemCreated.setExpiryDate(cal.getTime());
											newItemCreated.setItemType("REC");
											newItemCreated.setItemID(Long.parseLong(itemIdHashMap.get(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItemCode()).toString()));
											parentItem.setItemCode(globalHashMap.get(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItemCode()).toString());
											newItemCreated.setItemCode(globalHashMap.get(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItemCode()).toString());
											parentItem.setItem(newItemCreated);
											parentItem.setStatementType(checkListTrxVal.getCheckList().getCheckListItemList()[k].getStatementType());
											parentItem.setExpiryDate(cal.getTime());
											totalNewItems.add(parentItem);
										    }
										}
									}
								}
								checkListTrxVal.getCheckList().setCheckListItemList((ICheckListItem[]) totalNewItems.toArray(new OBCheckListItem[0]));
								checkListTrxVal = checklistProxyManager.makerUpdateCheckListReceipt(ctx, checkListTrxVal, checkListTrxVal.getCheckList());
								checkListTrxVal = checklistProxyManager.checkerApproveCheckListReceipt(ctx, checkListTrxVal);
							}
						}
					}
				}
			}
*/
		
			log.append("Pass");
			
			try {
				log.append("\nGenerating Recurrent Documents For Next Year Part Two : ");
				ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
				System.out.println("Before calling EOY Procedure");
				collateralDAO.executeEOYProc();
				System.out.println("After calling EOY Procedure");
				log.append("Pass");
			} catch (Exception e) {
				log.append("Fail");
				DefaultLogger.debug(this,"error in 1082");
				System.out.println("error in 1082");
				DefaultLogger.error(this,e);
				e.printStackTrace();
			}
			System.out.println("Inside End EOY-Rec Function");
		}
		catch (Exception exception) {
			log.append("Fail");
			DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : generateRecurrentDocsForNextYear())" );
			exception.printStackTrace();
		}
	}

	private SBCMSTrxManager getTrxManager() throws TrxOperationException {
		SBCMSTrxManager mgr = (SBCMSTrxManager) BeanController.getEJB(ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME,
				SBCMSTrxManagerHome.class.getName());

		if (null == mgr) {
			throw new TrxOperationException("failed to find cms trx manager remote interface using jndi name ["
					+ ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME + "]");
		}
		else {
			return mgr;
		}
	}
	final static String DOCUMENT_CODE_SEQ = "DOCUMENT_CODE_SEQ";
	private String genrateUserSegmentSeq() throws RemoteException {
		SequenceManager seqmgr = new SequenceManager();
		String seq = null;
		try {
			seq = seqmgr.getSeqNum(DOCUMENT_CODE_SEQ, true);
			DefaultLogger.debug(this, "Generated sequence " + seq);
			NumberFormat numberFormat = new DecimalFormat("0000000");
			String docCode = numberFormat.format(Long.parseLong(seq));
			docCode = "DOC" + docCode;	
			return docCode;
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception occured while generating sequence   " + e.getMessage());
			throw new RemoteException("Exception in creating the user id", e);
		}
	}
	private void generateHolidayListForNextYear() {
		log.append("\nGenerating Holidays For Next Year : ");
		System.out.println("Inside Start EOY-Holiday Function");
		try {
			OBTrxContext ctx= new OBTrxContext();
			if (recurrentHolidayList != null) {
				IHoliday holiday;
				Iterator iter = recurrentHolidayList.iterator();
				Calendar cal = Calendar.getInstance();
				while (iter.hasNext()) {
					holiday = (OBHoliday)iter.next();
					holiday.setId(0);
					holiday.setCreateBy("System");
					holiday.setCreationDate(currentDate.getTime());
					holiday.setDeprecated("N");
					if (holiday.getStartDate() != null ) {
						cal.setTime(holiday.getStartDate());
						cal.add(Calendar.YEAR, 1);
						holiday.setStartDate(cal.getTime());
					}
					if (holiday.getEndDate() != null ) {
						cal.setTime(holiday.getEndDate());
						cal.add(Calendar.YEAR, 1);
						holiday.setEndDate(cal.getTime());
					}
					holiday.setIsRecurrent("on");			
					IHolidayProxyManager holidayProxyManager = (IHolidayProxyManager)BeanHouse.get("holidayProxy");
					IHolidayTrxValue trxValueOut = new OBHolidayTrxValue();
					trxValueOut =holidayProxyManager.makerCreateHoliday(ctx,holiday);
					holidayProxyManager.checkerApproveHoliday(ctx, trxValueOut);
//					holidayDao.createHoliday("actualHoliday", holiday);
				}
				System.out.println("Inside End EOY-Holiday Function");
			}
			log.append("Pass");
		}
		catch (Exception exception) {
			log.append("Fail");
			DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : generateHolidayListForNextYear())" );
			exception.printStackTrace();
		}
	}

	private void generateEODReport() {
		File logFile = new File(logfileName);
		FileWriter fw = null;
		try {
			fw = new FileWriter(logFile);
			fw.write(log.toString());
			fw.flush();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				if (fw != null) {
					fw.close();
				}
			}
			catch (IOException ex) {

			}
		}
		DefaultLogger.error(this,log.toString());
	}
	private void updateLADDetails(IEODStatus eodStatus) {
		DefaultLogger.debug(this, " EndOfDayBatchServiceImpl.updateLADDetails() starts ==== ");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				//this.updateLAD();
				ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");

				boolean flag=collateralDAO.executeLADExpiryUpdate();
				//Start: Uma Khot:added as LAD expiry taking time for exec. So Splitting into 2 parts.
				if(flag){
				collateralDAO.executeLADExpiryUpdateStage();
				}
				//End: Uma Khot:added as LAD expiry taking time for exec. So Splitting into 2 parts.
				eodStatus.setStatus(EODConstants.STATUS_PASS);
			}
			catch (Exception e) {
				// This is specific to HDFC EOD, in case of LAD Fail, we have forced JOb to mark as pass. 
				// in case of failed JOB, next day EOD will take care of LAD Pending activities.
				// This job is very heavy, and having huge data it might fail.
				// Changed by Janki Mandalia on 1st May, 2012.
				// Mentioned SOP will be printed on systemOut.log in websphere log file in case of failure.
				// 


				eodStatus.setStatus(EODConstants.STATUS_PASS);
				//eodStatus.setStatus(EODConstants.STATUS_FAIL);
				e.printStackTrace();
				System.out.println("---------------LAD-----------------------");
			}
		}
		else if(EODConstants.STATUS_PASS.equalsIgnoreCase(eodStatus.getStatus())
				|| EODConstants.STATUS_DONE.equalsIgnoreCase(eodStatus.getStatus())) {
			eodStatus.setStatus(EODConstants.STATUS_DONE);
		}
		eodStatusProxy.updateEODActivity(eodStatus);
		DefaultLogger.debug(this, " EndOfDayBatchServiceImpl.updateLADDetails() ends ==== ");

		/*
		try {
			log.append("\nUpdating LAD Details : ");
			this.updateLAD();
			log.append("Pass");
		} catch (Exception e) {
			log.append("Fail");
			e.printStackTrace();
		}*/
	}

	private void generateEmailNotification(IEODStatus eodStatus) {
		DefaultLogger.debug(this, " EndOfDayBatchServiceImpl.generateEmailNotification() starts ==== ");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				HashMap map = new HashMap();
				getEmailNotificationMain().execute(map);
				eodStatus.setStatus(EODConstants.STATUS_PASS);
			}
			catch (Exception e) {
				eodStatus.setStatus(EODConstants.STATUS_FAIL);
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : generateEmailNotification())" );
				e.printStackTrace();
			}
		}
		else if(EODConstants.STATUS_PASS.equalsIgnoreCase(eodStatus.getStatus())
				|| EODConstants.STATUS_DONE.equalsIgnoreCase(eodStatus.getStatus())) {
			eodStatus.setStatus(EODConstants.STATUS_DONE);
		}
		eodStatusProxy.updateEODActivity(eodStatus);
		DefaultLogger.debug(this, " EndOfDayBatchServiceImpl.generateEmailNotification() ends ==== ");
	}

	private void updateLAD() throws Exception{
		//	DefaultLogger.debug(this, "----------LAD1-----------");
		HashMap updateLad = new HashMap();
		try{

			ILADProxyManager ladProxy =(ILADProxyManager)BeanHouse.get("ladProxy");	
			//	DefaultLogger.debug(this, "----------LAD 2-----------");	
			IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			//	DefaultLogger.debug(this, "----------LAD 3-----------");
			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			//	DefaultLogger.debug(this, "----------LAD 4-----------");
			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			//	DefaultLogger.debug(this, "----------LAD 5-----------");
			Date dateApplication=new Date();
			long ladGenIndicator=0l;
			//	DefaultLogger.debug(this, "----------LAD 6-----------");
			for(int i=0;i<generalParamEntries.length;i++){

				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){

					dateApplication=new Date(generalParamEntries[i].getParamValue());
				}else if(generalParamEntries[i].getParamCode().equals("LAD_GEN_INDICATOR")){
					ladGenIndicator=Long.parseLong(generalParamEntries[i].getParamValue());
				} 
			}
			//	DefaultLogger.debug(this, "----------LAD 7-----------" + dateApplication +"----"+ladGenIndicator);
			ILADDao iladDao= (ILADDao) BeanHouse.get("ladDao");


			// To avoid multiple marking of LAD : 01-05-2012 By Abhjit R.
			//List ladList= iladDao.getAllLAD();
			List ladList= iladDao.getAllLADNotGenerated();
			//	DefaultLogger.debug(this, "----------LAD 8-----------"+ladList);
			if(ladList!=null && ladList.size()!=0){
				//		DefaultLogger.debug(this, "----------LAD 9-----------"+ladList.size());
				int ind=0;
				for(int i=0;i<ladList.size();i++){
					ILAD ilad=(ILAD)ladList.get(i);
					if(ilad !=null && ilad.getLad_due_date()!=null){
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
				//	DefaultLogger.debug(this, "----------LAD 10-----------"+ind);
			}
			//	DefaultLogger.debug(this, "----------LAD 11-----------"+updateLad.size());
			if(updateLad.size()!=0){

				ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();	
				//		DefaultLogger.debug(this, "----------LAD 12-----------");

				for(int b=1;b<=updateLad.size();b++){
					List listDate=new ArrayList();
					String alimitProfileId=(String)updateLad.get(String.valueOf(b));
					long limitProfileId=Long.parseLong(alimitProfileId);
					ILimitProxy limitProxy = LimitProxyFactory.getProxy();
					ILimitProfile limit = limitProxy.getLimitProfile(limitProfileId);
					CheckListSearchResult ladCheckList= proxy.getCAMCheckListByCategoryAndProfileID("LAD",limitProfileId);

					ICheckList[] checkLists= proxy.getAllCheckList(limit);
					ICheckList[] finalCheckLists =null; 
					ArrayList expList= new ArrayList();


					//	     DefaultLogger.debug(this, "----------LAD 13-----------"+checkLists);
					if(checkLists!=null){
						finalCheckLists = new ICheckList[checkLists.length];
						int a=0;
						for (int y = 0; y < checkLists.length; y++) {
							if(checkLists[y].getCheckListType().equals("F")||checkLists[y].getCheckListType().equals("S")||checkLists[y].getCheckListType().equals("O")){

								ICheckListItem[] curLadList = (ICheckListItem[]) checkLists[y].getCheckListItemList();
								if(curLadList !=null){
									ArrayList expList2= new ArrayList();
									for (int z = 0; z < curLadList.length; z++) {

										ICheckListItem item = (ICheckListItem) curLadList[z];
										if(item!=null){
											if(item.getItemStatus().equals("RECEIVED")){
												if(item.getDocumentStatus()!=null && item.getDocumentStatus().equals("ACTIVE")){
												if(item.getExpiryDate()!=null){
													expList2.add(item.getExpiryDate());
												}
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



					if(finalCheckLists!=null && finalCheckLists.length > 0){
						for(int k=0;k<finalCheckLists.length;k++){
							if(finalCheckLists[k]!=null){
								if(!finalCheckLists[k].getCheckListType().equals("O")){
									if(finalCheckLists[k].getCheckListItemList()!=null){
										ICheckListItem[] checkListItems=finalCheckLists[k].getCheckListItemList();

										for(int m=0;m<checkListItems.length;m++){
											if(checkListItems[m].getExpiryDate()!=null){
												if(checkListItems[m].getItemStatus().equals("RECEIVED")){
													if(checkListItems[m].getDocumentStatus()!=null && checkListItems[m].getDocumentStatus().equals("ACTIVE")){
													if(checkListItems[m].getExpiryDate()!=null){
														listDate.add(checkListItems[m].getExpiryDate());
														generateLad=true;
													}
													}
												}
											}
										}
										Collections.sort(listDate);
									}
								}
							}
						}
					}


					//Janki has added last conditions "listDate.size()>0" on 26 Mar 2012
					if(finalCheckLists!=null && finalCheckLists.length > 0 ){
						boolean isNotPreviousGenerated=true;
						ILAD  ilad=(ILAD)iladDao.getLADNormal(limitProfileId).get(0);
						if("Y".equals(ilad.getIsOperationAllowed())){
							isNotPreviousGenerated=false;
						}
						ilad.setIsOperationAllowed("Y");
						//Date changedDueDate = null;
						if(listDate!=null && listDate.size()>0){
							Date date=(Date)listDate.get(0);
							// changedDueDate  = CommonUtil.rollUpDateByYears(date, 3);
							ilad.setLad_due_date(date);
						}
						ilad=ladProxy.updateLAD(ilad);
						iladDao.updateLADOperation("Y", limitProfileId);
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
								iladItem.setDoc_item_id(finalCheckLists[i].getCheckListID());
								iladItem=ladProxy.createLADItem(iladItem);

								if(finalCheckLists[i].getCheckListItemList()!=null){
									ICheckListItem[] checkListItems=finalCheckLists[i].getCheckListItemList();
									for(int j=0;j<checkListItems.length;j++){
										if(checkListItems[j].getDocumentStatus()!=null && checkListItems[j].getDocumentStatus().trim().equals("ACTIVE")){
											
										if(checkListItems[j].getExpiryDate()!=null){
											if(checkListItems[j].getItemStatus().equals("RECEIVED")){
												ILADSubItem iladSubItem= new OBLADSubItem();
												iladSubItem.setDoc_item_id(iladItem.getDoc_item_id());
												iladSubItem.setCategory(finalCheckLists[i].getCheckListType());
												iladSubItem.setDoc_description(checkListItems[j].getItemDesc());
												iladSubItem.setExpiry_date(checkListItems[j].getExpiryDate());
												iladSubItem.setDoc_sub_item_id(checkListItems[j].getCheckListItemID());
												ladProxy.createLADSubItem(iladSubItem);
											}
										
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
			throw e;
		}
	}

	public void checkSystemHandoff() {
		clearLogs();
		IGeneralParamEntry generalParamEntry = generalParam.getGeneralParamEntryByParamCodeActual("USER_HAND_OFF");
		String userHandOff = generalParamEntry.getParamValue();
		if (userHandOff == null || "N".equalsIgnoreCase(userHandOff.trim())) {
			log.append(PropertyManager.getValue("error.string.eod.systemHandOff"));
			logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("systemHandOffFailFile");
		}
		else {
			log.append(PropertyManager.getValue("error.string.eod.systemHandOff.pass"));
			logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("systemHandOffSuccessFile");
		}
		generateEODReport();
	}
	protected void recpColLimitLink(IAdfRbiStatus eodStatus) {
		System.out.println("-----------1-------------recpColLimitLink--------start------------------");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {

				statusJdbc.recpColLimitLink();
			}
			catch (Exception e) {
				System.out.println("-----------1-------------recpColLimitLink--------error------------------");
				
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : recpColLimitLink())" );
				e.printStackTrace();
			}
		}
		System.out.println("-----------1-------------recpColLimitLink--------end------------------");
	}
	protected void recpColProductLink(IAdfRbiStatus eodStatus) {
		System.out.println("-----------2-------------recpColProductLink--------start------------------");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				statusJdbc.recpColProductLink();
			}
			catch (Exception e) {
				System.out.println("-----------2-------------recpColProductLink--------error------------------");
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : recpColProductLink())" );
				e.printStackTrace();
			}
		}
		System.out.println("-----------2-------------recpColProductLink--------end------------------");
	}
	protected void recpCollateral(IAdfRbiStatus eodStatus) {
		System.out.println("------------3------------recpCollateral--------start------------------");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				statusJdbc.recpCollateral();
			}
			catch (Exception e) {
				System.out.println("------------3------------recpCollateral--------error------------------");
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : recpCollateral())" );
				e.printStackTrace();
			}
		}
		System.out.println("-----------3-------------recpCollateral--------end------------------");
	}
	protected void recpCustLegal(IAdfRbiStatus eodStatus) {
		System.out.println("------------4------------recpCustLegal--------start------------------");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				statusJdbc.recpCustLegal();
			}
			catch (Exception e) {
				System.out.println("------------4------------recpCustLegal--------error------------------");
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : recpCustLegal())" );
				e.printStackTrace();
			}
		}
		System.out.println("------------4------------recpCustLegal--------end------------------");
	}
	protected void recpCustomer(IAdfRbiStatus eodStatus) {
		System.out.println("----------5--------------recpCustomer--------start------------------");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				statusJdbc.recpCustomer();
			}
			catch (Exception e) {
				System.out.println("----------5--------------recpCustomer--------error------------------");
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : recpCustomer())" );
				e.printStackTrace();
			}
		}
		System.out.println("-----------5-------------recpCustomer--------end------------------");
	}
	protected void recpParty(IAdfRbiStatus eodStatus) {
		System.out.println("----------6--------------recpParty--------start------------------");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				statusJdbc.recpParty();
			}
			catch (Exception e) {
				System.out.println("----------6--------------recpParty--------error------------------");
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : recpParty())" );
				e.printStackTrace();
			}
		}
		System.out.println("------------6------------recpParty--------end------------------");
	}

	private void recpRbiAdfAllProcedure(  ) {
		try {
			statusJdbc.recpRbiAdfAllProcedure();
		}
		catch (Exception e) {
			DefaultLogger.error(this,"RBI ADF Report Generation Error: (System Date : "+Calendar.getInstance().getTime()+") (Method : recpRbiAdfAllProcedure())" );
			e.printStackTrace();
		}
	}

	protected void finconParty(IAdfRbiStatus eodStatus) {
		System.out.println("------------4------------finconParty--------start------------------");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				statusJdbc.recpFinconParty();
			}
			catch (Exception e) {
				System.out.println("------------4------------finconParty--------error------------------");
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : finconParty())" );
				e.printStackTrace();
			}
		}
		System.out.println("------------4------------finconParty--------end------------------");
	}
	
	protected void finconCustomer(IAdfRbiStatus eodStatus) {
		System.out.println("------------4------------finconCustomer--------start------------------");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				statusJdbc.recpFinconCustomer();
			}
			catch (Exception e) {
				System.out.println("------------4------------finconCustomer--------error------------------");
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : finconCustomer())" );
				e.printStackTrace();
			}
		}
		System.out.println("------------4------------finconCustomer--------end------------------");
	}
	protected void finconCustLegal(IAdfRbiStatus eodStatus) {
		System.out.println("------------4------------finconCustLegal--------start------------------");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				statusJdbc.recpFinconCustLegal();
			}
			catch (Exception e) {
				System.out.println("------------4------------finconCustLegal--------error------------------");
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : finconCustLegal())" );
				e.printStackTrace();
			}
		}
		System.out.println("------------4------------finconCustLegal--------end------------------");
	}
	protected void finconCollateral(IAdfRbiStatus eodStatus) {
		System.out.println("------------4------------finconCollateral--------start------------------");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				statusJdbc.recpFinconCollateral();
			}
			catch (Exception e) {
				System.out.println("------------4------------finconCollateral--------error------------------");
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : finconCollateral())" );
				e.printStackTrace();
			}
		}
		System.out.println("------------4------------finconCollateral--------end------------------");
	}
	protected void finconColProductLink(IAdfRbiStatus eodStatus) {
		System.out.println("------------4------------finconColProductLink--------start------------------");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				statusJdbc.recpFinconColProductLink();
			}
			catch (Exception e) {
				System.out.println("------------4------------finconColProductLink--------error------------------");
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : finconColProductLink())" );
				e.printStackTrace();
			}
		}
		System.out.println("------------4------------finconColProductLink--------end------------------");
	}
	
	protected void finconColLimitLink(IAdfRbiStatus eodStatus) {
		System.out.println("------------4------------finconColLimitLink--------start------------------");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				statusJdbc.recpFinconColLimitLink();
			}
			catch (Exception e) {
				System.out.println("------------4------------finconColLimitLink--------error------------------");
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : finconColLimitLink())" );
				e.printStackTrace();
			}
		}
		System.out.println("------------4------------finconColLimitLink--------end------------------");
	}

	//Added by Abhijit R for BCP reconsilation of images in db2cm

	/*	public StringBuffer performBCPActivity(Map arg) {
		clearLogs();

		IGeneralParamEntry generalParamEntry = generalParam.getGeneralParamEntryByParamCodeActual("USER_HAND_OFF");
		String userHandOff = generalParamEntry.getParamValue();
		boolean status = false;
//		if (userHandOff == null || "N".equalsIgnoreCase(userHandOff.trim())) {
//			log.append(PropertyManager.getValue("error.string.eod.systemHandOff"));
//			status = false;
//			logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("eodFailFile");
//			generateEODReport();
//		}
//		else {

		IImageUploadDao imageUploadDao =(IImageUploadDao )BeanHouse.get("imageUploadDao");
		List imageList =imageUploadDao.getImageList("", "");
		ContentManagerFactory	contentManagerFactory =(ContentManagerFactory) BeanHouse.get("contentManagerFactory");
		if(imageList !=null){
			HashMap successfullObjects= new HashMap();
			HashMap unSuccessfullObjects= new HashMap();
		for(int i=0; i <imageList.size(); i++){
		IImageUploadAdd uploadAdd =(IImageUploadAdd) imageList.get(i);	

		String imagePath="";
		try {
			Object[] params  = new Object[3];
			params[0] = uploadAdd.getImageFilePath();
			params[1] = uploadAdd.getImgFileName();
			params[2] = uploadAdd.getStatus();
			imagePath=(String)contentManagerFactory.getContentManagerService().retrieveDocument(params);
		} catch (ContentManagerInitializationException e) {
			throw new CommandProcessingException(e.getMessage(),e);
		} catch (Exception e) {
			throw new CommandProcessingException(e.getMessage(),e);
		}		
		DefaultLogger.debug(this,"imagePath ---" + imagePath);

						if("/dmsImages/preset/sorry.jpg".equalsIgnoreCase(imagePath)){
							uploadAdd.setImgDepricated("N");
							imageUploadDao.createImageUploadBCP(uploadAdd);

						}else{
							uploadAdd.setImgDepricated("Y");
							imageUploadDao.createImageUploadBCP(uploadAdd);

						}

				}
			}
		IReportService reportService =(IReportService) BeanHouse.get("reportService");
		OBFilter filter = new OBFilter();
		String reportId= "";
		String fileName = "BCP Reconsile Details"+DateFormatUtils.format(new Date(), "_yyyyMMddHHmmss")+".xls";

		reportService.generateReport(reportId, fileName,filter);
		//--------------------------------------------------------

		String reportFile=PropertyManager.getValue(IReportConstants.BASE_PATH)+ System.getProperty("file.separator") + fileName;



//		}		
	//	logfileName = PropertyManager.getValue("eodLogPath")+"EndOfDay"+newDateFormat.format(Calendar.getInstance().getTime())+"_"+Calendar.getInstance().getTimeInMillis();
		return log;
	}*/

	// End abhijit R
	
	public void updateEODStatus(String eodStatus,String activityID) {
		DefaultLogger.debug(this, " updateEODStatus() starts ==== ");
		String sql="Update CMS_EOD_STATUS set STATUS='"+eodStatus+"' where ID = '"+activityID+"'";
		
		DBUtil dbUtil = null;
			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(sql);
			    int cnt = dbUtil.executeUpdate();
			    dbUtil.commit();
			} catch (Exception e) {
				DefaultLogger.error(this,"EndOfDayBatchServiceImpl Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : updateEODStatus())" );
				e.printStackTrace();
			}finally{
				try {
					if (dbUtil != null) {
						dbUtil.close();
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		DefaultLogger.debug(this, " updateEODStatus() ends ==== ");
    }
	
	// Added BY Dayananda Laishram on 10/06/2015 FINCON_CR2 || Starts
	
	protected void finconCustWiseSec(IAdfRbiStatus eodStatus) {
		System.out.println("------------5------------finconCustWiseSec--------start------------------");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				statusJdbc.recpFinconCustWiseSec();
			}
			catch (Exception e) {
				System.out.println("------------5------------finconCustWiseSec--------error------------------");
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : finconCustWiseSec())" );
				e.printStackTrace();
			}
		}
		System.out.println("------------5------------finconCustWiseSec--------end------------------");
	}
	
	
	protected void finconMasterIndustry(IAdfRbiStatus eodStatus) {
		System.out.println("------------6------------finconMasterIndustry--------start------------------");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				statusJdbc.recpFinconMasterIndustry();
			}
			catch (Exception e) {
				System.out.println("------------6------------finconMasterIndustry--------error------------------");
				DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : finconMasterIndustry())" );
				e.printStackTrace();
			}
		}
		System.out.println("------------6------------finconMasterIndustry--------end------------------");
	}
	
	//Added BY Dayananda Laishram on 10/06/2015 FINCON_CR2 || Ends
	
	//Start:Added by Uma Khot: for generating EOY issue clean up and generating recurrent master again
//	private void generateRecurrentDocsForNextYearCleanUp() {
//		log.append("\nGenerating Recurrent Documents For Next Year Part One : ");
//		System.out.println("Inside Start EOY-Rec Function");
//		System.out.println("calling generateRecurrentDocsForNextYearCleanUp method");
//		
//		try {
//			HashMap globalHashMap= new HashMap();
//			HashMap itemIdHashMap= new HashMap();
//			HashMap expiryHashMap= new HashMap();
//			OBTrxContext ctx= new OBTrxContext();
//			ArrayList  globalDocChkList = new ArrayList();
//			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
//			DocumentSearchCriteria criteria = new DocumentSearchCriteria();
//			criteria.setDocumentType("REC");
//			SearchResult sr = null;
//			try {
//				sr = proxy.getDocumentItemList(criteria);
//			}
//			catch (CheckListTemplateException ex) {
//				ex.printStackTrace();
//				throw new CommandProcessingException("failed to retrieve document item list using criteria [" + criteria
//						+ "]", ex);
//
//			}
//
//			if (sr != null && sr.getResultList() != null) {
//				globalDocChkList.addAll(sr.getResultList());
//
//				Collections.sort(globalDocChkList);
//
//			}
//			ITemplate itemTemplate =  proxy.getCAMTemplate("REC", "REC", "IN");
//			if (globalDocChkList != null) {
//				DocumentSearchResultItem docItem = new DocumentSearchResultItem(); 
//				Iterator iter = globalDocChkList.iterator();
//				Calendar cal = Calendar.getInstance();
//				String activityPerformed=generalParam.getActivityPerformedForParamCode("EOY_CLEAN_UP");
//				System.out.println("activityPerformed date of EOY_CLEAN_UP:"+activityPerformed);
//				Date date=null;
//				if(null!=activityPerformed && !activityPerformed.isEmpty()){
//				date=new Date(activityPerformed);
//				System.out.println("EOY date is:"+date);
//				}
//				while (iter.hasNext()) {
//					docItem = (DocumentSearchResultItem)iter.next();
//
//					if (docItem.getItem().getIsRecurrent() != null ) {
//						if(docItem.getItem().getIsRecurrent().trim().equals("on")){
//							IItem temp = (IItem)docItem.getItem();
//							OBDocumentItem documentItem= new OBDocumentItem();
//							if (temp.getExpiryDate() != null ) {
//								
//								if(temp.getExpiryDate().getYear()==date.getYear()){
//									temp.setItemID(-999999999L);
//									//temp.setVersionTime(-999999999L);
//									documentItem.setItemCode(genrateUserSegmentSeq());
//									//temp.setItemCode(genrateUserSegmentSeq());	
//									cal.setTime(temp.getExpiryDate());
//									cal.add(Calendar.YEAR, 1);
//									//temp.setExpiryDate(cal.getTime());
//									documentItem.setItemDesc(docItem.getItemDesc());
//									documentItem.setDocumentVersion("0");
//									documentItem.setTenureCount(docItem.getTenureCount());
//									documentItem.setTenureType(docItem.getTenureType());
//									documentItem.setDeprecated(docItem.getDeprecated());
//									documentItem.setStatus(docItem.getStatus());
//									documentItem.setStatementType(docItem.getStatementType());
//									documentItem.setIsRecurrent(docItem.getIsRecurrent());
//									documentItem.setRating(docItem.getRating());
//									documentItem.setSegment(docItem.getSegment());
//									documentItem.setClassification(docItem.getClassification());
//									documentItem.setGuarantor(docItem.getGuarantor());
//									documentItem.setExpiryDate(cal.getTime());
//									documentItem.setItemType("REC");
//									globalHashMap.put(docItem.getItem().getItemCode(), documentItem.getItemCode());
//
//									IDocumentItemTrxValue docTrxObj = null;
//
//									//					ITrxContext context=(ITrxContext)ctx;
//									docTrxObj = proxy.makerCreateDocItem(ctx, documentItem);
//									docTrxObj = proxy.checkerApproveDocItem(ctx, docTrxObj);
//									itemIdHashMap.put(temp.getItemCode(), String.valueOf(docTrxObj.getDocumentItem().getItemID()));
//									if(itemTemplate !=null){
//										itemTemplate.addItem(documentItem);
//									}
//									//expiryHashMap.put(temp.getItemCode(), docTrxObj.getDocumentItem().getExpiryDate());
//									//proxy.checkerApproveTemplate(ctx, trxValue);
//								
//								}
//							}
//						}
//					}
//				}
//			}
//			ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
//			ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
//			if(trxValue.getStatus().trim().equals("ACTIVE")){
//				trxValue=proxy.makerUpdateTemplate(ctx, trxValue, itemTemplate);
//				proxy.checkerApproveTemplate(ctx, trxValue);
//			}
//			ICheckListProxyManager checklistProxyManager=(ICheckListProxyManager) BeanHouse.get("checklistProxy");
//			CheckListSearchResult[] checkListSearchResults =checklistProxyManager.getCheckListByCategory("REC");
//			ICheckList[] checkLists=null;
//		/*	if(checkListSearchResults!=null){
//				for(int j =0; j<checkListSearchResults.length;j++){
//					ICheckListTrxValue checkListTrxVal = checklistProxyManager.getCheckList(checkListSearchResults[j].getCheckListID());
//					if( checkListTrxVal!=null){
//						if( checkListTrxVal.getStatus().equals("ACTIVE")){	
//							if( checkListTrxVal.getCheckList().getCheckListItemList()!=null){
//								ICheckListItem[] existingItems = checkListTrxVal.getCheckList().getCheckListItemList();
//								ArrayList totalNewItems = new ArrayList();
//								for(int cn=0;cn<existingItems.length;cn++ ){
//									totalNewItems.add(existingItems[cn]);
//								}
//								for(int k=0;k<checkListTrxVal.getCheckList().getCheckListItemList().length;k++){
//									if(globalHashMap.containsKey(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItemCode())){
//
//
//										if (existingItems != null) {
//											if(null != checkListTrxVal.getCheckList().getCheckListItemList() && null != checkListTrxVal.getCheckList().getCheckListItemList()[k].getExpiryDate()){
//											Calendar cal = Calendar.getInstance();
//											cal.setTime(checkListTrxVal.getCheckList().getCheckListItemList()[k].getExpiryDate());
//											cal.add(Calendar.YEAR, 1);
//											ICheckListItem parentItem = new OBCheckListItem();
//											IItem newItemCreated= new OBItem() ;
//											//									 newItemCreated=checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem();
//											newItemCreated.setItemDesc(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItemDesc());
//											newItemCreated.setDocumentVersion("0");
//											newItemCreated.setTenureCount(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getTenureCount());
//											newItemCreated.setTenureType(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getTenureType());
//											newItemCreated.setDeprecated(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getDeprecated());
//											//										newItemCreated.setStatus(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getStatus());
//											newItemCreated.setStatus("AWAITING");
//											newItemCreated.setStatementType(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getStatementType());
//											newItemCreated.setIsRecurrent(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getIsRecurrent());
//											newItemCreated.setRating(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getRating());
//											newItemCreated.setSegment(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getSegment());
//											newItemCreated.setClassification(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getClassification());
//											newItemCreated.setGuarantor(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItem().getGuarantor());
//											newItemCreated.setExpiryDate(cal.getTime());
//											newItemCreated.setItemType("REC");
//											newItemCreated.setItemID(Long.parseLong(itemIdHashMap.get(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItemCode()).toString()));
//											parentItem.setItemCode(globalHashMap.get(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItemCode()).toString());
//											newItemCreated.setItemCode(globalHashMap.get(checkListTrxVal.getCheckList().getCheckListItemList()[k].getItemCode()).toString());
//											parentItem.setItem(newItemCreated);
//											parentItem.setStatementType(checkListTrxVal.getCheckList().getCheckListItemList()[k].getStatementType());
//											parentItem.setExpiryDate(cal.getTime());
//											totalNewItems.add(parentItem);
//										    }
//										}
//									}
//								}
//								checkListTrxVal.getCheckList().setCheckListItemList((ICheckListItem[]) totalNewItems.toArray(new OBCheckListItem[0]));
//								checkListTrxVal = checklistProxyManager.makerUpdateCheckListReceipt(ctx, checkListTrxVal, checkListTrxVal.getCheckList());
//								checkListTrxVal = checklistProxyManager.checkerApproveCheckListReceipt(ctx, checkListTrxVal);
//							}
//						}
//					}
//				}
//			}
//*/
//		
//			log.append("Pass");
//		//call the procedure manually	
//	/*		try {
//				log.append("\nGenerating Recurrent Documents For Next Year Part Two : ");
//				ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
//				System.out.println("Before calling EOY Procedure");
//				collateralDAO.executeEOYProc();
//				System.out.println("After calling EOY Procedure");
//				log.append("Pass");
//			} catch (Exception e) {
//				log.append("Fail");
//				DefaultLogger.debug(this,"error in 1082");
//				System.out.println("error in 1082");
//				DefaultLogger.error(this,e);
//				e.printStackTrace();
//			} */
//			System.out.println("Inside End EOY-Rec Function");
//		}
//		catch (Exception exception) {
//			log.append("Fail");
//			DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : generateRecurrentDocsForNextYearCleanUp())" );
//			exception.printStackTrace();
//		}
//		
//	} 
	//End:Added by Uma Khot: for generating EOY issue clean up and generating recurrent master again
	
	
	
	//CR:FCUBS handoff
	public Object downloadFcubsFile(){
		String todayDate=null;
		String status="Download of FCUBS File for this month has already been performed.";
		boolean activityPerformedParamCode = getBaselDao().getActivityPerformedParamCode("MONTHEND_DATE_DOWNLOAD_FCCNCB");
		
		clearLogs();
		
		Calendar applicationDateLocal = Calendar.getInstance();
		
		generalParamEntry = generalParam.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
		if(generalParamEntry!=null){
			todayDate=generalParamEntry.getParamValue();
			applicationDateLocal.setTime(new Date(todayDate));
			log.append("\r\n Job running Current Application Date : "+newDateFormat.format(applicationDateLocal.getTime()));
			log.append("\r\n");
		}
		
		try{
		if(activityPerformedParamCode){
			CMSFtpClient ftpClient;
			String remoteDir = "";
			String localDir = "";
			String day="";
			//ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			
			
			IGeneralParamEntry generalParamEntry = generalParam.getGeneralParamEntryByParamCodeActual("MONTHEND_DATE_DOWNLOAD_FCCNCB");
			String monthendDateFccNcb = generalParamEntry.getParamValue();
			DefaultLogger.debug(this, "monthendDateFccNcb:"+monthendDateFccNcb);
			
			String fccncbFilename=PropertyManager.getValue("basel.report.file.from.fccncb");
			
			ftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.FCUBS_MONTHLY_FILE_DOWNLOAD);
			
			 remoteDir = PropertyManager.getValue("ftp.fcubs.upload.remote.dir");
			 localDir = PropertyManager.getValue("ftp.fcubs.upload.local.dir");
			 
			 DefaultLogger.debug(this, "executing openConnection()");
			 boolean openConnection = ftpClient.openConnection();
			 DefaultLogger.debug(this, "completed openConnection()");
			 
			 if(null!=monthendDateFccNcb){
				 String[] split = monthendDateFccNcb.split("-");
				 if(split.length>0){
					 day= split[0];
				 }
			 }
			 
			 remoteDir=remoteDir+day+"/OTHER/";
			 //status="remoteDir:"+remoteDir+" and localDir:"+localDir+"\n";
			 
			 boolean isDirectoryPresent = ftpClient.isARemoteDirectory(remoteDir);
			 if(isDirectoryPresent){
			 Vector<String> fileList = ftpClient.listFileInDir(remoteDir);
			 int size = fileList.size();
					if(fileList!=null&& size>0){
						for(int i=0;i<size;i++){
						String fileName =fileList.get(i);
							DefaultLogger.debug(this, "fileName:"+fileName);
							if( fileName.startsWith(fccncbFilename)&& (fileName.endsWith(".csv") ||  fileName.endsWith(".CSV")))
							{
								ftpClient.downloadFile(localDir+ fileName, remoteDir+ fileName);
								status=fileName+" is downloaded from location "+remoteDir +" to "+localDir+"\n";
								getBaselDao().updateActivityPerfForParamCode("MONTHEND_DATE_DOWNLOAD_FCCNCB",todayDate);
								
							}else if(i==(size-1)){
								status="File is not present at remote location'"+ remoteDir+ "'.\n";
							}
						
						}
					}else{
						status="File is not present at remote location '"+ remoteDir+ "'.\n";
					}
			 }else{
				 status="Remote Directory '"+ remoteDir+ "' is not present.\n";
			 }
					
					log.append("\r\n Status ::: "+status);
					log.append("\r\n");
					logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("fcubsFileDownloadSuccessFile");
		}else{
			//status="Download of FCUBS File for this month has already been performed.";
			log.append("\r\n");
			log.append("\r\n Status ::: "+status);
			log.append("\r\n");
			logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("fcubsFileDownloadNoGenerationNoFile");
		}
		}catch(Exception e){
			status=e.getMessage();
			log.append("\r\n");
			log.append("\r\n Status ::: "+status);
			log.append("\r\n");
			logfileName = PropertyManager.getValue("eodLogPath")+PropertyManager.getValue("fcubsFileDownloadFailFile");
			e.printStackTrace();
		}
		generateEODReport();
		return status;
		
	}
	
	public void removeFcUbsFileFromLocal(){
		DefaultLogger.debug(this, "Inside method removeFcUbsFileFromLocal");
		String localDir = PropertyManager.getValue("ftp.fcubs.upload.local.dir");
		String fccncbFilename=PropertyManager.getValue("basel.report.file.from.fccncb");
		File f=new File(localDir);
		if(f.exists()){
		File[] listFiles = f.listFiles();
		
		for(int i=0; i<listFiles.length;i++){
			File file = listFiles[i];
			String name = file.getName();
			if( name.startsWith(fccncbFilename)&& (name.endsWith(".csv") ||  name.endsWith(".CSV"))){
				DefaultLogger.debug(this,"File "+name +" exist at local location "+localDir);
				boolean deleteFlag = file.delete();
				if(deleteFlag){
					DefaultLogger.debug(this,"File "+name +" deleted from local location "+localDir);
				}
			}
			
		}
		}else{
			DefaultLogger.debug(this, "Local location "+localDir +" doesn't exist.");
		}
		
	}
	
	public void resetDiarySequence() {
		DefaultLogger.debug(this, " resetDiarySequence() starts ==== ");
		//String sql="Update CMS_EOD_STATUS set STATUS='"+eodStatus+"' where ID = '"++"'";
		
		DBUtil dbUtil = null;
			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL("{call RESET_SEQ_CMS_DIARY_NUMBER_SEQ ('CMS_DIARY_NUMBER_SEQ')}");
			    boolean cnt = dbUtil.execute();
			    dbUtil.commit();
			} catch (Exception e) {
				DefaultLogger.error(this,"EndOfDayBatchServiceImpl Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : resetDiarySequence())" );
				e.printStackTrace();
			}finally{
				try {
					if (dbUtil != null) {
						dbUtil.close();
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		DefaultLogger.debug(this, " resetDiarySequence() ends ==== ");
    }
	
	/*private void updateIntraDayLimitAvailable(Date currDate, Date nextdate) {
		DefaultLogger.debug(this, " updateIntraDayLimitAvailable() starts ==== ");
		//String sql="Update CMS_EOD_STATUS set STATUS='"+eodStatus+"' where ID = '"++"'";
		
		DBUtil dbUtil = null;
			try {
				SimpleDateFormat d=new SimpleDateFormat("dd-MMM-yy");
				System.out.println("currDate......"+currDate);
				System.out.println("nextdate......"+nextdate);
				String currDateFormat = d.format(currDate);
				System.out.println("currDateFomat......"+currDateFormat);
				String nextdateFormat = d.format(nextdate);
				System.out.println("nextdateFormat......"+nextdateFormat);
				
				dbUtil=new DBUtil();
				dbUtil.setSQL("{call SP_UPDATE_INTRDAY_LMT_EXP_DATE ('"+currDateFormat+"','"+nextdateFormat+"')}");
			    boolean cnt = dbUtil.execute();
			    dbUtil.commit();
			} catch (Exception e) {
				DefaultLogger.error(this,"EndOfDayBatchServiceImpl Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : updateIntraDayLimitAvailable())" );
				e.printStackTrace();
			}finally{
				try {
					if (dbUtil != null) {
						dbUtil.close();
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		DefaultLogger.debug(this, " updateIntraDayLimitAvailable() ends ==== ");
    }*/
	
	private IEODStatus updateIntraDayLimitAvailable(Date currDate, Date nextdate,IEODStatus eodStatus) {
		DefaultLogger.debug(this, " updateIntraDayLimitAvailable() starts ==== ");
		//String sql="Update CMS_EOD_STATUS set STATUS='"+eodStatus+"' where ID = '"++"'";
		
		DBUtil dbUtil = null;
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				SimpleDateFormat d=new SimpleDateFormat("dd-MMM-yy");
				System.out.println("currDate......"+currDate);
				System.out.println("nextdate......"+nextdate);
				String currDateFormat = d.format(currDate);
				System.out.println("currDateFomat......"+currDateFormat);
				String nextdateFormat = d.format(nextdate);
				System.out.println("nextdateFormat......"+nextdateFormat);
				
				dbUtil=new DBUtil();
				dbUtil.setSQL("{call SP_UPDATE_INTRDAY_LMT_EXP_DATE ('"+currDateFormat+"','"+nextdateFormat+"')}");
			    boolean cnt = dbUtil.execute();
			    dbUtil.commit();
			    eodStatus.setStatus(EODConstants.STATUS_PASS);
			} catch (Exception e) {
				eodStatus.setStatus(EODConstants.STATUS_FAIL);
				System.out.println("Exception caught in EndOfDayBatchServiceImpl (System Date : "+Calendar.getInstance().getTime()+") (Method : updateIntraDayLimitAvailable()).... e=>"+e);
				DefaultLogger.error(this,"EndOfDayBatchServiceImpl Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : updateIntraDayLimitAvailable())" );
				e.printStackTrace();
			}finally{
				try {
					if (dbUtil != null) {
						dbUtil.close();
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else if(EODConstants.STATUS_PASS.equalsIgnoreCase(eodStatus.getStatus())
				|| EODConstants.STATUS_DONE.equalsIgnoreCase(eodStatus.getStatus())) {
			eodStatus.setStatus(EODConstants.STATUS_DONE);
		}
		DefaultLogger.debug(this, " updateIntraDayLimitAvailable() ends ==== ");
		return eodStatus;
    }
}
