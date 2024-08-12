package com.integrosys.cms.batch.strc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.cms.batch.ecbf.OBEcbfDeferralReport;

public class ECommodityBasedFinancingJdbcImpl extends JdbcDaoSupport implements IECommodityBasedFinancingJdbc {

	
	
	public final static String ECBF_DEFERRAL_LAD_REPORT ="SELECT DISTINCT SLSP.LSP_LE_ID AS PARTY_ID,"+
			"  SLSP.LSP_SHORT_NAME   AS PARTY_NAME,"+
			"  CSEC.CMS_COLLATERAL_ID AS SOURCE_SEC_ID,"+
			"  CSEC.COLLATERAL_CODE   AS COLLATERAL_CODE_NAME,"+
			"  APPR.LMT_ID AS FACILITY_ID,"+
			"  APPR.FACILITY_NAME     AS FACILITY_NAME,"+
			"  APPR.CMS_REQ_SEC_COVERAGE    AS FAC_SANCTION_AMT,"+
			"  APPR.TOTAL_RELEASED_AMOUNT   AS FACILITY_RELEASED_AMOUNT,"+
			"  SCAGD.DP_CALCULATE_MANUALLY  AS DP_TO_BE_CALCULATED,"+
			"  SCAGD.CALCULATEDDP     AS DRAWING_POWER_AS_PER_STOCK_STATEMENT,"+
			"  SCAGD.STOCK_DOC_MONTH  AS STOCK_DOC_MONTH,"+
			"  SCAGD.STOCK_DOC_YEAR   AS STOCK_DOC_YEAR,"+
			"  SCAGD.DUE_DATE   AS DUE_DATE,"+
			"  SCAGD.DP_SHARE   AS DP_SHARE,"+
			"  CAGD.REMARK_BY_MAKER   AS REMARK_BY_MAKER,"+
			"  CSEC.TERM_LOAN_OUTSTD_AMT   AS  TERM_LOAN_OUTSTD_AMT,"+
			"  CSEC.MARGIN_ASSET_COVER  AS  MARGIN_ASSET_COVER,"+
			"  CSEC.RECEIVABLES_GIVEN_BY_CLIENT  AS  RECEIVABLES_GIVEN_BY_CLIENT, "+
			"  SCAGD.LAST_UPDATED_BY  AS MAKER_ID,"+
			"  SCAGD.LAST_APPROVED_BY AS CHECKER_ID,"+
			"  SCAGD.LAST_UPDATED_ON  AS MAKER_DATE,"+
			"  SCAGD.LAST_APPROVED_ON AS CHECKER_DATE"+
			" FROM "+
			"  (SELECT CMS_COLLATERAL_ID,"+
			"    STATUS,"+
			"    COLLATERAL_CODE,TERM_LOAN_OUTSTD_AMT,MARGIN_ASSET_COVER,RECEIVABLES_GIVEN_BY_CLIENT"+
			"  FROM CMS_SECURITY"+
			"  WHERE SECURITY_SUB_TYPE_ID='AB100'"+
			"  ) CSEC,"+
			"  SCI_LSP_APPR_LMTS APPR,"+
			"  CMS_LIMIT_SECURITY_MAP MAP,"+
			"  SCI_LSP_LMT_PROFILE LMT,"+
			"  SCI_LE_SUB_PROFILE SLSP,"+
			"  CMS_ASSET_GC_DET CAGD,"+
			"  CMS_STAGE_ASSET_GC_DET SCAGD,"+
			"  (SELECT trx.from_state,"+
			"    trx.transaction_id,"+
			"    trx.status ,"+
			"    trx.REFERENCE_ID,"+
			"    trx.login_id,"+
			"    trx.TRANSACTION_DATE,"+
			"    trx.CUR_TRX_HISTORY_ID"+
			"  FROM TRANSACTION trx"+
			"  WHERE TRANSACTION_TYPE='COL'"+
			"  ) trx"+
			" WHERE CSEC.CMS_COLLATERAL_ID = CAGD.CMS_COLLATERAL_ID"+
			" AND SCAGD.CMS_COLLATERAL_ID IN"+
			"  (SELECT STAGING_REFERENCE_ID"+
			"  FROM TRANS_HISTORY_COL"+
			"  WHERE REFERENCE_ID= CAGD.CMS_COLLATERAL_ID"+
			"  )"+
			" AND CSEC.CMS_COLLATERAL_ID     = MAP.CMS_COLLATERAL_ID"+
			" AND APPR.CMS_LSP_APPR_LMTS_ID  = MAP.CMS_LSP_APPR_LMTS_ID"+
			" AND LMT.CMS_LSP_LMT_PROFILE_ID = APPR.CMS_LIMIT_PROFILE_ID"+
			" AND LMT.LLP_LE_ID  = SLSP.LSP_LE_ID"+
			" AND SLSP.STATUS    = 'ACTIVE'"+
			" AND APPR.CMS_LIMIT_STATUS = 'ACTIVE'"+
			" AND CSEC.STATUS    = 'ACTIVE'"+
			" AND APPR.CMS_LSP_APPR_LMTS_ID  = MAP.CMS_LSP_APPR_LMTS_ID"+
			" AND (MAP.UPDATE_STATUS_IND    != 'D'"+
			" OR MAP.UPDATE_STATUS_IND IS NULL)"+
			" AND CSEC.CMS_COLLATERAL_ID     = TRX.REFERENCE_ID"+
			" AND TRX.TRANSACTION_DATE BETWEEN '08/JAN/2020' AND '07/FEB/2022'"


;

	
	
	@Override
	public List<OBStrcDeferralReport> ecbfDeferralReportDataMapper(String sql) {
		System.out.println("ecbfDeferralReportDataMapper => SQL =>"+sql);
		return getJdbcTemplate().query(sql, new RowMapper<OBStrcDeferralReport>() {

			public OBStrcDeferralReport mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBStrcDeferralReport report = new OBStrcDeferralReport();
				
				report.setPartyId(rs.getString("PARTY_ID"));
				report.setPartyName(rs.getString("PARTY_NAME"));
				report.setSourceSecurityID(rs.getString("SOURCE_SEC_ID"));
				report.setCollateralCodeName(rs.getString("COLLATERAL_CODE_NAME"));
				report.setFacilityId(rs.getString("FACILITY_ID"));
				report.setFacilityName(rs.getString("FACILITY_NAME"));
				report.setFacSanctionAmt(rs.getString("FAC_SANCTION_AMT"));
				report.setFacilityReleasedAmt(rs.getString("FACILITY_RELEASED_AMOUNT"));
				report.setSecurityDPAmt(rs.getString("DP_TO_BE_CALCULATED"));
				report.setDrawingPower(rs.getString("DRAWING_POWER_AS_PER_STOCK_STATEMENT"));
				report.setStockDueMonth(rs.getString("STOCK_DOC_MONTH"));
				report.setStockDueYear(rs.getString("STOCK_DOC_YEAR"));
				report.setDueDate(rs.getString("DUE_DATE"));
				report.setDpShare(rs.getString("DP_SHARE"));
				report.setRemarksByMaker(rs.getString("REMARK_BY_MAKER"));
				report.setTermLoanOutamt(rs.getString("TERM_LOAN_OUTSTD_AMT"));
				report.setMarginAssetCover(rs.getString("MARGIN_ASSET_COVER"));
				report.setReceivableGiven(rs.getString("RECEIVABLES_GIVEN_BY_CLIENT"));
				report.setMakerID(rs.getString("MAKER_ID"));
				report.setCheckerID(rs.getString("CHECKER_ID"));
				report.setMakerDate(rs.getString("MAKER_DATE"));
				report.setCheckerDate(rs.getString("CHECKER_DATE"));
				
				
				return report;
			}
		});
	}


	@Override
	public List<OBStrcDeferralReport> getEcbfDeferralReportData() {
		
		List<OBStrcDeferralReport> result = ecbfDeferralReportDataMapper(ECBF_DEFERRAL_LAD_REPORT);
		/*result.addAll(ecbfDeferralReportDataMapper(ECBF_DEFERRAL_DISCREPANCY_REPORT));
		result.addAll(ecbfDeferralReportDataMapper(ECBF_DEFERRAL_LAD_REPORT));*/
		
		return result;
	}

}
