package com.integrosys.cms.batch.finware;

import java.util.ArrayList;

import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.ubs.IUbsErrDetLog;

public interface IFinwarecopyDao {
	public void insertData(ArrayList result,String fileName,String uploadId,IUbsErrDetLog obUbsErrDetLog[])throws IncompleteBatchJobException;
	public int updateLmtUtilAmt(Double changedLimitAmt,Double changedUtilizationAmt,String upstatus,String Cust_Id, String Sr_No) ;
}
