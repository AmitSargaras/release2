package com.integrosys.cms.batch.partycam;

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

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.ui.partycamupload.dao.IPartyCamHibernateDAO;

public class PartyCamDaoImpl extends JdbcDaoSupport implements IPartyCamcopyDao {
	
	private IPartyCamHibernateDAO partyCamHibernateDao;

	/*
	 * private String updateLmtUtilAmt = "UPDATE SCI_LSP_SYS_XREF SET
	 * RELEASED_AMOUNT = ?,UTILIZED_AMOUNT =?,UP_FOREIGN_CUR_CODE=?," +
	 * "UP_FOREIGN_LMT_AMT=?,UP_FOREIGN_UTL_AMT=? WHERE FACILITY_SYSTEM_ID = ?
	 * AND LINE_NO=? AND SERIAL_NO=?";
	 */

	private String updateLmtUtilAmt = "UPDATE SCI_LSP_SYS_XREF SET RELEASED_AMOUNT = ?,UTILIZED_AMOUNT =?,UP_FOREIGN_CUR_CODE=?,"
			+ "UP_FOREIGN_LMT_AMT=?,UP_FOREIGN_UTL_AMT=?,UPLOAD_STATUS=? WHERE FACILITY_SYSTEM_ID = ? AND LINE_NO=? AND SERIAL_NO=?";

	private String updateStatus="UPDATE SCI_LSP_SYS_XREF SET UPLOAD_STATUS='N'";
	
	private String selectCustDetails="SELECT FACILITY_SYSTEM_ID,LINE_NO,SERIAL_NO FROM SCI_LSP_SYS_XREF WHERE UPLOAD_STATUS='N' and FACILITY_SYSTEM = 'UBS-LIMITS'";

	public void insertData(ArrayList result,String fileName,String uploadId,IPartyCamErrDetLog obUbsErrDetLog[]) throws IncompleteBatchJobException {
		
		int noOfRecUpdated = 0;
		int isUpdated=0;
		String cust_id = null;		
		Double limit_Amt=null;
		Double util_Amt=null;
		String line_No=null;
		String sr_No=null;
		String cur_Code=null;
		Double fgLimitAmt=null;
		Double fgUtilAmt=null;
		String upload_status="";
		int succRd=0;
		int fldRd=0;
		Timestamp st = null;
		Date date = null;
		IPartyCamErrorLog objPartyCamError=new OBPartyCamErrorLog();
		Set errSet=new HashSet();
		String errMsg="";
		Set errInd=new HashSet();
		int newInd=0;
		List selectCustDetails=new ArrayList();
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
			Amount limitAmt = null;
			Amount changedLimitAmt = null;
			Amount utilizationAmt = null;
			Amount changedUtilizationAmt = null;
			for (int index = 0; index < result.size(); index++) {
				HashMap eachDataMap = (HashMap) result.get(index);
				cur_Code=(String) eachDataMap.get("CURRENCY_CODE");
				fgLimitAmt=new Double(eachDataMap.get("LIMIT_AMOUNT").toString());
				fgUtilAmt=new Double(eachDataMap.get("UTILIZATION_AMOUNT").toString());
				limitAmt = new Amount(Double.parseDouble(eachDataMap.get("LIMIT_AMOUNT").toString()), "INR");
				changedLimitAmt = CommonUtil.convertAmount(limitAmt,cur_Code);
				utilizationAmt = new Amount(Double.parseDouble(eachDataMap.get("UTILIZATION_AMOUNT").toString()), "INR");
				changedUtilizationAmt = CommonUtil.convertAmount(utilizationAmt,cur_Code );               
				cust_id = eachDataMap.get("CUSTOMER_ID").toString();
				limit_Amt=new Double(changedLimitAmt.getAmount());
				util_Amt=new Double(changedUtilizationAmt.getAmount());
				line_No=eachDataMap.get("LINE_NO").toString();
				sr_No=eachDataMap.get("SR_NO").toString();
				upload_status="Y";
				isUpdated=updateLmtUtilAmt(limit_Amt,util_Amt, cur_Code,fgLimitAmt,fgUtilAmt,upload_status,cust_id,line_No,sr_No );
				errMsg="Combination of Customer_id, Line_no ,Sr_no i.e. ("+cust_id+","+line_No+","+sr_No+") not found in CMS System.";
				if(isUpdated==0)
				{
					newInd=getRecordNo(errInd,newInd);
					st = new Timestamp(System.currentTimeMillis());
					date =new Date(st.getTime());
					IPartyCamErrDetLog obUbsErrDet=new OBPartyCamErrDetLog();
					obUbsErrDet.setPtId(uploadId);
					obUbsErrDet.setRecordNo(newInd+1+"");					
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
				errMsg="Combination of Customer_id, Line_no ,Sr_no i.e. ("+selectCustDetails.get(i)+")not found in Uploaded file.";
				IPartyCamErrDetLog obPartyCamErrDet=new OBPartyCamErrDetLog();
				obPartyCamErrDet.setPtId(uploadId);
				obPartyCamErrDet.setRecordNo("");					
				obPartyCamErrDet.setErrorMsg(errMsg);
				obPartyCamErrDet.setTime(date);
				errSet.add(obPartyCamErrDet);
			}
			objPartyCamError.setUploadId(uploadId);
			objPartyCamError.setFileName(fileName);
			objPartyCamError.setNoOfRecords(succRd+fldRd+"");
			objPartyCamError.setSuccessRecords(succRd+"");
			objPartyCamError.setFailedRecords(fldRd+"");
			objPartyCamError.setUploadTime(date);
			objPartyCamError.setErrEntriesSet(errSet);
			
			getPartyCamHibernateDao().insertErrLog(objPartyCamError);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
					"Unable to update/insert dad retrived form CSV file");

		}
	}

	public int updateLmtUtilAmt(Double chLmtAmt, Double chUtilAmt,
			String fgCurCode, Double fgLmtAmt, Double fgUtlAmt,String upstatus, String Cust_Id,
			String Line_No, String Sr_No) {
		return getJdbcTemplate().update(
				updateLmtUtilAmt,
				new Object[] { chLmtAmt, chUtilAmt, fgCurCode, fgLmtAmt,
						fgUtlAmt,upstatus, Cust_Id, Line_No, Sr_No });
	}



	public IPartyCamHibernateDAO getPartyCamHibernateDao() {
		return partyCamHibernateDao;
	}

	public void setPartyCamHibernateDao(IPartyCamHibernateDAO partyCamHibernateDao) {
		this.partyCamHibernateDao = partyCamHibernateDao;
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
				data=rs.getString("FACILITY_SYSTEM_ID")+","+rs.getString("LINE_NO")+","+rs.getString("SERIAL_NO");
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
