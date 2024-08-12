package com.integrosys.cms.batch.reports;

import javax.ejb.EJBLocalObject;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

/**
 * Description:
 * 
 * @author $Author: ritika $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/27 03:40:41 $ Tag: $Name: $
 */

public interface EBReportRequestLocal extends EJBLocalObject {
	/**
	 * Get an object representation from persistance
	 * @return IReportRequest
	 */
	public IReportRequest getValue();

	/**
	 * 
	 * @param value is of type IReportRequest
	 */
	public void setValue(IReportRequest value);

}
