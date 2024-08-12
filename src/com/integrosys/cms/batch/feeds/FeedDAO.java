/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.batch.feeds;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.AbstractDBUtil;
import com.integrosys.base.techinfra.dbsupport.DB2Sequencer;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.collateral.bus.CollateralDetailFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.IBondsCommon;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.IEquityCommon;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.IPortfolioCommon;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.nonlistedlocal.INonListedLocal;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Purpose: A DAO object to upload the feeds data into the database Description:
 * @author lini <br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 */

public class FeedDAO {

	private final String NULL_STRING = "null";

	private AbstractDBUtil dbUtil;

	public List getFeeds(String feedType) throws SearchDAOException {
		DefaultLogger.info(this, "Entering into getFeeds DAO Method");
		String feedSql = getFeedTypeSql(feedType);
		ArrayList retList = new ArrayList();
		OBFeed feed;
		String type = "";
		OBCollateralSubType subType;
		String sql = feedSql;
//		System.out.println(sql);

		try {
			dbUtil = getDBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				subType = new OBCollateralSubType();
				subType.setSubTypeCode(rs.getString(2));
				type = rs.getString(3);
				ICollateral col = CollateralDetailFactory.getOB(subType);
				if (feedType.equals(ICMSConstant.STOCK_FEED_GROUP_TYPE)) {
					if ((col instanceof IPortfolioCommon)
							|| ((col instanceof IEquityCommon) && !(col instanceof INonListedLocal) && (type != null) && !type
									.equals(ICMSConstant.EQUITY_TYPE_UNIT_TRUST))) {
						// set the ob here and add to list..
						feed = new OBFeed();
						feed.setRic(rs.getString(1));
						retList.add(feed);
					}
					// Not used
				}
				else if (feedType.equals(ICMSConstant.CORPORATE_ACTION_FEED_GROUP_TYPE)) {
					if ((col instanceof IPortfolioCommon)
							|| ((col instanceof IEquityCommon) && !(col instanceof INonListedLocal) && (type != null) && !type
									.equals(ICMSConstant.EQUITY_TYPE_UNIT_TRUST))) {
						// set the ob here and add to list..
						feed = new OBFeed();
						feed.setRic(rs.getString(1));
						retList.add(feed);
					}
				}
				else if (feedType.equals(ICMSConstant.UNIT_TRUST_FEED_GROUP_TYPE)) {
					if ((col instanceof IEquityCommon) && (type != null)
							&& type.equals(ICMSConstant.EQUITY_TYPE_UNIT_TRUST)) {
						// set the ob here and add to list..
						feed = new OBFeed();
						feed.setRic(rs.getString(1));
						retList.add(feed);
					}
					// Not used
				}
				else if (feedType.equals(ICMSConstant.BOND_FEED_GROUP_TYPE)) {
					if (col instanceof IBondsCommon) {
						// set the ob here and add to list..
						feed = new OBFeed();
						feed.setRic(rs.getString(1));
						retList.add(feed);
					}
				}
			}
			rs.close();
			return retList;
		}
		catch (DBConnectionException e) {
			DefaultLogger.debug(this, "In getFeeds " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "In getFeeds " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		finally {
			finalize(dbUtil);
		}
	}

	private int updateEDOFeeds(List feedsList) throws SearchDAOException {
		DefaultLogger.info(this, "Entering into update feeds to load DAO Method");
		OBFeed feed;
		boolean canUpdate = false;
		boolean insertToHistory = false;
		ResultSet rs = null;
		long grpId;
		long feedRef;
		int rowUpdated = 0;

		try {
			dbUtil = getDBUtil();
			for (int i = 0; i < feedsList.size(); i++) {
				DefaultLogger.info(this, "Processing for feed " + i);
				feed = (OBFeed) feedsList.get(i);
				if ((feed.getClosePrice() != 0) && (feed.getTradingDate() != null)) {
					// check whether stock code is already there in
					// cms_price_feed table
					dbUtil.setSQL(getTickerFromCmsPriceFeed());
					dbUtil.setString(1, feed.getTicker());
					dbUtil.setString(2, feed.getShareType());
					rs = dbUtil.executeQuery();
					canUpdate = false;
					if (rs.next()) {
						canUpdate = true;
					}
					rs.close();
					insertToHistory = false;
					DefaultLogger.info(this, "Can Update? " + canUpdate);
					if (canUpdate) {
						// TODO check if there is require to open and close
						// dbUtil
						int ind = 1;
						dbUtil.setSQL(getBusFeedPriceUpdSqlEOD());
						dbUtil.setString(ind++, feed.getRic());
						dbUtil.setString(ind++, feed.getCurrencyCode());
						dbUtil.setString(ind++, feed.getDescription());
						dbUtil.setDouble(ind++, feed.getClosePrice());
						dbUtil.setString(ind++, "SYSTEM");
						dbUtil.setTimestamp(ind++, new java.sql.Timestamp(DateUtil.getDate().getTime()));
						dbUtil.setString(ind++, feed.getSuspended());

						if (feed.getExpiryDate() != null) {
							dbUtil.setTimestamp(ind++, new java.sql.Timestamp(feed.getExpiryDate().getTime()));
						}
						else {
							dbUtil.setTimestamp(ind++, null);
						}

						dbUtil.setDouble(ind++, feed.getParValue());
						dbUtil.setDouble(ind++, feed.getPaidUpCapital());
						dbUtil.setString(ind++, feed.getShareStatus());
						dbUtil.setDouble(ind++, feed.getTotalOutStandingShares());
						dbUtil.setString(ind++, feed.getBoardType());
						// Where condition
						dbUtil.setString(ind++, feed.getTicker());
						dbUtil.setTimestamp(ind++, new java.sql.Timestamp(DateUtil.getDate().getTime()));
						rowUpdated = dbUtil.executeUpdate();

						if (rowUpdated > 0) {
							insertToHistory = true;
							// TODO check if there is require to open and close
							// dbUtil
							dbUtil.setSQL(getBusGrpIdSql());
							dbUtil.setString(1, feed.getTicker());
							dbUtil.setString(2, feed.getShareType());
							rs = dbUtil.executeQuery();
							rs.next();
							grpId = rs.getLong(1);
							feedRef = rs.getLong(2);
							long priceFeedId = rs.getLong(3);
							rs.close();
							// TODO check if there is require to open and close
							// dbUtil
							dbUtil.setSQL(getBusFeedGrpUpdSql());
							dbUtil.setLong(1, DateUtil.getDate().getTime());
							dbUtil.setLong(2, grpId);
							rowUpdated = dbUtil.executeUpdate();

							DefaultLogger.info(this, " **** updated buss rec no " + i + " " + " grpId=" + grpId + " "
									+ DateUtil.getDate().getTime());
							// TODO check if there is require to open and close
							// dbUtil
							dbUtil.setSQL(getStgRefSql());
							dbUtil.setString(1, "" + grpId); // grp id it the
																// reference id
																// in trx
																// table..
							dbUtil.setString(2, ICMSConstant.INSTANCE_STOCK_FEED_GROUP);// todo
																						// make
																						// dynamic
																						// for
																						// stock
																						// /
																						// unit
																						// trust
							rs = dbUtil.executeQuery();
							if (rs.next()) { // *** only when trx status is not
												// active update staging....
								rs.close();
								dbUtil.setSQL(getStgUpdFeedPriceSqlEOD());
								ind = 1;
								dbUtil.setString(ind++, feed.getIsinCode());
								dbUtil.setString(ind++, feed.getCurrencyCode());
								dbUtil.setString(ind++, feed.getDescription());
								dbUtil.setDouble(ind++, feed.getClosePrice());
								dbUtil.setString(ind++, "SYSTEM");
								dbUtil.setTimestamp(ind++, new java.sql.Timestamp(DateUtil.getDate().getTime()));
								dbUtil.setString(ind++, feed.getSuspended());

								if (feed.getExpiryDate() != null) {
									dbUtil.setTimestamp(ind++, new java.sql.Timestamp(feed.getExpiryDate().getTime()));
								}
								else {
									dbUtil.setTimestamp(ind++, null);
								}

								dbUtil.setDouble(ind++, feed.getParValue());
								dbUtil.setDouble(ind++, feed.getPaidUpCapital());
								dbUtil.setString(ind++, feed.getShareStatus());
								dbUtil.setDouble(ind++, feed.getTotalOutStandingShares());
								dbUtil.setString(ind++, feed.getBoardType());
								// Where condition
								dbUtil.setString(ind++, feed.getTicker());
								dbUtil.setTimestamp(ind++, new java.sql.Timestamp(feed.getTradingDate().getTime()));
								dbUtil.setLong(ind++, feedRef);

								rowUpdated = dbUtil.executeUpdate();
								// TODO check if there is require to open and
								// close dbUtil
								dbUtil.setSQL(getStgGrpIdSql());
								dbUtil.setString(1, feed.getTicker());
								dbUtil.setString(2, feed.getShareType());
								rs = dbUtil.executeQuery();
								if ((rs != null) && rs.next()) {
									grpId = rs.getLong(1);
								}
								if (rs != null) {
									rs.close();
								}
								DefaultLogger.info(this, " **** updated stag rec no " + i + " "
										+ DateUtil.getDate().getTime());
								dbUtil.setSQL(getStgFeedGrpUpdSql());
								dbUtil.setLong(1, DateUtil.getDate().getTime());
								dbUtil.setLong(2, grpId);
								rowUpdated = dbUtil.executeUpdate();

								DefaultLogger.info(this, " **** updated stag rec no " + i + " "
										+ DateUtil.getDate().getTime());
							}
							else {
								rs.close();
							}

							// Credit Risk Param
							updateCRP_Stock(dbUtil, feed, grpId, priceFeedId, i);

						}
					}
					else {
						// insert if no rec in buss tables..

						// resolve the cms_feed grp id for the data from feed
						// group table..
						// TODO check if there is require to open and close
						// dbUtil
						dbUtil.setSQL(getFeedGrpIdFromFeedGroupEdo());
						DefaultLogger.debug(this, getFeedGrpIdFromFeedGroupEdo());
						dbUtil.setString(1, ICMSConstant.STOCK_FEED_GROUP_TYPE);
						dbUtil.setString(2, feed.getBoardType()); // Board Type
						dbUtil.setString(3, feed.getShareType()); // Share Type
						DefaultLogger.debug(this, "Board " + feed.getBoardType() + " ShareType " + feed.getShareType());
						rs = dbUtil.executeQuery();
						grpId = -1;
						if (rs.next()) {
							grpId = rs.getLong(1);
						}
						else {
							DefaultLogger.debug(this,
									"Stock not inserted as Feed_Group record was not found for the Board/ Sharemapping : "
											+ feed.getBoardType() + " " + feed.getShareType());
							continue;
						}
						rs.close();

						DB2Sequencer seq = new DB2Sequencer();
						long priceFeedId = Long.parseLong(seq.getSeqNum(ICMSConstant.SEQUENCE_FEED_PRICE_FEED));
						// insert actual
						// TODO check if there is require to open and close
						// dbUtil
						dbUtil.setSQL(feedInsertEDOSql());
						DefaultLogger.debug(this, feedInsertEDOSql());
						int ind = 1;
						dbUtil.setLong(ind++, priceFeedId);
						dbUtil.setString(ind++, feed.getRic());
						dbUtil.setString(ind++, feed.getShareType());// ICMSConstant
																		// .
																		// STOCK_FEED_GROUP_TYPE
																		// );
						dbUtil.setDouble(ind++, feed.getClosePrice());
						dbUtil.setString(ind++, feed.getIsinCode());
						dbUtil.setTimestamp(ind++, new java.sql.Timestamp(DateUtil.getDate().getTime()));
						dbUtil.setString(ind++, "SYSTEM");
						dbUtil.setLong(ind++, grpId);
						dbUtil.setLong(ind++, DateUtil.getDate().getTime());
						dbUtil.setLong(ind++, priceFeedId);
						dbUtil.setString(ind++, feed.getCurrencyCode());
						dbUtil.setString(ind++, feed.getDescription());
						dbUtil.setString(ind++, feed.getSuspended());

						// dbUtil.setString(ind++, feed.getShareType());
						dbUtil.setString(ind++, feed.getTicker());
						if (feed.getExpiryDate() != null) {
							dbUtil.setTimestamp(ind++, new java.sql.Timestamp(feed.getExpiryDate().getTime()));
						}
						else {
							dbUtil.setTimestamp(ind++, null);
						}

						dbUtil.setDouble(ind++, feed.getParValue());
						dbUtil.setDouble(ind++, feed.getPaidUpCapital());
						dbUtil.setString(ind++, feed.getShareStatus());
						dbUtil.setDouble(ind++, feed.getTotalOutStandingShares());
						dbUtil.setString(ind++, feed.getBoardType());
						dbUtil.setString(ind++, feed.getFIIndicator());

						dbUtil.setString(ind++, feed.getBoardType());
						dbUtil.setString(ind++, feed.getBoardType());

						rowUpdated = dbUtil.executeUpdate();
						if (rowUpdated > 0) {
							insertToHistory = true;
						}
						else {
							insertToHistory = false;
						}
						dbUtil.setSQL(feedInsertEDOStageSql());
						DefaultLogger.debug(this, feedInsertEDOStageSql());
						dbUtil.setLong(1, priceFeedId);
						rowUpdated = dbUtil.executeUpdate();

						// TODO check if there is require to open and close
						// dbUtil
						// go to trx table and search for this trx_type with
						// grp_id above as key, and get stg ref and status ..
						DefaultLogger.debug(this, "=== Trx Query" + getTrxDetails() + grpId);
						String trxStgRef = null;
						String trxStatus = null;
						dbUtil.setSQL(getTrxDetails());
						dbUtil.setString(1, ICMSConstant.INSTANCE_STOCK_FEED_GROUP);
						dbUtil.setString(2, "" + grpId);
						rs = dbUtil.executeQuery();
						if (rs.next()) {
							trxStgRef = rs.getString(1);
							trxStatus = rs.getString(2);
						}
						DefaultLogger.debug(this, "trxStgRef " + trxStgRef + " trxStatus " + trxStatus);
						if (rs != null) {
							rs.close();
							// if REF and STAGE REF both exists in trx
							// table..(the group is maintained earlier but not
							// ric )
						}

						// For first time to set Staging Reference ID in
						// transaction as DB setip does not set staging ref ID
						// Setting Stg ref ID = Ref ID = grpId
						if ((trxStgRef == null) || trxStgRef.trim().equals("")) { // If
																					// transaction
																					// does
																					// not
																					// exist
																					// ,
																					// Insert
							dbUtil.setSQL(feedUpdateEDOTrxSql());
							ind = 1;
							DefaultLogger.debug(this, feedUpdateEDOTrxSql() + grpId);
							dbUtil.setLong(ind++, grpId);
							dbUtil.setString(ind++, ICMSConstant.INSTANCE_STOCK_FEED_GROUP);
							dbUtil.setLong(ind++, grpId);
							rowUpdated = dbUtil.executeUpdate();
							DefaultLogger.debug(this, "rowUpdated = " + rowUpdated);

						} // end stg ref is null

						// Credit Risk Param
						updateCRP_Stock(dbUtil, feed, grpId, priceFeedId, i);

					}

					if (insertToHistory) {
						// insert into history...
						// TODO check if there is require to open and close
						// dbUtil
						dbUtil.setSQL(getFeedGrpIdFromFeedGroupEdo());
						dbUtil.setString(1, ICMSConstant.STOCK_FEED_GROUP_TYPE);
						// dbUtil.setString(2, feed.getExchange());
						dbUtil.setString(2, feed.getBoardType()); // Board Type
						dbUtil.setString(3, feed.getShareType()); // Share Type
						rs = dbUtil.executeQuery();
						grpId = -1;
						if (rs.next()) {
							grpId = rs.getLong(1);
						}
						else {
							DefaultLogger.debug(this,
									"Stock not inserted as Feed_Group record was not found for the exchange code mapping : "
											+ feed.getExchange());
							continue;
						}
						rs.close();

						DB2Sequencer seq = new DB2Sequencer();
						long priceFeedId = Long.parseLong(seq.getSeqNum(ICMSConstant.SEQUENCE_FEED_PRICE_FEED_HISTORY));
						// pstmtInsert = new RMDBUtil(this);
						// TODO check if there is require to open and close
						// dbUtil
						dbUtil.setSQL(feedInsertEDOHistorySql());
						int ind = 1;
						dbUtil.setLong(ind++, priceFeedId);
						dbUtil.setString(ind++, feed.getRic());
						dbUtil.setString(ind++, feed.getShareType());// ICMSConstant
																		// .
																		// STOCK_FEED_GROUP_TYPE
																		// );
						dbUtil.setDouble(ind++, feed.getClosePrice());
						dbUtil.setString(ind++, feed.getIsinCode());
						dbUtil.setTimestamp(ind++, new java.sql.Timestamp(DateUtil.getDate().getTime()));
						dbUtil.setString(ind++, "SYSTEM");
						dbUtil.setLong(ind++, grpId);
						dbUtil.setLong(ind++, DateUtil.getDate().getTime());
						dbUtil.setLong(ind++, priceFeedId);
						dbUtil.setString(ind++, feed.getCurrencyCode());
						dbUtil.setString(ind++, feed.getDescription());
						dbUtil.setString(ind++, feed.getSuspended());

						// dbUtil.setString(ind++, feed.getShareType());
						dbUtil.setString(ind++, feed.getTicker());
						if (feed.getExpiryDate() != null) {
							dbUtil.setTimestamp(ind++, new java.sql.Timestamp(feed.getExpiryDate().getTime()));
						}
						else {
							dbUtil.setTimestamp(ind++, null);
						}

						dbUtil.setDouble(ind++, feed.getParValue());
						dbUtil.setDouble(ind++, feed.getPaidUpCapital());
						dbUtil.setString(ind++, feed.getShareStatus());
						dbUtil.setDouble(ind++, feed.getTotalOutStandingShares());
						dbUtil.setString(ind++, feed.getBoardType());
						dbUtil.setString(ind++, feed.getFIIndicator());

						dbUtil.setString(ind++, feed.getBoardType());
						dbUtil.setString(ind++, feed.getBoardType());

						rowUpdated = dbUtil.executeUpdate();
					}
				}
			}
			DefaultLogger.info(this, "Committing all records");

		}
		catch (DBConnectionException e) {
			DefaultLogger.debug(this, "In getFeeds " + e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "In getFeeds " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			finalize(dbUtil);
		}
		return rowUpdated;
	}

	public int updateFeeds(List feedsList, String feedType) throws SearchDAOException {
		int rowUpdated = 0;
		if (feedType.equals(ICMSConstant.STOCK_FEED_GROUP_TYPE)) {
			rowUpdated = updateEDOFeeds(feedsList);
		}
		if (feedType.equals(ICMSConstant.UNIT_TRUST_FEED_GROUP_TYPE)) {
			rowUpdated = updateUTFeeds(feedsList);
		} // change unit_trust to be by ctry..
		/*
		 * if (feedType.equals(ICMSConstant.CORPORATE_ACTION_FEED_GROUP_TYPE)) {
		 * rowUpdated = updateSharesOutstandingFeeds(feedsList); } // corporate
		 * action need to clarify.. if
		 * (feedType.equals(ICMSConstant.BOND_FEED_GROUP_TYPE)) { rowUpdated =
		 * updateBasicBondFeeds(feedsList); }
		 */

		return rowUpdated;
	}

	private String getFeedTypeSql(String feedType) {
		StringBuffer sb = new StringBuffer();
		/*
		 * sb.append("select b.RIC from cms_stage_feed_group a, cms_stage_price_feed b where a.feed_group_id=b.feed_group_id "
		 * ); sb.append("and ric is not null and a.GROUP_TYPE='");
		 * sb.append(feedType); sb.append("'");
		 */
		sb.append("select unique(a.ric),security_sub_type_id,a.type from cms_portfolio_item a, cms_security s ");
		sb.append("where a.ric is not null and a.cms_collateral_id = s.cms_collateral_id");
		return sb.toString();
	}

	private String getStgUpdFeedPriceSqlEOD() {
		StringBuffer sb = new StringBuffer();
		sb.append("update cms_stage_price_feed set isin_code=?,currency=?,name=?,unit_price=?,");
		sb.append("last_updated_by=?,last_updated_date=?, is_suspended=?, ");
		sb.append("expiry_date=?,par_value=?,paid_up_capital=?,share_status=?,total_outstanding_unit=?, board_type=? ");
		sb.append("where ticker=? and last_updated_date is not null ");
		sb.append("and last_updated_date <=? and FEED_REF=?");
		return sb.toString();
	}

	private String getBusFeedPriceUpdSqlEOD() {
		StringBuffer sb = new StringBuffer();
		sb.append("update cms_price_feed set ric=?,currency=?,name=?,unit_price=?,");
		sb.append("last_updated_by=?,last_updated_date=?, is_suspended=?, ");
		sb
				.append("expiry_date=?,par_value=?,paid_up_capital=?,share_status=?,total_outstanding_unit=?, board_type=?  ");
		sb.append("where ticker=? and last_updated_date is not null ");
		sb.append("and last_updated_date <= ?");
		return sb.toString();
	}

	private String getStgGrpIdSql() {
		return "select feed_group_id from cms_stage_price_feed where ticker=? and group_stock_type=?";
	}

	private String getBusGrpIdSql() {
		return "select feed_group_id, feed_ref, feed_id from cms_price_feed where ticker=? and type=?";
	}

	private String getStgFeedGrpUpdSql() {
		return " update cms_stage_feed_group set cms_version_time = ? where feed_group_id = ?";
	}

	private String getBusFeedGrpUpdSql() {
		return " update cms_feed_group set cms_version_time = ? where feed_group_id = ?";
	}

	private String getStgRefSql() {
		return "select STAGING_REFERENCE_ID from TRANSACTION where REFERENCE_ID=? and TRANSACTION_TYPE=? and STATUS != 'ACTIVE'";
	}

	private String getFeedGrpIdFromFeedGroupEdo() {
		return "select feed_group_id from CMS_FEED_GROUP where GROUP_TYPE=? and GROUP_SUBTYPE = "
				+ " (SELECT REF_ENTRY_CODE from common_code_category_entry where category_code = 'BOARD_TYPE' "
				+ " and entry_code = ?) and group_stock_type = ? ";
	}

	private String getTrxDetails() {
		return "select STAGING_REFERENCE_ID,STATUS from TRANSACTION where TRANSACTION_TYPE=? and REFERENCE_ID=?";
	}

	private String feedInsertEDOSql() {
		return "insert into cms_price_feed "
				+ " (FEED_ID,RIC,TYPE,UNIT_PRICE,ISIN_CODE,LAST_UPDATED_DATE,LAST_UPDATED_BY,"
				+ "  FEED_GROUP_ID,CMS_VERSION_TIME,FEED_REF,CURRENCY,NAME, IS_SUSPENDED, "
				+ " TICKER, EXPIRY_DATE,PAR_VALUE,PAID_UP_CAPITAL,SHARE_STATUS, total_outstanding_unit, board_type, is_fi, EXCHANGE,COUNTRY )"
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?, ?,?, "
				+ " (SELECT '' ||ref_entry_code ||'' from common_code_category_entry where category_code = 'BOARD_TYPE' and entry_code = ? ),"
				+ " (SELECT '' ||country||'' from common_code_category_entry where category_code = 'BOARD_TYPE' and entry_code = ?))";
	}

	private String feedInsertEDOStageSql() {
		return "insert into cms_stage_price_feed "
				+ " (FEED_ID,RIC,TYPE,UNIT_PRICE,ISIN_CODE,LAST_UPDATED_DATE,LAST_UPDATED_BY,"
				+ "  FEED_GROUP_ID,CMS_VERSION_TIME,FEED_REF,CURRENCY,NAME, IS_SUSPENDED, "
				+ "  TICKER, EXPIRY_DATE,PAR_VALUE,PAID_UP_CAPITAL,SHARE_STATUS, total_outstanding_unit, board_type, is_fi, EXCHANGE,COUNTRY )"
				+ " (select FEED_ID,RIC,TYPE,UNIT_PRICE,ISIN_CODE,LAST_UPDATED_DATE,LAST_UPDATED_BY,"
				+ "  FEED_GROUP_ID,CMS_VERSION_TIME,FEED_REF,CURRENCY,NAME, IS_SUSPENDED, "
				+ "  TICKER, EXPIRY_DATE,PAR_VALUE,PAID_UP_CAPITAL,SHARE_STATUS, total_outstanding_unit, board_type, is_fi, EXCHANGE,COUNTRY "
				+ " from cms_price_feed" + " where feed_id = ? ) ";
	}

	private String feedUpdateEDOTrxSql() {
		return "update transaction set STAGING_REFERENCE_ID = ?" + " where TRANSACTION_TYPE = ?"
				+ "   and reference_id = ?";
	}

	private String feedInsertEDOHistorySql() {
		return "insert into cms_price_feed_history "
				+ "(HISTORY_ID,RIC,TYPE,UNIT_PRICE,ISIN_CODE,LAST_UPDATED_DATE,LAST_UPDATED_BY,"
				+ " FEED_GROUP_ID,CMS_VERSION_TIME,FEED_REF,CURRENCY,NAME, IS_SUSPENDED, "
				+ " TICKER, EXPIRY_DATE,PAR_VALUE,PAID_UP_CAPITAL,SHARE_STATUS, total_outstanding_unit, board_type, is_fi, EXCHANGE,COUNTRY )"
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?, ?,?, "
				+ " (SELECT '' ||ref_entry_code ||'' from common_code_category_entry where category_code = 'BOARD_TYPE' and entry_code = ? ),"
				+ " (SELECT '' ||country||'' from common_code_category_entry where category_code = 'BOARD_TYPE' and entry_code = ?))";
	}

	private String stockFeedInsertCreditRiskParam() {
		return "insert into cms_credit_risk_param (PARAM_ID,PARAM_TYPE,FEED_ID,PARAM_REF,IS_FI,BOARD_TYPE,SHARE_STATUS) "
				+ " values (?,?,?,?,?,?,?) ";
	}

	private String stockFeedInsertStageCreditRiskParam() {
		return "insert into cms_stage_credit_risk_param (PARAM_ID,PARAM_TYPE,FEED_ID,PARAM_REF,IS_FI,BOARD_TYPE,SHARE_STATUS) "
				+ " values (?,?,?,?,?,?,?) ";
	}

	private String getCreditRiskParam() {
		return "select * from cms_credit_risk_param " + "where feed_id = ?";
	}

	private String updateCreditRiskParam() {
		return "update cms_credit_risk_param " + " set IS_FI =?, BOARD_TYPE=?, SHARE_STATUS=?  "
				+ " where feed_id = ? ";
	}

	private String getTickerFromCmsPriceFeed() {
		return "select * from cms_price_feed where ticker=? and type=?";
	}

	// Unit Trusts

	private String getFundCodeFromCmsPriceFeed() {
		return "select * from cms_price_feed where fund_code=? and country=?";
	}

	private String getUTGrpIdSql() {
		return "select feed_group_id, feed_ref, feed_id from cms_price_feed where fund_code=? and country=?";
	}

	private String updateFeedGrpSql() {
		return " update cms_feed_group set cms_version_time = ? where feed_group_id = ?";
	}

	private String updateUTFeed() {
		StringBuffer sb = new StringBuffer();
		sb.append("update cms_price_feed set isin_code=?,currency=?,name=?,unit_price=?,offer=?,");
		sb.append(" last_updated_by=?,last_updated_date=? ");
		sb.append(" where fund_code=? and last_updated_date is not null ");
		sb.append(" and last_updated_date <= ?");
		return sb.toString();
	}

	private String insertUTSql() {
		return "insert into cms_price_feed "
				+ " (FEED_ID,TYPE,UNIT_PRICE,offer,ISIN_CODE,LAST_UPDATED_DATE,LAST_UPDATED_BY,"
				+ "  FEED_GROUP_ID,CMS_VERSION_TIME,FEED_REF,CURRENCY,NAME,fund_code, COUNTRY )"
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	private String updateStageUTSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("update cms_stage_price_feed set isin_code=?,currency=?,name=?,unit_price=?,offer=?,");
		sb.append(" last_updated_by=?,last_updated_date=? ");
		sb.append(" where fund_code=? and last_updated_date is not null ");
		sb.append(" and last_updated_date <=? and FEED_REF=?");
		return sb.toString();
	}

	private String getStgGrpIdUTSql() {
		return "select feed_group_id from cms_stage_price_feed where fund_code=? and country=?";
	}

	private String getFeedGrpIdFromFeedGroupUT() {
		return "select feed_group_id from CMS_FEED_GROUP where GROUP_TYPE=? and GROUP_SUBTYPE=? ";
	}

	private String feedInsertUTHistorySql() {
		return "insert into cms_price_feed_history "
				+ "(HISTORY_ID,TYPE,UNIT_PRICE,OFFER,ISIN_CODE,LAST_UPDATED_DATE,LAST_UPDATED_BY,"
				+ " FEED_GROUP_ID,CMS_VERSION_TIME,FEED_REF,CURRENCY,NAME, fund_code, COUNTRY )"
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	// not used now
	private String feedInsertBondSql() {
		return "insert into cms_price_feed "
				+ "(FEED_ID,RIC,TYPE,UNIT_PRICE,ISIN_CODE,LAST_UPDATED_DATE,LAST_UPDATED_BY,"
				+ "FEED_GROUP_ID,CMS_VERSION_TIME,FEED_REF,CURRENCY,COUNTRY,NAME,MATURITY_DATE,TICKER,RATING)"
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	private String getFeedGrpIdFromFeedGroup() {
		return "select feed_group_id from CMS_FEED_GROUP where GROUP_TYPE=? and GROUP_SUBTYPE = ? ";
	}

	private String getFeedGrpIdFromFeedGroupBond() {
		return "select FEED_GROUP_ID from CMS_FEED_GROUP where GROUP_TYPE=? and GROUP_SUBTYPE is null";
	}

	private String feedInsertBondHistorySql() {
		return "insert into cms_price_feed_history "
				+ "(HISTORY_ID,RIC,TYPE,UNIT_PRICE,ISIN_CODE,LAST_UPDATED_DATE,LAST_UPDATED_BY,"
				+ "FEED_GROUP_ID,CMS_VERSION_TIME,FEED_REF,CURRENCY,COUNTRY,NAME,MATURITY_DATE,TICKER,RATING)"
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	private String getIsinFromCmsPriceFeed() {
		return "select * from cms_price_feed where isin_code=?";
	}

	private String getStgUpdFeedPriceSqlSO() {
		StringBuffer sb = new StringBuffer();
		sb.append("update cms_stage_price_feed set currency=?,name=?,total_outstanding_unit=?,");
		sb.append("last_updated_by=? ");
		sb.append("where isin_code=? and last_updated_date is not null ");
		return sb.toString();
	}

	private String getBusFeedPriceUpdSqlSO() {
		StringBuffer sb = new StringBuffer();
		sb.append("update cms_price_feed set currency=?,total_outstanding_unit=?,");
		sb.append("last_updated_by=? ");
		sb.append("where ric=? and last_updated_date is not null ");
		return sb.toString();
	}

	private String getStgUpdFeedPriceSqlBond() {
		StringBuffer sb = new StringBuffer();
		sb.append("update cms_stage_price_feed set isin_code=?,name=?,unit_price=?,");
		sb.append("last_updated_by=?,last_updated_date=?,maturity_date=?,ticker=?,rating=? ");
		sb.append("where ric=? and last_updated_date is not null ");
		sb.append("and last_updated_date <= ?");
		return sb.toString();
	}

	private String getBusFeedPriceUpdSqlBond() {
		StringBuffer sb = new StringBuffer();
		sb.append("update cms_price_feed set isin_code=?,name=?,unit_price=?,");
		sb.append("last_updated_by=?,last_updated_date=?,maturity_date=?,ticker=?,rating=?  ");
		sb.append("where ric=? and last_updated_date is not null ");
		sb.append("and last_updated_date <= ?");
		return sb.toString();
	}

	private String getSecurityDetails() {
		return "select a.STOCK_EXCHANGE_COUNTRY,s.sci_security_currency from cms_portfolio_item a, cms_security s where "
				+ " a.cms_collateral_id = s.cms_collateral_id and a.isin_code = ?";
	}

	// not used now

	/**
	 * Helper method to clean up database resources.
	 * 
	 * @param dbUtil database utility object
	 * @throws com.integrosys.base.businfra.search.SearchDAOException error in
	 *         cleaning up DB resources
	 */
	private static void finalize(AbstractDBUtil dbUtil) throws SearchDAOException {
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in cleaning up DB resources: " + e.toString());
		}
	}

	private AbstractDBUtil getDBUtil() throws DBConnectionException, SQLException {
		return new DBUtil();
	}

	private int updateUTFeeds(List feedsList) throws SearchDAOException {
		DefaultLogger.info(this, "Entering into update UT feeds to load DAO Method");
		OBFeed feed;
		boolean canUpdate = false;
		boolean insertToHistory = false;
		ResultSet rs = null;
		long grpId;
		long feedRef;
		int rowUpdated = 0;

		try {
			dbUtil = getDBUtil();
			for (int i = 0; i < feedsList.size(); i++) {
				DefaultLogger.info(this, "Processing for feed " + i);
				feed = (OBFeed) feedsList.get(i);
				// Changed from Close Price to NAV acc to discussion with Simon
				// on 26 Jul 07
				if ((feed.getNAV() != 0) && (feed.getTradingDate() != null)) {
					// check whether fund code is already there in
					// cms_price_feed table
					canUpdate = canUpdateUT(dbUtil, feed);

					insertToHistory = false;
					DefaultLogger.info(this, "Can Update? " + canUpdate);
					if (canUpdate) {
						rowUpdated = updateUT(dbUtil, feed);

						if (rowUpdated > 0) {
							insertToHistory = true;
							// Get Group ID
							dbUtil.setSQL(getUTGrpIdSql());
							dbUtil.setString(1, feed.getFundCode());
							dbUtil.setString(2, feed.getCountry());
							rs = dbUtil.executeQuery();
							rs.next();
							grpId = rs.getLong(1);
							feedRef = rs.getLong(2);
							long priceFeedId = rs.getLong(3);
							rs.close();

							updateUTFeedGroup(dbUtil, grpId);
							DefaultLogger.info(this, " **** updated buss rec no " + i + " " + " grpId=" + grpId + " "
									+ DateUtil.getDate().getTime());
							rowUpdated = updateUTStage(dbUtil, feed, grpId, feedRef, i);

							// Credit Risk Param
							updateCRP(dbUtil, feed, grpId, priceFeedId, i);

						}
					}
					else {
						// insert if no rec in buss tables..

						// resolve the cms_feed grp id for the data from feed
						// group table..
						// TODO check if there is require to open and close
						// dbUtil
						dbUtil.setSQL(getFeedGrpIdFromFeedGroupUT());
						DefaultLogger.debug(this, getFeedGrpIdFromFeedGroupUT());
						dbUtil.setString(1, ICMSConstant.UNIT_TRUST_FEED_GROUP_TYPE);
						dbUtil.setString(2, feed.getCountry());
						DefaultLogger.debug(this, "Country  " + feed.getCountry());
						rs = dbUtil.executeQuery();
						grpId = -1;
						if (rs.next()) {
							grpId = rs.getLong(1);
						}
						else {
							DefaultLogger.debug(this,
									"UT not inserted as Feed_Group record was not found for the Board/ Sharemapping : "
											+ feed.getBoardType() + " " + feed.getShareType());
							continue;
						}
						rs.close();

						DB2Sequencer seq = new DB2Sequencer();
						long priceFeedId = Long.parseLong(seq.getSeqNum(ICMSConstant.SEQUENCE_FEED_PRICE_FEED));

						// Insert Unit Trust
						rowUpdated = insertUT(dbUtil, feed, grpId, priceFeedId);

						if (rowUpdated > 0) {
							insertToHistory = true;
						}
						else {
							insertToHistory = false;
						}
						rowUpdated = insertUTStage(dbUtil, priceFeedId);

						// TODO check if there is require to open and close
						// dbUtil
						// go to trx table and search for this trx_type with
						// grp_id above as key, and get stg ref and status ..
						DefaultLogger.debug(this, "=== Trx Query" + getTrxDetails() + grpId);
						String trxStgRef = null;
						String trxStatus = null;
						dbUtil.setSQL(getTrxDetails());
						dbUtil.setString(1, ICMSConstant.INSTANCE_UNIT_TRUST_FEED_GROUP);
						dbUtil.setString(2, "" + grpId);
						rs = dbUtil.executeQuery();
						if (rs.next()) {
							trxStgRef = rs.getString(1);
							trxStatus = rs.getString(2);
						}
						DefaultLogger.debug(this, "trxStgRef " + trxStgRef + " trxStatus " + trxStatus);
						if (rs != null) {
							rs.close();
							// if REF and STAGE REF both exists in trx
							// table..(the group is maintained earlier but not
							// ric )
						}

						// For first time to set Staging Reference ID in
						// transaction as DB setip does not set staging ref ID
						// Setting Stg ref ID = Ref ID = grpId
						if ((trxStgRef == null) || trxStgRef.trim().equals("")) { // If
																					// transaction
																					// does
																					// not
																					// exist
																					// ,
																					// Insert
							dbUtil.setSQL(feedUpdateEDOTrxSql());
							int ind = 1;
							DefaultLogger.debug(this, feedUpdateEDOTrxSql() + grpId);
							dbUtil.setLong(ind++, grpId);
							dbUtil.setString(ind++, ICMSConstant.INSTANCE_UNIT_TRUST_FEED_GROUP);
							dbUtil.setLong(ind++, grpId);
							rowUpdated = dbUtil.executeUpdate();
							DefaultLogger.debug(this, "rowUpdated = " + rowUpdated);

						} // end stg ref is null
						updateCRP(dbUtil, feed, grpId, priceFeedId, i);
					}
					if (insertToHistory) {
						// insert into history...
						// TODO check if there is require to open and close
						// dbUtil
						dbUtil.setSQL(getFeedGrpIdFromFeedGroupUT());
						dbUtil.setString(1, ICMSConstant.UNIT_TRUST_FEED_GROUP_TYPE);
						dbUtil.setString(2, feed.getCountry());
						rs = dbUtil.executeQuery();
						grpId = -1;
						if (rs.next()) {
							grpId = rs.getLong(1);
						}
						else {
							DefaultLogger.debug(this,
									"UT not inserted as Feed_Group record was not found for the exchange code mapping : "
											+ feed.getExchange());
							continue;
						}
						rs.close();

						rowUpdated = insertUTHistory(dbUtil, feed, grpId);
					}
				}
			}
			DefaultLogger.info(this, "Committing all records");

		}
		catch (DBConnectionException e) {
			DefaultLogger.debug(this, "In getFeeds " + e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "In getFeeds " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			finalize(dbUtil);
		}
		return rowUpdated;
	}

	private boolean canUpdateUT(AbstractDBUtil dbUtil, OBFeed feed) throws Exception {
		boolean canUpdate = false;
		dbUtil.setSQL(getFundCodeFromCmsPriceFeed());
		dbUtil.setString(1, feed.getFundCode());
		dbUtil.setString(2, feed.getCountry());
		ResultSet rs = dbUtil.executeQuery();
		if (rs.next()) {
			canUpdate = true;
		}
		rs.close();
		return canUpdate;
	}

	private int updateUT(AbstractDBUtil dbUtil, OBFeed feed) throws Exception {
		int ind = 1;
		dbUtil.setSQL(updateUTFeed());
		dbUtil.setString(ind++, feed.getIsinCode());
		dbUtil.setString(ind++, feed.getCurrencyCode());
		dbUtil.setString(ind++, feed.getDescription());
		dbUtil.setDouble(ind++, feed.getNAV());
		dbUtil.setDouble(ind++, feed.getOffer());
		dbUtil.setString(ind++, "SYSTEM");
		dbUtil.setTimestamp(ind++, new java.sql.Timestamp(DateUtil.getDate().getTime()));
		// Where condition
		dbUtil.setString(ind++, feed.getFundCode());
		dbUtil.setTimestamp(ind++, new java.sql.Timestamp(DateUtil.getDate().getTime()));
		int rowUpdated = dbUtil.executeUpdate();
		return rowUpdated;
	}

	private int updateUTFeedGroup(AbstractDBUtil dbUtil, long grpId) throws Exception {
		int rowUpdated;
		dbUtil.setSQL(updateFeedGrpSql());
		dbUtil.setLong(1, DateUtil.getDate().getTime());
		dbUtil.setLong(2, grpId);
		rowUpdated = dbUtil.executeUpdate();
		return rowUpdated;
	}

	private int updateUTStage(AbstractDBUtil dbUtil, OBFeed feed, long grpId, long feedRef, int i) throws Exception {
		dbUtil.setSQL(getStgRefSql());
		dbUtil.setString(1, "" + grpId); // grp id it the reference id in trx
											// table..
		dbUtil.setString(2, ICMSConstant.INSTANCE_UNIT_TRUST_FEED_GROUP);
		ResultSet rs = dbUtil.executeQuery();
		if (rs.next()) { // *** only when trx status is not active update
							// staging....
			rs.close();
			dbUtil.setSQL(updateStageUTSql());
			int ind = 1;
			dbUtil.setString(ind++, feed.getIsinCode());
			dbUtil.setString(ind++, feed.getCurrencyCode());
			dbUtil.setString(ind++, feed.getDescription());
			dbUtil.setDouble(ind++, feed.getNAV());
			dbUtil.setDouble(ind++, feed.getOffer());
			dbUtil.setString(ind++, "SYSTEM");
			dbUtil.setTimestamp(ind++, new java.sql.Timestamp(DateUtil.getDate().getTime()));
			// Where condition
			dbUtil.setString(ind++, feed.getFundCode());
			if (feed.getTradingDate() != null) {
				dbUtil.setTimestamp(ind++, new java.sql.Timestamp(feed.getTradingDate().getTime()));
			}
			else {
				dbUtil.setTimestamp(ind++, null);
			}
			dbUtil.setLong(ind++, feedRef);

			int rowUpdated = dbUtil.executeUpdate();
			// TODO check if there is require to open and close dbUtil
			dbUtil.setSQL(getStgGrpIdUTSql());
			dbUtil.setString(1, feed.getFundCode());
			dbUtil.setString(2, feed.getCountry());
			rs = dbUtil.executeQuery();
			if ((rs != null) && rs.next()) {
				grpId = rs.getLong(1);
			}
			if (rs != null) {
				rs.close();
			}
			dbUtil.setSQL(getStgFeedGrpUpdSql());
			dbUtil.setLong(1, DateUtil.getDate().getTime());
			dbUtil.setLong(2, grpId);
			rowUpdated = dbUtil.executeUpdate();
			DefaultLogger.info(this, " **** updated stag rec no " + i + " " + DateUtil.getDate().getTime());
			return rowUpdated;
		}
		else {
			rs.close();
		}
		return 0;
	}

	private int updateCRP(AbstractDBUtil dbUtil, OBFeed feed, long grpId, long priceFeedId, int i) throws Exception {
		int rowUpdated = 0;
		dbUtil.setSQL(getCreditRiskParam());
		int ind = 1;
		DefaultLogger.info(this, " **** CAN getCreditRiskParam " + getCreditRiskParam() + priceFeedId);
		dbUtil.setLong(ind++, priceFeedId);
		ResultSet rs = dbUtil.executeQuery();
		if (rs.next()) {
			// PriceFeedID exists update Credit Risk Param
			DefaultLogger.info(this, " **** CAN updateCreditRiskParam " + updateCreditRiskParam() + priceFeedId);
			dbUtil.setSQL(updateCreditRiskParam());
			ind = 1;
			dbUtil.setString(ind++, feed.getFIIndicator());
			dbUtil.setString(ind++, feed.getBoardType());
			dbUtil.setString(ind++, feed.getShareStatus());

			dbUtil.setLong(ind++, priceFeedId);
			rowUpdated = dbUtil.executeUpdate();
			DefaultLogger.debug(this, "rowUpdated in CRP = " + rowUpdated);

			DefaultLogger.debug(this, "=== CRP Trx Query" + getTrxDetails() + grpId);
			String trxStgRef = null;
			String trxStatus = null;
			dbUtil.setSQL(getTrxDetails());
			dbUtil.setString(1, ICMSConstant.INSTANCE_CREDIT_RISK_PARAM_UNIT_TRUST);
			dbUtil.setString(2, "" + grpId);
			rs = dbUtil.executeQuery();
			if (rs.next()) {
				trxStgRef = rs.getString(1);
				trxStatus = rs.getString(2);
			}
			DefaultLogger.debug(this, "CRP trxStgRef " + trxStgRef + " trxStatus " + trxStatus);
			if (rs != null) {
				rs.close();
				// if REF and STAGE REF both exists in trx table..(the group is
				// maintained earlier but not ric )
			}

			// For first time to set Staging Reference ID in transaction as DB
			// setip does not set staging ref ID
			// Setting Stg ref ID = Ref ID = grpId
			if ((trxStgRef == null) || trxStgRef.trim().equals("")) { // If
																		// transaction
																		// does
																		// not
																		// exist
																		// ,
																		// Insert
				dbUtil.setSQL(feedUpdateEDOTrxSql());
				ind = 1;
				DefaultLogger.debug(this, feedUpdateEDOTrxSql() + grpId);
				dbUtil.setLong(ind++, grpId);
				dbUtil.setString(ind++, ICMSConstant.INSTANCE_CREDIT_RISK_PARAM_UNIT_TRUST);
				dbUtil.setLong(ind++, grpId);
				rowUpdated = dbUtil.executeUpdate();
				DefaultLogger.debug(this, "rowUpdated = " + rowUpdated);
			} // end stg ref is null
		}
		else {
			// Insert Credit Risk Param
			DB2Sequencer seq = new DB2Sequencer();
			long paramID = Long.parseLong(seq.getSeqNum(ICMSConstant.SEQUENCE_CMS_CREDIT_RISK_PARAM_SEQ));

			DefaultLogger.info(this, " **** CAN stockFeedInsertCreditRiskParam " + stockFeedInsertCreditRiskParam()
					+ priceFeedId);
			dbUtil.setSQL(stockFeedInsertCreditRiskParam());
			ind = 1;
			dbUtil.setLong(ind++, paramID);
			dbUtil.setString(ind++, ICMSConstant.UNIT_TRUST_FEED_GROUP_TYPE);
			dbUtil.setLong(ind++, priceFeedId);
			dbUtil.setLong(ind++, paramID);
			dbUtil.setString(ind++, feed.getFIIndicator());
			dbUtil.setString(ind++, feed.getBoardType());
			dbUtil.setString(ind++, feed.getShareStatus());
			rowUpdated = dbUtil.executeUpdate();
			DefaultLogger.debug(this, "rowInserted in CRP = " + rowUpdated);
			// Stage CRP
			seq = new DB2Sequencer();
			paramID = Long.parseLong(seq.getSeqNum(ICMSConstant.SEQUENCE_CMS_STG_CREDIT_RISK_PARAM_REF_SEQ));
			DefaultLogger.info(this, " **** stockFeedInsertStageCreditRiskParam "
					+ stockFeedInsertStageCreditRiskParam() + priceFeedId);
			dbUtil.setSQL(stockFeedInsertStageCreditRiskParam());
			ind = 1;
			dbUtil.setLong(ind++, paramID);
			dbUtil.setString(ind++, ICMSConstant.UNIT_TRUST_FEED_GROUP_TYPE);
			dbUtil.setLong(ind++, priceFeedId);
			dbUtil.setLong(ind++, paramID);
			dbUtil.setString(ind++, feed.getFIIndicator());
			dbUtil.setString(ind++, feed.getBoardType());
			dbUtil.setString(ind++, feed.getShareStatus());
			rowUpdated = dbUtil.executeUpdate();
			DefaultLogger.debug(this, "rowInserted in Stage CRP = " + rowUpdated);
		}
		if (rs != null) {
			rs.close();
		}
		return rowUpdated;
	}

	{
		// Insert Credit Risk Param

		/*
		 * dbUtil.setSQL(getCreditRiskParam()); ind = 1;
		 * DefaultLogger.info(this, " **** getCreditRiskParam " +
		 * getCreditRiskParam() + priceFeedId); dbUtil.setLong (ind++,
		 * priceFeedId); rs = dbUtil.executeQuery(); if (rs.next()) { //
		 * PriceFeedID exists in Credit Risk Param update Credit Risk Param
		 * dbUtil.setSQL(updateCreditRiskParam()); ind = 1;
		 * DefaultLogger.info(this, " **** updateCreditRiskParam " +
		 * updateCreditRiskParam() + priceFeedId);
		 * dbUtil.setString(ind++,feed.getFIIndicator());
		 * dbUtil.setString(ind++,feed.getBoardType());
		 * dbUtil.setString(ind++,feed.getShareStatus());
		 * 
		 * dbUtil.setLong (ind++,priceFeedId); rowUpdated =
		 * dbUtil.executeUpdate(); DefaultLogger.debug(this,
		 * "rowUpdated in CRP = "+rowUpdated);
		 * 
		 * DefaultLogger.debug(this, "=== CRP Trx Query" + getTrxDetails() +
		 * grpId); trxStgRef = null; trxStatus = null;
		 * dbUtil.setSQL(getTrxDetails());
		 * dbUtil.setString(1,ICMSConstant.INSTANCE_CREDIT_RISK_PARAM_SHARE_COUNTER
		 * ); dbUtil.setString(2,""+grpId); rs = dbUtil.executeQuery();
		 * if(rs.next()){ trxStgRef = rs.getString(1); trxStatus =
		 * rs.getString(2); } DefaultLogger.debug(this, "CRP trxStgRef " +
		 * trxStgRef + " trxStatus " + trxStatus); if (rs != null) rs.close();
		 * // if REF and STAGE REF both exists in trx table..(the group is
		 * maintained earlier but not ric )
		 * 
		 * // For first time to set Staging Reference ID in transaction as DB
		 * setip does not set staging ref ID // Setting Stg ref ID = Ref ID =
		 * grpId For CRP if (trxStgRef == null || trxStgRef.trim().equals("")) {
		 * // If transaction does not exist, Insert
		 * dbUtil.setSQL(feedUpdateEDOTrxSql()); ind = 1;
		 * DefaultLogger.debug(this, feedUpdateEDOTrxSql() + grpId);
		 * dbUtil.setLong (ind++, grpId); dbUtil.setString(ind++,
		 * ICMSConstant.INSTANCE_CREDIT_RISK_PARAM_UNIT_TRUST); dbUtil.setLong
		 * (ind++, grpId); rowUpdated = dbUtil.executeUpdate();
		 * DefaultLogger.debug(this, "rowUpdated = "+rowUpdated);
		 * 
		 * } // end stg ref is null
		 * 
		 * } else { //Insert Credit Risk Param seq = new DB2Sequencer(); long
		 * paramID =Long.parseLong(seq.getSeqNum(ICMSConstant.
		 * SEQUENCE_CMS_CREDIT_RISK_PARAM_SEQ));
		 * 
		 * dbUtil.setSQL(stockFeedInsertCreditRiskParam()); ind =1;
		 * DefaultLogger.info(this, " **** stockFeedInsertCreditRiskParam " +
		 * stockFeedInsertCreditRiskParam() + priceFeedId); dbUtil.setLong
		 * (ind++,paramID);
		 * dbUtil.setString(ind++,ICMSConstant.UNIT_TRUST_FEED_GROUP_TYPE);
		 * dbUtil.setLong (ind++,priceFeedId); dbUtil.setLong (ind++,paramID);
		 * dbUtil.setString(ind++,feed.getFIIndicator());
		 * dbUtil.setString(ind++,feed.getBoardType());
		 * dbUtil.setString(ind++,feed.getShareStatus()); rowUpdated =
		 * dbUtil.executeUpdate(); DefaultLogger.debug(this,
		 * "rowInserted in CRP = "+rowUpdated); // Stage CRP seq = new
		 * DB2Sequencer(); paramID =Long.parseLong(seq.getSeqNum(ICMSConstant.
		 * SEQUENCE_CMS_STG_CREDIT_RISK_PARAM_REF_SEQ));
		 * 
		 * dbUtil.setSQL(stockFeedInsertStageCreditRiskParam()); ind = 1;
		 * dbUtil.setLong (ind++,paramID);
		 * dbUtil.setString(ind++,ICMSConstant.UNIT_TRUST_FEED_GROUP_TYPE);
		 * dbUtil.setLong (ind++,priceFeedId); dbUtil.setLong (ind++,paramID);
		 * dbUtil.setString(ind++,feed.getFIIndicator());
		 * dbUtil.setString(ind++,feed.getBoardType());
		 * dbUtil.setString(ind++,feed.getShareStatus()); rowUpdated =
		 * dbUtil.executeUpdate(); DefaultLogger.debug(this,
		 * "rowInserted in Stage CRP = "+rowUpdated); } if (rs != null)
		 * rs.close();
		 */
	}

	private int insertUT(AbstractDBUtil dbUtil, OBFeed feed, long grpId, long priceFeedId) throws Exception { // insert
																												// actual
		// TODO check if there is require to open and close dbUtil
		dbUtil.setSQL(insertUTSql());
		DefaultLogger.debug(this, insertUTSql());
		int ind = 1;
		dbUtil.setLong(ind++, priceFeedId);
		dbUtil.setString(ind++, ICMSConstant.FEED_SHARE_TYPE_UNIT_TRUST);// ICMSConstant
																			// .
																			// UNIT_TRUST_FEED_GROUP_TYPE
																			// )
																			// ;
		dbUtil.setDouble(ind++, feed.getNAV());
		dbUtil.setDouble(ind++, feed.getOffer());
		dbUtil.setString(ind++, feed.getIsinCode());
		dbUtil.setTimestamp(ind++, new java.sql.Timestamp(DateUtil.getDate().getTime()));
		dbUtil.setString(ind++, "SYSTEM");
		dbUtil.setLong(ind++, grpId);
		dbUtil.setLong(ind++, DateUtil.getDate().getTime());
		dbUtil.setLong(ind++, priceFeedId);
		dbUtil.setString(ind++, feed.getCurrencyCode());
		dbUtil.setString(ind++, feed.getDescription());
		dbUtil.setString(ind++, feed.getFundCode());
		dbUtil.setString(ind++, feed.getCountry());

		int rowUpdated = dbUtil.executeUpdate();
		return rowUpdated;
	}

	private int insertUTStage(AbstractDBUtil dbUtil, long priceFeedId) throws Exception {
		dbUtil.setSQL(feedInsertEDOStageSql());
		DefaultLogger.debug(this, feedInsertEDOStageSql());
		dbUtil.setLong(1, priceFeedId);
		int rowUpdated = dbUtil.executeUpdate();
		return rowUpdated;
	}

	private int insertUTHistory(AbstractDBUtil dbUtil, OBFeed feed, long grpId) throws Exception {
		DB2Sequencer seq = new DB2Sequencer();
		long priceFeedId = Long.parseLong(seq.getSeqNum(ICMSConstant.SEQUENCE_FEED_PRICE_FEED_HISTORY));
		// pstmtInsert = new RMDBUtil(this);
		// TODO check if there is require to open and close dbUtil
		dbUtil.setSQL(feedInsertUTHistorySql());
		int ind = 1;
		dbUtil.setLong(ind++, priceFeedId);
		dbUtil.setString(ind++, ICMSConstant.FEED_SHARE_TYPE_UNIT_TRUST);// ICMSConstant
																			// .
																			// UNIT_TRUST_FEED_GROUP_TYPE
																			// )
																			// ;
		dbUtil.setDouble(ind++, feed.getNAV());
		dbUtil.setDouble(ind++, feed.getOffer());
		dbUtil.setString(ind++, feed.getIsinCode());
		dbUtil.setTimestamp(ind++, new java.sql.Timestamp(DateUtil.getDate().getTime()));
		dbUtil.setString(ind++, "SYSTEM");
		dbUtil.setLong(ind++, grpId);
		dbUtil.setLong(ind++, DateUtil.getDate().getTime());
		dbUtil.setLong(ind++, priceFeedId);
		dbUtil.setString(ind++, feed.getCurrencyCode());
		dbUtil.setString(ind++, feed.getDescription());
		dbUtil.setString(ind++, feed.getFundCode());
		dbUtil.setString(ind++, feed.getCountry());

		int rowUpdated = dbUtil.executeUpdate();
		return rowUpdated;
	}

	private int updateCRP_Stock(AbstractDBUtil dbUtil, OBFeed feed, long grpId, long priceFeedId, int i)
			throws Exception {
		int rowUpdated = 0;
		dbUtil.setSQL(getCreditRiskParam());
		int ind = 1;
		DefaultLogger.info(this, " **** CAN getCreditRiskParam " + getCreditRiskParam() + priceFeedId);
		dbUtil.setLong(ind++, priceFeedId);
		ResultSet rs = dbUtil.executeQuery();
		if (rs.next()) {
			// PriceFeedID exists update Credit Risk Param
			DefaultLogger.info(this, " **** CAN updateCreditRiskParam " + updateCreditRiskParam() + priceFeedId);
			dbUtil.setSQL(updateCreditRiskParam());
			ind = 1;
			dbUtil.setString(ind++, feed.getFIIndicator());
			dbUtil.setString(ind++, feed.getBoardType());
			dbUtil.setString(ind++, feed.getShareStatus());

			dbUtil.setLong(ind++, priceFeedId);
			rowUpdated = dbUtil.executeUpdate();
			DefaultLogger.debug(this, "rowUpdated in CRP = " + rowUpdated);

			DefaultLogger.debug(this, "=== CRP Trx Query" + getTrxDetails() + grpId);
			String trxStgRef = null;
			String trxStatus = null;
			dbUtil.setSQL(getTrxDetails());
			dbUtil.setString(1, ICMSConstant.INSTANCE_CREDIT_RISK_PARAM_SHARE_COUNTER);
			dbUtil.setString(2, "" + grpId);
			rs = dbUtil.executeQuery();
			if (rs.next()) {
				trxStgRef = rs.getString(1);
				trxStatus = rs.getString(2);
			}
			DefaultLogger.debug(this, "CRP trxStgRef " + trxStgRef + " trxStatus " + trxStatus);
			if (rs != null) {
				rs.close();
				// if REF and STAGE REF both exists in trx table..(the group is
				// maintained earlier but not ric )
			}

			// For first time to set Staging Reference ID in transaction as DB
			// setip does not set staging ref ID
			// Setting Stg ref ID = Ref ID = grpId
			if ((trxStgRef == null) || trxStgRef.trim().equals("")) { // If
																		// transaction
																		// does
																		// not
																		// exist
																		// ,
																		// Insert
				dbUtil.setSQL(feedUpdateEDOTrxSql());
				ind = 1;
				DefaultLogger.debug(this, feedUpdateEDOTrxSql() + grpId);
				dbUtil.setLong(ind++, grpId);
				dbUtil.setString(ind++, ICMSConstant.INSTANCE_CREDIT_RISK_PARAM_SHARE_COUNTER);
				dbUtil.setLong(ind++, grpId);
				rowUpdated = dbUtil.executeUpdate();
				DefaultLogger.debug(this, "rowUpdated = " + rowUpdated);

			} // end stg ref is null

		}
		else {
			// Insert Credit Risk Param
			DB2Sequencer seq = new DB2Sequencer();
			long paramID = Long.parseLong(seq.getSeqNum(ICMSConstant.SEQUENCE_CMS_CREDIT_RISK_PARAM_SEQ));

			DefaultLogger.info(this, " **** CAN stockFeedInsertCreditRiskParam " + stockFeedInsertCreditRiskParam()
					+ priceFeedId);
			dbUtil.setSQL(stockFeedInsertCreditRiskParam());
			ind = 1;
			dbUtil.setLong(ind++, paramID);
			dbUtil.setString(ind++, ICMSConstant.STOCK_FEED_GROUP_TYPE);
			dbUtil.setLong(ind++, priceFeedId);
			dbUtil.setLong(ind++, paramID);
			dbUtil.setString(ind++, feed.getFIIndicator());
			dbUtil.setString(ind++, feed.getBoardType());
			dbUtil.setString(ind++, feed.getShareStatus());
			rowUpdated = dbUtil.executeUpdate();
			DefaultLogger.debug(this, "rowInserted in CRP = " + rowUpdated);
			// Stage CRP
			seq = new DB2Sequencer();
			paramID = Long.parseLong(seq.getSeqNum(ICMSConstant.SEQUENCE_CMS_STG_CREDIT_RISK_PARAM_REF_SEQ));
			DefaultLogger.info(this, " **** stockFeedInsertStageCreditRiskParam "
					+ stockFeedInsertStageCreditRiskParam() + priceFeedId);
			dbUtil.setSQL(stockFeedInsertStageCreditRiskParam());
			ind = 1;
			dbUtil.setLong(ind++, paramID);
			dbUtil.setString(ind++, ICMSConstant.STOCK_FEED_GROUP_TYPE);
			dbUtil.setLong(ind++, priceFeedId);
			dbUtil.setLong(ind++, paramID);
			dbUtil.setString(ind++, feed.getFIIndicator());
			dbUtil.setString(ind++, feed.getBoardType());
			dbUtil.setString(ind++, feed.getShareStatus());
			rowUpdated = dbUtil.executeUpdate();
			DefaultLogger.debug(this, "rowInserted in Stage CRP = " + rowUpdated);

		}
		if (rs != null) {
			rs.close();
		}
		return rowUpdated;
	}

	{
		/*
		 * // Insert Credit Risk Param dbUtil.setSQL(getCreditRiskParam()); ind
		 * = 1; DefaultLogger.info(this, " **** getCreditRiskParam " +
		 * getCreditRiskParam() + priceFeedId); dbUtil.setLong (ind++,
		 * priceFeedId); rs = dbUtil.executeQuery(); if (rs.next()) { //
		 * PriceFeedID exists in Credit Risk Param update Credit Risk Param
		 * dbUtil.setSQL(updateCreditRiskParam()); ind = 1;
		 * DefaultLogger.info(this, " **** updateCreditRiskParam " +
		 * updateCreditRiskParam() + priceFeedId);
		 * dbUtil.setString(ind++,feed.getFIIndicator());
		 * dbUtil.setString(ind++,feed.getBoardType());
		 * dbUtil.setString(ind++,feed.getShareStatus());
		 * 
		 * dbUtil.setLong (ind++,priceFeedId); rowUpdated =
		 * dbUtil.executeUpdate(); DefaultLogger.debug(this,
		 * "rowUpdated in CRP = "+rowUpdated);
		 * 
		 * DefaultLogger.debug(this, "=== CRP Trx Query" + getTrxDetails() +
		 * grpId); trxStgRef = null; trxStatus = null;
		 * dbUtil.setSQL(getTrxDetails());
		 * dbUtil.setString(1,ICMSConstant.INSTANCE_CREDIT_RISK_PARAM_SHARE_COUNTER
		 * ); dbUtil.setString(2,""+grpId); rs = dbUtil.executeQuery();
		 * if(rs.next()){ trxStgRef = rs.getString(1); trxStatus =
		 * rs.getString(2); } DefaultLogger.debug(this, "CRP trxStgRef " +
		 * trxStgRef + " trxStatus " + trxStatus); if (rs != null) rs.close();
		 * // if REF and STAGE REF both exists in trx table..(the group is
		 * maintained earlier but not ric )
		 * 
		 * // For first time to set Staging Reference ID in transaction as DB
		 * setip does not set staging ref ID // Setting Stg ref ID = Ref ID =
		 * grpId For CRP if (trxStgRef == null || trxStgRef.trim().equals("")) {
		 * // If transaction does not exist, Insert
		 * dbUtil.setSQL(feedUpdateEDOTrxSql()); ind = 1;
		 * DefaultLogger.debug(this, feedUpdateEDOTrxSql() + grpId);
		 * dbUtil.setLong (ind++, grpId); dbUtil.setString(ind++,
		 * ICMSConstant.INSTANCE_CREDIT_RISK_PARAM_SHARE_COUNTER);
		 * dbUtil.setLong (ind++, grpId); rowUpdated = dbUtil.executeUpdate();
		 * DefaultLogger.debug(this, "rowUpdated = "+rowUpdated);
		 * 
		 * } // end stg ref is null
		 * 
		 * } else { //Insert Credit Risk Param seq = new DB2Sequencer(); long
		 * paramID =Long.parseLong(seq.getSeqNum(ICMSConstant.
		 * SEQUENCE_CMS_CREDIT_RISK_PARAM_SEQ));
		 * 
		 * dbUtil.setSQL(stockFeedInsertCreditRiskParam()); ind =1;
		 * DefaultLogger.info(this, " **** stockFeedInsertCreditRiskParam " +
		 * stockFeedInsertCreditRiskParam() + priceFeedId); dbUtil.setLong
		 * (ind++,paramID);
		 * dbUtil.setString(ind++,ICMSConstant.STOCK_FEED_GROUP_TYPE);
		 * dbUtil.setLong (ind++,priceFeedId); dbUtil.setLong (ind++,paramID);
		 * dbUtil.setString(ind++,feed.getFIIndicator());
		 * dbUtil.setString(ind++,feed.getBoardType());
		 * dbUtil.setString(ind++,feed.getShareStatus()); rowUpdated =
		 * dbUtil.executeUpdate(); DefaultLogger.debug(this,
		 * "rowInserted in CRP = "+rowUpdated); // Stage CRP seq = new
		 * DB2Sequencer(); paramID =Long.parseLong(seq.getSeqNum(ICMSConstant.
		 * SEQUENCE_CMS_STG_CREDIT_RISK_PARAM_REF_SEQ));
		 * 
		 * dbUtil.setSQL(stockFeedInsertStageCreditRiskParam()); ind = 1;
		 * dbUtil.setLong (ind++,paramID);
		 * dbUtil.setString(ind++,ICMSConstant.STOCK_FEED_GROUP_TYPE);
		 * dbUtil.setLong (ind++,priceFeedId); dbUtil.setLong (ind++,paramID);
		 * dbUtil.setString(ind++,feed.getFIIndicator());
		 * dbUtil.setString(ind++,feed.getBoardType());
		 * dbUtil.setString(ind++,feed.getShareStatus()); rowUpdated =
		 * dbUtil.executeUpdate(); DefaultLogger.debug(this,
		 * "rowInserted in Stage CRP = "+rowUpdated);
		 * 
		 * } if (rs != null) rs.close();
		 */

	}
}