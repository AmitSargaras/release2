package com.integrosys.cms.app.collateral.bus.valuation.valuator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.IValuator;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationDetailIncompleteException;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.PropertyValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.support.PropertyProfileSingleton;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.propertyindex.bus.OBPropertyIndexParam;

/**
 * Valuator to be used to valuate property collateral, based on property index
 * parameter.
 * 
 * @author Andy Wong
 * @author Cynthia Zhou
 * @author Chong Jun Yong
 */
public class PropertyIndexValuator implements IValuator {

	private final static Logger logger = LoggerFactory.getLogger(PropertyIndexValuator.class);

	private PropertyProfileSingleton propertyProfileSingleton;

	/**
	 * To indicate whether to assess all the fully matched property indexes,
	 * else, according to the order of <tt>propertyIndexValType</tt>, once index
	 * is found, it will not care about the rest.
	 */
	private boolean isAssessAllFullyMatchedPropertyIndex = false;

	/**
	 * To indicator whether to take latest property index maintained if the base
	 * year exceed the latest property index maintained year.
	 */
	private boolean isTakingLatestPropertyIndexIfBaseYearExceed = false;

	/**
	 * Default index valuation type sequence when getting matching property
	 * index, ie ISTP, ITP, ID, IS, IRH.
	 */
	private static final List propertyIndexValType = new ArrayList();

	static {
		propertyIndexValType.add(ICMSConstant.PROP_VAL_DESCR_ISTP);
		propertyIndexValType.add(ICMSConstant.PROP_VAL_DESCR_ITP);
		propertyIndexValType.add(ICMSConstant.PROP_VAL_DESCR_ID);
		propertyIndexValType.add(ICMSConstant.PROP_VAL_DESCR_IS);
		propertyIndexValType.add(ICMSConstant.PROP_VAL_DESCR_IRH);
	}

	/**
	 * @param propertyProfileSingleton the propertyProfileSingleton to set
	 */
	public void setPropertyProfileSingleton(PropertyProfileSingleton propertyProfileSingleton) {
		this.propertyProfileSingleton = propertyProfileSingleton;
	}

	public PropertyIndexValuator() {
	}

	/**
	 * Constructor to state that whether to scan through all the fully matched
	 * property index (ie, include the matching criteria, <em>Year</em>), and
	 * also to state that whether to take latest property index maintained if
	 * the base year exceed the latest property index maintained year.
	 * @param isAssessAllFullyMatchedPropertyIndex whether to scan through all
	 *        the fully matched property index
	 * @param isTakingLatestPropertyIndexIfBaseYearExceed whether to take latest
	 *        property index maintained if the base year exceed the latest
	 *        property index maintained year.
	 */
	public PropertyIndexValuator(boolean isAssessAllFullyMatchedPropertyIndex,
			boolean isTakingLatestPropertyIndexIfBaseYearExceed) {
		this.isAssessAllFullyMatchedPropertyIndex = isAssessAllFullyMatchedPropertyIndex;
		this.isTakingLatestPropertyIndexIfBaseYearExceed = isTakingLatestPropertyIndexIfBaseYearExceed;
	}

	/**
	 * Constructor to state that whether to scan through all the fully matched
	 * property index (ie, include the matching criteria, <em>Year</em>), and
	 * also to state that whether to take latest property index maintained if
	 * the base year exceed the latest property index maintained year, and also
	 * provide the sequence of index valuation type when checking the possible
	 * matched (without checking on year) property index.
	 * @param isAssessAllFullyMatchedPropertyIndex whether to scan through all
	 *        the fully matched property index
	 * @param isTakingLatestPropertyIndexIfBaseYearExceed whether to take latest
	 *        property index maintained if the base year exceed the latest
	 *        property index maintained year.
	 * @param indexValuationTypeSequences the sequence of the index valuation
	 *        type
	 */
	public PropertyIndexValuator(boolean isAssessAllFullyMatchedPropertyIndex,
			boolean isTakingLatestPropertyIndexIfBaseYearExceed, String[] indexValuationTypeSequences) {
		this.isAssessAllFullyMatchedPropertyIndex = isAssessAllFullyMatchedPropertyIndex;
		propertyIndexValType.clear();
		propertyIndexValType.addAll(Arrays.asList(indexValuationTypeSequences));
	}

	public boolean checkCompleteForVal(IValuationModel model, List errorDesc) {
		PropertyValuationModel pValModel = (PropertyValuationModel) model;
		if (pValModel.getStateCode() == null) {
			errorDesc.add("state code is not defined");
			return false;
		}
		if (pValModel.getSpValue() == null) {
			errorDesc.add("SP value/latest CMV is not defined");
			return false;
		}
		if (pValModel.getSpDate() == null) {
			errorDesc.add("SP date/latest CMV date is not defined");
			return false;
		}

		if (pValModel.getPropertyCompletionStatus() == null) {
			errorDesc.add("Property completion status is not define");
			return false;
		}
		//else if (!(pValModel.getPropertyCompletionStatus().equals(ICMSConstant.PROP_COMPLETE_STATUS_W_COF_ISSUANCE) || pValModel
		//		.getPropertyCompletionStatus().equals(ICMSConstant.PROP_COMPLETE_STATUS_WO_COF_ISSUANCE))) {
        else if (!pValModel.getPropertyCompletionStatus().equals(ICMSConstant.PROP_COMPLETE_STATUS_W_COF_ISSUANCE)) {
			//errorDesc.add("Property completion status is not 'Completed with CF' or 'Completed without CF'");
            errorDesc.add("Property completion status is not 'Completed with CF'");
			return false;
		}

		return true;
	}

	public void performValuation(IValuationModel model) throws ValuationException {
		PropertyValuationModel pValModel = (PropertyValuationModel) model;
		Amount currentOmv = pValModel.getSpValue();

		OBPropertyIndexParam param = (OBPropertyIndexParam) findMatchingPropertyIndex(pValModel);
		if (param != null) {
			double newAmt = currentOmv.getAmount()
					* (param.getCurrIndex().doubleValue() / param.getIndex().doubleValue());
			pValModel.setValuationDate(CommonUtil.getCurrentDate());
			pValModel.setValOMV(new Amount(newAmt, currentOmv.getCurrencyCode()));
		}
		else {
			// do not update next valuation date if property index not found
			List errorList = new ArrayList();
			errorList.add("no matching property index profiles are found");
			throw new ValuationDetailIncompleteException("no matching property index profiles are found", errorList);
		}
	}

	protected OBPropertyIndexParam findMatchingPropertyIndex(PropertyValuationModel pValModel) {
		boolean found = false;
		OBPropertyIndexParam matchPropIndexParam = null;
		Map profiles = this.propertyProfileSingleton.getPiProfilesMap();

		int baseYear = CommonUtil.getYear(pValModel.getSpDate());
		String quarterCode = CommonUtil.getQuarterCode(pValModel.getSpDate());

		List allFullyMatchedIndexes = new ArrayList();
		List possibleMatchedIndexes = new ArrayList();
		List matchIndexList = new ArrayList();

		logger.debug("Looking up matching Property Index, CMS collateral id = [" + pValModel.getCollateralId()
				+ "], Collateral Subtype = [" + pValModel.getSecSubtype() + "], Category of land use = ["
				+ pValModel.getCategoryOfLandUse() + "], State code = [" + pValModel.getStateCode() + "], District = ["
				+ pValModel.getDistrictCode() + "], Mukim = [" + pValModel.getMukimCode() + "], Based Year = ["
				+ baseYear + "], Quarter Code = [" + quarterCode + "]");

		for (int i = 0; i < propertyIndexValType.size(); i++) {
			String propValType = (String) propertyIndexValType.get(i);

			List propIndexList = (List) profiles.get(propValType);
			if (propIndexList == null) {
				continue;
			}
			matchIndexList.clear();
			Collections.sort(propIndexList, new SortByYearQuarter());

			// retrieve the base index based on S&P agreement date
			for (Iterator itr = propIndexList.iterator(); itr.hasNext();) {

				OBPropertyIndexParam propIndexParam = (OBPropertyIndexParam) itr.next();

				if ((propValType.equals(ICMSConstant.PROP_VAL_DESCR_ISTP) || propValType
						.equals(ICMSConstant.PROP_VAL_DESCR_ITP))
						&& ((pValModel.getCategoryOfLandUse() == null) || !propIndexParam.getApplicablePropertyTypes()
								.contains(pValModel.getCategoryOfLandUse()))) {
					continue;
				}

				if (!propIndexParam.getApplicableSecSubTypes().contains(pValModel.getSecSubtype())) {
					continue;
				}

				if (propIndexParam.getStateCode() != null
						&& !propIndexParam.getStateCode().equals(pValModel.getStateCode())) {
					continue;
				}

				if (propValType.equals(ICMSConstant.PROP_VAL_DESCR_ISTP)
						|| propValType.equals(ICMSConstant.PROP_VAL_DESCR_ID)) {

					// chk district and mukim
					if ((pValModel.getDistrictCode() != null && !propIndexParam.getApplicableDistricts().contains(
							pValModel.getDistrictCode()))
							|| (pValModel.getDistrictCode() == null && propIndexParam.getApplicableDistricts()
									.contains(pValModel.getDistrictCode()))) {
						continue;
					}

					if ((pValModel.getMukimCode() != null && !propIndexParam.getApplicableMukims().contains(
							pValModel.getMukimCode()))
							|| (pValModel.getMukimCode() == null && propIndexParam.getApplicableMukims().contains(
									pValModel.getMukimCode()))) {
						continue;
					}
				}
				matchIndexList.add(propIndexParam);
				possibleMatchedIndexes.add(propIndexParam);

				// found year match
				if (propIndexParam.getYear() == baseYear) {

					// get the base index must be exact match by quarter code,
					// if quarter code not match then take the annual index
					if ((!found || this.isAssessAllFullyMatchedPropertyIndex)
							&& propIndexParam.getQuarterCode().equals(quarterCode)) {
						allFullyMatchedIndexes.add(propIndexParam);
						found = true;
						matchPropIndexParam = propIndexParam;
					}
					if ((!found || this.isAssessAllFullyMatchedPropertyIndex)
							&& propIndexParam.getQuarterCode().equals(ICMSConstant.PROP_IDX_TYPE_A)) {
						allFullyMatchedIndexes.add(propIndexParam);
						found = true;
						matchPropIndexParam = propIndexParam;
					}
				}
			}

			// retrieve the current index based on current date
			if (found) {
				populateCurrentYearPropertyIndexInfo(matchIndexList, matchPropIndexParam, quarterCode);
				if (!this.isAssessAllFullyMatchedPropertyIndex) {
					break;
				}
			}
		}

		if (this.isAssessAllFullyMatchedPropertyIndex && !allFullyMatchedIndexes.isEmpty()) {
			Collections.sort(allFullyMatchedIndexes, new SortByYearQuarter());
			matchPropIndexParam = (OBPropertyIndexParam) allFullyMatchedIndexes.get(0);
			populateCurrentYearPropertyIndexInfo((List) profiles.get(matchPropIndexParam.getIndexType()),
					matchPropIndexParam, quarterCode);
			found = true;
		}

		if (!found && !possibleMatchedIndexes.isEmpty()) {
			Collections.sort(possibleMatchedIndexes, new SortByYearQuarter(true));
			matchPropIndexParam = (OBPropertyIndexParam) possibleMatchedIndexes.get(0);
			OBPropertyIndexParam propertyIndexParameterForLatestYear = (OBPropertyIndexParam) possibleMatchedIndexes
					.get(possibleMatchedIndexes.size() - 1);
			if (baseYear <= propertyIndexParameterForLatestYear.getYear()) {
				found = true;
			}
			else {
				if (this.isTakingLatestPropertyIndexIfBaseYearExceed) {
					matchPropIndexParam = propertyIndexParameterForLatestYear;
				}
				else {
					matchPropIndexParam = null;
				}
			}

			if (matchPropIndexParam != null) {
				populateCurrentYearPropertyIndexInfo((List) profiles.get(matchPropIndexParam.getIndexType()),
						matchPropIndexParam, quarterCode);
			}
		}

		possibleMatchedIndexes.clear();
		matchIndexList.clear();
		allFullyMatchedIndexes.clear();

		if (found && matchPropIndexParam != null) {
			logger.debug("Matched Property Index found for collateral, CMS Collateral Id = ["
					+ pValModel.getCollateralId() + "], Property Index Item Id = ["
					+ matchPropIndexParam.getIndexItemID() + "] Year = [" + matchPropIndexParam.getYear()
					+ "], Quarter Code = [" + matchPropIndexParam.getQuarterCode() + "], Index = ["
					+ matchPropIndexParam.getIndex() + "], Currenty Index = [" + matchPropIndexParam.getCurrIndex()
					+ "]");
		}

		return matchPropIndexParam;
	}

	private void populateCurrentYearPropertyIndexInfo(List matchedPropertyIndexes,
			OBPropertyIndexParam matchedPropertyIndexParameter, String valuationQuarterCode) {
		int currYear = CommonUtil.getYear(CommonUtil.getCurrentDate());
		String currQuarterCode = CommonUtil.getQuarterCode(CommonUtil.getCurrentDate());

		Collections.sort(matchedPropertyIndexes, new SortByYearQuarter());
		int currQuarterCodeValue = CommonUtil.getQuarterCodeValue(currQuarterCode);

		for (Iterator itr = matchedPropertyIndexes.iterator(); itr.hasNext();) {

			OBPropertyIndexParam propIndexParam = (OBPropertyIndexParam) itr.next();
			int quarterCodeValue = CommonUtil.getQuarterCodeValue(propIndexParam.getQuarterCode());

			// get the index with latest date but not date earlier than
			// current date
			if (propIndexParam.getYear() > currYear) {
				continue;
			}

			if (propIndexParam.getYear() == currYear && !valuationQuarterCode.equals(ICMSConstant.PROP_IDX_TYPE_A)
					&& currQuarterCodeValue > quarterCodeValue) {
				continue;
			}

			matchedPropertyIndexParameter.setCurrIndex(propIndexParam.getIndex());
			break;

		}
	}

	/**
	 * <p>
	 * Comparator to sort <tt>OBPropertyIndexParam</tt> by Year and Quarter
	 * (descending order (default)), then by Primary Key (ascending order)
	 * <p>
	 * Use {@link #PropertyIndexValuator(boolean)} if require to sort the Year
	 * in ascending order
	 */
	class SortByYearQuarter implements Comparator {
		/** to indicate whether to sort by earliest year first */
		private boolean isAscending = false;

		public SortByYearQuarter() {
		}

		public SortByYearQuarter(boolean isAscending) {
			this.isAscending = isAscending;
		}

		public int compare(Object a, Object b) {
			int retValue = 0;
			if (a != null && b != null) {
				OBPropertyIndexParam obj1 = (OBPropertyIndexParam) a;
				OBPropertyIndexParam obj2 = (OBPropertyIndexParam) b;

				// if asc is false, latest year first
				retValue = obj2.getYear() < obj1.getYear() ? -1 : (obj2.getYear() == obj1.getYear() ? 0 : 1);
				if (retValue != 0) {
					return retValue * (isAscending ? -1 : 1);
				}

				// followed by quarter
				int quarterCodeValue1 = CommonUtil.getQuarterCodeValue(obj1.getQuarterCode());
				int quarterCodeValue2 = CommonUtil.getQuarterCodeValue(obj2.getQuarterCode());

				retValue = quarterCodeValue1 < quarterCodeValue2 ? -1
						: (quarterCodeValue1 == quarterCodeValue2 ? 0 : 1);
				if (retValue != 0) {
					return retValue;
				}

				// followed by latest created
				retValue = obj2.getIndexItemID() < obj1.getIndexItemID() ? -1 : (obj2.getIndexItemID() == obj1
						.getIndexItemID() ? 0 : 1);
				if (retValue != 0) {
					return retValue;
				}

			}
			return retValue;
		}
	}

	public void saveOnlineValuationInfoInCollateral(ICollateral collateral, IValuationModel model) {
	}

	public IValuationModel createValuationModelInstance() {
		return new PropertyValuationModel();
	}
}