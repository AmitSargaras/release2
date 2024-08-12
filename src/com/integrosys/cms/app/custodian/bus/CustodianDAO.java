/* 
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/CustodianDAO.java,v 1.77 2006/11/19 11:32:05 wltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//java
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICheckListTableConstants;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.eventmonitor.reversalcustodian.OBReversalCustodian;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelopeItem;
import com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelopeItem;

/**
 * DAO for custodian
 * @author $Author: wltan $
 * @version $Revision: 1.77 $
 * @since $Date: 2006/11/19 11:32:05 $ Tag: $Name: $
 */

public class CustodianDAO implements ICustodianDAO {
	private DBUtil dbUtil;

	// sql to find an existing trx for a particular checklist
	/*
	 * private static final String SELECT_CUSTODIAN_TRX =
	 * "select distinct txh.transaction_id"+
	 * " from trans_history txh,cms_stage_cust_doc doc,transaction trx"+
	 * " where txh.transaction_type='" + ICMSConstant.INSTANCE_CUSTODIAN + "'" +
	 * " and txh.STAGING_REFERENCE_ID = doc.CUSTODIAN_DOC_ID"+
	 * " and txh.STATUS != '" + ICMSConstant.STATE_CLOSED + "'"+
	 * " and doc.checklist_id = ? "+ " and trx.status != '" +
	 * ICMSConstant.STATE_CLOSED + "'"+ " and trx.transaction_type = '" +
	 * ICMSConstant.INSTANCE_CUSTODIAN + "'" +
	 * " and txh.transaction_id = trx.transaction_id";
	 */
	private static final String SELECT_CUSTODIAN_TRX = "select max(transaction_id) transaction_id "
			+ "from transaction trx, cms_stage_cust_doc cust " + "where trx.transaction_type = '"
			+ ICMSConstant.INSTANCE_CUSTODIAN + "' " + "and trx.status <> '" + ICMSConstant.STATE_CLOSED + "' "
			+ "and trx.staging_reference_id = cust.CUSTODIAN_DOC_ID " + "and cust.checklist_id = ? ";

	private static final String SELECT_CHECKLIST_ITEM = "SELECT chk.* , " + "       itm.* , " + "       cust_itm.*  "
			+ "FROM CMS_CHECKLIST chk, " + "     CMS_CHECKLIST_ITEM itm LEFT OUTER JOIN  "
			+ "     CMS_CUST_DOC_ITEM cust_itm ON cust_itm.CHECKLIST_ITEM_REF_ID = itm.DOC_ITEM_REF "
			+ "WHERE chk.CHECKLIST_ID = itm.CHECKLIST_ID "
			+ "      AND (cust_itm.CHECKLIST_ITEM_REF_ID IS NULL OR cust_itm.STATUS = 'DELETED') "
		//	+ "      AND itm.IN_VAULT = 'Y' " + "      AND itm.CPC_CUST_STATUS = 'RECEIVED' ";
            + "      AND itm.IN_VAULT = 'Y' " + "      AND itm.CPC_CUST_STATUS is not null ";

	
	
	/******************************
	ABCLIMS condition for pending lodgement
	
	1. Document has been perm/temp-uplifted previously. Once document has been perm/temp-uplifted , CSA maker can relodge doc (Status : PERM-UPLIFED / TEMP-UPLIFTED)
	2. (PERM-UPLIFED / TEMP-UPLIFTED) - > Choose Re-lodge (cms_checklist_item.cpc_cust_status = 'PERM_UPLIFT' , 'TEMP_UPLIFT')
	3. Once submitted , ALLOW_PENDING_RELODGE is set to stage_checklist_item.cpc_cust_status
	4. Lodgement memo will not show for operations : UPDATE , TEMP_UPLIFT , PERM_UPLIFT , RECEIVED (stage_cms_checklist_item.cpc_cust_status IN ('PENDING_UPDATE','%UPLIFTED' , 'RECEIVED')
	
	******************************/
	
	
	private static final String PENDING_LODGEMENT_CONDITION = "cms_checklist_item.in_vault = 'Y' "
		+ "AND ( "
		+
		// to filter out received documents that were reversed by custodian
		"  ( " + " stage_checklist_item.cpc_cust_status is not null AND (stage_checklist_item.cpc_cust_status not like '%UPLIFT%' AND stage_checklist_item.cpc_cust_status not like '%RECEIVE%' AND stage_checklist_item.cpc_cust_status not like '%UPDATE%')" + ")"
		+ ")";
	


	/******************************
	ABCLIMS condition for pending withdrawal
	
	1. Document has been lodged previously. Once document has been lodged , CSA maker can perm / temp uplift doc (Status : Lodged)
	2. Lodge - > Choose Perm Uplift / Term Uplift (cms_checklist_item.cpc_cust_status = 'RECEIVED' , 'COMPLETED')
	3. Once submitted , PENDING_TEMP_UPLIFT / PENDING_PERM_UPLIFT is set to stage_checklist_item.status
	4. Once submitted , PENDING_TEMP_UPLIFT / PENDING_PERM_UPLIFT is set to stage_checklist_item.status
	5. Lodgement memo will not show for operations : UPDATE , LODGE , RECEIVED (stage_cms_checklist_item.cpc_cust_status IN ('PENDING_UPDATE','%LODGE' , 'RECEIVED')

	******************************/
	
	
	
	private static final String PENDING_WITHDRAWAL_CONDITION = "cms_checklist_item.in_vault = 'Y' "
		+ "AND ( "
		+
		// to filter out received documents that were reversed by custodian
		"  ( " + " stage_checklist_item.cpc_cust_status is not null AND (stage_checklist_item.cpc_cust_status not like '%LODGE%' AND stage_checklist_item.cpc_cust_status not like '%RECEIVE%' AND stage_checklist_item.cpc_cust_status not like '%UPDATE%')" + ")"
		+ ")";
	
	
//	private static final String PENDING_WITHDRAWAL_CONDITION = "cms_checklist_item.in_vault = 'Y' "
//		+ "AND ( "
//		+
//		// to filter out received documents that were reversed by custodian
//		"  ( " + " stage_checklist_item.status = 'ALLOW_PENDING_LODGE')"
//		+ " OR (stage_checklist_item.status = 'PENDING_UPDATE' AND cms_checklist_item.cpc_cust_status = 'LODGED' )"
//		+ " OR (stage_checklist_item.status = 'PENDING_ALLOW_RELODGE' AND cms_checklist_item.cpc_cust_status = 'PENDING_RELODGE' )"
//		+ ")";
	

	// condition to get pending withdrawal list
	// catered for the following scenarios in the same order :
	// 1. in-vault item allow temp/perm uplift pending checker approval
	// 2. in-vault item allow temp uplift but not temp uplifted yet
	// 3. in-vault item allow perm uplift but not perm uplifted yet
//	private static final String PENDING_WITHDRAWAL_CONDITION = "cms_checklist_item.in_vault = 'Y' " + "AND (" + "  ("
//			+ "	  stage_checklist_item.cpc_cust_status in ('PENDING_ALLOW_TEMP_UPLIFT', 'PENDING_ALLOW_PERM_UPLIFT')"
//			+ "	  AND cms_checklist_item.cpc_cust_status = 'RECEIVED'" + "  ) OR ("
//			+ "	  cms_checklist_item.cpc_cust_status = 'ALLOW_TEMP_UPLIFT'"
//			+ "	  AND cms_cust_doc_item.status <> 'TEMP_UPLIFTED'" + "  ) OR ("
//			+ "	  cms_checklist_item.cpc_cust_status = 'ALLOW_PERM_UPLIFT'"
//			+ "	  AND cms_cust_doc_item.status <> 'PERM_UPLIFTED'" + "  )" + ")";



   private static final String PENDING_REVERSAL_CONDITION = "( cms_cust_doc_item.status<>'DELETED' \n" +
           " AND\n" +
           " custodian_trx.stage_status in ('PENDING_ALLOW_LODGED_REVERSAL',\t 'LODGED_REVERSAL', \t 'PENDING_ALLOW_TEMP_UPLIFT_REVERSAL',\t 'PENDING_ALLOW_PERM_UPLIFT_REVERSAL',\t'PENDING_ALLOW_RELODGE_REVERSAL')\n" +
           "OR\n" +
           " cms_checklist_item.cpc_cust_status in('PENDING_LODGE','LODGED','PENDING_TEMP_UPLIFT','PENDING_PERM_UPLIFT','PENDING_RELODGE')\n" +
           ")";

	private static final String INSERT_REVERSED_CHKLIST_ITEM = "INSERT INTO CMS_REV_CHECKLIST_ITEM ("
			+ "  CMS_REV_CHECKLIST_ITEM_ID,  CATEGORY,  DOCUMENT_CODE,"
			+ "  DOC_ITEM_REF,  DOC_DESCRIPTION,  DOC_REFERENCE,  DOC_DATE,"
			+ "  EXPIRY_DATE,    REMARKS,  REVERSAL_REMARKS, REVERSAL_DATE,  CHECKLIST_ID,"
			+ "  CMS_LSP_LMT_PROFILE_ID, SCI_SECURITY_DTL_ID, CMS_LMP_SUB_PROFILE_ID )"
			+ " VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

	private static final String SELECT_REVERSED_CHKLIST_ITEM_NON_BORROWER = "SELECT CASE  "
			+ "       WHEN stg_chk.CATEGORY = 'S' THEN 'Security' " + "       ELSE stg_chk.CATEGORY "
			+ "       END AS doc_type, " + "       stg_chk_item.DOCUMENT_CODE AS doc_code, "
			+ "       stg_chk_item.DOC_ITEM_REF AS doc_no, " + "       stg_chk_item.DOC_DESCRIPTION, "
			+ "       stg_chk_item.DOC_REFERENCE AS doc_ref, " + "       stg_chk_item.DOC_DATE, "
			+ "       stg_chk_item.EXPIRY_DATE, " + "       stg_chk_item.STATUS, "
			+ "       stg_chk_item.REMARKS AS narration, " + "       trx.REFERENCE_ID, "
			+ "       sp.LSP_SHORT_NAME AS le_name, " + "       sec.SCI_SECURITY_DTL_ID, "
			+ "       mp.LMP_LE_ID AS le_id, " + "       stg_chk.CMS_LMP_SUB_PROFILE_ID, "
			+ "       stg_chk.DOC_ORIG_COUNTRY " + "FROM TRANSACTION trx, "
			+ "     STAGE_CHECKLIST_ITEM stg_chk_item, " + "     SCI_LE_MAIN_PROFILE mp, "
			+ "     SCI_LE_SUB_PROFILE sp, " + "     STAGE_CHECKLIST stg_chk LEFT OUTER JOIN  "
			+ "     CMS_SECURITY sec ON sec.CMS_COLLATERAL_ID = stg_chk.CMS_COLLATERAL_ID "
			+ "WHERE trx.STAGING_REFERENCE_ID = stg_chk_item.CHECKLIST_ID "
			+ "      AND stg_chk.CHECKLIST_ID = stg_chk_item.CHECKLIST_ID "
			+ "      AND trx.TRANSACTION_TYPE = 'CHECKLIST' "
			+ "      AND mp.CMS_LE_MAIN_PROFILE_ID = sp.CMS_LE_MAIN_PROFILE_ID "
			+ "      AND sp.CMS_LE_SUB_PROFILE_ID = stg_chk.CMS_LMP_SUB_PROFILE_ID "
			+ "      AND stg_chk_item.doc_item_ref";

	private static final String SELECT_REVERSED_CHKLIST_ITEM_EXCL_NON_BORROWER = "SELECT CASE  "
			+ "       WHEN stg_chk.CATEGORY = 'S' THEN 'Security' " + "       ELSE stg_chk.CATEGORY "
			+ "       END AS doc_type, " + "       stg_chk_item.DOCUMENT_CODE AS doc_code, "
			+ "       stg_chk_item.DOC_ITEM_REF AS doc_no, " + "       stg_chk_item.DOC_DESCRIPTION, "
			+ "       stg_chk_item.DOC_REFERENCE AS doc_ref, " + "       stg_chk_item.DOC_DATE, "
			+ "       stg_chk_item.EXPIRY_DATE, " + "       stg_chk_item.STATUS, "
			+ "       stg_chk_item.REMARKS AS narration, " + "       trx.REFERENCE_ID, "
			+ "       sp.LSP_SHORT_NAME AS le_name, " + "       sec.SCI_SECURITY_DTL_ID, "
			+ "       lp.LLP_LE_ID AS le_id, " + "       stg_chk.CMS_LSP_LMT_PROFILE_ID, "
			+ "       stg_chk.DOC_ORIG_COUNTRY " + "FROM TRANSACTION trx, "
			+ "     STAGE_CHECKLIST_ITEM stg_chk_item, " + "     SCI_LSP_LMT_PROFILE lp, "
			+ "     SCI_LE_SUB_PROFILE sp, " + "     STAGE_CHECKLIST stg_chk LEFT OUTER JOIN  "
			+ "       CMS_SECURITY sec ON sec.CMS_COLLATERAL_ID = stg_chk.CMS_COLLATERAL_ID "
			+ "WHERE trx.STAGING_REFERENCE_ID = stg_chk_item.CHECKLIST_ID "
			+ "      AND stg_chk.CHECKLIST_ID = stg_chk_item.CHECKLIST_ID "
			+ "      AND trx.TRANSACTION_TYPE = 'CHECKLIST' "
			+ "      AND stg_chk.CMS_LSP_LMT_PROFILE_ID = lp.CMS_LSP_LMT_PROFILE_ID "
			+ "      AND lp.CMS_CUSTOMER_ID = sp.CMS_LE_SUB_PROFILE_ID " + "      AND stg_chk_item.doc_item_ref";

	// CMS - 3043
	public static final String CHECKLIST_STATUS = " SELECT STATUS FROM CMS_CHECKLIST WHERE CHECKLIST_ID = ? ";

    public static final String ENVELOPE_BARCODE_COUNT =  " SELECT count(sec_envelope_item_barcode) as totCount "
             + " FROM cms_sec_envelope_item WHERE "
             + " sec_envelope_id in (SELECT sec_envelope_id FROM cms_sec_envelope WHERE "
             + " sec_lsp_lmt_profile_id = ?) and sec_envelope_item_barcode = ? ";

    //Chee Hong : To determine the uniqueness of the Doc Bar Code, need to check the existence beside itself.
    public static final String DOCITEM_BARCODE_LIST = " SELECT count(custodian_doc_item_barcode) as totCount "
                + " FROM cms_cust_doc_item "
                + " WHERE custodian_doc_item_barcode = ? AND checklist_item_ref_id != ?";

    private static final String SELECT_ENV_ITEM_LOC = "SELECT * FROM CMS_SEC_ENVELOPE_ITEM WHERE SEC_ENVELOPE_ITEM_BARCODE = ? ";
	/**
	 * returns the Custodian SQL based on the criteria
	 */
	private String getCustodianQuery(CustodianSearchCriteria aCustodianSearchCriteria) {

		final String selectPart = "SELECT custodian_trx.reference_id, "
				+ "       getCurrentCustDocItemStatus(custodian_trx.trxstatus, custodian_trx.status, cms_cust_doc_item.status) status, "
				+ "       cms_checklist_item.status item_status, "
				+ "       stage_checklist_item.status stage_item_status, "
				+ "       custodian_trx.reason, "
				+ "       cms_checklist_item.doc_item_ref checklist_item_ref_id, "
				+ "       cms_checklist.cms_lsp_lmt_profile_id cms_lsp_appr_lmts_id, "             
				+ "       cms_checklist.checklist_id, "
				+ "       cms_checklist.cms_pledgor_dtl_id, "
				+ "       cms_checklist.cms_lmp_sub_profile_id, "
				+ "       cms_checklist.cms_collateral_id, "
				+ "       cms_checklist.category, "
				+ "       cms_checklist.sub_category, "
				+ "       custodian_trx.transaction_id, "
				+ "       COALESCE(custodian_trx.trxstatus, 'RECEIVED') trxstatus, "
				+ "       custodian_trx.transaction_date, "
				+ "       cms_checklist_item.document_code, "
				+ "       cms_checklist_item.doc_item_ref, "
				+ "       cms_checklist_item.doc_description, "
				+ "       cms_checklist_item.last_update_date, "
				+ "       cms_checklist_item.remarks narration, "
				+ "       cms_checklist_item.doc_date doc_date, "
				+ "       cms_checklist_item.expiry_date doc_expiry_date, "
				+ "       stage_checklist_item.last_update_date stage_item_last_update, "
				+ "       stage_checklist_item.cpc_cust_status stage_cpc_cust_status, "
				+ "       cms_checklist_item.cpc_cust_status cpc_cust_status, "
				+ "       cms_cust_doc.reversal_remarks reversal_remarks, "
				+ "       cms_cust_doc.reversal_rmk_updated_userinfo, "
				+ "       cms_cust_doc_item.last_update_date custodian_last_update, "
				+ "       cms_cust_doc_item.custodian_doc_item_id, "
				+ "       cms_checklist.status checklist_status, CMS_SEC_ENVELOPE_ITEM.SEC_ENVELOPE_ITEM_ADDR, CMS_SEC_ENVELOPE_ITEM.SEC_ENVELOPE_ITEM_CAB, CMS_SEC_ENVELOPE_ITEM.SEC_ENVELOPE_ITEM_DRW,"
				+ "		  CMS_SEC_ENVELOPE_ITEM.SEC_ENVELOPE_ITEM_BARCODE,\n"
				+ "       sci_le_main_profile.LMP_LONG_NAME BORROWER_NAME, "
				+ "       sci_le_main_profile.LMP_LE_ID BORROWER_LE_ID "
				+ "FROM   cms_checklist  LEFT OUTER JOIN  cms_cust_doc  ON  cms_checklist.checklist_id  = cms_cust_doc.checklist_id    LEFT OUTER JOIN  sci_le_sub_profile  ON  sci_le_sub_profile.CMS_LE_SUB_PROFILE_ID  = cms_checklist.CMS_LMP_SUB_PROFILE_ID   LEFT OUTER JOIN  sci_le_main_profile  ON  sci_le_main_profile.CMS_LE_MAIN_PROFILE_ID  = sci_le_sub_profile.CMS_LE_MAIN_PROFILE_ID, "
				+ "       cms_checklist_item  LEFT OUTER JOIN  cms_cust_doc_item  ON  cms_checklist_item.doc_item_ref  = cms_cust_doc_item.checklist_item_ref_id "
                + "     LEFT OUTER JOIN CMS_SEC_ENVELOPE_ITEM ON cms_cust_doc_item.sec_envelope_barcode=cms_sec_envelope_item.sec_envelope_item_barcode "
                + "   LEFT OUTER JOIN  "
				+ "       ( SELECT cms_stage_cust_doc_item.reason, " + "				   cms_stage_cust_doc_item.status, "
				+ "				   cms_stage_cust_doc_item.checklist_item_ref_id, "
				+ "			  	   cms_stage_cust_doc.checklist_id, " + "				   TRANSACTION.transaction_id, "
				+ "				   TRANSACTION.reference_id, " + "				   TRANSACTION.transaction_type, "
				+ "				   TRANSACTION.transaction_date, " + "				   TRANSACTION.status trxstatus "
				+ "          FROM  TRANSACTION, " + "				   cms_stage_cust_doc, " + "				   cms_stage_cust_doc_item, "
				+ "				   ( SELECT stage_cust_doc_item.checklist_item_ref_id, "
				+ "			 		    	MAX(stage_cust_doc_item.custodian_doc_item_id) custodian_doc_item_id "
				+ "		   			 FROM  cms_stage_cust_doc stage_cust_doc, "
				+ "			 			   cms_stage_cust_doc_item stage_cust_doc_item  "
				+ "		  			 WHERE  stage_cust_doc.custodian_doc_id  = stage_cust_doc_item.custodian_doc_id ";

		StringBuffer sb = new StringBuffer(selectPart);

		if (aCustodianSearchCriteria != null) {

			if (isValidChecklist(aCustodianSearchCriteria)) {

				sb.append(" AND stage_cust_doc.checklist_id = ? ");

			}
			else {
				sb.append(" AND stage_cust_doc.checklist_id ");
				sb.append(" IN (SELECT checklist_id ");
				sb.append("     FROM CMS_CHECKLIST ");
				// Priya - Fix for CMSSP-710 Print Lodgement Memo taking long
				// time and finally TD occurs.
				if (aCustodianSearchCriteria.getLimitProfileID() > com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					sb.append("     WHERE cms_lsp_lmt_profile_id = " + aCustodianSearchCriteria.getLimitProfileID()
							+ ")");
				}
				else if (aCustodianSearchCriteria.getSubProfileID() > com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					sb
							.append("     WHERE cms_lmp_sub_profile_id = " + aCustodianSearchCriteria.getSubProfileID()
									+ ")");
				}
			}

		}

		String baseCondition = "			 GROUP BY  stage_cust_doc_item.checklist_item_ref_id  "
				+ "	        ) max_stage_cust_doc  "
				+ "	  WHERE TRANSACTION.transaction_type  = 'CUSTODIAN' "
				+ "	   AND  TRANSACTION.staging_reference_id  = cms_stage_cust_doc.custodian_doc_id "
				+ "	   AND  cms_stage_cust_doc.custodian_doc_id  = cms_stage_cust_doc_item.custodian_doc_id "
				+ "	   AND  cms_stage_cust_doc_item.custodian_doc_item_id  = max_stage_cust_doc.custodian_doc_item_id "
				+ "    AND  TRANSACTION.status  <> 'CLOSED' "
				+ " ) custodian_trx  ON  cms_checklist_item.doc_item_ref  = custodian_trx.checklist_item_ref_id  , "
				+ "	stage_checklist_item  "
				+ "WHERE cms_checklist.checklist_id  = cms_checklist_item.checklist_id "
				+ "AND	 cms_checklist_item.is_deleted  = 'N' "
				+ "AND	(cms_checklist_item.status  <> 'DELETED' OR (cms_checklist_item.status  = 'DELETED' AND	custodian_trx.status  IS NOT NULL)) "
				+ "AND	stage_checklist_item.doc_item_id  = (SELECT MAX(stage_chklist_item.doc_item_id) "
				+ "		                             FROM  stage_checklist_item stage_chklist_item  "
				+ "		                             WHERE stage_chklist_item.doc_item_ref  = cms_checklist_item.doc_item_ref) ";

		return sb.append(baseCondition).toString();
	}

 private String getCustodianPrintReversalMemoQuery(CustodianSearchCriteria aCustodianSearchCriteria) {

final String selectPart = "\n" +
        "\n" +
            "SELECT custodian_trx.reference_id, getReversalCurrentCustDocItemStatus(COALESCE(custodian_trx.trxstatus,''),COALESCE(custodian_trx.status,''), COALESCE(cms_cust_doc_item.status,''), COALESCE(cms_checklist_item.cpc_cust_status,'')) status, cms_checklist_item.status item_status,     \n" +
            "stage_checklist_item.status stage_item_status,        custodian_trx.reason,       \n" +
            "cms_checklist_item.doc_item_ref checklist_item_ref_id,        cms_checklist.cms_lsp_lmt_profile_id cms_lsp_appr_lmts_id, \n" +
            "cms_checklist.checklist_id,        cms_checklist.cms_pledgor_dtl_id,        cms_checklist.cms_lmp_sub_profile_id,      \n" +
            "cms_checklist.cms_collateral_id,        cms_checklist.category,        cms_checklist.sub_category,       \n" +
            "custodian_trx.transaction_id,        COALESCE(custodian_trx.trxstatus, 'RECEIVED') trxstatus,     \n" +
            "custodian_trx.transaction_date,        cms_checklist_item.document_code,        cms_checklist_item.doc_item_ref,      \n" +
            "cms_checklist_item.doc_description,        cms_checklist_item.last_update_date,       \n" +
            "cms_checklist_item.remarks narration,        cms_checklist_item.doc_date doc_date,       \n" +
            "cms_checklist_item.expiry_date doc_expiry_date,        stage_checklist_item.last_update_date stage_item_last_update,        \n" +
            "stage_checklist_item.cpc_cust_status stage_cpc_cust_status,        cms_checklist_item.cpc_cust_status cpc_cust_status,   \n" +
            "cms_cust_doc.reversal_remarks reversal_remarks,        cms_cust_doc.reversal_rmk_updated_userinfo,       \n" +
            "cms_cust_doc_item.last_update_date custodian_last_update,        cms_cust_doc_item.custodian_doc_item_id,    \n" +
            "cms_checklist.status checklist_status,        sci_le_main_profile.LMP_LONG_NAME BORROWER_NAME,       \n" +
            "sci_le_main_profile.LMP_LE_ID BORROWER_LE_ID, CMS_SEC_ENVELOPE_ITEM.SEC_ENVELOPE_ITEM_ADDR, CMS_SEC_ENVELOPE_ITEM.SEC_ENVELOPE_ITEM_CAB, CMS_SEC_ENVELOPE_ITEM.SEC_ENVELOPE_ITEM_DRW,\n" +
            "CMS_SEC_ENVELOPE_ITEM.SEC_ENVELOPE_ITEM_BARCODE\n" +
            "FROM   cms_checklist\n" +
            "left outer join  cms_cust_doc ON cms_checklist.checklist_id  = cms_cust_doc.checklist_id\n" +
            "LEFT OUTER JOIN  sci_le_sub_profile  ON sci_le_sub_profile.CMS_LE_SUB_PROFILE_ID  = cms_checklist.CMS_LMP_SUB_PROFILE_ID  \n" +
            "LEFT OUTER JOIN  sci_le_main_profile  ON sci_le_main_profile.CMS_LE_MAIN_PROFILE_ID  = sci_le_sub_profile.CMS_LE_MAIN_PROFILE_ID ,    \n" +
            "cms_checklist_item \n" +
            "LEFT OUTER JOIN  cms_cust_doc_item  ON \n" +
            "cms_checklist_item.doc_item_ref  = cms_cust_doc_item.checklist_item_ref_id \n" +
            "LEFT OUTER JOIN CMS_SEC_ENVELOPE_ITEM ON cms_cust_doc_item.sec_envelope_barcode=cms_sec_envelope_item.sec_envelope_item_barcode\n" +
            "LEFT OUTER JOIN     \n" +
            "( SELECT cms_stage_cust_doc_item.reason, \t\t\t\t   cms_stage_cust_doc_item.status, cms_stage_cust_doc_item.status stage_status,\t\t\n" +
            "cms_stage_cust_doc_item.checklist_item_ref_id, \t\t\t  \t   cms_stage_cust_doc.checklist_id, \t\t\n" +
            "TRANSACTION.transaction_id, \t\t\t\t   TRANSACTION.reference_id, \t\t\t\t   TRANSACTION.transaction_type, \t\t\n" +
            "TRANSACTION.transaction_date, \t\t\t\t   TRANSACTION.status trxstatus     \n" +
            "FROM  TRANSACTION, \t\t\t\n" +
            "cms_stage_cust_doc, \t\t\t\t   cms_stage_cust_doc_item, \n" +
            "\n" +
            " ( SELECT  stage_cust_doc_item.checklist_item_ref_id,\t\t\t\n" +
            "MAX(stage_cust_doc_item.custodian_doc_item_id) custodian_doc_item_id \t\n" +
            "FROM  cms_stage_cust_doc stage_cust_doc, \t\t\t \t\t\t   cms_stage_cust_doc_item stage_cust_doc_item  \t\t  \n" +
            "WHERE  stage_cust_doc.custodian_doc_id  = stage_cust_doc_item.custodian_doc_id \n";


                StringBuffer sb = new StringBuffer(selectPart);

            if (aCustodianSearchCriteria != null) {

                if (isValidChecklist(aCustodianSearchCriteria)) {

                    sb.append(" AND stage_cust_doc.checklist_id = ? ");

                }
                else {
                    sb.append(" AND stage_cust_doc.checklist_id ");
                    sb.append(" IN (SELECT checklist_id ");
                    sb.append("     FROM CMS_CHECKLIST ");
                    // Priya - Fix for CMSSP-710 Print Lodgement Memo taking long
                    // time and finally TD occurs.
                    if (aCustodianSearchCriteria.getLimitProfileID() > com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
                        sb.append("     WHERE cms_lsp_lmt_profile_id = " + aCustodianSearchCriteria.getLimitProfileID()
                                + ")");
                    }
                    else if (aCustodianSearchCriteria.getSubProfileID() > com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
                        sb
                                .append("     WHERE cms_lmp_sub_profile_id = " + aCustodianSearchCriteria.getSubProfileID()
                                        + ")");
                    }
                }

            }

            String baseCondition = "			 GROUP BY  stage_cust_doc_item.checklist_item_ref_id  "
                    + "	        ) max_stage_cust_doc  "
                    + "	  WHERE TRANSACTION.transaction_type  = 'CUSTODIAN' "
                    + "	   AND  TRANSACTION.staging_reference_id  = cms_stage_cust_doc.custodian_doc_id "  
                    + "	   AND  cms_stage_cust_doc.custodian_doc_id  = cms_stage_cust_doc_item.custodian_doc_id "
                    + "	   AND  cms_stage_cust_doc_item.custodian_doc_item_id  = max_stage_cust_doc.custodian_doc_item_id "
                    + "    AND  TRANSACTION.status  <> 'CLOSED' "
                    + " ) custodian_trx  ON  cms_checklist_item.doc_item_ref  = custodian_trx.checklist_item_ref_id  , "
                    + "	stage_checklist_item  "
                    + "WHERE cms_checklist.checklist_id  = cms_checklist_item.checklist_id "
                    + "AND	 cms_checklist_item.is_deleted  = 'N' "
                    + "AND	(cms_checklist_item.status  <> 'DELETED' OR (cms_checklist_item.status  = 'DELETED' AND	custodian_trx.status  IS NOT NULL)) "
                    + "AND	stage_checklist_item.doc_item_id  = (SELECT MAX(stage_chklist_item.doc_item_id) "
                    + "		                             FROM  stage_checklist_item stage_chklist_item  "
                    + "		                             WHERE stage_chklist_item.doc_item_ref  = cms_checklist_item.doc_item_ref) ";

            return sb.append(baseCondition).toString();
        }



    /**
	 * Search for a list of custodian documents based on the criteria
	 * @param aCustodianSearchCriteria - CustodianSearchCriteria
	 * @return SearchResult - the object that contains a list of call custodian
	 *         doc that satisy the search criteria
	 * @throws SearchDAOException if errors
	 */
	public SearchResult searchCustodianDoc(CustodianSearchCriteria aCustodianSearchCriteria) throws SearchDAOException {
		if (aCustodianSearchCriteria == null) {
			throw new SearchDAOException("The CustodianSearchCriteria is null !!!");
		}
		DefaultLogger.debug("CustodianDAO.searchCustodianDoc", "criteria - checklist id   : "
				+ aCustodianSearchCriteria.getCheckListID());
		DefaultLogger.debug("CustodianDAO.searchCustodianDoc", "criteria - lmt profile id : "
				+ aCustodianSearchCriteria.getLimitProfileID());

		int startIndex = aCustodianSearchCriteria.getStartIndex();
		int nItems = aCustodianSearchCriteria.getNItems();
		String theSQL = getSearchCustodianDocSQL(aCustodianSearchCriteria);
		DefaultLogger.debug("CustodianDAO.searchCustodianDoc", ">>> SQL : " +
		 theSQL);
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(theSQL);
			if (aCustodianSearchCriteria.getCheckListID() > com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				dbUtil.setLong(1, aCustodianSearchCriteria.getCheckListID());
				dbUtil.setLong(2, aCustodianSearchCriteria.getCheckListID());
				dbUtil.setLong(3, aCustodianSearchCriteria.getCheckListID());
			}
			rs = dbUtil.executeQuery();
			
			boolean printEnv = false;
			if(aCustodianSearchCriteria.getIsPrintWithdrawal())
				printEnv = true;
			else if(aCustodianSearchCriteria.getIsPrintReversal())
				printEnv = true;
			
				ArrayList list = processResultSet(rs, nItems, aCustodianSearchCriteria.getIsDocItemNarrationRequired(), printEnv);
			rs.close();
			rs = null;
			if (list.size() == 0) {
				return null;
			}

			return new SearchResult(startIndex, list.size(), list.size(), list);
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			throw new SearchDAOException("SQLException in searchCustodianDoc", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in searchCustodianDoc", ex);
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getSearchCustodianDocRecordCount", ex);
			}
		}
	}

	/**
	 * Get a list of new custodian doc items given an existing custodian doc ID.
	 * 
	 * @param custodianDocID - long
	 * @return ICustodianDocItem[]
	 */
	public ICustodianDocItem[] getNewItems(long custodianDocID) throws SearchDAOException {
		if (custodianDocID == ICMSConstant.LONG_INVALID_VALUE) {
			return new ICustodianDocItem[0];
		}

		ArrayList newItemsList = null;
		ResultSet rs = null;
		try {
			StringBuffer buf = new StringBuffer();
			buf.append(SELECT_CHECKLIST_ITEM);
			buf
					.append(" AND chk.checklist_id = (SELECT checklist_id FROM cms_stage_cust_doc WHERE custodian_doc_id = ?)");
			String theSQL = buf.toString();

			DefaultLogger.debug("CustodianDAO.getNewItems", "custodian doc ID : " + custodianDocID);
			DefaultLogger.debug(this,"the sql is----------------------- " + theSQL);
			dbUtil = new DBUtil();
			dbUtil.setSQL(theSQL);
			dbUtil.setLong(1, custodianDocID);
			rs = dbUtil.executeQuery();

			newItemsList = new ArrayList();
			while (rs.next()) {
				newItemsList.add(createNewCustodianDocItem(rs));
			}
			rs.close();
			rs = null;
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getNewItems", ex);
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getNewItems", ex);
			}
		}
		return ((newItemsList == null) || (newItemsList.size() == 0)) ? new ICustodianDocItem[0]
				: (ICustodianDocItem[]) newItemsList.toArray(new ICustodianDocItem[newItemsList.size()]);
	}

	/**
	 * Get a new custodian doc given a checklist ID with no custodian items
	 * created.
	 * 
	 * @param checkListID - long
	 * @return ICustodianDoc
	 * @throws SearchDAOException
	 */
	public ICustodianDoc getNewDoc(long checkListID) throws SearchDAOException {
		if (checkListID == ICMSConstant.LONG_INVALID_VALUE) {
			return null;
		}

		ICustodianDoc doc = null;
		ResultSet rs = null;
		try {

			StringBuffer buf = new StringBuffer();
			buf.append(SELECT_CHECKLIST_ITEM).append(" AND chk.checklist_id = ?");
			String theSQL = buf.toString();
//            System.out.println("the sql==========: " + theSQL );
			DefaultLogger.debug("CustodianDAO.getNewDoc", "checkListID : " + checkListID);

			dbUtil = new DBUtil();
			dbUtil.setSQL(theSQL);
			dbUtil.setLong(1, checkListID);
			rs = dbUtil.executeQuery();

			while (rs.next()) {
				if (doc == null) {
					doc = new OBCustodianDoc();
					// set doc attributes
					doc.setLimitProfileID(rs.getLong("CMS_LSP_LMT_PROFILE_ID"));
					doc.setSubProfileID(rs.getLong("CMS_LMP_SUB_PROFILE_ID"));
					doc.setPledgorID(rs.getLong("CMS_PLEDGOR_DTL_ID"));
					doc.setCollateralID(rs.getLong("CMS_COLLATERAL_ID"));
					doc.setCheckListID(rs.getLong("CHECKLIST_ID"));
					doc.setDocType(rs.getString("CATEGORY"));
					doc.setDocSubType(rs.getString("SUB_CATEGORY"));
					// if (doc.getDocSubType() == null) {
					// doc.setDocSubType(doc.getDocType());
					// }

				}
				doc.addCustodianDocItem(createNewCustodianDocItem(rs));
			}
			rs.close();
			rs = null;

		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getNewDoc", ex);
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getNewDoc", ex);
			}
		}
		return doc;

	}

	/**
	 * Helper method to get a new custodian doc item.
	 * 
	 * @param rs the result set
	 * @return ICustodianDocItem
	 */
	private ICustodianDocItem createNewCustodianDocItem(ResultSet rs) throws SQLException {
		long checklist_item_ref_id = rs.getLong(ITEM_DOC_ITEM_REF);
		ICustodianDocItem item = new OBCustodianDocItem();
		// checklist item will be populated in proxy
		ICheckListItem dummyItem = new OBCheckListItem();
		dummyItem.setCheckListItemRef(checklist_item_ref_id);
		item.setCheckListItem(dummyItem);
		item.setStatus("RECEIVED");
        //item.setStatus("PENDING_LODGE");

		return item;
	}

	/**
	 * Get the custodian trx ID given a set of search criteria
	 * 
	 * @param searchCriteria - CustodianSearchCriteria
	 * @return long - the custodian trx ID
	 * @return ICMSConstant.LONG_INVALID_VALUE if no such custodian trx found.
	 */
	public long getTrxID(CustodianSearchCriteria searchCriteria) throws SearchDAOException {

		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SELECT_CUSTODIAN_TRX);
			// DefaultLogger.debug(this,
			// "<<< SELECT_CUSTODIAN_TRX: "+SELECT_CUSTODIAN_TRX);
			if (searchCriteria.getCheckListID() > com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				dbUtil.setLong(1, searchCriteria.getCheckListID());
			}

			rs = dbUtil.executeQuery();
			long trxID = (rs.next() && (rs.getLong("transaction_id") != 0)) ? rs.getLong("transaction_id")
					: ICMSConstant.LONG_INVALID_VALUE;
			rs.close();
			rs = null;

			return trxID;
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getTrxID", ex);
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getTrxID", ex);
			}
		}
	}

	/**
	 * Helper method to formulate the sql to search custodian doc, given
	 * criteria object.
	 * 
	 * @param aCustodianSearchCriteria
	 * @return
	 * @throws SearchDAOException
	 */
	private String getSearchCustodianDocSQL(CustodianSearchCriteria aCustodianSearchCriteria) throws SearchDAOException {
		if (aCustodianSearchCriteria == null) {
			throw new SearchDAOException("Criteria is null!!!");
		}

		// formulate sql
		StringBuffer buf = new StringBuffer();
		// buf.append(SELECT_CUSTODIAN_DOC);
        	if (aCustodianSearchCriteria.getIsPrintReversal()) {
			// filters print lodgement list based on statuses
                buf.append(getCustodianPrintReversalMemoQuery(aCustodianSearchCriteria));

            }

		else {

                buf.append(getCustodianQuery(aCustodianSearchCriteria));

		     }

		String condition = getSQLCondition(aCustodianSearchCriteria);

		if (!isEmpty(condition)) {
			buf.append(" AND ").append(condition);
		}
		appendOrderByCriteria(buf, aCustodianSearchCriteria);

		return buf.toString().trim();
	}

	/**
	 * Method to form the additional conditions to search custodian doc, given
	 * the criteria object
	 * @param aCustodianSearchCriteria - CustodianDocSearchCriteria
	 * @return String - the formatted sql condition
	 * @throws SearchDAOException if errors
	 */
	private String getSQLCondition(CustodianSearchCriteria aCustodianSearchCriteria) throws SearchDAOException {
		if (aCustodianSearchCriteria == null) {
			throw new SearchDAOException("Criteria is null!!!");
		}

		StringBuffer strBuffer = new StringBuffer();
		if (!aCustodianSearchCriteria.getForMemoPrinting()) {
			if (aCustodianSearchCriteria.getLimitProfileID() > com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				strBuffer.append(CHECKLIST_LIMIT_PROFILE_ID_PREF);
				strBuffer.append(" = ");
				strBuffer.append(aCustodianSearchCriteria.getLimitProfileID());
				strBuffer.append(" ");
			}

			if (aCustodianSearchCriteria.getSubProfileID() > com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				if (!isEmpty(strBuffer.toString())) {
					strBuffer.append(" AND ");
				}
				strBuffer.append(CHECKLIST_SUB_PROFILE_ID_PREF);
				strBuffer.append(" = ");
				strBuffer.append(aCustodianSearchCriteria.getSubProfileID());
				strBuffer.append(" ");
			}

			if (aCustodianSearchCriteria.getPledgorID() > com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				if (!isEmpty(strBuffer.toString())) {
					strBuffer.append(" AND ");
				}
				strBuffer.append(CHECKLIST_PLEDGOR_ID_PREF);
				strBuffer.append(" = ");
				strBuffer.append(aCustodianSearchCriteria.getPledgorID());
				strBuffer.append(" ");
			}

			if (aCustodianSearchCriteria.getCollateralID() > com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				if (!isEmpty(strBuffer.toString())) {
					strBuffer.append(" AND ");
				}
				strBuffer.append(CHECKLIST_COLLATERAL_ID_PREF);
				strBuffer.append(" = ");
				strBuffer.append(aCustodianSearchCriteria.getCollateralID());
				strBuffer.append(" ");
			}

			/*
			 * append the checklist id under all circumstances a valid checklist
			 * id returns good results an invalid checklist id returns no
			 * results without checklist id - it returns BIG results
			 */

			// DefaultLogger.debug(this, "ChklistID: " +
			// aCustodianSearchCriteria.getCheckListID());
			// DefaultLogger.debug(this, "LmtProfileID: " +
			// aCustodianSearchCriteria.getLimitProfileID());
			if (aCustodianSearchCriteria.getCheckListID() > com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				appendChecklistCond(strBuffer);
			}

			/*
			 * commented out - checklist id is used for search the
			 * doc-type/sub-type is not necessary since one checklist can only
			 * belong to a single doc type
			 */

			/*
			 * if (!isEmpty(aCustodianSearchCriteria.getDocType())) { if
			 * (!isEmpty(strBuffer.toString())) strBuffer.append(" AND ");
			 * strBuffer.append(CHECKLIST_TABLE + "." + DOC_TYPE);
			 * strBuffer.append(" = '");
			 * strBuffer.append(aCustodianSearchCriteria.getDocType());
			 * strBuffer.append("'"); }
			 * 
			 * if (!isEmpty(aCustodianSearchCriteria.getDocSubType())) { if
			 * (!isEmpty(strBuffer.toString())) strBuffer.append(" AND ");
			 * strBuffer.append(CHECKLIST_TABLE + ". " + DOC_SUB_TYPE);
			 * strBuffer.append(" = '");
			 * strBuffer.append(aCustodianSearchCriteria.getDocSubType());
			 * strBuffer.append("'"); }
			 */

		}
		else {
			// DefaultLogger.debug(this, "LimitProfile ID: " +
			// aCustodianSearchCriteria.getLimitProfileID());
			strBuffer.append("(");
			if (aCustodianSearchCriteria.getLimitProfileID() > com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				strBuffer.append(CHECKLIST_LIMIT_PROFILE_ID_PREF);
				strBuffer.append(" = ");
				strBuffer.append(aCustodianSearchCriteria.getLimitProfileID());
			}

			if (aCustodianSearchCriteria.getSubProfileID() > com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				if (!isEmpty(strBuffer.toString())) {
					strBuffer.append(" OR ");
				}
				strBuffer.append("(");
				strBuffer.append(CHECKLIST_SUB_PROFILE_ID_PREF);
				strBuffer.append(" = ");
				strBuffer.append(aCustodianSearchCriteria.getSubProfileID());
				strBuffer.append(" AND ");
				strBuffer.append(CHECKLIST_TABLE + "." + DOC_SUB_TYPE);
				strBuffer.append(" = '");
				strBuffer.append(ICMSConstant.CHECKLIST_NON_BORROWER);
				strBuffer.append("')");
			}
			strBuffer.append(") ");
		}
		String trxStatus[] = aCustodianSearchCriteria.getTrxStatus();
		if ((trxStatus != null) && (trxStatus.length > 0)) {
			strBuffer.append(" AND (");
			for (int i = 0; i < trxStatus.length; i++) {
				if (i > 0) {
					strBuffer.append(" OR ");
				}
				if (!isEmpty(strBuffer.toString())) {
					strBuffer.append("custodian_trx.status = '");
					strBuffer.append(trxStatus[i]);
					strBuffer.append("'");
				}
			}
			strBuffer.append(") ");
		}

		// bernard - old implementation, not in use cos getCPCCustodianStatus
		// will return null
		String cpcCustStatus[] = aCustodianSearchCriteria.getCPCCustodianStatus();
		if ((cpcCustStatus != null) && (cpcCustStatus.length > 0)) {
			strBuffer.append(" AND (");
			for (int i = 0; i < cpcCustStatus.length; i++) {
				if (i > 0) {
					strBuffer.append(" OR ");
				}
				if (!isEmpty(strBuffer.toString())) {
					strBuffer.append(CHECKLIST_ITEM);
					strBuffer.append(".");
					strBuffer.append(ITEM_CPC_CUST_STATUS);
					strBuffer.append(" = '");
					strBuffer.append(cpcCustStatus[i]);
					strBuffer.append("'");
				}
			}
			strBuffer.append(") ");
		}

		// bernard - old implementation, not in use cos
		// getCPCCustodianStatusExclude will return null
		String[] cpcCustStatusExclude = aCustodianSearchCriteria.getCPCCustodianStatusExclude();
		if ((cpcCustStatusExclude != null) && (cpcCustStatusExclude.length > 0)) {
			strBuffer.append(" AND (custodian_trx.status IS NULL OR custodian_trx.status NOT IN (");
			for (int i = 0; i < cpcCustStatusExclude.length; i++) {
				if (i > 0) {
					strBuffer.append(", ");
				}
				strBuffer.append("'");
				strBuffer.append(cpcCustStatusExclude[i]);
				strBuffer.append("'");
			}
			strBuffer.append(")) ");
		}

		// weiling - new implementation
		// filters lodgement/withdrawal list based on statuses
		if (aCustodianSearchCriteria.getIsPrintLodgement()) {
			// filters print lodgement list based on statuses
			strBuffer.append(" AND ");
			strBuffer.append(PENDING_LODGEMENT_CONDITION);
		}
		else if (aCustodianSearchCriteria.getIsPrintWithdrawal()) {
			// filters print withdrawal list based on statuses
			strBuffer.append(" AND ");
			strBuffer.append(PENDING_WITHDRAWAL_CONDITION);
		}
        else if (aCustodianSearchCriteria.getIsPrintReversal()) {
			// filters print withdrawal list based on statuses
			strBuffer.append(" AND ");
			strBuffer.append(PENDING_REVERSAL_CONDITION);
		}
        else {
			// others
			strBuffer.append(" AND ");
			strBuffer.append(CHECKLIST_ITEM + "." + ITEM_CPC_CUST_STATUS);
			strBuffer.append(" IS NOT NULL");
		}

		// weiling - old implementation
		// filters lodgement/withdrawal list according to statuses set in
		// checkListCustodianStatus
		/*
		 * CheckListCustodianStatus[] checkListCustodianStatus =
		 * aCustodianSearchCriteria.getCheckListCustodianStatus(); if
		 * (checkListCustodianStatus!=null && checkListCustodianStatus.length>0)
		 * { strBuffer.append(" AND ("); for (int i=0;
		 * i<checkListCustodianStatus.length; i++) { boolean andRequired =
		 * false; if (i>0) strBuffer.append(" OR "); strBuffer.append("("); if
		 * (checkListCustodianStatus[i].getStageCPCStatus()!=null) //if staging
		 * cpc status is null, do not include it { if (andRequired)
		 * strBuffer.append(" AND ");
		 * strBuffer.append(STAGE_CHECKLIST_ITEM_TABLE); strBuffer.append(".");
		 * strBuffer.append(ITEM_STATUS); if
		 * (!checkListCustodianStatus[i].getStageCPCStatus().equals("")) {
		 * strBuffer.append(" = '");
		 * strBuffer.append(checkListCustodianStatus[i].getStageCPCStatus());
		 * strBuffer.append("'"); } else //if empty space, assign IS NULL {
		 * strBuffer.append(" IS NULL"); } andRequired = true; } if
		 * (checkListCustodianStatus[i].getStageCPCCustStatus()!=null) //if
		 * staging cpc cust status is null, do not include it { if (andRequired)
		 * strBuffer.append(" AND ");
		 * strBuffer.append(STAGE_CHECKLIST_ITEM_TABLE); strBuffer.append(".");
		 * strBuffer.append(ITEM_CPC_CUST_STATUS); if
		 * (!checkListCustodianStatus[i].getStageCPCCustStatus().equals("")) {
		 * strBuffer.append(" = '");
		 * strBuffer.append(checkListCustodianStatus[i]
		 * .getStageCPCCustStatus()); strBuffer.append("'"); } else //if empty
		 * space, assign IS NULL { strBuffer.append(" IS NULL"); } andRequired =
		 * true; } if (checkListCustodianStatus[i].getCPCStatus()!=null) //if
		 * cpc status is null, do not include it { if (andRequired)
		 * strBuffer.append(" AND "); strBuffer.append(CHECKLIST_ITEM);
		 * strBuffer.append("."); strBuffer.append(ITEM_STATUS); if
		 * (!checkListCustodianStatus[i].getCPCStatus().equals("")) {
		 * strBuffer.append(" = '");
		 * strBuffer.append(checkListCustodianStatus[i].getCPCStatus());
		 * strBuffer.append("'"); } else //if empty space, assign IS NULL {
		 * strBuffer.append(" IS NULL"); } andRequired = true; } if
		 * (checkListCustodianStatus[i].getCPCCustStatus()!=null) //if cpc cust
		 * status is null, do not include it { if (andRequired)
		 * strBuffer.append(" AND "); strBuffer.append(CHECKLIST_ITEM);
		 * strBuffer.append("."); strBuffer.append(ITEM_CPC_CUST_STATUS); if
		 * (!checkListCustodianStatus[i].getCPCCustStatus().equals("")) {
		 * strBuffer.append(" = '");
		 * strBuffer.append(checkListCustodianStatus[i].getCPCCustStatus());
		 * strBuffer.append("'"); } else //if empty space, assign IS NULL {
		 * strBuffer.append(" IS NULL"); } andRequired = true; } if
		 * (checkListCustodianStatus[i].getCustStatus()!=null) //if cust status
		 * is null, do not include it { if (andRequired) {
		 * strBuffer.append(" AND "); }
		 * 
		 * strBuffer.append("custodian_trx.status"); if
		 * (!checkListCustodianStatus[i].getCustStatus().equals("")) {
		 * strBuffer.append(" = '");
		 * strBuffer.append(checkListCustodianStatus[i].getCustStatus());
		 * strBuffer.append("'"); } else //if empty space, assign IS NULL {
		 * strBuffer.append(" IS NULL"); } andRequired = true; } if
		 * (checkListCustodianStatus[i].getTrxStatus()!=null) //if transaction
		 * status is null, do not include it { if (andRequired) {
		 * strBuffer.append(" AND "); }
		 * strBuffer.append("custodian_trx.trxstatus"); if
		 * (!checkListCustodianStatus[i].getTrxStatus().equals("")) {
		 * strBuffer.append(" = '");
		 * strBuffer.append(checkListCustodianStatus[i].getTrxStatus());
		 * strBuffer.append("'"); } else //if empty space, assign IS NULL {
		 * strBuffer.append(" IS NULL"); } andRequired = true; }
		 * strBuffer.append(")"); } strBuffer.append(") "); }
		 */

		String[] countryCodes = aCustodianSearchCriteria.getCountryCodes();
		if ((countryCodes != null) && (countryCodes.length > 0)) {
			strBuffer.append(" AND ");
			strBuffer.append(CHECKLIST_DOC_LOC_CTRY_PREF);
			strBuffer.append(" IN (");
			for (int i = 0; i < countryCodes.length; i++) {
				if (i > 0) {
					strBuffer.append(", ");
				}
				// DefaultLogger.debug(this,
				// "> Appending Country code["+i+"]="+countryCodes[i]);
				strBuffer.append("'");
				strBuffer.append(countryCodes[i]);
				strBuffer.append("'");
			}
			strBuffer.append(") ");
		}

		String[] orgCodes = aCustodianSearchCriteria.getOrganisationCodes();
		if ((orgCodes != null) && (orgCodes.length > 0)) {
			strBuffer.append(" AND (");
			strBuffer.append(CHECKLIST_DOC_LOC_ORG_PREF);
			strBuffer.append(" IN (");
			for (int i = 0; i < orgCodes.length; i++) {
				if (i > 0) {
					strBuffer.append(", ");
				}
				// DefaultLogger.debug(this,
				// "> Appending Organisation code["+i+"]="+orgCodes[i]);
				strBuffer.append("'");
				strBuffer.append(orgCodes[i]);
				strBuffer.append("'");
			}
			strBuffer.append(") OR ");
			strBuffer.append(CHECKLIST_DOC_LOC_ORG_PREF);
			strBuffer.append(" IS NULL) ");
		}

		return strBuffer.toString().trim();
	}

	private boolean isValidChecklist(CustodianSearchCriteria aCustodianSearchCriteria) {
		return aCustodianSearchCriteria.getCheckListID() > ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * appends filter condition using checklist id
	 * @param strBuffer
	 */
	private void appendChecklistCond(StringBuffer strBuffer) {
		if (!isEmpty(strBuffer.toString())) {
			strBuffer.append(" AND ");
		}
		strBuffer.append("cms_checklist.checklist_id = ? ");
		strBuffer.append("AND (custodian_trx.checklist_id IS NULL OR custodian_trx.checklist_id = ?)");
	}

	/**
	 * Helper method to append order by criteria
	 * 
	 * @param aCustodianSearchCriteria
	 */
	private void appendOrderByCriteria(StringBuffer strBuffer, CustodianSearchCriteria aCustodianSearchCriteria) {
		String firstSort = aCustodianSearchCriteria.getFirstSort();
		String secondSort = aCustodianSearchCriteria.getSecondSort();
		if (!isEmpty(firstSort)) {
			strBuffer.append(" ORDER BY ");
			strBuffer.append(firstSort.trim());
			if ((!isEmpty(secondSort)) && (secondSort.equalsIgnoreCase(firstSort))) {
				strBuffer.append(", ");
				strBuffer.append(secondSort.trim());
			}
		}
	}

	/**
	 * Utiloty method to check if a string value is null or empty
	 * @param aValue - String
	 * @return boolean - true if empty and false otherwise
	 */
	private boolean isEmpty(String aValue) {
		return !((aValue != null) && (aValue.trim().length() > 0));
	}

	/**
	 * Process the custodian doc search result
	 * @param aResultSet - ResultSet
	 * @param aCountRequired - int
	 * @param isDocItemNarrationRequired - boolean
	 * @return ArrayList - the list of custodian doc from the resultset
	 * @throws SQLException if errors
	 */
	private ArrayList processResultSet(ResultSet aResultSet, int aCountRequired, boolean isDocItemNarrationRequired, boolean getIsPrintReversal)
			throws SQLException, SearchDAOException {
		ArrayList resultList = new ArrayList();
		HashMap custDocMap = new HashMap();

		long previousCheckListID = ICMSConstant.LONG_INVALID_VALUE;
		long currentCheckListID = ICMSConstant.LONG_INVALID_VALUE;
		OBCustodianDocSearchResult custDoc = null;
		while (aResultSet.next()) {
			currentCheckListID = aResultSet.getLong("checklist_id");

			// moving on to the next set of custodian doc
			if ((previousCheckListID != ICMSConstant.LONG_INVALID_VALUE) && (currentCheckListID != previousCheckListID)) {
				// if required number of documents retrieved, terminate loop
				if ((aCountRequired != 0) && (aCountRequired == resultList.size())) {
					break;
				}
			}

			// check if current custdoc exists
			custDoc = (OBCustodianDocSearchResult) custDocMap.get(new Long(currentCheckListID));

			// create new custdoc
			if (custDoc == null) {
				long custodianDocID = aResultSet.getLong("reference_id");
				custDoc = createCustodianDocSearchResult(custodianDocID, aResultSet,getIsPrintReversal);
				custDocMap.put(new Long(currentCheckListID), custDoc);
				resultList.add(custDoc);
			}

			// create new custdoc item
			custDoc.addCustodianDocItem(createCustodianDocItemSearchResult(aResultSet, isDocItemNarrationRequired,getIsPrintReversal));

			previousCheckListID = currentCheckListID;
		}
		return resultList;
	}

	/**
	 * Helper method to create a new custodian doc from an entry in the
	 * resultset.
	 * 
	 * @param docID
	 * @param aResultSet - ResultSet
	 * @return OBCustodianDocSearchResult
	 */
	private OBCustodianDocSearchResult createCustodianDocSearchResult(long docID, ResultSet aResultSet, boolean getIsPrintReversal)
			throws SQLException, SearchDAOException {
		OBCustodianDocSearchResult custDoc = new OBCustodianDocSearchResult();
		custDoc.setCustodianDocID(docID);
		// CR34
  		custDoc.setReversalRemarks(aResultSet.getString("reversal_remarks"));
		custDoc.setReversalRmkUpdatedUserInfo(aResultSet.getString("reversal_rmk_updated_userinfo"));
		custDoc.setCheckListID(aResultSet.getLong("CHECKLIST_ID"));
		custDoc.setCheckListStatus(aResultSet.getString("checklist_status"));
		custDoc.setTrxId(aResultSet.getString(TRX_ID));
		custDoc.setTrxStatus(aResultSet.getString(TRX_STATUS_ALIAS));
		custDoc.setCategory(aResultSet.getString(ICheckListTableConstants.CHKTBL_CATEGORY));
		custDoc.setSubCategory(aResultSet.getString(ICheckListTableConstants.CHKTBL_SUB_CATEGORY));
		custDoc.setLimitProfileID(aResultSet.getLong(LIMIT_PROFILE_ID));
		custDoc.setSubProfileID(aResultSet.getLong(ICheckListTableConstants.CHKTBL_BORROWER_ID));
		custDoc.setCollateralID(aResultSet.getLong(ICheckListTableConstants.CHKTBL_COLLATERAL_ID));
		custDoc.setPledgorID(aResultSet.getLong(ICheckListTableConstants.CHKTBL_PLEDGER_ID));
		custDoc.setLegalID(aResultSet.getString("borrower_le_id"));  //edit
		custDoc.setLegalName(aResultSet.getString("borrower_name"));
		custDoc.setCustomerList(getCustomerListByCollateralID(aResultSet
				.getLong(ICheckListTableConstants.CHKTBL_COLLATERAL_ID)));
		return custDoc;
	}

	private ArrayList getCustomerListByCollateralID(long collateralID) throws SearchDAOException {
		try {
			ArrayList customerList = (ArrayList) CheckListProxyManagerFactory.getCheckListProxyManager()
					.getCustomerListByCollateralID(collateralID);
			return customerList;
		}
		catch (Exception e) {
			throw new SearchDAOException("throw exception at getCustomerListByCollateralID:" + e.toString());
		}
	}

	/**
	 * Helper method to create a new custodian doc item from an entry in the
	 * resultset.
	 * 
	 * @param aResultSet - ResultSet
	 * @param isDocItemNarrationRequired - boolean
	 * @return OBCustodianDocItemSearchResult
	 */
	private OBCustodianDocItemSearchResult createCustodianDocItemSearchResult(ResultSet aResultSet,
			boolean isDocItemNarrationRequired,boolean getIsPrintReversal) throws SQLException {
		OBCustodianDocItemSearchResult custDocItem = new OBCustodianDocItemSearchResult();
		long itemID = aResultSet.getLong("custodian_doc_item_id");
		custDocItem.setCustodianDocItemID((itemID == 0) ? ICMSConstant.LONG_INVALID_VALUE : itemID);
		custDocItem.setDocNo(aResultSet.getString(ITEM_DOCUMENT_CODE));
		custDocItem.setDocItemRef(aResultSet.getLong(ITEM_DOC_ITEM_REF));
		custDocItem.setDocDescription(aResultSet.getString(ITEM_DOC_DESCRIPTION));
		// if(aResultSet.getString(CHECKLIST_STATUS)!=null)
		custDocItem.setStatus(aResultSet.getString(STATUS));
		custDocItem.setItemStatus(aResultSet.getString(ITEM_STATUS_ALIAS));
		custDocItem.setStageItemStatus(aResultSet.getString(STAGE_ITEM_STATUS_ALIAS));
		custDocItem.setDocType(aResultSet.getString(DOC_TYPE));
		custDocItem.setCheckListItemID(aResultSet.getLong(ITEM_DOC_ITEM_REF));
		custDocItem.setCPCCustDate(aResultSet.getDate(ITEM_LAST_UPDATE_DATE));
		custDocItem.setStageCPCDate(aResultSet.getDate(STAGE_CHECKLIST_ITEM_LAST_UPDATE_ALIAS));

		// CR130 : to display item narration in lodgement/withdrawal list
		if (isDocItemNarrationRequired) {
			String narration = aResultSet.getString("narration");
			custDocItem.setDocItemNarration(((narration == null) || (narration.length() == 0)) ? null : narration);
			custDocItem.setDocDate(aResultSet.getDate("doc_date"));
			custDocItem.setDocExpiryDate(aResultSet.getDate("doc_expiry_date"));
		}

        if(getIsPrintReversal)
        {
             		custDocItem.setSecEnvAdd(aResultSet.getString("SEC_ENVELOPE_ITEM_ADDR"));
            custDocItem.setSecEnvCab(aResultSet.getString("SEC_ENVELOPE_ITEM_CAB"));
                	custDocItem.setSecEnvDrw(aResultSet.getString("SEC_ENVELOPE_ITEM_DRW"));
            custDocItem.setSecEnvBarcode(aResultSet.getString("SEC_ENVELOPE_ITEM_BARCODE"));
        }

        // CMS-2298 : to correctly show the pending or actual cpc cust status
		// to cater to the scenario whereby stage cpc cust status != actual cpc
		// cust status after checker approval
		// new impl in 1.3.1 dictates that a duplicate set of stage chklist is
		// created after checker approval
		// to capture and track checker remarks => in this case, stage cpc cust
		// status == actual cpc cust status upon checker approval
		String stage_cpc_cust_status = aResultSet.getString("stage_cpc_cust_status");
		String actual_cpc_cust_status = aResultSet.getString(ITEM_CPC_CUST_STATUS);
		if (isCPCCustStatusApproved(stage_cpc_cust_status, actual_cpc_cust_status)) {
			custDocItem.setCPCCustStatus(actual_cpc_cust_status);
		}
		else {
			custDocItem.setCPCCustStatus(stage_cpc_cust_status);
		}

		// CMS-1476 : to maintain logic to fix CMS-1476 when getting trx date
		// for item
		Date custDocItemLastUpdateDate = aResultSet.getDate("custodian_last_update");
		if (custDocItem.getCPCCustStatus() == null) {
			// CMS-1394
			custDocItem.setTrxDate(custDocItemLastUpdateDate);
		}
		else {
			// if received and not lodged
			if (custDocItem.getCPCCustStatus().equals(ICMSConstant.STATE_ITEM_RECEIVED)
					&& ((custDocItem.getStatus() == null) || !custDocItem.getStatus().equals(ICMSConstant.STATE_LODGED))) {
				custDocItem.setTrxDate(null);

			}
			else if (custDocItem.getCPCCustStatus().startsWith(ICMSConstant.STATE_ITEM_PENDING_PREFIX)) {
				// get last update date from staging checklist item table
				custDocItem.setTrxDate(custDocItem.getStageCPCDate());
			}
			else {
				// CMS-2183
				// compare the custodian date and the cpc cust date
				// use the later of the two
				Date trxDate = null;
				if ((custDocItem.getCPCCustDate() != null) && (custDocItemLastUpdateDate != null)) {
					trxDate = (custDocItem.getCPCCustDate().compareTo(custDocItemLastUpdateDate) >= 0) ? custDocItem
							.getCPCCustDate() : custDocItemLastUpdateDate;
				}
				else {
					trxDate = custDocItemLastUpdateDate;
				}
				custDocItem.setTrxDate(trxDate);
			}
		}

		//DefaultLogger.debug("CustodianDAO.createCustodiandocItemSearchResult",
		// " >>> item ref : " + custDocItem.getDocItemRef());
		//DefaultLogger.debug("CustodianDAO.createCustodiandocItemSearchResult",
		// " >>> item status : " + custDocItem.getItemStatus());
		//DefaultLogger.debug("CustodianDAO.createCustodiandocItemSearchResult",
		// " >>> item cpc cust status : " + custDocItem.getCPCCustStatus());
		//DefaultLogger.debug("CustodianDAO.createCustodiandocItemSearchResult",
		// " >>> item trx date : " + custDocItem.getTrxDate());

		return custDocItem;
	}

	/**
	 * Helper method to check if the pending CPC cust status has been approved
	 * by comparing the stage and actual checklist item cpc cust status
	 * 
	 * @param stageStatus
	 * @param actualStatus
	 * @return boolean
	 */
	private boolean isCPCCustStatusApproved(String stageStatus, String actualStatus) {
		if (stageStatus == null) {
			return false;
		}
		if (actualStatus == null) {
			return false;
		}
		if (stageStatus.equals(actualStatus)) {
			return true;
		}

		// if stage status is "PENDING_" + suffix and actual status starts with
		// suffix
		// pending status has been approved
		int idx = stageStatus.indexOf(ICMSConstant.STATE_ITEM_PENDING_PREFIX);
		if (idx != -1) {
			String suffix = stageStatus.substring(ICMSConstant.STATE_ITEM_PENDING_PREFIX.length());
			return (actualStatus.startsWith(suffix));
		}

		return true;
	}

	public List getCustodianNotificationInfo(List chkListItemRefIDList, String subType) throws SearchDAOException {
		List resultList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		ArrayList params = new ArrayList();
		// DefaultLogger.debug(this, "\n\n\nsubType = " + subType);
		// For Non-Borrower
		if ((subType != null) && subType.equals(ICMSConstant.CHECKLIST_NON_BORROWER)) {
			sql.append(SELECT_REVERSED_CHKLIST_ITEM_NON_BORROWER);
			CommonUtil.buildSQLInList(chkListItemRefIDList, sql, params);
		}
		else // For Main Borrower, Co Borrower and Pledgor
		{
			sql.append(SELECT_REVERSED_CHKLIST_ITEM_EXCL_NON_BORROWER);
			CommonUtil.buildSQLInList(chkListItemRefIDList, sql, params);
		}
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql.toString());
			CommonUtil.setSQLParams(params, dbUtil);
			rs = dbUtil.executeQuery();
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
				ob.setCheckListID(rs.getLong("REFERENCE_ID"));
				if ((subType != null) && subType.equals(ICMSConstant.CHECKLIST_NON_BORROWER)) {
					ob.setSubprofileID(rs.getString("cms_lmp_sub_profile_id"));
				}
				else {
					ob.setLmtprofileID(rs.getString("cms_lsp_lmt_profile_id"));
				}
				ob.setSecurityId(rs.getString("sci_security_dtl_id"));
				ob.setOriginatingCountry(rs.getString("doc_orig_country"));
				resultList.add(ob);
			}
			return resultList;
		}
		catch (Exception e) {
			throw new SearchDAOException(e);
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException ex) {
				throw new SearchDAOException(ex);
			}
		}
	}

	public List saveReversedChecklistItem(List revItemList, String reversalRemarks, String subType)
			throws SearchDAOException {
		List resultList = new ArrayList();
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(INSERT_REVERSED_CHKLIST_ITEM);

			for (Iterator iterator = revItemList.iterator(); iterator.hasNext();) {
				OBReversalCustodian ob = (OBReversalCustodian) iterator.next();
				String revItemID = (new SequenceManager())
						.getSeqNum(ICMSConstant.SEQUENCE_CMS_REV_CHECKLIST_ITEM, true);
				dbUtil.setString(1, revItemID);
				dbUtil.setString(2, ob.getDocType());
				dbUtil.setString(3, ob.getDocCode());
				dbUtil.setString(4, ob.getDocNo());
				dbUtil.setString(5, ob.getDocDescription());
				dbUtil.setString(6, ob.getDocRef());
				if (ob.getDocDate() != null) {
					dbUtil.setDate(7, new java.sql.Date(ob.getDocDate().getTime()));
				}
				else {
					dbUtil.setDate(7, null);
				}
				if (ob.getDocExpiryDate() != null) {
					dbUtil.setDate(8, new java.sql.Date(ob.getDocExpiryDate().getTime()));
				}
				else {
					dbUtil.setDate(8, null);
				}
				dbUtil.setString(9, ob.getNarration());
				dbUtil.setString(10, reversalRemarks);
				dbUtil.setDate(11, new java.sql.Date(DateUtil.getDate().getTime()));
				dbUtil.setLong(12, ob.getCheckListID());
				// For Non-Borrower limit profile id not inserted
				if ((subType != null) && subType.equals(ICMSConstant.CHECKLIST_NON_BORROWER)) {
					dbUtil.setString(13, null);
				}
				else {
					dbUtil.setString(13, ob.getLmtprofileID());
				}
				dbUtil.setString(14, ob.getSecurityId());
				// For Non-Borrower subprofile id inserted
				if ((subType != null) && subType.equals(ICMSConstant.CHECKLIST_NON_BORROWER)) {
					dbUtil.setString(15, ob.getSubprofileID());
				}
				else {
					dbUtil.setString(15, null);
				}
				dbUtil.executeUpdate();
			}
			return resultList;
		}
		catch (Exception e) {
			throw new SearchDAOException(e);
		}
		finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException ex) {
				throw new SearchDAOException(ex);
			}
		}
	}

	/*
	 * For testing only public static void main(String[] args) { try {
	 * CustodianDAO dao = new CustodianDAO(); CustodianSearchCriteria criteria =
	 * new CustodianSearchCriteria(); criteria.setCheckListID(20041230017081l);
	 * SearchResult result = dao.searchCustodianDoc(criteria);
	 * System.out.println(result); } catch (Exception e) { e.printStackTrace();
	 * } }
	 */

	// Priya -CMS - 3043 - Start
	public String getChecklistStatus(long checklistId) throws Exception {
		String checklistStatus = null;
		StringBuffer sql = new StringBuffer();
		sql.append(CHECKLIST_STATUS);
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql.toString());
			if (checklistId > com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				dbUtil.setLong(1, checklistId);
				rs = dbUtil.executeQuery();
				checklistStatus = (rs.next() && (rs.getString("status") != null)) ? rs.getString("status") : null;
			}
		}
		catch (SQLException ex) {
			DefaultLogger.debug(" SQLException in getChecklistStatus of CustodianDAO ", ex);
		}
		catch (Exception e) {
			DefaultLogger.debug(" Exception in getChecklistStatus of CustodianDAO ", e);
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (Exception e) {
				DefaultLogger.debug(" Exception in getChecklistStatus of CustodianDAO ", e);
			}
		}
		return checklistStatus;
	}

	// CMS - 3043 - end

	// R1.5 CR17 - Relating to Shared Document Items
	public ICustodianDocItem[] getDocByChecklistItemRefID(Long[] itemRefList) throws SearchDAOException {
		StringBuffer buf = new StringBuffer("SELECT * FROM cms_cust_doc_item WHERE checklist_item_ref_id ");
		ArrayList params = new ArrayList();
		CommonUtil.buildSQLInList(itemRefList, buf, params);

		ArrayList resultList = new ArrayList();
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(buf.toString());
			CommonUtil.setSQLParams(params, dbUtil);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				OBCustodianDocItem item = new OBCustodianDocItem();
				item.setCheckListItemRefID(rs.getLong("checklist_item_ref_id"));
				item.setStatus(rs.getString("status"));
				item.setLastUpdateDate(rs.getDate("last_update_date"));
				resultList.add(item);
			}
		}
		catch (Exception e) {
			DefaultLogger.debug(" Exception in getDocByChecklistItemRefID of CustodianDAO ", e);
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (dbUtil != null) {
					dbUtil.close();
				}

			}
			catch (Exception e) {
				DefaultLogger.debug(" Exception in getDocByChecklistItemRefID of CustodianDAO finally method", e);
			}
		}

		return (ICustodianDocItem[]) resultList.toArray(new OBCustodianDocItem[0]);
	}

    public boolean getCheckEnvelopeBarcodeExist(long limitprofile, String envBarcode) throws SearchDAOException {
		int totCount = 0;
        boolean isExist = false;
		StringBuffer sql = new StringBuffer();
		sql.append(ENVELOPE_BARCODE_COUNT);
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql.toString());
			if (limitprofile > com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				dbUtil.setLong(1, limitprofile);
                dbUtil.setString(2, envBarcode);
				rs = dbUtil.executeQuery();
                while (rs.next()){
                    totCount = rs.getInt("totCount");
                }
			}
            if (totCount > 0){
                isExist = true;    
            }
		}
		catch (SQLException ex) {
			DefaultLogger.debug(" 1. SQLException in getEnvelopeBarcodeList of CustodianDAO ", ex);
		}
		catch (Exception e) {
			DefaultLogger.debug(" 2. Exception in getEnvelopeBarcodeList of CustodianDAO ", e);
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (Exception e) {
				DefaultLogger.debug(" 3. Exception in getEnvelopeBarcodeList of CustodianDAO ", e);
			}
		}
		return isExist;
	}

    public boolean getCheckDocItemBarcodeExist(String docItemBarcode, long checkListItemRefID) throws SearchDAOException {
		int totCount = 0;
        boolean isExist = false;

		StringBuffer sql = new StringBuffer();
		sql.append(DOCITEM_BARCODE_LIST);
		ResultSet rs = null;
		try {
            dbUtil = new DBUtil();
			dbUtil.setSQL(sql.toString());
            dbUtil.setString(1, docItemBarcode);
            dbUtil.setLong(2, checkListItemRefID);
			rs = dbUtil.executeQuery();
            while (rs.next()){
                totCount = rs.getInt("totCount");
            }
            if (totCount > 0){
                isExist = true;
            }
		}
		catch (SQLException ex) {
			DefaultLogger.debug(" 1. SQLException in getCheckDocItemBarcodeExist of CustodianDAO ", ex);
		}
		catch (Exception e) {
			DefaultLogger.debug(" 2. Exception in getCheckDocItemBarcodeExist of CustodianDAO ", e);
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (Exception e) {
				DefaultLogger.debug(" 3. Exception in getCheckDocItemBarcodeExist of CustodianDAO ", e);
			}
		}
		return isExist;
	}

    public ISecEnvelopeItem getSecEnvItemLoc(String docItemBarcode) throws SearchDAOException {
        StringBuffer sql = new StringBuffer();
		sql.append(SELECT_ENV_ITEM_LOC);
		ResultSet rs = null;
        OBSecEnvelopeItem item = new OBSecEnvelopeItem();

        try {
            dbUtil = new DBUtil();
			dbUtil.setSQL(sql.toString());
            dbUtil.setString(1, docItemBarcode);
			rs = dbUtil.executeQuery();
            while (rs.next()){
                item.setSecEnvelopeItemId(rs.getLong("SEC_ENVELOPE_ITEM_ID"));
                item.setSecEnvelopeId(rs.getLong("SEC_ENVELOPE_ID"));
                item.setSecEnvelopeItemAddr(rs.getString("SEC_ENVELOPE_ITEM_ADDR"));
                item.setSecEnvelopeItemCab(rs.getString("SEC_ENVELOPE_ITEM_CAB"));
                item.setSecEnvelopeItemDrw(rs.getString("SEC_ENVELOPE_ITEM_DRW"));
                item.setSecEnvelopeItemBarcode(rs.getString("SEC_ENVELOPE_ITEM_BARCODE"));
                item.setStatus(rs.getString("STATUS"));
                item.setSecEnvelopeRefId(rs.getLong("SEC_ENVELOPE_REF_ID"));
            }
		}
		catch (SQLException ex) {
			DefaultLogger.debug(" 1. SQLException in getSecEnvItemLoc of CustodianDAO ", ex);
		}
		catch (Exception e) {
			DefaultLogger.debug(" 2. Exception in getSecEnvItemLoc of CustodianDAO ", e);
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (Exception e) {
				DefaultLogger.debug(" 3. Exception in getCheckDocItemBarcodeExist of CustodianDAO ", e);
			}
		}
        return item;
    }
}