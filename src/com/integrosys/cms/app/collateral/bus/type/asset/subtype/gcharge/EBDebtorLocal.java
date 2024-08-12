/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBDebtorLocal.java,v 1.2 2005/03/17 03:20:09 wltan Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.EJBLocalObject;

/*
 * This is EBDebtorLocal interface
 * @author $Author: wltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/03/17 03:20:09 $
 * Tag: $Name:  $
 */

public interface EBDebtorLocal extends EJBLocalObject {

	/**
	 * Get the debtor business object.
	 * 
	 * @param debtor of type IDebtor
	 * @return debtor
	 */
	public IDebtor getValue();

	/**
	 * Set the debtor business object.
	 * 
	 * @param debtor is of type IDebtor
	 */
	public void setValue(IDebtor debtor);

}
