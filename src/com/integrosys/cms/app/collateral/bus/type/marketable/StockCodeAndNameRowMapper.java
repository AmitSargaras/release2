package com.integrosys.cms.app.collateral.bus.type.marketable;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * <p>
 * RowMapper to map the stock's name and code to a String array. First index is
 * stock name, second index is stock code.s
 * @author Chong Jun Yong
 * 
 */
public class StockCodeAndNameRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		String[] labelValue = new String[2];
		labelValue[0] = rs.getString("name");
		labelValue[1] = rs.getString("stock_code");
		return labelValue;

	}

}
