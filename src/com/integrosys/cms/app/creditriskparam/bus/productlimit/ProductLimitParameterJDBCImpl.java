package com.integrosys.cms.app.creditriskparam.bus.productlimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProductLimitParameterJDBCImpl extends JdbcDaoSupport {

	private static final String SELECT_PROD_PROG_REF_CODE =
    	"SELECT p.reference_code from cms_product_program p join cms_product_type t on " +
    	"p.product_program_id = t.product_program_id where " + "t.reference_code = ?";
	
	private static final String SELECT_PROD_TYPE_CODE_LIST =
    	"SELECT reference_code, product_type_desc from cms_product_type where status <> 'DELETED' order by product_type_desc";
	
	public String retrieveProductProgRefCode (String productTypeRefCode) throws ProductLimitException {
		try {
            String productProgRefCode = (String) getJdbcTemplate().query(SELECT_PROD_PROG_REF_CODE, new Object[] {productTypeRefCode} ,
                new ResultSetExtractor() {
                    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                        if (rs.next()) {
                            return rs.getString(1);
                        }
                        return null;
                    }
                });

            return productProgRefCode;

        } catch (Exception e) {
            DefaultLogger.error(this, "Error in retrieveProductProgRefCode: " + e.getMessage());
            throw new ProductLimitException("Error in retrieveProductProgRefCode: " + e);
        }
	}

    public Map retrieveProductTypeCodeMap () throws ProductLimitException {
		try {
            Map productTypeCodeMap = (Map)  getJdbcTemplate().query(SELECT_PROD_TYPE_CODE_LIST, new Object[] {},
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                        Map resultMap = new HashMap();
                        while (rs.next()) {
                            resultMap.put(rs.getString(1), rs.getString(2));
                        }
                        return resultMap;
					}
				});
            return productTypeCodeMap;
        } catch (Exception e) {
	            DefaultLogger.error(this, "Error in retrieveProductTypeCodeMap: " + e.getMessage());
	            throw new ProductLimitException("Error in retrieveProductTypeCodeMap: " + e);
        }
	}
}
