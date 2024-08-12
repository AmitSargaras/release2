package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for staging asset of type GeneralChargeDetails
 * 
 */
public abstract class EBGeneralChargeDetStgBean extends EBGeneralChargeDetailsBean {
	
	protected EBGeneralChargeStockDetailsLocalHome getEBLocalHomeGeneralChargeStockDetails() throws GeneralChargeException {
		EBGeneralChargeStockDetailsLocalHome home = (EBGeneralChargeStockDetailsLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_GENERALCHARGESTOCKDETAILS_LOCAL_STAGING_JNDI, EBGeneralChargeStockDetailsLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new GeneralChargeException("EBGeneralChargeStockDetailsLocalHomeStaging is null!");
		}
	}
	
	protected EBLeadBankStockLocalHome getEBLeadBankStockLocalHome() throws GeneralChargeException {
		EBLeadBankStockLocalHome home = (EBLeadBankStockLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LEAD_BANK_STOCK_STAGING_JNDI, EBLeadBankStockLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new GeneralChargeException("EBLeadBankStockStagingLocalHome is null!");
		}
	}
	
}