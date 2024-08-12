package com.integrosys.cms.app.collateral.bus.valuation.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashDeposit;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.CashValuationModel;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

public class CashValuationDAO extends GenericValuationDAO {
	public void retrieveValuationInfoForCal(IValuationModel valModel) throws ValuationException {
		CashValuationModel cValModel = (CashValuationModel) valModel;
		StringBuffer query = new StringBuffer();
		query.append("SELECT CASH_DEPOSIT_ID, DEPOSIT_AMOUNT, DEPOSIT_AMOUNT_CURRENCY FROM CMS_CASH_DEPOSIT ");
		query.append("WHERE CMS_COLLATERAL_ID = ? AND (STATUS IS NULL OR STATUS = ?) ");

		List argList = new ArrayList();
		argList.add(new Long(valModel.getCollateralId()));
		argList.add(ICMSConstant.STATE_ACTIVE);

		if (cValModel.getDepositList() != null && !cValModel.getDepositList().isEmpty()) {
			Long[] cashDepositRefId = (Long[]) getExistingDepositRefIds(cValModel.getDepositList());
			query.append(" AND cms_ref_id NOT ");

			CommonUtil.buildSQLInList(cashDepositRefId, query, argList);
		}

		List cashDepositList = getJdbcTemplate().query(query.toString(), argList.toArray(), new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				long depositId = rs.getLong("CASH_DEPOSIT_ID");
				double depositAmount = rs.getDouble("DEPOSIT_AMOUNT");
				String depositCur = rs.getString("DEPOSIT_AMOUNT_CURRENCY");

				ICashDeposit cashDeposit = new OBCashDeposit();
				cashDeposit.setCashDepositID(depositId);
				cashDeposit.setDepositAmount(new Amount(depositAmount, depositCur));

				return cashDeposit;
			}
		});

		cValModel.getDepositList().addAll(cashDepositList);
	}

	private Long[] getExistingDepositRefIds(List cashDepositList) {
		List existingDepositRefIds = new ArrayList();
		for (Iterator itr = cashDepositList.iterator(); itr.hasNext();) {
			ICashDeposit deposit = (ICashDeposit) itr.next();
			existingDepositRefIds.add(new Long(deposit.getRefID()));
		}
		return (Long[]) existingDepositRefIds.toArray(new Long[0]);
	}
}
