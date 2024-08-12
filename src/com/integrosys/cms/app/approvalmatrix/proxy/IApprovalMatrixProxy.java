/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/bond/IApprovalMatrixProxy.java,v 1.6 2005/07/28 08:02:41 lyng Exp $
 */
package com.integrosys.cms.app.approvalmatrix.proxy;

import com.integrosys.cms.app.approvalmatrix.bus.ApprovalMatrixEntryException;
import com.integrosys.cms.app.approvalmatrix.bus.ApprovalMatrixException;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixEntry;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixGroup;
import com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/07/28 08:02:41 $ Tag: $Name: $
 */
public interface IApprovalMatrixProxy extends java.io.Serializable {
	/**
	 * Get feed price for bond.
	 * 
	 * @return IApprovalMatrixGroup
	 * @throws ApprovalMatrixException on errors retrieving the bond feed
	 */
	public IApprovalMatrixGroup getActualApprovalMatrixGroup() throws ApprovalMatrixException;

	/**
	 * Gets the one and only bond feed group.
	 * @return The bond feed group.
	 * @throws com.integrosys.cms.app.feed.bus.bond.ApprovalMatrixException on
	 *         errors.
	 */
	IApprovalMatrixTrxValue getApprovalMatrixGroup() throws ApprovalMatrixException;

	/**
	 * Get the transaction value containing ApprovalMatrixGroup This method will
	 * create a transaction if it is not already present, when this module is
	 * first used by user and system is first setup.
	 */
	IApprovalMatrixTrxValue getApprovalMatrixGroup(long groupID) throws ApprovalMatrixException;

	/**
	 * Get the transaction value containing ApprovalMatrixGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IApprovalMatrixTrxValue
	 */
	IApprovalMatrixTrxValue getApprovalMatrixGroupByTrxID(long trxID) throws ApprovalMatrixException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ApprovalMatrixGroup object
	 */
	IApprovalMatrixTrxValue makerUpdateApprovalMatrixGroup(ITrxContext anITrxContext, IApprovalMatrixTrxValue aTrxValue,
			IApprovalMatrixGroup aFeedGroup) throws ApprovalMatrixException;

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ApprovalMatrixGroup object
	 */
	IApprovalMatrixTrxValue makerSubmitApprovalMatrixGroup(ITrxContext anITrxContext, IApprovalMatrixTrxValue aTrxValue,
			IApprovalMatrixGroup aFeedGroup) throws ApprovalMatrixException;

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws ApprovalMatrixException
	 */
	IApprovalMatrixTrxValue makerSubmitRejectedApprovalMatrixGroup(ITrxContext anITrxContext, IApprovalMatrixTrxValue aTrxValue)
			throws ApprovalMatrixException;

	/**
	 * This is essentially the same as makerUpdateApprovalMatrixGroup except that it
	 * triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ApprovalMatrixGroup object
	 */
	IApprovalMatrixTrxValue makerUpdateRejectedApprovalMatrixGroup(ITrxContext anITrxContext, IApprovalMatrixTrxValue aTrxValue)
			throws ApprovalMatrixException;

	/**
	 * Cancels an initiated transaction on a ApprovalMatrixGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ApprovalMatrixGroup object
	 */
	IApprovalMatrixTrxValue makerCloseRejectedApprovalMatrixGroup(ITrxContext anITrxContext, IApprovalMatrixTrxValue aTrxValue)
			throws ApprovalMatrixException;

	/**
	 * Cancels an initiated transaction on a ApprovalMatrixGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ApprovalMatrixGroup object
	 */
	IApprovalMatrixTrxValue makerCloseDraftApprovalMatrixGroup(ITrxContext anITrxContext, IApprovalMatrixTrxValue aTrxValue)
			throws ApprovalMatrixException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ApprovalMatrixGroup object
	 */
	IApprovalMatrixTrxValue checkerApproveApprovalMatrixGroup(ITrxContext anITrxContext, IApprovalMatrixTrxValue aTrxValue)
			throws ApprovalMatrixException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ApprovalMatrixGroup object
	 */

	IApprovalMatrixTrxValue checkerRejectApprovalMatrixGroup(ITrxContext anITrxContext, IApprovalMatrixTrxValue aTrxValue)
			throws ApprovalMatrixException;

	/**
	 * Gets the feed entry by ric.
	 * @param ric The RIC.
	 * @return The feed entry or <code>null</code>.
	 * @throws ApprovalMatrixEntryException
	 */
	IApprovalMatrixEntry getApprovalMatrixEntryByRic(String ric) throws ApprovalMatrixEntryException;

	public static final String NO_FEED_GROUP = "no.feed.group";
}
