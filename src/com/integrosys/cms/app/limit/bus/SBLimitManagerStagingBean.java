/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/SBLimitManagerStagingBean.java,v 1.4 2005/09/08 06:44:14 lyng Exp $
 */
package com.integrosys.cms.app.limit.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.EBCustomerSysXRefHome;

/**
 * This session
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/09/08 06:44:14 $ Tag: $Name: $
 */
public class SBLimitManagerStagingBean extends SBLimitManagerBean {
	/**
	 * Default Constructor
	 */
	public SBLimitManagerStagingBean() {
	}

	/**
	 * Create Limit Profile. This method does not create the limits details.
	 * 
	 * @param value is of type ILimitProfile
	 * @return ILimitProfile which exclude dependants.
	 * @throws LimitException on errors
	 */
	public ILimitProfile createLimitProfile(ILimitProfile value) throws LimitException {
		try {

			EBLimitProfileHome home = getEBHomeLimitProfile();
			EBLimitProfile rem = home.create(value);

			long verTime = rem.getVersionTime();
			// create child dependencies with checking on version time
			rem.createDependants(value, verTime);

			return rem.getValue(false);
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Update limit profile information.
	 * 
	 * @param value is of type ILimitProfile
	 * @return ILimitProfile which includes dependants
	 * @throws LimitException on errors
	 */
	public ILimitProfile updateLimitProfile(ILimitProfile value) throws LimitException {
		try {
			if (null == value) {
				throw new LimitException("ILimit is null!");
			}

			EBLimitProfileHome home = getEBHomeLimitProfile();
			EBLimitProfile rem = home.findByPrimaryKey(new Long(value.getLimitProfileID()));
			rem.setValue(value);

			return rem.getValue(true);
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.SBLimitManager#removeLimitProfile
	 */
	public ILimitProfile removeLimitProfile(ILimitProfile value) throws LimitException {
		try {
			if (null == value) {
				throw new LimitException("ILimit is null!");
			}
			EBLimitProfileHome home = getEBHomeLimitProfile();
			EBLimitProfile rem = home.findByPrimaryKey(new Long(value.getLimitProfileID()));

			// do soft delete
			rem.setStatusDeleted(value);

			return rem.getValue(true);
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	// ************* Protected Methods **************
	/**
	 * Method to get the EBHome for Limit Profile
	 * 
	 * @return EBLimitProfileHome
	 * @throws LimitException on error
	 */
	protected EBLimitProfileHome getEBHomeLimitProfile() throws LimitException {
		EBLimitProfileHome home = (EBLimitProfileHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_LIMIT_PROFILE_JNDI_STAGING, EBLimitProfileHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBLimitProfileHome is null!");
		}
	}

	/**
	 * Method to get the EBHome for Limit
	 * 
	 * @return EBLimitHome
	 * @throws LimitException on error
	 */
	protected EBLimitHome getEBHomeLimit() throws LimitException {
		EBLimitHome home = (EBLimitHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_LIMIT_JNDI_STAGING,
				EBLimitHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBLimitHome is null!");
		}
	}

	/**
	 * Method to get the staging limit local home interface.
	 * 
	 * @return EBLimitLocalHome
	 * @throws LimitException on error getting the staging limit local home
	 */
	protected EBLimitLocalHome getEBLimitLocalHome() throws LimitException {
		EBLimitLocalHome ejbHome = (EBLimitLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LIMIT_LOCAL_JNDI_STAGING, EBLimitLocalHome.class.getName());

		if (ejbHome == null) {
			throw new LimitException("EBLimitLocalHome for staging is NULL");
		}
		return ejbHome;
	}

	/**
	 * Method to get EB Local Home for EBCoBorrowerLimitHome
	 * 
	 * @return EBCoBorrowerLimitHome
	 * @throws LimitException on errors
	 */
	protected EBCoBorrowerLimitHome getEBHomeCoBorrowerLimit() throws LimitException {
		EBCoBorrowerLimitHome home = (EBCoBorrowerLimitHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_COBORROWER_LIMIT_JNDI_STAGING, EBCoBorrowerLimitHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBCoBorrowerLimitHome is null!");
		}
	}

	/**
	 * Method to get the local home interface for staging coborrower limit.
	 * 
	 * @return EBCoBorrowerLimitLocalHome
	 * @throws LimitException on error getting the staging coborrower limit
	 *         local home
	 */
	protected EBCoBorrowerLimitLocalHome getEBCoBorrowerLimitLocalHome() throws LimitException {
		EBCoBorrowerLimitLocalHome ejbHome = (EBCoBorrowerLimitLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COBORROWER_LIMIT_LOCAL_JNDI_STAGING, EBCoBorrowerLimitLocalHome.class.getName());

		if (ejbHome == null) {
			throw new LimitException("EBCoBorrowerLimitLocalHome for staging is NULL");
		}
		return ejbHome;
	}

	protected EBCustomerSysXRefHome getEBCustomerSysXRefHome() throws LimitException {
		EBCustomerSysXRefHome ejbHome = (EBCustomerSysXRefHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_CUSTOMER_SYS_REF_JNDI_STAGING, EBCustomerSysXRefHome.class.getName());

		if (ejbHome == null) {
			throw new LimitException("EBCustomerSysXRefLocalHome is NULL");
		}
		return ejbHome;
	}
}