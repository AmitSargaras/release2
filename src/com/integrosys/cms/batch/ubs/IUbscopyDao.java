package com.integrosys.cms.batch.ubs;

import java.util.ArrayList;

import com.integrosys.cms.batch.IncompleteBatchJobException;

public interface IUbscopyDao {
	public void insertData(ArrayList result,String fileName,String uploadId,IUbsErrDetLog obUbsErrDetLog[])throws IncompleteBatchJobException;
	public int updateLmtUtilAmt(Double chLmtAmt,Double chUtilAmt,String fgCurCode,Double fgLmtAmt,Double fgUtlAmt, String upstatus,String Cust_Id, String Line_No,String Sr_No);
}
