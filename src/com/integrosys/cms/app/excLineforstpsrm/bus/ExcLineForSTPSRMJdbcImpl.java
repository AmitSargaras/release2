package com.integrosys.cms.app.excLineforstpsrm.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.bus.PaginationUtil;

public class ExcLineForSTPSRMJdbcImpl extends JdbcTemplateAdapter implements
IExcLineForSTPSRMJdbc{

	private static final String SELECT_TRX = "SELECT id,LINE_CODE,EXCLUDED,STATUS,DEPRECATED from CMS_EXC_LINE_FR_STP_SRM WHERE STATUS = 'ACTIVE' ";

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
	
	public SearchResult getAll() {
		List resultList = null;
		try {
			resultList = getJdbcTemplate().query(SELECT_TRX, new ExcLineForSTPSRMRowMapper());
		} catch (Exception e) {
			throw new ExcLineForSTPSRMException("ERROR-- While retriving Exclusion Line for STP for SRM");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public class ExcLineForSTPSRMRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBExcLineForSTPSRM result = new OBExcLineForSTPSRM();
			
			result.setLineCode(rs.getString("LINE_CODE"));
			result.setExcluded(rs.getString("EXCLUDED"));
			result.setId(rs.getLong("id"));
			result.setDeprecated(rs.getString("DEPRECATED"));
			
			return result;
		}
	}

	@Override
	public boolean isExcluded(String lineCode, boolean isCheckedEnabled) {
		StringBuffer sql = new StringBuffer("select count(*) from CMS_EXC_LINE_FR_STP_SRM ")
							.append("where status = 'ACTIVE' and deprecated = 'N' and line_code = ?");

		if(isCheckedEnabled) {
			sql.append(" and excluded = 'on'");
		}
		System.out.println("ExcLineForSTPSRMJdbcImpl.java=>boolean isExcluded(String lineCode, boolean isCheckedEnabled)...lineCode=>"+lineCode+"***sql=>"+sql);
		try {
			return getJdbcTemplate().queryForInt(sql.toString(), new Object[] {lineCode}) > 0;
		}catch(Exception ex) {
			System.out.println("Exception occured while searching detail "
					+ "from Exclusion Line for STP for SRM for + ["
					+ lineCode + "]==>ex=>"+ex);
			ex.printStackTrace();
			DefaultLogger.error(this, "Exception occured while searching detail "
					+ "from Exclusion Line for STP for SRM for + ["
					+ lineCode + "]" + ex.getMessage(), ex);
			
		}
		return false;
	}

}