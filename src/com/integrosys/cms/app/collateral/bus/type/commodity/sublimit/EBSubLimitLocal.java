/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/sublimit/EBSubLimitLocal.java,v 1.1 2005/10/06 05:49:52 hmbao Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.commodity.sublimit;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-14
 * @Tag : com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.
 *      EBSubLimitLocal.java
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
	public void setValue(ISubLimit subLimit) throws VersionMismatchException;

}