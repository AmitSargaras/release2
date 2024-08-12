/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBLoanLimitLocalHome.java,v 1.2 2004/08/18 08:00:51 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Defines loan agency limit create and finder methods for local clients.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/08/18 08:00:51 $ Tag: $Name: $
 */
public interface EBLoanLimitLocalHome extends EJBLocalHome {

	/**
	 * Create loan agency limit.
	 * 
	 * @param loanLimit of type ILoanLimit
	 * @return local limit ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBLoanLimitLocal create(ILoanLimit loanLimit) throws CreateException;

	/**
	 * Find loan agency limit by its primary key, the subLimit id.
	 * 
	 * @param key limit id
	 * @return local limit ejb object
	 * @throws FinderException on error finding the limit
	 */
	public EBLoanLimitLocal findByPrimaryKey(Long key) throws FinderException;
}
