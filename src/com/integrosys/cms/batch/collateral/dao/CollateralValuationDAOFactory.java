package com.integrosys.cms.batch.collateral.dao;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-5-31
 * @Tag com.integrosys.cms.batch.collateral.CollateralValuationDAOFactory.java
 */
public class CollateralValuationDAOFactory implements ICollateralValuationConstants {
	public static ICollateralValuationDAO getDAO(int valuateType) throws Exception {
		switch (valuateType) {
		case PROPERTY_COLLATERAL:
			return new PropertyValuationDAO();

		case ASSET_COLLATERAL:
			return new AssetBasedOthersValuationDAO();

		case GENERALCHARGE_COLLATERAL:
			return new AssetBasedGeneralChargeValuationDAO();

		case PDC_COLLATERAL:
			return new AssetBasedPdcValuationDAO();

		case CASH_COLLATERAL:
			return new CashValuationDAO();

		case INSURANCE_COLLATERAL:
			return new InsuranceValuationDAO();

		case GUARANTEE_COLLATERAL:
			return new GuaranteeValuationDAO();

		case DOCUMENT_COLLATERAL:
			return new DocumentValuationDAO();

		case COMMODITY_COLLATERAL:
			return new CommodityValuationDAO();

		case MARKETABLE_COLLATERAL:
			return new MarketableValuationDAO();
		default:
			throw new Exception("Unknow valuation type :" + valuateType);
		}
	}
}
