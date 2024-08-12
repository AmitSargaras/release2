/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/proxy/AbstractLimitProxy.java,v 1.33 2006/10/27 02:52:28 hmbao Exp $
 */
package com.integrosys.cms.app.limit.proxy;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.forex.SBForexManager;
import com.integrosys.base.businfra.forex.SBForexManagerHome;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ITATEntry;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.TATComparator;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.proxy.ISCCertificateProxyManager;
import com.integrosys.cms.app.sccertificate.proxy.SCCertificateProxyManagerFactory;
import com.integrosys.cms.app.sharesecurity.bus.SBShareSecurityManager;
import com.integrosys.cms.app.sharesecurity.bus.SBShareSecurityManagerHome;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.collateralthreshold.CollateralRankingComparator;
import com.integrosys.cms.batch.collateralthreshold.CollateralThresholdDAO;

/**
 * This abstract class provides some of the services that are available in for
 * use in the interaction with Limit and Limit Profile.
 * 
 * @author $Author: hmbao $
 * @version $Revision: 1.33 $
 * @since $Date: 2006/10/27 02:52:28 $ Tag: $Name: $
 */
public abstract class AbstractLimitProxy implements ILimitProxy {

	private static final long serialVersionUID = 7285327929074666807L;

	/**
	 * Default Constructor
	 */
	public AbstractLimitProxy() {
	}

	/**
	 * Get a HashMap containing the following information: 1. The HashMap keys
	 * are ICollateral objects 2. The HashMap values are ArrayList of ILimit
	 * objects that are linked to the ICollateral keys
	 * 
	 * @param limitProfile
	 * @return a HashMap with key ICollateral and value an ArrayList of ILimit
	 *         objects
	 */
	public HashMap getCollateralLimitMap(ILimitProfile limitProfile) {
		ILimit[] limitList = limitProfile.getLimits();

		HashMap hmap = new HashMap();
		int limitCount = limitList == null ? 0 : limitList.length;
		int colCount;
		for (int i = 0; i < limitCount; i++) {

			ICollateralAllocation[] allocList = limitList[i].getCollateralAllocations();
			colCount = allocList == null ? 0 : allocList.length;
			for (int j = 0; j < colCount; j++) {
				ICollateral col = allocList[j].getCollateral();
				if (col != null) {
					ArrayList alist = (ArrayList) hmap.get(col);
					if (alist == null) {
						alist = new ArrayList();
					}
					alist.add(limitList[i]);
					hmap.put(col, alist);
				}
			}

			if (limitList[i].getCoBorrowerLimits() != null) {
				ICoBorrowerLimit[] colist = limitList[i].getCoBorrowerLimits();
				int limitCount1 = colist == null ? 0 : colist.length;
				for (int k = 0; k < limitCount1; k++) {
					ICollateralAllocation[] allocList1 = colist[k].getCollateralAllocations();
					int limitCount2 = allocList1 == null ? 0 : allocList1.length;
					for (int x = 0; x < limitCount2; x++) {
						if (allocList1[x].getCollateral() != null) {
							ICollateral col = allocList1[x].getCollateral();

							ArrayList alist = (ArrayList) hmap.get(col);
							if (alist == null) {
								alist = new ArrayList();
							}
							alist.add(colist[k]);
							hmap.put(col, alist);
						}
					}
				}
			}

		} // end for main loop

		return hmap;
	}

	/**
	 * Filter off deleted collaterals that have no checklist attached to it.
	 * 
	 * @param trxCtx of type ITrxContext
	 * @param limitProfile of type ILimitProfile
	 * @return limits with filtered collateral allocation
	 * @throws LimitException on error during filtering
	 */
	public ILimit[] getFilteredNilColCheckListLimits(ITrxContext trxCtx, ILimitProfile limitProfile)
			throws LimitException {
		if ((trxCtx == null) || (limitProfile == null)) {
			throw new LimitException("Transaction Context or BCA is NULL");
		}
		int countLimits = 0;
		ILimit[] lmts = limitProfile.getLimits();
		if ((lmts == null) || ((countLimits = lmts.length) == 0)) {
			return lmts;
		}

		// CHECKLIST module not in use by MBB/MBS
		// Comment out unnecessary call to get collateral checklist
		// try {
		// ICheckListProxyManager proxy =
		// CheckListProxyManagerFactory.getCheckListProxyManager();
		// HashMap allColChkLst = proxy.getAllCollateralCheckListSummaryList
		// (trxCtx, limitProfile.getLimitProfileID());
		HashMap allColChkLst = null;

		if (allColChkLst == null) { // no checklist available so we must filter
			// off deleted security allocation

			// filter out deleted limit security linkages
			for (int i = 0; i < countLimits; i++) {
				// lmts[i].setCollateralAllocations
				// (lmts[i].getNonDeletedCollateralAllocations());
				populateSourceSecId(lmts[i].getCollateralAllocations());
				ICoBorrowerLimit[] colmts = lmts[i].getCoBorrowerLimits();
				if (colmts != null) {
					for (int j = 0; j < colmts.length; j++) {
						// colmts[j].setCollateralAllocations(colmts[j].
						// getNonDeletedCollateralAllocations());
						populateSourceSecId(colmts[j].getCollateralAllocations());
					}
				}
			}
			return lmts;
		}
		else {
			// this section will not called for MBB/MBS
			ArrayList aList = new ArrayList();

			CollateralCheckListSummary[] normalList = (CollateralCheckListSummary[]) allColChkLst
					.get(ICMSConstant.NORMAL_LIST);
			CollateralCheckListSummary[] deletedList = (CollateralCheckListSummary[]) allColChkLst
					.get(ICMSConstant.DELETED_LIST);

			int countNormalList = normalList == null ? 0 : normalList.length;
			int countDeletedList = deletedList == null ? 0 : deletedList.length;

			for (int i = 0; i < countLimits; i++) {
				ICollateralAllocation[] lmtcols = lmts[i].getCollateralAllocations();
				lmts[i].setCollateralAllocations(filterColAllocationByColChecklist(normalList, deletedList, lmtcols));
				ICoBorrowerLimit[] colmts = lmts[i].getCoBorrowerLimits();
				if (colmts != null) {
					for (int j = 0; j < colmts.length; j++) {
						lmtcols = colmts[j].getCollateralAllocations();
						colmts[j].setCollateralAllocations(filterColAllocationByColChecklist(normalList, deletedList,
								lmtcols));
					}
				}
				lmts[i].setCoBorrowerLimits(colmts);
				aList.add(lmts[i]);
			}
			return (ILimit[]) aList.toArray(new ILimit[0]);
		}
		// }
		// catch(CheckListException ce){
		// throw new LimitException
		// ("Error encountered in getFilteredNilColCheckListLimits " +
		// ce.getMessage());
		// }
	}

	private void populateSourceSecId(ICollateralAllocation[] allocs) {
		try {
			if (allocs != null) {
				SBShareSecurityManager manager = getSBShareSecurityManager();
				List tempList = new ArrayList();
				for (int i = 0; i < allocs.length; i++) {
					ICollateralAllocation nextAlloc = allocs[i];
					ICollateral nextCol = nextAlloc.getCollateral();
					if (nextCol != null) {
						tempList.add(new Long(nextCol.getCollateralID()));
					}
				}
				Map m = manager.getSharedSecNameForCollaterals(tempList);
				for (int j = 0; j < allocs.length; j++) {
					ICollateralAllocation nextAlloc = allocs[j];
					ICollateral nextCol = nextAlloc.getCollateral();
					if (nextCol != null) {
						nextCol.setSourceSecIdAliases((List) (m.get(String.valueOf(nextCol.getCollateralID()))));
					}
				}
				tempList.clear();
			}
		}
		catch (RemoteException ex) {
			DefaultLogger.warn(this, "failed to retrieve share security id and type: " + ex.getCause());
		}
	}

	private SBShareSecurityManager getSBShareSecurityManager() throws IllegalStateException {
		SBShareSecurityManager home = (SBShareSecurityManager) (BeanController.getEJB(
				ICMSJNDIConstant.SB_SHARE_SECURITY_MGR_JNDI, SBShareSecurityManagerHome.class.getName()));
		if (null != home) {
			return home;
		}
		else {
			throw new IllegalStateException("Failed to obtain share security remote home using jndi name ["
					+ ICMSJNDIConstant.SB_SHARE_SECURITY_MGR_JNDI + "]");
		}
	}

	private ICollateralAllocation[] filterColAllocationByColChecklist(CollateralCheckListSummary[] normalList,
			CollateralCheckListSummary[] deletedList, ICollateralAllocation[] lmtcols) {

		int countNormalList = normalList == null ? 0 : normalList.length;
		int countDeletedList = deletedList == null ? 0 : deletedList.length;

		ArrayList newlmtcols = new ArrayList();
		int countLmtCols = lmtcols == null ? 0 : lmtcols.length;

		for (int j = 0; j < countLmtCols; j++) {
			long collateralID = lmtcols[j].getCollateral().getCollateralID();
			boolean isFoundInNormal = false;
			for (int k = 0; !isFoundInNormal && (k < countNormalList); k++) {
				if (normalList[k].getCollateralID() == collateralID) {
					newlmtcols.add(lmtcols[j]);
					isFoundInNormal = true;
				}
			}
			if (isFoundInNormal) {
				continue;
			}
			boolean isFoundInDeleted = false;
			for (int l = 0; !isFoundInDeleted && (l < countDeletedList); l++) {
				if (deletedList[l].getCollateralID() == collateralID) {
					newlmtcols.add(lmtcols[j]);
					isFoundInDeleted = true;
				}
			}
		}
		return (ICollateralAllocation[]) newlmtcols.toArray(new ICollateralAllocation[0]);
	}

	/**
	 * Identify if a limit profile has been issued SCC. This method returns an
	 * enumerated value in integer to represent if SCC status has not been
	 * issued, partially issued, or full issued. The constant values can be
	 * found in <code>ICMSConstant</code>
	 * 
	 * @param value is the ILimitProfile object to be evaluated
	 * @return int
	 */
	public int getSCCStatus(ILimitProfile value) {
		/*
		 * if (isCleanType(value)) { return ICMSConstant.SCC_NOT_APPLICABLE; }
		 */
		ITATEntry[] entryList = value.getTATEntries();
		if (null == entryList) {
			return ICMSConstant.SCC_NOT_ISSUED;
		}
		else {
			for (int i = 0; i < entryList.length; i++) {
				// check for full issue first, followed by not issued
				ITATEntry entry = entryList[i];
				String code = entry.getTATServiceCode();
				if (code.equals(ICMSConstant.TAT_CODE_GEN_SCC)) {
					return ICMSConstant.SCC_FULL_ISSUED;
				}
			}

			if (isPartialSCCIssued(value)) {
				return ICMSConstant.SCC_PARTIAL_ISSUED;
			}
			return ICMSConstant.SCC_NOT_ISSUED; // coz not found so return as
			// not issued
		}
	}

	private boolean isCleanType(ILimitProfile value) {
		if (value == null) {
			return true;
		}
		ILimit[] limitList = value.getLimits();
		if ((limitList == null) || (limitList.length == 0)) {
			return true;
		}
		for (int ii = 0; ii < limitList.length; ii++) {
			if ((limitList[ii].getNonDeletedCollateralAllocations() != null)
					&& (limitList[ii].getNonDeletedCollateralAllocations().length > 0)) {
				return false;
			}
		}
		return true;
	}

	private boolean isPartialSCCIssued(ILimitProfile value) {
		try {
			ISCCertificateProxyManager mgr = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
			IPartialSCCertificate pscc = mgr.getPartialSCCertificateByLimitProfile(value);
			if (pscc == null) {
				return false;
			}
			return true;
		}
		catch (Exception ex) {
			DefaultLogger.debug(this, "Caught Exception in isPartialSCCIssued");
			return false;
		}
	}

	/**
	 * Identify the BFL status of a limit profile. The possible BFL status are:
	 * 
	 * <pre>
	 * 1. BFL status unknown. This occurs when Create TAT has not been performed yet on the limit profile.
	 * 2. BFL not required. This occurs when during Create TAT, BFL not required is indicated.
	 * 3. BFL not init. This occurs when during Create TAT, BFL is indicated as required, but the first BFL task has
	 * not been performed yet.
	 * 4. BFL in progress. This occurs when at least one BFL tasks have been performed, but the final task has not been
	 * performed yet
	 * 5. BFL completed. This occurs when all BFL tasks have been performed.
	 * </pre>
	 * 
	 * Note: The constant values identifying the various status can be found in
	 * <code>ICMSConstant</code>
	 * 
	 * @param value is the ILimitProfile to be evaluated
	 * @return int
	 */
	public int getBFLStatus(ILimitProfile value) {
		if (value.getTATCreateDate() == null) { // TAT not created yet
			return ICMSConstant.BFL_STATUS_UNKNOWN;
		}
		else {
			if (!value.getBFLRequiredInd()) {
				return ICMSConstant.BFL_NOT_REQUIRED;
			}
			else {
				// check if customer accept BFL is in tat entries
				ITATEntry[] entries = value.getTATEntries();
				if ((entries != null) && (entries.length != 0)) {
					boolean inProgress = false;

					for (int i = 0; i < entries.length; i++) {
						ITATEntry entry = entries[i];
						String code = entry.getTATServiceCode();

						if (code.equals(ICMSConstant.TAT_CODE_CUSTOMER_ACCEPT_BFL)) {
							return ICMSConstant.BFL_COMPLETED;
						}
						else if (code.equals(ICMSConstant.TAT_CODE_ISSUE_DRAFT_BFL)
								|| code.equals(ICMSConstant.TAT_CODE_SPECIAL_ISSUE_CLEAN_BFL)) {

							inProgress = true;
						}
					}
					// not found so return in progress
					if (inProgress) {
						return ICMSConstant.BFL_IN_PROGRESS;
					}
					else {
						return ICMSConstant.BFL_REQUIRED_NOT_INIT;
					}
				}
				else {
					return ICMSConstant.BFL_REQUIRED_NOT_INIT;
				}
			}
		}
	}

	/**
	 * Get the description of the latest BFL that was performed
	 * 
	 * @return String
	 */
	public String getLatestBFLDesc(ILimitProfile value) {
		String desc = "-";

		if (null == value) {
			return desc;
		}
		if (!value.getBFLRequiredInd()) {
			return "Not Required";
		}

		ITATEntry[] entryList = value.getTATEntries();

		if ((null == entryList) || (entryList.length == 0)) {
			return desc;
		}

		Arrays.sort(entryList, new TATComparator());

		for (int i = entryList.length - 1; i >= 0; i--) {
			ITATEntry entry = entryList[i];
			String code = entry.getTATServiceCode();
			// start from last to first BFL status
			if (code.equals(ICMSConstant.TAT_CODE_CUSTOMER_ACCEPT_BFL)) {
				desc = "Customer Accepted";
				break;
			}
			else if (code.equals(ICMSConstant.TAT_CODE_ISSUE_FINAL_BFL)) {
				desc = "Final Issued";
				break;
			}
			else if (code.equals(ICMSConstant.TAT_CODE_SPECIAL_ISSUE_CLEAN_BFL)) {
				desc = "Special Issued";
				break;
			}
			else if (code.equals(ICMSConstant.TAT_CODE_ISSUE_CLEAN_BFL)) {
				desc = "Clean Issued";
				break;
			}
			else if (code.equals(ICMSConstant.TAT_CODE_ACK_REC_DRAFT_BFL)) {
				desc = "Draft Received";
				break;
			}
			else if (code.equals(ICMSConstant.TAT_CODE_SEND_DRAFT_BFL)) {
				desc = "Draft Sent";
				break;
			}
			else if (code.equals(ICMSConstant.TAT_CODE_ISSUE_DRAFT_BFL)) {
				desc = "Draft Issued";
				break;
			}
		}
		return desc;
	}

	/**
	 * Identify if all limits in a limit profile (including co-borrower limits)
	 * have been activated. The method returns true if ALL limits are not
	 * activated, partially activated or fully activated.
	 * 
	 * @param value is the ILimitProfile to be evaluated
	 * @return int
	 */
	public int getActivatedLimitsStatus(ILimitProfile value) {
		int limitCount = 0;
		int coBorrowerLimitCount = 0;
		int notActivatedCount = 0;

		ILimit[] limitList = value.getLimits();
		if ((null != limitList) && (limitList.length != 0)) {
			limitCount = limitList.length;

			for (int i = 0; i < limitList.length; i++) {
				ILimit limit = limitList[i];
				if (ICMSConstant.STATE_DELETED.equals(limit.getLimitStatus())) {
					continue; // don't include this
				}
				/*
				 * Amount amt = limit.getActivatedLimitAmount(); if(null == amt
				 * || amt.getAmount() == 0) { notActivatedCount++; }
				 */
				if (!limit.getLimitActivatedInd()) {
					notActivatedCount++;
				}
				else {
					ICoBorrowerLimit[] coLimitList = limit.getCoBorrowerLimits();
					if ((null != coLimitList) && (coLimitList.length != 0)) {
						coBorrowerLimitCount += coLimitList.length;

						for (int j = 0; j < coLimitList.length; j++) {
							ICoBorrowerLimit coLimit = coLimitList[j];
							/*
							 * Amount coAmt = coLimit.getActivatedLimitAmount();
							 * if(null == coAmt || coAmt.getAmount() == 0) {
							 * notActivatedCount++; }
							 */
							if (!coLimit.getLimitActivatedInd()) {
								notActivatedCount++;
							}
						}
					}
				}
			}
		}

		if (0 == notActivatedCount) {
			// full activation
			return ICMSConstant.ACTIVATED_LIMIT_FULL;
		}
		else if (notActivatedCount == (limitCount + coBorrowerLimitCount)) {
			// completely not activated
			return ICMSConstant.ACTIVATED_LIMIT_NONE;
		}
		else {
			return ICMSConstant.ACTIVATED_LIMIT_PARTIAL;
		}
	}

	/**
	 * Identify if all limits in a limit profile (including co-borrower limits)
	 * have been activated. The method returns true if ALL limits are activated.
	 * 
	 * @param value is the ILimitProfile to be evaluated
	 * @return boolean
	 */
	public boolean areAllLimitsActivated(ILimitProfile value) {
		// the method assumes true by default.
		boolean activated = true;

		ILimit[] limitList = value.getLimits();
		if ((null != limitList) && (limitList.length != 0)) {
			for (int i = 0; i < limitList.length; i++) {
				ILimit limit = limitList[i];
				if (ICMSConstant.STATE_DELETED.equals(limit.getLimitStatus())) {
					continue; // don't include this
				}

				if (!limit.getLimitActivatedInd()) {
					activated = false;
					return activated;
				}
				/*
				 * Amount amt = limit.getActivatedLimitAmount(); if(null == amt
				 * || amt.getAmount() == 0) { activated = false; return
				 * activated; }
				 */
				else {
					ICoBorrowerLimit[] coLimitList = limit.getCoBorrowerLimits();
					if ((null != coLimitList) && (coLimitList.length != 0)) {
						for (int j = 0; j < coLimitList.length; j++) {
							ICoBorrowerLimit coLimit = coLimitList[j];
							if (ICMSConstant.STATE_DELETED.equals(coLimit.getStatus())) {
								continue; // don't include this
							}
							/*
							 * Amount coAmt = coLimit.getActivatedLimitAmount();
							 * if(null == coAmt || coAmt.getAmount() == 0) {
							 * activated = false; return activated; }
							 */
							if (!coLimit.getLimitActivatedInd()) {
								activated = false;
								return activated;
							}
						}
					}
				}
			}
		}
		return activated;
	}

	/**
	 * To get the name of the FAM for the given BCA
	 * 
	 * @param limitProfileID the ID of the limit profile
	 * @return the name of the FAM
	 */
	public HashMap getFAMName(long limitProfileID) throws LimitException {
		return new LimitDAO().getFAMName(limitProfileID);
	}

	/**
	 * To get the transaction subtype of the limit transaction
	 * 
	 * @param trxID the ID of the transaction
	 * @return the transaction subtype
	 */
	public String getTrxSubTypeByTrxID(long aTrxID) throws LimitException {
		try {
			return new LimitDAO().getTrxSubTypeByTrxID(aTrxID);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new LimitException("getTrxSubTypeByTrxID exception:" + e.toString());
		}
	}

	/**
	 * To get the name of the FAM for the given customer
	 * 
	 * @param customerID the ID of the customer
	 * @return the name of the FAM
	 */
	public HashMap getFAMNameByCustomer(long customerID) throws LimitException {
		return new LimitDAO().getFAMNameByCustomer(customerID);
	}

	/**
	 * Computes the due date of a date given its day period and country code.
	 * 
	 * @param startDate start date
	 * @param numOfDays the number of days before the startDate is due
	 * @param countryCode country code
	 * @return due date
	 */
	public Date getDueDate(Date startDate, int numOfDays, String countryCode) {
		try {
			return new LimitDAO().getDueDate(startDate, numOfDays, countryCode);
		}
		catch (Exception e) {
			DefaultLogger.warn(this, "Exception in calculating Due date, returning null.", e);
			return null;
		}
	}

	/**
	 * Get a list of booking location belong to the country given.
	 * 
	 * @param country country code
	 * @return a list of booking location
	 * @throws LimitException on error getting the booking location
	 */
	public IBookingLocation[] getBookingLocationByCountry(String country) throws LimitException {
		return new LimitDAO().getBookingLocationByCountry(country);
	}

	/**
	 * Get a list of BFL TAT Parameters belong to the given country.
	 * 
	 * @param country code
	 * @return a list of BFL TAT Parameters
	 */
	public List getBFLTATParameter(String country) throws LimitException {
		return new LimitDAO().getBFLTATParameter(country);
	}

	/**
	 * Get a list of booking location belong to the country given.
	 * 
	 * @param country country code
	 * @return a list of booking location
	 * @throws LimitException on error getting the booking location
	 */
	public String[] getUniqueBookingLocation(String country) throws LimitException {
		return new LimitDAO().getUniqueBookingLocation(country);
	}

	/**
	 * Get a list of booking location belong to the given country list.
	 * 
	 * @param country a list of country codes
	 * @return a list of booking location
	 */
	public IBookingLocation[] getBookingLocationByCountry(String[] country) throws LimitException {
		return new LimitDAO().getBookingLocationByCountry(country);
	}

	/**
	 * Get a list of booking location belong to the given country list.
	 * 
	 * @param country a list of country codes
	 * @return a list of booking location
	 */
	public String[] getUniqueBookingLocation(String[] country) throws LimitException {
		return new LimitDAO().getUniqueBookingLocation(country);
	}

	/**
	 * Get country given the booking location.
	 * 
	 * @param bookingLocation country code
	 * @return booking location
	 * @throws LimitException on error getting the country code
	 */
	public IBookingLocation getCountryCodeByBookingLocation(String bookingLocation) throws LimitException {
		return new LimitDAO().getCountryCodeByBookingLocation(bookingLocation);
	}

	/**
	 * Get a booking location given the SCI booking location id.
	 * 
	 * @param sciBkgLocknID booking location id from SCI
	 * @return booking location
	 * @throws LimitException on error getting the booking location
	 */
	public IBookingLocation getBookingLocation(long sciBkgLocknID) throws LimitException {
		return new LimitDAO().getBookingLocation(sciBkgLocknID);
	}

	public Date[] getBFLDueDates(boolean isRenewed, String segment, String country, Date bcaRenewDate)
			throws LimitException {
		return new LimitDAO().getBFLDueDates(isRenewed, segment, country, bcaRenewDate);
	}

	public ITATEntry[] getTatEntry(long limitProfileID) throws LimitException {
		return new LimitDAO().getTatEntry(limitProfileID);
	}

	/***********************************************************************************
	 * Moved to Limit Proxy from CollateralMainThreshold to commit each
	 * iteration
	 ***********************************************************************************/
	private String baseCurrency = CommonUtil.getBaseCurrency();

	private float variance = 1; // default is 1. anything less than 1 is ignored

	/**
	 * Method to compute limit threshold
	 */
	public void computeLimitThreshold(ILimit limit, CollateralThresholdDAO dao, ILimitProxy proxy) throws Exception {
		HashMap profileMap = new HashMap();

		Long profileID = new Long(limit.getLimitProfileID());
		ILimitProfile profile = (ILimitProfile) profileMap.get(profileID);
		if (null == profile) {
			profile = proxy.getLimitProfile(profileID.longValue());
			profileMap.put(profileID, profile);
		}

		Amount totalActivatedAmt = computeTotalActivatedLimits(limit, profile.getLimits(), false);
		double totalChargeAmt = dao.getChargeAmountByLimit(limit.getLimitID());

		double percent = 0;
		double actDouble = totalActivatedAmt.getAmount();
		if (0 != actDouble) {
			DefaultLogger.debug(this, "Computing LimitThreshold. TotalChargeAmt: " + totalChargeAmt + " Activated: "
					+ actDouble);
			percent = (totalChargeAmt / actDouble) * 100D;
			percent = Math.round(percent); // round to nearest non decimal value
		}
		DefaultLogger.debug(this, "Percent in computeLimitThreshold: " + percent);
		// persist the actual threshold
		persistLimitThreshold(limit, percent);
	}

	/**
	 * Method to compuete limit profile threshold
	 */
	public void computeLimitProfileThreshold(ILimitProfile profile, CollateralThresholdDAO dao) throws Exception {
		ILimit[] limitList = profile.getLimits();
		long profileID = profile.getLimitProfileID();

		if ((null == limitList) || (limitList.length == 0)) {
			throw new Exception("Limit List in LimitProfile: " + profileID + " is null!");
		}
		Amount totalActivatedAmt = computeTotalActivatedLimits(limitList);
		double totalChargeAmt = dao.getChargeAmountByProfile(profileID);

		double percent = 0;
		double actDouble = totalActivatedAmt.getAmount();
		if (0 != actDouble) {
			DefaultLogger.debug(this, "Computing LimitProfileThreshold. TotalChargeAmt: " + totalChargeAmt
					+ " Activated: " + actDouble);
			percent = (totalChargeAmt / actDouble) * 100D;
			percent = Math.round(percent); // round to nearest non decimal value
		}
		DefaultLogger.debug(this, "Percent in computeLimitProfileThreshold: " + percent);
		// persist the actual threshold
		persistLimitProfileThreshold(profile, percent);
	}

	/*
	 * Method to persist limit profile threshold
	 */
	private void persistLimitProfileThreshold(ILimitProfile profile, double percent) throws Exception {
		percent = Math.round(percent); // round to nearest non decimal value

		ILimitProxy proxy = LimitProxyFactory.getProxy();
		ILimitProfileTrxValue trxValue = proxy.getTrxLimitProfile(profile.getLimitProfileID());

		ILimitProfile newProfile = trxValue.getLimitProfile();
		newProfile.setActualSecurityCoverage((float) percent);
		trxValue.setLimitProfile(newProfile);

		ILimitProfile stageProfile = trxValue.getStagingLimitProfile();
		stageProfile.setActualSecurityCoverage((float) percent);
		trxValue.setStagingLimitProfile(stageProfile);

		proxy.systemUpdateLimitProfile(trxValue);
		DefaultLogger.debug(this, "Limit profile updated:" + profile.getLimitProfileID() + " LE:"
				+ profile.getLEReference());
	}

	/*
	 * Method to persist limit threshold
	 */
	private void persistLimitThreshold(ILimit limit, double percent) throws Exception {
		percent = Math.round(percent); // round to nearest non decimal value

		ILimitProxy proxy = LimitProxyFactory.getProxy();
		ILimitTrxValue trxValue = proxy.getTrxLimit(limit.getLimitID());
		ILimit newLimit = trxValue.getLimit();
		newLimit.setActualSecurityCoverage((float) percent);
		trxValue.setLimit(newLimit);

		ILimit stageLimit = trxValue.getStagingLimit();
		stageLimit.setActualSecurityCoverage((float) percent);
		trxValue.setStagingLimit(stageLimit);

		proxy.systemUpdateLimit(trxValue);
		DefaultLogger.debug(this, "Limit updated:" + limit.getLimitID());
	}

	/*
	 * Method to compute total activated limits
	 */
	private Amount computeTotalActivatedLimits(ILimit[] limitList) throws Exception {
		Amount total = new Amount(0, baseCurrency);

		for (int i = 0; i < limitList.length; i++) {
			ILimit limit = limitList[i];
			total = total.add(computeTotalActivatedLimits(limit, limitList, true));
		}
		return total;
	}

	/**
	 * Method to compute total activated limits for 1 limit. exclude inner
	 * should be true if computing for total activated limit due to a limit,
	 * because the method will take care of inner limits. however the flag
	 * should be false when computing limit threshold, as it's possible for
	 * inner limits to be linked to collateral.
	 */
	private Amount computeTotalActivatedLimits(ILimit limit, ILimit[] limitList, boolean excludeInner) throws Exception {
		if (excludeInner) {
			if ((limit.getOuterLimitID() != ICMSConstant.LONG_MIN_VALUE) && (limit.getOuterLimitID() != 0)) {
				return new Amount(0, baseCurrency); // i.e. exclude this since
				// it's an inner limit
			}
		}

		Amount outActAmt = limit.getActivatedLimitAmount(); // outer limit
		// activated limit
		if (isSecuredByCommodity(limit)) {
			outActAmt = limit.getOperationalLimit();
		}
		Amount outApprAmt = convert(limit.getApprovedLimitAmount()); // outer
		// limit
		// approved
		// amount

		ArrayList aList = new ArrayList();
		long limitID = limit.getLimitID();
		for (int i = 0; i < limitList.length; i++) {
			ILimit temp = limitList[i];
			if (temp.getOuterLimitID() == limitID) {
				aList.add(temp); // add inner limits
			}
		}
		Amount innerTotal = new Amount(0, baseCurrency);
		if (aList.size() > 0) {
			// compute total inner limits
			Iterator it = aList.iterator();
			while (it.hasNext()) {
				ILimit inner = (ILimit) it.next();
				Amount innerAmt = inner.getActivatedLimitAmount();
				if (isSecuredByCommodity(inner)) {
					innerAmt = inner.getOperationalLimit();
				}
				if (isNullAmount(innerAmt)) {
					continue; // ignore it
				}
				else {
					innerAmt = convert(innerAmt);
					innerTotal = innerTotal.add(innerAmt);
				}
			}
		}

		Amount actualAmt = null;

		if (isNullAmount(outActAmt)) { // limit has not been activated
			if (isFirstAmtHigherEqual(outApprAmt, innerTotal)) {
				actualAmt = innerTotal;
			}
			else {
				actualAmt = outApprAmt;
			}
		}
		else { // limit is activated
			outActAmt = convert(outActAmt);
			if (isFirstAmtHigherEqual(outActAmt, innerTotal)) {
				actualAmt = outActAmt;
			}
			else {
				if (isFirstAmtHigherEqual(innerTotal, outApprAmt)) {
					actualAmt = outApprAmt;
				}
				else {
					actualAmt = innerTotal;
				}
			}
		}
		actualAmt = new Amount(actualAmt.getAmountAsBigDecimal(), new CurrencyCode(actualAmt.getCurrencyCode()));
		return actualAmt;
	}

	/**
	 * Helper method to check if amount 1 is greater or equals to amount 2.
	 * 
	 * @param amt1 of type Amount
	 * @param amt2 of type Amount
	 * @return true if amt1 is greater or equals to amt2, otherwise false
	 */
	private boolean isFirstAmtHigherEqual(Amount amt1, Amount amt2) {
		if (amt1.getAmountAsBigDecimal().compareTo(amt2.getAmountAsBigDecimal()) >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Method to do a lower of 2 amount fields. if any field is amount, it'll be
	 * considered to be invalid and excluded.
	 */
	private Amount lowerOfAmount(Amount amt1, Amount amt2) throws Exception {
		if ((null == amt1) && (null == amt2)) {
			return null;
		}
		else if (null == amt1) {
			return amt2;
		}
		else if (null == amt2) {
			return amt1;
		}
		else {
			Amount newAmt1 = new Amount(amt1.getAmount(), amt1.getCurrencyCode());
			Amount newAmt2 = new Amount(amt2.getAmount(), amt2.getCurrencyCode());
			newAmt1 = convert(newAmt1);
			newAmt2 = convert(newAmt2);

			if (newAmt1.getAmount() < newAmt2.getAmount()) {
				return amt1;
			}
			else if (newAmt1.getAmount() > newAmt2.getAmount()) {
				return amt2;
			}
			else {
				return amt1;
			}
		}
	}

	/**
	 * Method to allocation collateral FSV to limit charge
	 */
	public void performChargeAllocation(ICollateral col, CollateralThresholdDAO dao) throws Exception {
		DefaultLogger.debug(this, "Performing Charge Allocation...");
		Amount colAmt = col.getFSV();
		if (isNullAmount(colAmt)) {
			DefaultLogger.debug(this, "Collateral FSV is null. Returning.");
			return; // nothing to do since collateral amount is 0
		}
		else {
			// convert to base currency
			colAmt = convert(colAmt);
		}
		Amount originalAmt = new Amount(colAmt.getAmount(), colAmt.getCurrencyCode());

		ILimitCharge[] chargeList = col.getLimitCharges();
		Arrays.sort(chargeList, new CollateralRankingComparator()); // sort
		// according
		// to rank
		// first
		HashMap chargeMap = new HashMap(); // to store the limit charge ID, and
		// the actual charge amount
		HashMap colMap = new HashMap();
		long collateralID = col.getCollateralID();
		Amount surplus = new Amount(0, baseCurrency);
		// first pass, reset all collateral charges amounts
		resetChargeAllocation(chargeList, dao);

		// next prepare for allocation
		for (int i = 0; i < chargeList.length; i++) {
			ILimitCharge charge = chargeList[i];
			Amount chargeAmt = charge.getChargeAmount();
			if (!(isNullAmount(chargeAmt))) {
				// convert to base currency
				chargeAmt = convert(chargeAmt);
			}
			else {
				continue;
			}
			if (charge.getSecurityRank() == 1) { // special flow for first rank
				Amount balance = new Amount(colAmt.getAmount(), colAmt.getCurrencyCode());
				/*
				 * if(collateralID == 100100776) { System.out.println();
				 * System.out
				 * .println("XXXXXXXXXXXXX balance before surplus at rank 1: " +
				 * balance); System.out.println("XXXXXXXXXXXXX charge amt: " +
				 * chargeAmt); System.out.println("XXXXXXXXXXXXX surplus: " +
				 * surplus); }
				 */
				if (balance.getAmount() < chargeAmt.getAmount()) {
					balance = balance.add(surplus);
					colAmt = new Amount(balance.getAmount(), balance.getCurrencyCode()); // current
					// becomes
					// balance
					surplus = new Amount(0, baseCurrency); // allocate all
					// surplus to
					// balance
				}
				balance = balance.subtract(chargeAmt);

				if (computeLessThan(balance.getAmount(), 0)) {
					surplus = computeSurplus(surplus, charge, colAmt, colMap, collateralID, dao);
					chargeMap.put(new Long(charge.getChargeDetailID()), new Amount(colAmt.getAmount(), colAmt
							.getCurrencyCode()));
					colAmt = new Amount(0, balance.getCurrencyCode()); // reset
					// to 0
					break; // no need to continue since already go below 0
				}
				else {
					colAmt = balance; // to reflect the new balance
					surplus = computeSurplus(surplus, charge, chargeAmt, colMap, collateralID, dao);
					chargeMap.put(new Long(charge.getChargeDetailID()), new Amount(chargeAmt.getAmount(), chargeAmt
							.getCurrencyCode()));
				}
				/*
				 * if(collateralID == 100100776) { System.out.println();
				 * System.out
				 * .println("XXXXXXXXXXXXX balance after surplus at rank 1: " +
				 * balance); System.out.println("XXXXXXXXXXXXX charge amt: " +
				 * chargeAmt); System.out.println("XXXXXXXXXXXXX surplus: " +
				 * surplus); }
				 */
			}
			else {
				Amount priorAmt = charge.getPriorChargeAmount();
				if (!(isNullAmount(priorAmt))) { // not null,need to subtract
					// prior charge from
					// original
					priorAmt = convert(priorAmt);
					Amount balance = new Amount(originalAmt.getAmount(), originalAmt.getCurrencyCode());
					balance = balance.subtract(priorAmt);
					/*
					 * if(collateralID == 100100776) { System.out.println();
					 * System.out.println(
					 * "XXXXXXXXXXXXX balance before surplus at rank x: " +
					 * balance);
					 * System.out.println("XXXXXXXXXXXXX prior charge: " +
					 * priorAmt);
					 * System.out.println("XXXXXXXXXXXXX charge amt: " +
					 * chargeAmt); System.out.println("XXXXXXXXXXXXX surplus: "
					 * + surplus); }
					 */
					if (computeLessThan(balance.getAmount(), 0)) {
						colAmt = new Amount(0, balance.getCurrencyCode()); // implies
						// zero
						// balance
						// prior charge already took up all, can't assign
						// anymore to current charge
						break;
					}
					else {
						/*
						 * if(collateralID == 100100776) { System.out.println();
						 * System
						 * .out.println("XXXXXXXXXXXXX balance after prior charge: "
						 * + balance);
						 * System.out.println("XXXXXXXXXXXXX charge amt is : " +
						 * chargeAmt);
						 * System.out.println("XXXXXXXXXXXXX surplus : " +
						 * surplus); }
						 */
						colAmt = new Amount(balance.getAmount(), balance.getCurrencyCode()); // current
						// becomes
						// balance
						if (balance.getAmount() < chargeAmt.getAmount()) {
							balance = balance.add(surplus);
							colAmt = new Amount(balance.getAmount(), balance.getCurrencyCode()); // to
							// include
							// surplus
							// into
							// col
							// Amt
							/*
							 * if(collateralID == 100100776) {
							 * System.out.println();System.out.println(
							 * "XXXXXXXXXXXXX balance after surplus : " +
							 * balance); }
							 */
							surplus = new Amount(0, baseCurrency);
						}
						balance = balance.subtract(chargeAmt); // next using
						// balance,
						// subtract
						// charge amt
						if (computeLessThan(balance.getAmount(), 0)) {
							surplus = computeSurplus(surplus, charge, colAmt, colMap, collateralID, dao);
							chargeMap.put(new Long(charge.getChargeDetailID()), new Amount(colAmt.getAmount(), colAmt
									.getCurrencyCode())); // assign whatever is
							// available
							colAmt = new Amount(0, balance.getCurrencyCode()); // reset
							// to
							// 0
							break; // no need to continue since already go below
							// 0
						}
						else {
							colAmt = balance; // to reflect the new balance
							surplus = computeSurplus(surplus, charge, chargeAmt, colMap, collateralID, dao);
							chargeMap.put(new Long(charge.getChargeDetailID()), new Amount(chargeAmt.getAmount(),
									colAmt.getCurrencyCode()));
						}
					}
				}
				else {
					// since prior charge is null, use current colAmt as the
					// balance
					DefaultLogger.info(this, "Prior Amount for Charge DetailID: " + charge.getChargeDetailID()
							+ " of collateralID: " + col.getCollateralID() + " is null!");
					Amount balance = new Amount(colAmt.getAmount(), colAmt.getCurrencyCode());
					if (balance.getAmount() < chargeAmt.getAmount()) {
						balance = balance.add(surplus);
						colAmt = new Amount(balance.getAmount(), balance.getCurrencyCode()); // current
						// becomes
						// balance
						surplus = new Amount(0, baseCurrency);
					}
					balance = balance.subtract(chargeAmt);
					if (computeLessThan(balance.getAmount(), 0)) {
						surplus = computeSurplus(surplus, charge, colAmt, colMap, collateralID, dao);
						chargeMap.put(new Long(charge.getChargeDetailID()), new Amount(colAmt.getAmount(), colAmt
								.getCurrencyCode()));
						colAmt = new Amount(0, balance.getCurrencyCode()); // reset
						// to
						// 0
						break; // no need to continue since already go below 0
					}
					else {
						colAmt = balance; // to reflect the new balance
						surplus = computeSurplus(surplus, charge, chargeAmt, colMap, collateralID, dao);
						chargeMap.put(new Long(charge.getChargeDetailID()), new Amount(chargeAmt.getAmount(), colAmt
								.getCurrencyCode()));
					}
				}
			}
		}
		// then persist all the charge allocation via hashmap and dao
		persistChargeAllocation(chargeMap, false, dao);

		// persist the collateral amount as FSV balance. This should be done
		// last as the charge allocation persistence
		// is not using the same connection as collateral persistence.
		// collateral persistence value is a displayable
		// field in UI. as such we don't want to update it unless we're sure the
		// charge allocation persistence is
		// successful
		persistCollateralBalance(col, colAmt, dao);
	}

	/**
	 * Method to compute charge allocation surplus
	 */
	private Amount computeSurplus(Amount surplus, ILimitCharge charge, Amount actual, HashMap colMap,
			long collateralID, CollateralThresholdDAO dao) throws Exception {
		if (ICMSConstant.CHARGE_TYPE_SPECIFIC.equals(charge.getChargeType())) {
			return surplus;
		}

		long chargeDetailID = charge.getChargeDetailID();
		// Amount chargeAmount = charge.getChargeAmount();
		Amount chargeAmount = new Amount(0, baseCurrency);

		ILimitProxy proxy = LimitProxyFactory.getProxy();
		HashMap profileMap = new HashMap();

		HashMap idMap = dao.getLimitAndProfileByCharge(chargeDetailID);
		Iterator it = idMap.keySet().iterator();
		while (it.hasNext()) {
			Long limitID = (Long) it.next();
			Long profileID = (Long) idMap.get(limitID);

			ILimitProfile profile = (ILimitProfile) profileMap.get(profileID);
			if (null == profile) {
				profile = proxy.getLimitProfile(profileID.longValue());
				profileMap.put(profileID, profile);
			}
			ILimit[] limitList = profile.getLimits();
			ILimit limit = null;
			for (int i = 0; i < limitList.length; i++) {
				if (limitID.longValue() == limitList[i].getLimitID()) {
					limit = limitList[i];
					break;
				}
			}
			chargeAmount = chargeAmount.add(computeTotalActivatedLimits(limit, limitList, true));
		}

		// chargeAmount = convert(chargeAmount);
		surplus = convert(surplus);
		actual = convert(actual);

		colMap = dao.getMapByCollateral(colMap, collateralID, chargeDetailID);
		Double coverage = (Double) colMap.get(new Long(chargeDetailID));

		/*
		 * if(collateralID == 100100776) { System.out.println();
		 * System.out.println("XXXXXXXXXXXXX charge amt in compute: " +
		 * chargeAmount);
		 * System.out.println("XXXXXXXXXXXXX surplus in compute: " + surplus);
		 * System.out.println("XXXXXXXXXXXXX  actual in compute: " + actual);
		 * System.out.println("XXXXXXXXXXXXX  coverage: " + coverage); }
		 */

		if ((null == coverage) || (0 == coverage.doubleValue())) {
			coverage = new Double(100); // assume 100% for coverage that is null
			// or 0
		}

		double requiredDouble = (coverage.doubleValue() / 100) * chargeAmount.getAmount();
		double actualDouble = actual.getAmount();

		/*
		 * if(collateralID == 100100776) { System.out.println();
		 * System.out.println("XXXXXXXXXXXXX requiredDouble: " +
		 * requiredDouble); System.out.println("XXXXXXXXXXXXX actualDouble: " +
		 * actualDouble); }
		 */
		if (actualDouble > requiredDouble) {
			surplus = surplus.add(new Amount((actualDouble - requiredDouble), baseCurrency));
		}
		/*
		 * if(collateralID == 100100776) {
		 * System.out.println("XXXXXXXXXXXXX new surplus: " + surplus); }
		 */
		return surplus;
	}

	/**
	 * Method to reset all charge allocation data
	 */
	private void resetChargeAllocation(ILimitCharge[] chargeList, CollateralThresholdDAO dao) throws Exception {
		HashMap chargeMap = new HashMap();
		for (int i = 0; i < chargeList.length; i++) {
			ILimitCharge charge = chargeList[i];
			chargeMap.put(new Long(charge.getChargeDetailID()), new Amount(0, baseCurrency)); // reset
			// to
			// zero
		}
		persistChargeAllocation(chargeMap, true, dao);
	}

	/**
	 * Method to persist charge Allocation
	 */
	private void persistChargeAllocation(HashMap chargeMap, boolean reset, CollateralThresholdDAO dao) throws Exception {
		DefaultLogger.debug(this, "Persisting Charge Allocation with " + chargeMap.size() + " number of records.");

		dao.updateActualChargeAmount(chargeMap, reset);
	}

	/**
	 * Optimized Method to persist the collateral balance value
	 */
	private void persistCollateralBalance(ICollateral col, Amount colAmt, CollateralThresholdDAO dao) throws Exception {
		if (colAmt.getAmount() < 0) {
			colAmt.setAmount(0);
		}
		dao.updateSTGFSVAmount(col, colAmt);
		dao.updateFSVAmount(col, colAmt);
	}

	/**
	 * Method to persist the collateral balance value
	 */
	private void persistCollateralBalance(ICollateral col, Amount colAmt) throws Exception {
		if (colAmt.getAmount() < 0) {
			colAmt.setAmount(0);
		}

		ITrxContext context = new OBTrxContext();

		ICollateralProxy proxy = CollateralProxyFactory.getProxy();
		ICollateralTrxValue trxValue = proxy.getCollateralTrxValue(context, col.getCollateralID());

		ICollateral newCol = trxValue.getCollateral();
		newCol.setFSVBalance(colAmt);
		trxValue.setCollateral(newCol);

		ICollateral stageCol = trxValue.getStagingCollateral();
		if (stageCol != null) {
			stageCol.setFSVBalance(colAmt);
			trxValue.setStagingCollateral(stageCol);
		}

		proxy.systemUpdateCollateral(context, trxValue);
	}

	/**
	 * Method to identify if an amount object is null
	 * 
	 * @return boolean
	 */
	private boolean isNullAmount(Amount amt) {
		if (null == amt) {
			return true;
		}
		else {
			if ((amt.getCurrencyCode() == null) && (amt.getAmount() == 0)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	/**
	 * Private method to help to convert amount objects
	 */
	private Amount convert(Amount amt) throws Exception {
		if (amt.getCurrencyCode().equals(baseCurrency)) {
			return amt;
		}
		else {
			SBForexManager fx = getSBForexManager();
			Amount result = fx.convert(amt, new CurrencyCode(baseCurrency));
			if (null == result) {
				throw new Exception("Forex conversion returns null, for FROM Currency: " + amt.getCurrencyCode()
						+ " and TO Currency: " + baseCurrency);
			}
			else {
				return result;
			}
		}
	}

	/**
	 * Method to compute less than, given a variance for possible element of
	 * errors due to exchange conversion
	 */
	private boolean computeLessThan(double subtractFrom, double subtractUsing) {
		if (subtractFrom >= subtractUsing) {
			return false;
		}
		else {
			double diff = (subtractFrom - subtractUsing) * -1; // to make
			// positive
			if (diff <= variance) { // not considered less than since difference
				// is less than 1
				return false;
			}
			else {
				return true;
			}
		}
	}

	/**
	 * Helper method to check if a limit is fully or partially secured by
	 * commodities.
	 * 
	 * @param limit of type ILimit
	 * @return true if the limit is secured fully or partially by commodities,
	 *         otherwise false
	 */
	private boolean isSecuredByCommodity(ILimit limit) {
		ICollateralAllocation[] alloc = limit.getCollateralAllocations();
		if (alloc != null) {
			for (int i = 0; i < alloc.length; i++) {
				ICollateral col = alloc[i].getCollateral();
				if (col instanceof ICommodityCollateral) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Helper method to return the forex session bean
	 * 
	 * @return SBForexManager - the remote handler for the forex manager session
	 *         bean
	 * @throws Exception for any errors encountered
	 */
	private SBForexManager getSBForexManager() throws Exception {
		SBForexManager mgr = (SBForexManager) BeanController.getEJB(ICMSJNDIConstant.SB_FOREX_MANAGER_JNDI,
				SBForexManagerHome.class.getName());
		if (mgr == null) {
			throw new Exception("SBForexManager is null!");
		}
		return mgr;
	}

      /**
       * Get a list of Distinct Outer Limit ID belong to the given Limit Profile ID.
       *
       * @param limitProfileId is of type long
       * @return a list of Distinct Outer Limit ID
       * @throws LimitException on error getting the Outer Limit ID
       */
      public String[] getDistinctOuterLimitID (long limitProfileId) throws LimitException {
          return new LimitDAO().getDistinctOuterLimitID (limitProfileId);
      }
}
