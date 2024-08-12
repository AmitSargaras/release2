/*   Copyright Integro Technologies Pte Ltd
 *   MonCMDDealDAO.java
 *
 */
package com.integrosys.cms.app.eventmonitor.commoditydeallevel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.util.dataaccess.DAOContext;
import com.integrosys.cms.app.common.util.dataaccess.DataAccessException;
import com.integrosys.cms.app.common.util.dataaccess.DataAccessPreparedStatement;
import com.integrosys.cms.app.common.util.dataaccess.IDAODescriptor;
import com.integrosys.cms.app.common.util.dataaccess.SearchingParameters;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAO;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAOResult;
import com.integrosys.cms.app.eventmonitor.IRuleParam;

/**
 * @author heju
 * @version 1.0
 * @since Jun 10, 2004
 */

public class CMDDealDAO extends AbstractMonitorDAO implements IMonitorDAO {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.eventmonitor.IMonitorDAO#getInitialSet(com.integrosys
	 * .cms.app.eventmonitor.IRuleParam)
	 */
	public IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws EventMonitorException {
		ResultSet rs = null;
		DAOContext ctx = null;
		try {
			ctx = new DAOContext();
			// DefaultLogger.debug(this, "DAOContext initialized.");
			DefaultLogger
					.debug(this, "************************* ruleParam country code: " + ruleParam.getCountryCode());
			IDAODescriptor das = new CMDDealDAODescriptor();
			// DefaultLogger.debug(this, "CMDDealDAODescriptor initialized.");
			SearchingParameters criteria = new SearchingParameters(IDAODescriptor.QUERYTAG,
					CMDDealDAODescriptor.LEGAL_TAGPATH);
			criteria.put(IDAODescriptor.ALLOWED_COUNTRIES, ruleParam.getCountryCode());
			DataAccessPreparedStatement st = ctx.prepareStatement(das, criteria);
			DefaultLogger.debug(this, "SQL : " + st.toString());
			rs = st.executeQuery();
			List results = processResultSet(rs);
			return new CMDDealDAOResult(ctx, results);
		}
		catch (DataAccessException e) {
			// DefaultLogger.error(this, e );
			throw new EventMonitorException("Failed On DB Data Access", e.fillInStackTrace());
		}
		catch (SQLException e) {
			// e.printStackTrace();
			throw new EventMonitorException("Failed On SQL Connection", e.fillInStackTrace());

		}
		catch (DBConnectionException e) {
			// e.printStackTrace();
			throw new EventMonitorException("Failed On DB Connection", e.fillInStackTrace());
		}
		catch (Exception e) {
			throw new EventMonitorException("Exception from getInitialSet method ", e.fillInStackTrace());
		}

		finally {
			close(rs, null);
			// if ( ctx != null )ctx.close();
		}

	}

	/**
	 * Data access to All Deal Info per LEGALID. Called by @see
	 * CMDDealDAOResult.processResultSet()
	 */
	protected Collection getDealInfoCollection(SearchingParameters criteria_) throws DataAccessException

	{
		DAOContext ctx = null;
		ArrayList deals = new ArrayList();
		try {
			ctx = new DAOContext();
			IDAODescriptor das = new CMDDealDAODescriptor();
			DataAccessPreparedStatement st = ctx.prepareStatement(das, criteria_);
			ResultSet rs = st.executeQuery();
			try {
				if (rs != null) {
					while (rs.next()) {
						OBDealInfo obdeal = new OBDealInfo();
						obdeal.setDealRefNo(rs.getString("DEALREFNO"));
						obdeal.setProductType(rs.getString("PRODUCTTYPE"));
						obdeal.setSecured(rs.getString("SECURED"));
						obdeal.setEnforceable(rs.getString("ENFORCEABLE"));
						obdeal.setLastPrice(new Amount(rs.getDouble("LASTPRICE"), rs.getString("LASTCURRENCYCODE")));
						obdeal.setLastPriceString(rs.getString("SLASTPRICE"));
						obdeal.setLastPriceUnit(rs.getString("LASTCURRENCYCODE"));
						obdeal.setCurrentPrice(new Amount(rs.getDouble("CURRENTPRICE"), rs.getString("CURRENCYCODE")));
						obdeal.setCurrentPriceString(rs.getString("SCURRENTPRICE"));
						obdeal.setCurrentPriceUnit(rs.getString("CURRENCYCODE"));
						obdeal
								.setDealAmount(new Amount(rs.getDouble("DEALAMOUNT"), rs
										.getString("CURRENCY_DEALAMOUNT")));
						obdeal.setDealFSV(new Amount(rs.getDouble("DEALAFSV"), rs.getString("CURRENCY_DEALFSV")));
						obdeal.setShortFallValue(new Amount(rs.getDouble("SHORTFALLVALUE"), rs
								.getString("CURRENCY_DEALAMOUNT")));

						deals.add(obdeal);
					}
					rs.close();
				}
			}
			catch (SQLException e) {
				throw new DataAccessException("Failed on Deal ResultSet Exception", e.fillInStackTrace());
			}
			return deals;
		}
		finally {
			if (ctx != null) {
				ctx.close();
			}
		}

	}

	/**
	 * Process resultset to return a list of results.
	 * @param ResultSet rs
	 * @return List - list of results
	 * @throws Exception
	 */
	private List processResultSet(ResultSet rs) throws Exception {
		ArrayList results = new ArrayList();
		try {
			if (rs != null) {
				OBCommodityDealLevel obcmd = null;
				while (rs.next()) {
					obcmd = new OBCommodityDealLevel();
					// : Assemble OBCommodityDealLevel which contains OBDealInfo
					obcmd.setLeName(rs.getString("LEGAL_NAME"));
					obcmd.setLeID(rs.getString("LE_ID"));
					obcmd.setInstrRefNo(rs.getString("REFNO"));
					obcmd.setArm(rs.getString("ARMNAME"));
					obcmd.setLimitProfileID(rs.getString("LMTPROFILE_ID"));
					obcmd.setOriginatingCountry(rs.getString("CMS_ORIG_COUNTRY"));
					// : retrieve the OBDealInfo
					SearchingParameters criteria = new SearchingParameters(IDAODescriptor.QUERYTAG,
							CMDDealDAODescriptor.DEAL_TAGPATH);
					criteria.put("LEGALID", obcmd.getLeID());
					criteria.put(IDAODescriptor.LIMIT_PROFILE_ID, obcmd.getLimitProfileID());
					CMDDealDAO dao = new CMDDealDAO();
					Collection deals = dao.getDealInfoCollection(criteria);
					// DefaultLogger.debug(this,"Deal retrieved......"+
					// Integer.toString(deals.size() ) );
					// obcmd.setDealInfo( (OBDealInfo[] )deals.toArray());
					obcmd.setDealInfo(deals);
					// DefaultLogger.debug(this,"CMD Deal .....\n" + obcmd );

					results.add(obcmd);
				}
			}
		}
		catch (SQLException e) {
			// DefaultLogger.debug(this, e);
			throw new DataAccessException("Failed on DealLevel ResultSet Exception.", e.fillInStackTrace());
		}
		return results;
	}
}
