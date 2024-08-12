/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/proxy/CCCertificateProxyManagerImpl.java,v 1.22 2005/08/26 13:13:37 lyng Exp $
 */
package com.integrosys.cms.app.cccertificate.proxy;

//java
import java.rmi.RemoteException;
import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateException;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateSearchCriteria;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateSummary;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.cccertificate.trx.ICCCertificateTrxValue;
import com.integrosys.cms.app.common.IContext;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This class act as a facade to the services offered by the certificate modules
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.22 $
 * @since $Date: 2005/08/26 13:13:37 $ Tag: $Name: $
 */
public class CCCertificateProxyManagerImpl implements ICCCertificateProxyManager {

	/**
	 * Get the number of CCC required for the Limit Profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return int - the number of CCC required
	 * @throws CCCertificateException on errors
	 */
	public int getNoOfCCCRequired(ILimitProfile anILimitProfile) throws CCCertificateException {
		try {
			return getCertificateProxyManager().getNoOfCCCRequired(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getCCCertificate: " + ex.toString());
		}
	}

	/**
	 * Get the number of CCC generated for the Limit Profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return int - the number of CCC generated
	 * @throws CCCertificateException on errors
	 */
	public int getNoOfCCCGenerated(ILimitProfile anILimitProfile) throws CCCertificateException {
		try {
			return getCertificateProxyManager().getNoOfCCCGenerated(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getNoOfCCCGenerated: " + ex.toString());
		}
	}

	/**
	 * Check is all CCC are generated
	 * @param anILimitProfile of LimitProfile
	 * @return boolean - true if all the CCCs are generated and false otherwise
	 * @throws CCCertificateException on errors
	 */
	public boolean isAllCCCGenerated(ILimitProfile anILimitProfile) throws CCCertificateException {
		try {
			return getCertificateProxyManager().isAllCCCGenerated(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in isAllCCCGenerated: " + ex.toString());
		}
	}

	/**
	 * Check is CCC is generated for non borrower
	 * @param anICMSCustomer of ICMSCustomer
	 * @return boolean - true if all the CCCs are generated and false otherwise
	 * @throws CCCertificateException on errors
	 */
	public boolean isAllCCCGenerated(ICMSCustomer anICMSCustomer) throws CCCertificateException {
		try {
			return getCertificateProxyManager().isAllCCCGenerated(anICMSCustomer);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in isAllCCCGenerated: " + ex.toString());
		}
	}

	/**
	 * To get the CC certificate summary list
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return CCCertificateSummary[] - the list of cc certificate summary
	 * @throws CCCertificateException on errors
	 */
	public CCCertificateSummary[] getCCCertificateSummaryList(IContext anIContext, ILimitProfile anILimitProfile)
			throws CCCertificateException {
		try {
			return getCertificateProxyManager().getCCCertificateSummaryList(anIContext, anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getCCCertificate: " + ex.toString());
		}
	}

	/**
	 * To get the CC certificate summary list
	 * @param anIContext of IContext type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return CCCertificateSummary[] - the list of cc certificate summary
	 * @throws CCCertificateException on errors
	 */
	public CCCertificateSummary[] getCCCertificateSummaryList(IContext anIContext, ICMSCustomer anICMSCustomer)
			throws CCCertificateException {
		try {
			return getCertificateProxyManager().getCCCertificateSummaryList(anIContext, anICMSCustomer);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getCCCertificate: " + ex.toString());
		}
	}

	/**
	 * Check if there is any pending generate CCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param aCCCertificateSummary of CCCertificateSummary type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws CCCertificateException on errors
	 */
	public boolean hasPendingGenerateCCCTrx(ILimitProfile anILimitProfile, CCCertificateSummary aCCCertificateSummary)
			throws CCCertificateException {
		try {
			return getCertificateProxyManager().hasPendingGenerateCCCTrx(anILimitProfile, aCCCertificateSummary);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in hasPendingGenerateCCCTrx: " + ex.toString());
		}
	}

	/**
	 * To get the CC Certificate
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aCCCertificateSummary of CCCheckListSummary type
	 * @return HashMap - contain the cc customer info and the ccc trx value
	 * @throws CCCertificateException on errors
	 */
	public HashMap getCCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer,
			CCCertificateSummary aCCCertificateSummary) throws CCCertificateException {
		try {
			return getCertificateProxyManager()
					.getCCCertificate(anILimitProfile, anICMSCustomer, aCCCertificateSummary);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getCCCertificate: " + ex.toString());
		}
	}

	/**
	 * To get the CC Certificate
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aCCCertificateSummary of CCCheckListSummary type
	 * @param refreshLimit an indicator to refresh limit/customer info
	 * @return Hashmap - contain the cc customer info and the ccc trx value
	 * @throws CCCertificateException on errors
	 */
	public HashMap getCCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer,
			CCCertificateSummary aCCCertificateSummary, boolean refreshLimit) throws CCCertificateException {
		try {
			return getCertificateProxyManager().getCCCertificate(anILimitProfile, anICMSCustomer,
					aCCCertificateSummary, refreshLimit);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getCCCertificate: " + ex.toString());
		}
	}

	/**
	 * Get CCCertificate for printing.
	 * 
	 * @param trxID transaction id
	 * @return a HashMap of [ICMSConstant.CCC, ICCCertificateTrxValue],
	 *         [ICMSConstant.CCC_OWNER, ICCCertificateCustomerDetail]
	 * @throws CCCertificateException on error getting the cc certificate
	 */
	public HashMap getCCCertificate(ILimitProfile lp, ICMSCustomer cust, String trxID, boolean refreshLimit)
			throws CCCertificateException {
		try {
			return getCertificateProxyManager().getCCCertificate(lp, cust, trxID, refreshLimit);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getCCCertificate: " + ex.toString());
		}
	}

	/**
	 * To get the CCC customer info and the CCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @return HashMap - contain the cc customer info and the ccc trx value
	 * @throws CCCertificateException on errors
	 */
	public HashMap getCCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID)
			throws CCCertificateException {
		try {
			return getCertificateProxyManager().getCCCertificate(anILimitProfile, anICMSCustomer, aTrxID);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getCCCertificate: " + ex.toString());
		}
	}

	/**
	 * Maker generate the CCC
	 * @param anITrxContext of ITrxContext type
	 * @param anICCCertificateTrxValue of ICCCertificateTrxValue type
	 * @param anICCCertificate of ICCCertificate type
	 * @return ICCCertificateTrxValue - the generate CCC trx value
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificateTrxValue makerGenerateCCC(ITrxContext anITrxContext,
			ICCCertificateTrxValue anICCCertificateTrxValue, ICCCertificate anICCCertificate)
			throws CCCertificateException {
		try {
			return getCertificateProxyManager().makerGenerateCCC(anITrxContext, anICCCertificateTrxValue,
					anICCCertificate);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in makerGenerateCCC: " + ex.toString());
		}
	}

	/**
	 * Checker approve the CCC
	 * @param anITrxContext of ITrxContext type
	 * @param anICCCertificateTrxValue of ICCCertificateTrxValue type
	 * @return ICCCertificateTrxValue - the generated CCC trx value
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificateTrxValue checkerApproveGenerateCCC(ITrxContext anITrxContext,
			ICCCertificateTrxValue anICCCertificateTrxValue) throws CCCertificateException {
		try {
			return getCertificateProxyManager().checkerApproveGenerateCCC(anITrxContext, anICCCertificateTrxValue);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in checkerApproveGenerateCCC: " + ex.toString());
		}
	}

	/**
	 * Checker reject the CCC
	 * @param anITrxContext of ITrxContext type
	 * @param anICCCertificateTrxValue of ICCCertificateTrxValue type
	 * @return ICCCertificateTrxValue - the cc certificate trx value
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificateTrxValue checkerRejectGenerateCCC(ITrxContext anITrxContext,
			ICCCertificateTrxValue anICCCertificateTrxValue) throws CCCertificateException {
		try {
			return getCertificateProxyManager().checkerRejectGenerateCCC(anITrxContext, anICCCertificateTrxValue);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in checkerRejectGenerateCCC: " + ex.toString());
		}
	}

	/**
	 * Maker edit the rejected CCC
	 * @param anITrxContext of ITrxContext type
	 * @param anICCCertificateTrxValue of ICCCertificateTrxValue
	 * @param anICCCertificate of ICCCertificate
	 * @return ICCCertificateTrxValue - the cc certificate trx
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificateTrxValue makerEditRejectedGenerateCCC(ITrxContext anITrxContext,
			ICCCertificateTrxValue anICCCertificateTrxValue, ICCCertificate anICCCertificate)
			throws CCCertificateException {
		try {
			return getCertificateProxyManager().makerEditRejectedGenerateCCC(anITrxContext, anICCCertificateTrxValue,
					anICCCertificate);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in makerEditRejectedGenerateCCC: " + ex.toString());
		}
	}

	/**
	 * Make close the rejected CCC
	 * @param anITrxContext of ITrxContext type
	 * @param anICCCertificateTrxValue of ICCCertificateTrxValue type
	 * @return ICCCertificateTrxValue - the cc certificate trx
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificateTrxValue makerCloseRejectedGenerateCCC(ITrxContext anITrxContext,
			ICCCertificateTrxValue anICCCertificateTrxValue) throws CCCertificateException {
		try {
			return getCertificateProxyManager().makerCloseRejectedGenerateCCC(anITrxContext, anICCCertificateTrxValue);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in makerCloseRejectedGenerateCCC: " + ex.toString());
		}
	}

	/**
	 * System close all the CCCs under a limitprofile
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CCCertificateException on errors
	 */
	public void systemCloseCCC(ILimitProfile anILimitProfile) throws CCCertificateException {
		try {
			getCertificateProxyManager().systemCloseCCC(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in systemCloseCCC: " + ex.toString());
		}
	}

	/**
	 * System close the CCC
	 * @param anICCCertificateTrxValue of ICCCertificateTrxValue type
	 * @return ICCCertificateTrxValue - the cc certificate trx
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificateTrxValue systemCloseCCC(ICCCertificateTrxValue anICCCertificateTrxValue)
			throws CCCertificateException {
		try {
			return getCertificateProxyManager().systemCloseCCC(anICCCertificateTrxValue);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in systemCloseCCC: " + ex.toString());
		}
	}

	/**
	 * System close the Main Borrower CCC
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return ICCCerticateTrxValue - the CCCertificate being closed
	 * @throws CCCertificateException on error
	 */
	public ICCCertificateTrxValue systemCloseMainBorrowerCCC(long aLimitProfileID, long aCustomerID)
			throws CCCertificateException {
		try {
			return getCertificateProxyManager().systemCloseMainBorrowerCCC(aLimitProfileID, aCustomerID);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in systemCloseMainBorrowerCCC: " + ex.toString());
		}
	}

	/**
	 * System close the Pledgor CCC
	 * @param aLimitProfileID of long type
	 * @param aPledgorID of long type
	 * @return ICCCerticateTrxValue - the CCCertificate being closed
	 * @throws CCCertificateException on error
	 */
	public ICCCertificateTrxValue systemClosePledgorCCC(long aLimitProfileID, long aPledgorID)
			throws CCCertificateException {
		try {
			return getCertificateProxyManager().systemClosePledgorCCC(aLimitProfileID, aPledgorID);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in systemClosePledgorCCC: " + ex.toString());
		}
	}

	/**
	 * System close the Pledgor CCC
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return ICCCerticateTrxValue - the CCCertificate being closed
	 * @throws CCCertificateException on error
	 */
	public ICCCertificateTrxValue systemCloseCoBorrowerCCC(long aLimitProfileID, long aCustomerID)
			throws CCCertificateException {
		try {
			return getCertificateProxyManager().systemCloseCoBorrowerCCC(aLimitProfileID, aCustomerID);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in systemCloseCoBorrowerCCC: " + ex.toString());
		}
	}

	public ICCCertificateTrxValue systemCloseNonBorrowerCCC(long aCustomerID) throws CCCertificateException {
		try {
			return getCertificateProxyManager().systemCloseNonBorrowerCCC(aCustomerID);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in systemCloseNonBorrowerCCC: " + ex.toString());
		}
	}

	public void handleCertificateForRenewal(ILimitProfile anOldLimitProfile, long aNewLimitProfileID)
			throws CCCertificateException {
		try {
			getCertificateProxyManager().handleCertificateForRenewal(anOldLimitProfile, aNewLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in systemCloseCoBorrowerCCC: " + ex.toString());
		}
	}

	/**
	 * To recompute the total amounts based on the base currency
	 * @param anICCCertificate of ICCCertificate type
	 * @return ICCCertificate - the sc certificate object
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificate computeTotalAmounts(ICCCertificate anICCCertificate) throws CCCertificateException {
		try {
			return getCertificateProxyManager().computeTotalAmounts(anICCCertificate);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in computeTotalAmounts: " + ex.toString());
		}
	}

	/**
	 * Get the CCC for a customer under a particular category
	 * @param aCategory of String type
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return ICCCertificate - the object containing the CCC info
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificate getCCCertificate(String aCategory, long aLimitProfileID, long aCustomerID)
			throws CCCertificateException {
		try {
			return getCertificateProxyManager().getCCCertificate(aCategory, aLimitProfileID, aCustomerID);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getCCCertificate: " + ex.toString());
		}
	}

	/**
	 * Search CCCertificate based on the criteria given.
	 * 
	 * @param criteria cccertificate criteria
	 * @return SearchResult containing a list of CCCertificateSearchResult
	 * @throws CCCertificateException on any errors encountered
	 */
	public SearchResult searchCCCertificate(CCCertificateSearchCriteria criteria) throws CCCertificateException {
		try {
			return getCertificateProxyManager().searchCCCertificate(criteria);
		}
		catch (RemoteException e) {
			throw new CCCertificateException("Exception in searchCCCertificate:" + e.toString());
		}
	}

	public ICCCertificateTrxValue systemCloseCCC(String aCategory, long aLimitProfileID, long aCustomerID)
			throws CCCertificateException {
		try {
			return getCertificateProxyManager().systemCloseCCC(aCategory, aLimitProfileID, aCustomerID);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in systemCloseCCC: " + ex.toString());
		}
	}

	public Amount getTotalLimitProfileApprovalAmount(ILimitProfile anILimitProfile) throws CCCertificateException {
		try {
			return getCertificateProxyManager().getTotalLimitProfileApprovalAmount(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getTotalLimitProfileApprovalAmount: " + ex.toString());
		}
	}

	/**
	 * To get the remote handler for the certificate proxy manager
	 * @return SBCCCertificateProxyManager - the remote handler for the
	 *         certificate proxy manager
	 */
	private SBCCCertificateProxyManager getCertificateProxyManager() {
		SBCCCertificateProxyManager proxymgr = (SBCCCertificateProxyManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CCCERTIFICATE_PROXY_JNDI, SBCCCertificateProxyManagerHome.class.getName());
		return proxymgr;
	}
}
