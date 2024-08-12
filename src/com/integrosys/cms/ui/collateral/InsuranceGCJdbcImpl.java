package com.integrosys.cms.ui.collateral;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.OBInsurancePolicy;
import com.integrosys.cms.businfra.LabelValue;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGCJdbc;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC;
import com.integrosys.cms.ui.collateral.bus.IInsuranceHistoryReport;
import com.integrosys.cms.ui.collateral.bus.OBInsuranceHistoryReport;

public class InsuranceGCJdbcImpl  extends JdbcDaoSupport implements IInsuranceGCJdbc{

	private static final String UNPROCESS ="N";
	
	private static final String PROCESS ="Y";
	
	private static final String UNPROCESS_MODIFY ="NM";
	
	private static final String PROCESS_REJECT ="YR";
	
	
	
	
//	
//	private static final String SELECT_INSURANCE_TRX = "SELECT  ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON from CMS_STAGE_GC_INSURANCE where IS_PROCESSED='N'";
//	
//	private static final String MAKER_CLOSE_INSURANCE_TRX = "SELECT  ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON  from CMS_STAGE_GC_INSURANCE where IS_PROCESSED='YR'";
//	
//	private static final String SELECT_ACTUAL_INSURANCE_TRX = "SELECT  ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON  from CMS_GC_INSURANCE where IS_PROCESSED='Y'AND DEPRECATED='N'";
//	
//	private static final String SELECT_STAGE_INSURANCE_TRX = "SELECT  ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON  from CMS_STAGE_GC_INSURANCE where CMS_COLLATERAL_ID='";
//	
//	private static final String SELECT_DELETE_STAGE_INSURANCE_TRX = "SELECT ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON  from CMS_STAGE_GC_INSURANCE where IS_PROCESSED='N'AND DEPRECATED='Y'";
//	
//	private static final String SELECT_PROCESS_STAGE_INSURANCE_TRX = "SELECT ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON  from CMS_STAGE_GC_INSURANCE where IS_PROCESSED in ('N','NM') AND DEPRECATED='N'";
//	
//	private static final String SELECT_REJECT_STAGE_INSURANCE_TRX = "SELECT ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON  from CMS_STAGE_GC_INSURANCE where IS_PROCESSED='YR'AND (DEPRECATED='N'OR DEPRECATED='Y')";
//	
//	private static final String SELECT_ACTUAL_CODE_INSURANCE_TRX = "SELECT ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON  from CMS_GC_INSURANCE where IS_PROCESSED='Y'AND DEPRECATED='N'";
//	
//	private static final String SELECT_DRAFT_STAGE_INSURANCE_TRX = "SELECT ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON  from CMS_STAGE_GC_INSURANCE where (IS_PROCESSED='DE' OR IS_PROCESSED='DD' OR IS_PROCESSED='DA')AND (DEPRECATED='N'OR DEPRECATED='Y')";
	

	//Uma Khot::Insurance Deferral maintainance
	private static final String SELECT_INSURANCE_TRX = "SELECT  ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON,INSURED_AGAINST,INSURED_ADDRESS,REMARK2,ORIGINAL_TARGET_DATE,NEXT_DUE_DATE,DATE_DEFERRED,CREDIT_APPROVER,WAIVED_DATE,INSURANCE_STATUS,OLD_POLICY_NO from CMS_STAGE_GC_INSURANCE where IS_PROCESSED='N'";
	
	private static final String MAKER_CLOSE_INSURANCE_TRX = "SELECT  ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON,INSURED_AGAINST,INSURED_ADDRESS,REMARK2,ORIGINAL_TARGET_DATE,NEXT_DUE_DATE,DATE_DEFERRED,CREDIT_APPROVER,WAIVED_DATE,INSURANCE_STATUS,OLD_POLICY_NO  from CMS_STAGE_GC_INSURANCE where IS_PROCESSED='YR'";
	
	private static final String SELECT_ACTUAL_INSURANCE_TRX = "SELECT  ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON,INSURED_AGAINST,INSURED_ADDRESS,REMARK2,ORIGINAL_TARGET_DATE,NEXT_DUE_DATE,DATE_DEFERRED,CREDIT_APPROVER,WAIVED_DATE,INSURANCE_STATUS,OLD_POLICY_NO  from CMS_GC_INSURANCE where IS_PROCESSED='Y'AND DEPRECATED='N'";
	
	private static final String SELECT_STAGE_INSURANCE_TRX = "SELECT  ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON,INSURED_AGAINST,INSURED_ADDRESS,REMARK2,ORIGINAL_TARGET_DATE,NEXT_DUE_DATE,DATE_DEFERRED,CREDIT_APPROVER,WAIVED_DATE,INSURANCE_STATUS,OLD_POLICY_NO  from CMS_STAGE_GC_INSURANCE where CMS_COLLATERAL_ID='";
	
	private static final String SELECT_DELETE_STAGE_INSURANCE_TRX = "SELECT ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON,INSURED_AGAINST,INSURED_ADDRESS,REMARK2,ORIGINAL_TARGET_DATE,NEXT_DUE_DATE,DATE_DEFERRED,CREDIT_APPROVER,WAIVED_DATE,INSURANCE_STATUS,OLD_POLICY_NO  from CMS_STAGE_GC_INSURANCE where IS_PROCESSED='N'AND DEPRECATED='Y' AND CMS_COLLATERAL_ID NOT IN ('20120429100069413')";
	
	private static final String SELECT_PROCESS_STAGE_INSURANCE_TRX = "SELECT ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON,INSURED_AGAINST,INSURED_ADDRESS,REMARK2,ORIGINAL_TARGET_DATE,NEXT_DUE_DATE,DATE_DEFERRED,CREDIT_APPROVER,WAIVED_DATE,INSURANCE_STATUS,OLD_POLICY_NO  from CMS_STAGE_GC_INSURANCE where IS_PROCESSED in ('N','NM') AND DEPRECATED='N'";
	
	private static final String SELECT_REJECT_STAGE_INSURANCE_TRX = "SELECT ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON,INSURED_AGAINST,INSURED_ADDRESS,REMARK2,ORIGINAL_TARGET_DATE,NEXT_DUE_DATE,DATE_DEFERRED,CREDIT_APPROVER,WAIVED_DATE,INSURANCE_STATUS,OLD_POLICY_NO  from CMS_STAGE_GC_INSURANCE where IS_PROCESSED='YR'AND (DEPRECATED='N'OR DEPRECATED='Y')";
	
	private static final String SELECT_ACTUAL_CODE_INSURANCE_TRX = "SELECT ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON,INSURED_AGAINST,INSURED_ADDRESS,REMARK2,ORIGINAL_TARGET_DATE,NEXT_DUE_DATE,DATE_DEFERRED,CREDIT_APPROVER,WAIVED_DATE,INSURANCE_STATUS,OLD_POLICY_NO  from CMS_GC_INSURANCE where IS_PROCESSED='Y'AND DEPRECATED='N'";
	
	private static final String SELECT_DRAFT_STAGE_INSURANCE_TRX = "SELECT ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON,INSURED_AGAINST,INSURED_ADDRESS,REMARK2,ORIGINAL_TARGET_DATE,NEXT_DUE_DATE,DATE_DEFERRED,CREDIT_APPROVER,WAIVED_DATE,INSURANCE_STATUS,OLD_POLICY_NO  from CMS_STAGE_GC_INSURANCE where (IS_PROCESSED='DE' OR IS_PROCESSED='DD' OR IS_PROCESSED='DA')AND (DEPRECATED='N'OR DEPRECATED='Y')";
	private static final String SELECT_SEARCH_ACTUAL = "SELECT COUNT(1) FROM CMS_GC_INSURANCE WHERE ";
	
	private static final String SELECT_ALL_INSURANCE_TRX = "SELECT  ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE,POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON,INSURED_AGAINST,INSURED_ADDRESS,REMARK2,ORIGINAL_TARGET_DATE,NEXT_DUE_DATE,DATE_DEFERRED,CREDIT_APPROVER,WAIVED_DATE,INSURANCE_STATUS,OLD_POLICY_NO from CMS_GC_INSURANCE";
	
	public int getActualCount(String collateralID)throws InsuranceCGException {
		List resultList = null;
		String searchById=collateralID;
		int count=0;
		String GET_ID_QUERY_STRING = SELECT_SEARCH_ACTUAL
		+ "INS_CODE='"+ searchById +"'";
		
		
		try {

			count = getJdbcTemplate().queryForInt(GET_ID_QUERY_STRING);
			/*(GET_ID_QUERY_STRING,
					new InsuranceRowMapper());*/

		} catch (Exception e) {
			throw new InsuranceCGException("ERROR-- While retriving Insurance");
		}
		//SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return count;
		}
	
	
	
	public SearchResult getAllInsurance(String collateralID)throws InsuranceCGException {
		List resultList = null;
		String searchById=collateralID;
		
		String GET_ID_QUERY_STRING = SELECT_INSURANCE_TRX
		+ "AND CMS_COLLATERAL_ID=' " + searchById +"'";
		
		
		try {

			resultList = getJdbcTemplate().query(GET_ID_QUERY_STRING,
					new InsuranceRowMapper());

		} catch (Exception e) {
			throw new InsuranceCGException("ERROR-- While retriving Insurance");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	public SearchResult getAllCloseInsurance(String collateralID)throws InsuranceCGException {
		List resultList = null;
		String searchById=collateralID;
		
		String GET_ID_QUERY_STRING = MAKER_CLOSE_INSURANCE_TRX
		+ "AND CMS_COLLATERAL_ID=' " + searchById +"'";
		
		
		try {

			resultList = getJdbcTemplate().query(GET_ID_QUERY_STRING,
					new InsuranceRowMapper());

		} catch (Exception e) {
			throw new InsuranceCGException("ERROR-- While retriving Insurance");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	public SearchResult getAllActualInsurance(String collateralID)throws InsuranceCGException {
		List resultList = null;
		String searchById=collateralID;
		
		String GET_ID_QUERY_STRING = SELECT_ACTUAL_INSURANCE_TRX
		+ "AND CMS_COLLATERAL_ID=' " + searchById +"'";
		
		
		try {

			resultList = getJdbcTemplate().query(GET_ID_QUERY_STRING,
					new InsuranceRowMapper());

		} catch (Exception e) {
			throw new InsuranceCGException("ERROR-- While retriving Insurance");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	
	public SearchResult getAllStageInsurance(String collateralID)throws InsuranceCGException {
		List resultList = null;
		String searchById=collateralID;
		
		String GET_ID_QUERY_STRING = SELECT_STAGE_INSURANCE_TRX
		+ searchById +"'";
		
		
		try {

			resultList = getJdbcTemplate().query(GET_ID_QUERY_STRING,
					new InsuranceRowMapper());

		} catch (Exception e) {
			throw new InsuranceCGException("ERROR-- While retriving Insurance");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	
	public SearchResult getAllRejectStageInsurance(String collateralID)throws InsuranceCGException {
		List resultList = null;
		String searchById=collateralID;
		
		String GET_ID_QUERY_STRING = SELECT_REJECT_STAGE_INSURANCE_TRX
		+ "AND CMS_COLLATERAL_ID=' " + searchById +"'";
		
		
		try {

			resultList = getJdbcTemplate().query(GET_ID_QUERY_STRING,
					new InsuranceRowMapper());

		} catch (Exception e) {
			throw new InsuranceCGException("ERROR-- While retriving Insurance");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	public SearchResult getAllDraftStageInsurance(String collateralID)throws InsuranceCGException {
		List resultList = null;
		String searchById=collateralID;
		
		String GET_ID_QUERY_STRING = SELECT_DRAFT_STAGE_INSURANCE_TRX
		+ "AND CMS_COLLATERAL_ID=' " + searchById +"'";
		
		
		try {

			resultList = getJdbcTemplate().query(GET_ID_QUERY_STRING,
					new InsuranceRowMapper());

		} catch (Exception e) {
			throw new InsuranceCGException("ERROR-- While retriving Insurance");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	public SearchResult getAllProcessStageInsurance(String collateralID)throws InsuranceCGException {
		List resultList = null;
		String searchById=collateralID;
		
		String GET_ID_QUERY_STRING = SELECT_PROCESS_STAGE_INSURANCE_TRX
		+ "AND CMS_COLLATERAL_ID=' " + searchById +"'";
		
		
		try {

			resultList = getJdbcTemplate().query(GET_ID_QUERY_STRING,
					new InsuranceRowMapper());

		} catch (Exception e) {
			throw new InsuranceCGException("ERROR-- While retriving Insurance");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	
	public SearchResult getAllDeleteStageInsurance(String collateralID)throws InsuranceCGException {
		List resultList = null;
		String searchById=collateralID;
		
		String GET_ID_QUERY_STRING = SELECT_DELETE_STAGE_INSURANCE_TRX
		+ "AND CMS_COLLATERAL_ID=' " + searchById +"'";
		
		
		try {

			resultList = getJdbcTemplate().query(GET_ID_QUERY_STRING,
					new InsuranceRowMapper());

		} catch (Exception e) {
			throw new InsuranceCGException("ERROR-- While retriving Insurance");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	public SearchResult getAllActualCodeInsurance(String collateralID)throws InsuranceCGException {
		List resultList = null;
		String searchById=collateralID;
		
		String GET_ID_QUERY_STRING = SELECT_ACTUAL_CODE_INSURANCE_TRX
		+ "AND CMS_COLLATERAL_ID=' " + searchById +"'";
		
		
		try {

			resultList = getJdbcTemplate().query(GET_ID_QUERY_STRING,
					new InsuranceRowMapper());

		} catch (Exception e) {
			throw new InsuranceCGException("ERROR-- While retriving Insurance");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}

	
	   	
	      
			
			
	
	    public List getAllInsuranceForSec(String collateralId) throws InsuranceCGException {
	        String query="";
	       // String query =  " SELECT FROM "+ entityName +" where CMS_COLLATERAL_ID = " + collateralId ;
	   	 query = "SELECT ins_policy_no, ins_company,id  FROM CMS_GC_INSURANCE "
	        +" where CMS_COLLATERAL_ID = " + collateralId;
	   
	   	
	   	 List resultList = getJdbcTemplate().query(query, new RowMapper() {

	            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	           	 
	           	 OBInsuranceGC result = new OBInsuranceGC();
	    			
	    			result.setInsurancePolicyNo(rs
	    					.getString("ins_policy_no"));
	    			result.setInsuranceCompany(rs
	    					.getString("ins_company"));
	    			result.setId(
	    					new Long(rs.getString("id")).longValue());
	    			/* String[] stringArray = new String[3];
	                stringArray[0] = rs.getString("LSP_LE_ID");
	                stringArray[1] = rs.getString("LSP_SHORT_NAME");
	                stringArray[2] = rs.getString("CMS_LE_SUB_PROFILE_ID");*/

	                return result;
	            }
	        }
	        );

	        return resultList;
	        
	     
	   }
		
	public class InsuranceRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBInsuranceGC result = new OBInsuranceGC();
			result.setId(rs.getLong("ID"));			
			result.setVersionTime(rs.getLong("VERSION_TIME"));
			result.setCreationDate(rs.getDate("CREATION_DATE"));
			result.setParentId(rs.getLong("CMS_COLLATERAL_ID"));
			result.setInsuranceCode(rs.getString("INS_CODE"));
			result.setInsuranceRequired(rs.getString("INS_REQUIRED"));
			result.setSelectComponent(rs.getString("INS_SELECT_COMPONENT"));
			result.setInsurancePolicyNo(rs.getString("INS_POLICY_NO"));			
			result.setCoverNoteNo(rs.getString("COVER_NOTE_NO"));
			result.setInsuranceCompany(rs.getString("INS_COMPANY"));
			result.setInsuranceCurrency(rs.getString("INS_CURRENCY"));
			result.setInsuranceCoverge(rs.getString("INS_COVERAGE_TYPE"));
			result.setInsurancePolicyAmt(rs.getString("POLICY_AMOUNT"));
			result.setInsuredAmount(rs.getString("INSURED_AMOUNT"));
			result.setReceivedDate(rs.getDate("RECEIVED_DATE"));			
			result.setEffectiveDate(rs.getDate("EFFECTIVE_DATE"));
			result.setExpiryDate(rs.getDate("EXPIRY_DATE"));
			result.setInsurancePremium(rs.getString("INS_PREMIUM"));
			result.setInsuranceDefaulted(rs.getString("INS_DEFAULTED"));
			result.setRemark(rs.getString("INS_REMARK"));
			
			result.setIsProcessed(rs.getString("IS_PROCESSED"));			
			result.setDeprecated(rs.getString("DEPRECATED"));
			result.setLastApproveBy(rs.getString("LAST_APPROVED_BY"));
			result.setLastApproveOn(rs.getTimestamp("LAST_APPROVED_ON"));
			result.setLastUpdatedBy(rs.getString("LAST_UPDATED_BY"));
			result.setLastUpdatedOn(rs.getTimestamp("LAST_UPDATED_ON"));
			
			//Uma Khot::Insurance Deferral maintainance
			result.setInsuredAgainst(rs.getString("INSURED_AGAINST"));
			result.setInsuredAddress(rs.getString("INSURED_ADDRESS"));
			result.setRemark2(rs.getString("REMARK2"));
			result.setOriginalTargetDate(rs.getDate("ORIGINAL_TARGET_DATE"));
			result.setNextDueDate(rs.getDate("NEXT_DUE_DATE"));
			result.setDateDeferred(rs.getDate("DATE_DEFERRED"));
			result.setCreditApprover(rs.getString("CREDIT_APPROVER"));
			result.setWaivedDate(rs.getDate("WAIVED_DATE"));
			result.setInsuranceStatus(rs.getString("INSURANCE_STATUS"));
			result.setOldPolicyNo(rs.getString("OLD_POLICY_NO"));
			
			return result;
		}
	}


	 //Uma Khot::Insurance Deferral maintainance
	
	
	public SearchResult getAllInsuranceDetails(long collateralID)throws InsuranceCGException {
		List resultList = null;
		long searchById=collateralID;
		
		String GET_ID_QUERY_STRING = SELECT_ALL_INSURANCE_TRX + " where CMS_COLLATERAL_ID=" + searchById;
		
		try {

			resultList = getJdbcTemplate().query(GET_ID_QUERY_STRING,new InsuranceRowMapper());

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this,"Error in getAllInsuranceDetails:"+e.getMessage());
			throw new InsuranceCGException("ERROR-- While retriving Insurance");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	public List<String> getInsuranceId(long collateralID)throws InsuranceCGException {
		List<String> resultList = new ArrayList();
		
		String GET_ID_QUERY_STRING = "select INS_CODE from cms_gc_insurance where deprecated='N' and CMS_COLLATERAL_ID =" + collateralID;
		DBUtil dbutil=null;
		ResultSet rs=null;
		try {
			dbutil=new DBUtil();
			dbutil.setSQL(GET_ID_QUERY_STRING);
			rs=dbutil.executeQuery();
			//resultList = getJdbcTemplate().queryForList(GET_ID_QUERY_STRING);
			while(rs.next()){
				// int numberOfRecord=rs.getFetchSize()
				 String id=rs.getString(1);
				 resultList.add(id);
				 
			}
	} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this,"Error in getInsuranceId:"+e.getMessage());
			throw new InsuranceCGException("ERROR-- While retriving getInsuranceId");
		}
		finally{
			try {
				dbutil.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultList;
		}
	
	public List<String> getInsuranceIdFromChecklist(long checklistId)throws InsuranceCGException {
		List<String> resultList = new ArrayList();
		
		String GET_ID_QUERY_STRING = "select insurance_id from CMS_CHECKLIST_ITEM WHERE CHECKLIST_ID=" + checklistId +" and insurance_id is not null";
		DBUtil dbutil=null;
		ResultSet rs=null;
			try {
				dbutil=new DBUtil();
				dbutil.setSQL(GET_ID_QUERY_STRING);
				rs=dbutil.executeQuery();
				//resultList = getJdbcTemplate().queryForList(GET_ID_QUERY_STRING);
				while(rs.next()){
					// int numberOfRecord=rs.getFetchSize()
					String id=rs.getString(1);
					 resultList.add(id);
					 
				}
		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this,"Error in getInsuranceIdFromChecklist:"+e.getMessage());
			throw new InsuranceCGException("ERROR-- While retriving getInsuranceIdFromChecklist");
		}
		finally{
			try {
				dbutil.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultList;
		
		}
	
	public Set<String> getInsIdNotPresentInChecklist(long checklistId,long collateralID)throws InsuranceCGException {
		Set<String> resultList = new HashSet();
		
		String GET_ID_QUERY_STRING = "SELECT INS_CODE FROM CMS_GC_INSURANCE WHERE DEPRECATED='N' and IS_PROCESSED='Y' and CMS_COLLATERAL_ID ="+collateralID+" AND INS_CODE NOT IN (select insurance_id from CMS_CHECKLIST_ITEM WHERE CHECKLIST_ID="+checklistId+" and  insurance_id is not null)";
		DBUtil dbutil=null;
		ResultSet rs=null;
			try {
				dbutil=new DBUtil();
				dbutil.setSQL(GET_ID_QUERY_STRING);
				rs=dbutil.executeQuery();
				while(rs.next()){
					// int numberOfRecord=rs.getFetchSize()
					String id=rs.getString(1);
					 resultList.add(id);
					 
				}
						
       } catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this,"Error in getInsIdNotPresentInChecklist:"+e.getMessage());
			throw new InsuranceCGException("ERROR-- While retriving getInsIdNotPresentInChecklist");
		}
       
		return resultList;
		}
	
	
	public String updateInsuranceDetailStg(long checklistId) throws Exception {
		
		String flag="fail";
		System.out.println("checklist id inside InsuranceGc JDBC impl updateInsuranceDetailStg 510 is : "+checklistId);
		try{
			DefaultLogger.debug(this, "calling updateInsuranceDetailStg");
			getJdbcTemplate().execute("{call SP_UPDATE_INSURANCE_DET_STG("+checklistId+")}", new CallableStatementCallback() {
				
				@Override
				public Object doInCallableStatement(
						CallableStatement cs)
						throws SQLException, DataAccessException {
					cs.executeUpdate();
					return null;
				}
			});
			 flag="success";
		}catch(Exception e){
		
			e.printStackTrace();
			DefaultLogger.debug(this, e.getMessage());
			throw new Exception("Exception in updateInsuranceDetailStg.");
		}
		
		DefaultLogger.debug(this, "completed updateInsuranceDetailStg");
		return flag;
	
	}
	
public String updateInsuranceDetail(long checklistId) throws Exception {
		
		String flag="fail";
		System.out.println("checklist id inside InsuranceGc JDBC impl updateInsuranceDetail 539 is : "+checklistId);
		try{
			DefaultLogger.debug(this, "calling updateInsuranceDetail");
			getJdbcTemplate().execute("{call SP_UPDATE_INSURANCE_DETAILS("+checklistId+")}", new CallableStatementCallback() {
				
				@Override
				public Object doInCallableStatement(
						CallableStatement cs)
						throws SQLException, DataAccessException {
					cs.executeUpdate();
					return null;
				}
			});
			 flag="success";
		}catch(Exception e){
		
			e.printStackTrace();
			DefaultLogger.debug(this, e.getMessage());
			throw new Exception("Exception in updateInsuranceDetail.");
		}
		
		DefaultLogger.debug(this, "calling updateInsuranceDetail");
		return flag;
	
	}


public String updateChecklistForAllCustomersStg(String checklistId,String insuranceId) throws Exception {
	
	String flag="fail";
	try{
		DefaultLogger.debug(this, "calling updateChecklistForAllCustomersStg");
		getJdbcTemplate().execute("{call SP_UPDATE_CHECKLIST_ITEM_STAGE("+checklistId+","+insuranceId+")}", new CallableStatementCallback() {
			
			@Override
			public Object doInCallableStatement(
					CallableStatement cs)
					throws SQLException, DataAccessException {
				cs.executeUpdate();
				return null;
			}
		});
		 flag="success";
	}catch(Exception e){
	
		e.printStackTrace();
		DefaultLogger.debug(this, e.getMessage());
		throw new Exception("Exception in updateChecklistForAllCustomersStg.");
	}
	
	DefaultLogger.debug(this, "completed updateChecklistForAllCustomersStg");
	return flag;

}

public String updateChecklistForAllCustomers(String checklistId,String insuranceId) throws Exception {
	
	String flag="fail";
	try{
		DefaultLogger.debug(this, "calling updateChecklistForAllCustomers");
		getJdbcTemplate().execute("{call SP_UPDATE_CHECKLIST_ITEM("+checklistId+","+insuranceId+")}", new CallableStatementCallback() {
			
			@Override
			public Object doInCallableStatement(
					CallableStatement cs)
					throws SQLException, DataAccessException {
				cs.executeUpdate();
				return null;
			}
		});
		 flag="success";
	}catch(Exception e){
	
		e.printStackTrace();
		DefaultLogger.debug(this, e.getMessage());
		throw new Exception("Exception in updateChecklistForAllCustomers.");
	}
	
	DefaultLogger.debug(this, "completed updateChecklistForAllCustomers");
	return flag;

}

//Insurance defferal CR: get insurance policy from cms_insurance-policy

public SearchResult getAllInsurancePolicy(long collateralID) {
	List resultList = null;
	long searchById=collateralID;
	
	String GET_INSURANCE = "SELECT INSURANCE_POLICY_ID ,POLICY_NO,CURRENCY_CODE,INSURER_NAME,EXPIRY_DATE, "+
" INSURABLE_AMT,INSURED_AMT,EFFECTIVE_DATE,INSURED_AGAINST,ADDRESS,INSR_ID,STATUS,CMS_COLLATERAL_ID,REMARKS,INS_INSSUE_DATE, "+
" INSURANCE_COMPANY_NAME,INSURANCE_PREMIUM,TYPE_OF_PERILS1,REMARK1,REMARK2,RECEIVED_DATE,INSURANCE_STATUS,ORIGINAL_TARGET_DATE,NEXT_DUE_DATE,DATE_DEFERRED,WAIVED_DATE, "+
" CREDIT_APPROVER ,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON FROM CMS_INSURANCE_POLICY WHERE CMS_COLLATERAL_ID ='" +
 searchById +"'";

	
	try {

		resultList = getJdbcTemplate().query(GET_INSURANCE,new InsurancePolicyRowMapper());

	} catch (Exception e) {
		e.printStackTrace();
		DefaultLogger.debug(this,"Error in getAllInsurancePolicy:"+e.getMessage());
		throw new InsuranceCGException("ERROR-- While retriving Insurance");
	}
	SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
	return searchresult;
	}

public List<String> getInsurancePolicyId(long collateralID)throws InsuranceCGException {
	List<String> resultList = new ArrayList();
	
	String GET_ID_QUERY_STRING = "select INSR_ID from cms_insurance_policy where STATUS='ACTIVE' and CMS_COLLATERAL_ID =" + collateralID;
	DBUtil dbutil=null;
	ResultSet rs=null;
	try {
		dbutil=new DBUtil();
		dbutil.setSQL(GET_ID_QUERY_STRING);
		rs=dbutil.executeQuery();
		
		while(rs.next()){
			
			 String id=rs.getString(1);
			 resultList.add(id);
			 
		}
} catch (Exception e) {
		e.printStackTrace();
		DefaultLogger.debug(this,"Error in getInsurancePolicyId:"+e.getMessage());
		throw new InsuranceCGException("ERROR-- While retriving getInsurancePolicyId");
	}
	finally{
		try {
			dbutil.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return resultList;
	}


public Set<String> getInsPolicyIdNotPresentInChecklist(long checklistId,long collateralID)throws InsuranceCGException {
	Set<String> resultList = new HashSet();
	
	String GET_ID_QUERY_STRING = "SELECT INSR_ID FROM CMS_INSURANCE_POLICY WHERE STATUS='ACTIVE' and CMS_COLLATERAL_ID ="+collateralID+" AND INSR_ID NOT IN (select insurance_id from CMS_CHECKLIST_ITEM WHERE CHECKLIST_ID="+checklistId+" and  insurance_id is not null)";
	DBUtil dbutil=null;
	ResultSet rs=null;
		try {
			dbutil=new DBUtil();
			dbutil.setSQL(GET_ID_QUERY_STRING);
			rs=dbutil.executeQuery();
			while(rs.next()){
				// int numberOfRecord=rs.getFetchSize()
				String id=rs.getString(1);
				 resultList.add(id);
				 
			}
					
   } catch (Exception e) {
		e.printStackTrace();
		DefaultLogger.debug(this,"Error in getInsPolicyIdNotPresentInChecklist:"+e.getMessage());
		throw new InsuranceCGException("ERROR-- While retriving getInsPolicyIdNotPresentInChecklist");
	}
   
	return resultList;
	}

public class InsurancePolicyRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		OBInsurancePolicy result = new OBInsurancePolicy();
		result.setInsurancePolicyID(rs.getLong("INSURANCE_POLICY_ID"));			
		//result.setInsura(rs.getLong("INSURANCE_CATEGORY"));
		result.setPolicyNo(rs.getString("POLICY_NO"));
		result.setCurrencyCode(rs.getString("CURRENCY_CODE"));
		result.setInsurerName(rs.getString("INSURER_NAME"));
		
		result.setExpiryDate(rs.getDate("EXPIRY_DATE"));
		
		result.setInsurableAmount(new Amount(rs.getDouble("INSURABLE_AMT"),"INR"));			
		result.setInsuredAmount(new Amount(rs.getDouble("INSURED_AMT"),"INR"));
		result.setEffectiveDate(rs.getDate("EFFECTIVE_DATE"));
		result.setInsuredAgainst(rs.getString("INSURED_AGAINST"));
		result.setInsuredAddress1(rs.getString("ADDRESS"));
		result.setRefID(rs.getString("INSR_ID"));
		result.setStatus(rs.getString("STATUS"));
	//	result.setCrs.getDate("CMS_COLLATERAL_ID"));			
		result.setRemark1(rs.getString("REMARKS"));
		result.setInsIssueDate(rs.getDate("INS_INSSUE_DATE"));
	//	result.setExpiryDate(rs.getDate("INSURANCE_COMPANY_NAME"));
		
		result.setInsurancePremium(new Amount(rs.getDouble("INSURANCE_PREMIUM"),"INR"));
		result.setTypeOfPerils1(rs.getString("TYPE_OF_PERILS1"));
		
		result.setRemark1(rs.getString("REMARK1"));			
		result.setReceivedDate(rs.getDate("RECEIVED_DATE"));
		
		result.setRemark2(rs.getString("REMARK2"));
		result.setOriginalTargetDate(rs.getDate("ORIGINAL_TARGET_DATE"));
		result.setNextDueDate(rs.getDate("NEXT_DUE_DATE"));
		result.setDateDeferred(rs.getDate("DATE_DEFERRED"));
		result.setCreditApprover(rs.getString("CREDIT_APPROVER"));
		result.setWaivedDate(rs.getDate("WAIVED_DATE"));
		result.setInsuranceStatus(rs.getString("INSURANCE_STATUS"));
		
		result.setLastApproveBy(rs.getString("LAST_APPROVED_BY"));
		result.setLastApproveOn(rs.getTimestamp("LAST_APPROVED_ON"));
		result.setLastUpdatedBy(rs.getString("LAST_UPDATED_BY"));
		result.setLastUpdatedOn(rs.getTimestamp("LAST_UPDATED_ON"));
		
		return result;
	}
}

public List getRecentInsuranceForCode(String insuranceCode,String collateralId) throws InsuranceCGException{
	List resultList = null;
	
	String GET_ID_QUERY_STRING = "SELECT ID,VERSION_TIME,CREATION_DATE,CMS_COLLATERAL_ID,INS_CODE,INS_REQUIRED,INS_SELECT_COMPONENT,INS_POLICY_NO,COVER_NOTE_NO,INS_COMPANY,INS_CURRENCY,INS_COVERAGE_TYPE," +
			"POLICY_AMOUNT,INSURED_AMOUNT,RECEIVED_DATE,EFFECTIVE_DATE,EXPIRY_DATE,INS_PREMIUM,INS_DEFAULTED,INS_REMARK,IS_PROCESSED,DEPRECATED,LAST_UPDATED_BY,LAST_UPDATED_ON,LAST_APPROVED_BY,LAST_APPROVED_ON,INSURED_AGAINST,INSURED_ADDRESS,REMARK2," +
			"ORIGINAL_TARGET_DATE,NEXT_DUE_DATE,DATE_DEFERRED,CREDIT_APPROVER,WAIVED_DATE,INSURANCE_STATUS ,OLD_POLICY_NO FROM (SELECT * FROM CMS_STAGE_GC_INSURANCE WHERE CMS_COLLATERAL_ID='"+collateralId+"' AND INS_CODE='"+insuranceCode+"'  order by id desc) where  rownum=1";
	
	
	try {

		resultList = getJdbcTemplate().query(GET_ID_QUERY_STRING,
				new InsuranceRowMapper());

	} catch (Exception e) {
		throw new InsuranceCGException("ERROR-- While retriving getRecentInsuranceForCode");
	}
	
	return resultList;	
}

	public InsuranceHistorySearchResult getInsuranceHistory(InsuranceHistorySearchCriteria criteria) {
		InsuranceHistorySearchResult result = new InsuranceHistorySearchResult();
		result.setCollateralId(criteria.getCollateralId());
		result.setInsuranceCompanyName(criteria.getInsuranceCompanyName());
		result.setReceivedDateFrom(criteria.getReceivedDateFrom());
		result.setReceivedDateTo(criteria.getReceivedDateTo());
		
		if (criteria.getCollateralId() == null)
			return result;
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("collateralId", criteria.getCollateralId());
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT decode(insurance_status,'AWAITING','PENDING',insurance_status) insurance_status,");
		sql.append(" original_target_date, b.COMPANY_NAME, ");
		sql.append("ins_policy_no, insured_amount, expiry_date, received_date ");
		sql.append("FROM cms_gc_insurance a, CMS_INSURANCE_COVERAGE b WHERE cms_collateral_id= :collateralId ");
		sql.append(" and b.INSURANCE_COVERAGE_CODE(+)= a.INS_COMPANY ");

		
		
		if(StringUtils.isNotBlank(criteria.getInsuranceCompanyName())) {
			String insCompName=criteria.getInsuranceCompanyName().concat("%");
			sql.append(" AND b.COMPANY_NAME LIKE (:insCompanyName) ");
			parameters.addValue("insCompanyName", insCompName);
		}		
		if(StringUtils.isNotBlank(criteria.getReceivedDateFrom())) {
			sql.append(" AND trunc(received_date) >= :receivedDateFrom ");
			parameters.addValue("receivedDateFrom", criteria.getReceivedDateFrom());
		}
		if(StringUtils.isNotBlank(criteria.getReceivedDateTo())) {
			sql.append(" AND trunc(received_date) <= :receivedDateTo");
			parameters.addValue("receivedDateTo", criteria.getReceivedDateTo());
		}
		
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		System.out.println("InsuranceGCJdbcImpl.java=>InsuranceHistorySearchResult getInsuranceHistory=>criteria=>"+criteria);
				System.out.println("InsuranceGCJdbcImpl.java=>InsuranceHistorySearchResult getInsuranceHistory=>criteria.getCollateralId()=>"+criteria.getCollateralId()+"...sql.toString()=>"+sql.toString());
		List<IInsuranceHistoryItem> resultList = jdbcTemplate.query(sql.toString(), 
				parameters, new RowMapper<IInsuranceHistoryItem>() {
			public IInsuranceHistoryItem mapRow(ResultSet rs, int rowNum) throws SQLException {
				IInsuranceHistoryItem item = new OBInsuranceHistoryItem();
				item.setDueDate(rs.getDate("original_target_date"));
				item.setStatus(rs.getString("insurance_status"));
				item.setInsuranceCompanyName(rs.getString("COMPANY_NAME"));
				item.setInsurancePolicyNo(rs.getString("ins_policy_no"));
				item.setInsuredAmount(rs.getBigDecimal("insured_amount"));
				item.setExpiryDate(rs.getDate("expiry_date"));
				item.setReceivedDate(rs.getDate("received_date"));
				return item;
			}
		});
		
		result.setInsuranceHistory(resultList);

		return result;
	}
	
	public List<IInsuranceHistoryReport> getFullInsuranceHistory(InsuranceHistorySearchCriteria criteria) {
		
		if (criteria.getCollateralId() == null)
			return Collections.emptyList();
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("collateralId", criteria.getCollateralId());
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT rownum, insurance_status, original_target_date, b.COMPANY_NAME as COMPANY_NAME, ");
		sql.append("ins_policy_no, insured_amount, expiry_date, received_date, ");
		sql.append("ins_company old_ins_company, ins_policy_no old_ins_policy_no, ");
		sql.append("insured_amount old_insured_amount, expiry_date old_expiry_date, received_date old_received_date ");
		sql.append("FROM cms_gc_insurance a, CMS_INSURANCE_COVERAGE b  WHERE cms_collateral_id= :collateralId "); 
		sql.append(" and b.INSURANCE_COVERAGE_CODE(+)= a.INS_COMPANY ");
		
		if(StringUtils.isNotBlank(criteria.getInsuranceCompanyName())) {
			String insCompName=criteria.getInsuranceCompanyName().concat("%");
			sql.append(" AND b.COMPANY_NAME LIKE (:insCompanyName) ");
			parameters.addValue("insCompanyName",insCompName);
		}		
		if(StringUtils.isNotBlank(criteria.getReceivedDateFrom())) {
			sql.append(" AND trunc(received_date) >= :receivedDateFrom ");
			parameters.addValue("receivedDateFrom", criteria.getReceivedDateFrom());
		}
		if(StringUtils.isNotBlank(criteria.getReceivedDateTo())) {
			sql.append(" AND trunc(received_date) <= :receivedDateTo");
			parameters.addValue("receivedDateTo", criteria.getReceivedDateTo());
		}
		
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		System.out.println("List<IInsuranceHistoryReport> getFullInsuranceHistory=>parameters.toString()=>"+parameters.getValues());
		System.out.println("List<IInsuranceHistoryReport> getFullInsuranceHistory=>sql.toString()=>"+sql.toString());
		@SuppressWarnings("unchecked")
		List<IInsuranceHistoryReport> resultList = jdbcTemplate.query(sql.toString(), 
				parameters, new RowMapper() {
			public IInsuranceHistoryReport mapRow(ResultSet rs, int rowNum) throws SQLException {
				IInsuranceHistoryReport item = new OBInsuranceHistoryReport();
				item.setIndex(rs.getLong("rownum"));
				item.setDueDate(rs.getDate("original_target_date"));
				item.setStatus(rs.getString("insurance_status"));
				item.setInsuranceCompanyName(rs.getString("COMPANY_NAME"));
				item.setInsurancePolicyNo(rs.getString("ins_policy_no"));
				item.setInsuredAmount(rs.getBigDecimal("insured_amount"));
				item.setExpiryDate(rs.getDate("expiry_date"));
				item.setReceivedDate(rs.getDate("received_date"));
				item.setOldInsuranceCompanyName(rs.getString("old_ins_company"));
				item.setOldInsurancePolicyNo(rs.getString("old_ins_policy_no"));
				item.setOldInsuredAmount(rs.getBigDecimal("old_insured_amount"));
				item.setOldExpiryDate(rs.getDate("old_expiry_date"));
				item.setOldReceivedDate(rs.getDate("old_received_date"));
				return item;
			}
		});
		return resultList;
	}



	@Override
	public List<LabelValue> getInsurancePolicyNo(Long collateralId) {
		if(collateralId==null)
			return Collections.emptyList();
		
		String sql = "SELECT ins_policy_no FROM cms_gc_insurance WHERE cms_collateral_id=? "+ 
				"AND ins_policy_no IS NOT NULL";
		return getJdbcTemplate().query(sql, new Object[] {collateralId}, new RowMapper<LabelValue>() {
			@Override
			public LabelValue mapRow(ResultSet rs, int rn) throws SQLException {
				LabelValue lv = new LabelValue();
				lv.setLabel(rs.getString("ins_policy_no"));
				lv.setValue(rs.getString("ins_policy_no"));
				return lv;
			}
		});
	}
	
	
}
