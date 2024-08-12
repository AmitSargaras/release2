package com.integrosys.cms.app.collateral.bus.type.asset.subtype;

import java.io.Serializable;

public interface TradeInInfoDAO extends Serializable {

	public ITradeInInfo saveOrUpdateTradeInInfo(String entityName,
			ITradeInInfo tradeInInfo);

	public void deleteTradeInInfoByCollId(String entityName,long collateralId);

	public ITradeInInfo[] getTradeInInfoByCollId(String entityName,
			long collateralId);
}
