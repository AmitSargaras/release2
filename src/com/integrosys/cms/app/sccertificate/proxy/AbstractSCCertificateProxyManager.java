/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/proxy/AbstractSCCertificateProxyManager.java,v 1.78 2006/10/27 11:18:28 czhou Exp $
 */
package com.integrosys.cms.app.sccertificate.proxy;

//java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.common.bus.BaseCurrency;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICreditGrade;
import com.integrosys.cms.app.customer.bus.ICreditStatus;
import com.integrosys.cms.app.customer.bus.OBCreditGrade;
import com.integrosys.cms.app.customer.bus.OBCreditStatus;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ITATEntry;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.TATComparator;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificateItem;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificateCustomerDetail;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificateItem;
import com.integrosys.cms.app.sccertificate.bus.OBPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.bus.OBPartialSCCertificateItem;
import com.integrosys.cms.app.sccertificate.bus.OBSCCertificate;
import com.integrosys.cms.app.sccertificate.bus.OBSCCertificateCustomerDetail;
import com.integrosys.cms.app.sccertificate.bus.OBSCCertificateItem;
import com.integrosys.cms.app.sccertificate.bus.PartialSCCertificateSearchCriteria;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateException;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateSearchCriteria;
import com.integrosys.cms.app.sccertificate.trx.IPartialSCCertificateTrxValue;
import com.integrosys.cms.app.sccertificate.trx.ISCCertificateTrxValue;
import com.integrosys.cms.app.sccertificate.trx.OBPartialSCCertificateTrxValue;
import com.integrosys.cms.app.sccertificate.trx.OBSCCertificateTrxValue;
import com.integrosys.cms.app.sccertificate.trx.SCCertificateTrxControllerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This abstract class will contains all the biz related logic that is
 * independent of any technology implementation such as EJB
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.78 $
 * @since $Date: 2006/10/27 11:18:28 $ Tag: $Name: DEV_20061123_B286V1 $
 */
public abstract class AbstractSCCertificateProxyManager implements ISCCertificateProxyManager {
	/**
	 * Search SCCertificate based on the criteria given.
	 * 
	 * @param criteria sccertificate criteria
	 * @return SearchResult containing a list of SCCertificateSearchResult
	 * @throws SCCertificateException on any errors encountered
	 */
	public SearchResult searchSCCertificate(SCCertificateSearchCriteria criteria) throws SCCertificateException {
		if (criteria == null) {
			throw new SCCertificateException("SCCertificateSearchCriteria is null!");
		}
		return null;
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
		if (criteria == null) {
			throw new SCCertificateException("PartialSCCertificateSearchCriteria is null!");
		}
		return null;
	}

	/**
	 * Check if SCC has been generated for a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if SCC is generated and false otherwise
	 * @throws SCCertificateException on errors
	 */
	public boolean isSCCFullyGenerated(ILimitProfile anILimitProfile) throws SCCertificateException {
		// R1.5 CR146:- Removal of CCC therefore Clean BCA will also need to
		// issue SCC
		// if (isCleanType(anILimitProfile))
		// {
		// return true;
		// }
		ISCCertificate sccCert = getSCCertificateWithoutLimitInfo(anILimitProfile.getLimitProfileID());
		if (sccCert != null) {
			return true;
		}
		return false;
	}

    private boolean isCleanType(ILimitProfile anILimitProfile) {
        // if no limit
        if ((anILimitProfile.getNonDeletedLimits() == null) || (anILimitProfile.getNonDeletedLimits().length == 0)) {
            return true;
        }

        ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
        for (int ii = 0; ii < limitList.length; ii++) {
            ICollateralAllocation[] nonDeletedList = limitList[ii].getNonDeletedCollateralAllocations();

            // if any of the collateral in a limit is not null | empty, then return false
            if ((nonDeletedList != null) && (nonDeletedList.length > 0)) {
                return false;
            }
        }

        // if all the limit got empty collateral
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
	 * Check if there is any pending generate SCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws SCCertificateException on errors
	 */
	public boolean hasPendingGenerateSCCTrx(ILimitProfile anILimitProfile) throws SCCertificateException {
		SCCertificateSearchCriteria criteria = new SCCertificateSearchCriteria();
		criteria.setLimitProfileID(anILimitProfile.getLimitProfileID());
		String[] trxStatusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_PENDING_UPDATE,
				ICMSConstant.STATE_REJECTED };
		criteria.setTrxStatusList(trxStatusList);
		int count = getNoOfSCCertificate(criteria);
		if (count == 0) {
			return false;
		}
		return true;
	}

	/**
	 * Check if there is any pending generate Partial SCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws SCCertificateException on errors
	 */
	public boolean hasPendingGeneratePartialSCCTrx(ILimitProfile anILimitProfile) throws SCCertificateException {
		SCCertificateSearchCriteria criteria = new SCCertificateSearchCriteria();
		criteria.setLimitProfileID(anILimitProfile.getLimitProfileID());
		String[] trxStatusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_PENDING_UPDATE,
				ICMSConstant.STATE_REJECTED };
		criteria.setTrxStatusList(trxStatusList);
		int count = getNoOfPartialSCCertificate(criteria);
		if (count == 0) {
			return false;
		}
		return true;
	}

	/**
	 * To get the SC Certificate
	 * @param lp of ILimitProfile type
	 * @param cust of ICMSCustomer type
	 * @param refreshLimit if to refresh limit and customer info
	 * @return Hashmap - contain the sc customer info and the scc trx value
	 * @throws SCCertificateException on errors
	 */
	public HashMap getSCCertificate(ILimitProfile lp, ICMSCustomer cust, boolean refreshLimit)
			throws SCCertificateException {
		if (refreshLimit) {
			return getSCCertificate(lp, cust);

		}
		verifySCCertificate(lp, cust);
		HashMap map = new HashMap();

		ISCCertificateTrxValue trxValue = getSCCertificateTrxValue(lp, refreshLimit);
		map.put(ICMSConstant.SCC, trxValue);
		ISCCertificateCustomerDetail custDetails = null;
		if (trxValue.getSCCertificate() != null) {
			populateGeneralCustomerInfo(trxValue.getSCCertificate(), cust);
			custDetails = trxValue.getSCCertificate().getCustDetails();
		}
		else {
			populateGeneralCustomerInfo(trxValue.getStagingSCCertificate(), cust);
			custDetails = trxValue.getStagingSCCertificate().getCustDetails();
		}
		if (custDetails == null) {
			custDetails = getSCCustomerInfo(lp, cust);
		}
		map.put(ICMSConstant.SCC_OWNER, custDetails);
		return map;
	}

	/**
	 * Helper method to verify certificate.
	 * 
	 * @param lp of type ILimitProfile
	 * @param cust of type ICMSCustomer
	 * @throws SCCertificateException
	 */
	private void verifySCCertificate(ILimitProfile lp, ICMSCustomer cust) throws SCCertificateException {
		if (lp == null) {
			throw new SCCertificateException("The ILimitProfile is null !!!");
		}

		if (cust == null) {
			throw new SCCertificateException("The ICMSCustomer is null !!!");
		}

		if ((lp.getNonDeletedLimits() == null) || (lp.getNonDeletedLimits().length == 0)) {
			SCCertificateException exp = new SCCertificateException("There is no limit in this limit profile");
			exp.setErrorCode(ICMSErrorCodes.SCC_NOT_REQUIRED);
			throw exp;
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
		verifySCCertificate(anILimitProfile, anICMSCustomer);
		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			anILimitProfile = limitProxy.getLimitProfile(anILimitProfile.getLimitProfileID());
			/*
			 * if (!isBFLIssued(anILimitProfile)) { SCCertificateException exp =
			 * new SCCertificateException("BFL not allowed!!!");
			 * exp.setErrorCode(ICMSErrorCodes.SCC_BFL_NOT_ISSUED); throw exp; }
			 */

			HashMap map = new HashMap();
			ISCCertificateCustomerDetail custDetails = getSCCustomerInfo(anILimitProfile, anICMSCustomer);
			map.put(ICMSConstant.SCC_OWNER, custDetails);
			ISCCertificateTrxValue trxValue = getSCCertificateTrxValue(anILimitProfile, true);
			if (trxValue.getSCCertificate() != null) {
				trxValue.getSCCertificate().setCustDetails(custDetails);
			}
			if (trxValue.getStagingSCCertificate() != null) {
				trxValue.getStagingSCCertificate().setCustDetails(custDetails);
			}
			map.put(ICMSConstant.SCC, trxValue);

			return map;
		}
		catch (LimitException ex) {
			throw new SCCertificateException(ex);
		}
	}

	/**
	 * Helper method to populate generate customer info.
	 * 
	 * @param cert of type ISCCertificate
	 * @param cust of type ICMSCustomer
	 */
	private void populateGeneralCustomerInfo(ISCCertificate cert, ICMSCustomer cust) {
		ISCCertificateCustomerDetail custDetails = cert.getCustDetails();
		if (custDetails != null) {
			custDetails.setLegalID(cust.getCMSLegalEntity().getLEReference());
			custDetails.setCustomerReference(cust.getCustomerReference());
			custDetails.setCustomerID(cust.getCustomerID());
		}
	}

	/**
	 * Helper method to populate general limit info.
	 * 
	 * @param lp of type ILimitProfile
	 * @param items of type ISCCertificateItem
	 */
	private void populateGeneralLimitInfo(ILimitProfile lp, ISCCertificateItem[] items) {
		ILimit[] limits = lp.getLimits();
		int count = items == null ? 0 : items.length;
		for (int i = 0; i < count; i++) {
			boolean cobo = false;
			if ((items[i].getLimitType() != null) && items[i].getLimitType().equals(ICMSConstant.CCC_CB_INNER_LIMIT)) {
				cobo = true;
			}
			for (int j = 0; j < limits.length; j++) {
				if (cobo) {
					ICoBorrowerLimit[] coboLmts = limits[j].getCoBorrowerLimits();
					int coboCount = coboLmts == null ? 0 : coboLmts.length;
					for (int k = 0; k < coboCount; k++) {
						if (items[i].getLimitID() == coboLmts[k].getLimitID()) {
							items[i].setLimitRef(coboLmts[k].getLimitRef());
							break;
						}
					}
				}
				else {
					if (items[i].getLimitID() == limits[j].getLimitID()) {
						items[i].setLimitRef(limits[j].getLimitRef());
						break;
					}
				}
			}
		}
	}

	/**
	 * Check if BFL is required and if so is it issued or not
	 * @param anILimitProfile of ILimitProfile type
	 * @return boolean - true if BLF is not required or is issued
	 */
	private boolean isBFLIssued(ILimitProfile anILimitProfile) {
		DefaultLogger.debug(this, "BFL Required: " + anILimitProfile.getBFLRequiredInd());
		if (anILimitProfile.getBFLRequiredInd()) {
			ITATEntry[] entryList = anILimitProfile.getTATEntries();
			if ((entryList == null) || (entryList.length == 0)) {
				return false;
			}
			for (int ii = 0; ii < entryList.length; ii++) {
				DefaultLogger.debug(this, "entry: " + entryList[ii]);
				if (ICMSConstant.TAT_CODE_CUSTOMER_ACCEPT_BFL.equals(entryList[ii].getTATServiceCode())) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	/**
	 * To get the Partial SC Certificate
	 * @param lp of ILimitProfile type
	 * @param cust of ICMSCustomer type
	 * @return Hashmap - contain the partial sc customer info and the partial
	 *         scc trx value
	 * @throws SCCertificateException on errors
	 */
	public HashMap getPartialSCCertificate(ILimitProfile lp, ICMSCustomer cust, boolean refreshLimit)
			throws SCCertificateException {
		if (refreshLimit) {
			return getPartialSCCertificate(lp, cust);
		}
		verifyPSCCertificate(lp, cust);
		HashMap map = new HashMap();
		IPartialSCCertificateTrxValue trxValue = getPartialSCCertificateTrxValue(lp, refreshLimit);
		map.put(ICMSConstant.PSCC, trxValue);
		ISCCertificateCustomerDetail custDetails = null;
		if (trxValue.getPartialSCCertificate() != null) {
			populateGeneralCustomerInfo(trxValue.getPartialSCCertificate(), cust);
			custDetails = trxValue.getPartialSCCertificate().getCustDetails();
		}
		else {
			populateGeneralCustomerInfo(trxValue.getStagingPartialSCCertificate(), cust);
			custDetails = trxValue.getStagingPartialSCCertificate().getCustDetails();
		}
		if (custDetails == null) {
			custDetails = getSCCustomerInfo(lp, cust);
		}
		map.put(ICMSConstant.PSCC_OWNER, custDetails);

		return map;
	}

	/**
	 * Helper method to verify pssc certificate.
	 * 
	 * @param lp of type ILimitProfile
	 * @param cust of type ICMSCustomer
	 * @throws SCCertificateException
	 */
	private void verifyPSCCertificate(ILimitProfile lp, ICMSCustomer cust) throws SCCertificateException {
		if (lp == null) {
			throw new SCCertificateException("The ILimitProfile is null !!!");
		}

		if (cust == null) {
			throw new SCCertificateException("The ICMSCustomer is null !!!");
		}

		if ((lp.getNonDeletedLimits() == null) || (lp.getNonDeletedLimits().length == 0)) {
			SCCertificateException exp = new SCCertificateException("There is no limit in this limit profile");
			exp.setErrorCode(ICMSErrorCodes.PSCC_NOT_REQUIRED);
			throw exp;
		}

		if (isSCCGenerated(lp)) {
			SCCertificateException exp = new SCCertificateException("Partial SCC not allowed as SCC has been generated");
			exp.setErrorCode(ICMSErrorCodes.PSCC_NOT_ALLOWED);
			throw exp;
		}

		if (isCleanType(lp)) {
			SCCertificateException exp = new SCCertificateException("Partial SCC not allowed due to clean BCA");
			exp.setErrorCode(ICMSErrorCodes.PSCC_NOT_APPLICABLE_CLEAN_BCA);
			throw exp;
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
		verifyPSCCertificate(anILimitProfile, anICMSCustomer);
		HashMap map = new HashMap();
		ISCCertificateCustomerDetail custDetails = getSCCustomerInfo(anILimitProfile, anICMSCustomer);
		map.put(ICMSConstant.PSCC_OWNER, custDetails);
		IPartialSCCertificateTrxValue trxValue = getPartialSCCertificateTrxValue(anILimitProfile, true);
		if (trxValue.getPartialSCCertificate() != null) {
			trxValue.getPartialSCCertificate().setCustDetails(custDetails);
		}
		if (trxValue.getStagingPartialSCCertificate() != null) {
			trxValue.getStagingPartialSCCertificate().setCustDetails(custDetails);
		}
		map.put(ICMSConstant.PSCC, trxValue);
		return map;
	}

	/**
	 * Get SCC certificate by transaction id.
	 * 
	 * @param trxID transaction id
	 * @return a HashMap of [ICMSConstant.SCC_OWNER,
	 *         ISCCertificateCustomerDetail],[ICMSConstant.SCC,
	 *         ISCCertificateTrxValue]
	 * @throws SCCertificateException on any errors encountered
	 */
	public HashMap getSCCertificate(ILimitProfile lp, ICMSCustomer cust, String trxID, boolean refreshLimit)
			throws SCCertificateException {
		if (refreshLimit) {
			return getSCCertificate(lp, cust, trxID);
		}
		HashMap map = new HashMap();
		ISCCertificateTrxValue trxValue = getSCCertificateTrxValue(trxID);
		ISCCertificateCustomerDetail custDetails = null;

		if (trxValue.getSCCertificate() != null) {
			DefaultLogger.debug(this, ">>>>>>>> Entering updateItemCleanTypeIndicator from getSCCertificate (actual)");
			updateItemCleanTypeIndicator(trxValue.getSCCertificate().getSCCItemList(), lp); // CR146

			calcTotalLimitAmount(trxValue.getSCCertificate(), trxValue.getSCCertificate().getSCCItemList());
			populateGeneralCustomerInfo(trxValue.getSCCertificate(), cust);
			populateGeneralLimitInfo(lp, trxValue.getSCCertificate().getSCCItemList());
			custDetails = trxValue.getSCCertificate().getCustDetails();
			//trxValue.getSCCertificate().setSCCItemList(getSCCertificateItemList
			// (lp)); //CR146
			// DefaultLogger.debug(this,
			// ">>>>>>>> Entering updateItemCleanTypeIndicator from getSCCertificate (actual)"
			// );
			// updateItemCleanTypeIndicator(trxValue.getSCCertificate().
			// getSCCItemList(), lp); //CR146
		}

		if (trxValue.getStagingSCCertificate() != null) {
			DefaultLogger.debug(this, ">>>>>>>> Entering updateItemCleanTypeIndicator from getSCCertificate (staging)");
			updateItemCleanTypeIndicator(trxValue.getStagingSCCertificate().getSCCItemList(), lp); // CR146

			calcTotalLimitAmount(trxValue.getStagingSCCertificate(), trxValue.getStagingSCCertificate()
					.getSCCItemList());
			populateGeneralCustomerInfo(trxValue.getStagingSCCertificate(), cust);
			populateGeneralLimitInfo(lp, trxValue.getStagingSCCertificate().getSCCItemList());
			if (custDetails == null) {
				custDetails = trxValue.getStagingSCCertificate().getCustDetails();
			}
			// trxValue.getStagingSCCertificate().setSCCItemList(
			// getSCCertificateItemList(lp)); //CR146
			// DefaultLogger.debug(this,
			// ">>>>>>>> Entering updateItemCleanTypeIndicator from getSCCertificate (staging)"
			// );
			// updateItemCleanTypeIndicator(trxValue.getStagingSCCertificate().
			// getSCCItemList(), lp); //CR146
		}
		map.put(ICMSConstant.SCC_OWNER, custDetails);
		map.put(ICMSConstant.SCC, trxValue);
		return map;
	}

	private void updateItemCleanTypeIndicator(ISCCertificateItem[] itemList, ILimitProfile lp) {
		// DefaultLogger.debug(this,
		// ">>>>>>>>>>> IN updateItemCleanTypeIndicator");
		ILimit[] limitList = lp.getNonDeletedLimits();
		if ((limitList == null) || (limitList.length == 0) || (itemList == null) || (itemList.length == 0)) {
			return;
		}

		// DefaultLogger.debug(this, ">>>>>>>>>> size of itemList: " +
		// itemList.length);
		// DefaultLogger.debug(this, ">>>>>>>>>> size of limitList: " +
		// limitList.length);
		for (int i = 0; i < itemList.length; i++) {
			if (itemList[i] != null) {

				// If its a co-borrower item
				if (!CommonUtil.isEmpty(itemList[i].getCoBorrowerName())
						|| !CommonUtil.isEmpty(itemList[i].getCoBorrowerLegalID())) {
					for (int j = 0; j < limitList.length; j++) {
						ICoBorrowerLimit[] cbLimits = limitList[j].getNonDeletedCoBorrowerLimits();
						if (cbLimits != null) {
							for (int k = 0; k < cbLimits.length; k++) {
								if (itemList[i].getLimitID() == cbLimits[k].getLimitID()) {
									itemList[i].setIsCleanTypeInd(isCleanType(cbLimits[k]));
									continue;
								}
							}
						}
					}
				}

				// DefaultLogger.debug(this, ">>>>>>>>> Summary of item: \n" +
				// itemList[i]);
				for (int j = 0; j < limitList.length; j++) { // main borrower
																// item
					if (itemList[i].getLimitID() == (limitList[j].getLimitID())) {
						itemList[i].setIsCleanTypeInd(isCleanType(limitList[j]));
						break;
					}
				}// end limit for loop
			}// end if(itemList[i] == null)
		}// end item for loop
	}

	/**
	 * To get the SCC customer info and the SCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @return HashMap - contain the cc customer info and the SCC trx value
	 * @throws SCCertificateException on errors
	 */
	public HashMap getSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID)
			throws SCCertificateException {
		HashMap map = new HashMap();
		ISCCertificateTrxValue trxValue = getSCCertificateTrxValue(aTrxID);
		ISCCertificate staging = trxValue.getStagingSCCertificate();
		if (trxValue.getStagingSCCertificate() != null) {
			populateLimitInfo(anILimitProfile, trxValue.getStagingSCCertificate(), false);
		}
		if (trxValue.getSCCertificate() != null) {
			populateLimitInfo(anILimitProfile, trxValue.getSCCertificate(), false);
			// need to massage the staging and actual certificate to cater for
			// the
			// checker screen
			mergeCertificate(trxValue.getStagingSCCertificate(), trxValue.getSCCertificate());
		}
		map.put(ICMSConstant.SCC_OWNER, getSCCustomerInfo(anILimitProfile, anICMSCustomer));
		map.put(ICMSConstant.SCC, trxValue);
		return map;
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
		if (refreshLimit) {
			return getPartialSCCertificate(lp, cust, trxID);
		}
		HashMap map = new HashMap();
		IPartialSCCertificateTrxValue trxValue = getPartialSCCertificateTrxValue(trxID);
		ISCCertificateCustomerDetail custDetails = null;

		if (trxValue.getPartialSCCertificate() != null) {
			updateItemCleanTypeIndicator(trxValue.getPartialSCCertificate().getPartialSCCItemList(), lp); // CR146
			calcTotalLimitAmount(trxValue.getPartialSCCertificate(), trxValue.getPartialSCCertificate()
					.getPartialSCCItemList());
			populateGeneralCustomerInfo(trxValue.getPartialSCCertificate(), cust);
			populateGeneralLimitInfo(lp, trxValue.getPartialSCCertificate().getPartialSCCItemList());
			custDetails = trxValue.getPartialSCCertificate().getCustDetails();
			// updateItemCleanTypeIndicator(trxValue.getPartialSCCertificate().
			// getPartialSCCItemList(), lp); //CR146
		}
		if (trxValue.getStagingPartialSCCertificate() != null) {
			updateItemCleanTypeIndicator(trxValue.getStagingPartialSCCertificate().getPartialSCCItemList(), lp); // CR146
			calcTotalLimitAmount(trxValue.getStagingPartialSCCertificate(), trxValue.getStagingPartialSCCertificate()
					.getPartialSCCItemList());
			populateGeneralCustomerInfo(trxValue.getStagingPartialSCCertificate(), cust);
			populateGeneralLimitInfo(lp, trxValue.getStagingPartialSCCertificate().getPartialSCCItemList());
			if (custDetails == null) {
				custDetails = trxValue.getStagingPartialSCCertificate().getCustDetails();
			}
			// updateItemCleanTypeIndicator(trxValue.
			// getStagingPartialSCCertificate().getPartialSCCItemList(), lp);
			// //CR146
		}
		map.put(ICMSConstant.PSCC_OWNER, custDetails);
		map.put(ICMSConstant.PSCC, trxValue);
		return map;
	}

	/**
	 * To get the Partial SCC customer info and the Partial SCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @return HashMap - contain the partial sc customer info and the Partial
	 *         SCC trx value
	 * @throws SCCertificateException on errors
	 */
	public HashMap getPartialSCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID)
			throws SCCertificateException {
		HashMap map = new HashMap();
		IPartialSCCertificateTrxValue trxValue = getPartialSCCertificateTrxValue(aTrxID);
		IPartialSCCertificate staging = trxValue.getStagingPartialSCCertificate();
		if (trxValue.getStagingPartialSCCertificate() != null) {
			populateLimitInfo(anILimitProfile, trxValue.getStagingPartialSCCertificate());
		}
		if (trxValue.getPartialSCCertificate() != null) {
			populateLimitInfo(anILimitProfile, trxValue.getPartialSCCertificate());
			// need to massage the staging and actual certificate to cater for
			// the
			// checker screen
			mergeCertificate(trxValue.getStagingPartialSCCertificate(), trxValue.getPartialSCCertificate());
		}
		map.put(ICMSConstant.PSCC_OWNER, getSCCustomerInfo(anILimitProfile, anICMSCustomer));
		map.put(ICMSConstant.PSCC, trxValue);
		return map;
	}

	/**
	 * Maker generate the SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @param anISCCertificate of ISCCertificate type
	 * @return ISCCertificateTrxValue - the generate SCC trx value
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificateTrxValue makerGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue, ISCCertificate anISCCertificate)
			throws SCCertificateException {
		if (anITrxContext == null) {
			throw new SCCertificateException("The anITrxContext is null!!!");
		}
		if (anISCCertificateTrxValue == null) {
			throw new SCCertificateException("The anISCCertificateTrxValue to be updated is null!!!");
		}
		if (anISCCertificate == null) {
			throw new SCCertificateException("The ISCCertificate to be updated is null !!!");
		}
		// anISCCertificate.setDateGenerated(new Date());
		anISCCertificateTrxValue = formulateTrxValue(anITrxContext, anISCCertificateTrxValue, anISCCertificate);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_GENERATE_SCC);
		return operate(anISCCertificateTrxValue, param);
	}

	/**
	 * Maker generate the Partial SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 *        type
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @return IPartialSCCertificateTrxValue - the generate Partial SCC trx
	 *         value
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificateTrxValue makerGeneratePartialSCC(ITrxContext anITrxContext,
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue, IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException {
		if (anITrxContext == null) {
			throw new SCCertificateException("The anITrxContext is null!!!");
		}
		if (anIPartialSCCertificateTrxValue == null) {
			throw new SCCertificateException("The anIPartialSCCertificateTrxValue to be updated is null!!!");
		}
		if (anIPartialSCCertificate == null) {
			throw new SCCertificateException("The IPartialSCCertificate to be updated is null !!!");
		}
		// anIPartialSCCertificate.setDateGenerated(new Date());
		removePartialSCCNotIssued(anIPartialSCCertificate);
		anIPartialSCCertificateTrxValue = formulateTrxValue(anITrxContext, anIPartialSCCertificateTrxValue,
				anIPartialSCCertificate);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_GENERATE_PSCC);
		return operate(anIPartialSCCertificateTrxValue, param);
	}

	/**
	 * As we persist only those partial scc items that are issued. This will
	 * remove those that are not issued
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 */
	private void removePartialSCCNotIssued(IPartialSCCertificate anIPartialSCCertificate) {
		IPartialSCCertificateItem[] itemList = anIPartialSCCertificate.getPartialSCCItemList();
		if ((itemList != null) && (itemList.length > 0)) {
			for (int ii = 0; ii < itemList.length; ii++) {
				if (itemList[ii].getIsPartialSCCIssued()) {
					itemList[ii].setIssuedDate(new Date());
				}
			}
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
		if (anITrxContext == null) {
			throw new SCCertificateException("The anITrxContext is null!!!");
		}
		if (anISCCertificateTrxValue == null) {
			throw new SCCertificateException("The anISCCertificateTrxValue to be updated is null!!!");
		}
		anISCCertificateTrxValue = formulateTrxValue(anITrxContext, anISCCertificateTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_SCC);
		return operate(anISCCertificateTrxValue, param);
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
		if (anITrxContext == null) {
			throw new SCCertificateException("The anITrxContext is null!!!");
		}
		if (anIPartialSCCertificateTrxValue == null) {
			throw new SCCertificateException("The anIPartialSCCertificateTrxValue to be updated is null!!!");
		}
		anIPartialSCCertificateTrxValue = formulateTrxValue(anITrxContext, anIPartialSCCertificateTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_PSCC);
		return operate(anIPartialSCCertificateTrxValue, param);
	}

	/**
	 * Checker reject the SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the sc certificate trx value
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificateTrxValue checkerRejectGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue) throws SCCertificateException {
		if (anITrxContext == null) {
			throw new SCCertificateException("The anITrxContext is null!!!");
		}
		if (anISCCertificateTrxValue == null) {
			throw new SCCertificateException("The anISCCertificateTrxValue to be updated is null!!!");
		}
		anISCCertificateTrxValue = formulateTrxValue(anITrxContext, anISCCertificateTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_GENERATE_SCC);
		return operate(anISCCertificateTrxValue, param);
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
		if (anITrxContext == null) {
			throw new SCCertificateException("The anITrxContext is null!!!");
		}
		if (anIPartialSCCertificateTrxValue == null) {
			throw new SCCertificateException("The anIPartialSCCertificateTrxValue to be updated is null!!!");
		}
		anIPartialSCCertificateTrxValue = formulateTrxValue(anITrxContext, anIPartialSCCertificateTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_GENERATE_PSCC);
		return operate(anIPartialSCCertificateTrxValue, param);
	}

	/**
	 * Maker edit the rejected SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue
	 * @param anISCCertificate of ISCCertificate
	 * @return ISCCertificateTrxValue - the SC certificate trx
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificateTrxValue makerEditRejectedGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue, ISCCertificate anISCCertificate)
			throws SCCertificateException {
		if (anITrxContext == null) {
			throw new SCCertificateException("The anITrxContext is null!!!");
		}
		if (anISCCertificateTrxValue == null) {
			throw new SCCertificateException("The anISCCertificateTrxValue to be updated is null!!!");
		}
		if (anISCCertificate == null) {
			throw new SCCertificateException("The ISCCertificate to be updated is null !!!");
		}
		// anISCCertificate.setDateGenerated(new Date());
		anISCCertificateTrxValue = formulateTrxValue(anITrxContext, anISCCertificateTrxValue, anISCCertificate);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_GENERATE_SCC);
		return operate(anISCCertificateTrxValue, param);
	}

	/**
	 * Maker edit the rejected Partial SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 * @param anIPartialSCCertificate of IPartialSCCertificate
	 * @return IPartialSCCertificateTrxValue - the Partial SC certificate trx
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificateTrxValue makerEditRejectedGeneratePartialSCC(ITrxContext anITrxContext,
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue, IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException {
		if (anITrxContext == null) {
			throw new SCCertificateException("The anITrxContext is null!!!");
		}
		if (anIPartialSCCertificateTrxValue == null) {
			throw new SCCertificateException("The anIPartialSCCertificateTrxValue to be updated is null!!!");
		}
		if (anIPartialSCCertificate == null) {
			throw new SCCertificateException("The IPartialSCCertificate to be updated is null !!!");
		}
		// anIPartialSCCertificate.setDateGenerated(new Date());
		removePartialSCCNotIssued(anIPartialSCCertificate);
		anIPartialSCCertificateTrxValue = formulateTrxValue(anITrxContext, anIPartialSCCertificateTrxValue,
				anIPartialSCCertificate);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_GENERATE_PSCC);
		return operate(anIPartialSCCertificateTrxValue, param);
	}

	/**
	 * Make close the rejected SCC
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the sc certificate trx
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificateTrxValue makerCloseRejectedGenerateSCC(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue) throws SCCertificateException {
		if (anITrxContext == null) {
			throw new SCCertificateException("The anITrxContext is null!!!");
		}
		if (anISCCertificateTrxValue == null) {
			throw new SCCertificateException("The anISCCertificateTrxValue to be updated is null!!!");
		}
		anISCCertificateTrxValue = formulateTrxValue(anITrxContext, anISCCertificateTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GENERATE_SCC);
		return operate(anISCCertificateTrxValue, param);
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
		if (anITrxContext == null) {
			throw new SCCertificateException("The anITrxContext is null!!!");
		}
		if (anIPartialSCCertificateTrxValue == null) {
			throw new SCCertificateException("The anIPartialSCCertificateTrxValue to be updated is null!!!");
		}
		anIPartialSCCertificateTrxValue = formulateTrxValue(anITrxContext, anIPartialSCCertificateTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GENERATE_PSCC);
		return operate(anIPartialSCCertificateTrxValue, param);
	}

	/**
	 * System close the Partial SCC
	 * @param anILimitProfile of ILimitProfile type
	 * @throws SCCertificateException on errors
	 */
	public void systemClosePartialSCC(ILimitProfile anILimitProfile) throws SCCertificateException {
		if (anILimitProfile == null) {
			throw new SCCertificateException("The ILimitProfile is null !!!");
		}

		try {
			// if (!isCleanType(anILimitProfile))
			// {
			String trxID = getPSCCTrxIDbyLimitProfile(anILimitProfile.getLimitProfileID());
			if (trxID != null) {
				IPartialSCCertificateTrxValue certTrxValue = getPartialSCCertificateTrxValue(trxID);
				if (certTrxValue != null) {
					if (!ICMSConstant.STATE_CLOSED.equals(certTrxValue.getStatus())) {
						systemClosePartialSCC(certTrxValue);
					}
				}
			}
			// }
		}
		catch (SCCertificateException ex) {
			rollback();
			DefaultLogger.error(this, "Caught SCCertificateException in systemCloseSCC!", ex);
			throw ex;
		}
		catch (Exception ex) {
			rollback();
			DefaultLogger.error(this, "Caught Exception in systemCloseSCC!", ex);
			throw new SCCertificateException("Caught Exception in systemCloseSCC!", ex);
		}
	}

	/**
	 * System reset partial SCC. If pending create then will close it else will
	 * reset it to active
	 * @param aLimitProfileID of long type
	 */
	public void systemResetPartialSCC(long aLimitProfileID) throws SCCertificateException {
		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = limitProxy.getLimitProfile(aLimitProfileID);
			// if (!isCleanType(limitProfile))
			// {
			String trxID = getPSCCTrxIDbyLimitProfile(aLimitProfileID);
			if (trxID != null) {
				IPartialSCCertificateTrxValue certTrxValue = getPartialSCCertificateTrxValue(trxID);
				if (certTrxValue != null) {
					if (!ICMSConstant.STATE_CLOSED.equals(certTrxValue.getStatus())) {
						systemResetPartialSCC(certTrxValue);
					}
				}
			}
			// }
		}
		catch (SCCertificateException ex) {
			rollback();
			DefaultLogger.error(this, "Caught SCCertificateException in systemResetPartialSCC!", ex);
			throw new SCCertificateException("Caught Exception in systemResetPartialSCC!", ex);
		}
		catch (LimitException ex) {
			rollback();
			DefaultLogger.error(this, "Caught SCCertificateException in systemResetPartialSCC!", ex);
			throw new SCCertificateException("Caught Exception in systemResetPartialSCC!", ex);
		}
		catch (Exception ex) {
			rollback();
			DefaultLogger.error(this, "Caught Exception in systemResetPartialSCC!", ex);
			throw new SCCertificateException("Caught Exception in systemResetPartialSCC!", ex);
		}
	}

	/**
	 * System close the SCC
	 * @param anILimitProfile of ILimitProfile type
	 * @throws SCCertificateException on errors
	 */
	public void systemCloseSCC(ILimitProfile anILimitProfile) throws SCCertificateException {
		if (anILimitProfile == null) {
			throw new SCCertificateException("The ILimitProfile is null !!!");
		}

		try {
			// if (!isCleanType(anILimitProfile)) //R1.5 CR146:- Removal of CCC
			// therefore Clean BCA will also issuse SCC
			// {
			String trxID = getSCCTrxIDbyLimitProfile(anILimitProfile.getLimitProfileID());
			if (trxID != null) {
				ISCCertificateTrxValue certTrxValue = getSCCertificateTrxValue(trxID);
				if (certTrxValue != null) {
					if (!ICMSConstant.STATE_CLOSED.equals(certTrxValue.getStatus())) {
						systemCloseSCC(certTrxValue);
					}
				}
			}
			// }
		}
		catch (SCCertificateException ex) {
			rollback();
			DefaultLogger.error(this, "Caught SCCertificateException in systemCloseSCC!", ex);
			throw ex;
		}
		catch (Exception ex) {
			rollback();
			DefaultLogger.error(this, "Caught Exception in systemCloseSCC!", ex);
			throw new SCCertificateException("Caught Exception in systemCloseSCC!", ex);
		}
	}

	/**
	 * System reset SCC. If pending create then will close it else will reset it
	 * to active
	 * @param aLimitProfileID of long type
	 */
	public void systemResetSCC(long aLimitProfileID) throws SCCertificateException {
		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = limitProxy.getLimitProfile(aLimitProfileID);
			// if (!isCleanType(limitProfile)) //R1.5 CR146:- Removal of CCC
			// therefore Clean BCA will also issuse SCC
			// {
			String trxID = getSCCTrxIDbyLimitProfile(aLimitProfileID);
			if (trxID != null) {
				ISCCertificateTrxValue certTrxValue = getSCCertificateTrxValue(trxID);
				if (certTrxValue != null) {
					if (!ICMSConstant.STATE_CLOSED.equals(certTrxValue.getStatus())) {
						systemResetSCC(certTrxValue);
					}
				}
			}
			// }
		}
		catch (SCCertificateException ex) {
			rollback();
			DefaultLogger.error(this, "Caught SCCertificateException in systemResetSCC!", ex);
			throw new SCCertificateException("Caught Exception in systemResetSCC!", ex);
		}
		catch (LimitException ex) {
			rollback();
			DefaultLogger.error(this, "Caught SCCertificateException in systemResetSCC!", ex);
			throw new SCCertificateException("Caught Exception in systemResetSCC!", ex);
		}
		catch (Exception ex) {
			rollback();
			DefaultLogger.error(this, "Caught Exception in systemResetSCC!", ex);
			throw new SCCertificateException("Caught Exception in systemResetSCC!", ex);
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
			if (anIPartialSCCertificateTrxValue == null) {
				throw new SCCertificateException("The anIPartialSCCertificateTrxValue to be updated is null!!!");
			}
			anIPartialSCCertificateTrxValue = formulateTrxValue(null, anIPartialSCCertificateTrxValue);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_PSCC);
			return operate(anIPartialSCCertificateTrxValue, param);
		}
		catch (SCCertificateException e) {
			rollback();
			DefaultLogger.error(this, "Caught SCCertificateExceptionin systemClosePartialSCC!", e);
			throw e;
		}
		catch (Exception e) {
			rollback();
			DefaultLogger.error(this, "Caught Exception in systemClosePartialSCC!", e);
			throw new SCCertificateException("Caught Exception in systemClosePartialSCC!", e);
		}
	}

	/**
	 * System reset the Partial SCC
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 *        type
	 * @return IPartialSCCertificateTrxValue - the partial sc certificate trx
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificateTrxValue systemResetPartialSCC(
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws SCCertificateException {
		try {
			if (anIPartialSCCertificateTrxValue == null) {
				throw new SCCertificateException("The anIPartialSCCertificateTrxValue to be updated is null!!!");
			}
			anIPartialSCCertificateTrxValue = formulateTrxValue(null, anIPartialSCCertificateTrxValue);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_RESET_PSCC);
			return operate(anIPartialSCCertificateTrxValue, param);
		}
		catch (SCCertificateException e) {
			rollback();
			DefaultLogger.error(this, "Caught SCCertificateException in systemResetPartialSCC!", e);
			throw e;
		}
		catch (Exception e) {
			rollback();
			DefaultLogger.error(this, "Caught Exception in systemResetPartialSCC!", e);
			throw new SCCertificateException("Caught Exception in systemResetPartialSCC!", e);
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
			if (anISCCertificateTrxValue == null) {
				throw new SCCertificateException("The anISCCertificateTrxValue to be updated is null!!!");
			}
			anISCCertificateTrxValue = formulateTrxValue(null, anISCCertificateTrxValue);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_SCC);
			return operate(anISCCertificateTrxValue, param);
		}
		catch (SCCertificateException e) {
			rollback();
			DefaultLogger.error(this, "Caught SCCertificateExceptionin systemCloseSCC!", e);
			throw e;
		}
		catch (Exception e) {
			rollback();
			DefaultLogger.error(this, "Caught Exception in systemCloseSCC!", e);
			throw new SCCertificateException("Caught Exception in systemCloseSCC!", e);
		}
	}

	/**
	 * System reset the SCC
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the sc certificate trx
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificateTrxValue systemResetSCC(ISCCertificateTrxValue anISCCertificateTrxValue)
			throws SCCertificateException {
		try {
			if (anISCCertificateTrxValue == null) {
				throw new SCCertificateException("The anISCCertificateTrxValue to be updated is null!!!");
			}
			anISCCertificateTrxValue = formulateTrxValue(null, anISCCertificateTrxValue);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_RESET_SCC);
			return operate(anISCCertificateTrxValue, param);
		}
		catch (SCCertificateException e) {
			rollback();
			DefaultLogger.error(this, "Caught SCCertificateException in systemResetSCC!", e);
			throw e;
		}
		catch (Exception e) {
			rollback();
			DefaultLogger.error(this, "Caught Exception in systemResetSCC!", e);
			throw new SCCertificateException("Caught Exception in systemResetSCC!", e);
		}
	}

	/**
	 * To recompute the total amounts based on the base currency
	 * @param anISCCertificate of ISCCertificate type
	 * @return ISCCertificate - the sc certificate object
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificate computeTotalAmounts(ISCCertificate anISCCertificate) throws SCCertificateException {
		if (anISCCertificate == null) {
			throw new SCCertificateException("The ISCCertificate is null !!!");
		}

		ISCCertificateItem[] itemList = anISCCertificate.getSCCItemList();
		if ((itemList == null) || (itemList.length == 0)) {
			throw new SCCertificateException("There is no scc item in the scc");
		}

		CurrencyCode baseCurrency = BaseCurrency.getCurrencyCode();
		long totalActivatedAmt = 0;
		long totalCleanActivatedAmt = 0;
		long totalNotCleanActivatedAmt = 0;
		for (int ii = 0; ii < itemList.length; ii++) {
			if (!itemList[ii].isInnerLimit()) {
				try {
					if (totalActivatedAmt != -1) {
						long actAmt = convertAmount(itemList[ii].getActivatedAmount(), baseCurrency);
						totalActivatedAmt = totalActivatedAmt + actAmt;
						if (itemList[ii].isCleanType()) {
							totalCleanActivatedAmt += actAmt;
						}
						else {
							totalNotCleanActivatedAmt += actAmt;
						}
					}
				}
				catch (SCCertificateException ex) {
					totalActivatedAmt = -1;
					totalCleanActivatedAmt = -1;
					totalNotCleanActivatedAmt = -1;
				}
			}
		}
		if (totalActivatedAmt != -1) {
			anISCCertificate.setTotalActivatedAmount(new Amount(totalActivatedAmt, baseCurrency));
			anISCCertificate.setCleanActivatedAmount(new Amount(totalCleanActivatedAmt, baseCurrency));
			anISCCertificate.setActivatedAmount(new Amount(totalNotCleanActivatedAmt, baseCurrency));
		}
		return anISCCertificate;
	}

	/**
	 * To recompute the total amounts based on the base currency
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @return IPartialSCCertificate - the partial sc certificate object
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificate computeTotalAmounts(IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException {
		if (anIPartialSCCertificate == null) {
			throw new SCCertificateException("The IPartialSCCertificate is null !!!");
		}

		IPartialSCCertificateItem[] itemList = anIPartialSCCertificate.getPartialSCCItemList();

		if ((itemList == null) || (itemList.length == 0)) {
			throw new SCCertificateException("There is no partial scc item in the scc");
		}

		CurrencyCode baseCurrency = BaseCurrency.getCurrencyCode();
		long totalActivatedAmt = 0;
		long totalCleanActivatedAmt = 0;
		long totalNotCleanActivatedAmt = 0;
		for (int ii = 0; ii < itemList.length; ii++) {
			if (!itemList[ii].isInnerLimit()) {
				try {
					if (totalActivatedAmt != -1) {
						long actAmt = convertAmount(itemList[ii].getActivatedAmount(), baseCurrency);
						totalActivatedAmt = totalActivatedAmt + actAmt;
						if (itemList[ii].isCleanType()) {
							totalCleanActivatedAmt += actAmt;
						}
						else {
							totalNotCleanActivatedAmt += actAmt;
						}
					}
				}
				catch (SCCertificateException ex) {
					totalActivatedAmt = -1;
					totalCleanActivatedAmt = -1;
					totalNotCleanActivatedAmt = -1;
				}
			}
		}
		if (totalActivatedAmt != -1) {
			anIPartialSCCertificate.setTotalActivatedAmount(new Amount(totalActivatedAmt, baseCurrency));
			anIPartialSCCertificate.setCleanActivatedAmount(new Amount(totalCleanActivatedAmt, baseCurrency));
			anIPartialSCCertificate.setActivatedAmount(new Amount(totalNotCleanActivatedAmt, baseCurrency));
		}
		return anIPartialSCCertificate;
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
		if (anIPartialSCCertificate == null) {
			throw new SCCertificateException("The IPartialSCCertificate is null !!!");
		}

		IPartialSCCertificateItem[] itemList = anIPartialSCCertificate.getPartialSCCItemList();
		if ((itemList == null) || (itemList.length == 0)) {
			throw new SCCertificateException("There is no partial scc item in the scc");
		}

		CurrencyCode baseCurrency = BaseCurrency.getCurrencyCode();
		long totalActivatedAmt = 0;
		long totalApprovedAmt = 0;
		long totalCleanActivatedAmt = 0;
		long totalCleanApprovedAmt = 0;
		long totalNotCleanActivatedAmt = 0;
		long totalNotCleanApprovedAmt = 0;
		for (int ii = 0; ii < itemList.length; ii++) {
			if (itemList[ii].getIsPartialSCCIssued() && !itemList[ii].isInnerLimit()) {
				try {
					if (totalApprovedAmt != -1) {
						long actAmt = convertAmount(itemList[ii].getActivatedAmount(), baseCurrency);
						long appAmt = convertAmount(itemList[ii].getApprovedLimitAmount(), baseCurrency);

						totalActivatedAmt = totalActivatedAmt + actAmt;
						totalApprovedAmt = totalApprovedAmt + appAmt;
						if (itemList[ii].isCleanType()) {
							totalCleanActivatedAmt += actAmt;
							totalCleanApprovedAmt += appAmt;
						}
						else {
							totalNotCleanActivatedAmt += actAmt;
							totalNotCleanApprovedAmt += appAmt;
						}
					}
				}
				catch (SCCertificateException ex) {
					totalActivatedAmt = -1;
					totalApprovedAmt = -1;
					totalCleanActivatedAmt = -1;
					totalCleanApprovedAmt = -1;
					totalNotCleanActivatedAmt = -1;
					totalNotCleanApprovedAmt = -1;
				}
			}
		}
		if (totalApprovedAmt != -1) {
			anIPartialSCCertificate.setTotalActivatedAmount(new Amount(totalActivatedAmt, baseCurrency));
			anIPartialSCCertificate.setTotalApprovalAmount(new Amount(totalApprovedAmt, baseCurrency));
			anIPartialSCCertificate.setCleanActivatedAmount(new Amount(totalCleanActivatedAmt, baseCurrency));
			anIPartialSCCertificate.setCleanApprovalAmount(new Amount(totalCleanApprovedAmt, baseCurrency));
			anIPartialSCCertificate.setActivatedAmount(new Amount(totalNotCleanActivatedAmt, baseCurrency));
			anIPartialSCCertificate.setApprovalAmount(new Amount(totalNotCleanApprovedAmt, baseCurrency));
		}
		return anIPartialSCCertificate;
	}

	/**
	 * Check if there is any SCC generated
	 * @param anILimitProfile of ILimitProfile
	 * @return boolean - true if SCC is generated and false otherwise
	 * @throws SCCertificateException on errors
	 */
	private boolean isSCCGenerated(ILimitProfile anILimitProfile) throws SCCertificateException {
		SCCertificateSearchCriteria criteria = new SCCertificateSearchCriteria();
		criteria.setLimitProfileID(anILimitProfile.getLimitProfileID());
		String[] trxStatusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_PENDING_UPDATE,
				ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_ACTIVE };
		criteria.setTrxStatusList(trxStatusList);
		int count = getNoOfSCCertificate(criteria);
		if (count == 0) {
			return false;
		}
		return true;
	}

	private ISCCertificateCustomerDetail getSCCustomerInfo(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer)
			throws SCCertificateException {                                                        
		ISCCertificateCustomerDetail detail = getSCCustomerInfo(anICMSCustomer);
		detail.setApprovalDate(anILimitProfile.getApprovalDate());
		detail.setOriginatingLocation(anILimitProfile.getOriginatingLocation());
		detail.setNextReviewDate(anILimitProfile.getNextAnnualReviewDate());
		detail.setApprovalAuthority(anILimitProfile.getApproverEmployeeName1());
		detail.setExtReviewDate(anILimitProfile.getExtendedNextReviewDate());

		try {
			detail.setFinalBFLIssuedDate(getFinalBFLIssuedDate(anILimitProfile));
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			HashMap famInfo = (HashMap) limitProxy.getFAMName(anILimitProfile.getLimitProfileID());
			if (famInfo != null) {
				detail.setFamCode((String) famInfo.get(ICMSConstant.FAM_CODE));
				detail.setFamName((String) famInfo.get(ICMSConstant.FAM_NAME));
			}
			return detail;
		}
		catch (LimitException ex) {
			throw new SCCertificateException(ex);
		}
	}

	private Date getFinalBFLIssuedDate(ILimitProfile anILimitProfile) {
		ITATEntry[] entries = anILimitProfile.getTATEntries();
		if ((entries != null) && (entries.length != 0)) {
			Arrays.sort(entries, new TATComparator());
			for (int ii = (entries.length - 1); ii >= 0; ii--) {
				// To fix cms1347
				// if
				// (ICMSConstant.TAT_CODE_CUSTOMER_ACCEPT_BFL.equals(entries[ii
				// ].getTATServiceCode()))
				if (ICMSConstant.TAT_CODE_ISSUE_CLEAN_BFL.equals(entries[ii].getTATServiceCode())) {
					return entries[ii].getTATStamp();
				}
				else if (ICMSConstant.TAT_CODE_SPECIAL_ISSUE_CLEAN_BFL.equals(entries[ii].getTATServiceCode())) {
					return entries[ii].getTATStamp();
				}
			}
		}
		return null;
	}

	/**
	 * Formulate the ISCCertificateCustomerDetail from the ICMSCustomer
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return ISCCertificateCustomerDetail - the SCC customer details
	 * @throws SCCertificateException on errors
	 */
	private ISCCertificateCustomerDetail getSCCustomerInfo(ICMSCustomer anICMSCustomer) throws SCCertificateException {
		ISCCertificateCustomerDetail custDetail = new OBSCCertificateCustomerDetail();
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
		else {
			ICreditStatus creditStat = new OBCreditStatus();
			custDetail.setCreditStatus(creditStat);
		}
		return custDetail;
	}

	/**
	 * Get the SCC trx value based on the Limit Profile
	 * @param anILimitProfile of LimitProfile type
	 * @return ISCCertificateTrxValue - the SCC trx value
	 * @throws SCCertificateException
	 */
	public ISCCertificate getSCCertificateByLimitProfile(ILimitProfile anILimitProfile) throws SCCertificateException {
		DefaultLogger.debug(this, "LimitProfileID: " + anILimitProfile.getLimitProfileID());
		ISCCertificate sccCert = getSCCertificateWithoutLimitInfo(anILimitProfile.getLimitProfileID());
		/*
		 * ISCCertificateTrxValue trxValue = null; if (sccCert != null) { long
		 * sccCertID = sccCert.getSCCertID(); trxValue =
		 * getSCCertificateTrxValue(anILimitProfile, sccCertID, true); return
		 * trxValue; } return null;
		 */
		return sccCert;
	}

	public IPartialSCCertificate getPartialSCCertificateByLimitProfile(ILimitProfile anILimitProfile)
			throws SCCertificateException {
		DefaultLogger.debug(this, "LimitProfileID: " + anILimitProfile.getLimitProfileID());
		IPartialSCCertificate psccCert = getPartialSCCertificateWithoutLimitInfo(anILimitProfile.getLimitProfileID());
		return psccCert;
	}

	/**
	 * Get the SCC trx value based on the Limit Profile
	 * @param anILimitProfile of LimitProfile type
	 * @return ISCCertificateTrxValue - the SCC trx value
	 * @throws SCCertificateException
	 */
	public ISCCertificateTrxValue getSCCertificateTrxByLimitProfileWithoutLimitInfo(ILimitProfile anILimitProfile)
			throws SCCertificateException {
		DefaultLogger.debug(this, "LimitProfileID: " + anILimitProfile.getLimitProfileID());
		ISCCertificate sccCert = getSCCertificateWithoutLimitInfo(anILimitProfile.getLimitProfileID());
		ISCCertificateTrxValue trxValue = null;
		if (sccCert != null) {
			long sccCertID = sccCert.getSCCertID();
			trxValue = getSCCertificateTrxValueWithoutLimitInfo(anILimitProfile, sccCertID);
			return trxValue;
		}
		return null;
	}

	/**
	 * Get the SCC trx value based on the Limit Profile
	 * @param anILimitProfile of LimitProfile type
	 * @return ISCCertificateTrxValue - the SCC trx value
	 * @throws SCCertificateException
	 */
	private ISCCertificateTrxValue getSCCertificateTrxValue(ILimitProfile anILimitProfile, boolean refreshLimit)
			throws SCCertificateException {
		ISCCertificate sccCert = getSCCertificateWithoutLimitInfo(anILimitProfile.getLimitProfileID());
		ISCCertificateTrxValue trxValue = null;
		if (sccCert != null) {
			long sccCertID = sccCert.getSCCertID();
			if (refreshLimit) {
				trxValue = getSCCertificateTrxValue(anILimitProfile, sccCertID, false);
			}
			else {
				trxValue = getSCCertificateTrxValue(sccCertID);
				if (trxValue.getSCCertificate() != null) {
					updateItemCleanTypeIndicator(trxValue.getSCCertificate().getSCCItemList(), anILimitProfile);
					calcTotalLimitAmount(trxValue.getSCCertificate(), trxValue.getSCCertificate().getSCCItemList());
					populateGeneralLimitInfo(anILimitProfile, trxValue.getSCCertificate().getSCCItemList());
				}
				else {
					updateItemCleanTypeIndicator(trxValue.getStagingSCCertificate().getSCCItemList(), anILimitProfile);
					calcTotalLimitAmount(trxValue.getStagingSCCertificate(), trxValue.getStagingSCCertificate()
							.getSCCItemList());
					populateGeneralLimitInfo(anILimitProfile, trxValue.getStagingSCCertificate().getSCCItemList());
				}
			}
			if (!ICMSConstant.STATE_CLOSED.equals(trxValue.getStatus())) {
				// CR146: needed by checker view latest generated scc page ---
				// shifted up to before calcTotalLimitAmount
				// if(trxValue.getSCCertificate()!=null &&
				// trxValue.getSCCertificate().getSCCItemList()!=null) {
				// DefaultLogger.debug(this,
				// ">>>>>>>> Entering updateItemCleanTypeIndicator from getSCCertificateTrxValue (status != Closed) actual"
				// );
				// updateItemCleanTypeIndicator(trxValue.getSCCertificate().
				// getSCCItemList(), anILimitProfile);
				// }
				// if(trxValue.getStagingSCCertificate()!=null &&
				// trxValue.getStagingSCCertificate().getSCCItemList()!=null) {
				// DefaultLogger.debug(this,
				// ">>>>>>>> Entering updateItemCleanTypeIndicator from getSCCertificateTrxValue (status != Closed) staging"
				// );
				//updateItemCleanTypeIndicator(trxValue.getStagingSCCertificate(
				// ).getSCCItemList(), anILimitProfile);
				// }
				return trxValue;
			}
		}
		ISCCertificateItem[] itemList = getSCCertificateItemList(anILimitProfile);
		sccCert = new OBSCCertificate();
		sccCert.setLimitProfileID(anILimitProfile.getLimitProfileID());
		sccCert.setSCCItemList(itemList);
		populateLimitInfo(anILimitProfile, sccCert, false);
		trxValue = new OBSCCertificateTrxValue();
		trxValue.setStagingSCCertificate(sccCert);
		return trxValue;
	}

	/**
	 * Get the SCC trx value based on the Limit Profile
	 * @param anILimitProfile of LimitProfile type
	 * @return ISCCertificateTrxValue - the SCC trx value
	 * @throws SCCertificateException
	 */
	private ISCCertificateTrxValue getSCCertificateTrxValueWithoutLimitInfo(ILimitProfile anILimitProfile)
			throws SCCertificateException {
		ISCCertificate sccCert = getSCCertificateWithoutLimitInfo(anILimitProfile.getLimitProfileID());
		ISCCertificateTrxValue trxValue = null;
		if (sccCert != null) {
			long sccCertID = sccCert.getSCCertID();
			trxValue = getSCCertificateTrxValueWithoutLimitInfo(anILimitProfile, sccCertID);
			if (!ICMSConstant.STATE_CLOSED.equals(trxValue.getStatus())) {
				return trxValue;
			}
		}
		ISCCertificateItem[] itemList = getSCCertificateItemList(anILimitProfile);
		sccCert = new OBSCCertificate();
		sccCert.setLimitProfileID(anILimitProfile.getLimitProfileID());
		sccCert.setSCCItemList(itemList);
		// populateLimitInfo(anILimitProfile, sccCert, false);
		trxValue = new OBSCCertificateTrxValue();
		trxValue.setStagingSCCertificate(sccCert);
		return trxValue;
	}

	/**
	 * Get the Partial SCC trx value based on the Limit Profile
	 * @param anILimitProfile of LimitProfile type
	 * @return IPartialSCCertificateTrxValue - the Partial SCC trx value
	 * @throws SCCertificateException
	 */
	private IPartialSCCertificateTrxValue getPartialSCCertificateTrxValueWithoutLimit(ILimitProfile anILimitProfile)
			throws SCCertificateException {
		IPartialSCCertificate psccCert = getPartialSCCertificateWithoutLimitInfo(anILimitProfile.getLimitProfileID());
		IPartialSCCertificateTrxValue trxValue = null;
		if (psccCert != null) {
			long psccCertID = psccCert.getSCCertID();
			trxValue = getPartialSCCertificateTrxValue(anILimitProfile, psccCertID);
			return trxValue;
		}
		IPartialSCCertificateItem[] itemList = getPartialSCCertificateItemList(anILimitProfile);
		psccCert = new OBPartialSCCertificate();
		psccCert.setLimitProfileID(anILimitProfile.getLimitProfileID());
		psccCert.setSCCItemList(itemList);
		trxValue = new OBPartialSCCertificateTrxValue();
		trxValue.setStagingPartialSCCertificate(psccCert);
		return trxValue;
	}

	/**
	 * Get the Partial SCC trx value based on the Limit Profile
	 * @param anILimitProfile of LimitProfile type
	 * @return IPartialSCCertificateTrxValue - the Partial SCC trx value
	 * @throws SCCertificateException
	 */
	private IPartialSCCertificateTrxValue getPartialSCCertificateTrxValue(ILimitProfile anILimitProfile,
			boolean refreshLimit) throws SCCertificateException {
		IPartialSCCertificate psccCert = getPartialSCCertificateWithoutLimitInfo(anILimitProfile.getLimitProfileID());
		IPartialSCCertificateTrxValue trxValue = null;
		if (psccCert != null) {
			long psccCertID = psccCert.getSCCertID();
			if (refreshLimit) {
				trxValue = getPartialSCCertificateTrxValue(anILimitProfile, psccCertID);
			}
			else {
				trxValue = getPartialSCCertificateTrxValue(psccCertID);
				if (trxValue.getPartialSCCertificate() != null) {
					updateItemCleanTypeIndicator(trxValue.getPartialSCCertificate().getPartialSCCItemList(),
							anILimitProfile);
					calcTotalLimitAmount(trxValue.getPartialSCCertificate(), trxValue.getPartialSCCertificate()
							.getPartialSCCItemList());
					populateGeneralLimitInfo(anILimitProfile, trxValue.getPartialSCCertificate().getSCCItemList());
				}
				else {
					updateItemCleanTypeIndicator(trxValue.getStagingPartialSCCertificate().getPartialSCCItemList(),
							anILimitProfile);
					calcTotalLimitAmount(trxValue.getStagingPartialSCCertificate(), trxValue
							.getStagingPartialSCCertificate().getPartialSCCItemList());
					populateGeneralLimitInfo(anILimitProfile, trxValue.getStagingPartialSCCertificate()
							.getSCCItemList());
				}
			}

			// Shifted up to before calcTotalLimitAmount
			// if(trxValue.getPartialSCCertificate()!=null &&
			// trxValue.getPartialSCCertificate().getPartialSCCItemList()!=null)
			// {
			// DefaultLogger.debug(this,
			// ">>>>>>>> Entering updateItemCleanTypeIndicator from getPartialSCCertificateTrxValue (actual)"
			// );
			// updateItemCleanTypeIndicator(trxValue.getPartialSCCertificate().
			// getPartialSCCItemList(), anILimitProfile);
			// }
			// if(trxValue.getStagingPartialSCCertificate()!=null &&
			// trxValue.getStagingPartialSCCertificate
			// ().getPartialSCCItemList()!=null) {
			// DefaultLogger.debug(this,
			// ">>>>>>>> Entering updateItemCleanTypeIndicator from getPartialSCCertificateTrxValue (staging)"
			// );
			// updateItemCleanTypeIndicator(trxValue.
			// getStagingPartialSCCertificate().getPartialSCCItemList(),
			// anILimitProfile);
			// }

			return trxValue;
		}
		IPartialSCCertificateItem[] itemList = getPartialSCCertificateItemList(anILimitProfile);
		psccCert = new OBPartialSCCertificate();
		psccCert.setLimitProfileID(anILimitProfile.getLimitProfileID());
		psccCert.setSCCItemList(itemList);
		populateLimitInfo(anILimitProfile, psccCert);
		trxValue = new OBPartialSCCertificateTrxValue();
		trxValue.setStagingPartialSCCertificate(psccCert);
		return trxValue;
	}

	/**
	 * Get the SCC trx value based on limit profile and SCCert ID
	 * @param anILimitProfile of ILimitProfile type
	 * @param anSCCertID of long type
	 * @return ISCCertificateTrxValue - the scc trx value
	 * @throws SCCertificateException
	 */
	private ISCCertificateTrxValue getSCCertificateTrxValue(ILimitProfile anILimitProfile, long anSCCertID,
			boolean isBCARenewal) throws SCCertificateException {
		ISCCertificateTrxValue trxValue = getSCCertificateTrxValue(anSCCertID);
		if (trxValue.getStagingSCCertificate() != null) {
			populateLimitInfo(anILimitProfile, trxValue.getStagingSCCertificate(), isBCARenewal);
		}
		if (trxValue.getSCCertificate() != null) {
			populateLimitInfo(anILimitProfile, trxValue.getSCCertificate(), isBCARenewal);
			// need to massage the staging and actual certificate to cater for
			// the
			// checker screen
			mergeCertificate(trxValue.getStagingSCCertificate(), trxValue.getSCCertificate());
		}
		return trxValue;
	}

	private void mergeCertificate(ISCCertificate aStagingCertificate, ISCCertificate anActualCertificate)
			throws SCCertificateException {
		DefaultLogger.debug(this, "In mergeCertificate");
		ISCCertificateItem[] stageItemList = aStagingCertificate.getSCCItemList();
		ISCCertificateItem[] actualItemList = anActualCertificate.getSCCItemList();
		if ((stageItemList != null) && (stageItemList.length > 0) && (actualItemList != null)
				&& (actualItemList.length > 0)) {
			DefaultLogger.debug(this, "In processing !!!");
			for (int ii = 0; ii < actualItemList.length; ii++) {
				for (int jj = 0; jj < stageItemList.length; jj++) {
					if (actualItemList[ii].getLimitType().equals(stageItemList[jj].getLimitType())
							&& (actualItemList[ii].getLimitID() == stageItemList[jj].getLimitID())) {
						DefaultLogger.debug(this, "Stage Activated Limit: " + stageItemList[jj].getActivatedAmount());
						DefaultLogger.debug(this, "Actual Activated Limit: " + actualItemList[ii].getActivatedAmount());
						actualItemList[ii].setSCCertItemRef(stageItemList[jj].getSCCertItemRef());
					}
				}
			}
		}
	}

	private void mergeCertificate(IPartialSCCertificate aStagingCertificate, IPartialSCCertificate anActualCertificate)
			throws SCCertificateException {
		// DefaultLogger.debug(this, "In mergeCertificate");
		IPartialSCCertificateItem[] stageItemList = aStagingCertificate.getPartialSCCItemList();
		IPartialSCCertificateItem[] actualItemList = anActualCertificate.getPartialSCCItemList();
		if ((stageItemList != null) && (stageItemList.length > 0) && (actualItemList != null)
				&& (actualItemList.length > 0)) {
			for (int ii = 0; ii < actualItemList.length; ii++) {
				for (int jj = 0; jj < stageItemList.length; jj++) {
					if (actualItemList[ii].getLimitType().equals(stageItemList[jj].getLimitType())
							&& (actualItemList[ii].getLimitID() == stageItemList[jj].getLimitID())) {
						actualItemList[ii].setSCCertItemRef(stageItemList[jj].getSCCertItemRef());
					}
				}
			}
		}
	}

	private ISCCertificateTrxValue getSCCertificateTrxValueWithoutLimitInfo(ILimitProfile anILimitProfile,
			long anSCCertID) throws SCCertificateException {
		ISCCertificateTrxValue trxValue = getSCCertificateTrxValue(anSCCertID);
		/*
		 * if (trxValue.getStagingSCCertificate() != null) {
		 * populateLimitInfo(anILimitProfile,
		 * trxValue.getStagingSCCertificate()); } if
		 * (trxValue.getSCCertificate() != null) {
		 * populateLimitInfo(anILimitProfile, trxValue.getSCCertificate()); }
		 */
		return trxValue;
	}

	/**
	 * Get the Partial SCC trx value based on limit profile and SCCert ID
	 * @param anILimitProfile of ILimitProfile type
	 * @param anSCCertID of long type
	 * @return IPartialSCCertificateTrxValue - the partial scc trx value
	 * @throws SCCertificateException
	 */
	private IPartialSCCertificateTrxValue getPartialSCCertificateTrxValue(ILimitProfile anILimitProfile, long anSCCertID)
			throws SCCertificateException {
		IPartialSCCertificateTrxValue trxValue = getPartialSCCertificateTrxValue(anSCCertID);
		if (trxValue.getStagingPartialSCCertificate() != null) {
			populateLimitInfo(anILimitProfile, trxValue.getStagingPartialSCCertificate());
		}
		if (trxValue.getPartialSCCertificate() != null) {
			populateLimitInfo(anILimitProfile, trxValue.getPartialSCCertificate());
			// need to massage the staging and actual certificate to cater for
			// the
			// checker screen
			mergeCertificate(trxValue.getStagingPartialSCCertificate(), trxValue.getPartialSCCertificate());
		}
		return trxValue;
	}

	/**
	 * Helper method to calculate total activated and approved limit amount.
	 * @param scc of type ISCCertificate
	 * @param sccItems of type ISCCertificateItem[]
	 */
	private void calcTotalLimitAmount(ISCCertificate scc, ISCCertificateItem[] sccItems) {

		long cleanApprovalAmt = 0; // added for R1.5 CR146
		long cleanActivatedAmt = 0; // added for R1.5 CR146
		long notCleanApprovalAmt = 0; // added for R1.5 CR146
		long notCleanActivatedAmt = 0; // added for R1.5 CR146
		long totalApprovalAmt = 0;
		long totalActivatedAmt = 0;

		long amt = 0;
		CurrencyCode baseCurrency = BaseCurrency.getCurrencyCode();
		int count = sccItems == null ? 0 : sccItems.length;

		for (int ii = 0; ii < count; ii++) {
			ISCCertificateItem item = sccItems[ii];
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
				catch (SCCertificateException ex) {
					cleanApprovalAmt = -1;
					notCleanApprovalAmt = -1;
					totalApprovalAmt = -1;
				}
				try {
					if (totalActivatedAmt != -1) {
						if (item.getActivatedAmount() != null) {
							amt = convertAmount(item.getActivatedAmount(), baseCurrency);
							totalActivatedAmt = totalActivatedAmt + amt;

							if (item.isCleanType()) {
								cleanActivatedAmt = cleanActivatedAmt + amt;
							}
							else {
								notCleanActivatedAmt = notCleanActivatedAmt + amt;
							}
						}
					}
				}
				catch (SCCertificateException ex) {
					cleanActivatedAmt = -1;
					notCleanActivatedAmt = -1;
					totalActivatedAmt = -1;
				}
			}
		}
		if (totalApprovalAmt != -1) {
			scc.setCleanApprovalAmount(new Amount(cleanApprovalAmt, baseCurrency));
			scc.setApprovalAmount(new Amount(notCleanApprovalAmt, baseCurrency));
			scc.setTotalApprovalAmount(new Amount(totalApprovalAmt, baseCurrency));
		}
		if (totalActivatedAmt != -1) {
			scc.setCleanActivatedAmount(new Amount(cleanActivatedAmt, baseCurrency));
			scc.setActivatedAmount(new Amount(notCleanActivatedAmt, baseCurrency));
			scc.setTotalActivatedAmount(new Amount(totalActivatedAmt, baseCurrency));
		}
	}

	/**
	 * Populate the limit information base on the limitprofile
	 * @param anILimitProfile of ILimitProfile type
	 * @param anISCCertificate of ISCCertificate type
	 * @throws SCCertificateException
	 */
	private void populateLimitInfo(ILimitProfile anILimitProfile, ISCCertificate anISCCertificate, boolean isBCARenewal)
			throws SCCertificateException {
		ISCCertificateItem[] fullCertItemList = getSCCertificateItemList(anILimitProfile);
		ISCCertificateItem[] itemList = anISCCertificate.getSCCItemList();
		if ((itemList != null) && (itemList.length > 0)) {
			mergeCertItemList(fullCertItemList, itemList);
		}
		calcTotalLimitAmount(anISCCertificate, fullCertItemList);
		anISCCertificate.setSCCItemList(fullCertItemList);
	}

	private void mergeCertItemList(ISCCertificateItem[] aFullCertItemList, ISCCertificateItem[] aCertItemList) {
		for (int ii = 0; ii < aCertItemList.length; ii++) {
			for (int jj = 0; jj < aFullCertItemList.length; jj++) {
				if (aFullCertItemList[jj].getLimitType().equals(aCertItemList[ii].getLimitType())
						&& (aFullCertItemList[jj].getLimitID() == aCertItemList[ii].getLimitID())) {
					aFullCertItemList[jj].setSCCertItemID(aCertItemList[ii].getSCCertItemID());
					aFullCertItemList[jj].setSCCertItemRef(aCertItemList[ii].getSCCertItemRef());
					aFullCertItemList[jj].setIsDeletedInd(aCertItemList[ii].getIsDeletedInd());
					aFullCertItemList[jj].setActivatedAmount(aCertItemList[ii].getActivatedAmount());
					aFullCertItemList[jj].setMaturityDate(aCertItemList[ii].getMaturityDate());
                    aFullCertItemList[jj].setExpiryAvailabilityDate(aCertItemList[ii].getExpiryAvailabilityDate());
                    aFullCertItemList[jj].setDisbursementAmount(aCertItemList[ii].getDisbursementAmount());
                    aFullCertItemList[jj].setEnforceAmount(aCertItemList[ii].getEnforceAmount());
                    aFullCertItemList[jj].setPaymentInstruction(aCertItemList[ii].getPaymentInstruction());
				}
			}
		}
	}

	/*
	 * backup private void populateLimitInfo(ILimitProfile anILimitProfile,
	 * ISCCertificate anISCCertificate, boolean isBCARenewal) throws
	 * SCCertificateException { try { ILimit[] limitList =
	 * anILimitProfile.getNonDeletedLimits(); if
	 * ((anILimitProfile.getNonDeletedLimits() == null) ||
	 * (anILimitProfile.getNonDeletedLimits().length ==0)) {
	 * SCCertificateException exp = new
	 * SCCertificateException("There is on limit in this limit profile");
	 * exp.setErrorCode(ICMSErrorCodes.SCC_NOT_REQUIRED); throw exp; } if
	 * (isBCARenewal) { limitList = filterOffNewLimits(limitList); }
	 * ISCCertificateItem[] itemList = anISCCertificate.getSCCItemList(); Date
	 * approvalDate = anILimitProfile.getApprovalDate(); long totalApprovalAmt =
	 * 0; long totalActivatedAmt = 0; CurrencyCode baseCurrency =
	 * BaseCurrency.getCurrencyCode(); ICheckListProxyManager proxy =
	 * CheckListProxyManagerFactory.getCheckListProxyManager(); HashMap
	 * checkListMap =
	 * proxy.getCollateralCheckListStatus(anILimitProfile.getLimitProfileID());
	 * boolean hasCollateral = false; for (int ii=0; ii< limitList.length; ii++)
	 * { if (!ICMSConstant.STATE_DELETED.equals(limitList[ii].getLimitStatus()))
	 * { ICollateralAllocation[] colAllocationList =
	 * limitList[ii].getCollateralAllocations(); if ((colAllocationList != null)
	 * && (colAllocationList.length >0)) { hasCollateral = true;
	 * ISCCertificateItem item = getSCCertificateItem(itemList,
	 * limitList[ii].getLimitID()); item.setLimit(limitList[ii]);
	 * item.setApprovalDate(approvalDate);
	 * item.setCheckListStatus(getCheckListStatus(colAllocationList,
	 * checkListMap, isBCARenewal)); if (!item.isInnerLimit()) { try { if
	 * (totalApprovalAmt != -1) { totalApprovalAmt = totalApprovalAmt +
	 * convertAmount(limitList[ii].getApprovedLimitAmount(), baseCurrency); } }
	 * catch(SCCertificateException ex) { totalApprovalAmt = -1; } } try { if
	 * (totalActivatedAmt != -1) { if (item.getActivatedAmount() != null) {
	 * totalActivatedAmt = totalActivatedAmt +
	 * convertAmount(item.getActivatedAmount(), baseCurrency); } } }
	 * catch(SCCertificateException ex) { totalActivatedAmt = -1; } } } } if
	 * (!hasCollateral) { SCCertificateException exp = new
	 * SCCertificateException("There is no collateral in this limit profile");
	 * exp.setErrorCode(ICMSErrorCodes.SCC_NOT_REQUIRED); throw exp; } if
	 * (totalApprovalAmt != -1) { anISCCertificate.setTotalApprovalAmount(new
	 * Amount(totalApprovalAmt, baseCurrency)); } if (totalActivatedAmt != -1) {
	 * anISCCertificate.setTotalActivatedAmount(new Amount(totalActivatedAmt,
	 * baseCurrency)); } } catch(CheckListException ex) { rollback(); throw new
	 * SCCertificateException("Exception in populateLimitInfo", ex); }
	 * catch(SearchDAOException ex) { rollback(); throw new
	 * SCCertificateException("Exception in populateLimitInfo", ex); } }
	 */

	private ILimit[] filterOffNewLimits(ILimit[] aLimitList) {
		ArrayList list = new ArrayList();
		for (int ii = 0; ii < aLimitList.length; ii++) {
			if (aLimitList[ii].getExistingInd()) {
				list.add(aLimitList[ii]);
			}
		}
		return (ILimit[]) list.toArray(new ILimit[list.size()]);
	}

	/**
	 * Populate the limit information base on the limitprofile
	 * @param anILimitProfile of ILimitProfile type
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @throws SCCertificateException
	 */
	private void populateLimitInfo(ILimitProfile anILimitProfile, IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException {
		IPartialSCCertificateItem[] fullCertItemList = getPartialSCCertificateItemList(anILimitProfile);
		IPartialSCCertificateItem[] itemList = anIPartialSCCertificate.getPartialSCCItemList();
		if ((itemList != null) && (itemList.length > 0)) {
			mergeCertItemList(fullCertItemList, itemList);
		}
		calcTotalLimitAmount(anIPartialSCCertificate, fullCertItemList);
		anIPartialSCCertificate.setSCCItemList(fullCertItemList);
	}

	private void mergeCertItemList(IPartialSCCertificateItem[] aFullCertItemList,
			IPartialSCCertificateItem[] aCertItemList) {
		for (int ii = 0; ii < aCertItemList.length; ii++) {
			for (int jj = 0; jj < aFullCertItemList.length; jj++) {
				if (aFullCertItemList[jj].getLimitType().equals(aCertItemList[ii].getLimitType())
						&& (aFullCertItemList[jj].getLimitID() == aCertItemList[ii].getLimitID())) {
					aFullCertItemList[jj].setSCCertItemID(aCertItemList[ii].getSCCertItemID());
					aFullCertItemList[jj].setSCCertItemRef(aCertItemList[ii].getSCCertItemRef());
					aFullCertItemList[jj].setIsDeletedInd(aCertItemList[ii].getIsDeletedInd());
					aFullCertItemList[jj].setActivatedAmount(aCertItemList[ii].getActivatedAmount());
					aFullCertItemList[jj].setIsPartialSCCIssued(aCertItemList[ii].getIsPartialSCCIssued());
					aFullCertItemList[jj].setIssuedDate(aCertItemList[ii].getIssuedDate());
					aFullCertItemList[jj].setMaturityDate(aCertItemList[ii].getMaturityDate());
                    aFullCertItemList[jj].setExpiryAvailabilityDate(aCertItemList[ii].getExpiryAvailabilityDate());
                    aFullCertItemList[jj].setDisbursementAmount(aCertItemList[ii].getDisbursementAmount());
                    aFullCertItemList[jj].setEnforceAmount(aCertItemList[ii].getEnforceAmount());
                    aFullCertItemList[jj].setPaymentInstruction(aCertItemList[ii].getPaymentInstruction());
				}
			}
		}
	}

	/*
	 * private void populateLimitInfo(ILimitProfile anILimitProfile,
	 * IPartialSCCertificate anIPartialSCCertificate) throws
	 * SCCertificateException { try { ILimit[] limitList =
	 * anILimitProfile.getNonDeletedLimits(); if
	 * ((anILimitProfile.getNonDeletedLimits() == null) ||
	 * (anILimitProfile.getNonDeletedLimits().length ==0)) {
	 * SCCertificateException exp = new
	 * SCCertificateException("There is on limit in this limit profile");
	 * exp.setErrorCode(ICMSErrorCodes.PSCC_NOT_REQUIRED); throw exp; }
	 * IPartialSCCertificateItem[] itemList =
	 * anIPartialSCCertificate.getPartialSCCItemList(); Date approvalDate =
	 * anILimitProfile.getApprovalDate(); long totalApprovalAmt = 0; long
	 * totalActivatedAmt = 0; CurrencyCode baseCurrency =
	 * BaseCurrency.getCurrencyCode(); ICheckListProxyManager proxy =
	 * CheckListProxyManagerFactory.getCheckListProxyManager(); HashMap
	 * checkListMap =
	 * proxy.getCollateralCheckListStatus(anILimitProfile.getLimitProfileID());
	 * ArrayList psccList = new ArrayList(); int numOfCollaterals = 0; for (int
	 * ii=0; ii< limitList.length; ii++) { ICollateralAllocation[]
	 * colAllocationList = limitList[ii].getCollateralAllocations(); if
	 * ((colAllocationList != null) && (colAllocationList.length >0)) {
	 * numOfCollaterals = numOfCollaterals + colAllocationList.length; HashMap
	 * hmap = getCheckListMap(colAllocationList, checkListMap); if
	 * (isPartialSCCAllowed(hmap)) { IPartialSCCertificateItem item =
	 * getPartialSCCertificateItem(itemList, limitList[ii]);
	 * item.setLimit(limitList[ii]); item.setApprovalDate(approvalDate);
	 * item.setCheckListMap(hmap); psccList.add(item); if (!item.isInnerLimit())
	 * { try { if (totalApprovalAmt != -1) { totalApprovalAmt = totalApprovalAmt
	 * + convertAmount(limitList[ii].getApprovedLimitAmount(), baseCurrency); }
	 * } catch(SCCertificateException ex) { totalApprovalAmt = -1; } } try { if
	 * (item.getActivatedAmount() != null) { totalActivatedAmt =
	 * totalActivatedAmt + convertAmount(item.getActivatedAmount(),
	 * baseCurrency); } } catch(SCCertificateException ex) { totalActivatedAmt =
	 * -1; } } } }
	 * 
	 * if (numOfCollaterals == 0) { SCCertificateException exp = new
	 * SCCertificateException("There is no collateral in this limit profile");
	 * exp.setErrorCode(ICMSErrorCodes.PSCC_NOT_REQUIRED); throw exp; }
	 * IPartialSCCertificateItem[] psccItems =
	 * (IPartialSCCertificateItem[])psccList.toArray(new
	 * IPartialSCCertificateItem[0]); if ((psccItems == null) ||
	 * (psccItems.length == 0)) { SCCertificateException exp = new
	 * SCCertificateException
	 * ("There is no limit with security checklist perfected");
	 * exp.setErrorCode(ICMSErrorCodes.PSCC_CHECKLIST_NOT_PERFECTED); throw exp;
	 * } anIPartialSCCertificate.setSCCItemList(psccItems); if (totalApprovalAmt
	 * != -1) { anIPartialSCCertificate.setTotalApprovalAmount(new
	 * Amount(totalApprovalAmt, baseCurrency)); } if (totalActivatedAmt != -1) {
	 * anIPartialSCCertificate.setTotalActivatedAmount(new
	 * Amount(totalActivatedAmt, baseCurrency)); } } catch(CheckListException
	 * ex) { rollback(); throw new
	 * SCCertificateException("Exception in populateLimitInfo", ex); }
	 * catch(SearchDAOException ex) { rollback(); throw new
	 * SCCertificateException("Exception in populateLimitInfo", ex); } }
	 */

    private boolean isPartialSCCAllowed(HashMap aCheckListMap) throws SCCertificateException {
        try {

            //Allowed Check List Status - Add More Here
            HashMap STATUS = new HashMap();
            STATUS.put(ICMSConstant.STATE_CHECKLIST_IN_PROGRESS, null);

            for (Iterator itr = aCheckListMap.keySet().iterator(); itr.hasNext();) {
                Long collateralID = (Long) itr.next();

                String checkListStatus = (String) aCheckListMap.get(collateralID);

                if (!STATUS.containsKey(checkListStatus)) return false;

                /* Not Required in ABCLIMS
                ICollateral col = CollateralProxyFactory.getProxy().getCollateral(collateralID.longValue(), false);
                if (!col.getIsPerfected()) return false;
                */
            }

            return true;
            /* Rewrite at above
            Iterator iter = aCheckListMap.keySet().iterator();
            while (iter.hasNext()) {
                Long collateralID = (Long) iter.next();
                String checkListStatus = (String) aCheckListMap.get(collateralID);
                if ((ICMSConstant.STATE_CHECKLIST_IN_PROGRESS.equals(checkListStatus))
                        || (ICMSConstant.STATE_CHECKLIST_DEFERRED.equals(checkListStatus))
                        || (ICMSConstant.STATE_CHECKLIST_DELETED.equals(checkListStatus))
                        || (ICMSConstant.STATE_CHECKLIST_OBSOLETE.equals(checkListStatus))) {
                    return false;
                }
                ICollateral col = CollateralProxyFactory.getProxy().getCollateral(collateralID.longValue(), false);
                if (!col.getIsPerfected()) {
                    return false;
                }
            }
            return true;
            */
        }
        /* Not Required in ABCLIMS
        catch (CollateralException ex) {
            throw new SCCertificateException(ex);
        }
        */
        catch (Exception e) {
            throw new SCCertificateException(e);
        }
    }

	/**
	 * Get the scc item that belongs to the limit ID specified
	 * @param anISCCertificateItemList of ISCCertificateItem[] type
	 * @param aLimitID of long type
	 * @return ISCCertificateItem - the scc item with that limit ID
	 * @throws SCCertificateException
	 */
	private ISCCertificateItem getSCCertificateItem(ISCCertificateItem[] anISCCertificateItemList, long aLimitID)
			throws SCCertificateException {
		for (int ii = 0; ii < anISCCertificateItemList.length; ii++) {
			if (anISCCertificateItemList[ii].getLimitID() == aLimitID) {
				return anISCCertificateItemList[ii];
			}
		}
		throw new SCCertificateException("The SCC Item with Limit ID: " + aLimitID + " is not found");
	}

	/**
	 * Get the partial scc item that belongs to the limit specified or create a
	 * new partial scc item
	 * @param anIPartialSCCertificateItemList of IPartialSCCertificateItem[]
	 *        type
	 * @param anILimit of ILimit type
	 * @return IPartialSCCertificateItem - the partial scc item with that limit
	 *         ID
	 */
	private IPartialSCCertificateItem getPartialSCCertificateItem(
			IPartialSCCertificateItem[] anIPartialSCCertificateItemList, ILimit anILimit) {
		for (int ii = 0; ii < anIPartialSCCertificateItemList.length; ii++) {
			if (anIPartialSCCertificateItemList[ii].getLimitID() == anILimit.getLimitID()) {
				return anIPartialSCCertificateItemList[ii];
			}
		}
		IPartialSCCertificateItem item = new OBPartialSCCertificateItem();
		// item.setLimit(anILimit);
		return item;
	}

	/**
	 * Get the checklist status for each checklist
	 * @param anICollateralAllocationList of ICollateralAllocation[] type
	 * @param aCheckListMap of HashMap type
	 * @return String - the checklist status
	 * @throws SCCertificateException
	 */
	private String getCheckListStatus(ICollateralAllocation[] anICollateralAllocationList, HashMap aCheckListMap,
			boolean isBCARenewal) throws SCCertificateException {
		try {
			String overAllCheckListStatus = null;
			for (int ii = 0; ii < anICollateralAllocationList.length; ii++) {
				// DefaultLogger.debug(this, "Host Status: " +
				// anICollateralAllocationList[ii].getHostStatus());
				// DefaultLogger.debug(this, "OverAll Status1: " +
				// overAllCheckListStatus);
				ICollateral col = anICollateralAllocationList[ii].getCollateral();
				col = CollateralProxyFactory.getProxy().getCollateral(col.getCollateralID(), false);
				// DefaultLogger.debug(this, "CollateralID: " +
				// col.getCollateralID());
				// DefaultLogger.debug(this, "CollateralPerfected: " +
				// col.getIsPerfected());
                //TODO : Need to have further understand on this....
				//if ((!isBCARenewal) && (!col.getIsPerfected())) {//for go isBCARenewal checking since this is not applicable in ABCLIMS
                /*
                if (!col.getIsPerfected()) {
					SCCertificateException exp = new SCCertificateException("Collateral " + col.getCollateralID()
							+ " is not perfected !!!");
					exp.setErrorCode(ICMSErrorCodes.SCC_COLLATERAL_NOT_PERFECTED);
					throw exp;
				}
				*/
				CheckListSearchResult checkList = (CheckListSearchResult) aCheckListMap.get(new Long(col
						.getCollateralID()));
				if (checkList != null) {
					// DefaultLogger.debug(this, "Checklist status: " +
					// checkList.getCheckListStatus());
                    /*
					if (ICMSConstant.STATE_CHECKLIST_WAIVED.equals(checkList.getCheckListStatus())) {
						overAllCheckListStatus = checkList.getCheckListStatus();
					}
					else if (ICMSConstant.STATE_CHECKLIST_COMPLETED.equals(checkList.getCheckListStatus())
							|| ICMSConstant.STATE_CHECKLIST_CERT_ALLOWED.equals(checkList.getCheckListStatus())) {
						if (!ICMSConstant.STATE_CHECKLIST_WAIVED.equals(overAllCheckListStatus)) {
							overAllCheckListStatus = checkList.getCheckListStatus();
						}
					}
					*/
                    if (ICMSConstant.STATE_CHECKLIST_COMPLETED.equals(checkList.getCheckListStatus())) {
                        overAllCheckListStatus = checkList.getCheckListStatus();
                    }
					else {
						SCCertificateException exp = new SCCertificateException("Checklist for collateral "
								+ col.getCollateralID() + " not perfected !!!");
						exp.setErrorCode(ICMSErrorCodes.SCC_CHECKLIST_NOT_PERFECTED);
						throw exp;
					}
				}
				else {
                    /* for go this part as well since is not applicable to ABCLIMS
					// To handle new security during renewal
					if (isBCARenewal) {
						return ICMSConstant.STATE_CHECKLIST_IN_PROGRESS;
					}
					SCCertificateException exp = new SCCertificateException("No Checklist for collateral "
							+ col.getCollateralID() + " !!!");
					exp.setErrorCode(ICMSErrorCodes.SCC_NO_CHECKLIST_FOUND);
					throw exp;
					*/
				}
			}
			if (overAllCheckListStatus == null) {
				throw new SCCertificateException("There is no Collateral Allocation!!!");
			}
			return overAllCheckListStatus;
		}
		catch (CollateralException ex) {
			throw new SCCertificateException(ex);
		}
	}

	// private HashMap getCheckListMap(ICollateralAllocation[]
	// anICollateralAllocationList, HashMap aCheckListMap)

    // R1.5
    // CR35
    private HashMap getCheckListMap(ILimit limit, HashMap aCheckListMap) {
        HashMap hmap = new HashMap();
        ICollateralAllocation[] anICollateralAllocationList = limit.getNonDeletedCollateralAllocations();
        for (int ii = 0; ii < anICollateralAllocationList.length; ii++) {
            ICollateral col = anICollateralAllocationList[ii].getCollateral();
            Long collateralID = new Long(col.getCollateralID());
            CheckListSearchResult checkList = (CheckListSearchResult) aCheckListMap.get(collateralID);
            String checkListStatus = checkList == null ? ICMSConstant.STATE_CHECKLIST_IN_PROGRESS : checkList.getCheckListStatus();
            hmap.put(collateralID, checkListStatus);
        }
        return hmap;
    }

	private HashMap getCheckListMap(ICollateralAllocation[] collateralAllocList, HashMap aCheckListMap) {

		HashMap hmap = new HashMap();
		if ((collateralAllocList != null) && (collateralAllocList.length > 0)) {
			for (int k = 0; k < collateralAllocList.length; k++) {
				ICollateral col = collateralAllocList[k].getCollateral();
				Long collateralID = new Long(col.getCollateralID());
				CheckListSearchResult checkList = (CheckListSearchResult) aCheckListMap.get(collateralID);
				if (checkList != null) {
					hmap.put(collateralID, checkList.getCheckListStatus());
				}
				else {
					hmap.put(collateralID, ICMSConstant.STATE_CHECKLIST_IN_PROGRESS);
				}
			}
		}

		return hmap;
	}

	/**
	 * Formulate the SCC items based on the limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return ISCCertificateItem[] - the list of scc items
	 * @throws SCCertificateException
	 */
	private ISCCertificateItem[] getSCCertificateItemList(ILimitProfile anILimitProfile) throws SCCertificateException {
		ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
		if ((limitList == null) || (limitList.length == 0)) {
			SCCertificateException exp = new SCCertificateException("There is no limit in this limit profile");
			exp.setErrorCode(ICMSErrorCodes.SCC_NOT_REQUIRED);
			throw exp;
		}
		try {
			ArrayList itemList = new ArrayList();
			Date approvalDate = anILimitProfile.getApprovalDate();
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			HashMap checkListMap = proxy.getCollateralCheckListStatus(anILimitProfile.getLimitProfileID());

            HashMap allowCheckListItemStatus = new HashMap();
            allowCheckListItemStatus.put(ICMSConstant.STATE_ITEM_COMPLETED, null);
            allowCheckListItemStatus.put(ICMSConstant.STATE_ITEM_WAIVED, null);
            allowCheckListItemStatus.put(ICMSConstant.STATE_ITEM_RENEWED, null);

			for (int ii = 0; ii < limitList.length; ii++) {
				ICollateralAllocation[] nonDeletedList = limitList[ii].getNonDeletedCollateralAllocations();
				if ((nonDeletedList != null) && (nonDeletedList.length > 0)) // secured
																				// limits
				{
					getCheckListStatus(nonDeletedList, checkListMap, false);
				}

                //for co-borrower...i can for go now cause is not using i n ABCLIMS project
				ICoBorrowerLimit[] cbLimits = limitList[ii].getCoBorrowerLimits();
				for (int j = 0; j < cbLimits.length; j++) { // for R1.5 CR35 -
															// Secured CB Limits
					ICollateralAllocation[] cbNonDelList = cbLimits[j].getNonDeletedCollateralAllocations();
					if ((cbNonDelList != null) && (cbNonDelList.length > 0)) {
						getCheckListStatus(cbNonDelList, checkListMap, false);
					}
				}

                //TODO : Before formulate the result...need to check checklist item for COMPLETED, WAIVED, RENEWED
                //get all the checklist item (documents) by using the limit profile id and filter by Security 'S'. Return a hashmap key : checklistID value : checklist item
                HashMap checkListItem = proxy.getCheckListItemListbyCategory(limitList[ii].getLimitProfileID(), "S");

                boolean allow = true;
                for (Iterator itr = checkListItem.values().iterator(); itr.hasNext();) {
                    OBCheckListItem item = (OBCheckListItem) itr.next();
                    if (!allowCheckListItemStatus.containsKey(item.getItemStatus())) {
                        allow = false;
                        break;
                    }
                }

                if (allow) {
                    formulateSCCItemList(itemList, limitList[ii], approvalDate);
				    formulateSCCCoBorrowerItemList(itemList, limitList[ii], approvalDate);   
                }
				// ISCCertificateItem item = new OBSCCertificateItem();
				// item.setLimit(limitList[ii]);
				// itemList.add(item);
				// } else { //unsecured limits
				// formulateSCCItemList(itemList, limitList[ii], approvalDate);
				// formulateSCCCoBorrowerItemList(itemList, limitList[ii],
				// approvalDate);
				// }
			}

			// DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> Item List Size: " +
			// itemList.size());
			if (itemList.size() == 0) {
				SCCertificateException exp = new SCCertificateException("There is no items in the SCC !!!");
				exp.setErrorCode(ICMSErrorCodes.SCC_NOT_REQUIRED);
				throw exp;
			}
			return (ISCCertificateItem[]) itemList.toArray(new ISCCertificateItem[0]);
		}
		catch (CheckListException ex) {
			rollback();
			throw new SCCertificateException("Exception in getSCCertificateItemList", ex);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new SCCertificateException("Exception in getSCCertificateItemList", ex);
		}
	}

	/**
	 * To formulate the scc items for both the outer and inner limit
	 * @param anItemList of ArrayList type
	 * @param anILimit of ILimit type
	 * @param anApprovalDate of Date type
	 * @throws SCCertificateException on errors
	 */
	private void formulateSCCItemList(ArrayList anItemList, ILimit anILimit, Date anApprovalDate)
			throws SCCertificateException {
		ISCCertificateItem item = null;
		if (isInnerLimit(anILimit)) {
			item = new OBSCCertificateItem(ICMSConstant.SCC_INNER_LIMIT, anILimit.getLimitID());
		}
		else {
			item = new OBSCCertificateItem(ICMSConstant.SCC_OUTER_LIMIT, anILimit.getLimitID());
		}
		item.setLimitRef(anILimit.getLimitRef());
		item.setOuterLimitRef(anILimit.getOuterLimitRef());
		item.setOuterLimitID(anILimit.getOuterLimitID());
		item.setOuterLimitProfileID(anILimit.getOuterLimitProfileID());
		item.setIsInnerOuterSameBCA(anILimit.getIsInnerOuterSameBCA());
		item.setIsLimitExistingInd(anILimit.getExistingInd());
        ICollateralAllocation[] nonDeletedList = anILimit.getNonDeletedCollateralAllocations();
		item.setCollateralAllocations(nonDeletedList);
		item.setLimitBookingLocation(anILimit.getBookingLocation());
		item.setProductDesc(anILimit.getProductDesc());
		item.setApprovedLimitAmount(anILimit.getApprovedLimitAmount());
		item.setApprovalDate(anApprovalDate);
		item.setMaturityDate(anILimit.getLimitExpiryDate());
		item.setLimitExpiryDate(anILimit.getLimitExpiryDate());
		if (isCleanType(anILimit)) // R1.5 CR146
		{
			item.setIsCleanTypeInd(true);
		}
		else {
			item.setIsCleanTypeInd(false);
			item.setActivatedAmount(anILimit.getActivatedLimitAmount());
		}
		// DefaultLogger.debug(this, "Is Clean Type: " + item.isCleanType());
		anItemList.add(item);
	}

	/**
	 * To formulate the scc item for co-borrower limit
	 * @param anItemList of ArrayList type
	 * @param anILimit of ILimit type
	 * @param anApprovalDate of Date type
	 * @throws SCCertificateException on errors
	 */
	private void formulateSCCCoBorrowerItemList(ArrayList anItemList, ILimit anILimit, Date anApprovalDate)
			throws SCCertificateException {
		ICoBorrowerLimit[] coBorrowerLimitList = anILimit.getNonDeletedCoBorrowerLimits();
		if ((coBorrowerLimitList == null) || (coBorrowerLimitList.length == 0)) {
			return;
		}
		try {
			ICustomerProxy customerproxy = CustomerProxyFactory.getProxy();
			for (int ii = 0; ii < coBorrowerLimitList.length; ii++) {
				ISCCertificateItem item = new OBSCCertificateItem(ICMSConstant.SCC_CB_INNER_LIMIT,
						coBorrowerLimitList[ii].getLimitID());
				ICMSCustomer customer = customerproxy.getCustomer(coBorrowerLimitList[ii].getCustomerID());
				item.setCoBorrowerLegalID(customer.getCMSLegalEntity().getLEReference());
				item.setCoBorrowerName(customer.getCustomerName());
				item.setLimitRef(coBorrowerLimitList[ii].getLimitRef());
				item.setOuterLimitRef(coBorrowerLimitList[ii].getOuterLimitRef());
				// item.setIsLimitExistingInd(coBorrowerLimitList[ii].
				// getExistingInd());
				item.setLimitBookingLocation(coBorrowerLimitList[ii].getBookingLocation());
				item.setProductDesc(anILimit.getProductDesc());
				item.setApprovedLimitAmount(coBorrowerLimitList[ii].getApprovedLimitAmount());
				item.setApprovalDate(anApprovalDate);
				if (isCleanType(coBorrowerLimitList[ii])) // if
															// (isCleanType(anILimit
															// )) //R1.5 CR146,
															// CR35
				{
					item.setIsCleanTypeInd(true);
				}
				else {
					item.setIsCleanTypeInd(false);
					item.setActivatedAmount(coBorrowerLimitList[ii].getActivatedLimitAmount());
				}
				DefaultLogger.debug(this, ">>>>>>>> PSCC: COBORROWER!!! Is Clean Type: " + item.isCleanType()
						+ " Limit ID " + item.getLimitRef());
				anItemList.add(item);
			}
		}
		catch (CustomerException ex) {
			throw new SCCertificateException("Caught CustomerException in formulateSCCCoBorrowerItemList", ex);
		}
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
	 * Formulate the Partial SCC items based on the limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return IPartialSCCertificateItem[] - the list of partial scc items
	 * @throws SCCertificateException
	 */
	private IPartialSCCertificateItem[] getPartialSCCertificateItemList(ILimitProfile anILimitProfile)
			throws SCCertificateException {
		ILimit[] limitList = anILimitProfile.getNonDeletedLimits();

		if ((limitList == null) || (limitList.length == 0)) {
			SCCertificateException exp = new SCCertificateException("There is no limit in this limit profile");
			exp.setErrorCode(ICMSErrorCodes.PSCC_NOT_REQUIRED);
			throw exp;
		}

		try {
			ArrayList itemList = new ArrayList();
			Date approvalDate = anILimitProfile.getApprovalDate();
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();

            //Key : CollateralID, Value : CheckListOB
			HashMap checkListMap = proxy.getCollateralCheckListStatus(anILimitProfile.getLimitProfileID());

            boolean isCCCheckListAllCompleted = true;
            HashMap ccCheckListMap = proxy.getCCCheckListStatus(anILimitProfile.getLimitProfileID(), false);
            for (Iterator itr = ccCheckListMap.values().iterator(); itr.hasNext();) {
                CheckListSearchResult cc = (CheckListSearchResult) itr.next();

                if (!cc.getCheckListStatus().equals(ICMSConstant.STATE_CHECKLIST_COMPLETED)) {
                    isCCCheckListAllCompleted = false;
                    break;
                }
            }

			// R1.5 CR146
			boolean canGenerateSCC = verifyIfSCCCanBeGenerated(limitList, checkListMap);
			//if (canGenerateSCC) {
            if (canGenerateSCC && isCCCheckListAllCompleted) {
				SCCertificateException exp = new SCCertificateException(
						"PSCC Cannot be Generated when SCC Can Be Generated");
				exp.setErrorCode(ICMSErrorCodes.PSCC_CAN_GENERATE_SCC);
				throw exp;
			}

            //If SCC is not allow, for sure PSCC is allow from now on. No checking on the checklist level is needed.

            //so now we need to check on the checklist document (item) level to determine whether PSCC is allow.
            //Condition : (COMPLETED | WAIVED) & DEFERRED

            HashMap allowCheckListItemStatus = new HashMap();
            allowCheckListItemStatus.put(ICMSConstant.STATE_ITEM_COMPLETED, null);
            allowCheckListItemStatus.put(ICMSConstant.STATE_ITEM_WAIVED, null);

            boolean allowPSCC = true;
            //get all the checklist item (documents) by using the limit profile id and filter by Security 'S'. Return a hashmap key : checklistID value : checklist item
            HashMap checkListItem = proxy.getCheckListItemListbyCategory(anILimitProfile.getLimitProfileID(), "S");

            Iterator itr = checkListItem.values().iterator();
            for (; itr.hasNext();) {
                OBCheckListItem OB = (OBCheckListItem) itr.next();

                if (!OB.getItemStatus().equals(ICMSConstant.STATE_ITEM_DEFERRED)) {
                    //if this is not DEFERRED case, is this COMPLETED | WAIVED case?

                    if (!allowCheckListItemStatus.containsKey(OB.getItemStatus())) {
                        //found a checklist item status not belong to either COMPLETED | WAIVED
                        allowPSCC = false;
                        break;
                    }
                }
            }

            if (!allowPSCC) {
                SCCertificateException ex = new SCCertificateException("Error Generating PSCC as because it is not meet the condition [(COMPLETED | WAIVED) & DEFERRED]");
                ex.setErrorCode(ICMSErrorCodes.PSCC_CONDITION_NOT_MEET);
                throw ex;
            }

            for (int i = 0; i < limitList.length; i++) {
                formulatePSCCItemList(itemList, limitList[i], approvalDate);
                formulatePSCCCoBorrowerItemList(itemList, limitList[i], approvalDate, checkListMap);
            }

            /*
			for (int ii = 0; ii < limitList.length; ii++) {
				ICollateralAllocation[] nonDeletedList = limitList[ii].getNonDeletedCollateralAllocations();
                //got collateral
				if ((nonDeletedList != null) && (nonDeletedList.length > 0)) {
					// hasCollateral = true;
					// HashMap hmap = getCheckListMap(nonDeletedList,
					// checkListMap);
                    //TODO : for unfound object, it will set to IN_PROGRESS
					HashMap hmap = getCheckListMap(limitList[ii], checkListMap);
					if (isPartialSCCAllowed(hmap)) {
                        //Chee Hong TODO : Need to verify all the documents in the CheckList is either 'Completed' | 'Waived' | 'Deferred'

                        //get all the checklist item (documents) by using the limit profile id and filter by Security 'S'. Return a hashmap key : checklistID value : checklist item
                        HashMap checkListItem = proxy.getCheckListItemListbyCategory(limitList[ii].getLimitProfileID(), "S");

                        boolean allow = true;
                        for (Iterator itr = checkListItem.values().iterator(); itr.hasNext();) {
                            OBCheckListItem item = (OBCheckListItem) itr.next();
                            if (!allowCheckListItemStatus.containsKey(item.getItemStatus())) {
                                allow = false;
                                break;
                            }
                        }

						if (allow) formulatePSCCItemList(itemList, limitList[ii], approvalDate);
						// IPartialSCCertificateItem item = new
						// OBPartialSCCertificateItem();
						// item.setLimit(limitList[ii]);
						// itemList.add(item);
					}
					// even when main-borrower does not satisfy the conditions
					// for PSCC does not mean co-borrower will not satisfy the
					// conditions
					formulatePSCCCoBorrowerItemList(itemList, limitList[ii], approvalDate, checkListMap);

				}
                //got no collateral
				else { // unsecured limits //R1.5 CR146
                    //Chee Hong TODO : Since in the earlier verification, when thr are some limit with no collateral - Clean BCA will pass tru, thr for...
					formulatePSCCItemList(itemList, limitList[ii], approvalDate);
					formulatePSCCCoBorrowerItemList(itemList, limitList[ii], approvalDate);
				}
			}
			*/

			/*
			 * if (!hasCollateral) //Removed in R1.5 CR146 {
			 * SCCertificateException exp = new
			 * SCCertificateException("There is no limit with Collateral!!!");
			 * exp.setErrorCode(ICMSErrorCodes.PSCC_NOT_REQUIRED); throw exp; }
			 */

			if (itemList.size() == 0) {
				// SCCertificateException exp = new SCCertificateException(
				// "There is no limit with security checklist perfected");
				//exp.setErrorCode(ICMSErrorCodes.PSCC_CHECKLIST_NOT_PERFECTED);
				SCCertificateException exp = new SCCertificateException("There is no limit to be activated");
				exp.setErrorCode(ICMSErrorCodes.PSCC_NO_LIMITS);
				throw exp;
			}
			return (IPartialSCCertificateItem[]) itemList.toArray(new IPartialSCCertificateItem[0]);
		}
		catch (CheckListException ex) {
			rollback();
			throw new SCCertificateException("Exception in getPartialSCCertificateItemList", ex);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new SCCertificateException("Exception in getPartialSCCertificateItemList", ex);
		}
	}

	/**
	 * To formulate the pscc items for both the outer and inner limit
	 * @param anItemList of ArrayList type
	 * @param anILimit of ILimit type
	 * @param anApprovalDate of Date type
	 * @throws SCCertificateException on errors
	 */
	private void formulatePSCCItemList(ArrayList anItemList, ILimit anILimit, Date anApprovalDate)
			throws SCCertificateException {
		IPartialSCCertificateItem item = null;
		if (isInnerLimit(anILimit)) {
			item = new OBPartialSCCertificateItem(ICMSConstant.SCC_INNER_LIMIT, anILimit.getLimitID());
		}
		else {
			item = new OBPartialSCCertificateItem(ICMSConstant.SCC_OUTER_LIMIT, anILimit.getLimitID());
		}
		item.setLimitRef(anILimit.getLimitRef());
		item.setOuterLimitRef(anILimit.getOuterLimitRef());
		item.setOuterLimitID(anILimit.getOuterLimitID());
		item.setOuterLimitProfileID(anILimit.getOuterLimitProfileID());
		item.setIsInnerOuterSameBCA(anILimit.getIsInnerOuterSameBCA());
		item.setIsLimitExistingInd(anILimit.getExistingInd());
		ICollateralAllocation[] nonDeletedList = anILimit.getNonDeletedCollateralAllocations();
		item.setCollateralAllocations(nonDeletedList);
		item.setLimitBookingLocation(anILimit.getBookingLocation());
		item.setProductDesc(anILimit.getProductDesc());
		item.setApprovedLimitAmount(anILimit.getApprovedLimitAmount());
		item.setApprovalDate(anApprovalDate);
		item.setMaturityDate(anILimit.getLimitExpiryDate());
		item.setLimitExpiryDate(anILimit.getLimitExpiryDate());
		if (isCleanType(anILimit)) // R1.5 CR146
		{
			item.setIsCleanTypeInd(true);
		}
		else {
			item.setIsCleanTypeInd(false);
			item.setActivatedAmount(anILimit.getActivatedLimitAmount());
		}
		DefaultLogger.debug(this, "Is Clean Type: " + item.isCleanType());
		anItemList.add(item);
	}

	/**
	 * To formulate the pscc item for co-borrower limit (used when its unsecured
	 * limits)
	 * @param anItemList of ArrayList type
	 * @param anILimit of ILimit type
	 * @param anApprovalDate of Date type
	 * @throws SCCertificateException on errors
	 */
	private void formulatePSCCCoBorrowerItemList(ArrayList anItemList, ILimit anILimit, Date anApprovalDate)
			throws SCCertificateException // no longer in use since R1.5 CR35
	{
		ICoBorrowerLimit[] coBorrowerLimitList = anILimit.getNonDeletedCoBorrowerLimits();
		if ((coBorrowerLimitList == null) || (coBorrowerLimitList.length == 0)) {
			return;
		}
		try {
			ICustomerProxy customerproxy = CustomerProxyFactory.getProxy();
			for (int ii = 0; ii < coBorrowerLimitList.length; ii++) {
				IPartialSCCertificateItem item = new OBPartialSCCertificateItem(ICMSConstant.SCC_CB_INNER_LIMIT,
						coBorrowerLimitList[ii].getLimitID());
				ICMSCustomer customer = customerproxy.getCustomer(coBorrowerLimitList[ii].getCustomerID());
				item.setCoBorrowerLegalID(customer.getCMSLegalEntity().getLEReference());
				item.setCoBorrowerName(customer.getCustomerName());
				item.setLimitRef(coBorrowerLimitList[ii].getLimitRef());
				item.setOuterLimitRef(coBorrowerLimitList[ii].getOuterLimitRef());
				ICollateralAllocation[] nonDeletedList = anILimit.getNonDeletedCollateralAllocations();
				item.setCollateralAllocations(nonDeletedList);
				item.setIsLimitExistingInd(coBorrowerLimitList[ii].getExistingInd());
				item.setLimitBookingLocation(coBorrowerLimitList[ii].getBookingLocation());
				item.setProductDesc(anILimit.getProductDesc());
				item.setApprovedLimitAmount(coBorrowerLimitList[ii].getApprovedLimitAmount());
				item.setApprovalDate(anApprovalDate);
				if (isCleanType(coBorrowerLimitList[ii])) // R1.5 CR146
				{
					item.setIsCleanTypeInd(true);
				}
				else {
					item.setIsCleanTypeInd(false);
					item.setActivatedAmount(anILimit.getActivatedLimitAmount());
				}
				DefaultLogger.debug(this, "Is Clean Type: " + item.isCleanType());
				anItemList.add(item);
			}
		}
		catch (CustomerException ex) {
			throw new SCCertificateException("Caught CustomerException in formulatePSCCCoBorrowerItemList", ex);
		}
	}

	// newly added helper method for R1.5 CR35, to help loop through the
	// coborrower limits and
	// verify if they satisfy the conditions for PSCC to be included.
	// called only by getPartialSCCertificateItemList() method
	private void formulatePSCCCoBorrowerItemList(ArrayList itemList, ILimit limit, Date approvalDate,
			HashMap checkListMap) throws SCCertificateException {
		ICoBorrowerLimit[] cbLimits = limit.getCoBorrowerLimits();
		if ((cbLimits != null) && (cbLimits.length > 0)) {
			for (int i = 0; i < cbLimits.length; i++) {
				HashMap hmap = getCheckListMap(cbLimits[i].getNonDeletedCollateralAllocations(), checkListMap);
				if ((hmap.size() == 0) || isPartialSCCAllowed(hmap)) {
					formulatePSCCCoBorrowerItemList(itemList, cbLimits[i], approvalDate);
				}
			}
		}
	}

	/**
	 * To formulate the pscc item for co-borrower limit (used for secured
	 * limits)
	 * @param anItemList of ArrayList type
	 * @param cbLimit of ICoBorrowerLimit type
	 * @param anApprovalDate of Date type
	 * @throws SCCertificateException on errors
	 */
	private void formulatePSCCCoBorrowerItemList(ArrayList anItemList, ICoBorrowerLimit cbLimit, Date anApprovalDate)
			throws SCCertificateException // no longer in use since R1.5 CR35
	{
		try {
			ICustomerProxy customerproxy = CustomerProxyFactory.getProxy();
			IPartialSCCertificateItem item = new OBPartialSCCertificateItem(ICMSConstant.SCC_CB_INNER_LIMIT, cbLimit
					.getLimitID());
			ICMSCustomer customer = customerproxy.getCustomer(cbLimit.getCustomerID());
			item.setCoBorrowerLegalID(customer.getCMSLegalEntity().getLEReference());
			item.setCoBorrowerName(customer.getCustomerName());
			item.setLimitRef(cbLimit.getLimitRef());
			item.setOuterLimitRef(cbLimit.getOuterLimitRef());
			ICollateralAllocation[] nonDeletedList = cbLimit.getNonDeletedCollateralAllocations();
			item.setCollateralAllocations(nonDeletedList);
			item.setIsLimitExistingInd(cbLimit.getExistingInd());
			item.setLimitBookingLocation(cbLimit.getBookingLocation());
			item.setProductDesc(cbLimit.getProductDesc());
			item.setApprovedLimitAmount(cbLimit.getApprovedLimitAmount());
			item.setApprovalDate(anApprovalDate);
			if (isCleanType(cbLimit)) // R1.5 CR146
			{
				item.setIsCleanTypeInd(true);
			}
			else {
				item.setIsCleanTypeInd(false);
				item.setActivatedAmount(cbLimit.getActivatedLimitAmount());
			}
			DefaultLogger.debug(this, "Is Clean Type: " + item.isCleanType());
			anItemList.add(item);

		}
		catch (CustomerException ex) {
			throw new SCCertificateException("Caught CustomerException in formulatePSCCCoBorrowerItemList", ex);
		}
	}

	private boolean verifyIfSCCCanBeGenerated(ILimit[] limitList, HashMap checkListMap) throws SCCertificateException {
		try {
			boolean checklistPerfected = true;
			boolean canGenerateSCC = true;

			for (int i = 0; i < limitList.length; i++) {
				ICollateralAllocation[] anICollateralAllocationList = limitList[i].getNonDeletedCollateralAllocations();
				if ((anICollateralAllocationList != null) && (anICollateralAllocationList.length > 0)) { // for
																											// secured
																											// limits
																											// (
																											// unsecured
																											// limits
																											// is
																											// already
																											// verified
																											// by
																											// CCC
																											// check
																											// )
					for (int ii = 0; ii < anICollateralAllocationList.length; ii++) {
						ICollateral col = anICollateralAllocationList[ii].getCollateral();
						col = CollateralProxyFactory.getProxy().getCollateral(col.getCollateralID(), false);
						// DefaultLogger.debug(this, "CollateralID: " +
						// col.getCollateralID());
						// DefaultLogger.debug(this, "CollateralPerfected: " +
						// col.getIsPerfected());

						CheckListSearchResult checkList = (CheckListSearchResult) checkListMap.get(new Long(col
								.getCollateralID()));
						checklistPerfected = verifyIsCheckListPerfected(checkList);

						//canGenerateSCC = canGenerateSCC && col.getIsPerfected() && checklistPerfected;
                        canGenerateSCC = canGenerateSCC && checklistPerfected;
						if (!canGenerateSCC) {
							return false; // return canGenerateSCC
						}
					}
				}

				ICoBorrowerLimit[] cbLimits = limitList[i].getCoBorrowerLimits();
				if ((cbLimits != null) && (cbLimits.length > 0)) {
					for (int j = 0; j < cbLimits.length; j++) {
						ICollateralAllocation[] collateralAllocationList = cbLimits[j]
								.getNonDeletedCollateralAllocations();
						if ((collateralAllocationList != null) && (collateralAllocationList.length > 0)) {
							for (int k = 0; k < collateralAllocationList.length; k++) {
								ICollateral col = collateralAllocationList[k].getCollateral();
								col = CollateralProxyFactory.getProxy().getCollateral(col.getCollateralID(), false);

								CheckListSearchResult checkList = (CheckListSearchResult) checkListMap.get(new Long(col
										.getCollateralID()));
								checklistPerfected = verifyIsCheckListPerfected(checkList);

								//canGenerateSCC = canGenerateSCC && col.getIsPerfected() && checklistPerfected;
                                canGenerateSCC = canGenerateSCC && checklistPerfected;
								if (!canGenerateSCC) {
									return false; // return canGenerateSCC
								}
							}
						}
					}
				}
			}

			return canGenerateSCC; // return true
		}
		catch (CollateralException ex) {
			throw new SCCertificateException(ex);
		}

		/**/
	}

	private boolean verifyIsCheckListPerfected(CheckListSearchResult checkList) {
		if (checkList == null) {
			return false;
		}

		// DefaultLogger.debug(this, "Checklist status: " +
		// checkList.getCheckListStatus());
		if ((ICMSConstant.STATE_CHECKLIST_WAIVED.equals(checkList.getCheckListStatus()))
				|| (ICMSConstant.STATE_CHECKLIST_COMPLETED.equals(checkList.getCheckListStatus()))
				|| (ICMSConstant.STATE_CHECKLIST_CERT_ALLOWED.equals(checkList.getCheckListStatus()))) {
			return true;
		}
		else {
			return false;
		}

	}

	/**
	 * Get the SCC trx value based on the trx ID
	 * @param aTrxID of String type
	 * @return ISCCertificateTrxValue - the scc trx value
	 * @throws SCCertificateException
	 */
	private ISCCertificateTrxValue getSCCertificateTrxValue(String aTrxID) throws SCCertificateException {
		if (aTrxID == null) {
			throw new SCCertificateException("The TrxID is null !!!");
		}
		ISCCertificateTrxValue trxValue = new OBSCCertificateTrxValue();
		trxValue.setTransactionID(aTrxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_SCC);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_SCC);
		return operate(trxValue, param);
	}

	/**
	 * Get the Partial SCC trx value based on the trx ID
	 * @param aTrxID of String type
	 * @return IPartialSCCertificateTrxValue - the partial scc trx value
	 * @throws SCCertificateException
	 */
	private IPartialSCCertificateTrxValue getPartialSCCertificateTrxValue(String aTrxID) throws SCCertificateException {
		if (aTrxID == null) {
			throw new SCCertificateException("The TrxID is null !!!");
		}
		IPartialSCCertificateTrxValue trxValue = new OBPartialSCCertificateTrxValue();
		trxValue.setTransactionID(aTrxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_PSCC);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_PSCC);
		return operate(trxValue, param);
	}

	/**
	 * Get the SCC trx value based on the scc ID
	 * @param aSCCertID of long type
	 * @return ISCCertificateTrxValue - the scc trx value
	 * @throws SCCertificateException
	 */
	private ISCCertificateTrxValue getSCCertificateTrxValue(long aSCCertID) throws SCCertificateException {
		if (aSCCertID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new SCCertificateException("The SCCertID is invalid !!!");
		}
		ISCCertificateTrxValue trxValue = new OBSCCertificateTrxValue();
		trxValue.setReferenceID(String.valueOf(aSCCertID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_SCC);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_SCC_ID);
		return operate(trxValue, param);
	}

	/**
	 * Get the Partial SCC trx value based on the scc ID
	 * @param aSCCertID of long type
	 * @return IPartialSCCertificateTrxValue - the partial scc trx value
	 * @throws SCCertificateException
	 */
	private IPartialSCCertificateTrxValue getPartialSCCertificateTrxValue(long aSCCertID) throws SCCertificateException {
		if (aSCCertID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new SCCertificateException("The SCCertID is invalid !!!");
		}
		IPartialSCCertificateTrxValue trxValue = new OBPartialSCCertificateTrxValue();
		trxValue.setReferenceID(String.valueOf(aSCCertID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_PSCC);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_PSCC_ID);
		return operate(trxValue, param);
	}

	/**
	 * Formulate the sc certificate Trx Object
	 * @param anITrxContext of ITrxContext type
	 * @param anISCCertificate of ISCCertificate type
	 * @return ISCCertificateTrxValue - the sc certificate trx interface
	 *         formulated
	 */
	private ISCCertificateTrxValue formulateTrxValue(ITrxContext anITrxContext, ISCCertificate anISCCertificate) {
		return formulateTrxValue(anITrxContext, null, anISCCertificate);
	}

	/**
	 * Formulate the partial sc certificate Trx Object
	 * @param anITrxContext of ITrxContext type
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @return IPartialSCCertificateTrxValue - the partial sc certificate trx
	 *         interface formulated
	 */
	private IPartialSCCertificateTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IPartialSCCertificate anIPartialSCCertificate) {
		return formulateTrxValue(anITrxContext, null, anIPartialSCCertificate);
	}

	/**
	 * Formulate the sc certificate Trx Object
	 * @param anITrxContext of ITrxContext type
	 * @param anICMSTrxValue of ICMSTrxValue type
	 * @param anISCCertificate of ISCCertificate type
	 * @return ISCCertificateTrxValue - the sc certificate trx interface
	 *         formulated
	 */
	private ISCCertificateTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			ISCCertificate anISCCertificate) {
		ISCCertificateTrxValue scCertificateTrxValue = null;
		if (anICMSTrxValue != null) {
			scCertificateTrxValue = new OBSCCertificateTrxValue(anICMSTrxValue);
		}
		else {
			scCertificateTrxValue = new OBSCCertificateTrxValue();
		}
		scCertificateTrxValue = formulateTrxValue(anITrxContext, (ISCCertificateTrxValue) scCertificateTrxValue);
		scCertificateTrxValue.setStagingSCCertificate(anISCCertificate);
		return scCertificateTrxValue;
	}

	/**
	 * Formulate the partial sc certificate Trx Object
	 * @param anITrxContext of ITrxContext type
	 * @param anICMSTrxValue of ICMSTrxValue type
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @return IPartialSCCertificateTrxValue - the partial sc certificate trx
	 *         interface formulated
	 */
	private IPartialSCCertificateTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IPartialSCCertificate anIPartialSCCertificate) {
		IPartialSCCertificateTrxValue pscCertificateTrxValue = null;
		if (anICMSTrxValue != null) {
			pscCertificateTrxValue = new OBPartialSCCertificateTrxValue(anICMSTrxValue);
		}
		else {
			pscCertificateTrxValue = new OBPartialSCCertificateTrxValue();
		}
		pscCertificateTrxValue = formulateTrxValue(anITrxContext,
				(IPartialSCCertificateTrxValue) pscCertificateTrxValue);
		pscCertificateTrxValue.setStagingPartialSCCertificate(anIPartialSCCertificate);
		return pscCertificateTrxValue;
	}

	/**
	 * Formulate the sc certificate trx object
	 * @param anITrxContext - ITrxContext
	 * @param anISCCertificateTrxValue - ISCCertificateTrxValue
	 * @return ISCCertificateTrxValue - the sc certificate trx interface
	 *         formulated
	 */
	private ISCCertificateTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ISCCertificateTrxValue anISCCertificateTrxValue) {
		if (anITrxContext != null) {
			anISCCertificateTrxValue.setTrxContext(anITrxContext);
		}
		anISCCertificateTrxValue.setTransactionType(ICMSConstant.INSTANCE_SCC);
		return anISCCertificateTrxValue;
	}

	/**
	 * Formulate the partial sc certificate trx object
	 * @param anITrxContext - ITrxContext
	 * @param anIPartialSCCertificateTrxValue - IPartialSCCertificateTrxValue
	 * @return IPartialSCCertificateTrxValue - the partial sc certificate trx
	 *         interface formulated
	 */
	private IPartialSCCertificateTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) {
		if (anITrxContext != null) {
			anIPartialSCCertificateTrxValue.setTrxContext(anITrxContext);
		}
		anIPartialSCCertificateTrxValue.setTransactionType(ICMSConstant.INSTANCE_PSCC);
		return anIPartialSCCertificateTrxValue;
	}

	private ISCCertificateTrxValue operate(ISCCertificateTrxValue anISCCertificateTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws SCCertificateException {
		ICMSTrxResult result = operateForResult(anISCCertificateTrxValue, anOBCMSTrxParameter);
		return (ISCCertificateTrxValue) result.getTrxValue();
	}

	private IPartialSCCertificateTrxValue operate(IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws SCCertificateException {
		ICMSTrxResult result = operateForResult(anIPartialSCCertificateTrxValue, anOBCMSTrxParameter);
		return (IPartialSCCertificateTrxValue) result.getTrxValue();
	}

	private ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws SCCertificateException {
		try {
			ITrxController controller = (new SCCertificateTrxControllerFactory()).getController(anICMSTrxValue,
					anOBCMSTrxParameter);
			if (controller == null) {
				throw new SCCertificateException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			rollback();
			throw new SCCertificateException(e);
		}
		catch (Exception ex) {
			rollback();
			throw new SCCertificateException(ex.toString());
		}
	}

	protected abstract void rollback();

	protected abstract ISCCertificate getSCCertificateWithoutLimitInfo(long aLimitProfileID)
			throws SCCertificateException;

	protected abstract IPartialSCCertificate getPartialSCCertificateWithoutLimitInfo(long aLimitProfileID)
			throws SCCertificateException;

	protected abstract long convertAmount(Amount anAmount, CurrencyCode aCurrencyCode) throws SCCertificateException;

	protected abstract int getNoOfSCCertificate(SCCertificateSearchCriteria aCriteria) throws SCCertificateException;

	protected abstract int getNoOfPartialSCCertificate(SCCertificateSearchCriteria aCriteria)
			throws SCCertificateException;

	protected abstract String getSCCTrxIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException,
			SCCertificateException;

	protected abstract String getPSCCTrxIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException,
			SCCertificateException;
}
