package com.integrosys.cms.batch.bhavcopy;

import java.util.ArrayList;
import java.util.Map;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.batch.factory.BatchJob;

public class BhavcopyMain implements BatchJob {

	private IBhavcopyDao dao;

	public void execute(Map context) throws BatchJobException {
		ProcessDataFile dataFile = new ProcessDataFile(
				"batch-job.properties","BHAVCOPY");
		ArrayList resultList = dataFile.doProcessData();
		// System.out.println("ArrayList : " + resultList);
		int noOfRowsInserted = dao.insertData(resultList);
		// System.out.println("No of rows inserted == " + noOfRowsInserted);
	}

	public IBhavcopyDao getDao() {
		return dao;
	}

	public void setDao(IBhavcopyDao dao) {
		this.dao = dao;
	}

}
