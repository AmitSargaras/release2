/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/proxy/ISCCertificateProxyManager.java,v 1.16 2005/08/26 13:14:34 lyng Exp $
 */
package com.integrosys.cms.app.sccertificate.proxy;

//java
import java.util.HashMap;

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
 * This interface defines the list of attributes that will be available to the
 * generation of a sc certificate
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.16 $
 * @since $Date: 2005/08/26 13:14:34 $ Tag: $Name: $
 */
public interface ISCCertificateProxyManager {
	/**
	 * Check if SCC has been generated for a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if SCC is generated and false otherwise
	 * @throws SCCertificateException on errors
	 */
	public boolean isSCCFullyGenerated(ILimitProfile anILimitProfile) throws SCCertificateException;

	/**
	 * Check if there is any pending generate SCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws SCCertificateException on errors
	 */
	public boolean hasPendingGenerateSCCTrx(ILimitProfile anILimitProfile) throws SCCertificateException;

	/**
	 * Check if there is any pending generate Partial SCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws SCCertificateException on errors
	 */
	public boolean hasPendingGeneratePartialSCCTrx(ILimitProfile anILimitProfile) throws SCCertificateException;

	/**
	 * To get the SC Certificate
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param refreshLimit indicator to refresh limit/customer info
	 * @return Hashmap - contain the sc customer info and the scc trx value
	 * @throws SCCertificateException on errors
	 */
	public HashMap getSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, boolean refreshLimit)
			throws SCCertificateException;

	/**
	 * To get the SC Certificate
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return Hashmap - contain the sc customer info and the scc trx value
	 * @throws SCCertificateException on errors
	 */
	public HashMap getSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer)
			throws SCCertificateException;

	/**
	 * To get the Partial SC Certificate
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param refreshLimit indicator to refresh limit/customer info
	 * @return Hashmap - contain the partial sc customer info and the partial
	 *         scc trx value
	 * @throws SCCertificateException on errors
	 */
	public HashMap getPartialSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer,
			boolean refreshLimit) throws SCCertificateException;

	/**
	 * To get the Partial SC Certificate
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return Hashmap - contain the partial sc customer info and the partial
	 *         scc trx value
	 * @throws SCCertificateException on errors
	 */
	public HashMap getPartialSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer)
			throws SCCertificateException;

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
			throws SCCertificateException;

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
			throws SCCertificateException;

	/**
	 * To get the SCC customer info and the SCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @return HashMap - contain the cc customer info and the scc trx value
	 * @throws SCCertificateException on errors
	 */
	public HashMap getSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID)
			throws SCCertificateException;

	/**
	 * Get the SCC trx value based on the Limit Profile
	 * @param anILimitProfile of LimitProfile type
	 * @return ISCCertificateTrxValue - the SCC trx value
	 * @throws SCCertificateException
	 */
	public ISCCertificate getSCCertificateByLimitProfile(ILimitProfile anILimitProfile) throws SCCertificateException;

	/**
	 * Search SCCertificate based on the criteria given.
	 * 
	 * @param criteria sccertificate criteria
	 * @return SearchResult containing a list of SCCertificateSearchResult
	 * @throws SCCertificateException on any errors encountered
	 */
	public SearchResult searchSCCertificate(SCCertificateSearchCriteria criteria) throws SCCertificateException;

	/**
	 * Search Partial SCCertificate based on the criteria given.
	 * 
	 * @param criteria partial sccertificate criteria
	 * @return SearchResult containing a list of
	 *         PartialSCCertificateSearchResult
	 * @throws SCCertificateException on any errors encountered
	 */
	public SearchResult searchPSCCertificate(PartialSCCertificateSearchCriteria criteria) throws SCCertificateException;

	/**
	 * Get the PSCC trx value based on the Limit Profile
	 * @param anILimitProfile of LimitProfile type
	 * @return IPartialSCCertificate - the PSCC value
	 * @throws SCCertificateException
	 */
	public IPartialSCCertificate getPartialSCCertificateByLimitProfile(ILimitProfile anILimitProfile)
			throws SCCertificateException;

	/**
	 * Get the SCC trx value based on the Limit Profile
	 * @param anILimitProfile of LimitProfile type
	 * @return ISCCertificateTrxValue - the SCC trx value
	 * @throws SCCertificateException
	 */
	public ISCCertificateTrxValue getSCCertificateTrxByLimitProfileWithoutLimitInfo(ILimitProfile anILimitProfile)
			throws SCCertificateException;

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
			throws SCCertificateException;

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
			throws SCCertificateException;

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
			throws SCCertificateException;

	/**
	 * Checker approve the SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the generated SCC trx value
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificateTrxValue checkerApproveGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue) throws SCCertificateException;

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
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws SCCertificateException;

	/**
	 * Checker reject the SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the cc certificate trx value
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificateTrxValue checkerRejectGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue) throws SCCertificateException;

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
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws SCCertificateException;

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
			throws SCCertificateException;

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
			throws SCCertificateException;

	/**
	 * Make close the rejected SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the cc certificate trx
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificateTrxValue makerCloseRejectedGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue) throws SCCertificateException;

	/**
	 * Make close the rejected Partial SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 *        type
	 * @return IPartialSCCertificateTrxValue - the partial sc certificate trx
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificateTrxValue makerCloseRejectedGeneratePartialSCC(ITrxContext anITrxContext,
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws SCCertificateException;

	/**
	 * System close the Partial SCC
	 * @param anILimitProfile of ILimitProfile type
	 * @throws SCCertificateException on errors
	 */
	public void systemClosePartialSCC(ILimitProfile anILimitProfile) throws SCCertificateException;

	/**
	 * System reset partial SCC. If pending create then will close it else will
	 * reset it to active
	 * @param aLimitProfileID of long type
	 * @throws SCCertificateException
	 */
	public void systemResetPartialSCC(long aLimitProfileID) throws SCCertificateException;

	/**
	 * System close the SCC
	 * @param anILimitProfile of ILimitProfile type
	 * @throws SCCertificateException on errors
	 */
	public void systemCloseSCC(ILimitProfile anILimitProfile) throws SCCertificateException;

	/**
	 * System reset SCC. If pending create then will close it else will reset it
	 * to active
	 * @param aLimitProfileID of long type
	 * @throws SCCertificateException
	 */
	public void systemResetSCC(long aLimitProfileID) throws SCCertificateException;

	/**
	 * System close the Partial SCC
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 *        type
	 * @return IPartialSCCertificateTrxValue - the partial sc certificate trx
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificateTrxValue systemClosePartialSCC(
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws SCCertificateException;

	/**
	 * System close the SCC
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the sc certificate trx
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificateTrxValue systemCloseSCC(ISCCertificateTrxValue anISCCertificateTrxValue)
			throws SCCertificateException;

	/**
	 * To recompute the total amounts based on the base currency
	 * @param anISCCertificate of ISCCertificate type
	 * @return ISCCertificate - the sc certificate object
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificate computeTotalAmounts(ISCCertificate anISCCertificate) throws SCCertificateException;

	/**
	 * To recompute the total amounts based on the base currency
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @return IPartialSCCertificate - the partial sc certificate object
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificate computeTotalAmounts(IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException;

	/**
	 * To recompute the total amounts based on the base currency for only those
	 * selected items
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @return IPartialSCCertificate - the partial sc certificate object
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificate computeTotalAmountsForIssuedPSCC(IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException;
}
