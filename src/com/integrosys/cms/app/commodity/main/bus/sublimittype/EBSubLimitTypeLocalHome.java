/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/sublimittype/EBSubLimitTypeLocalHome.java,v 1.1 2005/10/06 03:39:36 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.sublimittype;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-16
 * @Tag com.integrosys.cms.app.commodity.main.bus.sublimittype.
 *      EBSubLimitTypeLocalHome.java
 */
public interface EBSubLimitTypeLocalHome extends EJBLocalHome {

	public EBSubLimitTypeLocal create(ISubLimitType sltOB) throws CreateException;

	public EBSubLimitTypeLocal findByPrimaryKey(Long theID) throws FinderException;

	public Collection findAll() throws FinderException;

	public Collection findByGroupID(Long groupID) throws FinderException;

	// public EBSubLimitType findByCommonRef (Long commonRef) throws
	// FinderException, RemoteException;
	// public EBSubLimitType findByGroupIDCommonRef (Long groupID, Long
	// commonRef) throws FinderException, RemoteException;
}
