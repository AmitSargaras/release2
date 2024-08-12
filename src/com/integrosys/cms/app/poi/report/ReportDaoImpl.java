package com.integrosys.cms.app.poi.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import java.util.ResourceBundle;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.app.poi.report.xml.schema.Param;
import com.integrosys.cms.app.poi.report.xml.schema.Reports;
import com.integrosys.cms.app.poi.report.xml.schema.WhereClause;
import com.integrosys.cms.ui.common.UIValidator;

public class ReportDaoImpl extends JdbcTemplateAdapter implements IReportDao{
//public class ReportDaoImpl extends HibernateDaoSupport implements IReportDao{
	
	/**
	 * @param reportQueryResult
	 * @param reports
	 * @return
	 */
	public List<Object[]> getReportQueryResult( Reports reports,OBFilter filter) {
		System.out.println("Inside getReportQueryResult == reportDaoImpl.java");
		List<Object[]> reportQueryResult= new LinkedList<Object[]>(); 
		/** genrating query from report object, object will be ob class containing filter values*/
		String sql=generateQuery(reports, filter);
		System.out.println("============In getReportQueryResult()===============sql: "+sql);
		DefaultLogger.debug(this, "============In getReportQueryResult()===============sql: "+sql);



		System.out.println("Template generated query : "+sql);

		try {
			reportQueryResult = getJdbcTemplate().query(sql,new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rownum) throws SQLException {
//					System.out.println("============inside Row mapper=========");
					int columnCount = rs.getMetaData().getColumnCount();
	//				System.out.println("===============Column Count================"+columnCount);
					Object[] objArray= new Object[columnCount];
						for (int i = 0; i < columnCount; i++) {
							objArray[i]=rs.getObject(i+1); 
						}
					return objArray;
				}
			});
		} catch (Exception e) {
			throw new ReportException("Error excecuting query.Please check the report template configuration.",e); 
		}
		DefaultLogger.debug(this, "=========Out getReportQueryResult()=======reportQueryResult.size(): "+reportQueryResult.size());
		return reportQueryResult;
	}
	
	int cnt = 0;
	public List<Object[]> getReportQueryResultFCC( Reports reports,OBFilter filter) {
		List<Object[]> reportQueryResult= new LinkedList<Object[]>(); 
		
		/** genrating query from report object, object will be ob class containing filter values*/
		cnt = 0;
		String sql=generateQuery(reports, filter);
		DefaultLogger.debug(this, "============In getReportQueryResult()===============sql: "+sql);
		try {
			reportQueryResult = getJdbcTemplate().query(sql,new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rownum) throws SQLException {
//					System.out.println("============inside Row mapper=========");
					int columnCount = rs.getMetaData().getColumnCount();
	//				System.out.println("===============Column Count================"+columnCount);
					Object[] objArray= new Object[columnCount];
						for (int i = 0; i < columnCount; i++) {
							if(i == 0) {
								objArray[i]=cnt+1; 
								cnt++;
							}else {
							objArray[i]=rs.getObject(i+1); 
							}
						} 
					return objArray;
				}
			});
		} catch (Exception e) {
			throw new ReportException("Error excecuting query.Please check the report template configuration.",e); 
		}
		DefaultLogger.debug(this, "=========Out getReportQueryResult()=======reportQueryResult.size(): "+reportQueryResult.size());
		return reportQueryResult;
	}
	
	
	public List<Object[]> getReportQueryResultEventOrCriteria( Reports reports,OBFilter filter) {
		List<Object[]> reportQueryResult= new LinkedList<Object[]>(); 
		/** genrating query from report object, object will be ob class containing filter values*/
		
		String sql= "";
		if(!"Limit is reduced or increased due to DP amount is changed".equals(filter.getEventOrCriteria())) {
			
			sql=generateQuery(reports, filter);
			
		}
		
		else {
			System.out.println("inside else reportDaoImpl class getReportQueryResultEventOrCriteria()");
			sql="SELECT DISTINCT  " + 
					"SLSP.LSP_SHORT_NAME AS PARTY_NAME, " + 
					"SLSP.LSP_LE_ID AS PARTY_ID, " + 
					"SLSP.PAN AS PAN_NO, " + 
					"APPR_STAGE.FACILITY_CAT AS FACILITY_CATEGORY, " + 
					"APPR_STAGE.FACILITY_NAME AS FACILITY_NAME, " + 
					"B_STAGE.FACILITY_SYSTEM_ID AS SYSTEM_ID,  " + 
//					"--B.LIABILITY_ID AS SYSTEM_ID1, " + 
					"B_STAGE.SERIAL_NO AS SERIAL_NO,   " + 
					"APPR_STAGE.TOTAL_RELEASED_AMOUNT AS RELEASED_AMOUNT,	 " + 
					"B_STAGE.RELEASED_AMOUNT AS LINE_RELEASED_AMOUNT, " + 
					"CSEC.TYPE_NAME AS SECURITY_TYPE, " + 
					"CSEC.SUBTYPE_NAME AS SECURITY_SUBTYPE,   " + 
					"CSEC.CMS_COLLATERAL_ID AS CMS_COLLATERAL_ID,  " + 
					"TO_CHAR(CAGD.DUE_DATE,'DD/MM/YYYY') AS DUE_DATE, " + 
					"CAGD.CALCULATEDDP AS DRAWING_POWER, " + 
					"CRM.RM_MGR_NAME AS RELATIONSHIP_MGR_NAME, " + 
					"CRM.REPORTING_HEAD AS SUPERVISOR_NAME, " + 
					"SLSP.LSP_LE_ID AS EVENT_CRITERIA, " + 
					/*" " + 
					"CASE " + 
					"						    WHEN trx.from_state = 'PENDING_PERFECTION' " + 
					"						    THEN " + 
					"						      (SELECT hist.login_id " + 
					"						      FROM trans_history hist " + 
					"						      WHERE hist.transaction_id = trx.transaction_id " + 
					"						      AND hist.from_state       = 'PENDING_PERFECTION' " + 
					"						      AND hist.to_state         ='DRAFT' " + 
					"						      ) " + 
					"						    WHEN trx.from_state = 'PENDING_CREATE' " + 
					"						    THEN " + 
					"						      (SELECT hist.login_id " + 
					"						      FROM trans_history hist " + 
					"						      WHERE hist.transaction_id = trx.transaction_id " + 
					"						      AND hist.from_state      IN ('ND','DRAFT') " + 
					"						      AND hist.to_state         ='PENDING_CREATE' " + 
					"						      ) " + 
					"						    WHEN trx.from_state = 'PENDING_UPDATE' " + 
					"						    THEN " + 
					"						      (SELECT hist.login_id " + 
					"						      FROM trans_history hist " + 
					"						      WHERE hist.TR_HISTORY_ID= " + 
					"						        (SELECT MAX(TR_HISTORY_ID) " + 
					"						        FROM trans_history " + 
					"						        WHERE transaction_id = trx.transaction_id " + 
					"						        AND from_state      IN ('ACTIVE','DRAFT') " + 
					"						        AND to_state         = 'PENDING_UPDATE' " + 
					"						        ) " + 
					"						      ) " + 
					"						    WHEN trx.from_state = 'PENDING_DELETE' " + 
					"						    THEN " + 
					"						      (SELECT hist.login_id " + 
					"						      FROM trans_history hist " + 
					"						      WHERE hist.TR_HISTORY_ID= " + 
					"						        (SELECT MAX(TR_HISTORY_ID) " + 
					"						        FROM trans_history " + 
					"						        WHERE transaction_id = trx.transaction_id " + 
					"						        AND from_state       ='ACTIVE' " + 
					"						        AND to_state         ='PENDING_DELETE' " + 
					"						        ) " + 
					"						      ) " + 
					"						    WHEN trx.from_state = 'ACTIVE' " + 
					"						    THEN " + 
					"						      (SELECT hist.login_id " + 
					"						      FROM trans_history hist " + 
					"						      WHERE hist.TR_HISTORY_ID= " + 
					"						        (SELECT MAX(TR_HISTORY_ID) " + 
					"						        FROM trans_history " + 
					"						        WHERE transaction_id = trx.transaction_id " + 
					"						        AND from_state       ='ACTIVE' " + 
					"						        AND to_state        IN ('PENDING_UPDATE','PENDING_DELETE') " + 
					"						        ) " + 
					"						      ) " + 
					"						    WHEN trx.from_staLimit is reduced or increasete = 'REJECTED' " + 
					"						    THEN " + 
					"						      (SELECT hist.login_id " + 
					"						      FROM trans_history hist " + 
					"						      WHERE hist.TR_HISTORY_ID= " + 
					"						        (SELECT MAX(TR_HISTORY_ID) " + 
					"						        FROM trans_history " + 
					"						        WHERE transaction_id = trx.transaction_id " + 
					"						        AND from_state       = 'REJECTED' " + 
					"						        AND to_state         ='ACTIVE' " + 
					"						        ) " + 
					"						      ) " + 
					"						    WHEN trx.from_state = 'DRAFT' " + 
					"						    THEN " + 
					"						      CASE " + 
					"						        WHEN trx.status = 'PENDING_UPDATE' " + 
					"						        THEN " + 
					"						          (SELECT hist.login_id " + 
					"						          FROM trans_history hist " + 
					"						          WHERE hist.TR_HISTORY_ID= " + 
					"						            (SELECT MAX(TR_HISTORY_ID) " + 
					"						            FROM trans_history " + 
					"						            WHERE transaction_id = trx.transaction_id " + 
					"						            AND from_state       = 'DRAFT' " + 
					"						            AND to_state         ='PENDING_UPDATE' " + 
					"						            ) " + 
					"						          ) " + 
					"						        WHEN trx.status = 'ACTIVE' " + 
					"						        THEN " + 
					"						          (SELECT hist.login_id " + 
					"						          FROM trans_history hist " + 
					"						          WHERE hist.TR_HISTORY_ID= " + 
					"						            (SELECT MAX(TR_HISTORY_ID) " + 
					"						            FROM trans_history " + 
					"						            WHERE transaction_id = trx.transaction_id " + 
					"						            AND from_state       = 'DRAFT' " + 
					"						            AND to_state         ='ACTIVE' " + 
					"						            ) " + 
					"						          ) " + 
					"						      END " + */
					"						  B_STAGE.CREATED_BY AS Maker, " + 
				/*	"						  CASE " + 
					"						    WHEN trx.status   = 'PENDING_CREATE' " + 
					"						    OR trx.from_state = 'PENDING_PERFECTION' " + 
					"						    THEN '' " + 
					"						    WHEN trx.status = 'PENDING_UPDATE' " + 
					"						    THEN " + 
					"						      (SELECT hist.login_id " + 
					"						      FROM trans_history hist " + 
					"						      WHERE hist.TR_HISTORY_ID= " + 
					"						        (SELECT MAX(TR_HISTORY_ID) " + 
					"						        FROM trans_history " + 
					"						        WHERE transaction_id = trx.transaction_id " + 
					"						        AND from_state      IN ('PENDING_UPDATE','ACTIVE') " + 
					"						        AND to_state        IN ('PENDING_UPDATE','ACTIVE') " + 
					"						        ) " + 
					"						      ) " + 
					"						    WHEN ((trx.status  != 'PENDING_CREATE' " + 
					"						    AND trx.from_state != 'PENDING_PERFECTION') " + 
					"						    AND trx.status     != 'PENDING_UPDATE') " + 
					"						    THEN TO_CHAR(trx.login_id) " + */
					"						  B_STAGE.CHECKER_ID_NEW AS Approved_By " + 
					"					  " + 
					"					FROM   " + 
					"					CMS_SECURITY CSEC,   " + 
					"					 SCI_LSP_APPR_LMTS APPR,   " + 
					"					  CMS_LIMIT_SECURITY_MAP MAP,  " + 
					"					 SCI_LSP_LMT_PROFILE LMT,   " + 
					"						SCI_LE_SUB_PROFILE SLSP,  " + 
//					"					 SCI_LSP_SYS_XREF b,   " + 
//					"					  SCI_LSP_LMTS_XREF_MAP c , " + 
					"            CMS_ASSET_GC_DET CAGD, " + 
					"            CMS_RELATIONSHIP_MGR CRM " + 
					"            , " + 
					"						  trans_history trx, " + 
					"              STAGE_LIMIT    APPR_STAGE, " + 
					"              CMS_STAGE_LSP_SYS_XREF  B_STAGE, " + 
					"              STAGE_LIMIT_XREF C_STAGE " + 
					"              " + 
					"               " + 
					"					WHERE    " + 
					"					CSEC.CMS_COLLATERAL_ID = CAGD.CMS_COLLATERAL_ID    " + 
					"       " + 
					"					 AND CSEC.CMS_COLLATERAL_ID            = MAP.CMS_COLLATERAL_ID   " + 
					"					AND APPR.CMS_LSP_APPR_LMTS_ID        = MAP.CMS_LSP_APPR_LMTS_ID   " + 
					"					 AND LMT.CMS_LSP_LMT_PROFILE_ID       = APPR.CMS_LIMIT_PROFILE_ID   " + 
					"					AND LMT.LLP_LE_ID                    =SLSP.LSP_LE_ID   " + 
					"					AND SLSP.STATUS = 'ACTIVE'  " + 
//					"					AND c.CMS_LSP_APPR_LMTS_ID = APPR.CMS_LSP_APPR_LMTS_ID   " + 
//					"					AND c.CMS_LSP_SYS_XREF_ID  = b.CMS_LSP_SYS_XREF_ID  			 " + 
//					"					--AND MAP.deletion_date is NULL   " + 
					"					AND APPR.CMS_LIMIT_STATUS = 'ACTIVE'  " + 
					"					 " + 
					"					 " + 
					"					AND CSEC.STATUS = 'ACTIVE'   " + 
					"          AND CSEC.SECURITY_SUB_TYPE_ID = 'AB100' " + 
					"					 AND APPR.CMS_LSP_APPR_LMTS_ID = MAP.CMS_LSP_APPR_LMTS_ID   " + 
					"					AND MAP.CHARGE_ID   in   " + 
					"					               (SELECT MAX(maps2.CHARGE_ID)   " + 
					"					       from cms_limit_security_map maps2   " + 
					"					       where maps2.cms_lsp_appr_lmts_id = APPR.cms_lsp_appr_lmts_id   " + 
					"					       AND maps2.cms_collateral_id      =MAP.cms_collateral_id   " + 
					"					          " + 
					"					       )    " + 
					"    AND trx.transaction_type='LIMIT' "+ 
					"					AND (MAP.UPDATE_STATUS_IND <> 'D' OR MAP.UPDATE_STATUS_IND IS NULL)  " + 
					"          AND SLSP.RELATION_MGR = CRM.ID " + 
					"          " + 
					"         AND APPR.CMS_LSP_APPR_LMTS_ID = TRX.REFERENCE_ID  " + 
					"          AND SLSP.RELATION_MGR = CRM.ID " + 
					"           " + 
					"          AND C_STAGE.CMS_LSP_APPR_LMTS_ID = APPR_STAGE.CMS_LSP_APPR_LMTS_ID   " + 
					"					AND C_STAGE.CMS_LSP_SYS_XREF_ID  = B_STAGE.CMS_LSP_SYS_XREF_ID  " + 
					"    and trx.STAGING_REFERENCE_ID= APPR_STAGE.CMS_LSP_APPR_LMTS_ID "+
					"        AND TRUNC(TRX.TRANSACTION_DATE) BETWEEN TO_DATE('"+filter.getFromDate()+"','DD/MM/YYYY') AND TO_DATE('"+filter.getToDate()+"','DD/MM/YYYY') ";
			
		}
		
		DefaultLogger.debug(this, "============In getReportQueryResultEventOrCriteria()===============sql: "+sql);
		System.out.println("Template generated query : "+sql);

		try {
			reportQueryResult = getJdbcTemplate().query(sql,new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rownum) throws SQLException {
//					System.out.println("============inside Row mapper=========");
					int columnCount = rs.getMetaData().getColumnCount();
	//				System.out.println("===============Column Count================"+columnCount);
					Object[] objArray= new Object[columnCount];
						for (int i = 0; i < columnCount; i++) {
							objArray[i]=rs.getObject(i+1); 
						}
					return objArray;
				}
			});
		} catch (Exception e) {
			throw new ReportException("getReportQueryResultEventOrCriteria =>Error excecuting query.Please check the report template configuration.",e); 
		}
		DefaultLogger.debug(this, "=========Out getReportQueryResultEventOrCriteria()=======reportQueryResult.size(): "+reportQueryResult.size());
		
		Object[] reportQueryResult1 = null;
		for(int i=0;i<reportQueryResult.size();i++) {
			reportQueryResult1= reportQueryResult.get(i); 
			reportQueryResult1[16] = filter.getEventOrCriteria();
			reportQueryResult.set(i, reportQueryResult1);
		}
		
		return reportQueryResult;
	}
	
	
	public List<String[]> getBorrowerRecords(String bankMethod,String fromDate,String toDate,String typeOfsecurity) {
		List<String[]> reportQueryResult= new LinkedList<String[]>(); 
		/** genrating query from report object, object will be ob class containing filter values*/
//		String sql=generateQuery(reports, filter);
		String sql="SELECT DISTINCT " + 
				"CMS_MAP.CERSAI_VALUE, " + 
				"CMS.CMS_COLLATERAL_ID AS SECURITY_ID, " +
				"CMS.CMV AS SECURITY_AMOUNT, " +
				"SLSP.LSP_SHORT_NAME AS PARTY_NAME, " + 
				"SLSP.CIN_LLPIN AS CIN, " + 
				"SLSP.PAN AS PAN, " + 
				"CASE when CMS_MAP.CERSAI_VALUE ='TRS' " + 
				"    THEN SLD.DIR_NAME  " + 
				"    when CMS_MAP.CERSAI_VALUE ='PAF' " + 
				"    THEN SLD.DIR_NAME " + 
				"    when CMS_MAP.CERSAI_VALUE ='LLP' " + 
				"    THEN SLD.DIR_NAME " + 
				"    when CMS_MAP.CERSAI_VALUE ='COS' " + 
				"    THEN SLD.DIR_NAME " + 
				"    END AS DIRECTOR_NAME, "+
				"SLRA.LRA_ADDR_LINE_1 AS ADDRESS1, " + 
				"SLRA.LRA_ADDR_LINE_2 AS ADDRESS2, " + 
				"SLRA.LRA_ADDR_LINE_3 AS ADDRESS3, " +
				
//				"SLD.DIR_NAME AS DNAME, " + 
//				"SLD.DIR_ADD1 AS ADDRESS1, " + 
//				"SLD.DIR_ADD2 AS ADDRESS2, " + 
//				"SLD.DIR_ADD3 AS ADDRESS3, " + 
				"CC.CITY_NAME AS CITY, " + 
				"SLRA.LRA_STATE AS STATE1, " + 
				"SLRA.LRA_POST_CODE AS PINCODE, "+
//				"SLD.DIR_STATE AS STATE1 , " + 
//				"SLD.DIR_POST_CODE AS PINCODE, " + 
				//"SLD.DIR_TEL_NO AS PHONE_NUM, " +
				"CMS.SECURITY_OWNERSHIP, "+
				
				"CASE " + 
				"    WHEN SLRA.LRA_STATE ='11' " + 
				"    THEN '01' " + 
				"    WHEN SLRA.LRA_STATE ='1' " + 
				"    THEN '02' " + 
				"    WHEN SLRA.LRA_STATE ='18' " + 
				"    THEN '04' " + 
				"    WHEN SLRA.LRA_STATE ='14' " + 
				"    THEN '05' " + 
				"    WHEN SLRA.LRA_STATE ='21' " + 
				"    THEN '06' " + 
				"    WHEN SLRA.LRA_STATE ='23' " + 
				"    THEN '08' " + 
				"    WHEN SLRA.LRA_STATE ='24' " + 
				"    THEN '09' " + 
				"    WHEN SLRA.LRA_STATE ='25' " + 
				"    THEN '10' " + 
				"    WHEN SLRA.LRA_STATE ='32' " + 
				"    THEN '11' " + 
				"    WHEN SLRA.LRA_STATE ='3' " + 
				"    THEN '12' " + 
				"    WHEN SLRA.LRA_STATE ='8' " + 
				"    THEN '13' " + 
				"    WHEN SLRA.LRA_STATE ='19' " + 
				"    THEN '14' " + 
				"    WHEN SLRA.LRA_STATE ='10' " + 
				"    THEN '15' " + 
				"    WHEN SLRA.LRA_STATE ='33' " + 
				"    THEN '16' " + 
				"    WHEN SLRA.LRA_STATE ='16' " + 
				"    THEN '17' " + 
				"    WHEN SLRA.LRA_STATE ='7' " + 
				"    THEN '18' " + 
				"    WHEN SLRA.LRA_STATE ='31' " + 
				"    THEN '19' " + 
				"    WHEN SLRA.LRA_STATE ='15' " + 
				"    THEN '20' " + 
				"    WHEN SLRA.LRA_STATE ='4' " + 
				"    THEN '21' " + 
				"    WHEN SLRA.LRA_STATE ='20' " + 
				"    THEN '22' " + 
				"    WHEN SLRA.LRA_STATE ='27' " + 
				"    THEN '23' " + 
				"    WHEN SLRA.LRA_STATE ='5' " + 
				"    THEN '24' " + 
				"    WHEN SLRA.LRA_STATE ='30' " + 
				"    THEN '25' " + 
				"    WHEN SLRA.LRA_STATE ='12' " + 
				"    THEN '26' " + 
				"    WHEN SLRA.LRA_STATE ='29' " + 
				"    THEN '27' " + 
				"    WHEN SLRA.LRA_STATE ='9' " + 
				"    THEN '28' " + 
				"    WHEN SLRA.LRA_STATE ='6' " + 
				"    THEN '29' " + 
				"    WHEN SLRA.LRA_STATE ='28' " + 
				"    THEN '30' " + 
				"    WHEN SLRA.LRA_STATE ='13' " + 
				"    THEN '31' " + 
				"    WHEN SLRA.LRA_STATE ='26' " + 
				"    THEN '32' " + 
				"    WHEN SLRA.LRA_STATE ='2' " + 
				"    THEN '33' " + 
				"    WHEN SLRA.LRA_STATE ='20140403000000086' " + 
				"    THEN '34' " + 
				"    WHEN SLRA.LRA_STATE ='17' " + 
				"    THEN '35' " + 
				"    WHEN SLRA.LRA_STATE ='20140911000000090' " + 
				"    THEN '36' " + 
				"    END AS STATE2 "+
//				" TO_CHAR(CHECKLIST_ITEM.DOC_DATE ,'dd-MM-yyyy') AS DATE_DOC "+
				
				"FROM  " + 
				"SCI_LE_SUB_PROFILE SLSP, " + 
				"SCI_LE_MAIN_PROFILE SLMP, " + 
				"SCI_LE_DIRECTOR SLD, " + 
				"CMS_CITY CC, " + 
				"CMS_COLLATERAL_NEW_MASTER CCNM, " + 
				"CMS_CHECKLIST_ITEM CHECKLIST_ITEM, " + 
				"CMS_CHECKLIST CHECKLIST, " + 
				"CMS_SECURITY CMS, " + 
				"SCI_LSP_LMT_PROFILE LMT, " + 
				"SCI_LSP_APPR_LMTS APPR, " + 
				"CMS_LIMIT_SECURITY_MAP MAP, " + 
				"CMS_SUB_CERSAI_MAPPING CMS_MAP, "+
				"COMMON_CODE_CATEGORY_ENTRY CCCE, "+
				"SCI_LE_REG_ADDR SLRA, "+
				"CMS_BANKING_METHOD_CUST CBMC " +
				"WHERE  " + 
				"SLRA.LRA_TYPE_VALUE= 'CORPORATE' AND "+
//				"UPPER(SLSP.ENTITY) IN (select clims_value from CMS_SUB_CERSAI_MAPPING where cersai_value in (select distinct cersai_value from  CMS_SUB_CERSAI_MAPPING where master_name = 'Entity')) AND " + 
//				"SLSP.ENTITY = CMS_MAP.CLIMS_VALUE AND "+
				"UPPER(SLSP.ENTITY) IN "+
				"(SELECT DISTINCT ENTRY_CODE FROM COMMON_CODE_CATEGORY_ENTRY WHERE ENTRY_NAME IN (SELECT DISTINCT CLIMS_VALUE FROM CMS_SUB_CERSAI_MAPPING WHERE MASTER_NAME = 'Entity')) AND  "+
				"SLSP.ENTITY = CCCE.ENTRY_CODE AND  " + 
				"CCCE.ENTRY_NAME = CMS_MAP.CLIMS_VALUE AND "+
				"CMS_MAP.CLIMS_VALUE in (SELECT DISTINCT CLIMS_VALUE FROM " + 
				"CMS_SUB_CERSAI_MAPPING  WHERE ID in " + 
//				"(SELECT CMS_MAP.ID FROM CMS_SUB_CERSAI_MAPPING CMS_MAP WHERE CMS_MAP.CLIMS_VALUE IN " + 
//				"(select clims_value from CMS_SUB_CERSAI_MAPPING where cersai_value in (select distinct cersai_value from  CMS_SUB_CERSAI_MAPPING where master_name = 'Entity')))) " + 
				"(SELECT MAX(ID) " + 
				"    FROM CMS_SUB_CERSAI_MAPPING " + 
				"    WHERE MASTER_NAME = 'Entity' " + 
				"    GROUP BY CLIMS_VALUE ) ) " + 
				"AND " + 
				"CMS_MAP.ID in "+
//				"(SELECT CMS_MAP.ID FROM CMS_SUB_CERSAI_MAPPING CMS_MAP WHERE  " + 
//				"CMS_MAP.CLIMS_VALUE IN (select clims_value from CMS_SUB_CERSAI_MAPPING where  " + 
//				"cersai_value in (select distinct cersai_value from  CMS_SUB_CERSAI_MAPPING where master_name = 'Entity'))) AND "+
				"(SELECT MAX(ID) " + 
				"  FROM CMS_SUB_CERSAI_MAPPING " + 
				"  WHERE MASTER_NAME = 'Entity' " + 
				"  GROUP BY CLIMS_VALUE " + 
				"  ) AND "+
				//"SLD.DIR_CITY = CC.ID AND  " + 
				"SLRA.LRA_CITY_TEXT = CC.ID AND "+
				"SLMP.CMS_LE_MAIN_PROFILE_ID = SLRA.CMS_LE_MAIN_PROFILE_ID AND "+
				"SLSP.LSP_SHORT_NAME = SLMP.LMP_SHORT_NAME AND  " + 
				"SLMP.CMS_LE_MAIN_PROFILE_ID = SLD.CMS_LE_MAIN_PROFILE_ID AND " + 
				"CMS.CERSAI_SECURITY_INTEREST_ID IS NULL AND " + 
				"CMS.COLLATERAL_CODE = CCNM.NEW_COLLATERAL_CODE AND  " + 
				"CCNM.CERSAI_IND ='Y' AND CCNM.NEW_COLLATERAL_CATEGORY = '"+typeOfsecurity+"' AND " + 
//				"UPPER(CHECKLIST_ITEM.DOC_DESCRIPTION) IN('MEMORANDUM OF ENTRY','DEED OF HYPOTHECATION','LETTER OF HYPOTHECATION','JOINT DEED OF HYPOTHECATION') AND " + 
				"CHECKLIST_ITEM.STATUS = 'RECEIVED' " +
				"AND SLSP.STATUS = 'ACTIVE' " + 
				"AND MAP.UPDATE_STATUS_IND = 'I' " + 
				"AND CMS.STATUS = 'ACTIVE' " + 
				"AND CHECKLIST_ITEM.IS_DELETED = 'N'  "+
				"AND CHECKLIST.CMS_COLLATERAL_ID = CMS.CMS_COLLATERAL_ID AND "+
				"(CHECKLIST_ITEM.RECEIVED_DATE BETWEEN TO_DATE('"+fromDate+"','DD/MM/YYYY') AND TO_DATE('"+toDate+"','DD/MM/YYYY') ) AND  "+
				//"CHECKLIST_ITEM.RECEIVED_DATE >= TO_DATE('23/02/2016','DD/MM/YYYY') AND "+
				"CHECKLIST.CHECKLIST_ID = CHECKLIST_ITEM.CHECKLIST_ID AND " + 
				"CHECKLIST.CMS_LSP_LMT_PROFILE_ID = LMT.CMS_LSP_LMT_PROFILE_ID AND " + 
				"CMS.CMS_COLLATERAL_ID= MAP.CMS_COLLATERAL_ID and " + 
				"APPR.CMS_LSP_APPR_LMTS_ID = MAP.CMS_LSP_APPR_LMTS_ID and  " + 
				"LMT.CMS_LSP_LMT_PROFILE_ID = APPR.CMS_LIMIT_PROFILE_ID and  " + 
				"LMT.LLP_LE_ID=SLSP.LSP_LE_ID and " + 
				"SLSP.LSP_LE_ID         =       CBMC.CUSTOMER_ID " + 
				"AND CBMC.STATUS = 'ACTIVE' " +
//				"SLSP.BANKING_METHOD='"+bankMethod+"'";
				"AND CBMC.CMS_BANKING_METHOD_NAME='"+bankMethod+"'";
			System.out.println("Query = "+sql);
		try {
			reportQueryResult = getJdbcTemplate().query(sql,new RowMapper() {
				@Override
				public String[] mapRow(ResultSet rs, int rownum) throws SQLException {
//					System.out.println("============inside Row mapper=========");
					int columnCount = rs.getMetaData().getColumnCount();
	//				System.out.println("===============Column Count================"+columnCount);
					String[] objArray= new String[columnCount];
						for (int i = 0; i < columnCount; i++) {
							objArray[i]=rs.getString(i+1);
						}
					return objArray;
				}
			});
		} catch (Exception e) {
			throw new ReportException("Error in getBorrowerRecords.",e); 
		}
		return reportQueryResult;
	}
	
	public List<String[]> getThirdPartyRecords(String bankMethod,String fromDate,String toDate,String typeOfsecurity) {
		List<String[]> reportQueryResult= new LinkedList<String[]>(); 
		/** genrating query from report object, object will be ob class containing filter values*/
//		String sql=generateQuery(reports, filter);
		String sql="SELECT DISTINCT  " + 
				"CMS_MAP.CERSAI_VALUE, " + 
				"CMS.CMS_COLLATERAL_ID AS SECURITY_ID , "+
				"CMS.CMV AS SECURITY_AMOUNT, " +
				"CP.DEVELOPER_GROUP_COMPANY, " + 
				"CMS.CIN_THIRD_PARTY, " + 
				//"CP.PROPERTY_ADDRESS, " + 
				"CMS.THIRD_PARTY_ADDRESS, "+
				"CC.CITY_NAME, " + 
				"CP.STATE, " + 
				"CP.PINCODE, " + 
				"SLSP.PAN, "+
				"CASE " + 
				"    WHEN CP.STATE ='11' " + 
				"    THEN '01' " + 
				"    WHEN CP.STATE ='1' " + 
				"    THEN '02' " + 
				"    WHEN CP.STATE ='18' " + 
				"    THEN '04' " + 
				"    WHEN CP.STATE ='14' " + 
				"    THEN '05' " + 
				"    WHEN CP.STATE ='21' " + 
				"    THEN '06' " + 
				"    WHEN CP.STATE ='23' " + 
				"    THEN '08' " + 
				"    WHEN CP.STATE ='24' " + 
				"    THEN '09' " + 
				"    WHEN CP.STATE ='25' " + 
				"    THEN '10' " + 
				"    WHEN CP.STATE ='32' " + 
				"    THEN '11' " + 
				"    WHEN CP.STATE ='3' " + 
				"    THEN '12' " + 
				"    WHEN CP.STATE ='8' " + 
				"    THEN '13' " + 
				"    WHEN CP.STATE ='19' " + 
				"    THEN '14' " + 
				"    WHEN CP.STATE ='10' " + 
				"    THEN '15' " + 
				"    WHEN CP.STATE ='33' " + 
				"    THEN '16' " + 
				"    WHEN CP.STATE ='16' " + 
				"    THEN '17' " + 
				"    WHEN CP.STATE ='7' " + 
				"    THEN '18' " + 
				"    WHEN CP.STATE ='31' " + 
				"    THEN '19' " + 
				"    WHEN CP.STATE ='15' " + 
				"    THEN '20' " + 
				"    WHEN CP.STATE ='4' " + 
				"    THEN '21' " + 
				"    WHEN CP.STATE ='20' " + 
				"    THEN '22' " + 
				"    WHEN CP.STATE ='27' " + 
				"    THEN '23' " + 
				"    WHEN CP.STATE ='5' " + 
				"    THEN '24' " + 
				"    WHEN CP.STATE ='30' " + 
				"    THEN '25' " + 
				"    WHEN CP.STATE ='12' " + 
				"    THEN '26' " + 
				"    WHEN CP.STATE ='29' " + 
				"    THEN '27' " + 
				"    WHEN CP.STATE ='9' " + 
				"    THEN '28' " + 
				"    WHEN CP.STATE ='6' " + 
				"    THEN '29' " + 
				"    WHEN CP.STATE ='28' " + 
				"    THEN '30' " + 
				"    WHEN CP.STATE ='13' " + 
				"    THEN '31' " + 
				"    WHEN CP.STATE ='26' " + 
				"    THEN '32' " + 
				"    WHEN CP.STATE ='2' " + 
				"    THEN '33' " + 
				"    WHEN CP.STATE ='20140403000000086' " + 
				"    THEN '34' " + 
				"    WHEN CP.STATE ='17' " + 
				"    THEN '35' " + 
				"    WHEN CP.STATE ='20140911000000090' " + 
				"    THEN '36' " + 
				"  END AS STATE2 "+
//				" TO_CHAR(CHECKLIST_ITEM.DOC_DATE ,'dd-MM-yyyy') AS DATE_DOC "+
				"FROM  " + 
				"CMS_SECURITY CMS, " + 
				"CMS_PROPERTY CP, " + 
				"SCI_LE_SUB_PROFILE SLSP, " + 
				"SCI_LSP_LMT_PROFILE LMT, " + 
				"SCI_LSP_APPR_LMTS APPR, " + 
				"CMS_LIMIT_SECURITY_MAP MAP, " + 
				"CMS_CITY CC, " + 
				"CMS_COLLATERAL_NEW_MASTER CCNM, " + 
				"CMS_CHECKLIST_ITEM CHECKLIST_ITEM, " + 
				"CMS_CHECKLIST CHECKLIST, " + 
				"CMS_SUB_CERSAI_MAPPING CMS_MAP, "+
				"COMMON_CODE_CATEGORY_ENTRY CCCE, " + 
				"CMS_BANKING_METHOD_CUST CBMC " +
				"WHERE  " + 
				"CMS.SECURITY_OWNERSHIP = 'THIRD_PARTY' AND  " + 
				"UPPER(CMS.THIRD_PARTY_ENTITY) IN (SELECT DISTINCT ENTRY_CODE FROM COMMON_CODE_CATEGORY_ENTRY WHERE ENTRY_NAME IN (SELECT DISTINCT CLIMS_VALUE FROM  CMS_SUB_CERSAI_MAPPING )) AND  " + 
				//"CMS.THIRD_PARTY_ENTITY = CMS_MAP.CLIMS_VALUE AND "+
				"CMS.THIRD_PARTY_ENTITY = CCCE.ENTRY_CODE AND  " + 
				"CCCE.ENTRY_NAME = CMS_MAP.CLIMS_VALUE AND  "+
				"CP.NEAREST_CITY = CC.ID AND  " + 
				"CMS.CERSAI_SECURITY_INTEREST_ID IS NULL AND " + 
				"CMS.COLLATERAL_CODE = CCNM.NEW_COLLATERAL_CODE AND  " + 
				"CCNM.CERSAI_IND ='Y' AND CCNM.NEW_COLLATERAL_CATEGORY = '"+typeOfsecurity+"' AND " + 
//				"UPPER(CHECKLIST_ITEM.DOC_DESCRIPTION) IN('MEMORANDUM OF ENTRY','DEED OF HYPOTHECATION','LETTER OF HYPOTHECATION','JOINT DEED OF HYPOTHECATION') AND " + 
				"CHECKLIST_ITEM.STATUS = 'RECEIVED' AND " + 
				"SLSP.STATUS = 'ACTIVE' " + 
				"AND MAP.UPDATE_STATUS_IND = 'I' " + 
				"AND CMS.STATUS = 'ACTIVE' " + 
				"AND CHECKLIST_ITEM.IS_DELETED = 'N' "+
				"AND CHECKLIST.CMS_COLLATERAL_ID = CMS.CMS_COLLATERAL_ID AND "+
				"(CHECKLIST_ITEM.RECEIVED_DATE BETWEEN TO_DATE('"+fromDate+"','DD/MM/YYYY') AND TO_DATE('"+toDate+"','DD/MM/YYYY') ) AND "+
				//"CHECKLIST_ITEM.RECEIVED_DATE >= TO_DATE('23/02/2016','DD/MM/YYYY') AND "+
				"CHECKLIST.CHECKLIST_ID = CHECKLIST_ITEM.CHECKLIST_ID AND " + 
				"CHECKLIST.CMS_LSP_LMT_PROFILE_ID = LMT.CMS_LSP_LMT_PROFILE_ID AND " + 
				"CMS.CMS_COLLATERAL_ID = CP.CMS_COLLATERAL_ID AND " + 
				"CMS.CMS_COLLATERAL_ID= MAP.CMS_COLLATERAL_ID and " + 
				"APPR.CMS_LSP_APPR_LMTS_ID =MAP.CMS_LSP_APPR_LMTS_ID and  " + 
				"LMT.CMS_LSP_LMT_PROFILE_ID=APPR.CMS_LIMIT_PROFILE_ID and  " + 
				"LMT.LLP_LE_ID=SLSP.LSP_LE_ID and " + 
				"SLSP.LSP_LE_ID         =       CBMC.CUSTOMER_ID " + 
				"AND CBMC.STATUS = 'ACTIVE' " +
//				"SLSP.BANKING_METHOD='"+bankMethod+"'";
				"AND CBMC.CMS_BANKING_METHOD_NAME='"+bankMethod+"'";
		System.out.println("Query = "+sql);
		try {
			reportQueryResult = getJdbcTemplate().query(sql,new RowMapper() {
				@Override
				public String[] mapRow(ResultSet rs, int rownum) throws SQLException {
//					System.out.println("============inside Row mapper=========");
					int columnCount = rs.getMetaData().getColumnCount();
	//				System.out.println("===============Column Count================"+columnCount);
					String[] objArray= new String[columnCount];
						for (int i = 0; i < columnCount; i++) {
							objArray[i]=rs.getString(i+1);
						}
					return objArray;
				}
			});
		} catch (Exception e) {
			throw new ReportException("Error in getThirdPartyRecords.",e); 
		}
		return reportQueryResult;
	}
	
	public List<String[]> getSecurityThirdInterestRecords(String bankMethod,String fromDate,String toDate,String typeOfsecurity) {
		List<String[]> reportQueryResult= new LinkedList<String[]>(); 
		/** genrating query from report object, object will be ob class containing filter values*/
//		String sql=generateQuery(reports, filter);
		String sql="SELECT DISTINCT  " + 
				"CSBB.SYSTEM_BANK_BRANCH_NAME, " + 
				"CMS.CMS_COLLATERAL_ID AS SECURITY_ID, " + 
				"CMS.CMV AS SECURITY_AMOUNT, " +
				"CC.CITY_NAME, " + 
				"CSBB.STATE, " + 
				"CSBB.PINCODE, " + 
				"CASE " + 
				"    WHEN CSBB.STATE ='11' " + 
				"    THEN '01' " + 
				"    WHEN CSBB.STATE ='1' " + 
				"    THEN '02' " + 
				"    WHEN CSBB.STATE ='18' " + 
				"    THEN '04' " + 
				"    WHEN CSBB.STATE ='14' " + 
				"    THEN '05' " + 
				"    WHEN CSBB.STATE ='21' " + 
				"    THEN '06' " + 
				"    WHEN CSBB.STATE ='23' " + 
				"    THEN '08' " + 
				"    WHEN CSBB.STATE ='24' " + 
				"    THEN '09' " + 
				"    WHEN CSBB.STATE ='25' " + 
				"    THEN '10' " + 
				"    WHEN CSBB.STATE ='32' " + 
				"    THEN '11' " + 
				"    WHEN CSBB.STATE ='3' " + 
				"    THEN '12' " + 
				"    WHEN CSBB.STATE ='8' " + 
				"    THEN '13' " + 
				"    WHEN CSBB.STATE ='19' " + 
				"    THEN '14' " + 
				"    WHEN CSBB.STATE ='10' " + 
				"    THEN '15' " + 
				"    WHEN CSBB.STATE ='33' " + 
				"    THEN '16' " + 
				"    WHEN CSBB.STATE ='16' " + 
				"    THEN '17' " + 
				"    WHEN CSBB.STATE ='7' " + 
				"    THEN '18' " + 
				"    WHEN CSBB.STATE ='31' " + 
				"    THEN '19' " + 
				"    WHEN CSBB.STATE ='15' " + 
				"    THEN '20' " + 
				"    WHEN CSBB.STATE ='4' " + 
				"    THEN '21' " + 
				"    WHEN CSBB.STATE ='20' " + 
				"    THEN '22' " + 
				"    WHEN CSBB.STATE ='27' " + 
				"    THEN '23' " + 
				"    WHEN CSBB.STATE ='5' " + 
				"    THEN '24' " + 
				"    WHEN CSBB.STATE ='30' " + 
				"    THEN '25' " + 
				"    WHEN CSBB.STATE ='12' " + 
				"    THEN '26' " + 
				"    WHEN CSBB.STATE ='29' " + 
				"    THEN '27' " + 
				"    WHEN CSBB.STATE ='9' " + 
				"    THEN '28' " + 
				"    WHEN CSBB.STATE ='6' " + 
				"    THEN '29' " + 
				"    WHEN CSBB.STATE ='28' " + 
				"    THEN '30' " + 
				"    WHEN CSBB.STATE ='13' " + 
				"    THEN '31' " + 
				"    WHEN CSBB.STATE ='26' " + 
				"    THEN '32' " + 
				"    WHEN CSBB.STATE ='2' " + 
				"    THEN '33' " + 
				"    WHEN CSBB.STATE ='20140403000000086' " + 
				"    THEN '34' " + 
				"    WHEN CSBB.STATE ='17' " + 
				"    THEN '35' " + 
				"    WHEN CSBB.STATE ='20140911000000090' " + 
				"    THEN '36' " + 
				"  END AS STATE2 "+
//				" TO_CHAR(CHECKLIST_ITEM.DOC_DATE ,'dd-MM-yyyy') AS DATE_DOC "+
				"FROM  " + 
				"CMS_SECURITY CMS, " + 
				"CMS_SYSTEM_BANK_BRANCH CSBB, " + 
				"CMS_CITY CC, " + 
				"CMS_COLLATERAL_NEW_MASTER CCNM, " + 
				"SCI_LE_SUB_PROFILE SLSP, " + 
				"SCI_LSP_LMT_PROFILE LMT, " + 
				"SCI_LSP_APPR_LMTS APPR, " + 
				"CMS_LIMIT_SECURITY_MAP MAP, " + 
				"CMS_CHECKLIST_ITEM CHECKLIST_ITEM, " + 
				"CMS_CHECKLIST CHECKLIST, " + 
				"CMS_BANKING_METHOD_CUST CBMC " +
				"WHERE  " + 
				"CSBB.CITY_TOWN = CC.ID AND  " + 
				"CMS.SECURITY_ORGANISATION = CSBB.SYSTEM_BANK_BRANCH_CODE AND  " + 
				"CMS.CERSAI_SECURITY_INTEREST_ID IS NULL AND " + 
				"CMS.COLLATERAL_CODE = CCNM.NEW_COLLATERAL_CODE AND  " + 
				"CCNM.CERSAI_IND ='Y' AND CCNM.NEW_COLLATERAL_CATEGORY = '"+typeOfsecurity+"' AND " + 
//				"UPPER(CHECKLIST_ITEM.DOC_DESCRIPTION) IN('MEMORANDUM OF ENTRY','DEED OF HYPOTHECATION','LETTER OF HYPOTHECATION','JOINT DEED OF HYPOTHECATION') AND " + 
				"CHECKLIST_ITEM.STATUS = 'RECEIVED' AND " + 
				"SLSP.STATUS = 'ACTIVE' " + 
				"AND MAP.UPDATE_STATUS_IND = 'I' " + 
				"AND CMS.STATUS = 'ACTIVE' " + 
				"AND CHECKLIST_ITEM.IS_DELETED = 'N' "+
				"AND CHECKLIST.CMS_COLLATERAL_ID = CMS.CMS_COLLATERAL_ID AND "+
				"(CHECKLIST_ITEM.RECEIVED_DATE BETWEEN TO_DATE('"+fromDate+"','DD/MM/YYYY') AND TO_DATE('"+toDate+"','DD/MM/YYYY') ) AND "+
				//"CHECKLIST_ITEM.RECEIVED_DATE >= TO_DATE('23/02/2016','DD/MM/YYYY') AND "+
				"CHECKLIST.CHECKLIST_ID = CHECKLIST_ITEM.CHECKLIST_ID AND " + 
				"CHECKLIST.CMS_LSP_LMT_PROFILE_ID = LMT.CMS_LSP_LMT_PROFILE_ID AND " + 
				"CMS.CMS_COLLATERAL_ID= MAP.CMS_COLLATERAL_ID and " + 
				"APPR.CMS_LSP_APPR_LMTS_ID =MAP.CMS_LSP_APPR_LMTS_ID and  " + 
				"LMT.CMS_LSP_LMT_PROFILE_ID=APPR.CMS_LIMIT_PROFILE_ID and  " + 
				"LMT.LLP_LE_ID=SLSP.LSP_LE_ID and " + 
				"SLSP.LSP_LE_ID         =       CBMC.CUSTOMER_ID " + 
				"AND CBMC.STATUS = 'ACTIVE' " +
//				"SLSP.BANKING_METHOD='"+bankMethod+"'";
				"AND CBMC.CMS_BANKING_METHOD_NAME='"+bankMethod+"'";
		System.out.println("Query = "+sql);
		try {
			reportQueryResult = getJdbcTemplate().query(sql,new RowMapper() {
				@Override
				public String[] mapRow(ResultSet rs, int rownum) throws SQLException {
//					System.out.println("============inside Row mapper=========");
					int columnCount = rs.getMetaData().getColumnCount();
	//				System.out.println("===============Column Count================"+columnCount);
					String[] objArray= new String[columnCount];
						for (int i = 0; i < columnCount; i++) {
							objArray[i]=rs.getString(i+1);
						}
					return objArray;
				}
			});
		} catch (Exception e) {
			throw new ReportException("Error in getSecurityThirdInterestRecords.",e); 
		}
		return reportQueryResult;
	}
	
	public List<String[]> getPropertyRecords(String bankMethod,String fromDate,String toDate,String typeOfsecurity) {
		List<String[]> reportQueryResult= new LinkedList<String[]>(); 
		/** genrating query from report object, object will be ob class containing filter values*/
//		String sql=generateQuery(reports, filter);
		String sql="SELECT DISTINCT " +
//				"CCCE.ENTRY_NAME AS NATURE_OF_PROPERTY, " +
				"CMS_MAP.CERSAI_VALUE AS NATURE_OF_PROPERTY, "+
				"CMS.CMS_COLLATERAL_ID, " +
				"CASE " + 
				"         WHEN CMS_MAP.CLIMS_VALUE in ('STOCKS AND RECEIVABLES') " + 
				"           THEN " + 
				"           FindDpValue(CMS.CMS_COLLATERAL_ID) " + 
				"         ELSE CMS.CMV " + 
				"  END AS DP_OMV_VALUE, "+
				
				//"CMS.CMV AS SECURITY_AMOUNT, " +
				"CP.PROPERTY_ADDRESS, " + 
				"CP.PROPERTY_ADDRESS_2, " + 
				"CP.PROPERTY_ADDRESS_3, " + 
				"CP.PROPERTY_ADDRESS_4, " + 
				"CP.PROPERTY_ADDRESS_5, " + 
				"CP.PROPERTY_ADDRESS_6, " + 
				"CP.BUILTUP_AREA, " + 
				"CP.BUILTUP_AREA_UOM, " + 
				"CC.CITY_NAME, " + 
				"CP.STATE, " + 
				"CP.PINCODE, " +
				"CASE " + 
				"    WHEN CP.STATE ='11' " + 
				"    THEN '01' " + 
				"    WHEN CP.STATE ='1' " + 
				"    THEN '02' " + 
				"    WHEN CP.STATE ='18' " + 
				"    THEN '04' " + 
				"    WHEN CP.STATE ='14' " + 
				"    THEN '05' " + 
				"    WHEN CP.STATE ='21' " + 
				"    THEN '06' " + 
				"    WHEN CP.STATE ='23' " + 
				"    THEN '08' " + 
				"    WHEN CP.STATE ='24' " + 
				"    THEN '09' " + 
				"    WHEN CP.STATE ='25' " + 
				"    THEN '10' " + 
				"    WHEN CP.STATE ='32' " + 
				"    THEN '11' " + 
				"    WHEN CP.STATE ='3' " + 
				"    THEN '12' " + 
				"    WHEN CP.STATE ='8' " + 
				"    THEN '13' " + 
				"    WHEN CP.STATE ='19' " + 
				"    THEN '14' " + 
				"    WHEN CP.STATE ='10' " + 
				"    THEN '15' " + 
				"    WHEN CP.STATE ='33' " + 
				"    THEN '16' " + 
				"    WHEN CP.STATE ='16' " + 
				"    THEN '17' " + 
				"    WHEN CP.STATE ='7' " + 
				"    THEN '18' " + 
				"    WHEN CP.STATE ='31' " + 
				"    THEN '19' " + 
				"    WHEN CP.STATE ='15' " + 
				"    THEN '20' " + 
				"    WHEN CP.STATE ='4' " + 
				"    THEN '21' " + 
				"    WHEN CP.STATE ='20' " + 
				"    THEN '22' " + 
				"    WHEN CP.STATE ='27' " + 
				"    THEN '23' " + 
				"    WHEN CP.STATE ='5' " + 
				"    THEN '24' " + 
				"    WHEN CP.STATE ='30' " + 
				"    THEN '25' " + 
				"    WHEN CP.STATE ='12' " + 
				"    THEN '26' " + 
				"    WHEN CP.STATE ='29' " + 
				"    THEN '27' " + 
				"    WHEN CP.STATE ='9' " + 
				"    THEN '28' " + 
				"    WHEN CP.STATE ='6' " + 
				"    THEN '29' " + 
				"    WHEN CP.STATE ='28' " + 
				"    THEN '30' " + 
				"    WHEN CP.STATE ='13' " + 
				"    THEN '31' " + 
				"    WHEN CP.STATE ='26' " + 
				"    THEN '32' " + 
				"    WHEN CP.STATE ='2' " + 
				"    THEN '33' " + 
				"    WHEN CP.STATE ='20140403000000086' " + 
				"    THEN '34' " + 
				"    WHEN CP.STATE ='17' " + 
				"    THEN '35' " + 
				"    WHEN CP.STATE ='20140911000000090' " + 
				"    THEN '36' " + 
				"  END AS STATE2, "+
//				" TO_CHAR(CHECKLIST_ITEM.DOC_DATE ,'dd-MM-yyyy') AS DATE_DOC, "+
				"CMS_MAP.CLIMS_VALUE "+
				"FROM  " + 
				"CMS_PROPERTY CP, " + 
				"CMS_CITY CC, " + 
				"CMS_SECURITY CMS, " + 
				"SCI_LE_SUB_PROFILE SLSP, " + 
				"SCI_LSP_LMT_PROFILE LMT, " + 
				"CMS_COLLATERAL_NEW_MASTER CCNM, " + 
				"SCI_LSP_APPR_LMTS APPR, " + 
				"CMS_LIMIT_SECURITY_MAP MAP, " + 
				"CMS_CHECKLIST_ITEM CHECKLIST_ITEM, " + 
				"CMS_CHECKLIST CHECKLIST, " + 
				"COMMON_CODE_CATEGORY_ENTRY CCCE, "+
				"CMS_SUB_CERSAI_MAPPING CMS_MAP, "+
				"CMS_BANKING_METHOD_CUST CBMC " +
				"WHERE  " + 
				"UPPER(CP.PROPERTY_TYPE) IN (SELECT DISTINCT ENTRY_CODE FROM COMMON_CODE_CATEGORY_ENTRY WHERE ENTRY_NAME IN (SELECT DISTINCT CLIMS_VALUE FROM CMS_SUB_CERSAI_MAPPING WHERE MASTER_NAME = 'PROPERTY_TYPE')) AND  " + 
				"CP.NEAREST_CITY = CC.ID AND " + 
				"CP.PROPERTY_TYPE = CCCE.ENTRY_CODE AND "+
				"CCCE.ENTRY_NAME = CMS_MAP.CLIMS_VALUE AND "+
				"CMS_MAP.ID in (SELECT MAX(ID) " + 
				"   FROM CMS_SUB_CERSAI_MAPPING " + 
				"   WHERE MASTER_NAME = 'PROPERTY_TYPE' " + 
				"   GROUP BY CLIMS_VALUE) AND "+
				"CMS.CMS_COLLATERAL_ID = CP.CMS_COLLATERAL_ID   AND " + 
				"CMS.CERSAI_SECURITY_INTEREST_ID IS NULL AND " + 
				"CMS.COLLATERAL_CODE = CCNM.NEW_COLLATERAL_CODE AND  " + 
				"CCNM.CERSAI_IND ='Y' AND CCNM.NEW_COLLATERAL_CATEGORY = '"+typeOfsecurity+"' AND " + 
//				"UPPER(CHECKLIST_ITEM.DOC_DESCRIPTION) IN('MEMORANDUM OF ENTRY','DEED OF HYPOTHECATION','LETTER OF HYPOTHECATION','JOINT DEED OF HYPOTHECATION') AND " + 
				"CHECKLIST_ITEM.STATUS = 'RECEIVED' AND " + 
				"SLSP.STATUS = 'ACTIVE' " + 
				"AND MAP.UPDATE_STATUS_IND = 'I' " + 
				"AND CMS.STATUS = 'ACTIVE' " + 
				"AND CHECKLIST_ITEM.IS_DELETED = 'N' "+
				"AND CHECKLIST.CMS_COLLATERAL_ID = CMS.CMS_COLLATERAL_ID AND "+
				"(CHECKLIST_ITEM.RECEIVED_DATE BETWEEN TO_DATE('"+fromDate+"','DD/MM/YYYY') AND TO_DATE('"+toDate+"','DD/MM/YYYY') ) AND "+
				//"CHECKLIST_ITEM.RECEIVED_DATE >= TO_DATE('23/02/2016','DD/MM/YYYY') AND "+
				"CHECKLIST.CHECKLIST_ID = CHECKLIST_ITEM.CHECKLIST_ID AND " + 
				"CHECKLIST.CMS_LSP_LMT_PROFILE_ID = LMT.CMS_LSP_LMT_PROFILE_ID AND " + 
				"CMS.CMS_COLLATERAL_ID= MAP.CMS_COLLATERAL_ID and " + 
				"APPR.CMS_LSP_APPR_LMTS_ID =MAP.CMS_LSP_APPR_LMTS_ID and  " + 
				"LMT.CMS_LSP_LMT_PROFILE_ID=APPR.CMS_LIMIT_PROFILE_ID and  " + 
				"LMT.LLP_LE_ID=SLSP.LSP_LE_ID and " + 
				"SLSP.LSP_LE_ID         =       CBMC.CUSTOMER_ID " + 
				"AND CBMC.STATUS = 'ACTIVE' " +
//				"SLSP.BANKING_METHOD='"+bankMethod+"'";
				"AND CBMC.CMS_BANKING_METHOD_NAME='"+bankMethod+"'";
		System.out.println("Query = "+sql);
		try {
			reportQueryResult = getJdbcTemplate().query(sql,new RowMapper() {
				@Override
				public String[] mapRow(ResultSet rs, int rownum) throws SQLException {
//					System.out.println("============inside Row mapper=========");
					int columnCount = rs.getMetaData().getColumnCount();
	//				System.out.println("===============Column Count================"+columnCount);
					String[] objArray= new String[columnCount];
						for (int i = 0; i < columnCount; i++) {
							objArray[i]=rs.getString(i+1);
						}
					return objArray;
				}
			});
		} catch (Exception e) {
			throw new ReportException("Error in getPropertyRecords.",e); 
		}
		return reportQueryResult;
	}
	
	public List<String[]> getDocumentRecords(String bankMethod,String fromDate,String toDate,String typeOfsecurity) {
		List<String[]> reportQueryResult= new LinkedList<String[]>(); 
		/** genrating query from report object, object will be ob class containing filter values*/
//		String sql=generateQuery(reports, filter);
		String sql="";
		if(typeOfsecurity.equals("IMMOVABLE")) {
		sql="SELECT DISTINCT CP.NEAREST_CITY , " + 
				"CMS.CMS_COLLATERAL_ID, " + 
				"CMS.CMV AS SECURITY_AMOUNT, " +
				"CC.CITY_NAME, " + 
				"CP.STATE, " + 
				"CP.PINCODE, " + 
				"TO_CHAR(CP.SALE_PURCHASE_DATE ,'dd-MM-yyyy') AS DATE1, " +
				"TO_CHAR(CMS.SALE_DEED_PURCHASE_DATE ,'dd-MM-yyyy') AS DATE2, "+
				"CASE " + 
				"    WHEN CP.STATE ='11' " + 
				"    THEN '01' " + 
				"    WHEN CP.STATE ='1' " + 
				"    THEN '02' " + 
				"    WHEN CP.STATE ='18' " + 
				"    THEN '04' " + 
				"    WHEN CP.STATE ='14' " + 
				"    THEN '05' " + 
				"    WHEN CP.STATE ='21' " + 
				"    THEN '06' " + 
				"    WHEN CP.STATE ='23' " + 
				"    THEN '08' " + 
				"    WHEN CP.STATE ='24' " + 
				"    THEN '09' " + 
				"    WHEN CP.STATE ='25' " + 
				"    THEN '10' " + 
				"    WHEN CP.STATE ='32' " + 
				"    THEN '11' " + 
				"    WHEN CP.STATE ='3' " + 
				"    THEN '12' " + 
				"    WHEN CP.STATE ='8' " + 
				"    THEN '13' " + 
				"    WHEN CP.STATE ='19' " + 
				"    THEN '14' " + 
				"    WHEN CP.STATE ='10' " + 
				"    THEN '15' " + 
				"    WHEN CP.STATE ='33' " + 
				"    THEN '16' " + 
				"    WHEN CP.STATE ='16' " + 
				"    THEN '17' " + 
				"    WHEN CP.STATE ='7' " + 
				"    THEN '18' " + 
				"    WHEN CP.STATE ='31' " + 
				"    THEN '19' " + 
				"    WHEN CP.STATE ='15' " + 
				"    THEN '20' " + 
				"    WHEN CP.STATE ='4' " + 
				"    THEN '21' " + 
				"    WHEN CP.STATE ='20' " + 
				"    THEN '22' " + 
				"    WHEN CP.STATE ='27' " + 
				"    THEN '23' " + 
				"    WHEN CP.STATE ='5' " + 
				"    THEN '24' " + 
				"    WHEN CP.STATE ='30' " + 
				"    THEN '25' " + 
				"    WHEN CP.STATE ='12' " + 
				"    THEN '26' " + 
				"    WHEN CP.STATE ='29' " + 
				"    THEN '27' " + 
				"    WHEN CP.STATE ='9' " + 
				"    THEN '28' " + 
				"    WHEN CP.STATE ='6' " + 
				"    THEN '29' " + 
				"    WHEN CP.STATE ='28' " + 
				"    THEN '30' " + 
				"    WHEN CP.STATE ='13' " + 
				"    THEN '31' " + 
				"    WHEN CP.STATE ='26' " + 
				"    THEN '32' " + 
				"    WHEN CP.STATE ='2' " + 
				"    THEN '33' " + 
				"    WHEN CP.STATE ='20140403000000086' " + 
				"    THEN '34' " + 
				"    WHEN CP.STATE ='17' " + 
				"    THEN '35' " + 
				"    WHEN CP.STATE ='20140911000000090' " + 
				"    THEN '36' " + 
				"  END AS STATE2, "+
				" CHECKLIST_ITEM.DOC_DESCRIPTION "+
//				" TO_CHAR(CHECKLIST_ITEM.DOC_DATE ,'dd-MM-yyyy') AS DATE_DOC "+
				"FROM  " + 
				"CMS_PROPERTY CP, " + 
				"CMS_CITY CC, " + 
				"CMS_SECURITY CMS, " + 
				"SCI_LE_SUB_PROFILE SLSP, " + 
				"SCI_LSP_LMT_PROFILE LMT, " + 
				"SCI_LSP_APPR_LMTS APPR, " + 
				"CMS_LIMIT_SECURITY_MAP MAP, " + 
				"CMS_COLLATERAL_NEW_MASTER CCNM, " + 
				"CMS_CHECKLIST_ITEM CHECKLIST_ITEM, " + 
				"CMS_CHECKLIST CHECKLIST, " + 
				"CMS_BANKING_METHOD_CUST CBMC " +
				"WHERE  " + 
				"CP.NEAREST_CITY = CC.ID AND " + 
				"CMS.CMS_COLLATERAL_ID = CP.CMS_COLLATERAL_ID   AND " + 
				"CMS.CERSAI_SECURITY_INTEREST_ID IS NULL AND " + 
				"CMS.COLLATERAL_CODE = CCNM.NEW_COLLATERAL_CODE AND  " + 
				"CCNM.CERSAI_IND ='Y' AND CCNM.NEW_COLLATERAL_CATEGORY = '"+typeOfsecurity+"' AND " + 
//				"UPPER(CHECKLIST_ITEM.DOC_DESCRIPTION) IN('MEMORANDUM OF ENTRY','DEED OF HYPOTHECATION','LETTER OF HYPOTHECATION','JOINT DEED OF HYPOTHECATION') AND " + 
				"CHECKLIST_ITEM.STATUS = 'RECEIVED' AND " +
				"SLSP.STATUS = 'ACTIVE' " + 
				"AND MAP.UPDATE_STATUS_IND = 'I' " + 
				"AND CMS.STATUS = 'ACTIVE' " + 
				"AND CHECKLIST_ITEM.IS_DELETED = 'N' AND "+
				"CHECKLIST.CMS_COLLATERAL_ID = CMS.CMS_COLLATERAL_ID AND "+
				"(CHECKLIST_ITEM.RECEIVED_DATE BETWEEN TO_DATE('"+fromDate+"','DD/MM/YYYY') AND TO_DATE('"+toDate+"','DD/MM/YYYY') ) AND "+
				//"CHECKLIST_ITEM.RECEIVED_DATE >= TO_DATE('23/02/2016','DD/MM/YYYY') AND "+
				"CHECKLIST.CHECKLIST_ID = CHECKLIST_ITEM.CHECKLIST_ID AND " + 
				"CHECKLIST.CMS_LSP_LMT_PROFILE_ID = LMT.CMS_LSP_LMT_PROFILE_ID AND " + 
				"CMS.CMS_COLLATERAL_ID= MAP.CMS_COLLATERAL_ID and " + 
				"APPR.CMS_LSP_APPR_LMTS_ID =MAP.CMS_LSP_APPR_LMTS_ID and  " + 
				"LMT.CMS_LSP_LMT_PROFILE_ID=APPR.CMS_LIMIT_PROFILE_ID and  " + 
				"LMT.LLP_LE_ID=SLSP.LSP_LE_ID and " + 
				"SLSP.LSP_LE_ID         =       CBMC.CUSTOMER_ID " + 
				"AND CBMC.STATUS = 'ACTIVE' " +
//				"SLSP.BANKING_METHOD='"+bankMethod+"'";
				"AND CBMC.CMS_BANKING_METHOD_NAME='"+bankMethod+"'";
		
			System.out.println("Query DOC of IMMOVABLE= "+sql);
		}else {
			
			sql="SELECT DISTINCT CSBB.CITY_TOWN, " + 
					"CMS.CMS_COLLATERAL_ID, " + 
					"CMS.CMV AS SECURITY_AMOUNT, " +
					"CC.CITY_NAME, " + 
					"CSBB.STATE, " + 
					"CSBB.PINCODE, " + 
					"TO_CHAR(CHECKLIST_ITEM.DOC_DATE ,'dd-MM-yyyy') AS DATE1, " +
					"CASE " + 
					"    WHEN CSBB.STATE ='11' " + 
					"    THEN '01' " + 
					"    WHEN CSBB.STATE ='1' " + 
					"    THEN '02' " + 
					"    WHEN CSBB.STATE ='18' " + 
					"    THEN '04' " + 
					"    WHEN CSBB.STATE ='14' " + 
					"    THEN '05' " + 
					"    WHEN CSBB.STATE ='21' " + 
					"    THEN '06' " + 
					"    WHEN CSBB.STATE ='23' " + 
					"    THEN '08' " + 
					"    WHEN CSBB.STATE ='24' " + 
					"    THEN '09' " + 
					"    WHEN CSBB.STATE ='25' " + 
					"    THEN '10' " + 
					"    WHEN CSBB.STATE ='32' " + 
					"    THEN '11' " + 
					"    WHEN CSBB.STATE ='3' " + 
					"    THEN '12' " + 
					"    WHEN CSBB.STATE ='8' " + 
					"    THEN '13' " + 
					"    WHEN CSBB.STATE ='19' " + 
					"    THEN '14' " + 
					"    WHEN CSBB.STATE ='10' " + 
					"    THEN '15' " + 
					"    WHEN CSBB.STATE ='33' " + 
					"    THEN '16' " + 
					"    WHEN CSBB.STATE ='16' " + 
					"    THEN '17' " + 
					"    WHEN CSBB.STATE ='7' " + 
					"    THEN '18' " + 
					"    WHEN CSBB.STATE ='31' " + 
					"    THEN '19' " + 
					"    WHEN CSBB.STATE ='15' " + 
					"    THEN '20' " + 
					"    WHEN CSBB.STATE ='4' " + 
					"    THEN '21' " + 
					"    WHEN CSBB.STATE ='20' " + 
					"    THEN '22' " + 
					"    WHEN CSBB.STATE ='27' " + 
					"    THEN '23' " + 
					"    WHEN CSBB.STATE ='5' " + 
					"    THEN '24' " + 
					"    WHEN CSBB.STATE ='30' " + 
					"    THEN '25' " + 
					"    WHEN CSBB.STATE ='12' " + 
					"    THEN '26' " + 
					"    WHEN CSBB.STATE ='29' " + 
					"    THEN '27' " + 
					"    WHEN CSBB.STATE ='9' " + 
					"    THEN '28' " + 
					"    WHEN CSBB.STATE ='6' " + 
					"    THEN '29' " + 
					"    WHEN CSBB.STATE ='28' " + 
					"    THEN '30' " + 
					"    WHEN CSBB.STATE ='13' " + 
					"    THEN '31' " + 
					"    WHEN CSBB.STATE ='26' " + 
					"    THEN '32' " + 
					"    WHEN CSBB.STATE ='2' " + 
					"    THEN '33' " + 
					"    WHEN CSBB.STATE ='20140403000000086' " + 
					"    THEN '34' " + 
					"    WHEN CSBB.STATE ='17' " + 
					"    THEN '35' " + 
					"    WHEN CSBB.STATE ='20140911000000090' " + 
					"    THEN '36' " + 
					"  END AS STATE2, "+
					" CHECKLIST_ITEM.DOC_DESCRIPTION "+
					"FROM  " + 
					"CMS_CITY CC, " + 
					"CMS_SYSTEM_BANK_BRANCH CSBB, "+
					"CMS_SECURITY CMS, " + 
					"SCI_LE_SUB_PROFILE SLSP, " + 
					"SCI_LSP_LMT_PROFILE LMT, " + 
					"SCI_LSP_APPR_LMTS APPR, " + 
					"CMS_LIMIT_SECURITY_MAP MAP, " + 
					"CMS_COLLATERAL_NEW_MASTER CCNM, " + 
					"CMS_CHECKLIST_ITEM CHECKLIST_ITEM, " + 
					"CMS_CHECKLIST CHECKLIST, " + 
					"CMS_BANKING_METHOD_CUST CBMC " +
					"WHERE  " + 
					"CMS.SECURITY_ORGANISATION = CSBB.SYSTEM_BANK_BRANCH_CODE AND " + 
					"CSBB.CITY_TOWN = CC.ID AND " + 
					"CMS.CERSAI_SECURITY_INTEREST_ID IS NULL AND " + 
					"CMS.COLLATERAL_CODE = CCNM.NEW_COLLATERAL_CODE AND  " + 
					"CCNM.CERSAI_IND ='Y' AND CCNM.NEW_COLLATERAL_CATEGORY = '"+typeOfsecurity+"' AND " + 
//					"UPPER(CHECKLIST_ITEM.DOC_DESCRIPTION) IN('MEMORANDUM OF ENTRY','DEED OF HYPOTHECATION','LETTER OF HYPOTHECATION','JOINT DEED OF HYPOTHECATION') AND " + 
					"CHECKLIST_ITEM.STATUS = 'RECEIVED' AND " +
					"SLSP.STATUS = 'ACTIVE' " + 
					"AND MAP.UPDATE_STATUS_IND = 'I' " + 
					"AND CMS.STATUS = 'ACTIVE' " + 
					"AND CHECKLIST_ITEM.IS_DELETED = 'N' AND "+
					"CHECKLIST.CMS_COLLATERAL_ID = CMS.CMS_COLLATERAL_ID AND "+
					"(CHECKLIST_ITEM.RECEIVED_DATE BETWEEN TO_DATE('"+fromDate+"','DD/MM/YYYY') AND TO_DATE('"+toDate+"','DD/MM/YYYY') ) AND "+
					//"CHECKLIST_ITEM.RECEIVED_DATE >= TO_DATE('23/02/2016','DD/MM/YYYY') AND "+
					"CHECKLIST.CHECKLIST_ID = CHECKLIST_ITEM.CHECKLIST_ID AND " + 
					"CHECKLIST.CMS_LSP_LMT_PROFILE_ID = LMT.CMS_LSP_LMT_PROFILE_ID AND " + 
					"CMS.CMS_COLLATERAL_ID= MAP.CMS_COLLATERAL_ID and " + 
					"APPR.CMS_LSP_APPR_LMTS_ID =MAP.CMS_LSP_APPR_LMTS_ID and  " + 
					"LMT.CMS_LSP_LMT_PROFILE_ID=APPR.CMS_LIMIT_PROFILE_ID and  " + 
					"LMT.LLP_LE_ID=SLSP.LSP_LE_ID and " + 
					"SLSP.LSP_LE_ID         =       CBMC.CUSTOMER_ID " + 
					"AND CBMC.STATUS = 'ACTIVE' " +
//					"SLSP.BANKING_METHOD='"+bankMethod+"'";
					"AND CBMC.CMS_BANKING_METHOD_NAME='"+bankMethod+"'";
			
			System.out.println("Query DOC of MOVABLE= "+sql);
		}
		
		try {
			reportQueryResult = getJdbcTemplate().query(sql,new RowMapper() {
				@Override
				public String[] mapRow(ResultSet rs, int rownum) throws SQLException {
//					System.out.println("============inside Row mapper=========");
					int columnCount = rs.getMetaData().getColumnCount();
	//				System.out.println("===============Column Count================"+columnCount);
					String[] objArray= new String[columnCount];
						for (int i = 0; i < columnCount; i++) {
							objArray[i]=rs.getString(i+1);
						}
					return objArray;
				}
			});
		} catch (Exception e) {
			throw new ReportException("Error in getDocumentRecords.",e); 
		}
		return reportQueryResult;
	}
	
	public List<String[]> getLoanRecords(String bankMethod,String fromDate,String toDate,String typeOfsecurity) {
		List<String[]> reportQueryResult= new LinkedList<String[]>(); 
		/** genrating query from report object, object will be ob class containing filter values*/
//		String sql=generateQuery(reports, filter);
		String sql="SELECT DISTINCT CMS_MAP.CERSAI_VALUE as  NATURE_OF_FACILITY, " + 
				"CMS.CMS_COLLATERAL_ID AS SECURITY_ID , " + 
				"CMS.CMV AS SECURITY_AMOUNT, " +
				"SLSP.LSP_LE_ID AS PARTY_ID, " + 
				"APPR.CMS_LSP_APPR_LMTS_ID AS FACILITY_ID, " + 
				//"APPR.CMS_REQ_SEC_COVERAGE AS SANCTION_AMOUNT, " + 
				"CHECKLIST_ITEM.DOC_AMT AS SANCTION_AMOUNT, "+
				//"CDI.EXPIRY_DATE AS DISBURSED_DATE, " + 
				"TO_CHAR(CHECKLIST_ITEM.DOC_DATE,'dd-MM-yyyy') AS DISBURSED_DATE, "+
				"SLU.UDF19  AS RATE_OF_INTEREST "+
				"FROM  " + 
				"CMS_FACILITY_NEW_MASTER CFNM, " + 
				//"CMS_DOCUMENT_ITEM CDI, " + 
				"CMS_SECURITY CMS, " + 
				"SCI_LE_SUB_PROFILE SLSP, " + 
				"SCI_LE_MAIN_PROFILE SLMP, "+
				"SCI_LE_UDF SLU, "+
				"SCI_LSP_LMT_PROFILE LMT, " + 
				"SCI_LSP_APPR_LMTS APPR, " + 
				"CMS_LIMIT_SECURITY_MAP MAP, " + 
				//"CMS_DOCUMENT_MASTERLIST CDM, " + 
				"CMS_COLLATERAL_NEW_MASTER CCNM, " + 
				"CMS_CHECKLIST_ITEM CHECKLIST_ITEM, " + 
				"CMS_CHECKLIST CHECKLIST, " +
				"CMS_SUB_CERSAI_MAPPING CMS_MAP, "+
				"COMMON_CODE_CATEGORY_ENTRY CCCE, "+
				"CMS_DOCUMENT_GLOBALLIST DOC_GLOB, "+
				"CMS_BANKING_METHOD_CUST CBMC " +
				"WHERE  " + 
				"UPPER(CFNM.NEW_FACILITY_CATEGORY) IN " + 
				" (SELECT DISTINCT ENTRY_CODE FROM COMMON_CODE_CATEGORY_ENTRY WHERE ENTRY_NAME IN (SELECT DISTINCT CLIMS_VALUE FROM CMS_SUB_CERSAI_MAPPING WHERE MASTER_NAME = 'FACILITY_CATEGORY')) AND " +
				"CMS_MAP.ID in (SELECT MAX(ID) " + 
				"    FROM CMS_SUB_CERSAI_MAPPING " + 
				"    WHERE MASTER_NAME = 'FACILITY_CATEGORY' " + 
				"    GROUP BY CLIMS_VALUE) AND "+
				"CFNM.NEW_FACILITY_CATEGORY = CCCE.ENTRY_CODE AND " + 
				"CCCE.ENTRY_NAME = CMS_MAP.CLIMS_VALUE AND " + 
				"APPR.LMT_FAC_CODE = CFNM.NEW_FACILITY_CODE AND " + 
//				"CDM.MASTERLIST_ID = CDI.MASTERLIST_ID AND " + 
//				"APPR.LMT_FAC_CODE = CDM.SECURITY_SUB_TYPE_ID AND " + 
				"CMS.CERSAI_SECURITY_INTEREST_ID IS NULL AND " + 
				"CMS.COLLATERAL_CODE = CCNM.NEW_COLLATERAL_CODE AND  " + 
				"CCNM.CERSAI_IND ='Y' AND CCNM.NEW_COLLATERAL_CATEGORY = '"+typeOfsecurity+"' AND " + 
				"DOC_GLOB.CERSAI_IND = 'Y' AND " + 
				"CHECKLIST_ITEM.DOCUMENT_CODE = DOC_GLOB.DOCUMENT_CODE AND " + 
				"CHECKLIST.CMS_COLLATERAL_ID = CMS.CMS_COLLATERAL_ID AND "+
//				"UPPER(CHECKLIST_ITEM.DOC_DESCRIPTION) IN('MEMORANDUM OF ENTRY','DEED OF HYPOTHECATION','LETTER OF HYPOTHECATION','JOINT DEED OF HYPOTHECATION') AND " + 
				"CHECKLIST_ITEM.STATUS = 'RECEIVED' AND " + 
				"SLSP.STATUS = 'ACTIVE' " + 
				"AND MAP.UPDATE_STATUS_IND = 'I' " + 
				"AND CMS.STATUS = 'ACTIVE' " + 
				"AND CHECKLIST_ITEM.IS_DELETED = 'N' AND "+
				"(CHECKLIST_ITEM.RECEIVED_DATE BETWEEN TO_DATE('"+fromDate+"','DD/MM/YYYY') AND TO_DATE('"+toDate+"','DD/MM/YYYY') ) AND "+
				//"CHECKLIST_ITEM.RECEIVED_DATE >= TO_DATE('23/02/2016','DD/MM/YYYY') AND "+
				"CHECKLIST.CHECKLIST_ID = CHECKLIST_ITEM.CHECKLIST_ID AND " + 
				"CHECKLIST.CMS_LSP_LMT_PROFILE_ID = LMT.CMS_LSP_LMT_PROFILE_ID AND " +
				"SLSP.CMS_LE_MAIN_PROFILE_ID = SLMP.CMS_LE_MAIN_PROFILE_ID AND "+
				"SLMP.CMS_LE_MAIN_PROFILE_ID = SLU.CMS_LE_MAIN_PROFILE_ID AND "+
				"CMS.CMS_COLLATERAL_ID= MAP.CMS_COLLATERAL_ID and " + 
				"APPR.CMS_LSP_APPR_LMTS_ID =MAP.CMS_LSP_APPR_LMTS_ID and  " + 
				"LMT.CMS_LSP_LMT_PROFILE_ID=APPR.CMS_LIMIT_PROFILE_ID and  " + 
				"LMT.LLP_LE_ID=SLSP.LSP_LE_ID and " + 
				"SLSP.LSP_LE_ID         =       CBMC.CUSTOMER_ID " + 
				"AND CBMC.STATUS = 'ACTIVE' " +
//				"SLSP.BANKING_METHOD='"+bankMethod+"'";
				"AND CBMC.CMS_BANKING_METHOD_NAME='"+bankMethod+"'";
		System.out.println("Query = "+sql);
		try {
			reportQueryResult = getJdbcTemplate().query(sql,new RowMapper() {
				@Override
				public String[] mapRow(ResultSet rs, int rownum) throws SQLException {
//					System.out.println("============inside Row mapper=========");
					int columnCount = rs.getMetaData().getColumnCount();
	//				System.out.println("===============Column Count================"+columnCount);
					String[] objArray= new String[columnCount];
						for (int i = 0; i < columnCount; i++) {
							objArray[i]=rs.getString(i+1);
						}
					return objArray;
				}
			});
		} catch (Exception e) {
			throw new ReportException("Error in getLoanRecords.",e); 
		}
		return reportQueryResult;
	}
	
	public List<String[]> getAssetRecords(String bankMethod,String fromDate,String toDate,String typeOfsecurity) {
		List<String[]> reportQueryResult= new LinkedList<String[]>(); 
		/** genrating query from report object, object will be ob class containing filter values*/
//		String sql=generateQuery(reports, filter);
		String sql="SELECT DISTINCT " +
//				"CCNM.NEW_COLLATERAL_DESCRIPTION, " +
				"CMS_MAP.CERSAI_VALUE, "+
				"CMS.CMS_COLLATERAL_ID , " + 
				"CASE " + 
				"         WHEN CMS_MAP.CLIMS_VALUE in ('STOCKS AND RECEIVABLES') " + 
				"           THEN " + 
				"           FindDpValue(CMS.CMS_COLLATERAL_ID) " + 
				"         ELSE CMS.CMV " + 
				"  END AS DP_OMV_VALUE, "+
//				"CMS.CMV AS SECURITY_AMOUNT, " +
				"CMS.SUBTYPE_NAME AS ASSET_SUBTYPE, "+
//				" TO_CHAR(CHECKLIST_ITEM.DOC_DATE ,'dd-MM-yyyy') AS DATE_DOC, "+
				"CMS_MAP.CLIMS_VALUE "+
				"FROM  " + 
				"CMS_COLLATERAL_NEW_MASTER CCNM, " + 
				"CMS_SECURITY CMS, " + 
				"SCI_LE_SUB_PROFILE SLSP, " + 
				"SCI_LSP_LMT_PROFILE LMT, " + 
				"SCI_LSP_APPR_LMTS APPR, " + 
				"CMS_LIMIT_SECURITY_MAP MAP, " + 
				"CMS_CHECKLIST_ITEM CHECKLIST_ITEM, " + 
				"CMS_CHECKLIST CHECKLIST, " + 
				"CMS_SUB_CERSAI_MAPPING CMS_MAP, " + 
				"COMMON_CODE_CATEGORY_ENTRY CCCE, " +
				"CMS_BANKING_METHOD_CUST CBMC " +
				"WHERE " + 
				"UPPER(CCNM.NEW_COLLATERAL_DESCRIPTION) IN  " + 
//				"('INVENTORY','RECEIVABLES','EQUIPMENT AND MACHINERY','MOVABLE','COPYRIGHT','PATENT','LICENSES','IPR','INTANGIBLE', " + 
//				"'PLANT AND MACHINERY','PLANT AND EQUIPMENT','STOCK AND RECEIVABLES') AND " + 
//				"(SELECT ENTRY_CODE FROM COMMON_CODE_CATEGORY_ENTRY WHERE ENTRY_NAME IN (SELECT DISTINCT CLIMS_VALUE FROM CMS_SUB_CERSAI_MAPPING WHERE MASTER_NAME = 'PROPERTY_TYPE')) AND "+
				"(SELECT DISTINCT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY WHERE ENTRY_NAME IN (SELECT DISTINCT CLIMS_VALUE FROM CMS_SUB_CERSAI_MAPPING WHERE MASTER_NAME = 'PROPERTY_TYPE')) AND "+
				"CCNM.NEW_COLLATERAL_DESCRIPTION = CCCE.ENTRY_NAME AND " +
				"CCCE.ENTRY_NAME = CMS_MAP.CLIMS_VALUE AND "+
				
				"CMS.COLLATERAL_CODE = CCNM.NEW_COLLATERAL_CODE AND  " + 
				"CMS.CERSAI_SECURITY_INTEREST_ID IS NULL AND " + 
				"CCNM.CERSAI_IND ='Y' AND CCNM.NEW_COLLATERAL_CATEGORY = '"+typeOfsecurity+"' AND " + 
//				"UPPER(CHECKLIST_ITEM.DOC_DESCRIPTION) IN('MEMORANDUM OF ENTRY','DEED OF HYPOTHECATION','LETTER OF HYPOTHECATION','JOINT DEED OF HYPOTHECATION') AND " + 
				"CHECKLIST_ITEM.STATUS = 'RECEIVED' AND " + 
				"SLSP.STATUS = 'ACTIVE' " + 
				"AND MAP.UPDATE_STATUS_IND = 'I' " + 
				"AND CMS.STATUS = 'ACTIVE' " + 
				"AND CHECKLIST_ITEM.IS_DELETED = 'N' "+
				"AND CHECKLIST.CMS_COLLATERAL_ID = CMS.CMS_COLLATERAL_ID AND "+
				"(CHECKLIST_ITEM.RECEIVED_DATE BETWEEN TO_DATE('"+fromDate+"','DD/MM/YYYY') AND TO_DATE('"+toDate+"','DD/MM/YYYY') ) AND "+
				//"CHECKLIST_ITEM.RECEIVED_DATE >= TO_DATE('23/02/2016','DD/MM/YYYY') AND "+
				"CHECKLIST.CHECKLIST_ID = CHECKLIST_ITEM.CHECKLIST_ID AND " + 
				"CHECKLIST.CMS_LSP_LMT_PROFILE_ID = LMT.CMS_LSP_LMT_PROFILE_ID AND " + 
				"CMS.CMS_COLLATERAL_ID= MAP.CMS_COLLATERAL_ID and " + 
				"APPR.CMS_LSP_APPR_LMTS_ID =MAP.CMS_LSP_APPR_LMTS_ID and  " + 
				"LMT.CMS_LSP_LMT_PROFILE_ID=APPR.CMS_LIMIT_PROFILE_ID and  " + 
				"LMT.LLP_LE_ID=SLSP.LSP_LE_ID and " + 
				"SLSP.LSP_LE_ID         =       CBMC.CUSTOMER_ID " + 
				"AND CBMC.STATUS = 'ACTIVE' " +
//				"SLSP.BANKING_METHOD='"+bankMethod+"'";
				"AND CBMC.CMS_BANKING_METHOD_NAME='"+bankMethod+"'";
		System.out.println("Query = "+sql);
		try {
			reportQueryResult = getJdbcTemplate().query(sql,new RowMapper() {
				@Override
				public String[] mapRow(ResultSet rs, int rownum) throws SQLException {
//					System.out.println("============inside Row mapper=========");
					int columnCount = rs.getMetaData().getColumnCount();
	//				System.out.println("===============Column Count================"+columnCount);
					String[] objArray= new String[columnCount];
						for (int i = 0; i < columnCount; i++) {
							objArray[i]=rs.getString(i+1);
						}
					return objArray;
				}
			});
		} catch (Exception e) {
			throw new ReportException("Error in getAssetRecords.",e); 
		}
		return reportQueryResult;
	}
	
	public int getSecurityIds(String secId) {
		String secIdCount="SELECT count(CMS_COLLATERAL_ID) FROM CMS_SECURITY where CMS_COLLATERAL_ID='"+secId+"'";
		int queryForInt = 0;
		try{
			
			queryForInt = getJdbcTemplate().queryForInt(secIdCount);
		
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in getSecurityIds"+e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
	
		return queryForInt;
	}
	
	public List<Object[]> getMortgageValuationsReportQueryResult( Reports reports,OBFilter filter) {
		System.out.println("Inside getMortgageValuationsReportQueryResult == reportDaoImpl.java");
		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
		Calendar date1 = Calendar.getInstance();
		date1.setTime(DateUtil.convertDate(Locale.getDefault(), filter.getFromDate()));
		date1.add(Calendar.MONTH, 6);
		String toDate = df.format(date1.getTime());
		if(filter.getToDate() != null && !"".equals(filter.getToDate())) {
			toDate = filter.getToDate();
		}
		
		List<Object[]> reportQueryResult= new LinkedList<Object[]>(); 
		List<Object[]> reportQueryResult1= new LinkedList<Object[]>(); 
		/** genrating query from report object, object will be ob class containing filter values*/
		String sql="SELECT DISTINCT " + 
				"TO_CHAR(TO_TIMESTAMP(CP.VALUATION_DATE_V1),'DD/MM/YYYY') AS DATE_OF_VALUATION,  " + 
				"TO_CHAR(TO_TIMESTAMP(CP.VAL_CREATION_DATE_V1),'DD/MM/YYYY') AS VALUATION_CREATION_DATE, " + 
				"CASE CP.VALUATION_DATE_V1 WHEN NULL THEN '1'  " + 
				"   ELSE '1' END AS VALUATION_NUMBER, " + 
				"CVA.VALUATION_AGENCY_NAME  AS VALUATION_COMPANY, " + 
				"CP.TOTAL_PROPERTY_AMOUNT_V1 AS TOTAL_PROPERTY_AMOUNT, " + 
				"TO_CHAR(TO_TIMESTAMP(CP.SALE_PURCHASE_DATE),'DD/MM/YYYY') AS MORTGAGE_CREATION_DATE , " + 
				"CP.PROPERTY_ADDRESS AS PROPERTY_ADDRESS_1, " + 
				"CP.PROPERTY_ADDRESS_2 AS PROPERTY_ADDRESS_2, " + 
				"CP.PROPERTY_ADDRESS_3 AS PROPERTY_ADDRESS_3, " + 
				"CP.PROPERTY_ADDRESS_4 AS PROPERTY_ADDRESS_4, " + 
				"CP.PROPERTY_ADDRESS_5 AS PROPERTY_ADDRESS_5, " + 
				"CP.PROPERTY_ADDRESS_6 AS PROPERTY_ADDRESS_6, " + 
				"sub_profile.LSP_SHORT_NAME AS PARTY_NAME, " + 
				"CP.CMS_COLLATERAL_ID AS SECURITY_ID, " + 
				"CMS.TYPE_NAME AS SECURITY_TYPE, " + 
				"CMS.SUBTYPE_NAME AS SECURITY_SUBTYPE, " + 
				"TO_CHAR(TO_DATE(CMS.DATE_CERSAI_REGISTERATION,'DD-MM-YY'),'DD/MM/YYYY') AS CERSAI_REG_DATE, " + 
				"TO_CHAR(TO_TIMESTAMP(CP.LEGALAUDITDATE),'DD/MM/YYYY') AS LEGAL_AUDIT_DATE, " + 
				"CP.SALE_PURCHASE_VALUE  AS FACILITY_SECURED_AMOUNT, " + 
				"TO_CHAR(CP.TSR_DATE,'DD/MM/YYYY') AS DATE_OF_TSR, " + 
				"CMS.SECURITY_ORGANISATION AS BRANCH_NAME, " + 
				"CP.PROPERTY_ID AS PROPERTY_ID, " + 
				"CCCE1.ENTRY_NAME AS TYPE_OF_CHARGE, " + 
				"TO_CHAR(TO_DATE(CMS.NEXT_VALUATION_DATE,'DD-MM-YY'),'DD/MM/YYYY') AS NEXT_VALUATION_DATE, " + 
				"TO_CHAR(TO_TIMESTAMP(CP.DATE_OFRECEIPTTITLEDEED),'DD/MM/YYYY') AS DATE_OF_RECEIPT_OF_TITLE_DEED, " + 
				"TO_CHAR(TO_TIMESTAMP(CP.INTERVEINGPERISEARCHDATE),'DD/MM/YYYY') AS INTERVEING_PERI_SEARCH_DATE, " + 
				"CMS.CERSAI_ID AS CERSAI_ID, " + 
				"CCCE.ENTRY_NAME AS CATEGORY_OF_LAND_USE, " + 
				"CP.DEVELOPER_NAME_V1 AS PRESENT_OCCUPANT_NAME, " + 
				"CP.LAND_AREA_V1 AS LAND_AREA, " + 
				"CP.IN_SQFT_LAND_AREA_V1 AS LAND_AREA_SQUARE_FEET, " + 
				"CP.BUILTUP_AREA_V1 AS BUILTUP_AREA, " + 
				"CP.IN_SQFT_BUILTUP_AREA_V1 AS BUILTUP_AREA_SQUARE_FEET, " + 
				"CP.PROPERTY_COMPLETION_STATUS_V1 AS PROPERTY_COMPLETION_STATUS, " + 
				"CP.LAND_VALUE_IRB_V1 AS LAND_VALUE, " + 
				"CP.BUILDING_VALUE_IRB_V1 AS BUILDING_VALUE, " + 
				"CP.RECONSTRUCTION_VALUE_IRB_V1 AS RECONSTRUCT_VAL_OF_BUILD, " + 
				"CASE CP.IS_PHY_INSPECT_V1 " + 
				"    WHEN '1' " + 
				"    THEN 'Yes' " + 
				"    ELSE 'No' " + 
				"  END AS PHYSICAL_INSPECTION, " + 
				"CCCE2.ENTRY_NAME AS FREQUENCY, " + 
				"TO_CHAR(TO_TIMESTAMP(CP.LAST_PHY_INSPECT_DATE_V1),'DD/MM/YYYY') AS PHYSICAL_INSPECTION_DONE_ON, " + 
				"TO_CHAR(TO_TIMESTAMP(CP.NEXT_PHY_INSPECT_DATE_V1),'DD/MM/YYYY') AS NXT_PHY_INSPECT_DUE_ON, " + 
				"CP.REMARKS_PROPERTY_V1 AS REMARKS " + 
				"FROM " + 
//				"CMS_PROPERTY CP, " + 
				"CMS_PROPERTY_VAL1 CP,"+
				"SCI_LE_SUB_PROFILE sub_profile, " + 
				"sci_lsp_lmt_profile LMT, " + 
				"sci_lsp_appr_lmts APPR, " + 
				"cms_limit_security_map MAP, " + 
				"cms_security CMS, " + 
				"CMS_VALUATION_AGENCY CVA, " + 
				"COMMON_CODE_CATEGORY_ENTRY CCCE, " + 
				"COMMON_CODE_CATEGORY_ENTRY CCCE1, " + 
				"COMMON_CODE_CATEGORY_ENTRY CCCE2 " +
				"WHERE " + 
				"CP.CMS_COLLATERAL_ID = CMS.CMS_COLLATERAL_ID AND " + 
				"CMS.CMS_COLLATERAL_ID = MAP.cms_collateral_id AND " + 
				"APPR.CMS_LSP_APPR_LMTS_ID =MAP.CMS_LSP_APPR_LMTS_ID AND " + 
				"LMT.CMS_LSP_LMT_PROFILE_ID=APPR.CMS_LIMIT_PROFILE_ID AND " + 
				"LMT.LLP_LE_ID=sub_profile.LSP_LE_ID AND " + 
//				"sub_profile.LSP_LE_ID='"+filter.getPartyId()+"' " + 
//				"AND " + 
				"CP.VALUATION_DATE_V1 BETWEEN '"+filter.getFromDate()+"' AND '"+toDate+"' AND " + 
				"CMS.STATUS = '"+filter.getSecurityStatus()+"' "+
				"AND CP.VALUATOR_COMPANY_V1 = CVA.ID " + 
				"AND CCCE.CATEGORY_CODE = 'LAND_USE_CAT'  " + 
				"AND CCCE1.CATEGORY_CODE = 'TYPE_CHARGE' " + 
				"AND CCCE2.CATEGORY_CODE = 'FREQUENCY' " + 
				"AND CCCE2.ENTRY_CODE = CP.PHY_INSPECT_FREQ_UNIT_V1 " + 
				"AND CMS.CHANGE_TYPE = CCCE1.ENTRY_CODE " + 
				"AND CCCE.ENTRY_CODE = CP.CATEGORY_OF_LAND_USE_V1 " +
				"AND CP.VALUATION_DATE_V1 IN (SELECT MAX(CPV1.VALUATION_DATE_V1) FROM CMS_PROPERTY_VAL1 CPV1, CMS_PROPERTY CP1 " +
				"		WHERE  CPV1.VALUATION_DATE_V1 BETWEEN '"+filter.getFromDate()+"' AND '"+toDate+"' AND CPV1.CMS_COLLATERAL_ID = CP1.CMS_COLLATERAL_ID " +
				"		GROUP BY CPV1.CMS_COLLATERAL_ID) ";

		if(!"ALL".equals(filter.getFilterPartyMode())) {
			sql = sql + " AND sub_profile.LSP_LE_ID='"+filter.getPartyId()+"' ";
		}

		System.out.println("Template generated query sql: "+sql);

		try {
			reportQueryResult = getJdbcTemplate().query(sql,new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rownum) throws SQLException {
//					System.out.println("============inside Row mapper=========");
					int columnCount = rs.getMetaData().getColumnCount();
	//				System.out.println("===============Column Count================"+columnCount);
					Object[] objArray= new Object[columnCount];
						for (int i = 0; i < columnCount; i++) {
							objArray[i]=rs.getObject(i+1);
						}
					return objArray;
				}
			});
		} catch (Exception e) {
			throw new ReportException("Error excecuting query.Please check the report template configuration mortgage sql.",e); 
		}
		
		
		/*String sql1 = "SELECT DISTINCT " + 
				"CP.VALUATION_DATE_V2 AS DATE_OF_VALUATION, " + 
				"CP.VAL_CREATION_DATE_V2 AS VALUATION_CREATION_DATE,  " + 
				"CASE CP.VALUATION_DATE_V2 WHEN NULL THEN '2'  " + 
				"   ELSE '2' END AS VALUATION_NUMBER, " + 
				"CP.VALUATOR_COMPANY_V2 AS VALUATION_COMPANY, " + 
				"CP.TOTAL_PROPERTY_AMOUNT_V2 AS TOTAL_PROPERTY_AMOUNT, " + 
				"CP.SALE_PURCHASE_DATE AS MORTGAGE_CREATION_DATE , " + 
				"CP.PROPERTY_ADDRESS AS PROPERTY_ADDRESS_1, " + 
				"CP.PROPERTY_ADDRESS_2 AS PROPERTY_ADDRESS_2, " + 
				"CP.PROPERTY_ADDRESS_3 AS PROPERTY_ADDRESS_3, " + 
				"CP.PROPERTY_ADDRESS_4 AS PROPERTY_ADDRESS_4, " + 
				"CP.PROPERTY_ADDRESS_5 AS PROPERTY_ADDRESS_5, " + 
				"CP.PROPERTY_ADDRESS_6 AS PROPERTY_ADDRESS_6, " + 
				"sub_profile.LSP_SHORT_NAME AS PARTY_NAME, " + 
				"CP.CMS_COLLATERAL_ID AS SECURITY_ID, " + 
				"CMS.TYPE_NAME AS SECURITY_TYPE, " + 
				"CMS.SUBTYPE_NAME AS SECURITY_SUBTYPE, " + 
				"CMS.DATE_CERSAI_REGISTERATION, " + 
				"CP.LEGALAUDITDATE AS LEGAL_AUDIT_DATE, " + 
				"CMS.CMV AS FACILITY_SECURED_AMOUNT, " + 
				"CP.TSR_DATE AS DATE_OF_TSR, " + 
				"CMS.SECURITY_ORGANISATION AS BRANCH_NAME, " + 
				"CP.PROPERTY_ID AS PROPERTY_ID, " + 
				"CMS.CHANGE_TYPE AS TYPE_OF_CHARGE, " + 
				"CMS.NEXT_VALUATION_DATE AS NEXT_VALUATION_DATE, " + 
				"CP.DATE_OFRECEIPTTITLEDEED AS DATE_OF_RECEIPT_OF_TITLE_DEED, " + 
				"CP.INTERVEINGPERISEARCHDATE AS INTERVEING_PERI_SEARCH_DATE, " + 
				"CMS.CERSAI_ID AS CERSAI_ID, " + 
				"CP.CATEGORY_OF_LAND_USE_V2 AS CATEGORY_OF_LAND_USE, " + 
				"CP.DEVELOPER_NAME_V2 AS PRESENT_OCCUPANT_NAME, " + 
				"CP.LAND_AREA_V2 AS LAND_AREA, " + 
				"CP.IN_SQFT_LAND_AREA_V2 AS LAND_AREA_SQUARE_FEET, " + 
				"CP.BUILTUP_AREA_V2 AS BUILTUP_AREA, " + 
				"CP.IN_SQFT_BUILTUP_AREA_V2 AS BUILTUP_AREA_SQUARE_FEET, " + 
				"CP.PROPERTY_COMPLETION_STATUS_V2 AS PROPERTY_COMPLETION_STATUS, " + 
				"CP.LAND_VALUE_IRB_V2 AS LAND_VALUE, " + 
				"CP.BUILDING_VALUE_IRB_V2 AS BUILDING_VALUE, " + 
				"CP.RECONSTRUCTION_VALUE_IRB_V2 AS RECONSTRUCT_VAL_OF_BUILD, " + 
				"CP.IS_PHY_INSPECT_V2 AS PHYSICAL_INSPECTION, " + 
				"CP.PHY_INSPECT_FREQ_UNIT_V2 AS FREQUENCY, " + 
				"CP.LAST_PHY_INSPECT_DATE_V2 AS PHYSICAL_INSPECTION_DONE_ON, " + 
				"CP.NEXT_PHY_INSPECT_DATE_V2 AS NXT_PHY_INSPECT_DUE_ON, " + 
				"CP.REMARKS_PROPERTY_V2 AS REMARKS " + 
				"FROM " +
				"CMS_PROPERTY CP, " + 
//				"CMS_PROPERTY_VAL2 CP, " + 
				"SCI_LE_SUB_PROFILE sub_profile, " + 
				"sci_lsp_lmt_profile LMT, " + 
				"sci_lsp_appr_lmts APPR, " + 
				"cms_limit_security_map MAP, " + 
				"cms_security CMS " + 
				"WHERE " + 
				"CP.CMS_COLLATERAL_ID = CMS.CMS_COLLATERAL_ID AND " + 
				"CMS.CMS_COLLATERAL_ID = MAP.cms_collateral_id AND " + 
				"APPR.CMS_LSP_APPR_LMTS_ID =MAP.CMS_LSP_APPR_LMTS_ID AND " + 
				"LMT.CMS_LSP_LMT_PROFILE_ID=APPR.CMS_LIMIT_PROFILE_ID AND " + 
				"LMT.LLP_LE_ID=sub_profile.LSP_LE_ID AND " + 
//				"sub_profile.LSP_LE_ID='"+filter.getPartyId()+"' " + 
//				"AND " + 
				"CP.VALUATION_DATE_V2 BETWEEN '"+filter.getFromDate()+"' AND '"+toDate+"' AND " + 
				"CMS.STATUS = '"+filter.getSecurityStatus()+"' ";*/
		
		String sql1 = "SELECT DISTINCT  " + 
				"TO_CHAR(TO_TIMESTAMP(CP.VALUATION_DATE_V2),'DD/MM/YYYY') AS DATE_OF_VALUATION, " + 
				"  TO_CHAR(TO_TIMESTAMP(CP.VAL_CREATION_DATE_V2),'DD/MM/YYYY')            AS VALUATION_CREATION_DATE, " + 
				"  CASE CP.VALUATION_DATE_V2 " + 
				"    WHEN NULL " + 
				"    THEN '2' " + 
				"    ELSE '2' " + 
				"  END                                                                     AS VALUATION_NUMBER, " + 
				"  CVA.VALUATION_AGENCY_NAME                                               AS VALUATION_COMPANY, " + 
				"  CP.TOTAL_PROPERTY_AMOUNT_V2                                             AS TOTAL_PROPERTY_AMOUNT, " + 
				"  TO_CHAR(TO_TIMESTAMP(CP.SALE_PURCHASE_DATE),'DD/MM/YYYY') AS MORTGAGE_CREATION_DATE , " + 
				"  CP.PROPERTY_ADDRESS                                                     AS PROPERTY_ADDRESS_1, " + 
				"  CP.PROPERTY_ADDRESS_2                                                   AS PROPERTY_ADDRESS_2, " + 
				"  CP.PROPERTY_ADDRESS_3                                                   AS PROPERTY_ADDRESS_3, " + 
				"  CP.PROPERTY_ADDRESS_4                                                   AS PROPERTY_ADDRESS_4, " + 
				"  CP.PROPERTY_ADDRESS_5                                                   AS PROPERTY_ADDRESS_5, " + 
				"  CP.PROPERTY_ADDRESS_6                                                   AS PROPERTY_ADDRESS_6, " + 
				"  sub_profile.LSP_SHORT_NAME                                              AS PARTY_NAME, " + 
				"  CP.CMS_COLLATERAL_ID                                                    AS SECURITY_ID, " + 
				"  CMS.TYPE_NAME                                                           AS SECURITY_TYPE, " + 
				"  CMS.SUBTYPE_NAME                                                        AS SECURITY_SUBTYPE, " + 
				"  TO_CHAR(TO_DATE(CMS.DATE_CERSAI_REGISTERATION,'DD-MM-YY'),'DD/MM/YYYY') AS CERSAI_REG_DATE, " + 
				"  TO_CHAR(TO_TIMESTAMP(CP.LEGALAUDITDATE),'DD/MM/YYYY')             AS LEGAL_AUDIT_DATE, " + 
				"  CP.SALE_PURCHASE_VALUE                                            AS FACILITY_SECURED_AMOUNT, " + 
				"  TO_CHAR(CP.TSR_DATE,'DD/MM/YYYY')                   AS DATE_OF_TSR, " + 
				"  CMS.SECURITY_ORGANISATION                                               AS BRANCH_NAME, " + 
				"  CP.PROPERTY_ID                                                          AS PROPERTY_ID, " + 
				"  CCCE1.ENTRY_NAME                                                         AS TYPE_OF_CHARGE, " + 
				"  TO_CHAR(TO_DATE(CMS.NEXT_VALUATION_DATE,'DD-MM-YY'),'DD/MM/YYYY')       AS NEXT_VALUATION_DATE, " + 
				"  TO_CHAR(TO_TIMESTAMP(CP.DATE_OFRECEIPTTITLEDEED),'DD/MM/YYYY')    AS DATE_OF_RECEIPT_OF_TITLE_DEED, " + 
				"  TO_CHAR(TO_TIMESTAMP(CP.INTERVEINGPERISEARCHDATE),'DD/MM/YYYY')   AS INTERVEING_PERI_SEARCH_DATE, " + 
				"  CMS.CERSAI_ID                                                           AS CERSAI_ID, " + 
				"  CCCE.ENTRY_NAME                                                         AS CATEGORY_OF_LAND_USE, " + 
				"  CP.DEVELOPER_NAME_V2                                                    AS PRESENT_OCCUPANT_NAME, " + 
				"  CP.LAND_AREA_V2                                                         AS LAND_AREA, " + 
				"  CP.IN_SQFT_LAND_AREA_V2                                                 AS LAND_AREA_SQUARE_FEET, " + 
				"  CP.BUILTUP_AREA_V2                                                      AS BUILTUP_AREA, " + 
				"  CP.IN_SQFT_BUILTUP_AREA_V2                                              AS BUILTUP_AREA_SQUARE_FEET, " + 
				"  CP.PROPERTY_COMPLETION_STATUS_V2                                        AS PROPERTY_COMPLETION_STATUS, " + 
				"  CP.LAND_VALUE_IRB_V2                                                    AS LAND_VALUE, " + 
				"  CP.BUILDING_VALUE_IRB_V2                                                AS BUILDING_VALUE, " + 
				"  CP.RECONSTRUCTION_VALUE_IRB_V2                                          AS RECONSTRUCT_VAL_OF_BUILD, " + 
				"  CASE CP.IS_PHY_INSPECT_V2 " + 
				"    WHEN '1' " + 
				"    THEN 'Yes' " + 
				"    ELSE 'No' " + 
				"  END                                                     					AS PHYSICAL_INSPECTION, " + 
				"  CCCE2.ENTRY_NAME                                                         AS FREQUENCY, " + 
				"  TO_CHAR(TO_TIMESTAMP(CP.LAST_PHY_INSPECT_DATE_V2),'DD/MM/YYYY')   AS PHYSICAL_INSPECTION_DONE_ON, " + 
				"  TO_CHAR(TO_TIMESTAMP(CP.NEXT_PHY_INSPECT_DATE_V2),'DD/MM/YYYY')   AS NXT_PHY_INSPECT_DUE_ON, " + 
				"  CP.REMARKS_PROPERTY_V2                                                  AS REMARKS " + 
				"FROM  " + 
				"  SCI_LE_SUB_PROFILE sub_profile, " + 
				"  sci_lsp_lmt_profile LMT, " + 
				"  sci_lsp_appr_lmts APPR, " + 
				"  cms_limit_security_map MAP, " + 
				"  cms_security CMS " + 
				"  ,CMS_PROPERTY_VAL2 CP, " +
				"  CMS_VALUATION_AGENCY CVA, " + 
				"  COMMON_CODE_CATEGORY_ENTRY CCCE, " + 
				"  COMMON_CODE_CATEGORY_ENTRY CCCE1, " + 
				"  COMMON_CODE_CATEGORY_ENTRY CCCE2 " +
				"WHERE CP.CMS_COLLATERAL_ID    = CMS.CMS_COLLATERAL_ID " + 
				"AND CMS.CMS_COLLATERAL_ID     = MAP.cms_collateral_id " + 
				"AND APPR.CMS_LSP_APPR_LMTS_ID =MAP.CMS_LSP_APPR_LMTS_ID " + 
				"AND LMT.CMS_LSP_LMT_PROFILE_ID=APPR.CMS_LIMIT_PROFILE_ID " + 
				"AND LMT.LLP_LE_ID             =sub_profile.LSP_LE_ID " + 
//				"CP.VALUATION_DATE_V2 BETWEEN '"+filter.getFromDate()+"' AND '"+toDate+"' AND " + 
				"AND CMS.STATUS = '"+filter.getSecurityStatus()+"' " +
				"AND CP.VALUATION_DATE_V2 BETWEEN '"+filter.getFromDate()+"' AND '"+toDate+"' " +
				"AND CP.VALUATOR_COMPANY_V2 = CVA.ID " + 
				"AND CCCE.CATEGORY_CODE = 'LAND_USE_CAT'  " + 
				"AND CCCE1.CATEGORY_CODE = 'TYPE_CHARGE' " + 
				"AND CCCE2.CATEGORY_CODE = 'FREQUENCY' " + 
				"AND CCCE2.ENTRY_CODE = CP.PHY_INSPECT_FREQ_UNIT_V2 " + 
				"AND CMS.CHANGE_TYPE = CCCE1.ENTRY_CODE " + 
				"AND CCCE.ENTRY_CODE = CP.CATEGORY_OF_LAND_USE_V2 " +
				"AND CP.VALUATION_DATE_V2 IN (SELECT MAX(CPV2.VALUATION_DATE_V2) FROM CMS_PROPERTY_VAL2 CPV2, CMS_PROPERTY CP1 " + 
				"WHERE  CPV2.VALUATION_DATE_V2 BETWEEN '"+filter.getFromDate()+"' AND '"+toDate+"' AND CPV2.CMS_COLLATERAL_ID = CP1.CMS_COLLATERAL_ID " + 
				"GROUP BY CPV2.CMS_COLLATERAL_ID) ";
		
		if(!"ALL".equals(filter.getFilterPartyMode())) {
			sql1 = sql1 + " AND sub_profile.LSP_LE_ID='"+filter.getPartyId()+"' ";
		}
		
		System.out.println("Template generated query sql1: "+sql1);
		
		try {
			 reportQueryResult1 = getJdbcTemplate().query(sql1,new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rownum) throws SQLException {
//					System.out.println("============inside Row mapper=========");
					int columnCount = rs.getMetaData().getColumnCount();
	//				System.out.println("===============Column Count================"+columnCount);
					Object[] objArray= new Object[columnCount];
						for (int i = 0; i < columnCount; i++) {
							objArray[i]=rs.getObject(i+1);
						}
					return objArray;
				}
			});
			 if(!(reportQueryResult1).isEmpty()) {
				 for(int i =0; i<reportQueryResult1.size();i++) {
					 reportQueryResult.add(reportQueryResult1.get(i));
				 }
			 
			 }
		} catch (Exception e) {
			throw new ReportException("Error excecuting query.Please check the report template configuration sql 1.",e); 
		}
		
		/*String sql2 = "SELECT DISTINCT " + 
				"CP.VALUATION_DATE_V3 AS DATE_OF_VALUATION, " + 
				"CP.VAL_CREATION_DATE_V3 AS VALUATION_CREATION_DATE, " + 
				"CASE CP.VALUATION_DATE_V3 WHEN NULL THEN '3'  " + 
				"   ELSE '3' END AS VALUATION_NUMBER, " + 
				"CP.VALUATOR_COMPANY_V3 AS VALUATION_COMPANY, " + 
				"CP.TOTAL_PROPERTY_AMOUNT_V3 AS TOTAL_PROPERTY_AMOUNT, " + 
				"CP.SALE_PURCHASE_DATE AS MORTGAGE_CREATION_DATE , " + 
				"CP.PROPERTY_ADDRESS AS PROPERTY_ADDRESS_1, " + 
				"CP.PROPERTY_ADDRESS_2 AS PROPERTY_ADDRESS_2, " + 
				"CP.PROPERTY_ADDRESS_3 AS PROPERTY_ADDRESS_3, " + 
				"CP.PROPERTY_ADDRESS_4 AS PROPERTY_ADDRESS_4, " + 
				"CP.PROPERTY_ADDRESS_5 AS PROPERTY_ADDRESS_5, " + 
				"CP.PROPERTY_ADDRESS_6 AS PROPERTY_ADDRESS_6, " + 
				"sub_profile.LSP_SHORT_NAME AS PARTY_NAME, " + 
				"CP.CMS_COLLATERAL_ID AS SECURITY_ID, " + 
				"CMS.TYPE_NAME AS SECURITY_TYPE, " + 
				"CMS.SUBTYPE_NAME AS SECURITY_SUBTYPE, " + 
				"CMS.DATE_CERSAI_REGISTERATION, " + 
				"CP.LEGALAUDITDATE AS LEGAL_AUDIT_DATE, " + 
				"CMS.CMV AS FACILITY_SECURED_AMOUNT, " + 
				"CP.TSR_DATE AS DATE_OF_TSR, " + 
				"CMS.SECURITY_ORGANISATION AS BRANCH_NAME, " + 
				"CP.PROPERTY_ID AS PROPERTY_ID, " + 
				"CMS.CHANGE_TYPE AS TYPE_OF_CHARGE, " + 
				"CMS.NEXT_VALUATION_DATE AS NEXT_VALUATION_DATE, " + 
				"CP.DATE_OFRECEIPTTITLEDEED AS DATE_OF_RECEIPT_OF_TITLE_DEED, " + 
				"CP.INTERVEINGPERISEARCHDATE AS INTERVEING_PERI_SEARCH_DATE, " + 
				"CMS.CERSAI_ID AS CERSAI_ID, " + 
				"CP.CATEGORY_OF_LAND_USE_V3 AS CATEGORY_OF_LAND_USE, " + 
				"CP.DEVELOPER_NAME_V3 AS PRESENT_OCCUPANT_NAME, " + 
				"CP.LAND_AREA_V3 AS LAND_AREA, " + 
				"CP.IN_SQFT_LAND_AREA_V3 AS LAND_AREA_SQUARE_FEET, " + 
				"CP.BUILTUP_AREA_V3 AS BUILTUP_AREA, " + 
				"CP.IN_SQFT_BUILTUP_AREA_V3 AS BUILTUP_AREA_SQUARE_FEET, " + 
				"CP.PROPERTY_COMPLETION_STATUS_V3 AS PROPERTY_COMPLETION_STATUS, " + 
				"CP.LAND_VALUE_IRB_V3 AS LAND_VALUE, " + 
				"CP.BUILDING_VALUE_IRB_V3 AS BUILDING_VALUE, " + 
				"CP.RECONSTRUCTION_VALUE_IRB_V3 AS RECONSTRUCT_VAL_OF_BUILD, " + 
				"CP.IS_PHY_INSPECT_V3 AS PHYSICAL_INSPECTION, " + 
				"CP.PHY_INSPECT_FREQ_UNIT_V3 AS FREQUENCY, " + 
				"CP.LAST_PHY_INSPECT_DATE_V3 AS PHYSICAL_INSPECTION_DONE_ON, " + 
				"CP.NEXT_PHY_INSPECT_DATE_V3 AS NXT_PHY_INSPECT_DUE_ON, " + 
				"CP.REMARKS_PROPERTY_V3 AS REMARKS " + 
				"FROM  " + 
				"CMS_PROPERTY CP, " +
//				"CMS_PROPERTY_VAL3 CP, " +
				"SCI_LE_SUB_PROFILE sub_profile, " + 
				"sci_lsp_lmt_profile LMT, " + 
				"sci_lsp_appr_lmts APPR, " + 
				"cms_limit_security_map MAP, " + 
				"cms_security CMS " + 
				"WHERE " + 
				"CP.CMS_COLLATERAL_ID = CMS.CMS_COLLATERAL_ID AND " + 
				"CMS.CMS_COLLATERAL_ID = MAP.cms_collateral_id AND " + 
				"APPR.CMS_LSP_APPR_LMTS_ID =MAP.CMS_LSP_APPR_LMTS_ID AND " + 
				"LMT.CMS_LSP_LMT_PROFILE_ID=APPR.CMS_LIMIT_PROFILE_ID AND " + 
				"LMT.LLP_LE_ID=sub_profile.LSP_LE_ID AND " + 
//				"sub_profile.LSP_LE_ID='"+filter.getPartyId()+"' " + 
//				"AND " + 
				"CP.VALUATION_DATE_V3 BETWEEN '"+filter.getFromDate()+"' AND '"+toDate+"' AND " + 
				"CMS.STATUS = '"+filter.getSecurityStatus()+"' ";*/
		
		String sql2 = "SELECT DISTINCT  " + 
				"TO_CHAR(TO_TIMESTAMP(CP.VALUATION_DATE_V3),'DD/MM/YYYY') AS DATE_OF_VALUATION, " + 
				"  TO_CHAR(TO_TIMESTAMP(CP.VAL_CREATION_DATE_V3),'DD/MM/YYYY')            AS VALUATION_CREATION_DATE, " + 
				"  CASE CP.VALUATION_DATE_V3 " + 
				"    WHEN NULL " + 
				"    THEN '3' " + 
				"    ELSE '3' " + 
				"  END                                                                     AS VALUATION_NUMBER, " + 
				"  CVA.VALUATION_AGENCY_NAME                                               AS VALUATION_COMPANY, " + 
				"  CP.TOTAL_PROPERTY_AMOUNT_V3                                             AS TOTAL_PROPERTY_AMOUNT, " + 
				"  TO_CHAR(TO_TIMESTAMP(CP.SALE_PURCHASE_DATE),'DD/MM/YYYY') AS MORTGAGE_CREATION_DATE , " + 
				"  CP.PROPERTY_ADDRESS                                                     AS PROPERTY_ADDRESS_1, " + 
				"  CP.PROPERTY_ADDRESS_2                                                   AS PROPERTY_ADDRESS_2, " + 
				"  CP.PROPERTY_ADDRESS_3                                                   AS PROPERTY_ADDRESS_3, " + 
				"  CP.PROPERTY_ADDRESS_4                                                   AS PROPERTY_ADDRESS_4, " + 
				"  CP.PROPERTY_ADDRESS_5                                                   AS PROPERTY_ADDRESS_5, " + 
				"  CP.PROPERTY_ADDRESS_6                                                   AS PROPERTY_ADDRESS_6, " + 
				"  sub_profile.LSP_SHORT_NAME                                              AS PARTY_NAME, " + 
				"  CP.CMS_COLLATERAL_ID                                                    AS SECURITY_ID, " + 
				"  CMS.TYPE_NAME                                                           AS SECURITY_TYPE, " + 
				"  CMS.SUBTYPE_NAME                                                        AS SECURITY_SUBTYPE, " + 
				"  TO_CHAR(TO_DATE(CMS.DATE_CERSAI_REGISTERATION,'DD-MM-YY'),'DD/MM/YYYY') AS CERSAI_REG_DATE, " + 
				"  TO_CHAR(TO_TIMESTAMP(CP.LEGALAUDITDATE),'DD/MM/YYYY')             AS LEGAL_AUDIT_DATE, " + 
				"  CP.SALE_PURCHASE_VALUE                                                   AS FACILITY_SECURED_AMOUNT, " + 
				"  TO_CHAR(CP.TSR_DATE,'DD/MM/YYYY')                   AS DATE_OF_TSR, " + 
				"  CMS.SECURITY_ORGANISATION                                               AS BRANCH_NAME, " + 
				"  CP.PROPERTY_ID                                                          AS PROPERTY_ID, " + 
				"  CCCE1.ENTRY_NAME                                                         AS TYPE_OF_CHARGE, " + 
				"  TO_CHAR(TO_DATE(CMS.NEXT_VALUATION_DATE,'DD-MM-YY'),'DD/MM/YYYY')       AS NEXT_VALUATION_DATE, " + 
				"  TO_CHAR(TO_TIMESTAMP(CP.DATE_OFRECEIPTTITLEDEED),'DD/MM/YYYY')    AS DATE_OF_RECEIPT_OF_TITLE_DEED, " + 
				"  TO_CHAR(TO_TIMESTAMP(CP.INTERVEINGPERISEARCHDATE),'DD/MM/YYYY')   AS INTERVEING_PERI_SEARCH_DATE, " + 
				"  CMS.CERSAI_ID                                                           AS CERSAI_ID, " + 
				"  CCCE.ENTRY_NAME                                                         AS CATEGORY_OF_LAND_USE, " + 
				"  CP.DEVELOPER_NAME_V3                                                    AS PRESENT_OCCUPANT_NAME, " + 
				"  CP.LAND_AREA_V3                                                         AS LAND_AREA, " + 
				"  CP.IN_SQFT_LAND_AREA_V3                                                 AS LAND_AREA_SQUARE_FEET, " + 
				"  CP.BUILTUP_AREA_V3                                                      AS BUILTUP_AREA, " + 
				"  CP.IN_SQFT_BUILTUP_AREA_V3                                              AS BUILTUP_AREA_SQUARE_FEET, " + 
				"  CP.PROPERTY_COMPLETION_STATUS_V3                                        AS PROPERTY_COMPLETION_STATUS, " + 
				"  CP.LAND_VALUE_IRB_V3                                                    AS LAND_VALUE, " + 
				"  CP.BUILDING_VALUE_IRB_V3                                                AS BUILDING_VALUE, " + 
				"  CP.RECONSTRUCTION_VALUE_IRB_V3                                          AS RECONSTRUCT_VAL_OF_BUILD, " + 
				"  CASE CP.IS_PHY_INSPECT_V3 " + 
				"    WHEN '1' " + 
				"    THEN 'Yes' " + 
				"    ELSE 'No' " + 
				"  END                                                     AS PHYSICAL_INSPECTION, " + 
				"  CCCE2.ENTRY_NAME                                                     AS FREQUENCY, " + 
				"  TO_CHAR(TO_TIMESTAMP(CP.LAST_PHY_INSPECT_DATE_V3),'DD/MM/YYYY')   AS PHYSICAL_INSPECTION_DONE_ON, " + 
				"  TO_CHAR(TO_TIMESTAMP(CP.NEXT_PHY_INSPECT_DATE_V3),'DD/MM/YYYY')   AS NXT_PHY_INSPECT_DUE_ON, " + 
				"  CP.REMARKS_PROPERTY_V3                                                  AS REMARKS " + 
				"FROM  " + 
				"  SCI_LE_SUB_PROFILE sub_profile, " + 
				"  sci_lsp_lmt_profile LMT, " + 
				"  sci_lsp_appr_lmts APPR, " + 
				"  cms_limit_security_map MAP, " + 
				"  cms_security CMS " + 
				"  ,CMS_PROPERTY_VAL3 CP, " +
				"  CMS_VALUATION_AGENCY CVA, " + 
				"  COMMON_CODE_CATEGORY_ENTRY CCCE, " + 
				"  COMMON_CODE_CATEGORY_ENTRY CCCE1, " + 
				"  COMMON_CODE_CATEGORY_ENTRY CCCE2 " +
				"WHERE CP.CMS_COLLATERAL_ID    = CMS.CMS_COLLATERAL_ID " + 
				"AND CMS.CMS_COLLATERAL_ID     = MAP.cms_collateral_id " + 
				"AND APPR.CMS_LSP_APPR_LMTS_ID =MAP.CMS_LSP_APPR_LMTS_ID " + 
				"AND LMT.CMS_LSP_LMT_PROFILE_ID=APPR.CMS_LIMIT_PROFILE_ID " + 
				"AND LMT.LLP_LE_ID             =sub_profile.LSP_LE_ID " + 
//				"CP.VALUATION_DATE_V3 BETWEEN '"+filter.getFromDate()+"' AND '"+toDate+"' AND " + 
				"AND CMS.STATUS = '"+filter.getSecurityStatus()+"' " +
				"AND CP.VALUATION_DATE_V3 BETWEEN '"+filter.getFromDate()+"' AND '"+toDate+"' " + 
				"AND CP.VALUATOR_COMPANY_V3 = CVA.ID " + 
				"AND CCCE.CATEGORY_CODE = 'LAND_USE_CAT'  " + 
				"AND CCCE1.CATEGORY_CODE = 'TYPE_CHARGE' " + 
				"AND CCCE2.CATEGORY_CODE = 'FREQUENCY' " + 
				"AND CCCE2.ENTRY_CODE = CP.PHY_INSPECT_FREQ_UNIT_V3 " + 
				"AND CMS.CHANGE_TYPE = CCCE1.ENTRY_CODE " + 
				"AND CCCE.ENTRY_CODE = CP.CATEGORY_OF_LAND_USE_V3 " +
				"AND CP.VALUATION_DATE_V3 IN (SELECT MAX(CPV3.VALUATION_DATE_V3) FROM CMS_PROPERTY_VAL3 CPV3, CMS_PROPERTY CP1 " + 
				"WHERE  CPV3.VALUATION_DATE_V3 BETWEEN '"+filter.getFromDate()+"' AND '"+toDate+"' AND CPV3.CMS_COLLATERAL_ID = CP1.CMS_COLLATERAL_ID " + 
				"GROUP BY CPV3.CMS_COLLATERAL_ID) ";
		
		if(!"ALL".equals(filter.getFilterPartyMode())) {
			sql2 = sql2 + " AND sub_profile.LSP_LE_ID='"+filter.getPartyId()+"' ";
		}
		
		System.out.println("Template generated query sql2: "+sql2);

		try {
			reportQueryResult1 = getJdbcTemplate().query(sql2,new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rownum) throws SQLException {
//					System.out.println("============inside Row mapper=========");
					int columnCount = rs.getMetaData().getColumnCount();
	//				System.out.println("===============Column Count================"+columnCount);
					Object[] objArray= new Object[columnCount];
						for (int i = 0; i < columnCount; i++) {
							objArray[i]=rs.getObject(i+1);
						}
					return objArray;
				}
			});
			if(!(reportQueryResult1).isEmpty()) {
				 for(int i =0; i<reportQueryResult1.size();i++) {
					 reportQueryResult.add(reportQueryResult1.get(i));
				 }
			 
			 }
		} catch (Exception e) {
			throw new ReportException("Error excecuting query.Please check the report template configuration sql 2.",e); 
		}
		
		DefaultLogger.debug(this, "=========Out getReportQueryResult()=======reportQueryResult.size(): "+reportQueryResult.size());
		return reportQueryResult;
	}
	
//	TODO :Logic of genrating query will change(for where claue)
	/**
	 * genrating query from report object
	 */
	private String generateQuery(Reports reports,OBFilter filter) {

		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String report = bundle.getString("report.leadnodal.banking.report");
		StringBuffer query = new StringBuffer();
		StringBuffer query1 = new StringBuffer();
		StringBuffer query2 = new StringBuffer();
		query.append(reports.getReport().getQuery());
		
		if ("RPT0085".equals(filter.getReportId())) {
			
			query= new StringBuffer();
			query.append("select PARTY_NAME, PARTY_ID,PAN_NO,FACILITY_CATEGORY,FACILITY_NAME,SYSTEM_ID,"
					+ "SERIAL_NO,RELEASED_AMOUNT,LINE_RELEASED_AMOUNT,"
					+ "SECURITY_TYPE,SECURITY_SUBTYPE,CMS_COLLATERAL_ID,DUE_DATE,"
					+ "DRAWING_POWER,RELATIONSHIP_MGR_NAME,SUPERVISOR_NAME,"
					+ " '"+filter.getEventOrCriteria()+"' as EVENT_CRITERIA,"
					+ "MAKER,APPROVED_BY from STOCK_STMNT_REPORT_RM_MV ");

			
		}
		
		if(reports.getReport().getWhereClause()!=null){
			//creating Criteria
			String criteria = createCriteria(reports,filter);
			if(criteria!=null && !("".equals(criteria.trim()))){
				query.append(" WHERE ");
				query.append(criteria);
			}


			if ("RPT0030".equals(filter.getReportId())) {

				if (report.equalsIgnoreCase("true")) {
					query.append("UNION "
							+ "SELECT DISTINCT    "
							+ "  sub_profile.lsp_le_id  AS Party_Id ,"
							+ "  sub_profile.lsp_short_name           AS party_name ,"
							+ "  sub_profile.mpbf                     AS mpbf ,"
							+ "  sub_profile.total_funded_limit       AS totalfundamount ,"
							+ " sub_profile.funded_share_percent     AS hdfcfundshare ,"
							+ "  sub_profile.funded_share_limit       AS hdfcfundamount ,"
							+ "  sub_profile.total_non_funded_limit   AS totalnonfunded , "
							+ " sub_profile.non_funded_share_percent AS hdfcnonfundedshare , "
							+ " sub_profile.non_funded_share_limit   AS hdfcnonfundedamount , "
							+ "(SELECT get_banking_method(sub_profile.LSP_LE_ID) AS BANKING_METHOD   FROM CMS_BANKING_METHOD_CUST   WHERE ROWNUM = 1  ) AS bankmethod,"
							+ "  '' AS ifsc, "
							+ " SYSTEMBANKBRANCH.SYSTEM_BANK_NAME AS BANKNAME, "
							+ " CASE  "
							+ "  WHEN BANKING.CMS_LE_LEAD ='Y'  "
							+ " THEN 'YES'   "
							+ "ELSE "
							+ "'NO'  END AS LEAD, "
							+ "  CASE    "
							+ " WHEN BANKING.CMS_LE_NODAL ='Y' "
							+ "    THEN 'YES'     "
							+ "ELSE "
							+ "'NO'   "
							+ "END      "
							+ "   AS NODAL,"
							+ "   sub_profile.MULTBNK_FUNDBSE_LEADBNK_PER    AS MULTBNK_FUNDBSE_LEADBNK_PER, "
							+ "  sub_profile.MULTBNK_NONFUNDBSE_LEADBNK_PER AS MULTBNK_NONFUNDBSE_LEADBNK_PER,"
							+ "   sub_profile.MULTBNK_FUNDBSE_HDFCBNK_PER    AS MULTBNK_FUNDBSE_HDFCBNK_PER,"
							+ "   sub_profile.MULTBNK_NONFUNDBSE_HDFCBNK_PER AS MULTBNK_NONFUNDBSE_HDFCBNK_PER,"
							+ "   sub_profile.CONSBNK_FUNDBSE_LEADBNK_PER    AS CONSBNK_FUNDBSE_LEADBNK_PER,"
							+ "  sub_profile.CONSBNK_NONFUNDBSE_LEADBNK_PER AS CONSBNK_NONFUNDBSE_LEADBNK_PER,"
							+ "   sub_profile.CONSBNK_FUNDBSE_HDFCBNK_PER    AS CONSBNK_FUNDBSE_HDFCBNK_PER,"
							+ "  sub_profile.CONSBNK_NONFUNDBSE_HDFCBNK_PER AS CONSBNK_NONFUNDBSE_HDFCBNK_PER,"
							+ " (select rm.RM_MGR_NAME from CMS_RELATIONSHIP_MGR rm where  rm.ID= sub_profile.RELATION_MGR) RM_MGR_NAME,  "
							+ "  (select reg.region_name from CMS_REGION reg     where  reg.id=(select ra.lra_region from SCI_LE_REG_ADDR ra "
							+ "				where sub_profile.CMS_LE_MAIN_PROFILE_ID = ra.CMS_LE_MAIN_PROFILE_ID    "
							+ "              AND ra.LRA_TYPE_VALUE        = 'CORPORATE')) Region, "
							+ " (SELECT entry_name FROM  common_code_category_entry WHERE category_code = 'HDFC_SEGMENT'  "
							+ " 	 and entry_code= sub_profile.lsp_sgmnt_code_value) AS segment, "
							+ " (sub_profile.total_funded_limit + sub_profile.total_non_funded_limit) AS totalAmount,"
							+ "   banking.status STATUS"
				
							+ " FROM  SCI_LE_SUB_PROFILE sub_profile, "
							+ "   SCI_LE_BANKING_METHOD banking, CMS_BANKING_METHOD_CUST banking_cust, "
							+ "   CMS_SYSTEM_BANK SYSTEMBANKBRANCH   "
							+ "WHERE "
							+ " exists (select 1 from SCI_LE_REG_ADDR ra where sub_profile.CMS_LE_MAIN_PROFILE_ID = ra.CMS_LE_MAIN_PROFILE_ID"
							+ "			AND ra.LRA_TYPE_VALUE                   = 'CORPORATE'       ) "
							+ "AND SUB_PROFILE.status                      != 'INACTIVE'   "
							+ "AND banking.CMS_LE_MAIN_PROFILE_ID           = sub_profile.CMS_LE_MAIN_PROFILE_ID "
							+ "AND banking_cust.CMS_LE_SUB_PROFILE_ID       =sub_profile.CMS_LE_SUB_PROFILE_ID "
							+ " AND banking_cust.Status  ='ACTIVE'  "
						//	+ "And banking.status                           is not null "
							+ " AND banking_cust.CMS_BANKING_METHOD_NAME!    ='SOLE' "
							+ "and BANKING.CMS_LE_BANK_ID                    = SYSTEMBANKBRANCH.id  "
							+ "AND BANKING.CMS_LE_BANK_TYPE = 'S' "
							
							
							
							);
					
					query1.append("UNION  "
							+ "SELECT   DISTINCT  "
							+ "    sub_profile.lsp_le_id  AS Party_Id ,"
							+ "  sub_profile.lsp_short_name           AS party_name ,"
							+ "  sub_profile.mpbf                     AS mpbf ,"
							+ "  sub_profile.total_funded_limit       AS totalfundamount ,"
							+ "  sub_profile.funded_share_percent     AS hdfcfundshare , "
							+ " sub_profile.funded_share_limit       AS hdfcfundamount , "
							+ " sub_profile.total_non_funded_limit   AS totalnonfunded ,  "
							+ "sub_profile.non_funded_share_percent AS hdfcnonfundedshare , "
							+ "sub_profile.non_funded_share_limit   AS hdfcnonfundedamount ,  "
							+ "(SELECT get_banking_method(sub_profile.LSP_LE_ID) AS BANKING_METHOD "
							+ "  FROM CMS_BANKING_METHOD_CUST  WHERE ROWNUM = 1   ) AS bankmethod,  "
							+ "   OTHER_BANK.BANK_CODE AS ifsc,  OTHER_BANK.BANK_NAME    ||'-'    ||OTHERBANKBRANCH.BRANCH_NAME AS BANKNAME,"
							+ "  CASE  "
							+ "   WHEN BANKING.CMS_LE_LEAD ='Y' "
							+ "    THEN 'YES'    "
							+ " ELSE 'NO' "
							+ "  END AS LEAD, "
							+ "  CASE  "
							+ " WHEN BANKING.CMS_LE_NODAL ='Y' "
							+ "    THEN 'YES' "
							+ "    ELSE 'NO'   "
							+ "END     AS NODAL, "
							+ " sub_profile.MULTBNK_FUNDBSE_LEADBNK_PER    AS MULTBNK_FUNDBSE_LEADBNK_PER, "
							+ "  sub_profile.MULTBNK_NONFUNDBSE_LEADBNK_PER AS MULTBNK_NONFUNDBSE_LEADBNK_PER, "
							+ "  sub_profile.MULTBNK_FUNDBSE_HDFCBNK_PER    AS MULTBNK_FUNDBSE_HDFCBNK_PER, "
							+ "  sub_profile.MULTBNK_NONFUNDBSE_HDFCBNK_PER AS MULTBNK_NONFUNDBSE_HDFCBNK_PER,"
							+ "   sub_profile.CONSBNK_FUNDBSE_LEADBNK_PER    AS CONSBNK_FUNDBSE_LEADBNK_PER, "
							+ " sub_profile.CONSBNK_NONFUNDBSE_LEADBNK_PER AS CONSBNK_NONFUNDBSE_LEADBNK_PER,"
							+ "   sub_profile.CONSBNK_FUNDBSE_HDFCBNK_PER    AS CONSBNK_FUNDBSE_HDFCBNK_PER,"
							+ "   sub_profile.CONSBNK_NONFUNDBSE_HDFCBNK_PER AS CONSBNK_NONFUNDBSE_HDFCBNK_PER,"
							+ "   (select rm.RM_MGR_NAME from CMS_RELATIONSHIP_MGR rm where  rm.ID= sub_profile.RELATION_MGR) RM_MGR_NAME,"
							+ "  reg.region_name          AS Region, "
							+ "  (SELECT entry_name FROM  common_code_category_entry WHERE category_code = 'HDFC_SEGMENT'  "
							+ "       and entry_code= sub_profile.lsp_sgmnt_code_value) AS segment, "
							+ "  (sub_profile.total_funded_limit + sub_profile.total_non_funded_limit) AS totalAmount, "
							+ "  banking.status STATUS   "
							+ "FROM  SCI_LE_SUB_PROFILE sub_profile, "
							+ "   SCI_LE_REG_ADDR ra ,  "
							+ "  CMS_REGION reg,   "
							+ " CMS_OTHER_BANK OTHER_BANK, "
							+ "   CMS_OTHER_BANK_BRANCH OTHERBANKBRANCH,   "
							+ " SCI_LE_BANKING_METHOD banking, "
							+ " CMS_BANKING_METHOD_CUST banking_cust  "
							+ "WHERE "
							+ "(sub_profile.CMS_LE_MAIN_PROFILE_ID    = ra.CMS_LE_MAIN_PROFILE_ID (+) "
							+ "AND ra.LRA_TYPE_VALUE                        = 'CORPORATE') "
							+ "AND ra.lra_region                            = reg.id  "
							+ "AND SUB_PROFILE.status                      != 'INACTIVE'  "
							+ "AND banking.CMS_LE_MAIN_PROFILE_ID           = sub_profile.CMS_LE_MAIN_PROFILE_ID"
							+ " AND banking_cust.CMS_LE_SUB_PROFILE_ID       =sub_profile.CMS_LE_SUB_PROFILE_ID "
							+ "AND banking_cust.Status                      ='ACTIVE' "
							+ " AND banking_cust.CMS_BANKING_METHOD_NAME!    ='SOLE' "
							+ "and BANKING.CMS_LE_BANK_ID                   = OTHERBANKBRANCH.ID "
							+ "AND OTHER_BANK.ID                            = OTHERBANKBRANCH.OTHER_BANK_CODE  "
							+ "AND BANKING.CMS_LE_BANK_TYPE = 'O' "
						
					);
					
					query2.append("UNION  	"
							+ " SELECT     DISTINCT  "
							+ "   sub_profile.lsp_le_id  AS Party_Id ,"
							+ " sub_profile.lsp_short_name           AS party_name , "
							+ " sub_profile.mpbf                     AS mpbf , "
							+ " sub_profile.total_funded_limit       AS totalfundamount ,"
							+ "  sub_profile.funded_share_percent     AS hdfcfundshare ,"
							+ "  sub_profile.funded_share_limit       AS hdfcfundamount , "
							+ " sub_profile.total_non_funded_limit   AS totalnonfunded , "
							+ " sub_profile.non_funded_share_percent AS hdfcnonfundedshare , "
							+ " sub_profile.non_funded_share_limit   AS hdfcnonfundedamount , "
							+ " (SELECT get_banking_method(sub_profile.LSP_LE_ID) AS BANKING_METHOD  "
							+ " FROM CMS_BANKING_METHOD_CUST   WHERE ROWNUM = 1   ) AS bankmethod,   "
							+ "IFSCBANKBRANCH.IFSC_CODE  AS ifsc,"
							+ "    IFSCBANKBRANCH.BANK_NAME AS BANKNAME, "
							+ "  CASE    "
							+ " WHEN IFSCBANKBRANCH.LEAD ='Y'    "
							+ " THEN 'YES'    "
							+ "ELSE 'NO' "
							+ "  END AS LEAD, "
							+ "  CASE    "
							+ " WHEN IFSCBANKBRANCH.NODAL ='Y'  "
							+ "   THEN 'YES'    "
							+ " ELSE 'NO'  "
							+ " END         AS NODAL, "
							+ "  sub_profile.MULTBNK_FUNDBSE_LEADBNK_PER    AS MULTBNK_FUNDBSE_LEADBNK_PER,"
							+ "   sub_profile.MULTBNK_NONFUNDBSE_LEADBNK_PER AS MULTBNK_NONFUNDBSE_LEADBNK_PER,  "
							+ "  sub_profile.MULTBNK_FUNDBSE_HDFCBNK_PER    AS MULTBNK_FUNDBSE_HDFCBNK_PER, "
							+ "  sub_profile.MULTBNK_NONFUNDBSE_HDFCBNK_PER AS MULTBNK_NONFUNDBSE_HDFCBNK_PER,  "
							+ " sub_profile.CONSBNK_FUNDBSE_LEADBNK_PER    AS CONSBNK_FUNDBSE_LEADBNK_PER, "
							+ " sub_profile.CONSBNK_NONFUNDBSE_LEADBNK_PER AS CONSBNK_NONFUNDBSE_LEADBNK_PER,  "
							+ " sub_profile.CONSBNK_FUNDBSE_HDFCBNK_PER    AS CONSBNK_FUNDBSE_HDFCBNK_PER, "
							+ " sub_profile.CONSBNK_NONFUNDBSE_HDFCBNK_PER AS CONSBNK_NONFUNDBSE_HDFCBNK_PER,  "
							+ "  (select rm.RM_MGR_NAME from CMS_RELATIONSHIP_MGR rm where  rm.ID= sub_profile.RELATION_MGR) RM_MGR_NAME, "
							+ " reg.region_name                                                       AS Region, "
							+ "   (SELECT entry_name FROM  common_code_category_entry WHERE category_code = 'HDFC_SEGMENT'     and entry_code= sub_profile.lsp_sgmnt_code_value) AS segment, "
							+ "  (sub_profile.total_funded_limit + sub_profile.total_non_funded_limit) AS totalAmount, "
							+ "  IFSCBANKBRANCH.status STATUS "
							+ "FROM  SCI_LE_SUB_PROFILE sub_profile, "
							+ "   SCI_LE_REG_ADDR ra ,    "
							+ "CMS_REGION reg,  "
							+ "   CMS_BANKING_METHOD_CUST banking_cust,   "
							+ "  CMS_IFSC_CODE IFSCBANKBRANCH "
							+ "  WHERE (sub_profile.CMS_LE_MAIN_PROFILE_ID    = ra.CMS_LE_MAIN_PROFILE_ID (+) "
							+ "AND ra.LRA_TYPE_VALUE                        = 'CORPORATE') "
							+ " AND ra.lra_region                            = reg.id  "
							+ "AND SUB_PROFILE.status                      != 'INACTIVE' "
							+ "AND banking_cust.CMS_LE_SUB_PROFILE_ID       =sub_profile.CMS_LE_SUB_PROFILE_ID  "
							+ "AND banking_cust.Status                      ='ACTIVE' "
							+ "AND banking_cust.CMS_BANKING_METHOD_NAME !='SOLE'  "
							+ " AND nvl(IFSCBANKBRANCH.STATUS,'ACTIVE')!= 'INACTIVE' "
							+ "AND IFSCBANKBRANCH.CMS_LE_MAIN_PROFILE_ID = sub_profile.CMS_LE_SUB_PROFILE_ID"
							+ "  AND IFSCBANKBRANCH.IFSC_CODE!='1'  "
					
							);
				} else {
					query.append("UNION \n"
							+ "                          SELECT distinct sub_profile.lsp_short_name AS party_name ,\n"
							+ "                          sub_profile.mpbf AS mpbf ,\n"
							+ "                          sub_profile.total_funded_limit AS totalfundamount ,\n"
							+ "                          sub_profile.funded_share_percent AS hdfcfundshare ,\n"
							+ "                          sub_profile.funded_share_limit AS hdfcfundamount ,\n"
							+ "                          sub_profile.total_non_funded_limit AS totalnonfunded ,\n"
							+ "                          sub_profile.non_funded_share_percent AS hdfcnonfundedshare ,\n"
							+ "                          sub_profile.non_funded_share_limit AS hdfcnonfundedamount ,\n"
							+ " ( SELECT get_banking_method(sub_profile.LSP_LE_ID) AS BANKING_METHOD \r\n"
							+ "     			FROM   CMS_BANKING_METHOD_CUST WHERE ROWNUM = 1)  As bankmethod, \n"
							+ "                          IFSCBANKBRANCH.BANK_NAME\n"
							+ "                                ||'-'\n"
							+ "                                ||IFSCBANKBRANCH.BRANCH_NAME\n"
							+ "                          AS BANKNAME,\n" +
							/*
							 * "                          CASE\n" +
							 * "                                WHEN IFSCBANKBRANCH.lead ='Y'\n" +
							 * "                                OR IFSCBANKBRANCH.lead ='Y'\n" +
							 * "                                THEN 'Yes'\n" +
							 * "                                ELSE 'No'\n" +
							 * "                          END AS \"Lead/Nodal\", \n" +
							 */
							"							IFSCBANKBRANCH.LEAD AS LEAD, "
							+ "							IFSCBANKBRANCH.NODAL AS NODAL, "
							+ " 							sub_profile.MULTBNK_FUNDBSE_LEADBNK_PER AS MULTBNK_FUNDBSE_LEADBNK_PER, "
							+ "							sub_profile.MULTBNK_NONFUNDBSE_LEADBNK_PER AS MULTBNK_NONFUNDBSE_LEADBNK_PER, "
							+ "							sub_profile.MULTBNK_FUNDBSE_HDFCBNK_PER AS MULTBNK_FUNDBSE_HDFCBNK_PER, "
							+ "							sub_profile.MULTBNK_NONFUNDBSE_HDFCBNK_PER AS MULTBNK_NONFUNDBSE_HDFCBNK_PER, "
							+ "							sub_profile.CONSBNK_FUNDBSE_LEADBNK_PER AS CONSBNK_FUNDBSE_LEADBNK_PER, "
							+ "							sub_profile.CONSBNK_NONFUNDBSE_LEADBNK_PER AS CONSBNK_NONFUNDBSE_LEADBNK_PER, "
							+ "							sub_profile.CONSBNK_FUNDBSE_HDFCBNK_PER AS CONSBNK_FUNDBSE_HDFCBNK_PER, "
							+ "							sub_profile.CONSBNK_NONFUNDBSE_HDFCBNK_PER AS CONSBNK_NONFUNDBSE_HDFCBNK_PER, "
							+ "                          rm.RM_MGR_NAME ,\n"
							+ "                          reg.region_name AS Region,\n"
							+ "                          cc_segment.entry_name AS segment,\n"
							+ "                          (sub_profile.total_funded_limit + sub_profile.total_non_funded_limit) AS totalAmount,\n"
							+ "							IFSCBANKBRANCH.status STATUS\n"
							+ "                        FROM\n"
							+ "                          SCI_LE_SUB_PROFILE sub_profile,\n"
							+ "                          SCI_LE_REG_ADDR ra ,\n"
							+ "                          CMS_REGION reg,\n"
							+ "                          (SELECT entry_name,\n"
							+ "                                entry_code\n"
							+ "                          FROM common_code_category_entry\n"
							+ "                          WHERE category_code = 'HDFC_SEGMENT'\n"
							+ "                          ) cc_segment,\n"
							+ "                          CMS_RELATIONSHIP_MGR rm,\n"
							+ "                          CMS_IFSC_CODE IFSCBANKBRANCH\n" +
							// " (select * from cms_ifsc_code where id in
							// (SELECT max(id) FROM cms_ifsc_code " +
							// " where bank_type='O' group by ifsc_code) order
							// by ifsc_code) IFSCBANKBRANCH\n" +
							"                         WHERE\n"
							+ "                                 (sub_profile.CMS_LE_MAIN_PROFILE_ID = ra.CMS_LE_MAIN_PROFILE_ID (+)\n"
							+ "                                AND ra.LRA_TYPE_VALUE = 'CORPORATE')\n"
							+ "                                AND ra.lra_region = reg.id\n"
							+ "                                AND cc_segment.entry_code(+) = sub_profile.lsp_sgmnt_code_value\n"
							+ "                                AND rm.ID(+) = sub_profile.RELATION_MGR\n"
							+ "                                AND SUB_PROFILE.status != 'INACTIVE'\n"
							+ "                                and IFSCBANKBRANCH.CMS_LE_MAIN_PROFILE_ID = sub_profile.cms_le_sub_profile_id "
							+ "                                and ( IFSCBANKBRANCH.STATUS != 'INACTIVE' or IFSCBANKBRANCH.STATUS is null)  ");
					// " and IFSCBANKBRANCH.STATUS != 'INACTIVE' ");
				}
				WhereClause whereClause = reports.getReport().getWhereClause();
				Param[] params = whereClause.getParam();
				if(params!=null && params.length>0){
					for (int i = 0; i < params.length; i++) {
				if(filter.getParty()!=null && !("".equals(filter.getParty().trim()))){
					query.append(" AND sub_profile.cms_le_sub_profile_id =");
					query.append("'");
					query.append(filter.getParty());
					query.append("' ");
					
					query1.append(" AND sub_profile.cms_le_sub_profile_id =");
					query1.append("'");
					query1.append(filter.getParty());
					query1.append("' ");
					
					query2.append(" AND sub_profile.cms_le_sub_profile_id =");
					query2.append("'");
					query2.append(filter.getParty());
					query2.append("' ");
				}
				
				
				}
			}
			
				query = query.append(query1).append(query2);

			//	int startIndex = query.indexOf(":subProfileId");
			//query.replace(startIndex, startIndex+":subProfileId".length(), filter.getParty());
		}
			
			if("RPT0003".equals(filter.getReportId())){
				query.append(" ) " + 
						"UNION " + 
						" " + 
						"(SELECT sub_profile.lsp_short_name AS party_name, " + 
						"                                         cc_segment.entry_name AS segmentName, " + 
						"                                         cr.region_name as region, " + 
						"                                         csupdate.id AS caseid, " + 
						" " + 
						"                                sysbranch.system_bank_branch_name as branchName, " + 
						" " + 
						"                                case " + 
						"                                 when checklist.category='O' then 'Other' " + 
						"                                when checklist.category='F' then 'Facility' " + 
						"                                when checklist.category='S' then 'Security' " + 
						"                                when checklist.category='CAM' then 'CAM' " + 
						"                                end " + 
						"                                as DocumentType, " + 
						" " + 
						"                                case " + 
						"                                 when checklist.category='O' then ' ' " + 
						"                                when checklist.category='F' then apprlimit.facility_name " + 
						"                                when checklist.category='S' then ' ' " + 
						"                                when checklist.category='CAM' then ' ' " + 
						"                                end as FacilityName, " + 
						" " + 
						"                                case " + 
						"                                 when checklist.category='O' then ' ' " + 
						"                                when checklist.category='F' then ' ' " + 
						"                                when checklist.category='S' then sec.type_name ||'-'||sec.subtype_name " + 
						"                                when checklist.category='CAM' then ' ' " + 
						"                                end as securitytype, " + 
						" " + 
						"                                  checklistitem.doc_description AS DocumentName, " + 
						"                                  checklistitem.document_code AS DocumentCode, " + 
						"                                        case " + 
						"                                 when casecreation.status='1' then 'CPUT Requested' " + 
						"                                when casecreation.status='2' then 'BRANCH Sent' " + 
						"                                when casecreation.status='3' then 'CPUT Received' " + 
						"                                when casecreation.status='4' then 'Wrong Request' " + 
						"        when casecreation.status='5' then 'Wrong Request Accepted' " + 
						"  WHEN casecreation.status='6' " +
					    "  THEN 'Sent to vault' "+
					    "  WHEN casecreation.status='7' "+
					    "  THEN 'Received at Vault' "+
					    "  WHEN casecreation.status='8' "+
					    "  THEN 'Permanent Retrieval' "+
					    "  WHEN casecreation.status='9' "+
					    "  THEN 'Temporary Retrieval' "+
						"                                end " + 
						"                                  AS Status, " + 
						"                                  casecreation.remark AS Remarks, " + 
						"                                  to_char(casecreation.requested_date,'DD/Mon/YYYY') as RequestedDate, " + 
						"                          to_char(casecreation.dispatched_date,'DD/Mon/YYYY') as DispatchedDate, " + 
						"                          to_char(casecreation.received_date,'DD/Mon/YYYY') as ReceivedDate " + 
						" " + 
						"                                FROM SCI_LE_SUB_PROFILE sub_profile , " + 
						"                                  SCI_LE_MAIN_PROFILE main_profile , " + 
						"                                  (SELECT entry_name, " + 
						"                                    entry_code " + 
						"                                  FROM common_code_category_entry " + 
						"                                  WHERE category_code = 'HDFC_SEGMENT' " + 
						"                                  ) cc_segment , " + 
						" " + 
						"                                  sci_lsp_lmt_profile lmt_profile , " + 
						"                                  cms_casecreation_item casecreation , " + 
						"          cms_casecreationupdate csupdate, " + 
						"          SCI_LE_REG_ADDR addr , " + 
						"          CMS_REGION cr, " + 
						"          cms_system_bank_branch sysbranch, " + 
						"                                  cms_checklist_item checklistItem, " + 
						"          cms_checklist checklist, " + 
						"          SCI_LSP_APPR_LMTS apprlimit, " + 
						"          CMS_SECURITY sec, " + 
						"  CMS_LIMIT_SECURITY_MAP SEC_MAP " + 
						" " + 
						" " + 
						" " + 
						" " + 
						"                         WHERE " + 
						"                                sub_profile.cms_le_main_profile_id = main_profile.cms_le_main_profile_id " + 
						"                                AND cc_segment.entry_code =main_profile.lmp_sgmnt_code_value " + 
						"                                AND sub_profile.lsp_le_id =lmt_profile.llp_le_id " + 
						"                                AND sub_profile.status ='ACTIVE' " + 
						"                                AND csupdate.limit_profile_id = lmt_profile.CMS_LSP_LMT_PROFILE_ID " + 
						"        AND csupdate.id = casecreation.casecreationid " + 
						"                                AND casecreation.checklistitemid =checklistitem.doc_item_id " + 
						"        and addr.cms_le_main_profile_id=sub_profile.cms_le_main_profile_id " + 
						"        and addr.lra_type_value ='CORPORATE' " + 
						"        and cr.id=addr.lra_region " + 
						"        and sysbranch.system_bank_branch_code = csupdate.branchcode " + 
						"        and checklistitem.checklist_id= checklist.checklist_id " + 
						"        and checklistItem.is_deleted='N' " + 
						"        and checklistItem.status not in ('WAIVED') " + 
						"        and checklistItem.is_deleted='N' " + 
						"        and checklistItem.status not in ('WAIVED') " + 
						"        and checklist.cms_collateral_id=apprlimit.CMS_LSP_APPR_LMTS_ID(+) " + 
						"        and checklist.cms_collateral_id= sec.cms_collateral_id(+) " + 
						"        AND checklist.category='S' " + 
						"        AND SEC_MAP.CMS_LSP_LMT_PROFILE_ID = lmt_profile.CMS_LSP_LMT_PROFILE_ID " + 
						"        AND " + 
						"          SEC_MAP.deletion_date is null " + 
						"AND SEC.cms_collateral_id =SEC_MAP.CMS_COLLATERAL_ID " + 
						"AND SEC_MAP.CHARGE_ID in " + 
						"(SELECT MAX(maps2.CHARGE_ID) " + 
						"from cms_limit_security_map maps2 " + 
						"where " + 
						"maps2.cms_collateral_id = SEC_MAP.cms_collateral_id " + 
						"AND maps2.cms_collateral_id = SEC.CMS_COLLATERAL_ID " + 
						"AND maps2.CMS_LSP_LMT_PROFILE_ID = csupdate.limit_profile_id " +
						"AND (maps2.UPDATE_STATUS_IND <> 'D' OR maps2.UPDATE_STATUS_IND IS NULL) " + 
						") " + 
				/*		"AND (SEC_MAP.UPDATE_STATUS_IND <> 'D' OR SEC_MAP.UPDATE_STATUS_IND IS NULL) " + */
						"  ");
				//		"                                and IFSCBANKBRANCH.STATUS != 'INACTIVE' ");
				WhereClause whereClauses = reports.getReport().getWhereClause();
				Param[] param = whereClauses.getParam();
				if(param!=null && param.length>0){
					for (int j = 0; j < param.length; j++) {
						Param param1 = param[j];
					
						if("party".equals(param1.getName())){
							
							if(filter.getParty()!=null && !("".equals(filter.getParty().trim()))){
								query.append(" AND  sub_profile.CMS_LE_SUB_PROFILE_ID = ");
								query.append("'");
								query.append(filter.getParty());
								query.append("'");
								if(filter.getStatus()== null || ("".equals(filter.getStatus()))){
									query.append(" )");
								}
							}
							
							if((filter.getParty()==null || ("".equals(filter.getParty().trim()))) && (filter.getStatus()==null || ("".equals(filter.getStatus())))){
								query.append(" )");
							}
						}
						else if("status".equals(param1.getName())) {
							if(filter.getStatus()!=null && !("".equals(filter.getStatus()))){
								query.append(" and casecreation.status  = ");
								query.append("'");
								query.append(filter.getStatus());
								query.append("'  ) ");
							}
						}
				//and casecreation.status  =
					}}}
			
		}
		
		DefaultLogger.debug(this, "============================= Displaying Query ==========================");
		DefaultLogger.debug(this, query.toString());
		return query.toString();
	}
	
	public String getPartyId(String id) {
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String sql = "SELECT LSP_LE_ID FROM SCI_LE_SUB_PROFILE WHERE CMS_LE_SUB_PROFILE_ID = '"+id+"'";
		String partyId = "";
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs=dbUtil.executeQuery();
			while(rs.next()){
				partyId = rs.getString("LSP_LE_ID");
			}
			rs.close();
			dbUtil.close();
		}catch(Exception e){
		e.printStackTrace();
		DefaultLogger.debug(this, "Exception in getPartyId");
		}
		return partyId;
	}


	/**
	 * @param reports
	 */
	private String createCriteria(Reports reports,OBFilter filter) {
		StringBuffer criteria= new StringBuffer();
		

		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
		Date dateobj = new Date();

		String errorCode = "";
		WhereClause whereClause = reports.getReport().getWhereClause();
		if(whereClause.getMandatoryClause()!=null && !"".equals(whereClause.getMandatoryClause().trim()))
			criteria.append(whereClause.getMandatoryClause());
		
		Param[] params = whereClause.getParam();
		if(params!=null && params.length>0){
			for (int i = 0; i < params.length; i++) {
				Param param = params[i];
				DefaultLogger.debug(this,"processing param------------"+param.getName());
				if("RPT0016".equals(filter.getReportId())){
					if(filter.getStatus()!=null && !("".equals(filter.getStatus()))){
						criteria.append(" "+param.getCondition());
						criteria.append("'");
						criteria.append(filter.getStatus());
						criteria.append("'");
						criteria.append(")");
					}else{
						criteria.append(")");
					}
					if(filter.getParty()!=null && !("".equals(filter.getParty()))){
						criteria.append(" WHERE PartyId = ");
						criteria.append("'");
						criteria.append(filter.getParty());
						criteria.append("'");
					}
					break;
				}else{
					if("segment".equals(param.getName())){
						if(filter.getSegment()!=null && !("".equals(filter.getSegment().trim()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getSegment());
							criteria.append("'");
						}
					}else if("industry".equals(param.getName())){
						if(filter.getIndustry()!=null && !("".equals(filter.getIndustry().trim()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getIndustry());
							criteria.append("'");
						}
					}else if("party".equals(param.getName())){
						if("RPT0070".equals(filter.getReportId())){
							if(filter.getParty()!=null && !("".equals(filter.getParty().trim()))){
								criteria.append(" "+param.getCondition());
								criteria.append(" AND SUB.LSP_LE_ID = '");
								criteria.append(getPartyId(filter.getParty()));
								criteria.append("'");
							}	
						}
						else if("RPT0010".equals(filter.getReportId())) {
							
							if(filter.getParty()!=null && !("".equals(filter.getParty().trim()))){
							if(filter.getRelationManager()!=null && !("".equals(filter.getRelationManager().trim()))){
								criteria.append(" AND "+param.getCondition());
								
							}else {
								criteria.append(" "+param.getCondition());
							}
							criteria.append("'");
							criteria.append(filter.getParty());
							criteria.append("'");
							}
						}else if("RPT0012".equals(filter.getReportId())) {
							
							if(filter.getParty()!=null && !("".equals(filter.getParty().trim()))){
							if(filter.getSegment()!=null && !("".equals(filter.getSegment().trim()))){
								criteria.append("  "+param.getCondition());
								
							}else {
								criteria.append(" "+param.getCondition());
							}
							criteria.append("'");
							criteria.append(filter.getParty());
							criteria.append("'");
							}
						}
						else if("RPT0103".equals(filter.getReportId())){
							if(filter.getParty()!=null && !("".equals(filter.getParty().trim()))){
								criteria.append(" "+param.getCondition());
								criteria.append(" AND SLMP.LMP_LE_ID = '");
								criteria.append(getPartyId(filter.getParty()));
								criteria.append("' ");
								criteria.append(" AND CDDEL.PARTY_ID = '");
								criteria.append(getPartyId(filter.getParty()));
								criteria.append("' ");
							}	
						}
						else if("RPT0099".equals(filter.getReportId())){
							if(filter.getParty()!=null && !("".equals(filter.getParty().trim()))){
								criteria.append(" "+param.getCondition());
								criteria.append(" '");
								criteria.append(getPartyId(filter.getParty()));
								criteria.append("' ");
							}	
						}
						else if("RPT0086".equals(filter.getReportId())){
							if(filter.getParty()!=null && !("".equals(filter.getParty().trim()))){
								criteria.append(" "+param.getCondition());
								criteria.append(" '");
								criteria.append(getPartyId(filter.getParty()));
								criteria.append("' ");
							}	
						}
						else{
						if(filter.getParty()!=null && !("".equals(filter.getParty().trim()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getParty());
							criteria.append("'");
						}
						}
					}else if("branch".equals(param.getName())){
						if(filter.getBranchId()!=null && !("".equals(filter.getBranchId().trim()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getBranchId());
							criteria.append("'");
						}
					}else if("docType".equals(param.getName())){
						if(filter.getDocumentType()!=null && !("".equals(filter.getDocumentType().trim()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getDocumentType());
							criteria.append("'");
						}
					}else if("relationship".equals(param.getName())){
						if(filter.getRelatoionship()!=null && !("".equals(filter.getRelatoionship().trim()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getRelatoionship());
							criteria.append("'");
						}
					}else if("guarantor".equals(param.getName())){
						if(filter.getGuarantor()!=null && !("".equals(filter.getGuarantor().trim()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getGuarantor());
							criteria.append("'");
						}
					}else if("userId".equals(param.getName())){
						if(filter.getUserId()!=null && !("".equals(filter.getUserId().trim()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getUserId());
							criteria.append("'");
						}
					}else if("department".equals(param.getName())){
						if(filter.getDepartmentId()!=null && !("".equals(filter.getDepartmentId().trim()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getDepartmentId());
							criteria.append("'");
						}
					}else if("fromDate".equals(param.getName())){
						if(filter.getFromDate()!=null && !("".equals(filter.getFromDate()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getFromDate());
							criteria.append("'");
						}else {
							if(filter.getToDate()!=null && !("".equals(filter.getToDate()))) {
								if("RPT0076".equals(filter.getReportId()) || "RPT0077".equals(filter.getReportId())) {
									criteria.append(" "+param.getCondition());
									criteria.append("'");
									criteria.append(filter.getToDate());
									criteria.append("'");
								}
							}
						}
					}else if("toDate".equals(param.getName())){
						if(filter.getToDate()!=null && !("".equals(filter.getToDate()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getToDate());
							criteria.append("'");
						}else {
							if(filter.getFromDate()!=null && !("".equals(filter.getFromDate()))) {
								if("RPT0076".equals(filter.getReportId()) || "RPT0077".equals(filter.getReportId())) {
									criteria.append(" "+param.getCondition());
									criteria.append("'");
									criteria.append(filter.getFromDate());
									criteria.append("'");
								}
							}
						}
					}else if("scodFromDate".equals(param.getName())){
						if(filter.getScodFromDate()!=null && !("".equals(filter.getScodFromDate()))){
							criteria.append(" "+param.getCondition());
							criteria.append(" AND APPR.SCOD BETWEEN '");
							criteria.append(filter.getScodFromDate());
							criteria.append("'");
						}
					}else if("scodToDate".equals(param.getName())){
						if(filter.getScodToDate()!=null && !("".equals(filter.getScodToDate()))){
							criteria.append(" "+param.getCondition());
							criteria.append(" AND '");
							criteria.append(filter.getScodToDate());
							criteria.append("' ");
						}else if(filter.getScodFromDate()!=null && !("".equals(filter.getScodFromDate()))){
							criteria.append(" "+param.getCondition());
							criteria.append(" AND '");
							if ((errorCode = UIValidator.compareDateEarlier(filter.getScodFromDate(),df.format(dateobj), Locale.getDefault()))
									.equals(Validator.ERROR_NONE)) {
								criteria.append(df.format(dateobj));
							}else {
								criteria.append(filter.getScodFromDate());
							}
							criteria.append("'");

						}
					}else if("escodFromDate".equals(param.getName())){
						if(filter.getEscodFromDate()!=null && !("".equals(filter.getEscodFromDate()))){
							criteria.append(" "+param.getCondition());
							criteria.append("AND APPR.ESCOD_L1 BETWEEN '");
							criteria.append(filter.getEscodFromDate());
							criteria.append("'");
						}
					}else if("escodToDate".equals(param.getName())){
						if(filter.getEscodToDate()!=null && !("".equals(filter.getEscodToDate()))){
							criteria.append(" "+param.getCondition());
							criteria.append(" AND '");
							criteria.append(filter.getEscodToDate());
							criteria.append("'");
							
						}else if(filter.getEscodFromDate()!=null && !("".equals(filter.getEscodFromDate()))){

							criteria.append(" "+param.getCondition());
							criteria.append(" AND '");
							if ((errorCode = UIValidator.compareDateEarlier(filter.getEscodFromDate(),df.format(dateobj), Locale.getDefault()))
									.equals(Validator.ERROR_NONE)) {
								criteria.append(df.format(dateobj));
							}else {
								criteria.append(filter.getEscodFromDate());
							}
							criteria.append("'");
						}
					}else if("status".equals(param.getName())){
						if(filter.getStatus()!=null && !("".equals(filter.getStatus()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getStatus());
							criteria.append("'");
						}
					}else if("relationManager".equals(param.getName())){
						if(filter.getRelationManager()!=null && !("".equals(filter.getRelationManager().trim()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getRelationManager());
							criteria.append("'");
						}
					}else if("rbiAsset".equals(param.getName())){
						if(filter.getRbiAsset()!=null && !("".equals(filter.getRbiAsset().trim()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getRbiAsset());
							criteria.append("'");
						}
					}else if("relationshipManager".equals(param.getName())){
						if(filter.getRelationshipMgr()!=null && !("".equals(filter.getRelationshipMgr()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getRelationshipMgr());
							criteria.append("'");
						}
					}else if("event".equals(param.getName())){
						if(filter.getModuleEvent()!=null && !("".equals(filter.getModuleEvent()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getModuleEvent());
							criteria.append("'");
						}
					}else if("tatCriteria".equals(param.getName())){
						if(filter.getTatCriteria()!=null && !("".equals(filter.getTatCriteria()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getTatCriteria());
							criteria.append("'");
						}
					}else if("category".equals(param.getName())){
						if(filter.getCategory()!=null && !("".equals(filter.getCategory()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getCategory());
							criteria.append("'");
						}
					}else if("caseId".equals(param.getName())){
						if(filter.getCaseId()!=null && !("".equals(filter.getCaseId()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getCaseId());
							criteria.append("'");
						}
					}else if("region".equals(param.getName())){
						if(filter.getRegion()!=null && !("".equals(filter.getRegion()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getRegion());
							criteria.append("'");
						}
					}else if("partyId".equals(param.getName())){
						if(filter.getPartyId()!=null && !("".equals(filter.getPartyId()))){
							
							if(filter.getSecurityId()!=null && !("".equals(filter.getSecurityId()))) {
								criteria.append("(");
							}
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getPartyId());
							criteria.append("'");
						}
						//Added by Prachit For Cersai Details Report (partyId)
					}else if("securityId".equals(param.getName())){
						if(filter.getSecurityId()!=null && !("".equals(filter.getSecurityId()))){
							if(filter.getPartyId()!=null && !("".equals(filter.getPartyId()))){
								criteria.append(" AND ");
							}
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getSecurityId());
							criteria.append("'");
							if(filter.getPartyId()!=null && !("".equals(filter.getPartyId()))){
								criteria.append(" )");
							}
						}
						//Added by Prachit For Cersai Details Report (securityId)
					}else if("bankingMethod".equals(param.getName())){
						if(filter.getBankingMethod()!=null && !("".equals(filter.getBankingMethod()))){
							
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getBankingMethod());
							criteria.append("'");
						}
						//Added by Prachit For Cersai Charge Release Report (fromdate) securityType
					}else if("fromDate1".equals(param.getName())){
						if(filter.getFromDate()!=null && !("".equals(filter.getFromDate()))){
							criteria.append(" "+param.getCondition());
							criteria.append(" AND (CHECKLIST_ITEM.RECEIVED_DATE BETWEEN TO_DATE('");
							criteria.append(filter.getFromDate());
							criteria.append("','DD/MM/YYYY') AND ");
						}
						//Added by Prachit For Cersai Charge Release Report (todate)
					}else if("toDate1".equals(param.getName())){
						if(filter.getToDate()!=null && !("".equals(filter.getToDate()))){
							criteria.append(" "+param.getCondition());
							criteria.append("TO_DATE('");
							criteria.append(filter.getToDate());
							criteria.append("','DD/MM/YYYY') ) AND ");
						}
						//Added by Prachit For Cersai Charge Release Report (security type)
					}else if("securityType".equals(param.getName())){
						if(filter.getTypeOfSecurity()!=null && !("".equals(filter.getTypeOfSecurity()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getTypeOfSecurity());
							criteria.append("'");
						}
						//Added by Prachit For Cersai Charge Release Report (bankingMethod)
					}else if("filterDocument".equals(param.getName())){
						if(filter.getFilterDocument()!=null && !("".equals(filter.getFilterDocument().trim()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getFilterDocument());
							criteria.append("'");
						}
						
					//added by santosh For UBS CR	
					}else if("recordType".equals(param.getName())){
						if(filter.getRecordType()!=null && !("".equals(filter.getRecordType()))){
							if(!filter.getRecordType().equalsIgnoreCase("SUMMARY")) {
								criteria.append(" "+param.getCondition());
								criteria.append("'");
								criteria.append(filter.getRecordType());
								criteria.append("'");
							}
						}
						else if(!"RPT0076".equals(filter.getReportId()) && !"RPT0077".equals(filter.getReportId())) {
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append("SUCCESS");
							criteria.append("'");
						}
					}
					//end santosh
					else if("facility".equals(param.getName())){
						if(filter.getFacility()!=null && !("".equals(filter.getFacility()))) {
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getFacility());
							criteria.append("'");
						}
					}
					else if("mortgageFromDate".equals(param.getName())){
						if(filter.getFromDate()!=null && !("".equals(filter.getFromDate()))){
							criteria.append(" "+param.getCondition());
//							criteria.append("AND APPR.ESCOD_L1 BETWEEN '");
							criteria.append(" AND CP.VALUATION_DATE_V1 BETWEEN '");
							criteria.append(filter.getFromDate());
							criteria.append("'");
						}
					}else if("mortgageToDate".equals(param.getName())){
						if(filter.getToDate()!=null && !("".equals(filter.getToDate()))){
							criteria.append(" "+param.getCondition());
							criteria.append(" AND '");
							criteria.append(filter.getToDate());
							criteria.append("'");
							
						}else if(filter.getFromDate()!=null && !("".equals(filter.getFromDate()))){
							criteria.append(" "+param.getCondition());
							criteria.append(" AND '");
							Calendar date1 = Calendar.getInstance();
							date1.setTime(DateUtil.convertDate(Locale.getDefault(), filter.getFromDate()));
							date1.add(Calendar.MONTH, 6);
							String d2 = df.format(date1.getTime());
							criteria.append(d2);
							criteria.append("'");
						}
					}else if("partyIdName".equals(param.getName())){
						if(filter.getPartyId()!=null && !("".equals(filter.getPartyId()))){
							criteria.append(" AND "+param.getCondition());
							criteria.append(" '");
							criteria.append(filter.getPartyId());
							criteria.append("'");
						}
					}else if("securityStatus".equals(param.getName())){
						if(filter.getSecurityStatus()!=null && !("".equals(filter.getSecurityStatus()))){
							criteria.append(" "+param.getCondition());
							criteria.append(" '");
							criteria.append(filter.getSecurityStatus());
							criteria.append("'");
						}
					}
					else if("securityType1".equals(param.getName())){
						if(filter.getSecurityType1()!=null && !("".equals(filter.getSecurityType1().trim()))){
							String secTypeName = getSecurityTypeName(filter.getSecurityType1());
							
							if((filter.getSegment()!=null && !("".equals(filter.getSegment().trim()))) || (filter.getParty()!=null && !("".equals(filter.getParty().trim())))){
								criteria.append(" AND "+param.getCondition());
								
							}else {
								criteria.append(" "+param.getCondition());
							}
							
//							criteria.append(" "+param.getCondition());
							criteria.append("'");
//							criteria.append(filter.getSecurityType1());
							criteria.append(secTypeName);
							criteria.append("'");
						}
					}
					else if("securitySubType".equals(param.getName())){
						if(filter.getSecuritySubType()!=null && !("".equals(filter.getSecuritySubType().trim()))){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(filter.getSecuritySubType());
							criteria.append("'");
						}
					}
					else if("systemName".equals(param.getName())) {
						if("RPT0078".equals(filter.getReportId())){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append("BAHRAIN");
							criteria.append("'");
						}
						else if("RPT0079".equals(filter.getReportId())){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append("GIFTCITY");
							criteria.append("'");
						}
						else if("RPT0080".equals(filter.getReportId())){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append("HONGKONG");
							criteria.append("'");
						}
					}
					else if("eodSyncUpDate".equals(param.getName())) {
						if("RPT0089".equals(filter.getReportId())){
							criteria.append("'").append(filter.getEodSyncUpDate()).append("'");
							criteria.append(param.getCondition());
						}
					}
					else if("systemDateAudit".equals(param.getName())) {
						if("RPT0086".equals(filter.getReportId())){
//							criteria.append(" "+param.getCondition());
							criteria.append(" AND (trunc(TRX.TRANSACTION_DATE) BETWEEN TO_CHAR((select add_months(sysdate,-"+filter.getMonthsOfAuditTrail()+") from dual),'DD/MON/YYYY') AND TO_CHAR((select sysdate  from dual),'DD/MON/YYYY') ) ");
						}
						
					}
					else if("uploadSystem".equals(param.getName())) {
						if("RPT0081".equals(filter.getReportId())) {
							if(!"ALL".equals(filter.getUploadSystem())) {
								criteria.append(" "+param.getCondition());
								criteria.append("'");
								criteria.append(filter.getUploadSystem());
								criteria.append("'");
							}
						}
					}	
					else if("dueDates".equals(param.getName())) {
						if("RPT0085".equals(filter.getReportId())){
//							criteria.append(" "+param.getCondition());
							if("Post updation of stock statement along with DP arrived".equals(filter.getEventOrCriteria())
								|| "Stock Statement is due for more than 2 months".equals(filter.getEventOrCriteria())	
								) {
								System.out.println("filter.getSelectYearDropdown====::"+filter.getSelectYearDropdown());
								String[] startYr= filter.getSelectYearDropdown().split("-");
								
//								criteria.append("  AND CAGD.DUE_DATE= (select sysdate -1 from dual) ");
								criteria.append(" AND TO_DATE(DUE_DATE,'DD/MM/YYYY') >=  '01-APR-"+startYr[0]+"'  ");
								criteria.append(" AND TO_DATE(DUE_DATE,'DD/MM/YYYY') <=  '31-MAR-20"+startYr[1]+"'  ");
							}
							else if("Stock statement is not submitted once the due date is over".equals(filter.getEventOrCriteria())) {
								criteria.append(" AND TO_DATE(DUE_DATE,'DD/MM/YYYY') = TO_DATE((select TO_CHAR(sysdate-1,'DD/Mon/YYYY')  from dual) ,'DD/MM/YYYY') ");
							}
							/*else if("Stock Statement is due for more than 2 months".equals(filter.getEventOrCriteria())) {
								System.out.println("filter.getSelectYearDropdown====::"+filter.getSelectYearDropdown());
								//criteria.append(" AND TO_DATE(DUE_DATE,'DD/MM/YYYY') <= TO_DATE((select TO_CHAR(sysdate-60,'DD/Mon/YYYY')  from dual),'DD/MM/YYYY') 
								criteria.append(" AND TO_DATE(DUE_DATE,'DD/MM/YYYY') >=  '01-JAN-"+filter.getSelectYearDropdown()+"'  ");
								criteria.append(" AND TO_DATE(DUE_DATE,'DD/MM/YYYY') <=  '31-DEC-"+filter.getSelectYearDropdown()+"'  ");
							
							}*/
							else if("Notification before 15 days from Stock statement Due date".equals(filter.getEventOrCriteria())) {
								criteria.append(" AND  TO_DATE(DUE_DATE,'DD/MM/YYYY') = TO_DATE((select TO_CHAR(sysdate+15,'DD/Mon/YYYY')  from dual),'DD/MM/YYYY')  ");
							}
							else if("Notification before 7 days from Stock statement Due date".equals(filter.getEventOrCriteria())) {
								criteria.append(" AND  TO_DATE(DUE_DATE,'DD/MM/YYYY') = TO_DATE((select TO_CHAR(sysdate+7,'DD/Mon/YYYY')  from dual),'DD/MM/YYYY')  ");
							}
							else if("Notification before 1 days from Stock statement Due date".equals(filter.getEventOrCriteria())) {
								criteria.append("  AND  TO_DATE(DUE_DATE,'DD/MM/YYYY') = TO_DATE((select TO_CHAR(sysdate+1,'DD/Mon/YYYY')  from dual),'DD/MM/YYYY')  ");
							}else {
								
							}
							System.out.println("criteria="+criteria);
//							criteria.append("BAHRAIN");
//							criteria.append("'");
						}
					}

				}
/*//				if (object.getFilterValue !=null){
					criteria.append(" "+param.getCondition());
					criteria.append(" ");
						//String method = filter.getField(filter,param.getName());
					criteria.append(param.getDefaultValue());
//				}				
*/			
			}
		}
		if("RPT0051".equals(filter.getReportId())){
			criteria.append(")");
		}
		
		//bellow added for line co borrower report
		if("RPT0091".equals(filter.getReportId())  ){
			criteria.append(" ) PIVOT ( MAX(CO_BORROWER_ID) AS ID, MAX(CO_BORROWER_NAME) AS NAME"
					+ " FOR NUM IN ( '1' AS co_borrower_1, '2' AS co_borrower_2,  '3' AS co_borrower_3,  '4' AS co_borrower_4, '5' AS co_borrower_5 ) )     ");
			
		}
		if(  "RPT0092".equals(filter.getReportId()) ){
			criteria.append(" ) PIVOT ( MAX(CO_BORROWER_LIAB_ID) AS ID, MAX(CO_BORROWER_NAME) AS NAME"
					+ " FOR NUM IN ( '1' AS co_borrower_1, '2' AS co_borrower_2,  '3' AS co_borrower_3,  '4' AS co_borrower_4, '5' AS co_borrower_5 ) )     ");
			
		}
		if(whereClause.getOrderAndGroupByClause() !=null && !"".equals(whereClause.getOrderAndGroupByClause().trim()))
			criteria.append(whereClause.getOrderAndGroupByClause());
		System.out.println("Report Criteria : "+criteria.toString());
		return criteria.toString();
	}
	
	public String getSecurityTypeName(String secTypeNameCode) {
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String sql = "SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY WHERE ENTRY_CODE = '"+secTypeNameCode +"' AND CATEGORY_CODE = '31' AND ACTIVE_STATUS = '1'";
		String secTypeName = "";
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs=dbUtil.executeQuery();
			while(rs.next()){
				secTypeName = rs.getString("ENTRY_NAME");
			}
			rs.close();
			dbUtil.close();
		}catch(Exception e){
		e.printStackTrace();
		DefaultLogger.debug(this, "Exception in getSecurityTypeName.");
		}
		return secTypeName;
	}
 
}
