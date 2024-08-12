/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/proxy/AbstractDDNProxyManager.java,v 1.68 2006/11/15 12:49:42 jychong Exp $
 */
package com.integrosys.cms.app.ddn.proxy;

//java
import java.util.*;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.exception.OFAException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateException;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificateItem;
import com.integrosys.cms.app.cccertificate.proxy.CCCertificateProxyManagerFactory;
import com.integrosys.cms.app.cccertificate.proxy.ICCCertificateProxyManager;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.bus.BaseCurrency;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICreditGrade;
import com.integrosys.cms.app.customer.bus.ICreditStatus;
import com.integrosys.cms.app.customer.bus.OBCreditGrade;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.ddn.bus.DDNCollateralInfo;
import com.integrosys.cms.app.ddn.bus.DDNDAO;
import com.integrosys.cms.app.ddn.bus.DDNException;
import com.integrosys.cms.app.ddn.bus.DDNNotRequiredException;
import com.integrosys.cms.app.ddn.bus.DDNSearchCriteria;
import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.ddn.bus.IDDNCustomerDetail;
import com.integrosys.cms.app.ddn.bus.IDDNItem;
import com.integrosys.cms.app.ddn.bus.OBDDN;
import com.integrosys.cms.app.ddn.bus.OBDDNCustomerDetail;
import com.integrosys.cms.app.ddn.bus.OBDDNItem;
import com.integrosys.cms.app.ddn.trx.DDNTrxControllerFactory;
import com.integrosys.cms.app.ddn.trx.IDDNTrxValue;
import com.integrosys.cms.app.ddn.trx.OBDDNTrxValue;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificateItem;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificateItem;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateDAOFactory;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateException;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateSearchCriteria;
import com.integrosys.cms.app.sccertificate.proxy.ISCCertificateProxyManager;
import com.integrosys.cms.app.sccertificate.proxy.SCCertificateProxyManagerFactory;
import com.integrosys.cms.app.transaction.*;

/**
 * This abstract class will contains all the biz related logic that is
 * independent of any technology implementation such as EJB
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.68 $
 * @since $Date: 2006/11/15 12:49:42 $ Tag: $Name: $
 */
public abstract class AbstractDDNProxyManager implements IDDNProxyManager {
	/**
	 * Check if DDN is the latest certificate generated for the limit profile.
	 * 
	 * @param limitProfile of type ILimitProfile
	 * @return true if DDN is the latest cert generated, otherwise false
	 * @throws DDNException on error checking the limit profile DDN
	 */
	public boolean isDDNGeneratedLatest(ILimitProfile limitProfile) throws DDNException {
		try {
			return new DDNDAO().isDDNLatestCert(limitProfile.getLimitProfileID());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new DDNException("Error in getting isDDNGeneratedLatest for : " + limitProfile.getLimitProfileID()
					+ " " + e.toString());
		}
	}

	/**
	 * Check if there is any pending generate DDN trx
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws DDNException on errors
	 */
	public boolean hasPendingGenerateDDNTrx(ILimitProfile anILimitProfile) throws DDNException {
		DDNSearchCriteria criteria = new DDNSearchCriteria();
		criteria.setLimitProfileID(anILimitProfile.getLimitProfileID());
		String[] trxStatusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_PENDING_UPDATE,
				ICMSConstant.STATE_REJECTED };
		criteria.setTrxStatusList(trxStatusList);
		int count = getNoOfDDN(criteria);
		if (count == 0) {
			return false;
		}
		return true;
	}

	public HashMap getDDN(ILimitProfile limitProfile, ICMSCustomer customer, List deferredList, List deferredApprovalList) throws DDNNotRequiredException,
			DDNException {
		return getDDN(limitProfile, customer, false, deferredList, deferredApprovalList);
	}

	// cr36
	/**
	 * Get DDN for the given BCA and customer.
	 * 
	 * @param limitProfile limit profile
	 * @param customer limit profile's customer
	 * @return a hashmap containing [ICMSConstant.DDN_OWNER,
	 *         IDDNCustomerDetail], [ICMSConstant.DDN, IDDNTrxValue]
	 * @throws DDNNotRequiredException if DDN is not required
	 * @throws DDNException on any other error encountered
	 */
	public HashMap getDDN(ILimitProfile limitProfile, ICMSCustomer customer, boolean isLatestActive, List deferredList, List deferredApprovalList)
			throws DDNNotRequiredException, DDNException {
		if (limitProfile == null) {
			throw new DDNException("The ILimitProfile is null !!!");
		}

		if (customer == null) {
			throw new DDNException("The ICMSCustomer is null !!!");
		}

		if ((limitProfile.getNonDeletedLimits() == null) || (limitProfile.getNonDeletedLimits().length == 0)) {
			throw new DDNNotRequiredException("There is no limit in this limit profile");
		}

		if (isSCCGeneratedForLimitProfile(limitProfile)) {
			DDNException exp = new DDNException("DDN not allowed as SCC has been generated");
			exp.setErrorCode(ICMSErrorCodes.DDN_NOT_ALLOWED);
			throw exp;
		}

        if (deferredList.size() == 0) {
            DDNException exp = new DDNException("DDN not required as there are no deferred documents.");
            exp.setErrorCode(ICMSErrorCodes.DDN_NO_DEFERRED_DOC);
            throw exp;
        }

		// IDDNItem[] ddnItems = getDDNItems (limitProfile);
		IDDNItem[] ddnItems = getDDNItemList(limitProfile, deferredList, deferredApprovalList);

		IDDNTrxValue trxValue = getDDNTrxValue(limitProfile, isLatestActive);
		if (trxValue == null) {
			systemCreateDefaultDDN(limitProfile, ddnItems);
			trxValue = getDDNTrxValue(limitProfile, isLatestActive);
		}
		else {
			IDDN cert = trxValue.getDDN();

			ArrayList newList = new ArrayList();
			IDDNItem[] existingItems = cert.getDDNItemList();

            if (existingItems != null) {
			for (int i = 0; i < ddnItems.length; i++) {
				boolean found = false;

				for (int j = 0; j < existingItems.length; j++) {
					//if (ddnItems[i].getLimitType().equals(existingItems[j].getLimitType())
					//		&& (ddnItems[i].getLimitID() == existingItems[j].getLimitID())) {
                    if (ddnItems[i].getDocNumber() == existingItems[j].getDocNumber()) {
						newList.add(existingItems[j]);
						found = true;
						break;
					}
				}
				if (!found) {
					newList.add(ddnItems[i]);
				}
			}

            cert.setDDNItemList((IDDNItem[]) newList.toArray(new IDDNItem[0]));
            } else {
                //TODO : Double check on this changes.
                cert.setDDNItemList(new IDDNItem[0]);
            }

			//computeDDNAmount(cert);
		}
		HashMap map = new HashMap();
		map.put(ICMSConstant.DDN_OWNER, getDDNCustomerInfo(limitProfile, customer));
		map.put(ICMSConstant.DDN, trxValue);

		return map;
	}

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
	public HashMap getPreviousDDN(ILimitProfile limitProfile, ICMSCustomer customer) throws DDNException {
		if (limitProfile == null) {
			throw new DDNException("The ILimitProfile is null !!!");
		}

		if (customer == null) {
			throw new DDNException("The ICMSCustomer is null !!!");
		}

		if ((limitProfile.getNonDeletedLimits() == null) || (limitProfile.getNonDeletedLimits().length == 0)) {
			throw new DDNException("There is no limit in this limit profile");
		}

		HashMap map = new HashMap();
		try {
			IDDNTrxValue trxValue = getPreviousDDNTrxValue(limitProfile);

			map.put(ICMSConstant.DDN_OWNER, getDDNCustomerInfo(limitProfile, customer));
			map.put(ICMSConstant.DDN, trxValue);
		}
		catch (DDNNotRequiredException e) {
			throw new DDNException("Exception in getPreviousDDN", e);
		}

		return map;
	}

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
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_PREVIOUS_DDN);
		IDDNTrxValue trxValue = new OBDDNTrxValue();
		trxValue.setTransactionID(trxID);
		trxValue = operateDDN(trxValue, param);

		try {
			HashMap map = new HashMap();
			if (trxValue.getStagingDDN() != null) {
				populateDDNInfo(limitProfile, trxValue.getStagingDDN());
			}
			if (trxValue.getDDN() != null) {
				populateDDNInfo(limitProfile, trxValue.getDDN());
			}
			map.put(ICMSConstant.DDN_OWNER, getDDNCustomerInfo(limitProfile, customer));
			map.put(ICMSConstant.DDN, trxValue);
			return map;
		}
		catch (DDNNotRequiredException ex) {
			throw new DDNException("Exception in getDDN", ex);
		}
	}

	private boolean isSCCGeneratedForLimitProfile(ILimitProfile aLimitProfile) throws DDNException {
		SCCertificateSearchCriteria aCriteria = new SCCertificateSearchCriteria();

		aCriteria.setLimitProfileID(aLimitProfile.getLimitProfileID());
		String[] trxStatusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_PENDING_UPDATE,
				ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_ACTIVE };
		aCriteria.setTrxStatusList(trxStatusList);

		try {
			return (SCCertificateDAOFactory.getSCCertificateDAO().getNoOfSCCertificate(aCriteria) != 0);
		}
		catch (Exception e) {
			throw new DDNException("Exception in isSCCGeneratedForLimitProfile", e);
		}

	}

	// cr36 ck 02mar05
	/**
	 * Helper method to populate DDN limit info.
	 * 
	 * @param lp limit profile
	 * @param ddn DDN
	 * @throws DDNNotRequiredException
	 * @throws DDNException
	 */
	private void populateDDNInfo(ILimitProfile lp, IDDN ddn) throws DDNNotRequiredException, DDNException {
		IDDNItem[] items = ddn.getDDNItemList();
		IDDNItem[] fullList = getDDNItemList(lp, new ArrayList(), new ArrayList());
		IDDNItem[] deletedList = null;

        if (items != null) {
		for (int i = 0; i < items.length; i++) {
			boolean found = false;
			for (int j = 0; j < fullList.length; j++) {
				//if (items[i].getLimitType().equals(fullList[j].getLimitType())
				//		&& (items[i].getLimitID() == fullList[j].getLimitID())) {
                if (items[i].getDocNumber() == fullList[j].getDocNumber()) {
					fullList[j].setDDNItemID(items[i].getDDNItemID());
					fullList[j].setDDNItemRef(items[i].getDDNItemRef());
					fullList[j].setIsDeletedInd(items[i].getIsDeletedInd());
					fullList[j].setDDNAmount(items[i].getDDNAmount());
					fullList[j].setIsDDNIssuedInd(items[i].getIsDDNIssuedInd());
					fullList[j].setIssuedDate(items[i].getIssuedDate());
					fullList[j].setMaturityDate(items[i].getMaturityDate());
					fullList[j].setProductType(items[i].getProductType());
					fullList[j].setApprovalLimitDate(items[i].getApprovalLimitDate());
					fullList[j].setCoBorrowName(items[i].getCoBorrowName());
					fullList[j].setCoBorrowLegalID(items[i].getCoBorrowLegalID());
					fullList[j].setOutLimitRef(items[i].getOutLimitRef());
					fullList[j].setSecurityTypes(items[i].getSecurityTypes());
					fullList[j].setSecurityIDs(items[i].getSecurityIDs());
					fullList[j].setBkgLoctnOrg(items[i].getBkgLoctnOrg());
					fullList[j].setBkgLoctnCtry(items[i].getBkgLoctnCtry());
					fullList[j].setApprovedLimitAmt(items[i].getApprovedLimitAmt());
					fullList[j].setApprovedLimitAmtCcy(items[i].getApprovedLimitAmtCcy());

					items[i] = fullList[j];
					found = true;
					break;
				}
			}

			if (!found) {
				if (deletedList == null) {
					deletedList = getDeletedDDNItemList(lp);
				}
				for (int j = 0; j < deletedList.length; j++) {
					//if (items[i].getLimitType().equals(deletedList[j].getLimitType())
					//		&& (items[i].getLimitID() == deletedList[j].getLimitID())) {
                    if (items[i].getDocNumber() == deletedList[j].getDocNumber()) {
						deletedList[j].setDDNItemID(items[i].getDDNItemID());
						deletedList[j].setDDNItemRef(items[i].getDDNItemRef());
						deletedList[j].setIsDeletedInd(items[i].getIsDeletedInd());
						deletedList[j].setDDNAmount(items[i].getDDNAmount());
						deletedList[j].setIsDDNIssuedInd(items[i].getIsDDNIssuedInd());
						deletedList[j].setIssuedDate(items[i].getIssuedDate());
						deletedList[j].setMaturityDate(items[i].getMaturityDate());
						//
						deletedList[j].setProductType(items[i].getProductType());
						deletedList[j].setApprovalLimitDate(items[i].getApprovalLimitDate());
						deletedList[j].setCoBorrowName(items[i].getCoBorrowName());
						deletedList[j].setCoBorrowLegalID(items[i].getCoBorrowLegalID());
						deletedList[j].setOutLimitRef(items[i].getOutLimitRef());
						deletedList[j].setSecurityTypes(items[i].getSecurityTypes());
						deletedList[j].setSecurityIDs(items[i].getSecurityIDs());
						deletedList[j].setBkgLoctnOrg(items[i].getBkgLoctnOrg());
						deletedList[j].setBkgLoctnCtry(items[i].getBkgLoctnCtry());
						deletedList[j].setApprovedLimitAmt(items[i].getApprovedLimitAmt());
						deletedList[j].setApprovedLimitAmtCcy(items[i].getApprovedLimitAmtCcy());

						items[i] = deletedList[j];
						break;
					}
				}
			}
		}
        }

		//computeDDNAmount(ddn);
	}

	public HashMap getDDN(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID)
			throws DDNException {
		return getDDN(anILimitProfile, anICMSCustomer, aTrxID, false);
	}

	// cr36 ck 02mar05
	public HashMap getDDN(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID,
			boolean isLatestActive) throws DDNException {
		try {
			HashMap map = new HashMap();
			IDDNTrxValue trxValue = getDDNTrxValue(aTrxID, isLatestActive);
			if (trxValue.getStagingDDN() != null) {
				populateDDNInfo(anILimitProfile, trxValue.getStagingDDN());
			}
			if (trxValue.getDDN() != null) {
				populateDDNInfo(anILimitProfile, trxValue.getDDN());
				// need to massage the staging and actual certificate to cater
				// for the
				// checker screen
				mergeDDN(trxValue.getStagingDDN(), trxValue.getDDN());
			}
			map.put(ICMSConstant.DDN_OWNER, getDDNCustomerInfo(anILimitProfile, anICMSCustomer));
			map.put(ICMSConstant.DDN, trxValue);
			return map;
		}
		catch (DDNNotRequiredException ex) {
			throw new DDNException("Exception in getDDN", ex);
		}
	}

	/**
	 * ck 02mar05 To create a default DDN for a renewed limit profile The DDN
	 * amount for each existing limit to be default to the SCC/PSCC/CCC
	 * activated limit amount
	 * @param anILimitProfile of ILimitProfile type
	 * 
	 * @throws DDNException on errors
	 */
	public void systemCreateDefaultDDN(ILimitProfile anILimitProfile) throws DDNException {
		try {
			IDDNItem[] itemList = getDDNItemList(anILimitProfile, new ArrayList(), new ArrayList());
			if ((itemList == null) || (itemList.length == 0)) {
				return;
			}
			IDDN ddn = new OBDDN();
			ddn.setDDNItemList(itemList);
			ddn.setDocumentType("DDN");
			setDefaultValue(anILimitProfile, ddn);
			IDDNTrxValue trxValue = formulateDDNTrxValue(new OBTrxContext(), ddn);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_CREATE_DDN);
			operateDDN(trxValue, param);
		}
		catch (OFAException ex) {
			throw new DDNException("Caught OFAException at systemCreateDefaultDDN", ex);
		}
	}

	/**
	 * System creates default DDN with the given DDN Items.
	 * 
	 * @throws DDNException on any errors encountered
	 */
	private void systemCreateDefaultDDN(ILimitProfile limitProfile, IDDNItem[] ddnItems) throws DDNException {
		try {
			IDDN ddn = new OBDDN();
			ddn.setDDNItemList(ddnItems);
			ddn.setDocumentType("DDN");
			setDefaultValue(limitProfile, ddn);
			IDDNTrxValue trxValue = formulateDDNTrxValue(new OBTrxContext(), ddn);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_CREATE_DDN);
			operateDDN(trxValue, param);
		}
		catch (Exception ex) {
			throw new DDNException("Caught Exception at systemCreateDefaultDDN (ILimitProfile, IDDNItem[])", ex);
		}
	}

	// test w cr 36
	public IDDN populateNewBCADefaultDDN(ILimitProfile anILimitProfile) throws DDNException {
		try {
			IDDNItem[] itemList = getDDNItemList(anILimitProfile, new ArrayList(), new ArrayList());
			DefaultLogger.debug(this, "here~~~~~");
			if ((itemList == null) || (itemList.length == 0)) {
				DefaultLogger.debug(this, "itemList is null~~~~~");
				return null;
			}
			IDDN ddn = new OBDDN();
			ddn.setDDNItemList(itemList);
			ddn.setDocumentType("DDN");
			setDefaultValue(anILimitProfile, ddn);
			if (ddn != null) {
				IDDNItem[] di = ddn.getDDNItemList();
				if (di != null) {
					for (int i = 0; i < di.length; i++) {
						DefaultLogger.debug(this, "ddnitem: " + di[i].getDDNItemID() + ", value: "
								+ di[i].getApprovedLimitAmount());
					}
				}
			}
			return ddn;
			// IDDNTrxValue trxValue = formulateTrxValue(new OBTrxContext(),
			// ddn);
			// OBCMSTrxParameter param = new OBCMSTrxParameter();
			// param.setAction (ICMSConstant.ACTION_SYSTEM_CREATE_DDN);
			// operate (trxValue, param);
		}
		catch (OFAException ex) {
			throw new DDNException("Caught OFAException at systemCreateDefaultDDN", ex);
		}
	}

	private void setDefaultValue(ILimitProfile anILimitProfile, IDDN anIDDN) throws DDNException {
		setDefaultValueForCleanLimits(anILimitProfile, anIDDN.getCleanDDNItemList());
		setDefaultValueForSecuredLimits(anILimitProfile, anIDDN.getNotCleanDDNItemList());
		anIDDN.setLimitProfileID(anILimitProfile.getLimitProfileID());
	}

	private void setDefaultValueForCleanLimits(ILimitProfile anILimitProfile, IDDNItem[] anIDDNItemList)
			throws DDNException {
		if ((anIDDNItemList == null) || (anIDDNItemList.length == 0)) {
			return;
		}
		setDefaultBasedOnCCC(anILimitProfile, ICMSConstant.CHECKLIST_MAIN_BORROWER, anILimitProfile.getCustomerID(),
				anIDDNItemList);
	}

	private void setDefaultValueForSecuredLimits(ILimitProfile anILimitProfile, IDDNItem[] anIDDNItemList)
			throws DDNException {
		if ((anIDDNItemList == null) || (anIDDNItemList.length == 0)) {
			return;
		}
		setDefaultBasedOnSCC(anILimitProfile, anIDDNItemList);
		setDefaultBasedOnPSCC(anILimitProfile, anIDDNItemList);
	}

	private void setDefaultBasedOnCCC(ILimitProfile anILimitProfile, String aCategory, long aOwnerID,
			IDDNItem[] anIDDNItemList) {
		try {
			ICCCertificateProxyManager proxy = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
			ICCCertificate ccc = proxy.getCCCertificate(ICMSConstant.CHECKLIST_MAIN_BORROWER, anILimitProfile
					.getLimitProfileID(), aOwnerID);
			if (ccc == null) {
				return;
			}
			ICCCertificateItem[] cccItemList = ccc.getCCCertificateItemList();
			if ((cccItemList == null) || (cccItemList.length == 0)) {
				return;
			}
			DefaultLogger.debug(this, "Number of CCC Items: " + cccItemList.length);
			for (int ii = 0; ii < cccItemList.length; ii++) {
				DefaultLogger.debug(this, "CCC Item List: " + cccItemList[ii]);
				for (int jj = 0; jj < anIDDNItemList.length; jj++) {
					DefaultLogger.debug(this, "LimitType: " + cccItemList[ii].getLimitType());
					DefaultLogger.debug(this, "LimitID: " + cccItemList[ii].getLimitID());
					if (cccItemList[ii].getLimitType().equals(anIDDNItemList[jj].getLimitType())
							&& (cccItemList[ii].getLimitID() == anIDDNItemList[jj].getLimitID())) {
						anIDDNItemList[jj].setDDNAmount(cccItemList[ii].getActivatedAmount());
					}
				}
			}
		}
		catch (CCCertificateException ex) {
			DefaultLogger.info(this, "CCCertificateException enctr in setDefaultBasedOnCCC");
		}
	}

	private void setDefaultBasedOnSCC(ILimitProfile anILimitProfile, IDDNItem[] anIDDNItemList) {
		try {
			ISCCertificateProxyManager proxy = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
			ISCCertificate scc = proxy.getSCCertificateByLimitProfile(anILimitProfile);
			if (scc == null) {
				return;
			}
			ISCCertificateItem[] sccItemList = scc.getSCCItemList();
			if ((sccItemList == null) || (sccItemList.length == 0)) {
				return;
			}
			DefaultLogger.debug(this, "Number SCC Items: " + sccItemList.length);
			for (int ii = 0; ii < sccItemList.length; ii++) {
				for (int jj = 0; jj < anIDDNItemList.length; jj++) {
					if (sccItemList[ii].getLimitType().equals(anIDDNItemList[jj].getLimitType())
							&& (sccItemList[ii].getLimitID() == anIDDNItemList[jj].getLimitID())) {
						anIDDNItemList[jj].setDDNAmount(sccItemList[ii].getActivatedAmount());
					}
				}
			}
		}
		catch (SCCertificateException ex) {
			DefaultLogger.info(this, "SCCertificateException enctr in setDefaultBasedOnSCC");
		}
	}

	private void setDefaultBasedOnPSCC(ILimitProfile anILimitProfile, IDDNItem[] anIDDNItemList) {
		try {
			ISCCertificateProxyManager proxy = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
			IPartialSCCertificate pscc = proxy.getPartialSCCertificateByLimitProfile(anILimitProfile);
			if (pscc == null) {
				return;
			}
			IPartialSCCertificateItem[] psccItemList = pscc.getPartialSCCItemList();
			if ((psccItemList == null) || (psccItemList.length == 0)) {
				return;
			}
			DefaultLogger.debug(this, "Number of PSCC Items: " + psccItemList.length);
			for (int ii = 0; ii < anIDDNItemList.length; ii++) {
				DefaultLogger.debug(this, "DDN  Item: " + anIDDNItemList[ii]);
				if ((anIDDNItemList[ii].getDDNAmount() == null)
						|| (anIDDNItemList[ii].getDDNAmount().getCurrencyCode() == null)) {
					for (int jj = 0; jj < psccItemList.length; jj++) {
						DefaultLogger.debug(this, "PSCC Item: " + psccItemList[jj]);
						if (anIDDNItemList[ii].getLimitType().equals(psccItemList[jj].getLimitType())
								&& (anIDDNItemList[ii].getLimitID() == psccItemList[jj].getLimitID())
								&& psccItemList[jj].getIsPartialSCCIssued()) {
							anIDDNItemList[ii].setDDNAmount(psccItemList[jj].getActivatedAmount());
						}
					}
				}
			}
		}
		catch (SCCertificateException ex) {
			DefaultLogger.info(this, "SCCertificateException enctr in setDefaultBasedOnPSCC");
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
		if (anITrxContext == null) {
			throw new DDNException("The ITrxContext is null !!!");
		}
		if (anILimitProfile == null) {
			throw new DDNException("The ILimitProfile is null !!!");
		}
		if (anISCCertificate == null) {
			throw new DDNException("The ISCCertificate is null !!!");
		}
		ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
		ISCCertificateItem[] itemList = anISCCertificate.getSCCItemList();
		if ((limitList == null) || (limitList.length == 0)) {
			throw new DDNException("There is no limit under the BCA !!!");
		}
		if ((itemList == null) || (itemList.length == 0)) {
			throw new DDNException("There is no item under the ISCCertificate !!!");
		}
		IDDN ddn = new OBDDN();
		ddn.setLimitProfileID(anILimitProfile.getLimitProfileID());
		ArrayList resultList = new ArrayList();

		DefaultLogger.debug(this, "Number of Limits: " + limitList.length);
		for (int ii = 0; ii < limitList.length; ii++) {
			DefaultLogger.debug(this, "Limit Existing Indicator: " + limitList[ii].getExistingInd());
			if ((limitList[ii].getExistingInd())
					&& (!limitList[ii].getLimitStatus().equals(ICMSConstant.STATE_DELETED))) {
				ISCCertificateItem sccItem = getSCCertificateItem(limitList[ii], itemList);
				if (sccItem != null) {
					IDDNItem ddnItem = new OBDDNItem();
					// ddnItem.setLimit(limitList[ii]);
					// TODO: format the limit info into DDN items
					ddnItem.setDDNAmount(sccItem.getActivatedAmount());
					resultList.add(ddnItem);
				}
			}
		}
		if (resultList.size() > 0) {
			IDDNItem[] ddnItems = (IDDNItem[]) resultList.toArray(new IDDNItem[resultList.size()]);
			ddn.setDDNItemList(ddnItems);
			// ddn.setDateGenerated(new Date());
			removeDDNNotIssued(ddn);
			IDDNTrxValue trxValue = formulateDDNTrxValue(anITrxContext, ddn);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_CREATE_DDN);
			return operateDDN(trxValue, param);
		}
		return null;
	}

	public IDDNTrxValue convertPartialSCCToDDN(ITrxContext anITrxContext, ILimitProfile anILimitProfile,
			IPartialSCCertificate anIPartialSCCertificate) throws DDNException {
		if (anITrxContext == null) {
			throw new DDNException("The ITrxContext is null !!!");
		}
		if (anILimitProfile == null) {
			throw new DDNException("The ILimitProfile is null !!!");
		}
		if (anIPartialSCCertificate == null) {
			throw new DDNException("The IPartialSCCertificate is null !!!");
		}
		ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
		IPartialSCCertificateItem[] itemList = anIPartialSCCertificate.getPartialSCCItemList();
		if ((limitList == null) || (limitList.length == 0)) {
			throw new DDNException("There is no limit under the BCA !!!");
		}
		if ((itemList == null) || (itemList.length == 0)) {
			throw new DDNException("There is no item under the IPartialSCCertificate !!!");
		}
		IDDN ddn = new OBDDN();
		ddn.setLimitProfileID(anILimitProfile.getLimitProfileID());
		ArrayList resultList = new ArrayList();

		DefaultLogger.debug(this, "Number of Limits: " + limitList.length);
		for (int ii = 0; ii < limitList.length; ii++) {
			DefaultLogger.debug(this, "Limit Existing Indicator: " + limitList[ii].getExistingInd());
			if ((limitList[ii].getExistingInd())
					&& (!limitList[ii].getLimitStatus().equals(ICMSConstant.STATE_DELETED))) {
				IPartialSCCertificateItem psccItem = getPartialSCCertificateItem(limitList[ii], itemList);
				if (psccItem != null) {
					if (psccItem.getIsPartialSCCIssued()) {
						IDDNItem ddnItem = new OBDDNItem();
						// ddnItem.setLimit(limitList[ii]);
						// TODO: format the limit info into DDN items
						ddnItem.setDDNAmount(psccItem.getActivatedAmount());
						resultList.add(ddnItem);
					}
				}
			}
		}
		if (resultList.size() > 0) {
			IDDNItem[] ddnItems = (IDDNItem[]) resultList.toArray(new IDDNItem[resultList.size()]);
			ddn.setDDNItemList(ddnItems);
			// ddn.setDateGenerated(new Date());
			removeDDNNotIssued(ddn);
			IDDNTrxValue trxValue = formulateDDNTrxValue(anITrxContext, ddn);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_CREATE_DDN);
			return operateDDN(trxValue, param);
		}
		return null;
	}

	private ISCCertificateItem getSCCertificateItem(ILimit anILimit, ISCCertificateItem[] anSCCItemList)
			throws DDNException {
		if ((anILimit.getNonDeletedCollateralAllocations() == null)
				|| (anILimit.getNonDeletedCollateralAllocations().length == 0)) {
			return null;
		}
		for (int ii = 0; ii < anSCCItemList.length; ii++) {
			if (anSCCItemList[ii].getLimitID() == anILimit.getLimitID()) {
				return anSCCItemList[ii];
			}
		}
		throw new DDNException("Limit with ID: " + anILimit.getLimitID()
				+ " was not found does not have a corresponding scc item !!!");
	}

	private IPartialSCCertificateItem getPartialSCCertificateItem(ILimit anILimit,
			IPartialSCCertificateItem[] anPartialSCCItemList) throws DDNException {
		if ((anILimit.getNonDeletedCollateralAllocations() == null)
				|| (anILimit.getNonDeletedCollateralAllocations().length == 0)) {
			return null;
		}
		for (int ii = 0; ii < anPartialSCCItemList.length; ii++) {
			if (anPartialSCCItemList[ii].getLimitID() == anILimit.getLimitID()) {
				return anPartialSCCItemList[ii];
			}
		}
		return null;
	}

	/**
	 * Maker generate the OLD DDN
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @param anIDDN of IDDN type
	 * @return IDDNTrxValue - the generate DDN trx value
	 * @throws DDNException on errors
	 */
	/*
	 * public IDDNTrxValue makerGenerateDDN(ITrxContext anITrxContext,
	 * IDDNTrxValue anIDDNTrxValue, IDDN anIDDN) throws DDNException { if
	 * (anITrxContext == null) { throw new
	 * DDNException("The anITrxContext is null!!!"); } if (anIDDNTrxValue ==
	 * null) { throw new
	 * DDNException("The anIDDNTrxValue to be updated is null!!!"); } if (anIDDN
	 * == null) { throw new DDNException("The IDDN to be updated is null !!!");
	 * } //anIDDN.setDateGenerated(new Date()); removeDDNNotIssued(anIDDN);
	 * anIDDNTrxValue = formulateTrxValue(anITrxContext, anIDDNTrxValue,
	 * anIDDN); OBCMSTrxParameter param = new OBCMSTrxParameter();
	 * param.setAction (ICMSConstant.ACTION_MAKER_GENERATE_DDN); return operate
	 * (anIDDNTrxValue, param); }
	 */

	// ccm
	public IDDNTrxValue makerGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue, IDDN anIDDN)
			throws DDNException {
		if (anITrxContext == null) {
			throw new DDNException("The anITrxContext is null!!!");
		}
		if (anIDDNTrxValue == null) { // ck
			DefaultLogger.debug(this, "~~~~~IDDNTrxValue is null");
			if (anIDDN != null) {
				DefaultLogger.debug(this, "~~~~~new IDDNTrxValue");
				anIDDNTrxValue = formulateDDNTrxValue(new OBTrxContext(), anIDDN);
			}

			// throw new
			// DDNException("The anIDDNTrxValue to be updated is null!!!");
		}
		if (anIDDN == null) {
			throw new DDNException("The IDDN to be updated is null !!!");
		}

		removeDDNNotIssued(anIDDN);
		anIDDNTrxValue = formulateDDNTrxValue(anITrxContext, anIDDNTrxValue, anIDDN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_GENERATE_DDN);
		// todo: looking for modification by cc
		return operateDDN(anIDDNTrxValue, param);
	}

	/**
	 * As we persist only those ddn items that are issued. This will remove
	 * those that are not issued
	 * @param anIDDN of IDDN type
	 */
	private void removeDDNNotIssued(IDDN anIDDN) {
		IDDNItem[] itemList = anIDDN.getDDNItemList();
		if ((itemList != null) && (itemList.length > 0)) {
			ArrayList resultList = new ArrayList();
			for (int ii = 0; ii < itemList.length; ii++) {
				if (itemList[ii].getIsDDNIssuedInd()) {
					itemList[ii].setIssuedDate(new Date());
				}
			}
		}
	}

	/**
	 * Checker approve the DDN
	 * @param anITrxContext of ITrxContext type
	 * 
	 * @return IDDNTrxValue - the generated DDN trx value
	 * @throws DDNException on errors
	 */
	/*
	 * public IDDNTrxValue checkerApproveGenerateDDN(ITrxContext anITrxContext,
	 * IDDNTrxValue anIDDNTrxValue) throws DDNException { if (anITrxContext ==
	 * null) { throw new DDNException("The anITrxContext is null!!!"); } if
	 * (anIDDNTrxValue == null) { throw new
	 * DDNException("The anIDDNTrxValue to be updated is null!!!"); }
	 * anIDDNTrxValue = formulateTrxValue(anITrxContext, anIDDNTrxValue);
	 * OBCMSTrxParameter param = new OBCMSTrxParameter(); param.setAction
	 * (ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_DDN); return operate
	 * (anIDDNTrxValue, param); }
	 */

	/**
	 * ck 02mar05 Checker approve the DDN
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @return IDDNTrxValue - the generated DDN trx value
	 * @throws DDNException on errors
	 */
	public IDDNTrxValue checkerApproveGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue)
			throws DDNException {
		if (anITrxContext == null) {
			throw new DDNException("The anITrxContext is null!!!");
		}
		if (anIDDNTrxValue == null) {
			throw new DDNException("The anIDDNTrxValue to be updated is null!!!");
		}
		anIDDNTrxValue = formulateDDNTrxValue(anITrxContext, anIDDNTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_DDN);
		return operateDDN(anIDDNTrxValue, param);
	}

	/**
	 * ck 02mar05 Checker reject the DDN
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @return IDDNTrxValue - the ddn trx value
	 * @throws DDNException on errors
	 */
	public IDDNTrxValue checkerRejectGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue)
			throws DDNException {
		if (anITrxContext == null) {
			throw new DDNException("The anITrxContext is null!!!");
		}
		if (anIDDNTrxValue == null) {
			throw new DDNException("The anIDDNTrxValue to be updated is null!!!");
		}
		anIDDNTrxValue = formulateDDNTrxValue(anITrxContext, anIDDNTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_GENERATE_DDN);
		return operateDDN(anIDDNTrxValue, param);
	}

	/**
	 * ck 02mar05 Maker edit the rejected DDN
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue
	 * @param anIDDN of IDDN
	 * @return IDDNTrxValue - the ddn trx
	 * @throws DDNException on errors
	 */
	public IDDNTrxValue makerEditRejectedGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue, IDDN anIDDN)
			throws DDNException {
		if (anITrxContext == null) {
			throw new DDNException("The anITrxContext is null!!!");
		}
		if (anIDDNTrxValue == null) {
			throw new DDNException("The anIDDNTrxValue to be updated is null!!!");
		}
		if (anIDDN == null) {
			throw new DDNException("The DDN to be updated is null !!!");
		}
		// anIDDN.setDateGenerated(new Date());
		removeDDNNotIssued(anIDDN);
		anIDDNTrxValue = formulateDDNTrxValue(anITrxContext, anIDDNTrxValue, anIDDN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_GENERATE_DDN);
		return operateDDN(anIDDNTrxValue, param);
	}

	/**
	 * ck 02mar05 Make close the rejected DDN
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @return IDDNTrxValue - the ddn trx
	 * @throws DDNException on errors
	 */
	public IDDNTrxValue makerCloseRejectedGenerateDDN(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue)
			throws DDNException {
		if (anITrxContext == null) {
			throw new DDNException("The anITrxContext is null!!!");
		}
		if (anIDDNTrxValue == null) {
			throw new DDNException("The anIDDNTrxValue to be updated is null!!!");
		}
		anIDDNTrxValue = formulateDDNTrxValue(anITrxContext, anIDDNTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GENERATE_DDN);
		return operateDDN(anIDDNTrxValue, param);
	}

	/**
	 * ck 02mar05 System close the DDN
	 * @param anILimitProfile of ILimitProfile type
	 * @throws DDNException on errors
	 */
	public void systemCloseDDN(ILimitProfile anILimitProfile) throws DDNException {
		if (anILimitProfile == null) {
			throw new DDNException("The ILimitProfile is null !!!");
		}

		try {
			IDDN ddn = getDDNWithoutLimitInfo(anILimitProfile.getLimitProfileID(), ICMSConstant.INSTANCE_DDN);
			IDDNTrxValue trxValue = null;
			if (ddn != null) {
				long ddnID = ddn.getDDNID();
				trxValue = getDDNTrxValue(ddnID, false);
				systemCloseDDN(trxValue);
			}
		}
		catch (DDNException e) {
			rollback();
			DefaultLogger.error(this, "Caught DDNException systemCloseDDN!", e);
			throw e;
		}
		catch (Exception e) {
			rollback();
			DefaultLogger.error(this, "Caught Exception in systemCloseDDN!", e);
			throw new DDNException("Caught Exception in systemCloseDDN!", e);
		}
	}

	/**
	 * ck 02mar05 System close the DDN
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @return IDDNTrxValue - the ddn trx
	 * @throws DDNException on errors
	 */
	public IDDNTrxValue systemCloseDDN(IDDNTrxValue anIDDNTrxValue) throws DDNException {
		try {
			if (anIDDNTrxValue == null) {
				throw new DDNException("The anIDDNTrxValue to be updated is null!!!");
			}
			anIDDNTrxValue = formulateDDNTrxValue(null, anIDDNTrxValue);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_DDN);
			return operateDDN(anIDDNTrxValue, param);
		}
		catch (DDNException e) {
			rollback();
			DefaultLogger.error(this, "Caught DDNException systemCloseDDN!", e);
			throw e;
		}
		catch (Exception e) {
			rollback();
			DefaultLogger.error(this, "Caught Exception in systemCloseDDN!", e);
			throw new DDNException("Caught Exception in systemCloseDDN!", e);
		}
	}

	/**
	 * To recompute the total amounts based on the base currency
	 * @param anIDDN of IDDN type
	 * @return IDDN - the sc certificate object
	 * @throws DDNException on errors
	 */
	public IDDN computeTotalAmounts(IDDN anIDDN) throws DDNException {
		if (anIDDN == null) {
			throw new DDNException("The IDDN is null !!!");
		}

		IDDNItem[] itemList = anIDDN.getDDNItemList();
		if ((itemList == null) || (itemList.length == 0)) {
			throw new DDNException("There is no ddn item");
		}

		CurrencyCode baseCurrency = BaseCurrency.getCurrencyCode();
		long totalDDNAmt = 0;
		long cleanDDNAmt = 0;
		long notCleanDDNAmt = 0;
		long amt = 0;
		for (int ii = 0; ii < itemList.length; ii++) {
			if ((itemList[ii].getDDNAmount() != null) && (itemList[ii].getDDNAmount().getCurrencyCode() != null)) {
				if (!itemList[ii].isInnerLimit()) {
					amt = convertAmount(itemList[ii].getDDNAmount(), baseCurrency);
					if (itemList[ii].isCleanType()) {
						cleanDDNAmt = cleanDDNAmt + amt;
					}
					else {
						notCleanDDNAmt = notCleanDDNAmt + amt;
					}
					totalDDNAmt = totalDDNAmt + amt;
				}
			}
		}
		anIDDN.setCleanDDNAmount(new Amount(cleanDDNAmt, baseCurrency));
		anIDDN.setDDNAmount(new Amount(notCleanDDNAmt, baseCurrency));
		anIDDN.setTotalDDNAmount(new Amount(totalDDNAmt, baseCurrency));
		return anIDDN;
	}

	/**
	 * To recompute the total amounts based on the base currency only for those
	 * items that are to be generated
	 * @param anIDDN of IDDN type
	 * @return IDDN - the DDN object
	 * @throws DDNException on errors
	 */
	public IDDN computeTotalAmountForGeneratedItems(IDDN anIDDN) throws DDNException {
		if (anIDDN == null) {
			throw new DDNException("The IDDN is null !!!");
		}

		IDDNItem[] itemList = anIDDN.getDDNItemList();
		if ((itemList == null) || (itemList.length == 0)) {
			throw new DDNException("There is no ddn item");
		}

		CurrencyCode baseCurrency = BaseCurrency.getCurrencyCode();

		long cleanApprovalAmt = 0;
		long notCleanApprovalAmt = 0;
		long totalApprovedAmt = 0;
		long cleanActivatedAmt = 0;
		long notCleanActivatedAmt = 0;
		long totalActivatedAmt = 0;
		long cleanDDNAmt = 0;
		long notCleanDDNAmt = 0;
		long totalDDNAmt = 0;
		long amt = 0;

		for (int ii = 0; ii < itemList.length; ii++) {
			if (itemList[ii].getIsDDNIssuedInd()) {
				if (!itemList[ii].isInnerLimit()) {
					amt = convertAmount(itemList[ii].getDDNAmount(), baseCurrency);
					if (itemList[ii].isCleanType()) {
						cleanDDNAmt = cleanDDNAmt + amt;
					}
					else {
						notCleanDDNAmt = notCleanDDNAmt + amt;
					}
					totalDDNAmt = totalDDNAmt + amt;

					amt = convertAmount(itemList[ii].getApprovedLimitAmount(), baseCurrency);
					if (itemList[ii].isCleanType()) {
						cleanApprovalAmt = cleanApprovalAmt + amt;
					}
					else {
						notCleanApprovalAmt = notCleanApprovalAmt + amt;
					}
					totalApprovedAmt = totalApprovedAmt + amt;

					amt = convertAmount(itemList[ii].getActivatedAmount(), baseCurrency);

					if (itemList[ii].isCleanType()) {
						cleanActivatedAmt = cleanActivatedAmt + amt;
					}
					else {
						notCleanActivatedAmt = notCleanActivatedAmt + amt;
					}
					totalActivatedAmt = totalActivatedAmt + amt;
				}
			}
		}
		anIDDN.setCleanApprovalAmount(new Amount(cleanApprovalAmt, baseCurrency));
		anIDDN.setApprovalAmount(new Amount(notCleanApprovalAmt, baseCurrency));
		anIDDN.setCleanActivatedAmount(new Amount(cleanActivatedAmt, baseCurrency));
		anIDDN.setActivatedAmount(new Amount(notCleanActivatedAmt, baseCurrency));
		anIDDN.setCleanDDNAmount(new Amount(cleanDDNAmt, baseCurrency));
		anIDDN.setDDNAmount(new Amount(notCleanDDNAmt, baseCurrency));
		anIDDN.setTotalDDNAmount(new Amount(totalDDNAmt, baseCurrency));
		anIDDN.setTotalApprovalAmount(new Amount(totalApprovedAmt, baseCurrency));
		anIDDN.setTotalActivatedAmount(new Amount(totalActivatedAmt, baseCurrency));
		return anIDDN;
	}

	private IDDNCustomerDetail getDDNCustomerInfo(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer)
			throws DDNException {
		// IDDNCustomerDetail detail = new OBDDNCustomerDetail();
		IDDNCustomerDetail detail = getDDNCustomerInfo(anICMSCustomer);
		detail.setApprovalDate(anILimitProfile.getApprovalDate());
		detail.setOriginatingLocation(anILimitProfile.getOriginatingLocation());
		detail.setNextReviewDate(anILimitProfile.getNextAnnualReviewDate());
		detail.setApprovalAuthority(anILimitProfile.getApproverEmployeeName1());
		detail.setExtReviewDate(anILimitProfile.getExtendedNextReviewDate());

		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			HashMap famInfo = (HashMap) limitProxy.getFAMName(anILimitProfile.getLimitProfileID());
			if (famInfo != null) {
				detail.setFamCode((String) famInfo.get(ICMSConstant.FAM_CODE));
				detail.setFamName((String) famInfo.get(ICMSConstant.FAM_NAME));
			}
			return detail;
		}
		catch (LimitException ex) {
			throw new DDNException(ex);
		}
	}

	/**
	 * Formulate the IDDNCustomerDetail from the ICMSCustomer
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return IDDNCustomerDetail - the DDN customer details
	 * @throws DDNException on errors
	 */
	private IDDNCustomerDetail getDDNCustomerInfo(ICMSCustomer anICMSCustomer) throws DDNException {
		IDDNCustomerDetail custDetail = new OBDDNCustomerDetail();
		custDetail.setCustomerID(anICMSCustomer.getCustomerID());
		custDetail.setLegalID(anICMSCustomer.getCMSLegalEntity().getLEReference());
		custDetail.setLegalName(anICMSCustomer.getCMSLegalEntity().getLegalName());
		custDetail.setCustomerReference(anICMSCustomer.getCustomerReference());
		custDetail.setCustomerName(anICMSCustomer.getCustomerName());
		custDetail.setCustomerSegmentCode(anICMSCustomer.getCMSLegalEntity().getCustomerSegment());
		ICreditGrade creditGrade = new OBCreditGrade();
		custDetail.setCreditGrade(creditGrade);
		ICreditGrade[] creditGrades = anICMSCustomer.getCMSLegalEntity().getCreditGrades();
		if ((creditGrades != null) && (creditGrades.length > 0)) {
			for (int ii = 0; ii < creditGrades.length; ii++) {
				if (ICMSConstant.CREDIT_GRADE_TYPE.equals(creditGrades[ii].getCGType())) {
					custDetail.setCreditGrade(creditGrades[ii]);
				}
			}
		}
		ICreditStatus[] creditStatus = anICMSCustomer.getCMSLegalEntity().getCreditStatus();
		if ((creditStatus != null) && (creditStatus.length > 0)) {
			custDetail.setCreditStatus(creditStatus[0]);
		}
		return custDetail;
	}

	/**
	 * ck 02mar05 Get the DDN trx value based on the Limit Profile
	 * @param anILimitProfile of LimitProfile type
	 * @return IDDNTrxValue - the DDN trx value
	 * @throws DDNNotRequiredException
	 * @throws DDNException
	 */
	public IDDNTrxValue getDDNTrxValue(ILimitProfile anILimitProfile, boolean isLatestActive)
			throws DDNNotRequiredException, DDNException {
		IDDN ddn = getDDNWithoutLimitInfo(anILimitProfile.getLimitProfileID(), ICMSConstant.INSTANCE_DDN);
		if (ddn == null) {
			return null;
		}
		return getDDNTrxValue(anILimitProfile, ddn.getDDNID(), isLatestActive);
	}

	/**
	 * ck 02mar05 Get the DDN trx value based on limit profile and DDN ID
	 * @param anILimitProfile of ILimitProfile type
	 * @param anDDNID of long type
	 * @return IDDNTrxValue - the DDN trx value
	 * @throws DDNNotRequiredException DDNException,
	 *         SCheckListNotPerfectedException
	 */
	private IDDNTrxValue getDDNTrxValue(ILimitProfile anILimitProfile, long anDDNID, boolean isLatestActive)
			throws DDNNotRequiredException, DDNException {
		IDDNTrxValue trxValue = getDDNTrxValue(anDDNID, isLatestActive);
		if (trxValue.getStagingDDN() != null) {
			populateDDNInfo(anILimitProfile, trxValue.getStagingDDN());
		}
		if (trxValue.getDDN() != null) {
			populateDDNInfo(anILimitProfile, trxValue.getDDN());
			// need to massage the staging and actual certificate to cater for
			// the
			// checker screen
			mergeDDN(trxValue.getStagingDDN(), trxValue.getDDN());
		}
		return trxValue;
	}

	private void mergeDDN(IDDN aStagingDDN, IDDN anActualDDN) throws DDNException {
		DefaultLogger.debug(this, "In mergeDDN");
		IDDNItem[] stageItemList = aStagingDDN.getDDNItemList();
		IDDNItem[] actualItemList = anActualDDN.getDDNItemList();
		if ((stageItemList != null) && (stageItemList.length > 0) && (actualItemList != null)
				&& (actualItemList.length > 0)) {
			DefaultLogger.debug(this, "In processing !!!");
			for (int ii = 0; ii < actualItemList.length; ii++) {
				for (int jj = 0; jj < stageItemList.length; jj++) {
					//if (actualItemList[ii].getLimitType().equals(stageItemList[jj].getLimitType())
					//		&& (actualItemList[ii].getLimitID() == stageItemList[jj].getLimitID())) {
                    if (actualItemList[ii].getDocNumber() == stageItemList[jj].getDocNumber()) {
						if (stageItemList[jj].getDDNItemRef() == ICMSConstant.LONG_INVALID_VALUE) {
							stageItemList[jj].setDDNItemRef(actualItemList[ii].getDDNItemRef());
						}
						else {
							actualItemList[ii].setDDNItemRef(stageItemList[jj].getDDNItemRef());
						}
					}
				}
			}
		}
	}

	/**
	 * Formulate the DDN items based on the limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return IDDNItem[] - the list of scc items
	 * @throws DDNNotRequiredException DDNException
	 */
	private IDDNItem[] getDDNItemList(ILimitProfile anILimitProfile, List deferredList, List deferredApprovalList) throws DDNNotRequiredException, DDNException {
		ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
		if ((limitList == null) || (limitList.length == 0)) {
			DDNNotRequiredException exp = new DDNNotRequiredException("There is no limit in this limit profile");
			throw exp;
		}
		ArrayList itemList = new ArrayList();
		//for (int ii = 0; ii < limitList.length; ii++) {
        for (int ii = 0; ii < deferredList.size(); ii++) {
			//formulateDDNItemList(itemList, limitList[ii]);
            OBCMSTrxHistoryLog log = new OBCMSTrxHistoryLog();
            if (deferredApprovalList != null && deferredApprovalList.size() > ii) {
                log = (OBCMSTrxHistoryLog)deferredApprovalList.get(ii);
            }
            formulateDDNItemList(itemList, (ICheckListItem)deferredList.get(ii), log);
            /**
             * Chee Hong : We use DDNItem to store the deferred document, hence the actual tbl column is nt in use
             * */
			//formulateDDNCoBorrowerItemList(itemList, limitList[ii], false);
		}
		return (IDDNItem[]) itemList.toArray(new IDDNItem[0]);
	}

	/**
	 * Get delete limit info for ddn items.
	 * 
	 * @param limitProfile of type ILimitProfile
	 * @return
	 * @throws DDNNotRequiredException
	 * @throws DDNException
	 */
	private IDDNItem[] getDeletedDDNItemList(ILimitProfile limitProfile) throws DDNNotRequiredException, DDNException {
		ILimit[] limitList = limitProfile.getLimits();
		if ((limitList == null) || (limitList.length == 0)) {
			DDNNotRequiredException exp = new DDNNotRequiredException("There is no limit in this limit profile");
			throw exp;
		}

		ArrayList itemList = new ArrayList();
		for (int i = 0; i < limitList.length; i++) {
			if ((limitList[i].getLimitStatus() != null)
					&& limitList[i].getLimitStatus().equals(ICMSConstant.STATE_DELETED)) {
				//formulateDDNItemList(itemList, limitList[i]);
			}
            /**
             * Chee Hong : We use DDNItem to store the deferred document, hence the actual tbl column is nt in use 
             * */
			//formulateDDNCoBorrowerItemList(itemList, limitList[i], true);
		}
		return (IDDNItem[]) itemList.toArray(new IDDNItem[0]);
	}

	/**
	 * To formulate the DDN items for both the outer and inner limit
	 * @param anItemList of ArrayList type
	 * @param anICheckListItem of an ICheckListItem
	 * @throws DDNException on errors
	 */
	//private void formulateDDNItemList(ArrayList anItemList, ILimit anILimit) throws DDNException {
    private void formulateDDNItemList(ArrayList anItemList, ICheckListItem anICheckListItem, OBCMSTrxHistoryLog log) throws DDNException {
		IDDNItem item = new OBDDNItem();
//		if (isInnerLimit(anILimit)) {
//			item = new OBDDNItem(ICMSConstant.DDN_INNER_LIMIT, anILimit.getLimitID());
//		}
//		else {
//			item = new OBDDNItem(ICMSConstant.DDN_OUTER_LIMIT, anILimit.getLimitID());
//		}
//		item.setLimitStatus(anILimit.getLimitStatus());
//		item.setLimitRef(anILimit.getLimitRef());
//		item.setOuterLimitRef(anILimit.getOuterLimitRef());
//		item.setOuterLimitID(anILimit.getOuterLimitID());
//		item.setOuterLimitProfileID(anILimit.getOuterLimitProfileID());
//		item.setIsInnerOuterSameBCA(anILimit.getIsInnerOuterSameBCA());
//		item.setIsLimitExistingInd(anILimit.getExistingInd());
//		item.setLimitBookingLocation(anILimit.getBookingLocation());
//		// item.setCollateralAllocations(anILimit.
//		// getNonDeletedCollateralAllocations());
//		item.setDDNCollateralInfoList(getDDNCollateralInfoList(anILimit));
//		item.setProductDesc(anILimit.getProductDesc());
//		item.setApprovedLimitAmount(anILimit.getApprovedLimitAmount());
//		item.setMaturityDate(anILimit.getLimitExpiryDate());
//		item.setLimitExpiryDate(anILimit.getLimitExpiryDate());
//		item.setActivatedAmount(anILimit.getActivatedLimitAmount());
//		if (isCleanType(anILimit)) {
//			item.setIsCleanTypeInd(true);
//		}
//		else {
//			item.setIsCleanTypeInd(false);
//		}
        item.setDocNumber(anICheckListItem.getCheckListItemRef());
        item.setDocCode(anICheckListItem.getItemCode());
        item.setDocDesc(anICheckListItem.getItemDesc());
        item.setDateDefer(anICheckListItem.getDeferExpiryDate());
        item.setDateOfReturn(anICheckListItem.getExpectedReturnDate());
        item.setDocStatus(anICheckListItem.getItemStatus());//should always = 'DEFERRED'
        item.setActionParty(anICheckListItem.getActionParty());

        Calendar cal2 = null;
        if (log.getLogDate()!= null) {
            String logDate = log.getLogDate().indexOf(" ") != -1 ? log.getLogDate().split(" ")[0] : "";
            String[] temp = logDate.indexOf("/") != -1 ? logDate.split("/") : null;
            String year = null;
            String month = null;
            String day = null;
            if (temp != null) {
                day = temp[0];
                month = temp[1];
                year = temp[2];

                cal2 = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
            }
        }

        item.setTheApprovalDate(cal2 == null ? null : cal2.getTime());

        String approvedBy = "";
        String userName = log.getLogUserName() == null ? "-" : log.getLogUserName();
        String groupName = log.getLogGroupName() == null ? "-" : log.getLogGroupName();
        approvedBy = userName + " (" + groupName + ")";
        
        item.setApprovedBy(approvedBy);
		anItemList.add(item);
	}

	/**
	 * To formulate the ddn item for co-borrower limit
	 * @param anItemList of ArrayList type
	 * @param anILimit of ILimit type
	 * @throws DDNException on errors
	 */
	private void formulateDDNCoBorrowerItemList(ArrayList anItemList, ILimit anILimit, boolean includeDeleted)
			throws DDNException {
		// ICoBorrowerLimit[] coBorrowerLimitList =
		// anILimit.getNonDeletedCoBorrowerLimits();
		ICoBorrowerLimit[] coBorrowerLimitList = anILimit.getCoBorrowerLimits();
		if ((coBorrowerLimitList == null) || (coBorrowerLimitList.length == 0)) {
			return;
		}
		try {
			ICustomerProxy customerproxy = CustomerProxyFactory.getProxy();
			for (int ii = 0; ii < coBorrowerLimitList.length; ii++) {
				if (!includeDeleted && (coBorrowerLimitList[ii].getStatus() != null)
						&& coBorrowerLimitList[ii].getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}
				IDDNItem item = new OBDDNItem(ICMSConstant.SCC_CB_INNER_LIMIT, coBorrowerLimitList[ii].getLimitID());
				ICMSCustomer customer = customerproxy.getCustomer(coBorrowerLimitList[ii].getCustomerID());

				item.setCoBorrowerCustID(customer.getCustomerID());
				item.setCoBorrowerLegalID(customer.getCMSLegalEntity().getLEReference());
				item.setCoBorrowerName(customer.getCustomerName());
				item.setLimitRef(coBorrowerLimitList[ii].getLimitRef());
				item.setOuterLimitRef(coBorrowerLimitList[ii].getOuterLimitRef());
				item.setIsLimitExistingInd(coBorrowerLimitList[ii].getExistingInd());
				item.setLimitBookingLocation(coBorrowerLimitList[ii].getBookingLocation());
				// item.setCollateralAllocations(anILimit.
				// getNonDeletedCollateralAllocations());
				//item.setDDNCollateralInfoList(getDDNCollateralInfoList(anILimit
				// )); getDDNCoBorrowerCollateralInfoList
				item.setDDNCollateralInfoList(getDDNCoBorrowerCollateralInfoList(coBorrowerLimitList[ii])); // R1
																											// .5
																											// CR35
																											// -
																											// Security
																											// securing
																											// multiple
																											// coborrower
				item.setProductDesc(anILimit.getProductDesc());
				item.setApprovedLimitAmount(coBorrowerLimitList[ii].getApprovedLimitAmount());
				item.setActivatedAmount(coBorrowerLimitList[ii].getActivatedLimitAmount());
				item.setLimitStatus(coBorrowerLimitList[ii].getStatus());
				if (isCleanType(coBorrowerLimitList[ii])) // R1.5 CR35
				{
					item.setIsCleanTypeInd(true);
				}
				else {
					item.setIsCleanTypeInd(false);
				}
				anItemList.add(item);
			}
		}
		catch (CustomerException ex) {
			throw new DDNException("Caught CustomerException in formulateDDNCoBorrowerItemList", ex);
		}
	}

	private DDNCollateralInfo[] getDDNCollateralInfoList(ILimit anILimit) {

		ICollateralAllocation[] colList = anILimit.getNonDeletedCollateralAllocations();
		if ((colList == null) || (colList.length == 0)) {
			return null;
		}

		DDNCollateralInfo[] colRefList = new DDNCollateralInfo[colList.length];
		DDNCollateralInfo info = null;
		DefaultLogger.debug(this,
				" >>>>>>>>>>>>>********* getDDNCollateralInfoList.getNonDeletedCollateralAllocations is NOT NULL");
		for (int ii = 0; ii < colList.length; ii++) {
			info = new DDNCollateralInfo();
			info.setCollateralID(colList[ii].getCollateral().getCollateralID());
			info.setCollateralRef(colList[ii].getCollateral().getSCISecurityID());
			info.setCollateralType(colList[ii].getCollateral().getCollateralType());
			info.setCollateralSubType(colList[ii].getCollateral().getCollateralSubType());
			colRefList[ii] = info;
		}
		return colRefList;
	}

	private DDNCollateralInfo[] getDDNCoBorrowerCollateralInfoList(ICoBorrowerLimit cbLimit) {
		ICollateralAllocation[] colList = cbLimit.getNonDeletedCollateralAllocations();
		if ((colList == null) || (colList.length == 0)) {
			return null;
		}

		DDNCollateralInfo[] colRefList = new DDNCollateralInfo[colList.length];
		DDNCollateralInfo info = null;
		DefaultLogger.debug(this,
				" >>>>>>>>>>>>>********* getDDNCollateralInfoList.getNonDeletedCollateralAllocations is NOT NULL");
		for (int ii = 0; ii < colList.length; ii++) {
			info = new DDNCollateralInfo();
			info.setCollateralID(colList[ii].getCollateral().getCollateralID());
			info.setCollateralRef(colList[ii].getCollateral().getSCISecurityID());
			info.setCollateralType(colList[ii].getCollateral().getCollateralType());
			info.setCollateralSubType(colList[ii].getCollateral().getCollateralSubType());
			colRefList[ii] = info;
		}
		return colRefList;
	}

	/**
	 * To check is a limit is an outer or inner limit
	 * @param anILimit of ILimit type
	 * @return boolean - true if it is an inner limit and false otherwise
	 */
	private boolean isInnerLimit(ILimit anILimit) {
		long outerLimitID = anILimit.getOuterLimitID();
		if ((0 == outerLimitID)
				|| (com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE == outerLimitID)) {
			return false;
		}
		return true;
	}

	/**
	 * To check if a limit is a clean type or not
	 * @param anILimit of ILimit type
	 * @return boolean - true if it is a clean type and false otherwise
	 */
	private boolean isCleanType(ILimit anILimit) {
		if ((anILimit.getNonDeletedCollateralAllocations() == null)
				|| (anILimit.getNonDeletedCollateralAllocations().length == 0)) {
			return true;
		}
		return false;
	}

	/**
	 * To check if a limit is a clean type or not
	 * @param anILimit of ILimit type
	 * @return boolean - true if it is a clean type and false otherwise
	 */
	private boolean isCleanType(ICoBorrowerLimit anILimit) {
		if ((anILimit.getNonDeletedCollateralAllocations() == null)
				|| (anILimit.getNonDeletedCollateralAllocations().length == 0)) {
			return true;
		}
		return false;
	}

	/**
	 * Populate the limit information base on the limitprofile
	 * @param anILimitProfile of ILimitProfile type
	 * @param anIDDN of IDDN type
	 * @throws DDNNotRequiredException DDNException
	 */
	private void populateLimitInfo(ILimitProfile anILimitProfile, IDDN anIDDN) throws DDNNotRequiredException,
			DDNException {
		IDDNItem[] fullDDNItemList = null;

		// if(anIDDN.getDocumentType().equalsIgnoreCase("DDN")){
		fullDDNItemList = getDDNItemList(anILimitProfile, new ArrayList(), new ArrayList());
		// }
		// else{
		// fullDDNItemList = getDDNItemList(anILimitProfile);
		// }

		IDDNItem[] ddnItemList = anIDDN.getDDNItemList();
		if ((fullDDNItemList != null) && (fullDDNItemList.length > 0)) {
			if ((ddnItemList != null) && (ddnItemList.length > 0)) {
				mergeDDNItemList(fullDDNItemList, ddnItemList);
			}
			long cleanApprovalAmt = 0;
			long notCleanApprovalAmt = 0;
			long totalApprovalAmt = 0;
			long cleanActivatedAmt = 0;
			long notCleanActivatedAmt = 0;
			long totalActivatedAmt = 0;
			long cleanDDNAmt = 0;
			long notCleanDDNAmt = 0;
			long totalDDNAmt = 0;
			long amt = 0;
			CurrencyCode baseCurrency = BaseCurrency.getCurrencyCode();
			for (int ii = 0; ii < fullDDNItemList.length; ii++) {
				IDDNItem item = fullDDNItemList[ii];
				if (!item.isInnerLimit()) {
					try {
						if (totalApprovalAmt != -1) {
							amt = convertAmount(item.getApprovedLimitAmount(), baseCurrency);
							totalApprovalAmt = totalApprovalAmt + amt;
							if (item.isCleanType()) {
								cleanApprovalAmt = cleanApprovalAmt + amt;
							}
							else {
								notCleanApprovalAmt = notCleanApprovalAmt + amt;
							}
						}
					}
					catch (DDNException ex) {
						cleanApprovalAmt = -1;
						notCleanApprovalAmt = -1;
						totalApprovalAmt = -1;
					}
					try {
						if (totalActivatedAmt != -1) {
							if (item.isCleanType()) {
								if ((item.getActivatedAmount() != null)
										&& (item.getActivatedAmount().getCurrencyCode() != null)) {
									amt = convertAmount(item.getActivatedAmount(), baseCurrency);
									cleanActivatedAmt = cleanActivatedAmt + amt;
									totalActivatedAmt = totalActivatedAmt + amt;
								}
							}
							else {
								if ((item.getActivatedAmount() != null)
										&& (item.getActivatedAmount().getCurrencyCode() != null)) {
									amt = convertAmount(item.getActivatedAmount(), baseCurrency);
									notCleanActivatedAmt = notCleanActivatedAmt + amt;
									totalActivatedAmt = totalActivatedAmt + amt;
								}
							}
						}
					}
					catch (DDNException ex) {
						cleanActivatedAmt = -1;
						notCleanActivatedAmt = -1;
						totalActivatedAmt = -1;
					}
					try {
						if (totalDDNAmt != -1) {
							if (item.isCleanType()) {
								if ((item.getDDNAmount() != null) && (item.getDDNAmount().getCurrencyCode() != null)) {
									amt = convertAmount(item.getDDNAmount(), baseCurrency);
									cleanDDNAmt = cleanDDNAmt + amt;
									totalDDNAmt = totalDDNAmt + amt;
								}
							}
							else {
								if ((item.getDDNAmount() != null) && (item.getDDNAmount().getCurrencyCode() != null)) {
									amt = convertAmount(item.getDDNAmount(), baseCurrency);
									notCleanDDNAmt = notCleanDDNAmt + amt;
									totalDDNAmt = totalDDNAmt + amt;
								}
							}
						}
					}
					catch (DDNException ex) {
						cleanDDNAmt = -1;
						notCleanDDNAmt = -1;
						totalDDNAmt = -1;
					}
				}
			}
			anIDDN.setDDNItemList(fullDDNItemList);
			if (totalApprovalAmt != -1) {
				anIDDN.setCleanApprovalAmount(new Amount(cleanApprovalAmt, baseCurrency));
				anIDDN.setApprovalAmount(new Amount(notCleanApprovalAmt, baseCurrency));
				anIDDN.setTotalApprovalAmount(new Amount(totalApprovalAmt, baseCurrency));
			}
			if (totalActivatedAmt != -1) {
				anIDDN.setCleanActivatedAmount(new Amount(cleanActivatedAmt, baseCurrency));
				anIDDN.setActivatedAmount(new Amount(notCleanActivatedAmt, baseCurrency));
				anIDDN.setTotalActivatedAmount(new Amount(totalActivatedAmt, baseCurrency));
			}
			if (totalDDNAmt != -1) {
				anIDDN.setCleanDDNAmount(new Amount(cleanDDNAmt, baseCurrency));
				anIDDN.setDDNAmount(new Amount(notCleanDDNAmt, baseCurrency));
				anIDDN.setTotalDDNAmount(new Amount(totalDDNAmt, baseCurrency));
			}
		}
	}

	/**
	 * Helper method to compute ddn amount.
	 * 
	 * @param anIDDN ddn
	 */
	private void computeDDNAmount(IDDN anIDDN) {
		IDDNItem[] items = anIDDN.getDDNItemList();
		long cleanApprovalAmt = 0;
		long notCleanApprovalAmt = 0;
		long totalApprovalAmt = 0;
		long cleanActivatedAmt = 0;
		long notCleanActivatedAmt = 0;
		long totalActivatedAmt = 0;
		long cleanDDNAmt = 0;
		long notCleanDDNAmt = 0;
		long totalDDNAmt = 0;
		long amt = 0;
		CurrencyCode baseCurrency = BaseCurrency.getCurrencyCode();
		for (int ii = 0; ii < items.length; ii++) {
			IDDNItem item = items[ii];
			if (!item.isInnerLimit()) {
				try {
					if (totalApprovalAmt != -1) {
						amt = convertAmount(item.getApprovedLimitAmount(), baseCurrency);
						totalApprovalAmt = totalApprovalAmt + amt;
						if (item.isCleanType()) {
							cleanApprovalAmt = cleanApprovalAmt + amt;
						}
						else {
							notCleanApprovalAmt = notCleanApprovalAmt + amt;
						}
					}
				}
				catch (DDNException ex) {
					cleanApprovalAmt = -1;
					notCleanApprovalAmt = -1;
					totalApprovalAmt = -1;
				}

				try {
					if (totalActivatedAmt != -1) {
						if (item.isCleanType()) {
							if ((item.getActivatedAmount() != null)
									&& (item.getActivatedAmount().getCurrencyCode() != null)) {
								amt = convertAmount(item.getActivatedAmount(), baseCurrency);
								cleanActivatedAmt = cleanActivatedAmt + amt;
								totalActivatedAmt = totalActivatedAmt + amt;
							}
						}
						else {
							if ((item.getActivatedAmount() != null)
									&& (item.getActivatedAmount().getCurrencyCode() != null)) {
								amt = convertAmount(item.getActivatedAmount(), baseCurrency);
								notCleanActivatedAmt = notCleanActivatedAmt + amt;
								totalActivatedAmt = totalActivatedAmt + amt;
							}
						}
					}
				}
				catch (DDNException ex) {
					cleanActivatedAmt = -1;
					notCleanActivatedAmt = -1;
					totalActivatedAmt = -1;
				}

				try {
					if (totalDDNAmt != -1) {
						if (item.isCleanType()) {
							if ((item.getDDNAmount() != null) && (item.getDDNAmount().getCurrencyCode() != null)) {
								amt = convertAmount(item.getDDNAmount(), baseCurrency);
								cleanDDNAmt = cleanDDNAmt + amt;
								totalDDNAmt = totalDDNAmt + amt;
							}
						}
						else {
							if ((item.getDDNAmount() != null) && (item.getDDNAmount().getCurrencyCode() != null)) {
								amt = convertAmount(item.getDDNAmount(), baseCurrency);
								notCleanDDNAmt = notCleanDDNAmt + amt;
								totalDDNAmt = totalDDNAmt + amt;
							}
						}
					}
				}
				catch (DDNException ex) {
					cleanDDNAmt = -1;
					notCleanDDNAmt = -1;
					totalDDNAmt = -1;
				}
			}
		}

		if (totalApprovalAmt != -1) {
			anIDDN.setCleanApprovalAmount(new Amount(cleanApprovalAmt, baseCurrency));
			anIDDN.setApprovalAmount(new Amount(notCleanApprovalAmt, baseCurrency));
			anIDDN.setTotalApprovalAmount(new Amount(totalApprovalAmt, baseCurrency));
		}
		if (totalActivatedAmt != -1) {
			anIDDN.setCleanActivatedAmount(new Amount(cleanActivatedAmt, baseCurrency));
			anIDDN.setActivatedAmount(new Amount(notCleanActivatedAmt, baseCurrency));
			anIDDN.setTotalActivatedAmount(new Amount(totalActivatedAmt, baseCurrency));
		}
		if (totalDDNAmt != -1) {
			anIDDN.setCleanDDNAmount(new Amount(cleanDDNAmt, baseCurrency));
			anIDDN.setDDNAmount(new Amount(notCleanDDNAmt, baseCurrency));
			anIDDN.setTotalDDNAmount(new Amount(totalDDNAmt, baseCurrency));
		}
	}

	private void mergeDDNItemList(IDDNItem[] aFullDDNItemList, IDDNItem[] aDDNItemList) {
		for (int ii = 0; ii < aDDNItemList.length; ii++) {
			for (int jj = 0; jj < aFullDDNItemList.length; jj++) {
				//if (aFullDDNItemList[jj].getLimitType().equals(aDDNItemList[ii].getLimitType())
				//		&& (aFullDDNItemList[jj].getLimitID() == aDDNItemList[ii].getLimitID())) {
                if (aFullDDNItemList[jj].getDocNumber() == aDDNItemList[ii].getDocNumber()) {
					aFullDDNItemList[jj].setDDNItemID(aDDNItemList[ii].getDDNItemID());
					aFullDDNItemList[jj].setDDNItemRef(aDDNItemList[ii].getDDNItemRef());
					aFullDDNItemList[jj].setIsDeletedInd(aDDNItemList[ii].getIsDeletedInd());
					aFullDDNItemList[jj].setDDNAmount(aDDNItemList[ii].getDDNAmount());
					aFullDDNItemList[jj].setIsDDNIssuedInd(aDDNItemList[ii].getIsDDNIssuedInd());
					aFullDDNItemList[jj].setIssuedDate(aDDNItemList[ii].getIssuedDate());
				}
			}
		}
	}

	private HashMap getCheckListMap(ICollateralAllocation[] anICollateralAllocationList, HashMap aCheckListMap) {
		HashMap hmap = new HashMap();
		for (int ii = 0; ii < anICollateralAllocationList.length; ii++) {
			ICollateral col = anICollateralAllocationList[ii].getCollateral();
			Long collateralID = new Long(col.getCollateralID());
			CheckListSearchResult checkList = (CheckListSearchResult) aCheckListMap.get(collateralID);
			if (checkList != null) {
				hmap.put(collateralID, checkList.getCheckListStatus());
			}
			else {
				hmap.put(collateralID, ICMSConstant.STATE_CHECKLIST_IN_PROGRESS);
			}
		}
		return hmap;
	}

	/**
	 * ck 02mar05 Get the DDN trx value based on the Limit Profile
	 * @param anILimitProfile of LimitProfile type
	 * @return IDDNTrxValue - the DDN trx value
	 * @throws DDNNotRequiredException
	 * @throws DDNException
	 */
	private IDDNTrxValue getPreviousDDNTrxValue(ILimitProfile anILimitProfile) throws DDNNotRequiredException,
			DDNException {
		IDDN ddn = getDDNWithoutLimitInfo(anILimitProfile.getLimitProfileID(), ICMSConstant.INSTANCE_DDN);
		if (ddn == null) {
			return null;
		}
		return getPreviousDDNTrxValue(anILimitProfile, ddn.getDDNID());
	}

	/**
	 * ck 02mar05 Get the DDN trx value based on limit profile and DDN ID
	 * @param anILimitProfile of ILimitProfile type
	 * @param anDDNID of long type
	 * @return IDDNTrxValue - the DDN trx value
	 * @throws DDNNotRequiredException DDNException,
	 *         SCheckListNotPerfectedException
	 */
	private IDDNTrxValue getPreviousDDNTrxValue(ILimitProfile anILimitProfile, long anDDNID)
			throws DDNNotRequiredException, DDNException {
		IDDNTrxValue trxValue = getPreviousDDNTrxValue(anDDNID);
		if (trxValue.getStagingDDN() != null) {
			populateDDNInfo(anILimitProfile, trxValue.getStagingDDN());
		}
		if (trxValue.getDDN() != null) {
			populateDDNInfo(anILimitProfile, trxValue.getDDN());
			// need to massage the staging and actual certificate to cater for
			// the
			// checker screen
			mergeDDN(trxValue.getStagingDDN(), trxValue.getDDN());
		}
		return trxValue;
	}

	/**
	 * ck 02mar05 Get the DDN trx value based on the trx ID
	 * @param aTrxID of String type
	 * @return IDDNTrxValue - the DDN trx value
	 * @throws DDNException
	 */
	private IDDNTrxValue getDDNTrxValue(String aTrxID, boolean isLatestActive) throws DDNException {
		DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<<<<< isLatestActive: " + isLatestActive);
		if (aTrxID == null) {
			throw new DDNException("The TrxID is null !!!");
		}
		IDDNTrxValue trxValue = new OBDDNTrxValue();
		trxValue.setTransactionID(aTrxID);
		trxValue.setIsLatestActive(isLatestActive);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_DDN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_DDN);
		return operateDDN(trxValue, param);
	}

	/**
	 * ck 02mar05 Get the DDN trx value based on the DDN ID
	 * @param aDDNID of long type
	 * @return IDDNTrxValue - the DDN trx value
	 * @throws DDNException
	 */
	private IDDNTrxValue getDDNTrxValue(long aDDNID, boolean isLatestActive) throws DDNException {
		if (aDDNID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new DDNException("The DDNID is invalid !!!");
		}
		IDDNTrxValue trxValue = new OBDDNTrxValue();
		trxValue.setReferenceID(String.valueOf(aDDNID));
		trxValue.setIsLatestActive(isLatestActive);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_DDN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_DDN_ID);
		return operateDDN(trxValue, param);
	}

	/**
	 * ck 02mar05 Get the DDN trx value based on the DDN ID
	 * @param aDDNID of long type
	 * @return IDDNTrxValue - the DDN trx value
	 * @throws DDNException
	 */
	private IDDNTrxValue getPreviousDDNTrxValue(long aDDNID) throws DDNException {
		if (aDDNID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new DDNException("The DDNID is invalid !!!");
		}
		IDDNTrxValue trxValue = new OBDDNTrxValue();
		trxValue.setReferenceID(String.valueOf(aDDNID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_DDN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_PREVIOUS_DDN);
		return operateDDN(trxValue, param);
	}

	/**
	 * Formulate the ddn Trx Object
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDN of IDDN type
	 * @return IDDNTrxValue - the ddn trx interface formulated
	 */
	/*
	 * private IDDNTrxValue formulateTrxValue(ITrxContext anITrxContext, IDDN
	 * anIDDN) { return formulateTrxValue(anITrxContext, null, anIDDN); }
	 */

	/**
	 * ck 02mar05 Formulate the ddn Trx Object
	 * @param anITrxContext of ITrxContext type
	 * @param anIDDN of IDDN type
	 * @return IDDNTrxValue - the ddn trx interface formulated
	 */
	private IDDNTrxValue formulateDDNTrxValue(ITrxContext anITrxContext, IDDN anIDDN) {
		return formulateDDNTrxValue(anITrxContext, null, anIDDN);
	}

	/**
	 * Formulate the ddn Trx Object
	 * @param anITrxContext of ITrxContext type
	 * @param anICMSTrxValue of ICMSTrxValue type
	 * @param anIDDN of IDDN type
	 * @return IDDNTrxValue - the ddn trx interface formulated
	 */
	/*
	 * private IDDNTrxValue formulateTrxValue(ITrxContext anITrxContext,
	 * ICMSTrxValue anICMSTrxValue, IDDN anIDDN) { IDDNTrxValue ddnTrxValue =
	 * null; if (anICMSTrxValue != null) { ddnTrxValue = new
	 * OBDDNTrxValue(anICMSTrxValue); } else { ddnTrxValue = new
	 * OBDDNTrxValue(); } ddnTrxValue = formulateTrxValue(anITrxContext,
	 * (IDDNTrxValue)ddnTrxValue); ddnTrxValue.setStagingDDN(anIDDN); return
	 * ddnTrxValue; }
	 */

	/**
	 * ck 02mar05 Formulate the ddn Trx Object
	 * @param anITrxContext of ITrxContext type
	 * @param anICMSTrxValue of ICMSTrxValue type
	 * @param anIDDN of IDDN type
	 * @return IDDNTrxValue - the ddn trx interface formulated
	 */
	private IDDNTrxValue formulateDDNTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IDDN anIDDN) {
		IDDNTrxValue ddnTrxValue = null;
		if (anICMSTrxValue != null) {
			ddnTrxValue = new OBDDNTrxValue(anICMSTrxValue);
		}
		else {
			ddnTrxValue = new OBDDNTrxValue();
		}
		ddnTrxValue = formulateDDNTrxValue(anITrxContext, (IDDNTrxValue) ddnTrxValue);
		ddnTrxValue.setStagingDDN(anIDDN);
		return ddnTrxValue;
	}

	/**
	 * Formulate the ddn trx object
	 * @param anITrxContext - ITrxContext
	 * @param anIDDNTrxValue - IDDNTrxValue
	 * @return IDDNTrxValue - the ddn trx interface formulated
	 */
	/*
	 * private IDDNTrxValue formulateTrxValue(ITrxContext anITrxContext,
	 * IDDNTrxValue anIDDNTrxValue) { if (anITrxContext != null) {
	 * anIDDNTrxValue.setTrxContext(anITrxContext); }
	 * anIDDNTrxValue.setTransactionType(ICMSConstant.INSTANCE_DDN); return
	 * anIDDNTrxValue; }
	 */

	/**
	 * ck 02mar05 Formulate the ddn trx object
	 * @param anITrxContext - ITrxContext
	 * @param anIDDNTrxValue - IDDNTrxValue
	 * @return IDDNTrxValue - the ddn trx interface formulated
	 */
	private IDDNTrxValue formulateDDNTrxValue(ITrxContext anITrxContext, IDDNTrxValue anIDDNTrxValue) {
		if (anITrxContext != null) {
			anIDDNTrxValue.setTrxContext(anITrxContext);
		}
		anIDDNTrxValue.setTransactionType(ICMSConstant.INSTANCE_DDN);
		return anIDDNTrxValue;
	}

	/*
	 * private IDDNTrxValue operate (IDDNTrxValue anIDDNTrxValue,
	 * OBCMSTrxParameter anOBCMSTrxParameter) throws DDNException {
	 * ICMSTrxResult result = operateForResult(anIDDNTrxValue,
	 * anOBCMSTrxParameter); return (IDDNTrxValue)result.getTrxValue(); }
	 */

	/*
	 * private ICMSTrxResult operateForResult (ICMSTrxValue anICMSTrxValue,
	 * OBCMSTrxParameter anOBCMSTrxParameter) throws DDNException { try {
	 * ITrxController controller = (new
	 * DDNTrxControllerFactory()).getController(anICMSTrxValue,
	 * anOBCMSTrxParameter); if (controller == null) { throw new
	 * DDNException("ITrxController is null!!!"); } ITrxResult result =
	 * controller.operate (anICMSTrxValue, anOBCMSTrxParameter); return
	 * (ICMSTrxResult)result; } catch (TransactionException e) { rollback();
	 * throw new DDNException(e); } catch (Exception ex) { rollback(); throw new
	 * DDNException (ex.toString()); } }
	 */
	// ck 02mar05
	private IDDNTrxValue operateDDN(IDDNTrxValue anIDDNTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws DDNException {
		ICMSTrxResult result = operateDDNForResult(anIDDNTrxValue, anOBCMSTrxParameter);
		return (IDDNTrxValue) result.getTrxValue();
	}

	// ck 02mar05
	private ICMSTrxResult operateDDNForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws DDNException {
		try {
			ITrxController controller = (new DDNTrxControllerFactory()).getController(anICMSTrxValue,
					anOBCMSTrxParameter);
			if (controller == null) {
				throw new DDNException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			rollback();
			throw new DDNException(e);
		}
		catch (Exception ex) {
			rollback();
			throw new DDNException(ex.toString());
		}
	}

	/**
	 * Helper method to check if any mandatory document item in the list is
	 * deferred.
	 * 
	 * @param items a list of document items
	 * @return true if any mandatory item is deferred, otherwise false
	 */
	private boolean isAnyMandatoryDeferred(ICheckListItem[] items) {
		int len = items.length;
		for (int i = 0; i < len; i++) {
			if (items[i].getIsMandatoryInd()) {
				if (items[i].getItemStatus().equals(ICMSConstant.STATE_ITEM_DEFERRED)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Helper method to get a list of checklist for a particular checker owner.
	 * 
	 * @param checkList a list of full checklist
	 * @param checkListType checklist owner type
	 * @return checklist belong to the owner
	 */
	private ICheckList[] getCheckList(ICheckList[] checkList, String checkListType) {
		int len = checkList.length;
		ArrayList list = new ArrayList();

		for (int i = 0; i < len; i++) {
			if (checkList[i].getCheckListOwner() instanceof ICCCheckListOwner) {
				ICCCheckListOwner owner = (ICCCheckListOwner) checkList[i].getCheckListOwner();
				if (owner.getSubOwnerType().equals(checkListType)) {
					list.add(checkList[i]);
				}
			}
			else if (checkList[i].getCheckListOwner() instanceof ICollateralCheckListOwner) {
				if (checkListType.equals(ICMSConstant.CHECKLIST_SECURITY)) {
					list.add(checkList[i]);
				}
			}
		}
		return (ICheckList[]) list.toArray(new ICheckList[0]);
	}

	/**
	 * Helper method get ddn limits belong to the customer id.
	 * 
	 * @param ddnList array list to be populated with ddn item belong to the
	 *        customer id
	 * @param ddnItems full ddn limit list
	 * @param custID customer id
	 */
	private void getCustomerDDNItems(ArrayList ddnList, IDDNItem[] ddnItems, long custID) {
		int len = ddnItems.length;
		for (int i = 0; i < len; i++) {
			if (ddnItems[i].getCoBorrowerCustID() == custID) {
				ddnList.add(ddnItems[i]);
			}
		}
	}

	/**
	 * Helper method to get a list of ddn items belong to the collateralID.
	 * 
	 * @param ddnList an array list to be populated with ddn items belong to the
	 *        collateralID
	 * @param ddnItems full ddn limit list
	 * @param collateralID collateral id
	 */
	private void getCollateralDDNItems(ArrayList ddnList, IDDNItem[] ddnItems, long collateralID) {
		int len = ddnItems.length;
		for (int i = 0; i < len; i++) {
			DDNCollateralInfo[] cols = ddnItems[i].getDDNCollateralInfoList();
			if (cols == null) {
				continue;
			}
			for (int j = 0; j < cols.length; j++) {
				if (cols[j].getCollateralID() == collateralID) {
					ddnList.add(ddnItems[i]);
					break;
				}
			}
		}
	}

	protected abstract void rollback();

	protected abstract IDDN getDDNWithoutLimitInfo(long aLimitProfileID, String type) throws DDNException;

	protected abstract long convertAmount(Amount anAmount, CurrencyCode aCurrencyCode) throws DDNException;

	protected abstract int getNoOfDDN(DDNSearchCriteria aCriteria) throws DDNException;
}
