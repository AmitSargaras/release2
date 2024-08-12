package com.integrosys.cms.app.json.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.json.party.dto.RetrieveScmPartyRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ScmPartyDaoImpl extends JdbcTemplateAdapter implements ScmPartyDao {

	public RetrieveScmPartyRequest getPartyDetails(String custId) {
		RetrieveScmPartyRequest partyDetails=new RetrieveScmPartyRequest();
		ResultSet rs = null;
		DBUtil dbUtil =null;
		String query = "SELECT   "+
				"	 sp.status,  "+
				"	 sp.LSP_LE_ID  , "+
				"	 sp.LSP_SHORT_NAME,   "+
				"    cri.msme_classification,    "+
				"  sp.entity,"+
				"  sp.PAN,    "+
				" sp.ind_nm,"+
				" sp.cin_llpin,"+
				" to_char(sp.lsp_incorp_date,'DD/MON/YYYY') lsp_incorp_date,"+
				" sp.lsp_sgmnt_code_value,"+
//				"  rm.EMPLOYEE_ID , "+
				"  rm.RM_MGR_CODE , "+
	      "  sp.TOTAL_SANCTIONED_LIMIT, "+
	       "  lp.CMS_APPR_OFFICER_GRADE, "+
	       "  sp.WILLFUL_DEFAULT_STATUS, "+
	       " sp.class_activity_1,"+
	      "   to_char(sp.DATE_WILLFUL_DEFAULT,'DD/MON/YYYY') DATE_WILLFUL_DEFAULT, "+
	       " (select entry_name from common_code_category_entry where sp.SUIT_FILLED_STATUS = entry_code and ACTIVE_STATUS = '1' and category_code='SUIT_FILLED_STATUS') SUIT_FILLED_STATUS ,"+
	        " sp.SUIT_AMOUNT, "+
	        " to_char(sp.DATE_OF_SUIT,'DD/MON/YYYY') DATE_OF_SUIT, "+
	        " ra.LRA_TYPE_VALUE, "+
					" ra.LRA_ADDR_LINE_1,  "+
					" ra.LRA_ADDR_LINE_2,   "+
					 " ra.LRA_ADDR_LINE_3,  "+
	       "  ra.LRA_STD_CODE_TEL,  "+
					"  ra.LRA_TELEPHONE_TEXT,   "+
	       "   (select(CASE when(ra.LRA_TELEPHONE_TEXT is not null) then concat(ra.LRA_STD_CODE_TEL,ra.LRA_TELEPHONE_TEXT) END) from dual) phoneno, " + 
					"  cc.CITY_NAME,   "+
					" udf.udf17,"+
	       "  reg.region_name,  "+
	       "  (select distinct STATE_CODE from CMS_STATE where id = ra.LRA_STATE and STATUS='ACTIVE') as LRA_STATE,   "+
					" (select distinct cntry.COUNTRY_NAME from CMS_COUNTRY cntry,CMS_STATE state where reg.id = state.region_id and reg.COUNTRY_ID=cntry.id and cntry.status = 'ACTIVE') as country_name, "+ 
	       "  ra.LRA_TELEX_TEXT,ra.LRA_STD_CODE_TELEX , "+
					"  (select(CASE when(ra.LRA_TELEX_TEXT is not null) then concat(ra.LRA_STD_CODE_TELEX,ra.LRA_TELEX_TEXT) END) from dual) fax, " + 
					" ra.LRA_POST_CODE,  "+
				"	 ra.LRA_EMAIL_TEXT "+
					" FROM  sci_le_udf udf, "+
					 "SCI_LE_REG_ADDR ra, sci_le_cri cri,SCI_LSP_LMT_PROFILE lp,SCI_LE_SUB_PROFILE sp,SCI_LE_main_PROFILE mp,  "+
	         " CMS_RELATIONSHIP_MGR rm, CMS_CITY cc,CMS_REGION reg   "+
					" WHERE   "+
					" sp.CMS_LE_MAIN_PROFILE_ID = ra.CMS_LE_MAIN_PROFILE_ID "+
					" AND cri.CMS_LE_MAIN_PROFILE_ID = sp.CMS_LE_MAIN_PROFILE_ID"+
	       "  AND sp.cms_le_main_profile_id = mp.cms_le_main_profile_id "+
					" AND sp.LSP_LE_ID = mp.lmp_le_id  "+
	       "  AND rm.ID(+) = sp.RELATION_MGR   "+
	        " AND sp.LSP_LE_ID = lp.LLP_LE_ID(+)   AND udf.CMS_LE_MAIN_PROFILE_ID = sp.CMS_LE_MAIN_PROFILE_ID " + 
				"	 AND ra.lra_region = reg.id(+) AND cc.id(+) = ra.lra_city_text  "+
	        " AND ra.LRA_TYPE_VALUE in ('CORPORATE') "+
				"AND sp.LSP_LE_ID ='"+custId+"'";
		StringBuffer strBuffer = new StringBuffer(query.trim());
		try {
			DefaultLogger.debug(this,"Select query is  "+strBuffer);
		    	 dbUtil = new DBUtil();
				 dbUtil.setSQL(strBuffer.toString());
				 rs = dbUtil.executeQuery();
				 while (rs.next()) 
				 {		
					    partyDetails.setStatus(rs.getString("STATUS").equalsIgnoreCase("ACTIVE")?"A":"I");
						partyDetails.setPartyId(rs.getString("LSP_LE_ID"));
					    partyDetails.setPartyName(rs.getString("LSP_SHORT_NAME"));
					    partyDetails.setConstitution(rs.getString("ENTITY"));
						partyDetails.setPan(rs.getString("PAN"));
						partyDetails.setMsme(rs.getString("msme_classification"));
						partyDetails.setDateOfIncorporation(rs.getString("lsp_incorp_date"));
						partyDetails.setIndustryType(rs.getString("ind_nm"));
						partyDetails.setTypeOfActivity(rs.getString("class_activity_1"));
						partyDetails.setVertical(rs.getString("lsp_sgmnt_code_value"));
						partyDetails.setCin(rs.getString("cin_llpin"));
						partyDetails.setRm(rs.getString("RM_MGR_CODE"));
						partyDetails.setCaptialLine(rs.getString("TOTAL_SANCTIONED_LIMIT"));
						partyDetails.setRiskRating(rs.getString("CMS_APPR_OFFICER_GRADE"));
						partyDetails.setWilfulDefaultStatus(rs.getString("WILLFUL_DEFAULT_STATUS"));
						partyDetails.setDateWilfulDefaultStatus(rs.getString("DATE_WILLFUL_DEFAULT"));
						partyDetails.setSuitField(rs.getString("SUIT_FILLED_STATUS"));
						partyDetails.setDateOfSuit(rs.getString("DATE_OF_SUIT"));
						partyDetails.setSuitAmount(rs.getString("SUIT_AMOUNT"));
						partyDetails.setAddressType(rs.getString("LRA_TYPE_VALUE"));
						partyDetails.setAddress1(rs.getString("LRA_ADDR_LINE_1"));
						partyDetails.setAddress2(rs.getString("LRA_ADDR_LINE_2"));
						partyDetails.setAddress3(rs.getString("LRA_ADDR_LINE_3"));
						partyDetails.setPhone(rs.getString("phoneno"));
						partyDetails.setMobile(rs.getString("udf17"));
						partyDetails.setCity(rs.getString("CITY_NAME"));
						partyDetails.setRegion(rs.getString("region_name"));
						partyDetails.setState(rs.getString("LRA_STATE"));
						partyDetails.setCountry(rs.getString("COUNTRY_NAME"));
						partyDetails.setFax(rs.getString("fax"));
						partyDetails.setPincode(rs.getString("LRA_POST_CODE"));
						partyDetails.setEmail(rs.getString("LRA_EMAIL_TEXT"));
//						DefaultLogger.debug(this,"Party details list  "+partyDetails);
				 }
		         
		     }
		     catch(Exception e){
		    	 DefaultLogger.debug(this,e.getMessage() );
					e.printStackTrace();
					return partyDetails;
		     }
		     finally{
		    	 try {
					dbUtil.close();
				} catch (SQLException e) {
					
				}
		     }
		 
		return partyDetails;
		}	
	
	public String generateSourceSeqNo() {
		String generateSourceString = null;
		String sequence = null;
		 generateSourceString="select concat (to_char(sysdate,'DDMMYYYY'), LPAD(CMS_JS_INTERFACE_SEQ.NEXTVAL, 9,'0')) sequence from dual";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(generateSourceString);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				 sequence = rs.getString("sequence");
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
	
	//For bank it will be 97
	//For QC it will be 17
	public String getBorrowerScmFlag(String custId){
		String borrowerScmFlag="No";
		String mainId = getMainID(custId);
		//String getBorrowerScmFlag="select nvl(udf17,'No') udf17 from SCI_LE_UDF where cms_le_main_profile_id='"+mainId+"'";
		String getBorrowerScmFlag="select nvl(udf97,'No') udf97 from SCI_LE_UDF where cms_le_main_profile_id='"+mainId+"'";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(getBorrowerScmFlag);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				String tempscmFlag = rs.getString(1);
				if(null!=tempscmFlag){
					borrowerScmFlag=tempscmFlag;
				}
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
			return borrowerScmFlag;
		}
		finally{
		finalize(dbUtil,rs);
		}
		return borrowerScmFlag;
	}
	
	public String getMainID(String custID) throws SearchDAOException {
		String sql = "SELECT CMS_LE_MAIN_PROFILE_ID FROM SCI_LE_SUB_PROFILE WHERE LSP_LE_ID='"+custID+"'";

		DBUtil dbUtil = null;
		ResultSet rs=null;
		String mainLineCode = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				mainLineCode = rs.getString(1);
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
			return mainLineCode;
		}
		finally{
		finalize(dbUtil,rs);
		}
		return mainLineCode;
		
	}
	//for QC it will be udf17
	//for Bank it will be udf97
	public String getStgBorrowerScmFlag(String refId) {
		/*String stgScmFlagqry ="select nvl(udf17,'No') udf17 from stage_sci_le_udf "
				+ "where cms_le_main_profile_id="
				+ "(select CMS_LE_MAIN_PROFILE_ID from stage_sci_le_sub_profile"
				+ " where CMS_LE_SUB_PROFILE_ID ='"+refId+"')";*/
		
		String stgScmFlagqry ="select nvl(udf97,'No') udf97 from stage_sci_le_udf "
			+ "where cms_le_main_profile_id="
			+ "(select CMS_LE_MAIN_PROFILE_ID from stage_sci_le_sub_profile"
			+ " where CMS_LE_SUB_PROFILE_ID ='"+refId+"')";
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String stgScmFlag = "No";
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(stgScmFlagqry);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				stgScmFlag = rs.getString(1);
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
			return stgScmFlag;
		}
		finally{
		finalize(dbUtil,rs);
		}
		return stgScmFlag;
		
	}
	
	public String getBorrowerScmFlagforCAM(String custId) {
		String stgScmFlagqry ="select nvl(scmflag,'N') scmflag,max(requestdatetime) "
				+ " from cms_js_interface_log where partyId = '"+custId+"'  and " 
				+ " status = 'Success' group by scmflag ";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String stgScmFlag = "N";
		String camScmFlag = "No";
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(stgScmFlagqry);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				stgScmFlag = rs.getString("scmflag");
			}
			camScmFlag = stgScmFlag.equalsIgnoreCase("N")?"No":"Yes";
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
			return camScmFlag;
		}
		finally{
		finalize(dbUtil,rs);
		}
		return camScmFlag;
		
	}
	
	public String getLatestOperationStatus(String custId,String module) {
		
		System.out.println("*****Inside ScmPartyDaoImpl******* party id :  "+custId+"module : "+module);
		
		String operationSuccessCount = "select count (1) AS CNT from cms_js_interface_log "+ 
		" where  partyId = '"+custId+"' and MODULENAME='"+module+"'    AND STATUS='Success' "+
		 " order by requestdatetime desc";
		
	DefaultLogger.debug(this,"Select query for operationSuccessCount is ==  "+operationSuccessCount);
	System.out.println("Select query for operationSuccessCount is ==  "+operationSuccessCount);
	
	String counts = "";
	try {
		List queryForList = getJdbcTemplate().queryForList(operationSuccessCount);
		for (int i = 0; i < queryForList.size(); i++) {
			Map  map = (Map) queryForList.get(i);
			if(null!=map.get("CNT") && !"".equals(map.get("CNT"))) {
				counts= map.get("CNT").toString();
			}else{
				counts = "";
			}
		}
		System.out.println("scmPartyDaoImpl.java=> getLatestOperationStatus=>counts=>"+counts);
	}
	catch (Exception ex) {
		System.out.println("Exception in getLatestOperationStatus=>Party lineno.-> 337 =>counts=>"+counts);
	
		ex.printStackTrace();
		
	}
	
	
	System.out.println(" getLatestOperationStatus=>Party lineno.-> 344 =>counts=>"+counts);
	
	if ("".equals(counts) || "0".equals(counts) || null==counts) {
		
		String operationStatusQuery = "select OPERATION , status,ERRORMESSAGE from (select OPERATION,SCMFLAG,partyId,requestdatetime,status,ERRORMESSAGE from cms_js_interface_log " + 
				" where  partyId = '"+custId+"' and MODULENAME='"+module+"'   " + 
				" order by requestdatetime desc) where rownum=1";
		
	DefaultLogger.debug(this,"Select query for operationStatusQuery is  "+operationStatusQuery);
	System.out.println(" ********Inside ScmPartyDao ********353**********Select query for operationStatusQuery is =>"+operationStatusQuery);

	List resultList = getJdbcTemplate().query(operationStatusQuery, new RowMapper() {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println(" status is : "+rs.getString("status")+", Error message is : "+rs.getString("ERRORMESSAGE")+", operation is : "+rs.getString("OPERATION"));
			if(null==rs.getString("status") || rs.getString("status").isEmpty()) {
				System.out.println(" status is null or empty so setting operation as A");
				return "A";
			}else if(rs.getString("status").equalsIgnoreCase("Error")||rs.getString("status").equalsIgnoreCase("Fail")) {
				System.out.println(" status is  "+rs.getString("status"));
/*				if( null!=rs.getString("ERRORMESSAGE") && !rs.getString("ERRORMESSAGE").isEmpty() && rs.getString("ERRORMESSAGE").contains("Party does not exist to update")) {
					System.out.println(" status is fail/error and error message is  'Party does not exist to update' so setting operation as A");
					return "A";
				}else if( null!=rs.getString("ERRORMESSAGE") && !rs.getString("ERRORMESSAGE").isEmpty() && rs.getString("ERRORMESSAGE").contains("Duplicate Party ID exists "))  {
					return "U";
				}else {
					System.out.println(" at line 368 inside else of errorMessage so setting operation from previous operation");
					return rs.getString("OPERATION");
				}*/
				return rs.getString("OPERATION");
				}else {
					System.out.println(" at line 372 inside else of Status fail/Error so setting operation as U ");
					return "U";
				}
		}
	});
	
	if(null!= resultList && resultList.size()==0){
		resultList.add("A");
		System.out.println(" *******380******no data present means adding new "+module+" 1st time so setting operation as A");
		
	}else if(null==resultList){
		resultList = new ArrayList();
		resultList.add("A");
		System.out.println(" ********385*********no data present means adding new "+module+" 1st time so setting operation as A in a null resultList");
	}
	System.out.println("as no previous data found operation : "+resultList.get(0).toString());
	return resultList.get(0).toString();
	}else {
		System.out.println(" at line 390 inside else of count=0,empty,null so setting operation as U ");
		return "U";
	}	}

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
