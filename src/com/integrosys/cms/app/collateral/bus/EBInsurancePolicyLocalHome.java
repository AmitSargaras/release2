/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBInsurancePolicyLocalHome.java,v 1.1 2005/08/08 06:05:48 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Entity bean local home interface for insurance policy. It defines methods for
 * local clients to create/find the insurance policy.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/08/08 06:05:48 $ Tag: $Name: $
 */
public interface EBInsurancePolicyLocalHome extends EJBLocalHome {
	/**
	 * Create a new insurance policy.
	 * 
	 * @param insurance of type IInsurancePolicy
	 * @return local insurance ejb object
	 * @throws CreateException on error creating the ejb object
	 */
	public EBInsurancePolicyLocal create(IInsurancePolicy insurance) throws CreateException;

	/**
	 * Find the insurance policy entity bean by its primary key.
	 * 
	 * @param insuranceID insurance id
	 * @return local insurance ejb object
	 * @throws FinderException on error finding the ejb object
	 */
	public EBInsurancePolicyLocal findByPrimaryKey(Long insuranceID) throws FinderException;
}