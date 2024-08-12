/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/proxy/IDDNProxyManager.java,v 1.21 2005/08/25 08:45:24 hshii Exp $
 */
package com.integrosys.cms.app.ddn.proxy;

//java
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.ddn.bus.DDNException;
import com.integrosys.cms.app.ddn.bus.DDNNotRequiredException;
import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.ddn.trx.IDDNTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the list of attributes that will be available to the
 * generation of a ddn
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.21 $
 * @since $Date: 2005/08/25 08:45:24 $ Tag: $Name: $
 */
public interface IDDNProxyManager {
	/**
	 * Check if DDN is the latest certificate generated for the limit profile.
	 * 
	 * @param limitProfile of type ILimitProfile
	 * @return true if DDN is the latest cert generated, otherwise false
	 * @throws DDNException on error checking the limit profile DDN
	 */
	public boolean isDDNGeneratedLatest(ILimitProfile limitProfile) throws DDNException;

	/**
	 * Check if there is any pending generate ddn trx
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws DDNException on errors
	 */
	public boolean hasPendingGenerateDDNTrx(ILimitProfile anILimitProfile) throws DDNException;

	/**
	 * To get the DDN
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return Hashmap - contain the ddn customer info and the DDN trx value
	 * @throws DDNNotRequiredException if DDN is not required
	 * @throws DDNException on errors
	 */
	public HashMap getDDN(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, List deferredList, List deferredApprovalList) throws DDNNotRequiredException,
			DDNException;

	/**
	 * To get the DDN
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param isLatestActivee of boolean
	 * @return Hashmap - contain the ddn customer info and the DDN trx value
	 * @throws DDNNotRequiredException if DDN is not required
	 * @throws DDNException on errors
	 */
	public HashMap getDDN(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, boolean isLatestActive, List deferredList, List deferredApprovalList)
			throws DDNNotRequiredException, DDNException;

	/**
	 * To get the DDN customer info and the DDN trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type @ param aTrxID of String type
	 * @param isLatestActive of boolean
	 * @return HashMap - contain the cc customer info and the DDN trx value
	 * @throws DDNException on errors
	 */
	public HashMap getDDN(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID,
			boolean isLatestActive) throws DDNException;

	/**
	 * To get the DDN customer info and the DDN trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type @ param aTrxID of String type
	 * @return HashMap - contain the cc customer info and the DDN trx value
	 * @throws DDNException on errors
	 */
	public HashMap getDDN(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID)
			throws DDNException;

	// public HashMap getNewBCADDN(ILimitProfile anILimitProfile, ICMSCustomer
	// anICMSCustomer) throws DDNNotRequiredException, DDNException;
	// public HashMap getNewBCADDN(ILimitProfile anILimitProfile, ICMSCustomer
	// anICMSCustomer, String aTrxID) throws DDNException;

	/**
	 * Get previous active LLI.
	 * 
	 * @param limitProfile limit profile
	 * @param customer of type ICMSCustomer
	 * @param trxID current transaction id
	 * @return a hashmap of [ICMSConstant.DDN_OWNER, IDDNCustomerDetail],
	 *         [ICMSConstant.DDN, IDDNTrxValue]
	 * @throws DDNException on any errors encountered
	 */
	public HashMap getPreviousDDN(ILimitProfile limitProfile, ICMSCustomer customer, String trxID) throws DDNException;

	/**
	 * Get previous active DDN.
	 * 
	 * @param limitProfile limit profile
	 * @param customer of type ICMSCustomer
	 * @return a hashmap of [ICMSConstant.DDN_OWNER, IDDNCustomerDetail],
	 *         [ICMSConstant.DDN, IDDNTrxValue]
	 * @throws DDNException on any errors encountered
	 */
	public HashMap getPreviousDDN(ILimitProfile limitProfile, ICMSCustomer customer) throws DDNException;

	/**
	 * Convert an SCC to a DDN for BCA renewal
	 * @param anITrxContext of ITrxContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @param anISCCertificate of ISCCertificate type
	 * @return IDDNTrxValue - the DDN trx value
	 * @throws DDNException
	 */
	public IDDNTrxValue convertSCCToDDN(ITrxContext anITrxContext, ILimitProfile anILimitProfile,
			ISCCertificate anISCCertificate) throws DDNException;

	public IDDNTrxValue convertPartialSCCToDDN(ITrxContext anITrxContext, ILimitProfile anILimitProfile,
			IPartialSCCertificate anISCCertificate) throws DDNException;

	/**
	 * Maker generate the DDN
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @param anIDDN of IDDN type
	 * @return IDDNTrxValue - the generates DDN trx value
	 * @throws DDNException on errors
	 */
	// public IDDNTrxValue makerGenerateDDN(ITrxContext anITrxContext,
	// IDDNTrxValue anIDDNTrxValue, IDDN anIDDN) throws DDNException;
	// ccm
	/**
	 * Maker generate the DDN for new BCA
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @param anIDDN of IDDN type
	 * @return IDDNTrxValue - the generates DDN trx value
	 * @throws DDNException on errors
	 */
	public IDDNTrxValue makerGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue, IDDN anIDDN)
			throws DDNException;

	/**
	 * Checker approve the DDN
	 * @param anITrxContext of ITrxContext type
	 * @return IDDNTrxValue - the generated DDN trx value
	 * @throws DDNException on errors
	 */
	public IDDNTrxValue checkerApproveGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue)
			throws DDNException;

	/**
	 * Checker reject the DDN
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @return IDDNTrxValue - the ddn trx value
	 * @throws DDNException on errors
	 */
	public IDDNTrxValue checkerRejectGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue)
			throws DDNException;

	/**
	 * Maker edit the rejected DDN
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue
	 * @param anIDDN of IDDN
	 * @return IDDNTrxValue - the ddn trx
	 * @throws DDNException on errors
	 */
	public IDDNTrxValue makerEditRejectedGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue, IDDN anIDDN)
			throws DDNException;

	/**
	 * Make close the rejected DDN
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @return IDDNTrxValue - the ddn trx
	 * @throws DDNException on errors
	 */
	public IDDNTrxValue makerCloseRejectedGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue)
			throws DDNException;

	/**
	 * System close the DDN
	 * @param anILimitProfile of ILimitProfile type
	 * @throws DDNException on errors
	 */
	public void systemCloseDDN(ILimitProfile anILimitProfile) throws DDNException;

	/**
	 * System close the DDN
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @return IDDNTrxValue - the ddn trx
	 * @throws DDNException on errors
	 */
	public IDDNTrxValue systemCloseDDN(IDDNTrxValue anIDDNTrxValue) throws DDNException;

	/**
	 * To mark the DDN as invalid as SCC has been issued
	 * @param aLimitProfileID of long type
	 * @throws ConcurrentUpdateException DDNException
	 */
	public void sccIssued(long aLimitProfileID) throws ConcurrentUpdateException, DDNException;

	/**
	 * To recompute the total amounts based on the base currency
	 * @param anIDDN of IDDN type
	 * @return IDDN - the DDN object
	 * @throws DDNException on errors
	 */
	public IDDN computeTotalAmounts(IDDN anIDDN) throws DDNException;

	/**
	 * To recompute the total amounts based on the base currency only for those
	 * items that are to be generated
	 * @param anIDDN of IDDN type
	 * @return IDDN - the DDN object
	 * @throws DDNException on errors
	 */
	public IDDN computeTotalAmountForGeneratedItems(IDDN anIDDN) throws DDNException;

	/**
	 * To create a default DDN for a renewed limit profile The DDN amount for
	 * each existing limit to be default to the SCC/PSCC/CCC activated limit
	 * amount
	 * @param anILimitProfile of ILimitProfile type
	 * @throws DDNException on errors
	 */
	public void systemCreateDefaultDDN(ILimitProfile anILimitProfile) throws DDNException;

	public IDDN populateNewBCADefaultDDN(ILimitProfile anILimitProfile) throws DDNException;

	/**
	 * Get a list of limits to be issued LLI.
	 * 
	 * @param bca of type ILimitProfile
	 * @return a list of limits to be issued LLI
	 * @throws DDNNotRequiredException if LLI is not required
	 * @throws DDNException on any other errors encountered
	 */
	// public IDDNItem[] getDDNItems (ILimitProfile bca) throws
	// DDNNotRequiredException, DDNException;

    public IDDNTrxValue getDDNTrxValue(ILimitProfile limitProfile, boolean isLatestActive) throws DDNException;
}
