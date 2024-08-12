/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/EBSupplierLocal.java,v 1.2 2004/06/04 04:53:01 hltan Exp $
 */

package com.integrosys.cms.app.commodity.main.bus.profile;

/**
 * @author dayanand $
 * @version $
 * @since $Date: 2004/06/04 04:53:01 $ Tag: $Name: $
 */

public interface EBSupplierLocal extends javax.ejb.EJBLocalObject {

	public OBSupplier getValue();

	public void setValue(OBSupplier contents);

}