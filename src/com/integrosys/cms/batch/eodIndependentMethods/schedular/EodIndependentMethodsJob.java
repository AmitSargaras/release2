package com.integrosys.cms.batch.eodIndependentMethods.schedular;

import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.eod.refreshMv.IRefreshMvDao;
import com.integrosys.cms.app.generalparam.bus.GeneralParamDaoImpl;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;


public class EodIndependentMethodsJob  {

	/*private IRefreshMvDao refreshMvDao;
	
	private IGeneralParamDao generalParam;
	
	public IGeneralParamDao getGeneralParam() {
		return generalParam;
	}

	public void setGeneralParam(IGeneralParamDao generalParam) {
		this.generalParam = generalParam;
	}
	
	public IRefreshMvDao getRefreshMvDao() {
		return refreshMvDao;
	}

	public void setRefreshMvDao(IRefreshMvDao refreshMvDao) {
		this.refreshMvDao = refreshMvDao;
	}*/

	private final static Logger logger = LoggerFactory.getLogger(EodIndependentMethodsJob.class);
	

	public static void main(String[] args) {

		new EodIndependentMethodsJob().execute();
	}

	public EodIndependentMethodsJob() {


	}

	/**
	 * This job is run and executed by quartz schedular.
	 * For more details refer to schedular configuration in 
	 * config\spring\batch\fcubsLimitFile\AppContext_Master.xml
	 * 
	 * Schedular has been designed to carry out the following activities
	 * 1. Fetch the pending released line details
	 * 2. Generate the file and add all the records as per the format shared
	 * 3. Upload the file to remote location
	 * 4. Store the records in the log table.
	 * 5.Move the file in backup folder
	 * 6.Delete file after 7 days.
	 */

	public void execute() {	
		try {
			System.out.println(" EodIndependentMethodsJob Started ... ");
			ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
			String eodIndependentMethodsServerName = bundle1.getString("eodIndependentMethods.server.name");
			
			System.out.println(" EodIndependentMethodsJob eodIndependentMethods.server.name =>"+eodIndependentMethodsServerName);
			
			if(null!= eodIndependentMethodsServerName && "app1".equalsIgnoreCase(eodIndependentMethodsServerName)){
				
				ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
				IRefreshMvDao refreshMvDao = (IRefreshMvDao)BeanHouse.get("refreshMvDao");
				System.out.println(" EodIndependentMethodsJob going for refreshMvForUserAdminReport method ... ");
				refreshMvDao.refreshMvForUserAdminReport();//1 /// Scheduler Added ******************
				
				System.out.println(" EodIndependentMethodsJob going for refreshMvForEwsStockDeferral method ... ");
				refreshMvDao.refreshMvForEwsStockDeferral(); 
				
				System.out.println(" EodIndependentMethodsJob going for refreshMvForCustomerWiseSecurityReport method ... ");
				refreshMvDao.refreshMvForCustomerWiseSecurityReport(); 
				
				System.out.println(" EodIndependentMethodsJob going for refreshMvForCustomerWiseStockDetailsReport method ... ");
				refreshMvDao.refreshMvForCustomerWiseStockDetailsReport();
				
				System.out.println(" EodIndependentMethodsJob going for resetDiarySequence method ... ");
				resetDiarySequence();
				
				System.out.println(" EodIndependentMethodsJob going for executeCmsInterfaceLogBackupProc method ... ");
				collateralDAO.executeCmsInterfaceLogBackupProc();//1 /// Scheduler Added ******************
				
				//clean facility and liability intefacelog table data except last 7 days.
				
				System.out.println(" EodIndependentMethodsJob going for executeFacilityIntLogBkpProc method ... ");
				collateralDAO.executeFacilityIntLogBkpProc();//1 /// Scheduler Added ******************
				
				System.out.println(" EodIndependentMethodsJob going for executeLiabilityIntLogBkpProc method ... ");
				collateralDAO.executeLiabilityIntLogBkpProc();//1 /// Scheduler Added ******************
				
				//Added to clean IFSCCODE_INTERFACE_LOG table data except latest 7 days data.
				System.out.println(" EodIndependentMethodsJob going for executeIfscCodeInterfaceLogBackupProc method ... ");
				collateralDAO.executeIfscCodeInterfaceLogBackupProc();
				
				System.out.println(" EodIndependentMethodsJob going for executeDeferralInsurancePolicy method ... ");
				collateralDAO.executeDeferralInsurancePolicy();//1 /// Scheduler Added ******************
				
				System.out.println(" EodIndependentMethodsJob going for executeDeferralGCPolicy method ... ");
				collateralDAO.executeDeferralGCPolicy();//1 /// Scheduler Added ******************
				
				System.out.println(" EodIndependentMethodsJob going for insertDeferralChecklistProperty method ... ");
				collateralDAO.insertDeferralChecklistProperty();//1 /// Scheduler Added ******************
				
				
				IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
				IGeneralParamEntry generalParamEntry2 = generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
				Date date = new Date(generalParamEntry2.getParamValue());
				Calendar currDate = Calendar.getInstance();
				currDate.setTime(date);

				Calendar nextdate = Calendar.getInstance();
				nextdate.setTime(date);
				nextdate.add(Calendar.DATE, 1);
				
				Calendar immdNextDate = Calendar.getInstance();
				immdNextDate.setTime(date);
				immdNextDate.add(Calendar.DATE, 1);
				
				System.out.println(" EodIndependentMethodsJob going for executePSRActivities method ... ");
				collateralDAO.executePSRActivities(immdNextDate.getTime(),nextdate.getTime());
				
				System.out.println(" EodIndependentMethodsJob going for refreshMvForSPRfreshSecMapChargeIdMV method ... ");
		        refreshMvDao.refreshMvForSPRfreshSecMapChargeIdMV();
		        
		        System.out.println(" EodIndependentMethodsJob going for refreshMvForAuditTrailReportDB method ... ");
		        refreshMvDao.refreshMvForAuditTrailReportDB();
		        System.out.println(" EodIndependentMethodsJob Completed for refreshMvForAuditTrailReportDB method ... ");
				
				System.out.println(" EodIndependentMethodsJob Done ... ");
			}

		}
		
		catch (Exception e) {
			System.out.println("EodIndependentMethodsJob in catch Exception......e=>"+e);
			DefaultLogger.debug(this,"EodIndependentMethodsJob in catch Exception......"+e.getMessage());
			e.printStackTrace();
		}

	}

	
	public void resetDiarySequence() {
		DefaultLogger.debug(this, " resetDiarySequence() starts ==== ");
		System.out.println(" resetDiarySequence() starts ====");
		//String sql="Update CMS_EOD_STATUS set STATUS='"+eodStatus+"' where ID = '"++"'";
		
		DBUtil dbUtil = null;
			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL("{call RESET_SEQ_CMS_DIARY_NUMBER_SEQ ('CMS_DIARY_NUMBER_SEQ')}");
			    boolean cnt = dbUtil.execute();
			    dbUtil.commit();
			} catch (Exception e) {
				System.out.println("EodIndependentMethodsJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : resetDiarySequence())");
				DefaultLogger.error(this,"EodIndependentMethodsJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : resetDiarySequence())" );
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
		System.out.println(" resetDiarySequence() ends ====");
    }

	
}
