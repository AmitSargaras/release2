/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/sublimittype/SubLimitTypeTrxDAO.java,v 1.1 2005/10/06 05:08:56 hmbao Exp $
 */
package com.integrosys.cms.app.creditriskparam.bus.internallimit;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-21
 * @Tag com.integrosys.cms.app.commodity.main.bus.sublimittype.SubLimitTypeTrxDAO.java
 */
public class InternalLimitParameterDAO {

	public ICMSTrxValue retrieveLastestILPTrx(ICMSTrxValue trxValue) throws SearchDAOException{
		DBUtil dbUtil=null;
		try {
			dbUtil = new DBUtil();
			String sql=" SELECT * FROM TRANSACTION WHERE TRANSACTION_TYPE = '" + ICMSConstant.INSTANCE_INTERNAL_LIMIT + "' AND STATUS<>'" + ICMSConstant.STATE_DELETED + "' ";
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if (rs.next()) {
				trxValue.setReferenceID(rs.getString(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID));
				trxValue.setStagingReferenceID(rs.getString(ICMSTrxTableConstants.TRXTBL_STAGING_REFERENCE_ID));				
				trxValue.setTransactionID(rs.getString(ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID));			
				trxValue.setStatus(rs.getString(ICMSTrxTableConstants.TRXTBL_STATUS));
			}
			return trxValue;
		} catch (Exception e) {
			throw new SearchDAOException(
					"Error in getting commodity profile trx value ", e);
		} finally {
			if(dbUtil!=null){
				try {
					dbUtil.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	/**
	     * Gets the banking group capital fund amount set in Internal Limit Module.
	     *	  
	     * @return the capital fund of Amount
	     * @throws SearchDAOException on errors encountered
	     */
	public Amount retrieveBankGroupCapitalFund() throws SearchDAOException{
		DBUtil dbUtil=null;
		try {
			dbUtil = new DBUtil();
			String sql=" SELECT CAP_FUND_AMT_CUR_CODE, CAP_FUND_AMT FROM CMS_INTERNAL_LIMIT_PARAMETER " +
								"WHERE DESCRIPTION_CODE = '" + ICMSConstant.BANKING_GROUP_CODE + "' AND STATUS<>'" + ICMSConstant.STATE_DELETED + "' ";
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if (rs.next()) {
			
				String ccy = rs.getString("CAP_FUND_AMT_CUR_CODE");
				double amt = rs.getDouble("CAP_FUND_AMT");

		        if( ccy != null && !ccy.trim().equals("") ) {

		        	return new Amount( amt, new CurrencyCode( ccy ) );
		        }
		       
			}
			return null;
		} catch (Exception e) {
			throw new SearchDAOException(
					"Error in getting bank group capital fund amount", e);
		} finally {
			if(dbUtil!=null){
				try {
					dbUtil.close();
				} catch (SQLException e) {
					throw new SearchDAOException(
					"Error in closing DB connection", e);
				}
			}
		}
	}
}
