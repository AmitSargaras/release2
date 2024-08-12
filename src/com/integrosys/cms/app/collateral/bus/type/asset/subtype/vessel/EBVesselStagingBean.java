package com.integrosys.cms.app.collateral.bus.type.asset.subtype.vessel;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetailHome;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

public abstract class EBVesselStagingBean extends EBVesselBean {
	public EBCollateralDetailHome getChargeEJBHome() throws CollateralException {
		return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_CHARGE_STAGING_JNDI,
				EBCollateralDetailHome.class.getName());
	}

}
