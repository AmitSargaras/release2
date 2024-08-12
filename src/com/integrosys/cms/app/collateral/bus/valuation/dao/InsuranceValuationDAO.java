package com.integrosys.cms.app.collateral.bus.valuation.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.InsuranceValuationModel;

public class InsuranceValuationDAO extends GenericValuationDAO {
	public void retrieveValuationInfoForCal(IValuationModel valModel) throws ValuationException {
		final InsuranceValuationModel iValModel = (InsuranceValuationModel) valModel;
		String query = "SELECT INSURED_AMOUNT, INSURED_AMT_CURR FROM CMS_INSURANCE WHERE CMS_COLLATERAL_ID = ?";

		getJdbcTemplate().query(query, new Object[] { new Long(valModel.getCollateralId()) }, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					String insuredAmt = rs.getString("INSURED_AMOUNT");
					String insuredCur = rs.getString("INSURED_AMT_CURR");
					if ((insuredAmt != null) && (insuredCur != null)) {
						Amount amt = new Amount(Double.parseDouble(insuredAmt), insuredCur);
						iValModel.setInsuredAmount(amt);
					}
				}
				return null;
			}
		});

	}
}
