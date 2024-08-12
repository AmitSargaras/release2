package com.integrosys.cms.app.bankingArrangementFacExclusion.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.bus.PaginationUtil;
import com.integrosys.cms.ui.common.UIUtil;

public class BankingArrangementFacExclusionJdbcImpl extends JdbcTemplateAdapter implements
IBankingArrangementFacExclusionJdbc{

	private static final String SELECT_TRX = "SELECT id,SYSTEM,FAC_CATEGORY,FAC_NAME,EXCLUDED,STATUS,DEPRECATED from CMS_BANKING_ARR_FAC_EXC WHERE STATUS = 'ACTIVE' ";

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
			resultList = getJdbcTemplate().query(SELECT_TRX, new BankingArrangementFacExclusionRowMapper());
		} catch (Exception e) {
			throw new BankingArrangementFacExclusionException("ERROR-- While retriving Banking Arrangement Fac Exclusion");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public class BankingArrangementFacExclusionRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBBankingArrangementFacExclusion result = new OBBankingArrangementFacExclusion();
			
			result.setSystem(rs.getString("SYSTEM"));
			result.setFacCategory(rs.getString("FAC_CATEGORY"));
			result.setFacName(rs.getString("FAC_NAME"));
			result.setExcluded(rs.getString("EXCLUDED"));
			result.setId(rs.getLong("id"));
			result.setDeprecated(rs.getString("DEPRECATED"));
			
			return result;
		}
	}

	@Override
	public boolean isExcluded(String system, String facCat, String facName, boolean isCheckedEnabled) {
		StringBuffer sql = new StringBuffer("select count(*) from CMS_BANKING_ARR_FAC_EXC ")
							.append("where status = 'ACTIVE' and deprecated = 'N' ");
		
		if(isCheckedEnabled) {
			sql.append(" and excluded ='Yes' ");
		}
		
		sql.append("and ( (system = ? and fac_category is null and fac_name is null) or ")
			.append("(system = ? and fac_category = ? and fac_name is null) or ")
			.append("(system = ? and fac_category = ? and fac_name = ?) )");
		try {
			return getJdbcTemplate().queryForInt(sql.toString(),
					new Object[] {system, system, facCat, system, facCat, facName}) > 0;
		}catch(Exception ex) {
			ex.printStackTrace();
			DefaultLogger.error(this, "Exception occured while searching detail "
					+ "from Banking Arrangement Facility Exclusion for + ["
					+ system + ", " + facCat + ", " + facName + "]" + ex.getMessage(), ex);
			
		}
		return false;
	}

}