/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/EBBuyerLocal.java,v 1.2 2004/06/04 04:53:01 hltan Exp $
 */

package com.integrosys.cms.app.commodity.main.bus.profile;

/**
 * @author dayanand $
 * @version $
 * @since $Date: 2004/06/04 04:53:01 $ Tag: $Name: $
 */

public interface EBBuyerLocal extends javax.ejb.EJBLocalObject {

	public OBBuyer getValue();

	public void setValue(OBBuyer contents);

}