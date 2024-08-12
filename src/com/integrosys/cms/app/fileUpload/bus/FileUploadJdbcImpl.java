package com.integrosys.cms.app.fileUpload.bus;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.common.bus.PaginationUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyDAO;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyFacilityList;
import com.integrosys.cms.app.discrepency.bus.NoSuchDiscrepencyException;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.app.discrepency.proxy.IDiscrepencyProxyManager;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.discrepency.trx.OBDiscrepencyTrxValue;
import com.integrosys.cms.app.eod.bus.IEODStatus;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.OBGeneralParamEntry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.LimitListSecItemBase;
import com.integrosys.cms.app.limit.bus.LimitListSummaryItemBase;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.relationshipmgr.bus.OBHRMSData;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.eod.EODConstants;
import com.integrosys.cms.batch.partycam.IPartyCamErrDetLog;
import com.integrosys.cms.batch.partycam.IPartyCamErrorLog;
import com.integrosys.cms.batch.partycam.OBPartyCamErrDetLog;
import com.integrosys.cms.batch.partycam.OBPartyCamErrorLog;
import com.integrosys.cms.batch.ramRatingDetails.schedular.RamRatingDetails;
import com.integrosys.cms.batch.ubs.IUbsErrDetLog;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;
import com.integrosys.cms.batch.ubs.OBUbsErrDetLog;
import com.integrosys.cms.batch.ubs.OBUbsErrorLog;
import com.integrosys.cms.ui.bulkudfupdateupload.OBTempBulkUDFFileUpload;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGCJdbc;
import com.integrosys.cms.ui.createfacupload.OBCreatefacilitylineFile;
import com.integrosys.cms.ui.fileUpload.CheckerApproveFileUploadCmd;
import com.integrosys.cms.ui.fileUpload.UploadAcknowledgmentFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadBahrainFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadFacilitydetailsFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadFdFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadHongKongFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadPartyCamFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadReleaselinedetailsFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadUbsFileCmd;
import com.integrosys.cms.ui.fileUpload.UploadfinWareFileCmd;
import com.integrosys.cms.ui.partycamupload.CheckerApprovePartyCamFileUploadCmd;
import com.integrosys.component.user.app.bus.ICommonUser;

public class FileUploadJdbcImpl extends JdbcDaoSupport implements IFileUploadJdbc{
	
	// Added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION 
	private String  spUploadTransaction;
	private String  spUpdateReleasedAmount;
private IDiscrepencyProxyManager discrepencyProxy;
	
	public IDiscrepencyProxyManager getDiscrepencyProxy() {
		return discrepencyProxy;
	}

	public void setDiscrepencyProxy(IDiscrepencyProxyManager discrepencyProxy) {
		this.discrepencyProxy = discrepencyProxy;
	}
	
	
	private String uploadFileCleanup;

	private static final String CHECK_UBS_UPLOAD_TRX=" SELECT count(1) FROM transaction WHERE transaction_type ='FILEUPLOAD' " +
			"AND transaction_subtype='";


	private static final String STAGE_UBS_FILE= "select ID,FILE_ID,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY,LIMIT_AMOUTNT,UTILIZATION_AMOUNT," +
			"REASON,STATUS,UPLOAD_STATUS from stage_ubs_file_upload where file_id='";

	private static final String ACTUAL_UBS_FILE= "select ID,FILE_ID,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY,LIMIT_AMOUTNT,UTILIZATION_AMOUNT," +
			"REASON,STATUS,UPLOAD_STATUS from cms_ubs_file_upload where file_id='";
	
	private static final String STAGE_TEMP_BULKUDF_FILE=" SELECT ID,FILE_ID, TYPE_OF_UDF, PARTY_ID,CAM_NO,SYSTEM_ID,LINE_NO,SERIAL_NO,LIAB_BRANCH,\r\n" + 
			"    		UDF_FIELD_SEQUENCE,UDF_FIELD_NAME,UDF_FIELD_VALUE,VALID,REMARKS,REASON, STATUS,UPLOAD_STATUS,\r\n" + 
			"    		CMS_LE_MAIN_PROFILE_ID,CMS_LSP_LMT_PROFILE_ID,SCI_LSP_SYS_XREF_ID FROM TEMP_BULK_UDF_UPLOAD where FILE_ID='";
	
	private static final String STAGE_PARTYCAM_FILE= "select ID,FILE_ID,PARTY_ID,CAM_DATE,CAM_LOGIN_DATE,RAM_RATING,RAM_RATING_YEAR," +
			"CUSTOMER_RAM_ID,CAM_EXPIRY_DATE,CAM_EXTENSION_DATE,REASON,RATING_TYPE,RAM_RATING_FINALIZATION_DATE,RM_EMPLOYEE_CODE,STATUS,UPLOAD_STATUS,TOTAL_FUNDED_LIMIT,TOTAL_NON_FUNDED_LIMIT FROM STAGE_PARTYCAM_FILE_UPLOAD WHERE FILE_ID='";
			
	private static final String ACTUAL_PARTYCAM_FILE= "select ID,FILE_ID,PARTY_ID,CAM_DATE,CAM_LOGIN_DATE,RAM_RATING,RAM_RATING_YEAR," +
			"CUSTOMER_RAM_ID,CAM_EXPIRY_DATE,CAM_EXTENSION_DATE,REASON,RATING_TYPE,RAM_RATING_FINALIZATION_DATE,STATUS,UPLOAD_STATUS,TOTAL_FUNDED_LIMIT FROM CMS_UBS_FILE_UPLOAD WHERE FILE_ID='";

	private static final String STAGE_LEIDETAILS_FILE= "select ID, FILE_ID, PARTY_ID, LEI_CODE, LEI_EXP_DATE, STATUS,REASON,UPLOAD_STATUS from STAGE_LEI_DETAILS_UPLOAD WHERE FILE_ID='";

	private static final String ACTUAL_LEIDETAILS_FILE= "select ID, FILE_ID, PARTY_ID, LEI_CODE, LEI_EXP_DATE, STATUS,REASON,UPLOAD_STATUS from ACTUAL_LEI_DETAILS_UPLOAD; WHERE FILE_ID='";
	
	private static final String STAGE_BAHRAIN_FILE= " select ID,FILE_ID,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY,LIMIT_AMOUTNT,UTILIZATION_AMOUNT," +
			"REASON,STATUS,UPLOAD_STATUS from stage_bahrain_file_upload where file_id ='";

	private static final String ACTUAL_BAHRAIN_FILE= "select ID,FILE_ID,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY,LIMIT_AMOUTNT,UTILIZATION_AMOUNT," +
			"REASON,STATUS,UPLOAD_STATUS from cms_bahrain_file_upload where file_id='";

	private static final String STAGE_HONGKONG_FILE= "select ID,FILE_ID,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY,LIMIT_AMOUTNT,UTILIZATION_AMOUNT," +
			"REASON,STATUS,UPLOAD_STATUS from STAGE_HONGKONG_FILE_UPLOAD where file_id='";

	private static final String ACTUAL_HONGKONG_FILE= "select ID,FILE_ID,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY,LIMIT_AMOUTNT,UTILIZATION_AMOUNT," +
			"REASON,STATUS,UPLOAD_STATUS from CMS_HONGKONG_FILE_UPLOAD where file_id='";

	private static final String STAGE_FINWARE_FILE= "select ID,FILE_ID,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY,LIMIT_AMOUTNT,UTILIZATION_AMOUNT," +
			"REASON,STATUS,UPLOAD_STATUS from STAGE_FINWARE_FILE_UPLOAD where file_id='";

	private static final String ACTUAL_FINWARE_FILE= "select ID,FILE_ID,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY,LIMIT_AMOUTNT,UTILIZATION_AMOUNT," +
			"REASON,STATUS,UPLOAD_STATUS from CMS_FINWARE_FILE_UPLOAD where file_id='";
	
	private static final String STAGE_ACKNOWLEDGMENT_FILE= "SELECT CMS_COLLATERAL_ID,CERSAI_TRX_REF_NO,CERSAI_SECURITY_INTEREST_ID,CERSAI_ASSET_ID," +
	"DATE_CERSAI_REGISTERATION from CMS_SECURITY WHERE CMS_COLLATERAL_ID='";

private static final String CMS_STAGE_ACKNOWLEDGMENT_FILE = "SELECT CMS_COLLATERAL_ID,CERSAI_TRX_REF_NO,CERSAI_SECURITY_INTEREST_ID,CERSAI_ASSET_ID, "+ 
" DATE_CERSAI_REGISTERATION FROM CMS_STAGE_SECURITY WHERE CMS_COLLATERAL_ID='";

private static final String CMS_STAGE_LEIDETAILS_FILE = "SELECT ID, FILE_ID, PARTY_ID, LEI_CODE, LEI_EXP_DATE, STATUS,REASON,UPLOAD_STATUS FROM STAGE_LEI_DETAILS_UPLOAD WHERE ID='";

private static final String STAGE_CERSAI_ACK_FILE= "SELECT ID,FILE_ID,SECURITY_ID,CERSAI_TRX_REF_NO,CERSAI_SECURITY_INTEREST_ID,CERSAI_ASSET_ID," +
	"DATE_CERSAI_REG,STATUS,REASON,UPLOAD_STATUS FROM STAGE_ACKNOWLEDGMENT_UPLOAD WHERE FILE_ID='";

private static final String ACTUAL_CERSAI_ACK_FILE= "SELECT ID,FILE_ID,SECURITY_ID,CERSAI_TRX_REF_NO,CERSAI_SECURITY_INTEREST_ID,CERSAI_ASSET_ID," +
	"DATE_CERSAI_REG,STATUS,REASON,UPLOAD_STATUS FROM ACTUAL_ACKNOWLEDGMENT_UPLOAD WHERE FILE_ID='";


private static final String STAGE_AUTO_UPDATION_FILE= "SELECT  " + 
		"ID, FILE_ID, PARTY_ID, PARTY_NAME, FACILITY_ID, FACILITY_NAME, LINE_NO, SERIAL_NO,LIAB_BRANCH,SECURITY_SUB_TYPE,SECURITY_ID,LINE_STATUS,AUTOUPDATION_STATUS,REASON,UPLOAD_STATUS,CMS_LSP_SYS_XREF_ID,FACILITY_SYSTEM_ID,FACILITY_SYSTEM,DUE_DATE,DOC_CODE  " + 
		"FROM STAGE_AUTOUPDATIONLMTS_UPLOAD WHERE FILE_ID='";

	private static final String ACTUAL_AUTO_UPDATION_FILE= "SELECT  " + 
			"		ID, FILE_ID, PARTY_ID, PARTY_NAME, FACILITY_ID, FACILITY_NAME, LINE_NO, SERIAL_NO,LIAB_BRANCH,SECURITY_SUB_TYPE,SECURITY_ID,LINE_STATUS,AUTOUPDATION_STATUS,REASON,UPLOAD_STATUS,CMS_LSP_SYS_XREF_ID,FACILITY_SYSTEM_ID,FACILITY_SYSTEM,DUE_DATE,DOC_CODE   " + 
			"		FROM ACTUAL_AUTOUPDATIONLMTS_UPLOAD WHERE FILE_ID='";


	 // 03/02/2016: Uma Khot:Commented below code as not to update Released Amount from upload file.
//	private String updateLmtUtilAmtStage = "UPDATE CMS_STAGE_LSP_SYS_XREF SET RELEASED_AMOUNT = ?, UTILIZED_AMOUNT =? ,UPLOAD_STATUS=? WHERE FACILITY_SYSTEM_ID = ?" +
//	" AND LINE_NO=? AND SERIAL_NO=? and FACILITY_SYSTEM = ? and cms_lsp_sys_xref_id = ?";
	
	private String updateLmtUtilAmtStage = "UPDATE CMS_STAGE_LSP_SYS_XREF SET UTILIZED_AMOUNT =? ,UPLOAD_STATUS=? WHERE FACILITY_SYSTEM_ID = ?" +
	" AND LINE_NO=? AND SERIAL_NO=? and FACILITY_SYSTEM = ? and cms_lsp_sys_xref_id = ?";

	// 03/02/2016: Uma Khot:Commented below code as not to update Released Amount from upload file.
//	private String updateLmtUtilAmtActual = "UPDATE SCI_LSP_SYS_XREF SET RELEASED_AMOUNT = ?, UTILIZED_AMOUNT =? ,UPLOAD_STATUS=?,CREATED_BY=?,CREATED_ON=?,UPDATED_BY=?,UPDATED_ON=?  WHERE FACILITY_SYSTEM_ID = ?" +
//	" AND LINE_NO=? AND SERIAL_NO=? and FACILITY_SYSTEM = ? and cms_lsp_sys_xref_id = ?";
	
	private String updateLmtUtilAmtActual = "UPDATE SCI_LSP_SYS_XREF SET UTILIZED_AMOUNT =? ,UPLOAD_STATUS=?,CREATED_BY=?,CREATED_ON=?,UPDATED_BY=?,UPDATED_ON=?  WHERE FACILITY_SYSTEM_ID = ?" +
	" AND LINE_NO=? AND SERIAL_NO=? and FACILITY_SYSTEM = ? and cms_lsp_sys_xref_id = ?";
	
	private String updateSecurityStatus1 = "UPDATE CMS_SECURITY SET CERSAI_TRX_REF_NO = ?, CERSAI_SECURITY_INTEREST_ID = ?, CERSAI_ASSET_ID = ?,"+
	  "DATE_CERSAI_REGISTERATION = ? where CMS_COLLATERAL_ID = ?";
    private String updateStageSecurityStatus1 = "UPDATE CMS_STAGE_SECURITY SET CERSAI_TRX_REF_NO = ?, CERSAI_SECURITY_INTEREST_ID = ?, CERSAI_ASSET_ID = ?,"+
	  "DATE_CERSAI_REGISTERATION = ? where CMS_COLLATERAL_ID =  (select MAX (staging_reference_id) from transaction where reference_id=?  and transaction_type ='COL' ) ";
	
	//Changes By Prachit: FOR RELEASE LINE DETAILS
	
	private String updateSecurityStatus = "UPDATE SCI_LSP_SYS_XREF SET RELEASED_AMOUNT = ? WHERE FACILITY_SYSTEM_ID = ? AND LINE_NO = ? AND SERIAL_NO = ? AND "+
			  "LIAB_BRANCH = ?";
	
	private String updateStageSecurityStatus = "UPDATE CMS_STAGE_LSP_SYS_XREF SET RELEASED_AMOUNT = ? WHERE FACILITY_SYSTEM_ID = ? AND LINE_NO = ? " +
				"AND SERIAL_NO = ? AND LIAB_BRANCH = ? ";
			  
	
	private static final String ACTUAL_UBS_RLD_FILE= "SELECT ID,FILE_ID,SYSTEM_ID,LINE_NO,SERIAL_NO,LIAB_BRANCH," +
			"RELEASED_AMOUNT,STATUS,REASON,UPLOAD_STATUS,SOURCE_REF_NO,EXP_DATE,PSL_FLAG,PSL_VALUE,RULE_ID FROM ACTUAL_RELEASELINEDET_UPLOAD WHERE FILE_ID='";
	
	private static final String STAGE_UBS_RLD_FILE= "SELECT ID,FILE_ID,SYSTEM_ID,LINE_NO,SERIAL_NO,LIAB_BRANCH," +
			"RELEASED_AMOUNT,STATUS,REASON,UPLOAD_STATUS,SOURCE_REF_NO,EXP_DATE,PSL_FLAG,PSL_VALUE,RULE_ID FROM STAGE_RELEASELINEDET_UPLOAD WHERE FILE_ID='";

	private static final String STAGE_FCT_UPD_FILE= "SELECT ID,FILE_ID,FACILITY_ID ,SANCTION_AMOUNT ,SANCTION_AMOUNT_INR ,RELEASABLE_AMOUNT ," +
			"STATUS,REASON,UPLOAD_STATUS FROM STAGE_FACILITYDET_UPLOAD WHERE FILE_ID='";

	 // 03/02/2016: Uma Khot:Commented below code as not to update Released Amount from upload file.
//	private String updateLmtUtilAmtFinwareStage = "UPDATE CMS_STAGE_LSP_SYS_XREF SET RELEASED_AMOUNT = ?, UTILIZED_AMOUNT =? ,UPLOAD_STATUS=? WHERE FACILITY_SYSTEM_ID = ? " +
////	"AND LINE_NO=? AND SERIAL_NO=? " +
//	"and FACILITY_SYSTEM in ('FW-LIMITS','FCNCB') and cms_lsp_sys_xref_id = ?";
//
//	private String updateLmtUtilAmtFinwareActual = "UPDATE SCI_LSP_SYS_XREF SET RELEASED_AMOUNT = ?, UTILIZED_AMOUNT =? ,UPLOAD_STATUS=?,CREATED_BY=?,CREATED_ON=?,UPDATED_BY=?,UPDATED_ON=?  WHERE FACILITY_SYSTEM_ID = ?" +
//	//" AND LINE_NO=? AND SERIAL_NO=? " +
//	"and FACILITY_SYSTEM in ('FW-LIMITS','FCNCB') and cms_lsp_sys_xref_id = ?";
	
	private String updateLmtUtilAmtFinwareStage = "UPDATE CMS_STAGE_LSP_SYS_XREF SET  UTILIZED_AMOUNT =? ,UPLOAD_STATUS=? WHERE FACILITY_SYSTEM_ID = ? " +
//	"AND LINE_NO=? AND SERIAL_NO=? " +
	"and FACILITY_SYSTEM in ('FW-LIMITS','FCNCB','FWL') and cms_lsp_sys_xref_id = ?";

	private String updateLmtUtilAmtFinwareActual = "UPDATE SCI_LSP_SYS_XREF SET  UTILIZED_AMOUNT =? ,UPLOAD_STATUS=?,CREATED_BY=?,CREATED_ON=?,UPDATED_BY=?,UPDATED_ON=?  WHERE FACILITY_SYSTEM_ID = ?" +
	//" AND LINE_NO=? AND SERIAL_NO=? " +
	"and FACILITY_SYSTEM in ('FW-LIMITS','FCNCB','FWL') and cms_lsp_sys_xref_id = ?";

/*
	private String selectCustDetailsUbs="SELECT "+
	" FACILITY_SYSTEM_ID,LINE_NO,SERIAL_NO "+
	" FROM cms_stage_lsp_sys_xref sys,stage_limit_xref mp,transaction t WHERE "+
	" sys.FACILITY_SYSTEM = 'UBS-LIMITS' "+
	" and  sys.UPLOAD_STATUS='N' "+
	" and t.transaction_type = 'LIMIT'  "+
	" and mp.cms_lsp_sys_xref_id = sys.cms_lsp_sys_xref_id "+
	" and t.staging_reference_id = mp.cms_lsp_appr_lmts_id "+
	" and t.status = 'ACTIVE' "
	;

	private String selectCustDetailsUbsActual = "SELECT "+
	" FACILITY_SYSTEM_ID,LINE_NO,SERIAL_NO  "+
	" FROM SCI_LSP_SYS_XREF xref,sci_lsp_lmts_xref_map mp,transaction t WHERE " +
	" xref.UPLOAD_STATUS='N' and xref.FACILITY_SYSTEM = 'UBS-LIMITS'  "+
	" and mp.cms_lsp_sys_xref_id = xref.cms_lsp_sys_xref_id  "+
	" and t.reference_id = mp.cms_lsp_appr_lmts_id  "+
	" and t.transaction_type = 'LIMIT'  "+
	" and t.status = 'ACTIVE' ";

	private String selectCustDetailsHongkong="SELECT "+
	" FACILITY_SYSTEM_ID,LINE_NO,SERIAL_NO "+
	" FROM cms_stage_lsp_sys_xref sys,stage_limit_xref mp,transaction t WHERE "+
	" sys.FACILITY_SYSTEM = 'HONGKONG' "+
	" and  sys.UPLOAD_STATUS='N' "+
	" and t.transaction_type = 'LIMIT'  "+
	" and mp.cms_lsp_sys_xref_id = sys.cms_lsp_sys_xref_id "+
	" and t.staging_reference_id = mp.cms_lsp_appr_lmts_id "+
	" and t.status = 'ACTIVE' ";

	private String selectCustDetailsHongkongActual = "SELECT "+
	" FACILITY_SYSTEM_ID,LINE_NO,SERIAL_NO  "+
	" FROM SCI_LSP_SYS_XREF xref,sci_lsp_lmts_xref_map mp,transaction t WHERE " +
	" xref.UPLOAD_STATUS='N' and xref.FACILITY_SYSTEM = 'HONGKONG'  "+
	" and mp.cms_lsp_sys_xref_id = xref.cms_lsp_sys_xref_id  "+
	" and t.reference_id = mp.cms_lsp_appr_lmts_id  "+
	" and t.transaction_type = 'LIMIT'  "+
	" and t.status = 'ACTIVE' ";
*/
	//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
	private String selectCustDetailsUbs="SELECT "+
	" sys.FACILITY_SYSTEM_ID,sys.LINE_NO,sys.SERIAL_NO ,stg_lmt.lmt_crrncy_iso_code"+
	" FROM cms_stage_lsp_sys_xref sys,stage_limit_xref mp,transaction t ,STAGE_LIMIT stg_lmt WHERE "+
	" sys.FACILITY_SYSTEM = 'UBS-LIMITS' "+
	" and  sys.UPLOAD_STATUS='N' "+
	" and t.transaction_type = 'LIMIT'  "+
	" and mp.cms_lsp_sys_xref_id = sys.cms_lsp_sys_xref_id "+
	" and t.staging_reference_id = mp.cms_lsp_appr_lmts_id "+
	" and t.status = 'ACTIVE'  AND stg_lmt.cms_lsp_appr_lmts_id  = t.staging_reference_id"
	;

	private String selectCustDetailsUbsActual = "SELECT "+
	" xref.FACILITY_SYSTEM_ID,xref.LINE_NO,xref.SERIAL_NO ,act_lmt.lmt_crrncy_iso_code "+
	" FROM SCI_LSP_SYS_XREF xref,sci_lsp_lmts_xref_map mp,transaction t , SCI_LSP_APPR_LMTS act_lmt WHERE " +
	" xref.UPLOAD_STATUS='N' and xref.FACILITY_SYSTEM = 'UBS-LIMITS'  "+
	" and mp.cms_lsp_sys_xref_id = xref.cms_lsp_sys_xref_id  "+
	" and t.reference_id = mp.cms_lsp_appr_lmts_id  "+
	" and t.transaction_type = 'LIMIT'  "+
	" and t.status = 'ACTIVE'  AND act_lmt.cms_lsp_appr_lmts_id  = t.reference_id";

	private String selectCustDetailsHongkong="SELECT "+
	" sys.FACILITY_SYSTEM_ID,sys.LINE_NO,sys.SERIAL_NO ,TRIM(stg_lmt.lmt_crrncy_iso_code) AS lmt_crrncy_iso_code"+
	" FROM cms_stage_lsp_sys_xref sys,stage_limit_xref mp,transaction t, STAGE_LIMIT stg_lmt WHERE "+
	" sys.FACILITY_SYSTEM = 'HONGKONG' "+
	" and  sys.UPLOAD_STATUS='N' "+
	" and t.transaction_type = 'LIMIT'  "+
	" and mp.cms_lsp_sys_xref_id = sys.cms_lsp_sys_xref_id "+
	" and t.staging_reference_id = mp.cms_lsp_appr_lmts_id "+
	" and t.status = 'ACTIVE'  AND stg_lmt.cms_lsp_appr_lmts_id  = t.staging_reference_id";

	private String selectCustDetailsHongkongActual = "SELECT "+
	" xref.FACILITY_SYSTEM_ID,xref.LINE_NO,xref.SERIAL_NO ,TRIM(act_lmt.lmt_crrncy_iso_code) AS lmt_crrncy_iso_code"+
	" FROM SCI_LSP_SYS_XREF xref,sci_lsp_lmts_xref_map mp,transaction t , SCI_LSP_APPR_LMTS act_lmt WHERE " +
	" xref.UPLOAD_STATUS='N' and xref.FACILITY_SYSTEM = 'HONGKONG'  "+
	" and mp.cms_lsp_sys_xref_id = xref.cms_lsp_sys_xref_id  "+
	" and t.reference_id = mp.cms_lsp_appr_lmts_id  "+
	" and t.transaction_type = 'LIMIT'  "+
	" and t.status = 'ACTIVE'  AND act_lmt.cms_lsp_appr_lmts_id  = t.reference_id";

	//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
	
	private String selectCustDetailsFinware="SELECT "+
	" FACILITY_SYSTEM_ID,LINE_NO,SERIAL_NO "+
	" FROM cms_stage_lsp_sys_xref sys,stage_limit_xref mp,transaction t WHERE "+
	" sys.FACILITY_SYSTEM in ('FW-LIMITS','FCNCB') "+
	" and  sys.UPLOAD_STATUS='N' "+
	" and t.transaction_type = 'LIMIT'  "+
	" and mp.cms_lsp_sys_xref_id = sys.cms_lsp_sys_xref_id "+
	" and t.staging_reference_id = mp.cms_lsp_appr_lmts_id "+
	" and t.status = 'ACTIVE' ";

	private String selectCustDetailsFinwareActual = "SELECT "+
	" FACILITY_SYSTEM_ID,LINE_NO,SERIAL_NO  "+
	" FROM SCI_LSP_SYS_XREF xref,sci_lsp_lmts_xref_map mp,transaction t WHERE " +
	" xref.UPLOAD_STATUS='N' and xref.FACILITY_SYSTEM in ('FW-LIMITS','FCNCB')  "+
	" and mp.cms_lsp_sys_xref_id = xref.cms_lsp_sys_xref_id  "+
	" and t.reference_id = mp.cms_lsp_appr_lmts_id  "+
	" and t.transaction_type = 'LIMIT'  "+
	" and t.status = 'ACTIVE' ";
/*
	private String selectCustDetailsBahrain="SELECT "+
	" FACILITY_SYSTEM_ID,LINE_NO,SERIAL_NO "+
	" FROM cms_stage_lsp_sys_xref sys,stage_limit_xref mp,transaction t WHERE "+
	" sys.FACILITY_SYSTEM = 'BAHRAIN' "+
	" and  sys.UPLOAD_STATUS='N' "+
	" and t.transaction_type = 'LIMIT'  "+
	" and mp.cms_lsp_sys_xref_id = sys.cms_lsp_sys_xref_id "+
	" and t.staging_reference_id = mp.cms_lsp_appr_lmts_id "+
	" and t.status = 'ACTIVE' ";

	private String selectCustDetailsBahrainActual = "SELECT "+
	" FACILITY_SYSTEM_ID,LINE_NO,SERIAL_NO  "+
	" FROM SCI_LSP_SYS_XREF xref,sci_lsp_lmts_xref_map mp,transaction t WHERE " +
	" xref.UPLOAD_STATUS='N' and xref.FACILITY_SYSTEM = 'BAHRAIN'  "+
	" and mp.cms_lsp_sys_xref_id = xref.cms_lsp_sys_xref_id  "+
	" and t.reference_id = mp.cms_lsp_appr_lmts_id  "+
	" and t.transaction_type = 'LIMIT'  "+
	" and t.status = 'ACTIVE' ";
*/
	
	//Start:Uma:Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
	private String selectCustDetailsBahrain="SELECT "+
	" sys.FACILITY_SYSTEM_ID,sys.LINE_NO,sys.SERIAL_NO,TRIM(stg_lmt.lmt_crrncy_iso_code) AS lmt_crrncy_iso_code "+
	" FROM cms_stage_lsp_sys_xref sys,stage_limit_xref mp,transaction t ,STAGE_LIMIT stg_lmt WHERE "+
	" sys.FACILITY_SYSTEM = 'BAHRAIN' "+
	" and  sys.UPLOAD_STATUS='N' "+
	" and t.transaction_type = 'LIMIT'  "+
	" and mp.cms_lsp_sys_xref_id = sys.cms_lsp_sys_xref_id "+
	" and t.staging_reference_id = mp.cms_lsp_appr_lmts_id "+
	" and t.status = 'ACTIVE' AND stg_lmt.cms_lsp_appr_lmts_id  = t.staging_reference_id ";

	private String selectCustDetailsBahrainActual = "SELECT "+
	" xref.FACILITY_SYSTEM_ID,xref.LINE_NO,xref.SERIAL_NO ,TRIM(act_lmt.lmt_crrncy_iso_code) AS lmt_crrncy_iso_code"+
	" FROM SCI_LSP_SYS_XREF xref,sci_lsp_lmts_xref_map mp,transaction t , SCI_LSP_APPR_LMTS act_lmt WHERE " +
	" xref.UPLOAD_STATUS='N' and xref.FACILITY_SYSTEM = 'BAHRAIN'  "+
	" and mp.cms_lsp_sys_xref_id = xref.cms_lsp_sys_xref_id  "+
	" and t.reference_id = mp.cms_lsp_appr_lmts_id  "+
	" and t.transaction_type = 'LIMIT'  "+
	" and t.status = 'ACTIVE'  AND act_lmt.cms_lsp_appr_lmts_id  = t.reference_id";
	//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
	//---------------------------------------------------------------------------------------------------------------------------------------
	
	private String updateStatusUbs  ="UPDATE cms_stage_lsp_sys_xref SET UPLOAD_STATUS='N'  where FACILITY_SYSTEM = 'UBS-LIMITS'  ";

	private String updateStatusActualUbs ="UPDATE SCI_LSP_SYS_XREF SET UPLOAD_STATUS='N'  where FACILITY_SYSTEM = 'UBS-LIMITS'";
	
	private String updateStatusBahrain  ="UPDATE cms_stage_lsp_sys_xref SET UPLOAD_STATUS='N' where FACILITY_SYSTEM = 'BAHRAIN' ";

	private String updateStatusActualBahrain ="UPDATE SCI_LSP_SYS_XREF SET UPLOAD_STATUS='N'  where FACILITY_SYSTEM = 'BAHRAIN' ";
	
	private String updateStatusHongkong  ="UPDATE cms_stage_lsp_sys_xref SET UPLOAD_STATUS='N'  where FACILITY_SYSTEM = 'HONGKONG' ";

	private String updateStatusActualHongkong ="UPDATE SCI_LSP_SYS_XREF SET UPLOAD_STATUS='N'  where FACILITY_SYSTEM = 'HONGKONG'";
	
	private String updateStatusFinware  ="UPDATE cms_stage_lsp_sys_xref SET UPLOAD_STATUS='N'  where FACILITY_SYSTEM in ('FW-LIMITS','FCNCB') ";

	private String updateStatusActualFinware ="UPDATE SCI_LSP_SYS_XREF SET UPLOAD_STATUS='N'  where FACILITY_SYSTEM in ('FW-LIMITS','FCNCB')";

	private String updateStatusActualPartyCam ="UPDATE SCI_LSP_SYS_XREF SET UPLOAD_STATUS='N'  where FACILITY_SYSTEM = 'PARTYCAM-LIMITS'";
	
	private String updateStatusActualReleaselinedetails ="UPDATE SCI_LSP_SYS_XREF SET UPLOAD_STATUS='N'  where FACILITY_SYSTEM = 'RELEASELINEDETAILS-LIMITS'";

	private int totalPageForPagination;

	private int recordsPerPageForPagination;

	private PaginationUtil paginationUtil;	

	private String updateStatusActualAcknowledgment ="UPDATE SCI_LSP_SYS_XREF SET UPLOAD_STATUS='N'  where FACILITY_SYSTEM = 'ACKNOWLEDGMENT-LIMITS'";
	
	//Added for FD Upload
	private String updateFdDetailsStage = "UPDATE cms_stage_cash_deposit SET UPLOAD_STATUS='Y', ISSUE_DATE = ?, DEPOSIT_MATURITY_DATE =? ,DEPOSIT_INTEREST_RATE=? WHERE DEPOSIT_REFERENCE_NUMBER = ?" +
	" AND CMS_REF_ID=? ";
	
	private String updateFdDetailsActual = "UPDATE cms_cash_deposit SET UPLOAD_STATUS='Y', ISSUE_DATE = ?, DEPOSIT_MATURITY_DATE =? ,DEPOSIT_INTEREST_RATE=? WHERE DEPOSIT_REFERENCE_NUMBER = ?" +
	" AND CMS_REF_ID=? "+ "AND status='ACTIVE' AND active='active'";
	
	
	private String updateFdDetailsStageUploadStatusN = "UPDATE cms_stage_cash_deposit SET UPLOAD_STATUS='N' WHERE DEPOSIT_REFERENCE_NUMBER=?  AND CMS_REF_ID =?";
	
	
	//NPS Classification FCUBS Stock and receivable Start
	private String updateRelAmtStatusActual = "UPDATE SCI_LSP_SYS_XREF SET RELEASED_AMOUNT = 0 ,STATUS ='PENDING', ACTION = 'MODIFY'  WHERE FACILITY_SYSTEM_ID = ? " + 
			"	 AND LINE_NO=? AND SERIAL_NO=? AND FACILITY_SYSTEM = ? AND CMS_LSP_SYS_XREF_ID = ? ";
	
	private String updateRelAmtStatusStaging = "UPDATE CMS_STAGE_LSP_SYS_XREF SET RELEASED_AMOUNT =0 ,STATUS ='PENDING', ACTION = 'MODIFY' WHERE FACILITY_SYSTEM_ID = ? " + 
			"	 AND LINE_NO=? AND SERIAL_NO=? AND FACILITY_SYSTEM = ? ";
	
	private String updateDPAmountActual = "UPDATE CMS_ASSET_GC_DET SET CALCULATEDDP = 0   WHERE DOC_CODE = ? " + 
			"	 AND DUE_DATE=? AND CMS_COLLATERAL_ID=?  ";
	
	private String updateDPAmountStaging = "UPDATE CMS_STAGE_ASSET_GC_DET SET CALCULATEDDP = 0  WHERE DOC_CODE = ? " + 
			"	 AND DUE_DATE=? AND STATUS = 'APPROVED' AND GC_DET_ID = (SELECT MAX(GC_DET_ID) FROM CMS_STAGE_ASSET_GC_DET WHERE DOC_CODE = ? " + 
			"	 AND DUE_DATE=? AND STATUS = 'APPROVED') ";
	
	//NPS Classification FCUBS Stock and receivable End
	
	
	private static final String STAGE_FD_FILE= "select ID,FILE_ID,DEPOSIT_REFERENCE_NUMBER ,ISSUE_DATE,DEPOSIT_MATURITY_DATE ,DEPOSIT_INTEREST_RATE ," +
	"REASON,STATUS,UPLOAD_STATUS from STAGE_FD_FILE_UPLOAD where file_id='";

	private static final String ACTUAL_FD_FILE= "select ID,FILE_ID,DEPOSIT_REFERENCE_NUMBER ,ISSUE_DATE,DEPOSIT_MATURITY_DATE ,DEPOSIT_INTEREST_RATE ," +
	"REASON,STATUS,UPLOAD_STATUS from CMS_FD_FILE_UPLOAD where file_id='";
	
	private static final String SelectFdDetails= "select DEPOSIT_REFERENCE_NUMBER, ISSUE_DATE, DEPOSIT_MATURITY_DATE ,DEPOSIT_INTEREST_RATE ,CMS_REF_ID from CMS_STAGE_CASH_DEPOSIT where DEPOSIT_REFERENCE_NUMBER in (33333,2343422) and CMS_REF_ID in (111,20111104000000100 ,20111115000000114)";

	private static final String CHECK_PARTYCAM_UPLOAD_TRX=" SELECT count(1) FROM transaction WHERE transaction_type ='PARTYCAM_UPLOAD' ";
	
	private static final String CHECK_ACKNOWLEDGMENT_UPLOAD_TRX=" SELECT count(1) FROM transaction WHERE transaction_type ='ACK_UPLOAD' ";
	
	private static final String CHECK_LEIDETAILS_UPLOAD_TRX=" SELECT count(1) FROM transaction WHERE transaction_type ='LEI_DETAILS_UPLOAD' ";
	
	private static final String CHECK_RELEASELINEDETAILS_UPLOAD_TRX=" SELECT count(1) FROM transaction WHERE transaction_type ='RLD_UPLOAD' ";
	
	private static final String CHECK_BULKUDF_UPLOAD_TRX="SELECT count(1) FROM transaction WHERE transaction_type ='BULK_UDF_UPLOAD'";
	
	private static final String CHECK_BULK_UDF_PARTY_ID_COUNT="select count(1) from SCI_LE_SUB_PROFILE where LSP_LE_ID='";
	
	private static final String GET_PARTY_ID_FROM_MAIN =" select CMS_LE_MAIN_PROFILE_ID from SCI_LE_MAIN_PROFILE where LMP_LE_ID='";
	
	private static final String GET_CAM_NO_FROM_MAIN =" select CMS_LSP_LMT_PROFILE_ID from SCI_LSP_LMT_PROFILE where LLP_BCA_REF_NUM='";
	
	private static final String CHECK_BULK_UDF_CAM_NO_COUNT="select count(1) from SCI_LSP_LMT_PROFILE where LLP_BCA_REF_NUM='";
	
	private static final String CHECK_BULK_UDF_LIMIT_COUNT="Select count(1) from SCI_LSP_SYS_XREF where LINE_NO='"+" and SERIAL_NO = '"+" and LIAB_BRANCH = '";
	
	private static final String CHECK_FACILITYDETAILS_UPLOAD_TRX=" SELECT count(1) FROM transaction WHERE transaction_type ='FCT_UPLOAD' ";
	
	private static final String CHECK_AUTOUPDATIONLMTS_UPLOAD_TRX=" SELECT count(1) FROM transaction WHERE transaction_type ='AUTO_UPLOAD' ";
	
	private static final String CHECK_MF_SCHEMA_DETAILS_UPLOAD_TRX = "SELECT count(1) FROM transaction WHERE transaction_type ='" + ICMSConstant.MF_SCHEMA_DETAILS_UPLOAD+"'";
	
	private static final String CHECK_STOCK_DETAILS_UPLOAD_TRX = "SELECT count(1) FROM transaction WHERE transaction_type ='STOCK_DTLS_UPLOAD' ";
  
	private static final String CHECK_PSL_VALUE_N_RLD_UPLOAD = "SELECT count(1) COUNT from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE='PRIORITY_SECTOR' and ENTRY_CODE='";
	
	private static final String CHECK_PSL_VALUE_Y_RLD_UPLOAD = "SELECT count(1) COUNT from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE='PRIORITY_SECTOR_Y' and ENTRY_CODE='";
	
	private static final String CHECK_BOND_DETAILS_UPLOAD_TRX = "SELECT count(1) FROM transaction WHERE transaction_type ='" + ICMSConstant.BOND_DETAILS_UPLOAD+"'";
	
	private static final String CHECK_RULE_COUNT_RLD_UPLOAD = "SELECT count(1) COUNT from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE='NPA_RULE_ID' and ENTRY_CODE='";
	
	private String spUpdatePartyCamUploadActual;
	private String spUpdatePartyCamUploadStage;

	private String spUpdateRAMRatingUploadActual;
	private String spBeforeUpdateRAMRating;
	
	private String spBaselMonthlyNpaTrackNew;

	public String getSpBaselMonthlyNpaTrackNew() {
		return spBaselMonthlyNpaTrackNew;
	}

	public void setSpBaselMonthlyNpaTrackNew(String spBaselMonthlyNpaTrackNew) {
		this.spBaselMonthlyNpaTrackNew = spBaselMonthlyNpaTrackNew;
	}

	public String getSpBeforeUpdateRAMRating() {
		return spBeforeUpdateRAMRating;
	}

	public void setSpBeforeUpdateRAMRating(String spBeforeUpdateRAMRating) {
		this.spBeforeUpdateRAMRating = spBeforeUpdateRAMRating;
	}

	public String getSpUpdateRAMRatingUploadActual() {
		return spUpdateRAMRatingUploadActual;
	}

	public void setSpUpdateRAMRatingUploadActual(String spUpdateRAMRatingUploadActual) {
		this.spUpdateRAMRatingUploadActual = spUpdateRAMRatingUploadActual;
	}

	public String getSpUpdatePartyCamUploadActual() {
		return spUpdatePartyCamUploadActual;
	}

	public void setSpUpdatePartyCamUploadActual(String spUpdatePartyCamUploadActual) {
		this.spUpdatePartyCamUploadActual = spUpdatePartyCamUploadActual;
	}

	public String getSpUpdatePartyCamUploadStage() {
		return spUpdatePartyCamUploadStage;
	}

	public void setSpUpdatePartyCamUploadStage(String spUpdatePartyCamUploadStage) {
		this.spUpdatePartyCamUploadStage = spUpdatePartyCamUploadStage;
	}

	public int getTotalPageForPagination() {
		return totalPageForPagination;
	}

	public void setTotalPageForPagination(int totalPageForPagination) {
		this.totalPageForPagination = totalPageForPagination;
	}

	public int getRecordsPerPageForPagination() {
		return recordsPerPageForPagination;
	}

	public void setRecordsPerPageForPagination(int recordsPerPageForPagination) {
		this.recordsPerPageForPagination = recordsPerPageForPagination;
	}

	public PaginationUtil getPaginationUtil() {
		return paginationUtil;
	}

	public void setPaginationUtil(PaginationUtil paginationUtil) {
		this.paginationUtil = paginationUtil;
	}

	public int getUplodCount(String fileType) throws FileUploadException {
		List resultList = null;
		int count=0;
		try {
			String SQL=CHECK_UBS_UPLOAD_TRX+fileType+"'AND (status LIKE 'PENDING%' OR STATUS LIKE 'REJECTED') AND LOGIN_ID NOT IN ('SYSTEM_UPLOAD')";
			count=getJdbcTemplate().queryForInt(SQL);			

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- Checkling File Transaction in FileUploadJdbcImpl");
		}

		return count;
	}
	
	
	public int getCountPassFromTemp()
	{
		int count=0;
		try {
			String SQL="select count(1) from TEMP_BULK_UDF_UPLOAD where VALID='TRUE' and REMARKS='APPROVED'";
			count=getJdbcTemplate().queryForInt(SQL);			

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- Checkling File Count Pass for Bulk UDF in FileUploadJdbcImpl");
		}

		return count;
		}
		
	//Modified By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION 
	public HashMap insertUbsfile( ArrayList result,UploadUbsFileCmd cmd,String fileName, ICommonUser user,Date date,ConcurrentMap<String, String> dataFromCacheView ,ConcurrentMap<String, String> dataFromUpdLineFacilityMV) {

		List totalUploadedList= new ArrayList();
		List errorList= new ArrayList();
		Timestamp st = null;
		String errMsg="";
		String tempData="";
		String strArrTemp[]=new String[3];
		List selectCustDetails=new ArrayList();
		String partyStatus="";
		HashMap retMap = new HashMap();
		DefaultLogger.debug(this, "##########3###########In FileUploadJdbcImpl insertUbsfile ##### line no 220#######:: ");
		try {
			if(result!=null&& result.size() != 0)
			{
				for (int index = 0; index < result.size(); index++) {
					HashMap eachDataMap = (HashMap) result.get(index);
					OBUbsFile obj=new OBUbsFile();
					obj.setCurrency((String) eachDataMap.get("CURRENCY_CODE"));
					if(null!=eachDataMap.get("CUSTOMER_ID")&& !"".equalsIgnoreCase(eachDataMap.get("CUSTOMER_ID").toString()) )
					{
						obj.setCustomer(eachDataMap.get("CUSTOMER_ID").toString());
					}
					if(null!=eachDataMap.get("LINE_NO")&& !"".equalsIgnoreCase(eachDataMap.get("LINE_NO").toString()) )
					{
						obj.setLine(eachDataMap.get("LINE_NO").toString());
					}
					
					if(null!=eachDataMap.get("LIMIT_AMOUNT")&& !"".equalsIgnoreCase(eachDataMap.get("LIMIT_AMOUNT").toString()) )
					{
						obj.setLimit(new Double(Double.parseDouble(eachDataMap.get("LIMIT_AMOUNT").toString())));
					}
					
					if(null!=eachDataMap.get("SR_NO")&& !"".equalsIgnoreCase(eachDataMap.get("SR_NO").toString()) )
					{
						obj.setSerialNo(eachDataMap.get("SR_NO").toString());
					}
					
					if(null!=eachDataMap.get("UTILIZATION_AMOUNT")&& !"".equalsIgnoreCase(eachDataMap.get("UTILIZATION_AMOUNT").toString()) )
					{
						obj.setUtilize(new Double(Double.parseDouble(eachDataMap.get("UTILIZATION_AMOUNT").toString())));
						
					}
					obj.setUploadStatus("Y");
					OBCommonFile commonObj = obj;
//					partyStatus=getPartyStatus(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()),"UBS-LIMITS");
					// Commented and added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION |Starts
					//partyStatus=getPartyStatusForUBS(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()));
					partyStatus = getPartyStatusForUBSUpload(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()),dataFromCacheView,"UBS-LIMITS");
					// Commented and added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION |Ends
					if(!partyStatus.equals("")&& "ACTIVE".equals(partyStatus)){
						commonObj=updateLmtUtilAmtStage(obj,"UBS-LIMITS",dataFromUpdLineFacilityMV);
					}else if(!partyStatus.equals("")&& "INACTIVE".equals(partyStatus)){
						errMsg="Party Is Inactive In CLIMS.";
						commonObj.setReason(errMsg);
						commonObj.setStatus("FAIL");
					}else if(partyStatus.equals("")){
						commonObj=updateLmtUtilAmtStage(obj,"UBS-LIMITS",dataFromUpdLineFacilityMV);
						errMsg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+obj.getCustomer()+","+obj.getLine()+","+obj.getSerialNo()+","+obj.getCurrency()+") Available in UBS not in CLIMS.";
					}
					totalUploadedList.add(commonObj);
				}
			}
			
			//To Do :::: Below functionality has been implemented for UBS-LIMITS file upload only. This needs to be implemented for Other Upload files. 
			//Also for inserting data to actual tables.
			int batchSize = 200;
			for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
				List<OBCommonFile> batchList = totalUploadedList.subList(j, j + batchSize > totalUploadedList.size() ? totalUploadedList.size() : j + batchSize);
				updateXrefStageAmount(batchList,"UBS-LIMITS");
			}
			
			st = new Timestamp(System.currentTimeMillis());
			selectCustDetails=selectCustDetails("UbsFile");
			for(int i=0;i<selectCustDetails.size();i++)
			{				
				tempData=selectCustDetails.get(i).toString();
				strArrTemp=tempData.split(ICMSConstant.FILEUPLOAD_SEPERATOR);
				errMsg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+selectCustDetails.get(i)+")Available in CLIMS not in UBS.";
				OBUbsFile obUbs=new OBUbsFile();
				//obUbs.setCurrency("");					
				obUbs.setCustomer(strArrTemp[0]);
				obUbs.setLine(strArrTemp[1]);
				obUbs.setSerialNo(strArrTemp[2]);
				//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				obUbs.setCurrency(strArrTemp[3].trim());
				//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				obUbs.setStatus("FAIL");
				obUbs.setReason(errMsg);
				obUbs.setUploadStatus("N");
				errorList.add(obUbs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.getMessage());
			throw new IncompleteBatchJobException(
			"Unable to update retrived record from CSV file");
		}
		getJdbcTemplate().update(updateStatusUbs);
		retMap.put("totalUploadedList", totalUploadedList);
		retMap.put("errorList", errorList);
		return retMap;
	}

	public HashMap insertHongKongfile( ArrayList result,UploadHongKongFileCmd cmd,String fileName, ICommonUser user, Date date,ConcurrentMap<String, String> dataFromCacheView,ConcurrentMap<String, String> dataFromUpdLineFacilityMV) {

		List totalUploadedList= new ArrayList();
		List errorList= new ArrayList();
		Timestamp st = null;
		String errMsg="";
		String tempData="";
		String strArrTemp[]=new String[3];
		List selectCustDetails=new ArrayList();
		String partyStatus="";
		HashMap retMap = new HashMap();
		
		DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 296 #######:: ");
		
		try {
			if(result!=null&& result.size() != 0)
			{
				for (int index = 0; index < result.size(); index++) {
					HashMap eachDataMap = (HashMap) result.get(index);
					OBHongKongFile obj=new OBHongKongFile();
					obj.setCurrency((String) eachDataMap.get("CURRENCY_CODE"));
					if(null!=eachDataMap.get("CUSTOMER_ID")&& !"".equalsIgnoreCase(eachDataMap.get("CUSTOMER_ID").toString()) )
					{
						obj.setCustomer(eachDataMap.get("CUSTOMER_ID").toString());
					}
					if(null!=eachDataMap.get("LINE_NO")&& !"".equalsIgnoreCase(eachDataMap.get("LINE_NO").toString()) )
					{
						obj.setLine(eachDataMap.get("LINE_NO").toString());
					}
					
					DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 339 #######:: "+eachDataMap.get("LIMIT_AMOUNT"));
					if(null!=eachDataMap.get("LIMIT_AMOUNT")&& !"".equalsIgnoreCase(eachDataMap.get("LIMIT_AMOUNT").toString()) )
					{
						obj.setLimit(new Double(Double.parseDouble(eachDataMap.get("LIMIT_AMOUNT").toString())));
					}
					DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 344 #######:: "+eachDataMap.get("LIMIT_AMOUNT"));
					if(null!=eachDataMap.get("SR_NO")&& !"".equalsIgnoreCase(eachDataMap.get("SR_NO").toString()) )
					{
						obj.setSerialNo(eachDataMap.get("SR_NO").toString());
					}
					
					DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 347 #######:: "+eachDataMap.get("UTILIZATION_AMOUNT"));
					if(null!=eachDataMap.get("UTILIZATION_AMOUNT")&& !"".equalsIgnoreCase(eachDataMap.get("UTILIZATION_AMOUNT").toString()) )
					{
						obj.setUtilize(new Double(Double.parseDouble(eachDataMap.get("UTILIZATION_AMOUNT").toString())));
					}
					DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 352 #######:: "+eachDataMap.get("UTILIZATION_AMOUNT"));
					obj.setUploadStatus("Y");
					OBCommonFile commonObj = obj;					
					
					// Commented and added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION |Starts
					//partyStatus=getPartyStatusForUBS(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()));
					partyStatus = getPartyStatusForUBSUpload(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()),dataFromCacheView,"HONGKONG");
					// Commented and added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION |Ends
					
					
//					partyStatus=getPartyStatus(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()),"HONGKONG");
					//partyStatus=getPartyStatusForHongKong(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()));
					if(!partyStatus.equals("")&& "ACTIVE".equals(partyStatus)){
						DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 314 #######:: PASS ##### UPLOAD_STATUS set to Y");
						commonObj=updateLmtUtilAmtStage(obj,"HONGKONG",dataFromUpdLineFacilityMV);
					}else if(!partyStatus.equals("")&& "INACTIVE".equals(partyStatus)){
						DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 318 #######:: FAIL ##### UPLOAD_STATUS set to Y");
						errMsg="Party Is Inactive In CLIMS.";
						commonObj.setReason(errMsg);
						commonObj.setStatus("FAIL");
					}else if(partyStatus.equals("")){
						commonObj=updateLmtUtilAmtStage(obj,"HONGKONG",dataFromUpdLineFacilityMV);
						errMsg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+obj.getCustomer()+","+obj.getLine()+","+obj.getSerialNo()+","+obj.getCurrency()+") Available in HONGKONG not in CLIMS.";
					}
					totalUploadedList.add(commonObj);
				}
			}
			
			DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 329 ####### before updating amount and status:: totalUploadedList  "+totalUploadedList.size());
			
			
			
			int batchSize = 200;
			for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
				List<OBCommonFile> batchList = totalUploadedList.subList(j, j + batchSize > totalUploadedList.size() ? totalUploadedList.size() : j + batchSize);
				
				updateXrefStageAmount(batchList,"HONGKONG");
			}
			
			DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 333 ####### after updating amount and status:: totalUploadedList "+totalUploadedList.size());
			
			DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 335 #######:: FAIL ##### before UPLOAD_STATUS will be N for errorList");
			
			st = new Timestamp(System.currentTimeMillis());
			selectCustDetails=selectCustDetails("HongkongFile");
			for(int i=0;i<selectCustDetails.size();i++)
			{				
				tempData=selectCustDetails.get(i).toString();
				strArrTemp=tempData.split(ICMSConstant.FILEUPLOAD_SEPERATOR);
				errMsg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+selectCustDetails.get(i)+")Available in CLIMS not in HONGKONG.";
				OBHongKongFile obHongkong=new OBHongKongFile();
//				obHongkong.setCurrency("");					
				obHongkong.setCustomer(strArrTemp[0]);
				obHongkong.setLine(strArrTemp[1]);
				obHongkong.setSerialNo(strArrTemp[2]);
				//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				obHongkong.setCurrency(strArrTemp[3].trim());
				//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				obHongkong.setStatus("FAIL");
				obHongkong.setReason(errMsg);
				obHongkong.setUploadStatus("N");
				errorList.add(obHongkong);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
			"Unable to update/insert dad retrived form CSV file");
		}
		
		DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 360 #######:: FAIL ##### after UPLOAD_STATUS will be N for errorList"+errorList.size());
		
		DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 362 #######:: before UPLOAD_STATUS  N Default value");
		
		getJdbcTemplate().update(updateStatusHongkong);
		
		DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 366 #######:: after UPLOAD_STATUS  N Default value");
		
		retMap.put("totalUploadedList", totalUploadedList);
		retMap.put("errorList", errorList);
		return retMap;
	}

	public HashMap insertFinwareFile( ArrayList result,UploadfinWareFileCmd cmd,String fileName, ICommonUser user,Date date,ConcurrentMap<String, String> dataFromCacheView,ConcurrentMap<String, String> dataFromUpdLineFacilityMV) {

		List totalUploadedList= new ArrayList();
		List errorList= new ArrayList();
		Timestamp st = null;
		String errMsg="";
		String tempData="";
		String strArrTemp[]=new String[3];
		List selectCustDetails=new ArrayList();
		String partyStatus="";
		HashMap retMap = new HashMap();
		try {
			if(result!=null&& result.size() != 0)
			{
				for (int index = 0; index < result.size(); index++) {
					HashMap eachDataMap = (HashMap) result.get(index);
					OBFinwareFile obj=new OBFinwareFile();
					obj.setCurrency((String) eachDataMap.get("CURRENCY_CODE"));
					if(null!=eachDataMap.get("CUSTOMER_ID")&& !"".equalsIgnoreCase(eachDataMap.get("CUSTOMER_ID").toString()) )
					{
						obj.setCustomer(eachDataMap.get("CUSTOMER_ID").toString());
					}
					
					if(null!=eachDataMap.get("LINE_NO")&& !"".equalsIgnoreCase(eachDataMap.get("LINE_NO").toString()) )
					{
						obj.setLine(eachDataMap.get("LINE_NO").toString());
					}
					
					
					if(null!=eachDataMap.get("LIMIT_AMOUNT")&& !"".equalsIgnoreCase(eachDataMap.get("LIMIT_AMOUNT").toString()) )
					{
						obj.setLimit(new Double(Double.parseDouble(eachDataMap.get("LIMIT_AMOUNT").toString())));
					}
					
					if(null!=eachDataMap.get("SR_NO")&& !"".equalsIgnoreCase(eachDataMap.get("SR_NO").toString()) )
					{
						obj.setSerialNo(eachDataMap.get("SR_NO").toString());
					}
					
					if(null!=eachDataMap.get("UTILIZATION_AMOUNT")&& !"".equalsIgnoreCase(eachDataMap.get("UTILIZATION_AMOUNT").toString()) )
					{
						obj.setUtilize(new Double(Double.parseDouble(eachDataMap.get("UTILIZATION_AMOUNT").toString())));
					}
					obj.setUploadStatus("Y");
					OBCommonFile commonObj = obj;
					// Commented and added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION |Starts
					//partyStatus=getPartyStatusForUBS(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()));
					partyStatus = getPartyStatusForUBSUpload(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()),dataFromCacheView,"FINWARE");
					// Commented and added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION |Ends
				//	partyStatus=getPartyStatusForFinware(obj.getCustomer());
					if(!partyStatus.equals("")&& "ACTIVE".equals(partyStatus)){
						commonObj=updateLmtUtilAmtFinwareStage(obj,"FINWARE",dataFromUpdLineFacilityMV);
					}else if(!partyStatus.equals("")&& "INACTIVE".equals(partyStatus)){
						errMsg="Party Is Inactive In CLIMS.";
						commonObj.setReason(errMsg);
						commonObj.setStatus("FAIL");
					}else if(partyStatus.equals("")){
						commonObj=updateLmtUtilAmtFinwareStage(obj,"FINWARE",dataFromUpdLineFacilityMV);
						errMsg="Combination of Customer_id i.e. ("+obj.getCustomer()+") Available in FINWARE not in CLIMS.";
					}
					totalUploadedList.add(commonObj);
				}
			}
			
			
			
			int batchSize = 200;
			for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
				List<OBCommonFile> batchList = totalUploadedList.subList(j, j + batchSize > totalUploadedList.size() ? totalUploadedList.size() : j + batchSize);
				
				updateXrefFinwareStageAmount(batchList,"FINWARE");
			}
			
			
			st = new Timestamp(System.currentTimeMillis());
			selectCustDetails=selectCustDetails("FinwareFile");
			for(int i=0;i<selectCustDetails.size();i++)
			{				
				tempData=selectCustDetails.get(i).toString();
				strArrTemp=tempData.split(ICMSConstant.FILEUPLOAD_SEPERATOR);
				errMsg="Combination of Customer_id i.e. ("+selectCustDetails.get(i)+")Available in CLIMS not in FINWARE.";
				OBFinwareFile obFinware=new OBFinwareFile();
				obFinware.setCurrency("");					
				obFinware.setCustomer(strArrTemp[0]);
				obFinware.setLine(strArrTemp[1]);
				obFinware.setSerialNo(strArrTemp[2]);
				obFinware.setStatus("FAIL");
				obFinware.setReason(errMsg);
				obFinware.setUploadStatus("N");
				errorList.add(obFinware);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
			"Unable to update/insert dad retrived form CSV file");
		}
		getJdbcTemplate().update(updateStatusFinware);
		retMap.put("totalUploadedList", totalUploadedList);
		retMap.put("errorList", errorList);
		return retMap;
	}

	public HashMap insertBahrainfile( ArrayList result,UploadBahrainFileCmd cmd,String fileName, ICommonUser user, Date date,ConcurrentMap<String, String> dataFromCacheView,ConcurrentMap<String, String> dataFromUpdLineFacilityMV) {

		List totalUploadedList= new ArrayList();
		List errorList= new ArrayList();
		Timestamp st = null;
		String errMsg="";
		String tempData="";
		String strArrTemp[]=new String[3];
		List selectCustDetails=new ArrayList();
		String partyStatus="";
		HashMap retMap = new HashMap();
		try {
			if(result!=null&& result.size() != 0)
			{
				for (int index = 0; index < result.size(); index++) {
					HashMap eachDataMap = (HashMap) result.get(index);

					OBBahrainFile obj=new OBBahrainFile();
					obj.setCurrency((String) eachDataMap.get("CURRENCY_CODE"));
					if(null!=eachDataMap.get("CUSTOMER_ID")&& !"".equalsIgnoreCase(eachDataMap.get("CUSTOMER_ID").toString()) )
					{
						obj.setCustomer(eachDataMap.get("CUSTOMER_ID").toString());
					}
					if(null!=eachDataMap.get("LINE_NO")&& !"".equalsIgnoreCase(eachDataMap.get("LINE_NO").toString()) )
					{
						obj.setLine(eachDataMap.get("LINE_NO").toString());
					}
					
					if(null!=eachDataMap.get("LIMIT_AMOUNT")&& !"".equalsIgnoreCase(eachDataMap.get("LIMIT_AMOUNT").toString()) )
					{
						obj.setLimit(new Double(Double.parseDouble(eachDataMap.get("LIMIT_AMOUNT").toString())));
					}
					if(null!=eachDataMap.get("SR_NO")&& !"".equalsIgnoreCase(eachDataMap.get("SR_NO").toString()) )
					{
						obj.setSerialNo(eachDataMap.get("SR_NO").toString());
					}
					
					if(null!=eachDataMap.get("UTILIZATION_AMOUNT")&& !"".equalsIgnoreCase(eachDataMap.get("UTILIZATION_AMOUNT").toString()) )
					{
						obj.setUtilize(new Double(Double.parseDouble(eachDataMap.get("UTILIZATION_AMOUNT").toString())));
					}
					obj.setUploadStatus("Y");
					OBCommonFile commonObj = obj;
					// Commented and added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION |Starts
					//partyStatus=getPartyStatusForUBS(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()));
					partyStatus = getPartyStatusForUBSUpload(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()),dataFromCacheView,"BAHRAIN");
					// Commented and added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION |Ends
//					partyStatus=getPartyStatus(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()),"BAHRAIN");
					//partyStatus=getPartyStatusForBahrain(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()));
					if(!partyStatus.equals("")&& "ACTIVE".equals(partyStatus)){
						commonObj=updateLmtUtilAmtStage(obj,"BAHRAIN",dataFromUpdLineFacilityMV);
					}else if(!partyStatus.equals("")&& "INACTIVE".equals(partyStatus)){
						errMsg="Party Is Inactive In CLIMS.";
						commonObj.setReason(errMsg);
						commonObj.setStatus("FAIL");
					}else if(partyStatus.equals("")){
						commonObj=updateLmtUtilAmtStage(obj,"BAHRAIN",dataFromUpdLineFacilityMV);
						errMsg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+obj.getCustomer()+","+obj.getLine()+","+obj.getSerialNo()+","+obj.getCurrency()+") Available in BAHRAIN not in CLIMS.";
					}
					totalUploadedList.add(commonObj);
				}
			}
			
			
			
			int batchSize = 200;
			for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
				List<OBCommonFile> batchList = totalUploadedList.subList(j, j + batchSize > totalUploadedList.size() ? totalUploadedList.size() : j + batchSize);
				
				updateXrefStageAmount(batchList,"BAHRAIN");
			}
			
			
			st = new Timestamp(System.currentTimeMillis());
			selectCustDetails=selectCustDetails("BahrainFile");
			for(int i=0;i<selectCustDetails.size();i++)
			{				
				tempData=selectCustDetails.get(i).toString();
				strArrTemp=tempData.split(ICMSConstant.FILEUPLOAD_SEPERATOR);
				errMsg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+selectCustDetails.get(i)+")Available in CLIMS not in BAHRAIN.";
				OBBahrainFile obBahrain=new OBBahrainFile();
//				obBahrain.setCurrency("");					
				obBahrain.setCustomer(strArrTemp[0]);
				obBahrain.setLine(strArrTemp[1]);
				obBahrain.setSerialNo(strArrTemp[2]);
				//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				obBahrain.setCurrency(strArrTemp[3].trim());
				//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				obBahrain.setStatus("FAIL");
				obBahrain.setReason(errMsg);
				obBahrain.setUploadStatus("N");
				errorList.add(obBahrain);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
			"Unable to update/insert dad retrived form CSV file");
		}
		getJdbcTemplate().update(updateStatusBahrain);
		retMap.put("totalUploadedList", totalUploadedList);
		retMap.put("errorList", errorList);
		return retMap;
	}

	public HashMap insertHRMSfile( ArrayList result,String fileName,Date date,ConcurrentMap<String, String> dataFromCacheView,ConcurrentMap<String, String> dataFromUpdLineFacilityMV) {

		List totalUploadedList= new ArrayList();
		List errorList= new ArrayList();
		Timestamp st = null;
		String errMsg="";
		String tempData="";
		String strArrTemp[]=new String[3];
		List selectCustDetails=new ArrayList();
		String partyStatus="";
		HashMap retMap = new HashMap();
		try {
			if(result!=null&& result.size() != 0)
			{
				for (int index = 0; index < result.size(); index++) {
					HashMap eachDataMap = (HashMap) result.get(index);

					OBHRMSData obj=new OBHRMSData();
					obj.setEmployeeCode((String) eachDataMap.get("EMPLOYEE_NUMBER"));
					if(null!=eachDataMap.get("TITLE")&& !"".equalsIgnoreCase(eachDataMap.get("TITLE").toString()) )
					{
						String name = eachDataMap.get("TITLE").toString()+" "+eachDataMap.get("FIRST_NAME").toString()+" "+eachDataMap.get("LAST_NAME").toString(); 
						obj.setName(name);
					}
					if(null!=eachDataMap.get("OFFICIAL_EMAIL")&& !"".equalsIgnoreCase(eachDataMap.get("OFFICIAL_EMAIL").toString()) )
					{
						obj.setEmailId(eachDataMap.get("OFFICIAL_EMAIL").toString());
					}
					
					if(null!=eachDataMap.get("CONTACT_1")&& !"".equalsIgnoreCase(eachDataMap.get("CONTACT_1").toString()) )
					{
						String mobNo = eachDataMap.get("CONTACT_1").toString() +" & "+eachDataMap.get("CONTACT_2").toString();
						obj.setMobileNo(mobNo);
					}
					if(null!=eachDataMap.get("REGION_2")&& !"".equalsIgnoreCase(eachDataMap.get("REGION_2").toString()) )
					{
						obj.setRegion(eachDataMap.get("REGION_2").toString());
					}
					if(null!=eachDataMap.get("TOWN_OR_CITY")&& !"".equalsIgnoreCase(eachDataMap.get("TOWN_OR_CITY").toString()) )
					{
						obj.setCity(eachDataMap.get("TOWN_OR_CITY").toString());
					}
					if(null!=eachDataMap.get("REGION_1")&& !"".equalsIgnoreCase(eachDataMap.get("REGION_1").toString()) )
					{
						obj.setRegion(eachDataMap.get("REGION_1").toString());
					}
					if(null!=eachDataMap.get("REGION")&& !"".equalsIgnoreCase(eachDataMap.get("REGION").toString()) )
					{
						obj.setWboRegion(eachDataMap.get("REGION").toString());
					}
					if(null!=eachDataMap.get("SUPERVISOR_EMP_CODE")&& !"".equalsIgnoreCase(eachDataMap.get("SUPERVISOR_EMP_CODE").toString()) )
					{
						obj.setSupervisorEmployeeCode(eachDataMap.get("SUPERVISOR_EMP_CODE").toString());
					}
					if(null!=eachDataMap.get("LOCATION_CODE")&& !"".equalsIgnoreCase(eachDataMap.get("LOCATION_CODE").toString()) )
					{
						obj.setBranchCode(eachDataMap.get("LOCATION_CODE").toString());
					}
					if(null!=eachDataMap.get("BAND")&& !"".equalsIgnoreCase(eachDataMap.get("BAND").toString()))
					{
						obj.setBand(eachDataMap.get("BAND").toString());
					}
					
					totalUploadedList.add(obj);
				}
			}
			
			
			
			int batchSize = 200;
			for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
				List<OBCommonFile> batchList = totalUploadedList.subList(j, j + batchSize > totalUploadedList.size() ? totalUploadedList.size() : j + batchSize);
				
				insertOrUpdateHRMS(batchList,"HRMS");
			}
			
			
			st = new Timestamp(System.currentTimeMillis());
			selectCustDetails=selectCustDetails("HRMSFile");
			for(int i=0;i<selectCustDetails.size();i++)
			{				
				tempData=selectCustDetails.get(i).toString();
				strArrTemp=tempData.split(ICMSConstant.FILEUPLOAD_SEPERATOR);
				OBHRMSData hrmsData=new OBHRMSData();
//				hrmsData.setCurrency("");					
				hrmsData.setCustomer(strArrTemp[0]);
				hrmsData.setLine(strArrTemp[1]);
				hrmsData.setSerialNo(strArrTemp[2]);
				//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				hrmsData.setCurrency(strArrTemp[3].trim());
				//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				hrmsData.setStatus("FAIL");
				hrmsData.setReason(errMsg);
				hrmsData.setUploadStatus("N");
				errorList.add(hrmsData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
			"Unable to update/insert dad retrived form CSV file");
		}
		getJdbcTemplate().update(updateStatusBahrain);
		retMap.put("totalUploadedList", totalUploadedList);
		retMap.put("errorList", errorList);
		return retMap;
	}

	private void insertOrUpdateHRMS(List<OBCommonFile> objList, String string) {
		
System.out.println("--------------------------"+objList);
//		}
		
		
	}
	
	

	public OBCommonFile updateLmtUtilAmtStage(OBCommonFile obj, String facilitySystem,ConcurrentMap<String, String> dataFromUpdLineFacilityMV) {
		String sys_xref_id="";
		String cur="";
		try{	
			List selectLmtUtilAmtStage=selectLmtUtilAmtStageRevised( obj.getCustomer(),obj.getLine(), obj.getSerialNo(),facilitySystem,dataFromUpdLineFacilityMV);
			DefaultLogger.info(this, "selectLmtUtilAmtStage size:::"+selectLmtUtilAmtStage.size()+"::");
			DefaultLogger.info(this, "selectLmtUtilAmtStage selectLmtUtilAmtStage List is::"+selectLmtUtilAmtStage+"::");
			if(selectLmtUtilAmtStage!=null && selectLmtUtilAmtStage.size()>=1)
			{
				String[] arr = (String[]) selectLmtUtilAmtStage.get(0);
				sys_xref_id = arr[0];
				DefaultLogger.info(this,"updateLmtUtilAmtStage() sys_xref_id::::"+sys_xref_id);
				cur = arr[1];
				DefaultLogger.info(this,"updateLmtUtilAmtStage() cur::::"+cur+":::::");
			}
			DefaultLogger.info(this,"updateLmtUtilAmtStage() obj.getCurrency()::::"+obj.getCurrency()+":::::");
			
			DefaultLogger.info(this,"updateLmtUtilAmtStage() 1:::: "+(obj.getCurrency()!=null)+":::::");
			DefaultLogger.info(this,"updateLmtUtilAmtStage() 2::::"+(!"".equals(obj.getCurrency()))+":::::");
			DefaultLogger.info(this,"updateLmtUtilAmtStage() 3::::"+(obj.getCurrency().equals(cur))+":::::");
			DefaultLogger.info(this,"updateLmtUtilAmtStage() 4::::"+(!obj.getCurrency().equals(cur))+":::::");
			DefaultLogger.info(this,"updateLmtUtilAmtStage() 5::::"+(!"".equals(cur))+":::::");
			
			
			if(obj.getCurrency()!=null && !"".equals(obj.getCurrency()) && cur!=null && !"".equalsIgnoreCase(cur.trim()) && obj.getCurrency().equals(cur.trim())){
				DefaultLogger.info(this," First condition!! ");	
				obj.setSysXrefId(Long.parseLong(sys_xref_id));
				obj.setStatus("PASS");
				String msg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+obj.getCustomer()+","+obj.getLine()+","+obj.getSerialNo()+","+obj.getCurrency()+") Matched in CLIMS and updated.";
				obj.setReason(msg);
			}else if(obj.getCurrency()!=null && cur!=null && !"".equals(cur) && !"".equalsIgnoreCase(cur.trim()) && !obj.getCurrency().equals(cur.trim())){
				DefaultLogger.info(this," Second condition!! ");
				obj.setSysXrefId(0);
				obj.setStatus("FAIL");
				String msg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+obj.getCustomer()+","+obj.getLine()+","+obj.getSerialNo()+","+obj.getCurrency()+") Currency not matched";
				obj.setReason(msg);
			}else{
				DefaultLogger.info(this," Else condition!! ");
				obj.setSysXrefId(0);
				obj.setStatus("FAIL");
				String msg="Combination of Customer_id, Line_no ,Sr_no i.e. ("+obj.getCustomer()+","+obj.getLine()+","+obj.getSerialNo()+") Available in "+facilitySystem+ " not in CLIMS.";
				obj.setReason(msg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;	
	}
	
	public OBCommonFile updateLmtUtilAmtActual(OBCommonFile obj,String facilitySystem,ConcurrentMap<String, String> dataFromUpdLineFacilityActual) {
		
		String sys_xref_id="";
		String cur="";
		try{		
			List selectLmtUtilAmtActual=selectLmtUtilAmtActualRevised( obj.getCustomer(),obj.getLine(), obj.getSerialNo(),facilitySystem,dataFromUpdLineFacilityActual);
			DefaultLogger.info(this, "updateLmtUtilAmtActual size:::"+selectLmtUtilAmtActual.size()+"::");
			DefaultLogger.info(this, "selectLmtUtilAmtStage selectLmtUtilAmtActual List is::"+selectLmtUtilAmtActual+"::");
			if(selectLmtUtilAmtActual!=null && selectLmtUtilAmtActual.size()>=1)
			{
				String[] arr = (String[]) selectLmtUtilAmtActual.get(0);
				sys_xref_id = arr[0];
				DefaultLogger.info(this,"updateLmtUtilAmtActual() sys_xref_id::::"+sys_xref_id);
				cur = arr[1];
				DefaultLogger.info(this,"updateLmtUtilAmtActual() cur::::"+cur+":::::");
			}
			
			DefaultLogger.info(this,"updateLmtUtilAmtActual() obj.getCurrency()::::"+obj.getCurrency()+":::::");
			
			DefaultLogger.info(this,"updateLmtUtilAmtActual() 1:::: "+(obj.getCurrency()!=null)+":::::");
			DefaultLogger.info(this,"updateLmtUtilAmtActual() 2::::"+(!"".equals(obj.getCurrency()))+":::::");
			DefaultLogger.info(this,"updateLmtUtilAmtActual() 3::::"+(obj.getCurrency().equals(cur))+":::::");
			DefaultLogger.info(this,"updateLmtUtilAmtActual() 4::::"+(!obj.getCurrency().equals(cur))+":::::");
			DefaultLogger.info(this,"updateLmtUtilAmtActual() 5::::"+(!"".equals(cur))+":::::");
			
			if(obj.getCurrency()!=null && !"".equals(obj.getCurrency()) && cur!=null && !"".equalsIgnoreCase(cur.trim())  && obj.getCurrency().equals(cur.trim())){
				DefaultLogger.info(this," First condition!! ");
				obj.setSysXrefId(Long.parseLong(sys_xref_id));
				obj.setStatus("PASS");
				String msg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+obj.getCustomer()+","+obj.getLine()+","+obj.getSerialNo()+","+obj.getCurrency()+") Matched in CLIMS and updated.";
				obj.setReason(msg);
			}else if(obj.getCurrency()!=null && cur!=null && !"".equals(cur) && !"".equalsIgnoreCase(cur.trim()) &&!obj.getCurrency().equals(cur.trim())){
				DefaultLogger.info(this," Second condition!! ");
				obj.setSysXrefId(0);
				obj.setStatus("FAIL");
				String msg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+obj.getCustomer()+","+obj.getLine()+","+obj.getSerialNo()+","+obj.getCurrency()+") Currency not matched";
				obj.setReason(msg);
			}else{
				DefaultLogger.info(this," else condition!! ");
				obj.setSysXrefId(0);
				obj.setStatus("FAIL");
				String msg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+obj.getCustomer()+","+obj.getLine()+","+obj.getSerialNo()+","+obj.getCurrency()+") Available in "+facilitySystem+ "not in CLIMS.";
				obj.setReason(msg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;	

	}
	
	public OBCommonFile updateLmtUtilAmtFinwareActual(OBCommonFile obj,String facilitySystem,ConcurrentMap<String, String> dataFromUpdLineFacilityActual) {
		String sys_xref_id="";
	//	String cur="";
		try{	

			List selectLmtUtilAmtActual=selectLmtUtilAmtActualRevised(obj.getCustomer(),obj.getLine(), obj.getSerialNo(),facilitySystem,dataFromUpdLineFacilityActual);
			if(selectLmtUtilAmtActual!=null && selectLmtUtilAmtActual.size()>=1)
			{
				String[] arr = (String[]) selectLmtUtilAmtActual.get(0);
				sys_xref_id = arr[0];
				//cur = arr[1];
			}
			if(sys_xref_id!=null && !"".equals(sys_xref_id)){
				obj.setSysXrefId(Long.parseLong(sys_xref_id));
				obj.setStatus("PASS");
				String msg="Combination of Customer_id i.e. ("+obj.getCustomer()+") Matched in CLIMS and updated.";
				obj.setReason(msg);
			}else{
				obj.setSysXrefId(0);
				obj.setStatus("FAIL");
				String msg="Combination of Customer_id i.e. ("+obj.getCustomer()+") Available in "+facilitySystem+ "not in CLIMS.";
				obj.setReason(msg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;	

	}

	public OBCommonFile updateLmtUtilAmtFinwareStage(OBCommonFile obj, String facilitySystem,ConcurrentMap<String, String> dataFromUpdLineFacilityMV) {
		int i=0;
		String sys_xref_id="";
	//	String cur="";
		try{	
			List selectLmtUtilAmtStage=selectLmtUtilAmtStageRevised( obj.getCustomer(),obj.getLine(), obj.getSerialNo(),facilitySystem,dataFromUpdLineFacilityMV);
			if(selectLmtUtilAmtStage!=null && selectLmtUtilAmtStage.size()>=1)
			{
				String[] arr = (String[]) selectLmtUtilAmtStage.get(0);
				sys_xref_id = arr[0];
				//cur = arr[1];
			}
			if(sys_xref_id!=null && !"".equals(sys_xref_id)){
				obj.setSysXrefId(Long.parseLong(sys_xref_id));
				obj.setStatus("PASS");
				String msg="Combination of Customer_id i.e. ("+obj.getCustomer()+") Matched in CLIMS and updated.";
				obj.setReason(msg);
			}else{
				obj.setSysXrefId(0);
				obj.setStatus("FAIL");
				String msg="Combination of Customer_id i.e. ("+obj.getCustomer()+") Available in "+facilitySystem+ "not in CLIMS.";
				obj.setReason(msg);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;	

	}

	public List selectLmtUtilAmtStageRevised(String FACILITY_SYSTEM_ID,String LINE_NO,String SERIAL_NO,String FACILITY_SYSTEM,ConcurrentMap<String, String> dataFromUpdLineFacilityMV)
	{
		List lstCustDetails=new ArrayList();
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String[] data= new String[3];
	    // Added and commented by : Dayananda for FC-UPLOAD changes || CSV format || Starts
		/*String str = "select stg_xref.cms_lsp_sys_xref_id as cms_lsp_sys_xref_id,stg_lmt.lmt_crrncy_iso_code,stg_lmt.cms_lsp_appr_lmts_id"  +
		" from CMS_STAGE_LSP_SYS_XREF stg_xref,stage_limit_xref stg_xref_map,stage_limit stg_lmt , transaction t"+
		" WHERE stg_xref.FACILITY_SYSTEM_ID = '"+ FACILITY_SYSTEM_ID+"'"+
		" and t.transaction_type = 'LIMIT' "+
		" and stg_xref.cms_lsp_sys_xref_id = stg_xref_map.cms_lsp_sys_xref_id "+
		" and stg_lmt.cms_lsp_appr_lmts_id = stg_xref_map.cms_lsp_appr_lmts_id "+
		" and stg_lmt.cms_lsp_appr_lmts_id = t.staging_reference_id "+
		" and stg_lmt.cms_limit_status = 'ACTIVE' "+
		" and t.status = 'ACTIVE' ";
		if(!FACILITY_SYSTEM.equals("FINWARE")){
		 str = str+" AND stg_xref.LINE_NO= '"+LINE_NO+"'"+
		" AND stg_xref.SERIAL_NO= '"+SERIAL_NO+"'"+
		" AND stg_xref.FACILITY_SYSTEM = '"+FACILITY_SYSTEM+"'" ;	
		}
		 //" AND stg_xref.LINE_NO= '"+LINE_NO+"'"+
		// " AND stg_xref.SERIAL_NO= '"+SERIAL_NO+"'";
		if(FACILITY_SYSTEM.equals("FINWARE")){
			str = str+" AND stg_xref.FACILITY_SYSTEM in ('FW-LIMITS','FCNCB') " ;
		}
		else{
			str = str+" AND stg_xref.FACILITY_SYSTEM = '"+FACILITY_SYSTEM+"'" ;	
		}

		
		
		String str = "SELECT * FROM UPD_LINE_FAC_MV ";
		
		if(!FACILITY_SYSTEM.equals("FINWARE")){
			 str = str+" where LINE_NO= '"+LINE_NO+"'"+
			" AND SERIAL_NO= '"+SERIAL_NO+"'"+
			" AND FACILITY_SYSTEM = '"+FACILITY_SYSTEM+"'" ;	
			}
			
			if(FACILITY_SYSTEM.equals("FINWARE")){
				str = str+" where FACILITY_SYSTEM in ('FW-LIMITS','FCNCB') " ;
			}
			*/
	
		try {
			/*dbUtil=new DBUtil();
			dbUtil.setSQL(str);
			rs = dbUtil.executeQuery();			
			while(rs.next())
			{
				data[0]=rs.getString("cms_lsp_sys_xref_id");
				data[1]=rs.getString("lmt_crrncy_iso_code");
				data[2]=rs.getString("cms_lsp_appr_lmts_id");
				lstCustDetails.add(data);
			}*/
			String concanatedValue= getSysXrefIdAndlmtCrrncyIsoCd(FACILITY_SYSTEM_ID,LINE_NO, SERIAL_NO, FACILITY_SYSTEM, dataFromUpdLineFacilityMV);
			DefaultLogger.debug(this, "#####################In FileUploadJdbcImpl ##### concanatedValue String is:  851 #######:: "+concanatedValue);
			System.out.println("In FileUploadJdbcImpl ##### concanatedValue String is:  851 #######:: "+concanatedValue);
			if(null!=concanatedValue)
			{
				data=concanatedValue.split("~");
				lstCustDetails.add(data);
			}
			
			//  Added and commented by : Dayananda for FC-UPLOAD changes || CSV format || Ends
			
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return lstCustDetails;
	}

	public List selectLmtUtilAmtActualRevised(String FACILITY_SYSTEM_ID,String LINE_NO,String SERIAL_NO,String FACILITY_SYSTEM,ConcurrentMap<String, String> dataFromUpdLineFacilityActual)
	{
		List lstCustDetails=new ArrayList();
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String[] data= new String[3];

		/*String str = "select xref.cms_lsp_sys_xref_id as cms_lsp_sys_xref_id,lmt.lmt_crrncy_iso_code ,lmt.cms_lsp_appr_lmts_id" +
		" from SCI_LSP_SYS_XREF xref,sci_lsp_lmts_xref_map xref_map,sci_lsp_appr_lmts lmt, transaction t "+
		" WHERE xref.FACILITY_SYSTEM_ID = '"+ FACILITY_SYSTEM_ID+"'"+
		" and t.transaction_type = 'LIMIT' "+
		" and xref.cms_lsp_sys_xref_id = xref_map.cms_lsp_sys_xref_id "+
		" and lmt.cms_lsp_appr_lmts_id = xref_map.cms_lsp_appr_lmts_id "+
		" and lmt.cms_lsp_appr_lmts_id = t.reference_id "+
		" and lmt.cms_limit_status = 'ACTIVE' "+
		" and t.status = 'ACTIVE' ";
		//if(!FACILITY_SYSTEM.equals("FINWARE")){
		// str = str+" AND xref.LINE_NO= '"+LINE_NO+"'"+
		//" AND xref.SERIAL_NO= '"+SERIAL_NO+"'"+
		//" AND xref.FACILITY_SYSTEM = '"+FACILITY_SYSTEM+"'" ;	
		//}
		//" AND xref.LINE_NO= '"+LINE_NO+"'"+
		//" AND xref.SERIAL_NO= '"+SERIAL_NO+"'";
		//if(FACILITY_SYSTEM.equals("FINWARE")){
			str = str+" AND xref.FACILITY_SYSTEM in ('FW-LIMITS','FCNCB') " ;
		//}
		else{
			str = str+" AND xref.FACILITY_SYSTEM = '"+FACILITY_SYSTEM+"'" ;	
		}*/


		
		try {
			/*//dbUtil=new DBUtil();
			//dbUtil.setSQL(str);
			//rs = dbUtil.executeQuery();			
			//while(rs.next())
			//{
				data[0]=rs.getString("cms_lsp_sys_xref_id");
				data[1]=rs.getString("lmt_crrncy_iso_code");
				data[2]=rs.getString("cms_lsp_appr_lmts_id");
				lstCustDetails.add(data);
			//}
*/				
				String concanatedValue= getSysXrefIdAndlmtCrrncyIsoCd(FACILITY_SYSTEM_ID,LINE_NO, SERIAL_NO, FACILITY_SYSTEM, dataFromUpdLineFacilityActual);
				DefaultLogger.debug(this, "#####################In FileUploadJdbcImpl ##### concanatedValue String is:  851 #######:: "+concanatedValue);
				System.out.println("In FileUploadJdbcImpl ##### concanatedValue String is:  918 #######:: "+concanatedValue);
				if(null!=concanatedValue)
				{//dataFromCacheView.putIfAbsent(key.toString(),rs.getString("cms_lsp_sys_xref_id")+"~"+rs.getString("lmt_crrncy_iso_code")+"~"+rs.getString("cms_lsp_appr_lmts_id"));
					data=concanatedValue.split("~");
					lstCustDetails.add(data);
				}

		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return lstCustDetails;
	}



	public HashMap insertUbsfileActual( ArrayList result,CheckerApproveFileUploadCmd cmd,String fileName,ICommonUser user,Date date,IFileUploadTrxValue trxValue,ConcurrentMap<String, String> dataFromUpdLineFacilityActual) {

		List totalUploadedList= new ArrayList();
		List errorList= new ArrayList();
		HashMap retMap = new HashMap();
		String upload_status="";
		Timestamp st = null;
		IUbsErrorLog objUbsError=new OBUbsErrorLog();
		String errMsg="";
		String tempData="";
		String strArrTemp[]=new String[3];
		List selectCustDetails=new ArrayList();
		String makerId = trxValue.getStagingfileUpload().getUploadBy();
		Date makerDate = trxValue.getStagingfileUpload().getUploadTime();
		try {
			if(result!=null&& result.size() != 0)
			{
				for (int index = 0; index < result.size(); index++) {
					OBUbsFile obj = (OBUbsFile) result.get(index);
					if(obj.getUploadStatus()!=null && obj.getUploadStatus().equals("Y") ){
						OBCommonFile object = obj;
						object.setMakerId(makerId);
						object.setMakerDate(makerDate);
						if(null != user) {
							object.setCheckerId(user.getLoginID());
						}
						object.setCheckerDate(date);
						//isUpdated=updateLmtUtilAmtActual(limit_Amt,util_Amt, cur_Code,upload_status,makerId,makerDate,user.getLoginID(),date,cust_id,line_No,sr_No,"UBS-LIMITS" );
						object=updateLmtUtilAmtActual(obj,"UBS-LIMITS",dataFromUpdLineFacilityActual );
						totalUploadedList.add(object);
						
					}
				}
			}
			
			int batchSize = 200;
			for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
				List<OBCommonFile> batchList = totalUploadedList.subList(j, j + batchSize > totalUploadedList.size() ? totalUploadedList.size() : j + batchSize);
				updateXrefActualAmount(batchList,"UBS-LIMITS");
			}
			
			st = new Timestamp(System.currentTimeMillis());
			date =new Date(st.getTime());
			selectCustDetails=selectCustDetailsActual("UbsFile");
			for(int i=0;i<selectCustDetails.size();i++)
			{				
				tempData=selectCustDetails.get(i).toString();
				strArrTemp=tempData.split(ICMSConstant.FILEUPLOAD_SEPERATOR);
				errMsg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+selectCustDetails.get(i)+")Available in CLIMS not in UBS.";
				IUbsErrDetLog obUbsErrDet=new OBUbsErrDetLog();
				OBUbsFile obUbs=new OBUbsFile();
//				obUbs.setCurrency("");					
				obUbs.setCustomer(strArrTemp[0]);
				obUbs.setLine(strArrTemp[1]);
				obUbs.setSerialNo(strArrTemp[2]);
				//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				obUbs.setCurrency(strArrTemp[3].trim());
				//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				obUbs.setStatus("FAIL");
				obUbs.setUploadStatus("N");
				obUbs.setReason(errMsg);
				errorList.add(obUbs);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
			"Unable to update/insert dad retrived form CSV file");

		}
		getJdbcTemplate().update(updateStatusActualUbs);
		retMap.put("totalUploadedList", totalUploadedList);
		retMap.put("errorList", errorList);
		return retMap;
	}

	public HashMap insertHongKongfileActual( ArrayList result,CheckerApproveFileUploadCmd cmd,String fileName,ICommonUser user,Date date,IFileUploadTrxValue trxValue,ConcurrentMap<String, String> dataFromUpdLineFacilityActual) {

		List totalUploadedList= new ArrayList();
		List errorList= new ArrayList();
		HashMap retMap = new HashMap();
		String upload_status="";
		Timestamp st = null;
		IUbsErrorLog objUbsError=new OBUbsErrorLog();
		String errMsg="";
		String tempData="";
		String strArrTemp[]=new String[3];
		List selectCustDetails=new ArrayList();
		String makerId = trxValue.getStagingfileUpload().getUploadBy();
		Date makerDate = trxValue.getStagingfileUpload().getUploadTime();
		try {
			if(result!=null&& result.size() != 0)
			{
				for (int index = 0; index < result.size(); index++) {
					OBHongKongFile obj = (OBHongKongFile) result.get(index);
					if(obj.getUploadStatus()!=null && obj.getUploadStatus().equals("Y")){
						OBCommonFile object = obj;
						object.setMakerId(makerId);
						object.setMakerDate(makerDate);
						if(null != user) {
							object.setCheckerId(user.getLoginID());
						}
						object.setCheckerDate(date);
						//isUpdated=updateLmtUtilAmtActual(limit_Amt,util_Amt,cur_Code,upload_status,makerId,makerDate,user.getLoginID(),date,cust_id,line_No,sr_No,"HONGKONG" );
						object=updateLmtUtilAmtActual(obj,"HONGKONG" ,dataFromUpdLineFacilityActual);
						totalUploadedList.add(object);
					}
				}
			}
			
			
			
			int batchSize = 200;
			for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
				List<OBCommonFile> batchList = totalUploadedList.subList(j, j + batchSize > totalUploadedList.size() ? totalUploadedList.size() : j + batchSize);
				updateXrefActualAmount(batchList,"HONGKONG");
			}
			
			
			st = new Timestamp(System.currentTimeMillis());
			date =new Date(st.getTime());
			selectCustDetails=selectCustDetailsActual("HongkongFile");
			for(int i=0;i<selectCustDetails.size();i++)
			{				
				tempData=selectCustDetails.get(i).toString();
				strArrTemp=tempData.split(ICMSConstant.FILEUPLOAD_SEPERATOR);
				errMsg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+selectCustDetails.get(i)+")Available in CLIMS not in HONGKONG.";
				OBHongKongFile obHongkong=new OBHongKongFile();
//				obHongkong.setCurrency("");					
				obHongkong.setCustomer(strArrTemp[0]);
				obHongkong.setLine(strArrTemp[1]);
				obHongkong.setSerialNo(strArrTemp[2]);
				//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				obHongkong.setCurrency(strArrTemp[3].trim());
				//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				obHongkong.setStatus("FAIL");
				obHongkong.setUploadStatus("N");
				obHongkong.setReason(errMsg);
				errorList.add(obHongkong);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
			"Unable to update/insert dad retrived form CSV file");

		}

		getJdbcTemplate().update(updateStatusActualHongkong);
		retMap.put("totalUploadedList", totalUploadedList);
		retMap.put("errorList", errorList);
		return retMap;
	}

	public HashMap insertFinwarefileActual( ArrayList result,CheckerApproveFileUploadCmd cmd,String fileName,ICommonUser user,Date date,IFileUploadTrxValue trxValue,ConcurrentMap<String, String> dataFromUpdLineFacilityActual) {

		List totalUploadedList= new ArrayList();
		List errorList= new ArrayList();
		HashMap retMap = new HashMap();
		String upload_status="";
		Timestamp st = null;
		IUbsErrorLog objUbsError=new OBUbsErrorLog();
		String errMsg="";
		String tempData="";
		String strArrTemp[]=new String[3];
		List selectCustDetails=new ArrayList();
		String makerId = trxValue.getStagingfileUpload().getUploadBy();
		Date makerDate = trxValue.getStagingfileUpload().getUploadTime();
		try {

			if(result!=null&& result.size() != 0)
			{
				for (int index = 0; index < result.size(); index++) {
					OBFinwareFile obj = (OBFinwareFile) result.get(index);
					if(obj.getUploadStatus()!=null && obj.getUploadStatus().equals("Y")){
						OBCommonFile object = obj;
						object.setMakerId(makerId);
						object.setMakerDate(makerDate);
						if(null != user) {
							object.setCheckerId(user.getLoginID());
						}
						object.setCheckerDate(date);
						//isUpdated=updateLmtUtilAmtFinwareActual(limit_Amt,util_Amt,cur_Code,upload_status,makerId,makerDate,user.getLoginID(),date,cust_id,line_No,sr_No,"FINWARE" );
						object=updateLmtUtilAmtFinwareActual(obj,"FINWARE",dataFromUpdLineFacilityActual );
						totalUploadedList.add(object);
					}
				}
			}
			
			int batchSize = 200;
			for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
				List<OBCommonFile> batchList = totalUploadedList.subList(j, j + batchSize > totalUploadedList.size() ? totalUploadedList.size() : j + batchSize);
			
				updateXrefFinwareActualAmount(batchList,"FINWARE");
			}
			
			
			
			
			st = new Timestamp(System.currentTimeMillis());
			date =new Date(st.getTime());
			selectCustDetails=selectCustDetailsActual("FinwareFile");
			for(int i=0;i<selectCustDetails.size();i++)
			{				
				tempData=selectCustDetails.get(i).toString();
				strArrTemp=tempData.split(ICMSConstant.FILEUPLOAD_SEPERATOR);
				errMsg="Combination of Customer_id, Line_no ,Sr_no i.e. ("+selectCustDetails.get(i)+")Available in CLIMS not in FINWARE.";
				OBFinwareFile obFinware=new OBFinwareFile();
				obFinware.setCurrency("");					
				obFinware.setCustomer(strArrTemp[0]);
				obFinware.setLine(strArrTemp[1]);
				obFinware.setSerialNo(strArrTemp[2]);
				obFinware.setStatus("FAIL");
				obFinware.setReason(errMsg);
				obFinware.setUploadStatus("N");
				errorList.add(obFinware);
			}


		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
			"Unable to update/insert dad retrived form CSV file");

		}

		getJdbcTemplate().update(updateStatusActualFinware);
		retMap.put("totalUploadedList", totalUploadedList);
		retMap.put("errorList", errorList);
		return retMap;
	}


	public HashMap insertBahrainfileActual( ArrayList result,CheckerApproveFileUploadCmd cmd,String fileName,ICommonUser user,Date date,IFileUploadTrxValue trxValue,ConcurrentMap<String, String> dataFromUpdLineFacilityActual) {

		List totalUploadedList= new ArrayList();
		List errorList= new ArrayList();
		HashMap retMap = new HashMap();
		String upload_status="";
		Timestamp st = null;
		IUbsErrorLog objUbsError=new OBUbsErrorLog();
		String errMsg="";
		String tempData="";
		String strArrTemp[]=new String[3];
		List selectCustDetails=new ArrayList();
		String makerId = trxValue.getStagingfileUpload().getUploadBy();
		Date makerDate = trxValue.getStagingfileUpload().getUploadTime();
		try {

			if(result!=null&& result.size() != 0)
			{
				for (int index = 0; index < result.size(); index++) {
					OBBahrainFile obj = (OBBahrainFile) result.get(index);
					if(obj.getUploadStatus()!=null && obj.getUploadStatus().equals("Y")){
						OBCommonFile object = obj;
						object.setMakerId(makerId);
						object.setMakerDate(makerDate);
						if(null != user) {
							object.setCheckerId(user.getLoginID());
						}
						object.setCheckerDate(date);
						//isUpdated=updateLmtUtilAmtActual(limit_Amt,util_Amt,cur_Code, upload_status,makerId,makerDate,user.getLoginID(),date,cust_id,line_No,sr_No,"BAHRAIN" );
						object=updateLmtUtilAmtActual(obj,"BAHRAIN",dataFromUpdLineFacilityActual );
						totalUploadedList.add(obj);
					}
				}
			}
			int batchSize = 200;
			for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
				List<OBCommonFile> batchList = totalUploadedList.subList(j, j + batchSize > totalUploadedList.size() ? totalUploadedList.size() : j + batchSize);
				updateXrefActualAmount(batchList,"BAHRAIN");
			}
			
			
			st = new Timestamp(System.currentTimeMillis());
			date =new Date(st.getTime());
			selectCustDetails=selectCustDetailsActual("BahrainFile");
			for(int i=0;i<selectCustDetails.size();i++)
			{				
				tempData=selectCustDetails.get(i).toString();
				strArrTemp=tempData.split(ICMSConstant.FILEUPLOAD_SEPERATOR);
				errMsg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+selectCustDetails.get(i)+")Available in CLIMS not in BAHRAIN.";
				IUbsErrDetLog obUbsErrDet=new OBUbsErrDetLog();
				OBBahrainFile obBahrain=new OBBahrainFile();
//				obBahrain.setCurrency("");					
				obBahrain.setCustomer(strArrTemp[0]);
				obBahrain.setLine(strArrTemp[1]);
				obBahrain.setSerialNo(strArrTemp[2]);
				//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				obBahrain.setCurrency(strArrTemp[3].trim());
				//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				obBahrain.setStatus("FAIL");
				obBahrain.setUploadStatus("N");
				obBahrain.setReason(errMsg);
				errorList.add(obBahrain);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
			"Unable to update/insert dad retrived form CSV file");

		}
		getJdbcTemplate().update(updateStatusActualBahrain);
		retMap.put("totalUploadedList", totalUploadedList);
		retMap.put("errorList", errorList);
		return retMap;
	}


	public SearchResult getAllUbsFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=STAGE_UBS_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new UbsFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving UBS File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public SearchResult getAllPartyCamFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=STAGE_PARTYCAM_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new PartyCamFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving Party Cam File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public SearchResult getAllBulkUDFFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			DefaultLogger.debug(this,"<<<<----transaction staging id getAllBulkUDFFile()---->>>"+id);
			String sql= STAGE_TEMP_BULKUDF_FILE+id+"'";
			DefaultLogger.debug(this,"<<<<----Query getAllBulkUDFFile()---->>>"+sql);
			resultList = getJdbcTemplate().query(sql,new BulkUDFFileRowMapper());
			
		} catch(Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this,"<<<<----ERROR-- While retriving Bulk UDF File List---->>>");
//			throw new FileUploadException("ERROR-- While retriving Bulk UDF File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;		
	}
	
	public SearchResult getAllLeiDetailsFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=STAGE_LEIDETAILS_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new LeiDetailsFileRowMapper());

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this,"<<<<----ERROR-- While retriving Party Cam File List---->>>");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}

	public SearchResult getAllBahrainFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=STAGE_BAHRAIN_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new BahrainFileRowMapper());

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this,"<<<<----ERROR-- While retriving UBS File List---->>>");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}

	public SearchResult getAllActualUbsFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=ACTUAL_UBS_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new UbsFileRowMapper());

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this,"<<<<----ERROR-- While retriving UBS File List---->>>");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public SearchResult getAllActualPartyCamFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=ACTUAL_PARTYCAM_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new PartyCamFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving Party Cam File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public SearchResult getAllActualLeiDetailsFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=ACTUAL_LEIDETAILS_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new LeiDetailsFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving Party Cam File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}

	public SearchResult getAllActualBahrainFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=ACTUAL_BAHRAIN_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new BahrainFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving UBS File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}

	public SearchResult getAllHongKongFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=STAGE_HONGKONG_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new HongKongFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving HongKong File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}

	public SearchResult getAllActualHongKongFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=ACTUAL_HONGKONG_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new HongKongFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving HongKong File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}

	public SearchResult getAllFinwareFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=STAGE_FINWARE_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new FinwareFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving Finware File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}

	public SearchResult getAllActualFinwareFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=ACTUAL_FINWARE_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new FinwareFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving Finware File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}

	public class UbsFileRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBUbsFile result = new OBUbsFile();
			result.setId(rs.getLong("id"));
			result.setCurrency(rs.getString("CURRENCY"));
			result.setCustomer(rs.getString("CUSTOMER_ID"));
			result.setLimit(rs.getDouble("LIMIT_AMOUTNT"));
			result.setLine(rs.getString("LINE_NO"));
			result.setSerialNo(rs.getString("SERIAL_NO"));
			result.setUtilize(rs.getDouble("UTILIZATION_AMOUNT"));
			result.setReason(rs.getString("REASON"));
			result.setStatus(rs.getString("STATUS"));
			result.setUploadStatus(rs.getString("UPLOAD_STATUS"));

			return result;
		}
	}

	public class HongKongFileRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBHongKongFile result = new OBHongKongFile();
			result.setId(rs.getLong("id"));
			result.setCurrency(rs.getString("CURRENCY"));
			result.setCustomer(rs.getString("CUSTOMER_ID"));
			result.setLimit(rs.getDouble("LIMIT_AMOUTNT"));
			result.setLine(rs.getString("LINE_NO"));
			result.setSerialNo(rs.getString("SERIAL_NO"));
			result.setUtilize(rs.getDouble("UTILIZATION_AMOUNT"));
			result.setReason(rs.getString("REASON"));
			result.setStatus(rs.getString("STATUS"));
			result.setUploadStatus(rs.getString("UPLOAD_STATUS"));
			return result;
		}
	}

	public class FinwareFileRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBFinwareFile result = new OBFinwareFile();
			result.setId(rs.getLong("id"));
			result.setCurrency(rs.getString("CURRENCY"));
			result.setCustomer(rs.getString("CUSTOMER_ID"));
			result.setLimit(rs.getDouble("LIMIT_AMOUTNT"));
			result.setLine(rs.getString("LINE_NO"));
			result.setSerialNo(rs.getString("SERIAL_NO"));
			result.setUtilize(rs.getDouble("UTILIZATION_AMOUNT"));
			result.setReason(rs.getString("REASON"));
			result.setStatus(rs.getString("STATUS"));
			result.setUploadStatus(rs.getString("UPLOAD_STATUS"));
			return result;
		}
	}

	public class BahrainFileRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBBahrainFile result = new OBBahrainFile();
			result.setId(rs.getLong("id"));
			result.setCurrency(rs.getString("CURRENCY"));
			result.setCustomer(rs.getString("CUSTOMER_ID"));
			result.setLimit(rs.getDouble("LIMIT_AMOUTNT"));
			result.setLine(rs.getString("LINE_NO"));
			result.setSerialNo(rs.getString("SERIAL_NO"));
			result.setUtilize(rs.getDouble("UTILIZATION_AMOUNT"));
			result.setReason(rs.getString("REASON"));
			result.setStatus(rs.getString("STATUS"));
			result.setUploadStatus(rs.getString("UPLOAD_STATUS"));
			return result;
		}
	}

	public class PartyCamFileRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			//TODO
			OBPartyCamFile result = new OBPartyCamFile();
			result.setId(rs.getLong("ID"));
			result.setFileId(rs.getLong("FILE_ID"));
			result.setPartyId(rs.getString("PARTY_ID"));
			result.setCamDate(rs.getDate("CAM_DATE"));
			result.setCamLoginDate(rs.getDate("CAM_LOGIN_DATE"));
			result.setRamRating(rs.getInt("RAM_RATING"));
			if(rs.wasNull()){
				result.setRamRating(null);
			}
			result.setRamRatingYear(rs.getInt("RAM_RATING_YEAR"));
			if(rs.wasNull()){
				result.setRamRatingYear(null);
			}
			result.setCustomerRamId(rs.getLong("CUSTOMER_RAM_ID"));
			if(rs.wasNull()){
				result.setCustomerRamId(null);
			}
			result.setCamExpiryDate(rs.getDate("CAM_EXPIRY_DATE"));
			result.setCamExtensionDate(rs.getDate("CAM_EXTENSION_DATE"));
			result.setRamType(rs.getString("RATING_TYPE"));
			result.setRmEmployeeCode(rs.getString("RM_EMPLOYEE_CODE"));
			result.setRamFinalizationDate(rs.getDate("RAM_RATING_FINALIZATION_DATE"));
			result.setReason(rs.getString("REASON"));
			result.setStatus(rs.getString("STATUS"));
			result.setUploadStatus(rs.getString("UPLOAD_STATUS"));
			result.setFundedAmount(String.valueOf(rs.getDouble("TOTAL_FUNDED_LIMIT")));
			if(rs.wasNull()){
				result.setFundedAmount(null);
			}
			result.setNonfundedAmount(String.valueOf(rs.getDouble("TOTAL_NON_FUNDED_LIMIT")));
			if(rs.wasNull()){
				result.setNonfundedAmount(null);
			}
			return result;
		}
	}
	
	public class BulkUDFFileRowMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBTempBulkUDFFileUpload bulkUdf = new OBTempBulkUDFFileUpload();
			bulkUdf.setId(rs.getLong("ID"));
			bulkUdf.setFileId(rs.getLong("FILE_ID"));
        	bulkUdf.setTypeOfUDF(rs.getString("TYPE_OF_UDF"));
        	bulkUdf.setPartyID(rs.getString("PARTY_ID"));
        	bulkUdf.setCamNo(rs.getString("CAM_NO"));
        	bulkUdf.setSystemId(rs.getString("SYSTEM_ID"));
        	bulkUdf.setLineNumber(rs.getString("LINE_NO"));
        	bulkUdf.setSerialNumber(rs.getString("SERIAL_NO"));
        	bulkUdf.setLiabBranch(rs.getString("LIAB_BRANCH"));
        	bulkUdf.setUdfFieldSequence(rs.getString("UDF_FIELD_SEQUENCE"));
        	bulkUdf.setUdfFieldName(rs.getString("UDF_FIELD_NAME"));
        	bulkUdf.setUdfFieldValue(rs.getString("UDF_FIELD_VALUE"));
        	bulkUdf.setValid(rs.getString("VALID"));
        	bulkUdf.setRemarks(rs.getString("REMARKS"));
        	bulkUdf.setReason(rs.getString("REASON"));
        	bulkUdf.setStatus(rs.getString("STATUS"));
        	bulkUdf.setUploadStatus(rs.getString("UPLOAD_STATUS"));
        	bulkUdf.setCMS_LE_MAIN_PROFILE_ID(rs.getString("CMS_LE_MAIN_PROFILE_ID"));
          	bulkUdf.setCMS_LSP_LMT_PROFILE_ID(rs.getString("CMS_LSP_LMT_PROFILE_ID"));
        	bulkUdf.setSCI_LSP_SYS_XREF_ID(rs.getString("SCI_LSP_SYS_XREF_ID"));
		
			return bulkUdf;
		}
		
	}
	
	public class AcknowledgmentFileRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBAcknowledgmentFile result = new OBAcknowledgmentFile();
			result.setId(rs.getLong("ID"));
			result.setFileId(rs.getLong("FILE_ID"));
			result.setSecurityID(rs.getString("SECURITY_ID"));
			result.setCerTrnRefNo(rs.getString("CERSAI_TRX_REF_NO"));
			result.setCerSecIntID(rs.getString("CERSAI_SECURITY_INTEREST_ID"));
			result.setCerAssetID(rs.getString("CERSAI_ASSET_ID"));
			result.setRegistrationDate(rs.getDate("DATE_CERSAI_REG"));
			result.setReason(rs.getString("REASON"));
			result.setStatus(rs.getString("STATUS"));
			result.setUploadStatus(rs.getString("UPLOAD_STATUS"));
			return result;
		}
	}
	
	public class LeiDetailsFileRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBLeiDetailsFile result = new OBLeiDetailsFile();
			result.setId(rs.getLong("ID"));
			result.setFileId(rs.getLong("FILE_ID"));
			result.setPartyId(rs.getString("PARTY_ID"));
			result.setLeiCode(rs.getString("LEI_CODE"));
			result.setLeiExpDate(rs.getDate("LEI_EXP_DATE"));
			result.setReason(rs.getString("REASON"));
			result.setStatus(rs.getString("STATUS"));
			result.setUploadStatus(rs.getString("UPLOAD_STATUS"));
			return result;
		}
	}
	

	public int getRecordNo(Set set,int recordno)
	{
		for(int i=1;i<=set.size();i++)
		{
			if(set.contains(recordno+1+""))
			{
				recordno++;
			}
			else
			{
				break;
			}
		}
		return recordno;
	}

	public void createEntireUbsStageFile(List<OBUbsFile> objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO STAGE_UBS_FILE_UPLOAD " +
		"( ID, FILE_ID,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY, LIMIT_AMOUTNT,UTILIZATION_AMOUNT,STATUS, REASON,UPLOAD_STATUS)" +
		" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ? , ?)";

		final List<OBUbsFile> objectList = objList;
		final int batchSize = objList.size();
		/*for (int j = 0; j < objectList.size(); j += batchSize) {
			final List batchList = objectList.subList(j, j + batchSize > objectList.size() ? objectList.size() : j + batchSize);
			DefaultLogger.debug(this, "#####batch inserted#######:: "+j);*/
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBUbsFile ubsFile = objectList.get(i);
					ps.setLong(1,ubsFile.getFileId());
					ps.setString(2,ubsFile.getCustomer());
					ps.setString(3,ubsFile.getLine());
					ps.setString(4,ubsFile.getSerialNo());
					ps.setString(5,ubsFile.getCurrency());
					ps.setDouble(6,ubsFile.getLimit());
					ps.setDouble(7,ubsFile.getUtilize());
					ps.setString(8,ubsFile.getStatus());
					ps.setString(9,ubsFile.getReason());
					ps.setString(10,ubsFile.getUploadStatus());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
		//}
	}
	public void insertReport(final ArrayList<OBExchangeRateAutoUpload> repostList)throws FileUploadException,SQLException{


		String sql = "INSERT INTO cms_exchange_rate_report " +
		"(currency_code,exchange_rate_old,exchange_rate_new,exchange_date,upload_status, file_name,upload_time,upload_status_message,upload_date)" +
		" VALUES (?, ? ,?, ?, ?,?, ?, ? ,?)";

		final int batchSize = repostList.size();
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBExchangeRateAutoUpload autoUpload = repostList.get(i);
					ps.setString(1,autoUpload.getCurrency_code());
					ps.setBigDecimal(2,autoUpload.getExchange_rate_old());
					ps.setBigDecimal(3,autoUpload.getExchange_rate_new());
					ps.setDate(4,autoUpload.getExchange_date());
					ps.setString(5,autoUpload.getUpload_status());
					ps.setString(6,autoUpload.getFile_name());
					ps.setString(7,autoUpload.getUpload_time());
					ps.setString(8,autoUpload.getUpload_status_message());
					ps.setDate(9,autoUpload.getUpload_date());
				}
				public int getBatchSize() {
					return batchSize;
				}
			});
		//}
	
	}
	
	public void createEntirePartyCamStageFile(List<OBPartyCamFile> objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO STAGE_PARTYCAM_FILE_UPLOAD " +
		"( ID, FILE_ID,PARTY_ID,CAM_DATE,CAM_LOGIN_DATE,RAM_RATING, RAM_RATING_YEAR,CUSTOMER_RAM_ID,CAM_EXPIRY_DATE, "
		+ "CAM_EXTENSION_DATE,STATUS,REASON,UPLOAD_STATUS,RATING_TYPE,RAM_RATING_FINALIZATION_DATE,RM_EMPLOYEE_CODE,TOTAL_FUNDED_LIMIT,TOTAL_NON_FUNDED_LIMIT)" +
		" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ? , ? , ? , ? , ? , ? ,?, ?,?)";


		final List<OBPartyCamFile> objectList = objList;
		final int batchSize = objList.size();
		/*for (int j = 0; j < objectList.size(); j += batchSize) {
			final List batchList = objectList.subList(j, j + batchSize > objectList.size() ? objectList.size() : j + batchSize);
			DefaultLogger.debug(this, "#####batch inserted#######:: "+j);*/
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBPartyCamFile partyCamFile = objectList.get(i);
					ps.setLong(1,partyCamFile.getFileId());
					ps.setString(2,partyCamFile.getPartyId());
					if(null==partyCamFile.getCamDate()){
						ps.setNull(3,Types.TIMESTAMP);	
					}else{
					ps.setTimestamp(3,new Timestamp(partyCamFile.getCamDate().getTime()));
					}
					if(null==partyCamFile.getCamLoginDate()){
						ps.setNull(4,Types.TIMESTAMP);	
					}else{
					ps.setTimestamp(4,new Timestamp(partyCamFile.getCamLoginDate().getTime()));
					}
					if(null==partyCamFile.getRamRating()){
						ps.setNull(5, Types.INTEGER);
					
					}else{
						ps.setInt(5,partyCamFile.getRamRating());
					}
					if(null==partyCamFile.getRamRatingYear()){
						ps.setNull(6, Types.INTEGER);
					}else{
						ps.setInt(6,partyCamFile.getRamRatingYear());	
					}
					if(null == partyCamFile.getCustomerRamId()){
						ps.setNull(7,Types.NUMERIC);
					}else{
					ps.setLong(7,partyCamFile.getCustomerRamId());
					}
					if(null==partyCamFile.getCamExpiryDate()){
						ps.setNull(8,Types.TIMESTAMP);	
					}else{
					ps.setTimestamp(8,new Timestamp(partyCamFile.getCamExpiryDate().getTime()));
					}
					if(null==partyCamFile.getCamExtensionDate()){
						ps.setNull(9,Types.TIMESTAMP);	
					}else{
					ps.setTimestamp(9,new Timestamp(partyCamFile.getCamExtensionDate().getTime()));
					}
					ps.setString(10,partyCamFile.getStatus());
					ps.setString(11,partyCamFile.getReason());
					ps.setString(12,partyCamFile.getUploadStatus());
					if(null==partyCamFile.getRamType()){
						ps.setNull(13,Types.VARCHAR);	
					}else{
					ps.setString(13,partyCamFile.getRamType());
					}
					if(null==partyCamFile.getRamFinalizationDate()){
						ps.setNull(14,Types.TIMESTAMP);	
					}else{
					ps.setTimestamp(14,new Timestamp(partyCamFile.getRamFinalizationDate().getTime()));
					}
					ps.setString(15,partyCamFile.getRmEmployeeCode());
					try{
						System.out.println(" partyCamFile.getFundedAmount()=>"+partyCamFile.getFundedAmount());
					if(null == partyCamFile.getFundedAmount()){
						ps.setNull(16,Types.DOUBLE);
					}else{
						ps.setBigDecimal(16,new BigDecimal(partyCamFile.getFundedAmount()));
					}
					}catch(Exception e){
						System.out.println("Exception at line number 2190=> e=>"+e);
						e.printStackTrace();
					}
					//MORATORIUM REGULATORY UPLOAD
					//DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 1972 #######:: non funded amount ##### "+Double.parseDouble(partyCamFile.getNonfundedAmount()));
				//	DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 1972 #######:: non funded amount ##### "+new BigDecimal(partyCamFile.getNonfundedAmount()));
					try{
						System.out.println(" getNonfundedAmount()=>"+partyCamFile.getNonfundedAmount());
					
					if(null == partyCamFile.getNonfundedAmount()){
						ps.setNull(17,Types.DOUBLE);
					}else{
					ps.setBigDecimal(17,new BigDecimal(partyCamFile.getNonfundedAmount()));
					}
					}catch(Exception e){
						System.out.println("Exception at line number 2206=> e=>"+e);
						e.printStackTrace();
					}
					

				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
		//}
	}
	
	public void updateXrefStageAmount(List<OBCommonFile> objList,String facilitySystem)throws FileUploadException,SQLException{

		final List<OBCommonFile> objectList = objList;
		final int batchSize = objList.size();
		 final String system = facilitySystem;
		/*for (int j = 0; j < objectList.size(); j += batchSize) {
			final List batchList = objectList.subList(j, j + batchSize > objectList.size() ? objectList.size() : j + batchSize);
			DefaultLogger.debug(this, "#####batch inserted#######:: "+j);*/
			getJdbcTemplate().batchUpdate(updateLmtUtilAmtStage,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBCommonFile ubsFile = objectList.get(i);
					// 03/02/2016: Uma Khot:Commented below code as not to update Released Amount from upload file.
				//	ps.setDouble(1,ubsFile.getLimit());
					ps.setDouble(1,ubsFile.getUtilize());
					ps.setString(2,ubsFile.getUploadStatus());
					ps.setString(3,ubsFile.getCustomer());
					ps.setString(4,ubsFile.getLine());
					ps.setString(5,ubsFile.getSerialNo());
					ps.setString(6,system);
					ps.setLong(7,ubsFile.getSysXrefId());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
//		}
	}
	
	
	public void updateXrefFinwareStageAmount( List<OBCommonFile> objList,String facilitySystem)throws FileUploadException,SQLException{

		final List<OBCommonFile> objectList = objList;
		final int batchSize = objList.size();
		 final String system = facilitySystem;
		/*for (int j = 0; j < objectList.size(); j += batchSize) {
			final List batchList = objectList.subList(j, j + batchSize > objectList.size() ? objectList.size() : j + batchSize);
			DefaultLogger.debug(this, "#####batch inserted#######:: "+j);*/
			getJdbcTemplate().batchUpdate(updateLmtUtilAmtFinwareStage,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBCommonFile ubsFile = objectList.get(i);
					 // 03/02/2016: Uma Khot:Commented below code as not to update Released Amount from upload file.
					//ps.setDouble(1,ubsFile.getLimit());
					ps.setDouble(1,ubsFile.getUtilize());
					ps.setString(2,ubsFile.getUploadStatus());
					ps.setString(3,ubsFile.getCustomer());
				//	ps.setString(5,ubsFile.getLine());
				//	ps.setString(6,ubsFile.getSerialNo());
				//	ps.setString(7,system);
					ps.setLong(4,ubsFile.getSysXrefId());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
		//}
	}
	
	public void updateXrefActualAmount(List<OBCommonFile> objList, String facilitySystem)throws FileUploadException,SQLException{

		final List<OBCommonFile> objectList = objList;
		final int batchSize = objList.size();
		final String system = facilitySystem;
		/*for (int j = 0; j < objectList.size(); j += batchSize) {
			final List batchList = objectList.subList(j, j + batchSize > objectList.size() ? objectList.size() : j + batchSize);
			DefaultLogger.debug(this, "#####batch inserted#######:: "+j);*/
			getJdbcTemplate().batchUpdate(updateLmtUtilAmtActual,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBCommonFile ubsFile = objectList.get(i);
					 // 03/02/2016: Uma Khot:Commented below code as not to update Released Amount from upload file.
					//ps.setDouble(1,ubsFile.getLimit());
					ps.setDouble(1,ubsFile.getUtilize());
					ps.setString(2,ubsFile.getUploadStatus());
					ps.setString(3,ubsFile.getMakerId());
					ps.setTimestamp(4,new Timestamp(ubsFile.getMakerDate().getTime()));
					ps.setString(5,ubsFile.getCheckerId());
					ps.setTimestamp(6,new Timestamp(ubsFile.getCheckerDate().getTime()));
					ps.setString(7,ubsFile.getCustomer());
					ps.setString(8,ubsFile.getLine());
					ps.setString(9,ubsFile.getSerialNo());
					ps.setString(10,system);
					ps.setLong(11,ubsFile.getSysXrefId());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
		//}
	}
	
	public void updateXrefFinwareActualAmount( List<OBCommonFile> objList, String facilitySystem)throws FileUploadException,SQLException{

		final List<OBCommonFile> objectList = objList;
		final int batchSize = objList.size();
		final String system = facilitySystem;
		
		/*for (int j = 0; j < objectList.size(); j += batchSize) {
			final List batchList = objectList.subList(j, j + batchSize > objectList.size() ? objectList.size() : j + batchSize);
			DefaultLogger.debug(this, "#####batch inserted#######:: "+j);*/
			getJdbcTemplate().batchUpdate(updateLmtUtilAmtFinwareActual,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBCommonFile ubsFile = objectList.get(i);
					 // 03/02/2016: Uma Khot:Commented below code as not to update Released Amount from upload file.
					//ps.setDouble(1,ubsFile.getLimit());
					ps.setDouble(1,ubsFile.getUtilize());
					ps.setString(2,ubsFile.getUploadStatus());
					ps.setString(3,ubsFile.getMakerId());
					ps.setTimestamp(4,new Timestamp(ubsFile.getMakerDate().getTime()));
					ps.setString(5,ubsFile.getCheckerId());
					ps.setTimestamp(6,new Timestamp(ubsFile.getCheckerDate().getTime()));
					ps.setString(7,ubsFile.getCustomer());
					//ps.setString(9,ubsFile.getLine());
					//ps.setString(10,ubsFile.getSerialNo());
					//ps.setString(11,system);
					ps.setLong(8,ubsFile.getSysXrefId());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
		//}
	}
	
	public void createEntireUbsActualFile(List<OBUbsFile> objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO CMS_UBS_FILE_UPLOAD " +
		"( ID, FILE_ID,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY, LIMIT_AMOUTNT,UTILIZATION_AMOUNT,STATUS, REASON,UPLOAD_STATUS)" +
		" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ? , ?)";

		final List<OBUbsFile> objectList = objList;
		final int batchSize = objList.size();
		/*for (int j = 0; j < objectList.size(); j += batchSize) {
			final List batchList = objectList.subList(j, j + batchSize > objectList.size() ? objectList.size() : j + batchSize);
			DefaultLogger.debug(this, "#####batch inserted#######:: "+j);*/
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBUbsFile ubsFile = objectList.get(i);
					ps.setLong(1,ubsFile.getFileId());
					ps.setString(2,ubsFile.getCustomer());
					ps.setString(3,ubsFile.getLine());
					ps.setString(4,ubsFile.getSerialNo());
					ps.setString(5,ubsFile.getCurrency());
					ps.setDouble(6,ubsFile.getLimit());
					ps.setDouble(7,ubsFile.getUtilize());
					ps.setString(8,ubsFile.getStatus());
					ps.setString(9,ubsFile.getReason());
					ps.setString(10,ubsFile.getUploadStatus());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
		//}
	}
	
	public void createEntirePartyCamActualFile(List<OBPartyCamFile> objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO CMS_PARTYCAM_FILE_UPLOAD " +
		"( ID, FILE_ID,PARTY_ID,CAM_DATE,CAM_LOGIN_DATE,RAM_RATING, RAM_RATING_YEAR,CUSTOMER_RAM_ID,CAM_EXPIRY_DATE, "
		+ "CAM_EXTENSION_DATE,STATUS,REASON,UPLOAD_STATUS,RATING_TYPE,RAM_RATING_FINALIZATION_DATE,RM_EMPLOYEE_CODE,TOTAL_FUNDED_LIMIT,TOTAL_NON_FUNDED_LIMIT)" +
		" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ? , ? , ? , ? , ? , ? , ?,?,?)";

		final List<OBPartyCamFile> objectList = objList;
		final int batchSize = objList.size();
		/*for (int j = 0; j < objectList.size(); j += batchSize) {
			final List batchList = objectList.subList(j, j + batchSize > objectList.size() ? objectList.size() : j + batchSize);
			DefaultLogger.debug(this, "#####batch inserted#######:: "+j);*/
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBPartyCamFile partyCamFile = objectList.get(i);
					ps.setLong(1,partyCamFile.getFileId());
					ps.setString(2,partyCamFile.getPartyId());
					if(null==partyCamFile.getCamDate()){
						ps.setNull(3,Types.TIMESTAMP);	
					}else{
					ps.setTimestamp(3,new Timestamp(partyCamFile.getCamDate().getTime()));
					}
					if(null==partyCamFile.getCamLoginDate()){
						ps.setNull(4,Types.TIMESTAMP);	
					}else{
					ps.setTimestamp(4,new Timestamp(partyCamFile.getCamLoginDate().getTime()));
					}
					if(null==partyCamFile.getRamRating()){
						ps.setNull(5, Types.INTEGER);
					
					}else{
						ps.setInt(5,partyCamFile.getRamRating());
					}
					if(null==partyCamFile.getRamRatingYear()){
						ps.setNull(6, Types.INTEGER);
					}else{
						ps.setInt(6,partyCamFile.getRamRatingYear());	
					}
					if(null == partyCamFile.getCustomerRamId()){
						ps.setNull(7,Types.NUMERIC);
					}else{
					ps.setLong(7,partyCamFile.getCustomerRamId());
					}
					if(null==partyCamFile.getCamExpiryDate()){
						ps.setNull(8,Types.TIMESTAMP);	
					}else{
					ps.setTimestamp(8,new Timestamp(partyCamFile.getCamExpiryDate().getTime()));
					}
					if(null==partyCamFile.getCamExtensionDate()){
						ps.setNull(9,Types.TIMESTAMP);	
					}else{
					ps.setTimestamp(9,new Timestamp(partyCamFile.getCamExtensionDate().getTime()));
					}
					ps.setString(10,partyCamFile.getStatus());
					ps.setString(11,partyCamFile.getReason());
					ps.setString(12,partyCamFile.getUploadStatus());
					if(null==partyCamFile.getRamType()){
						ps.setNull(13,Types.VARCHAR);	
					}else{
					ps.setString(13,partyCamFile.getRamType());
					}
					if(null==partyCamFile.getRamFinalizationDate()){
						ps.setNull(14,Types.TIMESTAMP);	
					}else{
					ps.setTimestamp(14,new Timestamp(partyCamFile.getRamFinalizationDate().getTime()));
					}
					
					ps.setString(15,partyCamFile.getRmEmployeeCode());
					
					if(null == partyCamFile.getFundedAmount()){
						ps.setNull(16,Types.DOUBLE);
					}else{
					ps.setBigDecimal(16,new BigDecimal(partyCamFile.getFundedAmount()));
					}
					//MORATORIUM REGULATORY UPLOAD
					//DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 2224 #######:: non funded amount ##### "+Double.parseDouble(partyCamFile.getNonfundedAmount()));
					//DefaultLogger.debug(this, "##### FileUploadJdbcImpl.java ########## Line No 2224 #######:: non funded amount ##### "+new BigDecimal(partyCamFile.getNonfundedAmount()));

					if(null == partyCamFile.getNonfundedAmount()){
						ps.setNull(17,Types.DOUBLE);
					}else{
					ps.setBigDecimal(17,new BigDecimal(partyCamFile.getNonfundedAmount()));
					}
					
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
		//}
	}

	public void createEntireFinwareStageFile(List<OBFinwareFile> objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO STAGE_FINWARE_FILE_UPLOAD " +
		"( ID, FILE_ID,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY, LIMIT_AMOUTNT,UTILIZATION_AMOUNT,STATUS, REASON,UPLOAD_STATUS)" +
		" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ? , ?)";

		final List<OBFinwareFile> objectList = objList;
		final int batchSize = objList.size();
		/*for (int j = 0; j < objectList.size(); j += batchSize) {
			final List batchList = objectList.subList(j, j + batchSize > objectList.size() ? objectList.size() : j + batchSize);
			DefaultLogger.debug(this, "#####batch inserted#######:: "+j);*/
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBFinwareFile finwareFile = objectList.get(i);
					ps.setLong(1,finwareFile.getFileId());
					ps.setString(2,finwareFile.getCustomer());
					ps.setString(3,finwareFile.getLine());
					ps.setString(4,finwareFile.getSerialNo());
					ps.setString(5,finwareFile.getCurrency());
					ps.setDouble(6,finwareFile.getLimit());
					ps.setDouble(7,finwareFile.getUtilize());
					ps.setString(8,finwareFile.getStatus());
					ps.setString(9,finwareFile.getReason());
					ps.setString(10,finwareFile.getUploadStatus());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
//		}
	}

	public void createEntireFinwareActualFile(List<OBFinwareFile> objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO CMS_FINWARE_FILE_UPLOAD " +
		"( ID, FILE_ID,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY, LIMIT_AMOUTNT,UTILIZATION_AMOUNT,STATUS, REASON,UPLOAD_STATUS)" +
		" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ? , ?)";

		final List<OBFinwareFile> objectList = objList;
		final int batchSize = objList.size();
		/*for (int j = 0; j < objectList.size(); j += batchSize) {
			final List batchList = objectList.subList(j, j + batchSize > objectList.size() ? objectList.size() : j + batchSize);
			DefaultLogger.debug(this, "#####batch inserted#######:: "+j);*/
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBFinwareFile finwareFile = objectList.get(i);
					ps.setLong(1,finwareFile.getFileId());
					ps.setString(2,finwareFile.getCustomer());
					ps.setString(3,finwareFile.getLine());
					ps.setString(4,finwareFile.getSerialNo());
					ps.setString(5,finwareFile.getCurrency());
					ps.setDouble(6,finwareFile.getLimit());
					ps.setDouble(7,finwareFile.getUtilize());
					ps.setString(8,finwareFile.getStatus());
					ps.setString(9,finwareFile.getReason());
					ps.setString(10,finwareFile.getUploadStatus());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
//		}
	}

	public void createEntireHongkongStageFile(List<OBHongKongFile> objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO STAGE_HONGKONG_FILE_UPLOAD " +
		"( ID, FILE_ID,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY, LIMIT_AMOUTNT,UTILIZATION_AMOUNT,STATUS, REASON,UPLOAD_STATUS)" +
		" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ? , ?)";

		final List<OBHongKongFile> objectList = objList;
		final int batchSize = objList.size();
		/*for (int j = 0; j < objectList.size(); j += batchSize) {
			final List batchList = objectList.subList(j, j + batchSize > objectList.size() ? objectList.size() : j + batchSize);
			DefaultLogger.debug(this, "#####batch inserted#######:: "+j);*/
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBHongKongFile hongkongFile = objectList.get(i);
//					DefaultLogger.debug(this,hongkongFile.getCustomer()+":::"+hongkongFile.getLine());
					ps.setLong(1,hongkongFile.getFileId());
					ps.setString(2,hongkongFile.getCustomer());
					ps.setString(3,hongkongFile.getLine());
					ps.setString(4,hongkongFile.getSerialNo());
					ps.setString(5,hongkongFile.getCurrency());
					ps.setDouble(6,hongkongFile.getLimit());
					ps.setDouble(7,hongkongFile.getUtilize());
					ps.setString(8,hongkongFile.getStatus());
					ps.setString(9,hongkongFile.getReason());
					ps.setString(10,hongkongFile.getUploadStatus());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
		//}
	}

	public void createEntireHongkongActualFile(List<OBHongKongFile> objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO CMS_HONGKONG_FILE_UPLOAD " +
		"( ID, FILE_ID,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY, LIMIT_AMOUTNT,UTILIZATION_AMOUNT,STATUS, REASON,UPLOAD_STATUS)" +
		" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ? , ?)";

		final List<OBHongKongFile> objectList = objList;
		final int batchSize = objList.size();
		/*for (int j = 0; j < objectList.size(); j += batchSize) {
			final List batchList = objectList.subList(j, j + batchSize > objectList.size() ? objectList.size() : j + batchSize);
			DefaultLogger.debug(this, "#####batch inserted#######:: "+j);*/
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBHongKongFile hongkongFile = objectList.get(i);
					ps.setLong(1,hongkongFile.getFileId());
					ps.setString(2,hongkongFile.getCustomer());
					ps.setString(3,hongkongFile.getLine());
					ps.setString(4,hongkongFile.getSerialNo());
					ps.setString(5,hongkongFile.getCurrency());
					ps.setDouble(6,hongkongFile.getLimit());
					ps.setDouble(7,hongkongFile.getUtilize());
					ps.setString(8,hongkongFile.getStatus());
					ps.setString(9,hongkongFile.getReason());
					ps.setString(10,hongkongFile.getUploadStatus());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
		//}
	}

	public void createEntireBahrainStageFile(List<OBBahrainFile> objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO STAGE_BAHRAIN_FILE_UPLOAD " +
		"( ID, FILE_ID,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY, LIMIT_AMOUTNT,UTILIZATION_AMOUNT,STATUS, REASON,UPLOAD_STATUS)" +
		" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ? , ?)";

		final List<OBBahrainFile> objectList = objList;
		final int batchSize = objList.size();
		/*for (int j = 0; j < objectList.size(); j += batchSize) {
			final List batchList = objectList.subList(j, j + batchSize > objectList.size() ? objectList.size() : j + batchSize);
			DefaultLogger.debug(this, "#####batch inserted#######:: "+j);*/
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBBahrainFile bahrainFile = objectList.get(i);
					ps.setLong(1,bahrainFile.getFileId());
					ps.setString(2,bahrainFile.getCustomer());
					ps.setString(3,bahrainFile.getLine());
					ps.setString(4,bahrainFile.getSerialNo());
					ps.setString(5,bahrainFile.getCurrency());
					ps.setDouble(6,bahrainFile.getLimit());
					ps.setDouble(7,bahrainFile.getUtilize());
					ps.setString(8,bahrainFile.getStatus());
					ps.setString(9,bahrainFile.getReason());
					ps.setString(10,bahrainFile.getUploadStatus());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
//		}
	}

	public void createEntireBahrainActualFile(List<OBBahrainFile> objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO CMS_BAHRAIN_FILE_UPLOAD " +
		"( ID, FILE_ID,CUSTOMER_ID,LINE_NO,SERIAL_NO,CURRENCY, LIMIT_AMOUTNT,UTILIZATION_AMOUNT,STATUS, REASON,UPLOAD_STATUS)" +
		" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ? , ?)";

		final List<OBBahrainFile> objectList = objList;
		final int batchSize = objList.size();
		/*for (int j = 0; j < objectList.size(); j += batchSize) {
			final List batchList = objectList.subList(j, j + batchSize > objectList.size() ? objectList.size() : j + batchSize);
			DefaultLogger.debug(this, "#####batch inserted#######:: "+j);*/
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBBahrainFile bahrainFile = objectList.get(i);
					ps.setLong(1,bahrainFile.getFileId());
					ps.setString(2,bahrainFile.getCustomer());
					ps.setString(3,bahrainFile.getLine());
					ps.setString(4,bahrainFile.getSerialNo());
					ps.setString(5,bahrainFile.getCurrency());
					ps.setDouble(6,bahrainFile.getLimit());
					ps.setDouble(7,bahrainFile.getUtilize());
					ps.setString(8,bahrainFile.getStatus());
					ps.setString(9,bahrainFile.getReason());
					ps.setString(10,bahrainFile.getUploadStatus());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
//		}
	}

	public List selectCustDetailsActual(final String fileType)
	{
		List lstCustDetails=new ArrayList();
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String data="";
		Boolean isDBConnectionException = Boolean.FALSE;
		
		try {
			dbUtil=new DBUtil();
			if(fileType.equals("UbsFile")){
				dbUtil.setSQL(selectCustDetailsUbsActual);
				//dbUtil.setSQL(selectCustDetailsUbsBharat);
			}else if(fileType.equals("HongkongFile")){
				dbUtil.setSQL(selectCustDetailsHongkongActual);	
				//dbUtil.setSQL(selectCustDetailsUbsBharat);
			}else if(fileType.equals("FinwareFile")){
				dbUtil.setSQL(selectCustDetailsFinwareActual);	
				//dbUtil.setSQL(selectCustDetailsUbsBharat);
			}else if(fileType.equals("BahrainFile")){
				dbUtil.setSQL(selectCustDetailsBahrainActual);
				//dbUtil.setSQL(selectCustDetailsUbsBharat);
			}
			rs = dbUtil.executeQuery();		
			while(rs.next())
			{
				if(fileType.equals("FinwareFile")){
				data=rs.getString("FACILITY_SYSTEM_ID")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("LINE_NO")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("SERIAL_NO");
				}
				//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				else{
				data=rs.getString("FACILITY_SYSTEM_ID")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("LINE_NO")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("SERIAL_NO")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("lmt_crrncy_iso_code").trim();
				}
				//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				lstCustDetails.add(data);
			}
		} catch (DBConnectionException e) {
			isDBConnectionException = Boolean.TRUE;
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		
		//Retry using JDBC for Batch (Websphere env)
		if (isDBConnectionException) {
			String query = null;

			try {
				if (fileType.equals("UbsFile")) {
					query = selectCustDetailsUbsActual;
				} else if (fileType.equals("HongkongFile")) {
					query = selectCustDetailsHongkongActual;
				} else if (fileType.equals("FinwareFile")) {
					query = selectCustDetailsFinwareActual;
				} else if (fileType.equals("BahrainFile")) {
					query = selectCustDetailsBahrainActual;
				}

				lstCustDetails = (List) getJdbcTemplate().query(query, new ArrayList<Object>().toArray(),
						new ResultSetExtractor() {
							public List extractData(ResultSet rs) throws SQLException, DataAccessException {

								List custDetails = new ArrayList();
								String result = null;

								while (rs.next()) {

									if(fileType.equals("FinwareFile")){
										result = rs.getString("FACILITY_SYSTEM_ID")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("LINE_NO")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("SERIAL_NO");
									}
									//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
									else{
										result = rs.getString("FACILITY_SYSTEM_ID")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("LINE_NO")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("SERIAL_NO")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("lmt_crrncy_iso_code").trim();
									}
									//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
									custDetails.add(result);
								
								}
								return custDetails;
							}
						});
			} catch (Exception e) {
				DefaultLogger.info(this, "Exception caught in selectCustDetails ::" + e.getMessage());
				e.printStackTrace();
			}

		}
		
		return lstCustDetails;
	}
	public List selectCustDetails(final String fileType)
	{
		List lstCustDetails=new ArrayList();
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String data="";
		Boolean isDBConnectionException = Boolean.FALSE;
		
		try {
			dbUtil=new DBUtil();
			if(fileType.equals("UbsFile")){
				dbUtil.setSQL(selectCustDetailsUbs);
				//dbUtil.setSQL(selectCustDetailsUbsBharat);
			}else if(fileType.equals("HongkongFile")){
				dbUtil.setSQL(selectCustDetailsHongkong);
				//dbUtil.setSQL(selectCustDetailsUbsBharat);;	
			}else if(fileType.equals("FinwareFile")){
				dbUtil.setSQL(selectCustDetailsFinware);	
				//dbUtil.setSQL(selectCustDetailsUbsBharat);
			}else if(fileType.equals("BahrainFile")){
				dbUtil.setSQL(selectCustDetailsBahrain);	
				//dbUtil.setSQL(selectCustDetailsUbsBharat);
			}
			rs = dbUtil.executeQuery();			
			while(rs.next())
			{
				if(fileType.equals("FinwareFile")){
				data=rs.getString("FACILITY_SYSTEM_ID")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("LINE_NO")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("SERIAL_NO");
				}
				
				//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				else{
				data=rs.getString("FACILITY_SYSTEM_ID")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("LINE_NO")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("SERIAL_NO")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("lmt_crrncy_iso_code").trim();
				}
				//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				lstCustDetails.add(data);
			}
		} catch (DBConnectionException e) {
			isDBConnectionException = Boolean.TRUE;
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		
		//Retry using JDBC for Batch (Websphere env)
		if (isDBConnectionException) {
			String query = null;
			
			try {
				if(fileType.equals("UbsFile")){
					query = selectCustDetailsUbs;
				}else if(fileType.equals("HongkongFile")){
					query = selectCustDetailsHongkong;
				}else if(fileType.equals("FinwareFile")){
					query = selectCustDetailsFinware;	
				}else if(fileType.equals("BahrainFile")){
					query = selectCustDetailsBahrain;	
				}
				

				lstCustDetails =  (List) getJdbcTemplate().query(query, new ArrayList<Object>().toArray(), new ResultSetExtractor() {
					public List extractData(ResultSet rs) throws SQLException, DataAccessException {
						
						List custDetails = new ArrayList();
						String result = null;
						
						while(rs.next()) {
							if(fileType.equals("FinwareFile")){
								result=rs.getString("FACILITY_SYSTEM_ID")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("LINE_NO")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("SERIAL_NO");
								}
								//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
								else{
									result=rs.getString("FACILITY_SYSTEM_ID")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("LINE_NO")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("SERIAL_NO")+ICMSConstant.FILEUPLOAD_SEPERATOR+rs.getString("lmt_crrncy_iso_code").trim();
								}
								//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
							custDetails.add(result);
						}
						return custDetails;
					}
				});
			}
			catch (Exception e) {
				DefaultLogger.info(this, "Exception caught in selectCustDetails ::"+e.getMessage());
				e.printStackTrace();
			}
			
		}
		return lstCustDetails;
	}

	public static void finalize(DBUtil dbUtil, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public String getPartyStatusForUBS(String Cust_Id,String Line_No,String Sr_No){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String status="";
		try {
			dbUtil=new DBUtil();
			//Earlier query to fetch data from different tables with joins,was taking time and impacting system performance.
			// To overcome of this issue, Materialized View has been created at DB and data has been fetched from that view. 
			// TO DO :::: This function has been implemented for UBS-LIMITS but needs to be implemented for Finware upload files.
			
			dbUtil.setSQL("SELECT STATUS FROM UBS_UPLOAD_CHK_PARTY_STATUS_MV WHERE FACILITY_SYSTEM_ID='"+Cust_Id+"' AND LINE_NO='"+Line_No+"' AND SERIAL_NO='"+Sr_No+"'");
			rs = dbUtil.executeQuery();		
			while(rs.next())
			{
				status=rs.getString("STATUS");
			}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return status;
	}
	public String getPartyStatusForHongKong(String Cust_Id,String Line_No,String Sr_No){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String status="";
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT STATUS FROM HNKG_UPLD_CHK_PARTY_STATUS_MV WHERE FACILITY_SYSTEM_ID='"+Cust_Id+"' AND LINE_NO='"+Line_No+"' AND SERIAL_NO='"+Sr_No+"'");
			rs = dbUtil.executeQuery();		
			while(rs.next())
			{
				status=rs.getString("STATUS");
			}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return status;
	}
	public String getPartyStatusForBahrain(String Cust_Id,String Line_No,String Sr_No){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String status="";
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT STATUS FROM BAHRIN_UPLD_CHK_PARTY_MV WHERE FACILITY_SYSTEM_ID='"+Cust_Id+"' AND LINE_NO='"+Line_No+"' AND SERIAL_NO='"+Sr_No+"'");
			rs = dbUtil.executeQuery();		
			while(rs.next())
			{
				status=rs.getString("STATUS");
			}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return status;
	}
	public String getPartyStatusForFinware(String Cust_Id){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String status="";
		try {
			dbUtil=new DBUtil();
			//To Do :::: For performance improvement: Need to create a materialized view and change below query accordingly
			dbUtil.setSQL("SELECT STATUS FROM sci_le_sub_profile WHERE cms_le_sub_profile_id IN (SELECT cms_customer_id FROM sci_lsp_lmt_profile WHERE cms_lsp_lmt_profile_id IN (SELECT cms_limit_profile_id FROM sci_lsp_appr_lmts WHERE cms_lsp_appr_lmts_id IN(SELECT cms_lsp_appr_lmts_id FROM sci_lsp_lmts_xref_map WHERE cms_lsp_sys_xref_id IN(SELECT cms_lsp_sys_xref_id FROM sci_lsp_sys_xref WHERE FACILITY_SYSTEM_ID='"+Cust_Id+"' AND facility_system in ('FW-LIMITS','FCNCB')))))");
			rs = dbUtil.executeQuery();		
			while(rs.next())
			{
				status=rs.getString("STATUS");
			}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return status;
	}

	
	public IEODStatus executeFileUploadCleanup(IEODStatus eodStatus) {
		DefaultLogger.debug(this, " FileUploadJdbcImpl.executeFileUploadCleanup() starts ==== ");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				getJdbcTemplate().execute("{call " + getUploadFileCleanup() + "()}",  new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
						cs.executeUpdate();
						return null;
					}
				});
				eodStatus.setStatus(EODConstants.STATUS_PASS);
			}
			catch (Exception ex) {
				eodStatus.setStatus(EODConstants.STATUS_FAIL);
				DefaultLogger.error(this,"FileUploadJdbcImpl Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : executeFileUploadCleanup())" );
				ex.printStackTrace();
			}
		}else if(EODConstants.STATUS_PASS.equalsIgnoreCase(eodStatus.getStatus())
				|| EODConstants.STATUS_DONE.equalsIgnoreCase(eodStatus.getStatus())) {
			eodStatus.setStatus(EODConstants.STATUS_DONE);
		}
		DefaultLogger.debug(this, " FileUploadJdbcImpl.executeFileUploadCleanup() ends ==== ");
		return eodStatus;
	}
	
	public void setSpUploadTransaction(String spUploadTransaction) {
		this.spUploadTransaction = spUploadTransaction;
	}
	
	public String getUploadFileCleanup() {
		return uploadFileCleanup;
	}

	public void setUploadFileCleanup(String uploadFileCleanup) {
		this.uploadFileCleanup = uploadFileCleanup;
	}
	
	
	public String getSpUpdateReleasedAmount() {
		return spUpdateReleasedAmount;
	}

	public void setSpUpdateReleasedAmount(String spUpdateReleasedAmount) {
		this.spUpdateReleasedAmount = spUpdateReleasedAmount;
	}

	
	// Added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION | Start
	
	public String spUploadTransaction(final String systemName){
		Object OutputObj = null;
        try {
        	String output="";
             OutputObj = getJdbcTemplate().execute("{call " + getSpUploadTransaction() + "(?,?)}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.setString(1, systemName);
                	cs.registerOutParameter(2, java.sql.Types.VARCHAR);
                	cs.execute();
                    return cs.getString(2);
                }
            });
            System.out.println("Output Param: "+OutputObj.toString());
            
        }
        catch (Exception ex) {
        	ex.printStackTrace();
           
        }
        return(OutputObj.toString());
    }
	
	public ConcurrentMap<String,String> cacheDataFromMaterializedView(final String materializedViewName)
	{
		 ConcurrentMap<String, String> dataFromCacheView = new ConcurrentHashMap();
			DBUtil dbUtil = null;
			ResultSet rs=null;
			StringBuilder key = null;
			Boolean isDBConnectionException = Boolean.FALSE;
			
			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL("SELECT * FROM "+materializedViewName);
				rs = dbUtil.executeQuery();	
				if(rs!=null)
				{
					while(rs.next())
					{
						key=new StringBuilder();
						if(!"FINWARE_UPLOAD_STATUS_MV".equalsIgnoreCase(materializedViewName)){
							key.append(rs.getString("FACILITY_SYSTEM_ID"));
							key.append("~");
							key.append(rs.getString("LINE_NO"));
							key.append("~");
							key.append(rs.getString("SERIAL_NO"));
						}
						
						if("FINWARE_UPLOAD_STATUS_MV".equalsIgnoreCase(materializedViewName))
							key.append(rs.getString("FACILITY_SYSTEM_ID"));
						
						key.trimToSize();
						dataFromCacheView.putIfAbsent(key.toString(), rs.getString("STATUS"));
					}
				}
			} catch (DBConnectionException e) {
				isDBConnectionException = Boolean.TRUE;
				e.printStackTrace();
			} catch (NoSQLStatementException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finalize(dbUtil,rs);
		
			//Retry using JDBC for Batch (Websphere env)
			if (isDBConnectionException) {
				String query =  "SELECT * FROM "+materializedViewName;
				
				try {
					dataFromCacheView =  (ConcurrentMap) getJdbcTemplate().query(query, new ArrayList<Object>().toArray(), new ResultSetExtractor() {
						public ConcurrentMap extractData(ResultSet rs) throws SQLException, DataAccessException {
							
							ConcurrentMap<String, String> dataFromCacheViewTest = new ConcurrentHashMap();
							
							while(rs.next()) {
								StringBuilder dataFromCacheKey=new StringBuilder();
								if(!"FINWARE_UPLOAD_STATUS_MV".equalsIgnoreCase(materializedViewName)){
									dataFromCacheKey.append(rs.getString("FACILITY_SYSTEM_ID"));
									dataFromCacheKey.append("~");
									dataFromCacheKey.append(rs.getString("LINE_NO"));
									dataFromCacheKey.append("~");
									dataFromCacheKey.append(rs.getString("SERIAL_NO"));
								}
								
								if("FINWARE_UPLOAD_STATUS_MV".equalsIgnoreCase(materializedViewName))
									dataFromCacheKey.append(rs.getString("FACILITY_SYSTEM_ID"));
								
								dataFromCacheKey.trimToSize();
								dataFromCacheViewTest.putIfAbsent(dataFromCacheKey.toString(), rs.getString("STATUS"));
								
							}
							return dataFromCacheViewTest;
						}
					});
				}catch (Exception e) {
					DefaultLogger.info(this, "Exception caught in cacheDataFromMaterializedView ::"+e.getMessage());
					e.printStackTrace();
				}
			}
			
		 return dataFromCacheView;
	}
	
	public String getPartyStatusForUBSUpload(String Cust_Id,String Line_No,String Sr_No,ConcurrentMap<String, String> dataFromCacheView , String facilitySystem){
		
		String status= "";
		if(null != dataFromCacheView && null!=dataFromCacheView.get(Cust_Id+"~"+Line_No+"~"+Sr_No) && (!"FINWARE".equalsIgnoreCase(facilitySystem)))
			status = dataFromCacheView.get(Cust_Id+"~"+Line_No+"~"+Sr_No);
		else if(null != dataFromCacheView && null!=dataFromCacheView.get(Cust_Id) && ("FINWARE".equalsIgnoreCase(facilitySystem)))
			status = dataFromCacheView.get(Cust_Id);
		
		return status;
	}

	public String getSpUploadTransaction() {
		return spUploadTransaction;
	}
	
	// Added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION | End
	
	// Added by : Dayananda for FC-UPLOAD changes || CSV format || Starts
	
	public ConcurrentMap<String,String> cacheDataFromUpdLineFacilityMV(String materializedViewName,final String facilitySystem)
	{
	 	ConcurrentMap<String, String> dataFromCacheView = new ConcurrentHashMap();
		DBUtil dbUtil = null;
		ResultSet rs=null;
		StringBuilder key = null;
		String temValue =null;
		Boolean isDBConnectionException = Boolean.FALSE;
		
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT * FROM  "+materializedViewName);
			rs = dbUtil.executeQuery();	
			if(rs!=null)
			{
				while(rs.next())
				{
					key=new StringBuilder();
										
						if(!facilitySystem.equals("FINWARE"))
						{
						    key.append(rs.getString("LINE_NO"));
							key.append("~");
							key.append(rs.getString("SERIAL_NO"));
							key.append("~");
							key.append(rs.getString("FACILITY_SYSTEM"));
							key.append("~");
							key.append(rs.getString("FACILITY_SYSTEM_ID"));
						}
						
						if(facilitySystem.equals("FINWARE"))
						{
							temValue = rs.getString("FACILITY_SYSTEM");
							/*if(temValue==null)
							{
								temValue= rs.getString("FCNCB");
							}*/
							key.append(temValue);
							key.append("~");
							key.append(rs.getString("FACILITY_SYSTEM_ID"));
						}
					key.trimToSize();
					dataFromCacheView.putIfAbsent(key.toString(),rs.getString("cms_lsp_sys_xref_id")+"~"+rs.getString("lmt_crrncy_iso_code")+"~"+rs.getString("cms_lsp_appr_lmts_id"));
				}
			}
		} catch (DBConnectionException e) {
			isDBConnectionException = Boolean.TRUE;
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		
		//Retry using JDBC for Batch (Websphere env)
		if (isDBConnectionException) {
			
			String query =  "SELECT * FROM "+materializedViewName;
			
			try {
				dataFromCacheView =  (ConcurrentMap) getJdbcTemplate().query(query, new ArrayList<Object>().toArray(), new ResultSetExtractor() {
					public ConcurrentMap extractData(ResultSet rs) throws SQLException, DataAccessException {
						
						ConcurrentMap<String, String> dataFromCacheViewTest = new ConcurrentHashMap();
						String temValueFac =null;
						
						while(rs.next()) {
							StringBuilder dataFromcacheKey = new StringBuilder();
							
							if(!facilitySystem.equals("FINWARE"))
							{
							    dataFromcacheKey.append(rs.getString("LINE_NO"));
								dataFromcacheKey.append("~");
								dataFromcacheKey.append(rs.getString("SERIAL_NO"));
								dataFromcacheKey.append("~");
								dataFromcacheKey.append(rs.getString("FACILITY_SYSTEM"));
								dataFromcacheKey.append("~");
								dataFromcacheKey.append(rs.getString("FACILITY_SYSTEM_ID"));
							}
							
							if(facilitySystem.equals("FINWARE"))
							{
								temValueFac = rs.getString("FACILITY_SYSTEM");
								dataFromcacheKey.append(temValueFac);
								dataFromcacheKey.append("~");
								dataFromcacheKey.append(rs.getString("FACILITY_SYSTEM_ID"));
							}
						dataFromcacheKey.trimToSize();
						dataFromCacheViewTest.putIfAbsent(dataFromcacheKey.toString(),rs.getString("cms_lsp_sys_xref_id")+"~"+rs.getString("lmt_crrncy_iso_code")+"~"+rs.getString("cms_lsp_appr_lmts_id"));
							
						}
						return dataFromCacheViewTest;
					}
				});
			}
			catch (Exception e) {
				DefaultLogger.info(this, "Exception caught in cacheDataFromMaterializedView ::"+e.getMessage());
				e.printStackTrace();
			}
		}
	
	 return dataFromCacheView;
}
	
	public String getSysXrefIdAndlmtCrrncyIsoCd(String FACILITY_SYSTEM_ID,String LINE_NO,String SERIAL_NO,String FACILITY_SYSTEM,ConcurrentMap<String, String> dataFromUpdLineFacilityMV){
		
		String concanatedResult= null;
		
		if(null!=dataFromUpdLineFacilityMV.get(LINE_NO+"~"+SERIAL_NO+"~"+FACILITY_SYSTEM+"~"+FACILITY_SYSTEM_ID) && (!"FINWARE".equalsIgnoreCase(FACILITY_SYSTEM))){
			concanatedResult = dataFromUpdLineFacilityMV.get(LINE_NO+"~"+SERIAL_NO+"~"+FACILITY_SYSTEM+"~"+FACILITY_SYSTEM_ID);
			DefaultLogger.info(this,"Key Is:---------------- "+(LINE_NO+"~"+SERIAL_NO+"~"+FACILITY_SYSTEM+"~"+FACILITY_SYSTEM_ID));
		}	
		else if("FINWARE".equalsIgnoreCase(FACILITY_SYSTEM) || "FCNCB".equalsIgnoreCase(FACILITY_SYSTEM))
		{
			String temValue = dataFromUpdLineFacilityMV.get("FW-LIMITS"+"~"+FACILITY_SYSTEM_ID);
			DefaultLogger.info(this,"Key Is:---------------- "+("FW-LIMITS"+"~"+FACILITY_SYSTEM_ID));
			if(temValue==null)
			{
				temValue= dataFromUpdLineFacilityMV.get("FCNCB"+"~"+FACILITY_SYSTEM_ID)==null?dataFromUpdLineFacilityMV.get("FWL"+"~"+FACILITY_SYSTEM_ID):dataFromUpdLineFacilityMV.get("FCNCB"+"~"+FACILITY_SYSTEM_ID);
				DefaultLogger.info(this,"Key Is:---------------- "+("FCNCB"+"~"+FACILITY_SYSTEM_ID));
			}
			concanatedResult = temValue;
			
		}
		
		return concanatedResult;
	}
	
	
	public ConcurrentMap<String,String> cacheDataFromUpdLineActual(final String facilitySystem)
	{
		 	ConcurrentMap<String, String> dataFromCacheView = new ConcurrentHashMap();
			DBUtil dbUtil = null;
			ResultSet rs=null;
			StringBuilder key = null;
			String temValue =null;
			StringBuilder queryBuilder= new StringBuilder();
			Boolean isDBConnectionException = Boolean.FALSE;
			
			queryBuilder.append("select xref.cms_lsp_sys_xref_id as cms_lsp_sys_xref_id,");
			queryBuilder.append("xref.FACILITY_SYSTEM_ID as FACILITY_SYSTEM_ID,");
			queryBuilder.append("lmt.lmt_crrncy_iso_code ,");
			queryBuilder.append("lmt.cms_lsp_appr_lmts_id,");
			queryBuilder.append("xref.LINE_NO,");
			queryBuilder.append(" xref.SERIAL_NO,");
			queryBuilder.append("xref.FACILITY_SYSTEM ");
			queryBuilder.append("from ");
			queryBuilder.append("SCI_LSP_SYS_XREF xref,");
			queryBuilder.append("sci_lsp_lmts_xref_map xref_map,");
			queryBuilder.append("sci_lsp_appr_lmts lmt, ");
			queryBuilder.append(" transaction t  ");
			queryBuilder.append("WHERE  ");
			queryBuilder.append("t.transaction_type = 'LIMIT' ");
			queryBuilder.append(" and xref.cms_lsp_sys_xref_id = xref_map.cms_lsp_sys_xref_id ");
			queryBuilder.append(" and lmt.cms_lsp_appr_lmts_id = xref_map.cms_lsp_appr_lmts_id"); 
			queryBuilder.append(" and lmt.cms_lsp_appr_lmts_id = t.reference_id ");
			queryBuilder.append(" and lmt.cms_limit_status = 'ACTIVE'");
			queryBuilder.append(" and t.status = 'ACTIVE'");
			queryBuilder.trimToSize();
			
			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryBuilder.toString());
				rs = dbUtil.executeQuery();	
				if(rs!=null)
				{
					while(rs.next())
					{
						key=new StringBuilder();
											
							if(!facilitySystem.equals("FINWARE"))
							{
							    key.append(rs.getString("LINE_NO"));
								key.append("~");
								key.append(rs.getString("SERIAL_NO"));
								key.append("~");
								key.append(rs.getString("FACILITY_SYSTEM"));
								key.append("~");
								key.append(rs.getString("FACILITY_SYSTEM_ID"));
							}
							
							if(facilitySystem.equals("FINWARE"))
							{
								temValue = rs.getString("FACILITY_SYSTEM");
								/*if(temValue==null)
								{
									temValue= rs.getString("FCNCB");
								}*/
								key.append(temValue);
								key.append("~");
								key.append(rs.getString("FACILITY_SYSTEM_ID"));
							}
						key.trimToSize();
						dataFromCacheView.putIfAbsent(key.toString(),rs.getString("cms_lsp_sys_xref_id")+"~"+rs.getString("lmt_crrncy_iso_code")+"~"+rs.getString("cms_lsp_appr_lmts_id"));
					}
				}
			} catch (DBConnectionException e) {
				isDBConnectionException = Boolean.TRUE;
				e.printStackTrace();
			} catch (NoSQLStatementException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finalize(dbUtil,rs);
			
			//Retry using JDBC for Batch (Websphere env)
			if (isDBConnectionException) {
				try {
					dataFromCacheView =  (ConcurrentMap) getJdbcTemplate().query(queryBuilder.toString(), new ArrayList<Object>().toArray(), new ResultSetExtractor() {
						public ConcurrentMap extractData(ResultSet rs) throws SQLException, DataAccessException {
							
							ConcurrentMap<String, String> dataFromCacheViewTest = new ConcurrentHashMap();
							String temValue =null;
							
							while(rs.next()) {
								StringBuilder key=new StringBuilder();
													
									if(!facilitySystem.equals("FINWARE"))
									{
									    key.append(rs.getString("LINE_NO"));
										key.append("~");
										key.append(rs.getString("SERIAL_NO"));
										key.append("~");
										key.append(rs.getString("FACILITY_SYSTEM"));
										key.append("~");
										key.append(rs.getString("FACILITY_SYSTEM_ID"));
									}
									
									if(facilitySystem.equals("FINWARE"))
									{
										temValue = rs.getString("FACILITY_SYSTEM");
										key.append(temValue);
										key.append("~");
										key.append(rs.getString("FACILITY_SYSTEM_ID"));
									}
								key.trimToSize();
								dataFromCacheViewTest.putIfAbsent(key.toString(),rs.getString("cms_lsp_sys_xref_id")+"~"+rs.getString("lmt_crrncy_iso_code")+"~"+rs.getString("cms_lsp_appr_lmts_id"));
							
							}
							return dataFromCacheViewTest;
						}
					});
				}catch (Exception e) {
					DefaultLogger.info(this, "Exception caught in cacheDataFromUpdLineActual ::"+e.getMessage());
					e.printStackTrace();
				}
			}
		
		 return dataFromCacheView;
	}
	
	// Added by : Dayananda for FC-UPLOAD changes || CSV format || Ends
	
	// Added By Dayananda Laishram on 31/03/2015 || SP_FACILITY_RELEASE | Start
	public void spUpdateReleasedAmount(final String facilitySystem,final String updateType){
		Object OutputObj = null;
        try {
        	String output="";
             OutputObj = getJdbcTemplate().execute("{call " + getSpUpdateReleasedAmount() + "(?,?)}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.setString(1, facilitySystem);
                	cs.setString(2, updateType);
                	cs.execute();
                    return null;
                }
            });
            
        }
        catch (Exception ex) {
        	ex.printStackTrace();
           
        }
        
    }
	// Added By Dayananda Laishram on 31/03/2015 || SP_FACILITY_RELEASE | Ends

	//Added by Uma for FD Upload
	public HashMap insertFdfile( ArrayList result,UploadFdFileCmd cmd,String fileName, Set<String> dataFromFdCacheView){
		List totalUploadedList= new ArrayList();
		List totalUploadedListWithCmsrefid= new ArrayList();
		List errorList= new ArrayList();
		Timestamp st = null;
		String errMsg="";
		String tempData="";
		String strArrTemp[]=new String[4];
		List<OBFdFile> fdDetailsFromClimaStage=new ArrayList<OBFdFile>();
		List<String> cmsRefIdList=new ArrayList<String>();
		HashMap retMap = new HashMap();
		List<String> fileDepositNumber=new ArrayList<String>();
		List<String> recordsInClimsNotInFile=new ArrayList<String>();
		List<OBFdFile> totalUploadedListWithDepnoCmsrefid= new ArrayList<OBFdFile>();
		//List<String> depositReceiptNoForUploadStatusN= new ArrayList<String>();
		//List<String> cmsRefIdParamsUploadStatusN=new ArrayList<String>();
		
		DefaultLogger.debug(this, "#####################In FileUploadJdbcImpl insertFdfile #########:: ");
		try {
			if(result!=null&& result.size() != 0)
			{
				for (int index = 0; index < result.size(); index++) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
					
					HashMap eachDataMap = (HashMap) result.get(index);
					OBFdFile obj=new OBFdFile();
					obj.setDepositNumber(((String) eachDataMap.get("DEPOSIT_NUMBER")).trim());
					
					fileDepositNumber.add(((String) eachDataMap.get("DEPOSIT_NUMBER")).trim()); //added to mainntain Deposit_number of CSV file so that we can use it to get the details present in CLIMS but not in FD File
				
					if(null!=eachDataMap.get("DATE_OF_DEPOSIT")&& !eachDataMap.get("DATE_OF_DEPOSIT").toString().isEmpty())
					{
						//obj.setDateOfDeposit(eachDataMap.get("DATE_OF_DEPOSIT").toString());
						
						//Uma Khot:Fd Start date issue, 2012 is shown as 2020
						obj.setDateOfDeposit(simpleDateFormat.parse(eachDataMap.get("DATE_OF_DEPOSIT").toString()));
					}
					if(null!=eachDataMap.get("DATE_OF_MATURITY")&& !eachDataMap.get("DATE_OF_MATURITY").toString().isEmpty()) 
					{
						//obj.setDateOfMaturity(eachDataMap.get("DATE_OF_MATURITY").toString());
						
						//Uma Khot:Fd Start date issue, 2012 is shown as 2020
						obj.setDateOfMaturity(simpleDateFormat.parse(eachDataMap.get("DATE_OF_MATURITY").toString()));
					}
					
					if(null!=eachDataMap.get("INTEREST_RATE")&& !eachDataMap.get("INTEREST_RATE").toString().isEmpty())
					{
						obj.setInterestRate(new Double(Double.parseDouble(eachDataMap.get("INTEREST_RATE").toString())));
					}
					obj.setUploadStatus("Y");
					OBCommonFdFile commonObj = obj;
					cmsRefIdList = getCmsRefIdListForFdUpload(obj.getDepositNumber(),dataFromFdCacheView);
					
					if(cmsRefIdList ==null || cmsRefIdList.size()==0){
						errMsg="FD Details Present in File but not in CLIMS";
						commonObj.setReason(errMsg);
						commonObj.setStatus("FAIL");
						obj.setReason(errMsg);
						obj.setStatus("FAIL");
						//obj.setUploadStatus("N");
					}else { // If MatchFound for FD receipt No and Multiple CMS REF ID then save that in Obj to Use for Future
						
						
						for(String cmsRefId:cmsRefIdList){
							OBFdFile newObj=new OBFdFile();
							newObj.setDepositNumber(obj.getDepositNumber());
							newObj.setDateOfDeposit(obj.getDateOfDeposit());
							newObj.setDateOfMaturity(obj.getDateOfMaturity());
							newObj.setInterestRate(obj.getInterestRate());
							//obj.setCmsRefId(cmsRefId);
							//totalUploadedListWithCmsrefid.add(obj);
							newObj.setCmsRefId(cmsRefId);
							totalUploadedListWithCmsrefid.add(newObj);
						}
						errMsg="FD Details Present in File and CLIMS";
						commonObj.setReason(errMsg);
						commonObj.setStatus("PASS");
					//	obj.setUploadStatus("Y");
					}
					totalUploadedList.add(commonObj);
				
				}
			}
			/*
			for(String str:dataFromFdCacheView){
				String[] split = str.split("~");
				if(split.length==2){
					OBFdFile obFdFile=new OBFdFile();
				// depositReceiptNoForUploadStatusN.add(split[0]);
				// cmsRefIdParamsUploadStatusN.add(split[1]);
					obFdFile.setDepositNumber(split[0]);
					obFdFile.setCmsRefId(split[1]);
					totalUploadedListWithDepnoCmsrefid.add(obFdFile);
				}
			} 
			OBFdFile obFdFile1=new OBFdFile();
			obFdFile1.setDepositNumber("22288");
			obFdFile1.setCmsRefId("20120525000000642");
			totalUploadedListWithDepnoCmsrefid.add(obFdFile1); 
			
			//Update UploadStatus Column as 'N' for all the details in Materialzed view 
			
			int batchSize = 200;
			if(totalUploadedListWithDepnoCmsrefid!=null && totalUploadedListWithDepnoCmsrefid.size()!=0){
				for (int j = 0; j < totalUploadedListWithDepnoCmsrefid.size(); j += batchSize) {
					List<OBFdFile> batchList = totalUploadedListWithDepnoCmsrefid.subList(j, j + batchSize > totalUploadedListWithDepnoCmsrefid.size() ? totalUploadedListWithDepnoCmsrefid.size() : j + batchSize);
					updateFdStageUploadStatus(batchList); // Update the Clims Stage Table for Column UploadStatus as 'N'
				}
					
			} */
			
			int batchSize=200;
			for (int j = 0; j < totalUploadedListWithCmsrefid.size(); j += batchSize) {
				List<OBFdFile> batchList = totalUploadedListWithCmsrefid.subList(j, j + batchSize > totalUploadedListWithCmsrefid.size() ? totalUploadedListWithCmsrefid.size() : j + batchSize);
				updateFdDetailStage(batchList); // Update the Clims Stage Table
			}
			DefaultLogger.debug(this, "############### Clims Stage Table updated For FD Upload ##############:: ");
			st = new Timestamp(System.currentTimeMillis());
			
			List<String> depositReceiptNoParams= new ArrayList<String>();
			List<String> cmsRefIdParams=new ArrayList<String>();
			//Extarct Those CMS ref Id where there is no match in FD File
			if(null!=dataFromFdCacheView && dataFromFdCacheView.size()>0){
			for(String str:dataFromFdCacheView){
				String substring = str.substring(0, str.lastIndexOf("~"));
				if(!fileDepositNumber.contains(substring)){
					depositReceiptNoParams.add(substring);
					cmsRefIdParams.add(str.substring(str.lastIndexOf("~")+1));
				}
			}
			}
			
			for (int j = 0; j < depositReceiptNoParams.size(); j += batchSize) {
				List<String> depositReceiptNoParamsList = depositReceiptNoParams.subList(j, j + batchSize > depositReceiptNoParams.size() ? depositReceiptNoParams.size() : j + batchSize);
				List<String> cmsRefIdParamsList = cmsRefIdParams.subList(j, j + batchSize > cmsRefIdParams.size() ? cmsRefIdParams.size() : j + batchSize);
				fdDetailsFromClimaStage.addAll(getFdDetailsFromClimsStage(depositReceiptNoParamsList,cmsRefIdParamsList));	
			}
			
		
			for(int i=0;i<fdDetailsFromClimaStage.size();i++)
			{				
				OBFdFile obFdFile=fdDetailsFromClimaStage.get(i);
			    errMsg="Available in CLIMS but not in Fd File.";
			    obFdFile.setStatus("FAIL");
				obFdFile.setReason(errMsg);
				obFdFile.setUploadStatus("N");
				errorList.add(obFdFile);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, e.getMessage());
			throw new IncompleteBatchJobException(
			"Unable to update retrived record from CSV file");
		}
	
		retMap.put("totalUploadedList", totalUploadedList);
		retMap.put("errorList", errorList);
		return retMap;
		}

		
	private void updateFdStageUploadStatus(List<OBFdFile> objList)throws FileUploadException,SQLException{
		
		final List<OBFdFile> objectList = objList;
		getJdbcTemplate().batchUpdate(updateFdDetailsStageUploadStatusN, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				OBFdFile obFdFile = objectList.get(i);
				ps.setString(1,obFdFile.getDepositNumber());
				ps.setString(2,obFdFile.getCmsRefId());
			}
			
			
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return objectList.size();
			}
		});
	}
	

	private List<OBFdFile> getFdDetailsFromClimsStage(List<String> depositReceiptNoParams,List<String> cmsRefIdParams) {
		// TODO Auto-generated method stub
		
		List<OBFdFile> fdDetailsFromClimaStage = new ArrayList<OBFdFile>();
	
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
		String sql="select DEPOSIT_REFERENCE_NUMBER, ISSUE_DATE, DEPOSIT_MATURITY_DATE ,DEPOSIT_INTEREST_RATE  from CMS_STAGE_CASH_DEPOSIT where DEPOSIT_REFERENCE_NUMBER in (:depositReceiptNoParams) and CMS_REF_ID in (:cmsRefIdParams)";

		Map<String, List<String>> parameter=new HashMap<String, List<String>>();
		parameter.put("depositReceiptNoParams", depositReceiptNoParams);
		parameter.put("cmsRefIdParams", cmsRefIdParams); 
	
		/*
		fdDetailsFromClimaStage= getJdbcTemplate().query(sql, new FdFileRowMapper(){
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBFdFile obFdFile =new OBFdFile();
				String issueDate="";
				String depositMaturityDate="";
				obFdFile.setDepositNumber(rs.getString("DEPOSIT_RECEIPT_NUMBER"));
				//obFdFile.setDepositNumber(rs.getString("DEPOSIT_AMOUNT"));
				Timestamp timestamp = rs.getTimestamp("ISSUE_DATE");
				if(timestamp!=null && timestamp.toString()!=""){
				String str=timestamp.toString();
				try {
					issueDate = new SimpleDateFormat("dd-MMM-yy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(str));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				obFdFile.setDateOfDeposit(issueDate);
				
				Timestamp timestamp2 = rs.getTimestamp("DEPOSIT_MATURITY_DATE");
				if(timestamp2!=null && timestamp2.toString()!=""){
				String str=timestamp2.toString();
				try {
					 depositMaturityDate = new SimpleDateFormat("dd-MMM-yy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(str));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				obFdFile.setDateOfMaturity(depositMaturityDate);
				obFdFile.setInterestRate(Double.parseDouble(rs.getString("DEPOSIT_INTEREST_RATE")));
				//obFdFile.setDepositNumber(rs.getString("CMS_REF_ID"));
				return obFdFile;
				}
				}); */

		fdDetailsFromClimaStage= namedParameterJdbcTemplate.query(sql, parameter, new FdFileRowMapper(){
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yy");
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
				OBFdFile obFdFile =new OBFdFile();
				String issueDate="";
				String depositMaturityDate="";
				obFdFile.setDepositNumber(rs.getString("DEPOSIT_REFERENCE_NUMBER"));
				//obFdFile.setDepositNumber(rs.getString("DEPOSIT_AMOUNT"));
				Timestamp timestamp = rs.getTimestamp("ISSUE_DATE");
				if(timestamp!=null && !timestamp.toString().isEmpty()){
				String str=timestamp.toString();
				try {
					issueDate = simpleDateFormat.format(simpleDateFormat2.parse(str));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
				//Uma Khot:Fd Start date issue, 2012 is shown as 2020
				try {
					obFdFile.setDateOfDeposit(simpleDateFormat.parse(issueDate));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Timestamp timestamp2 = rs.getTimestamp("DEPOSIT_MATURITY_DATE");
				if(timestamp2!=null && !timestamp2.toString().isEmpty()){
				String str=timestamp2.toString();
				try {
					 depositMaturityDate = simpleDateFormat.format(simpleDateFormat2.parse(str));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
				//Uma Khot:Fd Start date issue, 2012 is shown as 2020
				try {
					obFdFile.setDateOfMaturity(simpleDateFormat.parse(depositMaturityDate));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				obFdFile.setInterestRate(Double.parseDouble(rs.getString("DEPOSIT_INTEREST_RATE")));
				
				return obFdFile;
				}
				}); 
		 return fdDetailsFromClimaStage;
	
	}

	private List<String> getCmsRefIdListForFdUpload(String depositNumber,
			Set<String> dataFromFdCacheView) {
		// TODO Auto-generated method stub
	List<String> cmsRefIdList=new ArrayList<String>();
	
	if(null!=dataFromFdCacheView && dataFromFdCacheView.size()>0){
		for (String string : dataFromFdCacheView) {
			String tempDepositNumber=depositNumber+"~";
			if(string.startsWith(tempDepositNumber)){
				cmsRefIdList.add(string.substring(tempDepositNumber.length()));
			}
		}
	}
	return cmsRefIdList;
	}

	public Set<String> cacheDataFromFdMaterializedView(String materializedViewName)
	{
		Set<String> dataFromCacheView = new HashSet<String>();
			DBUtil dbUtil = null;
			ResultSet rs=null;
			StringBuilder key = null;
			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL("SELECT * FROM "+materializedViewName);
				rs = dbUtil.executeQuery();	
				if(rs!=null)
				{
					while(rs.next())
					{
							key=new StringBuilder();
							key.append(rs.getString("DEPOSIT_REFERENCE_NUMBER"));
							key.append("~");
							key.append(rs.getString("CMS_REF_ID"));
						
						dataFromCacheView.add(key.toString());
					}
				}
			} catch (DBConnectionException e) {
				e.printStackTrace();
			} catch (NoSQLStatementException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finalize(dbUtil,rs);
		
		 return dataFromCacheView;
	}
	
	public void createEntireFdStageFile(List<OBFdFile> objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO STAGE_FD_FILE_UPLOAD " +
		"( ID, FILE_ID,DEPOSIT_REFERENCE_NUMBER,ISSUE_DATE,DEPOSIT_MATURITY_DATE,DEPOSIT_INTEREST_RATE, STATUS, REASON,UPLOAD_STATUS)" +
		" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?)";

		final List<OBFdFile> objectList = objList;
		final int batchSize = objList.size();
		/*for (int j = 0; j < objectList.size(); j += batchSize) {
			final List batchList = objectList.subList(j, j + batchSize > objectList.size() ? objectList.size() : j + batchSize);
			DefaultLogger.debug(this, "#####batch inserted#######:: "+j);*/
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBFdFile fdFile = objectList.get(i);
					ps.setLong(1,fdFile.getFileId());
					ps.setString(2,fdFile.getDepositNumber());
					
					//Uma Khot:Fd Start date issue, 2012 is shown as 2020
					ps.setDate(3,new java.sql.Date(fdFile.getDateOfDeposit().getTime()));
					ps.setDate(4,new java.sql.Date(fdFile.getDateOfMaturity().getTime()));
					ps.setDouble(5,fdFile.getInterestRate());
					ps.setString(6,fdFile.getStatus());
					ps.setString(7,fdFile.getReason());
					ps.setString(8,fdFile.getUploadStatus());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
		//}
	}
	
	public void updateFdDetailStage(List<OBFdFile> objList)throws FileUploadException,SQLException{
		//Updates FD Details into Clims CMS_STAGE_CASH_DEPOSIT 
		final List<OBFdFile> objectList = objList;
	//	final int batchSize = objList.size();
	
			getJdbcTemplate().batchUpdate(updateFdDetailsStage,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBFdFile fdFile = objectList.get(i);
					
					//Uma Khot:Fd Start date issue, 2012 is shown as 2020
					ps.setDate(1,new java.sql.Date(fdFile.getDateOfDeposit().getTime()));
					ps.setDate(2,new java.sql.Date(fdFile.getDateOfMaturity().getTime()));
					ps.setDouble(3,fdFile.getInterestRate());
					ps.setString(4,fdFile.getDepositNumber());
					ps.setString(5,fdFile.getCmsRefId());
					
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
//		}
	}
	
	public class FdFileRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yy");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			String issueDate="";
			String depositMaturityDate="";
			OBFdFile result = new OBFdFile();
			result.setId(rs.getLong("id"));
			result.setDepositNumber(rs.getString("DEPOSIT_REFERENCE_NUMBER"));
		
			Timestamp timestamp = rs.getTimestamp("ISSUE_DATE");
			if(timestamp!=null && !timestamp.toString().isEmpty()){
			String str=timestamp.toString();
			try {
				issueDate = simpleDateFormat.format(simpleDateFormat2.parse(str));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
			//Uma Khot:Fd Start date issue, 2012 is shown as 2020
			try {
				result.setDateOfDeposit(simpleDateFormat.parse(issueDate));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Timestamp timestamp2 = rs.getTimestamp("DEPOSIT_MATURITY_DATE");
			if(timestamp2!=null && !timestamp2.toString().isEmpty()){
			String str=timestamp2.toString();
			try {
				 depositMaturityDate = simpleDateFormat.format(simpleDateFormat2.parse(str));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
			//Uma Khot:Fd Start date issue, 2012 is shown as 2020
			try {
				result.setDateOfMaturity(simpleDateFormat.parse(depositMaturityDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			String issuseDate = rs.getString("ISSUE_DATE");
			result.setDateOfDeposit(issuseDate.substring(0, issuseDate.contains(" ")? issuseDate.indexOf(" ") : issuseDate.length()));
			String maturityDate = rs.getString("DEPOSIT_MATURITY_DATE");
			result.setDateOfMaturity(maturityDate.substring(0, maturityDate.contains(" ")? maturityDate.indexOf(" "):maturityDate.length())); */
			result.setInterestRate(rs.getDouble("DEPOSIT_INTEREST_RATE"));
			result.setReason(rs.getString("REASON"));
			result.setStatus(rs.getString("STATUS"));
			result.setUploadStatus(rs.getString("UPLOAD_STATUS"));

			return result;
		}
	}
	public SearchResult getAllFdFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=STAGE_FD_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new FdFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving FD File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public SearchResult getAllActualFdFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=ACTUAL_FD_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new FdFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving Actual FD File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}


	public void createEntireFdActualFile(List<OBFdFile> objectList)
			throws FileUploadException, SQLException {
		// TODO Auto-generated method stub

		String sql = "INSERT INTO CMS_FD_FILE_UPLOAD " +
	    "( ID, FILE_ID,DEPOSIT_REFERENCE_NUMBER,ISSUE_DATE,DEPOSIT_MATURITY_DATE,DEPOSIT_INTEREST_RATE, STATUS, REASON,UPLOAD_STATUS)" +
		" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?)";

		final List<OBFdFile> objList = objectList;
		final int batchSize = objectList.size();
	
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBFdFile fdFile = objList.get(i);
					ps.setLong(1,fdFile.getFileId());
					ps.setString(2,fdFile.getDepositNumber());
					
					//Uma Khot:Fd Start date issue, 2012 is shown as 2020
					ps.setDate(3,new java.sql.Date(fdFile.getDateOfDeposit().getTime()));
					ps.setDate(4,new java.sql.Date(fdFile.getDateOfMaturity().getTime()));
					ps.setDouble(5,fdFile.getInterestRate());
					ps.setString(6,fdFile.getStatus());
					ps.setString(7,fdFile.getReason());
					ps.setString(8,fdFile.getUploadStatus());
				}
				public int getBatchSize() {
					return objList.size();
				}
			});
	
	
	}


	public HashMap insertFdfileActual(ArrayList result,
			CheckerApproveFileUploadCmd cmd, String fileName,
			Set<String> dataFromFdCacheView) {
		// TODO Auto-generated method stub

		List<OBFdFile> totalUploadedList= new ArrayList<OBFdFile>();
		List<OBFdFile> newTotalUploadedList= new ArrayList<OBFdFile>(); // set the CmsRefId to update the records in Clims Actual Table
		List errorList= new ArrayList();
		HashMap retMap = new HashMap();
		String upload_status="";
		Timestamp st = null;
		IUbsErrorLog objUbsError=new OBUbsErrorLog();
		String errMsg="";
		String tempData="";
		String strArrTemp[]=new String[3];
		List selectCustDetails=new ArrayList();
		List<String> fileDepositNumber=new ArrayList<String>();
		List<OBFdFile> fdDetailsFromClimaStage=new ArrayList<OBFdFile>();
/*		String makerId = trxValue.getStagingfileUpload().getUploadBy();
		Date makerDate = trxValue.getStagingfileUpload().getUploadTime(); */
		try {
		// No need to do this setting Auditing INfo incase of FD Upload
			
			DefaultLogger.debug(this, "############### Inside  insertFdfileActual##################:: ");
		 	if(result!=null&& result.size() != 0)
			{
				for (int index = 0; index < result.size(); index++) {
					OBFdFile obj = (OBFdFile) result.get(index);
				
					if(obj.getUploadStatus()!=null && obj.getUploadStatus().equals("Y") )
						totalUploadedList.add(obj);
						
					else if(obj.getUploadStatus()!=null && obj.getUploadStatus().equals("N") ){
							if(obj.getDepositNumber()!=null)
							fileDepositNumber.add(obj.getDepositNumber().trim());
						}
					
				}
			} 
			//set the CMSRef Id
		 	if(null!=dataFromFdCacheView && dataFromFdCacheView.size()>0){
				for(String str:dataFromFdCacheView){
					String substring = str.substring(0, str.lastIndexOf("~"));
					for(OBFdFile ObfdFile:totalUploadedList){
						if(ObfdFile.getDepositNumber().equals(substring)){
							OBFdFile newObj=new OBFdFile();
							newObj.setDepositNumber(ObfdFile.getDepositNumber());
							newObj.setDateOfDeposit(ObfdFile.getDateOfDeposit());
							newObj.setDateOfMaturity(ObfdFile.getDateOfMaturity());
							newObj.setInterestRate(ObfdFile.getInterestRate());
							newObj.setCmsRefId(str.substring(str.lastIndexOf("~")+1));
						//	ObfdFile.setCmsRefId(str.substring(str.lastIndexOf("~")+1));
						//	newTotalUploadedList.add(ObfdFile);
							
							newTotalUploadedList.add(newObj);
						}
					}
				}
			}
		 	
		 	//Update the Clims Actual TABLE
			int batchSize = 200;
			for (int j = 0; j < newTotalUploadedList.size(); j += batchSize) {
				List<OBFdFile> batchList = newTotalUploadedList.subList(j, j + batchSize > newTotalUploadedList.size() ? newTotalUploadedList.size() : j + batchSize);
				updateFdDetailActual(batchList);
			}
			DefaultLogger.debug(this, "##########################Actual Clims table Updated For FD Upload #######:: ");
			st = new Timestamp(System.currentTimeMillis());
			
			List<String> depositReceiptNoParams= new ArrayList<String>();
			List<String> cmsRefIdParams=new ArrayList<String>();
			//Extarct Those CMS ref Id where there is no match in FD File
			if(null!=dataFromFdCacheView && dataFromFdCacheView.size()>0){
				for(String str:dataFromFdCacheView){
					String substring = str.substring(0, str.lastIndexOf("~"));
					if(fileDepositNumber.contains(substring)){
						depositReceiptNoParams.add(substring);
						cmsRefIdParams.add(str.substring(str.lastIndexOf("~")+1));
					}
				}
			}
			
			
			for (int j = 0; j < depositReceiptNoParams.size(); j += batchSize) {
				List<String> depositReceiptNoParamsList = depositReceiptNoParams.subList(j, j + batchSize > depositReceiptNoParams.size() ? depositReceiptNoParams.size() : j + batchSize);
				List<String> cmsRefIdParamsList = cmsRefIdParams.subList(j, j + batchSize > cmsRefIdParams.size() ? cmsRefIdParams.size() : j + batchSize);
				fdDetailsFromClimaStage.addAll(getFdDetailsFromClimsActual(depositReceiptNoParamsList,cmsRefIdParamsList));	
			}
			
		
			for(int i=0;i<fdDetailsFromClimaStage.size();i++)
			{				
				OBFdFile obFdFile=fdDetailsFromClimaStage.get(i);
				errMsg="Available in CLIMS but not in Fd File.";
				obFdFile.setStatus("FAIL");
				obFdFile.setReason(errMsg);
				obFdFile.setUploadStatus("N");
				errorList.add(obFdFile);
			} 

		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
			"Unable to update/insert dad retrived form CSV file");

		}
		retMap.put("totalUploadedList", totalUploadedList);
		retMap.put("errorList", errorList);
		return retMap;
	
	}
	
	public void updateFdDetailActual(List<OBFdFile> objList)throws FileUploadException,SQLException{
		//Updates FD Details into Clims CMS_CASH_DEPOSIT 
		final List<OBFdFile> objectList = objList;
	
			getJdbcTemplate().batchUpdate(updateFdDetailsActual,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBFdFile fdFile = objectList.get(i);
					
					//Uma Khot:Fd Start date issue, 2012 is shown as 2020
					ps.setDate(1,new java.sql.Date(fdFile.getDateOfDeposit().getTime()));
					ps.setDate(2,new java.sql.Date(fdFile.getDateOfMaturity().getTime()));
					ps.setDouble(3,fdFile.getInterestRate());
					ps.setString(4,fdFile.getDepositNumber());
					ps.setString(5,fdFile.getCmsRefId());
					
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
//		}
	}

	
	private List<OBFdFile> getFdDetailsFromClimsActual(List<String> depositReceiptNoParams,List<String> cmsRefIdParams) {
		// TODO Auto-generated method stub
	
		List<OBFdFile> fdDetailsFromClimaStage = new ArrayList<OBFdFile>();
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
		String sql="select DEPOSIT_REFERENCE_NUMBER, ISSUE_DATE, DEPOSIT_MATURITY_DATE ,DEPOSIT_INTEREST_RATE  from CMS_CASH_DEPOSIT where DEPOSIT_REFERENCE_NUMBER in (:depositReceiptNoParams) and CMS_REF_ID in (:cmsRefIdParams) and status='ACTIVE' and active='active'";

		Map<String, List<String>> parameter=new HashMap<String, List<String>>();
		parameter.put("depositReceiptNoParams", depositReceiptNoParams);
		parameter.put("cmsRefIdParams", cmsRefIdParams); 
		
		fdDetailsFromClimaStage= namedParameterJdbcTemplate.query(sql, parameter, new FdFileRowMapper(){
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			//	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yy");
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
				OBFdFile obFdFile =new OBFdFile();
				String issueDate="";
				String depositMaturityDate="";
				obFdFile.setDepositNumber(rs.getString("DEPOSIT_REFERENCE_NUMBER"));
				
				Timestamp timestamp = rs.getTimestamp("ISSUE_DATE");
				if(timestamp!=null && !timestamp.toString().isEmpty()){
				String str=timestamp.toString();
				try {
					issueDate = simpleDateFormat.format(simpleDateFormat2.parse(str));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
				//Uma Khot:Fd Start date issue, 2012 is shown as 2020
				try {
					obFdFile.setDateOfDeposit(simpleDateFormat.parse(issueDate));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Timestamp timestamp2 = rs.getTimestamp("DEPOSIT_MATURITY_DATE");
				if(timestamp2!=null && !timestamp2.toString().isEmpty()){
				String str=timestamp2.toString();
				try {
					 depositMaturityDate = simpleDateFormat.format(simpleDateFormat2.parse(str));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
				//Uma Khot:Fd Start date issue, 2012 is shown as 2020
				try {
					obFdFile.setDateOfMaturity(simpleDateFormat.parse(depositMaturityDate));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				obFdFile.setInterestRate(Double.parseDouble(rs.getString("DEPOSIT_INTEREST_RATE")));
				
				return obFdFile;
				}
				}); 
		 return fdDetailsFromClimaStage;
	
	}
	//Start:Uma Khot:21/01/2016 Added for FD Upload NEw Logic as FD Upload was taking time
	public void updateCashDepositToNull(String tableName){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		Boolean isDBConnectionException = Boolean.FALSE;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("update "+tableName+" set upload_status = null");
					int noOfRecords=dbUtil.executeUpdate();
					DefaultLogger.debug(this, "Updated "+noOfRecords +" Records upload_status to 'NULL'");		
			} catch (DBConnectionException e) {
				isDBConnectionException = Boolean.TRUE;
				e.printStackTrace();		
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateCashDepositToNull:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateCashDepositToNull:"+e.getMessage());
			}
			finalize(dbUtil,rs);
			
			//Retry using JDBC for Batch (Websphere env)
			if (isDBConnectionException) {
				String query = "update "+tableName+" set upload_status = null";

				try {
					int noOfRecordsUpdated = getJdbcTemplate().update(query);
					DefaultLogger.debug(this, "Successfully Updated "+noOfRecordsUpdated +" Records upload_status to 'NULL'");
				} catch (Exception e) {
					DefaultLogger.info(this, "Exception caught in updateCashDepositToNull ::" + e.getMessage());
					e.printStackTrace();
				}

			}
			
		}
	
	public void updateStageFdUploadStatus(){
		 
		DBUtil dbUtil = null;
		ResultSet rs=null;
		Boolean isDBConnectionException = Boolean.FALSE;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("MERGE INTO CMS_STAGE_CASH_DEPOSIT dp USING "+
		 "(select * from  transaction where transaction_type='COL' ) tr ON (tr.staging_reference_id = dp.cms_collateral_id)"+
		"WHEN MATCHED THEN UPDATE SET dp.upload_status = 'N'");
					int noOfRecords=dbUtil.executeUpdate();
					DefaultLogger.debug(this, "Updated "+noOfRecords +" Records upload_status to 'N'");		
					
			} catch (DBConnectionException e) {
				isDBConnectionException = Boolean.TRUE;
				e.printStackTrace();		
		
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateStageFdUploadStatus:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateStageFdUploadStatus:"+e.getMessage());
			}
			finalize(dbUtil,rs);
			
			//Retry using JDBC for Batch (Websphere env)
			if (isDBConnectionException) {
				String query = "MERGE INTO CMS_STAGE_CASH_DEPOSIT dp USING "+
						 "(select * from  transaction where transaction_type='COL' ) tr ON (tr.staging_reference_id = dp.cms_collateral_id)"+
							"WHEN MATCHED THEN UPDATE SET dp.upload_status = 'N'";

				try {
					
					int noOfRecordsUpdated = getJdbcTemplate().update(query);
					DefaultLogger.debug(this, "Successfully Updated "+noOfRecordsUpdated +" Records upload_status to 'N'");
					
				} catch (Exception e) {
					DefaultLogger.info(this, "Exception caught in updateStageFdUploadStatus ::" + e.getMessage());
					e.printStackTrace();
				}
			}
			
	}
	
	public void updateStageFdDetails(long fileId){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		Boolean isDBConnectionException = Boolean.FALSE;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL(" MERGE INTO (select * from CMS_STAGE_CASH_DEPOSIT where upload_status='N') dp "+
" USING (select distinct DEPOSIT_REFERENCE_NUMBER,ISSUE_DATE,DEPOSIT_MATURITY_DATE,DEPOSIT_INTEREST_RATE from  STAGE_FD_FILE_UPLOAD where file_id= "+ fileId+") fl ON (fl.DEPOSIT_REFERENCE_NUMBER = dp.DEPOSIT_REFERENCE_NUMBER) "+
" WHEN MATCHED THEN UPDATE  SET dp.upload_status = 'Y', dp.ISSUE_DATE=fl.ISSUE_DATE , "+ 
" dp.DEPOSIT_MATURITY_DATE =fl.DEPOSIT_MATURITY_DATE, dp.DEPOSIT_INTEREST_RATE =fl.DEPOSIT_INTEREST_RATE ");
					int noOfRecords=dbUtil.executeUpdate();
					DefaultLogger.debug(this, "Updated "+noOfRecords +" Records dateOfMaturity, dateOfIssue, interestRate, and uploadStatus");		
					
			} catch (DBConnectionException e) {
				isDBConnectionException = Boolean.TRUE;
				e.printStackTrace();		
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateStageFdDetails:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateStageFdDetails:"+e.getMessage());
			}
			finalize(dbUtil,rs);
			
			//Retry using JDBC for Batch (Websphere env)
			if (isDBConnectionException) {
				String query = " MERGE INTO (select * from CMS_STAGE_CASH_DEPOSIT where upload_status='N') dp "+
						" USING (select distinct DEPOSIT_REFERENCE_NUMBER,ISSUE_DATE,DEPOSIT_MATURITY_DATE,DEPOSIT_INTEREST_RATE from  STAGE_FD_FILE_UPLOAD where file_id= "+ fileId+") fl ON (fl.DEPOSIT_REFERENCE_NUMBER = dp.DEPOSIT_REFERENCE_NUMBER) "+
						" WHEN MATCHED THEN UPDATE  SET dp.upload_status = 'Y', dp.ISSUE_DATE=fl.ISSUE_DATE , "+ 
						" dp.DEPOSIT_MATURITY_DATE =fl.DEPOSIT_MATURITY_DATE, dp.DEPOSIT_INTEREST_RATE =fl.DEPOSIT_INTEREST_RATE ";
				
				try {
				
					int noOfRecordsUpdated = getJdbcTemplate().update(query);
					DefaultLogger.debug(this, "Successfully Updated "+noOfRecordsUpdated +" Records dateOfMaturity, dateOfIssue, interestRate, and uploadStatus");
					
				} catch (Exception e) {
					DefaultLogger.info(this, "Exception caught in updateStageFdDetails ::" + e.getMessage());
					e.printStackTrace();
				}
			}
	}
	
	public void updateTempFdDetails(long fileId){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		Boolean isDBConnectionException = Boolean.FALSE;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("MERGE INTO (select * from STAGE_FD_FILE_UPLOAD where file_id="+ fileId +
							" ) fl USING (select distinct  DEPOSIT_REFERENCE_NUMBER,ISSUE_DATE,DEPOSIT_MATURITY_DATE,DEPOSIT_INTEREST_RATE from CMS_STAGE_CASH_DEPOSIT  where  upload_status is not null "+
							" ) dp ON (fl.DEPOSIT_REFERENCE_NUMBER = dp.DEPOSIT_REFERENCE_NUMBER) "+
							" WHEN MATCHED THEN UPDATE "+
							" SET fl.status='PASS', fl.REASON='FD Details Present in File and CLIMS' "+
							" WHEN not MATCHED THEN insert (fl.ID,fl.FILE_ID,fl.DEPOSIT_REFERENCE_NUMBER,fl.ISSUE_DATE,fl.DEPOSIT_MATURITY_DATE,fl.DEPOSIT_INTEREST_RATE,fl.STATUS,fl.REASON,fl.UPLOAD_STATUS) "+
							" values (CMS_FILEUPLOAD_SEQ.nextval,"+fileId+",dp.DEPOSIT_REFERENCE_NUMBER,to_timestamp(dp.ISSUE_DATE,'DD-MM-RR HH12:MI:SS.FF AM'), "+
							" to_timestamp(dp.DEPOSIT_MATURITY_DATE,'DD-MM-RR HH12:MI:SS.FF AM'),dp.DEPOSIT_INTEREST_RATE,'FAIL','Available in CLIMS but not in Fd File.','N')");
					int noOfRecords=dbUtil.executeUpdate();
					DefaultLogger.debug(this, "Updated "+noOfRecords +" Records in temp stage table.");		
					
			} catch (DBConnectionException e) {
				isDBConnectionException = Boolean.TRUE;
				e.printStackTrace();		
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateTempFdDetails:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateTempFdDetails:"+e.getMessage());
			}
			finalize(dbUtil,rs);
			
			//Retry using JDBC for Batch (Websphere env)
			if (isDBConnectionException) {
				String query = "MERGE INTO (select * from STAGE_FD_FILE_UPLOAD where file_id="+ fileId +
						" ) fl USING (select distinct  DEPOSIT_REFERENCE_NUMBER,ISSUE_DATE,DEPOSIT_MATURITY_DATE,DEPOSIT_INTEREST_RATE from CMS_STAGE_CASH_DEPOSIT  where  upload_status is not null "+
						" ) dp ON (fl.DEPOSIT_REFERENCE_NUMBER = dp.DEPOSIT_REFERENCE_NUMBER) "+
						" WHEN MATCHED THEN UPDATE "+
						" SET fl.status='PASS', fl.REASON='FD Details Present in File and CLIMS' "+
						" WHEN not MATCHED THEN insert (fl.ID,fl.FILE_ID,fl.DEPOSIT_REFERENCE_NUMBER,fl.ISSUE_DATE,fl.DEPOSIT_MATURITY_DATE,fl.DEPOSIT_INTEREST_RATE,fl.STATUS,fl.REASON,fl.UPLOAD_STATUS) "+
						" values (CMS_FILEUPLOAD_SEQ.nextval,"+fileId+",dp.DEPOSIT_REFERENCE_NUMBER,to_timestamp(dp.ISSUE_DATE,'DD-MM-RR HH12:MI:SS.FF AM'), "+
						" to_timestamp(dp.DEPOSIT_MATURITY_DATE,'DD-MM-RR HH12:MI:SS.FF AM'),dp.DEPOSIT_INTEREST_RATE,'FAIL','Available in CLIMS but not in Fd File.','N')";

				try {
					int noOfRecordsUpdated = getJdbcTemplate().update(query);
					DefaultLogger.debug(this, "Successfully Updated "+noOfRecordsUpdated +" Records in temp stage table.");
				} catch (Exception e) {
					DefaultLogger.info(this, "Exception caught in updateTempFdDetails ::" + e.getMessage());
					e.printStackTrace();
				}
			}
	}
	
	
	public long getCount(long fileId, String tableName, String status){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		Boolean isDBConnectionException = Boolean.FALSE;
		long count=0;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("select count(1) from "+tableName+" where file_id="+fileId+" and status='"+status+"' and upload_status='Y'");
					rs=dbUtil.executeQuery();
					if(null!=rs){
					while(rs.next()){
						count=rs.getLong(1);
				
					}
					}
			} catch (DBConnectionException e) {
				isDBConnectionException = Boolean.TRUE;
				e.printStackTrace();
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in getCount:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in getCount:"+e.getMessage());
			}
			finalize(dbUtil,rs);
			
			//Retry using JDBC for Batch (Websphere env)
			if (isDBConnectionException) {
				String query = "select count(1) from "+tableName+" where file_id="+fileId+" and status='"+status+"' and upload_status='Y'";

				try {
					count = (Long) getJdbcTemplate().queryForObject(query, new Object[]{}, Long.class);
					DefaultLogger.debug(this, ":: count is "+count);
					
				} catch (Exception e) {
					DefaultLogger.info(this, "Exception caught in getCount ::" + e.getMessage());
					e.printStackTrace();
				}
			}
		return count;
		
	}
	
	public List<OBFdFile> getTotalUploadedList(long fileId,String tableName){
		List obFdFileList=new ArrayList<OBFdFile>();
		DBUtil dbUtil = null;
		ResultSet rs=null;
		long count=0;
		Boolean isDBConnectionException = Boolean.FALSE;
		SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yyyy");
	/*	String issueDateStr="";
		String maturityDateStr=""; */
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("select DEPOSIT_REFERENCE_NUMBER,ISSUE_DATE ,DEPOSIT_MATURITY_DATE ,DEPOSIT_INTEREST_RATE , STATUS, REASON from "+tableName+" where file_id="+fileId );
					rs=dbUtil.executeQuery();
					if(null!=rs){
					while(rs.next()){
						OBFdFile obFdFile=new OBFdFile();
						Date issueDate=rs.getDate("ISSUE_DATE");
						Date maturityDate=rs.getDate("DEPOSIT_MATURITY_DATE");
						
					/*	if(null!=issueDate && !("".equals(issueDate))){
						 issueDateStr=	sdf.format(issueDate);
						} 
						if(null!=maturityDate && !("".equals(maturityDate))){
							maturityDateStr=	sdf.format(maturityDate);
							} */
						obFdFile.setDepositNumber(rs.getString("DEPOSIT_REFERENCE_NUMBER"));
					//	obFdFile.setDateOfDeposit(issueDateStr);
					
					//Uma Khot:Fd Start date issue, 2012 is shown as 2020
						obFdFile.setDateOfDeposit(issueDate);
						obFdFile.setDateOfMaturity(maturityDate);
						
					//	obFdFile.setDateOfMaturity(maturityDateStr);
						obFdFile.setInterestRate(rs.getDouble("DEPOSIT_INTEREST_RATE"));
						obFdFile.setStatus(rs.getString("STATUS"));
						obFdFile.setReason(rs.getString("REASON"));
						obFdFileList.add(obFdFile);
					}
					DefaultLogger.debug(this, "Completed getTotalUploadedList.");
					}
			} catch (DBConnectionException e) {
				isDBConnectionException = Boolean.TRUE;
				e.printStackTrace();
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in getTotalUploadedList:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in getTotalUploadedList:"+e.getMessage());
			}
			finalize(dbUtil,rs);
			
			//Retry using JDBC for Batch (Websphere env)
			if (isDBConnectionException) {
				String query = "select DEPOSIT_REFERENCE_NUMBER,ISSUE_DATE ,DEPOSIT_MATURITY_DATE ,DEPOSIT_INTEREST_RATE , STATUS, REASON from "+tableName+" where file_id="+fileId ;

				try {
					
					obFdFileList = (List) getJdbcTemplate().query(query, new ArrayList<Object>().toArray(),
							new ResultSetExtractor() {
								public List extractData(ResultSet rs) throws SQLException, DataAccessException {

									List obFdFileListTemp =new ArrayList<OBFdFile>();

									while (rs.next()) {
										
										OBFdFile obFdFile=new OBFdFile();
										Date issueDate=rs.getDate("ISSUE_DATE");
										Date maturityDate=rs.getDate("DEPOSIT_MATURITY_DATE");
										obFdFile.setDepositNumber(rs.getString("DEPOSIT_REFERENCE_NUMBER"));
										obFdFile.setDateOfDeposit(issueDate);
										obFdFile.setDateOfMaturity(maturityDate);
										obFdFile.setInterestRate(rs.getDouble("DEPOSIT_INTEREST_RATE"));
										obFdFile.setStatus(rs.getString("STATUS"));
										obFdFile.setReason(rs.getString("REASON"));
										obFdFileListTemp.add(obFdFile);
									}
									return obFdFileListTemp;
								}
							});
					
				} catch (Exception e) {
					DefaultLogger.info(this, "Exception caught in getTotalUploadedList ::" + e.getMessage());
					e.printStackTrace();
				}
			}
		return obFdFileList;
	}
	
	public void updateActualFdUploadStatus(){
		 
		DBUtil dbUtil = null;
		ResultSet rs=null;
		Boolean isDBConnectionException = Boolean.FALSE;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("update CMS_CASH_DEPOSIT set upload_status='N'");
					int noOfRecords=dbUtil.executeUpdate();
					DefaultLogger.debug(this, "Updated "+noOfRecords +" Records upload_status to 'N'");		
					
			} catch (DBConnectionException e) {
				isDBConnectionException = Boolean.TRUE;
				e.printStackTrace();
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateActualFdUploadStatus:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateActualFdUploadStatus:"+e.getMessage());
			}
			finalize(dbUtil,rs);
			
			//Retry using JDBC for Batch (Websphere env)
			if (isDBConnectionException) {
				String query = "update CMS_CASH_DEPOSIT set upload_status='N'";

				try {
					
					int noOfRecordsUpdated = getJdbcTemplate().update(query);
					DefaultLogger.debug(this, "Successfully Updated "+noOfRecordsUpdated +" Records upload_status to 'N'");
					
				} catch (Exception e) {
					DefaultLogger.info(this, "Exception caught in updateActualFdUploadStatus ::" + e.getMessage());
					e.printStackTrace();
				}
			}
			
	}
	
	public void insertFileDataToTempActualTable(long actualFileId,String stageFileId){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		Boolean isDBConnectionException = Boolean.FALSE;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("insert into CMS_FD_FILE_UPLOAD  ( select CMS_FILEUPLOAD_SEQ.nextval,"+actualFileId+",DEPOSIT_REFERENCE_NUMBER,to_timestamp(ISSUE_DATE,'DD-MM-RR HH12:MI:SS.FF AM'), "+
 " to_timestamp(DEPOSIT_MATURITY_DATE,'DD-MM-RR HH12:MI:SS.FF AM'),DEPOSIT_INTEREST_RATE,STATUS,REASON,UPLOAD_STATUS from STAGE_FD_FILE_UPLOAD where file_id="+stageFileId+" and upload_status='Y')");
					int noOfRecords=dbUtil.executeUpdate();
					DefaultLogger.debug(this, "File data is inserted into Temp Actual Table: "+noOfRecords);		
					
			} catch (DBConnectionException e) {
				isDBConnectionException = Boolean.TRUE;
				e.printStackTrace();
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in insertFileDataToTempActualTable:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in insertFileDataToTempActualTable:"+e.getMessage());
			}
			finalize(dbUtil,rs);
			
			//Retry using JDBC for Batch (Websphere env)
			if (isDBConnectionException) {
				String query = "insert into CMS_FD_FILE_UPLOAD  ( select CMS_FILEUPLOAD_SEQ.nextval,"+actualFileId+",DEPOSIT_REFERENCE_NUMBER,to_timestamp(ISSUE_DATE,'DD-MM-RR HH12:MI:SS.FF AM'), "+
						 " to_timestamp(DEPOSIT_MATURITY_DATE,'DD-MM-RR HH12:MI:SS.FF AM'),DEPOSIT_INTEREST_RATE,STATUS,REASON,UPLOAD_STATUS from STAGE_FD_FILE_UPLOAD where file_id="+stageFileId+" and upload_status='Y')";

				try {
					
					int noOfRecordsInserted = getJdbcTemplate().update(query);
					DefaultLogger.debug(this, "Successfully Inserted "+noOfRecordsInserted +" File data into Temp Actual Table");
					
				} catch (Exception e) {
					DefaultLogger.info(this, "Exception caught in insertFileDataToTempActualTable ::" + e.getMessage());
					e.printStackTrace();
				}
			}
	}
	
	public void updateActualFdDetails(long fileId){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		Boolean isDBConnectionException = Boolean.FALSE;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("MERGE INTO (select * from CMS_CASH_DEPOSIT where upload_status='N') dp USING "+
" (select distinct  DEPOSIT_REFERENCE_NUMBER,ISSUE_DATE,DEPOSIT_MATURITY_DATE,DEPOSIT_INTEREST_RATE from  CMS_FD_FILE_UPLOAD where file_id="+fileId+
" and status='PASS' ) fl ON (fl.DEPOSIT_REFERENCE_NUMBER = dp.DEPOSIT_REFERENCE_NUMBER) WHEN MATCHED THEN UPDATE SET dp.upload_status = 'Y',dp.ISSUE_DATE=fl.ISSUE_DATE , "+
" dp.DEPOSIT_MATURITY_DATE =fl.DEPOSIT_MATURITY_DATE,dp.DEPOSIT_INTEREST_RATE =fl.DEPOSIT_INTEREST_RATE");
					int noOfRecords=dbUtil.executeUpdate();
					
					DefaultLogger.debug(this, "Updated Actual CASH DEPOSIT Table: "+noOfRecords);		
					
			} catch (DBConnectionException e) {
				isDBConnectionException = Boolean.TRUE;
				e.printStackTrace();
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateActualFdDetails:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateActualFdDetails:"+e.getMessage());
			}
			finalize(dbUtil,rs);
			
			//Retry using JDBC for Batch (Websphere env)
			if (isDBConnectionException) {
				String query = "MERGE INTO (select * from CMS_CASH_DEPOSIT where upload_status='N') dp USING "+
						" (select distinct  DEPOSIT_REFERENCE_NUMBER,ISSUE_DATE,DEPOSIT_MATURITY_DATE,DEPOSIT_INTEREST_RATE from  CMS_FD_FILE_UPLOAD where file_id="+fileId+
						" and status='PASS' ) fl ON (fl.DEPOSIT_REFERENCE_NUMBER = dp.DEPOSIT_REFERENCE_NUMBER) WHEN MATCHED THEN UPDATE SET dp.upload_status = 'Y',dp.ISSUE_DATE=fl.ISSUE_DATE , "+
						" dp.DEPOSIT_MATURITY_DATE =fl.DEPOSIT_MATURITY_DATE,dp.DEPOSIT_INTEREST_RATE =fl.DEPOSIT_INTEREST_RATE";

				try {
					int noOfRecordsUpdated = getJdbcTemplate().update(query);
					DefaultLogger.debug(this, "Successfully Updated "+noOfRecordsUpdated +" records in Actual CASH DEPOSIT Table:");
					
				} catch (Exception e) {
					DefaultLogger.info(this, "Exception caught in updateActualFdDetails ::" + e.getMessage());
					e.printStackTrace();
				}
			}
	}
	
	public void updateTempActualFdDetails(long fileId){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		Boolean isDBConnectionException = Boolean.FALSE;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("	MERGE INTO (select * from CMS_FD_FILE_UPLOAD where file_id= "+fileId+") fl "+
" USING (select distinct DEPOSIT_REFERENCE_NUMBER from CMS_CASH_DEPOSIT  where  upload_status='Y' "+
" ) dp ON (fl.DEPOSIT_REFERENCE_NUMBER = dp.DEPOSIT_REFERENCE_NUMBER) "+
" WHEN MATCHED THEN UPDATE SET fl.status='PASS',fl.REASON='FD Details Present in File and CLIMS' ");
					int noOfRecords=dbUtil.executeUpdate();
					
					DefaultLogger.debug(this, "Updated Temp Actual File Upload Table: "+noOfRecords);		
					
			} catch (DBConnectionException e) {
				isDBConnectionException = Boolean.TRUE;
				e.printStackTrace();
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateTempActualFdDetails:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateTempActualFdDetails:"+e.getMessage());
			}
			finalize(dbUtil,rs);
			
			//Retry using JDBC for Batch (Websphere env)
			if (isDBConnectionException) {
				String query = "	MERGE INTO (select * from CMS_FD_FILE_UPLOAD where file_id= "+fileId+") fl "+
						" USING (select distinct DEPOSIT_REFERENCE_NUMBER from CMS_CASH_DEPOSIT  where  upload_status='Y' "+
						" ) dp ON (fl.DEPOSIT_REFERENCE_NUMBER = dp.DEPOSIT_REFERENCE_NUMBER) "+
						" WHEN MATCHED THEN UPDATE SET fl.status='PASS',fl.REASON='FD Details Present in File and CLIMS' ";

				try {
					
					int noOfRecordsUpdated = getJdbcTemplate().update(query);
					DefaultLogger.debug(this, "Successfully Updated "+noOfRecordsUpdated +" records in Temp Actual File Upload Table:");
					
				} catch (Exception e) {
					DefaultLogger.info(this, "Exception caught in updateTempActualFdDetails ::" + e.getMessage());
					e.printStackTrace();
				}
			}
	}
	
	public void insertTempActualFdDetails(long fileId){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		Boolean isDBConnectionException = Boolean.FALSE;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("insert into CMS_FD_FILE_UPLOAD  ( select CMS_FILEUPLOAD_SEQ.nextval,"+fileId+",DEPOSIT_REFERENCE_NUMBER,to_timestamp(ISSUE_DATE,'DD-MM-RR HH12:MI:SS.FF AM'), "+
 " to_timestamp(DEPOSIT_MATURITY_DATE,'DD-MM-RR HH12:MI:SS.FF AM'),DEPOSIT_INTEREST_RATE,'FAIL','Available in CLIMS but not in Fd File.','N' from CMS_CASH_DEPOSIT where upload_status = 'N')");
					int noOfRecords=dbUtil.executeUpdate();
					
					DefaultLogger.debug(this, "Inserted Non Matching Recods into Temp Actual File Upload Table: "+noOfRecords);		
			
			} catch (DBConnectionException e) {
				isDBConnectionException = Boolean.TRUE;
				e.printStackTrace();
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in insertTempActualFdDetails:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in insertTempActualFdDetails:"+e.getMessage());
			}
			finalize(dbUtil,rs);
			
			//Retry using JDBC for Batch (Websphere env)
			if (isDBConnectionException) {
				String query = "insert into CMS_FD_FILE_UPLOAD  ( select CMS_FILEUPLOAD_SEQ.nextval,"+fileId+",DEPOSIT_REFERENCE_NUMBER,to_timestamp(ISSUE_DATE,'DD-MM-RR HH12:MI:SS.FF AM'), "+
						 " to_timestamp(DEPOSIT_MATURITY_DATE,'DD-MM-RR HH12:MI:SS.FF AM'),DEPOSIT_INTEREST_RATE,'FAIL','Available in CLIMS but not in Fd File.','N' from CMS_CASH_DEPOSIT where upload_status = 'N')";

				try {
					
					int noOfRecordsInserted = getJdbcTemplate().update(query);
					DefaultLogger.debug(this, "Successfully Inserted "+noOfRecordsInserted +" Non Matching Recods into Temp Actual File Upload Table");
					
				} catch (Exception e) {
					DefaultLogger.info(this, "Exception caught in insertTempActualFdDetails ::" + e.getMessage());
					e.printStackTrace();
				}
			}
	}
	
	public List<OBFdFile> getTotalUploadedFileList(long fileId,String tableName){
		List obFdFileList=new ArrayList<OBFdFile>();
		DBUtil dbUtil = null;
		ResultSet rs=null;
		Boolean isDBConnectionException = Boolean.FALSE;
		long count=0;
		SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yyyy");
	/*	String issueDateStr="";
		String maturityDateStr=""; */
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("select DEPOSIT_REFERENCE_NUMBER,ISSUE_DATE ,DEPOSIT_MATURITY_DATE ,DEPOSIT_INTEREST_RATE , STATUS, REASON from "+tableName+" where file_id="+fileId+" AND UPLOAD_STATUS='Y'" );
					rs=dbUtil.executeQuery();
					if(null!=rs){
					while(rs.next()){
						OBFdFile obFdFile=new OBFdFile();
						Date issueDate=rs.getDate("ISSUE_DATE");
						Date maturityDate=rs.getDate("DEPOSIT_MATURITY_DATE");
						
					/*	if(null!=issueDate && !("".equals(issueDate))){
						 issueDateStr=	sdf.format(issueDate);
						} 
						if(null!=maturityDate && !("".equals(maturityDate))){
							maturityDateStr=	sdf.format(maturityDate);
							} */
						obFdFile.setDepositNumber(rs.getString("DEPOSIT_REFERENCE_NUMBER"));
						//obFdFile.setDateOfDeposit(issueDateStr);
						
						//Uma Khot:Fd Start date issue, 2012 is shown as 2020
						obFdFile.setDateOfDeposit(issueDate);
						obFdFile.setDateOfMaturity(maturityDate);
						
						//obFdFile.setDateOfMaturity(maturityDateStr);
						obFdFile.setInterestRate(rs.getDouble("DEPOSIT_INTEREST_RATE"));
						obFdFile.setStatus(rs.getString("STATUS"));
						obFdFile.setReason(rs.getString("REASON"));
						obFdFileList.add(obFdFile);
					}
					DefaultLogger.debug(this, "Completed getTotalUploadedFileList.");
					}
			} catch (DBConnectionException e) {
				isDBConnectionException = Boolean.TRUE;
				e.printStackTrace();
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in getTotalUploadedFileList:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in getTotalUploadedFileList:"+e.getMessage());
			}
			finalize(dbUtil,rs);
			
			//Retry using JDBC for Batch (Websphere env)
			if (isDBConnectionException) {
				String query = "select DEPOSIT_REFERENCE_NUMBER,ISSUE_DATE ,DEPOSIT_MATURITY_DATE ,DEPOSIT_INTEREST_RATE , STATUS, REASON from "+tableName+" where file_id="+fileId+" AND UPLOAD_STATUS='Y'" ;

				try {
					
					obFdFileList = (List) getJdbcTemplate().query(query, new ArrayList<Object>().toArray(),
							new ResultSetExtractor() {
								public List extractData(ResultSet rs) throws SQLException, DataAccessException {

									List obFdFileListTemp =new ArrayList<OBFdFile>();

									while (rs.next()) {

										OBFdFile obFdFile=new OBFdFile();
										Date issueDate=rs.getDate("ISSUE_DATE");
										Date maturityDate=rs.getDate("DEPOSIT_MATURITY_DATE");
										obFdFile.setDepositNumber(rs.getString("DEPOSIT_REFERENCE_NUMBER"));
										obFdFile.setDateOfDeposit(issueDate);
										obFdFile.setDateOfMaturity(maturityDate);
										obFdFile.setInterestRate(rs.getDouble("DEPOSIT_INTEREST_RATE"));
										obFdFile.setStatus(rs.getString("STATUS"));
										obFdFile.setReason(rs.getString("REASON"));
										obFdFileListTemp.add(obFdFile);
									}
									return obFdFileListTemp;
								}
							});
					
				} catch (Exception e) {
					DefaultLogger.info(this, "Exception caught in getTotalUploadedFileList ::" + e.getMessage());
					e.printStackTrace();
				}
			}
		return obFdFileList;
	}
	//End:Uma Khot:21/01/2016 Added for FD Upload NEw Logic as FD Upload was taking time
	
	//Start:24-05-2016 Uma added to update FD status as active in FD upload process
	
	public void updateStageFdDetailsWithStatusActive(long fileId,String appDate){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		Boolean isDBConnectionException = Boolean.FALSE;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL(" MERGE INTO (select * from CMS_STAGE_CASH_DEPOSIT where upload_status='N') dp "+
" USING (select distinct DEPOSIT_REFERENCE_NUMBER,ISSUE_DATE,DEPOSIT_MATURITY_DATE,DEPOSIT_INTEREST_RATE from  STAGE_FD_FILE_UPLOAD where file_id= "+ fileId+") fl ON (fl.DEPOSIT_REFERENCE_NUMBER = dp.DEPOSIT_REFERENCE_NUMBER and fl.DEPOSIT_MATURITY_DATE >= '"+
appDate+"') "+
" WHEN MATCHED THEN UPDATE  SET dp.upload_status = 'Y', dp.ISSUE_DATE=fl.ISSUE_DATE , "+ 
" dp.DEPOSIT_MATURITY_DATE =fl.DEPOSIT_MATURITY_DATE, dp.DEPOSIT_INTEREST_RATE =fl.DEPOSIT_INTEREST_RATE, dp.Active ='active'");
					int noOfRecords=dbUtil.executeUpdate();
					DefaultLogger.debug(this, "Updated "+noOfRecords +" Records dateOfMaturity, dateOfIssue, interestRate,  uploadStatus and active status");		
			} catch (DBConnectionException e) {
				isDBConnectionException = Boolean.TRUE;
				e.printStackTrace();		
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateStageFdDetailsWithStatusActive:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateStageFdDetailsWithStatusActive:"+e.getMessage());
			}
			finalize(dbUtil,rs);
			
			//Retry using JDBC for Batch (Websphere env)
			if (isDBConnectionException) {
				String query = " MERGE INTO (select * from CMS_STAGE_CASH_DEPOSIT where upload_status='N') dp "+
						" USING (select distinct DEPOSIT_REFERENCE_NUMBER,ISSUE_DATE,DEPOSIT_MATURITY_DATE,DEPOSIT_INTEREST_RATE from  STAGE_FD_FILE_UPLOAD where file_id= "+ fileId+") fl ON (fl.DEPOSIT_REFERENCE_NUMBER = dp.DEPOSIT_REFERENCE_NUMBER and fl.DEPOSIT_MATURITY_DATE >= '"+
						appDate+"') "+
						" WHEN MATCHED THEN UPDATE  SET dp.upload_status = 'Y', dp.ISSUE_DATE=fl.ISSUE_DATE , "+ 
						" dp.DEPOSIT_MATURITY_DATE =fl.DEPOSIT_MATURITY_DATE, dp.DEPOSIT_INTEREST_RATE =fl.DEPOSIT_INTEREST_RATE, dp.Active ='active'";

				try {
					
					int noOfRecordsUpdated = getJdbcTemplate().update(query);
					DefaultLogger.debug(this, "Successfully Updated "+noOfRecordsUpdated +" Records dateOfMaturity, dateOfIssue, interestRate,  uploadStatus and active status");
					
				} catch (Exception e) {
					DefaultLogger.info(this, "Exception caught in updateStageFdDetailsWithStatusActive ::" + e.getMessage());
					e.printStackTrace();
				}
			}
	}
	
	public void updateActualFdDetailsWithStatusActive(long fileId,String appDate){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		Boolean isDBConnectionException = Boolean.FALSE;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("MERGE INTO (select * from CMS_CASH_DEPOSIT where upload_status='N') dp USING "+
" (select distinct  DEPOSIT_REFERENCE_NUMBER,ISSUE_DATE,DEPOSIT_MATURITY_DATE,DEPOSIT_INTEREST_RATE from  CMS_FD_FILE_UPLOAD where file_id="+fileId+
" and status='PASS' ) fl ON (fl.DEPOSIT_REFERENCE_NUMBER = dp.DEPOSIT_REFERENCE_NUMBER  and fl.DEPOSIT_MATURITY_DATE >= '"+appDate+"') WHEN MATCHED THEN UPDATE SET dp.upload_status = 'Y',dp.ISSUE_DATE=fl.ISSUE_DATE , "+
" dp.DEPOSIT_MATURITY_DATE =fl.DEPOSIT_MATURITY_DATE,dp.DEPOSIT_INTEREST_RATE =fl.DEPOSIT_INTEREST_RATE, dp.Active ='active'");
			int noOfRecords=dbUtil.executeUpdate();
					
					DefaultLogger.debug(this, "Updated Actual CASH DEPOSIT Table with active status: "+noOfRecords);		
			} catch (DBConnectionException e) {
				isDBConnectionException = Boolean.TRUE;
				e.printStackTrace();	
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateActualFdDetailsWithStatusActive:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateActualFdDetailsWithStatusActive:"+e.getMessage());
			}
			finalize(dbUtil,rs);
			
			//Retry using JDBC for Batch (Websphere env)
			if (isDBConnectionException) {
				String query = "MERGE INTO (select * from CMS_CASH_DEPOSIT where upload_status='N') dp USING "+
						" (select distinct  DEPOSIT_REFERENCE_NUMBER,ISSUE_DATE,DEPOSIT_MATURITY_DATE,DEPOSIT_INTEREST_RATE from  CMS_FD_FILE_UPLOAD where file_id="+fileId+
						" and status='PASS' ) fl ON (fl.DEPOSIT_REFERENCE_NUMBER = dp.DEPOSIT_REFERENCE_NUMBER  and fl.DEPOSIT_MATURITY_DATE >= '"+appDate+"') WHEN MATCHED THEN UPDATE SET dp.upload_status = 'Y',dp.ISSUE_DATE=fl.ISSUE_DATE , "+
						" dp.DEPOSIT_MATURITY_DATE =fl.DEPOSIT_MATURITY_DATE,dp.DEPOSIT_INTEREST_RATE =fl.DEPOSIT_INTEREST_RATE, dp.Active ='active'";

				try {
					
					int noOfRecordsUpdated = getJdbcTemplate().update(query);
					DefaultLogger.debug(this, "Successfully Updated "+noOfRecordsUpdated +" records in Actual CASH DEPOSIT Table with active status");
					
				} catch (Exception e) {
					DefaultLogger.info(this, "Exception caught in updateActualFdDetailsWithStatusActive ::" + e.getMessage());
					e.printStackTrace();
				}
			}
	}
	//End:24-05-2016 Uma added to update FD status as active in FD upload process
	
	// Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD || Starts
	public List<OBBaselUploadFile> reportList() {
		List<OBBaselUploadFile> reportList = new ArrayList<OBBaselUploadFile>();
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
				dbUtil=new DBUtil();
				dbUtil.setSQL("select * from CMS_UPDATE_BASEL_REPORT WHERE STATUS in ('PASS','FAIL')");
				rs = dbUtil.executeQuery();	
				if(rs!=null)
				{
					while(rs.next())
					{
						OBBaselUploadFile BaselUploadFileObj = new OBBaselUploadFile();
						BaselUploadFileObj.setSerialNo(rs.getString("SERIAL_NO"));
						BaselUploadFileObj.setLine(rs.getString("LINE_NO"));
						BaselUploadFileObj.setCustomer(rs.getString("CUSTOMER_ID"));
						BaselUploadFileObj.setStatus(rs.getString("STATUS"));
						BaselUploadFileObj.setReason(rs.getString("REASON"));
						reportList.add(BaselUploadFileObj);
					}
					 
				}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return reportList;
	}
	
	// Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD || Ends
	
	public HashMap insertPartyCamfile( ArrayList result,UploadPartyCamFileCmd cmd,String fileName, ICommonUser user,Date date,ConcurrentMap<String, String> dataFromCacheView ,ConcurrentMap<String, String> dataFromUpdLineFacilityMV) {

		List totalUploadedList= new ArrayList();
		List errorList= new ArrayList();
		Timestamp st = null;
		String errMsg="";
		String tempData="";
		String strArrTemp[]=new String[3];
		List selectCustDetails=new ArrayList();
		String partyStatus="";
		HashMap retMap = new HashMap();
		DefaultLogger.debug(this, "##########3###########In FileUploadJdbcImpl insertUploadPartyfile ##### line no 220#######:: ");
		try {
			if(result!=null&& result.size() != 0)
			{
				for (int index = 0; index < result.size(); index++) {
					HashMap eachDataMap = (HashMap) result.get(index);
					OBUbsFile obj=new OBUbsFile();
					obj.setCurrency((String) eachDataMap.get("CURRENCY_CODE"));
					if(null!=eachDataMap.get("CUSTOMER_ID")&& !"".equalsIgnoreCase(eachDataMap.get("CUSTOMER_ID").toString()) )
					{
						obj.setCustomer(eachDataMap.get("CUSTOMER_ID").toString());
					}
					if(null!=eachDataMap.get("LINE_NO")&& !"".equalsIgnoreCase(eachDataMap.get("LINE_NO").toString()) )
					{
						obj.setLine(eachDataMap.get("LINE_NO").toString());
					}
					
					if(null!=eachDataMap.get("LIMIT_AMOUNT")&& !"".equalsIgnoreCase(eachDataMap.get("LIMIT_AMOUNT").toString()) )
					{
						obj.setLimit(new Double(Double.parseDouble(eachDataMap.get("LIMIT_AMOUNT").toString())));
					}
					
					if(null!=eachDataMap.get("SR_NO")&& !"".equalsIgnoreCase(eachDataMap.get("SR_NO").toString()) )
					{
						obj.setSerialNo(eachDataMap.get("SR_NO").toString());
					}
					
					if(null!=eachDataMap.get("UTILIZATION_AMOUNT")&& !"".equalsIgnoreCase(eachDataMap.get("UTILIZATION_AMOUNT").toString()) )
					{
						obj.setUtilize(new Double(Double.parseDouble(eachDataMap.get("UTILIZATION_AMOUNT").toString())));
						
					}
					obj.setUploadStatus("Y");
					OBCommonFile commonObj = obj;
//					partyStatus=getPartyStatus(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()),"UBS-LIMITS");
					// Commented and added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION |Starts
					//partyStatus=getPartyStatusForUBS(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()));
					partyStatus = getPartyStatusForUBSUpload(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()),dataFromCacheView,"UBS-LIMITS");
					// Commented and added By Dayananda Laishram on 26/02/2015 || UBS_UPLOAD_OPTIMIZATION |Ends
					if(!partyStatus.equals("")&& "ACTIVE".equals(partyStatus)){
						commonObj=updateLmtUtilAmtStage(obj,"PARTYCAM-LIMITS",dataFromUpdLineFacilityMV);
					}else if(!partyStatus.equals("")&& "INACTIVE".equals(partyStatus)){
						errMsg="Party Is Inactive In CLIMS.";
						commonObj.setReason(errMsg);
						commonObj.setStatus("FAIL");
					}else if(partyStatus.equals("")){
						commonObj=updateLmtUtilAmtStage(obj,"PARTYCAM-LIMITS",dataFromUpdLineFacilityMV);
						errMsg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+obj.getCustomer()+","+obj.getLine()+","+obj.getSerialNo()+","+obj.getCurrency()+") Available in UBS not in CLIMS.";
					}
					totalUploadedList.add(commonObj);
				}
			}
			
			//To Do :::: Below functionality has been implemented for UBS-LIMITS file upload only. This needs to be implemented for Other Upload files. 
			//Also for inserting data to actual tables.
			int batchSize = 200;
			for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
				List<OBCommonFile> batchList = totalUploadedList.subList(j, j + batchSize > totalUploadedList.size() ? totalUploadedList.size() : j + batchSize);
				updateXrefStageAmount(batchList,"PARTYCAM-LIMITS");
			}
			
			st = new Timestamp(System.currentTimeMillis());
			selectCustDetails=selectCustDetails("PartyCamFile");
			for(int i=0;i<selectCustDetails.size();i++)
			{				
				tempData=selectCustDetails.get(i).toString();
				strArrTemp=tempData.split(ICMSConstant.FILEUPLOAD_SEPERATOR);
				errMsg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+selectCustDetails.get(i)+")Available in CLIMS not in UBS.";
				OBPartyCamFile obPartyCam=new OBPartyCamFile();
				//obUbs.setCurrency("");					
				obPartyCam.setCustomer(strArrTemp[0]);
				obPartyCam.setLine(strArrTemp[1]);
				obPartyCam.setSerialNo(strArrTemp[2]);
				//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				obPartyCam.setCurrency(strArrTemp[3].trim());
				//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				obPartyCam.setStatus("FAIL");
				obPartyCam.setReason(errMsg);
				obPartyCam.setUploadStatus("N");
				errorList.add(obPartyCam);
			}
		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.getMessage());
			throw new IncompleteBatchJobException(
			"Unable to update retrived record from CSV file");
		}
		getJdbcTemplate().update(updateStatusActualPartyCam);
		retMap.put("totalUploadedList", totalUploadedList);
		retMap.put("errorList", errorList);
		return retMap;
	}
	
	public HashMap insertPartyCamfileActual( ArrayList result,CheckerApprovePartyCamFileUploadCmd cmd,String fileName,ICommonUser user,Date date,IFileUploadTrxValue trxValue,ConcurrentMap<String, String> dataFromUpdLineFacilityActual) {

		List totalUploadedList= new ArrayList();
		List errorList= new ArrayList();
		HashMap retMap = new HashMap();
		String upload_status="";
		Timestamp st = null;
		IPartyCamErrorLog objPartyCamError=new OBPartyCamErrorLog();
		String errMsg="";
		String tempData="";
		String strArrTemp[]=new String[3];
		List selectCustDetails=new ArrayList();
		String makerId = trxValue.getStagingfileUpload().getUploadBy();
		Date makerDate = trxValue.getStagingfileUpload().getUploadTime();
		try {
			if(result!=null&& result.size() != 0)
			{
				for (int index = 0; index < result.size(); index++) {
					OBPartyCamFile obj = (OBPartyCamFile) result.get(index);
					if(obj.getUploadStatus()!=null && obj.getUploadStatus().equals("Y") ){
						OBCommonFile object = obj;
						object.setMakerId(makerId);
						object.setMakerDate(makerDate);
						object.setCheckerId(user.getLoginID());
						object.setCheckerDate(date);
						//isUpdated=updateLmtUtilAmtActual(limit_Amt,util_Amt, cur_Code,upload_status,makerId,makerDate,user.getLoginID(),date,cust_id,line_No,sr_No,"UBS-LIMITS" );
						object=updateLmtUtilAmtActual(obj,"PARTYCAM-LIMITS",dataFromUpdLineFacilityActual );
						totalUploadedList.add(object);
						
					}
				}
			}
			
			int batchSize = 200;
			for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
				List<OBCommonFile> batchList = totalUploadedList.subList(j, j + batchSize > totalUploadedList.size() ? totalUploadedList.size() : j + batchSize);
				updateXrefActualAmount(batchList,"PARTYCAM-LIMITS");
			}
			
			st = new Timestamp(System.currentTimeMillis());
			date =new Date(st.getTime());
			selectCustDetails=selectCustDetailsActual("PartyCamFile");
			for(int i=0;i<selectCustDetails.size();i++)
			{				
				tempData=selectCustDetails.get(i).toString();
				strArrTemp=tempData.split(ICMSConstant.FILEUPLOAD_SEPERATOR);
				errMsg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+selectCustDetails.get(i)+")Available in CLIMS not in UBS.";
				IPartyCamErrDetLog obPartyCamErrDet=new OBPartyCamErrDetLog();
				OBPartyCamFile obPartyCam=new OBPartyCamFile();
//				obUbs.setCurrency("");					
				obPartyCam.setCustomer(strArrTemp[0]);
				obPartyCam.setLine(strArrTemp[1]);
				obPartyCam.setSerialNo(strArrTemp[2]);
				//Start:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				obPartyCam.setCurrency(strArrTemp[3].trim());
				//End:Uma :Adding Currency details in log file for UBS, HONGKONG, BAHRAIN File Upload.
				obPartyCam.setStatus("FAIL");
				obPartyCam.setUploadStatus("N");
				obPartyCam.setReason(errMsg);
				errorList.add(obPartyCam);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
			"Unable to update/insert dad retrived form CSV file");

		}
		getJdbcTemplate().update(updateStatusActualPartyCam);
		retMap.put("totalUploadedList", totalUploadedList);
		retMap.put("errorList", errorList);
		return retMap;
	}
	
	//Uma Khot:Cam upload and Dp field calculation CR
	public Set<String> cacheDataFromPartyCamUploadMV(String materializedViewName)
	{
		Set<String> dataFromCacheView = new HashSet<String>();
			DBUtil dbUtil = null;
			ResultSet rs=null;
			StringBuilder key = null;
			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL("SELECT * FROM "+materializedViewName);
				rs = dbUtil.executeQuery();	
				if(rs!=null)
				{
					while(rs.next())
					{
						dataFromCacheView.add(rs.getString("LSP_LE_ID"));
					}
				}
			} catch (DBConnectionException e) {
				e.printStackTrace();
			} catch (NoSQLStatementException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finalize(dbUtil,rs);
		
		 return dataFromCacheView;
	}

	public SearchResult getAllAcknowledgmentFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=STAGE_CERSAI_ACK_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new AcknowledgmentFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving Cersai Acknowledgment File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public SearchResult getAllActualAcknowledgmentFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=ACTUAL_CERSAI_ACK_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new AcknowledgmentFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving Cersai Acknowledgment  File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public void updateUploadStatusToNull(String tableName){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("update "+tableName+" set upload_status = null");
					int noOfRecords=dbUtil.executeUpdate();
					DefaultLogger.debug(this, "Updated "+noOfRecords +" Records upload_status to 'NULL'");		
					
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateUploadStatusToNull:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateUploadStatusToNull:"+e.getMessage());
			}
			finalize(dbUtil,rs);
			
		}
	
	public void spUpdatePartyCamUpload(String fileId,String table){
		try {
        	if("STAGE".equals(table)){
         getJdbcTemplate().execute("{call " + getSpUpdatePartyCamUploadStage() + "("+fileId+")}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        	}else if ("ACTUAL".equals(table)){
        		getJdbcTemplate().execute("{call " + getSpUpdatePartyCamUploadActual() + "("+fileId+")}",  new CallableStatementCallback() {
                    public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                    	cs.executeUpdate();
                        return null;
                    }
                });
        	}
        }
        catch (Exception ex) {
        	DefaultLogger.debug(this, " exception in spUpdatePartyCamUpload: "+ex.getMessage());
        	ex.printStackTrace();
        }
    }
	
	public void spBaselMonthlyNpaTrackNew() {
		try {
			System.out.println("Called spBaselMonthlyNpaTrackNew().....");
			getJdbcTemplate().execute("{call " + getSpBaselMonthlyNpaTrackNew() + "}",
					new CallableStatementCallback() {
						public Object doInCallableStatement(CallableStatement cs)
								throws SQLException, DataAccessException {
							cs.executeUpdate();
							return null;
						}
					});
		} catch (Exception ex) {
			DefaultLogger.debug(this, " exception in spBaselMonthlyNpaTrackNew: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	

	public String updatePartyfundStgtoMain(long fileId,String partyId,long id){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String nonFundAmt = null;
		try{
			DefaultLogger.debug(this, "fileId is "+fileId +" in updatePartyfundStgtoMain method'");		
			DefaultLogger.debug(this, "partyId is "+partyId +" in updatePartyfundStgtoMain method'");	
					dbUtil=new DBUtil();
					dbUtil.setSQL("select total_non_funded_limit from STAGE_PARTYCAM_FILE_UPLOAD "
							+ " where party_id='"+partyId+"' and  id = '"+id+"' and file_id =(select distinct staging_reference_id from transaction where transaction_type = 'PARTYCAM_UPLOAD' \r\n" + 
									" and reference_id ='"+fileId+"')");
					rs = dbUtil.executeQuery();
					if(null!=rs)
					{
						while(rs.next())
						{
							nonFundAmt = rs.getString("total_non_funded_limit");
						}
					}
					
					
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateUploadStatusToNull:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in updateUploadStatusToNull:"+e.getMessage());
			}
			finalize(dbUtil,rs);
    return nonFundAmt;
	}

	public int getPartyCamUplodCount() throws FileUploadException {
		List resultList = null;
		int count=0;
		try {
			String SQL=CHECK_PARTYCAM_UPLOAD_TRX+" AND (status LIKE 'PENDING%' OR STATUS LIKE 'REJECTED')";
			count=getJdbcTemplate().queryForInt(SQL);			

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- Checkling Party Cam File Transaction in FileUploadJdbcImpl");
		}

		return count;
	}
	
	
	public int getBulkUDFUploadCount() throws FileUploadException{
		List resultList= null;
		int count=0;
		try {
			String SQL= CHECK_BULKUDF_UPLOAD_TRX+" AND (status LIKE 'PENDING%' OR STATUS LIKE 'REJECTED')";
			count=getJdbcTemplate().queryForInt(SQL);	
		}
		catch(Exception e) {
			throw new FileUploadException("ERROR-- Checking Bulk UDF Upload File Transaction in FileUploadJdbcImpl");
		}
		return count;
	}
	
	public int getPartyIDCount(String PartyID) throws FileUploadException
	{		
		int count=0;
		try {
			String SQL= CHECK_BULK_UDF_PARTY_ID_COUNT+PartyID+"'";
			count=getJdbcTemplate().queryForInt(SQL);	
		}
		catch(Exception e) {
			throw new FileUploadException("ERROR-- Checking ount of PartyID Bulk UDF Upload File Transaction in FileUploadJdbcImpl");
		}
		return count;
	}
	
	public String getPartyIdFromMain(String PartyId) throws FileUploadException
	{
		List party;
		String partyId;
		try {
			String SQL= GET_PARTY_ID_FROM_MAIN+PartyId+"'";
			party=getJdbcTemplate().queryForList(SQL);
			 								
					Map  map = (Map) party.get(0);
					 partyId = map.get("CMS_LE_MAIN_PROFILE_ID").toString();		
		}
		catch(Exception e)
		{
			throw new FileUploadException("ERROR-- Getting key value for Party No in Party Id from main in FileUploadJdbcImpl");
		}
		return partyId;
}
	
	public String getCamIdFromMain(String camNo)
	{

		List camRef;
		String cam;
		try {
			String SQL= GET_CAM_NO_FROM_MAIN+camNo+"'";
			camRef=getJdbcTemplate().queryForList(SQL);
			 								
					Map  map = (Map) camRef.get(0);
					 cam = map.get("CMS_LSP_LMT_PROFILE_ID").toString();		
		}
		catch(Exception e)
		{
			throw new FileUploadException("ERROR-- Getting key value for CAM No in Cam Id from main in FileUploadJdbcImpl");
		}
		return cam;
		
	}
	
	public int getCamNoCount(String camNo)
	{		
	int count=0;
	try {
		String SQL= CHECK_BULK_UDF_CAM_NO_COUNT+camNo+"'";
		count=getJdbcTemplate().queryForInt(SQL);	
	}
	catch(Exception e) {
		throw new FileUploadException("ERROR-- Checking ount of CamNo Bulk UDF Upload File Transaction in FileUploadJdbcImpl");
	}
	return count;
		
	}
	
	public int getLimitCount(String systemId, String LineNo, String serailNo, String liabBranch)
	{
		int count=0;
		try {
			String SQL="Select count(1) from SCI_LSP_SYS_XREF where FACILITY_SYSTEM_ID='"+systemId+"' and LINE_NO='"+LineNo+"' AND SERIAL_NO='"+serailNo+"' AND LIAB_BRANCH='"+liabBranch+"'";
		count=getJdbcTemplate().queryForInt(SQL);	
		}
		catch(Exception e) {
			throw new FileUploadException("ERROR-- Checking Count of CamNo Bulk UDF Upload File Transaction in FileUploadJdbcImpl");
		}		
		return count;		
	}
	
	public int isValidUDFSequence(String TypeofUDF, String sequence, String fieldName)
	{
		int seq_count=0;
		int field_count=0;
		int count=0;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String flag="abc";
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT count(1) SEQ_COUNT FROM CMS_UDF where MODULENAME='"+TypeofUDF+"' AND SEQUENCE='"+sequence+"'");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					seq_count = rs.getInt("SEQ_COUNT");
				}
			}
						
			if(seq_count>0)
			{
				dbUtil.setSQL("SELECT count(1) FIELD_COUNT FROM CMS_UDF where MODULENAME='"+TypeofUDF+"' AND SEQUENCE='"+sequence+"' AND FIELDNAME='"+fieldName+"'");
				rs = dbUtil.executeQuery();
				if(null!=rs)
				{
					while(rs.next())
					{
						field_count = rs.getInt("FIELD_COUNT");
					}
				}
				
			}
		}
		catch(Exception e) {
			DefaultLogger.debug(this, " exception in insertOrUpdateBulkUDFStageFile: "+e.getMessage());
        	e.printStackTrace();
		}		
		if(field_count>0)
		{
			count=1;// correct data
		}
		else if(field_count==0 && seq_count>0)
		{
			count =2;//fieldname not correct but sequence present
		}
		else if(seq_count==0)
		{
			count= 3;//sequence incorrect
		}
		finalize(dbUtil,rs);
		return count;		
	}
	
	
	
	
	@Override
	public void insertOrUpdateBulkUDFStageFile(List<OBTempBulkUDFFileUpload> batchList) {
		// TODO Auto-generated method stub
		final List<OBTempBulkUDFFileUpload> objectList = batchList;
		final int batchSize = batchList.size();
		final List<OBBulkUDFFile> objStage  =new ArrayList<OBBulkUDFFile>() ;
		try {
			 for(OBTempBulkUDFFileUpload obj :batchList )
			 {
				 
				 OBBulkUDFFile file=new OBBulkUDFFile();
				 //check for valid flag
				 //data set to file object from temp obj
				 file.setPartyID(obj.getPartyID());
				 
				 objStage.add(file);
			 }
			
		}
		catch(Exception e)
		{
			DefaultLogger.debug(this, " exception in insertOrUpdateBulkUDFStageFile: "+e.getMessage());
        	e.printStackTrace();
		}
	}

	public Set<String> getRiskGrade(){
		Set<String> riskGrade=new HashSet<String>();
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("select entry_name from common_code_category_entry where category_code='RISK_GRADE' and active_status='1'");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					riskGrade.add(rs.getString("entry_name"));
				}
			}
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getRiskGrade: "+e.getMessage());
        	e.printStackTrace();
		}
		return riskGrade;
		
	}
	
	
	public String getIsLeiValidated(String partyId)
	{
		List party;
		String isLeiValidated = "N";
		try {
			String SQL= "select IS_LEI_VALIDATED from SCI_LE_SUB_PROFILE where LSP_LE_ID='"+partyId+"'";
			party=getJdbcTemplate().queryForList(SQL);			 								
				Map map = (Map) party.get(0);
				isLeiValidated = map.get("IS_LEI_VALIDATED").toString();	
				if(isLeiValidated == null || isLeiValidated.isEmpty()) {
					isLeiValidated = "N";
				}	
		}
		catch(Exception e)
		{
			DefaultLogger.debug(this, " exception in getIsLeiValidated: "+e.getMessage());
        	e.printStackTrace();
		}
		return isLeiValidated;
	}
	
	public String getLeiCode(String partyId)
	{
		List party;
		String leiCode = "";
		try {
			String SQL= "SELECT LEI_CODE FROM SCI_LE_SUB_PROFILE WHERE LSP_LE_ID='"+partyId+"'";
			party=getJdbcTemplate().queryForList(SQL);			 								
			Map map = (Map) party.get(0);
			leiCode = map.get("LEI_CODE").toString().trim();	
			if(leiCode == null || leiCode.isEmpty()) {
				leiCode = "";
			}	
		}
		catch(Exception e)
		{
			DefaultLogger.debug(this, " exception in getIsLeiValidated: "+e.getMessage());
        	e.printStackTrace();
		}
		return leiCode;
	}
	/*public Set<String> getAlreadyDownloadedFileNamesByBatch(String fileType, Date batchdate){
		String sql =  "SELECT distinct FILENAME from CMS_FILE_UPLOAD where FILETYPE = ? AND Trunc(UPLOADTIME) = trunc(TO_DATE(?,'DD-MM-YYYY')) ";
		
		DefaultLogger.debug(this, "::: Inside FileUploadJdbcImpl : getAlreadyDownloadedFileNamesByBatch :: param:"+fileType+"\t"+batchdate);
		System.out.println("::: Inside FileUploadJdbcImpl : getAlreadyDownloadedFileNamesByBatch :: param:"+fileType+"\t"+batchdate);

		List<Object> param = new ArrayList<Object>();
		param.add(fileType);
		if(null != batchdate) {
			param.add(DateUtil.formatDate("dd-MM-yyyy",batchdate));
		}
		else {
			param.add(batchdate);
		}
		
		try{
			return (Set<String>) getJdbcTemplate().query(sql, param.toArray(), new ResultSetExtractor() {
				public Set<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
					
					Set<String> downloadedFilesSet = new HashSet<String>();
					while(rs.next()) {
						String fileName = rs.getString("FILENAME");
						downloadedFilesSet.add(fileName);
					}
					DefaultLogger.debug(this, "::: getAlreadyDownloadedFileNamesByBatch :: downloadedFilesSet is :: "+downloadedFilesSet);
					System.out.println("::: getAlreadyDownloadedFileNamesByBatch :: downloadedFilesSet is :: "+downloadedFilesSet);
					return downloadedFilesSet;
				}
			});
		}
		catch (Exception e) {
			DefaultLogger.debug(this, " exception in getAlreadyDownloadedFileNamesByBatch: "+e.getMessage());
			System.out.println("exception in getAlreadyDownloadedFileNamesByBatch: "+e.getMessage());
        	e.printStackTrace();
			return null;
		}
	}*/
	
	
	public Set<String> getAlreadyDownloadedFileNamesByBatchForHRMS(String fileType, Date batchdate){
		String sql =  "SELECT distinct FILENAME from CMS_FILE_UPLOAD where FILETYPE = ?";
		
		List<Object> param = new ArrayList<Object>();
		param.add(fileType);
		
		
		try{
			return (Set<String>) getJdbcTemplate().query(sql, param.toArray(), new ResultSetExtractor() {
				public Set<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
					
					Set<String> downloadedFilesSet = new HashSet<String>();
					while(rs.next()) {
						String fileName = rs.getString("FILENAME");
						downloadedFilesSet.add(fileName);
					}
					return downloadedFilesSet;
				}
			});
		}
		catch (Exception e) {
			DefaultLogger.debug(this, " exception in getAlreadyDownloadedFileNamesByBatch: "+e.getMessage());
			System.out.println("exception in getAlreadyDownloadedFileNamesByBatch: "+e.getMessage());
        	e.printStackTrace();
			return null;
		}
	}
	
	
	public Set<String> getRatingType(){
		Set<String> ratingType=new HashSet<String>();
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("select entry_name from common_code_category_entry where category_code='SEC_RATING_TYPE' and active_status='1'");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					ratingType.add(rs.getString("entry_name"));
				}
			}
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getRatingType: "+e.getMessage());
        	e.printStackTrace();
		}
		return ratingType;
		
	}
	
	
	
	public Set<String> getAlreadyDownloadedExchangeReportFileNamesByBatch(Date batchdate){
		String sql =  "SELECT distinct FILE_NAME from CMS_EXCHANGE_RATE_REPORT where Trunc(UPLOAD_DATE) = trunc(TO_DATE(?,'DD-MM-YYYY')) ";
		
		DefaultLogger.debug(this, "::: Inside FileUploadJdbcImpl : getAlreadyDownloadedFileNamesByBatch :: param:"+batchdate);
		System.out.println("::: Inside FileUploadJdbcImpl : getAlreadyDownloadedFileNamesByBatch :: param: "+batchdate);

		List<Object> param = new ArrayList<Object>();
		if(null != batchdate) {
			param.add(DateUtil.formatDate("dd-MM-yyyy",batchdate));
		}
		else {
			param.add(batchdate);
		}
		
		try{
			return (Set<String>) getJdbcTemplate().query(sql, param.toArray(), new ResultSetExtractor() {
				public Set<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
					
					Set<String> downloadedFilesSet = new HashSet<String>();
					while(rs.next()) {
						String fileName = rs.getString("FILE_NAME");
						downloadedFilesSet.add(fileName);
					}
					DefaultLogger.debug(this, "::: getAlreadyDownloadedFileNamesByBatch :: downloadedFilesSet is :: "+downloadedFilesSet);
					System.out.println("::: getAlreadyDownloadedFileNamesByBatch :: downloadedFilesSet is :: "+downloadedFilesSet);
					return downloadedFilesSet;
				}
			});
		}
		catch (Exception e) {
			DefaultLogger.debug(this, " exception in getAlreadyDownloadedFileNamesByBatch: "+e.getMessage());
			System.out.println("exception in getAlreadyDownloadedFileNamesByBatch: "+e.getMessage());
        	e.printStackTrace();
			return null;
		}
	}
	
	//RAM RAting CR (Deferral Extension) By Santosh
	public HashMap insertDeferralExtnsionFile( ArrayList result,Date applicationDate,String fileName) {
		DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl result.size():"+result.size());
		List totalUploadedList= new ArrayList();
		List errorList= new ArrayList();
		Timestamp st = null;
		String tempData="";
		String strArrTemp[]=new String[3];
		List selectCustDetails=new ArrayList();
		String partyStatus="";
		HashMap retMap = new HashMap();
		//Date d = DateUtil.getDate();
		//d = DateUtil.clearTime(d);
		try {
			if(result!=null&& result.size() != 0)
			{
				List<ICheckListTrxValue> listcheckListTest = new ArrayList<ICheckListTrxValue>();
				for (int index = 0; index < result.size(); index++) {
					boolean isValidRecord=true;
					String errMsg="";
					HashMap eachDataMap = (HashMap) result.get(index);
					OBDeferralExtensionFileUpload obj=new OBDeferralExtensionFileUpload();
					if(null!=eachDataMap.get("MODULE")&& !"".equalsIgnoreCase
							(eachDataMap.get("MODULE").toString()) ){
						obj.setModule(eachDataMap.get("MODULE").toString());
					}
					if(null!=eachDataMap.get("PARTYID")&& !"".equalsIgnoreCase
							(eachDataMap.get("PARTYID").toString()) ){
						obj.setPartyID(eachDataMap.get("PARTYID").toString());
						//to set party name
						String partyName=getPartyName(eachDataMap.get("PARTYID").toString());
						obj.setPartyName(partyName);
						//to set segament
						String segment=getSegment(eachDataMap.get("PARTYID").toString());
						obj.setSegment(segment);
					}
					if(null!=eachDataMap.get("CHECKLISTID")&& !"".equalsIgnoreCase
							(eachDataMap.get("CHECKLISTID").toString()) ){
						obj.setChecklistID(eachDataMap.get("CHECKLISTID").toString());
						//to set checklist type
						String checklistType=getChecklistType(eachDataMap.get("CHECKLISTID").toString());
						obj.setChecklistType(checklistType);
					}
					
					if(null!=eachDataMap.get("DOCID")&& !"".equalsIgnoreCase
							(eachDataMap.get("DOCID").toString()) ){
						obj.setDocID(eachDataMap.get("DOCID").toString());
					}
					
					if(null!=eachDataMap.get("DISCREPANCYID")&& !"".equalsIgnoreCase
							(eachDataMap.get("DISCREPANCYID").toString()) ){
						obj.setDiscrepancyID(eachDataMap.get("DISCREPANCYID").toString());
					}
					
					if(null!=eachDataMap.get("LADCODE")&& !"".equalsIgnoreCase
							(eachDataMap.get("LADCODE").toString()) ){
						obj.setLadCode(eachDataMap.get("LADCODE").toString());
					}
					if(null!=eachDataMap.get("ACTION")&& !"".equalsIgnoreCase
							(eachDataMap.get("ACTION").toString()) ){
						obj.setAction(eachDataMap.get("ACTION").toString());
					}
					if(null!=eachDataMap.get("ORIGINALTARGETDATE")&& !"".equalsIgnoreCase
							(eachDataMap.get("ORIGINALTARGETDATE").toString()) ){
						Date originalTargetDate=new SimpleDateFormat("dd-MMM-yyyy").parse(eachDataMap.get("ORIGINALTARGETDATE").toString());
						java.sql.Date sDate = new java.sql.Date(originalTargetDate.getTime());
						obj.setOriginalTargetDate(sDate);
					}
					if(null!=eachDataMap.get("DATEDEFERRED")&& !"".equalsIgnoreCase
							(eachDataMap.get("DATEDEFERRED").toString()) ){
						Date dateDeferred=new SimpleDateFormat("dd-MMM-yyyy").parse(eachDataMap.get("DATEDEFERRED").toString());
						java.sql.Date sDate = new java.sql.Date(dateDeferred.getTime());
						obj.setDateDeferred(sDate);

						if((eachDataMap.get("DATEDEFERRED").toString() != null) 
								&& (eachDataMap.get("DATEDEFERRED").toString().length() > 0)){
							if(DateUtil.clearTime(applicationDate).before(DateUtil.clearTime(DateUtil.convertDate(eachDataMap.get("DATEDEFERRED").toString())))) {
								isValidRecord=false;
								errMsg=" Deferred Date cannot be a Future Date.";
								obj.setFailReason(errMsg);
								obj.setStatus("FAIL");
								//errorList.add(obj);
							}
						}						
						if(!"Discrepancy".equals(obj.getModule())) {
						if (DateUtil.clearTime(DateUtil.convertDate(eachDataMap.get("DATEDEFERRED").toString()))
								.before(DateUtil.clearTime(applicationDate))) {
							isValidRecord=false;
							errMsg=errMsg+" Defer Date should not be earlier than Current Date.";
							obj.setFailReason(errMsg);
							obj.setStatus("FAIL");
							//errorList.add(obj);
						}
						}
						if(!"Discrepancy".equals(obj.getModule())) {
							System.out.println("fileuploadjdbcimpl.java=>Discrepancy=>line 5491>"+obj.getModule());
							System.out.println("fileuploadjdbcimpl.java=>Discrepancy=>line 5492>"+eachDataMap.get("ORIGINALTARGETDATE"));
							System.out.println("fileuploadjdbcimpl.java=>Discrepancy=>line 5493>"+eachDataMap.get("DATEDEFERRED"));
						if(null!=eachDataMap.get("ORIGINALTARGETDATE") && !"".equalsIgnoreCase(eachDataMap.get("ORIGINALTARGETDATE").toString()) 
								&& (DateUtil.clearTime(DateUtil.convertDate(eachDataMap.get("DATEDEFERRED").toString()))).
								equals(DateUtil.clearTime(DateUtil.convertDate(eachDataMap.get("ORIGINALTARGETDATE").toString())))){
							isValidRecord=false;
							errMsg=errMsg+" Defer Date should not be same as Original Target Date.";
							obj.setFailReason(errMsg);
							obj.setStatus("FAIL");
							//errorList.add(obj);
						}}
					}else {
						isValidRecord=false;
						errMsg=errMsg+" Deferred Date is null. ";
						obj.setFailReason(errMsg);
						obj.setStatus("FAIL");
						//errorList.add(obj);
					}
					if(null!=eachDataMap.get("NEXTDUEDATE")&& !"".equalsIgnoreCase
							(eachDataMap.get("NEXTDUEDATE").toString())){
						Date nextDueDate=new SimpleDateFormat("dd-MMM-yyyy").parse(eachDataMap.get("NEXTDUEDATE").toString());
						java.sql.Date sDate = new java.sql.Date(nextDueDate.getTime());
						obj.setNextDueDate(sDate);
						
						if (null!=eachDataMap.get("DATEDEFERRED") && !"".equalsIgnoreCase(eachDataMap.get("DATEDEFERRED").toString()) 
								&& DateUtil.clearTime(DateUtil.convertDate(eachDataMap.get("NEXTDUEDATE").toString()))
								.before(DateUtil.clearTime(DateUtil.convertDate(eachDataMap.get("DATEDEFERRED").toString())))) {
							isValidRecord=false;
							errMsg=errMsg+" Next Due Date should not be earlier than Defere Date. ";
							obj.setFailReason(errMsg);
							obj.setStatus("FAIL");
							//errorList.add(obj);
						}
						if (null!=eachDataMap.get("ORIGINALTARGETDATE") && !"".equalsIgnoreCase(eachDataMap.get("ORIGINALTARGETDATE").toString()) 
								&& DateUtil.clearTime(DateUtil.convertDate(eachDataMap.get("NEXTDUEDATE").toString()))
								.before(DateUtil.clearTime(DateUtil.convertDate(eachDataMap.get("ORIGINALTARGETDATE").toString())))) {
							isValidRecord=false;
							errMsg=errMsg+" Next Due Date should not be earlier than Original Target Date. ";
							obj.setFailReason(errMsg);
							obj.setStatus("FAIL");
							//errorList.add(obj);
						}
					}else {
						isValidRecord=false;
						errMsg=errMsg+" Next Due Date is null. ";
						obj.setFailReason(errMsg);
						obj.setStatus("FAIL");
						//errorList.add(obj);
					}
					if(null!=eachDataMap.get("CREDITAPPROVER")&& !"".equalsIgnoreCase
							(eachDataMap.get("CREDITAPPROVER").toString()) ){
						obj.setCreditApprover(eachDataMap.get("CREDITAPPROVER").toString());
					}else {
						isValidRecord=false;
						errMsg=errMsg+" Credit Approver is null. ";
						obj.setFailReason(errMsg);
						obj.setStatus("FAIL");
						//errorList.add(obj);
					}
					if(null!=eachDataMap.get("REMARKS")&& !"".equalsIgnoreCase
							(eachDataMap.get("REMARKS").toString()) ){
						obj.setRemarks(eachDataMap.get("REMARKS").toString());
					}
					obj.setUploadStatus("Y");
					st = new Timestamp(System.currentTimeMillis());
					Date date =new Date(st.getTime());
					java.sql.Date sAppDate = new java.sql.Date(date.getTime());
					obj.setDateOfRequest(sAppDate);
					obj.setDateOfProcess(sAppDate);
					obj.setFileName(fileName);
					partyStatus=getPartyStatus(obj.getPartyID());
					DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl partyStatus:"+partyStatus);
					
					if(isValidRecord) {
					DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl isValidRecord:"+isValidRecord);
					if(null!= partyStatus && !partyStatus.equals("") && "ACTIVE".equals(partyStatus)){
						//Update "Discrepancy" module
						DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl obj.getModule()...."+obj.getModule());
						if("Discrepancy".equals(obj.getModule())) {
							if(null!=obj.getDiscrepancyID() && !"".equals(obj.getDiscrepancyID()) && 
									null!=getDiscrepencyStatus(obj.getDiscrepancyID()) &&	
									!"".equals(getDiscrepencyStatus(obj.getDiscrepancyID()))) {
								
								String status =getDiscrepencyStatus(obj.getDiscrepancyID());
								String transactionStatus =getDiscrepencyTransactionStatus(obj.getDiscrepancyID());
								if(!("CLOSED".equalsIgnoreCase(status)||"WAIVED".equalsIgnoreCase(status)) &&
										!("PENDING_WAIVE".equalsIgnoreCase(transactionStatus)
										||"PENDING_CLOSE".equalsIgnoreCase(transactionStatus)
										||"PENDING_DEFER".equalsIgnoreCase(transactionStatus)
										||"PENDING_UPDATE".equalsIgnoreCase(transactionStatus))){
									//update Discrepancy doc NEXT_DUE_DATE,DOC_REMARKS,DEFER_DATE,CREDIT_APPROVER,
									//DISCREPENCY_COUNTER,DEFERED_COUNTER,TOTAL_DEFERED_DAYS,ORG_DATE_DEFERED_DAYS,STATUS=PENDING_DEFER here
									try {
										IDiscrepencyProxyManager discrepencyProxy = (IDiscrepencyProxyManager)BeanHouse.get("discrepencyProxy");
										IDiscrepencyTrxValue trxValueIn = (IDiscrepencyTrxValue) discrepencyProxy.getDiscrepencyById(Long.parseLong(obj.getDiscrepancyID()));
										DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of executing Discrepancy module>>>>");
										if(null!=trxValueIn && !"".equals(trxValueIn)) {
											trxValueIn.setLoginId("System");
											IDiscrepencyTrxValue trxValueOut = new OBDiscrepencyTrxValue();
											OBTrxContext ctx = new OBTrxContext();
											IDiscrepencyDAO discrepencyDAO=(IDiscrepencyDAO)BeanHouse.get("discrepencyDAO");
									
											SearchResult searchResult= discrepencyDAO.listDiscrepencyFacility("actualDiscrepencyFacilityList", trxValueIn.getActualDiscrepency().getId());
											List disFacList = new ArrayList(searchResult.getResultList());
											if(disFacList !=null){
												IDiscrepencyFacilityList discrepencyFacilityList[]= new IDiscrepencyFacilityList[disFacList.size()];
												for(int i=0;i< disFacList.size();i++){
													discrepencyFacilityList[i]=(IDiscrepencyFacilityList) disFacList.get(i);
												}
												trxValueIn.getActualDiscrepency().setFacilityList(discrepencyFacilityList);
											}
									
											IDiscrepency discrepency = (IDiscrepency) trxValueIn.getActualDiscrepency();
											long count=trxValueIn.getActualDiscrepency().getCounter();
											count++;
											long deferCount=trxValueIn.getActualDiscrepency().getDeferedCounter();
											deferCount++;
									
											discrepency.setDeferedCounter(deferCount);
											discrepency.setCounter(count);
									
											Date creationDate=discrepency.getCreationDate();
											Date originalDate=discrepency.getOriginalTargetDate();
									
											//to set previous next due date
											if(null!=discrepency.getNextDueDate()) {
												DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
												String checkDate = df.format(discrepency.getNextDueDate());
												Date nextDueDate=new SimpleDateFormat("dd-MMM-yyyy").parse(checkDate);
												java.sql.Date sDate = new java.sql.Date(nextDueDate.getTime());
												obj.setPreviousNextDueDate(sDate);
											}
											//end
											discrepency.setNextDueDate(new java.util.Date(eachDataMap.get("NEXTDUEDATE").toString()));
											discrepency.setDeferDate(new java.util.Date(eachDataMap.get("DATEDEFERRED").toString()));
											discrepency.setOriginalDeferedDays(calculateDays(discrepency.getNextDueDate(),originalDate));
											discrepency.setTotalDeferedDays(calculateDays(discrepency.getNextDueDate(),creationDate));
											discrepency.setDocRemarks(obj.getRemarks());
											discrepency.setCreditApprover(obj.getCreditApprover());
											discrepency.setStatus("PENDING_DEFER");
									
											ctx=(OBTrxContext) trxValueIn.getTrxContext();
											//update stage
											trxValueOut=updateStageTable(ctx,trxValueIn,discrepency);
									
											//FOR MAIN TABLE ENTRY
											IDiscrepency discrepencyMain = (OBDiscrepency) trxValueOut.getStagingDiscrepency();
											discrepencyMain.setTransactionStatus("ACTIVE");
											SearchResult resultMain= discrepencyDAO.listDiscrepencyFacility("stagingDiscrepencyFacilityList", discrepencyMain.getId());
											Collection resultList = null;
											IDiscrepencyFacilityList[] discrepencyFacilityLists=new IDiscrepencyFacilityList[resultMain.getResultList().size()];
											if (result != null) {
												resultList = resultMain.getResultList();
											}
											if(resultList !=null){
												Iterator i= resultList.iterator();
													while(i.hasNext()){
														for(int j=0;j<resultList.size();j++){
															discrepencyFacilityLists[j]=(IDiscrepencyFacilityList)i.next();
														}
													}
											}
											discrepencyMain.setFacilityList(discrepencyFacilityLists);
											trxValueOut.setStagingDiscrepency(discrepencyMain);
											trxValueOut.setLoginId("System");
											//update main table
											IDiscrepencyTrxValue trxValueOutMain=updateMainTable(ctx,trxValueOut,discrepencyMain);
									
											if(trxValueOutMain.getActualDiscrepency().getFacilityList()!=null){
												IDiscrepencyFacilityList[] discrepencyFacilityList=(IDiscrepencyFacilityList[])trxValueOutMain.getActualDiscrepency().getFacilityList();
												for(int i=0;i<discrepencyFacilityList.length;i++){
													discrepencyFacilityList[i].setDiscrepencyId(trxValueOutMain.getActualDiscrepency().getId());
													discrepencyDAO.createDiscrepencyFacilityList("actualDiscrepencyFacilityList", discrepencyFacilityList[i]);
												}
											}
											obj.setStatus("PASS");
											totalUploadedList.add(obj);
										}else {
											errMsg="Invalid Discrepancy ID";
											obj.setFailReason(errMsg);
											obj.setStatus("FAIL");
											errorList.add(obj);
										}
								}catch(Exception e) {
									e.printStackTrace();
									DefaultLogger.debug(this, "<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl Discrepancy Exception: "+e.getMessage());
								}
								}else {
									errMsg="Discrepancy ID Either CLOSED or WAIVED or Pending for Authorization";
									obj.setFailReason(errMsg);
									obj.setStatus("FAIL");
									errorList.add(obj);
								}
							}else {
								errMsg="Discrepancy ID Not Found";
								obj.setFailReason(errMsg);
								obj.setStatus("FAIL");
								errorList.add(obj);
							}
						}
						//Update "Update Other Receipt" Module
						else if("OtherDocument".equals(obj.getModule())) {
							if(null!=obj.getChecklistID() && !"".equals(obj.getChecklistID())) {
								if(null!=obj.getDocID() && !"".equals(obj.getDocID())) {
									//update other doc next due date here
									try {
									OBTrxContext ctx = new OBTrxContext();
									ICheckListTrxValue checkListTrxVal=null;
									ICheckList checkList = null;
									ICheckListItem 	checkListItem=null;
									ICheckListProxyManager proxy = (ICheckListProxyManager)BeanHouse.get("checklistProxy");
									long checkListID = Long.parseLong(obj.getChecklistID());
									String checkListStatus=getChecklistStatus(checkListID);
									if(null!=checkListStatus && !"".equals(checkListStatus)) {
										checkListTrxVal = proxy.getCheckList(checkListID);//error
									}
									if(null!=checkListTrxVal && !"".equals(checkListTrxVal)) {
										if(!("WAIVED".equalsIgnoreCase(checkListTrxVal.getStatus().trim())
												||"RECEIVED".equalsIgnoreCase(checkListTrxVal.getStatus().trim()))
												&& "ACTIVE".equalsIgnoreCase(checkListTrxVal.getStatus().trim())){
											
											checkList = checkListTrxVal.getCheckList();
											ICheckListItem temp[] = checkList.getCheckListItemList();
											for(int i=0;i<temp.length;i++){//if(null!=temp[i] && null!=temp[i].getItem() && obj.getDocID().equals(temp[i].getItem().getItemCode())) {
												if(null!=temp[i] && null!=obj.getDocID() && obj.getDocID().equals(Long.toString(temp[i].getCheckListItemRef()))) {
													checkListItem = temp[i];
												}
											}
											if(null!=checkListItem && !"".equals(checkListItem)) {
												//set setDeferCount
												int deferCount=0;
												if(checkListItem.getDeferCount()==null || checkListItem.getDeferCount().trim().equals("")){
													deferCount=1;
													checkListItem.setDeferCount(String.valueOf(deferCount));
												}else{
													deferCount=Integer.parseInt(checkListItem.getDeferCount());
													deferCount=deferCount+1;
													checkListItem.setDeferCount(String.valueOf(deferCount));
												}
												//to setDeferedDays 
												if(null!=obj.getDateDeferred() && null!=obj.getNextDueDate()){
													Calendar calendar1 = Calendar.getInstance();
													Calendar calendar2 = Calendar.getInstance();
													calendar1.set(obj.getDateDeferred().getYear(),
														obj.getDateDeferred().getMonth(),
														obj.getDateDeferred().getDate());
													calendar2.set(obj.getNextDueDate().getYear(), 
														obj.getNextDueDate().getMonth(), 
														obj.getNextDueDate().getDate());
													long milliseconds1 = calendar1.getTimeInMillis();
													long milliseconds2 = calendar2.getTimeInMillis();
													long diff =  milliseconds2-milliseconds1;
													long diffSeconds = diff / 1000;
													long diffMinutes = diff / (60 * 1000);
													long diffHours = diff / (60 * 60 * 1000);
													long diffDays = diff / (24 * 60 * 60 * 1000);
															
													checkListItem.setDeferedDays(String.valueOf(diffDays));
												}
												//to set previous next_due_date
												if(null!=checkListItem.getExpectedReturnDate()) {
													DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
													String checkDate = df.format(checkListItem.getExpectedReturnDate());
													Date previousDate=new SimpleDateFormat("dd-MMM-yyyy").parse(checkDate);
													java.sql.Date sDate = new java.sql.Date(previousDate.getTime());
													obj.setPreviousNextDueDate(sDate);
												}
												checkListItem.setRemarks(obj.getRemarks());
												checkListItem.setDeferExpiryDate(obj.getDateDeferred());
												checkListItem.setExpectedReturnDate(obj.getNextDueDate());
												checkListItem.setCreditApprover(obj.getCreditApprover());
												checkListItem.setOriginalTargetDate(obj.getOriginalTargetDate());
												checkListItem.setUpdatedBy("System");
												checkListItem.setUpdatedDate(new Date());
												checkListItem.setApprovedBy("System");
												checkListItem.setApprovedDate(new Date());
												checkListItem.setItemStatus("PENDING_DEFER");
												
												for(int i=0;i<temp.length;i++){
													if(null!=temp[i] && obj.getDocID().equals(Long.toString(temp[i].getCheckListItemRef()))) {
												   System.out.println("checkList Item inside deferral extension job is : "+checkListItem+" status : "+checkListItem.getItemStatus()+
													" ItemRef : "+checkListItem.getCheckListItemRef()+" doc code : "+checkListItem.getItemCode());
														temp[i]=checkListItem;
													}
												}
												checkList.setCheckListItemList(temp);
												DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of executing Other module>>>>");			
												//Stage table update STAGE_CHECKLIST_ITEM 
												checkListTrxVal = proxy.makerUpdateCheckListReceipt(ctx, checkListTrxVal, checkList);
															
												//Main table update CMS_CHECKLIST_ITEM
												checkListTrxVal.setFlagSchedulers("Y");
												checkListTrxVal = proxy.checkerApproveCheckListReceipt(ctx, checkListTrxVal);
												proxy.updateSharedChecklistStatus(checkListTrxVal);
												
	                              listcheckListTest.add(checkListTrxVal);
													for(int i=0;i<temp.length;i++){
												if(null!=temp[i] && obj.getDocID().equals(Long.toString(temp[i].getCheckListItemRef()))) {
											String status =	getCheckListItemStatus(checkListTrxVal.getCheckList().getCheckListID(),temp[i].getCheckListItemRef(),temp[i].getItemDesc());
											System.out.println("checklist status from table inside deferral extension job before updateSecurityInsurance is : "+status);	
												}
											}
											updateSecurityInsurance(checkListTrxVal);
											
											for(int i=0;i<temp.length;i++){
											if(null!=temp[i] && obj.getDocID().equals(Long.toString(temp[i].getCheckListItemRef()))) {
											String status =	getCheckListItemStatus(checkListTrxVal.getCheckList().getCheckListID(),temp[i].getCheckListItemRef(),temp[i].getItemDesc());
											System.out.println("checklist status from table inside deferral extension job after updateSecurityInsurance is : "+status);	
											       }
										       }
												
												obj.setStatus("PASS");
												totalUploadedList.add(obj);
											}else {
												errMsg="Invalid DOC ID";
												obj.setFailReason(errMsg);
												obj.setStatus("FAIL");
												errorList.add(obj);
											}
										}else {
											errMsg="Checklist ID Either RECEIVED or WAIVED or Pending for Authorization";
											obj.setFailReason(errMsg);
											obj.setStatus("FAIL");
											errorList.add(obj);
										}
									}else {
										errMsg="Invalid CheckList ID";
										obj.setFailReason(errMsg);
										obj.setStatus("FAIL");
										errorList.add(obj);
									}
								}catch(Exception e) {
										e.printStackTrace();
										DefaultLogger.debug(this, "<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl OtherDocument Exception: "+e.getMessage());
								}
								}else {
									errMsg="DOC ID Not Found";
									obj.setFailReason(errMsg);
									obj.setStatus("FAIL");
									errorList.add(obj);
								}
							}else {
								errMsg="Checklist ID Not Found";
								obj.setFailReason(errMsg);
								obj.setStatus("FAIL");
								errorList.add(obj);
							}
						}
						//Update "Update Facility" Module
						else if("Facility".equals(obj.getModule())) {
							if(null!=obj.getChecklistID() && !"".equals(obj.getChecklistID())) {
								if(null!=obj.getDocID() && !"".equals(obj.getDocID())) {
									//update other doc next due date here
									try {
									OBTrxContext ctx = new OBTrxContext();
									ICheckListTrxValue checkListTrxVal=null;
									ICheckList checkList = null;
									ICheckListItem 	checkListItem=null;
									ICheckListProxyManager proxy = (ICheckListProxyManager)BeanHouse.get("checklistProxy");
									long checkListID = Long.parseLong(obj.getChecklistID());
									String checkListStatus=getChecklistStatus(checkListID);
									if(null!=checkListStatus && !"".equals(checkListStatus)) {
										checkListTrxVal = proxy.getCheckList(checkListID);//error
									}
									if(null!=checkListTrxVal && !"".equals(checkListTrxVal)) {
										if(!("WAIVED".equalsIgnoreCase(checkListTrxVal.getStatus().trim())
												||"RECEIVED".equalsIgnoreCase(checkListTrxVal.getStatus().trim()))
												&& "ACTIVE".equalsIgnoreCase(checkListTrxVal.getStatus().trim())){
											
											checkList = checkListTrxVal.getCheckList();
											ICheckListItem temp[] = checkList.getCheckListItemList();
											for(int i=0;i<temp.length;i++){//if(null!=temp[i] && null!=temp[i].getItem() && obj.getDocID().equals(temp[i].getItem().getItemCode())) {
												if(null!=temp[i] && null!=obj.getDocID() && obj.getDocID().equals(Long.toString(temp[i].getCheckListItemRef()))) {
													checkListItem = temp[i];
												}
											}
											if(null!=checkListItem && !"".equals(checkListItem)) {
												//set setDeferCount
												int deferCount=0;
												if(checkListItem.getDeferCount()==null || checkListItem.getDeferCount().trim().equals("")){
													deferCount=1;
													checkListItem.setDeferCount(String.valueOf(deferCount));
												}else{
													deferCount=Integer.parseInt(checkListItem.getDeferCount());
													deferCount=deferCount+1;
													checkListItem.setDeferCount(String.valueOf(deferCount));
												}
												//to setDeferedDays 
												if(null!=obj.getDateDeferred() && null!=obj.getNextDueDate()){
													Calendar calendar1 = Calendar.getInstance();
													Calendar calendar2 = Calendar.getInstance();
													calendar1.set(obj.getDateDeferred().getYear(),
														obj.getDateDeferred().getMonth(),
														obj.getDateDeferred().getDate());
													calendar2.set(obj.getNextDueDate().getYear(), 
														obj.getNextDueDate().getMonth(), 
														obj.getNextDueDate().getDate());
													long milliseconds1 = calendar1.getTimeInMillis();
													long milliseconds2 = calendar2.getTimeInMillis();
													long diff =  milliseconds2-milliseconds1;
													long diffSeconds = diff / 1000;
													long diffMinutes = diff / (60 * 1000);
													long diffHours = diff / (60 * 60 * 1000);
													long diffDays = diff / (24 * 60 * 60 * 1000);
															
													checkListItem.setDeferedDays(String.valueOf(diffDays));
												}
												//to set previous next_due_date
												if(null!=checkListItem.getExpectedReturnDate()) {
													DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
													String checkDate = df.format(checkListItem.getExpectedReturnDate());
													Date previousDate=new SimpleDateFormat("dd-MMM-yyyy").parse(checkDate);
													java.sql.Date sDate = new java.sql.Date(previousDate.getTime());
													obj.setPreviousNextDueDate(sDate);
												}
												checkListItem.setRemarks(obj.getRemarks());
												checkListItem.setDeferExpiryDate(obj.getDateDeferred());
												checkListItem.setExpectedReturnDate(obj.getNextDueDate());
												checkListItem.setCreditApprover(obj.getCreditApprover());
												checkListItem.setOriginalTargetDate(obj.getOriginalTargetDate());
												checkListItem.setUpdatedBy("System");
												checkListItem.setUpdatedDate(new Date());
												checkListItem.setApprovedBy("System");
												checkListItem.setApprovedDate(new Date());
												checkListItem.setItemStatus("PENDING_DEFER");
												
												for(int i=0;i<temp.length;i++){
													if(null!=temp[i] && obj.getDocID().equals(Long.toString(temp[i].getCheckListItemRef()))) {
														temp[i]=checkListItem;
													}
												}
												checkList.setCheckListItemList(temp);
												DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of executing Facility module>>>>");			
												//Stage table update STAGE_CHECKLIST_ITEM 
												checkListTrxVal = proxy.makerUpdateCheckListReceipt(ctx, checkListTrxVal, checkList);
															
												//Main table update CMS_CHECKLIST_ITEM
												checkListTrxVal.setFlagSchedulers("Y");
												checkListTrxVal = proxy.checkerApproveCheckListReceipt(ctx, checkListTrxVal);
												proxy.updateSharedChecklistStatus(checkListTrxVal);
												obj.setStatus("PASS");
												totalUploadedList.add(obj);
											}else {
												errMsg="Invalid DOC ID";
												obj.setFailReason(errMsg);
												obj.setStatus("FAIL");
												errorList.add(obj);
											}
										}else {
											errMsg="Checklist ID Either RECEIVED or WAIVED or Pending for Authorization";
											obj.setFailReason(errMsg);
											obj.setStatus("FAIL");
											errorList.add(obj);
										}
									}else {
										errMsg="Invalid CheckList ID";
										obj.setFailReason(errMsg);
										obj.setStatus("FAIL");
										errorList.add(obj);
									}
								}catch(Exception e) {
										e.printStackTrace();
										DefaultLogger.debug(this, "<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl Facility Document Exception: "+e.getMessage());
								}
								}else {
									errMsg="DOC ID Not Found";
									obj.setFailReason(errMsg);
									obj.setStatus("FAIL");
									errorList.add(obj);
								}
							}else {
								errMsg="Checklist ID Not Found";
								obj.setFailReason(errMsg);
								obj.setStatus("FAIL");
								errorList.add(obj);
							}
						}
						//Update "Update Security" Module
						else if("Security".equals(obj.getModule())) {
							if(null!=obj.getChecklistID() && !"".equals(obj.getChecklistID())) {
								if(null!=obj.getDocID() && !"".equals(obj.getDocID())) {
									//update other doc next due date here
									try {
									OBTrxContext ctx = new OBTrxContext();
									ICheckListTrxValue checkListTrxVal=null;
									ICheckList checkList = null;
									ICheckListItem 	checkListItem=null;
									ICheckListProxyManager proxy = (ICheckListProxyManager)BeanHouse.get("checklistProxy");
									long checkListID = Long.parseLong(obj.getChecklistID());
									String checkListStatus=getChecklistStatus(checkListID);
									if(null!=checkListStatus && !"".equals(checkListStatus)) {
										checkListTrxVal = proxy.getCheckList(checkListID);//error
									}
									if(null!=checkListTrxVal && !"".equals(checkListTrxVal)) {
										if(!("WAIVED".equalsIgnoreCase(checkListTrxVal.getStatus().trim())
												||"RECEIVED".equalsIgnoreCase(checkListTrxVal.getStatus().trim()))
												&& "ACTIVE".equalsIgnoreCase(checkListTrxVal.getStatus().trim())){
											
											checkList = checkListTrxVal.getCheckList();
											ICheckListItem temp[] = checkList.getCheckListItemList();
											for(int i=0;i<temp.length;i++){//if(null!=temp[i] && null!=temp[i].getItem() && obj.getDocID().equals(temp[i].getItem().getItemCode())) {
												if(null!=temp[i] && null!=obj.getDocID() && obj.getDocID().equals(Long.toString(temp[i].getCheckListItemRef()))) {
													checkListItem = temp[i];
												}
											}
											if(null!=checkListItem && !"".equals(checkListItem)) {
												//set setDeferCount
												int deferCount=0;
												if(checkListItem.getDeferCount()==null || checkListItem.getDeferCount().trim().equals("")){
													deferCount=1;
													checkListItem.setDeferCount(String.valueOf(deferCount));
												}else{
													deferCount=Integer.parseInt(checkListItem.getDeferCount());
													deferCount=deferCount+1;
													checkListItem.setDeferCount(String.valueOf(deferCount));
												}
												//to setDeferedDays 
												if(null!=obj.getDateDeferred() && null!=obj.getNextDueDate()){
													Calendar calendar1 = Calendar.getInstance();
													Calendar calendar2 = Calendar.getInstance();
													calendar1.set(obj.getDateDeferred().getYear(),
														obj.getDateDeferred().getMonth(),
														obj.getDateDeferred().getDate());
													calendar2.set(obj.getNextDueDate().getYear(), 
														obj.getNextDueDate().getMonth(), 
														obj.getNextDueDate().getDate());
													long milliseconds1 = calendar1.getTimeInMillis();
													long milliseconds2 = calendar2.getTimeInMillis();
													long diff =  milliseconds2-milliseconds1;
													long diffSeconds = diff / 1000;
													long diffMinutes = diff / (60 * 1000);
													long diffHours = diff / (60 * 60 * 1000);
													long diffDays = diff / (24 * 60 * 60 * 1000);
															
													checkListItem.setDeferedDays(String.valueOf(diffDays));
												}
												//to set previous next_due_date
												if(null!=checkListItem.getExpectedReturnDate()) {
													DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
													String checkDate = df.format(checkListItem.getExpectedReturnDate());
													Date previousDate=new SimpleDateFormat("dd-MMM-yyyy").parse(checkDate);
													java.sql.Date sDate = new java.sql.Date(previousDate.getTime());
													obj.setPreviousNextDueDate(sDate);
												}
												checkListItem.setRemarks(obj.getRemarks());
												checkListItem.setDeferExpiryDate(obj.getDateDeferred());
												checkListItem.setExpectedReturnDate(obj.getNextDueDate());
												checkListItem.setCreditApprover(obj.getCreditApprover());
												checkListItem.setOriginalTargetDate(obj.getOriginalTargetDate());
												checkListItem.setUpdatedBy("System");
												checkListItem.setUpdatedDate(new Date());
												checkListItem.setApprovedBy("System");
												checkListItem.setApprovedDate(new Date());
												checkListItem.setItemStatus("PENDING_DEFER");
												
												for(int i=0;i<temp.length;i++){
													if(null!=temp[i] && obj.getDocID().equals(Long.toString(temp[i].getCheckListItemRef()))) {
														temp[i]=checkListItem;
													}
												}
												checkList.setCheckListItemList(temp);
												DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of executing Security module>>>>");			
												//Stage table update STAGE_CHECKLIST_ITEM 
												checkListTrxVal = proxy.makerUpdateCheckListReceipt(ctx, checkListTrxVal, checkList);
															
												//Main table update CMS_CHECKLIST_ITEM
												checkListTrxVal.setFlagSchedulers("Y");
												checkListTrxVal = proxy.checkerApproveCheckListReceipt(ctx, checkListTrxVal);
												proxy.updateSharedChecklistStatus(checkListTrxVal);
												obj.setStatus("PASS");
												totalUploadedList.add(obj);
											}else {
												errMsg="Invalid DOC ID";
												obj.setFailReason(errMsg);
												obj.setStatus("FAIL");
												errorList.add(obj);
											}
										}else {
											errMsg="Checklist ID Either RECEIVED or WAIVED or Pending for Authorization";
											obj.setFailReason(errMsg);
											obj.setStatus("FAIL");
											errorList.add(obj);
										}
									}else {
										errMsg="Invalid CheckList ID";
										obj.setFailReason(errMsg);
										obj.setStatus("FAIL");
										errorList.add(obj);
									}
								}catch(Exception e) {
										e.printStackTrace();
										DefaultLogger.debug(this, "<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl Security Document Exception: "+e.getMessage());
								}
								}else {
									errMsg="DOC ID Not Found";
									obj.setFailReason(errMsg);
									obj.setStatus("FAIL");
									errorList.add(obj);
								}
							}else {
								errMsg="Checklist ID Not Found";
								obj.setFailReason(errMsg);
								obj.setStatus("FAIL");
								errorList.add(obj);
							}
						}
						//Update "Update CAM" Module
						else if("CAM".equals(obj.getModule())) {
							if(null!=obj.getChecklistID() && !"".equals(obj.getChecklistID())) {
								if(null!=obj.getDocID() && !"".equals(obj.getDocID())) {
									//update other doc next due date here
									try {
									OBTrxContext ctx = new OBTrxContext();
									ICheckListTrxValue checkListTrxVal=null;
									ICheckList checkList = null;
									ICheckListItem 	checkListItem=null;
									ICheckListProxyManager proxy = (ICheckListProxyManager)BeanHouse.get("checklistProxy");
									long checkListID = Long.parseLong(obj.getChecklistID());
									String checkListStatus=getChecklistStatus(checkListID);
									if(null!=checkListStatus && !"".equals(checkListStatus)) {
										checkListTrxVal = proxy.getCheckList(checkListID);//error
									}
									if(null!=checkListTrxVal && !"".equals(checkListTrxVal)) {
										if(!("WAIVED".equalsIgnoreCase(checkListTrxVal.getStatus().trim())
												||"RECEIVED".equalsIgnoreCase(checkListTrxVal.getStatus().trim()))
												&& "ACTIVE".equalsIgnoreCase(checkListTrxVal.getStatus().trim())){
											
											checkList = checkListTrxVal.getCheckList();
											ICheckListItem temp[] = checkList.getCheckListItemList();
											for(int i=0;i<temp.length;i++){//if(null!=temp[i] && null!=temp[i].getItem() && obj.getDocID().equals(temp[i].getItem().getItemCode())) {
												if(null!=temp[i] && null!=obj.getDocID() && obj.getDocID().equals(Long.toString(temp[i].getCheckListItemRef()))) {
													checkListItem = temp[i];
												}
											}
											if(null!=checkListItem && !"".equals(checkListItem)) {
												//set setDeferCount
												int deferCount=0;
												if(checkListItem.getDeferCount()==null || checkListItem.getDeferCount().trim().equals("")){
													deferCount=1;
													checkListItem.setDeferCount(String.valueOf(deferCount));
												}else{
													deferCount=Integer.parseInt(checkListItem.getDeferCount());
													deferCount=deferCount+1;
													checkListItem.setDeferCount(String.valueOf(deferCount));
												}
												//to setDeferedDays 
												if(null!=obj.getDateDeferred() && null!=obj.getNextDueDate()){
													Calendar calendar1 = Calendar.getInstance();
													Calendar calendar2 = Calendar.getInstance();
													calendar1.set(obj.getDateDeferred().getYear(),
														obj.getDateDeferred().getMonth(),
														obj.getDateDeferred().getDate());
													calendar2.set(obj.getNextDueDate().getYear(), 
														obj.getNextDueDate().getMonth(), 
														obj.getNextDueDate().getDate());
													long milliseconds1 = calendar1.getTimeInMillis();
													long milliseconds2 = calendar2.getTimeInMillis();
													long diff =  milliseconds2-milliseconds1;
													long diffSeconds = diff / 1000;
													long diffMinutes = diff / (60 * 1000);
													long diffHours = diff / (60 * 60 * 1000);
													long diffDays = diff / (24 * 60 * 60 * 1000);
															
													checkListItem.setDeferedDays(String.valueOf(diffDays));
												}
												//to set previous next_due_date
												if(null!=checkListItem.getExpectedReturnDate()) {
													DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
													String checkDate = df.format(checkListItem.getExpectedReturnDate());
													Date previousDate=new SimpleDateFormat("dd-MMM-yyyy").parse(checkDate);
													java.sql.Date sDate = new java.sql.Date(previousDate.getTime());
													obj.setPreviousNextDueDate(sDate);
												}
												checkListItem.setRemarks(obj.getRemarks());
												checkListItem.setDeferExpiryDate(obj.getDateDeferred());
												checkListItem.setExpectedReturnDate(obj.getNextDueDate());
												checkListItem.setCreditApprover(obj.getCreditApprover());
												checkListItem.setOriginalTargetDate(obj.getOriginalTargetDate());
												checkListItem.setUpdatedBy("System");
												checkListItem.setUpdatedDate(new Date());
												checkListItem.setApprovedBy("System");
												checkListItem.setApprovedDate(new Date());
												checkListItem.setItemStatus("PENDING_DEFER");
												
												for(int i=0;i<temp.length;i++){
													if(null!=temp[i] && obj.getDocID().equals(Long.toString(temp[i].getCheckListItemRef()))) {
														temp[i]=checkListItem;
													}
												}
												checkList.setCheckListItemList(temp);
												DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of executing CAM module>>>>");			
												//Stage table update STAGE_CHECKLIST_ITEM 
												checkListTrxVal = proxy.makerUpdateCheckListReceipt(ctx, checkListTrxVal, checkList);
															
												//Main table update CMS_CHECKLIST_ITEM
												checkListTrxVal.setFlagSchedulers("Y");
												checkListTrxVal = proxy.checkerApproveCheckListReceipt(ctx, checkListTrxVal);
												proxy.updateSharedChecklistStatus(checkListTrxVal);
												obj.setStatus("PASS");
												totalUploadedList.add(obj);
											}else {
												errMsg="Invalid DOC ID";
												obj.setFailReason(errMsg);
												obj.setStatus("FAIL");
												errorList.add(obj);
											}
										}else {
											errMsg="Checklist ID Either RECEIVED or WAIVED or Pending for Authorization";
											obj.setFailReason(errMsg);
											obj.setStatus("FAIL");
											errorList.add(obj);
										}
									}else {
										errMsg="Invalid CheckList ID";
										obj.setFailReason(errMsg);
										obj.setStatus("FAIL");
										errorList.add(obj);
									}
								}catch(Exception e) {
										e.printStackTrace();
										DefaultLogger.debug(this, "<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl CAMDocument Exception: "+e.getMessage());
								}
								}else {
									errMsg="DOC ID Not Found";
									obj.setFailReason(errMsg);
									obj.setStatus("FAIL");
									errorList.add(obj);
								}
							}else {
								errMsg="Checklist ID Not Found";
								obj.setFailReason(errMsg);
								obj.setStatus("FAIL");
								errorList.add(obj);
							}
						}
						//Update "Maintain Recurrent Document Checklist" Module
						else if("RecurrentDoc".equals(obj.getModule())) {
							if(null!=obj.getChecklistID() && !"".equals(obj.getChecklistID())) {
								if(null!=obj.getDocID() && !"".equals(obj.getDocID())) {
									//update Recurrent doc next due date here
									try {
									OBTrxContext ctx = new OBTrxContext();
									ICheckListTrxValue checkListTrxVal=null;
									ICheckList checkList = null;
									ICheckListItem 	checkListItem=null;
									ICheckListProxyManager proxy = (ICheckListProxyManager)BeanHouse.get("checklistProxy");
									long checkListID = Long.parseLong(obj.getChecklistID());
									String checkListStatus=getChecklistStatus(checkListID);
									if(null!=checkListStatus && !"".equals(checkListStatus)) {
										checkListTrxVal = proxy.getCheckList(checkListID);
									}
									if(null!=checkListTrxVal && !"".equals(checkListTrxVal)) {
										if(!("WAIVED".equalsIgnoreCase(checkListTrxVal.getStatus().trim())
												||"RECEIVED".equalsIgnoreCase(checkListTrxVal.getStatus().trim()))
												&& "ACTIVE".equalsIgnoreCase(checkListTrxVal.getStatus().trim())){
											
											checkList = checkListTrxVal.getCheckList();
											ICheckListItem temp[] = checkList.getCheckListItemList();
											for(int i=0;i<temp.length;i++){//if(null!=temp[i] && obj.getDocID().equals(Long.toString(temp[i].getCheckListItemRef()))) {
												if(null!=temp[i] && null!=obj.getDocID() && obj.getDocID().equals(Long.toString(temp[i].getCheckListItemRef()))) {
													checkListItem = temp[i];
												}
											}
											if(null!=checkListItem && !"".equals(checkListItem)) {
												//set setDeferCount
												int deferCount=0;
												if(checkListItem.getDeferCount()==null || checkListItem.getDeferCount().trim().equals("")){
													deferCount=1;
													checkListItem.setDeferCount(String.valueOf(deferCount));
												}else{
													deferCount=Integer.parseInt(checkListItem.getDeferCount());
													deferCount=deferCount+1;
													checkListItem.setDeferCount(String.valueOf(deferCount));
												}
												//to setDeferedDays 
												if(null!=obj.getDateDeferred() && null!=obj.getNextDueDate()){
													Calendar calendar1 = Calendar.getInstance();
													Calendar calendar2 = Calendar.getInstance();
													calendar1.set(obj.getDateDeferred().getYear(),
														obj.getDateDeferred().getMonth(),
														obj.getDateDeferred().getDate());
													calendar2.set(obj.getNextDueDate().getYear(), 
														obj.getNextDueDate().getMonth(), 
														obj.getNextDueDate().getDate());
													long milliseconds1 = calendar1.getTimeInMillis();
													long milliseconds2 = calendar2.getTimeInMillis();
													long diff =  milliseconds2-milliseconds1;
													long diffSeconds = diff / 1000;
													long diffMinutes = diff / (60 * 1000);
													long diffHours = diff / (60 * 60 * 1000);
													long diffDays = diff / (24 * 60 * 60 * 1000);
															
													checkListItem.setDeferedDays(String.valueOf(diffDays));
												}
												//to set previous next_due_date
												if(null!=checkListItem.getExpectedReturnDate()) {
													DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
													String checkDate = df.format(checkListItem.getExpectedReturnDate());
													Date previousDate=new SimpleDateFormat("dd-MMM-yyyy").parse(checkDate);
													java.sql.Date sDate = new java.sql.Date(previousDate.getTime());
													obj.setPreviousNextDueDate(sDate);
												}
												checkListItem.setRemarks(obj.getRemarks());
												checkListItem.setDeferExpiryDate(obj.getDateDeferred());
												checkListItem.setExpectedReturnDate(obj.getNextDueDate());
												checkListItem.setCreditApprover(obj.getCreditApprover());
												checkListItem.setUpdatedBy("System");
												checkListItem.setUpdatedDate(new Date());
												checkListItem.setApprovedBy("System");
												checkListItem.setApprovedDate(new Date());
												checkListItem.setItemStatus("PENDING_DEFER");
												
												for(int i=0;i<temp.length;i++){
													if(null!=temp[i] && obj.getDocID().equals(Long.toString(temp[i].getCheckListItemRef()))) {
														temp[i]=checkListItem;
													}
												}
												checkList.setCheckListItemList(temp);
												DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of executing recurrent module>>>>");			
												//Stage table update STAGE_CHECKLIST_ITEM 
												checkListTrxVal = proxy.makerUpdateCheckListReceipt(ctx, checkListTrxVal, checkList);
															
												//Main table update CMS_CHECKLIST_ITEM
												checkListTrxVal.setFlagSchedulers("Y");
												checkListTrxVal = proxy.checkerApproveCheckListReceipt(ctx, checkListTrxVal);
												proxy.updateSharedChecklistStatus(checkListTrxVal);
												obj.setStatus("PASS");
												totalUploadedList.add(obj);
											}else {
												errMsg="Invalid DOC ID";
												obj.setFailReason(errMsg);
												obj.setStatus("FAIL");
												errorList.add(obj);
											}
										}else {
											errMsg="Checklist ID Either RECEIVED or WAIVED or Pending for Authorization";
											obj.setFailReason(errMsg);
											obj.setStatus("FAIL");
											errorList.add(obj);
										}
									}else {
										errMsg="Invalid CheckList ID";
										obj.setFailReason(errMsg);
										obj.setStatus("FAIL");
										errorList.add(obj);
									}
								}catch(Exception e) {
										e.printStackTrace();
										DefaultLogger.debug(this, "<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl RecurrentDoc Exception: "+e.getMessage());
								}
								}else {
									errMsg="DOC ID Not Found";
									obj.setFailReason(errMsg);
									obj.setStatus("FAIL");
									errorList.add(obj);
								}
							}else {
								errMsg="Checklist ID Not Found";
								obj.setFailReason(errMsg);
								obj.setStatus("FAIL");
								errorList.add(obj);
							}
						}
						//Update "Update LAD Receipt" Module
						else if("LAD".equals(obj.getModule())) {
							if(null!=obj.getChecklistID() && !"".equals(obj.getChecklistID())) {
								if(null!=obj.getDocID() && !"".equals(obj.getDocID())) {
									//update LAD doc next due date here
									try {
									OBTrxContext ctx = new OBTrxContext();
									ICheckListTrxValue checkListTrxVal=null;
									ICheckList checkList = null;
									ICheckListItem 	checkListItem=null;
									ICheckListProxyManager proxy = (ICheckListProxyManager)BeanHouse.get("checklistProxy");
									long checkListID = Long.parseLong(obj.getChecklistID());
									String checkListStatus=getChecklistStatus(checkListID);
									if(null!=checkListStatus && !"".equals(checkListStatus)) {
										checkListTrxVal = proxy.getCheckList(checkListID);
									}
									if(null!=checkListTrxVal && !"".equals(checkListTrxVal)) {
										if(!("WAIVED".equalsIgnoreCase(checkListTrxVal.getStatus().trim())
												||"RECEIVED".equalsIgnoreCase(checkListTrxVal.getStatus().trim()))
												&& "ACTIVE".equalsIgnoreCase(checkListTrxVal.getStatus().trim())){
											
											checkList = checkListTrxVal.getCheckList();
											ICheckListItem temp[] = checkList.getCheckListItemList();
											for(int i=0;i<temp.length;i++){//null!=temp[i] && obj.getDocID().equals(Long.toString(temp[i].getCheckListItemRef()))
												if(null!=temp[i] && null!=obj.getDocID() && obj.getDocID().equals(Long.toString(temp[i].getCheckListItemRef()))) {
													checkListItem = temp[i];
												}
											}
											if(null!=checkListItem && !"".equals(checkListItem)) {
												//set setDeferCount
												int deferCount=0;
												if(checkListItem.getDeferCount()==null || checkListItem.getDeferCount().trim().equals("")){
													deferCount=1;
													checkListItem.setDeferCount(String.valueOf(deferCount));
												}else{
													deferCount=Integer.parseInt(checkListItem.getDeferCount());
													deferCount=deferCount+1;
													checkListItem.setDeferCount(String.valueOf(deferCount));
												}
												//to setDeferedDays 
												if(null!=obj.getDateDeferred() && null!=obj.getNextDueDate()){
													Calendar calendar1 = Calendar.getInstance();
													Calendar calendar2 = Calendar.getInstance();
													calendar1.set(obj.getDateDeferred().getYear(),
														obj.getDateDeferred().getMonth(),
														obj.getDateDeferred().getDate());
													calendar2.set(obj.getNextDueDate().getYear(), 
														obj.getNextDueDate().getMonth(), 
														obj.getNextDueDate().getDate());
													long milliseconds1 = calendar1.getTimeInMillis();
													long milliseconds2 = calendar2.getTimeInMillis();
													long diff =  milliseconds2-milliseconds1;
													long diffSeconds = diff / 1000;
													long diffMinutes = diff / (60 * 1000);
													long diffHours = diff / (60 * 60 * 1000);
													long diffDays = diff / (24 * 60 * 60 * 1000);
															
													checkListItem.setDeferedDays(String.valueOf(diffDays));
												}
												//to set previous next_due_date
												if(null!=checkListItem.getExpectedReturnDate()) {
													DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
													String checkDate = df.format(checkListItem.getExpectedReturnDate());
													Date previousDate=new SimpleDateFormat("dd-MMM-yyyy").parse(checkDate);
													java.sql.Date sDate = new java.sql.Date(previousDate.getTime());
													obj.setPreviousNextDueDate(sDate);
												}
												checkListItem.setRemarks(obj.getRemarks());
												checkListItem.setDeferExpiryDate(obj.getDateDeferred());
												checkListItem.setExpectedReturnDate(obj.getNextDueDate());
												checkListItem.setCreditApprover(obj.getCreditApprover());
												checkListItem.setUpdatedBy("System");
												checkListItem.setUpdatedDate(new Date());
												checkListItem.setApprovedBy("System");
												checkListItem.setApprovedDate(new Date());
												checkListItem.setItemStatus("PENDING_DEFER");
												
												for(int i=0;i<temp.length;i++){
													if(null!=temp[i] && obj.getDocID().equals(Long.toString(temp[i].getCheckListItemRef()))) {
														temp[i]=checkListItem;
													}
												}
												checkList.setCheckListItemList(temp);
												DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of executing LAD module>>>>");			
												//Stage table update STAGE_CHECKLIST_ITEM
												checkListTrxVal = proxy.makerUpdateCheckListReceipt(ctx, checkListTrxVal, checkList);
															
												//Main table update CMS_CHECKLIST_ITEM
												checkListTrxVal.setFlagSchedulers("Y");
												checkListTrxVal = proxy.checkerApproveCheckListReceipt(ctx, checkListTrxVal);
												proxy.updateSharedChecklistStatus(checkListTrxVal);
												obj.setStatus("PASS");
												totalUploadedList.add(obj);
											}else {
												errMsg="Invalid DOC ID";
												obj.setFailReason(errMsg);
												obj.setStatus("FAIL");
												errorList.add(obj);
											}
										}else {
											errMsg="Checklist ID Either RECEIVED or WAIVED or Pending for Authorization";
											obj.setFailReason(errMsg);
											obj.setStatus("FAIL");
											errorList.add(obj);
										}
									}else {
										errMsg="Invalid CheckList ID";
										obj.setFailReason(errMsg);
										obj.setStatus("FAIL");
										errorList.add(obj);
									}
								}catch(Exception e) {
									e.printStackTrace();
									DefaultLogger.debug(this, "<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl LAD Exception: "+e.getMessage());
								}
								}else {
									errMsg="DOC ID Not Found";
									obj.setFailReason(errMsg);
									obj.setStatus("FAIL");
									errorList.add(obj);
								}
							}else {
								errMsg="Checklist ID Not Found";
								obj.setFailReason(errMsg);
								obj.setStatus("FAIL");
								errorList.add(obj);
							}
						}
						//Invalid module name
						else {
							errMsg="Invalid module name";
							obj.setFailReason(errMsg);
							obj.setStatus("FAIL");
							errorList.add(obj);
						}
					}
					//Inactive party
					else if(!partyStatus.equals("") && "INACTIVE".equals(partyStatus)){
						errMsg="Party Is Inactive In CLIMS.";
						obj.setFailReason(errMsg);
						obj.setStatus("FAIL");
						errorList.add(obj);
					}
					//Party Not Found
					else if(partyStatus.equals("")){
						errMsg="Party Is Not Found In CLIMS.";
						obj.setFailReason(errMsg);
						obj.setStatus("FAIL");
						errorList.add(obj);
					}
				}//End if condition of Invalid Record 
				else {
					DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl isValidRecord:"+isValidRecord);
					errorList.add(obj);	
				}	
					//totalUploadedList.add(obj);
				}//End main for loop
				 ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
				for(int i=0;i<listcheckListTest.size();i++) {
					System.out.println("===============inside FileUploadJdbcImpl 6819 outside for loop after updating all checklist  ");
						updateSecurityInsurance(listcheckListTest.get(i));
						ICheckList checklist = listcheckListTest.get(i).getCheckList();
						  for(int j=0;j<checklist.getCheckListItemList().length;j++) {
							ICheckListItem checkListItem = checklist.getCheckListItemList()[j];
			String status =	getCheckListItemStatus(checklist.getCheckListID(),checkListItem.getCheckListItemRef(),checkListItem.getItemDesc());
			System.out.println("<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl at line 6825 \n CheckList Id  : "+checklist.getCheckListID()+
							" ItemRef : "+checkListItem.getCheckListItemRef()+" doc code : "+checkListItem.getItemCode()+" doc description : "         
	                                                 +checkListItem.getItemDesc()+" checklist status :"+status);
							if(null!=checkListItem.getInsuranceId()) {
						   
						    String insuranceStatus ="";
						    if(!checkListItem.getInsuranceId().startsWith("IG")) {
						    	System.out.println("inside if for getInsuranceStatus ");
						   insuranceStatus = collateralDAO.getGcInsuranceStatus(checkListItem.getInsuranceId());
						    }
						    else {
						    	System.out.println("inside else for getGcInsuranceStatus ");
						    	   insuranceStatus = collateralDAO.getGcInsuranceStatus(checkListItem.getInsuranceId());
						    }
		      System.out.println("<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl at line 6839  CheckList InsuranceId : "+checkListItem.getInsuranceId()+
								  " insurance status : "+insuranceStatus);
						    
							}
							else {
								System.out.println("inside else for getInsuranceId becoz InsuranceId is null ");
							}
						  }   
					}										 
			}
		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.getMessage());
			throw new IncompleteBatchJobException(
			"Unable to update retrived record from CSV file");
		}
		retMap.put("totalUploadedList", totalUploadedList);
		retMap.put("errorList", errorList);
		return retMap;
	}
	private IDiscrepencyTrxValue updateMainTable(OBTrxContext ctx, IDiscrepencyTrxValue trxValueOut, IDiscrepency discrepencyMain) 
			throws NoSuchDiscrepencyException, TrxParameterException, TransactionException, ConcurrentUpdateException {
		IDiscrepencyDAO discrepencyDAO=(IDiscrepencyDAO)BeanHouse.get("discrepencyDAO");
		IDiscrepencyProxyManager discrepencyProxy = (IDiscrepencyProxyManager)BeanHouse.get("discrepencyProxy");
		IDiscrepencyTrxValue trxValueOutMain = discrepencyProxy.checkerApproveDiscrepency(ctx,trxValueOut);//CMS_DISCREPENCY
		OBDiscrepency updateActualMain=replaceMainActual((OBDiscrepency)trxValueOutMain.getActualDiscrepency(),trxValueOutMain);				
		discrepencyDAO.updateDiscrepency(updateActualMain);//CMS_DISCREPENCY
		OBDiscrepency updateStageMain=replaceMainStage(discrepencyMain,trxValueOutMain);
		discrepencyDAO.updateStageDiscrepency(updateStageMain);//CMS_STAGE_DISCREPENCY
		return trxValueOutMain;
	}

	private IDiscrepencyTrxValue updateStageTable(OBTrxContext ctx, IDiscrepencyTrxValue trxValueIn,IDiscrepency discrepency) 
					throws NoSuchDiscrepencyException, TrxParameterException, TransactionException, ConcurrentUpdateException {
		IDiscrepencyTrxValue trxValueOut = new OBDiscrepencyTrxValue();
		IDiscrepencyDAO discrepencyDAO=(IDiscrepencyDAO)BeanHouse.get("discrepencyDAO");
		IDiscrepencyProxyManager discrepencyProxy = (IDiscrepencyProxyManager)BeanHouse.get("discrepencyProxy");
		trxValueOut = discrepencyProxy.makerUpdateDiscrepency(ctx, trxValueIn,discrepency);
		OBDiscrepency updateStage=replaceStage(discrepency,trxValueOut);
		OBDiscrepency updateActual=replaceActual(trxValueIn.getActualDiscrepency(),updateStage);
		discrepencyDAO.updateStageDiscrepency(updateStage);//CMS_STAGE_DISCREPENCY
		discrepencyDAO.updateDiscrepency(updateActual);//CMS_DISCREPENCY
		return trxValueOut;
	}
	//getChecklistStatus
	private String getChecklistStatus(long checklistID) {
		String query="SELECT STATUS FROM CMS_CHECKLIST WHERE CHECKLIST_ID=?";
		DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getChecklistStatus() query= "+query);
		try {
			List arrList = getJdbcTemplate().query(query,
					new Object[] { new Long(checklistID) }, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("STATUS");
				}
			});
			DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getChecklistStatus() arrList= "+arrList);
			if (arrList.size()<1){
				return null;
			}else{
				return (String) arrList.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getChecklistStatus() Exception= "+e);
		}
		return null;
	}
	
	//to getChecklistType
	private String getChecklistType(String checklistID) {
		String query="SELECT CATEGORY FROM CMS_CHECKLIST WHERE CHECKLIST_ID=?";
		DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getChecklistType() query= "+query);
		try {
			List arrList = getJdbcTemplate().query(query,
					new Object[] { new String(checklistID) }, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("CATEGORY");
				}
			});
			DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getSegment() arrList= "+arrList);
			if (arrList.size()<1){
				return null;
			}else{
				String type= (String) arrList.get(0);
				if("O".equals(type))
					return "Other";
				if("REC".equals(type))
					return "Recurrent";
				if("LAD".equals(type))
					return "LAD";
				if("F".equals(type))
					return "Facility";
				if("S".equals(type))
					return "Security";
				if("CAM".equals(type))
					return "CAM";
			}
		}catch(Exception e){
			e.printStackTrace();
			DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getPartyStatus() Exception= "+e);
		}
		return null;
	}
	// to getSegment
	private String getSegment(String partyID) {
		String selectSQL = " SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY WHERE ENTRY_CODE="
				+ "(SELECT LSP_SGMNT_CODE_VALUE FROM SCI_LE_SUB_PROFILE WHERE LSP_LE_ID=?) AND CATEGORY_CODE='HDFC_SEGMENT' " + 
				"				 AND ACTIVE_STATUS='1' ";
		DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getSegment() query= "+selectSQL);
		
		try {
			List arrList = getJdbcTemplate().query(selectSQL,
					new Object[] { new String(partyID) }, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("ENTRY_NAME");
				}
			});
			DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getSegment() arrList= "+arrList);
			if (arrList.size()<1){
				return null;
			}else{
				return (String) arrList.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getSegment() Exception= "+e);
		}
		return null;
	}
	// to getPartyName
	private String getPartyName(String partyID) {
		String selectSQL="SELECT LSP_SHORT_NAME FROM SCI_LE_SUB_PROFILE WHERE LSP_LE_ID=?";
		DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getPartyName() query= "+selectSQL);
		try {
			List arrList = getJdbcTemplate().query(selectSQL,
					new Object[] { new String(partyID) }, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("LSP_SHORT_NAME");
				}
			});
			DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getPartyName() arrList= "+arrList);
			if (arrList.size()<1){
				return null;
			}else{
				return (String) arrList.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getPartyName() Exception= "+e);
		}
		return null;
	}
	// to getPartyStatus
	private String getPartyStatus(String partyID) {
		System.out.println("<<<<<RAM_RATING getPartyStatus()>>>>>>");
		String selectSQL="SELECT STATUS FROM SCI_LE_SUB_PROFILE WHERE LSP_LE_ID=?";
		try {
			List arrList = getJdbcTemplate().query(selectSQL,
					new Object[] { new String(partyID) }, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("STATUS");
				}
			});
			if (arrList.size()<1){
				return null;
			}else{
				return (String) arrList.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("<<<<<RAM_RATING getPartyStatus()>>>>>>"+e);
		}
		return null;
	}
	
	private String getPartyStatusByCompanyCode(String partyID) {
		String selectSQL=" select status from sci_le_sub_profile where lsp_le_id=?";
		try {
			List arrList = getJdbcTemplate().query(selectSQL,
					new Object[] { new String(partyID.trim()) }, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("STATUS");
				}
			});
			if (arrList.size()<1){
				return null;
			}else{
				return (String) arrList.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("<<<<<Exception RAM_RATING getPartyStatus()>>>>>>"+e);
		}
		return null;
	}
	
	private String getPartyId(String companyCode) {
		String selectSQL="select LMP_LE_ID from sci_le_main_profile where CMS_LE_MAIN_PROFILE_ID ="
				+ "(select MAX(CMS_LE_MAIN_PROFILE_ID) from SCI_LE_CRI where CUSTOMER_RAM_ID=?)";
		
		System.out.println("<<<<<RAM_RATING getPartyStatus()>>>>>>"+selectSQL);
		try {
			List arrList = getJdbcTemplate().query(selectSQL,
					new Object[] { new String(companyCode.trim()) }, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("LMP_LE_ID");
				}
			});
			if (arrList.size()<1){
				return null;
			}else{
				return (String) arrList.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("<<<<<Exception RAM_RATING getPartyID()>>>>>>"+e);
		}
		return null;
	}
	
	//to create stage Deferral Extension File
	public void createEntireDeferralExtensionFile(List<OBDeferralExtensionFileUpload> objList)throws FileUploadException,SQLException{

		String sql = " INSERT INTO STAGE_DEFERRAL_UPLOAD "
				+ " (ID,PARTYID,PARTYNAME,SEGMENTS,MODULE,CHECKLISTID,CHECKLISTTYPE,DOCID,DISCREPANCYID, "
				+ " LADCODE,DATEDEFERRED,NEXTDUEDATE,PREVIOUSNEXTDUEDATE,ACTION,CREDITAPPROVER,REMARKS,STATUS, "
				+ " UPLOADSTATUS,FILEID,FILENAME,FAILREASON,DATEOFREQUEST,DATEOFPROCESS,ORIGINALTARGETDATE) "
				+ " VALUES(STAGE_DEFERRAL_UPLOAD_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		
		
		final List<OBDeferralExtensionFileUpload> objectList = objList;
		final int batchSize = objList.size();
		
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBDeferralExtensionFileUpload obj = objectList.get(i);
					ps.setString(1,obj.getPartyID());
					ps.setString(2,obj.getPartyName());
					ps.setString(3,obj.getSegment());
					ps.setString(4,obj.getModule());
					ps.setString(5,obj.getChecklistID());
					ps.setString(6,obj.getChecklistType());
					ps.setString(7,obj.getDocID());
					ps.setString(8,obj.getDiscrepancyID());
					ps.setString(9,obj.getLadCode());
					ps.setDate(10, (java.sql.Date)obj.getDateDeferred());
					ps.setDate(11, (java.sql.Date)obj.getNextDueDate());
					ps.setDate(12, (java.sql.Date)obj.getPreviousNextDueDate());
					ps.setString(13,obj.getAction());
					ps.setString(14,obj.getCreditApprover());
					ps.setString(15,obj.getRemarks());
					ps.setString(16,obj.getStatus());
					ps.setString(17,obj.getUploadStatus());
					ps.setLong(18, obj.getFileId());
					ps.setString(19,obj.getFileName());
					ps.setString(20,obj.getFailReason());
					ps.setDate(21, (java.sql.Date)obj.getDateOfRequest());
					ps.setDate(22, (java.sql.Date)obj.getDateOfProcess());
					ps.setDate(23, (java.sql.Date)obj.getOriginalTargetDate());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
	}
	
	//to create stage Deferral Extension File
	public void approveEntireDeferralExtensionFile(List<OBDeferralExtensionFileUpload> objList)throws FileUploadException,SQLException{

			String sql = " INSERT INTO CMS_DEFERRAL_UPLOAD "
					+ " (ID,PARTYID,PARTYNAME,SEGMENTS,MODULE,CHECKLISTID,CHECKLISTTYPE,DOCID,DISCREPANCYID, "
					+ " LADCODE,DATEDEFERRED,NEXTDUEDATE,PREVIOUSNEXTDUEDATE,ACTION,CREDITAPPROVER,REMARKS,STATUS, "
					+ " UPLOADSTATUS,FILEID,FILENAME,FAILREASON,DATEOFREQUEST,DATEOFPROCESS,ORIGINALTARGETDATE) "
					+ " VALUES(STAGE_DEFERRAL_UPLOAD_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			
			
			final List<OBDeferralExtensionFileUpload> objectList = objList;
			final int batchSize = objList.size();
			
				getJdbcTemplate().batchUpdate(sql,
						new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
					throws SQLException {
						OBDeferralExtensionFileUpload obj = objectList.get(i);
						ps.setString(1,obj.getPartyID());
						ps.setString(2,obj.getPartyName());
						ps.setString(3,obj.getSegment());
						ps.setString(4,obj.getModule());
						ps.setString(5,obj.getChecklistID());
						ps.setString(6,obj.getChecklistType());
						ps.setString(7,obj.getDocID());
						ps.setString(8,obj.getDiscrepancyID());
						ps.setString(9,obj.getLadCode());
						ps.setDate(10, (java.sql.Date)obj.getDateDeferred());
						ps.setDate(11, (java.sql.Date)obj.getNextDueDate());
						ps.setDate(12, (java.sql.Date)obj.getPreviousNextDueDate());
						ps.setString(13,obj.getAction());
						ps.setString(14,obj.getCreditApprover());
						ps.setString(15,obj.getRemarks());
						ps.setString(16,obj.getStatus());
						ps.setString(17,obj.getUploadStatus());
						ps.setLong(18, obj.getFileId());
						ps.setString(19,obj.getFileName());
						ps.setString(20,obj.getFailReason());
						ps.setDate(21, (java.sql.Date)obj.getDateOfRequest());
						ps.setDate(22, (java.sql.Date)obj.getDateOfProcess());
						ps.setDate(23, (java.sql.Date)obj.getOriginalTargetDate());
					}
					public int getBatchSize() {
						return objectList.size();
					}
				});
	}
		
	public String calculateDays(Date nextDate,Date calculateDate){
		Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(calculateDate.getYear(),calculateDate.getMonth(),calculateDate.getDate());
		calendar2.set(nextDate.getYear(), nextDate.getMonth(), nextDate.getDate());
		  long milliseconds1 = calendar1.getTimeInMillis();
		  long milliseconds2 = calendar2.getTimeInMillis();
		  long diff =  milliseconds2-milliseconds1;
		  long diffSeconds = diff / 1000;
		  long diffMinutes = diff / (60 * 1000);
		  long diffHours = diff / (60 * 60 * 1000);
		  long diffDays = diff / (24 * 60 * 60 * 1000);
		  String days=String.valueOf(diffDays);
		  return days;
	}
	
	public OBDiscrepency replaceActual(OBDiscrepency discrepency,IDiscrepencyTrxValue trxValueOut)
	{
		OBDiscrepency replace=new OBDiscrepency();
		replace.setAcceptedDate(discrepency.getAcceptedDate());
		replace.setApprovedBy(discrepency.getApprovedBy());
		replace.setCounter(discrepency.getCounter());
		replace.setCreationDate(discrepency.getCreationDate());
		replace.setCreditApprover(discrepency.getCreditApprover());
		replace.setCustomerId(discrepency.getCustomerId());
		replace.setDeferDate(discrepency.getDeferDate());
		replace.setTotalDeferedDays(discrepency.getTotalDeferedDays());
		replace.setOriginalDeferedDays(discrepency.getOriginalDeferedDays());
		replace.setDeferedCounter(discrepency.getDeferedCounter());
		replace.setDiscrepency(discrepency.getDiscrepency());
		replace.setDiscrepencyRemark(discrepency.getDiscrepencyRemark());
		replace.setDiscrepencyType(discrepency.getDiscrepencyType());
		replace.setDocRemarks(discrepency.getDocRemarks());
		replace.setFacilityList(discrepency.getFacilityList());
		replace.setId(discrepency.getId());
		replace.setNextDueDate(discrepency.getNextDueDate());
		replace.setOriginalTargetDate(discrepency.getOriginalTargetDate());
		replace.setRecDate(discrepency.getRecDate());
		replace.setStatus(discrepency.getStatus());
		replace.setTransactionStatus(trxValueOut.getStatus());
		replace.setCritical(discrepency.getCritical());
		replace.setWaiveDate(discrepency.getWaiveDate());
		
		return replace;
	}
	
	public OBDiscrepency replaceStage(IDiscrepency discrepency,IDiscrepencyTrxValue trxValueOut)
	{
		OBDiscrepency replace=new OBDiscrepency();
		replace.setAcceptedDate(discrepency.getAcceptedDate());
		replace.setApprovedBy(discrepency.getApprovedBy());
		replace.setCounter(discrepency.getCounter());
		replace.setCreationDate(discrepency.getCreationDate());
		replace.setCreditApprover(discrepency.getCreditApprover());
		replace.setCustomerId(discrepency.getCustomerId());
		replace.setDeferDate(discrepency.getDeferDate());
		replace.setTotalDeferedDays(discrepency.getTotalDeferedDays());
		replace.setOriginalDeferedDays(discrepency.getOriginalDeferedDays());
		replace.setDeferedCounter(discrepency.getDeferedCounter());
		replace.setDiscrepency(discrepency.getDiscrepency());
		replace.setDiscrepencyRemark(discrepency.getDiscrepencyRemark());
		replace.setDiscrepencyType(discrepency.getDiscrepencyType());
		replace.setDocRemarks(discrepency.getDocRemarks());
		replace.setFacilityList(discrepency.getFacilityList());
		replace.setId(Long.parseLong(trxValueOut.getStagingReferenceID()));
		replace.setNextDueDate(discrepency.getNextDueDate());
		replace.setOriginalTargetDate(discrepency.getOriginalTargetDate());
		replace.setRecDate(discrepency.getRecDate());
		replace.setStatus(discrepency.getStatus());
		replace.setTransactionStatus(discrepency.getStatus());
		replace.setCritical(discrepency.getCritical());
		//replace.setVersionTime(0);
		replace.setWaiveDate(discrepency.getWaiveDate());
		
		return replace;
	}
	
	public OBDiscrepency replaceActual(IDiscrepency discrepency,IDiscrepency stagediscrepency)
	{
		OBDiscrepency replace=new OBDiscrepency();
		replace.setAcceptedDate(discrepency.getAcceptedDate());
		replace.setApprovedBy(discrepency.getApprovedBy());
		replace.setCounter(discrepency.getCounter());
		replace.setCreationDate(discrepency.getCreationDate());
		replace.setCreditApprover(discrepency.getCreditApprover());
		replace.setCustomerId(discrepency.getCustomerId());
		replace.setDeferDate(discrepency.getDeferDate());
		replace.setTotalDeferedDays(discrepency.getTotalDeferedDays());
		replace.setOriginalDeferedDays(discrepency.getOriginalDeferedDays());
		replace.setDeferedCounter(discrepency.getDeferedCounter());
		replace.setDiscrepency(discrepency.getDiscrepency());
		replace.setDiscrepencyRemark(discrepency.getDiscrepencyRemark());
		replace.setDiscrepencyType(discrepency.getDiscrepencyType());
		replace.setDocRemarks(discrepency.getDocRemarks());
		replace.setFacilityList(discrepency.getFacilityList());
		replace.setId(discrepency.getId());
		replace.setNextDueDate(discrepency.getNextDueDate());
		replace.setOriginalTargetDate(discrepency.getOriginalTargetDate());
		replace.setRecDate(discrepency.getRecDate());
		replace.setStatus(discrepency.getStatus());
		replace.setTransactionStatus(stagediscrepency.getStatus()); //replace status by trxstatus for trxstatus in db
		replace.setCritical(discrepency.getCritical());
		//replace.setVersionTime(0);
		replace.setWaiveDate(discrepency.getWaiveDate());
		
		return replace;
	}
	
	// to getDiscrepencyStatus
	private String getDiscrepencyStatus(String discrepencyID) {
		String selectSQL="SELECT STATUS FROM CMS_STAGE_DISCREPENCY "
				+ "WHERE ID=(SELECT STAGING_REFERENCE_ID FROM TRANSACTION "
				+ "WHERE REFERENCE_ID=?) ";
		DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getDiscrepencyStatus() query= "+selectSQL);	
		try {
			List arrList = getJdbcTemplate().query(selectSQL,
					new Object[] { new String(discrepencyID) }, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("STATUS");
				}
			});
			DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getDiscrepencyStatus() arrList= "+arrList);
			if (arrList.size()<1){
				return null;
			}else{
				return (String) arrList.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getDiscrepencyStatus() Exception= "+e);
		}
		return null;
	}
	
	// to getDiscrepencyTransactionStatus
	private String getDiscrepencyTransactionStatus(String discrepencyID) {
		String selectSQL="SELECT TRANSACTION_STATUS FROM CMS_STAGE_DISCREPENCY "
				+ "WHERE ID=(SELECT STAGING_REFERENCE_ID FROM TRANSACTION "
				+ "WHERE REFERENCE_ID=?) ";
		DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getDiscrepencyTransactionStatus() query= "+selectSQL);		
		try {
			List arrList = getJdbcTemplate().query(selectSQL,
					new Object[] { new String(discrepencyID) }, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("TRANSACTION_STATUS");
				}
			});
			DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getDiscrepencyTransactionStatus() arrList= "+arrList);
			if (arrList.size()<1){
				return null;
			}else{
				return (String) arrList.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getDiscrepencyTransactionStatus() Exception= "+e);
		}
		return null;
	}
		
			public OBDiscrepency replaceMainActual(OBDiscrepency discrepency,IDiscrepencyTrxValue trxValueOut)
			{
				OBDiscrepency replace=new OBDiscrepency();
				replace.setAcceptedDate(discrepency.getAcceptedDate());
				replace.setApprovedBy(discrepency.getApprovedBy());
				replace.setCounter(discrepency.getCounter());
				replace.setCreationDate(discrepency.getCreationDate());
				replace.setCreditApprover(discrepency.getCreditApprover());
				replace.setCustomerId(discrepency.getCustomerId());
				replace.setDeferDate(discrepency.getDeferDate());
				replace.setTotalDeferedDays(discrepency.getTotalDeferedDays());
				replace.setOriginalDeferedDays(discrepency.getOriginalDeferedDays());
				replace.setDeferedCounter(discrepency.getDeferedCounter());
				replace.setDiscrepency(discrepency.getDiscrepency());
				replace.setDiscrepencyRemark(discrepency.getDiscrepencyRemark());
				replace.setDiscrepencyType(discrepency.getDiscrepencyType());
				replace.setDocRemarks(discrepency.getDocRemarks());
				replace.setFacilityList(discrepency.getFacilityList());
				replace.setId(discrepency.getId());
				replace.setNextDueDate(discrepency.getNextDueDate());
				replace.setOriginalTargetDate(discrepency.getOriginalTargetDate());
				replace.setRecDate(discrepency.getRecDate());
				replace.setStatus(discrepency.getStatus());
				replace.setTransactionStatus(trxValueOut.getStatus());
				replace.setCritical(discrepency.getCritical());
				//replace.setVersionTime(0);
				replace.setWaiveDate(discrepency.getWaiveDate());
				
				return replace;
				
			}
		public OBDiscrepency replaceMainStage(IDiscrepency discrepency,IDiscrepencyTrxValue trxValueOut)
		{
			OBDiscrepency replace=new OBDiscrepency();
			replace.setAcceptedDate(discrepency.getAcceptedDate());
			replace.setApprovedBy(discrepency.getApprovedBy());
			replace.setCounter(discrepency.getCounter());
			replace.setCreationDate(discrepency.getCreationDate());
			replace.setCreditApprover(discrepency.getCreditApprover());
			replace.setCustomerId(discrepency.getCustomerId());
			replace.setDeferDate(discrepency.getDeferDate());
			replace.setTotalDeferedDays(discrepency.getTotalDeferedDays());
			replace.setOriginalDeferedDays(discrepency.getOriginalDeferedDays());
			replace.setDeferedCounter(discrepency.getDeferedCounter());
			replace.setDiscrepency(discrepency.getDiscrepency());
			replace.setDiscrepencyRemark(discrepency.getDiscrepencyRemark());
			replace.setDiscrepencyType(discrepency.getDiscrepencyType());
			replace.setDocRemarks(discrepency.getDocRemarks());
			replace.setFacilityList(discrepency.getFacilityList());
			replace.setId(Long.parseLong(trxValueOut.getStagingReferenceID()));
			replace.setNextDueDate(discrepency.getNextDueDate());
			replace.setOriginalTargetDate(discrepency.getOriginalTargetDate());
			replace.setRecDate(discrepency.getRecDate());
			replace.setStatus(trxValueOut.getActualDiscrepency().getStatus());
			replace.setTransactionStatus(trxValueOut.getStatus());
			replace.setCritical(discrepency.getCritical());
			//replace.setVersionTime(0);
			replace.setWaiveDate(discrepency.getWaiveDate());
			
			return replace;
			
		}	
	//RAM RAting CR (Deferral Extension) By Santosh
		@Override
		public HashMap insertRAMRatingfile(ArrayList result, RamRatingDetails ramRatingDetails, String fileName,
			String string, Date applicationDate) {

		System.out.println("<<<<<RAM_RATING IN insertRAMRatingfile()>>>>>>" + result.size());	
		ArrayList totalUploadedList = new ArrayList();
		ILimitDAO limitDao = LimitDAOFactory.getDAO();
		Timestamp st = null;
		//String errMsg = "";
		String tempData = "";
		String strArrTemp[] = new String[3];
		List selectCustDetails = new ArrayList();
		
		HashMap retMap = new HashMap();
		
		/*Set<String> riskGrade=new HashSet<String>();
		riskGrade = getRiskGrade();*/
		
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(applicationDate);
		int currentYear = cal.get(cal.YEAR);  
	    int previousYear1=currentYear-1;
	    int previousYear2=previousYear1-1;
	    SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
		try {
			String errorMsg = "";
			String partyStatus = "";
			String lmtProfileId ="";
			String partyId="";
			ILimitProfileTrxValue limitProfileTrxVal = null;
			
			if (result != null && result.size() != 0) {
				System.out.println("<<<<<RAM_RATING insertRAMRatingfile()>>>>>>START" + result.size());
				
/*				for (int index = 0; index < result.size(); index++) {
					HashMap eachDataMap = (HashMap) result.get(index);
					RamRating obj = new RamRating();
					//ArrayList consolidateListMain=new ArrayList();
					
					if (null != eachDataMap.get("COMPANY_CODE")
							&& !"".equalsIgnoreCase(eachDataMap.get("COMPANY_CODE").toString())) {
						obj.setCompany_code(eachDataMap.get("COMPANY_CODE").toString());
						partyId= getPartyId(eachDataMap.get("COMPANY_CODE").toString());
						
						if(null!=partyId && !"".equals(partyId)) {
							obj.setPartyId(partyId);
							//For COMPANY_NAME
							if (null != eachDataMap.get("COMPANY_NAME") && !"".equalsIgnoreCase(eachDataMap.get("COMPANY_NAME").toString())) {
								partyStatus = getPartyStatusByCompanyCode(partyId);
								//check party status
								if (null == partyStatus || "INACTIVE".equals(partyStatus)) {
									errorMsg = "Party Is Inactive In CLIMS,";
									obj.setCompany_name((eachDataMap.get("COMPANY_NAME").toString()));
								} else {
									obj.setCompany_name((eachDataMap.get("COMPANY_NAME").toString()));
								}
								//check limit status
								lmtProfileId = getlimitIdByProfileIdName(partyId);
								ILimitProxy proxy = LimitProxyFactory.getProxy();
								if(null!=lmtProfileId && !"".equals(lmtProfileId)) {
									limitProfileTrxVal = proxy.getTrxLimitProfile(Long.parseLong(lmtProfileId));
									if (!((limitProfileTrxVal.getStatus().equals(ICMSConstant.STATE_ND)) || (limitProfileTrxVal.getStatus().equals(ICMSConstant.STATE_ACTIVE)))) {
										errorMsg =errorMsg+ "CAM is pending for authorization,";
									}
								}
							} else {
								errorMsg = errorMsg + "Company Name is Mandatory Field,";
							}
							//end COMPANY_NAME
							
						}else {
							errorMsg = errorMsg + "Invalid Company Code,";
						}
						
					} else {
						errorMsg = errorMsg + "Company Code is Mandatory Field,";
					}

					if (null != eachDataMap.get("INT_RATING")
							&& !"".equalsIgnoreCase(eachDataMap.get("INT_RATING").toString())) {
						
						if (!riskGrade.contains(String.valueOf(eachDataMap.get("INT_RATING").toString().substring(eachDataMap.get("INT_RATING").toString().length() - 1)))) {
							errorMsg = errorMsg + "Invalid RAM Rating.";
							obj.setInt_rating(eachDataMap.get("INT_RATING").toString());
						} else {	
							obj.setInt_rating(eachDataMap.get("INT_RATING").toString().substring(eachDataMap.get("INT_RATING").toString().length() - 1));
						}

					} else {
						errorMsg = errorMsg + "INT_RATING is Mandatory Field,";
					}

					if (null != eachDataMap.get("COMP_BASEYEAR") && !"".equalsIgnoreCase(eachDataMap.get("COMP_BASEYEAR").toString())) {
						if(eachDataMap.get("COMP_BASEYEAR").toString().equals(String.valueOf(currentYear))
								||eachDataMap.get("COMP_BASEYEAR").toString().equals(String.valueOf(previousYear1))
								||eachDataMap.get("COMP_BASEYEAR").toString().equals(String.valueOf(previousYear2))) {
							obj.setComp_baseyear(eachDataMap.get("COMP_BASEYEAR").toString());
						}else {
							errorMsg = errorMsg + "Invalid RAM Rating Year,";
						}
					}else {
						errorMsg = errorMsg + "Company Base Year is Mandatory Field,";
					}

					if (null != eachDataMap.get("DATE_FINAL")
							&& !"".equalsIgnoreCase(eachDataMap.get("DATE_FINAL").toString())) {
						obj.setDate_final(eachDataMap.get("DATE_FINAL").toString());
					} else {
						errorMsg = errorMsg + "Date of Finalization is Mandatory Field,";
					}

					if (null != eachDataMap.get("PAN_TANNO")
							&& !"".equalsIgnoreCase(eachDataMap.get("PAN_TANNO").toString())) {
						obj.setPan_tanNo(eachDataMap.get("PAN_TANNO").toString());

					}
					

					if (errorMsg.equals("")) {
						obj.setUploadStatus("Y");
						obj.setStatus("PASS");
						obj.setReason(errorMsg);
					} else {
						obj.setUploadStatus("N");
						obj.setStatus("FAIL");
						obj.setReason(errorMsg);

					}
					totalUploadedList.add(obj);
					
					if (obj.getStatus().equals("PASS")) {
						System.out.println("<<<<<RAM_RATING insertRAMRatingfile()>>>>>> PASS() "+formatter1.format(new Date()));
						
						if(null!=lmtProfileId && !"".equals(lmtProfileId)) {
							String checkListId=limitDao.getChecklistId(Long.parseLong(lmtProfileId));
							
							//Start Insert RAM statement for current year and update for previous year
							 
							DateFormat formatter = new SimpleDateFormat("yyyy");
							ILimitDAO limtDao = LimitDAOFactory.getDAO();
							
							String ramRatingYear=obj.getComp_baseyear();
							
							String oldRamRatingYear=limitDao.getOldRAMYear(Long.parseLong(lmtProfileId));
							
							String customerFyClosure=limtDao.getCustomerFyClosure(Long.parseLong(lmtProfileId));
							
							
							cal.setTime(applicationDate);
							cal.add(Calendar.MONTH, 8);
							Date newDate=cal.getTime();
					        String year= formatter.format(newDate);
					        
					        
					        if(ramRatingYear!=null && oldRamRatingYear!=null && !ramRatingYear.equals(oldRamRatingYear)) {
					        	int oldRAMYear=Integer.valueOf(oldRamRatingYear);
					        	int newRAMYear=Integer.valueOf(ramRatingYear);
					        	OBDocumentItem ramRatingChecklist = new OBDocumentItem();
					        	if(newRAMYear>oldRAMYear) {
					        		try {
					        			ramRatingChecklist=limtDao.getAllRamratingDocument();
					        			String docRefId=new SimpleDateFormat("yyyyMMdd").format(new Date())+limtDao.getDocSeqId();
					        			String docId=new SimpleDateFormat("yyyyMMdd").format(new Date())+limtDao.getDocSeqId();
					        			
					        			//Disable old RAM statement where status=Received
					        			limtDao.disableRAMChecklistDetails(checkListId);
					        			//update Ram statement status as Received
					        			limtDao.updateRAMChecklistDetails(checkListId);
					        			//create new Ram statement with pending status
					        			limtDao.insertRAMStatement(customerFyClosure,docId,ramRatingChecklist,checkListId,docRefId,year);
					        		} catch (Exception e) {
					        			e.printStackTrace();
					        		}
					        	}
					        }
					        //End Insert RAM statement for current year and update for previous year
					        System.out.println("<<<<<RAM_RATING insertRAMRatingfile()>>>>>> before Stage and main table update: "+formatter1.format(new Date()));
							//limitDao.updateChecklistDetails(checkListId,new SimpleDateFormat("yyyy").format(applicationDate),obj.getComp_baseyear());
					        updateRamRatingInStageCAMDetails(obj);
							updateRamRatingInCAMDetails(obj);
						}
					}
					//totalUploadedList.add(obj);
				}*/
				//insertRamRatingUploadData(result, fileName, applicationDate);
				
				insertRamRatingUploadData2(result, fileName, applicationDate);
				System.out.println("<<<<<RAM_RATING insertRAMRatingfile()>>>>>>END");
				
				//call procedure to update RAM statement
				IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
				
				System.out.println("SP_BEFORE_UPDATE_RAM_RATING started:");
				jdbc.spBeforeUpdateRAMRating();
				System.out.println("SP_BEFORE_UPDATE_RAM_RATING finished:");
				
				System.out.println("SP_UPDATE_RAM_RATING_UPLOAD started:");
				jdbc.spUpdateRAMRatingUpload();
				System.out.println("SP_UPDATE_RAM_RATING_UPLOAD finished:");
			}

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.getMessage());
			throw new IncompleteBatchJobException("Unable to update retrived record from CSV file");
		}
		//retMap.put("totalUploadedList", totalUploadedList);
		return retMap;
	}

		private int updateRamRatingDocStatus(String checklistId) {
			System.out.println("<<<<<RAM_RATING insertRAMRatingfile()>>>>>>after updateRamRatingDocStatus() "+checklistId);
			DBUtil dbUtil = null;
			ResultSet rs=null;
			int noOfRecords = 0;
			try{
						dbUtil=new DBUtil();
						dbUtil.setSQL("Update cms_checklist_item set status = 'RECEIVED' where checklist_id ='"+checklistId+"' AND STATEMENT_TYPE='RAM_RATING'" );
						if(null!=checklistId) {
							noOfRecords=dbUtil.executeUpdate();
							DefaultLogger.debug(this, "data has been Updated into Table: "+noOfRecords);
						}else {
							DefaultLogger.debug(this, "data has not Updated into Table as checklist ID is null: ");
						}
						//dbUtil.commit();
				}  catch (SQLException e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in updateRamRatingDocStatus:"+e.getMessage());
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in updateRamRatingDocStatus:"+e.getMessage());
				}finally {
					try {
						finalize(dbUtil,rs);
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			return noOfRecords;
			
		}
		
		private void updateRamRatingStageDocStatus(RamRating obj) {

			DBUtil dbUtil = null;
			ResultSet rs=null;
			try{
						dbUtil=new DBUtil();
						dbUtil.setSQL("Update STAGE_CHECKLIST_ITEM set status = 'RECEIVED' where checklist_id = ( select checklist_id from cms_checklist where cms_lsp_lmt_profile_id = (select cms_lsp_lmt_profile_id from SCI_LSP_LMT_PROFILE where llp_le_id ='"+obj.getCompany_name()+"' AND statement_type = 'RAM_RATING'))" );
						int noOfRecords=dbUtil.executeUpdate();
						DefaultLogger.debug(this, "data has been Updated into Table: "+noOfRecords);		
					//	dbUtil.commit();
				}  catch (SQLException e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in updateRamRatingStageDocStatus:"+e.getMessage());
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in updateRamRatingStageDocStatus:"+e.getMessage());
				}finally {
					try {
						finalize(dbUtil,rs);
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
		}

		private void updateRamRatingInCAMDetails(RamRating obj) {
			System.out.println("<<<<<RAM_RATING insertRAMRatingfile()>>>>>> updateRamRatingInCAMDetails()"+obj);
			DBUtil dbUtil = null;
			ResultSet rs=null;
			try{
						dbUtil=new DBUtil();
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
						dbUtil.setSQL("Update SCI_LSP_LMT_PROFILE set RATING_YEAR ='"+obj.getComp_baseyear()+"',CMS_APPR_OFFICER_GRADE ='"+obj.getInt_rating()+"', "
								+"RAM_RATING_FINALIZATION_DATE=TO_DATE('"+DateUtil.formatDate("dd-MMM-yyyy",simpleDateFormat.parse(obj.getDate_final().trim()))+"','DD-MM-YYYY')"+
								"where llp_le_id IN(select lmp_le_id from sci_le_main_profile where cms_le_main_profile_id IN(select cms_le_main_profile_id from sci_le_cri where customer_ram_id ='"+obj.getCompany_code()+"')) and llp_le_id=(SELECT LMP_LE_ID FROM SCI_LE_MAIN_PROFILE WHERE LMP_LONG_NAME ='"+obj.getCompany_name()+"')");

						int noOfRecords=dbUtil.executeUpdate();
						DefaultLogger.debug(this, "data has been Updated into Table: "+noOfRecords);		
						//dbUtil.commit();
				}  catch (SQLException e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in updateRamRatingInCAMDetails:"+e.getMessage());
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in updateRamRatingInCAMDetails:"+e.getMessage());
				}finally {
					try {
						finalize(dbUtil,rs);
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
		}
		
		private void updateRamRatingInStageCAMDetails(RamRating obj) {
			System.out.println("<<<<<RAM_RATING insertRAMRatingfile()>>>>>>after updateRamRatingInStageCAMDetails()"+obj);
			DBUtil dbUtil = null;
			ResultSet rs=null;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

			try{
						dbUtil=new DBUtil();
					//	dbUtil.setSQL("Update STAGE_LIMIT_PROFILE set RATING_YEAR ='"+obj.getComp_baseyear()+"',CMS_APPR_OFFICER_GRADE ='"+obj.getInt_rating().substring(obj.getInt_rating().length() - 1)+"' where llp_le_id='"+obj.getCompany_name()+"'");
	
						dbUtil.setSQL("Update STAGE_LIMIT_PROFILE set RATING_YEAR ='"+obj.getComp_baseyear()+"',CMS_APPR_OFFICER_GRADE ='"+obj.getInt_rating()+"', "
								+"RAM_RATING_FINALIZATION_DATE=TO_DATE('"+DateUtil.formatDate("dd-MMM-yyyy",simpleDateFormat.parse(obj.getDate_final().trim()))+"','DD-MM-YYYY')"+
								"where llp_le_id IN(select lmp_le_id from sci_le_main_profile where cms_le_main_profile_id IN(select cms_le_main_profile_id from sci_le_cri where customer_ram_id ='"+obj.getCompany_code()+"')) and llp_le_id=(SELECT LMP_LE_ID FROM SCI_LE_MAIN_PROFILE WHERE LMP_LONG_NAME ='"+obj.getCompany_name()+"')");

						
						int noOfRecords=dbUtil.executeUpdate();
						DefaultLogger.debug(this, "data has been inserted into Table: "+noOfRecords);		
						//dbUtil.commit();
				}  catch (SQLException e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in updateRamRatingInCAMDetails:"+e.getMessage());
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in updateRamRatingInCAMDetails:"+e.getMessage());
				}finally {
					try {
						finalize(dbUtil,rs);
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
		}

	private void insertRamRatingUploadData(ArrayList consolidateListMain, String fileName, Date applicationDate) {
		System.out.println("<<<<<RAM_RATING insertRAMRatingfile()>>>>>>IN insertRamRatingUploadData(): "+consolidateListMain.size());
		String regionName = null;
		try {
			String sql = "insert into RAM_RATINGUPLOAD_DETAILS VALUES(?,?"
					+ ",(select LMP_SGMNT_CODE_VALUE from SCI_LE_MAIN_PROFILE where LMP_LE_ID =?),?"
					+ ",'',''"
					+ ",(select CMS_APPR_OFFICER_GRADE from SCI_LSP_LMT_PROFILE where llp_le_id = ?)"
					+ ",?"
					+ ",(select RATING_YEAR from SCI_LSP_LMT_PROFILE where llp_le_id = ?)"
					+ ",? ,? ,TO_DATE('"+DateUtil.formatDate("dd-MM-yyyy", applicationDate)+"','DD-MM-YYYY') "
					+ ",TO_DATE('" + DateUtil.formatDate("dd-MM-yyyy", applicationDate)+"','DD-MM-YYYY') "
					+ ",?,?,TO_DATE(?,'DD-MM-YYYY'))";

			final List<RamRating> objectList = consolidateListMain;
			final int batchSize = consolidateListMain.size();
			final String ramFileName = fileName;
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					RamRating obj = objectList.get(i);
					ps.setString(1, obj.getPartyId());
					ps.setString(2, obj.getCompany_name());
					ps.setString(3, obj.getPartyId());
					ps.setString(4, obj.getCompany_code());
					ps.setString(5, obj.getPartyId());
					ps.setString(6, obj.getInt_rating());
					ps.setString(7, obj.getPartyId());
					ps.setString(8, obj.getComp_baseyear());
					ps.setString(9, ramFileName);
					ps.setString(10, obj.getStatus());
					ps.setString(11, obj.getReason());
					ps.setString(12, obj.getDate_final());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
			System.out.println("ramQuery..." + sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
		
		public void insertChecklist(String docId,String checkListId, String limitProfileID,String docRefId,Date applicationDate,String year) {
			DBUtil dbUtil = null;
			ResultSet rs=null;
			if(null==checkListId) {
				checkListId="";
			}
			String sql = "INSERT INTO CMS_CHECKLIST_ITEM (DOC_ITEM_ID,DOC_DESCRIPTION,IS_PRE_APPROVE,IS_INHERITED,IN_VAULT,IN_EXT_CUSTODY,IS_MANDATORY,STATUS,IS_DELETED,CHECKLIST_ID,DOCUMENT_ID,DOCUMENT_CODE,DOC_ITEM_REF,STATEMENT_TYPE,DOCUMENTSTATUS,EXPIRY_DATE,RAMYEAR)" 
				+"VALUES ("+new SimpleDateFormat("yyyymmdd").format(applicationDate)+"000"+docId+",'RAM Rating Statement','N','N','N','N','N','RECEIVED','N','"+checkListId+"','-999999999','DOC20180921000002084',"+docRefId+",'RAM_RATING','ACTIVE',TO_DATE('01-11-"+year+"','DD-MM-YYYY'),'"+year+"')";
			System.out.println("<<<<<RAM_RATING insertRAMRatingfile()>>>>>> insertChecklist()"+sql);
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.executeUpdate();
			//dbUtil.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

			
		}

		
		public String getDocSeqId() {
			DBUtil dbUtil = null;
			ResultSet rs=null;
			String sql = "select CHECKLIST_ITEM_SEQ.nextval S from dual";
			System.out.println("<<<<<RAM_RATING insertRAMRatingfile()>>>>>> getDocSeqId()"+sql);
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
		
		public String getChecklistIdByLimitId(String category, String limitID){
			String sql="select checklist_id from CMS_CHECKLIST where category='"+category+"' and CMS_LSP_LMT_PROFILE_ID ='"+limitID+"'";
			System.out.println("<<<<<RAM_RATING insertRAMRatingfile()>>>>>> getChecklistIdByLimitId() "+sql);
			DefaultLogger.debug(this, "sql:"+sql);
			DBUtil dbUtil = null;
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
		
		public String getlimitIdByProfileId(String profileId){
			String sql="select cms_lsp_lmt_profile_id from SCI_LSP_LMT_PROFILE  where llp_le_id='"+profileId+"'";
			System.out.println("<<<<<RAM_RATING insertRAMRatingfile()>>>>>> getlimitIdByProfileId() "+sql);
			DefaultLogger.debug(this, "sql:"+sql);
			DBUtil dbUtil = null;
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
		
		
		public String getlimitIdByProfileIdName(String partyId){
			//String sql="select cms_lsp_lmt_profile_id from SCI_LSP_LMT_PROFILE  where llp_le_id=(select LMP_LE_ID  from SCI_LE_MAIN_PROFILE where  LMP_LONG_NAME ='"+profileId.trim()+"')";
			
			/*String sql="select cms_lsp_lmt_profile_id from SCI_LSP_LMT_PROFILE where llp_le_id='"+partyId+"'";
			
			System.out.println("<<<<<RAM_RATING insertRAMRatingfile()>>>>>> getlimitIdByProfileIdName() "+sql);
			DefaultLogger.debug(this, "sql:"+sql);
			DBUtil dbUtil = null;
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
			DefaultLogger.debug(this, "Exception in getlimitIdByProfileIdName");
			}finally {
			try {
				finalize(dbUtil, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				DefaultLogger.debug(this, "Exception in getlimitIdByProfileIdName");
				}
			}
			return checkListId;*/
			
			String selectSQL="select CMS_LSP_LMT_PROFILE_ID from SCI_LSP_LMT_PROFILE where llp_le_id=?";
			System.out.println("<<<<<RAM_RATING insertRAMRatingfile()>>>>>> getlimitIdByProfileIdName() "+selectSQL);
			try {
				List arrList = getJdbcTemplate().query(selectSQL,
						new Object[] { new String(partyId.trim()) }, new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("CMS_LSP_LMT_PROFILE_ID");
					}
				});
				if (arrList.size()<1){
					return null;
				}else{
					return (String) arrList.get(0);
				}
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("<<<<<Exception RAM_RATING getlimitIdByProfileIdName()>>>>>>"+e);
			}
			return null;
			
		}
		
		public int getRecordAvailableForYear(String checklistid, String ramYear) {
			if(null == checklistid) {
				checklistid= "";
			}
			String sql="select count(*) from CMS_CHECKLIST_ITEM where checklist_id = '"+checklistid+"' and RAMYEAR ="+ramYear;
			System.out.println("<<<<<RAM_RATING insertRAMRatingfile()>>>>>> getRecordAvailableForYear()"+sql);
			DefaultLogger.debug(this, "sql:"+sql);
			DBUtil dbUtil = null; //DOCUMENT_CODE
			ResultSet rs;
			int isRecordAvailable=0;
			try {
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				rs=dbUtil.executeQuery();
				while(rs.next()){
					isRecordAvailable = rs.getInt(1);
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
			return isRecordAvailable;
		}
		
	public void spUpdateRAMRatingUpload() {
		try {

			getJdbcTemplate().execute("{call " + getSpUpdateRAMRatingUploadActual() + "}",
					new CallableStatementCallback() {
						public Object doInCallableStatement(CallableStatement cs)
								throws SQLException, DataAccessException {
							cs.executeUpdate();
							return null;
						}
					});
		} catch (Exception ex) {
			DefaultLogger.debug(this, " exception in spUpdateRAMRatingUpload: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void spBeforeUpdateRAMRating() {
		try {

			getJdbcTemplate().execute("{call " + getSpBeforeUpdateRAMRating() + "}",
					new CallableStatementCallback() {
						public Object doInCallableStatement(CallableStatement cs)
								throws SQLException, DataAccessException {
							cs.executeUpdate();
							return null;
						}
					});
		} catch (Exception ex) {
			DefaultLogger.debug(this, " exception in spBeforeUpdateRAMRating: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private void insertRamRatingUploadData2(ArrayList consolidatedList, String fileName, Date applicationDate) {
		System.out.println("<<<<<RAM_RATING insertRAMRatingfile()>>>>>>IN insertRamRatingUploadData2(): "+consolidatedList.size());
		String regionName = null;
		try {
			String sql = "insert into RAM_RATINGUPLOAD_DETAILS (PARTY_NAME,CUST_RAM_ID,RAM_RATING_NEW,RAM_RATING_YEAR_NEW,RAM_RATING_FINALIZATION_DATE,RAM_UPLOAD_FILE_NAME,DATE_REQUEST,ID)" + 
					"   values(?,?,?,?,?,?,to_timestamp(SYSDATE),RAM_RATINGUPLOAD_DETAILS_SEQ.nextval)";
			final List objectList = consolidatedList;
			final String ramFileName = fileName;
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					HashMap obj = (HashMap) objectList.get(i);
					ps.setString(1, (null!=obj.get("COMPANY_NAME"))?obj.get("COMPANY_NAME").toString():"");
					ps.setString(2, (null!=obj.get("COMPANY_CODE"))?obj.get("COMPANY_CODE").toString():"");
					ps.setString(3, (null!=obj.get("INT_RATING"))?obj.get("INT_RATING").toString():"");
					ps.setString(4, (null!=obj.get("COMP_BASEYEAR"))?obj.get("COMP_BASEYEAR").toString():"");
					ps.setString(5, (null!=obj.get("DATE_FINAL"))?obj.get("DATE_FINAL").toString():"");
					ps.setString(6, ramFileName);
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
			System.out.println("ramQuery..." + sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public Map getSystemUploadFileNameAndUploadTime(String stagingReferenceId){
		String sql =  "SELECT FILENAME,UPLOADTIME FROM STAGE_FILE_UPLOAD WHERE ID = ? ";
		
		DefaultLogger.debug(this, "::: Inside FileUploadJdbcImpl : getSystemUploadFileNameAndUploadTime :: stagingReferenceId:"+stagingReferenceId);
		System.out.println("::: Inside FileUploadJdbcImpl : getSystemUploadFileNameAndUploadTime :: stagingReferenceId:"+stagingReferenceId);

		List<Object> param = new ArrayList<Object>();
		param.add(stagingReferenceId);
		
		try{
			return (HashMap) getJdbcTemplate().query(sql, param.toArray(), new ResultSetExtractor() {
				public HashMap extractData(ResultSet rs) throws SQLException, DataAccessException {
					
					HashMap systemFileUploadMap = new HashMap();
					while(rs.next()) {
						systemFileUploadMap.put("FILENAME", rs.getString("FILENAME"));
						systemFileUploadMap.put("UPLOADTIME", rs.getTimestamp("UPLOADTIME"));
					}
					return systemFileUploadMap;
				}
			});
		}
		catch (Exception e) {
			DefaultLogger.debug(this, " exception in getSystemUploadFileNameAndUploadTime: "+e.getMessage());
			System.out.println("exception in getSystemUploadFileNameAndUploadTime: "+e.getMessage());
        	e.printStackTrace();
			return null;
		}
	}
	
	public Set<String> getAlreadyDownloadedFileNamesByBatch(String fileType, Date batchdate){
		String sql =  "SELECT distinct FILENAME from CMS_FILE_UPLOAD where FILETYPE = ? AND Trunc(UPLOADTIME) = trunc(TO_DATE(?,'DD-MM-YYYY')) ";
		
		DefaultLogger.debug(this, "::: Inside FileUploadJdbcImpl : getAlreadyDownloadedFileNamesByBatch :: param:"+fileType+"\t"+batchdate);
		System.out.println("::: Inside FileUploadJdbcImpl : getAlreadyDownloadedFileNamesByBatch :: param:"+fileType+"\t"+batchdate);

		List<Object> param = new ArrayList<Object>();
		param.add(fileType);
		if(null != batchdate) {
			param.add(DateUtil.formatDate("dd-MM-yyyy",batchdate));
		}
		else {
			param.add(batchdate);
		}
		
		try{
			return (Set<String>) getJdbcTemplate().query(sql, param.toArray(), new ResultSetExtractor() {
				public Set<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
					
					Set<String> downloadedFilesSet = new HashSet<String>();
					while(rs.next()) {
						String fileName = rs.getString("FILENAME");
						downloadedFilesSet.add(fileName);
					}
					DefaultLogger.debug(this, "::: getAlreadyDownloadedFileNamesByBatch :: downloadedFilesSet is :: "+downloadedFilesSet);
					System.out.println("::: getAlreadyDownloadedFileNamesByBatch :: downloadedFilesSet is :: "+downloadedFilesSet);
					return downloadedFilesSet;
				}
			});
		}
		catch (Exception e) {
			DefaultLogger.debug(this, " exception in getAlreadyDownloadedFileNamesByBatch: "+e.getMessage());
			System.out.println("exception in getAlreadyDownloadedFileNamesByBatch: "+e.getMessage());
        	e.printStackTrace();
			return null;
		}
	}
	
	public int getAcknowledgmentUplodCount() throws FileUploadException {
		List resultList = null;
		int count=0;
		try {
			String SQL=CHECK_ACKNOWLEDGMENT_UPLOAD_TRX+" AND (status LIKE 'PENDING%' OR STATUS LIKE 'REJECTED')";
			count=getJdbcTemplate().queryForInt(SQL);			

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- Checkling CERSAI ACKNOWLEDGMENT File Transaction in FileUploadJdbcImpl");
		}

		return count;
	}
	
	
	public int getLeiDetailsUplodCount() throws FileUploadException {
		List resultList = null;
		int count=0;
		try {
			String SQL=CHECK_LEIDETAILS_UPLOAD_TRX+" AND (status LIKE 'PENDING%' OR STATUS LIKE 'REJECTED')";
			count=getJdbcTemplate().queryForInt(SQL);			

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- Checkling LEI Details File Transaction in FileUploadJdbcImpl");
		}

		return count;
	}
	
	public void createEntireAcknowledgmentStageFile(List objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO STAGE_ACKNOWLEDGMENT_UPLOAD " +
		"(ID, FILE_ID, SECURITY_ID, CERSAI_TRX_REF_NO, CERSAI_SECURITY_INTEREST_ID, CERSAI_ASSET_ID, DATE_CERSAI_REG, STATUS,REASON,UPLOAD_STATUS)" +
		" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ?)";

		final List<OBAcknowledgmentFile> objectList = objList;
		final int batchSize = objList.size();
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBAcknowledgmentFile acknowledgmentFile = objectList.get(i);
					ps.setLong(1,acknowledgmentFile.getFileId());
					ps.setString(2,acknowledgmentFile.getSecurityID());
					ps.setString(3,acknowledgmentFile.getCerTrnRefNo());
					ps.setString(4,acknowledgmentFile.getCerSecIntID());
					ps.setString(5,acknowledgmentFile.getCerAssetID());
					if(null==acknowledgmentFile.getRegistrationDate()){
						ps.setNull(6,Types.TIMESTAMP);
					}
					else{
						ps.setTimestamp(6,new Timestamp(acknowledgmentFile.getRegistrationDate().getTime()));
						}
					ps.setString(7,acknowledgmentFile.getStatus());
					ps.setString(8,acknowledgmentFile.getReason());
					ps.setString(9,acknowledgmentFile.getUploadStatus());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
		//}
	}
	
	public void createEntireLeiDetailsStageFile(List objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO STAGE_LEI_DETAILS_UPLOAD " +
		"(ID, FILE_ID, PARTY_ID, LEI_CODE, LEI_EXP_DATE, STATUS,REASON,UPLOAD_STATUS)" +
		" VALUES (STAGE_LEI_DETAILS_UPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?)";

		final List<OBLeiDetailsFile> objectList = objList;
		final int batchSize = objList.size();
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBLeiDetailsFile leiDetailsFile = objectList.get(i);
					ps.setLong(1,leiDetailsFile.getFileId());
					ps.setString(2,leiDetailsFile.getPartyId());
					ps.setString(3,leiDetailsFile.getLeiCode());
					if(null==leiDetailsFile.getLeiExpDate()){
						ps.setNull(4,Types.TIMESTAMP);
					}
					else{
						ps.setTimestamp(4,new Timestamp(leiDetailsFile.getLeiExpDate().getTime()));
						}
					ps.setString(5,leiDetailsFile.getStatus());
					ps.setString(6,leiDetailsFile.getReason());
					ps.setString(7,leiDetailsFile.getUploadStatus());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
		//}
	}
	
	public void createEntireLeiDetailsActualFile(List objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO ACTUAL_LEI_DETAILS_UPLOAD " +
		"(ID, FILE_ID, PARTY_ID, LEI_CODE, LEI_EXP_DATE, STATUS,REASON,UPLOAD_STATUS,CHECKER_DATE)" +
		" VALUES (ACTUAL_LEI_DETAILS_UPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?,?,?)";

		final Date systemDate = DateUtil.getDate();
		final List<OBLeiDetailsFile> objectList = objList;
		final int batchSize = objList.size();
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBLeiDetailsFile leiDetailsFile = objectList.get(i);
					ps.setLong(1,leiDetailsFile.getFileId());
					ps.setString(2,leiDetailsFile.getPartyId());
					ps.setString(3,leiDetailsFile.getLeiCode());
					if(null==leiDetailsFile.getLeiExpDate()){
						ps.setNull(4,Types.TIMESTAMP);
					}
					else{
						ps.setTimestamp(4,new Timestamp(leiDetailsFile.getLeiExpDate().getTime()));
						}
					ps.setString(5,leiDetailsFile.getStatus());
					ps.setString(6,leiDetailsFile.getReason());
					ps.setString(7,leiDetailsFile.getUploadStatus());
					ps.setTimestamp(8,new Timestamp(systemDate.getTime()));
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
		//}
	}
	
	public void createEntireAcknowledgmentActualFile(List objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO ACTUAL_ACKNOWLEDGMENT_UPLOAD " +
		"(ID, FILE_ID, SECURITY_ID, CERSAI_TRX_REF_NO, CERSAI_SECURITY_INTEREST_ID, CERSAI_ASSET_ID, DATE_CERSAI_REG, STATUS,REASON,UPLOAD_STATUS)" +
		" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ?)";

		final List<OBAcknowledgmentFile> objectList = objList;
		final int batchSize = objList.size();
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBAcknowledgmentFile acknowledgmentFile = objectList.get(i);
					ps.setLong(1,acknowledgmentFile.getFileId());
					ps.setString(2,acknowledgmentFile.getSecurityID());
					ps.setString(3,acknowledgmentFile.getCerTrnRefNo());
					ps.setString(4,acknowledgmentFile.getCerSecIntID());
					ps.setString(5,acknowledgmentFile.getCerAssetID());
					if(null==acknowledgmentFile.getRegistrationDate()){
						ps.setNull(6,Types.TIMESTAMP);
					}
					else{
						ps.setTimestamp(6,new Timestamp(acknowledgmentFile.getRegistrationDate().getTime()));
						}
					ps.setString(7,acknowledgmentFile.getStatus());
					ps.setString(8,acknowledgmentFile.getReason());
					ps.setString(9,acknowledgmentFile.getUploadStatus());
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
		//}
	}

	public void updateAcknowledgmentSecurity(final OBAcknowledgmentFile acknowledgmentFile)throws FileUploadException,SQLException{
        getJdbcTemplate().execute(updateSecurityStatus1,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	            
        		ps.setString(1,acknowledgmentFile.getCerTrnRefNo());	
				ps.setString(2,acknowledgmentFile.getCerSecIntID());
				ps.setString(3,acknowledgmentFile.getCerAssetID());
				ps.setDate(4,acknowledgmentFile.getRegistrationDate());
				ps.setString(5,acknowledgmentFile.getSecurityID());  
                     
                return ps.execute();  
                      
            }  
           });          			
}
	
	public void updateLeiDetailsSecurity(final OBLeiDetailsFile obLeiDetailsFile)throws FileUploadException,SQLException{
		            getJdbcTemplate().execute(updateSecurityStatus1,new PreparedStatementCallback(){  
		            
		            	public Object doInPreparedStatement(PreparedStatement ps)  
		            	           throws SQLException {  
		            	            
		            		ps.setString(1,obLeiDetailsFile.getPartyId());	
							ps.setString(2,obLeiDetailsFile.getLeiCode());
							ps.setDate(4,obLeiDetailsFile.getLeiExpDate());
		                         
		                    return ps.execute();  
		                          
		                }  
		               });          			
	}	
	
	public void insertAcknowledgmentStageSecurity(final OBAcknowledgmentFile acknowledgmentFile)throws FileUploadException,SQLException{
		Object id = acknowledgmentFile.getSecurityID();
		String SQL = CMS_STAGE_ACKNOWLEDGMENT_FILE+id+"'";
		List<Map<String,Object>> rows =  getJdbcTemplate().queryForList(SQL);
		if(rows.size()==1)
		{
			getJdbcTemplate().execute(updateStageSecurityStatus1,new PreparedStatementCallback(){  
	            
            	public Object doInPreparedStatement(PreparedStatement ps)  
            	           throws SQLException {  
            	            
            		ps.setString(1,acknowledgmentFile.getCerTrnRefNo());	
					ps.setString(2,acknowledgmentFile.getCerSecIntID());
					ps.setString(3,acknowledgmentFile.getCerAssetID());
					ps.setDate(4,acknowledgmentFile.getRegistrationDate());
					ps.setString(5,acknowledgmentFile.getSecurityID());  
                         
                    return ps.execute();  
                          
                }  
               });  
		}
		else
		{
		
		String insertSecurityStageStatus = "INSERT INTO CMS_STAGE_SECURITY " +
    			"(CMS_COLLATERAL_ID, CERSAI_TRX_REF_NO, CERSAI_SECURITY_INTEREST_ID, CERSAI_ASSET_ID, DATE_CERSAI_REGISTERATION )" +
    			" VALUES ( ?, ? ,?, ?, ?)";
		getJdbcTemplate().execute(insertSecurityStageStatus,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	              
        		ps.setString(1,acknowledgmentFile.getSecurityID());	
        		ps.setString(2,acknowledgmentFile.getCerTrnRefNo()); 
				ps.setString(3,acknowledgmentFile.getCerSecIntID());
				ps.setString(4,acknowledgmentFile.getCerAssetID());
				ps.setDate(5,acknowledgmentFile.getRegistrationDate());
				
                return ps.execute();  
                      
            }  
           }); 
		}
}	
	
	public void insertLeiDetailsStageSecurity(final OBLeiDetailsFile leiDetailsFile)throws FileUploadException,SQLException{
		Object id = leiDetailsFile.getId();
		String SQL = CMS_STAGE_LEIDETAILS_FILE+id+"'";
		List<Map<String,Object>> rows =  getJdbcTemplate().queryForList(SQL);
		if(rows.size()==1)
		{
			getJdbcTemplate().execute(updateStageSecurityStatus1,new PreparedStatementCallback(){  
	            
            	public Object doInPreparedStatement(PreparedStatement ps)  
            	           throws SQLException {  
            		ps.setObject(1,leiDetailsFile.getFileId());	
					ps.setString(2,leiDetailsFile.getPartyId());
					ps.setString(3,leiDetailsFile.getLeiCode());
					ps.setDate(4,leiDetailsFile.getLeiExpDate()); 
                         
                    return ps.execute();  
                          
                }  
               });  
		}
		else
		{
		
		String insertSecurityStageStatus = "INSERT INTO STAGE_LEI_DETAILS_UPLOAD " +
    			"(FILE_ID, PARTY_ID, LEI_CODE, LEI_EXP_DATE )" +
    			" VALUES ( ? ,?, ?, ?)";
		getJdbcTemplate().execute(insertSecurityStageStatus,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        		ps.setObject(1,leiDetailsFile.getFileId());	
				ps.setString(2,leiDetailsFile.getPartyId());
				ps.setString(3,leiDetailsFile.getLeiCode());
				ps.setDate(4,leiDetailsFile.getLeiExpDate()); 
				
                return ps.execute();  
                      
            }  
           }); 
		}
}
	
	
public void createRejectAcknowledgmentActualFile(final OBAcknowledgmentFile acknowledgmentFile)throws FileUploadException,SQLException{
	String sql = "INSERT INTO ACTUAL_ACKNOWLEDGMENT_UPLOAD " +
			"(ID, FILE_ID, SECURITY_ID, CERSAI_TRX_REF_NO, CERSAI_SECURITY_INTEREST_ID, CERSAI_ASSET_ID, DATE_CERSAI_REG, STATUS,REASON,UPLOAD_STATUS)" +
			" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ?)";
		getJdbcTemplate().execute(sql,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        		
        		ps.setLong(1,acknowledgmentFile.getFileId());    
        		ps.setString(2,acknowledgmentFile.getSecurityID());	
        		ps.setString(3,acknowledgmentFile.getCerTrnRefNo()); 
				ps.setString(4,acknowledgmentFile.getCerSecIntID());
				ps.setString(5,acknowledgmentFile.getCerAssetID());
				ps.setDate(6,acknowledgmentFile.getRegistrationDate());
				ps.setString(7,acknowledgmentFile.getStatus());
				ps.setString(8,acknowledgmentFile.getReason());
				ps.setString(9,acknowledgmentFile.getUploadStatus());
                return ps.execute();  
                      
            }  
           });          			
}	

public void createRejectLeiDetailsActualFile(final OBLeiDetailsFile obLeiDetailsFile)throws FileUploadException,SQLException{
	String sql = "INSERT INTO ACTUAL_LEI_DETAILS_UPLOAD " +
			"(ID, FILE_ID, PARTY_ID, LEI_CODE, LEI_EXP_DATE, STATUS,REASON,UPLOAD_STATUS,CHECKER_DATE)" +
			" VALUES (ACTUAL_LEI_DETAILS_UPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?,?,?)";
	
	    final Date systemDate=DateUtil.getDate();
		getJdbcTemplate().execute(sql,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        		
        		ps.setLong(1,obLeiDetailsFile.getFileId());    
        		ps.setString(2,obLeiDetailsFile.getPartyId());	
        		ps.setString(3,obLeiDetailsFile.getLeiCode()); 
				ps.setDate(4,obLeiDetailsFile.getLeiExpDate());
				ps.setString(5,obLeiDetailsFile.getStatus());
				ps.setString(6,obLeiDetailsFile.getReason());
				ps.setString(7,obLeiDetailsFile.getUploadStatus());
				ps.setTimestamp(8,new Timestamp(systemDate.getTime()));
				return ps.execute();  
                      
            }  
           });          			
}


	public ArrayList<OBAcknowledgmentFile> getAllCMSSecurityFile(Object id) throws FileUploadException,SQLException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		ArrayList<OBAcknowledgmentFile> resultList = new ArrayList<OBAcknowledgmentFile>();
		List<Map<String,Object>> rows ;
		String regex = "\\d+";
		String sid=(String) id;
		if(id == null) {
			id="";
		}else if(!sid.matches(regex)) {
			id="";
		}
			String SQL=STAGE_ACKNOWLEDGMENT_FILE+id+"'";
			rows =  getJdbcTemplate().queryForList(SQL);
			if(rows.size()==0)
			{
				OBAcknowledgmentFile obj=new OBAcknowledgmentFile();
				obj.setUploadStatus("N");
				obj.setReason("Invalid Security Id.");
				obj.setStatus("FAIL");	
				resultList.add(obj);
			}
			else
			{
			for (Map<String,Object> row: rows) {
				OBAcknowledgmentFile obj = new OBAcknowledgmentFile();
				obj.setSecurityID(String.valueOf(row.get("CMS_COLLATERAL_ID")));
				obj.setCerTrnRefNo(String.valueOf(row.get("CERSAI_TRX_REF_NO")));
				obj.setCerSecIntID(String.valueOf(row.get("CERSAI_SECURITY_INTEREST_ID")));
				obj.setCerAssetID(String.valueOf(row.get("CERSAI_ASSET_ID")));
					try {
						if(null!=row.get("DATE_CERSAI_REGISTERATION") && !row.get("DATE_CERSAI_REGISTERATION").toString().trim().isEmpty()) {
						    obj.setRegistrationDate(new java.sql.Date((simpleDateFormat.parse(row.get("DATE_CERSAI_REGISTERATION").toString().trim()).getTime())));
						}
						else {
							obj.setRegistrationDate(null);
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// TODO Auto-generated catch block
				
				
				resultList.add(obj);
			}
		} 
		
		return resultList;
	}
	
	
	public ArrayList<OBLeiDetailsFile> getAllCMSLeiSecurityFile(Object id) throws FileUploadException,SQLException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		ArrayList<OBLeiDetailsFile> resultList = new ArrayList<OBLeiDetailsFile>();
		List<Map<String,Object>> rows ;
		String regex = "\\d+";
		String sid=(String) id;
		if(id == null) {
			id="";
		}else if(!sid.matches(regex)) {
			id="";
		}
			String SQL=STAGE_LEIDETAILS_FILE+id+"'";
			rows =  getJdbcTemplate().queryForList(SQL);
			if(rows.size()==0)
			{
				OBLeiDetailsFile obj=new OBLeiDetailsFile();
				obj.setUploadStatus("N");
				obj.setReason("Invalid Id");
				obj.setStatus("FAIL");	
				resultList.add(obj);
			}
			else
			{
			for (Map<String,Object> row: rows) {
				OBLeiDetailsFile obj = new OBLeiDetailsFile();
				obj.setFileId(Long.parseLong(row.get("FILE_ID").toString()));
				obj.setPartyId(String.valueOf(row.get("PARTY_ID")));
				obj.setLeiCode(String.valueOf(row.get("LEI_CODE")));
					try {
						if(null!=row.get("LEI_EXP_DATE") && !row.get("LEI_EXP_DATE").toString().trim().isEmpty()) {
						    obj.setLeiExpDate(new java.sql.Date((simpleDateFormat.parse(row.get("LEI_EXP_DATE").toString().trim()).getTime())));
						}
						else {
							obj.setLeiExpDate(null);
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// TODO Auto-generated catch block
				
				
				resultList.add(obj);
			}
		} 
		
		return resultList;
	}
	
	public HashMap insertAcknowledgmentfile( ArrayList result,UploadAcknowledgmentFileCmd cmd,String fileName, ICommonUser user,Date date,ConcurrentMap<String, String> dataFromCacheView ,ConcurrentMap<String, String> dataFromUpdLineFacilityMV) {

		List totalUploadedList= new ArrayList();
		List errorList= new ArrayList();
		Timestamp st = null;
		String errMsg="";
		String tempData="";
		String strArrTemp[]=new String[3];
		List selectCustDetails=new ArrayList();
		String partyStatus="";
		HashMap retMap = new HashMap();
		DefaultLogger.debug(this, "##########3###########In FileUploadJdbcImpl insertUploadCersaiAcknowledgmentFile ##### line no 220#######:: ");
		try {
			if(result!=null&& result.size() != 0)
			{
				for (int index = 0; index < result.size(); index++) {
					HashMap eachDataMap = (HashMap) result.get(index);
					OBUbsFile obj=new OBUbsFile();
					obj.setCurrency((String) eachDataMap.get("CURRENCY_CODE"));
					if(null!=eachDataMap.get("CUSTOMER_ID")&& !"".equalsIgnoreCase(eachDataMap.get("CUSTOMER_ID").toString()) )
					{
						obj.setCustomer(eachDataMap.get("CUSTOMER_ID").toString());
					}
					if(null!=eachDataMap.get("LINE_NO")&& !"".equalsIgnoreCase(eachDataMap.get("LINE_NO").toString()) )
					{
						obj.setLine(eachDataMap.get("LINE_NO").toString());
					}
					
					if(null!=eachDataMap.get("LIMIT_AMOUNT")&& !"".equalsIgnoreCase(eachDataMap.get("LIMIT_AMOUNT").toString()) )
					{
						obj.setLimit(new Double(Double.parseDouble(eachDataMap.get("LIMIT_AMOUNT").toString())));
					}
					
					if(null!=eachDataMap.get("SR_NO")&& !"".equalsIgnoreCase(eachDataMap.get("SR_NO").toString()) )
					{
						obj.setSerialNo(eachDataMap.get("SR_NO").toString());
					}
					
					if(null!=eachDataMap.get("UTILIZATION_AMOUNT")&& !"".equalsIgnoreCase(eachDataMap.get("UTILIZATION_AMOUNT").toString()) )
					{
						obj.setUtilize(new Double(Double.parseDouble(eachDataMap.get("UTILIZATION_AMOUNT").toString())));
						
					}
					obj.setUploadStatus("Y");
					OBCommonFile commonObj = obj;
//					partyStatus=getPartyStatus(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()),"UBS-LIMITS");
					// Commented and added By Mukesh Mohapatra || UBS_UPLOAD_OPTIMIZATION |Starts
					//partyStatus=getPartyStatusForUBS(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()));
					partyStatus = getPartyStatusForUBSUpload(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()),dataFromCacheView,"UBS-LIMITS");
					// Commented and added By Mukesh Mohapatra  || UBS_UPLOAD_OPTIMIZATION |Ends
					if((!partyStatus.equals("")&& "ACTIVE".equals(partyStatus)) || (!partyStatus.equals("")&& "INACTIVE".equals(partyStatus))){
						commonObj=updateLmtUtilAmtStage(obj,"ACKNOWLEDGMENT-LIMITS",dataFromUpdLineFacilityMV);
					}
					else if(partyStatus.equals("")){
						commonObj=updateLmtUtilAmtStage(obj,"ACKNOWLEDGMENT-LIMITS",dataFromUpdLineFacilityMV);
						errMsg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+obj.getCustomer()+","+obj.getLine()+","+obj.getSerialNo()+","+obj.getCurrency()+") Available in UBS not in CLIMS.";
					}
					totalUploadedList.add(commonObj);
				}
			}
			
			//To Do :::: Below functionality has been implemented for UBS-LIMITS file upload only. This needs to be implemented for Other Upload files. 
			//Also for inserting data to actual tables.
			int batchSize = 200;
			for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
				List<OBCommonFile> batchList = totalUploadedList.subList(j, j + batchSize > totalUploadedList.size() ? totalUploadedList.size() : j + batchSize);
				updateXrefStageAmount(batchList,"ACKNOWLEDGMENT-LIMITS");
			}
			
			st = new Timestamp(System.currentTimeMillis());
			selectCustDetails=selectCustDetails("AcknowledgmentFile");
			for(int i=0;i<selectCustDetails.size();i++)
			{				
				tempData=selectCustDetails.get(i).toString();
				strArrTemp=tempData.split(ICMSConstant.FILEUPLOAD_SEPERATOR);
				errMsg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+selectCustDetails.get(i)+")Available in CLIMS not in UBS.";
				OBAcknowledgmentFile obAcknowledgment=new OBAcknowledgmentFile();
				//obUbs.setCurrency("");					
				obAcknowledgment.setCustomer(strArrTemp[0]);
				obAcknowledgment.setLine(strArrTemp[1]);
				obAcknowledgment.setSerialNo(strArrTemp[2]);
				obAcknowledgment.setCurrency(strArrTemp[3].trim());
				obAcknowledgment.setStatus("FAIL");
				obAcknowledgment.setReason(errMsg);
				obAcknowledgment.setUploadStatus("N");
				errorList.add(obAcknowledgment);
			}
		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.getMessage());
			throw new IncompleteBatchJobException(
			"Unable to update retrived record from Cersai Acknowledgment file");
		}
		getJdbcTemplate().update(updateStatusActualAcknowledgment);
		retMap.put("totalUploadedList", totalUploadedList);
		retMap.put("errorList", errorList);
		return retMap;
	}

	// RELEASE LINE DETAILS BY PRACHIT
	
	
	public class ReleaselinedetailsFileRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBReleaselinedetailsFile result = new OBReleaselinedetailsFile();
			result.setId(rs.getLong("ID"));
			result.setFileId(rs.getLong("FILE_ID"));
			result.setSystemID(rs.getString("SYSTEM_ID"));
			result.setLineNo(rs.getString("LINE_NO"));
			result.setSerialNo(rs.getString("SERIAL_NO"));
			result.setLiabBranch(rs.getString("LIAB_BRANCH"));
			result.setReleaseAmount(rs.getString("RELEASED_AMOUNT"));
			result.setStatus(rs.getString("STATUS"));
			result.setReason(rs.getString("REASON"));
			result.setUploadStatus(rs.getString("UPLOAD_STATUS"));
			result.setSourceRefNo(rs.getString("SOURCE_REF_NO"));
			result.setExpDate(rs.getString("EXP_DATE"));
			result.setPslFlag(rs.getString("PSL_FLAG"));
			result.setPslValue(rs.getString("PSL_VALUE"));
			result.setRuleID(rs.getString("RULE_ID"));
			return result;
		}
	}
	
	public class facilitydetailsFileRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBFacilitydetailsFile result = new OBFacilitydetailsFile();
			result.setId(rs.getLong("ID"));
			result.setFileId(rs.getLong("FILE_ID"));
			result.setFacilityID(rs.getString("FACILITY_ID"));
			result.setSanctionAmt(rs.getString("SANCTION_AMOUNT"));
			result.setSanctionAmtInr(rs.getString("SANCTION_AMOUNT_INR"));
			result.setReleasableAmt(rs.getString("RELEASABLE_AMOUNT"));
			result.setStatus(rs.getString("STATUS"));
			result.setReason(rs.getString("REASON"));
			result.setUploadStatus(rs.getString("UPLOAD_STATUS"));
			return result;
		}
	}
	
	@Override
	public SearchResult getAllReleaselinedetailsFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=STAGE_UBS_RLD_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new ReleaselinedetailsFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving UBS Releaselinedetails File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	@Override
	public SearchResult getAllActualReleaselinedetailsFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=ACTUAL_UBS_RLD_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new ReleaselinedetailsFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving UBS Releaselinedetails  File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public static boolean validateAmount(String str) 
    { 
		
       // String regex = "[0-9]+\\.[0-9]*"; 
		String regex = "[0-9]+[\\.]?[0-9]*";
        Pattern p = Pattern.compile(regex); 
        if (str == null) { 
            return false; 
        }else if(str.contains("E")) {
        	return true;
        }else{
        	Matcher m = p.matcher(str); 
        	return m.matches(); 
        }
        
    } 

	@Override
	public void createEntireReleaselinedetailsStageFile(List<OBReleaselinedetailsFile> objList)
			throws FileUploadException, SQLException {
		String sql = "INSERT INTO STAGE_RELEASELINEDET_UPLOAD " +
				"(ID, FILE_ID, SYSTEM_ID,LINE_NO,SERIAL_NO,LIAB_BRANCH,RELEASED_AMOUNT,STATUS,REASON,UPLOAD_STATUS,SOURCE_REF_NO,EXP_DATE,PSL_FLAG,PSL_VALUE,RULE_ID)" +
				" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ?,?,?,?,?,?)";

				final List<OBReleaselinedetailsFile> objectList = objList;
				final int batchSize = objList.size();
					getJdbcTemplate().batchUpdate(sql,
							new BatchPreparedStatementSetter() {
						public void setValues(PreparedStatement ps, int i)
						throws SQLException {
							OBReleaselinedetailsFile releaselinedetailsFile = objectList.get(i);
							ps.setLong(1,releaselinedetailsFile.getFileId());
							ps.setString(2,releaselinedetailsFile.getSystemID());
							ps.setString(3,releaselinedetailsFile.getLineNo());
							ps.setString(4,releaselinedetailsFile.getSerialNo());
							ps.setString(5,releaselinedetailsFile.getLiabBranch());
							DefaultLogger.debug(this, "In fileuploadJdbcImpl "+releaselinedetailsFile.getReleaseAmount());
							if(validateAmount(releaselinedetailsFile.getReleaseAmount())) {
								ps.setString(6,releaselinedetailsFile.getReleaseAmount());
								}else {
									ps.setNull(6,Types.VARCHAR);	
								}
							ps.setString(7,releaselinedetailsFile.getStatus());
							ps.setString(8,releaselinedetailsFile.getReason());
							ps.setString(9,releaselinedetailsFile.getUploadStatus());
							ps.setString(10,releaselinedetailsFile.getSourceRefNo());
							ps.setString(11,releaselinedetailsFile.getExpDate());
							ps.setString(12, releaselinedetailsFile.getPslFlag());
							ps.setString(13, releaselinedetailsFile.getPslValue());
							ps.setString(14, releaselinedetailsFile.getRuleID());
						}
						public int getBatchSize() {
							return objectList.size();
						}
					});
	}

	@Override
	public void createEntireReleaselinedetailsActualFile(List<OBReleaselinedetailsFile> objList)
			throws FileUploadException, SQLException {
		String sql = "INSERT INTO ACTUAL_RELEASELINEDET_UPLOAD " +
				"(ID, FILE_ID, SYSTEM_ID,LINE_NO,SERIAL_NO,LIAB_BRANCH,RELEASED_AMOUNT,STATUS,REASON,UPLOAD_STATUS,SOURCE_REF_NO,EXP_DATE,PSL_FLAG,PSL_VALUE,RULE_ID)" +
				" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ?,?,?,?,?,?)";

				final List<OBReleaselinedetailsFile> objectList = objList;
				final int batchSize = objList.size();
					getJdbcTemplate().batchUpdate(sql,
							new BatchPreparedStatementSetter() {
						public void setValues(PreparedStatement ps, int i)
						throws SQLException {
							OBReleaselinedetailsFile releaselinedetailsFile = objectList.get(i);
							ps.setLong(1,releaselinedetailsFile.getFileId());
							ps.setString(2,releaselinedetailsFile.getSystemID());
							ps.setString(3,releaselinedetailsFile.getLineNo());
							ps.setString(4,releaselinedetailsFile.getSerialNo());
							ps.setString(5,releaselinedetailsFile.getLiabBranch());
							if(validateAmount(releaselinedetailsFile.getReleaseAmount())) {
								ps.setString(6,releaselinedetailsFile.getReleaseAmount());
								}else {
									ps.setNull(6,Types.VARCHAR);	
								}
							ps.setString(7,releaselinedetailsFile.getStatus());
							ps.setString(8,releaselinedetailsFile.getReason());
							ps.setString(9,releaselinedetailsFile.getUploadStatus());
							ps.setString(10,releaselinedetailsFile.getSourceRefNo());
							ps.setString(11,releaselinedetailsFile.getExpDate());
							ps.setString(12, releaselinedetailsFile.getPslFlag());
							ps.setString(13, releaselinedetailsFile.getPslValue());
							ps.setString(14, releaselinedetailsFile.getRuleID());
						}
						public int getBatchSize() {
							return objectList.size();
						}
					});
	}

	@Override
	public int getReleaselinedetailsUplodCount() throws FileUploadException {
		List resultList = null;
		int count=0;
		try {
			String SQL=CHECK_RELEASELINEDETAILS_UPLOAD_TRX+" AND (status LIKE 'PENDING%' OR STATUS LIKE 'REJECTED')";
			count=getJdbcTemplate().queryForInt(SQL);			

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- Checkling RELEASE LINE DETAILS File Transaction in FileUploadJdbcImpl");
		}

		return count;
	}

	public int getReleaselinedetailsFacilityIdCount(String releaseAmountFile) throws FileUploadException{
		int count=0;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT COUNT(1) AS COUNT FROM SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID='"+releaseAmountFile+"'");
			
			DefaultLogger.debug(this,">>>>SELECT COUNT(1) AS COUNT FROM SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID=>>>>" + releaseAmountFile);
			
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					count=rs.getInt("COUNT");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsFacilityIdCount: "+e.getMessage());
        	e.printStackTrace();
		}
		return count;
	}
	
	public int getReleaselinedetailsSerialNoCount(String releaseAmountFile) throws FileUploadException{
		int count=0;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT COUNT(1) AS COUNT FROM SCI_LSP_SYS_XREF WHERE SERIAL_NO='"+releaseAmountFile+"'");
			DefaultLogger.debug(this,">>>>SELECT COUNT(1) AS COUNT FROM SCI_LSP_SYS_XREF WHERE SERIAL_NO=>>>>" + releaseAmountFile);
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					count=rs.getInt("COUNT");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsSerialNoCount: "+e.getMessage());
        	e.printStackTrace();
		}
		return count;
	}
	
	public int getReleaselinedetailsLineNoCount(String releaseAmountFile) throws FileUploadException{
		int count=0;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT COUNT(1) AS COUNT FROM SCI_LSP_SYS_XREF WHERE LINE_NO='"+releaseAmountFile+"'");
			DefaultLogger.debug(this,">>>>SELECT COUNT(1) AS COUNT FROM SCI_LSP_SYS_XREF WHERE LINE_NO=>>>>" + releaseAmountFile);
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					count=rs.getInt("COUNT");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsLineNoCount: "+e.getMessage());
        	e.printStackTrace();
		}
		return count;
	}
	
	public String getReleaselinedetailsLiabBranchID(String releaseAmountFile) throws FileUploadException{
		String id="";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT ID FROM CMS_FCCBRANCH_MASTER WHERE BRANCHCODE='"+releaseAmountFile+"'");
			
			DefaultLogger.debug(this,">>>>SELECT ID FROM CMS_FCCBRANCH_MASTER WHERE BRANCHCODE=>>>>" + releaseAmountFile);
			
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					id=rs.getString("ID");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsLiabBranchID: "+e.getMessage());
        	e.printStackTrace();
		}
		if(id=="" || id==null) {
			return releaseAmountFile;
		}else {
			return id;
		}
	}
	
	public String getReleaselinedetailsLiabBranchReverseID(String releaseAmountFile) throws FileUploadException{
		String id="";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT BRANCHCODE FROM CMS_FCCBRANCH_MASTER WHERE ID='"+releaseAmountFile+"'");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					id=rs.getString("BRANCHCODE");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsLiabBranchReverseID: "+e.getMessage());
        	e.printStackTrace();
		}
		if(id=="" || id==null) {
			return releaseAmountFile;
		}else {
			return id;
		}
		
	}
	
	public int getReleaselinedetailsLiabBranchCount(String releaseAmountFile) throws FileUploadException{
		//String releaseAmountFile1=getReleaselinedetailsLiabBranchID(releaseAmountFile);
		int count=0;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT COUNT(1) AS COUNT FROM SCI_LSP_SYS_XREF WHERE LIAB_BRANCH='"+releaseAmountFile+"'");
			DefaultLogger.debug(this,">>>>SELECT COUNT(1) AS COUNT FROM SCI_LSP_SYS_XREF WHERE LIAB_BRANCH=>>>>" + releaseAmountFile);
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					count=rs.getInt("COUNT");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsLiabBranchCount: "+e.getMessage());
        	e.printStackTrace();
		}
		return count;
	}
	
	public BigDecimal getReleaselinedetailsSanctionAmount(OBReleaselinedetailsFile obj) throws FileUploadException{
		BigDecimal relasableAmount=new BigDecimal("0");
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT RELEASABLE_AMOUNT FROM SCI_LSP_APPR_LMTS WHERE CMS_LSP_APPR_LMTS_ID=" + 
					"(SELECT CMS_LSP_APPR_LMTS_ID FROM SCI_LSP_LMTS_XREF_MAP WHERE CMS_LSP_SYS_XREF_ID IN " + 
					"(SELECT max(CMS_LSP_SYS_XREF_ID) FROM  SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID='"+obj.getSystemID()+
					"' AND SERIAL_NO='"+obj.getSerialNo()+"' AND LINE_NO='"+obj.getLineNo()+"' AND "
							+ "LIAB_BRANCH='"+obj.getLiabBranch()+"')" + 
					")");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					relasableAmount=rs.getBigDecimal("RELEASABLE_AMOUNT");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsSanctionAmount: "+e.getMessage());
        	e.printStackTrace();
		}
		return relasableAmount;
	}
	
	public String getReleaselinedetailsLimitDetailsStatus(OBReleaselinedetailsFile obj) throws FileUploadException{
		String status="";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT CMS_LIMIT_STATUS FROM SCI_LSP_APPR_LMTS WHERE CMS_LSP_APPR_LMTS_ID="+ 
					"(SELECT CMS_LSP_APPR_LMTS_ID FROM SCI_LSP_LMTS_XREF_MAP WHERE CMS_LSP_SYS_XREF_ID IN "+
					"(SELECT max(CMS_LSP_SYS_XREF_ID) FROM  SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID='"+obj.getSystemID()+ 
					"' AND SERIAL_NO='"+obj.getSerialNo()+"' AND LINE_NO='"+obj.getLineNo()+"' AND " + 
					"LIAB_BRANCH='"+obj.getLiabBranch()+"')"+")");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					status=rs.getString("CMS_LIMIT_STATUS");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsLimitDetailsStatus: "+e.getMessage());
        	e.printStackTrace();
		}
		
		return status;
	}
	
	public String getReleaselinedetailsPartyStatus(OBReleaselinedetailsFile obj) throws FileUploadException{
		String status="";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT STATUS FROM SCI_LE_SUB_PROFILE WHERE LSP_LE_ID=" + 
					"(" + 
					"SELECT LLP_LE_ID FROM SCI_LSP_LMT_PROFILE WHERE CMS_LSP_LMT_PROFILE_ID=" + 
					"(" + 
					"SELECT CMS_LIMIT_PROFILE_ID FROM SCI_LSP_APPR_LMTS WHERE CMS_LSP_APPR_LMTS_ID=" + 
					"(" + 
					"SELECT CMS_LSP_APPR_LMTS_ID FROM SCI_LSP_LMTS_XREF_MAP WHERE CMS_LSP_SYS_XREF_ID IN " + 
					"(SELECT max(CMS_LSP_SYS_XREF_ID) FROM  SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID='"+obj.getSystemID()
					+ "' AND SERIAL_NO='"+obj.getSerialNo()+"' AND LINE_NO='"+obj.getLineNo()+"' AND "
					+ "LIAB_BRANCH='"+obj.getLiabBranch()+"')" + 
					")))");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					status=rs.getString("STATUS");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsPartyStatus: "+e.getMessage());
        	e.printStackTrace();
		}
		return status;
	}
	
	public String getReleaselinedetailsPartyName(OBReleaselinedetailsFile obj) throws FileUploadException{
		String partyName="";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT LSP_SHORT_NAME FROM SCI_LE_SUB_PROFILE WHERE LSP_LE_ID=" + 
					"(" + 
					"SELECT LLP_LE_ID FROM SCI_LSP_LMT_PROFILE WHERE CMS_LSP_LMT_PROFILE_ID=" + 
					"(" + 
					"SELECT CMS_LIMIT_PROFILE_ID FROM SCI_LSP_APPR_LMTS WHERE CMS_LSP_APPR_LMTS_ID=" + 
					"(" + 
					"SELECT CMS_LSP_APPR_LMTS_ID FROM SCI_LSP_LMTS_XREF_MAP WHERE CMS_LSP_SYS_XREF_ID IN " + 
					"(SELECT max(CMS_LSP_SYS_XREF_ID) FROM  SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID='"+obj.getSystemID()
					+ "' AND SERIAL_NO='"+obj.getSerialNo()+"' AND LINE_NO='"+obj.getLineNo()+"' AND "
					+ "LIAB_BRANCH='"+obj.getLiabBranch()+"')" + 
					")))");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					partyName=rs.getString("LSP_SHORT_NAME");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsPartyStatus: "+e.getMessage());
        	e.printStackTrace();
		}
		return partyName;
	}	
	
	public String getReleaselinedetailsStatus(OBReleaselinedetailsFile obj) throws FileUploadException{
		String status="";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT STATUS FROM SCI_LSP_SYS_XREF WHERE CMS_LSP_SYS_XREF_ID=(" + 
					"SELECT MAX(CMS_LSP_SYS_XREF_ID) FROM SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID='"+obj.getSystemID()+"' AND " + 
					"SERIAL_NO='"+obj.getSerialNo()+"' AND LINE_NO='"+obj.getLineNo()+"' AND LIAB_BRANCH='"+obj.getLiabBranch()
					+"')");
			rs = dbUtil.executeQuery();			
			if(null!=rs)
			{
				while(rs.next())
				{
					status=rs.getString("STATUS");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsStatus: "+e.getMessage());
        	e.printStackTrace();
		}
		return status;
	}
	
	public Set<String> getReleaselinedetailsReleasedAmount(OBReleaselinedetailsFile obj) throws FileUploadException{
		Set<String> releaseAmount=new HashSet<String>();
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			DefaultLogger.debug(this, " getReleaselinedetailsReleasedAmount ++++ system id "+obj.getSystemID());
			DefaultLogger.debug(this, " getReleaselinedetailsReleasedAmount ++++ serial no "+obj.getSerialNo());
			DefaultLogger.debug(this, " getReleaselinedetailsReleasedAmount ++++ line no "+obj.getLineNo());
			DefaultLogger.debug(this, " getReleaselinedetailsReleasedAmount ++++ liab branch "+obj.getLiabBranch());
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT RELEASED_AMOUNT FROM SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID='"+obj.getSystemID()+"' AND "
					+ "SERIAL_NO='"+obj.getSerialNo()+"' AND LINE_NO='"+obj.getLineNo()+"' AND LIAB_BRANCH='"+obj.getLiabBranch()+"'"
					+"  and CMS_LSP_SYS_XREF_ID = (SELECT MAX(CMS_LSP_SYS_XREF_ID)   FROM SCI_LSP_SYS_XREF   WHERE FACILITY_SYSTEM_ID = '"+obj.getSystemID()+"' AND LINE_NO='"+obj.getLineNo()+"' AND SERIAL_NO='"+obj.getSerialNo()+"' AND LIAB_BRANCH='"+obj.getLiabBranch()+"' )");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					releaseAmount.add(rs.getString("RELEASED_AMOUNT"));
				}
			}
			DefaultLogger.debug(this, " getReleaselinedetailsReleasedAmount release amount fetched from DB "+releaseAmount);
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsStatus: "+e.getMessage());
        	e.printStackTrace();
		}
		return releaseAmount;
	}
	
	//MORATORIUM REGULATORY UPLOAD
	public String getReleaselinedetailsexpDate(OBReleaselinedetailsFile obj) throws FileUploadException{
		String status="";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT to_char(DATE_OF_RESET,'DD/Mon/YYYY') DATE_OF_RESET FROM SCI_LSP_SYS_XREF WHERE CMS_LSP_SYS_XREF_ID=(" + 
					"SELECT MAX(CMS_LSP_SYS_XREF_ID) FROM SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID='"+obj.getSystemID()+"' AND " + 
					"SERIAL_NO='"+obj.getSerialNo()+"' AND LINE_NO='"+obj.getLineNo()+"' AND LIAB_BRANCH='"+obj.getLiabBranch()
					+"')");
			
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					status=rs.getString("DATE_OF_RESET");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsStatus: "+e.getMessage());
        	e.printStackTrace();
		}
		return status;
	}
	
	public int getPslValueCount(OBReleaselinedetailsFile obj) throws DBConnectionException, SQLException
	{
		int count=0;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String pslflag= obj.getPslFlag();
		String pslValue= obj.getPslValue();
		try {
			dbUtil=new DBUtil();
		if(pslflag.equalsIgnoreCase("Yes") &&  null !=obj.getPslValue())
		{
			dbUtil.setSQL(CHECK_PSL_VALUE_Y_RLD_UPLOAD+pslValue+"' and ACTIVE_STATUS=1");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					count = rs.getInt("COUNT");
				}
			}
			finalize(dbUtil,rs);
			
		}
		else if(pslflag.equalsIgnoreCase("No") && null !=obj.getPslValue())
		{
		    dbUtil.setSQL(CHECK_PSL_VALUE_N_RLD_UPLOAD+pslValue+"' and ACTIVE_STATUS=1");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					count = rs.getInt("COUNT");
				}
			}
			finalize(dbUtil,rs);
		    }
		else
			count=-1;
		}catch (Exception e) {
			throw new FileUploadException("Error in getPslValueCount ");
		}
		return count;

	}
	
	public int getRuleCount(OBReleaselinedetailsFile obj)
	{
		int count=0;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String ruleEntry= obj.getRuleID();
		//CHECK_RULE_COUNT_RLD_UPLOAD
		try {
			dbUtil=new DBUtil();
		if(ruleEntry!=null)
		{
			 dbUtil.setSQL(CHECK_RULE_COUNT_RLD_UPLOAD+ruleEntry+"' and ACTIVE_STATUS=1");
				rs = dbUtil.executeQuery();
				if(null!=rs)
				{
					while(rs.next())
					{
						count = rs.getInt("COUNT");
					}
				}
				finalize(dbUtil,rs);
		}
		else
			count=-1;
		}catch (Exception e) {
			throw new FileUploadException("Error in getRuleCount ");
		}
		
		return count;
	}

	@Override
	public void updateReleaseAmountStage(final OBReleaselinedetailsFile releaseAmountFile)
			throws FileUploadException, SQLException {

		String updateReleaseAmountStageStatus = "UPDATE CMS_STAGE_LSP_SYS_XREF SET RELEASED_AMOUNT=?, STATUS='PENDING', ACTION='MODIFY', SOURCE_REF_NO=? WHERE FACILITY_SYSTEM_ID=? AND "
				+ "LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? and CMS_LSP_SYS_XREF_ID in (select max(CMS_LSP_SYS_XREF_ID) from CMS_STAGE_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? AND "
				+"LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=?)";
		getJdbcTemplate().execute(updateReleaseAmountStageStatus,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	              
        		ps.setString(1,releaseAmountFile.getReleaseAmount());
        		ps.setString(2,releaseAmountFile.getSourceRefNo());
        		ps.setString(3,releaseAmountFile.getSystemID());	
        		ps.setString(4,releaseAmountFile.getLineNo()); 
				ps.setString(5,releaseAmountFile.getSerialNo());
				ps.setString(6,releaseAmountFile.getLiabBranch());
				ps.setString(7,releaseAmountFile.getSystemID());
				ps.setString(8,releaseAmountFile.getLineNo());
				ps.setString(9,releaseAmountFile.getSerialNo());
				ps.setString(10,releaseAmountFile.getLiabBranch());
				
                return ps.execute();       
            }  
           }); 
	}
	
	public void updateReleaseAmountStageforNull(final OBReleaselinedetailsFile releaseAmountFile)
			throws FileUploadException, SQLException {

		String updateReleaseAmountStageStatus = "UPDATE CMS_STAGE_LSP_SYS_XREF SET RELEASED_AMOUNT=(\r\n" + 
				" select nvl(RELEASED_AMOUNT,0) from SCI_LSP_SYS_XREF \r\n" + 
				" WHERE FACILITY_SYSTEM_ID=? AND LINE_NO=? \r\n" + 
				" AND SERIAL_NO=? AND LIAB_BRANCH=? \r\n" + 
				" and CMS_LSP_SYS_XREF_ID in (select max(CMS_LSP_SYS_XREF_ID) from SCI_LSP_SYS_XREF where  \r\n" + 
				" FACILITY_SYSTEM_ID=? AND LINE_NO=? \r\n" + 
				" AND SERIAL_NO=? AND LIAB_BRANCH=? )), STATUS='PENDING',\r\n" + 
				" ACTION='MODIFY', SOURCE_REF_NO=? WHERE FACILITY_SYSTEM_ID=? AND \r\n" + 
				" LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? and CMS_LSP_SYS_XREF_ID in\r\n" + 
				" (select max(CMS_LSP_SYS_XREF_ID) from CMS_STAGE_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? AND \r\n" + 
				" LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=?)";
		getJdbcTemplate().execute(updateReleaseAmountStageStatus,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	              
        		ps.setString(1,releaseAmountFile.getSystemID());
        		ps.setString(2,releaseAmountFile.getLineNo());
        		ps.setString(3,releaseAmountFile.getSerialNo());	
        		ps.setString(4,releaseAmountFile.getLiabBranch()); 
				ps.setString(5,releaseAmountFile.getSystemID());
				ps.setString(6,releaseAmountFile.getLineNo());
				ps.setString(7,releaseAmountFile.getSerialNo());
				ps.setString(8,releaseAmountFile.getLiabBranch());
				ps.setString(9,releaseAmountFile.getSourceRefNo());
				ps.setString(10,releaseAmountFile.getSystemID());
				ps.setString(11,releaseAmountFile.getLineNo());
        		ps.setString(12,releaseAmountFile.getSerialNo());
        		ps.setString(13,releaseAmountFile.getLiabBranch());	
        		ps.setString(14,releaseAmountFile.getSystemID()); 
				ps.setString(15,releaseAmountFile.getLineNo());
				ps.setString(16,releaseAmountFile.getSerialNo());
				ps.setString(17,releaseAmountFile.getLiabBranch());
				
                return ps.execute();       
            }  
           }); 
	}
	
	public void updateTotalReleasedAmtStageforNull(final OBReleaselinedetailsFile releaseAmountFile)
			throws FileUploadException, SQLException {
		
		String updateReleaseAmountStageStatus = "update stage_limit set TOTAL_RELEASED_AMOUNT =( "+
                "select sum(RELEASED_AMOUNT) from SCI_LSP_SYS_XREF where CMS_LSP_SYS_XREF_ID in (select CMS_LSP_SYS_XREF_ID "+ 
              "from SCI_LSP_LMTS_XREF_MAP where cms_status='ACTIVE' and CMS_LSP_APPR_LMTS_ID in (select CMS_LSP_APPR_LMTS_ID from  "+
         "SCI_LSP_LMTS_XREF_MAP  where cms_status='ACTIVE' and CMS_LSP_SYS_XREF_ID in "+
        " (select max(CMS_LSP_SYS_XREF_ID) from SCI_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? "+
       " and  LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? ))))  where CMS_LSP_APPR_LMTS_ID in ( select CMS_LSP_APPR_LMTS_ID from  "+
        " SCI_LSP_LMTS_XREF_MAP  where cms_status='ACTIVE' and CMS_LSP_SYS_XREF_ID in "+
         "(select max(CMS_LSP_SYS_XREF_ID) from SCI_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? AND "+
				" LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? ))";
		getJdbcTemplate().execute(updateReleaseAmountStageStatus,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	              
        		ps.setString(1,releaseAmountFile.getSystemID());	
        		ps.setString(2,releaseAmountFile.getLineNo()); 
				ps.setString(3,releaseAmountFile.getSerialNo());
				ps.setString(4,releaseAmountFile.getLiabBranch());
				ps.setString(5,releaseAmountFile.getSystemID());	
        		ps.setString(6,releaseAmountFile.getLineNo()); 
				ps.setString(7,releaseAmountFile.getSerialNo());
				ps.setString(8,releaseAmountFile.getLiabBranch());
				
                return ps.execute();       
            }  
           }); 
	}
	
	public void updateTotalReleasedAmtStage(final OBReleaselinedetailsFile releaseAmountFile)
			throws FileUploadException, SQLException {

//		String updateReleaseAmountStageStatus = "update stage_limit set TOTAL_RELEASED_AMOUNT= TOTAL_RELEASED_AMOUNT+? where CMS_LSP_APPR_LMTS_ID in ( select CMS_LSP_APPR_LMTS_ID from  STAGE_LIMIT_XREF  where CMS_LSP_SYS_XREF_ID in (select max(CMS_LSP_SYS_XREF_ID) from cms_stage_lsp_sys_xref where FACILITY_SYSTEM_ID=? AND " 
//				+" LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=?))";
		
		String updateReleaseAmountStageStatus = " update stage_limit set TOTAL_RELEASED_AMOUNT =( "+
	                "select sum(RELEASED_AMOUNT) from cms_stage_lsp_sys_xref where CMS_LSP_SYS_XREF_ID in (select CMS_LSP_SYS_XREF_ID "+ 
	              "from STAGE_LIMIT_XREF where cms_status='ACTIVE' and CMS_LSP_APPR_LMTS_ID in (select CMS_LSP_APPR_LMTS_ID from  "+
	        " STAGE_LIMIT_XREF  where cms_status='ACTIVE' and CMS_LSP_SYS_XREF_ID in "+
	         "(select max(CMS_LSP_SYS_XREF_ID) from cms_stage_lsp_sys_xref where FACILITY_SYSTEM_ID=? "+
	         "and  LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? ))))  where CMS_LSP_APPR_LMTS_ID in ( select CMS_LSP_APPR_LMTS_ID from  "+
	         "STAGE_LIMIT_XREF  where cms_status='ACTIVE' and CMS_LSP_SYS_XREF_ID in "+
	         "(select max(CMS_LSP_SYS_XREF_ID) from cms_stage_lsp_sys_xref where FACILITY_SYSTEM_ID=? AND "+
					" LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? ))";
		getJdbcTemplate().execute(updateReleaseAmountStageStatus,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	              
        		ps.setString(1,releaseAmountFile.getSystemID());	
        		ps.setString(2,releaseAmountFile.getLineNo()); 
				ps.setString(3,releaseAmountFile.getSerialNo());
				ps.setString(4,releaseAmountFile.getLiabBranch());
				ps.setString(5,releaseAmountFile.getSystemID());	
        		ps.setString(6,releaseAmountFile.getLineNo()); 
				ps.setString(7,releaseAmountFile.getSerialNo());
				ps.setString(8,releaseAmountFile.getLiabBranch());
				
                return ps.execute();       
            }  
           }); 
	}
	
	
	
	@Override
	public void updateReleaseAmountActual(final OBReleaselinedetailsFile releaseAmountFile)
			throws FileUploadException, SQLException {

		String updateReleaseAmountStageStatus = "UPDATE SCI_LSP_SYS_XREF SET RELEASED_AMOUNT=?,STATUS='PENDING', ACTION='MODIFY', SOURCE_REF_NO=? WHERE FACILITY_SYSTEM_ID=? AND "
				+ "LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? and CMS_LSP_SYS_XREF_ID in (select max(CMS_LSP_SYS_XREF_ID) from SCI_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? AND " 
				+" LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=?)";
		getJdbcTemplate().execute(updateReleaseAmountStageStatus,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	    if(releaseAmountFile.getReleaseAmount()!=null) {          
        		ps.setString(1,releaseAmountFile.getReleaseAmount());
        	    }else {
        	    	String rlsAmt = getreleaseAmtforactual(releaseAmountFile.getSystemID(),releaseAmountFile.getLineNo(),
        	    			releaseAmountFile.getSerialNo(),releaseAmountFile.getLiabBranch());
        	    ps.setString(1,rlsAmt);	
        	    }
        		ps.setString(2,releaseAmountFile.getSourceRefNo());
        		ps.setString(3,releaseAmountFile.getSystemID());	
        		ps.setString(4,releaseAmountFile.getLineNo()); 
				ps.setString(5,releaseAmountFile.getSerialNo());
				ps.setString(6,releaseAmountFile.getLiabBranch());
				ps.setString(7,releaseAmountFile.getSystemID());
				ps.setString(8,releaseAmountFile.getLineNo());
				ps.setString(9,releaseAmountFile.getSerialNo());
				ps.setString(10,releaseAmountFile.getLiabBranch());
				
				
                return ps.execute();        
            }  
           }); 
	}
		
	public String getreleaseAmtforactual(String systemId,String LineNo,String SerialNo,String LiabBranch) {
		String rlsAmt=null;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("select released_amount from  SCI_LSP_SYS_XREF  WHERE FACILITY_SYSTEM_ID='"+systemId+"' AND " + 
		" LINE_NO='"+LineNo+"' AND SERIAL_NO='"+SerialNo+"' AND LIAB_BRANCH='"+LiabBranch+"' "+
	    " and CMS_LSP_SYS_XREF_ID in (select max(CMS_LSP_SYS_XREF_ID) "+
		" from SCI_LSP_SYS_XREF where FACILITY_SYSTEM_ID='"+systemId+"'  AND " +
		" LINE_NO='"+LineNo+"' AND SERIAL_NO='"+SerialNo+"' AND LIAB_BRANCH='"+LiabBranch+"')");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					rlsAmt=rs.getString("released_amount");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getreleaseAmtforactual: "+e.getMessage());
        	e.printStackTrace();
		}
		return rlsAmt;
	}
		public void updateTotalReleasedAmtActual(final OBReleaselinedetailsFile releaseAmountFile)
				throws FileUploadException, SQLException {

//			String updateReleaseAmountStageStatus = "update sci_lsp_appr_lmts set TOTAL_RELEASED_AMOUNT= TOTAL_RELEASED_AMOUNT+? where CMS_LSP_APPR_LMTS_ID in ( select CMS_LSP_APPR_LMTS_ID from  SCI_LSP_LMTS_XREF_MAP "
//			+" where CMS_LSP_SYS_XREF_ID in (select max(CMS_LSP_SYS_XREF_ID) from SCI_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? AND " 
//					+" LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=?))";
			
			String updateReleaseAmountStageStatus="update sci_lsp_appr_lmts set TOTAL_RELEASED_AMOUNT =( "+
		                "select sum(RELEASED_AMOUNT) from SCI_LSP_SYS_XREF where CMS_LSP_SYS_XREF_ID in (select CMS_LSP_SYS_XREF_ID "+ 
		              "from SCI_LSP_LMTS_XREF_MAP where cms_status='ACTIVE' and CMS_LSP_APPR_LMTS_ID in (select CMS_LSP_APPR_LMTS_ID from  "+
		         "SCI_LSP_LMTS_XREF_MAP  where cms_status='ACTIVE' and CMS_LSP_SYS_XREF_ID in "+
		        " (select max(CMS_LSP_SYS_XREF_ID) from SCI_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? "+
		       " and  LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? ))))  where CMS_LSP_APPR_LMTS_ID in ( select CMS_LSP_APPR_LMTS_ID from  "+
		        " SCI_LSP_LMTS_XREF_MAP  where cms_status='ACTIVE' and CMS_LSP_SYS_XREF_ID in "+
		         "(select max(CMS_LSP_SYS_XREF_ID) from SCI_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? AND "+
						" LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? ))";
			getJdbcTemplate().execute(updateReleaseAmountStageStatus,new PreparedStatementCallback(){  
	        
	        	public Object doInPreparedStatement(PreparedStatement ps)  
	        	           throws SQLException {  
	        		ps.setString(1,releaseAmountFile.getSystemID());	
	        		ps.setString(2,releaseAmountFile.getLineNo()); 
					ps.setString(3,releaseAmountFile.getSerialNo());
					ps.setString(4,releaseAmountFile.getLiabBranch());
					ps.setString(5,releaseAmountFile.getSystemID());	
	        		ps.setString(6,releaseAmountFile.getLineNo()); 
					ps.setString(7,releaseAmountFile.getSerialNo());
					ps.setString(8,releaseAmountFile.getLiabBranch());
					
	                return ps.execute();       
	            }  
	           });
	}
	
	public String generateSourceRefNo() {
		ILimitDAO dao1 = LimitDAOFactory.getDAO();
		Date d = DateUtil.getDate();
		String dateFormat = "yyMMdd";
		SimpleDateFormat s = new SimpleDateFormat(dateFormat);
		String date = s.format(d);

		String tempSourceRefNo = "";
		tempSourceRefNo = "" + dao1.generateSourceSeqNo();
		int len = tempSourceRefNo.length();
		String concatZero = "";
		if (null != tempSourceRefNo && len != 5) 
			for (int m = 5; m > len; m--) 
				concatZero = "0" + concatZero;
				
		tempSourceRefNo = concatZero + tempSourceRefNo;
		String sorceRefNo = ICMSConstant.FCUBS_CAD + date + tempSourceRefNo;
		
		return sorceRefNo;
	}

	@Override
	public HashMap insertReleaselinedetailsfile(ArrayList result, UploadReleaselinedetailsFileCmd cmd, String fileName,
			ICommonUser user, Date date, ConcurrentMap<String, String> dataFromCacheView,
			ConcurrentMap<String, String> dataFromUpdLineFacilityMV) {
		List totalUploadedList= new ArrayList();
		List errorList= new ArrayList();
		Timestamp st = null;
		String errMsg="";
		String tempData="";
		String strArrTemp[]=new String[3];
		List selectCustDetails=new ArrayList();
		String partyStatus="";
		HashMap retMap = new HashMap();
		DefaultLogger.debug(this, "##########3###########In FileUploadJdbcImpl insertUploadUbsReleaselinedetailsFile ##### line no 220#######:: ");
		try {
			if(result!=null&& result.size() != 0)
			{
				for (int index = 0; index < result.size(); index++) {
					HashMap eachDataMap = (HashMap) result.get(index);
					OBUbsFile obj=new OBUbsFile();
					obj.setCurrency((String) eachDataMap.get("CURRENCY_CODE"));
					if(null!=eachDataMap.get("CUSTOMER_ID")&& !"".equalsIgnoreCase(eachDataMap.get("CUSTOMER_ID").toString()) )
					{
						obj.setCustomer(eachDataMap.get("CUSTOMER_ID").toString());
					}
					if(null!=eachDataMap.get("LINE_NO")&& !"".equalsIgnoreCase(eachDataMap.get("LINE_NO").toString()) )
					{
						obj.setLine(eachDataMap.get("LINE_NO").toString());
					}
					
					if(null!=eachDataMap.get("LIMIT_AMOUNT")&& !"".equalsIgnoreCase(eachDataMap.get("LIMIT_AMOUNT").toString()) )
					{
						obj.setLimit(new Double(Double.parseDouble(eachDataMap.get("LIMIT_AMOUNT").toString())));
					}
					
					if(null!=eachDataMap.get("SR_NO")&& !"".equalsIgnoreCase(eachDataMap.get("SR_NO").toString()) )
					{
						obj.setSerialNo(eachDataMap.get("SR_NO").toString());
					}
					
					if(null!=eachDataMap.get("UTILIZATION_AMOUNT")&& !"".equalsIgnoreCase(eachDataMap.get("UTILIZATION_AMOUNT").toString()) )
					{
						obj.setUtilize(new Double(Double.parseDouble(eachDataMap.get("UTILIZATION_AMOUNT").toString())));
						
					}
					obj.setUploadStatus("Y");
					OBCommonFile commonObj = obj;
//					partyStatus=getPartyStatus(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()),"UBS-LIMITS");
					// Commented and added By Mukesh Mohapatra || UBS_UPLOAD_OPTIMIZATION |Starts
					//partyStatus=getPartyStatusForUBS(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()));
					partyStatus = getPartyStatusForUBSUpload(obj.getCustomer(),obj.getLine(),String.valueOf(obj.getSerialNo()),dataFromCacheView,"UBS-LIMITS");
					// Commented and added By Mukesh Mohapatra  || UBS_UPLOAD_OPTIMIZATION |Ends
					if(!partyStatus.equals("") && "ACTIVE".equals(partyStatus)){
						commonObj=updateLmtUtilAmtStage(obj,"RELEASELINEDETAILS-LIMITS",dataFromUpdLineFacilityMV);
					}else if(!partyStatus.equals("") && "INACTIVE".equals(partyStatus)){
						errMsg="Party Is Inactive In CLIMS.";
						commonObj.setReason(errMsg);
						commonObj.setStatus("FAIL");
						commonObj.setUploadStatus("N");
					}
					else if(partyStatus.equals("")){
						commonObj=updateLmtUtilAmtStage(obj,"RELEASELINEDETAILS-LIMITS",dataFromUpdLineFacilityMV);
						errMsg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+obj.getCustomer()+","+obj.getLine()+","+obj.getSerialNo()+","+obj.getCurrency()+") Available in UBS not in CLIMS.";
					}
					totalUploadedList.add(commonObj);
				}
			}
			
			//To Do :::: Below functionality has been implemented for UBS-LIMITS file upload only. This needs to be implemented for Other Upload files. 
			//Also for inserting data to actual tables.
			int batchSize = 200;
			for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
				List<OBCommonFile> batchList = totalUploadedList.subList(j, j + batchSize > totalUploadedList.size() ? totalUploadedList.size() : j + batchSize);
				updateXrefStageAmount(batchList,"RELEASELINEDETAILS-LIMITS");
			}
			
			st = new Timestamp(System.currentTimeMillis());
			selectCustDetails=selectCustDetails("ReleaselinedetailsFile");
			for(int i=0;i<selectCustDetails.size();i++)
			{				
				tempData=selectCustDetails.get(i).toString();
				strArrTemp=tempData.split(ICMSConstant.FILEUPLOAD_SEPERATOR);
				errMsg="Combination of Customer_id, Line_no ,Sr_no, Currency i.e. ("+selectCustDetails.get(i)+")Available in CLIMS not in UBS.";
				OBReleaselinedetailsFile obReleaselinedetails=new OBReleaselinedetailsFile();
				//obUbs.setCurrency("");					
				obReleaselinedetails.setCustomer(strArrTemp[0]);
				obReleaselinedetails.setLine(strArrTemp[1]);
				obReleaselinedetails.setSerialNo(strArrTemp[2]);
				obReleaselinedetails.setCurrency(strArrTemp[3].trim());
				obReleaselinedetails.setStatus("FAIL");
				obReleaselinedetails.setReason(errMsg);
				obReleaselinedetails.setUploadStatus("N");
				errorList.add(obReleaselinedetails);
			}
		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.getMessage());
			throw new IncompleteBatchJobException(
			"Unable to update retrived record from Ubs Releaselinedetails file");
		}
		getJdbcTemplate().update(updateStatusActualReleaselinedetails);
		retMap.put("totalUploadedList", totalUploadedList);
		retMap.put("errorList", errorList);
		return retMap;
	}

	@Override
	public HashMap insertFacilitydetailsfile(ArrayList result, UploadFacilitydetailsFileCmd cmd, String fileName,
			ICommonUser user, Date date, ConcurrentMap<String, String> dataFromCacheView,
			ConcurrentMap<String, String> dataFromUpdLineFacilityMV) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchResult getAllFacilitydetailsFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=STAGE_FCT_UPD_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new facilitydetailsFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving Facilitydetails File List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}

	
	
	@Override
	public SearchResult getAllActualFacilitydetailsFile(String id) throws FileUploadException {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public int getFacilitydetailsUplodCount() throws FileUploadException {
		List resultList = null;
		int count=0;
		try {
			String SQL=CHECK_FACILITYDETAILS_UPLOAD_TRX+" AND (status LIKE 'PENDING%' OR STATUS LIKE 'REJECTED')";
			count=getJdbcTemplate().queryForInt(SQL);			

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- Checkling FACILITY DETAILS File Transaction in FileUploadJdbcImpl");
		}

		return count;
	}

	
	
	@Override
	public void createEntireFacilitydetailsStageFile(List<OBFacilitydetailsFile> objList)
			throws FileUploadException, SQLException {
		String sql = "INSERT INTO STAGE_FACILITYDET_UPLOAD " +
				"(ID, FILE_ID, FACILITY_ID ,SANCTION_AMOUNT,SANCTION_AMOUNT_INR,RELEASABLE_AMOUNT,STATUS,REASON,UPLOAD_STATUS)" +
				" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?)";

				final List<OBFacilitydetailsFile> objectList1 = objList;
				final int batchSize = objList.size();
					getJdbcTemplate().batchUpdate(sql,
							new BatchPreparedStatementSetter() {
						public void setValues(PreparedStatement ps, int i)
						throws SQLException {
							OBFacilitydetailsFile facilitydetailsFile = objectList1.get(i);
							ps.setLong(1,facilitydetailsFile.getFileId());
							ps.setString(2,facilitydetailsFile.getFacilityID());
							ps.setString(3,facilitydetailsFile.getSanctionAmt());
							ps.setString(4,facilitydetailsFile.getSanctionAmtInr());
							ps.setString(5,facilitydetailsFile.getReleasableAmt());
							ps.setString(6,facilitydetailsFile.getStatus());
							ps.setString(7,facilitydetailsFile.getReason());
							ps.setString(8,facilitydetailsFile.getUploadStatus());
							
						}
						public int getBatchSize() {
							return objectList1.size();
						}
					});
	}

	@Override
	public void createEntireFacilitydetailsActualFile(List<OBFacilitydetailsFile> objList)
			throws FileUploadException, SQLException {
		String sql = "INSERT INTO ACTUAL_FACILITYDET_UPLOAD " +
				"(ID, FILE_ID, FACILITY_ID ,SANCTION_AMOUNT,SANCTION_AMOUNT_INR,RELEASABLE_AMOUNT,STATUS,REASON,UPLOAD_STATUS)" +
				" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?)";

				final List<OBFacilitydetailsFile> objectList1 = objList;
				final int batchSize = objList.size();
					getJdbcTemplate().batchUpdate(sql,
							new BatchPreparedStatementSetter() {
						public void setValues(PreparedStatement ps, int i)
						throws SQLException {
							OBFacilitydetailsFile facilitydetailsFile = objectList1.get(i);
							ps.setLong(1,facilitydetailsFile.getFileId());
							ps.setString(2,facilitydetailsFile.getFacilityID());
							ps.setString(3,facilitydetailsFile.getSanctionAmt());
							ps.setString(4,facilitydetailsFile.getSanctionAmtInr());
							ps.setString(5,facilitydetailsFile.getReleasableAmt());
							ps.setString(6,facilitydetailsFile.getStatus());
							ps.setString(7,facilitydetailsFile.getReason());
							ps.setString(8,facilitydetailsFile.getUploadStatus());
						}
						public int getBatchSize() {
							return objectList1.size();
						}
					});
	}
	
	
	public long getLimitProfileId(String facilityId) throws FileUploadException{
		long id=0;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT CMS_LIMIT_PROFILE_ID FROM SCI_LSP_APPR_LMTS WHERE LMT_ID ='"+facilityId+"'");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					id=rs.getLong("CMS_LIMIT_PROFILE_ID");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsLiabBranchID: "+e.getMessage());
        	e.printStackTrace();
		}
		
			return id;
		
	}
	
	public long getLimitProfileApprId(String facilityId) throws FileUploadException{
		long id=0;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT CMS_LSP_APPR_LMTS_ID FROM SCI_LSP_APPR_LMTS WHERE LMT_ID ='"+facilityId+"'");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					id=rs.getLong("CMS_LSP_APPR_LMTS_ID");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsLiabBranchID: "+e.getMessage());
        	e.printStackTrace();
		}
		
			return id;
		
	}
	

	public List getLimitSummaryListByAA(String aaId) throws LimitException {

		String queryStr = "SELECT LMT.CMS_LSP_APPR_LMTS_ID, "
			+ " LMT.LMT_ID, "
			+ " CM1.ENTRY_NAME AS LMT_SOURCE, "
			+ " LMT.FACILITY_NAME, "
			+ " LMT.FACILITY_TYPE, "
			//+ " COUNTRY1.CTR_CNTRY_NAME, "
			//+ " LMT.CMS_BKG_COUNTRY, "
			//+ " LMT.CMS_BKG_ORGANISATION, "
			+ " LMT.LMT_OUTER_LMT_ID, "
			+ " LMT.LMT_CRRNCY_ISO_CODE, "
			+ " LMT.RELEASABLE_AMOUNT, "
			+ " LMT.CMS_ACTIVATED_LIMIT, "
			+ " LMT.TOTAL_RELEASED_AMOUNT, "
			+ " LMT.CMS_OUTSTANDING_AMT, "
			+ " LMT.CMS_REQ_SEC_COVERAGE, "
			+ " LMT.IS_ADHOC_TOSUM, "
			+ " LMT.IS_ADHOC, "
			+ " LMT.ADHOC_LMT_AMOUNT, "
			+ " (SELECT COUNT(L2.CMS_LSP_APPR_LMTS_ID) FROM SCI_LSP_APPR_LMTS L2 WHERE L2.LMT_TYPE_VALUE='INNER' AND L2.CMS_LIMIT_STATUS = 'ACTIVE' AND "
			+ " L2.LMT_OUTER_LMT_ID=LMT.CMS_LSP_APPR_LMTS_ID) AS COUNT_INNER, "
			+ " (SELECT COUNT(M2.CMS_COLLATERAL_ID) FROM CMS_LIMIT_SECURITY_MAP M2 "
			+ " WHERE ( M2.UPDATE_STATUS_IND IS NULL OR M2.UPDATE_STATUS_IND <> 'D' ) AND "
			+ " M2.CMS_LSP_APPR_LMTS_ID = LMT.CMS_LSP_APPR_LMTS_ID) AS COUNT_SEC, "
			+ " (SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY "
			+ " WHERE CATEGORY_CODE = 'SEC_SOURCE' "
			+ " AND ENTRY_CODE = SS.SOURCE_ID) AS SEC_SOURCE,"
			+ " SS.SOURCE_SECURITY_ID, "
			+ " SEC.TYPE_NAME, "
			+ " SEC.SUBTYPE_NAME, "
			+ " MAP.LMT_SECURITY_COVERAGE, "
			+ " SEC.COLLATERAL_CODE, "
			+ " SEC.CMS_COLLATERAL_ID, "
			+ " LMT.LMT_TYPE_VALUE "
			//+ " COUNTRY2.CTR_CNTRY_NAME, "
			// + " SEC.SECURITY_ORGANISATION "
			+ " FROM SCI_LSP_APPR_LMTS LMT LEFT OUTER JOIN CMS_LIMIT_SECURITY_MAP MAP ON LMT.CMS_LSP_APPR_LMTS_ID = MAP.CMS_LSP_APPR_LMTS_ID "
			+ "  AND ( MAP.UPDATE_STATUS_IND IS NULL OR MAP.UPDATE_STATUS_IND <> 'D' ) LEFT OUTER JOIN CMS_SECURITY SEC ON MAP.CMS_COLLATERAL_ID = SEC.CMS_COLLATERAL_ID AND SEC.STATUS = 'ACTIVE' "
			// + " LEFT OUTER JOIN SCI_COUNTRY COUNTRY2 ON SEC.SECURITY_LOCATION = COUNTRY2.CTR_CNTRY_ISO_CODE "
			+ " LEFT OUTER JOIN CMS_SECURITY_SOURCE SS ON SEC.CMS_COLLATERAL_ID = SS.CMS_COLLATERAL_ID AND SS.STATUS <> 'DELETED', "
			//+ " LEFT OUTER JOIN CMS_COLLATERAL_NEW_MASTER COLNEWMAST ON COLNEWMAST.NEW_COLLATERAL_CODE=SEC.COLLATERAL_CODE , "
			+ // AND SS.SOURCE_ID = LMT.SOURCE_ID
			//"SCI_COUNTRY COUNTRY1, " 
			" COMMON_CODE_CATEGORY_ENTRY CM1 "
			+ " WHERE LMT.CMS_LIMIT_PROFILE_ID = ? AND " + "LMT.CMS_LIMIT_STATUS = 'ACTIVE' AND "
			//+ "LMT.CMS_BKG_COUNTRY = COUNTRY1.CTR_CNTRY_ISO_CODE AND " 
			+ " CM1.CATEGORY_CODE = '" + ICMSConstant.CATEGORY_SOURCE_SYSTEM + "' " + "ORDER BY LMT.FACILITY_NAME ,SS.SOURCE_SECURITY_ID, MAP.CHARGE_ID DESC";

		return (List) getJdbcTemplate().query(queryStr, new Object[]{aaId}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List limitList = new ArrayList();

				List prevLimitId = new ArrayList();
				//String prevLimitId = "";
				String prevSecId = "";
				HashSet set = new HashSet();

				while (rs.next()) {
					String cmsLimitId = rs.getString("CMS_LSP_APPR_LMTS_ID");
					String securityId = rs.getString("SOURCE_SECURITY_ID");

					LimitListSummaryItemBase curSummary = null;
					if (!prevLimitId.contains(cmsLimitId)) {
						curSummary = new LimitListSummaryItemBase();
						curSummary.setCmsLimitId(cmsLimitId);
						String limitId = rs.getString("LMT_ID") + " - " + rs.getString("LMT_SOURCE");
						curSummary.setLimitId(limitId);
						String prodType = rs.getString("FACILITY_NAME");
						curSummary.setProdTypeCode(prodType);
						String facilityType = rs.getString("FACILITY_TYPE");
						curSummary.setFacilityTypeCode(facilityType);
						String lmtCountry = rs.getString("FACILITY_TYPE");
						curSummary.setLimitLoc(lmtCountry);
						// String lmtCtryCode = rs.getString("CMS_BKG_COUNTRY");
						//String lmtBkgOrg = rs.getString("CMS_BKG_ORGANISATION");
						// IBookingLocation lmtBkgLoc = new OBBookingLocation(lmtCtryCode, lmtBkgOrg);
						//curSummary.setLimitBookingLoc(lmtBkgLoc);
						String outerLmtId = rs.getString("LMT_OUTER_LMT_ID");
						if ("0".equals(outerLmtId)
								|| String.valueOf(ICMSConstant.LONG_INVALID_VALUE).equals(outerLmtId)) {
							outerLmtId = null;
						}
						//curSummary.setOuterLimitId(outerLmtId);
						String lmtCurrency = rs.getString("LMT_CRRNCY_ISO_CODE");
						if (lmtCurrency != null) {
							curSummary.setCurrencyCode(lmtCurrency);
						}

						String lmtApproveAmt = rs.getString("RELEASABLE_AMOUNT");
						if (lmtApproveAmt != null) {
							curSummary.setApprovedAmount(lmtApproveAmt);
						}
						String lmtActivateAmt = rs.getString("CMS_ACTIVATED_LIMIT");
						if (lmtActivateAmt != null) {
							curSummary.setAuthorizedAmount(new Amount(Double.parseDouble(lmtActivateAmt), lmtCurrency));
						}
						String lmtDrawAmt = rs.getString("TOTAL_RELEASED_AMOUNT");
						if (lmtDrawAmt != null) {
							curSummary.setDrawingAmount(lmtDrawAmt);
						}
						String lmtOutstdAmt = rs.getString("CMS_OUTSTANDING_AMT");
						if (lmtOutstdAmt != null) {
							curSummary.setOutstandingAmount(new Amount(Double.parseDouble(lmtOutstdAmt), lmtCurrency));
						}
						String actSeccov = rs.getString("CMS_REQ_SEC_COVERAGE");
						if (actSeccov != null) {
							curSummary.setActualSecCoverage(actSeccov);
						}
						String isAdhocToSum = rs.getString("IS_ADHOC_TOSUM");
						if (isAdhocToSum != null) {
							curSummary.setIsAdhocToSum(isAdhocToSum);
						}
						String isAdhoc = rs.getString("IS_ADHOC");
						if (isAdhoc != null) {
							curSummary.setIsAdhoc(isAdhoc);
						}

						String adhocAmount = rs.getString("ADHOC_LMT_AMOUNT");
						if (adhocAmount != null) {
							curSummary.setAdhocAmount(adhocAmount);
						}
						
						String isSubLimit = rs.getString("LMT_TYPE_VALUE");
						if (isSubLimit != null) {
							curSummary.setIsSubLimit(isSubLimit);
						}
						
						curSummary.setInnerLimitCount(rs.getInt("COUNT_INNER"));
						curSummary.setLinkSecCount(rs.getInt("COUNT_SEC"));
						limitList.add(curSummary);
						prevLimitId.add(cmsLimitId);
						prevSecId = "";
					} else {
						// limit id same as last added item

						for(int i =0 ; i<limitList.size();i++)
						{

							LimitListSummaryItemBase limit=(LimitListSummaryItemBase)limitList.get(i);
							if(limit.getCmsLimitId().equals(cmsLimitId))
							{
								curSummary = (LimitListSummaryItemBase) (limitList.get(i));
							}
						}
					}

					if ((securityId != null) && !securityId.equals(prevSecId)) {
						LimitListSecItemBase secItem = new LimitListSecItemBase();
						secItem.setSecurityId(rs.getString("CMS_COLLATERAL_ID"));
						String typeName = rs.getString("TYPE_NAME");
						secItem.setSecTypeName(typeName);
						String subtypeName = rs.getString("SUBTYPE_NAME");
						secItem.setSecSubtypeName(subtypeName);
						String lmtSecurityCoverage = rs.getString("LMT_SECURITY_COVERAGE");
						secItem.setLmtSecurityCoverage(lmtSecurityCoverage);
						secItem.setSecLocDesc(rs.getString("COLLATERAL_CODE"));
						//String secCountry = rs.getString("CTR_CNTRY_NAME");
						// secItem.setSecLocDesc(secCountry);
						//String secOrg = rs.getString("SECURITY_ORGANISATION");
						//secItem.setSecOrgDesc(secOrg); 
						curSummary.addSecItem(secItem);
						prevSecId = securityId;
						set.add(securityId);
					}
				}

				return limitList;
			}
		});
	}

	public boolean getFacilittIdValidOrNot(String facilityID)throws FileUploadException {
		//String releaseAmountFile1=getReleaselinedetailsLiabBranchID(releaseAmountFile);
		int count=0;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		boolean flag = false;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("select count(*) from SCI_LSP_APPR_LMTS where lmt_id = '"+facilityID+"'");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					count=rs.getInt(1);
					if(count > 0) {
					flag = true;
					}
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsLiabBranchCount: "+e.getMessage());
        	e.printStackTrace();
		}
		return flag;
	}

	@Override
	public void updateSanctionAmt(final OBFacilitydetailsFile obFacilitydetailsFile)
			throws FileUploadException, SQLException {

		String updateSanctionAmtStageStatus = "UPDATE STAGE_LIMIT SET CMS_REQ_SEC_COVERAGE=? WHERE CMS_LSP_APPR_LMTS_ID in (select max(CMS_LSP_APPR_LMTS_ID) from STAGE_LIMIT where LMT_ID=?) ";
		getJdbcTemplate().execute(updateSanctionAmtStageStatus,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	              
        		ps.setString(1,obFacilitydetailsFile.getSanctionAmt());
        		ps.setString(2,obFacilitydetailsFile.getFacilityID());
        		
                return ps.execute();       
            }  
           }); 
	}
	
	@Override
	public void updateReleasableAmt(final OBFacilitydetailsFile obFacilitydetailsFile)
			throws FileUploadException, SQLException {

		String updateReleasableAmtStageStatus = "UPDATE STAGE_LIMIT SET RELEASABLE_AMOUNT=? WHERE CMS_LSP_APPR_LMTS_ID in (select max(CMS_LSP_APPR_LMTS_ID) from STAGE_LIMIT where LMT_ID=?) ";
		getJdbcTemplate().execute(updateReleasableAmtStageStatus,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	              
        		ps.setString(1,obFacilitydetailsFile.getReleasableAmt());
        		ps.setString(2,obFacilitydetailsFile.getFacilityID());
        		
                return ps.execute();       
            }  
           }); 
	}
	
	
	@Override
	public void updateSanctionAmtActual(final OBFacilitydetailsFile obFacilitydetailsFile)
			throws FileUploadException, SQLException {

		String updateSanctionAmtActual = "UPDATE SCI_LSP_APPR_LMTS SET CMS_REQ_SEC_COVERAGE=? where LMT_ID=? ";
		getJdbcTemplate().execute(updateSanctionAmtActual,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	              
        		ps.setString(1,obFacilitydetailsFile.getSanctionAmt());
        		ps.setString(2,obFacilitydetailsFile.getFacilityID());
        		
                return ps.execute();       
            }  
           }); 
	}
	
	@Override
	public void updateReleasableAmtActual(final OBFacilitydetailsFile obFacilitydetailsFile)
			throws FileUploadException, SQLException {

		String updateReleasableAmtActual = "UPDATE SCI_LSP_APPR_LMTS SET RELEASABLE_AMOUNT=? where LMT_ID=? ";
		getJdbcTemplate().execute(updateReleasableAmtActual,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	              
        		ps.setString(1,obFacilitydetailsFile.getReleasableAmt());
        		ps.setString(2,obFacilitydetailsFile.getFacilityID());
        		
                return ps.execute();       
            }  
           }); 
	}
	
	@Override
	public void updateLimitExpDateforNull(final OBReleaselinedetailsFile releaseAmountFile)
			throws FileUploadException, SQLException {
try {
		String updateLimitExpDateActual = "UPDATE CMS_STAGE_LSP_SYS_XREF SET DATE_OF_RESET=(select DATE_OF_RESET from SCI_LSP_SYS_XREF WHERE "
				+ " FACILITY_SYSTEM_ID=? AND " 
				+" LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? and CMS_LSP_SYS_XREF_ID in " 
				+"        (select max(CMS_LSP_SYS_XREF_ID) from SCI_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? AND "  
				+" LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=?)) WHERE FACILITY_SYSTEM_ID=? AND "
				+" LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? and CMS_LSP_SYS_XREF_ID in (select max(CMS_LSP_SYS_XREF_ID) from CMS_STAGE_LSP_SYS_XREF "
				+" where FACILITY_SYSTEM_ID=? AND "
				+" LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=?)";
		getJdbcTemplate().execute(updateLimitExpDateActual,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        		ps.setString(1,releaseAmountFile.getSystemID());	
        		ps.setString(2,releaseAmountFile.getLineNo()); 
				ps.setString(3,releaseAmountFile.getSerialNo());
				ps.setString(4,releaseAmountFile.getLiabBranch());
				ps.setString(5,releaseAmountFile.getSystemID());
				ps.setString(6,releaseAmountFile.getLineNo());
				ps.setString(7,releaseAmountFile.getSerialNo());
				ps.setString(8,releaseAmountFile.getLiabBranch());
				ps.setString(9,releaseAmountFile.getSystemID());	
        		ps.setString(10,releaseAmountFile.getLineNo()); 
				ps.setString(11,releaseAmountFile.getSerialNo());
				ps.setString(12,releaseAmountFile.getLiabBranch());
				ps.setString(13,releaseAmountFile.getSystemID());
				ps.setString(14,releaseAmountFile.getLineNo());
				ps.setString(15,releaseAmountFile.getSerialNo());
				ps.setString(16,releaseAmountFile.getLiabBranch());
                return ps.execute();       
            }  
           });
	}catch(Exception e) {
		System.out.println(e);	
		}
	}
	
	@Override
	public void updateLimitExpDate(final OBReleaselinedetailsFile releaseAmountFile)
			throws FileUploadException, SQLException {
		String updateLimitExpDateActual = "UPDATE CMS_STAGE_LSP_SYS_XREF SET DATE_OF_RESET=? WHERE FACILITY_SYSTEM_ID=? AND "
				+ "LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? and CMS_LSP_SYS_XREF_ID in (select max(CMS_LSP_SYS_XREF_ID) from CMS_STAGE_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? AND "
				+"LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=?)";
		getJdbcTemplate().execute(updateLimitExpDateActual,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        		//java.sql.Date sqlDate = new java.sql.Date(releaseAmountFile.getDateOfReset().getTime());
        		ps.setDate(1, new java.sql.Date(releaseAmountFile.getDateOfReset().getTime()));//setDate(1, (java.sql.Date) releaseAmountFile.getDateOfReset());
        		ps.setString(2,releaseAmountFile.getSystemID());	
        		ps.setString(3,releaseAmountFile.getLineNo()); 
				ps.setString(4,releaseAmountFile.getSerialNo());
				ps.setString(5,releaseAmountFile.getLiabBranch());
				ps.setString(6,releaseAmountFile.getSystemID());
				ps.setString(7,releaseAmountFile.getLineNo());
				ps.setString(8,releaseAmountFile.getSerialNo());
				ps.setString(9,releaseAmountFile.getLiabBranch());
				
                return ps.execute();       
            }  
           }); 
	}
	
	@Override
	public void updatePSLFlagValue(final OBReleaselinedetailsFile releaselinedetailsFile)
			throws FileUploadException, SQLException {
		String query = "UPDATE CMS_STAGE_LSP_SYS_XREF SET IS_PRIORITY_SECTOR=?, 	PRIORITY_SECTOR=?,  PRIORITY_SECTOR_FLAG='M' WHERE FACILITY_SYSTEM_ID=? AND "
				+ "LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? and CMS_LSP_SYS_XREF_ID in (select max(CMS_LSP_SYS_XREF_ID) from CMS_STAGE_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? AND "
				+"LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=?)";
		getJdbcTemplate().execute(query,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {         		
        		ps.setString(1, releaselinedetailsFile.getPslFlag());
        		ps.setString(2, releaselinedetailsFile.getPslValue());
        		ps.setString(3,releaselinedetailsFile.getSystemID());	
        		ps.setString(4,releaselinedetailsFile.getLineNo()); 
				ps.setString(5,releaselinedetailsFile.getSerialNo());
				ps.setString(6,releaselinedetailsFile.getLiabBranch());
				ps.setString(7,releaselinedetailsFile.getSystemID());
				ps.setString(8,releaselinedetailsFile.getLineNo());
				ps.setString(9,releaselinedetailsFile.getSerialNo());
				ps.setString(10,releaselinedetailsFile.getLiabBranch());
				
                return ps.execute();       
            }  
           }); 

		
	}


	@Override
	public void updatePSLFlagValueActual(final OBReleaselinedetailsFile releaselinedetailsFile)
			throws FileUploadException, SQLException {
		try {
		String query = "UPDATE SCI_LSP_SYS_XREF SET IS_PRIORITY_SECTOR=? , PRIORITY_SECTOR=?, PRIORITY_SECTOR_FLAG='M' WHERE FACILITY_SYSTEM_ID=? AND "
				+ "LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? and CMS_LSP_SYS_XREF_ID in (select max(CMS_LSP_SYS_XREF_ID) from SCI_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? AND "
				+"LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=?)";

		getJdbcTemplate().execute(query,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	              
        		ps.setString(1, releaselinedetailsFile.getPslFlag());
        		ps.setString(2, releaselinedetailsFile.getPslValue());
        		ps.setString(3,releaselinedetailsFile.getSystemID());	
        		ps.setString(4,releaselinedetailsFile.getLineNo()); 
				ps.setString(5,releaselinedetailsFile.getSerialNo());
				ps.setString(6,releaselinedetailsFile.getLiabBranch());
				ps.setString(7,releaselinedetailsFile.getSystemID());
				ps.setString(8,releaselinedetailsFile.getLineNo());
				ps.setString(9,releaselinedetailsFile.getSerialNo());
				ps.setString(10,releaselinedetailsFile.getLiabBranch());
				
                return ps.execute();       
            }  
           }); 
	}catch(Exception e) {
	System.out.println(e);	
	}
	}


	@Override
	public void updateRuleID(final OBReleaselinedetailsFile releaselinedetailsFile)
			throws FileUploadException, SQLException {
		String query = "UPDATE CMS_STAGE_LSP_SYS_XREF SET RULE_ID=? WHERE FACILITY_SYSTEM_ID=? AND "
				+ "LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? and CMS_LSP_SYS_XREF_ID in (select max(CMS_LSP_SYS_XREF_ID) from CMS_STAGE_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? AND "
				+"LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=?)";
		getJdbcTemplate().execute(query,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        		ps.setString(1, releaselinedetailsFile.getRuleID());
        		ps.setString(2,releaselinedetailsFile.getSystemID());	
        		ps.setString(3,releaselinedetailsFile.getLineNo()); 
				ps.setString(4,releaselinedetailsFile.getSerialNo());
				ps.setString(5,releaselinedetailsFile.getLiabBranch());
				ps.setString(6,releaselinedetailsFile.getSystemID());
				ps.setString(7,releaselinedetailsFile.getLineNo());
				ps.setString(8,releaselinedetailsFile.getSerialNo());
				ps.setString(9,releaselinedetailsFile.getLiabBranch());
				
                return ps.execute();       
            }  
           }); 		
	}


	@Override
	public void updateRuleIDActual(final OBReleaselinedetailsFile releaselinedetailsFile)
			throws FileUploadException, SQLException {
		try {
			String query = "UPDATE SCI_LSP_SYS_XREF SET RULE_ID=?  WHERE FACILITY_SYSTEM_ID=? AND "
					+ "LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? and CMS_LSP_SYS_XREF_ID in (select max(CMS_LSP_SYS_XREF_ID) from SCI_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? AND "
					+"LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=?)";

			getJdbcTemplate().execute(query,new PreparedStatementCallback(){  
	        
	        	public Object doInPreparedStatement(PreparedStatement ps)  
	        	           throws SQLException {  
	        	              
	        		ps.setString(1, releaselinedetailsFile.getRuleID());
	        		ps.setString(2,releaselinedetailsFile.getSystemID());	
	        		ps.setString(3,releaselinedetailsFile.getLineNo()); 
					ps.setString(4,releaselinedetailsFile.getSerialNo());
					ps.setString(5,releaselinedetailsFile.getLiabBranch());
					ps.setString(6,releaselinedetailsFile.getSystemID());
					ps.setString(7,releaselinedetailsFile.getLineNo());
					ps.setString(8,releaselinedetailsFile.getSerialNo());
					ps.setString(9,releaselinedetailsFile.getLiabBranch());
					
	                return ps.execute();       
	            }  
	           }); 
		}catch(Exception e) {
		System.out.println(e);	
		}
	}

	public void updatePublicInputLEI(final OBLeiDetailsFile obLeiDetailsFile) {
		
		String sqlQuery = " update SCI_LE_MAIN_PROFILE set LEI_CODE =?, LEI_EXPIRY_DATE =? where LMP_LE_ID =?";
		
		getJdbcTemplate().execute(sqlQuery,new PreparedStatementCallback(){  
	        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        		ps.setString(1,obLeiDetailsFile.getLeiCode());	
        		ps.setDate(2,obLeiDetailsFile.getLeiExpDate()); 
				ps.setString(3,obLeiDetailsFile.getPartyId());				
                return ps.execute();       
            }  
           });
	}
	
	public void updateLeiDetailsLeiValidationStatus(final OBLeiDetailsFile obLeiDetailsFile) {
		
		String sqlQuery = " update ACTUAL_LEI_DETAILS_UPLOAD set IS_LEI_VALIDATED='N', LEI_CODE =?, LEI_EXPIRY_DATE =? where LSP_LE_ID =?";
		
		getJdbcTemplate().execute(sqlQuery,new PreparedStatementCallback(){  
	        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        		ps.setString(1,obLeiDetailsFile.getLeiCode());	
        		ps.setDate(2,obLeiDetailsFile.getLeiExpDate()); 
				ps.setString(3,obLeiDetailsFile.getPartyId());				
                return ps.execute();       
            }  
           });
	}

	public void updateSubProfile(final OBLeiDetailsFile obLeiDetailsFile) {
		
		String sqlQuery = " update SCI_LE_SUB_PROFILE set IS_LEI_VALIDATED='N', LEI_CODE =?, LEI_EXPIRY_DATE =? where LSP_LE_ID =?";
		
		getJdbcTemplate().execute(sqlQuery,new PreparedStatementCallback(){  
	        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        		ps.setString(1,obLeiDetailsFile.getLeiCode());	
        		ps.setDate(2,obLeiDetailsFile.getLeiExpDate()); 
				ps.setString(3,obLeiDetailsFile.getPartyId());				
                return ps.execute();       
            }  
           });
	}
	
	public void updateLeiValidationStatusforScheduler(final String partyId,final String leiCode) {
		
		String sqlQuery = " update ACTUAL_LEI_DETAILS_UPLOAD set LEI_VALIDATION_FLAG='Y' where PARTY_ID = ? AND LEI_CODE =? AND STATUS='PASS'";
		
		getJdbcTemplate().execute(sqlQuery,new PreparedStatementCallback(){  
	        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        		ps.setString(1,partyId);	
        		ps.setString(2,leiCode); 
                return ps.execute();       
            }  
           });
	}
	
	public void updateSubProfileforValidatedLei(final OBLeiDetailsFile obLeiDetailsFile) {
		
		String sqlQuery = " update SCI_LE_SUB_PROFILE set LEI_CODE =?, LEI_EXPIRY_DATE =? where LSP_LE_ID =?";
		
		getJdbcTemplate().execute(sqlQuery,new PreparedStatementCallback(){  
	        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        		ps.setString(1,obLeiDetailsFile.getLeiCode());	
        		ps.setDate(2,obLeiDetailsFile.getLeiExpDate()); 
				ps.setString(3,obLeiDetailsFile.getPartyId());				
                return ps.execute();       
            }  
           });
	}
	
	@Override
	public void updateLimitExpDateActual(final OBReleaselinedetailsFile releaseAmountFile)
			throws FileUploadException, SQLException {
try {
		String updateLimitExpDateActual = "UPDATE SCI_LSP_SYS_XREF SET DATE_OF_RESET=? WHERE FACILITY_SYSTEM_ID=? AND "
				+ "LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? and CMS_LSP_SYS_XREF_ID in (select max(CMS_LSP_SYS_XREF_ID) from SCI_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? AND "
				+"LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=?)";

		getJdbcTemplate().execute(updateLimitExpDateActual,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	              
        		ps.setDate(1, new java.sql.Date(releaseAmountFile.getDateOfReset().getTime()));
        		ps.setString(2,releaseAmountFile.getSystemID());	
        		ps.setString(3,releaseAmountFile.getLineNo()); 
				ps.setString(4,releaseAmountFile.getSerialNo());
				ps.setString(5,releaseAmountFile.getLiabBranch());
				ps.setString(6,releaseAmountFile.getSystemID());
				ps.setString(7,releaseAmountFile.getLineNo());
				ps.setString(8,releaseAmountFile.getSerialNo());
				ps.setString(9,releaseAmountFile.getLiabBranch());
				
                return ps.execute();       
            }  
           }); 
	}catch(Exception e) {
	System.out.println(e);	
	}
	}
	
	@Override
	public void updateLimitExpDateActualforNull(final OBReleaselinedetailsFile releaseAmountFile)
			throws FileUploadException, SQLException {
		try {
			String updateLimitExpDateActual = "UPDATE SCI_LSP_SYS_XREF SET DATE_OF_RESET=(select DATE_OF_RESET from SCI_LSP_SYS_XREF WHERE "
					+ " FACILITY_SYSTEM_ID=? AND " 
					+" LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? and CMS_LSP_SYS_XREF_ID in " 
					+"        (select max(CMS_LSP_SYS_XREF_ID) from SCI_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? AND "  
					+" LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=?)) WHERE FACILITY_SYSTEM_ID=? AND "
					+" LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? and CMS_LSP_SYS_XREF_ID in (select max(CMS_LSP_SYS_XREF_ID) from CMS_STAGE_LSP_SYS_XREF "
					+" where FACILITY_SYSTEM_ID=? AND "
					+" LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=?)";
			getJdbcTemplate().execute(updateLimitExpDateActual,new PreparedStatementCallback(){  
	        
	        	public Object doInPreparedStatement(PreparedStatement ps)  
	        	           throws SQLException {  
	        		ps.setString(1,releaseAmountFile.getSystemID());	
	        		ps.setString(2,releaseAmountFile.getLineNo()); 
					ps.setString(3,releaseAmountFile.getSerialNo());
					ps.setString(4,releaseAmountFile.getLiabBranch());
					ps.setString(5,releaseAmountFile.getSystemID());
					ps.setString(6,releaseAmountFile.getLineNo());
					ps.setString(7,releaseAmountFile.getSerialNo());
					ps.setString(8,releaseAmountFile.getLiabBranch());
					ps.setString(9,releaseAmountFile.getSystemID());	
	        		ps.setString(10,releaseAmountFile.getLineNo()); 
					ps.setString(11,releaseAmountFile.getSerialNo());
					ps.setString(12,releaseAmountFile.getLiabBranch());
					ps.setString(13,releaseAmountFile.getSystemID());
					ps.setString(14,releaseAmountFile.getLineNo());
					ps.setString(15,releaseAmountFile.getSerialNo());
					ps.setString(16,releaseAmountFile.getLiabBranch());
	                return ps.execute();       
	            }  
	           });
		}catch(Exception e) {
			System.out.println(e);	
			}
	}
	
	
	public long getFacilittId(final OBReleaselinedetailsFile releaseAmountFile)throws FileUploadException {
		//String releaseAmountFile1=getReleaselinedetailsLiabBranchID(releaseAmountFile);
		int count=0;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		long facilityId = 0;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT LMT_ID FROM SCI_LSP_APPR_LMTS WHERE CMS_LSP_APPR_LMTS_ID=(SELECT CMS_LSP_APPR_LMTS_ID " + 
					" FROM sci_lsp_lmts_xref_map " + 
					" WHERE CMS_LSP_SYS_XREF_ID in " + 
					"  (SELECT max(CMS_LSP_SYS_XREF_ID) " + 
					"  FROM SCI_LSP_SYS_XREF " + 
					"  WHERE FACILITY_SYSTEM_ID = '"+releaseAmountFile.getSystemID()+"'"  +
					" AND LINE_NO='"+releaseAmountFile.getLineNo()+"' AND SERIAL_NO='"+releaseAmountFile.getSerialNo()+"' AND LIAB_BRANCH='"+releaseAmountFile.getLiabBranch()+"'"+
					"  ))");
			
		
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
				
						facilityId = rs.getLong(1);
					
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getFacilittId: "+e.getMessage());
        	e.printStackTrace();
		}
		return facilityId;
	}

	public  BigDecimal getSerialAmt(final OBReleaselinedetailsFile releaseAmountFile)throws FileUploadException {
		//String releaseAmountFile1=getReleaselinedetailsLiabBranchID(releaseAmountFile);
		int count=0;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		BigDecimal releasedAmt = new BigDecimal("0");
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT RELEASED_AMOUNT " + 
					"  FROM SCI_LSP_SYS_XREF " + 
					"  WHERE FACILITY_SYSTEM_ID = '"+releaseAmountFile.getSystemID()+"'"  +
					" AND LINE_NO='"+releaseAmountFile.getLineNo()+"' AND SERIAL_NO='"+releaseAmountFile.getSerialNo()+"' AND LIAB_BRANCH='"+releaseAmountFile.getLiabBranch()+"'"+
					"  and CMS_LSP_SYS_XREF_ID = (SELECT MAX(CMS_LSP_SYS_XREF_ID)   FROM SCI_LSP_SYS_XREF   WHERE FACILITY_SYSTEM_ID = '"+releaseAmountFile.getSystemID()+"' AND LINE_NO='"+releaseAmountFile.getLineNo()+"' AND SERIAL_NO='"+releaseAmountFile.getSerialNo()+"' AND LIAB_BRANCH='"+releaseAmountFile.getLiabBranch()+"' )");
			
		
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
				
					releasedAmt = rs.getBigDecimal(1);
					
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getFacilittId: "+e.getMessage());
        	e.printStackTrace();
		}
		return releasedAmt;
	}
	
	public Double getTotalFundedAmount(String partyID){
		String query="SELECT TOTAL_FUNDED_LIMIT FROM SCI_LE_MAIN_PROFILE WHERE LMP_LE_ID='"+partyID+"'";
		Double totalFundedAmt = null;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL(query);
			rs = dbUtil.executeQuery();
			if(null!=rs) {
				while(rs.next()) {
					totalFundedAmt=rs.getDouble("TOTAL_FUNDED_LIMIT");
				}
			}
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getTotalFundedAmount: "+e.getMessage());
        	e.printStackTrace();
		}
		return totalFundedAmt;
	}
	
	@Override
	public void updateReleaseLineUploadFile(final OBReleaselinedetailsFile obReleaselinedetailsFile) {
		try {
			String updateLimitExpDateActual = "UPDATE actual_releaselinedet_upload SET status=? , upload_status=?, reason=? WHERE SYSTEM_ID=? AND "
					+ "LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? ";

			getJdbcTemplate().execute(updateLimitExpDateActual,new PreparedStatementCallback(){  
	        
	        	public Object doInPreparedStatement(PreparedStatement ps)  
	        	           throws SQLException {  
	        	              
	        		ps.setString(1, obReleaselinedetailsFile.getStatus());
	        		ps.setString(2, obReleaselinedetailsFile.getUploadStatus());
	        		ps.setString(3, obReleaselinedetailsFile.getReason());
	        		ps.setString(4,obReleaselinedetailsFile.getSystemID());	
	        		ps.setString(5,obReleaselinedetailsFile.getLineNo()); 
					ps.setString(6,obReleaselinedetailsFile.getSerialNo());
					ps.setString(7,obReleaselinedetailsFile.getLiabBranch());

					
	                return ps.execute();       
	            }  
	           }); 
		}catch(Exception e) {
		System.out.println(e);	 
		}
		}
	
	@Override
	public void createFacilitylineFile(List<OBCreatefacilitylineFile> createFacilityline) throws FileUploadException,SQLException{
		String sql = "INSERT INTO CMS_CREATE_FACLINE_UPLOAD " +
				"(ID,FILE_NAME,PARTYID,FAC_CATEGORY,FAC_NAME,DUMMY_REF_ID,FACILITY_SYSTEM," + 
				"GRADE,LMT_CRRNCY_ISO_CODE,IS_RELEASED,IS_ADHOC,ADHOC_LMT_AMOUNT," + 
				"SANCTIONED_AMT,RELEASABLE_AMOUNT,SYSTEM_ID,LINE_NO,SERIAL_NO,LIAB_BRANCH," + 
				"RELEASED_AMOUNT,LIMIT_START_DATE,AVAILABLE,REVOLVING_LINE,SENDTOFILE,DATE_OF_RESET," + 
				"FREEZE,SEGMENT_1,IS_CAPITAL_MARKET_EXPOSER,IS_REALESTATE_EXPOSER,RULE_ID," + 
				"IS_PRIORITY_SECTOR,PRIORITY_SECTOR,UNCONDI_CANCL,INTEREST_RATE_TYPE,INT_RATE_FIX,ESTATE_TYPE," + 
				"COMM_REAL_ESTATE_TYPE,LIMIT_TENOR_DAYS,INTERNAL_REMARKS,UPLOADED_BY,UPLOAD_DATE,STATUS,REASON)" +
				" VALUES (CMS_CREATE_FACLINE_SEQ.nextval,?, ?, ? ,?, ?, ?,?, ?, ?, ?,?,?, ? ,?, ?, ?,?, ?, ?, ?,?,?, ? ,?, ?, ?,?, ?, ?, ?,?,?, ? ,?, ?, ?,?, ?,?, ?, ?)";

		DefaultLogger.debug(this, "inside createFacilitylineFile");
		DefaultLogger.debug(this, "inside createFacilitylineFile sql:"+sql);
				final List<OBCreatefacilitylineFile> objectList = createFacilityline;
				final int batchSize = createFacilityline.size();
				
					getJdbcTemplate().batchUpdate(sql,
							new BatchPreparedStatementSetter() {
						public void setValues(PreparedStatement ps, int i)
						throws SQLException {
							OBCreatefacilitylineFile createFacilitylineFile = objectList.get(i);
							
							ps.setString(1,createFacilitylineFile.getFileName());
							ps.setString(2,createFacilitylineFile.getPartyId());
							ps.setString(3,createFacilitylineFile.getFacilityCategory());
							ps.setString(4,createFacilitylineFile.getFacilityName());
							ps.setString(5,createFacilitylineFile.getDummyRefId());
							ps.setString(6,createFacilitylineFile.getSystem());
							ps.setString(7,createFacilitylineFile.getGrade());
							ps.setString(8,createFacilitylineFile.getCurrency());
							ps.setString(9,createFacilitylineFile.getIsReleased());
							ps.setString(10,createFacilitylineFile.getIsAdhoc());
							ps.setString(11,createFacilitylineFile.getAdhoclimitAmt());
							ps.setString(12,createFacilitylineFile.getSanctionedAmt());
							ps.setString(13,createFacilitylineFile.getReleasableAmt());
							ps.setString(14,createFacilitylineFile.getSystemID());
							ps.setString(15,createFacilitylineFile.getLineNo());
							ps.setString(16,createFacilitylineFile.getSerialNo());
							ps.setString(17,createFacilitylineFile.getLiabBranch());
							ps.setString(18,createFacilitylineFile.getReleaseAmount());
						/*	if(null!=createFacilitylineFile.getLimitStartDate()) {
							ps.setDate(19, new java.sql.Date((createFacilitylineFile.getLimitStartDate()).getTime()));
							}
							else {
								ps.setDate(19,null);	
							}*/
							ps.setString(19, createFacilitylineFile.getLimitStartDate());
							ps.setString(20,createFacilitylineFile.getAvailable());
							ps.setString(21,createFacilitylineFile.getRevolvingLine());
							ps.setString(22,createFacilitylineFile.getSendToFile());
							
							/*if(null!=createFacilitylineFile.getLimitExpiryDate()) {
							ps.setDate(23, new java.sql.Date((createFacilitylineFile.getLimitExpiryDate()).getTime()));
							}
							else {
								ps.setDate(23, null);	
							}*/
							
							ps.setString(23, createFacilitylineFile.getLimitExpiryDate());
							ps.setString(24,createFacilitylineFile.getFreeze());
							ps.setString(25,createFacilitylineFile.getSegment1());
							ps.setString(26,createFacilitylineFile.getIsCapitalMarketExpo());
							ps.setString(27,createFacilitylineFile.getIsRealEstateExpo());
							ps.setString(28,createFacilitylineFile.getRuleId());
							ps.setString(29,createFacilitylineFile.getPslFlag());
							ps.setString(30,createFacilitylineFile.getPslValue());
							ps.setString(31,createFacilitylineFile.getUncondCancelComm());
							ps.setString(32,createFacilitylineFile.getInterestRate());
							ps.setString(33,createFacilitylineFile.getRateValue());
							ps.setString(34,createFacilitylineFile.getRealEsExpoValue());
							ps.setString(35,createFacilitylineFile.getCommercialRealEstate());
							ps.setString(36,createFacilitylineFile.getLimitTenor());
							ps.setString(37,createFacilitylineFile.getRemark());
							ps.setString(38,createFacilitylineFile.getUploadedBy());
							ps.setTimestamp(39, new Timestamp((createFacilitylineFile.getUploadDate()).getTime()));
							ps.setString(40,createFacilitylineFile.getStatus());
							ps.setString(41,createFacilitylineFile.getReason());
							
						}
						public int getBatchSize() {
							return objectList.size();
						}
					});
//				}catch(Exception e) {
//					e.printStackTrace();
//					DefaultLogger.debug(this, "Exception in  createFacilitylineFile:"+e.getMessage());
//				}
					
					DefaultLogger.debug(this, "completed createFacilitylineFile");
	}
	
	
	public ArrayList getAutoUpdationResultList(String applicationDate,String flag) {
		ArrayList resultList = new ArrayList();
		if(flag == null || "".equals(flag)) {
			flag = "FALSE";
		}
		System.out.println("getAutoUpdationResultList() => flag =>"+flag);
		
		DBUtil dbUtil=null;
		String data = "";
		
		try {
		
		String sql = "SELECT DISTINCT  " + 
				"SLSP.LSP_LE_ID AS PARTY_ID, " + 
				"SLSP.LSP_SHORT_NAME AS PARTY_NAME, " + 
				"a.LMT_ID as FACILITY_ID, " + 
				"a.FACILITY_NAME as FACILITY_NAME, " + 
//				"  --b.LIAB_BRANCH, " + 
				"  (SELECT BRANCHCODE FROM CMS_FCCBRANCH_MASTER WHERE ID = b.LIAB_BRANCH) AS LIAB_BRANCH, " + 
//				" -- b.FACILITY_SYSTEM_ID, " + 
				"  b.LINE_NO AS LINE_NO, " + 
				"  b.SERIAL_NO AS SERIAL_NO, " + 
				"  CAGD.CMS_COLLATERAL_ID AS SECURITY_ID, " + 
				"SEC.SUBTYPE_NAME AS SECURITY_SUBTYPE, " + 
				"b.STATUS AS STATUS, " + 
				" b.CMS_LSP_SYS_XREF_ID AS CMS_LSP_SYS_XREF_ID, " +
				" b.FACILITY_SYSTEM_ID AS FACILITY_SYSTEM_ID, " + 
				"  b.FACILITY_SYSTEM AS FACILITY_SYSTEM, " +
				" TO_CHAR(CAGD.DUE_DATE,'dd-Mon-yyyy') as DUE_DATE, " +
				"  CAGD.DOC_CODE AS DOC_CODE " +
				" " + 
				"FROM SCI_LSP_SYS_XREF b, " + 
				"  SCI_LSP_APPR_LMTS a, " + 
				"  SCI_LSP_LMTS_XREF_MAP c " + 
				"    ,CMS_LIMIT_SECURITY_MAP MAP, " + 
				"    CMS_ASSET_GC_DET CAGD, " + 
				"    SCI_LE_SUB_PROFILE SLSP, " + 
				"    SCI_LSP_LMT_PROFILE SLLP, " + 
				"    CMS_SECURITY SEC " ;
				
				if("TRUE".equals(flag)) {
				sql = sql + " ,CMS_DOCUMENT_GLOBALLIST CDG ";
				}
				
				sql = sql + "     " + 
				"WHERE    " + 
				"c.CMS_LSP_APPR_LMTS_ID = a.CMS_LSP_APPR_LMTS_ID " + 
				"AND c.CMS_LSP_SYS_XREF_ID  = b.CMS_LSP_SYS_XREF_ID " + 
				"    AND  CAGD.CMS_COLLATERAL_ID            = MAP.CMS_COLLATERAL_ID " + 
				"    AND a.CMS_LSP_APPR_LMTS_ID        = MAP.CMS_LSP_APPR_LMTS_ID " + 
				"    AND SEC.CMS_COLLATERAL_ID = CAGD.CMS_COLLATERAL_ID  " + 
//				"    AND CAGD.DUE_DATE = (SELECT MAX(DUE_DATE) FROM CMS_ASSET_GC_DET WHERE CMS_COLLATERAL_ID = MAP.CMS_COLLATERAL_ID AND ROWNUM = 1) " +
				"    AND CAGD.DUE_DATE IN (SELECT MAX(CA.DUE_DATE)  " + 
				"  FROM CMS_ASSET_GC_DET CA, CMS_DOCUMENT_GLOBALLIST CDG1 " + 
				"  WHERE CA.CMS_COLLATERAL_ID = MAP.CMS_COLLATERAL_ID " + 
				"  AND CA.DOC_CODE = CDG1.DOCUMENT_CODE " + 
				"  AND CDG1.DEPRECATED = 'N' " + 
				"  AND CDG1.STATUS = 'ENABLE' )   " +
				"    AND CAGD.DUE_DATE <= TRUNC(to_date('"+applicationDate+"')) - interval '3' month " + 
				"    AND SLSP.LSP_LE_ID = SLLP.LLP_LE_ID  " + 
				"    AND SLLP.CMS_LSP_LMT_PROFILE_ID = a.CMS_LIMIT_PROFILE_ID " + 
				"    AND b.RELEASED_AMOUNT > 0 " + 
				"    AND b.STATUS IN ('SUCCESS','PENDING') " +	
				"    AND SEC.SECURITY_SUB_TYPE_ID = 'AB100' " ; 
				if("TRUE".equals(flag)) {
						sql = sql + 
				"    AND CDG.DOCUMENT_CODE = CAGD.DOC_CODE  " + 
				"    AND (UPPER(CDG.DOCUMENT_DESCRIPTION) LIKE '%MONTH%' OR UPPER(CDG.DOCUMENT_DESCRIPTION) LIKE '%QUARTER%') " + 		//This line is part of query . Commented this line just for testing purpose
				"    AND CDG.STATUS = 'ENABLE' " +
				"  	 AND CDG.DEPRECATED = 'N' " +
				"    AND CDG.CATEGORY = 'REC' ";
				}
				sql = sql +
				"    AND SEC.STATUS = 'ACTIVE' " + 
				"    AND a.CMS_LIMIT_STATUS = 'ACTIVE' " +
				"    ORDER BY SECURITY_ID";
		
		
		System.out.println("getAutoUpdationResultList => sql=>"+sql);
		ResultSet rs=null;
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		rs = dbUtil.executeQuery();
		if(null!=rs){
			while(rs.next()){
				List resultList1 = new ArrayList();
				data = rs.getString("PARTY_ID");
				resultList1.add(data);
				data = rs.getString("PARTY_NAME");
				resultList1.add(data);
				data = rs.getString("FACILITY_ID");
				resultList1.add(data);
				data = rs.getString("FACILITY_NAME");
				resultList1.add(data);
				data = rs.getString("LIAB_BRANCH");
				resultList1.add(data);
				data = rs.getString("LINE_NO");
				resultList1.add(data);
				data = rs.getString("SERIAL_NO");
				resultList1.add(data);
				data = rs.getString("SECURITY_ID");
				resultList1.add(data);
				data = rs.getString("SECURITY_SUBTYPE");
				resultList1.add(data);
				data = rs.getString("STATUS");
				resultList1.add(data);
				data = rs.getString("CMS_LSP_SYS_XREF_ID");
				resultList1.add(data);
				data = rs.getString("FACILITY_SYSTEM_ID");
				resultList1.add(data);
				data = rs.getString("FACILITY_SYSTEM");
				resultList1.add(data);
				data = rs.getString("DUE_DATE");
				resultList1.add(data);
				data = rs.getString("DOC_CODE");
				resultList1.add(data);
				resultList.add(resultList1);
			}
		}
			rs.close();
		}catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{ 
			try {
				dbUtil.close();
				if(dbUtil != null) {
					dbUtil.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultList;
	}
	
	
	public void createEntireAutoupdationLmtsStageFile(List objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO STAGE_AUTOUPDATIONLMTS_UPLOAD " +   
				"	(ID, FILE_ID, PARTY_ID, PARTY_NAME, FACILITY_ID, FACILITY_NAME, LINE_NO, SERIAL_NO,LIAB_BRANCH,SECURITY_SUB_TYPE,SECURITY_ID,LINE_STATUS,AUTOUPDATION_STATUS,REASON,UPLOAD_STATUS,CMS_LSP_SYS_XREF_ID,FACILITY_SYSTEM_ID,FACILITY_SYSTEM,DUE_DATE,DOC_CODE) " +
		" VALUES (CMS_AUTO_UPDATE_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		final List<OBAutoupdationLmtsFile> objectList = objList;
		final int batchSize = objList.size();
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBAutoupdationLmtsFile autoupdationlmtsFile = objectList.get(i);
					ps.setLong(1,autoupdationlmtsFile.getFileId());
					ps.setString(2,autoupdationlmtsFile.getPartyId());
					ps.setString(3,autoupdationlmtsFile.getPartyName());
					ps.setString(4,autoupdationlmtsFile.getFacilityId());
					ps.setString(5,autoupdationlmtsFile.getFacilityName());
					ps.setString(6,autoupdationlmtsFile.getLineNo());
					ps.setString(7,autoupdationlmtsFile.getSerialNo());
					ps.setString(8,autoupdationlmtsFile.getLiabBranch());
					ps.setString(9,autoupdationlmtsFile.getSecuritySubtype());
					ps.setString(10,autoupdationlmtsFile.getSecurityID());
					ps.setString(11,autoupdationlmtsFile.getLineStatus());
					ps.setString(12,autoupdationlmtsFile.getStatus());
					ps.setString(13,autoupdationlmtsFile.getReason());
					ps.setString(14,autoupdationlmtsFile.getUploadStatus());
					ps.setString(15,autoupdationlmtsFile.getXrefId());
					ps.setString(16,autoupdationlmtsFile.getFacilitySystemId());
					ps.setString(17,autoupdationlmtsFile.getFacilitySystemName());
					ps.setString(18,autoupdationlmtsFile.getDueDateMax());
					ps.setString(19,autoupdationlmtsFile.getDocCode());
					
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
		//}
	}
	
	
	public void createRejectAutoupdationLmtsActualFile(final OBAutoupdationLmtsFile autoupdationlmtsFile)throws FileUploadException,SQLException{
		String sql = "INSERT INTO ACTUAL_AUTOUPDATIONLMTS_UPLOAD " +   
				"	(ID, FILE_ID, PARTY_ID, PARTY_NAME, FACILITY_ID, FACILITY_NAME, LINE_NO, SERIAL_NO,LIAB_BRANCH,SECURITY_SUB_TYPE,SECURITY_ID,LINE_STATUS,AUTOUPDATION_STATUS,REASON,UPLOAD_STATUS,CMS_LSP_SYS_XREF_ID,FACILITY_SYSTEM_ID,FACILITY_SYSTEM,DUE_DATE,DOC_CODE) " +
		" VALUES (CMS_AUTO_UPDATE_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			getJdbcTemplate().execute(sql,new PreparedStatementCallback(){  
	        
	        	public Object doInPreparedStatement(PreparedStatement ps)  
	        	           throws SQLException {  
	        		
	        		ps.setLong(1,autoupdationlmtsFile.getFileId());
					ps.setString(2,autoupdationlmtsFile.getPartyId());
					ps.setString(3,autoupdationlmtsFile.getPartyName());
					ps.setString(4,autoupdationlmtsFile.getFacilityId());
					ps.setString(5,autoupdationlmtsFile.getFacilityName());
					ps.setString(6,autoupdationlmtsFile.getLineNo());
					ps.setString(7,autoupdationlmtsFile.getSerialNo());
					ps.setString(8,autoupdationlmtsFile.getLiabBranch());
					ps.setString(9,autoupdationlmtsFile.getSecuritySubtype());
					ps.setString(10,autoupdationlmtsFile.getSecurityID());
					ps.setString(11,autoupdationlmtsFile.getLineStatus());
					ps.setString(12,autoupdationlmtsFile.getStatus());
					ps.setString(13,autoupdationlmtsFile.getReason());
					ps.setString(14,autoupdationlmtsFile.getUploadStatus());
					ps.setString(15,autoupdationlmtsFile.getXrefId());
					ps.setString(16,autoupdationlmtsFile.getFacilitySystemId());
					ps.setString(17,autoupdationlmtsFile.getFacilitySystemName());
					ps.setString(18,autoupdationlmtsFile.getDueDateMax());
					ps.setString(19,autoupdationlmtsFile.getDocCode());
	                return ps.execute();  
	                      
	            }  
	           });          			
	}	
	
	
	public SearchResult getAllAutoupdationLmtsFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=STAGE_AUTO_UPDATION_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new AutoupdationLmtsFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving AutoupdationLmts List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public SearchResult getAllActualAutoupdationLmtsFile(String id) throws FileUploadException {
		List resultList = null;
		try {
			String SQL=ACTUAL_AUTO_UPDATION_FILE+id+"'";
			resultList = getJdbcTemplate().query(SQL,
					new AutoupdationLmtsFileRowMapper());

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- While retriving AutoupdationLmts List");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public class AutoupdationLmtsFileRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBAutoupdationLmtsFile result = new OBAutoupdationLmtsFile();
			result.setId(rs.getLong("ID"));
			result.setFileId(rs.getLong("FILE_ID"));
			result.setPartyId(rs.getString("PARTY_ID"));
			result.setPartyName(rs.getString("PARTY_NAME"));
			result.setFacilityId(rs.getString("FACILITY_ID"));
			result.setFacilityName(rs.getString("FACILITY_NAME"));
			result.setLineNo(rs.getString("LINE_NO"));
			result.setSerialNo(rs.getString("SERIAL_NO"));
			result.setLiabBranch(rs.getString("LIAB_BRANCH"));
			result.setSecuritySubtype(rs.getString("SECURITY_SUB_TYPE"));
			result.setSecurityID(rs.getString("SECURITY_ID"));
			result.setLineStatus(rs.getString("LINE_STATUS"));
			result.setStatus(rs.getString("AUTOUPDATION_STATUS"));
			result.setReason(rs.getString("REASON"));
			result.setUploadStatus(rs.getString("UPLOAD_STATUS"));
			result.setXrefId(rs.getString("CMS_LSP_SYS_XREF_ID"));
			result.setFacilitySystemId(rs.getString("FACILITY_SYSTEM_ID"));
			result.setFacilitySystemName(rs.getString("FACILITY_SYSTEM"));
			result.setDueDateMax(rs.getString("DUE_DATE"));
			result.setDocCode(rs.getString("DOC_CODE"));
			return result;
			//ID, FILE_ID, PARTY_ID, PARTY_NAME, FACILITY_ID, FACILITY_NAME, LINE_NO, SERIAL_NO,LIAB_BRANCH,SECURITY_SUB_TYPE,SECURITY_ID,LINE_STATUS,AUTOUPDATION_STATUS,REASON,UPLOAD_STATUS,CMS_LSP_SYS_XREF_ID,FACILITY_SYSTEM_ID,FACILITY_SYSTEM
		}
	}
	

	public int getAutoupdationLmtsUplodCount() throws FileUploadException {
		List resultList = null;
		int count=0;
		try {
			String SQL=CHECK_AUTOUPDATIONLMTS_UPLOAD_TRX+" AND (status LIKE 'PENDING%' OR STATUS LIKE 'REJECTED')";
			count=getJdbcTemplate().queryForInt(SQL);			

		} catch (Exception e) {
			throw new FileUploadException("ERROR-- Checkling Auto Updation Limits Transaction in FileUploadJdbcImpl");
		}

		return count;
	}
	
	public void createEntireAutoupdationLmtsActualFile(List objList)throws FileUploadException,SQLException{

		String sql = "INSERT INTO ACTUAL_AUTOUPDATIONLMTS_UPLOAD " +   
				"	(ID, FILE_ID, PARTY_ID, PARTY_NAME, FACILITY_ID, FACILITY_NAME, LINE_NO, SERIAL_NO,LIAB_BRANCH,SECURITY_SUB_TYPE,SECURITY_ID,LINE_STATUS,AUTOUPDATION_STATUS,REASON,UPLOAD_STATUS,CMS_LSP_SYS_XREF_ID,FACILITY_SYSTEM_ID,FACILITY_SYSTEM,DUE_DATE,DOC_CODE) " +
		" VALUES (CMS_AUTO_UPDATE_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";

		final List<OBAutoupdationLmtsFile> objectList = objList;
		final int batchSize = objList.size();
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
				throws SQLException {
					OBAutoupdationLmtsFile autoupdationlmtsFile = objectList.get(i);
					ps.setLong(1,autoupdationlmtsFile.getFileId());
					ps.setString(2,autoupdationlmtsFile.getPartyId());
					ps.setString(3,autoupdationlmtsFile.getPartyName());
					ps.setString(4,autoupdationlmtsFile.getFacilityId());
					ps.setString(5,autoupdationlmtsFile.getFacilityName());
					ps.setString(6,autoupdationlmtsFile.getLineNo());
					ps.setString(7,autoupdationlmtsFile.getSerialNo());
					ps.setString(8,autoupdationlmtsFile.getLiabBranch());
					ps.setString(9,autoupdationlmtsFile.getSecuritySubtype());
					ps.setString(10,autoupdationlmtsFile.getSecurityID());
					ps.setString(11,autoupdationlmtsFile.getLineStatus());
					ps.setString(12,autoupdationlmtsFile.getStatus());
					ps.setString(13,autoupdationlmtsFile.getReason());
					ps.setString(14,autoupdationlmtsFile.getUploadStatus());
					ps.setString(15,autoupdationlmtsFile.getXrefId());
					ps.setString(16,autoupdationlmtsFile.getFacilitySystemId());
					ps.setString(17,autoupdationlmtsFile.getFacilitySystemName());
					ps.setString(18,autoupdationlmtsFile.getDueDateMax());
					ps.setString(19,autoupdationlmtsFile.getDocCode());
					
				}
				public int getBatchSize() {
					return objectList.size();
				}
			});
	}
	
	public void updateAutoupdationLmtsActual(final OBAutoupdationLmtsFile autoupdationlmtsFile)throws FileUploadException,SQLException{
		System.out.println("Inside updateAutoupdationLmtsActual.");
        getJdbcTemplate().execute(updateRelAmtStatusActual,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	            
        		ps.setString(1,autoupdationlmtsFile.getFacilitySystemId());	
				ps.setString(2,autoupdationlmtsFile.getLineNo());
				ps.setString(3,autoupdationlmtsFile.getSerialNo());
				ps.setString(4,autoupdationlmtsFile.getFacilitySystemName());
				ps.setString(5,autoupdationlmtsFile.getXrefId());  
                     
                return ps.execute();  
            }  
           });   
	}
	
	
	public void updateAutoupdationLmtsStage(final OBAutoupdationLmtsFile autoupdationlmtsFile)throws FileUploadException,SQLException{
		System.out.println("Inside updateAutoupdationLmtsStage.");
        getJdbcTemplate().execute(updateRelAmtStatusStaging,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	            
        		ps.setString(1,autoupdationlmtsFile.getFacilitySystemId());	
				ps.setString(2,autoupdationlmtsFile.getLineNo());
				ps.setString(3,autoupdationlmtsFile.getSerialNo());
				ps.setString(4,autoupdationlmtsFile.getFacilitySystemName()); 
                     
                return ps.execute();  
            }  
           });   
	}
	
	
	public void updateAutoupdationLmtsDpAmtActual(final OBAutoupdationLmtsFile autoupdationlmtsFile)throws FileUploadException,SQLException{
		System.out.println("Inside updateAutoupdationLmtsDpAmtActual.");
        getJdbcTemplate().execute(updateDPAmountActual,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	            
        		ps.setString(1,autoupdationlmtsFile.getDocCode());	
				ps.setString(2,autoupdationlmtsFile.getDueDateMax());
				ps.setString(3,autoupdationlmtsFile.getSecurityID());

				return ps.execute();  
            }  
           });   
	}
	
	public void updateAutoupdationLmtsDpAmtStage(final OBAutoupdationLmtsFile autoupdationlmtsFile)throws FileUploadException,SQLException{
		System.out.println("Inside updateAutoupdationLmtsDpAmtStage.");
        getJdbcTemplate().execute(updateDPAmountStaging,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	            
        		ps.setString(1,autoupdationlmtsFile.getDocCode());	
				ps.setString(2,autoupdationlmtsFile.getDueDateMax());
				ps.setString(3,autoupdationlmtsFile.getDocCode());	
				ps.setString(4,autoupdationlmtsFile.getDueDateMax());

				return ps.execute();  
            }  
           });   
	}

	@Override
	public float getReleasedAmount(OBAutoupdationLmtsFile obAutoupdationLmtsFile) {
		DBUtil dbUtil=null;
		float releasedAmt = 0;
		
		try {
		
		String sql = "SELECT RELEASED_AMOUNT FROM  SCI_LSP_SYS_XREF  WHERE FACILITY_SYSTEM_ID = '"+obAutoupdationLmtsFile.getFacilitySystemId()+"'  " + 
				"				 AND LINE_NO='"+obAutoupdationLmtsFile.getLineNo()+"' AND SERIAL_NO='"+obAutoupdationLmtsFile.getSerialNo()+"' AND FACILITY_SYSTEM = '"+obAutoupdationLmtsFile.getFacilitySystemName()+"' AND CMS_LSP_SYS_XREF_ID = '"+obAutoupdationLmtsFile.getXrefId()+"' ";
		
		
		System.out.println("getReleasedAmount => sql=>"+sql);
		ResultSet rs=null;
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		rs = dbUtil.executeQuery();
		if(null!=rs){
			while(rs.next()){
				List resultList1 = new ArrayList();
				releasedAmt = rs.getFloat("RELEASED_AMOUNT");
			}
		}
		rs.close();
		if(dbUtil != null) {
			dbUtil.close();
		}
		}catch(Exception e) {
			System.out.println("Exception in getReleasedAmount");
			e.printStackTrace();
		}
		return releasedAmt;
	}
	
	public void updateTotalReleasedAmountActual(final OBAutoupdationLmtsFile autoupdationlmtsFile,float releaseAmt, final OBAutoupdationLmtsFile autoupdationlmtsFileNew)throws FileUploadException,SQLException{
		System.out.println("Inside updateTotalReleasedAmountActual.");
		DBUtil dbUtil=null;
		float totalReleasedAmt = 0;
		 String lspApprLimitId = "";
		 
		float finalTotalReleasedAmount = 0;
		
		try {
			
		String sql = "SELECT  " + 
				"SCI.TOTAL_RELEASED_AMOUNT AS TOTAL_RELEASED_AMOUNT, " + 
				"SCI.CMS_LSP_APPR_LMTS_ID AS CMS_LSP_APPR_LMTS_ID " + 
				"FROM  " + 
				"SCI_LSP_SYS_XREF b, " + 
				"  SCI_LSP_LMTS_XREF_MAP c, " + 
				"  SCI_LSP_APPR_LMTS SCI " + 
				"WHERE  " + 
				" c.CMS_LSP_APPR_LMTS_ID = SCI.CMS_LSP_APPR_LMTS_ID " + 
				"AND c.CMS_LSP_SYS_XREF_ID  = b.CMS_LSP_SYS_XREF_ID " + 
				"AND b.CMS_LSP_SYS_XREF_ID = '"+autoupdationlmtsFile.getXrefId()+"' ";
		
		
		System.out.println("updateTotalReleasedAmountActual => sql=>"+sql);
		ResultSet rs=null;
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		rs = dbUtil.executeQuery();
		if(null!=rs){
			while(rs.next()){
				List resultList1 = new ArrayList();
				totalReleasedAmt = rs.getFloat("TOTAL_RELEASED_AMOUNT");
			lspApprLimitId = rs.getString("CMS_LSP_APPR_LMTS_ID");
			}
		}
		
		finalTotalReleasedAmount = totalReleasedAmt - releaseAmt;
		System.out.println("finalTotalReleasedAmount = >"+finalTotalReleasedAmount+" lspApprLimitId=>"+lspApprLimitId+" totalReleasedAmt=>"+totalReleasedAmt+" releaseAmt=>"+releaseAmt);
		autoupdationlmtsFileNew.setXrefId(lspApprLimitId);
		rs.close();
		if(dbUtil != null) {
			dbUtil.close();
		}
		
		String sql1 = " UPDATE SCI_LSP_APPR_LMTS SET TOTAL_RELEASED_AMOUNT = "+finalTotalReleasedAmount+" WHERE CMS_LSP_APPR_LMTS_ID = ? ";
		
		
		
		
		getJdbcTemplate().execute(sql1,new PreparedStatementCallback(){  
        
        	public Object doInPreparedStatement(PreparedStatement ps)  
        	           throws SQLException {  
        	            
        		ps.setString(1,autoupdationlmtsFileNew.getXrefId());	
				/*ps.setString(2,autoupdationlmtsFile.getDueDateMax());
				ps.setString(3,autoupdationlmtsFile.getDocCode());	
				ps.setString(4,autoupdationlmtsFile.getDueDateMax());*/
 
				return ps.execute();  
            }  
           });   
		}catch(Exception e) {
			System.out.println("Exception in updateTotalReleasedAmountActual.");
			e.printStackTrace();
		}
		}
	
	public boolean isMFSchemaDetailsUploadTrxPendingOrRejected() {
		List resultList = null;
		int count = 0;
		try {
			count = getJdbcTemplate().queryForInt(CHECK_MF_SCHEMA_DETAILS_UPLOAD_TRX+" AND (status LIKE 'PENDING%' OR STATUS LIKE 'REJECTED')");			
		}
		catch(DataAccessException ex) {
			
		}
		return count > 0;
		
	}

	public Map<String, Boolean> existsPartyIds(List<String> partyIds) {
		if(partyIds.size() == 0) {
			return new HashMap<String, Boolean>();
		}
		StringBuffer sql = new StringBuffer("select lsp_le_id, status from SCI_LE_SUB_PROFILE where lsp_le_id");
		List params = new ArrayList<Long>();
		CommonUtil.buildSQLInList(partyIds, sql, params);
		Map<String, Boolean> returnMap = (Map<String, Boolean>) getJdbcTemplate().query(sql.toString(),
				params.toArray(new Object[0]), new ResultSetExtractor() {
					public Map<String, Boolean> extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map<String, Boolean> result = new HashMap<String, Boolean>();
						while(rs.next()) {
							boolean isActive = ICMSConstant.ACTIVE.equals(rs.getString("status"));
							result.put(rs.getString("lsp_le_id"), isActive);
						}
						return result;
					}
				});

		return returnMap;
	}
	
	
	@Override
	public String getFacilityTransactionStatus(OBReleaselinedetailsFile obj) throws FileUploadException{
		String status="";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT STATUS FROM TRANSACTION WHERE REFERENCE_ID="+ 
					"(SELECT CMS_LSP_APPR_LMTS_ID FROM SCI_LSP_LMTS_XREF_MAP WHERE CMS_LSP_SYS_XREF_ID IN "+
					"(SELECT max(CMS_LSP_SYS_XREF_ID) FROM  SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID='"+obj.getSystemID()+ 
					"' AND SERIAL_NO='"+obj.getSerialNo()+"' AND LINE_NO='"+obj.getLineNo()+"' AND " + 
					"LIAB_BRANCH='"+obj.getLiabBranch()+"')"+")");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					status=rs.getString("STATUS");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getFacilityTransactionStatus: "+e.getMessage());
        	e.printStackTrace();
		}
		
		return status;
	}
	
	public Map<String, ICollateral> existsSecurityIds(List<String> securityIds) {
		if(securityIds.size() == 0) {
			return new HashMap<String, ICollateral>();
		}
		StringBuffer sql = new StringBuffer("select cms_collateral_id, security_sub_type_id, status ")
				.append("from cms_security where cms_collateral_id");
		
		List<String> params = new ArrayList<String>();
		CommonUtil.buildSQLInList(securityIds, sql, params);
		Map<String, ICollateral> returnMap = (Map<String, ICollateral>) getJdbcTemplate().query(sql.toString(),
				params.toArray(new Object[0]), new ResultSetExtractor() {
					public Map<String, ICollateral> extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map<String, ICollateral> result = new HashMap<String, ICollateral>();
						while(rs.next()) {
							ICollateral col =  new OBCollateral();
							col.setSourceSecuritySubType(rs.getString("security_sub_type_id"));
							col.setStatus(rs.getString("status"));
							result.put(String.valueOf(rs.getLong("cms_collateral_id")), col);
						}
						return result;
					}
				});

		return returnMap;
	}

	@Override
	public void createEntireMFSchemaDetailsFile(final List<OBMFSchemaDetailsFile> objectList, boolean isMaster)
			throws SQLException {

		String table = isMaster ? "CMS_MFSCHEMADET_UPLOAD" : "CMS_STAGE_MFSCHEMADET_UPLOAD";

		StringBuffer sql = new StringBuffer("INSERT INTO ").append(table).append(
				" (ID, FILE_ID, PARTY_ID, SOURCE_SEC_ID, SOURCE_SUB_TYPE, SCHEMA_CODE, NO_OF_UNITS, CERTIFICATE_NO, ISSUER_NAME, ")
				.append(" STATUS, REASON, UPLOAD_STATUS, UPLOADED_BY, UPLOAD_DATE) ")
				.append(" VALUES(CMS_FILEUPLOAD_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OBMFSchemaDetailsFile detailFile = objectList.get(i);
				ps.setLong(1, detailFile.getFileId());
				ps.setString(2, detailFile.getPartyId());
				ps.setString(3, detailFile.getSourceSecurityId());
				ps.setString(4, detailFile.getSecuritySubType());
				ps.setString(5, detailFile.getSchemaCode());
				ps.setString(6, detailFile.getNoOfUnits());
				ps.setString(7, detailFile.getCertificateNo());
				ps.setString(8, detailFile.getIssuerName());
				ps.setString(9, detailFile.getStatus());
				ps.setString(10, detailFile.getReason());
				ps.setString(11, detailFile.getUploadStatus());
				ps.setString(12, detailFile.getUploadedBy());
				ps.setTimestamp(13, new Timestamp(detailFile.getUploadDate().getTime()));
			}

			public int getBatchSize() {
				return objectList.size();
			}
		});

	}
	
	public boolean doesSecurityBelongsToParty(String partyId,Long securityId) {
		
		boolean result;
		
		StringBuffer sql = new StringBuffer("select count(*) from cms_security sec ")
				.append("inner join CMS_LIMIT_SECURITY_MAP secmap on secmap.cms_collateral_id = sec.cms_collateral_id ")
				.append("inner join SCI_LSP_LMT_PROFILE prf on prf.CMS_LSP_LMT_PROFILE_ID = secmap.CMS_LSP_LMT_PROFILE_ID ")
				.append("where prf.llp_le_id = ? and sec.cms_collateral_id = ? ");
		
		try {
			
			result = getJdbcTemplate().queryForInt(sql.toString(), new Object[] {partyId, securityId}) > 0 ? true : false;
			
		}catch(DataAccessException ex) {
			result = false;
		}
		
		
		return result;
	}

	public List<OBMFSchemaDetailsFile> getAllMFSchemaDetailsFile(long id){
		String sql = "select * from CMS_STAGE_MFSCHEMADET_UPLOAD where file_id = ?";
		
		List result = null;
		
		result = getJdbcTemplate().query(sql, new Object[] { id }, new RowMapper() {
			
			@Override
			public OBMFSchemaDetailsFile mapRow(ResultSet rs, int idx) throws SQLException {
				OBMFSchemaDetailsFile obj = new OBMFSchemaDetailsFile();
				obj.setId(rs.getLong("id"));
				obj.setFileId(rs.getLong("file_id"));
				obj.setPartyId(rs.getString("party_id"));
				obj.setSourceSecurityId(rs.getString("source_sec_id"));
				obj.setSecuritySubType(rs.getString("source_sub_type"));
				obj.setSchemaCode(rs.getString("schema_code"));
				obj.setNoOfUnits(rs.getString("no_of_units"));
				obj.setCertificateNo(rs.getString("certificate_no"));
				obj.setIssuerName(rs.getString("issuer_name"));
				obj.setStatus(rs.getString("status"));
				obj.setReason(rs.getString("reason"));
				obj.setUploadStatus(rs.getString("upload_status"));
				obj.setUploadedBy(rs.getString("uploaded_by"));
				obj.setUploadDate(rs.getTimestamp("upload_date"));
				return obj;
			}
		});
		
		return result;
	}
	
	public int createSchemaDetail(final IMarketableEquity equity,final long collateralId, boolean isMaster) {
		String table = isMaster ? "CMS_PORTFOLIO_ITEM" : "CMS_STAGE_PORTFOLIO_ITEM";
		String colIdParam = isMaster ? "?" : "(select staging_reference_id from transaction where reference_id = ?)";
		StringBuffer sql = new StringBuffer("INSERT into ").append(table)
				.append(" (ITEM_ID, CMS_REF_ID, CERTIFICATE_NO, NO_OF_UNITS, ISSUER_NAME, STOCK_CODE, NOMINAL_VALUE, ")
				.append(" STATUS, BROKER_NAME, RECOGN_EXCHANGE, CMS_COLLATERAL_ID) ")
				.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ").append(colIdParam).append(" )");
		
		return getJdbcTemplate().update(sql.toString(), new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, equity.getEquityID());
				ps.setLong(2, equity.getRefID());
				ps.setString(3,equity.getCertificateNo());
				ps.setDouble(4, equity.getNoOfUnits());
				ps.setString(5, equity.getIssuerName());
				ps.setString(6, equity.getStockCode());
				ps.setDouble(7, equity.getNominalValue().getAmountAsDouble());
				ps.setString(8, equity.getStatus());
				ps.setString(9, equity.getBrokerName());
				ps.setString(10, equity.getRecognizeExchange());
				ps.setLong(11, collateralId);
			}
		});
	}
	
	public void createEntireStockDetailsFile(final List<OBStockDetailsFile> objectList, boolean isMaster) throws SQLException{

		String table = isMaster ? "CMS_STOCK_DET_UPLOAD" : "CMS_STAGE_STOCK_DET_UPLOAD";

		StringBuffer sql = new StringBuffer("INSERT INTO ").append(table).append(
				" (ID, FILE_ID, PARTY_ID, SOURCE_SEC_ID, SOURCE_SUB_TYPE, NAME_OF_STK_EXCHG, SCRIPT_CODE, NO_OF_UNITS, ISSUER_ID_TYPE, NOMINAL_VALUE, ")
				.append(" CERTIFICATE_NO, ISSUER_NAME, STATUS, REASON, UPLOAD_STATUS, UPLOADED_BY, UPLOAD_DATE) ")
				.append(" VALUES(CMS_FILEUPLOAD_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OBStockDetailsFile detailFile = objectList.get(i);
				ps.setLong(1, detailFile.getFileId());
				ps.setString(2, detailFile.getPartyId());
				ps.setString(3, detailFile.getSourceSecurityId());
				ps.setString(4, detailFile.getSecuritySubType());
				ps.setString(5, detailFile.getNameOfStockExchange());
				ps.setString(6, detailFile.getScriptCode());
				ps.setString(7, detailFile.getNoOfUnits());
				ps.setString(8, detailFile.getIssuerIdType());
				ps.setString(9, detailFile.getNominalValue());
				ps.setString(10, detailFile.getCertificateNo());
				ps.setString(11, detailFile.getIssuerName());
				ps.setString(12, detailFile.getStatus());
				ps.setString(13, detailFile.getReason());
				ps.setString(14, detailFile.getUploadStatus());
				ps.setString(15, detailFile.getUploadedBy());
				ps.setTimestamp(16, new Timestamp(detailFile.getUploadDate().getTime()));
			}

			public int getBatchSize() {
				return objectList.size();
			}
		});

	}
	
	public List<OBStockDetailsFile> getAllStockDetailsFile(long id){
		String sql = "select * from CMS_STAGE_STOCK_DET_UPLOAD where file_id = ?";	
		
		List result = null;
		
		result = getJdbcTemplate().query(sql, new Object[] { id }, new RowMapper() {
			
			public OBStockDetailsFile mapRow(ResultSet rs, int idx) throws SQLException {
				OBStockDetailsFile obj = new OBStockDetailsFile();
				obj.setId(rs.getLong("id"));
				obj.setFileId(rs.getLong("file_id"));
				obj.setPartyId(rs.getString("party_id"));
				obj.setSourceSecurityId(rs.getString("source_sec_id"));
				obj.setSecuritySubType(rs.getString("source_sub_type"));
				obj.setNameOfStockExchange(rs.getString("name_of_stk_exchg"));
				obj.setScriptCode(rs.getString("script_code"));
				obj.setNoOfUnits(rs.getString("no_of_units"));
				obj.setIssuerIdType(rs.getString("issuer_id_type"));
				obj.setNominalValue(rs.getString("nominal_value"));
				obj.setCertificateNo(rs.getString("certificate_no"));
				obj.setIssuerName(rs.getString("issuer_name"));
				obj.setStatus(rs.getString("status"));
				obj.setReason(rs.getString("reason"));
				obj.setUploadStatus(rs.getString("upload_status"));
				obj.setUploadedBy(rs.getString("uploaded_by"));
				obj.setUploadDate(rs.getTimestamp("upload_date"));
				return obj;
			}
		});
		
		return result;
	}
	
	public int createStockDetails(final IMarketableEquity equity,final long collateralId, boolean isMaster) {
		String table = isMaster ? "CMS_PORTFOLIO_ITEM" : "CMS_STAGE_PORTFOLIO_ITEM";
		String colIdParam = isMaster ? "?" : "(select staging_reference_id from transaction where reference_id = ?)";
		StringBuffer sql = new StringBuffer("INSERT into ").append(table)
				.append(" (ITEM_ID, CMS_REF_ID, STATUS, STOCK_EXCHANGE, ISIN_CODE, UNIT_PRICE, NO_OF_UNITS, ")
				.append("  ISSUER_ID_TYPE, NOMINAL_VALUE, CERTIFICATE_NO, ISSUER_NAME , CMS_COLLATERAL_ID) ")
				.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ").append(colIdParam).append(" )");
		
		return getJdbcTemplate().update(sql.toString(), new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, equity.getEquityID());
				ps.setLong(2, equity.getRefID());
				ps.setString(3, equity.getStatus());
				ps.setString(4, equity.getStockExchange());
				ps.setString(5, equity.getIsinCode());
				ps.setDouble(6, equity.getUnitPrice() != null ? equity.getUnitPrice().getAmountAsDouble() : 0);
				ps.setDouble(7, equity.getNoOfUnits());
				ps.setString(8, equity.getIssuerIdType());
				ps.setDouble(9, equity.getNominalValue() != null ? equity.getNominalValue().getAmountAsDouble() : 0);
				ps.setString(10,equity.getCertificateNo());
				ps.setString(11, equity.getIssuerName());
				ps.setLong(12, collateralId);
			}
		});
	}

	public boolean isStockDetailsUploadTrxPendingOrRejected() {
		List resultList = null;
		int count = 0;
		try {
			count = getJdbcTemplate().queryForInt(CHECK_STOCK_DETAILS_UPLOAD_TRX+" AND (status LIKE 'PENDING%' OR STATUS LIKE 'REJECTED')");			
		}
		catch(DataAccessException ex) {
			DefaultLogger.error(this, "Error encountered in isStockDetailsUploadTrxPendingOrRejected :", ex);
			throw new FileUploadException("Error encountered in isStockDetailsUploadTrxPendingOrRejected in FileUploadJdbcImpl");
		}
		return count > 0;
		
	}
	
	public boolean isBondDetailsUploadTrxPendingOrRejected() {
		List resultList = null;
		int count = 0;
		try {
			count = getJdbcTemplate().queryForInt(CHECK_BOND_DETAILS_UPLOAD_TRX+" AND (status LIKE 'PENDING%' OR STATUS LIKE 'REJECTED')");			
		}
		catch(DataAccessException ex) {
			
		}
		return count > 0;
	}
	
	public void createEntireBondDetailsFile(final List<OBBondDetailsFile> objectList, boolean isMaster) throws SQLException {
		String table = isMaster ? "CMS_BOND_DTL_UPLOAD" : "CMS_STAGE_BOND_DTL_UPLOAD";

		StringBuffer sql = new StringBuffer("INSERT INTO ").append(table).append(
				" (ID, FILE_ID, PARTY_ID, SOURCE_SEC_ID, SOURCE_SUB_TYPE, BOND_CODE, NO_OF_UNITS, INTEREST, ")
				.append(" STATUS, REASON, UPLOAD_STATUS, UPLOADED_BY, UPLOAD_DATE) ")
				.append(" VALUES(CMS_FILEUPLOAD_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OBBondDetailsFile detailFile = objectList.get(i);
				ps.setLong(1, detailFile.getFileId());
				ps.setString(2, detailFile.getPartyId());
				ps.setString(3, detailFile.getSourceSecurityId());
				ps.setString(4, detailFile.getSecuritySubType());
				ps.setString(5, detailFile.getBondCode());
				ps.setString(6, detailFile.getNoOfUnits());
				ps.setString(7, detailFile.getInterest());
				ps.setString(8, detailFile.getStatus());
				ps.setString(9, detailFile.getReason());
				ps.setString(10, detailFile.getUploadStatus());
				ps.setString(11, detailFile.getUploadedBy());
				ps.setTimestamp(12, new Timestamp(detailFile.getUploadDate().getTime()));
			}


			public int getBatchSize() {
				return objectList.size();
			}
		});
	}

	public List<OBBondDetailsFile> getAllBondDetailsFile(long id) {

		String sql = "select * from CMS_STAGE_BOND_DTL_UPLOAD where file_id = ?";
		
		List result = null;
		
		result = getJdbcTemplate().query(sql, new Object[] { id }, new RowMapper() {
			
			@Override
			public OBBondDetailsFile mapRow(ResultSet rs, int idx) throws SQLException {
				OBBondDetailsFile obj = new OBBondDetailsFile();
				obj.setId(rs.getLong("id"));
				obj.setFileId(rs.getLong("file_id"));
				obj.setPartyId(rs.getString("party_id"));
				obj.setSourceSecurityId(rs.getString("source_sec_id"));
				obj.setSecuritySubType(rs.getString("source_sub_type"));
				obj.setBondCode(rs.getString("bond_code"));
				obj.setNoOfUnits(rs.getString("no_of_units"));
				obj.setInterest(rs.getString("interest"));
				obj.setStatus(rs.getString("status"));
				obj.setReason(rs.getString("reason"));
				obj.setUploadStatus(rs.getString("upload_status"));
				obj.setUploadedBy(rs.getString("uploaded_by"));
				obj.setUploadDate(rs.getTimestamp("upload_date"));
				return obj;
			}
		});
		
		return result;
	}
	
	public int createBondDetail(final IMarketableEquity equity,final long collateralId, boolean isMaster) {
		String table = isMaster ? "CMS_PORTFOLIO_ITEM" : "CMS_STAGE_PORTFOLIO_ITEM";
		String colIdParam = isMaster ? "?" : "(select staging_reference_id from transaction where reference_id = ?)";
		StringBuffer sql = new StringBuffer("INSERT into ").append(table)
				.append(" (ITEM_ID, CMS_REF_ID, BOND_RATING, NO_OF_UNITS, STOCK_CODE, UNIT_PRICE, ")
				.append(" STATUS, CMS_COLLATERAL_ID) ")
				.append(" values (?, ?, ?, ?, ?, ?, ?, ").append(colIdParam).append(" )");
		
		return getJdbcTemplate().update(sql.toString(), new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, equity.getEquityID());
				ps.setLong(2, equity.getRefID());
				ps.setString(3,equity.getBondRating());
				ps.setDouble(4, equity.getNoOfUnits());
				ps.setString(5, equity.getStockCode());
				ps.setDouble(6, equity.getUnitPrice().getAmountAsDouble());
				ps.setString(7, equity.getStatus());
				ps.setLong(8, collateralId);
			}
		});
	
		
	}
	

	
	
	@Override
	public void createEntireBulkUDFTempFile(List<OBTempBulkUDFFileUpload> batchList)
			throws FileUploadException, SQLException {
			String sql = "INSERT INTO TEMP_BULK_UDF_UPLOAD " +
					"(ID, FILE_ID, TYPE_OF_UDF, PARTY_ID,CAM_NO,SYSTEM_ID,LINE_NO,SERIAL_NO,LIAB_BRANCH,UDF_FIELD_SEQUENCE,UDF_FIELD_NAME,UDF_FIELD_VALUE,VALID,REMARKS,REASON,STATUS,UPLOAD_STATUS,CMS_LE_MAIN_PROFILE_ID,CMS_LSP_LMT_PROFILE_ID,SCI_LSP_SYS_XREF_ID)" +
					" VALUES (CMS_FILEUPLOAD_SEQ.nextval, ?, ? ,?, ?, ?,?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?)";

			final List<OBTempBulkUDFFileUpload> objectList = batchList;
			final int batchSize = batchList.size();
						getJdbcTemplate().batchUpdate(sql,
								new BatchPreparedStatementSetter() {
							public void setValues(PreparedStatement ps, int i)
							throws SQLException {
								OBTempBulkUDFFileUpload bulkudfFile = objectList.get(i);
			                    ps.setLong(1,bulkudfFile.getFileId());
								ps.setString(2,bulkudfFile.getTypeOfUDF());
								ps.setString(3,bulkudfFile.getPartyID());
								ps.setString(4,bulkudfFile.getCamNo());
								ps.setString(5,bulkudfFile.getSystemId());
								ps.setString(6,bulkudfFile.getLineNumber());
								ps.setString(7,bulkudfFile.getSerialNumber());
								ps.setString(8,bulkudfFile.getLiabBranch());
								ps.setString(9,bulkudfFile.getUdfFieldSequence());
								ps.setString(10,bulkudfFile.getUdfFieldName());
								ps.setString(11,bulkudfFile.getUdfFieldValue());
								ps.setString(12, bulkudfFile.getValid());
								ps.setString(13, bulkudfFile.getRemarks());
								ps.setString(14, bulkudfFile.getReason());
								ps.setString(15, bulkudfFile.getStatus());
								ps.setString(16, bulkudfFile.getUploadStatus());
							    ps.setString(17, bulkudfFile.getCMS_LE_MAIN_PROFILE_ID());
							    ps.setString(18, bulkudfFile.getCMS_LSP_LMT_PROFILE_ID());
							    ps.setString(19, bulkudfFile.getSCI_LSP_SYS_XREF_ID());
								//Setters Getter not created for below:
								/*private String CMS_LE_MAIN_PROFILE_ID;
								private String CMS_LSP_LMT_PROFILE_ID;
								private String SCI_LSP_SYS_XREF_ID;*/
							}
							public int getBatchSize() {
								return objectList.size();
							}
						});
		}
		
	
	
	public List<OBTempBulkUDFFileUpload> updateStagingCompBulkUDFTemp() {
				
    return getJdbcTemplate().query("SELECT FILE_ID, TYPE_OF_UDF, PARTY_ID,CAM_NO,SYSTEM_ID,LINE_NO,SERIAL_NO,LIAB_BRANCH,"
    		+ "UDF_FIELD_SEQUENCE,UDF_FIELD_NAME,UDF_FIELD_VALUE,VALID,REMARKS,REASON,STATUS,UPLOAD_STATUS,"
    		+ "CMS_LE_MAIN_PROFILE_ID,CMS_LSP_LMT_PROFILE_ID,SCI_LSP_SYS_XREF_ID FROM TEMP_BULK_UDF_UPLOAD", new RowMapper() { 
		  
        public OBTempBulkUDFFileUpload mapRow(ResultSet rs, int rowNum) throws SQLException { 
        	OBTempBulkUDFFileUpload bulkUdf = new OBTempBulkUDFFileUpload();
        	
        	bulkUdf.setFileId(rs.getLong("FILE_ID"));
        	bulkUdf.setTypeOfUDF(rs.getString("TYPE_OF_UDF"));
        	bulkUdf.setPartyID(rs.getString("PARTY_ID"));
        	bulkUdf.setCamNo(rs.getString("CAM_NO"));
        	bulkUdf.setSystemId(rs.getString("SYSTEM_ID"));
        	bulkUdf.setLineNumber(rs.getString("LINE_NO"));
        	bulkUdf.setSerialNumber(rs.getString("SERIAL_NO"));
        	bulkUdf.setLiabBranch(rs.getString("LIAB_BRANCH"));
        	bulkUdf.setUdfFieldSequence(rs.getString("UDF_FIELD_SEQUENCE"));
        	bulkUdf.setUdfFieldName(rs.getString("UDF_FIELD_NAME"));
        	bulkUdf.setUdfFieldValue(rs.getString("UDF_FIELD_VALUE"));
        	bulkUdf.setValid(rs.getString("VALID"));
        	bulkUdf.setRemarks(rs.getString("REMARKS"));
        	bulkUdf.setReason(rs.getString("REASON"));
        	bulkUdf.setStatus(rs.getString("STATUS"));
        	bulkUdf.setUploadStatus(rs.getString("UPLOAD_STATUS"));
        	bulkUdf.setCMS_LE_MAIN_PROFILE_ID(rs.getString("CMS_LE_MAIN_PROFILE_ID"));
          	bulkUdf.setCMS_LSP_LMT_PROFILE_ID(rs.getString("CMS_LSP_LMT_PROFILE_ID"));
        	bulkUdf.setSCI_LSP_SYS_XREF_ID(rs.getString("SCI_LSP_SYS_XREF_ID"));
        	
            return bulkUdf; 
        } 
	
		
	});
	}
	
	public boolean checkauth(String type,String s)
	{
        String typeUdf= type;
		int count = 0;
		try {
			if("party".equalsIgnoreCase(typeUdf))
			{
			count = getJdbcTemplate().queryForInt("select count(*) from Transaction where CUSTOMER_ID in"
					+ " (Select CMS_LE_SUB_PROFILE_ID from SCI_LE_SUB_PROFILE where LSP_LE_ID='"+s+"' )"
					+ " AND STATUS like 'PENDING%'");
			}
			else if("cam".equalsIgnoreCase(typeUdf))
			{
			count = getJdbcTemplate().queryForInt("Select count(*) from transaction where LIMIT_PROFILE_REF_NUM in"
					+ " (select LLP_BCA_REF_NUM from SCI_LSP_LMT_PROFILE where LLP_BCA_REF_NUM='"+s+"')"
					+ " AND STATUS like 'PENDING%' "
					);
			}
		}
		catch(DataAccessException ex) {
			
		}
		if (count==0)
		return true;
		
		else
			return false;
	}
	
	public String checkauthForLine(String systemId,String lineNo, String serialNo, String liabBranch)
	{
		String status="";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT STATUS FROM SCI_LSP_SYS_XREF WHERE CMS_LSP_SYS_XREF_ID=(" + 
					"SELECT MAX(CMS_LSP_SYS_XREF_ID) FROM SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID='"+systemId+"' AND " + 
					"SERIAL_NO='"+serialNo+"' AND LINE_NO='"+lineNo+"' AND LIAB_BRANCH='"+liabBranch
					+"')");
			
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					status=rs.getString("STATUS");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsStatus: "+e.getMessage());
        	e.printStackTrace();
		}
		return status;
	}
	
	public String checkauthForLimit(String systemId,String lineNo, String serialNo, String liabBranch)
	{
		String status="";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT STATUS FROM TRANSACTION WHERE REFERENCE_ID="+ 
					"(SELECT CMS_LSP_APPR_LMTS_ID FROM SCI_LSP_LMTS_XREF_MAP WHERE CMS_LSP_SYS_XREF_ID IN "+
					"(SELECT max(CMS_LSP_SYS_XREF_ID) FROM  SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID='"+systemId+ 
					"' AND SERIAL_NO='"+serialNo+"' AND LINE_NO='"+lineNo+"' AND " + 
					"LIAB_BRANCH='"+liabBranch+"')"+")");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					status=rs.getString("STATUS");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in checkauthForLimit: "+e.getMessage());
        	e.printStackTrace();
		}
		
		return status;
	}
	
	public String getLimitIdForLine(String systemId,String lineNo, String serialNo, String liabBranch){
		String id="";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("(SELECT max(CMS_LSP_SYS_XREF_ID) CMS_LSP_SYS_XREF_ID FROM  SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID='"+systemId+ 
					"' AND SERIAL_NO='"+serialNo+"' AND LINE_NO='"+lineNo+"' AND " + 
					"LIAB_BRANCH='"+liabBranch+"')");
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					id=rs.getString("CMS_LSP_SYS_XREF_ID");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getLimitIdForLine: "+e.getMessage());
        	e.printStackTrace();
		}
		
		return id;
	}
	
	public void updatePartyTempBulkUdf(List<OBTempBulkUDFFileUpload> ob1)
	{
	String query = "UPDATE TEMP_BULK_UDF_UPLOAD set VALID='TRUE', REMARKS='APPROVED' where VALID='true' and REMARKS is null and "
			+ "CMS_LE_MAIN_PROFILE_ID in (select sci.CMS_LE_MAIN_PROFILE_ID from SCI_LE_UDF sci, CMS_UDF cms where cms.SEQUENCE=? "
					+ "and cms.FIELDNAME=? and sci.CMS_LE_MAIN_PROFILE_ID=?"
			+ " )";	
	final List<OBTempBulkUDFFileUpload> objectList = ob1;
	
	getJdbcTemplate().batchUpdate(query,
			new BatchPreparedStatementSetter() {
		public void setValues(PreparedStatement ps, int i)
		throws SQLException { 
    		OBTempBulkUDFFileUpload ob=objectList.get(i);
    	            ps.setString(1, ob.getUdfFieldSequence());
    	            ps.setString(2, ob.getUdfFieldName());
    	            ps.setString(3, ob.getCMS_LE_MAIN_PROFILE_ID());
        }

		@Override
		public int getBatchSize() {
			return objectList.size();
		}  
       });   
	}
	
	public void updateTempToStageBulkUdfParty(OBTempBulkUDFFileUpload ob1, String str)
	{
	final OBTempBulkUDFFileUpload ob = ob1;
		String seq=str;
		
		String query1 = "UPDATE SCI_LE_UDF sci set UDF"+seq+"=? where sci.CMS_LE_MAIN_PROFILE_ID in "
				+ "(select temp.CMS_LE_MAIN_PROFILE_ID from TEMP_BULK_UDF_UPLOAD temp where temp.UDF_FIELD_SEQUENCE=? "
						+ "and temp.UDF_FIELD_NAME=? and temp.PARTY_ID=?"
				+ " )";	
		
		/*String query2="UPDATE STAGE_SCI_LE_UDF sci set UDF"+seq+"=? where sci.CMS_LE_MAIN_PROFILE_ID in " 
								+ "(select temp.CMS_LE_MAIN_PROFILE_ID from TEMP_BULK_UDF_UPLOAD temp where temp.UDF_FIELD_SEQUENCE=? "  
								+ "and temp.UDF_FIELD_NAME=? and temp.PARTY_ID=?" 
						       	+ ")";	*/
		
		String query2=" UPDATE STAGE_SCI_LE_UDF sci set UDF"+seq+"=? where sci.CMS_LE_MAIN_PROFILE_ID in "
				+ "(select max(CMS_LE_MAIN_PROFILE_ID) from STAGE_SCI_LE_MAIN_PROFILE where LMP_LE_ID=? ) ";
		
		//select max(CMS_LE_MAIN_PROFILE_ID) from STAGE_SCI_LE_MAIN_PROFILE where LMP_LE_ID='CUS0004110'
		DBUtil dbUtil = null;
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(query1);
			dbUtil.setString(1, ob.getUdfFieldValue());
			dbUtil.setString(2, ob.getUdfFieldSequence());
            dbUtil.setString(3, ob.getUdfFieldName());
            dbUtil.setString(4, ob.getPartyID());
			dbUtil.executeUpdate();
			
			dbUtil.setSQL(query2);
			dbUtil.setString(1, ob.getUdfFieldValue());
//			dbUtil.setString(2, ob.getUdfFieldSequence());
//            dbUtil.setString(3, ob.getUdfFieldName());
            dbUtil.setString(2, ob.getPartyID());
			dbUtil.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public void updateCamTempBulkUdf(List<OBTempBulkUDFFileUpload> ob1)
	{
		String query = "UPDATE TEMP_BULK_UDF_UPLOAD set VALID='TRUE', REMARKS='APPROVED' where VALID='true' and REMARKS is null and "
				+ "CMS_LSP_LMT_PROFILE_ID in (select cam.CMS_LSP_LMT_PROFILE_ID from SCI_LSP_LMT_PROFILE_UDF cam, CMS_UDF cms where cms.SEQUENCE=? "
						+ "and cms.FIELDNAME=? and cam.CMS_LSP_LMT_PROFILE_ID=?"
				+ " )";	
		
		final List<OBTempBulkUDFFileUpload> objectList = ob1;
		
		getJdbcTemplate().batchUpdate(query,
				new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)
			throws SQLException {
				OBTempBulkUDFFileUpload ob=objectList.get(i);
	    	            ps.setString(1, ob.getUdfFieldSequence());
	    	            ps.setString(2, ob.getUdfFieldName());
	    	            ps.setString(3, ob.getCMS_LSP_LMT_PROFILE_ID());

				
	        }

			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return objectList.size();
			}  
	       });   
	}
	
	public void updateTempToStageBulkUdfCam(OBTempBulkUDFFileUpload ob1, String str)
	{
		final OBTempBulkUDFFileUpload ob = ob1;
		String seq=str;
		
		String query1 = "UPDATE SCI_LSP_LMT_PROFILE_UDF sci set UDF"+seq+"=? where sci.CMS_LSP_LMT_PROFILE_ID in "
				+ "(select temp.CMS_LSP_LMT_PROFILE_ID from TEMP_BULK_UDF_UPLOAD temp where temp.UDF_FIELD_SEQUENCE=? "
						+ "and temp.UDF_FIELD_NAME=? and temp.CAM_NO=?"
				+ " )";	
		
		/*String query2="UPDATE STAGE_LIMIT_PROFILE_UDF sci set UDF"+seq+"=? where sci.CMS_LSP_LMT_PROFILE_ID in " 
								+ "(select temp.CMS_LSP_LMT_PROFILE_ID from TEMP_BULK_UDF_UPLOAD temp where temp.UDF_FIELD_SEQUENCE=? "  
								+ "and temp.UDF_FIELD_NAME=? and temp.CAM_NO=?" 
						       	+ ")";	*/
		
		String query2="UPDATE STAGE_LIMIT_PROFILE_UDF sci set UDF"+seq+"=? where sci.CMS_LSP_LMT_PROFILE_ID in " 
				+ "(select max(CMS_LSP_LMT_PROFILE_ID) from STAGE_LIMIT_PROFILE where LLP_BCA_REF_NUM=?)";	
		
		// select max(CMS_LSP_LMT_PROFILE_ID) from STAGE_LIMIT_PROFILE where LLP_BCA_REF_NUM='432524234322'
		  
		
		DBUtil dbUtil = null;
		try {
			dbUtil=new DBUtil();
			
			dbUtil.setSQL(query1);
			dbUtil.setString(1, ob.getUdfFieldValue());
			dbUtil.setString(2, ob.getUdfFieldSequence());
			dbUtil.setString(3, ob.getUdfFieldName());
			dbUtil.setString(4, ob.getCamNo());
			dbUtil.executeUpdate();
			

			dbUtil.setSQL(query2);
			dbUtil.setString(1, ob.getUdfFieldValue());
//			dbUtil.setString(2, ob.getUdfFieldSequence());
//			dbUtil.setString(3, ob.getUdfFieldName());
			dbUtil.setString(2, ob.getCamNo());
			dbUtil.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public void updateLimitTempBulkUdf(List<OBTempBulkUDFFileUpload> ob1)
	{
		String query = "UPDATE TEMP_BULK_UDF_UPLOAD set VALID='TRUE', REMARKS='APPROVED' where VALID='true' and REASON is null and "
				+ "SCI_LSP_SYS_XREF_ID in (select limit.SCI_LSP_SYS_XREF_ID from SCI_LSP_LMT_XREF_UDF limit, CMS_UDF cms where cms.SEQUENCE=? "
						+ "and cms.FIELDNAME=? and limit.SCI_LSP_SYS_XREF_ID=?"
				+ " )";	
		final List<OBTempBulkUDFFileUpload> objectList = ob1;
		
		getJdbcTemplate().batchUpdate(query,
				new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)
			throws SQLException {
				OBTempBulkUDFFileUpload ob=objectList.get(i);  
	    	            ps.setString(1, ob.getUdfFieldSequence());
	    	            ps.setString(2, ob.getUdfFieldName());
	    	            ps.setString(3, ob.getSCI_LSP_SYS_XREF_ID());

				  
	        }

			@Override
			public int getBatchSize() {
				
				return objectList.size();
			}  
	       }); 
	}
	
	
	
	public void updateTempToStageBulkUdfLimits(OBTempBulkUDFFileUpload ob1, String str)
	{
		final OBTempBulkUDFFileUpload ob = ob1;
		String seq=str;
		int i=Integer.parseInt(seq); 
		 if(i>=1 && i<=120 )
		 {
			 String query1 = "UPDATE SCI_LSP_LMT_XREF_UDF sci set UDF"+seq+"_VALUE=? where sci.SCI_LSP_SYS_XREF_ID in "
						+ "(select temp.SCI_LSP_SYS_XREF_ID from TEMP_BULK_UDF_UPLOAD temp where temp.UDF_FIELD_SEQUENCE=? "
								+ " and temp.UDF_FIELD_NAME=? and temp.SYSTEM_ID=? and temp.LINE_NO=? and temp.SERIAL_NO=?"
								+ " and temp.LIAB_BRANCH=? "
						+ " )";	
				
				/*String query2="UPDATE STAGE_SCI_LSP_LMT_XREF_UDF sci set UDF"+seq+"_VALUE=? where sci.SCI_LSP_SYS_XREF_ID in " 
						+ "(select temp.SCI_LSP_SYS_XREF_ID from TEMP_BULK_UDF_UPLOAD temp where temp.UDF_FIELD_SEQUENCE=? "
						+ " and temp.UDF_FIELD_NAME=? and temp.SYSTEM_ID=? and temp.LINE_NO=? and temp.SERIAL_NO=?"
						+ " and temp.LIAB_BRANCH=? "
				+ " )";	*/
				
			 String query2= " UPDATE STAGE_SCI_LSP_LMT_XREF_UDF sci set UDF"+seq+"_VALUE=? where sci.SCI_LSP_SYS_XREF_ID in (SELECT MAX(CMS_LSP_SYS_XREF_ID) FROM CMS_STAGE_LSP_SYS_XREF TEMP WHERE TEMP.FACILITY_SYSTEM_ID = ? AND temp.LINE_NO =?  AND temp.SERIAL_NO=? and temp.LIAB_BRANCH=? ) ";
				
				
				DBUtil dbUtil = null;
				try {
					dbUtil=new DBUtil();
					
					dbUtil.setSQL(query1);
					dbUtil.setString(1, ob.getUdfFieldValue());    
					dbUtil.setString(2, ob.getUdfFieldSequence()); 
					dbUtil.setString(3, ob.getUdfFieldName());     
					dbUtil.setString(4, ob.getSystemId());         
					dbUtil.setString(5, ob.getLineNumber());       
					dbUtil.setString(6, ob.getSerialNumber());     
					dbUtil.setString(7, ob.getLiabBranch());       
					dbUtil.executeUpdate();
					
					dbUtil.setSQL(query2);
					dbUtil.setString(1, ob.getUdfFieldValue());    
//					dbUtil.setString(2, ob.getUdfFieldSequence()); 
//					dbUtil.setString(3, ob.getUdfFieldName());     
					dbUtil.setString(2, ob.getSystemId());         
					dbUtil.setString(3, ob.getLineNumber());       
					dbUtil.setString(4, ob.getSerialNumber());     
					dbUtil.setString(5, ob.getLiabBranch());       
					dbUtil.executeUpdate();
					
					
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					try {
						if (dbUtil != null) {
							dbUtil.close();
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
		 }
		 else if(i>=120 && i<=215)
		 {
			 String query1 = "UPDATE SCI_LSP_LMT_XREF_UDF_2 sci set UDF"+seq+"_VALUE=? where sci.SCI_LSP_SYS_XREF_ID in "
						+ "(select temp.SCI_LSP_SYS_XREF_ID from TEMP_BULK_UDF_UPLOAD temp where temp.SEQUENCE=? "
								+ "and temp.FIELDNAME=? and temp.SYSTEM_ID=? and temp.LINE_NO=? and temp.SERIAL_NO=?"
								+ "and temp.LIAB_BRANCH=? "
						+ " )";	
				
				String query2="UPDATE STAGE_SCI_LSP_LMT_XREF_UDF_2 sci set UDF"+seq+"_VALUE=? where sci.SCI_LSP_SYS_XREF_ID in " 
						+ "(select temp.SCI_LSP_SYS_XREF_ID from TEMP_BULK_UDF_UPLOAD temp where temp.SEQUENCE=? "
						+ "and temp.FIELDNAME=? and temp.SYSTEM_ID=? and temp.LINE_NO=? and temp.SERIAL_NO=?"
						+ "and temp.LIAB_BRANCH=? "
				+ " )";		
				
				
				DBUtil dbUtil = null;
				try {
					dbUtil=new DBUtil();
					
					dbUtil.setSQL(query1);
					dbUtil.setString(1, ob.getUdfFieldValue());    
					dbUtil.setString(2, ob.getUdfFieldSequence()); 
					dbUtil.setString(3, ob.getUdfFieldName());     
					dbUtil.setString(4, ob.getSystemId());         
					dbUtil.setString(5, ob.getLineNumber());       
					dbUtil.setString(6, ob.getSerialNumber());     
					dbUtil.setString(7, ob.getLiabBranch());       
					dbUtil.executeUpdate();
					
					dbUtil.setSQL(query2);
					dbUtil.setString(1, ob.getUdfFieldValue());    
					dbUtil.setString(2, ob.getUdfFieldSequence()); 
					dbUtil.setString(3, ob.getUdfFieldName());     
					dbUtil.setString(4, ob.getSystemId());         
					dbUtil.setString(5, ob.getLineNumber());       
					dbUtil.setString(6, ob.getSerialNumber());     
					dbUtil.setString(7, ob.getLiabBranch());       
					dbUtil.executeUpdate();
					
					
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					try {
						if (dbUtil != null) {
							dbUtil.close();
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
		 }
		
		
	}

	public void updateLineAsPendingIfSystemUbs(List<OBTempBulkUDFFileUpload> nw)
	{
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String fcubsSystem = bundle.getString("fcubs.systemName");
		String ubsSystem = bundle.getString("ubs.systemName");
		final List<OBTempBulkUDFFileUpload> objectList = nw;
		
		String query="UPDATE CMS_STAGE_LSP_SYS_XREF set STATUS='PENDING', ACTION='MODIFY' where LINE_NO=? and"
				+ " SERIAL_NO=? and LIAB_BRANCH=? and FACILITY_SYSTEM=? and CMS_LSP_SYS_XREF_ID in "
				+ "(select max(CMS_LSP_SYS_XREF_ID) from CMS_STAGE_LSP_SYS_XREF stg where stg.FACILITY_SYSTEM_ID=? AND "  
				+ " stg.LINE_NO=? AND stg.SERIAL_NO=? AND stg.LIAB_BRANCH=?)";
		getJdbcTemplate().batchUpdate(query,
				new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)
			throws SQLException { 
	    		OBTempBulkUDFFileUpload ob=objectList.get(i);
	    	            ps.setString(1, ob.getLineNumber());
	    	            ps.setString(2, ob.getSerialNumber());
	    	            ps.setString(3, ob.getLiabBranch());
	    	            ps.setString(4, ob.getSystemId());
	    	            ps.setString(5, ob.getSystemId());
	    	            ps.setString(6, ob.getLineNumber());
	    	            ps.setString(7, ob.getSerialNumber());
	    	            ps.setString(8, ob.getLiabBranch());
	        }

			@Override
			public int getBatchSize() {
				return objectList.size();
			}  
	       });
		
		String query1="UPDATE SCI_LSP_SYS_XREF set STATUS='PENDING', ACTION='MODIFY' where LINE_NO=? and"
				+ " SERIAL_NO=? and LIAB_BRANCH=? and FACILITY_SYSTEM=? and CMS_LSP_SYS_XREF_ID in "
				+ "(select max(CMS_LSP_SYS_XREF_ID) from SCI_LSP_SYS_XREF stg where stg.FACILITY_SYSTEM_ID=? AND "  
				+ " stg.LINE_NO=? AND stg.SERIAL_NO=? AND stg.LIAB_BRANCH=?)";
		getJdbcTemplate().batchUpdate(query1,
				new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)
			throws SQLException { 
	    		OBTempBulkUDFFileUpload ob=objectList.get(i);
	    	            ps.setString(1, ob.getLineNumber());
	    	            ps.setString(2, ob.getSerialNumber());
	    	            ps.setString(3, ob.getLiabBranch());
	    	            ps.setString(4, ob.getSystemId());
	    	            ps.setString(5, ob.getSystemId());
	    	            ps.setString(6, ob.getLineNumber());
	    	            ps.setString(7, ob.getSerialNumber());
	    	            ps.setString(8, ob.getLiabBranch());
	        }

			@Override
			public int getBatchSize() {
				return objectList.size();
			}  
	       });
		
	}
	
	
	private void updateSecurityInsurance(ICheckListTrxValue checkListTrxVal) throws Exception {
		IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
			  System.out.println("checklist id inside file upload line 12052 updateSecurityInsurance is : "+checkListTrxVal.getCheckList().getCheckListID());
		   for(int i=0;i<checkListTrxVal.getCheckList().getCheckListItemList().length;i++) {
			ICheckListItem checklistItem = checkListTrxVal.getCheckList().getCheckListItemList()[i];
		        String status =	getCheckListItemStatus(checkListTrxVal.getCheckList().getCheckListID(), checklistItem.getCheckListItemRef(),checklistItem.getItemDesc());
		        System.out.println(" inside file upload line 12056 updateSecurityInsurance\n ItemRef is : "+checklistItem.getCheckListItemRef()+" status : "+status);
				}													
		String updateInsuranceDetailStg = insuranceGCJdbc
				.updateInsuranceDetailStg(checkListTrxVal.getCheckList().getCheckListID());
		DefaultLogger.info(this, "Stage Security Insurance update status: " + updateInsuranceDetailStg);

		if ("success".equals(updateInsuranceDetailStg)) {
			String updateInsuranceDetail = insuranceGCJdbc
					.updateInsuranceDetail(checkListTrxVal.getCheckList().getCheckListID());
			DefaultLogger.info(this, "Actual Security Insurance update status: " + updateInsuranceDetail);
		}
	}
	
	
	public  void updatePartyRMDetails(String empCode, IRegion region) {
		Long rmRegion = 0L;
		
		try {
			if(null != region) {
				rmRegion = region.getIdRegion();
		
			String query1 = "update SCI_LE_SUB_PROFILE set RM_REGION = '"+rmRegion+"' where RELATION_MGR_EMP_CODE = '"+empCode+"'"; 
			String query2 =	"update SCI_LE_MAIN_PROFILE set RM_REGION = '"+rmRegion+"' where RELATION_MGR_EMP_CODE = '"+empCode+"'";

			getJdbcTemplate().update(query1);
			getJdbcTemplate().update(query2);	
		}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		
	}
	
		 public String getCheckListItemStatus(Long checkListID,Long docItemRef,String docDesc) throws SearchDAOException {
		  String query="SELECT STATUS FROM CMS_CHECKLIST_ITEM WHERE CHECKLIST_ID=? and DOC_ITEM_REF=? and DOC_DESCRIPTION=?";
		  DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getChecklistStatus() query= "+query);
		    try {
			List arrList = getJdbcTemplate().query(query,
			new Object[] { new Long(checkListID),new Long(docItemRef),new Long(docDesc) }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			  return rs.getString("STATUS");
			 }
			});
			 if (arrList.size()<1){
			 return null;
			 }else{
			   return (String) arrList.get(0);
			    }
			    }catch(Exception e){
			   e.printStackTrace();
			DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getCheckListItemStatus() Exception= "+e);
			System.out.println("In insertDeferralExtnsionFile() of FileUploadJdbcImpl getCheckListItemStatus() with checklistId : "+checkListID+" and Exception= "+e);
			}
				return null;				  
			}
	 
		public String getCheckListItemStatusFromDocCode(Long checkListID,Long docItemRef,String docCode) throws SearchDAOException {
			String query="SELECT STATUS FROM CMS_CHECKLIST_ITEM WHERE CHECKLIST_ID=? and DOC_ITEM_REF=? and DOCUMENT_CODE==?";
			DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getChecklistStatus() query= "+query);
			try {
				List arrList = getJdbcTemplate().query(query,
						new Object[] { new Long(checkListID),new Long(docItemRef),new String(docCode) }, new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("STATUS");
					}
				});
				if (arrList.size()<1){
					return null;
				}else{
					return (String) arrList.get(0);		   
				}
			}catch(Exception e){
				e.printStackTrace();
				DefaultLogger.debug(this,"<<<<In insertDeferralExtnsionFile() of FileUploadJdbcImpl getCheckListItemStatusFromDocCode() Exception= "+e);
				System.out.println("In insertDeferralExtnsionFile() of FileUploadJdbcImpl getCheckListItemStatusFromDocCode() with checklistId : "+checkListID+" and Exception= "+e);
			}
			return null;				 
		}
	public void rollback()
	{
		try {
			String query1 = "truncate table TEMP_BULK_UDF_UPLOAD"; 
			getJdbcTemplate().update(query1);	
		}catch (Exception e) {
			e.printStackTrace();	
		}
	}

	public int getPslValueCheckWithLine(OBReleaselinedetailsFile obj) {
		int count=0;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT count(1) COUNT FROM SCI_LSP_LMTS_XREF_MAP WHERE CMS_LSP_SYS_XREF_ID IN(" + 
					"SELECT MAX(CMS_LSP_SYS_XREF_ID) FROM SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID='"+obj.getSystemID()+"' AND " + 
					"SERIAL_NO='"+obj.getSerialNo()+"' AND LINE_NO='"+obj.getLineNo()+"' AND LIAB_BRANCH='"+obj.getLiabBranch()+"' AND " +
					"IS_PRIORITY_SECTOR='"+obj.getPslFlag()+"' AND PRIORITY_SECTOR='"+obj.getPslValue()+"')");
			
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					count=rs.getInt("COUNT");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsStatus: "+e.getMessage());
        	e.printStackTrace();
		}
		return count;
	}

	public String getRuleValueCheckWithLine(OBReleaselinedetailsFile obj) {
		String count = null;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT RULE_ID  FROM SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID='"+obj.getSystemID()+"' AND " + 
					"SERIAL_NO='"+obj.getSerialNo()+"' AND LINE_NO='"+obj.getLineNo()+"' AND LIAB_BRANCH='"+obj.getLiabBranch()+"'" +
                    " AND CMS_LSP_SYS_XREF_ID=(" + 
					"SELECT MAX(CMS_LSP_SYS_XREF_ID) FROM SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID='"+obj.getSystemID()+"' AND " + 
					"SERIAL_NO='"+obj.getSerialNo()+"' AND LINE_NO='"+obj.getLineNo()+"' AND LIAB_BRANCH='"+obj.getLiabBranch()+"')");
			
			rs = dbUtil.executeQuery();
			if(null!=rs)
			{
				while(rs.next())
				{
					count=rs.getString("RULE_ID");
				}
			}
			finalize(dbUtil,rs);
		}catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsStatus: "+e.getMessage());
        	e.printStackTrace();
		}
		return count;
	}
	
	public OBGeneralParamEntry getAppDate() throws Exception{

		String sqlDate = "select PARAM_CODE,PARAM_VALUE from cms_general_param where param_code='APPLICATION_DATE'";
		DefaultLogger.debug(this, "In the getAppDate(): " + sqlDate);

		return (OBGeneralParamEntry) getJdbcTemplate().query(sqlDate, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				OBGeneralParamEntry result = new OBGeneralParamEntry();
				if (rs.next()) {
					result.setParamCode(rs.getString("PARAM_CODE"));
					result.setParamValue(rs.getString("PARAM_VALUE"));
				}
				return result;
			}
		});

	}
}

