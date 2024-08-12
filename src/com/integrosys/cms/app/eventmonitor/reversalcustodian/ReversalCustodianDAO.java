/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/reversalcustodian/ReversalCustodianDAO.java,v 1.7 2005/08/25 09:11:29 lini Exp $
 */
package com.integrosys.cms.app.eventmonitor.reversalcustodian;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * @author $Author: lini $
 * @version $Revision: 1.7 $
 * @since $Date: 2005/08/25 09:11:29 $ Tag: $Name: $
 */
public class ReversalCustodianDAO {

	public List getCustodianNotificationInfo(List chkListItemRefIDList) throws SearchDAOException {
		List resultList = new ArrayList();
		DBUtil dbUtil = null;
		StringBuffer sql = new StringBuffer();
		ArrayList params = new ArrayList();
		sql
				.append(
						" SELECT (CASE WHEN stg_chk.CATEGORY = 'S' THEN 'Security' ELSE stg_chk.CATEGORY END) AS doc_type, stg_chk_item.document_code AS doc_code,  ")
				.append("        stg_chk_item.doc_item_ref AS doc_no, stg_chk_item.doc_description,    ")
				.append("        stg_chk_item.doc_reference AS doc_ref, stg_chk_item.doc_date,         ")
				.append("        stg_chk_item.expiry_date, stg_chk_item.status,                        ")
				.append("        stg_chk_item.remarks AS narration,                                    ")
				.append("        lp.llp_le_id AS le_id,                                                ")
				.append("        sp.lsp_short_name AS le_name, sec.sci_security_dtl_id                 ")
				.append("   FROM transaction trx,                                                      ")
				.append(
						"        stage_checklist stg_chk LEFT OUTER JOIN cms_security sec ON sec.CMS_COLLATERAL_ID = stg_chk.CMS_COLLATERAL_ID, ")
				.append("        stage_checklist_item stg_chk_item,                                    ").append(
						"        sci_lsp_lmt_profile lp,                                               ").append(
						"        sci_le_sub_profile sp                                                 ").append(
						"  WHERE stg_chk_item.doc_item_ref                                             ");
		CommonUtil.buildSQLInList(chkListItemRefIDList, sql, params);
		sql.append("    AND trx.staging_reference_id = stg_chk_item.checklist_id                  ").append(
				"    AND stg_chk.checklist_id = stg_chk_item.checklist_id                      ").append(
				"    AND trx.transaction_type = 'CHECKLIST'                                    ").append(
				"    AND stg_chk.cms_lsp_lmt_profile_id = lp.cms_lsp_lmt_profile_id            ").append(
				"    AND lp.cms_customer_id = sp.cms_le_sub_profile_id                         ");

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql.toString());
			CommonUtil.setSQLParams(params, dbUtil);
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				OBReversalCustodian ob = new OBReversalCustodian();
				ob.setDocCode(rs.getString("DOC_CODE"));
				ob.setDocDate(rs.getDate("DOC_DATE"));
				ob.setDocDescription(rs.getString("DOC_DESCRIPTION"));
				ob.setDocExpiryDate(rs.getDate("EXPIRY_DATE"));
				ob.setNarration(rs.getString("NARRATION"));
				ob.setDocNo(rs.getString("DOC_NO"));
				ob.setDocRef(rs.getString("DOC_REF"));
				ob.setDocType(rs.getString("DOC_TYPE"));
				ob.setLeName(rs.getString("LE_NAME"));
				ob.setLeID(rs.getString("LE_ID"));
				ob.setSecurityId(rs.getString("sci_security_dtl_id"));
				resultList.add(ob);
			}
			return resultList;
		}
		catch (Exception e) {
			throw new SearchDAOException(e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException(ex);
			}
		}
	}
}