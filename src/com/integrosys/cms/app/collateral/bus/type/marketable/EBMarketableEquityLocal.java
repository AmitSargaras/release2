/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/EBMarketableEquityLocal.java,v 1.2 2003/10/23 06:20:35 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable;

import javax.ejb.EJBLocalObject;

/**
 * Entity bean local interface for marketable equity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/10/23 06:20:35 $ Tag: $Name: $
 */
public interface EBMarketableEquityLocal extends EJBLocalObject {
	/**
	 * Get the marketable equity business object.
	 * 
	 * @return marketable equity
	 */
	public IMarketableEquity getValue();

	/**
	 * Set the marketable equity to this entity.
	 * 
	 * @param equity is of type IMarketableEquity
	 */
	public void setValue(IMarketableEquity equity);

	/**
	 * Set the item status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}