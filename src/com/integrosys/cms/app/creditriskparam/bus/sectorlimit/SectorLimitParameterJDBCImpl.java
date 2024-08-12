package com.integrosys.cms.app.creditriskparam.bus.sectorlimit;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SectorLimitParameterJDBCImpl extends JdbcDaoSupport {

	private static final String SELECT_SUB_SECTOR_CODE =
    	"SELECT s.sector_code from cms_sub_sector_limit s join cms_eco_sector_limit e on " +
    	"s.sub_sector_limit_id = e.sub_sector_limit_id where " + "e.sector_code = ?";
	
	private static final String SELECT_MAIN_SECTOR_CODE =
    	"SELECT m.sector_code from cms_main_sector_limit m join cms_sub_sector_limit s on " +
    	"m.main_sector_limit_id = s.main_sector_limit_id where " + "s.sector_code = ?";
	
	private static final String SELECT_ECO_SECTOR_CODE_LIST =
    	"SELECT sector_code, loan_purpose_code_value from cms_eco_sector_limit where status <> '" + ICMSConstant.STATE_DELETED + "' order by loan_purpose_code_value";
	
	public String retrieveSubSectorCode (String loanPurposeCode) throws SectorLimitException {
        try {
            String subSectorCode = (String)  getJdbcTemplate().query(SELECT_SUB_SECTOR_CODE, new Object[] {loanPurposeCode},
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString(1);
    					}
                        return null;
					}
				});
            return subSectorCode;
        } catch (Exception e) {
	            DefaultLogger.error(this, "Error in retrieveSubSectorCode: " + e.getMessage());
	            throw new SectorLimitException("Error in retrieveSubSectorCode: " + e);
        }
	}
	
	public String retrieveMainSectorCode (String loanPurposeCode) throws SectorLimitException {
        try {
            String mainLoanPurposeCode = (String)  getJdbcTemplate().query(SELECT_MAIN_SECTOR_CODE, new Object[] {loanPurposeCode},
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString(1);
    					}
                        return null;
					}
				});
            return mainLoanPurposeCode;
        } catch (Exception e) {
	            DefaultLogger.error(this, "Error in retrieveMainSectorCode: " + e.getMessage());
	            throw new SectorLimitException("Error in retrieveMainSectorCode: " + e);
        }
	}
	
	public Map retrieveEcoSectorCodeMap () throws SectorLimitException {
		try {
            Map ecoSectorCodeMap = (Map)  getJdbcTemplate().query(SELECT_ECO_SECTOR_CODE_LIST, new Object[] {},
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                        Map resultMap = new HashMap();
                        while (rs.next()) {
                            resultMap.put(rs.getString(1), rs.getString(2));
                        }
                        return resultMap;
					}
				});
            return ecoSectorCodeMap;
        } catch (Exception e) {
	            DefaultLogger.error(this, "Error in retrieveEcoSectorCodeMap: " + e.getMessage());
	            throw new SectorLimitException("Error in retrieveEcoSectorCodeMap: " + e);
        }
	}
}
