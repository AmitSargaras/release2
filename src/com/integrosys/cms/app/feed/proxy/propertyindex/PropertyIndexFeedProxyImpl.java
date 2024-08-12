/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/propertyindex/PropertyIndexFeedProxyImpl.java,v 1.4 2005/01/12 07:44:39 hshii Exp $
 */
package com.integrosys.cms.app.feed.proxy.propertyindex;

import java.rmi.RemoteException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedGroup;
import com.integrosys.cms.app.feed.bus.propertyindex.PropertyIndexFeedGroupException;
import com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

public class PropertyIndexFeedProxyImpl extends AbstractPropertyIndexFeedProxy {

	protected void rollback() throws PropertyIndexFeedGroupException {
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 */
	public IPropertyIndexFeedGroupTrxValue checkerApprovePropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException {
		try {
			return getSbPropertyIndexFeedProxy().checkerApprovePropertyIndexFeedGroup(anITrxContext, aTrxValue);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 */

	public IPropertyIndexFeedGroupTrxValue checkerRejectPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException {
		try {
			return getSbPropertyIndexFeedProxy().checkerRejectPropertyIndexFeedGroup(anITrxContext, aTrxValue);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	public IPropertyIndexFeedGroupTrxValue getPropertyIndexFeedGroup(String subType)
			throws PropertyIndexFeedGroupException {
		try {
			return getSbPropertyIndexFeedProxy().getPropertyIndexFeedGroup(subType);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	/**
	 * Get the transaction value containing PropertyIndexFeedGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IPropertyIndexFeedGroupTrxValue
	 */
	public IPropertyIndexFeedGroupTrxValue getPropertyIndexFeedGroupByTrxID(long trxID)
			throws PropertyIndexFeedGroupException {
		try {
			return getSbPropertyIndexFeedProxy().getPropertyIndexFeedGroupByTrxID(trxID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the
	 *        PropertyIndexFeedGroup object
	 * @param aFeedGroup - IPropertyIndexFeedGroup, this could have been passed
	 *        in the trx value, but the intention is that the caller should not
	 *        have modified the trxValue, as the caller does not need to know
	 *        about staging settings et al.
	 * @return IPropertyIndexFeedGroupTrxValue the saved trxValue
	 */
	public IPropertyIndexFeedGroupTrxValue makerUpdatePropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aFeedGroupTrxValue, IPropertyIndexFeedGroup aFeedGroup)
			throws PropertyIndexFeedGroupException {
		try {
			return getSbPropertyIndexFeedProxy().makerUpdatePropertyIndexFeedGroup(anITrxContext, aFeedGroupTrxValue,
					aFeedGroup);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the
	 *        PropertyIndexFeedGroup object
	 * @param aFeedGroup - IPropertyIndexFeedGroup, this could have been passed
	 *        in the trx value, but the intention is that the caller should not
	 *        have modified the trxValue, as the caller does not need to know
	 *        about staging settings et al.
	 */
	public IPropertyIndexFeedGroupTrxValue makerSubmitPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aFeedGroupTrxValue, IPropertyIndexFeedGroup aFeedGroup)
			throws PropertyIndexFeedGroupException {

		try {
			return getSbPropertyIndexFeedProxy().makerSubmitPropertyIndexFeedGroup(anITrxContext, aFeedGroupTrxValue,
					aFeedGroup);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws PropertyIndexFeedGroupException
	 */
	public IPropertyIndexFeedGroupTrxValue makerSubmitRejectedPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException {
		try {
			return getSbPropertyIndexFeedProxy().makerSubmitRejectedPropertyIndexFeedGroup(anITrxContext, aTrxValue);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	// --- below are 2nd priority to be implemented
	/**
	 * This is essentially the same as makerUpdatePropertyIndexFeedGroup except
	 * that it triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 */
	public IPropertyIndexFeedGroupTrxValue makerUpdateRejectedPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException {
		try {
			return getSbPropertyIndexFeedProxy().makerUpdateRejectedPropertyIndexFeedGroup(anITrxContext, aTrxValue);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	/**
	 * Cancels an initiated transaction on a PropertyIndexFeedGroup to return it
	 * to last 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 */
	public IPropertyIndexFeedGroupTrxValue makerCloseRejectedPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException {
		try {
			return getSbPropertyIndexFeedProxy().makerCloseRejectedPropertyIndexFeedGroup(anITrxContext, aTrxValue);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	/**
	 * Cancels an initiated transaction on a PropertyIndexFeedGroup to return it
	 * to last 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 */
	public IPropertyIndexFeedGroupTrxValue makerCloseDraftPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException {
		try {
			return getSbPropertyIndexFeedProxy().makerCloseDraftPropertyIndexFeedGroup(anITrxContext, aTrxValue);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	private SBPropertyIndexFeedProxy getSbPropertyIndexFeedProxy() {

		return (SBPropertyIndexFeedProxy) BeanController.getEJB(ICMSJNDIConstant.SB_PROPERTY_INDEX_FEED_PROXY_JNDI,
				SBPropertyIndexFeedProxyHome.class.getName());
	}

}