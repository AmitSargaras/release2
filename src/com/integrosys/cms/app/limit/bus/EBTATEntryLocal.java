/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBTATEntryLocal.java,v 1.1 2003/07/15 08:24:49 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import javax.ejb.EJBLocalObject;

/**
 * This is the remote interface to the EBTATEntryLocal entity bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/15 08:24:49 $ Tag: $Name: $
 */
public interface EBTATEntryLocal extends EJBLocalObject {
	/**
	 * Get an object representation from persistance
	 * 
	 * @return ITATEntry
	 */
	public ITATEntry getValue();

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type ITATEntry
	 * @throws LimitException on errors
	 */
	public void setValue(ITATEntry value) throws LimitException;
}