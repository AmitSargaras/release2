/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/EBCustodianDocItemHome.java,v 1.2 2005/02/01 03:31:04 whuang Exp $
 */
package com.integrosys.cms.app.custodian.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * custodian doc local home interface
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/02/01 03:31:04 $ Tag: $Name: $
 */
public interface EBCustodianDocItemHome extends EJBLocalHome {
	/**
	 * Create custodian doc item
	 * @param ICustodianDocItem - ICustodianDocItem
	 * @return ICustodianDocItem
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBCustodianDocItem create(ICustodianDocItem item) throws CreateException;

	/**
	 * Find by primary Key, the custodian doc item ID
	 * @param aPK - Long
	 * @return EBCustodianDocItem - the remote handler for the custodian doc
	 *         item that has the PK as specified
	 * @throws FinderException
	 * @throws RemoteException
	 */
	public EBCustodianDocItem findByPrimaryKey(Long aPK) throws FinderException;

}
