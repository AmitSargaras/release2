package com.integrosys.cms.app.collateral.bus;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.common.util.DB2DateConverter;

/**
 * Query helper to append advanced search criteria to the query passed in.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class CollateralAdvancedSearchQueryHelper {

	private static String SELECT_COLLATERAL_SUBTYPE_ASSET = " SELECT CMS_COLLATERAL_ID FROM cms_asset  "
			+ " WHERE cms_security.CMS_COLLATERAL_ID = cms_asset.CMS_COLLATERAL_ID";

	private static String SELECT_COLLATERAL_SUBTYPE_GUARANTEE = " SELECT CMS_COLLATERAL_ID FROM cms_guarantee  "
			+ " WHERE cms_security.CMS_COLLATERAL_ID = cms_guarantee.CMS_COLLATERAL_ID";

	private static String SELECT_COLLATERAL_SUBTYPE_PROPERTY = " SELECT CMS_COLLATERAL_ID FROM cms_property  "
			+ " WHERE cms_security.CMS_COLLATERAL_ID = cms_property.CMS_COLLATERAL_ID";

	public static void getAssetAdvancedSearchQuery(CollateralSearchCriteria criteria, StringBuffer buf, List argList) {
		buf.append(" AND cms_security.CMS_COLLATERAL_ID IN (");
		buf.append(SELECT_COLLATERAL_SUBTYPE_ASSET);

		// for Vehicle, CHASSIS_NUMBER, REG_NO ,TYPE_OF_ASSET
		if (!isEmpty(criteria.getVehType())) {
			buf.append(" AND cms_asset.TYPE_OF_ASSET = ? ");
			argList.add(criteria.getVehType().trim());
		}

		if (!isEmpty(criteria.getRegN0())) {
			buf.append(" AND cms_asset.REG_NO LIKE ? ");
			argList.add(criteria.getRegN0().trim() + "%");
		}

		if (!isEmpty(criteria.getChassissNo()) || !isEmpty(criteria.getEngineNo())) {
			buf.append(" AND cms_asset.cms_collateral_id IN (SELECT cms_collateral_id FROM ").append(
					"cms_asset_vehicle veh WHERE ");

			if (!isEmpty(criteria.getChassissNo())) {
				buf.append(" veh.chassis_number LIKE ? ");
				argList.add(criteria.getChassissNo().trim() + "%");

				if (!isEmpty(criteria.getEngineNo())) {
					buf.append(" AND veh.engine_number LIKE ? ");
					argList.add(criteria.getEngineNo().trim() + "%");
				}
			}
			else {
				buf.append(" veh.engine_number LIKE ? ");
				argList.add(criteria.getEngineNo().trim() + "%");
			}

			buf.append(")");
		}

		// for plant and equipment type of plant (asset Type) , Serial No , yr
		// Manufacture , Model No.
		if (!isEmpty(criteria.getAssetType())) {
			buf.append(" AND cms_asset.TYPE_OF_ASSET = ? ");
			argList.add(criteria.getAssetType().trim());
		}

		if (!isEmpty(criteria.getYearOfManufacture())) {
			buf.append(" AND cms_asset.MANUFACTURE_YEAR = ? ");
			argList.add(Integer.valueOf(criteria.getYearOfManufacture().trim()));
		}

		if (!isEmpty(criteria.getModelNo())) {
			buf.append(" AND cms_asset.MODEL_NO LIKE ? ");
			argList.add(criteria.getModelNo().trim() + "%");
		}

		if (!isEmpty(criteria.getSerialNo())) {
			buf.append(" AND cms_asset.cms_collateral_id IN ");
			buf.append("(SELECT cms_collateral_id FROM cms_asset_plant_equip plantequip ");
			buf.append(" WHERE plantequip.serial_no LIKE ? ) ");
			argList.add(criteria.getSerialNo().trim() + "%");
		}

		// for Vessel ,
		if (!isEmpty(criteria.getFlagRegistered())) {
			buf.append(" AND cms_asset.cms_collateral_id IN (SELECT cms_collateral_id FROM cms_asset_vessel vessel ");
			buf.append(" WHERE vessel.reg_country = ? ) ");
			argList.add(criteria.getFlagRegistered().trim());
		}

		if (!isEmpty(criteria.getBuiltYear())) {
			buf.append(" AND cms_asset.cms_collateral_id IN (SELECT cms_collateral_id FROM cms_asset_vessel vessel ");
			buf.append(" WHERE vessel.build_year = ? ) ");
			argList.add(Integer.valueOf(criteria.getBuiltYear().trim()));
		}

		// for Gold , PURCHASE_RECEIPT_NO ,GOLD_GRADE
		if (!isEmpty(criteria.getPurchaseReceiptNo())) {
			buf.append(" AND cms_asset.cms_collateral_id IN (SELECT cms_collateral_id FROM cms_asset_gold gold ");
			buf.append(" WHERE gold.purchase_receipt_no LIKE ? ) ");
			argList.add(criteria.getPurchaseReceiptNo().trim() + "%");
		}

		if (!isEmpty(criteria.getGoldGrade())) {
			buf.append(" AND cms_asset.cms_collateral_id IN (SELECT cms_collateral_id FROM cms_asset_gold gold ");
			buf.append(" WHERE gold.gold_grade = ? )");
			argList.add(criteria.getGoldGrade().trim());
		}

		if (!isEmpty(criteria.getItemType())) {
			buf.append(" AND cms_asset.TYPE_OF_ASSET = ? ");
			argList.add(criteria.getItemType().trim());
		}
		buf.append(" ) ");
	}

	/**
	 * Helper method to get adv search filter for Guarantees
	 */
	public static void getGteAdvSearchSQL(CollateralSearchCriteria criteria, StringBuffer buf, List argList) {
		buf.append(" AND cms_security.CMS_COLLATERAL_ID IN ( ");
		buf.append(SELECT_COLLATERAL_SUBTYPE_GUARANTEE);

		// Issuer
		if (!isEmpty(criteria.getIssuer())) {
			buf.append(" AND UPPER(cms_guarantee.ISSUING_BANK) = ? ");
			argList.add(criteria.getIssuer().toUpperCase().trim());
		}

		// Standby LC
		if (!isEmpty(criteria.getStandbyLCNo())) {
			buf.append(" AND cms_guarantee.REFERENCE_NO LIKE ? ");
			argList.add(criteria.getStandbyLCNo().trim() + "%");
		}

		// getFromExpDate and getToExpDate()
		if (criteria.getFromExpDate() != null) {
			String fromExp = DB2DateConverter.getDateAsString(criteria.getFromExpDate());
			String fromExpDateStr = " DATE('" + fromExp + "') ";
			buf.append(" AND  DATE(cms_security.SECURITY_MATURITY_DATE) >= ");
			buf.append(fromExpDateStr);
		}

		// getToExpDate()
		if (criteria.getToExpDate() != null) {
			String toExpDate = DB2DateConverter.getDateAsString(criteria.getToExpDate());
			String toExpDateStr = " DATE('" + toExpDate + "') ";
			buf.append(" AND  DATE(cms_security.SECURITY_MATURITY_DATE) < ");
			buf.append(toExpDateStr);
		}

		buf.append(" ) ");
	}

	/**
	 * Helper method to get adv search filter for Property
	 */
	public static void getPropertyAdvSearchSQL(CollateralSearchCriteria criteria, StringBuffer buf, List argList) {
		buf.append(" AND cms_security.CMS_COLLATERAL_ID IN (");
		buf.append(SELECT_COLLATERAL_SUBTYPE_PROPERTY);

		if (!isEmptyOrNull(criteria.getTitleNoPrefix())) {
			buf.append(" AND cms_property.title_number_prefix = ? ");
			argList.add(criteria.getTitleNoPrefix().trim());
		}

		if (!isEmptyOrNull(criteria.getTitleNo())) {
			buf.append(" AND cms_property.title_number LIKE ? ");
			argList.add(criteria.getTitleNo().trim() + "%");
		}

		if (!isEmptyOrNull(criteria.getTitleTypeCD())) {
			buf.append(" AND cms_property.TITLE_TYPE = ? ");
			argList.add(criteria.getTitleTypeCD().trim());
		}

		if (!isEmptyOrNull(criteria.getStateCD())) {
			buf.append(" AND cms_property.STATE = ? ");
			argList.add(criteria.getStateCD().trim());
		}

		if (!isEmptyOrNull(criteria.getDistrictCD())) {
			buf.append(" AND cms_property.DISTRICT = ? ");
			argList.add(criteria.getDistrictCD().trim());
		}

		if (!isEmptyOrNull(criteria.getMukimCD())) {
			buf.append(" AND cms_property.MUKIM = ? ");
			argList.add(criteria.getMukimCD().trim());
		}

		buf.append(" ) ");
	}

	private static boolean isEmptyOrNull(String value) {
		if (isEmpty(value)) {
			return true;
		}

		if ("null".equalsIgnoreCase(value.trim())) {
			return true;
		}

		return false;
	}

	/**
	 * Utility method to check if a string value is null or empty.
	 * 
	 * @param aValue string to be checked
	 * @return boolean true if empty and false otherwise
	 */
	private static boolean isEmpty(String aValue) {
		return StringUtils.isBlank(aValue);
	}
}
