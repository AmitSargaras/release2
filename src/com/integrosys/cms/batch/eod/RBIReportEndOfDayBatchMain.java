package com.integrosys.cms.batch.eod;

import java.util.Map;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.factory.BatchJob;

public class RBIReportEndOfDayBatchMain  implements BatchJob  {

	IEndOfDayBatchService endOfDayBatchService;
	
	
	public IEndOfDayBatchService getEndOfDayBatchService() {
		return endOfDayBatchService;
	}


	public void setEndOfDayBatchService(IEndOfDayBatchService endOfDayBatchService) {
		this.endOfDayBatchService = endOfDayBatchService;
	}


	public void execute(Map arg0) throws BatchJobException {
		endOfDayBatchService.performAdfRbi();
		
		
	}



}
