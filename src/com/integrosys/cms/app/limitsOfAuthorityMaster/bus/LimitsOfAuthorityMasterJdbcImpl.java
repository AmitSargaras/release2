package com.integrosys.cms.app.limitsOfAuthorityMaster.bus;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.bus.PaginationUtil;
import com.integrosys.cms.app.limitsOfAuthorityMaster.trxlog.bus.ILimitsOfAuthorityMasterTrxLog;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.limitsOfAuthorityMaster.ILimitsOfAuthorityMasterConstant;

public class LimitsOfAuthorityMasterJdbcImpl extends JdbcTemplateAdapter implements
ILimitsOfAuthorityMasterJdbc{

	private static final String SELECT_TRX = "SELECT id,EMPLOYEE_GRADE,RANKING_OF_SEQUENCE,SEGMENT,LIMIT_RELEASE_AMT,TOTAL_SANCTIONED_LIMIT,PROPERTY_VALUATION_AMT,"
			+ "FD_AMOUNT,DRAWING_POWER,SBLC_SECURITY_OMV,FACILITY_CAM_COVENANT,STATUS,DEPRECATED from Cms_Loa_Master WHERE STATUS = 'ACTIVE' ORDER BY RANKING_OF_SEQUENCE ";

	private static final String RANKING_SEQ = "SELECT MAX(RANKING_OF_SEQUENCE) FROM CMS_LOA_MASTER WHERE DEPRECATED='N' AND STATUS='ACTIVE' FETCH FIRST ROW ONLY";
	
	private int totalPageForPagination;

	private int recordsPerPageForPagination;

	private PaginationUtil paginationUtil;

	public void setTotalPageForPagination(int totalPageForPagination) {
		this.totalPageForPagination = totalPageForPagination;
	}

	public void setRecordsPerPageForPagination(int recordsPerPageForPagination) {
		this.recordsPerPageForPagination = recordsPerPageForPagination;
	}

	public void setPaginationUtil(PaginationUtil paginationUtil) {
		this.paginationUtil = paginationUtil;
	}
	
	public SearchResult getAllLimitsOfAuthority() {
		List resultList = null;
		try {
			resultList = getJdbcTemplate().query(SELECT_TRX, new LimitsOfAuthorityMasterRowMapper());
		} catch (Exception e) {
			DefaultLogger.error(this, "Exeption caught in  getAllLimitsOfAuthority :"+e.getMessage() , e);
			throw new LimitsOfAuthorityMasterException("ERROR-- While retriving Limits Of Authority Master");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public class LimitsOfAuthorityMasterRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBLimitsOfAuthorityMaster result = new OBLimitsOfAuthorityMaster();
			
			result.setEmployeeGrade(rs.getString("EMPLOYEE_GRADE"));
			result.setRankingOfSequence(rs.getInt("RANKING_OF_SEQUENCE"));
			result.setSegment(rs.getString("SEGMENT"));
			result.setLimitReleaseAmt(rs.getBigDecimal("LIMIT_RELEASE_AMT"));
			result.setTotalSanctionedLimit(rs.getBigDecimal("TOTAL_SANCTIONED_LIMIT"));
			result.setPropertyValuation(rs.getBigDecimal("PROPERTY_VALUATION_AMT"));
			result.setFdAmount(rs.getBigDecimal("FD_AMOUNT"));
			result.setDrawingPower(rs.getBigDecimal("DRAWING_POWER"));
			result.setSblcSecurityOmv(rs.getBigDecimal("SBLC_SECURITY_OMV"));
			result.setFacilityCamCovenant(rs.getString("FACILITY_CAM_COVENANT"));
			
			result.setId(rs.getLong("id"));
			
			return result;
		}
	}

	public ILimitsOfAuthorityMaster getLimitsOfAuthorityMasterByEmployeeGradeAndSegment(String employeeGrade, String segment) {
		
		if(StringUtils.isBlank(employeeGrade) || StringUtils.isBlank(segment)) {
			return null;
		}
		
		ILimitsOfAuthorityMaster loaMaster = null;
		
		DefaultLogger.info(this, "In getLimitsOfAuthorityMasterByEmployeeGradeAndSegment with Employee grade : "+employeeGrade +" and segment : "+segment);
		System.out.println("In getLimitsOfAuthorityMasterByEmployeeGradeAndSegment with Employee grade : "+employeeGrade +" and segment : "+segment);
		
		String sql = "SELECT id,EMPLOYEE_GRADE,SEGMENT, RANKING_OF_SEQUENCE, LIMIT_RELEASE_AMT, TOTAL_SANCTIONED_LIMIT, "
				+ "PROPERTY_VALUATION_AMT, FD_AMOUNT, DRAWING_POWER, SBLC_SECURITY_OMV, FACILITY_CAM_COVENANT,last_update_date,status "
				+ "from CMS_LOA_MASTER where status= 'ACTIVE' and EMPLOYEE_GRADE = ? and (SEGMENT = 'ALL' OR SEGMENT = ?) ";
		try {
			loaMaster = (ILimitsOfAuthorityMaster) getJdbcTemplate().queryForObject(sql, new Object[]{employeeGrade,segment}, new LimitsOfAuthorityMasterRowMapper());	
		}
		catch (DataAccessException e) {
			System.out.println("Found no data in getLimitsOfAuthorityMasterByEmployeeGradeAndSegment with Employee grade : "+employeeGrade +" and segment : "+segment);
			DefaultLogger.error(this, "Found no data in getLimitsOfAuthorityMasterByEmployeeGradeAndSegment for Employee grade:"+employeeGrade+" segment: "+segment , e);
		}
		catch (Exception e) {
			System.out.println("Exception caught in getLimitsOfAuthorityMasterByEmployeeGradeAndSegment with Employee grade : "+employeeGrade +" | segment : "+segment);
			DefaultLogger.error(this, "Exception caught in getLimitsOfAuthorityMasterByEmployeeGradeAndSegment for Employee grade:"+employeeGrade+" | segment: "+segment , e);
		}
		
		return loaMaster;
	}

	public String getMinimumEmployeeGrade(Map loaMap) {
		BigDecimal fdAmt = (BigDecimal) loaMap.get(ILimitsOfAuthorityMasterConstant.LOA_MASTER_FIELD_FD_AMT);
		BigDecimal securityOmv = (BigDecimal) loaMap.get(ILimitsOfAuthorityMasterConstant.LOA_MASTER_FIELD_SBLC_SECURITY_OMV);
		BigDecimal propertyValuationAmt = (BigDecimal) loaMap.get(ILimitsOfAuthorityMasterConstant.LOA_MASTER_FIELD_PROPERTY_AMT);
		BigDecimal calculatedDPGeneralCharge = (BigDecimal) loaMap.get(ILimitsOfAuthorityMasterConstant.LOA_MASTER_FIELD_DRAWING_POWER);
		BigDecimal maxDeltaReleaseAmt = (BigDecimal) loaMap.get(ILimitsOfAuthorityMasterConstant.LOA_MASTER_FIELD_LIMIT_RELEASE_AMT);
		BigDecimal maxTotalSanctionedAmt = (BigDecimal) loaMap.get(ILimitsOfAuthorityMasterConstant.LOA_MASTER_FIELD_TOTAL_SANCTIONED_LMT);
		String customerSegment = (String) loaMap.get(ILimitsOfAuthorityMasterConstant.LOA_MASTER_CUSTOMER_SEGMENT);
		
		DefaultLogger.info(this, "In getMinimumEmployeeGrade : customerSegment : "+customerSegment+" | fdAmt :"+fdAmt+ " | securityOmv :"+"maxTotalSanctionedAmt: "+maxTotalSanctionedAmt+
				"| propertyValuationAmt: "+propertyValuationAmt+ " | calculatedDPGeneralCharge: "+calculatedDPGeneralCharge+ "maxDeltaReleaseAmt :"+maxDeltaReleaseAmt);
		
		String filterColumnName = "";
		BigDecimal filterColumnVal = null;
		if(fdAmt != null) {
			filterColumnName = "Fd_Amount";
			filterColumnVal = fdAmt;
		}
		else if(securityOmv != null) {
			filterColumnName = "SBLC_SECURITY_OMV";
			filterColumnVal = securityOmv;
		}
		else if(propertyValuationAmt != null) {
			filterColumnName = "PROPERTY_VALUATION_AMT";
			filterColumnVal = propertyValuationAmt;
		}
		else if(calculatedDPGeneralCharge != null) {
			filterColumnName = "DRAWING_POWER";
			filterColumnVal = calculatedDPGeneralCharge;
		}
		else if(maxDeltaReleaseAmt != null) {
			filterColumnName = "LIMIT_RELEASE_AMT";
			filterColumnVal = maxDeltaReleaseAmt;
		}
		else if(maxTotalSanctionedAmt != null) {
			filterColumnName = "TOTAL_SANCTIONED_LIMIT";
			filterColumnVal = maxTotalSanctionedAmt;
		}
		
		if(filterColumnVal == null)
			return null;
		
		StringBuffer query = new StringBuffer()
				.append("Select EMPLOYEE_GRADE From ")
				.append("(Select EMPLOYEE_GRADE From Cms_Loa_Master Where STATUS = 'ACTIVE' and (SEGMENT = 'ALL' OR SEGMENT = ?) AND ")
				.append(filterColumnName)
				.append(">= ? Order By Ranking_Of_Sequence, ")
				.append(filterColumnName)
				.append(") where rownum = 1");
		
		
		try {
			return (String) getJdbcTemplate().queryForObject(query.toString(), new Object[]{customerSegment, filterColumnVal}, String.class);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in getMinimumEmployeeGrade :: "+e.getMessage(), e);
		}
		
		return null;
	}

	public Long getStagingReferenceByActualLoaMaster(Long masterId) {
		try {
			StringBuffer sb = new StringBuffer()
			.append("Select ID From Cms_Stage_Loa_Master Where Id = ")
			.append(" (Select Staging_Reference_Id From Transaction Where Reference_Id = ? And TRANSACTION_TYPE = 'LIMITS_OF_AUTHORITY' )");
			return getJdbcTemplate().queryForLong(sb.toString(), new Object[]{masterId});
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in getStagingReferenceByActualLoaMaster :: "+e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getRankingSeqByActualLoaMaster() {
		try {
			return getJdbcTemplate().queryForInt(RANKING_SEQ, new Object[]{});
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in getRankingSeqByActualLoaMaster :: "+e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void insertIntoLoaMasterLog(ILimitsOfAuthorityMasterTrxLog loaTrx) {
;		
		try {
			StringBuffer sb = new StringBuffer()
					.append("INSERT INTO CMS_LOA_MASTER_TRX_LOG  ")
					.append("(ID,USER_ID,CMS_STAGE_LOA_MASTER_ID,TRX_HIST_ID,EXCEPTIONAL_USER, ")
					.append("TRX_LIMIT_RELEASE_AMT, TRX_TOTAL_SANCTIONED_LIMIT, TRX_PROPERTY_VALUATION_AMT,TRX_FD_AMOUNT, ")
					.append("TRX_DRAWING_POWER, TRX_SBLC_SECURITY_OMV, TRX_FACILITY_CAM_COVENANT, MAKER_ID, CHECKER_ID, ")
					.append("MAKER_DATETIME, CHECKER_DATETIME )")
					.append("values ")
					.append("(CMS_LOA_MASTER_TRX_LOG_SEQ.nextval, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			getJdbcTemplate().update(sb.toString(), new Object[] { loaTrx.getUserId(),
					loaTrx.getStagingLoaMasterReferenceId(), loaTrx.getTrxHistoryId(), loaTrx.getIsExceptionalUser(),
					loaTrx.getTrxLimitReleaseAmt(), loaTrx.getTrxTotalSanctionedLimit(), loaTrx.getTrxPropertyValuation(),
					loaTrx.getTrxFdAmount(), loaTrx.getTrxDrawingPower(), loaTrx.getTrxSblcSecurityOmv(), loaTrx.getTrxFacilityCamCovenant(),
					loaTrx.getMakerId(), loaTrx.getCheckerId(), loaTrx.getMakerDateTime(), loaTrx.getCheckerDateTime()
			});
			
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in insertIntoLoaMasterLog :: "+e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public List<ILimitsOfAuthorityMaster> getValidLimitsOfAuthorityMasterByCustomerSegmentAndTotalSanctionedLimit(
			String segment, BigDecimal amount) {
		
		DefaultLogger.debug(this, "In getValidLimitsOfAuthorityMasterByCustomerSegmentAndTotalSanctionedLimit with segment : "+segment+ " | amount : "+amount);
		
		if(StringUtils.isNotBlank(segment) && amount != null) {
			
			StringBuffer sb = new StringBuffer()
					.append("SELECT id,EMPLOYEE_GRADE,RANKING_OF_SEQUENCE,SEGMENT,LIMIT_RELEASE_AMT,TOTAL_SANCTIONED_LIMIT,PROPERTY_VALUATION_AMT,")
					.append("FD_AMOUNT,DRAWING_POWER,SBLC_SECURITY_OMV,FACILITY_CAM_COVENANT,STATUS,DEPRECATED ")
					.append("from Cms_Loa_Master WHERE STATUS = 'ACTIVE' and ")
					.append("(SEGMENT = 'ALL' OR SEGMENT = ?) and Total_Sanctioned_Limit >= ? ")
					.append("Order By Ranking_Of_Sequence ");
			try {
				List<ILimitsOfAuthorityMaster> masters = getJdbcTemplate().query(sb.toString(), new Object[]{segment, amount}, new LimitsOfAuthorityMasterRowMapper());
				return masters;
			}
			catch (Exception e) {
				DefaultLogger.error(this, "Exception caught in getValidLimitsOfAuthorityMasterByCustomerSegmentAndTotalSanctionedLimit "+e.getMessage(), e);
				e.printStackTrace();
			}
			
		}
		return Collections.emptyList();
	}

	public boolean isLimitsOfAuthorityMasterExistsByEmployeeGrade(String employeeGrade) {
		
		if(StringUtils.isBlank(employeeGrade))
			return false;
		
		int count = 0;
		
		String sql = "SELECT count(1) as count FROM CMS_LOA_MASTER WHERE STATUS = 'ACTIVE' AND EMPLOYEE_GRADE = ?" ;
		
		try {
			count =  getJdbcTemplate().queryForInt(sql, new Object[]{employeeGrade});
		}
		catch (Exception e) {
			System.out.println("Exception caught in isLimitsOfAuthorityMasterExistsByEmployeeGrade with Employee grade : "+employeeGrade);
			DefaultLogger.error(this, "Exception caught in isLimitsOfAuthorityMasterExistsByEmployeeGrade for Employee grade:"+employeeGrade);
		}
		
		
		return count>0;
	}
	
}