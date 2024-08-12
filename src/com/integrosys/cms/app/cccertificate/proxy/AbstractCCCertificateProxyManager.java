/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/proxy/AbstractCCCertificateProxyManager.java,v 1.81 2006/09/21 09:45:01 jychong Exp $
 */
package com.integrosys.cms.app.cccertificate.proxy;

//java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateDAO;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateException;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateSearchCriteria;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateSearchResult;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateSummary;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificateCustomerDetail;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificateItem;
import com.integrosys.cms.app.cccertificate.bus.OBCCCertificate;
import com.integrosys.cms.app.cccertificate.bus.OBCCCertificateCustomerDetail;
import com.integrosys.cms.app.cccertificate.bus.OBCCCertificateItem;
import com.integrosys.cms.app.cccertificate.trx.CCCertificateTrxControllerFactory;
import com.integrosys.cms.app.cccertificate.trx.ICCCertificateTrxValue;
import com.integrosys.cms.app.cccertificate.trx.OBCCCertificateTrxValue;
import com.integrosys.cms.app.checklist.bus.CCCheckListSummary;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.bus.IPledgor;
import com.integrosys.cms.app.collateral.bus.IPledgorCreditGrade;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.IContext;
import com.integrosys.cms.app.common.bus.BaseCurrency;
import com.integrosys.cms.app.common.constant.ICMSConstant;
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
import com.integrosys.cms.app.sccertificate.proxy.ISCCertificateProxyManager;
import com.integrosys.cms.app.sccertificate.proxy.SCCertificateProxyManagerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;

/**
 * This abstract class will contains all the biz related logic that is
 * independent of any technology implementation such as EJB
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.81 $
 * @since $Date: 2006/09/21 09:45:01 $ Tag: $Name: $
 */
public abstract class AbstractCCCertificateProxyManager implements ICCCertificateProxyManager {
	/**
	 * Get the number of CCC required for the Limit Profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return int - the number of CCC required
	 * @throws CCCertificateException on errors
	 */
	public int getNoOfCCCRequired(ILimitProfile anILimitProfile) throws CCCertificateException {
		ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
		int limitLen = 0;
		if ((limitList == null) || ((limitLen = limitList.length) == 0)) {
			return 1;
		}

		try {
			ArrayList list = new ArrayList();
			ArrayList colIDList = new ArrayList();
			ICollateralProxy colProxy = CollateralProxyFactory.getProxy();

			list.add(anILimitProfile.getLEReference());

			for (int ii = 0; ii < limitLen; ii++) {
				ICoBorrowerLimit[] coBorrowerLimitList = limitList[ii].getNonDeletedCoBorrowerLimits();

				if (coBorrowerLimitList != null) {
					int coLen = coBorrowerLimitList.length;

					for (int jj = 0; jj < coLen; jj++) {
						String coborrowerLEID = coBorrowerLimitList[jj].getLEReference();
						if (!list.contains(coborrowerLEID)) {
							DefaultLogger.info(this, "co-borrower: " + coborrowerLEID);
							list.add(coborrowerLEID);
						}
					}
				}

				ICollateralAllocation[] cols = limitList[ii].getNonDeletedCollateralAllocations();
				if (cols != null) {
					int colLen = cols.length;
					DefaultLogger.info(this, "Number of Col Allocations: " + colLen);

					for (int jj = 0; jj < colLen; jj++) {
						Long colID = new Long(cols[jj].getCollateral().getCollateralID());
						if (colIDList.contains(colID)) {
							continue;
						}

						colIDList.add(colID);
						ICollateralPledgor[] pledgors = colProxy.getCollateralPledgors(colID.longValue());

						if (pledgors != null) {
							int pledgorLen = pledgors.length;
							DefaultLogger.info(this, "Number of Pledgors: " + pledgorLen);

							for (int kk = 0; kk < pledgorLen; kk++) {
								ICollateralPledgor pledgor = pledgors[kk];
								if ((pledgor.getSCIPledgorMapStatus() != null)
										&& pledgor.getSCIPledgorMapStatus().equals(ICMSConstant.HOST_STATUS_DELETE)) {
									continue;
								}
								String id = pledgor.getLegalID();
								if (id == null) {
									id = "-1" + String.valueOf(pledgor.getPledgorID()); // prototyping
																						// .
																						// to
																						// ensure
																						// pledgorID
																						// set
																						// of
																						// values
									// don't overlap with legalID
								}
								String idObj = String.valueOf(id);
								if (!list.contains(idObj)) {
									DefaultLogger.info(this, "Pledgor: " + id);
									list.add(idObj);
								}
							}
						}
					}
				}
			}
			return list.size();
		}
		catch (Exception ex) {
			throw new CCCertificateException(ex);
		}
	}

	/**
	 * Get the number of CCC generated for the Limit Profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return int - the number of CCC generated
	 * @throws CCCertificateException on errors
	 */
	public int getNoOfCCCGenerated(ILimitProfile anILimitProfile) throws CCCertificateException {
		if (anILimitProfile == null) {
			throw new CCCertificateException("The ILimitProfile is null !!!");
		}
		try {
			CCCertificateSearchResult[] result = getCCCertificateGenerated(anILimitProfile.getLimitProfileID());
			if ((result == null) || (result.length == 0)) {
				return 0;
			}
			return result.length;
		}
		catch (SearchDAOException ex) {
			throw new CCCertificateException("Exception in getNoOfCCCGenerated", ex);
		}
	}

	/**
	 * Check is all CCC are generated
	 * @param anILimitProfile of LimitProfile
	 * @return boolean - true if all the CCCs are generated and false otherwise
	 * @throws CCCertificateException on errors
	 */
	public boolean isAllCCCGenerated(ILimitProfile anILimitProfile) throws CCCertificateException {
		int noOfCCCRequired = getNoOfCCCRequired(anILimitProfile);
		int noOfCCCGenerated = getNoOfCCCGenerated(anILimitProfile);
		DefaultLogger.debug(this, "No. of CCC Required: " + noOfCCCRequired);
		DefaultLogger.debug(this, "No. of CCC Generated: " + noOfCCCGenerated);

		if (noOfCCCRequired == noOfCCCGenerated) {
			return true;
		}
		return false;
	}

	/**
	 * Check is CCC is generated for non borrower
	 * @param anICMSCustomer of ICMSCustomer
	 * @return boolean - true if all the CCCs are generated and false otherwise
	 * @throws CCCertificateException on errors
	 */
	public boolean isAllCCCGenerated(ICMSCustomer anICMSCustomer) throws CCCertificateException {
		ICCCertificate cert = getNonBorrowerCCC(anICMSCustomer.getCustomerID());
		if (cert != null) {
			return true;
		}
		return false;
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
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			anILimitProfile = limitProxy.getLimitProfile(anILimitProfile.getLimitProfileID());
			/*
			 * if (!isBFLIssued(anILimitProfile)) { CCCertificateException exp =
			 * new CCCertificateException("BFL not allowed!!!");
			 * exp.setErrorCode(ICMSErrorCodes.CCC_BFL_NOT_ISSUED); throw exp; }
			 */
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			CCCheckListSummary[] summaryList = proxy.getCCCheckListSummaryForCCC(anIContext, anILimitProfile);
			if ((summaryList == null) || (summaryList.length == 0)) {
				return null;
			}
			CCCertificateSummary[] certList = getCCCCertificateSummaryInfo(summaryList);

			return certList;
		}
		catch (LimitException ex) {
			throw new CCCertificateException(ex);
		}
        catch (CheckListTemplateException ex) {
            throw new CCCertificateException("Exception in getCCCertificateSummaryList", ex);
        }
		catch (CheckListException ex) {
			throw new CCCertificateException("Exception in getCCCertificateSummaryList", ex);
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
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			CCCheckListSummary[] summaryList = proxy.getCCCheckListSummaryListForNonBorrower(anIContext,
					ICMSConstant.LONG_INVALID_VALUE, anICMSCustomer.getCustomerID(), true);
			if ((summaryList == null) || (summaryList.length == 0)) {
				return null;
			}
			CCCertificateSummary[] certList = getCCCCertificateSummaryInfo(summaryList);

			return certList;
		}
		catch (CheckListTemplateException ex) {
			throw new CCCertificateException("Exception in getCCCertificateSummaryList", ex);
		}
        catch (CheckListException ex) {
            throw new CCCertificateException("Exception in getCCCertificateSummaryList", ex);
        }
	}

	private CCCertificateSummary[] getCCCCertificateSummaryInfo(CCCheckListSummary[] summaryList)
			throws CCCertificateException {
		ArrayList allowGenCCCList = new ArrayList();
		CCCertificateSummary[] certList = new CCCertificateSummary[summaryList.length];
		for (int ii = 0; ii < summaryList.length; ii++) {

			certList[ii] = new CCCertificateSummary();
			certList[ii].setCertificateSummary(summaryList[ii]);
			// only if ccc has generate before then have last ccc generation
			// date and trx id
			if (certList[ii].allowGenerateCCC() && (certList[ii].getCheckListID() != ICMSConstant.LONG_INVALID_VALUE)) {
				allowGenCCCList.add(String.valueOf(certList[ii].getCheckListID()));
			}
		}

		// there is at least one checklist allow generate ccc
		// get ccc generation date and transaction id
		try {
			if (allowGenCCCList.size() > 0) {
				HashMap cccInfoMap = (new CCCertificateDAO()).getCCCInfo(allowGenCCCList);
				for (int i = 0; i < certList.length; i++) {
					if (certList[i].getCheckListID() == ICMSConstant.LONG_INVALID_VALUE) {
						continue;
					}

					Object[] cccInfoList = (Object[]) cccInfoMap.get(String.valueOf(certList[i].getCheckListID()));
					if (cccInfoList != null) {
						certList[i].setLastCCCUpdateDate((Date) cccInfoList[0]);
						certList[i].setCccTrxID((String) cccInfoList[1]);
					}
				}
			}
		}
		catch (SearchDAOException e) {
			throw new CCCertificateException("Exception in getCCCCertificateSummaryInfo", e);
		}
		return certList;
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
	 * Check if there is any pending generate CCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param aCCCertificateSummary of CCCertificateSummary type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws CCCertificateException on errors
	 */
	public boolean hasPendingGenerateCCCTrx(ILimitProfile anILimitProfile, CCCertificateSummary aCCCertificateSummary)
			throws CCCertificateException {
		CCCertificateSearchCriteria criteria = new CCCertificateSearchCriteria();
		criteria.setLimitProfileID(anILimitProfile.getLimitProfileID());
		criteria.setCategory(aCCCertificateSummary.getCustCategory());
		criteria.setCheckListID(aCCCertificateSummary.getCheckListID());
		if (ICMSConstant.CHECKLIST_PLEDGER.equals(aCCCertificateSummary.getCustCategory())) {
			criteria.setPledgorID(aCCCertificateSummary.getSubProfileID());
		}
		else {
			criteria.setSubProfileID(aCCCertificateSummary.getSubProfileID());
		}

		String[] trxStatusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_PENDING_UPDATE,
				ICMSConstant.STATE_REJECTED };
		criteria.setTrxStatusList(trxStatusList);
		int count = getNoOfCCCertificate(criteria);
		if (count == 0) {
			return false;
		}
		return true;
	}

	/**
	 * To get the CC Certificate
	 * @param lp of ILimitProfile type
	 * @param lp of ICMSCustomer type
	 * @param summary of CCCheckListSummary type
	 * @param refreshLimit indicator to refresh limit/customer info
	 * @return HashMap - contain the cc customer info and the ccc trx value
	 * @throws CCCertificateException on errors
	 */
	public HashMap getCCCertificate(ILimitProfile lp, ICMSCustomer cust, CCCertificateSummary summary,
			boolean refreshLimit) throws CCCertificateException {
		if (refreshLimit) {
			return getCCCertificate(lp, cust, summary);
		}
		verifyCCCertificate(lp, cust, summary);
		String category = summary.getCustCategory();
		long ownerID = summary.getSubProfileID();
		HashMap map = new HashMap();
		ICCCertificateTrxValue trxValue = getCCCertificateTrxValue(lp, summary, category, ownerID, refreshLimit);
		map.put(ICMSConstant.CCC, trxValue);
		ICCCertificateCustomerDetail custDetails = null;
		if (trxValue.getCCCertificate() != null) {
			populateGeneralCustomerInfo(trxValue.getCCCertificate(), cust);
			custDetails = trxValue.getCCCertificate().getCustDetails();
		}
		else {
			populateGeneralCustomerInfo(trxValue.getStagingCCCertificate(), cust);
			custDetails = trxValue.getStagingCCCertificate().getCustDetails();
		}
		if (custDetails == null) {
			custDetails = getCCCustomerInfo(lp, cust, category, ownerID);
		}
		map.put(ICMSConstant.CCC_OWNER, custDetails);
		addCategorySpecificInfo(lp, map, category);
		return map;
	}

	private void verifyCCCertificate(ILimitProfile lp, ICMSCustomer cust, CCCertificateSummary summary)
			throws CCCertificateException {
		if (lp == null) {
			throw new CCCertificateException("The ILimitProfile is null !!!");
		}
		if (cust == null) {
			throw new CCCertificateException("The ICMSCustomer is null !!!");
		}
		if (summary == null) {
			throw new CCCertificateException("The CCCheckListSummary is null !!!");
		}
		if (!summary.allowGenerateCCC()) {
			throw new CCCertificateException("CCC is not allowed for status " + summary.getCheckListStatus());
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
		verifyCCCertificate(anILimitProfile, anICMSCustomer, aCCCertificateSummary);
		long limitProfileID = anILimitProfile.getLimitProfileID();
		String category = aCCCertificateSummary.getCustCategory();
		long ownerID = aCCCertificateSummary.getSubProfileID();
		HashMap map = new HashMap();
		ICCCertificateCustomerDetail custDetails = getCCCustomerInfo(anILimitProfile, anICMSCustomer, category, ownerID);
		map.put(ICMSConstant.CCC_OWNER, custDetails);
		ICCCertificateTrxValue trxValue = getCCCertificateTrxValue(anILimitProfile, aCCCertificateSummary, category,
				ownerID, true);
		if (trxValue.getCCCertificate() != null) {
			trxValue.getCCCertificate().setCustDetails(custDetails);
		}
		if (trxValue.getStagingCCCertificate() != null) {
			trxValue.getStagingCCCertificate().setCustDetails(custDetails);
		}
		map.put(ICMSConstant.CCC, trxValue);
		addCategorySpecificInfo(anILimitProfile, map, category);
		return map;
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
		if (refreshLimit) {
			return getCCCertificate(lp, cust, trxID);
		}
		HashMap map = new HashMap();
		ICCCertificateTrxValue trxValue = getCCCertificateTrxValue(trxID);
		ICCCertificateCustomerDetail custDetails = null;
		map.put(ICMSConstant.CCC, trxValue);
		if (trxValue.getCCCertificate() != null) {
			calcTotalLimitAmount(trxValue.getCCCertificate(), trxValue.getCCCertificate().getCCCertificateItemList());
			populateGeneralCustomerInfo(trxValue.getCCCertificate(), cust);
			populateGeneralLimitInfo(lp, trxValue.getCCCertificate().getCCCertificateItemList());
			custDetails = trxValue.getCCCertificate().getCustDetails();
			addCategorySpecificInfo(lp, map, trxValue.getCCCertificate().getCCCertCategory());
		}

		ICCCertificate staging = trxValue.getStagingCCCertificate();
		if (staging != null) {
			calcTotalLimitAmount(staging, staging.getCCCertificateItemList());
			populateGeneralCustomerInfo(staging, cust);
			populateGeneralLimitInfo(lp, staging.getCCCertificateItemList());
			if (trxValue.getCCCertificate() == null) {
				custDetails = staging.getCustDetails();
				addCategorySpecificInfo(lp, map, staging.getCCCertCategory());
			}
		}

		map.put(ICMSConstant.CCC_OWNER, custDetails);

		return map;
	}

	/**
	 * Helper method to populate generate customer info.
	 * 
	 * @param cert of type ISCCertificate
	 * @param cust of type ICMSCustomer
	 */
	private void populateGeneralCustomerInfo(ICCCertificate cert, ICMSCustomer cust) {
		ICCCertificateCustomerDetail custDetails = cert.getCustDetails();
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
	private void populateGeneralLimitInfo(ILimitProfile lp, ICCCertificateItem[] items) {
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
	 * To get the CCC customer info and the CCC trx
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @return HashMap - contain the cc customer info and the ccc trx value
	 * @throws CCCertificateException on errors
	 */
	public HashMap getCCCertificate(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, String aTrxID)
			throws CCCertificateException {
		// try
		// {
		HashMap map = new HashMap();
		ICCCertificateTrxValue trxValue = getCCCertificateTrxValue(aTrxID);
		ICCCertificate staging = trxValue.getStagingCCCertificate();

		// ICheckListProxyManager proxy =
		// CheckListProxyManagerFactory.getCheckListProxyManager();
		// ICheckList checkList =
		// proxy.getCheckListByID(staging.getCheckListID());
		if (trxValue.getStagingCCCertificate() != null) {
			populateLimitInfo(anILimitProfile, null, trxValue.getStagingCCCertificate());
		}
		if (trxValue.getCCCertificate() != null) {
			populateLimitInfo(anILimitProfile, null, trxValue.getCCCertificate());
		}
		map.put(ICMSConstant.CCC, trxValue);
		map.put(ICMSConstant.CCC_OWNER, getCCCustomerInfo(anILimitProfile, anICMSCustomer, staging.getCCCertCategory(),
				staging.getOwnerID()));
		addCategorySpecificInfo(anILimitProfile, map, staging.getCCCertCategory());
		return map;
		// }
		// catch(CheckListException ex)
		// {
		// throw new CCCertificateException("Exception in getCCCertificate",
		// ex);
		// }
	}

	/**
	 * This method will add in the additonal information required according to
	 * the category of CCC
	 * @param aHashMap of HashMap type
	 * @param aCategory of String type
	 * @throws CCCertificateException on errors
	 */
	private void addCategorySpecificInfo(ILimitProfile anILimitProfile, HashMap aHashMap, String aCategory)
			throws CCCertificateException {
		if (ICMSConstant.CHECKLIST_PLEDGER.equals(aCategory)) {
			addPledgorInfo(anILimitProfile, aHashMap);
		}
		else if (ICMSConstant.CHECKLIST_CO_BORROWER.equals(aCategory)) {
			addCoBorrowerInfo(aHashMap);
		}
	}

	private void addCoBorrowerInfo(HashMap aHashMap) throws CCCertificateException {
		ICCCertificateTrxValue ccCertTrxValue = (ICCCertificateTrxValue) aHashMap.get(ICMSConstant.CCC);
		if (ccCertTrxValue != null) {
			ICCCertificate ccCert = ccCertTrxValue.getStagingCCCertificate();
			if (ccCert != null) {
				long coBorrowerID = ccCert.getSubProfileID();
				aHashMap.put(ICMSConstant.CCC_COBORROWER_DETAIL, getCMSCustomer(coBorrowerID));
			}
		}
	}

	/**
	 * To add in the info specific to pledgor CCC into the hashmap
	 * @param anILimitProfile of ILimitProfile type
	 * @param aHashMap of HashMap type
	 * @throws CCCertificateException on errors
	 */
	private void addPledgorInfo(ILimitProfile anILimitProfile, HashMap aHashMap) throws CCCertificateException {
		ICCCertificateTrxValue ccCertTrxValue = (ICCCertificateTrxValue) aHashMap.get(ICMSConstant.CCC);
		if (ccCertTrxValue != null) {
			ICCCertificate ccCert = ccCertTrxValue.getStagingCCCertificate();
			if (ccCert != null) {
				long pledgorID = ccCert.getPledgorID();
				aHashMap.put(ICMSConstant.CCC_PLEDGOR_DETAIL, getPledgorInfo(pledgorID));
				aHashMap.put(ICMSConstant.CCC_PLEDGOR_COLLATERAL_LIST, getPledgorCollateralList(anILimitProfile,
						pledgorID));
			}
		}
	}

	/**
	 * To get the list of collateral that the pledgor has pledged
	 * @param anILimitProfile of ILimitProfile type
	 * @param aPledgorID of long type
	 * @return ICollateral[] - the list of collaterals that the pledgor had
	 *         pledged
	 * @throws CCCertificateException on errors
	 */
	private ICollateral[] getPledgorCollateralList(ILimitProfile anILimitProfile, long aPledgorID)
			throws CCCertificateException {
		ArrayList collateralIDList = new ArrayList();
		ArrayList collateralList = new ArrayList();
		try {

			ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
			if ((limitList != null) && (limitList.length > 0)) {
				ICollateralProxy colProxy = CollateralProxyFactory.getProxy();
				for (int ii = 0; ii < limitList.length; ii++) {
					ICollateralAllocation[] colAllocationList = limitList[ii].getNonDeletedCollateralAllocations();
					if ((colAllocationList != null) && (colAllocationList.length > 0)) {
						for (int jj = 0; jj < colAllocationList.length; jj++) {
							ICollateral col = colAllocationList[jj].getCollateral();
							col = colProxy.getCollateral(col.getCollateralID(), true);
							if (col != null) {
								if (!collateralIDList.contains(new Long(col.getCollateralID()))) {
									collateralIDList.add(new Long(col.getCollateralID()));
									ICollateralPledgor[] pledgorList = col.getPledgors();
									if ((pledgorList != null) && (pledgorList.length > 0)) {
										for (int kk = 0; kk < pledgorList.length; kk++) {
											ICollateralPledgor pledgor = pledgorList[kk];
											/*
											 * Fix for CMSSP-535(Deleted
											 * Securities are appearing in the
											 * screen while generating CCC)
											 * Modified on Nov 11 2005
											 */
											if ((pledgor.getPledgorID() == aPledgorID)
													&& ((pledgor.getSCIPledgorMapStatus() != null) && !pledgor
															.getSCIPledgorMapStatus().equals(
																	ICMSConstant.HOST_STATUS_DELETE))) {
												collateralList.add(col);
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			return (ICollateral[]) collateralList.toArray(new ICollateral[0]);
		}
		catch (CollateralException ex) {
			DefaultLogger.error(this, ex);
			throw new CCCertificateException("Caught CollateralException in getPledgorCollateralList", ex);
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
		if (anITrxContext == null) {
			throw new CCCertificateException("The anITrxContext is null!!!");
		}
		if (anICCCertificateTrxValue == null) {
			throw new CCCertificateException("The anICCCertificateTrxValue to be updated is null!!!");
		}
		if (anICCCertificate == null) {
			throw new CCCertificateException("The ICCCertificate to be updated is null !!!");
		}
		// anICCCertificate.setDateGenerated(new Date());
		anICCCertificateTrxValue = formulateTrxValue(anITrxContext, anICCCertificateTrxValue, anICCCertificate);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_GENERATE_CCC);
		return operate(anICCCertificateTrxValue, param);
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
		if (anITrxContext == null) {
			throw new CCCertificateException("The anITrxContext is null!!!");
		}
		if (anICCCertificateTrxValue == null) {
			throw new CCCertificateException("The anICCCertificateTrxValue to be updated is null!!!");
		}
		anICCCertificateTrxValue = formulateTrxValue(anITrxContext, anICCCertificateTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_CCC);
		return operate(anICCCertificateTrxValue, param);
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
		if (anITrxContext == null) {
			throw new CCCertificateException("The anITrxContext is null!!!");
		}
		if (anICCCertificateTrxValue == null) {
			throw new CCCertificateException("The anICCCertificateTrxValue to be updated is null!!!");
		}
		anICCCertificateTrxValue = formulateTrxValue(anITrxContext, anICCCertificateTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_GENERATE_CCC);
		return operate(anICCCertificateTrxValue, param);
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
		if (anITrxContext == null) {
			throw new CCCertificateException("The anITrxContext is null!!!");
		}
		if (anICCCertificateTrxValue == null) {
			throw new CCCertificateException("The anICCCertificateTrxValue to be updated is null!!!");
		}
		if (anICCCertificate == null) {
			throw new CCCertificateException("The ICCCertificate to be updated is null !!!");
		}
		// anICCCertificate.setDateGenerated(new Date());
		anICCCertificateTrxValue = formulateTrxValue(anITrxContext, anICCCertificateTrxValue, anICCCertificate);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_GENERATE_CCC);
		return operate(anICCCertificateTrxValue, param);
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
		if (anITrxContext == null) {
			throw new CCCertificateException("The anITrxContext is null!!!");
		}
		if (anICCCertificateTrxValue == null) {
			throw new CCCertificateException("The anICCCertificateTrxValue to be updated is null!!!");
		}
		anICCCertificateTrxValue = formulateTrxValue(anITrxContext, anICCCertificateTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GENERATE_CCC);
		return operate(anICCCertificateTrxValue, param);
	}

	/**
	 * System close all the CCCs under a limitprofile
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CCCertificateException on errors
	 */
	public void systemCloseCCC(ILimitProfile anILimitProfile) throws CCCertificateException {
		if (anILimitProfile == null) {
			throw new CCCertificateException("The ILimitProfile is null !!!");
		}

		try {
			CCCertificateSearchResult[] resultList = getCCCertificateGenerated(anILimitProfile.getLimitProfileID());
			if ((resultList == null) || (resultList.length == 0)) {
				return;
			}
			for (int ii = 0; ii < resultList.length; ii++) {
				ICCCertificateTrxValue trxValue = getCCCertificateTrxValue(resultList[ii].getCCCertID());
				systemCloseCCC(trxValue);
			}
		}
		catch (SearchDAOException ex) {
			rollback();
			DefaultLogger.error(this, "Caught SearchDAOException in systemCloseCCC!", ex);
			throw new CCCertificateException("Exception in systemCloseCCC", ex);
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
			CCCertificateSearchCriteria criteria = new CCCertificateSearchCriteria();
			criteria.setLimitProfileID(aLimitProfileID);
			criteria.setCategory(ICMSConstant.CHECKLIST_MAIN_BORROWER);
			criteria.setSubProfileID(aCustomerID);
			String trxID = getCCCTrxID(criteria);
			if (trxID != null) {
				ICCCertificateTrxValue trxValue = getCCCertificateTrxValue(trxID);
				return systemCloseCCC(trxValue);
			}
			return null;
		}
		catch (SearchDAOException ex) {
			throw new CCCertificateException("Exception in systemCloseMainBorrowerCCC", ex);
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
			CCCertificateSearchCriteria criteria = new CCCertificateSearchCriteria();
			criteria.setLimitProfileID(aLimitProfileID);
			criteria.setCategory(ICMSConstant.CHECKLIST_PLEDGER);
			criteria.setPledgorID(aPledgorID);
			String trxID = getCCCTrxID(criteria);
			if (trxID != null) {
				ICCCertificateTrxValue trxValue = getCCCertificateTrxValue(trxID);
				return systemCloseCCC(trxValue);
			}
			return null;
		}
		catch (SearchDAOException ex) {
			throw new CCCertificateException("Exception in systemCloseCoBorrowerCCC", ex);
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
			CCCertificateSearchCriteria criteria = new CCCertificateSearchCriteria();
			criteria.setLimitProfileID(aLimitProfileID);
			criteria.setCategory(ICMSConstant.CHECKLIST_CO_BORROWER);
			criteria.setSubProfileID(aCustomerID);
			String trxID = getCCCTrxID(criteria);
			if (trxID != null) {
				ICCCertificateTrxValue trxValue = getCCCertificateTrxValue(trxID);
				return systemCloseCCC(trxValue);
			}
			return null;
		}
		catch (SearchDAOException ex) {
			throw new CCCertificateException("Exception in systemCloseCoBorrowerCCC", ex);
		}
	}

	public ICCCertificateTrxValue systemCloseNonBorrowerCCC(long aCustomerID) throws CCCertificateException {
		try {
			CCCertificateSearchCriteria criteria = new CCCertificateSearchCriteria();
			criteria.setCategory(ICMSConstant.CHECKLIST_NON_BORROWER);
			criteria.setSubProfileID(aCustomerID);
			String trxID = getCCCTrxID(criteria);
			if (trxID != null) {
				ICCCertificateTrxValue trxValue = getCCCertificateTrxValue(trxID);
				return systemCloseCCC(trxValue);
			}
			return null;
		}
		catch (SearchDAOException ex) {
			throw new CCCertificateException("Exception in systemCloseNonBorrowerCCC", ex);
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
			if (anICCCertificateTrxValue == null) {
				throw new CCCertificateException("The anICCCertificateTrxValue to be updated is null!!!");
			}
			anICCCertificateTrxValue = formulateTrxValue(null, anICCCertificateTrxValue);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_CCC);
			return operate(anICCCertificateTrxValue, param);
		}
		catch (CCCertificateException e) {
			rollback();
			DefaultLogger.error(this, "Caught CCCertificateException in systemCloseCCC!", e);
			throw e;
		}
		catch (Exception e) {
			rollback();
			DefaultLogger.error(this, "Caught Exception in systemCloseCCC!", e);
			throw new CCCertificateException("Caught Exception in systemCloseCCC!", e);
		}
	}

	public void handleCertificateForRenewal(ILimitProfile anOldLimitProfile, long aNewLimitProfileID)
			throws CCCertificateException {
		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitProfile newLimitProfile = limitProxy.getLimitProfile(aNewLimitProfileID);
			// handleExistingSCCertificate(anOldLimitProfile, newLimitProfile);
			systemCloseTrx(anOldLimitProfile);
		}
		catch (LimitException ex) {
			throw new CCCertificateException(ex);
		}
	}

	/**
	 * To recompute the total amounts based on the base currency
	 * @param anICCCertificate of ICCCertificate type
	 * @return ICCCertificate - the sc certificate object
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificate computeTotalAmounts(ICCCertificate anICCCertificate) throws CCCertificateException {
		if (anICCCertificate == null) {
			throw new CCCertificateException("The ICCCertificate is null !!!");
		}

		ICCCertificateItem[] itemList = anICCCertificate.getCCCertificateItemList();
		if ((itemList == null) || (itemList.length == 0)) {
			throw new CCCertificateException("There is no ccc item in the ccc");
		}

		CurrencyCode baseCurrency = BaseCurrency.getCurrencyCode();

		long amt = 0;
		long cleanActivatedAmt = 0;
		long notCleanActivatedAmt = 0;
		long totalActivatedAmt = 0;

		for (int ii = 0; ii < itemList.length; ii++) {
			try {
				if (totalActivatedAmt != -1) {
					if (!itemList[ii].isInnerLimit()) {
						if (itemList[ii].isCleanType()) {
							amt = convertAmount(itemList[ii].getActivatedAmount(), baseCurrency);
							cleanActivatedAmt = cleanActivatedAmt + amt;
						}
						totalActivatedAmt = totalActivatedAmt + amt;
					}
				}
			}
			catch (CCCertificateException ex) {
				totalActivatedAmt = -1;
			}
		}
		if (totalActivatedAmt != -1) {
			anICCCertificate.setCleanActivatedAmount(new Amount(cleanActivatedAmt, baseCurrency));
			anICCCertificate.setTotalActivatedAmount(new Amount(totalActivatedAmt, baseCurrency));
		}
		return anICCCertificate;
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
		ICCCertificate ccc = getCCCertificateWithoutLimitInfo(aLimitProfileID, aCategory, aCustomerID);
		return ccc;
	}

	/**
	 * Search CCCertificate based on the criteria given.
	 * 
	 * @param criteria cccertificate criteria
	 * @return SearchResult containing a list of CCCertificateSearchResult
	 * @throws CCCertificateException on any errors encountered
	 */
	public SearchResult searchCCCertificate(CCCertificateSearchCriteria criteria) throws CCCertificateException {
		if (criteria == null) {
			throw new CCCertificateException("CCCertificateSearchCriteria is null!");
		}
		return null;
	}

	/*
	 * private void handleExistingSCCertificate(ILimitProfile oldLimitProfile,
	 * ILimitProfile newLimitProfile) throws CCCertificateException { try {
	 * IDDNProxyManager ddnProxy = DDNProxyManagerFactory.getDDNProxyManager();
	 * 
	 * //close DDN if exist ddnProxy.systemCloseDDN(oldLimitProfile);
	 * 
	 * ISCCertificateProxyManager proxy =
	 * SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
	 * ISCCertificateTrxValue sccTrxValue =
	 * proxy.getSCCertificateTrxByLimitProfile(oldLimitProfile); if (sccTrxValue
	 * != null) { if (sccTrxValue.getSCCertificate() != null) { ITrxContext
	 * trxContext = new OBTrxContext(); IDDNTrxValue trxValue =
	 * ddnProxy.convertSCCToDDN(trxContext, newLimitProfile,
	 * sccTrxValue.getSCCertificate()); } } } catch(Exception ex) { throw new
	 * CCCertificateException("Error in handleExistingSCCertificate !!!", ex); }
	 * 
	 * }
	 */

	public ICCCertificateTrxValue systemCloseCCC(String aCategory, long aLimitProfileID, long aCustomerID)
			throws CCCertificateException {
		try {
			CCCertificateSearchCriteria criteria = new CCCertificateSearchCriteria();
			criteria.setLimitProfileID(aLimitProfileID);
			criteria.setCategory(aCategory);
			if (ICMSConstant.CHECKLIST_PLEDGER.equals(aCategory)) {
				criteria.setPledgorID(aCustomerID);
			}
			else {
				criteria.setSubProfileID(aCustomerID);
			}
			String trxID = getCCCTrxID(criteria);
			if (trxID != null) {
				ICCCertificateTrxValue trxValue = getCCCertificateTrxValue(trxID);
				return systemCloseCCC(trxValue);
			}
			return null;
		}
		catch (SearchDAOException ex) {
			throw new CCCertificateException("Exception in systemCloseCCC", ex);
		}
	}

	public Amount getTotalLimitProfileApprovalAmount(ILimitProfile anILimitProfile) throws CCCertificateException {
		if (anILimitProfile == null) {
			return null;
		}
		ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
		if ((limitList == null) || (limitList.length == 0)) {
			return null;
		}

		CurrencyCode baseCurrency = BaseCurrency.getCurrencyCode();
		long totalApprovalAmt = 0;
		for (int ii = 0; ii < limitList.length; ii++) {
			if (!isInnerLimit(limitList[ii])) {
				try {
					totalApprovalAmt = totalApprovalAmt
							+ convertAmount(limitList[ii].getApprovedLimitAmount(), baseCurrency);
				}
				catch (CCCertificateException ex) {
					totalApprovalAmt = -1;
					return new Amount(totalApprovalAmt, baseCurrency);
				}
			}
		}
		DefaultLogger.debug(this, "TotalApprovalAmount: " + totalApprovalAmt);
		return new Amount(totalApprovalAmt, baseCurrency);
	}

	/**
	 * Method to system close related trx
	 */
	private void systemCloseTrx(ILimitProfile profile) throws CCCertificateException {
		try {
			// close CCC if exist
			systemCloseCCC(profile);
			// close SCC and PSCC if exist
			ISCCertificateProxyManager sccProxy = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
			sccProxy.systemCloseSCC(profile);
			sccProxy.systemClosePartialSCC(profile);
		}
		catch (Exception e) {
			throw new CCCertificateException("Caught Exception in systemCloseTrx!", e);
		}
	}

	private ICCCertificateTrxValue getCCCertificateTrxValue(ILimitProfile anILimitProfile,
			CCCertificateSummary aCCCertificateSummary, String aCategory, long anOwnerID, boolean refreshLimit)
			throws CCCertificateException {
		ICCCertificate ccCert = null;
		ICCCertificateTrxValue trxValue = null;
		ccCert = getCCCertificateWithoutLimitInfo(anILimitProfile.getLimitProfileID(), aCategory, anOwnerID);
		if (ccCert != null) {
			long ccCertID = ccCert.getCCCertID();
			if (refreshLimit) {
				trxValue = getCCCertificateTrxValue(anILimitProfile, aCCCertificateSummary, ccCertID);
			}
			else {
				trxValue = getCCCertificateTrxValue(ccCertID);
				if (trxValue.getCCCertificate() != null) {
					calcTotalLimitAmount(trxValue.getCCCertificate(), trxValue.getCCCertificate()
							.getCCCertificateItemList());
					populateGeneralLimitInfo(anILimitProfile, trxValue.getCCCertificate().getCCCertificateItemList());
				}
				else {
					calcTotalLimitAmount(trxValue.getStagingCCCertificate(), trxValue.getStagingCCCertificate()
							.getCCCertificateItemList());
					populateGeneralLimitInfo(anILimitProfile, trxValue.getStagingCCCertificate()
							.getCCCertificateItemList());
				}
			}
			if (!ICMSConstant.STATE_CLOSED.equals(trxValue.getStatus())) {
				return trxValue;
			}
		}
		ccCert = new OBCCCertificate();
		ccCert.setCCCertCategory(aCategory);
		ccCert.setLimitProfileID(anILimitProfile.getLimitProfileID());
		ccCert.setCheckListID(aCCCertificateSummary.getCheckListID());
		if (aCategory.equals(ICMSConstant.CHECKLIST_PLEDGER)) {
			ccCert.setPledgorID(aCCCertificateSummary.getSubProfileID());
		}
		else {
			ccCert.setSubProfileID(aCCCertificateSummary.getSubProfileID());
		}
		ICCCertificateItem[] itemList = getCCCertificateItemList(anILimitProfile, aCategory, aCCCertificateSummary
				.getSubProfileID());
		ccCert.setCCCertificateItemList(itemList);
		trxValue = new OBCCCertificateTrxValue();
		trxValue.setTransactionType(ICMSConstant.INSTANCE_CCC);
		populateLimitInfo(anILimitProfile, aCCCertificateSummary.getCheckListStatus(), ccCert);
		trxValue.setCCCertificate(ccCert);
		trxValue.setStagingCCCertificate(ccCert);
		return trxValue;
	}

	/**
	 * Formulate the CCC items based on the limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return ICCCertificateItem[] - the list of ccc items
	 * @throws CCCertificateException
	 */
	private ICCCertificateItem[] getCCCertificateItemList(ILimitProfile anILimitProfile, String aCategory,
			long anOwnerID) throws CCCertificateException {
		ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
		ArrayList itemList = new ArrayList();
		Date approvalDate = anILimitProfile.getApprovalDate();
		if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(aCategory)) {
			/*
			 * To use both limits and coborrower limit for main borrower
			 */
			for (int ii = 0; ii < limitList.length; ii++) {
				// ICCCertificateItem item = new OBCCCertificateItem();
				// item.setLimit(limitList[ii]);
				// itemList.add(item);
				formulateItemList(itemList, limitList[ii], approvalDate);
				formulateCoBorrowerItemList(itemList, limitList[ii], approvalDate);
			}
		}
		else if (ICMSConstant.CHECKLIST_CO_BORROWER.equals(aCategory)) {
			/*
			 * Only use coborrower limit for coborrower
			 */
			for (int ii = 0; ii < limitList.length; ii++) {
				formulateCoBorrowerItemList(itemList, limitList[ii], approvalDate, anOwnerID);
			}
		}
		/*
		 * Dont need any item for pledgor
		 */
		return (ICCCertificateItem[]) itemList.toArray(new ICCCertificateItem[0]);
	}

	/**
	 * To formulate the ccc items for both the outer and inner limit
	 * @param anItemList of ArrayList type
	 * @param anILimit of ILimit type
	 * @param anApprovalDate of Date type
	 * @throws CCCertificateException on errors
	 */
	private void formulateItemList(ArrayList anItemList, ILimit anILimit, Date anApprovalDate)
			throws CCCertificateException {
		ICCCertificateItem item = null;
		if (isInnerLimit(anILimit)) {
			item = new OBCCCertificateItem(ICMSConstant.CCC_INNER_LIMIT, anILimit.getLimitID());
		}
		else {
			item = new OBCCCertificateItem(ICMSConstant.CCC_OUTER_LIMIT, anILimit.getLimitID());
		}
		item.setLimitRef(anILimit.getLimitRef());
		item.setOuterLimitRef(anILimit.getOuterLimitRef());
		item.setOuterLimitID(anILimit.getOuterLimitID());
		item.setOuterLimitProfileID(anILimit.getOuterLimitProfileID());
		item.setIsInnerOuterSameBCA(anILimit.getIsInnerOuterSameBCA());
		item.setLimitBookingLocation(anILimit.getBookingLocation());
		item.setProductDesc(anILimit.getProductDesc());
		item.setApprovedLimitAmount(anILimit.getApprovedLimitAmount());
		item.setApprovalDate(anApprovalDate);
		item.setMaturityDate(anILimit.getLimitExpiryDate());
		item.setLimitExpiryDate(anILimit.getLimitExpiryDate());
		if (isCleanType(anILimit)) {
			item.setIsCleanTypeInd(true);
		}
		else {
			item.setIsCleanTypeInd(false);
			item.setActivatedAmount(anILimit.getActivatedLimitAmount());
		}
		anItemList.add(item);
	}

	private void formulateCoBorrowerItemList(ArrayList anItemList, ILimit anILimit, Date anApprovalDate)
			throws CCCertificateException {
		formulateCoBorrowerItemList(anItemList, anILimit, anApprovalDate, ICMSConstant.LONG_MIN_VALUE);
	}

	/**
	 * To formulate the ccc item for co-borrower limit
	 * @param anItemList of ArrayList type
	 * @param anILimit of ILimit type
	 * @param anApprovalDate of Date type
	 * @throws CCCertificateException on errors
	 */
	private void formulateCoBorrowerItemList(ArrayList anItemList, ILimit anILimit, Date anApprovalDate, long anOwnerID)
			throws CCCertificateException {
		ICoBorrowerLimit[] coBorrowerLimitList = anILimit.getNonDeletedCoBorrowerLimits();
		if ((coBorrowerLimitList == null) || (coBorrowerLimitList.length == 0)) {
			return;
		}
		try {
			ICustomerProxy customerproxy = CustomerProxyFactory.getProxy();
			for (int ii = 0; ii < coBorrowerLimitList.length; ii++) {
				ICCCertificateItem item = new OBCCCertificateItem(ICMSConstant.CCC_CB_INNER_LIMIT,
						coBorrowerLimitList[ii].getLimitID());
				ICMSCustomer customer = customerproxy.getCustomer(coBorrowerLimitList[ii].getCustomerID());
				if ((anOwnerID == ICMSConstant.LONG_MIN_VALUE) || (anOwnerID == customer.getCustomerID())) {
					item.setCoBorrowerLegalID(customer.getCMSLegalEntity().getLEReference());
					item.setCoBorrowerName(customer.getCustomerName());
					item.setLimitRef(coBorrowerLimitList[ii].getLimitRef());
					item.setOuterLimitRef(coBorrowerLimitList[ii].getOuterLimitRef());
					item.setLimitBookingLocation(coBorrowerLimitList[ii].getBookingLocation());
					item.setProductDesc(anILimit.getProductDesc());
					item.setApprovedLimitAmount(coBorrowerLimitList[ii].getApprovedLimitAmount());
					item.setApprovalDate(anApprovalDate);
					if (isCleanType(anILimit)) {
						item.setIsCleanTypeInd(true);
					}
					else {
						item.setIsCleanTypeInd(false);
						item.setActivatedAmount(coBorrowerLimitList[ii].getActivatedLimitAmount());
					}
					anItemList.add(item);
				}
			}
		}
		catch (CustomerException ex) {
			throw new CCCertificateException("Caught CustomerException in formulateCoBorrowerItemList", ex);
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

	private ICCCertificateTrxValue getCCCertificateTrxValue(String aTrxID) throws CCCertificateException {
		if (aTrxID == null) {
			throw new CCCertificateException("The TrxID is null !!!");
		}
		ICCCertificateTrxValue trxValue = new OBCCCertificateTrxValue();
		trxValue.setTransactionID(aTrxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_CCC);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CCC);
		return operate(trxValue, param);
	}

	private ICCCertificateTrxValue getCCCertificateTrxValue(long aCCCertID) throws CCCertificateException {
		if (aCCCertID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new CCCertificateException("The CCCertID is invalid !!!");
		}
		ICCCertificateTrxValue trxValue = new OBCCCertificateTrxValue();
		trxValue.setReferenceID(String.valueOf(aCCCertID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_CCC);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CCC_ID);
		return operate(trxValue, param);
	}

	private ICCCertificateTrxValue getCCCertificateTrxValue(ILimitProfile anILimitProfile,
			CCCertificateSummary aCCCertificateSummary, long aCCCertID) throws CCCertificateException {
		ICCCertificateTrxValue trxValue = getCCCertificateTrxValue(aCCCertID);
		if (trxValue.getStagingCCCertificate() != null) {
			populateLimitInfo(anILimitProfile, aCCCertificateSummary.getCheckListStatus(), trxValue
					.getStagingCCCertificate());
		}
		if (trxValue.getCCCertificate() != null) {
			populateLimitInfo(anILimitProfile, aCCCertificateSummary.getCheckListStatus(), trxValue.getCCCertificate());
		}
		return trxValue;
	}

	/**
	 * Helper method to calculate total activated and approved limit amount.
	 * 
	 * @param ccc of type ICCCertificate
	 * @param cccItems of type ICCCertificateItem[]
	 */
	private void calcTotalLimitAmount(ICCCertificate ccc, ICCCertificateItem[] cccItems) {
		long cleanApprovalAmt = 0;
		long notCleanApprovalAmt = 0;
		long totalApprovalAmt = 0;
		long cleanActivatedAmt = 0;
		long notCleanActivatedAmt = 0;
		long totalActivatedAmt = 0;
		CurrencyCode baseCurrency = BaseCurrency.getCurrencyCode();
		long amt = 0;

		int count = cccItems == null ? 0 : cccItems.length;

		for (int ii = 0; ii < count; ii++) {
			ICCCertificateItem item = cccItems[ii];
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
				catch (CCCertificateException ex) {
					cleanApprovalAmt = -1;
					notCleanApprovalAmt = -1;
					totalApprovalAmt = -1;
				}
				try {
					if (totalActivatedAmt != -1) {
						if (item.isCleanType()) {
							if (item.getActivatedAmount() != null) {
								amt = convertAmount(item.getActivatedAmount(), baseCurrency);
								cleanActivatedAmt = cleanActivatedAmt + amt;
								totalActivatedAmt = totalActivatedAmt + amt;
							}
						}
						else {
							if (item.getActivatedAmount() != null) {
								amt = convertAmount(item.getActivatedAmount(), baseCurrency);
								notCleanActivatedAmt = notCleanActivatedAmt + amt;
								totalActivatedAmt = totalActivatedAmt + amt;
							}
						}
					}
				}
				catch (CCCertificateException ex) {
					cleanActivatedAmt = -1;
					notCleanActivatedAmt = -1;
					totalActivatedAmt = -1;
				}
			}
		}
		if (totalApprovalAmt != -1) {
			ccc.setCleanApprovalAmount(new Amount(cleanApprovalAmt, baseCurrency));
			ccc.setApprovalAmount(new Amount(notCleanApprovalAmt, baseCurrency));
			ccc.setTotalApprovalAmount(new Amount(totalApprovalAmt, baseCurrency));
		}
		if (totalActivatedAmt != -1) {
			ccc.setCleanActivatedAmount(new Amount(cleanActivatedAmt, baseCurrency));
			ccc.setActivatedAmount(new Amount(notCleanActivatedAmt, baseCurrency));
			ccc.setTotalActivatedAmount(new Amount(totalActivatedAmt, baseCurrency));
		}
	}

	private void populateLimitInfo(ILimitProfile anILimitProfile, String aCheckListStatus,
			ICCCertificate anICCCertificate) throws CCCertificateException {
		ICCCertificateItem[] fullCertItemList = getCCCertificateItemList(anILimitProfile, anICCCertificate
				.getCCCertCategory(), anICCCertificate.getSubProfileID());
		ICCCertificateItem[] certItemList = anICCCertificate.getCCCertificateItemList();
		if ((fullCertItemList != null) && (fullCertItemList.length > 0)) {
			if ((certItemList != null) && (certItemList.length > 0)) {
				mergeCertItemList(fullCertItemList, certItemList);
			}
			calcTotalLimitAmount(anICCCertificate, fullCertItemList);
			anICCCertificate.setCCCertificateItemList(fullCertItemList);
		}
		return;
	}

	private void mergeCertItemList(ICCCertificateItem[] aFullCertItemList, ICCCertificateItem[] aCertItemList) {
		for (int ii = 0; ii < aCertItemList.length; ii++) {
			for (int jj = 0; jj < aFullCertItemList.length; jj++) {
				if (aFullCertItemList[jj].getLimitType().equals(aCertItemList[ii].getLimitType())
						&& (aFullCertItemList[jj].getLimitID() == aCertItemList[ii].getLimitID())) {
					aFullCertItemList[jj].setCCCertItemID(aCertItemList[ii].getCCCertItemID());
					aFullCertItemList[jj].setCCCertItemRef(aCertItemList[ii].getCCCertItemRef());
					aFullCertItemList[jj].setIsDeletedInd(aCertItemList[ii].getIsDeletedInd());
					if (aFullCertItemList[jj].isCleanType()) {
						aFullCertItemList[jj].setActivatedAmount(aCertItemList[ii].getActivatedAmount());
						aFullCertItemList[jj].setMaturityDate(aCertItemList[ii].getMaturityDate());
					}
				}
			}
		}
	}

	/**
	 * Get the ccc item that belongs to the limit ID specified
	 * @param anICCCertificateItemList of ICCCertificateItem[] type
	 * @param anILimit of ILimit type
	 * @return ICCCertificateItem - the ccc item with that limit ID
	 * @throws CCCertificateException
	 */
	private ICCCertificateItem getCCCertificateItem(ICCCertificateItem[] anICCCertificateItemList, ILimit anILimit)
			throws CCCertificateException {
		DefaultLogger.debug(this, "Number of items: " + anICCCertificateItemList.length);
		for (int ii = 0; ii < anICCCertificateItemList.length; ii++) {
			if (anICCCertificateItemList[ii] == null) {
				return new OBCCCertificateItem();
			}
			DefaultLogger.debug(this, "Limit ID 1: " + anICCCertificateItemList[ii].getLimitID());
			DefaultLogger.debug(this, "Limit ID 2: " + anILimit.getLimitID());
			if (anICCCertificateItemList[ii].getLimitID() == anILimit.getLimitID()) {
				return anICCCertificateItemList[ii];
			}
		}
		throw new CCCertificateException("The CCC Item with Limit ID: " + anILimit.getLimitID() + " is not found");

	}

	private ICCCertificate getCCCertificateWithoutLimitInfo(long aLimitProfileID, String aCategory, long anOwnerID)
			throws CCCertificateException {
		ICCCertificate ccCert = null;
		if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(aCategory)) {
			return getMainBorrowerCCC(aLimitProfileID, anOwnerID);
		}
		if (ICMSConstant.CHECKLIST_CO_BORROWER.equals(aCategory)) {
			return getCoBorrowerCCC(aLimitProfileID, anOwnerID);
		}
		if (ICMSConstant.CHECKLIST_PLEDGER.equals(aCategory)) {
			return getPledgorCCC(aLimitProfileID, anOwnerID);
		}
		if (ICMSConstant.CHECKLIST_NON_BORROWER.equals(aCategory)) {
			DefaultLogger.debug(this, "NON BORROWER !!!");
			return getNonBorrowerCCC(anOwnerID);
		}
		throw new CCCertificateException("The category " + aCategory + " is invalid !!!");
	}

	private ICCCertificateCustomerDetail getCCCustomerInfo(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer,
			String aCategory, long anOwnerID) throws CCCertificateException {
		ICCCertificateCustomerDetail detail = getCustomerInfo(anICMSCustomer, aCategory, anOwnerID);
		detail.setApprovalDate(anILimitProfile.getApprovalDate());
		detail.setOriginatingLocation(anILimitProfile.getOriginatingLocation());
		detail.setNextReviewDate(anILimitProfile.getNextAnnualReviewDate());
		detail.setApprovalAuthority(anILimitProfile.getApproverEmployeeName1());
		detail.setExtReviewDate(anILimitProfile.getExtendedNextReviewDate());

		try {
			detail.setFinalBFLIssuedDate(getFinalBFLIssuedDate(anILimitProfile));
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			HashMap famInfo = null;
			if (anICMSCustomer.getNonBorrowerInd()) {
				famInfo = (HashMap) limitProxy.getFAMNameByCustomer(anICMSCustomer.getCustomerID());
			}
			else {
				famInfo = (HashMap) limitProxy.getFAMName(anILimitProfile.getLimitProfileID());
			}
			if (famInfo != null) {
				detail.setFamCode((String) famInfo.get(ICMSConstant.FAM_CODE));
				detail.setFamName((String) famInfo.get(ICMSConstant.FAM_NAME));
			}
			DefaultLogger.debug(this, "FAMCODE: " + detail.getFamCode());
			DefaultLogger.debug(this, "FAMName: " + detail.getFamName());
			return detail;
		}
		catch (LimitException ex) {
			throw new CCCertificateException(ex);
		}
	}

	private Date getFinalBFLIssuedDate(ILimitProfile anILimitProfile) {
		ITATEntry[] entries = anILimitProfile.getTATEntries();
		if ((entries != null) && (entries.length != 0)) {
			Arrays.sort(entries, new TATComparator());
			for (int ii = (entries.length - 1); ii >= 0; ii--) { // To fix
																	// cms1347
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

	private ICCCertificateCustomerDetail getCustomerInfo(ICMSCustomer anICMSCustomer, String aCategory, long anOwnerID)
			throws CCCertificateException {
		if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(aCategory)
				|| ICMSConstant.CHECKLIST_CO_BORROWER.equals(aCategory)
				|| ICMSConstant.CHECKLIST_PLEDGER.equals(aCategory)) {
			return getCMSCustomer(anICMSCustomer);
		}
		if (ICMSConstant.CHECKLIST_NON_BORROWER.equals(aCategory)) {
			return getCMSCustomer(anOwnerID);
		}
		throw new CCCertificateException("Category " + aCategory + " is invalid !!!");
	}

	private ICCCertificateCustomerDetail getCMSCustomer(long aSubProfileID) throws CCCertificateException {
		try {
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer customer = custProxy.getCustomer(aSubProfileID);
			return getCMSCustomer(customer);
		}
		catch (CustomerException ex) {
			throw new CCCertificateException("Exception in getCMSCustomer with customer ID " + aSubProfileID, ex);
		}
	}

	private ICCCertificateCustomerDetail getCMSCustomer(ICMSCustomer anICMSCustomer) throws CCCertificateException {
		ICCCertificateCustomerDetail custDetail = new OBCCCertificateCustomerDetail();
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

	private ICCCertificateCustomerDetail getPledgorInfo(long aPledgorID) throws CCCertificateException {
		IPledgor pledgor = getPledgor(aPledgorID);
		ICCCertificateCustomerDetail pledgorDetail = new OBCCCertificateCustomerDetail();
		pledgorDetail.setCustomerID(pledgor.getPledgorID());
		pledgorDetail.setLegalID(String.valueOf(pledgor.getLegalID()));
		pledgorDetail.setLegalName(pledgor.getPledgorName());
		pledgorDetail.setCustomerReference(String.valueOf(pledgor.getSysGenPledgorID()));
		// pledgorDetail.setCustomerName(pledgor.getPledgorName());
		pledgorDetail.setCustomerSegmentCode(pledgor.getSegmentCode());
		IPledgorCreditGrade[] gradeList = pledgor.getCreditGrades();
		ICreditGrade creditGrade = new OBCreditGrade();
		if ((gradeList != null) && (gradeList.length > 0)) {
			creditGrade.setCGCode(gradeList[0].getCreditGradeCode());
		}
		pledgorDetail.setCreditGrade(creditGrade);
		ICreditStatus creditStatus = new OBCreditStatus();
		pledgorDetail.setCreditStatus(creditStatus);
		return pledgorDetail;
	}

	/**
	 * Formulate the cc certificate Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICCCertificate - ICCCertificate
	 * @return ICCCertificateTrxValue - the cc certificate trx interface
	 *         formulated
	 */
	private ICCCertificateTrxValue formulateTrxValue(ITrxContext anITrxContext, ICCCertificate anICCCertificate) {
		return formulateTrxValue(anITrxContext, null, anICCCertificate);
	}

	/**
	 * Formulate the cc certificate Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @param anICCCertificate - ICCCertificate
	 * @return ICCCertificateTrxValue - the cc certificate trx interface
	 *         formulated
	 */
	private ICCCertificateTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			ICCCertificate anICCCertificate) {
		ICCCertificateTrxValue ccCertificateTrxValue = null;
		if (anICMSTrxValue != null) {
			ccCertificateTrxValue = new OBCCCertificateTrxValue(anICMSTrxValue);
		}
		else {
			ccCertificateTrxValue = new OBCCCertificateTrxValue();
		}
		ccCertificateTrxValue = formulateTrxValue(anITrxContext, (ICCCertificateTrxValue) ccCertificateTrxValue);
		ccCertificateTrxValue.setStagingCCCertificate(anICCCertificate);
		return ccCertificateTrxValue;
	}

	/**
	 * Formulate the cc certificate trx object
	 * @param anITrxContext - ITrxContext
	 * @param anICCCertificateTrxValue - ICCCertificateTrxValue
	 * @return ICCCertificateTrxValue - the cc certificate trx interface
	 *         formulated
	 */
	private ICCCertificateTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICCCertificateTrxValue anICCCertificateTrxValue) {
		if (anITrxContext != null) {
			anICCCertificateTrxValue.setTrxContext(anITrxContext);
		}
		anICCCertificateTrxValue.setTransactionType(ICMSConstant.INSTANCE_CCC);
		return anICCCertificateTrxValue;
	}

	private ICCCertificateTrxValue operate(ICCCertificateTrxValue anICCCertificateTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws CCCertificateException {
		ICMSTrxResult result = operateForResult(anICCCertificateTrxValue, anOBCMSTrxParameter);
		return (ICCCertificateTrxValue) result.getTrxValue();
	}

	private ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CCCertificateException {
		try {
			ITrxController controller = (new CCCertificateTrxControllerFactory()).getController(anICMSTrxValue,
					anOBCMSTrxParameter);
			if (controller == null) {
				throw new CCCertificateException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			rollback();
			throw new CCCertificateException(e);
		}
		catch (Exception ex) {
			rollback();
			throw new CCCertificateException(ex.toString());
		}
	}

	protected abstract void rollback();

	protected abstract ICCCertificate getMainBorrowerCCC(long aLimitProfileID, long anOwnerID)
			throws CCCertificateException;

	protected abstract ICCCertificate getCoBorrowerCCC(long aLimitProfileID, long anOwnerID)
			throws CCCertificateException;

	protected abstract ICCCertificate getPledgorCCC(long aLimitProfileID, long aPledgorID)
			throws CCCertificateException;

	protected abstract ICCCertificate getNonBorrowerCCC(long anOwnerID) throws CCCertificateException;

	protected abstract long convertAmount(Amount anAmount, CurrencyCode aCurrencyCode) throws CCCertificateException;

	protected abstract int getNoOfCCCertificate(CCCertificateSearchCriteria aCriteria) throws CCCertificateException;

	protected abstract IPledgor getPledgor(long aPledgorID) throws CCCertificateException;

	protected abstract CCCertificateSearchResult[] getCCCertificateGenerated(long aLimitProfileID)
			throws SearchDAOException, CCCertificateException;

	protected abstract String getCCCTrxID(CCCertificateSearchCriteria aCriteria) throws SearchDAOException,
			CCCertificateException;
}
