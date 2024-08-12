/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/proxy/SBDDNProxyManager.java,v 1.16 2005/08/25 08:45:24 hshii Exp $
 */
package com.integrosys.cms.app.ddn.proxy;

//java
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJBObject;

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
 * Session bean remote interface for the services provided by the ddn proxy
 * manager
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.16 $
 * @since $Date: 2005/08/25 08:45:24 $ Tag: $Name: $
 */
public interface SBDDNProxyManager extends EJBObject {
	/**
	 * Check if DDN is the latest certificate generated for the limit profile.
	 * 
	 * @param limitProfile of type ILimitProfile
	 * @return true if DDN is the latest cert generated, otherwise false
	 * @throws DDNException on error checking the limit profile DDN
	 * @throws RemoteException on any unexpected error during remote method call
	 */
	public boolean isDDNGeneratedLatest(ILimitProfile limitProfile) throws DDNException, RemoteException;

	/**
	 * Check if there is any pending generate DDN trx
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public boolean hasPendingGenerateDDNTrx(ILimitProfile anILimitProfile) throws DDNException, RemoteException;

	/**
	 * To get the DDN
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return Hashmap - contain the ddn customer info and the DDN trx value3
	 * @throws DDNNotRequiredException is DDN is not required
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getDDN(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, List deferredList, List deferredApprovalList) throws DDNNotRequiredException,
			DDNException, RemoteException;

	/**
	 * To get the DDN
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param isLatestActive of boolean
	 * @return Hashmap - contain the ddn customer info and the DDN trx value3
	 * @throws DDNNotRequiredException is DDN is not required
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getDDN(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, boolean isLatestActive, List deferredList, List deferredApprovalList)
			throws DDNNotRequiredException, DDNException, RemoteException;

	/**
	 * To get the DDN customer info and the DDN trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @return HashMap - contain the cc customer info and the DDN trx value
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getDDN(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID)
			throws DDNException, RemoteException;

	/**
	 * To get the DDN customer info and the DDN trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @param isLatestActive of boolean
	 * @return HashMap - contain the cc customer info and the DDN trx value
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getDDN(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID,
			boolean isLatestActive) throws DDNException, RemoteException;

	// public HashMap getNewBCADDN(ILimitProfile anILimitProfile, ICMSCustomer
	// anICMSCustomer) throws DDNNotRequiredException, DDNException,
	// RemoteException;
	// public HashMap getNewBCADDN(ILimitProfile anILimitProfile, ICMSCustomer
	// anICMSCustomer, String aTrxID) throws DDNException, RemoteException;

	/**
	 * Convert an SCC to a DDN for BCA renewal
	 * @param anITrxContext of ITrxContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @param anISCCertificate of ISCCertificate type
	 * @return IDDNTrxValue - the DDN trx value
	 * @throws DDNException , RemoteException
	 */
	public IDDNTrxValue convertSCCToDDN(ITrxContext anITrxContext, ILimitProfile anILimitProfile,
			ISCCertificate anISCCertificate) throws DDNException, RemoteException;

	public IDDNTrxValue convertPartialSCCToDDN(ITrxContext anITrxContext, ILimitProfile anILimitProfile,
			IPartialSCCertificate anISCCertificate) throws DDNException, RemoteException;

	/**
	 * Maker generate the DDN
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @param anIDDN of IDDN type
	 * @return IDDNTrxValue - the generates DDN trx value
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public IDDNTrxValue makerGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue, IDDN anIDDN)
			throws DDNException, RemoteException;

	// public IDDNTrxValue makerGenerateNewBcaDDN(ITrxContext anITrxContext,
	// IDDNTrxValue anIDDNTrxValue, IDDN anIDDN) throws DDNException,
	// RemoteException;

	/**
	 * Checker approve the DDN
	 * @param anITrxContext of ITrxContext type
	 * @return IDDNTrxValue - the generated DDN trx value
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public IDDNTrxValue checkerApproveGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue)
			throws DDNException, RemoteException;

	// public IDDNTrxValue checkerApproveGenerateLLI(ITrxContext anITrxContext,
	// IDDNTrxValue anIDDNTrxValue) throws DDNException, RemoteException;

	/**
	 * Checker reject the DDN
	 * @param anITrxContext of ITrxContext type
	 * @return IDDNTrxValue - the ddn trx value
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public IDDNTrxValue checkerRejectGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDN3TrxValue)
			throws DDNException, RemoteException;

	// public IDDNTrxValue checkerRejectGenerateLLI(ITrxContext anITrxContext,
	// IDDNTrxValue anIDDN3TrxValue) throws DDNException, RemoteException;

	/**
	 * Maker edit the rejected DDN
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue
	 * @param anIDDN of IDDN
	 * @return IDDNTrxValue - the ddn trx
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public IDDNTrxValue makerEditRejectedGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue, IDDN anIDDN)
			throws DDNException, RemoteException;

	// public IDDNTrxValue makerEditRejectedGenerateLLI(ITrxContext
	// anITrxContext, IDDNTrxValue anIDDNTrxValue, IDDN anIDDN) throws
	// DDNException, RemoteException;

	/**
	 * Make close the rejected DDN
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @return IDDNTrxValue - the ddn trx
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public IDDNTrxValue makerCloseRejectedGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue)
			throws DDNException, RemoteException;

	// public IDDNTrxValue makerCloseRejectedGenerateLLI(ITrxContext
	// anITrxContext, IDDNTrxValue anIDDNTrxValue) throws
	// DDNException,RemoteException;

	/**
	 * System close the DDN
	 * @param anILimitProfile of ILimitProfile type
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public void systemCloseDDN(ILimitProfile anILimitProfile) throws DDNException, RemoteException;

	// public void systemCloseLLI(ILimitProfile anILimitProfile) throws
	// DDNException, RemoteException;

	/**
	 * System close the DDN
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @return IDDNTrxValue - the ddn trx
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public IDDNTrxValue systemCloseDDN(IDDNTrxValue anIDDNTrxValue) throws DDNException, RemoteException;

	// public IDDNTrxValue systemCloseLLI(IDDNTrxValue anIDDNTrxValue) throws
	// DDNException, RemoteException;

	/**
	 * To mark the DDN as invalid as SCC has been issued
	 * @param aLimitProfileID of long type
	 * @throws ConcurrentUpdateException , DDNException
	 * @throws RemoteException on remote errors
	 */
	public void sccIssued(long aLimitProfileID) throws ConcurrentUpdateException, DDNException, RemoteException;

	/**
	 * To recompute the total amounts based on the base currency
	 * @param anIDDN of IDDN type
	 * @return IDDN - the sc certificate object
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public IDDN computeTotalAmounts(IDDN anIDDN) throws DDNException, RemoteException;

	/**
	 * To recompute the total amounts based on the base currency only for those
	 * items that are to be generated
	 * @param anIDDN of IDDN type
	 * @return IDDN - the DDN object
	 * @throws DDNException on errors
	 * @throws RemoteException on errors
	 */
	public IDDN computeTotalAmountForGeneratedItems(IDDN anIDDN) throws DDNException, RemoteException;

	/**
	 * To create a default DDN for a renewed limit profile The DDN amount for
	 * each existing limit to be default to the SCC/PSCC/CCC activated limit
	 * amount
	 * @param anILimitProfile of ILimitProfile type @ return IDDNTrxValue - the
	 *        default DDN trx value create
	 * @throws DDNException on errors
	 * @throws RemoteException on errors
	 */
	public void systemCreateDefaultDDN(ILimitProfile anILimitProfile) throws DDNException, RemoteException;

	// public void systemCreateDefaultLLI(ILimitProfile anILimitProfile) throws
	// DDNException, RemoteException;
	public IDDN populateNewBCADefaultDDN(ILimitProfile anILimitProfile) throws DDNException, RemoteException;

	/**
	 * Get previous active LLI.
	 * 
	 * @param limitProfile limit profile
	 * @param customer of type ICMSCustomer
	 * @param trxID current transaction id
	 * @return a hashmap of [ICMSConstant.DDN_OWNER, IDDNCustomerDetail],
	 *         [ICMSConstant.DDN, IDDNTrxValue]
	 * @throws DDNException on any errors encountered
	 * @throws RemoteException on unexpected errors during remote method call
	 */
	public HashMap getPreviousDDN(ILimitProfile limitProfile, ICMSCustomer customer, String trxID) throws DDNException,
			RemoteException;

	/**
	 * Get previous active DDN.
	 * 
	 * @param limitProfile limit profile
	 * @param customer of type ICMSCustomer
	 * @return a hashmap of [ICMSConstant.DDN_OWNER, IDDNCustomerDetail],
	 *         [ICMSConstant.DDN, IDDNTrxValue]
	 * @throws DDNException on any errors encountered
	 * @throws RemoteException on unexpected errors during remote method call
	 */
	public HashMap getPreviousDDN(ILimitProfile limitProfile, ICMSCustomer customer) throws DDNException,
			RemoteException;

    public IDDNTrxValue getDDNTrxValue(ILimitProfile limitProfile, boolean isLatestActive) throws DDNException, RemoteException;
}
