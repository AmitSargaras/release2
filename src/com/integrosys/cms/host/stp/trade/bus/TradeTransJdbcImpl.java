package com.integrosys.cms.host.stp.trade.bus;

import com.integrosys.base.techinfra.dbsupport.ISequenceFormatter;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.stp.bus.IStpTransJdbc;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.trade.common.ITradeConstants;
import com.integrosys.cms.ui.limit.CategoryCodeConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: Jerlin Ong Date: Sep 17, 2008 Time: 12:19:54
 * PM To change this template use File | Settings | File Templates.
 */
public class TradeTransJdbcImpl extends JdbcDaoSupport implements ITradeTransJdbc {
	//FOR DB2
	/*public static final String GET_UID_SEQ_SQL = "VALUES nextval FOR " + ITradeConstants.SEQUENCE_TRADE_TRANS_UID;

	public static final String GET_REF_SEQ_SQL = "VALUES nextval FOR " + ITradeConstants.SEQUENCE_TRADE_TRANS_REF;*/
	
	//FOR ORACLE
	public static final String GET_UID_SEQ_SQL = "SELECT " + ITradeConstants.SEQUENCE_TRADE_TRANS_UID+".NEXTVAL FROM DUAL";

	public static final String GET_REF_SEQ_SQL = "SELECT " + ITradeConstants.SEQUENCE_TRADE_TRANS_REF+".NEXTVAL FROM DUAL";

	private ISequenceFormatter sequenceFormatter;

	public ISequenceFormatter getSequenceFormatter() {
		return sequenceFormatter;
	}

	public void setSequenceFormatter(ISequenceFormatter sequenceFormatter) {
		this.sequenceFormatter = sequenceFormatter;
	}

	/**
	 * Method to return Trade transaction unique ID
	 *
	 * @return Trx UID
	 */
	public String getSeqNum() {
		long seq = getJdbcTemplate().queryForLong(GET_UID_SEQ_SQL);
		String seqString = "";

		try {
			seqString = getSequenceFormatter().formatSeq(String.valueOf(seq));
		}
		catch (Exception e) {
			if (getSequenceFormatter() == null) {
				IllegalStateException ise = new IllegalStateException(
						"sequence formatter required is null, please provide.");
				ise.initCause(e);
				throw ise;
			}

			logger.warn("encounter exception when doing formatting using formatter ["
					+ sequenceFormatter.getClass().getName() + "]", e);

			IllegalStateException ise = new IllegalStateException("not able to format using formatter ["
					+ sequenceFormatter.getClass().getName() + "]");
			ise.initCause(e);
			throw ise;
		}
		return seqString;
	}

	public String getTrxRefNum() {
		long seq = getJdbcTemplate().queryForLong(GET_REF_SEQ_SQL);
		String seqString = String.valueOf(seq);
		return seqString;
	}

	/**
	 * get Trade Biz Error Code From STP_COMMON_ERROR_CODE
	 */
	public Map getTradeBizErrorCode() {
        //TODO
/*		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf
				.append("SELECT ERROR_CODE, ERROR_DESCRIPTION FROM STP_ERROR_CODE_MAP WHERE STATUS = 'ACTIVE' AND ERROR_TYPE = 'BIZ'");

		return (Map) getJdbcTemplate().query(sqlBuf.toString(), new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map TradeBizErrorCodeMap = new HashMap();
				while (rs.next()) {
					String error_code = rs.getString("ERROR_CODE");
					String error_desc = rs.getString("ERROR_DESCRIPTION");
					if (StringUtils.isNotBlank(error_code)) {
						TradeBizErrorCodeMap.put(error_code, error_desc);
					}
				}
				return TradeBizErrorCodeMap;
			}
		});*/
        return null;

	}

	/**
	 * get Trade Error Message Description and transaction type From
	 * STP_TRANS_ERROR, STP_TRANS and STP_MASTER_TRANS
	 *
	 * @param transactionId String
	 */
	public List getErrorMessage(String transactionId) {
		// For Db2
		/*StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT ERR.ERROR_CODE, ERR.ERROR_DESC, ST.TRX_TYPE ");
		sqlBuf.append(" FROM STP_TRANS_ERROR ERR, STP_TRANS ST, STP_MASTER_TRANS MT ");
		sqlBuf.append(" WHERE ERR.TRX_UID = ST.TRX_UID ");
		sqlBuf.append(" AND MT.STATUS <> 'COMPLETE' ");
		sqlBuf.append(" AND (ST.RESPONSE_CODE <> 'AA' OR ST.RESPONSE_CODE IS NULL) ");
		sqlBuf
				.append(" AND ST.MASTER_TRX_ID = MT.MASTER_TRX_ID AND MT.TRANSACTION_ID = ? order by ERR.ERROR_ID DESC fetch first row only ");*/
		// For Oracle
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT * FROM ( SELECT ERR.ERROR_CODE, ERR.ERROR_DESC, ST.TRX_TYPE ");
		sqlBuf.append(" FROM STP_TRANS_ERROR ERR, STP_TRANS ST, STP_MASTER_TRANS MT ");
		sqlBuf.append(" WHERE ERR.TRX_UID = ST.TRX_UID ");
		sqlBuf.append(" AND MT.STATUS <> 'COMPLETE' ");
		sqlBuf.append(" AND (ST.RESPONSE_CODE <> 'AA' OR ST.RESPONSE_CODE IS NULL) ");
		sqlBuf.append(" AND ST.MASTER_TRX_ID = MT.MASTER_TRX_ID AND MT.TRANSACTION_ID = ? order by ERR.ERROR_ID DESC ");
		sqlBuf.append(" ) TEMP WHERE ROWNUM<=1");

		return getJdbcTemplate().query(sqlBuf.toString(), new Object[] { transactionId }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				List aList = new ArrayList();
				aList.add(rs.getString("TRX_TYPE"));
				aList.add(rs.getString("ERROR_CODE"));
				aList.add(rs.getString("ERROR_DESC"));
				return aList;
			}
		});
	}

	public String getCodeCategoryEntry(String categoryCode, String entryCode) {
		String selectSql = "SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = ? and ENTRY_CODE = ?";

		return (String) getJdbcTemplate().query(selectSql.toString(), new Object[] { categoryCode, entryCode },
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString("ENTRY_NAME");
						}
						return null;
					}
				});
	}
}