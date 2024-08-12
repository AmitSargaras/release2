package com.integrosys.cms.ui.finwareupload.dao;

import java.util.ArrayList;

import com.integrosys.cms.batch.ubs.IUbsErrDetLog;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;

	/**
	 * 
	 * @author sandiip.shinde
	 * @since 14-04-2011
	 */

public interface IFinwareUploadDAO {
	
	IUbsErrorLog insertFinwareUpload(ArrayList result,String fileName,String uploadId,IUbsErrDetLog obUbsErrDetLog[]);
	public int updateLmtUtilAmt(Double changedUtilizationAmt,String upstatus,String Cust_Id, String Sr_No) ;
}
