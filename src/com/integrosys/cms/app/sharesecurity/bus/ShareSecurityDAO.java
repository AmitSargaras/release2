package com.integrosys.cms.app.sharesecurity.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.Validate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.ISequenceFormatter;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * Implementation of {@link ShareSecurityJdbcDao} using Spring Framework Jdbc
 * Routine.
 * 
 * @author Chong Jun Yong
 * @since 17.03.2007
 * 
 */
public class ShareSecurityDAO extends JdbcTemplateAdapter implements ShareSecurityJdbcDao {

	private ISequenceFormatter sequenceFormatter;

	public List getSharedSecNameForCollateral(Long collateralId) throws SearchDAOException {
		Validate.notNull(collateralId, "'collateralId' supplied must not be null");

		String query = "SELECT SS.SOURCE_SECURITY_ID, CE.ENTRY_NAME "
				+ "FROM CMS_SECURITY_SOURCE SS, COMMON_CODE_CATEGORY_ENTRY CE "
				+ "WHERE SS.CMS_COLLATERAL_ID = ? AND SS.STATUS <> 'D' " + "AND CE.CATEGORY_CODE = '"
				+ ICMSConstant.CATEGORY_SEC_SOURCE + "' AND " + "CE.ENTRY_CODE = SS.SOURCE_ID ";

		try {
			return getJdbcTemplate().query(query, new Object[] { collateralId }, new RowMapper() {

				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					String res = rs.getString("ENTRY_NAME");
					res = rs.getString("SOURCE_SECURITY_ID") + " - " + res;

					return res;
				}
			});
		}
		catch (DataAccessException ex) {
			throw new SearchDAOException("Failed to retrieve share security type name and id using colId ["
					+ collateralId + "]", ex);
		}
	}

	public Map getSharedSecNameForCollaterals(List collateralIdList) throws SearchDAOException {
		if (collateralIdList == null || collateralIdList.isEmpty()) {
			return Collections.EMPTY_MAP;
		}

		List argList = new ArrayList();

		StringBuffer queryBuf = new StringBuffer();
		queryBuf.append("SELECT SS.CMS_COLLATERAL_ID, SS.SOURCE_SECURITY_ID, CE.ENTRY_NAME ");
		queryBuf.append("FROM CMS_SECURITY_SOURCE SS, COMMON_CODE_CATEGORY_ENTRY CE WHERE SS.CMS_COLLATERAL_ID ");

		CommonUtil.buildSQLInList(collateralIdList, queryBuf, argList);

		queryBuf.append(" AND SS.STATUS <> 'D' AND CE.CATEGORY_CODE = '");
		queryBuf.append(ICMSConstant.CATEGORY_SEC_SOURCE);
		queryBuf.append("' AND CE.ENTRY_CODE = SS.SOURCE_ID ORDER BY SS.CMS_COLLATERAL_ID");

		try {
			return (Map) getJdbcTemplate().query(queryBuf.toString(), argList.toArray(), new ResultSetExtractor() {

				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map result = new HashMap();

					while (rs.next()) {
						String colId = rs.getString("CMS_COLLATERAL_ID");
						String sourceColId = rs.getString("SOURCE_SECURITY_ID");
						String entryName = rs.getString("ENTRY_NAME");
						if (result.containsKey(colId)) {
							List curSecIdList = (List) (result.get(colId));
							curSecIdList.add(sourceColId + " - " + entryName);
						}
						else {
							List curSecIdList = new ArrayList();
							curSecIdList.add(sourceColId + " - " + entryName);
							result.put(colId, curSecIdList);
						}
					}
					return result;
				}
			});
		}
		catch (DataAccessException ex) {
			throw new SearchDAOException(
					"Failed to retrieve share security id and type, providing list of collateral id ["
							+ ArrayUtils.toString(collateralIdList.toArray()) + "]", ex);
		}

	}

	public void createSharedSecurity(IShareSecurity shareSecurity) throws SearchDAOException {
		Validate.notNull(shareSecurity, "'shareSecurity' supplied must not be null");

		String query = "INSERT INTO CMS_SECURITY_SOURCE "
				+ "(CMS_SECURITY_SOURCE_ID, CMS_COLLATERAL_ID, SOURCE_SECURITY_ID, SOURCE_ID, STATUS, LAST_UPDATE_DATE) "
				+ "VALUES(?, ?, ?, ?, ?, ?)";
		// FOR DB2
//		String nextSequenceSql = "VALUES nextval FOR " + ICMSConstant.SEQUENCE_SHARE_SECURITY;
		
		//FOR Oracle
		String nextSequenceSql = "SELECT "+ICMSConstant.SEQUENCE_SHARE_SECURITY+".NEXTVAL FROM DUAL";

		long seq = getJdbcTemplate().queryForLong(nextSequenceSql);
		String seqString = "";
		try {
			seqString = getSequenceFormatter().formatSeq(String.valueOf(seq));
		}
		catch (Exception e) {
			throw new SearchDAOException("Failed to format sequence", e);
		}

		try {
			getJdbcTemplate().update(
					query,
					new Object[] { seqString, new Long(shareSecurity.getCmsCollateralId()),
							shareSecurity.getSourceSecurityId(), shareSecurity.getSourceId(),
							shareSecurity.getStatus(), shareSecurity.getLastUpdatedTime() });
		}
		catch (DataAccessException ex) {
			throw new SearchDAOException("Failed to insert share security instance for [" + shareSecurity + "]", ex);
		}

	}

	public ISequenceFormatter getSequenceFormatter() {
		return this.sequenceFormatter;
	}

	/**
	 * To set sequence formatter to be used to format the sequence for share
	 * security to be inserted.
	 * 
	 * @param sequenceFormatter sequence formatter, normally append date before
	 *        the sequence
	 */
	public void setSequenceFormatter(ISequenceFormatter sequenceFormatter) {
		this.sequenceFormatter = sequenceFormatter;
	}
}
