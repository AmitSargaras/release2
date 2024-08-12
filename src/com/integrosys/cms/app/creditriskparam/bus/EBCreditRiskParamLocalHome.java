/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/creditrixkparam/bus/EBCreditRiskParamLocalHome.java,v 1.1 2007/02/12 08:34:09 shphoon Exp $
 */
package com.integrosys.cms.app.creditriskparam.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * EBCreditRiskParamLocalHome Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBCreditRiskParamLocalHome extends EJBLocalHome {

	/**
	 * Creates a credit risk param entry
	 * @param aICreditRiskParam ICreditRiskParam
	 * @throws javax.ejb.CreateException is error at record creation
	 * @return String - the primary key ID
	 */
	EBCreditRiskParamLocal create(ICreditRiskParam aICreditRiskParam) throws CreateException;

	/*
	 * Find by primary key which is the CreditRiskParamEntry's ID
	 * 
	 * @param aICreditRiskParamID long
	 * 
	 * @return EBCreditRiskParam
	 * 
	 * @throws FinderException if not found
	 * 
	 * @throws RemoteException on run-time errors
	 */
	EBCreditRiskParamLocal findByPrimaryKey(Long aICreditRiskParamID) throws FinderException;
}
