/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/sublimit/EBSubLimitLocalHome.java,v 1.1 2005/10/06 05:49:52 hmbao Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity.sublimit;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-14
 * @Tag : com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.
 *      ListSubLimitCommand.java
 */
public interface EBSubLimitLocalHome extends javax.ejb.EJBLocalHome {
	public EBSubLimitLocal create(ISubLimit subLimit) throws CreateException;

	public EBSubLimitLocal findByPrimaryKey(Long primaryKey) throws FinderException;

	public Collection findAll() throws FinderException;
}