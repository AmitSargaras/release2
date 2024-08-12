/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stock/StockExchangeDAO.java,v 1.2 2003/09/25 09:30:35 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * implementation of {@link IStockExchangeJdbc} using Spring Framework Jdbc
 * Library
 * 
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/09/25
 */
public class StockExchangeJdbcImpl extends JdbcDaoSupport implements IStockExchangeJdbc {

	private static final String COL_COUNTRY_CODE = "COUNTRY_ISO_CODE";

	private static final String COL_STOCK_EXCHANGE_CODE = "STOCK_EXCHANGE";

	private static final String COL_STOCK_EXCHANGE_NAME = "STOCK_EXCHANGE_NAME";

	private static final String TBL_STOCK_EXCHANGE = "CMS_STOCK_EXCHANGE";

	/**
	 * Gets all the stock exchanges' info.
	 * @return An array of element where each element represent a stock
	 *         exchange's information. This array can have zero elements.
	 */
	public IStockExchange[] getAllStockExchanges() {

		String sql = "SELECT " + COL_STOCK_EXCHANGE_CODE + ", " + COL_STOCK_EXCHANGE_NAME + ", " + COL_COUNTRY_CODE
				+ " FROM " + TBL_STOCK_EXCHANGE;

		List resultList = getJdbcTemplate().query(sql, new StockExchangeRowMapper());

		return (IStockExchange[]) resultList.toArray(new IStockExchange[0]);
	}

	private class StockExchangeRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBStockExchange stockExchange = new OBStockExchange();
			stockExchange.setStockExchangeCode(rs.getString(COL_STOCK_EXCHANGE_CODE));
			stockExchange.setStockExchangeName(rs.getString(COL_STOCK_EXCHANGE_NAME));
			stockExchange.setCountryCode(rs.getString(COL_COUNTRY_CODE));

			return stockExchange;
		}

	}
}
