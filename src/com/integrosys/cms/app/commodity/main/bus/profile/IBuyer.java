/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/IBuyer.java,v 1.3 2004/08/12 03:14:49 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.profile;

/**
 * @author dayanand $
 * @version $
 * @since $Date: 2004/08/12 03:14:49 $ Tag: $Name: $
 */

public interface IBuyer extends java.io.Serializable {

	public long getBuyerID();

	public String getName();

	public long getCommonReferenceID();

	public void setCommonReferenceID(long commonReferenceID);

	public String getStatus();

	public void setStatus(String status);
}