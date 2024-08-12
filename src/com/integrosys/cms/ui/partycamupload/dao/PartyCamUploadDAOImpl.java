package com.integrosys.cms.ui.partycamupload.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.partycam.IPartyCamErrDetLog;
import com.integrosys.cms.batch.partycam.IPartyCamErrorLog;
import com.integrosys.cms.batch.partycam.OBPartyCamErrDetLog;
import com.integrosys.cms.batch.partycam.OBPartyCamErrorLog;
import com.integrosys.cms.host.eai.customer.CustomerJdbcImpl;
import com.integrosys.cms.host.eai.limit.bus.LimitJdbcImpl;

/** 
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class PartyCamUploadDAOImpl extends JdbcDaoSupport  implements IPartyCamUploadDAO{
	
	private IPartyCamHibernateDAO partyCamHibernateDao;

	private String updateStatus="UPDATE SCI_LSP_SYS_XREF SET UPLOAD_STATUS='N'";
	
	
	public IPartyCamErrorLog insertPartyCamUpload( ArrayList result,String fileName,String uploadId,IPartyCamErrDetLog[] obPartyCamErrDetLog) {
		
		String partyId = null;		
		String line_No=null;
		String sr_No=null;
		String upload_status="";
		int succRd=0;
		int fldRd=0;
		Timestamp st = null;
		Date date = null;
		IPartyCamErrorLog objPartyCamError=new OBPartyCamErrorLog();
		Set errSet=new HashSet();
		String errMsg="";
		Set errInd=new TreeSet();
		int newInd=0;
		String tempData="";
		String strArrTemp[]=new String[3];
		
		
		
		if(obPartyCamErrDetLog!=null)
		{
			for(int i=0;i<obPartyCamErrDetLog.length;i++)
			{
				errInd.add(obPartyCamErrDetLog[i].getRecordNo());
			}
		}
		try {
			
			if(result!=null&& result.size() != 0)
			{
				LimitJdbcImpl dao = new LimitJdbcImpl();
				for (int index = 0; index < result.size(); index++) {
					HashMap eachDataMap = (HashMap) result.get(index);
					partyId = eachDataMap.get("PARTY_ID").toString();
					eachDataMap.get("CAM_DATE").toString();
					eachDataMap.get("CAM_LOGIN_DATE").toString();
					eachDataMap.get("RAM_RATING").toString();
					eachDataMap.get("RAM_RATING_YEAR").toString();
					eachDataMap.get("CUSTOMER_RAM_ID").toString();
					eachDataMap.get("CAM_EXPIRY_DATE").toString();
					eachDataMap.get("CAM_EXTENSION_DATE").toString();
					line_No=eachDataMap.get("LINE_NO").toString();
					sr_No=eachDataMap.get("SR_NO").toString();
					
					
					System.out.println("isPartyExist==="+dao.isPartyExist(partyId));
					upload_status="Y";
					st = new Timestamp(System.currentTimeMillis());
					date =new Date(st.getTime());
					IPartyCamErrDetLog obPartyCamErrDet=new OBPartyCamErrDetLog();
					obPartyCamErrDet.setPtId(uploadId);
					obPartyCamErrDet.setRecordNo(newInd+1+"");					
					obPartyCamErrDet.setTime(date);
					//obPartyCamErrDet.setFacSystemId(cust_id);
					obPartyCamErrDet.setLineNo(line_No);
					obPartyCamErrDet.setSerialNo(sr_No);
					obPartyCamErrDet.setUploadStatus("Succeed");
					errSet.add(obPartyCamErrDet);
					succRd++;
					newInd++;
				}
			}
			if(obPartyCamErrDetLog!=null)
			{
				fldRd=fldRd+obPartyCamErrDetLog.length;
				for(int i=0;i<obPartyCamErrDetLog.length;i++)
				{
					errSet.add(obPartyCamErrDetLog[i]);
				}
			}

			st = new Timestamp(System.currentTimeMillis());
			date =new Date(st.getTime());
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
		return objPartyCamError;
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
	public IPartyCamHibernateDAO getPartyCamHibernateDao() {
		return partyCamHibernateDao;
	}
	public void setPartyCamHibernateDao(IPartyCamHibernateDAO partyCamHibernateDao) {
		this.partyCamHibernateDao = partyCamHibernateDao;
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
	
	/*public int updateLmtUtilAmt(String partyId) {
		try{

			String sql = "SELECT COUNT(1) FROM SCI_LE_MAIN_PROFILE WHERE LMP_LE_ID = ";
				getJdbcTemplate().execute(sql)
		}catch(Exception e){
			e.printStackTrace();
		}
		return getJdbcTemplate().update(updateLmtUtilAmt,new Object[] { chUtilAmt,fgCurCode,fgLmtAmt,fgUtlAmt,upstatus, Cust_Id,Line_No, Sr_No });

	}*/
}
