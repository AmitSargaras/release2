package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargegold;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

public class SpecificChargeGoldDao implements ISpecificChargeGoldDao, ICMSTrxTableConstants{
	
	private DBUtil dbUtil = null;
	
	/**
	 * Get the amount of unit price by the gold grade
	 * @param goldGrade of String type
	 * @return Amount - the value of unit price 
	 * @throws SearchDAOException on errors
	 */
	public HashMap getUnitPriceByGoldGrade(String goldGrade) throws SearchDAOException{
		HashMap resultMap = new HashMap();
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("select PRICE,CURRENCY,GOLD_UOM_CODE_NUM ");
		strBuf.append("from cms_gold_feed ");
		strBuf.append("where GOLD_GRADE_CODE_NUM = ? ");
		
		String sql = strBuf.toString();
		DefaultLogger.debug(this, "sql is " + sql);
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, goldGrade);
			ResultSet rs = dbUtil.executeQuery();
			if(rs.next()){
			resultMap.put("price", rs.getString("PRICE"));
			resultMap.put("currency", rs.getString("CURRENCY"));
			resultMap.put("goldUomCodeNum", rs.getString("GOLD_UOM_CODE_NUM"));
			}
			rs.close();
			
			return resultMap;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getUnitPriceByGoldGrade", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getUnitPriceByGoldGrade", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getUnitPriceByGoldGrade", ex);
			}
		}
	}
	

}
