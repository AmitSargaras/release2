package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargevehicle;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetailHome;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.ITradeInInfo;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBTradeInInfo;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.TradeInInfoDAO;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

public abstract class EBSpecificChargeVehicleStagingBean extends EBSpecificChargeVehicleBean {

	private static final long serialVersionUID = 1893149193798135440L;

	public EBCollateralDetailHome getChargeEJBHome() throws CollateralException {
		return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_CHARGE_STAGING_JNDI,
				EBCollateralDetailHome.class.getName());
	}

	public void setTradeInInfo(ISpecificChargeVehicle vehicle) {
		ITradeInInfo[] tradeInInfos = vehicle.getTradeInInfo();
		if (tradeInInfos == null || tradeInInfos.length == 0) {
			return;
		}
		TradeInInfoDAO dao = (TradeInInfoDAO) BeanHouse.get("tradeInInfoDAO");

		for (int i = 0; i < tradeInInfos.length; i++) {
			// the trade in info actually belong to staging collateral, do
			// direct update
			if (tradeInInfos[i].getCollateralId() == vehicle.getCollateralID() && tradeInInfos[i].getId() != null) {
				ITradeInInfo[] storedTradeInInfos = dao.getTradeInInfoByCollId(getTradeInEntityName(), vehicle
						.getCollateralID());
				if (storedTradeInInfos != null && storedTradeInInfos.length != 0) {
					for (int j = 0; j < storedTradeInInfos.length; j++) {
						if (tradeInInfos[i].getId().equals(storedTradeInInfos[j].getId())) {
							AccessorUtil.copyValue(tradeInInfos[i], storedTradeInInfos[j],
									new String[] { "versionTime" });
							dao.saveOrUpdateTradeInInfo(getTradeInEntityName(), storedTradeInInfos[j]);
						}
					}
				}
			}
			else {
				OBTradeInInfo info = new OBTradeInInfo();
				AccessorUtil.copyValue(tradeInInfos[i], info, TRADE_IN_METHOD);
				info.setCollateralId(vehicle.getCollateralID());

				tradeInInfos[i] = null;

				info.setCollateralId(vehicle.getCollateralID());
				dao.saveOrUpdateTradeInInfo(getTradeInEntityName(), info);

				tradeInInfos[i] = info;
			}
		}
	}

	public String getTradeInEntityName() {
		return getStageTradeInEntityName();
	}
}
