package com.integrosys.cms.app.collateral.bus;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Abstract implementation of <code>ICollateralPerfector</code> to provide
 * common validation check on Common fields, Charge Details, Legal
 * Enforceability and helper methods to check valid string and amount.
 * 
 * @author Chong Jun Yong
 * @since 1.2
 */
public abstract class AbstractCollateralPerfector implements ICollateralPerfector {
	public abstract boolean isCollateralPerfected(ICollateral col);

	protected boolean isCollateralCommonFieldsPerfected(ICollateral col) {
		if (!isValidStr(col.getCurrencyCode()) || !isValidStr(col.getSecurityOrganization())
				|| (col.getPerfectionDate() == null) || !isLEValid(col)) {
			return false;
		}

		return true;
	}

	protected boolean isChargeDetailsPerfected(ILimitCharge[] charges) {
		if ((charges == null) || (charges.length == 0)) {
			return false;
		}

		for (int i = 0; i < charges.length; i++) {
			if (!isValidStr(charges[i].getNatureOfCharge()) || !isValidAmt(charges[i].getChargeAmount())) {
				return false;
			}

			if (charges[i].getSecurityRank() > 1) {
				if (!isValidStr(charges[i].getPriorChargeChargee()) || !isValidStr(charges[i].getPriorChargeType())
						|| !isValidAmt(charges[i].getPriorChargeAmount())) {
					return false;
				}
			}
		}

		return true;
	}

	protected boolean isInsurancePolicyDetailsPerfected(IInsurancePolicy[] insurances) {
		if ((insurances == null) || (insurances.length == 0)) {
			return false;
		}

		for (int i = 0; i < insurances.length; i++) {
			IInsurancePolicy insurance = insurances[i];
			if (!isValidStr(insurance.getPolicyNo())) {
				return false;
			}

			if (!isValidStr(insurance.getCoverNoteNumber())) {
				return false;
			}

			if (!isValidStr(insurance.getInsurerName())) {
				return false;
			}

			if (!isValidAmt(insurance.getInsuredAmount())) {
				return false;
			}

			if (insurance.getEffectiveDate() == null) {
				return false;
			}

			if (insurance.getExpiryDate() == null) {
				return false;
			}
		}

		return true;
	}

	/**
	 * <p>
	 * To check whether valuation detail is perfected.
	 * 
	 * <p>
	 * Two fields to be checked
	 * <ol>
	 * <li>Valuation Currency
	 * <li>Valuation CMV / OMV
	 * </ol>
	 * 
	 * @param valuation the valuation object to be validated
	 * @return whether valuation detail is perfected.
	 */
	protected boolean isValuationDetailPerfected(IValuation valuation) {
		if (valuation == null) {
			return false;
		}

		if (!isValidStr(valuation.getCurrencyCode()) || !isValidAmt(valuation.getCMV())) {
			return false;
		}

		return true;
	}

	/**
	 * Helper method to check if legal enforceability is valid.
	 * 
	 * @param collateral of type ICollateral
	 * @return true if user selected No/Not Applicable or selected both Yes and
	 *         LE date, otherwise return false
	 */
	protected boolean isLEValid(ICollateral collateral) {
		String legalEnforceability = collateral.getIsLE();
		if (isValidStr(legalEnforceability)) {
			if (legalEnforceability.equals(ICMSConstant.NOT_AVAILABLE_VALUE)
					|| legalEnforceability.equals(ICMSConstant.FALSE_VALUE)) {
				return true;
			}
			else {
				return legalEnforceability.equals(ICMSConstant.TRUE_VALUE) && (collateral.getLEDate() != null);
			}
		}
		else {
			return false;
		}
	}

	/**
	 * Helper method to validate Amount object.
	 * 
	 * @param amt the amount object to be validated
	 * @return whether the amount is at least 0 value
	 */
	protected boolean isValidAmt(Amount amt) {
		return (amt != null) && (amt.getAmountAsBigDecimal() != null)
				&& (amt.getAmountAsBigDecimal().compareTo(new BigDecimal("0")) >= 0);
	}

	/**
	 * Helper method to validate a string.
	 * 
	 * @param str string value to be validated
	 * @return whether the string value is not blank.
	 */
	protected boolean isValidStr(String str) {
		return StringUtils.isNotBlank(str);
	}

	/**
	 * To check whether the target class is instance of any class in the array
	 * 
	 * @param target the target class to be validated
	 * @param classes the array consists of class to be used to validate the
	 *        target class
	 * @return true if class is instance of any class in the array, else false
	 */
	protected boolean isClassAssignableWithAnyOfClasses(Class target, Class[] classes) {
		Validate.notNull(target, "'target' must not be null");

		if (classes == null) {
			return false;
		}

		for (int i = 0; i < classes.length; i++) {
			if ((classes[i].isAssignableFrom(target))) {
				return true;
			}
		}

		return false;
	}

}