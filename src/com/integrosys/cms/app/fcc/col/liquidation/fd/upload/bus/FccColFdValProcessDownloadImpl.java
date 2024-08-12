package com.integrosys.cms.app.fcc.col.liquidation.fd.upload.bus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.util.SQLParameter;

public class FccColFdValProcessDownloadImpl extends JdbcDaoSupport implements IFccColFdValProcessDownload {

	public void readingFileSuccess(String filePath,String fileNames) throws DBConnectionException, SQLException, Exception {
		BufferedReader bufferReader = null;
		FileReader fileReader = null;
		try {
			System.out.println("In FccColFdValProcessDownloadImpl reading success file......"+Calendar.getInstance().getTime());
			System.out.println("In FccColFdValProcessDownloadImpl reading success file......fileName=>"+fileNames+" ... filePath=>"+filePath);
			fileReader = new FileReader(filePath);
			bufferReader = new BufferedReader(fileReader);
			String sCurrentLine;
			String nameOfSystem = "";
			
			if(fileNames.contains("BH")) {
				nameOfSystem = "BAHRAIN";
			}else if(fileNames.contains("HK")) {
				nameOfSystem = "HONGKONG";
			}else if(fileNames.contains("GC")) {
				nameOfSystem = "GIFTCITY";
			}
			// BAHRAIN,GIFTCITY,HONGKONG
			
			Map<String, String> map = new HashMap<String, String>();
			int counter =0;
			while ((sCurrentLine = bufferReader.readLine()) != null) {
				 System.out.println("FccColFdValProcessDownloadImpl=>inside while sCurrentLine");
				counter++;
				final  Date today = DateUtil.clearTime(new Date());
				if(counter != 1) {
				String[] data = sCurrentLine.split("\\|");
				final  String liabilityId = data[0];
				final   String lineId = data[1];
				final  String serialno = data[2];
				final String collateralId = data[3];
				final  String collateralDesc = data[4];
				final  String collateraltype = data[5];
				final  String collateralNo = data[6];
				final  String collateralAmt = data[7];
				final  String fdLienAmount = data[8];
				final  String currencyCode = data[9];
				final  String securityCurrency = data[10];
				final   String systemName = data[11];
				final  String rejectDate = data[12];
				final   String rejectReason = data[13];
				final   String fileName = fileNames;
				final   String nameOfSystems = nameOfSystem;
                
                SQLParameter params = SQLParameter.getInstance();
                
                String sql = "INSERT INTO CMS_FCC_COL_DOWNLOAD_FILE (SYSTEM_ID, LIEN_NUMBER, SERIAL_NO, CMS_COLLATERAL_ID, COLLATERAL_CODE, SUBTYPE_NAME, DEPOSIT_RECEIPT_NUMBER, "
                		+ " DEPOSIT_AMOUNT, LIEN_AMOUNT, DEPOSIT_AMOUNT_CURRENCY, SCI_SECURITY_CURRENCY, SYSTEM_NAME, FILE_NAME, REJECT_DATE, REJECT_REASON, CREATE_DATE,FD_SYSTEMS_NAME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                System.out.println("FccColFdValProcessDownloadImpl=>sql=>"+sql);
                getJdbcTemplate().execute(sql,new PreparedStatementCallback(){  
                    
                	public Object doInPreparedStatement(PreparedStatement ps)  
                	           throws SQLException {  
                	              
                		ps.setString(1,liabilityId);
                        ps.setString(2,lineId);
                        ps.setString(3,serialno);
                        ps.setString(4,collateralId);
                        ps.setString(5,collateralDesc);
                        ps.setString(6,collateraltype);
                        ps.setString(7,collateralNo);
                        ps.setString(8,collateralAmt);
                        ps.setString(9,fdLienAmount);
                        ps.setString(10,currencyCode);
                        ps.setString(11,securityCurrency);
                        ps.setString(12,systemName);
                        ps.setString(13,fileName);
                        ps.setString(14,rejectDate);
                        ps.setString(15,rejectReason);
                        ps.setTimestamp(16,new Timestamp(today.getTime()));
                        ps.setString(17,nameOfSystems);
        				
                        return ps.execute();  
                              
                    }  
                   }); 
			}}
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				if (bufferReader != null)
					bufferReader.close();
				if (fileReader != null)
					fileReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	public void dataTransferTempToActualFcc(String procedureName) throws Exception {
        try {
//        	String fromServer = PropertyManager.getValue("integrosys.server.identification","app1");
        	System.out.println("before dataTransferTempToActualFcc procedure call .. procedure name == SP_FCC_TEMP_TO_ACTUAL and variable procedureName=>"+procedureName);
            getJdbcTemplate().execute("{call " + procedureName + "}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
            System.out.println("after dataTransferTempToActualFcc procedure call .. procedure name == SP_FCC_TEMP_TO_ACTUAL");
        }
        catch (Exception ex) {
        	ex.printStackTrace();
            throw new Exception("Error dataTransferTempToActualFcc.................................");
        }
    }


}
