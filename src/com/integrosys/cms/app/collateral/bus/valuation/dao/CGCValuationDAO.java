package com.integrosys.cms.app.collateral.bus.valuation.dao;

import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.CGCValuationModel;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.dao.DataAccessException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Sep 22, 2008
 * Time: 2:08:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class CGCValuationDAO extends GuaranteeValuationDAO implements ICGCValuationDAO {

    public void retrieveValuationInfoForCal(IValuationModel valModel) throws ValuationException {
        final CGCValuationModel gValModel = (CGCValuationModel) valModel;
        String query = "SELECT GUARANTEE_AMT, CURRENCY_CODE, SECURED_PORTION, UNSECURED_PORTION, " +
                        "SECURED_AMOUNT_ORIGIN, UNSECURED_AMOUNT_ORIGIN " +
                        "FROM CMS_GUARANTEE WHERE CMS_GUARANTEE.CMS_COLLATERAL_ID = ?";

        getJdbcTemplate().query(query, new Object[] { new Long(valModel.getCollateralId()) }, new ResultSetExtractor() {

            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    String guaranteeAmt = rs.getString("GUARANTEE_AMT");
                    String guaranteeCur = rs.getString("CURRENCY_CODE");
                    if ((guaranteeAmt != null) && (guaranteeCur != null)) {
                        Amount amt = new Amount(Double.parseDouble(guaranteeAmt), guaranteeCur);
                        gValModel.setGuaranteeAmount(amt);
                    }

                    String securedPortion = rs.getString("SECURED_PORTION");
                    String unsecuredPortion = rs.getString("UNSECURED_PORTION");

                    gValModel.setSecuredPortion((securedPortion == null) ? ICMSConstant.INT_INVALID_VALUE : Integer.parseInt(securedPortion));
                    gValModel.setUnsecuredPortion((unsecuredPortion == null) ? ICMSConstant.INT_INVALID_VALUE : Integer.parseInt(unsecuredPortion));

                    String securedAmt = rs.getString("SECURED_AMOUNT_ORIGIN");
                    String unsecuredAmt = rs.getString("UNSECURED_AMOUNT_ORIGIN");

                    if ((securedAmt != null) && (guaranteeCur != null)) {
                        Amount amt = new Amount(Double.parseDouble(securedAmt), guaranteeCur);
                        gValModel.setSecuredAmountOrigin(amt);
                    }

                    if ((unsecuredAmt != null) && (guaranteeCur != null)) {
                        Amount amt = new Amount(Double.parseDouble(unsecuredAmt), guaranteeCur);
                        gValModel.setUnsecuredAmountOrigin(amt);
                    }

                }
                return null;
            }
        });
    }


    public double getTotalFacOutstandingAmt(IValuationModel valModel) {
        
        String totalFacOutstandingAmt =
                "SELECT SUM(CONVERT_AMT(LMT.CMS_OUTSTANDING_AMT, LMT.LMT_CRRNCY_ISO_CODE, 'MYR')) TOTAL_OUTSTANDING " +
                "FROM SCI_LSP_APPR_LMTS LMT " +
                "WHERE (LMT.CMS_LIMIT_STATUS IS NULL OR CMS_LIMIT_STATUS = 'ACTIVE') " +
                "AND EXISTS (SELECT 1 FROM CMS_LIMIT_SECURITY_MAP LSM " +
                "              WHERE LSM.CMS_COLLATERAL_ID = ? " +
                "              AND LMT.CMS_LSP_APPR_LMTS_ID = LSM.CMS_LSP_APPR_LMTS_ID " +
                "              AND (LSM.UPDATE_STATUS_IND IS NULL OR LSM.UPDATE_STATUS_IND <> 'D')) ";



        Double outstandingAmt = (Double)getJdbcTemplate().query(totalFacOutstandingAmt, new Object[]
                                                                { new Long(valModel.getCollateralId()) },
                                                                new ResultSetExtractor() {

            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return new Double(rs.getDouble("TOTAL_OUTSTANDING"));
                }
                return new Double(0);
            }
        });

        return outstandingAmt.doubleValue();
    }


    public double getTotalSecOMVAmt(IValuationModel valModel) {

        String totalSecOmvAmt =
                "SELECT SUM(CONVERT_AMT(DISTINCT_SEC.CMV, DISTINCT_SEC.CMV_CURRENCY, 'MYR')) TOTAL_OMV FROM " +
                "(SELECT distinct SEC.CMS_COLLATERAL_ID, SEC.CMV, SEC.CMV_CURRENCY " +
                "FROM CMS_SECURITY SEC, CMS_LIMIT_SECURITY_MAP LSM " +
                "WHERE SEC.CMS_COLLATERAL_ID = LSM.CMS_COLLATERAL_ID " +
                "AND SEC.STATUS = 'ACTIVE' " +
                "AND IS_CGC_PLEDGED = 'Y' " +
                "AND SECURITY_SUB_TYPE_ID <> 'GT409' " +
                "AND (LSM.UPDATE_STATUS_IND IS NULL OR LSM.UPDATE_STATUS_IND <> 'D') " +
                "AND EXISTS (SELECT 1 FROM CMS_LIMIT_SECURITY_MAP LSM2 " +
                "              WHERE LSM2.CMS_COLLATERAL_ID = ? " +
                "              AND LSM.CMS_LSP_LMT_PROFILE_ID = LSM2.CMS_LSP_LMT_PROFILE_ID " +
                "              AND (LSM2.UPDATE_STATUS_IND IS NULL OR LSM2.UPDATE_STATUS_IND <> 'D'))) DISTINCT_SEC ";


        Double totalSecOMV = (Double)getJdbcTemplate().query(totalSecOmvAmt, new Object[]
                { new Long(valModel.getCollateralId()) }, new ResultSetExtractor() {

            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return new Double(rs.getDouble("TOTAL_OMV"));
                }
                return new Double(0);
            }
        });

        return totalSecOMV.doubleValue();
    }


    public void persistOtherInfo(IValuationModel valModel) {

        CGCValuationModel cgcModel = (CGCValuationModel)valModel;

        String updateSQL = "UPDATE CMS_GUARANTEE " +
                "SET SECURED_AMOUNT_CALC = ?, UNSECURED_AMOUNT_CALC = ?, GUARANTEE_AMT_CALC = ? " +
                "WHERE CMS_COLLATERAL_ID = ? ";

        getJdbcTemplate().update(updateSQL,
                new Object[] { new Double(cgcModel.getCalcSecuredAmount().getAmount()),
                               new Double(cgcModel.getCalcUnsecuredAmount().getAmount()),
                               new Double(cgcModel.getCalcTotalCgcCoverAmount().getAmount()),
                               new Long (cgcModel.getCollateralId()) } );
        
    }
}
