package com.integrosys.cms.app.goodsMaster.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.bus.PaginationUtil;

@SuppressWarnings("unchecked")
public class GoodsMasterJdbcImpl extends JdbcTemplateAdapter implements
IGoodsMasterJdbc{

	private static final String SELECT_GOODS_MASTER_TRX = "SELECT id,GOODS_CODE,GOODS_NAME,GOODS_PARENT_CODE,RESTRICTION_TYPE,STATUS from CMS_GOODS_MASTER where STATUS='ACTIVE' AND deprecated='N'";

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
	
	/**
	 * @return List of all authorized GoodsMaster
	 */
	public SearchResult getAllGoodsMaster() {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(SELECT_GOODS_MASTER_TRX,
					new GoodsMasterRowMapper());

		} catch (Exception e) {
			throw new GoodsMasterException("ERROR-- While retriving GoodsMaster");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	/**
	 * @return List of all GoodsMaster according to the query passed.
	 */

	public SearchResult getAllGoodsMaster(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = searchBy;
		String searchTextValue = searchText.toUpperCase();

		String GET_QUERY_STRING = SELECT_GOODS_MASTER_TRX
		+ "AND ( " + searchByValue + " LIKE '" + searchTextValue
		+ "%' )";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_QUERY_STRING,
						new GoodsMasterRowMapper());
			}

		} catch (Exception e) {
			throw new GoodsMasterException("ERROR-- While retriving GoodsMaster");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public class GoodsMasterRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBGoodsMaster result = new OBGoodsMaster();
			
			result.setGoodsCode(rs.getString("GOODS_CODE"));
			result.setGoodsName(rs.getString("GOODS_NAME"));
			result.setGoodsParentCode(rs.getString("GOODS_PARENT_CODE"));
			result.setStatus(rs.getString("STATUS"));
			result.setRestrictionType(rs.getString("RESTRICTION_TYPE"));
			
			result.setId(rs.getLong("id"));
			return result;
		}
	}
	
	//added by santosh
	/**
	 * @return List of all authorized GoodsMaster
	 */
	public SearchResult getAllFilteredGoodsMaster(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = "";
		if(null!=searchBy && searchBy.equals("goodsCode"))
			searchByValue = "GOODS_CODE";
		else 
			searchByValue = "GOODS_NAME";
		
		String searchTextValue = searchText.toUpperCase();
		
		String GET_GOODS_MASTER_QUERY_STRING = "SELECT id,GOODS_CODE,GOODS_NAME,GOODS_PARENT_CODE,RESTRICTION_TYPE,STATUS from CMS_GOODS_MASTER where deprecated='N'"
				+ " AND UPPER( " + searchByValue + ") LIKE '" + searchTextValue
				+ "%' ";
		//GET_GOODS_MASTER_QUERY_STRING+=" ORDER BY goodsCode asc ";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_GOODS_MASTER_QUERY_STRING,
						new GoodsMasterRowMapper());
			}
		} catch (Exception e) {
			throw new GoodsMasterException("ERROR-- While retriving GoodsMaster");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}       

	public List<String> getProhibitedGoodsCode() throws GoodsMasterException{
	
		String query = "select GOODS_CODE from cms_goods_master where RESTRICTION_TYPE = 'PROHIBITED' and STATUS = 'ACTIVE'";
		
		return (List<String>) getJdbcTemplate().query(query.toString(), new Object[]{}, new ResultSetExtractor() {
			public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> list = new ArrayList<String>();
				while(rs.next()) {
					list.add(rs.getString("GOODS_CODE").trim());
				}
				return list;
			}
		});
		
	}
	
	public List<String> getParentGoodsCodeList() throws GoodsMasterException{
		String query = "select GOODS_CODE from cms_goods_master where GOODS_PARENT_CODE is null and RESTRICTION_TYPE <> 'PROHIBITED' and STATUS = 'ACTIVE' ";
		
		return (List<String>) getJdbcTemplate().query(query.toString(), new Object[]{}, new ResultSetExtractor() {
			public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> list = new ArrayList<String>();
				while(rs.next()) {
					list.add(rs.getString("GOODS_CODE").trim());
				}
				return list;
			}
		});
	}
	
	
	public List<String> getNonProhibitedChildGoodsCodeListByParent(String parentGoodsCode) throws GoodsMasterException{
		
		if(StringUtils.isBlank(parentGoodsCode))
			return Collections.emptyList();
		
		String query = "select GOODS_CODE from cms_goods_master where GOODS_PARENT_CODE = ? and RESTRICTION_TYPE <> 'PROHIBITED' and STATUS = 'ACTIVE' ";
		
		return (List<String>) getJdbcTemplate().query(query.toString(), new Object[]{parentGoodsCode}, new ResultSetExtractor() {
			public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> list = new ArrayList<String>();
				while(rs.next()) {
					list.add(rs.getString("GOODS_CODE").trim());
				}
				return list;
			}
		});
	}
	
	public List<IGoodsMaster> getAllNonProhibitedGoodsMasterListByParent(String parentGoodsCode) throws GoodsMasterException {
		
		List resultList = Collections.emptyList();
		
		if(StringUtils.isBlank(parentGoodsCode))
			return resultList;
		
		String sql = "SELECT GOODS_CODE, GOODS_NAME, GOODS_PARENT_CODE,LEVEL FROM CMS_GOODS_MASTER WHERE RESTRICTION_TYPE <> 'PROHIBITED' and STATUS = 'ACTIVE' "
				+ " CONNECT BY PRIOR GOODS_CODE = GOODS_PARENT_CODE START WITH GOODS_PARENT_CODE IN (?) ";
		
		try {
			resultList =(List) getJdbcTemplate().query(sql, new Object[]{parentGoodsCode}, new ResultSetExtractor() {
				
				public List<IGoodsMaster> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<IGoodsMaster> list = new ArrayList<IGoodsMaster>();
					while(rs.next()) {
						IGoodsMaster goodsObj = new OBGoodsMaster();
						goodsObj.setGoodsCode(rs.getString("GOODS_CODE").trim());
						goodsObj.setGoodsParentCode(rs.getString("GOODS_PARENT_CODE").trim());
						list.add(goodsObj);
					}
					return list;
				}
			});
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in getAllGoodsMasterListByParent for parent code: "+parentGoodsCode, e);
		}

		return resultList;
	}
	
}
