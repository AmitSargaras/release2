/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBPreConditionLocal.java,v 1.1 2005/07/15 06:25:01 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import javax.ejb.EJBLocalObject;

/**
 * Entity bean local interface to EBPreConditionBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/07/15 06:25:01 $ Tag: $Name: $
 */
public interface EBPreConditionLocal extends EJBLocalObject {
	/**
	 * Get the pre-condition business object.
	 * 
	 * @return pre condition
	 */
	public IPreCondition getValue();

	/**
	 * Set the pre-condition business object.
	 * 
	 * @param preCondition is of type IPreCondition
	 */
	public void setValue(IPreCondition preCondition);
}