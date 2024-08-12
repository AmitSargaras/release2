/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/EBSupplierLocalHome.java,v 1.2 2004/06/04 04:53:01 hltan Exp $
 */

package com.integrosys.cms.app.commodity.main.bus.profile;

import java.util.Collection;

/**
 * @author dayanand $
 * @version $
 * @since $Date: 2004/06/04 04:53:01 $ Tag: $Name: $
 */

public interface EBSupplierLocalHome extends javax.ejb.EJBLocalHome {
	public EBSupplierLocal create(OBSupplier contents) throws javax.ejb.CreateException;

	public EBSupplierLocal findByPrimaryKey(java.lang.Long primaryKey) throws javax.ejb.FinderException;

	public Collection findAll() throws javax.ejb.FinderException;
}