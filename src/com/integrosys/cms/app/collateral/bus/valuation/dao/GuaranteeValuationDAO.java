/*
 * Created on May 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus.valuation.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.GuaranteeValuationModel;

public class GuaranteeValuationDAO extends GenericValuationDAO {
	public void retrieveValuationInfoForCal(IValuationModel valModel) throws ValuationException {
		final GuaranteeValuationModel gValModel = (GuaranteeValuationModel) valModel;
		String query = "SELECT GUARANTEE_AMT, CURRENCY_CODE FROM CMS_GUARANTEE WHERE CMS_GUARANTEE.CMS_COLLATERAL_ID = ?";

		getJdbcTemplate().query(query, new Object[] { new Long(valModel.getCollateralId()) }, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					String guaranteeAmt = rs.getString("GUARANTEE_AMT");
					String guaranteeCur = rs.getString("CURRENCY_CODE");
					if ((guaranteeAmt != null) && (guaranteeCur != null)) {
						Amount amt = new Amount(Double.parseDouble(guaranteeAmt), guaranteeCur);
						gValModel.setGuaranteeAmount(amt);
					}
				}
				return null;
			}
		});

	}
}
