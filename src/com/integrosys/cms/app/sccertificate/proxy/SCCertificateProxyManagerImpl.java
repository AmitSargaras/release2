/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/proxy/SCCertificateProxyManagerImpl.java,v 1.16 2005/08/26 13:14:34 lyng Exp $
 */
package com.integrosys.cms.app.sccertificate.proxy;

//java
import java.rmi.RemoteException;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
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
 * This class act as a facade to the services offered by the sc certificate
 * modules
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.16 $
 * @since $Date: 2005/08/26 13:14:34 $ Tag: $Name: $
 */
public class SCCertificateProxyManagerImpl implements ISCCertificateProxyManager {
	/**
	 * Check if SCC has been generated for a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if SCC is generated and false otherwise
	 * @throws SCCertificateException on errors
	 */
	public boolean isSCCFullyGenerated(ILimitProfile anILimitProfile) throws SCCertificateException {
		try {
			return getCertificateProxyManager().isSCCFullyGenerated(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in isSCCFullyGenerated: " + ex.toString());
		}
	}

	/**
	 * Check if there is any pending generate SCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws SCCertificateException on errors
	 */
	public boolean hasPendingGenerateSCCTrx(ILimitProfile anILimitProfile) throws SCCertificateException {
		try {
			return getCertificateProxyManager().hasPendingGenerateSCCTrx(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in hasPendingGenerateSCCTrx: " + ex.toString());
		}
	}

	/**
	 * Check if there is any pending generate Partial SCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws SCCertificateException on errors
	 */
	public boolean hasPendingGeneratePartialSCCTrx(ILimitProfile anILimitProfile) throws SCCertificateException {
		try {
			return getCertificateProxyManager().hasPendingGeneratePartialSCCTrx(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in hasPendingGeneratePartialSCCTrx: " + ex.toString());
		}
	}

	/**
	 * To get the SC Certificate
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param refreshLimit indicator to refresh limit/customer info
	 * @return Hashmap - contain the sc customer info and the scc trx value
	 * @throws SCCertificateException on errors
	 */
	public HashMap getSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, boolean refreshLimit)
			throws SCCertificateException {
		try {
			return getCertificateProxyManager().getSCCertificate(anILimitProfile, anICMSCustomer, refreshLimit);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getSCCertificate: " + ex.toString());
		}
	}

	/**
	 * To get the SC Certificate
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return Hashmap - contain the sc customer info and the scc trx value
	 * @throws SCCertificateException on errors
	 */
	public HashMap getSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer)
			throws SCCertificateException {
		try {
			return getCertificateProxyManager().getSCCertificate(anILimitProfile, anICMSCustomer);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getSCCertificate: " + ex.toString());
		}
	}

	/**
	 * Get the SCC trx value based on the Limit Profile
	 * @param anILimitProfile of LimitProfile type
	 * @return ISCCertificateTrxValue - the SCC trx value
	 * @throws SCCertificateException
	 */
	public ISCCertificate getSCCertificateByLimitProfile(ILimitProfile anILimitProfile) throws SCCertificateException {
		try {
			return getCertificateProxyManager().getSCCertificateByLimitProfile(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getSCCertificateByLimitProfile: " + ex.toString());
		}
	}

	public IPartialSCCertificate getPartialSCCertificateByLimitProfile(ILimitProfile anILimitProfile)
			throws SCCertificateException {
		try {
			return getCertificateProxyManager().getPartialSCCertificateByLimitProfile(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getPartialSCCertificateByLimitProfile: " + ex.toString());
		}
	}

	public ISCCertificateTrxValue getSCCertificateTrxByLimitProfileWithoutLimitInfo(ILimitProfile anILimitProfile)
			throws SCCertificateException {
		try {
			return getCertificateProxyManager().getSCCertificateTrxByLimitProfileWithoutLimitInfo(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getSCCertificateTrxByLimitProfileWithoutLimitInfo: "
					+ ex.toString());
		}
	}

	/**
	 * To get the Partial SC Certificate
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param refreshLimit indicator to refresh limit/ customer info
	 * @return Hashmap - contain the partial sc customer info and the partial
	 *         scc trx value
	 * @throws SCCertificateException on errors
	 */
	public HashMap getPartialSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer,
			boolean refreshLimit) throws SCCertificateException {
		try {
			return getCertificateProxyManager().getPartialSCCertificate(anILimitProfile, anICMSCustomer, refreshLimit);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getPartialSCCertificate: " + ex.toString());
		}
	}

	/**
	 * To get the Partial SC Certificate
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return Hashmap - contain the partial sc customer info and the partial
	 *         scc trx value
	 * @throws SCCertificateException on errors
	 */
	public HashMap getPartialSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer)
			throws SCCertificateException {
		try {
			return getCertificateProxyManager().getPartialSCCertificate(anILimitProfile, anICMSCustomer);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getPartialSCCertificate: " + ex.toString());
		}
	}

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
	 */
	public HashMap getPartialSCCertificate(ILimitProfile lp, ICMSCustomer cust, String trxID, boolean refreshLimit)
			throws SCCertificateException {
		try {
			return getCertificateProxyManager().getPartialSCCertificate(lp, cust, trxID, refreshLimit);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getPartialSCCertificate by transaction id: " + ex.toString());
		}
	}

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
	 */
	public HashMap getSCCertificate(ILimitProfile lp, ICMSCustomer cust, String trxID, boolean refreshLimit)
			throws SCCertificateException {
		try {
			return getCertificateProxyManager().getSCCertificate(lp, cust, trxID, refreshLimit);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getSCCertificate by transaction id: " + ex.toString());
		}
	}

	/**
	 * To get the SCC customer info and the SCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @return HashMap - contain the cc customer info and the scc trx value
	 * @throws SCCertificateException on errors
	 */
	public HashMap getSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID)
			throws SCCertificateException {
		try {
			return getCertificateProxyManager().getSCCertificate(anILimitProfile, anICMSCustomer, aTrxID);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getSCCertificate: " + ex.toString());
		}
	}

	/**
	 * To get the Partial SCC customer info and the Partial SCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @return HashMap - contain the partial sc customer info and the partial
	 *         scc trx value
	 * @throws SCCertificateException on errors
	 */
	public HashMap getPartialSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID)
			throws SCCertificateException {
		try {
			return getCertificateProxyManager().getPartialSCCertificate(anILimitProfile, anICMSCustomer, aTrxID);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getSCCertificate: " + ex.toString());
		}
	}

	/**
	 * Maker generate the SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @param anISCCertificate of ISCCertificate type
	 * @return ISCCertificateTrxValue - the generates SCC trx value
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificateTrxValue makerGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue, ISCCertificate anISCCertificate)
			throws SCCertificateException {
		try {
			return getCertificateProxyManager().makerGenerateSCC(anITrxContext, anISCCertificateTrxValue,
					anISCCertificate);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in makerGenerateSCC: " + ex.toString());
		}
	}

	/**
	 * Maker generate the Partial SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 *        type
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @return IPartialSCCertificateTrxValue - the generates Partial SCC trx
	 *         value
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificateTrxValue makerGeneratePartialSCC(ITrxContext anITrxContext,
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue, IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException {
		try {
			return getCertificateProxyManager().makerGeneratePartialSCC(anITrxContext, anIPartialSCCertificateTrxValue,
					anIPartialSCCertificate);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in makerGeneratePartialSCC: " + ex.toString());
		}
	}

	/**
	 * Checker approve the SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the generated SCC trx value
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificateTrxValue checkerApproveGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue) throws SCCertificateException {
		try {
			return getCertificateProxyManager().checkerApproveGenerateSCC(anITrxContext, anISCCertificateTrxValue);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in checkerApproveGenerateSCC: " + ex.toString());
		}
	}

	/**
	 * Checker approve the Partial SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 *        type
	 * @return IPartialSCCertificateTrxValue - the generated Partial SCC trx
	 *         value
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificateTrxValue checkerApproveGeneratePartialSCC(ITrxContext anITrxContext,
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws SCCertificateException {
		try {
			return getCertificateProxyManager().checkerApproveGeneratePartialSCC(anITrxContext,
					anIPartialSCCertificateTrxValue);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in checkerApproveGeneratePartialSCC: " + ex.toString());
		}
	}

	/**
	 * Checker reject the SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the cc certificate trx value
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificateTrxValue checkerRejectGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue) throws SCCertificateException {
		try {
			return getCertificateProxyManager().checkerRejectGenerateSCC(anITrxContext, anISCCertificateTrxValue);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in checkerRejectGenerateSCC: " + ex.toString());
		}
	}

	/**
	 * Checker reject the Partial SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 *        type
	 * @return IPartialSCCertificateTrxValue - the partial sc certificate trx
	 *         value
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificateTrxValue checkerRejectGeneratePartialSCC(ITrxContext anITrxContext,
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws SCCertificateException {
		try {
			return getCertificateProxyManager().checkerRejectGeneratePartialSCC(anITrxContext,
					anIPartialSCCertificateTrxValue);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in checkerRejectGeneratePartialSCC: " + ex.toString());
		}
	}

	/**
	 * Maker edit the rejected SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue
	 * @param anISCCertificate of ISCCertificate
	 * @return ISCCertificateTrxValue - the cc certificate trx
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificateTrxValue makerEditRejectedGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue, ISCCertificate anISCCertificate)
			throws SCCertificateException {
		try {
			return getCertificateProxyManager().makerEditRejectedGenerateSCC(anITrxContext, anISCCertificateTrxValue,
					anISCCertificate);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in makerEditRejectedGenerateSCC: " + ex.toString());
		}
	}

	/**
	 * Maker edit the rejected Partial SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 * @param anIPartialSCCertificate of IPartialSCCertificate
	 * @return IPartialSCCertificateTrxValue - the partial sc certificate trx
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificateTrxValue makerEditRejectedGeneratePartialSCC(ITrxContext anITrxContext,
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue, IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException {
		try {
			return getCertificateProxyManager().makerEditRejectedGeneratePartialSCC(anITrxContext,
					anIPartialSCCertificateTrxValue, anIPartialSCCertificate);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in makerEditRejectedGeneratePartialSCC: " + ex.toString());
		}
	}

	/**
	 * Make close the rejected SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the cc certificate trx
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificateTrxValue makerCloseRejectedGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue) throws SCCertificateException {
		try {
			return getCertificateProxyManager().makerCloseRejectedGenerateSCC(anITrxContext, anISCCertificateTrxValue);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in makerCloseRejectedGenerateSCC: " + ex.toString());
		}
	}

	/**
	 * Search SCCertificate based on the criteria given.
	 * 
	 * @param criteria sccertificate criteria
	 * @return SearchResult containing a list of SCCertificateSearchResult
	 * @throws SCCertificateException on any errors encountered
	 */
	public SearchResult searchSCCertificate(SCCertificateSearchCriteria criteria) throws SCCertificateException {
		try {
			return getCertificateProxyManager().searchSCCertificate(criteria);
		}
		catch (Exception e) {
			throw new SCCertificateException("Exception in searchSCCertificate: " + e.toString());
		}
	}

	/**
	 * Search Partial SCCertificate based on the criteria given.
	 * 
	 * @param criteria partial sccertificate criteria
	 * @return SearchResult containing a list of
	 *         PartialSCCertificateSearchResult
	 * @throws SCCertificateException on any errors encountered
	 */
	public SearchResult searchPSCCertificate(PartialSCCertificateSearchCriteria criteria) throws SCCertificateException {
		try {
			return getCertificateProxyManager().searchPSCCertificate(criteria);
		}
		catch (Exception e) {
			throw new SCCertificateException("Exception in searchPSCCertificate: " + e.toString());
		}
	}

	/**
	 * Make close the rejected Partial SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 *        type
	 * @return IPartialSCCertificateTrxValue - the partial sc certificate trx
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificateTrxValue makerCloseRejectedGeneratePartialSCC(ITrxContext anITrxContext,
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws SCCertificateException {
		try {
			return getCertificateProxyManager().makerCloseRejectedGeneratePartialSCC(anITrxContext,
					anIPartialSCCertificateTrxValue);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in makerCloseRejectedGeneratePartialSCC: " + ex.toString());
		}
	}

	/**
	 * System close all the Partial SCC under a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @throws SCCertificateException on errors
	 */
	public void systemClosePartialSCC(ILimitProfile anILimitProfile) throws SCCertificateException {
		try {
			getCertificateProxyManager().systemClosePartialSCC(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in systemClosePartialSCC: " + ex.toString());
		}
	}

	/**
	 * System reset partial SCC. If pending create then will close it else will
	 * reset it to active
	 * @param aLimitProfileID of long type
	 * @throws SCCertificateException
	 */
	public void systemResetPartialSCC(long aLimitProfileID) throws SCCertificateException {
		try {
			getCertificateProxyManager().systemResetPartialSCC(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in systemResetPartialSCC: " + ex.toString());
		}
	}

	/**
	 * System close the SCC under a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @throws SCCertificateException on errors
	 */
	public void systemCloseSCC(ILimitProfile anILimitProfile) throws SCCertificateException {
		try {
			getCertificateProxyManager().systemCloseSCC(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in systemCloseSCC: " + ex.toString());
		}
	}

	/**
	 * System reset SCC. If pending create then will close it else will reset it
	 * to active
	 * @param aLimitProfileID of long type
	 * @throws SCCertificateException
	 */
	public void systemResetSCC(long aLimitProfileID) throws SCCertificateException {
		try {
			getCertificateProxyManager().systemResetSCC(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in systemResetSCC: " + ex.toString());
		}
	}

	/**
	 * System close the Partial SCC
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 *        type
	 * @return IPartialSCCertificateTrxValue - the partial sc certificate trx
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificateTrxValue systemClosePartialSCC(
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws SCCertificateException {
		try {
			return getCertificateProxyManager().systemClosePartialSCC(anIPartialSCCertificateTrxValue);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in systemClosePartialSCC: " + ex.toString());
		}
	}

	/**
	 * System close the SCC
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the sc certificate trx
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificateTrxValue systemCloseSCC(ISCCertificateTrxValue anISCCertificateTrxValue)
			throws SCCertificateException {
		try {
			return getCertificateProxyManager().systemCloseSCC(anISCCertificateTrxValue);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in systemCloseSCC: " + ex.toString());
		}
	}

	/**
	 * To recompute the total amounts based on the base currency
	 * @param anISCCertificate of ISCCertificate type
	 * @return ISCCertificate - the sc certificate object
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificate computeTotalAmounts(ISCCertificate anISCCertificate) throws SCCertificateException {
		try {
			return getCertificateProxyManager().computeTotalAmounts(anISCCertificate);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in computeTotalAmounts: " + ex.toString());
		}
	}

	/**
	 * To recompute the total amounts based on the base currency
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @return IPartialSCCertificate - the partial sc certificate object
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificate computeTotalAmounts(IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException {
		try {
			return getCertificateProxyManager().computeTotalAmounts(anIPartialSCCertificate);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in computeTotalAmounts: " + ex.toString());
		}
	}

	/**
	 * To recompute the total amounts based on the base currency for only those
	 * selected items
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @return IPartialSCCertificate - the partial sc certificate object
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificate computeTotalAmountsForIssuedPSCC(IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException {
		try {
			return getCertificateProxyManager().computeTotalAmountsForIssuedPSCC(anIPartialSCCertificate);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in computeTotalAmountsForIssuedPSCC: " + ex.toString());
		}
	}

	/**
	 * To get the remote handler for the certificate proxy manager
	 * @return SBSCCertificateProxyManager - the remote handler for the
	 *         certificate proxy manager
	 */
	private SBSCCertificateProxyManager getCertificateProxyManager() {
		SBSCCertificateProxyManager proxymgr = (SBSCCertificateProxyManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_SCCERTIFICATE_PROXY_JNDI, SBSCCertificateProxyManagerHome.class.getName());
		return proxymgr;
	}
}
