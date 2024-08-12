package com.integrosys.cms.batch.eod;

import java.util.Map;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.factory.BatchJob;

public class PosidexFileGenerationMain  implements BatchJob  {

	IGeneratePosidexReportFileService generatePosidexReportFileService;
	
	public IGeneratePosidexReportFileService getGeneratePosidexReportFileService() {
		return generatePosidexReportFileService;
	}

	public void setGeneratePosidexReportFileService(
			IGeneratePosidexReportFileService generatePosidexReportFileService) {
		this.generatePosidexReportFileService = generatePosidexReportFileService;
	}

	public void execute(Map arg0) throws BatchJobException {
		try {
			getGeneratePosidexReportFileService().generateFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
