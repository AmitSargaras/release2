/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/uom/UnitofMeasureDAO.java,v 1.3 2004/07/22 03:28:17 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.uom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.OBProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/22 03:28:17 $ Tag: $Name: $
 */
public class UnitofMeasureDAO implements IUnitofMeasureDAOConstants {
	private DBUtil dbUtil;

	private static String SELECT_UOM_PROFILE = null;

	private static String SELECT_MAX_GROUP_ID = null;

	private static String SELECT_MAX_GROUP_ID_CATCODE_PDTTYPECODE = null;

	static {
		StringBuffer buf = new StringBuffer();
		buf.append("select ").append(PROFILE_TABLE).append(".").append(PROFILE_PROFILE_ID).append(", ");
		buf.append(PROFILE_TABLE).append(".").append(PROFILE_COMMODITY_CATEGORY).append(", ");
		buf.append(PROFILE_TABLE).append(".").append(PROFILE_PRODUCT_TYPE).append(", ");
		buf.append(PROFILE_TABLE).append(".").append(PROFILE_PRODUCT_SUBTYPE).append(", ");
		buf.append(PROFILE_TABLE).append(".").append(PROFILE_COUNTRY_CODE).append(", ");
		buf.append(PROFILE_TABLE).append(".").append(PROFILE_RIC).append(", ");
		buf.append(PROFILE_TABLE).append(".").append(PROFILE_PRICE_TYPE).append(", ");
		buf.append(PROFILE_TABLE).append(".").append(PROFILE_STATUS).append(", ");
		buf.append(UOM_TABLE).append(".").append(UOM_GROUP_ID).append(", ");
		buf.append(UOM_TABLE).append(".").append(UOM_UOM_ID);
		buf.append(" from ").append(PROFILE_TABLE).append(", ");
		buf.append(UOM_TABLE);
		buf.append(" where ").append(PROFILE_TABLE).append(".").append(PROFILE_PROFILE_ID).append(" = ");
		buf.append(UOM_TABLE).append(".").append(UOM_PROFILE_ID).append(" and ");
		buf.append(UOM_TABLE).append(".").append(UOM_STATUS).append(" = '").append(ICMSConstant.STATE_ACTIVE).append(
				"'");

		SELECT_UOM_PROFILE = buf.toString();

		buf = new StringBuffer();
		buf.append("select max(").append(UOM_GROUP_ID).append(") as ").append(MAX_UOM_GROUP_ID);
		buf.append(" from ");

		SELECT_MAX_GROUP_ID = buf.toString();

		buf = new StringBuffer();
		buf.append("select max(uom.").append(UOM_GROUP_ID).append(") as ").append(MAX_UOM_GROUP_ID);
		buf.append(" from ");
		buf.append(PROFILE_TABLE).append(" profile, ");
		SELECT_MAX_GROUP_ID_CATCODE_PDTTYPECODE = buf.toString();
	}

	/**
	 * Default Constructor
	 */
	public UnitofMeasureDAO() {
	}

	/**
	 * Get commodity price profile given the category and product type code.
	 * 
	 * @param catCode commodity category code
	 * @param pdtTypecode commodity product type code
	 * @return a list of commodity price
	 * @throws com.integrosys.base.businfra.search.SearchDAOException on error
	 *         searching the profile for commodity price
	 */
	public IUnitofMeasure[] getUnitofMeasureProfile(String catCode, String pdtTypecode) throws SearchDAOException {
		HashMap conditionMap = new HashMap(2);
		conditionMap.put(PROFILE_COMMODITY_CATEGORY, catCode);
		conditionMap.put(PROFILE_PRODUCT_TYPE, pdtTypecode);
		String sql = constructSQL(conditionMap);

		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processResultSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting uom's profile ", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (Exception e) {
				throw new SearchDAOException("Error in cleaning up DB resources.", e);
			}
		}
	}

	private String constructSQL(Map criteria) {
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_UOM_PROFILE);
		if (criteria == null) {
			return buf.toString();
		}
		Iterator iterator = criteria.keySet().iterator();
		String colName = null;
		String value = null;
		while (iterator.hasNext()) {
			colName = (String) iterator.next();
			value = (String) criteria.get(colName);

			buf.append(" and ");
			buf.append(colName);

			if (value == null) {
				buf.append(" is null");
			}
			else {
				buf.append(" = '").append(value).append("'");
			}
		}
		return buf.toString();
	}

	/**
	 * Helper method to process the result set of commodity price's profile.
	 * 
	 * @param rs result set
	 * @return a list of commodity prices
	 * @throws java.sql.SQLException on error processing the result set
	 */
	private IUnitofMeasure[] processResultSet(ResultSet rs) throws SQLException {
		ArrayList results = new ArrayList();

		while (rs.next()) {

			IProfile profile = getCommodityProfile(rs);

			OBUnitofMeasure uom = new OBUnitofMeasure();
			uom.setUnitofMeasureID(rs.getLong(UOM_UOM_ID));
			uom.setGroupID(rs.getLong(UOM_GROUP_ID));
			uom.setProfileID(profile.getProfileID());
			uom.setCommodityProfile(profile);
			uom.setStatus(rs.getString(PROFILE_STATUS));
			results.add(uom);
		}
		return (OBUnitofMeasure[]) results.toArray(new OBUnitofMeasure[0]);
	}

	private IProfile getCommodityProfile(ResultSet rs) throws SQLException {
		OBProfile profile = new OBProfile();
		profile.setProfileID(rs.getLong(PROFILE_PROFILE_ID));
		profile.setStatus(rs.getString(PROFILE_STATUS));
		profile.setReuterSymbol(rs.getString(PROFILE_RIC));
		profile.setCountryArea(rs.getString(PROFILE_COUNTRY_CODE));
		profile.setPriceType(rs.getString(PROFILE_PRICE_TYPE));
		profile.setCategory(rs.getString(PROFILE_COMMODITY_CATEGORY));
		profile.setProductType(rs.getString(PROFILE_PRODUCT_TYPE));
		profile.setProductSubType(rs.getString(PROFILE_PRODUCT_SUBTYPE));
		return profile;
	}

	public String constructGetMaxGroupIDSQL(String categoryCode, String productTypeCode, boolean isStaging) {
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_MAX_GROUP_ID_CATCODE_PDTTYPECODE);
		if (isStaging) {
			buf.append(UOM_STAGE_TABLE);
		}
		else {
			buf.append(UOM_TABLE);
		}
		buf.append(" uom where uom.").append(UOM_PROFILE_ID).append(" = profile.").append(PROFILE_PROFILE_ID);

		if ((categoryCode != null) && (categoryCode.length() > 0)) {
			buf.append(" and profile.").append(PROFILE_COMMODITY_CATEGORY).append(" = '").append(categoryCode).append(
					"'");
		}

		if ((productTypeCode != null) && (productTypeCode.length() > 0)) {
			buf.append(" and profile.").append(PROFILE_PRODUCT_TYPE).append(" = '").append(productTypeCode).append("'");
		}

		return buf.toString();
	}

	public String constructGetMaxGroupIDSQL(IUnitofMeasure[] uoms, boolean isStaging) {
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_MAX_GROUP_ID);
		if (isStaging) {
			buf.append(UOM_STAGE_TABLE);
		}
		else {
			buf.append(UOM_TABLE);
		}
		buf.append(" where ").append(UOM_PROFILE_ID).append(" in ");
		buf.append(getSQLList(uoms));
		return buf.toString();
	}

	private String getSQLList(IUnitofMeasure[] uoms) {
		if ((uoms == null) || (uoms.length == 0)) {
			return null;
		}

		StringBuffer buf = new StringBuffer();
		buf.append("(");

		for (int i = 0; i < uoms.length; i++) {
			buf.append(uoms[i].getProfileID());
			if (i != uoms.length - 1) {
				buf.append(",");
			}
			else {
				buf.append(")");
			}
		}
		return buf.toString();
	}
}
