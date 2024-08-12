/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/dataprotection/bus/EBCollateralMetaData.java,v 1.1 2003/06/19 13:18:29 jtan Exp $
 */
package com.integrosys.cms.app.dataprotection.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Purpose: Entity Bean Remote Interface Description:
 * 
 * @author $jtan$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 * 
 */
public interface EBCollateralMetaData extends EJBObject {
	/**
	 * Retrieves meta data information of a field belonging to a subtype
	 * @return A value object with subtype field information
	 * @throws RemoteException
	 */
	public ICollateralMetaData getCollateralMetaData() throws RemoteException;

}
