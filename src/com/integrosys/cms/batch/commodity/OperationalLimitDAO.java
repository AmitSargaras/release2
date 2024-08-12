///*
// * Copyright Integro Technologies Pte Ltd
// * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/commodity/OperationalLimitDAO.java,v 1.4 2006/05/16 07:59:02 hmbao Exp $
// */
//package com.integrosys.cms.batch.commodity;
//
//import com.integrosys.base.techinfra.dbsupport.RMDBUtil;
//import com.integrosys.base.techinfra.dbsupport.RMConnectionManager;
//import com.integrosys.base.techinfra.dbsupport.ConnectionManagerFactory;
//import com.integrosys.base.techinfra.logger.DefaultLogger;
//import com.integrosys.base.businfra.search.SearchDAOException;
//import com.integrosys.base.businfra.currency.Amount;
//import com.integrosys.base.businfra.currency.CurrencyCode;
//import com.integrosys.cms.app.commodity.deal.bus.ICommodityDealDAOConstants;
//import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
//import com.integrosys.cms.app.commodity.deal.bus.OBCommodityDeal;
//import com.integrosys.cms.app.commodity.deal.bus.finance.OBSettlement;
//import com.integrosys.cms.app.commodity.deal.bus.finance.ISettlement;
//import com.integrosys.cms.app.common.constant.ICMSConstant;
//import com.integrosys.cms.batch.common.SCBJDBCConnectionManager;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.math.BigDecimal;
//
///**
// * DAO for operational limit.
// *
// * @author  $Author: hmbao $<br>
// * @version $Revision: 1.4 $
// * @since   $Date: 2006/05/16 07:59:02 $
// * Tag:     $Name:  $
// */
//public class OperationalLimitDAO implements ICommodityDealDAOConstants
//{
//    private RMConnectionManager rmCon = null;
//    private final static String SELECT_DEAL_SETTLE = "select CMS_CMDT_DEAL.DEAL_ID, CMS_LSP_APPR_LMTS_ID, ORIG_FACE_AMT, ORIG_FACE_AMT_CCY, FINANCE_PCT, SETTLEMENT_ID, PAYMENT_AMT, PAYMENT_CCY, CMS_SETTLEMENT.STATUS from CMS_CMDT_DEAL, CMS_SETTLEMENT where CMS_CMDT_DEAL.STATUS = 'ACTIVE' and CMS_CMDT_DEAL.DEAL_ID = CMS_SETTLEMENT.DEAL_ID(+)";
//    private HashMap limitDealMap = new HashMap();
//
//
//    /**
//     * Get actual commodity deals to calculate operational limit.
//     *
//     * @return ICommodityDeal[]
//     * @throws SearchDAOException on error getting the deals
//     */
//    public ICommodityDeal[] getCommodityDeals() throws SearchDAOException
//    {
//        RMDBUtil dbUtil = null;
//
//        try {
//            if (rmCon == null)
//                rmCon = (SCBJDBCConnectionManager) ConnectionManagerFactory.createConnectionManager(this);
//            dbUtil = new RMDBUtil(rmCon);
//            String sql = SELECT_DEAL_SETTLE;
//            dbUtil.setSQL (sql);
//            ResultSet rs = dbUtil.executeQuery();
//            HashMap dealMap = new HashMap();
//            processCommodityDeals (rs, dealMap);
//            return (ICommodityDeal[]) dealMap.values().toArray (new ICommodityDeal[0]);
//        }
//        catch(Exception e) {
//            DefaultLogger.error (this, "Caught Exception in getCommodityDeals!", e);
//            throw new SearchDAOException ("Caught Exception in getCommodityDeals", e);
//        }
//        finally {
//            finalize (dbUtil);
//        }
//    }
//
//    /**
//     * Get actual commodity deals to calculate operational limit.
//     *
//     * @return ICommodityDeal[]
//     * @throws SearchDAOException on error getting the deals
//     */
//    public HashMap getLimitDealMap() throws SearchDAOException
//    {
//        RMDBUtil dbUtil = null;
//
//        try {
//            if (rmCon == null)
//                rmCon = (SCBJDBCConnectionManager) ConnectionManagerFactory.createConnectionManager(this);
//            dbUtil = new RMDBUtil(rmCon);
//            String sql = SELECT_DEAL_SETTLE;
//            dbUtil.setSQL (sql);
//            ResultSet rs = dbUtil.executeQuery();
//            HashMap dealMap = new HashMap();
//            processCommodityDeals (rs, dealMap);
//            return limitDealMap;
//        }
//        catch(Exception e) {
//            DefaultLogger.error (this, "Caught Exception in getCommodityDeals!", e);
//            throw new SearchDAOException ("Caught Exception in getCommodityDeals", e);
//        }
//        finally {
//            finalize (dbUtil);
//        }
//    }
//
//    /**
//     * Helper method to process the result set of commodity deals.
//     *
//     * @param rs result set
//     * @param dealMap a hashmap of ICommodityDeal objects with deal id as its key
//     * @throws SQLException on error processing the result set
//     */
//    private void processCommodityDeals (ResultSet rs, HashMap dealMap)
//        throws SQLException
//    {
//        HashMap settleMap = new HashMap();
//        ArrayList dealList = new ArrayList();
//
//        while (rs.next())
//        {
//            Long dealID = new Long (rs.getLong (DEAL_ID));
//            Long limitID = new Long (rs.getLong (DEAL_LIMIT_ID));
//
//            ICommodityDeal deal = (ICommodityDeal) dealMap.get (dealID);
//            if (deal == null) {
//                deal = new OBCommodityDeal();
//                deal.setCommodityDealID (dealID.longValue());
//                deal.setOrigFaceValue ( getAmount (rs.getBigDecimal (DEAL_FACE_VALUE), rs.getString (DEAL_FACE_VALUE_CCY)) );
//                BigDecimal pct = rs.getBigDecimal (DEAL_FINANCING_PCT);
//                if (pct != null)
//                    deal.setFinancingPct (pct.doubleValue());
//
//                deal.setLimitID (limitID.longValue());
//                dealMap.put (dealID, deal);
//
//                dealList = (ArrayList) limitDealMap.get (limitID);
//                if (dealList == null) {
//                    dealList = new ArrayList();
//                }
//                dealList.add (deal);
//                limitDealMap.put (limitID, dealList);
//            }
//
//            ArrayList settleList = (ArrayList) settleMap.get (dealID);
//            if (settleList == null) {
//                settleList = new ArrayList();
//            }
//
//            BigDecimal settleID = rs.getBigDecimal (SETTLE_SETTLEMENT_ID);
//            settleMap.put (dealID, settleList);
//            if (settleID != null)
//            {
//                String status = rs.getString (SETTLE_STATUS);
//                if (status != null && status.equals (ICMSConstant.STATE_DELETED))
//                    continue;
//
//                OBSettlement settle = new OBSettlement();
//                settle.setStatus (status);
//                settle.setSettlementID (settleID.longValue());
//                settle.setPaymentAmt ( getAmount (rs.getBigDecimal (SETTLE_PAYMENT), rs.getString (SETTLE_PAYMENT_CCY)) );
//                settleList.add (settle);
//            }
//        }
//        rs.close();
//
//        Iterator i = dealMap.keySet().iterator();
//        while (i.hasNext()) {
//            Long dealID = (Long) i.next();
//            ICommodityDeal deal = (ICommodityDeal) dealMap.get (dealID);
//            ArrayList cashList = (ArrayList) settleMap.get (dealID);
//            deal.setSettlements ((ISettlement[]) cashList.toArray (new ISettlement[0]));
//        }
//    }
//
//    /**
//     * Helper method to get amount object given the amount value and its currency.
//     *
//     * @param amt amount value
//     * @param ccy currency code
//     * @return Amount object
//     */
//    private Amount getAmount (BigDecimal amt, String ccy)
//    {
//        if (amt != null && ccy != null) {
//            return new Amount (amt, new CurrencyCode (ccy));
//        }
//        return null;
//    }
//
//    /**
//     * Method to rollback transaction.
//     *
//     * @throws SearchDAOException on error rollback the transaction
//     */
//    public void rollback() throws SearchDAOException
//    {
//        rollback (rmCon);
//    }
//
//    /**
//     * Helper method to rollback transaction.
//     *
//     * @param con connection manager
//     * @throws SearchDAOException on error rollback the transaction
//     */
//    private void rollback (RMConnectionManager con)
//        throws SearchDAOException
//    {
//        try {
//            if (con != null)
//                con.rollbackConnection (this);
//            else
//                DefaultLogger.info (this, "RMConnectionManager is NULL when rollback");
//        }
//        catch (Exception e) {
//            throw new SearchDAOException ("Erorr in rollback the transaction!");
//        }
//    }
//
//    /**
//     * Method to rollback transaction.
//     *
//     * @throws SearchDAOException on error rollback the transaction
//     */
//    public void commit() throws SearchDAOException
//    {
//        try {
//            if (rmCon != null)
//                rmCon.commitConnection (this);
//            else
//                DefaultLogger.info (this, "RMConnectionManager is null for committing transaction.");
//        }
//        catch (Exception e) {
//            throw new SearchDAOException ("Error in commit the transaction");
//        }
//    }
//
//    /**
//     * Helper method to clean up database resources.
//     *
//     * @param dbUtil database utility object
//     * @throws SearchDAOException error in cleaning up DB resources
//     */
//    private static void finalize (RMDBUtil dbUtil)
//        throws SearchDAOException
//    {
//        try {
//            if (dbUtil != null)
//                dbUtil.close();
//        } catch (Exception e) {
//            throw new SearchDAOException ("Error in cleaning up DB resources: " + e.toString());
//        }
//    }
//}
