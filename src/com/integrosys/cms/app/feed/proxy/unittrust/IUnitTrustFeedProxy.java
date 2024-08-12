/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/unittrust/IUnitTrustFeedProxy.java,v 1.6 2005/07/29 03:29:21 lyng Exp $
 */
package com.integrosys.cms.app.feed.proxy.unittrust;

import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedEntry;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup;
import com.integrosys.cms.app.feed.bus.unittrust.UnitTrustFeedEntryException;
import com.integrosys.cms.app.feed.bus.unittrust.UnitTrustFeedGroupException;
import com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/07/29 03:29:21 $ Tag: $Name: $
 */
public interface IUnitTrustFeedProxy extends java.io.Serializable {
	/**
	 * Get actual unit price stock feed price group.
	 * 
	 * @param subType unit trust country
	 * @return unit trust feed group
	 * @throws UnitTrustFeedGroupException on errors getting the unit trust feed
	 *         group
	 */
	public IUnitTrustFeedGroup getActualUnitTrustFeedGroup(String subType) throws UnitTrustFeedGroupException;

	/**
	 * Gets the one and only unit trust feed group.
	 * @param subType Identifies the subtype of the unit trust feed group.
	 * @return The unit trust feed group.
	 * @throws com.integrosys.cms.app.feed.bus.unittrust.UnitTrustFeedGroupException
	 *         on errors.
	 */
	IUnitTrustFeedGroupTrxValue getUnitTrustFeedGroup(String subType) throws UnitTrustFeedGroupException;

	/**
	 * Get the transaction value containing UnitTrustFeedGroup This method will
	 * create a transaction if it is not already present, when this module is
	 * first used by user and system is first setup.
	 */
	IUnitTrustFeedGroupTrxValue getUnitTrustFeedGroup(long groupID) throws UnitTrustFeedGroupException;

	/**
	 * Get the transaction value containing UnitTrustFeedGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IUnitTrustFeedGroupTrxValue
	 */
	IUnitTrustFeedGroupTrxValue getUnitTrustFeedGroupByTrxID(long trxID) throws UnitTrustFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the UnitTrustFeedGroup object
	 */
	IUnitTrustFeedGroupTrxValue makerUpdateUnitTrustFeedGroup(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aTrxValue, IUnitTrustFeedGroup aFeedGroup) throws UnitTrustFeedGroupException;

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the UnitTrustFeedGroup object
	 */
	IUnitTrustFeedGroupTrxValue makerSubmitUnitTrustFeedGroup(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aTrxValue, IUnitTrustFeedGroup aFeedGroup) throws UnitTrustFeedGroupException;

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws UnitTrustFeedGroupException
	 */
	IUnitTrustFeedGroupTrxValue makerSubmitRejectedUnitTrustFeedGroup(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aTrxValue) throws UnitTrustFeedGroupException;

	/**
	 * This is essentially the same as makerUpdateUnitTrustFeedGroup except that
	 * it triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the UnitTrustFeedGroup object
	 */
	IUnitTrustFeedGroupTrxValue makerUpdateRejectedUnitTrustFeedGroup(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aTrxValue) throws UnitTrustFeedGroupException;

	/**
	 * Cancels an initiated transaction on a UnitTrustFeedGroup to return it to
	 * last 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the UnitTrustFeedGroup object
	 */
	IUnitTrustFeedGroupTrxValue makerCloseRejectedUnitTrustFeedGroup(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aTrxValue) throws UnitTrustFeedGroupException;

	/**
	 * Cancels an initiated transaction on a UnitTrustFeedGroup to return it to
	 * last 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the UnitTrustFeedGroup object
	 */
	IUnitTrustFeedGroupTrxValue makerCloseDraftUnitTrustFeedGroup(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aTrxValue) throws UnitTrustFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the UnitTrustFeedGroup object
	 */
	IUnitTrustFeedGroupTrxValue checkerApproveUnitTrustFeedGroup(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aTrxValue) throws UnitTrustFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the UnitTrustFeedGroup object
	 */

	IUnitTrustFeedGroupTrxValue checkerRejectUnitTrustFeedGroup(ITrxContext anITrxContext,
			IUnitTrustFeedGroupTrxValue aTrxValue) throws UnitTrustFeedGroupException;

	/**
	 * Gets the feed entry by ric.
	 * @param ric The RIC.
	 * @return The feed entry or <code>null</code>.
	 * @throws UnitTrustFeedEntryException
	 */
	IUnitTrustFeedEntry getUnitTrustFeedEntryByRic(String ric) throws UnitTrustFeedEntryException;

	public static final String NO_FEED_GROUP = "no.feed.group";

}
