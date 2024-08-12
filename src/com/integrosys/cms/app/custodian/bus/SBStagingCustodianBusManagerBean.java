/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/SBStagingCustodianBusManagerBean.java,v 1.5 2005/03/02 10:31:16 lini Exp $
 */
package com.integrosys.cms.app.custodian.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Session bean implementation of the services provided by the custodian bus
 * manager. This will only contains the persistance logic.
 * 
 * @author $Author: lini $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/03/02 10:31:16 $ Tag: $Name: $
 */
public class SBStagingCustodianBusManagerBean extends SBCustodianBusManagerBean {
	/**
	 * Default constructor.
	 */
	public SBStagingCustodianBusManagerBean() {
	}

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(javax.ejb.SessionContext sc) {
	}

	/**
	 * Update a custodian that is already in the custodian registry.
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the updated custodian doc
	 * @throws ConcurrentUpdateException
	 * @throws CustodianException
	 */
	public ICustodianDoc update(ICustodianDoc anICustodianDoc) throws ConcurrentUpdateException, CustodianException {
		throw new CustodianException("This method is not applicable to staging data !!!");
	}

	/**
	 * Delete a custodian document from the custodian registry. This will
	 * perform a soft delete
	 * @param aCustodianDocID - long
	 * @throws ConcurrentUpdateException
	 * @throws CustodianException
	 */
	public void delete(long aCustodianDocID) throws ConcurrentUpdateException, CustodianException {
		throw new CustodianException("This method is not applicable to staging data !!!");
	}

	/**
	 * To get the home handler for the Custodian Doc Entity Bean
	 * @return EBCustodianDocLocalHome - the home handler for the custodian doc
	 *         entity bean
	 */
	protected EBCustodianDocLocalHome getEBCustodianDocLocalHome() {
		EBCustodianDocLocalHome ejbHome = (EBCustodianDocLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_CUSTODIAN_DOC_LOCAL_HOME, EBCustodianDocLocalHome.class.getName());
		return ejbHome;
	}
}