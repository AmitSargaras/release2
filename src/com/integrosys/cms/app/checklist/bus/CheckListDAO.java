/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CheckListDAO.java,v 1.140 2006/11/07 06:01:31 czhou Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.InvalidStatementTypeException;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.chktemplate.bus.DocumentHeldSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentHeld;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentHeldItem;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.collateral.bus.CollateralBusManagerFactory;
import com.integrosys.cms.app.collateral.bus.CollateralDAO;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.DB2DateConverter;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.creditApproval.bus.OBCreditApproval;
import com.integrosys.cms.app.customer.bus.ICriInfo;
import com.integrosys.cms.app.customer.bus.OBSystem;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitDAOConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * DAO for Checklist
 * @author $Author: Abhijit Rudrakshawar $
 * @version $Revision: 1.140 $
 * @since $Date: 2006/11/07 06:01:31 $
 */

public class CheckListDAO implements ICheckListDAO, ICMSTrxTableConstants {
	private DBUtil dbUtil = null;
//Note Replacing BIGINT with NUMBER used in cast function for Db2 to Oracle Migration. Anil Pandey
	private static final String SELECT_CHECKLIST = "SELECT CMS_CHECKLIST.CHECKLIST_ID, CMS_CHECKLIST.CMS_LSP_LMT_PROFILE_ID, CMS_CHECKLIST.MASTERLIST_ID,"
			+ " CMS_CHECKLIST.CMS_COLLATERAL_ID, CMS_CHECKLIST.CMS_LMP_SUB_PROFILE_ID, CMS_CHECKLIST.CMS_PLEDGOR_DTL_ID, "
			+ " CMS_CHECKLIST.CATEGORY, CMS_CHECKLIST.SUB_CATEGORY, CMS_CHECKLIST.STATUS, CMS_CHECKLIST.DOC_ORIG_COUNTRY, CMS_CHECKLIST.APPLICATION_TYPE, "
			+ " CMS_CHECKLIST.DOC_ORIG_ORGANISATION, CMS_CHECKLIST.ALLOW_DELETE_IND,CMS_CHECKLIST.disable_collaboration_ind,CMS_CHECKLIST.CAMDATE,CMS_CHECKLIST.CAMNUMBER,CMS_CHECKLIST.CAMTYPE, CMS_CHECKLIST.IS_LATEST, "
			+ " TRANSACTION.TRANSACTION_ID, TRANSACTION.STATUS AS TRX_STATUS,TRANSACTION.FROM_STATE "
			+ " FROM CMS_CHECKLIST, TRANSACTION "
			+ " WHERE TRANSACTION.REFERENCE_ID = CMS_CHECKLIST.CHECKLIST_ID "
			+ " AND TRANSACTION.TRANSACTION_TYPE = 'CHECKLIST' AND TRANSACTION.STATUS <> 'OBSOLETE'";

	private static final String SELECT_CHECKLIST_SHARE = " SELECT cms_checklist.CHECKLIST_ID,"
			+ " cms_checklist.CMS_LSP_LMT_PROFILE_ID, " + " cms_checklist.MASTERLIST_ID, "
			+ " cms_checklist.CMS_COLLATERAL_ID, " + " cms_checklist.CMS_LMP_SUB_PROFILE_ID, "
			+ " cms_checklist.CMS_PLEDGOR_DTL_ID,  " + " cms_checklist.CATEGORY,  " + " cms_checklist.SUB_CATEGORY,  "
			+ " cms_checklist.STATUS, " + " cms_checklist.DOC_ORIG_COUNTRY, "
			+ " cms_checklist.DOC_ORIG_ORGANISATION, " + " cms_checklist.ALLOW_DELETE_IND, "
			+ " transaction.TRANSACTION_ID, " + " transaction.STATUS  " + " FROM cms_checklist," + "    transaction,"
			+ "    cms_checklist_item_share " + " WHERE 0=0 "
			+ " AND transaction.REFERENCE_ID = cms_checklist.CHECKLIST_ID  "
			+ " AND transaction.TRANSACTION_TYPE = 'CHECKLIST'  " + " AND transaction.STATUS <> 'OBSOLETE'  "
			+ " AND cms_checklist.CATEGORY ='S'   "
			+ " AND cms_checklist_item_share.CHECKLIST_ID = cms_checklist.CHECKLIST_ID  "
			+ " AND cms_checklist_item_share.CHECKLIST_ID = cms_checklist.CHECKLIST_ID  "
			+ " AND cms_checklist_item_share.CMS_LSP_LMT_PROFILE_ID = cms_checklist.CMS_LSP_LMT_PROFILE_ID  ";

	private static final String SELECT_STAGE_CHECKLIST = "SELECT STAGE_CHECKLIST.CHECKLIST_ID, STAGE_CHECKLIST.CMS_LSP_LMT_PROFILE_ID, STAGE_CHECKLIST.MASTERLIST_ID, "
			+ " STAGE_CHECKLIST.CMS_COLLATERAL_ID, STAGE_CHECKLIST.CMS_LMP_SUB_PROFILE_ID, STAGE_CHECKLIST.CMS_PLEDGOR_DTL_ID, "
			+ " STAGE_CHECKLIST.CATEGORY, STAGE_CHECKLIST.SUB_CATEGORY, STAGE_CHECKLIST.STATUS, STAGE_CHECKLIST.DOC_ORIG_COUNTRY,"
			+ " STAGE_CHECKLIST.DOC_ORIG_ORGANISATION, STAGE_CHECKLIST.ALLOW_DELETE_IND, STAGE_CHECKLIST.APPLICATION_TYPE, "
			+ " TRANSACTION.TRANSACTION_ID, TRANSACTION.STATUS AS TRX_STATUS,TRANSACTION.FROM_STATE "
			+ " FROM STAGE_CHECKLIST, TRANSACTION WHERE TRANSACTION.STAGING_REFERENCE_ID = STAGE_CHECKLIST.CHECKLIST_ID AND "
			+ " TRANSACTION.TRANSACTION_TYPE = 'CHECKLIST' AND TRANSACTION.STATUS <> 'OBSOLETE'";

	private static final String SELECT_CHECKLIST_ITEM = "SELECT CMS_CHECKLIST_ITEM.CHECKLIST_ID, CMS_CHECKLIST_ITEM.DOC_ITEM_ID, CMS_CHECKLIST_ITEM.DOC_DESCRIPTION,"
			+ " CMS_CHECKLIST_ITEM.DOCUMENT_CODE, CMS_CHECKLIST_ITEM.DOC_ITEM_REF, CMS_CHECKLIST_ITEM.STATUS, "
			+ " CMS_CHECKLIST_ITEM.DEFER_EXPIRY_DATE FROM CMS_CHECKLIST, CMS_CHECKLIST_ITEM, TRANSACTION "
			+ " WHERE TRANSACTION.TRANSACTION_TYPE = 'CHECKLIST' AND TRANSACTION.STATUS = 'ACTIVE' "
			+ " AND TRANSACTION.REFERENCE_ID = CMS_CHECKLIST.CHECKLIST_ID AND "
			+ " CMS_CHECKLIST.CHECKLIST_ID = CMS_CHECKLIST_ITEM.CHECKLIST_ID AND"
			+ " CMS_CHECKLIST_ITEM.IS_DELETED = 'N'";

	private static final String SELECT_CHECKLIST_ITEM_OPERATION = "SELECT STATEID, STATEINS, OPERATION, FROMSTATE, TOSTATE, USERSTATE FROM TR_STATE_MATRIX "
			+ " WHERE STATEINS = 'CHECKLIST_ITEM' ORDER BY STATEID";

	private static final String SELECT_DOC_ITEM_REF = "SELECT doc_item_ref "
			+ "FROM  vw_all_doc_item vw  LEFT OUTER JOIN  CMS_CUST_DOC_ITEM cust  ON  vw.doc_item_ref  = cust.CHECKLIST_ITEM_REF_ID "
			+ "WHERE	 vw.CATEGORY  = 'S' " + "AND	(vw.is_deleted  IS NULL OR	vw.is_deleted  = 'N') "
			+ "AND	vw.monitor_type  = 'INS_POLICY' " + "AND	vw.cms_collateral_id  = ? "
			+ "AND	vw.cms_lsp_lmt_profile_id  = ? " + "AND	vw.list_status  <> 'OBSOLETE' "
			+ "AND	vw.DOC_STATUS  <> 'DELETED' " + "AND	(cust.status  IS NULL OR	cust.status  <> 'PERM_UPLIFTED')";

	private static final String SELECT_DOC_ITEM_INS = "SELECT pol.DOCUMENT_NO FROM CMS_INSURANCE_POLICY pol "
			+ " WHERE pol.STATUS <>'DELETED'" + " AND pol.document_no IS NOT NULL";

	// CR34 : custodian doc migrated from cms_custodian_doc to cms_cust_doc and
	// cms_cust_doc_item
	private static final String SELECT_AUDIT_ITEM = "SELECT	CMS_CHECKLIST.checklist_id, "
			+ "CMS_CHECKLIST.CATEGORY, "
			+ "CMS_CHECKLIST.sub_category, "
			+ "CMS_CHECKLIST.cms_lmp_sub_profile_id, "
			+ "CMS_CHECKLIST.cms_pledgor_dtl_id, "
			+ "CMS_CHECKLIST.cms_collateral_id, "
			+ "CMS_CHECKLIST_ITEM.doc_item_id, "
			+ "CMS_CHECKLIST_ITEM.document_code, "
			+ "CMS_CHECKLIST_ITEM.doc_date, "
			+ "CMS_CHECKLIST_ITEM.doc_description, "
			+ "CMS_CHECKLIST_ITEM.in_vault "
			+ "FROM  CMS_CHECKLIST_ITEM  LEFT OUTER JOIN  CMS_CUST_DOC_ITEM  ON  CMS_CHECKLIST_ITEM.doc_item_ref  = CMS_CUST_DOC_ITEM.checklist_item_ref_id  , "
			+ "CMS_CHECKLIST " + "WHERE CMS_CHECKLIST.checklist_id  = CMS_CHECKLIST_ITEM.checklist_id "
			+ "AND	CMS_CHECKLIST_ITEM.is_audit  = 'Y' " + "AND	(CMS_CUST_DOC_ITEM.status  IS NULL "
			+ "OR	CMS_CUST_DOC_ITEM.status  != 'PERM_UPLIFTED') ";

	private static final String SELECT_DOC_CAT_EXCL_NON_BORROWER = "SELECT " + "cms_checklist.checklist_id, "
			+ "cms_checklist.category, " + "cms_checklist.sub_category, " + "cms_checklist.doc_orig_country, "
			+ "cms_checklist.cms_lmp_sub_profile_id borrower_id, "
			+ "sci_le_main_profile.lmp_long_name borrower_name, " + "sci_le_main_profile.lmp_le_id borrower_le_id, "
			+ "CAST(NULL AS NUMBER) pledgor_id, " + "CAST(NULL AS VARCHAR(100)) pledgor_name, "
			+ "CAST(NULL AS VARCHAR(20)) pledgor_le_id, " + "CAST(NULL AS NUMBER) cms_pledgor_id, "
			+ "CAST(NULL AS NUMBER) collateral_id, " + "CAST(NULL AS VARCHAR(50)) sci_security_id, "
			+ "CAST(NULL AS VARCHAR(40)) security_type_name, " + "CAST(NULL AS VARCHAR(60)) security_sub_type_name "
			+ "FROM  sci_le_main_profile, " + "	 sci_le_sub_profile, " + "	 cms_checklist  "
			+ "WHERE sci_le_main_profile.cms_le_main_profile_id  = sci_le_sub_profile.cms_le_main_profile_id "
			+ "AND	 sci_le_sub_profile.cms_le_sub_profile_id  = cms_checklist.cms_lmp_sub_profile_id "
			+ "AND	 cms_checklist.category  = 'CC' "
			+ "AND	 cms_checklist.sub_category  IN ( 'MAIN_BORROWER' , 'CO_BORROWER', 'JOINT_BORROWER' ) "
			+ "AND	 cms_checklist.status  <> 'OBSOLETE' " + "AND	 cms_checklist.cms_lsp_lmt_profile_id  = ? "
			+ "UNION " + "SELECT " + "cms_checklist.checklist_id, " + "cms_checklist.category, "
			+ "cms_checklist.sub_category, " + "cms_checklist.doc_orig_country, "
			+ "CAST(NULL AS DECIMAL) borrower_id, " + "CAST(NULL AS VARCHAR(100)) borrower_name, "
			+ "CAST(NULL AS VARCHAR(20)) borrower_le_id, " + "sci_pledgor_dtl.plg_pledgor_id pledgor_id, "
			+ "sci_pledgor_dtl.plg_legal_name pledgor_name, " + "sci_pledgor_dtl.plg_le_id pledgor_le_id, "
			+ "cms_checklist.cms_pledgor_dtl_id cms_pledgor_id, " + "CAST(NULL AS NUMBER) collateral_id, "
			+ "CAST(NULL AS VARCHAR(50)) sci_security_id, " + "CAST(NULL AS VARCHAR(40)) security_type_name, "
			+ "CAST(NULL AS VARCHAR(60)) security_sub_type_name " + "FROM  sci_pledgor_dtl, " + "	 cms_checklist  "
			+ "WHERE sci_pledgor_dtl.cms_pledgor_dtl_id  = cms_checklist.cms_pledgor_dtl_id "
			+ "AND	 cms_checklist.category  = 'CC' " + "AND	 cms_checklist.sub_category  = 'PLEDGOR' "
			+ "AND	 cms_checklist.status  <> 'OBSOLETE' " + "AND	 cms_checklist.cms_lsp_lmt_profile_id  = ? "
			+ "UNION " + "SELECT " + "cms_checklist.checklist_id, " + "cms_checklist.category, "
			+ "cms_checklist.sub_category, " + "cms_checklist.doc_orig_country, "
			+ "CAST(NULL AS DECIMAL) borrower_id, " + "CAST(NULL AS VARCHAR(100)) borrower_name, "
			+ "CAST(NULL AS VARCHAR(20)) borrower_le_id, " + "CAST(NULL AS NUMBER) pledgor_id, "
			+ "CAST(NULL AS VARCHAR(100)) pledgor_name, " + "CAST(NULL AS VARCHAR(20)) pledgor_le_id, "
			+ "CAST(NULL AS NUMBER) cms_pledgor_id, " + "cms_checklist.cms_collateral_id collateral_id, "
			+ "cms_security.sci_security_dtl_id sci_security_id, " + "cms_security.type_name security_type_name, "
			+ "cms_security.subtype_name security_sub_type_name " + "FROM  cms_security, " + "	 cms_checklist  "
			+ "WHERE cms_checklist.cms_collateral_id  = cms_security.cms_collateral_id "
			+ "AND	 cms_checklist.category  = 'S' " + "AND	 cms_checklist.status  <> 'OBSOLETE' "
			+ "AND	 cms_checklist.cms_lsp_lmt_profile_id  = ?	" 
			+"UNION "
			+"SELECT "
			+"cms_checklist.checklist_id,"
			+"cms_checklist.category,"
			+"cms_checklist.sub_category,"
			+"cms_checklist.doc_orig_country,"
			+"CAST(NULL AS DECIMAL) borrower_id,"
			+"CAST(NULL AS VARCHAR(100)) borrower_name,"
			+"CAST(NULL AS VARCHAR(20)) borrower_le_id,"
			+"CAST(NULL AS NUMBER) pledgor_id,"
			+"CAST(NULL AS VARCHAR(100)) pledgor_name,"
			+"CAST(NULL AS VARCHAR(20)) pledgor_le_id,"
			+"CAST(NULL AS NUMBER) cms_pledgor_id,"
			+"cms_checklist.cms_collateral_id collateral_id,"
			+"CAST(NULL AS VARCHAR(50)) sci_security_id,"
			+"CAST(NULL AS VARCHAR(40)) security_type_name,"
			+"CAST(NULL AS VARCHAR(60)) security_sub_type_name "
			+" FROM  cms_checklist "
			+"WHERE  cms_checklist.category  = 'F' "
			+"AND	 cms_checklist.status  <> 'OBSOLETE' "
			+"AND	 cms_checklist.cms_lsp_lmt_profile_id  = ?"
			+"UNION "
			+"SELECT "
			+"cms_checklist.checklist_id,"
			+"cms_checklist.category,"
			+"cms_checklist.sub_category,"
			+"cms_checklist.doc_orig_country,"
			+"CAST(NULL AS DECIMAL) borrower_id,"
			+"CAST(NULL AS VARCHAR(100)) borrower_name,"
			+"CAST(NULL AS VARCHAR(20)) borrower_le_id,"
			+"CAST(NULL AS NUMBER) pledgor_id,"
			+"CAST(NULL AS VARCHAR(100)) pledgor_name,"
			+"CAST(NULL AS VARCHAR(20)) pledgor_le_id,"
			+"CAST(NULL AS NUMBER) cms_pledgor_id,"
			+"cms_checklist.cms_collateral_id collateral_id,"
			+"CAST(NULL AS VARCHAR(50)) sci_security_id,"
			+"CAST(NULL AS VARCHAR(40)) security_type_name,"
			+"CAST(NULL AS VARCHAR(60)) security_sub_type_name "
			+" FROM  cms_checklist "
			+"WHERE  cms_checklist.category  = 'CAM' "
			+"AND	 cms_checklist.status  <> 'OBSOLETE' "
			+"AND	 cms_checklist.cms_lsp_lmt_profile_id  = ? " + "ORDER BY category, " + "	    sub_category, "
			+ "	    borrower_name, " + "	    pledgor_name, " + "	    security_type_name, "
			+ "	    security_sub_type_name, " + "	    sci_security_id, " + "	    checklist_id  ";
	
    /*
	private static final String SELECT_DOC_CAT_NON_BORROWER = "SELECT " + "cms_checklist.checklist_id, "
			+ "'CC' category, " + "'NON_BORROWER' sub_category, " + "CAST(NULL AS VARCHAR(20)) doc_orig_country, "
			+ "CAST(NULL AS DECIMAL) borrower_id, " + "sci_le_main_profile.lmp_long_name borrower_name, "
			+ "CAST(NULL AS BIGINT) borrower_le_id, " + "CAST(NULL AS BIGINT) pledgor_id, "
			+ "CAST(NULL AS VARCHAR(100)) pledgor_name, " + "CAST(NULL AS BIGINT) pledgor_le_id, "
			+ "CAST(NULL AS DECIMAL) cms_pledgor_id, " + "CAST(NULL AS DECIMAL) collateral_id, "
			+ "CAST(NULL AS BIGINT) sci_security_id, " + "CAST(NULL AS VARCHAR(40)) security_type_name, "
			+ "CAST(NULL AS VARCHAR(60)) security_sub_type_name " + "FROM  sci_le_main_profile, "
			+ "	 sci_le_sub_profile, " + "	 cms_checklist  "
			+ "WHERE sci_le_main_profile.cms_le_main_profile_id  = sci_le_sub_profile.cms_le_main_profile_id "
			+ "AND	 sci_le_sub_profile.cms_le_sub_profile_id  = cms_checklist.cms_lmp_sub_profile_id "
			+ "AND	 cms_checklist.CATEGORY  = 'CC' " + "AND	 cms_checklist.sub_category  = 'NON_BORROWER' "
			+ "AND	 cms_checklist.status  <> 'OBSOLETE' " + "AND	 cms_checklist.cms_lmp_sub_profile_id  = ? "
			+ "AND	 cms_checklist.cms_lsp_lmt_profile_id  = ? " + "UNION " + "SELECT " + "cms_checklist.checklist_id, "
			+ "cms_checklist.category, " + "cms_checklist.sub_category, " + "cms_checklist.doc_orig_country, "
			+ "cms_checklist.cms_lmp_sub_profile_id borrower_id, "
			+ "sci_le_main_profile.lmp_long_name borrower_name, " + "sci_le_main_profile.lmp_le_id borrower_le_id, "
			+ "CAST(NULL AS BIGINT) pledgor_id, " + "CAST(NULL AS VARCHAR(100)) pledgor_name, "
			+ "CAST(NULL AS BIGINT) pledgor_le_id, " + "CAST(NULL AS DECIMAL) cms_pledgor_id, "
			+ "CAST(NULL AS DECIMAL) collateral_id, " + "CAST(NULL AS BIGINT) sci_security_id, "
			+ "CAST(NULL AS VARCHAR(40)) security_type_name, " + "CAST(NULL AS VARCHAR(60)) security_sub_type_name "
			+ "FROM  sci_le_main_profile, " + "	 sci_le_sub_profile, " + "	 cms_checklist  "
			+ "WHERE sci_le_main_profile.cms_le_main_profile_id  = sci_le_sub_profile.cms_le_main_profile_id "
			+ "AND	 sci_le_sub_profile.cms_le_sub_profile_id  = cms_checklist.cms_lmp_sub_profile_id "
			+ "AND	 cms_checklist.category  = 'CC' "
			+ "AND	 cms_checklist.sub_category  IN ( 'MAIN_BORROWER'  , 'CO_BORROWER'  ) "
			+ "AND	 cms_checklist.status  = 'DELETED' " + "AND	 cms_checklist.cms_lsp_lmt_profile_id  = ? " + "UNION "
			+ "SELECT " + "cms_checklist.checklist_id, " + "cms_checklist.category, " + "cms_checklist.sub_category, "
			+ "cms_checklist.doc_orig_country, " + "CAST(NULL AS DECIMAL) borrower_id, "
			+ "CAST(NULL AS VARCHAR(100)) borrower_name, " + "CAST(NULL AS BIGINT) borrower_le_id, "
			+ "sci_pledgor_dtl.plg_pledgor_id pledgor_id, " + "sci_pledgor_dtl.plg_legal_name pledgor_name, "
			+ "sci_pledgor_dtl.plg_le_id pledgor_le_id, " + "cms_checklist.cms_pledgor_dtl_id cms_pledgor_id, "
			+ "CAST(NULL AS DECIMAL) collateral_id, " + "CAST(NULL AS BIGINT) sci_security_id, "
			+ "CAST(NULL AS VARCHAR(40)) security_type_name, " + "CAST(NULL AS VARCHAR(60)) security_sub_type_name "
			+ "FROM  sci_pledgor_dtl, " + "	 cms_checklist  "
			+ "WHERE sci_pledgor_dtl.cms_pledgor_dtl_id  = cms_checklist.cms_pledgor_dtl_id "
			+ "AND	 cms_checklist.category  = 'CC' " + "AND	 cms_checklist.sub_category  = 'PLEDGOR' "
			+ "AND	 cms_checklist.status  = 'DELETED' " + "AND	 cms_checklist.cms_lsp_lmt_profile_id  = ? " + "UNION "
			+ "SELECT " + "cms_checklist.checklist_id, " + "cms_checklist.category, " + "cms_checklist.sub_category, "
			+ "cms_checklist.doc_orig_country, " + "CAST(NULL AS DECIMAL) borrower_id, "
			+ "CAST(NULL AS VARCHAR(100)) borrower_name, " + "CAST(NULL AS BIGINT) borrower_le_id, "
			+ "CAST(NULL AS BIGINT) pledgor_id, " + "CAST(NULL AS VARCHAR(100)) pledgor_name, "
			+ "CAST(NULL AS BIGINT) pledgor_le_id, " + "CAST(NULL AS DECIMAL) cms_pledgor_id, "
			+ "cms_checklist.cms_collateral_id collateral_id, " + "cms_security.sci_security_dtl_id sci_security_id, "
			+ "cms_security.type_name security_type_name, " + "cms_security.subtype_name security_sub_type_name "
			+ "FROM  cms_security, " + "	 cms_checklist  "
			+ "WHERE cms_checklist.cms_collateral_id  = cms_security.cms_collateral_id "
			+ "AND	 cms_checklist.category  = 'S' " + "AND	 cms_checklist.status  <> 'OBSOLETE' "
			+ "AND	 cms_checklist.cms_lsp_lmt_profile_id  = ?	  " + "ORDER BY category, " + "	    sub_category, "
			+ "	    borrower_name, " + "	    pledgor_name, " + "	    security_type_name, "
			+ "	    security_sub_type_name, " + "	    sci_security_id, " + "	    checklist_id  ";
    */
    private static final String SELECT_DOC_CAT_NON_BORROWER = "SELECT cms_checklist.checklist_id, 'CC' category, 'NON_BORROWER' sub_category, CAST(NULL AS VARCHAR(20)) doc_orig_country, " +
            "CAST(NULL AS NUMBER) borrower_id, sci_le_main_profile.lmp_long_name borrower_name, CAST(NULL AS VARCHAR(20)) borrower_le_id, " +
            "CAST(NULL AS NUMBER) pledgor_id, CAST(NULL AS VARCHAR(100)) pledgor_name, CAST(NULL AS VARCHAR(20)) pledgor_le_id, CAST(NULL AS NUMBER) cms_pledgor_id, " +
            "CAST(NULL AS NUMBER) collateral_id, CAST(NULL AS VARCHAR(50)) sci_security_id, CAST(NULL AS VARCHAR(40)) security_type_name, " +
            "CAST(NULL AS VARCHAR(100)) security_sub_type_name " +
            "FROM  sci_le_main_profile,  sci_le_sub_profile,  cms_checklist  " +
            "WHERE sci_le_main_profile.cms_le_main_profile_id  = sci_le_sub_profile.cms_le_main_profile_id " +
            "AND sci_le_sub_profile.cms_le_sub_profile_id  = cms_checklist.cms_lmp_sub_profile_id " +
            "AND cms_checklist.CATEGORY  = 'CC' AND cms_checklist.sub_category  = 'NON_BORROWER' " +
            "AND cms_checklist.status  <> 'OBSOLETE' AND cms_checklist.cms_lmp_sub_profile_id  = ? AND cms_checklist.cms_lsp_lmt_profile_id  = ? " +
            "UNION " +
            "SELECT cms_checklist.checklist_id, cms_checklist.category, cms_checklist.sub_category, cms_checklist.doc_orig_country, " +
            "cms_checklist.cms_lmp_sub_profile_id borrower_id, sci_le_main_profile.lmp_long_name borrower_name, sci_le_main_profile.lmp_le_id borrower_le_id, " +
            "CAST(NULL AS NUMBER) pledgor_id, CAST(NULL AS VARCHAR(100)) pledgor_name, CAST(NULL AS VARCHAR(20)) pledgor_le_id, CAST(NULL AS NUMBER) cms_pledgor_id, " +
            "CAST(NULL AS NUMBER) collateral_id, CAST(NULL AS VARCHAR(50)) sci_security_id, CAST(NULL AS VARCHAR(40)) security_type_name, " +
            "CAST(NULL AS VARCHAR(100)) security_sub_type_name " +
            "FROM  sci_le_main_profile,  sci_le_sub_profile,  cms_checklist  " +
            "WHERE sci_le_main_profile.cms_le_main_profile_id  = sci_le_sub_profile.cms_le_main_profile_id " +
            "AND sci_le_sub_profile.cms_le_sub_profile_id  = cms_checklist.cms_lmp_sub_profile_id " +
            "AND cms_checklist.category  = 'CC' AND cms_checklist.sub_category  IN ( 'MAIN_BORROWER'  , 'CO_BORROWER'  ) " +
            "AND cms_checklist.status  = 'DELETED' AND cms_checklist.cms_lsp_lmt_profile_id  = ? " +
            "UNION " +
            "SELECT cms_checklist.checklist_id, cms_checklist.category, cms_checklist.sub_category, cms_checklist.doc_orig_country, " +
            "CAST(NULL AS NUMBER) borrower_id, CAST(NULL AS VARCHAR(150)) borrower_name, CAST(NULL AS VARCHAR(20)) borrower_le_id, " +
            "sci_pledgor_dtl.plg_pledgor_id pledgor_id, sci_pledgor_dtl.plg_legal_name pledgor_name, sci_pledgor_dtl.plg_le_id pledgor_le_id, cms_checklist.cms_pledgor_dtl_id cms_pledgor_id, " +
            "CAST(NULL AS NUMBER) collateral_id, CAST(NULL AS VARCHAR(50)) sci_security_id, CAST(NULL AS VARCHAR(40)) security_type_name, " +
            "CAST(NULL AS VARCHAR(100)) security_sub_type_name " +
            "FROM  sci_pledgor_dtl,  cms_checklist " +
            " WHERE sci_pledgor_dtl.cms_pledgor_dtl_id  = cms_checklist.cms_pledgor_dtl_id " +
            "AND cms_checklist.category  = 'CC' AND cms_checklist.sub_category  = 'PLEDGOR' AND cms_checklist.status  = 'DELETED' AND cms_checklist.cms_lsp_lmt_profile_id  = ? " +
            "UNION " +
            "SELECT cms_checklist.checklist_id, cms_checklist.category, cms_checklist.sub_category, cms_checklist.doc_orig_country, " +
            "CAST(NULL AS NUMBER) borrower_id, CAST(NULL AS VARCHAR(150)) borrower_name, CAST(NULL AS VARCHAR(20)) borrower_le_id, " +
            "CAST(NULL AS NUMBER) pledgor_id, CAST(NULL AS VARCHAR(100)) pledgor_name, CAST(NULL AS VARCHAR(20)) pledgor_le_id, CAST(NULL AS NUMBER) cms_pledgor_id, " +
            "cms_checklist.cms_collateral_id collateral_id, cms_security.sci_security_dtl_id sci_security_id, cms_security.type_name security_type_name, " +
            "cms_security.subtype_name security_sub_type_name " +
            "FROM  cms_security,  cms_checklist  " +
            "WHERE cms_checklist.cms_collateral_id  = cms_security.cms_collateral_id " +
            "AND cms_checklist.category  = 'S' AND cms_checklist.status  <> 'OBSOLETE' " +
            "AND cms_checklist.cms_lsp_lmt_profile_id  = ?  " +
            "ORDER BY category,     sub_category,     borrower_name,     pledgor_name,     security_type_name,     security_sub_type_name,     sci_security_id,     checklist_id";

	private static final String SELECT_CC_DOC_HELD = "SELECT "
			+ "cms_checklist.checklist_id, "
            + "cms_checklist.status, "
			+ "cms_checklist.category, "
			+ "cms_checklist.sub_category, "
			+ "cms_checklist.doc_orig_country, "
			+ "cms_checklist.doc_orig_organisation, "
			+ "cms_checklist.cms_lmp_sub_profile_id borrower_id, "
			+ "sci_le_main_profile.lmp_long_name borrower_name, "
			+ "sci_le_main_profile.lmp_le_id borrower_le_id, "
			+ "CAST(NULL AS NUMBER) pledgor_id, "
			+ "CAST(NULL AS VARCHAR(100)) pledgor_name, "
			+ "CAST(NULL AS VARCHAR(20)) pledgor_le_id, "
			+ "CAST(NULL AS NUMBER) cms_pledgor_id, "
			+ "CAST(NULL AS NUMBER) collateral_id, "
			+ "CAST(NULL AS VARCHAR(50)) sci_security_id, "
			+ "CAST(NULL AS VARCHAR(40)) security_type_name, "
			+ "CAST(NULL AS VARCHAR(60)) security_sub_type_name, "
			+ "cms_checklist_item.doc_item_ref, "
			+ "cms_checklist_item.document_code, "
			+ "cms_checklist_item.doc_description, "
			+ "cms_checklist_item.remarks, "
			+ "cms_checklist_item.in_vault, "
			+ "cms_checklist_item.in_ext_custody, "
			+ "cms_checklist_item.status docstatus, "
			+ "cms_checklist_item.cpc_cust_status cpc_custstatus,			  "
			// + "CASE "
			// + " WHEN cms_cust_doc_item.STATUS = 'RECEIVED' THEN NULL "
			// + " WHEN cms_cust_doc_item.STATUS = 'DELETED' THEN NULL "
			// + " ELSE cms_cust_doc_item.STATUS "
			// + "END CUSTSTATUS, "
			+ "cms_checklist_item.doc_date, "
			+ "cms_checklist_item.expiry_date, "
			+ "cms_checklist_item.status item_status, "
			+ "cms_checklist_item.parent_checklist_item_ref parent_item_ref_id, "
			+ "parent_item.document_code parent_item_doc_code, "
			+ "CMS_CHECKLIST_ITEM.SHARE_STATUS, "
			+ "'share_details' share_details "
			// + "GETSHAREITEMINFOALL(CMS_CHECKLIST_ITEM.DOC_ITEM_ID,
			// CMS_CHECKLIST.CHECKLIST_ID, CMS_CHECKLIST.CATEGORY) share_details
			// "
			+ "FROM  cms_checklist_item  LEFT OUTER JOIN  cms_cust_doc_item  ON  cms_checklist_item.doc_item_ref  = cms_cust_doc_item.checklist_item_ref_id    LEFT OUTER JOIN  cms_checklist_item parent_item  ON  cms_checklist_item.parent_checklist_item_ref  = parent_item.doc_item_ref  , "
			+ "	 sci_le_main_profile, " + "	 sci_le_sub_profile, "
			+ "	 cms_checklist  "
			+ "WHERE sci_le_main_profile.cms_le_main_profile_id  = sci_le_sub_profile.cms_le_main_profile_id "
			+ "AND	 sci_le_sub_profile.cms_le_sub_profile_id  = cms_checklist.cms_lmp_sub_profile_id "
			+ "AND	 cms_checklist.category  = 'CC' "
			+ "AND	 cms_checklist.sub_category IN ('MAIN_BORROWER', 'CO_BORROWER', 'JOINT_BORROWER', 'NON_BORROWER') "
			// + "AND cms_checklist.status <> 'OBSOLETE' "
			//+ "AND	 cms_checklist.status  = 'COMPLETED' "
			+ "AND	 cms_checklist.checklist_id  = cms_checklist_item.checklist_id "
			+ "AND	 cms_checklist_item.is_deleted  = 'N' ";

	private static final String SELECT_CC_DOC_HELD_SHARE_FOR_MB_CB_NB = "SELECT DISTINCT "
			+ "cms_checklist_item_share.CHECKLIST_ID CHECKLIST_ID, "
			+ "cms_checklist.CATEGORY, "
			+ "cms_checklist.SUB_CATEGORY, "
			+ "cms_checklist.DOC_ORIG_COUNTRY, "
			+ "cms_checklist.DOC_ORIG_ORGANISATION, "
			+ "cms_checklist.CMS_LMP_SUB_PROFILE_ID BORROWER_ID, "
			+ "sci_le_main_profile.LMP_LONG_NAME BORROWER_NAME, "
			+ "sci_le_main_profile.LMP_LE_ID BORROWER_LE_ID, "
			+ "CAST(NULL AS NUMBER) PLEDGOR_ID, "
			+ "CAST(NULL AS VARCHAR(100)) PLEDGOR_NAME, "
			+ "CAST(NULL AS VARCHAR(20)) PLEDGOR_LE_ID, "
			+ "CAST(NULL AS NUMBER) CMS_PLEDGOR_ID, "
			+ "CAST(NULL AS NUMBER) COLLATERAL_ID, "
			+ "CAST(NULL AS VARCHAR(50)) SCI_SECURITY_ID, "
			+ "CAST(NULL AS VARCHAR(40)) SECURITY_TYPE_NAME, "
			+ "CAST(NULL AS VARCHAR(60)) SECURITY_SUB_TYPE_NAME, "
			+ "cms_checklist_item.DOC_ITEM_REF, "
			+ "cms_checklist_item.DOCUMENT_CODE, "
			+ "cms_checklist_item.DOC_DESCRIPTION, "
			+ "cms_checklist_item.REMARKS, "
			+ "cms_checklist_item.IN_VAULT, "
			+ "cms_checklist_item.IN_EXT_CUSTODY, "
			+ "cms_checklist_item.STATUS DOCSTATUS, "
			+ "cms_checklist_item.CPC_CUST_STATUS CPC_CUSTSTATUS,			  "
			// + "CASE "
			// + " WHEN cms_cust_doc_item.STATUS = 'RECEIVED' THEN NULL "
			// + " WHEN cms_cust_doc_item.STATUS = 'DELETED' THEN NULL "
			// + " ELSE cms_cust_doc_item.STATUS "
			// + "END CUSTSTATUS, "
			+ "cms_checklist_item.DOC_DATE, "
			+ "cms_checklist_item.EXPIRY_DATE, "
			+ "cms_checklist_item.STATUS item_status, "
			+ "cms_checklist_item.PARENT_CHECKLIST_ITEM_REF PARENT_ITEM_REF_ID, "
			+ "parent_item.DOCUMENT_CODE PARENT_ITEM_DOC_CODE, "
			+ "CMS_CHECKLIST_ITEM.SHARE_STATUS, "
			// + "GETSHAREITEMINFOALL(CMS_CHECKLIST_ITEM.DOC_ITEM_ID,
			// CMS_CHECKLIST_ITEM_SHARE.CHECKLIST_ID, CMS_CHECKLIST.CATEGORY)
			// share_details "
			+ "'share_details' share_details "
			+ "FROM  cms_checklist_item  "
			// + "LEFT OUTER JOIN cms_cust_doc_item "
			// + "ON cms_checklist_item.DOC_ITEM_REF =
			// cms_cust_doc_item.CHECKLIST_ITEM_REF_ID "
			+ "LEFT OUTER JOIN  cms_checklist_item parent_item  "
			+ "ON  cms_checklist_item.PARENT_CHECKLIST_ITEM_REF  = parent_item.DOC_ITEM_REF  , "
			+ "	 cms_checklist, "
			+ "	 sci_le_main_profile, "
			+ "	 sci_le_sub_profile, "
			+ "	 cms_checklist_item_share  "
			+ "WHERE sci_le_main_profile.CMS_LE_MAIN_PROFILE_ID  = sci_le_sub_profile.CMS_LE_MAIN_PROFILE_ID "
			+ "AND	 sci_le_sub_profile.CMS_LE_SUB_PROFILE_ID  = cms_checklist.CMS_LMP_SUB_PROFILE_ID "
			+ "AND	 cms_checklist.CATEGORY  = 'CC' "
			+ "AND	 cms_checklist.SUB_CATEGORY IN ('MAIN_BORROWER', 'CO_BORROWER', 'JOINT_BORROWER', 'NON_BORROWER') "
			// + "AND cms_checklist.STATUS <> 'OBSOLETE' "
			+ "AND	 cms_checklist.STATUS  = 'COMPLETED' "
			+ "AND	 cms_checklist.CHECKLIST_ID  = cms_checklist_item_share.CHECKLIST_ID "
			// + "AND (cms_cust_doc_item.STATUS IS NULL " + " OR
			// cms_cust_doc_item.STATUS != 'PERM_UPLIFTED') "
			+ "AND	 cms_checklist_item_share.DOC_ITEM_ID  = cms_checklist_item.DOC_ITEM_ID "
			+ "AND	 cms_checklist_item.IS_DELETED  = 'N' "
			+ "AND	 (cms_checklist_item_share.status  is null OR cms_checklist_item_share.status  <> 'DELETED') ";

	private static final String SELECT_CC_DOC_HELD_SHARE_FOR_MB = "SELECT "
			+ "cms_checklist.CHECKLIST_ID CHECKLIST_ID, "
			+ "cms_checklist.CATEGORY, "
			+ "cms_checklist.SUB_CATEGORY, "
			+ "cms_checklist.DOC_ORIG_COUNTRY, "
			+ "cms_checklist.DOC_ORIG_ORGANISATION, "
			+ "cms_checklist.CMS_LMP_SUB_PROFILE_ID BORROWER_ID, "
			+ "sci_le_main_profile.LMP_LONG_NAME BORROWER_NAME, "
			+ "sci_le_main_profile.LMP_LE_ID BORROWER_LE_ID, "
			+ "CAST(NULL AS NUMBER) PLEDGOR_ID, "
			+ "CAST(NULL AS VARCHAR(100)) PLEDGOR_NAME, "
			+ "CAST(NULL AS NUMBER) PLEDGOR_LE_ID, "
			+ "CAST(NULL AS DECIMAL) CMS_PLEDGOR_ID, "
			+ "CAST(NULL AS DECIMAL) COLLATERAL_ID, "
			+ "CAST(NULL AS NUMBER) SCI_SECURITY_ID, "
			+ "CAST(NULL AS VARCHAR(40)) SECURITY_TYPE_NAME, "
			+ "CAST(NULL AS VARCHAR(60)) SECURITY_SUB_TYPE_NAME, "
			+ "cms_checklist_item.DOC_ITEM_REF, "
			+ "cms_checklist_item.DOCUMENT_CODE, "
			+ "cms_checklist_item.DOC_DESCRIPTION, "
			+ "cms_checklist_item.REMARKS, "
			+ "cms_checklist_item.IN_VAULT, "
			+ "cms_checklist_item.IN_EXT_CUSTODY, "
			+ "cms_checklist_item.STATUS DOCSTATUS, "
			+ "cms_checklist_item.CPC_CUST_STATUS CPC_CUSTSTATUS,			  "
			+ "CASE  "
			+ "	WHEN cms_cust_doc_item.STATUS  = 'RECEIVED' THEN NULL  "
			+ "	ELSE cms_cust_doc_item.STATUS  "
			+ "END CUSTSTATUS, "
			+ "cms_checklist_item.DOC_DATE, "
			+ "cms_checklist_item.EXPIRY_DATE, "
			+ "cms_checklist_item.STATUS item_status, "
			+ "cms_checklist_item.PARENT_CHECKLIST_ITEM_REF PARENT_ITEM_REF_ID, "
			+ "parent_item.DOCUMENT_CODE PARENT_ITEM_DOC_CODE "
			+ "FROM  cms_checklist_item  LEFT OUTER JOIN  cms_cust_doc_item  ON  cms_checklist_item.DOC_ITEM_REF  = cms_cust_doc_item.CHECKLIST_ITEM_REF_ID    LEFT OUTER JOIN  cms_checklist_item parent_item  ON  cms_checklist_item.PARENT_CHECKLIST_ITEM_REF  = parent_item.DOC_ITEM_REF  , "
			+ "	 cms_checklist, " + "	 sci_le_main_profile, " + "	 sci_le_sub_profile, "
			+ "	 cms_checklist_item_share  " + "WHERE 0  = 0 "
			+ "AND	 sci_le_main_profile.CMS_LE_MAIN_PROFILE_ID  = sci_le_sub_profile.CMS_LE_MAIN_PROFILE_ID "
			+ "AND	 sci_le_sub_profile.CMS_LE_SUB_PROFILE_ID  = cms_checklist.CMS_LMP_SUB_PROFILE_ID "
			+ "AND	 cms_checklist.CATEGORY  = 'CC' "
			+ "AND	 cms_checklist.SUB_CATEGORY  IN ( 'MAIN_BORROWER'  , 'CO_BORROWER'  , 'NON_BORROWER'  ) "
			+ "AND	 cms_checklist.STATUS  <> 'OBSOLETE' "
			+ "AND	 cms_checklist.CHECKLIST_ID  = cms_checklist_item.CHECKLIST_ID "
			+ "AND	 cms_checklist_item.STATUS  IN ( 'COMPLETED'  , 'EXPIRED'  ) "
			+ "AND	 (cms_cust_doc_item.STATUS  IS NULL " + "	  OR	cms_cust_doc_item.STATUS  != 'PERM_UPLIFTED') "
			+ "AND	 sci_le_main_profile.CMS_LE_MAIN_PROFILE_ID  = sci_le_sub_profile.CMS_LE_MAIN_PROFILE_ID "
			+ "AND	 sci_le_sub_profile.CMS_LE_SUB_PROFILE_ID  = cms_checklist.CMS_LMP_SUB_PROFILE_ID "
			+ "AND	 cms_checklist_item_share.DOC_ITEM_ID  = cms_checklist_item.DOC_ITEM_ID "
			+ "AND	 cms_checklist_item.IS_DELETED  = 'N' ";

	private static final String SELECT_CC_DOC_HELD_SHARE_FOR_CB = "SELECT "
			+ "cms_checklist.CHECKLIST_ID CHECKLIST_ID, "
			+ "cms_checklist.CATEGORY, "
			+ "cms_checklist.SUB_CATEGORY, "
			+ "cms_checklist.DOC_ORIG_COUNTRY, "
			+ "cms_checklist.DOC_ORIG_ORGANISATION, "
			+ "cms_checklist.CMS_LMP_SUB_PROFILE_ID BORROWER_ID, "
			+ "sci_le_main_profile.LMP_LONG_NAME BORROWER_NAME, "
			+ "sci_le_main_profile.LMP_LE_ID BORROWER_LE_ID, "
			+ "CAST(NULL AS NUMBER) PLEDGOR_ID, "
			+ "CAST(NULL AS VARCHAR(100)) PLEDGOR_NAME, "
			+ "CAST(NULL AS NUMBER) PLEDGOR_LE_ID, "
			+ "CAST(NULL AS DECIMAL) CMS_PLEDGOR_ID, "
			+ "CAST(NULL AS DECIMAL) COLLATERAL_ID, "
			+ "CAST(NULL AS NUMBER) SCI_SECURITY_ID, "
			+ "CAST(NULL AS VARCHAR(40)) SECURITY_TYPE_NAME, "
			+ "CAST(NULL AS VARCHAR(60)) SECURITY_SUB_TYPE_NAME, "
			+ "cms_checklist_item.DOC_ITEM_REF, "
			+ "cms_checklist_item.DOCUMENT_CODE, "
			+ "cms_checklist_item.DOC_DESCRIPTION, "
			+ "cms_checklist_item.REMARKS, "
			+ "cms_checklist_item.IN_VAULT, "
			+ "cms_checklist_item.IN_EXT_CUSTODY, "
			+ "cms_checklist_item.STATUS DOCSTATUS, "
			+ "cms_checklist_item.CPC_CUST_STATUS CPC_CUSTSTATUS,			  "
			+ "CASE  "
			+ "	WHEN cms_cust_doc_item.STATUS  = 'RECEIVED' THEN NULL  "
			+ "	ELSE cms_cust_doc_item.STATUS  "
			+ "END CUSTSTATUS, "
			+ "cms_checklist_item.DOC_DATE, "
			+ "cms_checklist_item.EXPIRY_DATE, "
			+ "cms_checklist_item.STATUS item_status, "
			+ "cms_checklist_item.PARENT_CHECKLIST_ITEM_REF PARENT_ITEM_REF_ID, "
			+ "parent_item.DOCUMENT_CODE PARENT_ITEM_DOC_CODE "
			+ "FROM  cms_checklist_item  LEFT OUTER JOIN  cms_cust_doc_item  ON  cms_checklist_item.DOC_ITEM_REF  = cms_cust_doc_item.CHECKLIST_ITEM_REF_ID    LEFT OUTER JOIN  cms_checklist_item parent_item  ON  cms_checklist_item.PARENT_CHECKLIST_ITEM_REF  = parent_item.DOC_ITEM_REF  , "
			+ "	 cms_checklist, " + "	 sci_le_main_profile, " + "	 sci_le_sub_profile, "
			+ "	 cms_checklist_item_share  " + "WHERE 0  = 0 "
			+ "AND	 sci_le_main_profile.CMS_LE_MAIN_PROFILE_ID  = sci_le_sub_profile.CMS_LE_MAIN_PROFILE_ID "
			+ "AND	 sci_le_sub_profile.CMS_LE_SUB_PROFILE_ID  = cms_checklist.CMS_LMP_SUB_PROFILE_ID "
			+ "AND	 cms_checklist.CATEGORY  = 'CC' " + "AND	 cms_checklist.STATUS  <> 'OBSOLETE' "
			+ "AND	 cms_checklist.CHECKLIST_ID  = cms_checklist_item.CHECKLIST_ID "
			+ "AND	 cms_checklist_item.STATUS  IN ( 'COMPLETED'  , 'EXPIRED'  ) "
			+ "AND	 (cms_cust_doc_item.STATUS  IS NULL " + "	  OR	cms_cust_doc_item.STATUS  != 'PERM_UPLIFTED') "
			+ "AND	 sci_le_main_profile.CMS_LE_MAIN_PROFILE_ID  = sci_le_sub_profile.CMS_LE_MAIN_PROFILE_ID "
			+ "AND	 sci_le_sub_profile.CMS_LE_SUB_PROFILE_ID  = cms_checklist.CMS_LMP_SUB_PROFILE_ID "
			+ "AND	 cms_checklist_item_share.DOC_ITEM_ID  = cms_checklist_item.DOC_ITEM_ID "
			+ "AND	 cms_checklist_item.IS_DELETED  = 'N' ";

	private static final String SELECT_CC_DOC_HELD_SHARE_FOR_PLEDGOR = "SELECT "
			+ "cms_checklist_item_share.CHECKLIST_ID, "
			+ "cms_checklist.CATEGORY, "
			+ "cms_checklist.SUB_CATEGORY, "
			+ "cms_checklist.DOC_ORIG_COUNTRY, "
			+ "cms_checklist.DOC_ORIG_ORGANISATION, "
			+ "CAST(NULL AS DECIMAL) BORROWER_ID, "
			+ "CAST(NULL AS VARCHAR(100)) BORROWER_NAME, "
			+ "CAST(NULL AS VARCHAR(20)) BORROWER_LE_ID, "
			+ "sci_pledgor_dtl.PLG_PLEDGOR_ID pledgor_id, "
			+ "sci_pledgor_dtl.PLG_LEGAL_NAME pledgor_name, "
			+ "sci_pledgor_dtl.PLG_LE_ID pledgor_le_id, "
			+ "cms_checklist.CMS_PLEDGOR_DTL_ID cms_pledgor_id, "
			+ "CAST(NULL AS NUMBER) COLLATERAL_ID, "
			+ "CAST(NULL AS VARCHAR(50)) SCI_SECURITY_ID, "
			+ "CAST(NULL AS VARCHAR(40)) SECURITY_TYPE_NAME, "
			+ "CAST(NULL AS VARCHAR(60)) SECURITY_SUB_TYPE_NAME, "
			+ "cms_checklist_item.DOC_ITEM_REF, "
			+ "cms_checklist_item.DOCUMENT_CODE, "
			+ "cms_checklist_item.DOC_DESCRIPTION, "
			+ "cms_checklist_item.REMARKS, "
			+ "cms_checklist_item.in_vault, "
			+ "cms_checklist_item.IN_EXT_CUSTODY, "
			+ "cms_checklist_item.STATUS docstatus, "
			+ "cms_checklist_item.CPC_CUST_STATUS cpc_custstatus,			  "
			// + "CASE "
			// + " WHEN cms_cust_doc_item.STATUS = 'RECEIVED' THEN NULL "
			// + " WHEN cms_cust_doc_item.STATUS = 'DELETED' THEN NULL "
			// + " ELSE cms_cust_doc_item.STATUS "
			// + "END CUSTSTATUS, "
			+ "cms_checklist_item.DOC_DATE, "
			+ "cms_checklist_item.EXPIRY_DATE, "
			+ "cms_checklist_item.STATUS item_status, "
			+ "cms_checklist_item.PARENT_CHECKLIST_ITEM_REF parent_item_ref_id, "
			+ "parent_item.DOCUMENT_CODE parent_item_doc_code, "
			+ "CMS_CHECKLIST_ITEM.SHARE_STATUS, "
			// + "GETSHAREITEMINFOALL(CMS_CHECKLIST_ITEM.DOC_ITEM_ID,
			// CMS_CHECKLIST_ITEM_SHARE.CHECKLIST_ID, CMS_CHECKLIST.CATEGORY)
			// share_details "
			+ "'share_details' share_details "
			+ "FROM  cms_checklist_item  "
			// + "LEFT OUTER JOIN cms_cust_doc_item "
			// + "ON cms_checklist_item.DOC_ITEM_REF =
			// cms_cust_doc_item.CHECKLIST_ITEM_REF_ID "
			+ "LEFT OUTER JOIN  cms_checklist_item parent_item  "
			+ "ON  cms_checklist_item.PARENT_CHECKLIST_ITEM_REF  = parent_item.DOC_ITEM_REF  , "
			+ "	 sci_pledgor_dtl, "
			+ "      cms_checklist, "
			+ "	 cms_checklist_item_share  "
			+ "WHERE sci_pledgor_dtl.CMS_PLEDGOR_DTL_ID  = cms_checklist.CMS_PLEDGOR_DTL_ID "
			+ "AND	 cms_checklist.CATEGORY  = 'CC' "
			+ "AND	 cms_checklist.SUB_CATEGORY  = 'PLEDGOR' "
			// + "AND cms_checklist.STATUS <> 'OBSOLETE' "
			+ "AND	 cms_checklist.STATUS  = 'COMPLETED' "
			+ "AND	 cms_checklist.CHECKLIST_ID  = cms_checklist_item_share.CHECKLIST_ID "
			+ "AND	 cms_checklist_item.IS_DELETED  = 'N' "
			+ "AND	 cms_checklist_item_share.DOC_ITEM_ID  = cms_checklist_item.DOC_ITEM_ID "
			+ "AND	 (cms_checklist_item_share.status  is null OR cms_checklist_item_share.status  <> 'DELETED') ";

	private static final String SELECT_CC_DOC_HELD_SHARE_FOR_NB = "SELECT "
			+ "cms_checklist.checklist_id, "
			+ "cms_checklist.category, "
			+ "cms_checklist.sub_category, "
			+ "cms_checklist.doc_orig_country, "
			+ "cms_checklist.doc_orig_organisation, "
			+ "cms_checklist.cms_lmp_sub_profile_id borrower_id, "
			+ "sci_le_main_profile.lmp_long_name borrower_name, "
			+ "sci_le_main_profile.lmp_le_id borrower_le_id, "
			+ "CAST(NULL AS NUMBER) pledgor_id, "
			+ "CAST(NULL AS VARCHAR(100)) pledgor_name, "
			+ "CAST(NULL AS NUMBER) pledgor_le_id, "
			+ "CAST(NULL AS DECIMAL) cms_pledgor_id, "
			+ "CAST(NULL AS DECIMAL) collateral_id, "
			+ "CAST(NULL AS NUMBER) sci_security_id, "
			+ "CAST(NULL AS VARCHAR(40)) security_type_name, "
			+ "CAST(NULL AS VARCHAR(60)) security_sub_type_name, "
			+ "cms_checklist_item.doc_item_ref, "
			+ "cms_checklist_item.document_code, "
			+ "cms_checklist_item.doc_description, "
			+ "cms_checklist_item.remarks, "
			+ "cms_checklist_item.in_vault, "
			+ "cms_checklist_item.in_ext_custody, "
			+ "cms_checklist_item.status docstatus, "
			+ "cms_checklist_item.cpc_cust_status cpc_custstatus,			  "
			+ "CASE  "
			+ "	WHEN cms_cust_doc_item.status  = 'RECEIVED' THEN NULL  "
			+ "	ELSE cms_cust_doc_item.status  "
			+ "END custstatus, "
			+ "cms_checklist_item.doc_date, "
			+ "cms_checklist_item.expiry_date, "
			+ "cms_checklist_item.status item_status, "
			+ "cms_checklist_item.parent_checklist_item_ref parent_item_ref_id, "
			+ "parent_item.document_code parent_item_doc_code "
			+ "FROM  cms_checklist_item  LEFT OUTER JOIN  cms_cust_doc_item  ON  cms_checklist_item.doc_item_ref  = cms_cust_doc_item.checklist_item_ref_id    LEFT OUTER JOIN  cms_checklist_item parent_item  ON  cms_checklist_item.parent_checklist_item_ref  = parent_item.doc_item_ref  , "
			+ "	 sci_le_main_profile, " + "	 sci_le_sub_profile, " + "	 cms_checklist, "
			+ "	 cms_checklist_item_share  "
			+ "WHERE sci_le_main_profile.cms_le_main_profile_id  = sci_le_sub_profile.cms_le_main_profile_id "
			+ "AND	 sci_le_sub_profile.cms_le_sub_profile_id  = cms_checklist.cms_lmp_sub_profile_id "
			+ "AND	 cms_checklist.category  = 'CC' "
			+ "AND	 cms_checklist.sub_category  IN ( 'MAIN_BORROWER'  , 'CO_BORROWER'  , 'NON_BORROWER'  ) "
			+ "AND	 cms_checklist.status  <> 'OBSOLETE' "
			+ "AND	 cms_checklist.checklist_id  = cms_checklist_item.checklist_id "
			+ "AND	 cms_checklist_item.IS_DELETED  = 'N' "
			+ "AND	 cms_checklist_item_share.DOC_ITEM_ID  = cms_checklist_item.DOC_ITEM_ID ";

	private static final String SELECT_CC_DOC_HELD_PLEDGOR = "SELECT "
			+ "cms_checklist.checklist_id, "
            + "cms_checklist.status, "
			+ "cms_checklist.category, "
			+ "cms_checklist.sub_category, "
			+ "cms_checklist.doc_orig_country, "
			+ "cms_checklist.doc_orig_organisation, "
			+ "CAST(NULL AS DECIMAL) borrower_id, "
			+ "CAST(NULL AS VARCHAR(100)) borrower_name, "
			+ "CAST(NULL AS VARCHAR(20)) borrower_le_id, "
			+ "sci_pledgor_dtl.plg_pledgor_id pledgor_id, "
			+ "sci_pledgor_dtl.plg_legal_name pledgor_name, "
			+ "sci_pledgor_dtl.plg_le_id pledgor_le_id, "
			+ "cms_checklist.cms_pledgor_dtl_id cms_pledgor_id, "
			+ "CAST(NULL AS NUMBER) collateral_id, "
			+ "CAST(NULL AS VARCHAR(50)) sci_security_id, "
			+ "CAST(NULL AS VARCHAR(40)) security_type_name, "
			+ "CAST(NULL AS VARCHAR(60)) security_sub_type_name, "
			+ "cms_checklist_item.doc_item_ref, "
			+ "cms_checklist_item.document_code, "
			+ "cms_checklist_item.doc_description, "
			+ "cms_checklist_item.remarks, "
			+ "cms_checklist_item.in_vault, "
			+ "cms_checklist_item.in_ext_custody, "
			+ "cms_checklist_item.status docstatus, "
			+ "cms_checklist_item.cpc_cust_status cpc_custstatus,			  "
			// + "CASE "
			// + " WHEN cms_cust_doc_item.STATUS = 'RECEIVED' THEN NULL "
			// + " WHEN cms_cust_doc_item.STATUS = 'DELETED' THEN NULL "
			// + " ELSE cms_cust_doc_item.STATUS "
			// + "END CUSTSTATUS, "
			+ "cms_checklist_item.doc_date, "
			+ "cms_checklist_item.expiry_date, "
			+ "cms_checklist_item.status item_status, "
			+ "cms_checklist_item.parent_checklist_item_ref parent_item_ref_id, "
			+ "parent_item.document_code parent_item_doc_code, "
			+ "CMS_CHECKLIST_ITEM.SHARE_STATUS, "
			+ "'share_details' share_details "
			// + "GETSHAREITEMINFOALL(CMS_CHECKLIST_ITEM.DOC_ITEM_ID,
			// CMS_CHECKLIST.CHECKLIST_ID, CMS_CHECKLIST.CATEGORY) share_details
			// "
			+ "FROM  cms_checklist_item  "
			// + "LEFT OUTER JOIN cms_cust_doc_item "
			// + "ON cms_checklist_item.doc_item_ref =
			// cms_cust_doc_item.checklist_item_ref_id "
			+ "LEFT OUTER JOIN  cms_checklist_item parent_item  "
			+ "ON  cms_checklist_item.parent_checklist_item_ref  = parent_item.doc_item_ref  , "
			+ "	 sci_pledgor_dtl, " + "	 cms_checklist  "
			+ "WHERE sci_pledgor_dtl.cms_pledgor_dtl_id  = cms_checklist.cms_pledgor_dtl_id "
			+ "AND	 cms_checklist.category  = 'CC' "
			+ "AND	 cms_checklist.sub_category  = 'PLEDGOR' "
			// + "AND cms_checklist.status <> 'OBSOLETE' "
			//+ "AND	 cms_checklist.status  = 'COMPLETED' "
			+ "AND	 cms_checklist.checklist_id  = cms_checklist_item.checklist_id "
			+ "AND	 cms_checklist_item.is_deleted  = 'N' ";

	/*
	 * private static final String SELECT_CC_DOC_HELD_NON_BORROWER = //
	 * non-borrower "SELECT cms_checklist.checklist_id," + "
	 * cms_checklist.category," + " cms_checklist.sub_category," + "
	 * cms_checklist.doc_orig_country," + " cms_checklist.cms_lmp_sub_profile_id
	 * borrower_id," + " sci_le_main_profile.lmp_long_name borrower_name," + "
	 * sci_le_main_profile.lmp_le_id borrower_le_id," + " null pledgor_id, null
	 * pledgor_name, null pledgor_le_id, null cms_pledgor_id," + " null
	 * collateral_id, null sci_security_id," + " null security_type_name, null
	 * security_sub_type_name," + " cms_checklist_item.doc_item_ref,
	 * cms_checklist_item.document_code," + "
	 * cms_checklist_item.doc_description, cms_checklist_item.remarks," + "
	 * cms_checklist_item.in_vault, cms_checklist_item.in_ext_custody," + "
	 * cms_checklist_item.status docstatus," + "
	 * DECODE(cms_cust_doc_item.status, 'RECEIVED', null,
	 * cms_cust_doc_item.status) custstatus," + " cms_checklist_item.doc_date,
	 * cms_checklist_item.expiry_date, cms_checklist_item.status
	 * item_status," + " cms_checklist_item.parent_checklist_item_ref
	 * parent_item_ref_id," + " parent_item.document_code parent_item_doc_code
	 * " + "FROM sci_le_main_profile, sci_le_sub_profile, cms_checklist,
	 * cms_checklist_item, cms_cust_doc_item, cms_checklist_item parent_item " +
	 * "WHERE sci_le_main_profile.cms_le_main_profile_id =
	 * sci_le_sub_profile.cms_le_main_profile_id" + " AND
	 * sci_le_sub_profile.cms_le_sub_profile_id =
	 * cms_checklist.cms_lmp_sub_profile_id" + " AND cms_checklist.CATEGORY =
	 * 'CC'" + " AND cms_checklist.sub_category = 'NON_BORROWER'" + " AND
	 * cms_checklist.status NOT IN ('DELETED', 'OBSOLETE')" + " AND
	 * cms_checklist.checklist_id = cms_checklist_item.checklist_id" + " AND
	 * cms_checklist_item.doc_item_ref =
	 * cms_cust_doc_item.checklist_item_ref_id(+)" + " AND
	 * cms_checklist_item.parent_checklist_item_ref = parent_item.doc_item_ref
	 * (+)" + // do not filter out items - to do when processing resultset //"
	 * AND cms_checklist_item.status IN ('COMPLETED', 'EXPIRED')" + " AND
	 * (cms_cust_doc_item.status IS NULL OR cms_cust_doc_item.status !=
	 * 'PERM_UPLIFTED')" + " AND cms_checklist_item.is_deleted = 'N'";
	 */

	private static final String SELECT_SEC_DOC_HELD = "SELECT "
			+ "cms_checklist.checklist_id, "
            + "cms_checklist.status, "
			+ "cms_checklist.category, "
			+ "cms_checklist.sub_category, "
			+ "cms_checklist.doc_orig_country, "
			+ "cms_checklist.doc_orig_organisation, "
			+ "CAST(NULL AS DECIMAL) borrower_id, "
			+ "sci_le_main_profile.lmp_long_name borrower_name, "
			+ "sci_le_main_profile.lmp_le_id borrower_le_id, "
			+ "CAST(NULL AS NUMBER) pledgor_id, "
			+ "CAST(NULL AS VARCHAR(100)) pledgor_name, "
			+ "CAST(NULL AS VARCHAR(20)) pledgor_le_id, "
			+ "CAST(NULL AS NUMBER) cms_pledgor_id, "
			+ "cms_checklist.cms_collateral_id collateral_id, "
			+ "cms_security.sci_security_dtl_id sci_security_id, "
			+ "cms_security.type_name security_type_name, "
			+ "cms_security.subtype_name security_sub_type_name, "
			+ "cms_checklist_item.doc_item_ref, "
			+ "cms_checklist_item.document_code, "
			+ "cms_checklist_item.doc_description, "
			+ "cms_checklist_item.remarks, "
			+ "cms_checklist_item.in_vault, "
			+ "cms_checklist_item.in_ext_custody, "
			+ "cms_checklist_item.status docstatus, "
			+ "cms_checklist_item.cpc_cust_status cpc_custstatus,			  "
			// + "CASE "
			// + " WHEN cms_cust_doc_item.STATUS = 'RECEIVED' THEN NULL "
			// + " WHEN cms_cust_doc_item.STATUS = 'DELETED' THEN NULL "
			// + " ELSE cms_cust_doc_item.STATUS "
			// + "END CUSTSTATUS, "
			+ "cms_checklist_item.doc_date, "
			+ "cms_checklist_item.expiry_date, "
			+ "cms_checklist_item.status item_status, "
			+ "cms_checklist_item.parent_checklist_item_ref parent_item_ref_id, "
			+ "parent_item.document_code parent_item_doc_code, "
			+ "CMS_CHECKLIST_ITEM.SHARE_STATUS, "
			+ "'share_details' share_details "
			// + "GETSHAREITEMINFOALL(CMS_CHECKLIST_ITEM.DOC_ITEM_ID,
			// CMS_CHECKLIST.CHECKLIST_ID, CMS_CHECKLIST.CATEGORY) share_details
			// "
			+ "FROM  cms_checklist_item  "
			// + "LEFT OUTER JOIN cms_cust_doc_item "
			// + "ON cms_checklist_item.doc_item_ref =
			// cms_cust_doc_item.checklist_item_ref_id "
			+ "LEFT OUTER JOIN  cms_checklist_item parent_item  "
			+ "ON  cms_checklist_item.parent_checklist_item_ref  = parent_item.doc_item_ref  , "
			+ "	 sci_le_main_profile  "
			+ "RIGHT OUTER JOIN  sci_le_sub_profile  "
			+ "ON  sci_le_main_profile.cms_le_main_profile_id  = sci_le_sub_profile.cms_le_main_profile_id   RIGHT OUTER JOIN  cms_checklist  ON  sci_le_sub_profile.cms_le_sub_profile_id  = cms_checklist.cms_lmp_sub_profile_id , "
			+ "	 cms_security  "
			+ "WHERE cms_checklist.cms_collateral_id  = cms_security.cms_collateral_id "
			+ "AND	 cms_checklist.category  = 'S' "
			// + "AND cms_checklist.status <> 'OBSOLETE' "
			//+ "AND	 cms_checklist.status  = 'COMPLETED' "
			+ "AND	 cms_checklist.checklist_id  = cms_checklist_item.checklist_id "
			+ "AND	 cms_checklist_item.is_deleted  = 'N' ";

	private static final String SELECT_CAM_DOC_HELD = "SELECT "
		+ "cms_checklist.checklist_id, "
        + "cms_checklist.status, "
		+ "cms_checklist.category, "
		+ "cms_checklist.sub_category, "
		+ "cms_checklist.doc_orig_country, "
		+ "cms_checklist.doc_orig_organisation, "
		+ "CAST(NULL AS DECIMAL) borrower_id, "
		+ "sci_le_main_profile.lmp_long_name borrower_name, "
		+ "sci_le_main_profile.lmp_le_id borrower_le_id, "
		+ "CAST(NULL AS NUMBER) pledgor_id, "
		+ "CAST(NULL AS VARCHAR(100)) pledgor_name, "
		+ "CAST(NULL AS VARCHAR(20)) pledgor_le_id, "
		+ "CAST(NULL AS NUMBER) cms_pledgor_id, "
		+ "cms_checklist.cms_collateral_id collateral_id, "
		+ "CAST(NULL AS VARCHAR(50)) sci_security_id, "
		+ "CAST(NULL AS VARCHAR(40)) security_type_name, "
		+ "CAST(NULL AS VARCHAR(60)) security_sub_type_name, "
		+ "cms_checklist_item.doc_item_ref, "
		+ "cms_checklist_item.document_code, "
		+ "cms_checklist_item.doc_description, "
		+ "cms_checklist_item.remarks, "
		+ "cms_checklist_item.in_vault, "
		+ "cms_checklist_item.in_ext_custody, "
		+ "cms_checklist_item.status docstatus, "
		+ "cms_checklist_item.cpc_cust_status cpc_custstatus,			  "
		// + "CASE "
		// + " WHEN cms_cust_doc_item.STATUS = 'RECEIVED' THEN NULL "
		// + " WHEN cms_cust_doc_item.STATUS = 'DELETED' THEN NULL "
		// + " ELSE cms_cust_doc_item.STATUS "
		// + "END CUSTSTATUS, "
		+ "cms_checklist_item.doc_date, "
		+ "cms_checklist_item.expiry_date, "
		+ "cms_checklist_item.status item_status, "
		+ "cms_checklist_item.parent_checklist_item_ref parent_item_ref_id, "
		+ "parent_item.document_code parent_item_doc_code, "
		+ "CMS_CHECKLIST_ITEM.SHARE_STATUS, "
		+ "'share_details' share_details "
		// + "GETSHAREITEMINFOALL(CMS_CHECKLIST_ITEM.DOC_ITEM_ID,
		// CMS_CHECKLIST.CHECKLIST_ID, CMS_CHECKLIST.CATEGORY) share_details
		// "
		+ "FROM  cms_checklist_item  "
		// + "LEFT OUTER JOIN cms_cust_doc_item "
		// + "ON cms_checklist_item.doc_item_ref =
		// cms_cust_doc_item.checklist_item_ref_id "
		+ "LEFT OUTER JOIN  cms_checklist_item parent_item  "
		+ "ON  cms_checklist_item.parent_checklist_item_ref  = parent_item.doc_item_ref  , "
		+ "	 sci_le_main_profile  "
		+ "RIGHT OUTER JOIN  sci_le_sub_profile  "
		+ "ON  sci_le_main_profile.cms_le_main_profile_id  = sci_le_sub_profile.cms_le_main_profile_id   RIGHT OUTER JOIN  cms_checklist  ON  sci_le_sub_profile.cms_le_sub_profile_id  = cms_checklist.cms_lmp_sub_profile_id  "
		+ "	  "
		+ "WHERE  cms_checklist.category  = 'CAM' "
		+ " "
		// + "AND cms_checklist.status <> 'OBSOLETE' "
		//+ "AND	 cms_checklist.status  = 'COMPLETED' "
		+ "AND	 cms_checklist.checklist_id  = cms_checklist_item.checklist_id "
		+ "AND	 cms_checklist_item.is_deleted  = 'N' ";
	
	
		private static final String SELECT_FACILITY_DOC_HELD = "SELECT "
			+ "cms_checklist.checklist_id, "
            + "cms_checklist.status, "
			+ "cms_checklist.category, "
			+ "cms_checklist.sub_category, "
			+ "cms_checklist.doc_orig_country, "
			+ "cms_checklist.doc_orig_organisation, "
			+ "CAST(NULL AS DECIMAL) borrower_id, "
			+ "sci_le_main_profile.lmp_long_name borrower_name, "
			+ "sci_le_main_profile.lmp_le_id borrower_le_id, "
			+ "CAST(NULL AS NUMBER) pledgor_id, "
			+ "CAST(NULL AS VARCHAR(100)) pledgor_name, "
			+ "CAST(NULL AS VARCHAR(20)) pledgor_le_id, "
			+ "CAST(NULL AS NUMBER) cms_pledgor_id, "
			+ "cms_checklist.cms_collateral_id collateral_id, "
			+ "CAST(NULL AS VARCHAR(50)) sci_security_id, "
			+ "CAST(NULL AS VARCHAR(40)) security_type_name, "
			+ "CAST(NULL AS VARCHAR(60)) security_sub_type_name, "
			+ "cms_checklist_item.doc_item_ref, "
			+ "cms_checklist_item.document_code, "
			+ "cms_checklist_item.doc_description, "
			+ "cms_checklist_item.remarks, "
			+ "cms_checklist_item.in_vault, "
			+ "cms_checklist_item.in_ext_custody, "
			+ "cms_checklist_item.status docstatus, "
			+ "cms_checklist_item.cpc_cust_status cpc_custstatus,			  "
			// + "CASE "
			// + " WHEN cms_cust_doc_item.STATUS = 'RECEIVED' THEN NULL "
			// + " WHEN cms_cust_doc_item.STATUS = 'DELETED' THEN NULL "
			// + " ELSE cms_cust_doc_item.STATUS "
			// + "END CUSTSTATUS, "
			+ "cms_checklist_item.doc_date, "
			+ "cms_checklist_item.expiry_date, "
			+ "cms_checklist_item.status item_status, "
			+ "cms_checklist_item.parent_checklist_item_ref parent_item_ref_id, "
			+ "parent_item.document_code parent_item_doc_code, "
			+ "CMS_CHECKLIST_ITEM.SHARE_STATUS, "
			+ "'share_details' share_details "
			// + "GETSHAREITEMINFOALL(CMS_CHECKLIST_ITEM.DOC_ITEM_ID,
			// CMS_CHECKLIST.CHECKLIST_ID, CMS_CHECKLIST.CATEGORY) share_details
			// "
			+ "FROM  cms_checklist_item  "
			// + "LEFT OUTER JOIN cms_cust_doc_item "
			// + "ON cms_checklist_item.doc_item_ref =
			// cms_cust_doc_item.checklist_item_ref_id "
			+ "LEFT OUTER JOIN  cms_checklist_item parent_item  "
			+ "ON  cms_checklist_item.parent_checklist_item_ref  = parent_item.doc_item_ref  , "
			+ "	 sci_le_main_profile  "
			+ "RIGHT OUTER JOIN  sci_le_sub_profile  "
			+ "ON  sci_le_main_profile.cms_le_main_profile_id  = sci_le_sub_profile.cms_le_main_profile_id   RIGHT OUTER JOIN  cms_checklist  ON  sci_le_sub_profile.cms_le_sub_profile_id  = cms_checklist.cms_lmp_sub_profile_id "
			+ "WHERE cms_checklist.category  = 'F' "
			// + "AND cms_checklist.status <> 'OBSOLETE' "
			//+ "AND	 cms_checklist.status  = 'COMPLETED' "
			+ "AND	 cms_checklist.checklist_id  = cms_checklist_item.checklist_id "
			+ "AND	 cms_checklist_item.is_deleted  = 'N' ";

	
	
	
	
	
	
	
	
	
	private static final String SELECT_SEC_DOC_HELD_SHARE_FOR_S = "SELECT "
			+ "cms_checklist_item_share.checklist_id, "
			+ "cms_checklist.category, "
			+ "cms_checklist.sub_category, "
			+ "cms_checklist.doc_orig_country, "
			+ "cms_checklist.doc_orig_organisation, "
			+ "CAST(NULL AS DECIMAL) borrower_id, "
			+ "sci_le_main_profile.lmp_long_name borrower_name, "
			+ "sci_le_main_profile.lmp_le_id borrower_le_id, "
			+ "CAST(NULL AS NUMBER) pledgor_id, "
			+ "CAST(NULL AS VARCHAR(100)) pledgor_name, "
			+ "CAST(NULL AS VARCHAR(20)) pledgor_le_id, "
			+ "CAST(NULL AS NUMBER) cms_pledgor_id, "
			+ "cms_checklist.cms_collateral_id collateral_id, "
			+ "cms_security.sci_security_dtl_id sci_security_id, "
			+ "cms_security.type_name security_type_name, "
			+ "cms_security.subtype_name security_sub_type_name, "
			+ "cms_checklist_item.doc_item_ref, "
			+ "cms_checklist_item.document_code, "
			+ "cms_checklist_item.doc_description, "
			+ "cms_checklist_item.remarks, "
			+ "cms_checklist_item.in_vault, "
			+ "cms_checklist_item.in_ext_custody, "
			+ "cms_checklist_item.status docstatus, "
			+ "cms_checklist_item.cpc_cust_status cpc_custstatus,			  "
			// + "CASE "
			// + " WHEN cms_cust_doc_item.STATUS = 'RECEIVED' THEN NULL "
			// + " WHEN cms_cust_doc_item.STATUS = 'DELETED' THEN NULL "
			// + " ELSE cms_cust_doc_item.STATUS "
			// + "END CUSTSTATUS, "
			+ "cms_checklist_item.doc_date, "
			+ "cms_checklist_item.expiry_date, "
			+ "cms_checklist_item.status item_status, "
			+ "cms_checklist_item.parent_checklist_item_ref parent_item_ref_id, "
			+ "parent_item.document_code parent_item_doc_code, "
			+ "CMS_CHECKLIST_ITEM.SHARE_STATUS, "
			// + "GETSHAREITEMINFOALL(CMS_CHECKLIST_ITEM.DOC_ITEM_ID,
			// CMS_CHECKLIST_ITEM_SHARE.CHECKLIST_ID, CMS_CHECKLIST.CATEGORY)
			// share_details "
			+ "'share_details' share_details "
			+ "FROM  cms_checklist_item  "
			// + "LEFT OUTER JOIN cms_cust_doc_item "
			// + "ON cms_checklist_item.doc_item_ref =
			// cms_cust_doc_item.checklist_item_ref_id "
			+ "LEFT OUTER JOIN  cms_checklist_item parent_item  "
			+ "ON  cms_checklist_item.parent_checklist_item_ref  = parent_item.doc_item_ref, "
			+ "sci_le_main_profile  RIGHT OUTER JOIN  sci_le_sub_profile  "
			+ "ON  sci_le_main_profile.cms_le_main_profile_id  = sci_le_sub_profile.cms_le_main_profile_id   "
			+ "RIGHT OUTER JOIN  cms_checklist  ON  sci_le_sub_profile.cms_le_sub_profile_id  = cms_checklist.cms_lmp_sub_profile_id, "
			+ "cms_security, "
			+ "	 cms_checklist_item_share  "
			+ "WHERE cms_checklist.cms_collateral_id  = cms_security.cms_collateral_id "
			+ "AND	 cms_checklist.category  = 'S' "
			// + "AND cms_checklist.status <> 'OBSOLETE' "
			+ "AND	 cms_checklist.status  = 'COMPLETED' "
			+ "AND	 cms_checklist.checklist_id  = cms_checklist_item_share.checklist_id "
			+ "AND	 cms_checklist_item_share.DOC_ITEM_ID  = cms_checklist_item.DOC_ITEM_ID "
			+ "AND	 cms_checklist_item.is_deleted  = 'N' "
			+ "AND	(cms_checklist_item_share.status  is null OR cms_checklist_item_share.status  <> 'DELETED') ";

	private static final String SELECT_DOC_HELD_ORDER_BY = " ORDER BY category, sub_category, borrower_name, pledgor_name, security_type_name, security_sub_type_name, sci_security_id, checklist_id, doc_item_ref";

	// to b replaced by SELECT_CC_DOC_HELD_*
	// CR34 : custodian doc migrated from cms_custodian_doc to cms_cust_doc and
	// cms_cust_doc_item
	private static final String SELECT_CC_DOCUMENTS_HELD = "SELECT "
			+ "cms_checklist.checklist_id, "
			+ "cms_checklist.CATEGORY, "
			+ "cms_checklist.sub_category, "
			+ "CAST(NULL AS DECIMAL) collateral_id, "
			+ "cms_checklist_item.doc_item_ref, "
			+ "cms_checklist_item.document_code, "
			+ "cms_checklist_item.doc_description, "
			+ "cms_checklist_item.remarks, "
			+ "cms_checklist_item.in_vault, "
			+ "cms_checklist_item.in_ext_custody, "
			+ "cms_checklist_item.status docstatus,			  "
			+ "CASE  "
			+ "	WHEN cms_cust_doc_item.status  = 'RECEIVED' THEN NULL  "
			+ "	ELSE cms_cust_doc_item.status  "
			+ "END custstatus, "
			+ "cms_checklist_item.doc_date, "
			+ "cms_checklist_item.expiry_date, "
			+ "cms_checklist_item.parent_checklist_item_ref "
			+ "FROM  cms_checklist_item  LEFT OUTER JOIN  cms_cust_doc_item  ON  cms_checklist_item.doc_item_ref  = cms_cust_doc_item.checklist_item_ref_id  , "
			+ "	 cms_checklist  " + "WHERE cms_checklist.checklist_id  = cms_checklist_item.checklist_id "
			+ "AND	 cms_checklist_item.status  IN ( 'COMPLETED'  , 'EXPIRED'  ) "
			+ "AND	 cms_checklist.category  = 'CC' " + "AND	 CMS_CHECKLIST_ITEM.IS_DELETED  = 'N' ";

	// to b replaced by SELECT_SEC_DOC_HELD
	// CR34 : custoidan doc migrated from cms_custodian_doc to cms_cust_doc and
	// cms_cust_doc_item
	private static final String SELECT_SEC_DOCUMENTS_HELD = "SELECT "
			+ "cms_checklist.checklist_id, "
			+ "cms_checklist.CATEGORY, "
			+ "cms_checklist.sub_category, "
			+ "cms_checklist.cms_collateral_id, "
			+ "cms_checklist_item.doc_item_ref, "
			+ "cms_checklist_item.document_code, "
			+ "cms_checklist_item.doc_description, "
			+ "cms_checklist_item.remarks, "
			+ "cms_checklist_item.in_vault, "
			+ "cms_checklist_item.in_ext_custody, "
			+ "cms_checklist_item.status docstatus,			  "
			+ "CASE  "
			+ "	WHEN cms_cust_doc_item.status  = 'RECEIVED' THEN NULL  "
			+ "	ELSE cms_cust_doc_item.status  "
			+ "END custstatus, "
			+ "cms_checklist_item.doc_date, "
			+ "cms_checklist_item.expiry_date, "
			+ "cms_checklist_item.parent_checklist_item_ref "
			+ "FROM  cms_checklist_item  LEFT OUTER JOIN  cms_cust_doc_item  ON  cms_checklist_item.doc_item_ref  = cms_cust_doc_item.checklist_item_ref_id  , "
			+ "	 cms_checklist  " + "WHERE cms_checklist.checklist_id  = cms_checklist_item.checklist_id "
			+ "AND	 cms_checklist_item.status  IN ( 'COMPLETED'  , 'EXPIRED'  ) "
			+ "AND	 cms_checklist.category  = 'S' " + "AND	 CMS_CHECKLIST_ITEM.IS_DELETED  = 'N' ";

	private static final String SELECT_SEC_PLEDGED = "SELECT DISTINCT CMS_SECURITY.SCI_SECURITY_DTL_ID, CMS_SECURITY.TYPE_NAME, CMS_SECURITY.SUBTYPE_NAME FROM SCI_PLEDGOR_DTL, "
			+ " SCI_SEC_PLDGR_MAP, CMS_SECURITY, CMS_LIMIT_SECURITY_MAP, SCI_LSP_APPR_LMTS WHERE SCI_PLEDGOR_DTL.CMS_PLEDGOR_DTL_ID = SCI_SEC_PLDGR_MAP.CMS_PLEDGOR_DTL_ID "
			+ " AND SCI_SEC_PLDGR_MAP.CMS_COLLATERAL_ID = CMS_SECURITY.CMS_COLLATERAL_ID AND CMS_SECURITY.CMS_COLLATERAL_ID = CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID "
			+ " AND CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID = SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID";

	private static final String SELECT_SECURITY_PLEDGED = "SELECT DISTINCT sci_pledgor_dtl.plg_pledgor_id pledgor_id,"
			+ "  cms_security.type_name security_type_name," + "  cms_security.subtype_name security_subtype_name,"
			+ "  cms_security.sci_security_dtl_id sci_security_id " + "FROM sci_pledgor_dtl," + "  sci_sec_pldgr_map,"
			+ "  cms_security," + "  cms_limit_security_map," + "  sci_lsp_appr_lmts "
			+ "WHERE sci_pledgor_dtl.cms_pledgor_dtl_id = sci_sec_pldgr_map.cms_pledgor_dtl_id"
			+ "  AND sci_sec_pldgr_map.cms_collateral_id = cms_security.cms_collateral_id"
			+ "  AND cms_security.cms_collateral_id = cms_limit_security_map.cms_collateral_id"
			+ "  AND cms_limit_security_map.cms_lsp_appr_lmts_id = sci_lsp_appr_lmts.cms_lsp_appr_lmts_id";

	private static final String SELECT_SECURITY_PLEDGED_ORDER_BY = " ORDER BY sci_pledgor_dtl.plg_pledgor_id, cms_security.type_name, cms_security.subtype_name, cms_security.sci_security_dtl_id";

	private static final String SELECT_CHECKLIST_TRX = "SELECT CMS_CHECKLIST.CHECKLIST_ID,CMS_CHECKLIST.CMS_LSP_LMT_PROFILE_ID,CMS_CHECKLIST.MASTERLIST_ID,CMS_CHECKLIST.CMS_COLLATERAL_ID,"
			+ " CMS_CHECKLIST.CMS_LMP_SUB_PROFILE_ID,CMS_CHECKLIST.CMS_PLEDGOR_DTL_ID,CMS_CHECKLIST.CATEGORY,CMS_CHECKLIST.SUB_CATEGORY,CMS_CHECKLIST.STATUS,"
			+ " CMS_CHECKLIST.DOC_ORIG_COUNTRY,CMS_CHECKLIST.DOC_ORIG_ORGANISATION,CMS_CHECKLIST.ALLOW_DELETE_IND, TRANSACTION.TRANSACTION_ID,"
			+ " TRANSACTION.STATUS AS TRX_STATUS FROM CMS_CHECKLIST,TRANSACTION WHERE TRANSACTION.REFERENCE_ID = CMS_CHECKLIST.CHECKLIST_ID "
			+ " AND TRANSACTION.TRANSACTION_TYPE = 'CHECKLIST' AND TRANSACTION.TO_GROUP_TYPE_ID <> 13 "
			+ " AND ( TRANSACTION.STATUS='PENDING_AUTH' OR TRANSACTION.STATUS='PENDING_OFFICE' OR TRANSACTION.STATUS='PENDING_VERIFY')";

	private static final String ORDER_BY_ITEM_DESC = " ORDER BY CMS_CHECKLIST.CHECKLIST_ID, CMS_CHECKLIST_ITEM.DOC_DESCRIPTION";

	private static final String SELECT_LE_PARAMS = "SELECT " + "cms_checklist.category, "
			+ "cms_checklist.sub_category, " + "sci_le_main_profile.lmp_le_id le_id, "
			+ "sci_le_main_profile.lmp_long_name le_name, " + "CAST(NULL AS NUMBER) pledgor_id, "
			+ "CAST(NULL AS VARCHAR(100)) pledgor_name, " + "CAST(NULL AS NUMBER) sci_security_id, "
			+ "CAST(NULL AS VARCHAR(100)) security_type_subtype " + "FROM  sci_le_main_profile, "
			+ "	 sci_le_sub_profile, " + "	 cms_checklist  "
			+ "WHERE sci_le_main_profile.cms_le_main_profile_id  = sci_le_sub_profile.cms_le_main_profile_id "
			+ "AND	 sci_le_sub_profile.cms_le_sub_profile_id  = cms_checklist.cms_lmp_sub_profile_id "
			+ "AND	 cms_checklist.checklist_id  = ? " + "UNION " + "SELECT " + "cms_checklist.category, "
			+ "cms_checklist.sub_category, " + "CAST(NULL AS NUMBER) le_id, " + "CAST(NULL AS VARCHAR(100)) le_name, "
			+ "sci_pledgor_dtl.plg_pledgor_id pledgor_id, " + "sci_pledgor_dtl.plg_legal_name pledgor_name, "
			+ "CAST(NULL AS NUMBER) sci_security_id, " + "CAST(NULL AS VARCHAR(100)) security_type_subtype "
			+ "FROM  sci_pledgor_dtl, " + "	 cms_checklist  "
			+ "WHERE sci_pledgor_dtl.cms_pledgor_dtl_id  = cms_checklist.cms_pledgor_dtl_id "
			+ "AND	 cms_checklist.checklist_id  = ? " + "UNION " + "SELECT " + "cms_checklist.category, "
			+ "cms_checklist.sub_category, " + "mp.lmp_le_id le_id, " + "mp.lmp_long_name le_name, "
			+ "mp.lmp_le_id pledgor_id, " + "CAST(NULL AS VARCHAR(100)) pledgor_name, "
			+ "cms_security.sci_security_dtl_id sci_security_id, "
			+ "cms_security.type_name || '/' || cms_security.subtype_name security_type_subtype "
			+ "FROM  cms_security, " + "	 cms_checklist, " + "	 sci_lsp_lmt_profile llp, "
			+ "	 sci_le_sub_profile sp, " + "	 sci_le_main_profile mp  "
			+ "WHERE cms_checklist.cms_collateral_id  = cms_security.cms_collateral_id "
			+ "AND	 cms_checklist.cms_lsp_lmt_profile_id  = llp.CMS_LSP_LMT_PROFILE_ID "
			+ "AND	 llp.cms_customer_id  = sp.CMS_LE_SUB_PROFILE_ID "
			+ "AND	 sp.CMS_LE_MAIN_PROFILE_ID  = mp.CMS_LE_MAIN_PROFILE_ID " + "AND	 cms_checklist.checklist_id  = ? "
			+ "UNION " + "SELECT " + "cms_checklist.category, " + "cms_checklist.sub_category, "
			+ "sci_le_main_profile.lmp_le_id le_id, " + "sci_le_main_profile.lmp_long_name le_name, "
			+ "CAST(NULL AS NUMBER) pledgor_id, " + "CAST(NULL AS VARCHAR(100)) pledgor_name, "
			+ "CAST(NULL AS NUMBER) sci_security_id, " + "CAST(NULL AS VARCHAR(100)) security_type_subtype "
			+ "FROM  sci_le_main_profile, " + "	 sci_le_sub_profile, " + "	 cms_checklist  "
			+ "WHERE sci_le_main_profile.cms_le_main_profile_id  = sci_le_sub_profile.cms_le_main_profile_id "
			+ "AND	 sci_le_sub_profile.cms_le_sub_profile_id  = cms_checklist.cms_lmp_sub_profile_id "
			+ "AND	 cms_checklist.checklist_id  = ? ";

	private static final String PRE_DISBURSEMENT_REMINDER_LETTER = "SELECT ENTRY.ENTRY_NAME LAW_FIRM, LAW_FIRM_ADDRESS "
			+ "FROM CMS_CHECKLIST CHK LEFT OUTER JOIN CMS_TAT_DOCUMENT TAT "
			+ "ON TAT.CMS_LSP_LMT_PROFILE_ID = CHK.CMS_LSP_LMT_PROFILE_ID, "
			+ "CMS_CHECKLIST_ITEM ITM, COMMON_CODE_CATEGORY_ENTRY ENTRY  "
			+ "WHERE CHK.CHECKLIST_ID = ITM.CHECKLIST_ID "
			+ "AND ENTRY.CATEGORY_CODE = 'SOLICITOR' "
			+ "AND CHK.LEGAL_FIRM = ENTRY.ENTRY_CODE "
			+ "AND CHK.CMS_LSP_LMT_PROFILE_ID = ? "
			+ "AND ITM.STATUS = 'AWAITING' "
			+ "AND CHK.CATEGORY='S' "
			+ "AND ITM.ACTION_PARTY = 'LAW' "
			+ "AND CHK.LAW_FIRM_PANEL_FLAG = 'Y' "
			+ "AND (TAT.DISBURSMENT_DOC_COMPLETED_DATE IS NULL OR TAT.DOC_COMPLETION_DATE IS NULL) "
			+ "GROUP BY LAWYER_IN_CHARGE, ENTRY.ENTRY_NAME, LAW_FIRM_ADDRESS "
			+ "   UNION  "
			+ "SELECT CHK.LEGAL_FIRM LAW_FIRM, LAW_FIRM_ADDRESS "
			+ "FROM  CMS_CHECKLIST CHK ,CMS_CHECKLIST_ITEM ITM , CMS_TAT_DOCUMENT TAT "
			+ "WHERE CHK.CHECKLIST_ID = ITM.CHECKLIST_ID "
			+ "AND CHK.CMS_LSP_LMT_PROFILE_ID = ? "
			+ "AND ITM.STATUS = 'AWAITING' "
			+ "AND CHK.CATEGORY='S' "
			+ "AND ITM.ACTION_PARTY = 'LAW' "
			+ "AND CHK.LAW_FIRM_PANEL_FLAG = 'N' "
			+ "AND (TAT.DISBURSMENT_DOC_COMPLETED_DATE IS NULL OR TAT.DOC_COMPLETION_DATE IS NULL) "
			+ "GROUP BY LAWYER_IN_CHARGE, CHK.LEGAL_FIRM, LAW_FIRM_ADDRESS ";

	private static final String POST_DISBURSEMENT_REMINDER_LETTER = "SELECT ENTRY.ENTRY_NAME LAW_FIRM, LAW_FIRM_ADDRESS "
			+ "FROM CMS_CHECKLIST CHK, CMS_CHECKLIST_ITEM ITM, COMMON_CODE_CATEGORY_ENTRY ENTRY, CMS_TAT_DOCUMENT TAT "
			+ "WHERE CHK.CHECKLIST_ID = ITM.CHECKLIST_ID "
			+ "AND TAT.CMS_LSP_LMT_PROFILE_ID = CHK.CMS_LSP_LMT_PROFILE_ID "
			+ "AND ENTRY.CATEGORY_CODE = 'SOLICITOR' "
			+ "AND CHK.LEGAL_FIRM = ENTRY.ENTRY_CODE "
			+ "AND CHK.CMS_LSP_LMT_PROFILE_ID = ? "
			+ "AND (ITM.STATUS = 'AWAITING' OR ITM.STATUS = 'DEFERRED') "
			+ "AND CHK.CATEGORY='S' "
			+ "AND ITM.ACTION_PARTY = 'LAW' "
			+ "AND CHK.LAW_FIRM_PANEL_FLAG = 'Y' "
			+ "AND (TAT.DISBURSMENT_DOC_COMPLETED_DATE IS NOT NULL AND TAT.DOC_COMPLETION_DATE IS NULL) "
			+ "GROUP BY LAWYER_IN_CHARGE, ENTRY.ENTRY_NAME, LAW_FIRM_ADDRESS "
			+ "  UNION  "
			+ "SELECT CHK.LEGAL_FIRM LAW_FIRM, LAW_FIRM_ADDRESS "
			+ "FROM  CMS_CHECKLIST CHK ,CMS_CHECKLIST_ITEM ITM ,CMS_TAT_DOCUMENT TAT "
			+ "WHERE CHK.CHECKLIST_ID = ITM.CHECKLIST_ID  "
			+ "AND CHK.CMS_LSP_LMT_PROFILE_ID = ? "
			+ "AND (ITM.STATUS = 'AWAITING' OR ITM.STATUS = 'DEFERRED') "
			+ "AND CHK.CATEGORY='S' "
			+ "AND ITM.ACTION_PARTY = 'LAW' "
			+ "AND CHK.LAW_FIRM_PANEL_FLAG = 'N' "
			+ "AND (TAT.DISBURSMENT_DOC_COMPLETED_DATE IS NOT NULL AND TAT.DOC_COMPLETION_DATE IS NULL) "
			+ "GROUP BY LAWYER_IN_CHARGE, CHK.LEGAL_FIRM, LAW_FIRM_ADDRESS ";
	
	
	private static final String SEARCH_DOC="SELECT DOCUMENT_CODE,DOC_DESCRIPTION,STATUS,ORIGINALTARGETDATE,EXPIRY_DATE,IS_MANDATORY,"
		+"IS_MANDATORY_DISPLAY,DOCUMENTSTATUS,DOCUMENTVERSION,DOC_DATE"
		+" FROM CMS_CHECKLIST_ITEM "+" WHERE IS_DELETED='N'";
	
	private static final String SEARCH_STAGE_DOC="SELECT DOCUMENT_CODE,DOC_DESCRIPTION,STATUS,ORIGINALTARGETDATE,EXPIRY_DATE,IS_MANDATORY,"
		+"IS_MANDATORY_DISPLAY,DOCUMENTSTATUS,DOCUMENTVERSION,DOC_DATE"
		+" FROM STAGE_CHECKLIST_ITEM "+" WHERE IS_DELETED='N'";

	private static final String RETRIVE_CAM="select CAM_TYPE,LLP_BCA_REF_APPR_DATE,LLP_NEXT_ANNL_RVW_DATE"+" from stage_limit_profile where llp_bca_ref_num=";
 	private static final String SEARCH_CHKLIST = "select CMS_CHECKLIST.CHECKLIST_ID " +
 			" from CMS_CHECKLIST where CMS_LSP_LMT_PROFILE_ID = ? " +
 			" and CMS_CHECKLIST.CATEGORY in('O','F','S','CAM','REC') ";
 	private static final String SERACH_CHKLIST_ITEM_BY_CHKLIST_ID = "Select STATUS," +
 			" Document_Code,Doc_Description,DOC_AMT,HDFC_AMT,Currency,Doc_Date,ORIGINALTARGETDATE," +
 			" Expiry_Date,Remarks,Doc_Item_Id,DEFER_EXPIRY_DATE,WAIVED_DATE,RECEIVED_DATE " +
 			" From Cms_Checklist_Item Where Checklist_Id = ? "+ 
 			" and is_deleted='N'";
 	
 	private static final String SEARCH_CHKLISTITEM_BY_CPSID =  "Select count(CPS_ID) " +
			" From Cms_Checklist_Item Where CPS_ID = ? AND IS_DELETED='N' ";
 	
 	private static final String SEARCH_DOC_IN_CHKLIST =  "Select count(1) From Cms_Checklist_Item " +
 			" Where Checklist_Id In(Select Checklist_Id From Cms_Checklist " +
 			" Where Cms_Lsp_Lmt_Profile_Id = ?" +
 			" and Cms_Collateral_Id= ?  " +
 			" and Category=?) " +
 			" and document_code=? and IS_DELETED='N' ";
 	
	/*
	 * public final String SELECT_SHARED_DOC = "SELECT mainprofile.LMP_LE_ID,
	 * mainprofile.lmp_long_name, " + "sec.SCI_SECURITY_DTL_ID,
	 * secsubtype.SECURITY_TYPE_NAME, secsubtype.SUBTYPE_NAME, " + "shareitem. "
	 * + "FROM CMS_CHECKLIST_ITEM_SHARE shareitem, SCI_PLEDGOR_DTL pledgor, " +
	 * "SCI_LE_SUB_PROFILE subprofile, SCI_LE_MAIN_PROFILE mainprofile, " +
	 * "CMS_SECURITY sec, CMS_SECURITY_SUB_TYPE secsubtype " + "WHERE
	 * shareitem.cms_lmp_sub_profile_id = subprofile.cms_le_sub_profile_id(+) "
	 * + "AND subprofile.CMS_LE_MAIN_PROFILE_ID =
	 * mainprofile.cms_le_main_profile_id(+) " + "AND
	 * shareitem.CMS_PLEDGOR_DTL_ID = pledgor.CMS_PLEDGOR_DTL_ID(+) " + "AND
	 * shareitem.cms_collateral_id = sec.CMS_COLLATERAL_ID(+) " + "AND
	 * sec.SECURITY_SUB_TYPE_ID = secsubtype.SECURITY_SUB_TYPE_ID(+) " + "AND
	 * shareitem.doc_item_id = ? " + "AND shareitem.status <> 'DELETED'";
	 * 
	 * public final String SELECT_STAGE_SHARED_DOC = "SELECT
	 * mainprofile.LMP_LE_ID, mainprofile.lmp_long_name, " +
	 * "sec.SCI_SECURITY_DTL_ID, secsubtype.SECURITY_TYPE_NAME,
	 * secsubtype.SUBTYPE_NAME, " + "shareitem. " + "FROM
	 * STAGE_CHECKLIST_ITEM_SHARE shareitem, SCI_PLEDGOR_DTL pledgor, " +
	 * "SCI_LE_SUB_PROFILE subprofile, SCI_LE_MAIN_PROFILE mainprofile, " +
	 * "CMS_SECURITY sec, CMS_SECURITY_SUB_TYPE secsubtype " + "WHERE
	 * shareitem.cms_lmp_sub_profile_id = subprofile.cms_le_sub_profile_id(+) "
	 * + "AND subprofile.CMS_LE_MAIN_PROFILE_ID =
	 * mainprofile.cms_le_main_profile_id(+) " + "AND
	 * shareitem.CMS_PLEDGOR_DTL_ID = pledgor.CMS_PLEDGOR_DTL_ID(+) " + "AND
	 * shareitem.cms_collateral_id = sec.CMS_COLLATERAL_ID(+) " + "AND
	 * sec.SECURITY_SUB_TYPE_ID = secsubtype.SECURITY_SUB_TYPE_ID(+) " + "AND
	 * shareitem.doc_item_id = ? " + "AND shareitem.status <> 'DELETED'";
	 */
	/**
	 * get le id and le name
	 * @param checkListID
	 * @return
	 * @throws SearchDAOException
	 */
	public HashMap getLeParams(long checkListID) throws SearchDAOException {
		HashMap paramMap = new HashMap();
		DBUtil dbUtil = null;
		DefaultLogger.debug(this, "sql is " + SELECT_LE_PARAMS);
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SELECT_LE_PARAMS);
			dbUtil.setLong(1, checkListID);
			dbUtil.setLong(2, checkListID);
			dbUtil.setLong(3, checkListID);
			dbUtil.setLong(4, checkListID);
			ResultSet rs = dbUtil.executeQuery();
			if (rs.next()) {
				paramMap.put("category", rs.getString("category"));
				paramMap.put("sub_category", rs.getString("sub_category"));
				paramMap.put("le_id", rs.getString("le_id"));
				paramMap.put("le_name", rs.getString("le_name"));
				paramMap.put("pledgor_id", rs.getString("pledgor_id"));
				paramMap.put("pledgor_name", rs.getString("pledgor_name"));
			}
			return paramMap;
		}
		catch (DBConnectionException dbe) {
			throw new SearchDAOException(dbe);
		}
		catch (InvalidStatementTypeException ie) {
			throw new SearchDAOException(ie);
		}
		catch (NoSQLStatementException ne) {
			throw new SearchDAOException(ne);
		}
		catch (SQLException se) {
			throw new SearchDAOException(se);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException(e);
			}
		}
	}

	/**
	 * Get the number of limits that a collateral is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @return int - the number of limits that a collateral is related in a
	 *         limit profile
	 * @throws SearchDAOException on errors
	 */
	public int getLimitProfileCollateralCount(long aLimitProfileID, long aCollateralID) throws SearchDAOException {
		DefaultLogger.info(this, "IN method getLimitProfileCollateralCount");
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("SELECT COUNT(*) FROM ");
		strBuf.append("SCI_LSP_LMT_PROFILE PROF, SCI_LSP_APPR_LMTS LMT, ");
		strBuf.append("CMS_LIMIT_SECURITY_MAP LMT_MAP ");
		strBuf.append("WHERE  PROF.CMS_LSP_LMT_PROFILE_ID = LMT.CMS_LIMIT_PROFILE_ID ");
		strBuf.append("AND LMT.CMS_LSP_APPR_LMTS_ID = LMT_MAP.CMS_LSP_APPR_LMTS_ID ");
		strBuf.append("AND LMT.CMS_LIMIT_STATUS <> 'DELETED' ");
		strBuf.append("AND LMT_MAP.UPDATE_STATUS_IND <> 'D' ");
		strBuf.append("AND PROF.CMS_LSP_LMT_PROFILE_ID = ? ");
		strBuf.append("AND PROF.LLP_ID = LMT_MAP.SCI_LAS_LLP_ID ");
		strBuf.append("AND LMT_MAP.CMS_COLLATERAL_ID = ?");
		String sql = strBuf.toString();

		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, aLimitProfileID);
			dbUtil.setLong(2, aCollateralID);
			ResultSet rs = dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			rs.close();
			return count;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getLimitProfileCollateralCount", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getLimitProfileCollateralCount", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getLimitProfileCollateralCount", ex);
			}
		}
	}

	public int getLimitProfilePledgorCount(long aLimitProfileID, long aPledgorID, long[] aDeletedLimitSecMapList)
			throws SearchDAOException {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("SELECT COUNT(*) FROM ");
		strBuf.append("SCI_LSP_LMT_PROFILE PROF, SCI_LSP_APPR_LMTS LMT, ");
		strBuf.append("CMS_LIMIT_SECURITY_MAP LMT_MAP, SCI_SEC_PLDGR_MAP PLG_MAP ");
		strBuf.append("WHERE  PROF.CMS_LSP_LMT_PROFILE_ID = LMT.CMS_LIMIT_PROFILE_ID ");
		strBuf.append("AND LMT.CMS_LSP_APPR_LMTS_ID = LMT_MAP.CMS_LSP_APPR_LMTS_ID ");
		strBuf.append("AND LMT_MAP.CMS_COLLATERAL_ID = PLG_MAP.CMS_COLLATERAL_ID ");
		strBuf.append("AND LMT.CMS_LIMIT_STATUS <> 'DELETED' ");
		strBuf.append("AND LMT_MAP.UPDATE_STATUS_IND <> 'D' ");
		if ((aDeletedLimitSecMapList != null) && (aDeletedLimitSecMapList.length > 0)) {
			strBuf.append(getDeletedLimitSecMapQuery(aDeletedLimitSecMapList));
		}
		strBuf.append("AND PROF.CMS_LSP_LMT_PROFILE_ID = ");
		strBuf.append(aLimitProfileID);
		strBuf.append(" ");
		strBuf.append("AND PLG_MAP.CMS_PLEDGOR_DTL_ID = ");
		strBuf.append(aPledgorID);

		String sql = strBuf.toString();
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			rs.close();
			return count;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getLimitProfilePledgorCount", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getLimitProfilePledgorCount", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getLimitProfilePledgorCount", ex);
			}
		}
	}

	private String getDeletedLimitSecMapQuery(long[] aDeletedLimitSecMapList) {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("AND LMT_MAP.CHARGE_ID NOT IN (");
		for (int ii = 0; ii < aDeletedLimitSecMapList.length; ii++) {
			strBuf.append(aDeletedLimitSecMapList[ii]);
			if (ii < aDeletedLimitSecMapList.length - 1) {
				strBuf.append(", ");
			}
		}
		strBuf.append(")");
		return strBuf.toString();
	}

	/**
	 * Get checklist item sequence number. This number is useful if the caller
	 * needs to set checklist item reference before the actual primary key
	 * creation.
	 * 
	 * @return checklist item sequence number
	 */
	public long getCheckListItemSeqNo() {
		try {
			return Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_CHECKLIST_ITEM, true));
		}
		catch (Exception e) {
			DefaultLogger.warn("WARNING! Encountered Error in getCheckListItemSeqNo!", e);
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Get the number of limits that a pledgor is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aPledgorID of long type
	 * @return int - the number of limits that a pledgor is related in a limit
	 *         profile
	 * @throws SearchDAOException on errors
	 */
	public int getLimitProfilePledgorCount(long aLimitProfileID, long aPledgorID) throws SearchDAOException {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("SELECT COUNT(*) FROM ");
		strBuf.append("SCI_LSP_LMT_PROFILE PROF, SCI_LSP_APPR_LMTS LMT, ");
		strBuf.append("CMS_LIMIT_SECURITY_MAP LMT_MAP, SCI_SEC_PLDGR_MAP PLG_MAP ");
		strBuf.append("WHERE  PROF.CMS_LSP_LMT_PROFILE_ID = LMT.CMS_LIMIT_PROFILE_ID ");
		strBuf.append("AND LMT.CMS_LSP_APPR_LMTS_ID = LMT_MAP.CMS_LSP_APPR_LMTS_ID ");
		strBuf.append("AND LMT_MAP.CMS_COLLATERAL_ID = PLG_MAP.CMS_COLLATERAL_ID ");
		strBuf.append("AND LMT.CMS_LIMIT_STATUS <> 'DELETED' ");
		strBuf.append("AND LMT_MAP.UPDATE_STATUS_IND <> 'D' ");
		strBuf.append("AND PROF.CMS_LSP_LMT_PROFILE_ID = ?");
		strBuf.append(" ");
		strBuf.append("AND PLG_MAP.CMS_PLEDGOR_DTL_ID = ?");

		String sql = strBuf.toString();
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, aLimitProfileID);
			dbUtil.setLong(2, aPledgorID);
			ResultSet rs = dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			rs.close();
			return count;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getLimitProfilePledgorCount", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getLimitProfilePledgorCount", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getLimitProfilePledgorCount", ex);
			}
		}
	}

	/**
	 * Get the number of limits that a coborrower is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return int - the number of limits that a coborrower is related in a
	 *         limit profile
	 * @throws SearchDAOException on errors
	 */
	public int getLimitProfileCoBorrowerCount(long aLimitProfileID, long aCustomerID) throws SearchDAOException {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("SELECT COUNT(*) FROM ");
		strBuf.append("SCI_LSP_LMT_PROFILE PROF, SCI_LSP_APPR_LMTS LMT, ");
		strBuf.append("SCI_LSP_CO_BORROW_LMT COB ");
		strBuf.append("WHERE  PROF.CMS_LSP_LMT_PROFILE_ID = LMT.CMS_LIMIT_PROFILE_ID ");
		strBuf.append("AND	   LMT.CMS_LSP_APPR_LMTS_ID 	= COB.CMS_LIMIT_ID ");
		strBuf.append("AND	   LMT.CMS_LIMIT_STATUS 		<> 'DELETED' ");
		strBuf.append("AND	   COB.CMS_LIMIT_STATUS			<> 'DELETED' ");
		strBuf.append("AND PROF.CMS_LSP_LMT_PROFILE_ID = ?");
		strBuf.append(" ");
		strBuf.append("AND	   COB.CMS_CUSTOMER_ID 			= ?");

		String sql = strBuf.toString();
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, aLimitProfileID);
			dbUtil.setLong(2, aCustomerID);
			ResultSet rs = dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			rs.close();
			return count;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getLimitProfileCoBorrowerCount", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getLimitProfileCoBorrowerCount", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getLimitProfileCoBorrowerCount", ex);
			}
		}
	}

	/**
	 * Get Hashtable with collateral ID and the status
	 * @param aLimitProfileID of long type
	 * @return HashMap - this the security ID as the key and the status as the
	 *         value
	 * @throws SearchDAOException on errors
	 */
	public HashMap getCollateralCheckListStatus(long aLimitProfileID) throws SearchDAOException {
		DefaultLogger.debug(this, "Inside getCollateralCheckListStatus() aLimitProfileID="+aLimitProfileID);
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_LIMIT_PROFILE_ID_PREF);
		strBuf.append(" = ?");
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_CATEGORY_PREF);
		strBuf.append(" = ?");
		String sql = SELECT_CHECKLIST + strBuf.toString();
		// for cr-17--> commented since there is no impact checklistStatus
		// String sql = SELECT_CHECKLIST + strBuf.toString() + " UNION " +
		// getSelectCollateralCheckListStatusShare(aLimitProfileID) ;
		// DefaultLogger.info(this, "getCollateralCheckListStatus sql \n\n " +
		// sql + "\n\n");
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, aLimitProfileID);
			dbUtil.setString(2, ICMSConstant.DOC_TYPE_SECURITY);
			ResultSet rs = dbUtil.executeQuery();
			HashMap map = new HashMap();
			CheckListSearchResult checkList = null;
			long collateralID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			while (rs.next()) {
				collateralID = rs.getLong(CHKTBL_COLLATERAL_ID);
				checkList = new CheckListSearchResult();
				checkList.setCheckListID(rs.getLong(CHKTBL_CHECKLISTID));
				checkList.setCheckListType(rs.getString(CHKTBL_CATEGORY));
				checkList.setCustomerType(rs.getString(CHKTBL_SUB_CATEGORY));
				checkList.setCheckListStatus(rs.getString(CHKTBL_STATUS));
				checkList.setApplicationType(rs.getString(CHKTBL_APPLICATION_TYPE));
				checkList.setTrxID(rs.getString(TRXTBL_TRANSACTION_ID));
				checkList.setTrxStatus(rs.getString("TRX_STATUS"));
				checkList.setTrxFromState(rs.getString(TRXTBL_FROM_STATE));
				IBookingLocation location = new OBBookingLocation(rs.getString(CHKTBL_DOC_LOC_CTRY), rs
						.getString(CHKTBL_DOC_LOC_ORG));
				checkList.setCheckListLocation(location);
				checkList.setCheckListOrganization(rs.getString(CHKTBL_DOC_LOC_ORG));
				if (ICMSConstant.TRUE_VALUE.equals(rs.getString(CHKTBL_ALLOW_DELETE_IND))) {
					checkList.setAllowDeleteInd(true);
				}
				checkList.setDisableTaskInd(rs.getString(CHKTBL_DISABLE_TASK_IND));
				map.put(new Long(collateralID), checkList);
			}
			rs.close();
			return map;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
			}
		}
	}

	
	private StringBuffer getSelectCollateralCheckListStatusShare(long aLimitProfileID) {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(SELECT_CHECKLIST_SHARE);
		if (aLimitProfileID > 0) {
			strBuf.append(" AND cms_checklist.cms_lsp_lmt_profile_id =  ");
			strBuf.append(new Long(aLimitProfileID));
		}
		return strBuf;
	}

	public CheckListSearchResult getCheckListByCollateralID(long collateralId) throws SearchDAOException {

		/*final String SELECT_CHECKLIST_BY_COLLATERAL_ID = "SELECT CMS_CHECKLIST.CHECKLIST_ID"
			+ " FROM CMS_CHECKLIST"
			+ " WHERE ";*/
		
		
		
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_COLLATERAL_ID);
		strBuf.append(" = ?");
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_CATEGORY_PREF);
		strBuf.append(" = ?");
//		String sql = SELECT_CHECKLIST_BY_COLLATERAL_ID + strBuf.toString();
		String sql = SELECT_CHECKLIST + strBuf.toString();
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, collateralId);
			dbUtil.setString(2, ICMSConstant.DOC_TYPE_FACILITY);
			ResultSet rs = dbUtil.executeQuery();
			
			// By abhijit for complete checklist object
		/*	long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			while (rs.next()) {
				checkListID = rs.getLong(CHKTBL_CHECKLISTID);
							
			}
			rs.close();
			return new Long(checkListID);*/ 
			HashMap map = new HashMap();
			CheckListSearchResult checkList = null;
			//long collateralID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			while (rs.next()) {
				//collateralID = rs.getLong(CHKTBL_COLLATERAL_ID);
				checkList = new CheckListSearchResult();
				checkList.setCheckListID(rs.getLong(CHKTBL_CHECKLISTID));
				checkList.setCheckListType(rs.getString(CHKTBL_CATEGORY));
				checkList.setCustomerType(rs.getString(CHKTBL_SUB_CATEGORY));
				checkList.setCheckListStatus(rs.getString(CHKTBL_STATUS));
				checkList.setApplicationType(rs.getString(CHKTBL_APPLICATION_TYPE));
				checkList.setTrxID(rs.getString(TRXTBL_TRANSACTION_ID));
				checkList.setTrxStatus(rs.getString("TRX_STATUS"));
				checkList.setTrxFromState(rs.getString(TRXTBL_FROM_STATE));
				IBookingLocation location = new OBBookingLocation(rs.getString(CHKTBL_DOC_LOC_CTRY), rs
						.getString(CHKTBL_DOC_LOC_ORG));
				checkList.setCheckListLocation(location);
				checkList.setCheckListOrganization(rs.getString(CHKTBL_DOC_LOC_ORG));
				if (ICMSConstant.TRUE_VALUE.equals(rs.getString(CHKTBL_ALLOW_DELETE_IND))) {
					checkList.setAllowDeleteInd(true);
				}
				checkList.setDisableTaskInd(rs.getString(CHKTBL_DISABLE_TASK_IND));
				//map.put(new Long(collateralId), checkList);
			}
			rs.close();
			return checkList;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
			}
		}
	}
	
	
	
	public CheckListSearchResult getCAMCheckListByCategoryAndProfileID(String category,long collateralID) throws SearchDAOException {

		/*final String SELECT_CHECKLIST_BY_COLLATERAL_ID = "SELECT CMS_CHECKLIST.CHECKLIST_ID"
			+ " FROM CMS_CHECKLIST"
			+ " WHERE ";*/
		
		
		
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_LIMIT_PROFILE_ID);
		strBuf.append(" = ?");
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_CATEGORY_PREF);
		strBuf.append(" = ?");		
		if(!"".equals(category) && "LAD".equals(category)){
			strBuf.append(" AND ");
			strBuf.append(CHKTBL_IS_DISPLAY);
			strBuf.append(" = 'Y' ");
		}
		strBuf.append("order by(CMS_CHECKLIST.CHECKLIST_ID)desc ");
//		String sql = SELECT_CHECKLIST_BY_COLLATERAL_ID + strBuf.toString();
		String sql = SELECT_CHECKLIST + strBuf.toString();
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, collateralID);
			dbUtil.setString(2, category);
			ResultSet rs = dbUtil.executeQuery();
			/*long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			while (rs.next()) {
				checkListID = rs.getLong(CHKTBL_CHECKLISTID);
							
			}
			rs.close();
			return new Long(checkListID);*/
			HashMap map = new HashMap();
			CheckListSearchResult checkList = null;
			while (rs.next()) {
				//collateralID = rs.getLong(CHKTBL_COLLATERAL_ID);
				checkList = new CheckListSearchResult();
				checkList.setCheckListID(rs.getLong(CHKTBL_CHECKLISTID));
				checkList.setCheckListType(rs.getString(CHKTBL_CATEGORY));
				checkList.setCustomerType(rs.getString(CHKTBL_SUB_CATEGORY));
				checkList.setCheckListStatus(rs.getString(CHKTBL_STATUS));
				checkList.setApplicationType(rs.getString(CHKTBL_APPLICATION_TYPE));
				checkList.setTrxID(rs.getString(TRXTBL_TRANSACTION_ID));
				checkList.setTrxStatus(rs.getString("TRX_STATUS"));
				checkList.setTrxFromState(rs.getString(TRXTBL_FROM_STATE));
				IBookingLocation location = new OBBookingLocation(rs.getString(CHKTBL_DOC_LOC_CTRY), rs
						.getString(CHKTBL_DOC_LOC_ORG));
				checkList.setCheckListLocation(location);
				checkList.setCheckListOrganization(rs.getString(CHKTBL_DOC_LOC_ORG));
				if (ICMSConstant.TRUE_VALUE.equals(rs.getString(CHKTBL_ALLOW_DELETE_IND))) {
					checkList.setAllowDeleteInd(true);
				}
				checkList.setDisableTaskInd(rs.getString(CHKTBL_DISABLE_TASK_IND));
				//map.put(new Long(collateralId), checkList);
			}
			rs.close();
			return checkList;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
			}
		}
	}
	
	public CheckListSearchResult[] getCheckListByCategory(String category) throws SearchDAOException {

		/*final String SELECT_CHECKLIST_BY_COLLATERAL_ID = "SELECT CMS_CHECKLIST.CHECKLIST_ID"
			+ " FROM CMS_CHECKLIST"
			+ " WHERE ";*/
		
		
		
		StringBuffer strBuf = new StringBuffer();
		
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_CATEGORY_PREF);
		strBuf.append(" = ?");
//		String sql = SELECT_CHECKLIST_BY_COLLATERAL_ID + strBuf.toString();
		String sql = SELECT_CHECKLIST + strBuf.toString();
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			
			dbUtil.setString(1, category);
			ResultSet rs = dbUtil.executeQuery();
			/*long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			while (rs.next()) {
				checkListID = rs.getLong(CHKTBL_CHECKLISTID);
							
			}
			rs.close();
			return new Long(checkListID);*/
			HashMap map = new HashMap();
			ArrayList resultList = new ArrayList();
			CheckListSearchResult checkList=null;
			                                
			int i=0;
			while (rs.next()) {
				//collateralID = rs.getLong(CHKTBL_COLLATERAL_ID);
				checkList= new CheckListSearchResult();
				checkList.setCheckListID(rs.getLong(CHKTBL_CHECKLISTID));
				checkList.setCheckListType(rs.getString(CHKTBL_CATEGORY));
				checkList.setCustomerType(rs.getString(CHKTBL_SUB_CATEGORY));
				checkList.setCheckListStatus(rs.getString(CHKTBL_STATUS));
				checkList.setApplicationType(rs.getString(CHKTBL_APPLICATION_TYPE));
				checkList.setTrxID(rs.getString(TRXTBL_TRANSACTION_ID));
				checkList.setTrxStatus(rs.getString("TRX_STATUS"));
				checkList.setTrxFromState(rs.getString(TRXTBL_FROM_STATE));
				IBookingLocation location = new OBBookingLocation(rs.getString(CHKTBL_DOC_LOC_CTRY), rs
						.getString(CHKTBL_DOC_LOC_ORG));
				checkList.setCheckListLocation(location);
				checkList.setCheckListOrganization(rs.getString(CHKTBL_DOC_LOC_ORG));
				if (ICMSConstant.TRUE_VALUE.equals(rs.getString(CHKTBL_ALLOW_DELETE_IND))) {
					checkList.setAllowDeleteInd(true);
				}
				checkList.setDisableTaskInd(rs.getString(CHKTBL_DISABLE_TASK_IND));
				//map.put(new Long(collateralId), checkList[i]);
				resultList.add(checkList);
				i++;
			}
			rs.close();
			return (CheckListSearchResult[]) resultList.toArray(new CheckListSearchResult[resultList.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
			}
		}
	}
	
	public CheckListSearchResult getPariPassuCheckListByCategoryAndProfileID(String category,long collateralID) throws SearchDAOException {

		/*final String SELECT_CHECKLIST_BY_COLLATERAL_ID = "SELECT CMS_CHECKLIST.CHECKLIST_ID"
			+ " FROM CMS_CHECKLIST"
			+ " WHERE ";*/
		
		
		
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_LIMIT_PROFILE_ID);
		strBuf.append(" = ?");
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_CATEGORY_PREF);
		strBuf.append(" = ?");
//		String sql = SELECT_CHECKLIST_BY_COLLATERAL_ID + strBuf.toString();
		String sql = SELECT_CHECKLIST + strBuf.toString();
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, collateralID);
			dbUtil.setString(2, ICMSConstant.DOC_TYPE_PARIPASSU);
			ResultSet rs = dbUtil.executeQuery();
			/*long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			while (rs.next()) {
				checkListID = rs.getLong(CHKTBL_CHECKLISTID);
							
			}
			rs.close();
			return new Long(checkListID);*/
			HashMap map = new HashMap();
			CheckListSearchResult checkList = null;
			while (rs.next()) {
				//collateralID = rs.getLong(CHKTBL_COLLATERAL_ID);
				checkList = new CheckListSearchResult();
				checkList.setCheckListID(rs.getLong(CHKTBL_CHECKLISTID));
				checkList.setCheckListType(rs.getString(CHKTBL_CATEGORY));
				checkList.setCustomerType(rs.getString(CHKTBL_SUB_CATEGORY));
				checkList.setCheckListStatus(rs.getString(CHKTBL_STATUS));
				checkList.setApplicationType(rs.getString(CHKTBL_APPLICATION_TYPE));
				checkList.setTrxID(rs.getString(TRXTBL_TRANSACTION_ID));
				checkList.setTrxStatus(rs.getString("TRX_STATUS"));
				checkList.setTrxFromState(rs.getString(TRXTBL_FROM_STATE));
				IBookingLocation location = new OBBookingLocation(rs.getString(CHKTBL_DOC_LOC_CTRY), rs
						.getString(CHKTBL_DOC_LOC_ORG));
				checkList.setCheckListLocation(location);
				checkList.setCheckListOrganization(rs.getString(CHKTBL_DOC_LOC_ORG));
				if (ICMSConstant.TRUE_VALUE.equals(rs.getString(CHKTBL_ALLOW_DELETE_IND))) {
					checkList.setAllowDeleteInd(true);
				}
				checkList.setDisableTaskInd(rs.getString(CHKTBL_DISABLE_TASK_IND));
				//map.put(new Long(collateralId), checkList);
			}
			rs.close();
			return checkList;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
			}
		}
	}
	
	
	/**
	 * Get Hashtable with borrower ID/pledger ID and the status
	 * @param aLimitProfileID of long type
	 * @return HashMap - this the borrowerID/pledgerID as the key and the status
	 *         as the value
	 * @throws SearchDAOException on errors
	 */

	public HashMap getCCCheckListStatus(long aLimitProfileID, boolean isFullListInd) throws SearchDAOException {
		DefaultLogger.info(this, "IN method getCCCheckListStatus");

		StringBuffer strBuf = new StringBuffer();

		ArrayList params = new ArrayList();

		if (!isFullListInd) {
			strBuf.append(" AND ");
			strBuf.append(CHKTBL_STATUS_PREF);
			strBuf.append(" NOT ");
			final String[] checklistStatus = new String[] { ICMSConstant.STATE_DELETED, ICMSConstant.STATE_OBSOLETE };
			CommonUtil.buildSQLInList(checklistStatus, strBuf, params);

			/*
			 * String checklistValues =
			 * CommonUtil.arrayToDelimStr(checklistStatus);
			 * strBuf.append(CommonUtil.SQL_STRING_SET);
			 * params.add(checklistValues);
			 */
		}
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_LIMIT_PROFILE_ID_PREF);
		strBuf.append(" = ?");
		params.add(new Long(aLimitProfileID));
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_CATEGORY_PREF);
		strBuf.append(" = '");
		strBuf.append(ICMSConstant.DOC_TYPE_CC);
		strBuf.append("'");
		String sql = SELECT_CHECKLIST + strBuf.toString();

		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);
			ResultSet rs = dbUtil.executeQuery();
			HashMap map = new HashMap();
			CheckListSearchResult checkList = null;
			long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			String ccType = ICMSConstant.CHECKLIST_MAIN_BORROWER;
			while (rs.next()) {
				if (!isEmpty(rs.getString(CHKTBL_SUB_CATEGORY))) {
					ccType = rs.getString(CHKTBL_SUB_CATEGORY);
				}
				if (ICMSConstant.CHECKLIST_PLEDGER.equals(ccType)) {
					customerID = rs.getLong(CHKTBL_PLEDGER_ID);
				}
				else {
					customerID = rs.getLong(CHKTBL_BORROWER_ID);
				}
				checkList = new CheckListSearchResult();
				checkList.setCheckListID(rs.getLong(CHKTBL_CHECKLISTID));
				checkList.setCheckListType(rs.getString(CHKTBL_CATEGORY));
				checkList.setCheckListStatus(rs.getString(CHKTBL_STATUS));
				checkList.setDisableTaskInd(rs.getString(CHKTBL_DISABLE_TASK_IND));
				IBookingLocation location = new OBBookingLocation(rs.getString(CHKTBL_DOC_LOC_CTRY), rs
						.getString(CHKTBL_DOC_LOC_ORG));
				checkList.setCheckListLocation(location);
				checkList.setCheckListOrganization(rs.getString(CHKTBL_DOC_LOC_ORG));
				if (ICMSConstant.TRUE_VALUE.equals(rs.getString(CHKTBL_ALLOW_DELETE_IND))) {
					checkList.setAllowDeleteInd(true);
				}

				checkList.setTrxID(rs.getString(TRXTBL_TRANSACTION_ID));
				checkList.setTrxStatus(rs.getString("TRX_STATUS"));
				checkList.setTrxFromState(rs.getString(TRXTBL_FROM_STATE));
				map.put(ccType + customerID, checkList);
			}
			rs.close();
			return map;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
			}
		}
	}

	
	public HashMap getFacilityCheckListStatus(long aLimitProfileID, boolean isFullListInd) throws SearchDAOException {
		DefaultLogger.info(this, "IN method getCCCheckListStatus");

		StringBuffer strBuf = new StringBuffer();

		ArrayList params = new ArrayList();

		if (!isFullListInd) {
			strBuf.append(" AND ");
			strBuf.append(CHKTBL_STATUS_PREF);
			strBuf.append(" NOT ");
			final String[] checklistStatus = new String[] { ICMSConstant.STATE_DELETED, ICMSConstant.STATE_OBSOLETE };
			CommonUtil.buildSQLInList(checklistStatus, strBuf, params);

			/*
			 * String checklistValues =
			 * CommonUtil.arrayToDelimStr(checklistStatus);
			 * strBuf.append(CommonUtil.SQL_STRING_SET);
			 * params.add(checklistValues);
			 */
		}
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_LIMIT_PROFILE_ID_PREF);
		strBuf.append(" = ?");
		params.add(new Long(aLimitProfileID));
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_CATEGORY_PREF);
		strBuf.append(" = '");
		strBuf.append(ICMSConstant.DOC_TYPE_FACILITY);
		strBuf.append("'");
		String sql = SELECT_CHECKLIST + strBuf.toString();

		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);
			ResultSet rs = dbUtil.executeQuery();
			HashMap map = new HashMap();
			CheckListSearchResult checkList = null;
			long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			String ccType = ICMSConstant.CHECKLIST_MAIN_BORROWER;
			while (rs.next()) {
				if (!isEmpty(rs.getString(CHKTBL_SUB_CATEGORY))) {
					ccType = rs.getString(CHKTBL_SUB_CATEGORY);
				}
				if (ICMSConstant.CHECKLIST_PLEDGER.equals(ccType)) {
					customerID = rs.getLong(CHKTBL_PLEDGER_ID);
				}
				else {
					customerID = rs.getLong(CHKTBL_BORROWER_ID);
				}
				checkList = new CheckListSearchResult();
				checkList.setCheckListID(rs.getLong(CHKTBL_CHECKLISTID));
				checkList.setCheckListType(rs.getString(CHKTBL_CATEGORY));
				checkList.setCheckListStatus(rs.getString(CHKTBL_STATUS));
				checkList.setDisableTaskInd(rs.getString(CHKTBL_DISABLE_TASK_IND));
				IBookingLocation location = new OBBookingLocation(rs.getString(CHKTBL_DOC_LOC_CTRY), rs
						.getString(CHKTBL_DOC_LOC_ORG));
				checkList.setCheckListLocation(location);
				checkList.setCheckListOrganization(rs.getString(CHKTBL_DOC_LOC_ORG));
				if (ICMSConstant.TRUE_VALUE.equals(rs.getString(CHKTBL_ALLOW_DELETE_IND))) {
					checkList.setAllowDeleteInd(true);
				}

				checkList.setTrxID(rs.getString(TRXTBL_TRANSACTION_ID));
				checkList.setTrxStatus(rs.getString("TRX_STATUS"));
				checkList.setTrxFromState(rs.getString(TRXTBL_FROM_STATE));
				map.put(ccType + customerID, checkList);
			}
			rs.close();
			return map;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
			}
		}
	}

	/**
	 * Retrieves a summary of checklists belonging to this limit profile
	 * @param aLimitProfileID
	 * @param aDeletedInd
	 * @return CCCheckListSummary[]
	 * @throws SearchDAOException
	 */
	public CCCheckListSummary[] getCCCheckListList(long aLimitProfileID, boolean aDeletedInd) throws SearchDAOException {
		DefaultLogger.info(this, "IN method getCCCheckListList");
		StringBuffer strBuf = new StringBuffer();

		ArrayList params = new ArrayList();
		final String[] checkListStates = new String[] { ICMSConstant.STATE_DELETED, ICMSConstant.STATE_OBSOLETE };

		strBuf.append(" AND ");
		strBuf.append(CHKTBL_STATUS_PREF);
		if (aDeletedInd) {
			strBuf.append(" = ?");
			params.add(ICMSConstant.STATE_DELETED);
		}
		else {
			strBuf.append(" NOT ");
			CommonUtil.buildSQLInList(checkListStates, strBuf, params);

			/*
			 * String checkListValues =
			 * CommonUtil.arrayToDelimStr(checkListStates);
			 * strBuf.append(CommonUtil.SQL_STRING_SET);
			 * params.add(checkListValues);
			 */
		}
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_LIMIT_PROFILE_ID_PREF);
		strBuf.append(" = ?");
		params.add(new Long(aLimitProfileID));
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_CATEGORY_PREF);
		strBuf.append(" = '");
		strBuf.append(ICMSConstant.DOC_TYPE_CC);
		strBuf.append("'");

		String sql = SELECT_CHECKLIST + strBuf.toString();

		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			CCCheckListSummary summary = null;
			while (rs.next()) {
				summary = new CCCheckListSummary();
				summary.setCustCategory(rs.getString(CHKTBL_SUB_CATEGORY));
				if ((ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(summary.getCustCategory()))
						|| (ICMSConstant.CHECKLIST_CO_BORROWER.equals(summary.getCustCategory()))
						|| (ICMSConstant.CHECKLIST_NON_BORROWER.equals(summary.getCustCategory()))) {
					summary.setSubProfileID(rs.getLong(CHKTBL_BORROWER_ID));
				}
				else if (ICMSConstant.CHECKLIST_PLEDGER.equals(summary.getCustCategory())) {
					summary.setSubProfileID(rs.getLong(CHKTBL_PLEDGER_ID));
				}
				summary.setCheckListID(rs.getLong(CHKTBL_CHECKLISTID));
				summary.setCheckListStatus(rs.getString(CHKTBL_STATUS));
				summary.setDomicileCtry(rs.getString(CHKTBL_DOC_LOC_CTRY));
				summary.setOrgCode(rs.getString(CHKTBL_DOC_LOC_ORG));
				summary.setTrxStatus(rs.getString("TRX_STATUS"));
				if (ICMSConstant.TRUE_VALUE.equals(rs.getString(CHKTBL_ALLOW_DELETE_IND))) {
					summary.setAllowDeleteInd(true);
				}
				resultList.add(summary);
			}
			rs.close();
			return (CCCheckListSummary[]) resultList.toArray(new CCCheckListSummary[resultList.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCCCheckListList", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCCCheckListList", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCCCheckListList", ex);
			}
		}
	}

	/**
	 * Retrieves a summary of checklists for this non-borrower customer
	 * @param aCustomerID
	 * @param aDeletedInd
	 * @return CCCheckListSummary[]
	 * @throws SearchDAOException
	 */
	public CCCheckListSummary[] getCCCheckListListForNonBorrower(long aCustomerID, boolean aDeletedInd)
			throws SearchDAOException {
		DefaultLogger.info(this, "IN method getCCCheckListListForNonBorrower");

		StringBuffer strBuf = new StringBuffer();
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_STATUS_PREF);

		final String[] checkListStates = new String[] { ICMSConstant.STATE_DELETED, ICMSConstant.STATE_OBSOLETE };
		ArrayList params = new ArrayList();

		if (aDeletedInd) {
			strBuf.append(" = ?");
			params.add(ICMSConstant.STATE_DELETED);
		}
		else {
			strBuf.append(" NOT ");
			CommonUtil.buildSQLInList(checkListStates, strBuf, params);

			/*
			 * String checkListValues =
			 * CommonUtil.arrayToDelimStr(checkListStates);
			 * strBuf.append(CommonUtil.SQL_STRING_SET);
			 * params.add(checkListValues);
			 */
		}

		strBuf.append(" AND ");
		strBuf.append(CHKTBL_BORROWER_ID_PREF);
		strBuf.append(" = ?");
		params.add(new Long(aCustomerID));
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_CATEGORY_PREF);
		strBuf.append(" = '");
		strBuf.append(ICMSConstant.DOC_TYPE_CC);
		strBuf.append("'");
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_SUB_CATEGORY_PREF);
		strBuf.append(" = '");
		strBuf.append(ICMSConstant.CHECKLIST_NON_BORROWER);
		strBuf.append("'");

		String sql = SELECT_CHECKLIST + strBuf.toString();
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			CCCheckListSummary summary = null;
			while (rs.next()) {
				summary = new CCCheckListSummary();
				summary.setCustCategory(rs.getString(CHKTBL_SUB_CATEGORY));
				summary.setSubProfileID(rs.getLong(CHKTBL_BORROWER_ID));
				summary.setCheckListID(rs.getLong(CHKTBL_CHECKLISTID));
				summary.setCheckListStatus(rs.getString(CHKTBL_STATUS));
				if (ICMSConstant.TRUE_VALUE.equals(rs.getString(CHKTBL_ALLOW_DELETE_IND))) {
					summary.setAllowDeleteInd(true);
				}
				summary.setDomicileCtry(rs.getString(CHKTBL_DOC_LOC_CTRY));
				summary.setOrgCode(rs.getString(CHKTBL_DOC_LOC_ORG));
				resultList.add(summary);
			}
			rs.close();
			return (CCCheckListSummary[]) resultList.toArray(new CCCheckListSummary[resultList.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCCCheckListListForNonBorrower", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCCCheckListListForNonBorrower", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCCCheckListListForNonBorrower", ex);
			}
		}
	}

	/**
	 * Get Hashtable with customerID and the status
	 * @param aCustomerID of long type
	 * @return HashMap - this the customerID as the key and the status as the
	 *         value
	 * @throws SearchDAOException on errors
	 */
	public HashMap getCCCheckListStatusForNonBorrower(long aLimitProfileID, long aCustomerID) throws SearchDAOException {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_BORROWER_ID_PREF);
		strBuf.append(" = ?");
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_CATEGORY_PREF);
		strBuf.append(" = ?");
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_SUB_CATEGORY_PREF);
		strBuf.append(" = ?");

		if (aLimitProfileID != ICMSConstant.LONG_MIN_VALUE) {
			strBuf.append(" AND ");
			strBuf.append(CHKTBL_LIMIT_PROFILE_ID);
			strBuf.append(" = ?");
		}

		String sql = SELECT_CHECKLIST + strBuf.toString();
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, aCustomerID);
			dbUtil.setString(2, ICMSConstant.DOC_TYPE_CC);
			dbUtil.setString(3, ICMSConstant.CHECKLIST_NON_BORROWER);
			if (aLimitProfileID != ICMSConstant.LONG_MIN_VALUE) {
				dbUtil.setLong(4, aLimitProfileID);
			}
			ResultSet rs = dbUtil.executeQuery();
			HashMap map = new HashMap();
			CheckListSearchResult checkList = null;
			long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			while (rs.next()) {
				String ccType = rs.getString(CHKTBL_SUB_CATEGORY);
				customerID = rs.getLong(CHKTBL_BORROWER_ID);
				checkList = new CheckListSearchResult();
				checkList.setCheckListID(rs.getLong(CHKTBL_CHECKLISTID));
				checkList.setCheckListType(rs.getString(CHKTBL_CATEGORY));
				checkList.setCheckListStatus(rs.getString(CHKTBL_STATUS));
				checkList.setDisableTaskInd(rs.getString(CHKTBL_DISABLE_TASK_IND));
				if (ICMSConstant.TRUE_VALUE.equals(rs.getString(CHKTBL_ALLOW_DELETE_IND))) {
					checkList.setAllowDeleteInd(true);
				}
				IBookingLocation location = new OBBookingLocation(rs.getString(CHKTBL_DOC_LOC_CTRY), rs
						.getString(CHKTBL_DOC_LOC_ORG));
				checkList.setCheckListLocation(location);
				checkList.setCheckListOrganization(rs.getString(CHKTBL_DOC_LOC_ORG));
				checkList.setTrxID(rs.getString(TRXTBL_TRANSACTION_ID));
				checkList.setTrxStatus(rs.getString("TRX_STATUS"));
				checkList.setTrxFromState(rs.getString(TRXTBL_FROM_STATE));
				map.put(ccType + customerID, checkList);
			}
			rs.close();
			return map;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
			}
		}

	}

	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param anICheckListOwner of ICheckListOwner
	 * @param aStatusList of String[] type
	 * @return CheckListSearchResult[] - the list of checklist result
	 * @throws SearchDAOException
	 */
	public CheckListSearchResult[] getCheckList(ICheckListOwner anICheckListOwner, String[] aStatusList)
			throws SearchDAOException {
		DefaultLogger.info(this, "IN method getCheckList");

		ArrayList params = new ArrayList();

		String conditionPart = getStagingSearchString(anICheckListOwner, aStatusList, params);
		String sql = SELECT_STAGE_CHECKLIST;

		if (conditionPart != null) {
			sql += conditionPart;
		}

		// DefaultLogger.debug(this, ">>>>>>>>>>>> SQL in getCheckList = \n" +
		// sql);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);
			DefaultLogger.info(this, "getCheckList --> sql --> " + sql);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			CheckListSearchResult checkList = null;
			while (rs.next()) {
				checkList = new CheckListSearchResult();
				checkList.setLimitProfileID(rs.getLong(CHKTBL_LIMIT_PROFILE_ID));
				checkList.setCheckListID(rs.getLong(CHKTBL_CHECKLISTID));
				checkList.setCheckListType(rs.getString(CHKTBL_CATEGORY));
				checkList.setCheckListStatus(rs.getString(CHKTBL_STATUS));
				checkList.setApplicationType(rs.getString(CHKTBL_APPLICATION_TYPE));
				checkList.setTrxID(rs.getString(TRXTBL_TRANSACTION_ID));
				checkList.setTrxStatus(rs.getString("TRX_STATUS"));
				checkList.setTrxFromState(rs.getString(TRXTBL_FROM_STATE));
				if (ICMSConstant.TRUE_VALUE.equals(rs.getString(CHKTBL_ALLOW_DELETE_IND))) {
					checkList.setAllowDeleteInd(true);
				}
				IBookingLocation location = new OBBookingLocation(rs.getString(CHKTBL_DOC_LOC_CTRY), rs
						.getString(CHKTBL_DOC_LOC_ORG));
				checkList.setCheckListLocation(location);
				checkList.setCheckListOrganization(rs.getString(CHKTBL_DOC_LOC_ORG));
				resultList.add(checkList);
			}
			rs.close();
			return (CheckListSearchResult[]) resultList.toArray(new CheckListSearchResult[resultList.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckList", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckList", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckList", ex);
			}
		}
	}

	/**
	 * Get the checklist item for waiver/deferral generation
	 * @param aLimitProfileID of long type
	 * @param anItemStatus of String type
	 * @return HashMap - the checkListID and the list of checklist item (not the
	 *         full detail)
	 * @throws SearchDAOException
	 */
	public HashMap getCheckListItemListbyStatus(long aLimitProfileID, String anItemStatus) throws SearchDAOException {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(ITMTBL_STATUS_PREF);
		strBuf.append(" = ?");
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_LIMIT_PROFILE_ID_PREF);
		strBuf.append(" = ?");

		String sql = new StringBuffer().append(SELECT_CHECKLIST_ITEM).append(" AND ").append(strBuf).toString();
		try {
			dbUtil = new DBUtil();
            dbUtil.setSQL(sql);
			dbUtil.setString(1, anItemStatus);
			dbUtil.setLong(2, aLimitProfileID);
			// DefaultLogger.debug(this, sql);
			//dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			HashMap map = new HashMap();
			ICheckListItem checkListItem = null;
			IItem item = null;
			int ctr = 0;
			while (rs.next()) {
				checkListItem = new OBCheckListItem();
				checkListItem.setCheckListItemID(rs.getLong(ITMTBL_DOC_ITEM_NO));
				checkListItem.setCheckListItemRef(rs.getLong(ITMTBL_DOC_ITEM_REF));
				item = new OBItem();
				item.setItemCode(rs.getString(ITMTBL_DOCUMENT_CODE));
				item.setItemDesc(rs.getString(ITMTBL_DOC_DESCRIPTION));
				checkListItem.setItem(item);
				checkListItem.setItemStatus(rs.getString(ITMTBL_STATUS));
				checkListItem.setDeferExpiryDate(rs.getDate(ITMTBL_DEFER_EXPIRY_DATE));
				long checkListID = rs.getLong(ITMTBL_CHECKLIST_ID);
				String key = String.valueOf(ctr) + "_" + String.valueOf(checkListID);
				// DefaultLogger.debug(this, "Key:" + key);
				map.put(key, checkListItem);
				ctr++;
			}
			rs.close();
			return map;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListbyStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListItemListbyStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckListItemListbyStatus", ex);
			}
		}
	}

    /**
     * Get the checklist item by checklist category
     *
     * @param aLimitProfileID of long type
     * @param aCheckListCategory of String type
     * @return HashMap - the checkListID and the list of checklist item (not the full detail)
     * @throws SearchDAOException
     */
    public HashMap getCheckListItemListbyCategory(long aLimitProfileID, String aCheckListCategory) throws SearchDAOException {

        StringBuffer strBuf = new StringBuffer();
        strBuf.append(SELECT_CHECKLIST_ITEM);
        strBuf.append(" AND ");
        strBuf.append(CHKTBL_CATEGORY_PREF);
        strBuf.append(" = ?");
        strBuf.append(" AND ");
        strBuf.append(CHKTBL_LIMIT_PROFILE_ID_PREF);
        strBuf.append(" = ?");

        String sql = strBuf.toString();

        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(sql);
            dbUtil.setString(1, aCheckListCategory);
            dbUtil.setLong(2, aLimitProfileID);

            ResultSet rs = dbUtil.executeQuery();
            HashMap map = new HashMap();
            ICheckListItem checkListItem = null;
            IItem item = null;
            int ctr = 0;
            while (rs.next()) {
                checkListItem = new OBCheckListItem();
                checkListItem.setCheckListItemID(rs.getLong(ITMTBL_DOC_ITEM_NO));
                checkListItem.setCheckListItemRef(rs.getLong(ITMTBL_DOC_ITEM_REF));
                item = new OBItem();
                item.setItemCode(rs.getString(ITMTBL_DOCUMENT_CODE));
                item.setItemDesc(rs.getString(ITMTBL_DOC_DESCRIPTION));
                checkListItem.setItem(item);
                checkListItem.setItemStatus(rs.getString(ITMTBL_STATUS));
                checkListItem.setDeferExpiryDate(rs.getDate(ITMTBL_DEFER_EXPIRY_DATE));
                long checkListID = rs.getLong(ITMTBL_CHECKLIST_ID);
                String key = String.valueOf(ctr) + "_" + String.valueOf(checkListID);
                map.put(key, checkListItem);
                ctr++;
            }
            rs.close();
            return map;
        }
        catch (SQLException ex) {
            throw new SearchDAOException("SQLException in getCheckListItemListbyCategory", ex);
        }
        catch (Exception ex) {
            throw new SearchDAOException("Exception in getCheckListItemListbyCategory", ex);
        }
        finally {
            try {
                dbUtil.close();
            }
            catch (SQLException ex) {
                throw new SearchDAOException("SQLException in getCheckListItemListbyCategory", ex);
			}
		}
	}

	/**
	 * Get the checklist item for waiver/deferral generation for non borrower
	 * @param aCustomerID of long type
	 * @param anItemStatus of String type
	 * @return HashMap - the checkListID and the list of checklist item (not the
	 *         full detail)
	 * @throws SearchDAOException
	 */
	public HashMap getCheckListItemListbyStatusForNonBorrower(long aCustomerID, String anItemStatus)
			throws SearchDAOException {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(ITMTBL_STATUS_PREF);
		strBuf.append(" = ?");
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_SUB_CATEGORY_PREF);
		strBuf.append(" = ?");
		strBuf.append("' AND ");
		strBuf.append(CHKTBL_BORROWER_ID_PREF);
		strBuf.append(" = ?");

		String sql = new StringBuffer().append(SELECT_CHECKLIST_ITEM).append(" AND ").append(strBuf).toString();
		try {
			dbUtil = new DBUtil();
			dbUtil.setString(1, anItemStatus);
			dbUtil.setString(2, ICMSConstant.CHECKLIST_NON_BORROWER);
			dbUtil.setLong(3, aCustomerID);
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			HashMap map = new HashMap();
			ICheckListItem checkListItem = null;
			IItem item = null;
			int ctr = 0;
			while (rs.next()) {
				checkListItem = new OBCheckListItem();
				checkListItem.setCheckListItemID(rs.getLong(ITMTBL_DOC_ITEM_NO));
				item = new OBItem();
				item.setItemCode(rs.getString(ITMTBL_DOCUMENT_CODE));
				item.setItemDesc(rs.getString(ITMTBL_DOC_DESCRIPTION));
				checkListItem.setItem(item);
				checkListItem.setItemStatus(rs.getString(ITMTBL_STATUS));
				long checkListID = rs.getLong(ITMTBL_CHECKLIST_ID);
				String key = String.valueOf(ctr) + "_" + String.valueOf(checkListID);
				DefaultLogger.debug(this, "Key:" + key);
				map.put(key, checkListItem);
				ctr++;
			}
			rs.close();
			return map;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListbyStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListItemListbyStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckListItemListbyStatus", ex);
			}
		}
	}

	/**
	 * Get the list of collateral Checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param aStatusList of String[] type
	 * @return ICollateralAuditItem[] - the list of checklist items that
	 *         requires auditing
	 * @throws SearchDAOException on errors
	 */
	public ICheckListAudit[] getCheckListAuditList(long aLimitProfileID, String[] aStatusList)
			throws SearchDAOException {
		DefaultLogger.info(this, "IN method getCheckListAuditList");

		ArrayList params = new ArrayList();

		String conditionPart = getCheckListAuditListCond(aLimitProfileID, params, aStatusList);
		String sql = SELECT_AUDIT_ITEM;

		if (conditionPart != null) {
			sql = sql + " AND " + conditionPart;
		}

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			CommonUtil.setSQLParams(params, dbUtil);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			ArrayList itemList = new ArrayList();
			ICheckListAudit audit = null;
			IAuditItem item = null;
			long prevCheckListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			long curCheckListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			String category = null;
			while (rs.next()) {
				curCheckListID = rs.getLong(CHKTBL_CHECKLISTID);
				if (prevCheckListID != curCheckListID) {
					if (audit != null) {
						if (itemList.size() > 0) {
							audit.setAuditItemList((IAuditItem[]) itemList.toArray(new IAuditItem[0]));
							itemList = new ArrayList();
							resultList.add(audit);
						}
					}
					audit = new OBCheckListAudit();
					audit.setCustomerCategory(rs.getString(CHKTBL_SUB_CATEGORY));
					audit.setCheckListID(curCheckListID);
					prevCheckListID = curCheckListID;
					category = rs.getString(CHKTBL_CATEGORY);
					if ((ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(category))
							|| (ICMSConstant.CHECKLIST_CO_BORROWER.equals(category))) {
						audit.setCustomerID(rs.getLong(CHKTBL_BORROWER_ID));
					}
					else {
						if (ICMSConstant.CHECKLIST_PLEDGER.equals(category)) {
							audit.setCustomerID(rs.getLong(CHKTBL_PLEDGER_ID));
						}
						else {
							audit.setCollateralID(rs.getLong(CHKTBL_COLLATERAL_ID));
						}
					}
				}
				item = new OBAuditItem();
				item.setCheckListItemID(rs.getLong(ITMTBL_DOC_ITEM_NO));
				item.setItemCode(rs.getString(ITMTBL_DOCUMENT_CODE));
				item.setDescription(rs.getString(ITMTBL_DOC_DESCRIPTION));
				item.setDocumentDate(rs.getDate(ITMTBL_DOC_DATE));
				String inVaultInd = rs.getString(ITMTBL_IN_VAULT);
				if (ICMSConstant.TRUE_VALUE.equals(inVaultInd)) {
					item.setIsInVaultInd(true);
				}
				else {
					item.setIsInVaultInd(false);
				}
				itemList.add(item);
			}
			if (audit != null) {
				if (itemList.size() > 0) {
					audit.setAuditItemList((IAuditItem[]) itemList.toArray(new IAuditItem[0]));
					// itemList = new ArrayList();
					resultList.add(audit);
				}
			}

			rs.close();
			return (ICheckListAudit[]) resultList.toArray(new ICheckListAudit[resultList.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListItemListForAudit", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
			}
		}
	}

	/**
	 * Constructs the SQL string based on the search criteria Populates the
	 * parameter list for DBUtil to perform JDBC variables binding
	 * @param aLimitProfileID
	 * @param params
	 * @param aStatusList
	 * @return String
	 */
	private String getCheckListAuditListCond(long aLimitProfileID, ArrayList params, String[] aStatusList) {
		StringBuffer sb = new StringBuffer();
		if (aLimitProfileID != ICMSConstant.LONG_INVALID_VALUE) {
			sb.append(CHKTBL_LIMIT_PROFILE_ID_PREF);
			sb.append(" = ?");
			params.add(new Long(aLimitProfileID));
		}

		if (!CommonUtil.isEmptyArray(aStatusList)) {
			sb.append(" AND ");
			sb.append(ITMTBL_STATUS_PREF);
			CommonUtil.buildSQLInList(aStatusList, sb, params);

		}
		/*
		 * Oracle 9i JDBC if ((aStatusList != null) && (aStatusList.length > 0))
		 * { sb.append(" AND "); sb.append(ITMTBL_STATUS_PREF); String
		 * statusValues = CommonUtil.arrayToDelimStr(aStatusList);
		 * sb.append(CommonUtil.SQL_STRING_SET); params.add(statusValues); }
		 */
		sb.append(" " + ORDER_BY_ITEM_DESC);
		return sb.toString();
	}

	/**
	 * Get the list of collateral Checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param aStatusList of String[] type
	 * @return ICollateralAuditItem[] - the list of checklist items that
	 *         requires auditing
	 * @throws SearchDAOException on errors
	 */
	public IAuditItem[] getCheckListItemListForAudit(long aLimitProfileID, String[] aStatusList)
			throws SearchDAOException {
		DefaultLogger.info(this, "IN method getCheckListItemListForAudit");

		ArrayList params = new ArrayList();

		String conditionPart = getCheckListItemListForAuditCond(aLimitProfileID, params, aStatusList);

		String sql = SELECT_AUDIT_ITEM;

		if (conditionPart != null) {
			sql += conditionPart;
		}

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			IAuditItem item = null;
			while (rs.next()) {
				item = new OBAuditItem();
				item.setCheckListItemID(rs.getLong(ITMTBL_DOC_ITEM_NO));
				item.setItemCode(rs.getString(ITMTBL_DOCUMENT_CODE));
				item.setDescription(rs.getString(ITMTBL_DOC_DESCRIPTION));
				item.setDocumentDate(rs.getDate(ITMTBL_DOC_DATE));
				String inVaultInd = rs.getString(ITMTBL_IN_VAULT);
				if (ICMSConstant.TRUE_VALUE.equals(inVaultInd)) {
					item.setIsInVaultInd(true);
				}
				else {
					item.setIsInVaultInd(false);
				}
				resultList.add(item);
			}
			rs.close();
			return (IAuditItem[]) resultList.toArray(new IAuditItem[resultList.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListItemListForAudit", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
			}
		}
	}

	/**
	 * Constructs the SQL string based on the search criteria Populates the
	 * parameter list for DBUtil to perform JDBC variables binding
	 * @param aLimitProfileID
	 * @param params
	 * @param aStatusList
	 * @return String
	 */
	private String getCheckListItemListForAuditCond(long aLimitProfileID, ArrayList params, String[] aStatusList) {

		DefaultLogger.info(this, "IN method getCheckListItemListForAuditCond");

		StringBuffer strBuffer = new StringBuffer();
		if (aLimitProfileID != ICMSConstant.LONG_INVALID_VALUE) {
			strBuffer.append(" AND ");
			strBuffer.append(CHKTBL_LIMIT_PROFILE_ID_PREF);
			strBuffer.append(" = ?");
			params.add(new Long(aLimitProfileID));
		}

		if (!CommonUtil.isEmptyArray(aStatusList)) {
			strBuffer.append(" AND ");
			strBuffer.append(ITMTBL_STATUS_PREF);
			CommonUtil.buildSQLInList(aStatusList, strBuffer, params);

		}

		/*
		 * Oracle 9i JDBC if ((aStatusList != null) && (aStatusList.length > 0))
		 * { strBuffer.append(" AND "); strBuffer.append(ITMTBL_STATUS_PREF);
		 * String statusValues = CommonUtil.arrayToDelimStr(aStatusList);
		 * strBuffer.append(CommonUtil.SQL_STRING_SET);
		 * params.add(statusValues); }
		 */
		strBuffer.append(" " + ORDER_BY_ITEM_DESC);
		return strBuffer.toString();
	}

	/**
	 * Get the list of Checklist items that requires auditing for non borrower
	 * @param aCustomerID of long type
	 * @param aStatusList of String[] type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 */
	public ICheckListAudit[] getCheckListAuditListForNonBorrower(long aCustomerID, String[] aStatusList)
			throws SearchDAOException {
		// DefaultLogger.info(this,
		// "IN method getCheckListAuditListForNonBorrower");

		ArrayList params = new ArrayList();

		String conditionPart = getCheckListAuditForNonBorrowerCond(aCustomerID, params, aStatusList);

		String sql = SELECT_AUDIT_ITEM;
		if (conditionPart != null) {
			sql += conditionPart;
		}

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			ArrayList itemList = new ArrayList();
			ICheckListAudit audit = null;
			IAuditItem item = null;
			long prevCheckListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			long curCheckListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			// String category = null;
			while (rs.next()) {
				curCheckListID = rs.getLong(CHKTBL_CHECKLISTID);
				if (prevCheckListID != curCheckListID) {
					if (audit != null) {
						if (itemList.size() > 0) {
							audit.setAuditItemList((IAuditItem[]) itemList.toArray(new IAuditItem[0]));
							itemList = new ArrayList();
							resultList.add(audit);
						}
					}
					audit = new OBCheckListAudit();
					audit.setCustomerCategory(rs.getString(CHKTBL_SUB_CATEGORY));
					audit.setCheckListID(curCheckListID);
					prevCheckListID = curCheckListID;
					// category = rs.getString(CHKTBL_CATEGORY);
					audit.setCustomerID(rs.getLong(CHKTBL_BORROWER_ID));
				}
				item = new OBAuditItem();
				item.setCheckListItemID(rs.getLong(ITMTBL_DOC_ITEM_NO));
				item.setItemCode(rs.getString(ITMTBL_DOCUMENT_CODE));
				item.setDescription(rs.getString(ITMTBL_DOC_DESCRIPTION));
				item.setDocumentDate(rs.getDate(ITMTBL_DOC_DATE));
				String inVaultInd = rs.getString(ITMTBL_IN_VAULT);
				if (ICMSConstant.TRUE_VALUE.equals(inVaultInd)) {
					item.setIsInVaultInd(true);
				}
				else {
					item.setIsInVaultInd(false);
				}
				itemList.add(item);
			}
			if (audit != null) {
				if (itemList.size() > 0) {
					audit.setAuditItemList((IAuditItem[]) itemList.toArray(new IAuditItem[0]));
					// itemList = new ArrayList();
					resultList.add(audit);
				}
			}
			rs.close();
			return (ICheckListAudit[]) resultList.toArray(new ICheckListAudit[resultList.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListItemListForAudit", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
			}
		}
	}

	/**
	 * Constructs the SQL string based on the search criteria Populates the
	 * parameter list for DBUtil to perform JDBC variables binding
	 * @param aCustomerID
	 * @param params
	 * @param aStatusList
	 * @return String
	 */
	private String getCheckListAuditForNonBorrowerCond(long aCustomerID, ArrayList params, String[] aStatusList) {

		// DefaultLogger.info(this,
		// "IN method getCheckListAuditForNonBorrowerCond");

		StringBuffer strBuffer = new StringBuffer();

		strBuffer.append(" AND ");
		strBuffer.append(CHKTBL_SUB_CATEGORY_PREF);
		strBuffer.append(" = '");
		strBuffer.append(ICMSConstant.CHECKLIST_NON_BORROWER);
		strBuffer.append("'");
		if (aCustomerID != ICMSConstant.LONG_INVALID_VALUE) {
			strBuffer.append(" AND ");
			strBuffer.append(CHKTBL_BORROWER_ID_PREF);
			strBuffer.append(" = ?");
			params.add(new Long(aCustomerID));

		}

		if (!CommonUtil.isEmptyArray(aStatusList)) {

			strBuffer.append(" AND ");
			strBuffer.append(ITMTBL_STATUS_PREF);
			CommonUtil.buildSQLInList(aStatusList, strBuffer, params);

		}

		/*
		 * Oracle 9i JDBC if ((aStatusList != null) && (aStatusList.length > 0))
		 * { strBuffer.append(" AND "); strBuffer.append(ITMTBL_STATUS_PREF);
		 * String statusValues = CommonUtil.arrayToDelimStr(aStatusList);
		 * strBuffer.append(CommonUtil.SQL_STRING_SET);
		 * params.add(statusValues); }
		 */
		strBuffer.append(" " + ORDER_BY_ITEM_DESC);
		return strBuffer.toString();
	}

	/**
	 * Get the list of collateral Checklist items that requires auditing for non
	 * borrower
	 * @param aCustomerID of long type
	 * @param aStatusList of String[] type
	 * @return ICollateralAuditItem[] - the list of checklist items that
	 *         requires auditing
	 * @throws SearchDAOException on errors
	 */
	public IAuditItem[] getCheckListItemListForAuditForNonBorrower(long aCustomerID, String[] aStatusList)
			throws SearchDAOException {
		DefaultLogger.info(this, "IN method getCheckListItemListForAuditForNonBorrower");

		ArrayList params = new ArrayList();

		String conditionPart = getCheckListItemListForAuditForNonBorrowerCond(aCustomerID, params, aStatusList);
		String sql = SELECT_AUDIT_ITEM;

		if (conditionPart != null) {
			sql = sql + " AND " + conditionPart;
		}

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			IAuditItem item = null;
			while (rs.next()) {
				item = new OBAuditItem();
				item.setCheckListItemID(rs.getLong(ITMTBL_DOC_ITEM_NO));
				item.setItemCode(rs.getString(ITMTBL_DOCUMENT_CODE));
				item.setDescription(rs.getString(ITMTBL_DOC_DESCRIPTION));
				item.setDocumentDate(rs.getDate(ITMTBL_DOC_DATE));
				String inVaultInd = rs.getString(ITMTBL_IN_VAULT);
				if (ICMSConstant.TRUE_VALUE.equals(inVaultInd)) {
					item.setIsInVaultInd(true);
				}
				else {
					item.setIsInVaultInd(false);
				}
				resultList.add(item);
			}
			rs.close();
			return (IAuditItem[]) resultList.toArray(new IAuditItem[resultList.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListForAuditForNonBorrower", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListItemListForAuditForNonBorrower", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckListItemListForAuditForNonBorrower", ex);
			}
		}
	}

	/**
	 * Constructs the SQL string based on the search criteria Populates the
	 * parameter list for DBUtil to perform JDBC variables binding
	 * @param aCustomerID
	 * @param params
	 * @param aStatusList
	 * @return String
	 */
	private String getCheckListItemListForAuditForNonBorrowerCond(long aCustomerID, ArrayList params,
			String[] aStatusList) {

		// DefaultLogger.info(this,
		// "IN method getCheckListItemListForAuditForNonBorrowerCond");

		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(CHKTBL_SUB_CATEGORY_PREF);
		strBuffer.append(" = '");
		strBuffer.append(ICMSConstant.CHECKLIST_NON_BORROWER);
		strBuffer.append("'");
		if (aCustomerID != ICMSConstant.LONG_INVALID_VALUE) {
			strBuffer.append(" AND ");
			strBuffer.append(CHKTBL_BORROWER_ID_PREF);
			strBuffer.append(" = ?");
			params.add(new Long(aCustomerID));
		}

		if (!CommonUtil.isEmptyArray(aStatusList)) {
			strBuffer.append(" AND ");
			strBuffer.append(ITMTBL_STATUS_PREF);
			CommonUtil.buildSQLInList(aStatusList, strBuffer, params);

		}

		/*
		 * Oracle 9i JDBC if ((aStatusList != null) && (aStatusList.length > 0))
		 * { strBuffer.append(" AND "); strBuffer.append(ITMTBL_STATUS_PREF);
		 * String statusValues = CommonUtil.arrayToDelimStr(aStatusList);
		 * strBuffer.append(CommonUtil.SQL_STRING_SET);
		 * params.add(statusValues); }
		 */

		strBuffer.append(" " + ORDER_BY_ITEM_DESC);
		return strBuffer.toString();

	}

	/**
	 * Get a map of document categories, one entry for each checklist type :
	 * main borrower, co-borrower, pledgor, security.<br>
	 * 
	 * Each entry consists of a list of String arrays sorted as follows :<br>
	 * main borrower - sorted by customer legal name<br>
	 * co-borrower - sorted by customer legal name<br>
	 * pledgor - sorted by customer legal name<br>
	 * security - sorted by security type name, security sub type name, then sci
	 * security detail id<br>
	 * 
	 * Data for checklist of main borrower type<br>
	 * mainBorrowerCat[0] = checklistID<br>
	 * mainBorrowerCat[1] = customer legal name<br>
	 * mainBorrowerCat[2] = cms customer id<br>
	 * mainBorrowerCat[3] = doc originating country<br>
	 * <br>
	 * 
	 * Data for checklist of co-borrower type<br>
	 * coBorrowerCat[0] = checklistID<br>
	 * coBorrowerCat[1] = customer legal name<br>
	 * coBorrowerCat[2] = customer legal ID<br>
	 * coBorrowerCat[3] = cms customer id<br>
	 * coBorrowerCat[4] = doc originating country<br>
	 * <br>
	 * 
	 * Data for checklist of pledgor type<br>
	 * pledgorCat[0] = checklistID<br>
	 * pledgorCat[1] = pledgor legal name<br>
	 * pledgorCat[2] = pledgor legal ID<br>
	 * pledgorCat[3] = sci pledgor id <br>
	 * pledgorCat[4] = cms pledgor id <br>
	 * pledgorCat[5] = doc originating country<br>
	 * <br>
	 * 
	 * Data for checklist of security type<br>
	 * securityCat[0] = checklistID<br>
	 * securityCat[1] = sci security detail id<br>
	 * securityCat[2] = security type name<br>
	 * securityCat[3] = security subtype name<br>
	 * securityCat[4] = cms collateral id<br>
	 * securityCat[5] = doc originating country<br>
	 * 
	 * @param aLimitProfileID - long
	 * @return HashMap
	 * @throws SearchDAOException
	 */
	// to supercede getOldDocumentCategories
	public HashMap getDocumentCategories(long aLimitProfileID) throws SearchDAOException {
		if (isInvalid(aLimitProfileID)) {
			throw new SearchDAOException("Invalid limit profile ID : " + aLimitProfileID);
		}

		DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<<< limit profile ID: " + aLimitProfileID);

		HashMap categoryMap = new HashMap(4);
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			// consolidated to single db call
			dbUtil.setSQL(SELECT_DOC_CAT_EXCL_NON_BORROWER);
			dbUtil.setLong(1, aLimitProfileID);
			dbUtil.setLong(2, aLimitProfileID);
			dbUtil.setLong(3, aLimitProfileID);
			dbUtil.setLong(4, aLimitProfileID);
			dbUtil.setLong(5, aLimitProfileID);
			rs = dbUtil.executeQuery();
			categoryMap = processDocHeldCategoriesResultSet(rs);
		}
		catch (Exception ex) {
			throw new SearchDAOException("getDocumentCategories : Exception", ex);
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("getDocumentCategories : SQLException - unable to close db resource", ex);
			}
		}
		return categoryMap;
	}

	/**
	 * Helper method to process given resultset to yield a map with entries for
	 * each type :<br>
	 * main borrower, co-borrower, pledgor, security.
	 * 
	 * @param rs - ResultSet HashMap - Map
	 */
	private HashMap processDocHeldCategoriesResultSet(ResultSet rs) throws Exception {
		HashMap categoryMap = new HashMap(6);

		Object result = null;
		String checklistID = null;
		String category = null;
		String subCategory = null;
		String docOriginatingCountry = null;
		String borrowerID = null;
		String borrowerName = null;
		String borrowerLEID = null;
		String sciPledgorID = null;
		String pledgorID = null;
		String pledgorName = null;
		String pledgorLEID = null;
		String collateralID = null;
		String sciSecID = null;
		String secTypeName = null;
		String secSubTypeName = null;
		ArrayList mainBorrowerList = new ArrayList();
		ArrayList coBorrowerList = new ArrayList();
		ArrayList jointBorrowerList = new ArrayList();
		ArrayList pledgorList = new ArrayList();
		ArrayList nonBorrowerList = new ArrayList();
		ArrayList securitiesList4MB = new ArrayList();
		ArrayList securitiesList4CB = new ArrayList();

		while (rs.next()) {
			category = rs.getString("category");
			subCategory = rs.getString("sub_category");
			if (category != null) {
				//checklistID = formatString(rs.getString("checklist_id"));
                checklistID = formatString(String.valueOf(rs.getLong("checklist_id")));
				docOriginatingCountry = rs.getString("doc_orig_country");

				if (checklistID.length() > 0) {
					// security checklist
					if (ICMSConstant.DOC_TYPE_SECURITY.equals(category)) {

						//collateralID = formatString(rs.getString("collateral_id"));
                        collateralID = formatString(String.valueOf(rs.getLong("collateral_id")));
						sciSecID = formatString(rs.getString("sci_security_id"));
						secTypeName = formatString(rs.getString("security_type_name"));
						secSubTypeName = formatString(rs.getString("security_sub_type_name"));
						result = new String[] { checklistID, sciSecID, secTypeName, secSubTypeName, collateralID,
								docOriginatingCountry, subCategory };
						if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(subCategory)) {
							securitiesList4MB.add(result);
						}
						else {
							securitiesList4CB.add(result);
						}
					}
					else if (subCategory != null) {
						if (ICMSConstant.CHECKLIST_PLEDGER.equals(subCategory)) { // pledgor
							//sciPledgorID = formatString(rs.getString("pledgor_id"));
                            sciPledgorID = formatString(String.valueOf(rs.getLong("pledgor_id")));
							pledgorName = formatString(rs.getString("pledgor_name"));
							pledgorLEID = formatString(rs.getString("pledgor_le_id"));
							//pledgorID = formatString(rs.getString("cms_pledgor_id"));
                            pledgorID = formatString(String.valueOf(rs.getLong("cms_pledgor_id")));
							result = new String[] { checklistID, pledgorName, pledgorLEID, sciPledgorID, pledgorID,
									docOriginatingCountry };
							pledgorList.add(result);
						}
						else {
							//borrowerID = formatString(rs.getString("borrower_id"));
                            borrowerID = formatString(String.valueOf(rs.getLong("borrower_id")));
							borrowerName = formatString(rs.getString("borrower_name"));
							if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(subCategory)) { // main
								// borrower
								result = new String[] { checklistID, borrowerName, borrowerID, docOriginatingCountry };
								mainBorrowerList.add(result);
							}
							else if (ICMSConstant.CHECKLIST_CO_BORROWER.equals(subCategory)) { // co
								// -
								// borrower
								borrowerLEID = formatString(rs.getString("borrower_le_id"));
								result = new String[] { checklistID, borrowerName, borrowerLEID, borrowerID,
										docOriginatingCountry };
								coBorrowerList.add(result);
							}
							else if (ICMSConstant.CHECKLIST_JOINT_BORROWER.equals(subCategory)) { // joint
								// borrower
								//borrowerID = formatString(rs.getString("borrower_id"));
                                borrowerID = formatString(String.valueOf(rs.getLong("borrower_id")));
								borrowerName = formatString(rs.getString("borrower_name"));
								result = new String[] { checklistID, borrowerName, borrowerLEID, borrowerID,
										docOriginatingCountry };
								jointBorrowerList.add(result);
							}
							else {
								result = new String[] { checklistID, borrowerName };
								nonBorrowerList.add(result);
								DefaultLogger.debug(this,
										"###### <processDocHeldCategoriesResultSet> nonBorrowerList has something");
							}
						}
					}
				}
			}
		}

		if (mainBorrowerList.size() > 0) {
			// DefaultLogger.debug(this, "mainBorrowerList = " +
			// mainBorrowerList.size());
			categoryMap.put(ICMSConstant.CHECKLIST_MAIN_BORROWER, mainBorrowerList);
		}
		if (coBorrowerList.size() > 0) {
			// DefaultLogger.debug(this, "coBorrowerList = " +
			// coBorrowerList.size());
			categoryMap.put(ICMSConstant.CHECKLIST_CO_BORROWER, coBorrowerList);
		}
		if (jointBorrowerList.size() > 0) {
			// DefaultLogger.debug(this, "jointBorrowerList = " +
			// jointBorrowerList.size());
			categoryMap.put(ICMSConstant.CHECKLIST_JOINT_BORROWER, jointBorrowerList);
		}
		if (pledgorList.size() > 0) {
			// DefaultLogger.debug(this, "pledgorList = " + pledgorList.size());
			categoryMap.put(ICMSConstant.CHECKLIST_PLEDGER, pledgorList);
		}
		if (nonBorrowerList.size() > 0) {
			categoryMap.put(ICMSConstant.CHECKLIST_NON_BORROWER, nonBorrowerList);
		}
		if (securitiesList4MB.size() > 0) {
			// DefaultLogger.debug(this, "securitiesList = " +
			// securitiesList.size());
			categoryMap.put(ICMSConstant.CHECKLIST_SECURITY_MB, securitiesList4MB);
		}
		if (securitiesList4CB.size() > 0) {
			// DefaultLogger.debug(this, "securitiesList = " +
			// securitiesList.size());
			categoryMap.put(ICMSConstant.CHECKLIST_SECURITY_CB, securitiesList4CB);
		}
		return categoryMap;
	}

	/**
	 * Helper method to format string. If param is null, en empty string will be
	 * returned.
	 * @param str - String to be formatted.
	 */
	private String formatString(String str) {
		return (str == null) ? "" : str;
	}

	/**
	 * Get the list of document categories for this non-borrower.
	 * @param aCustomerID of long type
	 * @return HashMap - the list of document categories
	 * @throws SearchDAOException on errors
	 */
	public HashMap getDocumentCategoriesForNonBorrower(long aCustomerID, long aLimitProfileID)
			throws SearchDAOException {
		if (isInvalid(aCustomerID)) {
			throw new SearchDAOException("Invalid customer ID : " + aCustomerID);
		}

		HashMap categoryMap = new HashMap(5);
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SELECT_DOC_CAT_NON_BORROWER);

			dbUtil.setLong(1, aCustomerID);
			dbUtil.setLong(2, aLimitProfileID);
			dbUtil.setLong(3, aLimitProfileID);
			dbUtil.setLong(4, aLimitProfileID);
			dbUtil.setLong(5, aLimitProfileID);

			rs = dbUtil.executeQuery();

			categoryMap = processDocHeldCategoriesResultSet(rs);
		}
		catch (Exception ex) {
			throw new SearchDAOException("getDocumentCategoriesForNonBorrower: Exception", ex);
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException(
						"getDocumentCategoriesForNonBorrower : SQLException - unable to close db resource", ex);
			}
		}
		return categoryMap;
	}

	/**
	 * Get a map of list of document held given a set of search criteria.<br>
	 * Key for retreiving the entries : <br>
	 * ICMSConstant.CHECKLIST_MAIN_BORROWER - Value = a list of OBDocumentHeld
	 * belonging to the main borrower<br>
	 * ICMSConstant.CHECKLIST_CO_BORROWER - Value = a list of OBDocumentHeld
	 * belonging to the co borrower<br>
	 * ICMSConstant.CHECKLIST_NON_BORROWER - Value = a list of OBDocumentHeld
	 * belonging to the non borrower<br>
	 * ICMSConstant.CHECKLIST_PLEDGER - Value = a list of OBDocumentHeld
	 * belonging to the pledgor<br>
	 * ICMSConstant.CHECKLIST_SECURITY - Value = a list of OBDocumentHeld
	 * belonging to the security<br>
	 * 
	 * Each entry consists of a list of OBDocumentHeld sorted as follows :<br>
	 * main borrower - sorted by customer legal name<br>
	 * co-borrower - sorted by customer legal name<br>
	 * pledgor - sorted by customer legal name<br>
	 * security - sorted by security type name, security sub type name, then sci
	 * security detail id<br>
	 * 
	 * @param searchCriteria - DocumentHeldSearchCriteria
	 * @return HashMap
	 * @throws SearchDAOException
	 */
	// to supercede getCCDocumentHeld and getSecDocumentHeld and
	// getCCDocumentHeldByNonBorrower
	public HashMap getDocumentsHeld(DocumentHeldSearchCriteria searchCriteria) throws SearchDAOException {
		HashMap documentListMap = null;
		ResultSet rs = null;
		ArrayList params = new ArrayList();
		try {
			HashMap pledgorSecPledgedMap = null;
			if (searchCriteria.getLimitProfileID() != ICMSConstant.LONG_INVALID_VALUE) {
				pledgorSecPledgedMap = getPledgorSecuritiesPledged(searchCriteria.getLimitProfileID(),
						ICMSConstant.LONG_INVALID_VALUE);
			}

			String sql = getDocumentsHeldSQL(searchCriteria, params);
            //System.out.println("Get All Documents SQL -:::: " + sql+" ::::");
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);

			rs = dbUtil.executeQuery();
			//System.out.println("::::::::searchCriteria.isAllReqDoc()::::::::::"+searchCriteria.isAllReqDoc());
			if (searchCriteria.isAllReqDoc()) {
				
				documentListMap = processDocumentHeldResultSet(rs, pledgorSecPledgedMap, true);
			}
			else {
				documentListMap = processDocumentHeldResultSet(rs, pledgorSecPledgedMap);
			}

		}
		catch (Exception ex) {
			throw new SearchDAOException("failed to retrieve document held, search criteria [" + searchCriteria + "]",
					ex);
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("getDocumentsHeld : SQLException - unable to close db resource", ex);
			}
		}

		return documentListMap;
	}

	/**
	 * Helper method to formulate the required SQL to retrieve documents held
	 * based on the search criteria.
	 * 
	 * @param searchCriteria - DocumentHeldSearchCriteria
	 * @param params - ArrayList
	 * @return String - required SQL
	 */
	private String getDocumentsHeldSQL(DocumentHeldSearchCriteria searchCriteria, ArrayList params)
			throws SearchDAOException {
		checkDocHeldSearchCriteria(searchCriteria);
		StringBuffer sqlBuffer = new StringBuffer();

		/*
		 * StringBuffer sqlBufferShare = new StringBuffer(); String sqlShareCond
		 * = getShareDocumentsHeldCond(searchCriteria); if
		 * (isAllDocHeldRequired(searchCriteria)) {
		 * sqlBufferShare.append(SELECT_CC_DOC_HELD_SHARE_FOR_MB_CB_NB);
		 * sqlBufferShare.append(sqlShareCond);
		 * sqlBufferShare.append(" UNION ");
		 * sqlBufferShare.append(SELECT_CC_DOC_HELD_SHARE_FOR_PLEDGOR);
		 * sqlBufferShare.append(sqlShareCond);
		 * sqlBufferShare.append(" UNION ");
		 * sqlBufferShare.append(SELECT_SEC_DOC_HELD_SHARE_FOR_S);
		 * sqlBufferShare.append(sqlShareCond); }
		 */

		if (DocumentHeldSearchCriteria.CATEGORY_NON_BORROWER.equals(searchCriteria.getSearchCategory())) {
			DefaultLogger.debug(this, " - Retrieve Non Borrower Doc.");
			sqlBuffer.append(SELECT_CC_DOC_HELD);
			sqlBuffer.append(" AND cms_checklist.cms_lmp_sub_profile_id = ? ");
			params.add(new Long(searchCriteria.getSubProfileID()));
            if (searchCriteria.isCompletedOnly()) {
                sqlBuffer.append(" AND cms_checklist.status = 'COMPLETED' ");
            }
			if (searchCriteria.getLimitProfileID() != ICMSConstant.LONG_INVALID_VALUE) {
				sqlBuffer.append(" AND cms_checklist.cms_lsp_lmt_profile_id = ? ");
				params.add(new Long(searchCriteria.getLimitProfileID()));

				sqlBuffer.append(" UNION ");
				sqlBuffer.append(SELECT_SEC_DOC_HELD);
				sqlBuffer.append(" AND cms_checklist.cms_lsp_lmt_profile_id = ? ");
				params.add(new Long(searchCriteria.getLimitProfileID()));
                if (searchCriteria.isCompletedOnly()) {
                    sqlBuffer.append(" AND cms_checklist.status = 'COMPLETED' ");
                }
			}
			sqlBuffer.append(SELECT_DOC_HELD_ORDER_BY);
			DefaultLogger.debug(this, " Param : " + params);
			return sqlBuffer.toString();
		}
		ArrayList condParams = new ArrayList();
		String sqlCond = getDocumentsHeldCond(searchCriteria, condParams);
		if (isAllDocHeldRequired(searchCriteria)) {
			DefaultLogger.debug("CheckListDAO.getDocumentsHeldSQL", "Retrieve all docs!");
			sqlBuffer.append(SELECT_CC_DOC_HELD);
			sqlBuffer.append(sqlCond).append(" UNION ");
			sqlBuffer.append(SELECT_CC_DOC_HELD_PLEDGOR);
			sqlBuffer.append(sqlCond).append(" UNION ");
			sqlBuffer.append(SELECT_SEC_DOC_HELD);
			sqlBuffer.append(sqlCond);
			sqlBuffer.append(" UNION ");
			sqlBuffer.append(SELECT_CAM_DOC_HELD);
			sqlBuffer.append(" AND cms_checklist.cms_lsp_lmt_profile_id = ?");
			params.add(new Long(searchCriteria.getLimitProfileID()));
			sqlBuffer.append(" UNION ");
			sqlBuffer.append(SELECT_FACILITY_DOC_HELD);
			sqlBuffer.append(" AND cms_checklist.cms_lsp_lmt_profile_id = ?");
			params.add(new Long(searchCriteria.getLimitProfileID()));	
			// sqlBuffer.append(sqlBufferShare);
			sqlBuffer.append(SELECT_DOC_HELD_ORDER_BY);

			if (condParams.size() > 0) {
				params.addAll(condParams);
				params.addAll(condParams);
				params.addAll(condParams);
			}
			return sqlBuffer.toString();
		}

		if (isDocCategoryRequired(searchCriteria, ICMSConstant.CHECKLIST_SECURITY, null)) {
			DefaultLogger.debug("CheckListDAO.getDocumentsHeldSQL", "Retrieve a security doc!");
			sqlBuffer.append(SELECT_SEC_DOC_HELD);
			sqlBuffer.append(sqlCond);
			if (condParams.size() > 0) {
				params.addAll(condParams);
			}
			// sqlBuffer.append(" UNION ");
			// sqlBuffer.append(SELECT_SEC_DOC_HELD_SHARE_FOR_S);
			// sqlBuffer.append(sqlShareCond);
			return sqlBuffer.toString();
		}
		if (isDocCategoryRequired(searchCriteria, null, ICMSConstant.CHECKLIST_MAIN_BORROWER)) {
			DefaultLogger.debug("CheckListDAO.getDocumentsHeldSQL", "Retrieve a main borrower doc!");
			sqlBuffer.append(SELECT_CC_DOC_HELD);
			sqlBuffer.append(sqlCond);
			if (condParams.size() > 0) {
				params.addAll(condParams);
			}
			// sqlBuffer.append(" UNION ");
			// sqlBuffer.append(SELECT_CC_DOC_HELD_SHARE_FOR_MB_CB_NB);
			// sqlBuffer.append(sqlShareCond);
			return sqlBuffer.toString();
		}
		if (isDocCategoryRequired(searchCriteria, null, ICMSConstant.CHECKLIST_CO_BORROWER)) {
			DefaultLogger.debug("CheckListDAO.getDocumentsHeldSQL", "Retrieve a co borrower doc!");
			sqlBuffer.append(SELECT_CC_DOC_HELD);
			sqlBuffer.append(sqlCond);
			if (condParams.size() > 0) {
				params.addAll(condParams);
			}
			// sqlBuffer.append(" UNION ");
			// sqlBuffer.append(SELECT_CC_DOC_HELD_SHARE_FOR_MB_CB_NB);
			// sqlBuffer.append(sqlShareCond);
			return sqlBuffer.toString();
		}
		if (isDocCategoryRequired(searchCriteria, null, ICMSConstant.CHECKLIST_PLEDGER)) {
			DefaultLogger.debug("CheckListDAO.getDocumentsHeldSQL", "Retrieve a pledgor doc!");
			sqlBuffer.append(SELECT_CC_DOC_HELD_PLEDGOR);
			sqlBuffer.append(sqlCond);
			if (condParams.size() > 0) {
				params.addAll(condParams);
			}
			// sqlBuffer.append(" UNION ");
			// sqlBuffer.append(SELECT_CC_DOC_HELD_SHARE_FOR_PLEDGOR);
			// sqlBuffer.append(sqlShareCond);
			return sqlBuffer.toString();
		}
		if (isDocCategoryRequired(searchCriteria, null, ICMSConstant.CHECKLIST_NON_BORROWER)) {
			DefaultLogger.debug("CheckListDAO.getDocumentsHeldSQL", "Retrieve a non borrower doc!");
			sqlBuffer.append(SELECT_CC_DOC_HELD);
			sqlBuffer.append(sqlCond);
			if (condParams.size() > 0) {
				params.addAll(condParams);
			}
			// sqlBuffer.append(" UNION ");
			// sqlBuffer.append(SELECT_CC_DOC_HELD_SHARE_FOR_MB_CB_NB);
			// sqlBuffer.append(sqlShareCond);
			return sqlBuffer.toString();
		}
		return null;
	}

	/**
	 * Helper method to check if all doc held required
	 */
	private boolean isAllDocHeldRequired(DocumentHeldSearchCriteria searchCriteria) {
		return (((searchCriteria.getLimitProfileID() > 0) || (searchCriteria.getSubProfileID() > 0))
				&& ((searchCriteria.getCategory() == null) || (searchCriteria.getCategory().length() == 0)) && (searchCriteria
				.getSubCategory() == null));
	}

	/**
	 * Helper method to check if a particular doc category is requied.
	 */
	private boolean isDocCategoryRequired(DocumentHeldSearchCriteria searchCriteria, String category, String subCategory) {
		if ((searchCriteria.getCategory() != null) && ICMSConstant.CHECKLIST_SECURITY.equals(category)) {
			return (ICMSConstant.CHECKLIST_SECURITY.equals(searchCriteria.getCategory()));
		}
		if (searchCriteria.getSubCategory() == null) {
			return false;
		}
		if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(subCategory)) {
			return (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(searchCriteria.getSubCategory()));
		}
		if (ICMSConstant.CHECKLIST_CO_BORROWER.equals(subCategory)) {
			return (ICMSConstant.CHECKLIST_CO_BORROWER.equals(searchCriteria.getSubCategory()));
		}
		if (ICMSConstant.CHECKLIST_PLEDGER.equals(subCategory)) {
			return (ICMSConstant.CHECKLIST_PLEDGER.equals(searchCriteria.getSubCategory()));
		}
		if (ICMSConstant.CHECKLIST_NON_BORROWER.equals(subCategory)) {
			return (ICMSConstant.CHECKLIST_NON_BORROWER.equals(searchCriteria.getSubCategory()));
		}
		return false;
	}

	/**
	 * Helper method to process the resultset to get map of document held.
	 * 
	 * @param rs - ResultSet
	 * @param pledgorSecPledgedMap - HashMap of sec pledged by all pledgors for
	 *        this customer
	 * @return HashMap - Map containing list of required documents. Keys can be
	 *         MAIN_BORROWER, CO_BORROWER, PLEDGOR, NON_BORROWER, SECURITY.
	 *         Values are a list of OBDocumentHeld.
	 * @throws SQLException
	 */
	private HashMap processDocumentHeldResultSet(ResultSet rs, HashMap pledgorSecPledgedMap) throws SQLException,
			CollateralException, SearchDAOException {
		ArrayList mainBorrowersDocList = new ArrayList();
		ArrayList coBorrowersDocList = new ArrayList();
		ArrayList jointBorrowerDocList = new ArrayList();
		ArrayList pledgorDocList = new ArrayList();
		ArrayList nonBorrowerDocList = new ArrayList();
		ArrayList securityDocList = new ArrayList();
		ArrayList camDocList = new ArrayList();
		ArrayList facilityDocList = new ArrayList();
//		System.out.println("::::::::processDocumentHeldResultSet()-----2::::::::::");
		long checklistID = ICMSConstant.LONG_INVALID_VALUE;
		String category = null;

		ArrayList doclist = new ArrayList();
		ArrayList itemsList = null;
		OBDocumentHeld currentDoc = null;
		OBDocumentHeld previousDoc = null;
		int itemCount = 0;
		ICollateralBusManager colMgr = CollateralBusManagerFactory.getActualCollateralBusManager();
		while (rs.next()) {

			checklistID = rs.getLong("checklist_id");
			category = rs.getString(CHKTBL_CATEGORY);

			if (!doclist.contains(new Long(checklistID))) {
				// DefaultLogger.debug("CheckListDAO.processDocumentHeldResultSet"
				// , ">>>>>> checklistID : " + checklistID);

				doclist.add(new Long(checklistID));
				previousDoc = currentDoc;

				if ((previousDoc != null) && (itemsList != null) && (itemsList.size() > 0)) {
					previousDoc.setDocHeldItems((OBDocumentHeldItem[]) itemsList
							.toArray(new OBDocumentHeldItem[itemsList.size()]));

				}

				// create new OBDocumentHeld
				currentDoc = createNewDocumentHeld(rs, colMgr, pledgorSecPledgedMap);

				itemsList = new ArrayList();
				addToCorrectList(currentDoc, mainBorrowersDocList, coBorrowersDocList, jointBorrowerDocList,
						pledgorDocList, nonBorrowerDocList, securityDocList,camDocList,facilityDocList);
			}
			itemCount++;

			String itemStatus = rs.getString("item_status");
			String custDocStatus = null;

			if ((custDocStatus == null) || !ICMSConstant.STATE_PERM_UPLIFTED.equals(custDocStatus)) {
				if (isDocumentHeldItemIncluded(itemStatus)) {
					itemsList.add(createNewDocumentHeldItem(rs)); // create nw
					// OBdocumentHeldItem
				}
			}
		}

		// DefaultLogger.debug("CheckListDAO.processDocumentHeldResultSet",
		// ">>>>> TOTAL item count : " + itemCount);

		// to cater for the last iteration
		if ((currentDoc != null) && (itemsList != null) && (itemsList.size() > 0)) {
			currentDoc.setDocHeldItems((OBDocumentHeldItem[]) itemsList
					.toArray(new OBDocumentHeldItem[itemsList.size()]));
		}

		HashMap documentHeldListMap = new HashMap(5);
		if (mainBorrowersDocList.size() > 0) {
			documentHeldListMap.put(ICMSConstant.CHECKLIST_MAIN_BORROWER, mainBorrowersDocList);
		}
		if (coBorrowersDocList.size() > 0) {
			documentHeldListMap.put(ICMSConstant.CHECKLIST_CO_BORROWER, coBorrowersDocList);
		}
		if (jointBorrowerDocList.size() > 0) {
			documentHeldListMap.put(ICMSConstant.CHECKLIST_JOINT_BORROWER, jointBorrowerDocList);
		}
		if (pledgorDocList.size() > 0) {
			documentHeldListMap.put(ICMSConstant.CHECKLIST_PLEDGER, pledgorDocList);
		}
		if (nonBorrowerDocList.size() > 0) {
			documentHeldListMap.put(ICMSConstant.CHECKLIST_NON_BORROWER, nonBorrowerDocList);
		}
		if (securityDocList.size() > 0) {
			documentHeldListMap.put(ICMSConstant.CHECKLIST_SECURITY, securityDocList);
		}
		//System.out.println("::::::::camDocList.size()::::::::::"+camDocList.size());
		if (camDocList.size() > 0) {
			documentHeldListMap.put(ICMSConstant.CHECKLIST_CAM, camDocList);
		}
		if (facilityDocList.size() > 0) {
			documentHeldListMap.put(ICMSConstant.CHECKLIST_FACILITY, facilityDocList);
		}

		return documentHeldListMap;
	}

	/**
	 * Helper method to process the resultset to get map of document held.
	 * 
	 * @param rs - ResultSet
	 * @param pledgorSecPledgedMap - HashMap of sec pledged by all pledgors for
	 *        this customer
	 * @return HashMap - Map containing list of required documents. Keys can be
	 *         MAIN_BORROWER, CO_BORROWER, PLEDGOR, NON_BORROWER, SECURITY.
	 *         Values are a list of OBDocumentHeld.
	 * @throws SQLException
	 */
	private HashMap processDocumentHeldResultSet(ResultSet rs, HashMap pledgorSecPledgedMap, boolean allReq)
			throws SQLException, CollateralException, SearchDAOException {
		ArrayList mainBorrowersDocList = new ArrayList();
		ArrayList coBorrowersDocList = new ArrayList();
		ArrayList jointBorrowerDocList = new ArrayList();
		ArrayList pledgorDocList = new ArrayList();
		ArrayList nonBorrowerDocList = new ArrayList();
		ArrayList securityDocList = new ArrayList();
		//System.out.println("::::::::processDocumentHeldResultSet()-----3::::::::::");
		long checklistID = ICMSConstant.LONG_INVALID_VALUE;
		String category = null;

		ArrayList doclist = new ArrayList();
		ArrayList itemsList = null;
		OBDocumentHeld currentDoc = null;
		OBDocumentHeld previousDoc = null;
		ICollateralBusManager colMgr = CollateralBusManagerFactory.getActualCollateralBusManager();
		while (rs.next()) {

			checklistID = rs.getLong("checklist_id");
			category = rs.getString(CHKTBL_CATEGORY);

			if (!doclist.contains(new Long(checklistID))) {
				doclist.add(new Long(checklistID));
				previousDoc = currentDoc;

				if ((previousDoc != null) && (itemsList != null) && (itemsList.size() > 0)) {
					previousDoc.setDocHeldItems((OBDocumentHeldItem[]) itemsList
							.toArray(new OBDocumentHeldItem[itemsList.size()]));
				}

				// create new OBDocumentHeld
				currentDoc = createNewDocumentHeld(rs, colMgr, pledgorSecPledgedMap);
				itemsList = new ArrayList();
				addToCorrectList(currentDoc, mainBorrowersDocList, coBorrowersDocList, jointBorrowerDocList,
						pledgorDocList, nonBorrowerDocList, securityDocList);
			}
			itemsList.add(createNewDocumentHeldItem(rs));
		}

		// to cater for the last iteration
		if ((currentDoc != null) && (itemsList != null) && (itemsList.size() > 0)) {
			currentDoc.setDocHeldItems((OBDocumentHeldItem[]) itemsList
					.toArray(new OBDocumentHeldItem[itemsList.size()]));
		}

		HashMap documentHeldListMap = new HashMap(5);
		if (mainBorrowersDocList.size() > 0) {
			documentHeldListMap.put(ICMSConstant.CHECKLIST_MAIN_BORROWER, mainBorrowersDocList);
		}
		if (coBorrowersDocList.size() > 0) {
			documentHeldListMap.put(ICMSConstant.CHECKLIST_CO_BORROWER, coBorrowersDocList);
		}
		if (jointBorrowerDocList.size() > 0) {
			documentHeldListMap.put(ICMSConstant.CHECKLIST_JOINT_BORROWER, jointBorrowerDocList);
		}
		if (pledgorDocList.size() > 0) {
			documentHeldListMap.put(ICMSConstant.CHECKLIST_PLEDGER, pledgorDocList);
		}
		if (nonBorrowerDocList.size() > 0) {
			documentHeldListMap.put(ICMSConstant.CHECKLIST_NON_BORROWER, nonBorrowerDocList);
		}
		if (securityDocList.size() > 0) {
			documentHeldListMap.put(ICMSConstant.CHECKLIST_SECURITY, securityDocList);
		}

		return documentHeldListMap;
	}

	/**
	 * Helper method to check if item is to be included
	 */
	private boolean isDocumentHeldItemIncluded(String itemStatus) {
		return ((itemStatus != null) && (ICMSConstant.STATE_ITEM_COMPLETED.equals(itemStatus) || ICMSConstant.STATE_ITEM_EXPIRED
				.equals(itemStatus)));
	}

	public ArrayList getSecurityOwnerList(long collateralID, long lmtProfileID) throws SearchDAOException {
		try {
			String sql = "select * from VW_BCA_SEC_MAP where CMS_COLLATERAL_ID=? and CMS_LSP_LMT_PROFILE_ID=?";
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, collateralID);
			dbUtil.setLong(2, lmtProfileID);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList customerList = parseOwerList(rs);
			rs.close();
			return customerList;
		}
		catch (Exception eX) {
			throw new SearchDAOException("Exception in getMutiCustomerListByCollateralID", eX);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException sqlEx) {
				throw new SearchDAOException("SQLException in getMutiCustomerListByCollateralID", sqlEx);
			}
		}
	}

	public ArrayList getMutiCustomerListByCollateralID(long collateralID) throws SearchDAOException {
		try {
			String sql = "select * from VW_BCA_SEC_MAP where CMS_COLLATERAL_ID=?";
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, collateralID);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList customerList = parseOwerList(rs);
			rs.close();
			return customerList;
		}
		catch (SQLException sqlEx) {
			throw new SearchDAOException("SQLException in getMutiCustomerListByCollateralID", sqlEx);
		}
		catch (Exception eX) {
			throw new SearchDAOException("Exception in getMutiCustomerListByCollateralID", eX);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException sqlEx) {
				throw new SearchDAOException("SQLException in getMutiCustomerListByCollateralID", sqlEx);
			}
		}
	}

	private ArrayList parseOwerList(ResultSet rs) throws SQLException {
		ArrayList customerList = new ArrayList();
		if (rs == null) {
			return customerList;
		}
		while (rs.next()) {
			ISecurityCustomer sCustomer = new OBSecurityCustomer();
			sCustomer.setCollateralID(rs.getLong("CMS_COLLATERAL_ID"));
			sCustomer.setSecurityID(rs.getLong("SCI_SECURITY_DTL_ID"));
			sCustomer.setCustomerCategory(rs.getString("CUSTOMER_CATEGORY"));
			sCustomer.setSegment(rs.getString("customer_segment_code"));
			if (ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER.equals(sCustomer.getCustomerCategory())) {
				sCustomer.setCustomerID(rs.getLong("CMS_CUSTOMER_ID"));
				sCustomer.setLeID(rs.getLong("LSP_LE_ID"));
				sCustomer.setLeName(rs.getString("LSP_SHORT_NAME"));
				sCustomer.setLspID(rs.getLong("LSP_ID"));
			}
			else {
				sCustomer.setCustomerID(rs.getLong("CB_CUSTOMER_ID"));
				sCustomer.setLeID(rs.getLong("CB_LE_ID"));
				sCustomer.setLeName(rs.getString("CB_LEGAL_NAME"));
				sCustomer.setLspID(rs.getLong("CB_LSP_ID"));
			}
			customerList.add(sCustomer);
		}
		return customerList;
	}

	/**
	 * return customer name String formate like
	 * 'customerAname;customerBname;customerCname'
	 */
	private String getCustomerNameByCollateralID(long collateralID) throws SearchDAOException {
		String customerName = "";
		ArrayList aCustomerList = getMutiCustomerListByCollateralID(collateralID);
		if ((aCustomerList == null) || (aCustomerList.size() == 0)) {
			return customerName;
		}
		for (int i = 0; i < aCustomerList.size(); i++) {
			ISecurityCustomer sCustomer = (OBSecurityCustomer) aCustomerList.get(i);
			if ((sCustomer == null) || (sCustomer.getLeName() == null)) {
				continue;
			}
			if ((i == 0) || (i == aCustomerList.size())) {
				customerName = customerName + sCustomer.getLeName();
			}
			else {
				customerName = customerName + ";" + sCustomer.getLeName();
			}
		}

		return customerName;

	}

	/**
	 * Helper method to create new document held.
	 * 
	 * @param rs - ResultSet
	 * @param colMgr - collateral business manager
	 */
	private OBDocumentHeld createNewDocumentHeld(ResultSet rs, ICollateralBusManager colMgr,
			HashMap pledgorSecPledgedMap) throws SQLException, CollateralException, SearchDAOException {
		OBDocumentHeld doc = new OBDocumentHeld();

		String category = rs.getString(CHKTBL_CATEGORY);
		String subCategory = rs.getString(CHKTBL_SUB_CATEGORY);
        String checkListStatus = rs.getString(CHKTBL_STATUS);
		String leID = null;
		String leName = null;
		String tempStr = null;

		doc.setCheckListID(rs.getLong(CHKTBL_CHECKLISTID));
		doc.setCategory(category);
		doc.setSubCategory(subCategory);
        doc.setCheckListStatus(checkListStatus);

		if (ICMSConstant.DOC_TYPE_SECURITY.equals(category)) {
			long collateral_id = rs.getLong("collateral_id");
			ICollateral col = colMgr.getCollateral(collateral_id);
			doc.setCollateral(col);
			// doc.setCoCustomerList(getMutiCustomerListByCollateralID(collateral_id));
			IBookingLocation bkg = new OBBookingLocation(rs.getString("DOC_ORIG_COUNTRY"), rs
					.getString("DOC_ORIG_ORGANISATION"));
			doc.setCheckListLocation(bkg);

			leID = rs.getString("borrower_le_id");

			doc.setLegalID(leID);
			// doc.setLegalName(rs.getString("borrower_name"));
			// doc.setLegalName(getCustomerNameByCollateralID(collateral_id));
		}
		else {

			if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(subCategory)
					|| ICMSConstant.CHECKLIST_CO_BORROWER.equals(subCategory)
					|| ICMSConstant.CHECKLIST_JOINT_BORROWER.equals(subCategory)
					|| ICMSConstant.CHECKLIST_NON_BORROWER.equals(subCategory)) {
				leID = rs.getString("borrower_le_id");
				leName = rs.getString("borrower_name");
				doc.setCustomerID(rs.getLong("BORROWER_ID"));
			}
			else if (ICMSConstant.CHECKLIST_PLEDGER.equals(subCategory)) {
				leID = rs.getString("pledgor_le_id");
				leName = rs.getString("pledgor_name");

				tempStr = rs.getString("pledgor_id");
				doc.setPledgorID((tempStr == null) ? ICMSConstant.LONG_INVALID_VALUE : Long.parseLong(tempStr));
				doc.setCustomerID(rs.getLong("CMS_PLEDGOR_ID"));
				ArrayList secPledgedDetailsList = getSecurityPledgedDetailsList(doc.getPledgorID(),
						pledgorSecPledgedMap);
				doc.setPledgorSecurityPledged(secPledgedDetailsList);
			}
			IBookingLocation bkg = new OBBookingLocation(rs.getString("DOC_ORIG_COUNTRY"), rs
					.getString("DOC_ORIG_ORGANISATION"));
			doc.setCheckListLocation(bkg);
			doc.setLegalID(leID);
			doc.setLegalName(leName);
		}

		return doc;
	}

	private ArrayList getSecurityPledgedDetailsList(long pledgorID, HashMap pledgorSecPledgedMap)
			throws SearchDAOException {
		ArrayList secPledgedDetailsList = null;
		if (pledgorID != ICMSConstant.LONG_INVALID_VALUE) {
			if (pledgorSecPledgedMap != null) {
				secPledgedDetailsList = (ArrayList) (pledgorSecPledgedMap.get(new Long(pledgorID)));
			}
			if (secPledgedDetailsList == null) {
				HashMap map = getPledgorSecuritiesPledged(ICMSConstant.LONG_INVALID_VALUE, pledgorID);
				secPledgedDetailsList = (ArrayList) ((map != null) ? map.get(new Long(pledgorID)) : null);
			}
		}
		return secPledgedDetailsList;
	}

	/**
	 * Helper method to create a new document held item
	 * 
	 * @param rs - ResultSet
	 * @return OBDocumentHeldItem
	 * @throws SQLException
	 */
	private OBDocumentHeldItem createNewDocumentHeldItem(ResultSet rs) throws SQLException {
		OBDocumentHeldItem newItem = new OBDocumentHeldItem();
		newItem.setDocNo(rs.getLong(ITMTBL_DOC_ITEM_REF));
		newItem.setDocCode(rs.getString(ITMTBL_DOCUMENT_CODE));
		newItem.setDocDesc(rs.getString(ITMTBL_DOC_DESCRIPTION));
		newItem.setNarration(rs.getString(ITMTBL_REMARKS));

		newItem.setDocStatus(rs.getString("docstatus"));
		newItem.setDocDate(rs.getDate(ITMTBL_DOC_DATE));
		newItem.setDocExpiry(rs.getDate(ITMTBL_EXPIRY_DATE));

		// R1.5 CR17
		String shareStatus = rs.getString("SHARE_STATUS");
		newItem
				.setIsShared(((shareStatus == null) || shareStatus.equals("") || shareStatus.equals("N")) ? false
						: true);
		newItem.setShareDetailsSummary(rs.getString("share_details"));

		// parent item details
		String tempStr = rs.getString("parent_item_ref_id");
		long parentDocItemRefID = ((tempStr == null) || (Long.parseLong(tempStr) <= 0)) ? ICMSConstant.LONG_INVALID_VALUE
				: Long.parseLong(tempStr);
		String parentDocItemDocCode = null;
		if ((tempStr != null) && (Long.parseLong(tempStr) > 0)) {
			parentDocItemRefID = Long.parseLong(tempStr);
			parentDocItemDocCode = rs.getString("parent_item_doc_code");
		}
		else {
			parentDocItemRefID = ICMSConstant.LONG_INVALID_VALUE;
			parentDocItemDocCode = null;
		}
		newItem.setParentDocItemRefID(parentDocItemRefID);
		newItem.setParentDocItemDocCode(parentDocItemDocCode);

		tempStr = rs.getString(ITMTBL_IN_EXT_CUSTODY);
		newItem.setExtCust(tempStr == null ? null : tempStr.equals("Y") ? Boolean.TRUE : Boolean.FALSE);

		tempStr = rs.getString(ITMTBL_IN_VAULT);
		newItem.setInVault(tempStr == null ? null : tempStr.equals("Y") ? Boolean.TRUE : Boolean.FALSE);

		// derive custodian status based on cpc_custstatus and custstatus
		String item_cpc_cust_status = rs.getString("cpc_custstatus");
		String item_cust_status = null; // rs.getString("custstatus");
		String derived_cust_status = CheckListCustodianHelper.getCheckListCustodianStatus(item_cpc_cust_status,
				item_cust_status);
		// convert cust status to AWAITING where necessary
		derived_cust_status = (isItemInAwaitStatus(derived_cust_status, newItem.isInVault())) ? ICMSConstant.STATE_ITEM_AWAITING
				: derived_cust_status;
		newItem.setCustStatus(derived_cust_status);

		return newItem;
	}

	/**
	 * Helper method to check if item should be in awaiting state
	 */
	private boolean isItemInAwaitStatus(String custStatus, Boolean isInVault) {
		// TODO : consider moving to a util class
		return (((custStatus == null) && isInVault.booleanValue()) || ((custStatus != null) && ICMSConstant.STATE_ITEM_RECEIVED
				.equals(custStatus)));
	}

	/**
	 * Helper method to add doc to the correct list
	 */
	
	private void addToCorrectList(OBDocumentHeld doc, ArrayList mainBorrowersDocList, ArrayList coBorrowersDocList,
			ArrayList jointBorrowerList, ArrayList pledgorDocList, ArrayList nonBorrowerDocList,
			ArrayList securityDocList ) {
		String category = doc.getCategory();
		String subCategory = doc.getSubCategory();

		if (ICMSConstant.DOC_TYPE_SECURITY.equals(category)) {
			securityDocList.add(doc);
		}
		else if (subCategory != null) {
			if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(subCategory)) { // main
				// borrower
				mainBorrowersDocList.add(doc);
			}
			else if (ICMSConstant.CHECKLIST_JOINT_BORROWER.equals(subCategory)) {
				jointBorrowerList.add(doc);

			}
			else if (ICMSConstant.CHECKLIST_PLEDGER.equals(subCategory)) { // pledgor
				pledgorDocList.add(doc);
			}
			else if (ICMSConstant.CHECKLIST_CO_BORROWER.equals(subCategory)) { // co
				// -
				// borrower
				coBorrowersDocList.add(doc);
			}
			else if (ICMSConstant.CHECKLIST_NON_BORROWER.equals(subCategory)) { // non
				// -
				// borrower
				nonBorrowerDocList.add(doc);
			}
		}
	}
	private void addToCorrectList(OBDocumentHeld doc, ArrayList mainBorrowersDocList, ArrayList coBorrowersDocList,
			ArrayList jointBorrowerList, ArrayList pledgorDocList, ArrayList nonBorrowerDocList,
			ArrayList securityDocList,ArrayList camDocList,ArrayList facilityDocList) {
		String category = doc.getCategory();
		String subCategory = doc.getSubCategory();

		if (ICMSConstant.DOC_TYPE_SECURITY.equals(category)) {
			securityDocList.add(doc);
		}else if (ICMSConstant.DOC_TYPE_CAM.equals(category)) {
			camDocList.add(doc);
		}else if (ICMSConstant.DOC_TYPE_FACILITY.equals(category)) {
			facilityDocList.add(doc);
		}
		/*else if (subCategory != null) {
			if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(subCategory)) { // main
				// borrower
				mainBorrowersDocList.add(doc);
			}
			else if (ICMSConstant.CHECKLIST_JOINT_BORROWER.equals(subCategory)) {
				jointBorrowerList.add(doc);

			}
			else if (ICMSConstant.CHECKLIST_PLEDGER.equals(subCategory)) { // pledgor
				pledgorDocList.add(doc);
			}
			else if (ICMSConstant.CHECKLIST_CO_BORROWER.equals(subCategory)) { // co
				// -
				// borrower
				coBorrowersDocList.add(doc);
			}
			else if (ICMSConstant.CHECKLIST_NON_BORROWER.equals(subCategory)) { // non
				// -
				// borrower
				nonBorrowerDocList.add(doc);
			}
		}*/
	}

	/**
	 * Helper method to formulate the additional conditions to retrieve list of
	 * documents held.
	 * 
	 * @param searchCriteria - DocumentHeldSearchCriteria
	 * @param params - ArrayList
	 * @return String - additional SQL condition
	 */
	private String getDocumentsHeldCond(DocumentHeldSearchCriteria searchCriteria, ArrayList params)
			throws SearchDAOException {
		if (searchCriteria.getCheckListID() > 0) {
			params.add(new Long(searchCriteria.getCheckListID()));
			return " AND cms_checklist.checklist_id = ?";
		}

		StringBuffer buf = new StringBuffer();
		if (searchCriteria.getLimitProfileID() != ICMSConstant.LONG_INVALID_VALUE) {
			buf.append(" AND cms_checklist.cms_lsp_lmt_profile_id = ?");
			params.add(new Long(searchCriteria.getLimitProfileID()));
		}
		if (searchCriteria.getSubProfileID() != ICMSConstant.LONG_INVALID_VALUE) {
			buf.append(" AND cms_checklist.cms_lmp_sub_profile_id = ?");
			params.add(new Long(searchCriteria.getSubProfileID()));
		}
		if ((searchCriteria.getCategory() != null) && (searchCriteria.getCategory().length() > 0)) {
			buf.append(" AND cms_checklist.category = ?");
			params.add(searchCriteria.getCategory());
		}
		if ((searchCriteria.getSubCategory() != null) && (searchCriteria.getSubCategory().length() > 0)) {
			buf.append(" AND cms_checklist.sub_category = ?");
			params.add(searchCriteria.getSubCategory());
		}
		if (searchCriteria.isAllReqDoc()) {
			buf.append(" AND cms_checklist_item.status IN ('" + ICMSConstant.STATE_ITEM_PENDING_DEFERRAL + "', '"
					+ ICMSConstant.STATE_ITEM_AWAITING + "', '" + ICMSConstant.STATE_ITEM_DEFERRED + "', '"
					+ ICMSConstant.STATE_ITEM_REMINDED + "')");
		}
        if (searchCriteria.isCompletedOnly()) {
            buf.append(" AND cms_checklist.status = 'COMPLETED'");
        }
		return buf.toString();
	}

	private String getShareDocumentsHeldCond(DocumentHeldSearchCriteria searchCriteria) {
		StringBuffer buf = new StringBuffer();
		if (searchCriteria.getCheckListID() > 0) {
			buf.append(" AND cms_checklist_item_share.CHECKLIST_ID =  ");
			buf.append(new Long(searchCriteria.getCheckListID()));
			return buf.toString();
		}

		if (searchCriteria.getSubProfileID() != ICMSConstant.LONG_INVALID_VALUE) {
			buf.append(" AND cms_checklist.CMS_LMP_SUB_PROFILE_ID = ");
			buf.append(new Long(searchCriteria.getSubProfileID()));
		}

		if (searchCriteria.getLimitProfileID() != ICMSConstant.LONG_INVALID_VALUE) {
			// buf.append(
			// " AND cms_checklist_item_share.CMS_LSP_LMT_PROFILE_ID = ");
			buf.append(" AND cms_checklist.CMS_LSP_LMT_PROFILE_ID =  ");
			buf.append(new Long(searchCriteria.getLimitProfileID()));
		}

		if ((searchCriteria.getCategory() != null) && (searchCriteria.getCategory().length() > 0)) {
			buf.append(" AND cms_checklist.category = ");
			buf.append("'" + searchCriteria.getCategory() + "'");
		}
		// if (searchCriteria.getSubCategory() != null &&
		// searchCriteria.getSubCategory().length() > 0) { MainBorrower can be
		// link to Co-Borrower/Non-Borrower etc
		// buf.append(" AND cms_checklist.sub_category = ");
		// buf.append("'" + searchCriteria.getSubCategory()+ "'");
		// }
		if (searchCriteria.isAllReqDoc()) {
			buf.append(" AND cms_checklist_item.status IN ('" + ICMSConstant.STATE_ITEM_PENDING_DEFERRAL + "', '"
					+ ICMSConstant.STATE_ITEM_AWAITING + "', '" + ICMSConstant.STATE_ITEM_DEFERRED + "', '"
					+ ICMSConstant.STATE_ITEM_REMINDED + "')");
			// } else { //R1.5 UAT CMS-3580:- Originally shifted from
			// "SELECT_CC_DOC_HELD_SHARE_FOR_MB_CB_NB" to here, but its actually
			// taken care of in processDocumentHeldResultSet
			// buf.append(
			// " AND cms_checklist_item.STATUS IN ('COMPLETED', 'EXPIRED') ");
		}
		return buf.toString();
	}

	/**
	 * Check if valid document held search criteria has been specified.
	 * 
	 * @param searchCriteria - DocumentHeldSearchCriteria
	 * @throws SearchDAOException if no criteria specified or invalid search
	 *         criteria is specified.
	 */
	private void checkDocHeldSearchCriteria(DocumentHeldSearchCriteria searchCriteria) throws SearchDAOException {
		if (searchCriteria == null) {
			throw new SearchDAOException("No search Criteria specified");
		}
		if (((searchCriteria.getCheckListID() <= 0) && (searchCriteria.getLimitProfileID() <= 0) && (searchCriteria
				.getSubProfileID() <= 0))
				|| ((searchCriteria.getCheckListID() > 0)
						&& ((searchCriteria.getCategory() == null) || (searchCriteria.getCategory().length() == 0)) && ((searchCriteria
						.getSubCategory() == null) || (searchCriteria.getSubCategory().length() == 0)))) {
			throw new SearchDAOException("Invalid search criteria specified - checklist ID : "
					+ searchCriteria.getCheckListID() + "    limit profile ID : " + searchCriteria.getLimitProfileID()
					+ "    sub profile ID : " + searchCriteria.getSubProfileID() + "    category : "
					+ searchCriteria.getCategory() + "    sub category : " + searchCriteria.getSubCategory());
		}
	}

	/**
	 * Get list of securities pledged for this borrower and the specific pledgor
	 * if pledgor id specified.
	 * 
	 * @param aLimitProfileID
	 * @param aPledgorID
	 * @return HashMap - map with key as pledgor id and value as a String array
	 *         of securities details
	 */
	public HashMap getPledgorSecuritiesPledged(long aLimitProfileID, long aPledgorID) throws SearchDAOException {
		HashMap secPledgedMap = null;
		ArrayList params = new ArrayList();
		String sql = getPledgorSecuritiesPledgedSQL(aLimitProfileID, aPledgorID, params);
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug("CheckListDAO.getPledgorSecuritiesPledged",
			// "SQL to retrieve sec pledged : " + sql);
			CommonUtil.setSQLParams(params, dbUtil);
			rs = dbUtil.executeQuery();
			secPledgedMap = processSecuritiesPledgedResultSet(rs);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException("getPledgorSecuritiesPledged: Exception", ex);
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException(
						"getPledgorSecuritiesPledged : SQLException - unable to close db resource", ex);
			}
		}
		return secPledgedMap;
	}

	/**
	 * Helper method to get the SQL to retrieve details for pledged securities.
	 * 
	 * @param aLimitProfileID - long
	 * @param aPledgorID - long
	 * @param params - ArrayList to contain parameters to be set to SQL
	 * @return String - SQL to retrieve details for pledged securities.
	 */
	private String getPledgorSecuritiesPledgedSQL(long aLimitProfileID, long aPledgorID, ArrayList params)
			throws SearchDAOException {
		if ((aLimitProfileID == ICMSConstant.LONG_INVALID_VALUE) && (aPledgorID == ICMSConstant.LONG_INVALID_VALUE)) {
			throw new SearchDAOException("Invalid params encountered when retrieving securities pledged.");
		}
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_SECURITY_PLEDGED);
		if (aLimitProfileID != ICMSConstant.LONG_INVALID_VALUE) {
			buf.append(" AND sci_lsp_appr_lmts.cms_limit_profile_id = ?");
			params.add(new Long(aLimitProfileID));
		}
		if (aPledgorID != ICMSConstant.LONG_INVALID_VALUE) {
			buf.append(" AND sci_pledgor_dtl.plg_pledgor_id = ?");
			params.add(new Long(aPledgorID));
		}
		buf.append(SELECT_SECURITY_PLEDGED_ORDER_BY);
		return buf.toString();
	}

	/**
	 * Helper method to process the results set to retrieve details for pledged
	 * securities.
	 * 
	 * @param rs ResultSet
	 * @return HashMap - map with key as pledgor id and value as a String array
	 *         of securities details
	 */
	private HashMap processSecuritiesPledgedResultSet(ResultSet rs) throws SQLException {
		HashMap secPledgedMap = new HashMap();
		ArrayList secPledgedList = null;
		String sciSecurityID = null;
		String securityTypeName = null;
		String securitySubTypeName = null;
		long pledgor_id = ICMSConstant.LONG_INVALID_VALUE;
		while (rs.next()) {
			pledgor_id = rs.getLong("pledgor_id");
			if (!secPledgedMap.containsKey(new Long(pledgor_id))) {
				// new pledgor detected
				secPledgedList = new ArrayList();
				secPledgedMap.put(new Long(pledgor_id), secPledgedList);
			}
			else {
				secPledgedList = (ArrayList) secPledgedMap.get(new Long(pledgor_id));
			}

			sciSecurityID = rs.getString("sci_security_id");
			securityTypeName = formatString(rs.getString("security_type_name"));
			securitySubTypeName = formatString(rs.getString("security_subtype_name"));

			secPledgedList.add(new String[] { sciSecurityID, securityTypeName, securitySubTypeName });
		}

		return secPledgedMap;
	}

	/**
	 * Get the list of securities pledged for this borrower type.
	 * @param aLimitProfileID the limit profile identifier
	 * @param pledgorID the pledgor identifier
	 * @return HashMap - the list of document categories
	 * @throws SearchDAOException on errors
	 */
	public HashMap getSecuritiesPledged(long aLimitProfileID, long pledgorID) throws SearchDAOException {
		DefaultLogger.info(this, "IN method getSecuritiesPledged");

		if (inValidLimitProfile(aLimitProfileID)) {
			return null;
		}
		if (inValidPledgor(pledgorID)) {
			return null;
		}

		String sql = getSecuritiesPledgedSQL();

		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, aLimitProfileID);
			dbUtil.setLong(2, pledgorID);

			ResultSet rs = dbUtil.executeQuery();
			HashMap resultMap = new HashMap();
			ArrayList secPledgedList = new ArrayList();
			String securityID = null;
			String typeName = null;
			String subTypeName = null;
			while (rs.next()) {
				securityID = rs.getString(CollateralDAO.SCI_SECURITY_ID);
				typeName = rs.getString(CollateralDAO.TYPE_NAME);
				typeName = typeName == null ? "" : typeName;
				subTypeName = rs.getString(CollateralDAO.SUBTYPE_NAME);
				subTypeName = subTypeName == null ? "" : subTypeName;
				if (securityID != null) {
					String[] data = { securityID, typeName, subTypeName };
					secPledgedList.add(data);
				}
			}
			resultMap.put("SECPLEDGED", secPledgedList);
			rs.close();
			return resultMap;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCCDocumentsHeld", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCCDocumentsHeld", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCCDocumentsHeld", ex);
			}
		}
	}

	/**
	 * Constructs the SQL string for securities
	 * 
	 * @return String
	 */
	private String getSecuritiesPledgedSQL() {

		DefaultLogger.info(this, "IN method getSecuritiesPledgedSQL");

		return new StringBuffer().append(SELECT_SEC_PLEDGED).append(" AND ").append(ILimitDAOConstant.LIMIT_TABLE)
				.append(".").append(ILimitDAOConstant.LIMIT_PROFILE_FK).append(" = ? ").append(
						" AND SCI_PLEDGOR_DTL.PLG_PLEDGOR_ID = ?").toString();
	}

	/**
	 * tests for invalid pledgor
	 * @param pledgorID
	 * @return boolean
	 */
	private boolean inValidPledgor(long pledgorID) {
		return pledgorID == ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * tests for invalid limit profile
	 * @param aLimitProfileID
	 * @return boolean
	 */
	private boolean inValidLimitProfile(long aLimitProfileID) {
		return aLimitProfileID == ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Helper method to test for invalid id
	 * @param id aLimitProfileID
	 * @return boolean
	 */
	private boolean isInvalid(long id) {
		return id == ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Constructs the SQL string based on the search criteria Populates the
	 * parameter list for DBUtil to perform JDBC variables binding
	 * @param anICheckListOwner
	 * @param aStatusList
	 * @param params
	 * @return String - constructed SQL
	 */
	private String getStagingSearchString(ICheckListOwner anICheckListOwner, String[] aStatusList, ArrayList params) {
		DefaultLogger.info(this, "IN method getStagingSearchString");

		StringBuffer strBuffer = new StringBuffer();

		if (!CommonUtil.isEmptyArray(aStatusList)) {
			strBuffer.append(" AND ");
			strBuffer.append(ICMSTrxTableConstants.TRX_TBL_NAME);
			strBuffer.append(".");
			strBuffer.append(ICMSTrxTableConstants.TRXTBL_STATUS);
			DefaultLogger.debug(this, ">>>>>>>>> condition part = " + strBuffer);
			CommonUtil.buildSQLInList(aStatusList, strBuffer, params);
			DefaultLogger.debug(this, ">>>>>>>>> condition part 2 = " + strBuffer);
		}

		/*
		 * 9i JDBC if ((aStatusList != null) && (aStatusList.length > 0)) {
		 * strBuffer.append(" AND ");
		 * strBuffer.append(ICMSTrxTableConstants.TRX_TBL_NAME);
		 * strBuffer.append(".");
		 * strBuffer.append(ICMSTrxTableConstants.TRXTBL_STATUS);
		 * 
		 * String statusValues = CommonUtil.arrayToDelimStr(aStatusList);
		 * strBuffer.append(CommonUtil.SQL_STRING_SET);
		 * params.add(statusValues); }
		 */

		if (anICheckListOwner.getLimitProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			strBuffer.append(" AND ");
			strBuffer.append(STAGE_CHKTBL_LIMIT_PROFILE_ID_PREF);
			strBuffer.append(" = ?");
			params.add(new Long(anICheckListOwner.getLimitProfileID()));
		}

		if (anICheckListOwner instanceof ICCCheckListOwner) {
			ICCCheckListOwner ccOwner = (ICCCheckListOwner) anICheckListOwner;
			strBuffer.append(" AND ");
			strBuffer.append(STAGE_CHKTBL_CATEGORY_PREF);
			strBuffer.append(" = '");
			strBuffer.append(ICMSConstant.DOC_TYPE_CC);
			strBuffer.append("'");

			if (ccOwner.getSubOwnerID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				strBuffer.append(" AND ");
				strBuffer.append(STAGE_CHKTBL_SUB_CATEGORY_PREF);
				strBuffer.append(" = ?");
				params.add(ccOwner.getSubOwnerType());

				strBuffer.append(" AND ");
				if ((ccOwner.getSubOwnerType().equals(ICMSConstant.CHECKLIST_MAIN_BORROWER))
						|| (ccOwner.getSubOwnerType().equals(ICMSConstant.CHECKLIST_CO_BORROWER))
						|| (ccOwner.getSubOwnerType().equals(ICMSConstant.CHECKLIST_JOINT_BORROWER))) {
					strBuffer.append(STAGE_CHKTBL_BORROWER_ID_PREF);
					strBuffer.append(" = ?");
					params.add(new Long(ccOwner.getSubOwnerID()));
				}
				else if (ccOwner.getSubOwnerType().equals(ICMSConstant.CHECKLIST_PLEDGER)) {
					strBuffer.append(STAGE_CHKTBL_PLEDGER_ID_PREF);
					strBuffer.append(" = ?");
					params.add(new Long(ccOwner.getSubOwnerID()));
				}
				else if (ccOwner.getSubOwnerType().equals(ICMSConstant.CHECKLIST_NON_BORROWER)) {
					strBuffer.append(STAGE_CHKTBL_BORROWER_ID_PREF);
					strBuffer.append(" = ?");
					params.add(new Long(ccOwner.getSubOwnerID()));
				}
			}
			// DefaultLogger.debug(this,
			// ">>>>>>>>>>>>>>>>>> ccOwner.getSubOwnerType() = " +
			// ccOwner.getSubOwnerType() + "\n strBuffer = " + strBuffer);
			return strBuffer.toString();
		}
		// formulate based on collateral
		ICollateralCheckListOwner colOwner = (ICollateralCheckListOwner) anICheckListOwner;
		//strBuffer.append(" AND ");
		//strBuffer.append(STAGE_CHKTBL_CATEGORY_PREF);
		//strBuffer.append(" = '");
		//strBuffer.append(ICMSConstant.DOC_TYPE_FACILITY);
		//strBuffer.append(ICMSConstant.DOC_TYPE_SECURITY);
		//strBuffer.append("'");
		if (colOwner.getCollateralID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			strBuffer.append(" AND ");
			strBuffer.append(STAGE_CHKTBL_COLLATERAL_ID_PREF);
			strBuffer.append(" = ?");
			params.add(new Long(colOwner.getCollateralID()));
		}
		return strBuffer.toString();
	}

	/**
	 * Get the list of checklist item operation
	 * @return ICheckListItemOperation[] - the list of checklist item
	 * @throws SearchDAOException on errors
	 */
	public ICheckListItemOperation[] getCheckListItemOperation() throws SearchDAOException {
		String sql = SELECT_CHECKLIST_ITEM_OPERATION;
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList operationList = new ArrayList();
			ICheckListItemOperation operation = null;
			while (rs.next()) {
				operation = new OBCheckListItemOperation();
				operation.setState(rs.getString(ICMSTrxTableConstants.TSTTBL_FROM_STATE));
				operation.setOperation(rs.getString(ICMSTrxTableConstants.TSTTBL_OPERATION));
				operationList.add(operation);
			}
			rs.close();
			return (ICheckListItemOperation[]) operationList.toArray(new ICheckListItemOperation[operationList.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
			}
		}
	}

	/**
	 * Utilty method to check if a string value is null or empty
	 * @param aValue - String
	 * @return boolean - true if empty and false otherwise
	 */
	private boolean isEmpty(String aValue) {
		return (aValue != null) && (aValue.trim().length() > 0) ? false : true;
	}

	/**
	 * FOR CR CMS-662 Get all CHECKLIST related to a SECURITY based on
	 * CMS_COLLATERAL_ID.
	 * @param aCollateralID of long type
	 * @return int - the number of checklist related to security.
	 * @throws SearchDAOException on errors
	 */
	public int getSecurityChkListCount(long aCollateralID) throws SearchDAOException {
		StringBuffer strBuf = new StringBuffer();
		/*
		 * strBuf.append("SELECT COUNT(*) FROM "); strBuf.append("CMS_CHECKLIST
		 * CHK "); strBuf.append("WHERE CHK.CMS_COLLATERAL_ID = ");
		 * strBuf.append(aCollateralID); strBuf.append(" "); strBuf.append("AND
		 * CHK.STATUS <> '");
		 * strBuf.append(ICMSConstant.STATE_CHECKLIST_OBSOLETE);
		 * strBuf.append("'"); strBuf.append("AND CHK.CATEGORY = 'S' ");
		 */

		strBuf.append("SELECT COUNT(distinct chk.checklist_id) FROM ");
		strBuf.append("CMS_CHECKLIST CHK, CMS_CHECKLIST_ITEM ITM ");
		strBuf.append("WHERE CHK.CHECKLIST_ID = ITM.CHECKLIST_ID ");
		strBuf.append("AND CHK.CATEGORY = 'S' ");
		strBuf.append("AND CHK.STATUS <> 'OBSOLETE' ");
		strBuf
				.append("AND ((itm.IN_VAULT = 'Y' and itm.CPC_CUST_STATUS <> 'ALLOW_PERM_UPLIFT') OR (itm.IN_VAULT = 'N' and itm.status <> 'DELETED')) ");
		strBuf.append("AND CHK.CMS_COLLATERAL_ID = ?");

		String sql = strBuf.toString();
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, aCollateralID);
			ResultSet rs = dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			rs.close();
			return count;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getSecurityChkListCount", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getSecurityChkListCount", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getSecurityChkListCount", ex);
			}
		}
	}

	/**
	 * FOR CR CMS-310 Get the list of collateral Checklist items that requires
	 * auditing
	 * @param aLimitProfileID of long type
	 * @param asOfDate of String type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 */
	public ICheckListAudit[] getCheckListAudit(long aLimitProfileID, String asOfDate) throws SearchDAOException {
		StringBuffer strBuffer = new StringBuffer();
		if ((aLimitProfileID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)
				&& (asOfDate != null)) {
			// addSql = true;
			strBuffer.append(CHKTBL_LIMIT_PROFILE_ID_PREF);
			strBuffer.append(" = ");
			strBuffer.append(aLimitProfileID);
			strBuffer.append(" AND TRUNC(CMS_CHECKLIST_ITEM.DOC_COMPLETION_DATE) <= ");
			strBuffer.append(DB2DateConverter.getDateTimeString(asOfDate, "dd/MMM/yyyy"));
		}

		String sql = strBuffer.toString();
		if ((sql != null) && (sql.trim().length() > 0)) {
			sql = new StringBuffer().append(SELECT_AUDIT_ITEM).append(" AND ").append(sql).toString();
		}

		sql = new StringBuffer().append(sql).append(ORDER_BY_ITEM_DESC).toString();
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			ArrayList itemList = new ArrayList();
			ICheckListAudit audit = null;
			IAuditItem item = null;
			long prevCheckListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			long curCheckListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			String category = null;
			while (rs.next()) {

				curCheckListID = rs.getLong(CHKTBL_CHECKLISTID);
				if (prevCheckListID != curCheckListID) {
					if (audit != null) {
						if (itemList.size() > 0) {
							audit.setAuditItemList((IAuditItem[]) itemList.toArray(new IAuditItem[0]));
							itemList = new ArrayList();
							resultList.add(audit);
						}
					}
					audit = new OBCheckListAudit();
					audit.setCustomerCategory(rs.getString(CHKTBL_SUB_CATEGORY));
					audit.setCheckListID(curCheckListID);
					prevCheckListID = curCheckListID;
					category = rs.getString(CHKTBL_CATEGORY);
					if ((ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(category))
							|| (ICMSConstant.CHECKLIST_CO_BORROWER.equals(category))) {
						audit.setCustomerID(rs.getLong(CHKTBL_BORROWER_ID));
					}
					else {
						if (ICMSConstant.CHECKLIST_PLEDGER.equals(category)) {
							audit.setCustomerID(rs.getLong(CHKTBL_PLEDGER_ID));
						}
						else {
							audit.setCollateralID(rs.getLong(CHKTBL_COLLATERAL_ID));
						}
					}
				}
				item = new OBAuditItem();
				item.setCheckListItemID(rs.getLong(ITMTBL_DOC_ITEM_NO));
				item.setItemCode(rs.getString(ITMTBL_DOCUMENT_CODE));
				item.setDescription(rs.getString(ITMTBL_DOC_DESCRIPTION));
				item.setDocumentDate(rs.getDate(ITMTBL_DOC_DATE));
				String inVaultInd = rs.getString(ITMTBL_IN_VAULT);
				if (ICMSConstant.TRUE_VALUE.equals(inVaultInd)) {
					item.setIsInVaultInd(true);
				}
				else {
					item.setIsInVaultInd(false);
				}
				itemList.add(item);
			}
			if (audit != null) {
				if (itemList.size() > 0) {
					audit.setAuditItemList((IAuditItem[]) itemList.toArray(new IAuditItem[0]));
					// itemList = new ArrayList();
					resultList.add(audit);
				}
			}
			rs.close();
			return (ICheckListAudit[]) resultList.toArray(new ICheckListAudit[resultList.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListAudit", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListAudit", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckListAudit", ex);
			}
		}
	}

	/**
	 * FOR CR CMS-310 Get the list of Checklist items that requires auditing for
	 * non borrower
	 * @param aCustomerID of long type
	 * @param asOfDate of String type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 */
	public ICheckListAudit[] getCheckListAuditForNonBorrower(long aCustomerID, String asOfDate)
			throws SearchDAOException {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(CHKTBL_SUB_CATEGORY_PREF);
		strBuffer.append(" = '");
		strBuffer.append(ICMSConstant.CHECKLIST_NON_BORROWER);
		strBuffer.append("'");
		if ((aCustomerID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)
				&& (asOfDate != null)) {
			strBuffer.append(" AND ");
			strBuffer.append(CHKTBL_BORROWER_ID_PREF);
			strBuffer.append(" = ");
			strBuffer.append(aCustomerID);
			strBuffer.append(" AND TRUNC(CMS_CHECKLIST_ITEM.DOC_COMPLETION_DATE) <= ");
			strBuffer.append(DB2DateConverter.getDateTimeString(asOfDate, "dd/MMM/yyyy"));
		}

		String sql = strBuffer.toString();
		if ((sql != null) && (sql.trim().length() > 0)) {
			sql = new StringBuffer().append(SELECT_AUDIT_ITEM).append(" AND ").append(sql).toString();
		}

		sql = new StringBuffer().append(sql).append(ORDER_BY_ITEM_DESC).toString();
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			ArrayList itemList = new ArrayList();
			ICheckListAudit audit = null;
			IAuditItem item = null;
			long prevCheckListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			long curCheckListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			while (rs.next()) {
				curCheckListID = rs.getLong(CHKTBL_CHECKLISTID);
				if (prevCheckListID != curCheckListID) {
					if (audit != null) {
						if (itemList.size() > 0) {
							audit.setAuditItemList((IAuditItem[]) itemList.toArray(new IAuditItem[0]));
							itemList = new ArrayList();
							resultList.add(audit);
						}
					}
					audit = new OBCheckListAudit();
					audit.setCustomerCategory(rs.getString(CHKTBL_SUB_CATEGORY));
					audit.setCheckListID(curCheckListID);
					prevCheckListID = curCheckListID;
					// category = rs.getString(CHKTBL_CATEGORY);
					audit.setCustomerID(rs.getLong(CHKTBL_BORROWER_ID));
				}
				item = new OBAuditItem();
				item.setCheckListItemID(rs.getLong(ITMTBL_DOC_ITEM_NO));
				item.setItemCode(rs.getString(ITMTBL_DOCUMENT_CODE));
				item.setDescription(rs.getString(ITMTBL_DOC_DESCRIPTION));
				item.setDocumentDate(rs.getDate(ITMTBL_DOC_DATE));
				String inVaultInd = rs.getString(ITMTBL_IN_VAULT);
				if (ICMSConstant.TRUE_VALUE.equals(inVaultInd)) {
					item.setIsInVaultInd(true);
				}
				else {
					item.setIsInVaultInd(false);
				}
				itemList.add(item);
			}
			if (audit != null) {
				if (itemList.size() > 0) {
					audit.setAuditItemList((IAuditItem[]) itemList.toArray(new IAuditItem[0]));
					// itemList = new ArrayList();
					resultList.add(audit);
				}
			}
			rs.close();
			return (ICheckListAudit[]) resultList.toArray(new ICheckListAudit[resultList.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListAuditForNonBorrower", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListAuditForNonBorrower", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckListAuditForNonBorrower", ex);
			}
		}
	}

	/**
	 * Get the checklist ID of those pledgor checklist that is no longer valid
	 * due to this coborrower
	 * @param aCoBorrowerLegalRef of long type
	 * @param aLimitProfileRef of long type
	 * @return Long[] - the list of checklist IDs of those pledgor checklist
	 *         affected
	 * @throws SearchDAOException on error
	 */
	public Long[] getAffectedPledgorCheckList(long aCoBorrowerLegalRef, long aLimitProfileRef)
			throws SearchDAOException {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("SELECT DISTINCT	CHK.CHECKLIST_ID FROM ");
		strBuf.append("SCI_LSP_LMT_PROFILE PROF, CMS_CHECKLIST CHK, SCI_PLEDGOR_DTL DTL ");
		strBuf.append("WHERE  PROF.CMS_LSP_LMT_PROFILE_ID = CHK.CMS_LSP_LMT_PROFILE_ID ");
		strBuf.append("AND	  CHK.CMS_PLEDGOR_DTL_ID = DTL.CMS_PLEDGOR_DTL_ID ");
		strBuf.append("AND	  CHK.CATEGORY = 'CC'");
		strBuf.append("AND	  CHK.SUB_CATEGORY = 'PLEDGOR' ");
		strBuf.append("AND	  CHK.STATUS NOT IN ('OBSOLETE', 'DELETED') ");
		strBuf.append("AND	  PROF.LLP_ID = ?");
		strBuf.append(" ");
		strBuf.append("AND	   DTL.PLG_LE_ID = ?");
		String sql = strBuf.toString();
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, aLimitProfileRef);
			dbUtil.setLong(2, aCoBorrowerLegalRef);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList result = new ArrayList();
			while (rs.next()) {
				result.add(new Long(rs.getLong("CHECKLIST_ID")));
			}
			rs.close();
			return (Long[]) result.toArray(new Long[result.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getAffectedPledgorCheckList", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getAffectedPledgorCheckList", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getAffectedPledgorCheckList", ex);
			}
		}
	}

	/**
	 * Get the limit profile IDs that are linked to this pledgor
	 * @param aPledgorLegalRef of long type
	 * @return Long[] - the list of limit profile IDs linked to this pledgor
	 * @throws SearchDAOException on error
	 */
	public Long[] getAffectedLimitProfileID(long aPledgorLegalRef) throws SearchDAOException {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("SELECT PROF.CMS_LSP_LMT_PROFILE_ID FROM ");
		strBuf.append("SCI_LSP_LMT_PROFILE PROF ");
		strBuf.append("WHERE  PROF.LLP_LE_ID = ?");
		strBuf.append(" UNION ");
		strBuf.append("SELECT LMT.CMS_LIMIT_PROFILE_ID FROM ");
		strBuf.append("SCI_LSP_APPR_LMTS LMT, SCI_LSP_CO_BORROW_LMT CB ");
		strBuf.append("WHERE  LMT.CMS_LSP_APPR_LMTS_ID = CB.CMS_LIMIT_ID ");
		strBuf.append("AND	   CB.LCL_COBO_LE_ID = ?");
		String sql = strBuf.toString();
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, aPledgorLegalRef);
			dbUtil.setLong(2, aPledgorLegalRef);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList result = new ArrayList();
			while (rs.next()) {
				result.add(new Long(rs.getLong("CMS_LSP_LMT_PROFILE_ID")));
			}
			rs.close();
			return (Long[]) result.toArray(new Long[result.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getAffectedPledgorCheckList", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getAffectedPledgorCheckList", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getAffectedPledgorCheckList", ex);
			}
		}
	}

	/**
	 * Search checklist based on the criteria specified. Currently only used to
	 * search for checklist pending multi-level approval.
	 * 
	 * @param criteria of type CheckListSearchCriteria
	 * @return CheckListSearchResult[]
	 * @throws SearchDAOException on errors encountered searching for checklist
	 */
	public CheckListSearchResult[] searchCheckList(CheckListSearchCriteria criteria) throws SearchDAOException {
		CheckListSearchResult[] resultList = null;
		try {
			ArrayList params = new ArrayList();
			String sql = buildSearchCheckListSQL(criteria, params);
			// DefaultLogger.debug(this, "CHECKLIST SQL: "+sql);
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);
			ResultSet rs = dbUtil.executeQuery();
			resultList = processCheckListResultSet(rs, criteria);
			rs.close();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Exception in searchCheckList", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchCheckList", ex);
			}
		}
		return resultList;
	}

	/**
	 * Constructs the SQL string based on the search criteria Populates the
	 * parameter list for DBUtil to perform JDBC variables binding
	 * @param criteria
	 * @param params
	 * @return String - constructed SQL
	 * @throws CheckListDAO.DAPFilterException
	 */
	private String buildSearchCheckListSQL(CheckListSearchCriteria criteria, ArrayList params)
			throws CheckListDAO.DAPFilterException {

		DefaultLogger.debug(this, "Criteria for searchChecklist : " + criteria);
		StringBuffer buf = new StringBuffer();

		if (criteria.isPendingOfficerApproval()) {
			buf.append(SELECT_CHECKLIST_TRX).append(" AND CMS_CHECKLIST.CATEGORY = ?");
			params.add(criteria.getChecklistType());

			if (criteria.getCheckListID() != ICMSConstant.LONG_INVALID_VALUE) {
				buf.append(" AND CMS_CHECKLIST.CHECKLIST_ID = ?");
				params.add(new Long(criteria.getCheckListID()));

			}
			else if ((criteria.getTrxID() != null) && (criteria.getTrxID().length() != 0)) {
				buf.append(" AND TRANSACTION.CUR_TRX_HISTORY_ID = ?");
				params.add(criteria.getTrxID());
			}
		}

		// DAP conditions
		if (criteria.getTrxContext() != null) {
			ITeam team = criteria.getTrxContext().getTeam();

			String[] countries = team.getCountryCodes();
			String[] organisations = team.getOrganisationCodes();

			if (CommonUtil.isEmptyArray(countries) && CommonUtil.isEmptyArray(countries)) {
				throw new CheckListDAO.DAPFilterException("Country and Organisation List in Team is empty.");
			}

			if (!CommonUtil.isEmptyArray(countries)) {
				buf.append(" AND ( TRANSACTION.TRX_ORIGIN_COUNTRY IS NULL " + "OR TRANSACTION.TRX_ORIGIN_COUNTRY ");
				CommonUtil.buildSQLInList(countries, buf, params);
				buf.append(" )");

			}

			if (!CommonUtil.isEmptyArray(organisations)) {
				buf.append(" AND ( TRANSACTION.TRX_ORIGIN_ORGANISATION IS NULL "
						+ "OR TRANSACTION.TRX_ORIGIN_ORGANISATION  ");
				CommonUtil.buildSQLInList(organisations, buf, params);
				buf.append(" )");

			}

			/*
			 * 9i JDBC
			 * 
			 * String countryList =
			 * CommonUtil.arrayToDelimStr(team.getCountryCodes()); String
			 * orgList =
			 * CommonUtil.arrayToDelimStr(team.getOrganisationCodes());
			 * 
			 * if (isEmpty(countryList) && isEmpty(orgList)) { throw new
			 * CheckListDAO .DAPFilterException("Country and Organisation List
			 * in Team is empty." ); }
			 * 
			 * 
			 * if (!isEmpty(countryList)) { buf.append(" AND (
			 * TRANSACTION.TRX_ORIGIN_COUNTRY IS NULL " + "OR
			 * TRANSACTION.TRX_ORIGIN_COUNTRY ");
			 * buf.append(CommonUtil.SQL_STRING_SET); params.add(countryList);
			 * buf.append(" )"); } if (!isEmpty(orgList)) { buf.append(" AND (
			 * TRANSACTION.TRX_ORIGIN_ORGANISATION IS NULL " + "OR
			 * TRANSACTION.TRX_ORIGIN_ORGANISATION ");
			 * buf.append(CommonUtil.SQL_STRING_SET); params.add(orgList);
			 * buf.append(" )"); }
			 */

		}

		return buf.toString();

	}

	/**
	 * Helper method to process checklist resultset.
	 * 
	 * @param rs of type ResultSet
	 * @param criteria of type CheckListSearchCriteria
	 * @return CheckListSearchResult[]
	 * @throws Exception
	 */
	private CheckListSearchResult[] processCheckListResultSet(ResultSet rs, CheckListSearchCriteria criteria)
			throws Exception {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			if (criteria.isPendingOfficerApproval()) {
				CheckListSearchResult searchResult = createCheckListSearchResult(rs);
				if (searchResult != null) {
					list.add(searchResult);
				}
			}
		}
		return (CheckListSearchResult[]) list.toArray(new CheckListSearchResult[list.size()]);
	}

	/**
	 * Helper method to create the checklist search result.
	 * 
	 * @param rs of type ResultSet
	 * @return
	 * @throws Exception
	 */
	private CheckListSearchResult createCheckListSearchResult(ResultSet rs) throws Exception {

		String category = rs.getString(CHKTBL_CATEGORY);
		String subCategory = rs.getString(CHKTBL_SUB_CATEGORY);

		CheckListSearchResult searchResult = null;
		if (ICMSConstant.DOC_TYPE_CC.equals(category)) {
			CCCheckListSearchResult ccSearchResult = new CCCheckListSearchResult();
			// set cc checklist specific attributes
			ccSearchResult.setCustomerType(subCategory);
			if ((ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(subCategory))
					|| (ICMSConstant.CHECKLIST_CO_BORROWER.equals(subCategory))
					|| (ICMSConstant.CHECKLIST_NON_BORROWER.equals(subCategory))) {
				ccSearchResult.setCustomerID(rs.getLong(CHKTBL_BORROWER_ID));
			}
			else if (ICMSConstant.CHECKLIST_PLEDGER.equals(subCategory)) {
				ccSearchResult.setCustomerID(rs.getLong(CHKTBL_PLEDGER_ID));
			}
			searchResult = ccSearchResult;
		}
		else if (ICMSConstant.DOC_TYPE_SECURITY.equals(category)) {
			CollateralCheckListSearchResult colSearchResult = new CollateralCheckListSearchResult();
			// set collaterla specific attributes
			colSearchResult.setCollateralID(rs.getLong(CHKTBL_COLLATERAL_ID));
			searchResult = colSearchResult;
		}

		// set common attributes
		if (searchResult != null) {
			searchResult.setCheckListID(rs.getLong(CHKTBL_CHECKLISTID));
			searchResult.setLimitProfileID(rs.getLong(CHKTBL_LIMIT_PROFILE_ID));
			searchResult.setCheckListType(category);
			searchResult.setCheckListStatus(rs.getString(CHKTBL_STATUS));
			searchResult.setTrxID(rs.getString(TRXTBL_TRANSACTION_ID));
			searchResult.setTrxStatus(rs.getString(TRXTBL_STATUS));
			IBookingLocation location = new OBBookingLocation(rs.getString(CHKTBL_DOC_LOC_CTRY), rs
					.getString(CHKTBL_DOC_LOC_ORG));
			searchResult.setCheckListLocation(location);
			searchResult.setCheckListOrganization(rs.getString(CHKTBL_DOC_LOC_ORG));
			searchResult.setAllowDeleteInd(ICMSConstant.TRUE_VALUE.equals(rs.getString(CHKTBL_ALLOW_DELETE_IND)));
		}
		return searchResult;
	}

	// inner class
	private class DAPFilterException extends Exception {
		public DAPFilterException(String msg) {
			super(msg);
		}
	}

	private void printDocHeldMap(HashMap map) {
		if (map != null) {
			ArrayList docList = (ArrayList) map.get(ICMSConstant.CHECKLIST_MAIN_BORROWER);
			DefaultLogger.debug("CheckListDAO.printDocHeldMap", ">>>>> MAIN_BORROWER - ["
					+ ((docList == null) ? 0 : docList.size()) + "]");
			printDocHeldList(docList);

			docList = (ArrayList) map.get(ICMSConstant.CHECKLIST_CO_BORROWER);
			DefaultLogger.debug("CheckListDAO.printDocHeldMap", ">>>>> CO_BORROWER - ["
					+ ((docList == null) ? 0 : docList.size()) + "]");

			docList = (ArrayList) map.get(ICMSConstant.CHECKLIST_PLEDGER);
			DefaultLogger.debug("CheckListDAO.printDocHeldMap", ">>>>> PLEDGOR - ["
					+ ((docList == null) ? 0 : docList.size()) + "]");

			docList = (ArrayList) map.get(ICMSConstant.CHECKLIST_NON_BORROWER);
			DefaultLogger.debug("CheckListDAO.printDocHeldMap", ">>>>> NON_BORROWER - ["
					+ ((docList == null) ? 0 : docList.size()) + "]");

			docList = (ArrayList) map.get(ICMSConstant.CHECKLIST_SECURITY);
			DefaultLogger.debug("CheckListDAO.printDocHeldMap", ">>>>> SECURITY - ["
					+ ((docList == null) ? 0 : docList.size()) + "]");
		}
	}

	private void printDocHeldList(ArrayList docList) {
		if ((docList != null) && (docList.size() > 0)) {
			for (java.util.Iterator i = docList.iterator(); i.hasNext();) {
				OBDocumentHeld doc = (OBDocumentHeld) i.next();
				DefaultLogger.debug("CheckListDAO.printDocHeldList", ">>>>> >>>>> doc (" + doc.getCheckListID()
						+ ") - no. of items : [" + ((doc.getDocHeldItems() == null) ? 0 : doc.getDocHeldItems().length)
						+ "]");
			}
		}
	}

	private void printPledgorSecPledgedMap(HashMap secPledgedMap) {
		if (secPledgedMap != null) {
			for (java.util.Iterator mapKeys = secPledgedMap.keySet().iterator(); mapKeys.hasNext();) {
				Long pledgorID = (Long) mapKeys.next();
				DefaultLogger.debug("CheckListDAO.printPledgorSecPledgedMap", ">>>>> >>>>> pledgor ID : " + pledgorID);
				java.util.List secPledgedList = (java.util.List) secPledgedMap.get(pledgorID);
				for (java.util.Iterator listIterator = secPledgedList.iterator(); listIterator.hasNext();) {
					String[] secDetails = (String[]) listIterator.next();
					DefaultLogger.debug("CheckListDAO.printPledgorSecPledgedMap", ">>>>> secPledged : secID : "
							+ secDetails[0] + "     typeName : " + secDetails[1] + "     subTypeName : "
							+ secDetails[2]);
				}

			}
		}
	}

	// Start for cr-17
	public ArrayList getCheckListDetailsByCheckListId(String[] aCheckListId) throws SearchDAOException {
		return getCheckListDetailsByCheckListId(aCheckListId, null);
	}

	public ArrayList getCheckListDetailsByCheckListId(String[] aCheckListId, String category) throws SearchDAOException {
		String sql = constructRetrieveDetailsForShareDocSQL(aCheckListId, category);
		// DefaultLogger.info(this,
		// ">>>>>>>>> getCheckListDetailsByCheckListId (SQL)\n\n " + sql +
		// "\n\n");

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			OBShareDoc objShareDoc = null;
			while (rs.next()) {
				objShareDoc = new OBShareDoc();
				objShareDoc.setCheckListId(rs.getLong("CHECKLIST_ID"));
				objShareDoc.setProfileId(rs.getLong("PROFILE_ID"));
				objShareDoc.setSubProfileId(rs.getLong("SUB_PROFILE_ID"));
				objShareDoc.setPledgorDtlId(rs.getLong("PLEDGOR_DTL_ID"));
				objShareDoc.setCollateralId(rs.getLong("COLLATERAL_ID"));
				objShareDoc.setItemStatus(rs.getString("STATUS"));
				objShareDoc.setLeID(rs.getString("BORROWER_LE_ID"));
				objShareDoc.setLeName(rs.getString("BORROWER_NAME"));
				objShareDoc.setSecurityDtlId(rs.getString("SECURITY_DTL_ID"));
				objShareDoc.setSecurityType(rs.getString("TYPE_VALUE"));
				objShareDoc.setSecuritySubType(rs.getString("SUBTYPE_VALUE"));
				// DefaultLogger.debug(this, ">>>>>>>>>> shareDoc: " +
				// objShareDoc);
				resultList.add(objShareDoc);
			}
			rs.close();
			return resultList;
		}
		catch (SQLException sqlEx) {
			throw new SearchDAOException("SQLException in getCheckListDetailsByCheckListId", sqlEx);
		}
		catch (Exception eX) {
			throw new SearchDAOException("Exception in getCheckListDetailsByCheckListId", eX);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException sqlEx) {
				throw new SearchDAOException("SQLException in getCheckListDetailsByCheckListId", sqlEx);
			}
		}
	}

	private static String constructRetrieveDetailsForShareDocSQL(String[] checkListIds, String category) {

		StringBuffer buf = new StringBuffer("SELECT " + "chk.checklist_id, "
				+ "chk.CMS_LSP_LMT_PROFILE_ID PROFILE_ID, " + "chk.CMS_LMP_SUB_PROFILE_ID SUB_PROFILE_ID, "
				+ "chk.CMS_PLEDGOR_DTL_ID PLEDGOR_DTL_ID, " + "chk.CMS_COLLATERAL_ID COLLATERAL_ID, "
				+ "chk.STATUS STATUS, " + "CASE "
				+ " WHEN chk.sub_category  = 'PLEDGOR' THEN (SELECT min(pledgor.PLG_PLEDGOR_ID) "
				+ "					     FROM  sci_pledgor_dtl pledgor  "
				+ "					     WHERE pledgor.CMS_PLEDGOR_DTL_ID  = chk.cms_pledgor_dtl_id) "
				+ "				       ELSE (SELECT min(sb.lsp_le_id) " + "					     FROM  sci_le_sub_profile sb  "
				+ "					     WHERE sb.cms_le_sub_profile_id  = chk.cms_lmp_sub_profile_id) " + "END borrower_le_id, "
				+ "CASE " + " WHEN chk.sub_category  = 'PLEDGOR' THEN (SELECT min(pledgor.PLG_LEGAL_NAME) "
				+ "					     FROM  sci_pledgor_dtl pledgor  "
				+ "					     WHERE pledgor.CMS_PLEDGOR_DTL_ID  = chk.cms_pledgor_dtl_id) "
				+ "					 ELSE (SELECT min(sb.lsp_short_name) " + "					       FROM  sci_le_sub_profile sb  "
				+ "					       WHERE sb.cms_le_sub_profile_id  = chk.cms_lmp_sub_profile_id) " + "END borrower_name, "
				+ "sec.SCI_SECURITY_DTL_ID SECURITY_DTL_ID, " + "secsubtype.SECURITY_TYPE_NAME TYPE_VALUE, "
				+ "secsubtype.SUBTYPE_NAME SUBTYPE_VALUE " + "FROM ");

		StringBuffer fromTablesSB = new StringBuffer(
				"cms_checklist chk  LEFT OUTER JOIN  cms_security sec  ON  chk.cms_collateral_id  = sec.cms_collateral_id    LEFT OUTER JOIN  CMS_SECURITY_SUB_TYPE secsubtype  ON  sec.SECURITY_SUB_TYPE_ID  = secsubtype.SECURITY_SUB_TYPE_ID    ");

		String checkListIdStr = CommonUtil.arrayToDelimStr(checkListIds);
		StringBuffer conditionSB = new StringBuffer("WHERE chk.status <> 'OBSOLETE' " + " AND chk.checklist_id IN ( "
				+ checkListIdStr + " )");

		if (category != null) {
			conditionSB.append(" AND chk.category = '" + category + "'");
		}

		StringBuffer actualBuf = new StringBuffer();
		return (actualBuf.append(buf).append(fromTablesSB).append(conditionSB)).toString();

	}

	public HashMap getCheckListStatus(Long[] checkListIDList) throws SearchDAOException {
		ResultSet rs =null;
		try {
			StringBuffer sb = new StringBuffer("SELECT CHECKLIST_ID, STATUS FROM CMS_CHECKLIST WHERE CHECKLIST_ID ");
			ArrayList paramList = new ArrayList();

			dbUtil = new DBUtil();
			CommonUtil.buildSQLInList(checkListIDList, sb, paramList);
			// DefaultLogger.debug(this,
			// ">>>>>>>>>>>> getCheckListStatus() SQL:- " + sb.toString());
			dbUtil.setSQL(sb.toString());
			CommonUtil.setSQLParams(paramList, dbUtil);

			rs = dbUtil.executeQuery();
			HashMap checkListIDWithStatus = new HashMap();

			while (rs.next()) {
				Long checkListId = new Long(rs.getLong("CHECKLIST_ID"));
				String status = rs.getString("STATUS");
				checkListIDWithStatus.put(checkListId, status);
			}

			return checkListIDWithStatus;

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("SQLException in getCheckListStatus", e);
		}finally{
			finalize(dbUtil, rs);
		}
	}

	public List getAllShareDocuments(long id, boolean isNonBorrower) throws SearchDAOException {
		ResultSet rs=null;
		try {
			String sql = getViewShareDocSQL(isNonBorrower);
			// DefaultLogger.debug(this,
			// ">>>>>>>>>>>> getCheckListStatus() SQL:- " + sql);

			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, id);
			dbUtil.setLong(2, id);
			rs = dbUtil.executeQuery();

			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				OBShareDocSummary obj = new OBShareDocSummary();
				obj.setDocCode(rs.getString("document_code"));
				obj.setDocNo(rs.getLong("doc_item_ref"));
				obj.setDocDesc(rs.getString("doc_description"));
				obj.setIsMandatory("Y".equals(rs.getString("is_mandatory")));
				obj.setChecklistCategory(rs.getString("category"));
				obj.setDocStatus(rs.getString("status"));
				String item_cpc_cust_status = rs.getString("cpc_cust_status");
				String item_cust_status = null; // rs.getString("custstatus");
				String derived_cust_status = CheckListCustodianHelper.getCheckListCustodianStatus(item_cpc_cust_status,
						item_cust_status);
				obj.setCustStatus(derived_cust_status);
				obj.setDocDate(rs.getDate("doc_date"));
				obj.setDocExpiry(rs.getDate("expiry_date"));
				// obj.setLeID(rs.getLong("le_id"));
				// obj.setLeName(rs.getString("le_name"));
				// obj.setChecklistID(rs.getLong("checklist_id"));
				obj.setShareDetailsSummary(rs.getString("share_details"));
				obj.setNarration(rs.getString("remarks"));
				resultList.add(obj);
			}

			return resultList;

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("SQLException in getAllShareDocuments", e);
		}finally{
			finalize(dbUtil, rs);
		}
	}
	
	public List getAllDeferCreditApprover() throws SearchDAOException {
		ResultSet rs=null;
		try {
			String sql = "select approval_code,approval_name from cms_credit_approval where deferral_powers='Y' and status='ACTIVE'";
			// DefaultLogger.debug(this,
			// ">>>>>>>>>>>> getCheckListStatus() SQL:- " + sql);

			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			
			rs = dbUtil.executeQuery();

			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				OBCreditApproval obj = new OBCreditApproval();
				obj.setApprovalCode(rs.getString("approval_code"));
				obj.setApprovalName(rs.getString("approval_name"));
				
				resultList.add(obj);
			}

			return resultList;

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("SQLException in getAllShareDocuments", e);
		}finally{
			finalize(dbUtil, rs);
		}
	}
	
	public List getAllWaiveCreditApprover() throws SearchDAOException {
		ResultSet rs=null;
		try {
			String sql = "select approval_code,approval_name from cms_credit_approval where waiving_powers='Y' and status='ACTIVE'";
			// DefaultLogger.debug(this,
			// ">>>>>>>>>>>> getCheckListStatus() SQL:- " + sql);

			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			
			rs = dbUtil.executeQuery();

			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				OBCreditApproval obj = new OBCreditApproval();
				obj.setApprovalCode(rs.getString("approval_code"));
				obj.setApprovalName(rs.getString("approval_name"));
				
				resultList.add(obj);
			}

			return resultList;

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("SQLException in getAllShareDocuments", e);
		}finally{
			finalize(dbUtil, rs);
		}
	}

	public List getAllBothCreditApprover() throws SearchDAOException {
		ResultSet rs=null;
		try {
			String sql = "select approval_code,approval_name from cms_credit_approval where  status='ACTIVE' ";
			// DefaultLogger.debug(this,
			// ">>>>>>>>>>>> getCheckListStatus() SQL:- " + sql);

			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			
			rs = dbUtil.executeQuery();

			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				OBCreditApproval obj = new OBCreditApproval();
				obj.setApprovalCode(rs.getString("approval_code"));
				obj.setApprovalName(rs.getString("approval_name"));
				
				resultList.add(obj);
			}

			return resultList;

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("SQLException in getAllShareDocuments", e);
		}finally{
			finalize(dbUtil, rs);
		}
	}


	private String getViewShareDocSQL(boolean isNonBorrower) {
		String shareOutCondition = (isNonBorrower) ? "AND chk.cms_lmp_sub_profile_id = ? "
				: "AND chk.cms_lsp_lmt_profile_id = ? ";
		String shareInCondition = (isNonBorrower) ? "WHERE chk.cms_lmp_sub_profile_id = ?) "
				: "WHERE chk.cms_lsp_lmt_profile_id = ?) ";

		StringBuffer sql = new StringBuffer(
				"SELECT distinct item.document_code, item.doc_item_ref, item.doc_description, item.is_mandatory, ");
		sql.append("chk.category, item.status,  ");
		sql
				.append("item.cpc_cust_status, CASE WHEN custdocitem.STATUS = 'RECEIVED' THEN NULL ELSE custdocitem.STATUS END CUSTSTATUS,  ");
		sql.append("item.doc_date, item.expiry_date,  ");
		// sql.append("CASE WHEN ishare.cms_pledgor_dtl_id <> 0 ");
		// sql.append(
		// "THEN (SELECT pledgor.PLG_PLEDGOR_ID FROM SCI_PLEDGOR_DTL pledgor "
		// );
		// sql.append(
		// "WHERE pledgor.CMS_PLEDGOR_DTL_ID = ishare.cms_pledgor_dtl_id) ");
		// sql.append("ELSE (SELECT lmt.llp_le_id FROM SCI_LSP_LMT_PROFILE lmt
		// ")
		// ;
		// sql.append(
		// "WHERE lmt.cms_lsp_lmt_profile_id = ishare.cms_lsp_lmt_profile_id)
		// END le_id, "
		// );
		// sql.append(
		// "CASE WHEN ishare.cms_pledgor_dtl_id <> 0 THEN (SELECT
		// pledgor.PLG_LEGAL_NAME "
		// );
		// sql.append(
		// "FROM SCI_PLEDGOR_DTL pledgor WHERE pledgor.CMS_PLEDGOR_DTL_ID =
		// ishare.cms_pledgor_dtl_id) "
		// );
		// sql.append(
		// "ELSE (SELECT sb.lsp_short_name FROM SCI_LE_SUB_PROFILE sb,
		// SCI_LSP_LMT_PROFILE lmt "
		// );
		// sql.append(
		// "WHERE lmt.cms_lsp_lmt_profile_id = ishare.cms_lsp_lmt_profile_id ");
		// sql.append(
		// "AND sb.cms_le_sub_profile_id = lmt.cms_customer_id) END le_name,
		// ishare.checklist_id, "
		// );
		sql
		// .append("item.remarks, getShareItemInfoAll(item.doc_item_id,
				// chk.checklist_id, chk.category) share_details ");
				.append("item.remarks, 'share_details' share_details ");
		sql
				.append("FROM CMS_CHECKLIST_ITEM item LEFT OUTER JOIN CMS_CUST_DOC_ITEM custdocitem ON custdocitem.CHECKLIST_ITEM_REF_ID = item.DOC_ITEM_REF, CMS_CHECKLIST chk, CMS_CHECKLIST_ITEM_SHARE ishare ");
		sql.append("WHERE item.checklist_id = chk.checklist_id ");
		sql.append("AND item.doc_item_id = ishare.doc_item_id ");
		sql.append("AND (ishare.status IS NULL OR ishare.status <> 'DELETED') ");
		sql.append("AND item.share_status = 'Y' ");
		sql.append("AND item.is_deleted = 'N' ");
		sql.append(shareOutCondition);

		sql.append("UNION "); // start 'sharing inwards' sql
		sql.append("SELECT item.document_code, item.doc_item_ref, item.doc_description, item.is_mandatory, ");
		sql.append("chk.category, item.status,  ");
		sql
				.append("item.cpc_cust_status, CASE WHEN custdocitem.STATUS = 'RECEIVED' THEN NULL ELSE custdocitem.STATUS END CUSTSTATUS,  ");
		sql.append("item.doc_date, item.expiry_date,  ");
		// sql.append("CASE WHEN chk.sub_category = 'PLEDGOR' ");
		// sql.append(
		// "THEN (SELECT pledgor.PLG_PLEDGOR_ID FROM SCI_PLEDGOR_DTL pledgor "
		// );
		// sql.append(
		// "WHERE pledgor.CMS_PLEDGOR_DTL_ID = chk.cms_pledgor_dtl_id) ");
		// sql.append("ELSE (SELECT lmt.llp_le_id FROM SCI_LSP_LMT_PROFILE lmt
		// ")
		// ;
		// sql.append(
		// "WHERE lmt.cms_lsp_lmt_profile_id = chk.cms_lsp_lmt_profile_id) END
		// le_id, "
		// );
		// sql.append(
		// "CASE WHEN chk.sub_category = 'PLEDGOR' THEN (SELECT
		// pledgor.PLG_LEGAL_NAME "
		// );
		// sql.append(
		// "FROM SCI_PLEDGOR_DTL pledgor WHERE pledgor.CMS_PLEDGOR_DTL_ID =
		// chk.cms_pledgor_dtl_id) "
		// );
		// sql.append(
		// "ELSE (SELECT sb.lsp_short_name FROM SCI_LE_SUB_PROFILE sb,
		// SCI_LSP_LMT_PROFILE lmt "
		// );
		// sql.append(
		// "WHERE lmt.cms_lsp_lmt_profile_id = chk.cms_lsp_lmt_profile_id ");
		// sql.append(
		// "AND sb.cms_le_sub_profile_id = lmt.cms_customer_id) END le_name,
		// chk.checklist_id, "
		// );
		sql
		// .append("item.remarks, getShareItemInfoAll(item.doc_item_id,
				// ishare.checklist_id, chk.category) share_details ");
				.append("item.remarks, 'share_details' share_details ");
		sql
				.append("FROM CMS_CHECKLIST_ITEM item LEFT OUTER JOIN CMS_CUST_DOC_ITEM custdocitem ON custdocitem.CHECKLIST_ITEM_REF_ID = item.DOC_ITEM_REF, CMS_CHECKLIST_ITEM_SHARE ishare, CMS_CHECKLIST chk ");
		sql.append("WHERE item.doc_item_id = ishare.doc_item_id ");
		sql.append("AND item.checklist_id = chk.checklist_id ");
		sql.append("AND (ishare.status IS NULL OR ishare.status <> 'DELETED') ");
		sql.append("AND item.share_status = 'Y' ");
		sql.append("AND item.is_deleted = 'N' ");
		sql.append("AND chk.status <> 'OBSOLETE' ");
		sql.append("AND ishare.checklist_id IN ( ");
		sql.append("SELECT chk.checklist_id FROM CMS_CHECKLIST chk  ");
		sql.append(shareInCondition);

		return sql.toString();
	}

	// Method added by Pratheepa on 29Sep2006 while fixing bug CMS-3479
	public ArrayList getDocumentIdsForCheckList(ArrayList documentIds) throws SearchDAOException {
		DefaultLogger.debug(this, "Inside CheckListDAO" + documentIds);

		ArrayList outputDocumentIds = new ArrayList();

		if (documentIds == null || documentIds.size() == 0)
			return outputDocumentIds;

		Long docsIdList[] = new Long[documentIds.size()];
		for (int p = 0; p < documentIds.size(); p++) {
			Long temp = new Long((String) documentIds.get(p));
			docsIdList[p] = temp;
		}

		String documentId = null;
		ResultSet rs=null;
		try {
			StringBuffer sb = new StringBuffer(
					"SELECT pol.DOCUMENT_NO FROM CMS_INSURANCE_POLICY pol WHERE pol.DOCUMENT_NO ");
			ArrayList paramList = new ArrayList();

			dbUtil = new DBUtil();
			CommonUtil.buildSQLInList(docsIdList, sb, paramList);

			sb.append(" AND pol.STATUS <>'DELETED' AND pol.document_no IS NOT NULL");
			dbUtil.setSQL(sb.toString());
			DefaultLogger.debug(this, ">>>>>>>>>>>> getDocsId() SQL:- " + sb.toString());
			CommonUtil.setSQLParams(paramList, dbUtil);

			rs = dbUtil.executeQuery();

			while (rs.next()) {
				documentId = rs.getString("document_no");
				outputDocumentIds.add(documentId);
			}
		}

		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("SQLException in searchCheckList", e);
		}finally{
			finalize(dbUtil, rs);
		}
		DefaultLogger.debug(this, "Inside CheckListDAOResult" + outputDocumentIds);
		return outputDocumentIds;

	}

	public HashMap getCollateralIdForSharedDocs(long documentNo, Long[] collateralIds) throws SearchDAOException {
		DefaultLogger.debug(this, "Inside CheckListDAO" + documentNo + "CollateralIds" + collateralIds);

		long collateralId = ICMSConstant.LONG_INVALID_VALUE;
		long lmtProfileId = ICMSConstant.LONG_INVALID_VALUE;
		HashMap output = new HashMap();

		String documentId = null;
		ResultSet rs=null;
		try {
			StringBuffer sb = new StringBuffer(
					"SELECT pol.CMS_COLLATERAL_ID, pol.lmt_profile_id FROM CMS_INSURANCE_POLICY pol WHERE pol.STATUS <>'DELETED' and  pol.CMS_COLLATERAL_ID ");
			ArrayList paramList = new ArrayList();

			dbUtil = new DBUtil();
			CommonUtil.buildSQLInList(collateralIds, sb, paramList);

			if (documentNo != ICMSConstant.LONG_INVALID_VALUE) {
				sb.append("AND pol.DOCUMENT_NO=? ");
				paramList.add(new Long(documentNo));
			}

			dbUtil.setSQL(sb.toString());
			DefaultLogger.debug(this, ">>>>>>>>>>>> getCollId() SQL:- " + sb.toString());
			CommonUtil.setSQLParams(paramList, dbUtil);

			rs = dbUtil.executeQuery();

			while (rs.next()) {
				collateralId = rs.getLong("CMS_COLLATERAL_ID");
				lmtProfileId = rs.getLong("lmt_profile_id");

			}
			output.put("collateralId", new Long(collateralId));
			output.put("lmtProfileId", new Long(lmtProfileId));
		}

		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("SQLException in searchCheckList", e);
		}finally{
			finalize(dbUtil, rs);
		}
		DefaultLogger.debug(this, "Inside CheckListDAOResult" + collateralId);
		return output;

	}

	public HashMap getSecuritySubTypes(String secType) throws SearchDAOException {
		ResultSet rs = null;
		Collection subTypeCode = new ArrayList();
		Collection subTypeLabel = new ArrayList();
		HashMap output = new HashMap();
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("select SECURITY_SUB_TYPE_ID, SUBTYPE_NAME from cms_security_sub_type ");
		strBuf.append("where SECURITY_TYPE_ID ='");
		strBuf.append(secType);
		strBuf.append("'");

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(strBuf.toString());
			DefaultLogger.debug(this, ">>>>>>>>>>>> getSecuritySubTypes() SQL:- " + strBuf.toString());
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				subTypeCode.add(rs.getString("SECURITY_SUB_TYPE_ID"));
				subTypeLabel.add(rs.getString("SUBTYPE_NAME"));
			}
			output.put("subTypeLabel", subTypeLabel);
			output.put("subTypeCode", subTypeCode);

		}
		catch (SQLException e) {
			throw new SearchDAOException("Error in getSecuritySubTypes" + e.getClass());
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in getSecuritySubTypes" + e.getClass());
		}
		finally {
			try {
				finalize(dbUtil, rs);
			}
			catch (Exception e) {
				throw new SearchDAOException("Error in finalize" + e.getMessage());
			}
		}
		return output;
	}

	public HashMap getStateDistrictMukim(HashMap inputMap) throws SearchDAOException {
		HashMap result = new HashMap();
		ResultSet rs = null;

		// for state
		List stateValue = new ArrayList();
		List stateLabel = new ArrayList();

		// for district
		List districtValue = new ArrayList();
		List districtLabel = new ArrayList();

		// for mukim
		List mukimValue = new ArrayList();
		List mukimLabel = new ArrayList();

		CollateralSearchCriteria objSearch = (CollateralSearchCriteria) inputMap.get("objSearch");

		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("select distinct PARAMETER_ID, COUNTRY_CODE, STATE_CODE, DISTRICT_CODE, MUKIM_CODE  ");
		strBuffer.append(" from ");
		strBuffer.append(" cms_property_parameters ");
		strBuffer.append(" where 0 = 0 ");
		String COMMON_SQL = strBuffer.toString();
		SQLParameter params = SQLParameter.getInstance();

		StringBuffer strBuf = new StringBuffer();

		try {
			if (!isEmpty(objSearch.getSecurityLoc())) {
				params = SQLParameter.getInstance();
				strBuf = new StringBuffer();
				strBuf.append(COMMON_SQL);
				strBuf.append(" and COUNTRY_CODE =?");
				params.addString(objSearch.getSecurityLoc());
				dbUtil = new DBUtil();
				dbUtil.setSQL(strBuf.toString());
				DefaultLogger.debug(this, ">>>>>>>>>>>> getStateDistrictMukim() STATE :- " + strBuf.toString());
				CommonUtil.setSQLParams(params, dbUtil);
				rs = dbUtil.executeQuery();
				while (rs.next()) {
					if (!stateValue.contains(rs.getString("STATE_CODE"))) {
						stateValue.add(rs.getString("STATE_CODE"));
						stateLabel.add(rs.getString("STATE_CODE"));
					}
				}
					finalize(dbUtil, rs);

			}

			if (!isEmpty(objSearch.getSecurityLoc()) && !isEmpty(objSearch.getStateCD())) {
				params = SQLParameter.getInstance();
				strBuf = new StringBuffer();
				strBuf.append(COMMON_SQL);
				strBuf.append(" and COUNTRY_CODE =?");
				params.addString(objSearch.getSecurityLoc());
				strBuf.append(" and STATE_CODE =?");
				params.addString(objSearch.getStateCD());
				dbUtil = new DBUtil();
				dbUtil.setSQL(strBuf.toString());
				DefaultLogger.debug(this, ">>>>>>>>>>>> getStateDistrictMukim() DISTRICT :- " + strBuf.toString());
				CommonUtil.setSQLParams(params, dbUtil);
				rs = dbUtil.executeQuery();
				while (rs.next()) {
					if (!districtValue.contains(rs.getString("DISTRICT_CODE"))) {
						districtValue.add(rs.getString("DISTRICT_CODE"));
						districtValue.add(rs.getString("DISTRICT_CODE"));
					}
				}
			finalize(dbUtil, rs);
			}

			if (!isEmpty(objSearch.getSecurityLoc()) && !isEmpty(objSearch.getStateCD())
					&& !isEmpty(objSearch.getDistrictCD())) {
				params = SQLParameter.getInstance();
				strBuf = new StringBuffer();
				strBuf.append(COMMON_SQL);
				strBuf.append(" and COUNTRY_CODE =?");
				params.addString(objSearch.getSecurityLoc());
				strBuf.append(" and STATE_CODE =?");
				params.addString(objSearch.getStateCD());
				strBuf.append(" and DISTRICT_CODE =?");
				params.addString(objSearch.getDistrictCD());
				dbUtil = new DBUtil();
				dbUtil.setSQL(strBuf.toString());
				DefaultLogger.debug(this, ">>>>>>>>>>>> getStateDistrictMukim() - MUKIM :- " + strBuf.toString());
				CommonUtil.setSQLParams(params, dbUtil);
				rs = dbUtil.executeQuery();
				while (rs.next()) {
					if (!mukimValue.contains(rs.getString("MUKIM_CODE"))) {
						mukimValue.add(rs.getString("MUKIM_CODE"));
						mukimLabel.add(rs.getString("MUKIM_CODE"));
					}
				}
				finalize(dbUtil, rs);
			}

			result.put("stateValue", stateValue);
			result.put("stateLabel", stateLabel);

			result.put("districtValue", districtValue);
			result.put("districtLabel", districtLabel);

			result.put("mukimValue", mukimValue);
			result.put("mukimLabel", mukimLabel);

		}
		catch (SQLException e) {
			throw new SearchDAOException("Error in getStateDistrictMukim" + e.getClass());
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in getStateDistrictMukim" + e.getClass());
		}
		finally {
			try {
				finalize(dbUtil, rs);
			}
			catch (Exception e) {
				throw new SearchDAOException("Error in finalize" + e.getMessage());
			}
		}

		return result;

	}

	private HashMap processResultSet(ResultSet rs, String listType) throws SQLException {
		HashMap result = new HashMap();

		// for state
		List stateValue = new ArrayList();
		List stateLabel = new ArrayList();

		// for district
		List districtValue = new ArrayList();
		List districtLabel = new ArrayList();

		// for mukim
		List mukimValue = new ArrayList();
		List mukimLabel = new ArrayList();

		while (rs.next()) {
			if ("COUNTRY_CODE".equals(listType)) {
				if (!stateValue.contains(rs.getString("STATE_CODE"))) {
					stateValue.add(rs.getString("STATE_CODE"));
					stateLabel.add(rs.getString("STATE_CODE"));
				}
			}
			if ("STATE_CODE".equals(listType)) {
				if (!stateValue.contains(rs.getString("STATE_CODE"))) {
					stateValue.add(rs.getString("STATE_CODE"));
					stateLabel.add(rs.getString("STATE_CODE"));
				}
				if (!districtValue.contains(rs.getString("DISTRICT_CODE"))) {
					districtValue.add(rs.getString("DISTRICT_CODE"));
					districtLabel.add(rs.getString("DISTRICT_CODE"));
				}
			}
			if ("DISTRICT_CODE".equals(listType)) {
				if (!stateValue.contains(rs.getString("STATE_CODE"))) {
					stateValue.add(rs.getString("STATE_CODE"));
					stateLabel.add(rs.getString("STATE_CODE"));
				}
				if (!districtValue.contains(rs.getString("DISTRICT_CODE"))) {
					districtValue.add(rs.getString("DISTRICT_CODE"));
					districtLabel.add(rs.getString("DISTRICT_CODE"));
				}
				if (!mukimValue.contains(rs.getString("MUKIM_CODE"))) {
					mukimValue.add(rs.getString("MUKIM_CODE"));
					mukimLabel.add(rs.getString("MUKIM_CODE"));
				}
			}
		}

		result.put("stateValue", stateValue);
		result.put("stateLabel", stateLabel);

		result.put("districtValue", districtValue);
		result.put("districtLabel", districtLabel);

		result.put("mukimValue", mukimValue);
		result.put("mukimLabel", mukimLabel);

		return result;
	}

	public HashMap getDocumentationStatus(long lmtProfileID) throws SearchDAOException {
		ResultSet rs = null;
		String docStatus = "-";
		Date docDate = null;
		HashMap output = new HashMap();
//		For Db2
		/*StringBuffer strBuf = new StringBuffer();
		strBuf.append("SELECT STATUS, LAST_DOC_REC_DATE ");
		strBuf.append("FROM cms_checklist ");
		strBuf.append("WHERE CMS_LSP_LMT_PROFILE_ID =? ");
		strBuf.append("order by LAST_DOC_REC_DATE DESC ");
		strBuf.append("FETCH FIRST 1 ROWS ONLY ");*/
//		For Oracle
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("SELECT * FROM (");
		strBuf.append("SELECT STATUS, LAST_DOC_REC_DATE ");
		strBuf.append("FROM cms_checklist ");
		strBuf.append("WHERE CMS_LSP_LMT_PROFILE_ID =? ");
		strBuf.append("order by LAST_DOC_REC_DATE DESC ");
		strBuf.append(" ) TEMP WHERE ROWNUM<=1 ");

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(strBuf.toString());
			dbUtil.setLong(1, lmtProfileID);
			DefaultLogger.debug(this, ">>>>>>>>>>>> getDocumentationStatus() SQL:- " + strBuf.toString());
			rs = dbUtil.executeQuery();
			if (rs.next()) {
				docStatus = rs.getString("STATUS");
				docDate = rs.getDate("LAST_DOC_REC_DATE");
			}
			output.put("docStatus", docStatus);
			output.put("docDate", docDate);

		}
		catch (SQLException e) {
			throw new SearchDAOException("Error in getDocumentationStatus" + e.getClass());
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in getDocumentationStatus" + e.getClass());
		}
		finally {
			try {
				finalize(dbUtil, rs);
			}
			catch (Exception e) {
				throw new SearchDAOException("Error in finalize" + e.getMessage());
			}
		}
		return output;

	}

	public void processCheckList(String limitId) throws Exception {
		String[] result = getCheckList(limitId);
		String limitProfileId = result[0];
		String checkListId = result[1];
		if (checkListId == null) {
			createCheckList(limitProfileId);
		}
		else {
			updateCheckList(limitProfileId);
		}
	}

	private String[] getCheckList(String limitId) throws Exception {
		DBUtil myDBUtil = null;
		ResultSet rs = null;
		String[] result = new String[2];
		String query = "SELECT SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID, CMS_CHECKLIST.CHECKLIST_ID FROM SCI_LSP_APPR_LMTS LEFT OUTER JOIN CMS_CHECKLIST ON SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID = CMS_CHECKLIST.CMS_LSP_LMT_PROFILE_ID WHERE SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = ? ORDER BY CMS_CHECKLIST.LAST_DOC_REC_DATE DESC";
		try {
			myDBUtil = new DBUtil();
			myDBUtil.setSQL(query);
			myDBUtil.setString(1, limitId);
			String checkListId = null;
			String limitProfileId = null;
			rs = myDBUtil.executeQuery();
			if (rs.next()) {
				limitProfileId = rs.getString("CMS_LIMIT_PROFILE_ID");
				checkListId = rs.getString("CHECKLIST_ID");
				result[0] = limitProfileId;
				result[1] = checkListId;
			}
			return result;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			try {
				finalize(myDBUtil, rs);
			}
			catch (Exception ex) {
			}
		}
	}

	private void createCheckList(String limitProfileId) throws Exception {
		DBUtil myDBUtil = null;
		String query = "INSERT INTO CMS_CHECKLIST(CHECKLIST_ID, CMS_LSP_LMT_PROFILE_ID, STATUS, VERSION_TIME, LAST_DOC_REC_DATE) VALUES(?, ?, ?, ?, ?)";
		try {
			myDBUtil = new DBUtil();
			myDBUtil.setSQL(query);
			String checkListId = new SequenceManager().getSeqNum(ICMSConstant.SEQUENCE_CHECKLIST, true);
			myDBUtil.setString(1, checkListId);
			myDBUtil.setString(2, limitProfileId);
			myDBUtil.setString(3, ICMSConstant.DOCUMENTATION_STATUS_COMPLIED);
			long curTime = new java.util.Date().getTime();
			myDBUtil.setLong(4, VersionGenerator.getVersionNumber());
			myDBUtil.setDate(5, new java.sql.Date(curTime));
			myDBUtil.executeUpdate();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			try {
				finalize(myDBUtil, null);
			}
			catch (Exception ex) {
			}
		}
	}

	private void updateCheckList(String limitProfileId) throws Exception {
		DBUtil myDBUtil = null;
		String query = "UPDATE CMS_CHECKLIST SET STATUS = ?, LAST_DOC_REC_DATE = ? WHERE CMS_LSP_LMT_PROFILE_ID = ?";
		try {
			myDBUtil = new DBUtil();
			myDBUtil.setSQL(query);
			myDBUtil.setString(1, ICMSConstant.DOCUMENTATION_STATUS_COMPLIED);
			myDBUtil.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
			myDBUtil.setString(3, limitProfileId);
			myDBUtil.executeUpdate();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			try {
				finalize(myDBUtil, null);
			}
			catch (Exception ex) {
			}

		}
	}

	/**
	 * GET THE getChecklistCompletedStatus
	 * @param collID
	 * @return
	 * @throws SearchDAOException
	 */

	public boolean getChecklistCompletedStatus(long collID) throws SearchDAOException {
		ResultSet rs = null;
		boolean status = false;
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("SELECT chk.status FROM cms_checklist chk, cms_security sec, ");
		strBuf.append(" sci_lsp_appr_lmts lmt, cms_limit_security_map lsm ");
		strBuf.append(" WHERE sec.cms_collateral_id = lsm.cms_collateral_id ");
		strBuf.append(" and lmt.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID ");
		strBuf.append(" and lmt.CMS_LIMIT_PROFILE_ID = chk.cms_lsp_lmt_profile_id ");
		strBuf.append(" and chk.STATUS = ? and sec.cms_collateral_id = ? ");
		strBuf.append(" and chk.cms_collateral_id = sec.cms_collateral_id ");

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(strBuf.toString());
			dbUtil.setString(1, ICMSConstant.STATE_CHECKLIST_COMPLETED);
			dbUtil.setLong(2, collID);
			DefaultLogger.debug(this, ">>>>>>>>>>>> getChecklistCompletedStatus() SQL:- " + strBuf.toString());
			rs = dbUtil.executeQuery();
			if (rs.next()) {
				status = true;
			}
		}
		catch (SQLException e) {
			throw new SearchDAOException("Error in getChecklistCompletedStatus" + e.getClass());
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in getChecklistCompletedStatus" + e.getClass());
		}
		finally {
			try {
				finalize(dbUtil, rs);
			}
			catch (Exception e) {
				throw new SearchDAOException("Error in finalize" + e.getMessage());
			}
		}
		return status;

	}

	/**
	 * // MBB-1281 This validation is applicable for AB - Aircraft, Vessel,
	 * Plant & Equipment Vehicles
	 * @param secSubTypeId
	 * @return int
	 */

	public int getAssetResidualLife(String secSubTypeId) throws SearchDAOException {
		ResultSet rs = null;
		int assetResidualLife = ICMSConstant.INT_INVALID_VALUE;
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("SELECT LIFESPAN FROM CMS_SECURITY_ASSET_LIFE sec " + " where sec.STATUS = '"
				+ ICMSConstant.STATE_ACTIVE + "'" + " and sec.SECURITY_SUB_TYPE_ID = ? ");

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(strBuf.toString());
			dbUtil.setString(1, secSubTypeId);
			DefaultLogger.debug(this, ">>>>>>>>>>>> getAssetResidualLife() SQL:- " + strBuf.toString());
			rs = dbUtil.executeQuery();
			if (rs.next()) {
				assetResidualLife = rs.getInt("LIFESPAN");
			}
		}
		catch (SQLException e) {
			throw new SearchDAOException("Error in getAssetResidualLife" + e.getClass());
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in getAssetResidualLife" + e.getClass());
		}
		finally {
			try {
				finalize(dbUtil, rs);
			}
			catch (Exception e) {
				throw new SearchDAOException("Error in finalize" + e.getMessage());
			}
		}
		return assetResidualLife;

	}

	public HashMap[] getDetailsForPreDisbursementReminderLetter(long limitProfileID) throws SearchDAOException {
		return getDetailsForReminderLetter(PRE_DISBURSEMENT_REMINDER_LETTER, limitProfileID);

	}

	public HashMap[] getDetailsForPostDisbursementReminderLetter(long limitProfileID) throws SearchDAOException {
		return getDetailsForReminderLetter(POST_DISBURSEMENT_REMINDER_LETTER, limitProfileID);
	}

	private HashMap[] getDetailsForReminderLetter(String sql, long limitProfileID) throws SearchDAOException {
		ResultSet rs = null;
		ArrayList list = new ArrayList();

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, limitProfileID);
			dbUtil.setLong(2, limitProfileID);
			DefaultLogger.debug(this, ">>>>>>>>>>>> getDetailsForReminderLetter() SQL:- " + sql);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				String name = rs.getString("LAW_FIRM");
				String addr = rs.getString("LAW_FIRM_ADDRESS");
				HashMap map = new HashMap();
				map.put("SOLICITOR_NAME", name);
				map.put("SOLICITOR_ADDRESS", addr);
				list.add(map);
			}

			return (HashMap[]) list.toArray(new HashMap[0]);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new SearchDAOException("Error in getDetailsForReminderLetter" + e.getClass());
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in getDetailsForReminderLetter" + e.getClass());
		}
		finally {
			try {
				finalize(dbUtil, rs);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new SearchDAOException("Error in finalize" + e.getMessage());
			}
		}
	}

	private void finalize(DBUtil dbUtil, ResultSet rs) throws SearchDAOException {
		try {
			if (null != rs) {
				rs.close();
			}
		}
		catch (Exception e) {
		}
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in cleaning up DB resources!", e);
		}
	}

	
	public ArrayList getMigratedCheckListItem(String limitId,String Category) throws Exception {
		DBUtil myDBUtil = null;
		ResultSet rs = null;
		ArrayList list= new ArrayList();
		StringBuffer strBuf = new StringBuffer();
		String query = "SELECT MIG_DOC_TYPE,MST_DOC_ID,LSS_VERSION,DOCUMENT_NAME FROM CMS_UPLOADED_IMAGES WHERE IMG_ID NOT IN (SELECT IMAGE_ID FROM CMS_IMAGE_TAG_MAP)AND CUST_ID=(SELECT  LLP_LE_ID FROM  SCI_LSP_LMT_PROFILE WHERE  CMS_LSP_LMT_PROFILE_ID=?)AND MIG_DOC_TYPE=? GROUP BY MIG_DOC_TYPE, MST_DOC_ID, LSS_VERSION, DOCUMENT_NAME ORDER BY DOCUMENT_NAME ";
		try {
			myDBUtil = new DBUtil();
			myDBUtil.setSQL(query);
			myDBUtil.setString(1, limitId);
			myDBUtil.setString(2, Category);
			
			rs = myDBUtil.executeQuery();
			while(rs.next()) {
				String[] result = new String[4];
				result[0]  = rs.getString("MIG_DOC_TYPE");
				result[1]  = rs.getString("MST_DOC_ID");
				result[2]  = rs.getString("LSS_VERSION");
				result[3]  = rs.getString("DOCUMENT_NAME");
				
				
				list.add(result);
			}
			return list;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			try {
				finalize(myDBUtil, rs);
			}
			catch (Exception ex) {
			}
		}
	}
	
	
	
	public ArrayList getMigratedCheckListItemImages(String limitId,String Category,String doc_id, String version) throws Exception {
		DBUtil myDBUtil = null;
		ResultSet rs = null;
		myDBUtil = new DBUtil();
		boolean lssVer=false;
		boolean docId=false;
		ArrayList list= new ArrayList();
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("SELECT MIG_DOC_TYPE,MST_DOC_ID,LSS_VERSION,IMG_PATH,IMG_FILENAME,STATUS,DOCUMENT_NAME,HCP_FILENAME,HCP_STATUS FROM CMS_UPLOADED_IMAGES WHERE IMG_ID NOT IN (SELECT IMAGE_ID FROM CMS_IMAGE_TAG_MAP)AND CUST_ID=(SELECT  LLP_LE_ID FROM  SCI_LSP_LMT_PROFILE WHERE  CMS_LSP_LMT_PROFILE_ID=?)AND MIG_DOC_TYPE=?  ");
		if(doc_id.equals("null")){
			strBuf.append("AND MST_DOC_ID is null  ");
			
		}else{
			docId=true;
			strBuf.append("AND MST_DOC_ID=? ");
		}
		if(version.equals("null") ){
			strBuf.append("AND LSS_VERSION is null  ");
			
		}else{
			lssVer=true;
			strBuf.append("AND LSS_VERSION =? ");
		}
		
		strBuf.append(" ORDER BY MIG_DOC_TYPE,IMG_FILENAME ");
		try {
			
			myDBUtil.setSQL(strBuf.toString());
			myDBUtil.setString(1, limitId);
			myDBUtil.setString(2, Category);
			if(docId){
				myDBUtil.setString(3, doc_id);
			}
			if(lssVer){
				myDBUtil.setString(4, version);
			}
			
			
			rs = myDBUtil.executeQuery();
			while(rs.next()) {
				String[] result = new String[8];
				result[0]  = rs.getString("MIG_DOC_TYPE");
				result[1]  = rs.getString("MST_DOC_ID");
				result[2]  = rs.getString("LSS_VERSION");
				result[3]  = rs.getString("IMG_PATH");
				result[4]  = rs.getString("IMG_FILENAME");
				result[5]  = rs.getString("STATUS");
				result[6]  = rs.getString("HCP_FILENAME");
				result[7]  = rs.getString("HCP_STATUS");
				
				
				list.add(result);
			}
			return list;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			try {
				finalize(myDBUtil, rs);
			}
			catch (Exception ex) {
			}
		}
	}


	public boolean getErrorInChecklist(String checklistId) throws Exception {
		DBUtil myDBUtil = null;
		ResultSet rs = null;
		myDBUtil = new DBUtil();
		
		
		String sql = "  select * from ( " 
		+" select checklist_id as id_1 from cms_checklist where category='REC' and  cms_lsp_lmt_profile_id  "
		 +" in (select cms_lsp_lmt_profile_id from sci_lsp_lmt_profile  "
		 +" where llp_le_id in ( select lsp_le_id from sci_le_sub_profile where status='ACTIVE')) "
		 +" and checklist_id not in ( select checklistid_1 from  "
		 +" (select chk.doc_item_id,chk.checklist_id as checklistid_1,doc.doc_desc1,doc.doc_code1,doc.exp_1 from cms_checklist_item chk, "
		 +" (select * from ( select document_description||statement_type as doc_desc1,document_code as doc_code1,expiry_date as exp_1 from cms_document_globallist where category='REC'  "
		 +" and  expiry_date > to_timestamp('31-DEC-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM') "
		 +" and expiry_date < to_timestamp('01-JAN-13 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM')),  "
		 +" ( select document_description||statement_type as doc_desc2,document_code as doc_code2,expiry_date as exp_2 from cms_document_globallist where category='REC'  "
		 +" and  expiry_date > to_timestamp('31-DEC-12 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM') "
		 +" and expiry_date < to_timestamp('01-JAN-14 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM')) where  doc_desc1= doc_desc2) doc "
		 +" where chk.doc_description||chk.statement_type=doc.doc_desc1 and chk.document_code=doc.doc_code1 and chk.expiry_date=doc.exp_1) main_1 "
		 +" ,( select chk.doc_item_id as doc_item_2,chk.checklist_id as checklistid_2,doc.doc_desc2,doc.doc_code2,doc.exp_2 from cms_checklist_item chk, "
		 +" (select * from ( select document_description||statement_type as doc_desc1,document_code as doc_code1,expiry_date as exp_1 from cms_document_globallist where category='REC'  "
		 +" and  expiry_date > to_timestamp('31-DEC-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM') "
		 +" and expiry_date < to_timestamp('01-JAN-13 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM')),  "
		 +" ( select document_description||statement_type as doc_desc2,document_code as doc_code2,expiry_date as exp_2 from cms_document_globallist where category='REC'  "
		 +" and  expiry_date > to_timestamp('31-DEC-12 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM') "
		 +" and expiry_date < to_timestamp('01-JAN-14 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM')) where  doc_desc1= doc_desc2) doc "
		 +" where chk.doc_description||chk.statement_type=doc.doc_desc2 and chk.document_code=doc.doc_code2 and chk.expiry_date=doc.exp_2)main_2  "
		 +" where main_1.doc_desc1=main_2.doc_desc2 and main_1.checklistid_1=main_2.checklistid_2) ) "
			 +" where id_1=? ";
		
		try {
			
			myDBUtil.setSQL(sql);
			myDBUtil.setString(1, checklistId);
			
			boolean value= false;
			
			rs = myDBUtil.executeQuery();
			// if resultset is empty : Checklist is without error
			// else checklist has error.
			if(rs.next()){
				value=true;
				return value;
			}else{
				value=false;
				return value;
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			try {
				finalize(myDBUtil, rs);
			}
			catch (Exception ex) {
			}
		}
	}
	
	
	
	
	public boolean getErrorInChecklistItemDocCode(String checklistId,String documentCode) throws Exception {
		DBUtil myDBUtil = null;
		ResultSet rs = null;
		myDBUtil = new DBUtil();
		
		
		String sql =  "  select * from cms_checklist_item chk , "
			 +" (select * from ( "
			 +" select document_description as doc_desc1, statement_type as stat_1 ,document_code as doc_code1,expiry_date as exp_1 from cms_document_globallist where category='REC'  "
			 +" and  expiry_date "
			 +" > to_timestamp('31-DEC-11 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM') "
			 +" and expiry_date < to_timestamp('01-JAN-13 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM')), "

			 +" ( "
			 +" select document_description as doc_desc2, statement_type as stat_2 ,document_code as doc_code2,expiry_date as exp_2 from cms_document_globallist where category='REC'  "
			 +" and  expiry_date "
			 +" > to_timestamp('31-DEC-12 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM') "
			 +" and expiry_date < to_timestamp('01-JAN-14 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'))  "
			 +" where  doc_desc1= doc_desc2 and stat_1=stat_2 and doc_code1=?) main_1 "
			 +" where chk.document_code=main_1.doc_code2 "
			 +" and chk.checklist_id=? "
			 +" and chk.doc_description=main_1.doc_desc2 "
			 +" and chk.statement_type=main_1.stat_2 "
			 +" and chk.expiry_date=main_1.exp_2 ";
		
		try {
			
			myDBUtil.setSQL(sql);
			myDBUtil.setString(1, documentCode);
			myDBUtil.setString(2, checklistId);
			
			boolean value= false;
			
			rs = myDBUtil.executeQuery();
			// if resultset is empty : Checklist is without error
			// else checklist has error.
			if(rs.next()){
				value=true;
				return value;
			}else{
				value=false;
				return value;
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			try {
				finalize(myDBUtil, rs);
			}
			catch (Exception ex) {
			}
		}
	}
	/*
	 * Added by Abhijit R dated : 25- Apr - 2013
	 * method to update old cam checklist is_latest to 'N'
	 * @see com.integrosys.cms.app.checklist.bus.ICheckListDAO#updateOldCheckLists(long)
	 */
	public void updateOldCheckLists(long limitProfileID) {
		
		DBUtil dbUtil = null;
		
		String sql = "update cms_checklist chk set chk.is_latest='N' where chk.category='CAM' and chk.cms_lsp_lmt_profile_id='"+limitProfileID+"' ";


		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
		
			int  rs = dbUtil.executeUpdate();
			dbUtil.commit();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
	
		
	}
	
	//Method to retrieve multiple checklist id
	//CAM Document CR
	public HashMap getBulkCAMCheckListByCategoryAndProfileID(String category,long collateralID) throws SearchDAOException {

		/*final String SELECT_CHECKLIST_BY_COLLATERAL_ID = "SELECT CMS_CHECKLIST.CHECKLIST_ID"
			+ " FROM CMS_CHECKLIST"
			+ " WHERE ";*/
		
		
		
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_LIMIT_PROFILE_ID);
		strBuf.append(" = ?");
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_CATEGORY_PREF);
		strBuf.append(" = ?");
		strBuf.append("order by(CMS_CHECKLIST.CHECKLIST_ID)desc ");
//		String sql = SELECT_CHECKLIST_BY_COLLATERAL_ID + strBuf.toString();
		String sql = SELECT_CHECKLIST + strBuf.toString();
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, collateralID);
			dbUtil.setString(2, category);
			ResultSet rs = dbUtil.executeQuery();
			/*long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			while (rs.next()) {
				checkListID = rs.getLong(CHKTBL_CHECKLISTID);
							
			}
			rs.close();
			return new Long(checkListID);*/
			HashMap map = new HashMap();
			ArrayList checkListArray= new ArrayList();
			CheckListSearchResult checkList = null;
			while (rs.next()) {
				//collateralID = rs.getLong(CHKTBL_COLLATERAL_ID);
				checkList = new CheckListSearchResult();
				checkList.setCheckListID(rs.getLong(CHKTBL_CHECKLISTID));
				checkList.setCheckListType(rs.getString(CHKTBL_CATEGORY));
				checkList.setCustomerType(rs.getString(CHKTBL_SUB_CATEGORY));
				checkList.setCheckListStatus(rs.getString(CHKTBL_STATUS));
				checkList.setApplicationType(rs.getString(CHKTBL_APPLICATION_TYPE));
				checkList.setTrxID(rs.getString(TRXTBL_TRANSACTION_ID));
				checkList.setTrxStatus(rs.getString("TRX_STATUS"));
				checkList.setTrxFromState(rs.getString(TRXTBL_FROM_STATE));
				IBookingLocation location = new OBBookingLocation(rs.getString(CHKTBL_DOC_LOC_CTRY), rs
						.getString(CHKTBL_DOC_LOC_ORG));
				checkList.setCheckListLocation(location);
				checkList.setCheckListOrganization(rs.getString(CHKTBL_DOC_LOC_ORG));
				if (ICMSConstant.TRUE_VALUE.equals(rs.getString(CHKTBL_ALLOW_DELETE_IND))) {
					checkList.setAllowDeleteInd(true);
				}
				checkList.setDisableTaskInd(rs.getString(CHKTBL_DISABLE_TASK_IND));
				checkList.setCamDate(rs.getDate(CHKTBL_CAMDATE));
				checkList.setCamNumber(rs.getString(CHKTBL_CAMNUMBER));
				checkList.setCamType(rs.getString(CHKTBL_CAMTYPE));
				checkList.setIsLatest(rs.getString(CHKTBL_IS_LATEST));
				checkListArray.add(checkList);
			}
			
			map.put("NORMAL_LIST", checkListArray);
			rs.close();
			return map;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
			}
		}
	}
	
	//Method to search cam document checklist id
	//CAM Document CR
	public HashMap getSearchByCAM(long limitProfileID ,String searchCam) throws SearchDAOException {

		/*final String SELECT_CHECKLIST_BY_COLLATERAL_ID = "SELECT CMS_CHECKLIST.CHECKLIST_ID"
			+ " FROM CMS_CHECKLIST"
			+ " WHERE ";*/
		
		
		
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_LIMIT_PROFILE_ID);
		strBuf.append(" = ?");
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_CAM_NO_PREF);
		strBuf.append(" = ?");
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_CATEGORY_PREF);
		strBuf.append(" = 'CAM'");
		strBuf.append("order by(CMS_CHECKLIST.CHECKLIST_ID)desc ");
		
//		String sql = SELECT_CHECKLIST_BY_COLLATERAL_ID + strBuf.toString();
		String sql = SELECT_CHECKLIST + strBuf.toString();
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, limitProfileID);
			dbUtil.setString(2, searchCam);
			
			ResultSet rs = dbUtil.executeQuery();
			/*long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			while (rs.next()) {
				checkListID = rs.getLong(CHKTBL_CHECKLISTID);
							
			}
			rs.close();
			return new Long(checkListID);*/
			HashMap map = new HashMap();
			ArrayList checkListArray= new ArrayList();
			CheckListSearchResult checkList = null;
			while (rs.next()) {
				//collateralID = rs.getLong(CHKTBL_COLLATERAL_ID);
				checkList = new CheckListSearchResult();
				checkList.setCheckListID(rs.getLong(CHKTBL_CHECKLISTID));
				checkList.setCheckListType(rs.getString(CHKTBL_CATEGORY));
				checkList.setCustomerType(rs.getString(CHKTBL_SUB_CATEGORY));
				checkList.setCheckListStatus(rs.getString(CHKTBL_STATUS));
				checkList.setApplicationType(rs.getString(CHKTBL_APPLICATION_TYPE));
				checkList.setTrxID(rs.getString(TRXTBL_TRANSACTION_ID));
				checkList.setTrxStatus(rs.getString("TRX_STATUS"));
				checkList.setTrxFromState(rs.getString(TRXTBL_FROM_STATE));
				IBookingLocation location = new OBBookingLocation(rs.getString(CHKTBL_DOC_LOC_CTRY), rs
						.getString(CHKTBL_DOC_LOC_ORG));
				checkList.setCheckListLocation(location);
				checkList.setCheckListOrganization(rs.getString(CHKTBL_DOC_LOC_ORG));
				if (ICMSConstant.TRUE_VALUE.equals(rs.getString(CHKTBL_ALLOW_DELETE_IND))) {
					checkList.setAllowDeleteInd(true);
				}
				checkList.setDisableTaskInd(rs.getString(CHKTBL_DISABLE_TASK_IND));
				checkList.setCamDate(rs.getDate(CHKTBL_CAMDATE));
				checkList.setCamNumber(rs.getString(CHKTBL_CAMNUMBER));
				checkList.setCamType(rs.getString(CHKTBL_CAMTYPE));
				checkList.setIsLatest(rs.getString(CHKTBL_IS_LATEST));
				checkListArray.add(checkList);
			}
			
			map.put("NORMAL_LIST", checkListArray);
			rs.close();
			return map;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
			}
		}
	}
	
	public CheckListSearchResult getCAMCheckListByCategoryAndProfileIDMaintain(String category,long collateralID) throws SearchDAOException {

		/*final String SELECT_CHECKLIST_BY_COLLATERAL_ID = "SELECT CMS_CHECKLIST.CHECKLIST_ID"
			+ " FROM CMS_CHECKLIST"
			+ " WHERE ";*/
		
		
		
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_LIMIT_PROFILE_ID);
		strBuf.append(" = ?");
		strBuf.append(" AND ");
		strBuf.append(CHKTBL_CATEGORY_PREF);
		strBuf.append(" = ?");
		strBuf.append("order by(CMS_CHECKLIST.CHECKLIST_ID)desc ");
//		String sql = SELECT_CHECKLIST_BY_COLLATERAL_ID + strBuf.toString();
		String sql = SELECT_CHECKLIST + strBuf.toString();
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, collateralID);
			dbUtil.setString(2, category);
			ResultSet rs = dbUtil.executeQuery();
			/*long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			while (rs.next()) {
				checkListID = rs.getLong(CHKTBL_CHECKLISTID);
							
			}
			rs.close();
			return new Long(checkListID);*/
			HashMap map = new HashMap();
			CheckListSearchResult checkList = null;
			while (rs.next()) {
				//collateralID = rs.getLong(CHKTBL_COLLATERAL_ID);
				checkList = new CheckListSearchResult();
				checkList.setCheckListID(rs.getLong(CHKTBL_CHECKLISTID));
				checkList.setCheckListType(rs.getString(CHKTBL_CATEGORY));
				checkList.setCustomerType(rs.getString(CHKTBL_SUB_CATEGORY));
				checkList.setCheckListStatus(rs.getString(CHKTBL_STATUS));
				checkList.setApplicationType(rs.getString(CHKTBL_APPLICATION_TYPE));
				checkList.setTrxID(rs.getString(TRXTBL_TRANSACTION_ID));
				checkList.setTrxStatus(rs.getString("TRX_STATUS"));
				checkList.setTrxFromState(rs.getString(TRXTBL_FROM_STATE));
				IBookingLocation location = new OBBookingLocation(rs.getString(CHKTBL_DOC_LOC_CTRY), rs
						.getString(CHKTBL_DOC_LOC_ORG));
				checkList.setCheckListLocation(location);
				checkList.setCheckListOrganization(rs.getString(CHKTBL_DOC_LOC_ORG));
				if (ICMSConstant.TRUE_VALUE.equals(rs.getString(CHKTBL_ALLOW_DELETE_IND))) {
					checkList.setAllowDeleteInd(true);
				}
				checkList.setDisableTaskInd(rs.getString(CHKTBL_DISABLE_TASK_IND));
				//map.put(new Long(collateralId), checkList);
				break;
			}
			rs.close();
			return checkList;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
			}
		}
	}
	public ICheckListItem[] searchDoc(long checkListId,String list,String searchType,String search) throws SearchDAOException{
		
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(" AND ");
		strBuf.append(" CHECKLIST_ID ");
		strBuf.append(" = '");
		strBuf.append(checkListId);
		strBuf.append("' ");
		
		if(list!=null && !list.equals("")){
			if(!list.equalsIgnoreCase("INACTIVE")){
			strBuf.append(" AND ");
			strBuf.append(" STATUS ");
			strBuf.append(" ='");
			strBuf.append(list);
			strBuf.append("' ");
			}
			else{
				strBuf.append(" AND ");
				strBuf.append(" DOCUMENTSTATUS ");
				strBuf.append(" ='");
				strBuf.append(list);
				strBuf.append("' ");
			}
		}
		if(searchType!=null && !searchType.equals("")&& search!=null && !search.equals("")){
			strBuf.append(" AND ");
			strBuf.append("UPPER ( " +searchType+ " )");
			strBuf.append(" LIKE UPPER ");
			strBuf.append("('%"+search+"%')");
						
			
			}
		String sql = SEARCH_DOC + strBuf.toString();
		
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			HashMap map = new HashMap();
			ArrayList checkListArray= new ArrayList();
			ICheckListItem checkList =null;
			IItem item = null;
			while (rs.next()) {
				//collateralID = rs.getLong(CHKTBL_COLLATERAL_ID);
				checkList = new OBCheckListItem();
				if(rs.getDate("EXPIRY_DATE")!=null){
				checkList.setExpiryDate(rs.getDate("EXPIRY_DATE"));
				}
				if(rs.getDate("DOC_DATE")!=null){
				checkList.setDocDate(rs.getDate("DOC_DATE"));
				}
				item = new OBItem();
				item.setItemCode(rs.getString("DOCUMENT_CODE"));
				item.setItemDesc(rs.getString("DOC_DESCRIPTION"));
				item.setDocumentVersion(rs.getString("DOCUMENTVERSION"));
				item.setStatus(rs.getString("DOCUMENTSTATUS"));		
				
				checkList.setItem(item);
				/*checkList.setItemDesc(rs.getString("DOC_DESCRIPTION"));
				checkList.setItemCode(rs.getString("DOCUMENT_CODE"));*/
			/*	checkList.setDocumentStatus(rs.getString("DOCUMENTSTATUS"));
				checkList.setDocumentVersion(rs.getString("DOCUMENTVERSION"));*/
				
				if(rs.getDate("ORIGINALTARGETDATE")!=null){
				checkList.setOriginalTargetDate(rs.getDate("ORIGINALTARGETDATE"));
				}
				if(rs.getString("IS_MANDATORY_DISPLAY").equalsIgnoreCase("Y")){
					checkList.setIsMandatoryDisplayInd(true);
				}
				else{
					checkList.setIsMandatoryDisplayInd(false);
				}
				if(rs.getString("IS_MANDATORY").equalsIgnoreCase("Y")){
					checkList.setIsMandatoryInd(true);
				}
				else{
					checkList.setIsMandatoryInd(false);
				}
				
				checkList.setItemStatus(rs.getString("STATUS"));
				checkListArray.add(checkList);
			}
		//	map.put("searchList", checkListArray);
			rs.close();
			return (ICheckListItem[]) checkListArray.toArray(new OBCheckListItem[checkListArray.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
			}
		}
		
		
	}
	
public ICheckListItem[] searchStageDoc(long checkListId,String list,String searchType,String search) throws SearchDAOException{
		
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(" AND ");
		strBuf.append(" CHECKLIST_ID ");
		strBuf.append(" = '");
		strBuf.append(checkListId);
		strBuf.append("' ");
		
		if(list!=null && !list.equals("")){
			if(!list.equalsIgnoreCase("INACTIVE")){
			strBuf.append(" AND ");
			strBuf.append(" STATUS ");
			strBuf.append(" ='");
			strBuf.append(list);
			strBuf.append("' ");
			}
			else{
				strBuf.append(" AND ");
				strBuf.append(" DOCUMENTSTATUS ");
				strBuf.append(" ='");
				strBuf.append(list);
				strBuf.append("' ");
			}
		}
		if(searchType!=null && !searchType.equals("")&& search!=null && !search.equals("")){
			strBuf.append(" AND ");
			strBuf.append("UPPER ( " +searchType+ " )");
			strBuf.append(" LIKE UPPER ");
			strBuf.append("('%"+search+"%')");
						
			
			}
		String sql = SEARCH_STAGE_DOC + strBuf.toString();
		
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			HashMap map = new HashMap();
			ArrayList checkListArray= new ArrayList();
			ICheckListItem checkList =null;
			IItem item = null;
			while (rs.next()) {
				//collateralID = rs.getLong(CHKTBL_COLLATERAL_ID);
				checkList = new OBCheckListItem();
				if(rs.getDate("EXPIRY_DATE")!=null){
				checkList.setExpiryDate(rs.getDate("EXPIRY_DATE"));
				}
				if(rs.getDate("DOC_DATE")!=null){
				checkList.setDocDate(rs.getDate("DOC_DATE"));
				}
				item = new OBItem();
				item.setItemCode(rs.getString("DOCUMENT_CODE"));
				item.setItemDesc(rs.getString("DOC_DESCRIPTION"));
				item.setDocumentVersion(rs.getString("DOCUMENTVERSION"));
				item.setStatus(rs.getString("DOCUMENTSTATUS"));		
				
				checkList.setItem(item);
				/*checkList.setItemDesc(rs.getString("DOC_DESCRIPTION"));
				checkList.setItemCode(rs.getString("DOCUMENT_CODE"));*/
			/*	checkList.setDocumentStatus(rs.getString("DOCUMENTSTATUS"));
				checkList.setDocumentVersion(rs.getString("DOCUMENTVERSION"));*/
				
				if(rs.getDate("ORIGINALTARGETDATE")!=null){
				checkList.setOriginalTargetDate(rs.getDate("ORIGINALTARGETDATE"));
				}
				if(rs.getString("IS_MANDATORY_DISPLAY").equalsIgnoreCase("Y")){
					checkList.setIsMandatoryDisplayInd(true);
				}
				else{
					checkList.setIsMandatoryDisplayInd(false);
				}
				if(rs.getString("IS_MANDATORY").equalsIgnoreCase("Y")){
					checkList.setIsMandatoryInd(true);
				}
				else{
					checkList.setIsMandatoryInd(false);
				}
				
				checkList.setItemStatus(rs.getString("STATUS"));
				checkListArray.add(checkList);
			}
		//	map.put("searchList", checkListArray);
			rs.close();
			return (ICheckListItem[]) checkListArray.toArray(new OBCheckListItem[checkListArray.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
			}
		}
		
		
	}
	
	public long getCollateralIdMap(String custID,String docCode) throws SearchDAOException {
		ResultSet rs = null;
		long collateralId = 0;
		
//		For Db2
		/*StringBuffer strBuf = new StringBuffer();
		strBuf.append("SELECT STATUS, LAST_DOC_REC_DATE ");
		strBuf.append("FROM cms_checklist ");
		strBuf.append("WHERE CMS_LSP_LMT_PROFILE_ID =? ");
		strBuf.append("order by LAST_DOC_REC_DATE DESC ");
		strBuf.append("FETCH FIRST 1 ROWS ONLY ");*/
//		For Oracle
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("select ");
		strBuf.append(" distinct( grnt.cms_collateral_id) as id ");
		strBuf.append(" FROM CMS_LIMIT_SECURITY_MAP lsm, ");
		strBuf.append(" SCI_LSP_APPR_LMTS lmts, ");
		strBuf.append(" SCI_LSP_LMT_PROFILE pf, ");
		strBuf.append(" SCI_LE_SUB_PROFILE sub_profile, ");
		strBuf.append(" CMS_SECURITY secDetail, ");
		strBuf.append(" cms_asset_gc_det grnt ");
		strBuf.append(" where ");
		strBuf.append(" sub_profile.cms_le_sub_profile_id = pf.cms_customer_id ");
		strBuf.append(" AND secdetail.cms_collateral_id = grnt.cms_collateral_id   ");        
		strBuf.append(" AND pf.cms_lsp_lmt_profile_id           = lmts.CMS_LIMIT_PROFILE_ID ");
		strBuf.append(" AND lmts.cms_lsp_appr_lmts_id           = lsm.cms_lsp_appr_lmts_id	 ");				
		strBuf.append(" AND sub_profile.CMS_LE_MAIN_PROFILE_ID  = sub_profile.CMS_LE_MAIN_PROFILE_ID ");
		strBuf.append(" AND secdetail.cms_collateral_id         =LSM.CMS_COLLATERAL_ID ");
		strBuf.append(" AND sub_profile.status != 'INACTIVE' ");
		strBuf.append(" and SUB_PROFILE.LSP_LE_ID=? ");
		strBuf.append(" and grnt.doc_code= ? ");

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(strBuf.toString());
			dbUtil.setString(1, custID);
			dbUtil.setString(2, docCode);
			DefaultLogger.debug(this, ">>>>>>>>>>>> getDocumentationStatus() SQL:- " + strBuf.toString());
			rs = dbUtil.executeQuery();
			if (rs.next()) {
				
				return collateralId = rs.getLong("id");
				
			}
			

		}
		catch (SQLException e) {
			throw new SearchDAOException("Error in getDocumentationStatus" + e.getClass());
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in getDocumentationStatus" + e.getClass());
		}
		finally {
			try {
				finalize(dbUtil, rs);
			}
			catch (Exception e) {
				throw new SearchDAOException("Error in finalize" + e.getMessage());
			}
		}
		return collateralId;

	}
	
	public ILimitProfile retriveCam(String camNo){
		HashMap map = new HashMap();
		ILimitProfile ob=new OBLimitProfile();
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("'"+camNo+"'");		
		strBuf.append("order by(CMS_LSP_LMT_PROFILE_ID)desc ");
		ResultSet rs=null;
//		String sql = SELECT_CHECKLIST_BY_COLLATERAL_ID + strBuf.toString();
		String sql = RETRIVE_CAM + strBuf.toString();
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			//dbUtil.setString(1, camNo);
			rs = dbUtil.executeQuery();
			/*long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			while (rs.next()) {
				checkListID = rs.getLong(CHKTBL_CHECKLISTID);
							
			}
			rs.close();
			return new Long(checkListID);*/
		
			while (rs.next()) {
				ob.setCamType(rs.getString("CAM_TYPE"));
				ob.setNextAnnualReviewDate(rs.getDate("LLP_NEXT_ANNL_RVW_DATE"));
				ob.setApprovalDate(rs.getDate("LLP_BCA_REF_APPR_DATE"));
				/*map.put("camDate", rs.getDate("LLP_BCA_REF_APPR_DATE"));
				map.put("expiryDate", rs.getDate("LLP_NEXT_ANNL_RVW_DATE"));
				map.put("camType", rs.getString("CAM_TYPE"));*/
				break;
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				finalize(dbUtil, rs);
			}
			catch (Exception e) {
				throw new SearchDAOException("Error in finalize" + e.getMessage());
			}
		}
		
		return ob;
		
	}
	
	public List getAllCheckListId(String limitId){
		
		
		String sql = "SELECT checklist_id,  category,  status FROM cms_checklist WHERE cms_lsp_lmt_profile_id="+limitId+" And category is not null and (is_display IS NULL or is_display='Y') AND category not in ('CAM','REC') order BY category";
		
		try {
			dbUtil = new DBUtil();
			
			dbUtil.setSQL(sql);
		
			// DefaultLogger.debug(this, sql);
			ArrayList resultList = new ArrayList();
			ResultSet rs = dbUtil.executeQuery();
			
			
			ICheckList iList=null;
			while (rs.next()) {
				
				iList= new OBCheckList();
				iList.setCheckListID(rs.getLong("checklist_id"));
				iList.setCheckListStatus(rs.getString("status"));
				iList.setCheckListType(rs.getString("category"));
				
				resultList.add(iList);
				
			}
			

			rs.close();
			ICheckList iList1=getCAM(limitId);
			if(iList1!=null ){
				if( null!=iList1.getCheckListType()){
				resultList.add(iList1);
				}
			}
			return resultList;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getAllCheckListId", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getAllCheckListId", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getAllCheckListId", ex);
			}
		}

		}
	
public List getAllCheckListItem(String checklistId){
		
		
		String sql = "SELECT doc_item_id,doc_description,status,documentversion,documentstatus,doc_amt,doc_date,expiry_date FROM cms_checklist_item WHERE checklist_id="+checklistId+" AND status ='RECEIVED' AND documentstatus='ACTIVE'";


		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			ArrayList resultList = new ArrayList();
			ResultSet rs = dbUtil.executeQuery();
			
			ICheckListItem iList=null;
			while (rs.next()) {
				
				iList= new OBCheckListItem();
				iList.setItemDesc(rs.getString("doc_description"));
				iList.setCheckListItemID(rs.getLong("doc_item_id"));
				iList.setItemStatus(rs.getString("status"));
				iList.setDocumentStatus(rs.getString("documentstatus"));
				iList.setDocumentVersion(rs.getString("documentversion"));
				iList.setDocDate(rs.getDate("doc_date"));
				iList.setExpiryDate(rs.getDate("expiry_date"));
				iList.setDocAmt(rs.getString("doc_amt"));
				
				resultList.add(iList);
			}	

			rs.close();
			return resultList;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getAllCheckListItem", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getAllCheckListItem", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
			}
		}

		}

public String getFacilityInfo(long id){
	String sql="select facility_type from sci_lsp_appr_lmts where cms_lsp_appr_lmts_id=(select cms_collateral_id from cms_checklist where checklist_id="+id+")";
	try {
		dbUtil = new DBUtil();
		dbUtil.setSQL(sql);
		// DefaultLogger.debug(this, sql);
		HashMap resultMap = new HashMap();
		ResultSet rs = dbUtil.executeQuery();
		
		ILimit facility=new OBLimit();
		while (rs.next()) {
			
			
			facility.setFacilityType(rs.getString("facility_type"));
		}	

		rs.close();
		return facility.getFacilityType();
	}
	catch (SQLException ex) {
		throw new SearchDAOException("SQLException in getFacilityInfo", ex);
	}
	catch (Exception ex) {
		throw new SearchDAOException("Exception in getFacilityInfo", ex);
	}
	finally {
		try {
			dbUtil.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
		}
	}
}

public String getSecurityInfo(long id){
	String sql="SELECT type_name FROM cms_security WHERE cms_collateral_id=(select cms_collateral_id from cms_checklist where checklist_id="+id+")";
	try {
		dbUtil = new DBUtil();
		dbUtil.setSQL(sql);
		// DefaultLogger.debug(this, sql);
		HashMap resultMap = new HashMap();
		ResultSet rs = dbUtil.executeQuery();
		
		ILimit facility=new OBLimit();
		while (rs.next()) {
			
			
			facility.setFacilityType(rs.getString("type_name"));
		}	

		rs.close();
		return facility.getFacilityType();
	}
	catch (SQLException ex) {
		throw new SearchDAOException("SQLException in getSecurityInfo", ex);
	}
	catch (Exception ex) {
		throw new SearchDAOException("Exception in getSecurityInfo", ex);
	}
	finally {
		try {
			dbUtil.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
		}
	}
}

public List getSearchCheckListId(String limitId, String category){
	
	
	String sql = "SELECT checklist_id,  category,  status FROM cms_checklist WHERE cms_lsp_lmt_profile_id="+limitId+" AND category ='"+category+"' and (is_display IS NULL or is_display='Y')";


	try {
		ArrayList resultList = new ArrayList();
			if(!category.equals("CAM")){
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			
			ResultSet rs = dbUtil.executeQuery();
			
			ICheckList iList=null;
			while (rs.next()) {
				
				iList= new OBCheckList();
				iList.setCheckListID(rs.getLong("checklist_id"));
				iList.setCheckListStatus(rs.getString("status"));
				iList.setCheckListType(rs.getString("category"));
				
				resultList.add(iList);
			}	
	
			rs.close();
		}else{
			ICheckList iList1=getCAM(limitId);
			if(iList1!=null){
				resultList.add(iList1);
			}
		}
		return resultList;
	}
	catch (SQLException ex) {
		throw new SearchDAOException("SQLException in getSearchCheckListId", ex);
	}
	catch (Exception ex) {
		throw new SearchDAOException("Exception in getSearchCheckListId", ex);
	}
	finally {
		try {
			dbUtil.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
		}
	}

	}

public List getSearchCheckListItem(String checklistId,String status,String description){
	
	String sql ="";
	if((status!=null && !status.equals(""))&&(description!=null && !description.equals(""))){
		if(status.equals("INACTIVE")||status.equals("ACTIVE")){
			sql = "SELECT doc_item_id,  doc_description,  status,  documentversion,  documentstatus,  doc_amt,  doc_date,  expiry_date FROM cms_checklist_item WHERE checklist_id="+ checklistId +" AND documentstatus ='"+status+"' AND upper(doc_description) like upper('%"+description+"%')";
		}else{
			sql = "SELECT doc_item_id,  doc_description,  status,  documentversion,  documentstatus,  doc_amt,  doc_date,  expiry_date FROM cms_checklist_item WHERE checklist_id="+ checklistId +" AND status ='"+status+"' AND upper(doc_description) like upper('%"+description+"%')";
		}
	}else if((status!=null && !status.equals(""))&&(description==null || description.equals(""))){
		if(status.equals("INACTIVE")||status.equals("ACTIVE")){
			sql="SELECT doc_item_id,  doc_description,  status,  documentversion,  documentstatus,  doc_amt,  doc_date,  expiry_date FROM cms_checklist_item WHERE checklist_id="+ checklistId+" and documentstatus='"+status+"'";
		}else{
			sql="SELECT doc_item_id,  doc_description,  status,  documentversion,  documentstatus,  doc_amt,  doc_date,  expiry_date FROM cms_checklist_item WHERE checklist_id="+ checklistId+" and status='"+status+"' and documentstatus='ACTIVE'";
		}
		
	}else if((status==null || status.equals(""))&&(description!=null && !description.equals(""))){
		sql="SELECT doc_item_id,  doc_description,  status,  documentversion,  documentstatus,  doc_amt,  doc_date, expiry_date FROM cms_checklist_item WHERE checklist_id="+ checklistId+" AND documentstatus='ACTIVE' AND upper(doc_description) like upper('%"+description+"%')";
	}


	try {
		dbUtil = new DBUtil();
		dbUtil.setSQL(sql);
		// DefaultLogger.debug(this, sql);
		ArrayList resultList = new ArrayList();
		ResultSet rs = dbUtil.executeQuery();
		
		ICheckListItem iList=null;
		while (rs.next()) {
			
			iList= new OBCheckListItem();
			iList.setItemDesc(rs.getString("doc_description"));
			iList.setCheckListItemID(rs.getLong("doc_item_id"));
			iList.setItemStatus(rs.getString("status"));
			iList.setDocumentStatus(rs.getString("documentstatus"));
			iList.setDocumentVersion(rs.getString("documentversion"));
			iList.setDocDate(rs.getDate("doc_date"));
			iList.setExpiryDate(rs.getDate("expiry_date"));
			iList.setDocAmt(rs.getString("doc_amt"));
			
			resultList.add(iList);
		}	

		rs.close();
		return resultList;
	}
	catch (SQLException ex) {
		throw new SearchDAOException("SQLException in getSearchCheckListItem", ex);
	}
	catch (Exception ex) {
		throw new SearchDAOException("Exception in getSearchCheckListItem", ex);
	}
	finally {
		try {
			dbUtil.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
		}
	}

}

public ICheckListItem viewCheckListItem(long id){

	
	String sql ="SELECT document_code,doc_description,doc_amt,status,hdfc_amt,currency,doc_date,expiry_date,received_date,waived_date,documentstatus FROM cms_checklist_item WHERE doc_item_id="+id;
	


	try {
		dbUtil = new DBUtil();
		dbUtil.setSQL(sql);
		// DefaultLogger.debug(this, sql);
		//ArrayList resultList = new ArrayList();
		ResultSet rs = dbUtil.executeQuery();
		
		ICheckListItem iObj=new OBCheckListItem();
		while (rs.next()) {
			iObj.setItemCode(rs.getString("document_code"));
			iObj.setCurrency(rs.getString("currency"));
			iObj.setItemDesc(rs.getString("doc_description"));			
			iObj.setItemStatus(rs.getString("status"));			
			iObj.setDocDate(rs.getDate("doc_date"));
			iObj.setExpiryDate(rs.getDate("expiry_date"));
			iObj.setReceivedDate(rs.getDate("received_date"));
			iObj.setWaivedDate(rs.getDate("waived_date"));
			iObj.setDocAmt(rs.getString("doc_amt"));
			iObj.setHdfcAmt(rs.getString("hdfc_amt"));
			iObj.setDocumentStatus(rs.getString("documentstatus"));
			
			//resultList.add(iList);
		}	

		rs.close();
		return iObj;
	}
	catch (SQLException ex) {
		throw new SearchDAOException("SQLException in getSearchCheckListItem", ex);
	}
	catch (Exception ex) {
		throw new SearchDAOException("Exception in getSearchCheckListItem", ex);
	}
	finally {
		try {
			dbUtil.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
		}
	}


}
public List getAllCheckListStatus(String checklistId){
	
	
	String sql = "SELECT doc_item_id,doc_description,status,documentversion,documentstatus,doc_amt,doc_date,expiry_date FROM cms_checklist_item WHERE checklist_id="+checklistId+" AND documentstatus='ACTIVE'";


	try {
		dbUtil = new DBUtil();
		dbUtil.setSQL(sql);
		// DefaultLogger.debug(this, sql);
		ArrayList resultList = new ArrayList();
		ResultSet rs = dbUtil.executeQuery();
		
		ICheckListItem iList=null;
		while (rs.next()) {
			
			iList= new OBCheckListItem();
			iList.setItemDesc(rs.getString("doc_description"));
			iList.setCheckListItemID(rs.getLong("doc_item_id"));
			iList.setItemStatus(rs.getString("status"));
			iList.setDocumentStatus(rs.getString("documentstatus"));
			iList.setDocumentVersion(rs.getString("documentversion"));
			iList.setDocDate(rs.getDate("doc_date"));
			iList.setExpiryDate(rs.getDate("expiry_date"));
			iList.setDocAmt(rs.getString("doc_amt"));
			
			resultList.add(iList);
		}	

		rs.close();
		return resultList;
	}
	catch (SQLException ex) {
		throw new SearchDAOException("SQLException in getAllCheckListItem", ex);
	}
	catch (Exception ex) {
		throw new SearchDAOException("Exception in getAllCheckListItem", ex);
	}
	finally {
		try {
			dbUtil.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
		}
	}

	}
public ICheckList getCAM(String limitId){
	
	
	
	String sql = "SELECT checklist_id,  category,  status FROM cms_checklist WHERE cms_lsp_lmt_profile_id="+limitId+" AND category IS NOT NULL and (is_display IS NULL or is_display='Y') AND category  in ('CAM') ORDER BY checklist_id desc";

	try {
		dbUtil = new DBUtil();
		
		dbUtil.setSQL(sql);
	
		// DefaultLogger.debug(this, sql);
		//ArrayList resultList = new ArrayList();
		ResultSet rs = dbUtil.executeQuery();
		
		
		ICheckList iList=new OBCheckList();
		while (rs.next()) {
			
			iList.setCheckListID(rs.getLong("checklist_id"));
			iList.setCheckListStatus(rs.getString("status"));
			iList.setCheckListType(rs.getString("category"));
			break;
			
			
		}
		

		rs.close();
		
		return iList;
	}
	catch (SQLException ex) {
		throw new SearchDAOException("SQLException in getAllCheckListId", ex);
	}
	catch (Exception ex) {
		throw new SearchDAOException("Exception in getAllCheckListId", ex);
	}
	finally {
		try {
			dbUtil.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
		}
	}

	}

public ICheckListItem getCheckListItem(long docId){
	
	
	
	String sql = "select documentstatus,documentversion,doc_amt,doc_date,expiry_date from cms_checklist_item where doc_item_id="+docId;

	try {
		dbUtil = new DBUtil();
		
		dbUtil.setSQL(sql);
	
		// DefaultLogger.debug(this, sql);
		//ArrayList resultList = new ArrayList();
		ResultSet rs = dbUtil.executeQuery();
		
		
		ICheckListItem iList=new OBCheckListItem();
		while (rs.next()) {
			
			iList.setDocAmt(rs.getString("doc_amt"));
			iList.setDocumentStatus(rs.getString("documentstatus"));
			iList.setDocumentVersion(rs.getString("documentversion"));		
			iList.setDocDate(rs.getDate("doc_date"));
			iList.setExpiryDate(rs.getDate("expiry_date"));
		}
		

		rs.close();
		
		return iList;
	}
	catch (SQLException ex) {
		throw new SearchDAOException("SQLException in getAllCheckListId", ex);
	}
	catch (Exception ex) {
		throw new SearchDAOException("Exception in getAllCheckListId", ex);
	}
	finally {
		try {
			dbUtil.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
		}
	}

	}

public int updateLAD(long docId,String version,String status,String amount,String docDate,String expiryDate){
	
	
	
	String sql = "update cms_lad_sub_item set doc_version='"+version+"',doc_amount='"+amount+"',document_status='"+status+"',doc_date=to_timestamp('"+docDate+"','dd/Mon/yyyy'),expiry_date=to_timestamp('"+expiryDate+"','dd/Mon/yyyy') where chk_doc_id="+docId;
	if(null==docDate && null==expiryDate){
		sql= "update cms_lad_sub_item set doc_version='"+version+"',doc_amount='"+amount+"',document_status='"+status+"',doc_date=to_timestamp("+docDate+",'dd/Mon/yyyy'),expiry_date=to_timestamp("+expiryDate+",'dd/Mon/yyyy') where chk_doc_id="+docId;	
	}
	else if(null==docDate){
		sql= "update cms_lad_sub_item set doc_version='"+version+"',doc_amount='"+amount+"',document_status='"+status+"',doc_date=to_timestamp("+docDate+",'dd/Mon/yyyy'),expiry_date=to_timestamp('"+expiryDate+"','dd/Mon/yyyy') where chk_doc_id="+docId;
	}
	else if(null==expiryDate){
		sql= "update cms_lad_sub_item set doc_version='"+version+"',doc_amount='"+amount+"',document_status='"+status+"',doc_date=to_timestamp('"+docDate+"','dd/Mon/yyyy'),expiry_date=to_timestamp("+expiryDate+",'dd/Mon/yyyy') where chk_doc_id="+docId;
	}
	DefaultLogger.debug(this, "SQL LAD Update Statement >"+sql);
	try {
		dbUtil = new DBUtil();
		
		dbUtil.setSQL(sql);
	
		// DefaultLogger.debug(this, sql);
		//ArrayList resultList = new ArrayList();
		int update=dbUtil.executeUpdate();
		dbUtil.commit();
		DefaultLogger.debug(this, "SQL Updated Rows >"+update);
		
		
		return update;
	}
	catch (SQLException ex) {
		throw new SearchDAOException("SQLException in getAllCheckListId", ex);
	}
	catch (Exception ex) {
		throw new SearchDAOException("Exception in getAllCheckListId", ex);
	}
	finally {
		try {
			dbUtil.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
		}
	}

	}

	public String getCheckListIdUsingMappingIdAndCamId(String camId, String mappingId,String docCategory)throws SearchDAOException {
		
		StringBuilder SEARCH_CHKLIST_ID_SB = new StringBuilder();	
		
		SEARCH_CHKLIST_ID_SB.append("SELECT cms_checklist.CHECKLIST_ID FROM cms_checklist ");	
		SEARCH_CHKLIST_ID_SB.append(" WHERE ");
		
		if(camId!=null && !camId.trim().isEmpty()){
			SEARCH_CHKLIST_ID_SB.append(" cms_checklist.CMS_LSP_LMT_PROFILE_ID = '"+camId+"'");
		}
		if(mappingId!=null && !mappingId.trim().isEmpty()){
			SEARCH_CHKLIST_ID_SB.append(" AND cms_checklist.CMS_COLLATERAL_ID = '"+mappingId+"'");
		}
		if(docCategory!=null && !docCategory.trim().isEmpty()){
			SEARCH_CHKLIST_ID_SB.append(" AND cms_checklist.CATEGORY = '"+docCategory.trim().toUpperCase()+"'");
		}
		
		ResultSet rs;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SEARCH_CHKLIST_ID_SB.toString());
			rs = dbUtil.executeQuery();
			rs.next();
			String checkListID = rs.getString(1);
			rs.close();
			return checkListID;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListIdUsingMappingIdAndCamId ", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListIdUsingMappingIdAndCamId ", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckListIdUsingMappingIdAndCamId ", ex);
			}
		}
	}
	
	public List<String> getCheckListId(String camId)throws SearchDAOException {
		
		List<String> resultList = new ArrayList<String>();
		ResultSet rs;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SEARCH_CHKLIST.toString());
			dbUtil.setString(1, camId);
			rs = dbUtil.executeQuery();
//			rs.next();
			
			/*ResultSetMetaData metadata = rs.getMetaData();
			int resultCount = metadata.getColumnCount();*/
			
			while(rs.next()){
		    	resultList.add(rs.getString("CHECKLIST_ID"));
			}
			rs.close();
			return resultList;
		}catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListId ", ex);
		}catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListId ", ex);
		}finally {
			try {
				dbUtil.close();
			}catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckListId ", ex);
			}
		}
	}
	public Boolean getChkListByCPSId(String cpsId)throws SearchDAOException {
		
		ResultSet rs;
		Boolean recordFound = false;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SEARCH_CHKLISTITEM_BY_CPSID.toString());
			dbUtil.setString(1, cpsId);
			rs = dbUtil.executeQuery();
			if(rs.next()){
				int count = rs.getInt(1);
				if(count>0){
					recordFound = true;
				}
			}
			rs.close();
			return recordFound;
		}catch (SQLException ex) {
			recordFound = false;
			throw new SearchDAOException("SQLException in getCheckListId ", ex);
		}catch (Exception ex) {
			recordFound = false;
			throw new SearchDAOException("Exception in getCheckListId ", ex);
		}finally {
			try {
				dbUtil.close();
			}catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckListId ", ex);
			}
		}
	}
	public List<OBCheckListItem> getCheckListItemByCheckListId(String checkListId)throws SearchDAOException {
		
		List<OBCheckListItem> obCheckItemList = new ArrayList<OBCheckListItem>();
		ResultSet rs;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SERACH_CHKLIST_ITEM_BY_CHKLIST_ID.toString());
			dbUtil.setString(1, checkListId);
			rs = dbUtil.executeQuery();
//			rs.next();
			
			/*ResultSetMetaData metadata = rs.getMetaData();
			int resultCount = metadata.getColumnCount();*/
			while(rs.next()){
				OBCheckListItem checkListItem = new OBCheckListItem();
				
				checkListItem.setDocumentStatus(rs.getString(1));
				checkListItem.setItemCode(rs.getString(2));
				checkListItem.setItemDesc(rs.getString(3));
				if(rs.getString(4)!= null && !rs.getString(4).isEmpty()){
					checkListItem.setDocAmt(rs.getString(4));
				}
				if(rs.getString(5)!= null && !rs.getString(5).isEmpty()){
					checkListItem.setHdfcAmt(rs.getString(5));
				}
				checkListItem.setCurrency(rs.getString(6));//Currency
				checkListItem.setDocDate(rs.getDate(7));
				checkListItem.setOriginalTargetDate(rs.getDate(8));
				checkListItem.setExpiryDate(rs.getDate(9));
				checkListItem.setRemarks(rs.getString(10));
				checkListItem.setCheckListItemID(rs.getLong(11));
				checkListItem.setDeferExpiryDate(rs.getDate(12));
				checkListItem.setWaivedDate(rs.getDate(13));
				checkListItem.setReceivedDate(rs.getDate(14));
				
				obCheckItemList.add(checkListItem);
			}
			rs.close();
			return obCheckItemList;
		}catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemByCheckListId ", ex);
		}catch (Exception ex) {
			throw new SearchDAOException("Exception in getCheckListItemByCheckListId ", ex);
		}finally {
			try {
				dbUtil.close();
			}catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCheckListItemByCheckListId ", ex);
			}
		}
	}
	
	public Boolean isDocumentFoundInCheckListItem(String camId,String mappingId,
			String docCategory,String documentCode)throws SearchDAOException {
		
		Boolean isRecFound = false;
		ResultSet rs;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SEARCH_DOC_IN_CHKLIST.toString());
			dbUtil.setString(1, camId);
			dbUtil.setString(2, mappingId);
			dbUtil.setString(3, docCategory.trim().toUpperCase());
			dbUtil.setString(4, documentCode);
			rs = dbUtil.executeQuery();
			
			while(rs.next()){
				int recCount = rs.getInt(1);
				if(recCount > 0){
					isRecFound = true;
				}
			}
			rs.close();
		}catch (SQLException ex) {
			throw new SearchDAOException("SQLException in isDocumentFoundInCheckListItem ", ex);
		}catch (Exception ex) {
			throw new SearchDAOException("Exception in isDocumentFoundInCheckListItem ", ex);
		}finally {
			try {
				dbUtil.close();
			}catch (SQLException ex) {
				throw new SearchDAOException("SQLException in isDocumentFoundInCheckListItem ", ex);
			}
		}
		
		return isRecFound;
	}

	  //Uma:Start:Prod issue: To add masterIdlist for facility checklist
	public String getMasterListId(String facilityCode){
		String sql="SELECT DOC_MASTER.MASTERLIST_ID FROM CMS_DOCUMENT_MASTERLIST DOC_MASTER WHERE DOC_MASTER.SECURITY_SUB_TYPE_ID ='"+ facilityCode +"' AND DOC_MASTER.CATEGORY='F'";
		DefaultLogger.debug(this, "sql:"+sql);
		
		ResultSet rs;
		String masterListId=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			while(rs.next()){
				 masterListId = rs.getString(1);
				
			}
			
		} catch (DBConnectionException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in getMasterListId");
		} catch (SQLException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in getMasterListId");
		}catch(Exception e){
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in getMasterListId");
		}finally {
				try {
					dbUtil.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					DefaultLogger.debug(this, "Exception in getMasterListId");
				}
		}
		
		DefaultLogger.debug(this, "getMasterListId completed");
		return masterListId;
		}
	
	
	public void updateCheckListMasterlistId(String masterListId,String apprLimitId){
		String sql="update cms_checklist set masterlist_id='"+masterListId+"'  where category='F' and cms_collateral_id='"+apprLimitId +"' and masterlist_id='0'";
		DefaultLogger.debug(this, "sql:"+sql);
		
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			int executeUpdate = dbUtil.executeUpdate();
			dbUtil.commit();
			DefaultLogger.debug(this, executeUpdate+" rows updated in updateCheckListMasterlistId");
		} catch (DBConnectionException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in updateCheckListMasterlistId");
		} catch (SQLException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in updateCheckListMasterlistId");
		}catch(Exception e){
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in updateCheckListMasterlistId");
		}finally {
			try {
				finalize(dbUtil, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				DefaultLogger.debug(this, "Exception in updateCheckListMasterlistId");
			}
		}
	}
   //Uma:End:Prod issue: To add masterIdlist for facility checklist
	
	public int getCountSecurityDocsInCheckList(long aCollateralID) throws SearchDAOException {
		
		String selectSQL = "select count(1) as count from transaction where transaction_type='CHECKLIST' "+
		" and transaction_subtype in('COL_CHECKLIST_REC') "+
		" AND status <> 'ACTIVE' AND reference_id in (select checklist_id from cms_checklist where "+
		" cms_collateral_id =? and category = 'S')"+
		" AND STAGING_REFERENCE_ID IN(SELECT CHECKLIST_ID FROM STAGE_CHECKLIST_ITEM WHERE STATUS='PENDING_RECEIVED')";

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(selectSQL);
			dbUtil.setLong(1, aCollateralID);
			ResultSet rs = dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			rs.close();
			return count;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCountSecurityDocsInCheckList", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCountSecurityDocsInCheckList", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCountSecurityDocsInCheckList", ex);
			}
		}
	}
	
	//For phase 3 cam online impacted CR
	public int getPendingChecklistCount(long checklistId){
		String sql="SELECT count(1) FROM TRANSACTION  WHERE REFERENCE_ID="+checklistId+" and transaction_type='CHECKLIST' and status!='ACTIVE'";
		DefaultLogger.debug(this, "sql:"+sql);
		
		ResultSet rs;
		int count=0;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			if(rs.next()){
			 count = rs.getInt(1);
			}
			
		} catch (DBConnectionException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in getPendingChecklistCount");
		} catch (SQLException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in getPendingChecklistCount");
		}catch(Exception e){
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in getPendingChecklistCount");
		}finally {
				try {
					dbUtil.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					DefaultLogger.debug(this, "Exception in getPendingChecklistCount");
				}
		}
		
		DefaultLogger.debug(this, "getPendingChecklistCount completed");
		return count;
		}
	
	
	public int getPendingStageChecklistCount(String category, long limitProfileID){
		String sql="SELECT count(1) FROM TRANSACTION  WHERE  transaction_type='CHECKLIST' and status!='ACTIVE' AND STATUS!='CLOSED' and STAGING_REFERENCE_ID in "+ 
		" (select checklist_id from STAGE_CHECKLIST where CATEGORY='"+category+"' and cms_lsp_lmt_profile_id='"+limitProfileID+"')";
		DefaultLogger.debug(this, "sql:"+sql);
		
		ResultSet rs;
		int count=0;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			if(rs.next()){
			 count = rs.getInt(1);
			}
			
		} catch (DBConnectionException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in getPendingStageChecklistCount");
		} catch (SQLException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in getPendingStageChecklistCount");
		}catch(Exception e){
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in getPendingStageChecklistCount");
		}finally {
				try {
					dbUtil.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					DefaultLogger.debug(this, "Exception in getPendingStageChecklistCount");
				}
		}
		
		DefaultLogger.debug(this, "getPendingStageChecklistCount completed");
		return count;
		}
	
	
	 //Uma Khot::Insurance Deferral maintainance
	public String getChecklistId(String Category,long lspLmtProfileId){
		String sql="select checklist_id from CMS_CHECKLIST where category='"+Category+"' and CMS_LSP_LMT_PROFILE_ID ='"+lspLmtProfileId+"'";
		DefaultLogger.debug(this, "sql:"+sql);
		
		ResultSet rs;
		String checkListId=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs=dbUtil.executeQuery();
			while(rs.next()){
				 checkListId = rs.getString(1);
			}
		}catch(Exception e){
		e.printStackTrace();
		DefaultLogger.debug(this, "Exception in getChecklistId");
		}finally {
		try {
			finalize(dbUtil, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			DefaultLogger.debug(this, "Exception in getChecklistId");
			}
		}
		return checkListId;
	}
	
	public String getChecklistIdByCustomerId(String category, long customerID){
		String sql="select checklist_id from CMS_CHECKLIST where category='"+category+"' and CMS_LSP_LMT_PROFILE_ID in (SELECT CMS_LSP_LMT_PROFILE_ID FROM sci_lsp_lmt_profile where cms_customer_id="+customerID+")";
		DefaultLogger.debug(this, "sql:"+sql);
		
		ResultSet rs;
		String checkListId=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs=dbUtil.executeQuery();
			while(rs.next()){
				 checkListId = rs.getString(1);
			}
		}catch(Exception e){
		e.printStackTrace();
		DefaultLogger.debug(this, "Exception in getChecklistId");
		}finally {
		try {
			finalize(dbUtil, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			DefaultLogger.debug(this, "Exception in getChecklistId");
			}
		}
		return checkListId;
	}
	
	public int getOtherChecklistCount(String transactionType,String checklistId){
		String sql="select count(1) from transaction where transaction_type='"+transactionType+"' and status!='ACTIVE' and REFERENCE_ID="+checklistId;
		DefaultLogger.debug(this, "sql:"+sql);
		
		ResultSet rs;
		int count=0;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs=dbUtil.executeQuery();
			while(rs.next()){
				count = rs.getInt(1);
			}
		}catch(Exception e){
		e.printStackTrace();
		DefaultLogger.debug(this, "Exception in getOtherChecklistCount");
		}finally {
		try {
			finalize(dbUtil, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			DefaultLogger.debug(this, "Exception in getOtherChecklistCount");
			}
		}
		return count;
	}
	
	public String getChecklistIdByLimitId(String category, long limitID){
		String sql="select checklist_id from CMS_CHECKLIST where category='"+category+"' and CMS_LSP_LMT_PROFILE_ID ='"+limitID+"'";
		DefaultLogger.debug(this, "sql:"+sql);
		
		ResultSet rs;
		String checkListId=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs=dbUtil.executeQuery();
			while(rs.next()){
				 checkListId = rs.getString(1);
			}
		}catch(Exception e){
		e.printStackTrace();
		DefaultLogger.debug(this, "Exception in getChecklistId");
		}finally {
		try {
			finalize(dbUtil, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			DefaultLogger.debug(this, "Exception in getChecklistId");
			}
		}
		return checkListId;
	}
	

	public List getAllRamratingDocumentlist(ICriInfo[] criList) {
		 String sql = "select * from cms_document_globallist where statement_type='RAM_RATING' and document_code='DOC20180921000002084'";
		 String sql1= "select * from cms_document_globallist where statement_type='RAM_RATING' and document_code='DOC20180921000002085'";
		try {
			 
			dbUtil = new DBUtil();
			int i =0;
			if(criList[i].getCustomerFyClouser().equals("December Ending"))
			{
				dbUtil.setSQL(sql);
			}
			if(criList[i].getCustomerFyClouser().equals("March Ending"))
			{
				dbUtil.setSQL(sql1);
			}
			ResultSet rs = dbUtil.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
		    int columnCount = md.getColumnCount();
			List resultList = null ;
			while(rs.next()){
				int j = 1;
				   while(j <= columnCount) {
				        resultList.add(rs.getString(j++));
			}
			}
 			return resultList;
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getAllRamratingDocumentlist", ex);
			}
		}
		return null;
	}

	@Override
	public void updateChecklist(String docId,String checkListId, long limitProfileID,String docRefId) {
		String sql = "INSERT INTO CMS_CHECKLIST_ITEM (DOC_ITEM_ID,DOC_DESCRIPTION,IS_PRE_APPROVE,IS_INHERITED,IN_VAULT,IN_EXT_CUSTODY,IS_MANDATORY,STATUS,CHECKLIST_ID,DOCUMENT_ID,DOCUMENT_CODE,DOC_ITEM_REF,STATEMENT_TYPE,DOCUMENTSTATUS)" 
			+"VALUES ("+docId+",'Ram rating December Ending','N','N','N','N','N','RECEIVED',"+checkListId+",'-999999999','DOC20180921000002084',"+docRefId+",'RAM_RATING','ACTIVE' )";
		
	try {
		dbUtil = new DBUtil();
		dbUtil.setSQL(sql);
		dbUtil.executeUpdate();
		dbUtil.commit();
	} catch (Exception ex) {
		ex.printStackTrace();
	}

		
	}

	@Override
	public String getDocSeqId() {
		String sql = "select CHECKLIST_ITEM_SEQ.nextval S from dual";
		ResultSet rs;
		Long count=0l;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs=dbUtil.executeQuery();
			while(rs.next()){
				count = rs.getLong(1);
			}
		}catch(Exception e){
		e.printStackTrace();
		DefaultLogger.debug(this, "Exception in getOtherChecklistCount");
		}finally {
		try {
			finalize(dbUtil, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			DefaultLogger.debug(this, "Exception in getOtherChecklistCount");
			}
		}
		return String.valueOf(count);
		
	}

	@Override
	public void updateChecklistMarchEnding(String docId, String checkListId,
			long limitProfileID,String docRefId) {
		String sql = "INSERT INTO CMS_CHECKLIST_ITEM (DOC_ITEM_ID,DOC_DESCRIPTION,IS_PRE_APPROVE,IS_INHERITED,IN_VAULT,IN_EXT_CUSTODY,IS_MANDATORY,STATUS,CHECKLIST_ID,DOCUMENT_ID,DOCUMENT_CODE,DOC_ITEM_REF,STATEMENT_TYPE,DOCUMENTSTATUS)" 
			+"VALUES ("+docId+",'Ram rating March Ending','N','N','N','N','N','RECEIVED',"+checkListId+",'-999999999','DOC20180921000002085',"+docRefId+",'RAM_RATING','ACTIVE' )";
		
     try {
     dbUtil = new DBUtil();
     dbUtil.setSQL(sql);
     dbUtil.executeUpdate();
     dbUtil.commit();
     } catch (Exception ex) {
      ex.printStackTrace();
     }
	}

	@Override
	public void updateChecklistDetails(String checkListId) {
		String sql="update  cms_checklist set category='REC' where checklist_id='"+checkListId+"'";
		 try {
		     dbUtil = new DBUtil();
		     dbUtil.setSQL(sql);
		     dbUtil.executeUpdate();
		     dbUtil.commit();
		     } catch (Exception ex) {
		      ex.printStackTrace();
		     }
	}
	
	public boolean getRamRatingDocument(long limitProfileID) {
		ResultSet rs = null;
		String sql="select * from cms_checklist_item where statement_type='RAM_RATING' " +
				" and checklist_id in(select checklist_id from cms_checklist where cms_lsp_lmt_profile_id='"+limitProfileID+"')";
       try {
    	   dbUtil = new DBUtil();	
	       dbUtil.setSQL(sql);
			boolean value= false;
			
			rs = dbUtil.executeQuery();
			// if resultset is empty : Checklist is without error
			// else checklist has error.
			if(rs.next()){
				value=true;
				return value;
			}else{
				value=false;
				return value;
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			
		}
	return false;
		
	}

	@Override
	public boolean isRamDocAlreadyExist(long limitProfileID) {
		ResultSet rs = null;
		String sql="select * from cms_checklist_item where statement_type='RAM_RATING' " +
				" and checklist_id in(select checklist_id from cms_checklist where cms_lsp_lmt_profile_id='"+limitProfileID+"' and category='REC')";
       try {
    	   dbUtil = new DBUtil();	
	       dbUtil.setSQL(sql);
			boolean value= false;
			
			rs = dbUtil.executeQuery();
			// if resultset is empty : Checklist is without error
			// else checklist has error.
			if(rs.next()){
				value=true;
				return value;
			}else{
				value=false;
				return value;
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			
		}
	return false;
		
	}
	
	public String getReferenceId(String trxId){
		String sql="select reference_id from transaction where transaction_id="+trxId;
		DefaultLogger.debug(this, "sql:"+sql);
		
		ResultSet rs;
		String referenceId=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			while(rs.next()){
				referenceId = rs.getString(1);
			}
			
		} catch (DBConnectionException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in getReferenceId");
		} catch (SQLException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in getReferenceId");
		}catch(Exception e){
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in getReferenceId");
		}finally {
				try {
					dbUtil.close();
				} catch (SQLException e) {
					DefaultLogger.debug(this, "Exception in getReferenceId");
				}
		}
		
		DefaultLogger.debug(this, "getReferenceId completed");
		return referenceId;
		}
	
	public String getOtherChecklistCountPartyName(String transactionType,String checklistId){
		String sql="select LEGAL_NAME from transaction where transaction_type='"+transactionType+"' and status!='ACTIVE' and REFERENCE_ID="+checklistId;
		DefaultLogger.debug(this, "getOtherChecklistCountPartyName=>sql:"+sql);
		
		ResultSet rs;
		String partyNames="";
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs=dbUtil.executeQuery();
			while(rs.next()){
				partyNames = rs.getString(1);
			}
		}catch(Exception e){
		e.printStackTrace();
		DefaultLogger.debug(this, "Exception in getOtherChecklistCountPartyName=>1) e=>"+e);
		}finally {
		try {
			finalize(dbUtil, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			DefaultLogger.debug(this, "Exception in getOtherChecklistCountPartyName=>2) e=>"+e);
			}
		}
		return partyNames;
	}
	
	public String getDocumentCount(){
		String sql="select COUNT(*) AS CNT from CMS_DOCUMENT_GLOBALLIST ";
		System.out.println("QuerygetDocumentCount()=>sql Query :"+sql);
		
		ResultSet rs=null;
		String docCount=null;
		DBUtil dbUtil1 = null;
		try {
			dbUtil1 = new DBUtil();
			dbUtil1.setSQL(sql);
			rs = dbUtil1.executeQuery();
			System.out.println("String getDocumentCount()=>Line 8307..");
			while(rs.next()){
				docCount = rs.getString(1);
			}
			System.out.println("public String getDocumentCount()=>docCount=>"+docCount);
		} catch (DBConnectionException e) {
			e.printStackTrace();
			System.out.println("Exception in getDocumentCount Line 8313=>e=>"+e);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Exception in getDocumentCount Line 8316=>e=>"+e);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Exception in getDocumentCount Line 8319=>e=>"+e);
		}finally {
				try {
					if(rs != null) {
						rs.close();
					}
					if(dbUtil1 != null) {
						dbUtil1.close();
					}
					
				} catch (SQLException e) {
					System.out.println("Exception in getDocumentCount");
				}
		}
		
		DefaultLogger.debug(this, "getDocumentCount completed");
		return docCount;
		}

}
