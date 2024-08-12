/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/proxy/DocumentLocationProxyManagerImpl.java,v 1.3 2004/04/06 09:22:38 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.proxy;

//java
import java.rmi.RemoteException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.IContext;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.documentlocation.bus.CCDocumentLocationSearchCriteria;
import com.integrosys.cms.app.documentlocation.bus.CCDocumentLocationSearchResult;
import com.integrosys.cms.app.documentlocation.bus.CCDocumentLocationSummary;
import com.integrosys.cms.app.documentlocation.bus.DocumentLocationException;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;
import com.integrosys.cms.app.documentlocation.trx.ICCDocumentLocationTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This class act as a facade to the services offered by the document location
 * modules
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/04/06 09:22:38 $ Tag: $Name: $
 */
public class DocumentLocationProxyManagerImpl implements IDocumentLocationProxyManager {
	/**
	 * Check if there is any pending CC document location trx
	 * @param aLimitProfileID of long type
	 * @param aCategory of String type
	 * @param aCustomerID of long type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws DocumentLocationException on errors
	 */
	public boolean hasPendingCCDocumentLocationTrx(long aLimitProfileID, String aCategory, long aCustomerID)
			throws DocumentLocationException {
		try {
			return getDocumentLocationProxyManager().hasPendingCCDocumentLocationTrx(aLimitProfileID, aCategory,
					aCustomerID);
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in hasPendingCCDocumentLocationTrx: " + ex.toString());
		}
	}

	/**
	 * Get the CC summary list for non borrower
	 * @param anIContext of IContext type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return CCDocumentLocationSummary[] - the will contain the CC summary
	 *         list and the whether collaboration is allowed or not
	 * @throws DocumentLocationException
	 */
	public CCDocumentLocationSummary[] getCCSummaryList(IContext anIContext, ICMSCustomer anICMSCustomer)
			throws DocumentLocationException {
		try {
			return getDocumentLocationProxyManager().getCCSummaryList(anIContext, anICMSCustomer);
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in getCCSummaryList: " + ex.toString());
		}
	}

	/**
	 * Get the CC summary list
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return CCDocumentLocationSummary[] - the will contain the CC summary
	 *         list and the whether collaboration is allowed or not
	 * @throws DocumentLocationException
	 */
	public CCDocumentLocationSummary[] getCCSummaryList(IContext anIContext, ILimitProfile anILimitProfile)
			throws DocumentLocationException {
		try {
			return getDocumentLocationProxyManager().getCCSummaryList(anIContext, anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in getCCSummaryList: " + ex.toString());
		}
	}

	/**
	 * Get the cc document location trx value using the limitprofile id,
	 * customer type, customer id and the country
	 * @param aLimitProfileID of long type
	 * @param aCategory of String type
	 * @param aCustomerID of long type
	 * @return ICCDocumentLocationTrxValue - the trx value of the CC document
	 *         location
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue getCCDocumentLocationTrxValue(long aLimitProfileID, String aCategory,
			long aCustomerID) throws DocumentLocationException {
		try {
			return getDocumentLocationProxyManager().getCCDocumentLocationTrxValue(aLimitProfileID, aCategory,
					aCustomerID);
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in getCCDocumentLocationTrxValue: " + ex.toString());
		}
	}

	/**
	 * Get the list of cc document location based on the criteria
	 * @param anOwnerType of String type
	 * @param aLimitProfileID of long type
	 * @param anOwnerID of long type
	 * @return ICCDocumentLocation[] - the list of cc document location
	 * @throws DocumentLocationException on error
	 */
	public ICCDocumentLocation[] getCCDocumentLocation(String anOwnerType, long aLimitProfileID, long anOwnerID)
			throws DocumentLocationException {
		try {
			return getDocumentLocationProxyManager().getCCDocumentLocation(anOwnerType, aLimitProfileID, anOwnerID);
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in getCCDocumentLocation " + ex.toString());
		}
	}

	/**
	 * Get the CC document location trx value using the trx ID
	 * @param aTrxID of String type
	 * @return ICCDocumentLocationTrxValue - the CC document location trx value
	 * @throws DocumentLocationException
	 */
	public ICCDocumentLocationTrxValue getCCDocumentLocationByTrxID(String aTrxID) throws DocumentLocationException {
		try {
			return getDocumentLocationProxyManager().getCCDocumentLocationByTrxID(aTrxID);
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in getCCDocumentLocationByTrxID: " + ex.toString());
		}
	}

	/**
	 * Maker create the document location
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocation of ICCDocumentLocation type
	 * @return ICCDocumentLocationTrxValue - the created CC document location
	 *         trx value
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue makerCreateDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocation anICCDocumentLocation) throws DocumentLocationException {
		try {
			return getDocumentLocationProxyManager().makerCreateDocumentLocation(anITrxContext, anICCDocumentLocation);
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in makerCreateDocumentLocation: " + ex.toString());
		}
	}

	/**
	 * Maker update the document location
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocation of ICCDocumentLocation type
	 * @return ICCDocumentLocationTrxValue - the updated CC document location
	 *         trx value
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue makerUpdateDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue, ICCDocumentLocation anICCDocumentLocation)
			throws DocumentLocationException {
		try {
			return getDocumentLocationProxyManager().makerUpdateDocumentLocation(anITrxContext,
					anICCDocumentLocationTrxValue, anICCDocumentLocation);
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in makerUpdateDocumentLocation: " + ex.toString());
		}
	}

	/**
	 * Checker approve the document location
	 * @param anITrxContext of ITrxContext type
	 * @param ICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue type
	 * @return ICCDocumentLocationTrxValue - the created CC document location
	 *         trx value
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue checkerApproveDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) throws DocumentLocationException {
		try {
			return getDocumentLocationProxyManager().checkerApproveDocumentLocation(anITrxContext,
					anICCDocumentLocationTrxValue);
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in checkerApproveDocumentLocation: " + ex.toString());
		}
	}

	/**
	 * Checker reject the document location
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue type
	 * @return ICCDocumentLocationTrxValue - the document location trx value
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue checkerRejectDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) throws DocumentLocationException {
		try {
			return getDocumentLocationProxyManager().checkerRejectDocumentLocation(anITrxContext,
					anICCDocumentLocationTrxValue);
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in checkerRejectDocumentLocation: " + ex.toString());
		}
	}

	/**
	 * Maker edit the rejected document location
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue
	 * @param anICCDocumentLocation of ICCDocumentLocation
	 * @return ICCDocumentLocationTrxValue - the CC document location trx
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue makerEditRejectedDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue, ICCDocumentLocation anICCDocumentLocation)
			throws DocumentLocationException {
		try {
			return getDocumentLocationProxyManager().makerEditRejectedDocumentLocation(anITrxContext,
					anICCDocumentLocationTrxValue, anICCDocumentLocation);
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in makerEditRejectedDocumentLocation: " + ex.toString());
		}
	}

	/**
	 * Make close the rejected document location
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue type
	 * @return ICCDocumentLocationTrxValue - the CC document location trx
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue makerCloseRejectedDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) throws DocumentLocationException {
		try {
			return getDocumentLocationProxyManager().makerCloseRejectedDocumentLocation(anITrxContext,
					anICCDocumentLocationTrxValue);
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in makerCloseRejectedDocumentLocation: " + ex.toString());
		}
	}

	/**
	 * Get the list of CC document location that satisfy the search criteria
	 * @param aCriteria of CCDocumentLocationSearchCriteria type
	 * @return CCDocumentLocationSearchResult[] - the list of cc document
	 *         location result that satisfy the criteria
	 * @throws DocumentLocationException
	 */
	public CCDocumentLocationSearchResult[] getCCDocumentLocation(CCDocumentLocationSearchCriteria aCriteria)
			throws DocumentLocationException {
		try {
			return getDocumentLocationProxyManager().getCCDocumentLocation(aCriteria);
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in getCCDocumentLocation: " + ex.toString());
		}
	}

	/**
	 * To get the remote handler for the document location proxy manager
	 * @return SBDocumentLocationProxyManager - the remote handler for the
	 *         document location proxy manager
	 */
	private SBDocumentLocationProxyManager getDocumentLocationProxyManager() {
		SBDocumentLocationProxyManager proxymgr = (SBDocumentLocationProxyManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_DOC_LOCATION_PROXY_JNDI, SBDocumentLocationProxyManagerHome.class.getName());
		return proxymgr;
	}
}
