package com.integrosys.cms.batch.finwarefd;

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
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.ubs.IUbsErrDetLog;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;
import com.integrosys.cms.batch.ubs.OBUbsErrDetLog;
import com.integrosys.cms.batch.ubs.OBUbsErrorLog;
import com.integrosys.cms.ui.finwarefdupload.dao.IFinwareFdHibernateDAO;

public class FinwarefdDaoImpl extends JdbcDaoSupport implements
		IFinwarefdcopyDao {

	private IFinwareFdHibernateDAO finwarefdHibernateDao;
	
	private String updateNStatus = "UPDATE CMS_LIEN SET UPLOAD_STATUS='N'";
	
	private String updateStatus="UPDATE CMS_LIEN XYZ SET XYZ.UPLOAD_STATUS='Y' WHERE exists " +
			"(select csh.deposit_reference_number,csh.deposit_amount,csh.deposit_interest_rate from cms_cash_deposit csh "+
			"where csh.deposit_reference_number=? and csh.deposit_amount=? and csh.deposit_interest_rate=?) AND XYZ.LIEN_AMOUNT=?";
	
	
	public IUbsErrorLog compareFinwareFdfile(ArrayList result,String fileName,String uploadId,IUbsErrDetLog obUbsErrDetLog[]) {

		List lstCustDetails=new ArrayList();
		List lstNotUpData=new ArrayList();
		String fdNo=null;		
		Double fdAmount=null;
		Double fdAmountLien=null;
		Double fdInterestRate=null;
		int succRd=0;
		int fldRd=0;
		Timestamp st = null;
		Date date = null;
		IUbsErrorLog objUbsError=new OBUbsErrorLog();
		Set errSet=new HashSet();
		String errMsg="";
		Set errInd=new HashSet();
		String data=null;
		int newInd=0;
		String selectCustDetails="SELECT A.DEPOSIT_REFERENCE_NUMBER,A.DEPOSIT_AMOUNT,A.deposit_interest_rate,B.LIEN_AMOUNT FROM CMS_CASH_DEPOSIT A,CMS_LIEN B WHERE A.CASH_DEPOSIT_ID=B.CASH_DEPOSIT_ID";
		
		String selectNotUpData="SELECT A.DEPOSIT_REFERENCE_NUMBER,A.DEPOSIT_AMOUNT,A.deposit_interest_rate,B.LIEN_AMOUNT FROM CMS_CASH_DEPOSIT A,CMS_LIEN B WHERE A.CASH_DEPOSIT_ID=B.CASH_DEPOSIT_ID AND B.UPLOAD_STATUS='N'";
		if(obUbsErrDetLog!=null)
		{
			for(int i=0;i<obUbsErrDetLog.length;i++)
			{
				errInd.add(obUbsErrDetLog[i].getRecordNo());
			}
		}
		if (result == null || result.size() <= 0) {
			throw new IncompleteBatchJobException(
					"Data to be compared with DB is null or empty");
		}
		try {
			lstCustDetails=selectCustDetails(selectCustDetails);
			for (int index = 0; index < result.size(); index++) {
				HashMap eachDataMap = (HashMap) result.get(index);
				fdNo = eachDataMap.get("FD_NO").toString();
				fdAmount=new Double(eachDataMap.get("FD_AMOUNT").toString());
				fdAmountLien=new Double(eachDataMap.get("FD_AMOUNT_LIEN").toString());
				fdInterestRate=new Double(eachDataMap.get("FD_INTEREST_RATE").toString());
				data=fdNo+","+fdAmount+","+fdAmountLien+","+fdInterestRate;
				errMsg="Combination of FD_NO, FD_AMOUNT ,FD_AMOUNT_LIEN,FD_INTEREST_RATE i.e. ("+fdNo+","+fdAmount+","+fdAmountLien+","+fdInterestRate+") not found in CMS System.";
				if(lstCustDetails.contains(data))
				{
					updateStatus(fdNo,fdAmount,fdInterestRate,fdAmountLien);
					succRd++;
				}
				else
				{
					newInd=getRecordNo(errInd,newInd);
					st = new Timestamp(System.currentTimeMillis());
					date =new Date(st.getTime());
					IUbsErrDetLog obUbsErrDet=new OBUbsErrDetLog();
					obUbsErrDet.setPtId(uploadId);
					obUbsErrDet.setRecordNo(newInd+1+"");					
					obUbsErrDet.setErrorMsg(errMsg);
					obUbsErrDet.setTime(date);
					errSet.add(obUbsErrDet);
					fldRd++;
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
	
			lstNotUpData=selectCustDetails(selectNotUpData);
			
			for(int i=0;i<lstNotUpData.size();i++)
			{
				errMsg="Combination of FD_NO, FD_AMOUNT ,FD_AMOUNT_LIEN,FD_INTEREST_RATE i.e. ("+lstNotUpData.get(i)+")not found in Uploaded file.";
				IUbsErrDetLog obUbsErrDet=new OBUbsErrDetLog();
				obUbsErrDet.setPtId(uploadId);
				obUbsErrDet.setRecordNo("");					
				obUbsErrDet.setErrorMsg(errMsg);
				obUbsErrDet.setTime(date);
				errSet.add(obUbsErrDet);
			}
			getJdbcTemplate().update(updateNStatus );
			objUbsError.setUploadId(uploadId);
			objUbsError.setFileName(fileName);
			objUbsError.setNoOfRecords(succRd+fldRd+"");
			objUbsError.setSuccessRecords(succRd+"");
			objUbsError.setFailedRecords(fldRd+"");
			objUbsError.setUploadTime(date);
			objUbsError.setErrEntriesSet(errSet);
			getFinwarefdHibernateDao().insertErrLog(objUbsError);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
					"Unable to update/insert dad retrived form Text file");

		}
	return objUbsError;
	}
	
	public void updateStatus(String fdNo,Double fdAmount,Double fdIntRate,Double fdAmountLien) {
		getJdbcTemplate().update(updateStatus,new Object[] { fdNo, fdAmount, fdIntRate,fdAmountLien });
	}
	public List selectCustDetails(String selectCustDetails)
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
				data=rs.getString("DEPOSIT_REFERENCE_NUMBER")+","+rs.getDouble("DEPOSIT_AMOUNT")+",";
				data+=rs.getDouble("LIEN_AMOUNT")+","+rs.getDouble("DEPOSIT_INTEREST_RATE");
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
	public IFinwareFdHibernateDAO getFinwarefdHibernateDao() {
		return finwarefdHibernateDao;
	}
	public void setFinwarefdHibernateDao(
			IFinwareFdHibernateDAO finwarefdHibernateDao) {
		this.finwarefdHibernateDao = finwarefdHibernateDao;
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
