/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/creditrixkparam/bus/EBCreditRiskParamLocal.java,v 1.1 2007/02/12 08:34:09 shphoon Exp $
 */
package com.integrosys.cms.app.creditriskparam.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * EBCreditRiskParamLocal Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBCreditRiskParamLocal extends EJBLocalObject {

	/**
	 * Returns the primary key
	 * @return String - the primary key ID
	 */
	long getParameterId();

	long getFeedId();

	long getParameterRef();

	/**
	 * Returns the value object representing this entity bean
	 */
	ICreditRiskParam getValue();

	/**
	 * Sets the entity using its corresponding value object.
	 */
	void setValue(ICreditRiskParam aICreditRiskParam) throws ConcurrentUpdateException;
}
