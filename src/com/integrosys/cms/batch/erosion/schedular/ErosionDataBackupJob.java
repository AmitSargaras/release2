package com.integrosys.cms.batch.erosion.schedular;

import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.integrosys.base.techinfra.logger.DefaultLogger;

public class ErosionDataBackupJob implements IErosionFileConstants {

	private final static Logger logger = LoggerFactory.getLogger(ErosionFileWriteJob.class);
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

	public ErosionDataBackupJob() {
	}

	public void execute() {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String erosionServerName = bundle.getString("erosion.server.name");

		DefaultLogger.debug(this, "<<<<In execute() ErosionDataBackupJob Strating....>>>>" + erosionServerName);

		if (null != erosionServerName && erosionServerName.equalsIgnoreCase("app1")) {
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			DefaultLogger.debug(this, "Starting ErosionDataBackupJob");
			// Fetch record for processing
			try {
				DefaultLogger.debug(this, "Before Execute Erosion Data Backup .. ");
				erosionHelper.executeErosionDataBackup();
				DefaultLogger.debug(this, "After Execute Erosion Data Backup .. ");

			} catch (Exception e) {
				DefaultLogger.error(this, "Got error in ErosionDataBackupJob.java...:" + e.getMessage());
				e.printStackTrace();
			} finally {
				DefaultLogger.debug(this, "Inside Finally block >> ErosionDataBackupJob.java");
			}
		}
	}

}
