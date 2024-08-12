package com.integrosys.cms.ui.collateral;

import java.util.Map;

import com.integrosys.cms.app.collateral.bus.ICollateral;

public final class CollateralStpValidatorFactory {
	/** the key is the collateral sub type, value is the stp validator */
	private Map collateralSubTypeStpValidatorMap;

	/**
	 * @return the collateralSubTypeStpValidatorMap
	 */
	public Map getCollateralSubTypeStpValidatorMap() {
		return collateralSubTypeStpValidatorMap;
	}

	/**
	 * @param collateralSubTypeStpValidatorMap the
	 *        collateralSubTypeStpValidatorMap to set
	 */
	public void setCollateralSubTypeStpValidatorMap(Map collateralSubTypeStpValidatorMap) {
		this.collateralSubTypeStpValidatorMap = collateralSubTypeStpValidatorMap;
	}

	public CollateralStpValidator getCollateralStpValidator(ICollateral collateral) {
		String subType = collateral.getCollateralSubType().getSubTypeCode();
		return (CollateralStpValidator) getCollateralSubTypeStpValidatorMap().get(subType);
	}
}