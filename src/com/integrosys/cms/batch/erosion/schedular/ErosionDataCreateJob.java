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
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;

public class ErosionDataCreateJob implements IErosionFileConstants {

	private final static Logger logger = LoggerFactory.getLogger(ErosionDataCreateJob.class);
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

		new ErosionDataCreateJob().execute();
	}

	public ErosionDataCreateJob() {
	}

	public void execute() {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String erosionServerName = bundle.getString("erosion.server.name");
		logger.debug("<<<<In execute() ErosionDataCreateJob Strating....>>>>" + erosionServerName);
		clearLogs();
		log.append("\n-----<<<<In execute() ErosionDataCreateJob Strating....>>>> (System Date: "+Calendar.getInstance().getTime().toString()+")-----");

 		if (null != erosionServerName && erosionServerName.equalsIgnoreCase("app1")) {
 			log.append("\n <<<<In execute() ErosionDataCreateJob ");
			logger.debug("<<<<In execute() ErosionDataCreateJob ");
			try {
				boolean erosionProcessExecuted = false; 
				boolean errorSuccessFileCheck = false;
				try {
					IGeneralParamEntry generalParamEntries=erosionHelper.getErosionDate();
					Date erosionDate=new Date(generalParamEntries.getParamValue());
					String appDate=new SimpleDateFormat("yyyy-MM-dd").format(erosionDate);
					
					List erosionActivites = erosionHelper.findErosionActivities(true);
					Iterator iter = erosionActivites.iterator();
					IErosionStatus erosionStatus;
					
					log.append("\n Going to Execute Erosion Activities for ErosionDataCreateJob()..appDate= "+appDate);
					logger.debug("Going to Execute Erosion Activities for ErosionDataCreateJob()..appDate="+appDate);
					boolean isFailed=true;
					while (iter.hasNext()) {
						erosionStatus = (IErosionStatus)iter.next();
						isFailed=erosionHelper.performActivity(erosionStatus,true,appDate);
						if(!isFailed) {
							break;
						}else {
							erosionProcessExecuted=true;
						}
					}
					log.append("\n After Execute Erosion Activities ErosionDataCreateJob()... End  ");
					logger.debug("After Execute Erosion Activities ErosionDataCreateJob()... End ");
				}catch (Exception e){
					logger.error("Got exception in execute() of ErosionDataCreateJob() "+e.getMessage());
					log.append("\n Got exception in execute() of ErosionDataCreateJob() ");
					errorSuccessFileCheck=true;
					e.printStackTrace();
				}finally{
					logger.debug("inside Finally block >> ErosionDataCreateJob()");
					log.append("\n inside Finally block >> ErosionDataCreateJob()="+errorSuccessFileCheck);
					erosionHelper.finalizeErosion(erosionProcessExecuted,true);
					
					if(errorSuccessFileCheck) {
						logfileName = PropertyManager.getValue("erosion.data.logPath")+PropertyManager.getValue("erosionDataGenerationFailFile");
						generateErosionReport();
					}else {
						logfileName = PropertyManager.getValue("erosion.data.logPath")+PropertyManager.getValue("erosionDataGenerationSuccessFile");
						generateErosionReport();
					}
					
				}
			} catch (Exception e) {
				logger.debug( "ErosionDataCreateJob() in catch Exception......" + e.getMessage());
				log.append("\n ErosionDataCreateJob() in catch Exception...... ");
				logfileName = PropertyManager.getValue("erosion.data.logPath")+PropertyManager.getValue("erosionDataGenerationFailFile");
				generateErosionReport();
				e.printStackTrace();
			}
		}

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
