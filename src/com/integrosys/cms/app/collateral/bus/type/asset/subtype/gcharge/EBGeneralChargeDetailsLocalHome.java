/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBDebtorLocalHome.java,v 1.1 2005/03/15 03:40:46 htli Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/*
 * This is EBGeneralChargeDetailsLocalHome interface
 */

public interface EBGeneralChargeDetailsLocalHome extends EJBLocalHome {

	/**
	 * Create a GeneralChargeDetails
	 * 
	 * 
	 * @param value is the IGeneralChargeDetails object
	 * @return EBBankingMethodLocal
	 * @throws CreateException on error
	 */
	public EBGeneralChargeDetailsLocal create(IGeneralChargeDetails value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is String value of the contact ID
	 * @return EBGeneralChargeDetailsLocal
	 * @throws FinderException on error
	 */
	public EBGeneralChargeDetailsLocal findByPrimaryKey(Long pk) throws FinderException;

}
