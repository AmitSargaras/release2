package com.integrosys.cms.batch.eod;

import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.InvalidParameterBatchJobException;
import com.integrosys.cms.batch.factory.BatchJob;
/**
 * This is main class for End of Day master sync process which triggers the job.
 * 
 * @author anil.pandey
 * @createdOn Sep 26, 2014 11:32:29 AM
 *
 */
public class EndOfDaySyncMastersMain  implements BatchJob  {

	IEndOfDaySyncMastersService endOfDaySyncMastersService;
	
	/**
	 * @return the endOfDaySyncMastersService
	 */
	public IEndOfDaySyncMastersService getEndOfDaySyncMastersService() {
		return endOfDaySyncMastersService;
	}

	/**
	 * @param endOfDaySyncMastersService the endOfDaySyncMastersService to set
	 */
	public void setEndOfDaySyncMastersService(IEndOfDaySyncMastersService endOfDaySyncMastersService) {
		this.endOfDaySyncMastersService = endOfDaySyncMastersService;
	}

	public void execute(Map contextMap) throws BatchJobException {
		DefaultLogger.debug(this, "EndOfDaySyncMastersMain::: execute Method called()"+(String) contextMap.get("syncDirection"));
		String syncDirection = (String) contextMap.get("syncDirection");
		if(IEodSyncConstants.SYNC_DIRECTION_CLIMSTOCPS.equals(syncDirection)){
			DefaultLogger.debug(this, "syncDirection::"+syncDirection);
			getEndOfDaySyncMastersService().performEODSyncClimsToCps();
		}else if(IEodSyncConstants.SYNC_DIRECTION_CPSTOCLIMS.equals(syncDirection)){
			DefaultLogger.debug(this, "syncDirection::"+syncDirection);
			getEndOfDaySyncMastersService().performEODSyncCpsToClims();
		}else{
			DefaultLogger.debug(this, "Invalid Sync Direction");
			throw new InvalidParameterBatchJobException("Invalid Sync Direction.");
		}
	}



}
