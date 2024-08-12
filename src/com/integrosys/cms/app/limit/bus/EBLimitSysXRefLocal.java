/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBLimitSysXRefLocal.java,v 1.3 2003/07/30 05:58:40 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import javax.ejb.EJBLocalObject;

/**
 * This is the remote interface to the EBLimitSysXRefLocal entity bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/30 05:58:40 $ Tag: $Name: $
 */
public interface EBLimitSysXRefLocal extends EJBLocalObject {
	/**
	 * Get the xref ID
	 * 
	 * @return long
	 */
	public long getXRefID();

	/**
	 * Get the SID
	 * 
	 * @return long
	 */
	public long getSID();

	/**
	 * Get status
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Get an object representation from persistance
	 * 
	 * @return ILimitSysXRef
	 * @throws LimitException on error
	 */
	public ILimitSysXRef getValue() throws LimitException;

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type ILimitSysXRef
	 * @throws LimitException on error
	 */
	public void setValue(ILimitSysXRef value) throws LimitException;

	/**
	 * Set status
	 * 
	 * @param value is of type String
	 */
	public void setStatus(String value);
}