package com.integrosys.cms.batch.eod;

import java.util.Map;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.factory.BatchJob;

//Created  By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD
public class EndOfDayBaselUpdateReportMain  implements BatchJob  {

	IEndOfDayBatchService endOfDayBatchService;
	
	
	public IEndOfDayBatchService getEndOfDayBatchService() {
		return endOfDayBatchService;
	}


	public void setEndOfDayBatchService(IEndOfDayBatchService endOfDayBatchService) {
		this.endOfDayBatchService = endOfDayBatchService;
	}


	public void execute(Map arg0) throws BatchJobException {
		endOfDayBatchService.performBaselUpdateReport();
		
		
	}



}
