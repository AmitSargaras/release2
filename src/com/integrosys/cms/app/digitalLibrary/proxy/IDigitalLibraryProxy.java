/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/bond/IDigitalLibraryProxy.java,v 1.6 2005/07/28 08:02:41 lyng Exp $
 */
package com.integrosys.cms.app.digitalLibrary.proxy;

import com.integrosys.cms.app.digitalLibrary.bus.DigitalLibraryEntryException;
import com.integrosys.cms.app.digitalLibrary.bus.DigitalLibraryException;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryEntry;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryGroup;
import com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/07/28 08:02:41 $ Tag: $Name: $
 */
public interface IDigitalLibraryProxy extends java.io.Serializable {
	/**
	 * Get feed price for bond.
	 * 
	 * @return IDigitalLibraryGroup
	 * @throws DigitalLibraryException on errors retrieving the bond feed
	 */
	public IDigitalLibraryGroup getActualDigitalLibraryGroup() throws DigitalLibraryException;

	/**
	 * Gets the one and only bond feed group.
	 * @return The bond feed group.
	 * @throws com.integrosys.cms.app.feed.bus.bond.DigitalLibraryException on
	 *         errors.
	 */
	IDigitalLibraryTrxValue getDigitalLibraryGroup() throws DigitalLibraryException;

	/**
	 * Get the transaction value containing DigitalLibraryGroup This method will
	 * create a transaction if it is not already present, when this module is
	 * first used by user and system is first setup.
	 */
	IDigitalLibraryTrxValue getDigitalLibraryGroup(long groupID) throws DigitalLibraryException;

	/**
	 * Get the transaction value containing DigitalLibraryGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IDigitalLibraryTrxValue
	 */
	IDigitalLibraryTrxValue getDigitalLibraryGroupByTrxID(long trxID) throws DigitalLibraryException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the DigitalLibraryGroup object
	 */
	IDigitalLibraryTrxValue makerUpdateDigitalLibraryGroup(ITrxContext anITrxContext, IDigitalLibraryTrxValue aTrxValue,
			IDigitalLibraryGroup aFeedGroup) throws DigitalLibraryException;

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the DigitalLibraryGroup object
	 */
	IDigitalLibraryTrxValue makerSubmitDigitalLibraryGroup(ITrxContext anITrxContext, IDigitalLibraryTrxValue aTrxValue,
			IDigitalLibraryGroup aFeedGroup) throws DigitalLibraryException;

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws DigitalLibraryException
	 */
	IDigitalLibraryTrxValue makerSubmitRejectedDigitalLibraryGroup(ITrxContext anITrxContext, IDigitalLibraryTrxValue aTrxValue)
			throws DigitalLibraryException;

	/**
	 * This is essentially the same as makerUpdateDigitalLibraryGroup except that it
	 * triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the DigitalLibraryGroup object
	 */
	IDigitalLibraryTrxValue makerUpdateRejectedDigitalLibraryGroup(ITrxContext anITrxContext, IDigitalLibraryTrxValue aTrxValue)
			throws DigitalLibraryException;

	/**
	 * Cancels an initiated transaction on a DigitalLibraryGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the DigitalLibraryGroup object
	 */
	IDigitalLibraryTrxValue makerCloseRejectedDigitalLibraryGroup(ITrxContext anITrxContext, IDigitalLibraryTrxValue aTrxValue)
			throws DigitalLibraryException;

	/**
	 * Cancels an initiated transaction on a DigitalLibraryGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the DigitalLibraryGroup object
	 */
	IDigitalLibraryTrxValue makerCloseDraftDigitalLibraryGroup(ITrxContext anITrxContext, IDigitalLibraryTrxValue aTrxValue)
			throws DigitalLibraryException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the DigitalLibraryGroup object
	 */
	IDigitalLibraryTrxValue checkerApproveDigitalLibraryGroup(ITrxContext anITrxContext, IDigitalLibraryTrxValue aTrxValue)
			throws DigitalLibraryException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the DigitalLibraryGroup object
	 */

	IDigitalLibraryTrxValue checkerRejectDigitalLibraryGroup(ITrxContext anITrxContext, IDigitalLibraryTrxValue aTrxValue)
			throws DigitalLibraryException;

	/**
	 * Gets the feed entry by ric.
	 * @param ric The RIC.
	 * @return The feed entry or <code>null</code>.
	 * @throws DigitalLibraryEntryException
	 */
	IDigitalLibraryEntry getDigitalLibraryEntryByRic(String ric) throws DigitalLibraryEntryException;

	public static final String NO_FEED_GROUP = "no.feed.group";
}
