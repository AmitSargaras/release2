package com.integrosys.cms.batch.finware;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.ubs.IUbsErrDetLog;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;
import com.integrosys.cms.batch.ubs.OBUbsErrDetLog;
import com.integrosys.cms.batch.ubs.OBUbsErrorLog;
import com.integrosys.cms.ui.finwareupload.dao.IFinwareHibernateDAO;

public class FinwareDaoImpl extends JdbcDaoSupport implements IFinwarecopyDao {
	private String updateLmtUtilAmt = "UPDATE SCI_LSP_SYS_XREF SET RELEASED_AMOUNT = ?,UTILIZED_AMOUNT =?,UPLOAD_STATUS=? WHERE FACILITY_SYSTEM_ID = ? AND SERIAL_NO=?";

	private String updateStatus="UPDATE SCI_LSP_SYS_XREF SET UPLOAD_STATUS='N'";
	
	private String selectCustDetails="SELECT FACILITY_SYSTEM_ID,SERIAL_NO FROM SCI_LSP_SYS_XREF WHERE UPLOAD_STATUS='N'";
	private IFinwareHibernateDAO finwareHibernateDao;
	public IFinwareHibernateDAO getFinwareHibernateDao() {
		return finwareHibernateDao;
	}

	public void setFinwareHibernateDao(IFinwareHibernateDAO finwareHibernateDao) {
		this.finwareHibernateDao = finwareHibernateDao;
	}

	public void insertData(ArrayList result,String fileName,String uploadId,IUbsErrDetLog obUbsErrDetLog[]) throws IncompleteBatchJobException {
		int noOfRecUpdated = 0;
		int isUpdated=0;
		String cust_id = null;
		Double limit_Amt=null;
		Double util_Amt=null;
		String sr_No=null;
		String upload_status="";
		List selectCustDetails=new ArrayList();
		int succRd=0;
		int fldRd=0;
		Timestamp st = null;
		Date date = null;
		IUbsErrorLog objUbsError=new OBUbsErrorLog();
		Set errSet=new HashSet();
		String errMsg="";
		Set errInd=new HashSet();
		int newInd=0;
		if(obUbsErrDetLog!=null)
		{
			for(int i=0;i<obUbsErrDetLog.length;i++)
			{
				errInd.add(obUbsErrDetLog[i].getRecordNo());
			}
		}
		if (result == null || result.size() <= 0) {
			throw new IncompleteBatchJobException(
					"Data to be instered to DB is null or empty");
		}
		try {
			for (int index = 0; index < result.size(); index++) {
				HashMap eachDataMap = (HashMap) result.get(index);
				limit_Amt=new Double(Double.parseDouble(eachDataMap.get("LIMIT_AMOUNT").toString()));
				util_Amt=new Double(Double.parseDouble(eachDataMap.get("UTILIZATION_AMOUNT").toString()));
				cust_id = eachDataMap.get("CUSTOMER_ID").toString();
				sr_No=eachDataMap.get("SR_NO").toString();
				upload_status="Y";
				isUpdated=updateLmtUtilAmt(limit_Amt,util_Amt,upload_status, cust_id,sr_No);
				errMsg="Combination of Customer_id, Sr_no i.e. ("+cust_id+","+sr_No+") not found in CMS System.";
				if(isUpdated==0)
				{
					newInd=getRecordNo(errInd,newInd);
					st = new Timestamp(System.currentTimeMillis());
					date =new Date(st.getTime());
					IUbsErrDetLog obUbsErrDet=new OBUbsErrDetLog();
					obUbsErrDet.setPtId(uploadId);
					obUbsErrDet.setRecordNo(index+1+"");					
					obUbsErrDet.setErrorMsg(errMsg);
					obUbsErrDet.setTime(date);
					errSet.add(obUbsErrDet);
					fldRd++;
				}
				else
				{
					noOfRecUpdated=noOfRecUpdated+isUpdated;
					succRd++;
				}
				newInd++;
			}
			if(obUbsErrDetLog!=null)
			{
				fldRd=fldRd+obUbsErrDetLog.length;
				for(int i=0;i<obUbsErrDetLog.length;i++)
				{
					errSet.add(obUbsErrDetLog[i]);
				}
			}
			st = new Timestamp(System.currentTimeMillis());
			date =new Date(st.getTime());
			selectCustDetails=selectCustDetails();
			for(int i=0;i<selectCustDetails.size();i++)
			{
				errMsg="Combination of Customer_id,Sr_no i.e. ("+selectCustDetails.get(i)+")not found in Uploaded file.";
				IUbsErrDetLog obUbsErrDet=new OBUbsErrDetLog();
				obUbsErrDet.setPtId(uploadId);
				obUbsErrDet.setRecordNo("");					
				obUbsErrDet.setErrorMsg(errMsg);
				obUbsErrDet.setTime(date);
				errSet.add(obUbsErrDet);
			}
			objUbsError.setUploadId(uploadId);
			objUbsError.setFileName(fileName);
			objUbsError.setNoOfRecords(succRd+fldRd+"");
			objUbsError.setSuccessRecords(succRd+"");
			objUbsError.setFailedRecords(fldRd+"");
			objUbsError.setUploadTime(date);
			objUbsError.setErrEntriesSet(errSet);
			getFinwareHibernateDao().insertErrLog(objUbsError);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
					"Unable to update/insert dad retrived form Text file");

		}
	
	}

	public int updateLmtUtilAmt(Double changedLimitAmt,Double changedUtilizationAmt,String upstatus,String Cust_Id, String Sr_No) {
		return getJdbcTemplate().update(updateLmtUtilAmt,new Object[] { changedLimitAmt, changedUtilizationAmt,upstatus, Cust_Id, Sr_No });
	}
	
	public List selectCustDetails()
	{
		List lstCustDetails=new ArrayList();
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String data="";
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(selectCustDetails);
			rs = dbUtil.executeQuery();			
			while(rs.next())
			{
				data=rs.getString("FACILITY_SYSTEM_ID")+","+rs.getString("SERIAL_NO");
				lstCustDetails.add(data);
			}
			
			
		} catch (DBConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		getJdbcTemplate().update(updateStatus);
		return lstCustDetails;
	}
	public static void finalize(DBUtil dbUtil, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public int getRecordNo(Set set,int recordno)
	{
		for(int i=1;i<=set.size();i++)
		{
		if(set.contains(recordno+1+""))
		{
			recordno++;
		}
		else
		{
			break;
		}
		}
		return recordno;
	}
}

