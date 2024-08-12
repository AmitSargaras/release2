/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/CollateralStagingDAO.java,v 1.3 2005/09/19 09:02:07 czhou Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * DAO for staging collateral.
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/09/19 09:02:07 $ Tag: $Name: $
 */
public class CollateralStagingDAO extends CollateralDAO {
	private static String SELECT_STAGE_COLLATERAL_SUBTYPE = "select SECURITY_SUB_TYPE_ID, SUBTYPE_NAME, SUBTYPE_DESCRIPTION, SECURITY_TYPE_ID, SECURITY_TYPE_NAME, "
			+ "MAX_PERCENT, GROUP_ID, VERSION_TIME, STATUS, SUBTYPE_STANDARDISED_APPROACH, "
			+ "SUBTYPE_FOUNDATION_IRB, SUBTYPE_ADVANCED_IRB from CMS_STAGE_SECURITY_SUB_TYPE";

	// Table name for staging security sub type
	public static final String COL_SUBTYPE_STAGING_TABLE = "CMS_STAGE_SECURITY_SUB_TYPE";

	/**
	 * Default Constructor
	 */
	public CollateralStagingDAO() {
		super();
	}

	/**
	 * Helper method to construct query for getting collateral subtypes by type
	 * code.
	 * 
	 * @return sql query
	 */
	protected String constructColSubTypeByTypeCodeSQL(String typeCode) {
		if (typeCode == null) {
			typeCode = "";
		}

		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_STAGE_COLLATERAL_SUBTYPE);
		buf.append(" where ");
		buf.append(COL_SUBTYPE_GROUP_ID);
		buf.append(" = (");
		buf.append("select ");
		buf.append("max (");
		buf.append(COL_SUBTYPE_GROUP_ID);
		buf.append(") from ");
		buf.append(COL_SUBTYPE_STAGING_TABLE);
		buf.append(" where ");
		buf.append(COL_TYPE_CODE);
		buf.append(" = '");
		buf.append(typeCode);
		buf.append("')");
		buf.append(" and ");
		buf.append(COL_TYPE_CODE);
		buf.append(" ='");
		buf.append(typeCode);
		buf.append("' order by ");
		buf.append(COL_SUBTYPE_CODE);

		return buf.toString();
	}

	public IValuation getSourceValuation(long collateralId, String sourceType) throws SearchDAOException {
		//For Db2
		/*String sql = "SELECT SOURCE_ID, VALUATION_ID, VALUATION_CURRENCY, VALUATION_TYPE, CMS_COLLATERAL_ID, "
				+ "  VALUER, CMV, VALUATION_DATE, FSV, EVALUATION_DATE_FSV ,RESERVE_PRICE,  RESERVE_PRICE_DATE, "
                + "  SOURCE_TYPE, REVAL_FREQ, REVAL_FREQ_UNIT "
				+ "  FROM CMS_STAGE_VALUATION WHERE SOURCE_TYPE = '" + sourceType + "' AND CMS_COLLATERAL_ID = ? "
				+ " ORDER BY VALUATION_ID DESC FETCH FIRST 1 ROWS ONLY ";*/
		//For Oracle
		String sql = "select * from (SELECT SOURCE_ID, VALUATION_ID, VALUATION_CURRENCY, VALUATION_TYPE, CMS_COLLATERAL_ID, "
				+ "  VALUER, CMV, VALUATION_DATE, FSV, EVALUATION_DATE_FSV ,RESERVE_PRICE,  RESERVE_PRICE_DATE, "
                + "  SOURCE_TYPE, REVAL_FREQ, REVAL_FREQ_UNIT "
				+ "  FROM CMS_STAGE_VALUATION WHERE SOURCE_TYPE = '" + sourceType + "' AND CMS_COLLATERAL_ID = ? "
				+ "  ORDER BY VALUATION_ID DESC) temp where rownum<=1";

		return super.doGetSourceValuation(sql, collateralId);
	}

}
