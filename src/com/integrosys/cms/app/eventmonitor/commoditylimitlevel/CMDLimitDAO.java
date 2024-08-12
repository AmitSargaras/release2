/*   Copyright Integro Technologies Pte Ltd
 *   CMDLimitDAO.java created on  3:06:55 PM Jun 10, 2004
 *
 */
package com.integrosys.cms.app.eventmonitor.commoditylimitlevel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.util.dataaccess.DAO;
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
public class CMDLimitDAO extends AbstractMonitorDAO implements IMonitorDAO {
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
			IDAODescriptor das = new CMDLimitDAODescriptor();
			SearchingParameters criteria = new SearchingParameters(IDAODescriptor.QUERYTAG,
					CMDLimitDAODescriptor.LEGAL_TAGPATH);
			criteria.put(IDAODescriptor.ALLOWED_COUNTRIES, ruleParam.getCountryCode());
			DataAccessPreparedStatement st = ctx.prepareStatement(das, criteria);
			rs = st.executeQuery();
			List results = processResultSet(rs);
			return new CMDLimitDAOResult(ctx, results);
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
	protected Collection getLimitInfoCollection(SearchingParameters criteria_) throws DataAccessException {
		DAOContext ctx = null;
		ArrayList limits = new ArrayList();
		OBLimitInfo oblimit = null;
		try {
			ctx = new DAOContext(DAO.getConnection());
			IDAODescriptor das = new CMDLimitDAODescriptor();
			DataAccessPreparedStatement st = ctx.prepareStatement(das, criteria_);
			ResultSet rs = st.executeQuery();
			String prevLimitId = "DUMMY", currLimitId;
			ArrayList secs = null;
			OBSecInfo obsec = null;
			double sumSV = 0;
			if (rs != null)
			// IF.1
			{
				while (rs.next())
				// WHILE1.1
				{
					currLimitId = rs.getString("LIMITID");
					if (!currLimitId.equalsIgnoreCase(prevLimitId))
					// IF1.1.1
					{// Occurrence of New Limit, Group the Security Info
						// together
						if (oblimit != null)
						// IF1.1.1.1
						{
							oblimit
									.setShortFallValue(new Amount(sumSV, oblimit.getOperationalLimit()
											.getCurrencyCode()));
							sumSV = 0;
							// Grab the new limit
							oblimit = new OBLimitInfo();
							oblimit.setLimitId(currLimitId);
							oblimit.setLimitFacilityType(rs.getString("LIMITFACILITYTYPE"));
							oblimit.setReqCoverage(rs.getString("REQCOVERAGE"));
							oblimit.setActCoverage(rs.getString("ACTUALCOVERAGE"));
							// oblimit.setShortFallValue( new Amount(
							// rs.getDouble("SHORTFALLVALUE"),
							// rs.getString("CURRENCYCODE") ) );
							oblimit.setApprovedLimit(new Amount(rs.getDouble("APPROVEDLIMIT"), rs
									.getString("CURRENCYCODE")));
							oblimit.setActivatedLimit(new Amount(rs.getDouble("ACTIVATEDLIMIT"), rs
									.getString("CURRENCYCODE")));
							oblimit.setOperationalLimit(new Amount(rs.getDouble("OPERATIONALLIMIT"), rs
									.getString("CURRENCYCODE")));
							// Save each limit to the result
							limits.add(oblimit);

							// Preparint the new Secruity Group for within the
							// new Limit
							secs = new ArrayList();
							// Set the Secruty List
							oblimit.setSecInfo(secs);
						}
						else {// This is for the 1st row of Limit occuring once
								// only .

							// Grab the 1st limit
							oblimit = new OBLimitInfo();
							oblimit.setLimitId(currLimitId);
							oblimit.setLimitFacilityType(rs.getString("LIMITFACILITYTYPE"));
							oblimit.setReqCoverage(rs.getString("REQCOVERAGE"));
							oblimit.setActCoverage(rs.getString("ACTUALCOVERAGE"));
							// oblimit.setShortFallValue( new Amount(
							// rs.getDouble("SHORTFALLVALUE"),
							// rs.getString("CURRENCYCODE") ) );
							oblimit.setApprovedLimit(new Amount(rs.getDouble("APPROVEDLIMIT"), rs
									.getString("CURRENCYCODE")));
							oblimit.setActivatedLimit(new Amount(rs.getDouble("ACTIVATEDLIMIT"), rs
									.getString("CURRENCYCODE")));
							oblimit.setOperationalLimit(new Amount(rs.getDouble("OPERATIONALLIMIT"), rs
									.getString("CURRENCYCODE")));
							// Save each limit to the result
							limits.add(oblimit);

							// Preparing the 1st Secruity Group for the 1st
							// Limit
							secs = new ArrayList();
							// Set the security list
							oblimit.setSecInfo(secs);
						}// END IF1.1.1.1
					}// END IF1.1.1

					// Grab the secruity information all the time
					obsec = new OBSecInfo();
					obsec.setSecId(rs.getString("SECURITYID"));
					obsec.setSecType(rs.getString("SECURITYTYPE"));
					obsec.setSecsubType(rs.getString("SECURITYSUBTYPE"));
					sumSV += rs.getDouble("SHORTFALLVALUE");
					secs.add(obsec);
				}// End of WHILE1.1
				rs.close();
			}// END IF1

			// In Case: Only 1 result and The last Limit
			if (oblimit != null) {
				oblimit.setShortFallValue(new Amount(sumSV, oblimit.getOperationalLimit().getCurrencyCode()));
			}

			return limits;
		}
		catch (SQLException e) {
			throw new DataAccessException("Failed on Deal ResultSet Exception", e.fillInStackTrace());
		}
		finally {
			// if ( ctx != null )ctx.close();
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
		if (rs != null) {
			OBCommodityLimitLevel obcmd = null;
			while (rs.next()) {
				obcmd = new OBCommodityLimitLevel();

				// Assemble OBCommodityLimitLevel which contains OBLimitInfo
				obcmd.setLeName(rs.getString("LEGAL_NAME"));
				obcmd.setLeID(rs.getString("LE_ID"));
				obcmd.setInstrRefNo(rs.getString("REFNO"));
				obcmd.setArm(rs.getString("ARMNAME"));
				obcmd.setLimitProfileID(rs.getString("LMTPROFILE_ID"));
				obcmd.setOriginatingCountry(rs.getString("CMS_ORIG_COUNTRY"));
				// retrieve the Limit info
				SearchingParameters criteria = new SearchingParameters(IDAODescriptor.QUERYTAG,
						CMDLimitDAODescriptor.LIMIT_TAGPATH);
				criteria.put("LEGALID", obcmd.getLeID());
				criteria.put(IDAODescriptor.LIMIT_PROFILE_ID, obcmd.getLimitProfileID());
				CMDLimitDAO dao = new CMDLimitDAO();
				Collection deals = dao.getLimitInfoCollection(criteria);
				DefaultLogger.debug(this, "Deal retrieved......" + Integer.toString(deals.size()));
				obcmd.setLimitInfo(deals);
				DefaultLogger.debug(this, "Deal retrieved......" + obcmd);
				results.add(obcmd);
			}
		}
		return results;
	}
}
