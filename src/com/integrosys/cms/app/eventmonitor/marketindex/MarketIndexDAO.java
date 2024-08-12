/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/marketindex/MarketIndexDAO.java,v 1.8 2005/05/27 05:35:53 wltan Exp $
 */

package com.integrosys.cms.app.eventmonitor.marketindex;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAOResult;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.MonitorDaoResultRetrievalFailureException;

public class MarketIndexDAO extends AbstractMonitorDAO implements IMonitorDAO {

	public IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws MonitorDaoResultRetrievalFailureException {

		StringBuffer strBuf = new StringBuffer();

		strBuf.append("SELECT DISTINCT N.FEED_GROUP_ID, N.ISIN_CODE, N.UNIT_PRICE NEW_PRICE, ");
		strBuf.append("  CMS_STAGE_PRICE_FEED.NAME AS EXCHANGE, N.LAST_UPDATED_DATE, ");
		strBuf.append("  O.UNIT_PRICE OLD_PRICE, O.LAST_UPDATED_DATE ");
		strBuf.append(" FROM VW_PRICE_FLUX N, ");
		strBuf.append("  VW_PRICE_FLUX O, ");
		strBuf.append("  CMS_STAGE_PRICE_FEED, ");
		strBuf.append("  CMS_STAGE_FEED_GROUP ");
		strBuf.append("WHERE N.ISIN_CODE = O.ISIN_CODE ");
		strBuf.append(" AND N.LAST_UPDATED_DATE > O.LAST_UPDATED_DATE ");
		strBuf.append(" AND N.ISIN_CODE = CMS_STAGE_PRICE_FEED.ISIN_CODE ");
		strBuf.append(" AND CMS_STAGE_PRICE_FEED.FEED_GROUP_ID = CMS_STAGE_FEED_GROUP.FEED_GROUP_ID ");
		strBuf.append(" AND CMS_STAGE_FEED_GROUP.GROUP_TYPE = 'STOCK_INDEX' ");
		strBuf.append("ORDER BY 1 ");

		List resultList = getJdbcTemplate().query(strBuf.toString(), new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBMarketIndexInfo marketIndexInfo = new OBMarketIndexInfo();
				marketIndexInfo.setStockExchange(rs.getString("exchange"));
				marketIndexInfo.setCurrentIndex(rs.getDouble("new_price"));
				marketIndexInfo.setOldIndex(rs.getDouble("old_price"));

				return marketIndexInfo;
			}
		});

		return new MarketIndexDAOResult(resultList);

	}
}
