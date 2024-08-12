package com.integrosys.cms.batch.erosion.schedular;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.erosion.bus.IErosionStatus;
import com.integrosys.cms.app.ftp.FileUploadFtpService;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;

public class ErosionFileWriteJob implements IErosionFileConstants {

	private final static Logger logger = LoggerFactory.getLogger(ErosionFileWriteJob.class);
	private StringBuffer log = new StringBuffer();
	String logfileName;
	private IErosionHelper erosionHelper;

	public IErosionHelper getErosionHelper() {
		return erosionHelper;
	}

	public void setErosionHelper(IErosionHelper erosionHelper) {
		this.erosionHelper = erosionHelper;
	}

	public static void main(String[] args) {

		new ErosionFileWriteJob().execute();
	}

	public ErosionFileWriteJob() {
	}

	public void execute() {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String erosionServerName = bundle.getString("erosion.server.name");
		logger.debug( "<<<<In execute() ErosionFileWriteJob() Strating....>>>>" + erosionServerName);
		clearLogs();
		log.append("\n-----<<<<In execute() ErosionFileWriteJob Strating....>>>> (System Date: "+Calendar.getInstance().getTime().toString()+")-----");

		if (null != erosionServerName && erosionServerName.equalsIgnoreCase("app1")) {
			log.append("\n <<<<In execute() ErosionFileWriteJob ");
			logger.debug( "<<<<In execute() ErosionFileWriteJob ");
			boolean erosionProcessExecuted = false;
			boolean errorSuccessFileCheck = false;
			try {
				IGeneralParamEntry generalParamEntries=erosionHelper.getErosionDate();
				Date erosionDate=new Date(generalParamEntries.getParamValue());
				String appDate=new SimpleDateFormat("yyyy-MM-dd").format(erosionDate);
				
				List erosionActivites = erosionHelper.findErosionActivities(false);
				Iterator iter = erosionActivites.iterator();
				IErosionStatus erosionStatus;

				log.append("\n Going to Execute archiveLocalFiles() for ErosionFileWriteJob()..appDate="+appDate);
				logger.debug( "Going to Execute archiveLocalFiles() for ErosionFileWriteJob()..appDate="+appDate);
				try {
					archiveLocalFiles(bundle.getString(FTP_EROSION_UPLOAD_LOCAL_DIR),bundle.getString(FTP_EROSION_BACKUP_LOCAL_DIR));
				} catch (IOException e) {
					log.append("\n Got exception while archiveLocalFiles() for ErosionFileWriteJob() ");
					logger.error( "Got exception while archiveLocalFiles() for ErosionFileWriteJob()");
					e.printStackTrace();
				}
				
				log.append("\n Going to Execute Erosion Activities for ErosionFileWriteJob()... Start ");
				logger.debug("Going to Execute Erosion Activities for ErosionFileWriteJob()... Start");
				boolean isFailed = true;
				while (iter.hasNext()) {
					erosionStatus = (IErosionStatus) iter.next();
					isFailed = erosionHelper.performActivity(erosionStatus, false, appDate);
					if (!isFailed) {
						break;
					} else {
						erosionProcessExecuted = true;
					}
				}
				log.append("\n Going to Execute Erosion Activities for ErosionFileWriteJob()... End ");
				logger.debug("Going to Execute Erosion Activities for ErosionFileWriteJob()... End");
				
				// update the next execution date if erosionProcessExecuted=true
				if (erosionProcessExecuted) {
					log.append("\n Before update next execution date for ErosionFileWriteJob()..erosionProcessExecuted= "+erosionProcessExecuted);
					logger.debug( "Before update next execution date for ErosionFileWriteJob()..erosionProcessExecuted= "+erosionProcessExecuted);
					String newExecutionDate="";
					Calendar cal = Calendar.getInstance();
					cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(appDate));
					int res = 0;

					// if current execution date is month end
					if (cal.get(cal.DAY_OF_MONTH) != 24) {
						cal.add(cal.MONTH, 1);
						cal.set(cal.DAY_OF_MONTH, cal.getActualMaximum(cal.DAY_OF_MONTH));
						res = getMonthDays(cal.get(cal.MONTH)+1, cal.get(cal.YEAR));
						
						if (res == 28) {
							cal.add(cal.DAY_OF_MONTH, -4);
						} else if (res == 29) {
							cal.add(cal.DAY_OF_MONTH, -5);
						} else if (res == 30) {
							cal.add(cal.DAY_OF_MONTH, -6);
						} else {
							cal.add(cal.DAY_OF_MONTH, -7);
						}
						
						newExecutionDate = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
						log.append("\n If current execution date is month end - ErosionFileWriteJob()..newExecutionDate="+newExecutionDate);
						logger.debug( "If current execution date is month end - ErosionFileWriteJob()..newExecutionDate="+newExecutionDate);
						erosionHelper.updateGeneralParamErosionDate(newExecutionDate,erosionDate);
					} else {
						//if current execution date is not month end
						cal.set(cal.DAY_OF_MONTH, cal.getActualMaximum(cal.DAY_OF_MONTH));
						newExecutionDate = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
						log.append("\n If current execution date is not month end - ErosionFileWriteJob()..newExecutionDate="+newExecutionDate);
						logger.debug( "If current execution date is not month end - ErosionFileWriteJob()..newExecutionDate="+newExecutionDate);
						erosionHelper.updateGeneralParamErosionDate(newExecutionDate,erosionDate);
					}
				}
				log.append("\n After update next execution date for ErosionFileWriteJob()..");
				logger.debug( "After update next execution date for ErosionFileWriteJob()..");
			} catch (Exception e) {
				errorSuccessFileCheck=true;
				logger.error("Got exception in ErosionFileWriteJob()...:" + e.getMessage());
				log.append("\n Got exception in execute() of ErosionFileWriteJob() ");
				e.printStackTrace();
			} finally {
				log.append("\n inside Finally block >> ErosionFileWriteJob()="+errorSuccessFileCheck);
				logger.debug( "Inside Finally block >> ErosionFileWriteJob()");
				erosionHelper.finalizeErosion(erosionProcessExecuted,false);
				
				if(errorSuccessFileCheck) {
					logfileName = PropertyManager.getValue("erosion.file.logPath")+PropertyManager.getValue("erosionFileGenerationFailFile");
					generateErosionReport();
				}else {
					logfileName = PropertyManager.getValue("erosion.file.logPath")+PropertyManager.getValue("erosionFileGenerationSuccessFile");
					generateErosionReport();
				}
			}
		}
	}
	
	private void archiveLocalFiles(String localDir, String localArchiveDir) throws IOException {
		File localDirFile = new File(localDir);
		String[] fileList = localDirFile.list();
		if(fileList!=null&& fileList.length>0){
			for (int i = 0; i < fileList.length; i++) {
				FileUploadFtpService.moveFile(new File(localDir+fileList[i]), new File(localArchiveDir+fileList[i]));
			}
		}
	}
	
	private int getMonthDays(int month, int year) {
	    int daysInMonth ;
	    if (month == 4 || month == 6 || month == 9 || month == 11) {
	        daysInMonth = 30;
	    }
	    else {
	        if (month == 2) {
	            daysInMonth = (year % 4 == 0) ? 29 : 28;
	        } else {
	            daysInMonth = 31;
	        }
	    }
	    return daysInMonth;
	}
	
	private void clearLogs() {
		if (log != null && log.length() > 0) {
			log.delete(0, log.length());
		}
	}
	
	private void generateErosionReport() {
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

}
