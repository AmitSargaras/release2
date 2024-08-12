/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/SBCollateralBusManagerStagingBean.java,v 1.6 2003/08/14 13:45:44 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.EBMFChecklistHome;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This session bean provides the implementation of the
 * AbstractCollateralManager, wrapped in an EJB mechanism.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/08/14 13:45:44 $ Tag: $Name: $
 */
public class SBCollateralBusManagerStagingBean extends SBCollateralBusManagerBean {
	/**
	 * Default Constructor
	 */
	public SBCollateralBusManagerStagingBean() {
	}

	/**
	 * helper method to get staging collateral home interface.
	 * 
	 * @return collateral home interface
	 * @throws CollateralException on errors encountered
	 */
	protected EBCollateralHome getEBCollateralHome() throws CollateralException {
		EBCollateralHome ejbHome = (EBCollateralHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_COLLATERAL_STAGING_JNDI, EBCollateralHome.class.getName());

		if (ejbHome == null) {
			throw new CollateralException("EBCollateralHome for staging is null!");
		}

		return ejbHome;
	}

	/**
	 * helper method to get home interface of EBValuationBean.
	 * 
	 * @return valuation home interface
	 * @throws CollateralException on errors encountered
	 */
	protected EBValuationHome getEBValuationHome() throws CollateralException {
		EBValuationHome ejbHome = (EBValuationHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_COL_VALUATION_STAGING_JNDI, EBValuationHome.class.getName());

		if (ejbHome == null) {
			throw new CollateralException("EBValuationHome for staging is null!");
		}

		return ejbHome;
	}

	/**
	 * Helper method to get home interface for collateral detail.
	 * 
	 * @param collateral of type ICollateral
	 * @return EBCollateralDetailHome
	 * @throws CollateralException on error getting the home interface
	 */
	protected EBCollateralDetailHome getEBCollateralDetailHome(ICollateral collateral) throws CollateralException {
		return CollateralDetailFactory.getEBStagingHome(collateral);
	}

	/**
	 * Get home interface of EBCollateralSubTypeBean.
	 * 
	 * @return collateral subtype home interface
	 * @throws CollateralException on errors encountered
	 */
	protected EBCollateralSubTypeHome getEBCollateralSubTypeHome() throws CollateralException {
		EBCollateralSubTypeHome ejbHome = (EBCollateralSubTypeHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_COL_SUBTYPE_STAGING_JNDI, EBCollateralSubTypeHome.class.getName());

		if (ejbHome == null) {
			throw new CollateralException("EBCollateralSubTypeStagingHome for STAGING is null!");
		}

		return ejbHome;
	}

	/**
	 * Helper method to get home interface of EBCollateralParameterBean.
	 * 
	 * @return collateral parameter home interface
	 * @throws CollateralException on errors encountered
	 */
	protected EBCollateralParameterHome getEBCollateralParameterHome() throws CollateralException {
		EBCollateralParameterHome home = (EBCollateralParameterHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_COLLATERAL_PARAMETER_STAGING_JNDI, EBCollateralParameterHome.class.getName());

		if (home == null) {
			throw new CollateralException("EBCollateralParameterStagingHome for STAGING is null!");
		}

		return home;
	}

	/**
	 * Helper method to get home interface of EBCollateralAssetLifeBean.
	 * 
	 * @return collateral asset life home interface
	 * @throws CollateralException on errors encountered
	 */
	protected EBCollateralAssetLifeHome getEBCollateralAssetLifeHome() throws CollateralException {
		EBCollateralAssetLifeHome ejbHome = (EBCollateralAssetLifeHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_COL_ASSETLIFE_STAGING_JNDI, EBCollateralAssetLifeHome.class.getName());

		if (ejbHome == null) {
			throw new CollateralException("EBCollateralAssetLifeStagingHome for STAGING is null!");
		}

		return ejbHome;
	}

	/**
	 * helper method to get home interface of EBPledgorBean.
	 * 
	 * @return pledgor home interface
	 * @throws CollateralException on errors encountered
	 */
	protected EBPledgorHome getEBPledgorHome() throws CollateralException {
		EBPledgorHome ejbHome = (EBPledgorHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_PLEDGOR_STAGING_JNDI,
				EBPledgorHome.class.getName());

		if (ejbHome == null) {
			throw new CollateralException("EBPledgorHome for STAGING is null!");
		}

		return ejbHome;
	}

	/**
	 * Helper method to get home interface of EBMFChecklistStagingBean.
	 * 
	 * @return MF checklist staging home interface
	 * @throws CollateralException on errors encountered
	 */
	protected EBMFChecklistHome getEBMFChecklistHome() throws CollateralException {
		EBMFChecklistHome ejbHome = (EBMFChecklistHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_MF_CHECKLIST_STAGING_JNDI, EBMFChecklistHome.class.getName());

		if (ejbHome == null) {
			throw new CollateralException("EBMFChecklistHome for STAGING is null!");
		}

		return ejbHome;
	}

}