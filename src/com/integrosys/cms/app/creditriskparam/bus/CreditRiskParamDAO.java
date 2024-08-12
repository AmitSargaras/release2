package com.integrosys.cms.app.creditriskparam.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.creditriskparam.sharecounter.ShareCounterConstants;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * CreditRiskParamDAO
 * 
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class CreditRiskParamDAO {
	private DBUtil dbUtil = null;

	private static final String SELECT_PRICE_FEED_ENTRY = "SELECT \n"
			+ "PARAM_ID, MOA, MAX_CAP, PARAM_REF, param.FEED_ID AS PARAM_FEED_ID, VERSION_TIME, PARAM_TYPE, IS_LIQUID, IS_INT_SUSPEND, STATUS, \n"
			+ "feed.FEED_ID AS PRICE_FEED_ID, RIC, TYPE, COUNTRY, EXCHANGE, UNIT_PRICE, CURRENCY, ISIN_CODE, NAME, IS_BLACKLISTED, IS_SUSPENDED,\n"
			+ "FUND_MANAGER_NAME, LAST_UPDATED_DATE, LAST_UPDATED_BY, TOTAL_OUTSTANDING_UNIT, feed.FEED_GROUP_ID, feed.CMS_VERSION_TIME, OFFER, TICKER, \n"
			+ "FUND_MANAGER_CODE, PRODUCT_CODE, MATURITY_DATE, FEED_REF, RATING, STOCK_CODE, param.IS_FI, param.BOARD_TYPE, \n"
			+ "param.SHARE_STATUS  ,\n "
			+ // param.MAX_COLL_CAP_FI , param.MAX_COLL_CAP_NON_FI ,
				// param.QUOTA_COLL_CAP_FI , param.QUOTA_COLL_CAP_NON_FI ,\n" +
			"feedGroup.GROUP_SUBTYPE , feedGroup.GROUP_STOCK_TYPE, param.IS_ACCEPTABLE AS PARAM_IS_ACCEPTABLE\n"
			+ "FROM \n" + "\tCMS_PRICE_FEED feed \n" + "\tLEFT OUTER JOIN\n" + "\tCMS_CREDIT_RISK_PARAM param \n"
			+ "\tON feed.FEED_ID = param.FEED_ID \n" + "\tINNER JOIN \n" + "\tCMS_FEED_GROUP feedGroup \n"
			+ "\tON feedGroup.FEED_GROUP_ID = feed.FEED_GROUP_ID \n" + "\tWHERE feed.FEED_GROUP_ID =  ? "
			+ "\tORDER BY NAME";

	private static final String SELECT_STAGING_PRICE_FEED_ENTRY = "SELECT "
			+ "PARAM_ID, MOA, MAX_CAP, PARAM_REF, param.FEED_ID AS PARAM_FEED_ID, VERSION_TIME, PARAM_TYPE, IS_LIQUID, IS_INT_SUSPEND, param.STATUS AS STATUS, "
			+ "feed.FEED_ID AS PRICE_FEED_ID, RIC, TYPE, COUNTRY, EXCHANGE, UNIT_PRICE, CURRENCY, ISIN_CODE, NAME, IS_BLACKLISTED, IS_SUSPENDED, "
			+ "FUND_MANAGER_NAME, LAST_UPDATED_DATE, LAST_UPDATED_BY, TOTAL_OUTSTANDING_UNIT, feed.FEED_GROUP_ID, feed.CMS_VERSION_TIME, OFFER, TICKER, \n"
			+ "FUND_MANAGER_CODE, PRODUCT_CODE, MATURITY_DATE, FEED_REF, RATING, STOCK_CODE, param.IS_FI, param.BOARD_TYPE, \n"
			+ "param.SHARE_STATUS, param.IS_ACCEPTABLE AS PARAM_IS_ACCEPTABLE,\n "
			+ "feedGroup.GROUP_SUBTYPE AS GROUP_SUBTYPE, feedGroup.GROUP_STOCK_TYPE AS GROUP_STOCK_TYPE \n "
			+
			// " , param.MAX_COLL_CAP_FI , param.MAX_COLL_CAP_NON_FI , param.QUOTA_COLL_CAP_FI , param.QUOTA_COLL_CAP_NON_FI "
			// +
			"FROM CMS_PRICE_FEED feed INNER JOIN CMS_FEED_GROUP feedGroup \n "
			+ "ON feedGroup.FEED_GROUP_ID = feed.FEED_GROUP_ID "
			+ "INNER JOIN TRANSACTION trx ON feed.FEED_GROUP_ID = trx.REFERENCE_ID "
			+ "LEFT OUTER JOIN CMS_STAGE_CREDIT_RISK_PARAM param ON feed.FEED_ID = param.FEED_ID "
			+ "WHERE trx.STAGING_REFERENCE_ID = param.PARAM_REF AND feed.FEED_GROUP_ID = ? " + "\tORDER BY NAME";

    private static final String SELECT_EXEMPT_FACILITY_TRX_ID = "select transaction_id from transaction " +
    		" where transaction_type='EXEMPT_FACILITY'";    

	/**
	 * search credit risk param group
	 * 
	 * @param groupType String
	 * @param groupSubType String
	 * @param groupStockType String
	 * @throws com.integrosys.base.businfra.search.SearchDAOException on error
	 *         checking the ddn
	 */
	public List getCreditRiskParamGroup(String groupType, String groupSubType, String groupStockType)
			throws SearchDAOException {
		List groupList = new ArrayList();
		try {
			dbUtil = new DBUtil();

			String sqlSelect = "SELECT * FROM CMS_FEED_GROUP WHERE GROUP_TYPE = ? ";

			if ((groupSubType != null) && !("").equals(groupSubType)) {
				sqlSelect += "AND GROUP_SUBTYPE = '" + groupSubType + "' ";
			}

			if ((groupStockType != null) && !("").equals(groupStockType)) {
				sqlSelect += "AND GROUP_STOCK_TYPE = '" + groupStockType + "' ";
			}

			sqlSelect += " ORDER BY GROUP_SUBTYPE";

			//DefaultLogger.debug(this, "Sql Select : " + sqlSelect);
			DefaultLogger.debug(this, "groupType parameter : " + groupType);
			DefaultLogger.debug(this, "groupSubType parameter : " + groupSubType);
			DefaultLogger.debug(this, "groupStockType parameter : " + groupStockType);

			dbUtil.setSQL(sqlSelect);
			dbUtil.setString(1, groupType);

			ResultSet rs = dbUtil.executeQuery();

			while (rs.next()) {
				ICreditRiskParamGroup obGroup = new OBCreditRiskParamGroup();
				obGroup.setCreditRiskParamGroupID(rs.getLong("FEED_GROUP_ID"));
				if (!isEmpty(rs.getString("GROUP_TYPE"))) {
					obGroup.setType(rs.getString("GROUP_TYPE"));
				}
				if (!isEmpty(rs.getString("GROUP_SUBTYPE"))) {
					obGroup.setSubType(rs.getString("GROUP_SUBTYPE").trim());
				}
				if (!isEmpty(rs.getString("GROUP_STOCK_TYPE"))) {
					obGroup.setStockType(rs.getString("GROUP_STOCK_TYPE"));
				}
				groupList.add(obGroup);
			}

			rs.close();

			return groupList;
		}
		catch (Exception e) {
			throw new SearchDAOException("Exception at getCreditRiskParamGroup", e);
		}
		finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				// throw new SearchDAOException
				// ("SQLException in closing dbUtil at getCreditRiskParamGroup",
				// e);
			}
		}
	}

	public ICreditRiskParamGroup getCreditRiskParams(long groupFeedId) throws SearchDAOException {
		DefaultLogger.debug(this, "In getCreditRiskParams");

		ICreditRiskParamGroup paramGroup = new OBCreditRiskParamGroup();
		List groupList = new ArrayList();
		DefaultLogger.debug(this, "groupFeedId in DAO :" + groupFeedId);
		try {
			dbUtil = new DBUtil();
			String sqlSelect = SELECT_PRICE_FEED_ENTRY;

			DefaultLogger.debug(this, "Sql Select : " + sqlSelect);
			dbUtil.setSQL(sqlSelect);
			dbUtil.setLong(1, groupFeedId);

			ResultSet rs = dbUtil.executeQuery();
			SequenceManager seq = new SequenceManager();

			while (rs.next()) {
				OBCreditRiskParam obCreditRiskParam = new OBCreditRiskParam();

				// ICreditRiskParam
				obCreditRiskParam.setParameterId(rs.getLong("PARAM_ID"));
				obCreditRiskParam.setMarginOfAdvance(rs.getDouble("MOA"));
				obCreditRiskParam.setMaximumCap(rs.getDouble("MAX_CAP"));
				obCreditRiskParam.setParameterRef(rs.getLong("PARAM_REF"));
				obCreditRiskParam.setFeedId(rs.getLong("PARAM_FEED_ID"));
				obCreditRiskParam.setVersionTime(rs.getLong("VERSION_TIME"));

				if (obCreditRiskParam.getFeedId() <= 0) {
					obCreditRiskParam.setFeedId(Long.parseLong(seq.getSeqNum(
							ICMSConstant.SEQUENCE_CMS_CREDIT_RISK_PARAM_SEQ, true)));
				}

				if (!isEmpty(rs.getString("PARAM_TYPE"))) {
					obCreditRiskParam.setParameterType(rs.getString("PARAM_TYPE"));
				}
				if (!isEmpty(rs.getString("IS_LIQUID"))) {
					obCreditRiskParam.setIsLiquid(rs.getString("IS_LIQUID"));
				}
				if (!isEmpty(rs.getString("IS_INT_SUSPEND"))) {
					obCreditRiskParam.setIsIntSuspend(rs.getString("IS_INT_SUSPEND"));
				}
				if (!isEmpty(rs.getString("STATUS"))) {
					obCreditRiskParam.setStatus(rs.getString("STATUS"));
				}

				// IPriceFeedEntry
				obCreditRiskParam.setOffer(rs.getDouble("OFFER"));
				obCreditRiskParam.setCreditRiskParamEntryID(rs.getLong("PRICE_FEED_ID"));
				obCreditRiskParam.setCreditRiskParamEntryRef(rs.getLong("FEED_REF"));
				obCreditRiskParam.setPriceFeedversionTime(rs.getLong("CMS_VERSION_TIME"));
				obCreditRiskParam.setUnitPrice(rs.getDouble("UNIT_PRICE"));

				obCreditRiskParam.setTotalOutstandingUnit(rs.getLong("TOTAL_OUTSTANDING_UNIT"));
				obCreditRiskParam.setFeedGroupId(rs.getLong("FEED_GROUP_ID"));

				// obCreditRiskParam.setMaxCollCapFi ( rs.getDouble (
				// "MAX_COLL_CAP_FI" ) ) ;
				// obCreditRiskParam.setMaxCollCapNonFi ( rs.getDouble (
				// "MAX_COLL_CAP_NON_FI" ) ) ;
				// obCreditRiskParam.setQuotaCollCapFi ( rs.getDouble (
				// "QUOTA_COLL_CAP_FI" ) ) ;
				// obCreditRiskParam.setQuotaCollCapNonFi ( rs.getDouble (
				// "QUOTA_COLL_CAP_NON_FI" ) ) ;

				if (!isEmpty(rs.getString("TICKER"))) {
					obCreditRiskParam.setTicker(rs.getString("TICKER"));
				}
				if (!isEmpty(rs.getString("RIC"))) {
					obCreditRiskParam.setRic(rs.getString("RIC"));
				}
				if (!isEmpty(rs.getString("TYPE"))) {
					obCreditRiskParam.setType(rs.getString("TYPE"));
				}
				if (!isEmpty(rs.getString("COUNTRY"))) {
					obCreditRiskParam.setCountryCode(rs.getString("COUNTRY"));
				}
				if (!isEmpty(rs.getString("EXCHANGE"))) {
					obCreditRiskParam.setExchange(rs.getString("EXCHANGE").trim());
				}
				if (!isEmpty(rs.getString("CURRENCY"))) {
					obCreditRiskParam.setCurrencyCode(rs.getString("CURRENCY"));
				}
				if (!isEmpty(rs.getString("ISIN_CODE"))) {
					obCreditRiskParam.setIsinCode(rs.getString("ISIN_CODE"));
				}
				if (!isEmpty(rs.getString("NAME"))) {
					obCreditRiskParam.setName(rs.getString("NAME"));
				}
				if (!isEmpty(rs.getString("FUND_MANAGER_NAME"))) {
					obCreditRiskParam.setFundManagerName(rs.getString("FUND_MANAGER_NAME"));
				}
				if (rs.getTimestamp("LAST_UPDATED_DATE") != null) {
					obCreditRiskParam.setLastUpdatedDate(rs.getTimestamp("LAST_UPDATED_DATE"));
				}
				if (!isEmpty(rs.getString("IS_BLACKLISTED"))) {
					obCreditRiskParam.setBlackListed(rs.getString("IS_BLACKLISTED"));
				}
				if (!isEmpty(rs.getString("IS_SUSPENDED"))) {
					obCreditRiskParam.setSuspended(rs.getString("IS_SUSPENDED"));
				}

				if (!isEmpty(rs.getString("LAST_UPDATED_BY"))) {
					obCreditRiskParam.setLastUpdatedBy(rs.getString("LAST_UPDATED_BY"));
				}
				if (!isEmpty(rs.getString("FUND_MANAGER_CODE"))) {
					obCreditRiskParam.setFundManagerCode(rs.getString("FUND_MANAGER_CODE"));
				}
				if (!isEmpty(rs.getString("PRODUCT_CODE"))) {
					obCreditRiskParam.setProductCode(rs.getString("PRODUCT_CODE"));
				}
				if (rs.getTimestamp("MATURITY_DATE") != null) {
					obCreditRiskParam.setMaturityDate(rs.getTimestamp("MATURITY_DATE"));
				}
				if (!isEmpty(rs.getString("RATING"))) {
					obCreditRiskParam.setRating(rs.getString("RATING"));
				}
				// if(!isEmpty (rs.getString ("STOCK_TYPE")))
				// {
				// obCreditRiskParam.setStockType (rs.getString ("STOCK_TYPE"));
				// }

				if (!isEmpty(rs.getString("PARAM_IS_ACCEPTABLE"))) {
					obCreditRiskParam.setIsAcceptable(rs.getString("PARAM_IS_ACCEPTABLE"));
				}
				// else if(!isEmpty (rs.getString ("FEED_IS_ACCEPTABLE")))
				// {
				// obCreditRiskParam.setIsAcceptable (rs.getString
				// ("FEED_IS_ACCEPTABLE"));
				// }

				if (!isEmpty(rs.getString("STOCK_CODE"))) {
					obCreditRiskParam.setStockCode(rs.getString("STOCK_CODE"));
				}
				if (!isEmpty(rs.getString("IS_FI"))) {
					obCreditRiskParam.setIsFi(rs.getString("IS_FI"));
				}
				if (!isEmpty(rs.getString("BOARD_TYPE"))) {
					obCreditRiskParam.setParamBoardType(rs.getString("BOARD_TYPE").trim());
					obCreditRiskParam.setBoardType(rs.getString("BOARD_TYPE").trim());

					// DefaultLogger.debug ( this ,
					// "rs.getString(\"BOARD_TYPE\" ) : \"" +
					// rs.getString("BOARD_TYPE" ) +"\"") ;
				}
				if (!isEmpty(rs.getString("SHARE_STATUS"))) {
					obCreditRiskParam.setParamShareStatus(rs.getString("SHARE_STATUS").trim());
					obCreditRiskParam.setShareStatus(rs.getString("SHARE_STATUS").trim());
				}

				if (!isEmpty(rs.getString("GROUP_SUBTYPE"))) {
					paramGroup.setSubType(rs.getString("GROUP_SUBTYPE").trim());
					paramGroup.setSubTypeDescription(CommonDataSingleton.getCodeCategoryLabelByValue(
							ShareCounterConstants.STOCK_EXCHANGE, paramGroup.getSubType()));
				}

				if (!isEmpty(rs.getString("GROUP_STOCK_TYPE"))) {
					paramGroup.setStockType(rs.getString("GROUP_STOCK_TYPE").trim());
					paramGroup.setStockTypeDescription(CommonDataSingleton.getCodeCategoryLabelByValue(
							ShareCounterConstants.SHARE_TYPE, paramGroup.getStockType()));
				}

				groupList.add(obCreditRiskParam);
			}

			rs.close();

			DefaultLogger.debug(this, "groupList.size() : " + groupList.size());
			paramGroup.setFeedEntries((OBCreditRiskParam[]) groupList.toArray(new OBCreditRiskParam[0]));
			DefaultLogger.debug(this, "paramGroup.getFeedEntries().length : " + paramGroup.getFeedEntries().length);

			return paramGroup;
		}
		catch (Exception e) {
			throw new SearchDAOException("Exception at getCreditRiskParamGroup", e);
		}
		finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				// throw new SearchDAOException
				// ("SQLException in closing dbUtil at getCreditRiskParamGroup",
				// e);
			}
		}
	}

	public ICreditRiskParamGroup getStagingCreditRiskParams(long groupFeedId) throws SearchDAOException {
		DefaultLogger.debug(this, "In getStagingCreditRiskParams");

		ICreditRiskParamGroup paramGroup = new OBCreditRiskParamGroup();
		List groupList = new ArrayList();
		DefaultLogger.debug(this, "groupFeedId in DAO :" + groupFeedId);

		try {
			dbUtil = new DBUtil();
			String sqlSelect = SELECT_STAGING_PRICE_FEED_ENTRY;

			DefaultLogger.debug(this, "Sql Select : " + sqlSelect);
			dbUtil.setSQL(sqlSelect);
			dbUtil.setLong(1, groupFeedId);

			ResultSet rs = dbUtil.executeQuery();

			while (rs.next()) {
				OBCreditRiskParam obCreditRiskParam = new OBCreditRiskParam();

				// ICreditRiskParam
				obCreditRiskParam.setParameterId(rs.getLong("PARAM_ID"));
				obCreditRiskParam.setMarginOfAdvance(rs.getDouble("MOA"));
				obCreditRiskParam.setMaximumCap(rs.getDouble("MAX_CAP"));
				obCreditRiskParam.setParameterRef(rs.getLong("PARAM_REF"));
				obCreditRiskParam.setFeedId(rs.getLong("PARAM_FEED_ID"));
				obCreditRiskParam.setVersionTime(rs.getLong("VERSION_TIME"));

				if (!isEmpty(rs.getString("PARAM_TYPE"))) {
					obCreditRiskParam.setParameterType(rs.getString("PARAM_TYPE"));
				}
				if (!isEmpty(rs.getString("IS_LIQUID"))) {
					obCreditRiskParam.setIsLiquid(rs.getString("IS_LIQUID"));
				}
				if (!isEmpty(rs.getString("IS_INT_SUSPEND"))) {
					obCreditRiskParam.setIsIntSuspend(rs.getString("IS_INT_SUSPEND"));
				}
				if (!isEmpty(rs.getString("STATUS"))) {
					obCreditRiskParam.setStatus(rs.getString("STATUS"));
				}

				// IPriceFeedEntry
				obCreditRiskParam.setOffer(rs.getDouble("OFFER"));
				obCreditRiskParam.setCreditRiskParamEntryID(rs.getLong("PRICE_FEED_ID"));
				obCreditRiskParam.setCreditRiskParamEntryRef(rs.getLong("FEED_REF"));
				obCreditRiskParam.setPriceFeedversionTime(rs.getLong("CMS_VERSION_TIME"));
				obCreditRiskParam.setUnitPrice(rs.getDouble("UNIT_PRICE"));

				obCreditRiskParam.setTotalOutstandingUnit(rs.getLong("TOTAL_OUTSTANDING_UNIT"));
				obCreditRiskParam.setFeedGroupId(rs.getLong("FEED_GROUP_ID"));

				// obCreditRiskParam.setMaxCollCapFi ( rs.getDouble (
				// "MAX_COLL_CAP_FI" ) ) ;
				// obCreditRiskParam.setMaxCollCapNonFi ( rs.getDouble (
				// "MAX_COLL_CAP_NON_FI" ) ) ;
				// obCreditRiskParam.setQuotaCollCapFi ( rs.getDouble (
				// "QUOTA_COLL_CAP_FI" ) ) ;
				// obCreditRiskParam.setQuotaCollCapNonFi ( rs.getDouble (
				// "QUOTA_COLL_CAP_NON_FI" ) ) ;

				if (!isEmpty(rs.getString("TICKER"))) {
					obCreditRiskParam.setTicker(rs.getString("TICKER"));
				}
				if (!isEmpty(rs.getString("RIC"))) {
					obCreditRiskParam.setRic(rs.getString("RIC"));
				}
				if (!isEmpty(rs.getString("TYPE"))) {
					obCreditRiskParam.setType(rs.getString("TYPE"));
				}
				if (!isEmpty(rs.getString("COUNTRY"))) {
					obCreditRiskParam.setCountryCode(rs.getString("COUNTRY"));
				}
				if (!isEmpty(rs.getString("EXCHANGE"))) {
					obCreditRiskParam.setExchange(rs.getString("EXCHANGE"));
				}
				if (!isEmpty(rs.getString("CURRENCY"))) {
					obCreditRiskParam.setCurrencyCode(rs.getString("CURRENCY"));
				}
				if (!isEmpty(rs.getString("ISIN_CODE"))) {
					obCreditRiskParam.setIsinCode(rs.getString("ISIN_CODE"));
				}
				if (!isEmpty(rs.getString("NAME"))) {
					obCreditRiskParam.setName(rs.getString("NAME"));
				}
				if (!isEmpty(rs.getString("FUND_MANAGER_NAME"))) {
					obCreditRiskParam.setFundManagerName(rs.getString("FUND_MANAGER_NAME"));
				}
				if (rs.getTimestamp("LAST_UPDATED_DATE") != null) {
					obCreditRiskParam.setLastUpdatedDate(rs.getTimestamp("LAST_UPDATED_DATE"));
				}
				if (!isEmpty(rs.getString("IS_BLACKLISTED"))) {
					obCreditRiskParam.setBlackListed(rs.getString("IS_BLACKLISTED"));
				}
				if (!isEmpty(rs.getString("IS_SUSPENDED"))) {
					obCreditRiskParam.setSuspended(rs.getString("IS_SUSPENDED"));
				}

				if (!isEmpty(rs.getString("LAST_UPDATED_BY"))) {
					obCreditRiskParam.setLastUpdatedBy(rs.getString("LAST_UPDATED_BY"));
				}
				if (!isEmpty(rs.getString("FUND_MANAGER_CODE"))) {
					obCreditRiskParam.setFundManagerCode(rs.getString("FUND_MANAGER_CODE"));
				}
				if (!isEmpty(rs.getString("PRODUCT_CODE"))) {
					obCreditRiskParam.setProductCode(rs.getString("PRODUCT_CODE"));
				}
				if (rs.getTimestamp("MATURITY_DATE") != null) {
					obCreditRiskParam.setMaturityDate(rs.getTimestamp("MATURITY_DATE"));
				}
				if (!isEmpty(rs.getString("RATING"))) {
					obCreditRiskParam.setRating(rs.getString("RATING"));
				}
				// if(!isEmpty (rs.getString ("STOCK_TYPE")))
				// {
				// obCreditRiskParam.setStockType (rs.getString ("STOCK_TYPE"));
				// }

				if (!isEmpty(rs.getString("PARAM_IS_ACCEPTABLE"))) {
					obCreditRiskParam.setIsAcceptable(rs.getString("PARAM_IS_ACCEPTABLE"));
				}
				// else if(!isEmpty (rs.getString ("FEED_IS_ACCEPTABLE")))
				// {
				// obCreditRiskParam.setIsAcceptable (rs.getString
				// ("FEED_IS_ACCEPTABLE"));
				// }

				if (!isEmpty(rs.getString("STOCK_CODE"))) {
					obCreditRiskParam.setStockCode(rs.getString("STOCK_CODE"));
				}
				if (!isEmpty(rs.getString("IS_FI"))) {
					obCreditRiskParam.setIsFi(rs.getString("IS_FI"));
				}
				if (!isEmpty(rs.getString("BOARD_TYPE"))) {
					obCreditRiskParam.setParamBoardType(rs.getString("BOARD_TYPE").trim());
					obCreditRiskParam.setBoardType(rs.getString("BOARD_TYPE").trim());
				}
				if (!isEmpty(rs.getString("SHARE_STATUS"))) {
					obCreditRiskParam.setParamShareStatus(rs.getString("SHARE_STATUS").trim());
					obCreditRiskParam.setShareStatus(rs.getString("SHARE_STATUS").trim());
				}

				if (!isEmpty(rs.getString("GROUP_SUBTYPE"))) {
					paramGroup.setSubType(rs.getString("GROUP_SUBTYPE").trim());
					paramGroup.setSubTypeDescription(CommonDataSingleton.getCodeCategoryLabelByValue(
							ShareCounterConstants.STOCK_EXCHANGE, paramGroup.getSubType()));
				}

				if (!isEmpty(rs.getString("GROUP_STOCK_TYPE"))) {
					paramGroup.setStockType(rs.getString("GROUP_STOCK_TYPE").trim());
					paramGroup.setStockTypeDescription(CommonDataSingleton.getCodeCategoryLabelByValue(
							ShareCounterConstants.SHARE_TYPE, paramGroup.getStockType()));
				}

				groupList.add(obCreditRiskParam);
			}

			rs.close();

			DefaultLogger.debug(this, "groupList.size() : " + groupList.size());
			paramGroup.setFeedEntries((OBCreditRiskParam[]) groupList.toArray(new OBCreditRiskParam[0]));
			DefaultLogger.debug(this, "paramGroup.getFeedEntries().length : " + paramGroup.getFeedEntries().length);

			return paramGroup;
		}
		catch (Exception e) {
			throw new SearchDAOException("Exception at getCreditRiskParamGroup", e);
		}
		finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				// throw new SearchDAOException
				// ("SQLException in closing dbUtil at getCreditRiskParamGroup",
				// e);
			}
		}
	}

	private boolean isEmpty(String aValue) {
		return (aValue == null) || (aValue.trim().length() <= 0);
		// return false;
		// return true;
	}

   public String getExemptFacilityTrxID () throws SearchDAOException
    {
        DefaultLogger.debug (this, "In getExemptFacilityTrxID" );

        try {
            dbUtil = new DBUtil();
            DefaultLogger.debug(this, "Sql Select : " + SELECT_EXEMPT_FACILITY_TRX_ID);
            dbUtil.setSQL(SELECT_EXEMPT_FACILITY_TRX_ID);

            ResultSet rs = dbUtil.executeQuery();

            while (rs.next()) {
                String trxId = rs.getString(1);
                return trxId;
            }
        } catch (SQLException e) {
             throw new SearchDAOException ("Exception at getExemptFacilityTrxID", e);
        } catch (DBConnectionException e) {
             throw new SearchDAOException ("Exception at getExemptFacilityTrxID", e);
        } catch (NoSQLStatementException e) {
             throw new SearchDAOException ("Exception at getExemptFacilityTrxID", e);
        }
        return null;
    }    
}
