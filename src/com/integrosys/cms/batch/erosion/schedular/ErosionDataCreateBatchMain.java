package com.integrosys.cms.batch.erosion.schedular;

import java.util.Map;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.factory.BatchJob;

public class ErosionDataCreateBatchMain implements BatchJob {

	ErosionDataCreateJob erosionDataCreateJob;
	
	public ErosionDataCreateJob getErosionDataCreateJob() {
		return erosionDataCreateJob;
	}

	public void setErosionDataCreateJob(ErosionDataCreateJob erosionDataCreateJob) {
		this.erosionDataCreateJob = erosionDataCreateJob;
	}

	public void execute(Map context) throws BatchJobException {
		// TODO Auto-generated method stub
		erosionDataCreateJob.execute();
	}

}
