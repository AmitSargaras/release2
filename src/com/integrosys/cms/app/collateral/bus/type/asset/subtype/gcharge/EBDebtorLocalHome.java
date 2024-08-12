/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBDebtorLocalHome.java,v 1.1 2005/03/15 03:40:46 htli Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/*
 * This is EBDebtorLocalHome interface
 * @author $Author: htli $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/03/15 03:40:46 $
 * Tag: $Name:  $
 */

public interface EBDebtorLocalHome extends EJBLocalHome {

	/**
	 * Create a new debtor.
	 * 
	 * @param debtor of type IDebtor
	 * @return local debtor ejb object
	 * @throws CreateException on error creating the ejb object
	 */
	public EBDebtorLocal create(IDebtor debtor) throws CreateException;

	/**
	 * Find the debtor entity bean by its primary key.
	 * 
	 * @param debtorID debtor id
	 * @return local debtor ejb object
	 * @throws FinderException on error finding the ejb object
	 */
	public EBDebtorLocal findByPrimaryKey(Long debtorID) throws FinderException;

}
