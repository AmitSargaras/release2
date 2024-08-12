package com.integrosys.cms.app.collateral.bus.valuation.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.OBPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.PDCValuationModel;

public class PDCValuationDAO extends GenericValuationDAO {

	public void retrieveValuationInfoForCal(IValuationModel valModel) throws ValuationException {
		PDCValuationModel pdcValModel = (PDCValuationModel) valModel;
		String query = "SELECT CHEQUE_AMOUNT, CHEQUE_AMOUNT_CURRENCY FROM CMS_ASSET_PDC WHERE CMS_COLLATERAL_ID = ?";

		List postDatedChequeList = (List) getJdbcTemplate().query(query,
				new Object[] { new Long(valModel.getCollateralId()) }, new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List resultList = new ArrayList();

						String chequeAmt = rs.getString("CHEQUE_AMOUNT");
						String chequeCur = rs.getString("CHEQUE_AMOUNT_CURRENCY");
						if ((chequeAmt != null) && (chequeCur != null)) {
							IPostDatedCheque cheque = new OBPostDatedCheque();
							cheque.setChequeAmount(new Amount(Double.parseDouble(chequeAmt), chequeCur));
							resultList.add(cheque);
						}

						return resultList;
					}

				});

		pdcValModel.getPostDatedChequeList().addAll(postDatedChequeList);

	}
}
