/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/bond/IBondFeedProxy.java,v 1.6 2005/07/28 08:02:41 lyng Exp $
 */
package com.integrosys.cms.app.generalparam.proxy;

import com.integrosys.cms.app.generalparam.bus.GeneralParamEntryException;
import com.integrosys.cms.app.generalparam.bus.GeneralParamGroupException;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public interface IGeneralParamProxy extends java.io.Serializable {
	/**
	 * Get feed price for bond.
	 * 
	 * @return IGeneralParamGroup
	 * @throws GeneralParamGroupException on errors retrieving the bond feed
	 */
	public IGeneralParamGroup getActualGeneralParamGroup() throws GeneralParamGroupException;

	/**
	 * Gets the one and only bond feed group.
	 * @return The bond feed group.
	 * @throws com.integrosys.cms.app.feed.bus.bond.GeneralParamGroupException on
	 *         errors.
	 */
	IGeneralParamGroupTrxValue getGeneralParamGroup() throws GeneralParamGroupException;

	/**
	 * Get the transaction value containing GeneralParamGroup This method will
	 * create a transaction if it is not already present, when this module is
	 * first used by user and system is first setup.
	 */
	IGeneralParamGroupTrxValue getGeneralParamGroup(long groupID) throws GeneralParamGroupException;

	/**
	 * Get the transaction value containing GeneralParamGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IGeneralParamGroupTrxValue
	 */
	IGeneralParamGroupTrxValue getGeneralParamGroupByTrxID(long trxID) throws GeneralParamGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GeneralParamGroup object
	 */
	IGeneralParamGroupTrxValue makerUpdateGeneralParamGroup(ITrxContext anITrxContext, IGeneralParamGroupTrxValue aTrxValue,
			IGeneralParamGroup aFeedGroup) throws GeneralParamGroupException;

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GeneralParamGroup object
	 */
	IGeneralParamGroupTrxValue makerSubmitGeneralParamGroup(ITrxContext anITrxContext, IGeneralParamGroupTrxValue aTrxValue,
			IGeneralParamGroup aFeedGroup) throws GeneralParamGroupException;

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws GeneralParamGroupException
	 */
	IGeneralParamGroupTrxValue makerSubmitRejectedGeneralParamGroup(ITrxContext anITrxContext, IGeneralParamGroupTrxValue aTrxValue)
			throws GeneralParamGroupException;

	/**
	 * This is essentially the same as makerUpdateGeneralParamGroup except that it
	 * triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GeneralParamGroup object
	 */
	IGeneralParamGroupTrxValue makerUpdateRejectedGeneralParamGroup(ITrxContext anITrxContext, IGeneralParamGroupTrxValue aTrxValue)
			throws GeneralParamGroupException;

	/**
	 * Cancels an initiated transaction on a GeneralParamGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GeneralParamGroup object
	 */
	IGeneralParamGroupTrxValue makerCloseRejectedGeneralParamGroup(ITrxContext anITrxContext, IGeneralParamGroupTrxValue aTrxValue)
			throws GeneralParamGroupException;

	/**
	 * Cancels an initiated transaction on a GeneralParamGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GeneralParamGroup object
	 */
	IGeneralParamGroupTrxValue makerCloseDraftGeneralParamGroup(ITrxContext anITrxContext, IGeneralParamGroupTrxValue aTrxValue)
			throws GeneralParamGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GeneralParamGroup object
	 */
	IGeneralParamGroupTrxValue checkerApproveGeneralParamGroup(ITrxContext anITrxContext, IGeneralParamGroupTrxValue aTrxValue)
			throws GeneralParamGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the GeneralParamGroup object
	 */

	IGeneralParamGroupTrxValue checkerRejectGeneralParamGroup(ITrxContext anITrxContext, IGeneralParamGroupTrxValue aTrxValue)
			throws GeneralParamGroupException;

	/**
	 * Gets the feed entry by ric.
	 * @param ric The RIC.
	 * @return The feed entry or <code>null</code>.
	 * @throws GeneralParamEntryException
	 */
	IGeneralParamEntry getGeneralParamEntryByRic(String ric) throws GeneralParamEntryException;

	public static final String NO_FEED_GROUP = "no.feed.group";
	
	public IGeneralParamEntry getGeneralParamEntryByParamCodeActual(String paramCode) throws GeneralParamEntryException;
	
}
