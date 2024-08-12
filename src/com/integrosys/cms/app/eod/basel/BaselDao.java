package com.integrosys.cms.app.eod.basel;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.fileUpload.bus.OBBaselUploadFile;
import com.integrosys.cms.app.fileUpload.bus.OBCommonFile;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.batch.IncompleteBatchJobException;


public class BaselDao implements IBaselDao {

	private String INSERT_FOR_CMS_UPDATE_BASEL_REPORT ="Insert into CMS_UPDATE_BASEL_REPORT (ID,TRANSACTION_DATE,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY,LIMIT_AMOUTNT,UTILIZATION_AMOUNT,STATUS,REASON,UPLOAD_STATUS,CMS_COLLATERAL_ID) "
			+ " values (?,sysdate, ? , ? , ? , ? , ? , ?, ?, ?, ? ,?)";
    private JdbcTemplate jdbcTemplate;
    private String dailyBaselReport;
    private String monthlyBaselReport;
    private String monthlyBaselReport_p2; 
    
    
    // Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD || Starts
    
    private String sp_basel_update_report;
	public String getSp_basel_update_report() {
		return sp_basel_update_report;
	}

	public void setSp_basel_update_report(String sp_basel_update_report) {
		this.sp_basel_update_report = sp_basel_update_report;
	}
	
	// Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD || Ends
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public String getDailyBaselReport() {
		return dailyBaselReport;
	}

	public void setDailyBaselReport(String dailyBaselReport) {
		this.dailyBaselReport = dailyBaselReport;
	}

	public String getMonthlyBaselReport() {
		return monthlyBaselReport;
	}

	public void setMonthlyBaselReport(String monthlyBaselReport) {
		this.monthlyBaselReport = monthlyBaselReport;
	}

	public String getMonthlyBaselReport_p2() {
		return monthlyBaselReport_p2;
	}

	public void setMonthlyBaselReport_p2(String monthlyBaselReportP2) {
		monthlyBaselReport_p2 = monthlyBaselReportP2;
	}
	
	public void executeEndOfDayBaselReports() throws Exception {
        try {
            getJdbcTemplate().execute("{call " + getDailyBaselReport() + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (Exception ex) {
        	ex.printStackTrace();
            throw new Exception("Error execution Basel Reports for end of day.");
        }
    }
	
	
	
	public void executeEndOfMonthBaselReports() throws Exception {
        try {
        	 IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
        	IGeneralParamEntry generalParamEntry2 = generalParamDao.getGeneralParamEntryByParamCodeActual("BASEL_MONTHLY_PHASE_2");
			String Phase = generalParamEntry2.getParamValue();
			if(Phase.equals("N"))
			{
        	getJdbcTemplate().execute("{call " + getMonthlyBaselReport() + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
			}
			else{
            getJdbcTemplate().execute("{call " + getMonthlyBaselReport_p2() + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
			}
        }
        catch (Exception ex) {
        	ex.printStackTrace();
            throw new Exception("Error execution Basel Reports for end of month.");
        }
    }
	
	// Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD || Starts
	
	
	public Object executeSp_basel_update_report() throws Exception {
		Object flag=null;
        try {
        	flag=getJdbcTemplate().execute("{call " + getSp_basel_update_report() + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        	flag="success";
        }
        catch (Exception ex) {
        	ex.printStackTrace();
        	flag=null;
            throw new Exception("Error execution Sp_basel_update_report.");
        }
        return flag;
    }
		
	public Object executeUpdateBaselReports(List<OBCommonFile> OBBaselUploadFileList,String facility_system)
	{
		final List<OBCommonFile> objectList = OBBaselUploadFileList;
		Object status ="Sucess";
	 try {
			getJdbcTemplate().batchUpdate(INSERT_FOR_CMS_UPDATE_BASEL_REPORT,new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)throws SQLException {
					OBCommonFile baselFile = objectList.get(i);
					ps.setLong(1,baselFile.getFileId());
					ps.setString(2,baselFile.getCustomer());
					ps.setString(3,baselFile.getLine());
					ps.setString(4,baselFile.getSerialNo());
					ps.setString(5,baselFile.getCurrency());
					ps.setDouble(6,baselFile.getLimit());
					ps.setDouble(7,baselFile.getUtilize());
					ps.setString(8,baselFile.getStatus());
					ps.setString(9,baselFile.getReason());
					ps.setString(10,baselFile.getUploadStatus());
					ps.setString(11,baselFile.getSecurity_id());
					
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
			
			//status=executeSp_basel_update_report();
			
		 }catch (Exception ex) {
        	ex.printStackTrace();
        	status="fail";
        }
	 
	    return status;
	}
	
	String getFileId(String query)
	{
		String uniqueId=null;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(query);
				rs = dbUtil.executeQuery();	
				if(rs!=null)
				{
					while(rs.next())
					{
						uniqueId = rs.getString("key_value");
						NumberFormat numberFormat = new DecimalFormat("0000000");
						uniqueId = numberFormat.format(Long.parseLong(uniqueId));
					}
				}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return uniqueId;
	}
	
	public static void finalize(DBUtil dbUtil, ResultSet rs) 
	{
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
	
	
	
	public List<ConcurrentMap<String, String>> catcheDataFromMonthlyBaselP2() {
		ConcurrentMap<String, String> cacheDataMonthlyBaselP2 = null;
		List<ConcurrentMap<String, String>> arrayListMonthlyBaselP2 = new ArrayList<ConcurrentMap<String, String>>();
		DBUtil dbUtil = null;
		ResultSet rs=null;
		StringBuilder key = null;
		StringBuilder queryBuilder= new StringBuilder();
		queryBuilder.append("SELECT V_F_SYSTEM_EXP_IND,V_F_LINE_CODE,N_F_LINE_SERIAL,V_F_SECURITY_ID   ");
		queryBuilder.append("FROM CMS_BASEL_MONTHLY_P2   ");
		queryBuilder.append("WHERE V_F_MITIGANT_TYPE_CODE='COL0000139'");
		queryBuilder.trimToSize();
		
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(queryBuilder.toString());
			rs = dbUtil.executeQuery();	
			if(rs!=null)
			{
				while(rs.next())
				{
					cacheDataMonthlyBaselP2 = new ConcurrentHashMap<String, String>();
					key=new StringBuilder();
				    key.append(rs.getString("V_F_SYSTEM_EXP_IND")!=null ? rs.getString("V_F_SYSTEM_EXP_IND").trim() : "NA");
					key.append("~");
					key.append(rs.getString("V_F_LINE_CODE")!=null ? rs.getString("V_F_LINE_CODE").trim() : "NA");
					key.append("~");
					key.append(rs.getString("N_F_LINE_SERIAL")!=null ? rs.getString("N_F_LINE_SERIAL").trim() : "NA");
					key.trimToSize();
					cacheDataMonthlyBaselP2.putIfAbsent(key.toString(),rs.getString("V_F_SECURITY_ID"));
					arrayListMonthlyBaselP2.add(cacheDataMonthlyBaselP2);
				}
			}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return arrayListMonthlyBaselP2;
	}

	
	public Object insertBaselUpdateDetails(ArrayList resultList,List<ConcurrentMap<String, String>> cacheDataMonthlyBaselP2) {

		 Object status =null;
		 List<OBCommonFile> totalUploadedList= new ArrayList<OBCommonFile>();
		 String query="SELECT BASEL_UPDATE_UPLOAD_SEQ.NEXTVAL as key_value FROM dual";
		 System.out.println("Size of data in Basel CSV file:"+resultList.size());
		 String fileId = null;
		 try {
				if(resultList!=null&& resultList.size() != 0)
				{
					for (int index = 0; index < resultList.size(); index++) {
						HashMap eachDataMap = (HashMap) resultList.get(index);
						OBBaselUploadFile obj=new OBBaselUploadFile();
						fileId = getFileId(query);
						obj.setFileId(Long.parseLong(fileId));
						obj.setCurrency((String) eachDataMap.get("CURRENCY_CODE"));
						if(null!=eachDataMap.get("CUSTOMER_ID")&& !"".equalsIgnoreCase(eachDataMap.get("CUSTOMER_ID").toString()) )
						{
							obj.setCustomer(eachDataMap.get("CUSTOMER_ID").toString());
						}
						if(null!=eachDataMap.get("LINE_NO")&& !"".equalsIgnoreCase(eachDataMap.get("LINE_NO").toString()) )
						{
							obj.setLine(eachDataMap.get("LINE_NO").toString());
						}
						
						if(null!=eachDataMap.get("LIMIT_AMOUNT")&& !"".equalsIgnoreCase(eachDataMap.get("LIMIT_AMOUNT").toString()) )
						{
							obj.setLimit(new Double(Double.parseDouble(eachDataMap.get("LIMIT_AMOUNT").toString())));
						}
						
						if(null!=eachDataMap.get("SR_NO")&& !"".equalsIgnoreCase(eachDataMap.get("SR_NO").toString()) )
						{
							obj.setSerialNo(eachDataMap.get("SR_NO").toString());
						}
						
						if(null!=eachDataMap.get("UTILIZATION_AMOUNT")&& !"".equalsIgnoreCase(eachDataMap.get("UTILIZATION_AMOUNT").toString()) )
						{
							obj.setUtilize(new Double(Double.parseDouble(eachDataMap.get("UTILIZATION_AMOUNT").toString())));
							
						}
						obj.setUploadStatus("Y");
						String recordMatch = getMatchRecordFromBaselSUpload(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()),cacheDataMonthlyBaselP2);
						if(recordMatch!=null && recordMatch.length()>0)
						{
							String errMsg = "Combination of Customer_id, Line_no ,Sr_no i.e. ("+obj.getCustomer()+","+obj.getLine()+","+obj.getSerialNo()+") Matched in CLIMS and updated.";
							obj.setSecurity_id(recordMatch);
							obj.setReason(errMsg);
							obj.setStatus("PASS");
							//updateGrossAmountForMonthlyBaselP2(recordMatch , ""+obj.getUtilize());
							//updateCMVForCmsSecurity(recordMatch , ""+obj.getUtilize());
						}
						else
						{
							String errMsg = "Combination of Customer_id, Line_no ,Sr_no i.e. ("+obj.getCustomer()+","+obj.getLine()+","+obj.getSerialNo()+") Available in UBS-UPLOAD not in CLIMS.";
							obj.setSecurity_id(recordMatch);
							obj.setReason(errMsg);
							obj.setStatus("FAIL");
						}
							
						OBCommonFile commonObj = obj;
						totalUploadedList.add(commonObj);
					}
				}
				
				int batchSize = 200;
				for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
					List<OBCommonFile> batchList = totalUploadedList.subList(j, j + batchSize > totalUploadedList.size() ? totalUploadedList.size() : j + batchSize);
					status=executeUpdateBaselReports(batchList,"BASEL_SYSTEM");
				}
				status=executeSp_basel_update_report();
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IncompleteBatchJobException(
				"Unable to update retrived record from CSV file");
			}
		 
		 return status;
	}
	
	
	

	public String getMatchRecordFromBaselSUpload(String Cust_Id,String Line_No,String Sr_No,List<ConcurrentMap<String, String>> arrayListMonthlyBaselP2)
	{
		String status= "";
		ConcurrentMap<String, String> tempCacheMap = null;
		if(arrayListMonthlyBaselP2!=null && arrayListMonthlyBaselP2.size()>0)
		{
			for(int i=0;i<arrayListMonthlyBaselP2.size() ; i++)
			{
				tempCacheMap = arrayListMonthlyBaselP2.get(i);
				if(tempCacheMap!=null)
					status = tempCacheMap.get(Cust_Id+"~"+Line_No+"~"+Sr_No);
					
				if(status!=null && !"".equals(status))
				{
					break;
				}
					
			}
		}
		return status;
	}
	
	
	public void updateGrossAmountForMonthlyBaselP2(String securityId , String grossAmount)
	{
		
			DBUtil dbUtil = null;
			ResultSet rs=null;
					
			StringBuilder queryBuilder= new StringBuilder();
			queryBuilder.append("UPDATE CMS_BASEL_MONTHLY_P2  ");
			queryBuilder.append("SET GROSS_VALUE = '");
			queryBuilder.append(grossAmount);
			queryBuilder.append("', ");
			queryBuilder.append("upload_status= 'Y'  ");
			queryBuilder.append("where V_F_SECURITY_ID = '");
			queryBuilder.append(securityId);
			queryBuilder.append("' and V_F_MITIGANT_TYPE_CODE='COL0000139'");
			queryBuilder.trimToSize();
			
			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryBuilder.toString());
				dbUtil.executeUpdate();	
				
			} catch (DBConnectionException e) {
				e.printStackTrace();
			} catch (NoSQLStatementException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finalize(dbUtil,rs);
		
	}
	
	
	private void cleanBaselTempTable() 
	{
		DBUtil dbUtil = null;
		ResultSet rs=null;
				
		StringBuilder queryBuilder= new StringBuilder();
		//queryBuilder.append("TRUNCATE TABLE CMS_UPDATE_BASEL_REPORT");
		queryBuilder.append("DELETE FROM CMS_UPDATE_BASEL_REPORT");
		queryBuilder.trimToSize();
		
		try {
			DefaultLogger.debug(this, "Inside method cleanBaselTempTable");
			dbUtil=new DBUtil();
			dbUtil.setSQL(queryBuilder.toString());
			DefaultLogger.debug(this, "executing sql: DELETE FROM CMS_UPDATE_BASEL_REPORT");
			dbUtil.executeUpdate();
			
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		
	}
	
	public boolean getActivityPerformed()
	{
		DBUtil dbUtil = null;
		ResultSet rs = null;
		boolean status = true;	
		String ACTIVITY_PERFORMED = null;
		StringBuilder queryBuilder= new StringBuilder();
		queryBuilder.append("SELECT ACTIVITY_PERFORMED as status FROM cms_general_param WHERE param_code = 'BASEL_UPDATE_BILLS_COL' ");
		queryBuilder.trimToSize();
		DefaultLogger.debug(this, "Inside method getActivityPerformed");
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(queryBuilder.toString());
			rs = dbUtil.executeQuery();
			if(rs!=null)
			{
				while(rs.next())
				{
					ACTIVITY_PERFORMED = rs.getString("status");
				}
			}
			
			if(ACTIVITY_PERFORMED!=null)
			{
				status = false;
			}
			
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		if(status)
		{
			cleanBaselTempTable();
			DefaultLogger.debug(this, "cleanBaselTempTable method Completed");
		}
		
		DefaultLogger.debug(this, "getActivityPerformed method completed.");
		return status;
	}
	
	
	// Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD || Ends
		
	
	public void updateMonthendDateAndFccFlag(String monthendDate)
	{
		DBUtil dbUtil = null;
		ResultSet rs = null;
		
		StringBuilder queryBuilder= new StringBuilder();
		queryBuilder.append("update cms_general_param set activity_performed=null , PARAM_VALUE ='"+monthendDate+"' WHERE param_code = 'MONTHEND_DATE_DOWNLOAD_FCCNCB'");
		queryBuilder.trimToSize();
		DefaultLogger.debug(this, "Inside method updateMonthendDateAndFccFlag");
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(queryBuilder.toString());
			dbUtil.executeUpdate();
			dbUtil.commit();
			
		} catch (Exception e) {
			DefaultLogger.debug(this,"error message in updateMonthendDateAndFccFlag:"+e.getMessage());
			e.printStackTrace();
		}
		finally{
		finalize(dbUtil,rs);
		}
		DefaultLogger.debug(this, "updateMonthendDateAndFccFlag method completed.");
		return;
	}
	
	public boolean getActivityPerformedParamCode(String paramCode)
	{
		DBUtil dbUtil = null;
		ResultSet rs = null;
		boolean status = true;	
		String ACTIVITY_PERFORMED = null;
		StringBuilder queryBuilder= new StringBuilder();
		queryBuilder.append("SELECT ACTIVITY_PERFORMED as status FROM cms_general_param WHERE param_code = '"+ paramCode+"' ");
		queryBuilder.trimToSize();
		DefaultLogger.debug(this, "Inside method getActivityPerformedParamCode");
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(queryBuilder.toString());
			rs = dbUtil.executeQuery();
			if(rs!=null)
			{
				while(rs.next())
				{
					ACTIVITY_PERFORMED = rs.getString("status");
				}
			}
			
			if(ACTIVITY_PERFORMED!=null)
			{
				status = false;
			}
			
		} catch (Exception e) {
			DefaultLogger.debug(this,"error message in getActivityPerformedParamCode:"+ e.getMessage());
			e.printStackTrace();
		} 
		finalize(dbUtil,rs);
		
		
		DefaultLogger.debug(this, "getActivityPerformedParamCode method completed.");
		return status;
	}
	
	public void updateActivityPerfForParamCode(String paramCode,String date)
	{
		DBUtil dbUtil = null;
		ResultSet rs = null;
		
		StringBuilder queryBuilder= new StringBuilder();
		queryBuilder.append("update cms_general_param set activity_performed='"+date+"' WHERE param_code = '"+paramCode+"'");
		queryBuilder.trimToSize();
		DefaultLogger.debug(this, "Inside method updateActivityPerfForParamCode");
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(queryBuilder.toString());
			dbUtil.executeUpdate();
			dbUtil.commit();

			
		} catch (Exception e) {
			DefaultLogger.debug(this,"error message in updateActivityPerfForParamCode:"+e.getMessage());
			e.printStackTrace();
		}
		finally{
		finalize(dbUtil,rs);
		}
		DefaultLogger.debug(this, "updateActivityPerfForParamCode method completed.");
		return;
	}
}
