/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBGeneralChargeStagingBean.java,v 1.2 2005/08/12 02:15:13 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for staging asset of type general charge.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/12 02:15:13 $ Tag: $Name: $
 */
public abstract class EBGeneralChargeStagingBean extends EBGeneralChargeBean {
	/**
	 * Get Stock local home
	 * 
	 * @return EBStockLocalHome
	 */
	protected EBStockLocalHome getEBStockLocalHome() {
		EBStockLocalHome ejbHome = (EBStockLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STOCK_STAGING_LOCAL_JNDI, EBStockLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBStockLocalHome for staging is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get Debtor local home
	 * 
	 * @return EBDebtorLocalHome
	 */
	protected EBDebtorLocalHome getEBDebtorLocalHome() {
		EBDebtorLocalHome ejbHome = (EBDebtorLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_DEBTOR_STAGING_LOCAL_JNDI, EBDebtorLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBDebtorLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get FAO local home
	 * 
	 * @return EBFixedAssetOthersLocalHome
	 */
	protected EBFixedAssetOthersLocalHome getEBFixedAssetOthersLocalHome() {
		EBFixedAssetOthersLocalHome ejbHome = (EBFixedAssetOthersLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_FAO_STAGING_LOCAL_JNDI, EBFixedAssetOthersLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBFixedAssetOthersLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get insurance stock map local home
	 * 
	 * @return EBInsuranceStockMapLocalHome
	 */
	protected EBInsuranceStockMapLocalHome getEBInsuranceStockMapLocalHome() {
		EBInsuranceStockMapLocalHome ejbHome = (EBInsuranceStockMapLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_INS_STOCK_MAP_STAGING_LOCAL_JNDI, EBInsuranceStockMapLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBInsuranceStockMapLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get insurance fao map local home
	 * 
	 * @return EBInsuranceFaoMapLocalHome
	 */
	protected EBInsuranceFaoMapLocalHome getEBInsuranceFaoMapLocalHome() {
		EBInsuranceFaoMapLocalHome ejbHome = (EBInsuranceFaoMapLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_INS_FAO_MAP_STAGING_LOCAL_JNDI, EBInsuranceFaoMapLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBInsuranceFaoMapLocalHome is Null!");
		}

		return ejbHome;
	}
	
	
	protected EBGeneralChargeDetailsLocalHome getEBLocalHomeGeneralChargeDetails() throws GeneralChargeException {
		EBGeneralChargeDetailsLocalHome home = (EBGeneralChargeDetailsLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_GENERALCHARGEDETAILS_LOCAL_STAGING_JNDI, EBGeneralChargeDetailsLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new GeneralChargeException("EBGeneralChargeDetailsLocalHome --Stagging--is null!");
		}
	}
	
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

}