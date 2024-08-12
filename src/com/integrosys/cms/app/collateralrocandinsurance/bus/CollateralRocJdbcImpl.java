package com.integrosys.cms.app.collateralrocandinsurance.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.cms.app.common.bus.PaginationUtil;

public class CollateralRocJdbcImpl extends JdbcTemplateAdapter implements ICollateralRocJdbc {

	private static final String SELECT_COLLATERAL_ROC_TRX = "SELECT id,COLLATERAL_CATEGORY,COLLATERAL_ROC_CODE,COLLATERAL_ROC_DESCRIPTION,IRB_CATEGORY,INSURANCE_APPLICABLE,STATUS from CMS_COLLATERAL_ROC_MAP where deprecated='N' ORDER BY COLLATERAL_ROC_CODE asc  ";

	private int totalPageForPagination;

	private int recordsPerPageForPagination;

	private PaginationUtil paginationUtil;

	public int getTotalPageForPagination() {
		return totalPageForPagination;
	}

	public void setTotalPageForPagination(int totalPageForPagination) {
		this.totalPageForPagination = totalPageForPagination;
	}

	public int getRecordsPerPageForPagination() {
		return recordsPerPageForPagination;
	}

	public void setRecordsPerPageForPagination(int recordsPerPageForPagination) {
		this.recordsPerPageForPagination = recordsPerPageForPagination;
	}

	public PaginationUtil getPaginationUtil() {
		return paginationUtil;
	}

	public void setPaginationUtil(PaginationUtil paginationUtil) {
		this.paginationUtil = paginationUtil;
	}

	/**
	 * @return List of all authorized Collateral Roc
	 */
	public SearchResult getAllCollateralRoc() {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(SELECT_COLLATERAL_ROC_TRX, new CollateralRocRowMapper());

		} catch (Exception e) {
			throw new CollateralRocException("ERROR-- While retriving CollateralRoc");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public class CollateralRocRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBCollateralRoc result = new OBCollateralRoc();
			
			result.setCollateralCategory(rs.getString("COLLATERAL_CATEGORY"));
			result.setCollateralRocCode(rs.getString("COLLATERAL_ROC_CODE"));
			result.setCollateralRocDescription(rs.getString("COLLATERAL_ROC_DESCRIPTION"));
			result.setIrbCategory(rs.getString("IRB_CATEGORY"));
			result.setInsuranceApplicable(rs.getString("INSURANCE_APPLICABLE"));
			
			result.setStatus(rs.getString("STATUS"));
			
			result.setId(rs.getLong("id"));
			return result;
		}
	}
}
