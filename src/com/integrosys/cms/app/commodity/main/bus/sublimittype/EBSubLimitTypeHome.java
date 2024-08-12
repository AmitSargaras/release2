/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/sublimittype/EBSubLimitTypeHome.java,v 1.1 2005/10/06 03:39:36 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.sublimittype;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-16
 * @Tag 
 *      com.integrosys.cms.app.commodity.main.bus.sublimittype.EBSubLimitTypeHome
 *      .java
 */
public interface EBSubLimitTypeHome extends EJBHome {

	public EBSubLimitType create(ISubLimitType sltOB) throws CreateException, RemoteException;

	public EBSubLimitType findByPrimaryKey(Long theID) throws FinderException, RemoteException;

	public Collection findAll() throws FinderException, RemoteException;

	public Collection findByGroupID(Long groupID) throws FinderException, RemoteException;

	// public EBSubLimitType findByCommonRef (Long commonRef) throws
	// FinderException, RemoteException;
	// public EBSubLimitType findByGroupIDCommonRef (Long groupID, Long
	// commonRef) throws FinderException, RemoteException;
}
