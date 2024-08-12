package com.integrosys.cms.batch.eod;

import java.util.Map;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.factory.BatchJob;

public class SystemHandoffBatchMain implements BatchJob {

	public void execute(Map context) throws BatchJobException {
		endOfDayBatchService.checkSystemHandoff();
	}

	IEndOfDayBatchService endOfDayBatchService;
	
	
	public IEndOfDayBatchService getEndOfDayBatchService() {
		return endOfDayBatchService;
	}


	public void setEndOfDayBatchService(IEndOfDayBatchService endOfDayBatchService) {
		this.endOfDayBatchService = endOfDayBatchService;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
