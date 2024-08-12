package com.integrosys.cms.app.collateral.bus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.integrosys.cms.app.collateral.bus.type.asset.AssetBasedCollateralPerfector;
import com.integrosys.cms.app.collateral.bus.type.cash.CashCollateralPerfector;
import com.integrosys.cms.app.collateral.bus.type.document.DocumentCollateralPerfector;
import com.integrosys.cms.app.collateral.bus.type.guarantee.GuaranteeCollateralPerfector;
import com.integrosys.cms.app.collateral.bus.type.insurance.InsuranceCollateralPerfector;
import com.integrosys.cms.app.collateral.bus.type.marketable.MarketableCollateralPerfector;
import com.integrosys.cms.app.collateral.bus.type.others.OthersCollateralPerfector;
import com.integrosys.cms.app.collateral.bus.type.property.PropertyCollateralPerfector;
import com.integrosys.cms.app.collateral.bus.type.nocollateral.NoCollateralPerfector;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Factory to return collateral perfector based on collateral type code.
 * 
 * @author Chong Jun Yong
 * 
 */
public final class CollateralPerfectorFactory {

	/**
	 * a Map<String, ICollateralPerfector> to store Collateral Pecfector backed
	 * by key of collateral type code
	 */
	private static final Map collateralTypeCodePerfectorMap;

	static {
		collateralTypeCodePerfectorMap = Collections.synchronizedMap(new HashMap());
		collateralTypeCodePerfectorMap.put(ICMSConstant.SECURITY_TYPE_ASSET, new AssetBasedCollateralPerfector());
		collateralTypeCodePerfectorMap.put(ICMSConstant.SECURITY_TYPE_CASH, new CashCollateralPerfector());
		collateralTypeCodePerfectorMap.put(ICMSConstant.SECURITY_TYPE_DOCUMENT, new DocumentCollateralPerfector());
		collateralTypeCodePerfectorMap.put(ICMSConstant.SECURITY_TYPE_GUARANTEE, new GuaranteeCollateralPerfector());
		collateralTypeCodePerfectorMap.put(ICMSConstant.SECURITY_TYPE_INSURANCE, new InsuranceCollateralPerfector());
		collateralTypeCodePerfectorMap.put(ICMSConstant.SECURITY_TYPE_MARKETABLE, new MarketableCollateralPerfector());
		collateralTypeCodePerfectorMap.put(ICMSConstant.SECURITY_TYPE_OTHERS, new OthersCollateralPerfector());
		collateralTypeCodePerfectorMap.put(ICMSConstant.SECURITY_TYPE_PROPERTY, new PropertyCollateralPerfector());
		collateralTypeCodePerfectorMap.put(ICMSConstant.SECURITY_TYPE_CLEAN, new NoCollateralPerfector());
	}

	private CollateralPerfectorFactory() {
	}

	/**
	 * @param col collateral to check against for getting collateral perfector
	 * @return collateral perfector to be used by the collateral passed in
	 */
	public static ICollateralPerfector getCollateralPerfector(ICollateral col) {
		String collateralTypeCode = col.getCollateralType().getTypeCode();
		return getCollateralPerfectorByTypeCode(collateralTypeCode);
	}

	/**
	 * @param collateralTypeCode collateral type code to check against for
	 *        getting collateral perfector
	 * @return collateral perfector to be used depends on the collateral type
	 *         code passed in
	 * @throws IllegalArgumentException if collateral type code is unknown for
	 *         the system.
	 */
	public static ICollateralPerfector getCollateralPerfectorByTypeCode(String collateralTypeCode) {
		ICollateralPerfector collateralPerfector = (ICollateralPerfector) collateralTypeCodePerfectorMap
				.get(collateralTypeCode);

		if (collateralPerfector == null) {
			throw new IllegalArgumentException("Unknown collateralType: [" + collateralTypeCode + "]");
		}

		return collateralPerfector;
	}
}