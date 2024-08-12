/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/propertyindex/IPropertyIndexFeedProxy.java,v 1.3 2003/09/12 09:24:00 btchng Exp $
 */
package com.integrosys.cms.app.feed.proxy.propertyindex;

import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedGroup;
import com.integrosys.cms.app.feed.bus.propertyindex.PropertyIndexFeedGroupException;
import com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/12 09:24:00 $ Tag: $Name: $
 */
public interface IPropertyIndexFeedProxy extends java.io.Serializable {

	/**
	 * Gets the one and only property index feed group.
	 * @param subType Identifies the subtype of the property index feed group.
	 * @return The property index feed group.
	 * @throws com.integrosys.cms.app.feed.bus.propertyindex.PropertyIndexFeedGroupException
	 *         on errors.
	 */
	IPropertyIndexFeedGroupTrxValue getPropertyIndexFeedGroup(String subType) throws PropertyIndexFeedGroupException;

	/**
	 * Get the transaction value containing PropertyIndexFeedGroup This method
	 * will create a transaction if it is not already present, when this module
	 * is first used by user and system is first setup.
	 */
	IPropertyIndexFeedGroupTrxValue getPropertyIndexFeedGroup(long groupID) throws PropertyIndexFeedGroupException;

	/**
	 * Get the transaction value containing PropertyIndexFeedGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IPropertyIndexFeedGroupTrxValue
	 */
	IPropertyIndexFeedGroupTrxValue getPropertyIndexFeedGroupByTrxID(long trxID) throws PropertyIndexFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 */
	IPropertyIndexFeedGroupTrxValue makerUpdatePropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue, IPropertyIndexFeedGroup aFeedGroup)
			throws PropertyIndexFeedGroupException;

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 */
	IPropertyIndexFeedGroupTrxValue makerSubmitPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue, IPropertyIndexFeedGroup aFeedGroup)
			throws PropertyIndexFeedGroupException;

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws PropertyIndexFeedGroupException
	 */
	IPropertyIndexFeedGroupTrxValue makerSubmitRejectedPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException;

	/**
	 * This is essentially the same as makerUpdatePropertyIndexFeedGroup except
	 * that it triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 */
	IPropertyIndexFeedGroupTrxValue makerUpdateRejectedPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException;

	/**
	 * Cancels an initiated transaction on a PropertyIndexFeedGroup to return it
	 * to last 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 */
	IPropertyIndexFeedGroupTrxValue makerCloseRejectedPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException;

	/**
	 * Cancels an initiated transaction on a PropertyIndexFeedGroup to return it
	 * to last 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 */
	IPropertyIndexFeedGroupTrxValue makerCloseDraftPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 */
	IPropertyIndexFeedGroupTrxValue checkerApprovePropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 */

	IPropertyIndexFeedGroupTrxValue checkerRejectPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException;

	public static final String NO_FEED_GROUP = "no.feed.group";

}
