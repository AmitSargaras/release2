/**
 * This interface represents an Limit Profile.
 *
 * @author $Author: jzhan $
 * @version $Revision: 1.5 $
 * @since $Date: 2006/05/24 09:51:29 $
 * Tag: $Name:  $
 */
package com.integrosys.cms.app.eventmonitor.seccoverage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecCoverageCalculator {

	public List getLimitProfileIdWithPsccOrScc() throws Exception {
		try {
			SecCoverageCalDAO dao = new SecCoverageCalDAO();
			return dao.getLimitProfileIdWithPsccOrScc();
		}
		catch (Exception ex) {
			throw ex;
		}
	}

	public void calculateSecurityCoverage(String limitId) throws Exception {
		try {
			SecCoverageCalDAO dao = new SecCoverageCalDAO();

			// clear up data first?
			dao.cleanUp(limitId);

			// first get a map of limitProfiles (outer limit) for the client
			Map outerLimitMap = getOuterLimitsForBCA(dao, limitId);

			// get a list of outer limit id from the outerLimitMap
			List outerLimitIdList = getOuterLimitId(outerLimitMap);

			// get a map contains securities tied to the list of outer limit
			Map securityInfoModelMap = getSecurityCoverageInfoModels(dao, outerLimitIdList);

			// initialize various data needed for calculation,
			prepareCalculation(securityInfoModelMap, outerLimitMap);

			// calculate the security coverage for each security
			Iterator iter = securityInfoModelMap.values().iterator();
			while (iter.hasNext()) {
				OBSecCovSecurityInfo nextSecurity = (OBSecCovSecurityInfo) (iter.next());
				calculateCoverageForSecurity(nextSecurity, outerLimitMap);
			}
			updateActualSecCoverageForLimit(dao, outerLimitMap);

			// update the fsv balance for each security
			updateFSVBalance(dao, securityInfoModelMap);

			// calculate the BCA level level security coverage
			double bcaSecurityCoverage = calculateCoverageForBCA(limitId, securityInfoModelMap, outerLimitMap);
			updateActualSecCoverageForBCA(dao, limitId, bcaSecurityCoverage);
		}
		catch (Exception ex) {
			throw ex;
		}
	}

	private Map getOuterLimitsForBCA(SecCoverageCalDAO dao, String limitId) throws Exception {
		return dao.getActiveLimitProfileByLimit(limitId);
	}

	private List getOuterLimitId(Map outerLimitMap) {
		// DefaultLogger.debug(this, "Inside getOuterLimitId");
		List result = new ArrayList();
		Object[] keyArr = outerLimitMap.keySet().toArray();
		for (int i = 0; i < keyArr.length; i++) {
			// DefaultLogger.debug(this, "Next outer limit id is: " +
			// keyArr[i]);
			result.add(keyArr[i]);
		}
		return result;
	}

	private Map getSecurityCoverageInfoModels(SecCoverageCalDAO dao, List outerLimitIdList) throws Exception {
		return dao.getSecurityCoverageInfoModels(outerLimitIdList);
	}

	private void prepareCalculation(Map securityInfoModelMap, Map outerLimitMap) {
		DefaultLogger.debug(this, "Inside prepare calculation");
		// first we calculate activate amount for each outerLimit
		calculateActivateAmount(outerLimitMap);

		Iterator iter = securityInfoModelMap.values().iterator();
		while (iter.hasNext()) {
			OBSecCovSecurityInfo nextSecurity = (OBSecCovSecurityInfo) (iter.next());
			// first we calculate the FSV balance of the security, which is
			// just the FSV amount converted to base currency
			double fsvBalance = 0;
			try {
				fsvBalance = SecCoverageCalUtil.convertToBase(nextSecurity.getFsv(), nextSecurity.getFsvCurrency());
			}
			catch (Exception ex) {
				DefaultLogger.error(this, "Error in calculating FSV balance for security "
						+ nextSecurity.getCollateralID());
			}
			DefaultLogger.debug(this, "Total FSV balance for security " + nextSecurity.getCollateralID() + " is "
					+ fsvBalance);
			nextSecurity.setFsvBalance(fsvBalance);

			// next compute the total limit amount under this security
			double totalLimitAmount = calculateTotalLimitAmount(nextSecurity, outerLimitMap);
			DefaultLogger.debug(this, "Total limit amount for security " + nextSecurity.getCollateralID() + " is "
					+ totalLimitAmount);
			nextSecurity.setTotalLimitAmount(totalLimitAmount);

			// next compute the general charge surplus, which is the sum of all
			// charge amount for general charge
			double genChargeSurplus = calculateGenChargeSurplus(nextSecurity, outerLimitMap);
			DefaultLogger.debug(this, "General charge surplus for security " + nextSecurity.getCollateralID() + " is "
					+ genChargeSurplus);
			nextSecurity.setGenChargeSurplus(genChargeSurplus);
		}
	}

	private double calculateTotalLimitAmount(OBSecCovSecurityInfo securityInfoModel, Map outerLimitMap) {
		double totalLimitAmount = 0;
		List chargeList = securityInfoModel.getCharges();
		if (chargeList != null) {
			for (int i = 0; i < chargeList.size(); i++) {
				OBSecCovChargeInfo nextChargeModel = (OBSecCovChargeInfo) (chargeList.get(i));
				OBActivateLimitInfo limitInfoModel = (OBActivateLimitInfo) (outerLimitMap.get(nextChargeModel
						.getLimitID()));
				totalLimitAmount = totalLimitAmount + limitInfoModel.getActivateLmtDerived();
			}
		}
		return totalLimitAmount;
	}

	private void calculateActivateAmount(Map outerLimitMap) {
		Iterator iter = outerLimitMap.values().iterator();
		while (iter.hasNext()) {
			OBActivateLimitInfo nextInfoModel = (OBActivateLimitInfo) (iter.next());
			double val = SecCoverageCalUtil.calculateActivedAmount(nextInfoModel);
			nextInfoModel.setActivateLmtDerived(val);
		}
	}

	private double calculateGenChargeSurplus(OBSecCovSecurityInfo securityInfoModel, Map outerLimitMap) {
		double totalSurplus = 0;
		List chargeList = securityInfoModel.getCharges();
		List existingIds = new ArrayList();
		if (chargeList != null) {
			for (int i = 0; i < chargeList.size(); i++) {
				OBSecCovChargeInfo nextChargeModel = (OBSecCovChargeInfo) (chargeList.get(i));
				if (ICMSConstant.CHARGE_TYPE_GENERAL.equals(nextChargeModel.getChargeType())) {
					if (!existingIds.contains(nextChargeModel.getChargeDetailID())) {
						double nextChargeAmount = 0;
						try {
							nextChargeAmount = SecCoverageCalUtil.convertToBase(nextChargeModel.getChargeAmount(),
									nextChargeModel.getChargeCurrency());
						}
						catch (Exception ex) {
							DefaultLogger.error(this, "Error in calculating charge amount for charge "
									+ nextChargeModel.getChargeDetailID());
						}
						totalSurplus = totalSurplus + nextChargeAmount;
						existingIds.add(nextChargeModel.getChargeDetailID());
					}
				}
			}
		}
		return totalSurplus;
	}

	private void calculateCoverageForSecurity(OBSecCovSecurityInfo securityInfoModel, Map outerLimitMap) {
		DefaultLogger.debug(this, "Inside calculateCoverageForSecurity");
		String securityId = securityInfoModel.getCollateralID();
		double fsvBalance = securityInfoModel.getFsvBalance();
		double genChargeSurplus = securityInfoModel.getGenChargeSurplus();
		double totalLimitAmount = securityInfoModel.getTotalLimitAmount();
		double secLevelSecurityCoverage = securityInfoModel.getSecLevelSecurityCoverage(); // 0
																							// at
																							// start
		List chargeList = securityInfoModel.getCharges();
		if ((chargeList != null) && (chargeList.size() > 0)) {
			if (chargeList.size() == 1) {
				// only one limit for the security
				OBSecCovChargeInfo chargeInfoModel = (OBSecCovChargeInfo) (chargeList.get(0));
				if (ICMSConstant.CHARGE_TYPE_GENERAL.equals(chargeInfoModel.getChargeType())) {
					// DefaultLogger.debug(this, "Charge " +
					// chargeInfoModel.getChargeDetailID() +
					// " is general charge");
					fsvBalance = fsvBalance - getPriorChargeAmount(chargeList, chargeInfoModel);
					double activateLimitAmount = getActivateLimitAmount(chargeInfoModel.getLimitID(), outerLimitMap);
					double actualCoveredAmount = Math.min(fsvBalance, genChargeSurplus);
					double actualSecCoverage = (activateLimitAmount == 0 ? 0
							: (actualCoveredAmount / activateLimitAmount));
					// DefaultLogger.debug(this,
					// "Activate Limit Amount for limit " +
					// chargeInfoModel.getLimitID() + " is " +
					// activateLimitAmount);
					// DefaultLogger.debug(this,
					// "Actual Covered Amount for limit " +
					// chargeInfoModel.getLimitID() + " is " +
					// actualCoveredAmount);
					saveActualSecurityCoverage(securityId, chargeInfoModel.getLimitID(), outerLimitMap,
							actualSecCoverage);
					double secLevelApportionment = (totalLimitAmount == 0 ? 0
							: (actualCoveredAmount / totalLimitAmount));
					secLevelSecurityCoverage = secLevelSecurityCoverage + secLevelApportionment;
					saveSecLevelApportionment(securityId, chargeInfoModel.getLimitID(), outerLimitMap,
							secLevelApportionment);
					fsvBalance = fsvBalance - actualCoveredAmount;
					genChargeSurplus = genChargeSurplus - actualCoveredAmount;
				}
				else if (ICMSConstant.CHARGE_TYPE_SPECIFIC.equals(chargeInfoModel.getChargeType())) {
					// DefaultLogger.debug(this, "Charge " +
					// chargeInfoModel.getChargeDetailID() +
					// " is specific charge");
					fsvBalance = fsvBalance - getPriorChargeAmount(chargeList, chargeInfoModel);
					double chargeAmount = 0;
					try {
						chargeAmount = SecCoverageCalUtil.convertToBase(chargeInfoModel.getChargeAmount(),
								chargeInfoModel.getChargeCurrency());
					}
					catch (Exception ex) {
						DefaultLogger.error(this, "Error converting charge amount in calculateCoverageForSecurity");
					}
					double activateLimitAmount = getActivateLimitAmount(chargeInfoModel.getLimitID(), outerLimitMap);
					double actualCoveredAmount = Math.min(fsvBalance, chargeAmount);
					double actualSecCoverage = (activateLimitAmount == 0 ? 0
							: (actualCoveredAmount / activateLimitAmount));
					// DefaultLogger.debug(this,
					// "Activate Limit Amount for limit " +
					// chargeInfoModel.getLimitID() + " is " +
					// activateLimitAmount);
					// DefaultLogger.debug(this,
					// "Actual Covered Amount for limit " +
					// chargeInfoModel.getLimitID() + " is " +
					// actualCoveredAmount);
					saveActualSecurityCoverage(securityId, chargeInfoModel.getLimitID(), outerLimitMap,
							actualSecCoverage);

					// here if actual covered amount >= limit amount, we take
					// limit amount so that the BCA level coverage will not be
					// more than 100%
					double tempAmount = actualCoveredAmount;
					if (actualCoveredAmount >= activateLimitAmount) {
						tempAmount = activateLimitAmount;
					}
					double secLevelApportionment = (totalLimitAmount == 0 ? 0 : (tempAmount / totalLimitAmount));
					secLevelSecurityCoverage = secLevelSecurityCoverage + secLevelApportionment;
					saveSecLevelApportionment(securityId, chargeInfoModel.getLimitID(), outerLimitMap,
							secLevelApportionment);
					fsvBalance = fsvBalance - actualCoveredAmount;
				}
			}
			else {
				// compute charge by charge
				for (int i = 0; i < chargeList.size(); i++) {
					OBSecCovChargeInfo chargeInfoModel = (OBSecCovChargeInfo) (chargeList.get(i));
					if (ICMSConstant.CHARGE_TYPE_GENERAL.equals(chargeInfoModel.getChargeType())) {
						// DefaultLogger.debug(this, "Charge " +
						// chargeInfoModel.getChargeDetailID() +
						// " is general charge");
						fsvBalance = fsvBalance - getPriorChargeAmount(chargeList, chargeInfoModel);
						double activateLimitAmount = getActivateLimitAmount(chargeInfoModel.getLimitID(), outerLimitMap);
						double actualCoveredAmount = Math.min(activateLimitAmount, Math.min(fsvBalance,
								genChargeSurplus));
						double actualSecCoverage = (activateLimitAmount == 0 ? 0
								: (actualCoveredAmount / activateLimitAmount));
						// DefaultLogger.debug(this,
						// "Activate Limit Amount for limit " +
						// chargeInfoModel.getLimitID() + " is " +
						// activateLimitAmount);
						// DefaultLogger.debug(this,
						// "Actual Covered Amount for limit " +
						// chargeInfoModel.getLimitID() + " is " +
						// actualCoveredAmount);
						saveActualSecurityCoverage(securityId, chargeInfoModel.getLimitID(), outerLimitMap,
								actualSecCoverage);
						double secLevelApportionment = (totalLimitAmount == 0 ? 0
								: (actualCoveredAmount / totalLimitAmount));
						secLevelSecurityCoverage = secLevelSecurityCoverage + secLevelApportionment;
						saveSecLevelApportionment(securityId, chargeInfoModel.getLimitID(), outerLimitMap,
								secLevelApportionment);
						fsvBalance = fsvBalance - actualCoveredAmount;
						genChargeSurplus = genChargeSurplus - actualCoveredAmount;
					}
					else if (ICMSConstant.CHARGE_TYPE_SPECIFIC.equals(chargeInfoModel.getChargeType())) {
						// DefaultLogger.debug(this, "Charge " +
						// chargeInfoModel.getChargeDetailID() +
						// " is specific charge");
						fsvBalance = fsvBalance - getPriorChargeAmount(chargeList, chargeInfoModel);
						double chargeAmount = 0;
						try {
							chargeAmount = SecCoverageCalUtil.convertToBase(chargeInfoModel.getChargeAmount(),
									chargeInfoModel.getChargeCurrency());
						}
						catch (Exception ex) {
							DefaultLogger.error(this, "Error converting charge amount in calculateCoverageForSecurity");
						}
						double activateLimitAmount = getActivateLimitAmount(chargeInfoModel.getLimitID(), outerLimitMap);
						double actualCoveredAmount = Math.min(activateLimitAmount, Math.min(fsvBalance, chargeAmount));
						double actualSecCoverage = (activateLimitAmount == 0 ? 0
								: (actualCoveredAmount / activateLimitAmount));
						// DefaultLogger.debug(this,
						// "Activate Limit Amount for limit " +
						// chargeInfoModel.getLimitID() + " is " +
						// activateLimitAmount);
						// DefaultLogger.debug(this,
						// "Actual Covered Amount for limit " +
						// chargeInfoModel.getLimitID() + " is " +
						// actualCoveredAmount);
						saveActualSecurityCoverage(securityId, chargeInfoModel.getLimitID(), outerLimitMap,
								actualSecCoverage);
						double secLevelApportionment = (totalLimitAmount == 0 ? 0
								: (actualCoveredAmount / totalLimitAmount));
						secLevelSecurityCoverage = secLevelSecurityCoverage + secLevelApportionment;
						saveSecLevelApportionment(securityId, chargeInfoModel.getLimitID(), outerLimitMap,
								secLevelApportionment);
						fsvBalance = fsvBalance - actualCoveredAmount;
					}
				}
			}
			// save back the fsv and charge surplus
			DefaultLogger.debug(this, "Final balance of fsv for security: " + securityId + " is " + fsvBalance);
			DefaultLogger
					.debug(this, "Final gen charge surplus for security " + securityId + " is " + genChargeSurplus);
			securityInfoModel.setFsvBalance(fsvBalance);
			securityInfoModel.setGenChargeSurplus(genChargeSurplus);

			// if we still have surplus we can add back to the security level
			// actual security coverage
			double finalSurplus = Math.min(fsvBalance, genChargeSurplus);
			secLevelSecurityCoverage = secLevelSecurityCoverage
					+ (totalLimitAmount == 0 ? 0 : (finalSurplus / totalLimitAmount));
			DefaultLogger.debug(this, "Final security level actual coverage for security: " + securityId + " is "
					+ secLevelSecurityCoverage);
			securityInfoModel.setSecLevelSecurityCoverage(secLevelSecurityCoverage);
		}
	}

	private double calculateCoverageForBCA(String limitId, Map securityInfoModelMap, Map outerLimitMap) {
		DefaultLogger.debug(this, "Inside calculateCoverageForBCA");
		double result = 0;
		double totalLimitAmountForBCA = 0;
		// calculate the total limit amount for BCA by sum of all the total
		// amount at security level
		// note that a limit may be shared across security, when calculate
		// total, we should only count
		// it once
		List countedLimitId = new ArrayList();
		Iterator iter = securityInfoModelMap.values().iterator();
		while (iter.hasNext()) {
			OBSecCovSecurityInfo nextSecurity = (OBSecCovSecurityInfo) (iter.next());
			List chargeList = nextSecurity.getCharges();
			if (chargeList != null) {
				for (int i = 0; i < chargeList.size(); i++) {
					OBSecCovChargeInfo nextCharge = (OBSecCovChargeInfo) (chargeList.get(i));
					String curLimitId = nextCharge.getLimitID();
					if (!countedLimitId.contains(curLimitId)) {
						totalLimitAmountForBCA = totalLimitAmountForBCA
								+ getActivateLimitAmount(curLimitId, outerLimitMap);
						countedLimitId.add(curLimitId);
					}
				}
			}
		}

		// do apportionment at BCA level
		iter = securityInfoModelMap.values().iterator();
		while (iter.hasNext()) {
			OBSecCovSecurityInfo nextSecurity = (OBSecCovSecurityInfo) (iter.next());
			double ratio = (totalLimitAmountForBCA == 0 ? 0 : nextSecurity.getTotalLimitAmount()
					/ totalLimitAmountForBCA);
			result = result + ratio * nextSecurity.getSecLevelSecurityCoverage();
		}
		result = result * 100;
		DefaultLogger.debug(this, "Final actual security coverage for BCA: " + limitId + " is " + result);
		return result;
	}

	private double getPriorChargeAmount(List chargeList, OBSecCovChargeInfo curCharge) {
		return 0;
	}

	private double getActivateLimitAmount(String limitId, Map outerLimitMap) {
		OBActivateLimitInfo limitInfoModel = (OBActivateLimitInfo) (outerLimitMap.get(limitId));
		return limitInfoModel.getActivateLmtDerived();
	}

	private void saveActualSecurityCoverage(String securityId, String limitId, Map outerLimitMap,
			double actualSecCoverage) {
		OBActivateLimitInfo limitInfoModel = (OBActivateLimitInfo) (outerLimitMap.get(limitId));
		limitInfoModel.addSecurityCoverageForSec(securityId, actualSecCoverage);
		// DefaultLogger.debug(this, "Save actual security coverage for limit: "
		// + limitId + " security: " + securityId + " value: " +
		// actualSecCoverage);
	}

	private void saveSecLevelApportionment(String securityId, String limitId, Map outerLimitMap,
			double secLevelApportionment) {
		OBActivateLimitInfo limitInfoModel = (OBActivateLimitInfo) (outerLimitMap.get(limitId));
		limitInfoModel.addApportionmentForSec(securityId, secLevelApportionment);
		// DefaultLogger.debug(this, "Save sec level apportionment for limit: "
		// + limitId + " security: " + securityId + " value: " +
		// secLevelApportionment);
	}

	private void updateActualSecCoverageForLimit(SecCoverageCalDAO dao, Map outerLimitMap) throws Exception {
		Iterator iter = outerLimitMap.values().iterator();
		while (iter.hasNext()) {
			OBActivateLimitInfo nextInfoModel = (OBActivateLimitInfo) (iter.next());
			dao.updateLmtProfActualSecCoverage(String.valueOf(nextInfoModel.getActualSecCoverageForLimit()),
					nextInfoModel.getLimitID());
			dao.updateStgLmtProfActualSecCoverage(String.valueOf(nextInfoModel.getActualSecCoverageForLimit()),
					nextInfoModel.getLimitID());
		}
	}

	private void updateFSVBalance(SecCoverageCalDAO dao, Map securityInfoModelMap) throws Exception {
		Iterator iter = securityInfoModelMap.values().iterator();
		while (iter.hasNext()) {
			OBSecCovSecurityInfo nextSecurity = (OBSecCovSecurityInfo) (iter.next());
			dao.updateStgSecFsvBalance(String.valueOf(nextSecurity.getFsvBalance()), SecCoverageCalUtil.baseCurrency,
					nextSecurity.getCollateralID());
			dao.updateSecFsvBalance(String.valueOf(nextSecurity.getFsvBalance()), SecCoverageCalUtil.baseCurrency,
					nextSecurity.getCollateralID());
		}
	}

	private void updateActualSecCoverageForBCA(SecCoverageCalDAO dao, String limitId, double bcaSecCoverage)
			throws Exception {
		dao.updateBCAActualSecCoverage(String.valueOf(bcaSecCoverage), limitId);
		dao.updateStgBCAActualSecCoverage(String.valueOf(bcaSecCoverage), limitId);
	}

	public List getLimitProfileIdByLE(List leIdList) throws Exception {
		SecCoverageCalDAO dao = new SecCoverageCalDAO();
		return dao.getLimitProfileIdByLE(leIdList);
	}

}
