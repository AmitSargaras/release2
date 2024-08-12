/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/stockindex/IStockIndexFeedProxy.java,v 1.6 2003/09/12 09:24:00 btchng Exp $
 */
package com.integrosys.cms.app.feed.proxy.stockindex;

import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedEntry;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedGroup;
import com.integrosys.cms.app.feed.bus.stockindex.StockIndexFeedEntryException;
import com.integrosys.cms.app.feed.bus.stockindex.StockIndexFeedGroupException;
import com.integrosys.cms.app.feed.trx.stockindex.IStockIndexFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/09/12 09:24:00 $ Tag: $Name: $
 */
public interface IStockIndexFeedProxy extends java.io.Serializable {

	/**
	 * Gets the one and only stock index feed group.
	 * @param subType Identifies the subtype of the stock index feed group.
	 * @return The stock index feed group.
	 * @throws com.integrosys.cms.app.feed.bus.stockindex.StockIndexFeedGroupException
	 *         on errors.
	 */
	IStockIndexFeedGroupTrxValue getStockIndexFeedGroup(String subType) throws StockIndexFeedGroupException;

	/**
	 * Get the transaction value containing StockIndexFeedGroup This method will
	 * create a transaction if it is not already present, when this module is
	 * first used by user and system is first setup.
	 */
	IStockIndexFeedGroupTrxValue getStockIndexFeedGroup(long groupID) throws StockIndexFeedGroupException;

	/**
	 * Get the transaction value containing StockIndexFeedGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IStockIndexFeedGroupTrxValue
	 */
	IStockIndexFeedGroupTrxValue getStockIndexFeedGroupByTrxID(long trxID) throws StockIndexFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockIndexFeedGroup object
	 */
	IStockIndexFeedGroupTrxValue makerUpdateStockIndexFeedGroup(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aTrxValue, IStockIndexFeedGroup aFeedGroup)
			throws StockIndexFeedGroupException;

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockIndexFeedGroup object
	 */
	IStockIndexFeedGroupTrxValue makerSubmitStockIndexFeedGroup(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aTrxValue, IStockIndexFeedGroup aFeedGroup)
			throws StockIndexFeedGroupException;

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws StockIndexFeedGroupException
	 */
	IStockIndexFeedGroupTrxValue makerSubmitRejectedStockIndexFeedGroup(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aTrxValue) throws StockIndexFeedGroupException;

	/**
	 * This is essentially the same as makerUpdateStockIndexFeedGroup except
	 * that it triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockIndexFeedGroup object
	 */
	IStockIndexFeedGroupTrxValue makerUpdateRejectedStockIndexFeedGroup(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aTrxValue) throws StockIndexFeedGroupException;

	/**
	 * Cancels an initiated transaction on a StockIndexFeedGroup to return it to
	 * last 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockIndexFeedGroup object
	 */
	IStockIndexFeedGroupTrxValue makerCloseRejectedStockIndexFeedGroup(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aTrxValue) throws StockIndexFeedGroupException;

	/**
	 * Cancels an initiated transaction on a StockIndexFeedGroup to return it to
	 * last 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockIndexFeedGroup object
	 */
	IStockIndexFeedGroupTrxValue makerCloseDraftStockIndexFeedGroup(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aTrxValue) throws StockIndexFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockIndexFeedGroup object
	 */
	IStockIndexFeedGroupTrxValue checkerApproveStockIndexFeedGroup(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aTrxValue) throws StockIndexFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockIndexFeedGroup object
	 */

	IStockIndexFeedGroupTrxValue checkerRejectStockIndexFeedGroup(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aTrxValue) throws StockIndexFeedGroupException;

	/**
	 * Gets the feed entry by ric.
	 * @param ric The RIC.
	 * @return The feed entry or <code>null</code>.
	 * @throws com.integrosys.cms.app.feed.bus.stockindex.StockIndexFeedEntryException
	 */
	IStockIndexFeedEntry getStockIndexFeedEntryByRic(String ric) throws StockIndexFeedEntryException;

	public static final String NO_FEED_GROUP = "no.feed.group";

}
