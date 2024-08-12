/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/proxy/SBCCCertificateProxyManager.java,v 1.22 2005/08/26 13:13:37 lyng Exp $
 */
package com.integrosys.cms.app.cccertificate.proxy;

//java
import java.rmi.RemoteException;
import java.util.HashMap;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateException;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateSearchCriteria;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateSummary;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.cccertificate.trx.ICCCertificateTrxValue;
import com.integrosys.cms.app.common.IContext;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Session bean remote interface for the services provided by the certificate
 * proxy manager
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.22 $
 * @since $Date: 2005/08/26 13:13:37 $ Tag: $Name: $
 */
public interface SBCCCertificateProxyManager extends EJBObject {
	/**
	 * Get the number of CCC required for the Limit Profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return int - the number of CCC required
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfCCCRequired(ILimitProfile anILimitProfile) throws CCCertificateException, RemoteException;

	/**
	 * Get the number of CCC generated for the Limit Profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return int - the number of CCC generated
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfCCCGenerated(ILimitProfile anILimitProfile) throws CCCertificateException, RemoteException;

	/**
	 * Check is all CCC are generated
	 * @param anILimitProfile of LimitProfile
	 * @return boolean - true if all the CCCs are generated and false otherwise
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public boolean isAllCCCGenerated(ILimitProfile anILimitProfile) throws CCCertificateException, RemoteException;

	/**
	 * Check is CCC is generated for non borrower
	 * @param anICMSCustomer of ICMSCustomer
	 * @return boolean - true if all the CCCs are generated and false otherwise
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public boolean isAllCCCGenerated(ICMSCustomer anICMSCustomer) throws CCCertificateException, RemoteException;

	/**
	 * To get the CC certificate summary list
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return CCCertificateSummary[] - the list of cc certificate summary
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public CCCertificateSummary[] getCCCertificateSummaryList(IContext anIContext, ILimitProfile anILimitProfile)
			throws CCCertificateException, RemoteException;

	/**
	 * To get the CC certificate summary list
	 * @param anIContext of IContext type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return CCCertificateSummary[] - the list of cc certificate summary
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on errors
	 */
	public CCCertificateSummary[] getCCCertificateSummaryList(IContext anIContext, ICMSCustomer anICMSCustomer)
			throws CCCertificateException, RemoteException;

	/**
	 * Check if there is any pending generate CCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param aCCCertificateSummary of CCCertificateSummary type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public boolean hasPendingGenerateCCCTrx(ILimitProfile anILimitProfile, CCCertificateSummary aCCCertificateSummary)
			throws CCCertificateException, RemoteException;

	/**
	 * To get the CC Certificate
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aCCCertificateSummary of CCCheckListSummary type
	 * @return HashMap
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getCCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer,
			CCCertificateSummary aCCCertificateSummary) throws CCCertificateException, RemoteException;

	/**
	 * To get the CC Certificate
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aCCCertificateSummary of CCCheckListSummary type
	 * @param refreshLimit an indicator to refresh limit/customer info
	 * @return Hashmap - contain the cc customer info and the ccc trx value
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on error during remote method call
	 */
	public HashMap getCCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer,
			CCCertificateSummary aCCCertificateSummary, boolean refreshLimit) throws CCCertificateException,
			RemoteException;

	/**
	 * Get CCCertificate for printing.
	 * 
	 * @param trxID transaction id
	 * @return a HashMap of [ICMSConstant.CCC, ICCCertificateTrxValue],
	 *         [ICMSConstant.CCC_OWNER, ICCCertificateCustomerDetail]
	 * @throws CCCertificateException on error getting the cc certificate
	 * @throws RemoteException on error during remote method call
	 */
	public HashMap getCCCertificate(ILimitProfile lp, ICMSCustomer cust, String trxID, boolean refreshLimit)
			throws CCCertificateException, RemoteException;

	/**
	 * To get the CCC customer info and the CCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @return HashMap - contain the cc customer info and the ccc trx value
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getCCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID)
			throws CCCertificateException, RemoteException;

	/**
	 * Maker generate the CCC
	 * @param anITrxContext of ITrxContext type
	 * @param anICCCertificateTrxValue of ICCCertificateTrxValue type
	 * @param anICCCertificate of ICCCertificate type
	 * @return ICCCertificateTrxValue - the generate CCC trx value
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCCertificateTrxValue makerGenerateCCC(ITrxContext anITrxContext,
			ICCCertificateTrxValue anICCCertificateTrxValue, ICCCertificate anICCCertificate)
			throws CCCertificateException, RemoteException;

	/**
	 * Checker approve the CCC
	 * @param anITrxContext of ITrxContext type
	 * @param anICCCertificateTrxValue of ICCCertificateTrxValue type
	 * @return ICCCertificateTrxValue - the generated CCC trx value
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCCertificateTrxValue checkerApproveGenerateCCC(ITrxContext anITrxContext,
			ICCCertificateTrxValue anICCCertificateTrxValue) throws CCCertificateException, RemoteException;

	/**
	 * Checker reject the CCC
	 * @param anITrxContext of ITrxContext type
	 * @param anICCCertificateTrxValue of ICCCertificateTrxValue type
	 * @return ICCCertificateTrxValue - the cc certificate trx value
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCCertificateTrxValue checkerRejectGenerateCCC(ITrxContext anITrxContext,
			ICCCertificateTrxValue anICCCertificateTrxValue) throws CCCertificateException, RemoteException;

	/**
	 * Maker edit the rejected CCC
	 * @param anITrxContext of ITrxContext type
	 * @param anICCCertificateTrxValue of ICCCertificateTrxValue
	 * @param anICCCertificate of ICCCertificate
	 * @return ICCCertificateTrxValue - the cc certificate trx
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCCertificateTrxValue makerEditRejectedGenerateCCC(ITrxContext anITrxContext,
			ICCCertificateTrxValue anICCCertificateTrxValue, ICCCertificate anICCCertificate)
			throws CCCertificateException, RemoteException;

	/**
	 * Make close the rejected CCC
	 * @param anITrxContext of ITrxContext type
	 * @param anICCCertificateTrxValue of ICCCertificateTrxValue type
	 * @return ICCCertificateTrxValue - the cc certificate trx
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCCertificateTrxValue makerCloseRejectedGenerateCCC(ITrxContext anITrxContext,
			ICCCertificateTrxValue anICCCertificateTrxValue) throws CCCertificateException, RemoteException;

	/**
	 * System close all the CCCs under a limitprofile
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public void systemCloseCCC(ILimitProfile anILimitProfile) throws CCCertificateException, RemoteException;

	/**
	 * System close the CCC
	 * @param anICCCertificateTrxValue of ICCCertificateTrxValue type
	 * @return ICCCertificateTrxValue - the cc certificate trx
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCCertificateTrxValue systemCloseCCC(ICCCertificateTrxValue anICCCertificateTrxValue)
			throws CCCertificateException, RemoteException;

	/**
	 * System close the Main Borrower CCC
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return ICCCerticateTrxValue - the CCCertificate being closed
	 * @throws CCCertificateException on error
	 * @throws RemoteException in remote error
	 */
	public ICCCertificateTrxValue systemCloseMainBorrowerCCC(long aLimitProfileID, long aCustomerID)
			throws CCCertificateException, RemoteException;

	/**
	 * System close the Pledgor CCC
	 * @param aLimitProfileID of long type
	 * @param aPledgorID of long type
	 * @return ICCCerticateTrxValue - the CCCertificate being closed
	 * @throws CCCertificateException on error
	 * @throws RemoteException in remote error
	 */
	public ICCCertificateTrxValue systemClosePledgorCCC(long aLimitProfileID, long aPledgorID)
			throws CCCertificateException, RemoteException;

	/**
	 * System close the Pledgor CCC
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return ICCCerticateTrxValue - the CCCertificate being closed
	 * @throws CCCertificateException on error
	 * @throws RemoteException on remote error
	 */
	public ICCCertificateTrxValue systemCloseCoBorrowerCCC(long aLimitProfileID, long aCustomerID)
			throws CCCertificateException, RemoteException;

	public ICCCertificateTrxValue systemCloseNonBorrowerCCC(long aCustomerID) throws CCCertificateException,
			RemoteException;

	public void handleCertificateForRenewal(ILimitProfile anOldLimitProfile, long aNewLimitProfileID)
			throws CCCertificateException, RemoteException;

	/**
	 * To recompute the total amounts based on the base currency
	 * @param anICCCertificate of ICCCertificate type
	 * @return ICCCertificate - the sc certificate object
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote error
	 */
	public ICCCertificate computeTotalAmounts(ICCCertificate anICCCertificate) throws CCCertificateException,
			RemoteException;

	/**
	 * Get the CCC for a customer under a particular category
	 * @param aCategory of String type
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return ICCCertificate - the object containing the CCC info
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote error
	 */
	public ICCCertificate getCCCertificate(String aCategory, long aLimitProfileID, long aCustomerID)
			throws CCCertificateException, RemoteException;

	/**
	 * Search CCCertificate based on the criteria given.
	 * 
	 * @param criteria cccertificate criteria
	 * @return SearchResult containing a list of CCCertificateSearchResult
	 * @throws CCCertificateException on any errors encountered
	 * @throws RemoteException on any unexpected error during remote method call
	 */
	public SearchResult searchCCCertificate(CCCertificateSearchCriteria criteria) throws CCCertificateException,
			RemoteException;

	public ICCCertificateTrxValue systemCloseCCC(String aCategory, long aLimitProfileID, long aCustomerID)
			throws CCCertificateException, RemoteException;

	public Amount getTotalLimitProfileApprovalAmount(ILimitProfile anILimitProfile) throws CCCertificateException,
			RemoteException;
}
