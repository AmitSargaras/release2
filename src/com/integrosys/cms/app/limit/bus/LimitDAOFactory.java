/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/LimitDAOFactory.java,v 1.1 2006/01/24 07:58:30 vishal Exp $
 */
package com.integrosys.cms.app.limit.bus;

import com.integrosys.base.techinfra.context.BeanHouse;

/**
 * This factory class will load <tt>ILimitDAO</tt> implementations.
 * 
 * @author $Author: vishal $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2006/01/24 07:58:30 $ Tag: $Name: $
 */
public class LimitDAOFactory {

	public static ILimitDAO getDAO() {
		return (ILimitDAO) BeanHouse.get("limitJdbcDao");
	}

}