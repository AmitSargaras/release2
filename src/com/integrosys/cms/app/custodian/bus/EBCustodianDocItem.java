/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/EBCustodianDocItem.java,v 1.3 2005/03/10 04:34:34 wltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * custodian doc item local interface
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/03/10 04:34:34 $ Tag: $Name: $
 */
public interface EBCustodianDocItem extends EJBLocalObject {
	/**
	 * Retrieve an instance of a custodian document item
	 * @return ICustodianDocItem - the object encapsulating the custodian doc
	 *         item info
	 * @throws CustodianException
	 */
	public ICustodianDocItem getValue() throws CustodianException;

	/**
	 * Set the custodian doc item object
	 * @param anICustodianDocItem - ICustodianDocItem
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(ICustodianDocItem anICustodianDocItem) throws ConcurrentUpdateException;

}
