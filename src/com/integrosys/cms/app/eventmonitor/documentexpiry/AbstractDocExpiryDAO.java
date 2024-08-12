/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.eventmonitor.documentexpiry;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.common.OBDocumentExpiryInfo;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * DAO for checklist item monitor.. Doc expiry date - system date = xx days (xx
 * should be -ve)
 * 
 * @author $Author: hmbao $
 * @version $Revision: 1.7 $
 * @since $Date: 2006/08/24 05:45:58 $ Tag: $Name: $
 */

public abstract class AbstractDocExpiryDAO extends AbstractMonitorDAO implements IMonitorDAO, ICMSTrxTableConstants {

	/**
	 * Process resultset to return a list of results.
	 * 
	 * @param ResultSet rs
	 * @return List - list of results
	 * @throws Exception
	 */
	protected List processResultSet(ResultSet rs) throws SQLException {
		List results = new ArrayList();
		OBDocumentExpiryInfo nn = null;
		if (rs != null) {
			while (rs.next()) {
				nn = new OBDocumentExpiryInfo();
				String category = rs.getString("CATEGORY");
				if (ICMSConstant.DOC_TYPE_CC.equals(category)) {
					nn.setDocType(category);
				}
				else if (ICMSConstant.DOC_TYPE_SECURITY.equals(category)) {
					nn.setDocType("SC");
				}

				nn.setDocumentCode(rs.getString("DOCUMENT_CODE"));
				nn.setDocRef(rs.getString("DOC_REFERENCE"));
				Date deferalExpiryDate = rs.getDate("DEFER_EXTENDED_DATE");
				if (deferalExpiryDate == null) {
					deferalExpiryDate = rs.getDate("DEFER_EXPIRY_DATE");
				}
				nn.setDocDefferalExpiryDate(deferalExpiryDate);
				nn.setLeName(rs.getString("LSP_SHORT_NAME"));
				nn.setLeID(rs.getString("LSP_LE_ID"));
				nn.setSegment(rs.getString("SEGMENT"));
				nn.setDaysDue(rs.getInt("DAYSDUE"));
				nn.setOriginatingCountry(rs.getString("CMS_ORIG_COUNTRY"));
				nn.setOriginatingOrganisation(rs.getString("CMS_ORIG_ORGANISATION"));
				nn.setDocumentDate(rs.getDate("DOC_DATE"));
				nn.setExpiryDate(rs.getDate("EXPIRY_DATE"));
				nn.setDocumentType(rs.getString("DOC_DESCRIPTION"));
				nn.setCheckListItemID(rs.getLong("DOC_ITEM_ID"));
				nn.setChecklistItemRef(rs.getLong("DOC_ITEM_REF"));
				nn.setSciSecurityID(rs.getString("SCI_SECURITY_DTL_ID"));
				nn.setSecuritySubType(rs.getString("SUBTYPE_NAME"));
				nn.setSecurityMaturityDate(rs.getDate("SECURITY_MATURITY_DATE"));
				String ssArray[] = new String[1];
				ssArray[0] = rs.getString("SECURITY_LOCATION");
				if (!"dummy".equals(ssArray[0])) {
					nn.setSecondaryCountryList(ssArray);
					nn.setLocale(new Locale("en", ssArray[0]));
				}
				else {
					nn.setLocale(new Locale("en", nn.getOriginatingCountry()));
				}
				results.add(nn);
			}
		}

		logger.debug("Num of records : " + results.size());
		return results;
	}

	protected String getCklBCACustConnStmt() {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" AND stage_checklist.cms_lsp_lmt_profile_id = sci_lsp_lmt_profile.cms_lsp_lmt_profile_id");
		sqlBuffer.append(" AND stage_checklist.cms_lmp_sub_profile_id = sci_le_sub_profile.cms_le_sub_profile_id");
		sqlBuffer.append(" AND sci_le_sub_profile.cms_le_main_profile_id = sci_le_main_profile.cms_le_main_profile_id");
		return sqlBuffer.toString();
	}
}