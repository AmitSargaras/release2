package com.integrosys.cms.app.lei.bus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.fileUpload.bus.OBLeiDetailsFile;
import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;


public class LeiJdbcImpl extends JdbcDaoSupport implements ILeiJdbc {

	public List<OBLeiDetailsFile> getUploadDetailsListForLeiValidation() {
		String query ="select ID,PARTY_ID,LEI_CODE,LEI_VALIDATION_FLAG from ACTUAL_LEI_DETAILS_UPLOAD where UPLOAD_STATUS='Y' AND STATUS='PASS' AND LEI_VALIDATION_FLAG ='Y'"; 
		System.out.println("getUploadDetailsListForLeiValidation =>sql query =>"+query);
		List resultList = getJdbcTemplate().query(query, new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            	OBLeiDetailsFile result = new OBLeiDetailsFile();
            	result.setId(rs.getLong("ID"));
    			result.setPartyId(rs.getString("PARTY_ID"));
            	result.setLeiCode(rs.getString("LEI_CODE"));
            	result.setLeiValidationFlag(rs.getString("LEI_VALIDATION_FLAG"));
                return result;
            }});
		return resultList;		
	}
	
	public void updateLeiValidationFlag(OBLeiDetailsFile obLeiDetailsFile) {
		String query = "UPDATE ACTUAL_LEI_DETAILS_UPLOAD SET LEI_VALIDATION_FLAG ='N' WHERE ID='"+obLeiDetailsFile.getId()+"' AND PARTY_ID='"+obLeiDetailsFile.getPartyId()+"' AND LEI_CODE='"+obLeiDetailsFile.getLeiCode()+"'";
		getJdbcTemplate().update(query,new Object[]{});
	}
	
	public void updateSubProfileValidatedLeiExpiryDateFromScheduler(final String partyId,final String leiCode,final java.sql.Date leiExpDate) {
		
		String sqlQuery = " UPDATE SCI_LE_SUB_PROFILE SET DEFER_LEI='N',IS_LEI_VALIDATED ='Y',LEI_EXPIRY_DATE =? WHERE LSP_LE_ID = ? AND LEI_CODE =?";
		
		getJdbcTemplate().execute(sqlQuery,new PreparedStatementCallback(){  
	        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        		ps.setDate(1,leiExpDate);	
        		ps.setString(2,partyId);
        		ps.setString(3,leiCode); 
                return ps.execute();       
            }  
           });
	}
	
	public void updateMainProfileValidatedLeiExpiryDateFromScheduler(final String partyId,final String leiCode, final java.sql.Date leiExpDate) {
		
		String sqlQuery = " UPDATE SCI_LE_MAIN_PROFILE SET DEFER_LEI='N',LEI_EXPIRY_DATE =? WHERE LMP_LE_ID = ? AND LEI_CODE =?";
		
		getJdbcTemplate().execute(sqlQuery,new PreparedStatementCallback(){  
	        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        		ps.setDate(1,leiExpDate);	
        		ps.setString(2,partyId);
        		ps.setString(3,leiCode); 
        		return ps.execute();       
            }  
           });
	}
	
	public String generateReferenceNumber() {
		String generateSourceString = null;
		String sequence = null;
		Long seq = 0L;
		generateSourceString="select concat (to_char(sysdate,'YYYYMMDD'), LPAD(CMS_LEI_REFNO_INTERFACE_SEQ.NEXTVAL, 8,'0')) sequence from dual";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(generateSourceString);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				 seq = rs.getLong("sequence");
				 seq++;
				 sequence = seq.toString();
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
			return sequence;
		}
		finally{
		finalize(dbUtil,rs);
		}
		return sequence;
	}
	
	public String generateExternalReferenceNo() {
		String generateSourceString = null;
		String sequence = null;
		Long seq = 0L;
		generateSourceString="select concat (to_char(sysdate,'YYYYMMDD'), LPAD(CMS_LEI_EXREFNO_INTERFACE_SEQ.NEXTVAL, 9,'0')) sequence from dual";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(generateSourceString);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				 seq = rs.getLong("sequence");
				 seq++;
				 sequence = seq.toString();
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
			return sequence;
		}
		finally{
		finalize(dbUtil,rs);
		}
		return sequence;
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
}
