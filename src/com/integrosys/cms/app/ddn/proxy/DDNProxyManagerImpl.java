/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/proxy/DDNProxyManagerImpl.java,v 1.19 2005/08/25 08:45:24 hshii Exp $
 */
package com.integrosys.cms.app.ddn.proxy;

//java
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.ddn.bus.DDNException;
import com.integrosys.cms.app.ddn.bus.DDNNotRequiredException;
import com.integrosys.cms.app.ddn.bus.DDNSearchCriteria;
import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.ddn.trx.IDDNTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This class act as a facade to the services offered by the ddn modules
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2005/08/25 08:45:24 $ Tag: $Name: $
 */
public class DDNProxyManagerImpl extends AbstractDDNProxyManager {
	/**
	 * Check if DDN is the latest certificate generated for the limit profile.
	 * 
	 * @param limitProfile of type ILimitProfile
	 * @return true if DDN is the latest cert generated, otherwise false
	 * @throws DDNException on error checking the limit profile DDN
	 */
	public boolean isDDNGeneratedLatest(ILimitProfile limitProfile) throws DDNException {
		try {
			return getDDNProxyManager().isDDNGeneratedLatest(limitProfile);
		}
		catch (RemoteException e) {
			throw new DDNException("Exception in isDDNGeneratedLatest:" + e.toString());
		}
	}

	/**
	 * Check if there is any pending generate ddn trx
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws DDNException on errors
	 */
	public boolean hasPendingGenerateDDNTrx(ILimitProfile anILimitProfile) throws DDNException {
		try {
			return getDDNProxyManager().hasPendingGenerateDDNTrx(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in hasPendingGenerateDDNTrx: " + ex.toString());
		}
	}

	/**
	 * To get the OLD DDN
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return Hashmap - contain the sc customer info and the DDN trx value
	 * @throws DDNNotRequiredException if DDN is not required
	 * @throws DDNException on errors
	 */
	/*
	 * public HashMap getDDN(ILimitProfile anILimitProfile, ICMSCustomer
	 * anICMSCustomer) throws DDNNotRequiredException, DDNException { try {
	 * return getDDNProxyManager().getDDN(anILimitProfile, anICMSCustomer); }
	 * catch(RemoteException ex) { throw new
	 * DDNException("Exception in getDDN: " + ex.toString()); } }
	 */

	public HashMap getDDN(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, List deferredList, List deferredApprovalList) throws DDNNotRequiredException,
			DDNException {
		try {
			return getDDNProxyManager().getDDN(anILimitProfile, anICMSCustomer, deferredList, deferredApprovalList);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in getDDN: " + ex.toString());
		}
	}

	public HashMap getDDN(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, boolean isLatestActive, List deferredList, List deferredApprovalList)
			throws DDNNotRequiredException, DDNException {
		try {
			return getDDNProxyManager().getDDN(anILimitProfile, anICMSCustomer, isLatestActive, deferredList, deferredApprovalList);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in getDDN: " + ex.toString());
		}
	}

	/**
	 * To get the OLD DDN customer info and the DDN trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @return HashMap - contain the cc customer info and the DDN trx value
	 * @throws DDNException on errors
	 */
	/*
	 * public HashMap getDDN(ILimitProfile anILimitProfile, ICMSCustomer
	 * anICMSCustomer, String aTrxID) throws DDNException { try { return
	 * getDDNProxyManager().getDDN(anILimitProfile, anICMSCustomer, aTrxID); }
	 * catch(RemoteException ex) { throw new
	 * DDNException("Exception in getDDN: " + ex.toString()); } }
	 */

	public HashMap getDDN(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID)
			throws DDNException {
		try {
			return getDDNProxyManager().getDDN(anILimitProfile, anICMSCustomer, aTrxID);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in getDDN: " + ex.toString());
		}
	}

	public HashMap getDDN(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID,
			boolean isLatestActive) throws DDNException {
		try {
			return getDDNProxyManager().getDDN(anILimitProfile, anICMSCustomer, aTrxID, isLatestActive);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in : " + ex.toString());
		}
	}

	/**
	 * Convert an SCC to a DDN for BCA renewal
	 * @param anITrxContext of ITrxContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @param anISCCertificate of ISCCertificate type
	 * @return IDDNTrxValue - the DDN trx value
	 * @throws DDNException
	 */
	public IDDNTrxValue convertSCCToDDN(ITrxContext anITrxContext, ILimitProfile anILimitProfile,
			ISCCertificate anISCCertificate) throws DDNException {
		try {
			return getDDNProxyManager().convertSCCToDDN(anITrxContext, anILimitProfile, anISCCertificate);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in convertSCCToDDN: " + ex.toString());
		}
	}

	public IDDNTrxValue convertPartialSCCToDDN(ITrxContext anITrxContext, ILimitProfile anILimitProfile,
			IPartialSCCertificate anIPartialSCCertificate) throws DDNException {
		try {
			return getDDNProxyManager().convertPartialSCCToDDN(anITrxContext, anILimitProfile, anIPartialSCCertificate);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in convertPartialSCCToDDN: " + ex.toString());
		}
	}

	/**
	 * Maker generate the OLD DDN
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @param anIDDN of IDDN type
	 * @return IDDNTrxValue - the generates DDN trx value
	 * @throws DDNException on errors
	 */
	/*
	 * public IDDNTrxValue makerGenerateDDN(ITrxContext anITrxContext,
	 * IDDNTrxValue anIDDNTrxValue, IDDN anIDDN) throws DDNException { try {
	 * return getDDNProxyManager().makerGenerateDDN(anITrxContext,
	 * anIDDNTrxValue, anIDDN); } catch(RemoteException ex) { throw new
	 * DDNException("Exception in makerGenerateDDN: " + ex.toString()); } }
	 */

	public IDDNTrxValue makerGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue, IDDN anIDDN)
			throws DDNException {
		try {
			return getDDNProxyManager().makerGenerateDDN(anITrxContext, anIDDNTrxValue, anIDDN);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in makerGenerateDDN: " + ex.toString());
		}
	}

	/**
	 * Checker approve the OLD DDN
	 * @param anITrxContext of ITrxContext type
	 * 
	 * @return IDDNTrxValue - the generated DDN trx value
	 * @throws DDNException on errors
	 */
	/*
	 * public IDDNTrxValue checkerApproveGenerateDDN(ITrxContext anITrxContext,
	 * IDDNTrxValue anIDDNTrxValue) throws DDNException { try { return
	 * getDDNProxyManager().checkerApproveGenerateDDN(anITrxContext,
	 * anIDDNTrxValue); } catch(RemoteException ex) { throw new
	 * DDNException("Exception in checkerApproveGenerateDDN: " + ex.toString());
	 * } }
	 */
	// ck 02mar05
	public IDDNTrxValue checkerApproveGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue)
			throws DDNException {
		try {
			return getDDNProxyManager().checkerApproveGenerateDDN(anITrxContext, anIDDNTrxValue);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in checkerApproveGenerateDDN: " + ex.toString());
		}
	}

	/**
	 * Checker reject the OLD DDN
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @return IDDNTrxValue - the ddn trx value
	 * @throws DDNException on errors
	 */
	/*
	 * public IDDNTrxValue checkerRejectGenerateDDN(ITrxContext anITrxContext,
	 * IDDNTrxValue anIDDNTrxValue) throws DDNException { try { return
	 * getDDNProxyManager().checkerRejectGenerateDDN(anITrxContext,
	 * anIDDNTrxValue); } catch(RemoteException ex) { throw new
	 * DDNException("Exception in checkerRejectGenerateDDN: " + ex.toString());
	 * } }
	 */
	// ck 02mar05
	public IDDNTrxValue checkerRejectGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue)
			throws DDNException {
		try {
			return getDDNProxyManager().checkerRejectGenerateDDN(anITrxContext, anIDDNTrxValue);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in checkerRejectGenerateDDN: " + ex.toString());
		}
	}

	/**
	 * Maker edit the rejected OLD DDN
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue
	 * @param anIDDN of IDDN
	 * @return IDDNTrxValue - the ddn trx
	 * @throws DDNException on errors
	 */
	/*
	 * public IDDNTrxValue makerEditRejectedGenerateDDN(ITrxContext
	 * anITrxContext, IDDNTrxValue anIDDNTrxValue, IDDN anIDDN) throws
	 * DDNException { try { return
	 * getDDNProxyManager().makerEditRejectedGenerateDDN(anITrxContext,
	 * anIDDNTrxValue, anIDDN); } catch(RemoteException ex) { throw new
	 * DDNException("Exception in makerEditRejectedGenerateDDN: " +
	 * ex.toString()); } }
	 */
	// ck 02mar05
	public IDDNTrxValue makerEditRejectedGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue, IDDN anIDDN)
			throws DDNException {
		try {
			return getDDNProxyManager().makerEditRejectedGenerateDDN(anITrxContext, anIDDNTrxValue, anIDDN);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in makerEditRejectedGenerateDDN: " + ex.toString());
		}
	}

	/**
	 * Make close the rejected OLD DDN
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @return IDDNTrxValue - the ddn trx
	 * @throws DDNException on errors
	 */
	/*
	 * public IDDNTrxValue makerCloseRejectedGenerateDDN(ITrxContext
	 * anITrxContext, IDDNTrxValue anIDDNTrxValue) throws DDNException { try {
	 * return getDDNProxyManager().makerCloseRejectedGenerateDDN(anITrxContext,
	 * anIDDNTrxValue); } catch(RemoteException ex) { throw new
	 * DDNException("Exception in makerCloseRejectedGenerateDDN: " +
	 * ex.toString()); } }
	 */
	// ck 02mar05
	public IDDNTrxValue makerCloseRejectedGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue)
			throws DDNException {
		try {
			return getDDNProxyManager().makerCloseRejectedGenerateDDN(anITrxContext, anIDDNTrxValue);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in makerCloseRejectedGenerateDDN: " + ex.toString());
		}
	}

	/**
	 * System close the OLD DDN
	 * @param anILimitProfile of ILimitProfile type
	 * 
	 * @throws DDNException on errors
	 */
	/*
	 * public void systemCloseDDN(ILimitProfile anILimitProfile) throws
	 * DDNException { try {
	 * getDDNProxyManager().systemCloseDDN(anILimitProfile); }
	 * catch(RemoteException ex) { throw new
	 * DDNException("Exception in systemCloseDDN: " + ex.toString()); } }
	 */
	// ck 02mar05
	public void systemCloseDDN(ILimitProfile anILimitProfile) throws DDNException {
		try {
			getDDNProxyManager().systemCloseDDN(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in systemCloseDDN: " + ex.toString());
		}
	}

	/**
	 * System close the OLD DDN
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @return IDDNTrxValue - the ddn trx
	 * @throws DDNException on errors
	 */
	/*
	 * public IDDNTrxValue systemCloseDDN(IDDNTrxValue anIDDNTrxValue) throws
	 * DDNException { try { return
	 * getDDNProxyManager().systemCloseDDN(anIDDNTrxValue); }
	 * catch(RemoteException ex) { throw new
	 * DDNException("Exception in systemCloseDDN: " + ex.toString()); } }
	 */
	// ck 02mar05
	public IDDNTrxValue systemCloseDDN(IDDNTrxValue anIDDNTrxValue) throws DDNException {
		try {
			return getDDNProxyManager().systemCloseDDN(anIDDNTrxValue);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in systemCloseDDN: " + ex.toString());
		}
	}

	/**
	 * To mark the DDN as invalid as SCC has been issued
	 * 
	 * @throws ConcurrentUpdateException DDNException
	 */
	public void sccIssued(long aLimitProfileID) throws ConcurrentUpdateException, DDNException {
		try {
			getDDNProxyManager().sccIssued(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in sccIssued: " + ex.toString());
		}
	}

	/**
	 * To recompute the total amounts based on the base currency
	 * @param anIDDN of IDDN type
	 * @return IDDN - the sc certificate object
	 * @throws DDNException on errors
	 */
	public IDDN computeTotalAmounts(IDDN anIDDN) throws DDNException {
		try {
			return getDDNProxyManager().computeTotalAmounts(anIDDN);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in computeTotalAmounts: " + ex.toString());
		}
	}

	/**
	 * To recompute the total amounts based on the base currency only for those
	 * items that are to be generated
	 * @param anIDDN of IDDN type
	 * @return IDDN - the DDN object
	 * @throws DDNException on errors
	 */
	public IDDN computeTotalAmountForGeneratedItems(IDDN anIDDN) throws DDNException {
		try {
			return getDDNProxyManager().computeTotalAmountForGeneratedItems(anIDDN);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in computeTotalAmountForGeneratedItems: " + ex.toString());
		}
	}

	/**
	 * To create a default DDN for a renewed limit profile The DDN amount for
	 * each existing limit to be default to the SCC/PSCC/CCC activated limit
	 * amount
	 * @param anILimitProfile of ILimitProfile type
	 * @throws DDNException on errors
	 */
	/*
	 * public void systemCreateDefaultDDN(ILimitProfile anILimitProfile) throws
	 * DDNException { try {
	 * getDDNProxyManager().systemCreateDefaultDDN(anILimitProfile); }
	 * catch(RemoteException ex) { throw new
	 * DDNException("Exception in systemCreateDefaultDDN: " + ex.toString()); }
	 * }
	 */

	/**
	 * Get previous active DDN.
	 * 
	 * @param limitProfile limit profile
	 * @param customer of type ICMSCustomer
	 * @param trxID current transaction id
	 * @return a hashmap of [ICMSConstant.DDN_OWNER, IDDNCustomerDetail],
	 *         [ICMSConstant.DDN, IDDNTrxValue]
	 * @throws DDNException on any errors encountered
	 */
	public HashMap getPreviousDDN(ILimitProfile limitProfile, ICMSCustomer customer, String trxID) throws DDNException {
		try {
			return getDDNProxyManager().getPreviousDDN(limitProfile, customer, trxID);
		}
		catch (Exception e) {
			throw new DDNException("Exception in getPreviousActiveDDN:" + e.toString());
		}
	}

	/**
	 * Get previous active DDN.
	 * 
	 * @param limitProfile limit profile
	 * @param customer of type ICMSCustomer
	 * @return a hashmap of [ICMSConstant.DDN_OWNER, IDDNCustomerDetail],
	 *         [ICMSConstant.DDN, IDDNTrxValue]
	 * @throws DDNException on any errors encountered
	 */
	public HashMap getPreviousDDN(ILimitProfile limitProfile, ICMSCustomer customer) throws DDNException {
		try {
			return getDDNProxyManager().getPreviousDDN(limitProfile, customer);
		}
		catch (Exception e) {
			throw new DDNException("Exception in getPreviousActiveDDN:" + e.toString());
		}
	}

	// ck 02mar05
	public void systemCreateDefaultDDN(ILimitProfile anILimitProfile) throws DDNException {
		try {
			getDDNProxyManager().systemCreateDefaultDDN(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in systemCreateDefaultDDN: " + ex.toString());
		}
	}

	public IDDN populateNewBCADefaultDDN(ILimitProfile anILimitProfile) throws DDNException {
		try {
			return getDDNProxyManager().populateNewBCADefaultDDN(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in populateNewBCADefaultDDN: " + ex.toString());
		}
	}

    public IDDNTrxValue getDDNTrxValue(ILimitProfile limitProfile, boolean isLatestActive) throws DDNException {
        try {
            return getDDNProxyManager().getDDNTrxValue(limitProfile, isLatestActive);    
        } catch (RemoteException ex) {
            throw new DDNException("Exception in getDDNTrxValue : " + ex.toString());
        }
    }

	/**
	 * To get the remote handler for the DDN proxy manager
	 * @return SBDDNProxyManager - the remote handler for the DDN proxy manager
	 */
	private SBDDNProxyManager getDDNProxyManager() {
		SBDDNProxyManager proxymgr = (SBDDNProxyManager) BeanController.getEJB(ICMSJNDIConstant.SB_DDN_PROXY_JNDI,
				SBDDNProxyManagerHome.class.getName());
		return proxymgr;
	}

	protected IDDN getDDNWithoutLimitInfo(long aLimitProfileID, String type) throws DDNException {
		return null;
	}

	protected int getNoOfDDN(DDNSearchCriteria aCriteria) throws DDNException {
		return ICMSConstant.INT_INVALID_VALUE;
	}

	protected void rollback() {
	}

	protected long convertAmount(Amount anAmount, CurrencyCode aCurrencyCode) throws DDNException {
		return ICMSConstant.LONG_INVALID_VALUE;
	}
}
