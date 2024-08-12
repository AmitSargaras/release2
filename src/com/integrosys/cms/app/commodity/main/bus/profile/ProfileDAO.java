/*
 * Created on Jul 28, 2004
 *
 */
package com.integrosys.cms.app.commodity.main.bus.profile;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.StringUtil;
import com.integrosys.cms.app.commodity.main.CommodityException;

/**
 * @author Administrator
 * 
 */
public class ProfileDAO implements IProfileDAOConstants {

	private String[] PROFILE_COLUMN_ARRAY = new String[] { "PROFILE_ID", "CATEGORY", "PRODUCT_TYPE",
			"PRODUCT_SUB_TYPE", "RIC", "PRICE_TYPE", "RIC_TYPE", "GROUP_ID", "CMS_REF_ID", "PRICE_DIFF_SIGN",
			"PRICE_DIFFERENTIAL", "MARKET_NAME", "COUNTRY_AREA", "CHAINS", "OUTRIGHTS", "VERSION_TIME", "STATUS",
			"NON_RIC_DESC" };

	private static String SELECT_MAX_GROUP_ID = null;
	static {
		StringBuffer buf = new StringBuffer();
		buf = new StringBuffer();
		buf.append("select max(").append(PROFILE_GROUP_ID).append(") as ").append(MAX_PROFILE_GROUP_ID)
				.append(" from ");
		SELECT_MAX_GROUP_ID = buf.toString();
	}

	/**
	 * Default Constructor
	 */
	public ProfileDAO() {
	}

	public String constructGetGroupIDSQL(boolean isStaging, String category) {

		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_MAX_GROUP_ID);
		if (isStaging) {
			buf.append(PROFILE_STAGE_TABLE);
		}
		else {
			buf.append(PROFILE_TABLE);
		}
		if ((category != null) && !"".equals(category.trim())) {
			buf.append(" WHERE ");
			buf.append(" CATEGORY = ");
			buf.append("'");
			buf.append(category);
			buf.append("'");
		}
		return buf.toString();
	}

	public List searchProfile(ProfileSearchCriteria searchCriteria) throws CommodityException {
		String sql = constructProfileSQL(searchCriteria);
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processProfileResulSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException("Error in getting commodity profile ", e);
		}
		finally {
			finalize(dbUtil);
		}

	}

	private List processProfileResulSet(ResultSet rs) throws Exception {
		DBUtil buyerUtil = null;
		DBUtil supplierUtil = null;
		try {
			ArrayList profileList = new ArrayList();
			buyerUtil = new DBUtil();
			supplierUtil = new DBUtil();
			while (rs.next()) {
				OBProfile profile = new OBProfile();
				int index = 0;
				profile.setProfileID(rs.getLong(PROFILE_COLUMN_ARRAY[index++]));
				profile.setCategory(rs.getString(PROFILE_COLUMN_ARRAY[index++]));
				profile.setProductType(rs.getString(PROFILE_COLUMN_ARRAY[index++]));
				profile.setProductSubType(rs.getString(PROFILE_COLUMN_ARRAY[index++]));
				profile.setReuterSymbol(rs.getString(PROFILE_COLUMN_ARRAY[index++]));
				profile.setPriceType(rs.getString(PROFILE_COLUMN_ARRAY[index++]));
				profile.setRICType(rs.getString(PROFILE_COLUMN_ARRAY[index++]));
				profile.setGroupID(rs.getLong(PROFILE_COLUMN_ARRAY[index++]));
				profile.setCommonRef(rs.getLong(PROFILE_COLUMN_ARRAY[index++]));
				profile.setDifferentialSign(rs.getString(PROFILE_COLUMN_ARRAY[index++]));
				profile.setPriceDifferential(rs.getBigDecimal(PROFILE_COLUMN_ARRAY[index++]));
				profile.setMarketName(rs.getString(PROFILE_COLUMN_ARRAY[index++]));
				profile.setCountryArea(rs.getString(PROFILE_COLUMN_ARRAY[index++]));
				profile.setChains(rs.getString(PROFILE_COLUMN_ARRAY[index++]));
				profile.setOutrights(rs.getString(PROFILE_COLUMN_ARRAY[index++]));
				profile.setVersionTime(rs.getLong(PROFILE_COLUMN_ARRAY[index++]));
				profile.setStatus(rs.getString(PROFILE_COLUMN_ARRAY[index++]));
				profile.setNonRICDesc(rs.getString(PROFILE_COLUMN_ARRAY[index++]));

				profile.setBuyers(getBuyers(buyerUtil, profile.getProfileID()));
				profile.setSuppliers(getSuppliers(supplierUtil, profile.getProfileID()));
				profileList.add(profile);
			}
			DefaultLogger.debug(this, "-Num of Profile : " + profileList.size());
			return profileList;
		}
		finally {
			finalize(buyerUtil);
			finalize(supplierUtil);
		}
	}

	private String constructProfileSQL(ProfileSearchCriteria searchCriteria) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT ");
		for (int index = 0; index < PROFILE_COLUMN_ARRAY.length; index++) {
			if (index != 0) {
				sqlBuffer.append(" , ");
			}
			sqlBuffer.append(PROFILE_COLUMN_ARRAY[index]);
		}
		sqlBuffer.append(" FROM CMS_CMDT_PROFILE ");
		if (searchCriteria == null) {
			return sqlBuffer.toString();
		}
		boolean firstCondition = true;
		String temp = searchCriteria.getCategory();
		firstCondition = appendCondition(sqlBuffer, firstCondition, "CATEGORY", temp);
		temp = searchCriteria.getPriceType();
		firstCondition = appendCondition(sqlBuffer, firstCondition, "PRICE_TYPE", temp);
		temp = searchCriteria.getNonRICCode();
		firstCondition = appendCondition(sqlBuffer, firstCondition, "RIC", temp);
		temp = searchCriteria.getProductSubType();
		firstCondition = appendCondition(sqlBuffer, firstCondition, "PRODUCT_SUB_TYPE", temp);

		return sqlBuffer.toString();
	}

	private boolean appendCondition(StringBuffer buffer, boolean firstCondition, String columnName, String value) {
		if ((value == null) || "".equals(value.trim())) {
			return firstCondition;
		}
		if (firstCondition) {
			buffer.append(" WHERE ");
		}
		else {
			buffer.append(" AND ");
		}
		buffer.append(columnName);
		buffer.append("='");
		buffer.append(StringUtil.replaceString(value, "'", "''"));
		buffer.append("'");
		return false;
	}

	private void finalize(DBUtil dbUtil) throws CommodityException {
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			throw new CommodityException("Error in cleaning up DB resources.");
		}
	}

	private OBSupplier[] getSuppliers(DBUtil supplierUtil, long profileID) throws Exception {
		String sql = "SELECT SUPPLIER_ID,NAME,CMS_REF_ID,STATUS FROM CMS_CMDT_SUPPLIER WHERE PROFILE_ID = " + profileID
				+ " AND STATUS <> 'DELETED'";
		// DefaultLogger.debug(this, "- Supplier : " + sql);
		supplierUtil.setSQL(sql);
		ResultSet rs = supplierUtil.executeQuery();
		ArrayList suplierList = new ArrayList();
		if (rs != null) {
			while (rs.next()) {
				OBSupplier suplier = new OBSupplier();
				suplier.setSupplierID(rs.getLong("SUPPLIER_ID"));
				suplier.setName(rs.getString("NAME"));
				suplier.setCommonReferenceID(rs.getLong("CMS_REF_ID"));
				suplier.setStatus(rs.getString("STATUS"));
				suplierList.add(suplier);
			}
			rs.close();
		}
		return (OBSupplier[]) suplierList.toArray(new OBSupplier[0]);
	}

	private OBBuyer[] getBuyers(DBUtil buyerUtil, long profileID) throws Exception {
		String sql = "SELECT BUYER_ID,NAME,CMS_REF_ID,STATUS FROM CMS_CMDT_BUYER WHERE PROFILE_ID = " + profileID
				+ " AND STATUS <> 'DELETED'";
		// DefaultLogger.debug(this, " - Buyer : " + sql);
		buyerUtil.setSQL(sql);
		ResultSet rs = buyerUtil.executeQuery();
		ArrayList buyersList = new ArrayList();
		if (rs != null) {
			while (rs.next()) {
				OBBuyer buyer = new OBBuyer();
				buyer.setBuyerID(rs.getLong("BUYER_ID"));
				buyer.setName(rs.getString("NAME"));
				buyer.setCommonReferenceID(rs.getLong("CMS_REF_ID"));
				buyer.setStatus(rs.getString("STATUS"));
				buyersList.add(buyer);
			}
			rs.close();
		}
		return (OBBuyer[]) buyersList.toArray(new OBBuyer[0]);
	}
}
