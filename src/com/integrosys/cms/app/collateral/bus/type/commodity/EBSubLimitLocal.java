/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBSubLimitLocal.java,v 1.2 2004/08/18 08:00:51 wltan Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.commodity;

/**
 * Local interface for sub-limit entity bean.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/08/18 08:00:51 $ Tag: $Name: $
 */
public interface EBSubLimitLocal extends javax.ejb.EJBLocalObject {

	/**
	 * Get sub-limit business object.
	 * 
	 * @return ISubLimit
	 */
	public ISubLimit getValue();

	/**
	 * Persist newly updated sub-limit.
	 * 
	 * @param subLimit of type ISubLimit
	 */
	public void setValue(ISubLimit subLimit);

	/**
	 * Set status of this sub-limit.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}