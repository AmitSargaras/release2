/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/proxy/SBSCCertificateProxyManager.java,v 1.17 2005/08/26 13:14:34 lyng Exp $
 */
package com.integrosys.cms.app.sccertificate.proxy;

//java
import java.rmi.RemoteException;
import java.util.HashMap;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;
import com.integrosys.cms.app.sccertificate.bus.PartialSCCertificateSearchCriteria;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateException;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateSearchCriteria;
import com.integrosys.cms.app.sccertificate.trx.IPartialSCCertificateTrxValue;
import com.integrosys.cms.app.sccertificate.trx.ISCCertificateTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Session bean remote interface for the services provided by the sc certificate
 * proxy manager
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/08/26 13:14:34 $ Tag: $Name: $
 */
public interface SBSCCertificateProxyManager extends EJBObject {
	/**
	 * Check if SCC has been generated for a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if SCC is generated and false otherwise
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public boolean isSCCFullyGenerated(ILimitProfile anILimitProfile) throws SCCertificateException, RemoteException;

	/**
	 * Check if there is any pending generate SCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public boolean hasPendingGenerateSCCTrx(ILimitProfile anILimitProfile) throws SCCertificateException,
			RemoteException;

	/**
	 * Check if there is any pending generate Partial SCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public boolean hasPendingGeneratePartialSCCTrx(ILimitProfile anILimitProfile) throws SCCertificateException,
			RemoteException;

	/**
	 * To get the SC Certificate
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param refreshLimit an indicator to refresh limit/customer info
	 * @return Hashmap - contain the sc customer info and the scc trx value
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, boolean refreshLimit)
			throws SCCertificateException, RemoteException;

	/**
	 * To get the SC Certificate
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return Hashmap - contain the sc customer info and the scc trx value
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer)
			throws SCCertificateException, RemoteException;

	/**
	 * Get the SCC trx value based on the Limit Profile
	 * @param anILimitProfile of LimitProfile type
	 * @return ISCCertificateTrxValue - the SCC trx value
	 * @throws SCCertificateException
	 * @throws RemoteException on remote errors
	 */
	public ISCCertificate getSCCertificateByLimitProfile(ILimitProfile anILimitProfile) throws SCCertificateException,
			RemoteException;

	public IPartialSCCertificate getPartialSCCertificateByLimitProfile(ILimitProfile anILimitProfile)
			throws SCCertificateException, RemoteException;

	public ISCCertificateTrxValue getSCCertificateTrxByLimitProfileWithoutLimitInfo(ILimitProfile anILimitProfile)
			throws SCCertificateException, RemoteException;

	/**
	 * To get the Partial SC Certificate
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param refreshLimit indicator to refresh limit/customer info
	 * @return Hashmap - contain the partial sc customer info and the partial
	 *         scc trx value
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getPartialSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer,
			boolean refreshLimit) throws SCCertificateException, RemoteException;

	/**
	 * To get the Partial SC Certificate
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return Hashmap - contain the partial sc customer info and the partial
	 *         scc trx value
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getPartialSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer)
			throws SCCertificateException, RemoteException;

	/**
	 * Get partial scc certificate.
	 * 
	 * @param lp of type ILimitProfile
	 * @param cust of type ICMSCustomer
	 * @param refreshLimit indicator to refresh limit/customer info
	 * @param trxID transaction id
	 * @return a HashMap of [ICMSConstant.PSCC_OWNER,
	 *         ISCCertificateCustomerDetail],[ICMSConstant.PSCC,
	 *         IPartialSCCertificateTrxValue]
	 * @throws SCCertificateException on any errors encountered
	 * @throws RemoteException on any unexpected error during remote method call
	 */
	public HashMap getPartialSCCertificate(ILimitProfile lp, ICMSCustomer cust, String trxID, boolean refreshLimit)
			throws SCCertificateException, RemoteException;

	/**
	 * Get SCC certificate by transaction id.
	 * 
	 * @param lp of type ILimitProfile
	 * @param cust of type ICMSCustomer
	 * @param refreshLimit indicator to refresh limit/customer info
	 * @param trxID transaction id
	 * @return a HashMap of [ICMSConstant.SCC_OWNER,
	 *         ISCCertificateCustomerDetail],[ICMSConstant.SCC,
	 *         ISCCertificateTrxValue]
	 * @throws SCCertificateException on any errors encountered
	 * @throws RemoteException on any unexpected error during remote method call
	 */
	public HashMap getSCCertificate(ILimitProfile lp, ICMSCustomer cust, String trxID, boolean refreshLimit)
			throws SCCertificateException, RemoteException;

	/**
	 * To get the SCC customer info and the SCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @return HashMap - contain the cc customer info and the scc trx value
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID)
			throws SCCertificateException, RemoteException;

	/**
	 * To get the Partial SCC customer info and the Partial SCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @return HashMap - contain the partial sc customer info and the partial
	 *         scc trx value
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getPartialSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID)
			throws SCCertificateException, RemoteException;

	/**
	 * Maker generate the SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @param anISCCertificate of ISCCertificate type
	 * @return ISCCertificateTrxValue - the generates SCC trx value
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ISCCertificateTrxValue makerGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue, ISCCertificate anISCCertificate)
			throws SCCertificateException, RemoteException;

	/**
	 * Maker generate the Partial SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anIPartialSCCertificateTrxValue of ISCCertificateTrxValue type
	 * @param anIPartialSCCertificate of ISCCertificate type
	 * @return ISCCertificateTrxValue - the generates SCC trx value
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public IPartialSCCertificateTrxValue makerGeneratePartialSCC(ITrxContext anITrxContext,
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue, IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException, RemoteException;

	/**
	 * Checker approve the SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the generated SCC trx value
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ISCCertificateTrxValue checkerApproveGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue) throws SCCertificateException, RemoteException;

	/**
	 * Checker approve the Partial SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 *        type
	 * @return IPartialSCCertificateTrxValue - the generated Partial SCC trx
	 *         value
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public IPartialSCCertificateTrxValue checkerApproveGeneratePartialSCC(ITrxContext anITrxContext,
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws SCCertificateException,
			RemoteException;

	/**
	 * Checker reject the SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the cc certificate trx value
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ISCCertificateTrxValue checkerRejectGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue) throws SCCertificateException, RemoteException;

	/**
	 * Checker reject the Partial SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 *        type
	 * @return IPartialSCCertificateTrxValue - the Partial sc certificate trx
	 *         value
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public IPartialSCCertificateTrxValue checkerRejectGeneratePartialSCC(ITrxContext anITrxContext,
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws SCCertificateException,
			RemoteException;

	/**
	 * Maker edit the rejected SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue
	 * @param anISCCertificate of ISCCertificate
	 * @return ISCCertificateTrxValue - the cc certificate trx
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ISCCertificateTrxValue makerEditRejectedGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue, ISCCertificate anISCCertificate)
			throws SCCertificateException, RemoteException;

	/**
	 * Maker edit the rejected partial SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 * @param anIPartialSCCertificate of IPartialSCCertificate
	 * @return IPartialSCCertificateTrxValue - the partial sc certificate trx
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public IPartialSCCertificateTrxValue makerEditRejectedGeneratePartialSCC(ITrxContext anITrxContext,
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue, IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException, RemoteException;

	/**
	 * Make close the rejected SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the cc certificate trx
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ISCCertificateTrxValue makerCloseRejectedGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue) throws SCCertificateException, RemoteException;

	/**
	 * Make close the rejected Partial SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 *        type
	 * @return IPartialSCCertificateTrxValue - the partial sc certificate trx
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public IPartialSCCertificateTrxValue makerCloseRejectedGeneratePartialSCC(ITrxContext anITrxContext,
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws SCCertificateException,
			RemoteException;

	/**
	 * System close the Partial SCC
	 * @param anILimitProfile of ILimitProfile type
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public void systemClosePartialSCC(ILimitProfile anILimitProfile) throws SCCertificateException, RemoteException;

	/**
	 * System reset partial SCC. If pending create then will close it else will
	 * reset it to active
	 * @param aLimitProfileID of long type
	 * @throws SCCertificateException
	 * @throws RemoteException on remote errors
	 */
	public void systemResetPartialSCC(long aLimitProfileID) throws SCCertificateException, RemoteException;

	/**
	 * System close the SCC
	 * @param anILimitProfile of ILimitProfile type
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public void systemCloseSCC(ILimitProfile anILimitProfile) throws SCCertificateException, RemoteException;

	/**
	 * System reset SCC. If pending create then will close it else will reset it
	 * to active
	 * @param aLimitProfileID of long type
	 * @throws SCCertificateException
	 * @throws RemoteException on remote errors
	 */
	public void systemResetSCC(long aLimitProfileID) throws SCCertificateException, RemoteException;

	/**
	 * System close the Partial SCC
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 *        type
	 * @return IPartialSCCertificateTrxValue - the partial sc certificate trx
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public IPartialSCCertificateTrxValue systemClosePartialSCC(
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws SCCertificateException,
			RemoteException;

	/**
	 * System close the SCC
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the sc certificate trx
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ISCCertificateTrxValue systemCloseSCC(ISCCertificateTrxValue anISCCertificateTrxValue)
			throws SCCertificateException, RemoteException;

	/**
	 * To recompute the total amounts based on the base currency
	 * @param anISCCertificate of ISCCertificate type
	 * @return ISCCertificate - the sc certificate object
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ISCCertificate computeTotalAmounts(ISCCertificate anISCCertificate) throws SCCertificateException,
			RemoteException;

	/**
	 * To recompute the total amounts based on the base currency
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @return IPartialSCCertificate - the partial sc certificate object
	 * @throws RemoteException on remote errors
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificate computeTotalAmounts(IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException, RemoteException;

	/**
	 * To recompute the total amounts based on the base currency for only those
	 * selected items
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @return IPartialSCCertificate - the partial sc certificate object
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public IPartialSCCertificate computeTotalAmountsForIssuedPSCC(IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException, RemoteException;

	/**
	 * Search SCCertificate based on the criteria given.
	 * 
	 * @param criteria sccertificate criteria
	 * @return SearchResult containing a list of SCCertificateSearchResult
	 * @throws SCCertificateException on any errors encountered
	 * @throws RemoteException on any unexpected error during remote method call
	 */
	public SearchResult searchSCCertificate(SCCertificateSearchCriteria criteria) throws SCCertificateException,
			RemoteException;

	/**
	 * Search Partial SCCertificate based on the criteria given.
	 * 
	 * @param criteria partial sccertificate criteria
	 * @return SearchResult containing a list of
	 *         PartialSCCertificateSearchResult
	 * @throws SCCertificateException on any errors encountered
	 * @throws RemoteException on any unexpected error during remote method call
	 */
	public SearchResult searchPSCCertificate(PartialSCCertificateSearchCriteria criteria)
			throws SCCertificateException, RemoteException;
}
