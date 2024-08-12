package com.integrosys.cms.batch.erosion.schedular;

import java.util.Map;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.factory.BatchJob;

public class ErosionFileGenerationBatchMain implements BatchJob{

	ErosionFileWriteJob erosionFileWriteJob;
	
	public ErosionFileWriteJob getErosionFileWriteJob() {
		return erosionFileWriteJob;
	}

	public void setErosionFileWriteJob(ErosionFileWriteJob erosionFileWriteJob) {
		this.erosionFileWriteJob = erosionFileWriteJob;
	}

	public void execute(Map context) throws BatchJobException {
		// TODO Auto-generated method stub
		erosionFileWriteJob.execute();
		
	}

}
