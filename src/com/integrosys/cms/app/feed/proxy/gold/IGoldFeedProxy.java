package com.integrosys.cms.app.feed.proxy.gold;

import com.integrosys.cms.app.feed.bus.gold.GoldFeedGroupException;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;
import com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

public interface IGoldFeedProxy extends java.io.Serializable {

	/**
	 * Gets the one and only gold feed group.
	 * @return The gold feed group.
	 * @throws GoldFeedGroupException on errors.
	 */
	IGoldFeedGroupTrxValue getGoldFeedGroup() throws GoldFeedGroupException;

	/**
	 * Get the transaction value containing GoldFeedGroup This method will
	 * create a transaction if it is not already present, when this module is
	 * first used by user and system is first setup.
	 */
	IGoldFeedGroupTrxValue getGoldFeedGroup(long groupID) throws GoldFeedGroupException;

	/**
	 * Get the transaction value containing GoldFeedGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IGoldFeedGroupTrxValue
	 */
	IGoldFeedGroupTrxValue getGoldFeedGroupByTrxID(long trxID) throws GoldFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GoldFeedGroup object
	 */
	IGoldFeedGroupTrxValue makerUpdateGoldFeedGroup(ITrxContext anITrxContext, IGoldFeedGroupTrxValue aTrxValue,
			IGoldFeedGroup aFeedGroup) throws GoldFeedGroupException;

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GoldFeedGroup object
	 */
	IGoldFeedGroupTrxValue makerSubmitGoldFeedGroup(ITrxContext anITrxContext, IGoldFeedGroupTrxValue aTrxValue,
			IGoldFeedGroup aFeedGroup) throws GoldFeedGroupException;

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws GoldFeedGroupException
	 */
	IGoldFeedGroupTrxValue makerSubmitRejectedGoldFeedGroup(ITrxContext anITrxContext, IGoldFeedGroupTrxValue aTrxValue)
			throws GoldFeedGroupException;

	/**
	 * This is essentially the same as makerUpdateGoldFeedGroup except that it
	 * triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GoldFeedGroup object
	 */
	IGoldFeedGroupTrxValue makerUpdateRejectedGoldFeedGroup(ITrxContext anITrxContext, IGoldFeedGroupTrxValue aTrxValue)
			throws GoldFeedGroupException;

	/**
	 * Cancels an initiated transaction on a GoldFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GoldFeedGroup object
	 */
	IGoldFeedGroupTrxValue makerCloseRejectedGoldFeedGroup(ITrxContext anITrxContext, IGoldFeedGroupTrxValue aTrxValue)
			throws GoldFeedGroupException;

	/**
	 * Cancels an initiated transaction on a GoldFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GoldFeedGroup object
	 */
	IGoldFeedGroupTrxValue makerCloseDraftGoldFeedGroup(ITrxContext anITrxContext, IGoldFeedGroupTrxValue aTrxValue)
			throws GoldFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GoldFeedGroup object
	 */
	IGoldFeedGroupTrxValue checkerApproveGoldFeedGroup(ITrxContext anITrxContext, IGoldFeedGroupTrxValue aTrxValue)
			throws GoldFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GoldFeedGroup object
	 */

	IGoldFeedGroupTrxValue checkerRejectGoldFeedGroup(ITrxContext anITrxContext, IGoldFeedGroupTrxValue aTrxValue)
			throws GoldFeedGroupException;

	public static final String NO_FEED_GROUP = "no.feed.group";
}
