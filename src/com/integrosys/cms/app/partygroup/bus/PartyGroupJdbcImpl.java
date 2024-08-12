package com.integrosys.cms.app.partygroup.bus;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.bus.BaseCurrency;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.NumberUtils;

/**
 * @author Bharat Waghela
 * 
 *         Jdbc implication of Interface Party Group Jdbc
 */

public class PartyGroupJdbcImpl extends JdbcDaoSupport implements
		IPartyGroupJdbc {

	private static final String SELECT_PARTY_GROUP_TRX = "SELECT id,party_group_code,party_group_name,group_exp_limit,last_update_date,status from CMS_PARTY_GROUP where status= 'ACTIVE' ORDER BY UPPER(party_group_name)";

	protected static String SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME = "SELECT LSP_LE_ID,LSP_SHORT_NAME,CMS_LE_SUB_PROFILE_ID FROM SCI_LE_SUB_PROFILE INNER JOIN SCI_LE_MAIN_PROFILE "
        + "ON SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID "
        + "WHERE STATUS = 'ACTIVE'";

	
	/**
	 * @return List of all Party group according to the query passed.
	 */

	public List listPartyGroup(long bankCode) throws SearchDAOException {

		String SELECT_PARTY_GROUP_ID = "SELECT id,party_group_code,party_group_name,group_exp_limit,last_update_date from cms_party_group  where id="
				+ bankCode;

		List resultList = null;
		try {
			resultList = getJdbcTemplate().query(SELECT_PARTY_GROUP_ID,
					new PartyGroupRowMapper());

		} catch (Exception e) {
			e.printStackTrace();
			throw new PartyGroupException(
					"ERROR-- Unable to get System Bank List");
		}

		return resultList;

	}

	/**
	 * @return List of all authorized Party Group
	 */

	public List getAllPartyGroup() {
		List resultList = null;
		try {
			resultList = getJdbcTemplate().query(SELECT_PARTY_GROUP_TRX,
					new PartyGroupRowMapper());

		} catch (Exception e) {
			e.printStackTrace();
			throw new PartyGroupException(
					"ERROR-- Unable to get Party Group List");
		}

		return resultList;
	}

	public List getPartyByFacilityList(String partyName) {
		List resultList = null;
		
		
		String query = "";
		if(partyName!=null && !partyName.trim().equals("")){
			
	 query = " SELECT LSP_LE_ID, CMS_LE_SUB_PROFILE_ID, LSP_SHORT_NAME FROM SCI_LE_SUB_PROFILE WHERE  LSP_LE_ID in "

	 +"(SELECT LLP_LE_ID FROM SCI_LSP_LMT_PROFILE WHERE CMS_LSP_LMT_PROFILE_ID in  "

	+" (SELECT CMS_LIMIT_PROFILE_ID FROM SCI_LSP_APPR_LMTS  WHERE facility_cat = 'F19')) "

	+"AND ( STATUS ='ACTIVE' AND UPPER(LSP_SHORT_NAME) like  '"+partyName.toUpperCase()+"%' )";

		 
		 //SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME
	// +" AND UPPER(LSP_SHORT_NAME) like '"+partyName.toUpperCase()+"%' ";
		}
		//else
		//{
	 //query = SELECT_CUSTOMER_BY_CIFNO_SOURCE_PNAME;
		//}
		
		try {
			
			  resultList = getJdbcTemplate().query(query, new RowMapper() {

	             public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	            	 
	            	 OBCMSCustomer result = new OBCMSCustomer();
	     			
	     			result.setCifId(rs
	     					.getString("LSP_LE_ID"));
	     			result.setCustomerName(rs
	     					.getString("LSP_SHORT_NAME"));
	     			result.setCustomerID(rs
	     					.getLong("CMS_LE_SUB_PROFILE_ID"));
	     			
	                /* String[] stringArray = new String[3];
	                 stringArray[0] = rs.getString("LSP_LE_ID");
	                 stringArray[1] = rs.getString("LSP_SHORT_NAME");
	                 stringArray[2] = rs.getString("CMS_LE_SUB_PROFILE_ID");*/

	                 return result;
	             }
	         });

		} catch (Exception e) {
			e.printStackTrace();
			throw new PartyGroupException(
					"ERROR-- Unable to get Party Group List");
		}

		return resultList;
	}
	
	public class PartyGroupRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBPartyGroup result = new OBPartyGroup();
			result.setPartyCode(rs.getString("party_group_code"));
			result.setPartyName(rs.getString("party_group_name"));	
			result.setGroupExpLimit(new Amount(
					new BigDecimal(rs.getString("group_exp_limit")), BaseCurrency.getCurrencyCode()));
			result.setStatus(rs.getString("status"));
			result.setLastUpdateDate(rs.getDate("last_update_date"));
			result.setId(rs.getLong("id"));
			return result;
		}
	}
/*	public List getAllPartyGroupSearch(String login) {
		String SELECT_PARTY_GROUP_BY_SEARCH = "SELECT id,party_group_code,party_group_name,group_exp_limit,last_update_date,status from CMS_PARTY_GROUP  AND description  LIKE '"
			+ login + "%' ";

		List resultList = null;
		try {

			if (login == null || login.trim() == "") {
				resultList = getJdbcTemplate().query(
						SELECT_PARTY_GROUP_TRX,
						new PartyGroupRowMapper());
			} else {
				resultList = getJdbcTemplate().query(
						SELECT_PARTY_GROUP_BY_SEARCH,
						new PartyGroupRowMapper());
			}

		} catch (Exception e) {
			throw new PartyGroupException("ERROR-- While retriving CollateralNewMaster");
		}
		return resultList;
	}*/
	/**
	 * appends the customer filter condition to the SQL
	 * 
	 * @param sb
	 *//*
	private static void appendCustomerFilter(StringBuffer sb) {
		sb.append(" AND UPPER(CUSTOMER_NAME) LIKE ?");
	}

	*//**
	 * appends the customer le reference filter condition to the SQL
	 * 
	 * @param sb
	 *//*
	private static void appendLeIDFilter(StringBuffer sb) {
		sb.append(" AND LE_ID LIKE ?");
	}

	*//**
	 * appends the country filter condition to the SQL
	 * 
	 * @param sb
	 *//*
	private static void appendCountryFilter(StringBuffer sb) {
		sb.append(" AND ALLOWED_COUNTRY = ? ");
	}

	private void appendDateRangeFilter(StringBuffer sb) {
		sb.append(" AND ( trunc(ITEM_EXPIRY_DATE) between ? and ? )");
	}

	private boolean hasDateFilter(java.util.Date startExpDate,
			java.util.Date endExpDate) {
		return (startExpDate != null) && (endExpDate != null);
	}

	*//**
	 * helper method to prepare bind variables to SQL statement
	 * 
	 * @param allowedCountries
	 * @return String - country where clause
	 *//*
	private String getCountryCondition(String[] allowedCountries) {
		StringBuffer sb = new StringBuffer();

		if ((allowedCountries != null) && (allowedCountries.length > 0)) {
			sb.append(" AND ALLOWED_COUNTRY IN (");
			for (int i = 0; i < allowedCountries.length; i++) {
				sb.append("?");
				if (i < allowedCountries.length - 1) {
					sb.append(",");
				}
			}
			sb.append(" )");
		}
		return sb.toString();
	}

	*//**
	 * tests a string for its proposition
	 * 
	 * @param s
	 * @return boolean - true if match
	 *//*
	private boolean isTrue(String s) {
		return ICMSConstant.TRUE_VALUE.equals(s) ? true : false;
	}

	private void appendSortOrder(StringBuffer sb, String customerIndex) {
		if ((customerIndex != null) && !customerIndex.trim().equals("")) {
			sb
					.append(" ORDER BY UPPER(CUSTOMER_NAME) ASC, ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC ");
		} else {
			sb
					.append(" ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC");
		}

	}
*/
	@Override
	public Map<String,Integer> getFailedPartyRequestforScm()  {
		/*String sql = "select count(partyId) count,partyId from (" + 
				"select partyId,count(partyId),operation from cms_js_interface_log " + 
				"where  " + 
				"modulename in ('CAM','CUSTOMER') " + 
//				"and status in  ('Error','Fail') " + 
				"and status in  ('Error') " + 
				"  AND PARTYID IS NOT NULL " +
				"group by partyId,operation " + 
				"order by partyId) group by partyId ";*/
		
		String sql = "SELECT MAX(requestdatetime),COUNT(PARTYID) AS count,PARTYID,MODULENAME FROM (select partyId,count(partyId),operation,requestdatetime,ID,modulename from cms_js_interface_log where modulename in ('CAM','CUSTOMER')and status in('Error') AND PARTYID IS NOT NULL group by partyId,operation,requestdatetime,modulename,ID )GROUP BY PARTYID,MODULENAME ";	
		
		System.out.println("Select query is getFailedPartyRequestforScm=> "+sql);
		
		return (Map<String,Integer>) getJdbcTemplate().query(sql,new ResultSetExtractor() {
			
			@Override
			public Map<String, Integer> extractData(ResultSet rs) throws SQLException {
				Map<String, Integer> returnData = new HashMap<String, Integer>();
				while(rs.next()) {
					returnData.put(rs.getString("partyId"),rs.getInt("count"));
				}
				return returnData;
			}
		});
		
	}
	
	public List<Long> getFailedListforAdd(String partyId) {
		/*String stgScmFlagqry ="select id,requestdatetime from cms_js_interface_log where partyId = '"+partyId+"'" + 
//				" and status in  ('Error','Fail')  and modulename in ('CAM','CUSTOMER')  " + 
				" and status in  ('Error')  and modulename in ('CAM','CUSTOMER')  " + 
				" order by requestdatetime asc ";*/
		
		String stgScmFlagqry ="SELECT JS.requestdatetime,JS.PARTYID,JS.MODULENAME,JS.id AS ID FROM  " + 
				"(SELECT MAX(requestdatetime) AS requestdatetime ,PARTYID,MODULENAME FROM( " + 
				"				select partyId,count(partyId),operation,requestdatetime,ID,modulename from cms_js_interface_log  " + 
				"				where   " + 
				"				modulename in ('CAM','CUSTOMER')  " + 
				"				and status in  ('Error')  " + 
				"				  AND PARTYID IS NOT NULL  " + 
				"          AND partyId   = '"+partyId+"' " + 
				"				group by partyId,operation ,requestdatetime,modulename,ID " + 
//				"				order by partyId   " + 
				"        ) GROUP BY PARTYID,MODULENAME) A, " + 
				"        cms_js_interface_log JS " + 
				"        WHERE A.requestdatetime = JS.requestdatetime " + 
				"        AND JS.partyId   = '"+partyId+"' " + 
				"        AND A.MODULENAME = JS.MODULENAME " + 
				"        AND JS.status ='Error' " 
//				"        order by JS.requestdatetime asc "
				;
		
		
		System.out.println("getFailedListforAdd=>sql query stgScmFlagqry=>"+stgScmFlagqry);
		
		return (List<Long>) getJdbcTemplate().query(stgScmFlagqry,new ResultSetExtractor() {
			
			@Override
			public List<Long> extractData(ResultSet rs) throws SQLException {
				List<Long> returnData = new ArrayList<Long>();
				while(rs.next()) {
					returnData.add(rs.getLong("id"));
				}
				return returnData;
			}
		});

		
	}
	
	//This query will return the latest record for the particular party Id with status. 
	//If the status is success that Id will be ignored and the latest data is interfaced.
	public List<Long> getFailedListforUpd(String partyId) {
		/*String stgScmFlagqry ="select id, requestdatetime,status  from cms_js_interface_log " + 
				"where partyId = '"+partyId+"' " + 
				"and requestdatetime >= " + 
				"(select max(requestdatetime) from cms_js_interface_log " + 
				"where partyId = '"+partyId+"' " + 
//				" and modulename in ('CAM','CUSTOMER') and status in  ('Error','Fail')  )  ";
				" and modulename in ('CAM','CUSTOMER') and status in  ('Error')  )  " +
				" and modulename in ('CAM','CUSTOMER') and status in  ('Error')  "
				;*/
		
		String stgScmFlagqry = "SELECT JS.requestdatetime,JS.PARTYID,JS.MODULENAME,JS.id AS ID ,JS.status AS STATUS FROM  " + 
				"(SELECT MAX(requestdatetime) AS requestdatetime ,PARTYID,MODULENAME FROM( " + 
				"				select partyId,count(partyId),operation,requestdatetime,ID,modulename from cms_js_interface_log  " + 
				"				where   " + 
				"				modulename in ('CAM','CUSTOMER')  " + 
				"				and status in  ('Error')  " + 
				"				  AND PARTYID IS NOT NULL  " + 
				"          AND partyId   = '"+partyId+"' " + 
				"				group by partyId,operation ,requestdatetime,modulename,ID " + 
//				"				order by partyId   " + 
				"        ) GROUP BY PARTYID,MODULENAME) A, " + 
				"        cms_js_interface_log JS " + 
				"        WHERE A.requestdatetime = JS.requestdatetime " + 
				"        AND JS.partyId   = '"+partyId+"' " + 
				"        AND A.MODULENAME = JS.MODULENAME " + 
				"        AND JS.status ='Error' "  
//				"        order by JS.requestdatetime asc "
				;
		
		
		
			return (List<Long>) getJdbcTemplate().query(stgScmFlagqry,new ResultSetExtractor() {
			
			@Override
			public List<Long> extractData(ResultSet rs) throws SQLException {
				List<Long> returnData = new ArrayList<Long>();
				while(rs.next()) {
					if(rs.getString("status").equalsIgnoreCase("Error")) {
						returnData.add(rs.getLong("id"));
					}
				}
				return returnData;
			}
		});

	}
	
	public List<OBJsInterfaceLog> getInterfaceLogDetailsForParty(Long id) {
		String query ="select ID ," + 
				"	INTERFACENAME, " + 
				"	REQUESTDATETIME, " + 
				"	RESPONSEDATETIME , " + 
				"	ERRORMESSAGE, " + 
				"	ERRORCODE, " + 
				"	STATUS, " + 
				"	TRANSACTIONID , " + 
				"    REQUESTMESSAGE , " + 
				"	RESPONSEMESSAGE , " + 
				"	MODULENAME , " + 
				"	OPERATION ," + 
				"	PARTYID, " + 
				"	PARTYNAME ," + 
				"	scmflag ," + 
				"	LIMITPROFILEID ," + 
				"    LINE_NO ,  " + 
				"    SERIAL_NO, " + 
				"    FAILCOUNT " + 
				"    from cms_js_interface_log where id ='"+id+"' " ;
		System.out.println("getInterfaceLogDetailsForParty =>sql query =>"+query);
		List resultList = getJdbcTemplate().query(query, new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            	OBJsInterfaceLog result = new OBJsInterfaceLog();
    			result.setId(rs.getLong("id"));
    			result.setInterfaceName(rs.getString("INTERFACENAME"));
    			result.setRequestDateTime(rs.getDate("REQUESTDATETIME"));
    			result.setResponseDateTime(rs.getDate("REQUESTDATETIME"));
    			result.setRequestMessage(rs.getString("REQUESTMESSAGE"));
    			result.setResponseMessage(rs.getString("RESPONSEMESSAGE"));
    			result.setErrorCode(rs.getString("ERRORCODE"));
    			result.setErrorMessage(rs.getString("ERRORMESSAGE"));
    			result.setStatus(rs.getString("STATUS"));
    			result.setTransactionId(rs.getLong("TRANSACTIONID"));
    			result.setModuleName(rs.getString("MODULENAME"));
    			result.setOperation(rs.getString("OPERATION"));
    			result.setPartyId(rs.getString("PARTYID"));
    			result.setPartyName(rs.getString("PARTYNAME"));
    			result.setScmFlag(rs.getString("scmflag"));
    			result.setFailCount(rs.getInt("FAILCOUNT"));
    			
                return result;
            }
        }
        );
   	 return resultList;

		
	}
	
	public int insertPartyToInterfaceLogBackupTable() {

   String sql = "insert into cms_js_interface_log_audit (SELECT * FROM cms_js_interface_log WHERE requestdatetime IN (SELECT MAX(requestdatetime) FROM (SELECT partyId,requestdatetime,modulename FROM cms_js_interface_log WHERE modulename IN ('CAM','CUSTOMER') AND status IN('Error') AND PARTYID IS NOT NULL GROUP BY partyId,requestdatetime, modulename) GROUP BY PARTYID,MODULENAME)) ";	
		System.out.println("Select query is getFailedPartyRequestforScm=> "+sql);
		int	insetionCount =	getJdbcTemplate().update(sql);
		return insetionCount;
	}
	
	public void updateTheFailedResponseLog(OBJsInterfaceLog log) {
		 System.out.println("Inside the updateTheFailedResponseLog to update the failed request log.getId : "+log.getId()+"log.getfailCount : "+log.getFailCount());
			String query = "UPDATE cms_js_interface_log SET RESPONSEMESSAGE = ?, RESPONSEDATETIME = ?,ERRORCODE = ?, ERRORMESSAGE = ?,STATUS = ?,FAILCOUNT = ?"
					+ " WHERE id = ?";

			getJdbcTemplate().update(query,
					new Object[]{log.getResponseMessage(),log.getResponseDateTime(),log.getErrorCode(),log.getErrorMessage(),
							log.getStatus(),log.getFailCount(),new Long(log.getId())});

		}
	
	private void mapDataToResult(Map<String, String> resultMap, ResultSet rs, String column) throws SQLException{
		if(column.endsWith("_AMT")) {
			BigDecimal amt = rs.getBigDecimal(column);
			if(amt != null) {
				String value = BigDecimal.ZERO.equals(amt) ? "0" : NumberUtils.formatBigDecimalValue(rs.getBigDecimal(column), "0.00");
				resultMap.put(column.replace("_AMT", ""), value);
			}
		}else
			resultMap.put(column, rs.getString(column));
	}
	
	public Map<String, String> getPartyDetailsForECBFParty(String partyId) {
		String commonCodeSql = "(select ECBF_ID from common_code_category_entry where category_code = '%s' and entry_code = %s)";
		String commonCVSql = "(select ENTRY_NAME from common_code_category_entry where category_code = '%s' and entry_code = %s)";

		String query = new StringBuffer("SELECT ra.lra_addr_line_1 address1, (nvl(ra.lra_addr_line_2,'') ||nvl2(ra.lra_addr_line_2,', ','')|| nvl(ra.lra_addr_line_3,'')) address2,") 
				.append(" " + String.format(commonCodeSql, CategoryCodeConstant.CommonCode_ASSET_ClASSIFICATION, "lp.Asset_Classification") + " assetclassification,") 
				.append(" " + String.format(commonCVSql, CategoryCodeConstant.CommonCode_ASSET_ClASSIFICATION, "lp.Asset_Classification") + " assetclassification_value,")
				.append(" sp.LSP_SHORT_NAME bulkborrowername, to_char(Lp.Llp_Bca_Ref_Appr_Date,'DD/MM/YYYY') camdate, to_char(lp.LLP_NEXT_ANNL_RVW_DATE,'DD/MM/YYYY') camexpirydate,")
				.append(" lp.LLP_BCA_REF_NUM camnumber, to_char(lp.LLP_EXTD_NEXT_RVW_DATE,'DD/MM/YYYY') camsrmexpirydate, sp.TOTAL_SANCTIONED_LIMIT camsanctionedamount_amt,")
				.append(" " + String.format(commonCVSql, CategoryCodeConstant.ENTITY, "sp.entity") + " constitution_value,")
				.append(" lp.cam_type camtype, " + String.format(commonCodeSql, CategoryCodeConstant.ENTITY, "sp.entity") + " constitution, ra.LRA_STD_CODE_TEL || ra.LRA_TELEPHONE_TEXT contactno,")
				.append(" to_char(sp.LSP_INCORP_DATE,'DD/MM/YYYY') dateofbirth, to_char(sp.LSP_INCORP_DATE,'DD/MM/YYYY') dateofincorporation, ra.LRA_EMAIL_TEXT emailid,")
				.append(" city.ecbf_city_id location, city.CITY_NAME location_value, sp.pan pannumber, sp.lsp_le_id partyid, ra.lra_post_code pincode,") 
				.append(" cri.avg_annual_turnover presentturnover_amt,  cri.FIRST_YEAR_TURNOVER previousturnover_amt,")
				.append(" " + String.format(commonCVSql, CategoryCodeConstant.CommonCode_RISK_GRADE, "lp.CMS_APPR_OFFICER_GRADE") +" ramrating_value,")
				.append(" " + String.format(commonCodeSql, CategoryCodeConstant.CommonCode_RISK_GRADE, "lp.CMS_APPR_OFFICER_GRADE") +" ramrating,")
				.append(" " + String.format(commonCVSql, CategoryCodeConstant.HDFC_SEGMENT, "sp.LSP_SGMNT_CODE_VALUE") +" segmentname_value,")
				.append(" rm.rm_mgr_name rmname, " + String.format(commonCodeSql, CategoryCodeConstant.HDFC_SEGMENT, "sp.LSP_SGMNT_CODE_VALUE") + " segmentname, sp.status")
				.append(" FROM SCI_LE_REG_ADDR ra, SCI_LSP_LMT_PROFILE lp, SCI_LE_SUB_PROFILE sp, SCI_LE_CRI cri, CMS_RELATIONSHIP_MGR rm, CMS_CITY city")
				.append(" WHERE sp.CMS_LE_MAIN_PROFILE_ID = ra.CMS_LE_MAIN_PROFILE_ID AND sp.LSP_LE_ID = lp.LLP_LE_ID(+) AND rm.ID(+) = sp.RELATION_MGR")
				.append(" AND ra.LRA_TYPE_VALUE in ('CORPORATE', 'ECG', 'Business Banking-Working CAP', 'Commodity Finance')")
				.append(" and city.id(+) = ra.lra_city_text")
				.append(" AND cri.CMS_LE_MAIN_PROFILE_ID (+)= sp.CMS_LE_MAIN_PROFILE_ID AND sp.lsp_le_id = ?").toString();
		
		try {
			return (Map<String, String>) getJdbcTemplate().queryForObject(query, new Object[] {partyId}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					Map<String, String> resultMap = new HashMap<String, String>();
					
					String[] columns = {"ADDRESS1", "ADDRESS2", "ASSETCLASSIFICATION", "ASSETCLASSIFICATION_VALUE", "BULKBORROWERNAME",
							"CAMDATE", "CAMEXPIRYDATE", "CAMNUMBER", "CAMSRMEXPIRYDATE", "CAMSANCTIONEDAMOUNT_AMT", "CAMTYPE",
							"CONSTITUTION", "CONSTITUTION_VALUE", "CONTACTNO", "DATEOFBIRTH", "DATEOFINCORPORATION", "EMAILID", "LOCATION",
							"LOCATION_VALUE", "PANNUMBER", "PARTYID", "PINCODE", "PRESENTTURNOVER_AMT", "PREVIOUSTURNOVER_AMT",
							"RAMRATING", "RAMRATING_VALUE", "RMNAME", "SEGMENTNAME", "SEGMENTNAME_VALUE", "STATUS"};
					
					for(String column : columns) {
						mapDataToResult(resultMap, rs, column);
					}
					return resultMap;
				}
				});
		}catch(Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Exception caught while fetching data for ECBF Party webservice", e);
			throw new RuntimeException(e);
		}
	}
	
	public void updateSCFJSLogTableForPartyAndCam(String partyId) {
		 System.out.println("Inside the updateSCFJSLogTableForPartyAndCam to update the Error Status records except max records.");
	
			String query = "UPDATE CMS_JS_INTERFACE_LOG SET status ='Fail' where ID NOT IN (                 " + 
					"SELECT DISTINCT JS.id FROM    " + 
					"				 (SELECT MAX(requestdatetime) AS requestdatetime ,PARTYID,MODULENAME FROM(   " + 
					"				 				select partyId,count(partyId),operation,requestdatetime,ID,modulename from cms_js_interface_log    " + 
					"				 				where     " + 
					"				 				modulename in ('CAM','CUSTOMER')    " + 
					"				 				and status in  ('Error')    " + 
					"				 				  AND PARTYID IS NOT NULL    " + 
					"				           AND partyId   = '"+partyId+ "'   " + 
					"				 				group by partyId,operation ,requestdatetime,modulename,ID   " + 
//					"				 				order by partyId     " + 
					"				         ) GROUP BY PARTYID,MODULENAME) A,   " + 
					"				         cms_js_interface_log JS   " + 
					"				         WHERE A.requestdatetime = JS.requestdatetime   " + 
					"				         AND JS.partyId   = '"+partyId+"'   " + 
					"				         AND A.MODULENAME = JS.MODULENAME   " + 
					"				         AND JS.status ='Error'   " + 
//					"				         order by JS.requestdatetime asc " + 
					"                 ) " + 
					"                 AND STATUS = 'Error' " + 
					"                AND modulename in ('CAM','CUSTOMER') " + 
					"                AND partyId   = '"+partyId+"' ";

			System.out.println("Before updateSCFJSLogTableForPartyAndCam to update the Error Status records except max records.SQL Query=>"+query);
			getJdbcTemplate().update(query);
			System.out.println("After updateSCFJSLogTableForPartyAndCam to update the Error Status records except max records..");
			
		}
	
}
