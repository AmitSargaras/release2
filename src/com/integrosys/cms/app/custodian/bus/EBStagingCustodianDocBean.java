/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/EBStagingCustodianDocBean.java,v 1.3 2005/03/02 10:33:34 lini Exp $
 */
package com.integrosys.cms.app.custodian.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * staging custodian doc entity bean
 * 
 * @author $Author: lini $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/03/02 10:33:34 $ Tag: $Name: $
 */
public abstract class EBStagingCustodianDocBean extends EBCustodianDocBean {

	/**
	 * Default Constructor
	 */
	public EBStagingCustodianDocBean() {
	}

	/**
	 * Method to get EB Home for staging custodian doc item
	 */
	protected EBCustodianDocItemHome getEBCustodianDocItemHome() throws CustodianException {
		EBCustodianDocItemHome home = (EBCustodianDocItemHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_CUSTODIAN_DOC_ITEM_HOME, EBCustodianDocItemHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CustodianException("EBStagingCustodianDocItemHome is null!");
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
