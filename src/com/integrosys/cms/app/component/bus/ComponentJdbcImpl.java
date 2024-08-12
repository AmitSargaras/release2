package com.integrosys.cms.app.component.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.common.bus.PaginationUtil;
import com.integrosys.cms.app.component.bus.IComponentJdbc;
import com.integrosys.cms.app.component.bus.ComponentException;

public class ComponentJdbcImpl extends JdbcDaoSupport implements IComponentJdbc{

	
	private static final String SELECT_COMPONENT_TRX = "SELECT id,status,component_Type,component_Name,has_Insurance,COMPONENT_CATEGORY from CMS_COMPONENT where deprecated='N'";
	
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


	public SearchResult getAllComponent(String searchBy, String searchText)
			throws ComponentException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SearchResult getAllComponent() throws ComponentException {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(SELECT_COMPONENT_TRX,
					new ComponentRowMapper());

		} catch (Exception e) {
			throw new ComponentException("ERROR-- While retriving Component");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	public class ComponentRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBComponent result = new OBComponent();
			result.setId(rs.getLong("id"));
			result.setStatus(rs.getString("status"));
			result.setComponentType(rs.getString("component_Type"));
			result.setComponentName(rs.getString("component_Name"));
			result.setHasInsurance(rs.getString("has_Insurance"));
			result.setComponentCategory(rs.getString("component_Category"));
//			result.setApplicableForDp(rs.getString("applicable_For_Dp"));
			return result;
		}
	}

	
	public List getAllComponentSearch(String login) throws ComponentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}


