package com.integrosys.cms.ui.ubsupload.dao;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.cms.batch.ubs.IUbsErrDetLog;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;

	/**
	 * 
	 * @author sandiip.shinde
	 * @since 14-04-2011
	 */

public interface IUbsUploadDAO {
	
	public IUbsErrorLog insertUbsUpload( ArrayList result,String fileName,String uploadId,IUbsErrDetLog[] obUbsErrDetLog);
	public int updateLmtUtilAmt(Double chLmtAmt,Double chUtilAmt,String fgCurCode,Double fgLmtAmt,Double fgUtlAmt,String upstatus, String Cust_Id, String Line_No,String Sr_No) ;
}
