package com.integrosys.cms.app.segmentWiseEmail.bus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.cms.app.common.bus.PaginationUtil;
import com.integrosys.cms.app.customer.bus.CustomerException;

public class SegmentWiseEmailJdbcImpl extends JdbcTemplateAdapter implements
ISegmentWiseEmailJdbc{
	
	private static final String QUERY = "SELECT ID,SEGMENTS,EMAIL FROM CMS_DIARY_EMAIL_SEG_MAP WHERE STATUS='ACTIVE'";
	
	private DBUtil dbUtil;
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
	 * @return List of all authorized ProductMaster
	 */
	public SearchResult getAllSegmentWiseEmail() {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(QUERY,
					new SegmentWiseEmailRowMapper());

		} catch (Exception e) {
			throw new SegmentWiseEmailException("ERROR-- While retriving SegmentWiseEmail");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public class SegmentWiseEmailRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBSegmentWiseEmail result = new OBSegmentWiseEmail();
			result.setEmail(rs.getString("EMAIL"));
			result.setSegment(rs.getString("SEGMENTS"));
			result.setID(rs.getLong("ID"));
			return result;
		}
	}
	
	@Override
	public List getAllSegment() throws SegmentWiseEmailException {
		String segmentList = "SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS='1'";
		List queryForList = getJdbcTemplate().query(segmentList,new RowMapper() {
		public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
		String entryCode=rs.getString("ENTRY_NAME");
		return entryCode;
		}
		});
		return queryForList;
	}

	@Override
	public SearchResult getSegmentWiseEmail(String segment) throws SegmentWiseEmailException {
		
		String QUERY = "SELECT ID,SEGMENTS,EMAIL FROM CMS_DIARY_EMAIL_SEG_MAP WHERE STATUS='ACTIVE' AND SEGMENTS='"+segment+"'";
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(QUERY,
					new SegmentWiseEmailRowMapper());

		} catch (Exception e) {
			throw new SegmentWiseEmailException("ERROR-- While retriving SegmentWiseEmail");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public void insertDataToEmailTable(String refsId,List emails,String segments)throws SQLException{
		
		final String refId = refsId;
		final String segment = segments;
		String sql = " INSERT INTO STAGE_DIARY_EMAIL_SEG_MAP "
				+ " (ID,SEGMENTS,EMAIL,STATUS,REF_ID) "
				+ " VALUES(STAGE_DIARY_SEG_SEQ.nextval,?,?,?,?) ";
		final List<String> emailList = emails;
		final int batchSize = emails.size();
		
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					String email = emailList.get(i);
					ps.setString(1,segment);
					ps.setString(2,email);
					ps.setString(3,"ACTIVE");
					ps.setString(4,refId);
				}
				public int getBatchSize() {
					return emailList.size();
				}
			});
	}
	//Get staging email list
	public SearchResult getStageEmail(long id){
		List resultList = null;
		String sql="SELECT ID,SEGMENTS,EMAIL FROM STAGE_DIARY_EMAIL_SEG_MAP WHERE REF_ID='"+id+"' ";
		try {

			resultList = getJdbcTemplate().query(sql,
					new SegmentWiseEmailRowMapper());

		} catch (Exception e) {
			throw new SegmentWiseEmailException("ERROR-- While retriving SegmentWiseEmail");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public void insertDataToActualEmailTable(String refsId,List<OBSegmentWiseEmail> objList)throws SQLException{
		
		final String refId = refsId;
		String sql = " INSERT INTO CMS_DIARY_EMAIL_SEG_MAP "
				+ " (ID,SEGMENTS,EMAIL,STATUS,REF_ID) "
				+ " VALUES(CMS_DIARY_SEG_SEQ.nextval,?,?,?,?) ";
		
		final List<OBSegmentWiseEmail> objectList = objList;
		final int batchSize = objList.size();
		
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBSegmentWiseEmail obj = objectList.get(i);
					ps.setString(1,obj.getSegment());
					ps.setString(2,obj.getEmail());
					ps.setString(3,"ACTIVE");
					ps.setString(4,refId);
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
	}
	
	public void deleteStageEmailIDs(String refsId,List emails,String segments)throws SQLException{
		
		final String refId = refsId;
		final String segment = segments;
		String sql = " INSERT INTO STAGE_DIARY_EMAIL_SEG_MAP "
				+ " (ID,SEGMENTS,EMAIL,STATUS,REF_ID) "
				+ " VALUES(STAGE_DIARY_SEG_SEQ.nextval,?,?,?,?) ";
		final List<String> emailList = emails;
		final int batchSize = emails.size();
		
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					String email = emailList.get(i);
					ps.setString(1,segment);
					ps.setString(2,email);
					ps.setString(3,"INACTIVE");
					ps.setString(4,refId);
				}
				public int getBatchSize() {
					return emailList.size();
				}
			});
	}
	
	public void deleteActualEmailIDs(String refsId,List<OBSegmentWiseEmail> objList)throws SQLException{
		
		final String refId = refsId;
		String sql ="UPDATE CMS_DIARY_EMAIL_SEG_MAP SET STATUS=?,REF_ID=? " + 
				"WHERE SEGMENTS=? AND EMAIL=? ";
		
		final List<OBSegmentWiseEmail> objectList = objList;
		final int batchSize = objList.size();
		
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBSegmentWiseEmail obj = objectList.get(i);
					ps.setString(1,"INACTIVE");
					ps.setString(2,refId);
					ps.setString(3,obj.getSegment());
					ps.setString(4,obj.getEmail());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
	}
	
	public long getSegmentId(String segment) throws SearchDAOException{
		String selectSql = "select ID from CMS_DIARY_EMAIL_MAP where SEGMENTS='"+segment+"' ";//and STATUS='ACTIVE'";
		
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(selectSql);
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				return rs.getLong("ID");
			}
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getSegmentId", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getSegmentId", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getSegmentId", ex);
			}
		}
	}

}
